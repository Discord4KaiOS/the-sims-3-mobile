package com.ea.sims3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreNotFoundException;

public final class GameCanvas extends Canvas implements SpriteIndex, SoundIndex, Resources, PlayerListener {
   public MonkeyApp app;
   private boolean running;
   public BaseScreen screen;
   public boolean suspended;
   public static boolean landscape = false;
   public static int lastLandscape = -1;
   public static int currentLandscape = -1;
   public PromoImages promoImages = null;
   public boolean promoActive = false;
   public ResourceLoader resources = null;
   private int soundLoopId;
   public SdkSound sound;
   private Random random;
   private boolean gcPending;
   private static GameCanvas instance = null;
   private Timer timer = null;
   private boolean updating = false;
   public static boolean resuming = false;
   public int frozenFrames = 0;
   private int actionState;
   private int softKeyAction;
   private int[] keyQueue;
   private int keyQueueSize;
   public int rightSoftKeyAction;
   public int leftSoftKeyAction;
   private Object[] fonts;
   public int headerCount;
   private static byte[] ioBuffer;
   private int tempStringCursor;
   private SdkString[] tempStrings;
   private SdkString[] formattedStrings = new SdkString[10];
   private static final SdkString[] formatArgs = new SdkString[3];
   private SdkString buildBuffer;
   private SdkString[] stringPool;
   private int buildStringCursor;
   private short[] wrappedLineOffsets;
   private SdkString wrappedString;
   private short[][] wrapOffsets;
   private SdkString[] slotTexts;
   private short[] slotStringIds;
   private int[] marqueeStringIds = new int[2];
   private SdkString[] marqueeTexts = new SdkString[2];
   private int[] textWidths = new int[2];
   private int[] marqueeTimers = new int[2];
   private int[] scrollOffsets = new int[2];
   private ScreenFade fade = new ScreenFade();
   private boolean fading;
   public boolean fadeFinished;
   private int fadeColor;
   private int fadeStep;
   public int fadeSpeed;
   public int pendingScreenAction;
   private int pendingLanguage;
   public boolean cheatToggled;
   private static final int[][] cheatSequences = new int[][]{{0, 2048, 2048, 16, 64, 128, 8}};
   private long leftSoftKeyTimer;
   private long rightSoftKeyTimer;
   private Animator leftSoftKeyAnimator;
   private Animator rightSoftKeyAnimator;
   private boolean rightSoftKeySelected;
   private int[] tileMasks;
   private int tileLoadCursor;
   private boolean vibrationEnabled;
   public boolean tutorialsEnabled;
   public boolean autonomousEnabled;
   public boolean savePromptsEnabled;
   public int language;
   public int profileSlot;
   private SdkString[] profileNames = new SdkString[2];
   private boolean[] profileUsed = new boolean[2];
   private int progressFlags;
   public boolean gameStarted;
   private long flagsLow;
   private long flagsHigh;
   private long visitedFlagsLow;
   private long visitedFlagsHigh;
   private long newFlagsLow;
   private long newFlagsHigh;
   private long newVisitedFlagsLow;
   private long newVisitedFlagsHigh;
   private int loadingTipId;
   private int loadingTipX;
   private int loadingTipWidth;
   public int menuMode;
   private short[] optionsMenu;
   private short[] compactOptionsMenu;
   private short[] helpMenu;
   private short[] currentMenu;
   private int returnMenuMode;
   public short menuRowSpacing;
   public Neighborhood neighborhood;
   public Lot lot;
   public int houseIndex;
   public int houseUpgrade;
   private short[][] tileGroups;
   public int simPosX;
   public int simPosY;
   public int walkTargetX;
   public int walkTargetY;
   public int walkTargetFrame;

   public final void playerUpdate(Player player, String event, Object data) {
   }

   public final void setSoundLoop(int soundId) {
      if (this.soundLoopId != soundId && this.soundLoopId != -1) {
         this.sound.stop();
      }

      this.soundLoopId = soundId;
      if (soundId == -1) {
         this.sound.setLoopCount(1);
      } else {
         this.sound.setLoopCount(-1);
         this.sound.pendingSound = soundId;
      }
   }

   private void preloadSounds() {
      SdkSound soundRef = this.sound;
      this.sound.play(11);
      soundRef.play(10);
      soundRef.play(2);
      soundRef.play(3);
      soundRef.play(0);
      soundRef.play(1);
      soundRef.play(4);
      soundRef.play(5);
      soundRef.play(6);
      soundRef.play(7);
      soundRef.play(8);
      soundRef.play(9);
      soundRef.play(14);
      soundRef.play(15);
      soundRef.play(12);
      soundRef.play(13);
   }

   public final int halfWidth() {
      return this.getWidth() >> 1;
   }

   public final int halfHeight() {
      return this.getHeight() >> 1;
   }

   public static void create(MonkeyApp app) {
      instance = new GameCanvas(app);
   }

   public static GameCanvas instance() {
      return instance;
   }

   private GameCanvas(MonkeyApp app) {
      com.ea.sims3.SdkText.setMidlet((MIDlet)app);
      if (this.getWidth() > this.getHeight()) {
         lastLandscape = 1;
         currentLandscape = 1;
      } else {
         lastLandscape = 0;
         currentLandscape = 0;
      }

      this.app = app;
      this.running = false;
      this.keyQueue = new int[8];
      this.keyQueueSize = 0;
      this.gcPending = false;
      this.resources = new ResourceLoader();
      this.sound = new SdkSound();
      this.sound.setEnabled(false);
      this.sound.currentLoop = 1;
      this.sound.resources = this.resources;
      this.soundLoopId = -1;
      this.preloadSounds();
      this.random = new Random();
      com.ea.sims3.SpriteRenderer.loadSpriteRects(this.resources);
      com.ea.sims3.SpriteRenderer.loadFrameData(this.resources);
      com.ea.sims3.SpriteRenderer.loadPalette(this.resources);
      this.leftSoftKeyAnimator = new Animator();
      this.rightSoftKeyAnimator = new Animator();
      this.initWorld();
      this.promoImages = new PromoImages(this.getWidth(), this.getHeight());
      SdkString blankName = null;

      for (int slot = 0; slot < 2; slot++) {
         (blankName = new SdkString(8, 0)).setLength(8);

         for (int charIndex = 0; charIndex != 8; charIndex++) {
            blankName.setCharAt(charIndex, ' ');
         }

         this.profileNames[slot] = blankName;
      }

      this.loadSettings();
      this.initTextSlots();
      this.loadFonts();
      if (this.tempStrings == null) {
         this.tempStrings = new SdkString[10];

         for (int stringIndex = 0; stringIndex < 10; stringIndex++) {
            this.tempStrings[stringIndex] = new SdkString();
         }
      }

      this.stringPool = new SdkString[25];

      for (int poolIndex = 0; poolIndex < 25; poolIndex++) {
         this.stringPool[poolIndex] = new SdkString(100, 0);
      }

      this.loadTilesBlocking(1);
      this.resetGameData();
      this.loadGameData();
   }

   public final synchronized void startGame() {
      this.buildMenus();
      byte languageIndex = 5;
      if (this.language == -1) {
         languageIndex = 4;
      }

      this.setPendingScreen(1, languageIndex);
      this.promoImages.setScreenSize(this.getWidth(), this.getHeight());
      this.suspended = false;
   }

   public final void shutdown() {
      this.running = false;
      if (this.screen != null) {
         this.screen.pause();
      }

      this.saveSettings();
      this.setGcPending(true);

      for (int index = 0; index < com.ea.sims3.SoundIndex.SOUND_IDS.length; index++) {
         this.sound.stopAll();
      }
   }

   public final void showNotify() {
      if (!com.ea.sims3.GameScreen.updateBlocked) {
         if (this.suspended) {
            this.resumeGame();
            this.startTimer();
         }
      }
   }

   public final void hideNotify() {
      try {
         Thread.sleep(1000L);
      } catch (Exception ignored) {
      }

      if (!com.ea.sims3.GameScreen.updateBlocked) {
         this.suspendGame();
      }
   }

   public final void startTimer() {
      this.running = true;
      if (this.timer != null) {
         this.timer.cancel();
         this.timer = null;
         gcYield();
      }

      this.timer = new Timer();
      this.timer.schedule(new FrameTimer(this), 0L, 15L);
   }

   private void tick() {
      try {
         this.repaint();
         if (this.promoImages != null) {
            this.promoImages.setScreenSize(this.getWidth(), this.getHeight());
         }

         if (this.screen != null) {
            if (this.screen instanceof GameScreen) {
               GameScreen game = (GameScreen)this.screen;
               if (com.ea.sims3.GameScreen.screenState != 1) {
                  game.layoutViewport();
                  return;
               }
            } else if (this.screen instanceof TitleScreen) {
               Object unused = null;
               if (com.ea.sims3.TitleScreen.state == 5) {
                  this.screen.onTick(5);
               }

               if (com.ea.sims3.TitleScreen.state == 11) {
                  this.slotStringIds[0] = 0;
               }

               if (com.ea.sims3.TitleScreen.state == 10) {
                  this.screen.onTick(10);
               }
            }
         }
      } catch (Exception ignored) {
      }
   }

   public final void advanceFrame(int deltaMs) {
      int actions = this.consumeKeyState();
      this.updateTimers(deltaMs);
      this.clearAction(actions);
      if (this.gcPending) {
         gcYield();
         this.gcPending = false;
      }
   }

   private int consumeKeyState() {
      int[] queue = this.keyQueue;

      for (int clearIndex = this.keyQueueSize - 1; clearIndex >= 0; clearIndex--) {
         if (queue[clearIndex] < 0) {
            this.actionState = this.actionState & ~(-queue[clearIndex]);
            if (-queue[clearIndex] == 131072 || -queue[clearIndex] == 262144 || -queue[clearIndex] == 524288) {
               this.softKeyAction = 0;
            }
         }
      }

      for (int pressIndex = this.keyQueueSize - 1; pressIndex >= 0; pressIndex--) {
         if (queue[pressIndex] > 0) {
            this.actionState = this.actionState | queue[pressIndex];
            if (queue[pressIndex] == 524288) {
               this.softKeyAction = 4;
            } else if (queue[pressIndex] == 262144) {
               this.softKeyAction = this.rightSoftKeyAction;
            } else if (queue[pressIndex] == 131072) {
               this.softKeyAction = this.leftSoftKeyAction;
            }
         }
      }

      int state = 0;

      for (int actionBit = 1; actionBit <= 18; actionBit++) {
         for (int index = 0; index < this.keyQueueSize; index++) {
            if (queue[index] < 0) {
               state |= -queue[index];
            } else if (state == 1 << actionBit && queue[index] > 0) {
               state &= ~queue[index];
            }

            queue[index] = 0;
         }
      }

      this.keyQueueSize = 0;
      return state;
   }

