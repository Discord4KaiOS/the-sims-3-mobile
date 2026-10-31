package com.ea.sims3;

import javax.microedition.lcdui.Graphics;

public final class Sim extends Entity {
   private int j2;
   private int k2;
   public int f2;
   private int l2;
   private int m2;
   private int n2;
   private int o2;
   public Entity a10;
   public int g2;
   private int p2;
   public int h2;
   public Entity b6;
   private int q;
   private int r;
   private int s;
   private Entity c3;
   private int t;
   private int u;
   private int v;
   private int w;
   private Entity d3;
   private int x;
   private int y;
   private int z;
   private int[] c4;
   private int A;
   private boolean b7;
   private int B;
   private int C;
   private int D;
   private int E;
   private int F;
   private int G;
   private boolean c5;
   private boolean d4;
   public int i2;
   private int H;
   private boolean e2;
   private int I;
   private int J = -551;
   private static final int[] d5 = new int[]{4, 2, 8, 16, 64};

   public Sim(GameScreen var1) {
      super(var1);
   }

   public final void a2(int var1, int var2, int var3, int var4, int var5) {
      super.a2(var1, var2, var3, var4, var5);
      this.l2 = 0;
      this.m2 = 0;
      this.n2 = 0;
      this.o2 = 0;
      this.a10 = null;
      this.g2 = 0;
      this.p2 = 0;
      this.h2 = 0;
      this.b6 = null;
      this.q = 0;
      this.r = 0;
      this.s = -1;
      this.c3 = null;
      this.t = 0;
      this.u = 0;
      this.v = 0;
      this.w = 0;
      this.d3 = null;
      this.x = -1;
      this.y = super.c;
      this.z = super.d;
      this.c4 = null;
      this.A = 0;
      this.b7 = false;
      this.B = 0;
      this.C = 0;
      this.D = -1;
      this.E = 0;
      this.F = -1;
      this.G = -1;
      this.c5 = true;
      this.i2 = 0;
      this.e2 = false;
      this.I = -1;
      this.b5(2);
      this.b5(1);
      this.j2 = -1;
      this.k2 = 0;
      Neighborhood var6 = this.a8();
      if (super.a2.b5()) {
         this.f2 = var6.T(var5);
         this.v2(2);
      } else {
         if (var6.i(var5, 0) == 0) {
            this.v2(0);
         } else {
            this.v2(1);
         }

         this.f2 = -1;
      }

      if (var5 == 0) {
         for (int var7 = 0; var7 < 6; var7++) {
            int var8;
            if ((var8 = var6.A(var7)) != -1) {
               this.t2(var8);
            }
         }
      }

      this.w2(1);
   }

   public final void f3(int var1) {
      if (this.m2 < 3000000) {
         this.m2 += var1;
      }

      if (this.D != -1) {
         this.E += var1;
         if (this.E > 20000) {
            this.Q();
         }
      } else if (this.F >= 0) {
         this.F += var1;
         if (this.F > 20000) {
            this.F = -1;
         }
      }

      switch (this.l2) {
         case 1:
            this.x2(var1);
            return;
         case 2:
            this.y2(var1);
            return;
         case 3:
            this.z2(var1);
            return;
         case 4:
            this.A2(var1);
            return;
         case 5:
            this.B2(var1);
            return;
         case 6:
            this.C2(var1);
            return;
         case 7:
            this.D2(var1);
            return;
         case 8:
            this.E2(var1);
      }
   }

   public final void a(Graphics var1) {
      if (this.l2 != 8 && this.f2 != -1) {
         Lot var2;
         int var3 = (var2 = this.a5()).d();
         int var4 = var2.e();
         int var5 = var3 + var2.c(super.c, super.d);
         int var6 = var4 + var2.d(super.c, super.d);
         Animator var7 = null;
         if (this.k2 != 50) {
            if (!super.a2.b5() && !super.a2.e3() && (this.o2 == 0 || this.o2 == 6)) {
               com.ea.sims3.SpriteRenderer.drawFrame(var1, 33, 0, var5, var6);
            }

            com.ea.sims3.SpriteRenderer.a12(this.f2);
            var7 = this.a6(0);
            if (!super.a2.b5()) {
               int var8 = var7.animId;
               int var9 = var7.frame;
               this.a2(var1, var8, var9, var5, var6);
            } else {
               var7.draw(var1, var5, var6);
               if (super.b == 0) {
                  super.a2.a(var1, var5, var6 - 16);
               }
            }

            if (this.a4() != null && this.a4().a10 == null) {
               this.a4().b(var1);
            }

            com.ea.sims3.SpriteRenderer.a12(0);
         }

         Animator var17 = this.a6(2);
         if (this.i2 > 0 && var17.isRunning()) {
            int[] var18 = com.ea.sims3.Entity.a;
            int var13 = (this.e2 ? com.ea.sims3.FixedMath.smoothStep(1000, 2000, this.i2) : 65536) * 10;
            int var15 = var6 + (var13 >> 16) - 60 - 10;
            com.ea.sims3.SpriteRenderer.drawAnimator(var1, var17, var5, var15);
            if (this.h2 == 5) {
               var17.matchesFrame(var18, 0);
               com.ea.sims3.SpriteRenderer.drawFrame(var1, this.I, 0, var5 + var18[0], var15 + var18[1]);
            }
         }
      }
   }

   public final void b(Graphics var1) {
      Lot var2;
      int var3;
      int var4;
      if ((var3 = (var2 = this.a5()).a(this.j2, this.k2, true)) != 0 && (var4 = var2.i(var3, super.e)) != 196) {
         int var5 = var2.d();
         int var6 = var2.e();
         int var7;
         int var8;
         if (this.k2 == 50) {
            int[] var9 = com.ea.sims3.Entity.a;
            this.b6.b(var9);
            var7 = var5 + var9[0];
            var8 = var6 + var9[1];
         } else {
            var7 = var5 + var2.c(super.c, super.d);
            var8 = var6 + var2.d(super.c, super.d);
         }

         int var10 = this.a6(0).frame;
         com.ea.sims3.SpriteRenderer.a12(this.f2);
         this.a2(var1, var4, var10, var7, var8);
         com.ea.sims3.SpriteRenderer.a12(0);
      }
   }

   private void a2(Graphics var1, int var2, int var3, int var4, int var5) {
      int var6 = this.j2 == 0 ? 489 : 382;
      int var7 = this.a8().i(super.b, 2) + 1;
      int[] var8 = null;
      boolean var9;
      if (var9 = super.b == 0) {
         var8 = com.ea.sims3.Entity.a;
      }

      boolean var10 = com.ea.sims3.SpriteRenderer.a(var1, var2, var3, var4, var5, var6, var7, var8);
      if (var9 && var10) {
         super.a2.a(var1, var8[0], var8[1]);
      }
   }

   public final int c() {
      return this.a8().R(super.b);
   }

