#!/usr/bin/env python3
"""Generates OpMatrix.java covering every arithmetic operator/operand-type
combination the translator has to emit, so the emitted TypeScript can be
compared against real javac results."""
import os

TYPES = {
    "int": "int",
    "long": "long",
    "byte": "byte",
    "short": "short",
    "char": "char",
}

COMBOS = [
    # (op, javaOp, leftType, rightType, returnType)
    ("PLUS", "+", "int", "int", "int"),
    ("PLUS", "+", "long", "long", "long"),
    ("PLUS", "+", "int", "long", "long"),
    ("PLUS", "+", "long", "int", "long"),
    ("PLUS", "+", "byte", "int", "int"),
    ("PLUS", "+", "short", "int", "int"),
    ("PLUS", "+", "int", "byte", "int"),
    ("PLUS", "+", "int", "short", "int"),
    ("PLUS", "+", "byte", "byte", "int"),
    ("PLUS", "+", "short", "short", "int"),
    ("PLUS", "+", "char", "int", "int"),
    ("PLUS", "+", "byte", "short", "int"),
    ("MINUS", "-", "int", "int", "int"),
    ("MINUS", "-", "long", "long", "long"),
    ("MINUS", "-", "int", "long", "long"),
    ("MINUS", "-", "byte", "int", "int"),
    ("MINUS", "-", "int", "byte", "int"),
    ("MINUS", "-", "short", "int", "int"),
    ("MINUS", "-", "int", "short", "int"),
    ("MINUS", "-", "short", "short", "int"),
    ("MINUS", "-", "byte", "byte", "int"),
    ("MULTIPLY", "*", "int", "int", "int"),
    ("MULTIPLY", "*", "long", "long", "long"),
    ("MULTIPLY", "*", "long", "int", "long"),
    ("MULTIPLY", "*", "byte", "int", "int"),
    ("MULTIPLY", "*", "int", "byte", "int"),
    ("MULTIPLY", "*", "short", "int", "int"),
    ("MULTIPLY", "*", "int", "short", "int"),
    ("MULTIPLY", "*", "byte", "short", "int"),
    ("MULTIPLY", "*", "short", "short", "int"),
    ("DIVIDE", "/", "int", "int", "int"),
    ("DIVIDE", "/", "long", "long", "long"),
    ("DIVIDE", "/", "long", "int", "long"),
    ("DIVIDE", "/", "int", "byte", "int"),
    ("DIVIDE", "/", "short", "int", "int"),
    ("DIVIDE", "/", "int", "short", "int"),
    ("DIVIDE", "/", "byte", "byte", "int"),
    ("REMAINDER", "%", "int", "int", "int"),
    ("REMAINDER", "%", "long", "long", "long"),
    ("REMAINDER", "%", "int", "short", "int"),
    ("REMAINDER", "%", "short", "int", "int"),
    ("LEFT_SHIFT", "<<", "int", "int", "int"),
    ("LEFT_SHIFT", "<<", "long", "int", "long"),
    ("LEFT_SHIFT", "<<", "byte", "int", "int"),
    ("LEFT_SHIFT", "<<", "short", "int", "int"),
    ("RIGHT_SHIFT", ">>", "int", "int", "int"),
    ("RIGHT_SHIFT", ">>", "long", "int", "long"),
    ("RIGHT_SHIFT", ">>", "byte", "int", "int"),
    ("RIGHT_SHIFT", ">>", "short", "int", "int"),
    ("UNSIGNED_RIGHT_SHIFT", ">>>", "int", "int", "int"),
    ("AND", "&", "int", "int", "int"),
    ("AND", "&", "byte", "int", "int"),
    ("AND", "&", "short", "char", "int"),
    ("AND", "&", "long", "long", "long"),
    ("OR", "|", "int", "int", "int"),
    ("OR", "|", "long", "long", "long"),
    ("XOR", "^", "int", "int", "int"),
    ("XOR", "^", "long", "long", "long"),
]

CASTS = [
    ("byte", "int"),
    ("byte", "short"),
    ("byte", "char"),
    ("byte", "long"),
    ("short", "int"),
    ("short", "char"),
    ("short", "short"),
    ("char", "int"),
    ("char", "short"),
    ("int", "long"),
    ("int", "int"),
    ("int", "byte"),
    ("long", "int"),
]

COMPOUND = [
    ("PLUS", "+=", "int", "int"),
    ("PLUS", "+=", "byte", "int"),
    ("PLUS", "+=", "short", "short"),
    ("PLUS", "+=", "long", "int"),
    ("PLUS", "+=", "int", "byte"),
    ("MINUS", "-=", "int", "int"),
    ("MULTIPLY", "*=", "int", "int"),
    ("DIVIDE", "/=", "int", "int"),
    ("REMAINDER", "%=", "int", "int"),
    ("LEFT_SHIFT", "<<=", "int", "int"),
    ("RIGHT_SHIFT", ">>=", "int", "int"),
    ("UNSIGNED_RIGHT_SHIFT", ">>>=", "int", "int"),
    ("AND", "&=", "int", "int"),
    ("AND", "&=", "short", "int"),
    ("OR", "|=", "int", "int"),
    ("OR", "|=", "long", "long"),
    ("OR", "|=", "byte", "int"),
    ("XOR", "^=", "int", "int"),
]


def main():
    lines = ["package com.ea.sims3;", "", "public final class OpMatrix {"]
    for name, op, lt, rt, result in COMBOS:
        lines.append(
            f"   public static {result} op_{name.lower()}_{lt}_{rt}({lt} a, {rt} b) {{ return a {op} b; }}")
    for target, source in CASTS:
        lines.append(
            f"   public static long cast_{target}_from_{source}({source} a) {{ return ({target})a; }}")
    for name, op, target, right in COMPOUND:
        lines.append(
            f"   public static {target} cmp_{name.lower()}_{target}_{right}({target} a, {right} b) {{ a {op} b; return a; }}")
    lines.append("}")
    path = os.path.join(os.path.dirname(os.path.abspath(__file__)), "out", "src", "com", "ea", "sims3",
                        "OpMatrix.java")
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as handle:
        handle.write("\n".join(lines) + "\n")
    print(f"wrote {path} ({len(COMBOS)} binary, {len(CASTS)} cast, {len(COMPOUND)} compound)")


if __name__ == "__main__":
    main()