   public final void muteSound() {
      com.ea.sims3.SdkSound.muteAll();
      this.running = false;
   }

   public final void suspendGame() {
      this.releaseSoftKeys();
      this.clearActions();
      if (this.screen != null) {
         this.screen.stop();
      }

      com.ea.sims3.SdkSound.muteAll();
      this.sound.playQueued();
      this.sound.update();
      this.suspended = true;
   }

   public final void resumeGame() {
      resuming = true;
      this.releaseSoftKeys();
      this.clearActions();
      this.keyQueueSize = 0;
      this.suspended = false;
   }

   private final void updateTimers(int deltaMs) {
      if (resuming) {
         this.frozenFrames++;
      }

      if (this.frozenFrames > 500) {
         if (this.soundLoopId != -1) {
            this.setSoundLoop(this.soundLoopId);
         }

         this.frozenFrames = 0;
         resuming = false;
      }

      if (deltaMs > 150) {
         deltaMs = 150;
      }

      if (this.pendingScreenAction != -1) {
         this.changeScreen(this.pendingScreenAction, this.pendingLanguage);
         this.pendingScreenAction = -1;
         this.pendingLanguage = -1;
      }

      if (this.screen != null) {
         if (this.fading) {
            this.fadeColor = this.fadeColor + this.fadeStep * this.fadeSpeed * (deltaMs >> 3);
            if (this.fadeStep > 0 && this.fadeColor >= 16777215) {
               this.fadeColor = 16777215;
            } else if (this.fadeStep < 0 && this.fadeColor <= 0) {
               this.fadeColor = 0;
            }
         }

         if (this.screen != null) {
            try {
               byte newAction = 0;
               if (this.softKeyAction == 4 && this.rightSoftKeyAction != 4 && this.leftSoftKeyAction != 4) {
                  newAction = 4;
                  this.softKeyAction = 0;
               }

               this.screen.update(this.actionState, newAction);
               this.startSoftKeyAnimation(this.softKeyAction);
            } catch (Exception updateError) {
            }

            try {
               this.updateSoftKeyAnimation(deltaMs);
               this.screen.keyPressed(deltaMs);
               return;
            } catch (Exception keyError) {
            }
         }
      }
   }

   public final synchronized void paint(Graphics gfx) {
      try {
         Graphics graphicsCopy = gfx;
         com.ea.sims3.SdkText.setGraphics(gfx);

         try {
            if (this.screen != null && this.running) {
               this.screen.render(graphicsCopy);
            }

            if (this.isFading()) {
               this.drawFade(graphicsCopy);
               if (this.fadeStep > 0 && this.fadeColor >= 16777215 || this.fadeStep < 0 && this.fadeColor <= 0) {
                  this.fadeFinished = true;
               }
            }
         } catch (Exception ignored) {
         }
      } finally {
         this.updating = false;
      }
   }

   private int keyToAction(int keyCode) {
      switch (keyCode) {
         case -8:
            return 524288;
         case -7:
            return 262144;
         case -6:
            return 131072;
         case 35:
            return 1024;
         case 42:
            return 2048;
         case 48:
            return 1;
         case 49:
            return 2;
         case 50:
            return 4;
         case 51:
            return 8;
         case 52:
            return 16;
         case 53:
            return 32;
         case 54:
            return 64;
         case 55:
            return 128;
         case 56:
            return 256;
         case 57:
            return 512;
         default:
            switch (this.getGameAction(keyCode)) {
               case 1:
                  return 8192;
               case 2:
                  return 32768;
               case 3:
               case 4:
               case 7:
               default:
                  return 0;
               case 5:
                  return 65536;
               case 6:
                  return 16384;
               case 8:
                  return 4096;
            }
      }
   }

   public final void keyPressed(int keyCode) {
      if (this.promoActive) {
         this.promoImages.processKey(keyCode);
      } else {
         int action;
         if ((action = this.keyToAction(keyCode)) != 0 && this.keyQueueSize < this.keyQueue.length) {
            this.keyQueue[this.keyQueueSize++] = action;
            this.trackCheatSequence(action);
         }
      }
   }

   public final void keyRepeated(int keyCode) {
      this.keyPressed(keyCode);
   }

   public final void keyReleased(int keyCode) {
      int action;
      if ((action = this.keyToAction(keyCode)) != 0 && this.keyQueueSize < this.keyQueue.length) {
         this.keyQueue[this.keyQueueSize++] = -action;
      }
   }

   public final void clearAction(int mask) {
      this.actionState &= ~mask;
   }

   public final void clearActions() {
      this.actionState = 0;
   }

   public final void releaseSoftKeys() {
      this.softKeyAction = 0;
      this.clearAction(524288);
   }

   public final void setSoftKeys(int leftAction, int rightAction) {
      if (leftAction != this.rightSoftKeyAction) {
         this.rightSoftKeyAction = leftAction;
         this.rightSoftKeyTimer = 0L;
      }

      if (rightAction != this.leftSoftKeyAction) {
         this.leftSoftKeyAction = rightAction;
         this.leftSoftKeyTimer = 0L;
      }
   }

   private static final int actionToLabelId(int action) {
      if (action == 0) {
         return -1;
      } else if (action == 1) {
         return 45;
      } else if (action == 2) {
         return 36;
      } else if (action == 4) {
         return 37;
      } else if (action == 8) {
         return 38;
      } else if (action == 16) {
         return 39;
      } else if (action == 32) {
         return 40;
      } else if (action == 64) {
         return 41;
      } else if (action == 128) {
         return 49;
      } else if (action == 256) {
         return 43;
      } else if (action == 512) {
         return 44;
      } else if (action == 1024) {
         return 35;
      } else if (action == 2048) {
         return 46;
      } else if (action == 4096) {
         return 47;
      } else if (action == 8192) {
         return 48;
      } else if (action == 16384) {
         return 1;
      } else {
         return action == 32768 ? 2 : -1;
      }
   }

   private void loadFonts() {
      ioBuffer = new byte[2600];
      byte[] header = this.readResource("/hdr");
      this.headerCount = com.ea.sims3.SdkText.loadTextHeader(header);
      int languageArg = 0;
      int fontSetIndex;
      int selectedLanguage = fontSetIndex = com.ea.sims3.SdkText.chooseLanguage(languageArg = 0 + this.language);
      if (fontSetIndex < 0) {
         selectedLanguage = -1;
         fontSetIndex = 0;
      }

      Object[] fontArray = new Object[5];
      Image[] fontImages = new Image[5];
      this.fonts = fontArray;
      String fontPath = "/" + com.ea.sims3.SdkText.getFontDifferentiator(fontSetIndex);

      try {
         fontImages[0] = Image.createImage(fontPath + "font_sims_white_outline.png");
         fontImages[1] = Image.createImage(fontPath + "font_sims_black.png");
         fontImages[2] = Image.createImage(fontPath + "font_sims_white_smallcaps.png");
         fontImages[3] = Image.createImage(fontPath + "font_the.png");
         fontImages[4] = Image.createImage(fontPath + "font_thesmall.png");
      } catch (IOException ignored) {
      }

      byte[] data = this.readResource(fontPath + "font_sims_white_outline.rff");
      fontArray[0] = com.ea.sims3.SdkText.createFont(fontImages[0], data);
      data = this.readResource(fontPath + "font_sims_black.rff");
      fontArray[1] = com.ea.sims3.SdkText.createFont(fontImages[1], data);
      data = this.readResource(fontPath + "font_sims_white_smallcaps.rff");
      fontArray[2] = com.ea.sims3.SdkText.createFont(fontImages[2], data);
      data = this.readResource(fontPath + "font_the.rff");
      fontArray[3] = com.ea.sims3.SdkText.createFont(fontImages[3], data);
      data = this.readResource(fontPath + "font_thesmall.rff");
      fontArray[4] = com.ea.sims3.SdkText.createFont(fontImages[4], data);
      ioBuffer = null;
      gcYield();
      this.setLanguage(fontSetIndex);
      this.language = selectedLanguage;
      Object[] orderedFonts;
      (orderedFonts = new Object[5])[0] = fontArray[1];
      orderedFonts[1] = fontArray[0];
      orderedFonts[2] = fontArray[0];
      orderedFonts[3] = fontArray[1];
      orderedFonts[4] = fontArray[0];
      this.promoImages.setFonts(orderedFonts);

      try {
         InputStream stream = this.resources.openStream(52);
         byte[] productData = new byte[com.ea.sims3.ResourceLoader.getSize(52)];
         stream.read(productData);
         stream.close();
         this.promoImages.setProductData(productData);
         gcYield();
      } catch (IOException ignored2) {
      }
   }

   public final void setLanguage(int languageIndex) {
      this.language = languageIndex;
      com.ea.sims3.SdkText.setCurrentLanguage(languageIndex);
      com.ea.sims3.SdkText.loadStringsChunk(2);
      if (this.titleScreen() != null) {
         com.ea.sims3.SdkText.loadStringsChunk(1);
      }

      fillShorts(this.slotStringIds, -1);
      fillObjects(this.slotTexts, (Object) null);
      this.wrappedString = null;
      fillInts(this.marqueeStringIds, -1);
      fillObjects(this.marqueeTexts, (Object) null);
   }

   private byte[] readResource(String path) {
      byte[] data = null;
      InputStream stream = null;

      try {
         if ((stream = this.getClass().getResourceAsStream(path)) != null) {
            int read = stream.read(ioBuffer);
            int total = 0;

            for (int remaining = 2600; read > 0; read = stream.read(ioBuffer, total, remaining)) {
               total += read;
               remaining -= read;
            }

            data = new byte[total];
            System.arraycopy(ioBuffer, 0, data, 0, total);
         }
      } catch (IOException ignored) {
      }

      return data;
   }

   public final void drawStringById(int stringId, int fontIndex, int xPos, int yPos, int anchor) {
      if (stringId != -1) {
         SdkString text = this.getString(stringId);
         com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
         this.drawString(text, fontIndex, xPos, yPos, anchor);
      }
   }

