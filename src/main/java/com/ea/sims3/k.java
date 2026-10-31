package com.ea.sims3;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

public final class k {
   public static int a = 16;
   public static int b = 32;
   public static int c = 64;
   public static int d = 1;
   public static int e = 2;
   public static int f = 4;
   public static int g = 8;
   private static String a2 = null;
   private static Object a3;
   private static Object b2;
   private static int h;
   private static byte[] a4;
   private static short[] a5;
   private static short[] b3;
   private static short[] c2;
   private static byte[] b4;
   private static int[] a6;
   private static short[] d2;
   private static short[] e2;
   private static byte[][] a7;
   private static short[][] a8;
   private static byte[] c3;
   private static byte[] d3;
   private static byte a9;
   private static short[][] b5;
   private static short[] f2;
   private static short[] g2;
   private static short[] h2;
   private static byte[] e3;
   private static byte[] f3;
   private static int i;
   private static String[] a10;
   private static byte[] g3;
   private static byte b6 = 0;
   private static int[] b7 = new int[2];
   private static int[] c4 = new int[4];
   private static byte[] h3 = new byte[2];
   private static Graphics a11;
   private static StringBuffer a12;
   private static boolean a13 = false;
   public static MIDlet a14;
   private static int j;
   private static StringBuffer b8;

   private static Object a(Object var0, byte[] var1, int var2) {
      Object[] var3 = a4(var1);
      return new Object[]{var0, var3[0], var3[1], var3[2], var3[3], var3[4], var3[5], new Integer(var2)};
   }

   public static Object a(Image var0, byte[] var1) {
      return a(var0, var1, -1);
   }

   // The retail build calls these directly; the checked exception is wrapped so the
   // decompiled source compiles (it never happens for classpath resources).
   private static long a(InputStream var0, long var1) {
      try {
         return var0.skip(var1);
      } catch (IOException var3) {
         throw new IllegalStateException(var3);
      }
   }

   public static void a(Object var0) {
      a3 = null;
      a4 = null;
      b2 = null;
      b3 = null;
      a5 = null;
      c2 = null;
      b4 = null;
      a6 = null;
      if (var0 != null) {
         if (var0 instanceof Font && com.ea.sims3.f.c3()) {
            (a4 = new byte[12])[0] = 1;
            a4[1] = 2;
            a4[7] = 0;
            a4[8] = 1;
            a4[9] = 2;
            a4[10] = 4;
            if (a11 != null) {
               a11.setFont((Font)var0);
            }
         } else if (com.ea.sims3.f.b5() || com.ea.sims3.f.d3()) {
            a4 = (byte[])((Object[])var0)[1];
            b2 = ((Object[])var0)[0];
            h = (Integer)((Object[])var0)[7];
            b3 = (short[])((Object[])var0)[2];
            a5 = (short[])((Object[])var0)[3];
            c2 = (short[])((Object[])var0)[4];
            b4 = (byte[])((Object[])var0)[5];
            a6 = (int[])((Object[])var0)[6];
            if (f3 != null && a4[11] != f3[b6] && f3[b6] > 1) {
               b4();
            }
         }
      }

      a3 = var0;
   }

   public static void a12(int var0) {
      a4[5] = (byte)var0;
   }

   public static int a() {
      if (a3 == null && com.ea.sims3.f.a11()) {
         a(a2 + "getSpaceSpacing::no font loaded, use setFont(Object)", 4);
      }

      return a4[5] & 0xFF;
   }

   public static int b() {
      if (a3 == null && com.ea.sims3.f.a11()) {
         a(a2 + "getLeadingSpacing::no font loaded, use setFont(Object)", 4);
      }

      return a4[2];
   }

   public static int c() {
      if (a3 == null && com.ea.sims3.f.a11()) {
         a(a2 + "getLineSize::no font loaded, use setFont(Object)", 4);
      }

      if ((a4[10] & 4) != 0 && com.ea.sims3.f.c3()) {
         return ((Font)a3).getHeight();
      } else {
         return !com.ea.sims3.f.b5() && !com.ea.sims3.f.d3() ? -1 : (a4[3] & 0xFF) + (a4[4] & 0xFF) + a4[2];
      }
   }

   public static void a(b var0, int var1, int var2, int var3) {
      a(var0, 0, var0.a4, var1, var2, var3);
   }

   private static void a(int[] var0, int var1) {
      if (a4[8] == 1) {
         var0[0] += var1;
      } else if (a4[8] == 4) {
         var0[0] -= var1;
      } else if (a4[8] == 2) {
         var0[1] += var1;
      } else {
         if (a4[8] == 8) {
            var0[1] -= var1;
         }
      }
   }

