#!/usr/bin/env python3
"""
Sync Java Core TSV flashcards into Anki through AnkiConnect.

Requirements:
- Anki Desktop is open.
- AnkiConnect is installed and enabled.
- AnkiConnect is reachable from the Python environment running this script.
"""

from __future__ import annotations

import argparse
import base64
import csv
import hashlib
import json
import subprocess
import sys
import urllib.error
import urllib.request
from dataclasses import dataclass
from pathlib import Path
from typing import Any


DEFAULT_ANKI_CONNECT_URL = "http://127.0.0.1:8765"
DEFAULT_DECK_NAME = "Java Core"
DEFAULT_ANKI_CONNECT_API_VERSION = 6


@dataclass(frozen=True)
class NoteConfig:
    file_name: str
    model_name: str
    required_columns: tuple[str, ...]
    optional_columns: tuple[str, ...]
    anki_fields: tuple[str, ...]
    identity_column: str


NOTE_CONFIGS = {
    "basic.tsv": NoteConfig(
        file_name="basic.tsv",
        model_name="Java Basic",
        required_columns=("Front", "Back"),
        optional_columns=("ID", "Source", "Tags"),
        anki_fields=("ID", "Front", "Back", "Source"),
        identity_column="Front",
    ),
    "basic-extra.tsv": NoteConfig(
        file_name="basic-extra.tsv",
        model_name="Java Basic Extra",
        required_columns=("Front", "Back", "Extra"),
        optional_columns=("ID", "Source", "Tags"),
        anki_fields=("ID", "Front", "Back", "Extra", "Source"),
        identity_column="Front",
    ),
    "cloze.tsv": NoteConfig(
        file_name="cloze.tsv",
        model_name="Java Cloze",
        required_columns=("Text", "Extra"),
        optional_columns=("ID", "Source", "Tags"),
        anki_fields=("ID", "Text", "Extra", "Source"),
        identity_column="Text",
    ),
    "code-question.tsv": NoteConfig(
        file_name="code-question.tsv",
        model_name="Java Code Question",
        required_columns=("Question", "Code", "Answer", "Explanation"),
        optional_columns=("ID", "Source", "Tags"),
        anki_fields=("ID", "Question", "Code", "Answer", "Explanation", "Source"),
        identity_column="Question",
    ),
}


class SyncError(Exception):
    pass


class AnkiConnectClient:
    def __init__(self, url: str, api_version: int) -> None:
        self.url = url
        self.api_version = api_version

    def invoke(self, action: str, params: dict[str, Any] | None = None) -> Any:
        payload = {
            "action": action,
            "version": self.api_version,
            "params": params or {},
        }

        request = urllib.request.Request(
            self.url,
            data=json.dumps(payload).encode("utf-8"),
            headers={"Content-Type": "application/json"},
        )

        try:
            with urllib.request.urlopen(request, timeout=10) as response:
                raw = response.read().decode("utf-8")
        except urllib.error.URLError as exc:
            raise SyncError(
                "Could not connect to AnkiConnect. Open Anki Desktop, make sure "
                "AnkiConnect is installed/enabled, then try again. If you are "
                "running this script in WSL while Anki runs on Windows, see "
                "anki-guides/workflow/ankiconnect-sync.md."
            ) from exc

        try:
            result = json.loads(raw)
        except json.JSONDecodeError as exc:
            raise SyncError(f"Invalid JSON response from AnkiConnect: {raw}") from exc

        if result.get("error") is not None:
            raise SyncError(f"AnkiConnect error during {action}: {result['error']}")

        return result.get("result")


@dataclass
class CardRow:
    path: Path
    line_number: int
    config: NoteConfig
    note_id: str
    source: str
    tags: list[str]
    fields: dict[str, str]


def repo_relative(path: Path, root: Path) -> str:
    return path.resolve().relative_to(root.resolve()).as_posix()


def generated_id(path: Path, root: Path, line_number: int, config: NoteConfig, row: dict[str, str]) -> str:
    identity = row.get(config.identity_column, "")
    seed = f"{repo_relative(path, root)}:{line_number}:{config.file_name}:{identity}"
    digest = hashlib.sha1(seed.encode("utf-8")).hexdigest()[:16]
    return f"jc_{digest}"


