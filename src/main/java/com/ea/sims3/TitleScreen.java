package com.ea.sims3;

import javax.microedition.lcdui.Graphics;

public final class TitleScreen extends BaseScreen implements Runnable {
   public static int state = 0;
   private int pendingState = 0;
   private int pendingAction = 0;
   private int pendingParam = -1;
   private int languageIndex;
   private short[] mainMenuItems;
   private int loadStep;
   private Thread loaderThread;
   private int loaderState;
   private short[] languageMenuItems;
   private SdkString[] languageNames;
   private int introStep;
   private int introElapsed;
   private int introDuration;
   private Animator simAnimator;
   private int simFrameId;
   private static final short[] optionIds = new short[]{723, 724, 725, 726, 727, 728, 729, 730, 731, 732, 733, 734, 735};
   private int[] recolorFrom = new int[32];
   private int[] recolorTo = new int[32];
   private Animator upArrowAnimator;
   private Animator downArrowAnimator;
   private int optionIndex;
   private int optionScroll;
   private int optionScrollTarget;
   private int optionSlot;
   private int variantSlot;
   private int variantScroll;
   private int variantScrollTarget;
   private int optionIconId;
   private int optionFrameSelected;
   private int optionFrameNormal;
   private int optionValue;
   private int optionMin;
   private int optionMax;
   private boolean longSleeves;
   private boolean longPants;
   private boolean hairHasAccent;
   private static final int[] scrollBarGradient = new int[]{11, 12, 13, 14, 15, 14, 13, 12, 11};
   private static final int[] panelGradientTop = new int[]{16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
   private static final int[] panelGradientBottom = new int[]{31, 32, 33, 34, 35, 36, 37, 38, 39};
   private static final char[][] KEY_CHARS = new char[][]{
      {'1'},
      {'A', 'B', 'C', '2'},
      {'D', 'E', 'F', '3'},
      {'G', 'H', 'I', '4'},
      {'J', 'K', 'L', '5'},
      {'M', 'N', 'O', '6'},
      {'P', 'Q', 'R', 'S', '7'},
      {'T', 'U', 'V', '8'},
      {'W', 'X', 'Y', 'Z', '9'},
      {'0'}
   };
   private boolean multiTapActive;
   private int nameCursor;
   private int keyRepeatTimer;
   private int repeatKey;
   private int keyCharIndex;
   private int hintWidth;
   private int hintScrollX;
   private boolean newSimSelected;

   public TitleScreen(GameCanvas canvas) {
      super(canvas);
      canvas.fadeSpeed = 197379;
      this.upArrowAnimator = new Animator();
      this.downArrowAnimator = new Animator();
      this.simAnimator = new Animator();
      this.simAnimator.setRunning(false);
   }

   public final void setLanguage(int languageIndex) {
      this.initLayout();
      super.canvas.clearNewFlags();
      this.buildLanguageMenu();
      if (languageIndex == -1) {
         languageIndex = 7;
      }

      this.languageIndex = languageIndex;
      if (languageIndex != 4 && languageIndex != 5) {
         this.onTick(1);
      } else {
         this.languageIndex = 6;
         this.onTick(languageIndex);
      }

      this.loadStep = 0;
   }

   private void startLoading(int loadingTipDelta) {
      super.canvas.updateLoadingTip(loadingTipDelta);
      if (this.loaderThread == null) {
         this.loaderThread = new Thread(this);
         this.loaderState = 0;
         this.loaderThread.start();
      } else {
         this.loaderState = 0;
      }
   }

   public final void run() {
      while (this.loaderState != 2) {
         if (this.loaderState != 0) {
            try {
               synchronized (this) {
                  this.wait(1000L);
               }
            } catch (InterruptedException ignored) {
            }
         } else {
            this.loaderState = 1;

            try {
               this.loadNextStep();
            } catch (Exception ignored2) {
            }
         }
      }
   }

   private void loadNextStep() {
      GameCanvas canvas = super.canvas;
      if (!super.canvas.isFading()) {
         new StringBuffer().append("updateLoadingState: ").append(this.loadStep).toString();
         switch (this.loadStep) {
            case 0:
               short[] items = new short[15];
               this.mainMenuItems = items;
               this.buildMainMenu(-1);
               this.playClickSound();
               SdkText.loadStringsChunk(1);
               this.loadStep++;
               canvas.resetTileLoad();
               return;
            case 1:
               if (canvas.loadTilesFor(4)) {
                  this.loadStep++;
                  return;
               }
               break;
            case 2:
               this.onTick(this.languageIndex);
               this.loaderState = 2;
         }
      }
   }

   private void playClickSound() {
      super.canvas.sound.play(10);
   }

   public final void stop() {
   }

   public final void pause() {
      super.canvas.unloadTiles(3076, -1);
      super.canvas.setSoundLoop(-1);
      SdkSound.muteAll();
      SdkText.freeStringsChunk(1);
   }

   public final void render(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      int menuMode = super.canvas.menuMode;
      if (super.canvas.menuMode != -1) {
         if (menuMode == 5) {
            if (state == 4) {
               this.fillScreen(gfx, 77);
            } else {
               this.drawIntroLogo(gfx);
            }

            this.drawLanguageMenu(gfx);
         } else {
            this.drawIntroLogo(gfx);
            canvas.drawMenu(gfx);
            canvas.drawBottomBar(gfx);
         }
      } else {
         switch (state) {
            case 1:
               canvas.drawLoadingScreen(gfx);
               return;
            case 4:
               this.fillScreen(gfx, 77);
               this.drawLanguageMenu(gfx);
               return;
            case 5:
               this.fillScreen(gfx, 77);
               this.drawPageOne(gfx);
               canvas.drawBottomBar(gfx);
               return;
            case 6:
               this.drawIntro(gfx);
               return;
            case 7:
               this.drawIntroLogo(gfx);
               this.drawMainMenuBox(gfx);
               return;
            case 8:
               this.drawHeader(gfx, 3);
               canvas.drawBottomBar(gfx);
               return;
            case 9:
               canvas.promoImages.draw(gfx);
               return;
            case 10:
               this.drawAppearanceScreen(gfx);
               return;
            case 11:
               this.drawPersonaScreen(gfx);
               return;
            case 12:
               this.drawTraitScreen(gfx);
               return;
            case 13:
               this.drawNameEntryScreen(gfx);
               return;
            case 14:
            case 15:
               this.drawProfileScreen(gfx);
               return;
            case 16:
               this.drawProfileScreen(gfx);
               canvas.drawDialogOverlay(gfx);
               this.drawPageOne(gfx);
               canvas.drawBottomBar(gfx);
               return;
            case 17:
               this.drawAppearanceScreen(gfx);
               canvas.drawDialogOverlay(gfx);
               this.drawPageOne(gfx);
               canvas.drawBottomBar(gfx);
               return;
            case 18:
               this.drawNameEntryScreen(gfx);
               canvas.drawDialogOverlay(gfx);
               this.drawPageOne(gfx);
               canvas.drawBottomBar(gfx);
               return;
            case 19:
               this.drawIntroLogo(gfx);
               this.drawMainMenuBox(gfx);
               canvas.drawDialogOverlay(gfx);
               this.drawPageOne(gfx);
               canvas.drawBottomBar(gfx);
            case 2:
            case 3:
         }
      }
   }

   public final void update(int keyCode, int action) {
      if (state != 1) {
         GameCanvas canvas = super.canvas;
         if (super.canvas.fadeFinished && this.pendingState != 0) {
            this.onTick(this.pendingState);
            canvas.fadeFromBlack();
            this.pendingState = 0;
         } else if (!canvas.isFading()) {
            int menuMode = canvas.menuMode;
            if (canvas.menuMode != -1) {
               if (menuMode == 5) {
                  this.languageMenuKeyPressed(keyCode, action);
               } else {
                  canvas.menuKeyPressed(keyCode, action);
               }

               if (canvas.menuMode == -1) {
                  this.onTick(state);
               }
            } else {
               switch (state) {
                  case 4:
                     this.languageMenuKeyPressed(keyCode, action);
                     return;
                  case 5:
                     if (this.matchesInput(keyCode, action, 4128, 256)) {
                        canvas.sound.setEnabled(true);
                        this.fadeToState(1);
                        return;
                     }

                     if (this.matchesInput(keyCode, action, 0, 512)) {
                        canvas.sound.setEnabled(false);
                        this.fadeToState(1);
                        return;
                     }
                     break;
                  case 6:
                     this.introKeyPressed(keyCode, action);
                     return;
                  case 7:
                     if (this.matchesInput(keyCode, action, 4128, 16)) {
                        int selectedIndex = 5 + this.mainMenuItems[2];
                        this.personaKeyPressed(this.mainMenuItems[selectedIndex]);
                        return;
                     }

                     if (this.matchesInput(keyCode, action, 0, 4)) {
                        this.onTick(19);
                        return;
                     }

                     canvas.navigateLeftRight(this.mainMenuItems, keyCode);
                     return;
                  case 8:
                     this.scrollList(keyCode, action);
                     if (this.matchesInput(keyCode, action, 0, 4)) {
                        this.onTick(7);
                        return;
                     }
                  case 9:
                  default:
                     break;
                  case 10:
                     this.optionKeyPressed(keyCode, action);
                     return;
                  case 11:
                     this.appearanceKeyPressed(keyCode, action);
                     return;
                  case 12:
                     this.traitKeyPressed(keyCode, action);
                     return;
                  case 13:
                     this.nameEntryKeyPressed(keyCode, action);
                     return;
                  case 14:
                  case 15:
                     this.profileSlotKeyPressed(keyCode, action);
                     return;
                  case 16:
                     if (this.matchesInput(keyCode, action, 4128, 256)) {
                        canvas.clearProfile(canvas.profileSlot);
                        canvas.saveSettings();
                        this.onTick(14);
                        return;
                     }

                     if (this.matchesInput(keyCode, action, 0, 512)) {
                        this.onTick(15);
                        return;
                     }
                     break;
                  case 17:
                     if (this.matchesInput(keyCode, action, 4128, 256)) {
                        this.onTick(14);
                        return;
                     }

                     if (this.matchesInput(keyCode, action, 0, 516)) {
                        this.onTick(10);
                        return;
                     }
                     break;
                  case 18:
                     if (this.matchesInput(keyCode, action, 4128, 256)) {
                        canvas.commitProfile();
                        canvas.reloadGameData();
                        this.requestScreen(2, -1);
                        return;
                     }

                     if (this.matchesInput(keyCode, action, 0, 516)) {
                        this.onTick(13);
                        return;
                     }
                     break;
                  case 19:
                     if (this.matchesInput(keyCode, action, 4128, 256)) {
                        this.onTick(3);
                        return;
                     }

                     if (this.matchesInput(keyCode, action, 0, 516)) {
                        this.onTick(7);
                     }
               }
            }
         }
      }
   }

   public final void keyPressed(int keyCode) {
      if (state == 1) {
         this.startLoading(keyCode);
      } else {
         switch (state) {
            case 6:
               this.advanceIntro(keyCode);
               return;
            case 7:
               return;
            case 8:
               return;
            case 9:
               this.promoKeyPressed();
               return;
            case 10:
               this.advanceSimAnimator(keyCode, false);
               this.updateScrollAnimators(keyCode);
               return;
            case 11:
               this.advanceSimAnimator(keyCode, true);
               return;
            case 12:
               this.advanceSimAnimator(keyCode, true);
               this.updateNameEntryMarquees(keyCode);
               return;
            case 13:
               this.updateNameEntry(keyCode);
               return;
            case 14:
               this.advanceSimAnimator(keyCode, false);
            case 15:
         }
      }
   }

   public final void onTick(int newState) {
      GameCanvas canvas = super.canvas;
      int previousState = state;
      state = newState;
      canvas.clearActions();
      canvas.releaseSoftKeys();
      switch (newState) {
         case 1:
            canvas.setSoftKeys(0, 0);
            return;
         case 2:
            canvas.setPendingScreen(this.pendingAction, this.pendingParam);
            return;
         case 3:
            canvas.muteSound();
            return;
         case 4:
            canvas.setSoftKeys(0, 16384);
            return;
         case 5:
            canvas.setSoftKeys(512, 256);
            this.showInfoPanel(78, 79);
            return;
         case 6:
            canvas.setSoundLoop(10);
            canvas.loadTilesBlocking(2);
            canvas.setSoftKeys(0, 0);
            this.restartIntro();
            return;
         case 7:
            canvas.setSoundLoop(10);
            this.introStep = 18;
            SpriteRenderer.loadTile(super.canvas.resources, 82);
            canvas.unloadTiles(2, 0);
            canvas.setSoftKeys(0, 16);
            return;
         case 8:
            this.resetList();
            canvas.setSoftKeys(4, 0);
            return;
         case 9:
            SpriteRenderer.loadTile(super.canvas.resources, 48);
            SpriteRenderer.loadTile(super.canvas.resources, 47);
            SpriteRenderer.loadTile(super.canvas.resources, 46);
            SpriteRenderer.loadTile(super.canvas.resources, 45);
            SpriteRenderer.loadTile(super.canvas.resources, 44);
            SpriteRenderer.loadTile(super.canvas.resources, 43);
            canvas.promoActive = true;
            canvas.promoImages.reset();
            return;
         case 10:
            this.upArrowAnimator.play(681, 20);
            this.downArrowAnimator.play(679, 20);
            if (previousState != 11 && previousState != 17) {
               this.setSimOption(0, 0);
            } else if (previousState == 11) {
               this.setSimFrames(false);
            }

            canvas.setSoftKeys(4, 2);
            return;
         case 11:
            this.layoutPersonaList();
            this.setSimFrames(true);
            canvas.setWrappedString(736, 1, super.panelHeight);
            canvas.setSoftKeys(4, canvas.neighborhood.selectedResident != -1 ? 2 : 0);
            return;
         case 12:
            this.layoutTraitList();
            this.setSimFrames(true);
            canvas.setWrappedString(744, 1, super.panelHeight);
            int count = canvas.neighborhood.simKnownFreeSlot();
            boolean twoSlots = 5 == count;
            canvas.setSoftKeys(4, twoSlots ? 2 : 0);
            return;
         case 13:
            if (previousState != 18) {
               this.resetNameEntry();
            }

            canvas.setSoftKeys(4, canvas.profileNameLength() != 0 ? 2 : 0);
            return;
         case 14:
            this.newSimSelected = false;
            this.applySimAppearance(false);
            canvas.setSoftKeys(4, 16);
            return;
         case 15:
            this.newSimSelected = false;
            if (canvas.profileSlot != 0) {
               canvas.selectProfile(0);
               this.applySimAppearance(false);
            }

            canvas.setSoftKeys(64, canvas.isProfileUsed() ? 32 : 0);
            return;
         case 16:
            canvas.setSoftKeys(512, 256);
            this.showInfoPanel(55, 54);
            return;
         case 17:
            this.showInfoPanel(57, 56);
            canvas.setSoftKeys(512, 256);
            return;
         case 18:
            this.showInfoPanel(58, 52);
            canvas.setSoftKeys(512, 256);
            return;
         case 19:
            this.showInfoPanel(68, 42);
            canvas.setSoftKeys(512, 256);
      }
   }

   private void fadeToState(int nextState) {
      this.pendingState = nextState;
      if (!super.canvas.isFadingFromWhite()) {
         super.canvas.fadeFromWhite();
      }
   }

   private void requestScreen(int action, int param) {
      this.pendingAction = action;
      this.pendingParam = param;
      this.fadeToState(2);
   }

   public final void buildMainMenu(int selectedId) {
      short[] items = this.mainMenuItems;
      GameCanvas.initListWith(this.mainMenuItems, 50, super.canvas.menuRowSpacing);
      GameCanvas.addToList(items, 51);
      GameCanvas.addToList(items, 59);
      GameCanvas.addToList(items, 60);
      GameCanvas.addToList(items, 61);
      if (super.canvas.promoImages.isAvailable()) {
         GameCanvas.addToList(items, 63);
      }

      GameCanvas.addToList(items, 42);
      GameCanvas.selectListValue(items, selectedId);
   }

   private void personaKeyPressed(int menuId) {
      GameCanvas canvas = super.canvas;
      switch (menuId) {
         case 42:
            this.onTick(19);
         default:
            return;
         case 51:
            SpriteRenderer.unloadTile(82, 0);
            SpriteRenderer.unloadTile(48, 0);
            SpriteRenderer.unloadTile(47, 0);
            SpriteRenderer.unloadTile(46, 0);
            SpriteRenderer.unloadTile(45, 0);
            SpriteRenderer.unloadTile(44, 0);
            SpriteRenderer.unloadTile(43, 0);
            canvas.selectProfile(0);
            this.onTick(14);
            return;
         case 59:
            this.onTick(8);
            return;
         case 60:
            canvas.setMenuMode(0);
            return;
         case 61:
            canvas.setMenuMode(3);
            return;
         case 63:
            this.onTick(9);
      }
   }

   private void promoKeyPressed() {
      PromoImages promo = super.canvas.promoImages;
      if (super.canvas.promoImages.available) {
         promo.handleKeys();
      } else {
         super.canvas.promoActive = false;
         this.onTick(7);
      }
   }

   private void buildLanguageMenu() {
      if (this.languageMenuItems == null) {
         int headerCount = super.canvas.headerCount;
         short[] items = new short[5 + headerCount];
         this.languageMenuItems = items;
         GameCanvas.initListWith(items, 3, super.canvas.menuRowSpacing);
         SdkString[] names = new SdkString[headerCount + 1];
         this.languageNames = names;

         for (int headerIndex = 0; headerIndex < headerCount; headerIndex++) {
            GameCanvas.addToList(items, headerIndex);
            names[headerIndex] = SdkText.getHeaderString(headerIndex, 0, null);
         }

         int languageIndex = super.canvas.language;
         if (super.canvas.language != -1) {
            items[2] = (short)languageIndex;
         }
      }
   }

   private void drawLanguageMenu(Graphics gfx) {
      this.drawTwoColumnList(gfx, this.languageMenuItems, 1, this.languageNames);
      super.canvas.drawBottomBar(gfx);
   }

   private void languageMenuKeyPressed(int keyCode, int action) {
      GameCanvas canvas = super.canvas;
      short previousId = this.languageMenuItems[2];
      if (this.matchesInput(keyCode, action, 4128, 16384)) {
         canvas.setLanguage(previousId);
         canvas.saveSettings();
         if (canvas.menuMode == 5) {
            canvas.setMenuMode(0);
         } else {
            this.fadeToState(5);
         }
      } else {
         if (this.matchesInput(keyCode, action, 524288, 32768)) {
            if (canvas.menuMode == 5) {
               SdkText.setCurrentLanguage(canvas.language);
               canvas.setMenuMode(0);
               return;
            }
         } else {
            super.canvas.navigateLeftRight(this.languageMenuItems, keyCode);
            short selectedId;
            if ((selectedId = this.languageMenuItems[2]) != previousId) {
               SdkText.setCurrentLanguage(selectedId);
            }
         }
      }
   }

   private void restartIntro() {
      super.canvas.clearFade();
      this.setIntroStep(0);
   }

   private void setIntroStep(int introStepIndex) {
      int waitMs = 0;
      switch (introStepIndex) {
         case 0:
            waitMs = 500;
            break;
         case 1:
            waitMs = 1000;
            break;
         case 2:
            waitMs = 2000;
            break;
         case 3:
            waitMs = 600;
            break;
         case 4:
            waitMs = 500;
            break;
         case 5:
            waitMs = 600;
            break;
         case 6:
            waitMs = 300;
            break;
         case 7:
            waitMs = 750;
            break;
         case 8:
            waitMs = 700;
            break;
         case 9:
            waitMs = 600;
            break;
         case 10:
            waitMs = 600;
            break;
         case 11:
            waitMs = 300;
            break;
         case 12:
            waitMs = 750;
            break;
         case 13:
            waitMs = 750;
            break;
         case 14:
            waitMs = 500;
            break;
         case 15:
            waitMs = 750;
            break;
         case 16:
            waitMs = this.mainMenuItems[0] * 200;
            break;
         case 17:
            waitMs = 500;
            break;
         case 18:
            this.onTick(7);
      }

      this.introStep = introStepIndex;
      this.introElapsed = 0;
      this.introDuration = waitMs;
   }

   private void advanceIntro(int deltaMs) {
      this.introElapsed += deltaMs;
      if (this.introElapsed >= this.introDuration) {
         if (this.introStep == 13) {
            this.introElapsed %= 750;
            return;
         }

         this.setIntroStep(this.introStep + 1);
      }
   }

   private void introKeyPressed(int keyCode, int action) {
      if (this.introStep == 13 && this.matchesInput(keyCode, action, 4128, 0)) {
         this.setIntroStep(this.introStep + 1);
      }
   }

   private void drawIntro(Graphics gfx) {
      switch (this.introStep) {
         case 0:
            this.fillScreen(gfx, 0);
            return;
         case 1:
         case 2:
         case 3:
         case 4:
            // Black with the EA logo until the single fade to white below: the
            // intro must not flash white before the logo and back to black.
            this.drawIntroBackground(gfx);
            return;
         case 5:
            this.fillFadeBlack(gfx, this.introElapsed, this.introDuration);
            return;
         case 6:
            this.fillScreen(gfx, 1);
            return;
         case 7:
            // No bands here either: they dimmed the white scene back to black and
            // flashed again when step 8 repainted it.
            this.fillScreen(gfx, 1);
            this.drawIntroSim(gfx);
            return;
         case 8:
         case 9:
         case 10:
         case 11:
            this.fillScreen(gfx, 1);
            this.drawIntroSim(gfx);
            return;
         case 12:
         case 13:
            this.drawIntroSim(gfx);
            return;
         case 14:
            this.drawIntroSim(gfx);
            this.drawMainMenuBox(gfx);
            return;
         case 15:
         case 16:
         case 17:
            this.drawIntroLogo(gfx);
            this.drawMainMenuBox(gfx);
         case 18:
      }
   }

   private void fillFadeBlack(Graphics gfx, int elapsed, int duration) {
      GameCanvas canvas = super.canvas;
      int alpha = Math.min(elapsed * 255 / duration, 255);
      int color = 0xFF000000 | alpha << 16 | alpha << 8 | alpha << 0;
      gfx.setColor(color);
      gfx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
   }

   private void fillScreen(Graphics gfx, int colorIndex) {
      GameCanvas canvas = super.canvas;
      SpriteRenderer.setColor(gfx, colorIndex);
      gfx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
   }

   private void drawIntroBackground(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      this.fillScreen(gfx, 60);
      SpriteRenderer.drawFrame(gfx, 556, 0, canvas.halfWidth(), canvas.halfHeight());
   }

   private void drawIntroSim(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      short frameId = 0;
      if (GameCanvas.currentLandscape == 1) {
         frameId = 558;
      } else {
         frameId = 557;
      }

      int centerX = canvas.halfWidth();
      int centerY = canvas.halfHeight();
      int progress = 0;
      if (this.introDuration != 0) {
         progress = FixedMath.div(this.introElapsed, this.introDuration);
      }

      // No dimming bands on the logo frames either: they pulled the white scene
      // back down to black instead of leaving it white.
      if (this.introStep >= 12) {
         SpriteRenderer.drawFrame(gfx, frameId, 8, centerX, centerY);
      }

      if (this.introStep >= 11) {
         SpriteRenderer.drawFrame(gfx, frameId, 6, centerX, centerY);
      }

      SpriteRenderer.drawFrame(gfx, frameId, 0, centerX, centerY);
      int simX = centerX;
      if (this.introStep == 10) {
         int width = canvas.getWidth();
         simX = easeValue(width, centerX, progress, 3);
      }

      int shiftX = Math.min(0, simX - centerX);
      if (this.introStep >= 8) {
         int doorTop = -SpriteRenderer.frameHeight(frameId, 1);
         int doorY = centerY;
         if (this.introStep == 8) {
            doorY = easeValue(doorTop, centerY, progress, 4);
         }

         if (doorY >= centerY) {
            SpriteRenderer.drawFrame(gfx, frameId, 2, centerX, centerY);
         }

         SpriteRenderer.drawFrame(gfx, frameId, 1, centerX + shiftX, doorY);
      }

      if (this.introStep >= 9) {
         int headX = centerX;
         if (this.introStep == 9) {
            headX = easeValue(0, centerX, progress, 3);
         }

         int[] hitRect = super.hitRect;
         SpriteRenderer.hitTest(super.hitRect, frameId, 3, 0);
         this.drawSampleLetter(headX + shiftX + hitRect[0], centerY + hitRect[1], 3);
      }

      if (this.introStep >= 10) {
         SpriteRenderer.drawFrame(gfx, frameId, 4, simX, centerY);
         if (simX <= centerX) {
            SpriteRenderer.drawFrame(gfx, frameId, 5, simX, centerY);
         }
      }

      if (this.introStep == 13 && this.introElapsed % 750 < 375) {
         int[] pressRect = super.hitRect;
         SpriteRenderer.hitTest(super.hitRect, frameId, 7, 0);
         canvas.drawStringById(34, 0, centerX + pressRect[0], centerY + pressRect[1], 3);
      }
   }

   private void drawSampleLetter(int xPos, int yPos, int fontIndex) {
      GameCanvas canvas = super.canvas;
      SdkString letter = super.canvas.getString(25);
      canvas.drawString(letter, fontIndex, xPos, yPos, 3);
   }

   private static int easeValue(int from, int target, int progress, int kind) {
      int range = target - from;
      switch (kind) {
         case 0:
         default:
            return from + FixedMath.clamp(FixedMath.mul(progress, range), 0, range);
         case 1:
            progress = FixedMath.smoothStep(0, 65536, progress);
            return from + FixedMath.clamp(FixedMath.mul(progress, range), 0, range);
         case 2:
            progress = FixedMath.smoothStep(0, 65536, 32768 + (progress >> 1));
            return from + FixedMath.clamp(FixedMath.mul(progress, range), 0, range);
         case 3:
            if (progress < 58982) {
               progress = FixedMath.mul(FixedMath.smoothStep(0, 58982, progress), 68812);
            } else {
               int overshootOne = 65536 - FixedMath.div(progress - 58982, 6554);
               progress = 65536 + FixedMath.mul(3276, overshootOne);
            }

            return from + FixedMath.mul(progress, range);
         case 4:
            if (progress < 55705) {
               progress = FixedMath.mul(FixedMath.smoothStep(0, 55705, progress), 68812);
            } else {
               int overshootTwo = 65536 - FixedMath.div(progress - 55705, 9831);
               progress = 65536 + FixedMath.mul(3276, overshootTwo);
            }

            return from + FixedMath.mul(progress, range);
         case 5:
            if (progress < 49152) {
               progress = FixedMath.mul(FixedMath.smoothStep(0, 49152, progress), 98304);
            } else {
               int overshootThree = 65536 - FixedMath.div(progress - 49152, 16384);
               progress = 65536 + FixedMath.mul(32768, overshootThree);
            }

            return from + FixedMath.mul(progress, range);
         case 6:
            if (progress < 32768) {
               progress = FixedMath.mul(FixedMath.sinShifted(FixedMath.div(FixedMath.mul(progress, 102944), 32768)), 72089);
            } else {
               int fraction;
               int angle = FixedMath.mul(fraction = FixedMath.div(progress - 32768, 32768), 617662);
               int amplitude = 6554 - FixedMath.mul(6554, fraction);
               progress = FixedMath.mul(FixedMath.cos(angle), amplitude) + 65536;
            }

            return from + FixedMath.mul(progress, range);
      }
   }

   private void drawIntroLogo(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      short frameId = 0;
      if (GameCanvas.currentLandscape == 1) {
         frameId = 558;
      } else {
         frameId = 557;
      }

      int centerX = canvas.halfWidth();
      int centerY = canvas.halfHeight();
      SpriteRenderer.drawFrame(gfx, frameId, 8, centerX, centerY);
   }

   private void drawMainMenuBox(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      short[] items = this.mainMenuItems;
      short count = this.mainMenuItems[0];
      short selectedIndex = items[2];
      int boxHeight = count * 22 - 2 + 14 + 19 + 5;
      int boxWidth = canvas.getWidth() * 140 / 176;
      int boxX = canvas.getWidth() - boxWidth >> 1;
      int boxY = (canvas.getHeight() - boxHeight >> 1) + 2;
      int progress = 0;
      if (this.introDuration != 0) {
         progress = FixedMath.div(this.introElapsed, this.introDuration);
      }

      int drawX = boxY;
      int drawY = boxHeight;
      if (this.introStep == 14) {
         drawY = easeValue(27, boxHeight, progress, 0);
         drawX = (canvas.getHeight() - drawY >> 1) + 2;
      }

      this.drawDialogFrame(gfx, boxX, drawX, boxWidth, drawY, -1, 4);
      if (this.introStep >= 15) {
         drawY = canvas.getWidth() >> 1;
         int titleHeight;
         int titleY = titleHeight = boxY + 9;
         if (this.introStep == 15) {
            titleY = easeValue(-SpriteRenderer.frameHeight(557, 9), titleHeight, progress, 3);
         }

         SpriteRenderer.drawFrame(gfx, 557, 9, drawY, titleY);
         int[] buttonRect = super.hitRect;
         SpriteRenderer.hitTest(super.hitRect, 557, 9, 0);
         this.drawSampleLetter(drawY + buttonRect[0], titleY + buttonRect[1], 4);
      }

      if (this.introStep >= 16) {
         drawX = boxWidth - 14;
         drawY = boxX + 7;
         int animIndex = count;
         if (this.introStep == 16) {
            animIndex = this.introElapsed / 200;
            progress = FixedMath.div(this.introElapsed % 200, 200);
         }

         int rowY = boxY + 22 + 7;

         for (int row = 0; row < count; row++) {
            short itemId = items[5 + row];
            int itemY = rowY;
            if (row == animIndex) {
               itemY = rowY + easeValue(-3, 0, progress, 5);
            } else if (row > animIndex) {
               break;
            }

            this.drawButton(gfx, drawY, itemY, drawX, 20, row == selectedIndex, itemId);
            rowY += 22;
         }
      }

      if (this.introStep >= 17) {
         drawX = 0;
         if (this.introStep == 17) {
            drawX = 15 - easeValue(0, 15, progress, 0);
         }

         canvas.drawBottomBar(gfx, drawX);
      }
   }

   private void resetForNewSim() {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = super.canvas.neighborhood;
      canvas.resetGameData();
      canvas.clearProfileName();
      this.longSleeves = neighborhood.simAttribute(0, 6) != 0;
      this.longPants = neighborhood.simAttribute(0, 8) != 0;
      this.hairHasAccent = this.hairStyleHasAccent(neighborhood.simAttribute(0, 2));
      this.nameCursor = 0;
      this.onTick(10);
      this.applySimAppearance(false);
   }

   private void applySimAppearance(boolean immediate) {
      this.setSimFrames(immediate);
      GameCanvas canvas = super.canvas;
      Lot lot = super.canvas.lot;
      Neighborhood neighborhood = canvas.neighborhood;
      int[] recolorFrom = this.recolorFrom;
      int[] recolorTo = this.recolorTo;
      lot.buildPaletteFor(0, recolorFrom, recolorTo);
      int tileMask = neighborhood.simAttribute(0, 0) == 0 ? 2048 : 1024;
      canvas.unloadTiles(3072, -1);
      canvas.loadTiles(tileMask, recolorFrom, recolorTo, 0);
   }

   private void drawSimPreview(Graphics gfx, int xPos, int yPos, boolean offsetByFrame) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = super.canvas.neighborhood;
      int animId = this.simAnimator.animId;
      if (offsetByFrame) {
         int[] hitRect = super.hitRect;
         SpriteRenderer.hitTest(super.hitRect, animId, 0, 1);
         xPos -= hitRect[0];
         yPos -= hitRect[1];
      }

      this.simAnimator.draw(gfx, xPos, yPos);
      int[] frameRect = super.hitRect;
      this.simAnimator.matchesFrame(frameRect, 0);
      int hairStyle = neighborhood.simAttribute(0, 2);
      SpriteRenderer.drawFrame(gfx, this.simFrameId, hairStyle, xPos + frameRect[0], yPos + frameRect[1]);
      if (neighborhood.simAttribute(0, 8) != 0) {
         int accentFrameId = animId == 703 ? 704 : (animId == 706 ? 707 : 17091);
         SpriteRenderer.drawFrame(gfx, accentFrameId, 0, xPos, yPos);
      }
   }

