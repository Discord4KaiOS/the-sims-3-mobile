package com.ea.sims3;

import javax.microedition.lcdui.Graphics;

public abstract class BaseScreen implements SpriteIndex, SoundIndex, Resources {
   public final GameCanvas canvas;
   public final int[] a2;
   public int d;
   private int a3;
   public int e;
   public int f;
   public int g;
   public int h;
   public int i;
   private static final int[] c = new int[6];
   private final short[] b = new short[11];
   public Animator leftArrowAnim;
   public Animator rightArrowAnim;
   public int j;
   private int b3;
   public int k;
   private int c2;
   public int l;
   private int m;

   public boolean matchesInput(int var1, int var2, int var3, int var4) {
      if ((var2 & var4) == 0 && (var1 & var3) == 0) {
         return false;
      } else {
         this.canvas.h2();
         this.canvas.c4(var3);
         return true;
      }
   }

   public BaseScreen(GameCanvas var1) {
      this.canvas = var1;
      this.a2 = new int[4];
   }

   public abstract void setLanguage(int var1);

   public abstract void stop();

   public abstract void pause();

   public abstract void render(Graphics var1);

   public abstract void update(int var1, int var2);

   public abstract void keyPressed(int var1);

   public abstract void onTick(int var1);

   public final void a(Graphics var1, int var2) {
      GameCanvas var3 = this.canvas;
      this.b(var1, var2, 0, 0, var3.getWidth(), var3.getHeight() - 15);
   }

   public final void b(Graphics var1, int var2, int var3) {
      GameCanvas var4 = this.canvas;
      this.b(var1, var2, 0, var3, var4.getWidth(), var4.getHeight() - 15 - var3);
   }

   public final void initLayout() {
      GameCanvas var1 = this.canvas;
      this.e = var1.b2(1);
      this.f = 216;
      this.g = this.f * 4 / 5;
      this.d = this.f * 17 / 20 - b();
      this.h = 192;
      this.a3 = var1.getHeight() * 3 / 5 / var1.b2(1);
      this.i = var1.b2(1) + 2;
      this.leftArrowAnim = new Animator();
      this.rightArrowAnim = new Animator();
      this.leftArrowAnim.play(560, 4);
      this.rightArrowAnim.play(562, 4);
   }

   public final void showLeftArrow() {
      this.leftArrowAnim.play(559, 16);
   }

   public final void showRightArrow() {
      this.rightArrowAnim.play(561, 16);
   }

   public final void advanceArrows(int var1) {
      this.leftArrowAnim.advance(var1);
      this.rightArrowAnim.advance(var1);
      if (!this.leftArrowAnim.isRunning()) {
         this.leftArrowAnim.play(560, 20);
      }

      if (!this.rightArrowAnim.isRunning()) {
         this.rightArrowAnim.play(562, 20);
      }
   }

   public final void setValue(int var1, int var2) {
      this.b[var1] = (short)var2;
   }

   public final void b(Graphics var1, int var2, int var3, int var4, int var5, int var6) {
      this.a(var1, this.a16(var2), var3, var4, var5, var6);
   }

   public final void a(Graphics var1, short[] var2, int var3, int var4, int var5, int var6) {
      int[] var7 = c;
      c[0] = var3;
      var7[1] = var4;
      var7[2] = var5;
      var7[3] = 0;
      this.a(null, var2, var7);
      var7[0] = var3;
      var7[2] = var5;
      int var8 = var7[3];
      if (this.a(var2) != 0) {
         var7[1] = var4;
         var7[3] = var6 - var8;
      } else {
         var7[1] = var4 + (var6 - var8 >> 1);
         var7[3] = var8;
      }

      this.a(var1, var2, var7);
   }

   private void a(Graphics var1, short[] var2, int[] var3) {
      int var4 = var3[0];
      int var5 = var3[1];
      int var6 = var3[2];
      int var7 = var3[3];
      int var8 = 0;
      int var9 = 0;

      for (byte var10 = 0; var10 < var2.length; var10 += 2) {
         short var11 = var2[var10];
         short var12 = var2[var10 + 1];
         var3[4] = var12;
         this.a(null, var11, var3);
         int var13 = var3[3];
         var9 += this.a2(var11, var12);
         var8 += var13;
      }

      if (var1 != null) {
         int var17 = var7;
         if (var9 > 0) {
            var17 = var7 + var8;
         }

         var3[0] = var4;
         var3[1] = var5;
         var3[2] = var6;
         int var18 = 0;

         for (byte var19 = 0; var19 < var2.length; var19 += 2) {
            short var20 = var2[var19];
            short var14 = var2[var19 + 1];
            var3[4] = var14;
            int var15;
            if ((var15 = this.a2(var20, var14)) != 0) {
               int var16 = var7 * var15 / var9;
               var3[3] = var16;
               this.a(var1, var20, var3);
            } else {
               var3[3] = var17 - var18;
               this.a(var1, var20, var3);
            }

            var18 += var3[3];
         }
      }

      if (var1 == null) {
         var3[3] = var8;
      } else {
         var3[3] = var7 + var8;
      }
   }