   private final void v2(int var1) {
      if (this.j2 != var1) {
         this.j2 = var1;
         int var2 = this.k2;
         this.k2 = -1;
         this.r2(var2);
      }
   }

   public final void r2(int var1) {
      boolean var2 = super.e == 0 || super.e == 1;
      var1 = this.a5().a(var1, var2);
      if (this.k2 != var1) {
         this.k2 = var1;
         int var3 = this.a5().a(this.j2, var1, false);
         this.p2(var3);
      }
   }

   private final void w2(int var1) {
      if (var1 == 1 && this.l2 == 4 && this.n2 == 2 && super.b == 0 && this.J != -551) {
         this.n2(this.J);
      }

      GameScreen var2 = super.a2;
      switch (this.l2) {
         case 2:
            if (this.a10 != null && this.n2 == 0) {
               this.a10.c(this);
               this.a10 = null;
            }
            break;
         case 3:
            this.J = -551;
            if (var1 != 3 && var2.b5()) {
               this.v2(2);
               if (var2.a11 == this) {
                  var2.c((Entity) null);
               }
            }
      }

      this.b7(128);
      int var3 = this.l2;
      this.l2 = var1;
      this.m2 = 0;
      switch (var1) {
         case 1:
            int var14 = this.n2 == 4
               ? 15
               : (this.n2 == 5 ? 54 : (this.n2 == 1 ? 19 : (this.n2 == 2 ? 19 : (this.n2 == 3 ? 28 : (this.n2 == 6 ? 43 : (this.n2 == 7 ? 36 : 0))))));
            this.r2(var14);
            break;
         case 2:
            byte var13 = -1;
            int var15 = this.n2;
            Entity var16 = this.b6;
            switch (this.n2) {
               case 0:
               default:
                  if (this.o2 == 7) {
                     var13 = 34;
                     var15 = 7;
                     this.a10 = var16;
                     var16.w();
                  } else if (this.o2 == 3) {
                     var13 = 27;
                     var15 = 3;
                  } else {
                     if (this.o2 != 1 && this.o2 != 4 && this.o2 != 5) {
                        if (this.o2 == 6) {
                           var13 = 44;
                           var15 = 6;
                        } else {
                           var16.b(this);
                           this.a10 = var16;
                           int var7 = 0;
                           int var8 = 0;
                           switch (super.e) {
                              case 0:
                              default:
                                 var7 = 524288;
                                 break;
                              case 1:
                                 var8 = 524288;
                                 break;
                              case 2:
                                 var7 = -524288;
                                 break;
                              case 3:
                                 var8 = -524288;
                           }

                           int var9 = var16.e;
                           this.n2(var9);
                           int var10 = 0;
                           int var11 = 0;
                           switch (var9) {
                              case 0:
                              default:
                                 var10 = -196608;
                                 var7 += 131072;
                                 break;
                              case 1:
                                 var11 = -196608;
                                 var8 += 131072;
                                 break;
                              case 2:
                                 var10 = 196608;
                                 var7 -= 131072;
                                 break;
                              case 3:
                                 var11 = 196608;
                                 var8 -= 131072;
                           }

                           if (var16.f3()) {
                              var16.a4(var16.c + var10, var16.d + var11);
                              var16.b7(131072);
                           } else {
                              var7 -= var10;
                              var8 -= var11;
                           }

                           this.a4(super.c + var7, super.d + var8);
                           var13 = 20;
                           var15 = 2;
                        }
                        break;
                     }

                     this.a10 = var16;
                     var16.b(this);
                     var13 = 20;
                     var15 = 1;
                  }
                  break;
               case 1:
                  if (this.o2 == 0) {
                     var13 = 18;
                     var15 = 0;
                  } else if (this.o2 == 4) {
                     var13 = 17;
                     var15 = 4;
                  } else if (this.o2 == 5) {
                     var13 = 53;
                     var15 = 5;
                  }
                  break;
               case 2:
                  var13 = 18;
                  var15 = 0;
                  break;
               case 3:
                  var13 = 30;
                  var15 = 0;
                  break;
               case 4:
                  var13 = 16;
                  var15 = 1;
                  break;
               case 5:
                  var13 = 55;
                  var15 = 1;
                  break;
               case 6:
                  var13 = 42;
                  var15 = 0;
                  break;
               case 7:
                  var13 = 35;
                  var15 = 0;
                  this.a10.x();
            }

            this.n2 = var15;
            this.c4(128);
            this.r2(var13);
            break;
         case 3:
            this.r2(1);
            if (super.b == 0 && var2.b5()) {
               if (!super.a7 && var3 != 3) {
                  var2.c(this);
               }

               var2.d2();
            }
            break;
         case 4:
            Neighborhood var12 = this.a8();
            if (this.H != -1) {
               this.a6(2).play(this.H, 20);
               if (this.h2 == 5) {
                  this.I = var12.M(this.g2);
               }
            } else {
               this.a6(2).setRunning(false);
            }

            if (this.G == -1) {
               this.G = var12.I(this.g2);
            }

            Sim var5 = this.a4();
            if (this.g2 != 82 && this.g2 != 83) {
               if (this.g2 == 93) {
                  var5.c4(128);
                  var5.r2(32);
               } else if (this.g2 == 84) {
                  int var6 = var5.b;
                  if (var12.a5(super.b, var6, this.g2)) {
                     var2.c2(3, -1, var6);
                  }
               }
            } else if (var12.a5(super.b, var5.b, this.g2)) {
               var5.c4(128);
               var5.r2(38);
               this.G = 41;
            } else {
               var5.c4(128);
               var5.r2(40);
               this.G = 39;
            }

            if (!this.c5) {
               this.c4(128);
            }

            this.r2(this.G);
            break;
         case 5:
         case 6:
            this.r2(0);
            break;
         case 7:
            this.a2(this.b6);
            byte var4 = 7;
            if (this.a4() != null && (this.a4().g2 == 82 || this.a4().g2 == 83)) {
               var4 = 0;
            }

            this.r2(var4);
         case 8:
         default:
            break;
         case 9:
            this.a10();
      }

      if (this.l2 == 1 && this.n2 == 2 && super.b == 0) {
         this.J = super.e;
      }
   }

   private final void a(int var1, int var2, int var3, boolean var4, boolean var5) {
      this.G = var1;
      this.c5 = var2 >= 0;
      this.i2 = var2;
      this.H = var3;
      this.e2 = var4;
      this.d4 = var5;
      this.w2(4);
   }

   private final void H() {
      this.G = -1;
      this.c5 = false;
      this.i2 = -1;
      this.H = -1;
      this.e2 = false;
      this.d4 = true;
   }

   private final void b(int var1, boolean var2) {
      this.i2 = var1;
      this.d4 = var2;
      this.w2(8);
   }