   private static void a(b var0, int var1, int var2, int var3, int var4, int var5) {
      if (com.ea.sims3.f.a11()) {
         if (a11 == null) {
            a(a2 + "drawSubString::Graphics are null, use setGraphics(Graphics)", 4);
         }

         if (a3 == null) {
            a(a2 + "drawSubString ::Font is null, use setFont(Object)", 4);
         }

         if (!com.ea.sims3.f.c3() && !com.ea.sims3.f.b5() && !com.ea.sims3.f.d3()) {
            a(a2 + "drawSubString ::no font support enabled, you must enable at least 1 type of font support in SDKConfig", 4);
         }
      }

      if ((a4[10] & 4) != 0 && com.ea.sims3.f.c3()) {
         if (a12 == null) {
            a12 = new StringBuffer();
         }

         a12.setLength(0);

         for (int var27 = var1; var27 < var1 + var2; var27++) {
            char var28;
            if ((var28 = var0.a2(var27)) != 173 || var27 == var1 + var2 - 1) {
               a12.append(var28);
            }
         }

         if (a4[7] != 0 && com.ea.sims3.f.a11()) {
            a(a2 + "drawSubString ::rendering of system fonts does not support transform: " + a4[7], 4);
         }

         a11.setColor(j);
         a11.drawString(a12.toString(), var3, var4, var5);
      } else {
         if (com.ea.sims3.f.b5() || com.ea.sims3.f.d3()) {
            int var6 = a4[3] & 255;
            int var7 = c();
            int var8 = a4[8];
            int var9 = a4[9];
            boolean var10 = h != -1;
            boolean var11 = (a4[10] & 2) != 0;
            boolean var12 = (a4[10] & -128) != 0;
            int var13 = 0;
            int var14 = 0;
            if ((var5 & g) != 0) {
               var13 = 0 - a(var0, var1, var2);
            } else if ((var5 & d) != 0) {
               var13 = 0 - (a(var0, var1, var2) >> 1);
            }

            if ((var5 & c) != 0) {
               var14 = 0 - var6;
            } else if ((var5 & b) != 0) {
               var14 = 0 - var7;
            }

            if (var8 == 1) {
               var3 += var13;
               var4 += var14;
            } else if (var8 == 4) {
               var3 -= var13;
               var4 -= var14;
            } else if (var8 == 2) {
               var3 -= var14;
               var4 += var13;
            } else if (var8 == 8) {
               var3 -= var14;
               var4 -= var13;
            }

            b7[0] = var3;
            b7[1] = var4;
            if ((var3 = var0.a()) != 1 && var3 != 0 && var3 != f3[b6] && com.ea.sims3.f.a11()) {
               a(
                  a2
                     + "drawSubString::SDKString ["
                     + var0.toString()
                     + "] has encoding="
                     + var0.a()
                     + " but the currently set language has encoding: "
                     + f3[b6],
                  4
               );
            }

            byte var25 = (byte)((var8 & 5) != 0 ? (var8 & 4) >> 1 : ((var9 & 1) << 1) + ((~var9 & 2) >> 1));
            byte var26 = (byte)((var8 & 10) != 0 ? (var8 & 8) >> 2 : (var9 & 2) + (~var9 & 1));
            if (com.ea.sims3.f.a11()) {
               a(a2 + "drawSubString::offset = " + var1 + " len = " + var2 + " \"" + var0.toString() + "\"", 1);
               if (var11 && com.ea.sims3.f.d3() && a4[7] != 0) {
                  a(a2 + "drawSubString ::rendering of rectangle fonts does not currently support transform: " + a4[7], 4);
               }
            }

            var9 = var8 = a11.getColor();

            for (int var31 = var1; var31 < var1 + var2; var31++) {
               short var32;
               if ((var32 = var0.a11(var31)) != 173 || var31 == var1 + var2 - 1) {
                  short var15;
                  int var16 = a(var15 = a((short)var32, var3));
                  if (var15 == -1) {
                     if (var32 != 32) {
                        if (com.ea.sims3.f.a11()) {
                           a(a2 + "drawSubString::character not in Font: \\u" + (var3 != 0 && var32 >= 128 ? b((short)var32) : var32), 4);
                        }

                        a11.setColor(65280);
                        a11.fillRect(b7[0], b7[1], var16, var7);
                        a11.setColor(16711680);
                        var9 = 16711680;
                        a11.drawLine(b7[0], b7[1], b7[0] + var16, b7[1] + var7);
                        a11.drawLine(b7[0], b7[1] + var7, b7[0] + var16, b7[1]);
                     }

                     a(b7, var16);
                  } else {
                     short var10001 = ++var15;
                     var15++;
                     var32 = (short)(b4[var10001] & 255);

                     for (int var17 = 0; var17 < var32; var17++) {
                        if (var11 && com.ea.sims3.f.d3()) {
                           int var18 = b4[var15++] & 255;
                           byte var19 = b4[var15++];
                           byte var20 = b4[var15++];
                           int var21;
                           int var22;
                           if (var12) {
                              var21 = b4[var15] >> 4 & 15;
                              var22 = b4[var15++] & 15;
                           } else {
                              var21 = b4[var15++] & 255;
                              var22 = b4[var15++] & 255;
                           }

                           if (a6[var18] != var9) {
                              a11.setColor(a6[var18]);
                              var9 = a6[var18];
                           }

                           a11.fillRect(b7[0] + var19, b7[1] + var6 - var20, var21, var22);
                        }

                        if (!var11 && com.ea.sims3.f.b5()) {
                           int var45 = b4[var15++] & 255;
                           int var46 = b4[var15++] & 255;
                           int var47 = b4[var15++];
                           int var49 = b4[var15++];
                           int var51 = b4[var15++] & 255;
                           int var23 = b4[var15++] & 255;
                           c4[0] = var47;
                           c4[1] = var49 - var6;
                           c4[2] = -var47 - var51;
                           c4[3] = var6 - var49 - var23;
                           var47 = b7[0] + c4[var25];
                           var49 = b7[1] + c4[var26];
                           if (var10) {
                              a10();
                           } else {
                              a((Image)b2, var45, var46, var51, var23, a4[7], var47, var49, 0);
                           }
                        }
                     }

                     a(b7, var16);
                  }
               }
            }

            a11.setColor(var8);
         }
      }
   }

