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

# Locate the matching directory
MATCH_DIR=$(find . -maxdepth 1 -type d -name "${TOPIC_ARG}-*" | head -n 1)

# Fallback: substring search (e.g. "oop" matches "09-oop")
if [ -z "$MATCH_DIR" ]; then
  MATCH_DIR=$(find . -maxdepth 1 -type d -name "*${TOPIC_ARG}*" | grep -E '\/[0-9]{2}-' | head -n 1)
fi

if [ -z "$MATCH_DIR" ] || [ ! -d "$MATCH_DIR" ]; then
  echo "❌ Error: No topic matching '$1' found."
  exit 1
fi

TOPIC_FOLDER=$(basename "$MATCH_DIR")

echo "⏳ Running tests for: $TOPIC_FOLDER"
python3 scripts/verify_exercise.py "$TOPIC_FOLDER" "${@:2}"