   private final void x2(int var1) {
      this.g2(var1);
      this.w += var1;
      if (super.a4 == 0) {
         if (this.m2 > 3000 && this.a8().z(10) != -1) {
            this.a(3, (Entity) null);
            return;
         }

         if (this.m2 > 3500 && this.n2 == 0) {
            int var2;
            if ((var2 = this.a8().h6) > 4915200) {
               this.a(5, (Entity) null);
               return;
            }

            if (var2 < -3276800) {
               this.a(6, (Entity) null);
               return;
            }
         }
      }

      Entity var8;
      if (this.n2 == 1 && this.a10.a() == 9 && (var8 = super.a2.b4(22)) != null && var8.b8(64) && this.m2 > 1000) {
         if (super.b == 0) {
            this.a8().e3(36);
         }

         byte var10 = 16;
         if (var8.b8(32768)) {
            var10 = 15;
         }

         this.a(var10, (Entity) null);
      } else {
         if (this.n2 == 0 && this.m2 > 5000 && (var8 = super.a2.b4(18)) != null && var8.b8(64)) {
            Object var3 = null;
            int var4 = com.ea.sims3.Lot.f(var8.c);
            int var5 = com.ea.sims3.Lot.g(var8.d);
            int var6 = com.ea.sims3.Lot.f(super.c);
            int var7 = com.ea.sims3.Lot.g(super.d);
            if (Math.abs(var6 - var4) + Math.abs(var7 - var5) < 8) {
               this.a(17, (Entity) null);
               return;
            }
         }

         if (this.w > 2500 && this.a11()) {
            this.w = 0;
            this.O();
         }
      }
   }

   private final void y2(int var1) {
      this.g2(var1);
      if (!this.h3()) {
         if (this.n2 == 0 && this.a10 != null && this.a10.a() == 6) {
            int var2 = 131072;
            byte var3 = 0;
            byte var4 = 0;
            switch (super.e) {
               case 0:
               default:
                  var3 = 1;
                  break;
               case 1:
                  var4 = 1;
                  break;
               case 2:
                  var3 = -1;
                  break;
               case 3:
                  var4 = -1;
            }

            Entity var5 = this.a10;
            if (this.a10.b8(131072)) {
               var5.c4(131072);
               var5.a4(var5.c + var3 * 196608, var5.d + var4 * 196608);
            } else {
               var2 += 196608;
            }

            Lot var6 = this.a5();
            int var7 = super.c + var4 * 524288 + var3 * -var2;
            int var8 = super.d + var3 * 524288 + var4 * -var2;
            boolean var9 = var6.b3(var7, var8);
            int var10 = super.c + var4 * -524288 + var3 * -var2;
            int var11 = super.d + var3 * -524288 + var4 * -var2;
            boolean var12 = var6.b3(var10, var11);
            boolean var13 = super.a2.a5(0, var10, var11);
            if (!var9 || !var13 && var12) {
               this.a4(var10, var11);
            } else {
               this.a4(var7, var8);
            }
         }

         if (this.n2 == this.o2) {
            this.E();
            return;
         }

         this.w2(2);
      }
   }

   private final void z2(int var1) {
      Lot var2 = this.a5();
      int var3 = super.c;
      int var4 = super.d;
      int var5 = this.y;
      int var6 = this.z;
      int var7 = com.ea.sims3.Lot.f(var3);
      int var8 = com.ea.sims3.Lot.g(var4);
      int var9 = var2.g(var7, var8);
      int var10;
      int var11 = (var10 = com.ea.sims3.FixedMath.sign(var5 - var3)) != 0 ? 0 : com.ea.sims3.FixedMath.sign(var6 - var4);
      short var12 = 2048;
      short var13 = 1024;
      if (this.b8(2048)) {
         var12 = 2621;
         var13 = 1310;
      } else if (this.b8(4096)) {
         var12 = 1638;
         var13 = 786;
      }

      if (super.a2.b5()) {
         var12 = var13;
         if (this.p3()) {
            int var14 = com.ea.sims3.Lot.f(var3);
            int var15 = com.ea.sims3.Lot.g(var4);
            boolean var17;
            if (var17 = (var2.g(var14, var15) & 32) != 0) {
               int var16;
               if (var11 == 0) {
                  int var18 = com.ea.sims3.Lot.d(var14);
                  if ((var10 >= 0 || var3 > var18) && (var10 <= 0 || var3 < var18)) {
                     var16 = var2.g(var14 - var10, var15);
                  } else {
                     var16 = var2.g(var14 + var10, var15);
                  }
               } else {
                  int var28 = com.ea.sims3.Lot.e(var15);
                  if ((var11 >= 0 || var4 > var28) && (var11 <= 0 || var4 < var28)) {
                     var16 = var2.g(var14, var15 - var11);
                  } else {
                     var16 = var2.g(var14, var15 + var11);
                  }
               }

               var17 = (var16 & 32) != 0;
            }

            if (var17) {
               var12 = 2048;
               this.v2(3);
            } else {
               this.v2(2);
            }
         }
      }

      int var21 = var12 * var1;
      var3 += var10 * var21;
      var4 += var11 * var21;
      if (var10 > 0 && var3 > var5 || var10 < 0 && var3 < var5) {
         var3 = var5;
      }

      if (var11 > 0 && var4 > var6 || var11 < 0 && var4 < var6) {
         var4 = var6;
      }

      this.d2(var10, var11);
      this.a4(var3, var4);
      if (!super.a2.b5()) {
         int var22 = com.ea.sims3.Lot.f(var3);
         int var24 = com.ea.sims3.Lot.g(var4);
         int var26 = var2.g(var22, var24);
         Entity var29;
         if ((var9 & 32) == 0 && (var26 & 32) != 0 && (var29 = super.a2.b4(10)) != null && !var29.b8(64)) {
            var29.a(150, this);
         }
      }

      this.g2(var1);
      if (var3 == var5 && var4 == var6) {
         if (!this.a11() && super.a4 != 0) {
            this.c(true);
            return;
         }

         if (this.b6 != null && (this.b6.c != this.B || this.b6.d != this.C)) {
            this.a(this.b6, 0, 0);
            return;
         }

         boolean var23 = !this.a3(this.b6);
         boolean var25 = this.b7 || var23;
         int[] var27 = this.c4;
         int var30;
         if ((var30 = this.c4 != null ? (var27.length >> 1) - this.A : -1) > (var25 ? 2 : 1)) {
            if (this.b6 != null && ((var30 & 3) == 0 || var30 < 4)) {
               if (!var23) {
                  this.a3(null);
                  this.a3(this.b6);
               }

               this.a(this.b6, 0, 0);
               return;
            }

            this.A++;
            this.y = var27[(this.A << 1) + 0];
            this.z = var27[(this.A << 1) + 1];
            return;
         }

         this.c4 = null;
         if (var23) {
            this.F2(9);
            return;
         }

         this.E();
      }
   }

