package com.ea.sims3;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

public abstract class w {
   private static final byte[] a = new byte[5];
   private static boolean b = false;
   private short a2 = 0;
   private int a3 = 0;
   private int b2 = -1;
   private int c = 0;
   private boolean c2 = true;
   public b a4 = null;
   public b b3 = null;
   private MIDlet a5 = null;
   private Object[] a6 = null;
   public boolean a7 = false;
   private Image a8 = null;
   private int d = 0;
   private int e = 0;
   private String a9 = null;
   private int f = 0;
   private boolean d2 = true;
   private boolean e2 = true;
   private String[] a10 = null;
   private short[] a11 = null;
   private short[] b4 = null;
   private short[] c3 = null;
   private int[] a12 = null;
   private String[] b5 = null;
   private String[] c4 = null;
   private int g = 0;
   private int h = 0;
   private boolean f2 = false;
   private short[] d3 = null;
   private short[] e3 = null;
   private short[] f3 = null;
   private b c5 = null;
   private b d4 = null;
   private b e4 = null;
   private int i = 0;
   private int j = 0;
   private int k = 6;
   private int l = 0;
   private int m = 0;
   private int n = 0;
   private int o = 0;
   private int p = 0;
   private int q = 0;
   private short[] g2 = new short[11];
   private b[] a13 = new b[11];
   private int r = -1;
   private int s = -1;

   public w(int var1, int var2) {
      if (com.ea.sims3.f.a11() && (var1 <= 0 || var2 <= 0)) {
         com.ea.sims3.k.a("SDKMoreGamesSDKMoreGames::constructor was called with zero or negative screen size. Aborting...", 4);
      }

      this.s = var1;
      this.r = var2;
      if (com.ea.sims3.f.e3()) {
         for (int var3 = 0; var3 < a.length; var3++) {
            a[var3] = -1;
         }
      }
   }

   public abstract Image a3(String var1);

   public final void a4(int var1, int var2) {
      if (com.ea.sims3.f.a11() && (var1 <= 0 || var2 <= 0)) {
         com.ea.sims3.k.a("SDKMoreGamessetScreenSize:: was called with zero or negative screen size. Aborting...", 4);
      }

      boolean var3 = var1 != this.s || var2 != this.r;
      this.s = var1;
      this.r = var2;
      if (this.a7 && var3) {
         this.c2();
      }
   }

   public final void a10() {
      if (com.ea.sims3.f.a11()) {
         if (this.a5 == null) {
            com.ea.sims3.k.a("SDKMoreGamesreset:: was called before the midlet instance was set. Aborting...", 4);
         }

         if (this.a11 == null || this.b4 == null || this.c3 == null) {
            com.ea.sims3.k.a("SDKMoreGamesreset:: was called before seting the product data. Aborting...", 4);
         }

         if (!this.a11()) {
            com.ea.sims3.k.a("SDKMoreGamesreset:: was called even though MoreGames is unavailable. Aborting...", 4);
         }

         if (this.a6 == null) {
            com.ea.sims3.k.a("SDKMoreGamesreset:: was called before the fonts were set. Aborting...", 4);
         }
      }

      if (this.a12 == null || com.ea.sims3.f.e3() && b) {
         this.d2();
      }

      for (int var1 = 0; var1 < this.a13.length; var1++) {
         this.a13[var1] = com.ea.sims3.k.a(this.g2[var1], null);
      }

      this.d = 0;
      this.a7 = true;
      this.h = 0;
      this.a12(0);
      com.ea.sims3.k.a(this.a6[0]);
      this.d3 = com.ea.sims3.k.a(this.a13[0], null, this.s - 10, (short)124);
      this.e3 = com.ea.sims3.k.a(this.a13[4], null, this.s - 10, (short)124);
   }

