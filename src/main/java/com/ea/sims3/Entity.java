package com.ea.sims3;

import javax.microedition.lcdui.Graphics;

public class Entity implements SpriteIndex, SoundIndex, Resources {
   public static final int[] boundsScratch = new int[10];
   public final GameScreen screen;
   public final GameCanvas canvas;
   public int type;
   public int variant;
   private short baseAnimId;
   private int flags;
   private int state;
   private int mode;
   private int modeTime;
   public int posX;
   public int posY;
   public int facing;
   private byte tileWidth;
   private byte tileHeight;
   public boolean reservedFlag;
   private short stateTimer;
   private int intervalTimer;
   private boolean layoutDirty;
   private int sortYFront;
   private int sortYBack;
   private int slotMask;
   private int messageId;
   private short idleAnimId;
   private Animator[] animators;
   private short overlayAnimId;
   private int offsetYFront;
   private int offsetYBack;
   private Sim owner;
   private Sim partner;

   public final Neighborhood neighborhood() {
      return this.canvas.neighborhood;
   }

   public final Lot lot() {
      return this.canvas.lot;
   }

   public Entity(GameScreen gameScreen) {
      this.screen = gameScreen;
      this.canvas = GameCanvas.instance();
      this.animators = new Animator[1];
      this.animators[0] = new Animator();
   }

   public void place(int type, int x, int y, int facing, int value) {
      Lot lot = this.lot();
      Neighborhood neighborhood = this.neighborhood();
      this.moveTo(x, y);
      this.setFacing(facing);
      this.type = type;
      this.variant = value;
      this.flags = lot.objectFlagsAt(type);
      this.state = 0;
      this.baseAnimId = (short)lot.spriteIdAt(type);
      this.offsetYFront = 0;
      this.offsetYBack = 0;
      this.owner = null;
      this.partner = null;
      this.addState(128);
      this.slotMask = 0;
      int idleAnimId = lot.subTypeAt(type);
      int modeAnimId = lot.attachmentAt(type, 0);
      if (type == 99) {
         idleAnimId = lot.stageMusicId();
      }

      if (idleAnimId == 196 && modeAnimId != 0) {
         this.setValueB(modeAnimId);
      } else {
         this.idleAnimId = -1;
         this.playIdleAnimation(idleAnimId);
      }

      this.setValueA(-1);
      int stateBits = this.readStateBits();
      int modeValue = 0;
      if (type == 9) {
         modeValue = 1;
      } else if (type == 92) {
         if ((modeValue = stateBits & 16777215) != 0 && lot.areaId() != 0) {
            int clockNow = neighborhood.gameTime;
            int savedTime;
            if ((savedTime = this.readStateTime()) != -1 && clockNow - savedTime > 1440) {
               modeValue = 0;
            }
         }
      } else if (lot.objectKindAt(type) == 16) {
         modeValue = stateBits & 16777215;
      }

      this.setModeFromState(modeValue);
      if ((stateBits & 16777216) != 0) {
         this.turnOnPrimary();
      }

      if ((stateBits & 33554432) != 0) {
         this.turnOnSecondary();
      }

      if (this.hasFlag(201326592) && !this.hasState(768)) {
         this.stateTimer = (short)(stateBits & 16777215);
         this.intervalTimer = 0;
         if (this.stateTimer == 0) {
            this.stateTimer = (short)this.canvas.randomRange(0, 100);
         }
      }

      if (this.behaviorClass() == 10) {
         byte bubbleId = 47;
         if (type == 48) {
            bubbleId = 49;
         } else if (type == 50) {
            bubbleId = 51;
         } else if (type == 52) {
            bubbleId = 53;
         }

         this.screen.createEntity(bubbleId, this.posX, this.posY, this.facing, this.variant).setValueC(20);
      }

      if (this.behaviorClass() == 8) {
         this.notifyScreenPosition();
      }
   }

   public final void despawn() {
      if (this.behaviorClass() == 8) {
         this.occupant().despawn();
      }

      this.screen.removeEntity(this);
      this.owner = null;
      this.partner = null;
   }

   public final int behaviorClass() {
      return this.lot().objectKindAt(this.type);
   }

   public final int typeAnimId() {
      return this.hasFlag(64) ? this.drawOwnerNames() : this.baseAnimId;
   }

   public final void moveTo(int x, int y) {
      this.layoutDirty = true;
      this.posX = x;
      this.posY = y;
   }

   public final boolean hasFlag(int flag) {
      return (this.flags & flag) != 0;
   }

   public final void addFlag(int flag) {
      this.flags |= flag;
   }

   public final boolean hasState(int flag) {
      return (this.state & flag) != 0;
   }

   public final void setState(int mask, boolean enabled) {
      if (enabled) {
         this.addState(mask);
      } else {
         this.clearState(mask);
      }
   }

   public final void addState(int flag) {
      this.state |= flag;
   }

   public final void clearState(int flag) {
      this.state &= ~flag;
   }

   public final void setBusy() {
      this.addState(8192);
      if (this.behaviorClass() == 8) {
         this.occupant().addState(8192);
      }
   }

   public final void clearBusy() {
      this.clearState(8192);
      if (this.behaviorClass() == 8) {
         this.occupant().clearState(8192);
      }
   }

