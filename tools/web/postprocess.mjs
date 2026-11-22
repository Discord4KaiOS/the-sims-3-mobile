#!/usr/bin/env node
/**
 * Applies the hand written adjustments to the generated TypeScript.
 *
 * The translator keeps Java semantics, but a few things have no direct
 * JavaScript equivalent and are rewritten here:
 *
 *  - the 64 bit progress flags in GameCanvas (JavaScript numbers lose bits
 *    above 2^53 and bitwise operators only handle 32 bits),
 *  - the loading worker threads, which become cooperative pumps because a
 *    browser has no blocking wait(),
 *  - the pre-rendered Ogg assets, which replace the MIDI/AMR decoders.
 *
 * Every replacement is exact and checked, so a change in the generated output
 * fails loudly instead of silently producing a stale patched file.
 */
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const here = path.dirname(fileURLToPath(import.meta.url));
const gameDir = path.resolve(here, '..', '..', 'web', 'src', 'game');

function patch(file, replacements) {
  const target = path.join(gameDir, file);
  let text = fs.readFileSync(target, 'utf8');
  for (const [from, to] of replacements) {
    if (!text.includes(from)) {
      throw new Error(`${file}: patch anchor not found:\n${from.slice(0, 160)}`);
    }
    text = text.split(from).join(to);
  }
  fs.writeFileSync(target, text);
  console.log(`patched ${file}`);
}

/**
 * Replaces a whole method, located by its signature, so the generated body can
 * change without invalidating the patch.
 */
function replaceMethod(file, signature, replacement) {
  const target = path.join(gameDir, file);
  const text = fs.readFileSync(target, 'utf8');
  const start = text.indexOf(signature);
  if (start < 0) {
    throw new Error(`${file}: method not found:\n${signature}`);
  }
  const lineStart = text.lastIndexOf('\n', start) + 1;
  let depth = 0;
  let index = text.indexOf('{', start);
  for (; index < text.length; index++) {
    if (text[index] === '{') {
      depth++;
    } else if (text[index] === '}') {
      depth--;
      if (depth === 0) {
        index++;
        break;
      }
    }
  }
  if (depth !== 0) {
    throw new Error(`${file}: unbalanced braces for ${signature}`);
  }
  fs.writeFileSync(target, text.slice(0, lineStart) + replacement + text.slice(index));
  console.log(`patched ${file}`);
}

// --------------------------------------------------------------- 64 bit flags

const FLAG_FIELDS = ['flagsLow', 'flagsHigh', 'visitedFlagsLow', 'visitedFlagsHigh',
  'newFlagsLow', 'newFlagsHigh', 'newVisitedFlagsLow', 'newVisitedFlagsHigh'];

const flagFieldPatches = FLAG_FIELDS.map((name) => [
  `\t${name}: number = 0;`,
  `\t${name}: bigint = 0n;`,
]);

const flagResetPatches = FLAG_FIELDS.map((name) => [
  `\t\tthis.${name} = 0;`,
  `\t\tthis.${name} = 0n;`,
]);

// Only the four persisted masks are read/written; the pending ones never are.
const PERSISTED_FLAGS = ['flagsLow', 'flagsHigh', 'visitedFlagsLow', 'visitedFlagsHigh'];

const flagIoPatches = [
  ...PERSISTED_FLAGS.map((name) => [
    `\t\t\t\tthis.${name} = input.readLong();`,
    `\t\t\t\tthis.${name} = input.readLong64();`,
  ]),
  ...PERSISTED_FLAGS.map((name) => [
    `\t\t\tout.writeLong(this.${name});`,
    `\t\t\tout.writeLong64(this.${name});`,
  ]),
];

const flagMethodStart = `\thasFlag(flag: number): boolean {`;
const flagMethodEnd = `\tupdateLoadingTip(deltaMs: number): void {`;

