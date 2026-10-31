package com.ea.sims3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Neighborhood implements SpriteIndex, SoundIndex, Resources {
   private GameCanvas a;
   private short[] b;
   private int[][] a2;
   private short[][] a3;
   private short[][] b2;
   private short[] c;
   private short[] d;
   private short[] e;
   private byte[] a4;
   private short[] f;
   private short[] g;
   private short[] h;
   private short[][] c2;
   private short[][] d2;
   private short[][] e2;
   private short[][] f2;
   private short[][] g2;
   private byte[][][] a5;
   private short[] i;
   private short[] j;
   private short[] k;
   private short[] l;
   private short[] m;
   private short[] n;
   public int a6;
   private short[] o;
   private int[] f3;
   private int[] g3;
   private short[] p;
   private int[] h2;
   private short[] q;
   private short[] r;
   private byte[] b3;
   private int j2;
   private short[] s;
   private int[] i2;
   private byte[] c3;
   private byte[] d3;
   private byte[] e3;
   private byte[][] a7;
   private int[][] b4;
   private byte[][] b5;
   private byte[][] c4;
   private byte[][] d4;
   private int[][] c5;
   private short[] t;
   private short[] u;
   private int k2;
   private short[] v;
   private byte[] f4;
   private short[] w;
   private short[] x;
   private byte[][] e4;
   private byte[][] f5;
   private short[][] h3;
   public int b6;
   private short[] y;
   private byte[] g4;
   private byte[][] g5;
   private short[] z;
   private byte[] h4;
   private byte[] i3;
   private byte[] j3;
   private byte[] k3;
   private byte[][] h5;
   private byte[][] i4;
   public int c6;
   private byte[][] j4;
   private short[] A;
   private int l2;
   private short[] B;
   private short[] C;
   private int[] j5;
   private short[] D;
   private byte[][] k4;
   private byte[][] l3;
   private int[][] d5;
   private short[][] i5;
   private byte[][] m2;
   private byte[][] n2;
   private byte[][] o2;
   private byte[][] p2;
   private short[][] j6;
   private byte[][] q2;
   private short[] E;
   private byte[] l4;
   private byte[] m3;
   private byte[] n3;
   private short[] F;
   private byte[] o3;
   private short[] G;
   private byte[] p3;
   private short[] H;
   private byte[] q3;
   private short[] I;
   private byte[] r2;
   private short[] J;
   private short[] K;
   private short[] L;
   private short[] M;
   private short[] N;
   private short[][] k5;
   public static final int[] a8 = new int[]{4, 8, 16, 32, 64, 128, 256};
   public static final int[] c7 = new int[]{653, 654, 655, 656, 657, 658, 659};
   private int m4;
   public int d6;
   public int e5;
   public int f6;
   public boolean a9;
   private byte c8;
   private short a10;
   private byte[] s2;
   public int g6;
   private int[] k6;
   public static final int[] d7 = new int[]{1, 2};
   public static final int[] e6 = new int[]{665, 666};
   private byte d8;
   private short b7;
   private short c9;
   private byte[] t2;
   private byte e7;
   private int n4;
   public byte a11;
   public byte b8;
   private short d9;
   private int o4;
   private byte[] u2;
   private int p4;
   private int q4;
   private int[] l5;
   private short e8;
   private short f7;
   private int[] m5;
   private int r3;
   private int s3;
   private int[] n5;
   private int[] o5;
   private int[] p5;
   private int[] q5;
   private short[] O;
   private int[] r4;
   private static final short[] P = new short[]{387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398};
   private int[] s4;
   private int t3;
   public int h6;
   private byte[] v2;
   private short[] Q;
   private int[][] e9;
   private int[][] f8;
   private short[][] l6;
   private byte[][] r5;
   private int u3;
   public int i6;
   private int v3;
   private static final short[] R = new short[]{572, 571, 570, 196, 573, 574, 575};
   private static final short[] S = new short[]{
      1,
      5,
      2,
      165,
      3,
      9,
      7,
      289,
      8,
      261,
      9,
      321,
      14,
      257,
      11,
      1,
      12,
      289,
      15,
      257,
      16,
      261,
      17,
      257,
      18,
      257,
      19,
      1,
      20,
      513,
      21,
      257,
      22,
      257,
      24,
      257,
      25,
      305,
      26,
      1073,
      27,
      257,
      196,
      2
   };
   private byte[] w2;
   private int[] t4;
   private int w3;
   private short[] T;
   private int[] u4;
   private int[] v4;

   public Neighborhood(GameCanvas var1) {
      this.a = var1;
      this.i2();
      this.h2();
   }

   public final void a4(int var1, int var2) {
      int var3;
      int var4;
      if ((var4 = com.ea.sims3.GameCanvas.a(var3 = 0 | var1, this.T)) != -1) {
         int var5 = this.T[var4] & '\uff00';
         this.T[var4] = (short)(var5 | var2);
      }
   }

   private static final short a(DataInputStream var0) throws IOException {
      return var0.readShort();
   }

   private void i2() {
      ResourceLoader var1 = this.a.a6;

      try {
         DataInputStream var2;
         byte var3;
         short[] var4 = new short[var3 = (var2 = new DataInputStream(var1.openStream(61))).readByte()];
         int[][] var5 = new int[var3][];
         short[][] var6 = new short[var3][];
         short[][] var7 = new short[var3][];

         for (int var8 = 0; var8 < var3; var8++) {
            var4[var8] = a(var2);
            short[] var9 = new short[3];

            for (int var10 = 0; var10 < var9.length; var10++) {
               var9[var10] = a(var2);
            }

            var6[var8] = var9;
            int[] var121 = new int[5];

            for (int var11 = 0; var11 < 5; var11++) {
               var121[var11] = var2.readInt();
            }

            var5[var8] = var121;
            short[] var123 = new short[6];

            for (int var12 = 0; var12 < 6; var12++) {
               var123[var12] = a(var2);
            }

            var7[var8] = var123;
         }

         byte var119;
         short[] var120 = new short[var119 = var2.readByte()];
         short[] var122 = new short[var119];
         short[] var124 = new short[var119];
         byte[] var125 = new byte[var119];
         short[] var13 = new short[var119];
         short[] var14 = new short[var119];
         short[] var15 = new short[var119];
         short[][] var16 = new short[var119][];
         short[][] var17 = new short[var119][];
         short[][] var18 = new short[var119][];
         short[][] var19 = new short[var119][];
         short[][] var20 = new short[var119][];
         byte[][][] var21 = new byte[var119][][];

         for (int var22 = 0; var22 < var119; var22++) {
            var120[var22] = a(var2);
            var122[var22] = a(var2);
            var124[var22] = a(var2);
            var125[var22] = var2.readByte();
            var13[var22] = a(var2);
            var14[var22] = a(var2);
            var15[var22] = a(var2);
            byte var23;
            short[] var24 = new short[var23 = var2.readByte()];
            short[] var25 = new short[var23];
            short[] var26 = new short[var23];
            short[] var27 = new short[var23];
            short[] var28 = new short[var23];
            byte[][] var29 = new byte[var23][];

            for (int var30 = 0; var30 < var23; var30++) {
               var24[var30] = a(var2);
               var25[var30] = var2.readShort();
               var26[var30] = var2.readShort();
               int var31 = var2.readShort();
               int var32 = var2.readShort();
               var31 = var31 % 100 + var31 / 100 * 60;
               var32 = var32 % 100 + var32 / 100 * 60;
               var27[var30] = (short)var31;
               var28[var30] = (short)var32;
               byte[] var33;
               (var33 = new byte[4])[0] = (byte)a(var2);
               var33[1] = var2.readByte();
               var33[2] = (byte)a(var2);
               var33[3] = var2.readByte();
               var29[var30] = var33;
            }

            var16[var22] = var24;
            var17[var22] = var25;
            var18[var22] = var26;
            var19[var22] = var27;
            var20[var22] = var28;
            var21[var22] = var29;
         }

         byte var126;
         short[] var127 = new short[var126 = var2.readByte()];
         short[] var128 = new short[var126];
         short[] var129 = new short[var126];
         short[] var130 = new short[var126];

         for (int var131 = 0; var131 < var126; var131++) {
            var127[var131] = a(var2);
            var128[var131] = a(var2);
            var129[var131] = a(var2);
            var130[var131] = var2.readShort();
         }

         byte var132;
         short[] var133 = new short[var132 = var2.readByte()];
         short[] var134 = new short[var132];

         for (int var135 = 0; var135 < var132; var135++) {
            var133[var135] = a(var2);
            var134[var135] = var2.readShort();
         }

         byte var136;
         short[] var138 = new short[var136 = var2.readByte()];
         int[] var140 = new int[var136];
         int[] var141 = new int[var136];
         short[] var34 = new short[var136];
         int[] var35 = new int[var136];
         short[] var36 = new short[var136];
         short[] var37 = new short[var136];

         for (int var38 = 0; var38 < var136; var38++) {
            var138[var38] = a(var2);
            var140[var38] = var2.readInt();
            var141[var38] = var2.readInt();
            var34[var38] = var2.readShort();
            var35[var38] = var2.readInt();
            var36[var38] = a(var2);
            var37[var38] = a(var2);
         }

         byte[] var39 = new byte[var2.readByte()];
         var2.readFully(var39);
         byte var40;
         short[] var41 = new short[var40 = var2.readByte()];
         int[] var42 = new int[var40];
         byte[] var43 = new byte[var40];
         byte[] var44 = new byte[var40];
         byte[] var45 = new byte[var40];
         byte[][] var46 = new byte[var40][];
         int[][] var47 = new int[var40][];
         byte[][] var48 = new byte[var40][];
         byte[][] var49 = new byte[var40][];
         byte[][] var50 = new byte[var40][];
         int[][] var51 = new int[var40][];

         for (int var52 = 0; var52 < var40; var52++) {
            var41[var52] = a(var2);
            var42[var52] = var2.readInt();
            var43[var52] = var2.readByte();
            var44[var52] = var2.readByte();
            var45[var52] = var2.readByte();
            byte var53;
            byte[] var54 = new byte[var53 = var2.readByte()];
            int[] var55 = new int[var53];

            for (int var56 = 0; var56 < var53; var56++) {
               var54[var56] = var2.readByte();
               var55[var56] = var2.readInt();
            }

            var46[var52] = var54;
            var47[var52] = var55;
            var54 = new byte[var53 = var2.readByte()];
            byte[] var149 = new byte[var53];

            for (int var153 = 0; var153 < var53; var153++) {
               var54[var153] = (byte)a(var2);
               var149[var153] = var2.readByte();
            }

            var48[var52] = var54;
            var49[var52] = var149;
            var54 = new byte[var53 = var2.readByte()];
            var55 = new int[var53];

            for (int var154 = 0; var154 < var53; var154++) {
               var54[var154] = var2.readByte();
               var55[var154] = var2.readInt();
            }

            var50[var52] = var54;
            var51[var52] = var55;
         }

         byte var142;
         short[] var145 = new short[var142 = var2.readByte()];
         short[] var148 = new short[var142];

         for (int var151 = 0; var151 < var142; var151++) {
            var145[var151] = a(var2);
            var148[var151] = var2.readByte();
         }

         byte var152 = var2.readByte();
         byte var155;
         short[] var57 = new short[var155 = var2.readByte()];
         byte[] var58 = new byte[var155];
         short[] var59 = new short[var155];
         short[] var60 = new short[var155];
         byte[][] var61 = new byte[var155][];
         byte[][] var62 = new byte[var155][];
         short[][] var63 = new short[var155][];

         for (int var64 = 0; var64 < var155; var64++) {
            var57[var64] = a(var2);
            var58[var64] = var2.readByte();
            var59[var64] = a(var2);
            var60[var64] = a(var2);
            int var65;
            byte[] var66 = new byte[var65 = var2.readUnsignedByte()];
            byte[] var67 = new byte[var65];
            short[] var68 = new short[var65];

            for (int var69 = 0; var69 < var65; var69++) {
               var66[var69] = var2.readByte();
               var67[var69] = var2.readByte();
               var68[var69] = var2.readShort();
            }

            var61[var64] = var66;
            var62[var64] = var67;
            var63[var64] = var68;
         }

         byte var156;
         short[] var157 = new short[var156 = var2.readByte()];
         byte[] var158 = new byte[var156];
         byte[][] var159 = new byte[var156][10];
         short[] var160 = new short[var156];
         byte[] var161 = new byte[var156];
         byte[] var70 = new byte[var156];
         byte[] var71 = new byte[var156];
         byte[] var72 = new byte[var156];
         byte[][] var73 = new byte[var156][];
         byte[][] var74 = new byte[var156][];

         for (int var75 = 0; var75 < var156; var75++) {
            var157[var75] = a(var2);
            var158[var75] = var2.readByte();

            for (int var76 = 0; var76 < 10; var76++) {
               var159[var75][var76] = var2.readByte();
            }

            var160[var75] = a(var2);
            var161[var75] = var2.readByte();
            var70[var75] = var2.readByte();
            var71[var75] = var2.readByte();
            var72[var75] = var2.readByte();
            byte[] var77 = new byte[var2.readByte()];
            var2.readFully(var77);
            var73[var75] = var77;
            byte[] var79 = new byte[var2.readByte()];
            var2.readFully(var79);
            var74[var75] = var79;
         }

         byte var162;
         byte[][] var163 = new byte[var162 = var2.readByte()][12];

         for (int var164 = 0; var164 < var162; var164++) {
            for (int var78 = 0; var78 < 12; var78++) {
               var163[var164][var78] = var2.readByte();
            }
         }

         byte var165;
         short[] var166 = new short[var165 = var2.readByte()];

         for (int var167 = 0; var167 < var165; var167++) {
            var166[var167] = a(var2);
         }

         short var168;
         short[] var80 = new short[var168 = var2.readShort()];
         short[] var81 = new short[var168];
         int[] var82 = new int[var168];
         short[] var83 = new short[var168];
         byte[][] var84 = new byte[var168][];
         byte[][] var85 = new byte[var168][];
         byte[][] var86 = new byte[var168][];
         int[][] var87 = new int[var168][];
         short[][] var88 = new short[var168][];
         byte[][] var89 = new byte[var168][];
         byte[][] var90 = new byte[var168][];
         byte[][] var91 = new byte[var168][];
         byte[][] var92 = new byte[var168][];
         short[][] var93 = new short[var168][];

         for (int var94 = 0; var94 < var168; var94++) {
            var80[var94] = a(var2);
            var81[var94] = a(var2);
            int var95 = var2.readInt();
            var82[var94] = var95;
            var83[var94] = a(var2);
            byte var96;
            byte[] var97 = new byte[var96 = var2.readByte()];
            byte[] var98 = new byte[var96];
            int[] var99 = new int[var96];
            short[] var100 = new short[var96];
            byte[] var101 = new byte[var96];
            byte[] var102 = new byte[var96];
            byte[] var103 = new byte[var96];
            byte[] var104 = new byte[var96];
            short[] var105 = new short[var96];

            for (int var106 = 0; var106 < var96; var106++) {
               var97[var106] = var2.readByte();
               var98[var106] = var2.readByte();
               int var107 = var2.readInt();
               var100[var106] = var2.readShort();
               var101[var106] = var2.readByte();
               var102[var106] = var2.readByte();
               var103[var106] = var2.readByte();
               var104[var106] = var2.readByte();
               var105[var106] = var2.readShort();
               var99[var106] = (var107 & 1) != 0 ? ~var107 : var107;
            }

            byte var181 = var2.readByte();
            Object var184 = null;
            if ((var95 & 1073741824) != 0) {
               var184 = var84[26];
            } else {
               var184 = new byte[var181];
               var2.readFully((byte[])var184);
            }

            var85[var94] = var97;
            var86[var94] = var98;
            var87[var94] = var99;
            var88[var94] = var100;
            var89[var94] = var101;
            var90[var94] = var102;
            var91[var94] = var103;
            var92[var94] = var104;
            var93[var94] = var105;
            var84[var94] = (byte[])var184;
         }

         byte var169;
         short[] var170 = new short[var169 = var2.readByte()];
         byte[] var171 = new byte[var169];
         byte[] var172 = new byte[var169];
         byte[] var173 = new byte[var169];
         short[] var174 = new short[var169];
         byte[] var175 = new byte[var169];
         short[] var176 = new short[var169];
         byte[] var177 = new byte[var169];
         short[] var178 = new short[var169];
         byte[] var179 = new byte[var169];
         short[] var180 = new short[var169];

         for (int var182 = 0; var182 < var169; var182++) {
            var170[var182] = a(var2);
            var171[var182] = var2.readByte();
            var172[var182] = var2.readByte();
            var173[var182] = var2.readByte();
            var174[var182] = a(var2);
            var175[var182] = var2.readByte();
            var176[var182] = a(var2);
            var177[var182] = var2.readByte();
            var178[var182] = a(var2);
            var179[var182] = var2.readByte();
            var180[var182] = a(var2);
         }

         byte var183;
         byte[] var186 = new byte[var183 = var2.readByte()];
         short[] var108 = new short[var183];
         short[] var109 = new short[var183];
         short[] var110 = new short[var183];
         short[] var111 = new short[var183];
         short[] var112 = new short[var183];
         short[][] var113 = new short[var183][];

         for (int var114 = 0; var114 < var183; var114++) {
            var186[var114] = var2.readByte();
            var108[var114] = a(var2);
            var109[var114] = a(var2);
            var110[var114] = var2.readShort();
            var111[var114] = a(var2);
            var112[var114] = var2.readShort();
            byte var115;
            short[] var116 = new short[var115 = var2.readByte()];

            for (int var117 = 0; var117 < var115; var117++) {
               var116[var117] = var2.readShort();
            }

            var113[var114] = var116;
         }

         var2.close();
         this.b = var4;
         this.a2 = var5;
         this.a3 = var6;
         this.b2 = var7;
         this.c = var120;
         this.d = var122;
         this.e = var124;
         this.a4 = var125;
         this.f = var13;
         this.g = var14;
         this.h = var15;
         this.c2 = var16;
         this.d2 = var17;
         this.e2 = var18;
         this.f2 = var19;
         this.g2 = var20;
         this.a5 = var21;
         this.i = var127;
         this.j = var128;
         this.k = var129;
         this.l = var130;
         this.m = var133;
         this.n = var134;
         this.a6 = var136;
         this.o = var138;
         this.f3 = var140;
         this.g3 = var141;
         this.p = var34;
         this.h2 = var35;
         this.q = var36;
         this.r = var37;
         this.b3 = var39;
         this.j2 = var40;
         this.s = var41;
         this.i2 = var42;
         this.c3 = var43;
         this.d3 = var44;
         this.e3 = var45;
         this.a7 = var46;
         this.b4 = var47;
         this.b5 = var48;
         this.c4 = var49;
         this.d4 = var50;
         this.c5 = var51;
         this.t = var145;
         this.u = var148;
         this.k2 = var152;
         this.v = var57;
         this.f4 = var58;
         this.w = var59;
         this.x = var60;
         this.e4 = var61;
         this.f5 = var62;
         this.h3 = var63;
         this.b6 = var156;
         this.y = var157;
         this.g4 = var158;
         this.g5 = var159;
         this.z = var160;
         this.h4 = var161;
         this.i3 = var70;
         this.j3 = var71;
         this.k3 = var72;
         this.h5 = var73;
         this.i4 = var74;
         this.c6 = var162;
         this.j4 = var163;
         this.A = var166;
         this.l2 = var168;
         this.B = var80;
         this.C = var81;
         this.j5 = var82;
         this.D = var83;
         this.k4 = var85;
         this.l3 = var86;
         this.d5 = var87;
         this.i5 = var88;
         this.m2 = var89;
         this.n2 = var90;
         this.o2 = var91;
         this.p2 = var92;
         this.j6 = var93;
         this.q2 = var84;
         this.E = var170;
         this.l4 = var171;
         this.m3 = var172;
         this.n3 = var173;
         this.F = var174;
         this.o3 = var175;
         this.G = var176;
         this.p3 = var177;
         this.H = var178;
         this.q3 = var179;
         this.I = var180;
         this.r2 = var186;
         this.J = var108;
         this.K = var109;
         this.L = var110;
         this.M = var111;
         this.N = var112;
         this.k5 = var113;
      } catch (IOException var118) {
      }
   }

   public final int a() {
      return Z(this.d6);
   }

   private static int Z(int var0) {
      return var0 % 1440;
   }

   public final int b() {
      return aa(this.d6);
   }

   private static int aa(int var0) {
      return var0 / 1440 % 7;
   }

   private static int ab(int var0) {
      return var0 / 1440 * 1440;
   }

   public final int c() {
      return (this.d6 - 840) / 1440;
   }

   public final void a12(int var1) {
      this.e5 += var1;
      if (var1 < 0) {
         this.f6 -= var1;
      } else {
         if (this.e5 >= 1000) {
            this.e3(43);
         }

         if (this.e5 >= 5000) {
            this.e3(62);
         }
      }
   }

   private void j2() {
      this.d6 = 840;
      this.e5 = 700;
      this.f6 = 0;
      this.a9 = false;
      this.c8 = 0;
      this.a10 = -1;
   }

   private void p2(int var1) {
      this.m4 += var1;
      if (this.m4 > 1000) {
         int var2 = this.m4 / 1000;
         this.m4 -= var2 * 1000;
         int var3 = this.d6;
         this.d6 += var2;
         this.a.a6().h2(var3, this.d6);
      }
   }

   public final int a3(int var1) {
      if (var1 == 125) {
         return 10;
      } else if (var1 == 158) {
         int var2;
         if ((var2 = this.V(0)) != -1) {
            int var3 = this.W(0);
            if ((this.d(var2, var3) & 2) != 0) {
               return 15;
            }
         }

         return 30;
      } else {
         return 0;
      }
   }

   public final int d() {
      int var1 = this.c();

      for (int var2 = 0; var2 < this.g(); var2++) {
         var1 += this.k(var2);
      }

      if (var1 >= 15) {
         return 2;
      } else {
         return var1 >= 5 ? 1 : 0;
      }
   }

   public final boolean a11() {
      int var1;
      if ((var1 = this.d()) > this.c8) {
         this.c8 = (byte)var1;
         return true;
      } else {
         return false;
      }
   }

   public final void a10() {
      this.a10 = (short)this.c();
   }

   public final int e() {
      int var1 = this.c();
      byte var2 = this.a.a24.a5;
      if (this.a.a24.a5 == 0) {
         if (var1 >= 3 && !this.b8(16)) {
            this.r2(16);
            return 777;
         }
      } else if (var2 == 1 && var1 - this.a10 >= 5 && !this.b8(32)) {
         this.r2(32);
         return 778;
      }

      return -1;
   }

   private int ac(int var1) {
      short var2 = 480;
      if (this.j(var1, 10) != -1) {
         var2 = 540;
      } else if (this.j(var1, 11) != -1) {
         var2 = 420;
      }

      return var2;
   }

   public final int b2(int var1) {
      int var2 = this.d6;
      int var3;
      if (var1 == 0) {
         int var4 = this.n5[1];
         int var5 = 65536 - com.ea.sims3.FixedMath.div(var4, 6553600);
         var3 = var2 + com.ea.sims3.FixedMath.mul(var5, 600);
      } else {
         var3 = var2 + 600;
      }

      int var6;
      if (this.V(var1) != -1 && (var6 = this.n(var1, var2) - 60) > var2 && var6 - var2 < 1440) {
         var3 = Math.min(var6, var3);
      }

      return var3;
   }

   public final void b4() {
      int var1 = this.V(0);
      int var2 = this.W(0);
      int var3 = this.f(var1, var2);
      int var4 = this.a();
      int var5;
      if ((var5 = var3 - var4) < 0) {
         var5 += 1440;
      }

      int var6 = var5 * 1000;
      this.p2(var6);
      this.c2(1, -983040, 3);
      this.c2(3, -1310720, 8);
      this.d2(5, -1638400);
      this.d2(2, this.y(2) < 2293760 ? 3276800 : -983040);
      if (this.V(0) != 0) {
         this.c2(0, -1310720, 0);
      }

      this.f2();
   }

   public final void b7(int var1) {
      int var2 = this.d6;
      int var3 = (var1 - var2) * 1000;
      this.p2(var3);
      this.d2(1, 6553600);
      this.d2(2, -655360);
      this.c2(0, -1310720, 0);
      this.c2(3, -983040, 8);
      this.f2();
   }

   private void k2() {
      if (this.s2 == null) {
         int var1 = this.a.a24.h();
         this.s2 = new byte[var1];
      }

      com.ea.sims3.GameCanvas.a3(this.s2, 0);
   }

   public final int c(int var1) {
      return this.s2[var1];
   }

   public final int f() {
      return this.s2.length - com.ea.sims3.GameCanvas.b(0, this.s2);
   }

   public final int d(int var1) {
      return com.ea.sims3.GameCanvas.b(var1, this.s2, 0);
   }

   public final void b2(int var1, int var2) {
      int var3 = this.a.a24.z(var1);
      this.s2[var1] = (byte)Math.min(var3, this.s2[var1] + var2);
      if (var2 > 0) {
         Lot var4 = this.a.a24;
         if ((this.a.a24.w(var1) & 2048) != 0) {
            this.e3(27);
         }
      }
   }

   public final void a(short[] var1, short[] var2, int var3) {
      short var4 = this.B[var3];
      com.ea.sims3.GameCanvas.b2(var1, var4);
      com.ea.sims3.GameCanvas.b2(var2, var4);
      Lot var5 = this.a.a24;
      byte[] var6 = this.s2;

      for (int var7 = 0; var7 < var6.length; var7++) {
         if (var6[var7] > 0 && (var5.w(var7) & 64) != 0) {
            com.ea.sims3.GameCanvas.c2(var1, var5.u(var7));
            com.ea.sims3.GameCanvas.c2(var2, var3);
         }
      }
   }

   public final void b(short[] var1, short[] var2, int var3) {
      short var4 = this.B[var3];
      com.ea.sims3.GameCanvas.b2(var1, var4);
      com.ea.sims3.GameCanvas.b2(var2, var4);
      Lot var5 = this.a.a24;
      byte[] var6 = this.s2;

      for (int var7 = 0; var7 < var6.length; var7++) {
         if (var6[var7] > 0 && (var5.w(var7) & 2048) != 0) {
            com.ea.sims3.GameCanvas.c2(var1, var5.u(var7));
            com.ea.sims3.GameCanvas.c2(var2, var3);
         }
      }
   }

   public final boolean a13(int var1) {
      Lot var2 = this.a.a24;
      int var3 = this.a.a24.E(var1);

      for (int var4 = 0; var4 < var3; var4++) {
         int var5 = var2.k(var1, var4);
         if (this.c(var5) < 1) {
            return false;
         }
      }

      for (int var6 = 0; var6 < var3; var6++) {
         int var7 = var2.k(var1, var6);
         this.b2(var7, -1);
      }

      this.g6 = var1;
      return true;
   }

   public final boolean b5() {
      return this.c(2) > 0;
   }

   public final boolean c3() {
      return this.c(1) > 0;
   }

   public final boolean d3() {
      return this.c(0) > 0;
   }

   private void l2() {
      int var1 = this.g();
      if (this.k6 == null) {
         this.k6 = new int[var1];
      }

      com.ea.sims3.GameCanvas.a(this.k6, 0);
   }

   public final int e(int var1) {
      return this.k6[var1];
   }

   public final int g() {
      return this.b.length;
   }

   public final int f(int var1) {
      return this.b[var1];
   }

   public final int g(int var1) {
      return this.a3[var1][0];
   }

   public final int h(int var1) {
      return this.a3[var1][1];
   }

   public final int i(int var1) {
      return this.a3[var1][2];
   }

   public final int j(int var1) {
      return this.b2[var1][this.k(var1)];
   }

   public final int k(int var1) {
      return this.k6[var1] / 65536;
   }

   public final void c4(int var1) {
      int[] var2 = this.k6;
      int var3;
      if ((var3 = this.k(var1)) < 5) {
         var2[var1] += this.a2[var1][var3];
         var2[var1] = Math.min(var2[var1], 65536 * (var3 + 1));
      }
   }

   private void m2() {
      this.j3[0] = -1;
      this.k3[0] = 0;
      this.d8 = -1;
      this.b7 = 0;
      this.c9 = 0;
      if (this.t2 == null) {
         this.t2 = new byte[this.c.length];
      }

      com.ea.sims3.GameCanvas.a3(this.t2, 0);
      this.e7 = -1;
      this.n4 = 1440000;
   }

   private void q2(int var1) {
      this.n4 += var1;
      if (this.n4 > 1440000) {
         this.n4 = 0;
         int var2 = this.c.length;
         if (!this.a.j2) {
            var2--;
         }

         this.e7 = (byte)(this.a.b(0, var2) - 1);
      }
   }

   public final void c2(int var1, int var2) {
      byte var3 = this.j3[0];
      byte var4 = this.k3[0];
      this.j3[0] = (byte)var1;
      this.k3[0] = (byte)var2;
      if (var1 != -1) {
         this.t2[var1] = (byte)var2;
      }

      this.b7 = 0;
      this.c9 = 0;
      if (var1 != -1) {
         if (var3 != var1) {
            this.d8 = (byte)this.b();
         }

         GameCanvas var5 = this.a;
         GameScreen var6 = this.a.a6();
         int var7 = var3 == var1 ? (var2 > var4 ? 635 : 637) : 634;
         int var9 = aa(this.n(0, this.d6));
         int var10;
         if (aa(this.d6 + 1440) == var9) {
            var10 = 660;
         } else {
            var10 = c7[var9];
         }

         SdkString var11 = var5.a4(var10);
         SdkString var12 = var5.a2();
         var5.h2(this.f2[var1][var2]);
         SdkString var13 = var5.a4(var5.a24.i(this.e[var1]));
         var5.a(-11, var7, var11, var12, var13);
         byte var14 = 0;
         if (var3 == var1 && var2 > var4) {
            var14 = 4;
         } else if (var1 != -1) {
            var14 = 3;
         }

         int var15 = this.b(var1, var2);
         var6.a4(-11, var15, var14);
      }
   }

   public final int h() {
      int var1 = this.V(0);
      int var2 = this.W(0);
      int var3 = this.c(var1, var2);
      this.a12(var3);
      this.b7++;
      if (this.b7 % 3 == 2 && this.c9 > 0) {
         this.c9--;
      }

      return var3;
   }

   public final void c2() {
      this.c9++;
      if (this.c9 >= 3) {
         this.c2(-1, 0);
      }
   }

   public final int l(int var1) {
      switch (var1) {
         case 0:
         default:
            return this.k(0);
         case 1:
            return this.k(2);
         case 2:
            return this.b7;
         case 3:
            int var2 = 0;

            for (int var3 = 1; var3 < this.b6; var3++) {
               byte var4 = this.v2[var3];
               if ((this.F(var4) & 16) != 0) {
                  var2++;
               }
            }

            return var2;
         case 4:
            return this.k(4);
         case 5:
            return this.k(3);
      }
   }

   public static int m(int var0) {
      switch (var0) {
         case 0:
         default:
            return 486;
         case 1:
            return 500;
         case 2:
            return 708;
         case 3:
            return 707;
         case 4:
            return 514;
         case 5:
            return 507;
      }
   }

   public final boolean a5(int var1, int var2) {
      byte[] var3;
      byte var4 = (var3 = this.a5[var1][var2])[0];
      byte var5 = var3[1];
      if (var4 != -1 && this.l(var4) < var5) {
         return false;
      } else {
         byte var6 = var3[2];
         byte var7 = var3[3];
         if (var6 != -1 && this.l(var6) < var7) {
            return false;
         } else {
            int var8 = this.t(var1);
            byte var9 = this.v2[var8];
            return (this.F(var9) & 2) == 0;
         }
      }
   }

   public final boolean e3() {
      int var1 = this.V(0);
      int var2;
      return (var2 = this.W(0)) < this.p(var1) - 1 && this.a5(var1, var2 + 1) && this.b7 >= 10;
   }

   public final int a(Entity var1) {
      byte var2 = this.j3[0];
      int var3 = -1;
      if (var1 != null && var1.type != 77) {
         var3 = com.ea.sims3.GameCanvas.a(var1.type, this.e);
      } else {
         int var4;
         if ((var4 = this.a()) >= 450 && var4 <= 1110 && this.e7 != var2) {
            var3 = this.e7;
         }
      }

      return var3;
   }

   public final int n(int var1) {
      return var1 == -1 ? 0 : Math.max(this.t2[var1] - 1, 0);
   }

   private static boolean b3(int var0, int var1, int var2) {
      var1 = (var1 + 1440) % 1440;
      var2 = (var2 + 1440) % 1440;
      return var1 < var2 ? var0 >= var1 && var0 <= var2 : var0 >= var1 || var0 <= var2;
   }

   public final int o(int var1) {
      return this.a2(var1, this.d6);
   }

   public final int a2(int var1, int var2) {
      byte var3 = 0;
      int var4 = aa(var2);
      int var5 = Z(var2);
      int var6;
      int var7 = (var6 = this.ac(var1)) - 600;
      byte var8;
      if ((var8 = this.j3[var1]) == -1) {
         var3 = 8;
      } else {
         short var10;
         label51: {
            byte var9 = this.k3[var1];
            var10 = this.f2[var8][var9];
            if (var1 != 0 || var4 != this.d8) {
               if (var1 == 0) {
                  this.d8 = -1;
               }

               int var11 = a8[var4];
               if ((this.e2[var8][var9] & var11) != 0) {
                  short var13 = this.g2[var8][var9];
                  if (var5 >= var10 - 30) {
                     if (var5 < var10) {
                        var3 = 1;
                        break label51;
                     }

                     if (var5 < var10 + 60) {
                        var3 = 2;
                        break label51;
                     }

                     if (var5 < var13) {
                        var3 = 4;
                        break label51;
                     }
                  }

                  var3 = 8;
                  break label51;
               }
            }

            var3 = 8;
         }

         var7 = var10 - 660;
         var6 = var10 - 60;
      }

      if (b3(var5, var7, var6)) {
         var3 |= 16;
      }

      return var3;
   }

   private int n(int var1, int var2) {
      int var3 = this.V(var1);
      int var4 = this.W(var1);
      int var5 = this.d(var3, var4);
      int var6 = this.e(var3, var4);
      int var7 = Z(var2);
      int var8;
      int var9 = var8 = aa(var2);

      for (int var10 = 0; var10 < 7; var10++) {
         var9 = (var8 + var10) % 7;
         if ((var5 & a8[var9]) != 0 && (var1 != 0 || var9 != this.d8) && (var9 != var8 || var7 < var6)) {
            break;
         }
      }

      int var11 = ab(this.d6) + var6;
      if (var9 != var8) {
         var11 += (var9 - var8 + 7) % 7 * 1440;
      }

      return var11;
   }

   private boolean b(Entity var1, int var2) {
      byte var3;
      if ((var3 = this.j3[var2]) == -1) {
         return false;
      } else if (var1 != null) {
         int var4 = this.s(var3);
         return var1.type == var4;
      } else {
         return false;
      }
   }

   public final boolean a(Entity var1, int var2) {
      return var1 != null && !this.b(var1, var2) ? false : (this.o(var2) & 3) != 0;
   }

   public final int p(int var1) {
      return this.c2[var1].length;
   }

   public final int q(int var1) {
      return var1 == -1 ? 667 : this.c[var1];
   }

   public final int r(int var1) {
      return this.d[var1];
   }

   public final int s(int var1) {
      return this.e[var1];
   }

   public final int t(int var1) {
      return this.a4[var1];
   }

   public final int i() {
      byte var1 = this.j3[0];
      return this.a.d() < 50 ? this.f[var1] : this.g[var1];
   }

   public final int u(int var1) {
      return this.h[var1];
   }

   public final int b(int var1, int var2) {
      return this.c2[var1][var2];
   }

   public final int c(int var1, int var2) {
      return this.d2[var1][var2];
   }

   public final int d(int var1, int var2) {
      return this.e2[var1][var2];
   }

   public final int e(int var1, int var2) {
      return this.f2[var1][var2];
   }

   public final int f(int var1, int var2) {
      return this.g2[var1][var2];
   }

   public final byte[] a6(int var1, int var2) {
      return this.a5[var1][var2];
   }

   public final void d3(int var1) {
      this.a11 = (byte)var1;
   }

   public final int j() {
      return this.i.length;
   }

   public final short a11(int var1) {
      return var1 == 1 && this.i(0, 0) == 1 ? 534 : this.i[var1];
   }

   public final short b6(int var1) {
      return this.j[var1];
   }

   public final int k() {
      return this.k[this.a11];
   }

   private int t() {
      return this.l[this.a11];
   }

   public final int l() {
      return com.ea.sims3.GameCanvas.d(this.t(), this.n);
   }

   public final int v(int var1) {
      return com.ea.sims3.GameCanvas.c(var1, this.n, this.t());
   }

   private int u() {
      switch (this.a11) {
         case 0:
            return 536870912;
         case 1:
            return 134217728;
         case 2:
            return 268435456;
         case 3:
            return 33554432;
         case 4:
            return 67108864;
         case 5:
         default:
            return 16777216;
      }
   }

   public final boolean f3() {
      if (this.b8(1) && !this.b8(2)) {
         this.r2(2);
         return true;
      } else {
         return false;
      }
   }

   private void n2() {
      this.b8 = -1;
      this.d9 = (short)this.a.b(0, this.n.length - 1);
      this.o4 = 115000;
      if (this.u2 == null) {
         this.u2 = new byte[4];
         this.l5 = new int[this.b3.length];
         this.m5 = new int[10];
      }

      com.ea.sims3.GameCanvas.a3(this.u2, -1);
      this.p4 = 0;
      this.q4 = 0;
      com.ea.sims3.GameCanvas.a(this.l5, this.d6);
      this.e8 = 0;
      this.f7 = 0;
      com.ea.sims3.GameCanvas.a(this.m5, -1);
      this.r3 = 0;
   }

   public final boolean b8(int var1) {
      return (this.q4 & var1) != 0;
   }

   private void r2(int var1) {
      this.q4 |= var1;
   }

   private void s2(int var1) {
      this.o4 += var1;
      if (this.b8 != -1) {
         if (this.o4 > 240000) {
            this.a.a6().e3(this.b8);
            this.b8 = -1;
            this.o4 = 0;
         }
      } else if (this.o4 > 120000) {
         this.b8 = (byte)this.v();
         this.o4 = 0;
         this.a.a6().j2();
      }

      int var2 = this.d6;

      for (int var3 = 0; var3 < this.l5.length; var3++) {
         int var4;
         if ((var4 = this.l5[var3]) != -1 && var2 - var4 > 4320) {
            this.l5[var3] = var2;
            this.e3(this.b3[var3]);
         }
      }
   }

   private int v() {
      int var6 = this.n.length;
      int var7 = -1;
      int var8 = this.d9;

      for (int var9 = 0; var9 < var6; var9++) {
         while ((var8 = (1543 * var8 + 3571) % 257) >= var6) {
         }

         short var11;
         if (this.ad(var8) == -1 && ((var11 = this.n[var8]) & 8) == 0 && this.f4(var8) && (!this.a.e4(var8) || this.a.d() >= 70)) {
            var7 = var8;
            this.d9 = (short)var8;
            break;
         }
      }

      switch (var7) {
         case 44:
            this.e8 = 0;
            break;
         case 45:
            this.f7 = 0;
            break;
         case 52:
            this.y2(1536);
      }

      int var12;
      if ((var12 = com.ea.sims3.GameCanvas.a(var7, this.b3)) != -1 && this.l5[var12] != -1) {
         this.l5[var12] = this.d6;
      }

      this.a.o2(var7);
      return var7;
   }

   public final void d2() {
      int var1;
      if ((var1 = this.ad(-1)) != -1 && this.b8 != -1) {
         this.u2[var1] = this.b8;
         this.b8 = -1;
         this.o4 = 0;
         this.a.a6().f3(var1);
      }
   }

   public final boolean g3() {
      return com.ea.sims3.GameCanvas.a(-1, this.u2) != -1;
   }

   private boolean f4(int var1) {
      Lot var2 = this.a.a24;
      switch (var1) {
         case 0:
            if (!this.b8(4)) {
               return true;
            }

            return false;
         case 1:
            if (!this.b8(8)) {
               return true;
            }

            return false;
         case 2:
            if (this.c(2) == 0) {
               return true;
            }

            return false;
         case 3:
            if (this.c(3) == 0) {
               return true;
            }

            return false;
         case 4:
            if (this.c(1) == 0) {
               return true;
            }

            return false;
         case 5:
            if (!var2.a13(19)) {
               return true;
            }

            return false;
         case 6:
            if (!var2.a13(3)) {
               return true;
            }

            return false;
         case 7:
            if (this.k(0) < 5) {
               return true;
            }

            return false;
         case 8:
            if (this.k(2) < 5) {
               return true;
            }

            return false;
         case 9:
            if (this.k(1) < 5) {
               return true;
            }

            return false;
         case 10:
            if (this.k(3) < 5) {
               return true;
            }

            return false;
         case 11:
         case 50:
            if (com.ea.sims3.GameCanvas.b(0, this.v2) > 1) {
               return true;
            }

            return false;
         case 12:
         case 13:
         case 14:
            int var5 = var1 == 12 ? 16 : (var1 == 13 ? 4 : 8);

            for (int var4 = 1; var4 < this.b6; var4++) {
               if ((this.F(this.v2[var4]) & var5) == 0) {
                  return true;
               }
            }

            return false;
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 51:
         case 52:
         case 53:
         default:
            return true;
         case 20:
            if (this.V(0) == -1) {
               return true;
            }

            return false;
         case 27:
            for (int var3 = 0; var3 < this.s2.length; var3++) {
               if (this.s2[var3] == 0 && (var2.w(var3) & 2048) != 0) {
                  return true;
               }
            }

            return false;
         case 41:
            if (var2.a13(9) && !var2.b8(43) && !var2.b8(44) && !var2.b8(45)) {
               return true;
            }

            return false;
         case 42:
            if (var2.a13(22) && !var2.b8(95) && !var2.b8(96) && !var2.b8(97)) {
               return true;
            }

            return false;
         case 43:
            if (this.e5 < 500) {
               return true;
            }

            return false;
         case 54:
         case 55:
            return this.V(0) != -1 && this.W(0) < this.p(this.V(0));
      }
   }

   private int ad(int var1) {
      return com.ea.sims3.GameCanvas.a(var1, this.u2);
   }

   public final int w(int var1) {
      return this.u2[var1];
   }

   public final int x(int var1) {
      return var1 >= 0 && var1 < this.m.length ? this.m[var1] : 548;
   }

   public final int m() {
      return this.m.length;
   }

   private int ae(int var1) {
      return this.n[var1];
   }

   public final void e3(int var1) {
      GameCanvas var2 = this.a;
      GameScreen var3 = this.a.a6();
      boolean var4 = false;
      int var5;
      if ((var5 = this.ad(var1)) != -1) {
         var4 = true;
         this.u2[var5] = -1;
      } else if (this.b8 == var1) {
         var4 = true;
         this.b8 = -1;
         this.o4 = 0;
      }

      byte var6 = 0;
      switch (var1) {
         case 0:
            var6 = 4;
            break;
         case 1:
            var6 = 8;
      }

      this.r2(var6);
      int var7 = 0;
      int var8 = 0;
      int var9 = this.m();
      int var10 = this.t();

      for (int var11 = 0; var11 < var9; var11++) {
         if ((this.ae(var11) & var10) != 0) {
            int var12 = 1 << var7;
            if (var1 == var11 && (this.p4 & var12) == 0) {
               this.p4 |= var12;
               var4 = true;
            }

            if ((this.p4 & var12) != 0) {
               var8++;
            }

            var7++;
         }
      }

      if (var4) {
         var2.p2(var1);
         var3.f2(var1, var5);
      }

      if (var8 == var7) {
         this.r2(1);
      }

      boolean var13 = var2.j2;
      if (!var2.j2) {
         int var14 = this.m.length;
         if (var2.g() == var14) {
            var3.k2();
         }
      }

      switch (var1) {
         case 22:
            this.e8++;
            if (this.e8 >= 15) {
               this.e3(44);
            }
      }
   }

   public final void f3(int var1) {
      Lot var2 = this.a.a24;
      int var3;
      if ((var3 = this.a.a24.h(var1)) == 3) {
         this.e3(6);
      } else if (var3 == 9) {
         if (var1 != 42) {
            this.e3(41);
         }
      } else if (var3 == 19) {
         this.e3(5);
      } else if (var3 == 22 && var1 != 94) {
         this.e3(42);
      }

      if (var2.b5()) {
         this.e3(64);
      }

      this.e3(21);
   }

   public final void g2(int var1) {
      this.e3(21);
      if (var1 == 2) {
         this.e3(2);
      }

      if (var1 == 3) {
         this.e3(3);
      }

      if (var1 == 1) {
         this.e3(4);
      }
   }

   public final void h2(int var1) {
      this.f7 = (short)(this.f7 + var1);
      if (this.f7 > 30) {
         this.e3(45);
      }
   }

   public final boolean h3() {
      int[] var1 = this.m5;
      int var2 = this.d6;
      int var3 = 0;
      int var4 = -1;

      for (int var5 = 0; var5 < 10; var5++) {
         int var6 = var1[var5];
         if (var2 - var6 > 1440) {
            var1[var5] = -1;
            var6 = -1;
         }

         if (var6 == -1 && var4 == -1) {
            var4 = var5;
            var1[var5] = var2;
            var6 = var2;
         }

         if (var6 != -1) {
            var3++;
         }
      }

      if (var4 != -1 && var3 < 10) {
         if (var3 >= 8) {
            this.e3(61);
         }

         this.e3(53);
         return false;
      } else {
         return true;
      }
   }

   public final boolean c5(int var1) {
      return (this.p4 & 1 << var1) != 0;
   }

   public final boolean d4(int var1) {
      int var2;
      return (var2 = this.a.a24.s(var1)) != -1 ? (this.r3 & 1 << var2) != 0 : false;
   }

   public final void i2(int var1) {
      int var2;
      if ((var2 = this.a.a24.s(var1)) != -1) {
         this.r3 |= 1 << var2;
      }
   }

   public final short c3(int var1) {
      return this.o[var1];
   }

   public final short d2(int var1) {
      return this.q[var1];
   }

   public final short e2(int var1) {
      return this.r[var1];
   }

   public final int y(int var1) {
      return this.n5[var1];
   }

   private void o2() {
      if (this.n5 == null) {
         this.n5 = new int[this.a6];
         this.o5 = new int[this.a6];
         this.q5 = new int[this.a6];
         this.p5 = new int[this.a6];
      }

      System.arraycopy(this.f3, 0, this.n5, 0, this.a6);
      System.arraycopy(this.g3, 0, this.o5, 0, this.a6);
      com.ea.sims3.GameCanvas.a(this.q5, 0);
      com.ea.sims3.GameCanvas.a(this.p5, 65536);
      this.s3 = 0;
   }

   private void t2(int var1) {
      this.s3 += var1;
      if (this.s3 > 500) {
         this.s3 -= 500;
         int[] var2 = this.o5;
         int[] var3 = this.p5;
         int[] var4 = this.q5;

         for (int var5 = 0; var5 < this.a6; var5++) {
            int var6 = com.ea.sims3.FixedMath.mul(var3[var5], var2[var5]);
            this.d2(var5, var6 + var4[var5] >> 1);
         }
      }
   }

   public final void d2(int var1, int var2) {
      int[] var3 = this.n5;
      this.n5[var1] = this.n5[var1] + var2;
      var3[var1] = com.ea.sims3.FixedMath.clamp(var3[var1], 0, 6553600);
   }

   private void c2(int var1, int var2, int var3) {
      if (this.z(var3) == -1) {
         this.d2(var1, var2);
      }
   }

   public final void e2(int var1, int var2) {
      this.q5[var1] = var2;
   }

   public final int n() {
      short var1 = 0;
      int[] var2 = this.n5;
      short[] var3 = this.p;
      int[] var4 = this.h2;

      for (int var5 = 0; var5 < var2.length; var5++) {
         if (var2[var5] < var4[var5]) {
            var1 |= var3[var5];
         }
      }

      return var1;
   }

   private void p2() {
      if (this.O == null) {
         this.O = new short[6];
         this.r4 = new int[6];
      }

      com.ea.sims3.GameCanvas.a2(this.O, -1);
      com.ea.sims3.GameCanvas.a(this.r4, 0);
   }

   private void u2(int var1) {
      GameCanvas var2 = this.a;
      short[] var3 = this.O;
      int[] var4 = this.r4;
      byte[] var5 = this.e3;
      int[] var6 = this.i2;
      byte[] var7 = this.c3;

      for (int var8 = 0; var8 < 6; var8++) {
         short var9;
         if ((var9 = var3[var8]) != -1) {
            if ((var5[var9] & 2) == 0 && !this.g3(var9)) {
               this.w2(var9);
            } else {
               int var10 = var6[var9] * 1000;
               var4[var8] += var1;
               if (var4[var8] > var10) {
                  var4[var8] = var10;
                  if (var7[var9] != -1) {
                     this.k2(var8, var7[var9]);
                  } else if (!this.g3(var9)) {
                     this.w2(var9);
                  } else {
                     switch (var9) {
                        case 2:
                           var2.a2(-11, 802, var2.b2());
                           var2.a6().d3(-11);
                           this.f2();
                           return;
                        case 5:
                           var2.a6().e2();
                           this.f2();
                           this.k2(var8, 4);
                           return;
                        case 10:
                           this.d2(2, 3276800);
                           this.w2(var9);
                           GameScreen var11;
                           if (!(var11 = var2.a6()).b5()) {
                              Sim var12 = var11.a14;
                              if (!var11.a14.l3()) {
                                 var12.F();
                              }

                              var12.a(4, null, 0, 0);
                           }

                           this.f2();
                           return;
                     }
                  }
               }
            }
         }
      }

      int var13 = this.j2;

      for (int var14 = 0; var14 < var13; var14++) {
         int var15 = var14;
         if ((var5[var14] & 8) == 0 && this.g3(var14)) {
            if (var14 == 3 && (this.a.a6().a14.h2 == 50 || this.z(6) != -1)) {
               var15 = 6;
            }

            int var16;
            if ((var16 = this.z(var15)) == -1) {
               this.v2(var15);
               this.f2();
               return;
            }

            if (var15 == 6) {
               this.r4[var16] = 0;
            }
         }
      }
   }

   private void q2() {
      int[] var1 = this.s4;
      com.ea.sims3.GameCanvas.a(this.s4, 0);

      for (int var2 = 0; var2 < 6; var2++) {
         short var3;
         if ((var3 = this.O[var2]) != -1) {
            byte[] var4 = this.b5[var3];
            byte[] var5 = this.c4[var3];
            int var6 = var4.length;

            for (int var7 = 0; var7 < var6; var7++) {
               var1[var4[var7]] = var1[var4[var7]] + (var5[var7] << 16);
            }
         }
      }

      for (int var8 = 0; var8 < 2; var8++) {
         var1[var8] = com.ea.sims3.FixedMath.clamp(var1[var8], -6553600, 6553600);
      }

      this.s2();
   }

   private void k2(int var1, int var2) {
      short var3 = this.O[var1];
      this.O[var1] = (short)var2;
      this.r4[var1] = 0;
      Sim var4;
      (var4 = this.a.a6().a14).u2(var3);
      var4.t2(var2);
      byte[][] var5 = this.d4;
      int[][] var6 = this.c5;
      int[] var7 = this.p5;
      if (var3 != -1) {
         for (int var8 = 0; var8 < var5[var3].length; var8++) {
            var7[var5[var3][var8]] = var7[var5[var3][var8]] + var6[var3][var8];
         }
      }

      if (var2 != -1) {
         for (int var9 = 0; var9 < var5[var2].length; var9++) {
            var7[var5[var2][var9]] = var7[var5[var2][var9]] - var6[var2][var9];
         }

         if (var2 == 9) {
            this.w2(8);
         } else if (var2 == 6) {
            this.w2(3);
            this.w2(7);
         } else if (var2 == 3) {
            this.w2(6);
            this.w2(7);
         }

         GameScreen var10 = this.a.a6();
         if (var2 == 2) {
            var10.c2(801, 401);
         } else if (var2 == 1) {
            var10.a2((byte)10);
         } else if (var2 == 4) {
            var10.a2((byte)11);
         } else if (var2 == 5) {
            var10.a2((byte)12);
         } else if (var2 == 10) {
            var10.a2((byte)13);
         } else if (var2 == 9) {
            var10.a2((byte)14);
         } else if (var2 == 11) {
            var10.a2((byte)15);
         }
      }

      this.q2();
   }

   private void v2(int var1) {
      byte var2;
      if ((var2 = this.d3[var1]) != -1) {
         this.l5[var2] = -1;
      }

      int var3 = com.ea.sims3.GameCanvas.a(-1, this.O);
      this.k2(var3, var1);
   }

   private void w2(int var1) {
      byte var2;
      if ((var2 = this.d3[var1]) != -1) {
         this.l5[var2] = this.d6;
      }

      int var3;
      if ((var3 = com.ea.sims3.GameCanvas.a(var1, this.O)) != -1) {
         this.k2(var3, -1);
      }
   }

   private boolean g3(int var1) {
      byte var2;
      if ((var2 = this.c3[var1]) != -1 && this.z(var2) != -1) {
         return false;
      } else {
         int[] var3 = this.n5;
         byte[] var4 = this.a7[var1];
         int[] var5 = this.b4[var1];
         int var6;
         if ((var6 = var4.length) == 0) {
            return true;
         } else {
            for (int var7 = 0; var7 < var6; var7++) {
               byte var8 = var4[var7];
               int var9 = var5[var7];
               int var10 = var3[var8];
               if (var9 > 0 && var10 > var9) {
                  return true;
               }

               if (var9 < 0 && var10 < -var9) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public final int z(int var1) {
      short[] var2 = this.O;

      for (int var3 = 0; var3 < 6; var3++) {
         if (var2[var3] == var1) {
            return var3;
         }
      }

      return -1;
   }

   public final int A(int var1) {
      return this.O[var1];
   }

   public final int B(int var1) {
      return this.s[var1];
   }

   public final int C(int var1) {
      return this.e3[var1];
   }

   public static int o() {
      return 17;
   }

   public final short f2(int var1) {
      return this.t[var1];
   }

   private void r2() {
      if (this.s4 == null) {
         this.s4 = new int[4];
      }

      com.ea.sims3.GameCanvas.a(this.s4, 1638400);
   }

   private void s2() {
      int[] var1 = this.s4;
      int var2 = Integer.MIN_VALUE;
      int var3 = -1;
      int var4 = Integer.MAX_VALUE;

      for (int var5 = 0; var5 < 4; var5++) {
         if (var1[var5] > var2) {
            var2 = var1[var5];
         }

         if (var1[var5] < var4) {
            var3 = var5;
            var4 = var1[var5];
         }
      }

      byte var7 = 3;
      int var6 = var2;
      if (var4 < -655360) {
         if (var4 < -3276800) {
            switch (var3) {
               case 0:
               default:
                  var7 = 5;
                  break;
               case 1:
                  var7 = 7;
                  break;
               case 2:
                  var7 = 9;
                  break;
               case 3:
                  var7 = 11;
            }
         } else {
            switch (var3) {
               case 0:
               default:
                  var7 = 4;
                  break;
               case 1:
                  var7 = 6;
                  break;
               case 2:
                  var7 = 8;
                  break;
               case 3:
                  var7 = 10;
            }
         }

         var6 = var4;
      } else if (var2 > 655360) {
         if (var2 > 4915200) {
            var7 = 0;
         } else if (var2 > 2621440) {
            var7 = 1;
         } else {
            var7 = 2;
         }

         var6 = var2;
      }

      this.t3 = var7;
      this.h6 = var6;
   }

   public final int p() {
      return P[this.t3];
   }

   private void t2() {
      int var1 = this.b6;
      if (this.v2 == null) {
         byte[] var2 = new byte[var1];
         short[] var3 = new short[var1];
         int[][] var4 = new int[var1][];
         int[][] var5 = new int[var1][];
         short[][] var6 = new short[var1][];
         byte[][] var7 = new byte[var1][];

         for (int var8 = 1; var8 < var1; var8++) {
            var4[var8] = new int[this.k2];
            var5[var8] = new int[this.A.length];
            var6[var8] = new short[10];
            var7[var8] = new byte[this.h5[var8].length];
         }

         this.v2 = var2;
         this.Q = var3;
         this.e9 = var4;
         this.f8 = var5;
         this.l6 = var6;
         this.r5 = var7;
      }

      for (int var9 = 1; var9 < var1; var9++) {
         this.v2[var9] = 0;
         this.Q[var9] = 0;
         com.ea.sims3.GameCanvas.a(this.e9[var9], 0);
         com.ea.sims3.GameCanvas.a2(this.l6[var9], -1);
         com.ea.sims3.GameCanvas.a3(this.r5[var9], 0);
      }

      this.u3 = 0;
      this.i6 = -1;
      this.v3 = 0;
   }

   private void x2(int var1) {
      if (this.v3 < 10000) {
         this.v3 = this.v3 + this.a.a6().b5;
         if (this.v3 >= 10000) {
            this.i6 = -1;
         }
      }

      this.u3 += var1;
      if (this.u3 > 3000) {
         this.u3 = 0;
      }

      int var2 = this.b6;

      for (int var3 = 1; var3 < var2; var3++) {
         this.i3(var3);
         if (this.u3 == 0) {
            int var4 = this.ag(var3);
            this.l2(var3, 327680);
            int var5 = this.ag(var3);
            if (this.i6 == var3 && this.v3 < 10000 && var4 != var5) {
               short var6 = this.A[var5];
               int var7 = this.R(var3);
               this.a.a4(-12, var6, var7);
               this.a.a6().d2(-12, -1);
            }
         }
      }
   }

   public final void j2(int var1) {
      this.i6 = var1;
      this.v3 = 0;
   }

   public final boolean i3() {
      return this.v3 < 10000;
   }

   private void l2(int var1, int var2) {
      int[] var3 = this.f8[var1];

      for (int var4 = 0; var4 < var3.length; var4++) {
         var3[var4] -= var2;
         if (var3[var4] < 0) {
            var3[var4] = 0;
         }
      }
   }

   private boolean h3(int var1) {
      if (this.v2[var1] == 0) {
         byte var2 = 1;
         if (this.e9[var1][0] < 0) {
            var2 = 2;
         }

         this.m2(var1, var2);
         return true;
      } else {
         return false;
      }
   }

   public final void a4(int var1, int var2, int var3) {
      int[] var4;
      int var5 = (var4 = this.e9[var1])[0] + (var2 << 16);
      int var6 = var4[1] + (var3 << 16);
      var4[0] = com.ea.sims3.FixedMath.clamp(var5, -1966080, 1966080);
      var4[1] = com.ea.sims3.FixedMath.clamp(var6, -327680, 1966080);
   }

   private boolean i3(int var1) {
      byte var2 = this.v2[var1];
      int[] var3 = this.e9[var1];
      byte[] var4 = this.e4[var2];
      byte[] var5 = this.f5[var2];
      short[] var6 = this.h3[var2];
      int var7 = var4.length;

      for (int var8 = 0; var8 < var7; var8++) {
         boolean var9 = true;
         byte var10 = var5[var8];
         short var11 = var6[var8];
         int var12 = var3[var10];
         boolean var13 = var11 < 0;
         int var14 = var11 << 16;
         if (var13 && var12 > var14 || !var13 && var12 < var14) {
            var9 = false;
         }

         if (var9) {
            byte var15;
            if ((var15 = var4[var8]) == 1 && var3[0] < 0) {
               var15 = 2;
            }

            this.m2(var1, var15);
            return true;
         }
      }

      return false;
   }

   private void m2(int var1, int var2) {
      byte[] var3 = this.v2;
      byte var4 = this.v2[var1];
      var3[var1] = (byte)var2;
      com.ea.sims3.GameCanvas.a(this.e9[var1], 0);
      if (var4 == 0) {
         this.e3(11);
      }

      if (var2 == 12) {
         this.e3(65);
      }

      if ((this.F(var4) & 16) == 0 && (this.F(var2) & 16) != 0) {
         this.e3(12);
      }

      if ((this.F(var4) & 8) == 0 && (this.F(var2) & 8) != 0) {
         this.e3(14);
      }

      if ((this.F(var4) & 4) == 0 && (this.F(var2) & 4) != 0) {
         this.e3(13);
      }

      if (com.ea.sims3.GameCanvas.a(0, var3, 1) == -1) {
         this.e3(50);
      }

      int var5 = 0;
      boolean var6b = true;  // var6 is reused later for the short below
      boolean var7 = true;

      for (int var8 = 1; var8 < var3.length; var8++) {
         byte var9 = var3[var8];
         var6b = var6b && (this.f4[var9] & 1) != 0;
         var7 = var7 && (this.f4[var9] & 2) != 0;
         if ((this.f4[var9] & 4) != 0) {
            var5++;
         }
      }

      if (var6b) {
         this.e3(66);
      }

      if (var7) {
         this.e3(67);
      }

      if (var5 >= 3) {
         this.e3(60);
      }

      if (var2 == 12) {
         this.a.a6().c2(1, -1, var1);
      } else {
         short var10 = this.x[var2];
         short var6 = this.y[var1];
         this.a.a4(-11, var10, var6);
         this.a.a6().c2(-11, this.E(var2));
      }
   }

   private boolean c3(int var1, int var2, int var3) {
      GameScreen var4 = this.a.a6();
      byte var6 = -1;
      if (var2 == 85) {
         var6 = 11;
      } else if (var2 == 86) {
         var6 = 12;
      } else if (var2 == 96) {
         var6 = 13;
      } else if (var2 == 95) {
         var6 = 14;
      } else if (var2 == 97) {
         this.g2(var1, 2);
         var4.c2(769, 768);
      } else if (var2 == 98) {
         this.n2(var1, 2);
         var4.c2(770, 765);
      } else if (var2 == 99) {
         if ((var3 & 4096) != 0) {
            var4.c2(636, this.R(var1));
         } else {
            byte var7 = this.j3[0];
            byte var8;
            if ((var8 = this.k3[0]) < this.e2[var7].length - 1) {
               var4.a2(var7, var8 + 1, false);
            }
         }
      } else if (var2 == 100) {
         var4.i2();
      }

      if (var6 != -1) {
         this.m2(var1, var6);
         return true;
      } else {
         return false;
      }
   }

   public static int D(int var0) {
      return R[var0 & 15];
   }

   public final void a(int[] var1, int var2, int var3, int var4) {
      GameScreen var5 = this.a.a6();
      int var6 = 0;
      short var7 = -1;
      short var8 = -1;
      short var9 = 0;
      boolean var10 = false;
      int var11 = this.j5[var4];
      if (var3 == 0) {
         if ((var11 & 524288) == 0) {
            if (var4 == 19) {
               var6 = 35;
               var7 = 11;
            } else {
               var6 = 19;
               var7 = 10;
            }
         }

         this.g2(var2, 1);
         this.g2(0, 1);
         if (this.h3(var2)) {
            var10 = true;
         }
      } else if (var2 != 0) {
         if ((var11 & 524288) == 0) {
            var6 = 20;
            var7 = 10;
         }
      } else {
         int var12;
         if ((var12 = this.o(var3, var4)) == -1) {
            if ((var11 & 2097152) != 0) {
               var7 = -1;
            } else {
               var7 = -1;
            }
         } else {
            boolean var13 = false;
            byte var14;
            if ((var14 = this.l3[var4][var12]) != -1) {
               var13 = this.e3(var3, var14);
            }

            short[] var15 = this.l6[var3];

            for (int var16 = 9; var16 > 0; var16--) {
               var15[var16] = var15[var16 - 1];
            }

            var15[0] = (short)var4;
            if ((var11 & 16) != 0) {
               this.n2(var3, 1);
            } else {
               this.g2(var3, 1);
               this.g2(0, 1);
            }

            var7 = this.j6[var4][var12];
            boolean var30 = true;
            Sim var17 = var5.a7(var3);
            if (var7 == -1 && var17 != null && var17.n3()) {
               var30 = false;
            }

            int var18 = 0;
            if (var30) {
               byte var19 = this.m2[var4][var12];
               byte var20 = this.n2[var4][var12];
               this.a4(var3, var19, var20);
               var18 = var19 + var20;
               if (var20 > 0) {
                  var6 = 64;
               }

               int var21 = this.ag(var3);
               byte var22 = this.o2[var4][var12];
               byte var23 = this.p2[var4][var12];
               int[] var24 = this.f8[var3];

               for (int var25 = 0; var25 < var24.length; var25++) {
                  if (var25 == var22) {
                     var24[var22] += var23 << 16;
                     if (var24[var22] > 13107200) {
                        var24[var22] = 13107200;
                     }
                  } else {
                     var24[var25] -= 655360;
                     if (var24[var25] < 0) {
                        var24[var25] = 0;
                     }
                  }
               }

               int var35;
               if ((var35 = this.ag(var3)) != var21 && !var13) {
                  short var26 = this.A[var35];
                  int var27 = this.R(var3);
                  this.a.a4(-12, var26, var27);
                  var5.d2(-12, -1);
                  if (var35 == 1 || var35 == 2 || var35 == 8) {
                     this.e3(16);
                     this.g2(var3, 16384);
                  }

                  if (var35 == 2) {
                     this.e3(17);
                     this.g2(var3, 64);
                  }

                  if (var35 == 1) {
                     this.e3(18);
                     this.g2(var3, 128);
                  }
               }
            }

            if (((var9 = this.i5[var4][var12]) & 8) != 0) {
               if (this.c3(var3, var4, var9)) {
                  var10 = true;
               }
            } else if (var4 == 99) {
               byte var31 = this.j3[0];
               byte var34;
               if ((var34 = this.k3[0]) < this.e2[var31].length - 1) {
                  var5.e2(var31, var34 + 1);
               }
            }

            if (this.i3(var3)) {
               var10 = true;
            } else if ((var11 & 2097152) == 0 && this.h3(var3)) {
               var10 = true;
            }

            if (var10) {
               var8 = this.w[this.v2[var3]];
            }

            if (var8 != -1) {
               var6 |= 3;
            } else {
               int var32;
               var32 = com.ea.sims3.FixedMath.clamp(var32 = 3 + var18, 0, 6);
               var6 |= var32;
            }

            if (var6 < 3) {
               var6 |= 32;
            } else {
               var6 |= 16;
            }
         }
      }

      if (var7 != -1) {
         int var29 = -1;
         if (var2 == 0) {
            var29 = var3;
         } else if (var3 == 0) {
            var29 = var2;
         }

         if (var29 != -1) {
            this.d2(4, 1310720);
            this.j2(var29);
         }
      }

      if (var2 == 0 && (var9 & 8192) != 0) {
         this.a2(var3, var4, !var10);
      }

      var1[0] = var6;
      var1[1] = var7;
      var1[2] = var8;
   }

   public final int E(int var1) {
      return this.v[var1];
   }

   public final int F(int var1) {
      return this.f4[var1];
   }

   public final int G(int var1) {
      return this.v2[var1];
   }

   public final void a2(int var1, int[] var2) {
      int var3 = this.G(var1);
      int[] var4 = this.e9[var1];
      byte[] var5 = this.e4[var3];
      byte[] var6 = this.f5[var3];
      short[] var7 = this.h3[var3];
      byte var8 = 0;
      int var9 = var5.length;

      for (int var10 = 0; var10 < var9; var10++) {
         byte var11 = var6[var10];
         short var12 = var7[var10];
         int var13 = var4[var11] >> 16;
         if (var12 > 0 && var13 > 0 || var12 < 0 && var13 < 0) {
            var2[var8 + 0] = var5[var10];
            var2[var8 + 1] = com.ea.sims3.FixedMath.div(var13 << 16, var12 << 16);
            var8 += 2;
         }
      }

      var2[var8] = -1;
   }

   public final boolean j3() {
      if (!this.a.a6().a11()) {
         return true;
      } else {
         int var1 = this.a.a24.a();
         int var2 = this.b6;

         for (int var3 = 1; var3 < var2; var3++) {
            if (this.U(var3) == var1) {
               int var4 = this.G(var3);
               if ((this.F(var4) & 32) != 0) {
                  return true;
               }
            }
         }

         return var1 == 0;
      }
   }

   private void y2(int var1) {
      for (int var2 = 1; var2 < this.b6; var2++) {
         this.Q[var2] = (short)(this.Q[var2] & ~var1);
      }
   }

   private int af(int var1) {
      boolean var2 = true;
      int var3 = 0;

      for (int var4 = this.b6 - 1; var4 >= 1; var4--) {
         int var5;
         if ((var5 = this.U(var4)) != 0) {
            boolean var6 = (this.Q[var4] & var1) != 0;
            var2 = var2 && var6;
            if (var6 && com.ea.sims3.GameCanvas.a(var5, this.z, var4 + 1) == -1) {
               var3++;
            }
         }
      }

      return var2 ? -1 : var3;
   }

   public final void f2(int var1, int var2) {
      for (int var3 = 1; var3 < this.b6; var3++) {
         if (this.z[var3] == var1) {
            this.Q[var3] = (short)(this.Q[var3] | var2);
         }
      }

      int var5;
      if ((var5 = this.af(var2)) == -1) {
         switch (var2) {
            case 4:
               this.e3(56);
               break;
            case 8:
               this.e3(57);
               break;
            case 16:
               this.e3(70);
         }
      }

      if (var2 == 512 || var2 == 1024) {
         if (var5 == -1) {
            this.e3(52);
            return;
         }

         int var4 = var2 == 512 ? 1024 : 512;
         if (var5 + this.af(var4) >= 3) {
            this.e3(52);
         }
      }
   }

   public final void g2(int var1, int var2) {
      this.Q[var1] = (short)(this.Q[var1] | var2);
      int var3 = com.ea.sims3.GameCanvas.d(var2, this.Q, 0);
      switch (var2) {
         case 32:
            if (var3 >= 4) {
               this.e3(68);
               return;
            }
            break;
         case 64:
            if (var3 >= 5) {
               this.e3(69);
               return;
            }
            break;
         case 128:
            if (var3 >= 5) {
               this.e3(58);
               return;
            }
            break;
         case 256:
            if (var3 >= 3) {
               this.e3(59);
            }
      }
   }

   private void n2(int var1, int var2) {
      this.Q[var1] = (short)(this.Q[var1] & ~var2);
   }

   public final boolean b3(int var1, int var2) {
      return (this.Q[var1] & var2) != 0;
   }

   public final int q() {
      return this.v2.length - com.ea.sims3.GameCanvas.b(0, this.v2);
   }

   public final int H(int var1) {
      return com.ea.sims3.GameCanvas.b(var1, this.v2, 0);
   }

   public final int I(int var1) {
      return this.D[var1];
   }

   public final int J(int var1) {
      return this.B[var1];
   }

   public final int K(int var1) {
      return this.j5[var1];
   }

   public final int L(int var1) {
      return this.q2[var1].length;
   }

   public final int g(int var1, int var2) {
      return this.q2[var1][var2];
   }

   public final void a(Entity var1, short[] var2, short[] var3) {
      if (var1.hasFlag(16384)) {
         Sim var4 = (Sim)var1;
         this.a(var4, var2, var3);
      } else {
         this.b(var1, var2, var3);
      }
   }

   private void a(Sim var1, short[] var2, short[] var3) {
      int var4 = this.l2;
      short[] var5 = this.B;
      int[] var6 = this.j5;
      int var7 = var1.b;
      boolean var8 = var1.n3();
      this.j2(var7);
      short var9 = this.y[var7];
      com.ea.sims3.GameCanvas.b2(var2, var9);
      com.ea.sims3.GameCanvas.b2(var3, var9);
      if (this.a.a6().b5()) {
         boolean var16 = false;
         com.ea.sims3.GameCanvas.c2(var2, var5[155]);
         com.ea.sims3.GameCanvas.c2(var3, 155);
      } else {
         for (int var10 = 0; var10 < var4; var10++) {
            if ((var6[var10] & 1) != 0 && ((var6[var10] & 4) != 0 || !var8) && ((var6[var10] & 4) == 0 || var8) && var5[var10] != 24) {
               boolean var11 = true;
               int var12;
               if (((var12 = var6[var10]) & 8) != 0) {
                  var11 = !this.b3(var7, 1);
               } else if ((var12 & 16) != 0) {
                  var11 = this.b3(var7, 1);
               }

               if ((var12 & 1056964608) != 0) {
                  int var13 = this.u();
                  var11 = (var12 & var13) != 0;
               }

               if (var11) {
                  var11 = this.o(var7, var10) != -1;
               }

               if (var11) {
                  byte var17 = this.j3[0];
                  if ((var12 & 16384) != 0) {
                     if (var17 != -1) {
                        var11 = this.t(var17) == var7;
                     } else {
                        var11 = false;
                     }
                  }

                  if (var11 && (var12 & 32768) != 0) {
                     if (var17 != -1) {
                        var11 = this.V(var7) == var17;
                     } else {
                        var11 = false;
                     }
                  }
               }

               label146:
               if (var11) {
                  int var18 = this.a.a24.a();
                  int var14 = this.U(var7);
                  boolean var10000;
                  if ((var12 & 196608) == 196608) {
                     var10000 = this.b3(var7, 2);
                  } else {
                     if ((var12 & 65536) != 0) {
                        var11 = var18 == 0 && var14 != 0;
                        break label146;
                     }

                     if ((var12 & 131072) == 0) {
                        break label146;
                     }

                     var10000 = var18 != 0 && var14 == var18;
                  }

                  var11 = var10000;
               }

               if (var11 && (var12 & 8388608) != 0) {
                  int var19 = this.ai(var10);
                  var11 = this.w2[var19] == var7 && this.k3(var19);
               }

               if (var11) {
                  switch (var10) {
                     case 85:
                        var11 = com.ea.sims3.GameCanvas.a(12, this.v2) == -1;
                        break;
                     case 94:
                        Sim var20 = this.a.a6().a14;
                        int var21 = Math.abs(var1.posX - var20.posX);
                        int var15 = Math.abs(var1.posY - var20.posY);
                        var11 = var21 + var15 > 2097152;
                        break;
                     case 97:
                        var11 = com.ea.sims3.GameCanvas.b(2, this.Q) == -1;
                        break;
                     case 98:
                        var11 = this.v2[var7] != 12;
                  }
               }

               if (var11) {
                  com.ea.sims3.GameCanvas.c2(var2, var5[var10]);
                  com.ea.sims3.GameCanvas.c2(var3, var10);
               }
            }
         }
      }
   }

   private void b(Entity var1, short[] var2, short[] var3) {
      Sim var4 = this.a.a6().a14;
      short[] var5 = this.B;
      int[] var6 = this.j5;
      int var7 = var1.b();
      com.ea.sims3.GameCanvas.b2(var2, var7);
      com.ea.sims3.GameCanvas.b2(var3, var7);
      int var8 = var1.type;
      Lot var9 = this.a.a24;
      short[] var10 = this.a.a24.a16(var8);
      int var11 = var9.k(var8);
      int var12 = var10.length;
      if ((var11 & 67108864) != 0 && var1.hasState(256)) {
         com.ea.sims3.GameCanvas.c2(var2, var5[110]);
         com.ea.sims3.GameCanvas.c2(var3, 110);
      }

      if ((var11 & 134217728) != 0 && var1.hasState(512)) {
         com.ea.sims3.GameCanvas.c2(var2, var5[109]);
         com.ea.sims3.GameCanvas.c2(var3, 109);
      }

      for (int var13 = 0; var13 < var12; var13++) {
         short var14 = var10[var13];
         int var15;
         if (((var15 = var6[var14]) & 1) == 0
            && ((var15 & 32) == 0 || var1.hasState(64))
            && ((var15 & 64) == 0 || !var1.hasState(64))
            && ((var15 & 128) == 0 || var1.c3())
            && ((var15 & 256) == 0 || var1.d3())
            && ((var15 & 512) == 0 || !var1.hasState(256))
            && ((var15 & 1024) == 0 || !var1.hasState(512))
            && ((var15 & 4096) == 0 || this.b(var1, 0))
            && ((var15 & 8192) == 0 || this.a(var1, 0))
            && ((var15 & 2048) == 0 || !this.b(var1, 0))
            && ((var15 & 131072) == 0 || var9.a() == 0)
            && ((var15 & 65536) == 0 || var9.a() != 0)
            && (var14 != 142 || var8 != 114 || this.a.j2)) {
            if ((var15 & 1056964608) != 0) {
               int var16 = this.u();
               if ((var15 & var16) == 0) {
                  continue;
               }
            }

            if ((var14 != 130 || !var1.hasState(16384))
               && (var14 != 131 || !var1.hasState(32768))
               && (var14 != 150 || this.b3(0, 1) || var9.a() == 0)
               && (var14 != 153 || !this.b3(0, 1) && var9.a() != 0)) {
               if (var14 == 152) {
                  int var18 = com.ea.sims3.Lot.f(var4.posX);
                  int var17 = com.ea.sims3.Lot.g(var4.posY);
                  if ((var9.g(var18, var17) & 16) != 0) {
                     continue;
                  }
               }

               com.ea.sims3.GameCanvas.c2(var2, var5[var14]);
               com.ea.sims3.GameCanvas.c2(var3, var14);
            }
         }
      }

      if ((var11 & 4096) != 0) {
         com.ea.sims3.GameCanvas.c2(var2, var5[106]);
         com.ea.sims3.GameCanvas.c2(var3, 106);
      }

      if (var2[0] == 0) {
         com.ea.sims3.GameCanvas.c2(var2, 274);
         com.ea.sims3.GameCanvas.c2(var3, -4);
      }
   }

   public final void a(short[] var1, short[] var2, short[] var3) {
      com.ea.sims3.GameCanvas.b2(var1, var3[1]);
      com.ea.sims3.GameCanvas.b2(var2, 24);
      short[] var4 = this.B;
      short[] var5 = this.C;
      short var6 = var3[0];

      for (int var7 = 0; var7 < var6; var7++) {
         short var8 = var3[5 + var7];
         short var9;
         if ((var9 = var5[var8]) == 24 || this.a(var3, var9) < 2) {
            com.ea.sims3.GameCanvas.c2(var1, var4[var8]);
            com.ea.sims3.GameCanvas.c2(var2, var8);
         } else if (!com.ea.sims3.GameCanvas.a3(var1, var9)) {
            com.ea.sims3.GameCanvas.c2(var1, var9);
            com.ea.sims3.GameCanvas.c2(var2, -2);
         }
      }
   }

   private int a(short[] var1, int var2) {
      short[] var3 = this.C;
      int var4 = 0;
      short var5 = var1[0];

      for (int var6 = 0; var6 < var5; var6++) {
         short var7 = var1[var6 + 5];
         short var8;
         if ((var8 = var3[var7]) == var2) {
            var4++;
         }
      }

      return var4;
   }

   public final void a(short[] var1, short[] var2, short[] var3, int var4) {
      com.ea.sims3.GameCanvas.b2(var1, var4);
      com.ea.sims3.GameCanvas.b2(var2, var4);
      short[] var5 = this.B;
      short[] var6 = this.C;
      short var7 = var3[0];

      for (int var8 = 0; var8 < var7; var8++) {
         short var9 = var3[5 + var8];
         short var10;
         if ((var10 = var6[var9]) == var4) {
            com.ea.sims3.GameCanvas.c2(var1, var5[var9]);
            com.ea.sims3.GameCanvas.c2(var2, var9);
         }
      }
   }

   public final void c(short[] var1, short[] var2, int var3) {
      short var4 = this.B[var3];
      com.ea.sims3.GameCanvas.b2(var1, var4);
      com.ea.sims3.GameCanvas.b2(var2, var4);
      int var5 = this.a.a24.a();

      for (int var6 = 1; var6 < this.b6; var6++) {
         if (this.v2[var6] != 0 && this.U(var6) != var5) {
            com.ea.sims3.GameCanvas.c2(var1, this.y[var6]);
            com.ea.sims3.GameCanvas.c2(var2, var3);
         }
      }
   }

   private int ag(int var1) {
      int[] var2;
      int var3 = (var2 = this.f8[var1]).length;
      int var4 = 3;
      int var5 = 0;

      for (int var6 = 0; var6 < var3; var6++) {
         if (var2[var6] > var5) {
            var5 = var2[var6];
            var4 = var6;
         }
      }

      return var4;
   }

   private static int ah(int var0) {
      switch (var0) {
         case 0:
         default:
            return 2;
         case 1:
            return 4;
         case 2:
            return 32;
         case 3:
            return 1024;
         case 4:
            return 2048;
         case 5:
            return 8;
         case 6:
            return 64;
         case 7:
            return 128;
         case 8:
            return 16;
         case 9:
            return 256;
         case 10:
            return 512;
         case 11:
            return 4096;
         case 12:
            return 8192;
         case 13:
            return 16384;
         case 14:
            return 32768;
      }
   }

   private boolean a3(int var1, int var2, int var3, int var4) {
      short[] var5 = this.l6[var1];
      int var6 = 0;

      for (int var7 = 0; var7 < var4; var7++) {
         if (var5[var7] == var2) {
            if (++var6 >= var3) {
               return true;
            }
         }
      }

      return false;
   }

   private int o(int var1, int var2) {
      int var3 = this.ag(var1);
      byte var4;
      int var5 = ah(var4 = this.v2[var1]);
      byte[] var6 = this.k4[var2];
      byte[] var7 = this.l3[var2];
      int[] var8 = this.d5[var2];
      short[] var9 = this.i5[var2];

      for (int var10 = var6.length - 1; var10 >= 0; var10--) {
         byte var11;
         byte var12;
         int var13;
         if (((var11 = var6[var10]) == -1 || var11 == var3)
            && ((var12 = var7[var10]) == -1 || this.j(var1, var12) != -1)
            && ((var13 = var8[var10]) == 0 || (var13 & var5) != 0)) {
            short var14;
            if (((var14 = var9[var10]) & 7) != 0) {
               byte var15 = 3;
               byte var16 = 6;
               if ((var14 & 1) != 0) {
                  var15 = 2;
                  var16 = 4;
               } else if ((var14 & 4) != 0) {
                  var15 = 6;
                  var16 = 10;
               }

               if (!this.a3(var1, var2, var15, var16)) {
                  continue;
               }
            }

            if ((var14 & 496) != 0) {
               byte var19;
               if ((var14 & 16) != 0) {
                  var19 = 0;
               } else if ((var14 & 32) != 0) {
                  var19 = 1;
               } else if ((var14 & 64) != 0) {
                  var19 = 2;
               } else if ((var14 & 128) != 0) {
                  var19 = 3;
               } else {
                  var19 = 4;
               }

               int var17 = this.k6[var19];
               int var18 = (var14 & 512) != 0 ? 196608 : 65536;
               if (var17 < var18) {
                  continue;
               }
            }

            byte var20;
            byte var21;
            if (((var14 & 3072) == 0 || ((var14 & 2048) != 0 ? this.g3(4) : this.g3(1)))
               && (
                  var2 != 99
                     || (var14 & 8) == 0
                     || (var14 & 4096) != 0
                     || (var20 = this.j3[0]) != -1 && (var21 = this.k3[0]) < this.c2[var20].length - 1 && this.a5(var20, var21 + 1)
               )) {
               return var10;
            }
         }
      }

      return -1;
   }

   public final boolean a5(int var1, int var2, int var3) {
      if (var1 == 0) {
         int var4 = this.o(var2, var3);
         return (this.i5[var3][var4] & 4096) == 0;
      } else {
         return true;
      }
   }

   public final int M(int var1) {
      short var2;
      switch (var1) {
         case 19:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 70:
         case 79:
         case 82:
         case 83:
         case 84:
         case 93:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 113:
         case 114:
         case 115:
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 128:
         case 129:
         case 130:
         case 131:
         case 132:
         case 133:
         case 134:
         case 135:
         default:
            var2 = 16;
            break;
         case 20:
            var2 = 16;
            break;
         case 26:
            var2 = 1;
            break;
         case 27:
            var2 = 1;
            break;
         case 28:
            var2 = 16;
            break;
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
            var2 = 1;
            break;
         case 59:
            var2 = 4;
            break;
         case 60:
            var2 = 64;
            break;
         case 61:
            var2 = 1;
            break;
         case 62:
            var2 = 1;
            break;
         case 63:
            var2 = 1;
            break;
         case 64:
            var2 = 1;
            break;
         case 65:
            var2 = 1;
            break;
         case 66:
            var2 = 1;
            break;
         case 67:
            var2 = 4;
            break;
         case 68:
            var2 = 128;
            break;
         case 69:
            var2 = 512;
            break;
         case 71:
            var2 = 256;
            break;
         case 72:
            var2 = 4;
            break;
         case 73:
            var2 = 128;
            break;
         case 74:
            var2 = 512;
            break;
         case 75:
            var2 = 256;
            break;
         case 76:
            var2 = 32;
            break;
         case 77:
            var2 = 32;
            break;
         case 78:
            var2 = 32;
            break;
         case 80:
            var2 = 1;
            break;
         case 81:
            var2 = 1;
            break;
         case 85:
            var2 = 1;
            break;
         case 86:
            var2 = 1;
            break;
         case 87:
            var2 = 1;
            break;
         case 88:
            var2 = 1;
            break;
         case 89:
            var2 = 1;
            break;
         case 90:
            var2 = 16;
            break;
         case 91:
            var2 = 16;
            break;
         case 92:
            var2 = 16;
            break;
         case 94:
            var2 = 8;
            break;
         case 95:
            var2 = 1;
            break;
         case 96:
            var2 = 1;
            break;
         case 97:
            var2 = 1;
            break;
         case 98:
            var2 = 1;
            break;
         case 99:
            var2 = 1;
            break;
         case 100:
            var2 = 1;
            break;
         case 101:
            var2 = 1;
            break;
         case 102:
            var2 = 1;
            break;
         case 103:
            var2 = 1;
            break;
         case 112:
            var2 = -1;
            break;
         case 136:
            var2 = 1;
            break;
         case 137:
            var2 = 1;
            break;
         case 138:
            var2 = 256;
            break;
         case 139:
            var2 = 256;
            break;
         case 140:
            var2 = 1;
      }

      int var3 = 0;

      for (byte var4 = 0; var4 < S.length; var4 += 2) {
         if ((var2 & S[var4 + 1]) != 0) {
            var3++;
         }
      }

      int var7 = this.a.b(0, var3 - 1);
      var3 = 0;

      for (byte var5 = 0; var5 < S.length; var5 += 2) {
         if ((var2 & S[var5 + 1]) != 0) {
            if (var3 == var7) {
               return S[var5];
            }

            var3++;
         }
      }

      return 196;
   }

   private boolean j3(int var1) {
      byte var2 = this.p3[var1];
      byte var3 = this.q3[var1];
      byte var4 = this.n3[var1];
      byte var5 = this.o3[var1];
      int var6 = this.V(0);
      int var7 = this.W(0);
      if ((var2 == 2 || var3 == 2 || var4 == 2 || var5 == 2) && var7 == 0) {
         return false;
      } else {
         int var8 = this.c2[var6].length - 1;
         return var2 != 1 && var3 != 1 && var4 != 1 && var5 != 1 || var7 != var8;
      }
   }

   public final int r() {
      int var1;
      do {
         var1 = this.a.b(0, this.E.length - 1);
         new StringBuffer().append("trying card: ").append(var1).toString();
      } while (!this.j3(var1));

      new StringBuffer().append("final card: ").append(var1).toString();
      return var1;
   }

   public final int a(int var1, boolean var2) {
      int var3 = this.a.d();
      if (var2) {
         return var3 < this.l4[var1] ? 0 : 1;
      } else {
         return var3 < this.m3[var1] ? 2 : 3;
      }
   }

   public final int N(int var1) {
      return this.E[var1];
   }

   public final int h(int var1, int var2) {
      switch (var2) {
         case 0:
         default:
            return this.F[var1];
         case 1:
            return this.G[var1];
         case 2:
            return this.H[var1];
         case 3:
            return this.I[var1];
      }
   }

   public final void h2(int var1, int var2) {
      byte var3;
      switch (var2) {
         case 0:
         default:
            var3 = this.n3[var1];
            break;
         case 1:
            var3 = this.o3[var1];
            break;
         case 2:
            var3 = this.p3[var1];
            break;
         case 3:
            var3 = this.q3[var1];
      }

      byte var4 = this.j3[0];
      byte var5 = this.k3[0];
      switch (var3) {
         case 0:
            return;
         case 1:
            this.e3(54);
            this.c2(var4, var5 + 1);
            return;
         case 2:
            this.c2(var4, var5 - 1);
            return;
         case 3:
            this.c2(-1, 0);
            return;
         case 4:
            byte var6 = -1;
            switch (var4) {
               case 0:
                  var6 = 21;
                  break;
               case 1:
                  var6 = 10;
                  break;
               case 2:
                  var6 = 22;
                  break;
               case 3:
                  var6 = 32;
                  break;
               case 4:
                  var6 = 17;
            }

            this.b2(var6, 1);
            return;
         case 5:
            this.a12(1000);
      }
   }

   private void u2() {
      if (this.w2 == null) {
         this.w2 = new byte[this.J.length];
         this.t4 = new int[this.J.length];
      }

      com.ea.sims3.GameCanvas.a3(this.w2, -1);
      com.ea.sims3.GameCanvas.a(this.t4, -1);
   }

   public final int O(int var1) {
      return this.J[var1];
   }

   public final int P(int var1) {
      return this.K[var1];
   }

   public final int Q(int var1) {
      return this.r2[var1];
   }

   private int ai(int var1) {
      return com.ea.sims3.GameCanvas.a(var1, this.L);
   }

   private void a2(int var1, int var2, boolean var3) {
      int var4 = this.d6;
      byte[] var5 = this.i4[var1];

      for (int var6 = 0; var6 < var5.length; var6++) {
         byte var7 = var5[var6];
         int var8 = this.t4[var7];
         boolean var9 = false;
         if (this.w2[var7] == var1) {
            if (this.L[var7] == var2) {
               this.a.a6().j2(var7, var1);
               return;
            }

            if (var4 - var8 < 1440) {
               continue;
            }

            var9 = true;
         } else if (this.w2[var7] != -1) {
            continue;
         }

         if (var3
            && com.ea.sims3.GameCanvas.a(var2, this.k5[var7]) != -1
            && (var9 || com.ea.sims3.GameCanvas.a(var1, this.w2) == -1 && (var8 == -1 || var4 - var8 >= 1440) && this.a.d() <= 30)) {
            this.t4[var7] = this.d6;
            this.o2(var7, var1);
            return;
         }
      }
   }

   private void o2(int var1, int var2) {
      this.w2[var1] = (byte)var2;
      this.a.a6().i2(var1, var2);
      switch (var1) {
         case 0:
            this.n2(1, 4096);
            return;
         case 1:
            this.n2(1, 2048);
            return;
         case 4:
            this.n2(2, 8192);
            return;
         case 9:
            this.n2(13, 4096);
            return;
         case 16:
            this.n2(4, 4096);
            return;
         case 17:
            this.n2(4, 16384);
            return;
         case 18:
            this.n2(4, 2048);
            return;
         case 19:
            this.n2(2, 4096);
            return;
         case 20:
            this.n2(2, 16384);
            return;
         case 21:
            this.n2(2, 2048);
            return;
         case 29:
            this.n2(7, 4096);
         case 2:
         case 3:
         case 5:
         case 6:
         case 7:
         case 8:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
      }
   }

   public final void k2(int var1) {
      short var2;
      if ((var2 = this.N[var1]) != 0) {
         this.a12(var2);
      }

      short var3;
      if ((var3 = this.M[var1]) != -1) {
         this.b2(var3, -1);
      }

      byte var4 = -1;
      if ((this.r2[var1] & 1) != 0) {
         var4 = -2;
      }

      this.w2[var1] = var4;
      this.t4[var1] = this.d6;
      if (var1 == 22) {
         this.b2(48, 1);
      }
   }

   private boolean k3(int var1) {
      short[] var2 = this.Q;
      switch (var1) {
         case 0:
            if ((var2[1] & 4096) != 0) {
               return true;
            }

            return false;
         case 1:
            if ((var2[1] & 2048) != 0) {
               return true;
            }

            return false;
         case 2:
         case 3:
         case 5:
         case 6:
         case 7:
         case 8:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         default:
            if (this.c(this.M[var1]) > 0) {
               return true;
            }

            return false;
         case 4:
            if ((var2[2] & 8192) != 0) {
               return true;
            }

            return false;
         case 9:
            if ((var2[13] & 4096) != 0) {
               return true;
            }

            return false;
         case 16:
            if ((var2[4] & 4096) != 0) {
               return true;
            }

            return false;
         case 17:
            if ((var2[4] & 16384) != 0) {
               return true;
            }

            return false;
         case 18:
            if ((var2[4] & 2048) != 0) {
               return true;
            }

            return false;
         case 19:
            if ((var2[2] & 4096) != 0) {
               return true;
            }

            return false;
         case 20:
            if ((var2[2] & 16384) != 0) {
               return true;
            }

            return false;
         case 21:
            if ((var2[2] & 2048) != 0) {
               return true;
            }

            return false;
         case 29:
            return (var2[7] & 4096) != 0;
      }
   }

   private void v2() {
      if (this.h5[0].length != 5) {
         this.h5[0] = new byte[5];
         com.ea.sims3.GameCanvas.a3(this.h5[0], -1);
      }
   }

   public final int R(int var1) {
      return this.y[var1];
   }

   public final int S(int var1) {
      return this.i3[var1];
   }

   public final int T(int var1) {
      return this.g4[var1];
   }

   public final int i(int var1, int var2) {
      return this.g5[var1][var2];
   }

   public final int U(int var1) {
      return this.b3(var1, 2) ? 0 : this.z[var1];
   }

   public final int V(int var1) {
      return var1 == 10 && this.a.j2 ? 4 : this.j3[var1];
   }

   public final int W(int var1) {
      return var1 == 10 && this.a.j2 ? this.c2[4].length - 1 : this.k3[var1];
   }

   public final byte[] a14(int var1) {
      return this.j4[var1];
   }

   public final void i2(int var1, int var2) {
      this.g5[0][var1] = (byte)var2;
   }

   public final boolean c3(int var1, int var2) {
      return (this.h4[var1] & var2) != 0;
   }

   public final int j(int var1, int var2) {
      return com.ea.sims3.GameCanvas.a(var2, this.h5[var1]);
   }

   public final boolean d3(int var1, int var2) {
      int var3;
      return (var3 = this.j(var1, var2)) != -1 ? this.r5[var1][var3] >= 3 : false;
   }

   public final boolean e3(int var1, int var2) {
      byte[] var3 = this.r5[var1];
      int var4 = this.j(var1, var2);
      if (var3[var4] < 3) {
         var3[var4]++;
         if (var3[var4] == 3) {
            int var5 = this.R(var1);
            short var6 = this.t[var2];
            this.a.a2(-12, 412, var5, var6);
            this.a.a6().d2(-12, -1);
            return true;
         }
      }

      return false;
   }

   public final int X(int var1) {
      return this.h5[var1].length;
   }

   public final int k(int var1, int var2) {
      return this.h5[var1][var2];
   }

   public final int s() {
      byte[] var1;
      return (var1 = this.h5[0]).length - com.ea.sims3.GameCanvas.b(-1, var1);
   }

   public final void l2(int var1) {
      if (this.j(0, var1) == -1) {
         short var2;
         if ((var2 = this.u[var1]) != -1) {
            this.m2(var2);
         }

         int var3;
         if ((var3 = this.j(0, -1)) != -1) {
            this.h5[0][var3] = (byte)var1;
         }
      }
   }

   public final void m2(int var1) {
      int var2;
      if ((var2 = this.j(0, var1)) != -1) {
         this.h5[0][var2] = -1;
      }
   }

   public final int Y(int var1) {
      return com.ea.sims3.GameCanvas.a(var1, this.y, 1);
   }

   public final boolean e4(int var1) {
      byte var2;
      return (var2 = this.v2[var1]) != 2 && var2 != 3 && var2 != 4 && (this.o(var1) & 16) == 0 && (this.o(var1) & 8) != 0;
   }

   public final void n2(int var1) {
      this.p2(var1);
      this.q2(var1);
      if (!this.k3()) {
         this.t2(var1);
         this.u2(var1);
      }

      if (!this.k3()) {
         this.x2(var1);
      }

      if (!this.k3()) {
         this.s2(var1);
      }

      if (this.k3()) {
         this.w3 = Math.max(this.w3 - var1, 0);
      }
   }

   public final void e2() {
      this.a9 = false;
      this.j2(-1);
      com.ea.sims3.GameCanvas.a(this.q5, 0);

      for (int var1 = 1; var1 < this.b6; var1++) {
         this.n2(var1, 1);
         com.ea.sims3.GameCanvas.a(this.f8[var1], 0);
         com.ea.sims3.GameCanvas.a2(this.l6[var1], -1);
      }

      this.n2(0, 1);
      this.f2();
   }

   public final void f2() {
      this.w3 = 2500;
   }

   public final boolean k3() {
      return this.w3 != 0;
   }

   public final void g2() {
      int var1 = this.m();
      int var2 = this.t();

      for (int var3 = 0; var3 < var1; var3++) {
         if ((this.ae(var3) & var2) != 0) {
            this.a.o2(var3);
         }
      }

      this.i2(99);
   }

   private void w() {
      if (this.T == null) {
         this.T = new short[100];
         this.u4 = new int[100];
         this.v4 = new int[100];
      }

      com.ea.sims3.GameCanvas.a2(this.T, -1);
      com.ea.sims3.GameCanvas.a(this.u4, 0);
      com.ea.sims3.GameCanvas.a(this.v4, 0);
   }

   public final void b2(int var1, int var2, int var3) {
      int var4;
      int var5 = com.ea.sims3.GameCanvas.a(var4 = var1 << 8 | var2, this.T);
      if (var3 == 0 && var5 != -1) {
         this.j2(var1, var2);
      } else {
         if (var5 == -1) {
            var5 = com.ea.sims3.GameCanvas.a(-1, this.T);
         }

         this.T[var5] = (short)var4;
         this.u4[var5] = var3;
         this.v4[var5] = this.d6;
      }
   }

   public final int l(int var1, int var2) {
      int var4;
      return (var4 = com.ea.sims3.GameCanvas.a(var1 << 8 | var2, this.T)) == -1 ? 0 : this.u4[var4];
   }

   public final int m(int var1, int var2) {
      int var4;
      return (var4 = com.ea.sims3.GameCanvas.a(var1 << 8 | var2, this.T)) == -1 ? -1 : this.v4[var4];
   }

   public final void j2(int var1, int var2) {
      int var4;
      if ((var4 = com.ea.sims3.GameCanvas.a(var1 << 8 | var2, this.T)) != -1) {
         this.T[var4] = -1;
         this.u4[var4] = 0;
         this.v4[var4] = 0;
      }
   }

   public final void h2() {
      this.j2();
      this.k2();
      this.l2();
      this.m2();
      this.a11 = -1;
      com.ea.sims3.GameCanvas.a3(this.h5[0], -1);
      this.n2();
      this.o2();
      this.p2();
      this.r2();
      this.u2();
      this.v2();
      this.t2();
      this.w();
      com.ea.sims3.GameCanvas.a3(this.g5[0], 0);
   }

   public final void a2(DataInputStream var1) throws IOException {
      for (int var2 = 0; var2 < 10; var2++) {
         this.g5[0][var2] = var1.readByte();
      }

      byte[] var8 = this.h5[0];

      for (int var3 = 0; var3 < 5; var3++) {
         var8[var3] = var1.readByte();
      }

      this.m4 = var1.readInt();
      this.d6 = var1.readInt();
      this.e5 = var1.readInt();
      this.f6 = var1.readInt();
      this.c8 = var1.readByte();
      this.a10 = var1.readShort();

      for (int var9 = 0; var9 < this.s2.length; var9++) {
         this.s2[var9] = var1.readByte();
      }

      for (int var10 = 0; var10 < this.k6.length; var10++) {
         this.k6[var10] = var1.readInt();
      }

      this.j3[0] = var1.readByte();
      this.k3[0] = var1.readByte();
      this.d8 = var1.readByte();
      this.b7 = var1.readShort();
      this.c9 = var1.readShort();

      for (int var11 = 0; var11 < this.t2.length; var11++) {
         this.t2[var11] = var1.readByte();
      }

      this.e7 = var1.readByte();
      this.n4 = var1.readInt();
      this.a11 = var1.readByte();
      this.b8 = var1.readByte();
      this.d9 = var1.readShort();
      this.o4 = var1.readInt();

      for (int var12 = 0; var12 < this.u2.length; var12++) {
         this.u2[var12] = var1.readByte();
      }

      this.p4 = var1.readInt();
      this.q4 = var1.readInt();

      for (int var13 = 0; var13 < this.l5.length; var13++) {
         this.l5[var13] = var1.readInt();
      }

      this.e8 = var1.readShort();
      this.f7 = var1.readShort();

      for (int var14 = 0; var14 < this.m5.length; var14++) {
         this.m5[var14] = var1.readInt();
      }

      this.r3 = var1.readInt();

      for (int var15 = 0; var15 < this.n5.length; var15++) {
         this.n5[var15] = var1.readInt();
      }

      for (int var16 = 0; var16 < 6; var16++) {
         this.O[var16] = var1.readShort();
         this.r4[var16] = var1.readInt();
      }

      this.q2();
      int var17 = this.b6;

      for (int var4 = 1; var4 < var17; var4++) {
         this.v2[var4] = var1.readByte();
         this.Q[var4] = var1.readShort();
         int[] var5 = this.e9[var4];

         for (int var6 = 0; var6 < var5.length; var6++) {
            var5[var6] = var1.readInt();
         }

         byte[] var20 = this.r5[var4];

         for (int var7 = 0; var7 < var20.length; var7++) {
            var20[var7] = var1.readByte();
         }
      }

      for (int var18 = 0; var18 < 100; var18++) {
         this.T[var18] = var1.readShort();
         this.u4[var18] = var1.readInt();
         this.v4[var18] = var1.readInt();
      }

      for (int var19 = 0; var19 < this.w2.length; var19++) {
         this.w2[var19] = var1.readByte();
         this.t4[var19] = var1.readInt();
      }
   }

   public final void a(DataOutputStream var1) throws IOException {
      for (int var2 = 0; var2 < 10; var2++) {
         var1.writeByte(this.g5[0][var2]);
      }

      byte[] var8 = this.h5[0];

      for (int var3 = 0; var3 < 5; var3++) {
         var1.writeByte(var8[var3]);
      }

      var1.writeInt(this.m4);
      var1.writeInt(this.d6);
      var1.writeInt(this.e5);
      var1.writeInt(this.f6);
      var1.writeByte(this.c8);
      var1.writeShort(this.a10);

      for (int var9 = 0; var9 < this.s2.length; var9++) {
         var1.writeByte(this.s2[var9]);
      }

      for (int var10 = 0; var10 < this.k6.length; var10++) {
         var1.writeInt(this.k6[var10]);
      }

      var1.writeByte(this.j3[0]);
      var1.writeByte(this.k3[0]);
      var1.writeByte(this.d8);
      var1.writeShort(this.b7);
      var1.writeShort(this.c9);

      for (int var11 = 0; var11 < this.t2.length; var11++) {
         var1.writeByte(this.t2[var11]);
      }

      var1.writeByte(this.e7);
      var1.writeInt(this.n4);
      var1.writeByte(this.a11);
      var1.writeByte(this.b8);
      var1.writeShort(this.d9);
      var1.writeInt(this.o4);

      for (int var12 = 0; var12 < this.u2.length; var12++) {
         var1.writeByte(this.u2[var12]);
      }

      var1.writeInt(this.p4);
      var1.writeInt(this.q4);

      for (int var13 = 0; var13 < this.l5.length; var13++) {
         var1.writeInt(this.l5[var13]);
      }

      var1.writeShort(this.e8);
      var1.writeShort(this.f7);

      for (int var14 = 0; var14 < this.m5.length; var14++) {
         var1.writeInt(this.m5[var14]);
      }

      var1.writeInt(this.r3);

      for (int var15 = 0; var15 < this.n5.length; var15++) {
         var1.writeInt(this.n5[var15]);
      }

      for (int var16 = 0; var16 < 6; var16++) {
         var1.writeShort(this.O[var16]);
         var1.writeInt(this.r4[var16]);
      }

      int var17 = this.b6;

      for (int var4 = 1; var4 < var17; var4++) {
         var1.writeByte(this.v2[var4]);
         var1.writeShort(this.Q[var4]);
         int[] var5 = this.e9[var4];

         for (int var6 = 0; var6 < var5.length; var6++) {
            var1.writeInt(var5[var6]);
         }

         byte[] var20 = this.r5[var4];

         for (int var7 = 0; var7 < var20.length; var7++) {
            var1.writeByte(var20[var7]);
         }
      }

      for (int var18 = 0; var18 < 100; var18++) {
         var1.writeShort(this.T[var18]);
         var1.writeInt(this.u4[var18]);
         var1.writeInt(this.v4[var18]);
      }

      for (int var19 = 0; var19 < this.w2.length; var19++) {
         var1.writeByte(this.w2[var19]);
         var1.writeInt(this.t4[var19]);
      }
   }

   public final void o2(int var1) {
      int var2 = this.i6;
      int[] var3 = this.k6;
      switch (var1) {
         case 0:
            this.p2(60000);
            return;
         case 1:
            this.p2(1440000);
            return;
         case 2:
            int var8 = 3276800;

            for (int var5 = 0; var5 < this.n5.length; var5++) {
               this.n5[var5] = var8;
            }

            return;
         case 3:
            this.m2(var2, 5);
            return;
         case 4:
            this.m2(var2, 2);
            return;
         case 5:
            this.m2(var2, 8);
            return;
         case 6:
            this.m2(var2, 6);
            return;
         case 7:
            this.m2(var2, 3);
            return;
         case 8:
            this.m2(var2, 9);
            return;
         case 9:
            this.m2(var2, 7);
            return;
         case 10:
            this.m2(var2, 4);
            return;
         case 11:
            this.m2(var2, 10);
            return;
         case 12:
            for (int var7 = 1; var7 < this.b6; var7++) {
               if (this.v2[var7] == 0) {
                  this.m2(var7, 5);
               }
            }

            return;
         case 13:
            var3[0] = Math.max(var3[0] - 65536, 0);
            var3[0] = 327675;
            return;
         case 14:
            var3[0] = Math.min(var3[0] + 65536, 327680);
            return;
         case 15:
            var3[1] = Math.max(var3[1] - 65536, 0);
            var3[1] = 327675;
            return;
         case 16:
            var3[1] = Math.min(var3[1] + 65536, 327680);
            return;
         case 17:
            var3[2] = Math.max(var3[2] - 65536, 0);
            var3[2] = 327675;
            return;
         case 18:
            var3[2] = Math.min(var3[2] + 65536, 327680);
            return;
         case 19:
            var3[3] = Math.max(var3[3] - 65536, 0);
            var3[3] = 327675;
            return;
         case 20:
            var3[3] = Math.min(var3[3] + 65536, 327680);
            return;
         case 21:
            var3[4] = Math.max(var3[4] - 65536, 0);
            var3[4] = 327675;
            return;
         case 22:
            var3[4] = Math.min(var3[4] + 65536, 327680);
            return;
         case 23:
            this.b8 = (byte)this.v();
            this.o4 = 0;
            return;
         case 24:
            for (int var6 = 0; var6 < this.m.length; var6++) {
               this.a.o2(var6);
               this.a.p2(var6);
            }

            this.a.t2();
            return;
         case 25:
            for (int var4 = 0; var4 < this.s2.length; var4++) {
               this.s2[var4] = (byte)this.a.a24.z(var4);
            }
      }
   }
}
