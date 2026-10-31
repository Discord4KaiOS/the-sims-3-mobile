package com.ea.sims3;

import javax.microedition.lcdui.Graphics;

public final class Sim extends Entity {
   private int spriteVariant;
   private int poseId;
   public int recolorIndex;
   private int motionState;
   private int stateTime;
   private int poseIndex;
   private int targetPose;
   public Entity interactionTarget;
   public int actionId;
   private int actionStep;
   public int actionPhase;
   public Entity actionTarget;
   private int actionObjectId;
   private int actionValue;
   private int queuedActionId;
   private Entity queuedTarget;
   private int queuedObjectId;
   private int queuedValue;
   private int behaviourFlags;
   private int autonomyTimer;
   private Entity reservedTarget;
   private int reservedSlot;
   private int destX;
   private int destY;
   private int[] path;
   private int pathIndex;
   private boolean detourPath;
   private int targetLastX;
   private int targetLastY;
   private int partnerId;
   private int partnerTimer;
   private int partnerCooldown;
   private int poseAnimId;
   private boolean useTimer;
   private boolean waitForTargetTime;
   public int timer;
   private int overlayPoseAnimId;
   private boolean fadeOverlay;
   private int overlaySpriteId;
   private int lastFacing = -551;
   private static final int[] AUTONOMY_FLAGS = new int[]{4, 2, 8, 16, 64};

   public Sim(GameScreen screen) {
      super(screen);
   }

   public final void place(int type, int startX, int startY, int facing, int simId) {
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
      this.destX = super.posX;
      this.destY = super.posY;
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
      Neighborhood neighborhood = this.neighborhood();
      if (super.screen.isNewNeighborhood()) {
         this.recolorIndex = neighborhood.simSpareByte(simId);
         this.setSpriteVariant(2);
      } else {
         if (neighborhood.simAttribute(simId, 0) == 0) {
            this.setSpriteVariant(0);
         } else {
            this.setSpriteVariant(1);
         }

         this.recolorIndex = -1;
      }

      if (simId == 0) {
         for (int stateIndex = 0; stateIndex < 6; stateIndex++) {
            int locomotionState;
            if ((locomotionState = neighborhood.activeTaskAt(stateIndex)) != -1) {
               this.setLocomotionState(locomotionState);
            }
         }
      }

      this.setMotionState(1);
   }