   private void setSimFrames(boolean greeting) {
      Neighborhood neighborhood = super.canvas.neighborhood;
      short animId;
      short frameId;
      if (super.canvas.neighborhood.simAttribute(0, 0) == 0) {
         if (greeting) {
            animId = 706;
            frameId = 708;
         } else {
            animId = 17090;
            frameId = 17092;
         }
      } else {
         animId = 703;
         frameId = 705;
      }

      this.simAnimator.play(animId, 4);
      this.simFrameId = frameId;
   }

   private void advanceSimAnimator(int deltaMs, boolean greeting) {
      if (!this.simAnimator.isRunning()) {
         this.setSimFrames(greeting);
      }

      this.simAnimator.advance(deltaMs);
   }

   private void updateScrollAnimators(int deltaMs) {
      this.upArrowAnimator.advance(deltaMs);
      if (!this.upArrowAnimator.isRunning()) {
         this.upArrowAnimator.play(681, 20);
      }

      this.downArrowAnimator.advance(deltaMs);
      if (!this.downArrowAnimator.isRunning()) {
         this.downArrowAnimator.play(679, 20);
      }

      this.advanceArrows(deltaMs);
      this.optionScroll = BaseScreen.approach(this.optionScroll, this.optionScrollTarget, deltaMs, 8);
      this.variantScroll = BaseScreen.approach(this.variantScroll, this.variantScrollTarget, deltaMs, 8);
      if (Math.abs(this.optionScroll - this.optionScrollTarget) < 32768) {
         this.optionScroll = this.optionScrollTarget;
      }

      if (Math.abs(this.variantScroll - this.variantScrollTarget) < 32768) {
         this.variantScroll = this.variantScrollTarget;
      }
   }