   public final void b4() {
      switch (this.h) {
         case 0:
            if ((this.c & 2052) != 0 && this.a12.length > 0) {
               this.d--;
               if (this.d < 0) {
                  this.d = this.a12.length - 1;
               }

               this.e = this.a12[this.d];
               this.a12(this.d);
            } else if ((this.c & 8200) != 0 && this.a12.length > 0) {
               this.d++;
               this.d = this.d % this.a12.length;
               this.e = this.a12[this.d];
               this.a12(this.d);
            } else if (((this.c & 513) != 0 || (this.c & 32770) != 0) && this.a12.length > 0 && this.d2 && this.e2) {
               if (this.f == 1) {
                  this.f = 0;
               } else {
                  this.f = 1;
               }
            } else if ((this.c & 64) != 0) {
               this.a7 = false;
            } else if ((this.c & 4144) != 0) {
               if (this.f == 0) {
                  this.a9 = this.b5[this.d];
                  if (com.ea.sims3.e.a11()) {
                     this.a4 = this.a13[8];
                     this.b3 = this.a13[7];
                  } else {
                     this.a4 = this.a13[7];
                     this.b3 = this.a13[8];
                  }

                  this.h = 1;
               } else if (this.f == 1) {
                  if (this.g == 1 || this.g == 3 && (this.c4[this.d] == null || this.c4[this.d].compareTo("") == 0)) {
                     if (com.ea.sims3.e.a11()) {
                        this.a4 = this.a13[6];
                        this.b3 = null;
                     } else {
                        this.a4 = null;
                        this.b3 = this.a13[6];
                     }

                     this.h = 2;
                  } else {
                     if (com.ea.sims3.e.a11()) {
                        this.a4 = this.a13[8];
                        this.b3 = this.a13[7];
                     } else {
                        this.a4 = this.a13[7];
                        this.b3 = this.a13[8];
                     }

                     this.a9 = this.c4[this.d];
                     this.h = 1;
                  }
               }
            }
            break;
         case 1:
            if ((this.c & 64) != 0) {
               if (com.ea.sims3.e.a11()) {
                  this.a4 = this.a13[6];
                  this.b3 = this.a13[5];
               } else {
                  this.a4 = this.a13[5];
                  this.b3 = this.a13[6];
               }

               this.h = 0;
            } else if ((this.c & 32) != 0) {
               this.a4(this.a9);
            }
            break;
         case 2:
            if ((this.c & 64) != 0) {
               if (com.ea.sims3.e.a11()) {
                  this.a4 = this.a13[6];
                  this.b3 = this.a13[5];
               } else {
                  this.a4 = this.a13[5];
                  this.b3 = this.a13[6];
               }

               this.h = 0;
            }
      }

      this.c = 0;
   }

   public final void a(Graphics var1) {
      this.b(var1);
      com.ea.sims3.k.a(var1);
      switch (this.h) {
         case 0:
            this.k--;
            if (this.k < 0) {
               this.k = 6;
            }

            if (this.f2) {
               var1.setColor(19855);
               com.ea.sims3.k.a(this.a6[4]);
               var1.fillRect(0, 0, this.s, this.i);
               com.ea.sims3.k.a(this.a13[9], this.s >> 1, 1, com.ea.sims3.k.d | com.ea.sims3.k.a);
            }

            if (this.a8 != null) {
               var1.drawImage(this.a8, this.s >> 1, this.l, com.ea.sims3.k.d | com.ea.sims3.k.a);
               if (this.a12.length > 1) {
                  this.a(var1, (this.s - this.a8.getWidth() >> 2) - 4, this.m);
                  this.b(var1, this.s - (this.s - this.a8.getWidth() >> 2) + 4, this.m);
               }

               com.ea.sims3.k.a(this.a6[0]);
               com.ea.sims3.k.a(this.c5, this.f3, 1, this.f3[0], this.s >> 1, this.n, com.ea.sims3.k.d | com.ea.sims3.k.a);
            } else {
               com.ea.sims3.k.a(this.a6[0]);
               com.ea.sims3.k.a(this.d4, this.s >> 1, this.l, com.ea.sims3.k.d | com.ea.sims3.k.a);
               com.ea.sims3.k.a(this.c5, this.f3, 1, this.f3[0], this.s >> 1, this.n, com.ea.sims3.k.d | com.ea.sims3.k.a);
               if (this.a12.length > 1) {
                  this.a(var1, 1, this.m);
                  this.b(var1, this.s - 1, this.m);
               }
            }

            if (this.d2) {
               this.a(var1, this.o, this.j, this.a13[10], this.f == 0);
            }

            if (this.e2) {
               this.a(var1, this.p, this.j, this.e4, this.f == 1);
            }
            break;
         case 1:
            com.ea.sims3.k.a(this.a6[0]);
            com.ea.sims3.k.a(this.a13[4], this.e3, 1, this.e3[0], this.s >> 1, this.r - this.q >> 1, com.ea.sims3.k.d | com.ea.sims3.k.e);
            break;
         case 2:
            com.ea.sims3.k.a(this.a6[0]);
            com.ea.sims3.k.a(this.a13[0], this.d3, 1, this.d3[0], this.s >> 1, this.r - this.q >> 1, com.ea.sims3.k.d | com.ea.sims3.k.e);
      }

      if (this.c2) {
         com.ea.sims3.k.a(this.a6[3]);
         if (this.a4 != null) {
            com.ea.sims3.k.a(this.a4, 2, this.r + com.ea.sims3.k.b() - 2, com.ea.sims3.k.f | com.ea.sims3.k.b);
         }

         if (this.b3 != null) {
            com.ea.sims3.k.a(this.b3, this.s - 2, this.r + com.ea.sims3.k.b() - 2, com.ea.sims3.k.g | com.ea.sims3.k.b);
         }
      }
   }