   private final void a(Entity var1, int var2, int var3) {
      Lot var4 = this.a5();
      boolean var6 = false;
      int var7 = var2;
      int var8 = var3;
      if (var1 != null) {
         var6 = !this.a3(var1);
         int var9 = this.x;
         if (this.x == -1) {
            var9 = var1.a(super.c, super.d, false, null);
         }

         int[] var10 = com.ea.sims3.Entity.a;
         var1.a(var10, var9);
         var7 = var10[0];
         var8 = var10[1];
         this.B = var1.c;
         this.C = var1.d;
      }

      var7 = com.ea.sims3.Lot.d(com.ea.sims3.Lot.f(var7));
      var8 = com.ea.sims3.Lot.e(com.ea.sims3.Lot.g(var8));
      if ((var7 != super.c || var8 != super.d) && this.n2 != 0) {
         this.p2--;
         this.F2(30);
      } else {
         if (super.a2.a11() && var4.a() != 0 && super.b == 0 && this.g2 != 153 && !this.b8(32) && !this.a8().b3(0, 1)) {
            int var18 = com.ea.sims3.Lot.f(super.c);
            int var20 = com.ea.sims3.Lot.g(super.d);
            int var11 = com.ea.sims3.Lot.f(var7);
            int var12 = com.ea.sims3.Lot.g(var8);
            if ((var4.g(var18, var20) & 16) == 0 && (var4.g(var11, var12) & 16) != 0) {
               this.s = this.g2;
               this.c3 = this.b6;
               this.t = this.q;
               this.u = this.r;
               this.b7(16);
               this.a(153, super.a2.b4(10));
               return;
            }
         }

         this.c4(32);
         byte var19;
         if (super.a2.b5()) {
            if (this.p3()) {
               var19 = 2;
            } else {
               var19 = 4;
            }
         } else {
            var19 = 8;
            Sim[] var21;
            if ((var21 = super.a2.a13()).length > 1) {
               var4.d2();

               for (int var23 = 0; var23 < var21.length; var23++) {
                  Sim var25;
                  if ((var25 = var21[var23]) != this) {
                     int var13 = com.ea.sims3.Lot.f(var25.c);
                     int var14 = com.ea.sims3.Lot.g(var25.d);
                     var4.c2(var13, var14);
                  }
               }

               var19 = 9;
            }
         }

         int var22 = super.c;
         int var24 = super.d;
         int[] var26 = var4.a4(var22, var24, var7, var8, var19);
         if (var6 && var26.length == 2) {
            this.F2(9);
         } else if (var26.length >= 2) {
            this.c4 = var26;
            this.A = 0;
            this.b7 = false;
            this.y = var26[(this.A << 1) + 0];
            this.z = var26[(this.A << 1) + 1];
            this.w2(3);
         } else {
            int var27 = var22 - var7;
            int var28 = var24 - var8;
            int var15;
            if ((var15 = Math.abs(var27) + Math.abs(var28)) <= 524288) {
               if (var15 != 0) {
                  this.c4 = var26;
                  this.A = 0;
                  this.b7 = false;
                  this.y = var7;
                  this.z = var8;
                  this.w2(3);
               } else {
                  this.E();
               }
            } else {
               this.c(false);
            }
         }
      }
   }

   private final void A2(int var1) {
      int var2 = this.a6(0).frame;
      this.g2(var1);
      int var3 = this.a6(0).frame;
      Animator var4 = this.a6(0);
      if (var2 != var3) {
         if (this.g2 == 147 && var3 == 2) {
            this.b6.a(this);
         }

         if (this.g2 == 93 && var3 == 2 && (super.b == 0 || this.a4() != null && this.a4().b == 0)) {
            super.a3.n2();
         }

         int var5 = super.e != 0 && super.e != 1 ? 3 : 2;
         if (this.g2 == 83 && this.k2 == 41 && var3 == var5) {
            super.a2.c2(2, 789, this.b6.b);
         }

         if (this.g2 == 4 && var3 == 15 && super.a3.d() < 5) {
            super.a2.d3(803);
         }
      }

      this.a6(2).advance(var1);
      boolean var6;
      if (this.c5) {
         if (this.d4) {
            var6 = this.i2 <= this.a8().d6;
         } else {
            if (!com.ea.sims3.GameScreen.f3()) {
               this.i2 -= var1;
            }

            var6 = this.i2 <= 0;
         }
      } else {
         var6 = !var4.isRunning();
      }

      if (this.h2 == 49 && this.b6.g3()) {
         this.E();
      }

      if (var6) {
         this.G = -1;
         this.i2 = -1;
         this.c5 = true;
         this.E();
      } else {
         if (super.b == 0 && this.h2 == 20 && this.m2 > 4000 && this.d4 && this.i2 - this.a8().d6 > 60 && this.a11() && !super.a2.c3()) {
            super.a2.g2();
         }

         if (this.h2 == 17 && this.b6.a() == 3 && this.k2 != 50 && this.b6.e3()) {
            if (super.b == 0) {
               this.a8().e2(3, 655360);
            }

            this.r2(50);
         }
      }
   }

   private final void B2(int var1) {
      this.g2(var1);
      Sim var2 = this.a4();
      if (this.g2 != 94) {
         int var3 = var2.c - super.c;
         int var4 = var2.d - super.d;
         if (Math.abs(var3) + Math.abs(var4) > 524288) {
            this.c(false);
            return;
         }
      }

      if (this.m2 > 4000) {
         this.c(false);
      } else {
         if (var2.c3()) {
            var2.a(2, (Entity) this);
            this.E();
         }
      }
   }

   private final void C2(int var1) {
      this.g2(var1);
      if (this.m2 > 4000) {
         this.c(false);
      } else {
         if (this.a3(this.b6)) {
            this.F2(1);
         }
      }
   }

   private final void D2(int var1) {
      this.g2(var1);
      Sim var2;
      if ((var2 = this.a4()) != null && !var2.c3()) {
         if (var2 != null && var2.b8(1024)) {
            Neighborhood var3;
            if ((var3 = this.a8()).j(super.b, 12) != -1) {
               if (!this.b8(262144)) {
                  var3.e3(super.b, 12);
                  this.b7(262144);
                  return;
               }
            } else {
               var2.F();
               this.Q();
               byte var4 = 0;
               if (var3.j(super.b, 13) != -1) {
                  var4 = 34;
                  var3.e3(super.b, 13);
                  var3.a4(super.b, -1, -1);
               }

               this.b(14, var2, var4, 0);
            }
         }
      } else {
         this.E();
      }
   }

   private final void E2(int var1) {
      if (this.g2 != 159) {
         if (this.d4) {
            if (this.i2 <= this.a8().d6) {
               this.E();
               return;
            }
         } else if (this.i2 > 0) {
            this.i2 -= var1;
            if (this.i2 <= 0) {
               this.E();
            }
         }
      }
   }

