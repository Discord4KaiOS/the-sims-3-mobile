// Generated from SpriteRenderer.java by tools/web/JavaToTs.java; keep this file in sync.
import { Image, DataInputStream, Graphics, IOException } from '../runtime';
import { ResourceLoader } from './ResourceLoader';
import { SpriteIndex } from './SpriteIndex';
import { Animator } from './Animator';

export class SpriteRenderer {
	static palette: Int8Array = null!;
	static spriteRects: Int16Array[] = null!;
	static recolorIndex: number = 0;
	static tiles: Image[][] = Array.from({ length: 4 }, () => new Array(72).fill(null));
	static frameDurations: Int16Array[] = null!;
	static framePartOffsets: Int16Array[] = null!;
	static parts: Int16Array[] = null!;
	static loadPalette(loader: ResourceLoader): boolean {
		let input: DataInputStream = new DataInputStream(loader.openStream(4));
		SpriteRenderer.palette = new Int8Array(ResourceLoader.getSize(4));
		try {
			input.read(SpriteRenderer.palette);
			input.close();
			return true;
		}
		catch (ignored) {
			return false;
		}
	}
	static setColor(gfx: Graphics, colorIndex: number): void {
		let offset: number = Math.imul(colorIndex, 3);
		let colors: Int8Array = SpriteRenderer.palette;
		gfx.setColor(colors[offset] & 255, colors[((offset + 1) | 0)] & 255, colors[((offset + 2) | 0)] & 255);
	}
	static loadSpriteRects(loader: ResourceLoader): boolean {
		try {
			let rects: Int16Array[] = Array.from({ length: 1418 }, () => new Int16Array(5));
			let input: DataInputStream = new DataInputStream(loader.openStream(31));
			for (let rowIndex: number = 0; rowIndex < 1418; rowIndex++) {
				let rect: Int16Array = null!;
				(rect = rects[rowIndex])[0] = (input.readUnsignedByte() << 16 >> 16);
				rect[1] = input.readShort();
				rect[2] = input.readShort();
				rect[3] = input.readShort();
				rect[4] = input.readShort();
			}
			SpriteRenderer.spriteRects = rects;
			return true;
		}
		catch (ignored) {
			return false;
		}
	}
	static loadFrameData(loader: ResourceLoader): boolean {
		let loaded: boolean = false;
		try {
			let stream: DataInputStream = new DataInputStream(loader.openStream(3));
			let durations: Int16Array[] = new Array(710).fill(null);
			let partOffsets: Int16Array[] = new Array(710).fill(null);
			let partData: Int16Array[] = new Array(710).fill(null);
			for (let spriteId: number = 0; spriteId !== 710; spriteId++) {
				let partCount: number = 0;
				let durationCount: number = stream.readUnsignedByte();
				for (let frameIndex: number = 0; frameIndex < durationCount; frameIndex++) {
					stream.readShort();
					let framePartCount: number = stream.readUnsignedByte();
					for (let partIndex: number = 0; partIndex < framePartCount; partIndex++) {
						let kind: number = 0;
						let fieldCount: number = SpriteRenderer.partKind(kind = stream.readUnsignedByte());
						for (let fieldIndex: number = 0; fieldIndex < fieldCount; fieldIndex++) {
							stream.readByte();
						}
						partCount = ((partCount + SpriteRenderer.partSize(kind)) | 0);
					}
				}
				durations[spriteId] = new Int16Array(durationCount);
				partOffsets[spriteId] = new Int16Array(durationCount);
				partData[spriteId] = new Int16Array(partCount);
			}
			SpriteRenderer.frameDurations = durations;
			SpriteRenderer.framePartOffsets = partOffsets;
			SpriteRenderer.parts = partData;
			stream.close();
			stream = new DataInputStream(loader.openStream(3));
			for (let spriteId: number = 0; spriteId !== 710; spriteId++) {
				let offset: number = 0;
				let framePartData: Int16Array = partData[spriteId];
				let frameDurations: Int16Array = durations[spriteId];
				let framePartOffsets: Int16Array = partOffsets[spriteId];
				let durationCount: number = stream.readUnsignedByte();
				for (let frameIndex: number = 0; frameIndex < durationCount; frameIndex++) {
					frameDurations[frameIndex] = stream.readShort();
					framePartOffsets[frameIndex] = (offset << 16 >> 16);
					let framePartCount: number = stream.readUnsignedByte();
					for (let partIndex: number = 0; partIndex < framePartCount; partIndex++) {
						let kind: number = 0;
						switch (kind = stream.readUnsignedByte()) {
							case 0:
							case 11:
								framePartData[((offset + 3) | 0)] = stream.readShort();
								framePartData[((offset + 1) | 0)] = stream.readShort();
								framePartData[((offset + 2) | 0)] = stream.readShort();
								break;
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
							case 8:
								framePartData[((offset + 1) | 0)] = stream.readShort();
								framePartData[((offset + 2) | 0)] = stream.readShort();
								framePartData[((offset + 3) | 0)] = stream.readShort();
								framePartData[((offset + 4) | 0)] = stream.readShort();
								break;
							case 7:
								framePartData[((offset + 1) | 0)] = stream.readShort();
								framePartData[((offset + 2) | 0)] = stream.readShort();
							case 9:
							default:
								break;
							case 10:
								framePartData[((offset + 1) | 0)] = stream.readByte();
						}
						framePartData[offset] = (kind << 16 >> 16);
						offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
					}
				}
			}
			stream.close();
			return true;
		}
		catch (error) {
			if (error instanceof IOException) {
				return false;
			}
			else {
				throw error;
			}
		}
	}
	static partKind(kind: number): number {
		switch (kind) {
			case 0:
			case 11:
				return 6;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 8:
				return 8;
			case 7:
				return 4;
			case 9:
			default:
				return 0;
			case 10:
				return 1;
		}
	}
	static partSize(kind: number): number {
		switch (kind) {
			case 0:
			case 11:
				return 4;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 8:
				return 5;
			case 7:
				return 3;
			case 9:
			default:
				return 0;
			case 10:
				return 2;
		}
	}
	static partOffset(spriteId: number, frame: number): number {
		return SpriteRenderer.framePartOffsets[spriteId][frame];
	}
	static nextPartOffset(spriteId: number, frame: number): number {
		let offsets: Int16Array = SpriteRenderer.framePartOffsets[spriteId];
		return frame < ((offsets.length - 1) | 0) ? offsets[((frame + 1) | 0)] : SpriteRenderer.parts[spriteId].length;
	}
	static loadTile(loader: ResourceLoader, spriteId: number): boolean {
		return SpriteRenderer.loadTileRecolored(loader, spriteId, null, null, 0);
	}
	static loadTileRecolored(loader: ResourceLoader, spriteId: number, fromColors: Int32Array, toColors: Int32Array, recolorSet: number): boolean {
		let slot: number = 0;
		while (slot < 72 && spriteId !== SpriteIndex.SPRITE_IDS[slot]) {
			slot++;
		}
		if (72 === slot) {
			return false;
		}
		else {
			let slots: Image[] = null!;
			if ((slots = SpriteRenderer.tiles[recolorSet])[slot] === null) {
				let image: Image = null!;
				if (fromColors !== null) {
					image = loader.loadImageRecolored(spriteId, fromColors, toColors);
				}
				else {
					image = ResourceLoader.loadImage(spriteId);
				}
				slots[slot] = image;
				if (image === null) {
					return false;
				}
			}
			return true;
		}
	}
	static unloadTile(spriteId: number, recolorSet: number): boolean {
		let slot: number = 0;
		while (slot < 72 && spriteId !== SpriteIndex.SPRITE_IDS[slot]) {
			slot++;
		}
		if (72 === slot) {
			return false;
		}
		else {
			SpriteRenderer.tiles[recolorSet][slot] = null;
			return true;
		}
	}
	static drawFrame(gfx: Graphics, spriteId: number, frame: number, x: number, y: number): void {
		if (spriteId >= 0) {
			if ((spriteId & 16384) !== 0) {
				spriteId = (spriteId & -16385);
				SpriteRenderer.drawFrameMirrored(gfx, spriteId, frame, x, y);
			}
			else {
				SpriteRenderer.drawFrameNormal(gfx, spriteId, frame, x, y);
			}
		}
	}
	static drawFrameNormal(gfx: Graphics, spriteId: number, frame: number, x: number, y: number): void {
		let tileSlots: Image[] = SpriteRenderer.tiles[SpriteRenderer.recolorIndex];
		let rects: Int16Array[] = SpriteRenderer.spriteRects;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		while (offset < nextOffset) {
			let kind: number = 0;
			if ((kind = partData[((offset + 0) | 0)]) === 0 || kind === 11) {
				let rect: Int16Array = null!;
				let tileIndex: number = (rect = rects[partData[((offset + 3) | 0)]])[0] & 65535;
				let tile: Image = null!;
				if ((tile = tileSlots[tileIndex]) !== null) {
					let destX: number = ((x + partData[((offset + 1) | 0)]) | 0);
					let destY: number = ((y + partData[((offset + 2) | 0)]) | 0);
					let transform: number = kind === 0 ? 0 : 2;
					gfx.drawRegion(tile, rect[1], rect[2], rect[3], rect[4], transform, destX, destY, 20);
				}
			}
			else {
				if (kind !== 2 && kind !== 7) {
					SpriteRenderer.drawPart(gfx, x, y, partData, offset);
				}
			}
			offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
		}
	}
	static drawFrameMirrored(gfx: Graphics, spriteId: number, frame: number, x: number, y: number): void {
		let tileSlots: Image[] = SpriteRenderer.tiles[SpriteRenderer.recolorIndex];
		let rects: Int16Array[] = SpriteRenderer.spriteRects;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		while (offset < nextOffset) {
			let kind: number = 0;
			if ((kind = partData[((offset + 0) | 0)]) === 0 || kind === 11) {
				let rect: Int16Array = null!;
				let tileIndex: number = (rect = rects[partData[((offset + 3) | 0)]])[0] & 65535;
				let tile: Image = null!;
				if ((tile = tileSlots[tileIndex]) !== null) {
					let destX: number = ((((x - partData[((offset + 1) | 0)]) | 0) - rect[3]) | 0);
					let destY: number = ((y + partData[((offset + 2) | 0)]) | 0);
					let transform: number = kind === 0 ? 2 : 0;
					gfx.drawRegion(tile, rect[1], rect[2], rect[3], rect[4], transform, destX, destY, 20);
				}
			}
			else {
				if (kind !== 2 && kind !== 7) {
					SpriteRenderer.drawPartMirrored(gfx, x, y, partData, offset);
				}
			}
			offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
		}
	}
	static drawParts(gfx: Graphics, spriteId: number, frame: number, x: number, y: number, matchSpriteId: number, matchLimit: number, out: Int32Array): boolean {
		if (spriteId < 0) {
			return false;
		}
		else {
			if ((spriteId & 16384) !== 0) {
				spriteId = (spriteId & -16385);
				return SpriteRenderer.drawPartsClipped(gfx, spriteId, frame, x, y, matchSpriteId, matchLimit, out);
			}
			else {
				return SpriteRenderer.drawPartsMirrored(gfx, spriteId, frame, x, y, matchSpriteId, matchLimit, out);
			}
		}
	}
	static drawPartsMirrored(gfx: Graphics, spriteId: number, frame: number, x: number, y: number, matchSpriteId: number, matchLimit: number, out: Int32Array): boolean {
		let tileSlots: Image[] = SpriteRenderer.tiles[SpriteRenderer.recolorIndex];
		let rects: Int16Array[] = SpriteRenderer.spriteRects;
		let found: boolean = false;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		let matchParts: Int16Array = matchSpriteId >= 0 ? SpriteRenderer.parts[matchSpriteId] : null;
		while (offset < nextOffset) {
			let kind: number = 0;
			if ((kind = partData[((offset + 0) | 0)]) !== 0 && kind !== 11) {
				SpriteRenderer.drawPart(gfx, x, y, partData, offset);
			}
			else {
				let rectIndex: number = partData[((offset + 3) | 0)];
				let rect: Int16Array = rects[rectIndex];
				let destX: number = ((x + partData[((offset + 1) | 0)]) | 0);
				let destY: number = ((y + partData[((offset + 2) | 0)]) | 0);
				let tileIndex: number = rect[0] & 65535;
				let tile: Image = null!;
				if ((tile = tileSlots[tileIndex]) !== null) {
					let transform: number = kind === 0 ? 0 : 2;
					gfx.drawRegion(tile, rect[1], rect[2], rect[3], rect[4], transform, destX, destY, 20);
				}
				if (matchParts !== null) {
					let matchFrameCount: number = SpriteRenderer.frameCount(matchSpriteId);
					for (let matchFrame: number = 0; matchFrame < matchFrameCount; matchFrame++) {
						let matchOffset: number = SpriteRenderer.partOffset(matchSpriteId, matchFrame);
						let matchRectIndex: number = matchParts[((matchOffset + 3) | 0)] & 65535;
						if (rectIndex === matchRectIndex) {
							let matchNextOffset: number = SpriteRenderer.nextPartOffset(matchSpriteId, matchFrame);
							let matchX: number = matchParts[((matchOffset + 1) | 0)];
							let matchY: number = matchParts[((matchOffset + 2) | 0)];
							let skipped: number = 0;
							do {
								if ((matchOffset = ((matchOffset + SpriteRenderer.partSize(matchParts[((matchOffset + 0) | 0)])) | 0)) < matchNextOffset && matchParts[((matchOffset + 0) | 0)] === 0) {
									skipped++;
								}
							}
							while (skipped < matchLimit && matchOffset < matchNextOffset);
							if (out !== null) {
								found = true;
								let lastOffset: number = ((matchNextOffset - SpriteRenderer.partSize(7)) | 0);
								out[0] = ((((destX + matchParts[((lastOffset + 1) | 0)]) | 0) - matchX) | 0);
								out[1] = ((((destY + matchParts[((lastOffset + 2) | 0)]) | 0) - matchY) | 0);
							}
							if (matchOffset < matchNextOffset) {
								let offsetX: number = ((matchParts[((matchOffset + 1) | 0)] - matchX) | 0);
								let offsetY: number = ((matchParts[((matchOffset + 2) | 0)] - matchY) | 0);
								let clipRect: Int16Array = null!;
								let clipTileIndex: number = (clipRect = rects[matchParts[((matchOffset + 3) | 0)]])[0] & 65535;
								let clipTile: Image = tileSlots[clipTileIndex];
								if (tile !== null) {
									let clipTransform: number = 0;
									if (kind === 11) {
										clipTransform = 2;
										let clipWidth: number = clipRect[3];
										let partWidth: number = rect[3];
										offsetX = ((((offsetX * -1) | 0) - (((clipWidth - partWidth) | 0))) | 0);
									}
									else {
										clipTransform = 0;
									}
									offsetX = ((offsetX + destX) | 0);
									offsetY = ((offsetY + destY) | 0);
									gfx.drawRegion(clipTile, clipRect[1], clipRect[2], clipRect[3], clipRect[4], clipTransform, offsetX, offsetY, 20);
								}
							}
							break;
						}
					}
				}
			}
			offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
		}
		return found;
	}
	static drawPartsClipped(gfx: Graphics, spriteId: number, frame: number, x: number, y: number, matchSpriteId: number, matchLimit: number, out: Int32Array): boolean {
		let tileSlots: Image[] = SpriteRenderer.tiles[SpriteRenderer.recolorIndex];
		let rects: Int16Array[] = SpriteRenderer.spriteRects;
		let found: boolean = false;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		let matchParts: Int16Array = matchSpriteId >= 0 ? SpriteRenderer.parts[matchSpriteId] : null;
		while (offset < nextOffset) {
			let kind: number = 0;
			if ((kind = partData[((offset + 0) | 0)]) !== 0 && kind !== 11) {
				SpriteRenderer.drawPartMirrored(gfx, x, y, partData, offset);
			}
			else {
				let rectIndex: number = partData[((offset + 3) | 0)];
				let rect: Int16Array = rects[rectIndex];
				let destX: number = ((((x - partData[((offset + 1) | 0)]) | 0) - rect[3]) | 0);
				let destY: number = ((y + partData[((offset + 2) | 0)]) | 0);
				let tileIndex: number = rect[0] & 65535;
				let tile: Image = null!;
				if ((tile = tileSlots[tileIndex]) !== null) {
					let transform: number = kind === 0 ? 2 : 0;
					gfx.drawRegion(tile, rect[1], rect[2], rect[3], rect[4], transform, destX, destY, 20);
				}
				if (matchParts !== null) {
					let matchFrameCount: number = SpriteRenderer.frameCount(matchSpriteId);
					for (let matchFrame: number = 0; matchFrame < matchFrameCount; matchFrame++) {
						let matchOffset: number = SpriteRenderer.partOffset(matchSpriteId, matchFrame);
						let matchRectIndex: number = matchParts[((matchOffset + 3) | 0)] & 65535;
						if (rectIndex === matchRectIndex) {
							let matchNextOffset: number = SpriteRenderer.nextPartOffset(matchSpriteId, matchFrame);
							let matchX: number = matchParts[((matchOffset + 1) | 0)];
							let matchY: number = matchParts[((matchOffset + 2) | 0)];
							let skipped: number = 0;
							do {
								if ((matchOffset = ((matchOffset + SpriteRenderer.partSize(matchParts[((matchOffset + 0) | 0)])) | 0)) < matchNextOffset && matchParts[((matchOffset + 0) | 0)] === 0) {
									skipped++;
								}
							}
							while (skipped < matchLimit && matchOffset < matchNextOffset);
							if (out !== null) {
								found = true;
								let lastOffset: number = ((matchNextOffset - SpriteRenderer.partSize(7)) | 0);
								out[0] = ((((destX + matchParts[((lastOffset + 1) | 0)]) | 0) - matchX) | 0);
								out[1] = ((((destY + matchParts[((lastOffset + 2) | 0)]) | 0) - matchY) | 0);
							}
							if (matchOffset < matchNextOffset) {
								let offsetX: number = ((matchParts[((matchOffset + 1) | 0)] - matchX) | 0);
								let offsetY: number = ((matchParts[((matchOffset + 2) | 0)] - matchY) | 0);
								let clipRect: Int16Array = null!;
								let clipTileIndex: number = (clipRect = rects[matchParts[((matchOffset + 3) | 0)]])[0] & 65535;
								let clipTile: Image = tileSlots[clipTileIndex];
								if (tile !== null) {
									let clipTransform: number = 0;
									if (kind === 11) {
										clipTransform = 0;
									}
									else {
										clipTransform = 2;
										let clipWidth: number = clipRect[3];
										let partWidth: number = rect[3];
										offsetX = ((((offsetX * -1) | 0) - (((clipWidth - partWidth) | 0))) | 0);
									}
									offsetX = ((offsetX + destX) | 0);
									offsetY = ((offsetY + destY) | 0);
									gfx.drawRegion(clipTile, clipRect[1], clipRect[2], clipRect[3], clipRect[4], clipTransform, offsetX, offsetY, 20);
								}
							}
							break;
						}
					}
				}
			}
			offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
		}
		return found;
	}
	static drawPart(gfx: Graphics, x: number, y: number, partData: Int16Array, offset: number): void {
		let kind: number = 0;
		switch (kind = partData[((offset + 0) | 0)]) {
			case 3:
			case 4:
			case 5:
			case 6:
				let destX: number = ((x + partData[((offset + 1) | 0)]) | 0);
				let destY: number = ((y + partData[((offset + 2) | 0)]) | 0);
				let width: number = partData[((offset + 3) | 0)];
				let height: number = partData[((offset + 4) | 0)];
				switch (kind) {
					case 3:
						gfx.drawRect(destX, destY, ((width - 1) | 0), ((height - 1) | 0));
						break;
					case 4:
						gfx.fillRect(destX, destY, width, height);
						break;
					case 5:
						gfx.drawArc(destX, destY, ((width - 1) | 0), ((height - 1) | 0), 0, 360);
						break;
					case 6:
						gfx.fillArc(destX, destY, width, height, 0, 360);
					default:
						return;
				}
			case 7:
			case 9:
			default:
				break;
			case 8:
				let startX: number = ((x + partData[((offset + 1) | 0)]) | 0);
				let startY: number = ((y + partData[((offset + 2) | 0)]) | 0);
				let endX: number = ((x + partData[((offset + 3) | 0)]) | 0);
				let endY: number = ((y + partData[((offset + 4) | 0)]) | 0);
				gfx.drawLine(startX, startY, endX, endY);
				return;
			case 10:
				SpriteRenderer.setColor(gfx, partData[((offset + 1) | 0)]);
		}
	}
	static drawPartMirrored(gfx: Graphics, x: number, y: number, partData: Int16Array, offset: number): void {
		let kind: number = 0;
		switch (kind = partData[((offset + 0) | 0)]) {
			case 3:
			case 4:
			case 5:
			case 6:
				let width: number = partData[((offset + 3) | 0)];
				let height: number = partData[((offset + 4) | 0)];
				let destX: number = ((x - (((partData[((offset + 1) | 0)] + width) | 0))) | 0);
				let destY: number = ((y + partData[((offset + 2) | 0)]) | 0);
				switch (kind) {
					case 3:
						gfx.drawRect(destX, destY, ((width - 1) | 0), ((height - 1) | 0));
						break;
					case 4:
						gfx.fillRect(destX, destY, width, height);
						break;
					case 5:
						gfx.drawArc(destX, destY, ((width - 1) | 0), ((height - 1) | 0), 0, 360);
						break;
					case 6:
						gfx.fillArc(destX, destY, width, height, 0, 360);
					default:
						return;
				}
			case 7:
			case 9:
			default:
				break;
			case 8:
				let startX: number = ((x - partData[((offset + 1) | 0)]) | 0);
				let startY: number = ((y + partData[((offset + 2) | 0)]) | 0);
				let endX: number = ((x - partData[((offset + 3) | 0)]) | 0);
				let endY: number = ((y + partData[((offset + 4) | 0)]) | 0);
				gfx.drawLine(startX, startY, endX, endY);
				return;
			case 10:
				SpriteRenderer.setColor(gfx, partData[((offset + 1) | 0)]);
		}
	}
	static frameCount(spriteId: number): number {
		spriteId = (spriteId & -16385);
		return SpriteRenderer.frameDurations[spriteId].length;
	}
	static frameDuration(spriteId: number, frame: number): number {
		spriteId = (spriteId & -16385);
		return SpriteRenderer.frameDurations[spriteId][frame];
	}
	static partX(parts: Int16Array, offset: number): number {
		switch (parts[((offset + 0) | 0)]) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 9:
			case 11:
				return parts[((offset + 1) | 0)];
			case 8:
				let start: number = parts[((offset + 1) | 0)];
				let end: number = parts[((offset + 3) | 0)];
				if (start < end) {
					return start;
				}
				return end;
			case 10:
			default:
				return 0;
		}
	}
	static partY(parts: Int16Array, offset: number): number {
		switch (parts[((offset + 0) | 0)]) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 9:
			case 11:
				return parts[((offset + 2) | 0)];
			case 8:
				let start: number = parts[((offset + 2) | 0)];
				let end: number = parts[((offset + 4) | 0)];
				if (start < end) {
					return start;
				}
				return end;
			case 10:
			default:
				return 0;
		}
	}
	static partWidth(parts: Int16Array, offset: number): number {
		switch (parts[((offset + 0) | 0)]) {
			case 0:
			case 11:
				let rectIndex: number = 0;
				rectIndex = (((rectIndex = parts[((offset + 3) | 0)]) & 65535) << 16 >> 16);
				return SpriteRenderer.spriteRects[rectIndex][3];
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				return parts[((offset + 3) | 0)];
			case 7:
			case 10:
			default:
				return 0;
			case 8:
				return Math.abs(((parts[((offset + 1) | 0)] - parts[((offset + 3) | 0)]) | 0));
			case 9:
				return 0;
		}
	}
	static partHeight(parts: Int16Array, offset: number): number {
		switch (parts[((offset + 0) | 0)]) {
			case 0:
			case 11:
				let rectIndex: number = 0;
				rectIndex = (((rectIndex = parts[((offset + 3) | 0)]) & 65535) << 16 >> 16);
				return SpriteRenderer.spriteRects[rectIndex][4];
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				return parts[((offset + 4) | 0)];
			case 7:
			case 10:
			default:
				return 0;
			case 8:
				return Math.abs(((parts[((offset + 2) | 0)] - parts[((offset + 4) | 0)]) | 0));
			case 9:
				return 0;
		}
	}
	static frameMinX(spriteId: number, frame: number): number {
		spriteId = (spriteId & -16385);
		let min: number = 2147483647;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		if (offset === nextOffset) {
			return 0;
		}
		else {
			while (offset < nextOffset) {
				let kind: number = 0;
				let x: number = 0;
				if ((kind = partData[((offset + 0) | 0)]) !== 10 && (x = SpriteRenderer.partX(partData, offset)) < min) {
					min = x;
				}
				offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
			}
			return min;
		}
	}
	static frameMinY(spriteId: number, frame: number): number {
		spriteId = (spriteId & -16385);
		let min: number = 2147483647;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		if (offset === nextOffset) {
			return 0;
		}
		else {
			while (offset < nextOffset) {
				let kind: number = 0;
				let y: number = 0;
				if ((kind = partData[((offset + 0) | 0)]) !== 10 && (y = SpriteRenderer.partY(partData, offset)) < min) {
					min = y;
				}
				offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
			}
			return min;
		}
	}
	static frameWidth(spriteId: number, frame: number): number {
		spriteId = (spriteId & -16385);
		let min: number = 2147483647;
		let max: number = -2147483648;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		if (offset === nextOffset) {
			return 0;
		}
		else {
			while (offset < nextOffset) {
				let kind: number = 0;
				if ((kind = partData[((offset + 0) | 0)]) !== 10) {
					let x: number = 0;
					let right: number = (((x = SpriteRenderer.partX(partData, offset)) + SpriteRenderer.partWidth(partData, offset)) | 0);
					if (x < min) {
						min = x;
					}
					if (right > max) {
						max = right;
					}
				}
				offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
			}
			return ((max - min) | 0);
		}
	}
	static frameHeight(spriteId: number, frame: number): number {
		spriteId = (spriteId & -16385);
		let min: number = 2147483647;
		let max: number = -2147483648;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		if (offset === nextOffset) {
			return 0;
		}
		else {
			while (offset < nextOffset) {
				let kind: number = 0;
				if ((kind = partData[((offset + 0) | 0)]) !== 10) {
					let y: number = 0;
					let bottom: number = (((y = SpriteRenderer.partY(partData, offset)) + SpriteRenderer.partHeight(partData, offset)) | 0);
					if (y < min) {
						min = y;
					}
					if (bottom > max) {
						max = bottom;
					}
				}
				offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
			}
			return ((max - min) | 0);
		}
	}
	static countSpecialParts(spriteId: number, frame: number): number {
		spriteId = (spriteId & -16385);
		let count: number = 0;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		while (offset < nextOffset) {
			let kind: number = 0;
			if ((kind = partData[((offset + 0) | 0)]) === 7) {
				count++;
			}
			offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
		}
		return count;
	}
	static hitTest(out: Int32Array, spriteId: number, frame: number, handleIndex: number): boolean {
		let mirrored: boolean = false;
		if ((spriteId & 16384) !== 0) {
			spriteId = (spriteId & -16385);
			mirrored = true;
		}
		let handleCount: number = 0;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		while (offset < nextOffset) {
			let kind: number = 0;
			if ((kind = partData[((offset + 0) | 0)]) === 7) {
				if (handleCount === handleIndex) {
					out[0] = partData[((offset + 1) | 0)];
					out[1] = partData[((offset + 2) | 0)];
					if (mirrored) {
						out[0] = ((out[0] * -1) | 0);
					}
					return true;
				}
				handleCount++;
			}
			offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
		}
		out[0] = 0;
		out[1] = 0;
		return false;
	}
	static getTileImage(spriteId: number, frame: number, imageIndex: number): Image {
		spriteId = (spriteId & -16385);
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		while (offset < nextOffset) {
			let kind: number = 0;
			if ((kind = partData[((offset + 0) | 0)]) === 0 || kind === 11) {
				if (imageIndex === 0) {
					let rectIndex: number = partData[((offset + 3) | 0)];
					let tileIndex: number = SpriteRenderer.spriteRects[rectIndex][0];
					return SpriteRenderer.tiles[SpriteRenderer.recolorIndex][tileIndex];
				}
				imageIndex--;
			}
			offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
		}
		return null;
	}
	static hitTestMirrored(out: Int32Array, spriteId: number, frame: number, handleIndex: number): boolean {
		if ((spriteId & 16384) !== 0) {
			spriteId = (spriteId & -16385);
		}
		let handleCount: number = 0;
		let partData: Int16Array = SpriteRenderer.parts[spriteId];
		let offset: number = SpriteRenderer.partOffset(spriteId, frame);
		let nextOffset: number = SpriteRenderer.nextPartOffset(spriteId, frame);
		while (offset < nextOffset) {
			let kind: number = 0;
			if ((kind = partData[((offset + 0) | 0)]) === 2) {
				if (handleCount === handleIndex) {
					out[0] = partData[((offset + 1) | 0)];
					out[1] = partData[((offset + 2) | 0)];
					out[2] = partData[((offset + 3) | 0)];
					out[3] = partData[((offset + 4) | 0)];
					return true;
				}
				handleCount++;
			}
			offset = ((offset + SpriteRenderer.partSize(kind)) | 0);
		}
		out[0] = 0;
		out[1] = 0;
		out[2] = 0;
		out[3] = 0;
		return false;
	}
	static drawAnimator(gfx: Graphics, animator: Animator, x: number, y: number): void {
		SpriteRenderer.drawFrame(gfx, animator.animId, animator.frame, x, y);
	}
	static setRecolorIndex(index: number): void {
		SpriteRenderer.recolorIndex = index;
	}
	constructor() {
	}
}