   private void optionKeyPressed(int keyCode, int action) {
      if (this.matchesInput(keyCode, action, 0, 4)) {
         this.onTick(17);
      } else if (this.matchesInput(keyCode, action, 0, 2)) {
         this.fadeToState(11);
      } else if (this.matchesInput(keyCode, action, 8196, 0) && this.optionIndex > 0) {
         this.upArrowAnimator.play(680, 16);
         this.setSimOption(this.optionIndex - 1, -1);
      } else if (this.matchesInput(keyCode, action, 16640, 0) && this.optionIndex < optionIds.length - 1) {
         this.downArrowAnimator.play(678, 16);
         this.setSimOption(this.optionIndex + 1, 1);
      } else {
         if (optionIds[this.optionIndex] == 723) {
            if (this.matchesInput(keyCode, action, 32784, 0)) {
               this.selectVariant(this.optionValue - 1);
               this.showLeftArrow();
               return;
            }

            if (this.matchesInput(keyCode, action, 65600, 0)) {
               this.selectVariant(this.optionValue + 1);
               this.showRightArrow();
               return;
            }
         } else {
            if (this.matchesInput(keyCode, action, 32784, 0) && this.optionValue > this.optionMin) {
               this.setOptionValue(this.optionValue - 1, false);
               this.showLeftArrow();
               this.applySimAppearance(false);
               return;
            }

            if (this.matchesInput(keyCode, action, 65600, 0) && this.optionValue < this.optionMax) {
               this.setOptionValue(this.optionValue + 1, false);
               this.showRightArrow();
               this.applySimAppearance(false);
            }
         }
      }
   }

