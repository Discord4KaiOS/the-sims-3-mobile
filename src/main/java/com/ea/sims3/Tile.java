package com.ea.sims3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class Tile {
   public byte[] cellIndices;
   public short[] frames;
   public short[] rows;
   private int drawX;
   private int drawY;
   private int drawWidth;
   private int drawHeight;
   public int worldX;
   public int worldY;
   private Image sheet;
   private boolean loaded;
   public int columns;
   public int rowCountValue;
   private Image backing;
   private Graphics backingGraphics;
   private boolean backingEnabled;
   private boolean backingDirty;
   private int backingX;
   private int backingY;
   private int wrapX;
   private int wrapY;
   public Tile next;

   public static int tileWidth() {
      return 16;
   }

   public static int tileHeight() {
      return 8;
   }

   public static int toWorld16(int value) {
      return value << 20;
   }

   public static int toWorld8(int value) {
      return value << 19;
   }

   public final int frameCount() {
      return this.frames != null ? this.frames.length / 3 : 0;
   }

   public final int rowCount() {
      return this.rows != null ? this.rows.length / 3 : 0;
   }

   public Tile() {
      this.frames = null;
      this.sheet = null;
      this.loaded = false;
      this.drawX = 0;
      this.drawY = 0;
      this.drawWidth = 0;
      this.drawHeight = 0;
      this.worldX = 0;
      this.worldY = 0;
   }

   public Tile(int index, Image image) {
      this.frames = null;
      this.sheet = null;
      this.loaded = false;
      this.drawX = 0;
      this.drawY = 0;
      this.drawWidth = 0;
      this.drawHeight = 0;
      this.worldX = 0;
      this.worldY = 0;
      this.load(index);
      if (image != null) {
         this.sheet = image;
      }
   }

   public Tile(int columns, int rows, Image image) {
      this.sheet = image;
      this.columns = columns;
      this.rowCountValue = rows;
      int cellCount;
      byte[] cells = new byte[cellCount = columns * rows];

      for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
         cells[cellIndex] = 0;
      }

      this.cellIndices = cells;
      this.frames = null;
      this.loaded = true;
   }

   public final void setBounds(int x, int y, int width, int height) {
      if (this.backingEnabled && (this.drawWidth != width || this.drawHeight != height)) {
         this.backing = null;
         this.backing = Image.createImage(width, height);
         this.backingGraphics = this.backing.getGraphics();
         this.backingDirty = true;
      }

      this.drawX = x;
      this.drawY = y;
      this.drawWidth = width;
      this.drawHeight = height;
   }

   public final void draw(Graphics gfx) {
      int clipX = gfx.getClipX();
      int clipY = gfx.getClipY();
      int clipW = gfx.getClipWidth();
      int clipH = gfx.getClipHeight();
      int x = this.worldX >> 16;
      int y = this.worldY >> 16;
      if (this.backingEnabled) {
         this.drawBacking(gfx, x, y);
      } else {
         int dstX = clipX;
         int dstY = clipY;
         int dstW = clipW;
         int dstH = clipH;
         if (clipX < this.drawX) {
            dstX = this.drawX;
         }

         if (clipY < this.drawY) {
            dstY = this.drawY;
         }

         if (dstX + clipW > this.drawX + this.drawWidth) {
            dstW = clipW - (dstX + clipW - (this.drawX + this.drawWidth));
         }

         if (dstY + clipH > this.drawY + this.drawHeight) {
            dstH = clipH - (dstY + clipH - (this.drawY + this.drawHeight));
         }

         this.drawCells(gfx, x, y, dstX, dstY, dstW, dstH);
         if (this.next != null) {
            this.next.drawCells(gfx, x, y, dstX, dstY, dstW, dstH);
         }
      }

      gfx.setClip(clipX, clipY, clipW, clipH);
   }

   private boolean load(int index) {
      System.gc();
      if (this.loaded) {
         return false;
      } else {
         InputStream input = GameCanvas.instance().resources.openStream(index);
         System.gc();
         DataInputStream stream = new DataInputStream(input);

         try {
            stream.readUnsignedByte();
            int columns = stream.readUnsignedByte();
            int rows = stream.readUnsignedByte();
            stream.readUnsignedByte();
            stream.readUnsignedByte();
            this.columns = columns;
            this.rowCountValue = rows;
            System.gc();
            byte[] cells = new byte[columns * rows];

            for (int cellIndex = 0; cellIndex < columns * rows; cellIndex++) {
               cells[cellIndex] = stream.readByte();
            }

            this.cellIndices = cells;
            int frameCount;
            if ((frameCount = stream.readUnsignedByte()) > 0) {
               short[] frames = new short[frameCount * 3];

               for (int frameIndex = 0; frameIndex < frames.length; frameIndex++) {
                  frames[frameIndex] = stream.readShort();
               }

               this.frames = frames;
            }

            int rowCount;
            if ((rowCount = stream.readUnsignedByte()) > 0) {
               short[] rowsData = new short[rowCount * 3];

               for (int rowIndex = 0; rowIndex < rowsData.length; rowIndex++) {
                  rowsData[rowIndex] = stream.readShort();
               }

               this.rows = rowsData;
            }

            input.close();
         } catch (IOException exception) {
         }

         System.gc();
         this.loaded = true;
         return true;
      }
   }

   private void drawCells(Graphics gfx, int x, int y, int left, int top, int width, int height) {
      int remainingWidth = 0;
      int fullColumns = 0;
      int lastColumnWidth = 0;
      int remainingHeight = 0;
      int fullRows = 0;
      int lastRowHeight = 0;
      int xInTile = x & 15;
      int yInTile = y & 7;
      int firstColumnWidth = 0;
      if (xInTile > 0) {
         firstColumnWidth = 16 - xInTile;
      }

      int firstRowHeight = 0;
      if (yInTile > 0) {
         firstRowHeight = 8 - yInTile;
      }

      if (firstColumnWidth > width) {
         firstColumnWidth = width;
      } else {
         fullColumns = (remainingWidth = width - firstColumnWidth) >> 4;
         lastColumnWidth = remainingWidth & 15;
      }

      if (firstRowHeight > height) {
         firstRowHeight = height;
      } else {
         fullRows = (remainingHeight = height - firstRowHeight) >> 3;
         lastRowHeight = remainingHeight & 7;
      }

      int cellIndex;
      int rowStartIndex = cellIndex = (y >> 3) * this.columns + (x >> 4);
      int cellValue = 0;
      int sourceX = 0;
      int sourceY = 0;
      int destX = left;
      int destY = top;
      int lastCellIndex = this.cellIndices.length - 1;
      byte[] cellData = this.cellIndices;
      Image sheetImage = this.sheet;
      if (firstRowHeight > 0) {
         if (firstColumnWidth > 0) {
            if (cellIndex >= 0) {
               if (cellIndex > lastCellIndex) {
                  return;
               }

               if ((cellValue = cellData[cellIndex++] & 255) != 255) {
                  sourceX = (cellValue & 7) << 4;
                  sourceY = cellValue >> 3 << 3;
                  gfx.drawRegion(sheetImage, sourceX + xInTile, sourceY + yInTile, firstColumnWidth, firstRowHeight, 0, left, top, 20);
               }
            }

            destX = left + firstColumnWidth;
         }

         for (int column = 0; column < fullColumns; column++) {
            if (cellIndex >= 0) {
               if (cellIndex > lastCellIndex) {
                  return;
               }

               if ((cellValue = cellData[cellIndex++] & 255) != 255) {
                  sourceX = (cellValue & 7) << 4;
                  sourceY = cellValue >> 3 << 3;
                  gfx.drawRegion(sheetImage, sourceX, sourceY + yInTile, 16, firstRowHeight, 0, destX, destY, 20);
               }
            }

            destX += 16;
         }

         if (lastColumnWidth > 0 && cellIndex >= 0) {
            if (cellIndex > lastCellIndex) {
               return;
            }

            if ((cellValue = cellData[cellIndex] & 255) != 255) {
               sourceX = (cellValue & 7) << 4;
               sourceY = cellValue >> 3 << 3;
               gfx.drawRegion(this.sheet, sourceX, sourceY + yInTile, lastColumnWidth, firstRowHeight, 0, destX, destY, 20);
            }
         }

         destY += firstRowHeight;
         cellIndex = rowStartIndex += this.columns;
      }

      for (int row = 0; row < fullRows; row++) {
         destX = left;
         if (firstColumnWidth > 0) {
            if (cellIndex >= 0) {
               if (cellIndex > lastCellIndex) {
                  return;
               }

               if ((cellValue = cellData[cellIndex++] & 255) != 255) {
                  sourceX = (cellValue & 7) << 4;
                  sourceY = cellValue >> 3 << 3;
                  gfx.drawRegion(sheetImage, sourceX + xInTile, sourceY, firstColumnWidth, 8, 0, left, destY, 20);
               }
            }

            destX = left + firstColumnWidth;
         }

         for (int column = 0; column < fullColumns; column++) {
            if (cellIndex >= 0) {
               if (cellIndex > lastCellIndex) {
                  return;
               }

               if ((cellValue = cellData[cellIndex++] & 255) != 255) {
                  sourceX = (cellValue & 7) << 4;
                  sourceY = cellValue >> 3 << 3;
                  gfx.drawRegion(sheetImage, sourceX, sourceY, 16, 8, 0, destX, destY, 20);
               }
            }

            destX += 16;
         }

         if (lastColumnWidth > 0 && cellIndex >= 0) {
            if (cellIndex > lastCellIndex) {
               return;
            }

            if ((cellValue = cellData[cellIndex] & 255) != 255) {
               sourceX = (cellValue & 7) << 4;
               sourceY = cellValue >> 3 << 3;
               gfx.drawRegion(sheetImage, sourceX, sourceY, lastColumnWidth, 8, 0, destX, destY, 20);
            }
         }

         destY += 8;
         cellIndex = rowStartIndex += this.columns;
      }

      if (lastRowHeight > 0) {
         destX = left;
         if (firstColumnWidth > 0) {
            if (cellIndex >= 0) {
               if (cellIndex > lastCellIndex) {
                  return;
               }

               if ((cellValue = cellData[cellIndex++] & 255) != 255) {
                  sourceX = (cellValue & 7) << 4;
                  sourceY = cellValue >> 3 << 3;
                  gfx.drawRegion(sheetImage, sourceX + xInTile, sourceY, firstColumnWidth, lastRowHeight, 0, left, destY, 20);
               }
            }

            destX = left + firstColumnWidth;
         }

         for (int column = 0; column < fullColumns; column++) {
            if (cellIndex >= 0) {
               if (cellIndex > lastCellIndex) {
                  return;
               }

               if ((cellValue = cellData[cellIndex++] & 255) != 255) {
                  sourceX = (cellValue & 7) << 4;
                  sourceY = cellValue >> 3 << 3;
                  gfx.drawRegion(sheetImage, sourceX, sourceY, 16, lastRowHeight, 0, destX, destY, 20);
               }
            }

            destX += 16;
         }

         if (lastColumnWidth > 0 && cellIndex >= 0) {
            if (cellIndex > lastCellIndex) {
               return;
            }

            if ((cellValue = cellData[cellIndex] & 255) != 255) {
               sourceX = (cellValue & 7) << 4;
               sourceY = cellValue >> 3 << 3;
               gfx.drawRegion(sheetImage, sourceX, sourceY, lastColumnWidth, lastRowHeight, 0, destX, destY, 20);
            }
         }
      }
   }

   public final void setBackingEnabled(boolean enabled) {
      if (enabled && !this.backingEnabled) {
         if (this.drawWidth > 0 && this.drawHeight > 0) {
            this.backing = Image.createImage(this.drawWidth, this.drawHeight);
            this.backingGraphics = this.backing.getGraphics();
         }

         this.backingDirty = true;
         this.backingX = this.worldX >> 16;
         this.backingY = this.worldY >> 16;
         this.wrapX = 0;
         this.wrapY = 0;
         this.backingEnabled = true;
      } else {
         if (!enabled && this.backingEnabled) {
            this.backing = null;
            this.backingEnabled = false;
         }
      }
   }

   public final void invalidateBacking() {
      this.backingDirty = true;
   }

   private void drawBacking(Graphics gfx, int x, int y) {
      Graphics backingGfx = this.backingGraphics;
      int deltaX = x - this.backingX;
      int deltaY = y - this.backingY;
      int width = this.drawWidth;
      int height = this.drawHeight;
      if (!this.backingDirty && Math.abs(deltaX) <= width && Math.abs(deltaY) <= height) {
         int wrapX = this.wrapX;
         int wrapY = this.wrapY;
         int newWrapX = this.copyRows(backingGfx, x, wrapX, wrapY, deltaX, this.backingX, this.backingY);
         if (this.next != null) {
            this.next.copyRows(backingGfx, x, wrapX, wrapY, deltaX, this.backingX, this.backingY);
         }

         this.wrapX = newWrapX;
         int newWrapY = this.copyColumns(backingGfx, y, wrapY, deltaY, this.backingY, x, newWrapX);
         if (this.next != null) {
            this.next.copyColumns(backingGfx, y, wrapY, deltaY, this.backingY, x, newWrapX);
         }

         this.wrapY = newWrapY;
         int widthRemaining = width - this.wrapX;
         int heightRemaining = height - this.wrapY;
         backingGfx.setClip(0, 0, width, height);
         if (widthRemaining != 0 && heightRemaining != 0) {
            gfx.drawRegion(this.backing, this.wrapX, this.wrapY, widthRemaining, heightRemaining, 0, this.drawX, this.drawY, 20);
         }

         if (this.wrapX != 0 && heightRemaining != 0) {
            gfx.drawRegion(this.backing, 0, this.wrapY, this.wrapX, heightRemaining, 0, this.drawX + widthRemaining, this.drawY, 20);
         }

         if (widthRemaining != 0 && this.wrapY != 0) {
            gfx.drawRegion(this.backing, this.wrapX, 0, widthRemaining, this.wrapY, 0, this.drawX, heightRemaining + this.drawY, 20);
         }

         if (this.wrapX != 0 && this.wrapY != 0) {
            gfx.drawRegion(this.backing, 0, 0, this.wrapX, this.wrapY, 0, this.drawX + widthRemaining, heightRemaining + this.drawY, 20);
         }
      } else {
         this.backingDirty = false;
         this.drawCells(backingGfx, x, y, 0, 0, width, height);
         if (this.next != null) {
            this.next.drawCells(backingGfx, x, y, 0, 0, width, height);
         }

         this.wrapX = 0;
         this.wrapY = 0;
      }

      this.backingX = x;
      this.backingY = y;
   }

   private int copyRows(Graphics gfx, int x, int wrapX, int wrapY, int deltaX, int backingX, int backingY) {
      int width = this.drawWidth;
      int height = this.drawHeight;
      int newWrapX = wrapX + deltaX;
      if (deltaX > 0) {
         int destX = x + width - deltaX;
         if (newWrapX > width) {
            this.drawCells(gfx, destX, backingY, wrapX, wrapY, width - wrapX, height - wrapY);
            this.drawCells(gfx, destX, backingY + height - wrapY, wrapX, 0, width - wrapX, wrapY);
            destX += width - wrapX;
            this.drawCells(gfx, destX, backingY, 0, wrapY, newWrapX - width, height - wrapY);
            this.drawCells(gfx, destX, backingY + height - wrapY, 0, 0, newWrapX - width, wrapY);
            newWrapX -= width;
         } else {
            this.drawCells(gfx, destX, backingY, wrapX, wrapY, deltaX, height - wrapY);
            this.drawCells(gfx, destX, backingY + height - wrapY, wrapX, 0, deltaX, wrapY);
         }
      } else if (deltaX < 0) {
         if (newWrapX < 0) {
            int baseX = backingX - wrapX;
            this.drawCells(gfx, baseX, backingY, 0, wrapY, wrapX, height - wrapY);
            this.drawCells(gfx, baseX, backingY + height - wrapY, 0, 0, wrapX, wrapY);
            baseX += newWrapX;
            this.drawCells(gfx, baseX, backingY, width + newWrapX, wrapY, -newWrapX, height - wrapY);
            this.drawCells(gfx, baseX, backingY + height - wrapY, width + newWrapX, 0, -newWrapX, wrapY);
            newWrapX += width;
         } else {
            this.drawCells(gfx, x, backingY, newWrapX, wrapY, -deltaX, height - wrapY);
            this.drawCells(gfx, x, backingY + height - wrapY, newWrapX, 0, -deltaX, wrapY);
         }
      }

      return newWrapX;
   }

   private int copyColumns(Graphics gfx, int x, int wrapY, int deltaY, int backingY, int backingX, int wrapX) {
      int width = this.drawWidth;
      int height = this.drawHeight;
      int newWrapY = wrapY + deltaY;
      if (deltaY > 0) {
         int destY = x + height - deltaY;
         if (newWrapY > height) {
            this.drawCells(gfx, backingX, destY, wrapX, wrapY, width - wrapX, height - wrapY);
            this.drawCells(gfx, backingX + width - wrapX, destY, 0, wrapY, wrapX, height - wrapY);
            destY += height - wrapY;
            this.drawCells(gfx, backingX, destY, wrapX, 0, width - wrapX, newWrapY - height);
            this.drawCells(gfx, backingX + width - wrapX, destY, 0, 0, wrapX, newWrapY - height);
            newWrapY -= height;
         } else {
            this.drawCells(gfx, backingX, destY, wrapX, wrapY, width - wrapX, deltaY);
            this.drawCells(gfx, backingX + width - wrapX, destY, 0, wrapY, wrapX, deltaY);
         }
      } else if (deltaY < 0) {
         if (newWrapY < 0) {
            int baseY = backingY - wrapY;
            this.drawCells(gfx, backingX, baseY, wrapX, 0, width - wrapX, wrapY);
            this.drawCells(gfx, backingX + width - wrapX, baseY, 0, 0, wrapX, wrapY);
            baseY += newWrapY;
            this.drawCells(gfx, backingX, baseY, wrapX, height + newWrapY, width - wrapX, -newWrapY);
            this.drawCells(gfx, backingX + width - wrapX, baseY, 0, height + newWrapY, wrapX, -newWrapY);
            newWrapY += height;
         } else {
            this.drawCells(gfx, backingX, x, wrapX, newWrapY, width - wrapX, -deltaY);
            this.drawCells(gfx, backingX + width - wrapX, x, 0, newWrapY, wrapX, -deltaY);
         }
      }

      return newWrapY;
   }
}