   public final void drawString(SdkString text, int fontIndex, int xPos, int yPos, int anchor) {
      if (text != null) {
         com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
         com.ea.sims3.SdkText.setSpaceSpacing(5);
         if ((anchor & 2) != 0) {
            yPos -= com.ea.sims3.SdkText.getLineHeight() - com.ea.sims3.SdkText.getLeadingSpacing() >> 1;
            anchor &= -3;
         }

         com.ea.sims3.SdkText.drawString(text, xPos, yPos, anchor);
      }
   }

   public final int spaceWidth(int fontIndex) {
      com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
      return com.ea.sims3.SdkText.getSpaceSpacing();
   }

   public final int lineHeight(int fontIndex) {
      com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
      return com.ea.sims3.SdkText.getLineHeight();
   }

   public final SdkString getString(int stringId) {
      if (stringId < -2) {
         return this.formattedStrings[stringId - -12];
      } else {
         SdkString buffer = this.tempStrings[this.tempStringCursor];
         this.tempStringCursor = (this.tempStringCursor + 1) % 10;
         return stringId <= 3 ? com.ea.sims3.SdkText.getHeaderString(com.ea.sims3.SdkText.getCurrentLanguage(), stringId, buffer) : com.ea.sims3.SdkText.getString(stringId, buffer);
      }
   }

   public final int stringWidthById(int stringId, int fontIndex) {
      com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
      return com.ea.sims3.SdkText.stringWidth(this.getString(stringId));
   }

   public final int stringWidth(SdkString text, int fontIndex) {
      com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
      return com.ea.sims3.SdkText.stringWidth(text);
   }

   public static SdkString createString(String text) {
      return new SdkString(text);
   }

   public final void formatString1(int slot, int formatId, int argId) {
      SdkString format = this.getString(formatId);
      this.formattedStrings[slot - -12] = format.format(this.getString(argId));
   }

   public final void formatString2(int slot, int formatId, int argId1, int argId2) {
      SdkString format = this.getString(formatId);
      SdkString[] args;
      (args = formatArgs)[0] = this.getString(argId1);
      args[1] = this.getString(argId2);
      this.formattedStrings[slot - -12] = format.format(args);
   }

   public final void formatStringWith(int slot, int formatId, SdkString arg) {
      SdkString format = this.getString(formatId);
      this.formattedStrings[slot - -12] = format.format(arg);
   }

   public final void formatStringWith2(int slot, int formatId, SdkString argument1, SdkString argument2) {
      SdkString format = this.getString(formatId);
      SdkString[] args = formatArgs;
      formatArgs[0] = argument1;
      args[1] = argument2;
      this.formattedStrings[slot - -12] = format.format(args);
   }

   public final void formatStringWith3(int slot, int formatId, SdkString argument1, SdkString argument2, SdkString argument3) {
      SdkString format = this.getString(formatId);
      SdkString[] args = formatArgs;
      formatArgs[0] = argument1;
      args[1] = argument2;
      args[2] = argument3;
      this.formattedStrings[slot - -12] = format.format(args);
   }

   public final void setString(int slot, SdkString text) {
      this.formattedStrings[slot - -12] = text;
   }

   public final SdkString beginString() {
      this.buildStringCursor++;
      if (this.buildStringCursor >= 25) {
         this.buildStringCursor = 0;
      }

      this.buildBuffer = this.stringPool[this.buildStringCursor];
      this.buildBuffer.setLength(0);
      return this.buildBuffer;
   }

   public final void appendStringId(int stringId) {
      this.buildBuffer.append(this.getString(stringId));
   }

   public final void appendString(SdkString text) {
      this.buildBuffer.append(text);
   }

   public final void appendInt(int value) {
      SdkString builder = this.buildBuffer;
      int length = this.buildBuffer.length;
      if (value == 0) {
         builder.setLength(length + 1);
         builder.setCharAt(length, '0');
      } else {
         if (value < 0) {
            builder.setLength(length + 1);
            builder.setCharAt(length++, '-');
            value = -value;
         }

         int divisor = 1000000000;
         int digit = 0;

         for (boolean started = false; divisor > 0; divisor /= 10) {
            if ((digit = value / divisor) != 0 || started) {
               started = true;
               builder.setLength(length + 1);
               builder.setCharAt(length++, (char)(48 + digit));
            }

            value -= digit * divisor;
         }
      }
   }

   private void appendGroupedInt(int value) {
      SdkString builder = this.buildBuffer;
      int length = this.buildBuffer.length;
      if (value == 0) {
         builder.setLength(length + 1);
         builder.setCharAt(length, '0');
      } else {
         if (value < 0) {
            builder.setLength(length + 1);
            builder.setCharAt(length++, '-');
            value = -value;
         }

         int divisor = 1000000000;
         int digit = 0;

         for (boolean started = false; divisor > 0; divisor /= 10) {
            if ((digit = value / divisor) != 0 || started) {
               started = true;
               builder.setLength(length + 1);
               builder.setCharAt(length++, (char)(48 + digit));
               if (divisor == 1000000000 || divisor == 1000000 || divisor == 1000) {
                  builder.append(this.getString(31));
                  length = builder.length;
               }
            }

            value -= digit * divisor;
         }
      }
   }

   public final void formatNumber(int value) {
      int savedCursor = this.buildStringCursor;
      SdkString outer = this.buildBuffer;
      SdkString inner = this.beginString();
      this.appendGroupedInt(Math.abs(value));
      SdkString formatted = this.getString(30).format(inner);
      if (value < 0) {
         outer.append("-");
      }

      outer.append(formatted);
      this.buildBuffer = outer;
      this.buildStringCursor = savedCursor;
   }

   public final void appendTime(int seconds) {
      int minutes = seconds / 60;
      int secondsPart = seconds - minutes * 60;
      int minuteValue = minutes;
      SdkString builder = this.buildBuffer;
      int index = this.buildBuffer.length;
      char minuteTens = '0';
      if (minutes >= 20) {
         minuteTens = '2';
         minuteValue = minutes - 20;
      } else if (minutes >= 10) {
         minuteTens = '1';
         minuteValue = minutes - 10;
      }

      builder.setLength(index + 1);
      builder.setCharAt(index++, minuteTens);
      builder.setLength(index + 1);
      builder.setCharAt(index++, (char)(48 + minuteValue));
      builder.setLength(index + 1);
      builder.setCharAt(index++, ':');
      int secondTens = secondsPart / 10;
      builder.setLength(index + 2);
      builder.setCharAt(index++, (char)(48 + secondTens));
      builder.setCharAt(index, (char)(48 + (secondsPart - secondTens * 10)));
   }

   private void initTextSlots() {
      this.wrappedLineOffsets = new short[255];
      this.wrappedLineOffsets[0] = 0;
      this.wrapOffsets = new short[20][10];

      for (int slot = 0; slot < 20; slot++) {
         this.wrapOffsets[slot][0] = 0;
      }

      this.slotTexts = new SdkString[20];
      this.slotStringIds = new short[20];
   }

   public final int setWrappedString(int stringId, int fontIndex, int width) {
      com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
      if (stringId == -1) {
         return 0;
      } else {
         this.wrappedString = this.getString(stringId).copy();
         if (stringId == 89) {
            String version = this.app.getAppProperty("MIDlet-Version");
            this.wrappedString = this.wrappedString.format(version);
         }

         com.ea.sims3.SdkText.wrapText(this.wrappedString, this.wrappedLineOffsets, width, (short)124);
         return this.wrappedLineOffsets[0];
      }
   }

   public final int wrappedLineCount() {
      return this.wrappedLineOffsets[0];
   }

   public final void drawWrappedText(int fontIndex, int xPos, int yPos, int anchor) {
      com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
      com.ea.sims3.SdkText.setSpaceSpacing(5);
      com.ea.sims3.SdkText.drawWrappedString(this.wrappedString, this.wrappedLineOffsets, 1, this.wrappedLineOffsets[0], xPos, yPos, anchor);
   }

   public final void drawWrappedTextClipped(int fontIndex, int xPos, int yPos, int anchor, int firstLine, int lineCount) {
      com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
      com.ea.sims3.SdkText.setSpaceSpacing(5);
      if ((anchor & 2) != 0) {
         int lineHeight = com.ea.sims3.SdkText.getLineHeight() + com.ea.sims3.SdkText.getLeadingSpacing();
         int halfHeight;
         int centerOffset = (halfHeight = lineCount * lineHeight) >> 1;
         yPos -= centerOffset;
         anchor ^= 2;
      }

      lineCount = Math.min(this.wrappedLineOffsets[0] - firstLine, lineCount);
      com.ea.sims3.SdkText.drawWrappedString(this.wrappedString, this.wrappedLineOffsets, firstLine + 1, lineCount, xPos, yPos, anchor);
   }

   private int setSlotText(int slot, int stringId, int fontIndex, int width) {
      com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
      this.slotStringIds[slot] = (short)stringId;
      if (stringId == -1) {
         return 0;
      } else {
         this.slotTexts[slot] = this.getString(stringId).copy();
         com.ea.sims3.SdkText.wrapText(this.slotTexts[slot], this.wrapOffsets[slot], width, (short)124);
         return this.wrapOffsets[slot][0];
      }
   }

   public final void drawSlotText(int slot, int stringId, int fontIndex, int xPos, int yPos, int firstLine, int lineCount) {
      if (stringId != this.slotStringIds[slot]) {
         this.setSlotText(slot, stringId, fontIndex, xPos);
         this.slotStringIds[slot] = (short)stringId;
      }

      com.ea.sims3.SdkText.setFont(this.fonts[fontIndex]);
      com.ea.sims3.SdkText.setSpaceSpacing(5);
      com.ea.sims3.SdkText.drawWrappedString(this.slotTexts[slot], this.wrapOffsets[slot], 1, this.wrapOffsets[slot][0], yPos, firstLine, lineCount);
   }

   public final void drawTextById(Graphics gfx, int stringId, int fontIndex, int xPos, int yPos, int maxWidth, int anchor) {
      this.drawTextClipped(gfx, this.getString(stringId), fontIndex, xPos, yPos, maxWidth, anchor);
   }

