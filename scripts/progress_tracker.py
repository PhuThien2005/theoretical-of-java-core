#!/usr/bin/env python3
"""
progress_tracker.py — Track which topics have been enriched with code examples.
Usage:
  python3 scripts/progress_tracker.py list           # list all topics + status
  python3 scripts/progress_tracker.py done <topic>   # mark topic as done
  python3 scripts/progress_tracker.py next           # print next undone topic
  python3 scripts/progress_tracker.py reset          # reset all to pending
"""
import json
import sys
from pathlib import Path

ROOT = Path(__file__).parent.parent
STATE_FILE = ROOT / "reports" / "enrich_progress.json"

TOPICS = [
    "01-overview",
    "02-basic-syntax",
    "03-data-types",
    "04-variables-constants",
    "05-operators",
    "06-control-flow",
    "07-arrays",
    "08-string",
    "09-oop",
    "10-modifiers",
    "11-package-access-control",
    "12-exception-handling",
    "13-memory-management",
    "14-object-class",
    "15-inner-nested-class",
    "16-enum",
    "17-annotation",
    "18-generics",
    "19-collections-framework",
    "20-comparable-comparator",
    "21-lambda-expression",
    "22-functional-interface",
    "23-stream-api",
    "24-optional",
    "25-date-time-api",
    "26-io",
    "27-nio",
    "28-multithreading",
    "29-synchronization-concurrency",
    "30-regex",
    "31-reflection",
    "32-classloader",
    "33-module-system",
    "34-jdbc",
    "35-networking",
    "36-security-basic",
    "37-jvm-advanced",
    "38-build-compile-run",
    "39-utility-apis",
    "40-modern-java-concepts",
    "41-best-practices",
    "42-design-principles",
    "43-design-patterns",
    "44-unit-testing",
    "45-interview-questions",
]


def load_state():
    if STATE_FILE.exists():
        return json.loads(STATE_FILE.read_text())
    return {t: "pending" for t in TOPICS}


def save_state(state):
    STATE_FILE.parent.mkdir(parents=True, exist_ok=True)
    STATE_FILE.write_text(json.dumps(state, indent=2))


def cmd_list(state):
    done = sum(1 for v in state.values() if v == "done")
    print(f"Progress: {done}/{len(TOPICS)} done\n")
    for topic in TOPICS:
        status = state.get(topic, "pending")
        icon = "✅" if status == "done" else ("🔄" if status == "in_progress" else "⏳")
        print(f"  {icon}  {topic}")


def cmd_done(state, topic):
    if topic not in TOPICS:
        print(f"ERROR: unknown topic '{topic}'", file=sys.stderr)
        sys.exit(1)
    state[topic] = "done"
    save_state(state)
    done = sum(1 for v in state.values() if v == "done")
    print(f"Marked {topic} as done. ({done}/{len(TOPICS)})")


def cmd_next(state):
    for topic in TOPICS:
        if state.get(topic, "pending") != "done":
            print(topic)
            return
    print("ALL_DONE")


def cmd_reset(state):
    for t in TOPICS:
        state[t] = "pending"
    save_state(state)
    print("Reset all topics to pending.")


def main():
    args = sys.argv[1:]
    state = load_state()
    if not args or args[0] == "list":
        cmd_list(state)
    elif args[0] == "done" and len(args) == 2:
        cmd_done(state, args[1])
    elif args[0] == "next":
        cmd_next(state)
    elif args[0] == "reset":
        cmd_reset(state)
    else:
        print(__doc__)
        sys.exit(1)


if __name__ == "__main__":
    main()