   public static void a(b var0, short[] var1, int var2, int var3, int var4, int var5, int var6) {
      if (com.ea.sims3.f.a11()) {
         if (a11 == null) {
            a(a2 + "drawWrappedString::Graphics are null, use setGraphics(Graphics)", 4);
         }

         if (a3 == null) {
            a(a2 + "drawWrappedString::Font is null, use setFont(Object)", 4);
         }
      }

      int var7 = c();
      int var8 = 0;
      int var9 = 0;
      int var10 = 0;
      if ((var6 & e) != 0) {
         var10 = var1[0] * var7 - a4[2] >> 1;
         var6 ^= e;
      } else if ((var6 & b) != 0) {
         var10 = var1[0] * var7 - a4[2];
         var6 ^= b;
      }

      var6 |= a;
      if (a4[9] == 1) {
         var4 -= var10;
         var8 = var7;
      } else if (a4[9] == 4) {
         var4 += var10;
         var8 = -var7;
      } else if (a4[9] == 2) {
         var5 -= var10;
         var9 = var7;
      } else {
         var5 += var10;
         var9 = -var7;
      }

      short var15 = var1[(var1[0] & '\uffff') + 2];

      for (int var16 = var2; var16 < var2 + var3; var16++) {
         int var11 = var1[var16] & '\uffff';
         int var12 = (var1[var16 + 1] & '\uffff') - var11;
         short var13;
         if ((var13 = var0.a11((var1[var16 + 1] & '\uffff') - 1)) == var15 || var13 == 32) {
            var12--;
         }

         a(var0, var11, var12, var4, var5, var6);
         var4 += var8;
         var5 += var9;
      }
   }

   private static int a(short var0) {
      int var1 = a4[6] & 255;
      return var0 == -1 ? (a4[5] & 0xFF) + var1 : (b4[var0 & 65535] & 0xFF) + var1;
   }

   private static short a(short var0, int var1) {
      if (var1 == 0) {
         return c2[a(b3, var0, 0, b3.length - 1)];
      } else {
         return var0 >= 128 && var1 != f3[b6] ? -1 : c2[a5[var0] & '\uffff'];
      }
   }

   public static short[] a(b var0, short[] var1, int var2, short var3) {
      return a(var0, 0, var0.a4, var1, var2, var3);
   }

   private static short[] a(b var0, int var1, int var2, short[] var3, int var4, short var5) {
      if (a3 == null && com.ea.sims3.f.a11()) {
         a(a2 + "wrapSubString ::Font is null, use setFont(Object)", 4);
      }

      boolean var6 = false;
      if (var3 == null) {
         if (e2 == null) {
            if (com.ea.sims3.f.a11()) {
               a(a2 + "wrapSubString ::allocating initial _wrapOffsets[] with size: " + 50, 1);
            }

            e2 = new short[50];
         }

         var3 = e2;
         var6 = true;
      }

      int var7 = var0.a();
      int var8 = 0;
      int var9 = 0;
      int var10 = -1;
      var3[1] = (short)var1;
      int var11 = 2;

      for (int var12 = var1; var12 < var2 + var1; var12++) {
         short var13 = var0.a11(var12);
         if (var11 + 3 >= var3.length && var6) {
            if (com.ea.sims3.f.a11()) {
               a(a2 + "wrapSubString ::_wrapOffsets[] is growing to size: " + (var3.length + 50), 1);
            }

            e2 = new short[var3.length + 50];
            System.arraycopy(var3, 0, e2, 0, var3.length);
            var3 = e2;
         }

         if (var13 == var5) {
            var3[var11++] = (short)(var12 + 1);
            var8 = 0;
            var9 = 0;
            var10 = -1;
         } else {
            int var14 = 0;
            if ((a4[10] & 4) != 0 && com.ea.sims3.f.c3()) {
               var14 = ((Font)a3).charWidth(var0.a2(var12));
            } else if (com.ea.sims3.f.b5() || com.ea.sims3.f.d3()) {
               var14 = a(a(var13, var7));
            }

            var8 += var14;
            var9 += var14;
            if (var8 > var4) {
               if (var10 == -1) {
                  var3[var11++] = (short)var12;
                  var8 = var14;
                  var9 = 0;
               } else {
                  var3[var11++] = (short)(var10 + 1);
                  if (var13 != 32 && var13 != 173) {
                     var10 = -1;
                  } else {
                     var10 = var12;
                  }

                  var8 = var9;
                  var9 = 0;
               }
            } else if (var13 == 32 || var13 == 173) {
               var10 = var12;
               var9 = 0;
            }
         }
      }

      var3[0] = (short)(var11 - 1);
      var3[var11] = (short)(var2 + var1);
      var3[var11 + 1] = var5;
      if (com.ea.sims3.f.a11()) {
         for (int var15 = 0; var15 <= var11 + 1; var15++) {
            a(a2 + "wrapSubString::offsets[" + var15 + "] = " + (var3[var15] & 65535), 1);
         }
      }

      if (var6) {
         short[] var16 = new short[var11 + 2];
         System.arraycopy(var3, 0, var16, 0, var16.length);
         var3 = var16;
      }

      return var3;
   }

