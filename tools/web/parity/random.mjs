// Compares the ported java.util.Random with the reference implementation in the
// JDK for a fixed seed (out/random-java.txt).
import fs from 'node:fs';
import path from 'node:path';
import { Random } from '../../../web/src/runtime/support.ts';

const here = path.dirname(new URL(import.meta.url).pathname);
const expected = fs.readFileSync(path.join(here, 'out', 'random-java.txt'), 'utf8').trim().split('\n');

const bounds = [1, 2, 3, 4, 5, 7, 8, 16, 17, 64, 99, 100, 128, 255, 256, 1000, 1024,
  65536, 1000000, 1073741824, 2147483647];

const actual = [];
for (const bound of bounds) {
  const random = new Random(42);
  const values = [];
  for (let i = 0; i < 200; i++) {
    values.push(`nextInt(${bound})=${random.nextInt(bound)}`);
  }
  actual.push(values.join(' '));
  const unbounded = new Random(7);
  const raw = [];
  for (let i = 0; i < 20; i++) {
    raw.push(`next(${bound})=${unbounded.nextInt()}`);
  }
  actual.push(raw.join(' '));
}

const failures = [];
for (let i = 0; i < expected.length; i++) {
  if (expected[i].trim().split(/\s+/).join(' ') !== actual[i]) {
    failures.push(`  line ${i}\n  java: ${expected[i]}\n  ts  : ${actual[i]}`);
  }
}

console.log(`random: ${expected.length} sequences, ${failures.length} mismatches`);
if (failures.length > 0) {
  console.log(failures.slice(0, 3).join('\n'));
  process.exitCode = 1;
}
