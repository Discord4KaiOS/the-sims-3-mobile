package com.ea.sims3;

import javax.microedition.lcdui.Graphics;

public class Entity implements SpriteIndex, SoundIndex, Resources {
   public static final int[] a = new int[10];
   public final GameScreen screen;
   public final GameCanvas canvas;
   public int type;
   public int b;
   private short a5;
   private int flags;
   private int state;
   private int h;
   private int i;
   public int posX;
   public int posY;
   public int facing;
   private byte a6;
   private byte b2;
   public boolean a7;
   private short b3;
   private int j;
   private boolean b4;
   private int k;
   private int l;
   private int m;
   private int n;
   private short c2;
   private Animator[] animators;
   private short d2;
   private int o;
   private int p;
   private Sim owner;
   private Sim partner;

   public final Neighborhood neighborhood() {
      return this.canvas.a23;
   }

   public final Lot lot() {
      return this.canvas.a24;
   }

   public Entity(GameScreen var1) {
      this.screen = var1;
      this.canvas = GameCanvas.a7();
      this.animators = new Animator[1];
      this.animators[0] = new Animator();
   }

   public void a2(int type, int x, int y, int facing, int value) {
      Lot lot = this.lot();
      Neighborhood var7 = this.neighborhood();
      this.moveTo(x, y);
      this.n2(facing);
      this.type = type;
      this.b = value;
      this.flags = lot.k(type);
      this.state = 0;
      this.a5 = (short)lot.i(type);
      this.o = 0;
      this.p = 0;
      this.owner = null;
      this.partner = null;
      this.addState(128);
      this.m = 0;
      int var8 = lot.q(type);
      int var9 = lot.h(type, 0);
      if (type == 99) {
         var8 = lot.c();
      }

      if (var8 == 196 && var9 != 0) {
         this.setValueB(var9);
      } else {
         this.c2 = -1;
         this.playIdleAnimation(var8);
      }

      this.setValueA(-1);
      int var10 = this.g();
      int var11 = 0;
      if (type == 9) {
         var11 = 1;
      } else if (type == 92) {
         if ((var11 = var10 & 16777215) != 0 && lot.a() != 0) {
            int var12 = var7.d6;
            int var13;
            if ((var13 = this.h()) != -1 && var12 - var13 > 1440) {
               var11 = 0;
            }
         }
      } else if (lot.h(type) == 16) {
         var11 = var10 & 16777215;
      }

      this.setModeFromState(var11);
      if ((var10 & 16777216) != 0) {
         this.f2();
      }

      if ((var10 & 33554432) != 0) {
         this.h2();
      }

      if (this.hasFlag(201326592) && !this.hasState(768)) {
         this.b3 = (short)(var10 & 16777215);
         this.j = 0;
         if (this.b3 == 0) {
            this.b3 = (short)this.canvas.b(0, 100);
         }
      }

      if (this.a() == 10) {
         byte var14 = 47;
         if (type == 48) {
            var14 = 49;
         } else if (type == 50) {
            var14 = 51;
         } else if (type == 52) {
            var14 = 53;
         }

         this.screen.a(var14, this.posX, this.posY, this.facing, this.b).setValueC(20);
      }

      if (this.a() == 8) {
         this.F();
      }
   }

   public final void a10() {
      if (this.a() == 8) {
         this.b3().a10();
      }

      this.screen.e(this);
      this.owner = null;
      this.partner = null;
   }

   public final int a() {
      return this.lot().h(this.type);
   }

   public final int b() {
      return this.hasFlag(64) ? this.i() : this.a5;
   }

   public final void moveTo(int var1, int var2) {
      this.b4 = true;
      this.posX = var1;
      this.posY = var2;
   }

   public final boolean hasFlag(int var1) {
      return (this.flags & var1) != 0;
   }

   public final void addFlag(int var1) {
      this.flags |= var1;
   }

   public final boolean hasState(int var1) {
      return (this.state & var1) != 0;
   }

   public final void a2(int var1, boolean var2) {
      if (var2) {
         this.addState(var1);
      } else {
         this.clearState(var1);
      }
   }

   public final void addState(int var1) {
      this.state |= var1;
   }

   public final void clearState(int var1) {
      this.state &= ~var1;
   }

   public final void setBusy() {
      this.addState(8192);
      if (this.a() == 8) {
         this.b3().addState(8192);
      }
   }

   public final void clearBusy() {
      this.clearState(8192);
      if (this.a() == 8) {
         this.b3().clearState(8192);
      }
   }

   public final void a4(int var1, int var2, int var3) {
      Entity var4 = null;
      if (this.a() == 8) {
         var4 = this.b3();
      }

      this.moveTo(var1, var2);
      this.n2(var3);
      if (var4 != null) {
         int[] var5 = a;
         this.c(var5);
         var4.a4(var5[0], var5[1], var5[2]);
      }
   }

   public final void update() {
      if (this.b4) {
         this.b4 = false;
         int var2 = this.lot().d(this.posX, this.posY);
         this.k = var2 + this.o;
         this.l = var2 + this.p;
      }
   }