   public final Sim a4() {
      return this.b6 instanceof Sim ? (Sim)this.b6 : null;
   }

   public final boolean c3() {
      return (this.a8().K(this.g2) & 2) != 0;
   }

   public final boolean l3() {
      return this.c3() || this.h2 == 8 || this.h2 == 9 || this.h2 == 1;
   }

   public final boolean m3() {
      return this.h2 == 26;
   }

   public final boolean n3() {
      return (this.g2 == 122 || this.g2 == 121) && this.n2 == 4;
   }

   public final void a(int var1, Entity var2, int var3, int var4) {
      if (this.l3()) {
         this.b(var1, var2, var3, var4);
      } else {
         this.s = var1;
         this.c3 = var2;
         this.t = var3;
         this.u = var4;
      }
   }

   public final void a(int var1, Entity var2) {
      this.b(var1, var2, 0, 0);
   }

   public final void b(int var1, Entity var2, int var3, int var4) {
      Neighborhood var5 = this.a8();
      if (this.h2 != 0) {
         this.b(true);
      }

      this.c4(262146);
      if (var1 == 119 && this.j2 == 0 && super.a3.d() < 50) {
         var1 = 120;
      }

      this.g2 = var1;
      this.b6 = var2;
      this.q = var3;
      this.r = var4;
      if (var2 != null || var1 == 106) {
         this.a3(var2);
      }

      Sim var6;
      if ((var6 = this.a4()) != null && (var5.K(var1) & 1) != 0) {
         this.d(var6);
      }

      int var7 = var5.g(var1, 0);
      this.p2 = 0;
      this.F2(var7);
   }

   public final void b(Entity var1) {
      if (var1 != this && (this.v & 32) != 0) {
         Sim var2 = super.a2.a14.a4();
         if (this.D == 0 || var2 != null && this.D == var2.b) {
            this.Q();
         }
      }
   }

   public final boolean a3(Entity var1) {
      if (var1 == this.d3) {
         return true;
      } else {
         if (this.d3 != null) {
            this.d3.d3(this.x);
            this.d3 = null;
            this.x = -1;
         }

         if (var1 == null) {
            return true;
         } else {
            if (var1.b5()) {
               int var2 = 0;
               if (var1.a() == 10) {
                  Lot var3 = this.a5();
                  int var4 = com.ea.sims3.Lot.f(super.c);
                  int var5 = com.ea.sims3.Lot.g(super.d);
                  if ((var3.g(var4, var5) & 16) != 0) {
                     var2 = 0;
                  } else {
                     var2 = 1;
                  }
               } else {
                  var2 = var1.a(super.c, super.d, true, this);
               }

               if (var2 != -1 && var1.c5(var2)) {
                  this.d3 = var1;
                  this.x = var2;
                  return true;
               }
            }

            return false;
         }
      }
   }

   private final void I() {
      boolean var1 = this.b8(4);
      Neighborhood var2 = this.a8();
      this.c4(12);
      int var3 = this.g2;
      Entity var4 = this.b6;
      Sim var5 = this.a4();
      boolean var6 = super.b == 0;
      this.a(0, (Entity) null);
      int var7 = var2.K(var3);
      if (!var1 && var5 != null && (var7 & 1) != 0) {
         this.a3(null);
         var5.a3(null);
         if (var5.a(var3, this)) {
            this.Q();
         }

         if (var6) {
            int var9 = var5.b;
            if (var3 == 93) {
               var2.e3(19);
               var2.g2(var9, 32);
               var2.g2(var9, 2048);
            } else if (var3 == 105) {
               var2.g2(var9, 256);
            }
         }
      } else if (!var1) {
         switch (var3) {
            case 4:
               if (var6) {
                  var2.d2(3, -6553600);
               }

               if (!super.a2.a5(1, super.c, super.d)) {
                  int var8 = -1;
                  if (this.a5().a() == 0) {
                     var8 = this.a5().a(9, super.c, super.d, super.e);
                  }

                  super.a2.a(9, super.c, super.d, super.e, var8);
               }
               break;
            case 107:
               super.a2.f2();
               break;
            case 111:
            case 113:
               super.a2.h2(4);
               break;
            case 121:
            case 122:
               this.v &= -5;
               break;
            case 143:
               super.a2.i2();
               break;
            case 147:
               if (var6) {
                  var2.e3(37);
                  var2.f2(this.a5().a(), 16);
                  var2.f2(this.a5().a(), 4096);
               }
               break;
            case 152:
               if (!var6) {
                  this.m2 = 0;
                  super.a2.g2(super.b);
               }
               break;
            case 155:
            case 156:
            case 160:
               super.a2.a2(var4);
               break;
            case 158:
               var2.e3(21);
               var2.d2(0, 2621440);
         }
      }

      if (var3 == 153) {
         this.c4(16);
         if (var1) {
            this.s = -1;
            this.c3 = null;
         } else {
            this.b7(32);
         }
      }

      if (var4 != null && var5 == null && !var1) {
         var4.a(var3, this);
         if (var6) {
            var2.i2(var4.a4);
         }
      }

      if (this.s != -1 && this.c3()) {
         this.b(this.s, this.c3, this.t, this.u);
         this.s = -1;
         this.c3 = null;
      }
   }

   public final boolean a(int var1, Sim var2) {
      Neighborhood var3 = this.a8();
      int var4 = var2.b;
      int var5 = super.b;
      int[] var6 = com.ea.sims3.Entity.a;
      var3.a(var6, var4, var5, var1);
      int var7 = var6[0];
      int var8 = var6[1];
      int var9 = var6[2];
      if ((var7 & 64) != 0) {
         super.a2.a(25, var2, this);
      }

      if (var1 == 103 || var1 == 98) {
         this.a(0, (Entity) null);
         this.s2(256);
      } else if (var8 != -1) {
         if ((var3.K(var8) & 1) == 0 && var8 != 106) {
            var2 = null;
         }

         if (var4 == 0 && var8 == 13) {
            var3.e3(15);
            var3.g2(var5, 8192);
         }

         this.b(var8, var2, var7, 0);
         if (var9 != -1) {
            super.a2.a2(super.c, super.d, var9, 0);
         }
      }

      return (var7 & 32) != 0;
   }

   public final boolean a2(int var1, Sim var2, Entity var3) {
      Neighborhood var4 = this.a8();
      if (this.n3()) {
         return false;
      } else if (var1 == 25 && var4.S(super.b) != var3.b) {
         return false;
      } else {
         int[] var5 = com.ea.sims3.Entity.a;
         var4.a(var5, var2.b, super.b, var1);
         int var6;
         if ((var6 = var5[1]) != -1) {
            int var7;
            Object var8 = ((var7 = var5[0]) & 16384) != 0 ? var3 : var2;
            this.F();
            this.a(var6, (Entity)var8, 0, 0);
            if (var1 == 25 && var2.b == 0) {
               var4.e3(51);
            }

            this.b7(8);
            return true;
         } else {
            return false;
         }
      }
   }