   public final void relocate(int x, int y, int facing) {
      Entity occupant = null;
      if (this.behaviorClass() == 8) {
         occupant = this.occupant();
      }

      this.moveTo(x, y);
      this.setFacing(facing);
      if (occupant != null) {
         int[] offset = boundsScratch;
         this.computeDoorOffset(offset);
         occupant.relocate(offset[0], offset[1], offset[2]);
      }
   }

   public final void update() {
      if (this.layoutDirty) {
         this.layoutDirty = false;
         int tileY = this.lot().toScreenY(this.posX, this.posY);
         this.sortYFront = tileY + this.offsetYFront;
         this.sortYBack = tileY + this.offsetYBack;
      }
   }

   public int interactionAnimId() {
      return this.hasFlag(16) ? this.typeAnimId() : 24;
   }

   public final void computePosition(int[] out) {
      Lot lot = this.lot();
      int offsetX = 0;
      int offsetY = 0;
      Animator animator;
      int animId = (animator = this.animatorAt(0)).animId;
      int frame = animator.frame;
      if (this.behaviorClass() == 16) {
         animId = 270;
         frame = 0;
      }

      if (animId != -1) {
         if (SpriteRenderer.hitTest(out, animId, frame, 0)) {
            offsetX = out[0];
            offsetY = out[1];
         } else if (this.hasFlag(16384)) {
            offsetY = 0 - (this.screen.isNewNeighborhood() ? 7 : 38);
         }
      }

      out[0] = lot.toScreenFixedX(this.posX, this.posY) + (offsetX << 16);
      out[1] = lot.toScreenFixedY(this.posX, this.posY) + (offsetY << 16);
   }

   public final void computeSlotPosition(int[] out, int slot) {
      Lot lot = this.lot();
      int originTileX = com.ea.sims3.Lot.toTileX(this.posX);
      int originTileY = com.ea.sims3.Lot.toTileY(this.posY);
      int deltaTileX = lot.framePartX(this.type, slot, this.facing);
      int deltaTileY = lot.framePartY(this.type, slot, this.facing);
      int tileX = originTileX + deltaTileX;
      int tileY = originTileY + deltaTileY;
      out[0] = com.ea.sims3.Lot.toFixedX(tileX);
      out[1] = com.ea.sims3.Lot.toFixedY(tileY);
   }

   public final int findNearestSlot(int x, int y, boolean avoidOccupied, Sim ignoreSim) {
      Lot lot = this.lot();
      int[] out = boundsScratch;
      int slotCount;
      if ((slotCount = lot.frameCountAt(this.type)) <= 0) {
         return -1;
      } else {
         int bestSlot = -1;
         int bestDistance = Integer.MAX_VALUE;

         for (int slot = 0; slot < slotCount; slot++) {
            if (!avoidOccupied || this.isSlotFree(slot)) {
               this.computeSlotPosition(out, slot);
               int slotX = out[0];
               int slotY = out[1];
               if (lot.isWalkableAt(slotX, slotY)) {
                  if (avoidOccupied && !this.hasFlag(4194304)) {
                     int slotTileX = com.ea.sims3.Lot.toTileX(slotX);
                     int slotTileY = com.ea.sims3.Lot.toTileY(slotY);
                     boolean occupied = false;
                     Sim[] sims = this.screen.getSims();

                     for (int index = 0; index < sims.length && !occupied; index++) {
                        Sim other;
                        if ((other = sims[index]) != ignoreSim) {
                           int otherTileX = com.ea.sims3.Lot.toTileX(other.posX);
                           int otherTileY = com.ea.sims3.Lot.toTileY(other.posY);
                           if (otherTileX == slotTileX && otherTileY == slotTileY) {
                              occupied = true;
                           }
                        }
                     }

                     if (occupied) {
                        continue;
                     }
                  }

                  int deltaX = Math.abs(x - slotX);
                  int deltaY = Math.abs(y - slotY);
                  int distance;
                  if ((distance = deltaX + deltaY) < bestDistance) {
                     bestDistance = distance;
                     bestSlot = slot;
                  }
               }
            }
         }

         return bestSlot;
      }
   }

   public final boolean tryOccupySlot(int slot) {
      if (this.hasFlag(4194304)) {
         return true;
      } else {
         if (this.hasFlag(8388608)) {
            if ((this.slotMask & 1 << slot) == 0) {
               this.slotMask |= 1 << slot;
               return true;
            }
         } else if (this.slotMask == 0) {
            this.slotMask = -1;
            return true;
         }

         return false;
      }
   }

   public final void releaseSlot(int slot) {
      if (this.hasFlag(8388608)) {
         this.slotMask &= ~(1 << slot);
      } else {
         this.slotMask = 0;
      }
   }

   public final boolean canOccupySlot() {
      return this.hasFlag(4194304) ? true : this.slotMask == 0;
   }

   public final boolean hasFreeSlot() {
      if (this.canOccupySlot()) {
         return true;
      } else {
         int slotCount = this.lot().frameCountAt(this.type);
         return this.slotMask < (1 << slotCount) - 1;
      }
   }

   public final boolean isSlotFree(int slot) {
      return (this.slotMask & 1 << slot) == 0;
   }

   private int readStateBits() {
      if (this.variant == -1) {
         return 0;
      } else {
         Lot lot = this.lot();
         return this.neighborhood().tipValue(lot.areaId(), this.variant);
      }
   }

   private int readStateTime() {
      if (this.variant == -1) {
         return -1;
      } else {
         Lot lot = this.lot();
         return this.neighborhood().tipTime(lot.areaId(), this.variant);
      }
   }

