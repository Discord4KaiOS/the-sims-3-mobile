// Generated from Lot.java by tools/web/JavaToTs.java; keep this file in sync.
import { DataInputStream, IOException, arraycopy, DataOutputStream, Image, Graphics } from '../runtime';
import { GameCanvas } from './GameCanvas';
import { Tile } from './Tile';
import { ResourceLoader } from './ResourceLoader';
import { GameScreen } from './GameScreen';
import { Neighborhood } from './Neighborhood';
import { Sim } from './Sim';
import { Entity } from './Entity';
import { FixedMath } from './FixedMath';

export class Lot {
	canvas: GameCanvas = null!;
	objectKind: Int8Array = null!;
	spriteId: Int16Array = null!;
	spriteVariant: Int16Array = null!;
	objectFlags: Int32Array = null!;
	linkedId: Int16Array = null!;
	objectIds: Int16Array = null!;
	altIds: Int16Array = null!;
	objectWidth: Int8Array = null!;
	objectHeight: Int8Array = null!;
	subType: Int16Array = null!;
	attachments: Int16Array[] = null!;
	frameFlags: Int8Array[] = null!;
	frameMirrored: Int8Array[] = null!;
	frameRefs: Int16Array[] = null!;
	frameData: Int16Array = null!;
	frameParts: Int16Array[] = null!;
	layoutTables: Int8Array[] = null!;
	categoryIds: Int16Array = null!;
	categoryValues: Int16Array = null!;
	categoryMasks: Int32Array = null!;
	categoryTypes: Int16Array = null!;
	categorySubTypes: Int16Array = null!;
	categoryFlags: Int8Array = null!;
	categoryRefs: Int8Array = null!;
	categoryVolumes: Int8Array = null!;
	categoryLists: Int16Array[] = null!;
	lotIds: Int16Array = null!;
	floorX: Int8Array[] = null!;
	floorY: Int8Array[] = null!;
	floorWidth: Int8Array[] = null!;
	floorHeight: Int8Array[] = null!;
	floorEdgeA: Int8Array[] = null!;
	floorEdgeB: Int8Array[] = null!;
	wallX: Int8Array[] = null!;
	wallY: Int8Array[] = null!;
	wallLength: Int8Array[] = null!;
	levelObjectX: Int8Array[] = null!;
	levelObjectY: Int8Array[] = null!;
	levelObjectIds: Int16Array[] = null!;
	levelObjectFacing: Int8Array[] = null!;
	areaIdValue: number = 0;
	static STAGES: Int8Array = Int8Array.from([0, 1, 2]);
	static STAGE_MUSIC: Int16Array = Int16Array.from([166, 167, 168]);
	stageIndex: number = 0;
	liveObjectX: Int8Array = null!;
	liveObjectY: Int8Array = null!;
	liveObjectIds: Int16Array = null!;
	liveObjectFacing: Int8Array = null!;
	boundsX: number = 0;
	boundsY: number = 0;
	boundsWidth: number = 0;
	boundsHeight: number = 0;
	static DIRECTION_MASKS: Int32Array = Int32Array.from([128, 256, 512, 1024]);
	isoOriginX: number = 0;
	isoOriginY: number = 0;
	isoOriginXFixed: number = 0;
	isoOriginYFixed: number = 0;
	gridWidth: number = 0;
	gridHeight: number = 0;
	gridHalfWidth: number = 0;
	gridHalfHeight: number = 0;
	cellFlags: Int8Array = null!;
	groundTile: Tile = null!;
	overlayTile: Tile = null!;
	cameraX: number = 0;
	cameraY: number = 0;
	palettes: Int32Array[] = null!;
	categoryTables: Int8Array[] = null!;
	pathStartX: number = 0;
	pathStartY: number = 0;
	pathGoalX: number = 0;
	pathGoalY: number = 0;
	waypoints: Int32Array = null!;
	waypointCount: number = 0;
	pathCenterX: number = 0;
	pathCenterY: number = 0;
	openList: Int16Array = null!;
	openCount: number = 0;
	closedList: Int16Array = null!;
	closedCount: number = 0;
	parentLinks: Int16Array[] = null!;
	pathScore: Int32Array[] = null!;
	pathCost: Int32Array[] = null!;
	pathHeuristic: Int32Array[] = null!;
	pathResult: Int16Array = null!;
	pathFlags: number = 0;
	static readRef(stream: DataInputStream): number {
		return stream.readShort();
	}
	loadObjects(): void {
		let res: ResourceLoader = this.canvas.resources;
		try {
			let stream: DataInputStream = null!;
			let count: number = 0;
			let kinds: Int8Array = new Int8Array(count = (stream = new DataInputStream(res.openStream(64))).readShort());
			let spriteIds: Int16Array = new Int16Array(count);
			let spriteVariants: Int16Array = new Int16Array(count);
			let flags: Int32Array = new Int32Array(count);
			let linkedIds: Int16Array = new Int16Array(count);
			let ids: Int16Array = new Int16Array(count);
			let altIdValues: Int16Array = new Int16Array(count);
			let widths: Int8Array = new Int8Array(count);
			let heights: Int8Array = new Int8Array(count);
			let subTypes: Int16Array = new Int16Array(count);
			let attachmentLists: Int16Array[] = new Array(count).fill(null);
			let frameFlagLists: Int8Array[] = new Array(count).fill(null);
			let frameMirrorLists: Int8Array[] = new Array(count).fill(null);
			let frameRefLists: Int16Array[] = new Array(count).fill(null);
			for (let objIndex: number = 0; objIndex < count; objIndex++) {
				kinds[objIndex] = stream.readByte();
				spriteIds[objIndex] = Lot.readRef(stream);
				spriteVariants[objIndex] = Lot.readRef(stream);
				flags[objIndex] = stream.readInt();
				linkedIds[objIndex] = Lot.readRef(stream);
				if (linkedIds[objIndex] === -1) {
					linkedIds[objIndex] = 0;
				}
				ids[objIndex] = stream.readShort();
				altIdValues[objIndex] = stream.readShort();
				widths[objIndex] = stream.readByte();
				heights[objIndex] = stream.readByte();
				subTypes[objIndex] = Lot.readRef(stream);
				let attachmentCount: number = 0;
				let attachmentIds: Int16Array = new Int16Array(attachmentCount = stream.readByte());
				for (let attachIndex: number = 0; attachIndex < attachmentCount; attachIndex++) {
					attachmentIds[attachIndex] = stream.readShort();
				}
				let frameCount: number = 0;
				let frameFlagRow: Int8Array = new Int8Array(frameCount = stream.readByte());
				let frameMirrorRow: Int8Array = new Int8Array(frameCount);
				for (let frameIndex: number = 0; frameIndex < frameCount; frameIndex++) {
					frameFlagRow[frameIndex] = stream.readByte();
					frameMirrorRow[frameIndex] = stream.readByte();
				}
				let refCount: number = 0;
				let refIds: Int16Array = new Int16Array(refCount = stream.readByte());
				for (let refIndex: number = 0; refIndex < refCount; refIndex++) {
					refIds[refIndex] = Lot.readRef(stream);
				}
				attachmentLists[objIndex] = attachmentIds;
				frameFlagLists[objIndex] = frameFlagRow;
				frameMirrorLists[objIndex] = frameMirrorRow;
				frameRefLists[objIndex] = refIds;
			}
			let frameEntryCount: number = 0;
			let frameDataAll: Int16Array = new Int16Array(frameEntryCount = stream.readShort() << 2);
			for (let frameSlot: number = 0; frameSlot < frameEntryCount; frameSlot++) {
				frameDataAll[frameSlot] = Lot.readRef(stream);
			}
			let framePartCount: number = 0;
			let framePartRows: Int16Array[] = new Array(framePartCount = stream.readByte()).fill(null);
			for (let partIndex: number = 0; partIndex < framePartCount; partIndex++) {
				let partCount: number = 0;
				let partRow: Int16Array = new Int16Array(partCount = stream.readByte() << 1);
				for (let partSlot: number = 0; partSlot < partCount; partSlot++) {
					partRow[partSlot] = stream.readShort();
				}
				framePartRows[partIndex] = partRow;
			}
			stream.readByte();
			stream.readByte();
			let tableCount: number = 0;
			let tables: Int8Array[] = new Array(tableCount = stream.readByte()).fill(null);
			for (let tableIndex: number = 0; tableIndex < tableCount; tableIndex++) {
				let tableRow: Int8Array = new Int8Array(stream.readByte());
				stream.readFully(tableRow);
				tables[tableIndex] = tableRow;
			}
			let categoryCount: number = 0;
			let categoryIdValues: Int16Array = new Int16Array(categoryCount = stream.readByte());
			let categoryValueValues: Int16Array = new Int16Array(categoryCount);
			let categoryMaskValues: Int32Array = new Int32Array(categoryCount);
			let categoryTypeValues: Int16Array = new Int16Array(categoryCount);
			let categorySubTypeValues: Int16Array = new Int16Array(categoryCount);
			let categoryFlagValues: Int8Array = new Int8Array(categoryCount);
			for (let categoryIndex: number = 0; categoryIndex < categoryCount; categoryIndex++) {
				categoryIdValues[categoryIndex] = Lot.readRef(stream);
				categoryValueValues[categoryIndex] = Lot.readRef(stream);
				categoryMaskValues[categoryIndex] = stream.readInt();
				categoryTypeValues[categoryIndex] = stream.readShort();
				categorySubTypeValues[categoryIndex] = stream.readShort();
				categoryFlagValues[categoryIndex] = stream.readByte();
			}
			let refEntryCount: number = 0;
			let categoryRefValues: Int8Array = new Int8Array(refEntryCount = stream.readByte());
			let categoryVolumeValues: Int8Array = new Int8Array(refEntryCount);
			let categoryListRows: Int16Array[] = new Array(refEntryCount).fill(null);
			for (let listIndex: number = 0; listIndex < refEntryCount; listIndex++) {
				categoryRefValues[listIndex] = stream.readByte();
				categoryVolumeValues[listIndex] = stream.readByte();
				let listCount: number = 0;
				let listRow: Int16Array = new Int16Array(listCount = stream.readByte());
				for (let listSlot: number = 0; listSlot < listCount; listSlot++) {
					listRow[listSlot] = stream.readByte();
				}
				categoryListRows[listIndex] = listRow;
			}
			stream.close();
			this.objectKind = kinds;
			this.spriteId = spriteIds;
			this.spriteVariant = spriteVariants;
			this.objectFlags = flags;
			this.linkedId = linkedIds;
			this.objectIds = ids;
			this.altIds = altIdValues;
			this.objectWidth = widths;
			this.objectHeight = heights;
			this.subType = subTypes;
			this.attachments = attachmentLists;
			this.frameFlags = frameFlagLists;
			this.frameMirrored = frameMirrorLists;
			this.frameRefs = frameRefLists;
			this.frameData = frameDataAll;
			this.frameParts = framePartRows;
			this.layoutTables = tables;
			this.categoryIds = categoryIdValues;
			this.categoryValues = categoryValueValues;
			this.categoryMasks = categoryMaskValues;
			this.categoryTypes = categoryTypeValues;
			this.categorySubTypes = categorySubTypeValues;
			this.categoryFlags = categoryFlagValues;
			this.categoryRefs = categoryRefValues;
			this.categoryVolumes = categoryVolumeValues;
			this.categoryLists = categoryListRows;
		}
		catch (ignored) {
			if (ignored instanceof IOException) {
			}
			else {
				throw ignored;
			}
		}
	}
	static readShortValue(stream: DataInputStream): number {
		return stream.readShort();
	}
	loadPlacements(): void {
		let res: ResourceLoader = this.canvas.resources;
		try {
			let stream: DataInputStream = null!;
			let count: number = 0;
			let lotIdValues: Int16Array = new Int16Array(count = (stream = new DataInputStream(res.openStream(62))).readByte());
			let floorXLists: Int8Array[] = new Array(count).fill(null);
			let floorYLists: Int8Array[] = new Array(count).fill(null);
			let floorWidthLists: Int8Array[] = new Array(count).fill(null);
			let floorHeightLists: Int8Array[] = new Array(count).fill(null);
			let floorEdgeALists: Int8Array[] = new Array(count).fill(null);
			let floorEdgeBLists: Int8Array[] = new Array(count).fill(null);
			let wallXLists: Int8Array[] = new Array(count).fill(null);
			let wallYLists: Int8Array[] = new Array(count).fill(null);
			let wallLengthLists: Int8Array[] = new Array(count).fill(null);
			let objectXLists: Int8Array[] = new Array(count).fill(null);
			let objectYLists: Int8Array[] = new Array(count).fill(null);
			let objectIdLists: Int16Array[] = new Array(count).fill(null);
			let objectFacingLists: Int8Array[] = new Array(count).fill(null);
			for (let objIndex: number = 0; objIndex < count; objIndex++) {
				lotIdValues[objIndex] = Lot.readShortValue(stream);
				let rowCount: number = 0;
				let floorXRow: Int8Array = new Int8Array(rowCount = stream.readByte());
				let floorYRow: Int8Array = new Int8Array(rowCount);
				let floorWidthRow: Int8Array = new Int8Array(rowCount);
				let floorHeightRow: Int8Array = new Int8Array(rowCount);
				let floorEdgeARow: Int8Array = new Int8Array(rowCount);
				let floorEdgeBRow: Int8Array = new Int8Array(rowCount);
				for (let floorIndex: number = 0; floorIndex < rowCount; floorIndex++) {
					floorXRow[floorIndex] = stream.readByte();
					floorYRow[floorIndex] = stream.readByte();
					floorWidthRow[floorIndex] = stream.readByte();
					floorHeightRow[floorIndex] = stream.readByte();
					floorEdgeARow[floorIndex] = (Lot.readShortValue(stream) << 24 >> 24);
					floorEdgeBRow[floorIndex] = (Lot.readShortValue(stream) << 24 >> 24);
				}
				let wallCount: number = 0;
				let wallXRow: Int8Array = new Int8Array(wallCount = stream.readByte());
				let wallYRow: Int8Array = new Int8Array(wallCount);
				let wallLengthRow: Int8Array = new Int8Array(wallCount);
				for (let wallIndex: number = 0; wallIndex < wallCount; wallIndex++) {
					wallXRow[wallIndex] = stream.readByte();
					wallYRow[wallIndex] = stream.readByte();
					wallLengthRow[wallIndex] = stream.readByte();
				}
				let objectCount: number = 0;
				let objectXCol: Int8Array = new Int8Array(objectCount = stream.readByte());
				let objectYCol: Int8Array = new Int8Array(objectCount);
				let objectIdCol: Int16Array = new Int16Array(objectCount);
				let objectFacingCol: Int8Array = new Int8Array(objectCount);
				for (let objectIndex: number = 0; objectIndex < objectCount; objectIndex++) {
					objectXCol[objectIndex] = stream.readByte();
					objectYCol[objectIndex] = stream.readByte();
					objectIdCol[objectIndex] = Lot.readShortValue(stream);
					objectFacingCol[objectIndex] = (Lot.readShortValue(stream) << 24 >> 24);
				}
				floorXLists[objIndex] = floorXRow;
				floorYLists[objIndex] = floorYRow;
				floorWidthLists[objIndex] = floorWidthRow;
				floorHeightLists[objIndex] = floorHeightRow;
				floorEdgeALists[objIndex] = floorEdgeARow;
				floorEdgeBLists[objIndex] = floorEdgeBRow;
				wallXLists[objIndex] = wallXRow;
				wallYLists[objIndex] = wallYRow;
				wallLengthLists[objIndex] = wallLengthRow;
				objectXLists[objIndex] = objectXCol;
				objectYLists[objIndex] = objectYCol;
				objectIdLists[objIndex] = objectIdCol;
				objectFacingLists[objIndex] = objectFacingCol;
			}
			stream.close();
			this.lotIds = lotIdValues;
			this.floorX = floorXLists;
			this.floorY = floorYLists;
			this.floorWidth = floorWidthLists;
			this.floorHeight = floorHeightLists;
			this.floorEdgeA = floorEdgeALists;
			this.floorEdgeB = floorEdgeBLists;
			this.wallX = wallXLists;
			this.wallY = wallYLists;
			this.wallLength = wallLengthLists;
			this.levelObjectX = objectXLists;
			this.levelObjectY = objectYLists;
			this.levelObjectIds = objectIdLists;
			this.levelObjectFacing = objectFacingLists;
		}
		catch (ignored) {
			if (ignored instanceof IOException) {
			}
			else {
				throw ignored;
			}
		}
	}
	areaId(): number {
		return GameCanvas.indexOfByte(this.areaIdValue, Lot.STAGES) !== -1 ? 0 : this.areaIdValue;
	}
	lotIdAt(lotId: number): number {
		return this.lotIds[lotId];
	}
	indexOfLotId(lotId: number): number {
		return GameCanvas.indexOfShort(lotId, this.lotIds);
	}
	resetLiveObjects(): void {
		this.stageIndex = 0;
		if (this.liveObjectX === null) {
			this.liveObjectX = new Int8Array(150);
			this.liveObjectY = new Int8Array(150);
			this.liveObjectIds = new Int16Array(150);
			this.liveObjectFacing = new Int8Array(150);
		}
		GameCanvas.fillShorts(this.liveObjectIds, -1);
		let levelX: Int8Array = this.levelObjectX[0];
		let levelY: Int8Array = this.levelObjectY[0];
		let levelIds: Int16Array = this.levelObjectIds[0];
		let levelFacings: Int8Array = this.levelObjectFacing[0];
		arraycopy(levelX, 0, this.liveObjectX, 0, levelX.length);
		arraycopy(levelY, 0, this.liveObjectY, 0, levelY.length);
		arraycopy(levelIds, 0, this.liveObjectIds, 0, levelIds.length);
		arraycopy(levelFacings, 0, this.liveObjectFacing, 0, levelFacings.length);
	}
	stageReward(): number {
		if (this.stageIndex === ((Lot.STAGES.length - 1) | 0)) {
			return -1;
		}
		else {
			if (this.stageIndex === 0) {
				return this.canvas.neighborhood.hasMilestone(16) ? 1000 : 0;
			}
			else {
				return this.canvas.neighborhood.hasMilestone(32) ? 2500 : 0;
			}
		}
	}
	isFinalStage(): boolean {
		return this.stageIndex === ((Lot.STAGES.length - 1) | 0);
	}
	stageMusicId(): number {
		return Lot.STAGE_MUSIC[this.stageIndex];
	}
	allItemsPlaced(): boolean {
		let placedCount: number = 0;
		let ids: Int16Array = this.liveObjectIds;
		let flags: Int32Array = this.objectFlags;
		for (let slotIndex: number = ((ids.length - 1) | 0); slotIndex >= 0; slotIndex--) {
			let id: number = 0;
			if ((id = ids[slotIndex]) !== -1 && (flags[id] & 131072) !== 0 && GameCanvas.indexOfShortFrom(id, ids, ((slotIndex + 1) | 0)) === -1) {
				placedCount++;
			}
		}
		let expectedCount: number = GameCanvas.countAnyFlagInt(131072, flags);
		return placedCount === expectedCount;
	}
	nextStage(): void {
		this.stageIndex++;
		let level: number = Lot.STAGES[this.stageIndex];
		let levelX: Int8Array = this.levelObjectX[level];
		let levelY: Int8Array = this.levelObjectY[level];
		let levelIds: Int16Array = this.levelObjectIds[level];
		let levelFacings: Int8Array = this.levelObjectFacing[level];
		let ids: Int16Array = this.liveObjectIds;
		let liveXs: Int8Array = this.liveObjectX;
		let liveYs: Int8Array = this.liveObjectY;
		let facings: Int8Array = this.liveObjectFacing;
		for (let slotIndex: number = 0; slotIndex < levelIds.length; slotIndex++) {
			let id: number = levelIds[slotIndex];
			let x: number = levelX[slotIndex];
			let y: number = levelY[slotIndex];
			if (id !== ids[slotIndex]) {
				if (ids[slotIndex] !== -1) {
					let freeSlot: number = GameCanvas.indexOfShort(-1, ids);
					ids[freeSlot] = ids[slotIndex];
					liveXs[freeSlot] = liveXs[slotIndex];
					liveYs[freeSlot] = liveYs[slotIndex];
					facings[freeSlot] = facings[slotIndex];
					this.canvas.neighborhood.setTipValue(slotIndex, freeSlot);
				}
			}
			else {
				if ((this.objectFlagsAt(id) & 4) !== 0) {
					let baseX: number = liveXs[slotIndex];
					let baseY: number = liveYs[slotIndex];
					for (let otherIndex: number = 0; otherIndex < 150; otherIndex++) {
						let otherId: number = 0;
						if (slotIndex !== otherIndex && (otherId = ids[otherIndex]) !== -1) {
							let otherFlags: number = this.objectFlagsAt(otherId);
							let otherX: number = liveXs[otherIndex];
							let otherY: number = liveYs[otherIndex];
							if ((otherFlags & 524292) === 0 && otherX === baseX && otherY === baseY) {
								liveXs[otherIndex] = x;
								liveYs[otherIndex] = y;
								if ((otherFlags & 262144) !== 0) {
									let width: number = this.objectWidthAt(otherId);
									let height: number = this.objectHeightAt(otherId);
									let facing: number = 0;
									if ((facing = facings[otherIndex]) === 1 || facing === 3) {
										let swap: number = width;
										width = height;
										height = swap;
									}
									for (let areaSlotIndex: number = 0; areaSlotIndex < 150; areaSlotIndex++) {
										let idAt: number = 0;
										if ((idAt = ids[areaSlotIndex]) !== -1 && (this.objectFlagsAt(idAt) & 524288) !== 0) {
											let xAt: number = liveXs[areaSlotIndex];
											let yAt: number = liveYs[areaSlotIndex];
											if (xAt <= otherX && yAt <= otherY && xAt > ((otherX - width) | 0) && yAt > ((otherY - height) | 0)) {
												liveXs[areaSlotIndex] = ((((liveXs[areaSlotIndex] + (((x - otherX) | 0))) | 0)) << 24 >> 24);
												liveYs[areaSlotIndex] = ((((liveYs[areaSlotIndex] + (((y - otherY) | 0))) | 0)) << 24 >> 24);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			ids[slotIndex] = id;
			liveXs[slotIndex] = x;
			liveYs[slotIndex] = y;
			facings[slotIndex] = levelFacings[slotIndex];
		}
		this.canvas.neighborhood.markPromotionDay();
	}
	addObject(objectTypeId: number, worldX: number, worldY: number, facing: number): number {
		let slot: number = GameCanvas.indexOfShort(-1, this.liveObjectIds);
		this.liveObjectIds[slot] = (objectTypeId << 16 >> 16);
		this.liveObjectX[slot] = (Lot.toTileX(worldX) << 24 >> 24);
		this.liveObjectY[slot] = (Lot.toTileY(worldY) << 24 >> 24);
		this.liveObjectFacing[slot] = (facing << 24 >> 24);
		return slot;
	}
	removeObject(slot: number): void {
		this.liveObjectIds[slot] = -1;
		this.canvas.neighborhood.removeTip(0, slot);
	}
	setObjectFacing(slot: number, facing: number): void {
		this.liveObjectFacing[slot] = (facing << 24 >> 24);
	}
	setObjectId(slot: number, objectId: number): void {
		this.liveObjectIds[slot] = (objectId << 16 >> 16);
	}
	setObjectPosition(slot: number, worldX: number, worldY: number, facing: number): void {
		this.liveObjectX[slot] = (Lot.toTileX(worldX) << 24 >> 24);
		this.liveObjectY[slot] = (Lot.toTileY(worldY) << 24 >> 24);
		this.liveObjectFacing[slot] = (facing << 24 >> 24);
	}
	hasObjectKind(kind: number): boolean {
		let ids: Int16Array = this.liveObjectIds;
		for (let slotIndex: number = 0; slotIndex < ids.length; slotIndex++) {
			let id: number = 0;
			if ((id = ids[slotIndex]) !== -1 && this.objectKindAt(id) === kind) {
				return true;
			}
		}
		return false;
	}
	hasObject(objectId: number): boolean {
		return GameCanvas.indexOfShort(objectId, this.liveObjectIds) !== -1;
	}
	resetStages(): void {
		this.stageIndex = 0;
		this.resetLiveObjects();
	}
	readObjects(stream: DataInputStream): void {
		this.stageIndex = stream.readByte();
		for (let slot: number = 0; slot < this.liveObjectIds.length; slot++) {
			this.liveObjectIds[slot] = stream.readShort();
			this.liveObjectX[slot] = stream.readByte();
			this.liveObjectY[slot] = stream.readByte();
			this.liveObjectFacing[slot] = stream.readByte();
		}
	}
	writeObjects(out: DataOutputStream): void {
		out.writeByte(this.stageIndex);
		for (let slot: number = 0; slot < this.liveObjectIds.length; slot++) {
			out.writeShort(this.liveObjectIds[slot]);
			out.writeByte(this.liveObjectX[slot]);
			out.writeByte(this.liveObjectY[slot]);
			out.writeByte(this.liveObjectFacing[slot]);
		}
	}
	setBounds(x: number, y: number, width: number, height: number): void {
		this.boundsX = x;
		this.boundsY = y;
		this.boundsWidth = width;
		this.boundsHeight = height;
	}
	initGrid(): void {
		this.gridWidth = 64;
		this.gridHeight = 64;
		this.gridHalfWidth = this.gridWidth >> 1;
		this.gridHalfHeight = this.gridHeight >> 1;
		this.isoOriginX = 512;
		this.isoOriginXFixed = this.isoOriginX << 16;
		this.isoOriginY = 264;
		this.isoOriginYFixed = this.isoOriginY << 16;
		if (this.cellFlags === null) {
			this.cellFlags = new Int8Array(Math.imul(this.gridWidth, this.gridHeight));
		}
		GameCanvas.fillBytes(this.cellFlags, 0);
	}
	loadArea(area: number): void {
		if (area === 0) {
			area = Lot.STAGES[this.stageIndex];
		}
		this.areaIdValue = area;
		this.initGrid();
		this.buildGroundTiles(area);
		this.buildScene(area);
	}
	buildScene(level: number): void {
		let screen: GameScreen = (this.canvas.screen as unknown as GameScreen);
		let xRow: Int8Array = this.levelObjectX[level];
		let yRow: Int8Array = this.levelObjectY[level];
		let ids: Int16Array = this.levelObjectIds[level];
		let facings: Int8Array = this.levelObjectFacing[level];
		if (this.areaId() === 0) {
			xRow = this.liveObjectX;
			yRow = this.liveObjectY;
			ids = this.liveObjectIds;
			facings = this.liveObjectFacing;
		}
		let spawnFound: boolean = false;
		let spawnX: number = 0;
		let spawnY: number = 0;
		let count: number = ids.length;
		for (let slotIndex: number = 0; slotIndex < count; slotIndex++) {
			let id: number = 0;
			if ((id = ids[slotIndex]) !== -1) {
				let x: number = xRow[slotIndex];
				let y: number = yRow[slotIndex];
				let facing: number = facings[slotIndex];
				screen.createEntityAtTile(id, x, y, facing, slotIndex);
				if (!spawnFound && this.objectKind[id] === 16) {
					spawnFound = true;
					spawnX = x;
					spawnY = y;
				}
			}
		}
		if (spawnFound) {
			let halfWidth: number = this.gridHalfWidth;
			spawnX = ((spawnX + halfWidth) | 0);
			spawnY = ((spawnY + 0) | 0);
			this.paintRoomEdges(spawnX, spawnY, 3, 3);
		}
		this.buildRoomEdges();
		let spawnMode: number = this.areaId() === 0 ? 2 : 3;
		spawnX = GameCanvas.indexOfShort(spawnMode, ids);
		let playerSlot: number = xRow[spawnX];
		let playerY: number = yRow[spawnX];
		let playerFacing: number = facings[spawnX];
		screen.createEntityAtTile(0, playerSlot, playerY, playerFacing, 0);
		playerSlot = spawnMode === 2 ? spawnX : -1;
		let neighborhood: Neighborhood = screen.neighborhood;
		let simCount: number = screen.neighborhood.simCount;
		for (let simIndex: number = 1; simIndex < simCount; simIndex++) {
			let simFlags: number = 0;
			if (neighborhood.simHomeLotOf(simIndex) === level && ((simFlags = neighborhood.lotStateNow(simIndex)) & 8) !== 0) {
				playerSlot = GameCanvas.indexOfShortFrom(2, ids, ((playerSlot + 1) | 0));
				let simX: number = xRow[playerSlot];
				let simY: number = yRow[playerSlot];
				let simFacing: number = facings[playerSlot];
				let sim: Sim = (screen.createEntityAtTile(1, simX, simY, simFacing, simIndex) as unknown as Sim);
				if ((simFlags & 16) !== 0 && this.canvas.randomPercent() < 30) {
					screen.assignFreeWillAction(sim);
				}
			}
		}
	}
	hasObjectAt(tileX: number, tileY: number): boolean {
		let xValues: Int8Array = this.liveObjectX;
		let yValues: Int8Array = this.liveObjectY;
		let ids: Int16Array = this.liveObjectIds;
		for (let slotIndex: number = 0; slotIndex < ids.length; slotIndex++) {
			let id: number = 0;
			if ((id = ids[slotIndex]) !== -1 && (this.objectFlagsAt(id) & 524288) !== 0 && tileX === xValues[slotIndex] && tileY === yValues[slotIndex]) {
				return true;
			}
		}
		return false;
	}
	static directionOfMask(mask: number): number {
		for (let index: number = 0; index < 4; index++) {
			if ((mask & Lot.DIRECTION_MASKS[index]) !== 0) {
				return index;
			}
		}
		return -1;
	}
	footprintFree(x: number, y: number, width: number, length: number, direction: number): boolean {
		let free: boolean = true;
		if (direction !== 0 && direction !== 2) {
			if (direction === 1 || direction === 3) {
				let stepAlt: number = 1;
				if (direction === 3) {
					stepAlt = -1;
				}
				let xAtAlt: number = x;
				let yAtAlt: number = ((y - Math.imul(width, stepAlt)) | 0);
				for (let stepIndex: number = 0; stepIndex < length && free; stepIndex++) {
					free = (this.cellFlagsAt(((xAtAlt - Math.imul(stepIndex, stepAlt)) | 0), yAtAlt) & 64) !== 0;
				}
			}
		}
		else {
			let step: number = 1;
			if (direction === 2) {
				step = -1;
			}
			let xAt: number = ((x - Math.imul(width, step)) | 0);
			let yAt: number = y;
			for (let altStepIndex: number = 0; altStepIndex < length && free; altStepIndex++) {
				free = (this.cellFlagsAt(xAt, ((yAt - Math.imul(altStepIndex, step)) | 0)) & 64) !== 0;
			}
		}
		return free;
	}
	directionMaskAt(objectTypeId: number, x: number, y: number): number {
		let flags: number = this.objectFlagsAt(objectTypeId);
		let width: number = this.objectWidthAt(objectTypeId);
		let height: number = this.objectHeightAt(objectTypeId);
		let frames: number = this.frameCountAt(objectTypeId);
		let mask: number = 0;
		for (let dir: number = 0; dir < Lot.DIRECTION_MASKS.length; dir++) {
			let dirMask: number = Lot.DIRECTION_MASKS[dir];
			if ((flags & dirMask) !== 0) {
				let widthRotated: number = width;
				let heightRotated: number = height;
				if (dir === 1 || dir === 3) {
					widthRotated = height;
					heightRotated = width;
				}
				if ((flags & 524288) !== 0 || this.isAreaPassable(x, y, widthRotated, heightRotated)) {
					if (frames > 0) {
						let hits: number = 0;
						for (let frame: number = 0; frame < frames; frame++) {
							let dx: number = this.framePartX(objectTypeId, frame, dir);
							let dy: number = this.framePartY(objectTypeId, frame, dir);
							if (this.isPassable(((x + dx) | 0), ((y + dy) | 0))) {
								hits++;
							}
						}
						if (hits < 1) {
							continue;
						}
					}
					if (((flags & 1048576) === 0 || this.footprintFree(x, y, width, height, dir)) && ((flags & 2097152) === 0 || !this.footprintFree(x, y, width, height, dir))) {
						mask = (mask | dirMask);
					}
				}
			}
		}
		return mask;
	}
	spawnWalkers(objectTypeId: number): number {
		let screen: GameScreen = (this.canvas.screen as unknown as GameScreen);
		let xValues: Int8Array = this.liveObjectX;
		let yValues: Int8Array = this.liveObjectY;
		let ids: Int16Array = this.liveObjectIds;
		let facings: Int8Array = this.liveObjectFacing;
		let flags: number = this.objectFlagsAt(objectTypeId);
		let width: number = this.objectWidthAt(objectTypeId);
		let height: number = this.objectHeightAt(objectTypeId);
		let spawned: number = 0;
		let count: number = ids.length;
		for (let slotIndex: number = 0; slotIndex < count; slotIndex++) {
			let id: number = 0;
			if ((id = ids[slotIndex]) !== -1) {
				let x: number = xValues[slotIndex];
				let y: number = yValues[slotIndex];
				let facing: number = facings[slotIndex];
				let otherFlags: number = this.objectFlagsAt(id);
				let otherWidth: number = this.objectWidthAt(id);
				let otherHeight: number = this.objectHeightAt(id);
				if ((flags & 524288) !== 0) {
					if ((otherFlags & 262144) !== 0) {
						if (facing === 1 || facing === 3) {
							let swap: number = otherWidth;
							otherWidth = otherHeight;
							otherHeight = swap;
						}
						for (let dx: number = 0; dx < otherWidth; dx++) {
							for (let dy: number = 0; dy < otherHeight; dy++) {
								let xAt: number = ((x - dx) | 0);
								let yAt: number = ((y - dy) | 0);
								let dirAlt: number = 0;
								if (!this.hasObjectAt(xAt, yAt) && (dirAlt = Lot.directionOfMask(this.directionMaskAt(objectTypeId, xAt, yAt))) !== -1) {
									screen.createEntityAtTile(11, xAt, yAt, dirAlt, -1);
									spawned++;
								}
							}
						}
					}
				}
				else {
					let dir: number = 0;
					if ((otherFlags & 4) !== 0 && otherWidth === width && otherHeight === height && (dir = Lot.directionOfMask(this.directionMaskAt(objectTypeId, x, y))) !== -1) {
						screen.createEntityAtTile(10, x, y, dir, -1);
						spawned++;
					}
				}
			}
		}
		return spawned;
	}
	openHouse(imageId: number): void {
		this.initGrid();
		let canvas: GameCanvas = this.canvas;
		let image: Image = ResourceLoader.loadImage(0);
		let tile: Tile = null!;
		(tile = new Tile(imageId, image)).setBounds(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
		tile.setBackingEnabled(true);
		this.groundTile = tile;
		this.markBorderCells(imageId);
		this.createFurniture(true);
		let screen: GameScreen = null!;
		let sim: Entity = null!;
		let x: number = Lot.toTileX((sim = (screen = this.canvas.gameScreen()).findEntityByType(3)).posX);
		let y: number = Lot.toTileY(sim.posY);
		screen.createEntityAtTile(0, x, y, 3, 0);
		let simId: number = canvas.walkTargetFrame;
		if (canvas.walkTargetFrame !== -1) {
			let entity: Entity = screen.createEntityAtTile(1, x, ((y - 1) | 0), 1, simId);
			screen.followEntity(entity);
			screen.openInteractionMenu(entity);
		}
	}
	openArea(resetView: boolean): void {
		this.initGrid();
		let canvas: GameCanvas = this.canvas;
		let image: Image = ResourceLoader.loadImage(1);
		let tile: Tile = null!;
		(tile = new Tile(40, image)).setBounds(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
		tile.setBackingEnabled(true);
		this.groundTile = tile;
		this.markBlockedCells();
		this.createFurniture(false);
		let screen: GameScreen = canvas.gameScreen();
		if (resetView) {
			let neighborhood: Neighborhood = null!;
			let simIndex: number = (neighborhood = screen.neighborhood).lotOccupantType(neighborhood.simLot(0));
			let sim: Entity = screen.findEntityByType(simIndex);
			screen.createEntityNear(0, sim, 0);
			screen.startInteraction(159, sim, 0, 0);
		}
		else {
			let savedLotIndex: number = canvas.houseIndex;
			if (canvas.houseIndex !== -1) {
				this.areaIdValue = 13;
				let lotId: number = this.lotIdAt(savedLotIndex);
				let savedEntity: Entity = screen.findEntityByType(lotId);
				screen.createEntityNear(0, savedEntity, 0);
			}
			else {
				screen.createEntity(0, canvas.simPosX, canvas.simPosY, 0, 0);
				let simId: number = canvas.walkTargetFrame;
				if (canvas.walkTargetFrame !== -1) {
					screen.createEntity(1, canvas.walkTargetX, canvas.walkTargetY, 0, simId);
				}
				canvas.enterHouseWith((null as unknown as Sim));
			}
		}
	}
	createFurniture(snapToTiles: boolean): void {
		let screen: GameScreen = this.canvas.gameScreen();
		let tile: Tile = this.groundTile;
		let frameCount: number = this.groundTile.frameCount();
		let frames: Int16Array = tile.frames;
		for (let frame: number = 0; frame < frameCount; frame++) {
			let offset: number = Math.imul(frame, 3);
			let spriteId: number = frames[((offset + 0) | 0)];
			let x: number = frames[((offset + 1) | 0)] << 16;
			let y: number = frames[((offset + 2) | 0)] << 16;
			let worldX: number = this.toWorldX(x, y);
			let worldY: number = this.toWorldY(x, y);
			if (snapToTiles) {
				worldX = Lot.toFixedX(Lot.toTileX(worldX));
				worldY = Lot.toFixedY(Lot.toTileY(worldY));
			}
			screen.createEntity(spriteId, worldX, worldY, 0, -1);
		}
		let rowCount: number = tile.rowCount();
		let rows: Int16Array = tile.rows;
		for (let row: number = 0; row < rowCount; row++) {
			let rowOffset: number = Math.imul(row, 3);
			let value: number = rows[((rowOffset + 0) | 0)];
			let rowX: number = rows[((rowOffset + 1) | 0)] << 16;
			let rowY: number = rows[((rowOffset + 2) | 0)] << 16;
			let rowWorldX: number = this.toWorldX(rowX, rowY);
			let rowWorldY: number = this.toWorldY(rowX, rowY);
			if (snapToTiles) {
				rowWorldX = Lot.toFixedX(Lot.toTileX(rowWorldX));
				rowWorldY = Lot.toFixedY(Lot.toTileY(rowWorldY));
			}
			let entity: Entity = null!;
			(entity = screen.createEntity(98, rowWorldX, rowWorldY, 0, -1)).playIdleAnimation(value);
			if (value === 170 || value === 171 || value === 172 || value === 173 || value === 174) {
				entity.setValueC(-50);
			}
			else {
				if (value === 151 || value === 152 || value === 153 || value === 154) {
					entity.setValueC(-18);
				}
			}
		}
	}
	dispose(): void {
		this.groundTile = null;
		this.overlayTile = null;
	}
	draw(gfx: Graphics): void {
		this.drawScene(gfx);
	}
	viewCenterX(): number {
		return ((((this.boundsX + (this.boundsWidth >> 1)) | 0) - (this.cameraX >> 16)) | 0);
	}
	viewCenterY(): number {
		return ((((this.boundsY + (this.boundsHeight >> 1)) | 0) - (this.cameraY >> 16)) | 0);
	}
	toScreenFixedX(worldX: number, worldY: number): number {
		return (((((worldX - worldY) | 0) << 1) + this.isoOriginXFixed) | 0);
	}
	toScreenFixedY(worldX: number, worldY: number): number {
		return ((((worldX + worldY) | 0) + this.isoOriginYFixed) | 0);
	}
	toScreenX(worldX: number, worldY: number): number {
		return (((((worldX - worldY) | 0) >> 15) + this.isoOriginX) | 0);
	}
	toScreenY(worldX: number, worldY: number): number {
		return (((((worldX + worldY) | 0) >> 16) + this.isoOriginY) | 0);
	}
	toWorldX(screenX: number, screenY: number): number {
		return (((((screenX - this.isoOriginXFixed) | 0) >> 1) + (((screenY - this.isoOriginYFixed) | 0))) | 0) >> 1;
	}
	toWorldY(screenX: number, screenY: number): number {
		return ((((((((screenX - this.isoOriginXFixed) | 0)) * -1) | 0) >> 1) + (((screenY - this.isoOriginYFixed) | 0))) | 0) >> 1;
	}
	static subtract(left: number, right: number): number {
		return ((left - right) | 0);
	}
	static add(left: number, right: number): number {
		return ((left + right) | 0);
	}
	static toFixedX(value: number): number {
		return (((value << 19) + 262144) | 0);
	}
	static toFixedY(value: number): number {
		return (((value << 19) + 262144) | 0);
	}
	static toTileX(value: number): number {
		return value >> 19;
	}
	static toTileY(value: number): number {
		return value >> 19;
	}
	isWalkableAt(x: number, y: number): boolean {
		return this.isPassable(Lot.toTileX(x), Lot.toTileY(y));
	}
	isPassable(x: number, y: number): boolean {
		return (this.cellFlagsAt(x, y) & 128) === 0;
	}
	cellFlagsAt(x: number, y: number): number {
		x = ((x + this.gridHalfWidth) | 0);
		y = ((y + this.gridHalfHeight) | 0);
		if (x >= 0 && x < this.gridWidth && y >= 0 && y < this.gridHeight) {
			let index: number = ((x + Math.imul(y, this.gridWidth)) | 0);
			return this.cellFlags[index];
		}
		else {
			return 128;
		}
	}
	setCellFlags(x: number, y: number, clearMask: number, setMask: number): void {
		x = ((x + this.gridHalfWidth) | 0);
		y = ((y + this.gridHalfHeight) | 0);
		if (x >= 0 && x < this.gridWidth && y >= 0 && y < this.gridHeight) {
			let cells: Int8Array = this.cellFlags;
			let index: number = ((x + Math.imul(y, this.gridWidth)) | 0);
			let value: number = cells[index] & clearMask;
			cells[index] = ((value | setMask) << 24 >> 24);
		}
	}
	fillCellFlags(x: number, y: number, width: number, height: number, clearMask: number, setMask: number): void {
		let cells: Int8Array = this.cellFlags;
		let stride: number = this.gridWidth;
		let startX: number = ((this.gridHalfWidth + x) | 0);
		let x1: number = ((((this.gridHalfWidth + x) | 0) + width) | 0);
		let startY: number = ((this.gridHalfHeight + y) | 0);
		let y1: number = ((((this.gridHalfHeight + y) | 0) + height) | 0);
		for (let col: number = startX; col < x1; col++) {
			for (let row: number = startY; row < y1; row++) {
				let index: number = ((col + Math.imul(row, stride)) | 0);
				let value: number = cells[index] & clearMask;
				cells[index] = ((value | setMask) << 24 >> 24);
			}
		}
	}
	isAreaPassable(x: number, y: number, width: number, height: number): boolean {
		for (let dx: number = 0; dx < width; dx++) {
			for (let dy: number = 0; dy < height; dy++) {
				if (!this.isPassable(((x - dx) | 0), ((y - dy) | 0))) {
					return false;
				}
			}
		}
		return true;
	}
	drawScene(gfx: Graphics): void {
		let tile: Tile = this.groundTile;
		let clipX: number = gfx.getClipX();
		let clipY: number = gfx.getClipY();
		let clipWidth: number = gfx.getClipWidth();
		let clipHeight: number = gfx.getClipHeight();
		gfx.setClip(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
		tile.worldX = ((this.cameraX - (this.boundsWidth >> 1 << 16)) | 0);
		tile.worldY = ((this.cameraY - (this.boundsHeight >> 1 << 16)) | 0);
		tile.draw(gfx);
		this.drawEntities(gfx);
		gfx.setClip(clipX, clipY, clipWidth, clipHeight);
	}
	buildGroundTiles(level: number): void {
		if (this.groundTile === null) {
			let image: Image = ResourceLoader.loadImage(0);
			let tile: Tile = null!;
			(tile = new Tile(64, 64, image)).setBounds(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
			tile.setBackingEnabled(true);
			this.groundTile = tile;
		}
		if (this.overlayTile === null) {
			let overlayImage: Image = ResourceLoader.loadImage(2);
			let overlay: Tile = new Tile(64, 64, overlayImage);
			this.overlayTile = overlay;
			overlay.setBounds(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
			this.groundTile.next = overlay;
		}
		let floorXs: Int8Array = this.floorX[level];
		let floorYs: Int8Array = this.floorY[level];
		let widths: Int8Array = this.floorWidth[level];
		let heights: Int8Array = this.floorHeight[level];
		let edgeA: Int8Array = this.floorEdgeA[level];
		let edgeB: Int8Array = this.floorEdgeB[level];
		let wallsX: Int8Array = this.wallX[level];
		let wallsY: Int8Array = this.wallY[level];
		let wallLengths: Int8Array = this.wallLength[level];
		let floorCount: number = floorXs.length;
		let wallCount: number = wallsX.length;
		for (let floorIndex: number = 0; floorIndex < floorCount; floorIndex++) {
			let x: number = floorXs[floorIndex];
			let y: number = floorYs[floorIndex];
			let tileWidth: number = widths[floorIndex];
			let tileHeight: number = heights[floorIndex];
			this.fillCellFlags(x, y, tileWidth, tileHeight, 255, ((floorIndex + 1) | 0) | 16);
			this.fillCellFlags(((x - 1) | 0), ((y - 1) | 0), ((tileWidth + 2) | 0), 1, 255, 128);
			this.fillCellFlags(((x - 1) | 0), ((y + tileHeight) | 0), ((tileWidth + 2) | 0), 1, 255, 128);
			this.fillCellFlags(((x - 1) | 0), y, 1, tileHeight, 255, 128);
			this.fillCellFlags(((x + tileWidth) | 0), y, 1, tileHeight, 255, 128);
		}
		for (let wallIndex: number = 0; wallIndex < wallCount; wallIndex++) {
			let wallXAt: number = wallsX[wallIndex];
			let wallYAt: number = wallsY[wallIndex];
			let length: number = Math.abs(wallLengths[wallIndex]);
			if ((this.cellFlagsAt(((wallXAt - 1) | 0), wallYAt) & 128) !== 0) {
				this.fillCellFlags(wallXAt, wallYAt, length, 1, -129, 0);
			}
			else {
				this.fillCellFlags(wallXAt, wallYAt, 1, length, -129, 0);
				wallLengths[wallIndex] = ((((length * -1) | 0)) << 24 >> 24);
			}
		}
		GameCanvas.fillBytes(this.groundTile.cellIndices, 16);
		GameCanvas.fillBytes(this.overlayTile.cellIndices, 60);
		let halfWidth: number = this.gridHalfWidth;
		for (let floorSlot: number = 0; floorSlot < floorCount; floorSlot++) {
			let posX: number = ((halfWidth + floorXs[floorSlot]) | 0);
			let posY: number = ((0 + floorYs[floorSlot]) | 0);
			let tileW: number = widths[floorSlot];
			let tileH: number = heights[floorSlot];
			let edgeAId: number = edgeA[floorSlot];
			let edgeBId: number = edgeB[floorSlot];
			this.paintFloorRect(posX, posY, tileW, tileH, edgeAId, edgeBId);
		}
		for (let wIdx: number = 0; wIdx < wallCount; wIdx++) {
			let wallTileX: number = wallsX[wIdx];
			let wallTileY: number = wallsY[wIdx];
			let len: number = 0;
			let flagsA: number = 0;
			let flagsB: number = 0;
			if ((len = wallLengths[wIdx]) > 0) {
				flagsA = this.cellFlagsAt(wallTileX, ((wallTileY - 1) | 0));
				flagsB = this.cellFlagsAt(wallTileX, ((wallTileY + 1) | 0));
			}
			else {
				flagsA = this.cellFlagsAt(((wallTileX - 1) | 0), wallTileY);
				flagsB = this.cellFlagsAt(((wallTileX + 1) | 0), wallTileY);
			}
			let edgeAIndex: number = (((flagsA & 15) - 1) | 0);
			let edgeBIndex: number = (((flagsB & 15) - 1) | 0);
			let edgeAId2: number = edgeAIndex === -1 ? 3 : edgeA[edgeAIndex];
			let edgeBId2: number = edgeBIndex === -1 ? 0 : edgeB[edgeBIndex];
			this.paintWallStrip(((halfWidth + wallTileX) | 0), ((0 + wallTileY) | 0), Math.abs((len | 0)), edgeAId2, edgeBId2, len < 0 ? 2 : 1);
		}
		this.fixWallCorners();
		this.markPerimeter();
		this.groundTile.invalidateBacking();
	}
	paintFloorRect(x: number, y: number, width: number, height: number, edgeId: number, textureId: number): void {
		let tile: Tile = this.groundTile;
		let groundCells: Int8Array = this.groundTile.cellIndices;
		let overlayCells: Int8Array = this.overlayTile.cellIndices;
		let stride: number = tile.columns;
		let isoX: number = Lot.subtract(x, y);
		let isoY: number = Lot.add(x, y);
		let rows: number = ((width + height) | 0);
		let fillId: number = this.tableValueAt(5, edgeId);
		let nearId: number = this.tableValueAt(12, textureId);
		let farId: number = this.tableValueAt(18, textureId);
		let sideAId: number = this.tableValueAt(8, textureId);
		let sideBId: number = this.tableValueAt(14, textureId);
		let col: number = ((isoX - height) | 0);
		let row: number = 0;
		let rowB: number = (((row = ((isoY + height) | 0)) + 1) | 0);
		for (let rowIndex: number = 0; rowIndex < rows; rowIndex++) {
			let inHeight: boolean = rowIndex < height;
			let inWidth: boolean = rowIndex < width;
			let topId: number = inHeight ? nearId : farId;
			overlayCells[((col + Math.imul((((row - 0) | 0)), stride)) | 0)] = topId;
			let bottomId: number = inHeight ? sideAId : sideBId;
			overlayCells[((col + Math.imul((((row - 1) | 0)), stride)) | 0)] = bottomId;
			let rowId: number = inWidth ? 56 : 48;
			overlayCells[((col + Math.imul((((rowB + 0) | 0)), stride)) | 0)] = (rowId << 24 >> 24);
			let span: number = ((((rowB - row) | 0) + 2) | 0);
			for (let cellIndex: number = 0; cellIndex < span; cellIndex++) {
				groundCells[((col + Math.imul((((row + cellIndex) | 0)), stride)) | 0)] = fillId;
			}
			if (rowIndex === 0) {
				overlayCells[((((col - 1) | 0) + Math.imul((((row + 0) | 0)), stride)) | 0)] = this.tableValueAt(20, textureId);
			}
			else {
				if (rowIndex === ((rows - 1) | 0)) {
					overlayCells[((((col + 1) | 0) + Math.imul((((row + 0) | 0)), stride)) | 0)] = this.tableValueAt(21, textureId);
				}
			}
			col++;
			if (rowIndex !== ((width - 1) | 0)) {
				rowB = ((rowB + (inWidth ? 1 : -1)) | 0);
			}
			if (rowIndex !== ((height - 1) | 0)) {
				row = ((row + (inHeight ? -1 : 1)) | 0);
			}
		}
	}
	paintWallStrip(x: number, y: number, length: number, edgeIndex: number, textureId: number, orientation: number): void {
		let tile: Tile = this.groundTile;
		let stride: number = this.groundTile.columns;
		let cells: Int8Array = this.overlayTile.cellIndices;
		let isoX: number = Lot.subtract(x, y);
		let isoY: number = Lot.add(x, y);
		let leftSide: boolean = false;
		if (leftSide = (orientation & 1) !== 0) {
			isoX--;
			let topA: number = this.tableValueAt(24, textureId);
			let topB: number = this.tableValueAt(25, textureId);
			let side: number = this.tableValueAt(7, edgeIndex);
			let index: number = ((isoX + Math.imul(isoY, stride)) | 0);
			cells[index] = topA;
			index = ((index + stride) | 0);
			cells[index] = topB;
			index = ((index + stride) | 0);
			cells[index] = side;
			for (let stepIndex: number = 0; stepIndex < length; stepIndex++) {
				index = ((++isoX + Math.imul(++isoY, stride)) | 0);
				cells[index] = 60;
				index = ((index + stride) | 0);
				cells[index] = 60;
				index = ((index + stride) | 0);
				cells[index] = side;
			}
			index = ((++isoX + Math.imul(++isoY, stride)) | 0);
			cells[index] = 52;
			index = ((index + stride) | 0);
			if (cells[index] === 60) {
				cells[index] = this.tableValueAt(14, textureId);
				index = ((index + stride) | 0);
				cells[index] = this.tableValueAt(18, textureId);
			}
		}
		else {
			isoX = ((isoX - 2) | 0);
			isoY = ((isoY + 2) | 0);
			let topC: number = this.tableValueAt(22, textureId);
			let topD: number = this.tableValueAt(23, textureId);
			let side2: number = this.tableValueAt(6, edgeIndex);
			let index2: number = ((isoX + Math.imul((((isoY + 0) | 0)), stride)) | 0);
			cells[index2] = 52;
			index2 = ((index2 + stride) | 0);
			if (cells[index2] === 60) {
				cells[index2] = this.tableValueAt(8, textureId);
				index2 = ((index2 + stride) | 0);
				cells[index2] = this.tableValueAt(12, textureId);
			}
			for (let stepIndex2: number = 0; stepIndex2 < length; stepIndex2++) {
				index2 = ((++isoX + Math.imul((((--isoY + 0) | 0)), stride)) | 0);
				cells[index2] = 60;
				index2 = ((index2 + stride) | 0);
				cells[index2] = 60;
				index2 = ((index2 + stride) | 0);
				cells[index2] = side2;
			}
			index2 = ((++isoX + Math.imul((((--isoY + 0) | 0)), stride)) | 0);
			cells[index2] = topC;
			index2 = ((index2 + stride) | 0);
			cells[index2] = topD;
			index2 = ((index2 + stride) | 0);
			cells[index2] = side2;
		}
	}
	paintRoomEdges(x: number, y: number, width: number, height: number): void {
		let canvas: GameCanvas = this.canvas;
		let tile: Tile = this.groundTile;
		let cells: Int8Array = this.groundTile.cellIndices;
		let stride: number = tile.columns;
		let isoX: number = Lot.subtract(x, y);
		let isoY: number = Lot.add(x, y);
		let rows: number = ((((width + height) | 0) + 2) | 0);
		let col: number = ((((isoX - height) | 0) - 1) | 0);
		let rowA: number = 0;
		let rowB: number = rowA = ((((isoY + height) | 0) + 1) | 0);
		cells[((((col - 1) | 0) + Math.imul((((rowA - 2) | 0)), stride)) | 0)] = 4;
		cells[((((col - 1) | 0) + Math.imul((((rowA - 1) | 0)), stride)) | 0)] = 12;
		cells[((((col - 1) | 0) + Math.imul(rowA, stride)) | 0)] = 13;
		cells[((((col - 1) | 0) + Math.imul((((rowA + 1) | 0)), stride)) | 0)] = 21;
		cells[((((col - 2) | 0) + Math.imul((((rowA - 1) | 0)), stride)) | 0)] = 4;
		cells[((((col - 2) | 0) + Math.imul(rowA, stride)) | 0)] = 21;
		for (let rowIndex: number = 0; rowIndex < rows; rowIndex++) {
			let inHeight: boolean = rowIndex < ((height + 1) | 0);
			let inWidth: boolean = rowIndex < ((width + 1) | 0);
			let span: number = ((rowB - rowA) | 0);
			for (let cellIndex: number = 0; cellIndex < span; cellIndex++) {
				let id: number = canvas.randomPercent() < 50 ? 6 : 7;
				cells[((col + Math.imul((((rowA + cellIndex) | 0)), stride)) | 0)] = (id << 24 >> 24);
			}
			let idE: number = inHeight ? 22 : 23;
			cells[((col + Math.imul((((rowA - 1) | 0)), stride)) | 0)] = (idE << 24 >> 24);
			let idF: number = inHeight ? 4 : 5;
			cells[((col + Math.imul((((rowA - 3) | 0)), stride)) | 0)] = (idF << 24 >> 24);
			let idB: number = inWidth ? 15 : 14;
			cells[((col + Math.imul(rowB, stride)) | 0)] = (idB << 24 >> 24);
			let idC: number = inWidth ? 21 : 20;
			cells[((col + Math.imul((((rowB + 2) | 0)), stride)) | 0)] = (idC << 24 >> 24);
			let idD: number = canvas.randomPercent() < 50 ? 12 : 13;
			cells[((col + Math.imul((((rowA - 2) | 0)), stride)) | 0)] = (idD << 24 >> 24);
			cells[((col + Math.imul((((rowB + 1) | 0)), stride)) | 0)] = (idD << 24 >> 24);
			if (rowIndex !== width) {
				rowB = ((rowB + (inWidth ? 1 : -1)) | 0);
			}
			if (rowIndex !== height) {
				rowA = ((rowA + (inHeight ? -1 : 1)) | 0);
			}
			col++;
		}
		cells[((col + Math.imul((((rowA - 3) | 0)), stride)) | 0)] = 5;
		cells[((col + Math.imul((((rowA - 2) | 0)), stride)) | 0)] = 12;
		cells[((col + Math.imul((((rowA - 1) | 0)), stride)) | 0)] = 13;
		cells[((col + Math.imul(rowA, stride)) | 0)] = 20;
		cells[((((col + 1) | 0) + Math.imul((((rowA - 2) | 0)), stride)) | 0)] = 5;
		cells[((((col + 1) | 0) + Math.imul((((rowA - 1) | 0)), stride)) | 0)] = 20;
	}
	buildRoomEdges(): void {
		let screen: GameScreen = this.canvas.gameScreen();
		let flipped: boolean = false;
		let entity: Entity = null!;
		if ((entity = screen.findEntityByObjectType(10)).facing === 1) {
			flipped = true;
			let patternTable: Int8Array = this.tableAt(1);
			this.paintEdgePattern(1, patternTable);
			let patternA: Int8Array = Int8Array.from([16, 86, 94, 6, 6, 6, 6, 6]);
			this.paintEdgePattern(0, patternA);
		}
		else {
			let patternB: Int8Array = this.tableAt(0);
			this.paintEdgePattern(0, patternB);
			let patternC: Int8Array = Int8Array.from([17, 87, 95, 7, 7, 7, 7, 7]);
			this.paintEdgePattern(1, patternC);
		}
		if (!flipped) {
			let unusedFlag: boolean = false;
			let y: number = Lot.toTileY(entity.posY);
			screen.createEntityAtTile(57, 21, y, 0, -1);
			for (let yDown: number = ((y + 6) | 0); yDown < 15; yDown = ((yDown + 4) | 0)) {
				screen.createEntityAtTile(54, 21, yDown, 0, -1);
			}
			for (let yUp: number = ((y - 6) | 0); yUp > -14; yUp = ((yUp - 4) | 0)) {
				screen.createEntityAtTile(54, 21, yUp, 0, -1);
			}
		}
		else {
			let x: number = Lot.toTileX(entity.posX);
			let unusedFlag2: boolean = false;
			screen.createEntityAtTile(57, x, 21, 1, -1);
			for (let xRight: number = ((x + 6) | 0); xRight < 15; xRight = ((xRight + 4) | 0)) {
				screen.createEntityAtTile(54, xRight, 21, 1, -1);
			}
			for (let xLeft: number = ((x - 6) | 0); xLeft > -14; xLeft = ((xLeft - 4) | 0)) {
				screen.createEntityAtTile(54, xLeft, 21, 1, -1);
			}
		}
	}
	paintEdgePattern(orientation: number, pattern: Int8Array): void {
		let tile: Tile = this.groundTile;
		let cells: Int8Array = this.groundTile.cellIndices;
		let stride: number = tile.columns;
		let rows: number = tile.rowCountValue;
		let patternLength: number = ((pattern.length - 1) | 0);
		let col: number = ((stride - patternLength) | 0);
		let row: number = ((rows - 1) | 0);
		let step: number = 1;
		if (orientation === 1) {
			col = ((patternLength - 1) | 0);
			step = -1;
		}
		let mirrorTable: Int8Array = this.tableAt(2);
		for (let mapTable: Int8Array = this.tableAt(3); col >= 0 && col < stride; row = ((row + -1) | 0)) {
			for (let rowIndex: number = 0; ((row + rowIndex) | 0) < rows && rowIndex < pattern.length; rowIndex++) {
				let id: number = pattern[rowIndex];
				let index: number = 0;
				if ((col & 1) === 0 && (index = GameCanvas.indexOfByte(id, mirrorTable)) !== -1) {
					id = mapTable[index];
				}
				index = ((row + rowIndex) | 0);
				cells[((col + Math.imul(index, stride)) | 0)] = id;
				let worldX: number = Tile.toWorld16(col);
				let worldY: number = Tile.toWorld8(index);
				let fixedX: number = this.toWorldX(worldX, worldY);
				let fixedY: number = this.toWorldY(worldX, worldY);
				let tileX: number = Lot.toTileX(fixedX);
				let tileY: number = Lot.toTileY(fixedY);
				this.setCellFlags(tileX, tileY, 255, 128);
			}
			col = ((col + step) | 0);
		}
	}
	fixWallCorners(): void {
		let tile: Tile = this.groundTile;
		let overlayCells: Int8Array = this.overlayTile.cellIndices;
		let groundCells: Int8Array = tile.cellIndices;
		let stride: number = tile.columns;
		let rows: number = tile.rowCountValue;
		let table4: Int8Array = this.tableAt(4);
		let table5: Int8Array = this.tableAt(5);
		let table26: Int8Array = this.tableAt(26);
		let table8: Int8Array = this.tableAt(8);
		let table14: Int8Array = this.tableAt(14);
		let table20: Int8Array = this.tableAt(20);
		let table21: Int8Array = this.tableAt(21);
		for (let x: number = 1; x < ((stride - 1) | 0); x++) {
			let joined: boolean = false;
			for (let y: number = 0; y < ((rows - 1) | 0); y++) {
				let index: number = 0;
				let indexBelow: number = (((index = ((x + Math.imul((((y + 0) | 0)), stride)) | 0)) + stride) | 0);
				let groundAbove: number = groundCells[index];
				let groundBelow: number = groundCells[indexBelow];
				let overlayAbove: number = overlayCells[index];
				let overlayBelow: number = overlayCells[indexBelow];
				if (overlayAbove === 56 && overlayBelow === 60) {
					overlayBelow = table14[0];
				}
				if (overlayAbove === 48 && overlayBelow === 60) {
					overlayBelow = table8[0];
				}
				if ((this.indexInTable(overlayAbove, 27) !== -1 || GameCanvas.indexOfByte(overlayAbove, table20) !== -1) && overlayBelow === 60) {
					overlayBelow = table14[0];
					let indexA: number = ((index + stride) | 0);
					if (overlayCells[indexA] === 60) {
						overlayCells[indexA] = this.tableValueAt(18, 0);
					}
				}
				if ((GameCanvas.indexOfByte(overlayAbove, table26) !== -1 || GameCanvas.indexOfByte(overlayAbove, table21) !== -1) && overlayBelow === 60) {
					overlayBelow = table8[0];
					let indexB: number = ((index + stride) | 0);
					if (overlayCells[indexB] === 60) {
						overlayCells[indexB] = this.tableValueAt(12, 0);
					}
				}
				let indexC: number = 0;
				if ((indexC = GameCanvas.indexOfByte(overlayAbove, table14)) !== -1) {
					if (GameCanvas.indexOfByte(overlayBelow, table8) !== -1 || GameCanvas.indexOfByte(overlayBelow, table20) !== -1) {
						overlayAbove = table21[indexC];
					}
					else {
						if (GameCanvas.indexOfByte(groundBelow, table5) !== -1 && overlayBelow === 60) {
							overlayBelow = this.tableValueAt(18, indexC);
						}
					}
				}
				if ((indexC = GameCanvas.indexOfByte(overlayAbove, table8)) !== -1) {
					if (GameCanvas.indexOfByte(overlayBelow, table14) !== -1 || GameCanvas.indexOfByte(overlayBelow, table21) !== -1) {
						overlayAbove = table20[indexC];
					}
					else {
						if (GameCanvas.indexOfByte(groundBelow, table5) !== -1 && overlayBelow === 60) {
							overlayBelow = this.tableValueAt(12, indexC);
						}
					}
				}
				let indexD: number = 0;
				if ((indexD = GameCanvas.indexOfByte(groundAbove, table5)) !== -1) {
					let indexE: number = 0;
					if ((indexE = GameCanvas.indexOfByte(overlayBelow, table8)) === -1 && (indexE = GameCanvas.indexOfByte(overlayBelow, table20)) === -1) {
						if ((indexE = GameCanvas.indexOfByte(overlayBelow, table14)) === -1 && (indexE = GameCanvas.indexOfByte(overlayBelow, table21)) === -1) {
							let table24: Int8Array = this.tableAt(24);
							if (indexD === 3) {
								let randomIdx: number = this.canvas.randomRange(0, ((table4.length - 1) | 0));
								groundAbove = table4[randomIdx];
							}
							let overlayRight: number = 0;
							if (GameCanvas.indexOfByte(overlayRight = overlayCells[((((x + 1) | 0) + Math.imul(y, stride)) | 0)], table20) !== -1) {
								if (joined) {
									if (overlayAbove === 60) {
										overlayAbove = this.tableValueAt(22, 0);
									}
								}
								else {
									let endId: number = this.tableValueAt(30, 0);
									let midId: number = this.tableValueAt(29, 0);
									for (let rowIdLoop: number = 0; rowIdLoop < 5; rowIdLoop++) {
										let rowId: number = rowIdLoop === 4 ? endId : midId;
										overlayCells[((x + Math.imul((((((y - rowIdLoop) | 0) - 1) | 0)), stride)) | 0)] = rowId;
									}
									overlayAbove = this.tableValueAt(28, 0);
									let rowIndex: number = ((x + Math.imul((((((y - 1) | 0) - 5) | 0)), stride)) | 0);
									overlayCells[rowIndex] = this.tableValueAt(22, 0);
									joined = true;
								}
								if (overlayBelow === 60) {
									overlayBelow = this.tableValueAt(23, 0);
								}
							}
							let overlayLeft: number = 0;
							if (GameCanvas.indexOfByte(overlayLeft = overlayCells[((((x - 1) | 0) + Math.imul(y, stride)) | 0)], table21) !== -1) {
								if (overlayAbove === 60) {
									let overlayDiag: number = 0;
									let idxDiag: number = GameCanvas.indexOfByte(overlayDiag = overlayCells[((((x - 1) | 0) + Math.imul((((y + 1) | 0)), stride)) | 0)], table8);
									overlayAbove = table24[idxDiag];
									overlayBelow = this.tableValueAt(25, idxDiag);
								}
							}
							else {
								let idx8: number = 0;
								if ((idx8 = GameCanvas.indexOfByte(overlayLeft, table26)) !== -1) {
									if (!joined) {
										idx8 = 0;
										let endId2: number = this.tableValueAt(33, 0);
										let midId2: number = this.tableValueAt(32, 0);
										for (let columnIndex: number = 0; columnIndex < 5; columnIndex++) {
											let rowId2: number = columnIndex === 4 ? endId2 : midId2;
											overlayCells[((x + Math.imul((((((y - columnIndex) | 0) - 1) | 0)), stride)) | 0)] = rowId2;
										}
										overlayAbove = this.tableValueAt(31, 0);
										let rowIndex2: number = ((x + Math.imul((((((y - 1) | 0) - 5) | 0)), stride)) | 0);
										overlayCells[rowIndex2] = table24[0];
										joined = true;
										rowIndex2 = ((rowIndex2 - (((stride + 1) | 0))) | 0);
										if (overlayCells[rowIndex2] === 52) {
											overlayCells[rowIndex2] = 56;
										}
									}
									else {
										if (overlayAbove === 60) {
											overlayAbove = table24[idx8];
										}
									}
									if (overlayBelow === 60) {
										overlayBelow = this.tableValueAt(25, idx8);
									}
								}
							}
						}
						else {
							if (!joined) {
								let endId3: number = this.tableValueAt(16, indexE);
								let midId3: number = this.tableValueAt(17, indexE);
								for (let fillIndex: number = 0; fillIndex < 5; fillIndex++) {
									let rowId3: number = fillIndex === 3 ? endId3 : midId3;
									overlayCells[((x + Math.imul((((((y - fillIndex) | 0) - 1) | 0)), stride)) | 0)] = rowId3;
								}
								overlayAbove = midId3;
								let rowIndex3: number = ((x + Math.imul((((((y - 5) | 0) - 1) | 0)), stride)) | 0);
								let aboveId: number = overlayCells[((rowIndex3 - 1) | 0)];
								if (this.indexInTable(aboveId, 15) === -1 && aboveId !== 52) {
									overlayCells[rowIndex3] = 52;
								}
								else {
									overlayCells[rowIndex3] = 56;
								}
								overlayCells[((x + Math.imul((((((y - 5) | 0) - 0) | 0)), stride)) | 0)] = this.tableValueAt(15, indexE);
								if (overlayCells[--rowIndex3] === 52) {
									overlayCells[rowIndex3] = 48;
								}
								if ((indexE = GameCanvas.indexOfByte(overlayBelow, table14)) !== -1) {
									overlayBelow = this.tableValueAt(19, indexE);
								}
								else {
									if ((indexE = GameCanvas.indexOfByte(overlayBelow, table21)) !== -1) {
										overlayBelow = table26[indexE];
									}
								}
								joined = true;
							}
							else {
								if (overlayAbove === 60) {
									overlayAbove = 56;
								}
							}
						}
					}
					else {
						if (joined) {
							if (overlayAbove === 60) {
								overlayAbove = 48;
							}
						}
						else {
							let endId4: number = this.tableValueAt(10, indexE);
							let midId4: number = this.tableValueAt(11, indexE);
							for (let fillIndex2: number = 0; fillIndex2 < 5; fillIndex2++) {
								let rowId4: number = fillIndex2 === 4 ? endId4 : midId4;
								overlayCells[((x + Math.imul((((y - fillIndex2) | 0)), stride)) | 0)] = rowId4;
							}
							overlayAbove = midId4;
							overlayCells[((x + Math.imul((((((y - 5) | 0) - 1) | 0)), stride)) | 0)] = 52;
							let rowIndex4: number = ((x + Math.imul((((((y - 5) | 0) - 0) | 0)), stride)) | 0);
							overlayCells[rowIndex4] = this.tableValueAt(9, indexE);
							if (overlayCells[--rowIndex4] === 52) {
								overlayCells[rowIndex4] = 48;
							}
							if ((indexE = GameCanvas.indexOfByte(overlayBelow, table8)) !== -1) {
								overlayBelow = this.tableValueAt(13, indexE);
							}
							else {
								if ((indexE = GameCanvas.indexOfByte(overlayBelow, table20)) !== -1) {
									overlayBelow = this.tableValueAt(27, indexE);
								}
							}
							joined = true;
						}
					}
				}
				groundCells[index] = groundAbove;
				groundCells[indexBelow] = groundBelow;
				overlayCells[index] = overlayAbove;
				overlayCells[indexBelow] = overlayBelow;
			}
		}
	}
	markPerimeter(): void {
		let overlay: Tile = this.overlayTile;
		let cells: Int8Array = this.overlayTile.cellIndices;
		let stride: number = overlay.columns;
		let rows: number = overlay.rowCountValue;
		let flags: Int8Array = this.cellFlags;
		for (let flagIndex: number = 0; flagIndex < this.cellFlags.length; flagIndex++) {
			if ((flags[flagIndex] & 128) !== 0) {
				flags[flagIndex] = ((flags[flagIndex] | 64) << 24 >> 24);
			}
		}
		for (let x: number = 0; x < stride; x++) {
			for (let y: number = 0; y < rows; y++) {
				let id: number = cells[((x + Math.imul(y, stride)) | 0)];
				if (x === 0 || x === ((stride - 1) | 0) || y === 0 || y === ((rows - 1) | 0) || this.indexInTable(id, 11) !== -1 || this.indexInTable(id, 17) !== -1 || this.indexInTable(id, 31) !== -1 || this.indexInTable(id, 32) !== -1 || this.indexInTable(id, 33) !== -1 || this.indexInTable(id, 8) !== -1 || this.indexInTable(id, 10) !== -1 || this.indexInTable(id, 9) !== -1 || this.indexInTable(id, 14) !== -1 || this.indexInTable(id, 16) !== -1 || this.indexInTable(id, 15) !== -1) {
					let worldX: number = Tile.toWorld16(x);
					let worldY: number = Tile.toWorld8(y);
					let fixedX: number = this.toWorldX(worldX, worldY);
					let fixedY: number = this.toWorldY(worldX, worldY);
					let tileX: number = Lot.toTileX(fixedX);
					let tileY: number = Lot.toTileY(fixedY);
					this.setCellFlags(tileX, tileY, 255, 128);
				}
			}
		}
	}
	markBlockedCells(): void {
		let tile: Tile = this.groundTile;
		let cells: Int8Array = this.groundTile.cellIndices;
		let stride: number = tile.columns;
		let rows: number = tile.rowCountValue;
		let table: Int8Array = this.tableAt(34);
		GameCanvas.fillBytes(this.cellFlags, 128);
		for (let x: number = 0; x < stride; x++) {
			for (let y: number = 0; y < rows; y++) {
				let id: number = 0;
				if (GameCanvas.indexOfByte(id = cells[((x + Math.imul(y, stride)) | 0)], table) !== -1) {
					let worldX: number = Tile.toWorld16(x);
					let worldY: number = Tile.toWorld8(y);
					let fixedX: number = this.toWorldX(worldX, worldY);
					let fixedY: number = this.toWorldY(worldX, worldY);
					let tileX: number = Lot.toTileX(fixedX);
					let tileY: number = Lot.toTileY(fixedY);
					this.setCellFlags(tileX, tileY, -129, 32);
					this.setCellFlags(((tileX + 1) | 0), tileY, 255, 16);
					this.setCellFlags(((tileX - 1) | 0), tileY, 255, 16);
					this.setCellFlags(tileX, ((tileY + 1) | 0), 255, 16);
					this.setCellFlags(tileX, ((tileY - 1) | 0), 255, 16);
				}
			}
		}
		let flags: Int8Array = this.cellFlags;
		for (let flagIndex: number = 0; flagIndex < flags.length; flagIndex++) {
			let value: number = flags[flagIndex];
			let mask: number = 255;
			if ((value & 32) !== 0) {
				mask = 239;
			}
			if ((value & 16) !== 0) {
				mask = ((mask & -129) << 16 >> 16);
			}
			flags[flagIndex] = ((value & mask) << 24 >> 24);
		}
	}
	markBorderCells(imageId: number): void {
		let table: Int8Array = this.tableAt(imageId === 39 ? 36 : 35);
		let tile: Tile = this.groundTile;
		let cells: Int8Array = this.groundTile.cellIndices;
		let stride: number = tile.columns;
		let rows: number = tile.rowCountValue;
		for (let x: number = 0; x < stride; x++) {
			for (let y: number = 0; y < rows; y++) {
				let id: number = 0;
				let matched: boolean = GameCanvas.indexOfByte(id = cells[((x + Math.imul(y, stride)) | 0)], table) !== -1;
				if (x === 0 || x === ((stride - 1) | 0) || y === 0 || y === ((rows - 1) | 0) || matched) {
					let xAdj: number = x;
					if (!matched && x === ((stride - 1) | 0)) {
						xAdj = ((x + 1) | 0);
					}
					let worldX: number = Tile.toWorld16(xAdj);
					let worldY: number = Tile.toWorld8(y);
					let fixedX: number = this.toWorldX(worldX, worldY);
					let fixedY: number = this.toWorldY(worldX, worldY);
					let tileX: number = Lot.toTileX(fixedX);
					let tileY: number = Lot.toTileY(fixedY);
					this.setCellFlags(tileX, tileY, 255, 128);
				}
			}
		}
	}
	sortEntities(): void {
		let canvas: GameCanvas = this.canvas;
		let entities: Entity[] = this.canvas.gameScreen().getEntities();
		let swapped: boolean = false;
		for (let passIndex: number = 0; passIndex < entities.length; passIndex++) {
			for (let entryIndex: number = 1; entryIndex < entities.length; entryIndex++) {
				let current: Entity = entities[entryIndex];
				let previous: Entity = entities[((entryIndex - 1) | 0)];
				let depthX: number = this.toScreenX(current.posX, current.posY);
				let depthY: number = this.toScreenX(previous.posX, previous.posY);
				if (depthX < depthY) {
					if (current.backSortY() < previous.frontSortY()) {
						entities[((entryIndex - 1) | 0)] = current;
						entities[entryIndex] = previous;
						swapped = true;
					}
					if (current.type === 98 && previous.type === 0 && entities.length < 25 && (current.backSortY() === 176 || current.backSortY() === 144 || current.backSortY() === 112 || current.backSortY() === 80 || current.backSortY() === 48)) {
						entities[((entryIndex - 1) | 0)] = current;
						entities[entryIndex] = previous;
						swapped = true;
					}
					if (current.type === 0 && previous.type === 98 && entities.length < 25 && (previous.backSortY() === 400 || previous.backSortY() === 368 || previous.backSortY() === 336 || previous.backSortY() === 304 || previous.backSortY() === 272)) {
						entities[((entryIndex - 1) | 0)] = current;
						entities[entryIndex] = previous;
						swapped = true;
					}
				}
				else {
					if (current.frontSortY() < previous.backSortY()) {
						entities[((entryIndex - 1) | 0)] = current;
						entities[entryIndex] = previous;
						swapped = true;
					}
					if (current.type === 98 && previous.type === 0 && entities.length < 25 && (current.backSortY() === 176 || current.backSortY() === 144 || current.backSortY() === 112 || current.backSortY() === 80)) {
						entities[((entryIndex - 1) | 0)] = current;
						entities[entryIndex] = previous;
						swapped = true;
					}
					if (current.type === 0 && previous.type === 98 && entities.length < 25 && (previous.backSortY() === 400 || previous.backSortY() === 368 || previous.backSortY() === 336 || previous.backSortY() === 304 || previous.backSortY() === 272)) {
						entities[((entryIndex - 1) | 0)] = current;
						entities[entryIndex] = previous;
						swapped = true;
					}
				}
			}
			if (!swapped) {
				return;
			}
			swapped = false;
		}
	}
	updateVisibility(): void {
		let canvas: GameCanvas = this.canvas;
		let entities: Entity[] = this.canvas.gameScreen().getEntities();
		let minX: number = ((this.boundsX - 50) | 0);
		let minY: number = ((this.boundsY - 20) | 0);
		let maxX: number = ((((this.boundsX + this.boundsWidth) | 0) + 50) | 0);
		let maxY: number = ((((this.boundsY + this.boundsHeight) | 0) + 60) | 0);
		let centerX: number = this.viewCenterX();
		let centerY: number = this.viewCenterY();
		for (let entityIndex: number = 0; entityIndex < entities.length; entityIndex++) {
			let entity: Entity = entities[entityIndex];
			let screenX: number = ((centerX + this.toScreenX(entity.posX, entity.posY)) | 0);
			let screenY: number = ((centerY + this.toScreenY(entity.posX, entity.posY)) | 0);
			entity.reservedFlag = screenX >= minX && screenX <= maxX && screenY >= minY && screenY <= maxY;
		}
	}
	drawEntities(gfx: Graphics): void {
		this.sortEntities();
		this.updateVisibility();
		let canvas: GameCanvas = this.canvas;
		let entities: Entity[] = null!;
		let count: number = (entities = this.canvas.gameScreen().getEntities()).length;
		for (let entityIndex: number = 0; entityIndex < count; entityIndex++) {
			let entity: Entity = null!;
			if ((entity = entities[entityIndex]).reservedFlag && !entity.hasState(8192)) {
				entity.render(gfx);
			}
		}
	}
	drawEntityPair(gfx: Graphics, first: Entity, second: Entity, cameraX: number, cameraY: number): void {
		let savedCameraX: number = this.cameraX;
		let savedCameraY: number = this.cameraY;
		this.cameraX = cameraX;
		this.cameraY = cameraY;
		let canvas: GameCanvas = this.canvas;
		let entities: Entity[] = null!;
		let count: number = (entities = this.canvas.gameScreen().getEntities()).length;
		for (let entityIndex: number = 0; entityIndex < count; entityIndex++) {
			let entity: Entity = null!;
			if ((entity = entities[entityIndex]) === first || entity === second) {
				entity.render(gfx);
			}
		}
		this.cameraX = savedCameraX;
		this.cameraY = savedCameraY;
	}
	objectCount(): number {
		return this.spriteId.length;
	}
	objectKindAt(objectTypeId: number): number {
		return this.objectKind[objectTypeId];
	}
	spriteIdAt(objectTypeId: number): number {
		return this.spriteId[objectTypeId];
	}
	spriteVariantAt(objectTypeId: number): number {
		return this.spriteVariant[objectTypeId];
	}
	objectFlagsAt(objectTypeId: number): number {
		return this.objectFlags[objectTypeId];
	}
	linkedIdAt(objectTypeId: number): number {
		return this.linkedId[objectTypeId];
	}
	objectIdsAt(objectTypeId: number): number {
		return this.objectIds[objectTypeId];
	}
	altIdAt(objectTypeId: number): number {
		return this.altIds[objectTypeId];
	}
	objectWidthAt(objectTypeId: number): number {
		return this.objectWidth[objectTypeId];
	}
	objectHeightAt(objectTypeId: number): number {
		return this.objectHeight[objectTypeId];
	}
	subTypeAt(objectTypeId: number): number {
		return this.subType[objectTypeId];
	}
	attachmentAt(objectTypeId: number, index: number): number {
		return index < this.attachments[objectTypeId].length ? this.attachments[objectTypeId][index] : 0;
	}
	frameCountAt(objectTypeId: number): number {
		return this.frameFlags[objectTypeId].length;
	}
	framePartX(objectTypeId: number, frame: number, direction: number): number {
		let offset: number = this.frameFlags[objectTypeId][frame];
		let mirrored: number = this.frameMirrored[objectTypeId][frame];
		switch (direction) {
			case 0:
			default:
				break;
			case 1:
				offset = mirrored;
				break;
			case 2:
				offset = ((((1 - offset) | 0) - Math.max(this.objectHeight[objectTypeId], 1)) | 0);
				break;
			case 3:
				offset = ((((1 - Math.max(this.objectWidth[objectTypeId], 1)) | 0) - mirrored) | 0);
		}
		return offset;
	}
	framePartY(objectTypeId: number, frame: number, direction: number): number {
		let offset: number = this.frameFlags[objectTypeId][frame];
		let mirrored: number = this.frameMirrored[objectTypeId][frame];
		switch (direction) {
			case 0:
			default:
				break;
			case 1:
				mirrored = offset;
				break;
			case 2:
				mirrored = ((((1 - mirrored) | 0) - Math.max(this.objectWidth[objectTypeId], 1)) | 0);
				break;
			case 3:
				mirrored = ((((1 - Math.max(this.objectHeight[objectTypeId], 1)) | 0) - offset) | 0);
		}
		return mirrored;
	}
	frameRefsAt(objectTypeId: number): Int16Array {
		return this.frameRefs[objectTypeId];
	}
	indexOfKind(kind: number): number {
		let flags: Int32Array = this.objectFlags;
		if ((this.objectFlags[kind] & 32) === 0) {
			return -1;
		}
		else {
			let position: number = 0;
			for (let typeIndex: number = 0; typeIndex < flags.length; typeIndex++) {
				if ((flags[typeIndex] & 32) !== 0) {
					if (typeIndex === kind) {
						return position;
					}
					position++;
				}
			}
			return -1;
		}
	}
	frameDataAt(objectTypeId: number, index: number): number {
		let indexInArray: number = (((objectTypeId << 2) + index) | 0);
		return this.frameData[indexInArray];
	}
	resolveObjectId(objectId: number, isOutdoor: boolean): number {
		if (objectId < 0) {
			let progress: number = this.canvas.randomPercent();
			if (objectId === -2) {
				if (!isOutdoor) {
					progress = ((Math.imul(progress, 75) / 100) | 0);
				}
				if (progress < 25) {
					objectId = 6;
				}
				else {
					if (progress < 50) {
						objectId = 7;
					}
					else {
						if (progress < 75) {
							objectId = 8;
						}
						else {
							objectId = 9;
						}
					}
				}
			}
			else {
				if (objectId === -3) {
					objectId = progress < 50 ? 10 : 11;
				}
				else {
					if (objectId === -4) {
						objectId = progress < 50 ? 13 : 14;
					}
				}
			}
		}
		return objectId;
	}
	framePartAt(objectTypeId: number, frame: number, secondary: boolean): number {
		let parts: Int16Array = this.frameParts[objectTypeId];
		frame = (frame << 1);
		if (secondary) {
			frame++;
		}
		if (frame < parts.length) {
			return parts[frame];
		}
		else {
			return secondary ? 0 : parts[0];
		}
	}
	isStageItemAllowed(objectTypeId: number): boolean {
		let mask: number = this.platformMask();
		return (this.objectFlagsAt(objectTypeId) & mask) === 0;
	}
	platformMask(): number {
		switch (this.canvas.neighborhood.careerLevel()) {
			case 0:
			default:
				return 805306368;
			case 1:
				return 536870912;
			case 2:
				return 0;
		}
	}
	itemCount(): number {
		return ((this.objectIds.length - GameCanvas.countEqualShorts(-1, this.objectIds)) | 0);
	}
	indexOfItem(value: number): number {
		return GameCanvas.nthIndexNotEqualShort(value, this.objectIds, -1);
	}
	tableAt(tableId: number): Int8Array {
		return this.layoutTables[tableId];
	}
	tableValueAt(tableId: number, index: number): number {
		return this.layoutTables[tableId][index];
	}
	indexInTable(value: number, tableId: number): number {
		return GameCanvas.indexOfByte(value, this.layoutTables[tableId]);
	}
	categoryCount(): number {
		return this.categoryIds.length;
	}
	categoryIdAt(index: number): number {
		return this.categoryIds[index];
	}
	categoryValueAt(index: number): number {
		return this.categoryValues[index];
	}
	categoryMaskAt(index: number): number {
		return this.categoryMasks[index];
	}
	categoryTypeAt(index: number): number {
		return this.categoryTypes[index];
	}
	categorySubTypeAt(index: number): number {
		return this.categorySubTypes[index];
	}
	categoryFlagAt(index: number): number {
		return this.categoryFlags[index];
	}
	indexOfCategoryId(categoryId: number): number {
		return GameCanvas.indexOfShort(categoryId, this.categoryIds);
	}
	static soundMask(soundId: number): number {
		let result: number = 0;
		switch (soundId) {
			case 111:
				result = 32768;
				break;
			case 112:
			case 113:
			default:
				result = 4096;
				break;
			case 114:
				result = 8192;
				break;
			case 115:
				result = 16384;
		}
		return result;
	}
	countBySoundMask(soundId: number): number {
		return GameCanvas.countAnyFlagInt(Lot.soundMask(soundId), this.categoryMasks);
	}
	bitForSound(soundId: number, mask: number): number {
		let bit: number = Lot.soundMask(soundId);
		return GameCanvas.nthIndexWithFlagInt(mask, this.categoryMasks, bit);
	}
	volumeOf(categoryId: number): number {
		return FixedMath.mul(this.categoryVolumes[categoryId] << 16, 22937);
	}
	refAt(categoryId: number): number {
		return this.categoryRefs[categoryId];
	}
	listSize(categoryId: number): number {
		return this.categoryLists[categoryId].length;
	}
	listValueAt(categoryId: number, index: number): number {
		return this.categoryLists[categoryId][index];
	}
	indexOfRef(ref: number): number {
		return GameCanvas.indexOfByte(ref, this.categoryRefs);
	}
	categoryIdOfRef(ref: number): number {
		return this.categoryIds[this.categoryRefs[ref]];
	}
	refForCategoryId(categoryId: number): number {
		for (let index: number = 0; index < this.categoryRefs.length; index++) {
			if (this.categoryIdOfRef(index) === categoryId) {
				return index;
			}
		}
		return -1;
	}
	loadPalettes(): void {
		let res: ResourceLoader = this.canvas.resources;
		try {
			let stream: DataInputStream = null!;
			let paletteCount: number = 0;
			let paletteRows: Int32Array[] = new Array(paletteCount = (stream = new DataInputStream(res.openStream(60))).readByte()).fill(null);
			for (let paletteIndex: number = 0; paletteIndex < paletteCount; paletteIndex++) {
				let rowLength: number = 0;
				let row: Int32Array = new Int32Array(rowLength = stream.readByte());
				for (let rowIndex: number = 0; rowIndex < rowLength; rowIndex++) {
					row[rowIndex] = stream.readInt();
				}
				paletteRows[paletteIndex] = row;
			}
			let tableCount: number = 0;
			let tableRows: Int8Array[] = new Array(tableCount = stream.readByte()).fill(null);
			for (let tableIndex: number = 0; tableIndex < tableCount; tableIndex++) {
				let tableRow: Int8Array = new Int8Array(stream.readByte());
				stream.readFully(tableRow);
				tableRows[tableIndex] = tableRow;
			}
			stream.close();
			this.palettes = paletteRows;
			this.categoryTables = tableRows;
		}
		catch (ignored) {
			if (ignored instanceof IOException) {
			}
			else {
				throw ignored;
			}
		}
	}
	static fillPlaceholders(colorsA: Int32Array, colorsB: Int32Array): void {
		for (let slotIndex: number = 0; slotIndex < colorsA.length; slotIndex++) {
			colorsA[slotIndex] = 16711935;
			colorsB[slotIndex] = 16711935;
		}
	}
	applyPalette(colorsA: Int32Array, colorsB: Int32Array, paletteA: number, paletteB: number): void {
		if (paletteA >= 0 && paletteB >= 0) {
			let paletteRowA: Int32Array = this.palettes[paletteA];
			let paletteRowB: Int32Array = this.palettes[paletteB];
			let slot: number = -1;
			for (let slotIndex: number = 0; slotIndex < colorsA.length; slotIndex++) {
				if (colorsA[slotIndex] === 16711935) {
					slot = slotIndex;
					break;
				}
			}
			arraycopy(paletteRowA, 0, colorsA, slot, paletteRowA.length);
			arraycopy(paletteRowB, 0, colorsB, slot, paletteRowB.length);
		}
	}
	paletteAt(paletteId: number): Int32Array {
		return this.palettes[paletteId];
	}
	tableRowAt(tableId: number): Int8Array {
		return this.categoryTables[tableId];
	}
	buildPaletteFor(simIndex: number, colorsA: Int32Array, colorsB: Int32Array): void {
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		let table1Index: number = this.categoryTables[1][neighborhood.simAttribute(simIndex, 3)];
		let table2Index: number = this.categoryTables[2][neighborhood.simAttribute(simIndex, 4)];
		let table3Index: number = this.categoryTables[3][neighborhood.simAttribute(simIndex, 5)];
		let table4Index: number = this.categoryTables[4][neighborhood.simAttribute(simIndex, 6)];
		let table5Index: number = this.categoryTables[5][neighborhood.simAttribute(simIndex, 7)];
		let table6Index: number = this.categoryTables[6][neighborhood.simAttribute(simIndex, 8)];
		let table7Index: number = this.categoryTables[7][neighborhood.simAttribute(simIndex, 9)];
		let table0Index: number = this.categoryTables[0][neighborhood.simAttribute(simIndex, 1)];
		Lot.fillPlaceholders(colorsA, colorsB);
		this.applyPalette(colorsA, colorsB, 11, table1Index);
		this.applyPalette(colorsA, colorsB, 34, table2Index);
		this.applyPalette(colorsA, colorsB, 20, table3Index);
		this.applyPalette(colorsA, colorsB, 30, table4Index);
		this.applyPalette(colorsA, colorsB, 31, table5Index);
		this.applyPalette(colorsA, colorsB, 32, table6Index);
		this.applyPalette(colorsA, colorsB, 33, table7Index);
		this.applyPalette(colorsA, colorsB, 5, table0Index);
	}
	clearWaypoints(): void {
		this.waypointCount = 0;
	}
	addWaypoint(x: number, y: number): void {
		let offset: number = this.waypointCount << 1;
		this.waypoints[offset] = x;
		this.waypoints[((offset + 1) | 0)] = y;
		this.waypointCount++;
	}
	findPath(startX: number, startY: number, goalX: number, goalY: number, flags: number): Int32Array {
		this.resetPathfinder();
		this.pathFlags = flags;
		this.pathStartX = Lot.toTileX(startX);
		this.pathStartY = Lot.toTileY(startY);
		this.pathGoalX = Lot.toTileX(goalX);
		this.pathGoalY = Lot.toTileY(goalY);
		if (this.isPassable(this.pathStartX, this.pathStartY) && this.isPassable(this.pathGoalX, this.pathGoalY)) {
			this.pathCenterX = ((((this.pathStartX + this.pathGoalX) | 0) - 32) | 0) >> 1;
			this.pathCenterY = ((((this.pathStartY + this.pathGoalY) | 0) - 32) | 0) >> 1;
			let relX: number = ((this.pathStartX - this.pathCenterX) | 0);
			let relY: number = ((this.pathStartY - this.pathCenterY) | 0);
			if (relX >= 0 && relY >= 0 && relX < 32 && relY < 32) {
				relX = ((this.pathGoalX - this.pathCenterX) | 0);
				relY = ((this.pathGoalY - this.pathCenterY) | 0);
				if (relX >= 0 && relY >= 0 && relX < 32 && relY < 32) {
					this.pushOpen(((this.pathStartX - this.pathCenterX) | 0), ((this.pathStartY - this.pathCenterY) | 0));
					let done: boolean = false;
					while (!done) {
						if ((relY = this.bestOpenIndex()) === -1) {
							return new Int32Array(0);
						}
						let currentX: number = this.openList[relY] & 255;
						let currentY: number = (this.openList[relY] & 65280) >> 8;
						for (let dir: number = 0; dir < 4; dir++) {
							let nextX: number = ((currentX + (dir === 0 ? -1 : dir === 1 ? 1 : 0)) | 0);
							let nextY: number = ((currentY + (dir === 2 ? -1 : dir === 3 ? 1 : 0)) | 0);
							if (!this.isClosed(nextX, nextY)) {
								if (this.pushOpen(nextX, nextY)) {
									this.setParent(nextX, nextY, currentX, currentY);
									this.updateCost(nextX, nextY);
									this.updateHeuristic(nextX, nextY);
									this.updateScore(nextX, nextY);
								}
								else {
									if (this.isCheaper(nextX, nextY, currentX, currentY)) {
										this.setParent(nextX, nextY, currentX, currentY);
										this.updateCost(nextX, nextY);
										this.updateScore(nextX, nextY);
									}
								}
							}
						}
						this.popOpen(relY);
						if (currentX === ((this.pathGoalX - this.pathCenterX) | 0) && currentY === ((this.pathGoalY - this.pathCenterY) | 0)) {
							done = true;
						}
					}
					relY = 2147483647;
					let prevY: number = 2147483647;
					let goalRelX: number = ((this.pathGoalX - this.pathCenterX) | 0);
					let goalRelY: number = ((this.pathGoalY - this.pathCenterY) | 0);
					let startRelX: number = ((this.pathStartX - this.pathCenterX) | 0);
					let startRelY: number = ((this.pathStartY - this.pathCenterY) | 0);
					let count: number = 0;
					let link: number = 0;
					while (goalRelX >= 0 && goalRelY >= 0 && goalRelX < 32 && goalRelY < 32 && (goalRelX !== startRelX || goalRelY !== startRelY) && (relY !== goalRelX || prevY !== goalRelY) && (link = this.parentLinks[goalRelX][goalRelY]) !== -1) {
						this.pathResult[count] = ((goalRelX | goalRelY << 8) << 16 >> 16);
						count++;
						relY = goalRelX;
						prevY = goalRelY;
						goalRelX = link & 255;
						goalRelY = (link & 65280) >> 8;
					}
					let path: Int32Array = new Int32Array(count << 1);
					for (let stepIndex: number = 0; stepIndex < count; stepIndex++) {
						let index: number = ((((count - stepIndex) | 0) - 1) | 0);
						let x: number = (((this.pathResult[index] & 255) + this.pathCenterX) | 0);
						let y: number = ((((this.pathResult[index] & 65280) >> 8) + this.pathCenterY) | 0);
						path[(((stepIndex << 1) + 0) | 0)] = Lot.toFixedX(x);
						path[(((stepIndex << 1) + 1) | 0)] = Lot.toFixedY(y);
					}
					return path;
				}
				else {
					return new Int32Array(0);
				}
			}
			else {
				return new Int32Array(0);
			}
		}
		else {
			return new Int32Array(0);
		}
	}
	initPathfinder(): void {
		if (this.openList === null) {
			this.openList = new Int16Array(1024);
			this.openCount = 0;
			this.closedList = new Int16Array(1024);
			this.closedCount = 0;
			this.parentLinks = new Array(32).fill(null);
			for (let rowIndex: number = 0; rowIndex < this.parentLinks.length; rowIndex++) {
				this.parentLinks[rowIndex] = new Int16Array(32);
			}
			this.pathCost = new Array(32).fill(null);
			for (let rowIndex2: number = 0; rowIndex2 < this.pathCost.length; rowIndex2++) {
				this.pathCost[rowIndex2] = new Int32Array(32);
			}
			this.pathScore = new Array(32).fill(null);
			for (let rowIndex3: number = 0; rowIndex3 < this.pathScore.length; rowIndex3++) {
				this.pathScore[rowIndex3] = new Int32Array(32);
			}
			this.pathHeuristic = new Array(32).fill(null);
			for (let rowIndex4: number = 0; rowIndex4 < this.pathHeuristic.length; rowIndex4++) {
				this.pathHeuristic[rowIndex4] = new Int32Array(32);
			}
			this.pathResult = new Int16Array(256);
			this.waypointCount = 0;
			this.waypoints = new Int32Array(20);
		}
	}
	pushOpen(x: number, y: number): boolean {
		let key: number = ((x | y << 8) << 16 >> 16);
		for (let entryIndex: number = 0; entryIndex < this.openCount; entryIndex++) {
			if (this.openList[entryIndex] === key) {
				return false;
			}
		}
		this.openList[this.openCount] = key;
		this.openCount++;
		return true;
	}
	popOpen(index: number): void {
		this.closedList[this.closedCount] = this.openList[index];
		this.closedCount++;
		for (let entryIndex: number = index; entryIndex < ((this.openCount - 1) | 0); entryIndex++) {
			this.openList[entryIndex] = this.openList[((entryIndex + 1) | 0)];
		}
		this.openCount--;
	}
	setParent(x: number, y: number, parentX: number, parentY: number): void {
		this.parentLinks[x][y] = ((parentX | parentY << 8) << 16 >> 16);
	}
	stepCost(relX: number, relY: number): number {
		if ((this.pathFlags & 6) !== 0) {
			let tileX: number = ((relX + this.pathCenterX) | 0);
			let tileY: number = ((relY + this.pathCenterY) | 0);
			let flags: number = 0;
			if (((flags = this.cellFlagsAt(tileX, tileY)) & 32) !== 0) {
				return (this.pathFlags & 2) !== 0 ? 5 : 50;
			}
			else {
				return (flags & 16) !== 0 ? 10 : 15;
			}
		}
		else {
			return 15;
		}
	}
	updateCost(x: number, y: number): void {
		let parentX: number = this.parentLinks[x][y] & 255;
		let parentY: number = (this.parentLinks[x][y] & 65280) >> 8;
		let cost: number = this.pathCost[parentX][parentY];
		let penalty: number = 0;
		if ((this.pathFlags & 8) !== 0) {
			let grandX: number = this.parentLinks[parentX][parentY] & 255;
			let grandY: number = (this.parentLinks[parentX][parentY] & 65280) >> 8;
			if ((grandX !== parentX || parentX !== x) && (grandY !== parentY || parentY !== y)) {
				penalty = 20;
			}
		}
		this.pathCost[x][y] = ((((cost + this.stepCost(x, y)) | 0) + penalty) | 0);
	}
	isCheaper(x: number, y: number, parentX: number, parentY: number): boolean {
		let cost: number = this.pathCost[x][y];
		return ((this.pathCost[parentX][parentY] + this.stepCost(x, y)) | 0) < cost;
	}
	updateHeuristic(x: number, y: number): void {
		let dx: number = ((((this.pathGoalX - this.pathCenterX) | 0) - x) | 0);
		let dy: number = ((((this.pathGoalY - this.pathCenterY) | 0) - y) | 0);
		if (dx < 0) {
			dx = ((dx * -1) | 0);
		}
		if (dy < 0) {
			dy = ((dy * -1) | 0);
		}
		let penalty: number = 0;
		if ((this.pathFlags & 1) !== 0) {
			for (let waypointIndex: number = 0; waypointIndex < this.waypointCount; waypointIndex++) {
				let offset: number = waypointIndex << 1;
				if (((x + this.pathCenterX) | 0) === this.waypoints[offset] && ((y + this.pathCenterY) | 0) === this.waypoints[((offset + 1) | 0)]) {
					penalty = 250;
					break;
				}
			}
		}
		this.pathHeuristic[x][y] = ((((Math.imul(dx, 10) + Math.imul(dy, 10)) | 0) + penalty) | 0);
	}
	updateScore(x: number, y: number): void {
		this.pathScore[x][y] = ((this.pathHeuristic[x][y] + this.pathCost[x][y]) | 0);
	}
	bestOpenIndex(): number {
		let result: number = -1;
		let bestScore: number = 2147483647;
		for (let entryIndex: number = 0; entryIndex < this.openCount; entryIndex++) {
			let x: number = this.openList[entryIndex] & 255;
			let y: number = (this.openList[entryIndex] & 65280) >> 8;
			if (bestScore > this.pathScore[x][y]) {
				bestScore = this.pathScore[x][y];
				result = entryIndex;
			}
		}
		return result;
	}
	isWalkable(relX: number, relY: number): boolean {
		let tileX: number = ((relX + this.pathCenterX) | 0);
		let tileY: number = ((relY + this.pathCenterY) | 0);
		return this.isPassable(tileX, tileY);
	}
	isClosed(x: number, y: number): boolean {
		if (x >= 0 && y >= 0 && x < 32 && y < 32) {
			if (!this.isWalkable(x, y)) {
				return true;
			}
			else {
				let key: number = x | y << 8;
				for (let entryIndex: number = 0; entryIndex < this.closedCount; entryIndex++) {
					if (key === this.closedList[entryIndex]) {
						return true;
					}
				}
				return false;
			}
		}
		else {
			return true;
		}
	}
	resetPathfinder(): void {
		this.closedCount = 0;
		this.openCount = 0;
		GameCanvas.fillShortMatrix(this.parentLinks, 0);
		GameCanvas.fillIntMatrix(this.pathCost, 0);
		GameCanvas.fillIntMatrix(this.pathScore, 0);
		GameCanvas.fillIntMatrix(this.pathHeuristic, 0);
	}
	constructor(canvasArg: GameCanvas) {
		this.canvas = canvasArg;
		this.loadObjects();
		this.loadPlacements();
		this.loadPalettes();
		this.initPathfinder();
	}
}