   private final int a2(int var1, int var2) {
      int var3 = 0;
      if (var1 == 22) {
         return var2;
      } else {
         if (var1 == 27) {
            var3 = 1;
         } else if (var1 == 29) {
            var3 = this.a(this.a16(var2));
         }

         return var3;
      }
   }

   private final int a(short[] var1) {
      int var2 = 0;

      for (byte var3 = 0; var3 < var1.length; var3 += 2) {
         short var4 = var1[var3];
         short var5 = var1[var3 + 1];
         var2 += this.a2(var4, var5);
      }

      return var2;
   }

   private void a(Graphics var1, int var2, int[] var3) {
      GameCanvas var4 = this.canvas;
      int var5 = var3[0];
      int var6 = var3[1];
      int var7 = var3[2];
      int var8 = var3[3];
      int var9 = var3[4];
      int var10 = 0;
      switch (var2) {
         case 0:
            if (var1 != null) {
               var1.setColor(0);
               var1.drawRect(var5, var6, var7, var8);
            }
            break;
         case 1:
         case 2:
         case 3:
         case 4:
            if (var2 == 4) {
               var7 = this.f;
               var5 = this.canvas.getWidth() - var7 >> 1;
            }

            if (var1 != null) {
               this.a2(var1, var5, var6, var7, var8, var9, var2);
            }

            var5 += 5;
            var6 += 22;
            var7 -= 10;
            var10 = 27;
            break;
         case 5:
            if (var1 != null) {
               f(var1, var5, var6, var7, var8);
            }
            break;
         case 6:
            if (var1 != null) {
               this.g(var1, var5, var6, var7, var8);
            }

            var5 += 8;
            var6 += 8;
            var7 -= 16;
            var10 = 16;
            break;
         case 7:
            if (var1 != null) {
               this.j(var1, var5, var6, var7, var8);
            }

            var5 += 4;
            var6 += 4;
            var7 -= 8;
            var10 = 8;
         case 8:
         default:
            break;
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
            SdkString var20;
            if (var1 != null && (var20 = this.a4(var9)) != null) {
               int var24 = var5;
               int var27 = var6 + 1;
               byte var28 = 20;
               if (var2 == 10 || var2 == 13) {
                  var24 = var5 + var7;
                  var28 = 24;
               } else if (var2 == 11 || var2 == 14) {
                  var24 = var5 + (var7 >> 1);
                  var28 = 17;
               }

               int var30 = var2 != 12 && var2 != 13 && var2 != 14 ? 1 : 0;
               var4.a(var20, var30, var24, var27, var28);
            }
            break;
         case 15:
            var10 = this.i;
            var6 += var10;
            break;
         case 16:
         case 17:
            var10 = Math.max(var9, var4.c()) * this.e;
            if (var1 != null) {
               if (var2 == 17) {
                  var10 = var8;
               }

               var4.b2(1, var5 + (var7 >> 1), var6 + (var10 >> 1), 3);
            }

            var6 += var10;
            break;
         case 18:
            var10 = 1;
            if (var1 != null) {
               int var23 = var5 + 1;
               int var26 = var7 - 3;
               SpriteRenderer.setColor(var1, var9);
               var1.fillRect(var23, var6, var26, 1);
            }

            var6++;
            break;
         case 19:
            int var19 = var4.getWidth();
            int var22 = var6 + 3;
            int var14 = var4.getHeight() - 15 - 3 - var22;
            if (var1 != null) {
               SpriteRenderer.setColor(var1, 77);
               var1.fillRect(0, var6, var19, 3);
               SpriteRenderer.setColor(var1, 76);
               var1.fillRect(0, var22, var19, var14);
               var6 = var22;
               var10 = var8 - var14;
            } else {
               var10 = var8;
               var6 = var22;
            }

            var5 = 0;
            var7 = var19;
            break;
         case 20:
            if (var1 != null) {
               int var18 = this.i + 2;
               int var21 = var4.getWidth();
               int var25 = var6 - 2;
               SpriteRenderer.setColor(var1, 77);
               var1.fillRect(0, var25, var21, 1);
               var1.fillRect(0, var25 + var18 - 1, var4.getWidth(), 1);
               SpriteRenderer.setColor(var1, 1);
               var1.fillRect(0, var25 + 1, var21, var18 - 2);
               int var29 = var5 + var7 - 25 + 3;
               int var31 = var5 + var7 + 3;
               SpriteRenderer.drawFrame(var1, 660, 0, var29, var6);
               SpriteRenderer.drawFrame(var1, 659, 0, var31, var6);
            }
            break;
         case 21:
            int var17 = this.i + 8;
            if (var1 != null) {
               int var12 = var7 * 4 / 5;
               int var13 = var5 + (var7 - var12 >> 1);
               this.g(var1, var13, var6, var12, var17);
               int var15 = var13 + (var12 >> 1);
               int var16 = var6 + (var17 >> 1) + 2;
               var4.a(this.a4(var9), 0, var15, var16, 3);
            }

            var10 = var17;
            var6 += var17;
            break;
         case 22:
            if (var1 != null) {
               var10 = var8;
               var6 += var8;
            }
            break;
         case 23:
            var5 += var9;
            var7 -= var9 << 1;
            var6 += var9;
            var10 = var9 << 1;
            break;
         case 24:
            var5 += var9;
            var7 -= var9 << 1;
            break;
         case 25:
            var6 += var9;
            var10 = var9 << 1;
            break;
         case 26:
            var10 = var9;
            var6 += var9;
            break;
         case 27:
            if (var1 != null) {
               this.e(var1, var5, var6, var7, var8, var9);
               var10 = var8;
               var6 += var8;
            }
            break;
         case 28:
            this.b(var1, var3);
            var10 = var3[3];
            var6 += var10;
            break;
         case 29:
            short[] var11 = this.a16(var9);
            this.a(var1, var11, var3);
            var10 = var3[3];
            var6 += var10;
            break;
         case 30:
            var10 = ((TitleScreen)this).a(var1, var5, var6, var7);
            var6 += var10;
            break;
         case 31:
         case 33:
         case 36:
            var10 = this.d(var1, var5, var6, var7, var2);
            var6 += var10;
            break;
         case 32:
            var10 = ((GameScreen)this).a(var1, var5, var6, var7);
            var6 += var10;
            break;
         case 34:
            var10 = ((GameScreen)this).a(var1, var5, var6, var7, var9);
            var6 += var10;
            break;
         case 35:
            var10 = ((GameScreen)this).b(var1, var5, var6, var7);
            var6 += var10;
            break;
         case 37:
            ((GameScreen)this).a(var1, var3);
            return;
         case 38:
            var10 = ((GameScreen)this).c(var1, var5, var6, var7);
            var6 += var10;
      }

      var3[0] = var5;
      var3[1] = var6;
      var3[2] = var7;
      var3[3] = var10;
   }