   public int c() {
      return this.hasFlag(16) ? this.b() : 24;
   }

   public final void a(int[] var1) {
      Lot var2 = this.lot();
      int var3 = 0;
      int var4 = 0;
      Animator var5;
      int var6 = (var5 = this.animatorAt(0)).animId;
      int var7 = var5.frame;
      if (this.a() == 16) {
         var6 = 270;
         var7 = 0;
      }

      if (var6 != -1) {
         if (SpriteRenderer.hitTest(var1, var6, var7, 0)) {
            var3 = var1[0];
            var4 = var1[1];
         } else if (this.hasFlag(16384)) {
            var4 = 0 - (this.screen.b5() ? 7 : 38);
         }
      }

      var1[0] = var2.a2(this.posX, this.posY) + (var3 << 16);
      var1[1] = var2.b(this.posX, this.posY) + (var4 << 16);
   }

   public final void a(int[] var1, int var2) {
      Lot var3 = this.lot();
      int var4 = com.ea.sims3.Lot.f(this.posX);
      int var5 = com.ea.sims3.Lot.g(this.posY);
      int var6 = var3.b(this.type, var2, this.facing);
      int var7 = var3.c(this.type, var2, this.facing);
      int var8 = var4 + var6;
      int var9 = var5 + var7;
      var1[0] = com.ea.sims3.Lot.d(var8);
      var1[1] = com.ea.sims3.Lot.e(var9);
   }

   public final int a(int var1, int var2, boolean var3, Sim var4) {
      Lot var5 = this.lot();
      int[] var6 = a;
      int var7;
      if ((var7 = var5.r(this.type)) <= 0) {
         return -1;
      } else {
         int var8 = -1;
         int var9 = Integer.MAX_VALUE;

         for (int var10 = 0; var10 < var7; var10++) {
            if (!var3 || this.d4(var10)) {
               this.a(var6, var10);
               int var11 = var6[0];
               int var12 = var6[1];
               if (var5.b3(var11, var12)) {
                  if (var3 && !this.hasFlag(4194304)) {
                     int var13 = com.ea.sims3.Lot.f(var11);
                     int var14 = com.ea.sims3.Lot.g(var12);
                     boolean var15 = false;
                     Sim[] var16 = this.screen.a13();

                     for (int var17 = 0; var17 < var16.length && !var15; var17++) {
                        Sim var18;
                        if ((var18 = var16[var17]) != var4) {
                           int var19 = com.ea.sims3.Lot.f(var18.posX);
                           int var20 = com.ea.sims3.Lot.g(var18.posY);
                           if (var19 == var13 && var20 == var14) {
                              var15 = true;
                           }
                        }
                     }

                     if (var15) {
                        continue;
                     }
                  }

                  int var21 = Math.abs(var1 - var11);
                  int var22 = Math.abs(var2 - var12);
                  int var23;
                  if ((var23 = var21 + var22) < var9) {
                     var9 = var23;
                     var8 = var10;
                  }
               }
            }
         }

         return var8;
      }
   }

   public final boolean c5(int var1) {
      if (this.hasFlag(4194304)) {
         return true;
      } else {
         if (this.hasFlag(8388608)) {
            if ((this.m & 1 << var1) == 0) {
               this.m |= 1 << var1;
               return true;
            }
         } else if (this.m == 0) {
            this.m = -1;
            return true;
         }

         return false;
      }
   }

   public final void d3(int var1) {
      if (this.hasFlag(8388608)) {
         this.m &= ~(1 << var1);
      } else {
         this.m = 0;
      }
   }

   public final boolean a11() {
      return this.hasFlag(4194304) ? true : this.m == 0;
   }

   public final boolean b5() {
      if (this.a11()) {
         return true;
      } else {
         int var1 = this.lot().r(this.type);
         return this.m < (1 << var1) - 1;
      }
   }

   public final boolean d4(int var1) {
      return (this.m & 1 << var1) == 0;
   }

   private int g() {
      if (this.b == -1) {
         return 0;
      } else {
         Lot var1 = this.lot();
         return this.neighborhood().l(var1.a(), this.b);
      }
   }

   private int h() {
      if (this.b == -1) {
         return -1;
      } else {
         Lot var1 = this.lot();
         return this.neighborhood().m(var1.a(), this.b);
      }
   }

   private void E() {
      if (this.b != -1) {
         Lot var1 = this.lot();
         Neighborhood var2 = this.neighborhood();
         int var3 = 0;
         if (this.type == 92) {
            var3 = this.h;
         } else if (this.a() == 16) {
            var3 = this.h;
         }

         if (this.hasFlag(67108864) && this.hasState(256)) {
            var3 |= 16777216;
         }

         if (this.hasFlag(134217728) && this.hasState(512)) {
            var3 |= 33554432;
         }

         if (var1.a() == 0 && this.hasFlag(201326592)) {
            var3 |= this.b3;
         }

         if (this.hasState(32768)) {
            var3 |= 134217728;
         }

         if (this.hasState(16384)) {
            var3 |= 67108864;
         }

         if (this.hasState(65536)) {
            var3 |= 268435456;
         }

         var2.b2(var1.a(), this.b, var3);
      }
   }