   private void saveState() {
      if (this.variant != -1) {
         Lot lot = this.lot();
         Neighborhood neighborhood = this.neighborhood();
         int stateBits = 0;
         if (this.type == 92) {
            stateBits = this.mode;
         } else if (this.behaviorClass() == 16) {
            stateBits = this.mode;
         }

         if (this.hasFlag(67108864) && this.hasState(256)) {
            stateBits |= 16777216;
         }

         if (this.hasFlag(134217728) && this.hasState(512)) {
            stateBits |= 33554432;
         }

         if (lot.areaId() == 0 && this.hasFlag(201326592)) {
            stateBits |= this.stateTimer;
         }

         if (this.hasState(32768)) {
            stateBits |= 134217728;
         }

         if (this.hasState(16384)) {
            stateBits |= 67108864;
         }

         if (this.hasState(65536)) {
            stateBits |= 268435456;
         }

         neighborhood.pushTip(lot.areaId(), this.variant, stateBits);
      }
   }

   public boolean isIdleModeZero() {
      return this.behaviorClass() == 16 ? !this.isAnimating() && !this.isAnimatorRunning(1) && this.mode != 1 : this.mode == 0;
   }

   public final boolean isIdleModeOne() {
      return this.behaviorClass() != 16 || !this.isAnimating() && !this.isAnimatorRunning(1) ? this.mode == 1 : false;
   }

   public final void setModeFromState(int stateBits) {
      int behaviorClass;
      if ((behaviorClass = this.behaviorClass()) == 2) {
         this.applyTwoStateMode(stateBits);
      } else if (behaviorClass == 3) {
         this.applyFourStateMode(stateBits);
      } else if (behaviorClass == 7) {
         this.applyOverlayMode(stateBits);
      } else if (behaviorClass == 8) {
         this.setMode(stateBits);
      } else if (behaviorClass == 10) {
         this.applyActiveMode(stateBits);
      } else if (behaviorClass == 17) {
         this.applySimpleTwoStateMode(stateBits);
      } else if (behaviorClass == 19) {
         this.applyStaticTwoStateMode(stateBits);
      } else if (behaviorClass == 16) {
         this.applyMultiFrameMode(stateBits);
      } else if (this.type == 92) {
         this.applyDoorMode(stateBits);
      }

      this.mode = stateBits;
      this.modeTime = 0;
      if (this.type == 92) {
         this.saveState();
      }
   }

   public void advanceTime(int delta) {
      this.modeTime += delta;
      if (this.hasFlag(201326592) && !this.hasState(768)) {
         this.intervalTimer += delta;
         if (this.intervalTimer > 4000) {
            this.intervalTimer -= 4000;
            this.advanceStateTimer(1);
         }
      }

      if (this.hasState(1)) {
         delta = this.screen.lastTickDuration;
      }

      int type = this.type;
      int behaviorClass;
      if ((behaviorClass = this.behaviorClass()) == 3) {
         this.advanceModeAnimations(delta);
      } else if (behaviorClass == 16) {
         this.advanceMultiFrame(delta);
      } else {
         this.advanceAnimations(delta);
         if (!this.isAnimating()) {
            if (behaviorClass == 7) {
               this.setModeFromState(2);
            } else if (this.type == 92 && this.mode == 2) {
               this.setModeFromState(1);
            }
         }

         if (behaviorClass == 8) {
            this.advanceAnimator(delta, 2);
            if (!this.isAnimatorRunning(2)) {
               if (this.mode == 2) {
                  this.setModeFromState(1);
               } else {
                  this.setModeFromState(0);
               }
            }
         }
      }

      if (type == 7) {
         if (!this.isAnimating()) {
            this.despawn();
            return;
         }
      } else if (type == 8 && this.modeTime > 1500) {
         this.despawn();
      }
   }

   public final void advanceAnimations(int delta) {
      if (this.hasState(2048)) {
         delta = FixedMath.mul(delta << 16, 81920) >> 16;
      } else if (this.hasState(4096)) {
         delta = FixedMath.mul(delta << 16, 49152) >> 16;
      }

      boolean drawn = this.advanceAnimator(delta, 0);
      if (this.advanceAnimator(delta, 1)) {
         drawn = true;
      }

      if (this.overlayAnimId != -1 && drawn) {
         this.draw();
      }
   }

   public final boolean advanceAnimator(int delta, int index) {
      Animator animator;
      return (animator = this.animatorAt(index)) != null ? animator.advance(delta) : false;
   }

   public final void draw() {
      int[] bounds = boundsScratch;
      Animator idleAnimator = this.animatorAt(0);
      Animator overlayAnimator = this.animatorAt(1);
      byte firstFrame = 1;
      Animator animator = idleAnimator;
      int offsetX = 0;
      int offsetY = 0;
      if (overlayAnimator != null && overlayAnimator.isRunning()) {
         animator = overlayAnimator;
         firstFrame = 0;
         if (idleAnimator.matchesFrame(bounds, 1)) {
            offsetX = bounds[0];
            offsetY = bounds[1];
         } else if (this.hasState(1024)) {
            offsetY = -30;
         }
      }

      int frameCount;
      if ((frameCount = animator.frameDuration()) > firstFrame) {
         Lot lot = this.lot();
         if (this.behaviorClass() == 16) {
            int frame = animator.frame;
            int slotCount = this.slotCount();
            int animatorIndex = 2 + slotCount - frame;
            this.animatorFor(animatorIndex).play(this.overlayAnimId, 16);
            return;
         }

         for (int frameIndex = firstFrame; frameIndex < frameCount; frameIndex++) {
            int tileX = this.posX;
            int tileY = this.posY;
            int screenX = lot.toScreenFixedX(tileX, tileY);
            int screenY = lot.toScreenFixedY(tileX, tileY);
            animator.matchesFrame(bounds, frameIndex);
            screenX += bounds[0] + offsetX << 16;
            screenY += bounds[1] + offsetY << 16;
            tileX = lot.toWorldX(screenX, screenY);
            tileY = lot.toWorldY(screenX, screenY);
            int depthOffset = -(bounds[1] + offsetY);
            if (this.hasFlag(524288)) {
               depthOffset += 5;
            }

            this.screen.createRoomObject(tileX, tileY, this.overlayAnimId, depthOffset);
         }
      }
   }