   public final void a2(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      GameCanvas var8 = this.canvas;
      if (var7 == 4) {
         this.i(var1, var2, var3, var4, var5);
      }

      int var9 = var1.getClipX();
      int var10 = var1.getClipY();
      int var11 = var1.getClipWidth();
      int var12 = var1.getClipHeight();
      int var13 = SpriteRenderer.frameWidth(582, 0);
      int var14 = SpriteRenderer.frameHeight(582, 0);
      SpriteRenderer.drawFrame(var1, 582, 0, var2, var3);
      SpriteRenderer.drawFrame(var1, 582, 2, var2 + var4 - var13, var3);
      var1.setClip(var2 + var13, var10, var4 - (var13 << 1), var12);
      SpriteRenderer.drawFrame(var1, 582, 1, var2 + var13, var3);
      var1.setClip(var9, var10, var11, var12);
      if (var7 != 4) {
         SpriteRenderer.setColor(var1, 0);
         var1.fillRect(var2, var3 + var14, 1, var5 - var14);
         var1.fillRect(var2 + var4 - 1, var3 + var14, 1, var5 - var14);
         var1.fillRect(var2, var3 + var5 - 1, var4, 1);
         SpriteRenderer.setColor(var1, 76);
         var1.fillRect(var2 + 1, var3 + var14, var4 - 2, var5 - var14 - 1);
      }

      if (var6 != -1) {
         int var15 = var2 + (var4 >> 1);
         int var16 = var3 + 5;
         var8.a(this.a4(var6), 0, var15, var16, 17);
      }

      if (var7 == 2) {
         int var19 = var3 + 3;
         int var21 = var2 + 4;
         int var17 = var2 + var4 - 4;
         if (GameCanvas.b) {
            var21 = var2 + 56;
            var17 = var2 + var4 - 56;
         }

         if (((GameScreen)this).c9 != 0 || GameScreen.a5 != 11 && GameScreen.a5 != 10) {
            this.leftArrowAnim.draw(var1, var21, var19);
            this.rightArrowAnim.draw(var1, var17, var19);
         }
      }

      if (var7 == 3) {
         var6 = this.a3(var6);
         short var20 = -1;
         if (var6 == 209) {
            var20 = 666;
         } else if (var6 == 205) {
            var20 = 668;
         } else if (var6 == 208) {
            var20 = 667;
         } else if (var6 == 204) {
            var20 = 669;
         }

         if (var20 != -1) {
            int[] var22 = this.a2;
            SpriteRenderer.hitTestMirrored(this.a2, var20, 0, 0);
            var1.setClip(var2 + var22[0], var3 + var22[1], var22[2], var22[3]);
            SpriteRenderer.drawFrame(var1, var20, 0, var2, var3);
            var1.setClip(var2 + var4 - var22[2] + var22[0], var3 + var22[1], var22[2], var22[3]);
            SpriteRenderer.drawFrame(var1, var20, 0, var2 + var4 - var22[2], var3);
            var1.setClip(var9, var10, var11, var12);
         }
      }
   }

   public static void f(Graphics var0, int var1, int var2, int var3, int var4) {
      SpriteRenderer.setColor(var0, 77);
      var0.fillRect(var1, var2, var3, 6);
      var0.fillRect(var1, var2 + var4 - 6, var3, 6);
      var0.fillRect(var1, var2 + 6, 6, var4 - 12);
      var0.fillRect(var1 + var3 - 6, var2 + 6, 6, var4 - 12);
   }

