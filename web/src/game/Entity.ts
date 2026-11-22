// Generated from Entity.java by tools/web/JavaToTs.java; keep this file in sync.
import { arraycopy, Graphics } from '../runtime';
import { GameScreen } from './GameScreen';
import { GameCanvas } from './GameCanvas';
import { Animator } from './Animator';
import { Sim } from './Sim';
import { Neighborhood } from './Neighborhood';
import { Lot } from './Lot';
import { SpriteRenderer } from './SpriteRenderer';
import { FixedMath } from './FixedMath';
import { SdkString } from './SdkString';

export class Entity {
	static boundsScratch: Int32Array = new Int32Array(10);
	screen: GameScreen = null!;
	canvas: GameCanvas = null!;
	type: number = 0;
	variant: number = 0;
	baseAnimId: number = 0;
	flags: number = 0;
	state: number = 0;
	mode: number = 0;
	modeTime: number = 0;
	posX: number = 0;
	posY: number = 0;
	facing: number = 0;
	tileWidth: number = 0;
	tileHeight: number = 0;
	reservedFlag: boolean = false;
	stateTimer: number = 0;
	intervalTimer: number = 0;
	layoutDirty: boolean = false;
	sortYFront: number = 0;
	sortYBack: number = 0;
	slotMask: number = 0;
	messageId: number = 0;
	idleAnimId: number = 0;
	animators: Animator[] = null!;
	overlayAnimId: number = 0;
	offsetYFront: number = 0;
	offsetYBack: number = 0;
	owner: Sim = null!;
	partner: Sim = null!;
	neighborhood(): Neighborhood {
		return this.canvas.neighborhood;
	}
	lot(): Lot {
		return this.canvas.lot;
	}
	place(type: number, x: number, y: number, facing: number, value: number): void {
		let lot: Lot = this.lot();
		let neighborhood: Neighborhood = this.neighborhood();
		this.moveTo(x, y);
		this.setFacing(facing);
		this.type = type;
		this.variant = value;
		this.flags = lot.objectFlagsAt(type);
		this.state = 0;
		this.baseAnimId = (lot.spriteIdAt(type) << 16 >> 16);
		this.offsetYFront = 0;
		this.offsetYBack = 0;
		this.owner = null;
		this.partner = null;
		this.addState(128);
		this.slotMask = 0;
		let idleAnimId: number = lot.subTypeAt(type);
		let modeAnimId: number = lot.attachmentAt(type, 0);
		if (type === 99) {
			idleAnimId = lot.stageMusicId();
		}
		if (idleAnimId === 196 && modeAnimId !== 0) {
			this.setValueB(modeAnimId);
		}
		else {
			this.idleAnimId = -1;
			this.playIdleAnimation(idleAnimId);
		}
		this.setValueA(-1);
		let stateBits: number = this.readStateBits();
		let modeValue: number = 0;
		if (type === 9) {
			modeValue = 1;
		}
		else {
			if (type === 92) {
				if ((modeValue = stateBits & 16777215) !== 0 && lot.areaId() !== 0) {
					let clockNow: number = neighborhood.gameTime;
					let savedTime: number = 0;
					if ((savedTime = this.readStateTime()) !== -1 && ((clockNow - savedTime) | 0) > 1440) {
						modeValue = 0;
					}
				}
			}
			else {
				if (lot.objectKindAt(type) === 16) {
					modeValue = stateBits & 16777215;
				}
			}
		}
		this.setModeFromState(modeValue);
		if ((stateBits & 16777216) !== 0) {
			this.turnOnPrimary();
		}
		if ((stateBits & 33554432) !== 0) {
			this.turnOnSecondary();
		}
		if (this.hasFlag(201326592) && !this.hasState(768)) {
			this.stateTimer = ((stateBits & 16777215) << 16 >> 16);
			this.intervalTimer = 0;
			if (this.stateTimer === 0) {
				this.stateTimer = (this.canvas.randomRange(0, 100) << 16 >> 16);
			}
		}
		if (this.behaviorClass() === 10) {
			let bubbleId: number = 47;
			if (type === 48) {
				bubbleId = 49;
			}
			else {
				if (type === 50) {
					bubbleId = 51;
				}
				else {
					if (type === 52) {
						bubbleId = 53;
					}
				}
			}
			this.screen.createEntity(bubbleId, this.posX, this.posY, this.facing, this.variant).setValueC(20);
		}
		if (this.behaviorClass() === 8) {
			this.notifyScreenPosition();
		}
	}
	despawn(): void {
		if (this.behaviorClass() === 8) {
			this.occupant().despawn();
		}
		this.screen.removeEntity(this);
		this.owner = null;
		this.partner = null;
	}
	behaviorClass(): number {
		return this.lot().objectKindAt(this.type);
	}
	typeAnimId(): number {
		return this.hasFlag(64) ? this.drawOwnerNames() : this.baseAnimId;
	}
	moveTo(x: number, y: number): void {
		this.layoutDirty = true;
		this.posX = x;
		this.posY = y;
	}
	hasFlag(flag: number): boolean {
		return (this.flags & flag) !== 0;
	}
	addFlag(flag: number): void {
		this.flags = (this.flags | flag);
	}
	hasState(flag: number): boolean {
		return (this.state & flag) !== 0;
	}
	setState(mask: number, enabled: boolean): void {
		if (enabled) {
			this.addState(mask);
		}
		else {
			this.clearState(mask);
		}
	}
	addState(flag: number): void {
		this.state = (this.state | flag);
	}
	clearState(flag: number): void {
		this.state = (this.state & ~flag);
	}
	setBusy(): void {
		this.addState(8192);
		if (this.behaviorClass() === 8) {
			this.occupant().addState(8192);
		}
	}
	clearBusy(): void {
		this.clearState(8192);
		if (this.behaviorClass() === 8) {
			this.occupant().clearState(8192);
		}
	}
	relocate(x: number, y: number, facing: number): void {
		let occupant: Entity = null;
		if (this.behaviorClass() === 8) {
			occupant = this.occupant();
		}
		this.moveTo(x, y);
		this.setFacing(facing);
		if (occupant !== null) {
			let offset: Int32Array = Entity.boundsScratch;
			this.computeDoorOffset(offset);
			occupant.relocate(offset[0], offset[1], offset[2]);
		}
	}
	update(): void {
		if (this.layoutDirty) {
			this.layoutDirty = false;
			let tileY: number = this.lot().toScreenY(this.posX, this.posY);
			this.sortYFront = ((tileY + this.offsetYFront) | 0);
			this.sortYBack = ((tileY + this.offsetYBack) | 0);
		}
	}
	interactionAnimId(): number {
		return this.hasFlag(16) ? this.typeAnimId() : 24;
	}
	computePosition(out: Int32Array): void {
		let lot: Lot = this.lot();
		let offsetX: number = 0;
		let offsetY: number = 0;
		let animator: Animator = null!;
		let animId: number = (animator = this.animatorAt(0)).animId;
		let frame: number = animator.frame;
		if (this.behaviorClass() === 16) {
			animId = 270;
			frame = 0;
		}
		if (animId !== -1) {
			if (SpriteRenderer.hitTest(out, animId, frame, 0)) {
				offsetX = out[0];
				offsetY = out[1];
			}
			else {
				if (this.hasFlag(16384)) {
					offsetY = ((0 - (this.screen.isNewNeighborhood() ? 7 : 38)) | 0);
				}
			}
		}
		out[0] = ((lot.toScreenFixedX(this.posX, this.posY) + (offsetX << 16)) | 0);
		out[1] = ((lot.toScreenFixedY(this.posX, this.posY) + (offsetY << 16)) | 0);
	}
	computeSlotPosition(out: Int32Array, slot: number): void {
		let lot: Lot = this.lot();
		let originTileX: number = Lot.toTileX(this.posX);
		let originTileY: number = Lot.toTileY(this.posY);
		let deltaTileX: number = lot.framePartX(this.type, slot, this.facing);
		let deltaTileY: number = lot.framePartY(this.type, slot, this.facing);
		let tileX: number = ((originTileX + deltaTileX) | 0);
		let tileY: number = ((originTileY + deltaTileY) | 0);
		out[0] = Lot.toFixedX(tileX);
		out[1] = Lot.toFixedY(tileY);
	}
	findNearestSlot(x: number, y: number, avoidOccupied: boolean, ignoreSim: Sim): number {
		let lot: Lot = this.lot();
		let out: Int32Array = Entity.boundsScratch;
		let slotCount: number = 0;
		if ((slotCount = lot.frameCountAt(this.type)) <= 0) {
			return -1;
		}
		else {
			let bestSlot: number = -1;
			let bestDistance: number = 2147483647;
			for (let slot: number = 0; slot < slotCount; slot++) {
				if (!avoidOccupied || this.isSlotFree(slot)) {
					this.computeSlotPosition(out, slot);
					let slotX: number = out[0];
					let slotY: number = out[1];
					if (lot.isWalkableAt(slotX, slotY)) {
						if (avoidOccupied && !this.hasFlag(4194304)) {
							let slotTileX: number = Lot.toTileX(slotX);
							let slotTileY: number = Lot.toTileY(slotY);
							let occupied: boolean = false;
							let sims: Sim[] = this.screen.getSims();
							for (let index: number = 0; index < sims.length && !occupied; index++) {
								let other: Sim = null!;
								if ((other = sims[index]) !== ignoreSim) {
									let otherTileX: number = Lot.toTileX(other.posX);
									let otherTileY: number = Lot.toTileY(other.posY);
									if (otherTileX === slotTileX && otherTileY === slotTileY) {
										occupied = true;
									}
								}
							}
							if (occupied) {
								continue;
							}
						}
						let deltaX: number = Math.abs(((x - slotX) | 0));
						let deltaY: number = Math.abs(((y - slotY) | 0));
						let distance: number = 0;
						if ((distance = ((deltaX + deltaY) | 0)) < bestDistance) {
							bestDistance = distance;
							bestSlot = slot;
						}
					}
				}
			}
			return bestSlot;
		}
	}
	tryOccupySlot(slot: number): boolean {
		if (this.hasFlag(4194304)) {
			return true;
		}
		else {
			if (this.hasFlag(8388608)) {
				if ((this.slotMask & 1 << slot) === 0) {
					this.slotMask = (this.slotMask | (1 << slot));
					return true;
				}
			}
			else {
				if (this.slotMask === 0) {
					this.slotMask = -1;
					return true;
				}
			}
			return false;
		}
	}
	releaseSlot(slot: number): void {
		if (this.hasFlag(8388608)) {
			this.slotMask = (this.slotMask & ~(1 << slot));
		}
		else {
			this.slotMask = 0;
		}
	}
	canOccupySlot(): boolean {
		return this.hasFlag(4194304) ? true : this.slotMask === 0;
	}
	hasFreeSlot(): boolean {
		if (this.canOccupySlot()) {
			return true;
		}
		else {
			let slotCount: number = this.lot().frameCountAt(this.type);
			return this.slotMask < (((1 << slotCount) - 1) | 0);
		}
	}
	isSlotFree(slot: number): boolean {
		return (this.slotMask & 1 << slot) === 0;
	}
	readStateBits(): number {
		if (this.variant === -1) {
			return 0;
		}
		else {
			let lot: Lot = this.lot();
			return this.neighborhood().tipValue(lot.areaId(), this.variant);
		}
	}
	readStateTime(): number {
		if (this.variant === -1) {
			return -1;
		}
		else {
			let lot: Lot = this.lot();
			return this.neighborhood().tipTime(lot.areaId(), this.variant);
		}
	}
	saveState(): void {
		if (this.variant !== -1) {
			let lot: Lot = this.lot();
			let neighborhood: Neighborhood = this.neighborhood();
			let stateBits: number = 0;
			if (this.type === 92) {
				stateBits = this.mode;
			}
			else {
				if (this.behaviorClass() === 16) {
					stateBits = this.mode;
				}
			}
			if (this.hasFlag(67108864) && this.hasState(256)) {
				stateBits = (stateBits | 16777216);
			}
			if (this.hasFlag(134217728) && this.hasState(512)) {
				stateBits = (stateBits | 33554432);
			}
			if (lot.areaId() === 0 && this.hasFlag(201326592)) {
				stateBits = (stateBits | this.stateTimer);
			}
			if (this.hasState(32768)) {
				stateBits = (stateBits | 134217728);
			}
			if (this.hasState(16384)) {
				stateBits = (stateBits | 67108864);
			}
			if (this.hasState(65536)) {
				stateBits = (stateBits | 268435456);
			}
			neighborhood.pushTip(lot.areaId(), this.variant, stateBits);
		}
	}
	isIdleModeZero(): boolean {
		return this.behaviorClass() === 16 ? !this.isAnimating() && !this.isAnimatorRunning(1) && this.mode !== 1 : this.mode === 0;
	}
	isIdleModeOne(): boolean {
		return this.behaviorClass() !== 16 || !this.isAnimating() && !this.isAnimatorRunning(1) ? this.mode === 1 : false;
	}
	setModeFromState(stateBits: number): void {
		let behaviorClass: number = 0;
		if ((behaviorClass = this.behaviorClass()) === 2) {
			this.applyTwoStateMode(stateBits);
		}
		else {
			if (behaviorClass === 3) {
				this.applyFourStateMode(stateBits);
			}
			else {
				if (behaviorClass === 7) {
					this.applyOverlayMode(stateBits);
				}
				else {
					if (behaviorClass === 8) {
						this.setMode(stateBits);
					}
					else {
						if (behaviorClass === 10) {
							this.applyActiveMode(stateBits);
						}
						else {
							if (behaviorClass === 17) {
								this.applySimpleTwoStateMode(stateBits);
							}
							else {
								if (behaviorClass === 19) {
									this.applyStaticTwoStateMode(stateBits);
								}
								else {
									if (behaviorClass === 16) {
										this.applyMultiFrameMode(stateBits);
									}
									else {
										if (this.type === 92) {
											this.applyDoorMode(stateBits);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		this.mode = stateBits;
		this.modeTime = 0;
		if (this.type === 92) {
			this.saveState();
		}
	}
	advanceTime(delta: number): void {
		this.modeTime = ((this.modeTime + delta) | 0);
		if (this.hasFlag(201326592) && !this.hasState(768)) {
			this.intervalTimer = ((this.intervalTimer + delta) | 0);
			if (this.intervalTimer > 4000) {
				this.intervalTimer = ((this.intervalTimer - 4000) | 0);
				this.advanceStateTimer(1);
			}
		}
		if (this.hasState(1)) {
			delta = this.screen.lastTickDuration;
		}
		let type: number = this.type;
		let behaviorClass: number = 0;
		if ((behaviorClass = this.behaviorClass()) === 3) {
			this.advanceModeAnimations(delta);
		}
		else {
			if (behaviorClass === 16) {
				this.advanceMultiFrame(delta);
			}
			else {
				this.advanceAnimations(delta);
				if (!this.isAnimating()) {
					if (behaviorClass === 7) {
						this.setModeFromState(2);
					}
					else {
						if (this.type === 92 && this.mode === 2) {
							this.setModeFromState(1);
						}
					}
				}
				if (behaviorClass === 8) {
					this.advanceAnimator(delta, 2);
					if (!this.isAnimatorRunning(2)) {
						if (this.mode === 2) {
							this.setModeFromState(1);
						}
						else {
							this.setModeFromState(0);
						}
					}
				}
			}
		}
		if (type === 7) {
			if (!this.isAnimating()) {
				this.despawn();
				return;
			}
		}
		else {
			if (type === 8 && this.modeTime > 1500) {
				this.despawn();
			}
		}
	}
	advanceAnimations(delta: number): void {
		if (this.hasState(2048)) {
			delta = FixedMath.mul(delta << 16, 81920) >> 16;
		}
		else {
			if (this.hasState(4096)) {
				delta = FixedMath.mul(delta << 16, 49152) >> 16;
			}
		}
		let drawn: boolean = this.advanceAnimator(delta, 0);
		if (this.advanceAnimator(delta, 1)) {
			drawn = true;
		}
		if (this.overlayAnimId !== -1 && drawn) {
			this.draw();
		}
	}
	advanceAnimator(delta: number, index: number): boolean {
		let animator: Animator = null!;
		return (animator = this.animatorAt(index)) !== null ? animator.advance(delta) : false;
	}
	draw(): void {
		let bounds: Int32Array = Entity.boundsScratch;
		let idleAnimator: Animator = this.animatorAt(0);
		let overlayAnimator: Animator = this.animatorAt(1);
		let firstFrame: number = 1;
		let animator: Animator = idleAnimator;
		let offsetX: number = 0;
		let offsetY: number = 0;
		if (overlayAnimator !== null && overlayAnimator.isRunning()) {
			animator = overlayAnimator;
			firstFrame = 0;
			if (idleAnimator.matchesFrame(bounds, 1)) {
				offsetX = bounds[0];
				offsetY = bounds[1];
			}
			else {
				if (this.hasState(1024)) {
					offsetY = -30;
				}
			}
		}
		let frameCount: number = 0;
		if ((frameCount = animator.frameDuration()) > firstFrame) {
			let lot: Lot = this.lot();
			if (this.behaviorClass() === 16) {
				let frame: number = animator.frame;
				let slotCount: number = this.slotCount();
				let animatorIndex: number = ((((2 + slotCount) | 0) - frame) | 0);
				this.animatorFor(animatorIndex).play(this.overlayAnimId, 16);
				return;
			}
			for (let frameIndex: number = firstFrame; frameIndex < frameCount; frameIndex++) {
				let tileX: number = this.posX;
				let tileY: number = this.posY;
				let screenX: number = lot.toScreenFixedX(tileX, tileY);
				let screenY: number = lot.toScreenFixedY(tileX, tileY);
				animator.matchesFrame(bounds, frameIndex);
				screenX = ((screenX + (((bounds[0] + offsetX) | 0) << 16)) | 0);
				screenY = ((screenY + (((bounds[1] + offsetY) | 0) << 16)) | 0);
				tileX = lot.toWorldX(screenX, screenY);
				tileY = lot.toWorldY(screenX, screenY);
				let depthOffset: number = (((((bounds[1] + offsetY) | 0)) * -1) | 0);
				if (this.hasFlag(524288)) {
					depthOffset = ((depthOffset + 5) | 0);
				}
				this.screen.createRoomObject(tileX, tileY, this.overlayAnimId, depthOffset);
			}
		}
	}
	handleAction(actionId: number, sim: Sim): boolean {
		if (this.type === 92) {
			if (actionId === 148) {
				this.setModeFromState(0);
			}
		}
		else {
			if (this.type === 9) {
				if (actionId === 149) {
					if (this.variant !== -1) {
						this.lot().removeObject(this.variant);
					}
					this.despawn();
				}
			}
			else {
				if (this.behaviorClass() === 10) {
					if (actionId === 150 || actionId === 153) {
						this.setModeFromState(1);
						if (sim.variant === 0 && !this.neighborhood().hasSimMilestone(0, 1)) {
							this.screen.notifySimsOfAction(23, sim, this);
						}
					}
					else {
						if (actionId === 151) {
							if (!this.screen.isEntityAtTile(0, this.posX, this.posY)) {
								this.setModeFromState(0);
							}
						}
						else {
							if (actionId === 152 && sim.variant === 0) {
								this.screen.notifySimsOfAction(24, sim, this);
							}
						}
					}
				}
				else {
					if (this.behaviorClass() === 22) {
						if (actionId === 131) {
							this.clearState(16384);
							this.addState(32768);
							this.saveState();
						}
						else {
							if (actionId === 130) {
								this.addState(16384);
								this.clearState(32768);
								this.saveState();
							}
						}
					}
					else {
						if (this.behaviorClass() === 16 && actionId === 112) {
							this.addState(65536);
							this.saveState();
						}
					}
				}
			}
		}
		return false;
	}
	turnOnPrimary(): void {
		this.addState(256);
		switch (this.behaviorClass()) {
			case 15:
			case 22:
				this.markInactive();
			default:
				this.saveState();
				this.playAnimation(28, 20);
				this.screen.showNeighborhoodIntro((8 << 24 >> 24));
		}
	}
	turnOffPrimary(): void {
		this.clearState(256);
		this.saveState();
		this.stopAnimation();
	}
	turnOnSecondary(): void {
		this.addState(512);
		this.saveState();
		this.playAnimation(41, 20);
		this.screen.showNeighborhoodIntro((9 << 24 >> 24));
	}
	turnOffSecondary(): void {
		this.clearState(512);
		this.saveState();
		this.stopAnimation();
	}
	advanceStateTimer(amount: number): void {
		if (this.lot().areaId() === 0) {
			this.stateTimer = ((((this.stateTimer + amount) | 0)) << 16 >> 16);
			if (this.stateTimer > 300) {
				this.stateTimer = 0;
				if (this.hasFlag(67108864)) {
					this.turnOnPrimary();
				}
				else {
					this.turnOnSecondary();
				}
			}
			this.saveState();
		}
	}
	markActive(): void {
		let behaviorClass: number = 0;
		if ((behaviorClass = this.behaviorClass()) === 15 || behaviorClass === 22 || behaviorClass === 18) {
			this.playModeAnimation(1);
		}
		if (behaviorClass === 22 && !this.hasState(49152)) {
			this.addState(32768);
		}
		else {
			if (behaviorClass === 8) {
				this.setModeFromState(2);
			}
		}
		this.addState(64);
		this.advanceStateTimer(15);
	}
	markInactive(): void {
		let behaviorClass: number = 0;
		if ((behaviorClass = this.behaviorClass()) === 15 || behaviorClass === 22 || behaviorClass === 18) {
			this.playModeAnimation(0);
		}
		else {
			if (behaviorClass === 8) {
				this.setModeFromState(3);
			}
		}
		this.clearState(64);
		this.advanceStateTimer(15);
	}
	setIdleAnimation(animId: number, offset: number): void {
		this.clearState(128);
		this.playIdleAnimation(animId);
		if (animId === 35 || animId === 36) {
			this.addState(1);
		}
		this.setValueC(offset);
	}
	showMessage(textId: number): void {
		this.messageId = textId;
		this.playIdleAnimation(196);
		this.setValueC(30);
	}
	drawMessage(x: number, y: number): void {
		let canvas: GameCanvas = this.canvas;
		let progress: number = FixedMath.smoothStep(0, 1500, this.modeTime);
		let offsetY: number = ((-40 + FixedMath.mul(progress, -30)) | 0);
		let text: SdkString = canvas.beginString();
		canvas.formatNumber(this.messageId);
		canvas.drawString(text, 0, x, ((y + offsetY) | 0), 17);
	}
	enterModeOne(): void {
		this.setModeFromState(1);
	}
	enterModeZero(): void {
		this.setModeFromState(0);
	}
	applyTwoStateMode(mode: number): void {
		let modeAnimation: number = 0;
		let overlayAnimation: number = 0;
		switch (mode) {
			case 0:
			default:
				overlayAnimation = -1;
				modeAnimation = 0;
				break;
			case 1:
				overlayAnimation = 44;
				modeAnimation = 1;
		}
		this.setValueA(overlayAnimation);
		this.playModeAnimation(modeAnimation);
	}
	enterModeTwoIfIdle(): void {
		if (this.mode === 0 || this.mode === 3) {
			this.setModeFromState(2);
		}
	}
	enterModeThreeIfBusy(): void {
		if (this.mode === 1 || this.mode === 2) {
			this.setModeFromState(3);
		}
	}
	isModeOne(): boolean {
		return this.mode === 1;
	}
	computeFootprintOffset(out: Int32Array): void {
		let lot: Lot = null!;
		let deltaX: number = (lot = this.lot()).toScreenX(this.posX, this.posY);
		let deltaY: number = lot.toScreenY(this.posX, this.posY);
		this.animatorAt(2).matchesFrame(out, 0);
		out[0] = ((out[0] + deltaX) | 0);
		out[1] = ((out[1] + deltaY) | 0);
	}
	advanceModeAnimations(delta: number): void {
		this.advanceAnimations(delta);
		this.advanceAnimator(delta, 2);
		if (!this.isAnimatorRunning(2)) {
			if (this.mode === 2) {
				this.setModeFromState(1);
				return;
			}
			if (this.mode === 3) {
				this.setModeFromState(0);
			}
		}
	}
	applyFourStateMode(mode: number): void {
		let animId: number = -1;
		let loopForever: boolean = true;
		switch (mode) {
			case 0:
			default:
				break;
			case 1:
				animId = 158;
				break;
			case 2:
				animId = 157;
				loopForever = false;
				break;
			case 3:
				animId = 159;
				loopForever = false;
		}
		let animator: Animator = this.animatorFor(2);
		if (animId !== -1) {
			let frameId: number = this.lot().frameDataAt(animId, this.facing);
			animator.play(frameId, loopForever ? 20 : 16);
		}
		else {
			animator.setRunning(false);
		}
	}
	doorOffset(): number {
		let entities: Entity[] = this.screen.getEntities();
		let door: Entity = null;
		for (let index: number = 0; index < entities.length; index++) {
			let entity: Entity = null!;
			if ((entity = entities[index]) !== this && entity.behaviorClass() === 4) {
				door = entity;
				break;
			}
		}
		if (door !== null) {
			return this.variant < door.variant ? 512 : 1024;
		}
		else {
			return 512;
		}
	}
	isPassable(): boolean {
		let lot: Lot = this.lot();
		let tileX: number = Lot.toTileX(this.posX);
		let tileY: number = Lot.toTileY(this.posY);
		return !lot.footprintFree(tileX, tileY, 1, 1, this.facing) && this.type !== 41;
	}
	container(): Entity {
		let entities: Entity[] = this.screen.getEntities();
		for (let index: number = 0; index < entities.length; index++) {
			let entity: Entity = null!;
			if ((entity = entities[index]).behaviorClass() === 8 && entity.occupant() === this) {
				return entity;
			}
		}
		return null;
	}
	setModeOne(): void {
		this.setModeFromState(1);
	}
	setModeZero(): void {
		this.setModeFromState(0);
	}
	applyOverlayMode(mode: number): void {
		let modeAnimation: number = 0;
		let loopEnabled: boolean = true;
		let overlayAnimation: number = -1;
		if (mode === 1) {
			modeAnimation = 1;
			loopEnabled = false;
			overlayAnimation = 42;
		}
		this.overlayAnimId = overlayAnimation;
		this.setState(128, loopEnabled);
		this.playModeAnimation(modeAnimation);
	}
	isModeTwo(): boolean {
		return this.mode === 2;
	}
	notifyScreenPosition(): void {
		let bounds: Int32Array = Entity.boundsScratch;
		this.computeDoorOffset(bounds);
		this.screen.createEntity(41, bounds[0], bounds[1], bounds[2], -1);
	}
	computeDoorOffset(out: Int32Array): void {
		let x: number = this.posX;
		let y: number = this.posY;
		let facing: number = 3;
		if (this.facing === 0) {
			x = ((x + 524288) | 0);
			y = ((y - 524288) | 0);
			facing = 2;
		}
		else {
			x = ((x - 524288) | 0);
			y = ((y + 524288) | 0);
		}
		out[0] = x;
		out[1] = y;
		out[2] = facing;
	}
	occupant(): Entity {
		let bounds: Int32Array = Entity.boundsScratch;
		this.computeDoorOffset(bounds);
		return this.screen.findNearestEntityOfType(41, bounds[0], bounds[1]);
	}
	setMode(mode: number): void {
		this.mode = mode;
		let modeIndex: number = -1;
		let frameTime: number = 20;
		switch (mode) {
			case 0:
			default:
				break;
			case 1:
				modeIndex = 1;
				break;
			case 2:
				modeIndex = 2;
				frameTime = 16;
				break;
			case 3:
				modeIndex = 3;
				frameTime = 16;
		}
		if (modeIndex !== -1) {
			let lot: Lot = null!;
			let frameAnim: number = (lot = this.lot()).attachmentAt(this.type, modeIndex);
			let frameId: number = lot.frameDataAt(frameAnim, this.facing);
			this.animatorFor(2).play(frameId, frameTime);
		}
		else {
			this.animatorFor(2).setRunning(false);
		}
	}
	applyActiveMode(mode: number): void {
		this.mode = mode;
		let modeIndex: number = -1;
		let offsetY: number = 0;
		switch (mode) {
			case 0:
				modeIndex = 0;
				offsetY = 0;
				this.clearState(64);
				break;
			case 1:
				modeIndex = 1;
				offsetY = -15;
				this.addState(64);
		}
		this.playModeAnimation(modeIndex);
		this.updateFootprint(!this.hasState(64));
		this.setValueC(offsetY);
	}
	playFirstModeAnimation(): void {
		this.playModeAnimation(1);
	}
	playZeroModeAndTick(): void {
		this.playModeAnimation(0);
		this.advanceStateTimer(15);
	}
	drawOwnerNames(): number {
		let ownerSlot: number = -1;
		let partnerSlot: number = -1;
		let slotType: number = this.lot().indexOfLotId(this.type);
		let neighborhood: Neighborhood = null!;
		let slotCount: number = (neighborhood = this.neighborhood()).simCount;
		for (let index: number = 0; index < slotCount; index++) {
			if (neighborhood.simHomeLotOf(index) === slotType) {
				if (ownerSlot === -1) {
					ownerSlot = index;
				}
				else {
					if (partnerSlot === -1) {
						partnerSlot = index;
					}
				}
			}
		}
		if (ownerSlot === -1 && partnerSlot === -1) {
			return 201;
		}
		else {
			let canvas: GameCanvas = this.canvas;
			let nameText: SdkString = null!;
			if (ownerSlot === 0) {
				nameText = canvas.profileName();
			}
			else {
				nameText = canvas.getString(neighborhood.simNameText(ownerSlot));
			}
			if (partnerSlot === -1) {
				canvas.formatStringWith(-10, 202, nameText);
			}
			else {
				let partnerText: SdkString = canvas.getString(neighborhood.simNameText(partnerSlot));
				canvas.formatStringWith2(-10, 203, nameText, partnerText);
			}
			return -10;
		}
	}
	static upgradeTypeFor(upgradeId: number): number {
		switch (upgradeId) {
			case 5:
				return 18;
			case 6:
				return 21;
			case 7:
				return 19;
			case 8:
				return 22;
			case 9:
				return 20;
			default:
				return -1;
		}
	}
	static upgradeAnimFor(type: number, variant: number): number {
		switch (type) {
			case 18:
				return 264;
			case 19:
				return 265;
			case 20:
				return 266;
			case 21:
				if (variant === 0) {
					return 262;
				}
				return 263;
			case 22:
				if (variant === 0) {
					return 268;
				}
				return 269;
			default:
				return -1;
		}
	}
	static upgradeVariantFor(type: number): number {
		switch (type) {
			case 18:
				return 11;
			case 19:
				return 13;
			case 20:
				return 19;
			case 21:
				return 12;
			case 22:
				return 15;
			default:
				return -1;
		}
	}
	slotCount(): number {
		return this.isUpgraded() ? 5 : 8;
	}
	isUpgraded(): boolean {
		return (this.lot().objectFlagsAt(this.type) & 65536) !== 0;
	}
	setUpgradedType(upgradeId: number): void {
		let lot: Lot = this.lot();
		let newType: number = Entity.upgradeTypeFor(upgradeId);
		this.type = newType;
		lot.setObjectId(this.variant, newType);
		this.baseAnimId = (lot.spriteIdAt(newType) << 16 >> 16);
		this.mode = 2;
		this.saveState();
		this.clearState(128);
		if (this.isUpgraded()) {
			this.playIdleAnimation(271);
			this.playAnimation(267, 16);
			this.setValueA(Entity.upgradeAnimFor(newType, 0));
		}
		else {
			this.playIdleAnimation(272);
		}
	}
	toggleAlternate(): void {
		let upgraded: boolean = false;
		switch (this.mode) {
			case 2:
			default:
				if (this.isUpgraded()) {
					upgraded = true;
					this.mode = 1;
					this.clearState(128);
					this.playIdleAnimation(271);
					this.playAnimation(267, 16);
					this.setValueA(Entity.upgradeAnimFor(this.type, 1));
				}
				else {
					this.mode = 3;
					this.clearState(128);
					this.playIdleAnimation(273);
				}
				break;
			case 3:
				upgraded = true;
				this.mode = 1;
				this.clearState(128);
				this.playIdleAnimation(274);
				this.playAnimation(275, 16);
				this.setValueA(Entity.upgradeAnimFor(this.type, 0));
		}
		if (upgraded) {
			let neighborhood: Neighborhood = null!;
			(neighborhood = this.neighborhood()).unlockAchievement(32);
			if (this.type === 21) {
				neighborhood.unlockAchievement(33);
			}
			else {
				if (this.type === 19) {
					neighborhood.unlockAchievement(34);
				}
				else {
					if (this.type === 20) {
						neighborhood.unlockAchievement(35);
					}
				}
			}
		}
		this.saveState();
	}
	playSequenceOnAll(): void {
		this.playAnimation(196, 16);
		for (let index: number = 2; index <= 9; index++) {
			let animator: Animator = null!;
			if ((animator = this.animatorAt(index)) !== null) {
				animator.play(196, 16);
			}
		}
	}
	playChainAnimation(variant: number): void {
		let frameId: number = Entity.upgradeAnimFor(this.type, variant);
		let count: number = this.slotCount();
		let lastIndex: number = ((((2 + count) | 0) - 1) | 0);
		for (let index: number = 0; index < count; index++) {
			let animator: Animator = null!;
			(animator = this.animatorFor(((lastIndex - index) | 0))).play(frameId, 16);
			animator.rewind();
		}
	}
	completeUpgrade(): void {
		let variantId: number = Entity.upgradeVariantFor(this.type);
		let slotCount: number = this.slotCount();
		if (this.hasState(65536)) {
			slotCount = ((slotCount + 2) | 0);
			this.clearState(65536);
		}
		this.neighborhood().onRepeatAction(slotCount);
		this.neighborhood().addItem(variantId, slotCount);
		this.screen.showMessageDialog(variantId, slotCount, 289, 715);
		if (this.isUpgraded()) {
			this.transformToCompleted();
		}
		else {
			this.setModeFromState(3);
		}
		this.playSequenceOnAll();
		this.saveState();
	}
	transformToCompleted(): void {
		this.type = 17;
		this.lot().setObjectId(this.variant, 17);
		this.baseAnimId = (this.lot().spriteIdAt(17) << 16 >> 16);
		this.mode = 0;
		this.playSequenceOnAll();
		this.clearState(128);
		this.playIdleAnimation(270);
		this.saveState();
	}
	applyMultiFrameMode(mode: number): void {
		this.mode = mode;
		let animId: number = 0;
		switch (mode) {
			case 0:
			default:
				animId = 270;
				break;
			case 1:
				this.playChainAnimation(1);
				let overlayFrame: number = 275;
				animId = 274;
				if (this.isUpgraded()) {
					overlayFrame = 267;
					animId = 271;
				}
				let overlayAnimator: Animator = null!;
				(overlayAnimator = this.animatorFor(1)).play(overlayFrame, 16);
				overlayAnimator.rewind();
				break;
			case 2:
				if (this.isUpgraded()) {
					this.playChainAnimation(0);
					animId = 271;
					let alternateAnimator: Animator = null!;
					(alternateAnimator = this.animatorFor(1)).play(267, 16);
					alternateAnimator.rewind();
				}
				else {
					animId = 272;
				}
				break;
			case 3:
				animId = 273;
		}
		this.clearState(128);
		this.playIdleAnimation(animId);
		this.animatorAt(0).rewind();
	}
	advanceMultiFrame(delta: number): void {
		let slotCount: number = this.slotCount();
		for (let index: number = 0; index < slotCount; index++) {
			this.advanceAnimator(delta, ((2 + index) | 0));
		}
		this.advanceAnimations(delta);
	}
	playModeOneAnimation(): void {
		this.playModeAnimation(1);
	}
	playModeZeroAnimation(): void {
		this.playModeAnimation(0);
	}
	activateModeOne(): void {
		this.setModeFromState(1);
	}
	resetStateTimerToIdle(): void {
		this.advanceStateTimer(15);
		this.setModeFromState(0);
	}
	applySimpleTwoStateMode(mode: number): void {
		this.mode = mode;
		let modeAnimation: number = 0;
		let overlayAnimation: number = 0;
		switch (mode) {
			case 0:
			default:
				overlayAnimation = -1;
				modeAnimation = 0;
				break;
			case 1:
				overlayAnimation = 42;
				modeAnimation = 1;
		}
		this.setValueA(overlayAnimation);
		this.playModeAnimation(modeAnimation);
	}
	startModeOne(): void {
		this.setModeFromState(1);
	}
	activateModeZero(): void {
		this.setModeFromState(0);
	}
	applyStaticTwoStateMode(mode: number): void {
		this.mode = mode;
		let modeAnimation: number = 0;
		let overlayAnimation: number = 0;
		switch (mode) {
			case 0:
			default:
				overlayAnimation = -1;
				modeAnimation = 0;
				break;
			case 1:
				overlayAnimation = 42;
				modeAnimation = 1;
		}
		this.setValueA(overlayAnimation);
		this.playModeAnimation(modeAnimation);
	}
	interactWith(sim: Sim): void {
		this.screen.notifySimsOfAction(21, sim, this);
		this.faceEntity(sim);
		this.setFacing((((((this.facing + 2) | 0)) % 4) | 0));
		this.setModeFromState(2);
	}
	applyDoorMode(mode: number): void {
		let visible: boolean = true;
		let modeIndex: number = 0;
		switch (mode) {
			case 0:
			default:
				this.stopAnimation();
				modeIndex = 0;
				break;
			case 1:
				this.playAnimation(41, 4);
				modeIndex = 2;
				break;
			case 2:
				this.playAnimation(41, 20);
				modeIndex = 1;
				visible = false;
		}
		this.setState(128, visible);
		this.playModeAnimation(modeIndex);
	}
	animatorAt(index: number): Animator {
		return index < this.animators.length ? this.animators[index] : null;
	}
	animatorFor(index: number): Animator {
		if (index >= this.animators.length) {
			let grown: Animator[] = new Array(((index + 1) | 0)).fill(null);
			arraycopy(this.animators, 0, grown, 0, this.animators.length);
			this.animators = grown;
		}
		if (this.animators[index] === null) {
			let created: Animator = new Animator();
			this.animators[index] = created;
			created.setRunning(false);
		}
		return this.animators[index];
	}
	playAnimation(frameId: number, frameTime: number): void {
		this.animatorFor(1).play(frameId, frameTime);
	}
	stopAnimation(): void {
		let animator: Animator = null!;
		if ((animator = this.animatorAt(1)) !== null) {
			animator.setRunning(false);
		}
	}
	setValueA(animId: number): void {
		this.overlayAnimId = (animId << 16 >> 16);
	}
	frontSortY(): number {
		this.update();
		return this.sortYFront;
	}
	backSortY(): number {
		this.update();
		return this.sortYBack;
	}
	setValueC(value: number): void {
		this.offsetYFront = value;
		this.offsetYBack = value;
		this.layoutDirty = true;
	}
	setOwner(sim: Sim): void {
		if (this.owner === null) {
			this.owner = sim;
		}
		else {
			if (this.partner === null) {
				this.partner = sim;
			}
		}
	}
	clearOwner(sim: Sim): void {
		if (this.owner === sim) {
			this.owner = null;
		}
		if (this.partner === sim) {
			this.partner = null;
		}
	}
	render(graphics: Graphics): void {
		let lot: Lot = this.lot();
		let bounds: Int32Array = Entity.boundsScratch;
		let originX: number = lot.viewCenterX();
		let originY: number = lot.viewCenterY();
		let screenX: number = ((originX + lot.toScreenX(this.posX, this.posY)) | 0);
		let screenY: number = ((originY + lot.toScreenY(this.posX, this.posY)) | 0);
		if (this.type === 8) {
			this.drawMessage(screenX, screenY);
		}
		else {
			let idleAnimator: Animator = null!;
			(idleAnimator = this.animatorAt(0)).draw(graphics, screenX, screenY);
			let behaviorClass: number = 0;
			let auxAnimator: Animator = null!;
			if ((behaviorClass = this.behaviorClass()) === 8 && (auxAnimator = this.animatorAt(2)) !== null && auxAnimator.isRunning()) {
				auxAnimator.draw(graphics, screenX, screenY);
			}
			let overlayX: number = screenX;
			let overlayY: number = screenY;
			let overlayAnimator: Animator = null!;
			if ((overlayAnimator = this.animatorAt(1)) !== null && overlayAnimator.isRunning()) {
				if (idleAnimator.matchesFrame(bounds, 1)) {
					overlayX = ((screenX + bounds[0]) | 0);
					overlayY = ((screenY + bounds[1]) | 0);
				}
				else {
					if (idleAnimator.matchesFrame(bounds, 0)) {
						overlayX = ((screenX + bounds[0]) | 0);
						overlayY = ((screenY + bounds[1]) | 0);
					}
				}
				overlayAnimator.draw(graphics, overlayX, overlayY);
			}
			if (behaviorClass === 16 && overlayAnimator !== null) {
				let overlayAnimId: number = overlayAnimator.animId;
				if (overlayAnimator.animId !== 196) {
					let slotCount: number = 0;
					for (let slot: number = (((slotCount = this.slotCount()) - 1) | 0); slot >= 0; slot--) {
						let slotAnimator: Animator = null!;
						if ((slotAnimator = this.animatorAt(((2 + slot) | 0))) !== null) {
							let frameIndex: number = ((slotCount - slot) | 0);
							SpriteRenderer.hitTest(bounds, overlayAnimId, frameIndex, 0);
							let frameX: number = ((overlayX + bounds[0]) | 0);
							let frameY: number = ((overlayY + bounds[1]) | 0);
							slotAnimator.draw(graphics, frameX, frameY);
						}
					}
				}
			}
			let seatAnimator: Animator = null!;
			if (behaviorClass === 3 && (seatAnimator = this.animatorAt(2)).isRunning()) {
				seatAnimator.draw(graphics, screenX, screenY);
			}
			if (this.hasFlag(33554432)) {
				if (this.owner !== null) {
					this.owner.drawBody(graphics);
				}
				if (this.partner !== null) {
					this.partner.drawBody(graphics);
				}
				let animId: number = lot.attachmentAt(this.type, 1);
				let frameId: number = lot.frameDataAt(animId, this.facing);
				SpriteRenderer.drawFrame(graphics, frameId, 0, screenX, screenY);
			}
		}
	}
	addResourceIndices(out: Int16Array): void {
		GameCanvas.initList(out, this.baseAnimId);
		if (!this.isBlocked()) {
			if (this.needsFacingAdjust()) {
				GameCanvas.addToList(out, 326);
			}
			if (this.hasRotatedFootprint()) {
				GameCanvas.addToList(out, 325);
			}
			if (this.hasFurnitureSlots()) {
				GameCanvas.addToList(out, 324);
				return;
			}
		}
		else {
			GameCanvas.addToList(out, 275);
		}
	}
	updateFootprint(highlight: boolean): void {
		if (this.hasFlag(524288)) {
			this.setValueC(5);
		}
		else {
			if (this.hasFlag(4)) {
				if (this.type === 11) {
					this.setValueC(5);
				}
			}
			else {
				let lot: Lot = this.lot();
				let tileX: number = Lot.toTileX(this.posX);
				let tileY: number = Lot.toTileY(this.posY);
				let width: number = lot.objectWidthAt(this.type);
				let height: number = lot.objectHeightAt(this.type);
				if (this.facing === 1 || this.facing === 3) {
					let swap: number = width;
					width = height;
					height = swap;
				}
				let colorB: number = 0;
				let colorA: number = 0;
				if (highlight) {
					if (this.behaviorClass() === 10) {
						colorA = 255;
						colorB = 32;
					}
					else {
						if (this.type === 116) {
							colorA = -129;
							colorB = 0;
						}
						else {
							colorA = 255;
							colorB = 128;
						}
					}
				}
				else {
					colorA = -161;
					colorB = 0;
				}
				lot.fillCellFlags(((((tileX - width) | 0) + 1) | 0), ((((tileY - height) | 0) + 1) | 0), width, height, colorA, colorB);
				this.tileWidth = (width << 24 >> 24);
				this.tileHeight = (height << 24 >> 24);
				if (this.hasFlag(16777216)) {
					this.setValueC(-10);
				}
				else {
					if (this.hasFlag(8)) {
						this.setValueC(0);
					}
					else {
						if (this.behaviorClass() === 14 || this.behaviorClass() === 12) {
							this.setValueC(20);
						}
						else {
							if (width !== 0 && height !== 0) {
								this.offsetYFront = ((Math.imul(width, -8) + 4) | 0);
								this.offsetYBack = ((Math.imul(height, -8) + 4) | 0);
							}
						}
					}
				}
				this.layoutDirty = true;
			}
		}
	}
	setFacing(facing: number): void {
		if (this.facing !== facing) {
			this.layoutDirty = true;
			this.facing = facing;
			if (this.idleAnimId !== -1) {
				this.setValueB(this.idleAnimId);
			}
		}
	}
	faceDirection(dx: number, dy: number): void {
		this.setFacing(Math.abs(dx) > Math.abs(dy) ? dx < 0 ? 2 : 0 : dy < 0 ? 3 : 1);
	}
	faceEntity(other: Entity): void {
		let halfWidth: number = Math.max(((other.tileWidth - 1) | 0), 0);
		let halfHeight: number = Math.max(((other.tileHeight - 1) | 0), 0);
		let offsetX: number = halfWidth << 18;
		let offsetY: number = halfHeight << 18;
		let targetX: number = ((other.posX - offsetX) | 0);
		let targetY: number = ((other.posY - offsetY) | 0);
		this.faceDirection(((targetX - this.posX) | 0), ((targetY - this.posY) | 0));
	}
	playIdleAnimation(frameId: number): void {
		this.animatorAt(0).play(frameId, this.hasState(128) ? 20 : 16);
	}
	isAnimating(): boolean {
		return this.animators[0].isRunning();
	}
	isAnimatorRunning(index: number): boolean {
		let animator: Animator = null!;
		return (animator = this.animatorAt(index)) !== null && animator.isRunning();
	}
	setValueB(frameId: number): void {
		this.idleAnimId = (frameId << 16 >> 16);
		let frame: number = this.lot().frameDataAt(frameId, this.facing);
		this.playIdleAnimation(frame);
	}
	playModeAnimation(modeIndex: number): void {
		this.setValueB(this.lot().attachmentAt(this.type, modeIndex));
	}
	isBlocked(): boolean {
		if (this.slotMask !== 0) {
			return true;
		}
		else {
			if (this.hasFlag(262144)) {
				let lot: Lot = this.lot();
				let type: number = this.type;
				let width: number = lot.objectWidthAt(type);
				let height: number = lot.objectHeightAt(type);
				if (this.facing === 1 || this.facing === 3) {
					let swap: number = width;
					width = height;
					height = swap;
				}
				let tileX: number = Lot.toTileX(this.posX);
				let tileY: number = Lot.toTileY(this.posY);
				for (let dx: number = 0; dx < width; dx++) {
					for (let dy: number = 0; dy < height; dy++) {
						if (lot.hasObjectAt(((tileX - dx) | 0), ((tileY - dy) | 0))) {
							return true;
						}
					}
				}
			}
			let sims: Sim[] = this.screen.getSims();
			for (let index: number = 0; index < sims.length; index++) {
				if (sims[index].interactionTarget === this) {
					return true;
				}
			}
			return false;
		}
	}
	hasFurnitureSlots(): boolean {
		return this.lot().altIdAt(this.type) > 0;
	}
	hasRotatedFootprint(): boolean {
		let lot: Lot = this.lot();
		this.updateFootprint(false);
		let width: number = lot.spawnWalkers(this.type);
		this.screen.clearBrokenEntities();
		this.updateFootprint(true);
		return width > 1;
	}
	needsFacingAdjust(): boolean {
		return this.freeFacing() !== this.facing;
	}
	freeFacing(): number {
		let lot: Lot = this.lot();
		let hadFlag: boolean = false;
		if (!(hadFlag = this.hasFlag(524288))) {
			this.updateFootprint(false);
		}
		let facing: number = this.facing;
		let tileX: number = Lot.toTileX(this.posX);
		let tileY: number = Lot.toTileY(this.posY);
		let blockMask: number = lot.directionMaskAt(this.type, tileX, tileY);
		for (let step: number = 1; step <= 4; step++) {
			let direction: number = (((((facing + step) | 0)) % 4) | 0);
			let mask: number = Lot.DIRECTION_MASKS[direction];
			if ((blockMask & mask) !== 0) {
				facing = direction;
				break;
			}
		}
		if (!hadFlag) {
			this.updateFootprint(true);
		}
		return facing;
	}
	refreshFacing(): void {
		let facing: number = this.freeFacing();
		this.updateFootprint(false);
		this.relocate(this.posX, this.posY, facing);
		this.updateFootprint(true);
	}
	constructor(gameScreen: GameScreen) {
		this.screen = gameScreen;
		this.canvas = GameCanvas.instance();
		this.animators = new Array(1).fill(null);
		this.animators[0] = new Animator();
	}
}
