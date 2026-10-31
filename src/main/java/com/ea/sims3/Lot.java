package com.ea.sims3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class Lot implements SpriteIndex, SoundIndex, Resources {
   private GameCanvas canvas;
   private byte[] objectKind;
   private short[] spriteId;
   private short[] spriteVariant;
   private int[] objectFlags;
   private short[] linkedId;
   private short[] objectIds;
   private short[] altIds;
   private byte[] objectWidth;
   private byte[] objectHeight;
   private short[] subType;
   private short[][] attachments;
   private byte[][] frameFlags;
   private byte[][] frameMirrored;
   private short[][] frameRefs;
   private short[] frameData;
   private short[][] frameParts;
   private byte[][] layoutTables;
   private short[] categoryIds;
   private short[] categoryValues;
   private int[] categoryMasks;
   private short[] categoryTypes;
   private short[] categorySubTypes;
   private byte[] categoryFlags;
   private byte[] categoryRefs;
   private byte[] categoryVolumes;
   private short[][] categoryLists;
   private short[] lotIds;
   private byte[][] floorX;
   private byte[][] floorY;
   private byte[][] floorWidth;
   private byte[][] floorHeight;
   private byte[][] floorEdgeA;
   private byte[][] floorEdgeB;
   private byte[][] wallX;
   private byte[][] wallY;
   private byte[][] wallLength;
   private byte[][] levelObjectX;
   private byte[][] levelObjectY;
   private short[][] levelObjectIds;
   private byte[][] levelObjectFacing;
   private int areaId;
   private static final byte[] STAGES = new byte[]{0, 1, 2};
   private static final short[] STAGE_MUSIC = new short[]{166, 167, 168};
   public byte stageIndex;
   private byte[] liveObjectX;
   private byte[] liveObjectY;
   private short[] liveObjectIds;
   private byte[] liveObjectFacing;
   public int boundsX;
   public int boundsY;
   public int boundsWidth;
   public int boundsHeight;
   public static final int[] DIRECTION_MASKS = new int[]{128, 256, 512, 1024};
   private int isoOriginX;
   private int isoOriginY;
   private int isoOriginXFixed;
   private int isoOriginYFixed;
   private int gridWidth;
   private int gridHeight;
   private int gridHalfWidth;
   private int gridHalfHeight;
   private byte[] cellFlags;
   public Tile groundTile;
   private Tile overlayTile;
   public int cameraX;
   public int cameraY;
   private int[][] palettes;
   private byte[][] categoryTables;
   private int pathStartX;
   private int pathStartY;
   private int pathGoalX;
   private int pathGoalY;
   private int[] waypoints;
   private int waypointCount;
   private int pathCenterX;
   private int pathCenterY;
   private short[] openList;
   private int openCount;
   private short[] closedList;
   private int closedCount;
   private short[][] parentLinks;
   private int[][] pathScore;
   private int[][] pathCost;
   private int[][] pathHeuristic;
   private short[] pathResult;
   private int pathFlags;

   public Lot(GameCanvas canvasArg) {
      this.canvas = canvasArg;
      this.loadObjects();
      this.loadPlacements();
      this.loadPalettes();
      this.initPathfinder();
   }

   private static final short readRef(DataInputStream stream) throws IOException {
      return stream.readShort();
   }

   private void loadObjects() {
      ResourceLoader res = this.canvas.resources;

      try {
         DataInputStream stream;
         short count;
         byte[] kinds = new byte[count = (stream = new DataInputStream(res.openStream(64))).readShort()];
         short[] spriteIds = new short[count];
         short[] spriteVariants = new short[count];
         int[] flags = new int[count];
         short[] linkedIds = new short[count];
         short[] ids = new short[count];
         short[] altIdValues = new short[count];
         byte[] widths = new byte[count];
         byte[] heights = new byte[count];
         short[] subTypes = new short[count];
         short[][] attachmentLists = new short[count][];
         byte[][] frameFlagLists = new byte[count][];
         byte[][] frameMirrorLists = new byte[count][];
         short[][] frameRefLists = new short[count][];

         for (int objIndex = 0; objIndex < count; objIndex++) {
            kinds[objIndex] = stream.readByte();
            spriteIds[objIndex] = readRef(stream);
            spriteVariants[objIndex] = readRef(stream);
            flags[objIndex] = stream.readInt();
            linkedIds[objIndex] = readRef(stream);
            if (linkedIds[objIndex] == -1) {
               linkedIds[objIndex] = 0;
            }

            ids[objIndex] = stream.readShort();
            altIdValues[objIndex] = stream.readShort();
            widths[objIndex] = stream.readByte();
            heights[objIndex] = stream.readByte();
            subTypes[objIndex] = readRef(stream);
            byte attachmentCount;
            short[] attachmentIds = new short[attachmentCount = stream.readByte()];

            for (int attachIndex = 0; attachIndex < attachmentCount; attachIndex++) {
               attachmentIds[attachIndex] = stream.readShort();
            }

            byte frameCount;
            byte[] frameFlagRow = new byte[frameCount = stream.readByte()];
            byte[] frameMirrorRow = new byte[frameCount];

            for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
               frameFlagRow[frameIndex] = stream.readByte();
               frameMirrorRow[frameIndex] = stream.readByte();
            }

            byte refCount;
            short[] refIds = new short[refCount = stream.readByte()];

            for (int refIndex = 0; refIndex < refCount; refIndex++) {
               refIds[refIndex] = readRef(stream);
            }

            attachmentLists[objIndex] = attachmentIds;
            frameFlagLists[objIndex] = frameFlagRow;
            frameMirrorLists[objIndex] = frameMirrorRow;
            frameRefLists[objIndex] = refIds;
         }

         int frameEntryCount;
         short[] frameDataAll = new short[frameEntryCount = stream.readShort() << 2];

         for (int frameSlot = 0; frameSlot < frameEntryCount; frameSlot++) {
            frameDataAll[frameSlot] = readRef(stream);
         }

         byte framePartCount;
         short[][] framePartRows = new short[framePartCount = stream.readByte()][];

         for (int partIndex = 0; partIndex < framePartCount; partIndex++) {
            int partCount;
            short[] partRow = new short[partCount = stream.readByte() << 1];

            for (int partSlot = 0; partSlot < partCount; partSlot++) {
               partRow[partSlot] = stream.readShort();
            }

            framePartRows[partIndex] = partRow;
         }

         stream.readByte();
         stream.readByte();
         byte tableCount;
         byte[][] tables = new byte[tableCount = stream.readByte()][];

         for (int tableIndex = 0; tableIndex < tableCount; tableIndex++) {
            byte[] tableRow = new byte[stream.readByte()];
            stream.readFully(tableRow);
            tables[tableIndex] = tableRow;
         }

         byte categoryCount;
         short[] categoryIdValues = new short[categoryCount = stream.readByte()];
         short[] categoryValueValues = new short[categoryCount];
         int[] categoryMaskValues = new int[categoryCount];
         short[] categoryTypeValues = new short[categoryCount];
         short[] categorySubTypeValues = new short[categoryCount];
         byte[] categoryFlagValues = new byte[categoryCount];

         for (int categoryIndex = 0; categoryIndex < categoryCount; categoryIndex++) {
            categoryIdValues[categoryIndex] = readRef(stream);
            categoryValueValues[categoryIndex] = readRef(stream);
            categoryMaskValues[categoryIndex] = stream.readInt();
            categoryTypeValues[categoryIndex] = stream.readShort();
            categorySubTypeValues[categoryIndex] = stream.readShort();
            categoryFlagValues[categoryIndex] = stream.readByte();
         }

         byte refEntryCount;
         byte[] categoryRefValues = new byte[refEntryCount = stream.readByte()];
         byte[] categoryVolumeValues = new byte[refEntryCount];
         short[][] categoryListRows = new short[refEntryCount][];

         for (int listIndex = 0; listIndex < refEntryCount; listIndex++) {
            categoryRefValues[listIndex] = stream.readByte();
            categoryVolumeValues[listIndex] = stream.readByte();
            byte listCount;
            short[] listRow = new short[listCount = stream.readByte()];

            for (int listSlot = 0; listSlot < listCount; listSlot++) {
               listRow[listSlot] = stream.readByte();
            }

            categoryListRows[listIndex] = listRow;
         }

         stream.close();
         this.objectKind = kinds;
         this.spriteId = spriteIds;
         this.spriteVariant = spriteVariants;
         this.objectFlags = flags;
         this.linkedId = linkedIds;
         this.objectIds = ids;
         this.altIds = altIdValues;
         this.objectWidth = widths;
         this.objectHeight = heights;
         this.subType = subTypes;
         this.attachments = attachmentLists;
         this.frameFlags = frameFlagLists;
         this.frameMirrored = frameMirrorLists;
         this.frameRefs = frameRefLists;
         this.frameData = frameDataAll;
         this.frameParts = framePartRows;
         this.layoutTables = tables;
         this.categoryIds = categoryIdValues;
         this.categoryValues = categoryValueValues;
         this.categoryMasks = categoryMaskValues;
         this.categoryTypes = categoryTypeValues;
         this.categorySubTypes = categorySubTypeValues;
         this.categoryFlags = categoryFlagValues;
         this.categoryRefs = categoryRefValues;
         this.categoryVolumes = categoryVolumeValues;
         this.categoryLists = categoryListRows;
      } catch (IOException ignored) {
      }
   }

   private static final short readShortValue(DataInputStream stream) throws IOException {
      return stream.readShort();
   }

   private void loadPlacements() {
      ResourceLoader res = this.canvas.resources;

      try {
         DataInputStream stream;
         byte count;
         short[] lotIdValues = new short[count = (stream = new DataInputStream(res.openStream(62))).readByte()];
         byte[][] floorXLists = new byte[count][];
         byte[][] floorYLists = new byte[count][];
         byte[][] floorWidthLists = new byte[count][];
         byte[][] floorHeightLists = new byte[count][];
         byte[][] floorEdgeALists = new byte[count][];
         byte[][] floorEdgeBLists = new byte[count][];
         byte[][] wallXLists = new byte[count][];
         byte[][] wallYLists = new byte[count][];
         byte[][] wallLengthLists = new byte[count][];
         byte[][] objectXLists = new byte[count][];
         byte[][] objectYLists = new byte[count][];
         short[][] objectIdLists = new short[count][];
         byte[][] objectFacingLists = new byte[count][];

         for (int objIndex = 0; objIndex < count; objIndex++) {
            lotIdValues[objIndex] = readShortValue(stream);
            byte rowCount;
            byte[] floorXRow = new byte[rowCount = stream.readByte()];
            byte[] floorYRow = new byte[rowCount];
            byte[] floorWidthRow = new byte[rowCount];
            byte[] floorHeightRow = new byte[rowCount];
            byte[] floorEdgeARow = new byte[rowCount];
            byte[] floorEdgeBRow = new byte[rowCount];

            for (int floorIndex = 0; floorIndex < rowCount; floorIndex++) {
               floorXRow[floorIndex] = stream.readByte();
               floorYRow[floorIndex] = stream.readByte();
               floorWidthRow[floorIndex] = stream.readByte();
               floorHeightRow[floorIndex] = stream.readByte();
               floorEdgeARow[floorIndex] = (byte)readShortValue(stream);
               floorEdgeBRow[floorIndex] = (byte)readShortValue(stream);
            }

            byte wallCount;
            byte[] wallXRow = new byte[wallCount = stream.readByte()];
            byte[] wallYRow = new byte[wallCount];
            byte[] wallLengthRow = new byte[wallCount];

            for (int wallIndex = 0; wallIndex < wallCount; wallIndex++) {
               wallXRow[wallIndex] = stream.readByte();
               wallYRow[wallIndex] = stream.readByte();
               wallLengthRow[wallIndex] = stream.readByte();
            }

            byte objectCount;
            byte[] objectXCol = new byte[objectCount = stream.readByte()];
            byte[] objectYCol = new byte[objectCount];
            short[] objectIdCol = new short[objectCount];
            byte[] objectFacingCol = new byte[objectCount];

            for (int objectIndex = 0; objectIndex < objectCount; objectIndex++) {
               objectXCol[objectIndex] = stream.readByte();
               objectYCol[objectIndex] = stream.readByte();
               objectIdCol[objectIndex] = readShortValue(stream);
               objectFacingCol[objectIndex] = (byte)readShortValue(stream);
            }

            floorXLists[objIndex] = floorXRow;
            floorYLists[objIndex] = floorYRow;
            floorWidthLists[objIndex] = floorWidthRow;
            floorHeightLists[objIndex] = floorHeightRow;
            floorEdgeALists[objIndex] = floorEdgeARow;
            floorEdgeBLists[objIndex] = floorEdgeBRow;
            wallXLists[objIndex] = wallXRow;
            wallYLists[objIndex] = wallYRow;
            wallLengthLists[objIndex] = wallLengthRow;
            objectXLists[objIndex] = objectXCol;
            objectYLists[objIndex] = objectYCol;
            objectIdLists[objIndex] = objectIdCol;
            objectFacingLists[objIndex] = objectFacingCol;
         }

         stream.close();
         this.lotIds = lotIdValues;
         this.floorX = floorXLists;
         this.floorY = floorYLists;
         this.floorWidth = floorWidthLists;
         this.floorHeight = floorHeightLists;
         this.floorEdgeA = floorEdgeALists;
         this.floorEdgeB = floorEdgeBLists;
         this.wallX = wallXLists;
         this.wallY = wallYLists;
         this.wallLength = wallLengthLists;
         this.levelObjectX = objectXLists;
         this.levelObjectY = objectYLists;
         this.levelObjectIds = objectIdLists;
         this.levelObjectFacing = objectFacingLists;
      } catch (IOException ignored) {
      }
   }

   public final int areaId() {
      return com.ea.sims3.GameCanvas.indexOfByte(this.areaId, STAGES) != -1 ? 0 : this.areaId;
   }

   public final int lotIdAt(int lotId) {
      return this.lotIds[lotId];
   }

   public final int indexOfLotId(int lotId) {
      return com.ea.sims3.GameCanvas.indexOfShort(lotId, this.lotIds);
   }

   private void resetLiveObjects() {
      this.stageIndex = 0;
      if (this.liveObjectX == null) {
         this.liveObjectX = new byte[150];
         this.liveObjectY = new byte[150];
         this.liveObjectIds = new short[150];
         this.liveObjectFacing = new byte[150];
      }

      com.ea.sims3.GameCanvas.fillShorts(this.liveObjectIds, -1);
      byte[] levelX = this.levelObjectX[0];
      byte[] levelY = this.levelObjectY[0];
      short[] levelIds = this.levelObjectIds[0];
      byte[] levelFacings = this.levelObjectFacing[0];
      System.arraycopy(levelX, 0, this.liveObjectX, 0, levelX.length);
      System.arraycopy(levelY, 0, this.liveObjectY, 0, levelY.length);
      System.arraycopy(levelIds, 0, this.liveObjectIds, 0, levelIds.length);
      System.arraycopy(levelFacings, 0, this.liveObjectFacing, 0, levelFacings.length);
   }

   public final int stageReward() {
      if (this.stageIndex == STAGES.length - 1) {
         return -1;
      } else if (this.stageIndex == 0) {
         return this.canvas.neighborhood.hasMilestone(16) ? 1000 : 0;
      } else {
         return this.canvas.neighborhood.hasMilestone(32) ? 2500 : 0;
      }
   }

   public final boolean isFinalStage() {
      return this.stageIndex == STAGES.length - 1;
   }

   public final int stageMusicId() {
      return STAGE_MUSIC[this.stageIndex];
   }

   public final boolean allItemsPlaced() {
      int placedCount = 0;
      short[] ids = this.liveObjectIds;
      int[] flags = this.objectFlags;

      for (int slotIndex = ids.length - 1; slotIndex >= 0; slotIndex--) {
         short id;
         if ((id = ids[slotIndex]) != -1 && (flags[id] & 131072) != 0 && com.ea.sims3.GameCanvas.indexOfShortFrom(id, ids, slotIndex + 1) == -1) {
            placedCount++;
         }
      }

      int expectedCount = com.ea.sims3.GameCanvas.countAnyFlagInt(131072, flags);
      return placedCount == expectedCount;
   }

   public final void nextStage() {
      this.stageIndex++;
      byte level = STAGES[this.stageIndex];
      byte[] levelX = this.levelObjectX[level];
      byte[] levelY = this.levelObjectY[level];
      short[] levelIds = this.levelObjectIds[level];
      byte[] levelFacings = this.levelObjectFacing[level];
      short[] ids = this.liveObjectIds;
      byte[] liveXs = this.liveObjectX;
      byte[] liveYs = this.liveObjectY;
      byte[] facings = this.liveObjectFacing;

      for (int slotIndex = 0; slotIndex < levelIds.length; slotIndex++) {
         short id = levelIds[slotIndex];
         byte x = levelX[slotIndex];
         byte y = levelY[slotIndex];
         if (id != ids[slotIndex]) {
            if (ids[slotIndex] != -1) {
               int freeSlot = com.ea.sims3.GameCanvas.indexOfShort(-1, ids);
               ids[freeSlot] = ids[slotIndex];
               liveXs[freeSlot] = liveXs[slotIndex];
               liveYs[freeSlot] = liveYs[slotIndex];
               facings[freeSlot] = facings[slotIndex];
               this.canvas.neighborhood.setTipValue(slotIndex, freeSlot);
            }
         } else if ((this.objectFlagsAt(id) & 4) != 0) {
            byte baseX = liveXs[slotIndex];
            byte baseY = liveYs[slotIndex];

            for (int otherIndex = 0; otherIndex < 150; otherIndex++) {
               short otherId;
               if (slotIndex != otherIndex && (otherId = ids[otherIndex]) != -1) {
                  int otherFlags = this.objectFlagsAt(otherId);
                  byte otherX = liveXs[otherIndex];
                  byte otherY = liveYs[otherIndex];
                  if ((otherFlags & 524292) == 0 && otherX == baseX && otherY == baseY) {
                     liveXs[otherIndex] = x;
                     liveYs[otherIndex] = y;
                     if ((otherFlags & 262144) != 0) {
                        int width = this.objectWidthAt(otherId);
                        int height = this.objectHeightAt(otherId);
                        byte facing;
                        if ((facing = facings[otherIndex]) == 1 || facing == 3) {
                           int swap = width;
                           width = height;
                           height = swap;
                        }

                        for (int areaSlotIndex = 0; areaSlotIndex < 150; areaSlotIndex++) {
                           short idAt;
                           if ((idAt = ids[areaSlotIndex]) != -1 && (this.objectFlagsAt(idAt) & 524288) != 0) {
                              byte xAt = liveXs[areaSlotIndex];
                              byte yAt = liveYs[areaSlotIndex];
                              if (xAt <= otherX && yAt <= otherY && xAt > otherX - width && yAt > otherY - height) {
                                 liveXs[areaSlotIndex] = (byte)(liveXs[areaSlotIndex] + (x - otherX));
                                 liveYs[areaSlotIndex] = (byte)(liveYs[areaSlotIndex] + (y - otherY));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         ids[slotIndex] = id;
         liveXs[slotIndex] = x;
         liveYs[slotIndex] = y;
         facings[slotIndex] = levelFacings[slotIndex];
      }

      this.canvas.neighborhood.markPromotionDay();
   }

   public final int addObject(int objectTypeId, int worldX, int worldY, int facing) {
      int slot = com.ea.sims3.GameCanvas.indexOfShort(-1, this.liveObjectIds);
      this.liveObjectIds[slot] = (short)objectTypeId;
      this.liveObjectX[slot] = (byte)toTileX(worldX);
      this.liveObjectY[slot] = (byte)toTileY(worldY);
      this.liveObjectFacing[slot] = (byte)facing;
      return slot;
   }

   public final void removeObject(int slot) {
      this.liveObjectIds[slot] = -1;
      this.canvas.neighborhood.removeTip(0, slot);
   }

   public final void setObjectFacing(int slot, int facing) {
      this.liveObjectFacing[slot] = (byte)facing;
   }

   public final void setObjectId(int slot, int objectId) {
      this.liveObjectIds[slot] = (short)objectId;
   }

   public final void setObjectPosition(int slot, int worldX, int worldY, int facing) {
      this.liveObjectX[slot] = (byte)toTileX(worldX);
      this.liveObjectY[slot] = (byte)toTileY(worldY);
      this.liveObjectFacing[slot] = (byte)facing;
   }

   public final boolean hasObjectKind(int kind) {
      short[] ids = this.liveObjectIds;

      for (int slotIndex = 0; slotIndex < ids.length; slotIndex++) {
         short id;
         if ((id = ids[slotIndex]) != -1 && this.objectKindAt(id) == kind) {
            return true;
         }
      }

      return false;
   }

   public final boolean hasObject(int objectId) {
      return com.ea.sims3.GameCanvas.indexOfShort(objectId, this.liveObjectIds) != -1;
   }

   public final void resetStages() {
      this.stageIndex = 0;
      this.resetLiveObjects();
   }

   public final void readObjects(DataInputStream stream) throws IOException {
      this.stageIndex = stream.readByte();

      for (int slot = 0; slot < this.liveObjectIds.length; slot++) {
         this.liveObjectIds[slot] = stream.readShort();
         this.liveObjectX[slot] = stream.readByte();
         this.liveObjectY[slot] = stream.readByte();
         this.liveObjectFacing[slot] = stream.readByte();
      }
   }

   public final void writeObjects(DataOutputStream out) throws IOException {
      out.writeByte(this.stageIndex);

      for (int slot = 0; slot < this.liveObjectIds.length; slot++) {
         out.writeShort(this.liveObjectIds[slot]);
         out.writeByte(this.liveObjectX[slot]);
         out.writeByte(this.liveObjectY[slot]);
         out.writeByte(this.liveObjectFacing[slot]);
      }
   }

   public final void setBounds(int x, int y, int width, int height) {
      this.boundsX = x;
      this.boundsY = y;
      this.boundsWidth = width;
      this.boundsHeight = height;
   }

   private void initGrid() {
      this.gridWidth = 64;
      this.gridHeight = 64;
      this.gridHalfWidth = this.gridWidth >> 1;
      this.gridHalfHeight = this.gridHeight >> 1;
      this.isoOriginX = 512;
      this.isoOriginXFixed = this.isoOriginX << 16;
      this.isoOriginY = 264;
      this.isoOriginYFixed = this.isoOriginY << 16;
      if (this.cellFlags == null) {
         this.cellFlags = new byte[this.gridWidth * this.gridHeight];
      }

      com.ea.sims3.GameCanvas.fillBytes(this.cellFlags, 0);
   }

   public final void loadArea(int area) {
      if (area == 0) {
         area = STAGES[this.stageIndex];
      }

      this.areaId = area;
      this.initGrid();
      this.buildGroundTiles(area);
      this.buildScene(area);
   }

   private void buildScene(int level) {
      GameScreen screen = (GameScreen)this.canvas.screen;
      byte[] xRow = this.levelObjectX[level];
      byte[] yRow = this.levelObjectY[level];
      short[] ids = this.levelObjectIds[level];
      byte[] facings = this.levelObjectFacing[level];
      if (this.areaId() == 0) {
         xRow = this.liveObjectX;
         yRow = this.liveObjectY;
         ids = this.liveObjectIds;
         facings = this.liveObjectFacing;
      }

      boolean spawnFound = false;
      int spawnX = 0;
      int spawnY = 0;
      int count = ids.length;

      for (int slotIndex = 0; slotIndex < count; slotIndex++) {
         short id;
         if ((id = ids[slotIndex]) != -1) {
            byte x = xRow[slotIndex];
            byte y = yRow[slotIndex];
            byte facing = facings[slotIndex];
            screen.createEntityAtTile(id, x, y, facing, slotIndex);
            if (!spawnFound && this.objectKind[id] == 16) {
               spawnFound = true;
               spawnX = x;
               spawnY = y;
            }
         }
      }

      if (spawnFound) {
         int halfWidth = this.gridHalfWidth;
         spawnX += halfWidth;
         spawnY += 0;
         this.paintRoomEdges(spawnX, spawnY, 3, 3);
      }

      this.buildRoomEdges();
      int spawnMode = this.areaId() == 0 ? 2 : 3;
      spawnX = com.ea.sims3.GameCanvas.indexOfShort(spawnMode, ids);
      int playerSlot = xRow[spawnX];
      byte playerY = yRow[spawnX];
      byte playerFacing = facings[spawnX];
      screen.createEntityAtTile(0, playerSlot, playerY, playerFacing, 0);
      playerSlot = spawnMode == 2 ? spawnX : -1;
      Neighborhood neighborhood = screen.neighborhood;
      int simCount = screen.neighborhood.simCount;

      for (int simIndex = 1; simIndex < simCount; simIndex++) {
         int simFlags;
         if (neighborhood.simHomeLotOf(simIndex) == level && ((simFlags = neighborhood.lotStateNow(simIndex)) & 8) != 0) {
            playerSlot = com.ea.sims3.GameCanvas.indexOfShortFrom(2, ids, playerSlot + 1);
            byte simX = xRow[playerSlot];
            byte simY = yRow[playerSlot];
            byte simFacing = facings[playerSlot];
            Sim sim = (Sim)screen.createEntityAtTile(1, simX, simY, simFacing, simIndex);
            if ((simFlags & 16) != 0 && this.canvas.randomPercent() < 30) {
               screen.assignFreeWillAction(sim);
            }
         }
      }
   }

   public final boolean hasObjectAt(int tileX, int tileY) {
      byte[] xValues = this.liveObjectX;
      byte[] yValues = this.liveObjectY;
      short[] ids = this.liveObjectIds;

      for (int slotIndex = 0; slotIndex < ids.length; slotIndex++) {
         short id;
         if ((id = ids[slotIndex]) != -1 && (this.objectFlagsAt(id) & 524288) != 0 && tileX == xValues[slotIndex] && tileY == yValues[slotIndex]) {
            return true;
         }
      }

      return false;
   }

   private static int directionOfMask(int mask) {
      for (int index = 0; index < 4; index++) {
         if ((mask & DIRECTION_MASKS[index]) != 0) {
            return index;
         }
      }

      return -1;
   }

   public final boolean footprintFree(int x, int y, int width, int length, int direction) {
      boolean free = true;
      if (direction != 0 && direction != 2) {
         if (direction == 1 || direction == 3) {
            byte stepAlt = 1;
            if (direction == 3) {
               stepAlt = -1;
            }

            int xAtAlt = x;
            int yAtAlt = y - width * stepAlt;

            for (int stepIndex = 0; stepIndex < length && free; stepIndex++) {
               free = (this.cellFlagsAt(xAtAlt - stepIndex * stepAlt, yAtAlt) & 64) != 0;
            }
         }
      } else {
         byte step = 1;
         if (direction == 2) {
            step = -1;
         }

         int xAt = x - width * step;
         int yAt = y;

         for (int altStepIndex = 0; altStepIndex < length && free; altStepIndex++) {
            free = (this.cellFlagsAt(xAt, yAt - altStepIndex * step) & 64) != 0;
         }
      }

      return free;
   }

   public final int directionMaskAt(int objectTypeId, int x, int y) {
      int flags = this.objectFlagsAt(objectTypeId);
      int width = this.objectWidthAt(objectTypeId);
      int height = this.objectHeightAt(objectTypeId);
      int frames = this.frameCountAt(objectTypeId);
      int mask = 0;

      for (int dir = 0; dir < DIRECTION_MASKS.length; dir++) {
         int dirMask = DIRECTION_MASKS[dir];
         if ((flags & dirMask) != 0) {
            int widthRotated = width;
            int heightRotated = height;
            if (dir == 1 || dir == 3) {
               widthRotated = height;
               heightRotated = width;
            }

            if ((flags & 524288) != 0 || this.isAreaPassable(x, y, widthRotated, heightRotated)) {
               if (frames > 0) {
                  int hits = 0;

                  for (int frame = 0; frame < frames; frame++) {
                     int dx = this.framePartX(objectTypeId, frame, dir);
                     int dy = this.framePartY(objectTypeId, frame, dir);
                     if (this.isPassable(x + dx, y + dy)) {
                        hits++;
                     }
                  }

                  if (hits < 1) {
                     continue;
                  }
               }

               if (((flags & 1048576) == 0 || this.footprintFree(x, y, width, height, dir)) && ((flags & 2097152) == 0 || !this.footprintFree(x, y, width, height, dir))) {
                  mask |= dirMask;
               }
            }
         }
      }

      return mask;
   }

   public final int spawnWalkers(int objectTypeId) {
      GameScreen screen = (GameScreen)this.canvas.screen;
      byte[] xValues = this.liveObjectX;
      byte[] yValues = this.liveObjectY;
      short[] ids = this.liveObjectIds;
      byte[] facings = this.liveObjectFacing;
      int flags = this.objectFlagsAt(objectTypeId);
      int width = this.objectWidthAt(objectTypeId);
      int height = this.objectHeightAt(objectTypeId);
      int spawned = 0;
      int count = ids.length;

      for (int slotIndex = 0; slotIndex < count; slotIndex++) {
         short id;
         if ((id = ids[slotIndex]) != -1) {
            byte x = xValues[slotIndex];
            byte y = yValues[slotIndex];
            byte facing = facings[slotIndex];
            int otherFlags = this.objectFlagsAt(id);
            int otherWidth = this.objectWidthAt(id);
            int otherHeight = this.objectHeightAt(id);
            if ((flags & 524288) != 0) {
               if ((otherFlags & 262144) != 0) {
                  if (facing == 1 || facing == 3) {
                     int swap = otherWidth;
                     otherWidth = otherHeight;
                     otherHeight = swap;
                  }

                  for (int dx = 0; dx < otherWidth; dx++) {
                     for (int dy = 0; dy < otherHeight; dy++) {
                        int xAt = x - dx;
                        int yAt = y - dy;
                        int dirAlt;
                        if (!this.hasObjectAt(xAt, yAt) && (dirAlt = directionOfMask(this.directionMaskAt(objectTypeId, xAt, yAt))) != -1) {
                           screen.createEntityAtTile(11, xAt, yAt, dirAlt, -1);
                           spawned++;
                        }
                     }
                  }
               }
            } else {
               int dir;
               if ((otherFlags & 4) != 0 && otherWidth == width && otherHeight == height && (dir = directionOfMask(this.directionMaskAt(objectTypeId, x, y))) != -1) {
                  screen.createEntityAtTile(10, x, y, dir, -1);
                  spawned++;
               }
            }
         }
      }

      return spawned;
   }

   public final void openHouse(int imageId) {
      this.initGrid();
      GameCanvas canvas = this.canvas;
      Image image = com.ea.sims3.ResourceLoader.loadImage(0);
      Tile tile;
      (tile = new Tile(imageId, image)).setBounds(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
      tile.setBackingEnabled(true);
      this.groundTile = tile;
      this.markBorderCells(imageId);
      this.createFurniture(true);
      GameScreen screen;
      Entity sim;
      int x = toTileX((sim = (screen = this.canvas.gameScreen()).findEntityByType(3)).posX);
      int y = toTileY(sim.posY);
      screen.createEntityAtTile(0, x, y, 3, 0);
      int simId = canvas.walkTargetFrame;
      if (canvas.walkTargetFrame != -1) {
         Entity entity = screen.createEntityAtTile(1, x, y - 1, 1, simId);
         screen.followEntity(entity);
         screen.openInteractionMenu(entity);
      }
   }

   public final void openArea(boolean resetView) {
      this.initGrid();
      GameCanvas canvas = this.canvas;
      Image image = com.ea.sims3.ResourceLoader.loadImage(1);
      Tile tile;
      (tile = new Tile(40, image)).setBounds(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
      tile.setBackingEnabled(true);
      this.groundTile = tile;
      this.markBlockedCells();
      this.createFurniture(false);
      GameScreen screen = canvas.gameScreen();
      if (resetView) {
         Neighborhood neighborhood;
         int simIndex = (neighborhood = screen.neighborhood).lotOccupantType(neighborhood.simLot(0));
         Entity sim = screen.findEntityByType(simIndex);
         screen.createEntityNear(0, sim, 0);
         screen.startInteraction(159, sim, 0, 0);
      } else {
         int savedLotIndex = canvas.houseIndex;
         if (canvas.houseIndex != -1) {
            this.areaId = 13;
            int lotId = this.lotIdAt(savedLotIndex);
            Entity savedEntity = screen.findEntityByType(lotId);
            screen.createEntityNear(0, savedEntity, 0);
         } else {
            screen.createEntity(0, canvas.simPosX, canvas.simPosY, 0, 0);
            int simId = canvas.walkTargetFrame;
            if (canvas.walkTargetFrame != -1) {
               screen.createEntity(1, canvas.walkTargetX, canvas.walkTargetY, 0, simId);
            }

            canvas.enterHouseWith((Sim) null);
         }
      }
   }

   private void createFurniture(boolean snapToTiles) {
      GameScreen screen = this.canvas.gameScreen();
      Tile tile = this.groundTile;
      int frameCount = this.groundTile.frameCount();
      short[] frames = tile.frames;

      for (int frame = 0; frame < frameCount; frame++) {
         int offset = frame * 3;
         short spriteId = frames[offset + 0];
         int x = frames[offset + 1] << 16;
         int y = frames[offset + 2] << 16;
         int worldX = this.toWorldX(x, y);
         int worldY = this.toWorldY(x, y);
         if (snapToTiles) {
            worldX = toFixedX(toTileX(worldX));
            worldY = toFixedY(toTileY(worldY));
         }

         screen.createEntity(spriteId, worldX, worldY, 0, -1);
      }

      int rowCount = tile.rowCount();
      short[] rows = tile.rows;

      for (int row = 0; row < rowCount; row++) {
         int rowOffset = row * 3;
         short value = rows[rowOffset + 0];
         int rowX = rows[rowOffset + 1] << 16;
         int rowY = rows[rowOffset + 2] << 16;
         int rowWorldX = this.toWorldX(rowX, rowY);
         int rowWorldY = this.toWorldY(rowX, rowY);
         if (snapToTiles) {
            rowWorldX = toFixedX(toTileX(rowWorldX));
            rowWorldY = toFixedY(toTileY(rowWorldY));
         }

         Entity entity;
         (entity = screen.createEntity(98, rowWorldX, rowWorldY, 0, -1)).playIdleAnimation(value);
         if (value == 170 || value == 171 || value == 172 || value == 173 || value == 174) {
            entity.setValueC(-50);
         } else if (value == 151 || value == 152 || value == 153 || value == 154) {
            entity.setValueC(-18);
         }
      }
   }

   public final void dispose() {
      this.groundTile = null;
      this.overlayTile = null;
   }

   public final void draw(Graphics gfx) {
      this.drawScene(gfx);
   }

   public final int viewCenterX() {
      return this.boundsX + (this.boundsWidth >> 1) - (this.cameraX >> 16);
   }

   public final int viewCenterY() {
      return this.boundsY + (this.boundsHeight >> 1) - (this.cameraY >> 16);
   }

   public final int toScreenFixedX(int worldX, int worldY) {
      return (worldX - worldY << 1) + this.isoOriginXFixed;
   }

   public final int toScreenFixedY(int worldX, int worldY) {
      return worldX + worldY + this.isoOriginYFixed;
   }

   public final int toScreenX(int worldX, int worldY) {
      return (worldX - worldY >> 15) + this.isoOriginX;
   }

   public final int toScreenY(int worldX, int worldY) {
      return (worldX + worldY >> 16) + this.isoOriginY;
   }

   public final int toWorldX(int screenX, int screenY) {
      return (screenX - this.isoOriginXFixed >> 1) + (screenY - this.isoOriginYFixed) >> 1;
   }

   public final int toWorldY(int screenX, int screenY) {
      return (-(screenX - this.isoOriginXFixed) >> 1) + (screenY - this.isoOriginYFixed) >> 1;
   }

   private static int subtract(int left, int right) {
      return left - right;
   }

   private static int add(int left, int right) {
      return left + right;
   }

   public static int toFixedX(int value) {
      return (value << 19) + 262144;
   }

   public static int toFixedY(int value) {
      return (value << 19) + 262144;
   }

   public static int toTileX(int value) {
      return value >> 19;
   }

   public static int toTileY(int value) {
      return value >> 19;
   }

   public final boolean isWalkableAt(int x, int y) {
      return this.isPassable(toTileX(x), toTileY(y));
   }

   private boolean isPassable(int x, int y) {
      return (this.cellFlagsAt(x, y) & 128) == 0;
   }

   public final int cellFlagsAt(int x, int y) {
      x += this.gridHalfWidth;
      y += this.gridHalfHeight;
      if (x >= 0 && x < this.gridWidth && y >= 0 && y < this.gridHeight) {
         int index = x + y * this.gridWidth;
         return this.cellFlags[index];
      } else {
         return 128;
      }
   }

   private void setCellFlags(int x, int y, int clearMask, int setMask) {
      x += this.gridHalfWidth;
      y += this.gridHalfHeight;
      if (x >= 0 && x < this.gridWidth && y >= 0 && y < this.gridHeight) {
         byte[] cells = this.cellFlags;
         int index = x + y * this.gridWidth;
         int value = cells[index] & clearMask;
         cells[index] = (byte)(value | setMask);
      }
   }

   public final void fillCellFlags(int x, int y, int width, int height, int clearMask, int setMask) {
      byte[] cells = this.cellFlags;
      int stride = this.gridWidth;
      int startX = this.gridHalfWidth + x;
      int x1 = this.gridHalfWidth + x + width;
      int startY = this.gridHalfHeight + y;
      int y1 = this.gridHalfHeight + y + height;

      for (int col = startX; col < x1; col++) {
         for (int row = startY; row < y1; row++) {
            int index = col + row * stride;
            int value = cells[index] & clearMask;
            cells[index] = (byte)(value | setMask);
         }
      }
   }

   private boolean isAreaPassable(int x, int y, int width, int height) {
      for (int dx = 0; dx < width; dx++) {
         for (int dy = 0; dy < height; dy++) {
            if (!this.isPassable(x - dx, y - dy)) {
               return false;
            }
         }
      }

      return true;
   }

   private void drawScene(Graphics gfx) {
      Tile tile = this.groundTile;
      int clipX = gfx.getClipX();
      int clipY = gfx.getClipY();
      int clipWidth = gfx.getClipWidth();
      int clipHeight = gfx.getClipHeight();
      gfx.setClip(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
      tile.worldX = this.cameraX - (this.boundsWidth >> 1 << 16);
      tile.worldY = this.cameraY - (this.boundsHeight >> 1 << 16);
      tile.draw(gfx);
      this.drawEntities(gfx);
      gfx.setClip(clipX, clipY, clipWidth, clipHeight);
   }

   private void buildGroundTiles(int level) {
      if (this.groundTile == null) {
         Image image = com.ea.sims3.ResourceLoader.loadImage(0);
         Tile tile;
         (tile = new Tile(64, 64, image)).setBounds(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
         tile.setBackingEnabled(true);
         this.groundTile = tile;
      }

      if (this.overlayTile == null) {
         Image overlayImage = com.ea.sims3.ResourceLoader.loadImage(2);
         Tile overlay = new Tile(64, 64, overlayImage);
         this.overlayTile = overlay;
         overlay.setBounds(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
         this.groundTile.next = overlay;
      }

      byte[] floorXs = this.floorX[level];
      byte[] floorYs = this.floorY[level];
      byte[] widths = this.floorWidth[level];
      byte[] heights = this.floorHeight[level];
      byte[] edgeA = this.floorEdgeA[level];
      byte[] edgeB = this.floorEdgeB[level];
      byte[] wallsX = this.wallX[level];
      byte[] wallsY = this.wallY[level];
      byte[] wallLengths = this.wallLength[level];
      int floorCount = floorXs.length;
      int wallCount = wallsX.length;

      for (int floorIndex = 0; floorIndex < floorCount; floorIndex++) {
         byte x = floorXs[floorIndex];
         byte y = floorYs[floorIndex];
         byte tileWidth = widths[floorIndex];
         byte tileHeight = heights[floorIndex];
         this.fillCellFlags(x, y, tileWidth, tileHeight, 255, floorIndex + 1 | 16);
         this.fillCellFlags(x - 1, y - 1, tileWidth + 2, 1, 255, 128);
         this.fillCellFlags(x - 1, y + tileHeight, tileWidth + 2, 1, 255, 128);
         this.fillCellFlags(x - 1, y, 1, tileHeight, 255, 128);
         this.fillCellFlags(x + tileWidth, y, 1, tileHeight, 255, 128);
      }

      for (int wallIndex = 0; wallIndex < wallCount; wallIndex++) {
         byte wallXAt = wallsX[wallIndex];
         byte wallYAt = wallsY[wallIndex];
         int length = Math.abs(wallLengths[wallIndex]);
         if ((this.cellFlagsAt(wallXAt - 1, wallYAt) & 128) != 0) {
            this.fillCellFlags(wallXAt, wallYAt, length, 1, -129, 0);
         } else {
            this.fillCellFlags(wallXAt, wallYAt, 1, length, -129, 0);
            wallLengths[wallIndex] = (byte)(-length);
         }
      }

      com.ea.sims3.GameCanvas.fillBytes(this.groundTile.cellIndices, 16);
      com.ea.sims3.GameCanvas.fillBytes(this.overlayTile.cellIndices, 60);
      int halfWidth = this.gridHalfWidth;

      for (int floorSlot = 0; floorSlot < floorCount; floorSlot++) {
         int posX = halfWidth + floorXs[floorSlot];
         int posY = 0 + floorYs[floorSlot];
         byte tileW = widths[floorSlot];
         byte tileH = heights[floorSlot];
         byte edgeAId = edgeA[floorSlot];
         byte edgeBId = edgeB[floorSlot];
         this.paintFloorRect(posX, posY, tileW, tileH, edgeAId, edgeBId);
      }

      for (int wIdx = 0; wIdx < wallCount; wIdx++) {
         byte wallTileX = wallsX[wIdx];
         byte wallTileY = wallsY[wIdx];
         byte len;
         int flagsA;
         int flagsB;
         if ((len = wallLengths[wIdx]) > 0) {
            flagsA = this.cellFlagsAt(wallTileX, wallTileY - 1);
            flagsB = this.cellFlagsAt(wallTileX, wallTileY + 1);
         } else {
            flagsA = this.cellFlagsAt(wallTileX - 1, wallTileY);
            flagsB = this.cellFlagsAt(wallTileX + 1, wallTileY);
         }

         int edgeAIndex = (flagsA & 15) - 1;
         int edgeBIndex = (flagsB & 15) - 1;
         byte edgeAId2 = edgeAIndex == -1 ? 3 : edgeA[edgeAIndex];
         byte edgeBId2 = edgeBIndex == -1 ? 0 : edgeB[edgeBIndex];
         this.paintWallStrip(halfWidth + wallTileX, 0 + wallTileY, Math.abs((int)len), edgeAId2, edgeBId2, len < 0 ? 2 : 1);
      }

      this.fixWallCorners();
      this.markPerimeter();
      this.groundTile.invalidateBacking();
   }

   private void paintFloorRect(int x, int y, int width, int height, int edgeId, int textureId) {
      Tile tile = this.groundTile;
      byte[] groundCells = this.groundTile.cellIndices;
      byte[] overlayCells = this.overlayTile.cellIndices;
      int stride = tile.columns;
      int isoX = subtract(x, y);
      int isoY = add(x, y);
      int rows = width + height;
      byte fillId = this.tableValueAt(5, edgeId);
      byte nearId = this.tableValueAt(12, textureId);
      byte farId = this.tableValueAt(18, textureId);
      byte sideAId = this.tableValueAt(8, textureId);
      byte sideBId = this.tableValueAt(14, textureId);
      int col = isoX - height;
      int row;
      int rowB = (row = isoY + height) + 1;

      for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
         boolean inHeight = rowIndex < height;
         boolean inWidth = rowIndex < width;
         byte topId = inHeight ? nearId : farId;
         overlayCells[col + (row - 0) * stride] = topId;
         byte bottomId = inHeight ? sideAId : sideBId;
         overlayCells[col + (row - 1) * stride] = bottomId;
         int rowId = inWidth ? 56 : 48;
         overlayCells[col + (rowB + 0) * stride] = (byte)rowId;
         int span = rowB - row + 2;

         for (int cellIndex = 0; cellIndex < span; cellIndex++) {
            groundCells[col + (row + cellIndex) * stride] = fillId;
         }

         if (rowIndex == 0) {
            overlayCells[col - 1 + (row + 0) * stride] = this.tableValueAt(20, textureId);
         } else if (rowIndex == rows - 1) {
            overlayCells[col + 1 + (row + 0) * stride] = this.tableValueAt(21, textureId);
         }

         col++;
         if (rowIndex != width - 1) {
            rowB += inWidth ? 1 : -1;
         }

         if (rowIndex != height - 1) {
            row += inHeight ? -1 : 1;
         }
      }
   }

   private void paintWallStrip(int x, int y, int length, int edgeIndex, int textureId, int orientation) {
      Tile tile = this.groundTile;
      int stride = this.groundTile.columns;
      byte[] cells = this.overlayTile.cellIndices;
      int isoX = subtract(x, y);
      int isoY = add(x, y);
      boolean leftSide;
      if (leftSide = (orientation & 1) != 0) {
         isoX--;
         byte topA = this.tableValueAt(24, textureId);
         byte topB = this.tableValueAt(25, textureId);
         byte side = this.tableValueAt(7, edgeIndex);
         int index = isoX + isoY * stride;
         cells[index] = topA;
         index += stride;
         cells[index] = topB;
         index += stride;
         cells[index] = side;

         for (int stepIndex = 0; stepIndex < length; stepIndex++) {
            index = ++isoX + ++isoY * stride;
            cells[index] = 60;
            index += stride;
            cells[index] = 60;
            index += stride;
            cells[index] = side;
         }

         index = ++isoX + ++isoY * stride;
         cells[index] = 52;
         index += stride;
         if (cells[index] == 60) {
            cells[index] = this.tableValueAt(14, textureId);
            index += stride;
            cells[index] = this.tableValueAt(18, textureId);
         }
      } else {
         isoX -= 2;
         isoY += 2;
         byte topC = this.tableValueAt(22, textureId);
         byte topD = this.tableValueAt(23, textureId);
         byte side2 = this.tableValueAt(6, edgeIndex);
         int index2 = isoX + (isoY + 0) * stride;
         cells[index2] = 52;
         index2 += stride;
         if (cells[index2] == 60) {
            cells[index2] = this.tableValueAt(8, textureId);
            index2 += stride;
            cells[index2] = this.tableValueAt(12, textureId);
         }

         for (int stepIndex2 = 0; stepIndex2 < length; stepIndex2++) {
            index2 = ++isoX + (--isoY + 0) * stride;
            cells[index2] = 60;
            index2 += stride;
            cells[index2] = 60;
            index2 += stride;
            cells[index2] = side2;
         }

         index2 = ++isoX + (--isoY + 0) * stride;
         cells[index2] = topC;
         index2 += stride;
         cells[index2] = topD;
         index2 += stride;
         cells[index2] = side2;
      }
   }

   private void paintRoomEdges(int x, int y, int width, int height) {
      GameCanvas canvas = this.canvas;
      Tile tile = this.groundTile;
      byte[] cells = this.groundTile.cellIndices;
      int stride = tile.columns;
      int isoX = subtract(x, y);
      int isoY = add(x, y);
      int rows = width + height + 2;
      int col = isoX - height - 1;
      int rowA;
      int rowB = rowA = isoY + height + 1;
      cells[col - 1 + (rowA - 2) * stride] = 4;
      cells[col - 1 + (rowA - 1) * stride] = 12;
      cells[col - 1 + rowA * stride] = 13;
      cells[col - 1 + (rowA + 1) * stride] = 21;
      cells[col - 2 + (rowA - 1) * stride] = 4;
      cells[col - 2 + rowA * stride] = 21;

      for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
         boolean inHeight = rowIndex < height + 1;
         boolean inWidth = rowIndex < width + 1;
         int span = rowB - rowA;

         for (int cellIndex = 0; cellIndex < span; cellIndex++) {
            int id = canvas.randomPercent() < 50 ? 6 : 7;
            cells[col + (rowA + cellIndex) * stride] = (byte)id;
         }

         int idE = inHeight ? 22 : 23;
         cells[col + (rowA - 1) * stride] = (byte)idE;
         int idF = inHeight ? 4 : 5;
         cells[col + (rowA - 3) * stride] = (byte)idF;
         int idB = inWidth ? 15 : 14;
         cells[col + rowB * stride] = (byte)idB;
         int idC = inWidth ? 21 : 20;
         cells[col + (rowB + 2) * stride] = (byte)idC;
         int idD = canvas.randomPercent() < 50 ? 12 : 13;
         cells[col + (rowA - 2) * stride] = (byte)idD;
         cells[col + (rowB + 1) * stride] = (byte)idD;
         if (rowIndex != width) {
            rowB += inWidth ? 1 : -1;
         }

         if (rowIndex != height) {
            rowA += inHeight ? -1 : 1;
         }

         col++;
      }

      cells[col + (rowA - 3) * stride] = 5;
      cells[col + (rowA - 2) * stride] = 12;
      cells[col + (rowA - 1) * stride] = 13;
      cells[col + rowA * stride] = 20;
      cells[col + 1 + (rowA - 2) * stride] = 5;
      cells[col + 1 + (rowA - 1) * stride] = 20;
   }

   private void buildRoomEdges() {
      GameScreen screen = this.canvas.gameScreen();
      boolean flipped = false;
      Entity entity;
      if ((entity = screen.findEntityByObjectType(10)).facing == 1) {
         flipped = true;
         byte[] patternTable = this.tableAt(1);
         this.paintEdgePattern(1, patternTable);
         byte[] patternA = new byte[]{16, 86, 94, 6, 6, 6, 6, 6};
         this.paintEdgePattern(0, patternA);
      } else {
         byte[] patternB = this.tableAt(0);
         this.paintEdgePattern(0, patternB);
         byte[] patternC = new byte[]{17, 87, 95, 7, 7, 7, 7, 7};
         this.paintEdgePattern(1, patternC);
      }

      if (!flipped) {
         boolean unusedFlag = false;
         int y = toTileY(entity.posY);
         screen.createEntityAtTile(57, 21, y, 0, -1);

         for (int yDown = y + 6; yDown < 15; yDown += 4) {
            screen.createEntityAtTile(54, 21, yDown, 0, -1);
         }

         for (int yUp = y - 6; yUp > -14; yUp -= 4) {
            screen.createEntityAtTile(54, 21, yUp, 0, -1);
         }
      } else {
         int x = toTileX(entity.posX);
         boolean unusedFlag2 = false;
         screen.createEntityAtTile(57, x, 21, 1, -1);

         for (int xRight = x + 6; xRight < 15; xRight += 4) {
            screen.createEntityAtTile(54, xRight, 21, 1, -1);
         }

         for (int xLeft = x - 6; xLeft > -14; xLeft -= 4) {
            screen.createEntityAtTile(54, xLeft, 21, 1, -1);
         }
      }
   }

   private void paintEdgePattern(int orientation, byte[] pattern) {
      Tile tile = this.groundTile;
      byte[] cells = this.groundTile.cellIndices;
      int stride = tile.columns;
      int rows = tile.rowCountValue;
      int patternLength = pattern.length - 1;
      int col = stride - patternLength;
      int row = rows - 1;
      byte step = 1;
      if (orientation == 1) {
         col = patternLength - 1;
         step = -1;
      }

      byte[] mirrorTable = this.tableAt(2);

      for (byte[] mapTable = this.tableAt(3); col >= 0 && col < stride; row += -1) {
         for (int rowIndex = 0; row + rowIndex < rows && rowIndex < pattern.length; rowIndex++) {
            byte id = pattern[rowIndex];
            int index;
            if ((col & 1) == 0 && (index = com.ea.sims3.GameCanvas.indexOfByte(id, mirrorTable)) != -1) {
               id = mapTable[index];
            }

            index = row + rowIndex;
            cells[col + index * stride] = id;
            int worldX = com.ea.sims3.Tile.toWorld16(col);
            int worldY = com.ea.sims3.Tile.toWorld8(index);
            int fixedX = this.toWorldX(worldX, worldY);
            int fixedY = this.toWorldY(worldX, worldY);
            int tileX = toTileX(fixedX);
            int tileY = toTileY(fixedY);
            this.setCellFlags(tileX, tileY, 255, 128);
         }

         col += step;
      }
   }

   private void fixWallCorners() {
      Tile tile = this.groundTile;
      byte[] overlayCells = this.overlayTile.cellIndices;
      byte[] groundCells = tile.cellIndices;
      int stride = tile.columns;
      int rows = tile.rowCountValue;
      byte[] table4 = this.tableAt(4);
      byte[] table5 = this.tableAt(5);
      byte[] table26 = this.tableAt(26);
      byte[] table8 = this.tableAt(8);
      byte[] table14 = this.tableAt(14);
      byte[] table20 = this.tableAt(20);
      byte[] table21 = this.tableAt(21);

      for (int x = 1; x < stride - 1; x++) {
         boolean joined = false;

         for (int y = 0; y < rows - 1; y++) {
            int index;
            int indexBelow = (index = x + (y + 0) * stride) + stride;
            byte groundAbove = groundCells[index];
            byte groundBelow = groundCells[indexBelow];
            byte overlayAbove = overlayCells[index];
            byte overlayBelow = overlayCells[indexBelow];
            if (overlayAbove == 56 && overlayBelow == 60) {
               overlayBelow = table14[0];
            }

            if (overlayAbove == 48 && overlayBelow == 60) {
               overlayBelow = table8[0];
            }

            if ((this.indexInTable(overlayAbove, 27) != -1 || com.ea.sims3.GameCanvas.indexOfByte(overlayAbove, table20) != -1) && overlayBelow == 60) {
               overlayBelow = table14[0];
               int indexA = index + stride;
               if (overlayCells[indexA] == 60) {
                  overlayCells[indexA] = this.tableValueAt(18, 0);
               }
            }

            if ((com.ea.sims3.GameCanvas.indexOfByte(overlayAbove, table26) != -1 || com.ea.sims3.GameCanvas.indexOfByte(overlayAbove, table21) != -1) && overlayBelow == 60) {
               overlayBelow = table8[0];
               int indexB = index + stride;
               if (overlayCells[indexB] == 60) {
                  overlayCells[indexB] = this.tableValueAt(12, 0);
               }
            }

            int indexC;
            if ((indexC = com.ea.sims3.GameCanvas.indexOfByte(overlayAbove, table14)) != -1) {
               if (com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table8) != -1 || com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table20) != -1) {
                  overlayAbove = table21[indexC];
               } else if (com.ea.sims3.GameCanvas.indexOfByte(groundBelow, table5) != -1 && overlayBelow == 60) {
                  overlayBelow = this.tableValueAt(18, indexC);
               }
            }

            if ((indexC = com.ea.sims3.GameCanvas.indexOfByte(overlayAbove, table8)) != -1) {
               if (com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table14) != -1 || com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table21) != -1) {
                  overlayAbove = table20[indexC];
               } else if (com.ea.sims3.GameCanvas.indexOfByte(groundBelow, table5) != -1 && overlayBelow == 60) {
                  overlayBelow = this.tableValueAt(12, indexC);
               }
            }

            int indexD;
            if ((indexD = com.ea.sims3.GameCanvas.indexOfByte(groundAbove, table5)) != -1) {
               int indexE;
               if ((indexE = com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table8)) == -1 && (indexE = com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table20)) == -1) {
                  if ((indexE = com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table14)) == -1 && (indexE = com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table21)) == -1) {
                     byte[] table24 = this.tableAt(24);
                     if (indexD == 3) {
                        int randomIdx = this.canvas.randomRange(0, table4.length - 1);
                        groundAbove = table4[randomIdx];
                     }

                     byte overlayRight;
                     if (com.ea.sims3.GameCanvas.indexOfByte(overlayRight = overlayCells[x + 1 + y * stride], table20) != -1) {
                        if (joined) {
                           if (overlayAbove == 60) {
                              overlayAbove = this.tableValueAt(22, 0);
                           }
                        } else {
                           byte endId = this.tableValueAt(30, 0);
                           byte midId = this.tableValueAt(29, 0);

                           for (int rowIdLoop = 0; rowIdLoop < 5; rowIdLoop++) {
                              byte rowId = rowIdLoop == 4 ? endId : midId;
                              overlayCells[x + (y - rowIdLoop - 1) * stride] = rowId;
                           }

                           overlayAbove = this.tableValueAt(28, 0);
                           int rowIndex = x + (y - 1 - 5) * stride;
                           overlayCells[rowIndex] = this.tableValueAt(22, 0);
                           joined = true;
                        }

                        if (overlayBelow == 60) {
                           overlayBelow = this.tableValueAt(23, 0);
                        }
                     }

                     byte overlayLeft;
                     if (com.ea.sims3.GameCanvas.indexOfByte(overlayLeft = overlayCells[x - 1 + y * stride], table21) != -1) {
                        if (overlayAbove == 60) {
                           byte overlayDiag;
                           int idxDiag = com.ea.sims3.GameCanvas.indexOfByte(overlayDiag = overlayCells[x - 1 + (y + 1) * stride], table8);
                           overlayAbove = table24[idxDiag];
                           overlayBelow = this.tableValueAt(25, idxDiag);
                        }
                     } else {
                        int idx8;
                        if ((idx8 = com.ea.sims3.GameCanvas.indexOfByte(overlayLeft, table26)) != -1) {
                           if (!joined) {
                              idx8 = 0;
                              byte endId2 = this.tableValueAt(33, 0);
                              byte midId2 = this.tableValueAt(32, 0);

                              for (int columnIndex = 0; columnIndex < 5; columnIndex++) {
                                 byte rowId2 = columnIndex == 4 ? endId2 : midId2;
                                 overlayCells[x + (y - columnIndex - 1) * stride] = rowId2;
                              }

                              overlayAbove = this.tableValueAt(31, 0);
                              int rowIndex2 = x + (y - 1 - 5) * stride;
                              overlayCells[rowIndex2] = table24[0];
                              joined = true;
                              rowIndex2 -= stride + 1;
                              if (overlayCells[rowIndex2] == 52) {
                                 overlayCells[rowIndex2] = 56;
                              }
                           } else if (overlayAbove == 60) {
                              overlayAbove = table24[idx8];
                           }

                           if (overlayBelow == 60) {
                              overlayBelow = this.tableValueAt(25, idx8);
                           }
                        }
                     }
                  } else if (!joined) {
                     byte endId3 = this.tableValueAt(16, indexE);
                     byte midId3 = this.tableValueAt(17, indexE);

                     for (int fillIndex = 0; fillIndex < 5; fillIndex++) {
                        byte rowId3 = fillIndex == 3 ? endId3 : midId3;
                        overlayCells[x + (y - fillIndex - 1) * stride] = rowId3;
                     }

                     overlayAbove = midId3;
                     int rowIndex3 = x + (y - 5 - 1) * stride;
                     byte aboveId = overlayCells[rowIndex3 - 1];
                     if (this.indexInTable(aboveId, 15) == -1 && aboveId != 52) {
                        overlayCells[rowIndex3] = 52;
                     } else {
                        overlayCells[rowIndex3] = 56;
                     }

                     overlayCells[x + (y - 5 - 0) * stride] = this.tableValueAt(15, indexE);
                     if (overlayCells[--rowIndex3] == 52) {
                        overlayCells[rowIndex3] = 48;
                     }

                     if ((indexE = com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table14)) != -1) {
                        overlayBelow = this.tableValueAt(19, indexE);
                     } else if ((indexE = com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table21)) != -1) {
                        overlayBelow = table26[indexE];
                     }

                     joined = true;
                  } else if (overlayAbove == 60) {
                     overlayAbove = 56;
                  }
               } else if (joined) {
                  if (overlayAbove == 60) {
                     overlayAbove = 48;
                  }
               } else {
                  byte endId4 = this.tableValueAt(10, indexE);
                  byte midId4 = this.tableValueAt(11, indexE);

                  for (int fillIndex2 = 0; fillIndex2 < 5; fillIndex2++) {
                     byte rowId4 = fillIndex2 == 4 ? endId4 : midId4;
                     overlayCells[x + (y - fillIndex2) * stride] = rowId4;
                  }

                  overlayAbove = midId4;
                  overlayCells[x + (y - 5 - 1) * stride] = 52;
                  int rowIndex4 = x + (y - 5 - 0) * stride;
                  overlayCells[rowIndex4] = this.tableValueAt(9, indexE);
                  if (overlayCells[--rowIndex4] == 52) {
                     overlayCells[rowIndex4] = 48;
                  }

                  if ((indexE = com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table8)) != -1) {
                     overlayBelow = this.tableValueAt(13, indexE);
                  } else if ((indexE = com.ea.sims3.GameCanvas.indexOfByte(overlayBelow, table20)) != -1) {
                     overlayBelow = this.tableValueAt(27, indexE);
                  }

                  joined = true;
               }
            }

            groundCells[index] = groundAbove;
            groundCells[indexBelow] = groundBelow;
            overlayCells[index] = overlayAbove;
            overlayCells[indexBelow] = overlayBelow;
         }
      }
   }

   private void markPerimeter() {
      Tile overlay = this.overlayTile;
      byte[] cells = this.overlayTile.cellIndices;
      int stride = overlay.columns;
      int rows = overlay.rowCountValue;
      byte[] flags = this.cellFlags;

      for (int flagIndex = 0; flagIndex < this.cellFlags.length; flagIndex++) {
         if ((flags[flagIndex] & 128) != 0) {
            flags[flagIndex] = (byte)(flags[flagIndex] | 64);
         }
      }

      for (int x = 0; x < stride; x++) {
         for (int y = 0; y < rows; y++) {
            byte id = cells[x + y * stride];
            if (x == 0
               || x == stride - 1
               || y == 0
               || y == rows - 1
               || this.indexInTable(id, 11) != -1
               || this.indexInTable(id, 17) != -1
               || this.indexInTable(id, 31) != -1
               || this.indexInTable(id, 32) != -1
               || this.indexInTable(id, 33) != -1
               || this.indexInTable(id, 8) != -1
               || this.indexInTable(id, 10) != -1
               || this.indexInTable(id, 9) != -1
               || this.indexInTable(id, 14) != -1
               || this.indexInTable(id, 16) != -1
               || this.indexInTable(id, 15) != -1) {
               int worldX = com.ea.sims3.Tile.toWorld16(x);
               int worldY = com.ea.sims3.Tile.toWorld8(y);
               int fixedX = this.toWorldX(worldX, worldY);
               int fixedY = this.toWorldY(worldX, worldY);
               int tileX = toTileX(fixedX);
               int tileY = toTileY(fixedY);
               this.setCellFlags(tileX, tileY, 255, 128);
            }
         }
      }
   }

   private void markBlockedCells() {
      Tile tile = this.groundTile;
      byte[] cells = this.groundTile.cellIndices;
      int stride = tile.columns;
      int rows = tile.rowCountValue;
      byte[] table = this.tableAt(34);
      com.ea.sims3.GameCanvas.fillBytes(this.cellFlags, 128);

      for (int x = 0; x < stride; x++) {
         for (int y = 0; y < rows; y++) {
            byte id;
            if (com.ea.sims3.GameCanvas.indexOfByte(id = cells[x + y * stride], table) != -1) {
               int worldX = com.ea.sims3.Tile.toWorld16(x);
               int worldY = com.ea.sims3.Tile.toWorld8(y);
               int fixedX = this.toWorldX(worldX, worldY);
               int fixedY = this.toWorldY(worldX, worldY);
               int tileX = toTileX(fixedX);
               int tileY = toTileY(fixedY);
               this.setCellFlags(tileX, tileY, -129, 32);
               this.setCellFlags(tileX + 1, tileY, 255, 16);
               this.setCellFlags(tileX - 1, tileY, 255, 16);
               this.setCellFlags(tileX, tileY + 1, 255, 16);
               this.setCellFlags(tileX, tileY - 1, 255, 16);
            }
         }
      }

      byte[] flags = this.cellFlags;

      for (int flagIndex = 0; flagIndex < flags.length; flagIndex++) {
         byte value = flags[flagIndex];
         short mask = 255;
         if ((value & 32) != 0) {
            mask = 239;
         }

         if ((value & 16) != 0) {
            mask &= -129;
         }

         flags[flagIndex] = (byte)(value & mask);
      }
   }

   private void markBorderCells(int imageId) {
      byte[] table = this.tableAt(imageId == 39 ? 36 : 35);
      Tile tile = this.groundTile;
      byte[] cells = this.groundTile.cellIndices;
      int stride = tile.columns;
      int rows = tile.rowCountValue;

      for (int x = 0; x < stride; x++) {
         for (int y = 0; y < rows; y++) {
            byte id;
            boolean matched = com.ea.sims3.GameCanvas.indexOfByte(id = cells[x + y * stride], table) != -1;
            if (x == 0 || x == stride - 1 || y == 0 || y == rows - 1 || matched) {
               int xAdj = x;
               if (!matched && x == stride - 1) {
                  xAdj = x + 1;
               }

               int worldX = com.ea.sims3.Tile.toWorld16(xAdj);
               int worldY = com.ea.sims3.Tile.toWorld8(y);
               int fixedX = this.toWorldX(worldX, worldY);
               int fixedY = this.toWorldY(worldX, worldY);
               int tileX = toTileX(fixedX);
               int tileY = toTileY(fixedY);
               this.setCellFlags(tileX, tileY, 255, 128);
            }
         }
      }
   }

   private void sortEntities() {
      GameCanvas canvas = this.canvas;
      Entity[] entities = this.canvas.gameScreen().getEntities();
      boolean swapped = false;

      for (int passIndex = 0; passIndex < entities.length; passIndex++) {
         for (int entryIndex = 1; entryIndex < entities.length; entryIndex++) {
            Entity current = entities[entryIndex];
            Entity previous = entities[entryIndex - 1];
            int depthX = this.toScreenX(current.posX, current.posY);
            int depthY = this.toScreenX(previous.posX, previous.posY);
            if (depthX < depthY) {
               if (current.backSortY() < previous.frontSortY()) {
                  entities[entryIndex - 1] = current;
                  entities[entryIndex] = previous;
                  swapped = true;
               }

               if (current.type == 98
                  && previous.type == 0
                  && entities.length < 25
                  && (current.backSortY() == 176 || current.backSortY() == 144 || current.backSortY() == 112 || current.backSortY() == 80 || current.backSortY() == 48)) {
                  entities[entryIndex - 1] = current;
                  entities[entryIndex] = previous;
                  swapped = true;
               }

               if (current.type == 0
                  && previous.type == 98
                  && entities.length < 25
                  && (previous.backSortY() == 400 || previous.backSortY() == 368 || previous.backSortY() == 336 || previous.backSortY() == 304 || previous.backSortY() == 272)) {
                  entities[entryIndex - 1] = current;
                  entities[entryIndex] = previous;
                  swapped = true;
               }
            } else {
               if (current.frontSortY() < previous.backSortY()) {
                  entities[entryIndex - 1] = current;
                  entities[entryIndex] = previous;
                  swapped = true;
               }

               if (current.type == 98 && previous.type == 0 && entities.length < 25 && (current.backSortY() == 176 || current.backSortY() == 144 || current.backSortY() == 112 || current.backSortY() == 80)) {
                  entities[entryIndex - 1] = current;
                  entities[entryIndex] = previous;
                  swapped = true;
               }

               if (current.type == 0
                  && previous.type == 98
                  && entities.length < 25
                  && (previous.backSortY() == 400 || previous.backSortY() == 368 || previous.backSortY() == 336 || previous.backSortY() == 304 || previous.backSortY() == 272)) {
                  entities[entryIndex - 1] = current;
                  entities[entryIndex] = previous;
                  swapped = true;
               }
            }
         }

         if (!swapped) {
            return;
         }

         swapped = false;
      }
   }

   private void updateVisibility() {
      GameCanvas canvas = this.canvas;
      Entity[] entities = this.canvas.gameScreen().getEntities();
      int minX = this.boundsX - 50;
      int minY = this.boundsY - 20;
      int maxX = this.boundsX + this.boundsWidth + 50;
      int maxY = this.boundsY + this.boundsHeight + 60;
      int centerX = this.viewCenterX();
      int centerY = this.viewCenterY();

      for (int entityIndex = 0; entityIndex < entities.length; entityIndex++) {
         Entity entity = entities[entityIndex];
         int screenX = centerX + this.toScreenX(entity.posX, entity.posY);
         int screenY = centerY + this.toScreenY(entity.posX, entity.posY);
         entity.reservedFlag = screenX >= minX && screenX <= maxX && screenY >= minY && screenY <= maxY;
      }
   }

   private void drawEntities(Graphics gfx) {
      this.sortEntities();
      this.updateVisibility();
      GameCanvas canvas = this.canvas;
      Entity[] entities;
      int count = (entities = this.canvas.gameScreen().getEntities()).length;

      for (int entityIndex = 0; entityIndex < count; entityIndex++) {
         Entity entity;
         if ((entity = entities[entityIndex]).reservedFlag && !entity.hasState(8192)) {
            entity.render(gfx);
         }
      }
   }

   public final void drawEntityPair(Graphics gfx, Entity first, Entity second, int cameraX, int cameraY) {
      int savedCameraX = this.cameraX;
      int savedCameraY = this.cameraY;
      this.cameraX = cameraX;
      this.cameraY = cameraY;
      GameCanvas canvas = this.canvas;
      Entity[] entities;
      int count = (entities = this.canvas.gameScreen().getEntities()).length;

      for (int entityIndex = 0; entityIndex < count; entityIndex++) {
         Entity entity;
         if ((entity = entities[entityIndex]) == first || entity == second) {
            entity.render(gfx);
         }
      }

      this.cameraX = savedCameraX;
      this.cameraY = savedCameraY;
   }

   public final int objectCount() {
      return this.spriteId.length;
   }

   public final int objectKindAt(int objectTypeId) {
      return this.objectKind[objectTypeId];
   }

   public final int spriteIdAt(int objectTypeId) {
      return this.spriteId[objectTypeId];
   }

   public final int spriteVariantAt(int objectTypeId) {
      return this.spriteVariant[objectTypeId];
   }

   public final int objectFlagsAt(int objectTypeId) {
      return this.objectFlags[objectTypeId];
   }

   public final int linkedIdAt(int objectTypeId) {
      return this.linkedId[objectTypeId];
   }

   public final int objectIdsAt(int objectTypeId) {
      return this.objectIds[objectTypeId];
   }

   public final int altIdAt(int objectTypeId) {
      return this.altIds[objectTypeId];
   }

   public final int objectWidthAt(int objectTypeId) {
      return this.objectWidth[objectTypeId];
   }

   public final int objectHeightAt(int objectTypeId) {
      return this.objectHeight[objectTypeId];
   }

   public final int subTypeAt(int objectTypeId) {
      return this.subType[objectTypeId];
   }

   public final int attachmentAt(int objectTypeId, int index) {
      return index < this.attachments[objectTypeId].length ? this.attachments[objectTypeId][index] : 0;
   }

   public final int frameCountAt(int objectTypeId) {
      return this.frameFlags[objectTypeId].length;
   }

   public final int framePartX(int objectTypeId, int frame, int direction) {
      int offset = this.frameFlags[objectTypeId][frame];
      byte mirrored = this.frameMirrored[objectTypeId][frame];
      switch (direction) {
         case 0:
         default:
            break;
         case 1:
            offset = mirrored;
            break;
         case 2:
            offset = 1 - offset - Math.max(this.objectHeight[objectTypeId], 1);
            break;
         case 3:
            offset = 1 - Math.max(this.objectWidth[objectTypeId], 1) - mirrored;
      }

      return offset;
   }

   public final int framePartY(int objectTypeId, int frame, int direction) {
      byte offset = this.frameFlags[objectTypeId][frame];
      int mirrored = this.frameMirrored[objectTypeId][frame];
      switch (direction) {
         case 0:
         default:
            break;
         case 1:
            mirrored = offset;
            break;
         case 2:
            mirrored = 1 - mirrored - Math.max(this.objectWidth[objectTypeId], 1);
            break;
         case 3:
            mirrored = 1 - Math.max(this.objectHeight[objectTypeId], 1) - offset;
      }

      return mirrored;
   }

   public final short[] frameRefsAt(int objectTypeId) {
      return this.frameRefs[objectTypeId];
   }

   public final int indexOfKind(int kind) {
      int[] flags = this.objectFlags;
      if ((this.objectFlags[kind] & 32) == 0) {
         return -1;
      } else {
         int position = 0;

         for (int typeIndex = 0; typeIndex < flags.length; typeIndex++) {
            if ((flags[typeIndex] & 32) != 0) {
               if (typeIndex == kind) {
                  return position;
               }

               position++;
            }
         }

         return -1;
      }
   }

   public final int frameDataAt(int objectTypeId, int index) {
      int indexInArray = (objectTypeId << 2) + index;
      return this.frameData[indexInArray];
   }

   public final int resolveObjectId(int objectId, boolean isOutdoor) {
      if (objectId < 0) {
         int progress = this.canvas.randomPercent();
         if (objectId == -2) {
            if (!isOutdoor) {
               progress = progress * 75 / 100;
            }

            if (progress < 25) {
               objectId = 6;
            } else if (progress < 50) {
               objectId = 7;
            } else if (progress < 75) {
               objectId = 8;
            } else {
               objectId = 9;
            }
         } else if (objectId == -3) {
            objectId = progress < 50 ? 10 : 11;
         } else if (objectId == -4) {
            objectId = progress < 50 ? 13 : 14;
         }
      }

      return objectId;
   }

   public final int framePartAt(int objectTypeId, int frame, boolean secondary) {
      short[] parts = this.frameParts[objectTypeId];
      frame <<= 1;
      if (secondary) {
         frame++;
      }

      if (frame < parts.length) {
         return parts[frame];
      } else {
         return secondary ? 0 : parts[0];
      }
   }

   public final boolean isStageItemAllowed(int objectTypeId) {
      int mask = this.platformMask();
      return (this.objectFlagsAt(objectTypeId) & mask) == 0;
   }

   private int platformMask() {
      switch (this.canvas.neighborhood.careerLevel()) {
         case 0:
         default:
            return 805306368;
         case 1:
            return 536870912;
         case 2:
            return 0;
      }
   }

   public final int itemCount() {
      return this.objectIds.length - com.ea.sims3.GameCanvas.countEqualShorts(-1, this.objectIds);
   }

   public final int indexOfItem(int value) {
      return com.ea.sims3.GameCanvas.nthIndexNotEqualShort(value, this.objectIds, -1);
   }

   private final byte[] tableAt(int tableId) {
      return this.layoutTables[tableId];
   }

   private final byte tableValueAt(int tableId, int index) {
      return this.layoutTables[tableId][index];
   }

   private final int indexInTable(int value, int tableId) {
      return com.ea.sims3.GameCanvas.indexOfByte(value, this.layoutTables[tableId]);
   }

   public final int categoryCount() {
      return this.categoryIds.length;
   }

   public final int categoryIdAt(int index) {
      return this.categoryIds[index];
   }

   public final int categoryValueAt(int index) {
      return this.categoryValues[index];
   }

   public final int categoryMaskAt(int index) {
      return this.categoryMasks[index];
   }

   public final int categoryTypeAt(int index) {
      return this.categoryTypes[index];
   }

   public final int categorySubTypeAt(int index) {
      return this.categorySubTypes[index];
   }

   public final int categoryFlagAt(int index) {
      return this.categoryFlags[index];
   }

   public final int indexOfCategoryId(int categoryId) {
      return com.ea.sims3.GameCanvas.indexOfShort(categoryId, this.categoryIds);
   }

   private static int soundMask(int soundId) {
      char result = '\u0000';
      switch (soundId) {
         case 111:
            result = '\u8000';
            break;
         case 112:
         case 113:
         default:
            result = '\u1000';
            break;
         case 114:
            result = '\u2000';
            break;
         case 115:
            result = '\u4000';
      }

      return result;
   }

   public final int countBySoundMask(int soundId) {
      return com.ea.sims3.GameCanvas.countAnyFlagInt(soundMask(soundId), this.categoryMasks);
   }

   public final int bitForSound(int soundId, int mask) {
      int bit = soundMask(soundId);
      return com.ea.sims3.GameCanvas.nthIndexWithFlagInt(mask, this.categoryMasks, bit);
   }

   public final int volumeOf(int categoryId) {
      return com.ea.sims3.FixedMath.mul(this.categoryVolumes[categoryId] << 16, 22937);
   }

   public final int refAt(int categoryId) {
      return this.categoryRefs[categoryId];
   }

   public final int listSize(int categoryId) {
      return this.categoryLists[categoryId].length;
   }

   public final int listValueAt(int categoryId, int index) {
      return this.categoryLists[categoryId][index];
   }

   public final int indexOfRef(int ref) {
      return com.ea.sims3.GameCanvas.indexOfByte(ref, this.categoryRefs);
   }

   public final int categoryIdOfRef(int ref) {
      return this.categoryIds[this.categoryRefs[ref]];
   }

   public final int refForCategoryId(int categoryId) {
      for (int index = 0; index < this.categoryRefs.length; index++) {
         if (this.categoryIdOfRef(index) == categoryId) {
            return index;
         }
      }

      return -1;
   }

   private void loadPalettes() {
      ResourceLoader res = this.canvas.resources;

      try {
         DataInputStream stream;
         byte paletteCount;
         int[][] paletteRows = new int[paletteCount = (stream = new DataInputStream(res.openStream(60))).readByte()][];

         for (int paletteIndex = 0; paletteIndex < paletteCount; paletteIndex++) {
            byte rowLength;
            int[] row = new int[rowLength = stream.readByte()];

            for (int rowIndex = 0; rowIndex < rowLength; rowIndex++) {
               row[rowIndex] = stream.readInt();
            }

            paletteRows[paletteIndex] = row;
         }

         byte tableCount;
         byte[][] tableRows = new byte[tableCount = stream.readByte()][];

         for (int tableIndex = 0; tableIndex < tableCount; tableIndex++) {
            byte[] tableRow = new byte[stream.readByte()];
            stream.readFully(tableRow);
            tableRows[tableIndex] = tableRow;
         }

         stream.close();
         this.palettes = paletteRows;
         this.categoryTables = tableRows;
      } catch (IOException ignored) {
      }
   }

   private static void fillPlaceholders(int[] colorsA, int[] colorsB) {
      for (int slotIndex = 0; slotIndex < colorsA.length; slotIndex++) {
         colorsA[slotIndex] = 16711935;
         colorsB[slotIndex] = 16711935;
      }
   }

   private void applyPalette(int[] colorsA, int[] colorsB, int paletteA, int paletteB) {
      if (paletteA >= 0 && paletteB >= 0) {
         int[] paletteRowA = this.palettes[paletteA];
         int[] paletteRowB = this.palettes[paletteB];
         int slot = -1;

         for (int slotIndex = 0; slotIndex < colorsA.length; slotIndex++) {
            if (colorsA[slotIndex] == 16711935) {
               slot = slotIndex;
               break;
            }
         }

         System.arraycopy(paletteRowA, 0, colorsA, slot, paletteRowA.length);
         System.arraycopy(paletteRowB, 0, colorsB, slot, paletteRowB.length);
      }
   }

   public final int[] paletteAt(int paletteId) {
      return this.palettes[paletteId];
   }

   public final byte[] tableRowAt(int tableId) {
      return this.categoryTables[tableId];
   }

   public final void buildPaletteFor(int simIndex, int[] colorsA, int[] colorsB) {
      Neighborhood neighborhood = this.canvas.neighborhood;
      byte table1Index = this.categoryTables[1][neighborhood.simAttribute(simIndex, 3)];
      byte table2Index = this.categoryTables[2][neighborhood.simAttribute(simIndex, 4)];
      byte table3Index = this.categoryTables[3][neighborhood.simAttribute(simIndex, 5)];
      byte table4Index = this.categoryTables[4][neighborhood.simAttribute(simIndex, 6)];
      byte table5Index = this.categoryTables[5][neighborhood.simAttribute(simIndex, 7)];
      byte table6Index = this.categoryTables[6][neighborhood.simAttribute(simIndex, 8)];
      byte table7Index = this.categoryTables[7][neighborhood.simAttribute(simIndex, 9)];
      byte table0Index = this.categoryTables[0][neighborhood.simAttribute(simIndex, 1)];
      fillPlaceholders(colorsA, colorsB);
      this.applyPalette(colorsA, colorsB, 11, table1Index);
      this.applyPalette(colorsA, colorsB, 34, table2Index);
      this.applyPalette(colorsA, colorsB, 20, table3Index);
      this.applyPalette(colorsA, colorsB, 30, table4Index);
      this.applyPalette(colorsA, colorsB, 31, table5Index);
      this.applyPalette(colorsA, colorsB, 32, table6Index);
      this.applyPalette(colorsA, colorsB, 33, table7Index);
      this.applyPalette(colorsA, colorsB, 5, table0Index);
   }

   public final void clearWaypoints() {
      this.waypointCount = 0;
   }

   public final void addWaypoint(int x, int y) {
      int offset = this.waypointCount << 1;
      this.waypoints[offset] = x;
      this.waypoints[offset + 1] = y;
      this.waypointCount++;
   }

   public final int[] findPath(int startX, int startY, int goalX, int goalY, int flags) {
      this.resetPathfinder();
      this.pathFlags = flags;
      this.pathStartX = toTileX(startX);
      this.pathStartY = toTileY(startY);
      this.pathGoalX = toTileX(goalX);
      this.pathGoalY = toTileY(goalY);
      if (this.isPassable(this.pathStartX, this.pathStartY) && this.isPassable(this.pathGoalX, this.pathGoalY)) {
         this.pathCenterX = this.pathStartX + this.pathGoalX - 32 >> 1;
         this.pathCenterY = this.pathStartY + this.pathGoalY - 32 >> 1;
         int relX = this.pathStartX - this.pathCenterX;
         int relY = this.pathStartY - this.pathCenterY;
         if (relX >= 0 && relY >= 0 && relX < 32 && relY < 32) {
            relX = this.pathGoalX - this.pathCenterX;
            relY = this.pathGoalY - this.pathCenterY;
            if (relX >= 0 && relY >= 0 && relX < 32 && relY < 32) {
               this.pushOpen(this.pathStartX - this.pathCenterX, this.pathStartY - this.pathCenterY);
               boolean done = false;

               while (!done) {
                  if ((relY = this.bestOpenIndex()) == -1) {
                     return new int[0];
                  }

                  int currentX = this.openList[relY] & 255;
                  int currentY = (this.openList[relY] & '\uff00') >> 8;

                  for (int dir = 0; dir < 4; dir++) {
                     int nextX = currentX + (dir == 0 ? -1 : (dir == 1 ? 1 : 0));
                     int nextY = currentY + (dir == 2 ? -1 : (dir == 3 ? 1 : 0));
                     if (!this.isClosed(nextX, nextY)) {
                        if (this.pushOpen(nextX, nextY)) {
                           this.setParent(nextX, nextY, currentX, currentY);
                           this.updateCost(nextX, nextY);
                           this.updateHeuristic(nextX, nextY);
                           this.updateScore(nextX, nextY);
                        } else if (this.isCheaper(nextX, nextY, currentX, currentY)) {
                           this.setParent(nextX, nextY, currentX, currentY);
                           this.updateCost(nextX, nextY);
                           this.updateScore(nextX, nextY);
                        }
                     }
                  }

                  this.popOpen(relY);
                  if (currentX == this.pathGoalX - this.pathCenterX && currentY == this.pathGoalY - this.pathCenterY) {
                     done = true;
                  }
               }

               relY = Integer.MAX_VALUE;
               int prevY = Integer.MAX_VALUE;
               int goalRelX = this.pathGoalX - this.pathCenterX;
               int goalRelY = this.pathGoalY - this.pathCenterY;
               int startRelX = this.pathStartX - this.pathCenterX;
               int startRelY = this.pathStartY - this.pathCenterY;
               int count = 0;

               short link;
               while (
                  goalRelX >= 0
                     && goalRelY >= 0
                     && goalRelX < 32
                     && goalRelY < 32
                     && (goalRelX != startRelX || goalRelY != startRelY)
                     && (relY != goalRelX || prevY != goalRelY)
                     && (link = this.parentLinks[goalRelX][goalRelY]) != -1
               ) {
                  this.pathResult[count] = (short)(goalRelX | goalRelY << 8);
                  count++;
                  relY = goalRelX;
                  prevY = goalRelY;
                  goalRelX = link & 255;
                  goalRelY = (link & '\uff00') >> 8;
               }

               int[] path = new int[count << 1];

               for (int stepIndex = 0; stepIndex < count; stepIndex++) {
                  int index = count - stepIndex - 1;
                  int x = (this.pathResult[index] & 255) + this.pathCenterX;
                  int y = ((this.pathResult[index] & '\uff00') >> 8) + this.pathCenterY;
                  path[(stepIndex << 1) + 0] = toFixedX(x);
                  path[(stepIndex << 1) + 1] = toFixedY(y);
               }

               return path;
            } else {
               return new int[0];
            }
         } else {
            return new int[0];
         }
      } else {
         return new int[0];
      }
   }

   private void initPathfinder() {
      if (this.openList == null) {
         this.openList = new short[1024];
         this.openCount = 0;
         this.closedList = new short[1024];
         this.closedCount = 0;
         this.parentLinks = new short[32][];

         for (int rowIndex = 0; rowIndex < this.parentLinks.length; rowIndex++) {
            this.parentLinks[rowIndex] = new short[32];
         }

         this.pathCost = new int[32][];

         for (int rowIndex2 = 0; rowIndex2 < this.pathCost.length; rowIndex2++) {
            this.pathCost[rowIndex2] = new int[32];
         }

         this.pathScore = new int[32][];

         for (int rowIndex3 = 0; rowIndex3 < this.pathScore.length; rowIndex3++) {
            this.pathScore[rowIndex3] = new int[32];
         }

         this.pathHeuristic = new int[32][];

         for (int rowIndex4 = 0; rowIndex4 < this.pathHeuristic.length; rowIndex4++) {
            this.pathHeuristic[rowIndex4] = new int[32];
         }

         this.pathResult = new short[256];
         this.waypointCount = 0;
         this.waypoints = new int[20];
      }
   }

   private boolean pushOpen(int x, int y) {
      short key = (short)(x | y << 8);

      for (int entryIndex = 0; entryIndex < this.openCount; entryIndex++) {
         if (this.openList[entryIndex] == key) {
            return false;
         }
      }

      this.openList[this.openCount] = key;
      this.openCount++;
      return true;
   }

   private void popOpen(int index) {
      this.closedList[this.closedCount] = this.openList[index];
      this.closedCount++;

      for (int entryIndex = index; entryIndex < this.openCount - 1; entryIndex++) {
         this.openList[entryIndex] = this.openList[entryIndex + 1];
      }

      this.openCount--;
   }

   private void setParent(int x, int y, int parentX, int parentY) {
      this.parentLinks[x][y] = (short)(parentX | parentY << 8);
   }

   private int stepCost(int relX, int relY) {
      if ((this.pathFlags & 6) != 0) {
         int tileX = relX + this.pathCenterX;
         int tileY = relY + this.pathCenterY;
         int flags;
         if (((flags = this.cellFlagsAt(tileX, tileY)) & 32) != 0) {
            return (this.pathFlags & 2) != 0 ? 5 : 50;
         } else {
            return (flags & 16) != 0 ? 10 : 15;
         }
      } else {
         return 15;
      }
   }

   private void updateCost(int x, int y) {
      int parentX = this.parentLinks[x][y] & 255;
      int parentY = (this.parentLinks[x][y] & '\uff00') >> 8;
      int cost = this.pathCost[parentX][parentY];
      byte penalty = 0;
      if ((this.pathFlags & 8) != 0) {
         int grandX = this.parentLinks[parentX][parentY] & 255;
         int grandY = (this.parentLinks[parentX][parentY] & '\uff00') >> 8;
         if ((grandX != parentX || parentX != x) && (grandY != parentY || parentY != y)) {
            penalty = 20;
         }
      }

      this.pathCost[x][y] = cost + this.stepCost(x, y) + penalty;
   }

   private boolean isCheaper(int x, int y, int parentX, int parentY) {
      int cost = this.pathCost[x][y];
      return this.pathCost[parentX][parentY] + this.stepCost(x, y) < cost;
   }

   private void updateHeuristic(int x, int y) {
      int dx = this.pathGoalX - this.pathCenterX - x;
      int dy = this.pathGoalY - this.pathCenterY - y;
      if (dx < 0) {
         dx = -dx;
      }

      if (dy < 0) {
         dy = -dy;
      }

      short penalty = 0;
      if ((this.pathFlags & 1) != 0) {
         for (int waypointIndex = 0; waypointIndex < this.waypointCount; waypointIndex++) {
            int offset = waypointIndex << 1;
            if (x + this.pathCenterX == this.waypoints[offset] && y + this.pathCenterY == this.waypoints[offset + 1]) {
               penalty = 250;
               break;
            }
         }
      }

      this.pathHeuristic[x][y] = dx * 10 + dy * 10 + penalty;
   }

   private void updateScore(int x, int y) {
      this.pathScore[x][y] = this.pathHeuristic[x][y] + this.pathCost[x][y];
   }

   private int bestOpenIndex() {
      int result = -1;
      int bestScore = Integer.MAX_VALUE;

      for (int entryIndex = 0; entryIndex < this.openCount; entryIndex++) {
         int x = this.openList[entryIndex] & 255;
         int y = (this.openList[entryIndex] & '\uff00') >> 8;
         if (bestScore > this.pathScore[x][y]) {
            bestScore = this.pathScore[x][y];
            result = entryIndex;
         }
      }

      return result;
   }

   private boolean isWalkable(int relX, int relY) {
      int tileX = relX + this.pathCenterX;
      int tileY = relY + this.pathCenterY;
      return this.isPassable(tileX, tileY);
   }

   private boolean isClosed(int x, int y) {
      if (x >= 0 && y >= 0 && x < 32 && y < 32) {
         if (!this.isWalkable(x, y)) {
            return true;
         } else {
            int key = x | y << 8;

            for (int entryIndex = 0; entryIndex < this.closedCount; entryIndex++) {
               if (key == this.closedList[entryIndex]) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }

   private void resetPathfinder() {
      this.closedCount = 0;
      this.openCount = 0;
      com.ea.sims3.GameCanvas.fillShortMatrix(this.parentLinks, 0);
      com.ea.sims3.GameCanvas.fillIntMatrix(this.pathCost, 0);
      com.ea.sims3.GameCanvas.fillIntMatrix(this.pathScore, 0);
      com.ea.sims3.GameCanvas.fillIntMatrix(this.pathHeuristic, 0);
   }
}