   public static void b2(Graphics var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = var0.getClipX();
      int var8 = var0.getClipY();
      int var9 = var0.getClipWidth();
      int var10 = var0.getClipHeight();
      int var11 = SpriteRenderer.frameWidth(var5, 0);
      int var12 = SpriteRenderer.frameHeight(var5, 0);
      int var13 = var1 + var11;
      int var14 = var1 + var3 - var11;
      int var15 = var3 - (var11 << 1);
      int var16 = var2 + var12;
      int var17 = var2 + var4 - var12;
      int var18 = var4 - (var12 << 1);
      SpriteRenderer.drawFrame(var0, var5, 0, var1, var2);
      SpriteRenderer.drawFrame(var0, var5, 2, var14, var2);
      SpriteRenderer.drawFrame(var0, var5, 6, var1, var17);
      SpriteRenderer.drawFrame(var0, var5, 4, var14, var17);
      var0.setClip(var7, var16, var9, var18);
      SpriteRenderer.drawFrame(var0, var5, 7, var1, var16);
      SpriteRenderer.drawFrame(var0, var5, 3, var14, var16);
      var0.setClip(var13, var8, var15, var10);
      SpriteRenderer.drawFrame(var0, var5, 1, var13, var2);
      SpriteRenderer.drawFrame(var0, var5, 5, var13, var17);
      var0.setClip(var7, var8, var9, var10);
      if (var6 != -1) {
         SpriteRenderer.setColor(var0, var6);
         var0.fillRect(var13, var16, var15, var18);
      }
   }

   public final void g(Graphics var1, int var2, int var3, int var4, int var5) {
      b2(var1, var2, var3, var4, var5, 576, 1);
   }

   public final void h(Graphics var1, int var2, int var3, int var4, int var5) {
      b2(var1, var2, var3, var4, var5, 580, 81);
   }

   public final void a(Graphics var1, int var2, int var3, int var4, int var5, boolean var6, int var7) {
      this.a(var1, var2, var3, var4, var5, var6, this.canvas.a4(var7));
   }

   public final void a(Graphics var1, int var2, int var3, int var4, int var5, boolean var6, SdkString var7) {
      byte var8;
      if (var6) {
         var8 = 0;
         b2(var1, var2 - 1, var3 - 1, var4 + 2, var5 + 2, 578, 94);
      } else {
         var8 = 1;
         b2(var1, var2, var3, var4, var5, 579, 90);
      }

      this.canvas.a(var7, var8, var2 + (var4 >> 1), var3 + (var5 >> 1) + 2, 3);
   }

   public final void i(Graphics var1, int var2, int var3, int var4, int var5) {
      b2(var1, var2, var3, var4, var5, 577, 76);
   }

   public static void c(Graphics var0, int var1, int var2, int var3, int var4, int var5) {
      SpriteRenderer.setColor(var0, var5);
      var0.drawRect(var1, var2, var3 - 1, var4 - 1);
      SpriteRenderer.setColor(var0, 73);
      var0.fillRect(var1 + 1, var2 + 1, var3 - 2, 1);
      var0.fillRect(var1 + 1, var2 + 2, 1, var4 - 3);
      SpriteRenderer.setColor(var0, 74);
      var0.fillRect(var1 + 2, var2 + 2, var3 - 3, var4 - 3);
   }

   public final void j(Graphics var1, int var2, int var3, int var4, int var5) {
      c(var1, var2, var3, var4, var5, 77);
   }

   public final void k(Graphics var1, int var2, int var3, int var4, int var5) {
      c(var1, var2, var3, var4, var5, 0);
   }

   public final void b(Graphics var1, int[] var2) {
      GameCanvas var3 = this.canvas;
      int var4 = this.canvas.b2(1);
      int var5 = Math.max(5, Math.min(var3.c(), this.a3));
      int var6 = var4 * var5 + 16;
      if (var1 == null) {
         var2[3] = var6;
      } else {
         int var7 = var2[0];
         int var8 = var2[2] - b();
         int var9 = var2[1];
         int var10 = var7 + 8;
         int var11 = var9 + 8;
         int var12 = var7 + var8;
         this.g(var1, var7, var9, var8, var6);
         var3.a(1, var10, var11, 20, this.m, this.a3);
         this.a(var1, var12, var9, var6, this.m, this.a3, var3.c(), true);
      }
   }

   public final int b(Graphics var1, int var2, int var3, int var4, int var5) {
      GameCanvas var6 = this.canvas;
      int var7 = var4 - 3 - 3;
      int var9 = 6 + var7 + 3;
      this.i(var1, var2, -3, var3, var9);
      int var11 = var3 - 6;
      int var12 = var2 + (var3 - var11 >> 1);
      this.h(var1, var12, 3, var11, var7);
      var6.a2(var5, 0, var12 + (var11 >> 1), 3 + (var7 >> 1) + 2, 3);
      return var9 - 3;
   }

   public final int d(Graphics var1, int var2, int var3, int var4) {
      return this.b(var1, var2, var3, 25, var4);
   }