   public static int a(b var0) {
      return a(var0, 0, var0.a4);
   }

   public static int a(b var0, int var1, int var2) {
      if (a3 == null && com.ea.sims3.f.a11()) {
         a(a2 + "getSubStringSize::no font loaded, use setFont(Object)", 4);
      }

      int var3 = 0;
      if ((a4[10] & 4) != 0 && com.ea.sims3.f.c3()) {
         var3 = ((Font)a3).substringWidth(var0.toString(), var1, var2);
      } else if (com.ea.sims3.f.b5() || com.ea.sims3.f.d3()) {
         int var4 = var0.a();

         for (int var5 = var1; var5 < var1 + var2; var5++) {
            var3 += a(a(var0.a11(var5), var4));
         }
      }

      return var3;
   }

   private static boolean b8(int var0) {
      byte var1 = c3[var0];
      return a7[var1] != null && e3[var1] == var0;
   }

   public static int a3(int var0) {
      if (com.ea.sims3.f.a11()) {
         if (b6 == -1) {
            a(a2 + "loadStringsChunk::language must be selected first!", 4);
         }

         if (var0 < 1 || var0 >= c3.length) {
            a(a2 + "loadStringsChunk::chunkID '" + var0 + "' is out of bounds, valid range is [1," + c3.length + "] inclusive", 4);
         }

         if (a14 == null) {
            a(a2 + "loadStringsChunk::midlet is null, use setMidlet(MIDlet)", 4);
         }
      }

      if (b8(var0) && !a13) {
         if (com.ea.sims3.f.a11()) {
            a(a2 + "loadStringsChunk::chunkID: " + var0 + " already loaded, it won't be loaded again", 3);
         }

         return f2[var0];
      } else {
         InputStream var1 = null;

         try {
            byte var2 = (d3[var0] & 64) == 0 ? b6 : 0;
            if (a9 == 0) {
               if (com.ea.sims3.f.a11()) {
                  a(a2 + "loadStringsChunk::chunkID: " + var0 + " is being loaded using CHUNK_CONFIG_CONCAT for resIndex: " + var2, 1);
               }

               var1 = a14.getClass().getResourceAsStream("/t_" + a10[var2]);

               for (int var3 = 1; var3 < var0; var3++) {
                  if ((d3[var3] & 64) == 0 || var2 == 0) {
                     a(var1, (long)((f2[var3] + 1 << 1) + (b5[var2][var3] & '\uffff')));
                  }
               }
            } else if (a9 == 1) {
               if (com.ea.sims3.f.a11()) {
                  a(a2 + "loadStringsChunk::chunkID: " + var0 + " is being loaded using CHUNK_CONFIG_SEPARATE for resIndex: " + var2, 1);
               }

               var1 = a14.getClass().getResourceAsStream("/t_" + a10[var2] + var0);
            } else if (com.ea.sims3.f.a11()) {
               a(a2 + "loadStringsChunk::illegal value of chunk config: " + a9, 4);
            }

            byte var6 = c3[var0];
            if (a8[var6] == null) {
               a8[var6] = new short[h2[var6]];
               if (com.ea.sims3.f.a11()) {
                  a(a2 + "loadStringsChunk::allocating _chunkStringOffsets[" + var6 + "] to be of size: " + h2[var6], 1);
               }
            }

            for (int var4 = 0; var4 < f2[var0] + 1; var4++) {
               h3[0] = (byte)var1.read();
               h3[1] = (byte)var1.read();
               a8[var6][var4] = a2(h3, 0);
               if (com.ea.sims3.f.a11()) {
                  a(a2 + "loadStringsChunk::_chunkStringOffsets[" + var6 + "][" + var4 + "]: " + (a8[var6][var4] & 65535), 1);
               }
            }

            if (a7[var6] == null) {
               a7[var6] = new byte[g2[var6] & '\uffff'];
               if (com.ea.sims3.f.a11()) {
                  a(a2 + "loadStringsChunk::allocating _chunkStringData[" + var6 + "] to be of size: " + (g2[var6] & 65535), 1);
               }
            }

            a(var1, a7[var6], 0, b5[var2][var0] & '\uffff');
            e3[var6] = (byte)var0;
            if (com.ea.sims3.f.a11()) {
               a(a2 + "loadStringsChunk::_groupMap[" + var6 + "]: " + var0, 1);
            }

            var1.close();
         } catch (IOException var5) {
            if (com.ea.sims3.f.a11()) {
               a(a2 + "loadStringsChunk::IOException, e.msg: " + var5.getMessage(), 4);
            }
         }

         return f2[var0];
      }
   }

   public static void b7(int var0) {
      if (com.ea.sims3.f.a11()) {
         a(a2 + "freeStringsChunk::freeing chunkID: " + var0, 1);
      }

      if (var0 > 0) {
         a7[c3[var0]] = null;
         a8[c3[var0]] = null;
      } else {
         if (com.ea.sims3.f.a11()) {
            a(a2 + "freeStringsChunk::you are not allowed to free chunkID: " + var0, 3);
         }
      }
   }

