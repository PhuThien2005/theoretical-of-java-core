#!/usr/bin/env bash
set -euo pipefail

# Helper script to run any Java file from root by passing its file path
cd "$(dirname "$0")"

FILE="${1:-}"
if [ -z "$FILE" ] || [ ! -f "$FILE" ]; then
  echo "Usage: ./jrun.sh <path/to/File.java>"
  echo "Example: ./jrun.sh no09_oop/practice/Constructor.java"
  exit 1
fi

# Extract package name from file if present
PKG=$(grep -E '^\s*package\s+' "$FILE" | head -n 1 | sed -E 's/^\s*package\s+([^;]+);.*/\1/' | tr -d ' \r\n' || true)
CLASS_NAME=$(basename "$FILE" .java)

if [ -n "$PKG" ]; then
  FULL_CLASS="${PKG}.${CLASS_NAME}"
else
  FULL_CLASS="${CLASS_NAME}"
fi

# Compile & Run
javac "$FILE"
java "$FULL_CLASS"