   public final int c(Graphics var1, int var2, int var3, int var4, int var5) {
      GameCanvas var6 = this.canvas;
      this.i(var1, var2, -3, var3, 47);
      int var9 = var3 - 6;
      int var10 = var2 + (var3 - var9 >> 1);
      this.h(var1, var10, 3, var9, 19);
      var6.a2(var4, 0, var10 + (var9 >> 1), 14, 3);
      this.g(var1, var10, 25, var9, 16);
      if (var5 != -1) {
         var6.a2(var5, 1, var10 + (var9 >> 1), 34, 3);
      }

      return 44;
   }

   public final void c(Graphics var1, int var2, int var3) {
      this.a(var1, var2, var3, 0, false, 0);
   }

   public final void a(Graphics var1, int var2, int var3, int var4, boolean var5, int var6) {
      GameCanvas var7 = this.canvas;
      Neighborhood var8 = this.canvas.a23;
      int var9 = this.canvas.a23.i(var4, 0) == 0 ? (var5 ? 595 : 594) : (var5 ? 593 : 592);
      int var10 = var8.i(var4, 2);
      SpriteRenderer.setRecolorIndex(var6);
      SpriteRenderer.drawFrame(var1, var9, var10, var2, var3 + 0);
      SpriteRenderer.setRecolorIndex(0);
   }

   public static void d(Graphics var0, int var1, int var2, int var3, int var4, int var5) {
      int var7 = var4 - var5 - var5;
      var0.fillRect(var1, var2, var3, var5);
      var0.fillRect(var1, var2 + var4 - var5, var3, var5);
      var0.fillRect(var1, var2 + var5, var5, var7);
      var0.fillRect(var1 + var3 - var5, var2 + var5, var5, var7);
   }

   public final SdkString a4(int var1) {
      GameCanvas var2 = this.canvas;
      if (var1 >= -12) {
         return var2.a4(var1);
      } else {
         int var3;
         return (var3 = this.a3(var1)) == 24 ? null : var2.a4(var3);
      }
   }

   private int a3(int var1) {
      return this.b[-13 - var1];
   }

   public final short[] a16(int var1) {
      return this.canvas.a16(var1);
   }

   public final void d(int var1, int var2, int var3) {
      this.j = var1;
      this.b3 = var2;
      this.k = 0;
      this.c2 = -1;
      this.l = var3;
   }

   public final boolean a5(int var1, int var2) {
      if (this.c2 != -1) {
         int var3 = this.l;
         int var4;
         if (this.l == -1) {
            var4 = this.k;
         } else {
            var4 = this.l;
         }

         if (this.matchesInput(var1, var2, 16640, 0)) {
            var4++;
         } else if (this.matchesInput(var1, var2, 8196, 0)) {
            var4--;
         }

         if (this.l == -1) {
            this.k = Math.max(0, Math.min(var4, this.j - this.c2));
         } else {
            this.l = Math.max(0, Math.min(var4, this.j - 1));
         }

         return var3 != this.l;
      } else {
         return false;
      }
   }

   public final void e(Graphics var1, int var2, int var3, int var4, int var5, int var6) {
      GameCanvas var7 = this.canvas;
      int var8 = var4 - b();
      int var9 = var2 + 2;
      int var10 = var3 + 2;
      int var11 = var8 - 2 - 1;
      int var12 = var5 - 2 - 1;
      this.g(var1, var2, var3, var8, var5);
      int var13 = var12 / this.b3;
      int var14 = this.j;
      this.c2 = var13;
      if (this.l != -1) {
         int var15 = this.l;
         int var16 = this.k;
         if (var15 < var16) {
            var16 = var15;
         } else if (var15 >= var16 + var13) {
            var16 = Math.max(0, var15 - var13 + 1);
         }

         this.k = var16;
      }

      int var22 = var2 + var8;
      this.a(var1, var22, var3, var5, this.k, var13, var14, true);
      int var23 = var1.getClipX();
      int var17 = var1.getClipY();
      int var18 = var1.getClipWidth();
      int var19 = var1.getClipHeight();
      var1.setClip(var9, var10, var11, var12);
      TitleScreen var20 = var7.a9();
      GameScreen var21 = var7.a6();
      switch (var6) {
         case 0:
            var20.a2(var1, var9, var10, var11, var12);
            break;
         case 1:
            var20.b2(var1, var9, var10, var11, var12);
            break;
         case 2:
         case 4:
         case 8:
            this.h(var1, var9, var10, var11, var12, var6);
            break;
         case 3:
            var21.c2(var1, var9, var10, var11, var12);
            break;
         case 5:
         case 6:
         case 9:
            var21.a(var1, var9, var10, var11, var12, var6);
            break;
         case 7:
            var21.b2(var1, var9, var10, var11, var12);
            break;
         case 10:
            var21.a2(var1, var9, var10, var11, var12);
      }

      var1.setClip(var23, var17, var18, var19);
   }

   public static void a2(Graphics var0, int var1, int var2, int var3) {
      SpriteRenderer.setColor(var0, 75);
      var0.fillRect(var1, var2, var3, 1);
   }