   private final void F2(int var1) {
      GameScreen var2 = super.a2;
      Neighborhood var3 = this.a8();
      this.h2 = var1;
      int var4 = this.g2;
      Entity var5 = this.b6;
      Sim var6 = this.a4();
      int var7 = this.q;
      int var8 = this.r;
      boolean var9;
      if ((var9 = super.b == 0) && var6 != null) {
         var3.j2(var6.b);
      }

      boolean var10 = false;
      switch (var1) {
         case 0:
            if (this.n2 != 7 && this.n2 != 6) {
               this.w2(1);
            } else {
               this.p2--;
               this.F2(30);
            }
            break;
         case 1:
            this.a(var5, var7, var8);
            break;
         case 2:
         case 3:
            if (var5 != null) {
               this.a2(var5);
            } else {
               this.d2(var7 - super.c, var8 - super.d);
            }

            if (var1 == 3) {
               this.n2(super.e + 2 & 3);
            }

            this.E();
            break;
         case 4:
            this.n2(var5.e);
            this.E();
            break;
         case 5:
         case 6:
         case 7:
            this.e2(var4, var1);
            var10 = true;
            break;
         case 8:
            this.w2(5);
            break;
         case 9:
            this.w2(6);
            break;
         case 10:
            this.a4().F();
            this.E();
            break;
         case 11:
            this.w2(7);
            break;
         case 12:
            if (var3.a13(var7)) {
               var2.a(0, var5);
               var5.A();
               this.a(4, 1000, -1, false, false);
            } else {
               this.c(false);
            }
            break;
         case 13:
            if (var3.b5()) {
               var2.a(1, var5);
               if (var5.a4 == 5) {
                  this.n2(0);
               } else {
                  this.n2(1);
               }

               this.m2(20);
               this.a(24, 1000, -1, false, false);
            } else {
               this.c(false);
            }
            break;
         case 14:
            if (var3.c3()) {
               var2.a(2, var5);
               this.a(4, 1000, -1, false, false);
            } else {
               this.c(false);
            }
            break;
         case 15:
            if (var3.d3()) {
               var2.a(3, var5);
               this.a(4, 1000, -1, false, false);
            } else {
               this.c(false);
            }
            break;
         case 16:
            short var18 = 5000;
            if (var9) {
               int var19 = 458752;
               if (var4 == 126) {
                  var19 = this.a5().C(var7);
               }

               var3.e2(0, var19);
            }

            if (var5.a() == 13) {
               var5.r2();
               var18 = 3500;
            }

            byte var20 = 4;
            if (this.n2 == 2 || this.n2 == 1) {
               var20 = 60;
            }

            this.a(var20, var18, -1, false, false);
            var10 = true;
            break;
         case 17:
            int var17;
            if ((var17 = var5.a()) == 17) {
               if (var9) {
                  var3.e2(3, 655360);
               }

               var5.y();
               this.b(5000, false);
               var10 = true;
            } else if (var17 == 3) {
               var5.n2();
               var5.b(this);
               this.a(4, 8000, -1, false, false);
               var10 = true;
            } else if (var17 == 2) {
               if (var9 && !this.b8(1024)) {
                  var3.e2(3, 655360);
               }

               var5.l2();
               this.a(4, 1500, -1, false, false);
            }
            break;
         case 18:
            if (var9) {
               var3.e2(2, 3276800);
            }

            int var16 = var4 == 119 ? 28 : 29;
            this.a(var16, 2000, -1, false, false);
            break;
         case 19:
         case 20:
            this.G2(var1);
            var10 = true;
            break;
         case 21:
            var5.j2();
            this.E();
            var10 = true;
            break;
         case 22:
            var5.k2();
            this.E();
            var10 = true;
            break;
         case 23:
            if (var9) {
               var3.e3(36);
            }

            Entity var15;
            if ((var15 = var2.b4(9)) != null) {
               this.b(144, var15, 0, 0);
            } else {
               this.b(106, var5, 0, 0);
            }

            var10 = true;
            break;
         case 24:
            Entity var14;
            if ((var14 = var2.c2(128)) != null) {
               this.b6 = var14;
               this.E();
            } else {
               this.c(false);
            }
            break;
         case 25:
            if (var9) {
               var3.e2(5, 196608);
            }

            this.a(58, 10000, -1, false, false);
            break;
         case 26:
            if (var9) {
               this.w2(8);
               var2.g2();
            } else {
               if (var2.a13().length == 2 && !var3.j3()) {
                  var2.b2(767, var3.R(super.b));
               }

               this.w2(9);
            }
            break;
         case 27:
            var2.g2(var4, var7);
            this.E();
            break;
         case 28:
            int var13 = var3.a(var5);
            int var12 = var3.n(var13);
            var2.a2(var13, var12, true);
            this.E();
            break;
         case 29:
            var2.b2(766, 765);
            this.E();
            break;
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
            this.H2(var1);
            break;
         case 39:
            if (var9) {
               this.J();
            } else {
               this.E();
            }
            break;
         case 40:
            if (var9) {
               this.K();
            } else {
               this.E();
            }
            break;
         case 41:
            if (var9) {
               this.L();
            } else {
               this.E();
            }
            break;
         case 42:
            var2.g(var5);
            this.E();
            break;
         case 43:
            var2.l2();
            this.E();
            break;
         case 44:
            var2.m2();
            this.E();
            break;
         case 45:
            this.M();
            break;
         case 46:
            this.N();
            break;
         case 47:
            var5.u2();
            this.E();
            break;
         case 48:
            var5.v2();
            this.E();
            break;
         case 49:
            var5.p2();
            this.a(0, 60000, -1, false, false);
            var10 = true;
            break;
         case 50:
            if (var9) {
               var3.e2(2, -327680);
               var3.e2(1, 655360);
            }

            this.a(4, 2000, -1, false, false);
            break;
         case 51:
            Entity var11 = var5.b3();
            this.b6 = var11;
            this.a2(var11);
            this.F2(31);
            break;
         case 52:
            if (var9) {
               var3.e2(5, 393216);
            }

            this.b6 = this.b6.a3();
            this.b6.j2();
            this.a(60, 15000, -1, false, false);
      }

      if (var9 && var10 && (var3.K(var4) & 1048576) != 0) {
         var2.a(22, this, this.b6);
      }

      if (this.b8(2)) {
         this.c(true);
      }
   }

