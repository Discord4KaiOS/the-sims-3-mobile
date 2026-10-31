package com.ea.sims3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreNotFoundException;

public final class GameCanvas extends Canvas implements SpriteIndex, SoundIndex, Resources, PlayerListener {
   public MonkeyApp a;
   private boolean k;
   public BaseScreen a2;
   public boolean a3;
   public static boolean b = false;
   public static int a4 = -1;
   public static int b2 = -1;
   public PromoImages a5 = null;
   public boolean c = false;
   public ResourceLoader a6 = null;
   private int s;
   public SdkSound a7;
   private Random a8;
   private boolean l;
   private static GameCanvas a9 = null;
   private Timer a10 = null;
   private boolean m = false;
   public static boolean d = false;
   public int c2 = 0;
   private int t;
   private int u;
   private int[] a11;
   private int v;
   public int d2;
   public int e;
   private Object[] a12;
   public int f;
   private static byte[] a13;
   private int w;
   private SdkString[] a14;
   private SdkString[] b3 = new SdkString[10];
   private static final SdkString[] c3 = new SdkString[3];
   private SdkString a15;
   private SdkString[] d3;
   private int x;
   private short[] b4;
   private SdkString b5;
   private short[][] a16;
   private SdkString[] e2;
   private short[] c4;
   private int[] c5 = new int[2];
   private SdkString[] f2 = new SdkString[2];
   private int[] d4 = new int[2];
   private int[] e3 = new int[2];
   private int[] f3 = new int[2];
   private ScreenFade a17 = new ScreenFade();
   private boolean n;
   public boolean e4;
   private int y;
   private int z;
   public int g;
   public int h;
   private int A;
   public boolean f4;
   private static final int[][] a18 = new int[][]{{0, 2048, 2048, 16, 64, 128, 8}};
   private long a19;
   private long b6;
   private Animator a20;
   private Animator b7;
   private boolean o;
   private int[] g2;
   private int B;
   private boolean p;
   public boolean g3;
   public boolean h2;
   public boolean i;
   public int i2;
   public int j;
   private SdkString[] g4 = new SdkString[2];
   private boolean[] a21 = new boolean[2];
   private int C;
   public boolean j2;
   private long c6;
   private long d5;
   private long e5;
   private long f5;
   private long g5;
   private long h3;
   private long i3;
   private long j3;
   private int D;
   private int E;
   private int F;
   public int k2;
   private short[] d6;
   private short[] e6;
   private short[] f6;
   private short[] g6;
   private int G;
   public short a22;
   public Neighborhood a23;
   public Lot a24;
   public int l2;
   public int m2;
   private short[][] b8;
   public int n2;
   public int o2;
   public int p2;
   public int q;
   public int r;

   public final void playerUpdate(Player var1, String var2, Object var3) {
   }

   public final void a12(int var1) {
      if (this.s != var1 && this.s != -1) {
         this.a7.stop();
      }

      this.s = var1;
      if (var1 == -1) {
         this.a7.setLoopCount(1);
      } else {
         this.a7.setLoopCount(-1);
         this.a7.pendingSound = var1;
      }
   }

   private void v2() {
      SdkSound var1 = this.a7;
      this.a7.play(11);
      var1.play(10);
      var1.play(2);
      var1.play(3);
      var1.play(0);
      var1.play(1);
      var1.play(4);
      var1.play(5);
      var1.play(6);
      var1.play(7);
      var1.play(8);
      var1.play(9);
      var1.play(14);
      var1.play(15);
      var1.play(12);
      var1.play(13);
   }

   public final int a() {
      return this.getWidth() >> 1;
   }

   public final int b() {
      return this.getHeight() >> 1;
   }

   public static void a(MonkeyApp var0) {
      a9 = new GameCanvas(var0);
   }

   public static GameCanvas a7() {
      return a9;
   }

   private GameCanvas(MonkeyApp var1) {
      com.ea.sims3.SdkText.a((MIDlet)var1);
      if (this.getWidth() > this.getHeight()) {
         a4 = 1;
         b2 = 1;
      } else {
         a4 = 0;
         b2 = 0;
      }

      this.a = var1;
      this.k = false;
      this.a11 = new int[8];
      this.v = 0;
      this.l = false;
      this.a6 = new ResourceLoader();
      this.a7 = new SdkSound();
      this.a7.setEnabled(false);
      this.a7.currentLoop = 1;
      this.a7.resources = this.a6;
      this.s = -1;
      this.v2();
      this.a8 = new Random();
      com.ea.sims3.SpriteRenderer.b(this.a6);
      com.ea.sims3.SpriteRenderer.c(this.a6);
      com.ea.sims3.SpriteRenderer.a(this.a6);
      this.a20 = new Animator();
      this.b7 = new Animator();
      this.F();
      this.a5 = new PromoImages(this.getWidth(), this.getHeight());
      SdkString var2 = null;

      for (int var3 = 0; var3 < 2; var3++) {
         (var2 = new SdkString(8, 0)).a12(8);

         for (int var4 = 0; var4 != 8; var4++) {
            var2.a(var4, ' ');
         }

         this.g4[var3] = var2;
      }

      this.A();
      this.y();
      this.x();
      if (this.a14 == null) {
         this.a14 = new SdkString[10];

         for (int var6 = 0; var6 < 10; var6++) {
            this.a14[var6] = new SdkString();
         }
      }

      this.d3 = new SdkString[25];

      for (int var7 = 0; var7 < 25; var7++) {
         this.d3[var7] = new SdkString(100, 0);
      }

      this.j2(1);
      this.p2();
      this.B();
   }

   public final synchronized void a10() {
      this.E();
      byte var1 = 5;
      if (this.i2 == -1) {
         var1 = 4;
      }

      this.b2(1, var1);
      this.a5.a4(this.getWidth(), this.getHeight());
      this.a3 = false;
   }

   public final void b4() {
      this.k = false;
      if (this.a2 != null) {
         this.a2.pause();
      }

      this.c3();
      this.a(true);

      for (int var1 = 0; var1 < com.ea.sims3.SoundIndex.b.length; var1++) {
         this.a7.stopAll();
      }
   }

   public final void showNotify() {
      if (!com.ea.sims3.GameScreen.a10) {
         if (this.a3) {
            this.f2();
            this.c2();
         }
      }
   }

   public final void hideNotify() {
      try {
         Thread.sleep(1000L);
      } catch (Exception var2) {
      }

      if (!com.ea.sims3.GameScreen.a10) {
         this.e2();
      }
   }

   public final void c2() {
      this.k = true;
      if (this.a10 != null) {
         this.a10.cancel();
         this.a10 = null;
         m2();
      }

      this.a10 = new Timer();
      this.a10.schedule(new FrameTimer(this), 0L, 15L);
   }

   private void w() {
      try {
         this.repaint();
         if (this.a5 != null) {
            this.a5.a4(this.getWidth(), this.getHeight());
         }

         if (this.a2 != null) {
            if (this.a2 instanceof GameScreen) {
               GameScreen var1 = (GameScreen)this.a2;
               if (com.ea.sims3.GameScreen.a5 != 1) {
                  var1.a10();
                  return;
               }
            } else if (this.a2 instanceof TitleScreen) {
               Object var3 = null;
               if (com.ea.sims3.TitleScreen.a5 == 5) {
                  this.a2.onTick(5);
               }

               if (com.ea.sims3.TitleScreen.a5 == 11) {
                  this.c4[0] = 0;
               }

               if (com.ea.sims3.TitleScreen.a5 == 10) {
                  this.a2.onTick(10);
               }
            }
         }
      } catch (Exception var2) {
      }
   }

   public final void b7(int var1) {
      int var2 = this.h();
      this.t2(var1);
      this.c4(var2);
      if (this.l) {
         m2();
         this.l = false;
      }
   }

   private int h() {
      int[] var1 = this.a11;

      for (int var2 = this.v - 1; var2 >= 0; var2--) {
         if (var1[var2] < 0) {
            this.t = this.t & ~(-var1[var2]);
            if (-var1[var2] == 131072 || -var1[var2] == 262144 || -var1[var2] == 524288) {
               this.u = 0;
            }
         }
      }

      for (int var5 = this.v - 1; var5 >= 0; var5--) {
         if (var1[var5] > 0) {
            this.t = this.t | var1[var5];
            if (var1[var5] == 524288) {
               this.u = 4;
            } else if (var1[var5] == 262144) {
               this.u = this.d2;
            } else if (var1[var5] == 131072) {
               this.u = this.e;
            }
         }
      }

      int var6 = 0;

      for (int var3 = 1; var3 <= 18; var3++) {
         for (int var4 = 0; var4 < this.v; var4++) {
            if (var1[var4] < 0) {
               var6 |= -var1[var4];
            } else if (var6 == 1 << var3 && var1[var4] > 0) {
               var6 &= ~var1[var4];
            }

            var1[var4] = 0;
         }
      }

      this.v = 0;
      return var6;
   }

