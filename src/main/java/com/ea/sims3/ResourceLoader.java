package com.ea.sims3;

import java.io.DataInputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Image;

public final class ResourceLoader implements SpriteIndex, SoundIndex, Resources {
   private int[] crcTable;

   public ResourceLoader() {
      this.initCrcTable();
   }

   public static final int getSize(int index) {
      return Resources.b[index];
   }

   public static final String getName(int index) {
      return Resources.a[index];
   }

   public static Image loadImage(int index) {
      try {
         return Image.createImage(getName(index));
      } catch (Throwable ignored) {
         return null;
      }
   }

   public final InputStream openStream(int index) {
      return this.getClass().getResourceAsStream(getName(index));
   }

   public final byte[] loadSound(int index) {
      return this.loadBytes(SoundIndex.b[index]);
   }

   private byte[] loadBytes(int index) {
      int size;
      byte[] data = new byte[size = Resources.b[index]];

      try {
         DataInputStream in = new DataInputStream(this.openStream(index));
         in.readFully(data);
         return data;
      } catch (Exception exception) {
         return null;
      }
   }

   private static final int ubyte(byte value) {
      return 0xFF & value;
   }

   private static final int readInt(byte[] data, int offset) {
      int b0 = (data[offset + 0] & 255) << 24;
      int b1 = (data[offset + 1] & 255) << 16;
      int b2 = (data[offset + 2] & 255) << 8;
      int b3 = data[offset + 3] & 255;
      return b0 | b1 | b2 | b3;
   }

   private final void initCrcTable() {
      this.crcTable = new int[256];

      for (int i = 0; i <= 255; i++) {
         int crc = i;

         for (int bit = 8; bit > 0; bit--) {
            if ((crc & 1) == 1) {
               crc = crc >>> 1 ^ -306674912;
            } else {
               crc >>>= 1;
            }
         }

         this.crcTable[i] = crc;
      }
   }

   private int crc32(byte[] data, int offset, int length) {
      int crc = -1;

      for (int i = offset; i < offset + length; i++) {
         int index = 255 & data[i] ^ crc & 0xFF;
         crc = crc >>> 8 ^ this.crcTable[index];
      }

      return ~crc;
   }

   private static void replacePalette(byte[] data, int offset, int length, int[] fromColors, int[] toColors) {
      int count = fromColors.length;
      int pixelCount = length / 3;

      for (int i = 0; i < count; i++) {
         if (fromColors[i] != 16711935) {
            int fromColor;
            int fromR = ((fromColor = fromColors[i]) & 0xFF0000) >> 16;
            int fromG = (fromColor & 0xFF00) >> 8;
            int fromB = (fromColor & 0xFF) >> 0;
            int pos = offset;

            for (int p = 0; p < pixelCount; p++) {
               int r = data[pos + 0] & 255;
               int g = data[pos + 1] & 255;
               int b = data[pos + 2] & 255;
               if (r == fromR && g == fromG && b == fromB) {
                  int toR = (toColors[i] & 0xFF0000) >> 16;
                  int toG = (toColors[i] & 0xFF00) >> 8;
                  int toB = (toColors[i] & 0xFF) >> 0;
                  data[pos + 0] = (byte)(toR & 0xFF);
                  data[pos + 1] = (byte)(toG & 0xFF);
                  data[pos + 2] = (byte)(toB & 0xFF);
               }

               pos += 3;
            }
         }
      }
   }

   public final Image loadImageRecolored(int index, int[] fromColors, int[] toColors) {
      byte[] png;
      if (ubyte((png = this.loadBytes(index))[0]) == 137
         && ubyte(png[1]) == 80
         && ubyte(png[2]) == 78
         && ubyte(png[3]) == 71
         && ubyte(png[4]) == 13
         && ubyte(png[5]) == 10
         && ubyte(png[6]) == 26
         && ubyte(png[7]) == 10) {
         int chunkLength = readInt(png, 8);
         int chunkType = readInt(png, 12);
         byte bitDepth = 0;
         byte colorType = 0;
         if (chunkLength > 0 && chunkType == 1229472850) {
            bitDepth = png[24];
            colorType = png[25];
         }

         if ((bitDepth == 4 || bitDepth == 8) && colorType == 3) {
            int paletteOffset = 0;
            int paletteLength = 0;
            int pos = 8;

            int length;
            do {
               length = readInt(png, pos);
               if (readInt(png, pos + 4) == 1347179589) {
                  paletteOffset = pos;
                  paletteLength = length;
                  break;
               }
            } while ((pos += 12 + length) < png.length);

            replacePalette(png, paletteOffset + 4 + 4, paletteLength, fromColors, toColors);
            if (paletteOffset > 0) {
               length = this.crc32(png, paletteOffset + 4, paletteLength + 4);
               int crcOffset = paletteOffset + 4 + 4 + paletteLength;
               png[crcOffset] = (byte)((length & 0xFF000000) >>> 24);
               png[crcOffset + 1] = (byte)((length & 0xFF0000) >>> 16);
               png[crcOffset + 2] = (byte)((length & 0xFF00) >>> 8);
               png[crcOffset + 3] = (byte)(length & 0xFF);
            }

            try {
               Image image;
               if ((image = Image.createImage(png, 0, png.length)) != null) {
                  return image;
               }
            } catch (Exception ignored) {
            }

            return null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }
}
