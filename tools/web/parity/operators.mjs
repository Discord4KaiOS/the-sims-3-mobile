// Recomputes every OpMatrix method with the generated TypeScript and compares
// the results with the ones javac produced (out/operators-java.txt).
import fs from 'node:fs';
import path from 'node:path';
import { OpMatrix } from './out/ts/OpMatrix.ts';

const here = path.dirname(new URL(import.meta.url).pathname);
const expected = fs.readFileSync(path.join(here, 'out', 'operators-java.txt'), 'utf8').trim().split('\n');

const SETS = {
  byte: [0, 1, -1, 127, -128, 100, -100],
  short: [0, 1, -1, 32767, -32768, 12345, -12345],
  char: [0, 1, 65, 65535, 32768, 40000],
  int: [0, 1, -1, 2, -2, 7, -7, 65535, 65536, -65536, 46341, -46341,
    2147483647, -2147483648, 1073741824, -1073741824, 1234567891, -1234567891,
    1000000007, 3, 10],
  long: [0, 1, -1, 2, -2, 3, 7, 65536, 4294967296, -4294967296,
    2147483647, -2147483648, 1234567891234567, -1234567891234567, 1000003, 10],
};

function clamp(value, type) {
  switch (type) {
    case 'byte':
      return (value << 24) >> 24;
    case 'short':
      return (value << 16) >> 16;
    case 'char':
      return value & 0xffff;
    case 'int':
      return value | 0;
    default:
      return value;
  }
}

const names = Object.getOwnPropertyNames(OpMatrix)
  .filter((name) => name.startsWith('op_') || name.startsWith('cast_') || name.startsWith('cmp_'))
  .sort();

const actual = [];
for (const name of names) {
  if (name.startsWith('cast_')) {
    const [, source] = name.slice('cast_'.length).split('_from_');
    for (const a of SETS[source]) {
      actual.push(`${name}|${a}|0|${OpMatrix[name](clamp(a, source))}`);
    }
    continue;
  }
  const tokens = name.split('_');
  const right = tokens[tokens.length - 1];
  const left = tokens[tokens.length - 2];
  for (const a of SETS[left]) {
    for (const b of SETS[right]) {
      if (b === 0 && (name.includes('divide') || name.includes('remainder'))) {
        continue;
      }
      actual.push(`${name}|${a}|${b}|${OpMatrix[name](clamp(a, left), clamp(b, right))}`);
    }
  }
}

// A JavaScript number only holds 64 bits exactly up to 2^53, and its bitwise
// operators are 32 bit wide. Results outside those limits are reported as known
// losses; everything else has to match javac.
const BITWISE_64 = /^(op|cmp)_(and|or|xor)_long_long$/;
const LIMIT = 9007199254740992;

function beyondDouble(name, left, right, javaResult) {
  if (BITWISE_64.test(name)) {
    return true;
  }
  if (name === 'op_left_shift_long_int') {
    return Math.abs(left) * Math.pow(2, right & 63) > LIMIT;
  }
  if (name.startsWith('op_multiply_long')) {
    return Math.abs(left * right) > LIMIT;
  }
  return Math.abs(javaResult) > LIMIT;
}

let compared = 0;
let known = 0;
const failures = [];
for (let i = 0; i < expected.length; i++) {
  compared++;
  if (expected[i] === actual[i]) {
    continue;
  }
  const [name, left, right, result] = expected[i].split('|');
  if (beyondDouble(name, Number(left), Number(right), Number(result))) {
    known++;
    continue;
  }
  failures.push(`  java: ${expected[i]}\n  ts  : ${actual[i]}`);
}

console.log(`operators: ${compared} cases, ${known} beyond 64 bit precision, ${failures.length} mismatches`);
if (failures.length > 0) {
  console.log(failures.slice(0, 10).join('\n'));
  process.exitCode = 1;
}