   public boolean c3() {
      return this.a() == 16 ? !this.isAnimating() && !this.e4(1) && this.h != 1 : this.h == 0;
   }

   public final boolean d3() {
      return this.a() != 16 || !this.isAnimating() && !this.e4(1) ? this.h == 1 : false;
   }

   public final void setModeFromState(int var1) {
      int var2;
      if ((var2 = this.a()) == 2) {
         this.r2(var1);
      } else if (var2 == 3) {
         this.t2(var1);
      } else if (var2 == 7) {
         this.u2(var1);
      } else if (var2 == 8) {
         this.setMode(var1);
      } else if (var2 == 10) {
         this.v2(var1);
      } else if (var2 == 17) {
         this.z2(var1);
      } else if (var2 == 19) {
         this.A2(var1);
      } else if (var2 == 16) {
         this.x2(var1);
      } else if (this.type == 92) {
         this.B2(var1);
      }

      this.h = var1;
      this.i = 0;
      if (this.type == 92) {
         this.E();
      }
   }

   public void advanceTime(int var1) {
      this.i += var1;
      if (this.hasFlag(201326592) && !this.hasState(768)) {
         this.j += var1;
         if (this.j > 4000) {
            this.j -= 4000;
            this.h2(1);
         }
      }

      if (this.hasState(1)) {
         var1 = this.screen.b5;
      }

      int var2 = this.type;
      int var3;
      if ((var3 = this.a()) == 3) {
         this.s2(var1);
      } else if (var3 == 16) {
         this.y2(var1);
      } else {
         this.g2(var1);
         if (!this.isAnimating()) {
            if (var3 == 7) {
               this.setModeFromState(2);
            } else if (this.type == 92 && this.h == 2) {
               this.setModeFromState(1);
            }
         }

         if (var3 == 8) {
            this.a5(var1, 2);
            if (!this.e4(2)) {
               if (this.h == 2) {
                  this.setModeFromState(1);
               } else {
                  this.setModeFromState(0);
               }
            }
         }
      }

      if (var2 == 7) {
         if (!this.isAnimating()) {
            this.a10();
            return;
         }
      } else if (var2 == 8 && this.i > 1500) {
         this.a10();
      }
   }

   public final void g2(int var1) {
      if (this.hasState(2048)) {
         var1 = FixedMath.mul(var1 << 16, 81920) >> 16;
      } else if (this.hasState(4096)) {
         var1 = FixedMath.mul(var1 << 16, 49152) >> 16;
      }

      boolean var2 = this.a5(var1, 0);
      if (this.a5(var1, 1)) {
         var2 = true;
      }

      if (this.d2 != -1 && var2) {
         this.draw();
      }
   }

   public final boolean a5(int var1, int var2) {
      Animator var3;
      return (var3 = this.animatorAt(var2)) != null ? var3.advance(var1) : false;
   }

   public final void draw() {
      int[] var1 = a;
      Animator var2 = this.animatorAt(0);
      Animator var3 = this.animatorAt(1);
      byte var4 = 1;
      Animator var5 = var2;
      int var6 = 0;
      int var7 = 0;
      if (var3 != null && var3.isRunning()) {
         var5 = var3;
         var4 = 0;
         if (var2.matchesFrame(var1, 1)) {
            var6 = var1[0];
            var7 = var1[1];
         } else if (this.hasState(1024)) {
            var7 = -30;
         }
      }

      int var8;
      if ((var8 = var5.frameDuration()) > var4) {
         Lot var9 = this.lot();
         if (this.a() == 16) {
            int var16 = var5.frame;
            int var18 = this.j();
            int var20 = 2 + var18 - var16;
            this.animatorFor(var20).play(this.d2, 16);
            return;
         }

         for (int var10 = var4; var10 < var8; var10++) {
            int var11 = this.posX;
            int var12 = this.posY;
            int var13 = var9.a2(var11, var12);
            int var14 = var9.b(var11, var12);
            var5.matchesFrame(var1, var10);
            var13 += var1[0] + var6 << 16;
            var14 += var1[1] + var7 << 16;
            var11 = var9.e(var13, var14);
            var12 = var9.f(var13, var14);
            int var15 = -(var1[1] + var7);
            if (this.hasFlag(524288)) {
               var15 += 5;
            }

            this.screen.a2(var11, var12, this.d2, var15);
         }
      }
   }