   public static int a(byte[] var0) {
      int var1 = 0;
      if (a14 == null) {
         if (com.ea.sims3.f.a11()) {
            a(a2 + "loadTextHeader::the MIDlet must be set first, use setMidlet(MIDlet)", 4);
         }

         return 0;
      } else {
         var1++;
         byte var2;
         if ((var2 = var0[0]) != 1) {
            if (com.ea.sims3.f.a11()) {
               a(a2 + "loadTextHeader::unsupported text header format:" + var2, 4);
            }

            return 0;
         } else {
            var1++;
            a9 = var0[1];
            var1++;
            int var10000 = (var0[2] & 255) << 16;
            var1++;
            var10000 |= (var0[3] & 255) << 8;
            var1++;
            i = var10000 | var0[4] & 255;
            var2 = (byte)a2(var0, 5);
            var1 += 2;
            var1++;
            byte var3 = var0[7];
            var1++;
            short var4 = var0[8];
            var1++;
            short var5 = var0[9];
            int var6 = var4 + 1;
            int var7 = var5 + 1;
            if (com.ea.sims3.f.a11()) {
               a(a2 + "loadTextHeader::_chunkConfig = " + a9, 1);
               a(a2 + "loadTextHeader::_textEncodings = " + i, 1);
               a(a2 + "loadTextHeader::numHeaderStrings = " + var2, 1);
               a(a2 + "loadTextHeader::numLangs = " + var3, 1);
               a(a2 + "loadTextHeader::numGroups = " + var4, 1);
               a(a2 + "loadTextHeader::numChunks = " + var5, 1);
               a(a2 + "loadTextHeader::numStorageChunks = " + var6, 1);
               a(a2 + "loadTextHeader::numActualChunks = " + var7, 1);
            }

            f3 = new byte[var3];
            a10 = new String[var3];
            g3 = new byte[var3];
            f2 = new short[var7];
            d3 = new byte[var7];
            c3 = new byte[var7];
            b5 = new short[var3][var7];
            g2 = new short[var7];
            h2 = new short[var7];
            e3 = new byte[var7];
            a7 = new byte[var6][];
            a8 = new short[var6][];
            f2[0] = var2;
            var4 = (short)(var2 + 1);

            for (int var22 = 0; var22 < var3; var22++) {
               a10[var22] = new String(var0, var1, 5).trim();
               var1 += 5;
               f3[var22] = var0[var1++];
               g3[var22] = var0[var1++];
               if (com.ea.sims3.f.a11()) {
                  a(a2 + "loadTextHeader::_languageISOCodes[" + var22 + "]: " + a10[var22], 1);
                  a(a2 + "loadTextHeader::_languageEncodings[" + var22 + "]: " + f3[var22], 1);
                  a(a2 + "loadTextHeader::_languageFontDifferentiator[" + var22 + "]: " + g3[var22], 1);
               }

               if (var2 > 0) {
                  if (com.ea.sims3.f.a11()) {
                     a(a2 + "loadTextHeader::reading header string offsets for lang: " + var22, 1);
                  }

                  if (a8[0] == null) {
                     a8[0] = new short[var3 * var4];
                  }

                  for (int var25 = 0; var25 < var4; var25++) {
                     a8[0][var22 * var4 + var25] = a2(var0, var1);
                     if (com.ea.sims3.f.a11()) {
                        a(
                           a2
                              + "loadTextHeader::_chunkStringOffsets[HEADER_CHUNK_INDEX]["
                              + (var22 * var4 + var25)
                              + "]: "
                              + (a8[0][var22 * var4 + var25] & 65535),
                           1
                        );
                     }

                     var1 += 2;
                  }
               }
            }

            if (var2 > 0) {
               var5 = a8[0][var3 * var4 - 1];
               if (com.ea.sims3.f.a11()) {
                  a(a2 + "loadTextHeader::headerChunkLen = " + var5, 1);
               }

               a7[0] = new byte[var5];
               System.arraycopy(var0, var1, a7[0], 0, var5);
               var1 += var5;
            } else if (com.ea.sims3.f.a11()) {
               a(a2 + "loadTextHeader::skipping allocation and reading of header " + "string offsets and data because numHeaderStrings == 0", 1);
            }

            for (int var24 = 1; var24 < var7; var24++) {
               if (com.ea.sims3.f.a11()) {
                  a(a2 + "loadTextHeader::reading data for chunk: " + var24, 1);
               }

               d3[var24] = (byte)(var0[var1] & 192);
               c3[var24] = (byte)(var0[var1] & 63);
               f2[var24] = a2(var0, ++var1);
               var1 += 2;
               if (f2[var24] + 1 > h2[c3[var24]]) {
                  h2[c3[var24]] = (short)(f2[var24] + 1);
                  if (com.ea.sims3.f.a11()) {
                     a(a2 + "loadTextHeader::setting _groupMaxNumOffsets[" + c3[var24] + "]: " + h2[c3[var24]], 1);
                  }
               }

               if (com.ea.sims3.f.a11()) {
                  a(a2 + "loadTextHeader::_chunkFlags[" + var24 + "]: " + d3[var24], 1);
                  a(a2 + "loadTextHeader::_chunkGroup[" + var24 + "]: " + c3[var24], 1);
                  a(a2 + "loadTextHeader:: _numStringsPerChunk[" + var24 + "]: " + f2[var24], 1);
               }

               for (int var26 = 0; var26 < var3; var26++) {
                  b5[var26][var24] = a2(var0, var1);
                  if (com.ea.sims3.f.a11()) {
                     a(a2 + "loadTextHeader::chunkSize[" + var26 + "][" + var24 + "]: " + (b5[var26][var24] & 65535), 1);
                  }

                  if ((b5[var26][var24] & '\uffff') > (g2[c3[var24]] & '\uffff')) {
                     g2[c3[var24]] = b5[var26][var24];
                     if (com.ea.sims3.f.a11()) {
                        a(a2 + "loadTextHeader::setting _groupMaxSize[" + c3[var24] + "]: " + (g2[c3[var24]] & 65535), 1);
                     }
                  }

                  var1 += 2;
               }
            }

            if (f3[b6] > 1) {
               a2(f3[b6]);
            }

            return var3;
         }
      }
   }

