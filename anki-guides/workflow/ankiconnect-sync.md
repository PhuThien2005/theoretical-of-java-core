# Syncing TSV Cards With AnkiConnect

This repository can sync topic-local TSV cards directly into Anki Desktop by using AnkiConnect.

## Required Setup

Do this once:

1. Install Anki Desktop.
2. Install the AnkiConnect add-on.
3. Restart Anki.
4. Keep Anki Desktop open while running the sync script.

AnkiConnect listens locally at:

```text
http://127.0.0.1:8765
```

## Install AnkiConnect

In Anki Desktop:

```text
Tools -> Add-ons -> Get Add-ons...
```

Paste this add-on code:

```text
2055492159
```

Press OK, then restart Anki.

After restart, check:

```text
Tools -> Add-ons
```

You should see:

```text
AnkiConnect
```

## Quick Browser Test

Open this URL in a browser while Anki Desktop is open:

```text
http://localhost:8765
```

If AnkiConnect is running, you should see something like:

```text
{"apiVersion": "AnkiConnect v.6"}
```

If this works in a Windows browser but the script fails inside WSL, AnkiConnect is fine. The issue is that WSL and Windows may not share the same `127.0.0.1`.

## WSL And Windows Anki

If you run:

```text
fhu_thjen@LAPTOP-...:~/projects/learning-java$
```

you are running the script inside WSL/Linux.

If Anki Desktop is open on Windows, this can happen:

```text
Windows browser -> http://localhost:8765 works
WSL Python     -> http://127.0.0.1:8765 fails
```

That is because `127.0.0.1` inside WSL can mean the WSL environment, not the Windows Anki process.

### Option A: Run The Script With Windows Python

This is the simplest option if you already have Python installed on Windows.

Open PowerShell in Windows and run the script from the WSL path:

```powershell
cd \\wsl$\Ubuntu\home\fhu_thjen\projects\learning-java
py scripts\sync_anki.py --check-connection
py scripts\sync_anki.py --topic no01_overview
```

Depending on your distro name, `Ubuntu` may be different. You can also open the project folder in File Explorer through WSL and copy the path.

### Option B: Keep Running From WSL

If you want to run the script from WSL, first change AnkiConnect config in Anki Desktop:

```json
{
    "apiKey": null,
    "apiLogPath": null,
    "ignoreOriginList": [],
    "webBindAddress": "0.0.0.0",
    "webBindPort": 8765,
    "webCorsOriginList": [
        "http://localhost"
    ]
}
```

The important change is:

```text
"webBindAddress": "0.0.0.0"
```

Then restart Anki.

Important: press OK in the AnkiConnect config window first, then fully quit Anki Desktop and open it again. The Add-ons window message "Changes will take effect when Anki is restarted" means the new bind address is not active yet.

After restart, ask the script for the likely Windows host URL:

```bash
python3 scripts/sync_anki.py --print-wsl-host-url
```

It will print something like:

```text
http://172.24.96.1:8765
```

Then test:

```bash
python3 scripts/sync_anki.py --url http://172.24.96.1:8765 --check-connection
```

You can also let the script try common WSL URLs:

```bash
python3 scripts/sync_anki.py --probe-urls
```

It will test URLs such as:

```text
http://127.0.0.1:8765
http://<wsl-dns-host>:8765
http://<wsl-default-gateway>:8765
```

If one URL is open, you can let the script choose it automatically:

```bash
python3 scripts/sync_anki.py --auto-url --check-connection
```

Sync one topic with auto-detection:

```bash
python3 scripts/sync_anki.py --auto-url --topic no01_overview
```

Sync everything with auto-detection:

```bash
python3 scripts/sync_anki.py --auto-url
```

## Short Helper Script

From the repository root, you can use:

```bash
./r.sh check
./r.sh topic no01_overview
./r.sh
```

Commands:

