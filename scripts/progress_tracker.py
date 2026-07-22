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
    "no01_overview",
    "no02_basic_syntax",
    "no03_data_types",
    "no04_variables_constants",
    "no05_operators",
    "no06_control_flow",
    "no07_arrays",
    "no08_string",
    "no09_oop",
    "no10_modifiers",
    "no11_package_access_control",
    "no12_exception_handling",
    "no13_memory_management",
    "no14_object_class",
    "no15_inner_nested_class",
    "no16_enum",
    "no17_annotation",
    "no18_generics",
    "no19_collections_framework",
    "no20_comparable_comparator",
    "no21_lambda_expression",
    "no22_functional_interface",
    "no23_stream_api",
    "no24_optional",
    "no25_date_time_api",
    "no26_io",
    "no27_nio",
    "no28_multithreading",
    "no29_synchronization_concurrency",
    "no30_regex",
    "no31_reflection",
    "no32_classloader",
    "no33_module_system",
    "no34_jdbc",
    "no35_networking",
    "no36_security_basic",
    "no37_jvm_advanced",
    "no38_build_compile_run",
    "no39_utility_apis",
    "no40_modern_java_concepts",
    "no41_best_practices",
    "no42_design_principles",
    "no43_design_patterns",
    "no44_unit_testing",
    "no45_interview_questions",
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