const flagMethods = `\t/** Progress bitset: Java stores 64 flags in a long, so this uses BigInt. */
\thasFlag(flag: number): boolean {
\t\tconst bit = BigInt.asUintN(64, 1n << BigInt(flag < 64 ? flag : flag - 64));
\t\tconst bits = flag < 64 ? (this.flagsLow | this.newFlagsLow) : (this.flagsHigh | this.newFlagsHigh);
\t\treturn (bits & bit) !== 0n;
\t}
\taddFlag(flag: number): void {
\t\tconst bit = BigInt.asUintN(64, 1n << BigInt(flag < 64 ? flag : flag - 64));
\t\tif (flag < 64) {
\t\t\tthis.newFlagsLow |= bit;
\t\t} else {
\t\t\tthis.newFlagsHigh |= bit;
\t\t}
\t}
\tflagCount(): number {
\t\treturn countBits(this.flagsLow | this.newFlagsLow) + countBits(this.flagsHigh | this.newFlagsHigh);
\t}
\thasVisitedFlag(flag: number): boolean {
\t\tconst bit = BigInt.asUintN(64, 1n << BigInt(flag < 64 ? flag : flag - 64));
\t\tconst bits = flag < 64
\t\t\t? (this.visitedFlagsLow | this.newVisitedFlagsLow)
\t\t\t: (this.visitedFlagsHigh | this.newVisitedFlagsHigh);
\t\treturn (bits & bit) !== 0n;
\t}
\taddVisitedFlag(flag: number): void {
\t\tthis.addFlag(flag);
\t\tconst bit = BigInt.asUintN(64, 1n << BigInt(flag < 64 ? flag : flag - 64));
\t\tif (flag < 64) {
\t\t\tthis.newVisitedFlagsLow |= bit;
\t\t} else {
\t\t\tthis.newVisitedFlagsHigh |= bit;
\t\t}
\t}
\tvisitedFlagCount(): number {
\t\treturn countBits(this.visitedFlagsLow | this.newVisitedFlagsLow)
\t\t\t+ countBits(this.visitedFlagsHigh | this.newVisitedFlagsHigh);
\t}
`;

function patchGameCanvas() {
  const target = path.join(gameDir, 'GameCanvas.ts');
  let text = fs.readFileSync(target, 'utf8');
  const patches = [...flagFieldPatches, ...flagResetPatches, ...flagIoPatches];
  for (const [from, to] of patches) {
    if (!text.includes(from)) {
      throw new Error(`GameCanvas.ts: patch anchor not found:\n${from}`);
    }
    text = text.split(from).join(to);
  }
  const start = text.indexOf(flagMethodStart);
  const end = text.indexOf(flagMethodEnd);
  if (start < 0 || end < 0 || end < start) {
    throw new Error('GameCanvas.ts: flag method block not found');
  }
  text = text.slice(0, start) + flagMethods + text.slice(end);
  text = text.replace("import { Canvas, Random, Timer, Player, Graphics, Image, IOException, InputStream, loadResource, ByteArrayInputStream, arraycopy, gc, InterruptedException, RecordStore, DataInputStream, RecordStoreNotFoundException, ByteArrayOutputStream, DataOutputStream, MIDlet } from '../runtime';",
    "import { Canvas, Random, Timer, Player, Graphics, Image, IOException, InputStream, loadResource, ByteArrayInputStream, arraycopy, gc, countBits, InterruptedException, RecordStore, DataInputStream, RecordStoreNotFoundException, ByteArrayOutputStream, DataOutputStream, MIDlet } from '../runtime';");
  fs.writeFileSync(target, text);
  console.log('patched GameCanvas.ts');
}

// ------------------------------------------------------------ loading workers

patch('TitleScreen.ts', [[
`	startLoading(loadingTipDelta: number): void {
		this.canvas.updateLoadingTip(loadingTipDelta);
		if (this.loaderThread === null) {
			this.loaderThread = new Thread(this);
			this.loaderState = 0;
			this.loaderThread.start();
		}
		else {
			this.loaderState = 0;
		}
	}
	run(): void {
		while (this.loaderState !== 2) {
			if (this.loaderState !== 0) {
				try {
					{
						this.wait(1000);
					}
				}
				catch (ignored) {
					if (ignored instanceof InterruptedException) {
					}
					else {
						throw ignored;
					}
				}
			}
			else {
				this.loaderState = 1;
				try {
					this.loadNextStep();
				}
				catch (ignored2) {
				}
			}
		}
	}`,
`	/**
	 * The Java build runs this on a worker thread that waits for key presses; the
	 * port pumps one step per press from the event loop instead.
	 */
	startLoading(loadingTipDelta: number): void {
		this.canvas.updateLoadingTip(loadingTipDelta);
		this.loaderState = 0;
		if (!this.loaderScheduled) {
			this.loaderScheduled = true;
			setTimeout((): void => {
				this.loaderScheduled = false;
				if (this.loaderState === 0) {
					this.loaderState = 1;
					try {
						this.loadNextStep();
					}
					catch (ignored) {
					}
				}
			}, 0);
		}
	}`,
]]);

