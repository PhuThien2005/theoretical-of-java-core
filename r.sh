#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")"

case "${1:-sync}" in
  sync)
    shift || true
    python3 scripts/sync_anki.py --auto-url "$@"
    ;;
  check)
    shift || true
    python3 scripts/sync_anki.py --auto-url --check-connection "$@"
    ;;
  dry)
    shift || true
    python3 scripts/sync_anki.py --auto-url --dry-run "$@"
    ;;
  topic)
    shift || true
    if [ $# -lt 1 ]; then
      echo "Usage: ./r.sh topic <topic-folder>"
      echo "Example: ./r.sh topic no01_overview"
      exit 1
    fi
    python3 scripts/sync_anki.py --auto-url --topic "$1"
    ;;
  dry-topic)
    shift || true
    if [ $# -lt 1 ]; then
      echo "Usage: ./r.sh dry-topic <topic-folder>"
      echo "Example: ./r.sh dry-topic no01_overview"
      exit 1
    fi
    python3 scripts/sync_anki.py --auto-url --topic "$1" --dry-run
    ;;
  probe)
    shift || true
    python3 scripts/sync_anki.py --probe-urls "$@"
    ;;
  augment)
    shift || true
    python3 scripts/augment_anki_cards.py "$@"
    ;;
  audit-cards)
    shift || true
    python3 scripts/audit_anki_quality.py "$@"
    ;;
  audit-links)
    shift || true
    python3 scripts/audit_references.py "$@"
    ;;
  help|-h|--help)
    cat <<'EOF'
Usage:
  ./r.sh                     Sync all cards
  ./r.sh sync                Sync all cards
  ./r.sh check               Check AnkiConnect
  ./r.sh dry                 Dry-run all cards
  ./r.sh topic no01_overview   Sync one topic
  ./r.sh dry-topic no01_overview
  ./r.sh probe               Probe common WSL/AnkiConnect URLs
  ./r.sh augment             Add supplemental cards to reach 2x count
  ./r.sh augment --topic no09_oop --dry-run
  ./r.sh audit-cards         Check card quality and TSV structure
  ./r.sh audit-links         Check reference links and source trust

Extra args after "sync", "check", "dry", or "probe" are passed to sync_anki.py.
EOF
    ;;
  *)
    python3 scripts/sync_anki.py --auto-url "$@"
    ;;
esac