   public final void d2() {
      com.ea.sims3.SdkSound.muteAll();
      this.k = false;
   }

   public final void e2() {
      this.h2();
      this.g2();
      if (this.a2 != null) {
         this.a2.stop();
      }

      com.ea.sims3.SdkSound.muteAll();
      this.a7.playQueued();
      this.a7.update();
      this.a3 = true;
   }

   public final void f2() {
      d = true;
      this.h2();
      this.g2();
      this.v = 0;
      this.a3 = false;
   }

   private final void t2(int var1) {
      if (d) {
         this.c2++;
      }

      if (this.c2 > 500) {
         if (this.s != -1) {
            this.a12(this.s);
         }

         this.c2 = 0;
         d = false;
      }

      if (var1 > 150) {
         var1 = 150;
      }

      if (this.h != -1) {
         this.e2(this.h, this.A);
         this.h = -1;
         this.A = -1;
      }

      if (this.a2 != null) {
         if (this.n) {
            this.y = this.y + this.z * this.g * (var1 >> 3);
            if (this.z > 0 && this.y >= 16777215) {
               this.y = 16777215;
            } else if (this.z < 0 && this.y <= 0) {
               this.y = 0;
            }
         }

         if (this.a2 != null) {
            try {
               byte var2 = 0;
               if (this.u == 4 && this.d2 != 4 && this.e != 4) {
                  var2 = 4;
                  this.u = 0;
               }

               this.a2.update(this.t, var2);
               this.x2(this.u);
            } catch (Exception var3) {
            }

            try {
               this.y2(var1);
               this.a2.keyPressed(var1);
               return;
            } catch (Exception var4) {
            }
         }
      }
   }

   public final synchronized void paint(Graphics var1) {
      try {
         Graphics var2 = var1;
         com.ea.sims3.SdkText.a(var1);

         try {
            if (this.a2 != null && this.k) {
               this.a2.render(var2);
            }

            if (this.a11()) {
               this.e(var2);
               if (this.z > 0 && this.y >= 16777215 || this.z < 0 && this.y <= 0) {
                  this.e4 = true;
               }
            }
         } catch (Exception var7) {
         }
      } finally {
         this.m = false;
      }
   }

   private int d(int var1) {
      switch (var1) {
         case -8:
            return 524288;
         case -7:
            return 262144;
         case -6:
            return 131072;
         case 35:
            return 1024;
         case 42:
            return 2048;
         case 48:
            return 1;
         case 49:
            return 2;
         case 50:
            return 4;
         case 51:
            return 8;
         case 52:
            return 16;
         case 53:
            return 32;
         case 54:
            return 64;
         case 55:
            return 128;
         case 56:
            return 256;
         case 57:
            return 512;
         default:
            switch (this.getGameAction(var1)) {
               case 1:
                  return 8192;
               case 2:
                  return 32768;
               case 3:
               case 4:
               case 7:
               default:
                  return 0;
               case 5:
                  return 65536;
               case 6:
                  return 16384;
               case 8:
                  return 4096;
            }
      }
   }

   public final void keyPressed(int var1) {
      if (this.c) {
         this.a5.a(var1);
      } else {
         int var2;
         if ((var2 = this.d(var1)) != 0 && this.v < this.a11.length) {
            this.a11[this.v++] = var2;
            this.w2(var2);
         }
      }
   }

   public final void keyRepeated(int var1) {
      this.keyPressed(var1);
   }

   public final void keyReleased(int var1) {
      int var2;
      if ((var2 = this.d(var1)) != 0 && this.v < this.a11.length) {
         this.a11[this.v++] = -var2;
      }
   }

   public final void c4(int var1) {
      this.t &= ~var1;
   }

   public final void g2() {
      this.t = 0;
   }

   public final void h2() {
      this.u = 0;
      this.c4(524288);
   }

   public final void a4(int var1, int var2) {
      if (var1 != this.d2) {
         this.d2 = var1;
         this.b6 = 0L;
      }

      if (var2 != this.e) {
         this.e = var2;
         this.a19 = 0L;
      }
   }

   private static final int e(int var0) {
      if (var0 == 0) {
         return -1;
      } else if (var0 == 1) {
         return 45;
      } else if (var0 == 2) {
         return 36;
      } else if (var0 == 4) {
         return 37;
      } else if (var0 == 8) {
         return 38;
      } else if (var0 == 16) {
         return 39;
      } else if (var0 == 32) {
         return 40;
      } else if (var0 == 64) {
         return 41;
      } else if (var0 == 128) {
         return 49;
      } else if (var0 == 256) {
         return 43;
      } else if (var0 == 512) {
         return 44;
      } else if (var0 == 1024) {
         return 35;
      } else if (var0 == 2048) {
         return 46;
      } else if (var0 == 4096) {
         return 47;
      } else if (var0 == 8192) {
         return 48;
      } else if (var0 == 16384) {
         return 1;
      } else {
         return var0 == 32768 ? 2 : -1;
      }
   }

   private void x() {
      a13 = new byte[2600];
      byte[] var1 = this.a5("/hdr");
      this.f = com.ea.sims3.SdkText.a(var1);
      int var2 = 0;
      int var14;
      int var3 = var14 = com.ea.sims3.SdkText.b2(var2 = 0 + this.i2);
      if (var14 < 0) {
         var3 = -1;
         var14 = 0;
      }

      Object[] var4 = new Object[5];
      Image[] var5 = new Image[5];
      this.a12 = var4;
      String var6 = "/" + com.ea.sims3.SdkText.c(var14);

      try {
         var5[0] = Image.createImage(var6 + "font_sims_white_outline.png");
         var5[1] = Image.createImage(var6 + "font_sims_black.png");
         var5[2] = Image.createImage(var6 + "font_sims_white_smallcaps.png");
         var5[3] = Image.createImage(var6 + "font_the.png");
         var5[4] = Image.createImage(var6 + "font_thesmall.png");
      } catch (IOException var12) {
      }

      byte[] var7 = this.a5(var6 + "font_sims_white_outline.rff");
      var4[0] = com.ea.sims3.SdkText.a(var5[0], var7);
      var7 = this.a5(var6 + "font_sims_black.rff");
      var4[1] = com.ea.sims3.SdkText.a(var5[1], var7);
      var7 = this.a5(var6 + "font_sims_white_smallcaps.rff");
      var4[2] = com.ea.sims3.SdkText.a(var5[2], var7);
      var7 = this.a5(var6 + "font_the.rff");
      var4[3] = com.ea.sims3.SdkText.a(var5[3], var7);
      var7 = this.a5(var6 + "font_thesmall.rff");
      var4[4] = com.ea.sims3.SdkText.a(var5[4], var7);
      a13 = null;
      m2();
      this.d3(var14);
      this.i2 = var3;
      Object[] var8;
      (var8 = new Object[5])[0] = var4[1];
      var8[1] = var4[0];
      var8[2] = var4[0];
      var8[3] = var4[1];
      var8[4] = var4[0];
      this.a5.a(var8);

      try {
         InputStream var9 = this.a6.openStream(52);
         byte[] var10 = new byte[com.ea.sims3.ResourceLoader.getSize(52)];
         var9.read(var10);
         var9.close();
         this.a5.a3(var10);
         m2();
      } catch (IOException var11) {
      }
   }

   public final void d3(int var1) {
      this.i2 = var1;
      com.ea.sims3.SdkText.a13(var1);
      com.ea.sims3.SdkText.a3(2);
      if (this.a9() != null) {
         com.ea.sims3.SdkText.a3(1);
      }

      a2(this.c4, -1);
      a(this.e2, (Object) null);
      this.b5 = null;
      a(this.c5, -1);
      a(this.f2, (Object) null);
   }

   private byte[] a5(String var1) {
      byte[] var2 = null;
      InputStream var3 = null;

      try {
         if ((var3 = this.getClass().getResourceAsStream(var1)) != null) {
            int var4 = var3.read(a13);
            int var5 = 0;

            for (int var6 = 2600; var4 > 0; var4 = var3.read(a13, var5, var6)) {
               var5 += var4;
               var6 -= var4;
            }

            var2 = new byte[var5];
            System.arraycopy(a13, 0, var2, 0, var5);
         }
      } catch (IOException var7) {
      }

      return var2;
   }

   public final void a2(int var1, int var2, int var3, int var4, int var5) {
      if (var1 != -1) {
         SdkString var6 = this.a4(var1);
         com.ea.sims3.SdkText.a(this.a12[var2]);
         this.a(var6, var2, var3, var4, var5);
      }
   }