def normalize_tags(raw_tags: str) -> list[str]:
    return [tag.strip() for tag in raw_tags.split() if tag.strip()]


def read_cards(root: Path, topic_filters: list[str]) -> list[CardRow]:
    cards: list[CardRow] = []
    topic_filter_set = set(topic_filters)

    for path in sorted(root.glob("*/anki/*.tsv")):
        if path.name not in NOTE_CONFIGS:
            continue

        topic_name = path.parent.parent.name
        if topic_filter_set and topic_name not in topic_filter_set:
            continue

        config = NOTE_CONFIGS[path.name]

        with path.open("r", encoding="utf-8", newline="") as file:
            reader = csv.DictReader(file, delimiter="\t")
            headers = set(reader.fieldnames or [])
            missing = set(config.required_columns) - headers

            if missing:
                raise SyncError(
                    f"{repo_relative(path, root)} is missing required columns: "
                    f"{', '.join(sorted(missing))}"
                )

            for line_number, row in enumerate(reader, start=2):
                if not any((value or "").strip() for value in row.values()):
                    continue

                note_id = (row.get("ID") or "").strip()
                if not note_id:
                    note_id = generated_id(path, root, line_number, config, row)

                source = (row.get("Source") or "").strip()
                if not source:
                    source = repo_relative(path, root)

                fields = {
                    field: (row.get(field) or "").strip()
                    for field in config.anki_fields
                    if field not in {"ID", "Source"}
                }
                fields["ID"] = note_id
                fields["Source"] = source

                tags = normalize_tags(row.get("Tags") or f"java::core::{topic_name[3:]}")

                cards.append(
                    CardRow(
                        path=path,
                        line_number=line_number,
                        config=config,
                        note_id=note_id,
                        source=source,
                        tags=tags,
                        fields=fields,
                    )
                )

    return cards


def require_unique_ids(cards: list[CardRow], root: Path) -> None:
    seen: dict[str, CardRow] = {}

    for card in cards:
        previous = seen.get(card.note_id)
        if previous:
            raise SyncError(
                "Duplicate card ID found:\n"
                f"- {card.note_id} at {repo_relative(previous.path, root)}:{previous.line_number}\n"
                f"- {card.note_id} at {repo_relative(card.path, root)}:{card.line_number}"
            )
        seen[card.note_id] = card


def ensure_deck(client: AnkiConnectClient, deck_name: str) -> None:
    decks = client.invoke("deckNames")
    if deck_name not in decks:
        client.invoke("createDeck", {"deck": deck_name})


def ensure_models(client: AnkiConnectClient, cards: list[CardRow]) -> None:
    model_names = set(client.invoke("modelNames"))

    needed_configs = {card.config for card in cards}
    for config in sorted(needed_configs, key=lambda item: item.model_name):
        if config.model_name not in model_names:
            raise SyncError(
                f'Missing Anki note type "{config.model_name}". Create it in Anki first.'
            )

        fields = set(client.invoke("modelFieldNames", {"modelName": config.model_name}))
        missing = set(config.anki_fields) - fields
        if missing:
            raise SyncError(
                f'Note type "{config.model_name}" is missing fields: '
                f"{', '.join(sorted(missing))}"
            )


def media_files(root: Path, topic_filters: list[str]) -> list[Path]:
    topic_filter_set = set(topic_filters)
    files: list[Path] = []

    for path in sorted(root.glob("*/media/anki/*")):
        if not path.is_file():
            continue

        topic_name = path.parent.parent.parent.name
        if topic_filter_set and topic_name not in topic_filter_set:
            continue

        files.append(path)

    return files


def upload_media(client: AnkiConnectClient, paths: list[Path], dry_run: bool) -> None:
    if not paths:
        return

    for path in paths:
        if dry_run:
            print(f"DRY RUN  media {path.name}")
            continue

        encoded = base64.b64encode(path.read_bytes()).decode("ascii")
        client.invoke(
            "storeMediaFile",
            {
                "filename": path.name,
                "data": encoded,
            },
        )

    if not dry_run:
        print(f"Uploaded {len(paths)} media files.")


