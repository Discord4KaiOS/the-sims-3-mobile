package com.ea.sims3;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class SpriteRenderer implements SpriteIndex {
   private static byte[] palette;
   private static short[][] spriteRects;
   private static int recolorIndex;
   private static Image[][] tiles = new Image[4][72];
   private static short[][] frameDurations;
   private static short[][] framePartOffsets;
   private static short[][] parts;

   private SpriteRenderer() {
   }

   public static final boolean loadPalette(ResourceLoader loader) {
      DataInputStream in = new DataInputStream(loader.openStream(4));
      palette = new byte[ResourceLoader.getSize(4)];

      try {
         in.read(palette);
         in.close();
         return true;
      } catch (Throwable ignored) {
         return false;
      }
   }

   public static final void setColor(Graphics g, int colorIndex) {
      int offset = colorIndex * 3;
      byte[] colors = palette;
      g.setColor(colors[offset] & 255, colors[offset + 1] & 255, colors[offset + 2] & 255);
   }

   public static final boolean loadSpriteRects(ResourceLoader loader) {
      try {
         short[][] rects = new short[1418][5];
         DataInputStream in = new DataInputStream(loader.openStream(31));

         for (int i = 0; i < 1418; i++) {
            short[] rect;
            (rect = rects[i])[0] = (short)in.readUnsignedByte();
            rect[1] = in.readShort();
            rect[2] = in.readShort();
            rect[3] = in.readShort();
            rect[4] = in.readShort();
         }

         spriteRects = rects;
         return true;
      } catch (Throwable ignored) {
         return false;
      }
   }

   public static final boolean loadFrameData(ResourceLoader var0) {
      boolean var1 = false;

      try {
         DataInputStream var2 = new DataInputStream(var0.openStream(3));
         short[][] var3 = new short[710][];
         short[][] var4 = new short[710][];
         short[][] var5 = new short[710][];

         for (int var16 = 0; var16 != 710; var16++) {
            int var6 = 0;
            int var7 = var2.readUnsignedByte();

            for (int var8 = 0; var8 < var7; var8++) {
               var2.readShort();
               int var9 = var2.readUnsignedByte();

               for (int var10 = 0; var10 < var9; var10++) {
                  int var11;
                  int var12 = partKind(var11 = var2.readUnsignedByte());

                  for (int var13 = 0; var13 < var12; var13++) {
                     var2.readByte();
                  }

                  var6 += partSize(var11);
               }
            }

            var3[var16] = new short[var7];
            var4[var16] = new short[var7];
            var5[var16] = new short[var6];
         }

         frameDurations = var3;
         framePartOffsets = var4;
         parts = var5;
         var2.close();
         var2 = new DataInputStream(var0.openStream(3));

         for (int var17 = 0; var17 != 710; var17++) {
            int var19 = 0;
            short[] var20 = var5[var17];
            short[] var21 = var3[var17];
            short[] var22 = var4[var17];
            int var23 = var2.readUnsignedByte();

            for (int var24 = 0; var24 < var23; var24++) {
               var21[var24] = var2.readShort();
               var22[var24] = (short)var19;
               int var25 = var2.readUnsignedByte();

               for (int var26 = 0; var26 < var25; var26++) {
                  int var14;
                  switch (var14 = var2.readUnsignedByte()) {
                     case 0:
                     case 11:
                        var20[var19 + 3] = var2.readShort();
                        var20[var19 + 1] = var2.readShort();
                        var20[var19 + 2] = var2.readShort();
                        break;
                     case 1:
                     case 2:
                     case 3:
                     case 4:
                     case 5:
                     case 6:
                     case 8:
                        var20[var19 + 1] = var2.readShort();
                        var20[var19 + 2] = var2.readShort();
                        var20[var19 + 3] = var2.readShort();
                        var20[var19 + 4] = var2.readShort();
                        break;
                     case 7:
                        var20[var19 + 1] = var2.readShort();
                        var20[var19 + 2] = var2.readShort();
                     case 9:
                     default:
                        break;
                     case 10:
                        var20[var19 + 1] = var2.readByte();
                  }

                  var20[var19] = (short)var14;
                  var19 += partSize(var14);
               }
            }
         }

         var2.close();
         return true;
      } catch (IOException var15) {
         return false;
      }
   }

   private static final int partKind(int var0) {
      switch (var0) {
         case 0:
         case 11:
            return 6;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 8:
            return 8;
         case 7:
            return 4;
         case 9:
         default:
            return 0;
         case 10:
            return 1;
      }
   }

   private static final int partSize(int var0) {
      switch (var0) {
         case 0:
         case 11:
            return 4;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 8:
            return 5;
         case 7:
            return 3;
         case 9:
         default:
            return 0;
         case 10:
            return 2;
      }
   }

   private static final int partOffset(int var0, int var1) {
      return framePartOffsets[var0][var1];
   }

   private static final int nextPartOffset(int var0, int var1) {
      short[] var2 = framePartOffsets[var0];
      return var1 < var2.length - 1 ? var2[var1 + 1] : parts[var0].length;
   }

   public static final boolean loadTile(ResourceLoader loader, int spriteId) {
      return loadTileRecolored(loader, spriteId, null, null, 0);
   }

   public static final boolean loadTileRecolored(ResourceLoader loader, int spriteId, int[] fromColors, int[] toColors, int recolorSet) {
      int slot = 0;

      while (slot < 72 && spriteId != SpriteIndex.a[slot]) {
         slot++;
      }

      if (72 == slot) {
         return false;
      } else {
         Image[] slots;
         if ((slots = tiles[recolorSet])[slot] == null) {
            Image image;
            if (fromColors != null) {
               image = loader.loadImageRecolored(spriteId, fromColors, toColors);
            } else {
               image = ResourceLoader.loadImage(spriteId);
            }

            slots[slot] = image;
            if (image == null) {
               return false;
            }
         }

         return true;
      }
   }

   public static final boolean unloadTile(int spriteId, int recolorSet) {
      int slot = 0;

      while (slot < 72 && spriteId != SpriteIndex.a[slot]) {
         slot++;
      }

      if (72 == slot) {
         return false;
      } else {
         tiles[recolorSet][slot] = null;
         return true;
      }
   }

   public static final void drawFrame(Graphics g, int spriteId, int frame, int x, int y) {
      if (spriteId >= 0) {
         if ((spriteId & 16384) != 0) {
            spriteId &= -16385;
            drawFrameMirrored(g, spriteId, frame, x, y);
         } else {
            drawFrameNormal(g, spriteId, frame, x, y);
         }
      }
   }

   private static final void drawFrameNormal(Graphics var0, int var1, int var2, int var3, int var4) {
      Image[] var5 = tiles[recolorIndex];
      short[][] var6 = spriteRects;
      short[] var7 = parts[var1];
      int var8 = partOffset(var1, var2);
      int var9 = nextPartOffset(var1, var2);

      while (var8 < var9) {
         short var10;
         if ((var10 = var7[var8 + 0]) == 0 || var10 == 11) {
            short[] var11;
            int var12 = (var11 = var6[var7[var8 + 3]])[0] & '\uffff';
            Image var13;
            if ((var13 = var5[var12]) != null) {
               int var14 = var3 + var7[var8 + 1];
               int var15 = var4 + var7[var8 + 2];
               int var16 = var10 == 0 ? 0 : 2;
               var0.drawRegion(var13, var11[1], var11[2], var11[3], var11[4], var16, var14, var15, 20);
            }
         } else if (var10 != 2 && var10 != 7) {
            drawPart(var0, var3, var4, var7, var8);
         }

         var8 += partSize(var10);
      }
   }

   private static final void drawFrameMirrored(Graphics var0, int var1, int var2, int var3, int var4) {
      Image[] var5 = tiles[recolorIndex];
      short[][] var6 = spriteRects;
      short[] var7 = parts[var1];
      int var8 = partOffset(var1, var2);
      int var9 = nextPartOffset(var1, var2);

      while (var8 < var9) {
         short var10;
         if ((var10 = var7[var8 + 0]) == 0 || var10 == 11) {
            short[] var11;
            int var12 = (var11 = var6[var7[var8 + 3]])[0] & '\uffff';
            Image var13;
            if ((var13 = var5[var12]) != null) {
               int var14 = var3 - var7[var8 + 1] - var11[3];
               int var15 = var4 + var7[var8 + 2];
               int var16 = var10 == 0 ? 2 : 0;
               var0.drawRegion(var13, var11[1], var11[2], var11[3], var11[4], var16, var14, var15, 20);
            }
         } else if (var10 != 2 && var10 != 7) {
            drawPartMirrored(var0, var3, var4, var7, var8);
         }

         var8 += partSize(var10);
      }
   }

   public static final boolean drawParts(Graphics var0, int var1, int var2, int var3, int var4, int var5, int var6, int[] var7) {
      if (var1 < 0) {
         return false;
      } else if ((var1 & 16384) != 0) {
         var1 &= -16385;
         return drawPartsClipped(var0, var1, var2, var3, var4, var5, var6, var7);
      } else {
         return drawPartsMirrored(var0, var1, var2, var3, var4, var5, var6, var7);
      }
   }

   private static final boolean drawPartsMirrored(Graphics var0, int var1, int var2, int var3, int var4, int var5, int var6, int[] var7) {
      Image[] var8 = tiles[recolorIndex];
      short[][] var9 = spriteRects;
      boolean var10 = false;
      short[] var11 = parts[var1];
      int var12 = partOffset(var1, var2);
      int var13 = nextPartOffset(var1, var2);
      short[] var14 = var5 >= 0 ? parts[var5] : null;

      while (var12 < var13) {
         short var15;
         if ((var15 = var11[var12 + 0]) != 0 && var15 != 11) {
            drawPart(var0, var3, var4, var11, var12);
         } else {
            short var16 = var11[var12 + 3];
            short[] var17 = var9[var16];
            int var18 = var3 + var11[var12 + 1];
            int var19 = var4 + var11[var12 + 2];
            int var20 = var17[0] & '\uffff';
            Image var21;
            if ((var21 = var8[var20]) != null) {
               int var22 = var15 == 0 ? 0 : 2;
               var0.drawRegion(var21, var17[1], var17[2], var17[3], var17[4], var22, var18, var19, 20);
            }

            if (var14 != null) {
               int var38 = frameCount(var5);

               for (int var23 = 0; var23 < var38; var23++) {
                  int var24 = partOffset(var5, var23);
                  int var25 = var14[var24 + 3] & '\uffff';
                  if (var16 == var25) {
                     int var26 = nextPartOffset(var5, var23);
                     short var27 = var14[var24 + 1];
                     short var28 = var14[var24 + 2];
                     int var29 = 0;

                     do {
                        if ((var24 += partSize(var14[var24 + 0])) < var26 && var14[var24 + 0] == 0) {
                           var29++;
                        }
                     } while (var29 < var6 && var24 < var26);

                     if (var7 != null) {
                        var10 = true;
                        int var30 = var26 - partSize(7);
                        var7[0] = var18 + var14[var30 + 1] - var27;
                        var7[1] = var19 + var14[var30 + 2] - var28;
                     }

                     if (var24 < var26) {
                        int var39 = var14[var24 + 1] - var27;
                        int var31 = var14[var24 + 2] - var28;
                        short[] var32;
                        int var33 = (var32 = var9[var14[var24 + 3]])[0] & '\uffff';
                        Image var34 = var8[var33];
                        if (var21 != null) {
                           byte var35;
                           if (var15 == 11) {
                              var35 = 2;
                              short var36 = var32[3];
                              short var37 = var17[3];
                              var39 = -var39 - (var36 - var37);
                           } else {
                              var35 = 0;
                           }

                           var39 += var18;
                           var31 += var19;
                           var0.drawRegion(var34, var32[1], var32[2], var32[3], var32[4], var35, var39, var31, 20);
                        }
                     }
                     break;
                  }
               }
            }
         }

         var12 += partSize(var15);
      }

      return var10;
   }

   private static final boolean drawPartsClipped(Graphics var0, int var1, int var2, int var3, int var4, int var5, int var6, int[] var7) {
      Image[] var8 = tiles[recolorIndex];
      short[][] var9 = spriteRects;
      boolean var10 = false;
      short[] var11 = parts[var1];
      int var12 = partOffset(var1, var2);
      int var13 = nextPartOffset(var1, var2);
      short[] var14 = var5 >= 0 ? parts[var5] : null;

      while (var12 < var13) {
         short var15;
         if ((var15 = var11[var12 + 0]) != 0 && var15 != 11) {
            drawPartMirrored(var0, var3, var4, var11, var12);
         } else {
            short var16 = var11[var12 + 3];
            short[] var17 = var9[var16];
            int var18 = var3 - var11[var12 + 1] - var17[3];
            int var19 = var4 + var11[var12 + 2];
            int var20 = var17[0] & '\uffff';
            Image var21;
            if ((var21 = var8[var20]) != null) {
               int var22 = var15 == 0 ? 2 : 0;
               var0.drawRegion(var21, var17[1], var17[2], var17[3], var17[4], var22, var18, var19, 20);
            }

            if (var14 != null) {
               int var38 = frameCount(var5);

               for (int var23 = 0; var23 < var38; var23++) {
                  int var24 = partOffset(var5, var23);
                  int var25 = var14[var24 + 3] & '\uffff';
                  if (var16 == var25) {
                     int var26 = nextPartOffset(var5, var23);
                     short var27 = var14[var24 + 1];
                     short var28 = var14[var24 + 2];
                     int var29 = 0;

                     do {
                        if ((var24 += partSize(var14[var24 + 0])) < var26 && var14[var24 + 0] == 0) {
                           var29++;
                        }
                     } while (var29 < var6 && var24 < var26);

                     if (var7 != null) {
                        var10 = true;
                        int var30 = var26 - partSize(7);
                        var7[0] = var18 + var14[var30 + 1] - var27;
                        var7[1] = var19 + var14[var30 + 2] - var28;
                     }

                     if (var24 < var26) {
                        int var39 = var14[var24 + 1] - var27;
                        int var31 = var14[var24 + 2] - var28;
                        short[] var32;
                        int var33 = (var32 = var9[var14[var24 + 3]])[0] & '\uffff';
                        Image var34 = var8[var33];
                        if (var21 != null) {
                           byte var35;
                           if (var15 == 11) {
                              var35 = 0;
                           } else {
                              var35 = 2;
                              short var36 = var32[3];
                              short var37 = var17[3];
                              var39 = -var39 - (var36 - var37);
                           }

                           var39 += var18;
                           var31 += var19;
                           var0.drawRegion(var34, var32[1], var32[2], var32[3], var32[4], var35, var39, var31, 20);
                        }
                     }
                     break;
                  }
               }
            }
         }

         var12 += partSize(var15);
      }

      return var10;
   }

   private static final void drawPart(Graphics g, int x, int y, short[] parts, int offset) {
      short var5;
      switch (var5 = parts[offset + 0]) {
         case 3:
         case 4:
         case 5:
         case 6:
            int var10 = x + parts[offset + 1];
            int var11 = y + parts[offset + 2];
            short var12 = parts[offset + 3];
            short var13 = parts[offset + 4];
            switch (var5) {
               case 3:
                  g.drawRect(var10, var11, var12 - 1, var13 - 1);
                  break;
               case 4:
                  g.fillRect(var10, var11, var12, var13);
                  break;
               case 5:
                  g.drawArc(var10, var11, var12 - 1, var13 - 1, 0, 360);
                  break;
               case 6:
                  g.fillArc(var10, var11, var12, var13, 0, 360);
               default:
                  return;
            }
         case 7:
         case 9:
         default:
            break;
         case 8:
            int var6 = x + parts[offset + 1];
            int var7 = y + parts[offset + 2];
            int var8 = x + parts[offset + 3];
            int var9 = y + parts[offset + 4];
            g.drawLine(var6, var7, var8, var9);
            return;
         case 10:
            setColor(g, parts[offset + 1]);
      }
   }

   private static final void drawPartMirrored(Graphics var0, int var1, int var2, short[] var3, int var4) {
      short var5;
      switch (var5 = var3[var4 + 0]) {
         case 3:
         case 4:
         case 5:
         case 6:
            short var10 = var3[var4 + 3];
            short var11 = var3[var4 + 4];
            int var12 = var1 - (var3[var4 + 1] + var10);
            int var13 = var2 + var3[var4 + 2];
            switch (var5) {
               case 3:
                  var0.drawRect(var12, var13, var10 - 1, var11 - 1);
                  break;
               case 4:
                  var0.fillRect(var12, var13, var10, var11);
                  break;
               case 5:
                  var0.drawArc(var12, var13, var10 - 1, var11 - 1, 0, 360);
                  break;
               case 6:
                  var0.fillArc(var12, var13, var10, var11, 0, 360);
               default:
                  return;
            }
         case 7:
         case 9:
         default:
            break;
         case 8:
            int var6 = var1 - var3[var4 + 1];
            int var7 = var2 + var3[var4 + 2];
            int var8 = var1 - var3[var4 + 3];
            int var9 = var2 + var3[var4 + 4];
            var0.drawLine(var6, var7, var8, var9);
            return;
         case 10:
            setColor(var0, var3[var4 + 1]);
      }
   }

   public static final int frameCount(int spriteId) {
      spriteId &= -16385;
      return frameDurations[spriteId].length;
   }

   public static final short frameDuration(int spriteId, int frame) {
      spriteId &= -16385;
      return frameDurations[spriteId][frame];
   }

   private static final int partX(short[] parts, int offset) {
      switch (parts[offset + 0]) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 9:
         case 11:
            return parts[offset + 1];
         case 8:
            short var2 = parts[offset + 1];
            short var3 = parts[offset + 3];
            if (var2 < var3) {
               return var2;
            }

            return var3;
         case 10:
         default:
            return 0;
      }
   }

   private static final int partY(short[] var0, int var1) {
      switch (var0[var1 + 0]) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 9:
         case 11:
            return var0[var1 + 2];
         case 8:
            short var2 = var0[var1 + 2];
            short var3 = var0[var1 + 4];
            if (var2 < var3) {
               return var2;
            }

            return var3;
         case 10:
         default:
            return 0;
      }
   }

   private static final int partWidth(short[] var0, int var1) {
      switch (var0[var1 + 0]) {
         case 0:
         case 11:
            short var2;
            var2 = (short)((var2 = var0[var1 + 3]) & 65535);
            return spriteRects[var2][3];
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
            return var0[var1 + 3];
         case 7:
         case 10:
         default:
            return 0;
         case 8:
            return Math.abs(var0[var1 + 1] - var0[var1 + 3]);
         case 9:
            return 0;
      }
   }

   private static final int partHeight(short[] var0, int var1) {
      switch (var0[var1 + 0]) {
         case 0:
         case 11:
            short var2;
            var2 = (short)((var2 = var0[var1 + 3]) & 65535);
            return spriteRects[var2][4];
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
            return var0[var1 + 4];
         case 7:
         case 10:
         default:
            return 0;
         case 8:
            return Math.abs(var0[var1 + 2] - var0[var1 + 4]);
         case 9:
            return 0;
      }
   }

   public static final int frameMinX(int spriteId, int frame) {
      spriteId &= -16385;
      int var2 = Integer.MAX_VALUE;
      short[] var3 = parts[spriteId];
      int var4 = partOffset(spriteId, frame);
      int var5 = nextPartOffset(spriteId, frame);
      if (var4 == var5) {
         return 0;
      } else {
         while (var4 < var5) {
            short var6;
            int var7;
            if ((var6 = var3[var4 + 0]) != 10 && (var7 = partX(var3, var4)) < var2) {
               var2 = var7;
            }

            var4 += partSize(var6);
         }

         return var2;
      }
   }

   public static final int frameMinY(int spriteId, int frame) {
      spriteId &= -16385;
      int var2 = Integer.MAX_VALUE;
      short[] var3 = parts[spriteId];
      int var4 = partOffset(spriteId, frame);
      int var5 = nextPartOffset(spriteId, frame);
      if (var4 == var5) {
         return 0;
      } else {
         while (var4 < var5) {
            short var6;
            int var7;
            if ((var6 = var3[var4 + 0]) != 10 && (var7 = partY(var3, var4)) < var2) {
               var2 = var7;
            }

            var4 += partSize(var6);
         }

         return var2;
      }
   }

   public static final int frameWidth(int spriteId, int frame) {
      spriteId &= -16385;
      int var2 = Integer.MAX_VALUE;
      int var3 = Integer.MIN_VALUE;
      short[] var4 = parts[spriteId];
      int var5 = partOffset(spriteId, frame);
      int var6 = nextPartOffset(spriteId, frame);
      if (var5 == var6) {
         return 0;
      } else {
         while (var5 < var6) {
            short var7;
            if ((var7 = var4[var5 + 0]) != 10) {
               int var8;
               int var9 = (var8 = partX(var4, var5)) + partWidth(var4, var5);
               if (var8 < var2) {
                  var2 = var8;
               }

               if (var9 > var3) {
                  var3 = var9;
               }
            }

            var5 += partSize(var7);
         }

         return var3 - var2;
      }
   }

   public static final int frameHeight(int spriteId, int frame) {
      spriteId &= -16385;
      int var2 = Integer.MAX_VALUE;
      int var3 = Integer.MIN_VALUE;
      short[] var4 = parts[spriteId];
      int var5 = partOffset(spriteId, frame);
      int var6 = nextPartOffset(spriteId, frame);
      if (var5 == var6) {
         return 0;
      } else {
         while (var5 < var6) {
            short var7;
            if ((var7 = var4[var5 + 0]) != 10) {
               int var8;
               int var9 = (var8 = partY(var4, var5)) + partHeight(var4, var5);
               if (var8 < var2) {
                  var2 = var8;
               }

               if (var9 > var3) {
                  var3 = var9;
               }
            }

            var5 += partSize(var7);
         }

         return var3 - var2;
      }
   }

   public static final int countSpecialParts(int spriteId, int frame) {
      spriteId &= -16385;
      int var2 = 0;
      short[] var3 = parts[spriteId];
      int var4 = partOffset(spriteId, frame);
      int var5 = nextPartOffset(spriteId, frame);

      while (var4 < var5) {
         short var6;
         if ((var6 = var3[var4 + 0]) == 7) {
            var2++;
         }

         var4 += partSize(var6);
      }

      return var2;
   }

   public static final boolean hitTest(int[] var0, int var1, int var2, int var3) {
      boolean var4 = false;
      if ((var1 & 16384) != 0) {
         var1 &= -16385;
         var4 = true;
      }

      int var5 = 0;
      short[] var6 = parts[var1];
      int var7 = partOffset(var1, var2);
      int var8 = nextPartOffset(var1, var2);

      while (var7 < var8) {
         short var9;
         if ((var9 = var6[var7 + 0]) == 7) {
            if (var5 == var3) {
               var0[0] = var6[var7 + 1];
               var0[1] = var6[var7 + 2];
               if (var4) {
                  var0[0] = -var0[0];
               }

               return true;
            }

            var5++;
         }

         var7 += partSize(var9);
      }

      var0[0] = 0;
      var0[1] = 0;
      return false;
   }

   public static final Image getTileImage(int var0, int var1, int var2) {
      var0 &= -16385;
      short[] var3 = parts[var0];
      int var4 = partOffset(var0, var1);
      int var5 = nextPartOffset(var0, var1);

      while (var4 < var5) {
         short var6;
         if ((var6 = var3[var4 + 0]) == 0 || var6 == 11) {
            if (var2 == 0) {
               short var7 = var3[var4 + 3];
               short var8 = spriteRects[var7][0];
               return tiles[recolorIndex][var8];
            }

            var2--;
         }

         var4 += partSize(var6);
      }

      return null;
   }

   public static final boolean hitTestMirrored(int[] var0, int var1, int var2, int var3) {
      if ((var1 & 16384) != 0) {
         var1 &= -16385;
      }

      int var5 = 0;
      short[] var6 = parts[var1];
      int var7 = partOffset(var1, var2);
      int var8 = nextPartOffset(var1, var2);

      while (var7 < var8) {
         short var9;
         if ((var9 = var6[var7 + 0]) == 2) {
            if (var5 == var3) {
               var0[0] = var6[var7 + 1];
               var0[1] = var6[var7 + 2];
               var0[2] = var6[var7 + 3];
               var0[3] = var6[var7 + 4];
               return true;
            }

            var5++;
         }

         var7 += partSize(var9);
      }

      var0[0] = 0;
      var0[1] = 0;
      var0[2] = 0;
      var0[3] = 0;
      return false;
   }

   public static final void drawAnimator(Graphics var0, Animator var1, int var2, int var3) {
      drawFrame(var0, var1.animId, var1.frame, var2, var3);
   }

   public static final void setRecolorIndex(int index) {
      recolorIndex = index;
   }
}
