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

public final class SdkText {
   public static int TOP = 16;
   public static int BOTTOM = 32;
   public static int BASELINE = 64;
   public static int HCENTER = 1;
   public static int VCENTER = 2;
   public static int LEFT = 4;
   public static int RIGHT = 8;
   private static String logPrefix = null;
   private static Object font;
   private static Object fontImage;
   private static int usesTransform;
   private static byte[] fontHeader;
   private static short[] headerStringOffsets;
   private static short[] languageChunkGroups;
   private static short[] chunkStringCounts;
   private static byte[] glyphData;
   private static int[] colorTable;
   private static short[] glyphIndexTable;
   private static short[] glyphOffsetTable;
   private static byte[][] strings;
   private static short[][] chunkStringOffsets;
   private static byte[] chunkGroupOfId;
   private static byte[] chunkFlags;
   private static byte chunkConfig;
   private static short[][] storageChunkOffsets;
   private static short[] chunkStringCount;
   private static short[] chunkDataSize;
   private static short[] chunkOffsetCount;
   private static byte[] chunkOwners;
   private static byte[] languageEncodings;
   private static int numFontGlyphs;
   private static String[] languageCodes;
   private static byte[] fontDifferentiators;
   private static byte currentLanguage = 0;
   private static int[] penPosition = new int[2];
   private static int[] glyphBounds = new int[4];
   private static byte[] tempShort = new byte[2];
   private static Graphics graphics;
   private static StringBuffer a12;
   private static boolean changingLanguage = false;
   public static MIDlet midlet;
   private static int textColor;
   private static StringBuffer logBuffer;

   private static Object createFont(Object var0, byte[] var1, int var2) {
      Object[] var3 = parseFontChunk(var1);
      return new Object[]{var0, var3[0], var3[1], var3[2], var3[3], var3[4], var3[5], new Integer(var2)};
   }

   public static Object createFont(Image var0, byte[] var1) {
      return createFont(var0, var1, -1);
   }

   // The retail build calls these directly; the checked exception is wrapped so the
   // decompiled source compiles (it never happens for classpath resources).
   private static long skip(InputStream var0, long var1) {
      try {
         return var0.skip(var1);
      } catch (IOException var3) {
         throw new IllegalStateException(var3);
      }
   }

   public static void setFont(Object var0) {
      font = null;
      fontHeader = null;
      fontImage = null;
      languageChunkGroups = null;
      headerStringOffsets = null;
      chunkStringCounts = null;
      glyphData = null;
      colorTable = null;
      if (var0 != null) {
         if (var0 instanceof Font && com.ea.sims3.SdkFeatures.isSystemFontEnabled()) {
            (fontHeader = new byte[12])[0] = 1;
            fontHeader[1] = 2;
            fontHeader[7] = 0;
            fontHeader[8] = 1;
            fontHeader[9] = 2;
            fontHeader[10] = 4;
            if (graphics != null) {
               graphics.setFont((Font)var0);
            }
         } else if (com.ea.sims3.SdkFeatures.isRectangleFontEnabled() || com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
            fontHeader = (byte[])((Object[])var0)[1];
            fontImage = ((Object[])var0)[0];
            usesTransform = (Integer)((Object[])var0)[7];
            languageChunkGroups = (short[])((Object[])var0)[2];
            headerStringOffsets = (short[])((Object[])var0)[3];
            chunkStringCounts = (short[])((Object[])var0)[4];
            glyphData = (byte[])((Object[])var0)[5];
            colorTable = (int[])((Object[])var0)[6];
            if (languageEncodings != null && fontHeader[11] != languageEncodings[currentLanguage] && languageEncodings[currentLanguage] > 1) {
               loadChunksForLanguage();
            }
         }
      }

      font = var0;
   }

   public static void setSpaceSpacing(int var0) {
      fontHeader[5] = (byte)var0;
   }

   public static int getSpaceSpacing() {
      if (font == null && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getSpaceSpacing::no font loaded, use setFont(Object)", 4);
      }

      return fontHeader[5] & 0xFF;
   }

   public static int getLeadingSpacing() {
      if (font == null && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getLeadingSpacing::no font loaded, use setFont(Object)", 4);
      }

      return fontHeader[2];
   }

   public static int getLineHeight() {
      if (font == null && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getLineSize::no font loaded, use setFont(Object)", 4);
      }

      if ((fontHeader[10] & 4) != 0 && com.ea.sims3.SdkFeatures.isSystemFontEnabled()) {
         return ((Font)font).getHeight();
      } else {
         return !com.ea.sims3.SdkFeatures.isRectangleFontEnabled() && !com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled() ? -1 : (fontHeader[3] & 0xFF) + (fontHeader[4] & 0xFF) + fontHeader[2];
      }
   }

   public static void drawString(SdkString var0, int var1, int var2, int var3) {
      drawSubString(var0, 0, var0.length, var1, var2, var3);
   }