   public static b a(int var0, int var1, b var2) {
      if (com.ea.sims3.f.a11()) {
         a(a2 + "getHeaderString::getting stringID: " + var1 + " for language: " + var0, 1);
         if (var1 >= f2[0]) {
            a(a2 + "getHeaderString::stringID: " + var1 + " is not in the header strings chunk, use getString(int,SDKString)", 4);
         }
      }

      var0 = var1 + (f2[0] + 1) * var0;
      if (var2 == null) {
         var2 = new b();
      }

      var2.a2 = a7[0];
      var2.a3 = a8[0][var0];
      var2.a4 = (short)(a8[0][var0 + 1] - a8[0][var0] >> 1);
      var2.a5 = 0;
      if (com.ea.sims3.f.a11()) {
         a(a2 + "getHeaderString::string.toString(): " + var2.toString(), 1);
         a(a2 + "getHeaderString::string.off: " + var2.a3, 1);
         a(a2 + "getHeaderString::string.len: " + var2.a4, 1);
         a(a2 + "getHeaderString::string.encoding: " + var2.a5, 1);
      }

      return var2;
   }

   public static b a(int var0, b var1) {
      if (com.ea.sims3.f.a11()) {
         a(a2 + "getString::getting stringID: " + var0, 1);
         if (b6 == -1) {
            a(a2 + "getString::language must be selected first!", 4);
         }
      }

      int var2 = -1;
      short var3 = 0;

      for (int var4 = 0; var4 < f2.length; var4++) {
         var3 += f2[var4];
         if (var0 < var3) {
            var2 = var4;
            if (com.ea.sims3.f.a11()) {
               a(a2 + "getString::found stringID: " + var0 + " in chunkID: " + var4, 1);
            }
            break;
         }
      }

      if (com.ea.sims3.f.a11()) {
         if (var2 == -1) {
            a(a2 + "getString::failed to get the chunkID for stringID: " + var0, 4);
         } else if (var2 == 0) {
            a(a2 + "getString::use getHeaderString(int,int,SDKString) to load stringID: " + var0, 4);
         }
      }

      int var7 = var0 - (var3 - f2[var2]);
      byte var5 = c3[var2];
      if (com.ea.sims3.f.a11()) {
         a(a2 + "getString::calculated first stringID for this chunk as: " + (var3 - f2[var2]), 1);
         a(a2 + "getString::chunkOffset: " + var7, 1);
         a(a2 + "getString::groupID: " + var5, 1);
         if (a7[var5] == null) {
            a(a2 + "getString::first load chunkID: " + var2 + " before you can get stringID: " + var0, 4);
         }
      }

      if (var1 == null) {
         var1 = new b();
      }

      var1.a2 = a7[var5];
      var1.a3 = a8[var5][var7] & '\uffff';
      var1.a4 = (short)((a8[var5][var7 + 1] & '\uffff') - (a8[var5][var7] & '\uffff'));
      byte var6 = f3[b6];
      if ((d3[var2] & -64) != 0) {
         var6 = 0;
      }

      if (var6 == 0) {
         var1.a4 = (short)(var1.a4 >> 1);
      }

      var1.a5 = var6;
      if (com.ea.sims3.f.a11()) {
         a(a2 + "getString::string.toString(): " + var1.toString(), 1);
         a(a2 + "getString::string.off: " + var1.a3, 1);
         a(a2 + "getString::string.len: " + var1.a4, 1);
         a(a2 + "getString::string.encoding: " + var1.a5, 1);
      }

      return var1;
   }

   public static int d() {
      return b6;
   }

   public static boolean a13(int var0) {
      byte var1 = b6;
      if (a10 != null && var0 >= 0 && var0 < a10.length) {
         if (com.ea.sims3.f.a11()) {
            a(a2 + "setCurrentLanguage::setting _langIndex: " + var0, 1);
         }

         b6 = (byte)var0;
      } else if (com.ea.sims3.f.a11()) {
         a(a2 + "setCurrentLanguage::invalid index: " + var0, 4);
      }

      if (b6 != var1) {
         a13 = true;

         for (int var2 = 1; var2 < c3.length; var2++) {
            if (com.ea.sims3.f.a11()) {
               a(a2 + "setCurrentLanguage::checking storage for chunkID: " + var2 + " ,groupID: " + c3[var2], 1);
            }

            if (b8(var2) && (d3[var2] & 64) == 0) {
               a3(var2);
            }
         }

         a13 = false;
         if (f3[b6] != f3[var1] && f3[b6] > 1) {
            a2(f3[b6]);
         }
      }

      return b6 == var0;
   }