   public final void a(Object[] var1) {
      if (com.ea.sims3.f.a11()) {
         if (var1 != null) {
            for (int var2 = 0; var2 < var1.length; var2++) {
               if (var1[var2] == null) {
                  com.ea.sims3.k.a("SDKMoreGamessetFonts:: invalid parameter. The fonts must NOT be null.", 4);
               } else if ((!(var1[var2] instanceof Object[]) || !com.ea.sims3.f.b5() && !com.ea.sims3.f.d3())
                  && (!(var1[var2] instanceof Font) || !com.ea.sims3.f.c3())) {
                  com.ea.sims3.k.a(
                     "SDKMoreGamessetFonts:: invalid parameter. One of the objects in the fonts array (index " + var2 + ") does not appear to be a font", 3
                  );
               }
            }
         } else {
            com.ea.sims3.k.a("SDKMoreGamessetFonts:: invalid parameter. The font array is NULL.", 4);
         }
      }

      this.a6 = var1;
   }

   public final byte a(int var1) {
      if (!com.ea.sims3.f.e3()) {
         if (this.a7) {
            this.c = this.c | this.a3(var1);
         }

         return 0;
      } else {
         if (this.a7) {
            this.c = this.c | this.a3(var1);
         } else {
            byte var3;
            if ((var3 = b(var1)) == -1) {
               this.a2 = 0;
               this.a3 = 0;
            } else if (this.a3 == 0) {
               this.a2 = (short)(this.a2 << 4);
               this.a2 = (short)(this.a2 | var3 + 1);
               if (this.a2 == -26472) {
                  this.b2 = 3;
                  this.a3 = 1;
               } else {
                  if (this.a2 == -26461) {
                     a[4] = 1;
                     b = true;
                     this.a2 = 0;
                     if (com.ea.sims3.f.a11()) {
                        com.ea.sims3.k.a("SDKMoreGamesprocessKey::Activated cheat type=4", 1);
                     }

                     return 1;
                  }

                  if (this.a2 == -26475) {
                     this.b2 = 0;
                     this.a3 = 1;
                  } else if (this.a2 == -26473) {
                     this.b2 = 1;
                     this.a3 = 1;
                  } else if (this.a2 == -26476) {
                     this.b2 = 2;
                     this.a3 = 1;
                  }
               }
            } else if (this.a3 == 1) {
               boolean var2 = false;
               if (this.b2 == 0) {
                  var2 = var3 < 0 || var3 > 3;
               } else if (this.b2 == 2 || this.b2 == 1) {
                  var2 = var3 < 0 || var3 > 2;
               }

               if (!var2) {
                  a[this.b2] = var3;
                  b = true;
                  if (com.ea.sims3.f.a11()) {
                     com.ea.sims3.k.a("SDKMoreGamesprocessKey::Activated cheat type=" + this.b2 + ", value=" + var3, 1);
                  }
               }

               this.a3 = 0;
               this.a2 = 0;
               return 1;
            }
         }

         return 0;
      }
   }

