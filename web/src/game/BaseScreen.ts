// Generated from BaseScreen.java by tools/web/JavaToTs.java; keep this file in sync.
import { Graphics } from '../runtime';
import { GameCanvas } from './GameCanvas';
import { Animator } from './Animator';
import { SdkString } from './SdkString';
import { SpriteRenderer } from './SpriteRenderer';
import { TitleScreen } from './TitleScreen';
import { GameScreen } from './GameScreen';
import { Neighborhood } from './Neighborhood';
import { Lot } from './Lot';
import { FixedMath } from './FixedMath';

export abstract class BaseScreen {
	canvas: GameCanvas = null!;
	hitRect: Int32Array = null!;
	listY: number = 0;
	visibleLineCount: number = 0;
	textLineHeight: number = 0;
	panelWidth: number = 0;
	dialogWidth: number = 0;
	panelHeight: number = 0;
	rowHeight: number = 0;
	static rectScratch: Int32Array = new Int32Array(6);
	valueSlots: Int16Array = new Int16Array(11);
	leftArrowAnim: Animator = null!;
	rightArrowAnim: Animator = null!;
	itemCount: number = 0;
	lineHeight: number = 0;
	scrollOffset: number = 0;
	visibleRowCount: number = 0;
	selectedIndex: number = 0;
	menuScroll: number = 0;
	matchesInput(keyState: number, prevKeyState: number, keyMask: number, prevKeyMask: number): boolean {
		if ((prevKeyState & prevKeyMask) === 0 && (keyState & keyMask) === 0) {
			return false;
		}
		else {
			this.canvas.releaseSoftKeys();
			this.canvas.clearAction(keyMask);
			return true;
		}
	}
	abstract setLanguage(language: number): void;
	abstract stop(): void;
	abstract pause(): void;
	abstract render(graphics: Graphics): void;
	abstract update(delta: number, flags: number): void;
	abstract keyPressed(keyCode: number): void;
	abstract onTick(delta: number): void;
	drawPanel(graphics: Graphics, style: number): void {
		let canvas: GameCanvas = this.canvas;
		this.drawTextBlock(graphics, style, 0, 0, canvas.getWidth(), ((canvas.getHeight() - 15) | 0));
	}
	drawPanelBelow(graphics: Graphics, style: number, top: number): void {
		let canvas: GameCanvas = this.canvas;
		this.drawTextBlock(graphics, style, 0, top, canvas.getWidth(), ((((canvas.getHeight() - 15) | 0) - top) | 0));
	}
	initLayout(): void {
		let canvas: GameCanvas = this.canvas;
		this.textLineHeight = canvas.lineHeight(1);
		this.panelWidth = 216;
		this.dialogWidth = ((Math.imul(this.panelWidth, 4) / 5) | 0);
		this.listY = ((((Math.imul(this.panelWidth, 17) / 20) | 0) - BaseScreen.scrollbarWidth()) | 0);
		this.panelHeight = 192;
		this.visibleLineCount = ((((Math.imul(canvas.getHeight(), 3) / 5) | 0) / canvas.lineHeight(1)) | 0);
		this.rowHeight = ((canvas.lineHeight(1) + 2) | 0);
		this.leftArrowAnim = new Animator();
		this.rightArrowAnim = new Animator();
		this.leftArrowAnim.play(560, 4);
		this.rightArrowAnim.play(562, 4);
	}
	showLeftArrow(): void {
		this.leftArrowAnim.play(559, 16);
	}
	showRightArrow(): void {
		this.rightArrowAnim.play(561, 16);
	}
	advanceArrows(delta: number): void {
		this.leftArrowAnim.advance(delta);
		this.rightArrowAnim.advance(delta);
		if (!this.leftArrowAnim.isRunning()) {
			this.leftArrowAnim.play(560, 20);
		}
		if (!this.rightArrowAnim.isRunning()) {
			this.rightArrowAnim.play(562, 20);
		}
	}
	setValue(index: number, value: number): void {
		this.valueSlots[index] = (value << 16 >> 16);
	}
	drawTextBlock(graphics: Graphics, textId: number, x: number, y: number, width: number, height: number): void {
		this.drawParagraphs(graphics, this.textBlock(textId), x, y, width, height);
	}
	drawParagraphs(graphics: Graphics, text: Int16Array, x: number, y: number, width: number, height: number): void {
		let cursor: Int32Array = BaseScreen.rectScratch;
		BaseScreen.rectScratch[0] = x;
		cursor[1] = y;
		cursor[2] = width;
		cursor[3] = 0;
		this.layoutParagraphs(null, text, cursor);
		cursor[0] = x;
		cursor[2] = width;
		let measuredHeight: number = cursor[3];
		if (this.totalMeasurement(text) !== 0) {
			cursor[1] = y;
			cursor[3] = ((height - measuredHeight) | 0);
		}
		else {
			cursor[1] = ((y + (((height - measuredHeight) | 0) >> 1)) | 0);
			cursor[3] = measuredHeight;
		}
		this.layoutParagraphs(graphics, text, cursor);
	}
	layoutParagraphs(graphics: Graphics, text: Int16Array, cursor: Int32Array): void {
		let x: number = cursor[0];
		let y: number = cursor[1];
		let width: number = cursor[2];
		let height: number = cursor[3];
		let totalHeight: number = 0;
		let totalExtra: number = 0;
		for (let index: number = 0; index < text.length; index = (((index + 2) | 0) << 24 >> 24)) {
			let kind: number = text[index];
			let value: number = text[((index + 1) | 0)];
			cursor[4] = value;
			this.renderElement(null, kind, cursor);
			let lineHeight: number = cursor[3];
			totalExtra = ((totalExtra + this.measureParagraph(kind, value)) | 0);
			totalHeight = ((totalHeight + lineHeight) | 0);
		}
		if (graphics !== null) {
			let limit: number = height;
			if (totalExtra > 0) {
				limit = ((height + totalHeight) | 0);
			}
			cursor[0] = x;
			cursor[1] = y;
			cursor[2] = width;
			let used: number = 0;
			for (let row: number = 0; row < text.length; row = (((row + 2) | 0) << 24 >> 24)) {
				let kind2: number = text[row];
				let value2: number = text[((row + 1) | 0)];
				cursor[4] = value2;
				let extra: number = 0;
				if ((extra = this.measureParagraph(kind2, value2)) !== 0) {
					let allocated: number = ((Math.imul(height, extra) / totalExtra) | 0);
					cursor[3] = allocated;
					this.renderElement(graphics, kind2, cursor);
				}
				else {
					cursor[3] = ((limit - used) | 0);
					this.renderElement(graphics, kind2, cursor);
				}
				used = ((used + cursor[3]) | 0);
			}
		}
		if (graphics === null) {
			cursor[3] = totalHeight;
		}
		else {
			cursor[3] = ((height + totalHeight) | 0);
		}
	}
	measureParagraph(kind: number, value: number): number {
		let result: number = 0;
		if (kind === 22) {
			return value;
		}
		else {
			if (kind === 27) {
				result = 1;
			}
			else {
				if (kind === 29) {
					result = this.totalMeasurement(this.textBlock(value));
				}
			}
			return result;
		}
	}
	totalMeasurement(text: Int16Array): number {
		let total: number = 0;
		for (let index: number = 0; index < text.length; index = (((index + 2) | 0) << 24 >> 24)) {
			let kind: number = text[index];
			let value: number = text[((index + 1) | 0)];
			total = ((total + this.measureParagraph(kind, value)) | 0);
		}
		return total;
	}
	renderElement(graphics: Graphics, elementType: number, cursor: Int32Array): void {
		let canvas: GameCanvas = this.canvas;
		let x: number = cursor[0];
		let y: number = cursor[1];
		let width: number = cursor[2];
		let height: number = cursor[3];
		let value: number = cursor[4];
		let advance: number = 0;
		switch (elementType) {
			case 0:
				if (graphics !== null) {
					graphics.setColor(0);
					graphics.drawRect(x, y, width, height);
				}
				break;
			case 1:
			case 2:
			case 3:
			case 4:
				if (elementType === 4) {
					width = this.panelWidth;
					x = ((this.canvas.getWidth() - width) | 0) >> 1;
				}
				if (graphics !== null) {
					this.drawDialogFrame(graphics, x, y, width, height, value, elementType);
				}
				x = ((x + 5) | 0);
				y = ((y + 22) | 0);
				width = ((width - 10) | 0);
				advance = 27;
				break;
			case 5:
				if (graphics !== null) {
					BaseScreen.drawThickBorder(graphics, x, y, width, height);
				}
				break;
			case 6:
				if (graphics !== null) {
					this.drawWindowBox(graphics, x, y, width, height);
				}
				x = ((x + 8) | 0);
				y = ((y + 8) | 0);
				width = ((width - 16) | 0);
				advance = 16;
				break;
			case 7:
				if (graphics !== null) {
					this.drawRaisedBox(graphics, x, y, width, height);
				}
				x = ((x + 4) | 0);
				y = ((y + 4) | 0);
				width = ((width - 8) | 0);
				advance = 8;
			case 8:
			default:
				break;
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				let label: SdkString = null!;
				if (graphics !== null && (label = this.textFor(value)) !== null) {
					let anchorX: number = x;
					let anchorY: number = ((y + 1) | 0);
					let anchor: number = 20;
					if (elementType === 10 || elementType === 13) {
						anchorX = ((x + width) | 0);
						anchor = 24;
					}
					else {
						if (elementType === 11 || elementType === 14) {
							anchorX = ((x + (width >> 1)) | 0);
							anchor = 17;
						}
					}
					let alignFlag: number = elementType !== 12 && elementType !== 13 && elementType !== 14 ? 1 : 0;
					canvas.drawString(label, alignFlag, anchorX, anchorY, anchor);
				}
				break;
			case 15:
				advance = this.rowHeight;
				y = ((y + advance) | 0);
				break;
			case 16:
			case 17:
				advance = Math.imul(Math.max(value, canvas.wrappedLineCount()), this.textLineHeight);
				if (graphics !== null) {
					if (elementType === 17) {
						advance = height;
					}
					canvas.drawWrappedText(1, ((x + (width >> 1)) | 0), ((y + (advance >> 1)) | 0), 3);
				}
				y = ((y + advance) | 0);
				break;
			case 18:
				advance = 1;
				if (graphics !== null) {
					let rectX: number = ((x + 1) | 0);
					let rectW: number = ((width - 3) | 0);
					SpriteRenderer.setColor(graphics, value);
					graphics.fillRect(rectX, y, rectW, 1);
				}
				y++;
				break;
			case 19:
				let fullW: number = canvas.getWidth();
				let topY: number = ((y + 3) | 0);
				let innerH: number = ((((((canvas.getHeight() - 15) | 0) - 3) | 0) - topY) | 0);
				if (graphics !== null) {
					SpriteRenderer.setColor(graphics, 77);
					graphics.fillRect(0, y, fullW, 3);
					SpriteRenderer.setColor(graphics, 76);
					graphics.fillRect(0, topY, fullW, innerH);
					y = topY;
					advance = ((height - innerH) | 0);
				}
				else {
					advance = height;
					y = topY;
				}
				x = 0;
				width = fullW;
				break;
			case 20:
				if (graphics !== null) {
					let barHeight: number = ((this.rowHeight + 2) | 0);
					let panelW: number = canvas.getWidth();
					let lineY: number = ((y - 2) | 0);
					SpriteRenderer.setColor(graphics, 77);
					graphics.fillRect(0, lineY, panelW, 1);
					graphics.fillRect(0, ((((lineY + barHeight) | 0) - 1) | 0), canvas.getWidth(), 1);
					SpriteRenderer.setColor(graphics, 1);
					graphics.fillRect(0, ((lineY + 1) | 0), panelW, ((barHeight - 2) | 0));
					let arrowX1: number = ((((((x + width) | 0) - 25) | 0) + 3) | 0);
					let arrowX2: number = ((((x + width) | 0) + 3) | 0);
					SpriteRenderer.drawFrame(graphics, 660, 0, arrowX1, y);
					SpriteRenderer.drawFrame(graphics, 659, 0, arrowX2, y);
				}
				break;
			case 21:
				let rowHeight: number = ((this.rowHeight + 8) | 0);
				if (graphics !== null) {
					let innerW: number = ((Math.imul(width, 4) / 5) | 0);
					let innerX: number = ((x + (((width - innerW) | 0) >> 1)) | 0);
					this.drawWindowBox(graphics, innerX, y, innerW, rowHeight);
					let labelX: number = ((innerX + (innerW >> 1)) | 0);
					let labelY: number = ((((y + (rowHeight >> 1)) | 0) + 2) | 0);
					canvas.drawString(this.textFor(value), 0, labelX, labelY, 3);
				}
				advance = rowHeight;
				y = ((y + rowHeight) | 0);
				break;
			case 22:
				if (graphics !== null) {
					advance = height;
					y = ((y + height) | 0);
				}
				break;
			case 23:
				x = ((x + value) | 0);
				width = ((width - (value << 1)) | 0);
				y = ((y + value) | 0);
				advance = value << 1;
				break;
			case 24:
				x = ((x + value) | 0);
				width = ((width - (value << 1)) | 0);
				break;
			case 25:
				y = ((y + value) | 0);
				advance = value << 1;
				break;
			case 26:
				advance = value;
				y = ((y + value) | 0);
				break;
			case 27:
				if (graphics !== null) {
					this.drawScrollList(graphics, x, y, width, height, value);
					advance = height;
					y = ((y + height) | 0);
				}
				break;
			case 28:
				this.drawTextBox(graphics, cursor);
				advance = cursor[3];
				y = ((y + advance) | 0);
				break;
			case 29:
				let textBlock: Int16Array = this.textBlock(value);
				this.layoutParagraphs(graphics, textBlock, cursor);
				advance = cursor[3];
				y = ((y + advance) | 0);
				break;
			case 30:
				advance = (this as unknown as TitleScreen).drawTextBox_2(graphics, x, y, width);
				y = ((y + advance) | 0);
				break;
			case 31:
			case 33:
			case 36:
				advance = this.drawMenuList(graphics, x, y, width, elementType);
				y = ((y + advance) | 0);
				break;
			case 32:
				advance = (this as unknown as GameScreen).drawSkillHeader(graphics, x, y, width);
				y = ((y + advance) | 0);
				break;
			case 34:
				advance = (this as unknown as GameScreen).drawSkillRow(graphics, x, y, width, value);
				y = ((y + advance) | 0);
				break;
			case 35:
				advance = (this as unknown as GameScreen).drawFieldRow(graphics, x, y, width);
				y = ((y + advance) | 0);
				break;
			case 37:
				(this as unknown as GameScreen).drawMenuIcon(graphics, cursor);
				return;
			case 38:
				advance = (this as unknown as GameScreen).drawObjectIconRow(graphics, x, y, width);
				y = ((y + advance) | 0);
		}
		cursor[0] = x;
		cursor[1] = y;
		cursor[2] = width;
		cursor[3] = advance;
	}
	drawDialogFrame(graphics: Graphics, x: number, y: number, width: number, height: number, titleId: number, style: number): void {
		let canvas: GameCanvas = this.canvas;
		if (style === 4) {
			this.drawListBox(graphics, x, y, width, height);
		}
		let clipX: number = graphics.getClipX();
		let clipY: number = graphics.getClipY();
		let clipW: number = graphics.getClipWidth();
		let clipH: number = graphics.getClipHeight();
		let capW: number = SpriteRenderer.frameWidth(582, 0);
		let capH: number = SpriteRenderer.frameHeight(582, 0);
		SpriteRenderer.drawFrame(graphics, 582, 0, x, y);
		SpriteRenderer.drawFrame(graphics, 582, 2, ((((x + width) | 0) - capW) | 0), y);
		graphics.setClip(((x + capW) | 0), clipY, ((width - (capW << 1)) | 0), clipH);
		SpriteRenderer.drawFrame(graphics, 582, 1, ((x + capW) | 0), y);
		graphics.setClip(clipX, clipY, clipW, clipH);
		if (style !== 4) {
			SpriteRenderer.setColor(graphics, 0);
			graphics.fillRect(x, ((y + capH) | 0), 1, ((height - capH) | 0));
			graphics.fillRect(((((x + width) | 0) - 1) | 0), ((y + capH) | 0), 1, ((height - capH) | 0));
			graphics.fillRect(x, ((((y + height) | 0) - 1) | 0), width, 1);
			SpriteRenderer.setColor(graphics, 76);
			graphics.fillRect(((x + 1) | 0), ((y + capH) | 0), ((width - 2) | 0), ((((height - capH) | 0) - 1) | 0));
		}
		if (titleId !== -1) {
			let titleX: number = ((x + (width >> 1)) | 0);
			let titleY: number = ((y + 5) | 0);
			canvas.drawString(this.textFor(titleId), 0, titleX, titleY, 17);
		}
		if (style === 2) {
			let arrowY: number = ((y + 3) | 0);
			let leftArrowX: number = ((x + 4) | 0);
			let rightArrowX: number = ((((x + width) | 0) - 4) | 0);
			if (GameCanvas.landscape) {
				leftArrowX = ((x + 56) | 0);
				rightArrowX = ((((x + width) | 0) - 56) | 0);
			}
			if ((this as unknown as GameScreen).friendLevel !== 0 || GameScreen.screenState !== 11 && GameScreen.screenState !== 10) {
				this.leftArrowAnim.draw(graphics, leftArrowX, arrowY);
				this.rightArrowAnim.draw(graphics, rightArrowX, arrowY);
			}
		}
		if (style === 3) {
			titleId = this.valueSlotIndex(titleId);
			let cornerFrame: number = -1;
			if (titleId === 209) {
				cornerFrame = 666;
			}
			else {
				if (titleId === 205) {
					cornerFrame = 668;
				}
				else {
					if (titleId === 208) {
						cornerFrame = 667;
					}
					else {
						if (titleId === 204) {
							cornerFrame = 669;
						}
					}
				}
			}
			if (cornerFrame !== -1) {
				let hitRect: Int32Array = this.hitRect;
				SpriteRenderer.hitTestMirrored(this.hitRect, cornerFrame, 0, 0);
				graphics.setClip(((x + hitRect[0]) | 0), ((y + hitRect[1]) | 0), hitRect[2], hitRect[3]);
				SpriteRenderer.drawFrame(graphics, cornerFrame, 0, x, y);
				graphics.setClip(((((((x + width) | 0) - hitRect[2]) | 0) + hitRect[0]) | 0), ((y + hitRect[1]) | 0), hitRect[2], hitRect[3]);
				SpriteRenderer.drawFrame(graphics, cornerFrame, 0, ((((x + width) | 0) - hitRect[2]) | 0), y);
				graphics.setClip(clipX, clipY, clipW, clipH);
			}
		}
	}
	static drawThickBorder(graphics: Graphics, x: number, y: number, width: number, height: number): void {
		SpriteRenderer.setColor(graphics, 77);
		graphics.fillRect(x, y, width, 6);
		graphics.fillRect(x, ((((y + height) | 0) - 6) | 0), width, 6);
		graphics.fillRect(x, ((y + 6) | 0), 6, ((height - 12) | 0));
		graphics.fillRect(((((x + width) | 0) - 6) | 0), ((y + 6) | 0), 6, ((height - 12) | 0));
	}
	static drawNineSlice(graphics: Graphics, x: number, y: number, width: number, height: number, spriteId: number, fillColor: number): void {
		let clipX: number = graphics.getClipX();
		let clipY: number = graphics.getClipY();
		let clipW: number = graphics.getClipWidth();
		let clipH: number = graphics.getClipHeight();
		let capW: number = SpriteRenderer.frameWidth(spriteId, 0);
		let capH: number = SpriteRenderer.frameHeight(spriteId, 0);
		let innerX: number = ((x + capW) | 0);
		let rightX: number = ((((x + width) | 0) - capW) | 0);
		let innerW: number = ((width - (capW << 1)) | 0);
		let innerY: number = ((y + capH) | 0);
		let bottomY: number = ((((y + height) | 0) - capH) | 0);
		let innerH: number = ((height - (capH << 1)) | 0);
		SpriteRenderer.drawFrame(graphics, spriteId, 0, x, y);
		SpriteRenderer.drawFrame(graphics, spriteId, 2, rightX, y);
		SpriteRenderer.drawFrame(graphics, spriteId, 6, x, bottomY);
		SpriteRenderer.drawFrame(graphics, spriteId, 4, rightX, bottomY);
		graphics.setClip(clipX, innerY, clipW, innerH);
		SpriteRenderer.drawFrame(graphics, spriteId, 7, x, innerY);
		SpriteRenderer.drawFrame(graphics, spriteId, 3, rightX, innerY);
		graphics.setClip(innerX, clipY, innerW, clipH);
		SpriteRenderer.drawFrame(graphics, spriteId, 1, innerX, y);
		SpriteRenderer.drawFrame(graphics, spriteId, 5, innerX, bottomY);
		graphics.setClip(clipX, clipY, clipW, clipH);
		if (fillColor !== -1) {
			SpriteRenderer.setColor(graphics, fillColor);
			graphics.fillRect(innerX, innerY, innerW, innerH);
		}
	}
	drawWindowBox(graphics: Graphics, x: number, y: number, width: number, height: number): void {
		BaseScreen.drawNineSlice(graphics, x, y, width, height, 576, 1);
	}
	drawSunkenBox(graphics: Graphics, x: number, y: number, width: number, height: number): void {
		BaseScreen.drawNineSlice(graphics, x, y, width, height, 580, 81);
	}
	drawButton(graphics: Graphics, x: number, y: number, width: number, height: number, pressed: boolean, textId: number): void {
		this.drawButtonLabel(graphics, x, y, width, height, pressed, this.canvas.getString(textId));
	}
	drawButtonLabel(graphics: Graphics, x: number, y: number, width: number, height: number, pressed: boolean, label: SdkString): void {
		let colorIndex: number = 0;
		if (pressed) {
			colorIndex = 0;
			BaseScreen.drawNineSlice(graphics, ((x - 1) | 0), ((y - 1) | 0), ((width + 2) | 0), ((height + 2) | 0), 578, 94);
		}
		else {
			colorIndex = 1;
			BaseScreen.drawNineSlice(graphics, x, y, width, height, 579, 90);
		}
		this.canvas.drawString(label, colorIndex, ((x + (width >> 1)) | 0), ((((y + (height >> 1)) | 0) + 2) | 0), 3);
	}
	drawListBox(graphics: Graphics, x: number, y: number, width: number, height: number): void {
		BaseScreen.drawNineSlice(graphics, x, y, width, height, 577, 76);
	}
	static drawInsetRect(graphics: Graphics, x: number, y: number, width: number, height: number, color: number): void {
		SpriteRenderer.setColor(graphics, color);
		graphics.drawRect(x, y, ((width - 1) | 0), ((height - 1) | 0));
		SpriteRenderer.setColor(graphics, 73);
		graphics.fillRect(((x + 1) | 0), ((y + 1) | 0), ((width - 2) | 0), 1);
		graphics.fillRect(((x + 1) | 0), ((y + 2) | 0), 1, ((height - 3) | 0));
		SpriteRenderer.setColor(graphics, 74);
		graphics.fillRect(((x + 2) | 0), ((y + 2) | 0), ((width - 3) | 0), ((height - 3) | 0));
	}
	drawRaisedBox(graphics: Graphics, x: number, y: number, width: number, height: number): void {
		BaseScreen.drawInsetRect(graphics, x, y, width, height, 77);
	}
	drawPlainBox(graphics: Graphics, x: number, y: number, width: number, height: number): void {
		BaseScreen.drawInsetRect(graphics, x, y, width, height, 0);
	}
	drawTextBox(graphics: Graphics, cursor: Int32Array): void {
		let canvas: GameCanvas = this.canvas;
		let lineHeight: number = this.canvas.lineHeight(1);
		let lineCount: number = Math.max(5, Math.min(canvas.wrappedLineCount(), this.visibleLineCount));
		let height: number = ((Math.imul(lineHeight, lineCount) + 16) | 0);
		if (graphics === null) {
			cursor[3] = height;
		}
		else {
			let x: number = cursor[0];
			let width: number = ((cursor[2] - BaseScreen.scrollbarWidth()) | 0);
			let y: number = cursor[1];
			let iconX: number = ((x + 8) | 0);
			let iconY: number = ((y + 8) | 0);
			let rightX: number = ((x + width) | 0);
			this.drawWindowBox(graphics, x, y, width, height);
			canvas.drawWrappedTextClipped(1, iconX, iconY, 20, this.menuScroll, this.visibleLineCount);
			this.drawScrollbar(graphics, rightX, y, height, this.menuScroll, this.visibleLineCount, canvas.wrappedLineCount(), true);
		}
	}
	drawIconBar(graphics: Graphics, x: number, y: number, width: number, iconId: number): number {
		let canvas: GameCanvas = this.canvas;
		let innerH: number = ((((width - 3) | 0) - 3) | 0);
		let height: number = ((((6 + innerH) | 0) + 3) | 0);
		this.drawListBox(graphics, x, -3, y, height);
		let innerW: number = ((y - 6) | 0);
		let innerX: number = ((x + (((y - innerW) | 0) >> 1)) | 0);
		this.drawSunkenBox(graphics, innerX, 3, innerW, innerH);
		canvas.drawStringById(iconId, 0, ((innerX + (innerW >> 1)) | 0), ((((3 + (innerH >> 1)) | 0) + 2) | 0), 3);
		return ((height - 3) | 0);
	}
	drawIconRow(graphics: Graphics, x: number, y: number, iconId: number): number {
		return this.drawIconBar(graphics, x, y, 25, iconId);
	}
	drawIconPanel(graphics: Graphics, x: number, y: number, iconId: number, subIconId: number): number {
		let canvas: GameCanvas = this.canvas;
		this.drawListBox(graphics, x, -3, y, 47);
		let innerW: number = ((y - 6) | 0);
		let innerX: number = ((x + (((y - innerW) | 0) >> 1)) | 0);
		this.drawSunkenBox(graphics, innerX, 3, innerW, 19);
		canvas.drawStringById(iconId, 0, ((innerX + (innerW >> 1)) | 0), 14, 3);
		this.drawWindowBox(graphics, innerX, 25, innerW, 16);
		if (subIconId !== -1) {
			canvas.drawStringById(subIconId, 1, ((innerX + (innerW >> 1)) | 0), 34, 3);
		}
		return 44;
	}
	drawCenteredLabel(graphics: Graphics, x: number, y: number): void {
		this.drawIcon(graphics, x, y, 0, false, 0);
	}
	drawIcon(graphics: Graphics, x: number, y: number, iconId: number, highlighted: boolean, recolor: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		let frame: number = this.canvas.neighborhood.simAttribute(iconId, 0) === 0 ? highlighted ? 595 : 594 : highlighted ? 593 : 592;
		let frameIndex: number = neighborhood.simAttribute(iconId, 2);
		SpriteRenderer.setRecolorIndex(recolor);
		SpriteRenderer.drawFrame(graphics, frame, frameIndex, x, ((y + 0) | 0));
		SpriteRenderer.setRecolorIndex(0);
	}
	static drawFrameBorder(graphics: Graphics, x: number, y: number, width: number, height: number, thickness: number): void {
		let innerH: number = ((((height - thickness) | 0) - thickness) | 0);
		graphics.fillRect(x, y, width, thickness);
		graphics.fillRect(x, ((((y + height) | 0) - thickness) | 0), width, thickness);
		graphics.fillRect(x, ((y + thickness) | 0), thickness, innerH);
		graphics.fillRect(((((x + width) | 0) - thickness) | 0), ((y + thickness) | 0), thickness, innerH);
	}
	textFor(textId: number): SdkString {
		let canvas: GameCanvas = this.canvas;
		if (textId >= -12) {
			return canvas.getString(textId);
		}
		else {
			let mappedId: number = 0;
			return (mappedId = this.valueSlotIndex(textId)) === 24 ? null : canvas.getString(mappedId);
		}
	}
	valueSlotIndex(textId: number): number {
		return this.valueSlots[((-13 - textId) | 0)];
	}
	textBlock(textId: number): Int16Array {
		return this.canvas.tileGroup(textId);
	}
	beginList(count: number, lineHeight: number, selected: number): void {
		this.itemCount = count;
		this.lineHeight = lineHeight;
		this.scrollOffset = 0;
		this.visibleRowCount = -1;
		this.selectedIndex = selected;
	}
	scrollList(keyState: number, prevKeyState: number): boolean {
		if (this.visibleRowCount !== -1) {
			let previous: number = this.selectedIndex;
			let target: number = 0;
			if (this.selectedIndex === -1) {
				target = this.scrollOffset;
			}
			else {
				target = this.selectedIndex;
			}
			if (this.matchesInput(keyState, prevKeyState, 16640, 0)) {
				target++;
			}
			else {
				if (this.matchesInput(keyState, prevKeyState, 8196, 0)) {
					target--;
				}
			}
			if (this.selectedIndex === -1) {
				this.scrollOffset = Math.max(0, Math.min(target, ((this.itemCount - this.visibleRowCount) | 0)));
			}
			else {
				this.selectedIndex = Math.max(0, Math.min(target, ((this.itemCount - 1) | 0)));
			}
			return previous !== this.selectedIndex;
		}
		else {
			return false;
		}
	}
	drawScrollList(graphics: Graphics, x: number, y: number, width: number, height: number, listKind: number): void {
		let canvas: GameCanvas = this.canvas;
		let innerW: number = ((width - BaseScreen.scrollbarWidth()) | 0);
		let clipX: number = ((x + 2) | 0);
		let clipY: number = ((y + 2) | 0);
		let clipW: number = ((((innerW - 2) | 0) - 1) | 0);
		let clipH: number = ((((height - 2) | 0) - 1) | 0);
		this.drawWindowBox(graphics, x, y, innerW, height);
		let rowsVisible: number = ((clipH / this.lineHeight) | 0);
		let count: number = this.itemCount;
		this.visibleRowCount = rowsVisible;
		if (this.selectedIndex !== -1) {
			let selected: number = this.selectedIndex;
			let newOffset: number = this.scrollOffset;
			if (selected < newOffset) {
				newOffset = selected;
			}
			else {
				if (selected >= ((newOffset + rowsVisible) | 0)) {
					newOffset = Math.max(0, ((((selected - rowsVisible) | 0) + 1) | 0));
				}
			}
			this.scrollOffset = newOffset;
		}
		let scrollbarX: number = ((x + innerW) | 0);
		this.drawScrollbar(graphics, scrollbarX, y, height, this.scrollOffset, rowsVisible, count, true);
		let oldClipX: number = graphics.getClipX();
		let oldClipY: number = graphics.getClipY();
		let oldClipW: number = graphics.getClipWidth();
		let oldClipH: number = graphics.getClipHeight();
		graphics.setClip(clipX, clipY, clipW, clipH);
		let titleScreen: TitleScreen = canvas.titleScreen();
		let gameScreen: GameScreen = canvas.gameScreen();
		switch (listKind) {
			case 0:
				titleScreen.drawPersonaRows(graphics, clipX, clipY, clipW, clipH);
				break;
			case 1:
				titleScreen.drawTraitRows(graphics, clipX, clipY, clipW, clipH);
				break;
			case 2:
			case 4:
			case 8:
				this.drawItemList(graphics, clipX, clipY, clipW, clipH, listKind);
				break;
			case 3:
				gameScreen.drawCareerList(graphics, clipX, clipY, clipW, clipH);
				break;
			case 5:
			case 6:
			case 9:
				gameScreen.drawObjectRows(graphics, clipX, clipY, clipW, clipH, listKind);
				break;
			case 7:
				gameScreen.drawRosterList(graphics, clipX, clipY, clipW, clipH);
				break;
			case 10:
				gameScreen.drawObjectListRow(graphics, clipX, clipY, clipW, clipH);
		}
		graphics.setClip(oldClipX, oldClipY, oldClipW, oldClipH);
	}
	static drawHLine(graphics: Graphics, x: number, y: number, width: number): void {
		SpriteRenderer.setColor(graphics, 75);
		graphics.fillRect(x, y, width, 1);
	}
	drawClippedRow(graphics: Graphics, x: number, y: number, width: number, height: number, clipBottom: number, color: number, rowIndex: number): void {
		if (rowIndex === this.scrollOffset) {
			BaseScreen.fillRowFirst(graphics, x, y, width, height, color);
		}
		else {
			if (((y + height) | 0) >= clipBottom) {
				BaseScreen.fillRowLast(graphics, x, y, width, ((clipBottom - y) | 0), color);
			}
			else {
				BaseScreen.fillRowTop(graphics, x, y, width, height, color);
			}
		}
	}
	static fillRowTop(graphics: Graphics, x: number, y: number, width: number, height: number, color: number): void {
		SpriteRenderer.setColor(graphics, color);
		graphics.fillRect(x, ((y + 1) | 0), width, ((height - 1) | 0));
	}
	static fillRowFirst(graphics: Graphics, x: number, y: number, width: number, height: number, color: number): void {
		SpriteRenderer.setColor(graphics, color);
		graphics.fillRect(((x + 1) | 0), y, ((width - 2) | 0), 1);
		graphics.fillRect(x, ((y + 1) | 0), width, ((height - 1) | 0));
	}
	static fillRowLast(graphics: Graphics, x: number, y: number, width: number, height: number, color: number): void {
		SpriteRenderer.setColor(graphics, color);
		graphics.fillRect(x, ((y + 1) | 0), width, ((((height - 1) | 0) - 1) | 0));
		graphics.fillRect(((x + 1) | 0), ((((y + height) | 0) - 1) | 0), ((width - 2) | 0), 1);
	}
	drawHeader(graphics: Graphics, y: number): void {
		let canvas: GameCanvas = this.canvas;
		let levelCount: number = this.canvas.neighborhood.achievementCount();
		let text: SdkString = canvas.beginString();
		canvas.appendStringId(551);
		canvas.appendInt(canvas.visitedFlagCount());
		canvas.appendStringId(27);
		canvas.appendInt(levelCount);
		canvas.setString(-9, text);
		this.setValue(1, -9);
		text = canvas.beginString();
		canvas.appendStringId(550);
		canvas.appendInt(canvas.flagCount());
		canvas.appendStringId(27);
		canvas.appendInt(levelCount);
		canvas.setString(-8, text);
		this.setValue(2, -8);
		this.drawPanel(graphics, y);
	}
	resetList(): void {
		this.beginList(this.canvas.neighborhood.achievementCount(), 27, -1);
	}
	itemIndexAt(offset: number): number {
		let canvas: GameCanvas = this.canvas;
		let simCount: number = this.canvas.neighborhood.achievementCount();
		let firstSectionCount: number = canvas.flagCount();
		let secondSectionBase: number = canvas.visitedFlagCount();
		let firstSectionIndex: number = 0;
		let secondSectionIndex: number = 0;
		for (let index: number = 0; index < simCount; index++) {
			if (!canvas.hasFlag(index) && offset >= firstSectionCount) {
				return index;
			}
			if (canvas.hasFlag(index)) {
				if (canvas.hasVisitedFlag(index)) {
					if (firstSectionIndex === offset) {
						return index;
					}
					firstSectionIndex++;
				}
				else {
					if (secondSectionIndex === ((offset - secondSectionBase) | 0) && offset >= secondSectionBase) {
						return index;
					}
					secondSectionIndex++;
				}
			}
		}
		return -1;
	}
	drawItemList(graphics: Graphics, x: number, y: number, width: number, height: number, listKind: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		let iconX: number = ((x + 3) | 0);
		let iconH: number = SpriteRenderer.frameHeight(565, 0);
		let iconY: number = ((13 - (iconH >> 1)) | 0);
		let dividerX: number = 0;
		let textX: number = (((dividerX = ((((((x + 3) | 0) + iconH) | 0) + 3) | 0)) + 3) | 0);
		let rowW: number = ((((((x + width) | 0) - textX) | 0) - 3) | 0);
		SpriteRenderer.setColor(graphics, 75);
		graphics.fillRect(dividerX, y, 1, height);
		let rowY: number = y;
		let rowIndex: number = 0;
		let itemIndex: number = this.scrollOffset;
		while (rowY < ((y + height) | 0) && itemIndex < this.itemCount) {
			let iconId: number = 0;
			let frameSprite: number = 0;
			let frameIndex: number = 0;
			label39: {
				let frameIndexTemp: number = 0;
				label38: {
					frameIndex = 0;
					if (listKind === 2) {
						let entityIndex: number = this.itemIndexAt(itemIndex);
						if (!canvas.hasFlag(entityIndex)) {
							iconId = 549;
							frameSprite = 565;
							frameIndexTemp = 2;
							break label38;
						}
						iconId = neighborhood.achievementText(entityIndex);
						if (canvas.hasVisitedFlag(entityIndex)) {
							frameSprite = 565;
							frameIndexTemp = 1;
							break label38;
						}
						frameSprite = 565;
					}
					else {
						if (listKind === 8) {
							let levelIndex: number = neighborhood.achievementSlotValue(itemIndex);
							iconId = neighborhood.achievementText(levelIndex);
							frameSprite = 589;
							if (levelIndex === -1) {
								frameSprite = 196;
							}
							break label39;
						}
						let simIndex: number = neighborhood.selectedResidentAnimFrame(itemIndex);
						iconId = neighborhood.achievementText(simIndex);
						frameSprite = 565;
						if (neighborhood.hasUsedTask(itemIndex)) {
							frameIndexTemp = 1;
							break label38;
						}
					}
					frameIndexTemp = 0;
				}
				frameIndex = frameIndexTemp;
			}
			canvas.drawSlotText(rowIndex, iconId, 1, rowW, textX, ((rowY + 13) | 0), 6);
			SpriteRenderer.drawFrame(graphics, frameSprite, frameIndex, iconX, ((rowY + iconY) | 0));
			rowIndex++;
			rowY = ((rowY + 27) | 0);
			itemIndex++;
			BaseScreen.drawHLine(graphics, x, rowY, width);
		}
	}
	drawMenuList(graphics: Graphics, x: number, y: number, width: number, listKind: number): number {
		let height: number = ((8 + Math.imul(6, this.textLineHeight)) | 0);
		if (graphics !== null) {
			let canvas: GameCanvas = this.canvas;
			let neighborhood: Neighborhood = this.canvas.neighborhood;
			let category: number = 0;
			let itemCount: number = 5;
			if (listKind === 36) {
				category = (this as unknown as GameScreen).currentSimId();
				itemCount = neighborhood.simKnownCount(category);
			}
			this.drawRaisedBox(graphics, x, y, width, height);
			let iconX: number = ((x + 4) | 0);
			let iconY: number = ((y + 4) | 0);
			let labelX: number = ((iconX + 12) | 0);
			let rowY: number = ((((iconY + this.textLineHeight) | 0) + 2) | 0);
			let rowH: number = ((this.textLineHeight - 2) | 0);
			let halfW: number = width >> 1;
			let headerSprite: number = 644;
			if (listKind === 31) {
				headerSprite = 745;
			}
			canvas.drawStringById(headerSprite, 1, iconX, iconY, 20);
			canvas.beginString();
			let text: any = null;
			let textY: number = rowY;
			for (let index: number = 0; index < itemCount; index++) {
				text = canvas.beginString();
				canvas.appendInt(((index + 1) | 0));
				canvas.appendStringId(29);
				canvas.drawString((text as unknown as SdkString), 1, iconX, textY, 20);
				let itemId: number = neighborhood.simKnownId(category, index);
				if (listKind === 36 && !neighborhood.simKnowsWell(category, itemId)) {
					itemId = -1;
				}
				if (itemId !== -1) {
					let iconId: number = neighborhood.itemNameText(itemId);
					canvas.drawStringById(iconId, 1, labelX, textY, 20);
				}
				else {
					SpriteRenderer.setColor(graphics, 0);
					graphics.fillRect(labelX, ((textY + rowH) | 0), ((width - halfW) | 0), 1);
				}
				textY = ((textY + this.textLineHeight) | 0);
			}
			if (listKind === 36) {
				(this as unknown as GameScreen).drawScrollBar(graphics, x, y, width, height);
			}
			else {
				if (listKind === 33) {
					(this as unknown as GameScreen).drawScrollHint(graphics, x, y, width, height);
				}
			}
		}
		return height;
	}
	showInfoPanel(textId: number, value: number): void {
		this.setValue(0, value);
		this.canvas.setWrappedString(textId, 1, this.dialogWidth);
	}
	drawPageOne(graphics: Graphics): void {
		this.drawPanel(graphics, 1);
	}
	showPanelTwo(textId: number, value: number): void {
		this.setValue(0, value);
		this.canvas.setWrappedString(textId, 1, this.listY);
		this.menuScroll = 0;
	}
	drawPageTwo(graphics: Graphics): void {
		this.drawPanel(graphics, 2);
	}
	scrollMenu(keyState: number): void {
		if (this.matchesInput(keyState, 0, 8196, 0)) {
			this.menuScroll = Math.max(0, ((this.menuScroll - 1) | 0));
		}
		if (this.matchesInput(keyState, 0, 16640, 0) && this.canvas.wrappedLineCount() > ((this.menuScroll + this.visibleLineCount) | 0)) {
			this.menuScroll++;
		}
	}
	drawCaption(graphics: Graphics, text: Int16Array, align: number): void {
		this.drawTwoColumnList(graphics, text, align, null);
	}
	drawTwoColumnList(graphics: Graphics, data: Int16Array, flags: number, labels: SdkString[]): void {
		let canvas: GameCanvas = this.canvas;
		let lot: Lot = this.canvas.lot;
		let rowCount: number = data[0];
		let firstIndex: number = data[3];
		let selectedIndex: number = data[2];
		let maxRows: number = 0;
		let rowsShown: number = (maxRows = data[4]) !== -1 ? Math.min((maxRows | 0), (rowCount | 0)) : rowCount;
		let screenW: number = canvas.getWidth();
		let screenH: number = canvas.getHeight();
		let border: number = (flags & 1) !== 0 ? 4 : 0;
		let panelW: number = 0;
		let centered: boolean = false;
		if (centered = (flags & 6) !== 0) {
			panelW = screenW >> 1;
		}
		else {
			if ((flags & 8) !== 0) {
				panelW = screenW >> 1;
			}
			else {
				panelW = ((Math.imul(screenW, 4) / 5) | 0);
			}
		}
		let headerH: number = 0;
		let panelH: number = (((((((((headerH = (flags & 16) === 0 ? 14 : 0) + 7) | 0) + Math.imul(15, rowsShown)) | 0) + 1) | 0) + (border << 1)) | 0);
		let panelX: number = 0;
		if ((flags & 2) !== 0) {
			panelX = ((lot.boundsX + 3) | 0);
		}
		else {
			if ((flags & 4) !== 0) {
				panelX = ((((((lot.boundsX + canvas.getWidth()) | 0) - panelW) | 0) - 3) | 0);
			}
			else {
				if ((flags & 8) !== 0) {
					panelX = 3;
				}
				else {
					panelX = ((screenW - panelW) | 0) >> 1;
				}
			}
		}
		let panelY: number = 0;
		if (centered) {
			panelY = (((((((lot.boundsY + lot.boundsHeight) | 0) - panelH) | 0) >> 1) - ((flags & 2) !== 0 ? 15 : 0)) | 0);
		}
		else {
			panelY = ((((screenH - 15) | 0) - panelH) | 0) >> 1;
		}
		if (border !== 0) {
			let unusedFlag: boolean = false;
			let shadeColor: number = 61;
			if ((flags & 32) !== 0) {
				shadeColor = 76;
			}
			SpriteRenderer.setColor(graphics, 77);
			graphics.drawRect(panelX, panelY, panelW, panelH);
			SpriteRenderer.setColor(graphics, shadeColor);
			BaseScreen.drawFrameBorder(graphics, ((panelX + 1) | 0), ((panelY + 1) | 0), ((panelW - 1) | 0), ((panelH - 1) | 0), ((border - 1) | 0));
			panelX = ((panelX + border) | 0);
			panelY = ((panelY + border) | 0);
			panelW = ((panelW - (border << 1)) | 0);
			panelH = ((panelH - (border << 1)) | 0);
		}
		let scrollable: boolean = rowsShown !== rowCount;
		let textX: number = ((panelX + 3) | 0);
		let rowW: number = ((((panelW - 6) | 0) + (scrollable ? -9 : 1)) | 0);
		let centerX: number = ((textX + (rowW >> 1)) | 0);
		SpriteRenderer.setColor(graphics, 76);
		graphics.fillRect(panelX, panelY, panelW, panelH);
		SpriteRenderer.setColor(graphics, 77);
		graphics.drawRect(panelX, panelY, panelW, panelH);
		if ((flags & 16) === 0) {
			graphics.fillRect(panelX, ((panelY + headerH) | 0), panelW, 1);
			canvas.drawMarqueeId(0, graphics, data[1], 0, ((panelX + 3) | 0), ((panelY + 0) | 0), ((panelW - 6) | 0), 17);
		}
		if (scrollable) {
			let scrollbarX: number = ((((panelX + panelW) | 0) - BaseScreen.scrollbarWidth()) | 0);
			let scrollbarY: number = ((((panelY + headerH) | 0) + 2) | 0);
			let scrollbarH: number = ((((panelH - headerH) | 0) - 4) | 0);
			this.drawScrollbar(graphics, scrollbarX, scrollbarY, scrollbarH, firstIndex, rowsShown, rowCount, false);
		}
		let rowY: number = ((((panelY + headerH) | 0) + 7) | 0);
		for (let row: number = 0; row < rowsShown; row++) {
			let index: number = ((firstIndex + row) | 0);
			let colorIndex: number = 1;
			let iconId: number = -1;
			if (labels === null) {
				iconId = data[((5 + index) | 0)];
			}
			if (selectedIndex === index && iconId !== 274 && iconId !== 275) {
				colorIndex = 0;
				if ((flags & 32) !== 0) {
					SpriteRenderer.setColor(graphics, 1);
					graphics.fillRect(textX, rowY, rowW, 15);
				}
				else {
					if (centered) {
						SpriteRenderer.setColor(graphics, 77);
						graphics.fillRect(textX, rowY, rowW, 15);
					}
					else {
						BaseScreen.drawSeparator(graphics, textX, rowY, rowW, 15);
					}
				}
			}
			let secondIcon: number = 0;
			if ((secondIcon = canvas.optionStringId(iconId)) !== -1) {
				let iconW: number = ((canvas.stringWidthById(iconId, colorIndex) + canvas.spaceWidth(colorIndex)) | 0);
				let secondW: number = canvas.stringWidthById(secondIcon, colorIndex);
				let secondX: number = 0;
				let labelX: number = (((secondX = ((centerX - (((iconW + secondW) | 0) >> 1)) | 0)) + iconW) | 0);
				canvas.drawStringById(iconId, colorIndex, secondX, rowY, 20);
				canvas.drawStringById(secondIcon, colorIndex, labelX, rowY, 20);
			}
			else {
				if (iconId !== -1) {
					if (selectedIndex === index) {
						canvas.drawMarqueeId(1, graphics, iconId, colorIndex, textX, rowY, rowW, 17);
					}
					else {
						canvas.drawTextById(graphics, iconId, colorIndex, textX, rowY, rowW, 17);
					}
				}
				else {
					let text: SdkString = labels[index];
					if (selectedIndex === index) {
						canvas.drawMarqueeText(1, graphics, text, colorIndex, textX, rowY, rowW, 17);
					}
					else {
						canvas.drawTextClipped(graphics, text, colorIndex, textX, rowY, rowW, 17);
					}
				}
			}
			rowY = ((rowY + 15) | 0);
		}
	}
	static drawSeparator(graphics: Graphics, x: number, y: number, width: number, height: number): void {
		SpriteRenderer.setColor(graphics, 62);
		graphics.fillRect(x, y, width, 1);
		SpriteRenderer.setColor(graphics, 63);
		graphics.fillRect(x, ((y + 1) | 0), width, ((height - 3) | 0));
		SpriteRenderer.setColor(graphics, 64);
		graphics.fillRect(x, ((((y + height) | 0) - 2) | 0), width, 1);
	}
	drawScrollbar(graphics: Graphics, x: number, y: number, height: number, offset: number, visibleCount: number, totalCount: number, drawTrack: boolean): void {
		x = ((x + 3) | 0);
		let capH: number = SpriteRenderer.frameHeight(599, 0);
		let trackY: number = ((((((y + capH) | 0) + 1) | 0) + 2) | 0);
		let trackH: number = ((height - (((((capH + 1) | 0) + 2) | 0) << 1)) | 0);
		let topCapFrame: number = 0;
		let bottomCapFrame: number = 0;
		let thumbFrame: number = 0;
		let thumbY: number = 0;
		let thumbH: number = 0;
		if (visibleCount >= totalCount) {
			topCapFrame = 0;
			bottomCapFrame = 0;
			thumbFrame = 0;
			thumbY = trackY;
			thumbH = trackH;
		}
		else {
			topCapFrame = offset > 0 ? 1 : 0;
			bottomCapFrame = ((offset + visibleCount) | 0) < totalCount ? 1 : 0;
			thumbFrame = 2;
			thumbH = Math.max(18, ((Math.imul(trackH, visibleCount) / totalCount) | 0));
			thumbY = ((trackY + ((Math.imul((((trackH - thumbH) | 0)), offset) / (((totalCount - visibleCount) | 0))) | 0)) | 0);
		}
		let topY: number = ((y + 2) | 0);
		let bottomY: number = ((((((y + height) | 0) - 2) | 0) - capH) | 0);
		SpriteRenderer.drawFrame(graphics, 599, topCapFrame, x, topY);
		SpriteRenderer.drawFrame(graphics, 598, bottomCapFrame, x, bottomY);
		if (thumbFrame === 2) {
			BaseScreen.drawScrollThumb(graphics, x, trackY, trackH, 1);
		}
		BaseScreen.drawScrollThumb(graphics, x, thumbY, thumbH, thumbFrame);
		if (drawTrack) {
			SpriteRenderer.setColor(graphics, 77);
			let lineX: number = ((x - 2) | 0);
			let lineW: number = ((SpriteRenderer.frameWidth(602, 0) + 4) | 0);
			graphics.fillRect(lineX, y, lineW, 1);
			graphics.fillRect(lineX, ((((y + height) | 0) - 1) | 0), lineW, 1);
			graphics.fillRect(((lineX + lineW) | 0), y, 1, height);
		}
	}
	static drawScrollThumb(graphics: Graphics, x: number, y: number, height: number, frame: number): void {
		let capH: number = SpriteRenderer.frameHeight(601, frame);
		let bottomCapH: number = SpriteRenderer.frameHeight(600, frame);
		let clipX: number = graphics.getClipX();
		let clipY: number = graphics.getClipY();
		let clipW: number = graphics.getClipWidth();
		let clipH: number = graphics.getClipHeight();
		graphics.setClip(clipX, y, clipW, height);
		SpriteRenderer.drawFrame(graphics, 601, frame, x, y);
		SpriteRenderer.drawFrame(graphics, 600, frame, x, ((((y + height) | 0) - bottomCapH) | 0));
		y = ((y + capH) | 0);
		height = ((height - (((capH + bottomCapH) | 0))) | 0);
		graphics.setClip(clipX, y, clipW, height);
		SpriteRenderer.drawFrame(graphics, 602, frame, x, y);
		graphics.setClip(clipX, clipY, clipW, clipH);
	}
	static scrollbarWidth(): number {
		return ((SpriteRenderer.frameWidth(602, 0) + 6) | 0);
	}
	static approach(current: number, target: number, numerator: number, shift: number): number {
		if (current !== target) {
			let delta: number = 0;
			let step: number = 0;
			if ((step = Math.imul((delta = ((target - current) | 0)), numerator) >> shift) === 0) {
				step = FixedMath.sign(delta);
			}
			else {
				if (delta > 0 && ((current + step) | 0) > target || delta < 0 && ((current + step) | 0) < target) {
					step = delta;
				}
			}
			current = ((current + step) | 0);
		}
		return current;
	}
	constructor(gameCanvas: GameCanvas) {
		this.canvas = gameCanvas;
		this.hitRect = new Int32Array(4);
	}
}
