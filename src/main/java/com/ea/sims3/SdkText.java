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
   private static StringBuffer systemFontBuffer;
   private static boolean changingLanguage = false;
   public static MIDlet midlet;
   private static int textColor;
   private static StringBuffer logBuffer;

   private static Object createFont(Object fontData, byte[] data, int transform) {
      Object[] parsed = parseFontChunk(data);
      return new Object[]{fontData, parsed[0], parsed[1], parsed[2], parsed[3], parsed[4], parsed[5], new Integer(transform)};
   }

   public static Object createFont(Image image, byte[] data) {
      return createFont(image, data, -1);
   }

   // The retail build calls these directly; the checked exception is wrapped so the
   // decompiled source compiles (it never happens for classpath resources).
   private static long skip(InputStream stream, long count) {
      try {
         return stream.skip(count);
      } catch (IOException error) {
         throw new IllegalStateException(error);
      }
   }

   public static void setFont(Object fontData) {
      font = null;
      fontHeader = null;
      fontImage = null;
      languageChunkGroups = null;
      headerStringOffsets = null;
      chunkStringCounts = null;
      glyphData = null;
      colorTable = null;
      if (fontData != null) {
         if (fontData instanceof Font && com.ea.sims3.SdkFeatures.isSystemFontEnabled()) {
            (fontHeader = new byte[12])[0] = 1;
            fontHeader[1] = 2;
            fontHeader[7] = 0;
            fontHeader[8] = 1;
            fontHeader[9] = 2;
            fontHeader[10] = 4;
            if (graphics != null) {
               graphics.setFont((Font)fontData);
            }
         } else if (com.ea.sims3.SdkFeatures.isRectangleFontEnabled() || com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
            fontHeader = (byte[])((Object[])fontData)[1];
            fontImage = ((Object[])fontData)[0];
            usesTransform = (Integer)((Object[])fontData)[7];
            languageChunkGroups = (short[])((Object[])fontData)[2];
            headerStringOffsets = (short[])((Object[])fontData)[3];
            chunkStringCounts = (short[])((Object[])fontData)[4];
            glyphData = (byte[])((Object[])fontData)[5];
            colorTable = (int[])((Object[])fontData)[6];
            if (languageEncodings != null && fontHeader[11] != languageEncodings[currentLanguage] && languageEncodings[currentLanguage] > 1) {
               loadChunksForLanguage();
            }
         }
      }

      font = fontData;
   }

   public static void setSpaceSpacing(int spacing) {
      fontHeader[5] = (byte)spacing;
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

   public static void drawString(SdkString text, int x, int y, int anchor) {
      drawSubString(text, 0, text.length, x, y, anchor);
   }

   private static void advancePen(int[] position, int distance) {
      if (fontHeader[8] == 1) {
         position[0] += distance;
      } else if (fontHeader[8] == 4) {
         position[0] -= distance;
      } else if (fontHeader[8] == 2) {
         position[1] += distance;
      } else {
         if (fontHeader[8] == 8) {
            position[1] -= distance;
         }
      }
   }

   private static void drawSubString(SdkString text, int offset, int count, int x, int y, int anchor) {
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
         if (systemFontBuffer == null) {
            systemFontBuffer = new StringBuffer();
         }

         systemFontBuffer.setLength(0);

         for (int index = offset; index < offset + count; index++) {
            char codeUnit;
            if ((codeUnit = text.charAt(index)) != 173 || index == offset + count - 1) {
               systemFontBuffer.append(codeUnit);
            }
         }

         if (fontHeader[7] != 0 && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "drawSubString ::rendering of system fonts does not support transform: " + fontHeader[7], 4);
         }

         graphics.setColor(textColor);
         graphics.drawString(systemFontBuffer.toString(), x, y, anchor);
      } else {
         if (com.ea.sims3.SdkFeatures.isRectangleFontEnabled() || com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
            int ascent = fontHeader[3] & 255;
            int lineHeight = getLineHeight();
            int transform = fontHeader[8];
            int currentColor = fontHeader[9];
            boolean hasTransform = usesTransform != -1;
            boolean isFreeType = (fontHeader[10] & 2) != 0;
            boolean isPacked = (fontHeader[10] & -128) != 0;
            int anchorX = 0;
            int anchorY = 0;
            if ((anchor & RIGHT) != 0) {
               anchorX = 0 - substringWidth(text, offset, count);
            } else if ((anchor & HCENTER) != 0) {
               anchorX = 0 - (substringWidth(text, offset, count) >> 1);
            }

            if ((anchor & BASELINE) != 0) {
               anchorY = 0 - ascent;
            } else if ((anchor & BOTTOM) != 0) {
               anchorY = 0 - lineHeight;
            }

            if (transform == 1) {
               x += anchorX;
               y += anchorY;
            } else if (transform == 4) {
               x -= anchorX;
               y -= anchorY;
            } else if (transform == 2) {
               x -= anchorY;
               y += anchorX;
            } else if (transform == 8) {
               x -= anchorY;
               y -= anchorX;
            }

            penPosition[0] = x;
            penPosition[1] = y;
            if ((x = text.encoding()) != 1 && x != 0 && x != languageEncodings[currentLanguage] && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(
                  logPrefix
                     + "drawSubString::SDKString ["
                     + text.toString()
                     + "] has encoding="
                     + text.encoding()
                     + " but the currently set language has encoding: "
                     + languageEncodings[currentLanguage],
                  4
               );
            }

            byte boundsAnchorX = (byte)((transform & 5) != 0 ? (transform & 4) >> 1 : ((currentColor & 1) << 1) + ((~currentColor & 2) >> 1));
            byte boundsAnchorY = (byte)((transform & 10) != 0 ? (transform & 8) >> 2 : (currentColor & 2) + (~currentColor & 1));
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "drawSubString::offset = " + offset + " len = " + count + " \"" + text.toString() + "\"", 1);
               if (isFreeType && com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled() && fontHeader[7] != 0) {
                  log(logPrefix + "drawSubString ::rendering of rectangle fonts does not currently support transform: " + fontHeader[7], 4);
               }
            }

            currentColor = transform = graphics.getColor();

            for (int index = offset; index < offset + count; index++) {
               short charCode;
               if ((charCode = text.charCodeAt(index)) != 173 || index == offset + count - 1) {
                  short glyphOffset;
                  int glyphWidth = glyphHeight(glyphOffset = glyphIndex((short)charCode, x));
                  if (glyphOffset == -1) {
                     if (charCode != 32) {
                        if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                           log(logPrefix + "drawSubString::character not in Font: \\u" + (x != 0 && charCode >= 128 ? fromCharset((short)charCode) : charCode), 4);
                        }

                        graphics.setColor(65280);
                        graphics.fillRect(penPosition[0], penPosition[1], glyphWidth, lineHeight);
                        graphics.setColor(16711680);
                        currentColor = 16711680;
                        graphics.drawLine(penPosition[0], penPosition[1], penPosition[0] + glyphWidth, penPosition[1] + lineHeight);
                        graphics.drawLine(penPosition[0], penPosition[1] + lineHeight, penPosition[0] + glyphWidth, penPosition[1]);
                     }

                     advancePen(penPosition, glyphWidth);
                  } else {
                     short recordOffset = ++glyphOffset;
                     glyphOffset++;
                     charCode = (short)(glyphData[recordOffset] & 255);

                     for (int pixelIndex = 0; pixelIndex < charCode; pixelIndex++) {
                        if (isFreeType && com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
                           int colorIndex = glyphData[glyphOffset++] & 255;
                           byte glyphOffsetX = glyphData[glyphOffset++];
                           byte glyphOffsetY = glyphData[glyphOffset++];
                           int rectWidth;
                           int rectHeight;
                           if (isPacked) {
                              rectWidth = glyphData[glyphOffset] >> 4 & 15;
                              rectHeight = glyphData[glyphOffset++] & 15;
                           } else {
                              rectWidth = glyphData[glyphOffset++] & 255;
                              rectHeight = glyphData[glyphOffset++] & 255;
                           }

                           if (colorTable[colorIndex] != currentColor) {
                              graphics.setColor(colorTable[colorIndex]);
                              currentColor = colorTable[colorIndex];
                           }

                           graphics.fillRect(penPosition[0] + glyphOffsetX, penPosition[1] + ascent - glyphOffsetY, rectWidth, rectHeight);
                        }

                        if (!isFreeType && com.ea.sims3.SdkFeatures.isRectangleFontEnabled()) {
                           int sourceX = glyphData[glyphOffset++] & 255;
                           int sourceY = glyphData[glyphOffset++] & 255;
                           int drawX = glyphData[glyphOffset++];
                           int drawY = glyphData[glyphOffset++];
                           int srcWidth = glyphData[glyphOffset++] & 255;
                           int srcHeight = glyphData[glyphOffset++] & 255;
                           glyphBounds[0] = drawX;
                           glyphBounds[1] = drawY - ascent;
                           glyphBounds[2] = -drawX - srcWidth;
                           glyphBounds[3] = ascent - drawY - srcHeight;
                           drawX = penPosition[0] + glyphBounds[boundsAnchorX];
                           drawY = penPosition[1] + glyphBounds[boundsAnchorY];
                           if (hasTransform) {
                              flushLog();
                           } else {
                              drawGlyph((Image)fontImage, sourceX, sourceY, srcWidth, srcHeight, fontHeader[7], drawX, drawY, 0);
                           }
                        }
                     }

                     advancePen(penPosition, glyphWidth);
                  }
               }
            }

            graphics.setColor(transform);
         }
      }
   }

   public static void drawWrappedString(SdkString text, short[] wrapOffsets, int firstLine, int lineCount, int x, int y, int anchor) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (graphics == null) {
            log(logPrefix + "drawWrappedString::Graphics are null, use setGraphics(Graphics)", 4);
         }

         if (font == null) {
            log(logPrefix + "drawWrappedString::Font is null, use setFont(Object)", 4);
         }
      }

      int lineHeight = getLineHeight();
      int stepX = 0;
      int stepY = 0;
      int blockOffset = 0;
      if ((anchor & VCENTER) != 0) {
         blockOffset = wrapOffsets[0] * lineHeight - fontHeader[2] >> 1;
         anchor ^= VCENTER;
      } else if ((anchor & BOTTOM) != 0) {
         blockOffset = wrapOffsets[0] * lineHeight - fontHeader[2];
         anchor ^= BOTTOM;
      }

      anchor |= TOP;
      if (fontHeader[9] == 1) {
         x -= blockOffset;
         stepX = lineHeight;
      } else if (fontHeader[9] == 4) {
         x += blockOffset;
         stepX = -lineHeight;
      } else if (fontHeader[9] == 2) {
         y -= blockOffset;
         stepY = lineHeight;
      } else {
         y += blockOffset;
         stepY = -lineHeight;
      }

      short lineSeparator = wrapOffsets[(wrapOffsets[0] & '\uffff') + 2];

      for (int lineIndex = firstLine; lineIndex < firstLine + lineCount; lineIndex++) {
         int lineStart = wrapOffsets[lineIndex] & '\uffff';
         int lineLength = (wrapOffsets[lineIndex + 1] & '\uffff') - lineStart;
         short charCode;
         if ((charCode = text.charCodeAt((wrapOffsets[lineIndex + 1] & '\uffff') - 1)) == lineSeparator || charCode == 32) {
            lineLength--;
         }

         drawSubString(text, lineStart, lineLength, x, y, anchor);
         x += stepX;
         y += stepY;
      }
   }

   private static int glyphHeight(short glyphOffset) {
      int extraHeight = fontHeader[6] & 255;
      return glyphOffset == -1 ? (fontHeader[5] & 0xFF) + extraHeight : (glyphData[glyphOffset & 65535] & 0xFF) + extraHeight;
   }

   private static short glyphIndex(short charCode, int encoding) {
      if (encoding == 0) {
         return chunkStringCounts[glyphWidth(languageChunkGroups, charCode, 0, languageChunkGroups.length - 1)];
      } else {
         return charCode >= 128 && encoding != languageEncodings[currentLanguage] ? -1 : chunkStringCounts[headerStringOffsets[charCode] & '\uffff'];
      }
   }

   public static short[] wrapText(SdkString text, short[] offsets, int maxWidth, short lineSeparator) {
      return wrapTextImpl(text, 0, text.length, offsets, maxWidth, lineSeparator);
   }

   private static short[] wrapTextImpl(SdkString text, int offset, int count, short[] offsets, int maxWidth, short lineSeparator) {
      if (font == null && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "wrapSubString ::Font is null, use setFont(Object)", 4);
      }

      boolean ownsOffsets = false;
      if (offsets == null) {
         if (glyphOffsetTable == null) {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "wrapSubString ::allocating initial _wrapOffsets[] with size: " + 50, 1);
            }

            glyphOffsetTable = new short[50];
         }

         offsets = glyphOffsetTable;
         ownsOffsets = true;
      }

      int encoding = text.encoding();
      int lineWidth = 0;
      int wordWidth = 0;
      int wordStart = -1;
      offsets[1] = (short)offset;
      int writeIndex = 2;

      for (int charIndex = offset; charIndex < count + offset; charIndex++) {
         short charCode = text.charCodeAt(charIndex);
         if (writeIndex + 3 >= offsets.length && ownsOffsets) {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "wrapSubString ::_wrapOffsets[] is growing to size: " + (offsets.length + 50), 1);
            }

            glyphOffsetTable = new short[offsets.length + 50];
            System.arraycopy(offsets, 0, glyphOffsetTable, 0, offsets.length);
            offsets = glyphOffsetTable;
         }

         if (charCode == lineSeparator) {
            offsets[writeIndex++] = (short)(charIndex + 1);
            lineWidth = 0;
            wordWidth = 0;
            wordStart = -1;
         } else {
            int charWidth = 0;
            if ((fontHeader[10] & 4) != 0 && com.ea.sims3.SdkFeatures.isSystemFontEnabled()) {
               charWidth = ((Font)font).charWidth(text.charAt(charIndex));
            } else if (com.ea.sims3.SdkFeatures.isRectangleFontEnabled() || com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
               charWidth = glyphHeight(glyphIndex(charCode, encoding));
            }

            lineWidth += charWidth;
            wordWidth += charWidth;
            if (lineWidth > maxWidth) {
               if (wordStart == -1) {
                  offsets[writeIndex++] = (short)charIndex;
                  lineWidth = charWidth;
                  wordWidth = 0;
               } else {
                  offsets[writeIndex++] = (short)(wordStart + 1);
                  if (charCode != 32 && charCode != 173) {
                     wordStart = -1;
                  } else {
                     wordStart = charIndex;
                  }

                  lineWidth = wordWidth;
                  wordWidth = 0;
               }
            } else if (charCode == 32 || charCode == 173) {
               wordStart = charIndex;
               wordWidth = 0;
            }
         }
      }

      offsets[0] = (short)(writeIndex - 1);
      offsets[writeIndex] = (short)(count + offset);
      offsets[writeIndex + 1] = lineSeparator;
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         for (int offsetIndex = 0; offsetIndex <= writeIndex + 1; offsetIndex++) {
            log(logPrefix + "wrapSubString::offsets[" + offsetIndex + "] = " + (offsets[offsetIndex] & 65535), 1);
         }
      }

      if (ownsOffsets) {
         short[] resized = new short[writeIndex + 2];
         System.arraycopy(offsets, 0, resized, 0, resized.length);
         offsets = resized;
      }

      return offsets;
   }

   public static int stringWidth(SdkString text) {
      return substringWidth(text, 0, text.length);
   }

   public static int substringWidth(SdkString text, int offset, int count) {
      if (font == null && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getSubStringSize::no font loaded, use setFont(Object)", 4);
      }

      int width = 0;
      if ((fontHeader[10] & 4) != 0 && com.ea.sims3.SdkFeatures.isSystemFontEnabled()) {
         width = ((Font)font).substringWidth(text.toString(), offset, count);
      } else if (com.ea.sims3.SdkFeatures.isRectangleFontEnabled() || com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
         int encoding = text.encoding();

         for (int index = offset; index < offset + count; index++) {
            width += glyphHeight(glyphIndex(text.charCodeAt(index), encoding));
         }
      }

      return width;
   }

   private static boolean isChunkLoaded(int chunkId) {
      byte groupId = chunkGroupOfId[chunkId];
      return strings[groupId] != null && chunkOwners[groupId] == chunkId;
   }

   public static int loadStringsChunk(int chunkId) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (currentLanguage == -1) {
            log(logPrefix + "loadStringsChunk::language must be selected first!", 4);
         }

         if (chunkId < 1 || chunkId >= chunkGroupOfId.length) {
            log(logPrefix + "loadStringsChunk::chunkID '" + chunkId + "' is out of bounds, valid range is [1," + chunkGroupOfId.length + "] inclusive", 4);
         }

         if (midlet == null) {
            log(logPrefix + "loadStringsChunk::midlet is null, use setMidlet(MIDlet)", 4);
         }
      }

      if (isChunkLoaded(chunkId) && !changingLanguage) {
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "loadStringsChunk::chunkID: " + chunkId + " already loaded, it won't be loaded again", 3);
         }

         return chunkStringCount[chunkId];
      } else {
         InputStream stream = null;

         try {
            byte resourceIndex = (chunkFlags[chunkId] & 64) == 0 ? currentLanguage : 0;
            if (chunkConfig == 0) {
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadStringsChunk::chunkID: " + chunkId + " is being loaded using CHUNK_CONFIG_CONCAT for resIndex: " + resourceIndex, 1);
               }

               stream = midlet.getClass().getResourceAsStream("/t_" + languageCodes[resourceIndex]);

               for (int index = 1; index < chunkId; index++) {
                  if ((chunkFlags[index] & 64) == 0 || resourceIndex == 0) {
                     skip(stream, (long)((chunkStringCount[index] + 1 << 1) + (storageChunkOffsets[resourceIndex][index] & '\uffff')));
                  }
               }
            } else if (chunkConfig == 1) {
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadStringsChunk::chunkID: " + chunkId + " is being loaded using CHUNK_CONFIG_SEPARATE for resIndex: " + resourceIndex, 1);
               }

               stream = midlet.getClass().getResourceAsStream("/t_" + languageCodes[resourceIndex] + chunkId);
            } else if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadStringsChunk::illegal value of chunk config: " + chunkConfig, 4);
            }

            byte groupId = chunkGroupOfId[chunkId];
            if (chunkStringOffsets[groupId] == null) {
               chunkStringOffsets[groupId] = new short[chunkOffsetCount[groupId]];
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadStringsChunk::allocating _chunkStringOffsets[" + groupId + "] to be of size: " + chunkOffsetCount[groupId], 1);
               }
            }

            for (int stringIndex = 0; stringIndex < chunkStringCount[chunkId] + 1; stringIndex++) {
               tempShort[0] = (byte)stream.read();
               tempShort[1] = (byte)stream.read();
               chunkStringOffsets[groupId][stringIndex] = readShort(tempShort, 0);
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadStringsChunk::_chunkStringOffsets[" + groupId + "][" + stringIndex + "]: " + (chunkStringOffsets[groupId][stringIndex] & 65535), 1);
               }
            }

            if (strings[groupId] == null) {
               strings[groupId] = new byte[chunkDataSize[groupId] & '\uffff'];
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadStringsChunk::allocating _chunkStringData[" + groupId + "] to be of size: " + (chunkDataSize[groupId] & 65535), 1);
               }
            }

            readFully(stream, strings[groupId], 0, storageChunkOffsets[resourceIndex][chunkId] & '\uffff');
            chunkOwners[groupId] = (byte)chunkId;
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadStringsChunk::_groupMap[" + groupId + "]: " + chunkId, 1);
            }

            stream.close();
         } catch (IOException error) {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadStringsChunk::IOException, e.msg: " + error.getMessage(), 4);
            }
         }

         return chunkStringCount[chunkId];
      }
   }

   public static void freeStringsChunk(int chunkId) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "freeStringsChunk::freeing chunkID: " + chunkId, 1);
      }

      if (chunkId > 0) {
         strings[chunkGroupOfId[chunkId]] = null;
         chunkStringOffsets[chunkGroupOfId[chunkId]] = null;
      } else {
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "freeStringsChunk::you are not allowed to free chunkID: " + chunkId, 3);
         }
      }
   }

   public static int loadTextHeader(byte[] data) {
      int offset = 0;
      if (midlet == null) {
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "loadTextHeader::the MIDlet must be set first, use setMidlet(MIDlet)", 4);
         }

         return 0;
      } else {
         offset++;
         byte headerStringCount;
         if ((headerStringCount = data[0]) != 1) {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadTextHeader::unsupported text header format:" + headerStringCount, 4);
            }

            return 0;
         } else {
            offset++;
            chunkConfig = data[1];
            offset++;
            int headerValue = (data[2] & 255) << 16;
            offset++;
            headerValue |= (data[3] & 255) << 8;
            offset++;
            numFontGlyphs = headerValue | data[4] & 255;
            headerStringCount = (byte)readShort(data, 5);
            offset += 2;
            offset++;
            byte languageCount = data[7];
            offset++;
            short groupSize = data[8];
            offset++;
            short chunkCount = data[9];
            int storageGroupCount = groupSize + 1;
            int chunkTotal = chunkCount + 1;
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadTextHeader::_chunkConfig = " + chunkConfig, 1);
               log(logPrefix + "loadTextHeader::_textEncodings = " + numFontGlyphs, 1);
               log(logPrefix + "loadTextHeader::numHeaderStrings = " + headerStringCount, 1);
               log(logPrefix + "loadTextHeader::numLangs = " + languageCount, 1);
               log(logPrefix + "loadTextHeader::numGroups = " + groupSize, 1);
               log(logPrefix + "loadTextHeader::numChunks = " + chunkCount, 1);
               log(logPrefix + "loadTextHeader::numStorageChunks = " + storageGroupCount, 1);
               log(logPrefix + "loadTextHeader::numActualChunks = " + chunkTotal, 1);
            }

            languageEncodings = new byte[languageCount];
            languageCodes = new String[languageCount];
            fontDifferentiators = new byte[languageCount];
            chunkStringCount = new short[chunkTotal];
            chunkFlags = new byte[chunkTotal];
            chunkGroupOfId = new byte[chunkTotal];
            storageChunkOffsets = new short[languageCount][chunkTotal];
            chunkDataSize = new short[chunkTotal];
            chunkOffsetCount = new short[chunkTotal];
            chunkOwners = new byte[chunkTotal];
            strings = new byte[storageGroupCount][];
            chunkStringOffsets = new short[storageGroupCount][];
            chunkStringCount[0] = headerStringCount;
            groupSize = (short)(headerStringCount + 1);

            for (int languageIndex = 0; languageIndex < languageCount; languageIndex++) {
               languageCodes[languageIndex] = new String(data, offset, 5).trim();
               offset += 5;
               languageEncodings[languageIndex] = data[offset++];
               fontDifferentiators[languageIndex] = data[offset++];
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadTextHeader::_languageISOCodes[" + languageIndex + "]: " + languageCodes[languageIndex], 1);
                  log(logPrefix + "loadTextHeader::_languageEncodings[" + languageIndex + "]: " + languageEncodings[languageIndex], 1);
                  log(logPrefix + "loadTextHeader::_languageFontDifferentiator[" + languageIndex + "]: " + fontDifferentiators[languageIndex], 1);
               }

               if (headerStringCount > 0) {
                  if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                     log(logPrefix + "loadTextHeader::reading header string offsets for lang: " + languageIndex, 1);
                  }

                  if (chunkStringOffsets[0] == null) {
                     chunkStringOffsets[0] = new short[languageCount * groupSize];
                  }

                  for (int headerIndex = 0; headerIndex < groupSize; headerIndex++) {
                     chunkStringOffsets[0][languageIndex * groupSize + headerIndex] = readShort(data, offset);
                     if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                        log(
                           logPrefix
                              + "loadTextHeader::_chunkStringOffsets[HEADER_CHUNK_INDEX]["
                              + (languageIndex * groupSize + headerIndex)
                              + "]: "
                              + (chunkStringOffsets[0][languageIndex * groupSize + headerIndex] & 65535),
                           1
                        );
                     }

                     offset += 2;
                  }
               }
            }

            if (headerStringCount > 0) {
               chunkCount = chunkStringOffsets[0][languageCount * groupSize - 1];
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadTextHeader::headerChunkLen = " + chunkCount, 1);
               }

               strings[0] = new byte[chunkCount];
               System.arraycopy(data, offset, strings[0], 0, chunkCount);
               offset += chunkCount;
            } else if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "loadTextHeader::skipping allocation and reading of header " + "string offsets and data because numHeaderStrings == 0", 1);
            }

            for (int chunkId = 1; chunkId < chunkTotal; chunkId++) {
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadTextHeader::reading data for chunk: " + chunkId, 1);
               }

               chunkFlags[chunkId] = (byte)(data[offset] & 192);
               chunkGroupOfId[chunkId] = (byte)(data[offset] & 63);
               chunkStringCount[chunkId] = readShort(data, ++offset);
               offset += 2;
               if (chunkStringCount[chunkId] + 1 > chunkOffsetCount[chunkGroupOfId[chunkId]]) {
                  chunkOffsetCount[chunkGroupOfId[chunkId]] = (short)(chunkStringCount[chunkId] + 1);
                  if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                     log(logPrefix + "loadTextHeader::setting _groupMaxNumOffsets[" + chunkGroupOfId[chunkId] + "]: " + chunkOffsetCount[chunkGroupOfId[chunkId]], 1);
                  }
               }

               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  log(logPrefix + "loadTextHeader::_chunkFlags[" + chunkId + "]: " + chunkFlags[chunkId], 1);
                  log(logPrefix + "loadTextHeader::_chunkGroup[" + chunkId + "]: " + chunkGroupOfId[chunkId], 1);
                  log(logPrefix + "loadTextHeader:: _numStringsPerChunk[" + chunkId + "]: " + chunkStringCount[chunkId], 1);
               }

               for (int languageIndex = 0; languageIndex < languageCount; languageIndex++) {
                  storageChunkOffsets[languageIndex][chunkId] = readShort(data, offset);
                  if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                     log(logPrefix + "loadTextHeader::chunkSize[" + languageIndex + "][" + chunkId + "]: " + (storageChunkOffsets[languageIndex][chunkId] & 65535), 1);
                  }

                  if ((storageChunkOffsets[languageIndex][chunkId] & '\uffff') > (chunkDataSize[chunkGroupOfId[chunkId]] & '\uffff')) {
                     chunkDataSize[chunkGroupOfId[chunkId]] = storageChunkOffsets[languageIndex][chunkId];
                     if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                        log(logPrefix + "loadTextHeader::setting _groupMaxSize[" + chunkGroupOfId[chunkId] + "]: " + (chunkDataSize[chunkGroupOfId[chunkId]] & 65535), 1);
                     }
                  }

                  offset += 2;
               }
            }

            if (languageEncodings[currentLanguage] > 1) {
               loadFontChunk(languageEncodings[currentLanguage]);
            }

            return languageCount;
         }
      }
   }

   public static SdkString getHeaderString(int languageIndex, int stringId, SdkString result) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getHeaderString::getting stringID: " + stringId + " for language: " + languageIndex, 1);
         if (stringId >= chunkStringCount[0]) {
            log(logPrefix + "getHeaderString::stringID: " + stringId + " is not in the header strings chunk, use getString(int,SDKString)", 4);
         }
      }

      languageIndex = stringId + (chunkStringCount[0] + 1) * languageIndex;
      if (result == null) {
         result = new SdkString();
      }

      result.data = strings[0];
      result.offset = chunkStringOffsets[0][languageIndex];
      result.length = (short)(chunkStringOffsets[0][languageIndex + 1] - chunkStringOffsets[0][languageIndex] >> 1);
      result.flags = 0;
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getHeaderString::string.toString(): " + result.toString(), 1);
         log(logPrefix + "getHeaderString::string.off: " + result.offset, 1);
         log(logPrefix + "getHeaderString::string.len: " + result.length, 1);
         log(logPrefix + "getHeaderString::string.encoding: " + result.flags, 1);
      }

      return result;
   }

   public static SdkString getString(int stringId, SdkString result) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getString::getting stringID: " + stringId, 1);
         if (currentLanguage == -1) {
            log(logPrefix + "getString::language must be selected first!", 4);
         }
      }

      int chunkId = -1;
      short cumulative = 0;

      for (int index = 0; index < chunkStringCount.length; index++) {
         cumulative += chunkStringCount[index];
         if (stringId < cumulative) {
            chunkId = index;
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "getString::found stringID: " + stringId + " in chunkID: " + index, 1);
            }
            break;
         }
      }

      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (chunkId == -1) {
            log(logPrefix + "getString::failed to get the chunkID for stringID: " + stringId, 4);
         } else if (chunkId == 0) {
            log(logPrefix + "getString::use getHeaderString(int,int,SDKString) to load stringID: " + stringId, 4);
         }
      }

      int chunkOffset = stringId - (cumulative - chunkStringCount[chunkId]);
      byte groupId = chunkGroupOfId[chunkId];
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getString::calculated first stringID for this chunk as: " + (cumulative - chunkStringCount[chunkId]), 1);
         log(logPrefix + "getString::chunkOffset: " + chunkOffset, 1);
         log(logPrefix + "getString::groupID: " + groupId, 1);
         if (strings[groupId] == null) {
            log(logPrefix + "getString::first load chunkID: " + chunkId + " before you can get stringID: " + stringId, 4);
         }
      }

      if (result == null) {
         result = new SdkString();
      }

      result.data = strings[groupId];
      result.offset = chunkStringOffsets[groupId][chunkOffset] & '\uffff';
      result.length = (short)((chunkStringOffsets[groupId][chunkOffset + 1] & '\uffff') - (chunkStringOffsets[groupId][chunkOffset] & '\uffff'));
      byte encoding = languageEncodings[currentLanguage];
      if ((chunkFlags[chunkId] & -64) != 0) {
         encoding = 0;
      }

      if (encoding == 0) {
         result.length = (short)(result.length >> 1);
      }

      result.flags = encoding;
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "getString::string.toString(): " + result.toString(), 1);
         log(logPrefix + "getString::string.off: " + result.offset, 1);
         log(logPrefix + "getString::string.len: " + result.length, 1);
         log(logPrefix + "getString::string.encoding: " + result.flags, 1);
      }

      return result;
   }

   public static int getCurrentLanguage() {
      return currentLanguage;
   }

   public static boolean setCurrentLanguage(int languageIndex) {
      byte previousLanguage = currentLanguage;
      if (languageCodes != null && languageIndex >= 0 && languageIndex < languageCodes.length) {
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "setCurrentLanguage::setting _langIndex: " + languageIndex, 1);
         }

         currentLanguage = (byte)languageIndex;
      } else if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "setCurrentLanguage::invalid index: " + languageIndex, 4);
      }

      if (currentLanguage != previousLanguage) {
         changingLanguage = true;

         for (int chunkId = 1; chunkId < chunkGroupOfId.length; chunkId++) {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               log(logPrefix + "setCurrentLanguage::checking storage for chunkID: " + chunkId + " ,groupID: " + chunkGroupOfId[chunkId], 1);
            }

            if (isChunkLoaded(chunkId) && (chunkFlags[chunkId] & 64) == 0) {
               loadStringsChunk(chunkId);
            }
         }

         changingLanguage = false;
         if (languageEncodings[currentLanguage] != languageEncodings[previousLanguage] && languageEncodings[currentLanguage] > 1) {
            loadFontChunk(languageEncodings[currentLanguage]);
         }
      }

      return currentLanguage == languageIndex;
   }

   public static void setMidlet(MIDlet midletApp) {
      midlet = midletApp;
   }

   public static int chooseLanguage(int requested) {
      if (midlet == null && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "chooseLanguage::midlet instance not set. Call setMidlet() first!", 4);
      }

      if (languageCodes.length == 1) {
         return 0;
      } else {
         String forceLocale = midlet.getAppProperty("Force-Locale");
         String[] codes = new String[languageCodes.length];
         System.arraycopy(languageCodes, 0, codes, 0, codes.length);
         if (requested == -1) {
            byte mode = 0;
            if (forceLocale == null) {
               mode = 2;
            } else if ((forceLocale = forceLocale.toLowerCase().trim()).length() < 2) {
               mode = 2;
            } else if (forceLocale.equals("menu")) {
               mode = 1;
            } else if (forceLocale.startsWith("auto-")) {
               mode = 3;
               forceLocale = forceLocale.substring(5);
            } else {
               mode = 4;
            }

            for (int index = 0; index < codes.length; index++) {
               if (codes[index].length() > 2) {
                  String prefix = codes[index].substring(0, 2);
                  boolean unique = true;

                  for (int otherIndex = 0; otherIndex < codes.length; otherIndex++) {
                     if (index != otherIndex && codes[otherIndex].startsWith(prefix)) {
                        unique = false;
                        break;
                     }
                  }

                  if (unique) {
                     codes[index] = prefix;
                  }
               }
            }

            if (mode == 2 || mode == 3 || mode == 1) {
               requested = detectLanguage(codes);
               if (mode == 1 && requested > -1) {
                  requested = -1 - requested;
               }
            }

            if (mode == 4 || mode == 3 && requested == -1) {
               for (int matchIndex = 0; matchIndex < codes.length; matchIndex++) {
                  if (forceLocale.startsWith(codes[matchIndex])) {
                     requested = matchIndex;
                     break;
                  }
               }
            }

            if (mode == 4 && requested == -1) {
               requested = detectLanguage(codes);
            }
         }

         return requested;
      }
   }

   public static int getFontDifferentiator(int languageIndex) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled() && languageIndex < 0 || languageIndex >= fontDifferentiators.length) {
         log(logPrefix + "getFontDifferentiator::langIndex out of bounds", 4);
      }

      return fontDifferentiators[languageIndex];
   }

   public static void setGraphics(Graphics gfx) {
      graphics = null;
      graphics = gfx;
      if (font != null && (fontHeader[10] & 4) != 0 && com.ea.sims3.SdkFeatures.isSystemFontEnabled()) {
         graphics.setFont((Font)font);
      }
   }

   public static void log(String message, int severity) {
      if (severity >= com.ea.sims3.SdkFeatures.configMode() && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (logBuffer == null) {
            logBuffer = new StringBuffer();
         }

         logBuffer.setLength(0);
         switch (severity) {
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

         if (severity >= 3) {
            Alert alert;
            (alert = new Alert("", message, null, AlertType.ERROR)).setTimeout(-2);
            Display.getDisplay(midlet).setCurrent(alert);
         }

         logBuffer.append(message);
         System.out.println(logBuffer.toString());
      }
   }

   public static short readShort(byte[] data, int offset) {
      return (short)((data[offset] & 255) << 8 | data[offset + 1] & 255);
   }

   public static short toCharset(short charCode) {
      int index;
      if ((index = glyphWidth(glyphIndexTable, charCode, 0, glyphIndexTable.length - 1)) == glyphIndexTable.length && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "utf2iso::character code: " + charCode + " is not defined in encoding: " + languageEncodings[currentLanguage], 3);
      }

      return (short)(index + 128);
   }

   public static short fromCharset(short charCode) {
      if ((charCode < 128 || charCode > 255) && com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "iso2utf::character code: " + charCode + " is out of bounds for this method (must be in the range 128-255)", 4);
      }

      return glyphIndexTable[charCode - 128];
   }

   private static byte detectLanguage(String[] codes) {
      byte language = -1;
      String locale;
      if ((locale = System.getProperty("microedition.locale")) != null) {
         int localeLength;
         if ((localeLength = (locale = locale.toLowerCase().trim()).length()) < 2) {
            return -1;
         }

         for (int index = 0; index < codes.length; index++) {
            if (codes[index].length() > localeLength && codes[index].startsWith(locale)) {
               language = -1;
               break;
            }

            if (locale.startsWith(codes[index])) {
               language = (byte)index;
               break;
            }
         }
      }

      return language;
   }

   private static void flushLog() {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "drawRegion::the function is called with short[] parameter on non-Nokia-API phone!", 4);
      }
   }

   private static void drawGlyph(Image image, int sourceX, int sourceY, int width, int height, int transform, int x, int y, int anchor) {
      graphics.drawRegion(image, sourceX, sourceY, width, height, transform, x, y, anchor);
   }

   private static int glyphWidth(short[] table, int value, int low, int high) {
      if (low > high) {
         return table.length;
      } else {
         int middle = low + high >> 1;
         int middleValue = table[middle] & '\uffff';
         if (value == middleValue) {
            return middle;
         } else {
            return value < middleValue ? glyphWidth(table, value, low, middle - 1) : glyphWidth(table, value, middle + 1, high);
         }
      }
   }

   private static void loadFontChunk(byte encoding) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (encoding < 2) {
            log(logPrefix + "loadCharMap::encoding: " + encoding + " should not be searched in the charmap binary!", 4);
         } else {
            log(logPrefix + "loadCharMap::loading charmap for encoding: " + encoding, 1);
         }
      }

      int skippedCount = 0;

      for (int bit = 1; bit < encoding; bit++) {
         if ((numFontGlyphs & 1 << bit) != 0) {
            skippedCount++;
         }
      }

      if (glyphIndexTable == null) {
         glyphIndexTable = new short[128];
      }

      InputStream stream = null;

      try {
         skip(stream = midlet.getClass().getResourceAsStream("/charmap"), (long)(skippedCount << 8));

         for (int charIndex = 0; charIndex < 128; charIndex++) {
            tempShort[0] = (byte)stream.read();
            tempShort[1] = (byte)stream.read();
            glyphIndexTable[charIndex] = readShort(tempShort, 0);
         }

         stream.close();
      } catch (IOException error) {
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            log(logPrefix + "loadCharMap::IOException, e.msg: " + error.getMessage(), 4);
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

      for (short charCode = 128; charCode < 256; charCode++) {
         headerStringOffsets[charCode] = (short)glyphWidth(languageChunkGroups, fromCharset(charCode), 0, languageChunkGroups.length - 1);
      }

      fontHeader[11] = languageEncodings[currentLanguage];
   }

   private static Object[] parseFontChunk(byte[] data) {
      int offset = 0;
      byte fontVersion = data[0];
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled() && (fontVersion & 63) != 33) {
         log(logPrefix + "loadFontChunk::unsupported font version:" + fontVersion, 4);
      }

      boolean rectangleFont = (fontVersion & 128) != 0;
      boolean compactGlyphs = (fontVersion & 64) != 0;
      if (rectangleFont && !com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
         log(logPrefix + "loadFontChunk::detected rectangle font but support disabled, use SDKConfig to enable", 4);
      }

      byte[] header = new byte[12];
      System.arraycopy(data, 1, header, 0, 7);
      offset = (byte)8;
      header[7] = 0;
      header[8] = header[0];
      header[9] = header[1];
      if (rectangleFont) {
         header[10] = 2;
      } else {
         header[10] = 1;
      }

      if (compactGlyphs) {
         header[10] |= -128;
      }

      short charCount = readShort(data, 8);
      offset += 2;
      short totalGlyphs = readShort(data, 10);
      offset += 2;
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         log(logPrefix + "loadFontChunk::fontVersion = " + fontVersion, 1);
         log(logPrefix + "loadFontChunk::textDirection = " + header[0], 1);
         log(logPrefix + "loadFontChunk::lineDirection = " + header[1], 1);
         log(logPrefix + "loadFontChunk::leading = " + header[2], 1);
         log(logPrefix + "loadFontChunk::ascent = " + header[3], 1);
         log(logPrefix + "loadFontChunk::descent = " + header[4], 1);
         log(logPrefix + "loadFontChunk::space space= " + header[5], 1);
         log(logPrefix + "loadFontChunk::char space= " + header[6], 1);
         log(logPrefix + "loadFontChunk::numChars = " + charCount, 1);
         log(logPrefix + "loadFontChunk::numTotalGlyphs = " + totalGlyphs, 1);
      }

      short[] charIndexTable = new short[charCount];

      for (int index = 0; index < charCount; index++) {
         charIndexTable[index] = readShort(data, offset);
         offset += 2;
      }

      short[] glyphOffsets = new short[charCount + 1];

      for (int index = 0; index < charCount; index++) {
         glyphOffsets[index] = readShort(data, offset);
         offset += 2;
      }

      glyphOffsets[charCount] = -1;
      int glyphStride = 6;
      if (rectangleFont && com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
         glyphStride = compactGlyphs ? 4 : 5;
      }

      int dataSize = (charCount << 1) + totalGlyphs * glyphStride;
      byte[] glyphData = new byte[dataSize];
      System.arraycopy(data, offset, glyphData, 0, dataSize);
      offset += dataSize;
      int[] colorTable = null;
      if (rectangleFont && com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled()) {
         int colorCount = data[offset++];
         colorTable = new int[colorCount];

         for (int colorIndex = 0; colorIndex < colorCount; colorIndex++) {
            colorTable[colorIndex] = (data[offset] & 255) << 24 | (data[offset + 1] & 255) << 16 | (data[offset + 2] & 255) << 8 | data[offset + 3] & 255;
            offset += 4;
         }
      }

      short[] isoIndexes = new short[256];

      for (short charCode = 0; charCode < 128; charCode++) {
         isoIndexes[charCode] = (short)glyphWidth(charIndexTable, charCode, 0, charCount - 1);
      }

      header[11] = 1;
      return new Object[]{header, charIndexTable, isoIndexes, glyphOffsets, glyphData, colorTable};
   }

   private static int readFully(InputStream stream, byte[] buffer, int offset, int count) {
      try {
         return stream.read(buffer, offset, count);
      } catch (IOException error) {
         throw new IllegalStateException(error);
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
