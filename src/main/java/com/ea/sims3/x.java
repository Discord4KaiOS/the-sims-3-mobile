package com.ea.sims3;

public abstract class x {
   private static final int[] a = new int[]{205525, 205375, 205260, 205163, 205077};
   private static final int[] b = new int[]{270, 512, 627, 724, 809};
   private static final int[] c = new int[]{
      0,
      256,
      362,
      443,
      512,
      572,
      627,
      677,
      724,
      768,
      810,
      849,
      887,
      923,
      958,
      991,
      1024,
      1056,
      1086,
      1116,
      1145,
      1173,
      1201,
      1227,
      1254,
      1280,
      1305,
      1330,
      1355,
      1378,
      1402,
      1425,
      1448,
      1471,
      1493,
      1515,
      1536,
      1557,
      1578,
      1599,
      1619,
      1639,
      1659,
      1679,
      1698,
      1717,
      1736,
      1755
   };
   private static final int a2 = c.length;
   private static final int b2 = c[a2 - 1];

   private x() {
   }

   public static final int a3(int var0) {
      if (var0 > 0) {
         return 1;
      } else {
         return var0 < 0 ? -1 : 0;
      }
   }

   public static final int a(int var0, int var1, int var2) {
      return Math.max(var1, Math.min(var2, var0));
   }

   public static final int a2(int var0, int var1) {
      return (int)((long)var0 * var1 >> 16);
   }

   public static final int b(int var0, int var1) {
      return (int)(((long)var0 << 16) / var1);
   }

   public static final int c(int var0, int var1) {
      long var2 = var0;
      long var4 = var1;
      return f((int)(var2 * var2 + var4 * var4 >> 16));
   }

   public static final int b2(int var0) {
      if (var0 > 205887) {
         var0 -= Math.max(1, var0 / 411775) * 411775;
      } else if (var0 < -205887) {
         var0 += Math.max(1, -var0 / 411775) * 411775;
      }

      return var0;
   }

   public static final int c(int var0) {
      int var1;
      int var2;
      int var3 = (var2 = a2((var1 = var0 >= 0 ? 1 : -1) * var0 << 1, 20861) & 262143) >> 16 & 3;
      var2 &= 65535;
      var1 = 1;
      if ((var3 & 1) != 0) {
         var1 = -1;
         var2 = 65536 - var2;
      }

      if ((var3 & 2) != 0) {
         var1 = -var1;
      }

      var2 = a2(var2, 205887) >> 1;
      int var4 = 262144;
      int var5;
      int var6 = (var5 = a2(var2, var2)) << 1;
      var4 -= var6;
      var6 = a2(a2(var5, var6), 5461);
      var4 += var6;
      var6 = a2(a2(var5, var6), 2185);
      var4 -= var6;
      var6 = a2(a2(var5, var6), 1170);
      int var13;
      return (var13 = var4 + var6) * var1 >> 2;
   }

   public static final int d(int var0) {
      if (var0 <= -65536) {
         return 205887;
      } else if (65535 + var0 < a.length) {
         return a[65535 + var0];
      } else if (var0 >= 65536) {
         return 0;
      } else if (65535 - var0 < b.length) {
         return b[65535 - var0];
      } else {
         long var1 = (long)var0 * var0;
         if (var0 > 46661) {
            return 102944 - d(f((int)(4294967296L - var1 + 32768L >> 16)));
         } else if (var0 < -46661) {
            return 102944 + d(f((int)(4294967296L - var1 + 32768L >> 16)));
         } else {
            int var3 = (int)(var1 + 32768L >> 16);
            int var5 = -var0;
            int var4 = a2(var0, var3);
            var5 -= a2(10923, var4);
            var4 = a2(var4, var3);
            var5 -= a2(4915, var4);
            var4 = a2(var4, var3);
            var5 -= a2(2926, var4);
            return 102944 + var5;
         }
      }
   }

   public static final int e(int var0) {
      return c(var0 - 102944);
   }

   public static final int d(int var0, int var1) {
      if (var0 == 0) {
         return var1 < 0 ? 205887 : 0;
      } else if (var1 == 0) {
         return var0 < 0 ? -102944 : 102944;
      } else {
         int var2 = c(var0, var1);
         int var3 = var1 < 0 ? -var1 : var1;
         int var5 = d(var2 > 0 ? b(var3, var2) : var3);
         if (var0 < 0 && var1 < 0) {
            var5 -= 205887;
         } else if (var0 < 0 && var1 > 0) {
            var5 = -var5;
         } else if (var0 > 0 && var1 < 0) {
            var5 = 205887 - var5;
         }

         return var5;
      }
   }

   public static final int f(int var0) {
      if (var0 < 0) {
         return 0;
      } else if (var0 < a2) {
         return c[var0];
      } else {
         int var1 = var0;
         if (var0 < b2) {
            var1 = var0 << 2;
         } else if (var0 > 4194304) {
            var1 = var0 >> 2;
         }

         int var2;
         if (var1 > 65200 && var1 < 65800) {
            var2 = var1 + 65536 >> 1;
         } else {
            int var3;
            if ((var3 = (var1 >> 3) + 131072) > 8388608) {
               var3 = 8388608;
            }

            int var4 = b2;
            var2 = var3 + var4 >> 1;

            for (int var5 = 0; var5 < 13; var5++) {
               if (a2(var2, var2) > var1) {
                  var3 = var2;
               } else {
                  var4 = var2;
               }

               var2 = var3 + var4 >> 1;
            }
         }

         if (var0 < b2) {
            var2 >>= 1;
         } else if (var0 > 4194304) {
            var2 <<= 1;
         }

         return var2;
      }
   }

   public static final int b(int var0, int var1, int var2) {
      if (var2 < var0) {
         return 0;
      } else if (var2 >= var1) {
         return 65536;
      } else {
         int var3 = var2 - var0;
         int var4 = var1 - var0;
         int var5;
         int var6 = a2(var5 = b(var3, var4), var5);
         return -2 * a2(var6, var5) + 3 * var6;
      }
   }
}
