import csv

tsv_path = "/home/fhu_thjen/projects/learning-java/no19_collections_framework/anki/basic.tsv"
with open(tsv_path, "r", encoding="utf-8") as f:
    reader = csv.reader(f, delimiter="\t")
    header = next(reader)
    rows = list(reader)

print(f"Total basic cards: {len(rows)}")
for i in range(15):
    print(f"Row {i+1}: {rows[i][0]} | {rows[i][1][:50]} | {rows[i][2][:50]}")

print("...")
for i in range(112, 125):
    print(f"Row {i+1}: {rows[i][0]} | {rows[i][1][:50]} | {rows[i][2][:50]}")