   public final void a(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (var8 == this.k) {
         f(var1, var2, var3, var4, var5, var7);
      } else if (var3 + var5 >= var6) {
         g(var1, var2, var3, var4, var6 - var3, var7);
      } else {
         a(var1, var2, var3, var4, var5, var7);
      }
   }

   private static void a(Graphics var0, int var1, int var2, int var3, int var4, int var5) {
      SpriteRenderer.setColor(var0, var5);
      var0.fillRect(var1, var2 + 1, var3, var4 - 1);
   }

   private static void f(Graphics var0, int var1, int var2, int var3, int var4, int var5) {
      SpriteRenderer.setColor(var0, var5);
      var0.fillRect(var1 + 1, var2, var3 - 2, 1);
      var0.fillRect(var1, var2 + 1, var3, var4 - 1);
   }

   private static void g(Graphics var0, int var1, int var2, int var3, int var4, int var5) {
      SpriteRenderer.setColor(var0, var5);
      var0.fillRect(var1, var2 + 1, var3, var4 - 1 - 1);
      var0.fillRect(var1 + 1, var2 + var4 - 1, var3 - 2, 1);
   }

   public final void b(Graphics var1, int var2) {
      GameCanvas var3 = this.canvas;
      int var4 = this.canvas.a23.m();
      SdkString var5 = var3.a2();
      var3.e3(551);
      var3.f3(var3.g());
      var3.e3(27);
      var3.f3(var4);
      var3.a2(-9, var5);
      this.setValue(1, -9);
      var5 = var3.a2();
      var3.e3(550);
      var3.f3(var3.f());
      var3.e3(27);
      var3.f3(var4);
      var3.a2(-8, var5);
      this.setValue(2, -8);
      this.a(var1, var2);
   }

   public final void q2() {
      this.d(this.canvas.a23.m(), 27, -1);
   }

   private int b2(int var1) {
      GameCanvas var2 = this.canvas;
      int var3 = this.canvas.a23.m();
      int var4 = var2.f();
      int var5 = var2.g();
      int var6 = 0;
      int var7 = 0;

      for (int var8 = 0; var8 < var3; var8++) {
         if (!var2.d4(var8) && var1 >= var4) {
            return var8;
         }

         if (var2.d4(var8)) {
            if (var2.e4(var8)) {
               if (var6 == var1) {
                  return var8;
               }

               var6++;
            } else {
               if (var7 == var1 - var5 && var1 >= var5) {
                  return var8;
               }

               var7++;
            }
         }
      }

      return -1;
   }

   private void h(Graphics var1, int var2, int var3, int var4, int var5, int var6) {
      GameCanvas var7 = this.canvas;
      Neighborhood var8 = this.canvas.a23;
      int var9 = var2 + 3;
      int var10 = SpriteRenderer.frameHeight(565, 0);
      int var11 = 13 - (var10 >> 1);
      int var12;
      int var13 = (var12 = var2 + 3 + var10 + 3) + 3;
      int var14 = var2 + var4 - var13 - 3;
      SpriteRenderer.setColor(var1, 75);
      var1.fillRect(var12, var3, 1, var5);
      int var15 = var3;
      int var16 = 0;
      int var17 = this.k;

      while (var15 < var3 + var5 && var17 < this.j) {
         int var19;
         short var20;
         byte var21;
         label39: {
            byte var10000;
            label38: {
               var21 = 0;
               if (var6 == 2) {
                  int var18 = this.b2(var17);
                  if (!var7.d4(var18)) {
                     var19 = 549;
                     var20 = 565;
                     var10000 = 2;
                     break label38;
                  }

                  var19 = var8.x(var18);
                  if (var7.e4(var18)) {
                     var20 = 565;
                     var10000 = 1;
                     break label38;
                  }

                  var20 = 565;
               } else {
                  if (var6 == 8) {
                     int var23 = var8.w(var17);
                     var19 = var8.x(var23);
                     var20 = 589;
                     if (var23 == -1) {
                        var20 = 196;
                     }
                     break label39;
                  }

                  int var22 = var8.v(var17);
                  var19 = var8.x(var22);
                  var20 = 565;
                  if (var8.c5(var17)) {
                     var10000 = 1;
                     break label38;
                  }
               }

               var10000 = 0;
            }

            var21 = var10000;
         }

         var7.a(var16, var19, 1, var14, var13, var15 + 13, 6);
         SpriteRenderer.drawFrame(var1, var20, var21, var9, var15 + var11);
         var16++;
         var15 += 27;
         var17++;
         a2(var1, var2, var15, var4);
      }
   }

