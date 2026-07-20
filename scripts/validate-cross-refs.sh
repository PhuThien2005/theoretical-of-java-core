#!/usr/bin/env bash
# validate-cross-refs.sh — Kiểm tra tính hợp lệ của tất cả liên kết chéo trong dự án
# Sử dụng: bash scripts/validate-cross-refs.sh [chapter-dir]
# Ví dụ:   bash scripts/validate-cross-refs.sh vi/10-modifiers
#           bash scripts/validate-cross-refs.sh  (kiểm tra toàn bộ)

set -uo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
VI_DIR="$PROJECT_ROOT/vi"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
CYAN='\033[0;36m'
NC='\033[0m'

# Temp files for counting
TMPDIR_WORK=$(mktemp -d)
echo 0 > "$TMPDIR_WORK/total"
echo 0 > "$TMPDIR_WORK/valid"
echo 0 > "$TMPDIR_WORK/broken"
echo 0 > "$TMPDIR_WORK/dupes"
echo 0 > "$TMPDIR_WORK/format"

trap "rm -rf $TMPDIR_WORK" EXIT

# Target directory
TARGET_DIR="${1:-$VI_DIR}"
if [[ ! "$TARGET_DIR" = /* ]]; then
    TARGET_DIR="$PROJECT_ROOT/$TARGET_DIR"
fi

echo -e "${CYAN}=== Cross-Reference Link Validator ===${NC}"
echo -e "Scanning: ${TARGET_DIR}"
echo ""

# ============================================================
# TEST 1: Check all "Xem thêm" and "Học sâu tại" links
# ============================================================
echo -e "${CYAN}--- Test 1: Link Validity ---${NC}"

find "$TARGET_DIR" -name '*.md' -type f | while IFS= read -r file; do
    dir_of_file="$(dirname "$file")"
    rel_file="${file#$PROJECT_ROOT/}"

    # Extract all markdown links from cross-reference lines
    grep -n 'Xem thêm\|Học sâu tại' "$file" 2>/dev/null | grep -oP '\[.*?\]\(\K[^)]+' | while IFS= read -r link; do
        cur_total=$(cat "$TMPDIR_WORK/total")
        echo $((cur_total + 1)) > "$TMPDIR_WORK/total"

        # Skip external URLs and anchors
        [[ "$link" == http* ]] && continue
        [[ "$link" == \#* ]] && continue

        # Strip anchor
        file_path="${link%%#*}"

        # Resolve relative path
        resolved="$(cd "$dir_of_file" 2>/dev/null && realpath -m "$file_path" 2>/dev/null || echo "INVALID")"

        if [[ ! -f "$resolved" ]]; then
            echo -e "  ${RED}BROKEN${NC}: $rel_file"
            echo -e "         → $link"
            cur=$(cat "$TMPDIR_WORK/broken"); echo $((cur + 1)) > "$TMPDIR_WORK/broken"
        else
            cur=$(cat "$TMPDIR_WORK/valid"); echo $((cur + 1)) > "$TMPDIR_WORK/valid"
        fi
    done
done

echo ""

# ============================================================
# TEST 2: Duplicate links within same file
# ============================================================
echo -e "${CYAN}--- Test 2: Duplicate Links ---${NC}"

find "$TARGET_DIR" -name '*.md' -type f | while IFS= read -r file; do
    rel_file="${file#$PROJECT_ROOT/}"
    links=$(grep 'Xem thêm\|Học sâu tại' "$file" 2>/dev/null | grep -oP '\[.*?\]\(\K[^)]+' | sort 2>/dev/null || true)
    if [[ -n "$links" ]]; then
        dupes=$(echo "$links" | uniq -d 2>/dev/null || true)
        if [[ -n "$dupes" ]]; then
            echo -e "  ${YELLOW}DUPLICATE${NC}: $rel_file"
            echo "$dupes" | while IFS= read -r dup; do
                echo -e "         → $dup"
                cur=$(cat "$TMPDIR_WORK/dupes"); echo $((cur + 1)) > "$TMPDIR_WORK/dupes"
            done
        fi
    fi
done

echo ""

# ============================================================
# TEST 3: Format consistency
# ============================================================
echo -e "${CYAN}--- Test 3: Format Consistency ---${NC}"

find "$TARGET_DIR" -name '*.md' -type f | while IFS= read -r file; do
    rel_file="${file#$PROJECT_ROOT/}"
    prev_line="FIRST"
    line_num=0
    while IFS= read -r line || [[ -n "$line" ]]; do
        line_num=$((line_num + 1))
        if echo "$line" | grep -q '^> Xem thêm'; then
            if [[ "$prev_line" != "FIRST" && -n "$prev_line" ]]; then
                echo -e "  ${YELLOW}FORMAT${NC}: $rel_file:$line_num — missing blank line before '> Xem thêm'"
                cur=$(cat "$TMPDIR_WORK/format"); echo $((cur + 1)) > "$TMPDIR_WORK/format"
            fi
        fi
        prev_line="$line"
    done < "$file"
done

echo ""

# ============================================================
# TEST 4: Bidirectional check (Group A)
# ============================================================
echo -e "${CYAN}--- Test 4: Bidirectional Links (Group A) ---${NC}"

declare -a PAIRS=(
    "10-modifiers:29-synchronization"
    "10-modifiers:26-io"
    "10-modifiers:11-interfaces"
    "10-modifiers:22-lambda"
    "03-data-types:13-memory"
    "11-interfaces:22-lambda"
    "11-interfaces:41-functional"
    "22-lambda:23-stream"
    "14-object-class:19-collections"
    "15-generics:19-collections"
    "21-comparable:19-collections"
    "26-io:27-nio"
    "28-multithreading:29-synchronization"
    "28-multithreading:30-executor"
    "32-classloader:37-jvm"
    "26-io:36-security"
)

BIDIR_OK=0
BIDIR_PARTIAL=0
BIDIR_MISSING=0

for pair in "${PAIRS[@]}"; do
    src="${pair%%:*}"
    dst="${pair##*:}"

    src_dir=$(find "$VI_DIR" -maxdepth 1 -type d -name "${src}*" 2>/dev/null | head -1)
    dst_dir=$(find "$VI_DIR" -maxdepth 1 -type d -name "${dst}*" 2>/dev/null | head -1)

    [[ -z "$src_dir" || -z "$dst_dir" ]] && continue

    src_name=$(basename "$src_dir")
    dst_name=$(basename "$dst_dir")

    src_has=$(grep -rl "$dst_name" "$src_dir" --include='*.md' 2>/dev/null | head -1 || true)
    dst_has=$(grep -rl "$src_name" "$dst_dir" --include='*.md' 2>/dev/null | head -1 || true)

    if [[ -z "$src_has" && -z "$dst_has" ]]; then
        echo -e "  ${RED}MISSING BOTH${NC}: $src_name ↔ $dst_name"
        BIDIR_MISSING=$((BIDIR_MISSING + 1))
    elif [[ -z "$src_has" ]]; then
        echo -e "  ${YELLOW}MISSING →${NC}:  $src_name → $dst_name"
        BIDIR_PARTIAL=$((BIDIR_PARTIAL + 1))
    elif [[ -z "$dst_has" ]]; then
        echo -e "  ${YELLOW}MISSING ←${NC}:  $dst_name → $src_name"
        BIDIR_PARTIAL=$((BIDIR_PARTIAL + 1))
    else
        echo -e "  ${GREEN}OK${NC}:           $src_name ↔ $dst_name"
        BIDIR_OK=$((BIDIR_OK + 1))
    fi
done

echo ""

# ============================================================
# Summary
# ============================================================
TOTAL=$(cat "$TMPDIR_WORK/total")
VALID=$(cat "$TMPDIR_WORK/valid")
BROKEN=$(cat "$TMPDIR_WORK/broken")
DUPES=$(cat "$TMPDIR_WORK/dupes")
FORMAT=$(cat "$TMPDIR_WORK/format")

echo -e "${CYAN}=== Summary ===${NC}"
echo -e "Cross-ref links found: $TOTAL"
echo -e "  Valid:     ${GREEN}$VALID${NC}"
echo -e "  Broken:    ${RED}$BROKEN${NC}"
echo -e "  Duplicate: ${YELLOW}$DUPES${NC}"
echo -e "  Format:    ${YELLOW}$FORMAT${NC}"
echo -e ""
echo -e "Bidirectional (Group A): ${GREEN}$BIDIR_OK OK${NC} | ${YELLOW}$BIDIR_PARTIAL partial${NC} | ${RED}$BIDIR_MISSING missing${NC}"

ERRORS=$((BROKEN + DUPES))
WARNINGS=$((FORMAT + BIDIR_PARTIAL + BIDIR_MISSING))
if [[ $ERRORS -gt 0 ]]; then
    echo -e "\n${RED}❌ $ERRORS critical issue(s) found (broken/duplicate links).${NC}"
    exit 1
elif [[ $WARNINGS -gt 0 ]]; then
    echo -e "\n${YELLOW}⚠ No critical issues, but $WARNINGS warning(s) (format/bidirectional).${NC}"
    exit 0
else
    echo -e "\n${GREEN}✅ All checks passed!${NC}"
    exit 0
fi
