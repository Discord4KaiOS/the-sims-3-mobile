package com.ea.sims3;

public final class SdkString {
   private static String loggerPrefix = null;
   public byte[] data;
   public int offset;
   public short length;
   public byte flags;

   private static int convertChars(byte[] source, int sourceOffset, byte[] target, int targetOffset, short count, int sourceEncoding, int targetEncoding) {
      if (SdkFeatures.isLoggingEnabled() && sourceEncoding != targetEncoding && sourceEncoding != 1 && targetEncoding != 0) {
         SdkText.log(
            loggerPrefix
               + "convertBytes::method does not support copy from encoding: "
               + sourceEncoding
               + " to encoding: "
               + targetEncoding
               + "; please contact the SDK team as this scenario "
               + "should be protected in all public methods!",
            4
         );
      }

      int byteCount = targetEncoding == 0 ? count << 1 : count;
      if (sourceEncoding != targetEncoding && (sourceEncoding != 1 || targetEncoding == 0)) {
         for (int i = sourceOffset; i < sourceOffset + count; i++) {
            short ch;
            if ((ch = (short)(source[i] & 255)) > 127) {
               ch = SdkText.fromCharset(ch);
            }

            target[targetOffset++] = (byte)(ch >> 8);
            target[targetOffset++] = (byte)ch;
         }
      } else {
         System.arraycopy(source, sourceOffset, target, targetOffset, byteCount);
      }

      return byteCount;
   }

   public SdkString() {
   }

   public SdkString(String var1) {
      this.flags = 1;
      this.offset = 0;
      this.length = (short)var1.length();
      this.data = new byte[this.length];

      for (int var2 = 0; var2 < this.length; var2++) {
         this.data[var2] = (byte)var1.charAt(var2);
         if (var1.charAt(var2) > 127 && SdkFeatures.isLoggingEnabled()) {
            SdkText.log(
               loggerPrefix
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
         SdkText.log(loggerPrefix + "constructor::creating an empty SDKString for use as a buffer w/ capacity: " + var1 + " ,encoding: " + var2, 1);
      }

      this.flags = (byte)(var2 | -128);
      this.data = new byte[var2 == 0 ? var1 << 1 : var1];
      this.offset = 0;
      this.length = 0;
   }

   private boolean isMutable() {
      return (this.flags & -128) != 0;
   }

   public final SdkString append(SdkString other) {
      if (SdkFeatures.isLoggingEnabled()) {
         SdkText.log(loggerPrefix + "append::appending str: " + other.toString() + " onto this: " + this.toString(), 1);
      }

      if (!this.isMutable()) {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.log(loggerPrefix + "append::this is not a mutable SDKString, cannot append. " + "To create a mutable (buffer) SDKString, use SDKString(int,int)", 4);
         }

         return this;
      } else {
         byte ourEncoding = (byte)this.encoding();
         byte otherEncoding;
         if ((otherEncoding = (byte)other.encoding()) != ourEncoding && otherEncoding != 1 && ourEncoding != 0) {  // 1 == "not set"
            if (SdkFeatures.isLoggingEnabled()) {
               SdkText.log(loggerPrefix + "append::unable to append SDKString w/ encoding: " + otherEncoding + " onto SDKString w/ encoding: " + ourEncoding, 4);
            }

            return this;
         } else {
            int usedBytes = this.offset + (ourEncoding == 0 ? this.length << 1 : this.length);
            if (SdkFeatures.isLoggingEnabled()) {
               int addBytes = ourEncoding == 0 ? 2 : 1;
               addBytes = other.length * addBytes;
               if (usedBytes + addBytes > this.data.length) {
                  SdkText.log(
                     loggerPrefix
                        + "append::buffer overflow; "
                        + "this buffer has size(bytes): "
                        + this.data.length
                        + " , and contained: "
                        + usedBytes
                        + " bytes prior"
                        + " to appending str: "
                        + other.toString()
                        + " containing "
                        + addBytes
                        + " bytes",
                     4
                  );
               }
            }

            convertChars(other.data, other.offset, this.data, usedBytes, other.length, otherEncoding, ourEncoding);
            this.length = (short)(this.length + other.length);
            return this;
         }
      }
   }

   public final SdkString append(String text) {
      if (SdkFeatures.isLoggingEnabled()) {
         SdkText.log(loggerPrefix + "append::appending JAVA str: " + text + " onto this: " + this.toString(), 1);
      }

      if (!this.isMutable()) {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.log(loggerPrefix + "append::append called on an immutable SDKString", 4);
         }

         return this;
      } else {
         short start = this.length;
         this.length = (short)(this.length + text.length());

         for (int i = 0; i < text.length(); i++) {
            this.setCharAt(start + i, text.charAt(i));
         }

         return this;
      }
   }

   public final void setCharAt(int index, char value) {
      if (!this.isMutable()) {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.log(loggerPrefix + "setCharAt::this method cannot be called on an immutable" + "SDKString.  To create a mutable (buffer) SDKString, use SDKString(int,int)", 4);
         }
      } else {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.log(loggerPrefix + "setCharAt::setting the char at index: " + index + " of SDKString (mutable): " + this.toString() + " to: " + value, 1);
            if (index >= this.length || index < 0) {
               SdkText.log(loggerPrefix + "setCharAt::index out of bounds: " + index + " , this.length(): " + this.length, 4);
            }
         }

         byte encoding = (byte)this.encoding();
         short code;
         if ((code = (short)value) > 127 && encoding != 0) {
            code = SdkText.toCharset(code);
         }

         index = this.offset + (encoding == 0 ? index << 1 : index);
         if (encoding == 0) {
            this.data[index++] = (byte)(code >> 8);
         }