   public boolean a(int var1, Sim var2) {
      if (this.type == 92) {
         if (var1 == 148) {
            this.setModeFromState(0);
         }
      } else if (this.type == 9) {
         if (var1 == 149) {
            if (this.b != -1) {
               this.lot().a12(this.b);
            }

            this.a10();
         }
      } else if (this.a() == 10) {
         if (var1 == 150 || var1 == 153) {
            this.setModeFromState(1);
            if (var2.b == 0 && !this.neighborhood().b3(0, 1)) {
               this.screen.a(23, var2, this);
            }
         } else if (var1 == 151) {
            if (!this.screen.a5(0, this.posX, this.posY)) {
               this.setModeFromState(0);
            }
         } else if (var1 == 152 && var2.b == 0) {
            this.screen.a(24, var2, this);
         }
      } else if (this.a() == 22) {
         if (var1 == 131) {
            this.clearState(16384);
            this.addState(32768);
            this.E();
         } else if (var1 == 130) {
            this.addState(16384);
            this.clearState(32768);
            this.E();
         }
      } else if (this.a() == 16 && var1 == 112) {
         this.addState(65536);
         this.E();
      }

      return false;
   }

   public final void f2() {
      this.addState(256);
      switch (this.a()) {
         case 15:
         case 22:
            this.k2();
         default:
            this.E();
            this.playAnimation(28, 20);
            this.screen.a2((byte)8);
      }
   }

   public final void g2() {
      this.clearState(256);
      this.E();
      this.C();
   }

   public final void h2() {
      this.addState(512);
      this.E();
      this.playAnimation(41, 20);
      this.screen.a2((byte)9);
   }

   public final void i2() {
      this.clearState(512);
      this.E();
      this.C();
   }

   public final void h2(int var1) {
      if (this.lot().a() == 0) {
         this.b3 = (short)(this.b3 + var1);
         if (this.b3 > 300) {
            this.b3 = 0;
            if (this.hasFlag(67108864)) {
               this.f2();
            } else {
               this.h2();
            }
         }

         this.E();
      }
   }

   public final void j2() {
      int var1;
      if ((var1 = this.a()) == 15 || var1 == 22 || var1 == 18) {
         this.q2(1);
      }

      if (var1 == 22 && !this.hasState(49152)) {
         this.addState(32768);
      } else if (var1 == 8) {
         this.setModeFromState(2);
      }

      this.addState(64);
      this.h2(15);
   }

   public final void k2() {
      int var1;
      if ((var1 = this.a()) == 15 || var1 == 22 || var1 == 18) {
         this.q2(0);
      } else if (var1 == 8) {
         this.setModeFromState(3);
      }

      this.clearState(64);
      this.h2(15);
   }

   public final void b2(int var1, int var2) {
      this.clearState(128);
      this.playIdleAnimation(var1);
      if (var1 == 35 || var1 == 36) {
         this.addState(1);
      }

      this.setValueC(var2);
   }

   public final void i2(int var1) {
      this.n = var1;
      this.playIdleAnimation(196);
      this.setValueC(30);
   }

   private void e2(int var1, int var2) {
      GameCanvas var3 = this.canvas;
      int var4 = FixedMath.smoothStep(0, 1500, this.i);
      int var5 = -40 + FixedMath.mul(var4, -30);
      SdkString var6 = var3.a2();
      var3.g2(this.n);
      var3.a(var6, 0, var1, var2 + var5, 17);
   }

   public final void l2() {
      this.setModeFromState(1);
   }

   public final void m2() {
      this.setModeFromState(0);
   }

   private void r2(int var1) {
      byte var2;
      byte var3;
      switch (var1) {
         case 0:
         default:
            var3 = -1;
            var2 = 0;
            break;
         case 1:
            var3 = 44;
            var2 = 1;
      }

      this.setValueA(var3);
      this.q2(var2);
   }

   public final void n2() {
      if (this.h == 0 || this.h == 3) {
         this.setModeFromState(2);
      }
   }

   public final void o2() {
      if (this.h == 1 || this.h == 2) {
         this.setModeFromState(3);
      }
   }

   public final boolean isModeOne() {
      return this.h == 1;
   }

   public final void b(int[] var1) {
      Lot var2;
      int var3 = (var2 = this.lot()).c(this.posX, this.posY);
      int var4 = var2.d(this.posX, this.posY);
      this.animatorAt(2).matchesFrame(var1, 0);
      var1[0] += var3;
      var1[1] += var4;
   }

   private void s2(int var1) {
      this.g2(var1);
      this.a5(var1, 2);
      if (!this.e4(2)) {
         if (this.h == 2) {
            this.setModeFromState(1);
            return;
         }

         if (this.h == 3) {
            this.setModeFromState(0);
         }
      }
   }

   private void t2(int var1) {
      short var2 = -1;
      boolean var3 = true;
      switch (var1) {
         case 0:
         default:
            break;
         case 1:
            var2 = 158;
            break;
         case 2:
            var2 = 157;
            var3 = false;
            break;
         case 3:
            var2 = 159;
            var3 = false;
      }

      Animator var4 = this.animatorFor(2);
      if (var2 != -1) {
         int var5 = this.lot().i(var2, this.facing);
         var4.play(var5, var3 ? 20 : 16);
      } else {
         var4.setRunning(false);
      }
   }

