package com.ea.sims3;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class SpriteRenderer implements SpriteIndex {
   private static byte[] a;
   private static short[][] a2;
   private static int a3;
   private static Image[][] a4 = new Image[4][72];
   private static short[][] b;
   private static short[][] c;
   private static short[][] d;

   private SpriteRenderer() {
   }

   public static final boolean a(ResourceLoader var0) {
      DataInputStream var1 = new DataInputStream(var0.openStream(4));
      a = new byte[ResourceLoader.getSize(4)];

      try {
         var1.read(a);
         var1.close();
         return true;
      } catch (Throwable var3) {
         return false;
      }
   }

   public static final void a(Graphics var0, int var1) {
      int var2 = var1 * 3;
      byte[] var3 = a;
      var0.setColor(var3[var2] & 255, var3[var2 + 1] & 255, var3[var2 + 2] & 255);
   }

   public static final boolean b(ResourceLoader var0) {
      try {
         short[][] var1 = new short[1418][5];
         DataInputStream var2 = new DataInputStream(var0.openStream(31));

         for (int var3 = 0; var3 < 1418; var3++) {
            short[] var4;
            (var4 = var1[var3])[0] = (short)var2.readUnsignedByte();
            var4[1] = var2.readShort();
            var4[2] = var2.readShort();
            var4[3] = var2.readShort();
            var4[4] = var2.readShort();
         }

         a2 = var1;
         return true;
      } catch (Throwable var5) {
         return false;
      }
   }

   public static final boolean c(ResourceLoader var0) {
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
                  int var12 = b2(var11 = var2.readUnsignedByte());

                  for (int var13 = 0; var13 < var12; var13++) {
                     var2.readByte();
                  }

                  var6 += c(var11);
               }
            }

            var3[var16] = new short[var7];
            var4[var16] = new short[var7];
            var5[var16] = new short[var6];
         }

         b = var3;
         c = var4;
         d = var5;
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
                  var19 += c(var14);
               }
            }
         }

         var2.close();
         return true;
      } catch (IOException var15) {
         return false;
      }
   }

   private static final int b2(int var0) {
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

   private static final int c(int var0) {
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

   private static final int f(int var0, int var1) {
      return c[var0][var1];
   }

   private static final int g(int var0, int var1) {
      short[] var2 = c[var0];
      return var1 < var2.length - 1 ? var2[var1 + 1] : d[var0].length;
   }

   public static final boolean a(ResourceLoader var0, int var1) {
      return a(var0, var1, null, null, 0);
   }

   public static final boolean a(ResourceLoader var0, int var1, int[] var2, int[] var3, int var4) {
      int var5 = 0;

      while (var5 < 72 && var1 != SpriteIndex.a[var5]) {
         var5++;
      }

      if (72 == var5) {
         return false;
      } else {
         Image[] var6;
         if ((var6 = a4[var4])[var5] == null) {
            Image var7;
            if (var2 != null) {
               var7 = var0.loadImageRecolored(var1, var2, var3);
            } else {
               var7 = ResourceLoader.loadImage(var1);
            }

            var6[var5] = var7;
            if (var7 == null) {
               return false;
            }
         }

         return true;
      }
   }

   public static final boolean a5(int var0, int var1) {
      int var2 = 0;

      while (var2 < 72 && var0 != SpriteIndex.a[var2]) {
         var2++;
      }

      if (72 == var2) {
         return false;
      } else {
         a4[var1][var2] = null;
         return true;
      }
   }

   public static final void drawFrame(Graphics var0, int var1, int var2, int var3, int var4) {
      if (var1 >= 0) {
         if ((var1 & 16384) != 0) {
            var1 &= -16385;
            c2(var0, var1, var2, var3, var4);
         } else {
            b2(var0, var1, var2, var3, var4);
         }
      }
   }

   private static final void b2(Graphics var0, int var1, int var2, int var3, int var4) {
      Image[] var5 = a4[a3];
      short[][] var6 = a2;
      short[] var7 = d[var1];
      int var8 = f(var1, var2);
      int var9 = g(var1, var2);

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
            a(var0, var3, var4, var7, var8);
         }

         var8 += c(var10);
      }
   }

   private static final void c2(Graphics var0, int var1, int var2, int var3, int var4) {
      Image[] var5 = a4[a3];
      short[][] var6 = a2;
      short[] var7 = d[var1];
      int var8 = f(var1, var2);
      int var9 = g(var1, var2);

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
            b(var0, var3, var4, var7, var8);
         }

         var8 += c(var10);
      }
   }

   public static final boolean a(Graphics var0, int var1, int var2, int var3, int var4, int var5, int var6, int[] var7) {
      if (var1 < 0) {
         return false;
      } else if ((var1 & 16384) != 0) {
         var1 &= -16385;
         return c(var0, var1, var2, var3, var4, var5, var6, var7);
      } else {
         return b(var0, var1, var2, var3, var4, var5, var6, var7);
      }
   }

   private static final boolean b(Graphics var0, int var1, int var2, int var3, int var4, int var5, int var6, int[] var7) {
      Image[] var8 = a4[a3];
      short[][] var9 = a2;
      boolean var10 = false;
      short[] var11 = d[var1];
      int var12 = f(var1, var2);
      int var13 = g(var1, var2);
      short[] var14 = var5 >= 0 ? d[var5] : null;

      while (var12 < var13) {
         short var15;
         if ((var15 = var11[var12 + 0]) != 0 && var15 != 11) {
            a(var0, var3, var4, var11, var12);
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
                  int var24 = f(var5, var23);
                  int var25 = var14[var24 + 3] & '\uffff';
                  if (var16 == var25) {
                     int var26 = g(var5, var23);
                     short var27 = var14[var24 + 1];
                     short var28 = var14[var24 + 2];
                     int var29 = 0;

                     do {
                        if ((var24 += c(var14[var24 + 0])) < var26 && var14[var24 + 0] == 0) {
                           var29++;
                        }
                     } while (var29 < var6 && var24 < var26);

                     if (var7 != null) {
                        var10 = true;
                        int var30 = var26 - c(7);
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

         var12 += c(var15);
      }

      return var10;
   }

   private static final boolean c(Graphics var0, int var1, int var2, int var3, int var4, int var5, int var6, int[] var7) {
      Image[] var8 = a4[a3];
      short[][] var9 = a2;
      boolean var10 = false;
      short[] var11 = d[var1];
      int var12 = f(var1, var2);
      int var13 = g(var1, var2);
      short[] var14 = var5 >= 0 ? d[var5] : null;

      while (var12 < var13) {
         short var15;
         if ((var15 = var11[var12 + 0]) != 0 && var15 != 11) {
            b(var0, var3, var4, var11, var12);
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
                  int var24 = f(var5, var23);
                  int var25 = var14[var24 + 3] & '\uffff';
                  if (var16 == var25) {
                     int var26 = g(var5, var23);
                     short var27 = var14[var24 + 1];
                     short var28 = var14[var24 + 2];
                     int var29 = 0;

                     do {
                        if ((var24 += c(var14[var24 + 0])) < var26 && var14[var24 + 0] == 0) {
                           var29++;
                        }
                     } while (var29 < var6 && var24 < var26);

                     if (var7 != null) {
                        var10 = true;
                        int var30 = var26 - c(7);
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

         var12 += c(var15);
      }

      return var10;
   }

   private static final void a(Graphics var0, int var1, int var2, short[] var3, int var4) {
      short var5;
      switch (var5 = var3[var4 + 0]) {
         case 3:
         case 4:
         case 5:
         case 6:
            int var10 = var1 + var3[var4 + 1];
            int var11 = var2 + var3[var4 + 2];
            short var12 = var3[var4 + 3];
            short var13 = var3[var4 + 4];
            switch (var5) {
               case 3:
                  var0.drawRect(var10, var11, var12 - 1, var13 - 1);
                  break;
               case 4:
                  var0.fillRect(var10, var11, var12, var13);
                  break;
               case 5:
                  var0.drawArc(var10, var11, var12 - 1, var13 - 1, 0, 360);
                  break;
               case 6:
                  var0.fillArc(var10, var11, var12, var13, 0, 360);
               default:
                  return;
            }
         case 7:
         case 9:
         default:
            break;
         case 8:
            int var6 = var1 + var3[var4 + 1];
            int var7 = var2 + var3[var4 + 2];
            int var8 = var1 + var3[var4 + 3];
            int var9 = var2 + var3[var4 + 4];
            var0.drawLine(var6, var7, var8, var9);
            return;
         case 10:
            a(var0, var3[var4 + 1]);
      }
   }

   private static final void b(Graphics var0, int var1, int var2, short[] var3, int var4) {
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
            a(var0, var3[var4 + 1]);
      }
   }

   public static final int frameCount(int var0) {
      var0 &= -16385;
      return b[var0].length;
   }

   public static final short frameDuration(int var0, int var1) {
      var0 &= -16385;
      return b[var0][var1];
   }

   private static final int a(short[] var0, int var1) {
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
            return var0[var1 + 1];
         case 8:
            short var2 = var0[var1 + 1];
            short var3 = var0[var1 + 3];
            if (var2 < var3) {
               return var2;
            }

            return var3;
         case 10:
         default:
            return 0;
      }
   }

   private static final int b(short[] var0, int var1) {
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

   private static final int c(short[] var0, int var1) {
      switch (var0[var1 + 0]) {
         case 0:
         case 11:
            short var2;
            var2 = (short)((var2 = var0[var1 + 3]) & 65535);
            return a2[var2][3];
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

   private static final int d(short[] var0, int var1) {
      switch (var0[var1 + 0]) {
         case 0:
         case 11:
            short var2;
            var2 = (short)((var2 = var0[var1 + 3]) & 65535);
            return a2[var2][4];
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

   public static final int a2(int var0, int var1) {
      var0 &= -16385;
      int var2 = Integer.MAX_VALUE;
      short[] var3 = d[var0];
      int var4 = f(var0, var1);
      int var5 = g(var0, var1);
      if (var4 == var5) {
         return 0;
      } else {
         while (var4 < var5) {
            short var6;
            int var7;
            if ((var6 = var3[var4 + 0]) != 10 && (var7 = a(var3, var4)) < var2) {
               var2 = var7;
            }

            var4 += c(var6);
         }

         return var2;
      }
   }

   public static final int b(int var0, int var1) {
      var0 &= -16385;
      int var2 = Integer.MAX_VALUE;
      short[] var3 = d[var0];
      int var4 = f(var0, var1);
      int var5 = g(var0, var1);
      if (var4 == var5) {
         return 0;
      } else {
         while (var4 < var5) {
            short var6;
            int var7;
            if ((var6 = var3[var4 + 0]) != 10 && (var7 = b(var3, var4)) < var2) {
               var2 = var7;
            }

            var4 += c(var6);
         }

         return var2;
      }
   }

   public static final int c(int var0, int var1) {
      var0 &= -16385;
      int var2 = Integer.MAX_VALUE;
      int var3 = Integer.MIN_VALUE;
      short[] var4 = d[var0];
      int var5 = f(var0, var1);
      int var6 = g(var0, var1);
      if (var5 == var6) {
         return 0;
      } else {
         while (var5 < var6) {
            short var7;
            if ((var7 = var4[var5 + 0]) != 10) {
               int var8;
               int var9 = (var8 = a(var4, var5)) + c(var4, var5);
               if (var8 < var2) {
                  var2 = var8;
               }

               if (var9 > var3) {
                  var3 = var9;
               }
            }

            var5 += c(var7);
         }

         return var3 - var2;
      }
   }

   public static final int d(int var0, int var1) {
      var0 &= -16385;
      int var2 = Integer.MAX_VALUE;
      int var3 = Integer.MIN_VALUE;
      short[] var4 = d[var0];
      int var5 = f(var0, var1);
      int var6 = g(var0, var1);
      if (var5 == var6) {
         return 0;
      } else {
         while (var5 < var6) {
            short var7;
            if ((var7 = var4[var5 + 0]) != 10) {
               int var8;
               int var9 = (var8 = b(var4, var5)) + d(var4, var5);
               if (var8 < var2) {
                  var2 = var8;
               }

               if (var9 > var3) {
                  var3 = var9;
               }
            }

            var5 += c(var7);
         }

         return var3 - var2;
      }
   }

   public static final int e(int var0, int var1) {
      var0 &= -16385;
      int var2 = 0;
      short[] var3 = d[var0];
      int var4 = f(var0, var1);
      int var5 = g(var0, var1);

      while (var4 < var5) {
         short var6;
         if ((var6 = var3[var4 + 0]) == 7) {
            var2++;
         }

         var4 += c(var6);
      }

      return var2;
   }

   public static final boolean a2(int[] var0, int var1, int var2, int var3) {
      boolean var4 = false;
      if ((var1 & 16384) != 0) {
         var1 &= -16385;
         var4 = true;
      }

      int var5 = 0;
      short[] var6 = d[var1];
      int var7 = f(var1, var2);
      int var8 = g(var1, var2);

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

         var7 += c(var9);
      }

      var0[0] = 0;
      var0[1] = 0;
      return false;
   }

   public static final Image getTileImage(int var0, int var1, int var2) {
      var0 &= -16385;
      short[] var3 = d[var0];
      int var4 = f(var0, var1);
      int var5 = g(var0, var1);

      while (var4 < var5) {
         short var6;
         if ((var6 = var3[var4 + 0]) == 0 || var6 == 11) {
            if (var2 == 0) {
               short var7 = var3[var4 + 3];
               short var8 = a2[var7][0];
               return a4[a3][var8];
            }

            var2--;
         }

         var4 += c(var6);
      }

      return null;
   }

   public static final boolean b(int[] var0, int var1, int var2, int var3) {
      if ((var1 & 16384) != 0) {
         var1 &= -16385;
      }

      int var5 = 0;
      short[] var6 = d[var1];
      int var7 = f(var1, var2);
      int var8 = g(var1, var2);

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

         var7 += c(var9);
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

   public static final void a12(int var0) {
      a3 = var0;
   }
}
