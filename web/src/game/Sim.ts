// Generated from Sim.java by tools/web/JavaToTs.java; keep this file in sync.
import { Graphics } from '../runtime';
import { Entity } from './Entity';
import { Neighborhood } from './Neighborhood';
import { Lot } from './Lot';
import { Animator } from './Animator';
import { SpriteRenderer } from './SpriteRenderer';
import { FixedMath } from './FixedMath';
import { GameScreen } from './GameScreen';
import { GameCanvas } from './GameCanvas';

export class Sim extends Entity {
	spriteVariant: number = 0;
	poseId: number = 0;
	recolorIndex: number = 0;
	motionState: number = 0;
	stateTime: number = 0;
	poseIndex: number = 0;
	targetPose: number = 0;
	interactionTarget: Entity = null!;
	actionId: number = 0;
	actionStep: number = 0;
	actionPhase: number = 0;
	actionTarget: Entity = null!;
	actionObjectId: number = 0;
	actionValue: number = 0;
	queuedActionId: number = 0;
	queuedTarget: Entity = null!;
	queuedObjectId: number = 0;
	queuedValue: number = 0;
	behaviourFlags: number = 0;
	autonomyTimer: number = 0;
	reservedTarget: Entity = null!;
	reservedSlot: number = 0;
	destX: number = 0;
	destY: number = 0;
	path: Int32Array = null!;
	pathIndex: number = 0;
	detourPath: boolean = false;
	targetLastX: number = 0;
	targetLastY: number = 0;
	partnerId: number = 0;
	partnerTimer: number = 0;
	partnerCooldown: number = 0;
	poseAnimId: number = 0;
	useTimer: boolean = false;
	waitForTargetTime: boolean = false;
	timer: number = 0;
	overlayPoseAnimId: number = 0;
	fadeOverlay: boolean = false;
	overlaySpriteId: number = 0;
	lastFacing: number = -551;
	static AUTONOMY_FLAGS: Int32Array = Int32Array.from([4, 2, 8, 16, 64]);
	place(type: number, startX: number, startY: number, facing: number, simId: number): void {
		super.place(type, startX, startY, facing, simId);
		this.motionState = 0;
		this.stateTime = 0;
		this.poseIndex = 0;
		this.targetPose = 0;
		this.interactionTarget = null;
		this.actionId = 0;
		this.actionStep = 0;
		this.actionPhase = 0;
		this.actionTarget = null;
		this.actionObjectId = 0;
		this.actionValue = 0;
		this.queuedActionId = -1;
		this.queuedTarget = null;
		this.queuedObjectId = 0;
		this.queuedValue = 0;
		this.behaviourFlags = 0;
		this.autonomyTimer = 0;
		this.reservedTarget = null;
		this.reservedSlot = -1;
		this.destX = this.posX;
		this.destY = this.posY;
		this.path = null;
		this.pathIndex = 0;
		this.detourPath = false;
		this.targetLastX = 0;
		this.targetLastY = 0;
		this.partnerId = -1;
		this.partnerTimer = 0;
		this.partnerCooldown = -1;
		this.poseAnimId = -1;
		this.useTimer = true;
		this.timer = 0;
		this.fadeOverlay = false;
		this.overlaySpriteId = -1;
		this.animatorFor(2);
		this.animatorFor(1);
		this.spriteVariant = -1;
		this.poseId = 0;
		let neighborhood: Neighborhood = this.neighborhood();
		if (this.screen.isNewNeighborhood()) {
			this.recolorIndex = neighborhood.simSpareByte(simId);
			this.setSpriteVariant(2);
		}
		else {
			if (neighborhood.simAttribute(simId, 0) === 0) {
				this.setSpriteVariant(0);
			}
			else {
				this.setSpriteVariant(1);
			}
			this.recolorIndex = -1;
		}
		if (simId === 0) {
			for (let stateIndex: number = 0; stateIndex < 6; stateIndex++) {
				let locomotionState: number = 0;
				if ((locomotionState = neighborhood.activeTaskAt(stateIndex)) !== -1) {
					this.setLocomotionState(locomotionState);
				}
			}
		}
		this.setMotionState(1);
	}
	advanceTime(delta: number): void {
		if (this.stateTime < 3000000) {
			this.stateTime = ((this.stateTime + delta) | 0);
		}
		if (this.partnerId !== -1) {
			this.partnerTimer = ((this.partnerTimer + delta) | 0);
			if (this.partnerTimer > 20000) {
				this.unlinkPartner();
			}
		}
		else {
			if (this.partnerCooldown >= 0) {
				this.partnerCooldown = ((this.partnerCooldown + delta) | 0);
				if (this.partnerCooldown > 20000) {
					this.partnerCooldown = -1;
				}
			}
		}
		switch (this.motionState) {
			case 1:
				this.updateIdle(delta);
				return;
			case 2:
				this.updateInteraction(delta);
				return;
			case 3:
				this.updateWalking(delta);
				return;
			case 4:
				this.updateTimedAction(delta);
				return;
			case 5:
				this.updateSocial(delta);
				return;
			case 6:
				this.updateApproach(delta);
				return;
			case 7:
				this.updateDiningPartner(delta);
				return;
			case 8:
				this.updateWaiting(delta);
		}
	}
	render(gfx: Graphics): void {
		if (this.motionState !== 8 && this.recolorIndex !== -1) {
			let lot: Lot = null!;
			let lotOriginX: number = (lot = this.lot()).viewCenterX();
			let lotOriginY: number = lot.viewCenterY();
			let screenX: number = ((lotOriginX + lot.toScreenX(this.posX, this.posY)) | 0);
			let screenY: number = ((lotOriginY + lot.toScreenY(this.posX, this.posY)) | 0);
			let bodyAnimator: Animator = null;
			if (this.poseId !== 50) {
				if (!this.screen.isNewNeighborhood() && !this.screen.isGoalPageActive() && (this.targetPose === 0 || this.targetPose === 6)) {
					SpriteRenderer.drawFrame(gfx, 33, 0, screenX, screenY);
				}
				SpriteRenderer.setRecolorIndex(this.recolorIndex);
				bodyAnimator = this.animatorAt(0);
				if (!this.screen.isNewNeighborhood()) {
					let animId: number = bodyAnimator.animId;
					let frame: number = bodyAnimator.frame;
					this.drawSprite(gfx, animId, frame, screenX, screenY);
				}
				else {
					bodyAnimator.draw(gfx, screenX, screenY);
					if (this.variant === 0) {
						this.screen.drawCursorAnimator(gfx, screenX, ((screenY - 16) | 0));
					}
				}
				if (this.partnerSim() !== null && this.partnerSim().interactionTarget === null) {
					this.partnerSim().drawBody(gfx);
				}
				SpriteRenderer.setRecolorIndex(0);
			}
			let overlayAnimator: Animator = this.animatorAt(2);
			if (this.timer > 0 && overlayAnimator.isRunning()) {
				let frameOffsets: Int32Array = Entity.boundsScratch;
				let bubbleOffset: number = Math.imul((this.fadeOverlay ? FixedMath.smoothStep(1000, 2000, this.timer) : 65536), 10);
				let bubbleY: number = ((((((screenY + (bubbleOffset >> 16)) | 0) - 60) | 0) - 10) | 0);
				SpriteRenderer.drawAnimator(gfx, overlayAnimator, screenX, bubbleY);
				if (this.actionPhase === 5) {
					overlayAnimator.matchesFrame(frameOffsets, 0);
					SpriteRenderer.drawFrame(gfx, this.overlaySpriteId, 0, ((screenX + frameOffsets[0]) | 0), ((bubbleY + frameOffsets[1]) | 0));
				}
			}
		}
	}
	drawBody(gfx: Graphics): void {
		let lot: Lot = null!;
		let spriteId: number = 0;
		let animId: number = 0;
		if ((spriteId = (lot = this.lot()).framePartAt(this.spriteVariant, this.poseId, true)) !== 0 && (animId = lot.frameDataAt(spriteId, this.facing)) !== 196) {
			let lotOriginX: number = lot.viewCenterX();
			let lotOriginY: number = lot.viewCenterY();
			let screenX: number = 0;
			let screenY: number = 0;
			if (this.poseId === 50) {
				let offsets: Int32Array = Entity.boundsScratch;
				this.actionTarget.computeFootprintOffset(offsets);
				screenX = ((lotOriginX + offsets[0]) | 0);
				screenY = ((lotOriginY + offsets[1]) | 0);
			}
			else {
				screenX = ((lotOriginX + lot.toScreenX(this.posX, this.posY)) | 0);
				screenY = ((lotOriginY + lot.toScreenY(this.posX, this.posY)) | 0);
			}
			let frame: number = this.animatorAt(0).frame;
			SpriteRenderer.setRecolorIndex(this.recolorIndex);
			this.drawSprite(gfx, animId, frame, screenX, screenY);
			SpriteRenderer.setRecolorIndex(0);
		}
	}
	drawSprite(gfx: Graphics, animId: number, frame: number, screenX: number, screenY: number): void {
		let bodyBase: number = this.spriteVariant === 0 ? 489 : 382;
		let skinTone: number = ((this.neighborhood().simAttribute(this.variant, 2) + 1) | 0);
		let frameOffsets: Int32Array = null;
		let isPlayer: boolean = false;
		if (isPlayer = this.variant === 0) {
			frameOffsets = Entity.boundsScratch;
		}
		let drawn: boolean = SpriteRenderer.drawParts(gfx, animId, frame, screenX, screenY, bodyBase, skinTone, frameOffsets);
		if (isPlayer && drawn) {
			this.screen.drawCursorAnimator(gfx, frameOffsets[0], frameOffsets[1]);
		}
	}
	interactionAnimId(): number {
		return this.neighborhood().simNameText(this.variant);
	}
	setSpriteVariant(variant: number): void {
		if (this.spriteVariant !== variant) {
			this.spriteVariant = variant;
			let previousPose: number = this.poseId;
			this.poseId = -1;
			this.setPose(previousPose);
		}
	}
	setPose(animId: number): void {
		let horizontal: boolean = this.facing === 0 || this.facing === 1;
		animId = this.lot().resolveObjectId(animId, horizontal);
		if (this.poseId !== animId) {
			this.poseId = animId;
			let spriteId: number = this.lot().framePartAt(this.spriteVariant, animId, false);
			this.setValueB(spriteId);
		}
	}
	setMotionState(newState: number): void {
		if (newState === 1 && this.motionState === 4 && this.poseIndex === 2 && this.variant === 0 && this.lastFacing !== -551) {
			this.setFacing(this.lastFacing);
		}
		let screen: GameScreen = this.screen;
		switch (this.motionState) {
			case 2:
				if (this.interactionTarget !== null && this.poseIndex === 0) {
					this.interactionTarget.clearOwner(this);
					this.interactionTarget = null;
				}
				break;
			case 3:
				this.lastFacing = -551;
				if (newState !== 3 && screen.isNewNeighborhood()) {
					this.setSpriteVariant(2);
					if (screen.cameraTarget === this) {
						screen.setCameraTarget((null as unknown as Entity));
					}
				}
		}
		this.addState(128);
		let oldState: number = this.motionState;
		this.motionState = newState;
		this.stateTime = 0;
		switch (newState) {
			case 1:
				let defaultPose: number = this.poseIndex === 4 ? 15 : this.poseIndex === 5 ? 54 : this.poseIndex === 1 ? 19 : this.poseIndex === 2 ? 19 : this.poseIndex === 3 ? 28 : this.poseIndex === 6 ? 43 : this.poseIndex === 7 ? 36 : 0;
				this.setPose(defaultPose);
				break;
			case 2:
				let pose: number = -1;
				let newPoseIndex: number = this.poseIndex;
				let target: Entity = this.actionTarget;
				switch (this.poseIndex) {
					case 0:
					default:
						if (this.targetPose === 7) {
							pose = 34;
							newPoseIndex = 7;
							this.interactionTarget = target;
							target.playModeOneAnimation();
						}
						else {
							if (this.targetPose === 3) {
								pose = 27;
								newPoseIndex = 3;
							}
							else {
								if (this.targetPose !== 1 && this.targetPose !== 4 && this.targetPose !== 5) {
									if (this.targetPose === 6) {
										pose = 44;
										newPoseIndex = 6;
									}
									else {
										target.setOwner(this);
										this.interactionTarget = target;
										let offsetX: number = 0;
										let offsetY: number = 0;
										switch (this.facing) {
											case 0:
											default:
												offsetX = 524288;
												break;
											case 1:
												offsetY = 524288;
												break;
											case 2:
												offsetX = -524288;
												break;
											case 3:
												offsetY = -524288;
										}
										let targetFacing: number = target.facing;
										this.setFacing(targetFacing);
										let targetShiftX: number = 0;
										let targetShiftY: number = 0;
										switch (targetFacing) {
											case 0:
											default:
												targetShiftX = -196608;
												offsetX = ((offsetX + 131072) | 0);
												break;
											case 1:
												targetShiftY = -196608;
												offsetY = ((offsetY + 131072) | 0);
												break;
											case 2:
												targetShiftX = 196608;
												offsetX = ((offsetX - 131072) | 0);
												break;
											case 3:
												targetShiftY = 196608;
												offsetY = ((offsetY - 131072) | 0);
										}
										if (target.isPassable()) {
											target.moveTo(((target.posX + targetShiftX) | 0), ((target.posY + targetShiftY) | 0));
											target.addState(131072);
										}
										else {
											offsetX = ((offsetX - targetShiftX) | 0);
											offsetY = ((offsetY - targetShiftY) | 0);
										}
										this.moveTo(((this.posX + offsetX) | 0), ((this.posY + offsetY) | 0));
										pose = 20;
										newPoseIndex = 2;
									}
									break;
								}
								this.interactionTarget = target;
								target.setOwner(this);
								pose = 20;
								newPoseIndex = 1;
							}
						}
						break;
					case 1:
						if (this.targetPose === 0) {
							pose = 18;
							newPoseIndex = 0;
						}
						else {
							if (this.targetPose === 4) {
								pose = 17;
								newPoseIndex = 4;
							}
							else {
								if (this.targetPose === 5) {
									pose = 53;
									newPoseIndex = 5;
								}
							}
						}
						break;
					case 2:
						pose = 18;
						newPoseIndex = 0;
						break;
					case 3:
						pose = 30;
						newPoseIndex = 0;
						break;
					case 4:
						pose = 16;
						newPoseIndex = 1;
						break;
					case 5:
						pose = 55;
						newPoseIndex = 1;
						break;
					case 6:
						pose = 42;
						newPoseIndex = 0;
						break;
					case 7:
						pose = 35;
						newPoseIndex = 0;
						this.interactionTarget.playModeZeroAnimation();
				}
				this.poseIndex = newPoseIndex;
				this.clearState(128);
				this.setPose(pose);
				break;
			case 3:
				this.setPose(1);
				if (this.variant === 0 && screen.isNewNeighborhood()) {
					if (!this.reservedFlag && oldState !== 3) {
						screen.setCameraTarget(this);
					}
					screen.startMusic();
				}
				break;
			case 4:
				let neighborhood: Neighborhood = this.neighborhood();
				if (this.overlayPoseAnimId !== -1) {
					this.animatorAt(2).play(this.overlayPoseAnimId, 20);
					if (this.actionPhase === 5) {
						this.overlaySpriteId = neighborhood.commentText(this.actionId);
					}
				}
				else {
					this.animatorAt(2).setRunning(false);
				}
				if (this.poseAnimId === -1) {
					this.poseAnimId = neighborhood.interactionAltText(this.actionId);
				}
				let partner: Sim = this.partnerSim();
				if (this.actionId !== 82 && this.actionId !== 83) {
					if (this.actionId === 93) {
						partner.clearState(128);
						partner.setPose(32);
					}
					else {
						if (this.actionId === 84) {
							let partnerId: number = partner.variant;
							if (neighborhood.canUseInteraction(this.variant, partnerId, this.actionId)) {
								screen.openDialogPage(3, -1, partnerId);
							}
						}
					}
				}
				else {
					if (neighborhood.canUseInteraction(this.variant, partner.variant, this.actionId)) {
						partner.clearState(128);
						partner.setPose(38);
						this.poseAnimId = 41;
					}
					else {
						partner.clearState(128);
						partner.setPose(40);
						this.poseAnimId = 39;
					}
				}
				if (!this.useTimer) {
					this.clearState(128);
				}
				this.setPose(this.poseAnimId);
				break;
			case 5:
			case 6:
				this.setPose(0);
				break;
			case 7:
				this.faceEntity(this.actionTarget);
				let poseAfterAction: number = 7;
				if (this.partnerSim() !== null && (this.partnerSim().actionId === 82 || this.partnerSim().actionId === 83)) {
					poseAfterAction = 0;
				}
				this.setPose(poseAfterAction);
			case 8:
			default:
				break;
			case 9:
				this.despawn();
		}
		if (this.motionState === 1 && this.poseIndex === 2 && this.variant === 0) {
			this.lastFacing = this.facing;
		}
	}
	startTimedAction(idleAnim: number, duration: number, overlayAnim: number, fadeOverlay: boolean, waitForTargetTime: boolean): void {
		this.poseAnimId = idleAnim;
		this.useTimer = duration >= 0;
		this.timer = duration;
		this.overlayPoseAnimId = overlayAnim;
		this.fadeOverlay = fadeOverlay;
		this.waitForTargetTime = waitForTargetTime;
		this.setMotionState(4);
	}
	clearTimedAction(): void {
		this.poseAnimId = -1;
		this.useTimer = false;
		this.timer = -1;
		this.overlayPoseAnimId = -1;
		this.fadeOverlay = false;
		this.waitForTargetTime = true;
	}
	waitForTimer(duration: number, waitForTargetTime: boolean): void {
		this.timer = duration;
		this.waitForTargetTime = waitForTargetTime;
		this.setMotionState(8);
	}
	updateIdle(delta: number): void {
		this.advanceAnimations(delta);
		this.autonomyTimer = ((this.autonomyTimer + delta) | 0);
		if (this.type === 0) {
			if (this.stateTime > 3000 && this.neighborhood().activeTaskSlotOf(10) !== -1) {
				this.queueAction(3, (null as unknown as Entity));
				return;
			}
			if (this.stateTime > 3500 && this.poseIndex === 0) {
				let balance: number = 0;
				if ((balance = this.neighborhood().moodValue) > 4915200) {
					this.queueAction(5, (null as unknown as Entity));
					return;
				}
				if (balance < -3276800) {
					this.queueAction(6, (null as unknown as Entity));
					return;
				}
			}
		}
		let nearbyEntity: Entity = null!;
		if (this.poseIndex === 1 && this.interactionTarget.behaviorClass() === 9 && (nearbyEntity = this.screen.findEntityByObjectType(22)) !== null && nearbyEntity.hasState(64) && this.stateTime > 1000) {
			if (this.variant === 0) {
				this.neighborhood().unlockAchievement(36);
			}
			let interactionId: number = 16;
			if (nearbyEntity.hasState(32768)) {
				interactionId = 15;
			}
			this.queueAction(interactionId, (null as unknown as Entity));
		}
		else {
			if (this.poseIndex === 0 && this.stateTime > 5000 && (nearbyEntity = this.screen.findEntityByObjectType(18)) !== null && nearbyEntity.hasState(64)) {
				let ignored: any = null;
				let targetTileX: number = Lot.toTileX(nearbyEntity.posX);
				let targetTileY: number = Lot.toTileY(nearbyEntity.posY);
				let selfTileX: number = Lot.toTileX(this.posX);
				let selfTileY: number = Lot.toTileY(this.posY);
				if (((Math.abs(((selfTileX - targetTileX) | 0)) + Math.abs(((selfTileY - targetTileY) | 0))) | 0) < 8) {
					this.queueAction(17, (null as unknown as Entity));
					return;
				}
			}
			if (this.autonomyTimer > 2500 && this.canOccupySlot()) {
				this.autonomyTimer = 0;
				this.chooseAutonomousAction();
			}
		}
	}
	updateInteraction(delta: number): void {
		this.advanceAnimations(delta);
		if (!this.isAnimating()) {
			if (this.poseIndex === 0 && this.interactionTarget !== null && this.interactionTarget.behaviorClass() === 6) {
				let standOffset: number = 131072;
				let stepX: number = 0;
				let stepY: number = 0;
				switch (this.facing) {
					case 0:
					default:
						stepX = 1;
						break;
					case 1:
						stepY = 1;
						break;
					case 2:
						stepX = -1;
						break;
					case 3:
						stepY = -1;
				}
				let target: Entity = this.interactionTarget;
				if (this.interactionTarget.hasState(131072)) {
					target.clearState(131072);
					target.moveTo(((target.posX + Math.imul(stepX, 196608)) | 0), ((target.posY + Math.imul(stepY, 196608)) | 0));
				}
				else {
					standOffset = ((standOffset + 196608) | 0);
				}
				let lot: Lot = this.lot();
				let standX: number = ((((this.posX + Math.imul(stepY, 524288)) | 0) + Math.imul(stepX, ((standOffset * -1) | 0))) | 0);
				let standY: number = ((((this.posY + Math.imul(stepX, 524288)) | 0) + Math.imul(stepY, ((standOffset * -1) | 0))) | 0);
				let standOk: boolean = lot.isWalkableAt(standX, standY);
				let altX: number = ((((this.posX + Math.imul(stepY, -524288)) | 0) + Math.imul(stepX, ((standOffset * -1) | 0))) | 0);
				let altY: number = ((((this.posY + Math.imul(stepX, -524288)) | 0) + Math.imul(stepY, ((standOffset * -1) | 0))) | 0);
				let altOk: boolean = lot.isWalkableAt(altX, altY);
				let altFree: boolean = this.screen.isEntityAtTile(0, altX, altY);
				if (!standOk || !altFree && altOk) {
					this.moveTo(altX, altY);
				}
				else {
					this.moveTo(standX, standY);
				}
			}
			if (this.poseIndex === this.targetPose) {
				this.finishPhase();
				return;
			}
			this.setMotionState(2);
		}
	}
	updateWalking(delta: number): void {
		let lot: Lot = this.lot();
		let posX: number = this.posX;
		let posY: number = this.posY;
		let destX: number = this.destX;
		let destY: number = this.destY;
		let tileX: number = Lot.toTileX(posX);
		let tileY: number = Lot.toTileY(posY);
		let tileFlags: number = lot.cellFlagsAt(tileX, tileY);
		let dirX: number = 0;
		let dirY: number = (dirX = FixedMath.sign(((destX - posX) | 0))) !== 0 ? 0 : FixedMath.sign(((destY - posY) | 0));
		let speedX: number = 2048;
		let speedY: number = 1024;
		if (this.hasState(2048)) {
			speedX = 2621;
			speedY = 1310;
		}
		else {
			if (this.hasState(4096)) {
				speedX = 1638;
				speedY = 786;
			}
		}
		if (this.screen.isNewNeighborhood()) {
			speedX = speedY;
			if (this.isNearWater()) {
				let selfTileX: number = Lot.toTileX(posX);
				let selfTileY: number = Lot.toTileY(posY);
				let inWater: boolean = false;
				if (inWater = (lot.cellFlagsAt(selfTileX, selfTileY) & 32) !== 0) {
					let neighborFlags: number = 0;
					if (dirY === 0) {
						let tileCentreX: number = Lot.toFixedX(selfTileX);
						if ((dirX >= 0 || posX > tileCentreX) && (dirX <= 0 || posX < tileCentreX)) {
							neighborFlags = lot.cellFlagsAt(((selfTileX - dirX) | 0), selfTileY);
						}
						else {
							neighborFlags = lot.cellFlagsAt(((selfTileX + dirX) | 0), selfTileY);
						}
					}
					else {
						let tileCentreY: number = Lot.toFixedY(selfTileY);
						if ((dirY >= 0 || posY > tileCentreY) && (dirY <= 0 || posY < tileCentreY)) {
							neighborFlags = lot.cellFlagsAt(selfTileX, ((selfTileY - dirY) | 0));
						}
						else {
							neighborFlags = lot.cellFlagsAt(selfTileX, ((selfTileY + dirY) | 0));
						}
					}
					inWater = (neighborFlags & 32) !== 0;
				}
				if (inWater) {
					speedX = 2048;
					this.setSpriteVariant(3);
				}
				else {
					this.setSpriteVariant(2);
				}
			}
		}
		let step: number = Math.imul(speedX, delta);
		posX = ((posX + (Math.imul(dirX, step))) | 0);
		posY = ((posY + (Math.imul(dirY, step))) | 0);
		if (dirX > 0 && posX > destX || dirX < 0 && posX < destX) {
			posX = destX;
		}
		if (dirY > 0 && posY > destY || dirY < 0 && posY < destY) {
			posY = destY;
		}
		this.faceDirection(dirX, dirY);
		this.moveTo(posX, posY);
		if (!this.screen.isNewNeighborhood()) {
			let newTileX: number = Lot.toTileX(posX);
			let newTileY: number = Lot.toTileY(posY);
			let newTileFlags: number = lot.cellFlagsAt(newTileX, newTileY);
			let blockingEntity: Entity = null!;
			if ((tileFlags & 32) === 0 && (newTileFlags & 32) !== 0 && (blockingEntity = this.screen.findEntityByObjectType(10)) !== null && !blockingEntity.hasState(64)) {
				blockingEntity.handleAction(150, this);
			}
		}
		this.advanceAnimations(delta);
		if (posX === destX && posY === destY) {
			if (!this.canOccupySlot() && this.type !== 0) {
				this.abortAction(true);
				return;
			}
			if (this.actionTarget !== null && (this.actionTarget.posX !== this.targetLastX || this.actionTarget.posY !== this.targetLastY)) {
				this.walkTo(this.actionTarget, 0, 0);
				return;
			}
			let cannotReserve: boolean = !this.reserveTarget(this.actionTarget);
			let needsDetour: boolean = this.detourPath || cannotReserve;
			let pathPoints: Int32Array = this.path;
			let remainingPoints: number = 0;
			if ((remainingPoints = this.path !== null ? (((pathPoints.length >> 1) - this.pathIndex) | 0) : -1) > (needsDetour ? 2 : 1)) {
				if (this.actionTarget !== null && ((remainingPoints & 3) === 0 || remainingPoints < 4)) {
					if (!cannotReserve) {
						this.reserveTarget(null);
						this.reserveTarget(this.actionTarget);
					}
					this.walkTo(this.actionTarget, 0, 0);
					return;
				}
				this.pathIndex++;
				this.destX = pathPoints[(((this.pathIndex << 1) + 0) | 0)];
				this.destY = pathPoints[(((this.pathIndex << 1) + 1) | 0)];
				return;
			}
			this.path = null;
			if (cannotReserve) {
				this.runActionPhase(9);
				return;
			}
			this.finishPhase();
		}
	}
	walkTo(target: Entity, toX: number, toY: number): void {
		let lot: Lot = this.lot();
		let cannotReserve: boolean = false;
		let targetX: number = toX;
		let targetY: number = toY;
		if (target !== null) {
			cannotReserve = !this.reserveTarget(target);
			let slot: number = this.reservedSlot;
			if (this.reservedSlot === -1) {
				slot = target.findNearestSlot(this.posX, this.posY, false, null);
			}
			let slotOffsets: Int32Array = Entity.boundsScratch;
			target.computeSlotPosition(slotOffsets, slot);
			targetX = slotOffsets[0];
			targetY = slotOffsets[1];
			this.targetLastX = target.posX;
			this.targetLastY = target.posY;
		}
		targetX = Lot.toFixedX(Lot.toTileX(targetX));
		targetY = Lot.toFixedY(Lot.toTileY(targetY));
		if ((targetX !== this.posX || targetY !== this.posY) && this.poseIndex !== 0) {
			this.actionStep--;
			this.runActionPhase(30);
		}
		else {
			if (this.screen.isNoSaveMode() && lot.areaId() !== 0 && this.variant === 0 && this.actionId !== 153 && !this.hasState(32) && !this.neighborhood().hasSimMilestone(0, 1)) {
				let selfTileX: number = Lot.toTileX(this.posX);
				let selfTileY: number = Lot.toTileY(this.posY);
				let targetTileX: number = Lot.toTileX(targetX);
				let targetTileY: number = Lot.toTileY(targetY);
				if ((lot.cellFlagsAt(selfTileX, selfTileY) & 16) === 0 && (lot.cellFlagsAt(targetTileX, targetTileY) & 16) !== 0) {
					this.queuedActionId = this.actionId;
					this.queuedTarget = this.actionTarget;
					this.queuedObjectId = this.actionObjectId;
					this.queuedValue = this.actionValue;
					this.addState(16);
					this.queueAction(153, this.screen.findEntityByObjectType(10));
					return;
				}
			}
			this.clearState(32);
			let pathFlags: number = 0;
			if (this.screen.isNewNeighborhood()) {
				if (this.isNearWater()) {
					pathFlags = 2;
				}
				else {
					pathFlags = 4;
				}
			}
			else {
				pathFlags = 8;
				let sims: Sim[] = null!;
				if ((sims = this.screen.getSims()).length > 1) {
					lot.clearWaypoints();
					for (let simIndex: number = 0; simIndex < sims.length; simIndex++) {
						let otherSim: Sim = null!;
						if ((otherSim = sims[simIndex]) !== this) {
							let otherTileX: number = Lot.toTileX(otherSim.posX);
							let otherTileY: number = Lot.toTileY(otherSim.posY);
							lot.addWaypoint(otherTileX, otherTileY);
						}
					}
					pathFlags = 9;
				}
			}
			let startX: number = this.posX;
			let startY: number = this.posY;
			let pathPoints: Int32Array = lot.findPath(startX, startY, targetX, targetY, pathFlags);
			if (cannotReserve && pathPoints.length === 2) {
				this.runActionPhase(9);
			}
			else {
				if (pathPoints.length >= 2) {
					this.path = pathPoints;
					this.pathIndex = 0;
					this.detourPath = false;
					this.destX = pathPoints[(((this.pathIndex << 1) + 0) | 0)];
					this.destY = pathPoints[(((this.pathIndex << 1) + 1) | 0)];
					this.setMotionState(3);
				}
				else {
					let diffX: number = ((startX - targetX) | 0);
					let diffY: number = ((startY - targetY) | 0);
					let distance: number = 0;
					if ((distance = ((Math.abs(diffX) + Math.abs(diffY)) | 0)) <= 524288) {
						if (distance !== 0) {
							this.path = pathPoints;
							this.pathIndex = 0;
							this.detourPath = false;
							this.destX = targetX;
							this.destY = targetY;
							this.setMotionState(3);
						}
						else {
							this.finishPhase();
						}
					}
					else {
						this.abortAction(false);
					}
				}
			}
		}
	}
	updateTimedAction(delta: number): void {
		let previousFrame: number = this.animatorAt(0).frame;
		this.advanceAnimations(delta);
		let frame: number = this.animatorAt(0).frame;
		let animator: Animator = this.animatorAt(0);
		if (previousFrame !== frame) {
			if (this.actionId === 147 && frame === 2) {
				this.actionTarget.interactWith(this);
			}
			if (this.actionId === 93 && frame === 2 && (this.variant === 0 || this.partnerSim() !== null && this.partnerSim().variant === 0)) {
				this.canvas.vibrateShort();
			}
			let turnFrame: number = this.facing !== 0 && this.facing !== 1 ? 3 : 2;
			if (this.actionId === 83 && this.poseId === 41 && frame === turnFrame) {
				this.screen.openDialogPage(2, 789, this.actionTarget.variant);
			}
			if (this.actionId === 4 && frame === 15 && this.canvas.randomPercent() < 5) {
				this.screen.openStatsDialog(803);
			}
		}
		this.animatorAt(2).advance(delta);
		let finished: boolean = false;
		if (this.useTimer) {
			if (this.waitForTargetTime) {
				finished = this.timer <= this.neighborhood().gameTime;
			}
			else {
				if (!GameScreen.isDialogOpen()) {
					this.timer = ((this.timer - delta) | 0);
				}
				finished = this.timer <= 0;
			}
		}
		else {
			finished = !animator.isRunning();
		}
		if (this.actionPhase === 49 && this.actionTarget.isModeTwo()) {
			this.finishPhase();
		}
		if (finished) {
			this.poseAnimId = -1;
			this.timer = -1;
			this.useTimer = true;
			this.finishPhase();
		}
		else {
			if (this.variant === 0 && this.actionPhase === 20 && this.stateTime > 4000 && this.waitForTargetTime && ((this.timer - this.neighborhood().gameTime) | 0) > 60 && this.canOccupySlot() && !this.screen.hasFade()) {
				this.screen.syncFadeWithAction();
			}
			if (this.actionPhase === 17 && this.actionTarget.behaviorClass() === 3 && this.poseId !== 50 && this.actionTarget.isModeOne()) {
				if (this.variant === 0) {
					this.neighborhood().setNeedModifier(3, 655360);
				}
				this.setPose(50);
			}
		}
	}
	updateSocial(delta: number): void {
		this.advanceAnimations(delta);
		let partner: Sim = this.partnerSim();
		if (this.actionId !== 94) {
			let diffX: number = ((partner.posX - this.posX) | 0);
			let diffY: number = ((partner.posY - this.posY) | 0);
			if (((Math.abs(diffX) + Math.abs(diffY)) | 0) > 524288) {
				this.abortAction(false);
				return;
			}
		}
		if (this.stateTime > 4000) {
			this.abortAction(false);
		}
		else {
			if (partner.isIdleModeZero()) {
				partner.queueAction(2, (this as unknown as Entity));
				this.finishPhase();
			}
		}
	}
	updateApproach(delta: number): void {
		this.advanceAnimations(delta);
		if (this.stateTime > 4000) {
			this.abortAction(false);
		}
		else {
			if (this.reserveTarget(this.actionTarget)) {
				this.runActionPhase(1);
			}
		}
	}
	updateDiningPartner(delta: number): void {
		this.advanceAnimations(delta);
		let partner: Sim = null!;
		if ((partner = this.partnerSim()) !== null && !partner.isIdleModeZero()) {
			if (partner !== null && partner.hasState(1024)) {
				let neighborhood: Neighborhood = null!;
				if ((neighborhood = this.neighborhood()).simKnownIndex(this.variant, 12) !== -1) {
					if (!this.hasState(262144)) {
						neighborhood.simRemember(this.variant, 12);
						this.addState(262144);
						return;
					}
				}
				else {
					partner.cancelAction();
					this.unlinkPartner();
					let actionId: number = 0;
					if (neighborhood.simKnownIndex(this.variant, 13) !== -1) {
						actionId = 34;
						neighborhood.simRemember(this.variant, 13);
						neighborhood.moveSim(this.variant, -1, -1);
					}
					this.startAction(14, partner, actionId, 0);
				}
			}
		}
		else {
			this.finishPhase();
		}
	}
	updateWaiting(delta: number): void {
		if (this.actionId !== 159) {
			if (this.waitForTargetTime) {
				if (this.timer <= this.neighborhood().gameTime) {
					this.finishPhase();
					return;
				}
			}
			else {
				if (this.timer > 0) {
					this.timer = ((this.timer - delta) | 0);
					if (this.timer <= 0) {
						this.finishPhase();
					}
				}
			}
		}
	}
	partnerSim(): Sim {
		return this.actionTarget instanceof Sim ? (this.actionTarget as unknown as Sim) : null;
	}
	isIdleModeZero(): boolean {
		return (this.neighborhood().interactionFlagsOf(this.actionId) & 2) !== 0;
	}
	isFree(): boolean {
		return this.isIdleModeZero() || this.actionPhase === 8 || this.actionPhase === 9 || this.actionPhase === 1;
	}
	isLeavingLot(): boolean {
		return this.actionPhase === 26;
	}
	isSitting(): boolean {
		return (this.actionId === 122 || this.actionId === 121) && this.poseIndex === 4;
	}
	queueAction_2(actionId: number, target: Entity, objectId: number, value: number): void {
		if (this.isFree()) {
			this.startAction(actionId, target, objectId, value);
		}
		else {
			this.queuedActionId = actionId;
			this.queuedTarget = target;
			this.queuedObjectId = objectId;
			this.queuedValue = value;
		}
	}
	queueAction(actionId: number, target: Entity): void {
		this.startAction(actionId, target, 0, 0);
	}
	startAction(actionId: number, target: Entity, objectId: number, value: number): void {
		let neighborhood: Neighborhood = this.neighborhood();
		if (this.actionPhase !== 0) {
			this.endCurrentPhase(true);
		}
		this.clearState(262146);
		if (actionId === 119 && this.spriteVariant === 0 && this.canvas.randomPercent() < 50) {
			actionId = 120;
		}
		this.actionId = actionId;
		this.actionTarget = target;
		this.actionObjectId = objectId;
		this.actionValue = value;
		if (target !== null || actionId === 106) {
			this.reserveTarget(target);
		}
		let partner: Sim = null!;
		if ((partner = this.partnerSim()) !== null && (neighborhood.interactionFlagsOf(actionId) & 1) !== 0) {
			this.linkPartners(partner);
		}
		let phase: number = neighborhood.interactionVariant(actionId, 0);
		this.actionStep = 0;
		this.runActionPhase(phase);
	}
	onBumped(other: Entity): void {
		if (other !== this && (this.behaviourFlags & 32) !== 0) {
			let playerPartner: Sim = this.screen.activePlayer.partnerSim();
			if (this.partnerId === 0 || playerPartner !== null && this.partnerId === playerPartner.variant) {
				this.unlinkPartner();
			}
		}
	}
	reserveTarget(target: Entity): boolean {
		if (target === this.reservedTarget) {
			return true;
		}
		else {
			if (this.reservedTarget !== null) {
				this.reservedTarget.releaseSlot(this.reservedSlot);
				this.reservedTarget = null;
				this.reservedSlot = -1;
			}
			if (target === null) {
				return true;
			}
			else {
				if (target.hasFreeSlot()) {
					let slot: number = 0;
					if (target.behaviorClass() === 10) {
						let lot: Lot = this.lot();
						let tileX: number = Lot.toTileX(this.posX);
						let tileY: number = Lot.toTileY(this.posY);
						if ((lot.cellFlagsAt(tileX, tileY) & 16) !== 0) {
							slot = 0;
						}
						else {
							slot = 1;
						}
					}
					else {
						slot = target.findNearestSlot(this.posX, this.posY, true, this);
					}
					if (slot !== -1 && target.tryOccupySlot(slot)) {
						this.reservedTarget = target;
						this.reservedSlot = slot;
						return true;
					}
				}
				return false;
			}
		}
	}
	completeAction(): void {
		let alreadyCompleted: boolean = this.hasState(4);
		let neighborhood: Neighborhood = this.neighborhood();
		this.clearState(12);
		let actionId: number = this.actionId;
		let target: Entity = this.actionTarget;
		let partner: Sim = this.partnerSim();
		let isPlayer: boolean = this.variant === 0;
		this.queueAction(0, (null as unknown as Entity));
		let actionFlags: number = neighborhood.interactionFlagsOf(actionId);
		if (!alreadyCompleted && partner !== null && (actionFlags & 1) !== 0) {
			this.reserveTarget(null);
			partner.reserveTarget(null);
			if (partner.handleAction(actionId, this)) {
				this.unlinkPartner();
			}
			if (isPlayer) {
				let partnerId: number = partner.variant;
				if (actionId === 93) {
					neighborhood.unlockAchievement(19);
					neighborhood.addSimMilestoneCount(partnerId, 32);
					neighborhood.addSimMilestoneCount(partnerId, 2048);
				}
				else {
					if (actionId === 105) {
						neighborhood.addSimMilestoneCount(partnerId, 256);
					}
				}
			}
		}
		else {
			if (!alreadyCompleted) {
				switch (actionId) {
					case 4:
						if (isPlayer) {
							neighborhood.adjustNeed(3, -6553600);
						}
						if (!this.screen.isEntityAtTile(1, this.posX, this.posY)) {
							let slot: number = -1;
							if (this.lot().areaId() === 0) {
								slot = this.lot().addObject(9, this.posX, this.posY, this.facing);
							}
							this.screen.createEntity(9, this.posX, this.posY, this.facing, slot);
						}
						break;
					case 107:
						this.screen.startTutorialGame();
						break;
					case 111:
					case 113:
						this.screen.raiseSkillLevel(4);
						break;
					case 121:
					case 122:
						this.behaviourFlags = (this.behaviourFlags & -5);
						break;
					case 143:
						this.screen.confirmRequest();
						break;
					case 147:
						if (isPlayer) {
							neighborhood.unlockAchievement(37);
							neighborhood.addSimMilestone(this.lot().areaId(), 16);
							neighborhood.addSimMilestone(this.lot().areaId(), 4096);
						}
						break;
					case 152:
						if (!isPlayer) {
							this.stateTime = 0;
							this.screen.showSimProfile(this.variant);
						}
						break;
					case 155:
					case 156:
					case 160:
						this.screen.selectEntity(target);
						break;
					case 158:
						neighborhood.unlockAchievement(21);
						neighborhood.adjustNeed(0, 2621440);
				}
			}
		}
		if (actionId === 153) {
			this.clearState(16);
			if (alreadyCompleted) {
				this.queuedActionId = -1;
				this.queuedTarget = null;
			}
			else {
				this.addState(32);
			}
		}
		if (target !== null && partner === null && !alreadyCompleted) {
			target.handleAction(actionId, this);
			if (isPlayer) {
				neighborhood.markItemVisited(target.type);
			}
		}
		if (this.queuedActionId !== -1 && this.isIdleModeZero()) {
			this.startAction(this.queuedActionId, this.queuedTarget, this.queuedObjectId, this.queuedValue);
			this.queuedActionId = -1;
			this.queuedTarget = null;
		}
	}
	handleAction(actionId: number, otherSim: Sim): boolean {
		let neighborhood: Neighborhood = this.neighborhood();
		let otherSimId: number = otherSim.variant;
		let selfId: number = this.variant;
		let result: Int32Array = Entity.boundsScratch;
		neighborhood.resolveInteraction(result, otherSimId, selfId, actionId);
		let replyFlags: number = result[0];
		let replyActionId: number = result[1];
		let soundId: number = result[2];
		if ((replyFlags & 64) !== 0) {
			this.screen.notifySimsOfAction(25, otherSim, this);
		}
		if (actionId === 103 || actionId === 98) {
			this.queueAction(0, (null as unknown as Entity));
			this.addBehaviourFlags(256);
		}
		else {
			if (replyActionId !== -1) {
				if ((neighborhood.interactionFlagsOf(replyActionId) & 1) === 0 && replyActionId !== 106) {
					otherSim = null;
				}
				if (otherSimId === 0 && replyActionId === 13) {
					neighborhood.unlockAchievement(15);
					neighborhood.addSimMilestoneCount(selfId, 8192);
				}
				this.startAction(replyActionId, otherSim, replyFlags, 0);
				if (soundId !== -1) {
					this.screen.createRoomObject(this.posX, this.posY, soundId, 0);
				}
			}
		}
		return (replyFlags & 32) !== 0;
	}
	tryInteraction(actionId: number, otherSim: Sim, object: Entity): boolean {
		let neighborhood: Neighborhood = this.neighborhood();
		if (this.isSitting()) {
			return false;
		}
		else {
			if (actionId === 25 && neighborhood.simPortrait(this.variant) !== object.variant) {
				return false;
			}
			else {
				let result: Int32Array = Entity.boundsScratch;
				neighborhood.resolveInteraction(result, otherSim.variant, this.variant, actionId);
				let replyActionId: number = 0;
				if ((replyActionId = result[1]) !== -1) {
					let replyFlags: number = 0;
					let target: any = ((replyFlags = result[0]) & 16384) !== 0 ? object : otherSim;
					this.cancelAction();
					this.queueAction_2(replyActionId, (target as unknown as Entity), 0, 0);
					if (actionId === 25 && otherSim.variant === 0) {
						neighborhood.unlockAchievement(51);
					}
					this.addState(8);
					return true;
				}
				else {
					return false;
				}
			}
		}
	}
	runActionPhase(phase: number): void {
		let screen: GameScreen = this.screen;
		let neighborhood: Neighborhood = this.neighborhood();
		this.actionPhase = phase;
		let actionId: number = this.actionId;
		let target: Entity = this.actionTarget;
		let partner: Sim = this.partnerSim();
		let objectId: number = this.actionObjectId;
		let value: number = this.actionValue;
		let isPlayer: boolean = false;
		if ((isPlayer = this.variant === 0) && partner !== null) {
			neighborhood.watchSim(partner.variant);
		}
		let handled: boolean = false;
		switch (phase) {
			case 0:
				if (this.poseIndex !== 7 && this.poseIndex !== 6) {
					this.setMotionState(1);
				}
				else {
					this.actionStep--;
					this.runActionPhase(30);
				}
				break;
			case 1:
				this.walkTo(target, objectId, value);
				break;
			case 2:
			case 3:
				if (target !== null) {
					this.faceEntity(target);
				}
				else {
					this.faceDirection(((objectId - this.posX) | 0), ((value - this.posY) | 0));
				}
				if (phase === 3) {
					this.setFacing(((this.facing + 2) | 0) & 3);
				}
				this.finishPhase();
				break;
			case 4:
				this.setFacing(target.facing);
				this.finishPhase();
				break;
			case 5:
			case 6:
			case 7:
				this.runTimedPhase(actionId, phase);
				handled = true;
				break;
			case 8:
				this.setMotionState(5);
				break;
			case 9:
				this.setMotionState(6);
				break;
			case 10:
				this.partnerSim().cancelAction();
				this.finishPhase();
				break;
			case 11:
				this.setMotionState(7);
				break;
			case 12:
				if (neighborhood.purchaseUpgrade(objectId)) {
					screen.openMiniGame(0, target);
					target.startModeOne();
					this.startTimedAction(4, 1000, -1, false, false);
				}
				else {
					this.abortAction(false);
				}
				break;
			case 13:
				if (neighborhood.hasItemC()) {
					screen.openMiniGame(1, target);
					if (target.type === 5) {
						this.setFacing(0);
					}
					else {
						this.setFacing(1);
					}
					this.setValueC(20);
					this.startTimedAction(24, 1000, -1, false, false);
				}
				else {
					this.abortAction(false);
				}
				break;
			case 14:
				if (neighborhood.hasItemB()) {
					screen.openMiniGame(2, target);
					this.startTimedAction(4, 1000, -1, false, false);
				}
				else {
					this.abortAction(false);
				}
				break;
			case 15:
				if (neighborhood.hasItemA()) {
					screen.openMiniGame(3, target);
					this.startTimedAction(4, 1000, -1, false, false);
				}
				else {
					this.abortAction(false);
				}
				break;
			case 16:
				let duration: number = 5000;
				if (isPlayer) {
					let amount: number = 458752;
					if (actionId === 126) {
						amount = this.lot().volumeOf(objectId);
					}
					neighborhood.setNeedModifier(0, amount);
				}
				if (target.behaviorClass() === 13) {
					target.playFirstModeAnimation();
					duration = 3500;
				}
				let idleAnimForPose: number = 4;
				if (this.poseIndex === 2 || this.poseIndex === 1) {
					idleAnimForPose = 60;
				}
				this.startTimedAction(idleAnimForPose, duration, -1, false, false);
				handled = true;
				break;
			case 17:
				let targetType: number = 0;
				if ((targetType = target.behaviorClass()) === 17) {
					if (isPlayer) {
						neighborhood.setNeedModifier(3, 655360);
					}
					target.activateModeOne();
					this.waitForTimer(5000, false);
					handled = true;
				}
				else {
					if (targetType === 3) {
						target.enterModeTwoIfIdle();
						target.setOwner(this);
						this.startTimedAction(4, 8000, -1, false, false);
						handled = true;
					}
					else {
						if (targetType === 2) {
							if (isPlayer && !this.hasState(1024)) {
								neighborhood.setNeedModifier(3, 655360);
							}
							target.enterModeOne();
							this.startTimedAction(4, 1500, -1, false, false);
						}
					}
				}
				break;
			case 18:
				if (isPlayer) {
					neighborhood.setNeedModifier(2, 3276800);
				}
				let idleAnim: number = actionId === 119 ? 28 : 29;
				this.startTimedAction(idleAnim, 2000, -1, false, false);
				break;
			case 19:
			case 20:
				this.runSleepPhase(phase);
				handled = true;
				break;
			case 21:
				target.markActive();
				this.finishPhase();
				handled = true;
				break;
			case 22:
				target.markInactive();
				this.finishPhase();
				handled = true;
				break;
			case 23:
				if (isPlayer) {
					neighborhood.unlockAchievement(36);
				}
				let spareEntity: Entity = null!;
				if ((spareEntity = screen.findEntityByObjectType(9)) !== null) {
					this.startAction(144, spareEntity, 0, 0);
				}
				else {
					this.startAction(106, target, 0, 0);
				}
				handled = true;
				break;
			case 24:
				let freedEntity: Entity = null!;
				if ((freedEntity = screen.findFreeEntityForFlag(128)) !== null) {
					this.actionTarget = freedEntity;
					this.finishPhase();
				}
				else {
					this.abortAction(false);
				}
				break;
			case 25:
				if (isPlayer) {
					neighborhood.setNeedModifier(5, 196608);
				}
				this.startTimedAction(58, 10000, -1, false, false);
				break;
			case 26:
				if (isPlayer) {
					this.setMotionState(8);
					screen.syncFadeWithAction();
				}
				else {
					if (screen.getSims().length === 2 && !neighborhood.isFreeToLeave()) {
						screen.chooseDialogOption(767, neighborhood.simNameText(this.variant));
					}
					this.setMotionState(9);
				}
				break;
			case 27:
				screen.handleRelationshipCommand(actionId, objectId);
				this.finishPhase();
				break;
			case 28:
				let soundGroup: number = neighborhood.pickActionFor(target);
				let soundIndex: number = neighborhood.lastHourIndex(soundGroup);
				screen.selectFriend(soundGroup, soundIndex, true);
				this.finishPhase();
				break;
			case 29:
				screen.chooseDialogOption(766, 765);
				this.finishPhase();
				break;
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 36:
			case 37:
			case 38:
				this.startPosePhase(phase);
				break;
			case 39:
				if (isPlayer) {
					this.runPurchasePhase();
				}
				else {
					this.finishPhase();
				}
				break;
			case 40:
				if (isPlayer) {
					this.runLeaveCheckPhase();
				}
				else {
					this.finishPhase();
				}
				break;
			case 41:
				if (isPlayer) {
					this.runVisitPhase();
				}
				else {
					this.finishPhase();
				}
				break;
			case 42:
				screen.openVehicleInfo(target);
				this.finishPhase();
				break;
			case 43:
				screen.openBuildMenu();
				this.finishPhase();
				break;
			case 44:
				screen.openBuyMenu();
				this.finishPhase();
				break;
			case 45:
				this.runTakeItemPhase();
				break;
			case 46:
				this.runBuyFoodPhase();
				break;
			case 47:
				target.completeUpgrade();
				this.finishPhase();
				break;
			case 48:
				target.transformToCompleted();
				this.finishPhase();
				break;
			case 49:
				target.setModeOne();
				this.startTimedAction(0, 60000, -1, false, false);
				handled = true;
				break;
			case 50:
				if (isPlayer) {
					neighborhood.setNeedModifier(2, -327680);
					neighborhood.setNeedModifier(1, 655360);
				}
				this.startTimedAction(4, 2000, -1, false, false);
				break;
			case 51:
				let replacementTarget: Entity = target.occupant();
				this.actionTarget = replacementTarget;
				this.faceEntity(replacementTarget);
				this.runActionPhase(31);
				break;
			case 52:
				if (isPlayer) {
					neighborhood.setNeedModifier(5, 393216);
				}
				this.actionTarget = this.actionTarget.container();
				this.actionTarget.markActive();
				this.startTimedAction(60, 15000, -1, false, false);
		}
		if (isPlayer && handled && (neighborhood.interactionFlagsOf(actionId) & 1048576) !== 0) {
			screen.notifySimsOfAction(22, this, this.actionTarget);
		}
		if (this.hasState(2)) {
			this.abortAction(true);
		}
	}
	runTimedPhase(actionId: number, motionState: number): void {
		let neighborhood: Neighborhood = this.neighborhood();
		let entity: Entity = null!;
		if (actionId === 152 && (entity = this.screen.findEntityByObjectType(10)) !== null && entity.hasState(64)) {
			this.finishPhase();
		}
		else {
			let ignored: boolean = false;
			let soundId: number = -1;
			if (motionState === 5) {
				soundId = this.facing !== 3 && this.facing !== 0 ? 662 : 661;
				let soundA: number = 0;
				let soundB: number = 0;
				if (neighborhood.simAttribute(this.variant, 0) === 1) {
					soundA = 4;
					soundB = 6;
				}
				else {
					soundA = 7;
					soundB = 9;
				}
				let canvas: GameCanvas = null!;
				(canvas = GameCanvas.instance()).soundValue.pendingSound = canvas.randomRange(soundA, (soundB | 0));
			}
			let objectSound: boolean = false;
			if (actionId !== 136 && actionId !== 137 && actionId !== 111 && this.actionObjectId !== 0) {
				soundId = Neighborhood.reactionText(this.actionObjectId);
				objectSound = true;
			}
			let duration: number = motionState === 7 ? -1 : 2000;
			if (actionId === 17) {
				duration = 5000;
			}
			this.startTimedAction(-1, duration, soundId, objectSound, false);
			if (this.variant === 0) {
				if (actionId === 148) {
					neighborhood.setNeedModifier(3, -1310720);
					return;
				}
				if (actionId === 15 || actionId === 16) {
					neighborhood.setNeedModifier(5, 196608);
					return;
				}
				if (actionId === 17) {
					neighborhood.setNeedModifier(5, 196608);
				}
			}
		}
	}
	runSleepPhase(phase: number): void {
		let neighborhood: Neighborhood = null!;
		let hour: number = (neighborhood = this.neighborhood()).gameTime;
		let wakeHour: number = neighborhood.lotClosingTime(this.variant);
		if (phase === 19) {
			wakeHour = Math.min(wakeHour, ((hour + 120) | 0));
		}
		let sleepTime: number = ((wakeHour - hour) | 0);
		if (this.variant === 0) {
			neighborhood.paused = true;
			neighborhood.setNeedModifier(1, 65536);
		}
		this.stopAnimation();
		this.setValueA(this.facing === 0 ? 36 : 35);
		let targetHour: number = ((hour + sleepTime) | 0);
		this.startTimedAction(15, targetHour, -1, false, true);
	}
	startPosePhase(phase: number): void {
		if (phase === 31) {
			if (this.actionTarget.behaviorClass() === 6) {
				phase = 33;
			}
			else {
				this.setFacing(((this.facing + 2) | 0) & 3);
				phase = 32;
			}
		}
		let pose: number = phase === 30 ? 0 : phase === 32 ? 1 : phase === 33 ? 2 : phase === 34 ? 3 : phase === 37 ? 6 : phase === 35 ? 4 : phase === 36 ? 5 : 7;
		if (this.poseIndex === pose) {
			this.finishPhase();
		}
		else {
			this.targetPose = pose;
			this.setMotionState(2);
		}
	}
	runPurchasePhase(): void {
		let neighborhood: Neighborhood = null!;
		let cost: number = (neighborhood = this.neighborhood()).eventTimeCost(this.actionId);
		let funds: number = neighborhood.money;
		if (neighborhood.money >= cost) {
			neighborhood.addMoney(((cost * -1) | 0));
			this.screen.createObjectWithParam(this.posX, this.posY, cost);
			this.finishPhase();
		}
		else {
			this.abortAction(false);
		}
	}
	runLeaveCheckPhase(): void {
		let neighborhood: Neighborhood = this.neighborhood();
		let canLeave: boolean = true;
		if (this.screen.getSims().length > 1) {
			canLeave = false;
		}
		else {
			if (neighborhood.simLot(0) === 4) {
				canLeave = false;
			}
			else {
				if (neighborhood.isFreeToLeave()) {
					canLeave = false;
				}
			}
		}
		if (canLeave) {
			this.abortAction(false);
		}
		else {
			this.finishPhase();
		}
	}
	runVisitPhase(): void {
		let neighborhood: Neighborhood = null!;
		if ((neighborhood = this.neighborhood()).activeTaskSlotOf(12) === -1 && neighborhood.activeTaskSlotOf(11) === -1) {
			this.finishPhase();
		}
		else {
			let actionId: number = 7;
			if (this.canvas.randomPercent() < 50) {
				actionId = 14;
			}
			this.queueAction(actionId, this.actionTarget);
		}
	}
	runTakeItemPhase(): void {
		let neighborhood: Neighborhood = this.neighborhood();
		let itemId: number = this.actionObjectId;
		if (neighborhood.itemCount(itemId) > 0) {
			neighborhood.addItem(itemId, -1);
			this.actionTarget.setUpgradedType(itemId);
			this.finishPhase();
		}
		else {
			this.abortAction(false);
		}
	}
	runBuyFoodPhase(): void {
		let neighborhood: Neighborhood = null!;
		if ((neighborhood = this.neighborhood()).itemCount(3) > 0 && neighborhood.itemCount(4) > 0) {
			neighborhood.addItem(4, -1);
			this.actionTarget.toggleAlternate();
			this.finishPhase();
		}
		else {
			this.abortAction(false);
		}
	}
	finishPhase(): void {
		this.endCurrentPhase(false);
	}
	endCurrentPhase(force: boolean): void {
		let neighborhood: Neighborhood = this.neighborhood();
		let lotType: number = this.lot().areaId();
		let stepCount: number = neighborhood.interactionVariantCount(this.actionId);
		let isPlayer: boolean = this.variant === 0;
		let target: Entity = this.actionTarget;
		switch (this.actionPhase) {
			case 5:
			case 6:
			case 7:
				this.clearTimedAction();
				if (isPlayer) {
					if (this.actionId === 148) {
						neighborhood.setNeedModifier(3, 0);
					}
					else {
						if (this.actionId === 15 || this.actionId === 16 || this.actionId === 17) {
							neighborhood.setNeedModifier(5, 0);
						}
					}
				}
			case 8:
			case 9:
			case 10:
			case 11:
			case 14:
			case 15:
			case 21:
			case 22:
			case 23:
			case 24:
			case 26:
			case 27:
			case 28:
			case 29:
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 36:
			case 37:
			case 38:
			case 39:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 51:
			default:
				break;
			case 12:
				target.activateModeZero();
				break;
			case 13:
				this.setValueC(0);
				break;
			case 16:
				if (isPlayer) {
					neighborhood.setNeedModifier(0, 0);
				}
				if (target !== null && target.behaviorClass() === 13) {
					target.playZeroModeAndTick();
				}
				break;
			case 17:
				if (isPlayer) {
					neighborhood.setNeedModifier(3, 0);
				}
				if (target !== null) {
					let targetType: number = 0;
					if ((targetType = target.behaviorClass()) === 17) {
						target.resetStateTimerToIdle();
						if (isPlayer && lotType !== 0) {
							neighborhood.unlockAchievement(39);
							neighborhood.addSimMilestone(lotType, 8);
						}
					}
					else {
						if (targetType === 2) {
							target.enterModeZero();
						}
						else {
							if (targetType === 3) {
								target.enterModeThreeIfBusy();
								target.clearOwner(this);
							}
						}
					}
				}
				break;
			case 18:
				if (isPlayer) {
					neighborhood.setNeedModifier(2, 0);
					if (lotType !== 0) {
						neighborhood.unlockAchievement(40);
						neighborhood.addSimMilestone(lotType, 4);
					}
				}
				break;
			case 19:
			case 20:
				if (isPlayer) {
					neighborhood.setNeedModifier(1, 0);
					neighborhood.paused = false;
					if (this.actionPhase === 20 && lotType !== 0) {
						neighborhood.unlockAchievement(38);
						let partnerType: number = target.doorOffset();
						neighborhood.addSimMilestone(lotType, partnerType);
					}
				}
				this.setValueA(-1);
				this.refreshLocomotionState();
				break;
			case 25:
				if (isPlayer) {
					neighborhood.setNeedModifier(5, 0);
				}
				break;
			case 49:
				if (target !== null) {
					target.setModeZero();
				}
				break;
			case 50:
				if (isPlayer) {
					neighborhood.setNeedModifier(2, 0);
					neighborhood.setNeedModifier(1, 0);
				}
				break;
			case 52:
				if (isPlayer) {
					neighborhood.setNeedModifier(5, 0);
				}
				target.markInactive();
		}
		if (!force) {
			if (this.actionStep < ((stepCount - 1) | 0)) {
				this.actionStep++;
				let nextPhase: number = neighborhood.interactionVariant(this.actionId, this.actionStep);
				this.runActionPhase(nextPhase);
				return;
			}
			this.completeAction();
		}
	}
	cancelAction(): void {
		let actionFlags: number = 0;
		if (((actionFlags = this.neighborhood().interactionFlagsOf(this.actionId)) & 1) !== 0 && (actionFlags & 524288) === 0 && this.partnerSim() !== null) {
			this.partnerSim().cancelAction();
		}
		this.addState(2);
		this.abortAction(true);
	}
	abortAction(silent: boolean): void {
		this.addState(4);
		let messageId: number = -1;
		let messageArg: number = 273;
		switch (this.actionPhase) {
			case 0:
			case 2:
			case 3:
			case 4:
			case 10:
			case 26:
			case 27:
			case 29:
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 36:
			case 37:
			case 38:
			case 41:
			case 42:
			case 43:
			case 44:
			case 47:
			case 48:
			case 51:
			default:
				break;
			case 1:
				messageId = 278;
				break;
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 11:
			case 16:
			case 21:
			case 22:
			case 23:
			case 25:
			case 28:
			case 49:
			case 50:
				this.endCurrentPhase(true);
				this.completeAction();
				break;
			case 12:
				messageId = 779;
				messageArg = this.lot().categoryIdOfRef(this.actionObjectId);
				break;
			case 13:
				messageId = 336;
				break;
			case 14:
				messageId = 283;
				break;
			case 15:
				messageId = 281;
				break;
			case 17:
			case 18:
			case 19:
			case 20:
			case 52:
				this.finishPhase();
				break;
			case 24:
				if (this.actionId === 146) {
					messageId = 315;
				}
				else {
					messageId = 301;
				}
				break;
			case 39:
				messageId = 299;
				break;
			case 40:
				messageId = 323;
				break;
			case 45:
				messageId = 285;
				break;
			case 46:
				messageId = 288;
		}
		if (messageId !== -1) {
			if (!silent && this.variant === 0) {
				this.screen.chooseDialogOptionAt(messageId, messageArg);
			}
			this.completeAction();
		}
	}
	chooseAutonomousAction(): void {
		let canvas: GameCanvas = this.canvas;
		let flags: number = this.behaviourFlags;
		if ((this.behaviourFlags & 257) !== 0) {
			let spareEntity: Entity = this.screen.findEntityByType(4);
			this.queueAction(159, spareEntity);
		}
		else {
			if (this.screen.menuTarget !== this && (this.type !== 0 || this.screen.menuTarget === null)) {
				if (this.partnerCooldown !== -1 || (this.behaviourFlags & 32) === 0 || this.variant === 0 && (!canvas.autonomousEnabled || !this.screen.isIdleTooLong()) || this.variant !== 0 && (this.behaviourFlags & 4) !== 0 || !this.tryAutonomousBehaviour()) {
					if ((flags & 94) !== 0) {
						let index: number = canvas.randomRange(0, ((Sim.AUTONOMY_FLAGS.length - 1) | 0));
						if ((flags & 4) !== 0) {
							index = GameCanvas.indexOfInt(4, Sim.AUTONOMY_FLAGS);
						}
						for (let flagIndex: number = 0; flagIndex < Sim.AUTONOMY_FLAGS.length; flagIndex++) {
							let flag: number = Sim.AUTONOMY_FLAGS[index];
							let entity: Entity = null!;
							if ((flags & flag) !== 0 && (entity = this.screen.findFreeEntityForFlag(flag)) !== null) {
								let lot: Lot = this.lot();
								let entityType: number = entity.type;
								let actionId: number = lot.frameRefsAt(entityType)[0];
								this.queueAction(actionId, entity);
								let clearMask: number = lot.linkedIdAt(entityType);
								this.behaviourFlags = (this.behaviourFlags & ~clearMask);
								return;
							}
							index = (((((index + 1) | 0)) % Sim.AUTONOMY_FLAGS.length) | 0);
						}
					}
				}
			}
		}
	}
	tryAutonomousBehaviour(): boolean {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood();
		let targetSim: Sim = null;
		if (this.partnerId !== -1) {
			targetSim = this.screen.findSimByIndex(this.partnerId);
			if (!this.isValidInteractionTarget(targetSim)) {
				targetSim = null;
			}
		}
		let simId: number = this.variant;
		if (neighborhood.simHomeLotOf(simId) !== this.lot().areaId() && !neighborhood.hasSimMilestone(simId, 1) && (simId !== 0 || canvas.autonomousEnabled)) {
			if ((this.behaviourFlags & 512) !== 0) {
				if (simId !== 0) {
					if (this.stateTime > 30000) {
						this.behaviourFlags = (this.behaviourFlags | 256);
					}
				}
				else {
					this.behaviourFlags = (this.behaviourFlags & -513);
				}
				return true;
			}
			let spareEntity: Entity = null!;
			if ((spareEntity = this.screen.findEntityByObjectType(10)) !== null) {
				this.queueAction(152, spareEntity);
				this.behaviourFlags = (this.behaviourFlags | 512);
				return true;
			}
		}
		else {
			this.behaviourFlags = (this.behaviourFlags & -513);
		}
		if (targetSim === null) {
			let sims: Sim[] = null!;
			let count: number = (sims = this.screen.getSims()).length;
			let index: number = canvas.randomRange(0, ((count - 1) | 0));
			for (let simIndex: number = 0; simIndex < count; simIndex++) {
				let otherSim: Sim = sims[index];
				index = (((((index + 1) | 0)) % sims.length) | 0);
				if (this.isValidInteractionTarget(otherSim)) {
					targetSim = otherSim;
					break;
				}
			}
		}
		if (targetSim !== null) {
			let timeOfDay: number = 0;
			let actionId: number = (timeOfDay = canvas.randomPercent()) < 30 ? 63 : timeOfDay < 70 ? 90 : 76;
			this.queueAction(actionId, (targetSim as unknown as Entity));
			return true;
		}
		else {
			return false;
		}
	}
	isValidInteractionTarget(other: Sim): boolean {
		if (other !== this && other !== null) {
			let playerEntity: Entity = this.screen.menuTarget;
			if (other === playerEntity) {
				return false;
			}
			else {
				if (other.variant === 0 && playerEntity !== null) {
					return false;
				}
				else {
					if (other.isSitting()) {
						return false;
					}
					else {
						if (other.partnerId !== -1 && other.partnerId !== this.variant) {
							return false;
						}
						else {
							return other.hasState(8) ? false : !other.hasState(1024) || this.neighborhood().simKnownIndex(this.variant, 12) !== -1;
						}
					}
				}
			}
		}
		else {
			return false;
		}
	}
	clearBehaviourFlags(): void {
		this.behaviourFlags = 0;
	}
	addBehaviourFlags(flags: number): void {
		this.behaviourFlags = (this.behaviourFlags | flags);
	}
	setLocomotionState(state: number): void {
		switch (state) {
			case 4:
			case 5:
			case 7:
				this.addState(4096);
				break;
			case 6:
				this.addState(2048);
				return;
			case 8:
			default:
				break;
			case 9:
				this.addState(1024);
				if (!this.screen.isNewNeighborhood()) {
					this.setValueA(43);
					this.playAnimation(40, 20);
					return;
				}
		}
	}
	clearLocomotionState(state: number): void {
		switch (state) {
			case 4:
			case 5:
			case 7:
				this.clearState(4096);
				break;
			case 6:
				this.clearState(2048);
				return;
			case 8:
			default:
				break;
			case 9:
				this.clearState(1024);
				if (!this.screen.isNewNeighborhood()) {
					this.setValueA(-1);
					this.stopAnimation();
					return;
				}
		}
	}
	refreshLocomotionState(): void {
		if (this.hasState(1024)) {
			this.setLocomotionState(9);
		}
	}
	linkPartners(other: Sim): void {
		this.partnerId = other.variant;
		this.partnerCooldown = -1;
		this.addBehaviourFlags(32);
		other.partnerId = this.variant;
		other.partnerCooldown = -1;
		other.addBehaviourFlags(32);
	}
	unlinkPartner(): void {
		let other: Sim = null!;
		if ((other = this.screen.findSimByIndex(this.partnerId)) !== null) {
			other.behaviourFlags = (other.behaviourFlags & -33);
			other.partnerId = -1;
			other.partnerTimer = 0;
			other.partnerCooldown = 0;
		}
		this.behaviourFlags = (this.behaviourFlags & -33);
		this.partnerId = -1;
		this.partnerTimer = 0;
		this.partnerCooldown = 0;
	}
	isNearWater(): boolean {
		let neighborhood: Neighborhood = null!;
		let areaId: number = 0;
		if ((areaId = (neighborhood = this.neighborhood()).simLot(this.variant)) !== -1) {
			let areaValue: number = neighborhood.simHour(this.variant);
			if ((neighborhood.lotHourDays(areaId, areaValue) & 1) !== 0) {
				return true;
			}
		}
		return this.type === 0 && neighborhood.itemCount(35) !== 0 ? true : neighborhood.simHasFlag(this.variant, 1);
	}
	sitDown(target: Entity): void {
		this.reserveTarget(target);
		let slotOffsets: Int32Array = Entity.boundsScratch;
		target.computeSlotPosition(slotOffsets, this.reservedSlot);
		this.moveTo(slotOffsets[0], slotOffsets[1]);
		this.faceEntity(target);
		this.setFacing((((((this.facing + 2) | 0)) % 4) | 0));
		this.poseIndex = 4;
		this.targetPose = 4;
		this.interactionTarget = target;
		target.setOwner(this);
		if (this.variant === 0) {
			this.screen.placeCameraOnEntity(target);
		}
		this.queueAction(122, target);
	}
	constructor(screen: GameScreen) {
		super(screen);
	}
}