patch('GameScreen.ts', [[
`	startLoading(keyCode: number): void {
		this.canvas.updateLoadingTip(keyCode);
		if (this.loaderThread === null) {
			this.loaderThread = new Thread(this);
			this.threadState = 0;
			this.loaderThread.start();
		}
		else {
			this.threadState = 0;
		}
	}
	run(): void {
		while (this.threadState !== 2) {
			if (this.threadState !== 0) {
				try {
					{
						this.wait(1000);
					}
				}
				catch (interrupted) {
					if (interrupted instanceof InterruptedException) {
					}
					else {
						throw interrupted;
					}
				}
			}
			else {
				this.threadState = 1;
				try {
					this.runLoadingSteps();
				}
				catch (failure) {
				}
			}
		}
	}`,
`	/**
	 * The Java build runs this on a worker thread that waits for key presses; the
	 * port pumps one step per press from the event loop instead.
	 */
	startLoading(keyCode: number): void {
		this.canvas.updateLoadingTip(keyCode);
		this.threadState = 0;
		if (!this.loaderScheduled) {
			this.loaderScheduled = true;
			setTimeout((): void => {
				this.loaderScheduled = false;
				if (this.threadState === 0) {
					this.threadState = 1;
					try {
						this.runLoadingSteps();
					}
					catch (failure) {
					}
				}
			}, 0);
		}
	}`,
]]);

patch('GameScreen.ts', [[
  `\tloaderThread: Thread = null!;`,
  `\tloaderThread: Thread = null!;\n\tloaderScheduled: boolean = false;`,
]]);

patch('TitleScreen.ts', [[
  `\tloaderThread: Thread = null!;`,
  `\tloaderThread: Thread = null!;\n\tloaderScheduled: boolean = false;`,
]]);

// ------------------------------------------------------------- pre-rendered audio

patch('SdkSound.ts', [[
`	createPlayer(data: Int8Array): Player {
		let format: number = data[0];
		let contentType: string = null;
		switch (format) {
			case 35:
				contentType = "audio/amr";
				break;
			case 77:
				contentType = "audio/midi";
				break;
			case 82:
				if (this.isWavFormat) {
					contentType = "audio/x-wav";
				}
				else {
					contentType = "audio/wav";
				}
		}`,
`	createPlayer(data: Int8Array): Player {
		// The build renders MIDI/AMR/WAV assets to Ogg Vorbis, so the magic bytes
		// the Java player sniffs for are replaced by the Ogg encapsulation header.
		let contentType: string = null;
		if (data.length >= 4 && data[0] === 79 && data[1] === 103 && data[2] === 103 && data[3] === 83) {
			contentType = "audio/ogg";
		}`,
]]);

patch('ResourceLoader.ts', [[
`import { Image, InputStream, loadResource, ByteArrayInputStream, DataInputStream } from '../runtime';`,
`import { Image, InputStream, loadResource, ByteArrayInputStream, DataInputStream } from '../runtime';`,
]]);

// The Java build replaces the PLTE chunk and decodes the edited PNG again. The
// browser decodes each image once, so the same substitution is applied to the
// decoded pixels; the palette/bit depth guard is kept so a non palette image is
// still rejected exactly like the Java code rejects it.
patch('ResourceLoader.ts', [[
`import { Image, InputStream, loadResource, ByteArrayInputStream, DataInputStream } from '../runtime';`,
`import { Image, pngHeaderOf, pngPaletteOf, recolourImage, InputStream, loadResource, ByteArrayInputStream, DataInputStream } from '../runtime';`,
]]);

replaceMethod('ResourceLoader.ts', 'loadImageRecolored(index: number, fromColors: Int32Array, toColors: Int32Array): Image {', `	loadImageRecolored(index: number, fromColors: Int32Array, toColors: Int32Array): Image {
		let name: string = ResourceLoader.getName(index);
		let header = pngHeaderOf(name);
		let palette = pngPaletteOf(name);
		if (header === null || palette === null || (header.bitDepth !== 4 && header.bitDepth !== 8)) {
			return null;
		}
		let image: Image = ResourceLoader.loadImage(index);
		return image === null ? null : recolourImage(image, fromColors, toColors, palette);
	}
`);

