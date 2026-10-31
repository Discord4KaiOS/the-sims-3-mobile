package com.ea.sims3;

public final class SdkString {
   private static String a = null;
   public byte[] a2;
   public int a3;
   public short a4;
   public byte a5;

   private static int a(byte[] var0, int var1, byte[] var2, int var3, short var4, int var5, int var6) {
      if (SdkFeatures.isLoggingEnabled() && var5 != var6 && var5 != 1 && var6 != 0) {
         SdkText.a(
            a
               + "convertBytes::method does not support copy from encoding: "
               + var5
               + " to encoding: "
               + var6
               + "; please contact the SDK team as this scenario "
               + "should be protected in all public methods!",
            4
         );
      }

      int var7 = var6 == 0 ? var4 << 1 : var4;
      if (var5 != var6 && (var5 != 1 || var6 == 0)) {
         for (int var9 = var1; var9 < var1 + var4; var9++) {
            short var10;
            if ((var10 = (short)(var0[var9] & 255)) > 127) {
               var10 = SdkText.b(var10);
            }

            var2[var3++] = (byte)(var10 >> 8);
            var2[var3++] = (byte)var10;
         }
      } else {
         System.arraycopy(var0, var1, var2, var3, var7);
      }

      return var7;
   }

   public SdkString() {
   }

   public SdkString(String var1) {
      this.a5 = 1;
      this.a3 = 0;
      this.a4 = (short)var1.length();
      this.a2 = new byte[this.a4];

      for (int var2 = 0; var2 < this.a4; var2++) {
         this.a2[var2] = (byte)var1.charAt(var2);
         if (var1.charAt(var2) > 127 && SdkFeatures.isLoggingEnabled()) {
            SdkText.a(
               a
                  + "constructor::char: "
                  + var1.charAt(var2)
                  + " at index: "
                  + var2
                  + " of String: "
                  + var1
                  + " is above ASCII range, it's representation is undefined!",
               3
            );
         }
      }
   }

   public SdkString(int var1, int var2) {
      if (SdkFeatures.isLoggingEnabled()) {
         SdkText.a(a + "constructor::creating an empty SDKString for use as a buffer w/ capacity: " + var1 + " ,encoding: " + var2, 1);
      }

      this.a5 = (byte)(var2 | -128);
      this.a2 = new byte[var2 == 0 ? var1 << 1 : var1];
      this.a3 = 0;
      this.a4 = 0;
   }

   private boolean a11() {
      return (this.a5 & -128) != 0;
   }