   private static void a(int[] var0, int var1) {
      if (fontHeader[8] == 1) {
         var0[0] += var1;
      } else if (fontHeader[8] == 4) {
         var0[0] -= var1;
      } else if (fontHeader[8] == 2) {
         var0[1] += var1;
      } else {
         if (fontHeader[8] == 8) {
            var0[1] -= var1;
         }
      }
   }

   private static void drawSubString(SdkString var0, int var1, int var2, int var3, int var4, int var5) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (graphics == null) {
            log(logPrefix + "drawSubString::Graphics are null, use setGraphics(Graphics)", 4);
         }

         if (font == null) {
            log(logPrefix + "drawSubString ::Font is null, use setFont(Object)", 4);
         }

         if (!com.ea.sims3.SdkFeatures.isSystemFontEnabled() && !com.ea.sims3.SdkFeatures.isRectangleFontEnabled() && !com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
            log(logPrefix + "drawSubString ::no font support enabled, you must enable at least 1 type of font support in SDKConfig", 4);
         }
      }

      if ((fontHeader[10] & 4) != 0 && com.ea.sims3.SdkFeatures.isSystemFontEnabled()) {
         if (a12 == null) {
            a12 = new StringBuffer();
         }

         a12.setLength(0);

         for (int var27 = var1; var27 < var1 + var2; var27++) {
            char var28;
            if ((var28 = var0.charAt(var27)) != 173 || var27 == var1 + var2 - 1) {
               a12.append(var28);
            }
         }

         if (fontHeader[7] != 0 && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "drawSubString ::rendering of system fonts does not support transform: " + fontHeader[7], 4);
         }

         graphics.setColor(textColor);
         graphics.drawString(a12.toString(), var3, var4, var5);
      } else {
         if (com.ea.sims3.SdkFeatures.isRectangleFontEnabled() || com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
            int var6 = fontHeader[3] & 255;
            int var7 = getLineHeight();
            int var8 = fontHeader[8];
            int var9 = fontHeader[9];
            boolean var10 = usesTransform != -1;
            boolean var11 = (fontHeader[10] & 2) != 0;
            boolean var12 = (fontHeader[10] & -128) != 0;
            int var13 = 0;
            int var14 = 0;
            if ((var5 & RIGHT) != 0) {
               var13 = 0 - substringWidth(var0, var1, var2);
            } else if ((var5 & HCENTER) != 0) {
               var13 = 0 - (substringWidth(var0, var1, var2) >> 1);
            }

            if ((var5 & BASELINE) != 0) {
               var14 = 0 - var6;
            } else if ((var5 & BOTTOM) != 0) {
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

            penPosition[0] = var3;
            penPosition[1] = var4;
            if ((var3 = var0.encoding()) != 1 && var3 != 0 && var3 != languageEncodings[currentLanguage] && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(
                  logPrefix
                     + "drawSubString::SDKString ["
                     + var0.toString()
                     + "] has encoding="
                     + var0.encoding()
                     + " but the currently set language has encoding: "
                     + languageEncodings[currentLanguage],
                  4
               );
            }

            byte var25 = (byte)((var8 & 5) != 0 ? (var8 & 4) >> 1 : ((var9 & 1) << 1) + ((~var9 & 2) >> 1));
            byte var26 = (byte)((var8 & 10) != 0 ? (var8 & 8) >> 2 : (var9 & 2) + (~var9 & 1));
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "drawSubString::offset = " + var1 + " len = " + var2 + " \"" + var0.toString() + "\"", 1);
               if (var11 && com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled() && fontHeader[7] != 0) {
                  log(logPrefix + "drawSubString ::rendering of rectangle fonts does not currently support transform: " + fontHeader[7], 4);
               }
            }

            var9 = var8 = graphics.getColor();

            for (int var31 = var1; var31 < var1 + var2; var31++) {
               short var32;
               if ((var32 = var0.charCodeAt(var31)) != 173 || var31 == var1 + var2 - 1) {
                  short var15;
                  int var16 = glyphHeight(var15 = glyphIndex((short)var32, var3));
                  if (var15 == -1) {
                     if (var32 != 32) {
                        if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                           log(logPrefix + "drawSubString::character not in Font: \\u" + (var3 != 0 && var32 >= 128 ? fromCharset((short)var32) : var32), 4);
                        }

                        graphics.setColor(65280);
                        graphics.fillRect(penPosition[0], penPosition[1], var16, var7);
                        graphics.setColor(16711680);
                        var9 = 16711680;
                        graphics.drawLine(penPosition[0], penPosition[1], penPosition[0] + var16, penPosition[1] + var7);
                        graphics.drawLine(penPosition[0], penPosition[1] + var7, penPosition[0] + var16, penPosition[1]);
                     }

                     a(penPosition, var16);
                  } else {
                     short var10001 = ++var15;
                     var15++;
                     var32 = (short)(glyphData[var10001] & 255);

                     for (int var17 = 0; var17 < var32; var17++) {
                        if (var11 && com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
                           int var18 = glyphData[var15++] & 255;
                           byte var19 = glyphData[var15++];
                           byte var20 = glyphData[var15++];
                           int var21;
                           int var22;
                           if (var12) {
                              var21 = glyphData[var15] >> 4 & 15;
                              var22 = glyphData[var15++] & 15;
                           } else {
                              var21 = glyphData[var15++] & 255;
                              var22 = glyphData[var15++] & 255;
                           }

                           if (colorTable[var18] != var9) {
                              graphics.setColor(colorTable[var18]);
                              var9 = colorTable[var18];
                           }

                           graphics.fillRect(penPosition[0] + var19, penPosition[1] + var6 - var20, var21, var22);
                        }

                        if (!var11 && com.ea.sims3.SdkFeatures.isRectangleFontEnabled()) {
                           int var45 = glyphData[var15++] & 255;
                           int var46 = glyphData[var15++] & 255;
                           int var47 = glyphData[var15++];
                           int var49 = glyphData[var15++];
                           int var51 = glyphData[var15++] & 255;
                           int var23 = glyphData[var15++] & 255;
                           glyphBounds[0] = var47;
                           glyphBounds[1] = var49 - var6;
                           glyphBounds[2] = -var47 - var51;
                           glyphBounds[3] = var6 - var49 - var23;
                           var47 = penPosition[0] + glyphBounds[var25];
                           var49 = penPosition[1] + glyphBounds[var26];
                           if (var10) {
                              flushLog();
                           } else {
                              drawGlyph((Image)fontImage, var45, var46, var51, var23, fontHeader[7], var47, var49, 0);
                           }
                        }
                     }

                     a(penPosition, var16);
                  }
               }
            }

            graphics.setColor(var8);
         }
      }
   }

   public static void drawWrappedString(SdkString var0, short[] var1, int var2, int var3, int var4, int var5, int var6) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (graphics == null) {
            log(logPrefix + "drawWrappedString::Graphics are null, use setGraphics(Graphics)", 4);
         }

         if (font == null) {
            log(logPrefix + "drawWrappedString::Font is null, use setFont(Object)", 4);
         }
      }

      int var7 = getLineHeight();
      int var8 = 0;
      int var9 = 0;
      int var10 = 0;
      if ((var6 & VCENTER) != 0) {
         var10 = var1[0] * var7 - fontHeader[2] >> 1;
         var6 ^= VCENTER;
      } else if ((var6 & BOTTOM) != 0) {
         var10 = var1[0] * var7 - fontHeader[2];
         var6 ^= BOTTOM;
      }

      var6 |= TOP;
      if (fontHeader[9] == 1) {
         var4 -= var10;
         var8 = var7;
      } else if (fontHeader[9] == 4) {
         var4 += var10;
         var8 = -var7;
      } else if (fontHeader[9] == 2) {
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
         if ((var13 = var0.charCodeAt((var1[var16 + 1] & '\uffff') - 1)) == var15 || var13 == 32) {
            var12--;
         }

         drawSubString(var0, var11, var12, var4, var5, var6);
         var4 += var8;
         var5 += var9;
      }
   }

   private static int glyphHeight(short var0) {
      int var1 = fontHeader[6] & 255;
      return var0 == -1 ? (fontHeader[5] & 0xFF) + var1 : (glyphData[var0 & 65535] & 0xFF) + var1;
   }

   private static short glyphIndex(short var0, int var1) {
      if (var1 == 0) {
         return chunkStringCounts[glyphWidth(languageChunkGroups, var0, 0, languageChunkGroups.length - 1)];
      } else {
         return var0 >= 128 && var1 != languageEncodings[currentLanguage] ? -1 : chunkStringCounts[headerStringOffsets[var0] & '\uffff'];
      }
   }

   public static short[] wrapText(SdkString var0, short[] var1, int var2, short var3) {
      return wrapTextImpl(var0, 0, var0.length, var1, var2, var3);
   }

   private static short[] wrapTextImpl(SdkString var0, int var1, int var2, short[] var3, int var4, short var5) {
      if (font == null && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "wrapSubString ::Font is null, use setFont(Object)", 4);
      }

      boolean var6 = false;
      if (var3 == null) {
         if (glyphOffsetTable == null) {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "wrapSubString ::allocating initial _wrapOffsets[] with size: " + 50, 1);
            }

            glyphOffsetTable = new short[50];
         }

         var3 = glyphOffsetTable;
         var6 = true;
      }

      int var7 = var0.encoding();
      int var8 = 0;
      int var9 = 0;
      int var10 = -1;
      var3[1] = (short)var1;
      int var11 = 2;

      for (int var12 = var1; var12 < var2 + var1; var12++) {
         short var13 = var0.charCodeAt(var12);
         if (var11 + 3 >= var3.length && var6) {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "wrapSubString ::_wrapOffsets[] is growing to size: " + (var3.length + 50), 1);
            }

            glyphOffsetTable = new short[var3.length + 50];
            System.arraycopy(var3, 0, glyphOffsetTable, 0, var3.length);
            var3 = glyphOffsetTable;
         }

         if (var13 == var5) {
            var3[var11++] = (short)(var12 + 1);
            var8 = 0;
            var9 = 0;
            var10 = -1;
         } else {
            int var14 = 0;
            if ((fontHeader[10] & 4) != 0 && com.ea.sims3.SdkFeatures.isSystemFontEnabled()) {
               var14 = ((Font)font).charWidth(var0.charAt(var12));
            } else if (com.ea.sims3.SdkFeatures.isRectangleFontEnabled() || com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
               var14 = glyphHeight(glyphIndex(var13, var7));
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
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         for (int var15 = 0; var15 <= var11 + 1; var15++) {
            log(logPrefix + "wrapSubString::offsets[" + var15 + "] = " + (var3[var15] & 65535), 1);
         }
      }

      if (var6) {
         short[] var16 = new short[var11 + 2];
         System.arraycopy(var3, 0, var16, 0, var16.length);
         var3 = var16;
      }

      return var3;
   }

   public static int stringWidth(SdkString var0) {
      return substringWidth(var0, 0, var0.length);
   }

   public static int substringWidth(SdkString var0, int var1, int var2) {
      if (font == null && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getSubStringSize::no font loaded, use setFont(Object)", 4);
      }

      int var3 = 0;
      if ((fontHeader[10] & 4) != 0 && com.ea.sims3.SdkFeatures.isSystemFontEnabled()) {
         var3 = ((Font)font).substringWidth(var0.toString(), var1, var2);
      } else if (com.ea.sims3.SdkFeatures.isRectangleFontEnabled() || com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
         int var4 = var0.encoding();

         for (int var5 = var1; var5 < var1 + var2; var5++) {
            var3 += glyphHeight(glyphIndex(var0.charCodeAt(var5), var4));
         }
      }

      return var3;
   }

   private static boolean isChunkLoaded(int var0) {
      byte var1 = chunkGroupOfId[var0];
      return strings[var1] != null && chunkOwners[var1] == var0;
   }

   public static int loadStringsChunk(int var0) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (currentLanguage == -1) {
            log(logPrefix + "loadStringsChunk::language must be selected first!", 4);
         }

         if (var0 < 1 || var0 >= chunkGroupOfId.length) {
            log(logPrefix + "loadStringsChunk::chunkID '" + var0 + "' is out of bounds, valid range is [1," + chunkGroupOfId.length + "] inclusive", 4);
         }

         if (midlet == null) {
            log(logPrefix + "loadStringsChunk::midlet is null, use setMidlet(MIDlet)", 4);
         }
      }

      if (isChunkLoaded(var0) && !changingLanguage) {
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "loadStringsChunk::chunkID: " + var0 + " already loaded, it won't be loaded again", 3);
         }

         return chunkStringCount[var0];
      } else {
         InputStream var1 = null;

         try {
            byte var2 = (chunkFlags[var0] & 64) == 0 ? currentLanguage : 0;
            if (chunkConfig == 0) {
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadStringsChunk::chunkID: " + var0 + " is being loaded using CHUNK_CONFIG_CONCAT for resIndex: " + var2, 1);
               }

               var1 = midlet.getClass().getResourceAsStream("/t_" + languageCodes[var2]);

               for (int var3 = 1; var3 < var0; var3++) {
                  if ((chunkFlags[var3] & 64) == 0 || var2 == 0) {
                     skip(var1, (long)((chunkStringCount[var3] + 1 << 1) + (storageChunkOffsets[var2][var3] & '\uffff')));
                  }
               }
            } else if (chunkConfig == 1) {
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadStringsChunk::chunkID: " + var0 + " is being loaded using CHUNK_CONFIG_SEPARATE for resIndex: " + var2, 1);
               }

               var1 = midlet.getClass().getResourceAsStream("/t_" + languageCodes[var2] + var0);
            } else if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadStringsChunk::illegal value of chunk config: " + chunkConfig, 4);
            }

            byte var6 = chunkGroupOfId[var0];
            if (chunkStringOffsets[var6] == null) {
               chunkStringOffsets[var6] = new short[chunkOffsetCount[var6]];
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadStringsChunk::allocating _chunkStringOffsets[" + var6 + "] to be of size: " + chunkOffsetCount[var6], 1);
               }
            }

            for (int var4 = 0; var4 < chunkStringCount[var0] + 1; var4++) {
               tempShort[0] = (byte)var1.read();
               tempShort[1] = (byte)var1.read();
               chunkStringOffsets[var6][var4] = readShort(tempShort, 0);
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadStringsChunk::_chunkStringOffsets[" + var6 + "][" + var4 + "]: " + (chunkStringOffsets[var6][var4] & 65535), 1);
               }
            }

            if (strings[var6] == null) {
               strings[var6] = new byte[chunkDataSize[var6] & '\uffff'];
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadStringsChunk::allocating _chunkStringData[" + var6 + "] to be of size: " + (chunkDataSize[var6] & 65535), 1);
               }
            }

            readFully(var1, strings[var6], 0, storageChunkOffsets[var2][var0] & '\uffff');
            chunkOwners[var6] = (byte)var0;
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadStringsChunk::_groupMap[" + var6 + "]: " + var0, 1);
            }

            var1.close();
         } catch (IOException var5) {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadStringsChunk::IOException, e.msg: " + var5.getMessage(), 4);
            }
         }

         return chunkStringCount[var0];
      }
   }

   public static void freeStringsChunk(int var0) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "freeStringsChunk::freeing chunkID: " + var0, 1);
      }

      if (var0 > 0) {
         strings[chunkGroupOfId[var0]] = null;
         chunkStringOffsets[chunkGroupOfId[var0]] = null;
      } else {
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "freeStringsChunk::you are not allowed to free chunkID: " + var0, 3);
         }
      }
   }

   public static int loadTextHeader(byte[] var0) {
      int var1 = 0;
      if (midlet == null) {
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "loadTextHeader::the MIDlet must be set first, use setMidlet(MIDlet)", 4);
         }

         return 0;
      } else {
         var1++;
         byte var2;
         if ((var2 = var0[0]) != 1) {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadTextHeader::unsupported text header format:" + var2, 4);
            }

            return 0;
         } else {
            var1++;
            chunkConfig = var0[1];
            var1++;
            int var10000 = (var0[2] & 255) << 16;
            var1++;
            var10000 |= (var0[3] & 255) << 8;
            var1++;
            numFontGlyphs = var10000 | var0[4] & 255;
            var2 = (byte)readShort(var0, 5);
            var1 += 2;
            var1++;
            byte var3 = var0[7];
            var1++;
            short var4 = var0[8];
            var1++;
            short var5 = var0[9];
            int var6 = var4 + 1;
            int var7 = var5 + 1;
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadTextHeader::_chunkConfig = " + chunkConfig, 1);
               log(logPrefix + "loadTextHeader::_textEncodings = " + numFontGlyphs, 1);
               log(logPrefix + "loadTextHeader::numHeaderStrings = " + var2, 1);
               log(logPrefix + "loadTextHeader::numLangs = " + var3, 1);
               log(logPrefix + "loadTextHeader::numGroups = " + var4, 1);
               log(logPrefix + "loadTextHeader::numChunks = " + var5, 1);
               log(logPrefix + "loadTextHeader::numStorageChunks = " + var6, 1);
               log(logPrefix + "loadTextHeader::numActualChunks = " + var7, 1);
            }

            languageEncodings = new byte[var3];
            languageCodes = new String[var3];
            fontDifferentiators = new byte[var3];
            chunkStringCount = new short[var7];
            chunkFlags = new byte[var7];
            chunkGroupOfId = new byte[var7];
            storageChunkOffsets = new short[var3][var7];
            chunkDataSize = new short[var7];
            chunkOffsetCount = new short[var7];
            chunkOwners = new byte[var7];
            strings = new byte[var6][];
            chunkStringOffsets = new short[var6][];
            chunkStringCount[0] = var2;
            var4 = (short)(var2 + 1);

            for (int var22 = 0; var22 < var3; var22++) {
               languageCodes[var22] = new String(var0, var1, 5).trim();
               var1 += 5;
               languageEncodings[var22] = var0[var1++];
               fontDifferentiators[var22] = var0[var1++];
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadTextHeader::_languageISOCodes[" + var22 + "]: " + languageCodes[var22], 1);
                  log(logPrefix + "loadTextHeader::_languageEncodings[" + var22 + "]: " + languageEncodings[var22], 1);
                  log(logPrefix + "loadTextHeader::_languageFontDifferentiator[" + var22 + "]: " + fontDifferentiators[var22], 1);
               }

               if (var2 > 0) {
                  if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                     log(logPrefix + "loadTextHeader::reading header string offsets for lang: " + var22, 1);
                  }

                  if (chunkStringOffsets[0] == null) {
                     chunkStringOffsets[0] = new short[var3 * var4];
                  }

                  for (int var25 = 0; var25 < var4; var25++) {
                     chunkStringOffsets[0][var22 * var4 + var25] = readShort(var0, var1);
                     if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                        log(
                           logPrefix
                              + "loadTextHeader::_chunkStringOffsets[HEADER_CHUNK_INDEX]["
                              + (var22 * var4 + var25)
                              + "]: "
                              + (chunkStringOffsets[0][var22 * var4 + var25] & 65535),
                           1
                        );
                     }

                     var1 += 2;
                  }
               }
            }

            if (var2 > 0) {
               var5 = chunkStringOffsets[0][var3 * var4 - 1];
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadTextHeader::headerChunkLen = " + var5, 1);
               }

               strings[0] = new byte[var5];
               System.arraycopy(var0, var1, strings[0], 0, var5);
               var1 += var5;
            } else if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadTextHeader::skipping allocation and reading of header " + "string offsets and data because numHeaderStrings == 0", 1);
            }

            for (int var24 = 1; var24 < var7; var24++) {
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadTextHeader::reading data for chunk: " + var24, 1);
               }

               chunkFlags[var24] = (byte)(var0[var1] & 192);
               chunkGroupOfId[var24] = (byte)(var0[var1] & 63);
               chunkStringCount[var24] = readShort(var0, ++var1);
               var1 += 2;
               if (chunkStringCount[var24] + 1 > chunkOffsetCount[chunkGroupOfId[var24]]) {
                  chunkOffsetCount[chunkGroupOfId[var24]] = (short)(chunkStringCount[var24] + 1);
                  if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                     log(logPrefix + "loadTextHeader::setting _groupMaxNumOffsets[" + chunkGroupOfId[var24] + "]: " + chunkOffsetCount[chunkGroupOfId[var24]], 1);
                  }
               }

               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadTextHeader::_chunkFlags[" + var24 + "]: " + chunkFlags[var24], 1);
                  log(logPrefix + "loadTextHeader::_chunkGroup[" + var24 + "]: " + chunkGroupOfId[var24], 1);
                  log(logPrefix + "loadTextHeader:: _numStringsPerChunk[" + var24 + "]: " + chunkStringCount[var24], 1);
               }

               for (int var26 = 0; var26 < var3; var26++) {
                  storageChunkOffsets[var26][var24] = readShort(var0, var1);
                  if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                     log(logPrefix + "loadTextHeader::chunkSize[" + var26 + "][" + var24 + "]: " + (storageChunkOffsets[var26][var24] & 65535), 1);
                  }

                  if ((storageChunkOffsets[var26][var24] & '\uffff') > (chunkDataSize[chunkGroupOfId[var24]] & '\uffff')) {
                     chunkDataSize[chunkGroupOfId[var24]] = storageChunkOffsets[var26][var24];
                     if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                        log(logPrefix + "loadTextHeader::setting _groupMaxSize[" + chunkGroupOfId[var24] + "]: " + (chunkDataSize[chunkGroupOfId[var24]] & 65535), 1);
                     }
                  }

                  var1 += 2;
               }
            }

            if (languageEncodings[currentLanguage] > 1) {
               loadFontChunk(languageEncodings[currentLanguage]);
            }

            return var3;
         }
      }
   }

   public static SdkString getHeaderString(int var0, int var1, SdkString var2) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getHeaderString::getting stringID: " + var1 + " for language: " + var0, 1);
         if (var1 >= chunkStringCount[0]) {
            log(logPrefix + "getHeaderString::stringID: " + var1 + " is not in the header strings chunk, use getString(int,SDKString)", 4);
         }
      }

      var0 = var1 + (chunkStringCount[0] + 1) * var0;
      if (var2 == null) {
         var2 = new SdkString();
      }

      var2.data = strings[0];
      var2.offset = chunkStringOffsets[0][var0];
      var2.length = (short)(chunkStringOffsets[0][var0 + 1] - chunkStringOffsets[0][var0] >> 1);
      var2.flags = 0;
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getHeaderString::string.toString(): " + var2.toString(), 1);
         log(logPrefix + "getHeaderString::string.off: " + var2.offset, 1);
         log(logPrefix + "getHeaderString::string.len: " + var2.length, 1);
         log(logPrefix + "getHeaderString::string.encoding: " + var2.flags, 1);
      }

      return var2;
   }

   public static SdkString getString(int var0, SdkString var1) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getString::getting stringID: " + var0, 1);
         if (currentLanguage == -1) {
            log(logPrefix + "getString::language must be selected first!", 4);
         }
      }

      int var2 = -1;
      short var3 = 0;

      for (int var4 = 0; var4 < chunkStringCount.length; var4++) {
         var3 += chunkStringCount[var4];
         if (var0 < var3) {
            var2 = var4;
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "getString::found stringID: " + var0 + " in chunkID: " + var4, 1);
            }
            break;
         }
      }

      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (var2 == -1) {
            log(logPrefix + "getString::failed to get the chunkID for stringID: " + var0, 4);
         } else if (var2 == 0) {
            log(logPrefix + "getString::use getHeaderString(int,int,SDKString) to load stringID: " + var0, 4);
         }
      }

      int var7 = var0 - (var3 - chunkStringCount[var2]);
      byte var5 = chunkGroupOfId[var2];
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getString::calculated first stringID for this chunk as: " + (var3 - chunkStringCount[var2]), 1);
         log(logPrefix + "getString::chunkOffset: " + var7, 1);
         log(logPrefix + "getString::groupID: " + var5, 1);
         if (strings[var5] == null) {
            log(logPrefix + "getString::first load chunkID: " + var2 + " before you can get stringID: " + var0, 4);
         }
      }

      if (var1 == null) {
         var1 = new SdkString();
      }

      var1.data = strings[var5];
      var1.offset = chunkStringOffsets[var5][var7] & '\uffff';
      var1.length = (short)((chunkStringOffsets[var5][var7 + 1] & '\uffff') - (chunkStringOffsets[var5][var7] & '\uffff'));
      byte var6 = languageEncodings[currentLanguage];
      if ((chunkFlags[var2] & -64) != 0) {
         var6 = 0;
      }

      if (var6 == 0) {
         var1.length = (short)(var1.length >> 1);
      }

      var1.flags = var6;
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getString::string.toString(): " + var1.toString(), 1);
         log(logPrefix + "getString::string.off: " + var1.offset, 1);
         log(logPrefix + "getString::string.len: " + var1.length, 1);
         log(logPrefix + "getString::string.encoding: " + var1.flags, 1);
      }

      return var1;
   }

   public static int getCurrentLanguage() {
      return currentLanguage;
   }

   public static boolean setCurrentLanguage(int var0) {
      byte var1 = currentLanguage;
      if (languageCodes != null && var0 >= 0 && var0 < languageCodes.length) {
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "setCurrentLanguage::setting _langIndex: " + var0, 1);
         }

         currentLanguage = (byte)var0;
      } else if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "setCurrentLanguage::invalid index: " + var0, 4);
      }

      if (currentLanguage != var1) {
         changingLanguage = true;

         for (int var2 = 1; var2 < chunkGroupOfId.length; var2++) {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "setCurrentLanguage::checking storage for chunkID: " + var2 + " ,groupID: " + chunkGroupOfId[var2], 1);
            }

            if (isChunkLoaded(var2) && (chunkFlags[var2] & 64) == 0) {
               loadStringsChunk(var2);
            }
         }

         changingLanguage = false;
         if (languageEncodings[currentLanguage] != languageEncodings[var1] && languageEncodings[currentLanguage] > 1) {
            loadFontChunk(languageEncodings[currentLanguage]);
         }
      }

      return currentLanguage == var0;
   }

   public static void setMidlet(MIDlet var0) {
      midlet = var0;
   }

   public static int chooseLanguage(int var0) {
      if (midlet == null && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "chooseLanguage::midlet instance not set. Call setMidlet() first!", 4);
      }

      if (languageCodes.length == 1) {
         return 0;
      } else {
         String var1 = midlet.getAppProperty("Force-Locale");
         String[] var2 = new String[languageCodes.length];
         System.arraycopy(languageCodes, 0, var2, 0, var2.length);
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
               var0 = detectLanguage(var2);
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
               var0 = detectLanguage(var2);
            }
         }

         return var0;
      }
   }

   public static int getFontDifferentiator(int var0) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled() && var0 < 0 || var0 >= fontDifferentiators.length) {
         log(logPrefix + "getFontDifferentiator::langIndex out of bounds", 4);
      }

      return fontDifferentiators[var0];
   }

   public static void setGraphics(Graphics var0) {
      graphics = null;
      graphics = var0;
      if (font != null && (fontHeader[10] & 4) != 0 && com.ea.sims3.SdkFeatures.isSystemFontEnabled()) {
         graphics.setFont((Font)font);
      }
   }

   public static void log(String var0, int var1) {
      if (var1 >= com.ea.sims3.SdkFeatures.configMode() && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (logBuffer == null) {
            logBuffer = new StringBuffer();
         }

         logBuffer.setLength(0);
         switch (var1) {
            case 1:
               logBuffer.append("I:");
               break;
            case 2:
               logBuffer.append("S:");
               break;
            case 3:
               logBuffer.append("W:");
               break;
            case 4:
               logBuffer.append("E:");
         }

         if (var1 >= 3) {
            Alert var2;
            (var2 = new Alert("", var0, null, AlertType.ERROR)).setTimeout(-2);
            Display.getDisplay(midlet).setCurrent(var2);
         }

         logBuffer.append(var0);
         System.out.println(logBuffer.toString());
      }
   }

   public static short readShort(byte[] var0, int var1) {
      return (short)((var0[var1] & 255) << 8 | var0[var1 + 1] & 255);
   }

   public static short toCharset(short var0) {
      int var1;
      if ((var1 = glyphWidth(glyphIndexTable, var0, 0, glyphIndexTable.length - 1)) == glyphIndexTable.length && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "utf2iso::character code: " + var0 + " is not defined in encoding: " + languageEncodings[currentLanguage], 3);
      }

      return (short)(var1 + 128);
   }

   public static short fromCharset(short var0) {
      if ((var0 < 128 || var0 > 255) && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "iso2utf::character code: " + var0 + " is out of bounds for this method (must be in the range 128-255)", 4);
      }

      return glyphIndexTable[var0 - 128];
   }

   private static byte detectLanguage(String[] var0) {
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

   private static void flushLog() {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "drawRegion::the function is called with short[] parameter on non-Nokia-API phone!", 4);
      }
   }

   private static void drawGlyph(Image var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      graphics.drawRegion(var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   private static int glyphWidth(short[] var0, int var1, int var2, int var3) {
      if (var2 > var3) {
         return var0.length;
      } else {
         int var4 = var2 + var3 >> 1;
         int var5 = var0[var4] & '\uffff';
         if (var1 == var5) {
            return var4;
         } else {
            return var1 < var5 ? glyphWidth(var0, var1, var2, var4 - 1) : glyphWidth(var0, var1, var4 + 1, var3);
         }
      }
   }

   private static void loadFontChunk(byte var0) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (var0 < 2) {
            log(logPrefix + "loadCharMap::encoding: " + var0 + " should not be searched in the charmap binary!", 4);
         } else {
            log(logPrefix + "loadCharMap::loading charmap for encoding: " + var0, 1);
         }
      }

      int var1 = 0;

      for (int var2 = 1; var2 < var0; var2++) {
         if ((numFontGlyphs & 1 << var2) != 0) {
            var1++;
         }
      }

      if (glyphIndexTable == null) {
         glyphIndexTable = new short[128];
      }

      InputStream var5 = null;

      try {
         skip(var5 = midlet.getClass().getResourceAsStream("/charmap"), (long)(var1 << 8));

         for (int var4 = 0; var4 < 128; var4++) {
            tempShort[0] = (byte)var5.read();
            tempShort[1] = (byte)var5.read();
            glyphIndexTable[var4] = readShort(tempShort, 0);
         }

         var5.close();
      } catch (IOException var3) {
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "loadCharMap::IOException, e.msg: " + var3.getMessage(), 4);
         }
      }

      if (font != null && (fontHeader[10] & 4) == 0 && (com.ea.sims3.SdkFeatures.isRectangleFontEnabled() || com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled())) {
         loadChunksForLanguage();
      }
   }

   private static void loadChunksForLanguage() {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "refreshISOOffsets::mapping offsets for encoding: " + languageEncodings[currentLanguage], 1);
      }

      for (short var0 = 128; var0 < 256; var0++) {
         headerStringOffsets[var0] = (short)glyphWidth(languageChunkGroups, fromCharset(var0), 0, languageChunkGroups.length - 1);
      }

      fontHeader[11] = languageEncodings[currentLanguage];
   }

   private static Object[] parseFontChunk(byte[] var0) {
      int var1 = 0;
      byte var2 = var0[0];
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled() && (var2 & 63) != 33) {
         log(logPrefix + "loadFontChunk::unsupported font version:" + var2, 4);
      }

      // var3/var4 are reused later in this method for ints
      boolean var3b = (var2 & 128) != 0;
      boolean var4b = (var2 & 64) != 0;
      if (var3b && !com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
         log(logPrefix + "loadFontChunk::detected rectangle font but support disabled, use SDKConfig to enable", 4);
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

      short var6 = readShort(var0, 8);
      var1 += 2;
      short var7 = readShort(var0, 10);
      var1 += 2;
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "loadFontChunk::fontVersion = " + var2, 1);
         log(logPrefix + "loadFontChunk::textDirection = " + var5[0], 1);
         log(logPrefix + "loadFontChunk::lineDirection = " + var5[1], 1);
         log(logPrefix + "loadFontChunk::leading = " + var5[2], 1);
         log(logPrefix + "loadFontChunk::ascent = " + var5[3], 1);
         log(logPrefix + "loadFontChunk::descent = " + var5[4], 1);
         log(logPrefix + "loadFontChunk::space space= " + var5[5], 1);
         log(logPrefix + "loadFontChunk::char space= " + var5[6], 1);
         log(logPrefix + "loadFontChunk::numChars = " + var6, 1);
         log(logPrefix + "loadFontChunk::numTotalGlyphs = " + var7, 1);
      }

      short[] var15 = new short[var6];

      for (int var8 = 0; var8 < var6; var8++) {
         var15[var8] = readShort(var0, var1);
         var1 += 2;
      }

      short[] var21 = new short[var6 + 1];

      for (int var9 = 0; var9 < var6; var9++) {
         var21[var9] = readShort(var0, var1);
         var1 += 2;
      }

      var21[var6] = -1;
      int var22 = 6;
      if (var3b && com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
         var22 = var4b ? 4 : 5;
      }

      int var4 = (var6 << 1) + var7 * var22;
      byte[] var20 = new byte[var4];
      System.arraycopy(var0, var1, var20, 0, var4);
      var1 += var4;
      int[] var19 = null;
      if (var3b && com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
         int var3 = var0[var1++];
         var19 = new int[var3];

         for (int var23 = 0; var23 < var3; var23++) {
            var19[var23] = (var0[var1] & 255) << 24 | (var0[var1 + 1] & 255) << 16 | (var0[var1 + 2] & 255) << 8 | var0[var1 + 3] & 255;
            var1 += 4;
         }
      }

      short[] var17 = new short[256];

      for (short var24 = 0; var24 < 128; var24++) {
         var17[var24] = (short)glyphWidth(var15, var24, 0, var6 - 1);
      }

      var5[11] = 1;
      return new Object[]{var5, var15, var17, var21, var20, var19};
   }

   private static int readFully(InputStream var0, byte[] var1, int var2, int var3) {
      try {
         return var0.read(var1, var2, var3);
      } catch (IOException var4) {
         throw new IllegalStateException(var4);
      }
   }

   private SdkText() {
   }

   static {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         logPrefix = "SDKUtils::";
      }
   }
}