   public boolean handleAction(int actionId, Sim sim) {
      if (this.type == 92) {
         if (actionId == 148) {
            this.setModeFromState(0);
         }
      } else if (this.type == 9) {
         if (actionId == 149) {
            if (this.variant != -1) {
               this.lot().removeObject(this.variant);
            }

            this.despawn();
         }
      } else if (this.behaviorClass() == 10) {
         if (actionId == 150 || actionId == 153) {
            this.setModeFromState(1);
            if (sim.variant == 0 && !this.neighborhood().hasSimMilestone(0, 1)) {
               this.screen.notifySimsOfAction(23, sim, this);
            }
         } else if (actionId == 151) {
            if (!this.screen.isEntityAtTile(0, this.posX, this.posY)) {
               this.setModeFromState(0);
            }
         } else if (actionId == 152 && sim.variant == 0) {
            this.screen.notifySimsOfAction(24, sim, this);
         }
      } else if (this.behaviorClass() == 22) {
         if (actionId == 131) {
            this.clearState(16384);
            this.addState(32768);
            this.saveState();
         } else if (actionId == 130) {
            this.addState(16384);
            this.clearState(32768);
            this.saveState();
         }
      } else if (this.behaviorClass() == 16 && actionId == 112) {
         this.addState(65536);
         this.saveState();
      }

      return false;
   }

   public final void turnOnPrimary() {
      this.addState(256);
      switch (this.behaviorClass()) {
         case 15:
         case 22:
            this.markInactive();
         default:
            this.saveState();
            this.playAnimation(28, 20);
            this.screen.showNeighborhoodIntro((byte)8);
      }
   }

   public final void turnOffPrimary() {
      this.clearState(256);
      this.saveState();
      this.stopAnimation();
   }

   public final void turnOnSecondary() {
      this.addState(512);
      this.saveState();
      this.playAnimation(41, 20);
      this.screen.showNeighborhoodIntro((byte)9);
   }

   public final void turnOffSecondary() {
      this.clearState(512);
      this.saveState();
      this.stopAnimation();
   }

   public final void advanceStateTimer(int amount) {
      if (this.lot().areaId() == 0) {
         this.stateTimer = (short)(this.stateTimer + amount);
         if (this.stateTimer > 300) {
            this.stateTimer = 0;
            if (this.hasFlag(67108864)) {
               this.turnOnPrimary();
            } else {
               this.turnOnSecondary();
            }
         }

         this.saveState();
      }
   }

   public final void markActive() {
      int behaviorClass;
      if ((behaviorClass = this.behaviorClass()) == 15 || behaviorClass == 22 || behaviorClass == 18) {
         this.playModeAnimation(1);
      }

      if (behaviorClass == 22 && !this.hasState(49152)) {
         this.addState(32768);
      } else if (behaviorClass == 8) {
         this.setModeFromState(2);
      }

      this.addState(64);
      this.advanceStateTimer(15);
   }

   public final void markInactive() {
      int behaviorClass;
      if ((behaviorClass = this.behaviorClass()) == 15 || behaviorClass == 22 || behaviorClass == 18) {
         this.playModeAnimation(0);
      } else if (behaviorClass == 8) {
         this.setModeFromState(3);
      }

      this.clearState(64);
      this.advanceStateTimer(15);
   }

   public final void setIdleAnimation(int animId, int offset) {
      this.clearState(128);
      this.playIdleAnimation(animId);
      if (animId == 35 || animId == 36) {
         this.addState(1);
      }

      this.setValueC(offset);
   }

   public final void showMessage(int textId) {
      this.messageId = textId;
      this.playIdleAnimation(196);
      this.setValueC(30);
   }

   private void drawMessage(int x, int y) {
      GameCanvas canvas = this.canvas;
      int progress = FixedMath.smoothStep(0, 1500, this.modeTime);
      int offsetY = -40 + FixedMath.mul(progress, -30);
      SdkString text = canvas.beginString();
      canvas.formatNumber(this.messageId);
      canvas.drawString(text, 0, x, y + offsetY, 17);
   }

   public final void enterModeOne() {
      this.setModeFromState(1);
   }

   public final void enterModeZero() {
      this.setModeFromState(0);
   }

