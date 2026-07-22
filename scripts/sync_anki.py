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
import re
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
        optional_columns=("ID", "Code", "Source", "Tags"),
        anki_fields=("ID", "Front", "Back", "Code", "Source"),
        identity_column="Front",
    ),
    "basic-extra.tsv": NoteConfig(
        file_name="basic-extra.tsv",
        model_name="Java Basic Extra",
        required_columns=("Front", "Back", "Extra"),
        optional_columns=("ID", "Code", "Source", "Tags"),
        anki_fields=("ID", "Front", "Back", "Extra", "Code", "Source"),
        identity_column="Front",
    ),
    "cloze.tsv": NoteConfig(
        file_name="cloze.tsv",
        model_name="Java Cloze",
        required_columns=("Text", "Extra"),
        optional_columns=("ID", "Code", "Source", "Tags"),
        anki_fields=("ID", "Text", "Extra", "Code", "Source"),
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


def normalize_field_value(value: str) -> str:
    val = (
        value.strip()
        .replace("\\r\\n", "\n")
        .replace("\\n", "\n")
        .replace("\\t", "    ")
    )
    # Escape generic angle brackets (like <int> or <Integer>) but preserve HTML br and img tags
    br_pattern = re.compile(r'<br\s*/?>', re.IGNORECASE)
    img_pattern = re.compile(r'<img\s+[^>]*>', re.IGNORECASE)
    
    img_tags = []
    def img_replacer(match):
        img_tags.append(match.group(0))
        return f"___IMG_TAG_{len(img_tags)-1}___"
        
    val_protected = br_pattern.sub("___BR_TAG___", val)
    val_protected = img_pattern.sub(img_replacer, val_protected)
    
    val_escaped = val_protected.replace("<", "&lt;").replace(">", "&gt;")
    
    val_restored = val_escaped.replace("___BR_TAG___", "<br>")
    for idx, tag in enumerate(img_tags):
        val_restored = val_restored.replace(f"___IMG_TAG_{idx}___", tag)
        
    return val_restored


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
                    field: normalize_field_value(row.get(field) or "")
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


def chunk_list(lst: list[Any], chunk_size: int):
    for i in range(0, len(lst), chunk_size):
        yield lst[i : i + chunk_size]


def fetch_existing_notes(client: AnkiConnectClient, deck_name: str) -> dict[tuple[str, str], dict[str, Any]]:
    # Get all note IDs in the deck
    note_ids = client.invoke("findNotes", {"query": f'deck:"{deck_name}"'})
    if not note_ids:
        return {}

    existing_notes: dict[tuple[str, str], dict[str, Any]] = {}
    
    # Batch query their details
    for chunk in chunk_list(note_ids, 1000):
        notes_info = client.invoke("notesInfo", {"notes": chunk})
        for note in notes_info:
            model_name = note.get("modelName", "")
            fields = note.get("fields", {})
            
            # Convert fields dict to simple key-value dict
            fields_dict = {
                name: info.get("value", "")
                for name, info in fields.items()
            }
            
            note_id_val = fields_dict.get("ID", "").strip()
            if note_id_val:
                existing_notes[(model_name, note_id_val)] = {
                    "noteId": note.get("noteId"),
                    "fields": fields_dict,
                    "tags": note.get("tags", []),
                }
                
    return existing_notes


def sync_cards(
    client: AnkiConnectClient,
    cards: list[CardRow],
    deck_name: str,
    dry_run: bool,
    replace_tags: bool,
) -> tuple[int, int]:
    added = 0
    updated = 0

    if dry_run:
        # In a dry run, we don't query the full Anki deck to avoid network calls if not requested.
        # But wait, checking if they exist is helpful to print correct DRY RUN logs.
        # Let's try to fetch existing notes anyway, but if it fails we fallback or handle it.
        try:
            existing_notes = fetch_existing_notes(client, deck_name)
        except Exception:
            existing_notes = {}
    else:
        existing_notes = fetch_existing_notes(client, deck_name)

    cards_to_add: list[CardRow] = []
    cards_to_update: list[tuple[int, CardRow, bool, bool, list[str]]] = []

    for card in cards:
        key = (card.config.model_name, card.note_id)
        if key not in existing_notes:
            cards_to_add.append(card)
        else:
            existing_note = existing_notes[key]
            note_anki_id = existing_note["noteId"]
            
            # Compare fields
            fields_differ = False
            for field_name in card.config.anki_fields:
                local_val = card.fields.get(field_name, "")
                remote_val = existing_note["fields"].get(field_name, "")
                if local_val != remote_val:
                    fields_differ = True
                    break
            
            # Compare tags
            tags_differ = False
            if replace_tags:
                if sorted(card.tags) != sorted(existing_note["tags"]):
                    tags_differ = True
            else:
                # Only update tags if there are local tags not present in remote tags
                missing_tags = [t for t in card.tags if t not in existing_note["tags"]]
                if missing_tags:
                    tags_differ = True

            if fields_differ or tags_differ:
                cards_to_update.append((note_anki_id, card, fields_differ, tags_differ, existing_note["tags"]))

    # Perform additions
    if cards_to_add:
        if dry_run:
            for card in cards_to_add:
                print(
                    f"DRY RUN  ADD {card.config.model_name:<17} "
                    f"{card.note_id}  {card.source}"
                )
            added = len(cards_to_add)
        else:
            print(f"Adding {len(cards_to_add)} new cards...")
            # Batch add using addNotes
            for chunk in chunk_list(cards_to_add, 500):
                notes_payload = []
                for card in chunk:
                    notes_payload.append({
                        "deckName": deck_name,
                        "modelName": card.config.model_name,
                        "fields": card.fields,
                        "tags": card.tags,
                        "options": {
                            "allowDuplicate": False,
                        }
                    })
                results = client.invoke("addNotes", {"notes": notes_payload})
                for card, res in zip(chunk, results):
                    if res is None:
                        print(f"WARNING: Failed to add card {card.note_id} from {card.source}")
                    else:
                        added += 1

    # Perform updates
    if cards_to_update:
        if dry_run:
            for _, card, fields_differ, tags_differ, _ in cards_to_update:
                diffs = []
                if fields_differ:
                    diffs.append("fields")
                if tags_differ:
                    diffs.append("tags")
                print(
                    f"DRY RUN  UPDATE ({'+'.join(diffs)}) {card.config.model_name:<17} "
                    f"{card.note_id}  {card.source}"
                )
            updated = len(cards_to_update)
        else:
            print(f"Updating {len(cards_to_update)} modified cards...")
            for note_anki_id, card, fields_differ, tags_differ, remote_tags in cards_to_update:
                if fields_differ:
                    client.invoke(
                        "updateNoteFields",
                        {
                            "note": {
                                "id": note_anki_id,
                                "fields": card.fields,
                            }
                        },
                    )
                if tags_differ:
                    if replace_tags:
                        if remote_tags:
                            client.invoke(
                                "removeTags",
                                {
                                    "notes": [note_anki_id],
                                    "tags": " ".join(remote_tags),
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
                    else:
                        missing_tags = [t for t in card.tags if t not in remote_tags]
                        if missing_tags:
                            client.invoke(
                                "addTags",
                                {
                                    "notes": [note_anki_id],
                                    "tags": " ".join(missing_tags),
                                },
                            )
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
    parser.add_argument("--topic", action="append", default=[], help="Sync one topic folder. Can be repeated, e.g. --topic no01_overview.")
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