   public final void drawTextClipped(Graphics gfx, SdkString text, int fontIndex, int xPos, int yPos, int maxWidth, int anchor) {
      if (this.stringWidth(text, fontIndex) <= maxWidth) {
         if ((anchor & 1) != 0) {
            xPos += maxWidth >> 1;
         }

         this.drawString(text, fontIndex, xPos, yPos, anchor);
      } else {
         int clipX = gfx.getClipX();
         int clipY = gfx.getClipY();
         int clipWidth = gfx.getClipWidth();
         int clipHeight = gfx.getClipHeight();
         int ellipsisWidth = this.stringWidthById(26, fontIndex);
         gfx.setClip(xPos, clipY, maxWidth - ellipsisWidth, clipHeight);
         this.drawString(text, fontIndex, xPos, yPos, anchor & -10 | 4);
         gfx.setClip(clipX, clipY, clipWidth, clipHeight);
         this.drawStringById(26, fontIndex, xPos + maxWidth, yPos, anchor & -6 | 8);
      }
   }

   public final void resetMarquees() {
      for (int slot = 0; slot < 2; slot++) {
         this.marqueeStringIds[slot] = -1;
         this.marqueeTexts[slot] = null;
      }
   }

   private void setMarqueeId(int slot, int fontIndex, int stringId) {
      if (slot < 2) {
         this.marqueeTimers[slot] = 0;
         this.marqueeStringIds[slot] = stringId;
         this.marqueeTexts[slot] = null;
         if (stringId != -1) {
            this.textWidths[slot] = this.stringWidthById(stringId, fontIndex);
            this.scrollOffsets[slot] = 0;
         }
      }
   }

   private void setMarqueeText(int slot, int fontIndex, SdkString text) {
      if (slot < 2) {
         this.marqueeTimers[slot] = 0;
         this.marqueeStringIds[slot] = -1;
         this.marqueeTexts[slot] = text;
         if (text != null) {
            this.textWidths[slot] = this.stringWidth(text, fontIndex);
            this.scrollOffsets[slot] = 0;
         }
      }
   }

   public final void updateMarquees(int deltaMs) {
      for (int slot = 0; slot < 2; slot++) {
         if (this.marqueeStringIds[slot] != -1 || this.marqueeTexts[slot] != null) {
            this.marqueeTimers[slot] = this.marqueeTimers[slot] + deltaMs;
            if (this.marqueeTimers[slot] > 1500) {
               long overrun = this.marqueeTimers[slot] - 1500;
               this.scrollOffsets[slot] = (int)(overrun * 20L >> 10);
               int gap = this.textWidths[slot] + 20;
               if (this.scrollOffsets[slot] >= gap) {
                  this.scrollOffsets[slot] = this.scrollOffsets[slot] - gap;
                  this.marqueeTimers[slot] = this.marqueeTimers[slot] - (gap << 10) / 20;
               }
            }
         }
      }
   }

   public final void drawMarqueeId(int slot, Graphics gfx, int stringId, int fontIndex, int xPos, int yPos, int maxWidth, int anchor) {
      if (this.marqueeStringIds[slot] != stringId || this.textWidths[slot] != this.stringWidthById(stringId, fontIndex)) {
         this.setMarqueeId(slot, fontIndex, stringId);
      }

      if (this.textWidths[slot] <= maxWidth) {
         if ((anchor & 1) != 0) {
            xPos += maxWidth >> 1;
         } else if ((anchor & 8) != 0) {
            xPos += maxWidth;
         }

         this.drawStringById(stringId, fontIndex, xPos, yPos, anchor);
      } else {
         int clipX = gfx.getClipX();
         int clipY = gfx.getClipY();
         int clipWidth = gfx.getClipWidth();
         int clipHeight = gfx.getClipHeight();
         gfx.setClip(xPos, clipY, maxWidth, clipHeight);
         anchor = anchor & -10 | 4;
         this.drawStringById(stringId, fontIndex, xPos - this.scrollOffsets[slot], yPos, anchor);
         this.drawStringById(stringId, fontIndex, xPos - this.scrollOffsets[slot] + this.textWidths[slot] + 20, yPos, anchor);
         gfx.setClip(clipX, clipY, clipWidth, clipHeight);
      }
   }

   public final void drawMarqueeText(int slot, Graphics gfx, SdkString text, int fontIndex, int xPos, int yPos, int maxWidth, int anchor) {
      if (this.marqueeTexts[slot] != text || this.textWidths[slot] != this.stringWidth(text, fontIndex)) {
         this.setMarqueeText(slot, fontIndex, text);
      }

      if (this.textWidths[slot] <= maxWidth) {
         if ((anchor & 1) != 0) {
            xPos += maxWidth >> 1;
         } else if ((anchor & 8) != 0) {
            xPos += maxWidth;
         }

         this.drawString(text, fontIndex, xPos, yPos, anchor);
      } else {
         int clipX = gfx.getClipX();
         int clipY = gfx.getClipY();
         int clipWidth = gfx.getClipWidth();
         int clipHeight = gfx.getClipHeight();
         gfx.setClip(xPos, clipY, maxWidth, clipHeight);
         anchor = anchor & -10 | 4;
         this.drawString(text, fontIndex, xPos - this.scrollOffsets[slot], yPos, anchor);
         this.drawString(text, fontIndex, xPos - this.scrollOffsets[slot] + this.textWidths[slot] + 20, yPos, anchor);
         gfx.setClip(clipX, clipY, clipWidth, clipHeight);
      }
   }

   public final void drawScrollingText(int slot, int fontIndex, int stringId, int xPos, int yPos, int anchor) {
      if (this.marqueeStringIds[slot] != fontIndex) {
         this.setMarqueeText(slot, stringId, com.ea.sims3.SdkText.getString(fontIndex, null));
         this.marqueeStringIds[slot] = fontIndex;
         this.marqueeTimers[slot] = 1500;
      }

      SdkString text = this.marqueeTexts[slot];
      int textWidth = this.stringWidth(text, stringId);
      if ((anchor & 1) != 0) {
         xPos -= textWidth >> 1;
      } else if ((anchor & 8) != 0) {
         xPos -= textWidth;
      }

      anchor = anchor & -10 | 4;
      SdkString charString;
      (charString = this.beginString()).setLength(1);
      short charCount = text.length;
      int scroll;
      if ((scroll = this.scrollOffsets[slot] << 1) > textWidth + 20) {
         this.marqueeTimers[slot] = 1500;
         this.scrollOffsets[slot] = 0;
         scroll = 0;
      }

      int waveStart;
      int waveEnd = (waveStart = scroll - 10 - 20) + 20;
      int penX = 0;

      for (int charIndex = 0; charIndex < charCount; charIndex++) {
         charString.setCharAt(0, text.charAt(charIndex));
         if (penX >= waveStart && penX <= waveEnd) {
            int phase = com.ea.sims3.FixedMath.div(penX - waveStart << 16, 1310720);
            int wave = com.ea.sims3.FixedMath.cos(com.ea.sims3.FixedMath.mul(411775, phase) - 205887) * 6;
            this.drawString(charString, stringId, xPos + penX, yPos - (wave >> 16) - 6, anchor);
         } else {
            this.drawString(charString, stringId, xPos + penX, yPos, anchor);
         }

         penX += this.stringWidth(charString, stringId);
      }
   }

   public final void fadeFromWhite() {
      this.fadeColor = 16777215;
      this.fadeStep = -1;
      this.fading = true;
      this.fadeFinished = false;
   }

   public final void fadeFromBlack() {
      this.fadeColor = 0;
      this.fadeStep = 1;
      this.fading = true;
      this.fadeFinished = false;
   }

   public final void clearFade() {
      this.fadeColor = 16777215;
      this.fadeStep = -1;
      this.fading = false;
      this.fadeFinished = true;
   }

   public final boolean isFading() {
      return this.fading && !this.fadeFinished;
   }

   public final boolean isFadingFromWhite() {
      return this.isFading() && this.fadeStep == -1;
   }

   private void drawFade(Graphics gfx) {
      this.fade.setViewport(0, 0, this.getWidth(), this.getHeight());
      this.fade.setBlending(66);
      this.fade.setColor(this.fadeColor);
      this.fade.render(gfx);
   }

   public final void drawFadeRect(Graphics gfx, int blending, int color, int xPos, int yPos, int width, int height) {
      this.fade.setBlending(blending);
      this.fade.setViewport(xPos, yPos, width, height);
      this.fade.setColor(color);
      this.fade.render(gfx);
   }

   public final void drawDialogOverlay(Graphics gfx) {
      this.fade.setViewport(0, 0, this.getWidth(), this.getHeight() - 15);
      this.fade.setBlending(66);
      this.fade.setColor(6316128);
      this.fade.render(gfx);
   }

   public static final void gcYield() {
      System.gc();
      Thread.yield();

      try {
         Thread.sleep(15L);
      } catch (InterruptedException ignored) {
      }
   }

   private void setGcPending(boolean pending) {
      if (pending) {
         gcYield();
         this.gcPending = false;
      } else {
         this.gcPending = true;
      }
   }

   public final GameScreen gameScreen() {
      return this.screen instanceof GameScreen ? (GameScreen)this.screen : null;
   }

   public final TitleScreen titleScreen() {
      return this.screen instanceof TitleScreen ? (TitleScreen)this.screen : null;
   }

   public final void setPendingScreen(int action, int languageIndex) {
      this.pendingScreenAction = action;
      this.pendingLanguage = languageIndex;
   }

   private void changeScreen(int action, int languageIndex) {
      try {
         boolean sameGameScreen = this.gameScreen() != null && action == 2;
         this.loadingTipId = -1;
         if (this.screen != null) {
            this.screen.pause();
         }

         this.clearFade();
         if (!sameGameScreen) {
            this.screen = null;
         }

         this.setGcPending(true);
         if (!sameGameScreen) {
            switch (action) {
               case 1:
                  this.screen = new TitleScreen(this);
                  break;
               case 2:
                  this.screen = new GameScreen(this);
            }
         }

         this.screen.setLanguage(languageIndex);
      } catch (Exception ignored) {
      }

      this.clearActions();
      this.releaseSoftKeys();
      this.repaint();
   }

   public final int randomRange(int min, int max) {
      int range = max + 1 - min;
      return min + this.random.nextInt(range);
   }

   public final int randomPercent() {
      return this.randomRange(0, 99);
   }

   public final void vibrateShort() {
      this.vibrate(400);
   }