   private void selectVariant(int variant) {
      Neighborhood neighborhood = super.canvas.neighborhood;
      int variantCount = super.canvas.neighborhood.portraitCount;
      if (variant < 0) {
         variant = variantCount - 1;
      } else if (variant >= variantCount) {
         variant = 0;
      }

      this.optionValue = variant;
      byte[] values = neighborhood.portraitData(variant);

      for (int index = 0; index < 10; index++) {
         neighborhood.setAttribute(index, values[index]);
      }

      this.longSleeves = values[10] == 0;
      this.longPants = values[11] == 0;
      this.applySimAppearance(false);
   }

   private void setSimOption(int index, int step) {
      GameCanvas canvas = super.canvas;
      if (!this.longSleeves && optionIds[index] == 731) {
         index += step;
      }

      if (!this.longPants && optionIds[index] == 734) {
         index += step;
      }

      if (!this.hairHasAccent && optionIds[index] == 728) {
         index += step;
      }

      int listHeight = canvas.getHeight() - 15 - (SpriteRenderer.frameHeight(697, 0) << 1);
      int frameCount = SpriteRenderer.frameCount(687);
      int frameHeight = SpriteRenderer.frameHeight(688, 0);
      int scrollY = (listHeight >> 1) + 2 - frameHeight * index - (frameHeight >> 1);
      int minScroll = listHeight - 2 - frameHeight * frameCount;
      scrollY = FixedMath.clamp(scrollY, minScroll, 2);
      this.optionScrollTarget = scrollY << 16;
      if (step == 0) {
         this.optionScroll = scrollY << 16;
      }

      this.optionIndex = index;
      short iconId = 196;
      short selectedIconId = 196;
      byte slotIndex = -1;
      byte variantSlot = -1;
      byte minValue = 0;
      switch (optionIds[index]) {
         case 723:
         default:
            break;
         case 724:
            selectedIconId = 691;
            iconId = 690;
            slotIndex = 0;
            break;
         case 725:
            variantSlot = 0;
            slotIndex = 1;
            break;
         case 726:
            if (canvas.neighborhood.simAttribute(0, 0) == 1) {
               iconId = 692;
               selectedIconId = 693;
            } else {
               iconId = 694;
               selectedIconId = 695;
            }

            slotIndex = 2;
            break;
         case 727:
            variantSlot = 1;
            slotIndex = 3;
            break;
         case 728:
            variantSlot = 2;
            slotIndex = 4;
            break;
         case 729:
            variantSlot = 3;
            slotIndex = 5;
            break;
         case 730:
            iconId = 700;
            selectedIconId = 701;
            this.optionValue = this.longSleeves ? 0 : 1;
            break;
         case 731:
            variantSlot = 4;
            slotIndex = 6;
            minValue = 1;
            break;
         case 732:
            variantSlot = 5;
            slotIndex = 7;
            break;
         case 733:
            iconId = 698;
            selectedIconId = 699;
            this.optionValue = this.longPants ? 0 : 1;
            break;
         case 734:
            variantSlot = 6;
            slotIndex = 8;
            minValue = 1;
            break;
         case 735:
            variantSlot = 7;
            slotIndex = 9;
      }

      this.optionSlot = slotIndex;
      this.variantSlot = variantSlot;
      this.optionIconId = 689;
      this.optionFrameSelected = iconId;
      this.optionFrameNormal = selectedIconId;
      this.optionMin = minValue;
      if (variantSlot != -1) {
         this.optionMax = canvas.lot.tableRowAt(variantSlot).length - 1;
      } else {
         this.optionMax = SpriteRenderer.frameCount(iconId) - 1;
      }

      if (slotIndex != -1) {
         this.setOptionValue(canvas.neighborhood.simAttribute(0, slotIndex), true);
      } else {
         this.setOptionValue(this.optionValue, true);
      }
   }

