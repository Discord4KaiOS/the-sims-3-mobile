package com.ea.sims3;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Stack;
import javax.microedition.lcdui.Graphics;

public final class GameScreen extends BaseScreen implements Runnable {
   private int[] frameBounds = new int[10];
   public static int screenState;
   private int keyRepeatTime;
   private int pendingTick;
   private int dialogX;
   private int dialogY;
   private int startOption;
   private byte saveSlotMode;
   private boolean inputIdle;
   private int bottomBarHeight;
   private int viewX;
   private int viewY;
   private int viewWidth;
   private int viewHeight;
   private short visibleRows;
   private int loadStep;
   private Thread loaderThread;
   private int threadState;
   private int portraitSlot;
   private int portraitSimId;
   private int[] recolorSource = new int[32];
   private int[] recolorTarget = new int[32];
   private byte pendingPortraits;
   private byte[] slotSimIds;
   private boolean musicStarted;
   private boolean dialogActive;
   public int lastTickDuration;
   private Animator statusAnimator;
   private Animator needUpAnimator;
   private Animator needDownAnimator;
   private int displayedMoney;
   private int interactionSource;
   private int fadePhase;
   private int pendingAction;
   private int fadeAmount;
   public static boolean updateBlocked = false;
   private short notifyId;
   private short notifyId2;
   private int notificationTimer;
   private int notificationScroll;
   private int notificationWidth;
   private Entity followTarget;
   private int camX;
   private int camY;
   private int camTargetX;
   private int camTargetY;
   private boolean atTarget;
   private int stillTime;
   private Animator selectionAnimator;
   private int followTime;
   private int followFlag;
   private boolean movingToTarget;
   private boolean leftPressed;
   private boolean rightPressed;
   private boolean downPressed;
   private boolean upPressed;
   private int clampTargetX;
   private int clampTargetY;
   private int camVelX;
   private int camVelY;
   private int prevCamVelX;
   private int prevCamVelY;
   private boolean cameraClamped;
   public Entity cameraTarget;
   private boolean recenterPending;
   private Entity[] entityCache;
   private Sim[] simCache;
   public Sim activePlayer;
   private Stack entityStack;
   public boolean entitiesDirty;
   public boolean simsDirty;
   private boolean menuOpen;
   private boolean menuSubPage;
   private short menuParent;
   private short[] menuLabels;
   private short[] menuArgs;
   private short[] menuExtra;
   public Entity menuTarget;
   private boolean mainMenuOpen;
   private short[] menuOptionIds;
   private int mainMenuPage;
   private boolean actionMenuOpen;
   private boolean tabSwitchRequested;
   private short[] actionOptionIds;
   private boolean objectMenuOpen;
   private short[] objectOptionIds;
   private int menuPage;
   private int selectedType;
   private Entity pendingPlacement;
   private int optionsTab;
   private int shownSimId;
   private int rosterIndex;
   private int nameColumnWidth;
   private int listTextWidth;
   private int listValueWidth;
   private int objectIndex;
   private int objectRow;
   private int friendSimId;
   public int friendLevel;
   private int requestAmount;
   private boolean choiceMade;
   private boolean pendingEvent;
   public boolean pendingGoHome;
   private Animator[] slotAnimators;
   private int pendingDialogId;
   private int dialogArg1;
   private int dialogArg2;
   private int dialogText;
   private int aiTimer;
   private int soundTimer;
   private short[] soundChoices;
   private int soundInterval;
   private int idleTimer;
   private static final short[] DEFAULT_SOUND_CHOICES = new short[]{14, 15, 12, 13};
   private static final short[] ALT_SOUND_CHOICES = new short[]{14, 15};
   private int queuedCount;
   private int[] queuedInteractions;
   private Entity[] queuedTargets;
   private int[] queuedArgs;
   private int[] queuedArgs2;
   public Neighborhood neighborhood;
   private Lot currentLot;
   private int relationPercent;
   private int timeAdvanceSeconds;
   private Animator cursorAnimator;
   private int dialogSubject;
   private int dialogSubjectIndex;
   private int dialogPhase;
   private int dialogOption;
   private boolean dialogRequestPending;
   private int miniGamePhase;
   private int dialogKind;
   private int dialogIconTop;
   private int dialogIconBottom;
   private int dialogSubjectType;
   private int dialogClock;
   private Entity savedTarget;
   private int miniStep;
   private Entity miniTarget;
   private int miniKind;
   private int miniType;
   private int panelTop;
   private int panelBottom;
   private Animator[] effectAnimators;
   private int[][] effectPositions;
   private int progressValue;
   private int progressMax;
   private int candleColumns;
   private int plotIndex;
   private int candleDecrement;
   private int candleRiseSpeed;
   private int[] candleAnimValues = new int[4];
   private int[] candleTargets = new int[4];
   private byte[] candleFrames = new byte[4];
   private int toastAnimSlot;
   private int sliderPosition;
   private int targetPosition;
   private int targetStart;
   private int targetGoal;
   private int timingMeter;
   private Animator candleFlameAnimator;
   private Animator candleGlowAnimator;
   private int sliderStep;
   private int sliderRange;
   private int miniLevel;
   private Animator seedCursorAnimator;
   private static final byte[] DIR_X_TABLE = new byte[]{2, 2, 2, 0, 0, 2, 2, 1};
   private static final byte[] DIR_Y_TABLE = new byte[]{0, 1, -1, -2, 2, -1, 1, 0};
   private byte[][] seedRows;
   private byte[] seedTypes;
   private short[][] seedCells;
   private int cursorPosition;
   private int plotCount;
   private int seedCount;
   private int seedTimer;
   private Animator vehicleAnimator;
   private int gridX;
   private int gridY;
   private int hoverCell;
   private int wobbleAmount;
   private int wobbleSpeed;
   private int phaseX;
   private int phaseY;
   private int offsetX;
   private int offsetY;
   private int plantPhase;
   private int targetObjectType;
   private int interactionAmount;
   private short[] cheatLabels;
   private SdkString[] cheatText;

   public GameScreen(GameCanvas gameCanvas) {
      super(gameCanvas);
   }

   public final boolean isNoSaveMode() {
      return this.saveSlotMode == 0;
   }

   public final boolean isNewNeighborhood() {
      return this.saveSlotMode == 2;
   }

   private boolean isSavedNeighborhood() {
      return this.saveSlotMode == 1;
   }

   private void startLoading(int keyCode) {
      super.canvas.updateLoadingTip(keyCode);
      if (this.loaderThread == null) {
         this.loaderThread = new Thread(this);
         this.threadState = 0;
         this.loaderThread.start();
      } else {
         this.threadState = 0;
      }
   }

   public final void run() {
      while (this.threadState != 2) {
         if (this.threadState != 0) {
            try {
               synchronized (this) {
                  this.wait(1000L);
               }
            } catch (InterruptedException interrupted) {
            }
         } else {
            this.threadState = 1;

            try {
               this.runLoadingSteps();
            } catch (Exception failure) {
            }
         }
      }
   }

   private void runLoadingSteps() {
      GameCanvas canvas = super.canvas;
      switch (this.loadStep) {
         case 0:
            this.loadStep++;
            return;
         case 1:
            Lot lot = this.currentLot;
            this.currentLot.setBounds(this.viewX, this.viewY, 320, 320);
            this.entitiesDirty = true;
            this.simsDirty = true;
            boolean usePlayerPos = true;
            if (this.saveSlotMode == 2) {
               this.setupNewGame();
            } else if (this.saveSlotMode == 1) {
               this.restoreSavedSims();
               if (this.getSims().length > 1) {
                  usePlayerPos = false;
               }
            } else {
               this.resumeSavedGame();
            }

            if (usePlayerPos) {
               Sim player = this.activePlayer;
               int playerX = this.activePlayer.posX;
               int playerY = player.posY;
               this.camTargetX = lot.toScreenFixedX(playerX, playerY);
               this.camTargetY = lot.toScreenFixedY(playerX, playerY);
            }

            this.camX = this.camTargetX;
            this.camY = this.camTargetY;
            this.clearCameraTarget();
            this.snapCameraToTarget();
            this.loadStep++;
            canvas.resetTileLoad();
            return;
         case 2:
            int loadMask = 8;
            switch (this.saveSlotMode) {
               case 0:
               default:
                  loadMask = 24;
                  Entity hoverEntity;
                  if ((hoverEntity = this.findEntityByObjectType(10)) != null) {
                     if (hoverEntity.type == 46) {
                        loadMask = 24 | 134217728;
                     }

                     if (hoverEntity.type == 48) {
                        loadMask |= 268435456;
                     }

                     if (hoverEntity.type == 50) {
                        loadMask |= 536870912;
                     }

                     if (hoverEntity.type == 52) {
                        loadMask |= 1073741824;
                     }
                  }
                  break;
               case 1:
                  if (canvas.houseUpgrade == 39) {
                     loadMask = 8 | 131072;
                  } else if (canvas.houseUpgrade == 41) {
                     loadMask = 8 | 262144;
                  }
                  break;
               case 2:
                  loadMask = 40;
            }

            if (canvas.loadTilesFor(loadMask)) {
               this.loadStep++;
               return;
            }
            break;
         case 3:
            ResourceLoader resources = canvas.resources;
            Lot loadedLot = this.currentLot;
            if (this.isNewNeighborhood()) {
               int[] sourceColors = loadedLot.paletteAt(0);
               boolean recolorFlag = false;
               int[] targetColors = loadedLot.paletteAt(1);
               SpriteRenderer.loadTileRecolored(resources, 38, sourceColors, targetColors, 0);
               SpriteRenderer.loadTileRecolored(resources, 35, sourceColors, targetColors, 0);
               targetColors = loadedLot.paletteAt(2);
               SpriteRenderer.loadTileRecolored(resources, 38, sourceColors, targetColors, 1);
               SpriteRenderer.loadTileRecolored(resources, 35, sourceColors, targetColors, 1);
               targetColors = loadedLot.paletteAt(3);
               SpriteRenderer.loadTileRecolored(resources, 38, sourceColors, targetColors, 2);
               SpriteRenderer.loadTileRecolored(resources, 35, sourceColors, targetColors, 2);
               targetColors = loadedLot.paletteAt(4);
               SpriteRenderer.loadTileRecolored(resources, 38, sourceColors, targetColors, 3);
               SpriteRenderer.loadTileRecolored(resources, 35, sourceColors, targetColors, 3);
               this.loadPlayerPortrait();
               this.loadStep++;
               return;
            }

            this.pickNextPortrait();

            while (this.pendingPortraits > 0) {
               this.loadQueuedPortrait();
            }

            this.loadStep++;
            return;
         case 4:
            short[] soundChoices = null;
            short soundInterval = 0;
            if (this.isNewNeighborhood()) {
               soundChoices = DEFAULT_SOUND_CHOICES;
               soundInterval = 3000;
            } else if (canvas.houseUpgrade == 39) {
               soundChoices = ALT_SOUND_CHOICES;
               soundInterval = 8000;
            } else if (canvas.houseUpgrade != -1) {
               soundChoices = DEFAULT_SOUND_CHOICES;
               soundInterval = 10000;
            }

            this.soundChoices = soundChoices;
            this.soundInterval = soundInterval;
            this.loadStep++;
            return;
         case 5:
            this.layoutViewport();
            this.onTick(this.startOption);
            if (this.isNewNeighborhood()) {
               this.showNeighborhoodIntro((byte)1);
            } else {
               this.showNeighborhoodIntro((byte)0);
            }

            this.threadState = 2;
            canvas.fadeSpeed = 197379;
            canvas.fadeFromBlack();
      }
   }

   private void resetPortraitQueue() {
      this.portraitSlot = 0;
      this.portraitSimId = 0;
      this.pendingPortraits = 0;
      if (!this.isNewNeighborhood()) {
         if (this.slotSimIds == null) {
            this.slotSimIds = new byte[4];
         }

         GameCanvas.fillBytes(this.slotSimIds, -1);
      }
   }

   private void queuePortraitLoad(Sim sim) {
      if (!this.isNewNeighborhood()) {
         this.pendingPortraits++;
         int slot = this.findFreePortraitSlot();
         this.slotSimIds[slot] = (byte)sim.variant;
      }
   }

   private void pickNextPortrait() {
      for (int slot = 0; slot < 4; slot++) {
         byte simId;
         if ((simId = this.slotSimIds[slot]) != -1 && this.findSimByIndex(simId).recolorIndex == -1) {
            this.portraitSlot = slot;
            this.portraitSimId = simId;
            this.currentLot.buildPaletteFor(simId, this.recolorSource, this.recolorTarget);
            super.canvas.resetTileLoad();
            return;
         }
      }
   }

   private int findFreePortraitSlot() {
      return GameCanvas.indexOfByte(-1, this.slotSimIds);
   }

   private void loadQueuedPortrait() {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      int slot = this.portraitSlot;
      int simId = this.portraitSimId;
      int palette = neighborhood.simAttribute(simId, 0) == 0 ? 512 : 256;
      GameCanvas.gcYield();
      if (canvas.loadNextTileGroup(palette, this.recolorSource, this.recolorTarget, slot)) {
         int tileId = this.simPortraitTile(simId);
         SpriteRenderer.loadTileRecolored(canvas.resources, tileId, this.recolorSource, this.recolorTarget, slot);
         this.findSimByIndex(simId).recolorIndex = slot;
         this.portraitSimId = -1;
         this.portraitSlot = slot + 1;
         this.pendingPortraits--;
         if (simId == 0) {
            this.loadPlayerPortrait();
         }

         if (this.pendingPortraits > 0) {
            this.pickNextPortrait();
         }
      }
   }

   private void loadPlayerPortrait() {
      this.currentLot.buildPaletteFor(0, this.recolorSource, this.recolorTarget);
      int palette = this.neighborhood.simAttribute(0, 0) == 0 ? 4096 : 8192;
      super.canvas.loadTiles(palette, this.recolorSource, this.recolorTarget, 0);
   }

   private int simPortraitTile(int simId) {
      Neighborhood neighborhood = this.neighborhood;
      int variant = this.neighborhood.simAttribute(simId, 2);
      if (neighborhood.simAttribute(simId, 0) == 0) {
         if (variant == 0) {
            return 78;
         } else if (variant == 1) {
            return 79;
         } else {
            return variant == 2 ? 80 : 81;
         }
      } else if (variant == 0) {
         return 70;
      } else if (variant == 1) {
         return 71;
      } else {
         return variant == 2 ? 72 : 73;
      }
   }

   public final void layoutViewport() {
      GameCanvas canvas = super.canvas;
      Lot lot = this.currentLot;
      this.bottomBarHeight = SpriteRenderer.frameHeight(587, 0) + SpriteRenderer.frameHeight(586, 0);
      this.viewX = 0;
      this.viewY = 0;
      this.viewWidth = canvas.getWidth() - this.viewX;
      this.viewHeight = canvas.getHeight() - 15 - this.viewY - this.bottomBarHeight + 5;
      Tile tile = this.currentLot.groundTile;
      this.currentLot.groundTile.setBackingEnabled(true);
      tile.invalidateBacking();
      lot.setBounds(this.viewX, this.viewY, this.viewWidth, this.viewHeight);
      this.entitiesDirty = true;
      this.simsDirty = true;
      Sim player = this.activePlayer;
      int playerX = this.activePlayer.posX;
      int playerY = player.posY;
      this.camTargetX = lot.toScreenFixedX(playerX, playerY);
      this.camTargetY = lot.toScreenFixedY(playerX, playerY);
      this.camX = this.camTargetX;
      this.camY = this.camTargetY;
      this.clearCameraTarget();
      this.snapCameraToTarget();
      tile.setBackingEnabled(true);
      tile.invalidateBacking();
   }

   public final void setLanguage(int startMode) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = super.canvas.neighborhood;
      super.canvas.neighborhood.resetAll();
      if (super.leftArrowAnim == null) {
         super.leftArrowAnim = new Animator();
         super.rightArrowAnim = new Animator();
      }

      screenState = 0;
      this.keyRepeatTime = 0;
      this.pendingTick = 0;
      this.dialogX = 0;
      this.dialogY = -1;
      this.inputIdle = false;
      this.bottomBarHeight = SpriteRenderer.frameHeight(587, 0) + SpriteRenderer.frameHeight(586, 0);
      this.viewX = 0;
      this.viewY = 0;
      this.viewWidth = canvas.getWidth() - this.viewX;
      this.viewHeight = canvas.getHeight() - 15 - this.viewY - this.bottomBarHeight + 5;
      this.visibleRows = (short)(this.viewHeight / 30);
      this.loaderThread = null;
      this.threadState = 0;
      this.resetPortraitQueue();
      this.musicStarted = false;
      this.dialogActive = false;
      this.lastTickDuration = 0;
      this.displayedMoney = neighborhood.money;
      this.interactionSource = 0;
      this.fadePhase = 0;
      this.notifyId = -1;
      this.notifyId2 = -1;
      this.followTarget = null;
      this.followFlag = 0;
      this.movingToTarget = false;
      this.leftPressed = false;
      this.rightPressed = false;
      this.downPressed = false;
      this.upPressed = false;
      this.clampTargetX = 0;
      this.clampTargetY = 0;
      this.camVelX = 0;
      this.camVelY = 0;
      this.prevCamVelX = 0;
      this.prevCamVelY = 0;
      this.cameraClamped = false;
      this.cameraTarget = null;
      this.recenterPending = false;
      this.entityCache = null;
      this.simCache = null;
      this.activePlayer = null;
      if (this.entityStack == null) {
         this.entityStack = new Stack();
      }

      this.entityStack.removeAllElements();
      this.entitiesDirty = true;
      this.simsDirty = true;
      this.menuOpen = false;
      this.menuSubPage = false;
      this.menuParent = 0;
      if (this.menuLabels == null) {
         this.menuLabels = new short[55];
         this.menuArgs = new short[55];
         this.menuExtra = new short[55];
      }

      this.menuTarget = null;
      this.mainMenuOpen = false;
      if (this.menuOptionIds == null) {
         short[] soundChoices;
         GameCanvas.initListWith(soundChoices = new short[15], 66, canvas.menuRowSpacing);
         GameCanvas.addToList(soundChoices, 67);
         GameCanvas.addToList(soundChoices, 60);
         GameCanvas.addToList(soundChoices, 61);
         GameCanvas.addToList(soundChoices, 70);
         GameCanvas.addToList(soundChoices, 72);
         GameCanvas.addToList(soundChoices, 74);
         GameCanvas.addToList(soundChoices, 76);
         this.menuOptionIds = soundChoices;
      }

      this.mainMenuPage = 0;
      this.actionMenuOpen = false;
      this.tabSwitchRequested = false;
      if (this.actionOptionIds == null) {
         this.actionOptionIds = new short[20];
      }

      this.objectMenuOpen = false;
      if (this.objectOptionIds == null) {
         this.objectOptionIds = new short[25];
      }

      this.menuPage = 0;
      this.pendingPlacement = null;
      this.optionsTab = -1;
      this.shownSimId = 0;
      this.rosterIndex = 0;
      this.objectIndex = 0;
      this.objectRow = 0;
      this.friendSimId = 0;
      this.friendLevel = 0;
      this.requestAmount = 0;
      this.choiceMade = false;
      this.pendingEvent = false;
      this.pendingGoHome = false;
      this.pendingDialogId = -1;
      this.dialogArg1 = 0;
      this.dialogArg2 = 0;
      this.dialogText = 0;
      this.aiTimer = 0;
      this.soundTimer = 0;
      this.soundChoices = null;
      this.idleTimer = 0;
      this.resetInteractionQueue();
      this.neighborhood = neighborhood;
      this.currentLot = canvas.lot;
      this.relationPercent = neighborhood.moodValue;
      this.timeAdvanceSeconds = 0;
      this.dialogRequestPending = false;
      this.miniGamePhase = 0;
      this.savedTarget = null;
      this.miniStep = 0;
      this.miniTarget = null;
      if (this.candleAnimValues == null) {
         this.candleAnimValues = new int[4];
         this.candleTargets = new int[4];
         this.candleFrames = new byte[4];
      }

      this.seedRows = (byte[][])null;
      this.seedTypes = null;
      this.seedCells = (short[][])null;
      this.plantPhase = 0;
      if (this.statusAnimator == null) {
         this.statusAnimator = new Animator();
         this.needUpAnimator = new Animator();
         this.needDownAnimator = new Animator();
         this.selectionAnimator = new Animator();
         this.slotAnimators = new Animator[4];

         for (int slot = 0; slot < 4; slot++) {
            this.slotAnimators[slot] = new Animator();
         }

         this.cursorAnimator = new Animator();
         this.effectAnimators = new Animator[24];
         this.effectPositions = new int[24][3];

         for (int effect = 0; effect < 24; effect++) {
            this.effectAnimators[effect] = new Animator();
         }
      }

      this.statusAnimator.play(672, 20);
      this.needUpAnimator.play(637, 20);
      this.needDownAnimator.play(636, 20);
      this.selectionAnimator.play(567, 20);
      this.cursorAnimator.play(583, 20);
      this.initLayout();
      this.saveSlotMode = 0;
      if (startMode == 99) {
         this.saveSlotMode = 2;
      } else if (startMode == 98) {
         this.saveSlotMode = 2;
         startMode = 5;
      } else if (startMode == 100) {
         this.saveSlotMode = 1;
         startMode = 5;
      } else if (startMode == 97) {
         this.timeAdvanceSeconds = 30;
         startMode = 5;
      } else if (startMode == -1) {
         startMode = 5;
      }

