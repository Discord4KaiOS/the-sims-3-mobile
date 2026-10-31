package com.ea.sims3;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Stack;
import javax.microedition.lcdui.Graphics;

public final class GameScreen extends BaseScreen implements Runnable {
   private int[] c3 = new int[10];
   public static int a5;
   private int m2;
   private int n;
   private int o;
   private int p;
   private int q;
   private byte a6;
   private boolean e2;
   private int r;
   private int s;
   private int t;
   private int u;
   private int v;
   private short a7;
   private int w;
   private Thread a8;
   private int x;
   private int y;
   private int z;
   private int[] d2 = new int[32];
   private int[] e3 = new int[32];
   private byte b4;
   private byte[] a9;
   private boolean f2;
   private boolean g2;
   public int b5;
   private Animator c4;
   private Animator d3;
   private Animator e4;
   private int A;
   private int B;
   private int C;
   private int D;
   private int E;
   public static boolean a10 = false;
   private short b6;
   private short c5;
   private int F;
   private int G;
   private int H;
   private Entity c6;
   private int I;
   private int J;
   private int K;
   private int L;
   private boolean h2;
   private int M;
   private Animator f3;
   private int N;
   private int O;
   private boolean i2;
   private boolean j2;
   private boolean k2;
   private boolean l2;
   private boolean m3;
   private int P;
   private int Q;
   private int R;
   private int S;
   private int T;
   private int U;
   private boolean n2;
   public Entity a11;
   private boolean o2;
   private Entity[] a12;
   private Sim[] a13;
   public Sim a14;
   private Stack a15;
   public boolean b7;
   public boolean c7;
   private boolean p2;
   private boolean q2;
   private short d4;
   private short[] b8;
   private short[] c8;
   private short[] d5;
   public Entity b9;
   private boolean r2;
   private short[] e5;
   private int V;
   private boolean s2;
   private boolean t2;
   private short[] f4;
   private boolean u2;
   private short[] g3;
   private int W;
   private int X;
   private Entity d6;
   private int Y;
   private int Z;
   private int aa;
   private int ab;
   private int ac;
   private int ad;
   private int ae;
   private int af;
   private int ag;
   public int c9;
   private int ah;
   private boolean v2;
   private boolean w2;
   public boolean d7;
   private Animator[] a16;
   private int ai;
   private int aj;
   private int ak;
   private int al;
   private int am;
   private int an;
   private short[] h3;
   private int ao;
   private int ap;
   private static final short[] i3 = new short[]{14, 15, 12, 13};
   private static final short[] j3 = new short[]{14, 15};
   private int aq;
   private int[] f5;
   private Entity[] b10;
   private int[] g4;
   private int[] h4;
   public Neighborhood a17;
   private Lot a18;
   private int ar;
   private int as;
   private Animator g5;
   private int at;
   private int au;
   private int av;
   private int aw;
   private boolean x2;
   private int ax;
   private int ay;
   private int az;
   private int aA;
   private int aB;
   private int aC;
   private Entity e6;
   private int aD;
   private Entity f6;
   private int aE;
   private int aF;
   private int aG;
   private int aH;
   private Animator[] b11;
   private int[][] a19;
   private int aI;
   private int aJ;
   private int aK;
   private int aL;
   private int aM;
   private int aN;
   private int[] i4 = new int[4];
   private int[] j4 = new int[4];
   private byte[] b12 = new byte[4];
   private int aO;
   private int aP;
   private int aQ;
   private int aR;
   private int aS;
   private int aT;
   private Animator h5;
   private Animator i5;
   private int aU;
   private int aV;
   private int aW;
   private Animator j5;
   private static final byte[] c10 = new byte[]{2, 2, 2, 0, 0, 2, 2, 1};
   private static final byte[] d8 = new byte[]{0, 1, -1, -2, 2, -1, 1, 0};
   private byte[][] a20;
   private byte[] e7;
   private short[][] a21;
   private int aX;
   private int aY;
   private int aZ;
   private int ba;
   private Animator k3;
   private int bb;
   private int bc;
   private int bd;
   private int be;
   private int bf;
   private int bg;
   private int bh;
   private int bi;
   private int bj;
   private int bk;
   private int bl;
   private int bm;
   private short[] k4;
   private SdkString[] a22;

   public GameScreen(GameCanvas var1) {
      super(var1);
   }

   public final boolean a11() {
      return this.a6 == 0;
   }

   public final boolean b5() {
      return this.a6 == 2;
   }

   private boolean g3() {
      return this.a6 == 1;
   }

   private void k2(int var1) {
      super.canvas.q2(var1);
      if (this.a8 == null) {
         this.a8 = new Thread(this);
         this.x = 0;
         this.a8.start();
      } else {
         this.x = 0;
      }
   }

   public final void run() {
      while (this.x != 2) {
         if (this.x != 0) {
            try {
               synchronized (this) {
                  this.wait(1000L);
               }
            } catch (InterruptedException var4) {
            }
         } else {
            this.x = 1;

            try {
               this.r2();
            } catch (Exception var5) {
            }
         }
      }
   }

   private void r2() {
      GameCanvas var1 = super.canvas;
      switch (this.w) {
         case 0:
            this.w++;
            return;
         case 1:
            Lot var9 = this.a18;
            this.a18.b2(this.s, this.t, 320, 320);
            this.b7 = true;
            this.c7 = true;
            boolean var12 = true;
            if (this.a6 == 2) {
               this.M();
            } else if (this.a6 == 1) {
               this.L();
               if (this.a13().length > 1) {
                  var12 = false;
               }
            } else {
               this.K();
            }

            if (var12) {
               Sim var13 = this.a14;
               int var14 = this.a14.c;
               int var18 = var13.d;
               this.K = var9.a2(var14, var18);
               this.L = var9.b(var14, var18);
            }

            this.I = this.K;
            this.J = this.L;
            this.I();
            this.J();
            this.w++;
            var1.o2();
            return;
         case 2:
            int var8 = 8;
            switch (this.a6) {
               case 0:
               default:
                  var8 = 24;
                  Entity var11;
                  if ((var11 = this.b4(10)) != null) {
                     if (var11.a4 == 46) {
                        var8 = 24 | 134217728;
                     }

                     if (var11.a4 == 48) {
                        var8 |= 268435456;
                     }

                     if (var11.a4 == 50) {
                        var8 |= 536870912;
                     }

                     if (var11.a4 == 52) {
                        var8 |= 1073741824;
                     }
                  }
                  break;
               case 1:
                  if (var1.m2 == 39) {
                     var8 = 8 | 131072;
                  } else if (var1.m2 == 41) {
                     var8 = 8 | 262144;
                  }
                  break;
               case 2:
                  var8 = 40;
            }

            if (var1.a13(var8)) {
               this.w++;
               return;
            }
            break;
         case 3:
            ResourceLoader var7 = var1.a6;
            Lot var10 = this.a18;
            if (this.b5()) {
               int[] var5 = var10.a15(0);
               boolean var4 = false;
               int[] var6 = var10.a15(1);
               com.ea.sims3.SpriteRenderer.a(var7, 38, var5, var6, 0);
               com.ea.sims3.SpriteRenderer.a(var7, 35, var5, var6, 0);
               var6 = var10.a15(2);
               com.ea.sims3.SpriteRenderer.a(var7, 38, var5, var6, 1);
               com.ea.sims3.SpriteRenderer.a(var7, 35, var5, var6, 1);
               var6 = var10.a15(3);
               com.ea.sims3.SpriteRenderer.a(var7, 38, var5, var6, 2);
               com.ea.sims3.SpriteRenderer.a(var7, 35, var5, var6, 2);
               var6 = var10.a15(4);
               com.ea.sims3.SpriteRenderer.a(var7, 38, var5, var6, 3);
               com.ea.sims3.SpriteRenderer.a(var7, 35, var5, var6, 3);
               this.v2();
               this.w++;
               return;
            }

            this.t2();

            while (this.b4 > 0) {
               this.u2();
            }

            this.w++;
            return;
         case 4:
            short[] var2 = null;
            short var3 = 0;
            if (this.b5()) {
               var2 = i3;
               var3 = 3000;
            } else if (var1.m2 == 39) {
               var2 = j3;
               var3 = 8000;
            } else if (var1.m2 != -1) {
               var2 = i3;
               var3 = 10000;
            }

            this.h3 = var2;
            this.ao = var3;
            this.w++;
            return;
         case 5:
            this.a10();
            this.onTick(this.q);
            if (this.b5()) {
               this.a2((byte)1);
            } else {
               this.a2((byte)0);
            }

            this.x = 2;
            var1.g = 197379;
            var1.k2();
      }
   }

   private void s2() {
      this.y = 0;
      this.z = 0;
      this.b4 = 0;
      if (!this.b5()) {
         if (this.a9 == null) {
            this.a9 = new byte[4];
         }

         com.ea.sims3.GameCanvas.a3(this.a9, -1);
      }
   }

   private void b(Sim var1) {
      if (!this.b5()) {
         this.b4++;
         int var2 = this.c();
         this.a9[var2] = (byte)var1.b;
      }
   }

   private void t2() {
      for (int var1 = 0; var1 < 4; var1++) {
         byte var2;
         if ((var2 = this.a9[var1]) != -1 && this.a7(var2).f2 == -1) {
            this.y = var1;
            this.z = var2;
            this.a18.a2(var2, this.d2, this.e3);
            super.canvas.o2();
            return;
         }
      }
   }

   private int c() {
      return com.ea.sims3.GameCanvas.a(-1, this.a9);
   }

   private void u2() {
      GameCanvas var1 = super.canvas;
      Neighborhood var2 = this.a17;
      int var3 = this.y;
      int var4 = this.z;
      int var5 = var2.i(var4, 0) == 0 ? 512 : 256;
      com.ea.sims3.GameCanvas.m2();
      if (var1.a2(var5, this.d2, this.e3, var3)) {
         int var6 = this.a3(var4);
         com.ea.sims3.SpriteRenderer.a(var1.a6, var6, this.d2, this.e3, var3);
         this.a7(var4).f2 = var3;
         this.z = -1;
         this.y = var3 + 1;
         this.b4--;
         if (var4 == 0) {
            this.v2();
         }

         if (this.b4 > 0) {
            this.t2();
         }
      }
   }

   private void v2() {
      this.a18.a2(0, this.d2, this.e3);
      int var1 = this.a17.i(0, 0) == 0 ? 4096 : 8192;
      super.canvas.a(var1, this.d2, this.e3, 0);
   }

   private int a3(int var1) {
      Neighborhood var2 = this.a17;
      int var3 = this.a17.i(var1, 2);
      if (var2.i(var1, 0) == 0) {
         if (var3 == 0) {
            return 78;
         } else if (var3 == 1) {
            return 79;
         } else {
            return var3 == 2 ? 80 : 81;
         }
      } else if (var3 == 0) {
         return 70;
      } else if (var3 == 1) {
         return 71;
      } else {
         return var3 == 2 ? 72 : 73;
      }
   }

   public final void a10() {
      GameCanvas var1 = super.canvas;
      Lot var2 = this.a18;
      this.r = com.ea.sims3.SpriteRenderer.d(587, 0) + com.ea.sims3.SpriteRenderer.d(586, 0);
      this.s = 0;
      this.t = 0;
      this.u = var1.getWidth() - this.s;
      this.v = var1.getHeight() - 15 - this.t - this.r + 5;
      Tile var3 = this.a18.a8;
      this.a18.a8.setLooping(true);
      var3.reset();
      var2.b2(this.s, this.t, this.u, this.v);
      this.b7 = true;
      this.c7 = true;
      Sim var5 = this.a14;
      int var6 = this.a14.c;
      int var7 = var5.d;
      this.K = var2.a2(var6, var7);
      this.L = var2.b(var6, var7);
      this.I = this.K;
      this.J = this.L;
      this.I();
      this.J();
      var3.setLooping(true);
      var3.reset();
   }

   public final void setLanguage(int var1) {
      GameCanvas var2 = super.canvas;
      Neighborhood var3 = super.canvas.a23;
      super.canvas.a23.e2();
      if (super.leftArrowAnim == null) {
         super.leftArrowAnim = new Animator();
         super.rightArrowAnim = new Animator();
      }

      a5 = 0;
      this.m2 = 0;
      this.n = 0;
      this.o = 0;
      this.p = -1;
      this.e2 = false;
      this.r = com.ea.sims3.SpriteRenderer.d(587, 0) + com.ea.sims3.SpriteRenderer.d(586, 0);
      this.s = 0;
      this.t = 0;
      this.u = var2.getWidth() - this.s;
      this.v = var2.getHeight() - 15 - this.t - this.r + 5;
      this.a7 = (short)(this.v / 30);
      this.a8 = null;
      this.x = 0;
      this.s2();
      this.f2 = false;
      this.g2 = false;
      this.b5 = 0;
      this.A = var3.e5;
      this.B = 0;
      this.C = 0;
      this.b6 = -1;
      this.c5 = -1;
      this.c6 = null;
      this.O = 0;
      this.i2 = false;
      this.j2 = false;
      this.k2 = false;
      this.l2 = false;
      this.m3 = false;
      this.P = 0;
      this.Q = 0;
      this.R = 0;
      this.S = 0;
      this.T = 0;
      this.U = 0;
      this.n2 = false;
      this.a11 = null;
      this.o2 = false;
      this.a12 = null;
      this.a13 = null;
      this.a14 = null;
      if (this.a15 == null) {
         this.a15 = new Stack();
      }

      this.a15.removeAllElements();
      this.b7 = true;
      this.c7 = true;
      this.p2 = false;
      this.q2 = false;
      this.d4 = 0;
      if (this.b8 == null) {
         this.b8 = new short[55];
         this.c8 = new short[55];
         this.d5 = new short[55];
      }

      this.b9 = null;
      this.r2 = false;
      if (this.e5 == null) {
         short[] var4;
         com.ea.sims3.GameCanvas.a(var4 = new short[15], 66, var2.a22);
         com.ea.sims3.GameCanvas.c2(var4, 67);
         com.ea.sims3.GameCanvas.c2(var4, 60);
         com.ea.sims3.GameCanvas.c2(var4, 61);
         com.ea.sims3.GameCanvas.c2(var4, 70);
         com.ea.sims3.GameCanvas.c2(var4, 72);
         com.ea.sims3.GameCanvas.c2(var4, 74);
         com.ea.sims3.GameCanvas.c2(var4, 76);
         this.e5 = var4;
      }

      this.V = 0;
      this.s2 = false;
      this.t2 = false;
      if (this.f4 == null) {
         this.f4 = new short[20];
      }

      this.u2 = false;
      if (this.g3 == null) {
         this.g3 = new short[25];
      }

      this.W = 0;
      this.d6 = null;
      this.Y = -1;
      this.Z = 0;
      this.aa = 0;
      this.ae = 0;
      this.af = 0;
      this.ag = 0;
      this.c9 = 0;
      this.ah = 0;
      this.v2 = false;
      this.w2 = false;
      this.d7 = false;
      this.ai = -1;
      this.aj = 0;
      this.ak = 0;
      this.al = 0;
      this.am = 0;
      this.an = 0;
      this.h3 = null;
      this.ap = 0;
      this.af();
      this.a17 = var3;
      this.a18 = var2.a24;
      this.ar = var3.h6;
      this.as = 0;
      this.x2 = false;
      this.ax = 0;
      this.e6 = null;
      this.aD = 0;
      this.f6 = null;
      if (this.i4 == null) {
         this.i4 = new int[4];
         this.j4 = new int[4];
         this.b12 = new byte[4];
      }

      this.a20 = (byte[][])null;
      this.e7 = null;
      this.a21 = (short[][])null;
      this.bk = 0;
      if (this.c4 == null) {
         this.c4 = new Animator();
         this.d3 = new Animator();
         this.e4 = new Animator();
         this.f3 = new Animator();
         this.a16 = new Animator[4];

         for (int var5 = 0; var5 < 4; var5++) {
            this.a16[var5] = new Animator();
         }

         this.g5 = new Animator();
         this.b11 = new Animator[24];
         this.a19 = new int[24][3];

         for (int var6 = 0; var6 < 24; var6++) {
            this.b11[var6] = new Animator();
         }
      }

      this.c4.play(672, 20);
      this.d3.play(637, 20);
      this.e4.play(636, 20);
      this.f3.play(567, 20);
      this.g5.play(583, 20);
      this.initLayout();
      this.a6 = 0;
      if (var1 == 99) {
         this.a6 = 2;
      } else if (var1 == 98) {
         this.a6 = 2;
         var1 = 5;
      } else if (var1 == 100) {
         this.a6 = 1;
         var1 = 5;
      } else if (var1 == 97) {
         this.as = 30;
         var1 = 5;
      } else if (var1 == -1) {
         var1 = 5;
      }

      this.q = var1;
      this.onTick(1);
   }

   public final void stop() {
      if ((a5 == 5 || a5 == 16) && !this.r2 && !this.g2) {
         this.O();
      }
   }

   public final void pause() {
      this.a18.c2();
      int var1 = 2013688824;
      if (super.canvas.h == 2) {
         var1 &= -9;
      }

      super.canvas.c2(var1, -1);
      com.ea.sims3.SdkSound.muteAll();
   }

   public final void render(Graphics var1) {
      GameCanvas var2 = super.canvas;
      switch (a5) {
         case 1:
            var2.c(var1);
            return;
         case 3:
         case 9:
            this.e(var1);
            var2.a(var1);
            this.b(var1);
            this.d(var1);
            return;
         case 5:
            this.e(var1);
            this.d(var1);
            return;
         case 6:
            return;
         case 7:
            this.q(var1);
            return;
         case 8:
            this.t(var1);
            return;
         case 10:
            this.y(var1);
            return;
         case 11:
            this.z(var1);
            return;
         case 12:
            this.A(var1);
            return;
         case 13:
            this.B(var1);
            return;
         case 14:
            this.C(var1);
            return;
         case 15:
            this.D(var1);
            return;
         case 16:
            this.K(var1);
            return;
         case 17:
            this.R(var1);
            return;
         case 18:
            this.F(var1);
            return;
         case 19:
            this.G(var1);
         case 2:
         case 4:
      }
   }