```text
./r.sh                     sync all cards
./r.sh sync                sync all cards
./r.sh check               check AnkiConnect
./r.sh dry                 dry-run all cards
./r.sh topic no01_overview   sync one topic
./r.sh dry-topic no01_overview
./r.sh probe               probe common WSL URLs
```

If that works, sync:

```bash
python3 scripts/sync_anki.py --url http://172.24.96.1:8765 --topic no01_overview
```

If the connection still fails, Windows Firewall may be blocking WSL -> Windows access to port `8765`. In that case, Option A is usually easier.

## Note Types Expected By The Script

The script expects these Anki note types and fields.

### Java Basic

Fields:

```text
ID
Front
Back
Source
```

TSV file:

```text
<topic>/anki/basic.tsv
```

Required TSV columns:

```text
Front
Back
```

Optional TSV columns:

```text
ID
Source
Tags
```

### Java Basic Extra

Fields:

```text
ID
Front
Back
Extra
Source
```

TSV file:

```text
<topic>/anki/basic-extra.tsv
```

Required TSV columns:

```text
Front
Back
Extra
```

Optional TSV columns:

```text
ID
Source
Tags
```

### Java Cloze

Fields:

```text
ID
Text
Extra
Source
```

TSV file:

```text
<topic>/anki/cloze.tsv
```

Required TSV columns:

```text
Text
Extra
```

Optional TSV columns:

```text
ID
Source
Tags
```

### Java Code Question

Fields:

```text
ID
Question
Code
Answer
Explanation
Source
```

TSV file:

```text
<topic>/anki/code-question.tsv
```

Required TSV columns:

```text
Question
Code
Answer
Explanation
```

Optional TSV columns:

```text
ID
Source
Tags
```

## Important ID Rule

The script uses the `ID` field to update existing notes.

If a TSV row has no `ID`, the script generates one from:

```text
file path + row number + note type + question/text
```

That works for quick testing, but explicit IDs are safer for long-term editing. If you later rewrite the question or reorder rows, generated IDs can change.

Recommended long-term format:

```tsv
ID	Front	Back	Source	Tags
overview-001	What is Java?	Java is a general-purpose programming language.	no01_overview/theory/01-what-is-java.md	java::core::overview
```

## Run A Dry Run

Dry run reads and validates TSV files without modifying Anki:

```bash
python3 scripts/sync_anki.py --dry-run
```

Sync only one topic:

```bash
python3 scripts/sync_anki.py --topic no01_overview --dry-run
```

## Check AnkiConnect

With Anki Desktop open:

```bash
python3 scripts/sync_anki.py --check-connection
```

Expected output:

```text
AnkiConnect is reachable. Version: 6
```

## Sync Cards

Make sure Anki Desktop is open, then run:

```bash
python3 scripts/sync_anki.py
```

Sync one topic:

```bash
python3 scripts/sync_anki.py --topic no01_overview
```

The script will:

1. Read `*/anki/*.tsv`.
2. Create the `Java Core` deck if it does not exist.
3. Check that the expected note types and fields exist.
4. Convert escaped `\n` in TSV fields into real line breaks.
5. Upload topic media from `<topic>/media/anki/*`.
6. Add new notes.
7. Update existing notes with the same `ID`.

## Tag Behavior

By default, the script adds tags from the TSV file but does not remove existing tags from Anki.

To replace tags with exactly the TSV tags:

```bash
python3 scripts/sync_anki.py --replace-tags
```

Use this carefully if you manually add tags inside Anki.

## Common Errors

### Connection refused

Anki Desktop is not open, or AnkiConnect is not running.

Fix:

```text
Open Anki Desktop -> wait until it loads -> run the script again
```

### Missing note type

The Anki note type, such as `Java Basic`, does not exist.

Fix:

```text
Tools -> Manage Note Types -> Add
```

### Missing field

The note type exists but does not have a required field such as `ID` or `Source`.

Fix:

```text
Tools -> Manage Note Types -> Fields...
```
