#!/usr/bin/env node
/**
 * Copies the game resources into the web build and pre-renders audio.
 *
 * Images and data files are copied verbatim; the PNGs are decoded by the browser,
 * so a manifest of them is written for the runtime to preload.
 * MIDI, AMR and WAV assets are rendered to small Ogg Vorbis files so the browser
 * does not have to decode them, using timidity for MIDI and ffmpeg for the rest.
 *
 * Usage: node scripts/copy-assets.mjs [--force]
 */
import { execFileSync } from 'node:child_process';
import fs from 'node:fs';
import os from 'node:os';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const here = path.dirname(fileURLToPath(import.meta.url));
const webRoot = path.resolve(here, '..');
const sourceDir = path.resolve(webRoot, '..', 'src', 'main', 'resources');
const outputDir = path.resolve(webRoot, 'public', 'game');
const force = process.argv.includes('--force');

const OGG_QUALITY = '0';
const SAMPLE_RATE = '22050';

function isMidi(bytes) {
  return bytes.length >= 4 && bytes[0] === 0x4d && bytes[1] === 0x54 && bytes[2] === 0x68 && bytes[3] === 0x64;
}

function isAmr(bytes) {
  return (
    bytes.length >= 6 &&
    bytes[0] === 0x23 &&
    bytes[1] === 0x21 &&
    bytes[2] === 0x41 &&
    bytes[3] === 0x4d &&
    bytes[4] === 0x52
  );
}

function isPng(bytes) {
  return bytes.length >= 8 && bytes[0] === 0x89 && bytes[1] === 0x50 && bytes[2] === 0x4e && bytes[3] === 0x47;
}

function isWav(bytes) {
  return (
    bytes.length >= 12 && bytes[0] === 0x52 && bytes[1] === 0x49 && bytes[2] === 0x46 && bytes[3] === 0x46
  );
}

function isFresh(source, target) {
  if (!fs.existsSync(target)) {
    return false;
  }
  return fs.statSync(target).mtimeMs >= fs.statSync(source).mtimeMs;
}

function encodeOgg(input, output) {
  execFileSync(
    'ffmpeg',
    [
      '-hide_banner',
      '-loglevel',
      'error',
      '-y',
      '-i',
      input,
      '-ac',
      '1',
      '-ar',
      SAMPLE_RATE,
      '-c:a',
      'libvorbis',
      '-q:a',
      OGG_QUALITY,
      output,
    ],
    { stdio: 'inherit' },
  );
}

function renderMidi(source, output, scratch) {
  const wav = path.join(scratch, `${path.basename(source)}.wav`);
  execFileSync('timidity', ['-Ow', '-o', wav, source], { stdio: 'ignore' });
  try {
    encodeOgg(wav, output);
  } finally {
    fs.rmSync(wav, { force: true });
  }
}

function main() {
  if (!fs.existsSync(sourceDir)) {
    console.error(`resource directory not found: ${sourceDir}`);
    process.exit(1);
  }
  fs.mkdirSync(outputDir, { recursive: true });
  const scratch = fs.mkdtempSync(path.join(os.tmpdir(), 'sims3-audio-'));

  let copied = 0;
  let rendered = 0;
  let skipped = 0;
  const pngs = [];

  for (const name of fs.readdirSync(sourceDir).sort()) {
    if (name.startsWith('.')) {
      continue;
    }
    const source = path.join(sourceDir, name);
    if (!fs.statSync(source).isFile()) {
      continue;
    }
    const header = fs.readFileSync(source).subarray(0, 16);
    const audio = isMidi(header) || isAmr(header) || isWav(header);
    if (isPng(header)) {
      pngs.push(name);
    }
    const target = path.join(outputDir, audio ? `${name}.ogg` : name);
    if (!force && isFresh(source, target)) {
      skipped++;
      continue;
    }
    if (audio) {
      if (isMidi(header)) {
        renderMidi(source, target, scratch);
      } else {
        encodeOgg(source, target);
      }
      rendered++;
    } else {
      fs.copyFileSync(source, target);
      copied++;
    }
  }

  fs.rmSync(scratch, { recursive: true, force: true });

  fs.writeFileSync(path.join(outputDir, 'png.json'), `${JSON.stringify(pngs)}\n`);
  console.log(
    `assets: ${copied} copied, ${rendered} rendered to ogg, ${skipped} up to date, ` +
      `${pngs.length} png listed -> ${outputDir}`,
  );
}

main();
