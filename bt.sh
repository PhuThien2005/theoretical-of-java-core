#!/usr/bin/env bash
set -euo pipefail

# Deduces target workspace directory
cd "$(dirname "$0")"

# Usage check
if [ $# -lt 1 ]; then
  echo "Usage:"
  echo "  ./bt.sh <number/name>  - Run tests for a specific topic (e.g., ./bt.sh 2 or ./bt.sh oop)"
  echo "  ./bt.sh all            - Run tests for all topics"
  exit 1
fi

TOPIC_ARG="$1"

# Run all exercises
if [ "$TOPIC_ARG" = "all" ]; then
  echo "Running verification for all exercises..."
  python3 scripts/verify_exercise.py "${@:2}"
  exit 0
fi

# Pad single digit numbers to two digits (e.g., 2 -> 02, 9 -> 09)
if [[ "$TOPIC_ARG" =~ ^[0-9]$ ]]; then
  TOPIC_ARG="0$TOPIC_ARG"
fi

# Locate the matching directory (e.g. 9 or 09 or oop -> no09_oop)
MATCH_DIR=$(find . -maxdepth 1 -type d \( -name "no${TOPIC_ARG}_*" -o -name "*${TOPIC_ARG}*" \) | grep -E '\/no[0-9]{2}_' | head -n 1)

if [ -z "$MATCH_DIR" ] || [ ! -d "$MATCH_DIR" ]; then
  echo "❌ Error: No topic matching '$1' found."
  exit 1
fi

TOPIC_FOLDER=$(basename "$MATCH_DIR")

echo "⏳ Running tests for: $TOPIC_FOLDER"
python3 scripts/verify_exercise.py "$TOPIC_FOLDER" "${@:2}"
