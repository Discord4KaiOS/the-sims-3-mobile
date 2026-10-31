package com.ea.sims3;

public abstract class FixedMath {
   private static final int[] sinTableNegative = new int[]{205525, 205375, 205260, 205163, 205077};
   private static final int[] sinTablePositive = new int[]{270, 512, 627, 724, 809};
   private static final int[] sqrtTable = new int[]{
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
   private static final int sqrtTableLength = sqrtTable.length;
   private static final int sqrtTableMax = sqrtTable[sqrtTableLength - 1];

   private FixedMath() {
   }

   public static final int sign(int value) {
      if (value > 0) {
         return 1;
      } else {
         return value < 0 ? -1 : 0;
      }
   }

   public static final int clamp(int value, int min, int max) {
      return Math.max(min, Math.min(max, value));
   }

   public static final int mul(int left, int right) {
      return (int)((long)left * right >> 16);
   }

   public static final int div(int dividend, int divisor) {
      return (int)(((long)dividend << 16) / divisor);
   }

   public static final int length(int x, int y) {
      long wideX = x;
      long wideY = y;
      return sqrt((int)(wideX * wideX + wideY * wideY >> 16));
   }

   public static final int wrapAngle(int angle) {
      if (angle > 205887) {
         angle -= Math.max(1, angle / 411775) * 411775;
      } else if (angle < -205887) {
         angle += Math.max(1, -angle / 411775) * 411775;
      }

      return angle;
   }

   public static final int cos(int angle) {
      int sign;
      int angle16;
      int quadrant = (angle16 = mul((sign = angle >= 0 ? 1 : -1) * angle << 1, 20861) & 262143) >> 16 & 3;
      angle16 &= 65535;
      sign = 1;
      if ((quadrant & 1) != 0) {
         sign = -1;
         angle16 = 65536 - angle16;
      }

      if ((quadrant & 2) != 0) {
         sign = -sign;
      }

      angle16 = mul(angle16, 205887) >> 1;
      int series = 262144;
      int square;
      int term = (square = mul(angle16, angle16)) << 1;
      series -= term;
      term = mul(mul(square, term), 5461);
      series += term;
      term = mul(mul(square, term), 2185);
      series -= term;
      term = mul(mul(square, term), 1170);
      int result;
      return (result = series + term) * sign >> 2;
   }

   public static final int sin(int angle) {
      if (angle <= -65536) {
         return 205887;
      } else if (65535 + angle < sinTableNegative.length) {
         return sinTableNegative[65535 + angle];
      } else if (angle >= 65536) {
         return 0;
      } else if (65535 - angle < sinTablePositive.length) {
         return sinTablePositive[65535 - angle];
      } else {
         long angleSquared = (long)angle * angle;
         if (angle > 46661) {
            return 102944 - sin(sqrt((int)(4294967296L - angleSquared + 32768L >> 16)));
         } else if (angle < -46661) {
            return 102944 + sin(sqrt((int)(4294967296L - angleSquared + 32768L >> 16)));
         } else {
            int square = (int)(angleSquared + 32768L >> 16);
            int series = -angle;
            int term = mul(angle, square);
            series -= mul(10923, term);
            term = mul(term, square);
            series -= mul(4915, term);
            term = mul(term, square);
            series -= mul(2926, term);
            return 102944 + series;
         }
      }
   }

   public static final int sinShifted(int angle) {
      return cos(angle - 102944);
   }

   public static final int atan2(int y, int x) {
      if (y == 0) {
         return x < 0 ? 205887 : 0;
      } else if (x == 0) {
         return y < 0 ? -102944 : 102944;
      } else {
         int length = length(y, x);
         int absX = x < 0 ? -x : x;
         int angle = sin(length > 0 ? div(absX, length) : absX);
         if (y < 0 && x < 0) {
            angle -= 205887;
         } else if (y < 0 && x > 0) {
            angle = -angle;
         } else if (y > 0 && x < 0) {
            angle = 205887 - angle;
         }

         return angle;
      }
   }

   public static final int sqrt(int value) {
      if (value < 0) {
         return 0;
      } else if (value < sqrtTableLength) {
         return sqrtTable[value];
      } else {
         int adjusted = value;
         if (value < sqrtTableMax) {
            adjusted = value << 2;
         } else if (value > 4194304) {
            adjusted = value >> 2;
         }

         int result;
         if (adjusted > 65200 && adjusted < 65800) {
            result = adjusted + 65536 >> 1;
         } else {
            int low;
            if ((low = (adjusted >> 3) + 131072) > 8388608) {
               low = 8388608;
            }

            int high = sqrtTableMax;
            result = low + high >> 1;

            for (int step = 0; step < 13; step++) {
               if (mul(result, result) > adjusted) {
                  low = result;
               } else {
                  high = result;
               }

               result = low + high >> 1;
            }
         }

         if (value < sqrtTableMax) {
            result >>= 1;
         } else if (value > 4194304) {
            result <<= 1;
         }

         return result;
      }
   }

   public static final int smoothStep(int edge0, int edge1, int x) {
      if (x < edge0) {
         return 0;
      } else if (x >= edge1) {
         return 65536;
      } else {
         int deltaX = x - edge0;
         int deltaEdge = edge1 - edge0;
         int ratio;
         int squared = mul(ratio = div(deltaX, deltaEdge), ratio);
         return -2 * mul(squared, ratio) + 3 * squared;
      }
   }
}
