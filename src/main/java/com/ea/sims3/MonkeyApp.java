package com.ea.sims3;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public final class MonkeyApp extends MIDlet {
   private boolean a = false;
   private r a2;
   private Display a3;

   public MonkeyApp() {
      this.a = false;
      this.a3 = Display.getDisplay(this);
      r.a(this);
      this.a2 = r.a7();
   }

   public final void startApp() {
      r var1 = this.a2;
      this.a2.setFullScreenMode(true);
      this.a3.setCurrent(var1);
      var1.setFullScreenMode(true);
      if (!this.a) {
         var1.a10();
         this.a = true;
         var1.c2();
      }

      if (var1.a3) {
         var1.f2();
      }
   }

   public final void pauseApp() {
      if (this.a2 != null) {
         this.a2.e2();
      }
   }

   public final void destroyApp(boolean var1) {
      this.notifyDestroyed();
   }
}