   public static void a(MIDlet var0) {
      a14 = var0;
   }

   public static int b2(int var0) {
      if (a14 == null && com.ea.sims3.f.a11()) {
         a(a2 + "chooseLanguage::midlet instance not set. Call setMidlet() first!", 4);
      }

      if (a10.length == 1) {
         return 0;
      } else {
         String var1 = a14.getAppProperty("Force-Locale");
         String[] var2 = new String[a10.length];
         System.arraycopy(a10, 0, var2, 0, var2.length);
         if (var0 == -1) {
            byte var3 = 0;
            if (var1 == null) {
               var3 = 2;
            } else if ((var1 = var1.toLowerCase().trim()).length() < 2) {
               var3 = 2;
            } else if (var1.equals("menu")) {
               var3 = 1;
            } else if (var1.startsWith("auto-")) {
               var3 = 3;
               var1 = var1.substring(5);
            } else {
               var3 = 4;
            }

            for (int var5 = 0; var5 < var2.length; var5++) {
               if (var2[var5].length() > 2) {
                  String var4 = var2[var5].substring(0, 2);
                  boolean var7 = true;

                  for (int var6 = 0; var6 < var2.length; var6++) {
                     if (var5 != var6 && var2[var6].startsWith(var4)) {
                        var7 = false;
                        break;
                     }
                  }

                  if (var7) {
                     var2[var5] = var4;
                  }
               }
            }

            if (var3 == 2 || var3 == 3 || var3 == 1) {
               var0 = a(var2);
               if (var3 == 1 && var0 > -1) {
                  var0 = -1 - var0;
               }
            }

            if (var3 == 4 || var3 == 3 && var0 == -1) {
               for (int var9 = 0; var9 < var2.length; var9++) {
                  if (var1.startsWith(var2[var9])) {
                     var0 = var9;
                     break;
                  }
               }
            }

            if (var3 == 4 && var0 == -1) {
               var0 = a(var2);
            }
         }

         return var0;
      }
   }

   public static int c(int var0) {
      if (com.ea.sims3.f.a11() && var0 < 0 || var0 >= g3.length) {
         a(a2 + "getFontDifferentiator::langIndex out of bounds", 4);
      }

      return g3[var0];
   }

   public static void a(Graphics var0) {
      a11 = null;
      a11 = var0;
      if (a3 != null && (a4[10] & 4) != 0 && com.ea.sims3.f.c3()) {
         a11.setFont((Font)a3);
      }
   }

   public static void a(String var0, int var1) {
      if (var1 >= com.ea.sims3.f.a() && com.ea.sims3.f.a11()) {
         if (b8 == null) {
            b8 = new StringBuffer();
         }

         b8.setLength(0);
         switch (var1) {
            case 1:
               b8.append("I:");
               break;
            case 2:
               b8.append("S:");
               break;
            case 3:
               b8.append("W:");
               break;
            case 4:
               b8.append("E:");
         }

         if (var1 >= 3) {
            Alert var2;
            (var2 = new Alert("", var0, null, AlertType.ERROR)).setTimeout(-2);
            Display.getDisplay(a14).setCurrent(var2);
         }

         b8.append(var0);
         System.out.println(b8.toString());
      }
   }

   public static short a2(byte[] var0, int var1) {
      return (short)((var0[var1] & 255) << 8 | var0[var1 + 1] & 255);
   }

   public static short a2(short var0) {
      int var1;
      if ((var1 = a(d2, var0, 0, d2.length - 1)) == d2.length && com.ea.sims3.f.a11()) {
         a(a2 + "utf2iso::character code: " + var0 + " is not defined in encoding: " + f3[b6], 3);
      }

      return (short)(var1 + 128);
   }

   public static short b(short var0) {
      if ((var0 < 128 || var0 > 255) && com.ea.sims3.f.a11()) {
         a(a2 + "iso2utf::character code: " + var0 + " is out of bounds for this method (must be in the range 128-255)", 4);
      }

      return d2[var0 - 128];
   }

   private static byte a(String[] var0) {
      byte var1 = -1;
      String var2;
      if ((var2 = System.getProperty("microedition.locale")) != null) {
         int var3;
         if ((var3 = (var2 = var2.toLowerCase().trim()).length()) < 2) {
            return -1;
         }

         for (int var4 = 0; var4 < var0.length; var4++) {
            if (var0[var4].length() > var3 && var0[var4].startsWith(var2)) {
               var1 = -1;
               break;
            }

            if (var2.startsWith(var0[var4])) {
               var1 = (byte)var4;
               break;
            }
         }
      }

      return var1;
   }

   private static void a10() {
      if (com.ea.sims3.f.a11()) {
         a(a2 + "drawRegion::the function is called with short[] parameter on non-Nokia-API phone!", 4);
      }
   }

