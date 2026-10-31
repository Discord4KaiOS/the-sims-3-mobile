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

   public final boolean advance(int var1) {
      if ((this.flags & 2) == 0) {
         return false;
      } else {
         this.frameTime += var1;
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

   public final void play(int var1, int var2) {
      boolean var3;
      if (!(var3 = (var2 & 16) != 0) && this.animId == var1) {
         if (this.frame >= SpriteRenderer.frameCount(this.animId)) {
            this.frame = 0;
         } else if (this.frame < 0) {
            this.frame = SpriteRenderer.frameCount(this.animId) - 1;
         }
      } else {
         this.animId = var1;
         if ((this.flags & 8) != 0) {
            this.frame = SpriteRenderer.frameCount(this.animId) - 1;
         } else {
            this.frame = 0;
         }

         this.frameTime = 0;
      }

      this.flags = 3 | var2 & 4;
   }

   public final void draw(Graphics var1, int var2, int var3) {
      if (this.animId >= 0) {
         SpriteRenderer.drawFrame(var1, this.animId, this.frame, var2, var3);
      }
   }

   private int frameCount() {
      return this.animId == -1 ? 0 : SpriteRenderer.frameCount(this.animId);
   }

   public final int frameDuration() {
      return SpriteRenderer.e(this.animId, this.frame);
   }

   public final boolean matchesFrame(int[] var1, int var2) {
      return SpriteRenderer.a2(var1, this.animId, this.frame, var2);
   }

   public final boolean isRunning() {
      return (this.flags & 2) != 0;
   }

   public final boolean isLooping() {
      return (this.flags & 4) != 0;
   }

   public final void setRunning(boolean var1) {
      if (var1) {
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
