package com.ea.sims3;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class SpriteRenderer implements SpriteIndex {
   private static byte[] palette;
   private static short[][] spriteRects;
   private static int recolorIndex;
   private static Image[][] tiles = new Image[4][72];
   private static short[][] frameDurations;
   private static short[][] framePartOffsets;
   private static short[][] parts;

   private SpriteRenderer() {
   }

   public static final boolean loadPalette(ResourceLoader loader) {
      DataInputStream input = new DataInputStream(loader.openStream(4));
      palette = new byte[ResourceLoader.getSize(4)];

      try {
         input.read(palette);
         input.close();
         return true;
      } catch (Throwable ignored) {
         return false;
      }
   }

   public static final void setColor(Graphics gfx, int colorIndex) {
      int offset = colorIndex * 3;
      byte[] colors = palette;
      gfx.setColor(colors[offset] & 255, colors[offset + 1] & 255, colors[offset + 2] & 255);
   }

   public static final boolean loadSpriteRects(ResourceLoader loader) {
      try {
         short[][] rects = new short[1418][5];
         DataInputStream input = new DataInputStream(loader.openStream(31));

         for (int rowIndex = 0; rowIndex < 1418; rowIndex++) {
            short[] rect;
            (rect = rects[rowIndex])[0] = (short)input.readUnsignedByte();
            rect[1] = input.readShort();
            rect[2] = input.readShort();
            rect[3] = input.readShort();
            rect[4] = input.readShort();
         }

         spriteRects = rects;
         return true;
      } catch (Throwable ignored) {
         return false;
      }
   }

   public static final boolean loadFrameData(ResourceLoader loader) {
      boolean loaded = false;

      try {
         DataInputStream stream = new DataInputStream(loader.openStream(3));
         short[][] durations = new short[710][];
         short[][] partOffsets = new short[710][];
         short[][] partData = new short[710][];

         for (int spriteId = 0; spriteId != 710; spriteId++) {
            int partCount = 0;
            int durationCount = stream.readUnsignedByte();

            for (int frameIndex = 0; frameIndex < durationCount; frameIndex++) {
               stream.readShort();
               int framePartCount = stream.readUnsignedByte();

               for (int partIndex = 0; partIndex < framePartCount; partIndex++) {
                  int kind;
                  int fieldCount = partKind(kind = stream.readUnsignedByte());

                  for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++) {
                     stream.readByte();
                  }

                  partCount += partSize(kind);
               }
            }

            durations[spriteId] = new short[durationCount];
            partOffsets[spriteId] = new short[durationCount];
            partData[spriteId] = new short[partCount];
         }

         frameDurations = durations;
         framePartOffsets = partOffsets;
         parts = partData;
         stream.close();
         stream = new DataInputStream(loader.openStream(3));

         for (int spriteId = 0; spriteId != 710; spriteId++) {
            int offset = 0;
            short[] framePartData = partData[spriteId];
            short[] frameDurations = durations[spriteId];
            short[] framePartOffsets = partOffsets[spriteId];
            int durationCount = stream.readUnsignedByte();

            for (int frameIndex = 0; frameIndex < durationCount; frameIndex++) {
               frameDurations[frameIndex] = stream.readShort();
               framePartOffsets[frameIndex] = (short)offset;
               int framePartCount = stream.readUnsignedByte();

               for (int partIndex = 0; partIndex < framePartCount; partIndex++) {
                  int kind;
                  switch (kind = stream.readUnsignedByte()) {
                     case 0:
                     case 11:
                        framePartData[offset + 3] = stream.readShort();
                        framePartData[offset + 1] = stream.readShort();
                        framePartData[offset + 2] = stream.readShort();
                        break;
                     case 1:
                     case 2:
                     case 3:
                     case 4:
                     case 5:
                     case 6:
                     case 8:
                        framePartData[offset + 1] = stream.readShort();
                        framePartData[offset + 2] = stream.readShort();
                        framePartData[offset + 3] = stream.readShort();
                        framePartData[offset + 4] = stream.readShort();
                        break;
                     case 7:
                        framePartData[offset + 1] = stream.readShort();
                        framePartData[offset + 2] = stream.readShort();
                     case 9:
                     default:
                        break;
                     case 10:
                        framePartData[offset + 1] = stream.readByte();
                  }

                  framePartData[offset] = (short)kind;
                  offset += partSize(kind);
               }
            }
         }

         stream.close();
         return true;
      } catch (IOException error) {
         return false;
      }
   }

   private static final int partKind(int kind) {
      switch (kind) {
         case 0:
         case 11:
            return 6;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 8:
            return 8;
         case 7:
            return 4;
         case 9:
         default:
            return 0;
         case 10:
            return 1;
      }
   }

   private static final int partSize(int kind) {
      switch (kind) {
         case 0:
         case 11:
            return 4;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 8:
            return 5;
         case 7:
            return 3;
         case 9:
         default:
            return 0;
         case 10:
            return 2;
      }
   }

   private static final int partOffset(int spriteId, int frame) {
      return framePartOffsets[spriteId][frame];
   }

   private static final int nextPartOffset(int spriteId, int frame) {
      short[] offsets = framePartOffsets[spriteId];
      return frame < offsets.length - 1 ? offsets[frame + 1] : parts[spriteId].length;
   }

   public static final boolean loadTile(ResourceLoader loader, int spriteId) {
      return loadTileRecolored(loader, spriteId, null, null, 0);
   }

   public static final boolean loadTileRecolored(ResourceLoader loader, int spriteId, int[] fromColors, int[] toColors, int recolorSet) {
      int slot = 0;

      while (slot < 72 && spriteId != SpriteIndex.SPRITE_IDS[slot]) {
         slot++;
      }

      if (72 == slot) {
         return false;
      } else {
         Image[] slots;
         if ((slots = tiles[recolorSet])[slot] == null) {
            Image image;
            if (fromColors != null) {
               image = loader.loadImageRecolored(spriteId, fromColors, toColors);
            } else {
               image = ResourceLoader.loadImage(spriteId);
            }

            slots[slot] = image;
            if (image == null) {
               return false;
            }
         }

         return true;
      }
   }

   public static final boolean unloadTile(int spriteId, int recolorSet) {
      int slot = 0;

      while (slot < 72 && spriteId != SpriteIndex.SPRITE_IDS[slot]) {
         slot++;
      }

      if (72 == slot) {
         return false;
      } else {
         tiles[recolorSet][slot] = null;
         return true;
      }
   }

   public static final void drawFrame(Graphics gfx, int spriteId, int frame, int x, int y) {
      if (spriteId >= 0) {
         if ((spriteId & 16384) != 0) {
            spriteId &= -16385;
            drawFrameMirrored(gfx, spriteId, frame, x, y);
         } else {
            drawFrameNormal(gfx, spriteId, frame, x, y);
         }
      }
   }

   private static final void drawFrameNormal(Graphics gfx, int spriteId, int frame, int x, int y) {
      Image[] tileSlots = tiles[recolorIndex];
      short[][] rects = spriteRects;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);

      while (offset < nextOffset) {
         short kind;
         if ((kind = partData[offset + 0]) == 0 || kind == 11) {
            short[] rect;
            int tileIndex = (rect = rects[partData[offset + 3]])[0] & '\uffff';
            Image tile;
            if ((tile = tileSlots[tileIndex]) != null) {
               int destX = x + partData[offset + 1];
               int destY = y + partData[offset + 2];
               int transform = kind == 0 ? 0 : 2;
               gfx.drawRegion(tile, rect[1], rect[2], rect[3], rect[4], transform, destX, destY, 20);
            }
         } else if (kind != 2 && kind != 7) {
            drawPart(gfx, x, y, partData, offset);
         }

         offset += partSize(kind);
      }
   }

   private static final void drawFrameMirrored(Graphics gfx, int spriteId, int frame, int x, int y) {
      Image[] tileSlots = tiles[recolorIndex];
      short[][] rects = spriteRects;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);

      while (offset < nextOffset) {
         short kind;
         if ((kind = partData[offset + 0]) == 0 || kind == 11) {
            short[] rect;
            int tileIndex = (rect = rects[partData[offset + 3]])[0] & '\uffff';
            Image tile;
            if ((tile = tileSlots[tileIndex]) != null) {
               int destX = x - partData[offset + 1] - rect[3];
               int destY = y + partData[offset + 2];
               int transform = kind == 0 ? 2 : 0;
               gfx.drawRegion(tile, rect[1], rect[2], rect[3], rect[4], transform, destX, destY, 20);
            }
         } else if (kind != 2 && kind != 7) {
            drawPartMirrored(gfx, x, y, partData, offset);
         }

         offset += partSize(kind);
      }
   }

   public static final boolean drawParts(Graphics gfx, int spriteId, int frame, int x, int y, int matchSpriteId, int matchLimit, int[] out) {
      if (spriteId < 0) {
         return false;
      } else if ((spriteId & 16384) != 0) {
         spriteId &= -16385;
         return drawPartsClipped(gfx, spriteId, frame, x, y, matchSpriteId, matchLimit, out);
      } else {
         return drawPartsMirrored(gfx, spriteId, frame, x, y, matchSpriteId, matchLimit, out);
      }
   }

   private static final boolean drawPartsMirrored(Graphics gfx, int spriteId, int frame, int x, int y, int matchSpriteId, int matchLimit, int[] out) {
      Image[] tileSlots = tiles[recolorIndex];
      short[][] rects = spriteRects;
      boolean found = false;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);
      short[] matchParts = matchSpriteId >= 0 ? parts[matchSpriteId] : null;

      while (offset < nextOffset) {
         short kind;
         if ((kind = partData[offset + 0]) != 0 && kind != 11) {
            drawPart(gfx, x, y, partData, offset);
         } else {
            short rectIndex = partData[offset + 3];
            short[] rect = rects[rectIndex];
            int destX = x + partData[offset + 1];
            int destY = y + partData[offset + 2];
            int tileIndex = rect[0] & '\uffff';
            Image tile;
            if ((tile = tileSlots[tileIndex]) != null) {
               int transform = kind == 0 ? 0 : 2;
               gfx.drawRegion(tile, rect[1], rect[2], rect[3], rect[4], transform, destX, destY, 20);
            }

            if (matchParts != null) {
               int matchFrameCount = frameCount(matchSpriteId);

               for (int matchFrame = 0; matchFrame < matchFrameCount; matchFrame++) {
                  int matchOffset = partOffset(matchSpriteId, matchFrame);
                  int matchRectIndex = matchParts[matchOffset + 3] & '\uffff';
                  if (rectIndex == matchRectIndex) {
                     int matchNextOffset = nextPartOffset(matchSpriteId, matchFrame);
                     short matchX = matchParts[matchOffset + 1];
                     short matchY = matchParts[matchOffset + 2];
                     int skipped = 0;

                     do {
                        if ((matchOffset += partSize(matchParts[matchOffset + 0])) < matchNextOffset && matchParts[matchOffset + 0] == 0) {
                           skipped++;
                        }
                     } while (skipped < matchLimit && matchOffset < matchNextOffset);

                     if (out != null) {
                        found = true;
                        int lastOffset = matchNextOffset - partSize(7);
                        out[0] = destX + matchParts[lastOffset + 1] - matchX;
                        out[1] = destY + matchParts[lastOffset + 2] - matchY;
                     }

                     if (matchOffset < matchNextOffset) {
                        int offsetX = matchParts[matchOffset + 1] - matchX;
                        int offsetY = matchParts[matchOffset + 2] - matchY;
                        short[] clipRect;
                        int clipTileIndex = (clipRect = rects[matchParts[matchOffset + 3]])[0] & '\uffff';
                        Image clipTile = tileSlots[clipTileIndex];
                        if (tile != null) {
                           byte clipTransform;
                           if (kind == 11) {
                              clipTransform = 2;
                              short clipWidth = clipRect[3];
                              short partWidth = rect[3];
                              offsetX = -offsetX - (clipWidth - partWidth);
                           } else {
                              clipTransform = 0;
                           }

                           offsetX += destX;
                           offsetY += destY;
                           gfx.drawRegion(clipTile, clipRect[1], clipRect[2], clipRect[3], clipRect[4], clipTransform, offsetX, offsetY, 20);
                        }
                     }
                     break;
                  }
               }
            }
         }

         offset += partSize(kind);
      }

      return found;
   }

   private static final boolean drawPartsClipped(Graphics gfx, int spriteId, int frame, int x, int y, int matchSpriteId, int matchLimit, int[] out) {
      Image[] tileSlots = tiles[recolorIndex];
      short[][] rects = spriteRects;
      boolean found = false;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);
      short[] matchParts = matchSpriteId >= 0 ? parts[matchSpriteId] : null;

      while (offset < nextOffset) {
         short kind;
         if ((kind = partData[offset + 0]) != 0 && kind != 11) {
            drawPartMirrored(gfx, x, y, partData, offset);
         } else {
            short rectIndex = partData[offset + 3];
            short[] rect = rects[rectIndex];
            int destX = x - partData[offset + 1] - rect[3];
            int destY = y + partData[offset + 2];
            int tileIndex = rect[0] & '\uffff';
            Image tile;
            if ((tile = tileSlots[tileIndex]) != null) {
               int transform = kind == 0 ? 2 : 0;
               gfx.drawRegion(tile, rect[1], rect[2], rect[3], rect[4], transform, destX, destY, 20);
            }

            if (matchParts != null) {
               int matchFrameCount = frameCount(matchSpriteId);

               for (int matchFrame = 0; matchFrame < matchFrameCount; matchFrame++) {
                  int matchOffset = partOffset(matchSpriteId, matchFrame);
                  int matchRectIndex = matchParts[matchOffset + 3] & '\uffff';
                  if (rectIndex == matchRectIndex) {
                     int matchNextOffset = nextPartOffset(matchSpriteId, matchFrame);
                     short matchX = matchParts[matchOffset + 1];
                     short matchY = matchParts[matchOffset + 2];
                     int skipped = 0;

                     do {
                        if ((matchOffset += partSize(matchParts[matchOffset + 0])) < matchNextOffset && matchParts[matchOffset + 0] == 0) {
                           skipped++;
                        }
                     } while (skipped < matchLimit && matchOffset < matchNextOffset);

                     if (out != null) {
                        found = true;
                        int lastOffset = matchNextOffset - partSize(7);
                        out[0] = destX + matchParts[lastOffset + 1] - matchX;
                        out[1] = destY + matchParts[lastOffset + 2] - matchY;
                     }

                     if (matchOffset < matchNextOffset) {
                        int offsetX = matchParts[matchOffset + 1] - matchX;
                        int offsetY = matchParts[matchOffset + 2] - matchY;
                        short[] clipRect;
                        int clipTileIndex = (clipRect = rects[matchParts[matchOffset + 3]])[0] & '\uffff';
                        Image clipTile = tileSlots[clipTileIndex];
                        if (tile != null) {
                           byte clipTransform;
                           if (kind == 11) {
                              clipTransform = 0;
                           } else {
                              clipTransform = 2;
                              short clipWidth = clipRect[3];
                              short partWidth = rect[3];
                              offsetX = -offsetX - (clipWidth - partWidth);
                           }

                           offsetX += destX;
                           offsetY += destY;
                           gfx.drawRegion(clipTile, clipRect[1], clipRect[2], clipRect[3], clipRect[4], clipTransform, offsetX, offsetY, 20);
                        }
                     }
                     break;
                  }
               }
            }
         }

         offset += partSize(kind);
      }

      return found;
   }

   private static final void drawPart(Graphics gfx, int x, int y, short[] partData, int offset) {
      short kind;
      switch (kind = partData[offset + 0]) {
         case 3:
         case 4:
         case 5:
         case 6:
            int destX = x + partData[offset + 1];
            int destY = y + partData[offset + 2];
            short width = partData[offset + 3];
            short height = partData[offset + 4];
            switch (kind) {
               case 3:
                  gfx.drawRect(destX, destY, width - 1, height - 1);
                  break;
               case 4:
                  gfx.fillRect(destX, destY, width, height);
                  break;
               case 5:
                  gfx.drawArc(destX, destY, width - 1, height - 1, 0, 360);
                  break;
               case 6:
                  gfx.fillArc(destX, destY, width, height, 0, 360);
               default:
                  return;
            }
         case 7:
         case 9:
         default:
            break;
         case 8:
            int startX = x + partData[offset + 1];
            int startY = y + partData[offset + 2];
            int endX = x + partData[offset + 3];
            int endY = y + partData[offset + 4];
            gfx.drawLine(startX, startY, endX, endY);
            return;
         case 10:
            setColor(gfx, partData[offset + 1]);
      }
   }

   private static final void drawPartMirrored(Graphics gfx, int x, int y, short[] partData, int offset) {
      short kind;
      switch (kind = partData[offset + 0]) {
         case 3:
         case 4:
         case 5:
         case 6:
            short width = partData[offset + 3];
            short height = partData[offset + 4];
            int destX = x - (partData[offset + 1] + width);
            int destY = y + partData[offset + 2];
            switch (kind) {
               case 3:
                  gfx.drawRect(destX, destY, width - 1, height - 1);
                  break;
               case 4:
                  gfx.fillRect(destX, destY, width, height);
                  break;
               case 5:
                  gfx.drawArc(destX, destY, width - 1, height - 1, 0, 360);
                  break;
               case 6:
                  gfx.fillArc(destX, destY, width, height, 0, 360);
               default:
                  return;
            }
         case 7:
         case 9:
         default:
            break;
         case 8:
            int startX = x - partData[offset + 1];
            int startY = y + partData[offset + 2];
            int endX = x - partData[offset + 3];
            int endY = y + partData[offset + 4];
            gfx.drawLine(startX, startY, endX, endY);
            return;
         case 10:
            setColor(gfx, partData[offset + 1]);
      }
   }

   public static final int frameCount(int spriteId) {
      spriteId &= -16385;
      return frameDurations[spriteId].length;
   }

   public static final short frameDuration(int spriteId, int frame) {
      spriteId &= -16385;
      return frameDurations[spriteId][frame];
   }

   private static final int partX(short[] parts, int offset) {
      switch (parts[offset + 0]) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 9:
         case 11:
            return parts[offset + 1];
         case 8:
            short start = parts[offset + 1];
            short end = parts[offset + 3];
            if (start < end) {
               return start;
            }

            return end;
         case 10:
         default:
            return 0;
      }
   }

   private static final int partY(short[] parts, int offset) {
      switch (parts[offset + 0]) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 9:
         case 11:
            return parts[offset + 2];
         case 8:
            short start = parts[offset + 2];
            short end = parts[offset + 4];
            if (start < end) {
               return start;
            }

            return end;
         case 10:
         default:
            return 0;
      }
   }

   private static final int partWidth(short[] parts, int offset) {
      switch (parts[offset + 0]) {
         case 0:
         case 11:
            short rectIndex;
            rectIndex = (short)((rectIndex = parts[offset + 3]) & 65535);
            return spriteRects[rectIndex][3];
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
            return parts[offset + 3];
         case 7:
         case 10:
         default:
            return 0;
         case 8:
            return Math.abs(parts[offset + 1] - parts[offset + 3]);
         case 9:
            return 0;
      }
   }

   private static final int partHeight(short[] parts, int offset) {
      switch (parts[offset + 0]) {
         case 0:
         case 11:
            short rectIndex;
            rectIndex = (short)((rectIndex = parts[offset + 3]) & 65535);
            return spriteRects[rectIndex][4];
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
            return parts[offset + 4];
         case 7:
         case 10:
         default:
            return 0;
         case 8:
            return Math.abs(parts[offset + 2] - parts[offset + 4]);
         case 9:
            return 0;
      }
   }

   public static final int frameMinX(int spriteId, int frame) {
      spriteId &= -16385;
      int min = Integer.MAX_VALUE;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);
      if (offset == nextOffset) {
         return 0;
      } else {
         while (offset < nextOffset) {
            short kind;
            int x;
            if ((kind = partData[offset + 0]) != 10 && (x = partX(partData, offset)) < min) {
               min = x;
            }

            offset += partSize(kind);
         }

         return min;
      }
   }

   public static final int frameMinY(int spriteId, int frame) {
      spriteId &= -16385;
      int min = Integer.MAX_VALUE;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);
      if (offset == nextOffset) {
         return 0;
      } else {
         while (offset < nextOffset) {
            short kind;
            int y;
            if ((kind = partData[offset + 0]) != 10 && (y = partY(partData, offset)) < min) {
               min = y;
            }

            offset += partSize(kind);
         }

         return min;
      }
   }

   public static final int frameWidth(int spriteId, int frame) {
      spriteId &= -16385;
      int min = Integer.MAX_VALUE;
      int max = Integer.MIN_VALUE;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);
      if (offset == nextOffset) {
         return 0;
      } else {
         while (offset < nextOffset) {
            short kind;
            if ((kind = partData[offset + 0]) != 10) {
               int x;
               int right = (x = partX(partData, offset)) + partWidth(partData, offset);
               if (x < min) {
                  min = x;
               }

               if (right > max) {
                  max = right;
               }
            }

            offset += partSize(kind);
         }

         return max - min;
      }
   }

   public static final int frameHeight(int spriteId, int frame) {
      spriteId &= -16385;
      int min = Integer.MAX_VALUE;
      int max = Integer.MIN_VALUE;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);
      if (offset == nextOffset) {
         return 0;
      } else {
         while (offset < nextOffset) {
            short kind;
            if ((kind = partData[offset + 0]) != 10) {
               int y;
               int bottom = (y = partY(partData, offset)) + partHeight(partData, offset);
               if (y < min) {
                  min = y;
               }

               if (bottom > max) {
                  max = bottom;
               }
            }

            offset += partSize(kind);
         }

         return max - min;
      }
   }

   public static final int countSpecialParts(int spriteId, int frame) {
      spriteId &= -16385;
      int count = 0;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);

      while (offset < nextOffset) {
         short kind;
         if ((kind = partData[offset + 0]) == 7) {
            count++;
         }

         offset += partSize(kind);
      }

      return count;
   }

   public static final boolean hitTest(int[] out, int spriteId, int frame, int handleIndex) {
      boolean mirrored = false;
      if ((spriteId & 16384) != 0) {
         spriteId &= -16385;
         mirrored = true;
      }

      int handleCount = 0;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);

      while (offset < nextOffset) {
         short kind;
         if ((kind = partData[offset + 0]) == 7) {
            if (handleCount == handleIndex) {
               out[0] = partData[offset + 1];
               out[1] = partData[offset + 2];
               if (mirrored) {
                  out[0] = -out[0];
               }

               return true;
            }

            handleCount++;
         }

         offset += partSize(kind);
      }

      out[0] = 0;
      out[1] = 0;
      return false;
   }

   public static final Image getTileImage(int spriteId, int frame, int imageIndex) {
      spriteId &= -16385;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);

      while (offset < nextOffset) {
         short kind;
         if ((kind = partData[offset + 0]) == 0 || kind == 11) {
            if (imageIndex == 0) {
               short rectIndex = partData[offset + 3];
               short tileIndex = spriteRects[rectIndex][0];
               return tiles[recolorIndex][tileIndex];
            }

            imageIndex--;
         }

         offset += partSize(kind);
      }

      return null;
   }

   public static final boolean hitTestMirrored(int[] out, int spriteId, int frame, int handleIndex) {
      if ((spriteId & 16384) != 0) {
         spriteId &= -16385;
      }

      int handleCount = 0;
      short[] partData = parts[spriteId];
      int offset = partOffset(spriteId, frame);
      int nextOffset = nextPartOffset(spriteId, frame);

      while (offset < nextOffset) {
         short kind;
         if ((kind = partData[offset + 0]) == 2) {
            if (handleCount == handleIndex) {
               out[0] = partData[offset + 1];
               out[1] = partData[offset + 2];
               out[2] = partData[offset + 3];
               out[3] = partData[offset + 4];
               return true;
            }

            handleCount++;
         }

         offset += partSize(kind);
      }

      out[0] = 0;
      out[1] = 0;
      out[2] = 0;
      out[3] = 0;
      return false;
   }

   public static final void drawAnimator(Graphics gfx, Animator animator, int x, int y) {
      drawFrame(gfx, animator.animId, animator.frame, x, y);
   }

   public static final void setRecolorIndex(int index) {
      recolorIndex = index;
   }
}
