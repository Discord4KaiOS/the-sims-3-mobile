#!/usr/bin/env bash
# Checks that the generated TypeScript computes the same numbers as javac.
#
#   operators  every arithmetic operator / operand type combination the
#              translator emits, translated from a synthetic class
#   fixedmath  the 16.16 fixed point maths the game draws with
#   random     the ported java.util.Random against the JDK implementation
#
# Requires a JDK and node; no gradle and no network.
set -euo pipefail

here="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
root="$(cd "$here/../../.." && pwd)"
out="$here/out"

rm -rf "$out"
mkdir -p "$out/classes" "$out/ts"

javac -d "$out/classes" "$root/tools/web/JavaToTs.java"

python3 "$here/gen_matrix.py"
java -cp "$out/classes" JavaToTs "$out/src" "$out/ts" > /dev/null
javac -d "$out/classes" "$out/src/com/ea/sims3/OpMatrix.java" "$here/Operators.java"
java -cp "$out/classes" Operators > "$out/operators-java.txt"
node "$here/operators.mjs"

javac -d "$out/classes" "$root/src/main/java/com/ea/sims3/FixedMath.java" "$here/FixedMathProbe.java"
java -cp "$out/classes" FixedMathProbe > "$out/fixedmath-java.txt"
node "$here/fixedmath.mjs"

javac -d "$out/classes" "$here/RandomProbe.java"
java -cp "$out/classes" RandomProbe > "$out/random-java.txt"
node "$here/random.mjs"