   private void vibrate(int durationMs) {
      if (this.vibrationEnabled) {
         this.sound.vibrate(durationMs);
      }
   }

   private void trackCheatSequence(int action) {
      for (int sequenceIndex = 0; sequenceIndex < cheatSequences.length; sequenceIndex++) {
         int[] sequence;
         int lastIndex = (sequence = cheatSequences[sequenceIndex]).length - 1;
         int cursor = sequence[0];
         int expected;
         if ((expected = sequence[cursor + 1]) == action) {
            if (++cursor == lastIndex) {
               cursor = 0;
               switch (sequenceIndex) {
                  case 0:
                     this.cheatToggled = !this.cheatToggled;
               }
            }
         } else {
            cursor = 0;
         }

         sequence[0] = cursor;
      }
   }

   private static int ease(int kind, int from, int toValue, long elapsed, long duration) {
      if (elapsed >= duration) {
         return toValue;
      } else if (elapsed < 0L) {
         return from;
      } else {
         int value = 0;
         switch (kind) {
            case 0:
            default:
               return (int)(from + elapsed * (toValue - from) / duration);
            case 1:
               int half = (int)(duration >> 1);
               if (elapsed < half) {
                  value = com.ea.sims3.FixedMath.mul(com.ea.sims3.FixedMath.sinShifted(com.ea.sims3.FixedMath.div(com.ea.sims3.FixedMath.mul((int)elapsed, 102944), half)), 72089);
               } else {
                  int progress;
                  int angle = com.ea.sims3.FixedMath.mul(progress = com.ea.sims3.FixedMath.div((int)elapsed - half, (int)duration - half), 617662);
                  int amplitude = 6554 - com.ea.sims3.FixedMath.mul(6554, progress);
                  value = com.ea.sims3.FixedMath.mul(com.ea.sims3.FixedMath.cos(angle), amplitude) + 65536;
               }
               break;
            case 2:
               value = com.ea.sims3.FixedMath.smoothStep(0, (int)duration, (int)elapsed);
         }

         return from + (value * (toValue - from) >> 16);
      }
   }

   private void startSoftKeyAnimation(int action) {
      if (!this.isFading() && action != 0 && !this.leftSoftKeyAnimator.isRunning()) {
         if (action == this.leftSoftKeyAction) {
            this.releaseSoftKeys();
            this.rightSoftKeySelected = false;
            this.leftSoftKeyAnimator.play(675, 16);
            this.leftSoftKeyAnimator.frame = 1;
            this.rightSoftKeyAnimator.play(677, 16);
            this.rightSoftKeyAnimator.frame = 1;
         } else {
            if (action == this.rightSoftKeyAction) {
               this.releaseSoftKeys();
               this.rightSoftKeySelected = true;
               this.leftSoftKeyAnimator.play(675, 16);
               this.leftSoftKeyAnimator.frame = 1;
               this.rightSoftKeyAnimator.play(677, 16);
               this.rightSoftKeyAnimator.frame = 1;
            }
         }
      }
   }

   private void updateSoftKeyAnimation(int deltaMs) {
      if (this.isFading()) {
         this.leftSoftKeyTimer = 0L;
         this.rightSoftKeyTimer = 0L;
      } else {
         if (this.leftSoftKeyTimer < 400L) {
            this.leftSoftKeyTimer += deltaMs;
         }

         if (this.rightSoftKeyTimer < 400L) {
            this.rightSoftKeyTimer += deltaMs;
         }
      }

      if (this.leftSoftKeyAnimator.isRunning()) {
         this.leftSoftKeyAnimator.advance(deltaMs);
         this.rightSoftKeyAnimator.advance(deltaMs);
         if (!this.leftSoftKeyAnimator.isRunning()) {
            int action = this.rightSoftKeySelected ? this.rightSoftKeyAction : this.leftSoftKeyAction;
            if (this.screen != null) {
               this.screen.update(0, action);
            }
         }
      }
   }

   public final void drawBottomBar(Graphics gfx) {
      this.drawBottomBar(gfx, 0);
   }

   public final void drawBottomBar(Graphics gfx, int yOffset) {
      int height = this.getHeight();
      int width = this.getWidth();
      int frameWidth = com.ea.sims3.SpriteRenderer.frameWidth(674, 0);

      for (int xPos = 0; xPos < width; xPos += frameWidth) {
         com.ea.sims3.SpriteRenderer.drawFrame(gfx, 674, 0, xPos, height + yOffset);
      }

      if (!this.isFading()) {
         this.drawSoftKeys(gfx, yOffset);
      }
   }

   private void drawSoftKeys(Graphics gfx, int yOffset) {
      int leftLabelId = actionToLabelId(this.leftSoftKeyAction);
      int rightLabelId = actionToLabelId(this.rightSoftKeyAction);
      if (landscape) {
         if (leftLabelId >= 0) {
            SdkString leftTextWide = this.getString(leftLabelId);
            int leftAnimOffsetWide = ease(1, 15, 0, this.rightSoftKeyTimer, 400L);
            int leftYWide;
            int leftTextYWide = (leftYWide = this.getHeight() + leftAnimOffsetWide + yOffset) - 7;
            int leftWidthWide = Math.max(25, this.stringWidth(leftTextWide, 0));
            int rightXWide;
            int leftXWide;
            int centerXWide = (leftXWide = (rightXWide = this.getWidth() - 11) - leftWidthWide + 4) + rightXWide >> 1;
            if (this.leftSoftKeyAnimator.isRunning() && this.rightSoftKeySelected) {
               com.ea.sims3.SpriteRenderer.drawAnimator(gfx, this.leftSoftKeyAnimator, leftXWide, leftYWide);
               com.ea.sims3.SpriteRenderer.drawAnimator(gfx, this.rightSoftKeyAnimator, rightXWide, leftYWide);
               drawDivider(gfx, leftXWide + 2, leftYWide, rightXWide - leftXWide - 4, true);
               this.drawString(leftTextWide, 0, centerXWide, leftTextYWide, 3);
            } else {
               com.ea.sims3.SpriteRenderer.drawFrame(gfx, 677, 0, rightXWide, leftYWide);
               com.ea.sims3.SpriteRenderer.drawFrame(gfx, 675, 0, leftXWide, leftYWide);
               drawDivider(gfx, leftXWide + 2, leftYWide, rightXWide - leftXWide - 4, false);
               this.drawString(leftTextWide, 0, centerXWide, leftTextYWide, 3);
            }
         }

         if (rightLabelId >= 0) {
            SdkString rightTextWide = this.getString(rightLabelId);
            int rightAnimOffsetWide = ease(1, 15, 0, this.rightSoftKeyTimer, 400L);
            int rightYWide;
            int rightTextYWide = (rightYWide = 15 + rightAnimOffsetWide + yOffset) - 7;
            int rightWidthWide = Math.max(25, this.stringWidth(rightTextWide, 0));
            int rightRightXWide;
            int rightLeftXWide;
            int rightCenterXWide = (rightLeftXWide = (rightRightXWide = this.getWidth() - 11) - rightWidthWide + 4) + rightRightXWide >> 1;
            if (this.leftSoftKeyAnimator.isRunning() && this.rightSoftKeySelected) {
               com.ea.sims3.SpriteRenderer.drawAnimator(gfx, this.leftSoftKeyAnimator, rightLeftXWide, rightYWide);
               com.ea.sims3.SpriteRenderer.drawAnimator(gfx, this.rightSoftKeyAnimator, rightRightXWide, rightYWide);
               drawDivider(gfx, rightLeftXWide + 2, rightYWide, rightRightXWide - rightLeftXWide - 4, true);
               this.drawString(rightTextWide, 0, rightCenterXWide, rightTextYWide, 3);
               return;
            }

            com.ea.sims3.SpriteRenderer.drawFrame(gfx, 677, 0, rightRightXWide, rightYWide);
            com.ea.sims3.SpriteRenderer.drawFrame(gfx, 675, 0, rightLeftXWide, rightYWide);
            drawDivider(gfx, rightLeftXWide + 2, rightYWide, rightRightXWide - rightLeftXWide - 4, false);
            this.drawString(rightTextWide, 0, rightCenterXWide, rightTextYWide, 3);
         }
      } else {
         if (leftLabelId >= 0) {
            SdkString leftText = this.getString(leftLabelId);
            int leftAnimOffset = ease(1, 15, 0, this.leftSoftKeyTimer, 400L);
            int leftY;
            int leftTextY = (leftY = this.getHeight() + leftAnimOffset + yOffset) - 7;
            int leftWidth = Math.max(25, this.stringWidth(leftText, 0));
            int rightX = 11 + leftWidth - 4;
            int leftCenterX = 11 + rightX >> 1;
            if (this.leftSoftKeyAnimator.isRunning() && !this.rightSoftKeySelected) {
               com.ea.sims3.SpriteRenderer.drawAnimator(gfx, this.leftSoftKeyAnimator, 11, leftY);
               com.ea.sims3.SpriteRenderer.drawAnimator(gfx, this.rightSoftKeyAnimator, rightX, leftY);
               drawDivider(gfx, 13, leftY, rightX - 11 - 4, true);
               this.drawString(leftText, 0, leftCenterX, leftTextY, 3);
            } else {
               com.ea.sims3.SpriteRenderer.drawFrame(gfx, 677, 0, rightX, leftY);
               com.ea.sims3.SpriteRenderer.drawFrame(gfx, 675, 0, 11, leftY);
               drawDivider(gfx, 13, leftY, rightX - 11 - 4, false);
               this.drawString(leftText, 0, leftCenterX, leftTextY, 3);
            }
         }

         if (rightLabelId >= 0) {
            SdkString rightText = this.getString(rightLabelId);
            int rightAnimOffset = ease(1, 15, 0, this.rightSoftKeyTimer, 400L);
            int rightY;
            int rightTextY = (rightY = this.getHeight() + rightAnimOffset + yOffset) - 7;
            int rightWidth = Math.max(25, this.stringWidth(rightText, 0));
            int rightLimit;
            int rightRightX;
            int rightCenterX = (rightRightX = (rightLimit = this.getWidth() - 11) - rightWidth + 4) + rightLimit >> 1;
            if (!this.leftSoftKeyAnimator.isRunning() || !this.rightSoftKeySelected) {
               com.ea.sims3.SpriteRenderer.drawFrame(gfx, 677, 0, rightLimit, rightY);
               com.ea.sims3.SpriteRenderer.drawFrame(gfx, 675, 0, rightRightX, rightY);
               drawDivider(gfx, rightRightX + 2, rightY, rightLimit - rightRightX - 4, false);
               this.drawString(rightText, 0, rightCenterX, rightTextY, 3);
               return;
            }

            com.ea.sims3.SpriteRenderer.drawAnimator(gfx, this.leftSoftKeyAnimator, rightRightX, rightY);
            com.ea.sims3.SpriteRenderer.drawAnimator(gfx, this.rightSoftKeyAnimator, rightLimit, rightY);
            drawDivider(gfx, rightRightX + 2, rightY, rightLimit - rightRightX - 4, true);
            this.drawString(rightText, 0, rightCenterX, rightTextY, 3);
         }
      }
   }

