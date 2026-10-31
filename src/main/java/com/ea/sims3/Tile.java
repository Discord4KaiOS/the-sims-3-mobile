package com.ea.sims3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class Tile {
   public byte[] a;
   public short[] a2;
   public short[] b;
   private int e;
   private int f;
   private int g;
   private int h;
   public int a3;
   public int b2;
   private Image a4;
   private boolean a5;
   public int c;
   public int d;
   private Image b3;
   private Graphics a6;
   private boolean b4;
   private boolean c2;
   private int i;
   private int j;
   private int k;
   private int l;
   public Tile a7;

   public static int tileWidth() {
      return 16;
   }

   public static int tileHeight() {
      return 8;
   }

   public static int toWorld16(int var0) {
      return var0 << 20;
   }

   public static int toWorld8(int var0) {
      return var0 << 19;
   }

   public final int frameCount() {
      return this.a2 != null ? this.a2.length / 3 : 0;
   }

   public final int rowCount() {
      return this.b != null ? this.b.length / 3 : 0;
   }

   public Tile() {
      this.a2 = null;
      this.a4 = null;
      this.a5 = false;
      this.e = 0;
      this.f = 0;
      this.g = 0;
      this.h = 0;
      this.a3 = 0;
      this.b2 = 0;
   }

   public Tile(int var1, Image var2) {
      this.a2 = null;
      this.a4 = null;
      this.a5 = false;
      this.e = 0;
      this.f = 0;
      this.g = 0;
      this.h = 0;
      this.a3 = 0;
      this.b2 = 0;
      this.a13(var1);
      if (var2 != null) {
         this.a4 = var2;
      }
   }

   public Tile(int var1, int var2, Image var3) {
      this.a4 = var3;
      this.c = var1;
      this.d = var2;
      int var6;
      byte[] var7 = new byte[var6 = var1 * var2];

      for (int var8 = 0; var8 < var6; var8++) {
         var7[var8] = 0;
      }

      this.a = var7;
      this.a2 = null;
      this.a5 = true;
   }

   public final void a2(int var1, int var2, int var3, int var4) {
      if (this.b4 && (this.g != var3 || this.h != var4)) {
         this.b3 = null;
         this.b3 = Image.createImage(var3, var4);
         this.a6 = this.b3.getGraphics();
         this.c2 = true;
      }

      this.e = var1;
      this.f = var2;
      this.g = var3;
      this.h = var4;
   }

   public final void draw(Graphics var1) {
      int var2 = var1.getClipX();
      int var3 = var1.getClipY();
      int var4 = var1.getClipWidth();
      int var5 = var1.getClipHeight();
      int var6 = this.a3 >> 16;
      int var7 = this.b2 >> 16;
      if (this.b4) {
         this.a(var1, var6, var7);
      } else {
         int var8 = var2;
         int var9 = var3;
         int var10 = var4;
         int var11 = var5;
         if (var2 < this.e) {
            var8 = this.e;
         }

         if (var3 < this.f) {
            var9 = this.f;
         }

         if (var8 + var4 > this.e + this.g) {
            var10 = var4 - (var8 + var4 - (this.e + this.g));
         }

         if (var9 + var5 > this.f + this.h) {
            var11 = var5 - (var9 + var5 - (this.f + this.h));
         }

         this.a2(var1, var6, var7, var8, var9, var10, var11);
         if (this.a7 != null) {
            this.a7.a2(var1, var6, var7, var8, var9, var10, var11);
         }
      }

      var1.setClip(var2, var3, var4, var5);
   }

   private boolean a13(int var1) {
      System.gc();
      if (this.a5) {
         return false;
      } else {
         InputStream var3 = GameCanvas.a7().a6.openStream(var1);
         System.gc();
         DataInputStream var4 = new DataInputStream(var3);

         try {
            var4.readUnsignedByte();
            int var5 = var4.readUnsignedByte();
            int var6 = var4.readUnsignedByte();
            var4.readUnsignedByte();
            var4.readUnsignedByte();
            this.c = var5;
            this.d = var6;
            System.gc();
            byte[] var7 = new byte[var5 * var6];

            for (int var8 = 0; var8 < var5 * var6; var8++) {
               var7[var8] = var4.readByte();
            }

            this.a = var7;
            int var13;
            if ((var13 = var4.readUnsignedByte()) > 0) {
               short[] var9 = new short[var13 * 3];

               for (int var10 = 0; var10 < var9.length; var10++) {
                  var9[var10] = var4.readShort();
               }

               this.a2 = var9;
            }

            int var14;
            if ((var14 = var4.readUnsignedByte()) > 0) {
               short[] var15 = new short[var14 * 3];

               for (int var11 = 0; var11 < var15.length; var11++) {
                  var15[var11] = var4.readShort();
               }

               this.b = var15;
            }

            var3.close();
         } catch (IOException var12) {
         }

         System.gc();
         this.a5 = true;
         return true;
      }
   }

   private void a2(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = 0;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      int var12 = 0;
      int var13 = 0;
      int var14 = var2 & 15;
      int var15 = var3 & 7;
      int var16 = 0;
      if (var14 > 0) {
         var16 = 16 - var14;
      }

      int var17 = 0;
      if (var15 > 0) {
         var17 = 8 - var15;
      }

      if (var16 > var6) {
         var16 = var6;
      } else {
         var9 = (var8 = var6 - var16) >> 4;
         var10 = var8 & 15;
      }

      if (var17 > var7) {
         var17 = var7;
      } else {
         var12 = (var11 = var7 - var17) >> 3;
         var13 = var11 & 7;
      }

      int var18;
      int var19 = var18 = (var3 >> 3) * this.c + (var2 >> 4);
      int var20 = 0;
      int var21 = 0;
      int var22 = 0;
      int var23 = var4;
      int var24 = var5;
      int var25 = this.a.length - 1;
      byte[] var26 = this.a;
      Image var27 = this.a4;
      if (var17 > 0) {
         if (var16 > 0) {
            if (var18 >= 0) {
               if (var18 > var25) {
                  return;
               }

               if ((var20 = var26[var18++] & 255) != 255) {
                  var21 = (var20 & 7) << 4;
                  var22 = var20 >> 3 << 3;
                  var1.drawRegion(var27, var21 + var14, var22 + var15, var16, var17, 0, var4, var5, 20);
               }
            }

            var23 = var4 + var16;
         }

         for (int var29 = 0; var29 < var9; var29++) {
            if (var18 >= 0) {
               if (var18 > var25) {
                  return;
               }

               if ((var20 = var26[var18++] & 255) != 255) {
                  var21 = (var20 & 7) << 4;
                  var22 = var20 >> 3 << 3;
                  var1.drawRegion(var27, var21, var22 + var15, 16, var17, 0, var23, var24, 20);
               }
            }

            var23 += 16;
         }

         if (var10 > 0 && var18 >= 0) {
            if (var18 > var25) {
               return;
            }

            if ((var20 = var26[var18] & 255) != 255) {
               var21 = (var20 & 7) << 4;
               var22 = var20 >> 3 << 3;
               var1.drawRegion(this.a4, var21, var22 + var15, var10, var17, 0, var23, var24, 20);
            }
         }

         var24 += var17;
         var18 = var19 += this.c;
      }

      for (int var62 = 0; var62 < var12; var62++) {
         var23 = var4;
         if (var16 > 0) {
            if (var18 >= 0) {
               if (var18 > var25) {
                  return;
               }

               if ((var20 = var26[var18++] & 255) != 255) {
                  var21 = (var20 & 7) << 4;
                  var22 = var20 >> 3 << 3;
                  var1.drawRegion(var27, var21 + var14, var22, var16, 8, 0, var4, var24, 20);
               }
            }

            var23 = var4 + var16;
         }

         for (int var30 = 0; var30 < var9; var30++) {
            if (var18 >= 0) {
               if (var18 > var25) {
                  return;
               }

               if ((var20 = var26[var18++] & 255) != 255) {
                  var21 = (var20 & 7) << 4;
                  var22 = var20 >> 3 << 3;
                  var1.drawRegion(var27, var21, var22, 16, 8, 0, var23, var24, 20);
               }
            }

            var23 += 16;
         }

         if (var10 > 0 && var18 >= 0) {
            if (var18 > var25) {
               return;
            }

            if ((var20 = var26[var18] & 255) != 255) {
               var21 = (var20 & 7) << 4;
               var22 = var20 >> 3 << 3;
               var1.drawRegion(var27, var21, var22, var10, 8, 0, var23, var24, 20);
            }
         }

         var24 += 8;
         var18 = var19 += this.c;
      }

      if (var13 > 0) {
         var23 = var4;
         if (var16 > 0) {
            if (var18 >= 0) {
               if (var18 > var25) {
                  return;
               }

               if ((var20 = var26[var18++] & 255) != 255) {
                  var21 = (var20 & 7) << 4;
                  var22 = var20 >> 3 << 3;
                  var1.drawRegion(var27, var21 + var14, var22, var16, var13, 0, var4, var24, 20);
               }
            }

            var23 = var4 + var16;
         }

         for (int var63 = 0; var63 < var9; var63++) {
            if (var18 >= 0) {
               if (var18 > var25) {
                  return;
               }

               if ((var20 = var26[var18++] & 255) != 255) {
                  var21 = (var20 & 7) << 4;
                  var22 = var20 >> 3 << 3;
                  var1.drawRegion(var27, var21, var22, 16, var13, 0, var23, var24, 20);
               }
            }

            var23 += 16;
         }

         if (var10 > 0 && var18 >= 0) {
            if (var18 > var25) {
               return;
            }

            if ((var20 = var26[var18] & 255) != 255) {
               var21 = (var20 & 7) << 4;
               var22 = var20 >> 3 << 3;
               var1.drawRegion(var27, var21, var22, var10, var13, 0, var23, var24, 20);
            }
         }
      }
   }

   public final void setLooping(boolean var1) {
      if (var1 && !this.b4) {
         if (this.g > 0 && this.h > 0) {
            this.b3 = Image.createImage(this.g, this.h);
            this.a6 = this.b3.getGraphics();
         }

         this.c2 = true;
         this.i = this.a3 >> 16;
         this.j = this.b2 >> 16;
         this.k = 0;
         this.l = 0;
         this.b4 = true;
      } else {
         if (!var1 && this.b4) {
            this.b3 = null;
            this.b4 = false;
         }
      }
   }

   public final void reset() {
      this.c2 = true;
   }

   private void a(Graphics var1, int var2, int var3) {
      Graphics var4 = this.a6;
      int var5 = var2 - this.i;
      int var6 = var3 - this.j;
      int var7 = this.g;
      int var8 = this.h;
      if (!this.c2 && Math.abs(var5) <= var7 && Math.abs(var6) <= var8) {
         int var9 = this.k;
         int var10 = this.l;
         int var11 = this.a(var4, var2, var9, var10, var5, this.i, this.j);
         if (this.a7 != null) {
            this.a7.a(var4, var2, var9, var10, var5, this.i, this.j);
         }

         this.k = var11;
         int var12 = this.b(var4, var3, var10, var6, this.j, var2, var11);
         if (this.a7 != null) {
            this.a7.b(var4, var3, var10, var6, this.j, var2, var11);
         }

         this.l = var12;
         int var13 = var7 - this.k;
         int var14 = var8 - this.l;
         var4.setClip(0, 0, var7, var8);
         if (var13 != 0 && var14 != 0) {
            var1.drawRegion(this.b3, this.k, this.l, var13, var14, 0, this.e, this.f, 20);
         }

         if (this.k != 0 && var14 != 0) {
            var1.drawRegion(this.b3, 0, this.l, this.k, var14, 0, this.e + var13, this.f, 20);
         }

         if (var13 != 0 && this.l != 0) {
            var1.drawRegion(this.b3, this.k, 0, var13, this.l, 0, this.e, var14 + this.f, 20);
         }

         if (this.k != 0 && this.l != 0) {
            var1.drawRegion(this.b3, 0, 0, this.k, this.l, 0, this.e + var13, var14 + this.f, 20);
         }
      } else {
         this.c2 = false;
         this.a2(var4, var2, var3, 0, 0, var7, var8);
         if (this.a7 != null) {
            this.a7.a2(var4, var2, var3, 0, 0, var7, var8);
         }

         this.k = 0;
         this.l = 0;
      }

      this.i = var2;
      this.j = var3;
   }

   private int a(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = this.g;
      int var9 = this.h;
      int var10 = var3 + var5;
      if (var5 > 0) {
         int var11 = var2 + var8 - var5;
         if (var10 > var8) {
            this.a2(var1, var11, var7, var3, var4, var8 - var3, var9 - var4);
            this.a2(var1, var11, var7 + var9 - var4, var3, 0, var8 - var3, var4);
            var11 += var8 - var3;
            this.a2(var1, var11, var7, 0, var4, var10 - var8, var9 - var4);
            this.a2(var1, var11, var7 + var9 - var4, 0, 0, var10 - var8, var4);
            var10 -= var8;
         } else {
            this.a2(var1, var11, var7, var3, var4, var5, var9 - var4);
            this.a2(var1, var11, var7 + var9 - var4, var3, 0, var5, var4);
         }
      } else if (var5 < 0) {
         if (var10 < 0) {
            int var13 = var6 - var3;
            this.a2(var1, var13, var7, 0, var4, var3, var9 - var4);
            this.a2(var1, var13, var7 + var9 - var4, 0, 0, var3, var4);
            var13 += var10;
            this.a2(var1, var13, var7, var8 + var10, var4, -var10, var9 - var4);
            this.a2(var1, var13, var7 + var9 - var4, var8 + var10, 0, -var10, var4);
            var10 += var8;
         } else {
            this.a2(var1, var2, var7, var10, var4, -var5, var9 - var4);
            this.a2(var1, var2, var7 + var9 - var4, var10, 0, -var5, var4);
         }
      }

      return var10;
   }

   private int b(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = this.g;
      int var9 = this.h;
      int var10 = var3 + var4;
      if (var4 > 0) {
         int var11 = var2 + var9 - var4;
         if (var10 > var9) {
            this.a2(var1, var6, var11, var7, var3, var8 - var7, var9 - var3);
            this.a2(var1, var6 + var8 - var7, var11, 0, var3, var7, var9 - var3);
            var11 += var9 - var3;
            this.a2(var1, var6, var11, var7, 0, var8 - var7, var10 - var9);
            this.a2(var1, var6 + var8 - var7, var11, 0, 0, var7, var10 - var9);
            var10 -= var9;
         } else {
            this.a2(var1, var6, var11, var7, var3, var8 - var7, var4);
            this.a2(var1, var6 + var8 - var7, var11, 0, var3, var7, var4);
         }
      } else if (var4 < 0) {
         if (var10 < 0) {
            int var13 = var5 - var3;
            this.a2(var1, var6, var13, var7, 0, var8 - var7, var3);
            this.a2(var1, var6 + var8 - var7, var13, 0, 0, var7, var3);
            var13 += var10;
            this.a2(var1, var6, var13, var7, var9 + var10, var8 - var7, -var10);
            this.a2(var1, var6 + var8 - var7, var13, 0, var9 + var10, var7, -var10);
            var10 += var9;
         } else {
            this.a2(var1, var6, var2, var7, var10, var8 - var7, -var4);
            this.a2(var1, var6 + var8 - var7, var2, 0, var10, var7, -var4);
         }
      }

      return var10;
   }
}