   public final void a3(byte[] var1) {
      if (com.ea.sims3.f.a11() && var1[0] != 0) {
         com.ea.sims3.k.a("SDKMoreGamessetProductData::Binary format version for the moregames data is incorrect. expected=0, actual=" + var1[0], 4);
      }

      int var2 = 1;
      var2++;
      byte var3 = var1[1];
      this.a10 = new String[var3];
      this.a11 = new short[var3];
      this.b4 = new short[var3];
      this.c3 = new short[var3];

      for (int var4 = 0; var4 < var3; var4++) {
         byte var5 = var1[var2++];
         this.a10[var4] = new String(var1, var2, var5);
         var2 += var5;
      }

      int var10 = var3 << 1;
      int var11 = var3 << 2;

      for (int var6 = 0; var6 < var3; var6++) {
         this.a11[var6] = com.ea.sims3.k.a2(var1, var2);
         this.b4[var6] = com.ea.sims3.k.a2(var1, var2 + var10);
         this.c3[var6] = com.ea.sims3.k.a2(var1, var2 + var11);
         var2 += 2;
      }

      var2 += var11;

      for (int var12 = 0; var12 < this.a13.length; var12++) {
         this.g2[var12] = com.ea.sims3.k.a2(var1, var2);
         var2 += 2;
      }
   }

   public final boolean a11() {
      if (this.a12 == null || com.ea.sims3.f.e3() && b) {
         this.d2();
      }

      return this.g != 0;
   }

   public final void b(Graphics var1) {
      var1.setClip(0, 0, this.s, this.r);
      var1.setColor(16777215);
      var1.fillRect(0, 0, this.s, this.r);
   }

   public final void a4(String var1) {
      if (com.ea.sims3.f.a11() && this.a5 == null) {
         com.ea.sims3.k.a("SDKMoreGamesplatformRequestImpl::called without setting the midlet first", 4);
      }

      try {
         this.a5.platformRequest(var1);
         this.a5.notifyDestroyed();
         this.a5.notifyDestroyed();
      } catch (Exception var2) {
         this.a5.notifyDestroyed();
         if (com.ea.sims3.f.a11()) {
            com.ea.sims3.k.a("SDKMoreGamesplatformRequestImpl::'" + var2.getMessage() + "' received while doing native platformRequest", 4);
         }
      }
   }

   private void c2() {
      this.j = (this.s << 2) / 5;
      int var1 = this.r;
      this.q = 0;
      if (this.c2) {
         com.ea.sims3.k.a(this.a6[3]);
         this.q = com.ea.sims3.k.c() - com.ea.sims3.k.b();
         var1 -= this.q;
      }

      var1 -= 2;
      if (this.e2) {
         var1 -= 5;
         if (this.f == 1) {
            com.ea.sims3.k.a(this.a6[2]);
         } else {
            com.ea.sims3.k.a(this.a6[1]);
         }

         var1 -= com.ea.sims3.k.c() - com.ea.sims3.k.b();
         this.p = var1;
         int var2;
         if ((var2 = com.ea.sims3.k.a(this.e4) + 6 + com.ea.sims3.k.c() - com.ea.sims3.k.b() + 6) > this.j) {
            this.j = var2;
         }
      }

      if (this.d2) {
         var1 -= 5;
         if (this.f == 0) {
            com.ea.sims3.k.a(this.a6[2]);
         } else {
            com.ea.sims3.k.a(this.a6[1]);
         }

         var1 -= com.ea.sims3.k.c() - com.ea.sims3.k.b();
         this.o = var1;
         int var13;
         if ((var13 = com.ea.sims3.k.a(this.a13[10]) + 6 + com.ea.sims3.k.c() - com.ea.sims3.k.b() + 6) > this.j) {
            this.j = var13;
         }
      }

      if (this.e2 && this.d2) {
         var1 -= 2;
         this.o = var1;
      }

      var1 -= 2;
      if (this.a8 != null) {
         com.ea.sims3.k.a(this.a6[0]);
         this.f3 = com.ea.sims3.k.a(this.c5, null, this.s - 2, (short)124);
         int var9 = var1 - (this.f3[0] * com.ea.sims3.k.c() - com.ea.sims3.k.b());
         this.n = var9;
         var1 = var9 - this.a8.getHeight();
         this.l = var1;
         this.m = this.l + (this.a8.getHeight() >> 1);
      } else {
         com.ea.sims3.k.a(this.a6[0]);
         this.f3 = com.ea.sims3.k.a(this.c5, null, this.s - 7, (short)124);
         int var11 = var1 - this.f3[0] * com.ea.sims3.k.c();
         this.n = var11;
         var1 = var11 - com.ea.sims3.k.c();
         this.l = var1;
         this.m = this.l + (com.ea.sims3.k.c() - com.ea.sims3.k.b() >> 1);
      }

      var1--;
      com.ea.sims3.k.a(this.a6[4]);
      this.i = com.ea.sims3.k.c() - com.ea.sims3.k.b() + 1 + 1;
      if (var1 >= this.i) {
         this.f2 = true;
         var1 -= this.i;
      } else {
         this.f2 = false;
      }

      if (var1 > 0) {
         int var14 = var1 * 3 / 10;
         int var3 = var1 >> 2;
         int var4 = var1 >> 2;
         this.l -= var1 - var14;
         this.m -= var1 - var14;
         this.n -= var1 - var14 - var3;
         this.o -= var1 - var14 - var3 - var4;
         this.p -= var1 - var14 - var3 - var4;
      }
   }