      this.startOption = startMode;
      this.onTick(1);
   }

   public final void stop() {
      if ((screenState == 5 || screenState == 16) && !this.mainMenuOpen && !this.dialogActive) {
         this.openMainMenu();
      }
   }

   public final void pause() {
      this.currentLot.dispose();
      int configFlags = 2013688824;
      if (super.canvas.pendingScreenAction == 2) {
         configFlags &= -9;
      }

      super.canvas.unloadTiles(configFlags, -1);
      SdkSound.muteAll();
   }

   public final void render(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      switch (screenState) {
         case 1:
            canvas.drawLoadingScreen(gfx);
            return;
         case 3:
         case 9:
            this.drawGameLayer(gfx);
            canvas.drawDialogOverlay(gfx);
            this.drawPageOne(gfx);
            this.drawSoftKeyHint(gfx);
            return;
         case 5:
            this.drawGameLayer(gfx);
            this.drawSoftKeyHint(gfx);
            return;
         case 6:
            return;
         case 7:
            this.drawMenuPage(gfx);
            return;
         case 8:
            this.drawOptionsPage(gfx);
            return;
         case 10:
            this.drawFriendPanel(gfx);
            return;
         case 11:
            this.drawRequestPage(gfx);
            return;
         case 12:
            this.drawRequestDetails(gfx);
            return;
         case 13:
            this.drawSimplePage(gfx);
            return;
         case 14:
            this.drawRelationshipPage(gfx);
            return;
         case 15:
            this.drawMessagePage(gfx);
            return;
         case 16:
            this.drawMiniGame(gfx);
            return;
         case 17:
            this.drawVehiclePage(gfx);
            return;
         case 18:
            this.drawSimpleOverlay(gfx);
            return;
         case 19:
            this.drawDialog(gfx);
         case 2:
         case 4:
      }
   }

   private void drawSoftKeyHint(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      super.canvas.drawBottomBar(gfx);
      int[] hitRect = this.frameBounds;
      int centerX = canvas.getWidth() >> 1;
      int bottomY = canvas.getHeight();
      SpriteRenderer.drawFrame(gfx, 635, 0, centerX, bottomY);
      SpriteRenderer.hitTest(hitRect, 635, 0, 0);
      int textX = centerX + hitRect[0];
      int textY = bottomY + hitRect[1];
      SdkString label = canvas.beginString();
      canvas.formatNumber(this.displayedMoney);
      canvas.drawString(label, 2, textX, textY, 24);
   }

   private void drawGameLayer(Graphics gfx) {
      this.currentLot.draw(gfx);
      if (!this.objectMenuOpen) {
         this.drawTopBar(gfx);
      }

      if (this.hasFade()) {
         this.drawFadeBars(gfx);
      }

      if (this.mainMenuOpen) {
         this.drawCursorSelection(gfx);
         this.drawSimInfoPanel(gfx);
         this.drawBottomBar(gfx);
         this.drawRelationshipBar(gfx);
         this.drawMainMenu(gfx);
      } else if (!this.inputIdle && this.menuOpen) {
         this.drawSimInfoPanel(gfx);
         this.drawBottomBar(gfx);
         this.drawRelationshipBar(gfx);
         this.drawInteractionPrompt(gfx);
      } else if (this.dialogActive) {
         this.drawSimInfoPanel(gfx);
         this.drawBottomBar(gfx);
         this.drawRelationshipBar(gfx);
         super.canvas.drawDialogOverlay(gfx);
         this.drawPageTwo(gfx);
      } else {
         if (screenState != 16) {
            this.drawCursorSelection(gfx);
         }

         this.drawSimInfoPanel(gfx);
         this.drawBottomBar(gfx);
         this.drawRelationshipBar(gfx);
         if (!this.inputIdle && this.actionMenuOpen) {
            this.drawActionMenu(gfx);
         }
      }
   }

   public final boolean matchesInput(int keyState, int actionMask, int pageMask, int dataMask) {
      if (!super.matchesInput(keyState, actionMask, pageMask, dataMask)) {
         return false;
      } else {
         SdkSound sound = super.canvas.sound;
         super.canvas.sound.setLoopCount(1);
         if ((pageMask & 4128) != 0) {
            if (this.mainMenuOpen || this.menuOpen || this.actionMenuOpen) {
               sound.pendingSound = 3;
            }
         } else if ((actionMask & 32027) != 0) {
            sound.pendingSound = 3;
         } else if ((actionMask & 33508) == 0 && (pageMask & 524288) == 0) {
            if ((pageMask & 24836) != 0 && super.selectedIndex != -1) {
               sound.pendingSound = 0;
            } else if ((pageMask & 98384) != 0) {
               sound.pendingSound = 1;
            }
         } else if (this.actionMenuOpen || !this.menuOpen || this.menuSubPage) {
            sound.pendingSound = 2;
         }

         return true;
      }
   }

   public final void update(int keyState, int actionMask) {
      if (screenState != 1 && (!this.hasFade() || this.fadePhase == 2)) {
         GameCanvas canvas = super.canvas;
         if (super.canvas.fadeFinished && this.pendingTick != 0) {
            this.onTick(this.pendingTick);
            canvas.fadeFromBlack();
            this.pendingTick = 0;
         } else if (!canvas.isFading()) {
            if (this.mainMenuOpen) {
               this.handleMainMenuPageInput(keyState, actionMask);
            } else if (!this.inputIdle && this.menuOpen) {
               this.handleInteractionMenuInput(keyState, actionMask);
            } else if (!this.inputIdle && this.actionMenuOpen) {
               this.handleActionMenuInput(keyState, actionMask);
            } else if (this.dialogActive) {
               this.handleDialogKey(keyState, actionMask);
            } else if (this.objectMenuOpen) {
               this.handleObjectMenuInput(keyState, actionMask);
            } else {
               switch (screenState) {
                  case 3:
                     this.handleMainMenuInput(keyState, actionMask);
                     return;
                  case 5:
                     this.handlePlayInput(keyState, actionMask);
                     return;
                  case 6:
                     return;
                  case 7:
                     this.handleMenuPageInput(keyState, actionMask);
                     return;
                  case 8:
                     this.handleOptionsInput(keyState, actionMask);
                     return;
                  case 9:
                     this.handleDialogChoice(keyState, actionMask);
                     return;
                  case 10:
                     this.handleFriendPageInput(keyState, actionMask);
                     return;
                  case 11:
                     this.handleRequestPageInput(keyState, actionMask);
                     return;
                  case 12:
                     this.handleRequestChoiceInput(keyState, actionMask);
                     return;
                  case 13:
                     this.handleRequestCancel(keyState, actionMask);
                     return;
                  case 14:
                     this.handleRelationshipInput(keyState, actionMask);
                     return;
                  case 15:
                     this.handleMessageInput(keyState, actionMask);
                     return;
                  case 16:
                     this.handleMiniGameInput(keyState, actionMask);
                     return;
                  case 17:
                     this.handleVehicleListInput(keyState, actionMask);
                     return;
                  case 18:
                     this.handleGoalInput(keyState, actionMask);
                     return;
                  case 19:
                     this.handleDialogPhaseInput(keyState, actionMask);
                  case 4:
               }
            }
         }
      } else {
         updateBlocked = true;
      }
   }

   private void handleMainMenuInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 4128, 256)) {
         super.canvas.saveSettings();
         super.canvas.saveGameData();
         this.queueTick(2);
      } else {
         if (this.matchesInput(keyState, actionMask, 0, 512)) {
            this.queueTick(2);
         }
      }
   }

   private void handlePlayInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 0, 5)) {
         this.openMainMenu();
      } else if (this.matchesInput(keyState, actionMask, 0, 1024)) {
         this.openActionMenu();
      } else {
         this.handleInputBits(keyState);
         if (this.matchesInput(keyState, actionMask, 4128, 16)) {
            this.useCursorTarget();
         }

         if (this.matchesInput(keyState, actionMask, 2048, 0)) {
            this.onTick(8);
            this.setOptionsTab(4);
            this.tabSwitchRequested = true;
         } else {
            if (this.matchesInput(keyState, actionMask, 1024, 0)) {
               this.onTick(8);
               this.setOptionsTab(3);
               this.tabSwitchRequested = true;
            }
         }
      }
   }

   public final void keyPressed(int keyCode) {
      this.keyRepeatTime += keyCode;
      if (screenState == 1) {
         this.startLoading(keyCode);
      } else {
         this.advanceAnimators(keyCode);
         super.canvas.updateMarquees(keyCode);
         switch (screenState) {
            case 5:
               this.updateGame(keyCode);
               return;
            case 6:
               this.loadQueuedPortrait();
               if (this.pendingPortraits == 0) {
                  this.onTick(5);
                  return;
               }
               break;
            case 7:
               this.updateMenuPageAnim(keyCode);
               return;
            case 8:
               this.advanceMenuArrows(keyCode);
               return;
            case 9:
               return;
            case 10:
               return;
            case 11:
               return;
            case 12:
               return;
            case 13:
               return;
            case 14:
               return;
            case 15:
               return;
            case 16:
               this.updateMiniGame(keyCode);
               return;
            case 17:
               return;
            case 18:
               return;
            case 19:
               this.updateDialogPhase(keyCode);
         }
      }
   }

   public final void onTick(int tick) {
      GameCanvas canvas = super.canvas;
      screenState = tick;
      this.keyRepeatTime = 0;
      this.inputIdle = true;
      canvas.resetMarquees();
      this.clearMoveFlags();
      switch (tick) {
         case 1:
            this.loadStep = 0;
            this.menuOpen = false;
            this.mainMenuOpen = false;
            break;
         case 2:
            canvas.setPendingScreen(this.dialogX, this.dialogY);
            break;
         case 3:
            this.closeOpenMenus();
            this.showInfoPanel(84, 69);
            break;
         case 4:
            canvas.muteSound();
            break;
         case 5:
            this.followFlaggedEntity(1);
            this.inputIdle = false;
            break;
         case 6:
            this.pickNextPortrait();
            this.showInfoPanel(32, 33);
            break;
         case 7:
            this.inputIdle = false;
         case 8:
         case 9:
         case 10:
         case 12:
         case 13:
         case 14:
         case 16:
         case 17:
         case 18:
         case 19:
         default:
            break;
         case 11:
            this.requestAmount = this.neighborhood.tick();
            this.showInfoPanel(704, 703);
            break;
         case 15:
            this.showInfoPanel(this.dialogText, this.dialogArg2);
      }

      this.updateInputMasks();
   }

   private void queueTick(int tick) {
      this.pendingTick = tick;
      if (!super.canvas.isFadingFromWhite()) {
         super.canvas.fadeFromWhite();
      }

      this.clearMoveFlags();
   }

   private void openDialogAt(int posX, int posY) {
      if (super.canvas.savePromptsEnabled) {
         this.dialogX = posX;
         this.dialogY = posY;
         this.onTick(3);
      } else {
         this.showDialogAt(posX, posY);
      }
   }

   private void showDialogAt(int posX, int posY) {
      this.dialogX = posX;
      this.dialogY = posY;
      this.queueTick(2);
   }

   private void showDialogAtOnce(int posX, int posY) {
      this.dialogX = posX;
      this.dialogY = posY;
      this.onTick(2);
   }

   private void updateInputMasks() {
      int actionMask = 0;
      short pageMask = 0;
      if (this.mainMenuOpen) {
         if (this.mainMenuPage != 1 && this.mainMenuPage != 2 && this.mainMenuPage != 3 && this.mainMenuPage != 4) {
            pageMask = 4;
            actionMask = 16;
         } else {
            pageMask = 512;
            actionMask = 256;
         }
      } else if (!this.inputIdle && this.menuOpen) {
         pageMask = 4;
         actionMask = 16;
      } else if (!this.inputIdle && this.actionMenuOpen) {
         pageMask = 4;
         actionMask = 16;
      } else {
         label96:
         if (this.dialogActive) {
            actionMask = 8;
         } else if (this.objectMenuOpen) {
            pageMask = 4;
            actionMask = 16;
         } else {
            label92:
            switch (screenState) {
               case 3:
                  pageMask = 512;
                  actionMask = 256;
               case 4:
               case 6:
               default:
                  break;
               case 5:
                  pageMask = 1;
                  actionMask = 1024;
                  break;
               case 7:
                  switch (this.menuPage) {
                     case 1:
                        pageMask = 4;
                        actionMask = 16;
                        break label92;
                     case 2:
                        pageMask = 4;
                        actionMask = 16;
                        break label92;
                     case 3:
                     case 7:
                     case 8:
                        actionMask = 8;
                        break label92;
                     case 4:
                     case 11:
                        pageMask = 4;
                        actionMask = 2048;
                        break label92;
                     case 5:
                     case 9:
                        pageMask = 512;
                        actionMask = 256;
                     case 6:
                     default:
                        break label92;
                     case 10:
                        if (this.currentLot.stageReward() <= this.neighborhood.money) {
                           pageMask = 512;
                           actionMask = 256;
                        } else {
                           actionMask = 8;
                        }
                        break label92;
                  }
               case 8:
                  switch (this.optionsTab) {
                     case 0:
                        pageMask = 4;
                        actionMask = this.neighborhood.idleSimCount() > 0 ? 16 : 0;
                        break label92;
                     case 1:
                     case 2:
                     case 3:
                     case 4:
                     case 5:
                     case 9:
                     case 10:
                        pageMask = 4;
                        break label92;
                     case 6:
                        actionMask = 0;
                        pageMask = 4;
                        int objectIndex;
                        if ((objectIndex = this.getSelectedObjectIndex()) != -1 && (this.currentLot.categoryMaskAt(objectIndex) & 2048) != 0) {
                           actionMask = 16;
                        }
                     case 7:
                     case 8:
                     default:
                        break label92;
                  }
               case 9:
                  pageMask = 0;
                  break label96;
               case 10:
                  if (this.friendSimId != -1) {
                     pageMask = 512;
                     actionMask = 256;
                  } else {
                     actionMask = 8;
                  }
                  break;
               case 11:
                  actionMask = 8;
                  break;
               case 12:
                  actionMask = 8;
                  break;
               case 13:
                  if (!this.choiceMade) {
                     pageMask = 512;
                     actionMask = 256;
                     break;
                  }
                  break label96;
               case 14:
                  pageMask = 128;
                  break label96;
               case 15:
                  actionMask = 8;
                  break;
               case 16:
                  pageMask = 1;
                  actionMask = 64;
                  break;
               case 17:
                  int plantPhase = this.plantPhase;
                  if (this.plantPhase == 0) {
                     pageMask = 4;
                     if (this.interactionAmount > 0) {
                        actionMask = 4096;
                     } else if (this.interactionAmount < 0) {
                        actionMask = 8192;
                     }
                  }
                  break;
               case 18:
                  switch (this.dialogPhase) {
                     case 0:
                        pageMask = 512;
                        actionMask = 256;
                        break label92;
                     case 1:
                        actionMask = 8;
                        break label92;
                     case 2:
                     case 3:
                        actionMask = 8;
                     default:
                        break label92;
                  }
               case 19:
                  switch (this.miniGamePhase) {
                     case 3:
                        break label96;
                  }
            }
         }
      }

      GameCanvas canvas = super.canvas;
      super.canvas.releaseSoftKeys();
      canvas.clearActions();
      canvas.setSoftKeys(pageMask, actionMask);
   }

   private void closeOpenMenus() {
      if (this.menuOpen) {
         this.closeInteractionMenu();
      }

      if (this.actionMenuOpen) {
         this.closeActionMenu();
      }

      if (this.objectMenuOpen) {
         this.closeObjectMenu();
      }
   }

   public final void startMusic() {
      if (!this.musicStarted) {
         SdkSound sound = super.canvas.sound;
         if (super.canvas.sound.enabled) {
            sound.currentLoop = 0;
            sound.pendingSound = 11;
            sound.currentLoop = 1;
            this.musicStarted = true;
         }
      }
   }

   public final void chooseDialogOption(int posX, int posY) {
      this.openDialog(posX, posY, 1);
   }

   public final void openStatsDialog(int simIndex) {
      super.canvas.formatStringWith(-11, simIndex, super.canvas.profileName());
      this.openDialogPage(0, -11, -1);
   }

   public final void cancelPlayerAction() {
      this.activePlayer.queueAction(6, (Entity) null);
      this.syncFadeWithAction();
   }

   private void continueSavedGame() {
      super.canvas.setHouse(0);
      this.openDialogAt(2, 97);
   }

   private void startNewGame() {
      this.openDialogAt(2, 99);
   }

   public final void startTutorialGame() {
      this.openDialogAt(2, 98);
   }

   public final void selectEntity(Entity entity) {
      int type = entity.type;
      if (entity.type == 116) {
         super.canvas.enterHouseWith((Sim) null);
         super.canvas.houseUpgrade = 39;
         this.openDialogAt(2, 100);
      } else if (type == 1) {
         super.canvas.enterHouseWith((Sim)entity);
         super.canvas.houseUpgrade = 41;
         this.openDialogAt(2, 100);
      } else {
         int houseIndex = this.currentLot.indexOfLotId(type);
         super.canvas.setHouse(houseIndex);
         this.openDialogAt(2, -1);
      }
   }

   public final void showNeighborhoodIntro(byte mode) {
      GameCanvas canvas = super.canvas;
      if (!super.canvas.hasProgressFlag(mode)) {
         canvas.setProgressFlag(mode);
         if (canvas.tutorialsEnabled) {
            short textId;
            byte imageId;
            switch (mode) {
               case 0:
               default:
                  textId = 90;
                  imageId = 91;
                  break;
               case 1:
                  textId = 92;
                  imageId = 93;
                  break;
               case 2:
                  textId = 94;
                  imageId = 95;
                  break;
               case 3:
                  textId = 96;
                  imageId = 97;
                  break;
               case 4:
                  textId = 98;
                  imageId = 99;
                  break;
               case 5:
                  textId = 100;
                  imageId = 101;
                  break;
               case 6:
                  textId = 500;
                  imageId = 102;
                  break;
               case 7:
                  textId = 514;
                  imageId = 106;
                  break;
               case 8:
                  textId = 507;
                  imageId = 103;
                  break;
               case 9:
                  textId = 493;
                  imageId = 104;
                  break;
               case 10:
                  textId = 400;
                  imageId = 107;
                  break;
               case 11:
                  textId = 403;
                  imageId = 108;
                  break;
               case 12:
                  textId = 404;
                  imageId = 109;
                  break;
               case 13:
                  textId = 409;
                  imageId = 110;
                  break;
               case 14:
                  textId = 408;
                  imageId = 111;
                  break;
               case 15:
                  textId = 410;
                  imageId = 112;
            }

            this.closeOpenMenus();
            this.showPanelTwo(imageId, textId);
            this.dialogActive = true;
            this.updateInputMasks();
         }
      }
   }

   private void handleDialogKey(int keyState, int actionMask) {
      this.scrollMenu(keyState);
      if (this.matchesInput(keyState, actionMask, 4128, 8)) {
         this.closeDialog();
      }
   }

   private void closeDialog() {
      this.dialogActive = false;
      this.updateInputMasks();
   }

   private boolean isUiBlocked() {
      return this.mainMenuOpen || this.actionMenuOpen || this.dialogActive || screenState != 5;
   }

   private void updateGame(int delta) {
      if (!this.isUiBlocked()) {
         Neighborhood neighborhood = this.neighborhood;
         if (this.pendingPortraits > 0) {
            this.onTick(6);
         } else {
            if (this.hasFade()) {
               this.updateFade(delta);
            } else {
               updateBlocked = false;
            }

            if (this.dialogActive || neighborhood.isCelebrating() || !this.checkPendingEvents()) {
               this.advanceNotification(delta);
               this.lastTickDuration = delta;
               int scaledDelta = neighborhood.paused ? delta << 2 : delta;
               this.advanceAllEntities(scaledDelta);
               this.updatePlayerAnimator(delta);
               this.updateCameraPan(delta);
               this.advanceArrows(delta);
               if (!this.hasFade() && !super.canvas.isFading()) {
                  this.updateClock(delta);
                  if (this.saveSlotMode == 2) {
                     this.updateNpcSpawning(delta);
                  } else if (this.saveSlotMode == 0) {
                     this.updateTownieAi(delta);
                  } else {
                     this.updateRandomSimAi(delta);
                  }

                  if (this.soundChoices != null) {
                     this.playRandomSound(delta, this.soundInterval, this.soundChoices);
                  }

                  this.promoteNextInteraction();
               }
            }
         }
      }
   }

   private boolean checkPendingEvents() {
      Neighborhood neighborhood = this.neighborhood;
      if (this.dialogRequestPending) {
         this.dialogRequestPending = false;
         this.onTick(18);
         this.setGoalPage(3);
         return true;
      } else if (this.pendingDialogId != -1) {
         this.onTick(15);
         return true;
      } else if (neighborhood.unlockSecondMilestone()) {
         this.openDialogPage(8, -1, -1);
         return true;
      } else if (this.pendingEvent) {
         this.pendingEvent = false;
         super.canvas.markGameStarted();
         this.chooseDialogOptionAt(786, 747);
         return true;
      } else if (neighborhood.checkCareerPromotion()) {
         this.chooseDialogOptionAt(787, 788);
         return true;
      } else {
         int eventId;
         if ((eventId = neighborhood.pendingPromotion()) != -1) {
            this.chooseDialogOptionAt(eventId, 771);
            return true;
         } else {
            if (this.pendingGoHome) {
               this.pendingGoHome = false;
               if (neighborhood.pendingAchievementId != -1) {
                  this.goToSleep();
                  return true;
               }
            }

            return false;
         }
      }
   }

   private void advanceAnimators(int delta) {
      this.statusAnimator.advance(delta);
      int money = this.neighborhood.money;
      this.displayedMoney = BaseScreen.approach(this.displayedMoney, money, delta, 9);
      if (!this.isUiBlocked()) {
         this.needDownAnimator.advance(delta);
         this.needUpAnimator.advance(delta);
         this.cursorAnimator.advance(delta);
      }

      if (screenState == 5 && !this.actionMenuOpen && !this.mainMenuOpen) {
         Neighborhood neighborhood = this.neighborhood;

         for (int slot = 0; slot < this.slotAnimators.length; slot++) {
            int moodId = neighborhood.achievementSlotValue(slot);
            Animator animator;
            (animator = this.slotAnimators[slot]).advance(delta);
            if (!animator.isRunning() && (animator.animId == 590 || moodId != -1)) {
               animator.play(589, 20);
            }
         }
      }
   }

   private void drawTopBar(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      int[] hitRect = this.frameBounds;
      int barX = this.viewX + this.viewWidth;
      int barY = this.viewY;
      if (GameCanvas.landscape) {
         barY += 20;
      }

      if (screenState == 7) {
         int iconW = SpriteRenderer.frameWidth(596, 0);
         int iconH = SpriteRenderer.frameHeight(596, 0);
         int iconX = canvas.getWidth() - (iconW + 9);
         int iconY = iconH + 3 - 1;
         this.drawIconBar(gfx, 3, iconX, iconY, 114);
      } else {
         int firstIconW = SpriteRenderer.frameWidth(4, 0);
         int normalIconW = SpriteRenderer.frameWidth(5, 0);
         int slotX = this.viewX + 5 + (normalIconW >> 1);
         int slotY = barY + 3 + (normalIconW >> 1);
         int cursorX = slotX;
         int slotCount = this.queuedCount;
         int[] slotIds = this.queuedInteractions;
         Entity[] slotEntities = this.queuedTargets;

         for (int index = 0; index < slotCount; index++) {
            int objectType = slotIds[index];
            Entity entity = slotEntities[index];
            byte frame = 5;
            int slotWidth = normalIconW;
            if (index == 0) {
               frame = 4;
               slotWidth = firstIconW;
            }

            int iconFrame = 196;
            if (objectType == 106) {
               iconFrame = 3;
            } else if (entity != null) {
               if (entity.hasFlag(16384) && !this.isNewNeighborhood()) {
                  iconFrame = 6;
               } else {
                  iconFrame = this.currentLot.spriteVariantAt(entity.type);
               }
            }

            SpriteRenderer.drawFrame(gfx, frame, 0, cursorX, slotY);
            SpriteRenderer.drawFrame(gfx, iconFrame, 0, cursorX, slotY);
            cursorX += slotWidth + 2;
         }
      }

      int money = neighborhood.currentMinuteOfDay();
      SdkString label = canvas.beginString();
      canvas.appendTime(money);
      SpriteRenderer.drawFrame(gfx, 596, 0, barX, barY);
      SpriteRenderer.hitTest(hitRect, 596, 0, 0);
      int textX = barX + hitRect[0];
      int textY = barY + hitRect[1];
      SpriteRenderer.hitTest(hitRect, 596, 0, 1);
      int textX2 = barX + hitRect[0];
      int textY2 = barY + hitRect[1];
      canvas.drawString(label, 2, textX + 1, textY + 1, 24);
      int moodValue = Neighborhood.WEEKDAY_TEXT_IDS[neighborhood.dayOfWeek()];
      canvas.drawStringById(moodValue, 2, textX2 + 1, textY2 + 1, 20);
      byte anchorFrame;
      short statusAnimId;
      if (this.isUiBlocked()) {
         anchorFrame = 2;
         statusAnimId = 673;
      } else if (neighborhood.paused) {
         anchorFrame = 4;
         statusAnimId = 671;
      } else {
         anchorFrame = 3;
         statusAnimId = 672;
      }

      this.statusAnimator.play(statusAnimId, 4);
      SpriteRenderer.hitTest(hitRect, 596, 0, anchorFrame);
      SpriteRenderer.drawAnimator(gfx, this.statusAnimator, barX + hitRect[0], barY + hitRect[1]);
   }

   private void drawBottomBar(Graphics gfx) {
      if (this.objectMenuOpen) {
         this.drawObjectMenu(gfx);
      }

      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      int[] hitRect = this.frameBounds;
      int centerX = canvas.halfWidth();
      int bottomY = canvas.getHeight() - 15;
      if (screenState != 16 && screenState != 7 && this.getSelectedSimId() == -1 && !this.objectMenuOpen) {
         this.drawNeedArrows(gfx);
      }

      if (GameCanvas.landscape) {
         SpriteRenderer.drawFrame(gfx, 588, 0, centerX, bottomY);
      } else {
         SpriteRenderer.drawFrame(gfx, 587, 0, centerX, bottomY);
      }

      if (this.notifyId != -1) {
         this.drawNotification(gfx);
      }

      SpriteRenderer.hitTestMirrored(hitRect, 587, 0, 0);
      int iconX = centerX + hitRect[0];
      int iconY = bottomY + hitRect[1];

      for (int slot = 0; slot < 4; slot++) {
         int slotX = iconX + slot * 16;
         Animator animator;
         if ((animator = this.slotAnimators[slot]).isRunning()) {
            SpriteRenderer.drawAnimator(gfx, animator, slotX, iconY);
         }
      }

      SpriteRenderer.hitTestMirrored(hitRect, 587, 0, 1);
      iconX = centerX + hitRect[0] + -8;
      iconY = bottomY + hitRect[1] + (hitRect[3] >> 1);
      int barWidth = hitRect[2];
      int rightX = iconX + barWidth;
      int needCount = neighborhood.needCount;

      for (int index = 0; index < needCount && rightX > iconX; index++) {
         if (neighborhood.needValue(index) < 2293760) {
            short frameId = neighborhood.needHintText(index);
            SpriteRenderer.drawFrame(gfx, frameId, 0, rightX, iconY);
            rightX -= 16;
         }
      }

      for (int secondIndex = 0; secondIndex < needCount && rightX > iconX; secondIndex++) {
         if (neighborhood.needValue(secondIndex) > 4259840) {
            short secondFrameId = neighborhood.needLabelText(secondIndex);
            SpriteRenderer.drawFrame(gfx, secondFrameId, 0, rightX, iconY);
            rightX -= 16;
         }
      }
   }

   private void drawRelationshipBar(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      int centerX = super.canvas.getWidth() >> 1;
      int barY = canvas.getHeight() - 15;
      SpriteRenderer.drawFrame(gfx, 586, 0, centerX, barY);
      boolean negative = this.relationPercent < 0;
      int percent = Math.abs(this.relationPercent >> 16);
      int barWidth = SpriteRenderer.frameWidth(584, 0) * percent / 100;
      int clipX = centerX + (negative ? -barWidth : 0);
      int savedClipX = gfx.getClipX();
      int savedClipY = gfx.getClipY();
      int savedClipW = gfx.getClipWidth();
      int savedClipH = gfx.getClipHeight();
      gfx.setClip(clipX, savedClipY, barWidth, savedClipH);
      int frameId = negative ? 585 : 584;
      SpriteRenderer.drawFrame(gfx, frameId, 0, centerX, barY);
      gfx.setClip(savedClipX, savedClipY, savedClipW, savedClipH);
      this.drawCenteredLabel(gfx, centerX, barY);
   }

   public final void chooseDialogOptionAt(int posX, int posY) {
      this.openDialog(posX, posY, 0);
   }

   public final void openDialog(int posX, int posY, int source) {
      this.closeObjectMenu();
      this.interactionSource = source;
      this.showInfoPanel(posX, posY);
      this.onTick(9);
   }

   private void handleDialogChoice(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 4128, 12)) {
         switch (this.interactionSource) {
            case 0:
               this.onTick(5);
               return;
            case 1:
               this.startTutorialGame();
               return;
            case 2:
               if (this.activePlayer.actionId == 159) {
                  this.activePlayer.cancelAction();
               }

               this.onTick(5);
               return;
            case 3:
               this.openDialogPage(6, -1, -1);
               return;
            case 4:
               this.openDialogPage(7, -1, -1);
         }
      }
   }

   public final boolean hasFade() {
      return this.fadePhase != 0;
   }

   public final void syncFadeWithAction() {
      this.closeOpenMenus();
      this.pendingAction = this.activePlayer.actionId;
      this.setFadePhase(1);
   }

   private void startFadeIn() {
      this.closeOpenMenus();
      this.setFadePhase(2);
   }

   private void setFadePhase(int phase) {
      this.fadePhase = phase;
      switch (phase) {
         case 0:
            this.fadeAmount = 0;
            return;
         case 1:
            this.neighborhood.celebrate();
            this.fadeAmount = 0;
            return;
         case 2:
            this.fadeAmount = 1000;
            return;
         case 3:
            this.fadeAmount = 1000;
      }
   }

   private void updateFade(int delta) {
      if (this.cameraClamped) {
         switch (this.fadePhase) {
            case 0:
               return;
            case 1:
               this.neighborhood.celebrate();
               if (this.fadeAmount == 1000) {
                  this.setFadePhase(2);
                  this.runPendingAction();
                  return;
               }

               this.fadeAmount = Math.min(this.fadeAmount + delta, 1000);
               return;
            case 2:
               this.neighborhood.celebrate();
               this.fadeAmount += delta;
               if (this.fadeAmount >= 1500) {
                  this.setFadePhase(3);
                  return;
               }
               break;
            case 3:
               this.neighborhood.celebrate();
               if (this.fadeAmount == 0) {
                  this.setFadePhase(0);
                  this.finishPendingAction();
                  return;
               }

               this.fadeAmount = Math.max(this.fadeAmount - delta, 0);
         }
      }
   }

   private void runPendingAction() {
      switch (this.pendingAction) {
         case 6:
            if (this.isNoSaveMode() && this.currentLot.areaId() == 0) {
               this.assignFreeWillAction(this.activePlayer);
               this.timeAdvanceSeconds = 120;
               this.neighborhood.celebrate();
               Sim[] sims = this.getSims();

               for (int simIndex = 0; simIndex < sims.length; simIndex++) {
                  Sim player;
                  if ((player = sims[simIndex]) != this.activePlayer) {
                     if (this.neighborhood.simHomeLotOf(player.variant) != 0) {
                        player.despawn();
                     } else {
                        player.queueAction(0, (Entity) null);
                     }
                  }
               }

               return;
            } else {
               super.canvas.setHouse(0);
               this.showDialogAtOnce(2, 96);
            }
         default:
            return;
         case 121:
            this.neighborhood.skipToTime(this.activePlayer.timer - 15);
            return;
         case 139:
            Entity[] entities = this.getEntities();

            for (int index = 0; index < entities.length; index++) {
               entities[index].despawn();
            }

            this.currentLot.loadArea(0);
            return;
         case 159:
            this.neighborhood.endOfDay();
            this.clearNotificationPair();
            this.activePlayer.finishPhase();
      }
   }

   private void finishPendingAction() {
      switch (this.pendingAction) {
         case 139:
            this.openDialogPage(5, -1, -1);
            return;
         case 159:
            this.openRequestsDialog();
            return;
         default:
            this.neighborhood.celebrate();
      }
   }

   private void drawFadeBars(Graphics gfx) {
      if (this.cameraClamped) {
         int barHeight = (Math.min(this.fadeAmount, 1000) * this.viewHeight / 1000 >> 1) + 1;
         gfx.setColor(0);
         gfx.fillRect(this.viewX, this.viewY, this.viewWidth, barHeight);
         gfx.fillRect(this.viewX, this.viewY + this.viewHeight - barHeight, this.viewWidth, barHeight);
      }
   }

   public final void showNotification(int messageId, int secondId) {
      GameCanvas canvas = super.canvas;
      this.notifyId = (short)messageId;
      this.notifyId2 = (short)secondId;
      this.notificationTimer = 0;
      this.notificationScroll = 0;
      this.notificationWidth = (canvas.stringWidthById(messageId, 1) << 16) + 327680;
      if (secondId != -1) {
         this.notificationWidth = this.notificationWidth + (5 + canvas.stringWidthById(secondId, 1) << 16);
      }
   }

   private void clearNotification(int messageId) {
      if (this.notifyId == messageId) {
         this.notifyId = -1;
      }
   }

   private boolean hasNotification() {
      return this.notifyId != -1;
   }

   private void advanceNotification(int delta) {
      if (this.notifyId != -1) {
         this.notificationTimer += delta;
         if (this.notificationTimer > 2000) {
            if (this.notificationScroll > this.notificationWidth) {
               this.notifyId = -1;
               this.notifyId2 = -1;
               return;
            }

            this.notificationScroll += delta * 3276;
         }
      }
   }

   private void drawNotification(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      int bubbleHeight = SpriteRenderer.frameHeight(670, 0);
      int bubbleY = canvas.getHeight() - 15 - this.bottomBarHeight - bubbleHeight;
      int[] hitRect = this.frameBounds;
      SpriteRenderer.hitTest(this.frameBounds, 670, 0, 0);
      int anchorX = 0 + hitRect[0];
      int textY = bubbleY + hitRect[1];
      SpriteRenderer.drawFrame(gfx, 670, 0, 0, bubbleY);
      if (this.notificationTimer >= 1000 || (this.notificationTimer & 511) <= 255) {
         int scrollX = anchorX - (this.notificationScroll >> 16);
         canvas.drawStringById(this.notifyId, 1, scrollX, textY, 20);
         if (this.notifyId2 != -1) {
            int firstWidth = canvas.stringWidthById(this.notifyId, 1);
            int secondX = scrollX + firstWidth + 5;
            canvas.drawStringById(this.notifyId2, 1, secondX, textY, 20);
         }
      }
   }

   private void clearMoveFlags() {
      this.leftPressed = false;
      this.rightPressed = false;
      this.downPressed = false;
      this.upPressed = false;
      super.canvas.clearActions();
      super.canvas.releaseSoftKeys();
   }

   private void handleInputBits(int bits) {
      this.setMoveFlags(bits);
      if ((bits & 123870) != 0) {
         this.clearCameraTarget();
      }
   }

   private void setMoveFlags(int bits) {
      this.leftPressed = (bits & 32914) != 0;
      this.rightPressed = (bits & 66120) != 0;
      this.upPressed = (bits & 8206) != 0;
      this.downPressed = (bits & 17280) != 0;
   }

   private void useCursorTarget() {
      this.clearCameraTarget();
      Entity entity = this.followTarget;
      if (this.followTarget != null && this.atTarget) {
         if (entity == this.activePlayer) {
            this.openObjectMenu();
         } else {
            this.openInteractionMenu(entity);
         }
      } else {
         if (screenState == 5 && this.atTarget) {
            Lot lot = this.currentLot;
            int tileX = this.currentLot.toWorldX(this.camX, this.camY);
            int tileY = lot.toWorldY(this.camX, this.camY);
            if (this.currentLot.isWalkableAt(tileX, tileY)) {
               this.queueInteraction(106, null, tileX, tileY);
            }
         }
      }
   }

   private void drawCursorSelection(Graphics gfx) {
      if (this.menuPage != 6 && this.miniGamePhase != 1) {
         GameCanvas canvas = super.canvas;
         Lot lot = this.currentLot;
         int screenX = this.camX;
         int screenY = this.camY;
         if (this.objectMenuOpen && this.followTarget != null) {
            screenX = this.camTargetX;
            screenY = this.camTargetY;
         }

         screenX = (screenX >> 16) + lot.viewCenterX();
         screenY = (screenY >> 16) + lot.viewCenterY();
         if (this.objectMenuOpen) {
            this.drawMenuList(gfx, screenX, screenY);
         } else {
            SpriteRenderer.drawAnimator(gfx, this.selectionAnimator, screenX, screenY);
            int thoughtId;
            if (this.atTarget && this.followTarget != null && this.stillTime < 1000 && (thoughtId = this.followTarget.interactionAnimId()) != 24) {
               int thoughtW = canvas.stringWidthById(thoughtId, 1);
               int thoughtH = canvas.lineHeight(1) - 2;
               int boxW = 3 + thoughtW + 3;
               int boxH = 2 + thoughtH + 2;
               int boxX = Math.max(0, screenX + -5 - boxW);
               int boxY = screenY + -5 - boxH;
               SpriteRenderer.setColor(gfx, 77);
               gfx.drawRect(boxX, boxY, boxW, boxH);
               SpriteRenderer.setColor(gfx, 76);
               gfx.fillRect(boxX + 1, boxY + 1, boxW - 1, boxH - 1);
               canvas.drawStringById(thoughtId, 1, boxX + 3 + (thoughtW >> 1), boxY + 2, 17);
            }
         }
      }
   }

   private void updateMovement(int delta) {
      if (!this.movingToTarget) {
         int xDirection = (this.rightPressed ? 65536 : 0) + (this.leftPressed ? -65536 : 0);
         int yDirection = (this.downPressed ? 65536 : 0) + (this.upPressed ? -65536 : 0);
         if (xDirection != 0 || yDirection != 0) {
            int angle = FixedMath.atan2(yDirection, xDirection);
            this.turnToNearestEntity(angle);
         }
      }

      if (this.movingToTarget) {
         if (this.followTarget != null) {
            int[] hitRect = this.frameBounds;
            this.followTarget.computePosition(hitRect);
            this.camTargetX = hitRect[0];
            this.camTargetY = hitRect[1];
         }

         if (this.objectMenuOpen) {
         }

         int step = 19660 * delta;
         int targetDx = this.camTargetX - this.camX >> 8;
         int targetDy = this.camTargetY - this.camY >> 8;
         int distance = FixedMath.length(targetDx, targetDy);
         if (step >= distance << 8) {
            this.camX = this.camTargetX;
            this.camY = this.camTargetY;
         } else {
            step = FixedMath.div(step, distance);
            this.camX = this.camX + FixedMath.mul(targetDx, step);
            this.camY = this.camY + FixedMath.mul(targetDy, step);
         }

         this.atTarget = this.camX == this.camTargetX && this.camY == this.camTargetY;
         this.movingToTarget = this.movingToTarget && !this.atTarget;
      } else if (this.followTime <= 0) {
         int stepX = delta * ((this.rightPressed ? 13107 : 0) + (this.leftPressed ? -13107 : 0));
         int stepY = delta * ((this.downPressed ? 13107 : 0) + (this.upPressed ? -13107 : 0));
         this.camX += stepX;
         this.camY += stepY;
         if (stepX != 0 || stepY != 0) {
            this.followTarget = null;
            this.followTime = 0;
            Tile tile = this.currentLot.groundTile;
            int mapWidth = (this.currentLot.groundTile.columns * Tile.tileWidth() << 16) - 983040;
            int mapHeight = (tile.rowCountValue * Tile.tileHeight() << 16) - 983040;
            this.camX = FixedMath.clamp(this.camX, 0, mapWidth);
            this.camY = FixedMath.clamp(this.camY, 0, mapHeight);
            this.camTargetX = this.camX;
            this.camTargetY = this.camY;
            this.atTarget = true;
            this.stillTime = 0;
         } else if (this.followTarget != null && this.atTarget) {
            int[] hitRect2 = this.frameBounds;
            this.followTarget.computePosition(hitRect2);
            this.camTargetX = hitRect2[0];
            this.camTargetY = hitRect2[1];
            this.camX = hitRect2[0];
            this.camY = hitRect2[1];
         }
      }

      if (this.atTarget) {
         this.stillTime += delta;
      } else {
         this.stillTime = 0;
      }
   }

   private void updatePlayerAnimator(int delta) {
      if (!this.hasFade()) {
         if (!this.mainMenuOpen) {
            this.updateMovement(delta);
         }

         if (this.followTarget != null && this.followTime > 0) {
            this.followTime -= delta;
         }

         int frameId = (!this.atTarget || this.followTarget == null) && !this.menuOpen && (screenState != 7 || this.menuPage != 2) ? 566 : 567;
         this.selectionAnimator.play(frameId, 4);
         this.selectionAnimator.advance(delta);
      }
   }

   public final void followEntity(Entity entity) {
      this.followTarget = entity;
      if (entity != null) {
         int[] hitRect = this.frameBounds;
         entity.computePosition(hitRect);
         this.camTargetX = hitRect[0];
         this.camTargetY = hitRect[1];
         this.movingToTarget = true;
         this.stillTime = 0;
      } else {
         this.camTargetX = this.camX;
         this.camTargetY = this.camY;
         this.movingToTarget = false;
      }
   }

   private void followFlaggedEntity(int flag) {
      this.followFlag = flag;
      if (this.followTarget == null || !this.followTarget.hasFlag(flag)) {
         this.followEntity((Entity) null);
      }
   }

   private void turnToNearestEntity(int angle) {
      int[] hitRect = this.frameBounds;
      Entity best = null;
      int bestDistance = 7680;
      int originX = this.camX;
      int originY = this.camY;
      int wanted = FixedMath.wrapAngle(angle);
      Entity[] entities;
      int count = (entities = this.getEntities()).length;
      int flag = this.followFlag;

      for (int index = 0; index < count; index++) {
         Entity entity;
         (entity = entities[index]).computePosition(hitRect);
         int entityX = hitRect[0];
         int entityY = hitRect[1];
         if (entity != this.followTarget && entity.hasFlag(flag)) {
            int rangeX = this.viewWidth << 16;
            int rangeY = this.viewHeight << 16;
            int deltaX = Math.abs(originX - entityX);
            int deltaY = Math.abs(originY - entityY);
            if (deltaX <= rangeX && deltaY <= rangeY && (deltaX != 0 || deltaY != 0)) {
               int entityAngle = FixedMath.atan2(entityY - originY, entityX - originX);
               if (Math.abs(FixedMath.wrapAngle(wanted - entityAngle)) < 51472) {
                  int gapX = originX - entityX >> 8;
                  int gapY = originY - entityY >> 8;
                  int distance;
                  if ((distance = FixedMath.length(gapX, gapY)) < bestDistance) {
                     best = entity;
                     bestDistance = distance;
                  }
               }
            }
         }
      }

      if (best != null) {
         this.followEntity(best);
         this.followTime = 200;
      }
   }

   private void followNearestEntity() {
      int[] hitRect = this.frameBounds;
      Entity best = null;
      int bestDistance = Integer.MAX_VALUE;
      int originX = this.camX;
      int originY = this.camY;
      Entity[] entities;
      int count = (entities = this.getEntities()).length;
      int flag = this.followFlag;

      for (int index = 0; index < count; index++) {
         Entity entity;
         (entity = entities[index]).computePosition(hitRect);
         int entityX = hitRect[0];
         int entityY = hitRect[1];
         if (entity != this.followTarget && entity.hasFlag(flag)) {
            int deltaX = originX - entityX >> 8;
            int deltaY = originY - entityY >> 8;
            int distance;
            if ((distance = FixedMath.length(deltaX, deltaY)) < bestDistance) {
               best = entity;
               bestDistance = distance;
            }
         }
      }

      if (best != null) {
         this.followEntity(best);
         this.followTime = 200;
      }
   }

   private void updateCameraClampTarget() {
      Lot lot = this.currentLot;
      Neighborhood neighborhood = this.neighborhood;
      int targetX = this.camTargetX;
      int targetY = this.camTargetY;
      if (this.cameraTarget != null) {
         int[] hitRect = this.frameBounds;
         this.cameraTarget.computePosition(hitRect);
         targetX = hitRect[0];
         targetY = hitRect[1];
      } else if (this.menuOpen || neighborhood.watchedSim != -1) {
         Entity speaker = this.menuTarget;
         if (this.menuTarget == null && this.followTarget != null && this.followTarget.hasFlag(16384) && ((Sim)this.followTarget).variant == neighborhood.watchedSim) {
            speaker = this.followTarget;
         }

         if (speaker != null) {
            int halfRangeX = this.viewWidth << 14;
            if (speaker.hasFlag(16384)) {
               targetX -= halfRangeX;
            } else {
               targetX += halfRangeX;
            }
         }
      }

      Tile tile = lot.groundTile;
      int centreX = lot.boundsWidth;
      int halfCentreX = lot.boundsWidth >> 1;
      int centreY = lot.boundsHeight;
      int halfCentreY = lot.boundsHeight >> 1;
      int minX = halfCentreX << 16;
      int maxX = tile.columns * Tile.tileWidth() - halfCentreX - 1 << 16;
      int minY = halfCentreY << 16;
      int maxY = tile.rowCountValue * Tile.tileHeight() - halfCentreY - 1 << 16;
      if (GameCanvas.landscape) {
         maxY = tile.rowCountValue * Tile.tileHeight() - halfCentreY - 1 << 16;
      } else {
         maxX = tile.columns * Tile.tileWidth() - halfCentreX - 1 << 16;
      }

      targetX = FixedMath.clamp(targetX, minX, maxX);
      targetY = FixedMath.clamp(targetY, minY, maxY);
      this.clampTargetX = targetX;
      this.clampTargetY = targetY;
   }

   public final void setCameraTarget(Entity entity) {
      if (!this.menuOpen && !this.objectMenuOpen) {
         this.cameraTarget = entity;
         this.followTarget = null;
         if (entity == null) {
            this.recenterPending = true;
         }
      }
   }

   private void clearCameraTarget() {
      this.cameraTarget = null;
      this.recenterPending = false;
   }

   private void updateCameraPan(int delta) {
      Lot lot = this.currentLot;
      int maxSpeed = 11534;
      int lotX = 32;
      if (this.objectMenuOpen) {
         maxSpeed = FixedMath.mul(11534, 196608);
         lotX = FixedMath.mul(32, 196608);
      }

      int lotY = lot.cameraX;
      int signX = lot.cameraY;
      this.updateCameraClampTarget();
      int signY = FixedMath.sign(this.clampTargetX - lotY);
      int stepX = FixedMath.sign(this.clampTargetY - signX);
      if (signY > 0 && this.prevCamVelX > 0) {
         this.camVelX += lotX * delta;
      } else if (signY > 0 && this.prevCamVelX <= 0) {
         this.camVelX = 1441;
      } else if (signY < 0 && this.prevCamVelX < 0) {
         this.camVelX -= lotX * delta;
      } else if (signY < 0 && this.prevCamVelX >= 0) {
         this.camVelX = -1441;
      } else if (signY == 0 && this.prevCamVelX > 0) {
         this.camVelX = -1441;
      } else if (signY == 0 && this.prevCamVelX < 0) {
         this.camVelX = 1441;
      }

      if (this.camVelX < -maxSpeed) {
         this.camVelX = -maxSpeed;
      }

      if (this.camVelX > maxSpeed) {
         this.camVelX = maxSpeed;
      }

      if (stepX > 0 && this.prevCamVelY > 0) {
         this.camVelY += lotX * delta;
      } else if (stepX > 0 && this.prevCamVelY <= 0) {
         this.camVelY = 1441;
      } else if (stepX < 0 && this.prevCamVelY < 0) {
         this.camVelY -= lotX * delta;
      } else if (stepX < 0 && this.prevCamVelY >= 0) {
         this.camVelY = -1441;
      } else if (stepX == 0 && this.prevCamVelY > 0) {
         this.camVelY = -1441;
      } else if (stepX == 0 && this.prevCamVelY < 0) {
         this.camVelY = 1441;
      }

      if (this.camVelY < -maxSpeed) {
         this.camVelY = -maxSpeed;
      }

      if (this.camVelY > maxSpeed) {
         this.camVelY = maxSpeed;
      }

      int stepY = lotY + this.camVelX * delta;
      int nextX = signX + this.camVelY * delta;
      boolean reachedX;
      if ((this.camVelX <= 0 || stepY <= this.clampTargetX) && (this.camVelX >= 0 || stepY >= this.clampTargetX)) {
         reachedX = stepY == this.clampTargetX;
      } else {
         reachedX = true;
         stepY = this.clampTargetX;
      }

      boolean reachedY;
      if ((this.camVelY <= 0 || nextX <= this.clampTargetY) && (this.camVelY >= 0 || nextX >= this.clampTargetY)) {
         reachedY = nextX == this.clampTargetY;
      } else {
         reachedY = true;
         nextX = this.clampTargetY;
      }

      this.prevCamVelX = this.camVelX;
      this.prevCamVelY = this.camVelY;
      lot.cameraX = stepY;
      lot.cameraY = nextX;
      this.cameraClamped = reachedX && reachedY;
      if (this.cameraTarget != null || this.recenterPending) {
         this.camX += stepY - lotY;
         this.camY += nextX - signX;
      }
   }

   private void snapCameraToTarget() {
      this.updateCameraClampTarget();
      this.currentLot.cameraX = this.clampTargetX;
      this.currentLot.cameraY = this.clampTargetY;
      this.cameraClamped = true;
   }

   public final void placeCameraOnEntity(Entity entity) {
      this.followEntity(entity);
      this.camX = this.camTargetX;
      this.camY = this.camTargetY;
      this.snapCameraToTarget();
   }

   private void resumeSavedGame() {
      this.currentLot.loadArea(super.canvas.houseIndex);
      if (this.startOption == 96) {
         this.startOption = 5;
         this.assignFreeWillAction(this.activePlayer);
         this.timeAdvanceSeconds = 120;
         this.neighborhood.celebrate();
         this.startFadeIn();
      }
   }

   private void restoreSavedSims() {
      this.currentLot.openHouse(super.canvas.houseUpgrade);
      Sim[] sims = this.getSims();

      for (int index = 0; index < sims.length; index++) {
         Sim sim;
         if ((sim = sims[index]).variant != 0) {
            sim.addBehaviourFlags(32);
         }
      }
   }

   private void setupNewGame() {
      boolean addFlag = false;
      if (this.startOption == 99) {
         this.startOption = 5;
         addFlag = true;
      }

      this.currentLot.openArea(addFlag);
      this.activePlayer.addFlag(1);
   }

   public final Entity[] getEntities() {
      if (this.entitiesDirty) {
         this.entitiesDirty = false;
         if (this.entityCache == null || this.entityCache.length != this.entityStack.size()) {
            this.entityCache = null;
            this.entityCache = new Entity[this.entityStack.size()];
         }

         this.entityStack.copyInto(this.entityCache);
      }

      return this.entityCache;
   }

   public final Sim[] getSims() {
      if (this.simsDirty) {
         this.simsDirty = false;
         Entity[] entities = this.getEntities();
         int simCount = 0;

         for (int index = 0; index < entities.length; index++) {
            if (entities[index].hasFlag(16384)) {
               simCount++;
            }
         }

         Sim[] sims = this.simCache;
         if (this.simCache == null || sims.length != simCount) {
            this.simCache = null;
            sims = new Sim[simCount];
            this.simCache = sims;
         }

         int simIndex = 0;

         for (int secondIndex = 0; secondIndex < entities.length; secondIndex++) {
            if (entities[secondIndex].hasFlag(16384)) {
               sims[simIndex] = (Sim)entities[secondIndex];
               simIndex++;
            }
         }
      }

      return this.simCache;
   }

   public final Entity findEntityByType(int type) {
      Entity[] entities = this.getEntities();
      int start = super.canvas.randomRange(0, entities.length - 1);

      for (int offset = 0; offset < entities.length; offset++) {
         int index = (start + offset) % entities.length;
         Entity entity;
         if ((entity = entities[index]).type == type) {
            return entity;
         }
      }

      return null;
   }

   public final Entity findNearestEntityOfType(int type, int tileX, int tileY) {
      Entity best = null;
      int bestDistance = -1;
      Entity[] entities = this.getEntities();

      for (int index = 0; index < entities.length; index++) {
         Entity entity;
         int distance;
         if ((entity = entities[index]).type == type && ((distance = Math.abs(tileX - entity.posX) + Math.abs(tileY - entity.posY)) < bestDistance || best == null)) {
            bestDistance = distance;
            best = entity;
         }
      }

      return best;
   }

   public final boolean isEntityAtTile(int objectType, int posX, int posY) {
      int tileX = Lot.toTileX(posX);
      int tileY = Lot.toTileX(posY);
      Entity[] entities = this.getEntities();

      for (int index = 0; index < entities.length; index++) {
         Entity entity;
         if ((entity = entities[index]).behaviorClass() == objectType) {
            int entityX = Lot.toTileX(entity.posX);
            int entityY = Lot.toTileY(entity.posY);
            if (entityX == tileX && entityY == tileY) {
               return true;
            }
         }
      }

      return false;
   }

   public final Entity findEntityByObjectType(int objectType) {
      Entity[] entities = this.getEntities();
      int start = super.canvas.randomRange(0, entities.length - 1);

      for (int offset = 0; offset < entities.length; offset++) {
         int index = (start + offset) % entities.length;
         Entity entity;
         if ((entity = entities[index]).behaviorClass() == objectType) {
            return entity;
         }
      }

      return null;
   }

   public final Entity findFreeEntityForFlag(int flags) {
      Lot lot = this.currentLot;
      Entity[] entities = this.getEntities();
      int start = super.canvas.randomRange(0, entities.length - 1);

      for (int offset = 0; offset < entities.length; offset++) {
         int index = (start + offset) % entities.length;
         Entity entity;
         int type = (entity = entities[index]).type;
         if ((lot.linkedIdAt(type) & flags) != 0 && entity.hasFreeSlot() && !entity.hasState(768)) {
            return entity;
         }
      }

      return null;
   }

   public final Sim findSimByIndex(int simIndex) {
      Sim[] sims;
      int count = (sims = this.getSims()).length;

      for (int index = 0; index < count; index++) {
         Sim sim;
         if ((sim = sims[index]).variant == simIndex) {
            return sim;
         }
      }

      return null;
   }

   public final Entity createEntity(int type, int posX, int posY, int facing, int extra) {
      int traits = this.currentLot.objectFlagsAt(type);
      if (this.isSavedNeighborhood() && (type == 2 || type == 3)) {
         traits |= 8192;
      }

      Object entity = null;
      if ((traits & 16384) != 0) {
         entity = new Sim(this);
         if (type == 0) {
            this.activePlayer = (Sim)entity;
         }
      } else if ((traits & 8192) != 0) {
         entity = new Entity(this);
      }

      if (entity != null) {
         ((Entity)entity).place(type, posX, posY, facing, extra);
         this.registerEntity((Entity)entity);
         if (((Entity)entity).hasFlag(16384)) {
            this.queuePortraitLoad((Sim)entity);
         }
      }

      return (Entity)entity;
   }

   public final Entity createEntityNear(int type, Entity near, int facing) {
      int[] hitRect = this.frameBounds;
      int offset = 0;

      do {
         near.computeSlotPosition(hitRect, offset);
         offset++;
      } while (!this.currentLot.isWalkableAt(hitRect[0], hitRect[1]));

      return this.createEntity(type, hitRect[0], hitRect[1], 0, facing);
   }

   public final Entity createEntityAtTile(int type, int pixelX, int pixelY, int facing, int extra) {
      int tileX = Lot.toFixedX(pixelX);
      int tileY = Lot.toFixedY(pixelY);
      return this.createEntity(type, tileX, tileY, facing, extra);
   }

   public final void createRoomObject(int type, int posX, int posY, int param) {
      Entity entity;
      if ((entity = this.createEntity(7, type, posX, 3, -1)) != null) {
         entity.setIdleAnimation(posY, param);
      }
   }

   public final void createObjectWithParam(int type, int posX, int posY) {
      Entity entity;
      if ((entity = this.createEntity(8, type, posX, 3, -1)) != null) {
         entity.showMessage(posY);
      }
   }

   private void registerEntity(Entity entity) {
      entity.updateFootprint(true);
      this.entityStack.addElement(entity);
      this.entitiesDirty = true;
      if (entity.hasFlag(16384)) {
         this.simsDirty = true;
      }
   }

   public final void removeEntity(Entity entity) {
      if (this.followTarget == entity) {
         this.followEntity((Entity) null);
      }

      if (this.menuOpen && this.menuTarget == entity) {
         this.closeInteractionMenu();
      }

      int simIndex = -1;
      if (entity.hasFlag(16384)) {
         simIndex = entity.variant;
      }

      entity.updateFootprint(false);
      this.entityStack.removeElement(entity);
      this.entitiesDirty = true;
      if (simIndex != -1) {
         this.simsDirty = true;
         int slot;
         if ((slot = GameCanvas.indexOfByte(simIndex, this.slotSimIds)) != -1) {
            int recolorFlag = (this.neighborhood.simAttribute(simIndex, 0) == 0 ? 512 : 256) | 16384;
            super.canvas.unloadTiles(recolorFlag, slot);
            this.slotSimIds[slot] = -1;
         }
      }
   }

   private void advanceAllEntities(int delta) {
      Entity[] entities;
      if ((entities = this.getEntities()) != null) {
         for (int index = 0; index < entities.length; index++) {
            Entity entity;
            (entity = entities[index]).advanceTime(delta);
         }
      }
   }

   private void advanceEntitiesOfType(int delta, int type) {
      Entity[] entities;
      if ((entities = this.getEntities()) != null) {
         for (int index = 0; index < entities.length; index++) {
            Entity entity;
            if ((entity = entities[index]).type == type) {
               entity.advanceTime(delta);
            }
         }
      }
   }

   public final void clearBrokenEntities() {
      Entity[] entities = this.getEntities();

      for (int index = 0; index < entities.length; index++) {
         Entity entity;
         if ((entity = entities[index]).hasFlag(4)) {
            entity.despawn();
         }
      }
   }

   public final void openInteractionMenu(Entity entity) {
      Neighborhood neighborhood = this.neighborhood;
      if (this.actionMenuOpen) {
         this.closeActionMenu();
      }

      this.clearMoveFlags();
      short[] labels = this.menuLabels;
      short[] args = this.menuArgs;
      neighborhood.watchSim(-1);
      this.menuOpen = true;
      this.menuSubPage = false;
      this.menuParent = -1;
      this.menuTarget = entity;
      if (screenState == 7) {
         entity.addResourceIndices(labels);
         labels[4] = this.visibleRows;
         GameCanvas.copyList(args, labels);
      } else {
         int slotIndex;
         if ((slotIndex = GameCanvas.indexOfObject(this.menuTarget, this.queuedTargets)) != -1 && this.queuedInteractions[slotIndex] != 2) {
            int moodId;
            if (this.menuTarget.hasFlag(16384)) {
               moodId = this.neighborhood.simNameText(this.menuTarget.variant);
            } else {
               moodId = this.menuTarget.typeAnimId();
            }

            GameCanvas.initList(labels, moodId);
            GameCanvas.initList(args, moodId);
            GameCanvas.addToList(labels, 113);
            GameCanvas.addToList(args, -3);
         } else {
            neighborhood.fillInteractions(this.menuTarget, labels, args);
         }

         GameCanvas.copyList(this.menuExtra, args);
         if (labels[0] > this.visibleRows) {
            neighborhood.fillInventoryMenu(labels, args, this.menuExtra);
         }

         labels[4] = this.visibleRows;
      }

      this.updateInputMasks();
   }

   private void closeInteractionMenu() {
      this.menuOpen = false;
      this.menuSubPage = false;
      this.menuParent = -1;
      this.menuTarget = null;
      this.updateInputMasks();
      this.followEntity(this.followTarget);
   }

   private void drawInteractionPrompt(Graphics gfx) {
      int promptHeight = this.menuTarget != null && this.menuTarget.hasFlag(16384) ? 2 : 4;
      this.drawCaption(gfx, this.menuLabels, promptHeight);
   }

   private int getSelectedSimId() {
      Neighborhood neighborhood = this.neighborhood;
      if (!this.inputIdle && this.menuOpen) {
         if (this.menuTarget != null && this.menuTarget.hasFlag(16384)) {
            return ((Sim)this.menuTarget).variant;
         }
      } else if (neighborhood.isWatchMode()) {
         return neighborhood.watchedSim;
      }

      return -1;
   }

   private void drawSimInfoPanel(Graphics gfx) {
      Neighborhood neighborhood = this.neighborhood;
      int simId = this.getSelectedSimId();
      boolean showAction = !this.menuOpen || this.inputIdle;
      if (!this.inputIdle && this.menuOpen) {
         if (this.menuTarget != null && this.menuTarget.hasFlag(16384)) {
            simId = ((Sim)this.menuTarget).variant;
         }
      } else if (neighborhood.isWatchMode()) {
         showAction = true;
         simId = neighborhood.watchedSim;
      }

      if (simId != -1) {
         GameCanvas canvas = super.canvas;
         int[] hitRect = this.frameBounds;
         neighborhood.collectNeedChanges(simId, hitRect);
         int optionRows = (hitRect[0] != -1 ? 1 : 0) + (hitRect[2] != -1 ? 1 : 0);
         int panelX = this.viewX + 3;
         int panelWidth = canvas.getWidth() >> 1;
         int bubbleHeight = SpriteRenderer.frameHeight(670, 0);
         int panelY = canvas.getHeight() - 15 - this.bottomBarHeight - 18 * (optionRows + 1);
         if (this.notifyId != -1) {
            panelY -= bubbleHeight;
         }

         int rowValue = neighborhood.simState(simId);
         this.drawTwoToneBar(gfx, panelX, panelY, panelWidth, rowValue, 65536, true);
         int rowY = panelY + 18;

         for (byte rowIndex = 0; rowIndex < 4 && (rowValue = hitRect[rowIndex]) != -1; rowIndex += 2) {
            int rowValue2 = hitRect[rowIndex + 1];
            this.drawTwoToneBar(gfx, panelX, rowY, panelWidth, rowValue, rowValue2, false);
            rowY += 18;
         }

         if (showAction) {
            int actionId = -1;
            Sim actionTarget;
            if ((actionTarget = this.activePlayer.partnerSim()) != null && actionTarget.variant == simId) {
               actionId = neighborhood.interactionText(this.activePlayer.actionId);
            }

            if (actionId == 24) {
               actionId = -1;
            }

            int actionY = panelY - 14 - 4 - (actionId != -1 ? 12 : 0);
            int actionWidth = panelWidth - 4;
            SpriteRenderer.setColor(gfx, 76);
            gfx.fillRect(panelX, actionY, panelWidth, 14);
            SpriteRenderer.setColor(gfx, 77);
            gfx.drawRect(panelX, actionY, panelWidth, 14);
            canvas.drawStringById(neighborhood.simNameText(simId), 0, panelX + (panelWidth >> 1), actionY + 0, 17);
            if (actionId != -1) {
               int actionY2 = actionY + 14;
               SpriteRenderer.setColor(gfx, 76);
               gfx.fillRect(panelX, actionY2, panelWidth, 12);
               SpriteRenderer.setColor(gfx, 77);
               gfx.drawRect(panelX, actionY2, panelWidth, 12);
               canvas.drawTextById(gfx, actionId, 1, panelX + 2, actionY2 + 2, actionWidth, 17);
            }
         }
      }
   }

   private void handleInteractionMenuInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 0, 4)) {
         this.resetIdleTimer();
         if (this.menuSubPage) {
            this.neighborhood.fillInventoryMenu(this.menuLabels, this.menuArgs, this.menuExtra);
            this.menuLabels[4] = this.visibleRows;
            this.menuSubPage = false;
            this.menuLabels[2] = this.menuParent;
            GameCanvas.clampListIndex(this.menuLabels);
         } else {
            this.closeInteractionMenu();
         }
      } else {
         if (this.matchesInput(keyState, actionMask, 4128, 16)) {
            int optionIndex = 5 + this.menuLabels[2];
            if (screenState == 7) {
               this.handleMenuAction(this.menuLabels[optionIndex]);
            } else {
               short result;
               if ((result = this.menuArgs[optionIndex]) == -4) {
                  this.closeInteractionMenu();
               } else if (result == -3) {
                  this.cancelQueuedInteraction(this.menuTarget);
                  this.closeInteractionMenu();
               } else if (result == -2) {
                  this.menuParent = this.menuLabels[2];
                  this.menuSubPage = true;
                  short arg = this.menuLabels[optionIndex];
                  this.neighborhood.fillFilteredMenu(this.menuLabels, this.menuArgs, this.menuExtra, arg);
                  this.menuLabels[4] = this.visibleRows;
               } else if (result == 137 || result == 136) {
                  this.handleMenuCommandA();
               } else if (result == 111) {
                  this.handleMenuCommandB();
               } else {
                  if (result != 126) {
                     this.closeOpenMenus();
                     this.queueInteraction(result, this.followTarget, 0, 0);
                     return;
                  }

                  this.handleMenuCommandC();
               }
            }
         } else {
            super.canvas.navigateLeftRight(this.menuLabels, keyState);
         }
      }
   }

   private void setMainMenuPage(int page) {
      this.mainMenuPage = page;
      switch (page) {
         case 0:
         default:
            break;
         case 1:
            this.showInfoPanel(77, 76);
            break;
         case 2:
            this.showInfoPanel(71, 69);
            break;
         case 3:
            this.showInfoPanel(73, 69);
            break;
         case 4:
            this.showInfoPanel(75, 69);
            break;
         case 5:
            this.buildCheatMenu();
      }

      this.updateInputMasks();
   }

   private void openMainMenu() {
      if (this.menuOpen) {
         this.closeInteractionMenu();
      }

      if (this.actionMenuOpen) {
         this.closeActionMenu();
      }

      this.clearMoveFlags();
      this.mainMenuOpen = true;
      this.mainMenuPage = 0;
      this.menuOptionIds[2] = 0;
      this.updateInputMasks();
   }

   private void closeMainMenu() {
      this.mainMenuOpen = false;
      this.updateInputMasks();
   }

   private void handleMainMenuPageInput(int keyState, int actionMask) {
      GameCanvas canvas = super.canvas;
      if (super.canvas.menuMode != -1) {
         canvas.menuKeyPressed(keyState, actionMask);
      } else {
         switch (this.mainMenuPage) {
            case 0:
               if (this.matchesInput(keyState, actionMask, 0, 4)) {
                  this.closeMainMenu();
                  return;
               }

               if (this.matchesInput(keyState, actionMask, 4128, 16)) {
                  int optionIndex = 5 + this.menuOptionIds[2];
                  this.handleMenuAction(this.menuOptionIds[optionIndex]);
                  return;
               }

               canvas.navigateLeftRight(this.menuOptionIds, keyState);
               return;
            case 1:
            case 2:
            case 3:
               if (this.matchesInput(keyState, actionMask, 4128, 256)) {
                  if (this.mainMenuPage != 1) {
                     canvas.saveSettings();
                     canvas.saveGameData();
                  }

                  if (this.mainMenuPage != 2) {
                     this.showDialogAt(1, -1);
                     return;
                  }

                  this.closeMainMenu();
                  return;
               }

               if (this.matchesInput(keyState, actionMask, 0, 516)) {
                  this.setMainMenuPage(0);
                  return;
               }
               break;
            case 4:
               if (this.matchesInput(keyState, actionMask, 4128, 256)) {
                  canvas.saveSettings();
                  canvas.saveGameData();
                  this.queueTick(4);
                  return;
               }

               if (this.matchesInput(keyState, actionMask, 0, 516)) {
                  this.setMainMenuPage(0);
                  return;
               }
               break;
            case 5:
               this.handleCheatMenuInput(keyState, actionMask);
         }
      }
   }

   private void drawMainMenu(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      super.canvas.drawDialogOverlay(gfx);
      int optionId = this.mainMenuPage;
      if (canvas.menuMode != -1) {
         canvas.drawMenu(gfx);
      } else if (optionId == 0) {
         this.drawCaption(gfx, this.menuOptionIds, 1);
      } else if (optionId != 2 && optionId != 3 && optionId != 1 && optionId != 4) {
         if (optionId == 5) {
            this.drawCheatMenu(gfx);
         }
      } else {
         this.drawPageOne(gfx);
      }
   }

   private void openActionMenu() {
      this.tabSwitchRequested = false;
      if (this.menuOpen) {
         this.closeInteractionMenu();
      }

      this.clearMoveFlags();
      short[] options = this.actionOptionIds;
      this.actionMenuOpen = true;
      GameCanvas.initListWith(options, 35, super.canvas.menuRowSpacing);
      if (this.queuedCount > 0) {
         GameCanvas.addToList(options, 113);
      }

      if (this.neighborhood.canInteractAt(null, 0)) {
         GameCanvas.addToList(options, 327);
      }

      if (!this.isNewNeighborhood()) {
         GameCanvas.addToList(options, 328);
      } else {
         GameCanvas.addToList(options, 124);
      }

      if (!this.isNewNeighborhood() && this.currentLot.areaId() == 0) {
         GameCanvas.addToList(options, 114);
      } else {
         GameCanvas.addToList(options, 329);
      }

      GameCanvas.addToList(options, 116);
      GameCanvas.addToList(options, 115);
      GameCanvas.addToList(options, 117);
      GameCanvas.addToList(options, 118);
      GameCanvas.addToList(options, 119);
      GameCanvas.addToList(options, 120);
      GameCanvas.addToList(options, 122);
      if (super.canvas.cheatToggled) {
         GameCanvas.addToList(options, 123);
      }

      this.updateInputMasks();
   }

   private void closeActionMenu() {
      this.actionMenuOpen = false;
      this.updateInputMasks();
   }

   private void handleActionMenuInput(int keyState, int actionMask) {
      GameCanvas canvas = super.canvas;
      if (this.matchesInput(keyState, actionMask, 0, 4)) {
         canvas.releaseSoftKeys();
         this.closeActionMenu();
      } else if (this.matchesInput(keyState, actionMask, 4128, 16)) {
         int optionIndex = this.actionOptionIds[2] + 5;
         this.handleMenuAction(this.actionOptionIds[optionIndex]);
      } else {
         canvas.navigateLeftRight(this.actionOptionIds, keyState);
      }
   }

   private void drawActionMenu(Graphics gfx) {
      super.canvas.drawDialogOverlay(gfx);
      this.drawCaption(gfx, this.actionOptionIds, 1);
   }

   private void openObjectMenu() {
      this.clearCameraTarget();
      this.objectMenuOpen = true;
      short[] options = this.objectOptionIds;
      GameCanvas.initList(this.objectOptionIds, 24);
      int count = this.currentLot.objectCount();

      for (int index = 0; index < count; index++) {
         if (this.neighborhood.hasVisitedItem(index)) {
            GameCanvas.addToList(options, index);
         }
      }

      this.selectObjectOption(this.objectOptionIds[2]);
      this.updateInputMasks();
   }

   private void closeObjectMenu() {
      this.objectMenuOpen = false;
      this.updateInputMasks();
   }

   private void handleObjectMenuInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 0, 4)) {
         this.closeObjectMenu();
      } else if (this.matchesInput(keyState, actionMask, 4128, 16)) {
         this.closeObjectMenu();
         this.useCursorTarget();
      } else if (super.canvas.navigateUpDown(this.objectOptionIds, keyState)) {
         this.selectObjectOption(this.objectOptionIds[2]);
      }

      if (this.matchesInput(keyState, actionMask, 32784, 0)) {
         this.showLeftArrow();
      } else {
         if (this.matchesInput(keyState, actionMask, 65600, 0)) {
            this.showRightArrow();
         }
      }
   }

   private void selectObjectOption(int option) {
      short objectType = this.objectOptionIds[5 + option];
      Entity entity = this.findEntityByType(objectType);
      this.followEntity(entity);
   }

   private void drawObjectMenu(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      int frameHeight = SpriteRenderer.frameHeight(581, 0);
      int centerX = this.viewX + (this.viewWidth >> 1);
      int topY = this.viewY + (frameHeight >> 1);
      int bottomY = this.viewY + this.viewHeight - frameHeight;
      BaseScreen.drawNineSlice(gfx, this.viewX, this.viewY, this.viewWidth, this.viewHeight, 581, -1);
      canvas.drawStringById(124, 0, centerX, topY, 3);
      int optionCount = this.objectOptionIds[2] + 5;
      short objectType = this.objectOptionIds[optionCount];
      int objectId = this.findEntityByType(objectType).interactionAnimId();
      SdkString label = canvas.beginString();
      canvas.appendInt(this.objectOptionIds[2] + 1);
      canvas.appendStringId(29);
      canvas.appendStringId(objectId);
      canvas.drawString(label, 1, centerX, bottomY, 3);
      int arrowHeight = SpriteRenderer.frameHeight(559, 0);
      int arrowY = bottomY - (arrowHeight >> 1);
      int rightX = canvas.getWidth() - 3;
      super.leftArrowAnim.draw(gfx, 3, arrowY);
      super.rightArrowAnim.draw(gfx, rightX, arrowY);
   }

   private void drawMenuList(Graphics gfx, int posX, int posY) {
      GameCanvas canvas = super.canvas;
      SpriteRenderer.drawFrame(gfx, 653, 0, posX, posY);
      int[] hitRect = this.frameBounds;
      SpriteRenderer.hitTest(this.frameBounds, 653, 0, 0);
      SdkString label = canvas.beginString();
      canvas.appendInt(this.objectOptionIds[2] + 1);
      canvas.drawString(label, 0, posX + hitRect[0], posY + hitRect[1], 17);
   }

   private void setMenuPage(int page) {
      Lot lot = this.currentLot;
      switch (page) {
         case 0:
            this.onTick(5);
            break;
         case 1:
            this.followFlaggedEntity(2);
            break;
         case 2:
            if (this.menuPage != 7 && this.menuPage != 8 && this.menuPage != 3 && this.menuPage != 4 && this.menuPage != 6) {
               this.showObjectDetails();
            }
            break;
         case 3:
            this.showInfoPanel(749, 748);
            break;
         case 4:
            if (this.menuPage != 5) {
               this.followFlaggedEntity(4);
               this.followNearestEntity();
            }
            break;
         case 5:
            int objectId = lot.spriteIdAt(this.selectedType);
            this.showInfoPanel(752, objectId);
            break;
         case 6:
            this.keyRepeatTime = 0;
            break;
         case 7:
            int secondValue = lot.spriteIdAt(this.selectedType);
            this.showInfoPanel(750, secondValue);
            break;
         case 8:
            int value = lot.spriteIdAt(this.selectedType);
            this.showInfoPanel(751, value);
            break;
         case 9:
            int textId = this.followTarget.typeAnimId();
            this.showInfoPanel(753, textId);
            break;
         case 10:
            if (lot.stageReward() <= this.neighborhood.money) {
               this.showInfoPanel(772, 771);
            } else {
               this.showInfoPanel(773, 771);
            }
            break;
         case 11:
            this.selectedType = this.followTarget.type;
            this.pendingPlacement = this.followTarget;
            this.pendingPlacement.updateFootprint(false);
            this.pendingPlacement.setBusy();
            lot.spawnWalkers(this.selectedType);
            this.followFlaggedEntity(4);
            this.followNearestEntity();
      }

      this.menuPage = page;
      this.updateInputMasks();
   }

   private void handleMenuPageInput(int keyState, int actionMask) {
      switch (this.menuPage) {
         case 1:
            this.handlePage1Input(keyState, actionMask);
            return;
         case 2:
            this.handleBuyPageInput(keyState, actionMask);
            return;
         case 3:
         case 7:
         case 8:
            if (this.matchesInput(keyState, actionMask, 4128, 12)) {
               this.setMenuPage(2);
               return;
            }
            break;
         case 4:
         case 11:
            this.handlePlaceObjectInput(keyState, actionMask);
            return;
         case 5:
            this.handleBuyObjectInput(keyState, actionMask);
            return;
         case 6:
            return;
         case 9:
            this.handleSellObjectInput(keyState, actionMask);
            return;
         case 10:
            this.handleInventoryInput(keyState, actionMask);
      }
   }

   private void updateMenuPageAnim(int delta) {
      switch (this.menuPage) {
         case 1:
         case 4:
         case 6:
         case 11:
            this.advanceNotification(delta);
            this.updatePlayerAnimator(delta);
            this.updateCameraPan(delta);
            this.advanceEntitiesOfType(delta, 8);
            if (this.menuPage == 6 && this.keyRepeatTime > 1500) {
               this.setMenuPage(2);
               return;
            }
            break;
         case 2:
            this.updatePlayerAnimator(delta);
         case 3:
         case 5:
         case 7:
         case 8:
         case 9:
         case 10:
      }
   }

   private void drawMenuPage(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      switch (this.menuPage) {
         case 1:
         case 4:
         case 6:
         case 11:
            this.drawGameLayer(gfx);
            break;
         case 2:
            this.drawBuyPage(gfx);
            break;
         case 3:
         case 7:
         case 8:
            this.drawBuyPage(gfx);
            canvas.drawDialogOverlay(gfx);
            this.drawPageOne(gfx);
            break;
         case 5:
         case 9:
            int frameHeight;
            short option;
            int jobId;
            if (this.menuPage == 5) {
               frameHeight = this.currentLot.spriteIdAt(this.selectedType);
               option = 709;
               jobId = this.currentLot.objectIdsAt(this.selectedType);
            } else {
               frameHeight = this.currentLot.spriteIdAt(this.followTarget.type);
               option = 710;
               jobId = this.currentLot.altIdAt(this.followTarget.type);
            }

            this.setValue(1, frameHeight);
            this.setValue(2, option);
            SdkString label = canvas.beginString();
            canvas.formatNumber(jobId);
            canvas.setString(-9, label);
            this.setValue(3, -9);
            this.drawGameLayer(gfx);
            canvas.drawDialogOverlay(gfx);
            this.drawPanel(gfx, 20);
            this.drawSoftKeyHint(gfx);
            break;
         case 10:
            this.drawInventoryPage(gfx);
      }

      this.drawSoftKeyHint(gfx);
   }

   private void handlePage1Input(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 0, 4)) {
         this.onTick(5);
      }

      this.handleInputBits(keyState);
      if (this.matchesInput(keyState, actionMask, 4128, 16)) {
         this.useCursorTarget();
      }
   }

   private void handleBuyPageInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 0, 4)) {
         this.onTick(5);
      } else {
         if (this.matchesInput(keyState, actionMask, 4128, 16)) {
            int objectType = super.selectedIndex;
            int objectId = this.currentLot.indexOfItem(objectType);
            this.selectedType = objectId;
            if (!this.currentLot.isStageItemAllowed(objectId)) {
               this.setMenuPage(8);
            } else {
               int price = this.currentLot.objectIdsAt(this.selectedType);
               if (this.neighborhood.money >= price) {
                  if (this.currentLot.spawnWalkers(this.selectedType) > 0) {
                     this.setMenuPage(4);
                  } else {
                     this.setMenuPage(3);
                  }
               } else {
                  this.setMenuPage(7);
               }
            }
         }

         this.scrollList(keyState, actionMask);
      }
   }

   private void drawBuyPage(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      Lot lot = this.currentLot;
      int objectType = this.currentLot.indexOfItem(super.selectedIndex);
      if (!lot.isStageItemAllowed(objectType)) {
         this.setValue(1, 711);
      } else {
         SdkString label = canvas.beginString();
         canvas.formatNumber(lot.objectIdsAt(objectType));
         canvas.setString(-9, label);
         this.setValue(1, -9);
      }

      this.drawPanel(gfx, 23);
      this.drawSoftKeyHint(gfx);
   }

   public final void drawMenuIcon(Graphics gfx, int[] hitRect) {
      int slotWidth;
      int iconWidth = (slotWidth = hitRect[2]) * 3 / 5;
      if (gfx != null) {
         int iconX = hitRect[0] + slotWidth - iconWidth;
         int iconY = hitRect[1];
         int iconHeight = hitRect[3];
         this.drawRaisedBox(gfx, iconX, iconY, iconWidth, iconHeight);
         Lot lot = this.currentLot;
         int objectIndex = this.currentLot.indexOfItem(super.selectedIndex);
         int objectType = lot.subTypeAt(objectIndex);
         int objectId = lot.attachmentAt(objectIndex, 0);
         if (objectType == 196 && objectId != 0 && (objectType = lot.frameDataAt(objectId, 1)) == 196) {
            objectType = lot.frameDataAt(objectId, 2);
         }

         int frameX = SpriteRenderer.frameMinX(objectType, 0);
         int frameY = SpriteRenderer.frameMinY(objectType, 0);
         int frameWidth = SpriteRenderer.frameWidth(objectType, 0);
         int frameHeight = SpriteRenderer.frameHeight(objectType, 0);
         int drawX = iconX + (iconWidth >> 1) - frameX - (frameWidth >> 1);
         int drawY = iconY + (iconHeight >> 1) - frameY - (frameHeight >> 1);
         SpriteRenderer.drawFrame(gfx, objectType, 0, drawX, drawY);
      }

      hitRect[2] = slotWidth - iconWidth - 3;
   }

   private void showObjectDetails() {
      this.beginList(this.currentLot.itemCount(), 15, 0);
   }

   public final void drawObjectListRow(Graphics gfx, int posX, int posY, int width, int height) {
      GameCanvas canvas = super.canvas;
      Lot lot = this.currentLot;
      int iconX = posX + 3;
      int rowY = posY;
      int rowIndex = super.scrollOffset;
      int selectedRow = super.selectedIndex;

      while (rowY < posY + height && rowIndex < super.itemCount) {
         int objectIndex = lot.indexOfItem(rowIndex);
         int objectType = lot.spriteIdAt(objectIndex);
         byte selected = 1;
         if (rowIndex == selectedRow) {
            this.drawClippedRow(gfx, posX, rowY, width, 15, posY + height, 82, rowIndex);
            selected = 0;
         }

         canvas.drawStringById(objectType, selected, iconX, rowY + 7, 6);
         rowY += 15;
         rowIndex++;
         BaseScreen.drawHLine(gfx, posX, rowY, width);
      }
   }

   private void handlePlaceObjectInput(int keyState, int actionMask) {
      this.handleInputBits(keyState);
      Entity placement = this.pendingPlacement;
      if (this.matchesInput(keyState, actionMask, 0, 4)) {
         this.clearBrokenEntities();
         if (this.menuPage == 11) {
            this.clearBrokenEntities();
            placement.updateFootprint(true);
            placement.clearBusy();
            this.pendingPlacement = null;
            this.setMenuPage(1);
         } else {
            this.setMenuPage(2);
         }
      } else {
         if (this.matchesInput(keyState, actionMask, 4128, 2048) && this.followTarget != null) {
            if (this.menuPage == 11) {
               Entity target = this.followTarget;
               if (this.followTarget != null) {
                  placement.clearBusy();
                  placement.relocate(target.posX, target.posY, target.facing);
                  placement.updateFootprint(true);
                  this.currentLot.setObjectPosition(placement.variant, target.posX, target.posY, target.facing);
                  this.followEntity(placement);
                  this.pendingPlacement = null;
                  this.clearBrokenEntities();
                  this.setMenuPage(1);
               }

               return;
            }

            this.setMenuPage(5);
         }
      }
   }

   private void handleBuyObjectInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 4128, 256)) {
         Entity target = this.followTarget;
         if (this.followTarget != null) {
            Neighborhood neighborhood = this.neighborhood;
            Lot lot = this.currentLot;
            int price = this.currentLot.objectIdsAt(this.selectedType);
            this.createObjectWithParam(target.posX, target.posY, price);
            neighborhood.addMoney(-price);
            int extra = lot.addObject(this.selectedType, target.posX, target.posY, target.facing);
            this.clearBrokenEntities();
            Entity newEntity = this.createEntity(this.selectedType, target.posX, target.posY, target.facing, extra);
            this.followEntity(newEntity);
            neighborhood.onItemPlaced(this.selectedType);
            this.setMenuPage(6);
         }
      } else {
         if (this.matchesInput(keyState, actionMask, 0, 516)) {
            this.setMenuPage(4);
         }
      }
   }

   private void handleSellObjectInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 4128, 256)) {
         Entity target = this.followTarget;
         int objectType = this.followTarget.type;
         int[] hitRect = this.frameBounds;
         this.followTarget.computePosition(hitRect);
         this.currentLot.removeObject(this.followTarget.variant);
         this.cancelQueuedInteraction(this.followTarget);
         this.followTarget.despawn();
         this.followEntity((Entity) null);
         this.movingToTarget = true;
         this.camTargetX = hitRect[0];
         this.camTargetY = hitRect[1];
         int price = this.currentLot.altIdAt(objectType);
         this.neighborhood.addMoney(price);
         this.setMenuPage(1);
      } else {
         if (this.matchesInput(keyState, actionMask, 0, 516)) {
            this.setMenuPage(1);
         }
      }
   }

   private void handleInventoryInput(int keyState, int actionMask) {
      int row;
      int objectType;
      int count = (objectType = ((row = super.canvas.leftSoftKeyAction | super.canvas.rightSoftKeyAction) & 8) != 0 ? 4128 : 0) == 0 ? 4128 : 0;
      if (this.matchesInput(keyState, actionMask, objectType, 524)) {
         this.onTick(5);
      }

      if (this.matchesInput(keyState, actionMask, count, 256)) {
         this.neighborhood.addMoney(-this.currentLot.stageReward());
         this.currentLot.nextStage();
         this.setMenuPage(0);
         this.syncFadeWithAction();
      }
   }

   private void drawInventoryPage(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      this.setValue(1, 771);
      this.setValue(2, 776);
      SdkString label = canvas.beginString();
      canvas.formatNumber(this.currentLot.stageReward());
      canvas.setString(-9, label);
      this.setValue(3, -9);
      this.drawGameLayer(gfx);
      canvas.drawDialogOverlay(gfx);
      this.drawPanel(gfx, 20);
      this.drawSoftKeyHint(gfx);
   }

   private void setOptionsTab(int tab) {
      switch (tab) {
         case 0:
            if (this.optionsTab != 9) {
               this.rosterIndex = 0;
            }

            this.showRosterPage();
         case 1:
         case 2:
         default:
            break;
         case 3:
            short textId = 544;
            if (this.neighborhood.pendingAchievementId == -1) {
               textId = 547;
            } else if (!this.neighborhood.hasOpenAchievementSlot()) {
               textId = 545;
            }

            super.canvas.setWrappedString(textId, 1, super.panelHeight);
            this.showSkillsPage();
            break;
         case 4:
            this.showCareerPage();
            break;
         case 5:
            this.showFriendsPage();
            break;
         case 6:
            this.showObjectsPage();
            break;
         case 7:
            this.onTick(5);
            break;
         case 8:
            this.onTick(5);
            if (!this.tabSwitchRequested) {
               this.openActionMenu();
               byte frame = -1;
               switch (this.optionsTab) {
                  case 0:
                     frame = 116;
                     break;
                  case 1:
                     frame = 115;
                     break;
                  case 2:
                     frame = 117;
                     break;
                  case 3:
                     frame = 118;
                     break;
                  case 4:
                     frame = 119;
                     break;
                  case 5:
                     frame = 120;
                     break;
                  case 6:
                     frame = 122;
               }

               if (frame != -1) {
                  GameCanvas.selectListValue(this.actionOptionIds, frame);
               }
            }
            break;
         case 9:
            this.rosterIndex = super.selectedIndex;
            this.refreshOptionsPreview();
            break;
         case 10:
            this.openObjectsDialog();
      }

      this.optionsTab = tab;
      this.updateInputMasks();
   }

   private void refreshOptionsPreview() {
      int simIndex;
      if ((simIndex = this.currentSimId()) != this.shownSimId) {
         this.shownSimId = simIndex;
         GameCanvas canvas = super.canvas;
         int zoom = 98304;
         canvas.unloadTiles(zoom, 1);
         zoom = this.neighborhood.simAttribute(simIndex, 0) == 0 ? '\u8000' : 65536;
         this.currentLot.buildPaletteFor(simIndex, this.recolorSource, this.recolorTarget);
         canvas.loadTiles(zoom, this.recolorSource, this.recolorTarget, 1);
      }
   }

   private void handleOptionsInput(int keyState, int actionMask) {
      Neighborhood neighborhood = this.neighborhood;
      if (this.matchesInput(keyState, actionMask, 3072, 0)) {
         this.onTick(5);
      } else {
         if (this.optionsTab == 9) {
            if (this.matchesInput(keyState, actionMask, 0, 4)) {
               this.setOptionsTab(0);
               return;
            }

            int tabCount = neighborhood.idleSimCount();
            if (!this.matchesInput(keyState, actionMask, 32784, 0)) {
               if (this.matchesInput(keyState, actionMask, 65600, 0)) {
                  this.rosterIndex = (this.rosterIndex + 1) % tabCount;
                  this.showRightArrow();
                  this.refreshOptionsPreview();
               }

               return;
            }

            this.rosterIndex = (this.rosterIndex + tabCount - 1) % tabCount;
            this.showLeftArrow();
            this.refreshOptionsPreview();
         } else {
            if (this.optionsTab == 10) {
               if (this.matchesInput(keyState, actionMask, 0, 4)) {
                  this.setOptionsTab(6);
                  return;
               }

               this.scrollList(keyState, actionMask);
               return;
            }

            if (this.matchesInput(keyState, actionMask, 0, 4)) {
               this.setOptionsTab(8);
               return;
            }

            switch (this.optionsTab) {
               case 0:
                  this.scrollList(keyState, actionMask);
                  if (super.itemCount > 0 && this.matchesInput(keyState, actionMask, 4128, 16)) {
                     this.setOptionsTab(9);
                     return;
                  }
               case 1:
               case 2:
               default:
                  break;
               case 3:
                  this.scrollList(keyState, actionMask);
                  if (neighborhood.pendingAchievementId != -1 && neighborhood.hasOpenAchievementSlot() && this.matchesInput(keyState, actionMask, 4128, 0)) {
                     neighborhood.assignAchievement();
                     super.canvas.setWrappedString(546, 1, super.panelHeight);
                  }

                  if (super.canvas.cheatToggled && this.matchesInput(keyState, actionMask, 1, 0)) {
                     neighborhood.applyCommand(23);
                  }
                  break;
               case 4:
               case 5:
                  this.scrollList(keyState, actionMask);
                  break;
               case 6:
                  if (this.scrollList(keyState, actionMask)) {
                     this.updateInputMasks();
                  }

                  int index;
                  if ((index = this.getSelectedObjectIndex()) != -1 && (this.currentLot.categoryMaskAt(index) & 2048) != 0 && this.matchesInput(keyState, actionMask, 4128, 16)) {
                     this.setOptionsTab(10);
                  }
            }

            if (this.matchesInput(keyState, actionMask, 32784, 0)) {
               this.optionsTab--;
               if (this.optionsTab < 0) {
                  this.optionsTab = 6;
               }

               this.setOptionsTab(this.optionsTab);
               this.showLeftArrow();
               return;
            }

            if (this.matchesInput(keyState, actionMask, 65600, 0)) {
               this.optionsTab++;
               if (this.optionsTab > 6) {
                  this.optionsTab = 0;
               }

               this.setOptionsTab(this.optionsTab);
               this.showRightArrow();
            }
         }
      }
   }

   private void advanceMenuArrows(int delta) {
      this.advanceArrows(delta);
   }

   private void drawOptionsPage(Graphics gfx) {
      switch (this.optionsTab) {
         case 0:
            this.drawPanel(gfx, 14);
            break;
         case 1:
            this.drawPersonDetails(gfx, false);
            break;
         case 2:
            this.drawPanel(gfx, 17);
            break;
         case 3:
            this.drawPanel(gfx, 11);
            break;
         case 4:
            this.drawSkillsPage(gfx);
            break;
         case 5:
            this.drawFriendsPage(gfx);
            break;
         case 6:
            this.drawPanel(gfx, 12);
         case 7:
         case 8:
         default:
            break;
         case 9:
            this.drawSimDetailPage(gfx);
            break;
         case 10:
            this.drawObjectPage(gfx);
      }

      this.drawSoftKeyHint(gfx);
   }

   private void showRosterPage() {
      this.beginList(this.neighborhood.idleSimCount(), 16, this.rosterIndex);
   }

   public final void drawRosterList(Graphics gfx, int posX, int posY, int width, int height) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = super.canvas.neighborhood;
      if (super.itemCount == 0) {
         int emptyX = posX + (width >> 1);
         int emptyY = posY + 3;
         canvas.drawStringById(664, 1, emptyX, emptyY, 17);
      } else {
         if (this.nameColumnWidth == 0) {
            int maxNameWidth = 0;
            int simCount = neighborhood.simCount;

            for (int simIndex = 1; simIndex < simCount; simIndex++) {
               int nameTextId = neighborhood.simNameText(simIndex);
               int textWidth;
               if ((textWidth = canvas.stringWidthById(nameTextId, 1)) > maxNameWidth) {
                  maxNameWidth = textWidth;
               }
            }

            this.nameColumnWidth = maxNameWidth;
         }

         int valueWidth = width - this.nameColumnWidth - 9;
         int valueX = posX + width - valueWidth - 3;
         int nameX = posX + 3;
         int rowY = posY;
         int rowIndex = super.scrollOffset;
         int selectedRow = super.selectedIndex;

         while (rowY < posY + height && rowIndex < super.itemCount) {
            int simId = neighborhood.countSimsInState(rowIndex);
            int nameId = neighborhood.simNameText(simId);
            if (rowIndex == selectedRow) {
               this.drawClippedRow(gfx, posX, rowY, width, 16, posY + height, 82, rowIndex);
            }

            canvas.drawStringById(nameId, 1, nameX, rowY + 5, 20);
            int value = neighborhood.simState(simId);
            int scale = 65536;
            this.drawTwoToneBar(gfx, valueX, rowY + 2, valueWidth, value, scale, true);
            rowY += 16;
            rowIndex++;
            BaseScreen.drawHLine(gfx, posX, rowY, width);
         }
      }
   }

   private void drawPersonDetails(Graphics gfx, boolean useSelection) {
      Neighborhood neighborhood = this.neighborhood;
      int simId = 0;
      int level = 0;
      byte backgroundFrame;
      if (useSelection) {
         backgroundFrame = 8;
         simId = this.friendSimId;
         level = this.friendLevel;
      } else if ((simId = neighborhood.simLot(0)) == -1) {
         backgroundFrame = 5;
      } else {
         backgroundFrame = 6;
         level = neighborhood.simHour(0);
      }

      int labelFrame = 115;
      if (simId == -1) {
         if (useSelection) {
            labelFrame = 628;
         }
      } else {
         GameCanvas canvas = super.canvas;
         if (useSelection) {
            labelFrame = simId == neighborhood.simLot(0) ? 630 : 629;
         }

         this.setValue(1, labelFrame);
         this.setValue(2, neighborhood.lotNameText(simId));
         this.setValue(3, neighborhood.lotHourInteraction(simId, level));
         int value = neighborhood.lotHourIncome(simId, level);
         SdkString label = canvas.beginString();
         canvas.formatNumber(value);
         canvas.setString(-9, label);
         this.setValue(4, -9);
         int traitTextId = neighborhood.lotOccupantType(simId);
         this.setValue(5, this.currentLot.spriteIdAt(traitTextId));
         int traitId = neighborhood.lotKind(simId);
         this.setValue(6, neighborhood.simNameText(traitId));
         int mask = neighborhood.lotHourDays(simId, level);
         label = canvas.beginString();
         SdkString separator = canvas.getString(27);
         int traitCount = 0;

         for (int index = 0; index < Neighborhood.WEEKDAY_BITS.length; index++) {
            if ((mask & Neighborhood.WEEKDAY_BITS[index]) != 0) {
               if (traitCount > 0) {
                  canvas.appendString(separator);
               }

               canvas.appendStringId(Neighborhood.WEEKDAY_TEXT_IDS[index]);
               traitCount++;
            }
         }

         canvas.setString(-8, label);
         this.setValue(7, -8);
         label = canvas.beginString();
         int moodId = neighborhood.lotHourStart(simId, level);
         canvas.appendTime(moodId);
         canvas.appendStringId(662);
         int maskBit = neighborhood.lotHourEnd(simId, level);
         canvas.appendTime(maskBit);
         canvas.setString(-7, label);
         this.setValue(8, -7);
         if (!useSelection) {
            label = canvas.beginString();
            int secondTraitCount = 0;

            for (int secondIndex = 0; secondIndex < Neighborhood.FEATURE_FLAGS.length; secondIndex++) {
               if ((mask & Neighborhood.FEATURE_FLAGS[secondIndex]) != 0) {
                  canvas.appendStringId(Neighborhood.FEATURE_TEXT_IDS[secondIndex]);
                  secondTraitCount++;
               }
            }

            if (secondTraitCount > 0) {
               canvas.setString(-6, label);
               this.setValue(9, -6);
            } else {
               this.setValue(9, 664);
            }
         }
      }

      this.setValue(1, labelFrame);
      this.drawPanel(gfx, backgroundFrame);
   }

   public final int drawSkillRow(Graphics gfx, int posX, int posY, int width, int skillId) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      if (this.listTextWidth == 0) {
         int skillCount = neighborhood.residentCount();
         int maxWidth = 0;

         for (int skillIndex = 0; skillIndex < skillCount; skillIndex++) {
            int textId = neighborhood.residentId(skillIndex);
            int textWidth;
            if ((textWidth = canvas.stringWidthById(textId, 1)) > maxWidth) {
               maxWidth = textWidth;
            }
         }

         this.listTextWidth = maxWidth;
      }

      if (gfx != null) {
         int labelId = neighborhood.residentId(skillId);
         int value = neighborhood.careerPoints(skillId);
         int unitId = neighborhood.residentLevelValue(skillId);
         int barWidth = width - this.listTextWidth - 3;
         int barX = posX + width - barWidth;
         canvas.drawStringById(labelId, 1, posX, posY + 1, 20);
         drawProgressBar(gfx, barX, posY + 1, barWidth, value);
         posY += super.rowHeight;
         canvas.drawStringById(unitId, 1, barX, posY + 1, 20);
      }

      return super.rowHeight << 1;
   }

   private static void drawProgressBar(Graphics gfx, int posX, int posY, int width, int value) {
      int normalized = value - 0 >> 10;
      int innerWidth;
      int filledWidth = (innerWidth = width - 2) * normalized / 320;
      int barHeight = SpriteRenderer.frameHeight(657, 0);
      int innerX = posX + 1;
      int innerY = posY + 1;
      int outerHeight = barHeight + 2;
      SpriteRenderer.setColor(gfx, 77);
      gfx.drawRect(posX, posY, width - 1, outerHeight - 1);
      int savedClipX = gfx.getClipX();
      int savedClipY = gfx.getClipY();
      int savedClipWidth = gfx.getClipWidth();
      int savedClipHeight = gfx.getClipHeight();
      gfx.setClip(innerX, savedClipY, innerWidth, savedClipHeight);
      SpriteRenderer.drawFrame(gfx, 657, 0, innerX, innerY);
      gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
      SpriteRenderer.setColor(gfx, 77);

      for (int tick = 1; tick < 5; tick++) {
         int tickX = posX + width * tick / 5;
         gfx.fillRect(tickX, innerY, 1, barHeight);
      }

      gfx.setClip(innerX, savedClipY, filledWidth, savedClipHeight);
      SpriteRenderer.drawFrame(gfx, 658, 0, innerX, innerY);
      SpriteRenderer.setColor(gfx, 76);

      for (int secondTick = 1; secondTick < 5; secondTick++) {
         int secondTickX = posX + width * secondTick / 5;
         gfx.fillRect(secondTickX, innerY, 1, barHeight);
      }

      gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
   }

   private void showSkillsPage() {
      this.beginList(4, 27, -1);
   }

   public final int drawSkillHeader(Graphics gfx, int posX, int posY, int width) {
      if (gfx != null) {
         int iconX = posX + 10;
         int iconHeight = SpriteRenderer.frameHeight(565, 0);
         int iconY = 13 - (iconHeight >> 1);
         int textWidth = width - 30;
         int textX = posX + width - textWidth;
         Neighborhood neighborhood = this.neighborhood;
         byte jobId = this.neighborhood.pendingAchievementId;
         int labelId = neighborhood.achievementText(jobId);
         short frameId = 589;
         if (jobId == -1) {
            frameId = 196;
         }

         super.canvas.drawSlotText(19, labelId, 1, textWidth, textX, posY + 13 + 2, 6);
         SpriteRenderer.drawFrame(gfx, frameId, 0, iconX, posY + iconY);
      }

      return 30;
   }

   private void drawSkillsPage(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      this.setValue(1, neighborhood.moodTextId());
      int jobId = neighborhood.dayNumber();
      SdkString label = canvas.beginString();
      canvas.appendInt(jobId);
      canvas.setString(-9, label);
      this.setValue(2, -9);
      label = canvas.beginString();
      canvas.formatNumber(neighborhood.moneySpent);
      canvas.setString(-8, label);
      this.setValue(3, -8);
      this.drawPanel(gfx, 10);
   }

   private void showCareerPage() {
      this.beginList(this.neighborhood.needCount, 17, -1);
   }

   public final void drawCareerList(Graphics gfx, int posX, int posY, int width, int height) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = super.canvas.neighborhood;
      int entryCount = super.canvas.neighborhood.needCount;
      if (this.listValueWidth == 0) {
         int maxWidth = 0;

         for (int entryIndex = 0; entryIndex < entryCount; entryIndex++) {
            short textId = neighborhood.needId(entryIndex);
            int textWidth;
            if ((textWidth = canvas.stringWidthById(textId, 1)) > maxWidth) {
               maxWidth = textWidth;
            }
         }

         this.listValueWidth = maxWidth;
      }

      int nameX = posX + 3;
      int valueWidth = width - this.listValueWidth - 9;
      int valueX = posX + width - valueWidth - 3;
      int rowY = posY;
      int rowIndex = super.scrollOffset;

      while (rowY < posY + height && rowIndex < super.itemCount) {
         short nameId = neighborhood.needId(rowIndex);
         canvas.drawStringById(nameId, 1, nameX, rowY + 5, 20);
         int value = neighborhood.needValue(rowIndex);
         this.drawGauge(gfx, valueX, rowY + 5, valueWidth, value);
         rowY += 17;
         rowIndex++;
         BaseScreen.drawHLine(gfx, posX, rowY, width);
      }
   }

   private void showFriendsPage() {
      this.beginList(this.neighborhood.selectedResidentFrame(), 27, -1);
   }

   private void drawFriendsPage(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      SdkString label = super.canvas.profileName();
      canvas.setString(-9, label);
      this.setValue(1, -9);
      short friendCount = this.neighborhood.residentLabelText(this.neighborhood.selectedResident);
      this.setValue(2, friendCount);
      this.drawPanel(gfx, 16);
   }

   private void showObjectsPage() {
      int selectedRow = 0;
      if (this.optionsTab == 10) {
         selectedRow = this.objectRow;
      }

      this.beginList(this.neighborhood.ownedItemCount(), 18, selectedRow);
   }

   private void openObjectsDialog() {
      this.objectRow = super.selectedIndex;
      this.objectIndex = this.currentLot.indexOfRef(this.getSelectedObjectIndex());
      this.beginList(this.currentLot.listSize(this.objectIndex), 18, -1);
   }

   private int getSelectedObjectIndex() {
      return this.neighborhood.itemCountOf(super.selectedIndex);
   }

   public final void drawObjectRows(Graphics gfx, int posX, int posY, int width, int height, int kind) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      Lot lot = this.currentLot;
      int iconX = posX + 3 + 8;
      int iconRight;
      int textX = (iconRight = posX + 3 + 16 + 3) + 3;
      int valueX;
      int dividerX = (valueX = posX + width - 3) - 25;
      SpriteRenderer.setColor(gfx, 75);
      gfx.fillRect(iconRight, posY, 1, height);
      gfx.fillRect(dividerX, posY, 1, height);
      int rowY = posY;
      int rowIndex = super.scrollOffset;
      int selectedRow = super.selectedIndex;

      while (rowY < posY + height && rowIndex < super.itemCount) {
         int objectId;
         if (kind == 5) {
            objectId = neighborhood.itemCountOf(rowIndex);
         } else if (kind == 9) {
            objectId = lot.bitForSound(this.targetObjectType, rowIndex);
         } else {
            objectId = lot.listValueAt(this.objectIndex, rowIndex);
         }

         int iconId = lot.categoryIdAt(objectId);
         int frameId = lot.categoryValueAt(objectId);
         if (rowIndex == selectedRow) {
            this.drawClippedRow(gfx, posX, rowY, width, 18, posY + height, 82, rowIndex);
         }

         SpriteRenderer.drawFrame(gfx, frameId, 0, iconX, rowY + 9);
         canvas.drawStringById(iconId, 1, textX, rowY + 9, 6);
         int value;
         if ((value = neighborhood.itemCount(objectId)) > 0 || kind == 6) {
            SdkString label = canvas.beginString();
            canvas.appendInt(value);
            canvas.appendStringId(27);
            if (kind == 6) {
               canvas.appendInt(1);
            } else {
               canvas.appendInt(lot.categoryFlagAt(objectId));
            }

            canvas.drawString(label, 1, valueX, rowY + 9, 10);
         }

         rowY += 18;
         rowIndex++;
         BaseScreen.drawHLine(gfx, posX, rowY, width);
      }
   }

   private void drawObjectPage(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      Lot lot = this.currentLot;
      SpriteRenderer.setColor(gfx, 77);
      gfx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
      int nextY = this.drawIconRow(gfx, 4, canvas.getWidth() - 8, 122);
      int objectIndex = lot.refAt(this.objectIndex);
      int iconId = lot.categoryIdAt(objectIndex);
      this.setValue(1, iconId);
      this.drawPanelBelow(gfx, 13, nextY);
   }

   public final int currentSimId() {
      return this.neighborhood.countSimsInState(this.rosterIndex);
   }

   private void drawSimDetailPage(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      int simId = this.currentSimId();
      SpriteRenderer.setColor(gfx, 77);
      gfx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
      int nextY = this.drawIconRow(gfx, 4, canvas.getWidth() - 8, 116);
      this.setValue(1, neighborhood.simNameText(simId));
      int partnerId;
      if ((partnerId = neighborhood.simPortrait(simId)) == -1) {
         this.setValue(2, 24);
         this.setValue(3, 24);
      } else {
         this.setValue(2, 645);
         this.setValue(3, neighborhood.simNameText(partnerId));
      }

      int jobId = neighborhood.simLot(simId);
      int jobNameId = neighborhood.lotNameText(jobId);
      this.setValue(4, jobNameId);
      this.drawPanelBelow(gfx, 15, nextY);
   }

   public final int drawFieldRow(Graphics gfx, int posX, int posY, int width) {
      if (gfx != null) {
         Neighborhood neighborhood = this.neighborhood;
         int simId = this.currentSimId();
         int value = neighborhood.simState(simId);
         this.drawTwoToneBar(gfx, posX, posY, width, value, 65536, true);
         int rowY = posY + 15;
         int[] hitRect = this.frameBounds;
         neighborhood.collectNeedChanges(simId, hitRect);

         for (byte index = 0; index < 4 && (value = hitRect[index]) != -1; index += 2) {
            int scale = hitRect[index + 1];
            rowY += 3;
            this.drawTwoToneBar(gfx, posX, rowY, width, value, scale, false);
            rowY += 15;
         }
      }

      return 51;
   }

   public final void drawScrollBar(Graphics gfx, int posX, int posY, int width, int height) {
      int simId = this.currentSimId();
      int barX = posX + width - 20;
      int barY = posY + height - 1;
      this.drawIcon(gfx, barX, barY, simId, true, 1);
   }

   public final void drawScrollHint(Graphics gfx, int posX, int posY, int width, int height) {
      int barX = posX + width - 20;
      int barY = posY + height - 1;
      this.drawIcon(gfx, barX, barY, 0, true, 0);
   }

   private static void drawBorderedRect(Graphics gfx, int posX, int posY, int width, int value, int leftFrame, int rightFrame, int middleFrame, int frameIndex) {
      int savedClipX = gfx.getClipX();
      int savedClipY = gfx.getClipY();
      int savedClipWidth = gfx.getClipWidth();
      int savedClipHeight = gfx.getClipHeight();
      int scaledWidth = FixedMath.mul(width, value);
      int leftWidth = SpriteRenderer.frameWidth(leftFrame, frameIndex);
      int rightWidth = SpriteRenderer.frameWidth(rightFrame, frameIndex);
      int middleWidth = Math.min(width - rightWidth - leftWidth, scaledWidth - leftWidth);
      gfx.setClip(posX, savedClipY, scaledWidth, savedClipHeight);
      SpriteRenderer.drawFrame(gfx, leftFrame, frameIndex, posX, posY);
      SpriteRenderer.drawFrame(gfx, rightFrame, frameIndex, posX + width - rightWidth, posY);
      gfx.setClip(posX + leftWidth, savedClipY, middleWidth, savedClipHeight);
      SpriteRenderer.drawFrame(gfx, middleFrame, frameIndex, posX + leftWidth, posY);
      gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
   }

   private void drawGauge(Graphics gfx, int posX, int posY, int width, int value) {
      drawBorderedRect(gfx, posX, posY, width, 65536, 664, 665, 663, 0);
      int frameIndex = value < 2293760 ? 2 : 1;
      value = FixedMath.div(value, 6553600);
      drawBorderedRect(gfx, posX, posY, width, value, 664, 665, 663, frameIndex);
   }

   private void drawTwoToneBar(Graphics gfx, int posX, int posY, int width, int value, int secondValue, boolean firstTone) {
      width++;
      int mask;
      int frameIndex = ((mask = this.neighborhood.stateFlagsOf(value)) & 2) != 0 ? 1 : ((mask & 1) != 0 ? 3 : 2);
      int firstFrame = firstTone ? frameIndex : 0;
      drawBorderedRect(gfx, posX, posY, width, 65536, 655, 656, 654, firstFrame);
      if (!firstTone) {
         int secondFrame = frameIndex + 3;
         drawBorderedRect(gfx, posX, posY, width, secondValue, 655, 656, 654, secondFrame);
      }

      int iconId = this.neighborhood.stateSprite(value);
      int centerX = posX + (width >> 1);
      int centerY = posY + 0;
      int align = firstTone ? 0 : 1;
      super.canvas.drawStringById(iconId, align, centerX, centerY, 17);
   }

   public final void selectFriend(int simId, int level, boolean silent) {
      this.closeObjectMenu();
      this.friendSimId = simId;
      this.friendLevel = level;
      if (simId == -1) {
         this.showInfoPanel(552, 628);
      } else {
         if (!silent && !this.neighborhood.canEnterLot(simId, level)) {
            this.selectRequest(simId, level);
            return;
         }

         int currentFriendId;
         int frameId = (currentFriendId = this.neighborhood.simLot(0)) == -1 ? 631 : (currentFriendId == simId ? 633 : 632);
         super.canvas.setWrappedString(frameId, 1, super.dialogWidth);
      }

      this.onTick(10);
   }

   private void handleFriendPageInput(int keyState, int actionMask) {
      int primaryMask;
      int secondaryMask = (primaryMask = this.friendSimId == -1 ? 4128 : 0) == 0 ? 4128 : 0;
      if (this.matchesInput(keyState, actionMask, primaryMask, 524)) {
         this.onTick(5);
      } else {
         if (this.matchesInput(keyState, actionMask, secondaryMask, 256)) {
            Neighborhood neighborhood = this.neighborhood;
            int currentFriendId = this.neighborhood.simLot(0);
            int currentLevel = neighborhood.simHour(0);
            int newFriendId = this.friendSimId;
            int newLevel = this.friendLevel;
            neighborhood.selectLocation(newFriendId, newLevel);
            if (currentFriendId != newFriendId && newFriendId != -1) {
               neighborhood.unlockAchievement(20);
            }

            if (currentFriendId == newFriendId && currentLevel < newLevel) {
               neighborhood.unlockAchievement(54);
            }

            if (newLevel == neighborhood.lotHourCount(newFriendId) - 1) {
               neighborhood.unlockAchievement(55);
            }
         }
      }
   }

   private void drawFriendPanel(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      int friendSimId = this.friendSimId;
      if (this.friendSimId != -1) {
         this.drawPersonDetails(gfx, true);
      } else {
         this.drawGameLayer(gfx);
         canvas.drawDialogOverlay(gfx);
         this.drawPageOne(gfx);
      }

      this.drawSoftKeyHint(gfx);
   }

   private void openRequestsDialog() {
      if (super.canvas.randomPercent() < 5) {
         int simIndex = this.neighborhood.currentLotSprite();
         this.openStatsDialog(simIndex);
      } else {
         this.onTick(11);
      }
   }

   private void handleRequestPageInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 4128, 12)) {
         Neighborhood neighborhood = this.neighborhood;
         if (this.neighborhood.canAdvanceHour()) {
            int simId = neighborhood.simLot(0);
            int level = neighborhood.simHour(0);
            this.selectFriend(simId, level + 1, false);
            return;
         }

         if (super.canvas.randomPercent() < 15) {
            int goalId = this.neighborhood.pickRandomCard();
            this.openGoalPage(goalId);
            return;
         }

         this.onTick(5);
      }
   }

   private void drawRequestPage(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      this.setValue(1, 703);
      this.setValue(2, 648);
      SdkString label = canvas.beginString();
      canvas.formatNumber(this.requestAmount);
      canvas.setString(-9, label);
      this.setValue(3, -9);
      this.drawGameLayer(gfx);
      canvas.drawDialogOverlay(gfx);
      this.drawPanel(gfx, 20);
      this.drawSoftKeyHint(gfx);
   }

   public final void selectRequest(int simId, int level) {
      Neighborhood neighborhood = this.neighborhood;
      this.friendSimId = simId;
      this.friendLevel = level;
      int frameId = neighborhood.lotExtraSprite(simId);
      super.canvas.setWrappedString(frameId, 1, super.dialogWidth);
      this.onTick(12);
   }

   private void drawRequestDetails(Graphics gfx) {
      Neighborhood neighborhood = this.neighborhood;
      GameCanvas canvas = super.canvas;
      this.drawGameLayer(gfx);
      super.canvas.drawDialogOverlay(gfx);
      int simId = this.friendSimId;
      int level = this.friendLevel;
      byte[] entries;
      byte firstId = (entries = neighborhood.lotHourRequirement(simId, level))[0];
      byte firstValue = entries[1];
      byte secondId = entries[2];
      byte secondValue = entries[3];
      int nameId = neighborhood.simNameText(neighborhood.lotKind(simId));
      this.setValue(0, nameId);
      this.setValue(1, nameId);
      byte rowCount = 1;
      if (firstId != -1) {
         rowCount = 20;
         this.setValue(2, Neighborhood.statTextId(firstId));
         SdkString label = canvas.beginString();
         canvas.appendInt(neighborhood.statValue(firstId));
         canvas.appendStringId(27);
         canvas.appendInt(firstValue);
         canvas.setString(-9, label);
         this.setValue(3, -9);
      }

      if (secondId != -1) {
         rowCount = 21;
         this.setValue(4, Neighborhood.statTextId(secondId));
         SdkString secondLabel = canvas.beginString();
         canvas.appendInt(neighborhood.statValue(secondId));
         canvas.appendStringId(27);
         canvas.appendInt(secondValue);
         canvas.setString(-8, secondLabel);
         this.setValue(5, -8);
      }

      this.drawPanel(gfx, rowCount);
      this.drawSoftKeyHint(gfx);
   }

   private void handleRequestChoiceInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 4128, 8)) {
         this.onTick(5);
      }
   }

   public final void confirmRequest() {
      this.choiceMade = false;
      Neighborhood neighborhood;
      int textId = (neighborhood = this.neighborhood).lotHourInteraction(neighborhood.simLot(0), neighborhood.simHour(0));
      this.showInfoPanel(705, textId);
      this.onTick(13);
   }

   private void handleRequestCancel(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 4128, 524)) {
         this.onTick(5);
      } else {
         if (this.matchesInput(keyState, actionMask, 0, 256)) {
            this.neighborhood.selectLocation(-1, 0);
            this.choiceMade = true;
            this.updateInputMasks();
            this.showInfoPanel(706, 363);
         }
      }
   }

   private void drawSimplePage(Graphics gfx) {
      this.drawGameLayer(gfx);
      super.canvas.drawDialogOverlay(gfx);
      this.drawPageOne(gfx);
      this.drawSoftKeyHint(gfx);
   }

   public final void requestSleep() {
      this.pendingGoHome = true;
   }

   public final void showRelationshipDialog(int simIndex) {
      this.showNotification(540, this.neighborhood.achievementText(simIndex));
   }

   private void goToSleep() {
      this.closeObjectMenu();
      super.canvas.vibrateShort();
      short frameId = 544;
      if (!this.neighborhood.hasOpenAchievementSlot()) {
         frameId = 545;
      }

      super.canvas.setWrappedString(frameId, 1, super.panelHeight);
      this.onTick(14);
   }

   private void drawRelationshipPage(Graphics gfx) {
      this.drawGameLayer(gfx);
      super.canvas.drawDialogOverlay(gfx);
      this.drawPanel(gfx, 24);
      this.drawSoftKeyHint(gfx);
   }

   private void handleRelationshipInput(int keyState, int actionMask) {
      if (!this.matchesInput(keyState, actionMask, 0, 196)) {
         if (!this.matchesInput(keyState, actionMask, 4128, 8)) {
            return;
         }

         if (this.neighborhood.hasOpenAchievementSlot()) {
            this.neighborhood.assignAchievement();
         }
      }

      this.onTick(5);
   }

   public final void playSlotAnimator(int slot) {
      this.slotAnimators[slot].play(590, 16);
   }

   public final void playSlotAnimator2(int animId, int slot) {
      this.showNotification(541, this.neighborhood.achievementText(animId));
      this.activePlayer.queueAction(9, null, 0, 0);
      if (slot != -1) {
         this.slotAnimators[slot].play(591, 16);
      }
   }

   public final void requestNeighborhoodEvent() {
      this.pendingEvent = true;
   }

   public final void showSimProfile(int simId) {
      this.showNotification(this.neighborhood.simNameText(simId), 537);
   }

   public final void showMessageDialog(int kind, int firstArg, int secondArg, int textId) {
      this.pendingDialogId = kind;
      this.dialogArg1 = firstArg;
      this.dialogArg2 = secondArg;
      if (this.neighborhood.itemCount(kind) == this.currentLot.categoryFlagAt(kind)) {
         textId = 717;
      }

      this.dialogText = textId;
   }

   private void drawMessagePage(Graphics gfx) {
      this.drawGameLayer(gfx);
      super.canvas.drawDialogOverlay(gfx);
      this.drawPanel(gfx, 25);
      this.drawSoftKeyHint(gfx);
   }

   private void handleMessageInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 4128, 8)) {
         this.pendingDialogId = -1;
         this.onTick(5);
      }
   }

   public final int drawObjectIconRow(Graphics gfx, int posX, int posY, int width) {
      if (gfx != null) {
         Lot lot = this.currentLot;
         GameCanvas canvas = super.canvas;
         int objectIndex = this.pendingDialogId;
         int iconX = posX + 3 + 8;
         int iconY = posY + 9;
         int textX = posX + 3 + 16 + 3 + 3;
         int textY = posY + 9;
         int valueX = posX + width - 3;
         int iconId = lot.categoryValueAt(objectIndex);
         SpriteRenderer.drawFrame(gfx, iconId, 0, iconX, iconY);
         SdkString label = canvas.beginString();
         if (this.dialogArg1 != 1) {
            canvas.appendInt(this.dialogArg1);
            canvas.appendStringId(718);
         }

         canvas.appendStringId(lot.categoryIdAt(objectIndex));
         super.canvas.drawString(label, 1, textX, textY, 6);
         label = canvas.beginString();
         canvas.appendInt(this.neighborhood.itemCount(objectIndex));
         canvas.appendStringId(27);
         canvas.appendInt(lot.categoryFlagAt(objectIndex));
         canvas.drawString(label, 1, valueX, textY, 10);
      }

      return 18;
   }

   public final boolean isIdleTooLong() {
      return this.idleTimer > 20000;
   }

   private void resetIdleTimer() {
      this.idleTimer = 0;
      this.activePlayer.clearBehaviourFlags();
   }

   private void updateTownieAi(int delta) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      this.aiTimer += delta;
      short interval = 3000;
      if (neighborhood.paused) {
         interval = 1500;
      }

      if (this.aiTimer > interval) {
         this.aiTimer = 0;
         int houseValue = this.currentLot.areaId();
         int simCount = neighborhood.simCount;

         for (int simIndex = 1; simIndex < simCount; simIndex++) {
            int aiFlags = neighborhood.lotStateNow(simIndex);
            Sim sim;
            if ((sim = this.findSimByIndex(simIndex)) != null) {
               if ((aiFlags & 7) != 0) {
                  sim.addBehaviourFlags(1);
               }

               if ((aiFlags & 16) != 0) {
                  if (houseValue == neighborhood.simHomeLotOf(simIndex)) {
                     sim.addBehaviourFlags(4);
                  } else {
                     sim.addBehaviourFlags(256);
                  }
               }
            } else if ((aiFlags & 8) != 0 && houseValue == neighborhood.simHomeLotOf(simIndex) && this.findFreePortraitSlot() != -1) {
               Entity spawnPoint = this.findEntityByType(4);
               sim = (Sim)this.createEntityNear(1, spawnPoint, simIndex);
               int actionFlags = (canvas.randomPercent() < 50 ? 2 : 0) | (canvas.randomPercent() < 50 ? 8 : 0) | (canvas.randomPercent() < 50 ? 64 : 0) | (canvas.randomPercent() < 50 ? 32 : 0) | (canvas.randomPercent() < 50 ? 16 : 0);
               sim.addBehaviourFlags(actionFlags);
            }
         }

         Sim[] sims = this.getSims();

         for (int index = 0; index < sims.length; index++) {
            int randomIndex = canvas.randomRange(0, sims.length - 1);
            Sim randomSim;
            if ((randomSim = sims[randomIndex]).isIdleModeZero() && randomSim.canOccupySlot() && randomSim.type != 0) {
               int randomActions = (canvas.randomPercent() < 50 ? 2 : 0) | (canvas.randomPercent() < 25 ? 8 : 0) | (canvas.randomPercent() < 50 ? 64 : 0) | (canvas.randomPercent() < 25 ? 16 : 0) | (canvas.randomPercent() < 60 ? 32 : 0);
               if (houseValue != neighborhood.simHomeLotOf(randomSim.variant)) {
                  randomActions &= -19;
               }

               randomSim.addBehaviourFlags(randomActions);
            }
         }
      }

      Sim player = this.activePlayer;
      if (canvas.autonomousEnabled) {
         if (!this.isIdleTooLong()) {
            this.idleTimer += delta;
            return;
         }

         if (player.isIdleModeZero()) {
            int playerActions = neighborhood.moodFlags();
            if (this.currentLot.areaId() != 0 && !neighborhood.hasSimMilestone(0, 1)) {
               playerActions |= 32;
            }

            player.addBehaviourFlags(playerActions);
            return;
         }
      } else {
         player.clearBehaviourFlags();
      }
   }

   private void updateRandomSimAi(int delta) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      this.aiTimer += delta;
      short interval = 5000;
      if (neighborhood.paused) {
         interval = 2500;
      }

      if (this.aiTimer > interval) {
         this.aiTimer = 0;
         Sim[] sims = this.getSims();
         byte chance = 50;
         if (sims.length >= 2) {
            chance = 5;
         }

         boolean spawned = false;
         int simCount = neighborhood.simCount;

         for (int index = 0; index < 4; index++) {
            int simIndex = canvas.randomRange(1, simCount - 1);
            Sim sim = this.findSimByIndex(simIndex);
            int aiFlags = neighborhood.lotStateNow(simIndex);
            if (sim != null) {
               if ((aiFlags & 7) != 0) {
                  sim.addBehaviourFlags(1);
               }

               if ((aiFlags & 16) != 0) {
                  sim.addBehaviourFlags(256);
               }
            } else if ((aiFlags & 8) != 0 && (aiFlags & 16) == 0 && this.findFreePortraitSlot() != -1 && !spawned) {
               spawned = true;
               if (canvas.randomPercent() < chance) {
                  Entity spawnPoint = this.findEntityByType(4);
                  ((Sim)this.createEntityNear(1, spawnPoint, simIndex)).addBehaviourFlags(32);
               }
            }
         }

         for (int index2 = 0; index2 < sims.length; index2++) {
            Sim sim2;
            if ((sim2 = sims[index2]).variant != 0) {
               sim2.addBehaviourFlags(32);
            }
         }
      }
   }

   private void updateNpcSpawning(int delta) {
      this.aiTimer += delta;
      if (this.aiTimer > 5000) {
         this.aiTimer = 0;
         Neighborhood neighborhood = this.neighborhood;
         Lot lot = this.currentLot;
         int npcIndex = super.canvas.randomRange(1, neighborhood.simCount - 1);
         Sim npc;
         if ((npc = this.findSimByIndex(npcIndex)) == null) {
            int houseIndex = neighborhood.simHomeLotOf(npcIndex);
            int objectType = lot.lotIdAt(houseIndex);
            Entity spawnPoint = this.findEntityByType(objectType);
            new StringBuffer().append("can't find an NPC house: ").append(houseIndex).toString();
            npc = (Sim)this.createEntityNear(1, spawnPoint, npcIndex);
         }

         if (npc.isIdleModeZero()) {
            Entity target = this.findRandomBrokenEntity();
            npc.queueAction(106, target);
         }
      }
   }

   private void playRandomSound(int delta, int interval, short[] choices) {
      this.soundTimer += delta;
      if (this.soundTimer > interval) {
         this.soundTimer = 0;
         int index = super.canvas.randomRange(0, choices.length - 1);
         super.canvas.sound.pendingSound = choices[index];
      }
   }

   private Entity findRandomBrokenEntity() {
      Entity[] entities = this.getEntities();
      int index = super.canvas.randomRange(0, entities.length - 1);

      for (int guard = 0; guard < entities.length; guard++) {
         Entity entity;
         if ((entity = entities[index]).hasFlag(2048)) {
            return entity;
         }

         while ((index = (1543 * index + 3571) % 257) >= entities.length) {
         }
      }

      return null;
   }

   public final void assignFreeWillAction(Sim sim) {
      Entity[] entities = this.getEntities();
      Entity best = null;
      Entity fallback = null;

      for (int index = 0; index < entities.length; index++) {
         Entity entity;
         if ((entity = entities[index]).behaviorClass() == 4) {
            if (sim.reserveTarget(entity)) {
               best = entity;
               break;
            }
         } else if (entity.behaviorClass() == 9 && entity.canOccupySlot()) {
            fallback = entity;
         }
      }

      if (best == null) {
         best = fallback;
      }

      if (best != null) {
         sim.sitDown(best);
      } else {
         if (sim.variant == 0) {
            sim.queueAction(6, (Entity) null);
         }
      }
   }

   public final void notifySimsOfAction(int action, Sim source, Entity target) {
      Neighborhood neighborhood = this.neighborhood;
      int houseValue = this.currentLot.areaId();
      Sim[] sims = this.getSims();
      int simCount = neighborhood.interactionFlagsOf(action);
      if (action == 23) {
         neighborhood.addSimMilestoneCount(0, 1);
      }

      int index = sims.length;

      for (int mask = 0; mask < index; mask++) {
         Sim sim;
         int simId;
         if ((simId = (sim = sims[mask]).variant) != 0
            && sim != target
            && sim != source
            && ((simCount & 131072) == 0 || neighborhood.simHomeLotOf(simId) == houseValue)
            && sim.tryInteraction(action, source, target)
            && (simCount & 4194304) != 0) {
            return;
         }
      }
   }

   private void handleMenuAction(int action) {
      switch (action) {
         case 60:
            super.canvas.setMenuMode(1);
            return;
         case 61:
            super.canvas.setMenuMode(3);
            return;
         case 67:
            this.closeMainMenu();
            return;
         case 70:
            this.setMainMenuPage(2);
            return;
         case 72:
            this.setMainMenuPage(3);
            return;
         case 74:
            this.setMainMenuPage(4);
            return;
         case 76:
            this.setMainMenuPage(1);
            return;
         case 113:
            this.closeOpenMenus();
            this.popInteraction();
            return;
         case 114:
            this.closeOpenMenus();
            this.onTick(7);
            this.setMenuPage(1);
            return;
         case 115:
            this.closeOpenMenus();
            this.onTick(8);
            this.setOptionsTab(1);
            return;
         case 116:
            this.closeOpenMenus();
            this.onTick(8);
            this.setOptionsTab(0);
            return;
         case 117:
            this.closeOpenMenus();
            this.onTick(8);
            this.setOptionsTab(2);
            return;
         case 118:
            this.closeOpenMenus();
            this.onTick(8);
            this.setOptionsTab(3);
            return;
         case 119:
            this.closeOpenMenus();
            this.onTick(8);
            this.setOptionsTab(4);
            return;
         case 120:
            this.closeOpenMenus();
            this.onTick(8);
            this.setOptionsTab(5);
            return;
         case 122:
            this.closeOpenMenus();
            this.onTick(8);
            this.setOptionsTab(6);
            return;
         case 123:
            this.openCheatsDialog();
            return;
         case 124:
            this.closeOpenMenus();
            this.openObjectMenu();
            return;
         case 274:
         case 275:
            this.closeOpenMenus();
         default:
            return;
         case 324:
            this.closeOpenMenus();
            this.setMenuPage(9);
            return;
         case 325:
            this.closeOpenMenus();
            this.setMenuPage(11);
            return;
         case 326:
            this.closeOpenMenus();
            this.followTarget.refreshFacing();
            this.currentLot.setObjectFacing(this.followTarget.variant, this.followTarget.facing);
            return;
         case 327:
            if (this.isNewNeighborhood()) {
               this.closeOpenMenus();
               int slot = this.neighborhood.simLot(0);
               int arg = this.neighborhood.lotOccupantType(slot);
               Entity entity = this.findEntityByType(arg);
               this.startInteraction(159, entity, 0, 0);
               return;
            }

            this.startNewGame();
            return;
         case 328:
            this.startTutorialGame();
            return;
         case 329:
            this.continueSavedGame();
      }
   }

   private void resetInteractionQueue() {
      this.queuedCount = 0;
      if (this.queuedInteractions == null) {
         this.queuedInteractions = new int[3];
         this.queuedTargets = new Entity[3];
         this.queuedArgs = new int[3];
         this.queuedArgs2 = new int[3];
      }

      GameCanvas.fillInts(this.queuedInteractions, -1);
      GameCanvas.fillObjects(this.queuedTargets, (Object) null);
   }

   private void promoteNextInteraction() {
      Sim player = this.activePlayer;
      int[] queueIds = this.queuedInteractions;
      Entity[] queueTargets = this.queuedTargets;
      if (this.queuedCount > 0) {
         if (player.isIdleModeZero()) {
            this.queuedCount--;

            for (int index = 0; index < 2; index++) {
               queueIds[index] = queueIds[index + 1];
               queueTargets[index] = queueTargets[index + 1];
               this.queuedArgs[index] = this.queuedArgs[index + 1];
               this.queuedArgs2[index] = this.queuedArgs2[index + 1];
            }

            queueTargets[this.queuedCount] = null;
            if (this.queuedCount > 0) {
               int actionId = queueIds[0];
               Entity target = queueTargets[0];
               int firstArg = this.queuedArgs[0];
               int secondArg = this.queuedArgs2[0];
               this.startInteraction(actionId, target, firstArg, secondArg);
               return;
            }
         } else if (queueIds[0] != player.actionId) {
            int currentGoal = player.actionId;
            if ((this.neighborhood.interactionFlagsOf(currentGoal) & 262144) == 0) {
               queueIds[0] = currentGoal;
               queueTargets[0] = player.actionTarget;
            }

            return;
         }
      } else {
         int goal = player.actionId;
         if ((this.neighborhood.interactionFlagsOf(goal) & 262144) == 0) {
            queueIds[0] = player.actionId;
            queueTargets[0] = player.actionTarget;
            this.queuedCount = 1;
         }
      }
   }

   private void queueInteraction(int action, Entity target, int firstArg, int secondArg) {
      this.resetIdleTimer();
      if (this.activePlayer.isIdleModeZero()) {
         this.startInteraction(action, target, firstArg, secondArg);
      } else {
         int slot;
         if (this.queuedCount == 3) {
            slot = 2;
         } else {
            slot = this.queuedCount++;
         }

         this.queuedInteractions[slot] = action;
         this.queuedTargets[slot] = target;
         this.queuedArgs[slot] = firstArg;
         this.queuedArgs2[slot] = secondArg;
      }
   }

   public final void startInteraction(int action, Entity target, int firstArg, int secondArg) {
      Sim player = this.activePlayer;
      this.activePlayer.startAction(action, target, firstArg, secondArg);
      Sim[] sims = this.getSims();

      for (int index = 0; index < sims.length; index++) {
         Sim sim;
         if ((sim = sims[index]) != player) {
            sim.onBumped(target);
         }
      }
   }

   private void popInteraction() {
      this.resetIdleTimer();
      if (this.queuedCount > 0) {
         this.queuedCount--;
         this.queuedTargets[this.queuedCount] = null;
         if (this.queuedCount == 0) {
            this.activePlayer.cancelAction();
         }
      }
   }

   private void cancelQueuedInteraction(Entity entity) {
      this.resetIdleTimer();
      if (entity != null && this.queuedCount > 0) {
         int slot;
         if ((slot = GameCanvas.indexOfObject(entity, this.queuedTargets)) == 0) {
            this.activePlayer.cancelAction();
            return;
         }

         if (slot > 0) {
            this.queuedCount--;

            for (int index = slot; index < this.queuedCount; index++) {
               this.queuedInteractions[index] = this.queuedInteractions[index + 1];
               this.queuedTargets[index] = this.queuedTargets[index + 1];
               this.queuedArgs[index] = this.queuedArgs[index + 1];
               this.queuedArgs2[index] = this.queuedArgs2[index + 1];
            }

            this.queuedTargets[this.queuedCount] = null;
         }
      }
   }

   private void handleMenuCommandA() {
      int labelIndex = this.menuLabels[2] + 5;
      short action = this.menuArgs[labelIndex];
      short option;
      if ((option = this.menuLabels[labelIndex]) != 309 && option != 308) {
         this.closeOpenMenus();
         int arg = this.neighborhood.simIndexOf(option);
         this.queueInteraction(action, this.followTarget, arg, 0);
      } else {
         this.menuParent = this.menuLabels[2];
         this.menuSubPage = true;
         this.neighborhood.fillResidentMenu(this.menuLabels, this.menuArgs, action);
         this.menuLabels[4] = this.visibleRows;
         if (this.menuLabels[0] == 0) {
            this.closeOpenMenus();
            this.chooseDialogOptionAt(761, 760);
            return;
         }
      }
   }

   public final void handleRelationshipCommand(int kind, int simId) {
      Neighborhood neighborhood = this.neighborhood;
      if (kind == 137) {
         if (this.findSimByIndex(simId) != null) {
            this.chooseDialogOptionAt(762, 309);
         } else if (this.findFreePortraitSlot() != -1 && neighborhood.isSimIdle(simId)) {
            Entity spawnPoint = this.findEntityByType(4);
            ((Sim)this.createEntity(1, spawnPoint.posX, spawnPoint.posY, spawnPoint.facing, simId)).addBehaviourFlags(32);
            this.chooseDialogOptionAt(763, 309);
         } else {
            this.chooseDialogOptionAt(764, 309);
         }
      } else {
         if (kind == 136) {
            if (this.findSimByIndex(simId) != null) {
               this.chooseDialogOptionAt(762, 308);
               return;
            }

            int houseIndex = neighborhood.simState(simId);
            if ((neighborhood.stateFlagsOf(houseIndex) & 16) != 0) {
               neighborhood.adjustNeed(4, 983040);
            }
         }
      }
   }

   private void handleMenuCommandB() {
      int labelIndex = this.menuLabels[2] + 5;
      short action = this.menuArgs[labelIndex];
      short option;
      if ((option = this.menuLabels[labelIndex]) == 284) {
         this.menuParent = this.menuLabels[2];
         this.menuSubPage = true;
         this.neighborhood.buildItemMenu(this.menuLabels, this.menuArgs, action);
         this.menuLabels[4] = this.visibleRows;
         if (this.menuLabels[0] == 0) {
            this.closeOpenMenus();
            this.chooseDialogOptionAt(783, 782);
            return;
         }
      } else {
         this.closeOpenMenus();
         int arg = this.currentLot.indexOfCategoryId(option);
         this.queueInteraction(action, this.followTarget, arg, 0);
      }
   }

   private void handleMenuCommandC() {
      int labelIndex = this.menuLabels[2] + 5;
      short action = this.menuArgs[labelIndex];
      short option;
      if ((option = this.menuLabels[labelIndex]) == 300) {
         this.menuParent = this.menuLabels[2];
         this.menuSubPage = true;
         this.neighborhood.buildUseMenu(this.menuLabels, this.menuArgs, action);
         this.menuLabels[4] = this.visibleRows;
         if (this.menuLabels[0] == 0) {
            this.closeOpenMenus();
            this.chooseDialogOptionAt(781, 780);
            return;
         }
      } else {
         this.closeOpenMenus();
         int arg = this.currentLot.refForCategoryId(option);
         this.queueInteraction(action, this.followTarget, arg, 0);
      }
   }

   public final void openBuildMenu() {
      this.closeOpenMenus();
      this.onTick(7);
      this.setMenuPage(2);
   }

   public final void openBuyMenu() {
      this.closeOpenMenus();
      int objectType;
      if ((objectType = this.currentLot.stageReward()) == -1) {
         this.chooseDialogOptionAt(775, 310);
      } else if (objectType == 0) {
         this.chooseDialogOptionAt(774, 310);
      } else {
         this.onTick(7);
         this.setMenuPage(10);
      }
   }

   private void updateClock(int delta) {
      Neighborhood neighborhood = this.neighborhood;
      if (this.timeAdvanceSeconds != 0) {
         neighborhood.update(this.timeAdvanceSeconds * 1000);
         this.timeAdvanceSeconds = 0;
      } else {
         int timeDelta = delta;
         if (neighborhood.paused) {
            timeDelta = delta << 4;
         }

         neighborhood.update(timeDelta);
      }

      int targetPercent = neighborhood.moodValue;
      this.relationPercent = BaseScreen.approach(this.relationPercent, targetPercent, delta, 8);
      if (!this.activePlayer.isLeavingLot()) {
         int flags;
         if (((flags = neighborhood.lotStateNow(0)) & 1) == 0 || this.hasNotification()) {
            if ((flags & 2) != 0 && !this.hasNotification()) {
               this.showNotification(639, -1);
            }

            return;
         }

         this.showNotification(638, -1);
      } else {
         this.clearNotificationPair();
      }
   }

   private void clearNotificationPair() {
      this.clearNotification(639);
      this.clearNotification(638);
   }

   public final void advanceTimeUntil(int slot, int endSlot) {
      Neighborhood neighborhood = this.neighborhood;
      if (!this.activePlayer.isLeavingLot() && neighborhood.simLot(0) != -1) {
         for (int flags = slot; flags < endSlot; flags++) {
            int nextFlags = neighborhood.lotState(0, flags);
            int index = neighborhood.lotState(0, flags + 1);
            if ((nextFlags & 2) != 0 && (index & 4) != 0) {
               neighborhood.idleTick();
               this.clearNotificationPair();
               if (neighborhood.simLot(0) == -1) {
                  this.openDialog(643, 642, 2);
                  return;
               }

               this.openDialog(641, 640, 2);
               return;
            }
         }
      }
   }

   public final void drawCursorAnimator(Graphics gfx, int posX, int posY) {
      if (screenState != 19) {
         this.cursorAnimator.draw(gfx, posX, posY);
      }
   }

   private void drawNeedArrows(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      int leftX = this.viewX;
      int iconX = this.viewX + 8;
      int rowY = canvas.getHeight() - 15 - this.bottomBarHeight;
      if (this.notifyId != -1) {
         rowY -= SpriteRenderer.frameHeight(670, 0);
      }

      for (int index = 0; index < 6; index++) {
         int needId;
         if ((needId = neighborhood.activeTaskAt(index)) != -1) {
            int labelId = neighborhood.taskId(needId);
            int flags = neighborhood.taskFlagsOf(needId);
            int labelWidth = canvas.stringWidthById(labelId, 1);
            SpriteRenderer.setColor(gfx, 76);
            gfx.fillRect(leftX, rowY - 12, labelWidth + iconX - leftX + 4, 12);
            ((flags & 1) == 0 ? this.needDownAnimator : this.needUpAnimator).draw(gfx, leftX, rowY + -1);
            canvas.drawStringById(labelId, 1, iconX, rowY + 1, 36);
            rowY -= 12;
         }
      }
   }

   private void openGoalPage(int goalId) {
      this.dialogSubject = goalId;
      this.dialogSubjectIndex = -1;
      this.dialogOption = -1;
      this.onTick(18);
      this.setGoalPage(0);
   }

   public final void openSkillGoalPage(int goalId, int extra) {
      this.dialogSubject = goalId;
      this.dialogSubjectIndex = extra;
      this.onTick(18);
      this.setGoalPage(2);
   }

   public final void openCareerGoalPage(int goalId, int extra) {
      this.dialogRequestPending = true;
      this.dialogSubject = goalId;
      this.dialogSubjectIndex = extra;
   }

   private void setGoalPage(int page) {
      Neighborhood neighborhood = this.neighborhood;
      this.dialogPhase = page;
      int subjectId = this.dialogSubject;
      int optionIndex = -1;
      switch (page) {
         case 0:
            optionIndex = neighborhood.cardTimeCost(subjectId);
            break;
         case 1:
            optionIndex = neighborhood.cardOptionText(subjectId, this.dialogOption);
            break;
         case 2:
            optionIndex = neighborhood.cardLabelText(subjectId);
            break;
         case 3:
            optionIndex = neighborhood.cardAltText(subjectId);
            break;
         case 4:
            this.onTick(5);
      }

      if (optionIndex != -1) {
         int frameId = 0;
         if (this.dialogSubjectIndex != -1) {
            frameId = neighborhood.simNameText(this.dialogSubjectIndex);
         } else {
            frameId = 816;
         }

         this.showInfoPanel(optionIndex, frameId);
      }

      this.updateInputMasks();
   }

   private void drawSimpleOverlay(Graphics gfx) {
      this.drawGameLayer(gfx);
      super.canvas.drawDialogOverlay(gfx);
      this.drawPageOne(gfx);
      this.drawSoftKeyHint(gfx);
   }

   private void handleGoalInput(int keyState, int actionMask) {
      Neighborhood neighborhood = this.neighborhood;
      switch (this.dialogPhase) {
         case 0:
            if (this.matchesInput(keyState, actionMask, 4128, 256)) {
               this.dialogOption = neighborhood.visitOptionState(this.dialogSubject, true);
               this.setGoalPage(1);
               return;
            }

            if (this.matchesInput(keyState, actionMask, 0, 516)) {
               this.dialogOption = neighborhood.visitOptionState(this.dialogSubject, false);
               this.setGoalPage(1);
               return;
            }
            break;
         case 1:
            if (this.matchesInput(keyState, actionMask, 4128, 12)) {
               this.setGoalPage(4);
               neighborhood.applyCardDirection(this.dialogSubject, this.dialogOption);
               return;
            }
            break;
         case 2:
            if (this.matchesInput(keyState, actionMask, 4128, 8)) {
               int flags;
               if (((flags = neighborhood.cardFlagsAt(this.dialogSubject)) & 2) != 0) {
                  this.showNeighborhoodIntro((byte)6);
               } else if ((flags & 4) != 0) {
                  this.showNeighborhoodIntro((byte)7);
               }

               this.setGoalPage(4);
               return;
            }
            break;
         case 3:
            if (this.matchesInput(keyState, actionMask, 4128, 8)) {
               neighborhood.finishCard(this.dialogSubject);
               this.setGoalPage(4);
            }
      }
   }

   public final boolean isGoalPageActive() {
      return this.miniGamePhase != 0 && this.miniGamePhase != 1 && this.miniGamePhase != 5;
   }

   public final void openDialogPage(int kind, int arg, int subjectType) {
      GameCanvas canvas = super.canvas;
      ResourceLoader resources = super.canvas.resources;
      Neighborhood neighborhood = this.neighborhood;
      int[] recolorSource = this.recolorSource;
      int[] recolorTarget = this.recolorTarget;
      Lot lot = this.currentLot;
      neighborhood.celebrate();
      super.canvas.vibrateShort();
      this.dialogKind = kind;
      this.onTick(19);
      this.setDialogPhase(1);
      int mask = 0;
      int firstFrame = -1;
      int secondFrame = -1;
      switch (kind) {
         case 0:
            firstFrame = arg;
            mask = 524288;
            break;
         case 1:
            firstFrame = 790;
            mask = 1048576;
            break;
         case 2:
            firstFrame = 789;
            mask = 2097152;
            break;
         case 3:
            firstFrame = 791;
            mask = 4194304;
            break;
         case 4:
            firstFrame = 792;
            secondFrame = arg;
            mask = 8388608;
            break;
         case 5:
            firstFrame = 794;
            mask = 16777216;
            break;
         case 6:
            firstFrame = 795;
            secondFrame = arg;
            mask = 33554432;
            break;
         case 7:
            firstFrame = 797;
            secondFrame = arg;
            mask = 33554432;
            break;
         case 8:
            firstFrame = neighborhood.residentLabelText(neighborhood.selectedResident);
            secondFrame = 799;
            mask = 67108864;
      }

      SpriteRenderer.loadTile(resources, 14);
      SpriteRenderer.loadTile(resources, 13);
      if (mask == 33554432) {
         lot.buildPaletteFor(0, recolorSource, recolorTarget);
         SpriteRenderer.loadTileRecolored(resources, 7, recolorSource, recolorTarget, 0);
         int objectIndex = neighborhood.lotKind(this.friendSimId);
         lot.buildPaletteFor(objectIndex, recolorSource, recolorTarget);
         SpriteRenderer.loadTileRecolored(resources, 8, recolorSource, recolorTarget, 0);
      } else if (mask == 1048576) {
         lot.buildPaletteFor(0, recolorSource, recolorTarget);
         SpriteRenderer.loadTileRecolored(resources, 11, recolorSource, recolorTarget, 0);
         lot.buildPaletteFor(subjectType, recolorSource, recolorTarget);
         SpriteRenderer.loadTileRecolored(resources, 12, recolorSource, recolorTarget, this.findSimByIndex(subjectType).recolorIndex);
      } else if (mask == 8388608) {
         lot.buildPaletteFor(0, recolorSource, recolorTarget);
         SpriteRenderer.loadTileRecolored(resources, 16, recolorSource, recolorTarget, 0);
      } else {
         super.canvas.loadTilesBlocking(mask);
      }

      this.dialogIconTop = firstFrame;
      this.dialogIconBottom = secondFrame;
      this.dialogSubjectType = subjectType;
   }

   private void finishDialog() {
      int configFlags = 133693440;
      if (this.isNewNeighborhood()) {
         configFlags &= -16777217;
      }

      super.canvas.unloadTiles(configFlags, -1);
      switch (this.dialogKind) {
         case 0:
            super.canvas.setPendingScreen(1, -1);
            return;
         case 1:
         case 4:
         default:
            this.onTick(5);
            return;
         case 2:
            this.onTick(5);
            return;
         case 3:
            if (this.neighborhood.canActToday()) {
               this.openStatsDialog(804);
               this.setDialogPhase(2);
               return;
            }

            this.onTick(5);
            return;
         case 5:
            if (this.currentLot.isFinalStage()) {
               this.neighborhood.unlockAchievement(63);
            }

            this.onTick(5);
      }
   }

   private void setDialogPhase(int phase) {
      this.miniGamePhase = phase;
      this.keyRepeatTime = 0;
      switch (phase) {
         case 1:
            this.savedTarget = this.followTarget;
            this.followEntity((Entity)this.activePlayer);
         case 2:
         case 3:
         case 4:
         default:
            break;
         case 5:
            this.followEntity(this.savedTarget);
            this.savedTarget = null;
            this.finishDialog();
      }

      this.updateInputMasks();
   }

   private void updateDialogPhase(int delta) {
      this.dialogClock += delta;
      switch (this.miniGamePhase) {
         case 1:
            this.updateCameraPan(delta);
            if (this.cameraClamped) {
               this.followEntity((Entity) null);
               this.setDialogPhase(2);
               return;
            }
            break;
         case 2:
            if (this.keyRepeatTime > 500) {
               this.setDialogPhase(3);
               return;
            }
         case 3:
         default:
            break;
         case 4:
            if (this.keyRepeatTime > 1000) {
               this.setDialogPhase(5);
            }
      }
   }

   private void handleDialogPhaseInput(int keyState, int actionMask) {
      switch (this.miniGamePhase) {
         case 3:
            if (this.matchesInput(keyState, actionMask, 4128, 8)) {
               this.setDialogPhase(4);
            }
      }
   }

   private void drawDialog(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      switch (this.miniGamePhase) {
         case 1:
            this.drawGameLayer(gfx);
            this.drawSoftKeyHint(gfx);
            break;
         case 2:
         case 3:
         case 4:
            this.drawDialogContent(gfx);
            canvas.drawBottomBar(gfx);
      }

      if (this.miniGamePhase == 2) {
         int alpha = 255 - Math.min(this.keyRepeatTime * 255 / 500, 255);
         int color = 0xFF000000 | alpha << 16 | alpha << 8 | alpha << 0;
         canvas.drawFadeRect(gfx, 65, color, 0, 0, canvas.getWidth(), canvas.getHeight());
      } else {
         if (this.miniGamePhase == 4) {
            int alpha2 = 255 - Math.min(this.keyRepeatTime * 255 / 1000, 255);
            int color2 = 0xFF000000 | alpha2 << 16 | alpha2 << 8 | alpha2 << 0;
            canvas.drawFadeRect(gfx, 66, color2, 0, 0, canvas.getWidth(), canvas.getHeight());
         }
      }
   }

   private void drawDialogContent(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      if (GameCanvas.landscape) {
         SpriteRenderer.drawFrame(gfx, 46, 0, canvas.halfWidth(), canvas.halfHeight() - 7);
      } else {
         SpriteRenderer.drawFrame(gfx, 45, 0, canvas.halfWidth(), canvas.halfHeight() - 7);
      }

      SdkString label = null;
      if (this.dialogSubjectType != -1) {
         label = canvas.getString(this.neighborhood.simNameText(this.dialogSubjectType));
      }

      switch (this.dialogKind) {
         case 0:
            this.drawDialogBox(gfx, 63, canvas.profileName(), canvas.getString(800));
            return;
         case 1:
            this.drawScrollingBanner(gfx, 60, 57);
            this.drawDialogBox(gfx, 68, canvas.profileName(), label);
            this.drawHousePreview(gfx);
            return;
         case 2:
            this.drawScrollingBanner(gfx, 57, 196);
            this.drawDialogBox(gfx, 67, canvas.profileName(), label);
            this.drawRelationshipGraph(gfx);
            return;
         case 3:
            this.drawScrollingBanner(gfx, 57, 62);
            this.drawDialogBox(gfx, 82, canvas.profileName(), label);
            return;
         case 4:
            int subjectType = this.dialogSubjectType;
            int groundFrame = neighborhood.residentBodySprite(subjectType);
            int wallFrame = neighborhood.residentHeadSprite(subjectType);
            int objectFrame = neighborhood.residentColorIndex(subjectType);
            int overlayFrame = neighborhood.simAttribute(0, 0) == 0 ? 81 : 80;
            this.drawScrollingBanner(gfx, groundFrame, wallFrame);
            this.drawDialogBox(gfx, objectFrame, null, null);
            SpriteRenderer.drawFrame(gfx, overlayFrame, 0, canvas.halfWidth(), canvas.halfHeight());
            return;
         case 5:
            this.drawScrollingBanner(gfx, 61, 88);
            this.drawDialogBox(gfx, 66, null, null);
            return;
         case 6:
            int labelId3 = neighborhood.lotSubtitleText(neighborhood.simLot(0));
            this.drawScrollingBanner(gfx, 61, labelId3);
            this.drawDialogBox(gfx, 64, null, null);
            return;
         case 7:
            int labelId2 = neighborhood.lotSubtitleText(neighborhood.simLot(0));
            this.drawScrollingBanner(gfx, 61, labelId2);
            this.drawDialogBox(gfx, 65, null, null);
            return;
         case 8:
            int labelId = neighborhood.selectedResidentSpriteA();
            this.drawScrollingBanner(gfx, 87, labelId);
            this.drawDialogBox(gfx, 75, null, null);
      }
   }

   private void drawDialogBox(Graphics gfx, int frameId, SdkString topLabel, SdkString bottomLabel) {
      GameCanvas canvas = super.canvas;
      int screenWidth = super.canvas.getWidth();
      int screenHeight = canvas.getHeight();
      int kind = this.dialogKind;
      int offsetY = 0;
      if (this.dialogKind == 0) {
         offsetY = screenHeight / 10;
      }

      int centerX = screenWidth >> 1;
      int centerY = (screenHeight >> 1) + offsetY;
      SpriteRenderer.drawFrame(gfx, frameId, 0, centerX, centerY);
      int[] hitRect = super.hitRect;
      if (topLabel != null) {
         SpriteRenderer.hitTest(hitRect, frameId, 0, 0);
         canvas.drawString(topLabel, 1, centerX + hitRect[0], centerY + 1 + hitRect[1] - 13, 17);
      }

      if (bottomLabel != null) {
         SpriteRenderer.hitTest(hitRect, frameId, 0, 1);
         canvas.drawString(bottomLabel, 1, centerX + hitRect[0], centerY + 1 + hitRect[1] + 13, 17);
      }

      if (kind == 8) {
         SpriteRenderer.hitTest(hitRect, frameId, 0, 0);
         this.drawIcon(gfx, centerX + hitRect[0], centerY + hitRect[1], 0, true, 0);
      } else if (kind == 5) {
         int iconFrame = this.currentLot.stageMusicId();
         SpriteRenderer.hitTest(hitRect, iconFrame, 0, 1);
         SpriteRenderer.drawFrame(gfx, iconFrame, 0, centerX - hitRect[0], centerY - hitRect[1]);
      }

      if (this.miniGamePhase == 3) {
         if (this.dialogIconTop != -1) {
            int iconHeight = SpriteRenderer.frameHeight(frameId, 0);
            int halfCenterY;
            int topIconY = (halfCenterY = centerY - (iconHeight >> 1)) >> 1;
            if (kind != 3 && kind != 7) {
               canvas.drawSlotText(0, this.dialogIconTop, 0, super.dialogWidth, centerX, topIconY, 3);
            } else {
               canvas.drawScrollingText(0, this.dialogIconTop, 0, centerX, topIconY, 3);
            }
         }

         if (this.dialogIconBottom != -1) {
            int bottomGap = screenHeight - 15 - (centerY + (SpriteRenderer.frameHeight(frameId, 0) >> 1));
            int bottomIconY = screenHeight - 15 - (bottomGap >> 1);
            canvas.drawSlotText(0, this.dialogIconBottom, 0, super.dialogWidth, centerX, bottomIconY, 3);
         }
      }
   }

   private void drawScrollingBanner(Graphics gfx, int frameA, int frameB) {
      GameCanvas canvas = super.canvas;
      int bottomY = super.canvas.getHeight() - 15;
      int screenWidth;
      int leftX = (screenWidth = canvas.getWidth()) / 3 >> 1;
      int rightX = screenWidth - leftX;
      int waveAmplitude = screenWidth * 15 / 100;
      int bannerHeight = Math.max(SpriteRenderer.frameHeight(frameA, 0), SpriteRenderer.frameHeight(frameB, 0));
      int maxY = bottomY + bannerHeight;
      int scrollOffset = -(this.dialogClock * 850 % 5898240);
      int frameId = frameA;
      if (scrollOffset < -2949120) {
         scrollOffset += 2949120;
         frameId = frameB;
      }

      int wavePhase = this.dialogClock * 125 % 411775 - FixedMath.div(FixedMath.mul(164709, -scrollOffset), 2949120);
      int rowY = scrollOffset >> 16;
      int phase = wavePhase;

      for (int currentFrame = frameId; rowY < maxY; currentFrame = currentFrame == frameA ? frameB : frameA) {
         int waveOffset = FixedMath.mul(waveAmplitude, FixedMath.cos(phase));
         if (currentFrame == frameB && this.dialogKind == 7) {
            SpriteRenderer.drawFrame(gfx, 91, 0, leftX + waveOffset, rowY + -15);
         }

         SpriteRenderer.drawFrame(gfx, currentFrame, 0, leftX + waveOffset, rowY);
         int secondPhase = phase + 82354;
         waveOffset = FixedMath.mul(waveAmplitude, FixedMath.cos(secondPhase));
         int secondRowY = rowY + 22;
         if (currentFrame == frameB && this.dialogKind == 7) {
            SpriteRenderer.drawFrame(gfx, 91, 0, rightX + waveOffset, secondRowY + -15);
         }

         SpriteRenderer.drawFrame(gfx, currentFrame, 0, rightX + waveOffset, secondRowY);
         rowY += 45;
         phase += 164709;
      }
   }

   private void drawHousePreview(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      int subjectType = this.dialogSubjectType;
      byte groundFrame;
      short groundFrame2;
      byte wallFrame;
      short wallFrame2;
      if (neighborhood.simAttribute(0, 0) == 0) {
         groundFrame = 74;
         groundFrame2 = 489;
         if (neighborhood.simAttribute(subjectType, 0) == 0) {
            wallFrame = 71;
            wallFrame2 = 489;
         } else {
            wallFrame = 69;
            wallFrame2 = 382;
         }
      } else {
         if (neighborhood.simAttribute(subjectType, 0) == 0) {
            groundFrame = 72;
            wallFrame = 70;
            wallFrame2 = 489;
         } else {
            groundFrame = 73;
            wallFrame = 69;
            wallFrame2 = 382;
         }

         groundFrame2 = 382;
      }

      int labelId = neighborhood.simAttribute(0, 2) + 1;
      int rowCount = neighborhood.simAttribute(subjectType, 2) + 1;
      SpriteRenderer.setRecolorIndex(0);
      SpriteRenderer.drawParts(gfx, groundFrame, 0, canvas.halfWidth(), canvas.halfHeight(), groundFrame2, labelId, null);
      SpriteRenderer.setRecolorIndex(this.findSimByIndex(subjectType).recolorIndex);
      SpriteRenderer.drawParts(gfx, wallFrame, 0, canvas.halfWidth(), canvas.halfHeight(), wallFrame2, rowCount, null);
      SpriteRenderer.setRecolorIndex(0);
   }

   private void drawRelationshipGraph(Graphics gfx) {
      Lot lot = this.currentLot;
      Sim player = this.activePlayer;
      Sim other = this.findSimByIndex(this.dialogSubjectType);
      int[] hitRect = super.hitRect;
      int playerX = player.posX;
      int playerY = player.posY;
      int otherX = other.posX;
      int otherY = other.posY;
      int centerX = lot.toScreenFixedX(playerX, playerY) + lot.toScreenFixedX(otherX, otherY) >> 1;
      int centerY = lot.toScreenFixedY(playerX, playerY) + lot.toScreenFixedY(otherX, otherY) >> 1;
      SpriteRenderer.hitTest(hitRect, 67, 0, 2);
      centerX += hitRect[0] << 16;
      centerY -= hitRect[1] << 17;
      this.currentLot.drawEntityPair(gfx, player, other, centerX, centerY);
   }

   public static boolean isDialogOpen() {
      return screenState == 16;
   }

   public final void openMiniGame(int kind, Entity target) {
      this.closeOpenMenus();
      this.neighborhood.celebrate();
      this.miniTarget = target;
      this.miniKind = kind;
      this.resetEffectAnimators();
      byte duration = 0;
      this.progressValue = 0;
      switch (kind) {
         case 0:
            duration = 20;
            this.setupCandleGame();
            break;
         case 1:
            duration = 20;
            this.setupSliderGame();
            break;
         case 2:
            duration = 20;
            this.setupSeedGame();
            break;
         case 3:
            duration = 20;
            this.setupVehicleGame();
      }

      this.timeAdvanceSeconds = duration;
      this.onTick(16);
      this.setMiniGameStep(0);
   }

   private void setMiniGameStep(int step) {
      Neighborhood neighborhood;
      boolean playSound;
      Lot lot = this.currentLot;
      neighborhood = this.neighborhood;
      this.miniStep = step;
      playSound = false;
      label85:
      switch (step) {
         case 0:
            if (this.miniKind == 1) {
               int posX = this.activePlayer.posX;
               int posY = this.activePlayer.posY;
               int targetX = lot.toScreenX(posX, posY);
               int targetY = lot.toScreenY(posX, posY);
               if (this.miniTarget.type == 5) {
                  targetX += 50;
               } else {
                  targetX -= 10;
               }

               this.followTarget = null;
               this.camTargetX = targetX << 16;
               this.camX = this.camTargetX;
               this.camTargetY = targetY << 16;
               this.camY = this.camTargetY;
            } else {
               this.followEntity((Entity)this.activePlayer);
            }
            break;
         case 1:
            switch (this.miniKind) {
               case 0:
                  this.showNeighborhoodIntro((byte)2);
                  break label85;
               case 1:
                  this.showNeighborhoodIntro((byte)3);
                  break label85;
               case 2:
                  this.showNeighborhoodIntro((byte)4);
                  break label85;
               case 3:
                  this.showNeighborhoodIntro((byte)5);
               default:
                  break label85;
            }
         case 2:
            this.followEntity(this.miniTarget);
            this.onTick(5);
            this.raiseSkillLevel(this.miniType);
            switch (this.miniKind) {
               case 0:
                  neighborhood.unlockAchievement(28);
                  int skillId = neighborhood.lastPurchasedItem;
                  if (neighborhood.lastPurchasedItem == 0) {
                     neighborhood.unlockAchievement(29);
                  } else if (skillId == 9) {
                     neighborhood.unlockAchievement(31);
                  } else if (skillId == 6) {
                     neighborhood.unlockAchievement(30);
                  }
                  break;
               case 1:
                  this.miniTarget.clearBusy();
                  neighborhood.unlockAchievement(22);
                  if (this.miniLevel == 32) {
                     neighborhood.unlockAchievement(23);
                  } else if (this.miniLevel == 33) {
                     neighborhood.unlockAchievement(24);
                  } else if (this.miniLevel == 30) {
                     neighborhood.unlockAchievement(25);
                  }

                  neighborhood.addItem(this.miniLevel, 1);
                  this.showMessageDialog(this.miniLevel, 1, 335, 716);
                  break;
               case 2:
                  neighborhood.unlockAchievement(26);
                  this.miniTarget.turnOffPrimary();
                  break;
               case 3:
                  this.miniTarget.turnOffSecondary();
            }

            playSound = true;
            break;
         case 3:
            if (this.miniKind == 1) {
               this.miniTarget.clearBusy();
            }

            this.followEntity(this.miniTarget);
            if (this.miniKind == 2 && super.canvas.randomPercent() < 5) {
               this.openStatsDialog(805);
            } else {
               this.activePlayer.cancelAction();
               this.activePlayer.queueAction(8, null, 0, 0);
               this.chooseDialogOptionAt(755, 754);
               playSound = true;
            }
            break;
         case 4:
            if (this.miniKind == 1) {
               this.miniTarget.clearBusy();
            }

            this.timeAdvanceSeconds = 0;
            this.followEntity(this.miniTarget);
            this.activePlayer.cancelAction();
            this.chooseDialogOptionAt(756, 754);
            playSound = true;
            break;
         case 5:
            this.keyRepeatTime = 0;
            this.effectAnimators[0].play(634, 4);
         case 6:
         default:
            break;
         case 7:
            Sim player = this.activePlayer;
            this.activePlayer.clearState(128);
            player.setPose(22);
            break;
         case 8:
            Sim playerSim = this.activePlayer;
            this.activePlayer.clearState(128);
            int animId = this.isSliderComplete() ? 26 : 23;
            playerSim.setPose(animId);
            break;
         case 9:
            this.keyRepeatTime = 0;
            this.effectAnimators[0].play(634, 4);
      }

      if (playSound) {
         switch (this.miniKind) {
            case 0:
               neighborhood.unlockAchievement(1);
               return;
            case 1:
               neighborhood.unlockAchievement(0);
         }
      }
   }

   public final void raiseSkillLevel(int skillId) {
      Neighborhood neighborhood = this.neighborhood;
      int oldLevel = this.neighborhood.residentLevel(skillId);
      neighborhood.addCareerPoints(skillId);
      int newLevel = neighborhood.residentLevel(skillId);
      int frameId = 757;
      if (oldLevel != newLevel) {
         frameId = newLevel == 5 ? 759 : 758;
         switch (skillId) {
            case 0:
               neighborhood.unlockAchievement(7);
               if (newLevel == 5) {
                  neighborhood.unlockAchievement(73);
               }
               break;
            case 1:
               neighborhood.unlockAchievement(9);
               if (newLevel == 5) {
                  neighborhood.unlockAchievement(74);
               }
               break;
            case 2:
               neighborhood.unlockAchievement(8);
               if (newLevel == 5) {
                  neighborhood.unlockAchievement(71);
               }
               break;
            case 3:
               neighborhood.unlockAchievement(10);
               if (newLevel == 5) {
                  neighborhood.unlockAchievement(72);
               }
            case 4:
         }
      }

      if (oldLevel != newLevel && newLevel == 5) {
         super.canvas.formatString1(-11, 793, neighborhood.residentId(skillId));
         this.openDialogPage(4, -11, skillId);
      } else {
         if (oldLevel != 5) {
            int textId = neighborhood.residentId(skillId);
            this.chooseDialogOptionAt(frameId, textId);
         }
      }
   }

   private void drawMiniGame(Graphics gfx) {
      this.currentLot.draw(gfx);
      if (!this.mainMenuOpen && this.miniKind != 1) {
         super.canvas.drawDialogOverlay(gfx);
      }

      switch (this.miniKind) {
         case 0:
            this.drawMiniGameProgress(gfx);
            this.drawCandles(gfx);
            break;
         case 1:
            this.drawMiniGameTop(gfx);
            this.drawSliderGame(gfx);
            break;
         case 2:
            this.drawMiniGameProgress(gfx);
            this.drawSeedGame(gfx);
            break;
         case 3:
            this.drawMiniGameProgress(gfx);
            this.drawVehicleGame(gfx);
      }

      if (this.dialogActive) {
         this.drawPageTwo(gfx);
      }

      if (this.mainMenuOpen) {
         this.drawMainMenu(gfx);
      }

      this.drawSoftKeyHint(gfx);
   }

   private void drawMiniGameProgress(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      SpriteRenderer.drawFrame(gfx, 568, 0, 0, 34);
      int bottomBarHeight = SpriteRenderer.frameHeight(586, 0);
      int barY = canvas.getHeight() - 15 - 34 - bottomBarHeight;
      SpriteRenderer.drawFrame(gfx, 569, 0, 0, barY);
      this.panelTop = 34;
      this.panelBottom = barY;
      this.drawRelationshipBar(gfx);
      int savedClipX = gfx.getClipX();
      int savedClipY = gfx.getClipY();
      int savedClipWidth = gfx.getClipWidth();
      int savedClipHeight = gfx.getClipHeight();
      int centerX = canvas.getWidth() >> 1;
      int progressHeight = SpriteRenderer.frameHeight(634, 0);
      int progressY = 17 - progressHeight;
      int progressWidth = canvas.getWidth() - 40;
      int progressX = centerX - (progressWidth >> 1);
      gfx.setClip(progressX, savedClipY, progressWidth, savedClipHeight);
      SpriteRenderer.drawFrame(gfx, 633, 0, progressX, progressY);
      int filledWidth = this.progressValue * progressWidth / this.progressMax;
      gfx.setClip(progressX, savedClipY, filledWidth, savedClipHeight);
      if (this.miniStep == 5) {
         SpriteRenderer.drawAnimator(gfx, this.effectAnimators[0], progressX, progressY);
      } else {
         SpriteRenderer.drawFrame(gfx, 634, 0, progressX, progressY);
      }

      gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
   }

   private void drawMiniGameTop(Graphics gfx) {
      SpriteRenderer.drawFrame(gfx, 568, 0, 0, 14);
      int posX = SpriteRenderer.frameHeight(586, 0);
      int posY = super.canvas.getHeight() - 15 - 64 - posX;
      SpriteRenderer.drawFrame(gfx, 569, 0, 0, posY);
      this.panelTop = 14;
      this.panelBottom = posY;
      this.drawRelationshipBar(gfx);
   }

   private void handleMiniGameInput(int keyState, int actionMask) {
      if (this.matchesInput(keyState, actionMask, 0, 5)) {
         this.openMainMenu();
      } else if (this.matchesInput(keyState, actionMask, 0, 64)) {
         this.setMiniGameStep(4);
      } else {
         if (this.miniStep == 1) {
            switch (this.miniKind) {
               case 0:
                  this.handleCandleInput(keyState, actionMask);
                  return;
               case 1:
                  this.handleSliderInput(keyState, actionMask);
                  return;
               case 2:
                  this.handleSeedInput(keyState, actionMask);
                  return;
               case 3:
                  this.handleVehicleInput(keyState, actionMask);
            }
         }
      }
   }

   private void updateMiniGame(int delta) {
      if (!this.mainMenuOpen && !this.dialogActive) {
         this.activePlayer.advanceTime(delta);
         this.miniTarget.advanceTime(delta);
         this.advanceEntitiesOfType(delta, 7);
         if (this.miniStep == 0) {
            this.updateCameraPan(delta);
            if (this.cameraClamped) {
               this.followEntity((Entity) null);
               int nextStep = this.miniKind == 1 ? 7 : 1;
               this.setMiniGameStep(nextStep);
               return;
            }
         } else {
            switch (this.miniKind) {
               case 0:
                  this.updateCandleGame(delta);
                  return;
               case 1:
                  this.updateSliderGame(delta);
                  return;
               case 2:
                  this.updateSeedGame(delta);
                  return;
               case 3:
                  this.updateVehicleGame(delta);
            }
         }
      }
   }

   private void resetEffectAnimators() {
      for (int slot = 0; slot < 24; slot++) {
         this.effectAnimators[slot].setRunning(false);
         GameCanvas.fillInts(this.effectPositions[slot], -1);
      }
   }

   private int playEffect(int animId, int posX, int posY, int frameRate) {
      int slot = -1;

      for (int index = 0; index < 24; index++) {
         if (!this.effectAnimators[index].isRunning()) {
            slot = index;
            break;
         }
      }

      if (slot != -1) {
         this.effectAnimators[slot].play(animId, frameRate);
         this.effectPositions[slot][0] = posX;
         this.effectPositions[slot][1] = posY;
         this.effectPositions[slot][2] = -1;
      }

      return slot;
   }

   private void drawEffectsFrom(Graphics gfx, int startSlot) {
      this.drawEffects(gfx, startSlot, 0, 0);
   }

   private void drawEffects(Graphics gfx, int startSlot, int offsetX, int offsetY) {
      for (int slot = startSlot; slot < 24; slot++) {
         Animator animator;
         if ((animator = this.effectAnimators[slot]).isRunning()) {
            int[] position = this.effectPositions[slot];
            animator.draw(gfx, position[0] + offsetX, position[1] + offsetY);
         }
      }
   }

   private void drawEffectsScaled(Graphics gfx, int startSlot, int offsetX, int offsetY) {
      for (int slot = startSlot; slot < 24; slot++) {
         Animator animator;
         if ((animator = this.effectAnimators[slot]).isRunning()) {
            int[] position = this.effectPositions[slot];
            animator.draw(gfx, (position[0] >> 16) + offsetX, (position[1] >> 16) + offsetY);
         }
      }
   }

   private void advanceEffects(int delta, int startSlot) {
      for (int slot = startSlot; slot < 24; slot++) {
         this.effectAnimators[slot].advance(delta);
      }
   }

   private static void fillTiled(Graphics gfx, int posX, int posY, int width, int height, int frameId) {
      int savedClipX = gfx.getClipX();
      int savedClipY = gfx.getClipY();
      int savedClipWidth = gfx.getClipWidth();
      int savedClipHeight = gfx.getClipHeight();
      gfx.setClip(posX, posY, width, height);
      int tileWidth = SpriteRenderer.frameWidth(frameId, 0);
      int tileHeight = SpriteRenderer.frameHeight(frameId, 0);

      for (int row = 0; row < height; row += tileHeight) {
         for (int column = 0; column < width; column += tileWidth) {
            SpriteRenderer.drawFrame(gfx, frameId, 0, posX + column, posY + row);
         }
      }

      gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
   }

   private void setupCandleGame() {
      this.miniType = 0;
      int skillLevel = this.neighborhood.residentLevel(0);
      short riseSpeed = 655;
      byte columnCount = 2;
      char duration;
      switch (skillLevel) {
         case 0:
         default:
            duration = 20000;
            break;
         case 1:
            duration = 30000;
            riseSpeed = 983;
            break;
         case 2:
            columnCount = 3;
            duration = 30000;
            break;
         case 3:
            columnCount = 3;
            duration = 30000;
            riseSpeed = 983;
            break;
         case 4:
         case 5:
            columnCount = 4;
            duration = '\u9c40';
      }

      this.candleColumns = columnCount;
      this.progressMax = duration;
      this.candleRiseSpeed = riseSpeed;
      this.candleDecrement = 1638400;
      this.plotIndex = 0;
      this.progressValue = 0;
      GameCanvas canvas = super.canvas;

      for (int column = 0; column < this.candleColumns; column++) {
         int candleValue = canvas.randomRange(1638400, 4915200);
         this.candleAnimValues[column] = candleValue;
         this.candleTargets[column] = candleValue;
         this.effectAnimators[this.candleColumns + column].play(612, 20);
         this.candleFrames[column] = (byte)canvas.randomRange(0, 3);
      }
   }

   private void drawCandles(Graphics gfx) {
      int savedClipX = gfx.getClipX();
      int savedClipY = gfx.getClipY();
      int savedClipWidth = gfx.getClipWidth();
      int savedClipHeight = gfx.getClipHeight();
      int centerX = this.viewX + (this.viewWidth >> 1);
      int columnWidth = SpriteRenderer.frameWidth(610, 0);
      int columnHeight = SpriteRenderer.frameHeight(611, 0);
      int columnX = centerX - (columnWidth * this.candleColumns >> 1) + (columnWidth >> 1);
      int baseY = this.panelTop + this.panelBottom + columnHeight >> 1;
      int candleHeight = SpriteRenderer.frameHeight(612, 0);
      int[] hitRect = this.frameBounds;
      int posX = columnX;

      for (int column = 0; column < this.candleColumns; column++) {
         short baseFrame = 611;
         int animId = this.effectAnimators[this.candleColumns + column].animId;
         int frame = this.effectAnimators[this.candleColumns + column].frame;
         if (this.miniStep == 5) {
            animId = 613;
            frame = 0;
         }

         if (column == this.plotIndex) {
            baseFrame = 610;
            if (animId != 614) {
               animId = 612;
            }
         }

         int candleValue = this.candleTargets[column];
         SpriteRenderer.drawFrame(gfx, baseFrame, 0, posX, baseY);
         int fillHeight = candleHeight * (candleValue >> 16) / 100;
         gfx.setClip(savedClipX, baseY - fillHeight, savedClipWidth, fillHeight);
         SpriteRenderer.drawFrame(gfx, animId, frame, posX, baseY);
         gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
         SpriteRenderer.drawFrame(gfx, 615, 0, posX, baseY);
         if (candleValue > 0 && candleValue < 6553600) {
            SpriteRenderer.hitTest(hitRect, 611, 0, 0);
            SpriteRenderer.drawFrame(gfx, 608, this.candleFrames[column], posX + hitRect[0], baseY + hitRect[1]);
         }

         posX += columnWidth;
      }

      gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
      this.drawEffectsFrom(gfx, this.candleColumns << 1);
   }

   private void updateCandleGame(int delta) {
      this.advanceEffects(delta, this.candleColumns);
      if (this.miniStep == 5) {
         this.effectAnimators[0].advance(delta);
         if (this.keyRepeatTime > 3200) {
            this.setMiniGameStep(2);
         }
      } else if (this.miniStep == 6) {
         if (!this.effectAnimators[this.toastAnimSlot].isRunning()) {
            this.setMiniGameStep(3);
         }
      } else {
         this.progressValue += delta;
         if (this.progressValue > this.progressMax) {
            this.setMiniGameStep(5);
         } else {
            int centerX = this.viewX + (this.viewWidth >> 1);
            int columnWidth = SpriteRenderer.frameWidth(610, 0);
            int columnHeight = SpriteRenderer.frameHeight(611, 0);
            int columnX = centerX - (columnWidth * this.candleColumns >> 1) + (columnWidth >> 1);
            int baseY = this.panelTop + this.panelBottom + columnHeight >> 1;
            int[] hitRect = this.frameBounds;
            int posX = columnX;

            for (int column = 0; column < this.candleColumns; column++) {
               int candleValue;
               if ((candleValue = this.candleAnimValues[column]) > 0) {
                  candleValue += delta * this.candleRiseSpeed;
                  this.candleAnimValues[column] = candleValue;
               }

               if (candleValue <= 6553600) {
                  byte animId = 31;
                  short frameId = 614;
                  if (candleValue < 1638400) {
                     animId = 29;
                  } else if (candleValue < 4915200) {
                     animId = 30;
                     frameId = 613;
                  }

                  Animator animator;
                  (animator = this.effectAnimators[column]).play(animId, 4);
                  if (animator.advance(delta) && animator.matchesFrame(hitRect, 0)) {
                     this.playEffect(42, posX + hitRect[0], baseY + hitRect[1], 16);
                  }

                  Animator secondAnimator;
                  (secondAnimator = this.effectAnimators[this.candleColumns + column]).play(frameId, 4);
               }

               this.candleTargets[column] = BaseScreen.approach(this.candleTargets[column], candleValue, delta, 8);
               if (candleValue > 6553600 || this.candleTargets[column] < 65536) {
                  SpriteRenderer.hitTest(hitRect, 611, 0, 0);
                  this.toastAnimSlot = this.playEffect(609, posX + hitRect[0], baseY + hitRect[1], 16);
                  super.canvas.vibrateShort();
                  this.setMiniGameStep(6);
               }

               posX += columnWidth;
            }
         }
      }
   }

   private void handleCandleInput(int keyState, int actionMask) {
      int columnCount = this.candleColumns;
      int currentColumn = this.plotIndex;
      if (this.matchesInput(keyState, actionMask, 32784, 0) && currentColumn > 0) {
         currentColumn = (currentColumn + columnCount - 1) % columnCount;
      }

      if (this.matchesInput(keyState, actionMask, 65600, 0) && currentColumn < columnCount - 1) {
         currentColumn = (currentColumn + 1) % columnCount;
      }

      if (this.matchesInput(keyState, actionMask, 4128, 0)) {
         int newValue;
         newValue = Math.max((newValue = this.candleAnimValues[currentColumn]) - this.candleDecrement, 0);
         this.candleAnimValues[currentColumn] = newValue;
      }

      this.plotIndex = currentColumn;
      if (super.canvas.cheatToggled && this.matchesInput(keyState, actionMask, 1, 0)) {
         this.progressValue = this.progressMax - 1;
      }
   }

   private void setupSliderGame() {
      GameCanvas canvas = super.canvas;
      this.miniType = 2;
      this.leftPressed = false;
      this.rightPressed = false;
      this.miniTarget.setBusy();
      int level;
      int skillLevel;
      switch (skillLevel = this.neighborhood.residentLevel(2)) {
         case 0:
         default:
            level = 30;
            break;
         case 1:
            level = canvas.randomRange(30, 31);
            break;
         case 2:
            level = canvas.randomRange(30, 32);
            break;
         case 3:
            level = canvas.randomRange(30, 33);
            break;
         case 4:
         case 5:
            level = canvas.randomRange(30, 34);
            if (skillLevel == 5 && level < 32) {
               level = canvas.randomRange(30, 34);
            }
      }

      this.miniLevel = level;
      byte sliderStep;
      char sliderRange;
      switch (level) {
         case 30:
         default:
            sliderStep = 32;
            sliderRange = '\u8000';
            break;
         case 31:
            sliderStep = 39;
            sliderRange = 26214;
            break;
         case 32:
            sliderStep = 45;
            sliderRange = 19660;
            break;
         case 33:
            sliderStep = 52;
            sliderRange = 13107;
            break;
         case 34:
            sliderStep = 65;
            sliderRange = 13107;
      }

      this.sliderStep = sliderStep;
      this.sliderRange = sliderRange;
      this.timingMeter = 0;
      this.sliderPosition = 65536;
      this.targetPosition = super.canvas.randomRange(0, 131071);
      this.candleFlameAnimator = this.effectAnimators[0];
      this.candleFlameAnimator.play(617, 20);
      this.candleGlowAnimator = this.effectAnimators[1];
      this.candleGlowAnimator.play(620, 20);
      this.pickNextSliderTarget();
   }

   private void drawSliderGame(Graphics gfx) {
      if (this.miniStep != 0 && this.miniStep != 7) {
         int centerX = this.viewX + (this.viewWidth >> 1);
         int topY = this.panelBottom + 18;
         SpriteRenderer.drawFrame(gfx, 622, 0, centerX, topY);
         int[] hitRect = this.frameBounds;
         SpriteRenderer.hitTestMirrored(this.frameBounds, 622, 0, 0);
         int barY = topY + hitRect[1] + hitRect[3];
         int barX = centerX + hitRect[0];
         int barWidth = hitRect[2];
         int markerX = barX + FixedMath.mul(barWidth, FixedMath.div(this.sliderPosition, 131072));
         if (this.isSliderNear()) {
            int frame = -1;
            if (this.timingMeter < 58982) {
               int frameCount = SpriteRenderer.frameCount(619);
               int frameStep = 65536 / frameCount;
               frame = this.timingMeter / frameStep;
            }

            int savedClipX = gfx.getClipX();
            int savedClipY = gfx.getClipY();
            int savedClipWidth = gfx.getClipWidth();
            int savedClipHeight = gfx.getClipHeight();
            gfx.setClip(barX, savedClipY, barWidth, savedClipHeight);
            if (frame != -1) {
               SpriteRenderer.drawFrame(gfx, 619, frame, markerX, barY);
            } else {
               SpriteRenderer.drawAnimator(gfx, this.candleGlowAnimator, markerX, barY);
            }

            gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
         }

         if (this.timingMeter > -16384 && this.timingMeter < 65536) {
            SpriteRenderer.drawFrame(gfx, 621, 0, markerX, barY);
         }

         markerX = barX + FixedMath.mul(barWidth, FixedMath.div(this.targetPosition, 131072));
         SpriteRenderer.drawAnimator(gfx, this.candleFlameAnimator, markerX, barY);
      }
   }

   private void updateSliderGame(int delta) {
      Sim player = this.activePlayer;
      if (this.miniStep == 7) {
         if (!player.isAnimating()) {
            this.setMiniGameStep(1);
         }
      } else if (this.miniStep == 8) {
         if (!player.isAnimating()) {
            int nextStep = this.isSliderComplete() ? 2 : 3;
            this.setMiniGameStep(nextStep);
         }
      } else {
         int pressure = 0;
         if (this.leftPressed) {
            pressure = 0 - delta;
         }

         if (this.rightPressed) {
            pressure += delta;
         }

         if (pressure != 0) {
            int newPosition = this.sliderPosition + pressure * 131;
            this.sliderPosition = Math.max(Math.min(newPosition, 131072), 0);
         }

         this.candleFlameAnimator.advance(delta);
         this.candleGlowAnimator.advance(delta);
         if (this.timingMeter >= 65536) {
            if (!this.candleFlameAnimator.isRunning()) {
               this.setMiniGameStep(8);
               return;
            }
         } else {
            int meterStep = this.isSliderNear() ? 196 : -262;
            this.timingMeter += meterStep;
            if (this.timingMeter <= -16384) {
               super.canvas.vibrateShort();
               this.setMiniGameStep(8);
               return;
            }

            if (this.timingMeter >= 65536) {
               this.timingMeter = 65535;
            }

            short frameId = 617;
            if (this.timingMeter > 32768) {
               frameId = 618;
            }

            this.candleFlameAnimator.play(frameId, 4);
         }

         if (this.timingMeter > -16384 && this.timingMeter < 65536) {
            boolean movingLeft;
            int direction = (movingLeft = this.targetGoal < this.targetStart) ? -delta : delta;
            this.targetPosition = this.targetPosition + this.sliderStep * direction;
            if (movingLeft && this.targetPosition < this.targetGoal || !movingLeft && this.targetPosition > this.targetGoal) {
               this.targetPosition = this.targetGoal;
               this.pickNextSliderTarget();
            }
         }

         byte animId = 24;
         if (this.timingMeter > 32768) {
            animId = 25;
         }

         player.addState(128);
         player.setPose(animId);
      }
   }

   private boolean isSliderComplete() {
      return this.timingMeter == 65536;
   }

   private boolean isSliderNear() {
      return Math.abs(this.sliderPosition - this.targetPosition) < 26214;
   }

   private void pickNextSliderTarget() {
      int value = this.targetPosition;
      int limit = Math.max(this.targetPosition, 131072 - value);
      int step = super.canvas.randomRange(this.sliderRange, limit);
      boolean movingLeft = value > 131072 - step ? true : value >= step && super.canvas.randomPercent() < 50;
      this.targetStart = value;
      this.targetGoal = value + (movingLeft ? -step : step);
   }

   private void handleSliderInput(int keyState, int actionMask) {
      if (this.timingMeter > -16384 && this.timingMeter < 65536) {
         this.leftPressed = (keyState & 32784) != 0;
         this.rightPressed = (keyState & 65600) != 0;
      } else {
         this.leftPressed = false;
         this.rightPressed = false;
      }

      if (this.matchesInput(keyState, actionMask, 4128, 0) && this.timingMeter >= 58982) {
         if (this.isSliderNear()) {
            this.sliderPosition = this.targetPosition;
            this.candleFlameAnimator.play(616, 16);
            this.timingMeter = 65536;
         } else {
            super.canvas.vibrateShort();
            this.setMiniGameStep(8);
         }
      }

      if (super.canvas.cheatToggled && this.matchesInput(keyState, actionMask, 1, 0)) {
         this.sliderPosition = this.targetPosition;
         this.candleFlameAnimator.play(616, 16);
         this.timingMeter = 65536;
      }
   }

   private void loadSeedData(int resourceId) {
      DataInputStream stream = new DataInputStream(super.canvas.resources.openStream(resourceId));

      try {
         byte[][] rows = new byte[4][];

         for (int row = 0; row < 4; row++) {
            byte length;
            byte[] rowData = new byte[length = stream.readByte()];

            for (int index = 0; index < length; index++) {
               rowData[index] = stream.readByte();
            }

            rows[row] = rowData;
         }

         this.seedRows = rows;
         if (this.seedTypes == null) {
            this.seedTypes = new byte[4];
         }

         byte[] types = this.seedTypes;

         for (int typeIndex = 0; typeIndex < 4; typeIndex++) {
            types[typeIndex] = stream.readByte();
         }
      } catch (IOException failure) {
         this.seedRows = (byte[][])null;
         this.seedTypes = null;
      }
   }

   private void setupSeedGame() {
      GameCanvas canvas = super.canvas;
      super.canvas.randomRange(0, 4);
      this.loadSeedData(49);
      this.miniType = 3;
      int timeLimit = (this.neighborhood.careerPoints(3) >> 2) + 65536;
      this.seedCursorAnimator = this.effectAnimators[0];
      int rowCount = this.neighborhood.residentLevel(3) < 3 ? 3 : 4;
      this.plotCount = rowCount;
      this.seedCount = 10 * timeLimit >> 16;
      this.progressMax = this.seedCount;
      this.cursorPosition = 65536;
      this.seedCells = new short[rowCount][5];
      int tileWidth;
      int rowStep = (tileWidth = SpriteRenderer.frameWidth(630, 0) / DIR_X_TABLE[0]) << 2;

      for (int row = 0; row < rowCount; row++) {
         byte[] rowData = this.seedRows[row];
         short[] cells = this.seedCells[row];
         int length = rowData.length;
         int offsetX = 0;
         int offsetY = row * rowStep;
         cells[0] = 0;
         cells[1] = (short)offsetY;

         for (int index = 0; index < length; index++) {
            byte direction = rowData[index];
            offsetX += DIR_X_TABLE[direction] * tileWidth;
            offsetY += DIR_Y_TABLE[direction] * tileWidth;
            if (direction == 1 || direction == 2) {
               cells[4] = (short)offsetX;
            }
         }

         cells[2] = (short)offsetX;
         cells[3] = (short)offsetY;
      }

      this.seedTimer = 1;
      this.seedCursorAnimator.play(624, 20);
   }

   private void drawSeedGame(Graphics gfx) {
      int tileWidth;
      int rowStep;
      int totalHeight = (rowStep = (tileWidth = SpriteRenderer.frameWidth(630, 0) / DIR_X_TABLE[0]) << 2) * this.plotCount;
      int halfHeight;
      int rowY = (halfHeight = this.panelTop + this.panelBottom - totalHeight >> 1) + rowStep;
      int startX;
      int startY = (startX = this.viewX + (this.viewWidth >> 1) - (this.viewWidth >> 2)) + tileWidth * 11;
      fillTiled(gfx, this.viewX + 10, this.panelTop + 0, this.viewWidth - 20, this.panelBottom - this.panelTop - 0, 623);

      for (int row = 0; row < this.plotCount; row++) {
         int posX = startX;
         int posY = rowY + row * rowStep;
         SpriteRenderer.drawFrame(gfx, 627, 0, startX, posY);
         byte[] rowData = this.seedRows[row];

         for (int index = 0; index < rowData.length; index++) {
            byte direction = rowData[index];
            SpriteRenderer.drawFrame(gfx, 630, direction, posX, posY);
            posX += DIR_X_TABLE[direction] * tileWidth;
            posY += DIR_Y_TABLE[direction] * tileWidth;
         }
      }

      int cursorY = halfHeight + FixedMath.mul(totalHeight, FixedMath.div(this.cursorPosition, 131072));
      this.seedCursorAnimator.draw(gfx, startY, cursorY);
      this.drawEffectsScaled(gfx, 1, startX, rowY);
   }

   private void handleSeedInput(int keyState, int actionMask) {
      this.setMoveFlags(keyState);
      if (this.seedCursorAnimator.isLooping() && this.matchesInput(keyState, actionMask, 4128, 0)) {
         this.snapSeedCursor();
      }

      if (super.canvas.cheatToggled && this.matchesInput(keyState, actionMask, 1, 0)) {
         this.setMiniGameStep(2);
      }
   }

   private void updateSeedGame(int delta) {
      switch (this.miniStep) {
         case 1:
            this.seedCursorAnimator.advance(delta);
            int speed = this.neighborhood.careerPoints(3) / 5;
            if (!this.seedCursorAnimator.isRunning()) {
               this.seedCursorAnimator.play(624, 20);
               this.plantSeed();
            }

            this.advanceEffects(delta, 1);
            if (this.seedCursorAnimator.isLooping()) {
               int tileWidth = delta * ((this.downPressed ? 65 : 0) + (this.upPressed ? -65 : 0));
               int rowStep;
               if ((rowStep = this.cursorPosition + tileWidth) < 0) {
                  rowStep = 0;
               } else if (rowStep > 131072) {
                  rowStep = 131072;
               }

               this.cursorPosition = rowStep;
            }

            boolean anyRunning2 = false;

            for (int row = 1; row < 24; row++) {
               Animator animator;
               if ((animator = this.effectAnimators[row]).isRunning()) {
                  anyRunning2 = true;
                  int[] position;
                  int pathIndex;
                  if ((pathIndex = (position = this.effectPositions[row])[2]) != -1) {
                     boolean anyRunning = (pathIndex & 65536) != 0;
                     pathIndex &= 65535;
                     short[] cells = this.seedCells[pathIndex];
                     int endX;
                     int endY;
                     int startX;
                     int startY;
                     if (anyRunning) {
                        endX = cells[2] << 16;
                        endY = cells[3] << 16;
                        startX = cells[0] << 16;
                        startY = cells[1] << 16;
                     } else {
                        endX = cells[0] << 16;
                        endY = cells[1] << 16;
                        startX = cells[2] << 16;
                        startY = cells[3] << 16;
                     }

                     int seedX = cells[4] << 16;
                     int posX = position[0];
                     int posY = position[1];
                     int dirX;
                     int dirY;
                     if (posX == seedX) {
                        dirX = 0;
                        dirY = FixedMath.sign(startY - endY);
                     } else {
                        dirX = FixedMath.sign(startX - endX);
                        dirY = 0;
                     }

                     int step = (speed >> 1) + 65536;
                     int newX = FixedMath.mul(delta * 1310, step);
                     int newY = posX + dirX * newX;
                     int threshold = posY + dirY * newX;
                     if ((dirX <= 0 || posX >= seedX || newY <= seedX) && (dirX >= 0 || posX <= seedX || newY >= seedX)) {
                        if (dirX > 0 && posX < startX && newY >= startX || dirX < 0 && posX > startX && newY <= startX) {
                           animator.setRunning(false);
                           super.canvas.vibrateShort();
                           this.setMiniGameStep(3);
                           return;
                        }

                        if (dirY > 0 && posY < startY && threshold >= startY || dirY < 0 && posY > startY && threshold <= startY) {
                           threshold = startY;
                           newY = seedX + FixedMath.sign(startX - endX);
                        }
                     } else {
                        newY = seedX;
                     }

                     for (int otherIndex = 1; otherIndex < 24; otherIndex++) {
                        Animator otherAnimator = this.effectAnimators[otherIndex];
                        int[] otherPosition;
                        if (otherIndex != row
                           && otherAnimator.isRunning()
                           && ((otherPosition = this.effectPositions[otherIndex])[2] & 65535) == pathIndex
                           && Math.abs(otherPosition[0] - newY) + Math.abs(otherPosition[1] - threshold) < 196608) {
                           animator.play(629, 16);
                           position[2] = -1;
                           otherAnimator.setRunning(false);
                           otherPosition[2] = -1;
                        }
                     }

                     position[0] = newY;
                     position[1] = threshold;
                  }
               }
            }

            if (this.seedCount > 0) {
               this.seedTimer -= delta;
               if (this.seedTimer <= 0) {
                  int rowY = super.canvas.randomRange(0, this.plotCount - 1);
                  this.spawnSeedEffect(rowY, false);
                  this.seedCount--;
                  this.progressValue++;
                  int timer = 5000 * (65536 - (speed >> 1)) >> 16;
                  this.seedTimer = 1000 + super.canvas.randomRange(0, timer);
                  return;
               }
            } else if (!anyRunning2) {
               this.setMiniGameStep(2);
            }
      }
   }

   private void snapSeedCursor() {
      int step = 131072 / this.plotCount;
      int value = Math.min(this.cursorPosition / step, this.plotCount - 1) * step + (step >> 1);
      this.cursorPosition = value;
      this.seedCursorAnimator.play(625, 16);
   }

   private void plantSeed() {
      int step = 131072 / this.plotCount;
      int index = GameCanvas.indexOfByte(Math.min(this.cursorPosition / step, this.plotCount - 1), this.seedTypes);
      this.spawnSeedEffect(index, true);
   }

   private void spawnSeedEffect(int plot, boolean atStart) {
      short[] cells = this.seedCells[plot];
      short posX;
      short posY;
      short frameId;
      if (atStart) {
         posX = cells[2];
         posY = cells[3];
         frameId = 628;
      } else {
         posX = cells[0];
         posY = cells[1];
         frameId = 626;
      }

      int slot;
      if ((slot = this.playEffect(frameId, posX << 16, posY << 16, 20)) != -1) {
         this.effectPositions[slot][2] = plot | (atStart ? 65536 : 0);
      }
   }

   private void setupVehicleGame() {
      GameCanvas canvas = super.canvas;
      this.miniType = 1;
      int wobbleSpeed;
      int wobbleAmount;
      char duration;
      switch (this.neighborhood.residentLevel(1)) {
         case 0:
         default:
            wobbleSpeed = 32768;
            wobbleAmount = 19660;
            duration = '\uafc8';
            break;
         case 1:
            wobbleSpeed = 32768;
            wobbleAmount = 29491;
            duration = '\u9c40';
            break;
         case 2:
            wobbleSpeed = 39321;
            wobbleAmount = 39321;
            duration = '\u9c40';
            break;
         case 3:
            wobbleSpeed = 49152;
            wobbleAmount = 52428;
            duration = '\u88b8';
            break;
         case 4:
         case 5:
            wobbleSpeed = 65536;
            wobbleAmount = 65536;
            duration = 30000;
      }

      this.wobbleSpeed = wobbleSpeed;
      this.wobbleAmount = wobbleAmount;
      this.progressMax = duration;
      this.vehicleAnimator = this.effectAnimators[1];
      this.vehicleAnimator.play(605, 20);
      int tileWidth = SpriteRenderer.frameWidth(603, 0);
      int fieldWidth = 4 * tileWidth;
      int fieldHeight = 4 * tileWidth;
      int cellWidth = SpriteRenderer.frameWidth(606, 0);
      int cellHeight = SpriteRenderer.frameHeight(606, 0);
      int cellSpanX = cellWidth;
      int cellSpanY = cellHeight;
      int rangeX = fieldWidth - cellWidth;
      int rangeY = fieldHeight - cellHeight;

      for (int cell = 0; cell < 5; cell++) {
         int[] position;
         (position = this.effectPositions[cell + 2])[0] = canvas.randomRange(cellSpanX, rangeX);
         position[1] = canvas.randomRange(cellSpanY, rangeY);
         position[2] = 0;
      }

      this.clearMoveFlags();
      this.gridX = 262144;
      this.gridY = 262144;
      this.hoverCell = -1;
      this.phaseX = FixedMath.mul(1966, this.wobbleSpeed);
      this.phaseY = FixedMath.mul(1638, this.wobbleSpeed);
      this.offsetX = 0;
      this.offsetY = 0;
      this.progressValue = 0;
   }

   private void drawVehicleGame(Graphics gfx) {
      int cellWidth = SpriteRenderer.frameWidth(603, 0);
      int fieldWidth = 4 * cellWidth;
      int fieldHeight = 4 * cellWidth;
      fillTiled(gfx, this.viewX + 15, this.panelTop + 5, this.viewWidth - 30, this.panelBottom - this.panelTop - 10, 603);
      int centerX;
      int fieldX = (centerX = this.viewX + (this.viewWidth >> 1)) - (fieldWidth >> 1);
      int fieldY = this.panelTop + this.panelBottom - fieldHeight >> 1;

      for (int cell = 2; cell < 24; cell++) {
         int[] position;
         int cellValue;
         if ((cellValue = (position = this.effectPositions[cell])[2]) != -1) {
            SpriteRenderer.drawFrame(gfx, 606, cellValue, fieldX + position[0], fieldY + position[1]);
         }
      }

      int cursorX = this.gridX + this.offsetX >> 3;
      int cursorY = this.gridY + this.offsetY >> 3;
      int posX = FixedMath.mul(cursorX, fieldWidth);
      int posY = FixedMath.mul(cursorY, fieldHeight);
      this.vehicleAnimator.draw(gfx, fieldX + posX, fieldY + posY);
      this.drawEffects(gfx, 2, fieldX, fieldY);
   }

   private void handleVehicleInput(int keyState, int actionMask) {
      this.setMoveFlags(keyState);
      if (this.vehicleAnimator.animId != 604 && this.matchesInput(keyState, actionMask, 4128, 0)) {
         this.vehicleAnimator.play(604, 16);
      }

      if (super.canvas.cheatToggled && this.matchesInput(keyState, actionMask, 1, 0)) {
         this.setMiniGameStep(2);
      }
   }

   private void updateVehicleGame(int delta) {
      switch (this.miniStep) {
         case 1:
            this.advanceEffects(delta, 2);
            this.vehicleAnimator.advance(delta);
            if (!this.vehicleAnimator.isRunning()) {
               this.vehicleAnimator.play(605, 20);
               this.advanceVehicleFrame(this.hoverCell);
            }

            if (this.vehicleAnimator.animId != 604) {
               int moveX = delta * ((this.rightPressed ? 229 : 0) + (this.leftPressed ? -229 : 0));
               int moveY = delta * ((this.downPressed ? 229 : 0) + (this.upPressed ? -229 : 0));
               this.gridX = Math.max(0, Math.min(524288, this.gridX + moveX));
               this.gridY = Math.max(0, Math.min(524288, this.gridY + moveY));
               this.phaseX += delta;
               this.phaseY += delta;
               int phaseX = FixedMath.mul(this.wobbleSpeed, 1966);
               int phaseY = FixedMath.mul(this.wobbleSpeed, 1638);
               if (this.phaseX > phaseX) {
                  this.phaseX = 0;
               }

               if (this.phaseX > phaseX >> 1) {
                  int smoothValue = FixedMath.smoothStep(phaseX >> 1, phaseX, this.phaseX);
                  this.offsetX = this.wobbleAmount - FixedMath.mul(smoothValue, this.wobbleAmount);
               } else {
                  int smoothValue2 = FixedMath.smoothStep(0, phaseX >> 1, this.phaseX);
                  this.offsetX = FixedMath.mul(smoothValue2, this.wobbleAmount);
               }

               this.offsetX = this.offsetX - (this.wobbleAmount >> 1);
               if (this.phaseY > phaseY) {
                  this.phaseY = 0;
               }

               if (this.phaseY > phaseY >> 1) {
                  int smoothHigh = FixedMath.smoothStep(phaseY >> 1, phaseY, this.phaseY);
                  this.offsetY = this.wobbleAmount - FixedMath.mul(smoothHigh, this.wobbleAmount);
               } else {
                  int smoothLow = FixedMath.smoothStep(0, phaseY >> 1, this.phaseY);
                  this.offsetY = FixedMath.mul(smoothLow, this.wobbleAmount);
               }

               this.offsetY = this.offsetY - (this.wobbleAmount >> 1);
            }

            int cellWidth = SpriteRenderer.frameWidth(603, 0);
            int fieldWidth = 4 * cellWidth;
            int fieldHeight = 4 * cellWidth;
            int cursorX = this.gridX + this.offsetX >> 3;
            int cursorY = this.gridY + this.offsetY >> 3;
            int posX = FixedMath.mul(cursorX, fieldWidth);
            int posY = FixedMath.mul(cursorY, fieldHeight);
            boolean noCells = true;
            int hoverCell = -1;
            int bestDistance = -1;

            for (int cell = 2; cell < 24; cell++) {
               int[] position;
               if ((position = this.effectPositions[cell])[2] != -1) {
                  noCells = false;
                  int offsetX = position[0] - posX;
                  int offsetY = position[1] - posY;
                  int distance = offsetX * offsetX + offsetY * offsetY;
                  if (hoverCell == -1 || distance < bestDistance) {
                     hoverCell = cell;
                     bestDistance = distance;
                  }
               }
            }

            if (bestDistance > 225) {
               hoverCell = -1;
            }

            this.hoverCell = hoverCell;
            if (noCells) {
               this.setMiniGameStep(9);
               return;
            }

            this.progressValue += delta;
            if (this.progressValue > this.progressMax) {
               super.canvas.vibrateShort();
               this.setMiniGameStep(9);
               return;
            }
            break;
         case 9:
            this.advanceEffects(delta, 2);
            this.effectAnimators[0].advance(delta);
            if (this.keyRepeatTime > 3200) {
               if (this.progressValue <= this.progressMax) {
                  this.setMiniGameStep(2);
                  return;
               }

               this.setMiniGameStep(3);
            }
      }
   }

   private void advanceVehicleFrame(int cell) {
      if (cell != -1) {
         int[] position;
         int value = (position = this.effectPositions[cell])[2];
         if (++value >= SpriteRenderer.frameCount(606)) {
            value = -1;
            this.effectAnimators[cell].play(607, 16);
         }

         position[2] = value;
      }
   }

   public final void openVehicleInfo(Entity entity) {
      int objectType = entity.type;
      if (entity.type == 114 && !super.canvas.gameStarted) {
         this.chooseDialogOptionAt(785, 784);
      } else {
         this.targetObjectType = objectType;
         this.interactionAmount = 0;
         this.onTick(17);
         this.setVehiclePhase(0);
      }
   }

   private void setVehiclePhase(int phase) {
      this.plantPhase = phase;
      switch (phase) {
         case 0:
            this.showVehicleList();
            break;
         case 1:
            this.onTick(5);
      }

      this.updateInputMasks();
   }

   private void drawVehiclePage(Graphics gfx) {
      switch (this.plantPhase) {
         case 0:
            this.drawVehicleDetails(gfx);
         default:
            this.drawSoftKeyHint(gfx);
      }
   }

   private void drawVehicleDetails(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      Lot lot = this.currentLot;
      Neighborhood neighborhood = this.neighborhood;
      int objectIndex = lot.bitForSound(this.targetObjectType, super.selectedIndex);
      this.setValue(1, this.currentLot.spriteIdAt(this.targetObjectType));
      int increase;
      if ((increase = lot.categoryTypeAt(objectIndex)) > 0) {
         SdkString label = canvas.beginString();
         canvas.formatNumber(increase);
         canvas.setString(-9, label);
         this.setValue(2, -9);
      } else {
         this.setValue(2, 711);
      }

      int decrease;
      if ((decrease = lot.categorySubTypeAt(objectIndex)) > 0) {
         SdkString secondLabel = canvas.beginString();
         canvas.formatNumber(decrease);
         canvas.setString(-8, secondLabel);
         this.setValue(3, -8);
      } else {
         this.setValue(3, 711);
      }

      int delta;
      if (this.interactionAmount >= 0) {
         delta = this.interactionAmount * increase;
      } else {
         delta = -this.interactionAmount * decrease;
      }

      SdkString thirdLabel = canvas.beginString();
      canvas.appendInt(neighborhood.itemCount(objectIndex));
      canvas.appendStringId(27);
      canvas.appendInt(lot.categoryFlagAt(objectIndex));
      canvas.setString(-7, thirdLabel);
      this.setValue(4, -7);
      thirdLabel = canvas.beginString();
      canvas.appendInt(this.interactionAmount);
      canvas.setString(-6, thirdLabel);
      this.setValue(5, -6);
      thirdLabel = canvas.beginString();
      canvas.formatNumber(delta);
      canvas.setString(-5, thirdLabel);
      this.setValue(6, -5);
      this.drawPanel(gfx, 22);
   }

   private void showVehicleList() {
      this.beginList(this.currentLot.countBySoundMask(this.targetObjectType), 18, 0);
   }

   private void handleVehicleListInput(int keyState, int actionMask) {
      Neighborhood neighborhood = this.neighborhood;
      Lot lot = this.currentLot;
      switch (this.plantPhase) {
         case 0:
            if (this.matchesInput(keyState, actionMask, 0, 4)) {
               this.setVehiclePhase(1);
               return;
            } else if (this.scrollList(keyState, actionMask)) {
               this.adjustVehicleAmount(0);
               return;
            } else if (this.matchesInput(keyState, actionMask, 4128, 12288) && this.interactionAmount != 0) {
               int objectIndex = lot.bitForSound(this.targetObjectType, super.selectedIndex);
               int delta = this.interactionAmount * (this.interactionAmount > 0 ? lot.categoryTypeAt(objectIndex) : lot.categorySubTypeAt(objectIndex));
               neighborhood.addItem(objectIndex, this.interactionAmount);
               if (this.interactionAmount > 0) {
                  neighborhood.onInteractionResult(objectIndex);
               }

               neighborhood.addMoney(-delta);
               this.adjustVehicleAmount(0);
               return;
            } else {
               if (this.matchesInput(keyState, actionMask, 32784, 0)) {
                  this.adjustVehicleAmount(this.interactionAmount - 1);
               }

               if (this.matchesInput(keyState, actionMask, 65600, 0)) {
                  this.adjustVehicleAmount(this.interactionAmount + 1);
               }
            }
      }
   }

   private void adjustVehicleAmount(int amount) {
      Neighborhood neighborhood = this.neighborhood;
      Lot lot = this.currentLot;
      int objectIndex = this.currentLot.bitForSound(this.targetObjectType, super.selectedIndex);
      int money = neighborhood.money;
      int increase = lot.categoryTypeAt(objectIndex);
      int decrease = lot.categorySubTypeAt(objectIndex);
      int maxAmount;
      if (increase <= 0) {
         maxAmount = 0;
      } else {
         maxAmount = Math.min(Math.min(lot.categoryFlagAt(objectIndex) - neighborhood.itemCount(objectIndex), money / increase), lot.categoryFlagAt(objectIndex));
      }

      int minAmount;
      if (decrease <= 0) {
         minAmount = 0;
      } else {
         minAmount = -neighborhood.itemCount(objectIndex);
      }

      this.interactionAmount = FixedMath.clamp(amount, minAmount, maxAmount);
      this.updateInputMasks();
   }

   private void openCheatsDialog() {
      if (screenState == 5 && (!this.mainMenuOpen || this.mainMenuPage != 5)) {
         this.closeOpenMenus();
         this.openMainMenu();
         this.setMainMenuPage(5);
      }
   }

   private void buildCheatMenu() {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = this.neighborhood;
      short[] ids = new short[35];
      SdkString[] labels = new SdkString[35];
      GameCanvas.initListWith(ids, 123, canvas.menuRowSpacing - 4);
      GameCanvas.addToList(ids, 0);
      labels[0] = GameCanvas.createString("Give Money");
      GameCanvas.addToList(ids, 0);
      labels[1] = GameCanvas.createString("Advance 1 hour");
      GameCanvas.addToList(ids, 0);
      labels[2] = GameCanvas.createString("Advance 1 day");
      GameCanvas.addToList(ids, 0);
      labels[3] = GameCanvas.createString("Clear Motives");
      GameCanvas.addToList(ids, 0);
      labels[4] = GameCanvas.createString("Get Friends");
      GameCanvas.addToList(ids, 0);
      labels[5] = GameCanvas.createString("Break everything");
      GameCanvas.addToList(ids, 0);
      labels[6] = GameCanvas.createString("Fix everything");
      GameCanvas.addToList(ids, 0);
      labels[7] = GameCanvas.createString("Dirty everything");
      GameCanvas.addToList(ids, 0);
      labels[8] = GameCanvas.createString("Clean everything");
      GameCanvas.addToList(ids, 0);
      labels[9] = GameCanvas.createString("Unlock bonus");
      GameCanvas.addToList(ids, 0);
      labels[10] = GameCanvas.createString("Give all");
      GameCanvas.addToList(ids, 0);
      labels[11] = GameCanvas.createString("< Cooking: " + neighborhood.residentLevel(0) + " >");
      GameCanvas.addToList(ids, 0);
      labels[12] = GameCanvas.createString("< Cleaning: " + neighborhood.residentLevel(1) + " >");
      GameCanvas.addToList(ids, 0);
      labels[13] = GameCanvas.createString("< Fishing: " + neighborhood.residentLevel(2) + " >");
      GameCanvas.addToList(ids, 0);
      labels[14] = GameCanvas.createString("< Repairing: " + neighborhood.residentLevel(3) + " >");
      GameCanvas.addToList(ids, 0);
      labels[15] = GameCanvas.createString("< Gardening: " + neighborhood.residentLevel(4) + " >");
      int simId = neighborhood.watchedSim;
      if (neighborhood.watchedSim != -1) {
         String simName = canvas.getString(neighborhood.simNameText(simId)).toString();
         GameCanvas.addToList(ids, 3);
         labels[16] = GameCanvas.createString(simName + ": Friends");
         GameCanvas.addToList(ids, 4);
         labels[17] = GameCanvas.createString(simName + ": Disliked");
         GameCanvas.addToList(ids, 5);
         labels[18] = GameCanvas.createString(simName + ": Romance");
         GameCanvas.addToList(ids, 6);
         labels[19] = GameCanvas.createString(simName + ": Good Friends");
         GameCanvas.addToList(ids, 7);
         labels[20] = GameCanvas.createString(simName + ": Enemies");
         GameCanvas.addToList(ids, 8);
         labels[21] = GameCanvas.createString(simName + ": Date");
         GameCanvas.addToList(ids, 9);
         labels[22] = GameCanvas.createString(simName + ": Best Friends");
         GameCanvas.addToList(ids, 10);
         labels[23] = GameCanvas.createString(simName + ": Nemesis");
         GameCanvas.addToList(ids, 11);
         labels[24] = GameCanvas.createString(simName + ": Partner");
      }

      this.cheatLabels = ids;
      this.cheatText = labels;
      this.cheatLabels[2] = 0;
   }

   private void handleCheatMenuInput(int keyState, int actionMask) {
      GameCanvas canvas = super.canvas;
      super.canvas.navigateLeftRight(this.cheatLabels, keyState);
      Neighborhood neighborhood = this.neighborhood;
      short option = this.cheatLabels[2];
      if (this.matchesInput(keyState, actionMask, 4128, 16)) {
         switch (option) {
            case 0:
               neighborhood.addMoney(1000);
               return;
            case 1:
               neighborhood.applyCommand(0);
               return;
            case 2:
               neighborhood.applyCommand(1);
               return;
            case 3:
               neighborhood.applyCommand(2);
               this.closeMainMenu();
               return;
            case 4:
               neighborhood.applyCommand(12);
               this.closeMainMenu();
               return;
            case 5:
               Entity[] breakList = this.getEntities();

               for (int breakIndex = 0; breakIndex < breakList.length; breakIndex++) {
                  if (breakList[breakIndex].hasFlag(67108864) && !breakList[breakIndex].hasState(256)) {
                     breakList[breakIndex].turnOnPrimary();
                  }
               }

               this.closeMainMenu();
               return;
            case 6:
               Entity[] fixList = this.getEntities();

               for (int fixIndex = 0; fixIndex < fixList.length; fixIndex++) {
                  if (fixList[fixIndex].hasFlag(67108864) && fixList[fixIndex].hasState(256)) {
                     fixList[fixIndex].turnOffPrimary();
                  }
               }

               this.closeMainMenu();
               return;
            case 7:
               Entity[] dirtyList = this.getEntities();

               for (int dirtyIndex = 0; dirtyIndex < dirtyList.length; dirtyIndex++) {
                  if (dirtyList[dirtyIndex].hasFlag(134217728) && !dirtyList[dirtyIndex].hasState(512)) {
                     dirtyList[dirtyIndex].turnOnSecondary();
                  }
               }

               this.closeMainMenu();
               return;
            case 8:
               Entity[] cleanList = this.getEntities();

               for (int cleanIndex = 0; cleanIndex < cleanList.length; cleanIndex++) {
                  if (cleanList[cleanIndex].hasFlag(134217728) && cleanList[cleanIndex].hasState(512)) {
                     cleanList[cleanIndex].turnOffSecondary();
                  }
               }

               this.closeMainMenu();
               return;
            case 9:
               neighborhood.applyCommand(24);
               this.closeMainMenu();
               return;
            case 10:
               neighborhood.applyCommand(25);
               this.closeMainMenu();
               return;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
               return;
            default:
               neighborhood.applyCommand(this.cheatLabels[5 + option]);
               this.closeMainMenu();
         }
      } else {
         if (this.matchesInput(keyState, actionMask, 98384, 0)) {
            boolean decrease = (keyState & 32784) != 0;
            boolean bulkApplied = false;
            if (option == 11) {
               neighborhood.applyCommand(decrease ? 13 : 14);
               this.cheatText[11] = GameCanvas.createString("< Cooking: " + neighborhood.residentLevel(0) + " >");
            } else if (option == 12) {
               neighborhood.applyCommand(decrease ? 15 : 16);
               this.cheatText[12] = GameCanvas.createString("< Cleaning: " + neighborhood.residentLevel(1) + " >");
            } else if (option == 13) {
               neighborhood.applyCommand(decrease ? 17 : 18);
               this.cheatText[13] = GameCanvas.createString("< Fishing: " + neighborhood.residentLevel(2) + " >");
            } else {
               if (option != 14) {
                  if (option == 15) {
                     neighborhood.applyCommand(decrease ? 21 : 22);
                     this.cheatText[15] = GameCanvas.createString("< Gardening: " + neighborhood.residentLevel(4) + " >");
                  }

                  return;
               }

               neighborhood.applyCommand(decrease ? 19 : 20);
               this.cheatText[14] = GameCanvas.createString("< Repairing: " + neighborhood.residentLevel(3) + " >");
            }
         } else if (this.matchesInput(keyState, actionMask, 0, 4)) {
            this.closeMainMenu();
         }
      }
   }

   private void drawCheatMenu(Graphics gfx) {
      this.drawTwoColumnList(gfx, this.cheatLabels, 0, this.cheatText);
   }
}
