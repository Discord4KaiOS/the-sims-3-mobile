// Generated from Tile.java by tools/web/JavaToTs.java; keep this file in sync.
import { Image, Graphics, gc, InputStream, DataInputStream, IOException } from '../runtime';
import { GameCanvas } from './GameCanvas';

export class Tile {
	cellIndices: Int8Array = null!;
	frames: Int16Array = null!;
	rows: Int16Array = null!;
	drawX: number = 0;
	drawY: number = 0;
	drawWidth: number = 0;
	drawHeight: number = 0;
	worldX: number = 0;
	worldY: number = 0;
	sheet: Image = null!;
	loaded: boolean = false;
	columns: number = 0;
	rowCountValue: number = 0;
	backing: Image = null!;
	backingGraphics: Graphics = null!;
	backingEnabled: boolean = false;
	backingDirty: boolean = false;
	backingX: number = 0;
	backingY: number = 0;
	wrapX: number = 0;
	wrapY: number = 0;
	next: Tile = null!;
	static tileWidth(): number {
		return 16;
	}
	static tileHeight(): number {
		return 8;
	}
	static toWorld16(value: number): number {
		return value << 20;
	}
	static toWorld8(value: number): number {
		return value << 19;
	}
	frameCount(): number {
		return this.frames !== null ? ((this.frames.length / 3) | 0) : 0;
	}
	rowCount(): number {
		return this.rows !== null ? ((this.rows.length / 3) | 0) : 0;
	}
	setBounds(x: number, y: number, width: number, height: number): void {
		if (this.backingEnabled && (this.drawWidth !== width || this.drawHeight !== height)) {
			this.backing = null;
			this.backing = Image.createImage(width, height);
			this.backingGraphics = this.backing.getGraphics();
			this.backingDirty = true;
		}
		this.drawX = x;
		this.drawY = y;
		this.drawWidth = width;
		this.drawHeight = height;
	}
	draw(gfx: Graphics): void {
		let clipX: number = gfx.getClipX();
		let clipY: number = gfx.getClipY();
		let clipW: number = gfx.getClipWidth();
		let clipH: number = gfx.getClipHeight();
		let x: number = this.worldX >> 16;
		let y: number = this.worldY >> 16;
		if (this.backingEnabled) {
			this.drawBacking(gfx, x, y);
		}
		else {
			let dstX: number = clipX;
			let dstY: number = clipY;
			let dstW: number = clipW;
			let dstH: number = clipH;
			if (clipX < this.drawX) {
				dstX = this.drawX;
			}
			if (clipY < this.drawY) {
				dstY = this.drawY;
			}
			if (((dstX + clipW) | 0) > ((this.drawX + this.drawWidth) | 0)) {
				dstW = ((clipW - (((((dstX + clipW) | 0) - (((this.drawX + this.drawWidth) | 0))) | 0))) | 0);
			}
			if (((dstY + clipH) | 0) > ((this.drawY + this.drawHeight) | 0)) {
				dstH = ((clipH - (((((dstY + clipH) | 0) - (((this.drawY + this.drawHeight) | 0))) | 0))) | 0);
			}
			this.drawCells(gfx, x, y, dstX, dstY, dstW, dstH);
			if (this.next !== null) {
				this.next.drawCells(gfx, x, y, dstX, dstY, dstW, dstH);
			}
		}
		gfx.setClip(clipX, clipY, clipW, clipH);
	}
	load(index: number): boolean {
		gc();
		if (this.loaded) {
			return false;
		}
		else {
			let input: InputStream = GameCanvas.instance().resources.openStream(index);
			gc();
			let stream: DataInputStream = new DataInputStream(input);
			try {
				stream.readUnsignedByte();
				let columns: number = stream.readUnsignedByte();
				let rows: number = stream.readUnsignedByte();
				stream.readUnsignedByte();
				stream.readUnsignedByte();
				this.columns = columns;
				this.rowCountValue = rows;
				gc();
				let cells: Int8Array = new Int8Array(Math.imul(columns, rows));
				for (let cellIndex: number = 0; cellIndex < Math.imul(columns, rows); cellIndex++) {
					cells[cellIndex] = stream.readByte();
				}
				this.cellIndices = cells;
				let frameCount: number = 0;
				if ((frameCount = stream.readUnsignedByte()) > 0) {
					let frames: Int16Array = new Int16Array(Math.imul(frameCount, 3));
					for (let frameIndex: number = 0; frameIndex < frames.length; frameIndex++) {
						frames[frameIndex] = stream.readShort();
					}
					this.frames = frames;
				}
				let rowCount: number = 0;
				if ((rowCount = stream.readUnsignedByte()) > 0) {
					let rowsData: Int16Array = new Int16Array(Math.imul(rowCount, 3));
					for (let rowIndex: number = 0; rowIndex < rowsData.length; rowIndex++) {
						rowsData[rowIndex] = stream.readShort();
					}
					this.rows = rowsData;
				}
				input.close();
			}
			catch (exception) {
				if (exception instanceof IOException) {
				}
				else {
					throw exception;
				}
			}
			gc();
			this.loaded = true;
			return true;
		}
	}
	drawCells(gfx: Graphics, x: number, y: number, left: number, top: number, width: number, height: number): void {
		let remainingWidth: number = 0;
		let fullColumns: number = 0;
		let lastColumnWidth: number = 0;
		let remainingHeight: number = 0;
		let fullRows: number = 0;
		let lastRowHeight: number = 0;
		let xInTile: number = x & 15;
		let yInTile: number = y & 7;
		let firstColumnWidth: number = 0;
		if (xInTile > 0) {
			firstColumnWidth = ((16 - xInTile) | 0);
		}
		let firstRowHeight: number = 0;
		if (yInTile > 0) {
			firstRowHeight = ((8 - yInTile) | 0);
		}
		if (firstColumnWidth > width) {
			firstColumnWidth = width;
		}
		else {
			fullColumns = (remainingWidth = ((width - firstColumnWidth) | 0)) >> 4;
			lastColumnWidth = remainingWidth & 15;
		}
		if (firstRowHeight > height) {
			firstRowHeight = height;
		}
		else {
			fullRows = (remainingHeight = ((height - firstRowHeight) | 0)) >> 3;
			lastRowHeight = remainingHeight & 7;
		}
		let cellIndex: number = 0;
		let rowStartIndex: number = cellIndex = ((Math.imul((y >> 3), this.columns) + (x >> 4)) | 0);
		let cellValue: number = 0;
		let sourceX: number = 0;
		let sourceY: number = 0;
		let destX: number = left;
		let destY: number = top;
		let lastCellIndex: number = ((this.cellIndices.length - 1) | 0);
		let cellData: Int8Array = this.cellIndices;
		let sheetImage: Image = this.sheet;
		if (firstRowHeight > 0) {
			if (firstColumnWidth > 0) {
				if (cellIndex >= 0) {
					if (cellIndex > lastCellIndex) {
						return;
					}
					if ((cellValue = cellData[cellIndex++] & 255) !== 255) {
						sourceX = (cellValue & 7) << 4;
						sourceY = cellValue >> 3 << 3;
						gfx.drawRegion(sheetImage, ((sourceX + xInTile) | 0), ((sourceY + yInTile) | 0), firstColumnWidth, firstRowHeight, 0, left, top, 20);
					}
				}
				destX = ((left + firstColumnWidth) | 0);
			}
			for (let column: number = 0; column < fullColumns; column++) {
				if (cellIndex >= 0) {
					if (cellIndex > lastCellIndex) {
						return;
					}
					if ((cellValue = cellData[cellIndex++] & 255) !== 255) {
						sourceX = (cellValue & 7) << 4;
						sourceY = cellValue >> 3 << 3;
						gfx.drawRegion(sheetImage, sourceX, ((sourceY + yInTile) | 0), 16, firstRowHeight, 0, destX, destY, 20);
					}
				}
				destX = ((destX + 16) | 0);
			}
			if (lastColumnWidth > 0 && cellIndex >= 0) {
				if (cellIndex > lastCellIndex) {
					return;
				}
				if ((cellValue = cellData[cellIndex] & 255) !== 255) {
					sourceX = (cellValue & 7) << 4;
					sourceY = cellValue >> 3 << 3;
					gfx.drawRegion(this.sheet, sourceX, ((sourceY + yInTile) | 0), lastColumnWidth, firstRowHeight, 0, destX, destY, 20);
				}
			}
			destY = ((destY + firstRowHeight) | 0);
			cellIndex = (rowStartIndex = ((rowStartIndex + this.columns) | 0));
		}
		for (let row: number = 0; row < fullRows; row++) {
			destX = left;
			if (firstColumnWidth > 0) {
				if (cellIndex >= 0) {
					if (cellIndex > lastCellIndex) {
						return;
					}
					if ((cellValue = cellData[cellIndex++] & 255) !== 255) {
						sourceX = (cellValue & 7) << 4;
						sourceY = cellValue >> 3 << 3;
						gfx.drawRegion(sheetImage, ((sourceX + xInTile) | 0), sourceY, firstColumnWidth, 8, 0, left, destY, 20);
					}
				}
				destX = ((left + firstColumnWidth) | 0);
			}
			for (let column: number = 0; column < fullColumns; column++) {
				if (cellIndex >= 0) {
					if (cellIndex > lastCellIndex) {
						return;
					}
					if ((cellValue = cellData[cellIndex++] & 255) !== 255) {
						sourceX = (cellValue & 7) << 4;
						sourceY = cellValue >> 3 << 3;
						gfx.drawRegion(sheetImage, sourceX, sourceY, 16, 8, 0, destX, destY, 20);
					}
				}
				destX = ((destX + 16) | 0);
			}
			if (lastColumnWidth > 0 && cellIndex >= 0) {
				if (cellIndex > lastCellIndex) {
					return;
				}
				if ((cellValue = cellData[cellIndex] & 255) !== 255) {
					sourceX = (cellValue & 7) << 4;
					sourceY = cellValue >> 3 << 3;
					gfx.drawRegion(sheetImage, sourceX, sourceY, lastColumnWidth, 8, 0, destX, destY, 20);
				}
			}
			destY = ((destY + 8) | 0);
			cellIndex = (rowStartIndex = ((rowStartIndex + this.columns) | 0));
		}
		if (lastRowHeight > 0) {
			destX = left;
			if (firstColumnWidth > 0) {
				if (cellIndex >= 0) {
					if (cellIndex > lastCellIndex) {
						return;
					}
					if ((cellValue = cellData[cellIndex++] & 255) !== 255) {
						sourceX = (cellValue & 7) << 4;
						sourceY = cellValue >> 3 << 3;
						gfx.drawRegion(sheetImage, ((sourceX + xInTile) | 0), sourceY, firstColumnWidth, lastRowHeight, 0, left, destY, 20);
					}
				}
				destX = ((left + firstColumnWidth) | 0);
			}
			for (let column: number = 0; column < fullColumns; column++) {
				if (cellIndex >= 0) {
					if (cellIndex > lastCellIndex) {
						return;
					}
					if ((cellValue = cellData[cellIndex++] & 255) !== 255) {
						sourceX = (cellValue & 7) << 4;
						sourceY = cellValue >> 3 << 3;
						gfx.drawRegion(sheetImage, sourceX, sourceY, 16, lastRowHeight, 0, destX, destY, 20);
					}
				}
				destX = ((destX + 16) | 0);
			}
			if (lastColumnWidth > 0 && cellIndex >= 0) {
				if (cellIndex > lastCellIndex) {
					return;
				}
				if ((cellValue = cellData[cellIndex] & 255) !== 255) {
					sourceX = (cellValue & 7) << 4;
					sourceY = cellValue >> 3 << 3;
					gfx.drawRegion(sheetImage, sourceX, sourceY, lastColumnWidth, lastRowHeight, 0, destX, destY, 20);
				}
			}
		}
	}
	setBackingEnabled(enabled: boolean): void {
		if (enabled && !this.backingEnabled) {
			if (this.drawWidth > 0 && this.drawHeight > 0) {
				this.backing = Image.createImage(this.drawWidth, this.drawHeight);
				this.backingGraphics = this.backing.getGraphics();
			}
			this.backingDirty = true;
			this.backingX = this.worldX >> 16;
			this.backingY = this.worldY >> 16;
			this.wrapX = 0;
			this.wrapY = 0;
			this.backingEnabled = true;
		}
		else {
			if (!enabled && this.backingEnabled) {
				this.backing = null;
				this.backingEnabled = false;
			}
		}
	}
	invalidateBacking(): void {
		this.backingDirty = true;
	}
	drawBacking(gfx: Graphics, x: number, y: number): void {
		let backingGfx: Graphics = this.backingGraphics;
		let deltaX: number = ((x - this.backingX) | 0);
		let deltaY: number = ((y - this.backingY) | 0);
		let width: number = this.drawWidth;
		let height: number = this.drawHeight;
		if (!this.backingDirty && Math.abs(deltaX) <= width && Math.abs(deltaY) <= height) {
			let wrapX: number = this.wrapX;
			let wrapY: number = this.wrapY;
			let newWrapX: number = this.copyRows(backingGfx, x, wrapX, wrapY, deltaX, this.backingX, this.backingY);
			if (this.next !== null) {
				this.next.copyRows(backingGfx, x, wrapX, wrapY, deltaX, this.backingX, this.backingY);
			}
			this.wrapX = newWrapX;
			let newWrapY: number = this.copyColumns(backingGfx, y, wrapY, deltaY, this.backingY, x, newWrapX);
			if (this.next !== null) {
				this.next.copyColumns(backingGfx, y, wrapY, deltaY, this.backingY, x, newWrapX);
			}
			this.wrapY = newWrapY;
			let widthRemaining: number = ((width - this.wrapX) | 0);
			let heightRemaining: number = ((height - this.wrapY) | 0);
			backingGfx.setClip(0, 0, width, height);
			if (widthRemaining !== 0 && heightRemaining !== 0) {
				gfx.drawRegion(this.backing, this.wrapX, this.wrapY, widthRemaining, heightRemaining, 0, this.drawX, this.drawY, 20);
			}
			if (this.wrapX !== 0 && heightRemaining !== 0) {
				gfx.drawRegion(this.backing, 0, this.wrapY, this.wrapX, heightRemaining, 0, ((this.drawX + widthRemaining) | 0), this.drawY, 20);
			}
			if (widthRemaining !== 0 && this.wrapY !== 0) {
				gfx.drawRegion(this.backing, this.wrapX, 0, widthRemaining, this.wrapY, 0, this.drawX, ((heightRemaining + this.drawY) | 0), 20);
			}
			if (this.wrapX !== 0 && this.wrapY !== 0) {
				gfx.drawRegion(this.backing, 0, 0, this.wrapX, this.wrapY, 0, ((this.drawX + widthRemaining) | 0), ((heightRemaining + this.drawY) | 0), 20);
			}
		}
		else {
			this.backingDirty = false;
			this.drawCells(backingGfx, x, y, 0, 0, width, height);
			if (this.next !== null) {
				this.next.drawCells(backingGfx, x, y, 0, 0, width, height);
			}
			this.wrapX = 0;
			this.wrapY = 0;
		}
		this.backingX = x;
		this.backingY = y;
	}
	copyRows(gfx: Graphics, x: number, wrapX: number, wrapY: number, deltaX: number, backingX: number, backingY: number): number {
		let width: number = this.drawWidth;
		let height: number = this.drawHeight;
		let newWrapX: number = ((wrapX + deltaX) | 0);
		if (deltaX > 0) {
			let destX: number = ((((x + width) | 0) - deltaX) | 0);
			if (newWrapX > width) {
				this.drawCells(gfx, destX, backingY, wrapX, wrapY, ((width - wrapX) | 0), ((height - wrapY) | 0));
				this.drawCells(gfx, destX, ((((backingY + height) | 0) - wrapY) | 0), wrapX, 0, ((width - wrapX) | 0), wrapY);
				destX = ((destX + (((width - wrapX) | 0))) | 0);
				this.drawCells(gfx, destX, backingY, 0, wrapY, ((newWrapX - width) | 0), ((height - wrapY) | 0));
				this.drawCells(gfx, destX, ((((backingY + height) | 0) - wrapY) | 0), 0, 0, ((newWrapX - width) | 0), wrapY);
				newWrapX = ((newWrapX - width) | 0);
			}
			else {
				this.drawCells(gfx, destX, backingY, wrapX, wrapY, deltaX, ((height - wrapY) | 0));
				this.drawCells(gfx, destX, ((((backingY + height) | 0) - wrapY) | 0), wrapX, 0, deltaX, wrapY);
			}
		}
		else {
			if (deltaX < 0) {
				if (newWrapX < 0) {
					let baseX: number = ((backingX - wrapX) | 0);
					this.drawCells(gfx, baseX, backingY, 0, wrapY, wrapX, ((height - wrapY) | 0));
					this.drawCells(gfx, baseX, ((((backingY + height) | 0) - wrapY) | 0), 0, 0, wrapX, wrapY);
					baseX = ((baseX + newWrapX) | 0);
					this.drawCells(gfx, baseX, backingY, ((width + newWrapX) | 0), wrapY, ((newWrapX * -1) | 0), ((height - wrapY) | 0));
					this.drawCells(gfx, baseX, ((((backingY + height) | 0) - wrapY) | 0), ((width + newWrapX) | 0), 0, ((newWrapX * -1) | 0), wrapY);
					newWrapX = ((newWrapX + width) | 0);
				}
				else {
					this.drawCells(gfx, x, backingY, newWrapX, wrapY, ((deltaX * -1) | 0), ((height - wrapY) | 0));
					this.drawCells(gfx, x, ((((backingY + height) | 0) - wrapY) | 0), newWrapX, 0, ((deltaX * -1) | 0), wrapY);
				}
			}
		}
		return newWrapX;
	}
	copyColumns(gfx: Graphics, x: number, wrapY: number, deltaY: number, backingY: number, backingX: number, wrapX: number): number {
		let width: number = this.drawWidth;
		let height: number = this.drawHeight;
		let newWrapY: number = ((wrapY + deltaY) | 0);
		if (deltaY > 0) {
			let destY: number = ((((x + height) | 0) - deltaY) | 0);
			if (newWrapY > height) {
				this.drawCells(gfx, backingX, destY, wrapX, wrapY, ((width - wrapX) | 0), ((height - wrapY) | 0));
				this.drawCells(gfx, ((((backingX + width) | 0) - wrapX) | 0), destY, 0, wrapY, wrapX, ((height - wrapY) | 0));
				destY = ((destY + (((height - wrapY) | 0))) | 0);
				this.drawCells(gfx, backingX, destY, wrapX, 0, ((width - wrapX) | 0), ((newWrapY - height) | 0));
				this.drawCells(gfx, ((((backingX + width) | 0) - wrapX) | 0), destY, 0, 0, wrapX, ((newWrapY - height) | 0));
				newWrapY = ((newWrapY - height) | 0);
			}
			else {
				this.drawCells(gfx, backingX, destY, wrapX, wrapY, ((width - wrapX) | 0), deltaY);
				this.drawCells(gfx, ((((backingX + width) | 0) - wrapX) | 0), destY, 0, wrapY, wrapX, deltaY);
			}
		}
		else {
			if (deltaY < 0) {
				if (newWrapY < 0) {
					let baseY: number = ((backingY - wrapY) | 0);
					this.drawCells(gfx, backingX, baseY, wrapX, 0, ((width - wrapX) | 0), wrapY);
					this.drawCells(gfx, ((((backingX + width) | 0) - wrapX) | 0), baseY, 0, 0, wrapX, wrapY);
					baseY = ((baseY + newWrapY) | 0);
					this.drawCells(gfx, backingX, baseY, wrapX, ((height + newWrapY) | 0), ((width - wrapX) | 0), ((newWrapY * -1) | 0));
					this.drawCells(gfx, ((((backingX + width) | 0) - wrapX) | 0), baseY, 0, ((height + newWrapY) | 0), wrapX, ((newWrapY * -1) | 0));
					newWrapY = ((newWrapY + height) | 0);
				}
				else {
					this.drawCells(gfx, backingX, x, wrapX, newWrapY, ((width - wrapX) | 0), ((deltaY * -1) | 0));
					this.drawCells(gfx, ((((backingX + width) | 0) - wrapX) | 0), x, 0, newWrapY, wrapX, ((deltaY * -1) | 0));
				}
			}
		}
		return newWrapY;
	}
	constructor(...args: any[]) {
		if (args.length === 0) {
			this.frames = null;
			this.sheet = null;
			this.loaded = false;
			this.drawX = 0;
			this.drawY = 0;
			this.drawWidth = 0;
			this.drawHeight = 0;
			this.worldX = 0;
			this.worldY = 0;
		}
		else if (args.length === 2 && typeof args[0] === "number" && args[1] instanceof Image) {
			const index = args[0] as number;
			const image = args[1] as Image;
			this.frames = null;
			this.sheet = null;
			this.loaded = false;
			this.drawX = 0;
			this.drawY = 0;
			this.drawWidth = 0;
			this.drawHeight = 0;
			this.worldX = 0;
			this.worldY = 0;
			this.load(index);
			if (image !== null) {
				this.sheet = image;
			}
		}
		else if (args.length === 3 && typeof args[0] === "number" && typeof args[1] === "number" && args[2] instanceof Image) {
			const columns = args[0] as number;
			const rows = args[1] as number;
			const image = args[2] as Image;
			this.sheet = image;
			this.columns = columns;
			this.rowCountValue = rows;
			let cellCount: number = 0;
			let cells: Int8Array = new Int8Array(cellCount = Math.imul(columns, rows));
			for (let cellIndex: number = 0; cellIndex < cellCount; cellIndex++) {
				cells[cellIndex] = 0;
			}
			this.cellIndices = cells;
			this.frames = null;
			this.loaded = true;
		}
	}
}
