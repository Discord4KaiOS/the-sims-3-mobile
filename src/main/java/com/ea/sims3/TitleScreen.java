package com.ea.sims3;

import javax.microedition.lcdui.Graphics;

public final class TitleScreen extends BaseScreen implements Runnable {
   public static int a5 = 0;
   private int b4 = 0;
   private int c3 = 0;
   private int m2 = -1;
   private int n;
   private short[] b5;
   private int o;
   private Thread a6;
   private int p;
   private short[] c4;
   private SdkString[] a7;
   private int q;
   private int r;
   private int s;
   private Animator c5;
   private int t;
   private static final short[] d2 = new short[]{723, 724, 725, 726, 727, 728, 729, 730, 731, 732, 733, 734, 735};
   private int[] c6 = new int[32];
   private int[] d3 = new int[32];
   private Animator d4;
   private Animator e2;
   private int u;
   private int v;
   private int w;
   private int x;
   private int y;
   private int z;
   private int A;
   private int B;
   private int C;
   private int D;
   private int E;
   private int F;
   private int G;
   private boolean a8;
   private boolean b6;
   private boolean c7;
   private static final int[] e3 = new int[]{11, 12, 13, 14, 15, 14, 13, 12, 11};
   private static final int[] f2 = new int[]{16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
   private static final int[] g2 = new int[]{31, 32, 33, 34, 35, 36, 37, 38, 39};
   private static final char[][] a9 = new char[][]{
      {'1'},
      {'A', 'B', 'C', '2'},
      {'D', 'E', 'F', '3'},
      {'G', 'H', 'I', '4'},
      {'J', 'K', 'L', '5'},
      {'M', 'N', 'O', '6'},
      {'P', 'Q', 'R', 'S', '7'},
      {'T', 'U', 'V', '8'},
      {'W', 'X', 'Y', 'Z', '9'},
      {'0'}
   };
   private boolean d5;
   private int H;
   private int I;
   private int J;
   private int K;
   private int L;
   private int M;
   private boolean e4;

   public TitleScreen(GameCanvas var1) {
      super(var1);
      var1.g = 197379;
      this.d4 = new Animator();
      this.e2 = new Animator();
      this.c5 = new Animator();
      this.c5.setRunning(false);
   }

   public final void a12(int var1) {
      this.n2();
      super.a.u2();
      this.f2();
      if (var1 == -1) {
         var1 = 7;
      }

      this.n = var1;
      if (var1 != 4 && var1 != 5) {
         this.c4(1);
      } else {
         this.n = 6;
         this.c4(var1);
      }

      this.o = 0;
   }

   private void e3(int var1) {
      super.a.q2(var1);
      if (this.a6 == null) {
         this.a6 = new Thread(this);
         this.p = 0;
         this.a6.start();
      } else {
         this.p = 0;
      }
   }

   public final void run() {
      while (this.p != 2) {
         if (this.p != 0) {
            try {
               synchronized (this) {
                  this.wait(1000L);
               }
            } catch (InterruptedException var4) {
            }
         } else {
            this.p = 1;

            try {
               this.a10();
            } catch (Exception var5) {
            }
         }
      }
   }

   private void a10() {
      GameCanvas var1 = super.a;
      if (!super.a.a11()) {
         new StringBuffer().append("updateLoadingState: ").append(this.o).toString();
         switch (this.o) {
            case 0:
               short[] var2 = new short[15];
               this.b5 = var2;
               this.d3(-1);
               this.d2();
               com.ea.sims3.SdkText.a3(1);
               this.o++;
               var1.o2();
               return;
            case 1:
               if (var1.a13(4)) {
                  this.o++;
                  return;
               }
               break;
            case 2:
               this.c4(this.n);
               this.p = 2;
         }
      }
   }

   private void d2() {
      super.a.a7.play(10);
   }

   public final void b4() {
   }

   public final void c2() {
      super.a.c2(3076, -1);
      super.a.a12(-1);
      com.ea.sims3.SdkSound.muteAll();
      com.ea.sims3.SdkText.b7(1);
   }

   public final void a(Graphics var1) {
      GameCanvas var2 = super.a;
      int var3 = super.a.k2;
      if (super.a.k2 != -1) {
         if (var3 == 5) {
            if (a5 == 4) {
               this.c(var1, 77);
            } else {
               this.h(var1);
            }

            this.d(var1);
         } else {
            this.h(var1);
            var2.d(var1);
            var2.b(var1);
         }
      } else {
         switch (a5) {
            case 1:
               var2.c(var1);
               return;
            case 4:
               this.c(var1, 77);
               this.d(var1);
               return;
            case 5:
               this.c(var1, 77);
               this.b(var1);
               var2.b(var1);
               return;
            case 6:
               this.e(var1);
               return;
            case 7:
               this.h(var1);
               this.i(var1);
               return;
            case 8:
               this.b(var1, 3);
               var2.b(var1);
               return;
            case 9:
               var2.a5.a(var1);
               return;
            case 10:
               this.j(var1);
               return;
            case 11:
               this.k(var1);
               return;
            case 12:
               this.l(var1);
               return;
            case 13:
               this.m(var1);
               return;
            case 14:
            case 15:
               this.n(var1);
               return;
            case 16:
               this.n(var1);
               var2.a(var1);
               this.b(var1);
               var2.b(var1);
               return;
            case 17:
               this.j(var1);
               var2.a(var1);
               this.b(var1);
               var2.b(var1);
               return;
            case 18:
               this.m(var1);
               var2.a(var1);
               this.b(var1);
               var2.b(var1);
               return;
            case 19:
               this.h(var1);
               this.i(var1);
               var2.a(var1);
               this.b(var1);
               var2.b(var1);
            case 2:
            case 3:
         }
      }
   }

   public final void a4(int var1, int var2) {
      if (a5 != 1) {
         GameCanvas var3 = super.a;
         if (super.a.e4 && this.b4 != 0) {
            this.c4(this.b4);
            var3.k2();
            this.b4 = 0;
         } else if (!var3.a11()) {
            int var4 = var3.k2;
            if (var3.k2 != -1) {
               if (var4 == 5) {
                  this.c2(var1, var2);
               } else {
                  var3.d2(var1, var2);
               }

               if (var3.k2 == -1) {
                  this.c4(a5);
               }
            } else {
               switch (a5) {
                  case 4:
                     this.c2(var1, var2);
                     return;
                  case 5:
                     if (this.a3(var1, var2, 4128, 256)) {
                        var3.a7.setEnabled(true);
                        this.f3(1);
                        return;
                     }

                     if (this.a3(var1, var2, 0, 512)) {
                        var3.a7.setEnabled(false);
                        this.f3(1);
                        return;
                     }
                     break;
                  case 6:
                     this.d2(var1, var2);
                     return;
                  case 7:
                     if (this.a3(var1, var2, 4128, 16)) {
                        int var5 = 5 + this.b5[2];
                        this.g2(this.b5[var5]);
                        return;
                     }

                     if (this.a3(var1, var2, 0, 4)) {
                        this.c4(19);
                        return;
                     }

                     var3.b3(this.b5, var1);
                     return;
                  case 8:
                     this.a5(var1, var2);
                     if (this.a3(var1, var2, 0, 4)) {
                        this.c4(7);
                        return;
                     }
                  case 9:
                  default:
                     break;
                  case 10:
                     this.e2(var1, var2);
                     return;
                  case 11:
                     this.g2(var1, var2);
                     return;
                  case 12:
                     this.h2(var1, var2);
                     return;
                  case 13:
                     this.i2(var1, var2);
                     return;
                  case 14:
                  case 15:
                     this.j2(var1, var2);
                     return;
                  case 16:
                     if (this.a3(var1, var2, 4128, 256)) {
                        var3.m2(var3.j);
                        var3.c3();
                        this.c4(14);
                        return;
                     }

                     if (this.a3(var1, var2, 0, 512)) {
                        this.c4(15);
                        return;
                     }
                     break;
                  case 17:
                     if (this.a3(var1, var2, 4128, 256)) {
                        this.c4(14);
                        return;
                     }

                     if (this.a3(var1, var2, 0, 516)) {
                        this.c4(10);
                        return;
                     }
                     break;
                  case 18:
                     if (this.a3(var1, var2, 4128, 256)) {
                        var3.r2();
                        var3.s2();
                        this.b2(2, -1);
                        return;
                     }

                     if (this.a3(var1, var2, 0, 516)) {
                        this.c4(13);
                        return;
                     }
                     break;
                  case 19:
                     if (this.a3(var1, var2, 4128, 256)) {
                        this.c4(3);
                        return;
                     }

                     if (this.a3(var1, var2, 0, 516)) {
                        this.c4(7);
                     }
               }
            }
         }
      }
   }

   public final void b7(int var1) {
      if (a5 == 1) {
         this.e3(var1);
      } else {
         switch (a5) {
            case 6:
               this.k2(var1);
               return;
            case 7:
               return;
            case 8:
               return;
            case 9:
               this.e2();
               return;
            case 10:
               this.a2(var1, false);
               this.l2(var1);
               return;
            case 11:
               this.a2(var1, true);
               return;
            case 12:
               this.a2(var1, true);
               this.n2(var1);
               return;
            case 13:
               this.p2(var1);
               return;
            case 14:
               this.a2(var1, false);
            case 15:
         }
      }
   }

   public final void c4(int var1) {
      GameCanvas var2 = super.a;
      int var3 = a5;
      a5 = var1;
      var2.g2();
      var2.h2();
      switch (var1) {
         case 1:
            var2.a4(0, 0);
            return;
         case 2:
            var2.b2(this.c3, this.m2);
            return;
         case 3:
            var2.d2();
            return;
         case 4:
            var2.a4(0, 16384);
            return;
         case 5:
            var2.a4(512, 256);
            this.l2(78, 79);
            return;
         case 6:
            var2.a12(10);
            var2.j2(2);
            var2.a4(0, 0);
            this.g2();
            return;
         case 7:
            var2.a12(10);
            this.q = 18;
            com.ea.sims3.SpriteRenderer.a(super.a.a6, 82);
            var2.c2(2, 0);
            var2.a4(0, 16);
            return;
         case 8:
            this.q2();
            var2.a4(4, 0);
            return;
         case 9:
            com.ea.sims3.SpriteRenderer.a(super.a.a6, 48);
            com.ea.sims3.SpriteRenderer.a(super.a.a6, 47);
            com.ea.sims3.SpriteRenderer.a(super.a.a6, 46);
            com.ea.sims3.SpriteRenderer.a(super.a.a6, 45);
            com.ea.sims3.SpriteRenderer.a(super.a.a6, 44);
            com.ea.sims3.SpriteRenderer.a(super.a.a6, 43);
            var2.c = true;
            var2.a5.a10();
            return;
         case 10:
            this.d4.play(681, 20);
            this.e2.play(679, 20);
            if (var3 != 11 && var3 != 17) {
               this.f2(0, 0);
            } else if (var3 == 11) {
               this.b(false);
            }

            var2.a4(4, 2);
            return;
         case 11:
            this.i2();
            this.b(true);
            var2.a(736, 1, super.h);
            var2.a4(4, var2.a23.a11 != -1 ? 2 : 0);
            return;
         case 12:
            this.j2();
            this.b(true);
            var2.a(744, 1, super.h);
            int var4 = var2.a23.s();
            boolean var5 = 5 == var4;
            var2.a4(4, var5 ? 2 : 0);
            return;
         case 13:
            if (var3 != 18) {
               this.k2();
            }

            var2.a4(4, var2.e() != 0 ? 2 : 0);
            return;
         case 14:
            this.e4 = false;
            this.a(false);
            var2.a4(4, 16);
            return;
         case 15:
            this.e4 = false;
            if (var2.j != 0) {
               var2.l2(0);
               this.a(false);
            }

            var2.a4(64, var2.e3() ? 32 : 0);
            return;
         case 16:
            var2.a4(512, 256);
            this.l2(55, 54);
            return;
         case 17:
            this.l2(57, 56);
            var2.a4(512, 256);
            return;
         case 18:
            this.l2(58, 52);
            var2.a4(512, 256);
            return;
         case 19:
            this.l2(68, 42);
            var2.a4(512, 256);
      }
   }

   private void f3(int var1) {
      this.b4 = var1;
      if (!super.a.b5()) {
         super.a.j2();
      }
   }

   private void b2(int var1, int var2) {
      this.c3 = var1;
      this.m2 = var2;
      this.f3(2);
   }

   public final void d3(int var1) {
      short[] var2 = this.b5;
      com.ea.sims3.GameCanvas.a(this.b5, 50, super.a.a22);
      com.ea.sims3.GameCanvas.c2(var2, 51);
      com.ea.sims3.GameCanvas.c2(var2, 59);
      com.ea.sims3.GameCanvas.c2(var2, 60);
      com.ea.sims3.GameCanvas.c2(var2, 61);
      if (super.a.a5.a11()) {
         com.ea.sims3.GameCanvas.c2(var2, 63);
      }

      com.ea.sims3.GameCanvas.c2(var2, 42);
      com.ea.sims3.GameCanvas.d2(var2, var1);
   }

   private void g2(int var1) {
      GameCanvas var2 = super.a;
      switch (var1) {
         case 42:
            this.c4(19);
         default:
            return;
         case 51:
            com.ea.sims3.SpriteRenderer.a5(82, 0);
            com.ea.sims3.SpriteRenderer.a5(48, 0);
            com.ea.sims3.SpriteRenderer.a5(47, 0);
            com.ea.sims3.SpriteRenderer.a5(46, 0);
            com.ea.sims3.SpriteRenderer.a5(45, 0);
            com.ea.sims3.SpriteRenderer.a5(44, 0);
            com.ea.sims3.SpriteRenderer.a5(43, 0);
            var2.l2(0);
            this.c4(14);
            return;
         case 59:
            this.c4(8);
            return;
         case 60:
            var2.r2(0);
            return;
         case 61:
            var2.r2(3);
            return;
         case 63:
            this.c4(9);
      }
   }

   private void e2() {
      PromoImages var1 = super.a.a5;
      if (super.a.a5.a7) {
         var1.b4();
      } else {
         super.a.c = false;
         this.c4(7);
      }
   }

   private void f2() {
      if (this.c4 == null) {
         int var1 = super.a.f;
         short[] var2 = new short[5 + var1];
         this.c4 = var2;
         com.ea.sims3.GameCanvas.a(var2, 3, super.a.a22);
         SdkString[] var3 = new SdkString[var1 + 1];
         this.a7 = var3;

         for (int var4 = 0; var4 < var1; var4++) {
            com.ea.sims3.GameCanvas.c2(var2, var4);
            var3[var4] = com.ea.sims3.SdkText.a(var4, 0, null);
         }

         int var5 = super.a.i2;
         if (super.a.i2 != -1) {
            var2[2] = (short)var5;
         }
      }
   }

   private void d(Graphics var1) {
      this.a(var1, this.c4, 1, this.a7);
      super.a.b(var1);
   }

   private void c2(int var1, int var2) {
      GameCanvas var3 = super.a;
      short var4 = this.c4[2];
      if (this.a3(var1, var2, 4128, 16384)) {
         var3.d3(var4);
         var3.c3();
         if (var3.k2 == 5) {
            var3.r2(0);
         } else {
            this.f3(5);
         }
      } else {
         if (this.a3(var1, var2, 524288, 32768)) {
            if (var3.k2 == 5) {
               com.ea.sims3.SdkText.a13(var3.i2);
               var3.r2(0);
               return;
            }
         } else {
            super.a.b3(this.c4, var1);
            short var5;
            if ((var5 = this.c4[2]) != var4) {
               com.ea.sims3.SdkText.a13(var5);
            }
         }
      }
   }

   private void g2() {
      super.a.l2();
      this.h2(0);
   }

   private void h2(int var1) {
      int var2 = 0;
      switch (var1) {
         case 0:
            var2 = 500;
            break;
         case 1:
            var2 = 1000;
            break;
         case 2:
            var2 = 2000;
            break;
         case 3:
            var2 = 600;
            break;
         case 4:
            var2 = 500;
            break;
         case 5:
            var2 = 600;
            break;
         case 6:
            var2 = 300;
            break;
         case 7:
            var2 = 750;
            break;
         case 8:
            var2 = 700;
            break;
         case 9:
            var2 = 600;
            break;
         case 10:
            var2 = 600;
            break;
         case 11:
            var2 = 300;
            break;
         case 12:
            var2 = 750;
            break;
         case 13:
            var2 = 750;
            break;
         case 14:
            var2 = 500;
            break;
         case 15:
            var2 = 750;
            break;
         case 16:
            var2 = this.b5[0] * 200;
            break;
         case 17:
            var2 = 500;
            break;
         case 18:
            this.c4(7);
      }

      this.q = var1;
      this.r = 0;
      this.s = var2;
   }

   private void k2(int var1) {
      this.r += var1;
      if (this.r >= this.s) {
         if (this.q == 13) {
            this.r %= 750;
            return;
         }

         this.h2(this.q + 1);
      }
   }

   private void d2(int var1, int var2) {
      if (this.q == 13 && this.a3(var1, var2, 4128, 0)) {
         this.h2(this.q + 1);
      }
   }

   private void e(Graphics var1) {
      switch (this.q) {
         case 0:
            this.c(var1, 0);
            return;
         case 1:
            this.f(var1);
            this.a(var1, this.s - this.r, this.s);
            return;
         case 2:
            this.f(var1);
            return;
         case 3:
            this.f(var1);
            this.a(var1, this.r, this.s);
            return;
         case 4:
            this.c(var1, 0);
            return;
         case 5:
            this.e(var1, this.r, this.s);
            return;
         case 6:
            this.c(var1, 1);
            return;
         case 7:
            this.c(var1, 1);
            this.g(var1);
            this.d(var1, this.s - this.r, this.s);
            return;
         case 8:
         case 9:
         case 10:
         case 11:
            this.c(var1, 1);
            this.g(var1);
            return;
         case 12:
         case 13:
            this.g(var1);
            return;
         case 14:
            this.g(var1);
            this.i(var1);
            return;
         case 15:
         case 16:
         case 17:
            this.h(var1);
            this.i(var1);
         case 18:
      }
   }

   private void a(Graphics var1, int var2, int var3) {
      GameCanvas var4 = super.a;
      int var5 = 255 - Math.min(var2 * 255 / var3, 255);
      int var6 = 0xFF000000 | var5 << 16 | var5 << 8 | var5 << 0;
      var4.b2(var1, 66, var6, 0, 0, var4.getWidth(), var4.getHeight());
   }

   private void d(Graphics var1, int var2, int var3) {
      GameCanvas var4 = super.a;
      int var5 = Math.min(var2 * 255 / var3, 255);
      int var6 = 0xFF000000 | var5 << 16 | var5 << 8 | var5 << 0;
      var4.b2(var1, 65, var6, 0, 0, var4.getWidth(), var4.getHeight());
   }

   private void e(Graphics var1, int var2, int var3) {
      GameCanvas var4 = super.a;
      int var5 = Math.min(var2 * 255 / var3, 255);
      int var6 = 0xFF000000 | var5 << 16 | var5 << 8 | var5 << 0;
      var1.setColor(var6);
      var1.fillRect(0, 0, var4.getWidth(), var4.getHeight());
   }

   private void c(Graphics var1, int var2) {
      GameCanvas var3 = super.a;
      com.ea.sims3.SpriteRenderer.a(var1, var2);
      var1.fillRect(0, 0, var3.getWidth(), var3.getHeight());
   }

   private void f(Graphics var1) {
      GameCanvas var2 = super.a;
      this.c(var1, 60);
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 556, 0, var2.a(), var2.b());
   }

