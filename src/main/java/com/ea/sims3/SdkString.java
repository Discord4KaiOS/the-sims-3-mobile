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
         for (int index = sourceOffset; index < sourceOffset + count; index++) {
            short charCode;
            if ((charCode = (short)(source[index] & 255)) > 127) {
               charCode = SdkText.fromCharset(charCode);
            }

            target[targetOffset++] = (byte)(charCode >> 8);
            target[targetOffset++] = (byte)charCode;
         }
      } else {
         System.arraycopy(source, sourceOffset, target, targetOffset, byteCount);
      }

      return byteCount;
   }

   public SdkString() {
   }

   public SdkString(String text) {
      this.flags = 1;
      this.offset = 0;
      this.length = (short)text.length();
      this.data = new byte[this.length];

      for (int index = 0; index < this.length; index++) {
         this.data[index] = (byte)text.charAt(index);
         if (text.charAt(index) > 127 && SdkFeatures.isLoggingEnabled()) {
            SdkText.log(
               loggerPrefix
                  + "constructor::char: "
                  + text.charAt(index)
                  + " at index: "
                  + index
                  + " of String: "
                  + text
                  + " is above ASCII range, it's representation is undefined!",
               3
            );
         }
      }
   }

   public SdkString(int capacity, int encoding) {
      if (SdkFeatures.isLoggingEnabled()) {
         SdkText.log(loggerPrefix + "constructor::creating an empty SDKString for use as a buffer w/ capacity: " + capacity + " ,encoding: " + encoding, 1);
      }

      this.flags = (byte)(encoding | -128);
      this.data = new byte[encoding == 0 ? capacity << 1 : capacity];
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

         for (int index = 0; index < text.length(); index++) {
            this.setCharAt(start + index, text.charAt(index));
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

      for (int templateIndex = 0; templateIndex < args.length; templateIndex++) {
         if (args[templateIndex] != null) {
            written += args[templateIndex].length - 2;
            byte argEncoding;
            if ((argEncoding = (byte)(args[templateIndex].flags & 127)) != result.flags && argEncoding != 1) {
               result.flags = 0;
            }
         }
      }

      int lengthShift = result.flags == 0 ? 1 : 0;
      if (written < 0) {
         written = 0;
      }

      result.data = new byte[written + this.length << lengthShift];
      int outOffset = 0;
      written = 0;
      int sourceLengthShift = encoding == 0 ? 1 : 0;

      for (int sourceIndex = 0; sourceIndex < this.length; sourceIndex++) {
         if (this.charAt(sourceIndex) == '\\') {
            short chunkLength = (short)(sourceIndex - written);
            if (outOffset + (chunkLength << lengthShift) > result.data.length) {
               byte[] grownChunk = new byte[outOffset + (chunkLength << lengthShift)];
               System.arraycopy(result.data, 0, grownChunk, 0, outOffset);
               result.data = grownChunk;
            }

            outOffset += convertChars(this.data, this.offset + (written << sourceLengthShift), result.data, outOffset, chunkLength, encoding, result.flags);
            written = sourceIndex++;
            if (sourceIndex < this.length) {
               chunkLength = this.charCodeAt(sourceIndex);
               if (this.charAt(sourceIndex) == '\\') {
                  written = sourceIndex;
               } else if (chunkLength >= 48 && chunkLength <= 90) {
                  int argIndex = chunkLength - 48;
                  if (SdkFeatures.isLoggingEnabled() && (argIndex < 0 || argIndex >= args.length || args[argIndex] == null)) {
                     SdkText.log(loggerPrefix + "replace::replacement string missing: SDKString repl[" + argIndex + "]; it is" + " needed to replace: " + this.toString(), 4);
                  }

                  if (outOffset + (args[argIndex].length << lengthShift) > result.data.length) {
                     byte[] grownArg = new byte[outOffset + (args[argIndex].length << lengthShift)];
                     System.arraycopy(result.data, 0, grownArg, 0, outOffset);
                     result.data = grownArg;
                  }

                  outOffset += convertChars(args[argIndex].data, args[argIndex].offset, result.data, outOffset, args[argIndex].length, (byte)(args[argIndex].flags & 127), result.flags);
                  written = sourceIndex + 1;
               }
            }
         }
      }

      short tailLength = (short)(this.length - written);
      if (outOffset + (tailLength << lengthShift) > result.data.length) {
         byte[] grownTail = new byte[outOffset + (tailLength << lengthShift)];
         System.arraycopy(result.data, 0, grownTail, 0, outOffset);
         result.data = grownTail;
      }

      if ((outOffset = outOffset + convertChars(this.data, this.offset + (written << sourceLengthShift), result.data, outOffset, tailLength, encoding, result.flags)) < result.data.length) {
         byte[] trimmed = new byte[outOffset];
         System.arraycopy(result.data, 0, trimmed, 0, outOffset);
         result.data = trimmed;
      }

      result.length = (short)(outOffset >> lengthShift);
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

      for (int index = 0; index < this.length; index++) {
         text.append(this.charAt(index));
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

   public final SdkString format(String template) {
      return this.format(new SdkString[]{new SdkString(template)});
   }

   public final SdkString format(SdkString template) {
      return this.format(new SdkString[]{template});
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
