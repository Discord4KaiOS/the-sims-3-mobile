// Generated from SdkText.java by tools/web/JavaToTs.java; keep this file in sync.
import { Graphics, StringBuffer, MIDlet, Image, InputStream, IOException, IllegalStateException, Font, chr, arraycopy, loadResource, ByteArrayInputStream, bytesToString, Alert, AlertType, Display, getProperty, IndexOutOfBoundsException } from '../runtime';
import { SdkFeatures } from './SdkFeatures';
import { SdkString } from './SdkString';

export class SdkText {
	static TOP: number = 16;
	static BOTTOM: number = 32;
	static BASELINE: number = 64;
	static HCENTER: number = 1;
	static VCENTER: number = 2;
	static LEFT: number = 4;
	static RIGHT: number = 8;
	static logPrefix: string = null;
	static font: any = null!;
	static fontImage: any = null!;
	static usesTransform: number = 0;
	static fontHeader: Int8Array = null!;
	static headerStringOffsets: Int16Array = null!;
	static languageChunkGroups: Int16Array = null!;
	static chunkStringCounts: Int16Array = null!;
	static glyphData: Int8Array = null!;
	static colorTable: Int32Array = null!;
	static glyphIndexTable: Int16Array = null!;
	static glyphOffsetTable: Int16Array = null!;
	static strings: Int8Array[] = null!;
	static chunkStringOffsets: Int16Array[] = null!;
	static chunkGroupOfId: Int8Array = null!;
	static chunkFlags: Int8Array = null!;
	static chunkConfig: number = 0;
	static storageChunkOffsets: Int16Array[] = null!;
	static chunkStringCount: Int16Array = null!;
	static chunkDataSize: Int16Array = null!;
	static chunkOffsetCount: Int16Array = null!;
	static chunkOwners: Int8Array = null!;
	static languageEncodings: Int8Array = null!;
	static numFontGlyphs: number = 0;
	static languageCodes: string[] = null!;
	static fontDifferentiators: Int8Array = null!;
	static currentLanguage: number = 0;
	static penPosition: Int32Array = new Int32Array(2);
	static glyphBounds: Int32Array = new Int32Array(4);
	static tempShort: Int8Array = new Int8Array(2);
	static graphics: Graphics = null!;
	static systemFontBuffer: StringBuffer = null!;
	static changingLanguage: boolean = false;
	static midlet: MIDlet = null!;
	static textColor: number = 0;
	static logBuffer: StringBuffer = null!;
	static createFont(fontData: any, data: Int8Array, transform: number): any {
		let parsed: any[] = SdkText.parseFontChunk(data);
		return [fontData, parsed[0], parsed[1], parsed[2], parsed[3], parsed[4], parsed[5], transform];
	}
	static createFont_2(image: Image, data: Int8Array): any {
		return SdkText.createFont(image, data, -1);
	}
	static skip(stream: InputStream, count: number): number {
		try {
			return stream.skip(count);
		}
		catch (error) {
			if (error instanceof IOException) {
				throw new IllegalStateException(error);
			}
			else {
				throw error;
			}
		}
	}
	static setFont(fontData: any): void {
		SdkText.font = null;
		SdkText.fontHeader = null;
		SdkText.fontImage = null;
		SdkText.languageChunkGroups = null;
		SdkText.headerStringOffsets = null;
		SdkText.chunkStringCounts = null;
		SdkText.glyphData = null;
		SdkText.colorTable = null;
		if (fontData !== null) {
			if (fontData instanceof Font && SdkFeatures.isSystemFontEnabled()) {
				(SdkText.fontHeader = new Int8Array(12))[0] = 1;
				SdkText.fontHeader[1] = 2;
				SdkText.fontHeader[7] = 0;
				SdkText.fontHeader[8] = 1;
				SdkText.fontHeader[9] = 2;
				SdkText.fontHeader[10] = 4;
				if (SdkText.graphics !== null) {
					SdkText.graphics.setFont((fontData as unknown as Font));
				}
			}
			else {
				if (SdkFeatures.isRectangleFontEnabled() || SdkFeatures.isFreeTypeFontEnabled()) {
					SdkText.fontHeader = ((fontData as unknown as any[])[1] as unknown as Int8Array);
					SdkText.fontImage = (fontData as unknown as any[])[0];
					SdkText.usesTransform = ((fontData as unknown as any[])[7] as unknown as number);
					SdkText.languageChunkGroups = ((fontData as unknown as any[])[2] as unknown as Int16Array);
					SdkText.headerStringOffsets = ((fontData as unknown as any[])[3] as unknown as Int16Array);
					SdkText.chunkStringCounts = ((fontData as unknown as any[])[4] as unknown as Int16Array);
					SdkText.glyphData = ((fontData as unknown as any[])[5] as unknown as Int8Array);
					SdkText.colorTable = ((fontData as unknown as any[])[6] as unknown as Int32Array);
					if (SdkText.languageEncodings !== null && SdkText.fontHeader[11] !== SdkText.languageEncodings[SdkText.currentLanguage] && SdkText.languageEncodings[SdkText.currentLanguage] > 1) {
						SdkText.loadChunksForLanguage();
					}
				}
			}
		}
		SdkText.font = fontData;
	}
	static setSpaceSpacing(spacing: number): void {
		SdkText.fontHeader[5] = (spacing << 24 >> 24);
	}
	static getSpaceSpacing(): number {
		if (SdkText.font === null && SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "getSpaceSpacing::no font loaded, use setFont(Object)", 4);
		}
		return SdkText.fontHeader[5] & 255;
	}
	static getLeadingSpacing(): number {
		if (SdkText.font === null && SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "getLeadingSpacing::no font loaded, use setFont(Object)", 4);
		}
		return SdkText.fontHeader[2];
	}
	static getLineHeight(): number {
		if (SdkText.font === null && SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "getLineSize::no font loaded, use setFont(Object)", 4);
		}
		if ((SdkText.fontHeader[10] & 4) !== 0 && SdkFeatures.isSystemFontEnabled()) {
			return (SdkText.font as unknown as Font).getHeight();
		}
		else {
			return !SdkFeatures.isRectangleFontEnabled() && !SdkFeatures.isFreeTypeFontEnabled() ? -1 : (((((SdkText.fontHeader[3] & 255) + (SdkText.fontHeader[4] & 255)) | 0) + SdkText.fontHeader[2]) | 0);
		}
	}
	static drawString(text: SdkString, x: number, y: number, anchor: number): void {
		SdkText.drawSubString(text, 0, text.length, x, y, anchor);
	}
	static advancePen(position: Int32Array, distance: number): void {
		if (SdkText.fontHeader[8] === 1) {
			position[0] = ((position[0] + distance) | 0);
		}
		else {
			if (SdkText.fontHeader[8] === 4) {
				position[0] = ((position[0] - distance) | 0);
			}
			else {
				if (SdkText.fontHeader[8] === 2) {
					position[1] = ((position[1] + distance) | 0);
				}
				else {
					if (SdkText.fontHeader[8] === 8) {
						position[1] = ((position[1] - distance) | 0);
					}
				}
			}
		}
	}
	static drawSubString(text: SdkString, offset: number, count: number, x: number, y: number, anchor: number): void {
		if (SdkFeatures.isLoggingEnabled()) {
			if (SdkText.graphics === null) {
				SdkText.log(SdkText.logPrefix + "drawSubString::Graphics are null, use setGraphics(Graphics)", 4);
			}
			if (SdkText.font === null) {
				SdkText.log(SdkText.logPrefix + "drawSubString ::Font is null, use setFont(Object)", 4);
			}
			if (!SdkFeatures.isSystemFontEnabled() && !SdkFeatures.isRectangleFontEnabled() && !SdkFeatures.isFreeTypeFontEnabled()) {
				SdkText.log(SdkText.logPrefix + "drawSubString ::no font support enabled, you must enable at least 1 type of font support in SDKConfig", 4);
			}
		}
		if ((SdkText.fontHeader[10] & 4) !== 0 && SdkFeatures.isSystemFontEnabled()) {
			if (SdkText.systemFontBuffer === null) {
				SdkText.systemFontBuffer = new StringBuffer();
			}
			SdkText.systemFontBuffer.setLength(0);
			for (let index: number = offset; index < ((offset + count) | 0); index++) {
				let codeUnit: number = 0;
				if ((codeUnit = text.charAt(index)) !== 173 || index === ((((offset + count) | 0) - 1) | 0)) {
					SdkText.systemFontBuffer.append(chr(codeUnit));
				}
			}
			if (SdkText.fontHeader[7] !== 0 && SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkText.logPrefix + "drawSubString ::rendering of system fonts does not support transform: " + SdkText.fontHeader[7], 4);
			}
			SdkText.graphics.setColor(SdkText.textColor);
			SdkText.graphics.drawString(SdkText.systemFontBuffer.toString(), x, y, anchor);
		}
		else {
			if (SdkFeatures.isRectangleFontEnabled() || SdkFeatures.isFreeTypeFontEnabled()) {
				let ascent: number = SdkText.fontHeader[3] & 255;
				let lineHeight: number = SdkText.getLineHeight();
				let transform: number = SdkText.fontHeader[8];
				let currentColor: number = SdkText.fontHeader[9];
				let hasTransform: boolean = SdkText.usesTransform !== -1;
				let isFreeType: boolean = (SdkText.fontHeader[10] & 2) !== 0;
				let isPacked: boolean = (SdkText.fontHeader[10] & -128) !== 0;
				let anchorX: number = 0;
				let anchorY: number = 0;
				if ((anchor & SdkText.RIGHT) !== 0) {
					anchorX = ((0 - SdkText.substringWidth(text, offset, count)) | 0);
				}
				else {
					if ((anchor & SdkText.HCENTER) !== 0) {
						anchorX = ((0 - (SdkText.substringWidth(text, offset, count) >> 1)) | 0);
					}
				}
				if ((anchor & SdkText.BASELINE) !== 0) {
					anchorY = ((0 - ascent) | 0);
				}
				else {
					if ((anchor & SdkText.BOTTOM) !== 0) {
						anchorY = ((0 - lineHeight) | 0);
					}
				}
				if (transform === 1) {
					x = ((x + anchorX) | 0);
					y = ((y + anchorY) | 0);
				}
				else {
					if (transform === 4) {
						x = ((x - anchorX) | 0);
						y = ((y - anchorY) | 0);
					}
					else {
						if (transform === 2) {
							x = ((x - anchorY) | 0);
							y = ((y + anchorX) | 0);
						}
						else {
							if (transform === 8) {
								x = ((x - anchorY) | 0);
								y = ((y - anchorX) | 0);
							}
						}
					}
				}
				SdkText.penPosition[0] = x;
				SdkText.penPosition[1] = y;
				if ((x = text.encoding()) !== 1 && x !== 0 && x !== SdkText.languageEncodings[SdkText.currentLanguage] && SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkText.logPrefix + "drawSubString::SDKString [" + text.toString() + "] has encoding=" + text.encoding() + " but the currently set language has encoding: " + SdkText.languageEncodings[SdkText.currentLanguage], 4);
				}
				let boundsAnchorX: number = (((transform & 5) !== 0 ? (transform & 4) >> 1 : ((((currentColor & 1) << 1) + ((~currentColor & 2) >> 1)) | 0)) << 24 >> 24);
				let boundsAnchorY: number = (((transform & 10) !== 0 ? (transform & 8) >> 2 : (((currentColor & 2) + (~currentColor & 1)) | 0)) << 24 >> 24);
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkText.logPrefix + "drawSubString::offset = " + offset + " len = " + count + " \"" + text.toString() + "\"", 1);
					if (isFreeType && SdkFeatures.isFreeTypeFontEnabled() && SdkText.fontHeader[7] !== 0) {
						SdkText.log(SdkText.logPrefix + "drawSubString ::rendering of rectangle fonts does not currently support transform: " + SdkText.fontHeader[7], 4);
					}
				}
				currentColor = (transform = SdkText.graphics.getColor());
				for (let index: number = offset; index < ((offset + count) | 0); index++) {
					let charCode: number = 0;
					if ((charCode = text.charCodeAt(index)) !== 173 || index === ((((offset + count) | 0) - 1) | 0)) {
						let glyphOffset: number = 0;
						let glyphWidth: number = SdkText.glyphHeight(glyphOffset = SdkText.glyphIndex((charCode << 16 >> 16), x));
						if (glyphOffset === -1) {
							if (charCode !== 32) {
								if (SdkFeatures.isLoggingEnabled()) {
									SdkText.log(SdkText.logPrefix + "drawSubString::character not in Font: \\u" + (x !== 0 && charCode >= 128 ? SdkText.fromCharset((charCode << 16 >> 16)) : charCode), 4);
								}
								SdkText.graphics.setColor(65280);
								SdkText.graphics.fillRect(SdkText.penPosition[0], SdkText.penPosition[1], glyphWidth, lineHeight);
								SdkText.graphics.setColor(16711680);
								currentColor = 16711680;
								SdkText.graphics.drawLine(SdkText.penPosition[0], SdkText.penPosition[1], ((SdkText.penPosition[0] + glyphWidth) | 0), ((SdkText.penPosition[1] + lineHeight) | 0));
								SdkText.graphics.drawLine(SdkText.penPosition[0], ((SdkText.penPosition[1] + lineHeight) | 0), ((SdkText.penPosition[0] + glyphWidth) | 0), SdkText.penPosition[1]);
							}
							SdkText.advancePen(SdkText.penPosition, glyphWidth);
						}
						else {
							let recordOffset: number = ++glyphOffset;
							glyphOffset++;
							charCode = ((SdkText.glyphData[recordOffset] & 255) << 16 >> 16);
							for (let pixelIndex: number = 0; pixelIndex < charCode; pixelIndex++) {
								if (isFreeType && SdkFeatures.isFreeTypeFontEnabled()) {
									let colorIndex: number = SdkText.glyphData[glyphOffset++] & 255;
									let glyphOffsetX: number = SdkText.glyphData[glyphOffset++];
									let glyphOffsetY: number = SdkText.glyphData[glyphOffset++];
									let rectWidth: number = 0;
									let rectHeight: number = 0;
									if (isPacked) {
										rectWidth = SdkText.glyphData[glyphOffset] >> 4 & 15;
										rectHeight = SdkText.glyphData[glyphOffset++] & 15;
									}
									else {
										rectWidth = SdkText.glyphData[glyphOffset++] & 255;
										rectHeight = SdkText.glyphData[glyphOffset++] & 255;
									}
									if (SdkText.colorTable[colorIndex] !== currentColor) {
										SdkText.graphics.setColor(SdkText.colorTable[colorIndex]);
										currentColor = SdkText.colorTable[colorIndex];
									}
									SdkText.graphics.fillRect(((SdkText.penPosition[0] + glyphOffsetX) | 0), ((((SdkText.penPosition[1] + ascent) | 0) - glyphOffsetY) | 0), rectWidth, rectHeight);
								}
								if (!isFreeType && SdkFeatures.isRectangleFontEnabled()) {
									let sourceX: number = SdkText.glyphData[glyphOffset++] & 255;
									let sourceY: number = SdkText.glyphData[glyphOffset++] & 255;
									let drawX: number = SdkText.glyphData[glyphOffset++];
									let drawY: number = SdkText.glyphData[glyphOffset++];
									let srcWidth: number = SdkText.glyphData[glyphOffset++] & 255;
									let srcHeight: number = SdkText.glyphData[glyphOffset++] & 255;
									SdkText.glyphBounds[0] = drawX;
									SdkText.glyphBounds[1] = ((drawY - ascent) | 0);
									SdkText.glyphBounds[2] = ((((drawX * -1) | 0) - srcWidth) | 0);
									SdkText.glyphBounds[3] = ((((ascent - drawY) | 0) - srcHeight) | 0);
									drawX = ((SdkText.penPosition[0] + SdkText.glyphBounds[boundsAnchorX]) | 0);
									drawY = ((SdkText.penPosition[1] + SdkText.glyphBounds[boundsAnchorY]) | 0);
									if (hasTransform) {
										SdkText.flushLog();
									}
									else {
										SdkText.drawGlyph((SdkText.fontImage as unknown as Image), sourceX, sourceY, srcWidth, srcHeight, SdkText.fontHeader[7], drawX, drawY, 0);
									}
								}
							}
							SdkText.advancePen(SdkText.penPosition, glyphWidth);
						}
					}
				}
				SdkText.graphics.setColor(transform);
			}
		}
	}
	static drawWrappedString(text: SdkString, wrapOffsets: Int16Array, firstLine: number, lineCount: number, x: number, y: number, anchor: number): void {
		if (SdkFeatures.isLoggingEnabled()) {
			if (SdkText.graphics === null) {
				SdkText.log(SdkText.logPrefix + "drawWrappedString::Graphics are null, use setGraphics(Graphics)", 4);
			}
			if (SdkText.font === null) {
				SdkText.log(SdkText.logPrefix + "drawWrappedString::Font is null, use setFont(Object)", 4);
			}
		}
		let lineHeight: number = SdkText.getLineHeight();
		let stepX: number = 0;
		let stepY: number = 0;
		let blockOffset: number = 0;
		if ((anchor & SdkText.VCENTER) !== 0) {
			blockOffset = ((Math.imul(wrapOffsets[0], lineHeight) - SdkText.fontHeader[2]) | 0) >> 1;
			anchor = (anchor ^ SdkText.VCENTER);
		}
		else {
			if ((anchor & SdkText.BOTTOM) !== 0) {
				blockOffset = ((Math.imul(wrapOffsets[0], lineHeight) - SdkText.fontHeader[2]) | 0);
				anchor = (anchor ^ SdkText.BOTTOM);
			}
		}
		anchor = (anchor | SdkText.TOP);
		if (SdkText.fontHeader[9] === 1) {
			x = ((x - blockOffset) | 0);
			stepX = lineHeight;
		}
		else {
			if (SdkText.fontHeader[9] === 4) {
				x = ((x + blockOffset) | 0);
				stepX = ((lineHeight * -1) | 0);
			}
			else {
				if (SdkText.fontHeader[9] === 2) {
					y = ((y - blockOffset) | 0);
					stepY = lineHeight;
				}
				else {
					y = ((y + blockOffset) | 0);
					stepY = ((lineHeight * -1) | 0);
				}
			}
		}
		let lineSeparator: number = wrapOffsets[(((wrapOffsets[0] & 65535) + 2) | 0)];
		for (let lineIndex: number = firstLine; lineIndex < ((firstLine + lineCount) | 0); lineIndex++) {
			let lineStart: number = wrapOffsets[lineIndex] & 65535;
			let lineLength: number = (((wrapOffsets[((lineIndex + 1) | 0)] & 65535) - lineStart) | 0);
			let charCode: number = 0;
			if ((charCode = text.charCodeAt((((wrapOffsets[((lineIndex + 1) | 0)] & 65535) - 1) | 0))) === lineSeparator || charCode === 32) {
				lineLength--;
			}
			SdkText.drawSubString(text, lineStart, lineLength, x, y, anchor);
			x = ((x + stepX) | 0);
			y = ((y + stepY) | 0);
		}
	}
	static glyphHeight(glyphOffset: number): number {
		let extraHeight: number = SdkText.fontHeader[6] & 255;
		return glyphOffset === -1 ? (((SdkText.fontHeader[5] & 255) + extraHeight) | 0) : (((SdkText.glyphData[glyphOffset & 65535] & 255) + extraHeight) | 0);
	}
	static glyphIndex(charCode: number, encoding: number): number {
		if (encoding === 0) {
			return SdkText.chunkStringCounts[SdkText.glyphWidth(SdkText.languageChunkGroups, charCode, 0, ((SdkText.languageChunkGroups.length - 1) | 0))];
		}
		else {
			return charCode >= 128 && encoding !== SdkText.languageEncodings[SdkText.currentLanguage] ? -1 : SdkText.chunkStringCounts[SdkText.headerStringOffsets[charCode] & 65535];
		}
	}
	static wrapText(text: SdkString, offsets: Int16Array, maxWidth: number, lineSeparator: number): Int16Array {
		return SdkText.wrapTextImpl(text, 0, text.length, offsets, maxWidth, lineSeparator);
	}
	static wrapTextImpl(text: SdkString, offset: number, count: number, offsets: Int16Array, maxWidth: number, lineSeparator: number): Int16Array {
		if (SdkText.font === null && SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "wrapSubString ::Font is null, use setFont(Object)", 4);
		}
		let ownsOffsets: boolean = false;
		if (offsets === null) {
			if (SdkText.glyphOffsetTable === null) {
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkText.logPrefix + "wrapSubString ::allocating initial _wrapOffsets[] with size: " + 50, 1);
				}
				SdkText.glyphOffsetTable = new Int16Array(50);
			}
			offsets = SdkText.glyphOffsetTable;
			ownsOffsets = true;
		}
		let encoding: number = text.encoding();
		let lineWidth: number = 0;
		let wordWidth: number = 0;
		let wordStart: number = -1;
		offsets[1] = (offset << 16 >> 16);
		let writeIndex: number = 2;
		for (let charIndex: number = offset; charIndex < ((count + offset) | 0); charIndex++) {
			let charCode: number = text.charCodeAt(charIndex);
			if (((writeIndex + 3) | 0) >= offsets.length && ownsOffsets) {
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkText.logPrefix + "wrapSubString ::_wrapOffsets[] is growing to size: " + (((offsets.length + 50) | 0)), 1);
				}
				SdkText.glyphOffsetTable = new Int16Array(((offsets.length + 50) | 0));
				arraycopy(offsets, 0, SdkText.glyphOffsetTable, 0, offsets.length);
				offsets = SdkText.glyphOffsetTable;
			}
			if (charCode === lineSeparator) {
				offsets[writeIndex++] = ((((charIndex + 1) | 0)) << 16 >> 16);
				lineWidth = 0;
				wordWidth = 0;
				wordStart = -1;
			}
			else {
				let charWidth: number = 0;
				if ((SdkText.fontHeader[10] & 4) !== 0 && SdkFeatures.isSystemFontEnabled()) {
					charWidth = (SdkText.font as unknown as Font).charWidth(text.charAt(charIndex));
				}
				else {
					if (SdkFeatures.isRectangleFontEnabled() || SdkFeatures.isFreeTypeFontEnabled()) {
						charWidth = SdkText.glyphHeight(SdkText.glyphIndex(charCode, encoding));
					}
				}
				lineWidth = ((lineWidth + charWidth) | 0);
				wordWidth = ((wordWidth + charWidth) | 0);
				if (lineWidth > maxWidth) {
					if (wordStart === -1) {
						offsets[writeIndex++] = (charIndex << 16 >> 16);
						lineWidth = charWidth;
						wordWidth = 0;
					}
					else {
						offsets[writeIndex++] = ((((wordStart + 1) | 0)) << 16 >> 16);
						if (charCode !== 32 && charCode !== 173) {
							wordStart = -1;
						}
						else {
							wordStart = charIndex;
						}
						lineWidth = wordWidth;
						wordWidth = 0;
					}
				}
				else {
					if (charCode === 32 || charCode === 173) {
						wordStart = charIndex;
						wordWidth = 0;
					}
				}
			}
		}
		offsets[0] = ((((writeIndex - 1) | 0)) << 16 >> 16);
		offsets[writeIndex] = ((((count + offset) | 0)) << 16 >> 16);
		offsets[((writeIndex + 1) | 0)] = lineSeparator;
		if (SdkFeatures.isLoggingEnabled()) {
			for (let offsetIndex: number = 0; offsetIndex <= ((writeIndex + 1) | 0); offsetIndex++) {
				SdkText.log(SdkText.logPrefix + "wrapSubString::offsets[" + offsetIndex + "] = " + (offsets[offsetIndex] & 65535), 1);
			}
		}
		if (ownsOffsets) {
			let resized: Int16Array = new Int16Array(((writeIndex + 2) | 0));
			arraycopy(offsets, 0, resized, 0, resized.length);
			offsets = resized;
		}
		return offsets;
	}
	static stringWidth(text: SdkString): number {
		return SdkText.substringWidth(text, 0, text.length);
	}
	static substringWidth(text: SdkString, offset: number, count: number): number {
		if (SdkText.font === null && SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "getSubStringSize::no font loaded, use setFont(Object)", 4);
		}
		let width: number = 0;
		if ((SdkText.fontHeader[10] & 4) !== 0 && SdkFeatures.isSystemFontEnabled()) {
			width = (SdkText.font as unknown as Font).substringWidth(text.toString(), offset, count);
		}
		else {
			if (SdkFeatures.isRectangleFontEnabled() || SdkFeatures.isFreeTypeFontEnabled()) {
				let encoding: number = text.encoding();
				for (let index: number = offset; index < ((offset + count) | 0); index++) {
					width = ((width + SdkText.glyphHeight(SdkText.glyphIndex(text.charCodeAt(index), encoding))) | 0);
				}
			}
		}
		return width;
	}
	static isChunkLoaded(chunkId: number): boolean {
		let groupId: number = SdkText.chunkGroupOfId[chunkId];
		return SdkText.strings[groupId] !== null && SdkText.chunkOwners[groupId] === chunkId;
	}
	static loadStringsChunk(chunkId: number): number {
		if (SdkFeatures.isLoggingEnabled()) {
			if (SdkText.currentLanguage === -1) {
				SdkText.log(SdkText.logPrefix + "loadStringsChunk::language must be selected first!", 4);
			}
			if (chunkId < 1 || chunkId >= SdkText.chunkGroupOfId.length) {
				SdkText.log(SdkText.logPrefix + "loadStringsChunk::chunkID '" + chunkId + "' is out of bounds, valid range is [1," + SdkText.chunkGroupOfId.length + "] inclusive", 4);
			}
			if (SdkText.midlet === null) {
				SdkText.log(SdkText.logPrefix + "loadStringsChunk::midlet is null, use setMidlet(MIDlet)", 4);
			}
		}
		if (SdkText.isChunkLoaded(chunkId) && !SdkText.changingLanguage) {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkText.logPrefix + "loadStringsChunk::chunkID: " + chunkId + " already loaded, it won't be loaded again", 3);
			}
			return SdkText.chunkStringCount[chunkId];
		}
		else {
			let stream: InputStream = null;
			try {
				let resourceIndex: number = (SdkText.chunkFlags[chunkId] & 64) === 0 ? SdkText.currentLanguage : 0;
				if (SdkText.chunkConfig === 0) {
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log(SdkText.logPrefix + "loadStringsChunk::chunkID: " + chunkId + " is being loaded using CHUNK_CONFIG_CONCAT for resIndex: " + resourceIndex, 1);
					}
					stream = new ByteArrayInputStream(loadResource("/t_" + SdkText.languageCodes[resourceIndex]));
					for (let index: number = 1; index < chunkId; index++) {
						if ((SdkText.chunkFlags[index] & 64) === 0 || resourceIndex === 0) {
							SdkText.skip(stream, ((((((SdkText.chunkStringCount[index] + 1) | 0) << 1) + (SdkText.storageChunkOffsets[resourceIndex][index] & 65535)) | 0)));
						}
					}
				}
				else {
					if (SdkText.chunkConfig === 1) {
						if (SdkFeatures.isLoggingEnabled()) {
							SdkText.log(SdkText.logPrefix + "loadStringsChunk::chunkID: " + chunkId + " is being loaded using CHUNK_CONFIG_SEPARATE for resIndex: " + resourceIndex, 1);
						}
						stream = new ByteArrayInputStream(loadResource("/t_" + SdkText.languageCodes[resourceIndex] + chunkId));
					}
					else {
						if (SdkFeatures.isLoggingEnabled()) {
							SdkText.log(SdkText.logPrefix + "loadStringsChunk::illegal value of chunk config: " + SdkText.chunkConfig, 4);
						}
					}
				}
				let groupId: number = SdkText.chunkGroupOfId[chunkId];
				if (SdkText.chunkStringOffsets[groupId] === null) {
					SdkText.chunkStringOffsets[groupId] = new Int16Array(SdkText.chunkOffsetCount[groupId]);
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log(SdkText.logPrefix + "loadStringsChunk::allocating _chunkStringOffsets[" + groupId + "] to be of size: " + SdkText.chunkOffsetCount[groupId], 1);
					}
				}
				for (let stringIndex: number = 0; stringIndex < ((SdkText.chunkStringCount[chunkId] + 1) | 0); stringIndex++) {
					SdkText.tempShort[0] = (stream.read() << 24 >> 24);
					SdkText.tempShort[1] = (stream.read() << 24 >> 24);
					SdkText.chunkStringOffsets[groupId][stringIndex] = SdkText.readShort(SdkText.tempShort, 0);
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log(SdkText.logPrefix + "loadStringsChunk::_chunkStringOffsets[" + groupId + "][" + stringIndex + "]: " + (SdkText.chunkStringOffsets[groupId][stringIndex] & 65535), 1);
					}
				}
				if (SdkText.strings[groupId] === null) {
					SdkText.strings[groupId] = new Int8Array(SdkText.chunkDataSize[groupId] & 65535);
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log(SdkText.logPrefix + "loadStringsChunk::allocating _chunkStringData[" + groupId + "] to be of size: " + (SdkText.chunkDataSize[groupId] & 65535), 1);
					}
				}
				SdkText.readFully(stream, SdkText.strings[groupId], 0, SdkText.storageChunkOffsets[resourceIndex][chunkId] & 65535);
				SdkText.chunkOwners[groupId] = (chunkId << 24 >> 24);
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkText.logPrefix + "loadStringsChunk::_groupMap[" + groupId + "]: " + chunkId, 1);
				}
				stream.close();
			}
			catch (error) {
				if (error instanceof IOException) {
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log(SdkText.logPrefix + "loadStringsChunk::IOException, e.msg: " + (error as any).message, 4);
					}
				}
				else {
					throw error;
				}
			}
			return SdkText.chunkStringCount[chunkId];
		}
	}
	static freeStringsChunk(chunkId: number): void {
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "freeStringsChunk::freeing chunkID: " + chunkId, 1);
		}
		if (chunkId > 0) {
			SdkText.strings[SdkText.chunkGroupOfId[chunkId]] = null;
			SdkText.chunkStringOffsets[SdkText.chunkGroupOfId[chunkId]] = null;
		}
		else {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkText.logPrefix + "freeStringsChunk::you are not allowed to free chunkID: " + chunkId, 3);
			}
		}
	}
	static loadTextHeader(data: Int8Array): number {
		let offset: number = 0;
		if (SdkText.midlet === null) {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkText.logPrefix + "loadTextHeader::the MIDlet must be set first, use setMidlet(MIDlet)", 4);
			}
			return 0;
		}
		else {
			offset++;
			let headerStringCount: number = 0;
			if ((headerStringCount = data[0]) !== 1) {
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkText.logPrefix + "loadTextHeader::unsupported text header format:" + headerStringCount, 4);
				}
				return 0;
			}
			else {
				offset++;
				SdkText.chunkConfig = data[1];
				offset++;
				let headerValue: number = (data[2] & 255) << 16;
				offset++;
				headerValue = (headerValue | ((data[3] & 255) << 8));
				offset++;
				SdkText.numFontGlyphs = headerValue | data[4] & 255;
				headerStringCount = (SdkText.readShort(data, 5) << 24 >> 24);
				offset = ((offset + 2) | 0);
				offset++;
				let languageCount: number = data[7];
				offset++;
				let groupSize: number = data[8];
				offset++;
				let chunkCount: number = data[9];
				let storageGroupCount: number = ((groupSize + 1) | 0);
				let chunkTotal: number = ((chunkCount + 1) | 0);
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkText.logPrefix + "loadTextHeader::_chunkConfig = " + SdkText.chunkConfig, 1);
					SdkText.log(SdkText.logPrefix + "loadTextHeader::_textEncodings = " + SdkText.numFontGlyphs, 1);
					SdkText.log(SdkText.logPrefix + "loadTextHeader::numHeaderStrings = " + headerStringCount, 1);
					SdkText.log(SdkText.logPrefix + "loadTextHeader::numLangs = " + languageCount, 1);
					SdkText.log(SdkText.logPrefix + "loadTextHeader::numGroups = " + groupSize, 1);
					SdkText.log(SdkText.logPrefix + "loadTextHeader::numChunks = " + chunkCount, 1);
					SdkText.log(SdkText.logPrefix + "loadTextHeader::numStorageChunks = " + storageGroupCount, 1);
					SdkText.log(SdkText.logPrefix + "loadTextHeader::numActualChunks = " + chunkTotal, 1);
				}
				SdkText.languageEncodings = new Int8Array(languageCount);
				SdkText.languageCodes = new Array(languageCount).fill(null);
				SdkText.fontDifferentiators = new Int8Array(languageCount);
				SdkText.chunkStringCount = new Int16Array(chunkTotal);
				SdkText.chunkFlags = new Int8Array(chunkTotal);
				SdkText.chunkGroupOfId = new Int8Array(chunkTotal);
				SdkText.storageChunkOffsets = Array.from({ length: languageCount }, () => new Int16Array(chunkTotal));
				SdkText.chunkDataSize = new Int16Array(chunkTotal);
				SdkText.chunkOffsetCount = new Int16Array(chunkTotal);
				SdkText.chunkOwners = new Int8Array(chunkTotal);
				SdkText.strings = new Array(storageGroupCount).fill(null);
				SdkText.chunkStringOffsets = new Array(storageGroupCount).fill(null);
				SdkText.chunkStringCount[0] = headerStringCount;
				groupSize = ((((headerStringCount + 1) | 0)) << 16 >> 16);
				for (let languageIndex: number = 0; languageIndex < languageCount; languageIndex++) {
					SdkText.languageCodes[languageIndex] = bytesToString(data, offset, 5).trim();
					offset = ((offset + 5) | 0);
					SdkText.languageEncodings[languageIndex] = data[offset++];
					SdkText.fontDifferentiators[languageIndex] = data[offset++];
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log(SdkText.logPrefix + "loadTextHeader::_languageISOCodes[" + languageIndex + "]: " + SdkText.languageCodes[languageIndex], 1);
						SdkText.log(SdkText.logPrefix + "loadTextHeader::_languageEncodings[" + languageIndex + "]: " + SdkText.languageEncodings[languageIndex], 1);
						SdkText.log(SdkText.logPrefix + "loadTextHeader::_languageFontDifferentiator[" + languageIndex + "]: " + SdkText.fontDifferentiators[languageIndex], 1);
					}
					if (headerStringCount > 0) {
						if (SdkFeatures.isLoggingEnabled()) {
							SdkText.log(SdkText.logPrefix + "loadTextHeader::reading header string offsets for lang: " + languageIndex, 1);
						}
						if (SdkText.chunkStringOffsets[0] === null) {
							SdkText.chunkStringOffsets[0] = new Int16Array(Math.imul(languageCount, groupSize));
						}
						for (let headerIndex: number = 0; headerIndex < groupSize; headerIndex++) {
							SdkText.chunkStringOffsets[0][((Math.imul(languageIndex, groupSize) + headerIndex) | 0)] = SdkText.readShort(data, offset);
							if (SdkFeatures.isLoggingEnabled()) {
								SdkText.log(SdkText.logPrefix + "loadTextHeader::_chunkStringOffsets[HEADER_CHUNK_INDEX][" + (((Math.imul(languageIndex, groupSize) + headerIndex) | 0)) + "]: " + (SdkText.chunkStringOffsets[0][((Math.imul(languageIndex, groupSize) + headerIndex) | 0)] & 65535), 1);
							}
							offset = ((offset + 2) | 0);
						}
					}
				}
				if (headerStringCount > 0) {
					chunkCount = SdkText.chunkStringOffsets[0][((Math.imul(languageCount, groupSize) - 1) | 0)];
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log(SdkText.logPrefix + "loadTextHeader::headerChunkLen = " + chunkCount, 1);
					}
					SdkText.strings[0] = new Int8Array(chunkCount);
					arraycopy(data, offset, SdkText.strings[0], 0, chunkCount);
					offset = ((offset + chunkCount) | 0);
				}
				else {
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log(SdkText.logPrefix + "loadTextHeader::skipping allocation and reading of header string offsets and data because numHeaderStrings == 0", 1);
					}
				}
				for (let chunkId: number = 1; chunkId < chunkTotal; chunkId++) {
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log(SdkText.logPrefix + "loadTextHeader::reading data for chunk: " + chunkId, 1);
					}
					SdkText.chunkFlags[chunkId] = ((data[offset] & 192) << 24 >> 24);
					SdkText.chunkGroupOfId[chunkId] = ((data[offset] & 63) << 24 >> 24);
					SdkText.chunkStringCount[chunkId] = SdkText.readShort(data, ++offset);
					offset = ((offset + 2) | 0);
					if (((SdkText.chunkStringCount[chunkId] + 1) | 0) > SdkText.chunkOffsetCount[SdkText.chunkGroupOfId[chunkId]]) {
						SdkText.chunkOffsetCount[SdkText.chunkGroupOfId[chunkId]] = ((((SdkText.chunkStringCount[chunkId] + 1) | 0)) << 16 >> 16);
						if (SdkFeatures.isLoggingEnabled()) {
							SdkText.log(SdkText.logPrefix + "loadTextHeader::setting _groupMaxNumOffsets[" + SdkText.chunkGroupOfId[chunkId] + "]: " + SdkText.chunkOffsetCount[SdkText.chunkGroupOfId[chunkId]], 1);
						}
					}
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log(SdkText.logPrefix + "loadTextHeader::_chunkFlags[" + chunkId + "]: " + SdkText.chunkFlags[chunkId], 1);
						SdkText.log(SdkText.logPrefix + "loadTextHeader::_chunkGroup[" + chunkId + "]: " + SdkText.chunkGroupOfId[chunkId], 1);
						SdkText.log(SdkText.logPrefix + "loadTextHeader:: _numStringsPerChunk[" + chunkId + "]: " + SdkText.chunkStringCount[chunkId], 1);
					}
					for (let languageIndex: number = 0; languageIndex < languageCount; languageIndex++) {
						SdkText.storageChunkOffsets[languageIndex][chunkId] = SdkText.readShort(data, offset);
						if (SdkFeatures.isLoggingEnabled()) {
							SdkText.log(SdkText.logPrefix + "loadTextHeader::chunkSize[" + languageIndex + "][" + chunkId + "]: " + (SdkText.storageChunkOffsets[languageIndex][chunkId] & 65535), 1);
						}
						if ((SdkText.storageChunkOffsets[languageIndex][chunkId] & 65535) > (SdkText.chunkDataSize[SdkText.chunkGroupOfId[chunkId]] & 65535)) {
							SdkText.chunkDataSize[SdkText.chunkGroupOfId[chunkId]] = SdkText.storageChunkOffsets[languageIndex][chunkId];
							if (SdkFeatures.isLoggingEnabled()) {
								SdkText.log(SdkText.logPrefix + "loadTextHeader::setting _groupMaxSize[" + SdkText.chunkGroupOfId[chunkId] + "]: " + (SdkText.chunkDataSize[SdkText.chunkGroupOfId[chunkId]] & 65535), 1);
							}
						}
						offset = ((offset + 2) | 0);
					}
				}
				if (SdkText.languageEncodings[SdkText.currentLanguage] > 1) {
					SdkText.loadFontChunk(SdkText.languageEncodings[SdkText.currentLanguage]);
				}
				return languageCount;
			}
		}
	}
	static getHeaderString(languageIndex: number, stringId: number, result: SdkString): SdkString {
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "getHeaderString::getting stringID: " + stringId + " for language: " + languageIndex, 1);
			if (stringId >= SdkText.chunkStringCount[0]) {
				SdkText.log(SdkText.logPrefix + "getHeaderString::stringID: " + stringId + " is not in the header strings chunk, use getString(int,SDKString)", 4);
			}
		}
		languageIndex = ((stringId + Math.imul((((SdkText.chunkStringCount[0] + 1) | 0)), languageIndex)) | 0);
		if (result === null) {
			result = new SdkString();
		}
		result.data = SdkText.strings[0];
		result.offset = SdkText.chunkStringOffsets[0][languageIndex];
		result.length = ((((SdkText.chunkStringOffsets[0][((languageIndex + 1) | 0)] - SdkText.chunkStringOffsets[0][languageIndex]) | 0) >> 1) << 16 >> 16);
		result.flags = 0;
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "getHeaderString::string.toString(): " + result.toString(), 1);
			SdkText.log(SdkText.logPrefix + "getHeaderString::string.off: " + result.offset, 1);
			SdkText.log(SdkText.logPrefix + "getHeaderString::string.len: " + result.length, 1);
			SdkText.log(SdkText.logPrefix + "getHeaderString::string.encoding: " + result.flags, 1);
		}
		return result;
	}
	static getString(stringId: number, result: SdkString): SdkString {
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "getString::getting stringID: " + stringId, 1);
			if (SdkText.currentLanguage === -1) {
				SdkText.log(SdkText.logPrefix + "getString::language must be selected first!", 4);
			}
		}
		let chunkId: number = -1;
		let cumulative: number = 0;
		for (let index: number = 0; index < SdkText.chunkStringCount.length; index++) {
			cumulative = (((cumulative + SdkText.chunkStringCount[index]) | 0) << 16 >> 16);
			if (stringId < cumulative) {
				chunkId = index;
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkText.logPrefix + "getString::found stringID: " + stringId + " in chunkID: " + index, 1);
				}
				break;
			}
		}
		if (SdkFeatures.isLoggingEnabled()) {
			if (chunkId === -1) {
				SdkText.log(SdkText.logPrefix + "getString::failed to get the chunkID for stringID: " + stringId, 4);
			}
			else {
				if (chunkId === 0) {
					SdkText.log(SdkText.logPrefix + "getString::use getHeaderString(int,int,SDKString) to load stringID: " + stringId, 4);
				}
			}
		}
		let chunkOffset: number = ((stringId - (((cumulative - SdkText.chunkStringCount[chunkId]) | 0))) | 0);
		let groupId: number = SdkText.chunkGroupOfId[chunkId];
		if (chunkOffset < 0 || chunkOffset + 1 >= SdkText.chunkStringOffsets[groupId].length) {
			throw new IndexOutOfBoundsException("string id " + stringId + " is out of range");
		}
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "getString::calculated first stringID for this chunk as: " + (((cumulative - SdkText.chunkStringCount[chunkId]) | 0)), 1);
			SdkText.log(SdkText.logPrefix + "getString::chunkOffset: " + chunkOffset, 1);
			SdkText.log(SdkText.logPrefix + "getString::groupID: " + groupId, 1);
			if (SdkText.strings[groupId] === null) {
				SdkText.log(SdkText.logPrefix + "getString::first load chunkID: " + chunkId + " before you can get stringID: " + stringId, 4);
			}
		}
		if (result === null) {
			result = new SdkString();
		}
		result.data = SdkText.strings[groupId];
		result.offset = SdkText.chunkStringOffsets[groupId][chunkOffset] & 65535;
		result.length = (((((SdkText.chunkStringOffsets[groupId][((chunkOffset + 1) | 0)] & 65535) - (SdkText.chunkStringOffsets[groupId][chunkOffset] & 65535)) | 0)) << 16 >> 16);
		let encoding: number = SdkText.languageEncodings[SdkText.currentLanguage];
		if ((SdkText.chunkFlags[chunkId] & -64) !== 0) {
			encoding = 0;
		}
		if (encoding === 0) {
			result.length = ((result.length >> 1) << 16 >> 16);
		}
		result.flags = encoding;
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "getString::string.toString(): " + result.toString(), 1);
			SdkText.log(SdkText.logPrefix + "getString::string.off: " + result.offset, 1);
			SdkText.log(SdkText.logPrefix + "getString::string.len: " + result.length, 1);
			SdkText.log(SdkText.logPrefix + "getString::string.encoding: " + result.flags, 1);
		}
		return result;
	}
	static getCurrentLanguage(): number {
		return SdkText.currentLanguage;
	}
	static setCurrentLanguage(languageIndex: number): boolean {
		let previousLanguage: number = SdkText.currentLanguage;
		if (SdkText.languageCodes !== null && languageIndex >= 0 && languageIndex < SdkText.languageCodes.length) {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkText.logPrefix + "setCurrentLanguage::setting _langIndex: " + languageIndex, 1);
			}
			SdkText.currentLanguage = (languageIndex << 24 >> 24);
		}
		else {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkText.logPrefix + "setCurrentLanguage::invalid index: " + languageIndex, 4);
			}
		}
		if (SdkText.currentLanguage !== previousLanguage) {
			SdkText.changingLanguage = true;
			for (let chunkId: number = 1; chunkId < SdkText.chunkGroupOfId.length; chunkId++) {
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkText.logPrefix + "setCurrentLanguage::checking storage for chunkID: " + chunkId + " ,groupID: " + SdkText.chunkGroupOfId[chunkId], 1);
				}
				if (SdkText.isChunkLoaded(chunkId) && (SdkText.chunkFlags[chunkId] & 64) === 0) {
					SdkText.loadStringsChunk(chunkId);
				}
			}
			SdkText.changingLanguage = false;
			if (SdkText.languageEncodings[SdkText.currentLanguage] !== SdkText.languageEncodings[previousLanguage] && SdkText.languageEncodings[SdkText.currentLanguage] > 1) {
				SdkText.loadFontChunk(SdkText.languageEncodings[SdkText.currentLanguage]);
			}
		}
		return SdkText.currentLanguage === languageIndex;
	}
	static setMidlet(midletApp: MIDlet): void {
		SdkText.midlet = midletApp;
	}
	static chooseLanguage(requested: number): number {
		if (SdkText.midlet === null && SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "chooseLanguage::midlet instance not set. Call setMidlet() first!", 4);
		}
		if (SdkText.languageCodes.length === 1) {
			return 0;
		}
		else {
			let forceLocale: string = SdkText.midlet.getAppProperty("Force-Locale");
			let codes: string[] = new Array(SdkText.languageCodes.length).fill(null);
			arraycopy(SdkText.languageCodes, 0, codes, 0, codes.length);
			if (requested === -1) {
				let mode: number = 0;
				if (forceLocale === null) {
					mode = 2;
				}
				else {
					if ((forceLocale = forceLocale.toLowerCase().trim()).length < 2) {
						mode = 2;
					}
					else {
						if ((forceLocale === "menu")) {
							mode = 1;
						}
						else {
							if (forceLocale.startsWith("auto-")) {
								mode = 3;
								forceLocale = forceLocale.substring(5);
							}
							else {
								mode = 4;
							}
						}
					}
				}
				for (let index: number = 0; index < codes.length; index++) {
					if (codes[index].length > 2) {
						let prefix: string = codes[index].substring(0, 2);
						let unique: boolean = true;
						for (let otherIndex: number = 0; otherIndex < codes.length; otherIndex++) {
							if (index !== otherIndex && codes[otherIndex].startsWith(prefix)) {
								unique = false;
								break;
							}
						}
						if (unique) {
							codes[index] = prefix;
						}
					}
				}
				if (mode === 2 || mode === 3 || mode === 1) {
					requested = SdkText.detectLanguage(codes);
					if (mode === 1 && requested > -1) {
						requested = ((-1 - requested) | 0);
					}
				}
				if (mode === 4 || mode === 3 && requested === -1) {
					for (let matchIndex: number = 0; matchIndex < codes.length; matchIndex++) {
						if (forceLocale.startsWith(codes[matchIndex])) {
							requested = matchIndex;
							break;
						}
					}
				}
				if (mode === 4 && requested === -1) {
					requested = SdkText.detectLanguage(codes);
				}
			}
			return requested;
		}
	}
	static getFontDifferentiator(languageIndex: number): number {
		if (SdkFeatures.isLoggingEnabled() && languageIndex < 0 || languageIndex >= SdkText.fontDifferentiators.length) {
			SdkText.log(SdkText.logPrefix + "getFontDifferentiator::langIndex out of bounds", 4);
		}
		return SdkText.fontDifferentiators[languageIndex];
	}
	static setGraphics(gfx: Graphics): void {
		SdkText.graphics = null;
		SdkText.graphics = gfx;
		if (SdkText.font !== null && (SdkText.fontHeader[10] & 4) !== 0 && SdkFeatures.isSystemFontEnabled()) {
			SdkText.graphics.setFont((SdkText.font as unknown as Font));
		}
	}
	static log(message: string, severity: number): void {
		if (severity >= SdkFeatures.configMode() && SdkFeatures.isLoggingEnabled()) {
			if (SdkText.logBuffer === null) {
				SdkText.logBuffer = new StringBuffer();
			}
			SdkText.logBuffer.setLength(0);
			switch (severity) {
				case 1:
					SdkText.logBuffer.append("I:");
					break;
				case 2:
					SdkText.logBuffer.append("S:");
					break;
				case 3:
					SdkText.logBuffer.append("W:");
					break;
				case 4:
					SdkText.logBuffer.append("E:");
			}
			if (severity >= 3) {
				let alert: Alert = null!;
				(alert = new Alert("", message, null, AlertType.ERROR)).setTimeout(-2);
				Display.getDisplay(SdkText.midlet).setCurrent(alert);
			}
			SdkText.logBuffer.append(message);
			console.log(SdkText.logBuffer.toString());
		}
	}
	static readShort(data: Int8Array, offset: number): number {
		return (((data[offset] & 255) << 8 | data[((offset + 1) | 0)] & 255) << 16 >> 16);
	}
	static toCharset(charCode: number): number {
		let index: number = 0;
		if ((index = SdkText.glyphWidth(SdkText.glyphIndexTable, charCode, 0, ((SdkText.glyphIndexTable.length - 1) | 0))) === SdkText.glyphIndexTable.length && SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "utf2iso::character code: " + charCode + " is not defined in encoding: " + SdkText.languageEncodings[SdkText.currentLanguage], 3);
		}
		return ((((index + 128) | 0)) << 16 >> 16);
	}
	static fromCharset(charCode: number): number {
		if ((charCode < 128 || charCode > 255) && SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "iso2utf::character code: " + charCode + " is out of bounds for this method (must be in the range 128-255)", 4);
		}
		return SdkText.glyphIndexTable[((charCode - 128) | 0)];
	}
	static detectLanguage(codes: string[]): number {
		let language: number = -1;
		let locale: string = null!;
		if ((locale = getProperty("microedition.locale")) !== null) {
			let localeLength: number = 0;
			if ((localeLength = (locale = locale.toLowerCase().trim()).length) < 2) {
				return -1;
			}
			for (let index: number = 0; index < codes.length; index++) {
				if (codes[index].length > localeLength && codes[index].startsWith(locale)) {
					language = -1;
					break;
				}
				if (locale.startsWith(codes[index])) {
					language = (index << 24 >> 24);
					break;
				}
			}
		}
		return language;
	}
	static flushLog(): void {
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "drawRegion::the function is called with short[] parameter on non-Nokia-API phone!", 4);
		}
	}
	static drawGlyph(image: Image, sourceX: number, sourceY: number, width: number, height: number, transform: number, x: number, y: number, anchor: number): void {
		SdkText.graphics.drawRegion(image, sourceX, sourceY, width, height, transform, x, y, anchor);
	}
	static glyphWidth(table: Int16Array, value: number, low: number, high: number): number {
		if (low > high) {
			return table.length;
		}
		else {
			let middle: number = ((low + high) | 0) >> 1;
			let middleValue: number = table[middle] & 65535;
			if (value === middleValue) {
				return middle;
			}
			else {
				return value < middleValue ? SdkText.glyphWidth(table, value, low, ((middle - 1) | 0)) : SdkText.glyphWidth(table, value, ((middle + 1) | 0), high);
			}
		}
	}
	static loadFontChunk(encoding: number): void {
		if (SdkFeatures.isLoggingEnabled()) {
			if (encoding < 2) {
				SdkText.log(SdkText.logPrefix + "loadCharMap::encoding: " + encoding + " should not be searched in the charmap binary!", 4);
			}
			else {
				SdkText.log(SdkText.logPrefix + "loadCharMap::loading charmap for encoding: " + encoding, 1);
			}
		}
		let skippedCount: number = 0;
		for (let bit: number = 1; bit < encoding; bit++) {
			if ((SdkText.numFontGlyphs & 1 << bit) !== 0) {
				skippedCount++;
			}
		}
		if (SdkText.glyphIndexTable === null) {
			SdkText.glyphIndexTable = new Int16Array(128);
		}
		let stream: InputStream = null;
		try {
			SdkText.skip(stream = new ByteArrayInputStream(loadResource("/charmap")), (skippedCount << 8));
			for (let charIndex: number = 0; charIndex < 128; charIndex++) {
				SdkText.tempShort[0] = (stream.read() << 24 >> 24);
				SdkText.tempShort[1] = (stream.read() << 24 >> 24);
				SdkText.glyphIndexTable[charIndex] = SdkText.readShort(SdkText.tempShort, 0);
			}
			stream.close();
		}
		catch (error) {
			if (error instanceof IOException) {
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkText.logPrefix + "loadCharMap::IOException, e.msg: " + (error as any).message, 4);
				}
			}
			else {
				throw error;
			}
		}
		if (SdkText.font !== null && (SdkText.fontHeader[10] & 4) === 0 && (SdkFeatures.isRectangleFontEnabled() || SdkFeatures.isFreeTypeFontEnabled())) {
			SdkText.loadChunksForLanguage();
		}
	}
	static loadChunksForLanguage(): void {
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "refreshISOOffsets::mapping offsets for encoding: " + SdkText.languageEncodings[SdkText.currentLanguage], 1);
		}
		for (let charCode: number = 128; charCode < 256; charCode++) {
			SdkText.headerStringOffsets[charCode] = (SdkText.glyphWidth(SdkText.languageChunkGroups, SdkText.fromCharset(charCode), 0, ((SdkText.languageChunkGroups.length - 1) | 0)) << 16 >> 16);
		}
		SdkText.fontHeader[11] = SdkText.languageEncodings[SdkText.currentLanguage];
	}
	static parseFontChunk(data: Int8Array): any[] {
		let offset: number = 0;
		let fontVersion: number = data[0];
		if (SdkFeatures.isLoggingEnabled() && (fontVersion & 63) !== 33) {
			SdkText.log(SdkText.logPrefix + "loadFontChunk::unsupported font version:" + fontVersion, 4);
		}
		let rectangleFont: boolean = (fontVersion & 128) !== 0;
		let compactGlyphs: boolean = (fontVersion & 64) !== 0;
		if (rectangleFont && !SdkFeatures.isFreeTypeFontEnabled()) {
			SdkText.log(SdkText.logPrefix + "loadFontChunk::detected rectangle font but support disabled, use SDKConfig to enable", 4);
		}
		let header: Int8Array = new Int8Array(12);
		arraycopy(data, 1, header, 0, 7);
		offset = (8 << 24 >> 24);
		header[7] = 0;
		header[8] = header[0];
		header[9] = header[1];
		if (rectangleFont) {
			header[10] = 2;
		}
		else {
			header[10] = 1;
		}
		if (compactGlyphs) {
			header[10] = ((header[10] | -128) << 24 >> 24);
		}
		let charCount: number = SdkText.readShort(data, 8);
		offset = ((offset + 2) | 0);
		let totalGlyphs: number = SdkText.readShort(data, 10);
		offset = ((offset + 2) | 0);
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkText.logPrefix + "loadFontChunk::fontVersion = " + fontVersion, 1);
			SdkText.log(SdkText.logPrefix + "loadFontChunk::textDirection = " + header[0], 1);
			SdkText.log(SdkText.logPrefix + "loadFontChunk::lineDirection = " + header[1], 1);
			SdkText.log(SdkText.logPrefix + "loadFontChunk::leading = " + header[2], 1);
			SdkText.log(SdkText.logPrefix + "loadFontChunk::ascent = " + header[3], 1);
			SdkText.log(SdkText.logPrefix + "loadFontChunk::descent = " + header[4], 1);
			SdkText.log(SdkText.logPrefix + "loadFontChunk::space space= " + header[5], 1);
			SdkText.log(SdkText.logPrefix + "loadFontChunk::char space= " + header[6], 1);
			SdkText.log(SdkText.logPrefix + "loadFontChunk::numChars = " + charCount, 1);
			SdkText.log(SdkText.logPrefix + "loadFontChunk::numTotalGlyphs = " + totalGlyphs, 1);
		}
		let charIndexTable: Int16Array = new Int16Array(charCount);
		for (let index: number = 0; index < charCount; index++) {
			charIndexTable[index] = SdkText.readShort(data, offset);
			offset = ((offset + 2) | 0);
		}
		let glyphOffsets: Int16Array = new Int16Array(((charCount + 1) | 0));
		for (let index: number = 0; index < charCount; index++) {
			glyphOffsets[index] = SdkText.readShort(data, offset);
			offset = ((offset + 2) | 0);
		}
		glyphOffsets[charCount] = -1;
		let glyphStride: number = 6;
		if (rectangleFont && SdkFeatures.isFreeTypeFontEnabled()) {
			glyphStride = compactGlyphs ? 4 : 5;
		}
		let dataSize: number = (((charCount << 1) + Math.imul(totalGlyphs, glyphStride)) | 0);
		let glyphData: Int8Array = new Int8Array(dataSize);
		arraycopy(data, offset, glyphData, 0, dataSize);
		offset = ((offset + dataSize) | 0);
		let colorTable: Int32Array = null;
		if (rectangleFont && SdkFeatures.isFreeTypeFontEnabled()) {
			let colorCount: number = data[offset++];
			colorTable = new Int32Array(colorCount);
			for (let colorIndex: number = 0; colorIndex < colorCount; colorIndex++) {
				colorTable[colorIndex] = (data[offset] & 255) << 24 | (data[((offset + 1) | 0)] & 255) << 16 | (data[((offset + 2) | 0)] & 255) << 8 | data[((offset + 3) | 0)] & 255;
				offset = ((offset + 4) | 0);
			}
		}
		let isoIndexes: Int16Array = new Int16Array(256);
		for (let charCode: number = 0; charCode < 128; charCode++) {
			isoIndexes[charCode] = (SdkText.glyphWidth(charIndexTable, charCode, 0, ((charCount - 1) | 0)) << 16 >> 16);
		}
		header[11] = 1;
		return [header, charIndexTable, isoIndexes, glyphOffsets, glyphData, colorTable];
	}
	static readFully(stream: InputStream, buffer: Int8Array, offset: number, count: number): number {
		try {
			return stream.read(buffer, offset, count);
		}
		catch (error) {
			if (error instanceof IOException) {
				throw new IllegalStateException(error);
			}
			else {
				throw error;
			}
		}
	}
	static {
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.logPrefix = "SDKUtils::";
		}
	}
	constructor() {
	}
}