   public final void a(SdkString var1, int var2, int var3, int var4, int var5) {
      if (var1 != null) {
         com.ea.sims3.SdkText.a(this.a12[var2]);
         com.ea.sims3.SdkText.a12(5);
         if ((var5 & 2) != 0) {
            var4 -= com.ea.sims3.SdkText.c() - com.ea.sims3.SdkText.b() >> 1;
            var5 &= -3;
         }

         com.ea.sims3.SdkText.a(var1, var3, var4, var5);
      }
   }

   public final int a3(int var1) {
      com.ea.sims3.SdkText.a(this.a12[var1]);
      return com.ea.sims3.SdkText.a();
   }

   public final int b2(int var1) {
      com.ea.sims3.SdkText.a(this.a12[var1]);
      return com.ea.sims3.SdkText.c();
   }

   public final SdkString a4(int var1) {
      if (var1 < -2) {
         return this.b3[var1 - -12];
      } else {
         SdkString var2 = this.a14[this.w];
         this.w = (this.w + 1) % 10;
         return var1 <= 3 ? com.ea.sims3.SdkText.a(com.ea.sims3.SdkText.d(), var1, var2) : com.ea.sims3.SdkText.a(var1, var2);
      }
   }

   public final int a2(int var1, int var2) {
      com.ea.sims3.SdkText.a(this.a12[var2]);
      return com.ea.sims3.SdkText.a(this.a4(var1));
   }

   public final int a(SdkString var1, int var2) {
      com.ea.sims3.SdkText.a(this.a12[var2]);
      return com.ea.sims3.SdkText.a(var1);
   }

   public static SdkString a(String var0) {
      return new SdkString(var0);
   }

   public final void a4(int var1, int var2, int var3) {
      SdkString var4 = this.a4(var2);
      this.b3[var1 - -12] = var4.b(this.a4(var3));
   }

   public final void a2(int var1, int var2, int var3, int var4) {
      SdkString var5 = this.a4(var2);
      SdkString[] var6;
      (var6 = c3)[0] = this.a4(var3);
      var6[1] = this.a4(var4);
      this.b3[var1 - -12] = var5.a(var6);
   }

   public final void a2(int var1, int var2, SdkString var3) {
      SdkString var4 = this.a4(var2);
      this.b3[var1 - -12] = var4.b(var3);
   }

   public final void a(int var1, int var2, SdkString var3, SdkString var4) {
      SdkString var5 = this.a4(var2);
      SdkString[] var6 = c3;
      c3[0] = var3;
      var6[1] = var4;
      this.b3[var1 - -12] = var5.a(var6);
   }

   public final void a(int var1, int var2, SdkString var3, SdkString var4, SdkString var5) {
      SdkString var6 = this.a4(var2);
      SdkString[] var7 = c3;
      c3[0] = var3;
      var7[1] = var4;
      var7[2] = var5;
      this.b3[var1 - -12] = var6.a(var7);
   }

   public final void a2(int var1, SdkString var2) {
      this.b3[var1 - -12] = var2;
   }

   public final SdkString a2() {
      this.x++;
      if (this.x >= 25) {
         this.x = 0;
      }

      this.a15 = this.d3[this.x];
      this.a15.a12(0);
      return this.a15;
   }

   public final void e3(int var1) {
      this.a15.a2(this.a4(var1));
   }

   public final void a3(SdkString var1) {
      this.a15.a2(var1);
   }

   public final void f3(int var1) {
      SdkString var2 = this.a15;
      int var3 = this.a15.a4;
      if (var1 == 0) {
         var2.a12(var3 + 1);
         var2.a(var3, '0');
      } else {
         if (var1 < 0) {
            var2.a12(var3 + 1);
            var2.a(var3++, '-');
            var1 = -var1;
         }

         int var4 = 1000000000;
         int var5 = 0;

         for (boolean var6 = false; var4 > 0; var4 /= 10) {
            if ((var5 = var1 / var4) != 0 || var6) {
               var6 = true;
               var2.a12(var3 + 1);
               var2.a(var3++, (char)(48 + var5));
            }

            var1 -= var5 * var4;
         }
      }
   }

   private void u2(int var1) {
      SdkString var2 = this.a15;
      int var3 = this.a15.a4;
      if (var1 == 0) {
         var2.a12(var3 + 1);
         var2.a(var3, '0');
      } else {
         if (var1 < 0) {
            var2.a12(var3 + 1);
            var2.a(var3++, '-');
            var1 = -var1;
         }

         int var4 = 1000000000;
         int var5 = 0;

         for (boolean var6 = false; var4 > 0; var4 /= 10) {
            if ((var5 = var1 / var4) != 0 || var6) {
               var6 = true;
               var2.a12(var3 + 1);
               var2.a(var3++, (char)(48 + var5));
               if (var4 == 1000000000 || var4 == 1000000 || var4 == 1000) {
                  var2.a2(this.a4(31));
                  var3 = var2.a4;
               }
            }

            var1 -= var5 * var4;
         }
      }
   }

   public final void g2(int var1) {
      int var2 = this.x;
      SdkString var3 = this.a15;
      SdkString var4 = this.a2();
      this.u2(Math.abs(var1));
      SdkString var5 = this.a4(30).b(var4);
      if (var1 < 0) {
         var3.a("-");
      }

      var3.a2(var5);
      this.a15 = var3;
      this.x = var2;
   }

   public final void h2(int var1) {
      int var2 = var1 / 60;
      int var3 = var1 - var2 * 60;
      int var4 = var2;
      SdkString var5 = this.a15;
      int var6 = this.a15.a4;
      char var7 = '0';
      if (var2 >= 20) {
         var7 = '2';
         var4 = var2 - 20;
      } else if (var2 >= 10) {
         var7 = '1';
         var4 = var2 - 10;
      }

      var5.a12(var6 + 1);
      var5.a(var6++, var7);
      var5.a12(var6 + 1);
      var5.a(var6++, (char)(48 + var4));
      var5.a12(var6 + 1);
      var5.a(var6++, ':');
      int var8 = var3 / 10;
      var5.a12(var6 + 2);
      var5.a(var6++, (char)(48 + var8));
      var5.a(var6, (char)(48 + (var3 - var8 * 10)));
   }

   private void y() {
      this.b4 = new short[255];
      this.b4[0] = 0;
      this.a16 = new short[20][10];

      for (int var1 = 0; var1 < 20; var1++) {
         this.a16[var1][0] = 0;
      }

      this.e2 = new SdkString[20];
      this.c4 = new short[20];
   }

   public final int a(int var1, int var2, int var3) {
      com.ea.sims3.SdkText.a(this.a12[var2]);
      if (var1 == -1) {
         return 0;
      } else {
         this.b5 = this.a4(var1).a2();
         if (var1 == 89) {
            String var4 = this.a.getAppProperty("MIDlet-Version");
            this.b5 = this.b5.b(var4);
         }

         com.ea.sims3.SdkText.a(this.b5, this.b4, var3, (short)124);
         return this.b4[0];
      }
   }

   public final int c() {
      return this.b4[0];
   }

   public final void b2(int var1, int var2, int var3, int var4) {
      com.ea.sims3.SdkText.a(this.a12[var1]);
      com.ea.sims3.SdkText.a12(5);
      com.ea.sims3.SdkText.a(this.b5, this.b4, 1, this.b4[0], var2, var3, var4);
   }

   public final void a(int var1, int var2, int var3, int var4, int var5, int var6) {
      com.ea.sims3.SdkText.a(this.a12[var1]);
      com.ea.sims3.SdkText.a12(5);
      if ((var4 & 2) != 0) {
         int var7 = com.ea.sims3.SdkText.c() + com.ea.sims3.SdkText.b();
         int var8;
         int var9 = (var8 = var6 * var7) >> 1;
         var3 -= var9;
         var4 ^= 2;
      }

      var6 = Math.min(this.b4[0] - var5, var6);
      com.ea.sims3.SdkText.a(this.b5, this.b4, var5 + 1, var6, var2, var3, var4);
   }

   private int a(int var1, int var2, int var3, int var4) {
      com.ea.sims3.SdkText.a(this.a12[var3]);
      this.c4[var1] = (short)var2;
      if (var2 == -1) {
         return 0;
      } else {
         this.e2[var1] = this.a4(var2).a2();
         com.ea.sims3.SdkText.a(this.e2[var1], this.a16[var1], var4, (short)124);
         return this.a16[var1][0];
      }
   }

