package com.ea.sims3;

import javax.microedition.lcdui.Graphics;

public final class d {
   public int a = -1;
   public int b;
   private int c;
   private int d;

   public d() {
      this.a = -1;
      this.b = 0;
      this.c = 0;
      this.d = 0;
   }

   public final boolean a13(int var1) {
      if ((this.d & 2) == 0) {
         return false;
      } else {
         this.c += var1;
         if (this.c > t.a3(this.a, this.b)) {
            if ((this.d & 8) != 0) {
               this.c2();
               return true;
            } else {
               this.b4();
               return true;
            }
         } else {
            return false;
         }
      }
   }

   private void b4() {
      this.c = this.c - t.a3(this.a, this.b);
      if (this.c < 0) {
         this.c = 0;
      }

      this.b++;
      if (this.b >= t.a3(this.a)) {
         if ((this.d & 4) != 0) {
            this.b = 0;
            return;
         }

         this.b = t.a3(this.a) - 1;
         this.d &= -3;
      }
   }

   private void c2() {
      this.c = this.c - t.a3(this.a, this.b);
      if (this.c < 0) {
         this.c = 0;
      }

      this.b--;
      if (this.b <= 0) {
         if ((this.d & 4) != 0) {
            this.b = t.a3(this.a) - 1;
            return;
         }

         this.b = 0;
         this.d &= -3;
      }
   }

   public final void a4(int var1, int var2) {
      boolean var3;
      if (!(var3 = (var2 & 16) != 0) && this.a == var1) {
         if (this.b >= t.a3(this.a)) {
            this.b = 0;
         } else if (this.b < 0) {
            this.b = t.a3(this.a) - 1;
         }
      } else {
         this.a = var1;
         if ((this.d & 8) != 0) {
            this.b = t.a3(this.a) - 1;
         } else {
            this.b = 0;
         }

         this.c = 0;
      }

      this.d = 3 | var2 & 4;
   }

   public final void a(Graphics var1, int var2, int var3) {
      if (this.a >= 0) {
         t.a2(var1, this.a, this.b, var2, var3);
      }
   }

   private int b() {
      return this.a == -1 ? 0 : t.a3(this.a);
   }

   public final int a() {
      return t.e(this.a, this.b);
   }

   public final boolean a2(int[] var1, int var2) {
      return t.a2(var1, this.a, this.b, var2);
   }

   public final boolean a11() {
      return (this.d & 2) != 0;
   }

   public final boolean b5() {
      return (this.d & 4) != 0;
   }

   public final void a(boolean var1) {
      if (var1) {
         this.d |= 2;
      } else {
         this.d &= -3;
      }
   }

   public final void a10() {
      this.b = Math.max(0, this.b() - 1);
      if (!this.b5()) {
         this.a(false);
      }
   }
}