   private static void drawDivider(Graphics gfx, int xPos, int yPos, int width, boolean active) {
      int frame = active ? 1 : 0;
      int clipX = gfx.getClipX();
      int clipY = gfx.getClipY();
      int clipWidth = gfx.getClipWidth();
      int clipHeight = gfx.getClipHeight();
      gfx.setClip(xPos, clipY, width, clipHeight);
      com.ea.sims3.SpriteRenderer.drawFrame(gfx, 676, frame, xPos, yPos);
      gfx.setClip(clipX, clipY, clipWidth, clipHeight);
   }

   public final void resetTileLoad() {
      this.tileLoadCursor = 0;
   }

   public final boolean loadTilesFor(int mask) {
      return this.loadNextTileGroup(mask, null, null, 0);
   }

   public final boolean loadNextTileGroup(int mask, int[] recolorFrom, int[] recolorTo, int recolorIndex) {
      ResourceLoader loader = this.resources;
      int loaded = 0;

      int index;
      for (index = this.tileLoadCursor; index << 1 < this.tileMasks.length && loaded < 2; index++) {
         if ((this.tileMasks[(index << 1) + 1] & mask) != 0) {
            int attempt = 1;

            while (!com.ea.sims3.SpriteRenderer.loadTileRecolored(loader, this.tileMasks[index << 1], recolorFrom, recolorTo, recolorIndex) && attempt < 2) {
               attempt++;
               gcYield();
            }

            if (attempt >= 2) {
               throw new Error("OOM");
            }

            loaded++;
         }
      }

      this.tileLoadCursor = index;
      return index << 1 == this.tileMasks.length;
   }

   public final void loadTilesBlocking(int mask) {
      this.loadTiles(mask, null, null, 0);
   }

   public final void loadTiles(int mask, int[] recolorFrom, int[] recolorTo, int recolorIndex) {
      this.resetTileLoad();

      while (!this.loadNextTileGroup(mask, recolorFrom, recolorTo, recolorIndex)) {
      }
   }

   public final void unloadTiles(int mask, int variant) {
      int[] masks = this.tileMasks;

      for (int index = 0; index << 1 < masks.length; index++) {
         if ((masks[(index << 1) + 1] & mask) != 0) {
            int tileId = masks[index << 1];
            if (variant == -1) {
               for (int variantIndex = 0; variantIndex < 4; variantIndex++) {
                  com.ea.sims3.SpriteRenderer.unloadTile(tileId, variantIndex);
               }
            } else {
               com.ea.sims3.SpriteRenderer.unloadTile(tileId, variant);
            }
         }
      }

      gcYield();
   }

   public static final void fillObjects(Object[] array, Object value) {
      for (int index = 0; index < array.length; index++) {
         array[index] = value;
      }
   }

   public static final void fillBytes(byte[] array, int value) {
      for (int index = 0; index < array.length; index++) {
         array[index] = (byte)value;
      }
   }

   public static final void fillShorts(short[] array, int value) {
      for (int index = 0; index < array.length; index++) {
         array[index] = (short)value;
      }
   }

   public static final void fillInts(int[] array, int value) {
      for (int index = 0; index < array.length; index++) {
         array[index] = value;
      }
   }

   public static final void fillIntMatrix(int[][] matrix, int value) {
      for (int row = 0; row < matrix.length; row++) {
         fillInts(matrix[row], value);
      }
   }

   public static final void fillShortMatrix(short[][] matrix, int value) {
      for (int row = 0; row < matrix.length; row++) {
         fillShorts(matrix[row], value);
      }
   }

   public static final int indexOfInt(int value, int[] array) {
      return indexOfIntFrom(value, array, 0);
   }

   public static final int indexOfShort(int value, short[] array) {
      return indexOfShortFrom(value, array, 0);
   }

   public static final int indexOfByte(int value, byte[] array) {
      return indexOfByteFrom(value, array, 0);
   }

   public static final int indexOfObject(Object target, Object[] array) {
      return indexOfObjectFrom(target, array, 0);
   }

   private static int indexOfIntFrom(int value, int[] array, int from) {
      for (int index = from; index < array.length; index++) {
         if (array[index] == value) {
            return index;
         }
      }

      return -1;
   }

   public static final int indexOfShortFrom(int value, short[] array, int from) {
      for (int index = from; index < array.length; index++) {
         if (array[index] == value) {
            return index;
         }
      }

      return -1;
   }

   public static final int indexOfByteFrom(int value, byte[] array, int from) {
      for (int index = from; index < array.length; index++) {
         if (array[index] == value) {
            return index;
         }
      }

      return -1;
   }

   private static int indexOfObjectFrom(Object target, Object[] array, int from) {
      for (int index = from; index < array.length; index++) {
         if (array[index] == target) {
            return index;
         }
      }

      return -1;
   }

   public static final int indexOfAnyFlag(int mask, short[] array) {
      return indexOfAnyFlagFrom(mask, array, 0);
   }

   private static int indexOfAnyFlagFrom(int mask, short[] array, int from) {
      for (int index = from; index < array.length; index++) {
         if ((array[index] & mask) != 0) {
            return index;
         }
      }

      return -1;
   }

   public static final int nthIndexNotEqualByte(int occurrence, byte[] array, int excluded) {
      int count = 0;

      for (int index = 0; index < array.length; index++) {
         if (array[index] != excluded && count++ == occurrence) {
            return index;
         }
      }

      return -1;
   }

   public static final int nthIndexNotEqualShort(int occurrence, short[] array, int excluded) {
      int count = 0;

      for (int index = 0; index < array.length; index++) {
         if (array[index] != excluded && count++ == occurrence) {
            return index;
         }
      }

      return -1;
   }

   public static final int nthIndexWithFlag(int occurrence, short[] array, int mask) {
      int count = 0;

      for (int index = 0; index < array.length; index++) {
         if ((array[index] & mask) != 0 && count++ == occurrence) {
            return index;
         }
      }

      return -1;
   }

   public static final int nthIndexWithFlagInt(int occurrence, int[] array, int mask) {
      int count = 0;

      for (int index = 0; index < array.length; index++) {
         if ((array[index] & mask) != 0 && count++ == occurrence) {
            return index;
         }
      }

      return -1;
   }

   public static final int countEqualShorts(int value, short[] array) {
      int count = 0;

      for (int index = 0; index < array.length; index++) {
         if (array[index] == value) {
            count++;
         }
      }

      return count;
   }

   public static final int countEqualBytes(int value, byte[] array) {
      int count = 0;

      for (int index = 0; index < array.length; index++) {
         if (array[index] == value) {
            count++;
         }
      }

      return count;
   }

   public static final int countAnyFlagInt(int mask, int[] array) {
      return countAnyFlagIntFrom(mask, array, 0);
   }

   private static int countAnyFlagIntFrom(int mask, int[] array, int from) {
      int count = 0;

      for (int index = from; index < array.length; index++) {
         if ((array[index] & mask) != 0) {
            count++;
         }
      }

      return count;
   }

   public static final int countAllFlags(int mask, short[] array) {
      return countAllFlagsFrom(mask, array, 0);
   }

   public static final int countAllFlagsFrom(int mask, short[] array, int from) {
      int count = 0;

      for (int index = from; index < array.length; index++) {
         if ((array[index] & mask) != 0) {
            count++;
         }
      }

      return count;
   }

   public static void initList(short[] list, int value) {
      initListWith(list, value, -1);
   }

   public static void initListWith(short[] list, int value, int extra) {
      list[1] = (short)value;
      list[2] = 0;
      list[3] = 0;
      list[4] = (short)extra;
      list[0] = 0;
   }

   public static void addToList(short[] list, int value) {
      short count = list[0];
      list[5 + count] = (short)value;
      list[0] = (short)(count + 1);
   }

   public static boolean listContains(short[] list, int value) {
      int end = list[0] + 5;

      for (int index = 5; index < end; index++) {
         if (value == list[index]) {
            return true;
         }
      }

      return false;
   }

   public static void copyList(short[] target, short[] source) {
      System.arraycopy(source, 0, target, 0, 5 + source[0]);
   }

   public static void clampListIndex(short[] list) {
      short maxValue;
      if ((maxValue = list[4]) > 0) {
         short value = list[2];
         int start = list[3];
         if (value < start) {
            start = value;
         } else if (value >= start + maxValue) {
            start = Math.max(0, value - maxValue + 1);
         }

         list[3] = (short)start;
      }
   }

   public final boolean navigateLeftRight(short[] list, int actions) {
      short count = list[0];
      short index = list[2];
      if ((actions & 8196) != 0) {
         this.clearAction(8196);
         list[2] = (short)((index + count - 1) % count);
      }

      if ((actions & 16640) != 0) {
         this.clearAction(16640);
         list[2] = (short)((index + 1) % count);
      }

      if (list[2] != index) {
         clampListIndex(list);
         if (this.gameScreen() != null) {
            this.sound.pendingSound = 0;
         }

         return true;
      } else {
         return false;
      }
   }

   public final boolean navigateUpDown(short[] list, int actions) {
      short count = list[0];
      short index = list[2];
      if ((actions & 32784) != 0) {
         this.clearAction(32784);
         list[2] = (short)((index + count - 1) % count);
      }

      if ((actions & 65600) != 0) {
         this.clearAction(65600);
         list[2] = (short)((index + 1) % count);
      }

      if (list[2] != index) {
         clampListIndex(list);
         this.resetMarquees();
         if (this.gameScreen() != null) {
            this.sound.pendingSound = 1;
         }

         return true;
      } else {
         return false;
      }
   }

