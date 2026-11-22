#!/usr/bin/env node
/**
 * Packages dist/ into application.zip, the archive KaiOS installs.
 *
 * The zip holds the contents of dist/ at its root, because KaiOS expects
 * manifest.webmanifest and index.html next to each other at the top level.
 * Everything is written with node's own zlib, so packaging needs no extra tool.
 *
 * Usage: node scripts/package.mjs
 */
import fs from 'node:fs';
import path from 'node:path';
import zlib from 'node:zlib';
import { fileURLToPath } from 'node:url';

const here = path.dirname(fileURLToPath(import.meta.url));
const webRoot = path.resolve(here, '..');
const distDir = path.join(webRoot, 'dist');
const target = path.join(webRoot, 'application.zip');

const CRC_TABLE = (() => {
  const table = new Int32Array(256);
  for (let i = 0; i < 256; i++) {
    let value = i;
    for (let bit = 0; bit < 8; bit++) {
      value = (value & 1) !== 0 ? 0xedb88320 ^ (value >>> 1) : value >>> 1;
    }
    table[i] = value;
  }
  return table;
})();

function crc32(buffer) {
  let crc = -1;
  for (let i = 0; i < buffer.length; i++) {
    crc = CRC_TABLE[(crc ^ buffer[i]) & 0xff] ^ (crc >>> 8);
  }
  return (crc ^ -1) >>> 0;
}

/** Every regular file below {@code dir}, as slash separated relative paths. */
function listFiles(dir, base = dir, files = []) {
  const entries = fs.readdirSync(dir, { withFileTypes: true });
  entries.sort((a, b) => (a.name < b.name ? -1 : a.name > b.name ? 1 : 0));
  for (const entry of entries) {
    const full = path.join(dir, entry.name);
    if (entry.isDirectory()) {
      listFiles(full, base, files);
    } else if (entry.isFile()) {
      files.push(path.relative(base, full).split(path.sep).join('/'));
    }
  }
  return files;
}

function dosTimestamp(date) {
  const time = ((date.getHours() & 31) << 11) | ((date.getMinutes() & 63) << 5) | ((date.getSeconds() >> 1) & 31);
  const day = (((date.getFullYear() - 1980) & 127) << 9) | (((date.getMonth() + 1) & 15) << 5) | (date.getDate() & 31);
  return { time, day };
}

if (!fs.existsSync(distDir)) {
  console.error(`nothing to package, ${distDir} does not exist (run: npm run build)`);
  process.exit(1);
}

const names = listFiles(distDir);
if (names.length === 0) {
  console.error(`nothing to package, ${distDir} is empty`);
  process.exit(1);
}

const body = [];
const central = [];
let offset = 0;
let uncompressed = 0;

for (const name of names) {
  const source = path.join(distDir, name);
  const data = fs.readFileSync(source);
  const compressed = zlib.deflateRawSync(data, { level: 9 });
  const deflate = compressed.length < data.length;
  const payload = deflate ? compressed : data;
  const method = deflate ? 8 : 0;
  const nameBytes = Buffer.from(name, 'utf8');
  const checksum = crc32(data);
  const { time, day } = dosTimestamp(fs.statSync(source).mtime);
  uncompressed += data.length;

  const local = Buffer.alloc(30);
  local.writeUInt32LE(0x04034b50, 0);
  local.writeUInt16LE(20, 4);
  local.writeUInt16LE(0x0800, 6); // file names are UTF-8
  local.writeUInt16LE(method, 8);
  local.writeUInt16LE(time, 10);
  local.writeUInt16LE(day, 12);
  local.writeUInt32LE(checksum, 14);
  local.writeUInt32LE(payload.length, 18);
  local.writeUInt32LE(data.length, 22);
  local.writeUInt16LE(nameBytes.length, 26);
  local.writeUInt16LE(0, 28);
  body.push(local, nameBytes, payload);

  const entry = Buffer.alloc(46);
  entry.writeUInt32LE(0x02014b50, 0);
  entry.writeUInt16LE(20, 4);
  entry.writeUInt16LE(20, 6);
  entry.writeUInt16LE(0x0800, 8);
  entry.writeUInt16LE(method, 10);
  entry.writeUInt16LE(time, 12);
  entry.writeUInt16LE(day, 14);
  entry.writeUInt32LE(checksum, 16);
  entry.writeUInt32LE(payload.length, 20);
  entry.writeUInt32LE(data.length, 24);
  entry.writeUInt16LE(nameBytes.length, 28);
  entry.writeUInt32LE(offset, 42);
  central.push(entry, nameBytes);

  offset += local.length + nameBytes.length + payload.length;
}

const directory = Buffer.concat(central);
const end = Buffer.alloc(22);
end.writeUInt32LE(0x06054b50, 0);
end.writeUInt16LE(names.length, 8);
end.writeUInt16LE(names.length, 10);
end.writeUInt32LE(directory.length, 12);
end.writeUInt32LE(offset, 16);

const archive = Buffer.concat([...body, directory, end]);
fs.writeFileSync(target, archive);

const kilobytes = (bytes) => `${(bytes / 1024).toFixed(1)} kB`;
console.log(
  `packaged ${names.length} files (${kilobytes(uncompressed)} -> ${kilobytes(archive.length)}) into ${path.relative(webRoot, target)}`,
);