   private boolean hairStyleHasAccent(int hairStyle) {
      int mask = 1 << hairStyle;
      if (super.canvas.neighborhood.simAttribute(0, 0) == 0) {
         mask <<= 16;
      }

      return (mask & 786445) != 0;
   }

   private void setOptionValue(int value, boolean immediate) {
      Neighborhood neighborhood = super.canvas.neighborhood;
      this.optionValue = value;
      if (this.optionSlot != -1) {
         neighborhood.setAttribute(this.optionSlot, value);
      }

      short optionId;
      if ((optionId = optionIds[this.optionIndex]) == 730) {
         int count;
         if (value == 0) {
            this.longSleeves = true;
            count = neighborhood.simAttribute(0, 5) + 1;
         } else {
            this.longSleeves = false;
            count = 0;
         }

         neighborhood.setAttribute(6, count);
      } else if (optionId == 733) {
         int sleeveCount;
         if (value == 0) {
            this.longPants = true;
            sleeveCount = neighborhood.simAttribute(0, 7) + 1;
         } else {
            this.longPants = false;
            sleeveCount = 0;
         }

         neighborhood.setAttribute(8, sleeveCount);
      } else if (optionId == 724 || optionId == 726) {
         this.hairHasAccent = this.hairStyleHasAccent(neighborhood.simAttribute(0, 2));
      }

      int[] hitRect = super.hitRect;
      SpriteRenderer.hitTestMirrored(super.hitRect, 689, 0, 0);
      int listHeight = hitRect[2];
      int rowCount = this.optionMax - this.optionMin + 1;
      int rowHeight = SpriteRenderer.frameHeight(683, 0);
      int visibleRows = Math.min(rowCount * rowHeight, listHeight);
      int maxOffset = listHeight - visibleRows;
      int targetOffset = (visibleRows >> 1) + 0 - rowHeight * value - (rowHeight >> 1) + maxOffset;
      int minOffset = visibleRows - 0 - rowHeight * rowCount + (maxOffset >> 1);
      targetOffset = FixedMath.clamp(targetOffset, minOffset, 0);
      this.variantScrollTarget = targetOffset << 16;
      if (immediate) {
         this.variantScroll = targetOffset << 16;
      }
   }