   private void d2() {
      this.a5 = com.ea.sims3.k.a14;
      if (com.ea.sims3.f.a11() && this.a5 == null) {
         com.ea.sims3.k.a("SDKMoreGamesinitialize::called before the midlet is set. Aborting...", 4);
      }

      String var1;
      if ((var1 = this.a2("MG_Impl")) == null) {
         this.g = 0;
      } else {
         if (!(var1 = var1.toLowerCase()).equals("static")) {
            if (var1.equals("wapstatic")) {
            }

            if (var1.equals("wap")) {
               this.g = 2;
            } else {
               if (!var1.equals("wapstatic")) {
                  this.g = 0;
                  return;
               }

               this.g = 3;
            }
         } else {
            this.g = 1;
         }

         if ((var1 = this.a2("MG_PRODUCTS")) != null && var1.length() != 0) {
            int var2 = 0;
            int var3 = -1;

            int var4;
            do {
               String var5;
               if ((var4 = var1.indexOf(44, var3 + 1)) == -1) {
                  var5 = var1.substring(var3 + 1);
               } else {
                  var5 = var1.substring(var3 + 1, var4);
               }

               var3 = var4;
               if (var5.equals("LOG")) {
                  var2++;
               } else {
                  boolean var6 = false;

                  for (int var7 = 0; var7 < this.a10.length; var7++) {
                     if (this.a10[var7].equals(var5)) {
                        var6 = true;
                        break;
                     }
                  }

                  if (var6) {
                     var2++;
                  }
               }
            } while (var4 != -1);

            if (var2 == 0) {
               if (com.ea.sims3.f.a11()) {
                  com.ea.sims3.k.a("SDKMoreGamesinitialize::Found no valid advertised items in the JAD (MG_PRODUCTS property). More Games will be disabled.", 3);
               }

               this.g = 0;
            }

            if (com.ea.sims3.f.a11()) {
               com.ea.sims3.k.a("SDKMoreGamesinitialize::Found " + var2 + " advertised items", 1);
            }

            this.a12 = new int[var2];
            this.b5 = new String[var2];
            this.c4 = new String[var2];
            var3 = -1;

            for (int var11 = 0; var11 < this.a12.length; var11++) {
               int var12 = var1.indexOf(44, var3 + 1);
               Object var13 = null;
               if (var12 == -1) {
                  var13 = var1.substring(var3 + 1);
               } else {
                  var13 = var1.substring(var3 + 1, var12);
               }

               var3 = var12;
               if (var13.equals("LOG")) {
                  this.a12[var11] = -1;
                  this.b5[var11] = null;
                  this.c4[var11] = this.a2("MG_CATURI_LOG");
                  if (this.c4[var11] != null) {
                     this.c4[var11] = this.c4[var11].trim();
                  }

                  if (com.ea.sims3.f.a11()) {
                     com.ea.sims3.k.a("SDKMoreGamesinitialize::Found Generic Category. CATURI=" + this.c4[var11], 1);
                  }
               } else {
                  this.a12[var11] = -2;
                  this.b5[var11] = null;
                  this.c4[var11] = null;

                  for (int var15 = 0; var15 < this.a10.length; var15++) {
                     if (this.a10[var15].equals(var13)) {
                        this.a12[var11] = var15;
                        this.b5[var11] = this.a2("MG_BUYURI_" + var13);
                        this.c4[var11] = this.a2("MG_CATURI_" + var13);
                        if (this.b5[var11] != null) {
                           this.b5[var11] = this.b5[var11].trim();
                        }

                        if (this.c4[var11] != null) {
                           this.c4[var11] = this.c4[var11].trim();
                        }

                        if (com.ea.sims3.f.a11()) {
                           com.ea.sims3.k.a("SDKMoreGamesinitialize::Found Game " + var13 + ". CATURI=" + this.c4[var11] + " BUYURI=" + this.b5[var11], 1);
                        }
                        break;
                     }
                  }

                  if (this.a12[var11] == -2) {
                     if (com.ea.sims3.f.a11()) {
                        com.ea.sims3.k.a(
                           "SDKMoreGamesinitialize::Game '" + var13 + "' mentioned in the JAD (MG_PRODUCTS property) does not exist. Ignoring...", 3
                        );
                     }

                     var11--;
                  }
               }
            }
         } else {
            if (com.ea.sims3.f.a11()) {
               com.ea.sims3.k.a("SDKMoreGamesinitialize::MG_PRODUCTS property in the JAD file is not set or could not be read. More Games will be disabled.", 3);
            }

            this.g = 0;
         }
      }
   }

