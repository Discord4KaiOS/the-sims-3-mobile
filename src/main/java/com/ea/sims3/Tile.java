package com.ea.sims3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class Tile {
   public byte[] cellIndices;
   public short[] frames;
   public short[] rows;
   private int drawX;
   private int drawY;
   private int drawWidth;
   private int drawHeight;
   public int worldX;
   public int worldY;
   private Image sheet;
   private boolean loaded;
   public int columns;
   public int rowCountValue;
   private Image backing;
   private Graphics backingGraphics;
   private boolean backingEnabled;
   private boolean backingDirty;
   private int backingX;
   private int backingY;
   private int wrapX;
   private int wrapY;
   public Tile next;

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
      return this.frames != null ? this.frames.length / 3 : 0;
   }

   public final int rowCount() {
      return this.rows != null ? this.rows.length / 3 : 0;
   }

   public Tile() {
      this.frames = null;
      this.sheet = null;
      this.loaded = false;
      this.drawX = 0;
      this.drawY = 0;
      this.drawWidth = 0;
      this.drawHeight = 0;
      this.worldX = 0;
      this.worldY = 0;
   }

   public Tile(int var1, Image var2) {
      this.frames = null;
      this.sheet = null;
      this.loaded = false;
      this.drawX = 0;
      this.drawY = 0;
      this.drawWidth = 0;
      this.drawHeight = 0;
      this.worldX = 0;
      this.worldY = 0;
      this.load(var1);
      if (var2 != null) {
         this.sheet = var2;
      }
   }

   public Tile(int var1, int var2, Image var3) {
      this.sheet = var3;
      this.columns = var1;
      this.rowCountValue = var2;
      int var6;
      byte[] var7 = new byte[var6 = var1 * var2];

      for (int var8 = 0; var8 < var6; var8++) {
         var7[var8] = 0;
      }

      this.cellIndices = var7;
      this.frames = null;
      this.loaded = true;
   }

   public final void setBounds(int var1, int var2, int var3, int var4) {
      if (this.backingEnabled && (this.drawWidth != var3 || this.drawHeight != var4)) {
         this.backing = null;
         this.backing = Image.createImage(var3, var4);
         this.backingGraphics = this.backing.getGraphics();
         this.backingDirty = true;
      }

      this.drawX = var1;
      this.drawY = var2;
      this.drawWidth = var3;
      this.drawHeight = var4;
   }

   public final void draw(Graphics g) {
      int clipX = g.getClipX();
      int clipY = g.getClipY();
      int clipW = g.getClipWidth();
      int clipH = g.getClipHeight();
      int x = this.worldX >> 16;
      int y = this.worldY >> 16;
      if (this.backingEnabled) {
         this.drawBacking(g, x, y);
      } else {
         int dstX = clipX;
         int dstY = clipY;
         int dstW = clipW;
         int dstH = clipH;
         if (clipX < this.drawX) {
            dstX = this.drawX;
         }

         if (clipY < this.drawY) {
            dstY = this.drawY;
         }

         if (dstX + clipW > this.drawX + this.drawWidth) {
            dstW = clipW - (dstX + clipW - (this.drawX + this.drawWidth));
         }

         if (dstY + clipH > this.drawY + this.drawHeight) {
            dstH = clipH - (dstY + clipH - (this.drawY + this.drawHeight));
         }

         this.drawCells(g, x, y, dstX, dstY, dstW, dstH);
         if (this.next != null) {
            this.next.drawCells(g, x, y, dstX, dstY, dstW, dstH);
         }
      }

      g.setClip(clipX, clipY, clipW, clipH);
   }

   private boolean load(int var1) {
      System.gc();
      if (this.loaded) {
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
            this.columns = var5;
            this.rowCountValue = var6;
            System.gc();
            byte[] var7 = new byte[var5 * var6];

            for (int var8 = 0; var8 < var5 * var6; var8++) {
               var7[var8] = var4.readByte();
            }

            this.cellIndices = var7;
            int var13;
            if ((var13 = var4.readUnsignedByte()) > 0) {
               short[] var9 = new short[var13 * 3];

               for (int var10 = 0; var10 < var9.length; var10++) {
                  var9[var10] = var4.readShort();
               }

               this.frames = var9;
            }

            int var14;
            if ((var14 = var4.readUnsignedByte()) > 0) {
               short[] var15 = new short[var14 * 3];

               for (int var11 = 0; var11 < var15.length; var11++) {
                  var15[var11] = var4.readShort();
               }

               this.rows = var15;
            }

            var3.close();
         } catch (IOException var12) {
         }

         System.gc();
         this.loaded = true;
         return true;
      }
   }

   private void drawCells(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
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
      int var19 = var18 = (var3 >> 3) * this.columns + (var2 >> 4);
      int var20 = 0;
      int var21 = 0;
      int var22 = 0;
      int var23 = var4;
      int var24 = var5;
      int var25 = this.cellIndices.length - 1;
      byte[] var26 = this.cellIndices;
      Image var27 = this.sheet;
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
               var1.drawRegion(this.sheet, var21, var22 + var15, var10, var17, 0, var23, var24, 20);
            }
         }

         var24 += var17;
         var18 = var19 += this.columns;
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
         var18 = var19 += this.columns;
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

   public final void setBackingEnabled(boolean var1) {
      if (var1 && !this.backingEnabled) {
         if (this.drawWidth > 0 && this.drawHeight > 0) {
            this.backing = Image.createImage(this.drawWidth, this.drawHeight);
            this.backingGraphics = this.backing.getGraphics();
         }

         this.backingDirty = true;
         this.backingX = this.worldX >> 16;
         this.backingY = this.worldY >> 16;
         this.wrapX = 0;
         this.wrapY = 0;
         this.backingEnabled = true;
      } else {
         if (!var1 && this.backingEnabled) {
            this.backing = null;
            this.backingEnabled = false;
         }
      }
   }

   public final void invalidateBacking() {
      this.backingDirty = true;
   }

   private void drawBacking(Graphics var1, int var2, int var3) {
      Graphics var4 = this.backingGraphics;
      int var5 = var2 - this.backingX;
      int var6 = var3 - this.backingY;
      int var7 = this.drawWidth;
      int var8 = this.drawHeight;
      if (!this.backingDirty && Math.abs(var5) <= var7 && Math.abs(var6) <= var8) {
         int var9 = this.wrapX;
         int var10 = this.wrapY;
         int var11 = this.copyRows(var4, var2, var9, var10, var5, this.backingX, this.backingY);
         if (this.next != null) {
            this.next.copyRows(var4, var2, var9, var10, var5, this.backingX, this.backingY);
         }

         this.wrapX = var11;
         int var12 = this.copyColumns(var4, var3, var10, var6, this.backingY, var2, var11);
         if (this.next != null) {
            this.next.copyColumns(var4, var3, var10, var6, this.backingY, var2, var11);
         }

         this.wrapY = var12;
         int var13 = var7 - this.wrapX;
         int var14 = var8 - this.wrapY;
         var4.setClip(0, 0, var7, var8);
         if (var13 != 0 && var14 != 0) {
            var1.drawRegion(this.backing, this.wrapX, this.wrapY, var13, var14, 0, this.drawX, this.drawY, 20);
         }

         if (this.wrapX != 0 && var14 != 0) {
            var1.drawRegion(this.backing, 0, this.wrapY, this.wrapX, var14, 0, this.drawX + var13, this.drawY, 20);
         }

         if (var13 != 0 && this.wrapY != 0) {
            var1.drawRegion(this.backing, this.wrapX, 0, var13, this.wrapY, 0, this.drawX, var14 + this.drawY, 20);
         }

         if (this.wrapX != 0 && this.wrapY != 0) {
            var1.drawRegion(this.backing, 0, 0, this.wrapX, this.wrapY, 0, this.drawX + var13, var14 + this.drawY, 20);
         }
      } else {
         this.backingDirty = false;
         this.drawCells(var4, var2, var3, 0, 0, var7, var8);
         if (this.next != null) {
            this.next.drawCells(var4, var2, var3, 0, 0, var7, var8);
         }

         this.wrapX = 0;
         this.wrapY = 0;
      }

      this.backingX = var2;
      this.backingY = var3;
   }

   private int copyRows(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = this.drawWidth;
      int var9 = this.drawHeight;
      int var10 = var3 + var5;
      if (var5 > 0) {
         int var11 = var2 + var8 - var5;
         if (var10 > var8) {
            this.drawCells(var1, var11, var7, var3, var4, var8 - var3, var9 - var4);
            this.drawCells(var1, var11, var7 + var9 - var4, var3, 0, var8 - var3, var4);
            var11 += var8 - var3;
            this.drawCells(var1, var11, var7, 0, var4, var10 - var8, var9 - var4);
            this.drawCells(var1, var11, var7 + var9 - var4, 0, 0, var10 - var8, var4);
            var10 -= var8;
         } else {
            this.drawCells(var1, var11, var7, var3, var4, var5, var9 - var4);
            this.drawCells(var1, var11, var7 + var9 - var4, var3, 0, var5, var4);
         }
      } else if (var5 < 0) {
         if (var10 < 0) {
            int var13 = var6 - var3;
            this.drawCells(var1, var13, var7, 0, var4, var3, var9 - var4);
            this.drawCells(var1, var13, var7 + var9 - var4, 0, 0, var3, var4);
            var13 += var10;
            this.drawCells(var1, var13, var7, var8 + var10, var4, -var10, var9 - var4);
            this.drawCells(var1, var13, var7 + var9 - var4, var8 + var10, 0, -var10, var4);
            var10 += var8;
         } else {
            this.drawCells(var1, var2, var7, var10, var4, -var5, var9 - var4);
            this.drawCells(var1, var2, var7 + var9 - var4, var10, 0, -var5, var4);
         }
      }

      return var10;
   }

   private int copyColumns(Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = this.drawWidth;
      int var9 = this.drawHeight;
      int var10 = var3 + var4;
      if (var4 > 0) {
         int var11 = var2 + var9 - var4;
         if (var10 > var9) {
            this.drawCells(var1, var6, var11, var7, var3, var8 - var7, var9 - var3);
            this.drawCells(var1, var6 + var8 - var7, var11, 0, var3, var7, var9 - var3);
            var11 += var9 - var3;
            this.drawCells(var1, var6, var11, var7, 0, var8 - var7, var10 - var9);
            this.drawCells(var1, var6 + var8 - var7, var11, 0, 0, var7, var10 - var9);
            var10 -= var9;
         } else {
            this.drawCells(var1, var6, var11, var7, var3, var8 - var7, var4);
            this.drawCells(var1, var6 + var8 - var7, var11, 0, var3, var7, var4);
         }
      } else if (var4 < 0) {
         if (var10 < 0) {
            int var13 = var5 - var3;
            this.drawCells(var1, var6, var13, var7, 0, var8 - var7, var3);
            this.drawCells(var1, var6 + var8 - var7, var13, 0, 0, var7, var3);
            var13 += var10;
            this.drawCells(var1, var6, var13, var7, var9 + var10, var8 - var7, -var10);
            this.drawCells(var1, var6 + var8 - var7, var13, 0, var9 + var10, var7, -var10);
            var10 += var9;
         } else {
            this.drawCells(var1, var6, var2, var7, var10, var8 - var7, -var4);
            this.drawCells(var1, var6 + var8 - var7, var2, 0, var10, var7, -var4);
         }
      }

      return var10;
   }
}