   private static final void drawVerticalGradient(Graphics gfx, int[] colors, int xPos, int yPos, int width, int height) {
      int count = colors.length;
      int bandHeight = (height << 16) / count;
      int offsetY = 0;

      for (int band = 0; band < count; band++) {
         SpriteRenderer.setColor(gfx, colors[band]);
         int nextOffset = (band + 1) * bandHeight + 32768 >> 16;
         gfx.fillRect(xPos, yPos + offsetY, width, nextOffset - offsetY);
         offsetY = nextOffset;
      }
   }

   private static final void drawPanelGradient(Graphics gfx, int xPos, int yPos, int width, int height) {
      int topHeight = panelGradientHeight(height);
      int bottomHeight = height - topHeight;
      drawVerticalGradient(gfx, panelGradientTop, xPos, yPos, width, height);
      drawVerticalGradient(gfx, panelGradientBottom, xPos, yPos + bottomHeight, width, topHeight);
   }

   private static final int panelGradientHeight(int height) {
      return FixedMath.mul(17902, height);
   }

   private void drawAppearanceScreen(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      int width = super.canvas.getWidth();
      int height = canvas.getHeight() - 15;
      drawPanelGradient(gfx, 0, 0, width, height);
      int[] hitRect = super.hitRect;
      int panelFrameHeight = SpriteRenderer.frameHeight(697, 0);
      int panelFrameWidth;
      int panelWidth = (panelFrameWidth = SpriteRenderer.frameWidth(697, 0)) + 4;
      int listTop = 0 + panelFrameHeight;
      int listWidth = panelFrameWidth - 4;
      int listHeight = height - (panelFrameHeight << 1);
      int panelCenterX = width + 2 + panelWidth >> 1;
      int panelHeight = panelGradientHeight(height);
      int panelY = height - (panelHeight >> 1);
      int clipX = gfx.getClipX();
      int clipY = gfx.getClipY();
      int clipWidth = gfx.getClipWidth();
      int clipHeight = gfx.getClipHeight();
      SpriteRenderer.setColor(gfx, 60);
      gfx.fillRect(2, 0, 1, height);
      gfx.fillRect(2 + panelWidth - 1, 0, 1, height);
      SpriteRenderer.setColor(gfx, 77);
      gfx.fillRect(3, 0, 1, height);
      gfx.fillRect(2 + panelWidth - 2, 0, 1, height);
      SpriteRenderer.setColor(gfx, 76);
      gfx.fillRect(4, panelFrameHeight, 2, listHeight);
      gfx.fillRect(2 + panelWidth - 2 - 2, panelFrameHeight, 2, listHeight);
      drawVerticalGradient(gfx, scrollBarGradient, 6, listTop, listWidth, listHeight);
      SpriteRenderer.drawFrame(gfx, 697, 0, 4, 0);
      SpriteRenderer.drawFrame(gfx, 696, 0, 4, height);
      int gradientTop = 6 + (listWidth >> 1);
      int optionCount = SpriteRenderer.frameCount(687);
      int rowHeight = SpriteRenderer.frameHeight(688, 0);
      if (this.optionIndex > 0) {
         SpriteRenderer.hitTest(hitRect, 697, 0, 0);
         this.upArrowAnimator.draw(gfx, 4 + hitRect[0], 0 + hitRect[1]);
      }

      if (this.optionIndex < optionCount - 1) {
         SpriteRenderer.hitTest(hitRect, 696, 0, 0);
         this.downArrowAnimator.draw(gfx, 4 + hitRect[0], height + hitRect[1]);
      }

      gfx.setClip(6, listTop, listWidth, listHeight);
      int rowY = listTop + (this.optionScroll >> 16);

      for (int optionRow = 0; optionRow < optionCount; optionRow++) {
         int rowFrameId = optionRow == this.optionIndex ? 688 : 687;
         int rowFrameIndex = optionRow;
         if (optionIds[optionRow] == 731 && !this.longSleeves) {
            rowFrameId = 686;
            rowFrameIndex = 0;
         }

         if (optionIds[optionRow] == 734 && !this.longPants) {
            rowFrameId = 685;
            rowFrameIndex = 0;
         }

         if (optionIds[optionRow] == 728 && !this.hairHasAccent) {
            rowFrameId = 684;
            rowFrameIndex = 0;
         }

         SpriteRenderer.drawFrame(gfx, rowFrameId, rowFrameIndex, gradientTop, rowY);
         rowY += rowHeight;
      }

      gfx.setClip(clipX, clipY, clipWidth, clipHeight);
      SpriteRenderer.drawFrame(gfx, 702, 0, panelCenterX, panelY);
      this.drawSimPreview(gfx, panelCenterX, panelY, false);
      short optionId = optionIds[this.optionIndex];
      rowY = canvas.getWidth() - panelWidth - 2 - 8;
      int swatchX = 2 + panelWidth + 4;
      gradientTop = this.drawIconPanel(gfx, swatchX, rowY, 719, optionId);
      rowHeight = this.optionIconId;
      if (this.optionIconId != 196) {
         swatchX = gradientTop + FixedMath.mul(height - panelHeight - gradientTop, 12179);
         SpriteRenderer.drawFrame(gfx, rowHeight, 0, panelCenterX, swatchX);
         SpriteRenderer.hitTestMirrored(hitRect, rowHeight, 0, 0);
         int iconX = panelCenterX + hitRect[0];
         int iconY = swatchX + hitRect[1];
         int iconWidth = hitRect[2];
         int iconHeight = hitRect[3];
         int iconCenterY = iconY + (iconHeight >> 1);
         int cellWidth = SpriteRenderer.frameWidth(683, 0);
         gfx.setClip(iconX, iconY, iconWidth, iconHeight);
         boolean hasVariants = this.variantSlot != -1;
         int cellX = iconX + (this.variantScroll >> 16) + (hasVariants ? 0 : cellWidth >> 1);

         for (int variantIndex = this.optionMin; variantIndex <= this.optionMax; variantIndex++) {
            if (hasVariants) {
               Lot lot = canvas.lot;
               int variantSlot = this.variantSlot;
               byte[] variantValues = lot.tableRowAt(variantSlot);
               int[] colors = lot.paletteAt(variantValues[variantIndex]);
               this.drawVariantCell(gfx, cellX, iconCenterY, colors, variantIndex == this.optionValue);
            } else {
               int cellFrameId = variantIndex == this.optionValue ? this.optionFrameSelected : this.optionFrameNormal;
               SpriteRenderer.drawFrame(gfx, cellFrameId, variantIndex, cellX, iconCenterY);
            }

            cellX += cellWidth;
         }

         gfx.setClip(clipX, clipY, clipWidth, clipHeight);
         if (this.optionValue > this.optionMin || optionId == 723) {
            SpriteRenderer.hitTest(hitRect, rowHeight, 0, 0);
            super.leftArrowAnim.draw(gfx, panelCenterX + hitRect[0], swatchX + hitRect[1]);
         }

         if (this.optionValue < this.optionMax || optionId == 723) {
            SpriteRenderer.hitTest(hitRect, rowHeight, 0, 1);
            super.rightArrowAnim.draw(gfx, panelCenterX + hitRect[0], swatchX + hitRect[1]);
         }
      }

      canvas.drawBottomBar(gfx);
   }

   private void drawVariantCell(Graphics gfx, int xPos, int yPos, int[] colors, boolean selected) {
      int frameId = selected ? 683 : 682;
      SpriteRenderer.drawFrame(gfx, frameId, 0, xPos, yPos);
      int[] hitRect = super.hitRect;
      SpriteRenderer.hitTestMirrored(super.hitRect, 682, 0, 0);
      int cellX = xPos + hitRect[0];
      int cellY = yPos + hitRect[1];
      int cellWidth = hitRect[2];
      int cellHeight = hitRect[3];
      gfx.setColor(colors[1]);
      gfx.fillRect(cellX, cellY, cellWidth, cellHeight);
      gfx.setColor(colors[3]);
      gfx.fillRect(cellX + 1, cellY + 1, cellWidth - 2, 3);
      gfx.setColor(colors[2]);
      gfx.fillRect(cellX + 1, cellY + 1 + 3, cellWidth - 2, 5);
   }

   private void appearanceKeyPressed(int keyCode, int action) {
      this.scrollList(keyCode, action);
      if (this.matchesInput(keyCode, action, 0, 4)) {
         this.onTick(10);
      } else if (this.matchesInput(keyCode, action, 0, 2)) {
         this.onTick(12);
      } else {
         if (this.matchesInput(keyCode, action, 4128, 16)) {
            int traitIndex = super.selectedIndex;
            super.canvas.neighborhood.setSelectedResident(traitIndex);
            super.canvas.setSoftKeys(4, 2);
         }
      }
   }