   public final void advanceTime(int delta) {
      if (this.stateTime < 3000000) {
         this.stateTime += delta;
      }

      if (this.partnerId != -1) {
         this.partnerTimer += delta;
         if (this.partnerTimer > 20000) {
            this.unlinkPartner();
         }
      } else if (this.partnerCooldown >= 0) {
         this.partnerCooldown += delta;
         if (this.partnerCooldown > 20000) {
            this.partnerCooldown = -1;
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

   public final void render(Graphics gfx) {
      if (this.motionState != 8 && this.recolorIndex != -1) {
         Lot lot;
         int lotOriginX = (lot = this.lot()).viewCenterX();
         int lotOriginY = lot.viewCenterY();
         int screenX = lotOriginX + lot.toScreenX(super.posX, super.posY);
         int screenY = lotOriginY + lot.toScreenY(super.posX, super.posY);
         Animator bodyAnimator = null;
         if (this.poseId != 50) {
            if (!super.screen.isNewNeighborhood() && !super.screen.isGoalPageActive() && (this.targetPose == 0 || this.targetPose == 6)) {
               com.ea.sims3.SpriteRenderer.drawFrame(gfx, 33, 0, screenX, screenY);
            }

            com.ea.sims3.SpriteRenderer.setRecolorIndex(this.recolorIndex);
            bodyAnimator = this.animatorAt(0);
            if (!super.screen.isNewNeighborhood()) {
               int animId = bodyAnimator.animId;
               int frame = bodyAnimator.frame;
               this.drawSprite(gfx, animId, frame, screenX, screenY);
            } else {
               bodyAnimator.draw(gfx, screenX, screenY);
               if (super.variant == 0) {
                  super.screen.drawCursorAnimator(gfx, screenX, screenY - 16);
               }
            }

            if (this.partnerSim() != null && this.partnerSim().interactionTarget == null) {
               this.partnerSim().drawBody(gfx);
            }

            com.ea.sims3.SpriteRenderer.setRecolorIndex(0);
         }

         Animator overlayAnimator = this.animatorAt(2);
         if (this.timer > 0 && overlayAnimator.isRunning()) {
            int[] frameOffsets = com.ea.sims3.Entity.boundsScratch;
            int bubbleOffset = (this.fadeOverlay ? com.ea.sims3.FixedMath.smoothStep(1000, 2000, this.timer) : 65536) * 10;
            int bubbleY = screenY + (bubbleOffset >> 16) - 60 - 10;
            com.ea.sims3.SpriteRenderer.drawAnimator(gfx, overlayAnimator, screenX, bubbleY);
            if (this.actionPhase == 5) {
               overlayAnimator.matchesFrame(frameOffsets, 0);
               com.ea.sims3.SpriteRenderer.drawFrame(gfx, this.overlaySpriteId, 0, screenX + frameOffsets[0], bubbleY + frameOffsets[1]);
            }
         }
      }
   }

   public final void drawBody(Graphics gfx) {
      Lot lot;
      int spriteId;
      int animId;
      if ((spriteId = (lot = this.lot()).framePartAt(this.spriteVariant, this.poseId, true)) != 0 && (animId = lot.frameDataAt(spriteId, super.facing)) != 196) {
         int lotOriginX = lot.viewCenterX();
         int lotOriginY = lot.viewCenterY();
         int screenX;
         int screenY;
         if (this.poseId == 50) {
            int[] offsets = com.ea.sims3.Entity.boundsScratch;
            this.actionTarget.computeFootprintOffset(offsets);
            screenX = lotOriginX + offsets[0];
            screenY = lotOriginY + offsets[1];
         } else {
            screenX = lotOriginX + lot.toScreenX(super.posX, super.posY);
            screenY = lotOriginY + lot.toScreenY(super.posX, super.posY);
         }

         int frame = this.animatorAt(0).frame;
         com.ea.sims3.SpriteRenderer.setRecolorIndex(this.recolorIndex);
         this.drawSprite(gfx, animId, frame, screenX, screenY);
         com.ea.sims3.SpriteRenderer.setRecolorIndex(0);
      }
   }

   private void drawSprite(Graphics gfx, int animId, int frame, int screenX, int screenY) {
      int bodyBase = this.spriteVariant == 0 ? 489 : 382;
      int skinTone = this.neighborhood().simAttribute(super.variant, 2) + 1;
      int[] frameOffsets = null;
      boolean isPlayer;
      if (isPlayer = super.variant == 0) {
         frameOffsets = com.ea.sims3.Entity.boundsScratch;
      }

      boolean drawn = com.ea.sims3.SpriteRenderer.drawParts(gfx, animId, frame, screenX, screenY, bodyBase, skinTone, frameOffsets);
      if (isPlayer && drawn) {
         super.screen.drawCursorAnimator(gfx, frameOffsets[0], frameOffsets[1]);
      }
   }

   public final int interactionAnimId() {
      return this.neighborhood().simNameText(super.variant);
   }

   private final void setSpriteVariant(int variant) {
      if (this.spriteVariant != variant) {
         this.spriteVariant = variant;
         int previousPose = this.poseId;
         this.poseId = -1;
         this.setPose(previousPose);
      }
   }

   public final void setPose(int animId) {
      boolean horizontal = super.facing == 0 || super.facing == 1;
      animId = this.lot().resolveObjectId(animId, horizontal);
      if (this.poseId != animId) {
         this.poseId = animId;
         int spriteId = this.lot().framePartAt(this.spriteVariant, animId, false);
         this.setValueB(spriteId);
      }
   }

   private final void setMotionState(int newState) {
      if (newState == 1 && this.motionState == 4 && this.poseIndex == 2 && super.variant == 0 && this.lastFacing != -551) {
         this.setFacing(this.lastFacing);
      }

      GameScreen screen = super.screen;
      switch (this.motionState) {
         case 2:
            if (this.interactionTarget != null && this.poseIndex == 0) {
               this.interactionTarget.clearOwner(this);
               this.interactionTarget = null;
            }
            break;
         case 3:
            this.lastFacing = -551;
            if (newState != 3 && screen.isNewNeighborhood()) {
               this.setSpriteVariant(2);
               if (screen.cameraTarget == this) {
                  screen.setCameraTarget((Entity) null);
               }
            }
      }

      this.addState(128);
      int oldState = this.motionState;
      this.motionState = newState;
      this.stateTime = 0;
      switch (newState) {
         case 1:
            int defaultPose = this.poseIndex == 4
               ? 15
               : (this.poseIndex == 5 ? 54 : (this.poseIndex == 1 ? 19 : (this.poseIndex == 2 ? 19 : (this.poseIndex == 3 ? 28 : (this.poseIndex == 6 ? 43 : (this.poseIndex == 7 ? 36 : 0))))));
            this.setPose(defaultPose);
            break;
         case 2:
            byte pose = -1;
            int newPoseIndex = this.poseIndex;
            Entity target = this.actionTarget;
            switch (this.poseIndex) {
               case 0:
               default:
                  if (this.targetPose == 7) {
                     pose = 34;
                     newPoseIndex = 7;
                     this.interactionTarget = target;
                     target.playModeOneAnimation();
                  } else if (this.targetPose == 3) {
                     pose = 27;
                     newPoseIndex = 3;
                  } else {
                     if (this.targetPose != 1 && this.targetPose != 4 && this.targetPose != 5) {
                        if (this.targetPose == 6) {
                           pose = 44;
                           newPoseIndex = 6;
                        } else {
                           target.setOwner(this);
                           this.interactionTarget = target;
                           int offsetX = 0;
                           int offsetY = 0;
                           switch (super.facing) {
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

                           int targetFacing = target.facing;
                           this.setFacing(targetFacing);
                           int targetShiftX = 0;
                           int targetShiftY = 0;
                           switch (targetFacing) {
                              case 0:
                              default:
                                 targetShiftX = -196608;
                                 offsetX += 131072;
                                 break;
                              case 1:
                                 targetShiftY = -196608;
                                 offsetY += 131072;
                                 break;
                              case 2:
                                 targetShiftX = 196608;
                                 offsetX -= 131072;
                                 break;
                              case 3:
                                 targetShiftY = 196608;
                                 offsetY -= 131072;
                           }

                           if (target.isPassable()) {
                              target.moveTo(target.posX + targetShiftX, target.posY + targetShiftY);
                              target.addState(131072);
                           } else {
                              offsetX -= targetShiftX;
                              offsetY -= targetShiftY;
                           }

                           this.moveTo(super.posX + offsetX, super.posY + offsetY);
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
                  break;
               case 1:
                  if (this.targetPose == 0) {
                     pose = 18;
                     newPoseIndex = 0;
                  } else if (this.targetPose == 4) {
                     pose = 17;
                     newPoseIndex = 4;
                  } else if (this.targetPose == 5) {
                     pose = 53;
                     newPoseIndex = 5;
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
            if (super.variant == 0 && screen.isNewNeighborhood()) {
               if (!super.reservedFlag && oldState != 3) {
                  screen.setCameraTarget(this);
               }

               screen.startMusic();
            }
            break;
         case 4:
            Neighborhood neighborhood = this.neighborhood();
            if (this.overlayPoseAnimId != -1) {
               this.animatorAt(2).play(this.overlayPoseAnimId, 20);
               if (this.actionPhase == 5) {
                  this.overlaySpriteId = neighborhood.commentText(this.actionId);
               }
            } else {
               this.animatorAt(2).setRunning(false);
            }

            if (this.poseAnimId == -1) {
               this.poseAnimId = neighborhood.interactionAltText(this.actionId);
            }

            Sim partner = this.partnerSim();
            if (this.actionId != 82 && this.actionId != 83) {
               if (this.actionId == 93) {
                  partner.clearState(128);
                  partner.setPose(32);
               } else if (this.actionId == 84) {
                  int partnerId = partner.variant;
                  if (neighborhood.canUseInteraction(super.variant, partnerId, this.actionId)) {
                     screen.openDialogPage(3, -1, partnerId);
                  }
               }
            } else if (neighborhood.canUseInteraction(super.variant, partner.variant, this.actionId)) {
               partner.clearState(128);
               partner.setPose(38);
               this.poseAnimId = 41;
            } else {
               partner.clearState(128);
               partner.setPose(40);
               this.poseAnimId = 39;
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
            byte poseAfterAction = 7;
            if (this.partnerSim() != null && (this.partnerSim().actionId == 82 || this.partnerSim().actionId == 83)) {
               poseAfterAction = 0;
            }

            this.setPose(poseAfterAction);
         case 8:
         default:
            break;
         case 9:
            this.despawn();
      }

      if (this.motionState == 1 && this.poseIndex == 2 && super.variant == 0) {
         this.lastFacing = super.facing;
      }
   }

   private final void startTimedAction(int idleAnim, int duration, int overlayAnim, boolean fadeOverlay, boolean waitForTargetTime) {
      this.poseAnimId = idleAnim;
      this.useTimer = duration >= 0;
      this.timer = duration;
      this.overlayPoseAnimId = overlayAnim;
      this.fadeOverlay = fadeOverlay;
      this.waitForTargetTime = waitForTargetTime;
      this.setMotionState(4);
   }

   private final void clearTimedAction() {
      this.poseAnimId = -1;
      this.useTimer = false;
      this.timer = -1;
      this.overlayPoseAnimId = -1;
      this.fadeOverlay = false;
      this.waitForTargetTime = true;
   }

   private final void waitForTimer(int duration, boolean waitForTargetTime) {
      this.timer = duration;
      this.waitForTargetTime = waitForTargetTime;
      this.setMotionState(8);
   }

   private final void updateIdle(int delta) {
      this.advanceAnimations(delta);
      this.autonomyTimer += delta;
      if (super.type == 0) {
         if (this.stateTime > 3000 && this.neighborhood().activeTaskSlotOf(10) != -1) {
            this.queueAction(3, (Entity) null);
            return;
         }

         if (this.stateTime > 3500 && this.poseIndex == 0) {
            int balance;
            if ((balance = this.neighborhood().moodValue) > 4915200) {
               this.queueAction(5, (Entity) null);
               return;
            }

            if (balance < -3276800) {
               this.queueAction(6, (Entity) null);
               return;
            }
         }
      }

      Entity nearbyEntity;
      if (this.poseIndex == 1 && this.interactionTarget.behaviorClass() == 9 && (nearbyEntity = super.screen.findEntityByObjectType(22)) != null && nearbyEntity.hasState(64) && this.stateTime > 1000) {
         if (super.variant == 0) {
            this.neighborhood().unlockAchievement(36);
         }

         byte interactionId = 16;
         if (nearbyEntity.hasState(32768)) {
            interactionId = 15;
         }

         this.queueAction(interactionId, (Entity) null);
      } else {
         if (this.poseIndex == 0 && this.stateTime > 5000 && (nearbyEntity = super.screen.findEntityByObjectType(18)) != null && nearbyEntity.hasState(64)) {
            Object ignored = null;
            int targetTileX = com.ea.sims3.Lot.toTileX(nearbyEntity.posX);
            int targetTileY = com.ea.sims3.Lot.toTileY(nearbyEntity.posY);
            int selfTileX = com.ea.sims3.Lot.toTileX(super.posX);
            int selfTileY = com.ea.sims3.Lot.toTileY(super.posY);
            if (Math.abs(selfTileX - targetTileX) + Math.abs(selfTileY - targetTileY) < 8) {
               this.queueAction(17, (Entity) null);
               return;
            }
         }

         if (this.autonomyTimer > 2500 && this.canOccupySlot()) {
            this.autonomyTimer = 0;
            this.chooseAutonomousAction();
         }
      }
   }

   private final void updateInteraction(int delta) {
      this.advanceAnimations(delta);
      if (!this.isAnimating()) {
         if (this.poseIndex == 0 && this.interactionTarget != null && this.interactionTarget.behaviorClass() == 6) {
            int standOffset = 131072;
            byte stepX = 0;
            byte stepY = 0;
            switch (super.facing) {
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

            Entity target = this.interactionTarget;
            if (this.interactionTarget.hasState(131072)) {
               target.clearState(131072);
               target.moveTo(target.posX + stepX * 196608, target.posY + stepY * 196608);
            } else {
               standOffset += 196608;
            }

            Lot lot = this.lot();
            int standX = super.posX + stepY * 524288 + stepX * -standOffset;
            int standY = super.posY + stepX * 524288 + stepY * -standOffset;
            boolean standOk = lot.isWalkableAt(standX, standY);
            int altX = super.posX + stepY * -524288 + stepX * -standOffset;
            int altY = super.posY + stepX * -524288 + stepY * -standOffset;
            boolean altOk = lot.isWalkableAt(altX, altY);
            boolean altFree = super.screen.isEntityAtTile(0, altX, altY);
            if (!standOk || !altFree && altOk) {
               this.moveTo(altX, altY);
            } else {
               this.moveTo(standX, standY);
            }
         }

         if (this.poseIndex == this.targetPose) {
            this.finishPhase();
            return;
         }

         this.setMotionState(2);
      }
   }

   private final void updateWalking(int delta) {
      Lot lot = this.lot();
      int posX = super.posX;
      int posY = super.posY;
      int destX = this.destX;
      int destY = this.destY;
      int tileX = com.ea.sims3.Lot.toTileX(posX);
      int tileY = com.ea.sims3.Lot.toTileY(posY);
      int tileFlags = lot.cellFlagsAt(tileX, tileY);
      int dirX;
      int dirY = (dirX = com.ea.sims3.FixedMath.sign(destX - posX)) != 0 ? 0 : com.ea.sims3.FixedMath.sign(destY - posY);
      short speedX = 2048;
      short speedY = 1024;
      if (this.hasState(2048)) {
         speedX = 2621;
         speedY = 1310;
      } else if (this.hasState(4096)) {
         speedX = 1638;
         speedY = 786;
      }

      if (super.screen.isNewNeighborhood()) {
         speedX = speedY;
         if (this.isNearWater()) {
            int selfTileX = com.ea.sims3.Lot.toTileX(posX);
            int selfTileY = com.ea.sims3.Lot.toTileY(posY);
            boolean inWater;
            if (inWater = (lot.cellFlagsAt(selfTileX, selfTileY) & 32) != 0) {
               int neighborFlags;
               if (dirY == 0) {
                  int tileCentreX = com.ea.sims3.Lot.toFixedX(selfTileX);
                  if ((dirX >= 0 || posX > tileCentreX) && (dirX <= 0 || posX < tileCentreX)) {
                     neighborFlags = lot.cellFlagsAt(selfTileX - dirX, selfTileY);
                  } else {
                     neighborFlags = lot.cellFlagsAt(selfTileX + dirX, selfTileY);
                  }
               } else {
                  int tileCentreY = com.ea.sims3.Lot.toFixedY(selfTileY);
                  if ((dirY >= 0 || posY > tileCentreY) && (dirY <= 0 || posY < tileCentreY)) {
                     neighborFlags = lot.cellFlagsAt(selfTileX, selfTileY - dirY);
                  } else {
                     neighborFlags = lot.cellFlagsAt(selfTileX, selfTileY + dirY);
                  }
               }

               inWater = (neighborFlags & 32) != 0;
            }

            if (inWater) {
               speedX = 2048;
               this.setSpriteVariant(3);
            } else {
               this.setSpriteVariant(2);
            }
         }
      }

      int step = speedX * delta;
      posX += dirX * step;
      posY += dirY * step;
      if (dirX > 0 && posX > destX || dirX < 0 && posX < destX) {
         posX = destX;
      }

      if (dirY > 0 && posY > destY || dirY < 0 && posY < destY) {
         posY = destY;
      }

      this.faceDirection(dirX, dirY);
      this.moveTo(posX, posY);
      if (!super.screen.isNewNeighborhood()) {
         int newTileX = com.ea.sims3.Lot.toTileX(posX);
         int newTileY = com.ea.sims3.Lot.toTileY(posY);
         int newTileFlags = lot.cellFlagsAt(newTileX, newTileY);
         Entity blockingEntity;
         if ((tileFlags & 32) == 0 && (newTileFlags & 32) != 0 && (blockingEntity = super.screen.findEntityByObjectType(10)) != null && !blockingEntity.hasState(64)) {
            blockingEntity.handleAction(150, this);
         }
      }

      this.advanceAnimations(delta);
      if (posX == destX && posY == destY) {
         if (!this.canOccupySlot() && super.type != 0) {
            this.abortAction(true);
            return;
         }

         if (this.actionTarget != null && (this.actionTarget.posX != this.targetLastX || this.actionTarget.posY != this.targetLastY)) {
            this.walkTo(this.actionTarget, 0, 0);
            return;
         }

         boolean cannotReserve = !this.reserveTarget(this.actionTarget);
         boolean needsDetour = this.detourPath || cannotReserve;
         int[] pathPoints = this.path;
         int remainingPoints;
         if ((remainingPoints = this.path != null ? (pathPoints.length >> 1) - this.pathIndex : -1) > (needsDetour ? 2 : 1)) {
            if (this.actionTarget != null && ((remainingPoints & 3) == 0 || remainingPoints < 4)) {
               if (!cannotReserve) {
                  this.reserveTarget(null);
                  this.reserveTarget(this.actionTarget);
               }

               this.walkTo(this.actionTarget, 0, 0);
               return;
            }

            this.pathIndex++;
            this.destX = pathPoints[(this.pathIndex << 1) + 0];
            this.destY = pathPoints[(this.pathIndex << 1) + 1];
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

   private final void walkTo(Entity target, int toX, int toY) {
      Lot lot = this.lot();
      boolean cannotReserve = false;
      int targetX = toX;
      int targetY = toY;
      if (target != null) {
         cannotReserve = !this.reserveTarget(target);
         int slot = this.reservedSlot;
         if (this.reservedSlot == -1) {
            slot = target.findNearestSlot(super.posX, super.posY, false, null);
         }

         int[] slotOffsets = com.ea.sims3.Entity.boundsScratch;
         target.computeSlotPosition(slotOffsets, slot);
         targetX = slotOffsets[0];
         targetY = slotOffsets[1];
         this.targetLastX = target.posX;
         this.targetLastY = target.posY;
      }

      targetX = com.ea.sims3.Lot.toFixedX(com.ea.sims3.Lot.toTileX(targetX));
      targetY = com.ea.sims3.Lot.toFixedY(com.ea.sims3.Lot.toTileY(targetY));
      if ((targetX != super.posX || targetY != super.posY) && this.poseIndex != 0) {
         this.actionStep--;
         this.runActionPhase(30);
      } else {
         if (super.screen.isNoSaveMode() && lot.areaId() != 0 && super.variant == 0 && this.actionId != 153 && !this.hasState(32) && !this.neighborhood().hasSimMilestone(0, 1)) {
            int selfTileX = com.ea.sims3.Lot.toTileX(super.posX);
            int selfTileY = com.ea.sims3.Lot.toTileY(super.posY);
            int targetTileX = com.ea.sims3.Lot.toTileX(targetX);
            int targetTileY = com.ea.sims3.Lot.toTileY(targetY);
            if ((lot.cellFlagsAt(selfTileX, selfTileY) & 16) == 0 && (lot.cellFlagsAt(targetTileX, targetTileY) & 16) != 0) {
               this.queuedActionId = this.actionId;
               this.queuedTarget = this.actionTarget;
               this.queuedObjectId = this.actionObjectId;
               this.queuedValue = this.actionValue;
               this.addState(16);
               this.queueAction(153, super.screen.findEntityByObjectType(10));
               return;
            }
         }

         this.clearState(32);
         byte pathFlags;
         if (super.screen.isNewNeighborhood()) {
            if (this.isNearWater()) {
               pathFlags = 2;
            } else {
               pathFlags = 4;
            }
         } else {
            pathFlags = 8;
            Sim[] sims;
            if ((sims = super.screen.getSims()).length > 1) {
               lot.clearWaypoints();

               for (int simIndex = 0; simIndex < sims.length; simIndex++) {
                  Sim otherSim;
                  if ((otherSim = sims[simIndex]) != this) {
                     int otherTileX = com.ea.sims3.Lot.toTileX(otherSim.posX);
                     int otherTileY = com.ea.sims3.Lot.toTileY(otherSim.posY);
                     lot.addWaypoint(otherTileX, otherTileY);
                  }
               }

               pathFlags = 9;
            }
         }

         int startX = super.posX;
         int startY = super.posY;
         int[] pathPoints = lot.findPath(startX, startY, targetX, targetY, pathFlags);
         if (cannotReserve && pathPoints.length == 2) {
            this.runActionPhase(9);
         } else if (pathPoints.length >= 2) {
            this.path = pathPoints;
            this.pathIndex = 0;
            this.detourPath = false;
            this.destX = pathPoints[(this.pathIndex << 1) + 0];
            this.destY = pathPoints[(this.pathIndex << 1) + 1];
            this.setMotionState(3);
         } else {
            int diffX = startX - targetX;
            int diffY = startY - targetY;
            int distance;
            if ((distance = Math.abs(diffX) + Math.abs(diffY)) <= 524288) {
               if (distance != 0) {
                  this.path = pathPoints;
                  this.pathIndex = 0;
                  this.detourPath = false;
                  this.destX = targetX;
                  this.destY = targetY;
                  this.setMotionState(3);
               } else {
                  this.finishPhase();
               }
            } else {
               this.abortAction(false);
            }
         }
      }
   }

   private final void updateTimedAction(int delta) {
      int previousFrame = this.animatorAt(0).frame;
      this.advanceAnimations(delta);
      int frame = this.animatorAt(0).frame;
      Animator animator = this.animatorAt(0);
      if (previousFrame != frame) {
         if (this.actionId == 147 && frame == 2) {
            this.actionTarget.interactWith(this);
         }

         if (this.actionId == 93 && frame == 2 && (super.variant == 0 || this.partnerSim() != null && this.partnerSim().variant == 0)) {
            super.canvas.vibrateShort();
         }

         int turnFrame = super.facing != 0 && super.facing != 1 ? 3 : 2;
         if (this.actionId == 83 && this.poseId == 41 && frame == turnFrame) {
            super.screen.openDialogPage(2, 789, this.actionTarget.variant);
         }

         if (this.actionId == 4 && frame == 15 && super.canvas.randomPercent() < 5) {
            super.screen.openStatsDialog(803);
         }
      }

      this.animatorAt(2).advance(delta);
      boolean finished;
      if (this.useTimer) {
         if (this.waitForTargetTime) {
            finished = this.timer <= this.neighborhood().gameTime;
         } else {
            if (!com.ea.sims3.GameScreen.isDialogOpen()) {
               this.timer -= delta;
            }

            finished = this.timer <= 0;
         }
      } else {
         finished = !animator.isRunning();
      }

      if (this.actionPhase == 49 && this.actionTarget.isModeTwo()) {
         this.finishPhase();
      }

      if (finished) {
         this.poseAnimId = -1;
         this.timer = -1;
         this.useTimer = true;
         this.finishPhase();
      } else {
         if (super.variant == 0 && this.actionPhase == 20 && this.stateTime > 4000 && this.waitForTargetTime && this.timer - this.neighborhood().gameTime > 60 && this.canOccupySlot() && !super.screen.hasFade()) {
            super.screen.syncFadeWithAction();
         }

         if (this.actionPhase == 17 && this.actionTarget.behaviorClass() == 3 && this.poseId != 50 && this.actionTarget.isModeOne()) {
            if (super.variant == 0) {
               this.neighborhood().setNeedModifier(3, 655360);
            }

            this.setPose(50);
         }
      }
   }

   private final void updateSocial(int delta) {
      this.advanceAnimations(delta);
      Sim partner = this.partnerSim();
      if (this.actionId != 94) {
         int diffX = partner.posX - super.posX;
         int diffY = partner.posY - super.posY;
         if (Math.abs(diffX) + Math.abs(diffY) > 524288) {
            this.abortAction(false);
            return;
         }
      }

      if (this.stateTime > 4000) {
         this.abortAction(false);
      } else {
         if (partner.isIdleModeZero()) {
            partner.queueAction(2, (Entity) this);
            this.finishPhase();
         }
      }
   }

   private final void updateApproach(int delta) {
      this.advanceAnimations(delta);
      if (this.stateTime > 4000) {
         this.abortAction(false);
      } else {
         if (this.reserveTarget(this.actionTarget)) {
            this.runActionPhase(1);
         }
      }
   }

   private final void updateDiningPartner(int delta) {
      this.advanceAnimations(delta);
      Sim partner;
      if ((partner = this.partnerSim()) != null && !partner.isIdleModeZero()) {
         if (partner != null && partner.hasState(1024)) {
            Neighborhood neighborhood;
            if ((neighborhood = this.neighborhood()).simKnownIndex(super.variant, 12) != -1) {
               if (!this.hasState(262144)) {
                  neighborhood.simRemember(super.variant, 12);
                  this.addState(262144);
                  return;
               }
            } else {
               partner.cancelAction();
               this.unlinkPartner();
               byte actionId = 0;
               if (neighborhood.simKnownIndex(super.variant, 13) != -1) {
                  actionId = 34;
                  neighborhood.simRemember(super.variant, 13);
                  neighborhood.moveSim(super.variant, -1, -1);
               }

               this.startAction(14, partner, actionId, 0);
            }
         }
      } else {
         this.finishPhase();
      }
   }

   private final void updateWaiting(int delta) {
      if (this.actionId != 159) {
         if (this.waitForTargetTime) {
            if (this.timer <= this.neighborhood().gameTime) {
               this.finishPhase();
               return;
            }
         } else if (this.timer > 0) {
            this.timer -= delta;
            if (this.timer <= 0) {
               this.finishPhase();
            }
         }
      }
   }

   public final Sim partnerSim() {
      return this.actionTarget instanceof Sim ? (Sim)this.actionTarget : null;
   }

   public final boolean isIdleModeZero() {
      return (this.neighborhood().interactionFlagsOf(this.actionId) & 2) != 0;
   }

   public final boolean isFree() {
      return this.isIdleModeZero() || this.actionPhase == 8 || this.actionPhase == 9 || this.actionPhase == 1;
   }

   public final boolean isLeavingLot() {
      return this.actionPhase == 26;
   }

   public final boolean isSitting() {
      return (this.actionId == 122 || this.actionId == 121) && this.poseIndex == 4;
   }

   public final void queueAction(int actionId, Entity target, int objectId, int value) {
      if (this.isFree()) {
         this.startAction(actionId, target, objectId, value);
      } else {
         this.queuedActionId = actionId;
         this.queuedTarget = target;
         this.queuedObjectId = objectId;
         this.queuedValue = value;
      }
   }

   public final void queueAction(int actionId, Entity target) {
      this.startAction(actionId, target, 0, 0);
   }

   public final void startAction(int actionId, Entity target, int objectId, int value) {
      Neighborhood neighborhood = this.neighborhood();
      if (this.actionPhase != 0) {
         this.endCurrentPhase(true);
      }

      this.clearState(262146);
      if (actionId == 119 && this.spriteVariant == 0 && super.canvas.randomPercent() < 50) {
         actionId = 120;
      }

      this.actionId = actionId;
      this.actionTarget = target;
      this.actionObjectId = objectId;
      this.actionValue = value;
      if (target != null || actionId == 106) {
         this.reserveTarget(target);
      }

      Sim partner;
      if ((partner = this.partnerSim()) != null && (neighborhood.interactionFlagsOf(actionId) & 1) != 0) {
         this.linkPartners(partner);
      }

      int phase = neighborhood.interactionVariant(actionId, 0);
      this.actionStep = 0;
      this.runActionPhase(phase);
   }

   public final void onBumped(Entity other) {
      if (other != this && (this.behaviourFlags & 32) != 0) {
         Sim playerPartner = super.screen.activePlayer.partnerSim();
         if (this.partnerId == 0 || playerPartner != null && this.partnerId == playerPartner.variant) {
            this.unlinkPartner();
         }
      }
   }

   public final boolean reserveTarget(Entity target) {
      if (target == this.reservedTarget) {
         return true;
      } else {
         if (this.reservedTarget != null) {
            this.reservedTarget.releaseSlot(this.reservedSlot);
            this.reservedTarget = null;
            this.reservedSlot = -1;
         }

         if (target == null) {
            return true;
         } else {
            if (target.hasFreeSlot()) {
               int slot = 0;
               if (target.behaviorClass() == 10) {
                  Lot lot = this.lot();
                  int tileX = com.ea.sims3.Lot.toTileX(super.posX);
                  int tileY = com.ea.sims3.Lot.toTileY(super.posY);
                  if ((lot.cellFlagsAt(tileX, tileY) & 16) != 0) {
                     slot = 0;
                  } else {
                     slot = 1;
                  }
               } else {
                  slot = target.findNearestSlot(super.posX, super.posY, true, this);
               }

               if (slot != -1 && target.tryOccupySlot(slot)) {
                  this.reservedTarget = target;
                  this.reservedSlot = slot;
                  return true;
               }
            }

            return false;
         }
      }
   }

   private final void completeAction() {
      boolean alreadyCompleted = this.hasState(4);
      Neighborhood neighborhood = this.neighborhood();
      this.clearState(12);
      int actionId = this.actionId;
      Entity target = this.actionTarget;
      Sim partner = this.partnerSim();
      boolean isPlayer = super.variant == 0;
      this.queueAction(0, (Entity) null);
      int actionFlags = neighborhood.interactionFlagsOf(actionId);
      if (!alreadyCompleted && partner != null && (actionFlags & 1) != 0) {
         this.reserveTarget(null);
         partner.reserveTarget(null);
         if (partner.handleAction(actionId, this)) {
            this.unlinkPartner();
         }

         if (isPlayer) {
            int partnerId = partner.variant;
            if (actionId == 93) {
               neighborhood.unlockAchievement(19);
               neighborhood.addSimMilestoneCount(partnerId, 32);
               neighborhood.addSimMilestoneCount(partnerId, 2048);
            } else if (actionId == 105) {
               neighborhood.addSimMilestoneCount(partnerId, 256);
            }
         }
      } else if (!alreadyCompleted) {
         switch (actionId) {
            case 4:
               if (isPlayer) {
                  neighborhood.adjustNeed(3, -6553600);
               }

               if (!super.screen.isEntityAtTile(1, super.posX, super.posY)) {
                  int slot = -1;
                  if (this.lot().areaId() == 0) {
                     slot = this.lot().addObject(9, super.posX, super.posY, super.facing);
                  }

                  super.screen.createEntity(9, super.posX, super.posY, super.facing, slot);
               }
               break;
            case 107:
               super.screen.startTutorialGame();
               break;
            case 111:
            case 113:
               super.screen.raiseSkillLevel(4);
               break;
            case 121:
            case 122:
               this.behaviourFlags &= -5;
               break;
            case 143:
               super.screen.confirmRequest();
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
                  super.screen.showSimProfile(super.variant);
               }
               break;
            case 155:
            case 156:
            case 160:
               super.screen.selectEntity(target);
               break;
            case 158:
               neighborhood.unlockAchievement(21);
               neighborhood.adjustNeed(0, 2621440);
         }
      }

      if (actionId == 153) {
         this.clearState(16);
         if (alreadyCompleted) {
            this.queuedActionId = -1;
            this.queuedTarget = null;
         } else {
            this.addState(32);
         }
      }

      if (target != null && partner == null && !alreadyCompleted) {
         target.handleAction(actionId, this);
         if (isPlayer) {
            neighborhood.markItemVisited(target.type);
         }
      }

      if (this.queuedActionId != -1 && this.isIdleModeZero()) {
         this.startAction(this.queuedActionId, this.queuedTarget, this.queuedObjectId, this.queuedValue);
         this.queuedActionId = -1;
         this.queuedTarget = null;
      }
   }

   public final boolean handleAction(int actionId, Sim otherSim) {
      Neighborhood neighborhood = this.neighborhood();
      int otherSimId = otherSim.variant;
      int selfId = super.variant;
      int[] result = com.ea.sims3.Entity.boundsScratch;
      neighborhood.resolveInteraction(result, otherSimId, selfId, actionId);
      int replyFlags = result[0];
      int replyActionId = result[1];
      int soundId = result[2];
      if ((replyFlags & 64) != 0) {
         super.screen.notifySimsOfAction(25, otherSim, this);
      }

      if (actionId == 103 || actionId == 98) {
         this.queueAction(0, (Entity) null);
         this.addBehaviourFlags(256);
      } else if (replyActionId != -1) {
         if ((neighborhood.interactionFlagsOf(replyActionId) & 1) == 0 && replyActionId != 106) {
            otherSim = null;
         }

         if (otherSimId == 0 && replyActionId == 13) {
            neighborhood.unlockAchievement(15);
            neighborhood.addSimMilestoneCount(selfId, 8192);
         }

         this.startAction(replyActionId, otherSim, replyFlags, 0);
         if (soundId != -1) {
            super.screen.createRoomObject(super.posX, super.posY, soundId, 0);
         }
      }

      return (replyFlags & 32) != 0;
   }

   public final boolean tryInteraction(int actionId, Sim otherSim, Entity object) {
      Neighborhood neighborhood = this.neighborhood();
      if (this.isSitting()) {
         return false;
      } else if (actionId == 25 && neighborhood.simPortrait(super.variant) != object.variant) {
         return false;
      } else {
         int[] result = com.ea.sims3.Entity.boundsScratch;
         neighborhood.resolveInteraction(result, otherSim.variant, super.variant, actionId);
         int replyActionId;
         if ((replyActionId = result[1]) != -1) {
            int replyFlags;
            Object target = ((replyFlags = result[0]) & 16384) != 0 ? object : otherSim;
            this.cancelAction();
            this.queueAction(replyActionId, (Entity)target, 0, 0);
            if (actionId == 25 && otherSim.variant == 0) {
               neighborhood.unlockAchievement(51);
            }

            this.addState(8);
            return true;
         } else {
            return false;
         }
      }
   }

   private final void runActionPhase(int phase) {
      GameScreen screen = super.screen;
      Neighborhood neighborhood = this.neighborhood();
      this.actionPhase = phase;
      int actionId = this.actionId;
      Entity target = this.actionTarget;
      Sim partner = this.partnerSim();
      int objectId = this.actionObjectId;
      int value = this.actionValue;
      boolean isPlayer;
      if ((isPlayer = super.variant == 0) && partner != null) {
         neighborhood.watchSim(partner.variant);
      }

      boolean handled = false;
      switch (phase) {
         case 0:
            if (this.poseIndex != 7 && this.poseIndex != 6) {
               this.setMotionState(1);
            } else {
               this.actionStep--;
               this.runActionPhase(30);
            }
            break;
         case 1:
            this.walkTo(target, objectId, value);
            break;
         case 2:
         case 3:
            if (target != null) {
               this.faceEntity(target);
            } else {
               this.faceDirection(objectId - super.posX, value - super.posY);
            }

            if (phase == 3) {
               this.setFacing(super.facing + 2 & 3);
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
            } else {
               this.abortAction(false);
            }
            break;
         case 13:
            if (neighborhood.hasItemC()) {
               screen.openMiniGame(1, target);
               if (target.type == 5) {
                  this.setFacing(0);
               } else {
                  this.setFacing(1);
               }

               this.setValueC(20);
               this.startTimedAction(24, 1000, -1, false, false);
            } else {
               this.abortAction(false);
            }
            break;
         case 14:
            if (neighborhood.hasItemB()) {
               screen.openMiniGame(2, target);
               this.startTimedAction(4, 1000, -1, false, false);
            } else {
               this.abortAction(false);
            }
            break;
         case 15:
            if (neighborhood.hasItemA()) {
               screen.openMiniGame(3, target);
               this.startTimedAction(4, 1000, -1, false, false);
            } else {
               this.abortAction(false);
            }
            break;
         case 16:
            short duration = 5000;
            if (isPlayer) {
               int amount = 458752;
               if (actionId == 126) {
                  amount = this.lot().volumeOf(objectId);
               }

               neighborhood.setNeedModifier(0, amount);
            }

            if (target.behaviorClass() == 13) {
               target.playFirstModeAnimation();
               duration = 3500;
            }

            byte idleAnimForPose = 4;
            if (this.poseIndex == 2 || this.poseIndex == 1) {
               idleAnimForPose = 60;
            }

            this.startTimedAction(idleAnimForPose, duration, -1, false, false);
            handled = true;
            break;
         case 17:
            int targetType;
            if ((targetType = target.behaviorClass()) == 17) {
               if (isPlayer) {
                  neighborhood.setNeedModifier(3, 655360);
               }

               target.activateModeOne();
               this.waitForTimer(5000, false);
               handled = true;
            } else if (targetType == 3) {
               target.enterModeTwoIfIdle();
               target.setOwner(this);
               this.startTimedAction(4, 8000, -1, false, false);
               handled = true;
            } else if (targetType == 2) {
               if (isPlayer && !this.hasState(1024)) {
                  neighborhood.setNeedModifier(3, 655360);
               }

               target.enterModeOne();
               this.startTimedAction(4, 1500, -1, false, false);
            }
            break;
         case 18:
            if (isPlayer) {
               neighborhood.setNeedModifier(2, 3276800);
            }

            int idleAnim = actionId == 119 ? 28 : 29;
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

            Entity spareEntity;
            if ((spareEntity = screen.findEntityByObjectType(9)) != null) {
               this.startAction(144, spareEntity, 0, 0);
            } else {
               this.startAction(106, target, 0, 0);
            }

            handled = true;
            break;
         case 24:
            Entity freedEntity;
            if ((freedEntity = screen.findFreeEntityForFlag(128)) != null) {
               this.actionTarget = freedEntity;
               this.finishPhase();
            } else {
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
            } else {
               if (screen.getSims().length == 2 && !neighborhood.isFreeToLeave()) {
                  screen.chooseDialogOption(767, neighborhood.simNameText(super.variant));
               }

               this.setMotionState(9);
            }
            break;
         case 27:
            screen.handleRelationshipCommand(actionId, objectId);
            this.finishPhase();
            break;
         case 28:
            int soundGroup = neighborhood.pickActionFor(target);
            int soundIndex = neighborhood.lastHourIndex(soundGroup);
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
            } else {
               this.finishPhase();
            }
            break;
         case 40:
            if (isPlayer) {
               this.runLeaveCheckPhase();
            } else {
               this.finishPhase();
            }
            break;
         case 41:
            if (isPlayer) {
               this.runVisitPhase();
            } else {
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
            Entity replacementTarget = target.occupant();
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

      if (isPlayer && handled && (neighborhood.interactionFlagsOf(actionId) & 1048576) != 0) {
         screen.notifySimsOfAction(22, this, this.actionTarget);
      }

      if (this.hasState(2)) {
         this.abortAction(true);
      }
   }

   private void runTimedPhase(int actionId, int motionState) {
      Neighborhood neighborhood = this.neighborhood();
      Entity entity;
      if (actionId == 152 && (entity = super.screen.findEntityByObjectType(10)) != null && entity.hasState(64)) {
         this.finishPhase();
      } else {
         boolean ignored = false;
         int soundId = -1;
         if (motionState == 5) {
            soundId = super.facing != 3 && super.facing != 0 ? 662 : 661;
            byte soundA;
            byte soundB;
            if (neighborhood.simAttribute(super.variant, 0) == 1) {
               soundA = 4;
               soundB = 6;
            } else {
               soundA = 7;
               soundB = 9;
            }

            GameCanvas canvas;
            (canvas = com.ea.sims3.GameCanvas.instance()).sound.pendingSound = canvas.randomRange(soundA, (int)soundB);
         }

         boolean objectSound = false;
         if (actionId != 136 && actionId != 137 && actionId != 111 && this.actionObjectId != 0) {
            soundId = com.ea.sims3.Neighborhood.reactionText(this.actionObjectId);
            objectSound = true;
         }

         int duration = motionState == 7 ? -1 : 2000;
         if (actionId == 17) {
            duration = 5000;
         }

         this.startTimedAction(-1, duration, soundId, objectSound, false);
         if (super.variant == 0) {
            if (actionId == 148) {
               neighborhood.setNeedModifier(3, -1310720);
               return;
            }

            if (actionId == 15 || actionId == 16) {
               neighborhood.setNeedModifier(5, 196608);
               return;
            }

            if (actionId == 17) {
               neighborhood.setNeedModifier(5, 196608);
            }
         }
      }
   }

   private void runSleepPhase(int phase) {
      Neighborhood neighborhood;
      int hour = (neighborhood = this.neighborhood()).gameTime;
      int wakeHour = neighborhood.lotClosingTime(super.variant);
      if (phase == 19) {
         wakeHour = Math.min(wakeHour, hour + 120);
      }

      int sleepTime = wakeHour - hour;
      if (super.variant == 0) {
         neighborhood.paused = true;
         neighborhood.setNeedModifier(1, 65536);
      }

      this.stopAnimation();
      this.setValueA(super.facing == 0 ? 36 : 35);
      int targetHour = hour + sleepTime;
      this.startTimedAction(15, targetHour, -1, false, true);
   }

   private void startPosePhase(int phase) {
      if (phase == 31) {
         if (this.actionTarget.behaviorClass() == 6) {
            phase = 33;
         } else {
            this.setFacing(super.facing + 2 & 3);
            phase = 32;
         }
      }

      int pose = phase == 30 ? 0 : (phase == 32 ? 1 : (phase == 33 ? 2 : (phase == 34 ? 3 : (phase == 37 ? 6 : (phase == 35 ? 4 : (phase == 36 ? 5 : 7))))));
      if (this.poseIndex == pose) {
         this.finishPhase();
      } else {
         this.targetPose = pose;
         this.setMotionState(2);
      }
   }

   private void runPurchasePhase() {
      Neighborhood neighborhood;
      int cost = (neighborhood = this.neighborhood()).eventTimeCost(this.actionId);
      int funds = neighborhood.money;
      if (neighborhood.money >= cost) {
         neighborhood.addMoney(-cost);
         super.screen.createObjectWithParam(super.posX, super.posY, cost);
         this.finishPhase();
      } else {
         this.abortAction(false);
      }
   }

   private void runLeaveCheckPhase() {
      Neighborhood neighborhood = this.neighborhood();
      boolean canLeave = true;
      if (super.screen.getSims().length > 1) {
         canLeave = false;
      } else if (neighborhood.simLot(0) == 4) {
         canLeave = false;
      } else if (neighborhood.isFreeToLeave()) {
         canLeave = false;
      }

      if (canLeave) {
         this.abortAction(false);
      } else {
         this.finishPhase();
      }
   }

   private void runVisitPhase() {
      Neighborhood neighborhood;
      if ((neighborhood = this.neighborhood()).activeTaskSlotOf(12) == -1 && neighborhood.activeTaskSlotOf(11) == -1) {
         this.finishPhase();
      } else {
         byte actionId = 7;
         if (super.canvas.randomPercent() < 50) {
            actionId = 14;
         }

         this.queueAction(actionId, this.actionTarget);
      }
   }

   private void runTakeItemPhase() {
      Neighborhood neighborhood = this.neighborhood();
      int itemId = this.actionObjectId;
      if (neighborhood.itemCount(itemId) > 0) {
         neighborhood.addItem(itemId, -1);
         this.actionTarget.setUpgradedType(itemId);
         this.finishPhase();
      } else {
         this.abortAction(false);
      }
   }

   private void runBuyFoodPhase() {
      Neighborhood neighborhood;
      if ((neighborhood = this.neighborhood()).itemCount(3) > 0 && neighborhood.itemCount(4) > 0) {
         neighborhood.addItem(4, -1);
         this.actionTarget.toggleAlternate();
         this.finishPhase();
      } else {
         this.abortAction(false);
      }
   }

   public final void finishPhase() {
      this.endCurrentPhase(false);
   }

   private final void endCurrentPhase(boolean force) {
      Neighborhood neighborhood = this.neighborhood();
      int lotType = this.lot().areaId();
      int stepCount = neighborhood.interactionVariantCount(this.actionId);
      boolean isPlayer = super.variant == 0;
      Entity target = this.actionTarget;
      switch (this.actionPhase) {
         case 5:
         case 6:
         case 7:
            this.clearTimedAction();
            if (isPlayer) {
               if (this.actionId == 148) {
                  neighborhood.setNeedModifier(3, 0);
               } else if (this.actionId == 15 || this.actionId == 16 || this.actionId == 17) {
                  neighborhood.setNeedModifier(5, 0);
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

            if (target != null && target.behaviorClass() == 13) {
               target.playZeroModeAndTick();
            }
            break;
         case 17:
            if (isPlayer) {
               neighborhood.setNeedModifier(3, 0);
            }

            if (target != null) {
               int targetType;
               if ((targetType = target.behaviorClass()) == 17) {
                  target.resetStateTimerToIdle();
                  if (isPlayer && lotType != 0) {
                     neighborhood.unlockAchievement(39);
                     neighborhood.addSimMilestone(lotType, 8);
                  }
               } else if (targetType == 2) {
                  target.enterModeZero();
               } else if (targetType == 3) {
                  target.enterModeThreeIfBusy();
                  target.clearOwner(this);
               }
            }
            break;
         case 18:
            if (isPlayer) {
               neighborhood.setNeedModifier(2, 0);
               if (lotType != 0) {
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
               if (this.actionPhase == 20 && lotType != 0) {
                  neighborhood.unlockAchievement(38);
                  int partnerType = target.doorOffset();
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
            if (target != null) {
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
         if (this.actionStep < stepCount - 1) {
            this.actionStep++;
            int nextPhase = neighborhood.interactionVariant(this.actionId, this.actionStep);
            this.runActionPhase(nextPhase);
            return;
         }

         this.completeAction();
      }
   }

   public final void cancelAction() {
      int actionFlags;
      if (((actionFlags = this.neighborhood().interactionFlagsOf(this.actionId)) & 1) != 0 && (actionFlags & 524288) == 0 && this.partnerSim() != null) {
         this.partnerSim().cancelAction();
      }

      this.addState(2);
      this.abortAction(true);
   }

   private final void abortAction(boolean silent) {
      this.addState(4);
      short messageId = -1;
      int messageArg = 273;
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
            if (this.actionId == 146) {
               messageId = 315;
            } else {
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

      if (messageId != -1) {
         if (!silent && super.variant == 0) {
            super.screen.chooseDialogOptionAt(messageId, messageArg);
         }

         this.completeAction();
      }
   }

   private final void chooseAutonomousAction() {
      GameCanvas canvas = super.canvas;
      int flags = this.behaviourFlags;
      if ((this.behaviourFlags & 257) != 0) {
         Entity spareEntity = super.screen.findEntityByType(4);
         this.queueAction(159, spareEntity);
      } else if (super.screen.menuTarget != this && (super.type != 0 || super.screen.menuTarget == null)) {
         if (this.partnerCooldown != -1 || (this.behaviourFlags & 32) == 0 || super.variant == 0 && (!canvas.autonomousEnabled || !super.screen.isIdleTooLong()) || super.variant != 0 && (this.behaviourFlags & 4) != 0 || !this.tryAutonomousBehaviour()) {
            if ((flags & 94) != 0) {
               int index = canvas.randomRange(0, AUTONOMY_FLAGS.length - 1);
               if ((flags & 4) != 0) {
                  index = com.ea.sims3.GameCanvas.indexOfInt(4, AUTONOMY_FLAGS);
               }

               for (int flagIndex = 0; flagIndex < AUTONOMY_FLAGS.length; flagIndex++) {
                  int flag = AUTONOMY_FLAGS[index];
                  Entity entity;
                  if ((flags & flag) != 0 && (entity = super.screen.findFreeEntityForFlag(flag)) != null) {
                     Lot lot = this.lot();
                     int entityType = entity.type;
                     short actionId = lot.frameRefsAt(entityType)[0];
                     this.queueAction(actionId, entity);
                     int clearMask = lot.linkedIdAt(entityType);
                     this.behaviourFlags &= ~clearMask;
                     return;
                  }

                  index = (index + 1) % AUTONOMY_FLAGS.length;
               }
            }
         }
      }
   }

   private final boolean tryAutonomousBehaviour() {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood();
      Sim targetSim = null;
      if (this.partnerId != -1) {
         targetSim = super.screen.findSimByIndex(this.partnerId);
         if (!this.isValidInteractionTarget(targetSim)) {
            targetSim = null;
         }
      }

      int simId = super.variant;
      if (neighborhood.simHomeLotOf(simId) != this.lot().areaId() && !neighborhood.hasSimMilestone(simId, 1) && (simId != 0 || canvas.autonomousEnabled)) {
         if ((this.behaviourFlags & 512) != 0) {
            if (simId != 0) {
               if (this.stateTime > 30000) {
                  this.behaviourFlags |= 256;
               }
            } else {
               this.behaviourFlags &= -513;
            }

            return true;
         }

         Entity spareEntity;
         if ((spareEntity = super.screen.findEntityByObjectType(10)) != null) {
            this.queueAction(152, spareEntity);
            this.behaviourFlags |= 512;
            return true;
         }
      } else {
         this.behaviourFlags &= -513;
      }

      if (targetSim == null) {
         Sim[] sims;
         int count = (sims = super.screen.getSims()).length;
         int index = canvas.randomRange(0, count - 1);

         for (int simIndex = 0; simIndex < count; simIndex++) {
            Sim otherSim = sims[index];
            index = (index + 1) % sims.length;
            if (this.isValidInteractionTarget(otherSim)) {
               targetSim = otherSim;
               break;
            }
         }
      }

      if (targetSim != null) {
         int timeOfDay;
         int actionId = (timeOfDay = canvas.randomPercent()) < 30 ? 63 : (timeOfDay < 70 ? 90 : 76);
         this.queueAction(actionId, (Entity)targetSim);
         return true;
      } else {
         return false;
      }
   }

   private final boolean isValidInteractionTarget(Sim other) {
      if (other != this && other != null) {
         Entity playerEntity = super.screen.menuTarget;
         if (other == playerEntity) {
            return false;
         } else if (other.variant == 0 && playerEntity != null) {
            return false;
         } else if (other.isSitting()) {
            return false;
         } else if (other.partnerId != -1 && other.partnerId != super.variant) {
            return false;
         } else {
            return other.hasState(8) ? false : !other.hasState(1024) || this.neighborhood().simKnownIndex(super.variant, 12) != -1;
         }
      } else {
         return false;
      }
   }

   public final void clearBehaviourFlags() {
      this.behaviourFlags = 0;
   }

   public final void addBehaviourFlags(int flags) {
      this.behaviourFlags |= flags;
   }

   public final void setLocomotionState(int state) {
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
            if (!super.screen.isNewNeighborhood()) {
               this.setValueA(43);
               this.playAnimation(40, 20);
               return;
            }
      }
   }

   public final void clearLocomotionState(int state) {
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
            if (!super.screen.isNewNeighborhood()) {
               this.setValueA(-1);
               this.stopAnimation();
               return;
            }
      }
   }

   private void refreshLocomotionState() {
      if (this.hasState(1024)) {
         this.setLocomotionState(9);
      }
   }

   private void linkPartners(Sim other) {
      this.partnerId = other.variant;
      this.partnerCooldown = -1;
      this.addBehaviourFlags(32);
      other.partnerId = super.variant;
      other.partnerCooldown = -1;
      other.addBehaviourFlags(32);
   }

   private final void unlinkPartner() {
      Sim other;
      if ((other = super.screen.findSimByIndex(this.partnerId)) != null) {
         other.behaviourFlags &= -33;
         other.partnerId = -1;
         other.partnerTimer = 0;
         other.partnerCooldown = 0;
      }

      this.behaviourFlags &= -33;
      this.partnerId = -1;
      this.partnerTimer = 0;
      this.partnerCooldown = 0;
   }

   private final boolean isNearWater() {
      Neighborhood neighborhood;
      int areaId;
      if ((areaId = (neighborhood = this.neighborhood()).simLot(super.variant)) != -1) {
         int areaValue = neighborhood.simHour(super.variant);
         if ((neighborhood.lotHourDays(areaId, areaValue) & 1) != 0) {
            return true;
         }
      }

      return super.type == 0 && neighborhood.itemCount(35) != 0 ? true : neighborhood.simHasFlag(super.variant, 1);
   }

   public final void sitDown(Entity target) {
      this.reserveTarget(target);
      int[] slotOffsets = com.ea.sims3.Entity.boundsScratch;
      target.computeSlotPosition(slotOffsets, this.reservedSlot);
      this.moveTo(slotOffsets[0], slotOffsets[1]);
      this.faceEntity(target);
      this.setFacing((super.facing + 2) % 4);
      this.poseIndex = 4;
      this.targetPose = 4;
      this.interactionTarget = target;
      target.setOwner(this);
      if (super.variant == 0) {
         super.screen.placeCameraOnEntity(target);
      }

      this.queueAction(122, target);
   }
}