   private String a2(String var1) {
      if (!com.ea.sims3.f.e3()) {
         return this.a5.getAppProperty(var1);
      } else {
         if (b) {
            if (var1.startsWith("MG_CATURI_")) {
               if (a[1] == 2) {
                  return null;
               }
            } else if (var1.startsWith("MG_BUYURI_") && a[2] == 2) {
               return null;
            }

            if (var1.equals("MG_Impl")) {
               switch (a[0]) {
                  case 0:
                     return "off";
                  case 1:
                     return "static";
                  case 2:
                     return "wap";
                  case 3:
                     return "wapstatic";
               }
            }

            if (var1.equals("MG_PRODUCTS")) {
               String var3;
               if ((var3 = this.a5.getAppProperty(var1)) == null) {
                  return null;
               }

               if (a[3] == 0) {
                  return "";
               }

               String var4 = var3.trim();
               if (a[3] != -1) {
                  int var5 = -1;
                  int var2 = 0;

                  while (var2 < a[3] && (var5 = var4.indexOf(44, var5 + 1)) != -1) {
                     var2++;
                  }

                  if (var5 != -1) {
                     var4 = var4.substring(0, var5);
                  }
               }

               if (a[4] != -1) {
                  int var6;
                  if ((var6 = var4.indexOf(44, 0)) == -1) {
                     return var4;
                  }

                  var4 = var4.substring(var6 + 1) + ',' + var4.substring(0, var6);
               }

               return var4;
            }
         }

         return this.a5.getAppProperty(var1);
      }
   }

   private static byte b(int var0) {
      return var0 >= 48 && var0 <= 57 ? (byte)(var0 - 48) : -1;
   }

   private int a3(int var1) {
      if (var1 == com.ea.sims3.e.a()) {
         return 1;
      } else if (var1 == com.ea.sims3.e.b()) {
         return 2;
      } else if (var1 == com.ea.sims3.e.c()) {
         return 4;
      } else if (var1 == com.ea.sims3.e.d()) {
         return 8;
      } else if (var1 == com.ea.sims3.e.e()) {
         return 16;
      } else if (var1 == com.ea.sims3.e.f()) {
         return com.ea.sims3.e.a11() ? 64 : 32;
      } else if (var1 == com.ea.sims3.e.g()) {
         return com.ea.sims3.e.a11() ? 32 : 64;
      } else {
         byte var2;
         if ((var2 = b(var1)) != -1) {
            if (var2 == 5) {
               return 16;
            }

            if (var2 == 2) {
               return 1;
            }

            if (var2 == 8) {
               return 2;
            }

            if (var2 == 4) {
               return 4;
            }

            if (var2 == 6) {
               return 8;
            }
         }

         return 0;
      }
   }