   private void applyTwoStateMode(int mode) {
      byte modeAnimation;
      byte overlayAnimation;
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

   public final void enterModeTwoIfIdle() {
      if (this.mode == 0 || this.mode == 3) {
         this.setModeFromState(2);
      }
   }

   public final void enterModeThreeIfBusy() {
      if (this.mode == 1 || this.mode == 2) {
         this.setModeFromState(3);
      }
   }

   public final boolean isModeOne() {
      return this.mode == 1;
   }

   public final void computeFootprintOffset(int[] out) {
      Lot lot;
      int deltaX = (lot = this.lot()).toScreenX(this.posX, this.posY);
      int deltaY = lot.toScreenY(this.posX, this.posY);
      this.animatorAt(2).matchesFrame(out, 0);
      out[0] += deltaX;
      out[1] += deltaY;
   }

   private void advanceModeAnimations(int delta) {
      this.advanceAnimations(delta);
      this.advanceAnimator(delta, 2);
      if (!this.isAnimatorRunning(2)) {
         if (this.mode == 2) {
            this.setModeFromState(1);
            return;
         }

         if (this.mode == 3) {
            this.setModeFromState(0);
         }
      }
   }

   private void applyFourStateMode(int mode) {
      short animId = -1;
      boolean loopForever = true;
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

      Animator animator = this.animatorFor(2);
      if (animId != -1) {
         int frameId = this.lot().frameDataAt(animId, this.facing);
         animator.play(frameId, loopForever ? 20 : 16);
      } else {
         animator.setRunning(false);
      }
   }

   public final int doorOffset() {
      Entity[] entities = this.screen.getEntities();
      Entity door = null;

      for (int index = 0; index < entities.length; index++) {
         Entity entity;
         if ((entity = entities[index]) != this && entity.behaviorClass() == 4) {
            door = entity;
            break;
         }
      }

      if (door != null) {
         return this.variant < door.variant ? 512 : 1024;
      } else {
         return 512;
      }
   }

   public final boolean isPassable() {
      Lot lot = this.lot();
      int tileX = com.ea.sims3.Lot.toTileX(this.posX);
      int tileY = com.ea.sims3.Lot.toTileY(this.posY);
      return !lot.footprintFree(tileX, tileY, 1, 1, this.facing) && this.type != 41;
   }

   public final Entity container() {
      Entity[] entities = this.screen.getEntities();

      for (int index = 0; index < entities.length; index++) {
         Entity entity;
         if ((entity = entities[index]).behaviorClass() == 8 && entity.occupant() == this) {
            return entity;
         }
      }

      return null;
   }

   public final void setModeOne() {
      this.setModeFromState(1);
   }

   public final void setModeZero() {
      this.setModeFromState(0);
   }

   private void applyOverlayMode(int mode) {
      byte modeAnimation = 0;
      boolean loopEnabled = true;
      byte overlayAnimation = -1;
      if (mode == 1) {
         modeAnimation = 1;
         loopEnabled = false;
         overlayAnimation = 42;
      }

      this.overlayAnimId = overlayAnimation;
      this.setState(128, loopEnabled);
      this.playModeAnimation(modeAnimation);
   }

   public final boolean isModeTwo() {
      return this.mode == 2;
   }

   private void notifyScreenPosition() {
      int[] bounds = boundsScratch;
      this.computeDoorOffset(bounds);
      this.screen.createEntity(41, bounds[0], bounds[1], bounds[2], -1);
   }

   private void computeDoorOffset(int[] out) {
      int x = this.posX;
      int y = this.posY;
      byte facing = 3;
      if (this.facing == 0) {
         x += 524288;
         y -= 524288;
         facing = 2;
      } else {
         x -= 524288;
         y += 524288;
      }

      out[0] = x;
      out[1] = y;
      out[2] = facing;
   }

   public final Entity occupant() {
      int[] bounds = boundsScratch;
      this.computeDoorOffset(bounds);
      return this.screen.findNearestEntityOfType(41, bounds[0], bounds[1]);
   }

   public final void setMode(int mode) {
      this.mode = mode;
      byte modeIndex = -1;
      byte frameTime = 20;
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

      if (modeIndex != -1) {
         Lot lot;
         int frameAnim = (lot = this.lot()).attachmentAt(this.type, modeIndex);
         int frameId = lot.frameDataAt(frameAnim, this.facing);
         this.animatorFor(2).play(frameId, frameTime);
      } else {
         this.animatorFor(2).setRunning(false);
      }
   }

   private void applyActiveMode(int mode) {
      this.mode = mode;
      byte modeIndex = -1;
      byte offsetY = 0;
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

   public final void playFirstModeAnimation() {
      this.playModeAnimation(1);
   }

   public final void playZeroModeAndTick() {
      this.playModeAnimation(0);
      this.advanceStateTimer(15);
   }

   private final int drawOwnerNames() {
      int ownerSlot = -1;
      int partnerSlot = -1;
      int slotType = this.lot().indexOfLotId(this.type);
      Neighborhood neighborhood;
      int slotCount = (neighborhood = this.neighborhood()).simCount;

      for (int index = 0; index < slotCount; index++) {
         if (neighborhood.simHomeLotOf(index) == slotType) {
            if (ownerSlot == -1) {
               ownerSlot = index;
            } else if (partnerSlot == -1) {
               partnerSlot = index;
            }
         }
      }

      if (ownerSlot == -1 && partnerSlot == -1) {
         return 201;
      } else {
         GameCanvas canvas = this.canvas;
         SdkString nameText;
         if (ownerSlot == 0) {
            nameText = canvas.profileName();
         } else {
            nameText = canvas.getString(neighborhood.simNameText(ownerSlot));
         }

         if (partnerSlot == -1) {
            canvas.formatStringWith(-10, 202, nameText);
         } else {
            SdkString partnerText = canvas.getString(neighborhood.simNameText(partnerSlot));
            canvas.formatStringWith2(-10, 203, nameText, partnerText);
         }

         return -10;
      }
   }

   private static final int upgradeTypeFor(int upgradeId) {
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

   private static final int upgradeAnimFor(int type, int variant) {
      switch (type) {
         case 18:
            return 264;
         case 19:
            return 265;
         case 20:
            return 266;
         case 21:
            if (variant == 0) {
               return 262;
            }

            return 263;
         case 22:
            if (variant == 0) {
               return 268;
            }

            return 269;
         default:
            return -1;
      }
   }

   private static final int upgradeVariantFor(int type) {
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

   private int slotCount() {
      return this.isUpgraded() ? 5 : 8;
   }

   private boolean isUpgraded() {
      return (this.lot().objectFlagsAt(this.type) & 65536) != 0;
   }

   public final void setUpgradedType(int upgradeId) {
      Lot lot = this.lot();
      int newType = upgradeTypeFor(upgradeId);
      this.type = newType;
      lot.setObjectId(this.variant, newType);
      this.baseAnimId = (short)lot.spriteIdAt(newType);
      this.mode = 2;
      this.saveState();
      this.clearState(128);
      if (this.isUpgraded()) {
         this.playIdleAnimation(271);
         this.playAnimation(267, 16);
         this.setValueA(upgradeAnimFor(newType, 0));
      } else {
         this.playIdleAnimation(272);
      }
   }

   public final void toggleAlternate() {
      boolean upgraded = false;
      switch (this.mode) {
         case 2:
         default:
            if (this.isUpgraded()) {
               upgraded = true;
               this.mode = 1;
               this.clearState(128);
               this.playIdleAnimation(271);
               this.playAnimation(267, 16);
               this.setValueA(upgradeAnimFor(this.type, 1));
            } else {
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
            this.setValueA(upgradeAnimFor(this.type, 0));
      }

      if (upgraded) {
         Neighborhood neighborhood;
         (neighborhood = this.neighborhood()).unlockAchievement(32);
         if (this.type == 21) {
            neighborhood.unlockAchievement(33);
         } else if (this.type == 19) {
            neighborhood.unlockAchievement(34);
         } else if (this.type == 20) {
            neighborhood.unlockAchievement(35);
         }
      }

      this.saveState();
   }

   private void playSequenceOnAll() {
      this.playAnimation(196, 16);

      for (int index = 2; index <= 9; index++) {
         Animator animator;
         if ((animator = this.animatorAt(index)) != null) {
            animator.play(196, 16);
         }
      }
   }

   private void playChainAnimation(int variant) {
      int frameId = upgradeAnimFor(this.type, variant);
      int count = this.slotCount();
      int lastIndex = 2 + count - 1;

      for (int index = 0; index < count; index++) {
         Animator animator;
         (animator = this.animatorFor(lastIndex - index)).play(frameId, 16);
         animator.rewind();
      }
   }

   public final void completeUpgrade() {
      int variantId = upgradeVariantFor(this.type);
      int slotCount = this.slotCount();
      if (this.hasState(65536)) {
         slotCount += 2;
         this.clearState(65536);
      }

      this.neighborhood().onRepeatAction(slotCount);
      this.neighborhood().addItem(variantId, slotCount);
      this.screen.showMessageDialog(variantId, slotCount, 289, 715);
      if (this.isUpgraded()) {
         this.transformToCompleted();
      } else {
         this.setModeFromState(3);
      }

      this.playSequenceOnAll();
      this.saveState();
   }

   public final void transformToCompleted() {
      this.type = 17;
      this.lot().setObjectId(this.variant, 17);
      this.baseAnimId = (short)this.lot().spriteIdAt(17);
      this.mode = 0;
      this.playSequenceOnAll();
      this.clearState(128);
      this.playIdleAnimation(270);
      this.saveState();
   }

   private void applyMultiFrameMode(int mode) {
      this.mode = mode;
      short animId;
      switch (mode) {
         case 0:
         default:
            animId = 270;
            break;
         case 1:
            this.playChainAnimation(1);
            short overlayFrame = 275;
            animId = 274;
            if (this.isUpgraded()) {
               overlayFrame = 267;
               animId = 271;
            }

            Animator overlayAnimator;
            (overlayAnimator = this.animatorFor(1)).play(overlayFrame, 16);
            overlayAnimator.rewind();
            break;
         case 2:
            if (this.isUpgraded()) {
               this.playChainAnimation(0);
               animId = 271;
               Animator alternateAnimator;
               (alternateAnimator = this.animatorFor(1)).play(267, 16);
               alternateAnimator.rewind();
            } else {
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

   private void advanceMultiFrame(int delta) {
      int slotCount = this.slotCount();

      for (int index = 0; index < slotCount; index++) {
         this.advanceAnimator(delta, 2 + index);
      }

      this.advanceAnimations(delta);
   }

   public final void playModeOneAnimation() {
      this.playModeAnimation(1);
   }

   public final void playModeZeroAnimation() {
      this.playModeAnimation(0);
   }

   public final void activateModeOne() {
      this.setModeFromState(1);
   }

   public final void resetStateTimerToIdle() {
      this.advanceStateTimer(15);
      this.setModeFromState(0);
   }

   private void applySimpleTwoStateMode(int mode) {
      this.mode = mode;
      byte modeAnimation;
      byte overlayAnimation;
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

   public final void startModeOne() {
      this.setModeFromState(1);
   }

   public final void activateModeZero() {
      this.setModeFromState(0);
   }

   private void applyStaticTwoStateMode(int mode) {
      this.mode = mode;
      byte modeAnimation;
      byte overlayAnimation;
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

   public final void interactWith(Sim sim) {
      this.screen.notifySimsOfAction(21, sim, this);
      this.faceEntity(sim);
      this.setFacing((this.facing + 2) % 4);
      this.setModeFromState(2);
   }

   private void applyDoorMode(int mode) {
      boolean visible = true;
      byte modeIndex;
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

   public final Animator animatorAt(int index) {
      return index < this.animators.length ? this.animators[index] : null;
   }

   public final Animator animatorFor(int index) {
      if (index >= this.animators.length) {
         Animator[] grown = new Animator[index + 1];
         System.arraycopy(this.animators, 0, grown, 0, this.animators.length);
         this.animators = grown;
      }

      if (this.animators[index] == null) {
         Animator created = new Animator();
         this.animators[index] = created;
         created.setRunning(false);
      }

      return this.animators[index];
   }

   public final void playAnimation(int frameId, int frameTime) {
      this.animatorFor(1).play(frameId, frameTime);
   }

   public final void stopAnimation() {
      Animator animator;
      if ((animator = this.animatorAt(1)) != null) {
         animator.setRunning(false);
      }
   }

   public final void setValueA(int animId) {
      this.overlayAnimId = (short)animId;
   }

   public final int frontSortY() {
      this.update();
      return this.sortYFront;
   }

   public final int backSortY() {
      this.update();
      return this.sortYBack;
   }

   public final void setValueC(int value) {
      this.offsetYFront = value;
      this.offsetYBack = value;
      this.layoutDirty = true;
   }

   public final void setOwner(Sim sim) {
      if (this.owner == null) {
         this.owner = sim;
      } else {
         if (this.partner == null) {
            this.partner = sim;
         }
      }
   }

   public final void clearOwner(Sim sim) {
      if (this.owner == sim) {
         this.owner = null;
      }

      if (this.partner == sim) {
         this.partner = null;
      }
   }

   public void render(Graphics graphics) {
      Lot lot = this.lot();
      int[] bounds = boundsScratch;
      int originX = lot.viewCenterX();
      int originY = lot.viewCenterY();
      int screenX = originX + lot.toScreenX(this.posX, this.posY);
      int screenY = originY + lot.toScreenY(this.posX, this.posY);
      if (this.type == 8) {
         this.drawMessage(screenX, screenY);
      } else {
         Animator idleAnimator;
         (idleAnimator = this.animatorAt(0)).draw(graphics, screenX, screenY);
         int behaviorClass;
         Animator auxAnimator;
         if ((behaviorClass = this.behaviorClass()) == 8 && (auxAnimator = this.animatorAt(2)) != null && auxAnimator.isRunning()) {
            auxAnimator.draw(graphics, screenX, screenY);
         }

         int overlayX = screenX;
         int overlayY = screenY;
         Animator overlayAnimator;
         if ((overlayAnimator = this.animatorAt(1)) != null && overlayAnimator.isRunning()) {
            if (idleAnimator.matchesFrame(bounds, 1)) {
               overlayX = screenX + bounds[0];
               overlayY = screenY + bounds[1];
            } else if (idleAnimator.matchesFrame(bounds, 0)) {
               overlayX = screenX + bounds[0];
               overlayY = screenY + bounds[1];
            }

            overlayAnimator.draw(graphics, overlayX, overlayY);
         }

         if (behaviorClass == 16 && overlayAnimator != null) {
            int overlayAnimId = overlayAnimator.animId;
            if (overlayAnimator.animId != 196) {
               int slotCount;
               for (int slot = (slotCount = this.slotCount()) - 1; slot >= 0; slot--) {
                  Animator slotAnimator;
                  if ((slotAnimator = this.animatorAt(2 + slot)) != null) {
                     int frameIndex = slotCount - slot;
                     SpriteRenderer.hitTest(bounds, overlayAnimId, frameIndex, 0);
                     int frameX = overlayX + bounds[0];
                     int frameY = overlayY + bounds[1];
                     slotAnimator.draw(graphics, frameX, frameY);
                  }
               }
            }
         }

         Animator seatAnimator;
         if (behaviorClass == 3 && (seatAnimator = this.animatorAt(2)).isRunning()) {
            seatAnimator.draw(graphics, screenX, screenY);
         }

         if (this.hasFlag(33554432)) {
            if (this.owner != null) {
               this.owner.drawBody(graphics);
            }

            if (this.partner != null) {
               this.partner.drawBody(graphics);
            }

            int animId = lot.attachmentAt(this.type, 1);
            int frameId = lot.frameDataAt(animId, this.facing);
            SpriteRenderer.drawFrame(graphics, frameId, 0, screenX, screenY);
         }
      }
   }

   public final void addResourceIndices(short[] out) {
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
      } else {
         GameCanvas.addToList(out, 275);
      }
   }

   public final void updateFootprint(boolean highlight) {
      if (this.hasFlag(524288)) {
         this.setValueC(5);
      } else if (this.hasFlag(4)) {
         if (this.type == 11) {
            this.setValueC(5);
         }
      } else {
         Lot lot = this.lot();
         int tileX = com.ea.sims3.Lot.toTileX(this.posX);
         int tileY = com.ea.sims3.Lot.toTileY(this.posY);
         int width = lot.objectWidthAt(this.type);
         int height = lot.objectHeightAt(this.type);
         if (this.facing == 1 || this.facing == 3) {
            int swap = width;
            width = height;
            height = swap;
         }

         short colorB;
         short colorA;
         if (highlight) {
            if (this.behaviorClass() == 10) {
               colorA = 255;
               colorB = 32;
            } else if (this.type == 116) {
               colorA = -129;
               colorB = 0;
            } else {
               colorA = 255;
               colorB = 128;
            }
         } else {
            colorA = -161;
            colorB = 0;
         }

         lot.fillCellFlags(tileX - width + 1, tileY - height + 1, width, height, colorA, colorB);
         this.tileWidth = (byte)width;
         this.tileHeight = (byte)height;
         if (this.hasFlag(16777216)) {
            this.setValueC(-10);
         } else if (this.hasFlag(8)) {
            this.setValueC(0);
         } else if (this.behaviorClass() == 14 || this.behaviorClass() == 12) {
            this.setValueC(20);
         } else if (width != 0 && height != 0) {
            this.offsetYFront = width * -8 + 4;
            this.offsetYBack = height * -8 + 4;
         }

         this.layoutDirty = true;
      }
   }

   public final void setFacing(int facing) {
      if (this.facing != facing) {
         this.layoutDirty = true;
         this.facing = facing;
         if (this.idleAnimId != -1) {
            this.setValueB(this.idleAnimId);
         }
      }
   }

   public final void faceDirection(int dx, int dy) {
      this.setFacing(Math.abs(dx) > Math.abs(dy) ? (dx < 0 ? 2 : 0) : (dy < 0 ? 3 : 1));
   }

   public final void faceEntity(Entity other) {
      int halfWidth = Math.max(other.tileWidth - 1, 0);
      int halfHeight = Math.max(other.tileHeight - 1, 0);
      int offsetX = halfWidth << 18;
      int offsetY = halfHeight << 18;
      int targetX = other.posX - offsetX;
      int targetY = other.posY - offsetY;
      this.faceDirection(targetX - this.posX, targetY - this.posY);
   }

   public final void playIdleAnimation(int frameId) {
      this.animatorAt(0).play(frameId, this.hasState(128) ? 20 : 16);
   }

   public final boolean isAnimating() {
      return this.animators[0].isRunning();
   }

   public final boolean isAnimatorRunning(int index) {
      Animator animator;
      return (animator = this.animatorAt(index)) != null && animator.isRunning();
   }

   public final void setValueB(int frameId) {
      this.idleAnimId = (short)frameId;
      int frame = this.lot().frameDataAt(frameId, this.facing);
      this.playIdleAnimation(frame);
   }

   public final void playModeAnimation(int modeIndex) {
      this.setValueB(this.lot().attachmentAt(this.type, modeIndex));
   }

   private boolean isBlocked() {
      if (this.slotMask != 0) {
         return true;
      } else {
         if (this.hasFlag(262144)) {
            Lot lot = this.lot();
            int type = this.type;
            int width = lot.objectWidthAt(type);
            int height = lot.objectHeightAt(type);
            if (this.facing == 1 || this.facing == 3) {
               int swap = width;
               width = height;
               height = swap;
            }

            int tileX = com.ea.sims3.Lot.toTileX(this.posX);
            int tileY = com.ea.sims3.Lot.toTileY(this.posY);

            for (int dx = 0; dx < width; dx++) {
               for (int dy = 0; dy < height; dy++) {
                  if (lot.hasObjectAt(tileX - dx, tileY - dy)) {
                     return true;
                  }
               }
            }
         }

         Sim[] sims = this.screen.getSims();

         for (int index = 0; index < sims.length; index++) {
            if (sims[index].interactionTarget == this) {
               return true;
            }
         }

         return false;
      }
   }

   public final boolean hasFurnitureSlots() {
      return this.lot().altIdAt(this.type) > 0;
   }

   public final boolean hasRotatedFootprint() {
      Lot lot = this.lot();
      this.updateFootprint(false);
      int width = lot.spawnWalkers(this.type);
      this.screen.clearBrokenEntities();
      this.updateFootprint(true);
      return width > 1;
   }

   public final boolean needsFacingAdjust() {
      return this.freeFacing() != this.facing;
   }

   private int freeFacing() {
      Lot lot = this.lot();
      boolean hadFlag;
      if (!(hadFlag = this.hasFlag(524288))) {
         this.updateFootprint(false);
      }

      int facing = this.facing;
      int tileX = com.ea.sims3.Lot.toTileX(this.posX);
      int tileY = com.ea.sims3.Lot.toTileY(this.posY);
      int blockMask = lot.directionMaskAt(this.type, tileX, tileY);

      for (int step = 1; step <= 4; step++) {
         int direction = (facing + step) % 4;
         int mask = com.ea.sims3.Lot.DIRECTION_MASKS[direction];
         if ((blockMask & mask) != 0) {
            facing = direction;
            break;
         }
      }

      if (!hadFlag) {
         this.updateFootprint(true);
      }

      return facing;
   }

   public final void refreshFacing() {
      int facing = this.freeFacing();
      this.updateFootprint(false);
      this.relocate(this.posX, this.posY, facing);
      this.updateFootprint(true);
   }
}
