package com.ea.sims3;

import java.io.ByteArrayInputStream;
import javax.microedition.lcdui.Display;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

public final class m implements PlayerListener {
   private static int e = com.ea.sims3.e.h();
   public boolean a = false;
   public int a2 = -1;
   public int b = 1;
   public int c = 0;
   public boolean b2 = true;
   public Player a3;
   public s a4 = null;
   public Display a5;
   public static boolean c2 = false;
   public int d = -2;

   public m() {
      this.a = true;
   }

   public final void a12(int var1) {
      if (f.a11() && var1 < 1 && var1 != -1) {
         k.a("SDKSound::setCurrentLoop::count: " + var1 + " will result in undefined behavior", 4);
      }

      this.b = var1;
   }

   public final void b7(int var1) {
      if (this.a2 != var1) {
         byte[] var2 = this.a14(var1);
         if (this.a3 != null) {
            this.a3.removePlayerListener(this);
            this.a3.close();
         }

         this.a3 = this.a2(var2);

         try {
            this.a3.realize();
            this.a3.prefetch();
            this.a3.addPlayerListener(this);
         } catch (MediaException var4) {
         } catch (IllegalStateException var5) {
         } catch (SecurityException var6) {
         }
      }
   }

   public final void a10() {
      b4();
   }

   private void d3(int var1) {
      try {
         if (this.b2) {
            if (this.a11() && this.c == 1) {
               return;
            }

            this.b7(var1);
            this.e3(var1);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   private void e3(int var1) {
      if (this.a3 != null) {
         try {
            this.a3.setLoopCount(this.b);
            this.a3.setMediaTime(0L);
         } catch (Exception var3) {
         }

         try {
            VolumeControl var2;
            if (this.a3 != null && (var2 = (VolumeControl)this.a3.getControl("VolumeControl")) != null) {
               var2.setLevel(50);
            }

            this.a3.start();
            this.a2 = var1;
            return;
         } catch (Exception var4) {
         }
      }
   }

   public static void b4() {
      c2 = true;
   }

   private void f2() {
      this.c2();
   }

   public final void c2() {
      if (this.a2 != -1 && this.a3 != null) {
         try {
            if (this.a3.getState() == 400) {
               this.a3.stop();
            }

            this.a3.getState();
         } catch (Exception var2) {
            var2.printStackTrace();
            return;
         }

         this.a2 = -1;
      }
   }

   public final void c4(int var1) {
      var1 = var1 * com.ea.sims3.e.j() / com.ea.sims3.e.i();
      this.a5 = Display.getDisplay(r.a7().a);
      this.a5.vibrate(var1);
   }

   public final void a(boolean var1) {
      this.b2 = var1;
      if (!var1) {
         b4();
      }
   }

   private byte[] a14(int var1) {
      return this.a4.a14(var1);
   }

   private Player a2(byte[] var1) {
      byte var2 = var1[0];
      String var3 = null;
      switch (var2) {
         case 35:
            var3 = "audio/amr";
            break;
         case 77:
            var3 = "audio/midi";
            break;
         case 82:
            if (this.a) {
               var3 = "audio/x-wav";
            } else {
               var3 = "audio/wav";
            }
      }

      if (var3 == null) {
         return null;
      } else {
         Player var4 = null;

         try {
            ByteArrayInputStream var5;
            var4 = Manager.createPlayer(var5 = new ByteArrayInputStream(var1), var3);
            var5.close();
         } catch (Exception var6) {
            var6.printStackTrace();
         }

         return var4;
      }
   }

   private boolean a11() {
      return this.a2 == 1;
   }

   public final void d2() {
      if (this.d != -2) {
         this.d3(this.d);
         this.d = -2;
      }
   }

   public final void e2() {
      if (c2) {
         this.f2();
      }

      c2 = false;
   }

   public final void playerUpdate(Player var1, String var2, Object var3) {
      if (var2 == "endOfMedia" && this.b != -1) {
         this.a2 = -1;
      }
   }
}
