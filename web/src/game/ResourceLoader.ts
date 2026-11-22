// Generated from ResourceLoader.java by tools/web/JavaToTs.java; keep this file in sync.
import { Image, pngHeaderOf, pngPaletteOf, recolourImage, InputStream, loadResource, ByteArrayInputStream, DataInputStream } from '../runtime';
import { Resources } from './Resources';
import { SoundIndex } from './SoundIndex';

export class ResourceLoader {
	crcTable: Int32Array = null!;
	static getSize(index: number): number {
		return Resources.resourceSizes[index];
	}
	static getName(index: number): string {
		return Resources.resourcePaths[index];
	}
	static loadImage(index: number): Image {
		try {
			return Image.createImage(ResourceLoader.getName(index));
		}
		catch (ignored) {
			return null;
		}
	}
	openStream(index: number): InputStream {
		return new ByteArrayInputStream(loadResource(ResourceLoader.getName(index)));
	}
	/** Sound assets are pre-rendered to Ogg Vorbis by scripts/copy-assets.mjs. */
	loadSound(index: number): Int8Array {
		return loadResource(ResourceLoader.getName(SoundIndex.SOUND_IDS[index]) + ".ogg");
	}
	loadBytes(index: number): Int8Array {
		let size: number = 0;
		let data: Int8Array = new Int8Array(size = Resources.resourceSizes[index]);
		try {
			let input: DataInputStream = new DataInputStream(this.openStream(index));
			input.readFully(data);
			return data;
		}
		catch (exception) {
			return null;
		}
	}
	static ubyte(value: number): number {
		return 255 & value;
	}
	static readInt(data: Int8Array, offset: number): number {
		let byte0: number = (data[((offset + 0) | 0)] & 255) << 24;
		let byte1: number = (data[((offset + 1) | 0)] & 255) << 16;
		let byte2: number = (data[((offset + 2) | 0)] & 255) << 8;
		let byte3: number = data[((offset + 3) | 0)] & 255;
		return byte0 | byte1 | byte2 | byte3;
	}
	initCrcTable(): void {
		this.crcTable = new Int32Array(256);
		for (let index: number = 0; index <= 255; index++) {
			let crc: number = index;
			for (let bit: number = 8; bit > 0; bit--) {
				if ((crc & 1) === 1) {
					crc = ((crc >>> 1) | 0) ^ -306674912;
				}
				else {
					crc = ((crc >>> 1) | 0);
				}
			}
			this.crcTable[index] = crc;
		}
	}
	crc32(data: Int8Array, offset: number, length: number): number {
		let crc: number = -1;
		for (let byteIndex: number = offset; byteIndex < ((offset + length) | 0); byteIndex++) {
			let index: number = 255 & data[byteIndex] ^ crc & 255;
			crc = ((crc >>> 8) | 0) ^ this.crcTable[index];
		}
		return ~crc;
	}
	static replacePalette(data: Int8Array, offset: number, length: number, fromColors: Int32Array, toColors: Int32Array): void {
		let count: number = fromColors.length;
		let pixelCount: number = ((length / 3) | 0);
		for (let colorIndex: number = 0; colorIndex < count; colorIndex++) {
			if (fromColors[colorIndex] !== 16711935) {
				let fromColor: number = 0;
				let fromR: number = ((fromColor = fromColors[colorIndex]) & 16711680) >> 16;
				let fromG: number = (fromColor & 65280) >> 8;
				let fromB: number = (fromColor & 255) >> 0;
				let pos: number = offset;
				for (let pixel: number = 0; pixel < pixelCount; pixel++) {
					let red: number = data[((pos + 0) | 0)] & 255;
					let green: number = data[((pos + 1) | 0)] & 255;
					let blue: number = data[((pos + 2) | 0)] & 255;
					if (red === fromR && green === fromG && blue === fromB) {
						let toR: number = (toColors[colorIndex] & 16711680) >> 16;
						let toG: number = (toColors[colorIndex] & 65280) >> 8;
						let toB: number = (toColors[colorIndex] & 255) >> 0;
						data[((pos + 0) | 0)] = ((toR & 255) << 24 >> 24);
						data[((pos + 1) | 0)] = ((toG & 255) << 24 >> 24);
						data[((pos + 2) | 0)] = ((toB & 255) << 24 >> 24);
					}
					pos = ((pos + 3) | 0);
				}
			}
		}
	}
	loadImageRecolored(index: number, fromColors: Int32Array, toColors: Int32Array): Image {
		let name: string = ResourceLoader.getName(index);
		let header = pngHeaderOf(name);
		let palette = pngPaletteOf(name);
		if (header === null || palette === null || (header.bitDepth !== 4 && header.bitDepth !== 8)) {
			return null;
		}
		let image: Image = ResourceLoader.loadImage(index);
		return image === null ? null : recolourImage(image, fromColors, toColors, palette);
	}

	constructor() {
		this.initCrcTable();
	}
}