   private static void a(Image var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      a11.drawRegion(var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   private static int a(short[] var0, int var1, int var2, int var3) {
      if (var2 > var3) {
         return var0.length;
      } else {
         int var4 = var2 + var3 >> 1;
         int var5 = var0[var4] & '\uffff';
         if (var1 == var5) {
            return var4;
         } else {
            return var1 < var5 ? a(var0, var1, var2, var4 - 1) : a(var0, var1, var4 + 1, var3);
         }
      }
   }

   private static void a2(byte var0) {
      if (com.ea.sims3.f.a11()) {
         if (var0 < 2) {
            a(a2 + "loadCharMap::encoding: " + var0 + " should not be searched in the charmap binary!", 4);
         } else {
            a(a2 + "loadCharMap::loading charmap for encoding: " + var0, 1);
         }
      }

      int var1 = 0;

      for (int var2 = 1; var2 < var0; var2++) {
         if ((i & 1 << var2) != 0) {
            var1++;
         }
      }

      if (d2 == null) {
         d2 = new short[128];
      }

      InputStream var5 = null;

      try {
         a(var5 = a14.getClass().getResourceAsStream("/charmap"), (long)(var1 << 8));

         for (int var4 = 0; var4 < 128; var4++) {
            h3[0] = (byte)var5.read();
            h3[1] = (byte)var5.read();
            d2[var4] = a2(h3, 0);
         }

         var5.close();
      } catch (IOException var3) {
         if (com.ea.sims3.f.a11()) {
            a(a2 + "loadCharMap::IOException, e.msg: " + var3.getMessage(), 4);
         }
      }

      if (a3 != null && (a4[10] & 4) == 0 && (com.ea.sims3.f.b5() || com.ea.sims3.f.d3())) {
         b4();
      }
   }

   private static void b4() {
      if (com.ea.sims3.f.a11()) {
         a(a2 + "refreshISOOffsets::mapping offsets for encoding: " + f3[b6], 1);
      }

      for (short var0 = 128; var0 < 256; var0++) {
         a5[var0] = (short)a(b3, b(var0), 0, b3.length - 1);
      }

      a4[11] = f3[b6];
   }

   private static Object[] a4(byte[] var0) {
      int var1 = 0;
      byte var2 = var0[0];
      if (com.ea.sims3.f.a11() && (var2 & 63) != 33) {
         a(a2 + "loadFontChunk::unsupported font version:" + var2, 4);
      }

      // var3/var4 are reused later in this method for ints
      boolean var3b = (var2 & 128) != 0;
      boolean var4b = (var2 & 64) != 0;
      if (var3b && !com.ea.sims3.f.d3()) {
         a(a2 + "loadFontChunk::detected rectangle font but support disabled, use SDKConfig to enable", 4);
      }

      byte[] var5 = new byte[12];
      System.arraycopy(var0, 1, var5, 0, 7);
      var1 = (byte)8;
      var5[7] = 0;
      var5[8] = var5[0];
      var5[9] = var5[1];
      if (var3b) {
         var5[10] = 2;
      } else {
         var5[10] = 1;
      }

      if (var4b) {
         var5[10] |= -128;
      }

      short var6 = a2(var0, 8);
      var1 += 2;
      short var7 = a2(var0, 10);
      var1 += 2;
      if (com.ea.sims3.f.a11()) {
         a(a2 + "loadFontChunk::fontVersion = " + var2, 1);
         a(a2 + "loadFontChunk::textDirection = " + var5[0], 1);
         a(a2 + "loadFontChunk::lineDirection = " + var5[1], 1);
         a(a2 + "loadFontChunk::leading = " + var5[2], 1);
         a(a2 + "loadFontChunk::ascent = " + var5[3], 1);
         a(a2 + "loadFontChunk::descent = " + var5[4], 1);
         a(a2 + "loadFontChunk::space space= " + var5[5], 1);
         a(a2 + "loadFontChunk::char space= " + var5[6], 1);
         a(a2 + "loadFontChunk::numChars = " + var6, 1);
         a(a2 + "loadFontChunk::numTotalGlyphs = " + var7, 1);
      }

      short[] var15 = new short[var6];

      for (int var8 = 0; var8 < var6; var8++) {
         var15[var8] = a2(var0, var1);
         var1 += 2;
      }

      short[] var21 = new short[var6 + 1];

      for (int var9 = 0; var9 < var6; var9++) {
         var21[var9] = a2(var0, var1);
         var1 += 2;
      }

      var21[var6] = -1;
      int var22 = 6;
      if (var3b && com.ea.sims3.f.d3()) {
         var22 = var4b ? 4 : 5;
      }

      int var4 = (var6 << 1) + var7 * var22;
      byte[] var20 = new byte[var4];
      System.arraycopy(var0, var1, var20, 0, var4);
      var1 += var4;
      int[] var19 = null;
      if (var3b && com.ea.sims3.f.d3()) {
         int var3 = var0[var1++];
         var19 = new int[var3];

         for (int var23 = 0; var23 < var3; var23++) {
            var19[var23] = (var0[var1] & 255) << 24 | (var0[var1 + 1] & 255) << 16 | (var0[var1 + 2] & 255) << 8 | var0[var1 + 3] & 255;
            var1 += 4;
         }
      }

      short[] var17 = new short[256];

      for (short var24 = 0; var24 < 128; var24++) {
         var17[var24] = (short)a(var15, var24, 0, var6 - 1);
      }

      var5[11] = 1;
      return new Object[]{var5, var15, var17, var21, var20, var19};
   }

   private static int a(InputStream var0, byte[] var1, int var2, int var3) {
      try {
         return var0.read(var1, var2, var3);
      } catch (IOException var4) {
         throw new IllegalStateException(var4);
      }
   }

   private k() {
   }

   static {
      if (com.ea.sims3.f.a11()) {
         a2 = "SDKUtils::";
      }
   }
}