   private void e2(int var1, int var2) {
      Neighborhood var3 = this.a8();
      Entity var4;
      if (var1 == 152 && (var4 = super.a2.b4(10)) != null && var4.b8(64)) {
         this.E();
      } else {
         boolean var9 = false;
         int var5 = -1;
         if (var2 == 5) {
            var5 = super.e != 3 && super.e != 0 ? 662 : 661;
            byte var6;
            byte var7;
            if (var3.i(super.b, 0) == 1) {
               var6 = 4;
               var7 = 6;
            } else {
               var6 = 7;
               var7 = 9;
            }

            GameCanvas var8;
            (var8 = com.ea.sims3.GameCanvas.a7()).a7.pendingSound = var8.b(var6, (int)var7);
         }

         boolean var10 = false;
         if (var1 != 136 && var1 != 137 && var1 != 111 && this.q != 0) {
            var5 = com.ea.sims3.Neighborhood.D(this.q);
            var10 = true;
         }

         int var11 = var2 == 7 ? -1 : 2000;
         if (var1 == 17) {
            var11 = 5000;
         }

         this.a(-1, var11, var5, var10, false);
         if (super.b == 0) {
            if (var1 == 148) {
               var3.e2(3, -1310720);
               return;
            }

            if (var1 == 15 || var1 == 16) {
               var3.e2(5, 196608);
               return;
            }

            if (var1 == 17) {
               var3.e2(5, 196608);
            }
         }
      }
   }

   private void G2(int var1) {
      Neighborhood var2;
      int var3 = (var2 = this.a8()).d6;
      int var4 = var2.b2(super.b);
      if (var1 == 19) {
         var4 = Math.min(var4, var3 + 120);
      }

      int var5 = var4 - var3;
      if (super.b == 0) {
         var2.a9 = true;
         var2.e2(1, 65536);
      }

      this.C();
      this.l2(super.e == 0 ? 36 : 35);
      int var6 = var3 + var5;
      this.a(15, var6, -1, false, true);
   }

   private void H2(int var1) {
      if (var1 == 31) {
         if (this.b6.a() == 6) {
            var1 = 33;
         } else {
            this.n2(super.e + 2 & 3);
            var1 = 32;
         }
      }

      int var2 = var1 == 30 ? 0 : (var1 == 32 ? 1 : (var1 == 33 ? 2 : (var1 == 34 ? 3 : (var1 == 37 ? 6 : (var1 == 35 ? 4 : (var1 == 36 ? 5 : 7))))));
      if (this.n2 == var2) {
         this.E();
      } else {
         this.o2 = var2;
         this.w2(2);
      }
   }

   private void J() {
      Neighborhood var1;
      int var2 = (var1 = this.a8()).a3(this.g2);
      int var3 = var1.e5;
      if (var1.e5 >= var2) {
         var1.a12(-var2);
         super.a2.b2(super.c, super.d, var2);
         this.E();
      } else {
         this.c(false);
      }
   }

   private void K() {
      Neighborhood var1 = this.a8();
      boolean var2 = true;
      if (super.a2.a13().length > 1) {
         var2 = false;
      } else if (var1.V(0) == 4) {
         var2 = false;
      } else if (var1.j3()) {
         var2 = false;
      }

      if (var2) {
         this.c(false);
      } else {
         this.E();
      }
   }

   private void L() {
      Neighborhood var1;
      if ((var1 = this.a8()).z(12) == -1 && var1.z(11) == -1) {
         this.E();
      } else {
         byte var2 = 7;
         if (super.a3.d() < 50) {
            var2 = 14;
         }

         this.a(var2, this.b6);
      }
   }

   private void M() {
      Neighborhood var1 = this.a8();
      int var2 = this.q;
      if (var1.c(var2) > 0) {
         var1.b2(var2, -1);
         this.b6.k2(var2);
         this.E();
      } else {
         this.c(false);
      }
   }

   private void N() {
      Neighborhood var1;
      if ((var1 = this.a8()).c(3) > 0 && var1.c(4) > 0) {
         var1.b2(4, -1);
         this.b6.t2();
         this.E();
      } else {
         this.c(false);
      }
   }

   public final void E() {
      this.b(false);
   }

   private final void b(boolean var1) {
      Neighborhood var2 = this.a8();
      int var4 = this.a5().a();
      int var5 = var2.L(this.g2);
      boolean var6 = super.b == 0;
      Entity var7 = this.b6;
      switch (this.h2) {
         case 5:
         case 6:
         case 7:
            this.H();
            if (var6) {
               if (this.g2 == 148) {
                  var2.e2(3, 0);
               } else if (this.g2 == 15 || this.g2 == 16 || this.g2 == 17) {
                  var2.e2(5, 0);
               }
            }
         case 8:
         case 9:
         case 10:
         case 11:
         case 14:
         case 15:
         case 21:
         case 22:
         case 23:
         case 24:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 51:
         default:
            break;
         case 12:
            var7.B();
            break;
         case 13:
            this.m2(0);
            break;
         case 16:
            if (var6) {
               var2.e2(0, 0);
            }

            if (var7 != null && var7.a() == 13) {
               var7.s2();
            }
            break;
         case 17:
            if (var6) {
               var2.e2(3, 0);
            }

            if (var7 != null) {
               int var9;
               if ((var9 = var7.a()) == 17) {
                  var7.z();
                  if (var6 && var4 != 0) {
                     var2.e3(39);
                     var2.f2(var4, 8);
                  }
               } else if (var9 == 2) {
                  var7.m2();
               } else if (var9 == 3) {
                  var7.o2();
                  var7.c(this);
               }
            }
            break;
         case 18:
            if (var6) {
               var2.e2(2, 0);
               if (var4 != 0) {
                  var2.e3(40);
                  var2.f2(var4, 4);
               }
            }
            break;
         case 19:
         case 20:
            if (var6) {
               var2.e2(1, 0);
               var2.a9 = false;
               if (this.h2 == 20 && var4 != 0) {
                  var2.e3(38);
                  int var8 = var7.d();
                  var2.f2(var4, var8);
               }
            }

            this.l2(-1);
            this.P();
            break;
         case 25:
            if (var6) {
               var2.e2(5, 0);
            }
            break;
         case 49:
            if (var7 != null) {
               var7.q2();
            }
            break;
         case 50:
            if (var6) {
               var2.e2(2, 0);
               var2.e2(1, 0);
            }
            break;
         case 52:
            if (var6) {
               var2.e2(5, 0);
            }

            var7.k2();
      }

      if (!var1) {
         if (this.p2 < var5 - 1) {
            this.p2++;
            int var10 = var2.g(this.g2, this.p2);
            this.F2(var10);
            return;
         }

         this.I();
      }
   }

   public final void F() {
      int var1;
      if (((var1 = this.a8().K(this.g2)) & 1) != 0 && (var1 & 524288) == 0 && this.a4() != null) {
         this.a4().F();
      }

      this.b7(2);
      this.c(true);
   }