   public final int d(Graphics var1, int var2, int var3, int var4, int var5) {
      int var6 = 8 + 6 * this.e;
      if (var1 != null) {
         GameCanvas var7 = this.canvas;
         Neighborhood var8 = this.canvas.a23;
         int var9 = 0;
         int var10 = 5;
         if (var5 == 36) {
            var9 = ((GameScreen)this).a();
            var10 = var8.X(var9);
         }

         this.j(var1, var2, var3, var4, var6);
         int var11 = var2 + 4;
         int var12 = var3 + 4;
         int var13 = var11 + 12;
         int var14 = var12 + this.e + 2;
         int var15 = this.e - 2;
         int var16 = var4 >> 1;
         short var17 = 644;
         if (var5 == 31) {
            var17 = 745;
         }

         var7.a2(var17, 1, var11, var12, 20);
         var7.a2();
         Object var18 = null;
         int var19 = var14;

         for (int var20 = 0; var20 < var10; var20++) {
            var18 = var7.a2();
            var7.f3(var20 + 1);
            var7.e3(29);
            var7.a((SdkString)var18, 1, var11, var19, 20);
            int var21 = var8.k(var9, var20);
            if (var5 == 36 && !var8.d3(var9, var21)) {
               var21 = -1;
            }

            if (var21 != -1) {
               short var22 = var8.f2(var21);
               var7.a2(var22, 1, var13, var19, 20);
            } else {
               SpriteRenderer.setColor(var1, 0);
               var1.fillRect(var13, var19 + var15, var4 - var16, 1);
            }

            var19 += this.e;
         }

         if (var5 == 36) {
            ((GameScreen)this).d2(var1, var2, var3, var4, var6);
         } else if (var5 == 33) {
            ((GameScreen)this).e(var1, var2, var3, var4, var6);
         }
      }

      return var6;
   }

   public final void l2(int var1, int var2) {
      this.setValue(0, var2);
      this.canvas.a(var1, 1, this.g);
   }

   public final void b(Graphics var1) {
      this.a(var1, 1);
   }

   public final void m2(int var1, int var2) {
      this.setValue(0, var2);
      this.canvas.a(var1, 1, this.d);
      this.m = 0;
   }

   public final void c(Graphics var1) {
      this.a(var1, 2);
   }

   public final void j2(int var1) {
      if (this.matchesInput(var1, 0, 8196, 0)) {
         this.m = Math.max(0, this.m - 1);
      }

      if (this.matchesInput(var1, 0, 16640, 0) && this.canvas.c() > this.m + this.a3) {
         this.m++;
      }
   }

   public final void a(Graphics var1, short[] var2, int var3) {
      this.a(var1, var2, var3, null);
   }

   public final void a(Graphics var1, short[] var2, int var3, SdkString[] var4) {
      GameCanvas var5 = this.canvas;
      Lot var6 = this.canvas.a24;
      short var7 = var2[0];
      short var8 = var2[3];
      short var9 = var2[2];
      short var10;
      int var11 = (var10 = var2[4]) != -1 ? Math.min((int)var10, (int)var7) : var7;
      int var12 = var5.getWidth();
      int var13 = var5.getHeight();
      int var14 = (var3 & 1) != 0 ? 4 : 0;
      int var15;
      boolean var16;
      if (var16 = (var3 & 6) != 0) {
         var15 = var12 >> 1;
      } else if ((var3 & 8) != 0) {
         var15 = var12 >> 1;
      } else {
         var15 = var12 * 4 / 5;
      }

      int var17;
      int var18 = (var17 = (var3 & 16) == 0 ? 14 : 0) + 7 + 15 * var11 + 1 + (var14 << 1);
      int var19;
      if ((var3 & 2) != 0) {
         var19 = var6.a6 + 3;
      } else if ((var3 & 4) != 0) {
         var19 = var6.a6 + var5.getWidth() - var15 - 3;
      } else if ((var3 & 8) != 0) {
         var19 = 3;
      } else {
         var19 = var12 - var15 >> 1;
      }

      int var20;
      if (var16) {
         var20 = (var6.b5 + var6.d6 - var18 >> 1) - ((var3 & 2) != 0 ? 15 : 0);
      } else {
         var20 = var13 - 15 - var18 >> 1;
      }

      if (var14 != 0) {
         boolean var21 = false;
         byte var22 = 61;
         if ((var3 & 32) != 0) {
            var22 = 76;
         }

         SpriteRenderer.setColor(var1, 77);
         var1.drawRect(var19, var20, var15, var18);
         SpriteRenderer.setColor(var1, var22);
         d(var1, var19 + 1, var20 + 1, var15 - 1, var18 - 1, var14 - 1);
         var19 += var14;
         var20 += var14;
         var15 -= var14 << 1;
         var18 -= var14 << 1;
      }

      boolean var35 = var11 != var7;
      int var36 = var19 + 3;
      int var23 = var15 - 6 + (var35 ? -9 : 1);
      int var24 = var36 + (var23 >> 1);
      SpriteRenderer.setColor(var1, 76);
      var1.fillRect(var19, var20, var15, var18);
      SpriteRenderer.setColor(var1, 77);
      var1.drawRect(var19, var20, var15, var18);
      if ((var3 & 16) == 0) {
         var1.fillRect(var19, var20 + var17, var15, 1);
         var5.a(0, var1, var2[1], 0, var19 + 3, var20 + 0, var15 - 6, 17);
      }

      if (var35) {
         int var25 = var19 + var15 - b();
         int var26 = var20 + var17 + 2;
         int var27 = var18 - var17 - 4;
         this.a(var1, var25, var26, var27, var8, var11, var7, false);
      }

      int var37 = var20 + var17 + 7;

      for (int var38 = 0; var38 < var11; var38++) {
         int var39 = var8 + var38;
         byte var28 = 1;
         short var29 = -1;
         if (var4 == null) {
            var29 = var2[5 + var39];
         }

         if (var9 == var39 && var29 != 274 && var29 != 275) {
            var28 = 0;
            if ((var3 & 32) != 0) {
               SpriteRenderer.setColor(var1, 1);
               var1.fillRect(var36, var37, var23, 15);
            } else if (var16) {
               SpriteRenderer.setColor(var1, 77);
               var1.fillRect(var36, var37, var23, 15);
            } else {
               l(var1, var36, var37, var23, 15);
            }
         }

         int var30;
         if ((var30 = var5.c(var29)) != -1) {
            int var31 = var5.a2(var29, var28) + var5.a3(var28);
            int var32 = var5.a2(var30, var28);
            int var33;
            int var34 = (var33 = var24 - (var31 + var32 >> 1)) + var31;
            var5.a2(var29, var28, var33, var37, 20);
            var5.a2(var30, var28, var34, var37, 20);
         } else if (var29 != -1) {
            if (var9 == var39) {
               var5.a(1, var1, var29, var28, var36, var37, var23, 17);
            } else {
               var5.a2(var1, var29, var28, var36, var37, var23, 17);
            }
         } else {
            SdkString var40 = var4[var39];
            if (var9 == var39) {
               var5.a(1, var1, var40, var28, var36, var37, var23, 17);
            } else {
               var5.a(var1, var40, var28, var36, var37, var23, 17);
            }
         }

         var37 += 15;
      }
   }