   private void d(Graphics var1) {
      GameCanvas var2 = super.canvas;
      super.canvas.b(var1);
      int[] var3 = this.c3;
      int var4 = var2.getWidth() >> 1;
      int var5 = var2.getHeight();
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 635, 0, var4, var5);
      com.ea.sims3.SpriteRenderer.a2(var3, 635, 0, 0);
      int var6 = var4 + var3[0];
      int var7 = var5 + var3[1];
      SdkString var8 = var2.a2();
      var2.g2(this.A);
      var2.a(var8, 2, var6, var7, 24);
   }

   private void e(Graphics var1) {
      this.a18.a(var1);
      if (!this.u2) {
         this.f(var1);
      }

      if (this.c3()) {
         this.i(var1);
      }

      if (this.r2) {
         this.k(var1);
         this.m(var1);
         this.g(var1);
         this.h(var1);
         this.n(var1);
      } else if (!this.e2 && this.p2) {
         this.m(var1);
         this.g(var1);
         this.h(var1);
         this.l(var1);
      } else if (this.g2) {
         this.m(var1);
         this.g(var1);
         this.h(var1);
         super.canvas.a(var1);
         this.c(var1);
      } else {
         if (a5 != 16) {
            this.k(var1);
         }

         this.m(var1);
         this.g(var1);
         this.h(var1);
         if (!this.e2 && this.s2) {
            this.o(var1);
         }
      }
   }

   public final boolean matchesInput(int var1, int var2, int var3, int var4) {
      if (!super.matchesInput(var1, var2, var3, var4)) {
         return false;
      } else {
         SdkSound var5 = super.canvas.a7;
         super.canvas.a7.setLoopCount(1);
         if ((var3 & 4128) != 0) {
            if (this.r2 || this.p2 || this.s2) {
               var5.pendingSound = 3;
            }
         } else if ((var2 & 32027) != 0) {
            var5.pendingSound = 3;
         } else if ((var2 & 33508) == 0 && (var3 & 524288) == 0) {
            if ((var3 & 24836) != 0 && super.l != -1) {
               var5.pendingSound = 0;
            } else if ((var3 & 98384) != 0) {
               var5.pendingSound = 1;
            }
         } else if (this.s2 || !this.p2 || this.q2) {
            var5.pendingSound = 2;
         }

         return true;
      }
   }

   public final void update(int var1, int var2) {
      if (a5 != 1 && (!this.c3() || this.C == 2)) {
         GameCanvas var3 = super.canvas;
         if (super.canvas.e4 && this.n != 0) {
            this.onTick(this.n);
            var3.k2();
            this.n = 0;
         } else if (!var3.a11()) {
            if (this.r2) {
               this.w(var1, var2);
            } else if (!this.e2 && this.p2) {
               this.v(var1, var2);
            } else if (!this.e2 && this.s2) {
               this.x(var1, var2);
            } else if (this.g2) {
               this.s(var1, var2);
            } else if (this.u2) {
               this.y(var1, var2);
            } else {
               switch (a5) {
                  case 3:
                     this.n2(var1, var2);
                     return;
                  case 5:
                     this.o2(var1, var2);
                     return;
                  case 6:
                     return;
                  case 7:
                     this.z(var1, var2);
                     return;
                  case 8:
                     this.G(var1, var2);
                     return;
                  case 9:
                     this.t(var1, var2);
                     return;
                  case 10:
                     this.H(var1, var2);
                     return;
                  case 11:
                     this.I(var1, var2);
                     return;
                  case 12:
                     this.J(var1, var2);
                     return;
                  case 13:
                     this.K(var1, var2);
                     return;
                  case 14:
                     this.L(var1, var2);
                     return;
                  case 15:
                     this.M(var1, var2);
                     return;
                  case 16:
                     this.P(var1, var2);
                     return;
                  case 17:
                     this.V(var1, var2);
                     return;
                  case 18:
                     this.N(var1, var2);
                     return;
                  case 19:
                     this.O(var1, var2);
                  case 4:
               }
            }
         }
      } else {
         a10 = true;
      }
   }

   private void n2(int var1, int var2) {
      if (this.matchesInput(var1, var2, 4128, 256)) {
         super.canvas.c3();
         super.canvas.d3();
         this.l2(2);
      } else {
         if (this.matchesInput(var1, var2, 0, 512)) {
            this.l2(2);
         }
      }
   }

   private void o2(int var1, int var2) {
      if (this.matchesInput(var1, var2, 0, 5)) {
         this.O();
      } else if (this.matchesInput(var1, var2, 0, 1024)) {
         this.Q();
      } else {
         this.s2(var1);
         if (this.matchesInput(var1, var2, 4128, 16)) {
            this.F();
         }

         if (this.matchesInput(var1, var2, 2048, 0)) {
            this.onTick(8);
            this.E2(4);
            this.t2 = true;
         } else {
            if (this.matchesInput(var1, var2, 1024, 0)) {
               this.onTick(8);
               this.E2(3);
               this.t2 = true;
            }
         }
      }
   }

   public final void keyPressed(int var1) {
      this.m2 += var1;
      if (a5 == 1) {
         this.k2(var1);
      } else {
         this.n2(var1);
         super.canvas.i2(var1);
         switch (a5) {
            case 5:
               this.m2(var1);
               return;
            case 6:
               this.u2();
               if (this.b4 == 0) {
                  this.onTick(5);
                  return;
               }
               break;
            case 7:
               this.D2(var1);
               return;
            case 8:
               this.F2(var1);
               return;
            case 9:
               return;
            case 10:
               return;
            case 11:
               return;
            case 12:
               return;
            case 13:
               return;
            case 14:
               return;
            case 15:
               return;
            case 16:
               this.Q2(var1);
               return;
            case 17:
               return;
            case 18:
               return;
            case 19:
               this.O2(var1);
         }
      }
   }

   public final void onTick(int var1) {
      GameCanvas var2 = super.canvas;
      a5 = var1;
      this.m2 = 0;
      this.e2 = true;
      var2.i2();
      this.E();
      switch (var1) {
         case 1:
            this.w = 0;
            this.p2 = false;
            this.r2 = false;
            break;
         case 2:
            var2.b2(this.o, this.p);
            break;
         case 3:
            this.x();
            this.l2(84, 69);
            break;
         case 4:
            var2.d2();
            break;
         case 5:
            this.w2(1);
            this.e2 = false;
            break;
         case 6:
            this.t2();
            this.l2(32, 33);
            break;
         case 7:
            this.e2 = false;
         case 8:
         case 9:
         case 10:
         case 12:
         case 13:
         case 14:
         case 16:
         case 17:
         case 18:
         case 19:
         default:
            break;
         case 11:
            this.ah = this.a17.h();
            this.l2(704, 703);
            break;
         case 15:
            this.l2(this.al, this.ak);
      }

      this.w();
   }

   private void l2(int var1) {
      this.n = var1;
      if (!super.canvas.b5()) {
         super.canvas.j2();
      }

      this.E();
   }

   private void p(int var1, int var2) {
      if (super.canvas.i) {
         this.o = var1;
         this.p = var2;
         this.onTick(3);
      } else {
         this.q(var1, var2);
      }
   }

   private void q(int var1, int var2) {
      this.o = var1;
      this.p = var2;
      this.l2(2);
   }

   private void r(int var1, int var2) {
      this.o = var1;
      this.p = var2;
      this.onTick(2);
   }

   private void w() {
      int var1 = 0;
      short var2 = 0;
      if (this.r2) {
         if (this.V != 1 && this.V != 2 && this.V != 3 && this.V != 4) {
            var2 = 4;
            var1 = 16;
         } else {
            var2 = 512;
            var1 = 256;
         }
      } else if (!this.e2 && this.p2) {
         var2 = 4;
         var1 = 16;
      } else if (!this.e2 && this.s2) {
         var2 = 4;
         var1 = 16;
      } else {
         label96:
         if (this.g2) {
            var1 = 8;
         } else if (this.u2) {
            var2 = 4;
            var1 = 16;
         } else {
            label92:
            switch (a5) {
               case 3:
                  var2 = 512;
                  var1 = 256;
               case 4:
               case 6:
               default:
                  break;
               case 5:
                  var2 = 1;
                  var1 = 1024;
                  break;
               case 7:
                  switch (this.W) {
                     case 1:
                        var2 = 4;
                        var1 = 16;
                        break label92;
                     case 2:
                        var2 = 4;
                        var1 = 16;
                        break label92;
                     case 3:
                     case 7:
                     case 8:
                        var1 = 8;
                        break label92;
                     case 4:
                     case 11:
                        var2 = 4;
                        var1 = 2048;
                        break label92;
                     case 5:
                     case 9:
                        var2 = 512;
                        var1 = 256;
                     case 6:
                     default:
                        break label92;
                     case 10:
                        if (this.a18.b() <= this.a17.e5) {
                           var2 = 512;
                           var1 = 256;
                        } else {
                           var1 = 8;
                        }
                        break label92;
                  }
               case 8:
                  switch (this.Y) {
                     case 0:
                        var2 = 4;
                        var1 = this.a17.q() > 0 ? 16 : 0;
                        break label92;
                     case 1:
                     case 2:
                     case 3:
                     case 4:
                     case 5:
                     case 9:
                     case 10:
                        var2 = 4;
                        break label92;
                     case 6:
                        var1 = 0;
                        var2 = 4;
                        int var4;
                        if ((var4 = this.e()) != -1 && (this.a18.w(var4) & 2048) != 0) {
                           var1 = 16;
                        }
                     case 7:
                     case 8:
                     default:
                        break label92;
                  }
               case 9:
                  var2 = 0;
                  break label96;
               case 10:
                  if (this.ag != -1) {
                     var2 = 512;
                     var1 = 256;
                  } else {
                     var1 = 8;
                  }
                  break;
               case 11:
                  var1 = 8;
                  break;
               case 12:
                  var1 = 8;
                  break;
               case 13:
                  if (!this.v2) {
                     var2 = 512;
                     var1 = 256;
                     break;
                  }
                  break label96;
               case 14:
                  var2 = 128;
                  break label96;
               case 15:
                  var1 = 8;
                  break;
               case 16:
                  var2 = 1;
                  var1 = 64;
                  break;
               case 17:
                  int var3 = this.bk;
                  if (this.bk == 0) {
                     var2 = 4;
                     if (this.bm > 0) {
                        var1 = 4096;
                     } else if (this.bm < 0) {
                        var1 = 8192;
                     }
                  }
                  break;
               case 18:
                  switch (this.av) {
                     case 0:
                        var2 = 512;
                        var1 = 256;
                        break label92;
                     case 1:
                        var1 = 8;
                        break label92;
                     case 2:
                     case 3:
                        var1 = 8;
                     default:
                        break label92;
                  }
               case 19:
                  switch (this.ax) {
                     case 3:
                        break label96;
                  }
            }
         }
      }

      GameCanvas var5 = super.canvas;
      super.canvas.h2();
      var5.g2();
      var5.a4(var2, var1);
   }

   private void x() {
      if (this.p2) {
         this.N();
      }

      if (this.s2) {
         this.R();
      }

      if (this.u2) {
         this.T();
      }
   }

   public final void d2() {
      if (!this.f2) {
         SdkSound var1 = super.canvas.a7;
         if (super.canvas.a7.enabled) {
            var1.currentLoop = 0;
            var1.pendingSound = 11;
            var1.currentLoop = 1;
            this.f2 = true;
         }
      }
   }

   public final void b2(int var1, int var2) {
      this.a4(var1, var2, 1);
   }

   public final void d3(int var1) {
      super.canvas.a2(-11, var1, super.canvas.b2());
      this.c2(0, -11, -1);
   }

   public final void e2() {
      this.a14.a(6, (Entity) null);
      this.g2();
   }

   private void y() {
      super.canvas.s2(0);
      this.p(2, 97);
   }

   private void z() {
      this.p(2, 99);
   }

   public final void f2() {
      this.p(2, 98);
   }

   public final void a2(Entity var1) {
      int var2 = var1.a4;
      if (var1.a4 == 116) {
         super.canvas.a((Sim) null);
         super.canvas.m2 = 39;
         this.p(2, 100);
      } else if (var2 == 1) {
         super.canvas.a((Sim)var1);
         super.canvas.m2 = 41;
         this.p(2, 100);
      } else {
         int var3 = this.a18.b2(var2);
         super.canvas.s2(var3);
         this.p(2, -1);
      }
   }

   public final void a2(byte var1) {
      GameCanvas var2 = super.canvas;
      if (!super.canvas.c5(var1)) {
         var2.n2(var1);
         if (var2.g3) {
            short var3;
            byte var4;
            switch (var1) {
               case 0:
               default:
                  var3 = 90;
                  var4 = 91;
                  break;
               case 1:
                  var3 = 92;
                  var4 = 93;
                  break;
               case 2:
                  var3 = 94;
                  var4 = 95;
                  break;
               case 3:
                  var3 = 96;
                  var4 = 97;
                  break;
               case 4:
                  var3 = 98;
                  var4 = 99;
                  break;
               case 5:
                  var3 = 100;
                  var4 = 101;
                  break;
               case 6:
                  var3 = 500;
                  var4 = 102;
                  break;
               case 7:
                  var3 = 514;
                  var4 = 106;
                  break;
               case 8:
                  var3 = 507;
                  var4 = 103;
                  break;
               case 9:
                  var3 = 493;
                  var4 = 104;
                  break;
               case 10:
                  var3 = 400;
                  var4 = 107;
                  break;
               case 11:
                  var3 = 403;
                  var4 = 108;
                  break;
               case 12:
                  var3 = 404;
                  var4 = 109;
                  break;
               case 13:
                  var3 = 409;
                  var4 = 110;
                  break;
               case 14:
                  var3 = 408;
                  var4 = 111;
                  break;
               case 15:
                  var3 = 410;
                  var4 = 112;
            }

            this.x();
            this.m2(var4, var3);
            this.g2 = true;
            this.w();
         }
      }
   }

   private void s(int var1, int var2) {
      this.j2(var1);
      if (this.matchesInput(var1, var2, 4128, 8)) {
         this.A();
      }
   }

   private void A() {
      this.g2 = false;
      this.w();
   }

   private boolean h3() {
      return this.r2 || this.s2 || this.g2 || a5 != 5;
   }

   private void m2(int var1) {
      if (!this.h3()) {
         Neighborhood var2 = this.a17;
         if (this.b4 > 0) {
            this.onTick(6);
         } else {
            if (this.c3()) {
               this.p2(var1);
            } else {
               a10 = false;
            }

            if (this.g2 || var2.k3() || !this.i3()) {
               this.r2(var1);
               this.b5 = var1;
               int var3 = var2.a9 ? var1 << 2 : var1;
               this.z2(var3);
               this.v2(var1);
               this.y2(var1);
               this.advanceArrows(var1);
               if (!this.c3() && !super.canvas.a11()) {
                  this.K2(var1);
                  if (this.a6 == 2) {
                     this.I2(var1);
                  } else if (this.a6 == 0) {
                     this.G2(var1);
                  } else {
                     this.H2(var1);
                  }

                  if (this.h3 != null) {
                     this.a(var1, this.ao, this.h3);
                  }

                  this.ag();
               }
            }
         }
      }
   }

   private boolean i3() {
      Neighborhood var1 = this.a17;
      if (this.x2) {
         this.x2 = false;
         this.onTick(18);
         this.M2(3);
         return true;
      } else if (this.ai != -1) {
         this.onTick(15);
         return true;
      } else if (var1.f3()) {
         this.c2(8, -1, -1);
         return true;
      } else if (this.w2) {
         this.w2 = false;
         super.canvas.t2();
         this.c2(786, 747);
         return true;
      } else if (var1.a11()) {
         this.c2(787, 788);
         return true;
      } else {
         int var2;
         if ((var2 = var1.e()) != -1) {
            this.c2(var2, 771);
            return true;
         } else {
            if (this.d7) {
               this.d7 = false;
               if (var1.b8 != -1) {
                  this.ad();
                  return true;
               }
            }

            return false;
         }
      }
   }

   private void n2(int var1) {
      this.c4.advance(var1);
      int var2 = this.a17.e5;
      this.A = com.ea.sims3.BaseScreen.a(this.A, var2, var1, 9);
      if (!this.h3()) {
         this.e4.advance(var1);
         this.d3.advance(var1);
         this.g5.advance(var1);
      }

      if (a5 == 5 && !this.s2 && !this.r2) {
         Neighborhood var3 = this.a17;

         for (int var4 = 0; var4 < this.a16.length; var4++) {
            int var5 = var3.w(var4);
            Animator var6;
            (var6 = this.a16[var4]).advance(var1);
            if (!var6.isRunning() && (var6.animId == 590 || var5 != -1)) {
               var6.play(589, 20);
            }
         }
      }
   }

   private void f(Graphics var1) {
      GameCanvas var2 = super.canvas;
      Neighborhood var3 = this.a17;
      int[] var4 = this.c3;
      int var5 = this.s + this.u;
      int var6 = this.t;
      if (com.ea.sims3.GameCanvas.b) {
         var6 += 20;
      }

      if (a5 == 7) {
         int var8 = com.ea.sims3.SpriteRenderer.c(596, 0);
         int var9 = com.ea.sims3.SpriteRenderer.d(596, 0);
         int var10 = var2.getWidth() - (var8 + 9);
         int var11 = var9 + 3 - 1;
         this.b(var1, 3, var10, var11, 114);
      } else {
         int var7 = com.ea.sims3.SpriteRenderer.c(4, 0);
         int var25 = com.ea.sims3.SpriteRenderer.c(5, 0);
         int var12 = this.s + 5 + (var25 >> 1);
         int var13 = var6 + 3 + (var25 >> 1);
         int var14 = var12;
         int var15 = this.aq;
         int[] var16 = this.f5;
         Entity[] var17 = this.b10;

         for (int var18 = 0; var18 < var15; var18++) {
            int var19 = var16[var18];
            Entity var20 = var17[var18];
            byte var21 = 5;
            int var22 = var25;
            if (var18 == 0) {
               var21 = 4;
               var22 = var7;
            }

            int var23 = 196;
            if (var19 == 106) {
               var23 = 3;
            } else if (var20 != null) {
               if (var20.a13(16384) && !this.b5()) {
                  var23 = 6;
               } else {
                  var23 = this.a18.j(var20.a4);
               }
            }

            com.ea.sims3.SpriteRenderer.drawFrame(var1, var21, 0, var14, var13);
            com.ea.sims3.SpriteRenderer.drawFrame(var1, var23, 0, var14, var13);
            var14 += var22 + 2;
         }
      }

      int var24 = var3.a();
      SdkString var26 = var2.a2();
      var2.h2(var24);
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 596, 0, var5, var6);
      com.ea.sims3.SpriteRenderer.a2(var4, 596, 0, 0);
      int var27 = var5 + var4[0];
      int var28 = var6 + var4[1];
      com.ea.sims3.SpriteRenderer.a2(var4, 596, 0, 1);
      int var29 = var5 + var4[0];
      int var30 = var6 + var4[1];
      var2.a(var26, 2, var27 + 1, var28 + 1, 24);
      int var31 = com.ea.sims3.Neighborhood.c7[var3.b()];
      var2.a2(var31, 2, var29 + 1, var30 + 1, 20);
      byte var32;
      short var33;
      if (this.h3()) {
         var32 = 2;
         var33 = 673;
      } else if (var3.a9) {
         var32 = 4;
         var33 = 671;
      } else {
         var32 = 3;
         var33 = 672;
      }

      this.c4.play(var33, 4);
      com.ea.sims3.SpriteRenderer.a2(var4, 596, 0, var32);
      com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.c4, var5 + var4[0], var6 + var4[1]);
   }

   private void g(Graphics var1) {
      if (this.u2) {
         this.p(var1);
      }

      GameCanvas var2 = super.canvas;
      Neighborhood var3 = this.a17;
      int[] var4 = this.c3;
      int var5 = var2.a();
      int var6 = var2.getHeight() - 15;
      if (a5 != 16 && a5 != 7 && this.d() == -1 && !this.u2) {
         this.E(var1);
      }

      if (com.ea.sims3.GameCanvas.b) {
         com.ea.sims3.SpriteRenderer.drawFrame(var1, 588, 0, var5, var6);
      } else {
         com.ea.sims3.SpriteRenderer.drawFrame(var1, 587, 0, var5, var6);
      }

      if (this.b6 != -1) {
         this.j(var1);
      }

      com.ea.sims3.SpriteRenderer.b(var4, 587, 0, 0);
      int var7 = var5 + var4[0];
      int var8 = var6 + var4[1];

      for (int var9 = 0; var9 < 4; var9++) {
         int var10 = var7 + var9 * 16;
         Animator var11;
         if ((var11 = this.a16[var9]).isRunning()) {
            com.ea.sims3.SpriteRenderer.drawAnimator(var1, var11, var10, var8);
         }
      }

      com.ea.sims3.SpriteRenderer.b(var4, 587, 0, 1);
      var7 = var5 + var4[0] + -8;
      var8 = var6 + var4[1] + (var4[3] >> 1);
      int var16 = var4[2];
      int var17 = var7 + var16;
      int var18 = var3.a6;

      for (int var12 = 0; var12 < var18 && var17 > var7; var12++) {
         if (var3.y(var12) < 2293760) {
            short var13 = var3.e2(var12);
            com.ea.sims3.SpriteRenderer.drawFrame(var1, var13, 0, var17, var8);
            var17 -= 16;
         }
      }

      for (int var19 = 0; var19 < var18 && var17 > var7; var19++) {
         if (var3.y(var19) > 4259840) {
            short var20 = var3.d2(var19);
            com.ea.sims3.SpriteRenderer.drawFrame(var1, var20, 0, var17, var8);
            var17 -= 16;
         }
      }
   }

   private void h(Graphics var1) {
      GameCanvas var2 = super.canvas;
      int var3 = super.canvas.getWidth() >> 1;
      int var4 = var2.getHeight() - 15;
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 586, 0, var3, var4);
      boolean var5 = this.ar < 0;
      int var6 = Math.abs(this.ar >> 16);
      int var9 = com.ea.sims3.SpriteRenderer.c(584, 0) * var6 / 100;
      int var10 = var3 + (var5 ? -var9 : 0);
      int var11 = var1.getClipX();
      int var12 = var1.getClipY();
      int var13 = var1.getClipWidth();
      int var14 = var1.getClipHeight();
      var1.setClip(var10, var12, var9, var14);
      int var15 = var5 ? 585 : 584;
      com.ea.sims3.SpriteRenderer.drawFrame(var1, var15, 0, var3, var4);
      var1.setClip(var11, var12, var13, var14);
      this.c(var1, var3, var4);
   }

   public final void c2(int var1, int var2) {
      this.a4(var1, var2, 0);
   }

   public final void a4(int var1, int var2, int var3) {
      this.T();
      this.B = var3;
      this.l2(var1, var2);
      this.onTick(9);
   }

   private void t(int var1, int var2) {
      if (this.matchesInput(var1, var2, 4128, 12)) {
         switch (this.B) {
            case 0:
               this.onTick(5);
               return;
            case 1:
               this.f2();
               return;
            case 2:
               if (this.a14.g2 == 159) {
                  this.a14.F();
               }

               this.onTick(5);
               return;
            case 3:
               this.c2(6, -1, -1);
               return;
            case 4:
               this.c2(7, -1, -1);
         }
      }
   }

   public final boolean c3() {
      return this.C != 0;
   }

   public final void g2() {
      this.x();
      this.D = this.a14.g2;
      this.o2(1);
   }

   private void B() {
      this.x();
      this.o2(2);
   }

   private void o2(int var1) {
      this.C = var1;
      switch (var1) {
         case 0:
            this.E = 0;
            return;
         case 1:
            this.a17.f2();
            this.E = 0;
            return;
         case 2:
            this.E = 1000;
            return;
         case 3:
            this.E = 1000;
      }
   }

   private void p2(int var1) {
      if (this.n2) {
         switch (this.C) {
            case 0:
               return;
            case 1:
               this.a17.f2();
               if (this.E == 1000) {
                  this.o2(2);
                  this.C();
                  return;
               }

               this.E = Math.min(this.E + var1, 1000);
               return;
            case 2:
               this.a17.f2();
               this.E += var1;
               if (this.E >= 1500) {
                  this.o2(3);
                  return;
               }
               break;
            case 3:
               this.a17.f2();
               if (this.E == 0) {
                  this.o2(0);
                  this.D();
                  return;
               }

               this.E = Math.max(this.E - var1, 0);
         }
      }
   }

   private void C() {
      switch (this.D) {
         case 6:
            if (this.a11() && this.a18.a() == 0) {
               this.a(this.a14);
               this.as = 120;
               this.a17.f2();
               Sim[] var4 = this.a13();

               for (int var5 = 0; var5 < var4.length; var5++) {
                  Sim var3;
                  if ((var3 = var4[var5]) != this.a14) {
                     if (this.a17.U(var3.b) != 0) {
                        var3.a10();
                     } else {
                        var3.a(0, (Entity) null);
                     }
                  }
               }

               return;
            } else {
               super.canvas.s2(0);
               this.r(2, 96);
            }
         default:
            return;
         case 121:
            this.a17.b7(this.a14.i2 - 15);
            return;
         case 139:
            Entity[] var1 = this.a12();

            for (int var2 = 0; var2 < var1.length; var2++) {
               var1[var2].a10();
            }

            this.a18.b7(0);
            return;
         case 159:
            this.a17.b4();
            this.al();
            this.a14.E();
      }
   }

   private void D() {
      switch (this.D) {
         case 139:
            this.c2(5, -1, -1);
            return;
         case 159:
            this.ac();
            return;
         default:
            this.a17.f2();
      }
   }

   private void i(Graphics var1) {
      if (this.n2) {
         int var3 = (Math.min(this.E, 1000) * this.v / 1000 >> 1) + 1;
         var1.setColor(0);
         var1.fillRect(this.s, this.t, this.u, var3);
         var1.fillRect(this.s, this.t + this.v - var3, this.u, var3);
      }
   }

   public final void d2(int var1, int var2) {
      GameCanvas var3 = super.canvas;
      this.b6 = (short)var1;
      this.c5 = (short)var2;
      this.F = 0;
      this.G = 0;
      this.H = (var3.a2(var1, 1) << 16) + 327680;
      if (var2 != -1) {
         this.H = this.H + (5 + var3.a2(var2, 1) << 16);
      }
   }

   private void q2(int var1) {
      if (this.b6 == var1) {
         this.b6 = -1;
      }
   }

   private boolean j3() {
      return this.b6 != -1;
   }

   private void r2(int var1) {
      if (this.b6 != -1) {
         this.F += var1;
         if (this.F > 2000) {
            if (this.G > this.H) {
               this.b6 = -1;
               this.c5 = -1;
               return;
            }

            this.G += var1 * 3276;
         }
      }
   }

   private void j(Graphics var1) {
      GameCanvas var2 = super.canvas;
      int var3 = com.ea.sims3.SpriteRenderer.d(670, 0);
      int var4 = var2.getHeight() - 15 - this.r - var3;
      int[] var5 = this.c3;
      com.ea.sims3.SpriteRenderer.a2(this.c3, 670, 0, 0);
      int var6 = 0 + var5[0];
      int var7 = var4 + var5[1];
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 670, 0, 0, var4);
      if (this.F >= 1000 || (this.F & 511) <= 255) {
         int var8 = var6 - (this.G >> 16);
         var2.a2(this.b6, 1, var8, var7, 20);
         if (this.c5 != -1) {
            int var9 = var2.a2(this.b6, 1);
            int var10 = var8 + var9 + 5;
            var2.a2(this.c5, 1, var10, var7, 20);
         }
      }
   }

   private void E() {
      this.j2 = false;
      this.k2 = false;
      this.l2 = false;
      this.m3 = false;
      super.canvas.g2();
      super.canvas.h2();
   }

   private void s2(int var1) {
      this.t2(var1);
      if ((var1 & 123870) != 0) {
         this.I();
      }
   }

   private void t2(int var1) {
      this.j2 = (var1 & 32914) != 0;
      this.k2 = (var1 & 66120) != 0;
      this.m3 = (var1 & 8206) != 0;
      this.l2 = (var1 & 17280) != 0;
   }

   private void F() {
      this.I();
      Entity var1 = this.c6;
      if (this.c6 != null && this.h2) {
         if (var1 == this.a14) {
            this.S();
         } else {
            this.f(var1);
         }
      } else {
         if (a5 == 5 && this.h2) {
            Lot var2 = this.a18;
            int var3 = this.a18.e(this.I, this.J);
            int var4 = var2.f(this.I, this.J);
            if (this.a18.b3(var3, var4)) {
               this.b(106, null, var3, var4);
            }
         }
      }
   }

   private void k(Graphics var1) {
      if (this.W != 6 && this.ax != 1) {
         GameCanvas var2 = super.canvas;
         Lot var3 = this.a18;
         int var4 = this.I;
         int var5 = this.J;
         if (this.u2 && this.c6 != null) {
            var4 = this.K;
            var5 = this.L;
         }

         var4 = (var4 >> 16) + var3.d();
         var5 = (var5 >> 16) + var3.e();
         if (this.u2) {
            this.d(var1, var4, var5);
         } else {
            com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.f3, var4, var5);
            int var6;
            if (this.h2 && this.c6 != null && this.M < 1000 && (var6 = this.c6.c()) != 24) {
               int var7 = var2.a2(var6, 1);
               int var8 = var2.b2(1) - 2;
               int var9 = 3 + var7 + 3;
               int var10 = 2 + var8 + 2;
               int var11 = Math.max(0, var4 + -5 - var9);
               int var12 = var5 + -5 - var10;
               com.ea.sims3.SpriteRenderer.a(var1, 77);
               var1.drawRect(var11, var12, var9, var10);
               com.ea.sims3.SpriteRenderer.a(var1, 76);
               var1.fillRect(var11 + 1, var12 + 1, var9 - 1, var10 - 1);
               var2.a2(var6, 1, var11 + 3 + (var7 >> 1), var12 + 2, 17);
            }
         }
      }
   }

   private void u2(int var1) {
      if (!this.i2) {
         int var3 = (this.k2 ? 65536 : 0) + (this.j2 ? -65536 : 0);
         int var4 = (this.l2 ? 65536 : 0) + (this.m3 ? -65536 : 0);
         if (var3 != 0 || var4 != 0) {
            int var5 = com.ea.sims3.FixedMath.atan2(var4, var3);
            this.x2(var5);
         }
      }

      if (this.i2) {
         if (this.c6 != null) {
            int[] var9 = this.c3;
            this.c6.a(var9);
            this.K = var9[0];
            this.L = var9[1];
         }

         if (this.u2) {
         }

         int var10 = 19660 * var1;
         int var13 = this.K - this.I >> 8;
         int var15 = this.L - this.J >> 8;
         int var6 = com.ea.sims3.FixedMath.length(var13, var15);
         if (var10 >= var6 << 8) {
            this.I = this.K;
            this.J = this.L;
         } else {
            var10 = com.ea.sims3.FixedMath.div(var10, var6);
            this.I = this.I + com.ea.sims3.FixedMath.mul(var13, var10);
            this.J = this.J + com.ea.sims3.FixedMath.mul(var15, var10);
         }

         this.h2 = this.I == this.K && this.J == this.L;
         this.i2 = this.i2 && !this.h2;
      } else if (this.N <= 0) {
         int var12 = var1 * ((this.k2 ? 13107 : 0) + (this.j2 ? -13107 : 0));
         int var14 = var1 * ((this.l2 ? 13107 : 0) + (this.m3 ? -13107 : 0));
         this.I += var12;
         this.J += var14;
         if (var12 != 0 || var14 != 0) {
            this.c6 = null;
            this.N = 0;
            Tile var17 = this.a18.a8;
            int var7 = (this.a18.a8.c * com.ea.sims3.Tile.tileWidth() << 16) - 983040;
            int var8 = (var17.d * com.ea.sims3.Tile.tileHeight() << 16) - 983040;
            this.I = com.ea.sims3.FixedMath.clamp(this.I, 0, var7);
            this.J = com.ea.sims3.FixedMath.clamp(this.J, 0, var8);
            this.K = this.I;
            this.L = this.J;
            this.h2 = true;
            this.M = 0;
         } else if (this.c6 != null && this.h2) {
            int[] var16 = this.c3;
            this.c6.a(var16);
            this.K = var16[0];
            this.L = var16[1];
            this.I = var16[0];
            this.J = var16[1];
         }
      }

      if (this.h2) {
         this.M += var1;
      } else {
         this.M = 0;
      }
   }

   private void v2(int var1) {
      if (!this.c3()) {
         if (!this.r2) {
            this.u2(var1);
         }

         if (this.c6 != null && this.N > 0) {
            this.N -= var1;
         }

         int var2 = (!this.h2 || this.c6 == null) && !this.p2 && (a5 != 7 || this.W != 2) ? 566 : 567;
         this.f3.play(var2, 4);
         this.f3.advance(var1);
      }
   }

   public final void b(Entity var1) {
      this.c6 = var1;
      if (var1 != null) {
         int[] var2 = this.c3;
         var1.a(var2);
         this.K = var2[0];
         this.L = var2[1];
         this.i2 = true;
         this.M = 0;
      } else {
         this.K = this.I;
         this.L = this.J;
         this.i2 = false;
      }
   }

   private void w2(int var1) {
      this.O = var1;
      if (this.c6 == null || !this.c6.a13(var1)) {
         this.b((Entity) null);
      }
   }

   private void x2(int var1) {
      int[] var2 = this.c3;
      Entity var3 = null;
      int var4 = 7680;
      int var5 = this.I;
      int var6 = this.J;
      int var7 = com.ea.sims3.FixedMath.wrapAngle(var1);
      Entity[] var8;
      int var9 = (var8 = this.a12()).length;
      int var10 = this.O;

      for (int var11 = 0; var11 < var9; var11++) {
         Entity var12;
         (var12 = var8[var11]).a(var2);
         int var13 = var2[0];
         int var14 = var2[1];
         if (var12 != this.c6 && var12.a13(var10)) {
            int var15 = this.u << 16;
            int var16 = this.v << 16;
            int var17 = Math.abs(var5 - var13);
            int var18 = Math.abs(var6 - var14);
            if (var17 <= var15 && var18 <= var16 && (var17 != 0 || var18 != 0)) {
               int var19 = com.ea.sims3.FixedMath.atan2(var14 - var6, var13 - var5);
               if (Math.abs(com.ea.sims3.FixedMath.wrapAngle(var7 - var19)) < 51472) {
                  int var21 = var5 - var13 >> 8;
                  int var22 = var6 - var14 >> 8;
                  int var23;
                  if ((var23 = com.ea.sims3.FixedMath.length(var21, var22)) < var4) {
                     var3 = var12;
                     var4 = var23;
                  }
               }
            }
         }
      }

      if (var3 != null) {
         this.b(var3);
         this.N = 200;
      }
   }

   private void G() {
      int[] var1 = this.c3;
      Entity var2 = null;
      int var3 = Integer.MAX_VALUE;
      int var4 = this.I;
      int var5 = this.J;
      Entity[] var6;
      int var7 = (var6 = this.a12()).length;
      int var8 = this.O;

      for (int var9 = 0; var9 < var7; var9++) {
         Entity var10;
         (var10 = var6[var9]).a(var1);
         int var11 = var1[0];
         int var12 = var1[1];
         if (var10 != this.c6 && var10.a13(var8)) {
            int var13 = var4 - var11 >> 8;
            int var14 = var5 - var12 >> 8;
            int var15;
            if ((var15 = com.ea.sims3.FixedMath.length(var13, var14)) < var3) {
               var2 = var10;
               var3 = var15;
            }
         }
      }

      if (var2 != null) {
         this.b(var2);
         this.N = 200;
      }
   }

   private void H() {
      Lot var2 = this.a18;
      Neighborhood var3 = this.a17;
      int var4 = this.K;
      int var5 = this.L;
      if (this.a11 != null) {
         int[] var6 = this.c3;
         this.a11.a(var6);
         var4 = var6[0];
         var5 = var6[1];
      } else if (this.p2 || var3.i6 != -1) {
         Entity var17 = this.b9;
         if (this.b9 == null && this.c6 != null && this.c6.a13(16384) && ((Sim)this.c6).b == var3.i6) {
            var17 = this.c6;
         }

         if (var17 != null) {
            int var7 = this.u << 14;
            if (var17.a13(16384)) {
               var4 -= var7;
            } else {
               var4 += var7;
            }
         }
      }

      Tile var18 = var2.a8;
      int var19 = var2.c6;
      int var8 = var2.c6 >> 1;
      int var9 = var2.d6;
      int var10 = var2.d6 >> 1;
      int var11 = var8 << 16;
      int var12 = var18.c * com.ea.sims3.Tile.tileWidth() - var8 - 1 << 16;
      int var13 = var10 << 16;
      int var14 = var18.d * com.ea.sims3.Tile.tileHeight() - var10 - 1 << 16;
      if (com.ea.sims3.GameCanvas.b) {
         var14 = var18.d * com.ea.sims3.Tile.tileHeight() - var10 - 1 << 16;
      } else {
         var12 = var18.c * com.ea.sims3.Tile.tileWidth() - var8 - 1 << 16;
      }

      var4 = com.ea.sims3.FixedMath.clamp(var4, var11, var12);
      var5 = com.ea.sims3.FixedMath.clamp(var5, var13, var14);
      this.P = var4;
      this.Q = var5;
   }

   public final void c(Entity var1) {
      if (!this.p2 && !this.u2) {
         this.a11 = var1;
         this.c6 = null;
         if (var1 == null) {
            this.o2 = true;
         }
      }
   }

   private void I() {
      this.a11 = null;
      this.o2 = false;
   }

   private void y2(int var1) {
      Lot var2 = this.a18;
      int var3 = 11534;
      int var5 = 32;
      if (this.u2) {
         var3 = com.ea.sims3.FixedMath.mul(11534, 196608);
         var5 = com.ea.sims3.FixedMath.mul(32, 196608);
      }

      int var6 = var2.e5;
      int var7 = var2.f4;
      this.H();
      int var8 = com.ea.sims3.FixedMath.sign(this.P - var6);
      int var9 = com.ea.sims3.FixedMath.sign(this.Q - var7);
      if (var8 > 0 && this.T > 0) {
         this.R += var5 * var1;
      } else if (var8 > 0 && this.T <= 0) {
         this.R = 1441;
      } else if (var8 < 0 && this.T < 0) {
         this.R -= var5 * var1;
      } else if (var8 < 0 && this.T >= 0) {
         this.R = -1441;
      } else if (var8 == 0 && this.T > 0) {
         this.R = -1441;
      } else if (var8 == 0 && this.T < 0) {
         this.R = 1441;
      }

      if (this.R < -var3) {
         this.R = -var3;
      }

      if (this.R > var3) {
         this.R = var3;
      }

      if (var9 > 0 && this.U > 0) {
         this.S += var5 * var1;
      } else if (var9 > 0 && this.U <= 0) {
         this.S = 1441;
      } else if (var9 < 0 && this.U < 0) {
         this.S -= var5 * var1;
      } else if (var9 < 0 && this.U >= 0) {
         this.S = -1441;
      } else if (var9 == 0 && this.U > 0) {
         this.S = -1441;
      } else if (var9 == 0 && this.U < 0) {
         this.S = 1441;
      }

      if (this.S < -var3) {
         this.S = -var3;
      }

      if (this.S > var3) {
         this.S = var3;
      }

      int var10 = var6 + this.R * var1;
      int var11 = var7 + this.S * var1;
      boolean var12;
      if ((this.R <= 0 || var10 <= this.P) && (this.R >= 0 || var10 >= this.P)) {
         var12 = var10 == this.P;
      } else {
         var12 = true;
         var10 = this.P;
      }

      boolean var13;
      if ((this.S <= 0 || var11 <= this.Q) && (this.S >= 0 || var11 >= this.Q)) {
         var13 = var11 == this.Q;
      } else {
         var13 = true;
         var11 = this.Q;
      }

      this.T = this.R;
      this.U = this.S;
      var2.e5 = var10;
      var2.f4 = var11;
      this.n2 = var12 && var13;
      if (this.a11 != null || this.o2) {
         this.I += var10 - var6;
         this.J += var11 - var7;
      }
   }

   private void J() {
      this.H();
      this.a18.e5 = this.P;
      this.a18.f4 = this.Q;
      this.n2 = true;
   }

   public final void d(Entity var1) {
      this.b(var1);
      this.I = this.K;
      this.J = this.L;
      this.J();
   }

   private void K() {
      this.a18.b7(super.canvas.l2);
      if (this.q == 96) {
         this.q = 5;
         this.a(this.a14);
         this.as = 120;
         this.a17.f2();
         this.B();
      }
   }

   private void L() {
      this.a18.c4(super.canvas.m2);
      Sim[] var1 = this.a13();

      for (int var2 = 0; var2 < var1.length; var2++) {
         Sim var3;
         if ((var3 = var1[var2]).b != 0) {
            var3.s2(32);
         }
      }
   }

   private void M() {
      boolean var1 = false;
      if (this.q == 99) {
         this.q = 5;
         var1 = true;
      }

      this.a18.a(var1);
      this.a14.a12(1);
   }

   public final Entity[] a12() {
      if (this.b7) {
         this.b7 = false;
         if (this.a12 == null || this.a12.length != this.a15.size()) {
            this.a12 = null;
            this.a12 = new Entity[this.a15.size()];
         }

         this.a15.copyInto(this.a12);
      }

      return this.a12;
   }

   public final Sim[] a13() {
      if (this.c7) {
         this.c7 = false;
         Entity[] var1 = this.a12();
         int var2 = 0;

         for (int var3 = 0; var3 < var1.length; var3++) {
            if (var1[var3].a13(16384)) {
               var2++;
            }
         }

         Sim[] var6 = this.a13;
         if (this.a13 == null || var6.length != var2) {
            this.a13 = null;
            var6 = new Sim[var2];
            this.a13 = var6;
         }

         int var4 = 0;

         for (int var5 = 0; var5 < var1.length; var5++) {
            if (var1[var5].a13(16384)) {
               var6[var4] = (Sim)var1[var5];
               var4++;
            }
         }
      }

      return this.a13;
   }

   public final Entity a5(int var1) {
      Entity[] var2 = this.a12();
      int var3 = super.canvas.b(0, var2.length - 1);

      for (int var4 = 0; var4 < var2.length; var4++) {
         int var5 = (var3 + var4) % var2.length;
         Entity var6;
         if ((var6 = var2[var5]).a4 == var1) {
            return var6;
         }
      }

      return null;
   }

   public final Entity a2(int var1, int var2, int var3) {
      Entity var4 = null;
      int var5 = -1;
      Entity[] var6 = this.a12();

      for (int var7 = 0; var7 < var6.length; var7++) {
         Entity var8;
         int var9;
         if ((var8 = var6[var7]).a4 == var1 && ((var9 = Math.abs(var2 - var8.c) + Math.abs(var3 - var8.d)) < var5 || var4 == null)) {
            var5 = var9;
            var4 = var8;
         }
      }

      return var4;
   }

   public final boolean a5(int var1, int var2, int var3) {
      int var5 = com.ea.sims3.Lot.f(var2);
      int var6 = com.ea.sims3.Lot.f(var3);
      Entity[] var7 = this.a12();

      for (int var8 = 0; var8 < var7.length; var8++) {
         Entity var9;
         if ((var9 = var7[var8]).a() == var1) {
            int var10 = com.ea.sims3.Lot.f(var9.c);
            int var11 = com.ea.sims3.Lot.g(var9.d);
            if (var10 == var5 && var11 == var6) {
               return true;
            }
         }
      }

      return false;
   }

   public final Entity b4(int var1) {
      Entity[] var2 = this.a12();
      int var3 = super.canvas.b(0, var2.length - 1);

      for (int var4 = 0; var4 < var2.length; var4++) {
         int var5 = (var3 + var4) % var2.length;
         Entity var6;
         if ((var6 = var2[var5]).a() == var1) {
            return var6;
         }
      }

      return null;
   }

   public final Entity c2(int var1) {
      Lot var2 = this.a18;
      Entity[] var3 = this.a12();
      int var4 = super.canvas.b(0, var3.length - 1);

      for (int var5 = 0; var5 < var3.length; var5++) {
         int var6 = (var4 + var5) % var3.length;
         Entity var7;
         int var8 = (var7 = var3[var6]).a4;
         if ((var2.l(var8) & var1) != 0 && var7.b5() && !var7.b8(768)) {
            return var7;
         }
      }

      return null;
   }

   public final Sim a7(int var1) {
      Sim[] var2;
      int var3 = (var2 = this.a13()).length;

      for (int var4 = 0; var4 < var3; var4++) {
         Sim var5;
         if ((var5 = var2[var4]).b == var1) {
            return var5;
         }
      }

      return null;
   }

   public final Entity a(int var1, int var2, int var3, int var4, int var5) {
      int var6 = this.a18.k(var1);
      if (this.g3() && (var1 == 2 || var1 == 3)) {
         var6 |= 8192;
      }

      Object var7 = null;
      if ((var6 & 16384) != 0) {
         var7 = new Sim(this);
         if (var1 == 0) {
            this.a14 = (Sim)var7;
         }
      } else if ((var6 & 8192) != 0) {
         var7 = new Entity(this);
      }

      if (var7 != null) {
         ((Entity)var7).a2(var1, var2, var3, var4, var5);
         this.h((Entity)var7);
         if (((Entity)var7).a13(16384)) {
            this.b((Sim)var7);
         }
      }

      return (Entity)var7;
   }

   public final Entity a(int var1, Entity var2, int var3) {
      int[] var4 = this.c3;
      int var5 = 0;

      do {
         var2.a(var4, var5);
         var5++;
      } while (!this.a18.b3(var4[0], var4[1]));

      return this.a(var1, var4[0], var4[1], 0, var3);
   }

   public final Entity b(int var1, int var2, int var3, int var4, int var5) {
      int var7 = com.ea.sims3.Lot.d(var2);
      int var8 = com.ea.sims3.Lot.e(var3);
      return this.a(var1, var7, var8, var4, var5);
   }

   public final void a2(int var1, int var2, int var3, int var4) {
      Entity var5;
      if ((var5 = this.a(7, var1, var2, 3, -1)) != null) {
         var5.b2(var3, var4);
      }
   }

   public final void b2(int var1, int var2, int var3) {
      Entity var4;
      if ((var4 = this.a(8, var1, var2, 3, -1)) != null) {
         var4.i2(var3);
      }
   }

   private void h(Entity var1) {
      var1.a(true);
      this.a15.addElement(var1);
      this.b7 = true;
      if (var1.a13(16384)) {
         this.c7 = true;
      }
   }

   public final void e(Entity var1) {
      if (this.c6 == var1) {
         this.b((Entity) null);
      }

      if (this.p2 && this.b9 == var1) {
         this.N();
      }

      int var2 = -1;
      if (var1.a13(16384)) {
         var2 = var1.b;
      }

      var1.a(false);
      this.a15.removeElement(var1);
      this.b7 = true;
      if (var2 != -1) {
         this.c7 = true;
         int var3;
         if ((var3 = com.ea.sims3.GameCanvas.a(var2, this.a9)) != -1) {
            int var4 = (this.a17.i(var2, 0) == 0 ? 512 : 256) | 16384;
            super.canvas.c2(var4, var3);
            this.a9[var3] = -1;
         }
      }
   }

   private void z2(int var1) {
      Entity[] var2;
      if ((var2 = this.a12()) != null) {
         for (int var3 = 0; var3 < var2.length; var3++) {
            Entity var4;
            (var4 = var2[var3]).f3(var1);
         }
      }
   }

   private void u(int var1, int var2) {
      Entity[] var3;
      if ((var3 = this.a12()) != null) {
         for (int var4 = 0; var4 < var3.length; var4++) {
            Entity var5;
            if ((var5 = var3[var4]).a4 == var2) {
               var5.f3(var1);
            }
         }
      }
   }

   public final void h2() {
      Entity[] var1 = this.a12();

      for (int var2 = 0; var2 < var1.length; var2++) {
         Entity var3;
         if ((var3 = var1[var2]).a13(4)) {
            var3.a10();
         }
      }
   }

   public final void f(Entity var1) {
      Neighborhood var2 = this.a17;
      if (this.s2) {
         this.R();
      }

      this.E();
      short[] var3 = this.b8;
      short[] var4 = this.c8;
      var2.j2(-1);
      this.p2 = true;
      this.q2 = false;
      this.d4 = -1;
      this.b9 = var1;
      if (a5 == 7) {
         var1.a2(var3);
         var3[4] = this.a7;
         com.ea.sims3.GameCanvas.a(var4, var3);
      } else {
         int var5;
         if ((var5 = com.ea.sims3.GameCanvas.a(this.b9, this.b10)) != -1 && this.f5[var5] != 2) {
            int var6;
            if (this.b9.a13(16384)) {
               var6 = this.a17.R(this.b9.b);
            } else {
               var6 = this.b9.b();
            }

            com.ea.sims3.GameCanvas.b2(var3, var6);
            com.ea.sims3.GameCanvas.b2(var4, var6);
            com.ea.sims3.GameCanvas.c2(var3, 113);
            com.ea.sims3.GameCanvas.c2(var4, -3);
         } else {
            var2.a(this.b9, var3, var4);
         }

         com.ea.sims3.GameCanvas.a(this.d5, var4);
         if (var3[0] > this.a7) {
            var2.a(var3, var4, this.d5);
         }

         var3[4] = this.a7;
      }

      this.w();
   }

   private void N() {
      this.p2 = false;
      this.q2 = false;
      this.d4 = -1;
      this.b9 = null;
      this.w();
      this.b(this.c6);
   }

   private void l(Graphics var1) {
      int var3 = this.b9 != null && this.b9.a13(16384) ? 2 : 4;
      this.a(var1, this.b8, var3);
   }

   private int d() {
      Neighborhood var1 = this.a17;
      if (!this.e2 && this.p2) {
         if (this.b9 != null && this.b9.a13(16384)) {
            return ((Sim)this.b9).b;
         }
      } else if (var1.i3()) {
         return var1.i6;
      }

      return -1;
   }

   private void m(Graphics var1) {
      Neighborhood var2 = this.a17;
      int var3 = this.d();
      boolean var4 = !this.p2 || this.e2;
      if (!this.e2 && this.p2) {
         if (this.b9 != null && this.b9.a13(16384)) {
            var3 = ((Sim)this.b9).b;
         }
      } else if (var2.i3()) {
         var4 = true;
         var3 = var2.i6;
      }

      if (var3 != -1) {
         GameCanvas var5 = super.canvas;
         int[] var6 = this.c3;
         var2.a2(var3, var6);
         int var7 = (var6[0] != -1 ? 1 : 0) + (var6[2] != -1 ? 1 : 0);
         int var8 = this.s + 3;
         int var9 = var5.getWidth() >> 1;
         int var10 = com.ea.sims3.SpriteRenderer.d(670, 0);
         int var12 = var5.getHeight() - 15 - this.r - 18 * (var7 + 1);
         if (this.b6 != -1) {
            var12 -= var10;
         }

         int var14 = var2.G(var3);
         this.a(var1, var8, var12, var9, var14, 65536, true);
         int var13 = var12 + 18;

         for (byte var15 = 0; var15 < 4 && (var14 = var6[var15]) != -1; var15 += 2) {
            int var16 = var6[var15 + 1];
            this.a(var1, var8, var13, var9, var14, var16, false);
            var13 += 18;
         }

         if (var4) {
            int var21 = -1;
            Sim var22;
            if ((var22 = this.a14.a4()) != null && var22.b == var3) {
               var21 = var2.J(this.a14.g2);
            }

            if (var21 == 24) {
               var21 = -1;
            }

            int var17 = var12 - 14 - 4 - (var21 != -1 ? 12 : 0);
            int var18 = var9 - 4;
            com.ea.sims3.SpriteRenderer.a(var1, 76);
            var1.fillRect(var8, var17, var9, 14);
            com.ea.sims3.SpriteRenderer.a(var1, 77);
            var1.drawRect(var8, var17, var9, 14);
            var5.a2(var2.R(var3), 0, var8 + (var9 >> 1), var17 + 0, 17);
            if (var21 != -1) {
               int var19 = var17 + 14;
               com.ea.sims3.SpriteRenderer.a(var1, 76);
               var1.fillRect(var8, var19, var9, 12);
               com.ea.sims3.SpriteRenderer.a(var1, 77);
               var1.drawRect(var8, var19, var9, 12);
               var5.a2(var1, var21, 1, var8 + 2, var19 + 2, var18, 17);
            }
         }
      }
   }

   private void v(int var1, int var2) {
      if (this.matchesInput(var1, var2, 0, 4)) {
         this.ae();
         if (this.q2) {
            this.a17.a(this.b8, this.c8, this.d5);
            this.b8[4] = this.a7;
            this.q2 = false;
            this.b8[2] = this.d4;
            com.ea.sims3.GameCanvas.a2(this.b8);
         } else {
            this.N();
         }
      } else {
         if (this.matchesInput(var1, var2, 4128, 16)) {
            int var3 = 5 + this.b8[2];
            if (a5 == 7) {
               this.J2(this.b8[var3]);
            } else {
               short var4;
               if ((var4 = this.c8[var3]) == -4) {
                  this.N();
               } else if (var4 == -3) {
                  this.i(this.b9);
                  this.N();
               } else if (var4 == -2) {
                  this.d4 = this.b8[2];
                  this.q2 = true;
                  short var5 = this.b8[var3];
                  this.a17.a(this.b8, this.c8, this.d5, var5);
                  this.b8[4] = this.a7;
               } else if (var4 == 137 || var4 == 136) {
                  this.ai();
               } else if (var4 == 111) {
                  this.aj();
               } else {
                  if (var4 != 126) {
                     this.x();
                     this.b(var4, this.c6, 0, 0);
                     return;
                  }

                  this.ak();
               }
            }
         } else {
            super.canvas.b3(this.b8, var1);
         }
      }
   }

   private void A2(int var1) {
      this.V = var1;
      switch (var1) {
         case 0:
         default:
            break;
         case 1:
            this.l2(77, 76);
            break;
         case 2:
            this.l2(71, 69);
            break;
         case 3:
            this.l2(73, 69);
            break;
         case 4:
            this.l2(75, 69);
            break;
         case 5:
            this.ax();
      }

      this.w();
   }

   private void O() {
      if (this.p2) {
         this.N();
      }

      if (this.s2) {
         this.R();
      }

      this.E();
      this.r2 = true;
      this.V = 0;
      this.e5[2] = 0;
      this.w();
   }

   private void P() {
      this.r2 = false;
      this.w();
   }

   private void w(int var1, int var2) {
      GameCanvas var3 = super.canvas;
      if (super.canvas.k2 != -1) {
         var3.d2(var1, var2);
      } else {
         switch (this.V) {
            case 0:
               if (this.matchesInput(var1, var2, 0, 4)) {
                  this.P();
                  return;
               }

               if (this.matchesInput(var1, var2, 4128, 16)) {
                  int var4 = 5 + this.e5[2];
                  this.J2(this.e5[var4]);
                  return;
               }

               var3.b3(this.e5, var1);
               return;
            case 1:
            case 2:
            case 3:
               if (this.matchesInput(var1, var2, 4128, 256)) {
                  if (this.V != 1) {
                     var3.c3();
                     var3.d3();
                  }

                  if (this.V != 2) {
                     this.q(1, -1);
                     return;
                  }

                  this.P();
                  return;
               }

               if (this.matchesInput(var1, var2, 0, 516)) {
                  this.A2(0);
                  return;
               }
               break;
            case 4:
               if (this.matchesInput(var1, var2, 4128, 256)) {
                  var3.c3();
                  var3.d3();
                  this.l2(4);
                  return;
               }

               if (this.matchesInput(var1, var2, 0, 516)) {
                  this.A2(0);
                  return;
               }
               break;
            case 5:
               this.W(var1, var2);
         }
      }
   }

   private void n(Graphics var1) {
      GameCanvas var2 = super.canvas;
      super.canvas.a(var1);
      int var3 = this.V;
      if (var2.k2 != -1) {
         var2.d(var1);
      } else if (var3 == 0) {
         this.a(var1, this.e5, 1);
      } else if (var3 != 2 && var3 != 3 && var3 != 1 && var3 != 4) {
         if (var3 == 5) {
            this.T(var1);
         }
      } else {
         this.b(var1);
      }
   }

   private void Q() {
      this.t2 = false;
      if (this.p2) {
         this.N();
      }

      this.E();
      short[] var1 = this.f4;
      this.s2 = true;
      com.ea.sims3.GameCanvas.a(var1, 35, super.canvas.a22);
      if (this.aq > 0) {
         com.ea.sims3.GameCanvas.c2(var1, 113);
      }

      if (this.a17.a(null, 0)) {
         com.ea.sims3.GameCanvas.c2(var1, 327);
      }

      if (!this.b5()) {
         com.ea.sims3.GameCanvas.c2(var1, 328);
      } else {
         com.ea.sims3.GameCanvas.c2(var1, 124);
      }

      if (!this.b5() && this.a18.a() == 0) {
         com.ea.sims3.GameCanvas.c2(var1, 114);
      } else {
         com.ea.sims3.GameCanvas.c2(var1, 329);
      }

      com.ea.sims3.GameCanvas.c2(var1, 116);
      com.ea.sims3.GameCanvas.c2(var1, 115);
      com.ea.sims3.GameCanvas.c2(var1, 117);
      com.ea.sims3.GameCanvas.c2(var1, 118);
      com.ea.sims3.GameCanvas.c2(var1, 119);
      com.ea.sims3.GameCanvas.c2(var1, 120);
      com.ea.sims3.GameCanvas.c2(var1, 122);
      if (super.canvas.f4) {
         com.ea.sims3.GameCanvas.c2(var1, 123);
      }

      this.w();
   }

   private void R() {
      this.s2 = false;
      this.w();
   }

   private void x(int var1, int var2) {
      GameCanvas var3 = super.canvas;
      if (this.matchesInput(var1, var2, 0, 4)) {
         var3.h2();
         this.R();
      } else if (this.matchesInput(var1, var2, 4128, 16)) {
         int var4 = this.f4[2] + 5;
         this.J2(this.f4[var4]);
      } else {
         var3.b3(this.f4, var1);
      }
   }

   private void o(Graphics var1) {
      super.canvas.a(var1);
      this.a(var1, this.f4, 1);
   }

   private void S() {
      this.I();
      this.u2 = true;
      short[] var1 = this.g3;
      com.ea.sims3.GameCanvas.b2(this.g3, 24);
      int var2 = this.a18.f();

      for (int var3 = 0; var3 < var2; var3++) {
         if (this.a17.d4(var3)) {
            com.ea.sims3.GameCanvas.c2(var1, var3);
         }
      }

      this.B2(this.g3[2]);
      this.w();
   }

   private void T() {
      this.u2 = false;
      this.w();
   }

   private void y(int var1, int var2) {
      if (this.matchesInput(var1, var2, 0, 4)) {
         this.T();
      } else if (this.matchesInput(var1, var2, 4128, 16)) {
         this.T();
         this.F();
      } else if (super.canvas.c3(this.g3, var1)) {
         this.B2(this.g3[2]);
      }

      if (this.matchesInput(var1, var2, 32784, 0)) {
         this.showLeftArrow();
      } else {
         if (this.matchesInput(var1, var2, 65600, 0)) {
            this.showRightArrow();
         }
      }
   }

   private void B2(int var1) {
      short var2 = this.g3[5 + var1];
      Entity var3 = this.a5(var2);
      this.b(var3);
   }

   private void p(Graphics var1) {
      GameCanvas var2 = super.canvas;
      int var3 = com.ea.sims3.SpriteRenderer.d(581, 0);
      int var4 = this.s + (this.u >> 1);
      int var5 = this.t + (var3 >> 1);
      int var6 = this.t + this.v - var3;
      com.ea.sims3.BaseScreen.b2(var1, this.s, this.t, this.u, this.v, 581, -1);
      var2.a2(124, 0, var4, var5, 3);
      int var7 = this.g3[2] + 5;
      short var8 = this.g3[var7];
      int var10 = this.a5(var8).c();
      SdkString var11 = var2.a2();
      var2.f3(this.g3[2] + 1);
      var2.e3(29);
      var2.e3(var10);
      var2.a(var11, 1, var4, var6, 3);
      int var12 = com.ea.sims3.SpriteRenderer.d(559, 0);
      int var13 = var6 - (var12 >> 1);
      int var14 = var2.getWidth() - 3;
      super.leftArrowAnim.draw(var1, 3, var13);
      super.rightArrowAnim.draw(var1, var14, var13);
   }

   private void d(Graphics var1, int var2, int var3) {
      GameCanvas var4 = super.canvas;
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 653, 0, var2, var3);
      int[] var5 = this.c3;
      com.ea.sims3.SpriteRenderer.a2(this.c3, 653, 0, 0);
      SdkString var6 = var4.a2();
      var4.f3(this.g3[2] + 1);
      var4.a(var6, 0, var2 + var5[0], var3 + var5[1], 17);
   }

   private void C2(int var1) {
      Lot var2 = this.a18;
      switch (var1) {
         case 0:
            this.onTick(5);
            break;
         case 1:
            this.w2(2);
            break;
         case 2:
            if (this.W != 7 && this.W != 8 && this.W != 3 && this.W != 4 && this.W != 6) {
               this.U();
            }
            break;
         case 3:
            this.l2(749, 748);
            break;
         case 4:
            if (this.W != 5) {
               this.w2(4);
               this.G();
            }
            break;
         case 5:
            int var6 = var2.i(this.X);
            this.l2(752, var6);
            break;
         case 6:
            this.m2 = 0;
            break;
         case 7:
            int var5 = var2.i(this.X);
            this.l2(750, var5);
            break;
         case 8:
            int var4 = var2.i(this.X);
            this.l2(751, var4);
            break;
         case 9:
            int var3 = this.c6.b();
            this.l2(753, var3);
            break;
         case 10:
            if (var2.b() <= this.a17.e5) {
               this.l2(772, 771);
            } else {
               this.l2(773, 771);
            }
            break;
         case 11:
            this.X = this.c6.a4;
            this.d6 = this.c6;
            this.d6.a(false);
            this.d6.b4();
            var2.c(this.X);
            this.w2(4);
            this.G();
      }

      this.W = var1;
      this.w();
   }

   private void z(int var1, int var2) {
      switch (this.W) {
         case 1:
            this.A(var1, var2);
            return;
         case 2:
            this.B(var1, var2);
            return;
         case 3:
         case 7:
         case 8:
            if (this.matchesInput(var1, var2, 4128, 12)) {
               this.C2(2);
               return;
            }
            break;
         case 4:
         case 11:
            this.C(var1, var2);
            return;
         case 5:
            this.D(var1, var2);
            return;
         case 6:
            return;
         case 9:
            this.E(var1, var2);
            return;
         case 10:
            this.F(var1, var2);
      }
   }

   private void D2(int var1) {
      switch (this.W) {
         case 1:
         case 4:
         case 6:
         case 11:
            this.r2(var1);
            this.v2(var1);
            this.y2(var1);
            this.u(var1, 8);
            if (this.W == 6 && this.m2 > 1500) {
               this.C2(2);
               return;
            }
            break;
         case 2:
            this.v2(var1);
         case 3:
         case 5:
         case 7:
         case 8:
         case 9:
         case 10:
      }
   }

   private void q(Graphics var1) {
      GameCanvas var2 = super.canvas;
      switch (this.W) {
         case 1:
         case 4:
         case 6:
         case 11:
            this.e(var1);
            break;
         case 2:
            this.r(var1);
            break;
         case 3:
         case 7:
         case 8:
            this.r(var1);
            var2.a(var1);
            this.b(var1);
            break;
         case 5:
         case 9:
            int var3;
            short var4;
            int var5;
            if (this.W == 5) {
               var3 = this.a18.i(this.X);
               var4 = 709;
               var5 = this.a18.m(this.X);
            } else {
               var3 = this.a18.i(this.c6.a4);
               var4 = 710;
               var5 = this.a18.n(this.c6.a4);
            }

            this.setValue(1, var3);
            this.setValue(2, var4);
            SdkString var6 = var2.a2();
            var2.g2(var5);
            var2.a2(-9, var6);
            this.setValue(3, -9);
            this.e(var1);
            var2.a(var1);
            this.a(var1, 20);
            this.d(var1);
            break;
         case 10:
            this.s(var1);
      }

      this.d(var1);
   }

   private void A(int var1, int var2) {
      if (this.matchesInput(var1, var2, 0, 4)) {
         this.onTick(5);
      }

      this.s2(var1);
      if (this.matchesInput(var1, var2, 4128, 16)) {
         this.F();
      }
   }

   private void B(int var1, int var2) {
      if (this.matchesInput(var1, var2, 0, 4)) {
         this.onTick(5);
      } else {
         if (this.matchesInput(var1, var2, 4128, 16)) {
            int var3 = super.l;
            int var4 = this.a18.t(var3);
            this.X = var4;
            if (!this.a18.c5(var4)) {
               this.C2(8);
            } else {
               int var5 = this.a18.m(this.X);
               if (this.a17.e5 >= var5) {
                  if (this.a18.c(this.X) > 0) {
                     this.C2(4);
                  } else {
                     this.C2(3);
                  }
               } else {
                  this.C2(7);
               }
            }
         }

         this.a5(var1, var2);
      }
   }

   private void r(Graphics var1) {
      GameCanvas var2 = super.canvas;
      Lot var3 = this.a18;
      int var4 = this.a18.t(super.l);
      if (!var3.c5(var4)) {
         this.setValue(1, 711);
      } else {
         SdkString var5 = var2.a2();
         var2.g2(var3.m(var4));
         var2.a2(-9, var5);
         this.setValue(1, -9);
      }

      this.a(var1, 23);
      this.d(var1);
   }

   public final void a(Graphics var1, int[] var2) {
      int var3;
      int var4 = (var3 = var2[2]) * 3 / 5;
      if (var1 != null) {
         int var5 = var2[0] + var3 - var4;
         int var6 = var2[1];
         int var7 = var2[3];
         this.j(var1, var5, var6, var4, var7);
         Lot var8 = this.a18;
         int var9 = this.a18.t(super.l);
         int var10 = var8.q(var9);
         int var11 = var8.h(var9, 0);
         if (var10 == 196 && var11 != 0 && (var10 = var8.i(var11, 1)) == 196) {
            var10 = var8.i(var11, 2);
         }

         int var12 = com.ea.sims3.SpriteRenderer.a2(var10, 0);
         int var13 = com.ea.sims3.SpriteRenderer.b(var10, 0);
         int var14 = com.ea.sims3.SpriteRenderer.c(var10, 0);
         int var15 = com.ea.sims3.SpriteRenderer.d(var10, 0);
         int var16 = var5 + (var4 >> 1) - var12 - (var14 >> 1);
         int var17 = var6 + (var7 >> 1) - var13 - (var15 >> 1);
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var10, 0, var16, var17);
      }

      var2[2] = var3 - var4 - 3;
   }

   private void U() {
      this.d(this.a18.g(), 15, 0);
   }

   public final void a2(Graphics var1, int var2, int var3, int var4, int var5) {
      GameCanvas var6 = super.canvas;
      Lot var7 = this.a18;
      int var8 = var2 + 3;
      int var9 = var3;
      int var10 = super.k;
      int var11 = super.l;

      while (var9 < var3 + var5 && var10 < super.j) {
         int var12 = var7.t(var10);
         int var13 = var7.i(var12);
         byte var14 = 1;
         if (var10 == var11) {
            this.a(var1, var2, var9, var4, 15, var3 + var5, 82, var10);
            var14 = 0;
         }

         var6.a2(var13, var14, var8, var9 + 7, 6);
         var9 += 15;
         var10++;
         com.ea.sims3.BaseScreen.a2(var1, var2, var9, var4);
      }
   }

   private void C(int var1, int var2) {
      this.s2(var1);
      Entity var3 = this.d6;
      if (this.matchesInput(var1, var2, 0, 4)) {
         this.h2();
         if (this.W == 11) {
            this.h2();
            var3.a(true);
            var3.c2();
            this.d6 = null;
            this.C2(1);
         } else {
            this.C2(2);
         }
      } else {
         if (this.matchesInput(var1, var2, 4128, 2048) && this.c6 != null) {
            if (this.W == 11) {
               Entity var4 = this.c6;
               if (this.c6 != null) {
                  var3.c2();
                  var3.a4(var4.c, var4.d, var4.e);
                  var3.a(true);
                  this.a18.a2(var3.b, var4.c, var4.d, var4.e);
                  this.b(var3);
                  this.d6 = null;
                  this.h2();
                  this.C2(1);
               }

               return;
            }

            this.C2(5);
         }
      }
   }

   private void D(int var1, int var2) {
      if (this.matchesInput(var1, var2, 4128, 256)) {
         Entity var3 = this.c6;
         if (this.c6 != null) {
            Neighborhood var4 = this.a17;
            Lot var5 = this.a18;
            int var6 = this.a18.m(this.X);
            this.b2(var3.c, var3.d, var6);
            var4.a12(-var6);
            int var7 = var5.a(this.X, var3.c, var3.d, var3.e);
            this.h2();
            Entity var8 = this.a(this.X, var3.c, var3.d, var3.e, var7);
            this.b(var8);
            var4.f3(this.X);
            this.C2(6);
         }
      } else {
         if (this.matchesInput(var1, var2, 0, 516)) {
            this.C2(4);
         }
      }
   }

   private void E(int var1, int var2) {
      if (this.matchesInput(var1, var2, 4128, 256)) {
         Entity var3 = this.c6;
         int var4 = this.c6.a4;
         int[] var5 = this.c3;
         this.c6.a(var5);
         this.a18.a12(this.c6.b);
         this.i(this.c6);
         this.c6.a10();
         this.b((Entity) null);
         this.i2 = true;
         this.K = var5[0];
         this.L = var5[1];
         int var6 = this.a18.n(var4);
         this.a17.a12(var6);
         this.C2(1);
      } else {
         if (this.matchesInput(var1, var2, 0, 516)) {
            this.C2(1);
         }
      }
   }

   private void F(int var1, int var2) {
      int var3;
      int var4;
      int var5 = (var4 = ((var3 = super.canvas.e | super.canvas.d2) & 8) != 0 ? 4128 : 0) == 0 ? 4128 : 0;
      if (this.matchesInput(var1, var2, var4, 524)) {
         this.onTick(5);
      }

      if (this.matchesInput(var1, var2, var5, 256)) {
         this.a17.a12(-this.a18.b());
         this.a18.a10();
         this.C2(0);
         this.g2();
      }
   }

   private void s(Graphics var1) {
      GameCanvas var2 = super.canvas;
      this.setValue(1, 771);
      this.setValue(2, 776);
      SdkString var3 = var2.a2();
      var2.g2(this.a18.b());
      var2.a2(-9, var3);
      this.setValue(3, -9);
      this.e(var1);
      var2.a(var1);
      this.a(var1, 20);
      this.d(var1);
   }

   private void E2(int var1) {
      switch (var1) {
         case 0:
            if (this.Y != 9) {
               this.aa = 0;
            }

            this.W();
         case 1:
         case 2:
         default:
            break;
         case 3:
            short var3 = 544;
            if (this.a17.b8 == -1) {
               var3 = 547;
            } else if (!this.a17.g3()) {
               var3 = 545;
            }

            super.canvas.a(var3, 1, super.h);
            this.X();
            break;
         case 4:
            this.Y();
            break;
         case 5:
            this.Z();
            break;
         case 6:
            this.aa();
            break;
         case 7:
            this.onTick(5);
            break;
         case 8:
            this.onTick(5);
            if (!this.t2) {
               this.Q();
               byte var2 = -1;
               switch (this.Y) {
                  case 0:
                     var2 = 116;
                     break;
                  case 1:
                     var2 = 115;
                     break;
                  case 2:
                     var2 = 117;
                     break;
                  case 3:
                     var2 = 118;
                     break;
                  case 4:
                     var2 = 119;
                     break;
                  case 5:
                     var2 = 120;
                     break;
                  case 6:
                     var2 = 122;
               }

               if (var2 != -1) {
                  com.ea.sims3.GameCanvas.d2(this.f4, var2);
               }
            }
            break;
         case 9:
            this.aa = super.l;
            this.V();
            break;
         case 10:
            this.ab();
      }

      this.Y = var1;
      this.w();
   }

   private void V() {
      int var1;
      if ((var1 = this.a()) != this.Z) {
         this.Z = var1;
         GameCanvas var2 = super.canvas;
         int var3 = 98304;
         var2.c2(var3, 1);
         var3 = this.a17.i(var1, 0) == 0 ? '\u8000' : 65536;
         this.a18.a2(var1, this.d2, this.e3);
         var2.a(var3, this.d2, this.e3, 1);
      }
   }

   private void G(int var1, int var2) {
      Neighborhood var3 = this.a17;
      if (this.matchesInput(var1, var2, 3072, 0)) {
         this.onTick(5);
      } else {
         if (this.Y == 9) {
            if (this.matchesInput(var1, var2, 0, 4)) {
               this.E2(0);
               return;
            }

            int var4 = var3.q();
            if (!this.matchesInput(var1, var2, 32784, 0)) {
               if (this.matchesInput(var1, var2, 65600, 0)) {
                  this.aa = (this.aa + 1) % var4;
                  this.showRightArrow();
                  this.V();
               }

               return;
            }

            this.aa = (this.aa + var4 - 1) % var4;
            this.showLeftArrow();
            this.V();
         } else {
            if (this.Y == 10) {
               if (this.matchesInput(var1, var2, 0, 4)) {
                  this.E2(6);
                  return;
               }

               this.a5(var1, var2);
               return;
            }

            if (this.matchesInput(var1, var2, 0, 4)) {
               this.E2(8);
               return;
            }

            switch (this.Y) {
               case 0:
                  this.a5(var1, var2);
                  if (super.j > 0 && this.matchesInput(var1, var2, 4128, 16)) {
                     this.E2(9);
                     return;
                  }
               case 1:
               case 2:
               default:
                  break;
               case 3:
                  this.a5(var1, var2);
                  if (var3.b8 != -1 && var3.g3() && this.matchesInput(var1, var2, 4128, 0)) {
                     var3.d2();
                     super.canvas.a(546, 1, super.h);
                  }

                  if (super.canvas.f4 && this.matchesInput(var1, var2, 1, 0)) {
                     var3.o2(23);
                  }
                  break;
               case 4:
               case 5:
                  this.a5(var1, var2);
                  break;
               case 6:
                  if (this.a5(var1, var2)) {
                     this.w();
                  }

                  int var5;
                  if ((var5 = this.e()) != -1 && (this.a18.w(var5) & 2048) != 0 && this.matchesInput(var1, var2, 4128, 16)) {
                     this.E2(10);
                  }
            }

            if (this.matchesInput(var1, var2, 32784, 0)) {
               this.Y--;
               if (this.Y < 0) {
                  this.Y = 6;
               }

               this.E2(this.Y);
               this.showLeftArrow();
               return;
            }

            if (this.matchesInput(var1, var2, 65600, 0)) {
               this.Y++;
               if (this.Y > 6) {
                  this.Y = 0;
               }

               this.E2(this.Y);
               this.showRightArrow();
            }
         }
      }
   }

   private void F2(int var1) {
      this.advanceArrows(var1);
   }

   private void t(Graphics var1) {
      switch (this.Y) {
         case 0:
            this.a(var1, 14);
            break;
         case 1:
            this.a(var1, false);
            break;
         case 2:
            this.a(var1, 17);
            break;
         case 3:
            this.a(var1, 11);
            break;
         case 4:
            this.u(var1);
            break;
         case 5:
            this.v(var1);
            break;
         case 6:
            this.a(var1, 12);
         case 7:
         case 8:
         default:
            break;
         case 9:
            this.x(var1);
            break;
         case 10:
            this.w(var1);
      }

      this.d(var1);
   }

   private void W() {
      this.d(this.a17.q(), 16, this.aa);
   }

   public final void b2(Graphics var1, int var2, int var3, int var4, int var5) {
      GameCanvas var6 = super.canvas;
      Neighborhood var7 = super.canvas.a23;
      if (super.j == 0) {
         int var20 = var2 + (var4 >> 1);
         int var22 = var3 + 3;
         var6.a2(664, 1, var20, var22, 17);
      } else {
         if (this.ab == 0) {
            int var8 = 0;
            int var9 = var7.b6;

            for (int var10 = 1; var10 < var9; var10++) {
               int var11 = var7.R(var10);
               int var12;
               if ((var12 = var6.a2(var11, 1)) > var8) {
                  var8 = var12;
               }
            }

            this.ab = var8;
         }

         int var19 = var4 - this.ab - 9;
         int var21 = var2 + var4 - var19 - 3;
         int var23 = var2 + 3;
         int var24 = var3;
         int var13 = super.k;
         int var14 = super.l;

         while (var24 < var3 + var5 && var13 < super.j) {
            int var15 = var7.H(var13);
            int var16 = var7.R(var15);
            if (var13 == var14) {
               this.a(var1, var2, var24, var4, 16, var3 + var5, 82, var13);
            }

            var6.a2(var16, 1, var23, var24 + 5, 20);
            int var17 = var7.G(var15);
            int var18 = 65536;
            this.a(var1, var21, var24 + 2, var19, var17, var18, true);
            var24 += 16;
            var13++;
            com.ea.sims3.BaseScreen.a2(var1, var2, var24, var4);
         }
      }
   }

   private void a(Graphics var1, boolean var2) {
      Neighborhood var3 = this.a17;
      int var5 = 0;
      int var6 = 0;
      byte var4;
      if (var2) {
         var4 = 8;
         var5 = this.ag;
         var6 = this.c9;
      } else if ((var5 = var3.V(0)) == -1) {
         var4 = 5;
      } else {
         var4 = 6;
         var6 = var3.W(0);
      }

      int var7 = 115;
      if (var5 == -1) {
         if (var2) {
            var7 = 628;
         }
      } else {
         GameCanvas var8 = super.canvas;
         if (var2) {
            var7 = var5 == var3.V(0) ? 630 : 629;
         }

         this.setValue(1, var7);
         this.setValue(2, var3.q(var5));
         this.setValue(3, var3.b(var5, var6));
         int var9 = var3.c(var5, var6);
         SdkString var10 = var8.a2();
         var8.g2(var9);
         var8.a2(-9, var10);
         this.setValue(4, -9);
         int var11 = var3.s(var5);
         this.setValue(5, this.a18.i(var11));
         int var12 = var3.t(var5);
         this.setValue(6, var3.R(var12));
         int var13 = var3.d(var5, var6);
         var10 = var8.a2();
         SdkString var14 = var8.a4(27);
         int var15 = 0;

         for (int var16 = 0; var16 < com.ea.sims3.Neighborhood.a8.length; var16++) {
            if ((var13 & com.ea.sims3.Neighborhood.a8[var16]) != 0) {
               if (var15 > 0) {
                  var8.a3(var14);
               }

               var8.e3(com.ea.sims3.Neighborhood.c7[var16]);
               var15++;
            }
         }

         var8.a2(-8, var10);
         this.setValue(7, -8);
         var10 = var8.a2();
         int var24 = var3.e(var5, var6);
         var8.h2(var24);
         var8.e3(662);
         int var17 = var3.f(var5, var6);
         var8.h2(var17);
         var8.a2(-7, var10);
         this.setValue(8, -7);
         if (!var2) {
            var10 = var8.a2();
            int var18 = 0;

            for (int var19 = 0; var19 < com.ea.sims3.Neighborhood.d7.length; var19++) {
               if ((var13 & com.ea.sims3.Neighborhood.d7[var19]) != 0) {
                  var8.e3(com.ea.sims3.Neighborhood.e6[var19]);
                  var18++;
               }
            }

            if (var18 > 0) {
               var8.a2(-6, var10);
               this.setValue(9, -6);
            } else {
               this.setValue(9, 664);
            }
         }
      }

      this.setValue(1, var7);
      this.a(var1, var4);
   }

   public final int a(Graphics var1, int var2, int var3, int var4, int var5) {
      GameCanvas var6 = super.canvas;
      Neighborhood var7 = this.a17;
      if (this.ac == 0) {
         int var8 = var7.g();
         int var9 = 0;

         for (int var10 = 0; var10 < var8; var10++) {
            int var11 = var7.f(var10);
            int var12;
            if ((var12 = var6.a2(var11, 1)) > var9) {
               var9 = var12;
            }
         }

         this.ac = var9;
      }

      if (var1 != null) {
         int var14 = var7.f(var5);
         int var15 = var7.e(var5);
         int var16 = var7.j(var5);
         int var17 = var4 - this.ac - 3;
         int var18 = var2 + var4 - var17;
         var6.a2(var14, 1, var2, var3 + 1, 20);
         m(var1, var18, var3 + 1, var17, var15);
         var3 += super.i;
         var6.a2(var16, 1, var18, var3 + 1, 20);
      }

      return super.i << 1;
   }

   private static void m(Graphics var0, int var1, int var2, int var3, int var4) {
      int var5 = var4 - 0 >> 10;
      int var7;
      int var8 = (var7 = var3 - 2) * var5 / 320;
      int var9 = com.ea.sims3.SpriteRenderer.d(657, 0);
      int var10 = var1 + 1;
      int var11 = var2 + 1;
      int var12 = var9 + 2;
      com.ea.sims3.SpriteRenderer.a(var0, 77);
      var0.drawRect(var1, var2, var3 - 1, var12 - 1);
      int var13 = var0.getClipX();
      int var14 = var0.getClipY();
      int var15 = var0.getClipWidth();
      int var16 = var0.getClipHeight();
      var0.setClip(var10, var14, var7, var16);
      com.ea.sims3.SpriteRenderer.drawFrame(var0, 657, 0, var10, var11);
      var0.setClip(var13, var14, var15, var16);
      com.ea.sims3.SpriteRenderer.a(var0, 77);

      for (int var17 = 1; var17 < 5; var17++) {
         int var18 = var1 + var3 * var17 / 5;
         var0.fillRect(var18, var11, 1, var9);
      }

      var0.setClip(var10, var14, var8, var16);
      com.ea.sims3.SpriteRenderer.drawFrame(var0, 658, 0, var10, var11);
      com.ea.sims3.SpriteRenderer.a(var0, 76);

      for (int var19 = 1; var19 < 5; var19++) {
         int var20 = var1 + var3 * var19 / 5;
         var0.fillRect(var20, var11, 1, var9);
      }

      var0.setClip(var13, var14, var15, var16);
   }

   private void X() {
      this.d(4, 27, -1);
   }

   public final int a(Graphics var1, int var2, int var3, int var4) {
      if (var1 != null) {
         int var5 = var2 + 10;
         int var6 = com.ea.sims3.SpriteRenderer.d(565, 0);
         int var7 = 13 - (var6 >> 1);
         int var8 = var4 - 30;
         int var9 = var2 + var4 - var8;
         Neighborhood var10 = this.a17;
         byte var11 = this.a17.b8;
         int var12 = var10.x(var11);
         short var13 = 589;
         if (var11 == -1) {
            var13 = 196;
         }

         super.canvas.a(19, var12, 1, var8, var9, var3 + 13 + 2, 6);
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var13, 0, var5, var3 + var7);
      }

      return 30;
   }

   private void u(Graphics var1) {
      GameCanvas var2 = super.canvas;
      Neighborhood var3 = this.a17;
      this.setValue(1, var3.p());
      int var4 = var3.c();
      SdkString var5 = var2.a2();
      var2.f3(var4);
      var2.a2(-9, var5);
      this.setValue(2, -9);
      var5 = var2.a2();
      var2.g2(var3.f6);
      var2.a2(-8, var5);
      this.setValue(3, -8);
      this.a(var1, 10);
   }

   private void Y() {
      this.d(this.a17.a6, 17, -1);
   }

   public final void c2(Graphics var1, int var2, int var3, int var4, int var5) {
      GameCanvas var6 = super.canvas;
      Neighborhood var7 = super.canvas.a23;
      int var8 = super.canvas.a23.a6;
      if (this.ad == 0) {
         int var9 = 0;

         for (int var10 = 0; var10 < var8; var10++) {
            short var11 = var7.c3(var10);
            int var12;
            if ((var12 = var6.a2(var11, 1)) > var9) {
               var9 = var12;
            }
         }

         this.ad = var9;
      }

      int var17 = var2 + 3;
      int var18 = var4 - this.ad - 9;
      int var19 = var2 + var4 - var18 - 3;
      int var13 = var3;
      int var14 = super.k;

      while (var13 < var3 + var5 && var14 < super.j) {
         short var15 = var7.c3(var14);
         var6.a2(var15, 1, var17, var13 + 5, 20);
         int var16 = var7.y(var14);
         this.n(var1, var19, var13 + 5, var18, var16);
         var13 += 17;
         var14++;
         com.ea.sims3.BaseScreen.a2(var1, var2, var13, var4);
      }
   }

   private void Z() {
      this.d(this.a17.l(), 27, -1);
   }

   private void v(Graphics var1) {
      GameCanvas var2 = super.canvas;
      SdkString var3 = super.canvas.b2();
      var2.a2(-9, var3);
      this.setValue(1, -9);
      short var4 = this.a17.a11(this.a17.a11);
      this.setValue(2, var4);
      this.a(var1, 16);
   }

   private void aa() {
      int var1 = 0;
      if (this.Y == 10) {
         var1 = this.af;
      }

      this.d(this.a17.f(), 18, var1);
   }

   private void ab() {
      this.af = super.l;
      this.ae = this.a18.F(this.e());
      this.d(this.a18.E(this.ae), 18, -1);
   }

   private int e() {
      return this.a17.d(super.l);
   }

   public final void a(Graphics var1, int var2, int var3, int var4, int var5, int var6) {
      GameCanvas var7 = super.canvas;
      Neighborhood var8 = this.a17;
      Lot var9 = this.a18;
      int var10 = var2 + 3 + 8;
      int var11;
      int var12 = (var11 = var2 + 3 + 16 + 3) + 3;
      int var13;
      int var14 = (var13 = var2 + var4 - 3) - 25;
      com.ea.sims3.SpriteRenderer.a(var1, 75);
      var1.fillRect(var11, var3, 1, var5);
      var1.fillRect(var14, var3, 1, var5);
      int var15 = var3;
      int var16 = super.k;
      int var17 = super.l;

      while (var15 < var3 + var5 && var16 < super.j) {
         int var18;
         if (var6 == 5) {
            var18 = var8.d(var16);
         } else if (var6 == 9) {
            var18 = var9.j(this.bl, var16);
         } else {
            var18 = var9.k(this.ae, var16);
         }

         int var19 = var9.u(var18);
         int var20 = var9.v(var18);
         if (var16 == var17) {
            this.a(var1, var2, var15, var4, 18, var3 + var5, 82, var16);
         }

         com.ea.sims3.SpriteRenderer.drawFrame(var1, var20, 0, var10, var15 + 9);
         var7.a2(var19, 1, var12, var15 + 9, 6);
         int var21;
         if ((var21 = var8.c(var18)) > 0 || var6 == 6) {
            SdkString var22 = var7.a2();
            var7.f3(var21);
            var7.e3(27);
            if (var6 == 6) {
               var7.f3(1);
            } else {
               var7.f3(var9.z(var18));
            }

            var7.a(var22, 1, var13, var15 + 9, 10);
         }

         var15 += 18;
         var16++;
         com.ea.sims3.BaseScreen.a2(var1, var2, var15, var4);
      }
   }

   private void w(Graphics var1) {
      GameCanvas var2 = super.canvas;
      Lot var3 = this.a18;
      com.ea.sims3.SpriteRenderer.a(var1, 77);
      var1.fillRect(0, 0, var2.getWidth(), var2.getHeight());
      int var4 = this.d(var1, 4, var2.getWidth() - 8, 122);
      int var5 = var3.D(this.ae);
      int var6 = var3.u(var5);
      this.setValue(1, var6);
      this.b(var1, 13, var4);
   }

   public final int a() {
      return this.a17.H(this.aa);
   }

   private void x(Graphics var1) {
      GameCanvas var2 = super.canvas;
      Neighborhood var3 = this.a17;
      int var4 = this.a();
      com.ea.sims3.SpriteRenderer.a(var1, 77);
      var1.fillRect(0, 0, var2.getWidth(), var2.getHeight());
      int var5 = this.d(var1, 4, var2.getWidth() - 8, 116);
      this.setValue(1, var3.R(var4));
      int var6;
      if ((var6 = var3.S(var4)) == -1) {
         this.setValue(2, 24);
         this.setValue(3, 24);
      } else {
         this.setValue(2, 645);
         this.setValue(3, var3.R(var6));
      }

      int var7 = var3.V(var4);
      int var8 = var3.q(var7);
      this.setValue(4, var8);
      this.b(var1, 15, var5);
   }

   public final int b(Graphics var1, int var2, int var3, int var4) {
      if (var1 != null) {
         Neighborhood var5 = this.a17;
         int var6 = this.a();
         int var8 = var5.G(var6);
         this.a(var1, var2, var3, var4, var8, 65536, true);
         int var7 = var3 + 15;
         int[] var9 = this.c3;
         var5.a2(var6, var9);

         for (byte var10 = 0; var10 < 4 && (var8 = var9[var10]) != -1; var10 += 2) {
            int var11 = var9[var10 + 1];
            var7 += 3;
            this.a(var1, var2, var7, var4, var8, var11, false);
            var7 += 15;
         }
      }

      return 51;
   }

   public final void d2(Graphics var1, int var2, int var3, int var4, int var5) {
      int var6 = this.a();
      int var7 = var2 + var4 - 20;
      int var8 = var3 + var5 - 1;
      this.a(var1, var7, var8, var6, true, 1);
   }

   public final void e(Graphics var1, int var2, int var3, int var4, int var5) {
      int var6 = var2 + var4 - 20;
      int var7 = var3 + var5 - 1;
      this.a(var1, var6, var7, 0, true, 0);
   }

   private static void a(Graphics var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      int var9 = var0.getClipX();
      int var10 = var0.getClipY();
      int var11 = var0.getClipWidth();
      int var12 = var0.getClipHeight();
      int var13 = com.ea.sims3.FixedMath.mul(var3, var4);
      int var14 = com.ea.sims3.SpriteRenderer.c(var5, var8);
      int var15 = com.ea.sims3.SpriteRenderer.c(var6, var8);
      int var16 = Math.min(var3 - var15 - var14, var13 - var14);
      var0.setClip(var1, var10, var13, var12);
      com.ea.sims3.SpriteRenderer.drawFrame(var0, var5, var8, var1, var2);
      com.ea.sims3.SpriteRenderer.drawFrame(var0, var6, var8, var1 + var3 - var15, var2);
      var0.setClip(var1 + var14, var10, var16, var12);
      com.ea.sims3.SpriteRenderer.drawFrame(var0, var7, var8, var1 + var14, var2);
      var0.setClip(var9, var10, var11, var12);
   }

   private void n(Graphics var1, int var2, int var3, int var4, int var5) {
      a(var1, var2, var3, var4, 65536, 664, 665, 663, 0);
      int var6 = var5 < 2293760 ? 2 : 1;
      var5 = com.ea.sims3.FixedMath.div(var5, 6553600);
      a(var1, var2, var3, var4, var5, 664, 665, 663, var6);
   }

   private void a(Graphics var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      var4++;
      int var8;
      int var9 = ((var8 = this.a17.F(var5)) & 2) != 0 ? 1 : ((var8 & 1) != 0 ? 3 : 2);
      int var10 = var7 ? var9 : 0;
      a(var1, var2, var3, var4, 65536, 655, 656, 654, var10);
      if (!var7) {
         int var11 = var9 + 3;
         a(var1, var2, var3, var4, var6, 655, 656, 654, var11);
      }

      int var16 = this.a17.E(var5);
      int var12 = var2 + (var4 >> 1);
      int var13 = var3 + 0;
      int var14 = var7 ? 0 : 1;
      super.canvas.a2(var16, var14, var12, var13, 17);
   }

   public final void a2(int var1, int var2, boolean var3) {
      this.T();
      this.ag = var1;
      this.c9 = var2;
      if (var1 == -1) {
         this.l2(552, 628);
      } else {
         if (!var3 && !this.a17.a5(var1, var2)) {
            this.e2(var1, var2);
            return;
         }

         int var4;
         int var5 = (var4 = this.a17.V(0)) == -1 ? 631 : (var4 == var1 ? 633 : 632);
         super.canvas.a(var5, 1, super.g);
      }

      this.onTick(10);
   }

   private void H(int var1, int var2) {
      int var3;
      int var4 = (var3 = this.ag == -1 ? 4128 : 0) == 0 ? 4128 : 0;
      if (this.matchesInput(var1, var2, var3, 524)) {
         this.onTick(5);
      } else {
         if (this.matchesInput(var1, var2, var4, 256)) {
            Neighborhood var5 = this.a17;
            int var6 = this.a17.V(0);
            int var7 = var5.W(0);
            int var8 = this.ag;
            int var9 = this.c9;
            var5.c2(var8, var9);
            if (var6 != var8 && var8 != -1) {
               var5.e3(20);
            }

            if (var6 == var8 && var7 < var9) {
               var5.e3(54);
            }

            if (var9 == var5.p(var8) - 1) {
               var5.e3(55);
            }
         }
      }
   }

   private void y(Graphics var1) {
      GameCanvas var2 = super.canvas;
      int var3 = this.ag;
      if (this.ag != -1) {
         this.a(var1, true);
      } else {
         this.e(var1);
         var2.a(var1);
         this.b(var1);
      }

      this.d(var1);
   }

   private void ac() {
      if (super.canvas.d() < 5) {
         int var1 = this.a17.i();
         this.d3(var1);
      } else {
         this.onTick(11);
      }
   }

   private void I(int var1, int var2) {
      if (this.matchesInput(var1, var2, 4128, 12)) {
         Neighborhood var3 = this.a17;
         if (this.a17.e3()) {
            int var6 = var3.V(0);
            int var5 = var3.W(0);
            this.a2(var6, var5 + 1, false);
            return;
         }

         if (super.canvas.d() < 15) {
            int var4 = this.a17.r();
            this.L2(var4);
            return;
         }

         this.onTick(5);
      }
   }

   private void z(Graphics var1) {
      GameCanvas var2 = super.canvas;
      this.setValue(1, 703);
      this.setValue(2, 648);
      SdkString var3 = var2.a2();
      var2.g2(this.ah);
      var2.a2(-9, var3);
      this.setValue(3, -9);
      this.e(var1);
      var2.a(var1);
      this.a(var1, 20);
      this.d(var1);
   }

   public final void e2(int var1, int var2) {
      Neighborhood var3 = this.a17;
      this.ag = var1;
      this.c9 = var2;
      int var4 = var3.u(var1);
      super.canvas.a(var4, 1, super.g);
      this.onTick(12);
   }

   private void A(Graphics var1) {
      Neighborhood var2 = this.a17;
      GameCanvas var3 = super.canvas;
      this.e(var1);
      super.canvas.a(var1);
      int var4 = this.ag;
      int var5 = this.c9;
      byte[] var6;
      byte var7 = (var6 = var2.a6(var4, var5))[0];
      byte var8 = var6[1];
      byte var9 = var6[2];
      byte var10 = var6[3];
      int var11 = var2.R(var2.t(var4));
      this.setValue(0, var11);
      this.setValue(1, var11);
      byte var12 = 1;
      if (var7 != -1) {
         var12 = 20;
         this.setValue(2, com.ea.sims3.Neighborhood.m(var7));
         SdkString var13 = var3.a2();
         var3.f3(var2.l(var7));
         var3.e3(27);
         var3.f3(var8);
         var3.a2(-9, var13);
         this.setValue(3, -9);
      }

      if (var9 != -1) {
         var12 = 21;
         this.setValue(4, com.ea.sims3.Neighborhood.m(var9));
         SdkString var14 = var3.a2();
         var3.f3(var2.l(var9));
         var3.e3(27);
         var3.f3(var10);
         var3.a2(-8, var14);
         this.setValue(5, -8);
      }

      this.a(var1, var12);
      this.d(var1);
   }

   private void J(int var1, int var2) {
      if (this.matchesInput(var1, var2, 4128, 8)) {
         this.onTick(5);
      }
   }

   public final void i2() {
      this.v2 = false;
      Neighborhood var1;
      int var2 = (var1 = this.a17).b(var1.V(0), var1.W(0));
      this.l2(705, var2);
      this.onTick(13);
   }

   private void K(int var1, int var2) {
      if (this.matchesInput(var1, var2, 4128, 524)) {
         this.onTick(5);
      } else {
         if (this.matchesInput(var1, var2, 0, 256)) {
            this.a17.c2(-1, 0);
            this.v2 = true;
            this.w();
            this.l2(706, 363);
         }
      }
   }

   private void B(Graphics var1) {
      this.e(var1);
      super.canvas.a(var1);
      this.b(var1);
      this.d(var1);
   }

   public final void j2() {
      this.d7 = true;
   }

   public final void e3(int var1) {
      this.d2(540, this.a17.x(var1));
   }

   private void ad() {
      this.T();
      super.canvas.n2();
      short var1 = 544;
      if (!this.a17.g3()) {
         var1 = 545;
      }

      super.canvas.a(var1, 1, super.h);
      this.onTick(14);
   }

   private void C(Graphics var1) {
      this.e(var1);
      super.canvas.a(var1);
      this.a(var1, 24);
      this.d(var1);
   }

   private void L(int var1, int var2) {
      if (!this.matchesInput(var1, var2, 0, 196)) {
         if (!this.matchesInput(var1, var2, 4128, 8)) {
            return;
         }

         if (this.a17.g3()) {
            this.a17.d2();
         }
      }

      this.onTick(5);
   }

   public final void f3(int var1) {
      this.a16[var1].play(590, 16);
   }

   public final void f2(int var1, int var2) {
      this.d2(541, this.a17.x(var1));
      this.a14.a(9, null, 0, 0);
      if (var2 != -1) {
         this.a16[var2].play(591, 16);
      }
   }

   public final void k2() {
      this.w2 = true;
   }

   public final void g2(int var1) {
      this.d2(this.a17.R(var1), 537);
   }

   public final void b2(int var1, int var2, int var3, int var4) {
      this.ai = var1;
      this.aj = var2;
      this.ak = var3;
      if (this.a17.c(var1) == this.a18.z(var1)) {
         var4 = 717;
      }

      this.al = var4;
   }

   private void D(Graphics var1) {
      this.e(var1);
      super.canvas.a(var1);
      this.a(var1, 25);
      this.d(var1);
   }

   private void M(int var1, int var2) {
      if (this.matchesInput(var1, var2, 4128, 8)) {
         this.ai = -1;
         this.onTick(5);
      }
   }

   public final int c(Graphics var1, int var2, int var3, int var4) {
      if (var1 != null) {
         Lot var5 = this.a18;
         GameCanvas var6 = super.canvas;
         int var7 = this.ai;
         int var8 = var2 + 3 + 8;
         int var9 = var3 + 9;
         int var11 = var2 + 3 + 16 + 3 + 3;
         int var12 = var3 + 9;
         int var13 = var2 + var4 - 3;
         int var14 = var5.v(var7);
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var14, 0, var8, var9);
         SdkString var15 = var6.a2();
         if (this.aj != 1) {
            var6.f3(this.aj);
            var6.e3(718);
         }

         var6.e3(var5.u(var7));
         super.canvas.a(var15, 1, var11, var12, 6);
         var15 = var6.a2();
         var6.f3(this.a17.c(var7));
         var6.e3(27);
         var6.f3(var5.z(var7));
         var6.a(var15, 1, var13, var12, 10);
      }

      return 18;
   }

   public final boolean d3() {
      return this.ap > 20000;
   }

   private void ae() {
      this.ap = 0;
      this.a14.G();
   }

   private void G2(int var1) {
      GameCanvas var2 = super.canvas;
      Neighborhood var3 = this.a17;
      this.am += var1;
      short var4 = 3000;
      if (var3.a9) {
         var4 = 1500;
      }

      if (this.am > var4) {
         this.am = 0;
         int var5 = this.a18.a();
         int var6 = var3.b6;

         for (int var7 = 1; var7 < var6; var7++) {
            int var8 = var3.o(var7);
            Sim var9;
            if ((var9 = this.a7(var7)) != null) {
               if ((var8 & 7) != 0) {
                  var9.s2(1);
               }

               if ((var8 & 16) != 0) {
                  if (var5 == var3.U(var7)) {
                     var9.s2(4);
                  } else {
                     var9.s2(256);
                  }
               }
            } else if ((var8 & 8) != 0 && var5 == var3.U(var7) && this.c() != -1) {
               Entity var10 = this.a5(4);
               var9 = (Sim)this.a(1, var10, var7);
               int var11 = (var2.d() < 50 ? 2 : 0) | (var2.d() < 50 ? 8 : 0) | (var2.d() < 50 ? 64 : 0) | (var2.d() < 50 ? 32 : 0) | (var2.d() < 50 ? 16 : 0);
               var9.s2(var11);
            }
         }

         Sim[] var14 = this.a13();

         for (int var15 = 0; var15 < var14.length; var15++) {
            int var17 = var2.b(0, var14.length - 1);
            Sim var18;
            if ((var18 = var14[var17]).c3() && var18.a11() && var18.a4 != 0) {
               int var19 = (var2.d() < 50 ? 2 : 0) | (var2.d() < 25 ? 8 : 0) | (var2.d() < 50 ? 64 : 0) | (var2.d() < 25 ? 16 : 0) | (var2.d() < 60 ? 32 : 0);
               if (var5 != var3.U(var18.b)) {
                  var19 &= -19;
               }

               var18.s2(var19);
            }
         }
      }

      Sim var12 = this.a14;
      if (var2.h2) {
         if (!this.d3()) {
            this.ap += var1;
            return;
         }

         if (var12.c3()) {
            int var13 = var3.n();
            if (this.a18.a() != 0 && !var3.b3(0, 1)) {
               var13 |= 32;
            }

            var12.s2(var13);
            return;
         }
      } else {
         var12.G();
      }
   }

   private void H2(int var1) {
      GameCanvas var2 = super.canvas;
      Neighborhood var3 = this.a17;
      this.am += var1;
      short var4 = 5000;
      if (var3.a9) {
         var4 = 2500;
      }

      if (this.am > var4) {
         this.am = 0;
         Sim[] var5 = this.a13();
         byte var6 = 50;
         if (var5.length >= 2) {
            var6 = 5;
         }

         boolean var7 = false;
         int var8 = var3.b6;

         for (int var10 = 0; var10 < 4; var10++) {
            int var11 = var2.b(1, var8 - 1);
            Sim var12 = this.a7(var11);
            int var13 = var3.o(var11);
            if (var12 != null) {
               if ((var13 & 7) != 0) {
                  var12.s2(1);
               }

               if ((var13 & 16) != 0) {
                  var12.s2(256);
               }
            } else if ((var13 & 8) != 0 && (var13 & 16) == 0 && this.c() != -1 && !var7) {
               var7 = true;
               if (var2.d() < var6) {
                  Entity var14 = this.a5(4);
                  ((Sim)this.a(1, var14, var11)).s2(32);
               }
            }
         }

         for (int var15 = 0; var15 < var5.length; var15++) {
            Sim var16;
            if ((var16 = var5[var15]).b != 0) {
               var16.s2(32);
            }
         }
      }
   }

   private void I2(int var1) {
      this.am += var1;
      if (this.am > 5000) {
         this.am = 0;
         Neighborhood var2 = this.a17;
         Lot var3 = this.a18;
         int var4 = super.canvas.b(1, var2.b6 - 1);
         Sim var5;
         if ((var5 = this.a7(var4)) == null) {
            int var6 = var2.U(var4);
            int var7 = var3.a3(var6);
            Entity var8 = this.a5(var7);
            new StringBuffer().append("can't find an NPC house: ").append(var6).toString();
            var5 = (Sim)this.a(1, var8, var4);
         }

         if (var5.c3()) {
            Entity var9 = this.a3();
            var5.a(106, var9);
         }
      }
   }

   private void a(int var1, int var2, short[] var3) {
      this.an += var1;
      if (this.an > var2) {
         this.an = 0;
         int var4 = super.canvas.b(0, var3.length - 1);
         super.canvas.a7.pendingSound = var3[var4];
      }
   }

   private Entity a3() {
      Entity[] var1 = this.a12();
      int var2 = super.canvas.b(0, var1.length - 1);

      for (int var3 = 0; var3 < var1.length; var3++) {
         Entity var4;
         if ((var4 = var1[var2]).a13(2048)) {
            return var4;
         }

         while ((var2 = (1543 * var2 + 3571) % 257) >= var1.length) {
         }
      }

      return null;
   }

   public final void a(Sim var1) {
      Entity[] var2 = this.a12();
      Entity var3 = null;
      Entity var4 = null;

      for (int var5 = 0; var5 < var2.length; var5++) {
         Entity var6;
         if ((var6 = var2[var5]).a() == 4) {
            if (var1.a3(var6)) {
               var3 = var6;
               break;
            }
         } else if (var6.a() == 9 && var6.a11()) {
            var4 = var6;
         }
      }

      if (var3 == null) {
         var3 = var4;
      }

      if (var3 != null) {
         var1.c(var3);
      } else {
         if (var1.b == 0) {
            var1.a(6, (Entity) null);
         }
      }
   }

   public final void a(int var1, Sim var2, Entity var3) {
      Neighborhood var4 = this.a17;
      int var5 = this.a18.a();
      Sim[] var6 = this.a13();
      int var7 = var4.K(var1);
      if (var1 == 23) {
         var4.g2(0, 1);
      }

      int var8 = var6.length;

      for (int var9 = 0; var9 < var8; var9++) {
         Sim var10;
         int var11;
         if ((var11 = (var10 = var6[var9]).b) != 0
            && var10 != var3
            && var10 != var2
            && ((var7 & 131072) == 0 || var4.U(var11) == var5)
            && var10.a2(var1, var2, var3)
            && (var7 & 4194304) != 0) {
            return;
         }
      }
   }

   private void J2(int var1) {
      switch (var1) {
         case 60:
            super.canvas.r2(1);
            return;
         case 61:
            super.canvas.r2(3);
            return;
         case 67:
            this.P();
            return;
         case 70:
            this.A2(2);
            return;
         case 72:
            this.A2(3);
            return;
         case 74:
            this.A2(4);
            return;
         case 76:
            this.A2(1);
            return;
         case 113:
            this.x();
            this.ah();
            return;
         case 114:
            this.x();
            this.onTick(7);
            this.C2(1);
            return;
         case 115:
            this.x();
            this.onTick(8);
            this.E2(1);
            return;
         case 116:
            this.x();
            this.onTick(8);
            this.E2(0);
            return;
         case 117:
            this.x();
            this.onTick(8);
            this.E2(2);
            return;
         case 118:
            this.x();
            this.onTick(8);
            this.E2(3);
            return;
         case 119:
            this.x();
            this.onTick(8);
            this.E2(4);
            return;
         case 120:
            this.x();
            this.onTick(8);
            this.E2(5);
            return;
         case 122:
            this.x();
            this.onTick(8);
            this.E2(6);
            return;
         case 123:
            this.aw();
            return;
         case 124:
            this.x();
            this.S();
            return;
         case 274:
         case 275:
            this.x();
         default:
            return;
         case 324:
            this.x();
            this.C2(9);
            return;
         case 325:
            this.x();
            this.C2(11);
            return;
         case 326:
            this.x();
            this.c6.D();
            this.a18.a4(this.c6.b, this.c6.e);
            return;
         case 327:
            if (this.b5()) {
               this.x();
               int var2 = this.a17.V(0);
               int var3 = this.a17.s(var2);
               Entity var4 = this.a5(var3);
               this.a(159, var4, 0, 0);
               return;
            }

            this.z();
            return;
         case 328:
            this.f2();
            return;
         case 329:
            this.y();
      }
   }

   private void af() {
      this.aq = 0;
      if (this.f5 == null) {
         this.f5 = new int[3];
         this.b10 = new Entity[3];
         this.g4 = new int[3];
         this.h4 = new int[3];
      }

      com.ea.sims3.GameCanvas.a(this.f5, -1);
      com.ea.sims3.GameCanvas.a(this.b10, (Object) null);
   }

   private void ag() {
      Sim var1 = this.a14;
      int[] var2 = this.f5;
      Entity[] var3 = this.b10;
      if (this.aq > 0) {
         if (var1.c3()) {
            this.aq--;

            for (int var4 = 0; var4 < 2; var4++) {
               var2[var4] = var2[var4 + 1];
               var3[var4] = var3[var4 + 1];
               this.g4[var4] = this.g4[var4 + 1];
               this.h4[var4] = this.h4[var4 + 1];
            }

            var3[this.aq] = null;
            if (this.aq > 0) {
               int var8 = var2[0];
               Entity var5 = var3[0];
               int var6 = this.g4[0];
               int var7 = this.h4[0];
               this.a(var8, var5, var6, var7);
               return;
            }
         } else if (var2[0] != var1.g2) {
            int var9 = var1.g2;
            if ((this.a17.K(var9) & 262144) == 0) {
               var2[0] = var9;
               var3[0] = var1.b6;
            }

            return;
         }
      } else {
         int var10 = var1.g2;
         if ((this.a17.K(var10) & 262144) == 0) {
            var2[0] = var1.g2;
            var3[0] = var1.b6;
            this.aq = 1;
         }
      }
   }

   private void b(int var1, Entity var2, int var3, int var4) {
      this.ae();
      if (this.a14.c3()) {
         this.a(var1, var2, var3, var4);
      } else {
         int var5;
         if (this.aq == 3) {
            var5 = 2;
         } else {
            var5 = this.aq++;
         }

         this.f5[var5] = var1;
         this.b10[var5] = var2;
         this.g4[var5] = var3;
         this.h4[var5] = var4;
      }
   }

   public final void a(int var1, Entity var2, int var3, int var4) {
      Sim var5 = this.a14;
      this.a14.b(var1, var2, var3, var4);
      Sim[] var6 = this.a13();

      for (int var7 = 0; var7 < var6.length; var7++) {
         Sim var8;
         if ((var8 = var6[var7]) != var5) {
            var8.b(var2);
         }
      }
   }

   private void ah() {
      this.ae();
      if (this.aq > 0) {
         this.aq--;
         this.b10[this.aq] = null;
         if (this.aq == 0) {
            this.a14.F();
         }
      }
   }

   private void i(Entity var1) {
      this.ae();
      if (var1 != null && this.aq > 0) {
         int var2;
         if ((var2 = com.ea.sims3.GameCanvas.a(var1, this.b10)) == 0) {
            this.a14.F();
            return;
         }

         if (var2 > 0) {
            this.aq--;

            for (int var3 = var2; var3 < this.aq; var3++) {
               this.f5[var3] = this.f5[var3 + 1];
               this.b10[var3] = this.b10[var3 + 1];
               this.g4[var3] = this.g4[var3 + 1];
               this.h4[var3] = this.h4[var3 + 1];
            }

            this.b10[this.aq] = null;
         }
      }
   }

   private void ai() {
      int var1 = this.b8[2] + 5;
      short var2 = this.c8[var1];
      short var3;
      if ((var3 = this.b8[var1]) != 309 && var3 != 308) {
         this.x();
         int var4 = this.a17.Y(var3);
         this.b(var2, this.c6, var4, 0);
      } else {
         this.d4 = this.b8[2];
         this.q2 = true;
         this.a17.c(this.b8, this.c8, var2);
         this.b8[4] = this.a7;
         if (this.b8[0] == 0) {
            this.x();
            this.c2(761, 760);
            return;
         }
      }
   }

   public final void g2(int var1, int var2) {
      Neighborhood var3 = this.a17;
      if (var1 == 137) {
         if (this.a7(var2) != null) {
            this.c2(762, 309);
         } else if (this.c() != -1 && var3.e4(var2)) {
            Entity var6 = this.a5(4);
            ((Sim)this.a(1, var6.c, var6.d, var6.e, var2)).s2(32);
            this.c2(763, 309);
         } else {
            this.c2(764, 309);
         }
      } else {
         if (var1 == 136) {
            if (this.a7(var2) != null) {
               this.c2(762, 308);
               return;
            }

            int var4 = var3.G(var2);
            if ((var3.F(var4) & 16) != 0) {
               var3.d2(4, 983040);
            }
         }
      }
   }

   private void aj() {
      int var1 = this.b8[2] + 5;
      short var2 = this.c8[var1];
      short var3;
      if ((var3 = this.b8[var1]) == 284) {
         this.d4 = this.b8[2];
         this.q2 = true;
         this.a17.a(this.b8, this.c8, var2);
         this.b8[4] = this.a7;
         if (this.b8[0] == 0) {
            this.x();
            this.c2(783, 782);
            return;
         }
      } else {
         this.x();
         int var4 = this.a18.A(var3);
         this.b(var2, this.c6, var4, 0);
      }
   }

   private void ak() {
      int var1 = this.b8[2] + 5;
      short var2 = this.c8[var1];
      short var3;
      if ((var3 = this.b8[var1]) == 300) {
         this.d4 = this.b8[2];
         this.q2 = true;
         this.a17.b(this.b8, this.c8, var2);
         this.b8[4] = this.a7;
         if (this.b8[0] == 0) {
            this.x();
            this.c2(781, 780);
            return;
         }
      } else {
         this.x();
         int var4 = this.a18.H(var3);
         this.b(var2, this.c6, var4, 0);
      }
   }

   public final void l2() {
      this.x();
      this.onTick(7);
      this.C2(2);
   }

   public final void m2() {
      this.x();
      int var1;
      if ((var1 = this.a18.b()) == -1) {
         this.c2(775, 310);
      } else if (var1 == 0) {
         this.c2(774, 310);
      } else {
         this.onTick(7);
         this.C2(10);
      }
   }

   private void K2(int var1) {
      Neighborhood var2 = this.a17;
      if (this.as != 0) {
         var2.n2(this.as * 1000);
         this.as = 0;
      } else {
         int var3 = var1;
         if (var2.a9) {
            var3 = var1 << 4;
         }

         var2.n2(var3);
      }

      int var5 = var2.h6;
      this.ar = com.ea.sims3.BaseScreen.a(this.ar, var5, var1, 8);
      if (!this.a14.m3()) {
         int var4;
         if (((var4 = var2.o(0)) & 1) == 0 || this.j3()) {
            if ((var4 & 2) != 0 && !this.j3()) {
               this.d2(639, -1);
            }

            return;
         }

         this.d2(638, -1);
      } else {
         this.al();
      }
   }

   private void al() {
      this.q2(639);
      this.q2(638);
   }

   public final void h2(int var1, int var2) {
      Neighborhood var3 = this.a17;
      if (!this.a14.m3() && var3.V(0) != -1) {
         for (int var4 = var1; var4 < var2; var4++) {
            int var5 = var3.a2(0, var4);
            int var6 = var3.a2(0, var4 + 1);
            if ((var5 & 2) != 0 && (var6 & 4) != 0) {
               var3.c2();
               this.al();
               if (var3.V(0) == -1) {
                  this.a4(643, 642, 2);
                  return;
               }

               this.a4(641, 640, 2);
               return;
            }
         }
      }
   }

   public final void a(Graphics var1, int var2, int var3) {
      if (a5 != 19) {
         this.g5.draw(var1, var2, var3);
      }
   }

   private void E(Graphics var1) {
      GameCanvas var2 = super.canvas;
      Neighborhood var3 = this.a17;
      int var4 = this.s;
      int var5 = this.s + 8;
      int var6 = var2.getHeight() - 15 - this.r;
      if (this.b6 != -1) {
         var6 -= com.ea.sims3.SpriteRenderer.d(670, 0);
      }

      for (int var7 = 0; var7 < 6; var7++) {
         int var8;
         if ((var8 = var3.A(var7)) != -1) {
            int var9 = var3.B(var8);
            int var10 = var3.C(var8);
            int var11 = var2.a2(var9, 1);
            com.ea.sims3.SpriteRenderer.a(var1, 76);
            var1.fillRect(var4, var6 - 12, var11 + var5 - var4 + 4, 12);
            ((var10 & 1) == 0 ? this.e4 : this.d3).draw(var1, var4, var6 + -1);
            var2.a2(var9, 1, var5, var6 + 1, 36);
            var6 -= 12;
         }
      }
   }

   private void L2(int var1) {
      this.at = var1;
      this.au = -1;
      this.aw = -1;
      this.onTick(18);
      this.M2(0);
   }

   public final void i2(int var1, int var2) {
      this.at = var1;
      this.au = var2;
      this.onTick(18);
      this.M2(2);
   }

   public final void j2(int var1, int var2) {
      this.x2 = true;
      this.at = var1;
      this.au = var2;
   }

   private void M2(int var1) {
      Neighborhood var2 = this.a17;
      this.av = var1;
      int var3 = this.at;
      int var4 = -1;
      switch (var1) {
         case 0:
            var4 = var2.N(var3);
            break;
         case 1:
            var4 = var2.h(var3, this.aw);
            break;
         case 2:
            var4 = var2.O(var3);
            break;
         case 3:
            var4 = var2.P(var3);
            break;
         case 4:
            this.onTick(5);
      }

      if (var4 != -1) {
         int var5 = 0;
         if (this.au != -1) {
            var5 = var2.R(this.au);
         } else {
            var5 = 816;
         }

         this.l2(var4, var5);
      }

      this.w();
   }

   private void F(Graphics var1) {
      this.e(var1);
      super.canvas.a(var1);
      this.b(var1);
      this.d(var1);
   }

   private void N(int var1, int var2) {
      Neighborhood var3 = this.a17;
      switch (this.av) {
         case 0:
            if (this.matchesInput(var1, var2, 4128, 256)) {
               this.aw = var3.a(this.at, true);
               this.M2(1);
               return;
            }

            if (this.matchesInput(var1, var2, 0, 516)) {
               this.aw = var3.a(this.at, false);
               this.M2(1);
               return;
            }
            break;
         case 1:
            if (this.matchesInput(var1, var2, 4128, 12)) {
               this.M2(4);
               var3.h2(this.at, this.aw);
               return;
            }
            break;
         case 2:
            if (this.matchesInput(var1, var2, 4128, 8)) {
               int var4;
               if (((var4 = var3.Q(this.at)) & 2) != 0) {
                  this.a2((byte)6);
               } else if ((var4 & 4) != 0) {
                  this.a2((byte)7);
               }

               this.M2(4);
               return;
            }
            break;
         case 3:
            if (this.matchesInput(var1, var2, 4128, 8)) {
               var3.k2(this.at);
               this.M2(4);
            }
      }
   }

   public final boolean e3() {
      return this.ax != 0 && this.ax != 1 && this.ax != 5;
   }

   public final void c2(int var1, int var2, int var3) {
      GameCanvas var4 = super.canvas;
      ResourceLoader var5 = super.canvas.a6;
      Neighborhood var6 = this.a17;
      int[] var7 = this.d2;
      int[] var8 = this.e3;
      Lot var9 = this.a18;
      var6.f2();
      super.canvas.n2();
      this.ay = var1;
      this.onTick(19);
      this.N2(1);
      int var10 = 0;
      int var11 = -1;
      int var12 = -1;
      switch (var1) {
         case 0:
            var11 = var2;
            var10 = 524288;
            break;
         case 1:
            var11 = 790;
            var10 = 1048576;
            break;
         case 2:
            var11 = 789;
            var10 = 2097152;
            break;
         case 3:
            var11 = 791;
            var10 = 4194304;
            break;
         case 4:
            var11 = 792;
            var12 = var2;
            var10 = 8388608;
            break;
         case 5:
            var11 = 794;
            var10 = 16777216;
            break;
         case 6:
            var11 = 795;
            var12 = var2;
            var10 = 33554432;
            break;
         case 7:
            var11 = 797;
            var12 = var2;
            var10 = 33554432;
            break;
         case 8:
            var11 = var6.a11(var6.a11);
            var12 = 799;
            var10 = 67108864;
      }

      com.ea.sims3.SpriteRenderer.a(var5, 14);
      com.ea.sims3.SpriteRenderer.a(var5, 13);
      if (var10 == 33554432) {
         var9.a2(0, var7, var8);
         com.ea.sims3.SpriteRenderer.a(var5, 7, var7, var8, 0);
         int var13 = var6.t(this.ag);
         var9.a2(var13, var7, var8);
         com.ea.sims3.SpriteRenderer.a(var5, 8, var7, var8, 0);
      } else if (var10 == 1048576) {
         var9.a2(0, var7, var8);
         com.ea.sims3.SpriteRenderer.a(var5, 11, var7, var8, 0);
         var9.a2(var3, var7, var8);
         com.ea.sims3.SpriteRenderer.a(var5, 12, var7, var8, this.a7(var3).f2);
      } else if (var10 == 8388608) {
         var9.a2(0, var7, var8);
         com.ea.sims3.SpriteRenderer.a(var5, 16, var7, var8, 0);
      } else {
         super.canvas.j2(var10);
      }

      this.az = var11;
      this.aA = var12;
      this.aB = var3;
   }

   private void am() {
      int var1 = 133693440;
      if (this.b5()) {
         var1 &= -16777217;
      }

      super.canvas.c2(var1, -1);
      switch (this.ay) {
         case 0:
            super.canvas.b2(1, -1);
            return;
         case 1:
         case 4:
         default:
            this.onTick(5);
            return;
         case 2:
            this.onTick(5);
            return;
         case 3:
            if (this.a17.h3()) {
               this.d3(804);
               this.N2(2);
               return;
            }

            this.onTick(5);
            return;
         case 5:
            if (this.a18.a11()) {
               this.a17.e3(63);
            }

            this.onTick(5);
      }
   }

   private void N2(int var1) {
      this.ax = var1;
      this.m2 = 0;
      switch (var1) {
         case 1:
            this.e6 = this.c6;
            this.b((Entity)this.a14);
         case 2:
         case 3:
         case 4:
         default:
            break;
         case 5:
            this.b(this.e6);
            this.e6 = null;
            this.am();
      }

      this.w();
   }

   private void O2(int var1) {
      this.aC += var1;
      switch (this.ax) {
         case 1:
            this.y2(var1);
            if (this.n2) {
               this.b((Entity) null);
               this.N2(2);
               return;
            }
            break;
         case 2:
            if (this.m2 > 500) {
               this.N2(3);
               return;
            }
         case 3:
         default:
            break;
         case 4:
            if (this.m2 > 1000) {
               this.N2(5);
            }
      }
   }

   private void O(int var1, int var2) {
      switch (this.ax) {
         case 3:
            if (this.matchesInput(var1, var2, 4128, 8)) {
               this.N2(4);
            }
      }
   }

   private void G(Graphics var1) {
      GameCanvas var2 = super.canvas;
      switch (this.ax) {
         case 1:
            this.e(var1);
            this.d(var1);
            break;
         case 2:
         case 3:
         case 4:
            this.H(var1);
            var2.b(var1);
      }

      if (this.ax == 2) {
         int var5 = 255 - Math.min(this.m2 * 255 / 500, 255);
         int var6 = 0xFF000000 | var5 << 16 | var5 << 8 | var5 << 0;
         var2.b2(var1, 65, var6, 0, 0, var2.getWidth(), var2.getHeight());
      } else {
         if (this.ax == 4) {
            int var3 = 255 - Math.min(this.m2 * 255 / 1000, 255);
            int var4 = 0xFF000000 | var3 << 16 | var3 << 8 | var3 << 0;
            var2.b2(var1, 66, var4, 0, 0, var2.getWidth(), var2.getHeight());
         }
      }
   }

   private void H(Graphics var1) {
      GameCanvas var2 = super.canvas;
      Neighborhood var3 = this.a17;
      if (com.ea.sims3.GameCanvas.b) {
         com.ea.sims3.SpriteRenderer.drawFrame(var1, 46, 0, var2.a(), var2.b() - 7);
      } else {
         com.ea.sims3.SpriteRenderer.drawFrame(var1, 45, 0, var2.a(), var2.b() - 7);
      }

      SdkString var4 = null;
      if (this.aB != -1) {
         var4 = var2.a4(this.a17.R(this.aB));
      }

      switch (this.ay) {
         case 0:
            this.a(var1, 63, var2.b2(), var2.a4(800));
            return;
         case 1:
            this.e(var1, 60, 57);
            this.a(var1, 68, var2.b2(), var4);
            this.I(var1);
            return;
         case 2:
            this.e(var1, 57, 196);
            this.a(var1, 67, var2.b2(), var4);
            this.J(var1);
            return;
         case 3:
            this.e(var1, 57, 62);
            this.a(var1, 82, var2.b2(), var4);
            return;
         case 4:
            int var12 = this.aB;
            int var6 = var3.g(var12);
            int var7 = var3.h(var12);
            int var8 = var3.i(var12);
            int var9 = var3.i(0, 0) == 0 ? 81 : 80;
            this.e(var1, var6, var7);
            this.a(var1, var8, null, null);
            com.ea.sims3.SpriteRenderer.drawFrame(var1, var9, 0, var2.a(), var2.b());
            return;
         case 5:
            this.e(var1, 61, 88);
            this.a(var1, 66, null, null);
            return;
         case 6:
            int var11 = var3.r(var3.V(0));
            this.e(var1, 61, var11);
            this.a(var1, 64, null, null);
            return;
         case 7:
            int var10 = var3.r(var3.V(0));
            this.e(var1, 61, var10);
            this.a(var1, 65, null, null);
            return;
         case 8:
            int var5 = var3.k();
            this.e(var1, 87, var5);
            this.a(var1, 75, null, null);
      }
   }

   private void a(Graphics var1, int var2, SdkString var3, SdkString var4) {
      GameCanvas var5 = super.canvas;
      int var6 = super.canvas.getWidth();
      int var7 = var5.getHeight();
      int var8 = this.ay;
      int var9 = 0;
      if (this.ay == 0) {
         var9 = var7 / 10;
      }

      int var10 = var6 >> 1;
      int var11 = (var7 >> 1) + var9;
      com.ea.sims3.SpriteRenderer.drawFrame(var1, var2, 0, var10, var11);
      int[] var12 = super.a2;
      if (var3 != null) {
         com.ea.sims3.SpriteRenderer.a2(var12, var2, 0, 0);
         var5.a(var3, 1, var10 + var12[0], var11 + 1 + var12[1] - 13, 17);
      }

      if (var4 != null) {
         com.ea.sims3.SpriteRenderer.a2(var12, var2, 0, 1);
         var5.a(var4, 1, var10 + var12[0], var11 + 1 + var12[1] + 13, 17);
      }

      if (var8 == 8) {
         com.ea.sims3.SpriteRenderer.a2(var12, var2, 0, 0);
         this.a(var1, var10 + var12[0], var11 + var12[1], 0, true, 0);
      } else if (var8 == 5) {
         int var13 = this.a18.c();
         com.ea.sims3.SpriteRenderer.a2(var12, var13, 0, 1);
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var13, 0, var10 - var12[0], var11 - var12[1]);
      }

      if (this.ax == 3) {
         if (this.az != -1) {
            int var16 = com.ea.sims3.SpriteRenderer.d(var2, 0);
            int var14;
            int var15 = (var14 = var11 - (var16 >> 1)) >> 1;
            if (var8 != 3 && var8 != 7) {
               var5.a(0, this.az, 0, super.g, var10, var15, 3);
            } else {
               var5.b(0, this.az, 0, var10, var15, 3);
            }
         }

         if (this.aA != -1) {
            int var17 = var7 - 15 - (var11 + (com.ea.sims3.SpriteRenderer.d(var2, 0) >> 1));
            int var18 = var7 - 15 - (var17 >> 1);
            var5.a(0, this.aA, 0, super.g, var10, var18, 3);
         }
      }
   }

   private void e(Graphics var1, int var2, int var3) {
      GameCanvas var4 = super.canvas;
      int var5 = super.canvas.getHeight() - 15;
      int var6;
      int var7 = (var6 = var4.getWidth()) / 3 >> 1;
      int var8 = var6 - var7;
      int var9 = var6 * 15 / 100;
      int var10 = Math.max(com.ea.sims3.SpriteRenderer.d(var2, 0), com.ea.sims3.SpriteRenderer.d(var3, 0));
      int var11 = var5 + var10;
      int var12 = -(this.aC * 850 % 5898240);
      int var13 = var2;
      if (var12 < -2949120) {
         var12 += 2949120;
         var13 = var3;
      }

      int var15 = this.aC * 125 % 411775 - com.ea.sims3.FixedMath.div(com.ea.sims3.FixedMath.mul(164709, -var12), 2949120);
      int var16 = var12 >> 16;
      int var17 = var15;

      for (int var18 = var13; var16 < var11; var18 = var18 == var2 ? var3 : var2) {
         int var20 = com.ea.sims3.FixedMath.mul(var9, com.ea.sims3.FixedMath.cos(var17));
         if (var18 == var3 && this.ay == 7) {
            com.ea.sims3.SpriteRenderer.drawFrame(var1, 91, 0, var7 + var20, var16 + -15);
         }

         com.ea.sims3.SpriteRenderer.drawFrame(var1, var18, 0, var7 + var20, var16);
         int var19 = var17 + 82354;
         var20 = com.ea.sims3.FixedMath.mul(var9, com.ea.sims3.FixedMath.cos(var19));
         int var21 = var16 + 22;
         if (var18 == var3 && this.ay == 7) {
            com.ea.sims3.SpriteRenderer.drawFrame(var1, 91, 0, var8 + var20, var21 + -15);
         }

         com.ea.sims3.SpriteRenderer.drawFrame(var1, var18, 0, var8 + var20, var21);
         var16 += 45;
         var17 += 164709;
      }
   }

   private void I(Graphics var1) {
      GameCanvas var2 = super.canvas;
      Neighborhood var3 = this.a17;
      int var4 = this.aB;
      byte var5;
      short var6;
      byte var7;
      short var8;
      if (var3.i(0, 0) == 0) {
         var5 = 74;
         var6 = 489;
         if (var3.i(var4, 0) == 0) {
            var7 = 71;
            var8 = 489;
         } else {
            var7 = 69;
            var8 = 382;
         }
      } else {
         if (var3.i(var4, 0) == 0) {
            var5 = 72;
            var7 = 70;
            var8 = 489;
         } else {
            var5 = 73;
            var7 = 69;
            var8 = 382;
         }

         var6 = 382;
      }

      int var9 = var3.i(0, 2) + 1;
      int var10 = var3.i(var4, 2) + 1;
      com.ea.sims3.SpriteRenderer.a12(0);
      com.ea.sims3.SpriteRenderer.a(var1, var5, 0, var2.a(), var2.b(), var6, var9, null);
      com.ea.sims3.SpriteRenderer.a12(this.a7(var4).f2);
      com.ea.sims3.SpriteRenderer.a(var1, var7, 0, var2.a(), var2.b(), var8, var10, null);
      com.ea.sims3.SpriteRenderer.a12(0);
   }

   private void J(Graphics var1) {
      Lot var3 = this.a18;
      Sim var4 = this.a14;
      Sim var5 = this.a7(this.aB);
      int[] var6 = super.a2;
      int var7 = var4.c;
      int var8 = var4.d;
      int var9 = var5.c;
      int var10 = var5.d;
      int var11 = var3.a2(var7, var8) + var3.a2(var9, var10) >> 1;
      int var12 = var3.b(var7, var8) + var3.b(var9, var10) >> 1;
      com.ea.sims3.SpriteRenderer.a2(var6, 67, 0, 2);
      var11 += var6[0] << 16;
      var12 -= var6[1] << 17;
      this.a18.a(var1, var4, var5, var11, var12);
   }

   public static boolean f3() {
      return a5 == 16;
   }

   public final void a(int var1, Entity var2) {
      this.x();
      this.a17.f2();
      this.f6 = var2;
      this.aE = var1;
      this.an();
      byte var3 = 0;
      this.aI = 0;
      switch (var1) {
         case 0:
            var3 = 20;
            this.ao();
            break;
         case 1:
            var3 = 20;
            this.ap();
            break;
         case 2:
            var3 = 20;
            this.ar();
            break;
         case 3:
            var3 = 20;
            this.au();
      }

      this.as = var3;
      this.onTick(16);
      this.P2(0);
   }

   private void P2(int var1) {
      Neighborhood var3;
      boolean var4;
      Lot var2 = this.a18;
      var3 = this.a17;
      this.aD = var1;
      var4 = false;
      label85:
      switch (var1) {
         case 0:
            if (this.aE == 1) {
               int var11 = this.a14.c;
               int var12 = this.a14.d;
               int var7 = var2.c(var11, var12);
               int var8 = var2.d(var11, var12);
               if (this.f6.a4 == 5) {
                  var7 += 50;
               } else {
                  var7 -= 10;
               }

               this.c6 = null;
               this.K = var7 << 16;
               this.I = this.K;
               this.L = var8 << 16;
               this.J = this.L;
            } else {
               this.b((Entity)this.a14);
            }
            break;
         case 1:
            switch (this.aE) {
               case 0:
                  this.a2((byte)2);
                  break label85;
               case 1:
                  this.a2((byte)3);
                  break label85;
               case 2:
                  this.a2((byte)4);
                  break label85;
               case 3:
                  this.a2((byte)5);
               default:
                  break label85;
            }
         case 2:
            this.b(this.f6);
            this.onTick(5);
            this.h2(this.aF);
            switch (this.aE) {
               case 0:
                  var3.e3(28);
                  int var10 = var3.g6;
                  if (var3.g6 == 0) {
                     var3.e3(29);
                  } else if (var10 == 9) {
                     var3.e3(31);
                  } else if (var10 == 6) {
                     var3.e3(30);
                  }
                  break;
               case 1:
                  this.f6.c2();
                  var3.e3(22);
                  if (this.aW == 32) {
                     var3.e3(23);
                  } else if (this.aW == 33) {
                     var3.e3(24);
                  } else if (this.aW == 30) {
                     var3.e3(25);
                  }

                  var3.b2(this.aW, 1);
                  this.b2(this.aW, 1, 335, 716);
                  break;
               case 2:
                  var3.e3(26);
                  this.f6.g2();
                  break;
               case 3:
                  this.f6.i2();
            }

            var4 = true;
            break;
         case 3:
            if (this.aE == 1) {
               this.f6.c2();
            }

            this.b(this.f6);
            if (this.aE == 2 && super.canvas.d() < 5) {
               this.d3(805);
            } else {
               this.a14.F();
               this.a14.a(8, null, 0, 0);
               this.c2(755, 754);
               var4 = true;
            }
            break;
         case 4:
            if (this.aE == 1) {
               this.f6.c2();
            }

            this.as = 0;
            this.b(this.f6);
            this.a14.F();
            this.c2(756, 754);
            var4 = true;
            break;
         case 5:
            this.m2 = 0;
            this.b11[0].play(634, 4);
         case 6:
         default:
            break;
         case 7:
            Sim var9 = this.a14;
            this.a14.c4(128);
            var9.r2(22);
            break;
         case 8:
            Sim var5 = this.a14;
            this.a14.c4(128);
            int var6 = this.k3() ? 26 : 23;
            var5.r2(var6);
            break;
         case 9:
            this.m2 = 0;
            this.b11[0].play(634, 4);
      }

      if (var4) {
         switch (this.aE) {
            case 0:
               var3.e3(1);
               return;
            case 1:
               var3.e3(0);
         }
      }
   }

   public final void h2(int var1) {
      Neighborhood var2 = this.a17;
      int var3 = this.a17.k(var1);
      var2.c4(var1);
      int var4 = var2.k(var1);
      int var5 = 757;
      if (var3 != var4) {
         var5 = var4 == 5 ? 759 : 758;
         switch (var1) {
            case 0:
               var2.e3(7);
               if (var4 == 5) {
                  var2.e3(73);
               }
               break;
            case 1:
               var2.e3(9);
               if (var4 == 5) {
                  var2.e3(74);
               }
               break;
            case 2:
               var2.e3(8);
               if (var4 == 5) {
                  var2.e3(71);
               }
               break;
            case 3:
               var2.e3(10);
               if (var4 == 5) {
                  var2.e3(72);
               }
            case 4:
         }
      }

      if (var3 != var4 && var4 == 5) {
         super.canvas.a4(-11, 793, var2.f(var1));
         this.c2(4, -11, var1);
      } else {
         if (var3 != 5) {
            int var6 = var2.f(var1);
            this.c2(var5, var6);
         }
      }
   }

   private void K(Graphics var1) {
      this.a18.a(var1);
      if (!this.r2 && this.aE != 1) {
         super.canvas.a(var1);
      }

      switch (this.aE) {
         case 0:
            this.L(var1);
            this.N(var1);
            break;
         case 1:
            this.M(var1);
            this.O(var1);
            break;
         case 2:
            this.L(var1);
            this.P(var1);
            break;
         case 3:
            this.L(var1);
            this.Q(var1);
      }

      if (this.g2) {
         this.c(var1);
      }

      if (this.r2) {
         this.n(var1);
      }

      this.d(var1);
   }

   private void L(Graphics var1) {
      GameCanvas var2 = super.canvas;
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 568, 0, 0, 34);
      int var3 = com.ea.sims3.SpriteRenderer.d(586, 0);
      int var4 = var2.getHeight() - 15 - 34 - var3;
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 569, 0, 0, var4);
      this.aG = 34;
      this.aH = var4;
      this.h(var1);
      int var5 = var1.getClipX();
      int var6 = var1.getClipY();
      int var7 = var1.getClipWidth();
      int var8 = var1.getClipHeight();
      int var9 = var2.getWidth() >> 1;
      int var10 = com.ea.sims3.SpriteRenderer.d(634, 0);
      int var11 = 17 - var10;
      int var12 = var2.getWidth() - 40;
      int var13 = var9 - (var12 >> 1);
      var1.setClip(var13, var6, var12, var8);
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 633, 0, var13, var11);
      int var14 = this.aI * var12 / this.aJ;
      var1.setClip(var13, var6, var14, var8);
      if (this.aD == 5) {
         com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.b11[0], var13, var11);
      } else {
         com.ea.sims3.SpriteRenderer.drawFrame(var1, 634, 0, var13, var11);
      }

      var1.setClip(var5, var6, var7, var8);
   }

   private void M(Graphics var1) {
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 568, 0, 0, 14);
      int var2 = com.ea.sims3.SpriteRenderer.d(586, 0);
      int var3 = super.canvas.getHeight() - 15 - 64 - var2;
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 569, 0, 0, var3);
      this.aG = 14;
      this.aH = var3;
      this.h(var1);
   }

   private void P(int var1, int var2) {
      if (this.matchesInput(var1, var2, 0, 5)) {
         this.O();
      } else if (this.matchesInput(var1, var2, 0, 64)) {
         this.P2(4);
      } else {
         if (this.aD == 1) {
            switch (this.aE) {
               case 0:
                  this.R(var1, var2);
                  return;
               case 1:
                  this.S(var1, var2);
                  return;
               case 2:
                  this.T(var1, var2);
                  return;
               case 3:
                  this.U(var1, var2);
            }
         }
      }
   }

   private void Q2(int var1) {
      if (!this.r2 && !this.g2) {
         this.a14.f3(var1);
         this.f6.f3(var1);
         this.u(var1, 7);
         if (this.aD == 0) {
            this.y2(var1);
            if (this.n2) {
               this.b((Entity) null);
               int var2 = this.aE == 1 ? 7 : 1;
               this.P2(var2);
               return;
            }
         } else {
            switch (this.aE) {
               case 0:
                  this.R2(var1);
                  return;
               case 1:
                  this.S2(var1);
                  return;
               case 2:
                  this.U2(var1);
                  return;
               case 3:
                  this.V2(var1);
            }
         }
      }
   }

   private void an() {
      for (int var1 = 0; var1 < 24; var1++) {
         this.b11[var1].setRunning(false);
         com.ea.sims3.GameCanvas.a(this.a19[var1], -1);
      }
   }

   private int b(int var1, int var2, int var3, int var4) {
      int var5 = -1;

      for (int var6 = 0; var6 < 24; var6++) {
         if (!this.b11[var6].isRunning()) {
            var5 = var6;
            break;
         }
      }

      if (var5 != -1) {
         this.b11[var5].play(var1, var4);
         this.a19[var5][0] = var2;
         this.a19[var5][1] = var3;
         this.a19[var5][2] = -1;
      }

      return var5;
   }

   private void c(Graphics var1, int var2) {
      this.b2(var1, var2, 0, 0);
   }

   private void b2(Graphics var1, int var2, int var3, int var4) {
      for (int var5 = var2; var5 < 24; var5++) {
         Animator var6;
         if ((var6 = this.b11[var5]).isRunning()) {
            int[] var7 = this.a19[var5];
            var6.draw(var1, var7[0] + var3, var7[1] + var4);
         }
      }
   }

   private void c2(Graphics var1, int var2, int var3, int var4) {
      for (int var5 = var2; var5 < 24; var5++) {
         Animator var6;
         if ((var6 = this.b11[var5]).isRunning()) {
            int[] var7 = this.a19[var5];
            var6.draw(var1, (var7[0] >> 16) + var3, (var7[1] >> 16) + var4);
         }
      }
   }

   private void Q(int var1, int var2) {
      for (int var3 = var2; var3 < 24; var3++) {
         this.b11[var3].advance(var1);
      }
   }

   private static void f(Graphics var0, int var1, int var2, int var3, int var4, int var5) {
      int var6 = var0.getClipX();
      int var7 = var0.getClipY();
      int var8 = var0.getClipWidth();
      int var9 = var0.getClipHeight();
      var0.setClip(var1, var2, var3, var4);
      int var10 = com.ea.sims3.SpriteRenderer.c(var5, 0);
      int var11 = com.ea.sims3.SpriteRenderer.d(var5, 0);

      for (int var12 = 0; var12 < var4; var12 += var11) {
         for (int var13 = 0; var13 < var3; var13 += var10) {
            com.ea.sims3.SpriteRenderer.drawFrame(var0, var5, 0, var1 + var13, var2 + var12);
         }
      }

      var0.setClip(var6, var7, var8, var9);
   }

   private void ao() {
      this.aF = 0;
      int var1 = this.a17.k(0);
      short var2 = 655;
      byte var3 = 2;
      char var4;
      switch (var1) {
         case 0:
         default:
            var4 = 20000;
            break;
         case 1:
            var4 = 30000;
            var2 = 983;
            break;
         case 2:
            var3 = 3;
            var4 = 30000;
            break;
         case 3:
            var3 = 3;
            var4 = 30000;
            var2 = 983;
            break;
         case 4:
         case 5:
            var3 = 4;
            var4 = '\u9c40';
      }

      this.aK = var3;
      this.aJ = var4;
      this.aN = var2;
      this.aM = 1638400;
      this.aL = 0;
      this.aI = 0;
      GameCanvas var5 = super.canvas;

      for (int var6 = 0; var6 < this.aK; var6++) {
         int var7 = var5.b(1638400, 4915200);
         this.i4[var6] = var7;
         this.j4[var6] = var7;
         this.b11[this.aK + var6].play(612, 20);
         this.b12[var6] = (byte)var5.b(0, 3);
      }
   }

   private void N(Graphics var1) {
      int var2 = var1.getClipX();
      int var3 = var1.getClipY();
      int var4 = var1.getClipWidth();
      int var5 = var1.getClipHeight();
      int var6 = this.s + (this.u >> 1);
      int var7 = com.ea.sims3.SpriteRenderer.c(610, 0);
      int var8 = com.ea.sims3.SpriteRenderer.d(611, 0);
      int var9 = var6 - (var7 * this.aK >> 1) + (var7 >> 1);
      int var10 = this.aG + this.aH + var8 >> 1;
      int var11 = com.ea.sims3.SpriteRenderer.d(612, 0);
      int[] var12 = this.c3;
      int var13 = var9;

      for (int var14 = 0; var14 < this.aK; var14++) {
         short var15 = 611;
         int var16 = this.b11[this.aK + var14].animId;
         int var17 = this.b11[this.aK + var14].frame;
         if (this.aD == 5) {
            var16 = 613;
            var17 = 0;
         }

         if (var14 == this.aL) {
            var15 = 610;
            if (var16 != 614) {
               var16 = 612;
            }
         }

         int var18 = this.j4[var14];
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var15, 0, var13, var10);
         int var19 = var11 * (var18 >> 16) / 100;
         var1.setClip(var2, var10 - var19, var4, var19);
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var16, var17, var13, var10);
         var1.setClip(var2, var3, var4, var5);
         com.ea.sims3.SpriteRenderer.drawFrame(var1, 615, 0, var13, var10);
         if (var18 > 0 && var18 < 6553600) {
            com.ea.sims3.SpriteRenderer.a2(var12, 611, 0, 0);
            com.ea.sims3.SpriteRenderer.drawFrame(var1, 608, this.b12[var14], var13 + var12[0], var10 + var12[1]);
         }

         var13 += var7;
      }

      var1.setClip(var2, var3, var4, var5);
      this.c(var1, this.aK << 1);
   }

   private void R2(int var1) {
      this.Q(var1, this.aK);
      if (this.aD == 5) {
         this.b11[0].advance(var1);
         if (this.m2 > 3200) {
            this.P2(2);
         }
      } else if (this.aD == 6) {
         if (!this.b11[this.aO].isRunning()) {
            this.P2(3);
         }
      } else {
         this.aI += var1;
         if (this.aI > this.aJ) {
            this.P2(5);
         } else {
            int var2 = this.s + (this.u >> 1);
            int var3 = com.ea.sims3.SpriteRenderer.c(610, 0);
            int var4 = com.ea.sims3.SpriteRenderer.d(611, 0);
            int var5 = var2 - (var3 * this.aK >> 1) + (var3 >> 1);
            int var6 = this.aG + this.aH + var4 >> 1;
            int[] var7 = this.c3;
            int var8 = var5;

            for (int var9 = 0; var9 < this.aK; var9++) {
               int var10;
               if ((var10 = this.i4[var9]) > 0) {
                  var10 += var1 * this.aN;
                  this.i4[var9] = var10;
               }

               if (var10 <= 6553600) {
                  byte var11 = 31;
                  short var12 = 614;
                  if (var10 < 1638400) {
                     var11 = 29;
                  } else if (var10 < 4915200) {
                     var11 = 30;
                     var12 = 613;
                  }

                  Animator var13;
                  (var13 = this.b11[var9]).play(var11, 4);
                  if (var13.advance(var1) && var13.matchesFrame(var7, 0)) {
                     this.b(42, var8 + var7[0], var6 + var7[1], 16);
                  }

                  Animator var15;
                  (var15 = this.b11[this.aK + var9]).play(var12, 4);
               }

               this.j4[var9] = com.ea.sims3.BaseScreen.a(this.j4[var9], var10, var1, 8);
               if (var10 > 6553600 || this.j4[var9] < 65536) {
                  com.ea.sims3.SpriteRenderer.a2(var7, 611, 0, 0);
                  this.aO = this.b(609, var8 + var7[0], var6 + var7[1], 16);
                  super.canvas.n2();
                  this.P2(6);
               }

               var8 += var3;
            }
         }
      }
   }

   private void R(int var1, int var2) {
      int var3 = this.aK;
      int var4 = this.aL;
      if (this.matchesInput(var1, var2, 32784, 0) && var4 > 0) {
         var4 = (var4 + var3 - 1) % var3;
      }

      if (this.matchesInput(var1, var2, 65600, 0) && var4 < var3 - 1) {
         var4 = (var4 + 1) % var3;
      }

      if (this.matchesInput(var1, var2, 4128, 0)) {
         int var5;
         var5 = Math.max((var5 = this.i4[var4]) - this.aM, 0);
         this.i4[var4] = var5;
      }

      this.aL = var4;
      if (super.canvas.f4 && this.matchesInput(var1, var2, 1, 0)) {
         this.aI = this.aJ - 1;
      }
   }

   private void ap() {
      GameCanvas var1 = super.canvas;
      this.aF = 2;
      this.j2 = false;
      this.k2 = false;
      this.f6.b4();
      int var2;
      int var3;
      switch (var3 = this.a17.k(2)) {
         case 0:
         default:
            var2 = 30;
            break;
         case 1:
            var2 = var1.b(30, 31);
            break;
         case 2:
            var2 = var1.b(30, 32);
            break;
         case 3:
            var2 = var1.b(30, 33);
            break;
         case 4:
         case 5:
            var2 = var1.b(30, 34);
            if (var3 == 5 && var2 < 32) {
               var2 = var1.b(30, 34);
            }
      }

      this.aW = var2;
      byte var4;
      char var5;
      switch (var2) {
         case 30:
         default:
            var4 = 32;
            var5 = '\u8000';
            break;
         case 31:
            var4 = 39;
            var5 = 26214;
            break;
         case 32:
            var4 = 45;
            var5 = 19660;
            break;
         case 33:
            var4 = 52;
            var5 = 13107;
            break;
         case 34:
            var4 = 65;
            var5 = 13107;
      }

      this.aU = var4;
      this.aV = var5;
      this.aT = 0;
      this.aP = 65536;
      this.aQ = super.canvas.b(0, 131071);
      this.h5 = this.b11[0];
      this.h5.play(617, 20);
      this.i5 = this.b11[1];
      this.i5.play(620, 20);
      this.aq();
   }

   private void O(Graphics var1) {
      if (this.aD != 0 && this.aD != 7) {
         int var2 = this.s + (this.u >> 1);
         int var3 = this.aH + 18;
         com.ea.sims3.SpriteRenderer.drawFrame(var1, 622, 0, var2, var3);
         int[] var4 = this.c3;
         com.ea.sims3.SpriteRenderer.b(this.c3, 622, 0, 0);
         int var5 = var3 + var4[1] + var4[3];
         int var6 = var2 + var4[0];
         int var7 = var4[2];
         int var8 = var6 + com.ea.sims3.FixedMath.mul(var7, com.ea.sims3.FixedMath.div(this.aP, 131072));
         if (this.l3()) {
            int var9 = -1;
            if (this.aT < 58982) {
               int var10 = com.ea.sims3.SpriteRenderer.frameCount(619);
               int var11 = 65536 / var10;
               var9 = this.aT / var11;
            }

            int var15 = var1.getClipX();
            int var16 = var1.getClipY();
            int var12 = var1.getClipWidth();
            int var13 = var1.getClipHeight();
            var1.setClip(var6, var16, var7, var13);
            if (var9 != -1) {
               com.ea.sims3.SpriteRenderer.drawFrame(var1, 619, var9, var8, var5);
            } else {
               com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.i5, var8, var5);
            }

            var1.setClip(var15, var16, var12, var13);
         }

         if (this.aT > -16384 && this.aT < 65536) {
            com.ea.sims3.SpriteRenderer.drawFrame(var1, 621, 0, var8, var5);
         }

         var8 = var6 + com.ea.sims3.FixedMath.mul(var7, com.ea.sims3.FixedMath.div(this.aQ, 131072));
         com.ea.sims3.SpriteRenderer.drawAnimator(var1, this.h5, var8, var5);
      }
   }

   private void S2(int var1) {
      Sim var2 = this.a14;
      if (this.aD == 7) {
         if (!var2.h3()) {
            this.P2(1);
         }
      } else if (this.aD == 8) {
         if (!var2.h3()) {
            int var6 = this.k3() ? 2 : 3;
            this.P2(var6);
         }
      } else {
         int var3 = 0;
         if (this.j2) {
            var3 = 0 - var1;
         }

         if (this.k2) {
            var3 += var1;
         }

         if (var3 != 0) {
            int var4 = this.aP + var3 * 131;
            this.aP = Math.max(Math.min(var4, 131072), 0);
         }

         this.h5.advance(var1);
         this.i5.advance(var1);
         if (this.aT >= 65536) {
            if (!this.h5.isRunning()) {
               this.P2(8);
               return;
            }
         } else {
            int var7 = this.l3() ? 196 : -262;
            this.aT += var7;
            if (this.aT <= -16384) {
               super.canvas.n2();
               this.P2(8);
               return;
            }

            if (this.aT >= 65536) {
               this.aT = 65535;
            }

            short var5 = 617;
            if (this.aT > 32768) {
               var5 = 618;
            }

            this.h5.play(var5, 4);
         }

         if (this.aT > -16384 && this.aT < 65536) {
            boolean var8;
            int var10 = (var8 = this.aS < this.aR) ? -var1 : var1;
            this.aQ = this.aQ + this.aU * var10;
            if (var8 && this.aQ < this.aS || !var8 && this.aQ > this.aS) {
               this.aQ = this.aS;
               this.aq();
            }
         }

         byte var9 = 24;
         if (this.aT > 32768) {
            var9 = 25;
         }

         var2.b7(128);
         var2.r2(var9);
      }
   }

   private boolean k3() {
      return this.aT == 65536;
   }

   private boolean l3() {
      return Math.abs(this.aP - this.aQ) < 26214;
   }

   private void aq() {
      int var1 = this.aQ;
      int var2 = Math.max(this.aQ, 131072 - var1);
      int var3 = super.canvas.b(this.aV, var2);
      boolean var4 = var1 > 131072 - var3 ? true : var1 >= var3 && super.canvas.d() < 50;
      this.aR = var1;
      this.aS = var1 + (var4 ? -var3 : var3);
   }

   private void S(int var1, int var2) {
      if (this.aT > -16384 && this.aT < 65536) {
         this.j2 = (var1 & 32784) != 0;
         this.k2 = (var1 & 65600) != 0;
      } else {
         this.j2 = false;
         this.k2 = false;
      }

      if (this.matchesInput(var1, var2, 4128, 0) && this.aT >= 58982) {
         if (this.l3()) {
            this.aP = this.aQ;
            this.h5.play(616, 16);
            this.aT = 65536;
         } else {
            super.canvas.n2();
            this.P2(8);
         }
      }

      if (super.canvas.f4 && this.matchesInput(var1, var2, 1, 0)) {
         this.aP = this.aQ;
         this.h5.play(616, 16);
         this.aT = 65536;
      }
   }

   private void T2(int var1) {
      DataInputStream var3 = new DataInputStream(super.canvas.a6.openStream(var1));

      try {
         byte[][] var4 = new byte[4][];

         for (int var5 = 0; var5 < 4; var5++) {
            byte var6;
            byte[] var7 = new byte[var6 = var3.readByte()];

            for (int var8 = 0; var8 < var6; var8++) {
               var7[var8] = var3.readByte();
            }

            var4[var5] = var7;
         }

         this.a20 = var4;
         if (this.e7 == null) {
            this.e7 = new byte[4];
         }

         byte[] var10 = this.e7;

         for (int var11 = 0; var11 < 4; var11++) {
            var10[var11] = var3.readByte();
         }
      } catch (IOException var9) {
         this.a20 = (byte[][])null;
         this.e7 = null;
      }
   }

   private void ar() {
      GameCanvas var1 = super.canvas;
      super.canvas.b(0, 4);
      this.T2(49);
      this.aF = 3;
      int var2 = (this.a17.e(3) >> 2) + 65536;
      this.j5 = this.b11[0];
      int var3 = this.a17.k(3) < 3 ? 3 : 4;
      this.aY = var3;
      this.aZ = 10 * var2 >> 16;
      this.aJ = this.aZ;
      this.aX = 65536;
      this.a21 = new short[var3][5];
      int var5;
      int var6 = (var5 = com.ea.sims3.SpriteRenderer.c(630, 0) / c10[0]) << 2;

      for (int var7 = 0; var7 < var3; var7++) {
         byte[] var8 = this.a20[var7];
         short[] var9 = this.a21[var7];
         int var10 = var8.length;
         int var11 = 0;
         int var12 = var7 * var6;
         var9[0] = 0;
         var9[1] = (short)var12;

         for (int var13 = 0; var13 < var10; var13++) {
            byte var14 = var8[var13];
            var11 += c10[var14] * var5;
            var12 += d8[var14] * var5;
            if (var14 == 1 || var14 == 2) {
               var9[4] = (short)var11;
            }
         }

         var9[2] = (short)var11;
         var9[3] = (short)var12;
      }

      this.ba = 1;
      this.j5.play(624, 20);
   }

   private void P(Graphics var1) {
      int var3;
      int var4;
      int var5 = (var4 = (var3 = com.ea.sims3.SpriteRenderer.c(630, 0) / c10[0]) << 2) * this.aY;
      int var6;
      int var7 = (var6 = this.aG + this.aH - var5 >> 1) + var4;
      int var8;
      int var9 = (var8 = this.s + (this.u >> 1) - (this.u >> 2)) + var3 * 11;
      f(var1, this.s + 10, this.aG + 0, this.u - 20, this.aH - this.aG - 0, 623);

      for (int var10 = 0; var10 < this.aY; var10++) {
         int var11 = var8;
         int var12 = var7 + var10 * var4;
         com.ea.sims3.SpriteRenderer.drawFrame(var1, 627, 0, var8, var12);
         byte[] var13 = this.a20[var10];

         for (int var14 = 0; var14 < var13.length; var14++) {
            byte var15 = var13[var14];
            com.ea.sims3.SpriteRenderer.drawFrame(var1, 630, var15, var11, var12);
            var11 += c10[var15] * var3;
            var12 += d8[var15] * var3;
         }
      }

      int var16 = var6 + com.ea.sims3.FixedMath.mul(var5, com.ea.sims3.FixedMath.div(this.aX, 131072));
      this.j5.draw(var1, var9, var16);
      this.c2(var1, 1, var8, var7);
   }

   private void T(int var1, int var2) {
      this.t2(var1);
      if (this.j5.isLooping() && this.matchesInput(var1, var2, 4128, 0)) {
         this.as();
      }

      if (super.canvas.f4 && this.matchesInput(var1, var2, 1, 0)) {
         this.P2(2);
      }
   }

   private void U2(int var1) {
      switch (this.aD) {
         case 1:
            this.j5.advance(var1);
            int var2 = this.a17.e(3) / 5;
            if (!this.j5.isRunning()) {
               this.j5.play(624, 20);
               this.at();
            }

            this.Q(var1, 1);
            if (this.j5.isLooping()) {
               int var3 = var1 * ((this.l2 ? 65 : 0) + (this.m3 ? -65 : 0));
               int var4;
               if ((var4 = this.aX + var3) < 0) {
                  var4 = 0;
               } else if (var4 > 131072) {
                  var4 = 131072;
               }

               this.aX = var4;
            }

            boolean var28 = false;

            for (int var29 = 1; var29 < 24; var29++) {
               Animator var5;
               if ((var5 = this.b11[var29]).isRunning()) {
                  var28 = true;
                  int[] var6;
                  int var7;
                  if ((var7 = (var6 = this.a19[var29])[2]) != -1) {
                     boolean var8 = (var7 & 65536) != 0;
                     var7 &= 65535;
                     short[] var9 = this.a21[var7];
                     int var10;
                     int var11;
                     int var12;
                     int var13;
                     if (var8) {
                        var10 = var9[2] << 16;
                        var11 = var9[3] << 16;
                        var12 = var9[0] << 16;
                        var13 = var9[1] << 16;
                     } else {
                        var10 = var9[0] << 16;
                        var11 = var9[1] << 16;
                        var12 = var9[2] << 16;
                        var13 = var9[3] << 16;
                     }

                     int var14 = var9[4] << 16;
                     int var15 = var6[0];
                     int var16 = var6[1];
                     int var17;
                     int var18;
                     if (var15 == var14) {
                        var17 = 0;
                        var18 = com.ea.sims3.FixedMath.sign(var13 - var11);
                     } else {
                        var17 = com.ea.sims3.FixedMath.sign(var12 - var10);
                        var18 = 0;
                     }

                     int var19 = (var2 >> 1) + 65536;
                     int var20 = com.ea.sims3.FixedMath.mul(var1 * 1310, var19);
                     int var21 = var15 + var17 * var20;
                     int var22 = var16 + var18 * var20;
                     if ((var17 <= 0 || var15 >= var14 || var21 <= var14) && (var17 >= 0 || var15 <= var14 || var21 >= var14)) {
                        if (var17 > 0 && var15 < var12 && var21 >= var12 || var17 < 0 && var15 > var12 && var21 <= var12) {
                           var5.setRunning(false);
                           super.canvas.n2();
                           this.P2(3);
                           return;
                        }

                        if (var18 > 0 && var16 < var13 && var22 >= var13 || var18 < 0 && var16 > var13 && var22 <= var13) {
                           var22 = var13;
                           var21 = var14 + com.ea.sims3.FixedMath.sign(var12 - var10);
                        }
                     } else {
                        var21 = var14;
                     }

                     for (int var23 = 1; var23 < 24; var23++) {
                        Animator var24 = this.b11[var23];
                        int[] var25;
                        if (var23 != var29
                           && var24.isRunning()
                           && ((var25 = this.a19[var23])[2] & 65535) == var7
                           && Math.abs(var25[0] - var21) + Math.abs(var25[1] - var22) < 196608) {
                           var5.play(629, 16);
                           var6[2] = -1;
                           var24.setRunning(false);
                           var25[2] = -1;
                        }
                     }

                     var6[0] = var21;
                     var6[1] = var22;
                  }
               }
            }

            if (this.aZ > 0) {
               this.ba -= var1;
               if (this.ba <= 0) {
                  int var30 = super.canvas.b(0, this.aY - 1);
                  this.a2(var30, false);
                  this.aZ--;
                  this.aI++;
                  int var31 = 5000 * (65536 - (var2 >> 1)) >> 16;
                  this.ba = 1000 + super.canvas.b(0, var31);
                  return;
               }
            } else if (!var28) {
               this.P2(2);
            }
      }
   }

   private void as() {
      int var1 = 131072 / this.aY;
      int var3 = Math.min(this.aX / var1, this.aY - 1) * var1 + (var1 >> 1);
      this.aX = var3;
      this.j5.play(625, 16);
   }

   private void at() {
      int var1 = 131072 / this.aY;
      int var3 = com.ea.sims3.GameCanvas.a(Math.min(this.aX / var1, this.aY - 1), this.e7);
      this.a2(var3, true);
   }

   private void a2(int var1, boolean var2) {
      short[] var3 = this.a21[var1];
      short var4;
      short var5;
      short var6;
      if (var2) {
         var4 = var3[2];
         var5 = var3[3];
         var6 = 628;
      } else {
         var4 = var3[0];
         var5 = var3[1];
         var6 = 626;
      }

      int var7;
      if ((var7 = this.b(var6, var4 << 16, var5 << 16, 20)) != -1) {
         this.a19[var7][2] = var1 | (var2 ? 65536 : 0);
      }
   }

   private void au() {
      GameCanvas var1 = super.canvas;
      this.aF = 1;
      int var3;
      int var4;
      char var5;
      switch (this.a17.k(1)) {
         case 0:
         default:
            var3 = 32768;
            var4 = 19660;
            var5 = '\uafc8';
            break;
         case 1:
            var3 = 32768;
            var4 = 29491;
            var5 = '\u9c40';
            break;
         case 2:
            var3 = 39321;
            var4 = 39321;
            var5 = '\u9c40';
            break;
         case 3:
            var3 = 49152;
            var4 = 52428;
            var5 = '\u88b8';
            break;
         case 4:
         case 5:
            var3 = 65536;
            var4 = 65536;
            var5 = 30000;
      }

      this.bf = var3;
      this.be = var4;
      this.aJ = var5;
      this.k3 = this.b11[1];
      this.k3.play(605, 20);
      int var6 = com.ea.sims3.SpriteRenderer.c(603, 0);
      int var7 = 4 * var6;
      int var8 = 4 * var6;
      int var9 = com.ea.sims3.SpriteRenderer.c(606, 0);
      int var10 = com.ea.sims3.SpriteRenderer.d(606, 0);
      int var11 = var9;
      int var12 = var10;
      int var13 = var7 - var9;
      int var14 = var8 - var10;

      for (int var16 = 0; var16 < 5; var16++) {
         int[] var17;
         (var17 = this.a19[var16 + 2])[0] = var1.b(var11, var13);
         var17[1] = var1.b(var12, var14);
         var17[2] = 0;
      }

      this.E();
      this.bb = 262144;
      this.bc = 262144;
      this.bd = -1;
      this.bg = com.ea.sims3.FixedMath.mul(1966, this.bf);
      this.bh = com.ea.sims3.FixedMath.mul(1638, this.bf);
      this.bi = 0;
      this.bj = 0;
      this.aI = 0;
   }

   private void Q(Graphics var1) {
      int var2 = com.ea.sims3.SpriteRenderer.c(603, 0);
      int var3 = 4 * var2;
      int var4 = 4 * var2;
      f(var1, this.s + 15, this.aG + 5, this.u - 30, this.aH - this.aG - 10, 603);
      int var5;
      int var6 = (var5 = this.s + (this.u >> 1)) - (var3 >> 1);
      int var7 = this.aG + this.aH - var4 >> 1;

      for (int var8 = 2; var8 < 24; var8++) {
         int[] var9;
         int var10;
         if ((var10 = (var9 = this.a19[var8])[2]) != -1) {
            com.ea.sims3.SpriteRenderer.drawFrame(var1, 606, var10, var6 + var9[0], var7 + var9[1]);
         }
      }

      int var12 = this.bb + this.bi >> 3;
      int var13 = this.bc + this.bj >> 3;
      int var14 = com.ea.sims3.FixedMath.mul(var12, var3);
      int var11 = com.ea.sims3.FixedMath.mul(var13, var4);
      this.k3.draw(var1, var6 + var14, var7 + var11);
      this.b2(var1, 2, var6, var7);
   }

   private void U(int var1, int var2) {
      this.t2(var1);
      if (this.k3.animId != 604 && this.matchesInput(var1, var2, 4128, 0)) {
         this.k3.play(604, 16);
      }

      if (super.canvas.f4 && this.matchesInput(var1, var2, 1, 0)) {
         this.P2(2);
      }
   }

   private void V2(int var1) {
      switch (this.aD) {
         case 1:
            this.Q(var1, 2);
            this.k3.advance(var1);
            if (!this.k3.isRunning()) {
               this.k3.play(605, 20);
               this.W2(this.bd);
            }

            if (this.k3.animId != 604) {
               int var2 = var1 * ((this.k2 ? 229 : 0) + (this.j2 ? -229 : 0));
               int var3 = var1 * ((this.l2 ? 229 : 0) + (this.m3 ? -229 : 0));
               this.bb = Math.max(0, Math.min(524288, this.bb + var2));
               this.bc = Math.max(0, Math.min(524288, this.bc + var3));
               this.bg += var1;
               this.bh += var1;
               int var5 = com.ea.sims3.FixedMath.mul(this.bf, 1966);
               int var6 = com.ea.sims3.FixedMath.mul(this.bf, 1638);
               if (this.bg > var5) {
                  this.bg = 0;
               }

               if (this.bg > var5 >> 1) {
                  int var7 = com.ea.sims3.FixedMath.smoothStep(var5 >> 1, var5, this.bg);
                  this.bi = this.be - com.ea.sims3.FixedMath.mul(var7, this.be);
               } else {
                  int var22 = com.ea.sims3.FixedMath.smoothStep(0, var5 >> 1, this.bg);
                  this.bi = com.ea.sims3.FixedMath.mul(var22, this.be);
               }

               this.bi = this.bi - (this.be >> 1);
               if (this.bh > var6) {
                  this.bh = 0;
               }

               if (this.bh > var6 >> 1) {
                  int var23 = com.ea.sims3.FixedMath.smoothStep(var6 >> 1, var6, this.bh);
                  this.bj = this.be - com.ea.sims3.FixedMath.mul(var23, this.be);
               } else {
                  int var24 = com.ea.sims3.FixedMath.smoothStep(0, var6 >> 1, this.bh);
                  this.bj = com.ea.sims3.FixedMath.mul(var24, this.be);
               }

               this.bj = this.bj - (this.be >> 1);
            }

            int var19 = com.ea.sims3.SpriteRenderer.c(603, 0);
            int var4 = 4 * var19;
            int var20 = 4 * var19;
            int var21 = this.bb + this.bi >> 3;
            int var25 = this.bc + this.bj >> 3;
            int var8 = com.ea.sims3.FixedMath.mul(var21, var4);
            int var9 = com.ea.sims3.FixedMath.mul(var25, var20);
            boolean var10 = true;
            int var11 = -1;
            int var12 = -1;

            for (int var13 = 2; var13 < 24; var13++) {
               int[] var14;
               if ((var14 = this.a19[var13])[2] != -1) {
                  var10 = false;
                  int var16 = var14[0] - var8;
                  int var17 = var14[1] - var9;
                  int var18 = var16 * var16 + var17 * var17;
                  if (var11 == -1 || var18 < var12) {
                     var11 = var13;
                     var12 = var18;
                  }
               }
            }

            if (var12 > 225) {
               var11 = -1;
            }

            this.bd = var11;
            if (var10) {
               this.P2(9);
               return;
            }

            this.aI += var1;
            if (this.aI > this.aJ) {
               super.canvas.n2();
               this.P2(9);
               return;
            }
            break;
         case 9:
            this.Q(var1, 2);
            this.b11[0].advance(var1);
            if (this.m2 > 3200) {
               if (this.aI <= this.aJ) {
                  this.P2(2);
                  return;
               }

               this.P2(3);
            }
      }
   }

   private void W2(int var1) {
      if (var1 != -1) {
         int[] var2;
         int var3 = (var2 = this.a19[var1])[2];
         if (++var3 >= com.ea.sims3.SpriteRenderer.frameCount(606)) {
            var3 = -1;
            this.b11[var1].play(607, 16);
         }

         var2[2] = var3;
      }
   }

   public final void g(Entity var1) {
      int var2 = var1.a4;
      if (var1.a4 == 114 && !super.canvas.j2) {
         this.c2(785, 784);
      } else {
         this.bl = var2;
         this.bm = 0;
         this.onTick(17);
         this.X2(0);
      }
   }

   private void X2(int var1) {
      this.bk = var1;
      switch (var1) {
         case 0:
            this.av();
            break;
         case 1:
            this.onTick(5);
      }

      this.w();
   }

   private void R(Graphics var1) {
      switch (this.bk) {
         case 0:
            this.S(var1);
         default:
            this.d(var1);
      }
   }

   private void S(Graphics var1) {
      GameCanvas var2 = super.canvas;
      Lot var3 = this.a18;
      Neighborhood var4 = this.a17;
      int var5 = var3.j(this.bl, super.l);
      this.setValue(1, this.a18.i(this.bl));
      int var6;
      if ((var6 = var3.x(var5)) > 0) {
         SdkString var7 = var2.a2();
         var2.g2(var6);
         var2.a2(-9, var7);
         this.setValue(2, -9);
      } else {
         this.setValue(2, 711);
      }

      int var10;
      if ((var10 = var3.y(var5)) > 0) {
         SdkString var8 = var2.a2();
         var2.g2(var10);
         var2.a2(-8, var8);
         this.setValue(3, -8);
      } else {
         this.setValue(3, 711);
      }

      int var11;
      if (this.bm >= 0) {
         var11 = this.bm * var6;
      } else {
         var11 = -this.bm * var10;
      }

      SdkString var9 = var2.a2();
      var2.f3(var4.c(var5));
      var2.e3(27);
      var2.f3(var3.z(var5));
      var2.a2(-7, var9);
      this.setValue(4, -7);
      var9 = var2.a2();
      var2.f3(this.bm);
      var2.a2(-6, var9);
      this.setValue(5, -6);
      var9 = var2.a2();
      var2.g2(var11);
      var2.a2(-5, var9);
      this.setValue(6, -5);
      this.a(var1, 22);
   }

   private void av() {
      this.d(this.a18.B(this.bl), 18, 0);
   }

   private void V(int var1, int var2) {
      Neighborhood var3 = this.a17;
      Lot var4 = this.a18;
      switch (this.bk) {
         case 0:
            if (this.matchesInput(var1, var2, 0, 4)) {
               this.X2(1);
               return;
            } else if (this.a5(var1, var2)) {
               this.Y2(0);
               return;
            } else if (this.matchesInput(var1, var2, 4128, 12288) && this.bm != 0) {
               int var5 = var4.j(this.bl, super.l);
               int var6 = this.bm * (this.bm > 0 ? var4.x(var5) : var4.y(var5));
               var3.b2(var5, this.bm);
               if (this.bm > 0) {
                  var3.g2(var5);
               }

               var3.a12(-var6);
               this.Y2(0);
               return;
            } else {
               if (this.matchesInput(var1, var2, 32784, 0)) {
                  this.Y2(this.bm - 1);
               }

               if (this.matchesInput(var1, var2, 65600, 0)) {
                  this.Y2(this.bm + 1);
               }
            }
      }
   }

   private void Y2(int var1) {
      Neighborhood var2 = this.a17;
      Lot var3 = this.a18;
      int var4 = this.a18.j(this.bl, super.l);
      int var5 = var2.e5;
      int var6 = var3.x(var4);
      int var7 = var3.y(var4);
      int var8;
      if (var6 <= 0) {
         var8 = 0;
      } else {
         var8 = Math.min(Math.min(var3.z(var4) - var2.c(var4), var5 / var6), var3.z(var4));
      }

      int var9;
      if (var7 <= 0) {
         var9 = 0;
      } else {
         var9 = -var2.c(var4);
      }

      this.bm = com.ea.sims3.FixedMath.clamp(var1, var9, var8);
      this.w();
   }

   private void aw() {
      if (a5 == 5 && (!this.r2 || this.V != 5)) {
         this.x();
         this.O();
         this.A2(5);
      }
   }

   private void ax() {
      GameCanvas var1 = super.canvas;
      Neighborhood var2 = this.a17;
      short[] var3 = new short[35];
      SdkString[] var4 = new SdkString[35];
      com.ea.sims3.GameCanvas.a(var3, 123, var1.a22 - 4);
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[0] = com.ea.sims3.GameCanvas.a("Give Money");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[1] = com.ea.sims3.GameCanvas.a("Advance 1 hour");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[2] = com.ea.sims3.GameCanvas.a("Advance 1 day");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[3] = com.ea.sims3.GameCanvas.a("Clear Motives");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[4] = com.ea.sims3.GameCanvas.a("Get Friends");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[5] = com.ea.sims3.GameCanvas.a("Break everything");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[6] = com.ea.sims3.GameCanvas.a("Fix everything");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[7] = com.ea.sims3.GameCanvas.a("Dirty everything");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[8] = com.ea.sims3.GameCanvas.a("Clean everything");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[9] = com.ea.sims3.GameCanvas.a("Unlock bonus");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[10] = com.ea.sims3.GameCanvas.a("Give all");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[11] = com.ea.sims3.GameCanvas.a("< Cooking: " + var2.k(0) + " >");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[12] = com.ea.sims3.GameCanvas.a("< Cleaning: " + var2.k(1) + " >");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[13] = com.ea.sims3.GameCanvas.a("< Fishing: " + var2.k(2) + " >");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[14] = com.ea.sims3.GameCanvas.a("< Repairing: " + var2.k(3) + " >");
      com.ea.sims3.GameCanvas.c2(var3, 0);
      var4[15] = com.ea.sims3.GameCanvas.a("< Gardening: " + var2.k(4) + " >");
      int var6 = var2.i6;
      if (var2.i6 != -1) {
         String var7 = var1.a4(var2.R(var6)).toString();
         com.ea.sims3.GameCanvas.c2(var3, 3);
         var4[16] = com.ea.sims3.GameCanvas.a(var7 + ": Friends");
         com.ea.sims3.GameCanvas.c2(var3, 4);
         var4[17] = com.ea.sims3.GameCanvas.a(var7 + ": Disliked");
         com.ea.sims3.GameCanvas.c2(var3, 5);
         var4[18] = com.ea.sims3.GameCanvas.a(var7 + ": Romance");
         com.ea.sims3.GameCanvas.c2(var3, 6);
         var4[19] = com.ea.sims3.GameCanvas.a(var7 + ": Good Friends");
         com.ea.sims3.GameCanvas.c2(var3, 7);
         var4[20] = com.ea.sims3.GameCanvas.a(var7 + ": Enemies");
         com.ea.sims3.GameCanvas.c2(var3, 8);
         var4[21] = com.ea.sims3.GameCanvas.a(var7 + ": Date");
         com.ea.sims3.GameCanvas.c2(var3, 9);
         var4[22] = com.ea.sims3.GameCanvas.a(var7 + ": Best Friends");
         com.ea.sims3.GameCanvas.c2(var3, 10);
         var4[23] = com.ea.sims3.GameCanvas.a(var7 + ": Nemesis");
         com.ea.sims3.GameCanvas.c2(var3, 11);
         var4[24] = com.ea.sims3.GameCanvas.a(var7 + ": Partner");
      }

      this.k4 = var3;
      this.a22 = var4;
      this.k4[2] = 0;
   }

   private void W(int var1, int var2) {
      GameCanvas var3 = super.canvas;
      super.canvas.b3(this.k4, var1);
      Neighborhood var4 = this.a17;
      short var5 = this.k4[2];
      if (this.matchesInput(var1, var2, 4128, 16)) {
         switch (var5) {
            case 0:
               var4.a12(1000);
               return;
            case 1:
               var4.o2(0);
               return;
            case 2:
               var4.o2(1);
               return;
            case 3:
               var4.o2(2);
               this.P();
               return;
            case 4:
               var4.o2(12);
               this.P();
               return;
            case 5:
               Entity[] var11 = this.a12();

               for (int var15 = 0; var15 < var11.length; var15++) {
                  if (var11[var15].a13(67108864) && !var11[var15].b8(256)) {
                     var11[var15].f2();
                  }
               }

               this.P();
               return;
            case 6:
               Entity[] var10 = this.a12();

               for (int var14 = 0; var14 < var10.length; var14++) {
                  if (var10[var14].a13(67108864) && var10[var14].b8(256)) {
                     var10[var14].g2();
                  }
               }

               this.P();
               return;
            case 7:
               Entity[] var9 = this.a12();

               for (int var13 = 0; var13 < var9.length; var13++) {
                  if (var9[var13].a13(134217728) && !var9[var13].b8(512)) {
                     var9[var13].h2();
                  }
               }

               this.P();
               return;
            case 8:
               Entity[] var8 = this.a12();

               for (int var12 = 0; var12 < var8.length; var12++) {
                  if (var8[var12].a13(134217728) && var8[var12].b8(512)) {
                     var8[var12].i2();
                  }
               }

               this.P();
               return;
            case 9:
               var4.o2(24);
               this.P();
               return;
            case 10:
               var4.o2(25);
               this.P();
               return;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
               return;
            default:
               var4.o2(this.k4[5 + var5]);
               this.P();
         }
      } else {
         if (this.matchesInput(var1, var2, 98384, 0)) {
            boolean var6 = (var1 & 32784) != 0;
            boolean var7 = false;
            if (var5 == 11) {
               var4.o2(var6 ? 13 : 14);
               this.a22[11] = com.ea.sims3.GameCanvas.a("< Cooking: " + var4.k(0) + " >");
            } else if (var5 == 12) {
               var4.o2(var6 ? 15 : 16);
               this.a22[12] = com.ea.sims3.GameCanvas.a("< Cleaning: " + var4.k(1) + " >");
            } else if (var5 == 13) {
               var4.o2(var6 ? 17 : 18);
               this.a22[13] = com.ea.sims3.GameCanvas.a("< Fishing: " + var4.k(2) + " >");
            } else {
               if (var5 != 14) {
                  if (var5 == 15) {
                     var4.o2(var6 ? 21 : 22);
                     this.a22[15] = com.ea.sims3.GameCanvas.a("< Gardening: " + var4.k(4) + " >");
                  }

                  return;
               }

               var4.o2(var6 ? 19 : 20);
               this.a22[14] = com.ea.sims3.GameCanvas.a("< Repairing: " + var4.k(3) + " >");
            }
         } else if (this.matchesInput(var1, var2, 0, 4)) {
            this.P();
         }
      }
   }

   private void T(Graphics var1) {
      this.a(var1, this.k4, 0, this.a22);
   }
}
