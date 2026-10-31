#!/usr/bin/env bash
# Merges the mapping files produced by parallel renaming workers and applies
# them to the real sources.
#
#   tools/merge-renames.sh /tmp/w-a/map.txt /tmp/w-b/map.txt ...
#
# Each mapping file is a superset of tools/rename-symbols.txt (the workers copy
# it as a base), so only the lines that are new are appended.
#
# Declaration renames (classes/fields/methods) are applied first, then the
# variable renames: a worker may key a variable by the method name its own
# batch introduces, and a few entries are chained (`var2 -> in`, then
# `in -> stream`), so the variable phase runs until it reaches a fixed point.
set -euo pipefail

root="$(cd "$(dirname "$0")/.." && pwd)"
mapping="$root/tools/rename-symbols.txt"
staging="$(mktemp)"
classes="$(mktemp -d)"
trap 'rm -f "$staging"; rm -rf "$classes"' EXIT

for file in "$@"; do
    if [ ! -f "$file" ]; then
        echo "missing mapping: $file" >&2
        exit 1
    fi
    while IFS= read -r line; do
        [ -z "$line" ] && continue
        case "$line" in \#*) continue ;; esac
        grep -qxF "$line" "$mapping" || grep -qxF "$line" "$staging" || printf '%s\n' "$line" >> "$staging"
    done < "$file"
done

if [ ! -s "$staging" ]; then
    echo "nothing new to merge"
    exit 0
fi

echo "new mapping entries: $(wc -l < "$staging")"
cat "$staging" >> "$mapping"

javac -d "$classes" "$root/tools/Rename.java"

run() {
    java -cp "$classes" Rename "$mapping" "$root/src/main/java" "$1" | grep -E '^applied|^WARN' || true
}

echo "declarations: $(run decl)"
for pass in 1 2 3 4 5 6; do
    out="$(run vars)"
    echo "variables (pass $pass): $out"
    case "$out" in
        *"applied 0 edits"*) break ;;
    esac
done