def find_existing_note(client: AnkiConnectClient, deck_name: str, config: NoteConfig, note_id: str) -> int | None:
    query = f'deck:"{deck_name}" note:"{config.model_name}" ID:{note_id}'
    notes = client.invoke("findNotes", {"query": query})

    if len(notes) > 1:
        raise SyncError(
            f'Found more than one note for ID "{note_id}" in model "{config.model_name}".'
        )

    if not notes:
        return None

    return int(notes[0])


def add_note(client: AnkiConnectClient, deck_name: str, card: CardRow) -> int:
    return int(
        client.invoke(
            "addNote",
            {
                "note": {
                    "deckName": deck_name,
                    "modelName": card.config.model_name,
                    "fields": card.fields,
                    "tags": card.tags,
                    "options": {
                        "allowDuplicate": False,
                    },
                }
            },
        )
    )


def update_note(client: AnkiConnectClient, note_anki_id: int, card: CardRow, replace_tags: bool) -> None:
    client.invoke(
        "updateNoteFields",
        {
            "note": {
                "id": note_anki_id,
                "fields": card.fields,
            }
        },
    )

    if replace_tags:
        current_tags = client.invoke("getNoteTags", {"note": note_anki_id})
        if current_tags:
            client.invoke(
                "removeTags",
                {
                    "notes": [note_anki_id],
                    "tags": " ".join(current_tags),
                },
            )

    if card.tags:
        client.invoke(
            "addTags",
            {
                "notes": [note_anki_id],
                "tags": " ".join(card.tags),
            },
        )


def sync_cards(
    client: AnkiConnectClient,
    cards: list[CardRow],
    deck_name: str,
    dry_run: bool,
    replace_tags: bool,
) -> tuple[int, int]:
    added = 0
    updated = 0

    for card in cards:
        if dry_run:
            print(
                f"DRY RUN  {card.config.model_name:<17} "
                f"{card.note_id}  {card.source}"
            )
            continue

        existing_note_id = find_existing_note(client, deck_name, card.config, card.note_id)

        if existing_note_id is None:
            add_note(client, deck_name, card)
            added += 1
        else:
            update_note(client, existing_note_id, card, replace_tags)
            updated += 1

    return added, updated


def detect_wsl_windows_host_url() -> str | None:
    resolv_conf = Path("/etc/resolv.conf")
    if not resolv_conf.exists():
        return None

    for line in resolv_conf.read_text(encoding="utf-8", errors="ignore").splitlines():
        parts = line.strip().split()
        if len(parts) == 2 and parts[0] == "nameserver":
            return f"http://{parts[1]}:8765"

    return None


def detect_wsl_candidate_urls() -> list[str]:
    urls = [DEFAULT_ANKI_CONNECT_URL]

    resolv_url = detect_wsl_windows_host_url()
    if resolv_url:
        urls.append(resolv_url)

    try:
        route_output = subprocess.check_output(
            ["ip", "route"],
            text=True,
            stderr=subprocess.DEVNULL,
        )
    except (OSError, subprocess.CalledProcessError):
        route_output = ""

    for line in route_output.splitlines():
        parts = line.split()
        if len(parts) >= 3 and parts[0] == "default" and parts[1] == "via":
            urls.append(f"http://{parts[2]}:8765")

    deduped: list[str] = []
    for url in urls:
        if url not in deduped:
            deduped.append(url)
    return deduped


def probe_urls(urls: list[str], api_version: int) -> int:
    found = 0
    for url in urls:
        try:
            version = AnkiConnectClient(url, api_version).invoke("version")
        except SyncError as exc:
            print(f"CLOSED  {url}  ({exc})")
            continue

        print(f"OPEN    {url}  (AnkiConnect version: {version})")
        found += 1

    return found