   public final int d() {
      Entity[] var1 = this.screen.a12();
      Entity var2 = null;

      for (int var3 = 0; var3 < var1.length; var3++) {
         Entity var4;
         if ((var4 = var1[var3]) != this && var4.a() == 4) {
            var2 = var4;
            break;
         }
      }

      if (var2 != null) {
         return this.b < var2.b ? 512 : 1024;
      } else {
         return 512;
      }
   }

   public final boolean f3() {
      Lot var1 = this.lot();
      int var2 = com.ea.sims3.Lot.f(this.posX);
      int var3 = com.ea.sims3.Lot.g(this.posY);
      return !var1.a3(var2, var3, 1, 1, this.facing) && this.type != 41;
   }

   public final Entity a3() {
      Entity[] var1 = this.screen.a12();

      for (int var2 = 0; var2 < var1.length; var2++) {
         Entity var3;
         if ((var3 = var1[var2]).a() == 8 && var3.b3() == this) {
            return var3;
         }
      }

      return null;
   }

   public final void p2() {
      this.setModeFromState(1);
   }

   public final void q2() {
      this.setModeFromState(0);
   }

   private void u2(int var1) {
      byte var2 = 0;
      boolean var3 = true;
      byte var4 = -1;
      if (var1 == 1) {
         var2 = 1;
         var3 = false;
         var4 = 42;
      }

      this.d2 = var4;
      this.a2(128, var3);
      this.q2(var2);
   }

   public final boolean isModeTwo() {
      return this.h == 2;
   }

   private void F() {
      int[] var2 = a;
      this.c(var2);
      this.screen.a(41, var2[0], var2[1], var2[2], -1);
   }

   private void c(int[] var1) {
      int var2 = this.posX;
      int var3 = this.posY;
      byte var4 = 3;
      if (this.facing == 0) {
         var2 += 524288;
         var3 -= 524288;
         var4 = 2;
      } else {
         var2 -= 524288;
         var3 += 524288;
      }

      var1[0] = var2;
      var1[1] = var3;
      var1[2] = var4;
   }

   public final Entity b3() {
      int[] var1 = a;
      this.c(var1);
      return this.screen.a2(41, var1[0], var1[1]);
   }

   public final void setMode(int var1) {
      this.h = var1;
      byte var2 = -1;
      byte var3 = 20;
      switch (var1) {
         case 0:
         default:
            break;
         case 1:
            var2 = 1;
            break;
         case 2:
            var2 = 2;
            var3 = 16;
            break;
         case 3:
            var2 = 3;
            var3 = 16;
      }

      if (var2 != -1) {
         Lot var4;
         int var5 = (var4 = this.lot()).h(this.type, var2);
         int var6 = var4.i(var5, this.facing);
         this.animatorFor(2).play(var6, var3);
      } else {
         this.animatorFor(2).setRunning(false);
      }
   }

   private void v2(int var1) {
      this.h = var1;
      byte var2 = -1;
      byte var3 = 0;
      switch (var1) {
         case 0:
            var2 = 0;
            var3 = 0;
            this.clearState(64);
            break;
         case 1:
            var2 = 1;
            var3 = -15;
            this.addState(64);
      }

      this.q2(var2);
      this.a(!this.hasState(64));
      this.setValueC(var3);
   }

   public final void r2() {
      this.q2(1);
   }

   public final void s2() {
      this.q2(0);
      this.h2(15);
   }

   private final int i() {
      int var1 = -1;
      int var2 = -1;
      int var3 = this.lot().b2(this.type);
      Neighborhood var4;
      int var5 = (var4 = this.neighborhood()).b6;

      for (int var6 = 0; var6 < var5; var6++) {
         if (var4.U(var6) == var3) {
            if (var1 == -1) {
               var1 = var6;
            } else if (var2 == -1) {
               var2 = var6;
            }
         }
      }

      if (var1 == -1 && var2 == -1) {
         return 201;
      } else {
         GameCanvas var9 = this.canvas;
         SdkString var7;
         if (var1 == 0) {
            var7 = var9.b2();
         } else {
            var7 = var9.a4(var4.R(var1));
         }

         if (var2 == -1) {
            var9.a2(-10, 202, var7);
         } else {
            SdkString var8 = var9.a4(var4.R(var2));
            var9.a(-10, 203, var7, var8);
         }

         return -10;
      }
   }

   private static final int a3(int var0) {
      switch (var0) {
         case 5:
            return 18;
         case 6:
            return 21;
         case 7:
            return 19;
         case 8:
            return 22;
         case 9:
            return 20;
         default:
            return -1;
      }
   }

   private static final int a2(int var0, int var1) {
      switch (var0) {
         case 18:
            return 264;
         case 19:
            return 265;
         case 20:
            return 266;
         case 21:
            if (var1 == 0) {
               return 262;
            }

            return 263;
         case 22:
            if (var1 == 0) {
               return 268;
            }

            return 269;
         default:
            return -1;
      }
   }

   private static final int b2(int var0) {
      switch (var0) {
         case 18:
            return 11;
         case 19:
            return 13;
         case 20:
            return 19;
         case 21:
            return 12;
         case 22:
            return 15;
         default:
            return -1;
      }
   }

