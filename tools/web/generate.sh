#!/usr/bin/env bash
# Regenerates web/src/game from the Java sources.
#
#   1. translates the de-obfuscated Java to TypeScript (AST based, javac Trees)
#   2. applies the hand written adjustments the translation cannot express
#
# Requires a JDK (javac) on PATH; the TypeScript sources are the build output,
# they are checked in so the port can be built without a JDK.
set -euo pipefail

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
build="$root/build/tools"

mkdir -p "$build"
javac -d "$build" "$root/tools/web/JavaToTs.java"
java -cp "$build" JavaToTs "$root/src/main/java" "$root/web/src/game"
node "$root/tools/web/postprocess.mjs"

# A surviving marker means the translator emitted a 64 bit operation a JavaScript
# number cannot hold and no hand written rewrite replaced it.
if grep -rn "SIM_INEXACT" "$root/web/src/game"; then
  echo "error: inexact numeric emission was not rewritten (see markers above)" >&2
  exit 1
fi