   private final void c(boolean var1) {
      this.b7(4);
      short var2 = -1;
      int var3 = 273;
      switch (this.h2) {
         case 0:
         case 2:
         case 3:
         case 4:
         case 10:
         case 26:
         case 27:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 41:
         case 42:
         case 43:
         case 44:
         case 47:
         case 48:
         case 51:
         default:
            break;
         case 1:
            var2 = 278;
            break;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 11:
         case 16:
         case 21:
         case 22:
         case 23:
         case 25:
         case 28:
         case 49:
         case 50:
            this.b(true);
            this.I();
            break;
         case 12:
            var2 = 779;
            var3 = this.a5().G(this.q);
            break;
         case 13:
            var2 = 336;
            break;
         case 14:
            var2 = 283;
            break;
         case 15:
            var2 = 281;
            break;
         case 17:
         case 18:
         case 19:
         case 20:
         case 52:
            this.E();
            break;
         case 24:
            if (this.g2 == 146) {
               var2 = 315;
            } else {
               var2 = 301;
            }
            break;
         case 39:
            var2 = 299;
            break;
         case 40:
            var2 = 323;
            break;
         case 45:
            var2 = 285;
            break;
         case 46:
            var2 = 288;
      }

      if (var2 != -1) {
         if (!var1 && super.b == 0) {
            super.a2.c2(var2, var3);
         }

         this.I();
      }
   }

   private final void O() {
      GameCanvas var1 = super.a3;
      int var2 = this.v;
      if ((this.v & 257) != 0) {
         Entity var11 = super.a2.a5(4);
         this.a(159, var11);
      } else if (super.a2.b9 != this && (super.a4 != 0 || super.a2.b9 == null)) {
         if (this.F != -1 || (this.v & 32) == 0 || super.b == 0 && (!var1.h2 || !super.a2.d3()) || super.b != 0 && (this.v & 4) != 0 || !this.o3()) {
            if ((var2 & 94) != 0) {
               int var3 = var1.b(0, d5.length - 1);
               if ((var2 & 4) != 0) {
                  var3 = com.ea.sims3.GameCanvas.a(4, d5);
               }

               for (int var4 = 0; var4 < d5.length; var4++) {
                  int var5 = d5[var3];
                  Entity var6;
                  if ((var2 & var5) != 0 && (var6 = super.a2.c2(var5)) != null) {
                     Lot var7 = this.a5();
                     int var8 = var6.a4;
                     short var9 = var7.a16(var8)[0];
                     this.a(var9, var6);
                     int var10 = var7.l(var8);
                     this.v &= ~var10;
                     return;
                  }

                  var3 = (var3 + 1) % d5.length;
               }
            }
         }
      }
   }

   private final boolean o3() {
      GameCanvas var1 = super.a3;
      Neighborhood var2 = this.a8();
      Sim var3 = null;
      if (this.D != -1) {
         var3 = super.a2.a7(this.D);
         if (!this.a2(var3)) {
            var3 = null;
         }
      }

      int var4 = super.b;
      if (var2.U(var4) != this.a5().a() && !var2.b3(var4, 1) && (var4 != 0 || var1.h2)) {
         if ((this.v & 512) != 0) {
            if (var4 != 0) {
               if (this.m2 > 30000) {
                  this.v |= 256;
               }
            } else {
               this.v &= -513;
            }

            return true;
         }

         Entity var5;
         if ((var5 = super.a2.b4(10)) != null) {
            this.a(152, var5);
            this.v |= 512;
            return true;
         }
      } else {
         this.v &= -513;
      }

      if (var3 == null) {
         Sim[] var10;
         int var6 = (var10 = super.a2.a13()).length;
         int var7 = var1.b(0, var6 - 1);

         for (int var8 = 0; var8 < var6; var8++) {
            Sim var9 = var10[var7];
            var7 = (var7 + 1) % var10.length;
            if (this.a2(var9)) {
               var3 = var9;
               break;
            }
         }
      }

      if (var3 != null) {
         int var11;
         int var12 = (var11 = var1.d()) < 30 ? 63 : (var11 < 70 ? 90 : 76);
         this.a(var12, (Entity)var3);
         return true;
      } else {
         return false;
      }
   }

   private final boolean a2(Sim var1) {
      if (var1 != this && var1 != null) {
         Entity var2 = super.a2.b9;
         if (var1 == var2) {
            return false;
         } else if (var1.b == 0 && var2 != null) {
            return false;
         } else if (var1.n3()) {
            return false;
         } else if (var1.D != -1 && var1.D != super.b) {
            return false;
         } else {
            return var1.b8(8) ? false : !var1.b8(1024) || this.a8().j(super.b, 12) != -1;
         }
      } else {
         return false;
      }
   }

   public final void G() {
      this.v = 0;
   }

   public final void s2(int var1) {
      this.v |= var1;
   }

   public final void t2(int var1) {
      switch (var1) {
         case 4:
         case 5:
         case 7:
            this.b7(4096);
            break;
         case 6:
            this.b7(2048);
            return;
         case 8:
         default:
            break;
         case 9:
            this.b7(1024);
            if (!super.a2.b5()) {
               this.l2(43);
               this.c2(40, 20);
               return;
            }
      }
   }

   public final void u2(int var1) {
      switch (var1) {
         case 4:
         case 5:
         case 7:
            this.c4(4096);
            break;
         case 6:
            this.c4(2048);
            return;
         case 8:
         default:
            break;
         case 9:
            this.c4(1024);
            if (!super.a2.b5()) {
               this.l2(-1);
               this.C();
               return;
            }
      }
   }

   private void P() {
      if (this.b8(1024)) {
         this.t2(9);
      }
   }

   private void d(Sim var1) {
      this.D = var1.b;
      this.F = -1;
      this.s2(32);
      var1.D = super.b;
      var1.F = -1;
      var1.s2(32);
   }

   private final void Q() {
      Sim var1;
      if ((var1 = super.a2.a7(this.D)) != null) {
         var1.v &= -33;
         var1.D = -1;
         var1.E = 0;
         var1.F = 0;
      }

      this.v &= -33;
      this.D = -1;
      this.E = 0;
      this.F = 0;
   }

   private final boolean p3() {
      Neighborhood var1;
      int var2;
      if ((var2 = (var1 = this.a8()).V(super.b)) != -1) {
         int var3 = var1.W(super.b);
         if ((var1.d(var2, var3) & 1) != 0) {
            return true;
         }
      }

      return super.a4 == 0 && var1.c(35) != 0 ? true : var1.c3(super.b, 1);
   }

   public final void c(Entity var1) {
      this.a3(var1);
      int[] var3 = com.ea.sims3.Entity.a;
      var1.a(var3, this.x);
      this.a4(var3[0], var3[1]);
      this.a2(var1);
      this.n2((super.e + 2) % 4);
      this.n2 = 4;
      this.o2 = 4;
      this.a10 = var1;
      var1.b(this);
      if (super.b == 0) {
         super.a2.d(var1);
      }

      this.a(122, var1);
   }
}
