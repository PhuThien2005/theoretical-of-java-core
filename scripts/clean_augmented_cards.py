import csv
from pathlib import Path

def clean_topic(topic_dir: Path):
    anki_dir = topic_dir / "anki"
    if not anki_dir.exists():
        return
    for file_path in anki_dir.glob("*.tsv"):
        rows = []
        headers = []
        with file_path.open("r", encoding="utf-8", newline="") as f:
            reader = csv.reader(f, delimiter="\t")
            try:
                headers = next(reader)
            except StopIteration:
                continue
            for row in reader:
                if row and len(row) > 0:
                    note_id = row[0]
                    if "-aug-" in note_id:
                        continue
                rows.append(row)
        
        with file_path.open("w", encoding="utf-8", newline="") as f:
            writer = csv.writer(f, delimiter="\t", lineterminator="\n")
            writer.writerow(headers)
            writer.writerows(rows)

def main():
    root = Path.cwd()
    for topic_dir in sorted(root.iterdir()):
        if topic_dir.is_dir() and topic_dir.name[:2].isdigit():
            clean_topic(topic_dir)
    print("Cleanup complete. All augmented cards containing '-aug-' have been removed.")

if __name__ == "__main__":
    main()