   private int j() {
      return this.l3() ? 5 : 8;
   }

   private boolean l3() {
      return (this.lot().k(this.type) & 65536) != 0;
   }

   public final void k2(int var1) {
      Lot var2 = this.lot();
      int var3 = a3(var1);
      this.type = var3;
      var2.b2(this.b, var3);
      this.a5 = (short)var2.i(var3);
      this.h = 2;
      this.E();
      this.clearState(128);
      if (this.l3()) {
         this.playIdleAnimation(271);
         this.playAnimation(267, 16);
         this.setValueA(a2(var3, 0));
      } else {
         this.playIdleAnimation(272);
      }
   }

   public final void t2() {
      boolean var1 = false;
      switch (this.h) {
         case 2:
         default:
            if (this.l3()) {
               var1 = true;
               this.h = 1;
               this.clearState(128);
               this.playIdleAnimation(271);
               this.playAnimation(267, 16);
               this.setValueA(a2(this.type, 1));
            } else {
               this.h = 3;
               this.clearState(128);
               this.playIdleAnimation(273);
            }
            break;
         case 3:
            var1 = true;
            this.h = 1;
            this.clearState(128);
            this.playIdleAnimation(274);
            this.playAnimation(275, 16);
            this.setValueA(a2(this.type, 0));
      }

      if (var1) {
         Neighborhood var2;
         (var2 = this.neighborhood()).e3(32);
         if (this.type == 21) {
            var2.e3(33);
         } else if (this.type == 19) {
            var2.e3(34);
         } else if (this.type == 20) {
            var2.e3(35);
         }
      }

      this.E();
   }

   private void G() {
      this.playAnimation(196, 16);

      for (int var1 = 2; var1 <= 9; var1++) {
         Animator var2;
         if ((var2 = this.animatorAt(var1)) != null) {
            var2.play(196, 16);
         }
      }
   }

   private void w2(int var1) {
      int var2 = a2(this.type, var1);
      int var3 = this.j();
      int var4 = 2 + var3 - 1;

      for (int var5 = 0; var5 < var3; var5++) {
         Animator var6;
         (var6 = this.animatorFor(var4 - var5)).play(var2, 16);
         var6.rewind();
      }
   }

   public final void u2() {
      int var1 = b2(this.type);
      int var2 = this.j();
      if (this.hasState(65536)) {
         var2 += 2;
         this.clearState(65536);
      }

      this.neighborhood().h2(var2);
      this.neighborhood().b2(var1, var2);
      this.screen.b2(var1, var2, 289, 715);
      if (this.l3()) {
         this.v2();
      } else {
         this.setModeFromState(3);
      }

      this.G();
      this.E();
   }

   public final void v2() {
      this.type = 17;
      this.lot().b2(this.b, 17);
      this.a5 = (short)this.lot().i(17);
      this.h = 0;
      this.G();
      this.clearState(128);
      this.playIdleAnimation(270);
      this.E();
   }

   private void x2(int var1) {
      this.h = var1;
      short var2;
      switch (var1) {
         case 0:
         default:
            var2 = 270;
            break;
         case 1:
            this.w2(1);
            short var5 = 275;
            var2 = 274;
            if (this.l3()) {
               var5 = 267;
               var2 = 271;
            }

            Animator var4;
            (var4 = this.animatorFor(1)).play(var5, 16);
            var4.rewind();
            break;
         case 2:
            if (this.l3()) {
               this.w2(0);
               var2 = 271;
               Animator var3;
               (var3 = this.animatorFor(1)).play(267, 16);
               var3.rewind();
            } else {
               var2 = 272;
            }
            break;
         case 3:
            var2 = 273;
      }

      this.clearState(128);
      this.playIdleAnimation(var2);
      this.animatorAt(0).rewind();
   }

   private void y2(int var1) {
      int var2 = this.j();

      for (int var3 = 0; var3 < var2; var3++) {
         this.a5(var1, 2 + var3);
      }

      this.g2(var1);
   }

   public final void w() {
      this.q2(1);
   }

   public final void x() {
      this.q2(0);
   }

   public final void y() {
      this.setModeFromState(1);
   }

   public final void z() {
      this.h2(15);
      this.setModeFromState(0);
   }

   private void z2(int var1) {
      this.h = var1;
      byte var2;
      byte var3;
      switch (var1) {
         case 0:
         default:
            var3 = -1;
            var2 = 0;
            break;
         case 1:
            var3 = 42;
            var2 = 1;
      }

      this.setValueA(var3);
      this.q2(var2);
   }

   public final void A() {
      this.setModeFromState(1);
   }

   public final void B() {
      this.setModeFromState(0);
   }

   private void A2(int var1) {
      this.h = var1;
      byte var2;
      byte var3;
      switch (var1) {
         case 0:
         default:
            var3 = -1;
            var2 = 0;
            break;
         case 1:
            var3 = 42;
            var2 = 1;
      }

      this.setValueA(var3);
      this.q2(var2);
   }

