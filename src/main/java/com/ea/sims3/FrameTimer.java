package com.ea.sims3;

import java.util.TimerTask;

public final class FrameTimer extends TimerTask {
   private long lastTick;
   private final GameCanvas canvas;

   public FrameTimer(GameCanvas gameCanvas) {
      this.canvas = gameCanvas;
      this.lastTick = System.currentTimeMillis();
   }

   public final synchronized void run() {
      if (!GameCanvas.isRunning(this.canvas)) {
         this.canvas.shutdown();
         this.canvas.app.destroyApp(true);
      } else {
         if (this.canvas.getWidth() > this.canvas.getHeight()) {
            GameCanvas.landscape = true;
            GameCanvas.currentLandscape = 1;
         } else {
            GameCanvas.landscape = false;
            GameCanvas.currentLandscape = 0;
         }

         if (GameCanvas.lastLandscape != GameCanvas.currentLandscape) {
            GameCanvas.repaintTick(this.canvas);
            GameCanvas.lastLandscape = GameCanvas.currentLandscape;
         }

         if (!GameCanvas.isLandscape(this.canvas) && !GameCanvas.isUpdating(this.canvas)) {
            GameCanvas.setUpdating(this.canvas, true);
            long now = System.currentTimeMillis();
            this.canvas.advanceFrame((int)(now - this.lastTick));
            GameCanvas.sound(this.canvas).playQueued();
            GameCanvas.sound(this.canvas).update();
            this.lastTick = now;
            this.canvas.repaint();
         }
      }
   }
}