   public static void l(Graphics var0, int var1, int var2, int var3, int var4) {
      SpriteRenderer.setColor(var0, 62);
      var0.fillRect(var1, var2, var3, 1);
      SpriteRenderer.setColor(var0, 63);
      var0.fillRect(var1, var2 + 1, var3, var4 - 3);
      SpriteRenderer.setColor(var0, 64);
      var0.fillRect(var1, var2 + var4 - 2, var3, 1);
   }

   public final void a(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      var2 += 3;
      int var9 = SpriteRenderer.frameHeight(599, 0);
      int var10 = var3 + var9 + 1 + 2;
      int var11 = var4 - (var9 + 1 + 2 << 1);
      int var12;
      int var13;
      byte var14;
      int var15;
      int var16;
      if (var6 >= var7) {
         var12 = 0;
         var13 = 0;
         var14 = 0;
         var15 = var10;
         var16 = var11;
      } else {
         var12 = var5 > 0 ? 1 : 0;
         var13 = var5 + var6 < var7 ? 1 : 0;
         var14 = 2;
         var16 = Math.max(18, var11 * var6 / var7);
         var15 = var10 + (var11 - var16) * var5 / (var7 - var6);
      }

      int var17 = var3 + 2;
      int var18 = var3 + var4 - 2 - var9;
      SpriteRenderer.drawFrame(var1, 599, var12, var2, var17);
      SpriteRenderer.drawFrame(var1, 598, var13, var2, var18);
      if (var14 == 2) {
         a2(var1, var2, var10, var11, 1);
      }

      a2(var1, var2, var15, var16, var14);
      if (var8) {
         SpriteRenderer.setColor(var1, 77);
         int var19 = var2 - 2;
         int var20 = SpriteRenderer.frameWidth(602, 0) + 4;
         var1.fillRect(var19, var3, var20, 1);
         var1.fillRect(var19, var3 + var4 - 1, var20, 1);
         var1.fillRect(var19 + var20, var3, 1, var4);
      }
   }

   private static void a2(Graphics var0, int var1, int var2, int var3, int var4) {
      int var5 = SpriteRenderer.frameHeight(601, var4);
      int var6 = SpriteRenderer.frameHeight(600, var4);
      int var7 = var0.getClipX();
      int var8 = var0.getClipY();
      int var9 = var0.getClipWidth();
      int var10 = var0.getClipHeight();
      var0.setClip(var7, var2, var9, var3);
      SpriteRenderer.drawFrame(var0, 601, var4, var1, var2);
      SpriteRenderer.drawFrame(var0, 600, var4, var1, var2 + var3 - var6);
      var2 += var5;
      var3 -= var5 + var6;
      var0.setClip(var7, var2, var9, var3);
      SpriteRenderer.drawFrame(var0, 602, var4, var1, var2);
      var0.setClip(var7, var8, var9, var10);
   }

   public static int b() {
      return SpriteRenderer.frameWidth(602, 0) + 6;
   }

   public static int a(int var0, int var1, int var2, int var3) {
      if (var0 != var1) {
         int var4;
         int var5;
         if ((var5 = (var4 = var1 - var0) * var2 >> var3) == 0) {
            var5 = FixedMath.sign(var4);
         } else if (var4 > 0 && var0 + var5 > var1 || var4 < 0 && var0 + var5 < var1) {
            var5 = var4;
         }

         var0 += var5;
      }

      return var0;
   }
}