   public final void a(Sim var1) {
      this.screen.a(21, var1, this);
      this.a2(var1);
      this.n2((this.facing + 2) % 4);
      this.setModeFromState(2);
   }

   private void B2(int var1) {
      boolean var2 = true;
      byte var3;
      switch (var1) {
         case 0:
         default:
            this.C();
            var3 = 0;
            break;
         case 1:
            this.playAnimation(41, 4);
            var3 = 2;
            break;
         case 2:
            this.playAnimation(41, 20);
            var3 = 1;
            var2 = false;
      }

      this.a2(128, var2);
      this.q2(var3);
   }

   public final Animator animatorAt(int var1) {
      return var1 < this.animators.length ? this.animators[var1] : null;
   }

   public final Animator animatorFor(int var1) {
      if (var1 >= this.animators.length) {
         Animator[] var2 = new Animator[var1 + 1];
         System.arraycopy(this.animators, 0, var2, 0, this.animators.length);
         this.animators = var2;
      }

      if (this.animators[var1] == null) {
         Animator var3 = new Animator();
         this.animators[var1] = var3;
         var3.setRunning(false);
      }

      return this.animators[var1];
   }

   public final void playAnimation(int var1, int var2) {
      this.animatorFor(1).play(var1, var2);
   }

   public final void C() {
      Animator var1;
      if ((var1 = this.animatorAt(1)) != null) {
         var1.setRunning(false);
      }
   }

   public final void setValueA(int var1) {
      this.d2 = (short)var1;
   }

   public final int e() {
      this.update();
      return this.k;
   }

   public final int f() {
      this.update();
      return this.l;
   }

   public final void setValueC(int var1) {
      this.o = var1;
      this.p = var1;
      this.b4 = true;
   }

   public final void b(Sim var1) {
      if (this.owner == null) {
         this.owner = var1;
      } else {
         if (this.partner == null) {
            this.partner = var1;
         }
      }
   }

   public final void c(Sim var1) {
      if (this.owner == var1) {
         this.owner = null;
      }

      if (this.partner == var1) {
         this.partner = null;
      }
   }

   public void a(Graphics var1) {
      Lot var2 = this.lot();
      int[] var3 = a;
      int var4 = var2.d();
      int var5 = var2.e();
      int var6 = var4 + var2.c(this.posX, this.posY);
      int var7 = var5 + var2.d(this.posX, this.posY);
      if (this.type == 8) {
         this.e2(var6, var7);
      } else {
         Animator var8;
         (var8 = this.animatorAt(0)).draw(var1, var6, var7);
         int var9;
         Animator var10;
         if ((var9 = this.a()) == 8 && (var10 = this.animatorAt(2)) != null && var10.isRunning()) {
            var10.draw(var1, var6, var7);
         }

         int var20 = var6;
         int var11 = var7;
         Animator var12;
         if ((var12 = this.animatorAt(1)) != null && var12.isRunning()) {
            if (var8.matchesFrame(var3, 1)) {
               var20 = var6 + var3[0];
               var11 = var7 + var3[1];
            } else if (var8.matchesFrame(var3, 0)) {
               var20 = var6 + var3[0];
               var11 = var7 + var3[1];
            }

            var12.draw(var1, var20, var11);
         }

         if (var9 == 16 && var12 != null) {
            int var13 = var12.animId;
            if (var12.animId != 196) {
               int var14;
               for (int var15 = (var14 = this.j()) - 1; var15 >= 0; var15--) {
                  Animator var16;
                  if ((var16 = this.animatorAt(2 + var15)) != null) {
                     int var17 = var14 - var15;
                     SpriteRenderer.hitTest(var3, var13, var17, 0);
                     int var18 = var20 + var3[0];
                     int var19 = var11 + var3[1];
                     var16.draw(var1, var18, var19);
                  }
               }
            }
         }

         Animator var21;
         if (var9 == 3 && (var21 = this.animatorAt(2)).isRunning()) {
            var21.draw(var1, var6, var7);
         }

         if (this.hasFlag(33554432)) {
            if (this.owner != null) {
               this.owner.b(var1);
            }

            if (this.partner != null) {
               this.partner.b(var1);
            }

            int var22 = var2.h(this.type, 1);
            int var23 = var2.i(var22, this.facing);
            SpriteRenderer.drawFrame(var1, var23, 0, var6, var7);
         }
      }
   }

   public final void a2(short[] var1) {
      GameCanvas.b2(var1, this.a5);
      if (!this.m3()) {
         if (this.k3()) {
            GameCanvas.c2(var1, 326);
         }

         if (this.j3()) {
            GameCanvas.c2(var1, 325);
         }

         if (this.hasFurnitureSlots()) {
            GameCanvas.c2(var1, 324);
            return;
         }
      } else {
         GameCanvas.c2(var1, 275);
      }
   }

