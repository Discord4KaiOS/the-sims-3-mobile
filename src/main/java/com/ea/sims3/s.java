package com.ea.sims3;

import java.io.DataInputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Image;

public final class s implements h, g, j {
   private int[] a;

   public s() {
      this.a10();
   }

   public static final int a3(int var0) {
      return j.b[var0];
   }

   public static final String a9(int var0) {
      return j.a[var0];
   }

   public static Image a10(int var0) {
      try {
         return Image.createImage(a9(var0));
      } catch (Throwable var2) {
         return null;
      }
   }

   public final InputStream a8(int var1) {
      return this.getClass().getResourceAsStream(a9(var1));
   }

   public final byte[] a14(int var1) {
      return this.b9(g.b[var1]);
   }

   private byte[] b9(int var1) {
      int var2;
      byte[] var3 = new byte[var2 = j.b[var1]];

      try {
         DataInputStream var4 = new DataInputStream(this.a8(var1));
         var4.readFully(var3);
         return var3;
      } catch (Exception var5) {
         return null;
      }
   }

   private static final int a(byte var0) {
      return 0xFF & var0;
   }

   private static final int a(byte[] var0, int var1) {
      int var2 = (var0[var1 + 0] & 255) << 24;
      int var3 = (var0[var1 + 1] & 255) << 16;
      int var4 = (var0[var1 + 2] & 255) << 8;
      int var5 = var0[var1 + 3] & 255;
      return var2 | var3 | var4 | var5;
   }

   private final void a10() {
      this.a = new int[256];

      for (int var1 = 0; var1 <= 255; var1++) {
         int var2 = var1;

         for (int var3 = 8; var3 > 0; var3--) {
            if ((var2 & 1) == 1) {
               var2 = var2 >>> 1 ^ -306674912;
            } else {
               var2 >>>= 1;
            }
         }

         this.a[var1] = var2;
      }
   }

   private int a(byte[] var1, int var2, int var3) {
      int var4 = -1;

      for (int var5 = var2; var5 < var2 + var3; var5++) {
         int var6 = 255 & var1[var5] ^ var4 & 0xFF;
         var4 = var4 >>> 8 ^ this.a[var6];
      }

      return ~var4;
   }

   private static void a(byte[] var0, int var1, int var2, int[] var3, int[] var4) {
      int var5 = var3.length;
      int var6 = var2 / 3;

      for (int var7 = 0; var7 < var5; var7++) {
         if (var3[var7] != 16711935) {
            int var8;
            int var9 = ((var8 = var3[var7]) & 0xFF0000) >> 16;
            int var10 = (var8 & 0xFF00) >> 8;
            int var11 = (var8 & 0xFF) >> 0;
            int var12 = var1;

            for (int var13 = 0; var13 < var6; var13++) {
               int var14 = var0[var12 + 0] & 255;
               int var15 = var0[var12 + 1] & 255;
               int var16 = var0[var12 + 2] & 255;
               if (var14 == var9 && var15 == var10 && var16 == var11) {
                  int var17 = (var4[var7] & 0xFF0000) >> 16;
                  int var18 = (var4[var7] & 0xFF00) >> 8;
                  int var19 = (var4[var7] & 0xFF) >> 0;
                  var0[var12 + 0] = (byte)(var17 & 0xFF);
                  var0[var12 + 1] = (byte)(var18 & 0xFF);
                  var0[var12 + 2] = (byte)(var19 & 0xFF);
               }

               var12 += 3;
            }
         }
      }
   }

   public final Image a(int var1, int[] var2, int[] var3) {
      byte[] var4;
      if (a((var4 = this.b9(var1))[0]) == 137
         && a(var4[1]) == 80
         && a(var4[2]) == 78
         && a(var4[3]) == 71
         && a(var4[4]) == 13
         && a(var4[5]) == 10
         && a(var4[6]) == 26
         && a(var4[7]) == 10) {
         int var5 = a(var4, 8);
         int var6 = a(var4, 12);
         byte var7 = 0;
         byte var8 = 0;
         if (var5 > 0 && var6 == 1229472850) {
            var7 = var4[24];
            var8 = var4[25];
         }

         if ((var7 == 4 || var7 == 8) && var8 == 3) {
            int var9 = 0;
            int var10 = 0;
            int var11 = 8;

            int var12;
            do {
               var12 = a(var4, var11);
               if (a(var4, var11 + 4) == 1347179589) {
                  var9 = var11;
                  var10 = var12;
                  break;
               }
            } while ((var11 += 12 + var12) < var4.length);

            a(var4, var9 + 4 + 4, var10, var2, var3);
            if (var9 > 0) {
               var12 = this.a(var4, var9 + 4, var10 + 4);
               int var13 = var9 + 4 + 4 + var10;
               var4[var13] = (byte)((var12 & 0xFF000000) >>> 24);
               var4[var13 + 1] = (byte)((var12 & 0xFF0000) >>> 16);
               var4[var13 + 2] = (byte)((var12 & 0xFF00) >>> 8);
               var4[var13 + 3] = (byte)(var12 & 0xFF);
            }

            try {
               Image var16;
               if ((var16 = Image.createImage(var4, 0, var4.length)) != null) {
                  return var16;
               }
            } catch (Exception var14) {
            }

            return null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }
}