   private void drawPersonaScreen(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      this.drawPanel(gfx, 18);
      int width = canvas.getWidth();
      int height = canvas.getHeight() - 15;
      this.drawSimPreview(gfx, width, height, true);
      canvas.drawBottomBar(gfx);
   }

   private void layoutPersonaList() {
      Neighborhood neighborhood = super.canvas.neighborhood;
      this.beginList(neighborhood.residentLabelCount(), 15, 0);
   }

   public final void drawPersonaRows(Graphics gfx, int xPos, int yPos, int width, int height) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = super.canvas.neighborhood;
      int centerX = xPos + (width >> 1);
      int rowY = yPos;
      int startIndex = super.scrollOffset;
      int selectedIndex = super.selectedIndex;

      while (rowY < yPos + height && startIndex < super.itemCount) {
         short traitId = neighborhood.residentLabelText(startIndex);
         byte frameId = -1;
         byte rowFrame;
         byte textFrame;
         if (startIndex == neighborhood.selectedResident) {
            textFrame = 0;
            rowFrame = 1;
            frameId = 75;
         } else {
            textFrame = 1;
            rowFrame = 2;
         }

         if (startIndex == selectedIndex) {
            frameId = 82;
         }

         if (frameId != -1) {
            this.drawClippedRow(gfx, xPos, rowY, width, 15, yPos + height, frameId, startIndex);
         }

         canvas.drawStringById(traitId, textFrame, centerX, rowY + rowFrame, 17);
         rowY += 15;
         startIndex++;
         BaseScreen.drawHLine(gfx, xPos, rowY, width);
      }
   }

   public final int drawTextBox(Graphics gfx, int xPos, int yPos, int width) {
      int height = 8 + 4 * super.textLineHeight;
      if (gfx != null) {
         this.drawRaisedBox(gfx, xPos, yPos, width, height);
         int innerWidth = width - 8 - 15;
         int textX = xPos + 4;
         int textY;
         int valueY = (textY = yPos + 4) + super.textLineHeight;
         GameCanvas canvas = super.canvas;
         super.canvas.drawStringById(722, 1, textX, textY, 20);
         int selectedIndex = super.selectedIndex;
         short value = canvas.neighborhood.residentInfoText(selectedIndex);
         canvas.drawSlotText(0, value, 1, innerWidth, textX, valueY, 20);
      }

      return height;
   }

   private void updateNameEntryMarquees(int deltaMs) {
      super.canvas.updateMarquees(deltaMs);
   }

   private void traitKeyPressed(int keyCode, int action) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = super.canvas.neighborhood;
      this.scrollList(keyCode, action);
      if (this.matchesInput(keyCode, action, 0, 4)) {
         this.onTick(11);
      } else if (this.matchesInput(keyCode, action, 0, 2)) {
         this.onTick(13);
      } else {
         if (this.matchesInput(keyCode, action, 4128, 16)) {
            int interestIndex = super.selectedIndex;
            if (neighborhood.simKnownIndex(0, interestIndex) == -1) {
               neighborhood.addKnown(interestIndex);
            } else {
               neighborhood.removeKnown(interestIndex);
            }

            int status = canvas.neighborhood.simKnownFreeSlot();
            boolean available = 5 == status;
            canvas.setSoftKeys(4, available ? 2 : 0);
         }
      }
   }

   private void drawTraitScreen(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      this.drawPanel(gfx, 19);
      int width = canvas.getWidth();
      int height = canvas.getHeight() - 15;
      this.drawSimPreview(gfx, width, height, true);
      canvas.drawBottomBar(gfx);
   }

   private void layoutTraitList() {
      this.beginList(Neighborhood.initialScreenId(), 15, 0);
   }

   public final void drawTraitRows(Graphics gfx, int xPos, int yPos, int width, int height) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = super.canvas.neighborhood;
      int centerX = xPos + (width >> 1);
      int rowY = yPos;
      int startIndex = super.scrollOffset;
      int selectedIndex = super.selectedIndex;

      while (rowY < yPos + height && startIndex < super.itemCount) {
         short interestId = neighborhood.itemNameText(startIndex);
         byte frameId = -1;
         byte rowFrame;
         byte textFrame;
         if (neighborhood.simKnownIndex(0, startIndex) != -1) {
            textFrame = 0;
            rowFrame = 1;
            frameId = 75;
         } else {
            textFrame = 1;
            rowFrame = 2;
         }

         if (startIndex == selectedIndex) {
            frameId = 82;
         }

         if (frameId != -1) {
            this.drawClippedRow(gfx, xPos, rowY, width, 15, yPos + height, frameId, startIndex);
         }

         canvas.drawStringById(interestId, textFrame, centerX, rowY + rowFrame, 17);
         rowY += 15;
         startIndex++;
         BaseScreen.drawHLine(gfx, xPos, rowY, width);
      }
   }

   private static final int keyToCharIndex(int keyCode) {
      switch (keyCode) {
         case 1:
            return 9;
         case 2:
            return 0;
         case 4:
            return 1;
         case 8:
            return 2;
         case 16:
            return 3;
         case 32:
            return 4;
         case 64:
            return 5;
         case 128:
            return 6;
         case 256:
            return 7;
         case 512:
            return 8;
         default:
            return -1;
      }
   }

   private void resetNameEntry() {
      this.multiTapActive = false;
      this.nameCursor = super.canvas.profileName().length;
      this.keyRepeatTimer = 0;
      this.repeatKey = -1;
      this.keyCharIndex = 0;
   }

   private void nameKeyPressed(int keyIndex) {
      GameCanvas canvas = super.canvas;
      super.canvas.clearAction(1023);
      char[] chars = KEY_CHARS[keyIndex];
      if (this.multiTapActive && keyIndex == this.repeatKey) {
         this.keyRepeatTimer = 0;
         this.keyCharIndex = (this.keyCharIndex + 1) % chars.length;
         canvas.setProfileNameChar(this.nameCursor, chars[this.keyCharIndex]);
      } else {
         this.endMultiTap();
         if (canvas.profileNameLength() != 8) {
            canvas.insertProfileNameChar(this.nameCursor, chars[0]);
            this.multiTapActive = true;
            this.keyRepeatTimer = 0;
            this.repeatKey = keyIndex;
            this.keyCharIndex = 0;
         }
      }
   }

   private void endMultiTap() {
      if (this.multiTapActive) {
         this.multiTapActive = false;
         if (this.nameCursor < 8) {
            this.nameCursor++;
         }

         this.repeatKey = -1;
      }
   }

   private void updateNameEntry(int deltaMs) {
      GameCanvas canvas = super.canvas;
      this.keyRepeatTimer += deltaMs;
      this.hintScrollX -= deltaMs * 1638;
      this.hintWidth = canvas.stringWidthById(957, 0);
      if (this.hintScrollX < -(this.hintWidth << 16)) {
         this.hintScrollX = canvas.getWidth() << 16;
      }

      if (this.multiTapActive && this.keyRepeatTimer >= 1250) {
         this.endMultiTap();
      }
   }

   private void nameEntryKeyPressed(int keyCode, int action) {
      GameCanvas canvas = super.canvas;
      int nameLength = super.canvas.profileNameLength();
      if (this.matchesInput(keyCode, action, 1023, 0)) {
         int charIndex;
         if ((charIndex = keyToCharIndex(keyCode)) != -1) {
            this.nameKeyPressed(charIndex);
            canvas.setSoftKeys(4, canvas.profileNameLength() != 0 ? 2 : 0);
         }
      } else {
         if (this.matchesInput(keyCode, action, 4096, 2)) {
            if (canvas.profileNameLength() != 0) {
               this.onTick(18);
               return;
            }
         } else {
            if (this.matchesInput(keyCode, action, 524288, 0)) {
               this.endMultiTap();
               if (nameLength == 0) {
                  this.onTick(12);
                  return;
               }

               if (this.nameCursor > 0) {
                  this.nameCursor--;
               }

               canvas.deleteProfileNameChar(this.nameCursor);
               canvas.setSoftKeys(4, canvas.profileNameLength() != 0 ? 2 : 0);
               return;
            }

            if (this.matchesInput(keyCode, action, 0, 4)) {
               this.onTick(12);
               return;
            }

            if (this.matchesInput(keyCode, action, 32768, 0)) {
               this.endMultiTap();
               if (this.nameCursor != 0) {
                  this.nameCursor--;
                  return;
               }
            } else if (this.matchesInput(keyCode, action, 65536, 0)) {
               this.endMultiTap();
               if (this.nameCursor != nameLength) {
                  this.nameCursor++;
               }
            }
         }
      }
   }

   private void drawNameEntryScreen(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      Neighborhood neighborhood = super.canvas.neighborhood;
      drawPanelGradient(gfx, 0, 0, canvas.getWidth(), canvas.getHeight() - 15);
      int width = canvas.getWidth();
      int height = canvas.getHeight() - 15;
      int boxWidth = width * 7 / 10;
      int boxX = width - boxWidth >> 1;
      int boxHeight = super.rowHeight * 6 + 6 + 22 + 5;
      int boxY = height - boxHeight + 5;
      int boxTop = this.drawIconPanel(gfx, boxX, boxWidth, 746, -1);
      SdkString name = canvas.profileName();
      int nameWidth = canvas.stringWidth(name, 1);
      int nameX = boxX + (boxWidth - nameWidth >> 1);
      int nameY = boxTop - 16;
      int lineHeight = canvas.lineHeight(1) - 2;
      canvas.drawString(name, 1, nameX, nameY, 20);
      int cursorPos = -1;
      if (this.multiTapActive || this.keyRepeatTimer / 750 % 2 == 1) {
         cursorPos = this.nameCursor;
      }

      if (cursorPos != -1) {
         int cursorWidth = 5;
         int cursorX = nameX;
         if (name != null) {
            cursorX = nameX + SdkText.substringWidth(name, 0, cursorPos);
            if (cursorPos != canvas.profileNameLength()) {
               cursorWidth = SdkText.substringWidth(name, cursorPos, 1);
            }
         }

         int cursorY = nameY + lineHeight;
         SpriteRenderer.setColor(gfx, 0);
         gfx.fillRect(cursorX, cursorY, cursorWidth, 1);
      }

      int centerX = width >> 1;
      nameWidth = boxTop + (height - boxTop - boxHeight >> 1);
      SpriteRenderer.drawFrame(gfx, 709, 0, centerX, nameWidth);
      int[] hitRect = super.hitRect;
      SpriteRenderer.hitTest(super.hitRect, 709, 0, 0);
      nameY = centerX + hitRect[0];
      lineHeight = nameWidth + hitRect[1];
      this.drawCenteredLabel(gfx, nameY, lineHeight);
      short traitId = neighborhood.residentLabelText(neighborhood.selectedResident);
      this.drawDialogFrame(gfx, boxX, boxY, boxWidth, boxHeight, traitId, 1);
      nameWidth = boxX + 3;
      nameX = boxY + 22;
      nameY = boxWidth - 6;
      lineHeight = super.rowHeight * 5 + 6;
      this.drawRaisedBox(gfx, nameWidth, nameX, nameY, lineHeight);
      cursorPos = nameWidth + (nameY >> 1);
      int rowY = nameX + 3 + 1;

      for (int row = 0; row < 5; row++) {
         int traitSlot = neighborhood.simKnownId(0, row);
         short traitName = neighborhood.itemNameText(traitSlot);
         canvas.drawStringById(traitName, 1, cursorPos, rowY, 17);
         rowY += super.rowHeight;
      }

      int hintY = canvas.getHeight() - 30;
      if (GameCanvas.landscape) {
         canvas.drawStringById(957, 0, this.hintScrollX >> 16, hintY, 20);
      }

      canvas.drawBottomBar(gfx);
   }

   private void profileSlotKeyPressed(int keyCode, int action) {
      GameCanvas canvas = super.canvas;
      int selected;
      int previous = selected = this.newSimSelected ? 2 : canvas.profileSlot;
      int slotCount = 2 + (state == 14 ? 1 : 0);
      if (this.matchesInput(keyCode, action, 0, 68)) {
         this.onTick(state == 14 ? 7 : 14);
      } else if (this.matchesInput(keyCode, action, 4128, 48)) {
         if (this.newSimSelected) {
            this.onTick(15);
         } else if (state == 15) {
            if (canvas.isProfileUsed()) {
               this.onTick(16);
            }
         } else if (canvas.isProfileUsed()) {
            canvas.reloadGameData();
            this.requestScreen(2, -1);
         } else {
            this.resetForNewSim();
         }
      } else if (this.matchesInput(keyCode, action, 8196, 0)) {
         selected = (selected + slotCount - 1) % slotCount;
      } else if (this.matchesInput(keyCode, action, 16640, 0)) {
         selected = (selected + 1) % slotCount;
      }

      if (selected != previous) {
         this.newSimSelected = selected == 2;
         if (!this.newSimSelected) {
            canvas.selectProfile(selected);
            if (canvas.isProfileUsed()) {
               this.applySimAppearance(false);
            }
         }

         boolean used = canvas.isProfileUsed();
         if (state == 15) {
            canvas.setSoftKeys(64, used ? 32 : 0);
         }
      }
   }

   private void drawProfileScreen(Graphics gfx) {
      GameCanvas canvas = super.canvas;
      int width = super.canvas.getWidth();
      int height;
      int listHeight = (height = canvas.getHeight() - 15) - 6;
      int rowY = 3 + listHeight - (panelGradientHeight(listHeight) >> 1);
      int panelWidth = width - 3 - 64 - 3 - 3;
      int colorIndex = state == 14 ? 77 : 43;
      SpriteRenderer.setColor(gfx, colorIndex);
      gfx.fillRect(0, 0, width, height);
      colorIndex = gfx.getClipX();
      int clipX = gfx.getClipY();
      int clipY = gfx.getClipWidth();
      int clipWidth = gfx.getClipHeight();
      gfx.setClip(colorIndex, 3, clipY, listHeight);
      drawPanelGradient(gfx, 3, 3, 64, listHeight);
      gfx.setClip(colorIndex, clipX, clipY, clipWidth);
      SpriteRenderer.setColor(gfx, 0);
      gfx.drawRect(3, 3, 63, listHeight - 1);
      if (!this.newSimSelected && canvas.isProfileUsed()) {
         this.drawSimPreview(gfx, 35, rowY, false);
      }

      clipX = state == 14 ? 64 : 65;
      clipX = this.drawIconRow(gfx, 70, panelWidth, clipX) + 3;
      clipWidth = height - 3 - 19;
      int rowHeight = (super.rowHeight << 2) + 8;
      int statsY = clipWidth - 3 - rowHeight;
      int rowGap = (height - clipX - 3 - 19 - 3 - rowHeight - 3 - 3) / 2;
      int rowTop = clipX;

      for (int slot = 0; slot < 2; slot++) {
         boolean selected = !this.newSimSelected && slot == canvas.profileSlot;
         SdkString profileName = canvas.isProfileUsedAt(slot) ? canvas.profileNameAt(slot) : canvas.getString(52);
         this.drawButtonLabel(gfx, 70, rowTop, panelWidth, rowGap, selected, profileName);
         rowTop += rowGap + 3;
      }

      this.drawPlainBox(gfx, 70, statsY, panelWidth, rowHeight);
      if (!this.newSimSelected && canvas.isProfileUsed()) {
         Neighborhood neighborhood = canvas.neighborhood;
         rowGap = 70 + panelWidth - 4;
         rowTop = statsY + 4 + 1;
         short simNameId = neighborhood.residentLabelText(neighborhood.selectedResident);
         canvas.drawStringById(simNameId, 1, 74, rowTop, 20);
         rowTop += super.rowHeight;
         int money = neighborhood.moodTextId();
         canvas.drawStringById(521, 1, 74, rowTop, 20);
         canvas.drawStringById(money, 1, rowGap, rowTop, 24);
         rowTop += super.rowHeight;
         int careerLevel = neighborhood.simLot(0);
         int careerId = neighborhood.lotNameText(careerLevel);
         canvas.drawStringById(646, 1, 74, rowTop, 20);
         canvas.drawStringById(careerId, 1, rowGap, rowTop, 24);
         rowTop += super.rowHeight;
         SdkString balance = canvas.beginString();
         canvas.formatNumber(neighborhood.money);
         canvas.drawString(balance, 1, 74, rowTop, 20);
      }

      if (state == 14) {
         this.drawButton(gfx, 70, clipWidth, panelWidth, 19, this.newSimSelected, 54);
      }

      canvas.drawBottomBar(gfx);
   }
}