   public final void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var2 != this.c4[var1]) {
         this.a(var1, var2, var3, var4);
         this.c4[var1] = (short)var2;
      }

      com.ea.sims3.SdkText.a(this.a12[var3]);
      com.ea.sims3.SdkText.a12(5);
      com.ea.sims3.SdkText.a(this.e2[var1], this.a16[var1], 1, this.a16[var1][0], var5, var6, var7);
   }

   public final void a2(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.a(var1, this.a4(var2), var3, var4, var5, var6, var7);
   }

   public final void a(Graphics var1, SdkString var2, int var3, int var4, int var5, int var6, int var7) {
      if (this.a(var2, var3) <= var6) {
         if ((var7 & 1) != 0) {
            var4 += var6 >> 1;
         }

         this.a(var2, var3, var4, var5, var7);
      } else {
         int var9 = var1.getClipX();
         int var10 = var1.getClipY();
         int var11 = var1.getClipWidth();
         int var12 = var1.getClipHeight();
         int var13 = this.a2(26, var3);
         var1.setClip(var4, var10, var6 - var13, var12);
         this.a(var2, var3, var4, var5, var7 & -10 | 4);
         var1.setClip(var9, var10, var11, var12);
         this.a2(26, var3, var4 + var6, var5, var7 & -6 | 8);
      }
   }

   public final void i2() {
      for (int var1 = 0; var1 < 2; var1++) {
         this.c5[var1] = -1;
         this.f2[var1] = null;
      }
   }

   private void b2(int var1, int var2, int var3) {
      if (var1 < 2) {
         this.e3[var1] = 0;
         this.c5[var1] = var3;
         this.f2[var1] = null;
         if (var3 != -1) {
            this.d4[var1] = this.a2(var3, var2);
            this.f3[var1] = 0;
         }
      }
   }

   private void b(int var1, int var2, SdkString var3) {
      if (var1 < 2) {
         this.e3[var1] = 0;
         this.c5[var1] = -1;
         this.f2[var1] = var3;
         if (var3 != null) {
            this.d4[var1] = this.a(var3, var2);
            this.f3[var1] = 0;
         }
      }
   }

   public final void i2(int var1) {
      for (int var2 = 0; var2 < 2; var2++) {
         if (this.c5[var2] != -1 || this.f2[var2] != null) {
            this.e3[var2] = this.e3[var2] + var1;
            if (this.e3[var2] > 1500) {
               long var3 = this.e3[var2] - 1500;
               this.f3[var2] = (int)(var3 * 20L >> 10);
               int var5 = this.d4[var2] + 20;
               if (this.f3[var2] >= var5) {
                  this.f3[var2] = this.f3[var2] - var5;
                  this.e3[var2] = this.e3[var2] - (var5 << 10) / 20;
               }
            }
         }
      }
   }

   public final void a(int var1, Graphics var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (this.c5[var1] != var3 || this.d4[var1] != this.a2(var3, var4)) {
         this.b2(var1, var4, var3);
      }

      if (this.d4[var1] <= var7) {
         if ((var8 & 1) != 0) {
            var5 += var7 >> 1;
         } else if ((var8 & 8) != 0) {
            var5 += var7;
         }

         this.a2(var3, var4, var5, var6, var8);
      } else {
         int var9 = var2.getClipX();
         int var10 = var2.getClipY();
         int var11 = var2.getClipWidth();
         int var12 = var2.getClipHeight();
         var2.setClip(var5, var10, var7, var12);
         var8 = var8 & -10 | 4;
         this.a2(var3, var4, var5 - this.f3[var1], var6, var8);
         this.a2(var3, var4, var5 - this.f3[var1] + this.d4[var1] + 20, var6, var8);
         var2.setClip(var9, var10, var11, var12);
      }
   }

   public final void a(int var1, Graphics var2, SdkString var3, int var4, int var5, int var6, int var7, int var8) {
      if (this.f2[var1] != var3 || this.d4[var1] != this.a(var3, var4)) {
         this.b(var1, var4, var3);
      }

      if (this.d4[var1] <= var7) {
         if ((var8 & 1) != 0) {
            var5 += var7 >> 1;
         } else if ((var8 & 8) != 0) {
            var5 += var7;
         }

         this.a(var3, var4, var5, var6, var8);
      } else {
         int var9 = var2.getClipX();
         int var10 = var2.getClipY();
         int var11 = var2.getClipWidth();
         int var12 = var2.getClipHeight();
         var2.setClip(var5, var10, var7, var12);
         var8 = var8 & -10 | 4;
         this.a(var3, var4, var5 - this.f3[var1], var6, var8);
         this.a(var3, var4, var5 - this.f3[var1] + this.d4[var1] + 20, var6, var8);
         var2.setClip(var9, var10, var11, var12);
      }
   }

   public final void b(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (this.c5[var1] != var2) {
         this.b(var1, var3, com.ea.sims3.SdkText.a(var2, null));
         this.c5[var1] = var2;
         this.e3[var1] = 1500;
      }

      SdkString var7 = this.f2[var1];
      int var8 = this.a(var7, var3);
      if ((var6 & 1) != 0) {
         var4 -= var8 >> 1;
      } else if ((var6 & 8) != 0) {
         var4 -= var8;
      }

      var6 = var6 & -10 | 4;
      SdkString var9;
      (var9 = this.a2()).a12(1);
      short var10 = var7.a4;
      int var11;
      if ((var11 = this.f3[var1] << 1) > var8 + 20) {
         this.e3[var1] = 1500;
         this.f3[var1] = 0;
         var11 = 0;
      }

      int var12;
      int var13 = (var12 = var11 - 10 - 20) + 20;
      int var14 = 0;

      for (int var15 = 0; var15 < var10; var15++) {
         var9.a(0, var7.a2(var15));
         if (var14 >= var12 && var14 <= var13) {
            int var16 = com.ea.sims3.FixedMath.div(var14 - var12 << 16, 1310720);
            int var18 = com.ea.sims3.FixedMath.cos(com.ea.sims3.FixedMath.mul(411775, var16) - 205887) * 6;
            this.a(var9, var3, var4 + var14, var5 - (var18 >> 16) - 6, var6);
         } else {
            this.a(var9, var3, var4 + var14, var5, var6);
         }

         var14 += this.a(var9, var3);
      }
   }

   public final void j2() {
      this.y = 16777215;
      this.z = -1;
      this.n = true;
      this.e4 = false;
   }

   public final void k2() {
      this.y = 0;
      this.z = 1;
      this.n = true;
      this.e4 = false;
   }

   public final void l2() {
      this.y = 16777215;
      this.z = -1;
      this.n = false;
      this.e4 = true;
   }

   public final boolean a11() {
      return this.n && !this.e4;
   }

   public final boolean b5() {
      return this.a11() && this.z == -1;
   }

   private void e(Graphics var1) {
      this.a17.setViewport(0, 0, this.getWidth(), this.getHeight());
      this.a17.setBlending(66);
      this.a17.setColor(this.y);
      this.a17.render(var1);
   }

   public final void b2(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.a17.setBlending(var2);
      this.a17.setViewport(var4, var5, var6, var7);
      this.a17.setColor(var3);
      this.a17.render(var1);
   }

   public final void a(Graphics var1) {
      this.a17.setViewport(0, 0, this.getWidth(), this.getHeight() - 15);
      this.a17.setBlending(66);
      this.a17.setColor(6316128);
      this.a17.render(var1);
   }

   public static final void m2() {
      System.gc();
      Thread.yield();

      try {
         Thread.sleep(15L);
      } catch (InterruptedException var1) {
      }
   }

   private void a(boolean var1) {
      if (var1) {
         m2();
         this.l = false;
      } else {
         this.l = true;
      }
   }

   public final GameScreen a6() {
      return this.a2 instanceof GameScreen ? (GameScreen)this.a2 : null;
   }

   public final TitleScreen a9() {
      return this.a2 instanceof TitleScreen ? (TitleScreen)this.a2 : null;
   }

   public final void b2(int var1, int var2) {
      this.h = var1;
      this.A = var2;
   }

   private void e2(int var1, int var2) {
      try {
         boolean var3 = this.a6() != null && var1 == 2;
         this.D = -1;
         if (this.a2 != null) {
            this.a2.pause();
         }

         this.l2();
         if (!var3) {
            this.a2 = null;
         }

         this.a(true);
         if (!var3) {
            switch (var1) {
               case 1:
                  this.a2 = new TitleScreen(this);
                  break;
               case 2:
                  this.a2 = new GameScreen(this);
            }
         }

         this.a2.setLanguage(var2);
      } catch (Exception var4) {
      }

      this.g2();
      this.h2();
      this.repaint();
   }

   public final int b(int var1, int var2) {
      int var3 = var2 + 1 - var1;
      return var1 + this.a8.nextInt(var3);
   }

   public final int d() {
      return this.b(0, 99);
   }

   public final void n2() {
      this.v2(400);
   }

   private void v2(int var1) {
      if (this.p) {
         this.a7.vibrate(var1);
      }
   }

   private void w2(int var1) {
      for (int var2 = 0; var2 < a18.length; var2++) {
         int[] var3;
         int var4 = (var3 = a18[var2]).length - 1;
         int var5 = var3[0];
         int var6;
         if ((var6 = var3[var5 + 1]) == var1) {
            if (++var5 == var4) {
               var5 = 0;
               switch (var2) {
                  case 0:
                     this.f4 = !this.f4;
               }
            }
         } else {
            var5 = 0;
         }

         var3[0] = var5;
      }
   }

   private static int a(int var0, int var1, int var2, long var3, long var5) {
      if (var3 >= var5) {
         return var2;
      } else if (var3 < 0L) {
         return var1;
      } else {
         int var7 = 0;
         switch (var0) {
            case 0:
            default:
               return (int)(var1 + var3 * (var2 - var1) / var5);
            case 1:
               int var8 = (int)(var5 >> 1);
               if (var3 < var8) {
                  var7 = com.ea.sims3.FixedMath.mul(com.ea.sims3.FixedMath.sinShifted(com.ea.sims3.FixedMath.div(com.ea.sims3.FixedMath.mul((int)var3, 102944), var8)), 72089);
               } else {
                  int var9;
                  int var10 = com.ea.sims3.FixedMath.mul(var9 = com.ea.sims3.FixedMath.div((int)var3 - var8, (int)var5 - var8), 617662);
                  int var11 = 6554 - com.ea.sims3.FixedMath.mul(6554, var9);
                  var7 = com.ea.sims3.FixedMath.mul(com.ea.sims3.FixedMath.cos(var10), var11) + 65536;
               }
               break;
            case 2:
               var7 = com.ea.sims3.FixedMath.smoothStep(0, (int)var5, (int)var3);
         }

         return var1 + (var7 * (var2 - var1) >> 16);
      }
   }

   private void x2(int var1) {
      if (!this.a11() && var1 != 0 && !this.a20.isRunning()) {
         if (var1 == this.e) {
            this.h2();
            this.o = false;
            this.a20.play(675, 16);
            this.a20.frame = 1;
            this.b7.play(677, 16);
            this.b7.frame = 1;
         } else {
            if (var1 == this.d2) {
               this.h2();
               this.o = true;
               this.a20.play(675, 16);
               this.a20.frame = 1;
               this.b7.play(677, 16);
               this.b7.frame = 1;
            }
         }
      }
   }

   private void y2(int var1) {
      if (this.a11()) {
         this.a19 = 0L;
         this.b6 = 0L;
      } else {
         if (this.a19 < 400L) {
            this.a19 += var1;
         }

         if (this.b6 < 400L) {
            this.b6 += var1;
         }
      }

      if (this.a20.isRunning()) {
         this.a20.advance(var1);
         this.b7.advance(var1);
         if (!this.a20.isRunning()) {
            int var2 = this.o ? this.d2 : this.e;
            if (this.a2 != null) {
               this.a2.update(0, var2);
            }
         }
      }
   }

   public final void b(Graphics var1) {
      this.a(var1, 0);
   }

   public final void a(Graphics var1, int var2) {
      int var3 = this.getHeight();
      int var4 = this.getWidth();
      int var5 = com.ea.sims3.SpriteRenderer.c(674, 0);

      for (int var6 = 0; var6 < var4; var6 += var5) {
         com.ea.sims3.SpriteRenderer.drawFrame(var1, 674, 0, var6, var3 + var2);
      }

      if (!this.a11()) {
         this.b(var1, var2);
      }
   }

   private void b(Graphics var1, int var2) {
      int var3 = e(this.e);
      int var4 = e(this.d2);
      if (b) {
         if (var3 >= 0) {
            SdkString var14 = this.a4(var3);
            int var17 = a(1, 15, 0, this.b6, 400L);
            int var20;
            int var23 = (var20 = this.getHeight() + var17 + var2) - 7;
            int var26 = Math.max(25, this.a(var14, 0));
            int var28;
            int var31;
            int var34 = (var31 = (var28 = this.getWidth() - 11) - var26 + 4) + var28 >> 1;
            if (this.a20.isRunning() && this.o) {
               com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.a20, var31, var20);
               com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.b7, var28, var20);
               a(var1, var31 + 2, var20, var28 - var31 - 4, true);
               this.a(var14, 0, var34, var23, 3);
            } else {
               com.ea.sims3.SpriteRenderer.drawFrame(var1, 677, 0, var28, var20);
               com.ea.sims3.SpriteRenderer.drawFrame(var1, 675, 0, var31, var20);
               a(var1, var31 + 2, var20, var28 - var31 - 4, false);
               this.a(var14, 0, var34, var23, 3);
            }
         }

         if (var4 >= 0) {
            SdkString var15 = this.a4(var4);
            int var18 = a(1, 15, 0, this.b6, 400L);
            int var21;
            int var24 = (var21 = 15 + var18 + var2) - 7;
            int var27 = Math.max(25, this.a(var15, 0));
            int var29;
            int var32;
            int var35 = (var32 = (var29 = this.getWidth() - 11) - var27 + 4) + var29 >> 1;
            if (this.a20.isRunning() && this.o) {
               com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.a20, var32, var21);
               com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.b7, var29, var21);
               a(var1, var32 + 2, var21, var29 - var32 - 4, true);
               this.a(var15, 0, var35, var24, 3);
               return;
            }

            com.ea.sims3.SpriteRenderer.drawFrame(var1, 677, 0, var29, var21);
            com.ea.sims3.SpriteRenderer.drawFrame(var1, 675, 0, var32, var21);
            a(var1, var32 + 2, var21, var29 - var32 - 4, false);
            this.a(var15, 0, var35, var24, 3);
         }
      } else {
         if (var3 >= 0) {
            SdkString var5 = this.a4(var3);
            int var6 = a(1, 15, 0, this.a19, 400L);
            int var7;
            int var8 = (var7 = this.getHeight() + var6 + var2) - 7;
            int var9 = Math.max(25, this.a(var5, 0));
            int var11 = 11 + var9 - 4;
            int var12 = 11 + var11 >> 1;
            if (this.a20.isRunning() && !this.o) {
               com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.a20, 11, var7);
               com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.b7, var11, var7);
               a(var1, 13, var7, var11 - 11 - 4, true);
               this.a(var5, 0, var12, var8, 3);
            } else {
               com.ea.sims3.SpriteRenderer.drawFrame(var1, 677, 0, var11, var7);
               com.ea.sims3.SpriteRenderer.drawFrame(var1, 675, 0, 11, var7);
               a(var1, 13, var7, var11 - 11 - 4, false);
               this.a(var5, 0, var12, var8, 3);
            }
         }

         if (var4 >= 0) {
            SdkString var13 = this.a4(var4);
            int var16 = a(1, 15, 0, this.b6, 400L);
            int var19;
            int var22 = (var19 = this.getHeight() + var16 + var2) - 7;
            int var25 = Math.max(25, this.a(var13, 0));
            int var10;
            int var30;
            int var33 = (var30 = (var10 = this.getWidth() - 11) - var25 + 4) + var10 >> 1;
            if (!this.a20.isRunning() || !this.o) {
               com.ea.sims3.SpriteRenderer.drawFrame(var1, 677, 0, var10, var19);
               com.ea.sims3.SpriteRenderer.drawFrame(var1, 675, 0, var30, var19);
               a(var1, var30 + 2, var19, var10 - var30 - 4, false);
               this.a(var13, 0, var33, var22, 3);
               return;
            }

            com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.a20, var30, var19);
            com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.b7, var10, var19);
            a(var1, var30 + 2, var19, var10 - var30 - 4, true);
            this.a(var13, 0, var33, var22, 3);
         }
      }
   }

   private static void a(Graphics var0, int var1, int var2, int var3, boolean var4) {
      int var5 = var4 ? 1 : 0;
      int var6 = var0.getClipX();
      int var7 = var0.getClipY();
      int var8 = var0.getClipWidth();
      int var9 = var0.getClipHeight();
      var0.setClip(var1, var7, var3, var9);
      com.ea.sims3.SpriteRenderer.drawFrame(var0, 676, var5, var1, var2);
      var0.setClip(var6, var7, var8, var9);
   }

   public final void o2() {
      this.B = 0;
   }

   public final boolean a13(int var1) {
      return this.a2(var1, null, null, 0);
   }

   public final boolean a2(int var1, int[] var2, int[] var3, int var4) {
      ResourceLoader var6 = this.a6;
      int var7 = 0;

      int var8;
      for (var8 = this.B; var8 << 1 < this.g2.length && var7 < 2; var8++) {
         if ((this.g2[(var8 << 1) + 1] & var1) != 0) {
            int var9 = 1;

            while (!com.ea.sims3.SpriteRenderer.a(var6, this.g2[var8 << 1], var2, var3, var4) && var9 < 2) {
               var9++;
               m2();
            }

            if (var9 >= 2) {
               throw new Error("OOM");
            }

            var7++;
         }
      }

      this.B = var8;
      return var8 << 1 == this.g2.length;
   }

   public final void j2(int var1) {
      this.a(var1, null, null, 0);
   }

   public final void a(int var1, int[] var2, int[] var3, int var4) {
      this.o2();

      while (!this.a2(var1, var2, var3, var4)) {
      }
   }

   public final void c2(int var1, int var2) {
      int[] var3 = this.g2;

      for (int var4 = 0; var4 << 1 < var3.length; var4++) {
         if ((var3[(var4 << 1) + 1] & var1) != 0) {
            int var5 = var3[var4 << 1];
            if (var2 == -1) {
               for (int var6 = 0; var6 < 4; var6++) {
                  com.ea.sims3.SpriteRenderer.a5(var5, var6);
               }
            } else {
               com.ea.sims3.SpriteRenderer.a5(var5, var2);
            }
         }
      }

      m2();
   }

   public static final void a(Object[] var0, Object var1) {
      for (int var2 = 0; var2 < var0.length; var2++) {
         var0[var2] = var1;
      }
   }

   public static final void a3(byte[] var0, int var1) {
      for (int var2 = 0; var2 < var0.length; var2++) {
         var0[var2] = (byte)var1;
      }
   }

   public static final void a2(short[] var0, int var1) {
      for (int var2 = 0; var2 < var0.length; var2++) {
         var0[var2] = (short)var1;
      }
   }

   public static final void a(int[] var0, int var1) {
      for (int var2 = 0; var2 < var0.length; var2++) {
         var0[var2] = var1;
      }
   }

   public static final void a(int[][] var0, int var1) {
      for (int var2 = 0; var2 < var0.length; var2++) {
         a(var0[var2], var1);
      }
   }

   public static final void a(short[][] var0, int var1) {
      for (int var2 = 0; var2 < var0.length; var2++) {
         a2(var0[var2], var1);
      }
   }

   public static final int a(int var0, int[] var1) {
      return b(var0, var1, 0);
   }

   public static final int a(int var0, short[] var1) {
      return a(var0, var1, 0);
   }

   public static final int a(int var0, byte[] var1) {
      return a(var0, var1, 0);
   }

   public static final int a(Object var0, Object[] var1) {
      return a(var0, var1, 0);
   }

   private static int b(int var0, int[] var1, int var2) {
      for (int var3 = var2; var3 < var1.length; var3++) {
         if (var1[var3] == var0) {
            return var3;
         }
      }

      return -1;
   }

   public static final int a(int var0, short[] var1, int var2) {
      for (int var3 = var2; var3 < var1.length; var3++) {
         if (var1[var3] == var0) {
            return var3;
         }
      }

      return -1;
   }

   public static final int a(int var0, byte[] var1, int var2) {
      for (int var3 = var2; var3 < var1.length; var3++) {
         if (var1[var3] == var0) {
            return var3;
         }
      }

      return -1;
   }

   private static int a(Object var0, Object[] var1, int var2) {
      for (int var3 = var2; var3 < var1.length; var3++) {
         if (var1[var3] == var0) {
            return var3;
         }
      }

      return -1;
   }

   public static final int b(int var0, short[] var1) {
      return e(var0, var1, 0);
   }

   private static int e(int var0, short[] var1, int var2) {
      for (int var3 = var2; var3 < var1.length; var3++) {
         if ((var1[var3] & var0) != 0) {
            return var3;
         }
      }

      return -1;
   }

   public static final int b(int var0, byte[] var1, int var2) {
      int var3 = 0;

      for (int var4 = 0; var4 < var1.length; var4++) {
         if (var1[var4] != var2 && var3++ == var0) {
            return var4;
         }
      }

      return -1;
   }

   public static final int b(int var0, short[] var1, int var2) {
      int var3 = 0;

      for (int var4 = 0; var4 < var1.length; var4++) {
         if (var1[var4] != var2 && var3++ == var0) {
            return var4;
         }
      }

      return -1;
   }

   public static final int c(int var0, short[] var1, int var2) {
      int var3 = 0;

      for (int var4 = 0; var4 < var1.length; var4++) {
         if ((var1[var4] & var2) != 0 && var3++ == var0) {
            return var4;
         }
      }

      return -1;
   }

   public static final int a(int var0, int[] var1, int var2) {
      int var3 = 0;

      for (int var4 = 0; var4 < var1.length; var4++) {
         if ((var1[var4] & var2) != 0 && var3++ == var0) {
            return var4;
         }
      }

      return -1;
   }

   public static final int c(int var0, short[] var1) {
      int var2 = 0;

      for (int var3 = 0; var3 < var1.length; var3++) {
         if (var1[var3] == var0) {
            var2++;
         }
      }

      return var2;
   }

   public static final int b(int var0, byte[] var1) {
      int var2 = 0;

      for (int var3 = 0; var3 < var1.length; var3++) {
         if (var1[var3] == var0) {
            var2++;
         }
      }

      return var2;
   }

   public static final int b(int var0, int[] var1) {
      return c(var0, var1, 0);
   }

   private static int c(int var0, int[] var1, int var2) {
      int var3 = 0;

      for (int var4 = var2; var4 < var1.length; var4++) {
         if ((var1[var4] & var0) != 0) {
            var3++;
         }
      }

      return var3;
   }

   public static final int d(int var0, short[] var1) {
      return d(var0, var1, 0);
   }

   public static final int d(int var0, short[] var1, int var2) {
      int var3 = 0;

      for (int var4 = var2; var4 < var1.length; var4++) {
         if ((var1[var4] & var0) != 0) {
            var3++;
         }
      }

      return var3;
   }

   public static void b2(short[] var0, int var1) {
      a(var0, var1, -1);
   }

   public static void a(short[] var0, int var1, int var2) {
      var0[1] = (short)var1;
      var0[2] = 0;
      var0[3] = 0;
      var0[4] = (short)var2;
      var0[0] = 0;
   }

   public static void c2(short[] var0, int var1) {
      short var2 = var0[0];
      var0[5 + var2] = (short)var1;
      var0[0] = (short)(var2 + 1);
   }

   public static boolean a3(short[] var0, int var1) {
      int var2 = var0[0] + 5;

      for (int var3 = 5; var3 < var2; var3++) {
         if (var1 == var0[var3]) {
            return true;
         }
      }

      return false;
   }

   public static void a(short[] var0, short[] var1) {
      System.arraycopy(var1, 0, var0, 0, 5 + var1[0]);
   }

   public static void a2(short[] var0) {
      short var1;
      if ((var1 = var0[4]) > 0) {
         short var2 = var0[2];
         int var3 = var0[3];
         if (var2 < var3) {
            var3 = var2;
         } else if (var2 >= var3 + var1) {
            var3 = Math.max(0, var2 - var1 + 1);
         }

         var0[3] = (short)var3;
      }
   }

   public final boolean b3(short[] var1, int var2) {
      short var3 = var1[0];
      short var4 = var1[2];
      if ((var2 & 8196) != 0) {
         this.c4(8196);
         var1[2] = (short)((var4 + var3 - 1) % var3);
      }

      if ((var2 & 16640) != 0) {
         this.c4(16640);
         var1[2] = (short)((var4 + 1) % var3);
      }

      if (var1[2] != var4) {
         a2(var1);
         if (this.a6() != null) {
            this.a7.pendingSound = 0;
         }

         return true;
      } else {
         return false;
      }
   }

   public final boolean c3(short[] var1, int var2) {
      short var3 = var1[0];
      short var4 = var1[2];
      if ((var2 & 32784) != 0) {
         this.c4(32784);
         var1[2] = (short)((var4 + var3 - 1) % var3);
      }

      if ((var2 & 65600) != 0) {
         this.c4(65600);
         var1[2] = (short)((var4 + 1) % var3);
      }

      if (var1[2] != var4) {
         a2(var1);
         this.i2();
         if (this.a6() != null) {
            this.a7.pendingSound = 1;
         }

         return true;
      } else {
         return false;
      }
   }

   public static void d2(short[] var0, int var1) {
      for (int var2 = 5; var2 < var0.length; var2++) {
         if (var0[var2] == var1) {
            var0[2] = (short)(var2 - 5);
            return;
         }
      }
   }

   private void z() {
      this.p = true;
      this.g3 = true;
      this.h2 = true;
      this.i = true;
      this.j2 = false;
      this.c6 = 0L;
      this.d5 = 0L;
      this.e5 = 0L;
      this.f5 = 0L;
      this.i2 = -1;
      this.C();
   }

   private void A() {
      RecordStore var1 = null;
      ByteArrayInputStream var2 = null;
      DataInputStream var3 = null;

      try {
         byte[] var4 = (var1 = RecordStore.openRecordStore("sim3set", false)).getRecord(1);
         var2 = new ByteArrayInputStream(var4);
         int var5 = (var3 = new DataInputStream(var2)).readInt();
         int var6 = var3.readInt();
         if (var5 == 86 && var6 == 2) {
            this.p = var3.readBoolean();
            this.g3 = var3.readBoolean();
            this.h2 = var3.readBoolean();
            this.i = var3.readBoolean();
            this.j2 = var3.readBoolean();
            this.c6 = var3.readLong();
            this.d5 = var3.readLong();
            this.e5 = var3.readLong();
            this.f5 = var3.readLong();
            this.i2 = var3.readByte();

            for (int var7 = 0; var7 != 2; var7++) {
               boolean var8 = false;
               SdkString var10 = this.g4[var7];

               for (int var11 = 0; var11 < 8; var11++) {
                  char var9;
                  if ((var9 = var3.readChar()) == ' ') {
                     var8 = true;
                  } else if (!var8) {
                     var10.a12(var11 + 1);
                     var10.a(var11, var9);
                  }
               }

               if (!var8) {
                  var10.a12(8);
               }

               this.a21[var7] = var3.readBoolean();
            }
         } else {
            this.z();
            this.c3();
         }
      } catch (RecordStoreNotFoundException var13) {
         this.z();
      } catch (Exception var14) {
      }

      try {
         var3.close();
         var2.close();
         if (var1 != null) {
            var1.closeRecordStore();
         }
      } catch (Exception var12) {
      }
   }

   public final boolean c3() {
      try {
         RecordStore.deleteRecordStore("sim3set");
      } catch (Exception var10) {
      }

      boolean var1 = false;
      RecordStore var2 = null;
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      DataOutputStream var4 = new DataOutputStream(var3);

      try {
         var4.writeInt(86);
         var4.writeInt(2);
         var4.writeBoolean(this.p);
         var4.writeBoolean(this.g3);
         var4.writeBoolean(this.h2);
         var4.writeBoolean(this.i);
         var4.writeBoolean(this.j2);
         var4.writeLong(this.c6);
         var4.writeLong(this.d5);
         var4.writeLong(this.e5);
         var4.writeLong(this.f5);
         var4.writeByte(this.i2);

         for (int var5 = 0; var5 != 2; var5++) {
            SdkString var6 = this.g4[var5];

            for (int var7 = 0; var7 != 8; var7++) {
               char var8 = ' ';
               if (var7 < var6.a4) {
                  var8 = var6.a2(var7);
               }

               var4.writeChar(var8);
            }

            var4.writeBoolean(this.a21[var5]);
         }

         byte[] var12 = var3.toByteArray();
         if ((var2 = RecordStore.openRecordStore("sim3set", true)).getNumRecords() == 0) {
            var2.addRecord(var12, 0, var12.length);
         } else {
            var2.setRecord(1, var12, 0, var12.length);
         }
      } catch (Exception var11) {
         var1 = true;
      }

      try {
         var4.close();
         var3.close();
         var2.closeRecordStore();
      } catch (Exception var9) {
         var1 = true;
      }

      return !var1;
   }

   public final void p2() {
      this.C = 0;
      this.u2();
      this.a23.h2();
      this.a24.b4();
   }

   private void B() {
      RecordStore var1 = null;
      ByteArrayInputStream var2 = null;
      DataInputStream var3 = null;

      try {
         byte[] var4 = (var1 = RecordStore.openRecordStore("sim3dat" + this.j, false)).getRecord(1);
         var2 = new ByteArrayInputStream(var4);
         if ((var3 = new DataInputStream(var2)).readInt() == 86) {
            this.C = var3.readInt();
            this.a23.a2(var3);
            this.a24.a2(var3);
            this.l2 = 0;
         } else {
            this.p2();
            this.d3();
         }
      } catch (Exception var7) {
         this.p2();
         this.d3();
      }

      try {
         var3.close();
         var2.close();
         if (var1 != null) {
            var1.closeRecordStore();
         }
      } catch (Exception var6) {
      }
   }

   public final boolean d3() {
      String var1 = "sim3dat" + this.j;

      try {
         RecordStore.deleteRecordStore(var1);
      } catch (Exception var9) {
      }

      this.c6 = this.c6 | this.g5;
      this.d5 = this.d5 | this.h3;
      this.e5 = this.e5 | this.i3;
      this.f5 = this.f5 | this.j3;
      this.c3();
      boolean var2 = false;
      RecordStore var3 = null;
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      DataOutputStream var5 = new DataOutputStream(var4);

      try {
         var5.writeInt(86);
         var5.writeInt(this.C);
         this.a23.a(var5);
         this.a24.a(var5);
         byte[] var6 = var4.toByteArray();
         if ((var3 = RecordStore.openRecordStore(var1, true)).getNumRecords() == 0) {
            var3.addRecord(var6, 0, var6.length);
         } else {
            var3.setRecord(1, var6, 0, var6.length);
         }
      } catch (Exception var8) {
         var2 = true;
      }

      try {
         var5.close();
         var4.close();
         if (var3 != null) {
            var3.closeRecordStore();
         }
      } catch (Exception var7) {
         var2 = true;
      }

      return !var2;
   }

   public final int c(int var1) {
      if (var1 == 79) {
         return this.a7.enabled ? 87 : 88;
      } else if (var1 == 80) {
         return this.p ? 87 : 88;
      } else if (var1 == 81) {
         return this.g3 ? 87 : 88;
      } else if (var1 == 82) {
         return this.h2 ? 87 : 88;
      } else if (var1 == 83) {
         return this.i ? 87 : 88;
      } else {
         return -1;
      }
   }

   public final int e() {
      return this.g4[this.j].a4;
   }

   public final void q2() {
      SdkString var1;
      (var1 = this.g4[this.j]).a12(0);
   }

   public final void a(int var1, char var2) {
      this.g4[this.j].a(var1, var2);
   }

   public final void b(int var1, char var2) {
      SdkString var3;
      int var4 = (var3 = this.g4[this.j]).a4;
      var3.a12(var3.a4 + 1);

      while (var1 < var4) {
         char var5 = var3.a2(var4 - 1);
         var3.a(var4, var5);
         var4--;
      }

      var3.a(var1, var2);
   }

   public final void k2(int var1) {
      SdkString var2;
      int var3 = (var2 = this.g4[this.j]).a4 - 1;

      while (var1 != var3) {
         char var4 = var2.a2(var1 + 1);
         var2.a(var1, var4);
         var1++;
      }

      var2.a(var1, ' ');
      var2.a12(var3);
   }

   public final void l2(int var1) {
      this.j = var1;
      if (this.e3()) {
         this.B();
      }
   }

   public final SdkString b2() {
      return this.g4[this.j];
   }

   public final SdkString b3(int var1) {
      return this.g4[var1];
   }

   public final boolean e3() {
      return this.a21[this.j];
   }

   public final boolean b8(int var1) {
      return this.a21[var1];
   }

   private void C() {
      for (int var1 = 0; var1 < 2; var1++) {
         this.m2(var1);
      }
   }

   public final void m2(int var1) {
      this.a21[var1] = false;
   }

   public final void r2() {
      this.a21[this.j] = true;
      this.C = 0;
      this.c3();
      this.a23.g2();
      this.d3();
   }

   public final void s2() {
      this.B();
      this.l2 = 0;
   }

   public final boolean c5(int var1) {
      return (1 << var1 & this.C) != 0;
   }

   public final void n2(int var1) {
      this.C |= 1 << var1;
   }

   public final void t2() {
      this.j2 = true;
      this.c3();
   }

   private void D() {
      this.j2 = false;
      this.c6 = 0L;
      this.d5 = 0L;
      this.e5 = 0L;
      this.f5 = 0L;
   }

   public final void u2() {
      this.g5 = 0L;
      this.h3 = 0L;
      this.i3 = 0L;
      this.j3 = 0L;
   }

   public final boolean d4(int var1) {
      if (var1 < 64) {
         long var4 = 1L << var1;
         return ((this.c6 | this.g5) & var4) != 0L;
      } else {
         long var2 = 1L << var1 - 64;
         return ((this.d5 | this.h3) & var2) != 0L;
      }
   }

   public final void o2(int var1) {
      if (var1 < 64) {
         long var4 = 1L << var1;
         this.g5 |= var4;
      } else {
         long var2 = 1L << var1 - 64;
         this.h3 |= var2;
      }
   }

   public final int f() {
      long var1 = this.c6 | this.g5;
      long var3 = this.d5 | this.h3;
      int var5 = 0;

      for (int var6 = 0; var6 != 64; var6++) {
         var5 = (int)((int)(var5 + (var1 >> var6 & 1L)) + (var3 >> var6 & 1L));
      }

      return var5;
   }

   public final boolean e4(int var1) {
      if (var1 < 64) {
         long var4 = 1L << var1;
         return ((this.e5 | this.i3) & var4) != 0L;
      } else {
         long var2 = 1L << var1 - 64;
         return ((this.f5 | this.j3) & var2) != 0L;
      }
   }

   public final void p2(int var1) {
      this.o2(var1);
      if (var1 < 64) {
         long var4 = 1L << var1;
         this.i3 |= var4;
      } else {
         long var2 = 1L << var1 - 64;
         this.j3 |= var2;
      }
   }

   public final int g() {
      long var1 = this.e5 | this.i3;
      long var3 = this.f5 | this.j3;
      int var5 = 0;

      for (int var6 = 0; var6 != 64; var6++) {
         var5 = (int)((int)(var5 + (var1 >> var6 & 1L)) + (var3 >> var6 & 1L));
      }

      return var5;
   }

   public final void q2(int var1) {
      if (this.D == -1) {
         this.D = this.b(920, 955);
         this.E = this.getWidth() << 16;
         this.F = this.a2(this.D, 0);
      } else {
         this.E -= var1 * 4915;
         if (this.E < -(this.F << 16)) {
            this.D = -1;
         }
      }
   }

   public final void c(Graphics var1) {
      com.ea.sims3.SpriteRenderer.a(var1, 77);
      var1.fillRect(0, 0, this.getWidth(), this.getHeight());
      this.a2.b(var1, 0, 0, 0, this.getWidth(), this.getHeight());
      if (this.D != -1) {
         int var2 = this.getHeight() - 40;
         this.a2(this.D, 0, this.E >> 16, var2, 20);
      }
   }

   private void E() {
      this.a22 = (short)((this.getHeight() - 15 - 22) / 15);
      short[] var1;
      a(var1 = new short[12], 60, this.a22);
      c2(var1, 79);
      c2(var1, 80);
      c2(var1, 81);
      c2(var1, 82);
      c2(var1, 83);
      this.e6 = new short[var1[0] + 5];
      a(this.e6, var1);
      c2(var1, 3);
      c2(var1, 85);
      this.d6 = var1;
      a(var1 = new short[12], 61, this.a22);
      c2(var1, 62);
      c2(var1, 90);
      c2(var1, 94);
      c2(var1, 96);
      c2(var1, 98);
      c2(var1, 100);
      c2(var1, 105);
      this.f6 = var1;
      this.k2 = -1;
   }

   public final void r2(int var1) {
      short[] var2 = null;
      byte var3 = -1;
      char var4 = 4;
      short var5 = 16;
      switch (var1) {
         case -1:
         default:
            break;
         case 0:
            var2 = this.d6;
            break;
         case 1:
            var2 = this.e6;
            break;
         case 2:
            var5 = 256;
            var4 = 512;
            var3 = 0;
            this.a2.l2(86, 85);
            break;
         case 3:
            var2 = this.f6;
            break;
         case 4:
            var5 = 0;
            var3 = 3;
            break;
         case 5:
            var4 = '\u8000';
            var5 = 16384;
      }

      if (this.k2 == -1 && var2 != null) {
         var2[2] = 0;
      }

      this.g6 = var2;
      this.G = var3;
      this.k2 = var1;
      this.a4(var4, var5);
   }

   public final void d(Graphics var1) {
      BaseScreen var2 = this.a2;
      switch (this.k2) {
         case -1:
            return;
         case 0:
            var2.a(var1, this.d6, 1);
            return;
         case 1:
            var2.a(var1, this.e6, 1);
            return;
         case 2:
            var2.b(var1);
            return;
         case 3:
            var2.a(var1, this.f6, 1);
            return;
         case 4:
            var2.c(var1);
            return;
      }
   }

   public final void d2(int var1, int var2) {
      if (this.g6 != null) {
         if (var2 != 16 && (var1 & 4128) == 0) {
            this.b3(this.g6, var1);
         } else {
            this.h2();
            this.c4(4128);
            int var3 = 5 + this.g6[2];
            this.z2(this.g6[var3]);
         }
      }

      if (this.k2 == 4) {
         this.a2.j2(var1);
      } else if (this.k2 == 2) {
         if (var2 == 256) {
            this.h2();
            this.C();
            this.D();
            this.c3();
            this.p2();
            this.d3();
            this.r2(this.G);
            this.a9().d3(60);
         } else if (var2 == 512) {
            this.h2();
            this.r2(this.G);
         }
      }

      if (var2 == 4) {
         this.h2();
         this.r2(this.G);
      }
   }

   private void z2(int var1) {
      BaseScreen var2 = this.a2;
      switch (var1) {
         case 3:
            this.r2(5);
            return;
         case 62:
            var2.m2(89, 62);
            this.r2(4);
            return;
         case 79:
            this.a7.setEnabled(!this.a7.enabled);
            if (this.a7.enabled) {
               if (this.a9() != null) {
                  this.a12(this.s);
                  return;
               }

               this.a7.pendingSound = 3;
               return;
            }
            break;
         case 80:
            this.p = !this.p;
            if (this.p) {
               this.v2(400);
            }

            this.c3();
            return;
         case 81:
            this.g3 = !this.g3;
            this.c3();
            return;
         case 82:
            this.h2 = !this.h2;
            this.c3();
            return;
         case 83:
            this.i = !this.i;
            this.c3();
            return;
         case 85:
            this.r2(2);
            return;
         case 90:
            var2.m2(91, 90);
            this.r2(4);
            return;
         case 94:
            var2.m2(95, 94);
            this.r2(4);
            return;
         case 96:
            var2.m2(97, 96);
            this.r2(4);
            return;
         case 98:
            var2.m2(99, 98);
            this.r2(4);
            return;
         case 100:
            var2.m2(101, 100);
            this.r2(4);
            return;
         case 105:
            var2.m2(106, 105);
            this.r2(4);
      }
   }

   private void F() {
      this.G();
      this.a24 = new Lot(this);
      this.a23 = new Neighborhood(this);
      this.l2 = 0;
      this.m2 = -1;
      this.r = -1;
   }

   public final void s2(int var1) {
      this.l2 = var1;
      this.m2 = -1;
   }

   public final void a(Sim var1) {
      Sim var2 = this.a6().a14;
      this.n2 = var2.c;
      this.o2 = var2.d;
      if (var1 != null) {
         this.p2 = var1.c;
         this.q = var1.d;
         this.r = var1.b;
      } else {
         this.r = -1;
      }

      this.l2 = -1;
   }

   private static final short a(DataInputStream var0) throws IOException {
      return var0.readShort();
   }

   private void G() {
      ResourceLoader var1 = this.a6;

      try {
         DataInputStream var2;
         byte var3;
         short[][] var4 = new short[var3 = (var2 = new DataInputStream(var1.openStream(63))).readByte()][];

         for (int var5 = 0; var5 < var3; var5++) {
            byte var6;
            short[] var7 = new short[(var6 = var2.readByte()) << 1];
            // decompiled as byte; the table holds more entries than a byte can index
            int var8 = 0;

            for (int var9 = 0; var9 < var6; var9++) {
               var7[var8] = var2.readByte();
               var7[var8 + 1] = a(var2);
               var8 += 2;
            }

            var4[var5] = var7;
         }

         byte var11;
         int[] var12 = new int[(var11 = var2.readByte()) << 1];
         // decompiled as byte; the table holds more entries than a byte can index
         int var13 = 0;

         for (int var14 = 0; var14 < var11; var14++) {
            var12[var13] = a(var2);
            var12[var13 + 1] = var2.readInt();
            var13 += 2;
         }

         this.g2 = var12;
         var2.close();
         this.b8 = var4;
      } catch (IOException var10) {
      }
   }

   public final short[] a16(int var1) {
      return this.b8[var1];
   }

   public static boolean a3(GameCanvas var0) {
      return var0.k;
   }

   public static void a2(GameCanvas var0) {
      var0.w();
   }

   public static boolean b(GameCanvas var0) {
      return var0.a3;
   }

   public static boolean c(GameCanvas var0) {
      return var0.m;
   }

   public static boolean a(GameCanvas var0, boolean var1) {
      return var0.m = var1;
   }

   public static SdkSound a(GameCanvas var0) {
      return var0.a7;
   }
}