   public static void selectListValue(short[] list, int value) {
      for (int index = 5; index < list.length; index++) {
         if (list[index] == value) {
            list[2] = (short)(index - 5);
            return;
         }
      }
   }

   private void resetSettings() {
      this.vibrationEnabled = true;
      this.tutorialsEnabled = true;
      this.autonomousEnabled = true;
      this.savePromptsEnabled = true;
      this.gameStarted = false;
      this.flagsLow = 0L;
      this.flagsHigh = 0L;
      this.visitedFlagsLow = 0L;
      this.visitedFlagsHigh = 0L;
      this.language = -1;
      this.resetProfiles();
   }

   private void loadSettings() {
      RecordStore store = null;
      ByteArrayInputStream bytes = null;
      DataInputStream input = null;

      try {
         byte[] data = (store = RecordStore.openRecordStore("sim3set", false)).getRecord(1);
         bytes = new ByteArrayInputStream(data);
         int version = (input = new DataInputStream(bytes)).readInt();
         int format = input.readInt();
         if (version == 86 && format == 2) {
            this.vibrationEnabled = input.readBoolean();
            this.tutorialsEnabled = input.readBoolean();
            this.autonomousEnabled = input.readBoolean();
            this.savePromptsEnabled = input.readBoolean();
            this.gameStarted = input.readBoolean();
            this.flagsLow = input.readLong();
            this.flagsHigh = input.readLong();
            this.visitedFlagsLow = input.readLong();
            this.visitedFlagsHigh = input.readLong();
            this.language = input.readByte();

            for (int slot = 0; slot != 2; slot++) {
               boolean done = false;
               SdkString name = this.profileNames[slot];

               for (int charIndex = 0; charIndex < 8; charIndex++) {
                  char character;
                  if ((character = input.readChar()) == ' ') {
                     done = true;
                  } else if (!done) {
                     name.setLength(charIndex + 1);
                     name.setCharAt(charIndex, character);
                  }
               }

               if (!done) {
                  name.setLength(8);
               }

               this.profileUsed[slot] = input.readBoolean();
            }
         } else {
            this.resetSettings();
            this.saveSettings();
         }
      } catch (RecordStoreNotFoundException notFound) {
         this.resetSettings();
      } catch (Exception ignored) {
      }

      try {
         input.close();
         bytes.close();
         if (store != null) {
            store.closeRecordStore();
         }
      } catch (Exception closeError) {
      }
   }

   public final boolean saveSettings() {
      try {
         RecordStore.deleteRecordStore("sim3set");
      } catch (Exception deleteError) {
      }

      boolean failed = false;
      RecordStore store = null;
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(bytes);

      try {
         out.writeInt(86);
         out.writeInt(2);
         out.writeBoolean(this.vibrationEnabled);
         out.writeBoolean(this.tutorialsEnabled);
         out.writeBoolean(this.autonomousEnabled);
         out.writeBoolean(this.savePromptsEnabled);
         out.writeBoolean(this.gameStarted);
         out.writeLong(this.flagsLow);
         out.writeLong(this.flagsHigh);
         out.writeLong(this.visitedFlagsLow);
         out.writeLong(this.visitedFlagsHigh);
         out.writeByte(this.language);

         for (int slot = 0; slot != 2; slot++) {
            SdkString name = this.profileNames[slot];

            for (int charIndex = 0; charIndex != 8; charIndex++) {
               char character = ' ';
               if (charIndex < name.length) {
                  character = name.charAt(charIndex);
               }

               out.writeChar(character);
            }

            out.writeBoolean(this.profileUsed[slot]);
         }

         byte[] data = bytes.toByteArray();
         if ((store = RecordStore.openRecordStore("sim3set", true)).getNumRecords() == 0) {
            store.addRecord(data, 0, data.length);
         } else {
            store.setRecord(1, data, 0, data.length);
         }
      } catch (Exception writeError) {
         failed = true;
      }

      try {
         out.close();
         bytes.close();
         store.closeRecordStore();
      } catch (Exception closeError) {
         failed = true;
      }

      return !failed;
   }

   public final void resetGameData() {
      this.progressFlags = 0;
      this.clearNewFlags();
      this.neighborhood.resetAllState();
      this.lot.resetStages();
   }

   private void loadGameData() {
      RecordStore store = null;
      ByteArrayInputStream bytes = null;
      DataInputStream input = null;

      try {
         byte[] data = (store = RecordStore.openRecordStore("sim3dat" + this.profileSlot, false)).getRecord(1);
         bytes = new ByteArrayInputStream(data);
         if ((input = new DataInputStream(bytes)).readInt() == 86) {
            this.progressFlags = input.readInt();
            this.neighborhood.load(input);
            this.lot.readObjects(input);
            this.houseIndex = 0;
         } else {
            this.resetGameData();
            this.saveGameData();
         }
      } catch (Exception loadError) {
         this.resetGameData();
         this.saveGameData();
      }

      try {
         input.close();
         bytes.close();
         if (store != null) {
            store.closeRecordStore();
         }
      } catch (Exception closeError) {
      }
   }

   public final boolean saveGameData() {
      String recordName = "sim3dat" + this.profileSlot;

      try {
         RecordStore.deleteRecordStore(recordName);
      } catch (Exception deleteError) {
      }

      this.flagsLow = this.flagsLow | this.newFlagsLow;
      this.flagsHigh = this.flagsHigh | this.newFlagsHigh;
      this.visitedFlagsLow = this.visitedFlagsLow | this.newVisitedFlagsLow;
      this.visitedFlagsHigh = this.visitedFlagsHigh | this.newVisitedFlagsHigh;
      this.saveSettings();
      boolean failed = false;
      RecordStore store = null;
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(bytes);

      try {
         out.writeInt(86);
         out.writeInt(this.progressFlags);
         this.neighborhood.save(out);
         this.lot.writeObjects(out);
         byte[] data = bytes.toByteArray();
         if ((store = RecordStore.openRecordStore(recordName, true)).getNumRecords() == 0) {
            store.addRecord(data, 0, data.length);
         } else {
            store.setRecord(1, data, 0, data.length);
         }
      } catch (Exception writeError) {
         failed = true;
      }

      try {
         out.close();
         bytes.close();
         if (store != null) {
            store.closeRecordStore();
         }
      } catch (Exception closeError) {
         failed = true;
      }

      return !failed;
   }

   public final int optionStringId(int optionId) {
      if (optionId == 79) {
         return this.sound.enabled ? 87 : 88;
      } else if (optionId == 80) {
         return this.vibrationEnabled ? 87 : 88;
      } else if (optionId == 81) {
         return this.tutorialsEnabled ? 87 : 88;
      } else if (optionId == 82) {
         return this.autonomousEnabled ? 87 : 88;
      } else if (optionId == 83) {
         return this.savePromptsEnabled ? 87 : 88;
      } else {
         return -1;
      }
   }

   public final int profileNameLength() {
      return this.profileNames[this.profileSlot].length;
   }

   public final void clearProfileName() {
      SdkString name;
      (name = this.profileNames[this.profileSlot]).setLength(0);
   }

   public final void setProfileNameChar(int index, char character) {
      this.profileNames[this.profileSlot].setCharAt(index, character);
   }

   public final void insertProfileNameChar(int index, char character) {
      SdkString name;
      int length = (name = this.profileNames[this.profileSlot]).length;
      name.setLength(name.length + 1);

      while (index < length) {
         char moved = name.charAt(length - 1);
         name.setCharAt(length, moved);
         length--;
      }

      name.setCharAt(index, character);
   }

   public final void deleteProfileNameChar(int index) {
      SdkString name;
      int lastIndex = (name = this.profileNames[this.profileSlot]).length - 1;

      while (index != lastIndex) {
         char moved = name.charAt(index + 1);
         name.setCharAt(index, moved);
         index++;
      }

      name.setCharAt(index, ' ');
      name.setLength(lastIndex);
   }

   public final void selectProfile(int slot) {
      this.profileSlot = slot;
      if (this.isProfileUsed()) {
         this.loadGameData();
      }
   }

   public final SdkString profileName() {
      return this.profileNames[this.profileSlot];
   }

   public final SdkString profileNameAt(int slot) {
      return this.profileNames[slot];
   }

   public final boolean isProfileUsed() {
      return this.profileUsed[this.profileSlot];
   }

   public final boolean isProfileUsedAt(int slot) {
      return this.profileUsed[slot];
   }

   private void resetProfiles() {
      for (int slot = 0; slot < 2; slot++) {
         this.clearProfile(slot);
      }
   }

   public final void clearProfile(int slot) {
      this.profileUsed[slot] = false;
   }

   public final void commitProfile() {
      this.profileUsed[this.profileSlot] = true;
      this.progressFlags = 0;
      this.saveSettings();
      this.neighborhood.completeAllTips();
      this.saveGameData();
   }

   public final void reloadGameData() {
      this.loadGameData();
      this.houseIndex = 0;
   }

   public final boolean hasProgressFlag(int flag) {
      return (1 << flag & this.progressFlags) != 0;
   }

   public final void setProgressFlag(int flag) {
      this.progressFlags |= 1 << flag;
   }

   public final void markGameStarted() {
      this.gameStarted = true;
      this.saveSettings();
   }

   private void resetProgressFlags() {
      this.gameStarted = false;
      this.flagsLow = 0L;
      this.flagsHigh = 0L;
      this.visitedFlagsLow = 0L;
      this.visitedFlagsHigh = 0L;
   }

   public final void clearNewFlags() {
      this.newFlagsLow = 0L;
      this.newFlagsHigh = 0L;
      this.newVisitedFlagsLow = 0L;
      this.newVisitedFlagsHigh = 0L;
   }

   public final boolean hasFlag(int flag) {
      if (flag < 64) {
         long lowBit = 1L << flag;
         return ((this.flagsLow | this.newFlagsLow) & lowBit) != 0L;
      } else {
         long highBit = 1L << flag - 64;
         return ((this.flagsHigh | this.newFlagsHigh) & highBit) != 0L;
      }
   }

   public final void addFlag(int flag) {
      if (flag < 64) {
         long lowBit = 1L << flag;
         this.newFlagsLow |= lowBit;
      } else {
         long highBit = 1L << flag - 64;
         this.newFlagsHigh |= highBit;
      }
   }

