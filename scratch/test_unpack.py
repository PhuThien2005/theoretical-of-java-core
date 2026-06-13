import sys

sys.path.append("/home/fhu_thjen/projects/learning-java/scratch")
from enrich_cards import concepts

for i, t in enumerate(concepts):
    if len(t) != 7:
        print(f"Index {i} has length {len(t)}: {t[0]}")
