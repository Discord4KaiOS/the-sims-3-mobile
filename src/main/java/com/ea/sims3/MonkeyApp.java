package com.ea.sims3;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public final class MonkeyApp extends MIDlet {
   private boolean started = false;
   private GameCanvas canvas;
   private Display display;

   public MonkeyApp() {
      this.started = false;
      this.display = Display.getDisplay(this);
      GameCanvas.create(this);
      this.canvas = GameCanvas.instance();
   }

   public final void startApp() {
      GameCanvas midletCanvas = this.canvas;
      this.canvas.setFullScreenMode(true);
      this.display.setCurrent(midletCanvas);
      midletCanvas.setFullScreenMode(true);
      if (!this.started) {
         midletCanvas.startGame();
         this.started = true;
         midletCanvas.startTimer();
      }

      if (midletCanvas.suspended) {
         midletCanvas.resumeGame();
      }
   }

   public final void pauseApp() {
      if (this.canvas != null) {
         this.canvas.suspendGame();
      }
   }

   public final void destroyApp(boolean unconditional) {
      this.notifyDestroyed();
   }
}