   public final int flagCount() {
      long low = this.flagsLow | this.newFlagsLow;
      long high = this.flagsHigh | this.newFlagsHigh;
      int count = 0;

      for (int bit = 0; bit != 64; bit++) {
         count = (int)((int)(count + (low >> bit & 1L)) + (high >> bit & 1L));
      }

      return count;
   }

   public final boolean hasVisitedFlag(int flag) {
      if (flag < 64) {
         long lowBit = 1L << flag;
         return ((this.visitedFlagsLow | this.newVisitedFlagsLow) & lowBit) != 0L;
      } else {
         long highBit = 1L << flag - 64;
         return ((this.visitedFlagsHigh | this.newVisitedFlagsHigh) & highBit) != 0L;
      }
   }

   public final void addVisitedFlag(int flag) {
      this.addFlag(flag);
      if (flag < 64) {
         long lowBit = 1L << flag;
         this.newVisitedFlagsLow |= lowBit;
      } else {
         long highBit = 1L << flag - 64;
         this.newVisitedFlagsHigh |= highBit;
      }
   }

   public final int visitedFlagCount() {
      long low = this.visitedFlagsLow | this.newVisitedFlagsLow;
      long high = this.visitedFlagsHigh | this.newVisitedFlagsHigh;
      int count = 0;

      for (int bit = 0; bit != 64; bit++) {
         count = (int)((int)(count + (low >> bit & 1L)) + (high >> bit & 1L));
      }

      return count;
   }

   public final void updateLoadingTip(int deltaMs) {
      if (this.loadingTipId == -1) {
         this.loadingTipId = this.randomRange(920, 955);
         this.loadingTipX = this.getWidth() << 16;
         this.loadingTipWidth = this.stringWidthById(this.loadingTipId, 0);
      } else {
         this.loadingTipX -= deltaMs * 4915;
         if (this.loadingTipX < -(this.loadingTipWidth << 16)) {
            this.loadingTipId = -1;
         }
      }
   }

   public final void drawLoadingScreen(Graphics gfx) {
      com.ea.sims3.SpriteRenderer.setColor(gfx, 77);
      gfx.fillRect(0, 0, this.getWidth(), this.getHeight());
      this.screen.drawTextBlock(gfx, 0, 0, 0, this.getWidth(), this.getHeight());
      if (this.loadingTipId != -1) {
         int yPos = this.getHeight() - 40;
         this.drawStringById(this.loadingTipId, 0, this.loadingTipX >> 16, yPos, 20);
      }
   }

   private void buildMenus() {
      this.menuRowSpacing = (short)((this.getHeight() - 15 - 22) / 15);
      short[] items;
      initListWith(items = new short[12], 60, this.menuRowSpacing);
      addToList(items, 79);
      addToList(items, 80);
      addToList(items, 81);
      addToList(items, 82);
      addToList(items, 83);
      this.compactOptionsMenu = new short[items[0] + 5];
      copyList(this.compactOptionsMenu, items);
      addToList(items, 3);
      addToList(items, 85);
      this.optionsMenu = items;
      initListWith(items = new short[12], 61, this.menuRowSpacing);
      addToList(items, 62);
      addToList(items, 90);
      addToList(items, 94);
      addToList(items, 96);
      addToList(items, 98);
      addToList(items, 100);
      addToList(items, 105);
      this.helpMenu = items;
      this.menuMode = -1;
   }

   public final void setMenuMode(int mode) {
      short[] items = null;
      byte argMode = -1;
      char leftAction = 4;
      short rightAction = 16;
      switch (mode) {
         case -1:
         default:
            break;
         case 0:
            items = this.optionsMenu;
            break;
         case 1:
            items = this.compactOptionsMenu;
            break;
         case 2:
            rightAction = 256;
            leftAction = 512;
            argMode = 0;
            this.screen.showInfoPanel(86, 85);
            break;
         case 3:
            items = this.helpMenu;
            break;
         case 4:
            rightAction = 0;
            argMode = 3;
            break;
         case 5:
            leftAction = '\u8000';
            rightAction = 16384;
      }

      if (this.menuMode == -1 && items != null) {
         items[2] = 0;
      }

      this.currentMenu = items;
      this.returnMenuMode = argMode;
      this.menuMode = mode;
      this.setSoftKeys(leftAction, rightAction);
   }

   public final void drawMenu(Graphics gfx) {
      BaseScreen screen = this.screen;
      switch (this.menuMode) {
         case -1:
            return;
         case 0:
            screen.drawCaption(gfx, this.optionsMenu, 1);
            return;
         case 1:
            screen.drawCaption(gfx, this.compactOptionsMenu, 1);
            return;
         case 2:
            screen.drawPageOne(gfx);
            return;
         case 3:
            screen.drawCaption(gfx, this.helpMenu, 1);
            return;
         case 4:
            screen.drawPageTwo(gfx);
            return;
      }
   }

   public final void menuKeyPressed(int keyCode, int action) {
      if (this.currentMenu != null) {
         if (action != 16 && (keyCode & 4128) == 0) {
            this.navigateLeftRight(this.currentMenu, keyCode);
         } else {
            this.releaseSoftKeys();
            this.clearAction(4128);
            int selectedValue = 5 + this.currentMenu[2];
            this.menuAction(this.currentMenu[selectedValue]);
         }
      }

      if (this.menuMode == 4) {
         this.screen.scrollMenu(keyCode);
      } else if (this.menuMode == 2) {
         if (action == 256) {
            this.releaseSoftKeys();
            this.resetProfiles();
            this.resetProgressFlags();
            this.saveSettings();
            this.resetGameData();
            this.saveGameData();
            this.setMenuMode(this.returnMenuMode);
            this.titleScreen().buildMainMenu(60);
         } else if (action == 512) {
            this.releaseSoftKeys();
            this.setMenuMode(this.returnMenuMode);
         }
      }

      if (action == 4) {
         this.releaseSoftKeys();
         this.setMenuMode(this.returnMenuMode);
      }
   }

   private void menuAction(int actionId) {
      BaseScreen screen = this.screen;
      switch (actionId) {
         case 3:
            this.setMenuMode(5);
            return;
         case 62:
            screen.showPanelTwo(89, 62);
            this.setMenuMode(4);
            return;
         case 79:
            this.sound.setEnabled(!this.sound.enabled);
            if (this.sound.enabled) {
               if (this.titleScreen() != null) {
                  this.setSoundLoop(this.soundLoopId);
                  return;
               }

               this.sound.pendingSound = 3;
               return;
            }
            break;
         case 80:
            this.vibrationEnabled = !this.vibrationEnabled;
            if (this.vibrationEnabled) {
               this.vibrate(400);
            }

            this.saveSettings();
            return;
         case 81:
            this.tutorialsEnabled = !this.tutorialsEnabled;
            this.saveSettings();
            return;
         case 82:
            this.autonomousEnabled = !this.autonomousEnabled;
            this.saveSettings();
            return;
         case 83:
            this.savePromptsEnabled = !this.savePromptsEnabled;
            this.saveSettings();
            return;
         case 85:
            this.setMenuMode(2);
            return;
         case 90:
            screen.showPanelTwo(91, 90);
            this.setMenuMode(4);
            return;
         case 94:
            screen.showPanelTwo(95, 94);
            this.setMenuMode(4);
            return;
         case 96:
            screen.showPanelTwo(97, 96);
            this.setMenuMode(4);
            return;
         case 98:
            screen.showPanelTwo(99, 98);
            this.setMenuMode(4);
            return;
         case 100:
            screen.showPanelTwo(101, 100);
            this.setMenuMode(4);
            return;
         case 105:
            screen.showPanelTwo(106, 105);
            this.setMenuMode(4);
      }
   }

   private void initWorld() {
      this.loadTileTable();
      this.lot = new Lot(this);
      this.neighborhood = new Neighborhood(this);
      this.houseIndex = 0;
      this.houseUpgrade = -1;
      this.walkTargetFrame = -1;
   }

   public final void setHouse(int houseIndex) {
      this.houseIndex = houseIndex;
      this.houseUpgrade = -1;
   }

   public final void enterHouseWith(Sim sim) {
      Sim currentSim = this.gameScreen().activePlayer;
      this.simPosX = currentSim.posX;
      this.simPosY = currentSim.posY;
      if (sim != null) {
         this.walkTargetX = sim.posX;
         this.walkTargetY = sim.posY;
         this.walkTargetFrame = sim.variant;
      } else {
         this.walkTargetFrame = -1;
      }

      this.houseIndex = -1;
   }

   private static final short readShort(DataInputStream input) throws IOException {
      return input.readShort();
   }

   private void loadTileTable() {
      ResourceLoader loader = this.resources;

      try {
         DataInputStream input;
         byte groupCount;
         short[][] groups = new short[groupCount = (input = new DataInputStream(loader.openStream(63))).readByte()][];

         for (int groupIndex = 0; groupIndex < groupCount; groupIndex++) {
            byte groupSize;
            short[] entries = new short[(groupSize = input.readByte()) << 1];
            // decompiled as byte; the table holds more entries than a byte can index
            int entryIndex = 0;

            for (int index = 0; index < groupSize; index++) {
               entries[entryIndex] = input.readByte();
               entries[entryIndex + 1] = readShort(input);
               entryIndex += 2;
            }

            groups[groupIndex] = entries;
         }

         byte maskCount;
         int[] masks = new int[(maskCount = input.readByte()) << 1];
         // decompiled as byte; the table holds more entries than a byte can index
         int maskIndex = 0;

         for (int index2 = 0; index2 < maskCount; index2++) {
            masks[maskIndex] = readShort(input);
            masks[maskIndex + 1] = input.readInt();
            maskIndex += 2;
         }

         this.tileMasks = masks;
         input.close();
         this.tileGroups = groups;
      } catch (IOException ignored) {
      }
   }

   public final short[] tileGroup(int index) {
      return this.tileGroups[index];
   }

   public static boolean isRunning(GameCanvas canvas) {
      return canvas.running;
   }

   public static void repaintTick(GameCanvas canvas) {
      canvas.tick();
   }

   public static boolean isLandscape(GameCanvas canvas) {
      return canvas.suspended;
   }

   public static boolean isUpdating(GameCanvas canvas) {
      return canvas.updating;
   }

   public static boolean setUpdating(GameCanvas canvas, boolean updating) {
      return canvas.updating = updating;
   }

   public static SdkSound sound(GameCanvas canvas) {
      return canvas.sound;
   }
}
