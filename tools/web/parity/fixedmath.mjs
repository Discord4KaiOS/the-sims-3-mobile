// Compares the generated fixed point maths against javac over an exhaustive
// sweep of the sin/cos/sqrt ranges plus edge values (out/fixedmath-java.txt).
import fs from 'node:fs';
import path from 'node:path';
import { FixedMath as F } from '../../../web/src/game/FixedMath.ts';

const here = path.dirname(new URL(import.meta.url).pathname);
const expected = fs.readFileSync(path.join(here, 'out', 'fixedmath-java.txt'), 'utf8').trim().split('\n');

let seed = 123456789n;

function next() {
  seed = BigInt.asIntN(64, seed * 6364136223846793005n + 1442695040888963407n);
  return Number(BigInt.asIntN(32, BigInt.asUintN(64, seed) >> 33n));
}

const edge = [0, 1, -1, 2, -2, 65536, -65536, 65535, -65535, 32768, -32768, 32767,
  -32767, 205887, -205887, 20861, -20861, 102944, -102944, 411775, -411775, 65537,
  -65537, 131072, 46661, -46661, 4194304, -4194304, 8388608, 65200, 65800, 262143,
  1000000, -1000000, 16777216, 46341, 1000, 999, -999, 250000];

const actual = [];
for (const l of edge) {
  for (const r of edge) {
    actual.push(`mul|${l}|${r}|${F.mul(l, r)}`);
  }
}
for (const l of edge) {
  for (const r of edge) {
    if (r !== 0) {
      actual.push(`div|${l}|${r}|${F.div(l, r)}`);
    }
  }
}
for (const l of edge) {
  for (const r of edge) {
    actual.push(`length|${l}|${r}|${F.length(l, r)}`);
  }
}
for (let v = -70000; v <= 300000; v += 61) {
  actual.push(`sqrt|${v}|${F.sqrt(v)}`);
  actual.push(`sin|${v}|${F.sin(v)}`);
  actual.push(`cos|${v}|${F.cos(v)}`);
  actual.push(`sinShifted|${v}|${F.sinShifted(v)}`);
  actual.push(`wrapAngle|${v}|${F.wrapAngle(v)}`);
}
for (let i = 0; i < 40000; i++) {
  const v = next() % 900000;
  actual.push(`sqrt|${v}|${F.sqrt(v)}`);
  actual.push(`sin|${v}|${F.sin(v)}`);
  actual.push(`cos|${v}|${F.cos(v)}`);
}
for (let y = -400; y <= 400; y += 7) {
  for (let x = -400; x <= 400; x += 7) {
    actual.push(`atan2|${y}|${x}|${F.atan2(y, x)}`);
  }
}
for (const y of edge) {
  for (const x of edge) {
    actual.push(`atan2|${y}|${x}|${F.atan2(y, x)}`);
    actual.push(`smoothStep|${x}|${y}|${F.smoothStep(x, y, Math.trunc((x + y) / 2))}`);
  }
}
for (let i = 0; i < 20000; i++) {
  const edge0 = next() % 200000 - 100000;
  const edge1 = edge0 + next() % 50000;
  const x = next() % 250000 - 100000;
  actual.push(`smoothStep|${edge0}|${edge1}|${x}|${F.smoothStep(edge0, edge1, x)}`);
}

const failures = [];
for (let i = 0; i < expected.length; i++) {
  if (expected[i] !== actual[i]) {
    failures.push(`  java: ${expected[i]}\n  ts  : ${actual[i]}`);
  }
}

console.log(`fixedmath: ${expected.length} cases, ${failures.length} mismatches`);
if (failures.length > 0) {
  console.log(failures.slice(0, 10).join('\n'));
  process.exitCode = 1;
}
