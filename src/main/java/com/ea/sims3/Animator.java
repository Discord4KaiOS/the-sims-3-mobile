package com.ea.sims3;

import javax.microedition.lcdui.Graphics;

public final class Animator {
   public int animId = -1;
   public int frame;
   private int frameTime;
   private int flags;

   public Animator() {
      this.animId = -1;
      this.frame = 0;
      this.frameTime = 0;
      this.flags = 0;
   }

   public final boolean advance(int deltaMs) {
      if ((this.flags & 2) == 0) {
         return false;
      } else {
         this.frameTime += deltaMs;
         if (this.frameTime > SpriteRenderer.frameDuration(this.animId, this.frame)) {
            if ((this.flags & 8) != 0) {
               this.stepBackward();
               return true;
            } else {
               this.stepForward();
               return true;
            }
         } else {
            return false;
         }
      }
   }

   private void stepForward() {
      this.frameTime = this.frameTime - SpriteRenderer.frameDuration(this.animId, this.frame);
      if (this.frameTime < 0) {
         this.frameTime = 0;
      }

      this.frame++;
      if (this.frame >= SpriteRenderer.frameCount(this.animId)) {
         if ((this.flags & 4) != 0) {
            this.frame = 0;
            return;
         }

         this.frame = SpriteRenderer.frameCount(this.animId) - 1;
         this.flags &= -3;
      }
   }

   private void stepBackward() {
      this.frameTime = this.frameTime - SpriteRenderer.frameDuration(this.animId, this.frame);
      if (this.frameTime < 0) {
         this.frameTime = 0;
      }

      this.frame--;
      if (this.frame <= 0) {
         if ((this.flags & 4) != 0) {
            this.frame = SpriteRenderer.frameCount(this.animId) - 1;
            return;
         }

         this.frame = 0;
         this.flags &= -3;
      }
   }

   public final void play(int newAnimId, int playFlags) {
      boolean preserveFrame;
      if (!(preserveFrame = (playFlags & 16) != 0) && this.animId == newAnimId) {
         if (this.frame >= SpriteRenderer.frameCount(this.animId)) {
            this.frame = 0;
         } else if (this.frame < 0) {
            this.frame = SpriteRenderer.frameCount(this.animId) - 1;
         }
      } else {
         this.animId = newAnimId;
         if ((this.flags & 8) != 0) {
            this.frame = SpriteRenderer.frameCount(this.animId) - 1;
         } else {
            this.frame = 0;
         }

         this.frameTime = 0;
      }

      this.flags = 3 | playFlags & 4;
   }

   public final void draw(Graphics gfx, int x, int y) {
      if (this.animId >= 0) {
         SpriteRenderer.drawFrame(gfx, this.animId, this.frame, x, y);
      }
   }

   private int frameCount() {
      return this.animId == -1 ? 0 : SpriteRenderer.frameCount(this.animId);
   }

   public final int frameDuration() {
      return SpriteRenderer.countSpecialParts(this.animId, this.frame);
   }

   public final boolean matchesFrame(int[] hitMask, int x) {
      return SpriteRenderer.hitTest(hitMask, this.animId, this.frame, x);
   }

   public final boolean isRunning() {
      return (this.flags & 2) != 0;
   }

   public final boolean isLooping() {
      return (this.flags & 4) != 0;
   }

   public final void setRunning(boolean running) {
      if (running) {
         this.flags |= 2;
      } else {
         this.flags &= -3;
      }
   }

   public final void rewind() {
      this.frame = Math.max(0, this.frameCount() - 1);
      if (!this.isLooping()) {
         this.setRunning(false);
      }
   }
}