   private void g(Graphics var1) {
      GameCanvas var2 = super.a;
      short var3 = 0;
      if (com.ea.sims3.GameCanvas.b2 == 1) {
         var3 = 558;
      } else {
         var3 = 557;
      }

      int var4 = var2.a();
      int var5 = var2.b();
      int var6 = 0;
      if (this.s != 0) {
         var6 = com.ea.sims3.FixedMath.div(this.r, this.s);
      }

      if (this.q >= 12) {
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var3, 8, var4, var5);
         if (this.q == 12) {
            this.d(var1, this.s - this.r, this.s);
         }
      }

      if (this.q >= 11) {
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var3, 6, var4, var5);
         if (this.q == 11) {
            this.d(var1, this.s - this.r, this.s);
         }
      }

      com.ea.sims3.SpriteRenderer.drawFrame(var1, var3, 0, var4, var5);
      int var7 = var4;
      if (this.q == 10) {
         int var8 = var2.getWidth();
         var7 = b(var8, var4, var6, 3);
      }

      int var14 = Math.min(0, var7 - var4);
      if (this.q >= 8) {
         int var10 = -com.ea.sims3.SpriteRenderer.d(var3, 1);
         int var12 = var5;
         if (this.q == 8) {
            var12 = b(var10, var5, var6, 4);
         }

         if (var12 >= var5) {
            com.ea.sims3.SpriteRenderer.drawFrame(var1, var3, 2, var4, var5);
         }

         com.ea.sims3.SpriteRenderer.drawFrame(var1, var3, 1, var4 + var14, var12);
      }

      if (this.q >= 9) {
         int var11 = var4;
         if (this.q == 9) {
            var11 = b(0, var4, var6, 3);
         }

         int[] var15 = super.a2;
         com.ea.sims3.SpriteRenderer.a2(super.a2, var3, 3, 0);
         this.a4(var11 + var14 + var15[0], var5 + var15[1], 3);
      }

      if (this.q >= 10) {
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var3, 4, var7, var5);
         if (var7 <= var4) {
            com.ea.sims3.SpriteRenderer.drawFrame(var1, var3, 5, var7, var5);
         }
      }

      if (this.q == 13 && this.r % 750 < 375) {
         int[] var9 = super.a2;
         com.ea.sims3.SpriteRenderer.a2(super.a2, var3, 7, 0);
         var2.a2(34, 0, var4 + var9[0], var5 + var9[1], 3);
      }
   }

   private void a4(int var1, int var2, int var3) {
      GameCanvas var4 = super.a;
      SdkString var5 = super.a.a4(25);
      var4.a(var5, var3, var1, var2, 3);
   }

   private static int b(int var0, int var1, int var2, int var3) {
      int var4 = var1 - var0;
      switch (var3) {
         case 0:
         default:
            return var0 + com.ea.sims3.FixedMath.clamp(com.ea.sims3.FixedMath.mul(var2, var4), 0, var4);
         case 1:
            var2 = com.ea.sims3.FixedMath.smoothStep(0, 65536, var2);
            return var0 + com.ea.sims3.FixedMath.clamp(com.ea.sims3.FixedMath.mul(var2, var4), 0, var4);
         case 2:
            var2 = com.ea.sims3.FixedMath.smoothStep(0, 65536, 32768 + (var2 >> 1));
            return var0 + com.ea.sims3.FixedMath.clamp(com.ea.sims3.FixedMath.mul(var2, var4), 0, var4);
         case 3:
            if (var2 < 58982) {
               var2 = com.ea.sims3.FixedMath.mul(com.ea.sims3.FixedMath.smoothStep(0, 58982, var2), 68812);
            } else {
               int var16 = 65536 - com.ea.sims3.FixedMath.div(var2 - 58982, 6554);
               var2 = 65536 + com.ea.sims3.FixedMath.mul(3276, var16);
            }

            return var0 + com.ea.sims3.FixedMath.mul(var2, var4);
         case 4:
            if (var2 < 55705) {
               var2 = com.ea.sims3.FixedMath.mul(com.ea.sims3.FixedMath.smoothStep(0, 55705, var2), 68812);
            } else {
               int var15 = 65536 - com.ea.sims3.FixedMath.div(var2 - 55705, 9831);
               var2 = 65536 + com.ea.sims3.FixedMath.mul(3276, var15);
            }

            return var0 + com.ea.sims3.FixedMath.mul(var2, var4);
         case 5:
            if (var2 < 49152) {
               var2 = com.ea.sims3.FixedMath.mul(com.ea.sims3.FixedMath.smoothStep(0, 49152, var2), 98304);
            } else {
               int var14 = 65536 - com.ea.sims3.FixedMath.div(var2 - 49152, 16384);
               var2 = 65536 + com.ea.sims3.FixedMath.mul(32768, var14);
            }

            return var0 + com.ea.sims3.FixedMath.mul(var2, var4);
         case 6:
            if (var2 < 32768) {
               var2 = com.ea.sims3.FixedMath.mul(com.ea.sims3.FixedMath.sinShifted(com.ea.sims3.FixedMath.div(com.ea.sims3.FixedMath.mul(var2, 102944), 32768)), 72089);
            } else {
               int var5;
               int var6 = com.ea.sims3.FixedMath.mul(var5 = com.ea.sims3.FixedMath.div(var2 - 32768, 32768), 617662);
               int var7 = 6554 - com.ea.sims3.FixedMath.mul(6554, var5);
               var2 = com.ea.sims3.FixedMath.mul(com.ea.sims3.FixedMath.cos(var6), var7) + 65536;
            }

            return var0 + com.ea.sims3.FixedMath.mul(var2, var4);
      }
   }

   private void h(Graphics var1) {
      GameCanvas var2 = super.a;
      short var3 = 0;
      if (com.ea.sims3.GameCanvas.b2 == 1) {
         var3 = 558;
      } else {
         var3 = 557;
      }

      int var4 = var2.a();
      int var5 = var2.b();
      com.ea.sims3.SpriteRenderer.drawFrame(var1, var3, 8, var4, var5);
   }

   private void i(Graphics var1) {
      GameCanvas var2 = super.a;
      short[] var3 = this.b5;
      short var4 = this.b5[0];
      short var5 = var3[2];
      int var7 = var4 * 22 - 2 + 14 + 19 + 5;
      int var8 = var2.getWidth() * 140 / 176;
      int var9 = var2.getWidth() - var8 >> 1;
      int var10 = (var2.getHeight() - var7 >> 1) + 2;
      int var11 = 0;
      if (this.s != 0) {
         var11 = com.ea.sims3.FixedMath.div(this.r, this.s);
      }

      int var12 = var10;
      int var13 = var7;
      if (this.q == 14) {
         var13 = b(27, var7, var11, 0);
         var12 = (var2.getHeight() - var13 >> 1) + 2;
      }

      this.a2(var1, var9, var12, var8, var13, -1, 4);
      if (this.q >= 15) {
         var13 = var2.getWidth() >> 1;
         int var14;
         int var15 = var14 = var10 + 9;
         if (this.q == 15) {
            var15 = b(-com.ea.sims3.SpriteRenderer.d(557, 9), var14, var11, 3);
         }

         com.ea.sims3.SpriteRenderer.drawFrame(var1, 557, 9, var13, var15);
         int[] var16 = super.a2;
         com.ea.sims3.SpriteRenderer.a2(super.a2, 557, 9, 0);
         this.a4(var13 + var16[0], var15 + var16[1], 4);
      }

      if (this.q >= 16) {
         var12 = var8 - 14;
         var13 = var9 + 7;
         int var24 = var4;
         if (this.q == 16) {
            var24 = this.r / 200;
            var11 = com.ea.sims3.FixedMath.div(this.r % 200, 200);
         }

         int var25 = var10 + 22 + 7;

         for (int var17 = 0; var17 < var4; var17++) {
            short var18 = var3[5 + var17];
            int var19 = var25;
            if (var17 == var24) {
               var19 = var25 + b(-3, 0, var11, 5);
            } else if (var17 > var24) {
               break;
            }

            this.a(var1, var13, var19, var12, 20, var17 == var5, var18);
            var25 += 22;
         }
      }

      if (this.q >= 17) {
         var12 = 0;
         if (this.q == 17) {
            var12 = 15 - b(0, 15, var11, 0);
         }

         var2.a(var1, var12);
      }
   }

   private void h2() {
      GameCanvas var1 = super.a;
      Neighborhood var2 = super.a.a23;
      var1.p2();
      var1.q2();
      this.a8 = var2.i(0, 6) != 0;
      this.b6 = var2.i(0, 8) != 0;
      this.c7 = this.a13(var2.i(0, 2));
      this.H = 0;
      this.c4(10);
      this.a(false);
   }

   private void a(boolean var1) {
      this.b(var1);
      GameCanvas var2 = super.a;
      Lot var3 = super.a.a24;
      Neighborhood var4 = var2.a23;
      int[] var5 = this.c6;
      int[] var6 = this.d3;
      var3.a2(0, var5, var6);
      int var7 = var4.i(0, 0) == 0 ? 2048 : 1024;
      var2.c2(3072, -1);
      var2.a(var7, var5, var6, 0);
   }

   private void a(Graphics var1, int var2, int var3, boolean var4) {
      GameCanvas var5 = super.a;
      Neighborhood var6 = super.a.a23;
      int var7 = this.c5.animId;
      if (var4) {
         int[] var8 = super.a2;
         com.ea.sims3.SpriteRenderer.a2(super.a2, var7, 0, 1);
         var2 -= var8[0];
         var3 -= var8[1];
      }

      this.c5.draw(var1, var2, var3);
      int[] var11 = super.a2;
      this.c5.matchesFrame(var11, 0);
      int var9 = var6.i(0, 2);
      com.ea.sims3.SpriteRenderer.drawFrame(var1, this.t, var9, var2 + var11[0], var3 + var11[1]);
      if (var6.i(0, 8) != 0) {
         int var10 = var7 == 703 ? 704 : (var7 == 706 ? 707 : 17091);
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var10, 0, var2, var3);
      }
   }

   private void b(boolean var1) {
      Neighborhood var2 = super.a.a23;
      short var3;
      short var4;
      if (super.a.a23.i(0, 0) == 0) {
         if (var1) {
            var3 = 706;
            var4 = 708;
         } else {
            var3 = 17090;
            var4 = 17092;
         }
      } else {
         var3 = 703;
         var4 = 705;
      }

      this.c5.play(var3, 4);
      this.t = var4;
   }

   private void a2(int var1, boolean var2) {
      if (!this.c5.isRunning()) {
         this.b(var2);
      }

      this.c5.advance(var1);
   }

   private void l2(int var1) {
      this.d4.advance(var1);
      if (!this.d4.isRunning()) {
         this.d4.play(681, 20);
      }

      this.e2.advance(var1);
      if (!this.e2.isRunning()) {
         this.e2.play(679, 20);
      }

      this.i2(var1);
      this.v = com.ea.sims3.BaseScreen.a(this.v, this.w, var1, 8);
      this.z = com.ea.sims3.BaseScreen.a(this.z, this.A, var1, 8);
      if (Math.abs(this.v - this.w) < 32768) {
         this.v = this.w;
      }

      if (Math.abs(this.z - this.A) < 32768) {
         this.z = this.A;
      }
   }

   private void e2(int var1, int var2) {
      if (this.a3(var1, var2, 0, 4)) {
         this.c4(17);
      } else if (this.a3(var1, var2, 0, 2)) {
         this.f3(11);
      } else if (this.a3(var1, var2, 8196, 0) && this.u > 0) {
         this.d4.play(680, 16);
         this.f2(this.u - 1, -1);
      } else if (this.a3(var1, var2, 16640, 0) && this.u < d2.length - 1) {
         this.e2.play(678, 16);
         this.f2(this.u + 1, 1);
      } else {
         if (d2[this.u] == 723) {
            if (this.a3(var1, var2, 32784, 0)) {
               this.m2(this.E - 1);
               this.o2();
               return;
            }

            if (this.a3(var1, var2, 65600, 0)) {
               this.m2(this.E + 1);
               this.p2();
               return;
            }
         } else {
            if (this.a3(var1, var2, 32784, 0) && this.E > this.F) {
               this.b(this.E - 1, false);
               this.o2();
               this.a(false);
               return;
            }

            if (this.a3(var1, var2, 65600, 0) && this.E < this.G) {
               this.b(this.E + 1, false);
               this.p2();
               this.a(false);
            }
         }
      }
   }

   private void m2(int var1) {
      Neighborhood var2 = super.a.a23;
      int var3 = super.a.a23.c6;
      if (var1 < 0) {
         var1 = var3 - 1;
      } else if (var1 >= var3) {
         var1 = 0;
      }

      this.E = var1;
      byte[] var4 = var2.a14(var1);

      for (int var5 = 0; var5 < 10; var5++) {
         var2.i2(var5, var4[var5]);
      }

      this.a8 = var4[10] == 0;
      this.b6 = var4[11] == 0;
      this.a(false);
   }

   private void f2(int var1, int var2) {
      GameCanvas var3 = super.a;
      if (!this.a8 && d2[var1] == 731) {
         var1 += var2;
      }

      if (!this.b6 && d2[var1] == 734) {
         var1 += var2;
      }

      if (!this.c7 && d2[var1] == 728) {
         var1 += var2;
      }

      int var4 = var3.getHeight() - 15 - (com.ea.sims3.SpriteRenderer.d(697, 0) << 1);
      int var6 = com.ea.sims3.SpriteRenderer.frameCount(687);
      int var7 = com.ea.sims3.SpriteRenderer.d(688, 0);
      int var8 = (var4 >> 1) + 2 - var7 * var1 - (var7 >> 1);
      int var9 = var4 - 2 - var7 * var6;
      var8 = com.ea.sims3.FixedMath.clamp(var8, var9, 2);
      this.w = var8 << 16;
      if (var2 == 0) {
         this.v = var8 << 16;
      }

      this.u = var1;
      short var5 = 196;
      short var10 = 196;
      byte var11 = -1;
      byte var13 = -1;
      byte var14 = 0;
      switch (d2[var1]) {
         case 723:
         default:
            break;
         case 724:
            var10 = 691;
            var5 = 690;
            var11 = 0;
            break;
         case 725:
            var13 = 0;
            var11 = 1;
            break;
         case 726:
            if (var3.a23.i(0, 0) == 1) {
               var5 = 692;
               var10 = 693;
            } else {
               var5 = 694;
               var10 = 695;
            }

            var11 = 2;
            break;
         case 727:
            var13 = 1;
            var11 = 3;
            break;
         case 728:
            var13 = 2;
            var11 = 4;
            break;
         case 729:
            var13 = 3;
            var11 = 5;
            break;
         case 730:
            var5 = 700;
            var10 = 701;
            this.E = this.a8 ? 0 : 1;
            break;
         case 731:
            var13 = 4;
            var11 = 6;
            var14 = 1;
            break;
         case 732:
            var13 = 5;
            var11 = 7;
            break;
         case 733:
            var5 = 698;
            var10 = 699;
            this.E = this.b6 ? 0 : 1;
            break;
         case 734:
            var13 = 6;
            var11 = 8;
            var14 = 1;
            break;
         case 735:
            var13 = 7;
            var11 = 9;
      }

      this.x = var11;
      this.y = var13;
      this.B = 689;
      this.C = var5;
      this.D = var10;
      this.F = var14;
      if (var13 != -1) {
         this.G = var3.a24.a14(var13).length - 1;
      } else {
         this.G = com.ea.sims3.SpriteRenderer.frameCount(var5) - 1;
      }

      if (var11 != -1) {
         this.b(var3.a23.i(0, var11), true);
      } else {
         this.b(this.E, true);
      }
   }

   private boolean a13(int var1) {
      int var2 = 1 << var1;
      if (super.a.a23.i(0, 0) == 0) {
         var2 <<= 16;
      }

      return (var2 & 786445) != 0;
   }

   private void b(int var1, boolean var2) {
      Neighborhood var3 = super.a.a23;
      this.E = var1;
      if (this.x != -1) {
         var3.i2(this.x, var1);
      }

      short var4;
      if ((var4 = d2[this.u]) == 730) {
         int var5;
         if (var1 == 0) {
            this.a8 = true;
            var5 = var3.i(0, 5) + 1;
         } else {
            this.a8 = false;
            var5 = 0;
         }

         var3.i2(6, var5);
      } else if (var4 == 733) {
         int var13;
         if (var1 == 0) {
            this.b6 = true;
            var13 = var3.i(0, 7) + 1;
         } else {
            this.b6 = false;
            var13 = 0;
         }

         var3.i2(8, var13);
      } else if (var4 == 724 || var4 == 726) {
         this.c7 = this.a13(var3.i(0, 2));
      }

      int[] var14 = super.a2;
      com.ea.sims3.SpriteRenderer.b(super.a2, 689, 0, 0);
      int var6 = var14[2];
      int var7 = this.G - this.F + 1;
      int var8 = com.ea.sims3.SpriteRenderer.d(683, 0);
      int var9 = Math.min(var7 * var8, var6);
      int var10 = var6 - var9;
      int var11 = (var9 >> 1) + 0 - var8 * var1 - (var8 >> 1) + var10;
      int var12 = var9 - 0 - var8 * var7 + (var10 >> 1);
      var11 = com.ea.sims3.FixedMath.clamp(var11, var12, 0);
      this.A = var11 << 16;
      if (var2) {
         this.z = var11 << 16;
      }
   }

   private static final void a(Graphics var0, int[] var1, int var2, int var3, int var4, int var5) {
      int var6 = var1.length;
      int var7 = (var5 << 16) / var6;
      int var8 = 0;

      for (int var10 = 0; var10 < var6; var10++) {
         com.ea.sims3.SpriteRenderer.a(var0, var1[var10]);
         int var9 = (var10 + 1) * var7 + 32768 >> 16;
         var0.fillRect(var2, var3 + var8, var4, var9 - var8);
         var8 = var9;
      }
   }

   private static final void c2(Graphics var0, int var1, int var2, int var3, int var4) {
      int var5 = a3(var4);
      int var6 = var4 - var5;
      a(var0, f2, var1, var2, var3, var4);
      a(var0, g2, var1, var2 + var6, var3, var5);
   }

   private static final int a3(int var0) {
      return com.ea.sims3.FixedMath.mul(17902, var0);
   }

   private void j(Graphics var1) {
      GameCanvas var2 = super.a;
      int var3 = super.a.getWidth();
      int var4 = var2.getHeight() - 15;
      c2(var1, 0, 0, var3, var4);
      int[] var5 = super.a2;
      int var8 = com.ea.sims3.SpriteRenderer.d(697, 0);
      int var9;
      int var14 = (var9 = com.ea.sims3.SpriteRenderer.c(697, 0)) + 4;
      int var15 = 0 + var8;
      int var16 = var9 - 4;
      int var17 = var4 - (var8 << 1);
      int var18 = var3 + 2 + var14 >> 1;
      int var19 = a3(var4);
      int var20 = var4 - (var19 >> 1);
      int var21 = var1.getClipX();
      int var22 = var1.getClipY();
      int var23 = var1.getClipWidth();
      int var24 = var1.getClipHeight();
      com.ea.sims3.SpriteRenderer.a(var1, 60);
      var1.fillRect(2, 0, 1, var4);
      var1.fillRect(2 + var14 - 1, 0, 1, var4);
      com.ea.sims3.SpriteRenderer.a(var1, 77);
      var1.fillRect(3, 0, 1, var4);
      var1.fillRect(2 + var14 - 2, 0, 1, var4);
      com.ea.sims3.SpriteRenderer.a(var1, 76);
      var1.fillRect(4, var8, 2, var17);
      var1.fillRect(2 + var14 - 2 - 2, var8, 2, var17);
      a(var1, e3, 6, var15, var16, var17);
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 697, 0, 4, 0);
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 696, 0, 4, var4);
      int var25 = 6 + (var16 >> 1);
      int var26 = com.ea.sims3.SpriteRenderer.frameCount(687);
      int var27 = com.ea.sims3.SpriteRenderer.d(688, 0);
      if (this.u > 0) {
         com.ea.sims3.SpriteRenderer.a2(var5, 697, 0, 0);
         this.d4.draw(var1, 4 + var5[0], 0 + var5[1]);
      }

      if (this.u < var26 - 1) {
         com.ea.sims3.SpriteRenderer.a2(var5, 696, 0, 0);
         this.e2.draw(var1, 4 + var5[0], var4 + var5[1]);
      }

      var1.setClip(6, var15, var16, var17);
      int var28 = var15 + (this.v >> 16);

      for (int var29 = 0; var29 < var26; var29++) {
         int var30 = var29 == this.u ? 688 : 687;
         int var31 = var29;
         if (d2[var29] == 731 && !this.a8) {
            var30 = 686;
            var31 = 0;
         }

         if (d2[var29] == 734 && !this.b6) {
            var30 = 685;
            var31 = 0;
         }

         if (d2[var29] == 728 && !this.c7) {
            var30 = 684;
            var31 = 0;
         }

         com.ea.sims3.SpriteRenderer.drawFrame(var1, var30, var31, var25, var28);
         var28 += var27;
      }

      var1.setClip(var21, var22, var23, var24);
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 702, 0, var18, var20);
      this.a(var1, var18, var20, false);
      short var44 = d2[this.u];
      var28 = var2.getWidth() - var14 - 2 - 8;
      int var47 = 2 + var14 + 4;
      var25 = this.c(var1, var47, var28, 719, var44);
      var27 = this.B;
      if (this.B != 196) {
         var47 = var25 + com.ea.sims3.FixedMath.mul(var4 - var19 - var25, 12179);
         com.ea.sims3.SpriteRenderer.drawFrame(var1, var27, 0, var18, var47);
         com.ea.sims3.SpriteRenderer.b(var5, var27, 0, 0);
         int var49 = var18 + var5[0];
         int var50 = var47 + var5[1];
         int var32 = var5[2];
         int var33 = var5[3];
         int var34 = var50 + (var33 >> 1);
         int var35 = com.ea.sims3.SpriteRenderer.c(683, 0);
         var1.setClip(var49, var50, var32, var33);
         boolean var36 = this.y != -1;
         int var37 = var49 + (this.z >> 16) + (var36 ? 0 : var35 >> 1);

         for (int var38 = this.F; var38 <= this.G; var38++) {
            if (var36) {
               Lot var39 = var2.a24;
               int var40 = this.y;
               byte[] var41 = var39.a14(var40);
               int[] var42 = var39.a15(var41[var38]);
               this.a(var1, var37, var34, var42, var38 == this.E);
            } else {
               int var51 = var38 == this.E ? this.C : this.D;
               com.ea.sims3.SpriteRenderer.drawFrame(var1, var51, var38, var37, var34);
            }

            var37 += var35;
         }

         var1.setClip(var21, var22, var23, var24);
         if (this.E > this.F || var44 == 723) {
            com.ea.sims3.SpriteRenderer.a2(var5, var27, 0, 0);
            super.a4.draw(var1, var18 + var5[0], var47 + var5[1]);
         }

         if (this.E < this.G || var44 == 723) {
            com.ea.sims3.SpriteRenderer.a2(var5, var27, 0, 1);
            super.b2.draw(var1, var18 + var5[0], var47 + var5[1]);
         }
      }

      var2.b(var1);
   }

   private void a(Graphics var1, int var2, int var3, int[] var4, boolean var5) {
      int var6 = var5 ? 683 : 682;
      com.ea.sims3.SpriteRenderer.drawFrame(var1, var6, 0, var2, var3);
      int[] var7 = super.a2;
      com.ea.sims3.SpriteRenderer.b(super.a2, 682, 0, 0);
      int var8 = var2 + var7[0];
      int var9 = var3 + var7[1];
      int var10 = var7[2];
      int var11 = var7[3];
      var1.setColor(var4[1]);
      var1.fillRect(var8, var9, var10, var11);
      var1.setColor(var4[3]);
      var1.fillRect(var8 + 1, var9 + 1, var10 - 2, 3);
      var1.setColor(var4[2]);
      var1.fillRect(var8 + 1, var9 + 1 + 3, var10 - 2, 5);
   }

   private void g2(int var1, int var2) {
      this.a5(var1, var2);
      if (this.a3(var1, var2, 0, 4)) {
         this.c4(10);
      } else if (this.a3(var1, var2, 0, 2)) {
         this.c4(12);
      } else {
         if (this.a3(var1, var2, 4128, 16)) {
            int var3 = super.l;
            super.a.a23.d3(var3);
            super.a.a4(4, 2);
         }
      }
   }

   private void k(Graphics var1) {
      GameCanvas var2 = super.a;
      this.a(var1, 18);
      int var3 = var2.getWidth();
      int var4 = var2.getHeight() - 15;
      this.a(var1, var3, var4, true);
      var2.b(var1);
   }

   private void i2() {
      Neighborhood var1 = super.a.a23;
      this.d(var1.j(), 15, 0);
   }

   public final void a2(Graphics var1, int var2, int var3, int var4, int var5) {
      GameCanvas var6 = super.a;
      Neighborhood var7 = super.a.a23;
      int var8 = var2 + (var4 >> 1);
      int var9 = var3;
      int var10 = super.k;
      int var11 = super.l;

      while (var9 < var3 + var5 && var10 < super.j) {
         short var12 = var7.a11(var10);
         byte var15 = -1;
         byte var13;
         byte var14;
         if (var10 == var7.a11) {
            var14 = 0;
            var13 = 1;
            var15 = 75;
         } else {
            var14 = 1;
            var13 = 2;
         }

         if (var10 == var11) {
            var15 = 82;
         }

         if (var15 != -1) {
            this.a(var1, var2, var9, var4, 15, var3 + var5, var15, var10);
         }

         var6.a2(var12, var14, var8, var9 + var13, 17);
         var9 += 15;
         var10++;
         com.ea.sims3.BaseScreen.a2(var1, var2, var9, var4);
      }
   }

   public final int a(Graphics var1, int var2, int var3, int var4) {
      int var5 = 8 + 4 * super.e;
      if (var1 != null) {
         this.j(var1, var2, var3, var4, var5);
         int var6 = var4 - 8 - 15;
         int var7 = var2 + 4;
         int var8;
         int var9 = (var8 = var3 + 4) + super.e;
         GameCanvas var10 = super.a;
         super.a.a2(722, 1, var7, var8, 20);
         int var11 = super.l;
         short var12 = var10.a23.b6(var11);
         var10.a(0, var12, 1, var6, var7, var9, 20);
      }

      return var5;
   }

   private void n2(int var1) {
      super.a.i2(var1);
   }

   private void h2(int var1, int var2) {
      GameCanvas var3 = super.a;
      Neighborhood var4 = super.a.a23;
      this.a5(var1, var2);
      if (this.a3(var1, var2, 0, 4)) {
         this.c4(11);
      } else if (this.a3(var1, var2, 0, 2)) {
         this.c4(13);
      } else {
         if (this.a3(var1, var2, 4128, 16)) {
            int var5 = super.l;
            if (var4.j(0, var5) == -1) {
               var4.l2(var5);
            } else {
               var4.m2(var5);
            }

            int var6 = var3.a23.s();
            boolean var7 = 5 == var6;
            var3.a4(4, var7 ? 2 : 0);
         }
      }
   }

   private void l(Graphics var1) {
      GameCanvas var2 = super.a;
      this.a(var1, 19);
      int var3 = var2.getWidth();
      int var4 = var2.getHeight() - 15;
      this.a(var1, var3, var4, true);
      var2.b(var1);
   }

   private void j2() {
      this.d(com.ea.sims3.Neighborhood.o(), 15, 0);
   }

   public final void b2(Graphics var1, int var2, int var3, int var4, int var5) {
      GameCanvas var6 = super.a;
      Neighborhood var7 = super.a.a23;
      int var8 = var2 + (var4 >> 1);
      int var9 = var3;
      int var10 = super.k;
      int var11 = super.l;

      while (var9 < var3 + var5 && var10 < super.j) {
         short var12 = var7.f2(var10);
         byte var15 = -1;
         byte var13;
         byte var14;
         if (var7.j(0, var10) != -1) {
            var14 = 0;
            var13 = 1;
            var15 = 75;
         } else {
            var14 = 1;
            var13 = 2;
         }

         if (var10 == var11) {
            var15 = 82;
         }

         if (var15 != -1) {
            this.a(var1, var2, var9, var4, 15, var3 + var5, var15, var10);
         }

         var6.a2(var12, var14, var8, var9 + var13, 17);
         var9 += 15;
         var10++;
         com.ea.sims3.BaseScreen.a2(var1, var2, var9, var4);
      }
   }

   private static final int b2(int var0) {
      switch (var0) {
         case 1:
            return 9;
         case 2:
            return 0;
         case 4:
            return 1;
         case 8:
            return 2;
         case 16:
            return 3;
         case 32:
            return 4;
         case 64:
            return 5;
         case 128:
            return 6;
         case 256:
            return 7;
         case 512:
            return 8;
         default:
            return -1;
      }
   }

   private void k2() {
      this.d5 = false;
      this.H = super.a.b2().a4;
      this.I = 0;
      this.J = -1;
      this.K = 0;
   }

   private void o2(int var1) {
      GameCanvas var2 = super.a;
      super.a.c4(1023);
      char[] var3 = a9[var1];
      if (this.d5 && var1 == this.J) {
         this.I = 0;
         this.K = (this.K + 1) % var3.length;
         var2.a(this.H, var3[this.K]);
      } else {
         this.l2();
         if (var2.e() != 8) {
            var2.b(this.H, var3[0]);
            this.d5 = true;
            this.I = 0;
            this.J = var1;
            this.K = 0;
         }
      }
   }

   private void l2() {
      if (this.d5) {
         this.d5 = false;
         if (this.H < 8) {
            this.H++;
         }

         this.J = -1;
      }
   }

   private void p2(int var1) {
      GameCanvas var2 = super.a;
      this.I += var1;
      this.M -= var1 * 1638;
      this.L = var2.a2(957, 0);
      if (this.M < -(this.L << 16)) {
         this.M = var2.getWidth() << 16;
      }

      if (this.d5 && this.I >= 1250) {
         this.l2();
      }
   }

   private void i2(int var1, int var2) {
      GameCanvas var3 = super.a;
      int var4 = super.a.e();
      if (this.a3(var1, var2, 1023, 0)) {
         int var5;
         if ((var5 = b2(var1)) != -1) {
            this.o2(var5);
            var3.a4(4, var3.e() != 0 ? 2 : 0);
         }
      } else {
         if (this.a3(var1, var2, 4096, 2)) {
            if (var3.e() != 0) {
               this.c4(18);
               return;
            }
         } else {
            if (this.a3(var1, var2, 524288, 0)) {
               this.l2();
               if (var4 == 0) {
                  this.c4(12);
                  return;
               }

               if (this.H > 0) {
                  this.H--;
               }

               var3.k2(this.H);
               var3.a4(4, var3.e() != 0 ? 2 : 0);
               return;
            }

            if (this.a3(var1, var2, 0, 4)) {
               this.c4(12);
               return;
            }

            if (this.a3(var1, var2, 32768, 0)) {
               this.l2();
               if (this.H != 0) {
                  this.H--;
                  return;
               }
            } else if (this.a3(var1, var2, 65536, 0)) {
               this.l2();
               if (this.H != var4) {
                  this.H++;
               }
            }
         }
      }
   }

   private void m(Graphics var1) {
      GameCanvas var2 = super.a;
      Neighborhood var3 = super.a.a23;
      c2(var1, 0, 0, var2.getWidth(), var2.getHeight() - 15);
      int var4 = var2.getWidth();
      int var5 = var2.getHeight() - 15;
      int var6 = var4 * 7 / 10;
      int var7 = var4 - var6 >> 1;
      int var8 = super.i * 6 + 6 + 22 + 5;
      int var9 = var5 - var8 + 5;
      int var10 = this.c(var1, var7, var6, 746, -1);
      SdkString var11 = var2.b2();
      int var12 = var2.a(var11, 1);
      int var13 = var7 + (var6 - var12 >> 1);
      int var14 = var10 - 16;
      int var15 = var2.b2(1) - 2;
      var2.a(var11, 1, var13, var14, 20);
      int var16 = -1;
      if (this.d5 || this.I / 750 % 2 == 1) {
         var16 = this.H;
      }

      if (var16 != -1) {
         int var17 = 5;
         int var18 = var13;
         if (var11 != null) {
            var18 = var13 + com.ea.sims3.SdkText.a(var11, 0, var16);
            if (var16 != var2.e()) {
               var17 = com.ea.sims3.SdkText.a(var11, var16, 1);
            }
         }

         int var19 = var14 + var15;
         com.ea.sims3.SpriteRenderer.a(var1, 0);
         var1.fillRect(var18, var19, var17, 1);
      }

      int var21 = var4 >> 1;
      var12 = var10 + (var5 - var10 - var8 >> 1);
      com.ea.sims3.SpriteRenderer.drawFrame(var1, 709, 0, var21, var12);
      int[] var26 = super.a2;
      com.ea.sims3.SpriteRenderer.a2(super.a2, 709, 0, 0);
      var14 = var21 + var26[0];
      var15 = var12 + var26[1];
      this.c(var1, var14, var15);
      short var22 = var3.a11(var3.a11);
      this.a2(var1, var7, var9, var6, var8, var22, 1);
      var12 = var7 + 3;
      var13 = var9 + 22;
      var14 = var6 - 6;
      var15 = super.i * 5 + 6;
      this.j(var1, var12, var13, var14, var15);
      var16 = var12 + (var14 >> 1);
      int var33 = var13 + 3 + 1;

      for (int var34 = 0; var34 < 5; var34++) {
         int var35 = var3.k(0, var34);
         short var20 = var3.f2(var35);
         var2.a2(var20, 1, var16, var33, 17);
         var33 += super.i;
      }

      int var23 = var2.getHeight() - 30;
      if (com.ea.sims3.GameCanvas.b) {
         var2.a2(957, 0, this.M >> 16, var23, 20);
      }

      var2.b(var1);
   }

   private void j2(int var1, int var2) {
      GameCanvas var3 = super.a;
      int var4;
      int var5 = var4 = this.e4 ? 2 : var3.j;
      int var6 = 2 + (a5 == 14 ? 1 : 0);
      if (this.a3(var1, var2, 0, 68)) {
         this.c4(a5 == 14 ? 7 : 14);
      } else if (this.a3(var1, var2, 4128, 48)) {
         if (this.e4) {
            this.c4(15);
         } else if (a5 == 15) {
            if (var3.e3()) {
               this.c4(16);
            }
         } else if (var3.e3()) {
            var3.s2();
            this.b2(2, -1);
         } else {
            this.h2();
         }
      } else if (this.a3(var1, var2, 8196, 0)) {
         var4 = (var4 + var6 - 1) % var6;
      } else if (this.a3(var1, var2, 16640, 0)) {
         var4 = (var4 + 1) % var6;
      }

      if (var4 != var5) {
         this.e4 = var4 == 2;
         if (!this.e4) {
            var3.l2(var4);
            if (var3.e3()) {
               this.a(false);
            }
         }

         boolean var7 = var3.e3();
         if (a5 == 15) {
            var3.a4(64, var7 ? 32 : 0);
         }
      }
   }

   private void n(Graphics var1) {
      GameCanvas var2 = super.a;
      int var3 = super.a.getWidth();
      int var4;
      int var5 = (var4 = var2.getHeight() - 15) - 6;
      int var6 = 3 + var5 - (a3(var5) >> 1);
      int var7 = var3 - 3 - 64 - 3 - 3;
      int var8 = a5 == 14 ? 77 : 43;
      com.ea.sims3.SpriteRenderer.a(var1, var8);
      var1.fillRect(0, 0, var3, var4);
      var8 = var1.getClipX();
      int var9 = var1.getClipY();
      int var10 = var1.getClipWidth();
      int var11 = var1.getClipHeight();
      var1.setClip(var8, 3, var10, var5);
      c2(var1, 3, 3, 64, var5);
      var1.setClip(var8, var9, var10, var11);
      com.ea.sims3.SpriteRenderer.a(var1, 0);
      var1.drawRect(3, 3, 63, var5 - 1);
      if (!this.e4 && var2.e3()) {
         this.a(var1, 35, var6, false);
      }

      var9 = a5 == 14 ? 64 : 65;
      var9 = this.d(var1, 70, var7, var9) + 3;
      var11 = var4 - 3 - 19;
      int var12 = (super.i << 2) + 8;
      int var13 = var11 - 3 - var12;
      int var16 = (var4 - var9 - 3 - 19 - 3 - var12 - 3 - 3) / 2;
      int var17 = var9;

      for (int var18 = 0; var18 < 2; var18++) {
         boolean var19 = !this.e4 && var18 == var2.j;
         SdkString var20 = var2.b8(var18) ? var2.b3(var18) : var2.a4(52);
         this.a(var1, 70, var17, var7, var16, var19, var20);
         var17 += var16 + 3;
      }

      this.k(var1, 70, var13, var7, var12);
      if (!this.e4 && var2.e3()) {
         Neighborhood var14 = var2.a23;
         var16 = 70 + var7 - 4;
         var17 = var13 + 4 + 1;
         short var32 = var14.a11(var14.a11);
         var2.a2(var32, 1, 74, var17, 20);
         var17 += super.i;
         int var33 = var14.p();
         var2.a2(521, 1, 74, var17, 20);
         var2.a2(var33, 1, var16, var17, 24);
         var17 += super.i;
         int var34 = var14.V(0);
         int var21 = var14.q(var34);
         var2.a2(646, 1, 74, var17, 20);
         var2.a2(var21, 1, var16, var17, 24);
         var17 += super.i;
         SdkString var22 = var2.a2();
         var2.g2(var14.e5);
         var2.a(var22, 1, 74, var17, 20);
      }

      if (a5 == 14) {
         this.a(var1, 70, var11, var7, 19, this.e4, 54);
      }

      var2.b(var1);
   }
}
