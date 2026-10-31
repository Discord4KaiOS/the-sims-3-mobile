package com.ea.sims3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class Lot implements SpriteIndex, SoundIndex, Resources {
   private GameCanvas a;
   private byte[] a2;
   private short[] b;
   private short[] c;
   private int[] c2;
   private short[] d;
   private short[] e;
   private short[] f;
   private byte[] b2;
   private byte[] c3;
   private short[] g;
   private short[][] a3;
   private byte[][] a4;
   private byte[][] b3;
   private short[][] b4;
   private short[] h;
   private short[][] c4;
   private byte[][] c5;
   private short[] i;
   private short[] j;
   private int[] d2;
   private short[] k;
   private short[] l;
   private byte[] d3;
   private byte[] e2;
   private byte[] f2;
   private short[][] d4;
   private short[] m;
   private byte[][] d5;
   private byte[][] e3;
   private byte[][] f3;
   private byte[][] g2;
   private byte[][] h2;
   private byte[][] i2;
   private byte[][] j2;
   private byte[][] k2;
   private byte[][] l2;
   private byte[][] m2;
   private byte[][] n;
   private short[][] e4;
   private byte[][] o;
   private int g3;
   private static final byte[] g4 = new byte[]{0, 1, 2};
   private static final short[] n2 = new short[]{166, 167, 168};
   public byte a5;
   private byte[] h3;
   private byte[] i3;
   private short[] o2;
   private byte[] j3;
   public int a6;
   public int b5;
   public int c6;
   public int d6;
   public static final int[] a7 = new int[]{128, 256, 512, 1024};
   private int h4;
   private int i4;
   private int j4;
   private int k3;
   private int l3;
   private int m3;
   private int n3;
   private int o3;
   private byte[] k4;
   public Tile a8;
   private Tile b6;
   public int e5;
   public int f4;
   private int[][] a9;
   private byte[][] p;
   private int p2;
   private int q;
   private int r;
   private int s;
   private int[] e6;
   private int t;
   private int u;
   private int v;
   private short[] p3;
   private int w;
   private short[] q2;
   private int x;
   private short[][] f5;
   private int[][] b7;
   private int[][] c7;
   private int[][] d7;
   private short[] r2;
   private int y;

   public Lot(GameCanvas var1) {
      this.a = var1;
      this.e2();
      this.f2();
      this.o2();
      this.p2();
   }

   private static final short a(DataInputStream var0) throws IOException {
      return var0.readShort();
   }

   private void e2() {
      ResourceLoader var1 = this.a.a6;

      try {
         DataInputStream var2;
         short var3;
         byte[] var4 = new byte[var3 = (var2 = new DataInputStream(var1.openStream(64))).readShort()];
         short[] var5 = new short[var3];
         short[] var6 = new short[var3];
         int[] var7 = new int[var3];
         short[] var8 = new short[var3];
         short[] var9 = new short[var3];
         short[] var10 = new short[var3];
         byte[] var11 = new byte[var3];
         byte[] var12 = new byte[var3];
         short[] var13 = new short[var3];
         short[][] var14 = new short[var3][];
         byte[][] var15 = new byte[var3][];
         byte[][] var16 = new byte[var3][];
         short[][] var17 = new short[var3][];

         for (int var18 = 0; var18 < var3; var18++) {
            var4[var18] = var2.readByte();
            var5[var18] = a(var2);
            var6[var18] = a(var2);
            var7[var18] = var2.readInt();
            var8[var18] = a(var2);
            if (var8[var18] == -1) {
               var8[var18] = 0;
            }

            var9[var18] = var2.readShort();
            var10[var18] = var2.readShort();
            var11[var18] = var2.readByte();
            var12[var18] = var2.readByte();
            var13[var18] = a(var2);
            byte var19;
            short[] var20 = new short[var19 = var2.readByte()];

            for (int var21 = 0; var21 < var19; var21++) {
               var20[var21] = var2.readShort();
            }

            byte var45;
            byte[] var22 = new byte[var45 = var2.readByte()];
            byte[] var23 = new byte[var45];

            for (int var24 = 0; var24 < var45; var24++) {
               var22[var24] = var2.readByte();
               var23[var24] = var2.readByte();
            }

            byte var50;
            short[] var25 = new short[var50 = var2.readByte()];

            for (int var26 = 0; var26 < var50; var26++) {
               var25[var26] = a(var2);
            }

            var14[var18] = var20;
            var15[var18] = var22;
            var16[var18] = var23;
            var17[var18] = var25;
         }

         int var43;
         short[] var44 = new short[var43 = var2.readShort() << 2];

         for (int var46 = 0; var46 < var43; var46++) {
            var44[var46] = a(var2);
         }

         byte var47;
         short[][] var48 = new short[var47 = var2.readByte()][];

         for (int var49 = 0; var49 < var47; var49++) {
            int var51;
            short[] var53 = new short[var51 = var2.readByte() << 1];

            for (int var27 = 0; var27 < var51; var27++) {
               var53[var27] = var2.readShort();
            }

            var48[var49] = var53;
         }

         var2.readByte();
         var2.readByte();
         byte var52;
         byte[][] var54 = new byte[var52 = var2.readByte()][];

         for (int var55 = 0; var55 < var52; var55++) {
            byte[] var29 = new byte[var2.readByte()];
            var2.readFully(var29);
            var54[var55] = var29;
         }

         byte var56;
         short[] var28 = new short[var56 = var2.readByte()];
         short[] var57 = new short[var56];
         int[] var30 = new int[var56];
         short[] var31 = new short[var56];
         short[] var32 = new short[var56];
         byte[] var33 = new byte[var56];

         for (int var34 = 0; var34 < var56; var34++) {
            var28[var34] = a(var2);
            var57[var34] = a(var2);
            var30[var34] = var2.readInt();
            var31[var34] = var2.readShort();
            var32[var34] = var2.readShort();
            var33[var34] = var2.readByte();
         }

         byte var58;
         byte[] var35 = new byte[var58 = var2.readByte()];
         byte[] var36 = new byte[var58];
         short[][] var37 = new short[var58][];

         for (int var38 = 0; var38 < var58; var38++) {
            var35[var38] = var2.readByte();
            var36[var38] = var2.readByte();
            byte var39;
            short[] var40 = new short[var39 = var2.readByte()];

            for (int var41 = 0; var41 < var39; var41++) {
               var40[var41] = var2.readByte();
            }

            var37[var38] = var40;
         }

         var2.close();
         this.a2 = var4;
         this.b = var5;
         this.c = var6;
         this.c2 = var7;
         this.d = var8;
         this.e = var9;
         this.f = var10;
         this.b2 = var11;
         this.c3 = var12;
         this.g = var13;
         this.a3 = var14;
         this.a4 = var15;
         this.b3 = var16;
         this.b4 = var17;
         this.h = var44;
         this.c4 = var48;
         this.c5 = var54;
         this.i = var28;
         this.j = var57;
         this.d2 = var30;
         this.k = var31;
         this.l = var32;
         this.d3 = var33;
         this.e2 = var35;
         this.f2 = var36;
         this.d4 = var37;
      } catch (IOException var42) {
      }
   }

   private static final short b(DataInputStream var0) throws IOException {
      return var0.readShort();
   }

   private void f2() {
      ResourceLoader var1 = this.a.a6;

      try {
         DataInputStream var2;
         byte var3;
         short[] var4 = new short[var3 = (var2 = new DataInputStream(var1.openStream(62))).readByte()];
         byte[][] var5 = new byte[var3][];
         byte[][] var6 = new byte[var3][];
         byte[][] var7 = new byte[var3][];
         byte[][] var8 = new byte[var3][];
         byte[][] var9 = new byte[var3][];
         byte[][] var10 = new byte[var3][];
         byte[][] var11 = new byte[var3][];
         byte[][] var12 = new byte[var3][];
         byte[][] var13 = new byte[var3][];
         byte[][] var14 = new byte[var3][];
         byte[][] var15 = new byte[var3][];
         short[][] var16 = new short[var3][];
         byte[][] var17 = new byte[var3][];

         for (int var18 = 0; var18 < var3; var18++) {
            var4[var18] = b(var2);
            byte var19;
            byte[] var20 = new byte[var19 = var2.readByte()];
            byte[] var21 = new byte[var19];
            byte[] var22 = new byte[var19];
            byte[] var23 = new byte[var19];
            byte[] var24 = new byte[var19];
            byte[] var25 = new byte[var19];

            for (int var26 = 0; var26 < var19; var26++) {
               var20[var26] = var2.readByte();
               var21[var26] = var2.readByte();
               var22[var26] = var2.readByte();
               var23[var26] = var2.readByte();
               var24[var26] = (byte)b(var2);
               var25[var26] = (byte)b(var2);
            }

            byte var37;
            byte[] var27 = new byte[var37 = var2.readByte()];
            byte[] var28 = new byte[var37];
            byte[] var29 = new byte[var37];

            for (int var30 = 0; var30 < var37; var30++) {
               var27[var30] = var2.readByte();
               var28[var30] = var2.readByte();
               var29[var30] = var2.readByte();
            }

            byte var38;
            byte[] var31 = new byte[var38 = var2.readByte()];
            byte[] var32 = new byte[var38];
            short[] var33 = new short[var38];
            byte[] var34 = new byte[var38];

            for (int var35 = 0; var35 < var38; var35++) {
               var31[var35] = var2.readByte();
               var32[var35] = var2.readByte();
               var33[var35] = b(var2);
               var34[var35] = (byte)b(var2);
            }

            var5[var18] = var20;
            var6[var18] = var21;
            var7[var18] = var22;
            var8[var18] = var23;
            var9[var18] = var24;
            var10[var18] = var25;
            var11[var18] = var27;
            var12[var18] = var28;
            var13[var18] = var29;
            var14[var18] = var31;
            var15[var18] = var32;
            var16[var18] = var33;
            var17[var18] = var34;
         }

         var2.close();
         this.m = var4;
         this.d5 = var5;
         this.e3 = var6;
         this.f3 = var7;
         this.g2 = var8;
         this.h2 = var9;
         this.i2 = var10;
         this.j2 = var11;
         this.k2 = var12;
         this.l2 = var13;
         this.m2 = var14;
         this.n = var15;
         this.e4 = var16;
         this.o = var17;
      } catch (IOException var36) {
      }
   }

   public final int a() {
      return com.ea.sims3.GameCanvas.a(this.g3, g4) != -1 ? 0 : this.g3;
   }

   public final int a3(int var1) {
      return this.m[var1];
   }

   public final int b2(int var1) {
      return com.ea.sims3.GameCanvas.a(var1, this.m);
   }

   private void g2() {
      this.a5 = 0;
      if (this.h3 == null) {
         this.h3 = new byte[150];
         this.i3 = new byte[150];
         this.o2 = new short[150];
         this.j3 = new byte[150];
      }

      com.ea.sims3.GameCanvas.a2(this.o2, -1);
      byte[] var1 = this.m2[0];
      byte[] var2 = this.n[0];
      short[] var3 = this.e4[0];
      byte[] var4 = this.o[0];
      System.arraycopy(var1, 0, this.h3, 0, var1.length);
      System.arraycopy(var2, 0, this.i3, 0, var2.length);
      System.arraycopy(var3, 0, this.o2, 0, var3.length);
      System.arraycopy(var4, 0, this.j3, 0, var4.length);
   }

   public final int b() {
      if (this.a5 == g4.length - 1) {
         return -1;
      } else if (this.a5 == 0) {
         return this.a.a23.b8(16) ? 1000 : 0;
      } else {
         return this.a.a23.b8(32) ? 2500 : 0;
      }
   }

   public final boolean a11() {
      return this.a5 == g4.length - 1;
   }

   public final int c() {
      return n2[this.a5];
   }

   public final boolean b5() {
      int var1 = 0;
      short[] var2 = this.o2;
      int[] var3 = this.c2;

      for (int var4 = var2.length - 1; var4 >= 0; var4--) {
         short var5;
         if ((var5 = var2[var4]) != -1 && (var3[var5] & 131072) != 0 && com.ea.sims3.GameCanvas.a(var5, var2, var4 + 1) == -1) {
            var1++;
         }
      }

      int var6 = com.ea.sims3.GameCanvas.b(131072, var3);
      return var1 == var6;
   }

   public final void a10() {
      this.a5++;
      byte var1 = g4[this.a5];
      byte[] var2 = this.m2[var1];
      byte[] var3 = this.n[var1];
      short[] var4 = this.e4[var1];
      byte[] var5 = this.o[var1];
      short[] var6 = this.o2;
      byte[] var7 = this.h3;
      byte[] var8 = this.i3;
      byte[] var9 = this.j3;

      for (int var10 = 0; var10 < var4.length; var10++) {
         short var11 = var4[var10];
         byte var12 = var2[var10];
         byte var13 = var3[var10];
         if (var11 != var6[var10]) {
            if (var6[var10] != -1) {
               int var14 = com.ea.sims3.GameCanvas.a(-1, var6);
               var6[var14] = var6[var10];
               var7[var14] = var7[var10];
               var8[var14] = var8[var10];
               var9[var14] = var9[var10];
               this.a.a23.a4(var10, var14);
            }
         } else if ((this.k(var11) & 4) != 0) {
            byte var15 = var7[var10];
            byte var16 = var8[var10];

            for (int var17 = 0; var17 < 150; var17++) {
               short var18;
               if (var10 != var17 && (var18 = var6[var17]) != -1) {
                  int var19 = this.k(var18);
                  byte var20 = var7[var17];
                  byte var21 = var8[var17];
                  if ((var19 & 524292) == 0 && var20 == var15 && var21 == var16) {
                     var7[var17] = var12;
                     var8[var17] = var13;
                     if ((var19 & 262144) != 0) {
                        int var22 = this.o(var18);
                        int var23 = this.p(var18);
                        byte var24;
                        if ((var24 = var9[var17]) == 1 || var24 == 3) {
                           int var25 = var22;
                           var22 = var23;
                           var23 = var25;
                        }

                        for (int var29 = 0; var29 < 150; var29++) {
                           short var26;
                           if ((var26 = var6[var29]) != -1 && (this.k(var26) & 524288) != 0) {
                              byte var27 = var7[var29];
                              byte var28 = var8[var29];
                              if (var27 <= var20 && var28 <= var21 && var27 > var20 - var22 && var28 > var21 - var23) {
                                 var7[var29] = (byte)(var7[var29] + (var12 - var20));
                                 var8[var29] = (byte)(var8[var29] + (var13 - var21));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var6[var10] = var11;
         var7[var10] = var12;
         var8[var10] = var13;
         var9[var10] = var5[var10];
      }

      this.a.a23.a10();
   }

   public final int a(int var1, int var2, int var3, int var4) {
      int var5 = com.ea.sims3.GameCanvas.a(-1, this.o2);
      this.o2[var5] = (short)var1;
      this.h3[var5] = (byte)f(var2);
      this.i3[var5] = (byte)g(var3);
      this.j3[var5] = (byte)var4;
      return var5;
   }

   public final void a12(int var1) {
      this.o2[var1] = -1;
      this.a.a23.j2(0, var1);
   }

   public final void a4(int var1, int var2) {
      this.j3[var1] = (byte)var2;
   }

   public final void b2(int var1, int var2) {
      this.o2[var1] = (short)var2;
   }

   public final void a2(int var1, int var2, int var3, int var4) {
      this.h3[var1] = (byte)f(var2);
      this.i3[var1] = (byte)g(var3);
      this.j3[var1] = (byte)var4;
   }

   public final boolean a13(int var1) {
      short[] var2 = this.o2;

      for (int var3 = 0; var3 < var2.length; var3++) {
         short var4;
         if ((var4 = var2[var3]) != -1 && this.h(var4) == var1) {
            return true;
         }
      }

      return false;
   }

   public final boolean b8(int var1) {
      return com.ea.sims3.GameCanvas.a(var1, this.o2) != -1;
   }

   public final void b4() {
      this.a5 = 0;
      this.g2();
   }

   public final void a2(DataInputStream var1) throws IOException {
      this.a5 = var1.readByte();

      for (int var2 = 0; var2 < this.o2.length; var2++) {
         this.o2[var2] = var1.readShort();
         this.h3[var2] = var1.readByte();
         this.i3[var2] = var1.readByte();
         this.j3[var2] = var1.readByte();
      }
   }

   public final void a(DataOutputStream var1) throws IOException {
      var1.writeByte(this.a5);

      for (int var2 = 0; var2 < this.o2.length; var2++) {
         var1.writeShort(this.o2[var2]);
         var1.writeByte(this.h3[var2]);
         var1.writeByte(this.i3[var2]);
         var1.writeByte(this.j3[var2]);
      }
   }

   public final void b2(int var1, int var2, int var3, int var4) {
      this.a6 = var1;
      this.b5 = var2;
      this.c6 = var3;
      this.d6 = var4;
   }

   private void h2() {
      this.l3 = 64;
      this.m3 = 64;
      this.n3 = this.l3 >> 1;
      this.o3 = this.m3 >> 1;
      this.h4 = 512;
      this.j4 = this.h4 << 16;
      this.i4 = 264;
      this.k3 = this.i4 << 16;
      if (this.k4 == null) {
         this.k4 = new byte[this.l3 * this.m3];
      }

      com.ea.sims3.GameCanvas.a3(this.k4, 0);
   }

   public final void b7(int var1) {
      if (var1 == 0) {
         var1 = g4[this.a5];
      }

      this.g3 = var1;
      this.h2();
      this.e3(var1);
      this.d3(var1);
   }

   private void d3(int var1) {
      GameScreen var2 = (GameScreen)this.a.a2;
      byte[] var3 = this.m2[var1];
      byte[] var4 = this.n[var1];
      short[] var5 = this.e4[var1];
      byte[] var6 = this.o[var1];
      if (this.a() == 0) {
         var3 = this.h3;
         var4 = this.i3;
         var5 = this.o2;
         var6 = this.j3;
      }

      boolean var7b = false;  // var7 is reused below for the spawn mode
      int var8 = 0;
      int var9 = 0;
      int var10 = var5.length;

      for (int var11 = 0; var11 < var10; var11++) {
         short var12;
         if ((var12 = var5[var11]) != -1) {
            byte var13 = var3[var11];
            byte var14 = var4[var11];
            byte var15 = var6[var11];
            var2.b(var12, var13, var14, var15, var11);
            if (!var7b && this.a2[var12] == 16) {
               var7b = true;
               var8 = var13;
               var9 = var14;
            }
         }
      }

      if (var7b) {
         int var27 = this.n3;
         var8 += var27;
         var9 += 0;
         this.d(var8, var9, 3, 3);
      }

      this.i2();
      int var7 = this.a() == 0 ? 2 : 3;
      var8 = com.ea.sims3.GameCanvas.a(var7, var5);
      int var23 = var3[var8];
      byte var25 = var4[var8];
      byte var29 = var6[var8];
      var2.b(0, var23, var25, var29, 0);
      var23 = var7 == 2 ? var8 : -1;
      Neighborhood var26 = var2.a17;
      int var28 = var2.a17.b6;

      for (int var30 = 1; var30 < var28; var30++) {
         int var31;
         if (var26.U(var30) == var1 && ((var31 = var26.o(var30)) & 8) != 0) {
            var23 = com.ea.sims3.GameCanvas.a(2, var5, var23 + 1);
            byte var32 = var3[var23];
            byte var33 = var4[var23];
            byte var17 = var6[var23];
            Sim var18 = (Sim)var2.b(1, var32, var33, var17, var30);
            if ((var31 & 16) != 0 && this.a.d() < 30) {
               var2.a(var18);
            }
         }
      }
   }

   public final boolean a5(int var1, int var2) {
      byte[] var3 = this.h3;
      byte[] var4 = this.i3;
      short[] var5 = this.o2;

      for (int var6 = 0; var6 < var5.length; var6++) {
         short var7;
         if ((var7 = var5[var6]) != -1 && (this.k(var7) & 524288) != 0 && var1 == var3[var6] && var2 == var4[var6]) {
            return true;
         }
      }

      return false;
   }

   private static int I(int var0) {
      for (int var1 = 0; var1 < 4; var1++) {
         if ((var0 & a7[var1]) != 0) {
            return var1;
         }
      }

      return -1;
   }

   public final boolean a3(int var1, int var2, int var3, int var4, int var5) {
      boolean var6 = true;
      if (var5 != 0 && var5 != 2) {
         if (var5 == 1 || var5 == 3) {
            byte var11 = 1;
            if (var5 == 3) {
               var11 = -1;
            }

            int var12 = var1;
            int var13 = var2 - var3 * var11;

            for (int var14 = 0; var14 < var4 && var6; var14++) {
               var6 = (this.g(var12 - var14 * var11, var13) & 64) != 0;
            }
         }
      } else {
         byte var7 = 1;
         if (var5 == 2) {
            var7 = -1;
         }

         int var8 = var1 - var3 * var7;
         int var9 = var2;

         for (int var10 = 0; var10 < var4 && var6; var10++) {
            var6 = (this.g(var8, var9 - var10 * var7) & 64) != 0;
         }
      }

      return var6;
   }

   public final int a(int var1, int var2, int var3) {
      int var4 = this.k(var1);
      int var5 = this.o(var1);
      int var6 = this.p(var1);
      int var7 = this.r(var1);
      int var8 = 0;

      for (int var9 = 0; var9 < a7.length; var9++) {
         int var10 = a7[var9];
         if ((var4 & var10) != 0) {
            int var11 = var5;
            int var12 = var6;
            if (var9 == 1 || var9 == 3) {
               var11 = var6;
               var12 = var5;
            }

            if ((var4 & 524288) != 0 || this.a3(var2, var3, var11, var12)) {
               if (var7 > 0) {
                  int var13 = 0;

                  for (int var14 = 0; var14 < var7; var14++) {
                     int var15 = this.b(var1, var14, var9);
                     int var16 = this.c(var1, var14, var9);
                     if (this.c3(var2 + var15, var3 + var16)) {
                        var13++;
                     }
                  }

                  if (var13 < 1) {
                     continue;
                  }
               }

               if (((var4 & 1048576) == 0 || this.a3(var2, var3, var5, var6, var9)) && ((var4 & 2097152) == 0 || !this.a3(var2, var3, var5, var6, var9))) {
                  var8 |= var10;
               }
            }
         }
      }

      return var8;
   }

   public final int c(int var1) {
      GameScreen var2 = (GameScreen)this.a.a2;
      byte[] var3 = this.h3;
      byte[] var4 = this.i3;
      short[] var5 = this.o2;
      byte[] var6 = this.j3;
      int var7 = this.k(var1);
      int var8 = this.o(var1);
      int var9 = this.p(var1);
      int var10 = 0;
      int var11 = var5.length;

      for (int var12 = 0; var12 < var11; var12++) {
         short var13;
         if ((var13 = var5[var12]) != -1) {
            byte var14 = var3[var12];
            byte var15 = var4[var12];
            byte var16 = var6[var12];
            int var17 = this.k(var13);
            int var18 = this.o(var13);
            int var19 = this.p(var13);
            if ((var7 & 524288) != 0) {
               if ((var17 & 262144) != 0) {
                  if (var16 == 1 || var16 == 3) {
                     int var20 = var18;
                     var18 = var19;
                     var19 = var20;
                  }

                  for (int var26 = 0; var26 < var18; var26++) {
                     for (int var27 = 0; var27 < var19; var27++) {
                        int var22 = var14 - var26;
                        int var23 = var15 - var27;
                        int var25;
                        if (!this.a5(var22, var23) && (var25 = I(this.a(var1, var22, var23))) != -1) {
                           var2.b(11, var22, var23, var25, -1);
                           var10++;
                        }
                     }
                  }
               }
            } else {
               int var21;
               if ((var17 & 4) != 0 && var18 == var8 && var19 == var9 && (var21 = I(this.a(var1, var14, var15))) != -1) {
                  var2.b(10, var14, var15, var21, -1);
                  var10++;
               }
            }
         }
      }

      return var10;
   }

   public final void c4(int var1) {
      this.h2();
      GameCanvas var2 = this.a;
      Image var4 = com.ea.sims3.ResourceLoader.loadImage(0);
      Tile var5;
      (var5 = new Tile(var1, var4)).a2(this.a6, this.b5, this.c6, this.d6);
      var5.setLooping(true);
      this.a8 = var5;
      this.f3(var1);
      this.b(true);
      GameScreen var6;
      Entity var7;
      int var8 = f((var7 = (var6 = this.a.a6()).a5(3)).c);
      int var9 = g(var7.d);
      var6.b(0, var8, var9, 3, 0);
      int var10 = var2.r;
      if (var2.r != -1) {
         Entity var11 = var6.b(1, var8, var9 - 1, 1, var10);
         var6.b(var11);
         var6.f(var11);
      }
   }

   public final void a(boolean var1) {
      this.h2();
      GameCanvas var2 = this.a;
      Image var4 = com.ea.sims3.ResourceLoader.loadImage(1);
      Tile var6;
      (var6 = new Tile(40, var4)).a2(this.a6, this.b5, this.c6, this.d6);
      var6.setLooping(true);
      this.a8 = var6;
      this.l2();
      this.b(false);
      GameScreen var7 = var2.a6();
      if (var1) {
         Neighborhood var11;
         int var13 = (var11 = var7.a17).s(var11.V(0));
         Entity var14 = var7.a5(var13);
         var7.a(0, var14, 0);
         var7.a(159, var14, 0, 0);
      } else {
         int var8 = var2.l2;
         if (var2.l2 != -1) {
            this.g3 = 13;
            int var12 = this.a3(var8);
            Entity var10 = var7.a5(var12);
            var7.a(0, var10, 0);
         } else {
            var7.a(0, var2.n2, var2.o2, 0, 0);
            int var9 = var2.r;
            if (var2.r != -1) {
               var7.a(1, var2.p2, var2.q, 0, var9);
            }

            var2.a((Sim) null);
         }
      }
   }

   private void b(boolean var1) {
      GameScreen var2 = this.a.a6();
      Tile var3 = this.a8;
      int var4 = this.a8.frameCount();
      short[] var5 = var3.a2;

      for (int var6 = 0; var6 < var4; var6++) {
         int var7 = var6 * 3;
         short var8 = var5[var7 + 0];
         int var9 = var5[var7 + 1] << 16;
         int var10 = var5[var7 + 2] << 16;
         int var11 = this.e(var9, var10);
         int var12 = this.f(var9, var10);
         if (var1) {
            var11 = d(f(var11));
            var12 = e(g(var12));
         }

         var2.a(var8, var11, var12, 0, -1);
      }

      int var16 = var3.rowCount();
      short[] var17 = var3.b;

      for (int var18 = 0; var18 < var16; var18++) {
         int var19 = var18 * 3;
         short var20 = var17[var19 + 0];
         int var21 = var17[var19 + 1] << 16;
         int var22 = var17[var19 + 2] << 16;
         int var13 = this.e(var21, var22);
         int var14 = this.f(var21, var22);
         if (var1) {
            var13 = d(f(var13));
            var14 = e(g(var14));
         }

         Entity var15;
         (var15 = var2.a(98, var13, var14, 0, -1)).o2(var20);
         if (var20 == 170 || var20 == 171 || var20 == 172 || var20 == 173 || var20 == 174) {
            var15.m2(-50);
         } else if (var20 == 151 || var20 == 152 || var20 == 153 || var20 == 154) {
            var15.m2(-18);
         }
      }
   }

   public final void c2() {
      this.a8 = null;
      this.b6 = null;
   }

   public final void a(Graphics var1) {
      this.b(var1);
   }

   public final int d() {
      return this.a6 + (this.c6 >> 1) - (this.e5 >> 16);
   }

   public final int e() {
      return this.b5 + (this.d6 >> 1) - (this.f4 >> 16);
   }

   public final int a2(int var1, int var2) {
      return (var1 - var2 << 1) + this.j4;
   }

   public final int b(int var1, int var2) {
      return var1 + var2 + this.k3;
   }

   public final int c(int var1, int var2) {
      return (var1 - var2 >> 15) + this.h4;
   }

   public final int d(int var1, int var2) {
      return (var1 + var2 >> 16) + this.i4;
   }

   public final int e(int var1, int var2) {
      return (var1 - this.j4 >> 1) + (var2 - this.k3) >> 1;
   }

   public final int f(int var1, int var2) {
      return (-(var1 - this.j4) >> 1) + (var2 - this.k3) >> 1;
   }

   private static int l(int var0, int var1) {
      return var0 - var1;
   }

   private static int m(int var0, int var1) {
      return var0 + var1;
   }

   public static int d(int var0) {
      return (var0 << 19) + 262144;
   }

   public static int e(int var0) {
      return (var0 << 19) + 262144;
   }

   public static int f(int var0) {
      return var0 >> 19;
   }

   public static int g(int var0) {
      return var0 >> 19;
   }

   public final boolean b3(int var1, int var2) {
      return this.c3(f(var1), g(var2));
   }

   private boolean c3(int var1, int var2) {
      return (this.g(var1, var2) & 128) == 0;
   }

   public final int g(int var1, int var2) {
      var1 += this.n3;
      var2 += this.o3;
      if (var1 >= 0 && var1 < this.l3 && var2 >= 0 && var2 < this.m3) {
         int var3 = var1 + var2 * this.l3;
         return this.k4[var3];
      } else {
         return 128;
      }
   }

   private void c(int var1, int var2, int var3, int var4) {
      var1 += this.n3;
      var2 += this.o3;
      if (var1 >= 0 && var1 < this.l3 && var2 >= 0 && var2 < this.m3) {
         byte[] var5 = this.k4;
         int var6 = var1 + var2 * this.l3;
         int var7 = var5[var6] & var3;
         var5[var6] = (byte)(var7 | var4);
      }
   }

   public final void a(int var1, int var2, int var3, int var4, int var5, int var6) {
      byte[] var7 = this.k4;
      int var8 = this.l3;
      int var9 = this.n3 + var1;
      int var10 = this.n3 + var1 + var3;
      int var11 = this.o3 + var2;
      int var12 = this.o3 + var2 + var4;

      for (int var13 = var9; var13 < var10; var13++) {
         for (int var14 = var11; var14 < var12; var14++) {
            int var15 = var13 + var14 * var8;
            int var16 = var7[var15] & var5;
            var7[var15] = (byte)(var16 | var6);
         }
      }
   }

   private boolean a3(int var1, int var2, int var3, int var4) {
      for (int var5 = 0; var5 < var3; var5++) {
         for (int var6 = 0; var6 < var4; var6++) {
            if (!this.c3(var1 - var5, var2 - var6)) {
               return false;
            }
         }
      }

      return true;
   }

   private void b(Graphics var1) {
      Tile var2 = this.a8;
      int var3 = var1.getClipX();
      int var4 = var1.getClipY();
      int var5 = var1.getClipWidth();
      int var6 = var1.getClipHeight();
      var1.setClip(this.a6, this.b5, this.c6, this.d6);
      var2.a3 = this.e5 - (this.c6 >> 1 << 16);
      var2.b2 = this.f4 - (this.d6 >> 1 << 16);
      var2.draw(var1);
      this.c(var1);
      var1.setClip(var3, var4, var5, var6);
   }

   private void e3(int var1) {
      if (this.a8 == null) {
         Image var3 = com.ea.sims3.ResourceLoader.loadImage(0);
         Tile var4;
         (var4 = new Tile(64, 64, var3)).a2(this.a6, this.b5, this.c6, this.d6);
         var4.setLooping(true);
         this.a8 = var4;
      }

      if (this.b6 == null) {
         Image var27 = com.ea.sims3.ResourceLoader.loadImage(2);
         Tile var29 = new Tile(64, 64, var27);
         this.b6 = var29;
         var29.a2(this.a6, this.b5, this.c6, this.d6);
         this.a8.a7 = var29;
      }

      byte[] var28 = this.d5[var1];
      byte[] var30 = this.e3[var1];
      byte[] var5 = this.f3[var1];
      byte[] var6 = this.g2[var1];
      byte[] var7 = this.h2[var1];
      byte[] var8 = this.i2[var1];
      byte[] var9 = this.j2[var1];
      byte[] var10 = this.k2[var1];
      byte[] var11 = this.l2[var1];
      int var12 = var28.length;
      int var13 = var9.length;

      for (int var14 = 0; var14 < var12; var14++) {
         byte var15 = var28[var14];
         byte var16 = var30[var14];
         byte var17 = var5[var14];
         byte var18 = var6[var14];
         this.a(var15, var16, var17, var18, 255, var14 + 1 | 16);
         this.a(var15 - 1, var16 - 1, var17 + 2, 1, 255, 128);
         this.a(var15 - 1, var16 + var18, var17 + 2, 1, 255, 128);
         this.a(var15 - 1, var16, 1, var18, 255, 128);
         this.a(var15 + var17, var16, 1, var18, 255, 128);
      }

      for (int var31 = 0; var31 < var13; var31++) {
         byte var33 = var9[var31];
         byte var34 = var10[var31];
         int var37 = Math.abs(var11[var31]);
         if ((this.g(var33 - 1, var34) & 128) != 0) {
            this.a(var33, var34, var37, 1, -129, 0);
         } else {
            this.a(var33, var34, 1, var37, -129, 0);
            var11[var31] = (byte)(-var37);
         }
      }

      com.ea.sims3.GameCanvas.a3(this.a8.a, 16);
      com.ea.sims3.GameCanvas.a3(this.b6.a, 60);
      int var32 = this.n3;

      for (int var35 = 0; var35 < var12; var35++) {
         int var38 = var32 + var28[var35];
         int var40 = 0 + var30[var35];
         byte var19 = var5[var35];
         byte var20 = var6[var35];
         byte var21 = var7[var35];
         byte var22 = var8[var35];
         this.b(var38, var40, var19, var20, var21, var22);
      }

      for (int var36 = 0; var36 < var13; var36++) {
         byte var39 = var9[var36];
         byte var41 = var10[var36];
         byte var42;
         int var43;
         int var44;
         if ((var42 = var11[var36]) > 0) {
            var43 = this.g(var39, var41 - 1);
            var44 = this.g(var39, var41 + 1);
         } else {
            var43 = this.g(var39 - 1, var41);
            var44 = this.g(var39 + 1, var41);
         }

         int var45 = (var43 & 15) - 1;
         int var23 = (var44 & 15) - 1;
         byte var24 = var45 == -1 ? 3 : var7[var45];
         byte var26 = var23 == -1 ? 0 : var8[var23];
         this.c(var32 + var39, 0 + var41, Math.abs((int)var42), var24, var26, var42 < 0 ? 2 : 1);
      }

      this.j2();
      this.k2();
      this.a8.reset();
   }

   private void b(int var1, int var2, int var3, int var4, int var5, int var6) {
      Tile var7 = this.a8;
      byte[] var8 = this.a8.a;
      byte[] var9 = this.b6.a;
      int var10 = var7.c;
      int var11 = l(var1, var2);
      int var12 = m(var1, var2);
      int var13 = var3 + var4;
      byte var14 = this.a(5, var5);
      byte var15 = this.a(12, var6);
      byte var16 = this.a(18, var6);
      byte var17 = this.a(8, var6);
      byte var18 = this.a(14, var6);
      int var19 = var11 - var4;
      int var20;
      int var21 = (var20 = var12 + var4) + 1;

      for (int var22 = 0; var22 < var13; var22++) {
         boolean var23 = var22 < var4;
         boolean var24 = var22 < var3;
         byte var25 = var23 ? var15 : var16;
         var9[var19 + (var20 - 0) * var10] = var25;
         byte var26 = var23 ? var17 : var18;
         var9[var19 + (var20 - 1) * var10] = var26;
         int var27 = var24 ? 56 : 48;
         var9[var19 + (var21 + 0) * var10] = (byte)var27;
         int var28 = var21 - var20 + 2;

         for (int var29 = 0; var29 < var28; var29++) {
            var8[var19 + (var20 + var29) * var10] = var14;
         }

         if (var22 == 0) {
            var9[var19 - 1 + (var20 + 0) * var10] = this.a(20, var6);
         } else if (var22 == var13 - 1) {
            var9[var19 + 1 + (var20 + 0) * var10] = this.a(21, var6);
         }

         var19++;
         if (var22 != var3 - 1) {
            var21 += var24 ? 1 : -1;
         }

         if (var22 != var4 - 1) {
            var20 += var23 ? -1 : 1;
         }
      }
   }

   private void c(int var1, int var2, int var3, int var4, int var5, int var6) {
      Tile var7 = this.a8;
      int var8 = this.a8.c;
      byte[] var9 = this.b6.a;
      int var10 = l(var1, var2);
      int var11 = m(var1, var2);
      boolean var12;
      if (var12 = (var6 & 1) != 0) {
         var10--;
         byte var25 = this.a(24, var5);
         byte var26 = this.a(25, var5);
         byte var27 = this.a(7, var4);
         int var36 = var10 + var11 * var8;
         var9[var36] = var25;
         var36 += var8;
         var9[var36] = var26;
         var36 += var8;
         var9[var36] = var27;

         for (int var45 = 0; var45 < var3; var45++) {
            var36 = ++var10 + ++var11 * var8;
            var9[var36] = 60;
            var36 += var8;
            var9[var36] = 60;
            var36 += var8;
            var9[var36] = var27;
         }

         var36 = ++var10 + ++var11 * var8;
         var9[var36] = 52;
         var36 += var8;
         if (var9[var36] == 60) {
            var9[var36] = this.a(14, var5);
            var36 += var8;
            var9[var36] = this.a(18, var5);
         }
      } else {
         var10 -= 2;
         var11 += 2;
         byte var13 = this.a(22, var5);
         byte var14 = this.a(23, var5);
         byte var15 = this.a(6, var4);
         int var16 = var10 + (var11 + 0) * var8;
         var9[var16] = 52;
         var16 += var8;
         if (var9[var16] == 60) {
            var9[var16] = this.a(8, var5);
            var16 += var8;
            var9[var16] = this.a(12, var5);
         }

         for (int var17 = 0; var17 < var3; var17++) {
            var16 = ++var10 + (--var11 + 0) * var8;
            var9[var16] = 60;
            var16 += var8;
            var9[var16] = 60;
            var16 += var8;
            var9[var16] = var15;
         }

         var16 = ++var10 + (--var11 + 0) * var8;
         var9[var16] = var13;
         var16 += var8;
         var9[var16] = var14;
         var16 += var8;
         var9[var16] = var15;
      }
   }

   private void d(int var1, int var2, int var3, int var4) {
      GameCanvas var5 = this.a;
      Tile var6 = this.a8;
      byte[] var7 = this.a8.a;
      int var8 = var6.c;
      int var9 = l(var1, var2);
      int var10 = m(var1, var2);
      int var11 = var3 + var4 + 2;
      int var12 = var9 - var4 - 1;
      int var13;
      int var14 = var13 = var10 + var4 + 1;
      var7[var12 - 1 + (var13 - 2) * var8] = 4;
      var7[var12 - 1 + (var13 - 1) * var8] = 12;
      var7[var12 - 1 + var13 * var8] = 13;
      var7[var12 - 1 + (var13 + 1) * var8] = 21;
      var7[var12 - 2 + (var13 - 1) * var8] = 4;
      var7[var12 - 2 + var13 * var8] = 21;

      for (int var15 = 0; var15 < var11; var15++) {
         boolean var16 = var15 < var4 + 1;
         boolean var17 = var15 < var3 + 1;
         int var18 = var14 - var13;

         for (int var19 = 0; var19 < var18; var19++) {
            int var20 = var5.d() < 50 ? 6 : 7;
            var7[var12 + (var13 + var19) * var8] = (byte)var20;
         }

         int var24 = var16 ? 22 : 23;
         var7[var12 + (var13 - 1) * var8] = (byte)var24;
         int var25 = var16 ? 4 : 5;
         var7[var12 + (var13 - 3) * var8] = (byte)var25;
         int var21 = var17 ? 15 : 14;
         var7[var12 + var14 * var8] = (byte)var21;
         int var22 = var17 ? 21 : 20;
         var7[var12 + (var14 + 2) * var8] = (byte)var22;
         int var23 = var5.d() < 50 ? 12 : 13;
         var7[var12 + (var13 - 2) * var8] = (byte)var23;
         var7[var12 + (var14 + 1) * var8] = (byte)var23;
         if (var15 != var3) {
            var14 += var17 ? 1 : -1;
         }

         if (var15 != var4) {
            var13 += var16 ? -1 : 1;
         }

         var12++;
      }

      var7[var12 + (var13 - 3) * var8] = 5;
      var7[var12 + (var13 - 2) * var8] = 12;
      var7[var12 + (var13 - 1) * var8] = 13;
      var7[var12 + var13 * var8] = 20;
      var7[var12 + 1 + (var13 - 2) * var8] = 5;
      var7[var12 + 1 + (var13 - 1) * var8] = 20;
   }

   private void i2() {
      GameScreen var1 = this.a.a6();
      boolean var2 = false;
      Entity var3;
      if ((var3 = var1.b4(10)).e == 1) {
         var2 = true;
         byte[] var4 = this.b9(1);
         this.a2(1, var4);
         byte[] var5 = new byte[]{16, 86, 94, 6, 6, 6, 6, 6};
         this.a2(0, var5);
      } else {
         byte[] var7 = this.b9(0);
         this.a2(0, var7);
         byte[] var10 = new byte[]{17, 87, 95, 7, 7, 7, 7, 7};
         this.a2(1, var10);
      }

      if (!var2) {
         boolean var9 = false;
         int var12 = g(var3.d);
         var1.b(57, 21, var12, 0, -1);

         for (int var14 = var12 + 6; var14 < 15; var14 += 4) {
            var1.b(54, 21, var14, 0, -1);
         }

         for (int var15 = var12 - 6; var15 > -14; var15 -= 4) {
            var1.b(54, 21, var15, 0, -1);
         }
      } else {
         int var8 = f(var3.c);
         boolean var11 = false;
         var1.b(57, var8, 21, 1, -1);

         for (int var6 = var8 + 6; var6 < 15; var6 += 4) {
            var1.b(54, var6, 21, 1, -1);
         }

         for (int var13 = var8 - 6; var13 > -14; var13 -= 4) {
            var1.b(54, var13, 21, 1, -1);
         }
      }
   }

   private void a2(int var1, byte[] var2) {
      Tile var3 = this.a8;
      byte[] var4 = this.a8.a;
      int var5 = var3.c;
      int var6 = var3.d;
      int var7 = var2.length - 1;
      int var8 = var5 - var7;
      int var9 = var6 - 1;
      byte var10 = 1;
      if (var1 == 1) {
         var8 = var7 - 1;
         var10 = -1;
      }

      byte[] var12 = this.b9(2);

      for (byte[] var13 = this.b9(3); var8 >= 0 && var8 < var5; var9 += -1) {
         for (int var14 = 0; var9 + var14 < var6 && var14 < var2.length; var14++) {
            byte var15 = var2[var14];
            int var16;
            if ((var8 & 1) == 0 && (var16 = com.ea.sims3.GameCanvas.a(var15, var12)) != -1) {
               var15 = var13[var16];
            }

            var16 = var9 + var14;
            var4[var8 + var16 * var5] = var15;
            int var17 = com.ea.sims3.Tile.toWorld16(var8);
            int var18 = com.ea.sims3.Tile.toWorld8(var16);
            int var19 = this.e(var17, var18);
            int var20 = this.f(var17, var18);
            int var21 = f(var19);
            int var22 = g(var20);
            this.c(var21, var22, 255, 128);
         }

         var8 += var10;
      }
   }

   private void j2() {
      Tile var1 = this.a8;
      byte[] var2 = this.b6.a;
      byte[] var3 = var1.a;
      int var4 = var1.c;
      int var5 = var1.d;
      byte[] var6 = this.b9(4);
      byte[] var7 = this.b9(5);
      byte[] var8 = this.b9(26);
      byte[] var9 = this.b9(8);
      byte[] var10 = this.b9(14);
      byte[] var11 = this.b9(20);
      byte[] var12 = this.b9(21);

      for (int var13 = 1; var13 < var4 - 1; var13++) {
         boolean var14 = false;

         for (int var15 = 0; var15 < var5 - 1; var15++) {
            int var16;
            int var17 = (var16 = var13 + (var15 + 0) * var4) + var4;
            byte var18 = var3[var16];
            byte var19 = var3[var17];
            byte var20 = var2[var16];
            byte var21 = var2[var17];
            if (var20 == 56 && var21 == 60) {
               var21 = var10[0];
            }

            if (var20 == 48 && var21 == 60) {
               var21 = var9[0];
            }

            if ((this.n(var20, 27) != -1 || com.ea.sims3.GameCanvas.a(var20, var11) != -1) && var21 == 60) {
               var21 = var10[0];
               int var22 = var16 + var4;
               if (var2[var22] == 60) {
                  var2[var22] = this.a(18, 0);
               }
            }

            if ((com.ea.sims3.GameCanvas.a(var20, var8) != -1 || com.ea.sims3.GameCanvas.a(var20, var12) != -1) && var21 == 60) {
               var21 = var9[0];
               int var34 = var16 + var4;
               if (var2[var34] == 60) {
                  var2[var34] = this.a(12, 0);
               }
            }

            int var35;
            if ((var35 = com.ea.sims3.GameCanvas.a(var20, var10)) != -1) {
               if (com.ea.sims3.GameCanvas.a(var21, var9) != -1 || com.ea.sims3.GameCanvas.a(var21, var11) != -1) {
                  var20 = var12[var35];
               } else if (com.ea.sims3.GameCanvas.a(var19, var7) != -1 && var21 == 60) {
                  var21 = this.a(18, var35);
               }
            }

            if ((var35 = com.ea.sims3.GameCanvas.a(var20, var9)) != -1) {
               if (com.ea.sims3.GameCanvas.a(var21, var10) != -1 || com.ea.sims3.GameCanvas.a(var21, var12) != -1) {
                  var20 = var11[var35];
               } else if (com.ea.sims3.GameCanvas.a(var19, var7) != -1 && var21 == 60) {
                  var21 = this.a(12, var35);
               }
            }

            int var23;
            if ((var23 = com.ea.sims3.GameCanvas.a(var18, var7)) != -1) {
               int var24;
               if ((var24 = com.ea.sims3.GameCanvas.a(var21, var9)) == -1 && (var24 = com.ea.sims3.GameCanvas.a(var21, var11)) == -1) {
                  if ((var24 = com.ea.sims3.GameCanvas.a(var21, var10)) == -1 && (var24 = com.ea.sims3.GameCanvas.a(var21, var12)) == -1) {
                     byte[] var43 = this.b9(24);
                     if (var23 == 3) {
                        int var45 = this.a.b(0, var6.length - 1);
                        var18 = var6[var45];
                     }

                     byte var51;
                     if (com.ea.sims3.GameCanvas.a(var51 = var2[var13 + 1 + var15 * var4], var11) != -1) {
                        if (var14) {
                           if (var20 == 60) {
                              var20 = this.a(22, 0);
                           }
                        } else {
                           byte var54 = this.a(30, 0);
                           byte var29 = this.a(29, 0);

                           for (int var30 = 0; var30 < 5; var30++) {
                              byte var31 = var30 == 4 ? var54 : var29;
                              var2[var13 + (var15 - var30 - 1) * var4] = var31;
                           }

                           var20 = this.a(28, 0);
                           int var57 = var13 + (var15 - 1 - 5) * var4;
                           var2[var57] = this.a(22, 0);
                           var14 = true;
                        }

                        if (var21 == 60) {
                           var21 = this.a(23, 0);
                        }
                     }

                     byte var56;
                     if (com.ea.sims3.GameCanvas.a(var56 = var2[var13 - 1 + var15 * var4], var12) != -1) {
                        if (var20 == 60) {
                           byte var59;
                           int var61 = com.ea.sims3.GameCanvas.a(var59 = var2[var13 - 1 + (var15 + 1) * var4], var9);
                           var20 = var43[var61];
                           var21 = this.a(25, var61);
                        }
                     } else {
                        int var55;
                        if ((var55 = com.ea.sims3.GameCanvas.a(var56, var8)) != -1) {
                           if (!var14) {
                              var55 = 0;
                              byte var58 = this.a(33, 0);
                              byte var60 = this.a(32, 0);

                              for (int var32 = 0; var32 < 5; var32++) {
                                 byte var33 = var32 == 4 ? var58 : var60;
                                 var2[var13 + (var15 - var32 - 1) * var4] = var33;
                              }

                              var20 = this.a(31, 0);
                              int var62 = var13 + (var15 - 1 - 5) * var4;
                              var2[var62] = var43[0];
                              var14 = true;
                              var62 -= var4 + 1;
                              if (var2[var62] == 52) {
                                 var2[var62] = 56;
                              }
                           } else if (var20 == 60) {
                              var20 = var43[var55];
                           }

                           if (var21 == 60) {
                              var21 = this.a(25, var55);
                           }
                        }
                     }
                  } else if (!var14) {
                     byte var42 = this.a(16, var24);
                     byte var44 = this.a(17, var24);

                     for (int var48 = 0; var48 < 5; var48++) {
                        byte var52 = var48 == 3 ? var42 : var44;
                        var2[var13 + (var15 - var48 - 1) * var4] = var52;
                     }

                     var20 = var44;
                     int var49 = var13 + (var15 - 5 - 1) * var4;
                     byte var53 = var2[var49 - 1];
                     if (this.n(var53, 15) == -1 && var53 != 52) {
                        var2[var49] = 52;
                     } else {
                        var2[var49] = 56;
                     }

                     var2[var13 + (var15 - 5 - 0) * var4] = this.a(15, var24);
                     if (var2[--var49] == 52) {
                        var2[var49] = 48;
                     }

                     if ((var24 = com.ea.sims3.GameCanvas.a(var21, var10)) != -1) {
                        var21 = this.a(19, var24);
                     } else if ((var24 = com.ea.sims3.GameCanvas.a(var21, var12)) != -1) {
                        var21 = var8[var24];
                     }

                     var14 = true;
                  } else if (var20 == 60) {
                     var20 = 56;
                  }
               } else if (var14) {
                  if (var20 == 60) {
                     var20 = 48;
                  }
               } else {
                  byte var25 = this.a(10, var24);
                  byte var26 = this.a(11, var24);

                  for (int var27 = 0; var27 < 5; var27++) {
                     byte var28 = var27 == 4 ? var25 : var26;
                     var2[var13 + (var15 - var27) * var4] = var28;
                  }

                  var20 = var26;
                  var2[var13 + (var15 - 5 - 1) * var4] = 52;
                  int var46 = var13 + (var15 - 5 - 0) * var4;
                  var2[var46] = this.a(9, var24);
                  if (var2[--var46] == 52) {
                     var2[var46] = 48;
                  }

                  if ((var24 = com.ea.sims3.GameCanvas.a(var21, var9)) != -1) {
                     var21 = this.a(13, var24);
                  } else if ((var24 = com.ea.sims3.GameCanvas.a(var21, var11)) != -1) {
                     var21 = this.a(27, var24);
                  }

                  var14 = true;
               }
            }

            var3[var16] = var18;
            var3[var17] = var19;
            var2[var16] = var20;
            var2[var17] = var21;
         }
      }
   }

   private void k2() {
      Tile var1 = this.b6;
      byte[] var2 = this.b6.a;
      int var3 = var1.c;
      int var4 = var1.d;
      byte[] var5 = this.k4;

      for (int var6 = 0; var6 < this.k4.length; var6++) {
         if ((var5[var6] & 128) != 0) {
            var5[var6] = (byte)(var5[var6] | 64);
         }
      }

      for (int var15 = 0; var15 < var3; var15++) {
         for (int var7 = 0; var7 < var4; var7++) {
            byte var8 = var2[var15 + var7 * var3];
            if (var15 == 0
               || var15 == var3 - 1
               || var7 == 0
               || var7 == var4 - 1
               || this.n(var8, 11) != -1
               || this.n(var8, 17) != -1
               || this.n(var8, 31) != -1
               || this.n(var8, 32) != -1
               || this.n(var8, 33) != -1
               || this.n(var8, 8) != -1
               || this.n(var8, 10) != -1
               || this.n(var8, 9) != -1
               || this.n(var8, 14) != -1
               || this.n(var8, 16) != -1
               || this.n(var8, 15) != -1) {
               int var9 = com.ea.sims3.Tile.toWorld16(var15);
               int var10 = com.ea.sims3.Tile.toWorld8(var7);
               int var11 = this.e(var9, var10);
               int var12 = this.f(var9, var10);
               int var13 = f(var11);
               int var14 = g(var12);
               this.c(var13, var14, 255, 128);
            }
         }
      }
   }

   private void l2() {
      Tile var1 = this.a8;
      byte[] var2 = this.a8.a;
      int var3 = var1.c;
      int var4 = var1.d;
      byte[] var5 = this.b9(34);
      com.ea.sims3.GameCanvas.a3(this.k4, 128);

      for (int var6 = 0; var6 < var3; var6++) {
         for (int var7 = 0; var7 < var4; var7++) {
            byte var8;
            if (com.ea.sims3.GameCanvas.a(var8 = var2[var6 + var7 * var3], var5) != -1) {
               int var9 = com.ea.sims3.Tile.toWorld16(var6);
               int var10 = com.ea.sims3.Tile.toWorld8(var7);
               int var11 = this.e(var9, var10);
               int var12 = this.f(var9, var10);
               int var13 = f(var11);
               int var14 = g(var12);
               this.c(var13, var14, -129, 32);
               this.c(var13 + 1, var14, 255, 16);
               this.c(var13 - 1, var14, 255, 16);
               this.c(var13, var14 + 1, 255, 16);
               this.c(var13, var14 - 1, 255, 16);
            }
         }
      }

      byte[] var15 = this.k4;

      for (int var16 = 0; var16 < var15.length; var16++) {
         byte var17 = var15[var16];
         short var18 = 255;
         if ((var17 & 32) != 0) {
            var18 = 239;
         }

         if ((var17 & 16) != 0) {
            var18 &= -129;
         }

         var15[var16] = (byte)(var17 & var18);
      }
   }

   private void f3(int var1) {
      byte[] var2 = this.b9(var1 == 39 ? 36 : 35);
      Tile var3 = this.a8;
      byte[] var4 = this.a8.a;
      int var5 = var3.c;
      int var6 = var3.d;

      for (int var7 = 0; var7 < var5; var7++) {
         for (int var8 = 0; var8 < var6; var8++) {
            byte var9;
            boolean var10 = com.ea.sims3.GameCanvas.a(var9 = var4[var7 + var8 * var5], var2) != -1;
            if (var7 == 0 || var7 == var5 - 1 || var8 == 0 || var8 == var6 - 1 || var10) {
               int var11 = var7;
               if (!var10 && var7 == var5 - 1) {
                  var11 = var7 + 1;
               }

               int var12 = com.ea.sims3.Tile.toWorld16(var11);
               int var13 = com.ea.sims3.Tile.toWorld8(var8);
               int var14 = this.e(var12, var13);
               int var15 = this.f(var12, var13);
               int var16 = f(var14);
               int var17 = g(var15);
               this.c(var16, var17, 255, 128);
            }
         }
      }
   }

   private void m2() {
      GameCanvas var1 = this.a;
      Entity[] var3 = this.a.a6().a12();
      boolean var4 = false;

      for (int var5 = 0; var5 < var3.length; var5++) {
         for (int var6 = 1; var6 < var3.length; var6++) {
            Entity var7 = var3[var6];
            Entity var8 = var3[var6 - 1];
            int var9 = this.c(var7.c, var7.d);
            int var10 = this.c(var8.c, var8.d);
            if (var9 < var10) {
               if (var7.f() < var8.e()) {
                  var3[var6 - 1] = var7;
                  var3[var6] = var8;
                  var4 = true;
               }

               if (var7.a4 == 98
                  && var8.a4 == 0
                  && var3.length < 25
                  && (var7.f() == 176 || var7.f() == 144 || var7.f() == 112 || var7.f() == 80 || var7.f() == 48)) {
                  var3[var6 - 1] = var7;
                  var3[var6] = var8;
                  var4 = true;
               }

               if (var7.a4 == 0
                  && var8.a4 == 98
                  && var3.length < 25
                  && (var8.f() == 400 || var8.f() == 368 || var8.f() == 336 || var8.f() == 304 || var8.f() == 272)) {
                  var3[var6 - 1] = var7;
                  var3[var6] = var8;
                  var4 = true;
               }
            } else {
               if (var7.e() < var8.f()) {
                  var3[var6 - 1] = var7;
                  var3[var6] = var8;
                  var4 = true;
               }

               if (var7.a4 == 98 && var8.a4 == 0 && var3.length < 25 && (var7.f() == 176 || var7.f() == 144 || var7.f() == 112 || var7.f() == 80)) {
                  var3[var6 - 1] = var7;
                  var3[var6] = var8;
                  var4 = true;
               }

               if (var7.a4 == 0
                  && var8.a4 == 98
                  && var3.length < 25
                  && (var8.f() == 400 || var8.f() == 368 || var8.f() == 336 || var8.f() == 304 || var8.f() == 272)) {
                  var3[var6 - 1] = var7;
                  var3[var6] = var8;
                  var4 = true;
               }
            }
         }

         if (!var4) {
            return;
         }

         var4 = false;
      }
   }

   private void n2() {
      GameCanvas var1 = this.a;
      Entity[] var3 = this.a.a6().a12();
      int var4 = this.a6 - 50;
      int var5 = this.b5 - 20;
      int var6 = this.a6 + this.c6 + 50;
      int var7 = this.b5 + this.d6 + 60;
      int var8 = this.d();
      int var9 = this.e();

      for (int var10 = 0; var10 < var3.length; var10++) {
         Entity var11 = var3[var10];
         int var12 = var8 + this.c(var11.c, var11.d);
         int var13 = var9 + this.d(var11.c, var11.d);
         var11.a7 = var12 >= var4 && var12 <= var6 && var13 >= var5 && var13 <= var7;
      }
   }

   private void c(Graphics var1) {
      this.m2();
      this.n2();
      GameCanvas var2 = this.a;
      Entity[] var4;
      int var5 = (var4 = this.a.a6().a12()).length;

      for (int var6 = 0; var6 < var5; var6++) {
         Entity var7;
         if ((var7 = var4[var6]).a7 && !var7.b8(8192)) {
            var7.a(var1);
         }
      }
   }

   public final void a(Graphics var1, Entity var2, Entity var3, int var4, int var5) {
      int var6 = this.e5;
      int var7 = this.f4;
      this.e5 = var4;
      this.f4 = var5;
      GameCanvas var8 = this.a;
      Entity[] var10;
      int var11 = (var10 = this.a.a6().a12()).length;

      for (int var12 = 0; var12 < var11; var12++) {
         Entity var13;
         if ((var13 = var10[var12]) == var2 || var13 == var3) {
            var13.a(var1);
         }
      }

      this.e5 = var6;
      this.f4 = var7;
   }

   public final int f() {
      return this.b.length;
   }

   public final int h(int var1) {
      return this.a2[var1];
   }

   public final int i(int var1) {
      return this.b[var1];
   }

   public final int j(int var1) {
      return this.c[var1];
   }

   public final int k(int var1) {
      return this.c2[var1];
   }

   public final int l(int var1) {
      return this.d[var1];
   }

   public final int m(int var1) {
      return this.e[var1];
   }

   public final int n(int var1) {
      return this.f[var1];
   }

   public final int o(int var1) {
      return this.b2[var1];
   }

   public final int p(int var1) {
      return this.c3[var1];
   }

   public final int q(int var1) {
      return this.g[var1];
   }

   public final int h(int var1, int var2) {
      return var2 < this.a3[var1].length ? this.a3[var1][var2] : 0;
   }

   public final int r(int var1) {
      return this.a4[var1].length;
   }

   public final int b(int var1, int var2, int var3) {
      int var4 = this.a4[var1][var2];
      byte var5 = this.b3[var1][var2];
      switch (var3) {
         case 0:
         default:
            break;
         case 1:
            var4 = var5;
            break;
         case 2:
            var4 = 1 - var4 - Math.max(this.c3[var1], 1);
            break;
         case 3:
            var4 = 1 - Math.max(this.b2[var1], 1) - var5;
      }

      return var4;
   }

   public final int c(int var1, int var2, int var3) {
      byte var4 = this.a4[var1][var2];
      int var5 = this.b3[var1][var2];
      switch (var3) {
         case 0:
         default:
            break;
         case 1:
            var5 = var4;
            break;
         case 2:
            var5 = 1 - var5 - Math.max(this.b2[var1], 1);
            break;
         case 3:
            var5 = 1 - Math.max(this.c3[var1], 1) - var4;
      }

      return var5;
   }

   public final short[] a16(int var1) {
      return this.b4[var1];
   }

   public final int s(int var1) {
      int[] var2 = this.c2;
      if ((this.c2[var1] & 32) == 0) {
         return -1;
      } else {
         int var3 = 0;

         for (int var4 = 0; var4 < var2.length; var4++) {
            if ((var2[var4] & 32) != 0) {
               if (var4 == var1) {
                  return var3;
               }

               var3++;
            }
         }

         return -1;
      }
   }

   public final int i(int var1, int var2) {
      int var3 = (var1 << 2) + var2;
      return this.h[var3];
   }

   public final int a(int var1, boolean var2) {
      if (var1 < 0) {
         int var3 = this.a.d();
         if (var1 == -2) {
            if (!var2) {
               var3 = var3 * 75 / 100;
            }

            if (var3 < 25) {
               var1 = 6;
            } else if (var3 < 50) {
               var1 = 7;
            } else if (var3 < 75) {
               var1 = 8;
            } else {
               var1 = 9;
            }
         } else if (var1 == -3) {
            var1 = var3 < 50 ? 10 : 11;
         } else if (var1 == -4) {
            var1 = var3 < 50 ? 13 : 14;
         }
      }

      return var1;
   }

   public final int a(int var1, int var2, boolean var3) {
      short[] var4 = this.c4[var1];
      var2 <<= 1;
      if (var3) {
         var2++;
      }

      if (var2 < var4.length) {
         return var4[var2];
      } else {
         return var3 ? 0 : var4[0];
      }
   }

   public final boolean c5(int var1) {
      int var2 = this.i();
      return (this.k(var1) & var2) == 0;
   }

   private int i() {
      switch (this.a.a23.d()) {
         case 0:
         default:
            return 805306368;
         case 1:
            return 536870912;
         case 2:
            return 0;
      }
   }

   public final int g() {
      return this.e.length - com.ea.sims3.GameCanvas.c(-1, this.e);
   }

   public final int t(int var1) {
      return com.ea.sims3.GameCanvas.b(var1, this.e, -1);
   }

   private final byte[] b9(int var1) {
      return this.c5[var1];
   }

   private final byte a(int var1, int var2) {
      return this.c5[var1][var2];
   }

   private final int n(int var1, int var2) {
      return com.ea.sims3.GameCanvas.a(var1, this.c5[var2]);
   }

   public final int h() {
      return this.i.length;
   }

   public final int u(int var1) {
      return this.i[var1];
   }

   public final int v(int var1) {
      return this.j[var1];
   }

   public final int w(int var1) {
      return this.d2[var1];
   }

   public final int x(int var1) {
      return this.k[var1];
   }

   public final int y(int var1) {
      return this.l[var1];
   }

   public final int z(int var1) {
      return this.d3[var1];
   }

   public final int A(int var1) {
      return com.ea.sims3.GameCanvas.a(var1, this.i);
   }

   private static int J(int var0) {
      char var1 = '\u0000';
      switch (var0) {
         case 111:
            var1 = '\u8000';
            break;
         case 112:
         case 113:
         default:
            var1 = '\u1000';
            break;
         case 114:
            var1 = '\u2000';
            break;
         case 115:
            var1 = '\u4000';
      }

      return var1;
   }

   public final int B(int var1) {
      return com.ea.sims3.GameCanvas.b(J(var1), this.d2);
   }

   public final int j(int var1, int var2) {
      int var3 = J(var1);
      return com.ea.sims3.GameCanvas.a(var2, this.d2, var3);
   }

   public final int C(int var1) {
      return com.ea.sims3.FixedMath.mul(this.f2[var1] << 16, 22937);
   }

   public final int D(int var1) {
      return this.e2[var1];
   }

   public final int E(int var1) {
      return this.d4[var1].length;
   }

   public final int k(int var1, int var2) {
      return this.d4[var1][var2];
   }

   public final int F(int var1) {
      return com.ea.sims3.GameCanvas.a(var1, this.e2);
   }

   public final int G(int var1) {
      return this.i[this.e2[var1]];
   }

   public final int H(int var1) {
      for (int var2 = 0; var2 < this.e2.length; var2++) {
         if (this.G(var2) == var1) {
            return var2;
         }
      }

      return -1;
   }

   private void o2() {
      ResourceLoader var1 = this.a.a6;

      try {
         DataInputStream var2;
         byte var3;
         int[][] var4 = new int[var3 = (var2 = new DataInputStream(var1.openStream(60))).readByte()][];

         for (int var5 = 0; var5 < var3; var5++) {
            byte var6;
            int[] var7 = new int[var6 = var2.readByte()];

            for (int var8 = 0; var8 < var6; var8++) {
               var7[var8] = var2.readInt();
            }

            var4[var5] = var7;
         }

         byte var11;
         byte[][] var12 = new byte[var11 = var2.readByte()][];

         for (int var13 = 0; var13 < var11; var13++) {
            byte[] var9 = new byte[var2.readByte()];
            var2.readFully(var9);
            var12[var13] = var9;
         }

         var2.close();
         this.a9 = var4;
         this.p = var12;
      } catch (IOException var10) {
      }
   }

   private static void a(int[] var0, int[] var1) {
      for (int var2 = 0; var2 < var0.length; var2++) {
         var0[var2] = 16711935;
         var1[var2] = 16711935;
      }
   }

   private void a(int[] var1, int[] var2, int var3, int var4) {
      if (var3 >= 0 && var4 >= 0) {
         int[] var5 = this.a9[var3];
         int[] var6 = this.a9[var4];
         int var7 = -1;

         for (int var8 = 0; var8 < var1.length; var8++) {
            if (var1[var8] == 16711935) {
               var7 = var8;
               break;
            }
         }

         System.arraycopy(var5, 0, var1, var7, var5.length);
         System.arraycopy(var6, 0, var2, var7, var6.length);
      }
   }

   public final int[] a15(int var1) {
      return this.a9[var1];
   }

   public final byte[] a14(int var1) {
      return this.p[var1];
   }

   public final void a2(int var1, int[] var2, int[] var3) {
      Neighborhood var4 = this.a.a23;
      byte var5 = this.p[1][var4.i(var1, 3)];
      byte var6 = this.p[2][var4.i(var1, 4)];
      byte var7 = this.p[3][var4.i(var1, 5)];
      byte var8 = this.p[4][var4.i(var1, 6)];
      byte var9 = this.p[5][var4.i(var1, 7)];
      byte var10 = this.p[6][var4.i(var1, 8)];
      byte var11 = this.p[7][var4.i(var1, 9)];
      byte var12 = this.p[0][var4.i(var1, 1)];
      a(var2, var3);
      this.a(var2, var3, 11, var5);
      this.a(var2, var3, 34, var6);
      this.a(var2, var3, 20, var7);
      this.a(var2, var3, 30, var8);
      this.a(var2, var3, 31, var9);
      this.a(var2, var3, 32, var10);
      this.a(var2, var3, 33, var11);
      this.a(var2, var3, 5, var12);
   }

   public final void d2() {
      this.t = 0;
   }

   public final void c2(int var1, int var2) {
      int var3 = this.t << 1;
      this.e6[var3] = var1;
      this.e6[var3 + 1] = var2;
      this.t++;
   }

   public final int[] a4(int var1, int var2, int var3, int var4, int var5) {
      this.q2();
      this.y = var5;
      this.p2 = f(var1);
      this.q = g(var2);
      this.r = f(var3);
      this.s = g(var4);
      if (this.c3(this.p2, this.q) && this.c3(this.r, this.s)) {
         this.u = this.p2 + this.r - 32 >> 1;
         this.v = this.q + this.s - 32 >> 1;
         int var6 = this.p2 - this.u;
         int var7 = this.q - this.v;
         if (var6 >= 0 && var7 >= 0 && var6 < 32 && var7 < 32) {
            var6 = this.r - this.u;
            var7 = this.s - this.v;
            if (var6 >= 0 && var7 >= 0 && var6 < 32 && var7 < 32) {
               this.d3(this.p2 - this.u, this.q - this.v);
               boolean var20 = false;

               while (!var20) {
                  if ((var7 = this.j()) == -1) {
                     return new int[0];
                  }

                  int var8 = this.p3[var7] & 255;
                  int var9 = (this.p3[var7] & '\uff00') >> 8;

                  for (int var10 = 0; var10 < 4; var10++) {
                     int var11 = var8 + (var10 == 0 ? -1 : (var10 == 1 ? 1 : 0));
                     int var12 = var9 + (var10 == 2 ? -1 : (var10 == 3 ? 1 : 0));
                     if (!this.f3(var11, var12)) {
                        if (this.d3(var11, var12)) {
                           this.e(var11, var12, var8, var9);
                           this.d2(var11, var12);
                           this.e2(var11, var12);
                           this.f2(var11, var12);
                        } else if (this.b3(var11, var12, var8, var9)) {
                           this.e(var11, var12, var8, var9);
                           this.d2(var11, var12);
                           this.f2(var11, var12);
                        }
                     }
                  }

                  this.g2(var7);
                  if (var8 == this.r - this.u && var9 == this.s - this.v) {
                     var20 = true;
                  }
               }

               var7 = Integer.MAX_VALUE;
               int var24 = Integer.MAX_VALUE;
               int var25 = this.r - this.u;
               int var26 = this.s - this.v;
               int var27 = this.p2 - this.u;
               int var28 = this.q - this.v;
               int var13 = 0;

               short var14;
               while (
                  var25 >= 0
                     && var26 >= 0
                     && var25 < 32
                     && var26 < 32
                     && (var25 != var27 || var26 != var28)
                     && (var7 != var25 || var24 != var26)
                     && (var14 = this.f5[var25][var26]) != -1
               ) {
                  this.r2[var13] = (short)(var25 | var26 << 8);
                  var13++;
                  var7 = var25;
                  var24 = var26;
                  var25 = var14 & 255;
                  var26 = (var14 & '\uff00') >> 8;
               }

               int[] var29 = new int[var13 << 1];

               for (int var15 = 0; var15 < var13; var15++) {
                  int var16 = var13 - var15 - 1;
                  int var17 = (this.r2[var16] & 255) + this.u;
                  int var18 = ((this.r2[var16] & '\uff00') >> 8) + this.v;
                  var29[(var15 << 1) + 0] = d(var17);
                  var29[(var15 << 1) + 1] = e(var18);
               }

               return var29;
            } else {
               return new int[0];
            }
         } else {
            return new int[0];
         }
      } else {
         return new int[0];
      }
   }

   private void p2() {
      if (this.p3 == null) {
         this.p3 = new short[1024];
         this.w = 0;
         this.q2 = new short[1024];
         this.x = 0;
         this.f5 = new short[32][];

         for (int var1 = 0; var1 < this.f5.length; var1++) {
            this.f5[var1] = new short[32];
         }

         this.c7 = new int[32][];

         for (int var2 = 0; var2 < this.c7.length; var2++) {
            this.c7[var2] = new int[32];
         }

         this.b7 = new int[32][];

         for (int var3 = 0; var3 < this.b7.length; var3++) {
            this.b7[var3] = new int[32];
         }

         this.d7 = new int[32][];

         for (int var4 = 0; var4 < this.d7.length; var4++) {
            this.d7[var4] = new int[32];
         }

         this.r2 = new short[256];
         this.t = 0;
         this.e6 = new int[20];
      }
   }

   private boolean d3(int var1, int var2) {
      short var3 = (short)(var1 | var2 << 8);

      for (int var4 = 0; var4 < this.w; var4++) {
         if (this.p3[var4] == var3) {
            return false;
         }
      }

      this.p3[this.w] = var3;
      this.w++;
      return true;
   }

   private void g2(int var1) {
      this.q2[this.x] = this.p3[var1];
      this.x++;

      for (int var2 = var1; var2 < this.w - 1; var2++) {
         this.p3[var2] = this.p3[var2 + 1];
      }

      this.w--;
   }

   private void e(int var1, int var2, int var3, int var4) {
      this.f5[var1][var2] = (short)(var3 | var4 << 8);
   }

   private int o(int var1, int var2) {
      if ((this.y & 6) != 0) {
         int var3 = var1 + this.u;
         int var4 = var2 + this.v;
         int var5;
         if (((var5 = this.g(var3, var4)) & 32) != 0) {
            return (this.y & 2) != 0 ? 5 : 50;
         } else {
            return (var5 & 16) != 0 ? 10 : 15;
         }
      } else {
         return 15;
      }
   }

   private void d2(int var1, int var2) {
      int var3 = this.f5[var1][var2] & 255;
      int var4 = (this.f5[var1][var2] & '\uff00') >> 8;
      int var5 = this.c7[var3][var4];
      byte var6 = 0;
      if ((this.y & 8) != 0) {
         int var7 = this.f5[var3][var4] & 255;
         int var8 = (this.f5[var3][var4] & '\uff00') >> 8;
         if ((var7 != var3 || var3 != var1) && (var8 != var4 || var4 != var2)) {
            var6 = 20;
         }
      }

      this.c7[var1][var2] = var5 + this.o(var1, var2) + var6;
   }

   private boolean b3(int var1, int var2, int var3, int var4) {
      int var5 = this.c7[var1][var2];
      return this.c7[var3][var4] + this.o(var1, var2) < var5;
   }

   private void e2(int var1, int var2) {
      int var3 = this.r - this.u - var1;
      int var4 = this.s - this.v - var2;
      if (var3 < 0) {
         var3 = -var3;
      }

      if (var4 < 0) {
         var4 = -var4;
      }

      short var5 = 0;
      if ((this.y & 1) != 0) {
         for (int var6 = 0; var6 < this.t; var6++) {
            int var7 = var6 << 1;
            if (var1 + this.u == this.e6[var7] && var2 + this.v == this.e6[var7 + 1]) {
               var5 = 250;
               break;
            }
         }
      }

      this.d7[var1][var2] = var3 * 10 + var4 * 10 + var5;
   }

   private void f2(int var1, int var2) {
      this.b7[var1][var2] = this.d7[var1][var2] + this.c7[var1][var2];
   }

   private int j() {
      int var1 = -1;
      int var2 = Integer.MAX_VALUE;

      for (int var3 = 0; var3 < this.w; var3++) {
         int var4 = this.p3[var3] & 255;
         int var5 = (this.p3[var3] & '\uff00') >> 8;
         if (var2 > this.b7[var4][var5]) {
            var2 = this.b7[var4][var5];
            var1 = var3;
         }
      }

      return var1;
   }

   private boolean e3(int var1, int var2) {
      int var3 = var1 + this.u;
      int var4 = var2 + this.v;
      return this.c3(var3, var4);
   }

   private boolean f3(int var1, int var2) {
      if (var1 >= 0 && var2 >= 0 && var1 < 32 && var2 < 32) {
         if (!this.e3(var1, var2)) {
            return true;
         } else {
            int var3 = var1 | var2 << 8;

            for (int var4 = 0; var4 < this.x; var4++) {
               if (var3 == this.q2[var4]) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }

   private void q2() {
      this.x = 0;
      this.w = 0;
      com.ea.sims3.GameCanvas.a(this.f5, 0);
      com.ea.sims3.GameCanvas.a(this.c7, 0);
      com.ea.sims3.GameCanvas.a(this.b7, 0);
      com.ea.sims3.GameCanvas.a(this.d7, 0);
   }
}
