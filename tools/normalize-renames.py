#!/usr/bin/env python3
"""Rewrites variable-rename keys to use the final method names.

A variable entry is keyed by ``<class> <method(sig)> <varName>``. Workers that
renamed a method and its variables in the same batch sometimes keyed the
variables by the method's old name and sometimes by its new one. Because
declarations are applied before variables, only the new name is valid at that
point, so every key is rewritten here to the name the method ends up with.

    tools/normalize-renames.py tools/rename-symbols.txt

The file is rewritten in place; the operation is idempotent.
"""
import sys

METHODS = {}
LINES = []

for line in open(sys.argv[1], encoding="utf-8"):
    stripped = line.strip()
    if stripped and not stripped.startswith("#"):
        parts = stripped.split()
        if parts[0] == "M":
            METHODS[(parts[1], parts[2])] = parts[3]
    LINES.append(line)


def final_signature(owner, signature):
    seen = set()
    while True:
        name, _, rest = signature.partition("(")
        target = METHODS.get((owner, signature))
        if target is None or target == name or (owner, signature) in seen:
            return signature
        seen.add((owner, signature))
        signature = target + "(" + rest


changed = 0
for index, line in enumerate(LINES):
    stripped = line.strip()
    if not stripped.startswith("V "):
        continue
    parts = stripped.split()
    if len(parts) != 5:
        continue
    new = final_signature(parts[1], parts[2])
    if new != parts[2]:
        LINES[index] = " ".join(["V", parts[1], new, parts[3], parts[4]]) + "\n"
        changed += 1

open(sys.argv[1], "w", encoding="utf-8").writelines(LINES)
print("rewrote %d variable keys" % changed)
