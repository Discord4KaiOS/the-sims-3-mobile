package com.ea.sims3;

import java.util.TimerTask;

public final class n extends TimerTask {
   private long a;
   private final r a2;

   public n(r var1) {
      this.a2 = var1;
      this.a = System.currentTimeMillis();
   }

   public final synchronized void run() {
      if (!r.a3(this.a2)) {
         this.a2.b4();
         this.a2.a.destroyApp(true);
      } else {
         if (this.a2.getWidth() > this.a2.getHeight()) {
            r.b = true;
            r.b2 = 1;
         } else {
            r.b = false;
            r.b2 = 0;
         }

         if (r.a4 != r.b2) {
            r.a2(this.a2);
            r.a4 = r.b2;
         }

         if (!r.b(this.a2) && !r.c(this.a2)) {
            r.a(this.a2, true);
            long var1 = System.currentTimeMillis();
            this.a2.b7((int)(var1 - this.a));
            r.a(this.a2).d2();
            r.a(this.a2).e2();
            this.a = var1;
            this.a2.repaint();
         }
      }
   }
}