   public final void a(boolean var1) {
      if (this.hasFlag(524288)) {
         this.setValueC(5);
      } else if (this.hasFlag(4)) {
         if (this.type == 11) {
            this.setValueC(5);
         }
      } else {
         Lot var2 = this.lot();
         int var3 = com.ea.sims3.Lot.f(this.posX);
         int var4 = com.ea.sims3.Lot.g(this.posY);
         int var5 = var2.o(this.type);
         int var6 = var2.p(this.type);
         if (this.facing == 1 || this.facing == 3) {
            int var7 = var5;
            var5 = var6;
            var6 = var7;
         }

         short var8;
         short var9;
         if (var1) {
            if (this.a() == 10) {
               var9 = 255;
               var8 = 32;
            } else if (this.type == 116) {
               var9 = -129;
               var8 = 0;
            } else {
               var9 = 255;
               var8 = 128;
            }
         } else {
            var9 = -161;
            var8 = 0;
         }

         var2.a(var3 - var5 + 1, var4 - var6 + 1, var5, var6, var9, var8);
         this.a6 = (byte)var5;
         this.b2 = (byte)var6;
         if (this.hasFlag(16777216)) {
            this.setValueC(-10);
         } else if (this.hasFlag(8)) {
            this.setValueC(0);
         } else if (this.a() == 14 || this.a() == 12) {
            this.setValueC(20);
         } else if (var5 != 0 && var6 != 0) {
            this.o = var5 * -8 + 4;
            this.p = var6 * -8 + 4;
         }

         this.b4 = true;
      }
   }

   public final void n2(int var1) {
      if (this.facing != var1) {
         this.b4 = true;
         this.facing = var1;
         if (this.c2 != -1) {
            this.setValueB(this.c2);
         }
      }
   }

   public final void d2(int var1, int var2) {
      this.n2(Math.abs(var1) > Math.abs(var2) ? (var1 < 0 ? 2 : 0) : (var2 < 0 ? 3 : 1));
   }

   public final void a2(Entity var1) {
      int var2 = Math.max(var1.a6 - 1, 0);
      int var3 = Math.max(var1.b2 - 1, 0);
      int var4 = var2 << 18;
      int var5 = var3 << 18;
      int var6 = var1.posX - var4;
      int var7 = var1.posY - var5;
      this.d2(var6 - this.posX, var7 - this.posY);
   }

   public final void playIdleAnimation(int var1) {
      this.animatorAt(0).play(var1, this.hasState(128) ? 20 : 16);
   }

   public final boolean isAnimating() {
      return this.animators[0].isRunning();
   }

   public final boolean e4(int var1) {
      Animator var2;
      return (var2 = this.animatorAt(var1)) != null && var2.isRunning();
   }

   public final void setValueB(int var1) {
      this.c2 = (short)var1;
      int var2 = this.lot().i(var1, this.facing);
      this.playIdleAnimation(var2);
   }

   public final void q2(int var1) {
      this.setValueB(this.lot().h(this.type, var1));
   }

   private boolean m3() {
      if (this.m != 0) {
         return true;
      } else {
         if (this.hasFlag(262144)) {
            Lot var1 = this.lot();
            int var2 = this.type;
            int var3 = var1.o(var2);
            int var4 = var1.p(var2);
            if (this.facing == 1 || this.facing == 3) {
               int var5 = var3;
               var3 = var4;
               var4 = var5;
            }

            int var11 = com.ea.sims3.Lot.f(this.posX);
            int var6 = com.ea.sims3.Lot.g(this.posY);

            for (int var7 = 0; var7 < var3; var7++) {
               for (int var8 = 0; var8 < var4; var8++) {
                  if (var1.a5(var11 - var7, var6 - var8)) {
                     return true;
                  }
               }
            }
         }

         Sim[] var9 = this.screen.a13();

         for (int var10 = 0; var10 < var9.length; var10++) {
            if (var9[var10].a10 == this) {
               return true;
            }
         }

         return false;
      }
   }

   public final boolean hasFurnitureSlots() {
      return this.lot().n(this.type) > 0;
   }

   public final boolean j3() {
      Lot var1 = this.lot();
      this.a(false);
      int var2 = var1.c(this.type);
      this.screen.h2();
      this.a(true);
      return var2 > 1;
   }

   public final boolean k3() {
      return this.k() != this.facing;
   }

   private int k() {
      Lot var1 = this.lot();
      boolean var2;
      if (!(var2 = this.hasFlag(524288))) {
         this.a(false);
      }

      int var3 = this.facing;
      int var4 = com.ea.sims3.Lot.f(this.posX);
      int var5 = com.ea.sims3.Lot.g(this.posY);
      int var6 = var1.a(this.type, var4, var5);

      for (int var7 = 1; var7 <= 4; var7++) {
         int var8 = (var3 + var7) % 4;
         int var9 = com.ea.sims3.Lot.a7[var8];
         if ((var6 & var9) != 0) {
            var3 = var8;
            break;
         }
      }

      if (!var2) {
         this.a(true);
      }

      return var3;
   }

   public final void D() {
      int var1 = this.k();
      this.a(false);
      this.a4(this.posX, this.posY, var1);
      this.a(true);
   }
}
