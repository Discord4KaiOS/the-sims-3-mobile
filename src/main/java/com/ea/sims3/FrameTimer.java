package com.ea.sims3;

import java.util.TimerTask;

public final class FrameTimer extends TimerTask {
   private long lastTick;
   private final GameCanvas canvas;

   public FrameTimer(GameCanvas var1) {
      this.canvas = var1;
      this.lastTick = System.currentTimeMillis();
   }

   public final synchronized void run() {
      if (!GameCanvas.a3(this.canvas)) {
         this.canvas.b4();
         this.canvas.a.destroyApp(true);
      } else {
         if (this.canvas.getWidth() > this.canvas.getHeight()) {
            GameCanvas.b = true;
            GameCanvas.b2 = 1;
         } else {
            GameCanvas.b = false;
            GameCanvas.b2 = 0;
         }

         if (GameCanvas.a4 != GameCanvas.b2) {
            GameCanvas.a2(this.canvas);
            GameCanvas.a4 = GameCanvas.b2;
         }

         if (!GameCanvas.b(this.canvas) && !GameCanvas.c(this.canvas)) {
            GameCanvas.a(this.canvas, true);
            long var1 = System.currentTimeMillis();
            this.canvas.b7((int)(var1 - this.lastTick));
            GameCanvas.a(this.canvas).playQueued();
            GameCanvas.a(this.canvas).update();
            this.lastTick = var1;
            this.canvas.repaint();
         }
      }
   }
}