   public final SdkString a2(SdkString var1) {
      if (SdkFeatures.isLoggingEnabled()) {
         SdkText.a(a + "append::appending str: " + var1.toString() + " onto this: " + this.toString(), 1);
      }

      if (!this.a11()) {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.a(a + "append::this is not a mutable SDKString, cannot append. " + "To create a mutable (buffer) SDKString, use SDKString(int,int)", 4);
         }

         return this;
      } else {
         byte var2 = (byte)this.a();
         byte var3;
         if ((var3 = (byte)var1.a()) != var2 && var3 != 1 && var2 != 0) {  // 1 == "not set"
            if (SdkFeatures.isLoggingEnabled()) {
               SdkText.a(a + "append::unable to append SDKString w/ encoding: " + var3 + " onto SDKString w/ encoding: " + var2, 4);
            }

            return this;
         } else {
            int var4 = this.a3 + (var2 == 0 ? this.a4 << 1 : this.a4);
            if (SdkFeatures.isLoggingEnabled()) {
               int var5 = var2 == 0 ? 2 : 1;
               var5 = var1.a4 * var5;
               if (var4 + var5 > this.a2.length) {
                  SdkText.a(
                     a
                        + "append::buffer overflow; "
                        + "this buffer has size(bytes): "
                        + this.a2.length
                        + " , and contained: "
                        + var4
                        + " bytes prior"
                        + " to appending str: "
                        + var1.toString()
                        + " containing "
                        + var5
                        + " bytes",
                     4
                  );
               }
            }

            a(var1.a2, var1.a3, this.a2, var4, var1.a4, var3, var2);
            this.a4 = (short)(this.a4 + var1.a4);
            return this;
         }
      }
   }

   public final SdkString a(String var1) {
      if (SdkFeatures.isLoggingEnabled()) {
         SdkText.a(a + "append::appending JAVA str: " + var1 + " onto this: " + this.toString(), 1);
      }

      if (!this.a11()) {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.a(a + "append::append called on an immutable SDKString", 4);
         }

         return this;
      } else {
         short var2 = this.a4;
         this.a4 = (short)(this.a4 + var1.length());

         for (int var3 = 0; var3 < var1.length(); var3++) {
            this.a(var2 + var3, var1.charAt(var3));
         }

         return this;
      }
   }

   public final void a(int var1, char var2) {
      if (!this.a11()) {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.a(a + "setCharAt::this method cannot be called on an immutable" + "SDKString.  To create a mutable (buffer) SDKString, use SDKString(int,int)", 4);
         }
      } else {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.a(a + "setCharAt::setting the char at index: " + var1 + " of SDKString (mutable): " + this.toString() + " to: " + var2, 1);
            if (var1 >= this.a4 || var1 < 0) {
               SdkText.a(a + "setCharAt::index out of bounds: " + var1 + " , this.length(): " + this.a4, 4);
            }
         }

         byte var3 = (byte)this.a();
         short var5;
         if ((var5 = (short)var2) > 127 && var3 != 0) {
            var5 = SdkText.a2(var5);
         }

         var1 = this.a3 + (var3 == 0 ? var1 << 1 : var1);
         if (var3 == 0) {
            this.a2[var1++] = (byte)(var5 >> 8);
         }

         this.a2[var1] = (byte)var5;
      }
   }

   public final char a2(int var1) {
      short var2;
      return (var2 = this.a11(var1)) > 127 && this.a() != 0 ? (char)SdkText.b(var2) : (char)var2;
   }

   public final short a11(int var1) {
      if (this.a() == 0) {
         var1 = this.a3 + (var1 << 1);
         return (short)((this.a2[var1] & 255) << 8 | this.a2[var1 + 1] & 255);
      } else {
         return (short)(this.a2[this.a3 + var1] & 255);
      }
   }

   public final SdkString a(SdkString[] var1) {
      byte var2 = (byte)(this.a5 & 127);
      SdkString var3;
      (var3 = new SdkString()).a5 = var2;
      var3.a3 = 0;
      int var4 = 0;

      for (int var5 = 0; var5 < var1.length; var5++) {
         if (var1[var5] != null) {
            var4 += var1[var5].a4 - 2;
            byte var6;
            if ((var6 = (byte)(var1[var5].a5 & 127)) != var3.a5 && var6 != 1) {
               var3.a5 = 0;
            }
         }
      }

      int var13 = var3.a5 == 0 ? 1 : 0;
      if (var4 < 0) {
         var4 = 0;
      }

      var3.a2 = new byte[var4 + this.a4 << var13];
      int var14 = 0;
      var4 = 0;
      int var7 = var2 == 0 ? 1 : 0;

      for (int var9 = 0; var9 < this.a4; var9++) {
         if (this.a2(var9) == '\\') {
            short var8 = (short)(var9 - var4);
            if (var14 + (var8 << var13) > var3.a2.length) {
               byte[] var10 = new byte[var14 + (var8 << var13)];
               System.arraycopy(var3.a2, 0, var10, 0, var14);
               var3.a2 = var10;
            }

            var14 += a(this.a2, this.a3 + (var4 << var7), var3.a2, var14, var8, var2, var3.a5);
            var4 = var9++;
            if (var9 < this.a4) {
               var8 = this.a11(var9);
               if (this.a2(var9) == '\\') {
                  var4 = var9;
               } else if (var8 >= 48 && var8 <= 90) {
                  int var20 = var8 - 48;
                  if (SdkFeatures.isLoggingEnabled() && (var20 < 0 || var20 >= var1.length || var1[var20] == null)) {
                     SdkText.a(a + "replace::replacement string missing: SDKString repl[" + var20 + "]; it is" + " needed to replace: " + this.toString(), 4);
                  }

                  if (var14 + (var1[var20].a4 << var13) > var3.a2.length) {
                     byte[] var12 = new byte[var14 + (var1[var20].a4 << var13)];
                     System.arraycopy(var3.a2, 0, var12, 0, var14);
                     var3.a2 = var12;
                  }

                  var14 += a(var1[var20].a2, var1[var20].a3, var3.a2, var14, var1[var20].a4, (byte)(var1[var20].a5 & 127), var3.a5);
                  var4 = var9 + 1;
               }
            }
         }
      }

      short var17 = (short)(this.a4 - var4);
      if (var14 + (var17 << var13) > var3.a2.length) {
         byte[] var18 = new byte[var14 + (var17 << var13)];
         System.arraycopy(var3.a2, 0, var18, 0, var14);
         var3.a2 = var18;
      }

      if ((var14 = var14 + a(this.a2, this.a3 + (var4 << var7), var3.a2, var14, var17, var2, var3.a5)) < var3.a2.length) {
         byte[] var19 = new byte[var14];
         System.arraycopy(var3.a2, 0, var19, 0, var14);
         var3.a2 = var19;
      }

      var3.a4 = (short)(var14 >> var13);
      return var3;
   }

   private SdkString c(SdkString var1) {
      if (SdkFeatures.isLoggingEnabled()) {
         SdkText.a(a + "concat::concatenating '" + var1.toString() + "' onto '" + this.toString() + "'", 1);
      }

      byte var2 = (byte)this.a();
      byte var3 = (byte)var1.a();
      SdkString var4;
      (var4 = new SdkString()).a4 = (short)(this.a4 + var1.a4);
      var4.a3 = 0;
      if (var2 != var3 && var3 != 1) {
         var4.a5 = 0;
      } else {
         var4.a5 = var2;
      }

      var4.a2 = new byte[var4.a5 == 0 ? var4.a4 << 1 : var4.a4];
      int var5 = a(this.a2, this.a3, var4.a2, 0, this.a4, var2, var4.a5);
      a(var1.a2, var1.a3, var4.a2, var5, var1.a4, var3, var4.a5);
      return var4;
   }

   public final String toString() {
      StringBuffer var1 = new StringBuffer();

      for (int var2 = 0; var2 < this.a4; var2++) {
         var1.append(this.a2(var2));
      }

      return var1.toString();
   }

   public final SdkString a2() {
      return this.c(new SdkString(""));
   }

   private int b() {
      return this.a() == 0 ? this.a2.length >> 1 : this.a2.length;
   }

   public final int a() {
      return this.a5 & 127;
   }

   public final SdkString b(String var1) {
      return this.a(new SdkString[]{new SdkString(var1)});
   }

   public final SdkString b(SdkString var1) {
      return this.a(new SdkString[]{var1});
   }

   public final void a12(int var1) {
      if (!this.a11()) {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.a(a + "setLength::this method cannot be called on an immutable" + "SDKString.  To create a mutable (buffer) SDKString, use SDKString(int,int)", 4);
         }
      } else {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.a(a + "setLength::setting the length of SDKString (mutable): " + this.toString() + " to: " + var1, 1);
            if (var1 > this.b() || var1 < 0) {
               SdkText.a(a + "setLength::the specified length is out of bounds, capacity: " + this.b(), 4);
            }
         }

         this.a4 = (short)var1;
      }
   }

   static {
      if (SdkFeatures.isLoggingEnabled()) {
         a = "SDKString::";
      }
   }
}