patch('ResourceLoader.ts', [[
`	loadSound(index: number): Int8Array {
		return this.loadBytes(SoundIndex.SOUND_IDS[index]);
	}`,
`	/** Sound assets are pre-rendered to Ogg Vorbis by scripts/copy-assets.mjs. */
	loadSound(index: number): Int8Array {
		return loadResource(ResourceLoader.getName(SoundIndex.SOUND_IDS[index]) + ".ogg");
	}`,
]]);

// ------------------------------------------------------- out-of-range string ids
//
// The original game asks for string id -1 while a text slot is still empty and
// relies on the resulting ArrayIndexOutOfBoundsException to skip the draw. A
// typed array silently returns undefined instead, which would paint a row of
// NUL glyphs, so the bounds check is restored explicitly.
patch('SdkText.ts', [[
`\t\tlet chunkOffset: number = ((stringId - (((cumulative - SdkText.chunkStringCount[chunkId]) | 0))) | 0);
\t\tlet groupId: number = SdkText.chunkGroupOfId[chunkId];`,
`\t\tlet chunkOffset: number = ((stringId - (((cumulative - SdkText.chunkStringCount[chunkId]) | 0))) | 0);
\t\tlet groupId: number = SdkText.chunkGroupOfId[chunkId];
\t\tif (chunkOffset < 0 || chunkOffset + 1 >= SdkText.chunkStringOffsets[groupId].length) {
\t\t\tthrow new IndexOutOfBoundsException("string id " + stringId + " is out of range");
\t\t}`,
]]);

patch('SdkText.ts', [[
`import { Graphics, StringBuffer, MIDlet, Image, InputStream, IOException, IllegalStateException, Font, chr, arraycopy, loadResource, ByteArrayInputStream, bytesToString, Alert, AlertType, Display, getProperty } from '../runtime';`,
`import { Graphics, StringBuffer, MIDlet, Image, InputStream, IOException, IllegalStateException, Font, chr, arraycopy, loadResource, ByteArrayInputStream, bytesToString, Alert, AlertType, Display, getProperty, IndexOutOfBoundsException } from '../runtime';`,
]]);

// ------------------------------------------------- Java array bounds semantics
//
// Writing past the end of a byte[] throws in Java and the game relies on the
// exception to skip a draw or a copy; typed arrays silently ignore the write, so
// the checks are restored here.
patch('SdkString.ts', [[
`\t\tlet byteCount: number = targetEncoding === 0 ? count << 1 : count;`,
`\t\tlet byteCount: number = targetEncoding === 0 ? count << 1 : count;
\t\tif (targetOffset + byteCount > target.length || sourceOffset + count > source.length) {
\t\t\tthrow new IndexOutOfBoundsException("SDKString buffer overflow");
\t\t}`,
]]);

patch('SdkString.ts', [[
`import { arraycopy, chr, StringBuffer } from '../runtime';`,
`import { arraycopy, chr, StringBuffer, IndexOutOfBoundsException } from '../runtime';`,
]]);

patchGameCanvas();

// ------------------------------------------------------------- trait picking
//
// The original game ignores a trait once five are chosen: `addKnown` only writes
// into an empty slot, so tapping another trait does nothing at all and the list
// looks dead. The port swaps the oldest trait instead, so the choice the player
// just made always takes effect.
patch('Neighborhood.ts', [[
`\t\t\tlet slot: number = 0;
\t\t\tif ((slot = this.simKnownIndex(0, -1)) !== -1) {
\t\t\t\tthis.simKnownIds[0][slot] = (id << 24 >> 24);
\t\t\t}`,
`\t\t\tlet slot: number = 0;
\t\t\tif ((slot = this.simKnownIndex(0, -1)) === -1) {
\t\t\t\tslot = 0;
\t\t\t}
\t\t\tthis.simKnownIds[0][slot] = (id << 24 >> 24);`,
]]);

// ------------------------------------------------------ reviewed 64 bit ors
//
// The translator flags every `long & | ^` it emits because a JavaScript number
// cannot hold 64 bits. These four are the persisted flag masks, which the step
// above turned into BigInt, so the operator really is 64 bit here.
patch('GameCanvas.ts', [
  ['flagsLow', 'newFlagsLow'],
  ['flagsHigh', 'newFlagsHigh'],
  ['visitedFlagsLow', 'newVisitedFlagsLow'],
  ['visitedFlagsHigh', 'newVisitedFlagsHigh'],
].map(([left, right]) => [
  `(this.${left} | this.${right}) /* SIM_INEXACT long bitwise */`,
  `(this.${left} | this.${right})`,
]));
console.log('done');