         this.data[index] = (byte)code;
      }
   }

   public final char charAt(int index) {
      short code;
      return (code = this.charCodeAt(index)) > 127 && this.encoding() != 0 ? (char)SdkText.fromCharset(code) : (char)code;
   }

   public final short charCodeAt(int index) {
      if (this.encoding() == 0) {
         index = this.offset + (index << 1);
         return (short)((this.data[index] & 255) << 8 | this.data[index + 1] & 255);
      } else {
         return (short)(this.data[this.offset + index] & 255);
      }
   }

   public final SdkString format(SdkString[] args) {
      byte encoding = (byte)(this.flags & 127);
      SdkString result;
      (result = new SdkString()).flags = encoding;
      result.offset = 0;
      int written = 0;

      for (int i = 0; i < args.length; i++) {
         if (args[i] != null) {
            written += args[i].length - 2;
            byte argEncoding;
            if ((argEncoding = (byte)(args[i].flags & 127)) != result.flags && argEncoding != 1) {
               result.flags = 0;
            }
         }
      }

      int var13 = result.flags == 0 ? 1 : 0;
      if (written < 0) {
         written = 0;
      }

      result.data = new byte[written + this.length << var13];
      int var14 = 0;
      written = 0;
      int var7 = encoding == 0 ? 1 : 0;

      for (int var9 = 0; var9 < this.length; var9++) {
         if (this.charAt(var9) == '\\') {
            short var8 = (short)(var9 - written);
            if (var14 + (var8 << var13) > result.data.length) {
               byte[] var10 = new byte[var14 + (var8 << var13)];
               System.arraycopy(result.data, 0, var10, 0, var14);
               result.data = var10;
            }

            var14 += convertChars(this.data, this.offset + (written << var7), result.data, var14, var8, encoding, result.flags);
            written = var9++;
            if (var9 < this.length) {
               var8 = this.charCodeAt(var9);
               if (this.charAt(var9) == '\\') {
                  written = var9;
               } else if (var8 >= 48 && var8 <= 90) {
                  int var20 = var8 - 48;
                  if (SdkFeatures.isLoggingEnabled() && (var20 < 0 || var20 >= args.length || args[var20] == null)) {
                     SdkText.log(loggerPrefix + "replace::replacement string missing: SDKString repl[" + var20 + "]; it is" + " needed to replace: " + this.toString(), 4);
                  }

                  if (var14 + (args[var20].length << var13) > result.data.length) {
                     byte[] var12 = new byte[var14 + (args[var20].length << var13)];
                     System.arraycopy(result.data, 0, var12, 0, var14);
                     result.data = var12;
                  }

                  var14 += convertChars(args[var20].data, args[var20].offset, result.data, var14, args[var20].length, (byte)(args[var20].flags & 127), result.flags);
                  written = var9 + 1;
               }
            }
         }
      }

      short var17 = (short)(this.length - written);
      if (var14 + (var17 << var13) > result.data.length) {
         byte[] var18 = new byte[var14 + (var17 << var13)];
         System.arraycopy(result.data, 0, var18, 0, var14);
         result.data = var18;
      }

      if ((var14 = var14 + convertChars(this.data, this.offset + (written << var7), result.data, var14, var17, encoding, result.flags)) < result.data.length) {
         byte[] var19 = new byte[var14];
         System.arraycopy(result.data, 0, var19, 0, var14);
         result.data = var19;
      }

      result.length = (short)(var14 >> var13);
      return result;
   }

   private SdkString concat(SdkString other) {
      if (SdkFeatures.isLoggingEnabled()) {
         SdkText.log(loggerPrefix + "concat::concatenating '" + other.toString() + "' onto '" + this.toString() + "'", 1);
      }

      byte ourEncoding = (byte)this.encoding();
      byte otherEncoding = (byte)other.encoding();
      SdkString result;
      (result = new SdkString()).length = (short)(this.length + other.length);
      result.offset = 0;
      if (ourEncoding != otherEncoding && otherEncoding != 1) {
         result.flags = 0;
      } else {
         result.flags = ourEncoding;
      }

      result.data = new byte[result.flags == 0 ? result.length << 1 : result.length];
      int written = convertChars(this.data, this.offset, result.data, 0, this.length, ourEncoding, result.flags);
      convertChars(other.data, other.offset, result.data, written, other.length, otherEncoding, result.flags);
      return result;
   }

   public final String toString() {
      StringBuffer text = new StringBuffer();

      for (int i = 0; i < this.length; i++) {
         text.append(this.charAt(i));
      }

      return text.toString();
   }

   public final SdkString copy() {
      return this.concat(new SdkString(""));
   }

   private int capacity() {
      return this.encoding() == 0 ? this.data.length >> 1 : this.data.length;
   }

   public final int encoding() {
      return this.flags & 127;
   }

   public final SdkString format(String var1) {
      return this.format(new SdkString[]{new SdkString(var1)});
   }

   public final SdkString format(SdkString var1) {
      return this.format(new SdkString[]{var1});
   }

   public final void setLength(int newLength) {
      if (!this.isMutable()) {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.log(loggerPrefix + "setLength::this method cannot be called on an immutable" + "SDKString.  To create a mutable (buffer) SDKString, use SDKString(int,int)", 4);
         }
      } else {
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.log(loggerPrefix + "setLength::setting the length of SDKString (mutable): " + this.toString() + " to: " + newLength, 1);
            if (newLength > this.capacity() || newLength < 0) {
               SdkText.log(loggerPrefix + "setLength::the specified length is out of bounds, capacity: " + this.capacity(), 4);
            }
         }

         this.length = (short)newLength;
      }
   }

   static {
      if (SdkFeatures.isLoggingEnabled()) {
         loggerPrefix = "SDKString::";
      }
   }
}