   private void a12(int var1) {
      label71: {
         this.e = this.a12[var1];
         if (this.e != -1) {
            this.a8 = this.a3(this.a10[this.e]);
            this.c5 = com.ea.sims3.k.a(this.c3[this.e], null);
            this.e4 = com.ea.sims3.k.a(this.b4[this.e], null);
            this.d4 = com.ea.sims3.k.a(this.a11[this.e], null);
            switch (this.g) {
               case 1:
                  this.d2 = false;
                  break;
               case 2:
                  this.d2 = this.b5[var1] != null && this.b5[var1].compareTo("") != 0;
                  this.e2 = this.c4[var1] != null && this.c4[var1].compareTo("") != 0;
                  if (this.d2) {
                     this.f = 0;
                  } else if (this.e2) {
                     this.f = 1;
                  } else {
                     this.f = 2;
                  }
                  break label71;
               case 3:
                  this.e2 = true;
                  if (this.b5[var1] != null && this.b5[var1].compareTo("") != 0) {
                     this.d2 = true;
                     this.f = 0;
                  } else {
                     this.d2 = false;
                     this.f = 1;
                  }
               default:
                  break label71;
            }
         } else {
            this.a8 = this.a3("LOG");
            this.c5 = this.a13[1];
            this.e4 = this.a13[2];
            this.d4 = this.a13[3];
            this.d2 = false;
            if (this.g == 2 && (this.c4[var1] == null || this.c4[var1].compareTo("") == 0)) {
               this.e2 = false;
               this.f = 2;
               break label71;
            }
         }

         this.e2 = true;
         this.f = 1;
      }

      if (com.ea.sims3.e.a11()) {
         if (this.f != 2) {
            this.b3 = this.a13[5];
         } else {
            this.b3 = null;
         }

         this.a4 = this.a13[6];
      } else {
         if (this.f != 2) {
            this.a4 = this.a13[5];
         } else {
            this.a4 = null;
         }

         this.b3 = this.a13[6];
      }

      this.c2();
   }

   private void a(Graphics var1, int var2, int var3) {
      var2 += this.k >> 1;
      var1.setColor(19855);

      for (int var4 = 0; var4 <= 8; var4++) {
         var1.drawLine(var2 + var4, var3 - var4, var2 + var4, var3 + var4);
      }

      var1.setColor(7314887);
      var1.drawLine(var2, var3, var2 + 8, var3 - 8);
      var1.drawLine(var2, var3, var2 + 8, var3 + 8);
      var1.drawLine(var2 + 8, var3 - 8, var2 + 8, var3 + 8);
   }

   private void b(Graphics var1, int var2, int var3) {
      var2 -= this.k >> 1;
      var1.setColor(19855);

      for (int var4 = 0; var4 <= 8; var4++) {
         var1.drawLine(var2 - var4, var3 - var4, var2 - var4, var3 + var4);
      }

      var1.setColor(7314887);
      var1.drawLine(var2, var3, var2 - 8, var3 - 8);
      var1.drawLine(var2, var3, var2 - 8, var3 + 8);
      var1.drawLine(var2 - 8, var3 - 8, var2 - 8, var3 + 8);
   }

   private void a(Graphics var1, int var2, int var3, b var4, boolean var5) {
      int var6 = 0;
      if (var5) {
         com.ea.sims3.k.a(this.a6[2]);
         if (this.a6[2] == this.a6[1]) {
            var6 = 1;
         }
      } else {
         com.ea.sims3.k.a(this.a6[1]);
      }

      int var5b = com.ea.sims3.k.c() - com.ea.sims3.k.b();  // var5 is the boolean parameter above
      int var7 = var5b + 3 + 2;
      int var8 = this.s - var3 >> 1;
      var1.setColor(0);
      var1.fillRect(var8, var2, var3, var7);
      var1.setColor(19855);
      var1.fillRect(var8, var2, var3 - 1, var7 - 1);
      var1.setColor(7314887);
      var1.drawLine(var8 + 1, var2 + 1, var8 + var3 - 2, var2 + 1);
      var1.drawLine(var8 + 1, var2 + 1, var8 + 1, var2 + var7 - 3);
      if (var6 != 0) {
         var1.setColor(16777215);
         var3 = var8 + (var5b >> 1) + 3;
         var6 = var2 + (var7 >> 1);
         var5b = (var5b >> 1) - 1;

         for (int var13 = 0; var13 <= var5b; var13++) {
            var1.drawLine(var3 - var13, var6 - var13, var3 - var13, var6 + var13);
         }
      }

      com.ea.sims3.k.a(var4, this.s >> 1, var2 + 2, com.ea.sims3.k.a | com.ea.sims3.k.d);
   }
}