def find_first_reachable_url(urls: list[str], api_version: int) -> str | None:
    for url in urls:
        try:
            AnkiConnectClient(url, api_version).invoke("version")
        except SyncError:
            continue
        return url


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Sync topic-local Java Core Anki TSV files through AnkiConnect."
    )
    parser.add_argument("--root", default=".", help="Repository root. Default: current directory.")
    parser.add_argument("--deck", default=DEFAULT_DECK_NAME, help=f"Anki deck name. Default: {DEFAULT_DECK_NAME}")
    parser.add_argument("--url", default=DEFAULT_ANKI_CONNECT_URL, help=f"AnkiConnect URL. Default: {DEFAULT_ANKI_CONNECT_URL}")
    parser.add_argument("--api-version", type=int, default=DEFAULT_ANKI_CONNECT_API_VERSION, help=f"AnkiConnect API version. Default: {DEFAULT_ANKI_CONNECT_API_VERSION}")
    parser.add_argument("--topic", action="append", default=[], help="Sync one topic folder. Can be repeated, e.g. --topic 01-overview.")
    parser.add_argument("--dry-run", action="store_true", help="Read and validate TSV files without calling AnkiConnect.")
    parser.add_argument("--check-connection", action="store_true", help="Only check whether AnkiConnect is reachable.")
    parser.add_argument("--auto-url", action="store_true", help="Automatically use the first reachable AnkiConnect URL.")
    parser.add_argument("--probe-urls", action="store_true", help="Try common AnkiConnect URLs, useful when running in WSL.")
    parser.add_argument("--print-wsl-host-url", action="store_true", help="Print the likely Windows host AnkiConnect URL from WSL.")
    parser.add_argument("--replace-tags", action="store_true", help="Replace Anki note tags with the TSV Tags column instead of only adding tags.")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    root = Path(args.root).resolve()

    try:
        if args.auto_url:
            detected_url = find_first_reachable_url(detect_wsl_candidate_urls(), args.api_version)
            if not detected_url:
                raise SyncError(
                    "Could not auto-detect a reachable AnkiConnect URL. Run "
                    "`python3 scripts/sync_anki.py --probe-urls` for details."
                )
            args.url = detected_url
            print(f"Using AnkiConnect URL: {args.url}")

        if args.print_wsl_host_url:
            url = detect_wsl_windows_host_url()
            if not url:
                print("Could not detect a WSL Windows host URL.")
                return 1
            print(url)
            return 0

        if args.probe_urls:
            found = probe_urls(detect_wsl_candidate_urls(), args.api_version)
            if found == 0:
                print(
                    "No reachable AnkiConnect URL found. If Anki runs on Windows "
                    "and this script runs in WSL, restart Anki after setting "
                    'webBindAddress to "0.0.0.0", then try again.'
                )
                return 1
            return 0

        if args.check_connection:
            client = AnkiConnectClient(args.url, args.api_version)
            version = client.invoke("version")
            print(f"AnkiConnect is reachable. Version: {version}")
            return 0

        cards = read_cards(root, args.topic)
        require_unique_ids(cards, root)
        media = media_files(root, args.topic)

        if not cards:
            print("No TSV cards found.")
            return 0

        print(f"Loaded {len(cards)} cards from topic-local TSV files.")
        if media:
            print(f"Found {len(media)} media files.")

        if args.dry_run:
            upload_media(AnkiConnectClient(args.url, args.api_version), media, True)
            sync_cards(
                client=AnkiConnectClient(args.url, args.api_version),
                cards=cards,
                deck_name=args.deck,
                dry_run=True,
                replace_tags=args.replace_tags,
            )
            print("Dry run complete. Anki was not modified.")
            return 0

        client = AnkiConnectClient(args.url, args.api_version)
        version = client.invoke("version")
        print(f"Connected to AnkiConnect v{version}.")

        ensure_deck(client, args.deck)
        ensure_models(client, cards)
        upload_media(client, media, False)
        added, updated = sync_cards(client, cards, args.deck, False, args.replace_tags)

        print(f"Sync complete. Added: {added}. Updated: {updated}.")
        return 0
    except SyncError as exc:
        print(f"ERROR: {exc}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
