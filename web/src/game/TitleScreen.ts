// Generated from TitleScreen.java by tools/web/JavaToTs.java; keep this file in sync.
import { Thread, InterruptedException, StringBuffer, Graphics } from '../runtime';
import { BaseScreen } from './BaseScreen';
import { SdkString } from './SdkString';
import { Animator } from './Animator';
import { GameCanvas } from './GameCanvas';
import { SdkText } from './SdkText';
import { SdkSound } from './SdkSound';
import { SpriteRenderer } from './SpriteRenderer';
import { PromoImages } from './PromoImages';
import { FixedMath } from './FixedMath';
import { Neighborhood } from './Neighborhood';
import { Lot } from './Lot';

export class TitleScreen extends BaseScreen {
	static state: number = 0;
	pendingState: number = 0;
	pendingAction: number = 0;
	pendingParam: number = -1;
	languageIndex: number = 0;
	mainMenuItems: Int16Array = null!;
	loadStep: number = 0;
	loaderThread: Thread = null!;
	loaderScheduled: boolean = false;
	loaderState: number = 0;
	languageMenuItems: Int16Array = null!;
	languageNames: SdkString[] = null!;
	introStep: number = 0;
	introElapsed: number = 0;
	introDuration: number = 0;
	simAnimator: Animator = null!;
	simFrameId: number = 0;
	static optionIds: Int16Array = Int16Array.from([723, 724, 725, 726, 727, 728, 729, 730, 731, 732, 733, 734, 735]);
	recolorFrom: Int32Array = new Int32Array(32);
	recolorTo: Int32Array = new Int32Array(32);
	upArrowAnimator: Animator = null!;
	downArrowAnimator: Animator = null!;
	optionIndex: number = 0;
	optionScroll: number = 0;
	optionScrollTarget: number = 0;
	optionSlot: number = 0;
	variantSlot: number = 0;
	variantScroll: number = 0;
	variantScrollTarget: number = 0;
	optionIconId: number = 0;
	optionFrameSelected: number = 0;
	optionFrameNormal: number = 0;
	optionValue: number = 0;
	optionMin: number = 0;
	optionMax: number = 0;
	longSleeves: boolean = false;
	longPants: boolean = false;
	hairHasAccent: boolean = false;
	static scrollBarGradient: Int32Array = Int32Array.from([11, 12, 13, 14, 15, 14, 13, 12, 11]);
	static panelGradientTop: Int32Array = Int32Array.from([16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30]);
	static panelGradientBottom: Int32Array = Int32Array.from([31, 32, 33, 34, 35, 36, 37, 38, 39]);
	static KEY_CHARS: Uint16Array[] = [Uint16Array.from([49]), Uint16Array.from([65, 66, 67, 50]), Uint16Array.from([68, 69, 70, 51]), Uint16Array.from([71, 72, 73, 52]), Uint16Array.from([74, 75, 76, 53]), Uint16Array.from([77, 78, 79, 54]), Uint16Array.from([80, 81, 82, 83, 55]), Uint16Array.from([84, 85, 86, 56]), Uint16Array.from([87, 88, 89, 90, 57]), Uint16Array.from([48])];
	multiTapActive: boolean = false;
	nameCursor: number = 0;
	keyRepeatTimer: number = 0;
	repeatKey: number = 0;
	keyCharIndex: number = 0;
	hintWidth: number = 0;
	hintScrollX: number = 0;
	newSimSelected: boolean = false;
	setLanguage(languageIndex: number): void {
		this.initLayout();
		this.canvas.clearNewFlags();
		this.buildLanguageMenu();
		if (languageIndex === -1) {
			languageIndex = 7;
		}
		this.languageIndex = languageIndex;
		if (languageIndex !== 4 && languageIndex !== 5) {
			this.onTick(1);
		}
		else {
			this.languageIndex = 6;
			this.onTick(languageIndex);
		}
		this.loadStep = 0;
	}
	/**
	 * The Java build runs this on a worker thread that waits for key presses; the
	 * port pumps one step per press from the event loop instead.
	 */
	startLoading(loadingTipDelta: number): void {
		this.canvas.updateLoadingTip(loadingTipDelta);
		this.loaderState = 0;
		if (!this.loaderScheduled) {
			this.loaderScheduled = true;
			setTimeout((): void => {
				this.loaderScheduled = false;
				if (this.loaderState === 0) {
					this.loaderState = 1;
					try {
						this.loadNextStep();
					}
					catch (ignored) {
					}
				}
			}, 0);
		}
	}
	loadNextStep(): void {
		let canvas: GameCanvas = this.canvas;
		if (!this.canvas.isFading()) {
			new StringBuffer().append("updateLoadingState: ").append(this.loadStep).toString();
			switch (this.loadStep) {
				case 0:
					let items: Int16Array = new Int16Array(15);
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
	playClickSound(): void {
		this.canvas.soundValue.play(10);
	}
	stop(): void {
	}
	pause(): void {
		this.canvas.unloadTiles(3076, -1);
		this.canvas.setSoundLoop(-1);
		SdkSound.muteAll();
		SdkText.freeStringsChunk(1);
	}
	render(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let menuMode: number = this.canvas.menuMode;
		if (this.canvas.menuMode !== -1) {
			if (menuMode === 5) {
				if (TitleScreen.state === 4) {
					this.fillScreen(gfx, 77);
				}
				else {
					this.drawIntroLogo(gfx);
				}
				this.drawLanguageMenu(gfx);
			}
			else {
				this.drawIntroLogo(gfx);
				canvas.drawMenu(gfx);
				canvas.drawBottomBar(gfx);
			}
		}
		else {
			switch (TitleScreen.state) {
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
	update(keyCode: number, action: number): void {
		if (TitleScreen.state !== 1) {
			let canvas: GameCanvas = this.canvas;
			if (this.canvas.fadeFinished && this.pendingState !== 0) {
				this.onTick(this.pendingState);
				canvas.fadeFromBlack();
				this.pendingState = 0;
			}
			else {
				if (!canvas.isFading()) {
					let menuMode: number = canvas.menuMode;
					if (canvas.menuMode !== -1) {
						if (menuMode === 5) {
							this.languageMenuKeyPressed(keyCode, action);
						}
						else {
							canvas.menuKeyPressed(keyCode, action);
						}
						if (canvas.menuMode === -1) {
							this.onTick(TitleScreen.state);
						}
					}
					else {
						switch (TitleScreen.state) {
							case 4:
								this.languageMenuKeyPressed(keyCode, action);
								return;
							case 5:
								if (this.matchesInput(keyCode, action, 4128, 256)) {
									canvas.soundValue.setEnabled(true);
									this.fadeToState(1);
									return;
								}
								if (this.matchesInput(keyCode, action, 0, 512)) {
									canvas.soundValue.setEnabled(false);
									this.fadeToState(1);
									return;
								}
								break;
							case 6:
								this.introKeyPressed(keyCode, action);
								return;
							case 7:
								if (this.matchesInput(keyCode, action, 4128, 16)) {
									let selectedIndex: number = ((5 + this.mainMenuItems[2]) | 0);
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
	}
	keyPressed(keyCode: number): void {
		if (TitleScreen.state === 1) {
			this.startLoading(keyCode);
		}
		else {
			switch (TitleScreen.state) {
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
	onTick(newState: number): void {
		let canvas: GameCanvas = this.canvas;
		let previousState: number = TitleScreen.state;
		TitleScreen.state = newState;
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
				SpriteRenderer.loadTile(this.canvas.resources, 82);
				canvas.unloadTiles(2, 0);
				canvas.setSoftKeys(0, 16);
				return;
			case 8:
				this.resetList();
				canvas.setSoftKeys(4, 0);
				return;
			case 9:
				SpriteRenderer.loadTile(this.canvas.resources, 48);
				SpriteRenderer.loadTile(this.canvas.resources, 47);
				SpriteRenderer.loadTile(this.canvas.resources, 46);
				SpriteRenderer.loadTile(this.canvas.resources, 45);
				SpriteRenderer.loadTile(this.canvas.resources, 44);
				SpriteRenderer.loadTile(this.canvas.resources, 43);
				canvas.promoActive = true;
				canvas.promoImages.reset();
				return;
			case 10:
				this.upArrowAnimator.play(681, 20);
				this.downArrowAnimator.play(679, 20);
				if (previousState !== 11 && previousState !== 17) {
					this.setSimOption(0, 0);
				}
				else {
					if (previousState === 11) {
						this.setSimFrames(false);
					}
				}
				canvas.setSoftKeys(4, 2);
				return;
			case 11:
				this.layoutPersonaList();
				this.setSimFrames(true);
				canvas.setWrappedString(736, 1, this.panelHeight);
				canvas.setSoftKeys(4, canvas.neighborhood.selectedResident !== -1 ? 2 : 0);
				return;
			case 12:
				this.layoutTraitList();
				this.setSimFrames(true);
				canvas.setWrappedString(744, 1, this.panelHeight);
				let count: number = canvas.neighborhood.simKnownFreeSlot();
				let twoSlots: boolean = 5 === count;
				canvas.setSoftKeys(4, twoSlots ? 2 : 0);
				return;
			case 13:
				if (previousState !== 18) {
					this.resetNameEntry();
				}
				canvas.setSoftKeys(4, canvas.profileNameLength() !== 0 ? 2 : 0);
				return;
			case 14:
				this.newSimSelected = false;
				this.applySimAppearance(false);
				canvas.setSoftKeys(4, 16);
				return;
			case 15:
				this.newSimSelected = false;
				if (canvas.profileSlot !== 0) {
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
	fadeToState(nextState: number): void {
		this.pendingState = nextState;
		if (!this.canvas.isFadingFromWhite()) {
			this.canvas.fadeFromWhite();
		}
	}
	requestScreen(action: number, param: number): void {
		this.pendingAction = action;
		this.pendingParam = param;
		this.fadeToState(2);
	}
	buildMainMenu(selectedId: number): void {
		let items: Int16Array = this.mainMenuItems;
		GameCanvas.initListWith(this.mainMenuItems, 50, this.canvas.menuRowSpacing);
		GameCanvas.addToList(items, 51);
		GameCanvas.addToList(items, 59);
		GameCanvas.addToList(items, 60);
		GameCanvas.addToList(items, 61);
		if (this.canvas.promoImages.isAvailable()) {
			GameCanvas.addToList(items, 63);
		}
		GameCanvas.addToList(items, 42);
		GameCanvas.selectListValue(items, selectedId);
	}
	personaKeyPressed(menuId: number): void {
		let canvas: GameCanvas = this.canvas;
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
	promoKeyPressed(): void {
		let promo: PromoImages = this.canvas.promoImages;
		if (this.canvas.promoImages.available) {
			promo.handleKeys();
		}
		else {
			this.canvas.promoActive = false;
			this.onTick(7);
		}
	}
	buildLanguageMenu(): void {
		if (this.languageMenuItems === null) {
			let headerCount: number = this.canvas.headerCount;
			let items: Int16Array = new Int16Array(((5 + headerCount) | 0));
			this.languageMenuItems = items;
			GameCanvas.initListWith(items, 3, this.canvas.menuRowSpacing);
			let names: SdkString[] = new Array(((headerCount + 1) | 0)).fill(null);
			this.languageNames = names;
			for (let headerIndex: number = 0; headerIndex < headerCount; headerIndex++) {
				GameCanvas.addToList(items, headerIndex);
				names[headerIndex] = SdkText.getHeaderString(headerIndex, 0, null);
			}
			let languageIndex: number = this.canvas.language;
			if (this.canvas.language !== -1) {
				items[2] = (languageIndex << 16 >> 16);
			}
		}
	}
	drawLanguageMenu(gfx: Graphics): void {
		this.drawTwoColumnList(gfx, this.languageMenuItems, 1, this.languageNames);
		this.canvas.drawBottomBar(gfx);
	}
	languageMenuKeyPressed(keyCode: number, action: number): void {
		let canvas: GameCanvas = this.canvas;
		let previousId: number = this.languageMenuItems[2];
		if (this.matchesInput(keyCode, action, 4128, 16384)) {
			canvas.setLanguage(previousId);
			canvas.saveSettings();
			if (canvas.menuMode === 5) {
				canvas.setMenuMode(0);
			}
			else {
				this.fadeToState(5);
			}
		}
		else {
			if (this.matchesInput(keyCode, action, 524288, 32768)) {
				if (canvas.menuMode === 5) {
					SdkText.setCurrentLanguage(canvas.language);
					canvas.setMenuMode(0);
					return;
				}
			}
			else {
				this.canvas.navigateLeftRight(this.languageMenuItems, keyCode);
				let selectedId: number = 0;
				if ((selectedId = this.languageMenuItems[2]) !== previousId) {
					SdkText.setCurrentLanguage(selectedId);
				}
			}
		}
	}
	restartIntro(): void {
		this.canvas.clearFade();
		this.setIntroStep(0);
	}
	setIntroStep(introStepIndex: number): void {
		let waitMs: number = 0;
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
				waitMs = Math.imul(this.mainMenuItems[0], 200);
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
	advanceIntro(deltaMs: number): void {
		this.introElapsed = ((this.introElapsed + deltaMs) | 0);
		if (this.introElapsed >= this.introDuration) {
			if (this.introStep === 13) {
				this.introElapsed = ((this.introElapsed % 750) | 0);
				return;
			}
			this.setIntroStep(((this.introStep + 1) | 0));
		}
	}
	introKeyPressed(keyCode: number, action: number): void {
		if (this.introStep === 13 && this.matchesInput(keyCode, action, 4128, 0)) {
			this.setIntroStep(((this.introStep + 1) | 0));
		}
	}
	drawIntro(gfx: Graphics): void {
		switch (this.introStep) {
			case 0:
				this.fillScreen(gfx, 0);
				return;
			case 1:
			case 2:
			case 3:
			case 4:
				this.drawIntroBackground(gfx);
				return;
			case 5:
				this.fillFadeBlack(gfx, this.introElapsed, this.introDuration);
				return;
			case 6:
				this.fillScreen(gfx, 1);
				return;
			case 7:
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
	fillFadeBlack(gfx: Graphics, elapsed: number, duration: number): void {
		let canvas: GameCanvas = this.canvas;
		let alpha: number = Math.min(((Math.imul(elapsed, 255) / duration) | 0), 255);
		let color: number = -16777216 | alpha << 16 | alpha << 8 | alpha << 0;
		gfx.setColor(color);
		gfx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	fillScreen(gfx: Graphics, colorIndex: number): void {
		let canvas: GameCanvas = this.canvas;
		SpriteRenderer.setColor(gfx, colorIndex);
		gfx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	drawIntroBackground(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		this.fillScreen(gfx, 60);
		SpriteRenderer.drawFrame(gfx, 556, 0, canvas.halfWidth(), canvas.halfHeight());
	}
	drawIntroSim(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let frameId: number = 0;
		if (GameCanvas.currentLandscape === 1) {
			frameId = 558;
		}
		else {
			frameId = 557;
		}
		let centerX: number = canvas.halfWidth();
		let centerY: number = canvas.halfHeight();
		let progress: number = 0;
		if (this.introDuration !== 0) {
			progress = FixedMath.div(this.introElapsed, this.introDuration);
		}
		if (this.introStep >= 12) {
			SpriteRenderer.drawFrame(gfx, frameId, 8, centerX, centerY);
		}
		if (this.introStep >= 11) {
			SpriteRenderer.drawFrame(gfx, frameId, 6, centerX, centerY);
		}
		SpriteRenderer.drawFrame(gfx, frameId, 0, centerX, centerY);
		let simX: number = centerX;
		if (this.introStep === 10) {
			let width: number = canvas.getWidth();
			simX = TitleScreen.easeValue(width, centerX, progress, 3);
		}
		let shiftX: number = Math.min(0, ((simX - centerX) | 0));
		if (this.introStep >= 8) {
			let doorTop: number = ((SpriteRenderer.frameHeight(frameId, 1) * -1) | 0);
			let doorY: number = centerY;
			if (this.introStep === 8) {
				doorY = TitleScreen.easeValue(doorTop, centerY, progress, 4);
			}
			if (doorY >= centerY) {
				SpriteRenderer.drawFrame(gfx, frameId, 2, centerX, centerY);
			}
			SpriteRenderer.drawFrame(gfx, frameId, 1, ((centerX + shiftX) | 0), doorY);
		}
		if (this.introStep >= 9) {
			let headX: number = centerX;
			if (this.introStep === 9) {
				headX = TitleScreen.easeValue(0, centerX, progress, 3);
			}
			let hitRect: Int32Array = this.hitRect;
			SpriteRenderer.hitTest(this.hitRect, frameId, 3, 0);
			this.drawSampleLetter(((((headX + shiftX) | 0) + hitRect[0]) | 0), ((centerY + hitRect[1]) | 0), 3);
		}
		if (this.introStep >= 10) {
			SpriteRenderer.drawFrame(gfx, frameId, 4, simX, centerY);
			if (simX <= centerX) {
				SpriteRenderer.drawFrame(gfx, frameId, 5, simX, centerY);
			}
		}
		if (this.introStep === 13 && ((this.introElapsed % 750) | 0) < 375) {
			let pressRect: Int32Array = this.hitRect;
			SpriteRenderer.hitTest(this.hitRect, frameId, 7, 0);
			canvas.drawStringById(34, 0, ((centerX + pressRect[0]) | 0), ((centerY + pressRect[1]) | 0), 3);
		}
	}
	drawSampleLetter(xPos: number, yPos: number, fontIndex: number): void {
		let canvas: GameCanvas = this.canvas;
		let letter: SdkString = this.canvas.getString(25);
		canvas.drawString(letter, fontIndex, xPos, yPos, 3);
	}
	static easeValue(from: number, target: number, progress: number, kind: number): number {
		let range: number = ((target - from) | 0);
		switch (kind) {
			case 0:
			default:
				return ((from + FixedMath.clamp(FixedMath.mul(progress, range), 0, range)) | 0);
			case 1:
				progress = FixedMath.smoothStep(0, 65536, progress);
				return ((from + FixedMath.clamp(FixedMath.mul(progress, range), 0, range)) | 0);
			case 2:
				progress = FixedMath.smoothStep(0, 65536, ((32768 + (progress >> 1)) | 0));
				return ((from + FixedMath.clamp(FixedMath.mul(progress, range), 0, range)) | 0);
			case 3:
				if (progress < 58982) {
					progress = FixedMath.mul(FixedMath.smoothStep(0, 58982, progress), 68812);
				}
				else {
					let overshootOne: number = ((65536 - FixedMath.div(((progress - 58982) | 0), 6554)) | 0);
					progress = ((65536 + FixedMath.mul(3276, overshootOne)) | 0);
				}
				return ((from + FixedMath.mul(progress, range)) | 0);
			case 4:
				if (progress < 55705) {
					progress = FixedMath.mul(FixedMath.smoothStep(0, 55705, progress), 68812);
				}
				else {
					let overshootTwo: number = ((65536 - FixedMath.div(((progress - 55705) | 0), 9831)) | 0);
					progress = ((65536 + FixedMath.mul(3276, overshootTwo)) | 0);
				}
				return ((from + FixedMath.mul(progress, range)) | 0);
			case 5:
				if (progress < 49152) {
					progress = FixedMath.mul(FixedMath.smoothStep(0, 49152, progress), 98304);
				}
				else {
					let overshootThree: number = ((65536 - FixedMath.div(((progress - 49152) | 0), 16384)) | 0);
					progress = ((65536 + FixedMath.mul(32768, overshootThree)) | 0);
				}
				return ((from + FixedMath.mul(progress, range)) | 0);
			case 6:
				if (progress < 32768) {
					progress = FixedMath.mul(FixedMath.sinShifted(FixedMath.div(FixedMath.mul(progress, 102944), 32768)), 72089);
				}
				else {
					let fraction: number = 0;
					let angle: number = FixedMath.mul(fraction = FixedMath.div(((progress - 32768) | 0), 32768), 617662);
					let amplitude: number = ((6554 - FixedMath.mul(6554, fraction)) | 0);
					progress = ((FixedMath.mul(FixedMath.cos(angle), amplitude) + 65536) | 0);
				}
				return ((from + FixedMath.mul(progress, range)) | 0);
		}
	}
	drawIntroLogo(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let frameId: number = 0;
		if (GameCanvas.currentLandscape === 1) {
			frameId = 558;
		}
		else {
			frameId = 557;
		}
		let centerX: number = canvas.halfWidth();
		let centerY: number = canvas.halfHeight();
		SpriteRenderer.drawFrame(gfx, frameId, 8, centerX, centerY);
	}
	drawMainMenuBox(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let items: Int16Array = this.mainMenuItems;
		let count: number = this.mainMenuItems[0];
		let selectedIndex: number = items[2];
		let boxHeight: number = ((((((((Math.imul(count, 22) - 2) | 0) + 14) | 0) + 19) | 0) + 5) | 0);
		let boxWidth: number = ((Math.imul(canvas.getWidth(), 140) / 176) | 0);
		let boxX: number = ((canvas.getWidth() - boxWidth) | 0) >> 1;
		let boxY: number = (((((canvas.getHeight() - boxHeight) | 0) >> 1) + 2) | 0);
		let progress: number = 0;
		if (this.introDuration !== 0) {
			progress = FixedMath.div(this.introElapsed, this.introDuration);
		}
		let drawX: number = boxY;
		let drawY: number = boxHeight;
		if (this.introStep === 14) {
			drawY = TitleScreen.easeValue(27, boxHeight, progress, 0);
			drawX = (((((canvas.getHeight() - drawY) | 0) >> 1) + 2) | 0);
		}
		this.drawDialogFrame(gfx, boxX, drawX, boxWidth, drawY, -1, 4);
		if (this.introStep >= 15) {
			drawY = canvas.getWidth() >> 1;
			let titleHeight: number = 0;
			let titleY: number = titleHeight = ((boxY + 9) | 0);
			if (this.introStep === 15) {
				titleY = TitleScreen.easeValue(((SpriteRenderer.frameHeight(557, 9) * -1) | 0), titleHeight, progress, 3);
			}
			SpriteRenderer.drawFrame(gfx, 557, 9, drawY, titleY);
			let buttonRect: Int32Array = this.hitRect;
			SpriteRenderer.hitTest(this.hitRect, 557, 9, 0);
			this.drawSampleLetter(((drawY + buttonRect[0]) | 0), ((titleY + buttonRect[1]) | 0), 4);
		}
		if (this.introStep >= 16) {
			drawX = ((boxWidth - 14) | 0);
			drawY = ((boxX + 7) | 0);
			let animIndex: number = count;
			if (this.introStep === 16) {
				animIndex = ((this.introElapsed / 200) | 0);
				progress = FixedMath.div(((this.introElapsed % 200) | 0), 200);
			}
			let rowY: number = ((((boxY + 22) | 0) + 7) | 0);
			for (let row: number = 0; row < count; row++) {
				let itemId: number = items[((5 + row) | 0)];
				let itemY: number = rowY;
				if (row === animIndex) {
					itemY = ((rowY + TitleScreen.easeValue(-3, 0, progress, 5)) | 0);
				}
				else {
					if (row > animIndex) {
						break;
					}
				}
				this.drawButton(gfx, drawY, itemY, drawX, 20, row === selectedIndex, itemId);
				rowY = ((rowY + 22) | 0);
			}
		}
		if (this.introStep >= 17) {
			drawX = 0;
			if (this.introStep === 17) {
				drawX = ((15 - TitleScreen.easeValue(0, 15, progress, 0)) | 0);
			}
			canvas.drawBottomBar_2(gfx, drawX);
		}
	}
	resetForNewSim(): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		canvas.resetGameData();
		canvas.clearProfileName();
		this.longSleeves = neighborhood.simAttribute(0, 6) !== 0;
		this.longPants = neighborhood.simAttribute(0, 8) !== 0;
		this.hairHasAccent = this.hairStyleHasAccent(neighborhood.simAttribute(0, 2));
		this.nameCursor = 0;
		this.onTick(10);
		this.applySimAppearance(false);
	}
	applySimAppearance(immediate: boolean): void {
		this.setSimFrames(immediate);
		let canvas: GameCanvas = this.canvas;
		let lot: Lot = this.canvas.lot;
		let neighborhood: Neighborhood = canvas.neighborhood;
		let recolorFrom: Int32Array = this.recolorFrom;
		let recolorTo: Int32Array = this.recolorTo;
		lot.buildPaletteFor(0, recolorFrom, recolorTo);
		let tileMask: number = neighborhood.simAttribute(0, 0) === 0 ? 2048 : 1024;
		canvas.unloadTiles(3072, -1);
		canvas.loadTiles(tileMask, recolorFrom, recolorTo, 0);
	}
	drawSimPreview(gfx: Graphics, xPos: number, yPos: number, offsetByFrame: boolean): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		let animId: number = this.simAnimator.animId;
		if (offsetByFrame) {
			let hitRect: Int32Array = this.hitRect;
			SpriteRenderer.hitTest(this.hitRect, animId, 0, 1);
			xPos = ((xPos - hitRect[0]) | 0);
			yPos = ((yPos - hitRect[1]) | 0);
		}
		this.simAnimator.draw(gfx, xPos, yPos);
		let frameRect: Int32Array = this.hitRect;
		this.simAnimator.matchesFrame(frameRect, 0);
		let hairStyle: number = neighborhood.simAttribute(0, 2);
		SpriteRenderer.drawFrame(gfx, this.simFrameId, hairStyle, ((xPos + frameRect[0]) | 0), ((yPos + frameRect[1]) | 0));
		if (neighborhood.simAttribute(0, 8) !== 0) {
			let accentFrameId: number = animId === 703 ? 704 : animId === 706 ? 707 : 17091;
			SpriteRenderer.drawFrame(gfx, accentFrameId, 0, xPos, yPos);
		}
	}
	setSimFrames(greeting: boolean): void {
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		let animId: number = 0;
		let frameId: number = 0;
		if (this.canvas.neighborhood.simAttribute(0, 0) === 0) {
			if (greeting) {
				animId = 706;
				frameId = 708;
			}
			else {
				animId = 17090;
				frameId = 17092;
			}
		}
		else {
			animId = 703;
			frameId = 705;
		}
		this.simAnimator.play(animId, 4);
		this.simFrameId = frameId;
	}
	advanceSimAnimator(deltaMs: number, greeting: boolean): void {
		if (!this.simAnimator.isRunning()) {
			this.setSimFrames(greeting);
		}
		this.simAnimator.advance(deltaMs);
	}
	updateScrollAnimators(deltaMs: number): void {
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
		if (Math.abs(((this.optionScroll - this.optionScrollTarget) | 0)) < 32768) {
			this.optionScroll = this.optionScrollTarget;
		}
		if (Math.abs(((this.variantScroll - this.variantScrollTarget) | 0)) < 32768) {
			this.variantScroll = this.variantScrollTarget;
		}
	}
	optionKeyPressed(keyCode: number, action: number): void {
		if (this.matchesInput(keyCode, action, 0, 4)) {
			this.onTick(17);
		}
		else {
			if (this.matchesInput(keyCode, action, 0, 2)) {
				this.fadeToState(11);
			}
			else {
				if (this.matchesInput(keyCode, action, 8196, 0) && this.optionIndex > 0) {
					this.upArrowAnimator.play(680, 16);
					this.setSimOption(((this.optionIndex - 1) | 0), -1);
				}
				else {
					if (this.matchesInput(keyCode, action, 16640, 0) && this.optionIndex < ((TitleScreen.optionIds.length - 1) | 0)) {
						this.downArrowAnimator.play(678, 16);
						this.setSimOption(((this.optionIndex + 1) | 0), 1);
					}
					else {
						if (TitleScreen.optionIds[this.optionIndex] === 723) {
							if (this.matchesInput(keyCode, action, 32784, 0)) {
								this.selectVariant(((this.optionValue - 1) | 0));
								this.showLeftArrow();
								return;
							}
							if (this.matchesInput(keyCode, action, 65600, 0)) {
								this.selectVariant(((this.optionValue + 1) | 0));
								this.showRightArrow();
								return;
							}
						}
						else {
							if (this.matchesInput(keyCode, action, 32784, 0) && this.optionValue > this.optionMin) {
								this.setOptionValue(((this.optionValue - 1) | 0), false);
								this.showLeftArrow();
								this.applySimAppearance(false);
								return;
							}
							if (this.matchesInput(keyCode, action, 65600, 0) && this.optionValue < this.optionMax) {
								this.setOptionValue(((this.optionValue + 1) | 0), false);
								this.showRightArrow();
								this.applySimAppearance(false);
							}
						}
					}
				}
			}
		}
	}
	selectVariant(variant: number): void {
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		let variantCount: number = this.canvas.neighborhood.portraitCount;
		if (variant < 0) {
			variant = ((variantCount - 1) | 0);
		}
		else {
			if (variant >= variantCount) {
				variant = 0;
			}
		}
		this.optionValue = variant;
		let values: Int8Array = neighborhood.portraitData(variant);
		for (let index: number = 0; index < 10; index++) {
			neighborhood.setAttribute(index, values[index]);
		}
		this.longSleeves = values[10] === 0;
		this.longPants = values[11] === 0;
		this.applySimAppearance(false);
	}
	setSimOption(index: number, step: number): void {
		let canvas: GameCanvas = this.canvas;
		if (!this.longSleeves && TitleScreen.optionIds[index] === 731) {
			index = ((index + step) | 0);
		}
		if (!this.longPants && TitleScreen.optionIds[index] === 734) {
			index = ((index + step) | 0);
		}
		if (!this.hairHasAccent && TitleScreen.optionIds[index] === 728) {
			index = ((index + step) | 0);
		}
		let listHeight: number = ((((canvas.getHeight() - 15) | 0) - (SpriteRenderer.frameHeight(697, 0) << 1)) | 0);
		let frameCount: number = SpriteRenderer.frameCount(687);
		let frameHeight: number = SpriteRenderer.frameHeight(688, 0);
		let scrollY: number = (((((((listHeight >> 1) + 2) | 0) - Math.imul(frameHeight, index)) | 0) - (frameHeight >> 1)) | 0);
		let minScroll: number = ((((listHeight - 2) | 0) - Math.imul(frameHeight, frameCount)) | 0);
		scrollY = FixedMath.clamp(scrollY, minScroll, 2);
		this.optionScrollTarget = scrollY << 16;
		if (step === 0) {
			this.optionScroll = scrollY << 16;
		}
		this.optionIndex = index;
		let iconId: number = 196;
		let selectedIconId: number = 196;
		let slotIndex: number = -1;
		let variantSlot: number = -1;
		let minValue: number = 0;
		switch (TitleScreen.optionIds[index]) {
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
				if (canvas.neighborhood.simAttribute(0, 0) === 1) {
					iconId = 692;
					selectedIconId = 693;
				}
				else {
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
		if (variantSlot !== -1) {
			this.optionMax = ((canvas.lot.tableRowAt(variantSlot).length - 1) | 0);
		}
		else {
			this.optionMax = ((SpriteRenderer.frameCount(iconId) - 1) | 0);
		}
		if (slotIndex !== -1) {
			this.setOptionValue(canvas.neighborhood.simAttribute(0, slotIndex), true);
		}
		else {
			this.setOptionValue(this.optionValue, true);
		}
	}
	hairStyleHasAccent(hairStyle: number): boolean {
		let mask: number = 1 << hairStyle;
		if (this.canvas.neighborhood.simAttribute(0, 0) === 0) {
			mask = (mask << 16);
		}
		return (mask & 786445) !== 0;
	}
	setOptionValue(value: number, immediate: boolean): void {
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		this.optionValue = value;
		if (this.optionSlot !== -1) {
			neighborhood.setAttribute(this.optionSlot, value);
		}
		let optionId: number = 0;
		if ((optionId = TitleScreen.optionIds[this.optionIndex]) === 730) {
			let count: number = 0;
			if (value === 0) {
				this.longSleeves = true;
				count = ((neighborhood.simAttribute(0, 5) + 1) | 0);
			}
			else {
				this.longSleeves = false;
				count = 0;
			}
			neighborhood.setAttribute(6, count);
		}
		else {
			if (optionId === 733) {
				let sleeveCount: number = 0;
				if (value === 0) {
					this.longPants = true;
					sleeveCount = ((neighborhood.simAttribute(0, 7) + 1) | 0);
				}
				else {
					this.longPants = false;
					sleeveCount = 0;
				}
				neighborhood.setAttribute(8, sleeveCount);
			}
			else {
				if (optionId === 724 || optionId === 726) {
					this.hairHasAccent = this.hairStyleHasAccent(neighborhood.simAttribute(0, 2));
				}
			}
		}
		let hitRect: Int32Array = this.hitRect;
		SpriteRenderer.hitTestMirrored(this.hitRect, 689, 0, 0);
		let listHeight: number = hitRect[2];
		let rowCount: number = ((((this.optionMax - this.optionMin) | 0) + 1) | 0);
		let rowHeight: number = SpriteRenderer.frameHeight(683, 0);
		let visibleRows: number = Math.min(Math.imul(rowCount, rowHeight), listHeight);
		let maxOffset: number = ((listHeight - visibleRows) | 0);
		let targetOffset: number = (((((((((visibleRows >> 1) + 0) | 0) - Math.imul(rowHeight, value)) | 0) - (rowHeight >> 1)) | 0) + maxOffset) | 0);
		let minOffset: number = ((((((visibleRows - 0) | 0) - Math.imul(rowHeight, rowCount)) | 0) + (maxOffset >> 1)) | 0);
		targetOffset = FixedMath.clamp(targetOffset, minOffset, 0);
		this.variantScrollTarget = targetOffset << 16;
		if (immediate) {
			this.variantScroll = targetOffset << 16;
		}
	}
	static drawVerticalGradient(gfx: Graphics, colors: Int32Array, xPos: number, yPos: number, width: number, height: number): void {
		let count: number = colors.length;
		let bandHeight: number = (((height << 16) / count) | 0);
		let offsetY: number = 0;
		for (let band: number = 0; band < count; band++) {
			SpriteRenderer.setColor(gfx, colors[band]);
			let nextOffset: number = ((Math.imul((((band + 1) | 0)), bandHeight) + 32768) | 0) >> 16;
			gfx.fillRect(xPos, ((yPos + offsetY) | 0), width, ((nextOffset - offsetY) | 0));
			offsetY = nextOffset;
		}
	}
	static drawPanelGradient(gfx: Graphics, xPos: number, yPos: number, width: number, height: number): void {
		let topHeight: number = TitleScreen.panelGradientHeight(height);
		let bottomHeight: number = ((height - topHeight) | 0);
		TitleScreen.drawVerticalGradient(gfx, TitleScreen.panelGradientTop, xPos, yPos, width, height);
		TitleScreen.drawVerticalGradient(gfx, TitleScreen.panelGradientBottom, xPos, ((yPos + bottomHeight) | 0), width, topHeight);
	}
	static panelGradientHeight(height: number): number {
		return FixedMath.mul(17902, height);
	}
	drawAppearanceScreen(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let width: number = this.canvas.getWidth();
		let height: number = ((canvas.getHeight() - 15) | 0);
		TitleScreen.drawPanelGradient(gfx, 0, 0, width, height);
		let hitRect: Int32Array = this.hitRect;
		let panelFrameHeight: number = SpriteRenderer.frameHeight(697, 0);
		let panelFrameWidth: number = 0;
		let panelWidth: number = (((panelFrameWidth = SpriteRenderer.frameWidth(697, 0)) + 4) | 0);
		let listTop: number = ((0 + panelFrameHeight) | 0);
		let listWidth: number = ((panelFrameWidth - 4) | 0);
		let listHeight: number = ((height - (panelFrameHeight << 1)) | 0);
		let panelCenterX: number = ((((width + 2) | 0) + panelWidth) | 0) >> 1;
		let panelHeight: number = TitleScreen.panelGradientHeight(height);
		let panelY: number = ((height - (panelHeight >> 1)) | 0);
		let clipX: number = gfx.getClipX();
		let clipY: number = gfx.getClipY();
		let clipWidth: number = gfx.getClipWidth();
		let clipHeight: number = gfx.getClipHeight();
		SpriteRenderer.setColor(gfx, 60);
		gfx.fillRect(2, 0, 1, height);
		gfx.fillRect(((((2 + panelWidth) | 0) - 1) | 0), 0, 1, height);
		SpriteRenderer.setColor(gfx, 77);
		gfx.fillRect(3, 0, 1, height);
		gfx.fillRect(((((2 + panelWidth) | 0) - 2) | 0), 0, 1, height);
		SpriteRenderer.setColor(gfx, 76);
		gfx.fillRect(4, panelFrameHeight, 2, listHeight);
		gfx.fillRect(((((((2 + panelWidth) | 0) - 2) | 0) - 2) | 0), panelFrameHeight, 2, listHeight);
		TitleScreen.drawVerticalGradient(gfx, TitleScreen.scrollBarGradient, 6, listTop, listWidth, listHeight);
		SpriteRenderer.drawFrame(gfx, 697, 0, 4, 0);
		SpriteRenderer.drawFrame(gfx, 696, 0, 4, height);
		let gradientTop: number = ((6 + (listWidth >> 1)) | 0);
		let optionCount: number = SpriteRenderer.frameCount(687);
		let rowHeight: number = SpriteRenderer.frameHeight(688, 0);
		if (this.optionIndex > 0) {
			SpriteRenderer.hitTest(hitRect, 697, 0, 0);
			this.upArrowAnimator.draw(gfx, ((4 + hitRect[0]) | 0), ((0 + hitRect[1]) | 0));
		}
		if (this.optionIndex < ((optionCount - 1) | 0)) {
			SpriteRenderer.hitTest(hitRect, 696, 0, 0);
			this.downArrowAnimator.draw(gfx, ((4 + hitRect[0]) | 0), ((height + hitRect[1]) | 0));
		}
		gfx.setClip(6, listTop, listWidth, listHeight);
		let rowY: number = ((listTop + (this.optionScroll >> 16)) | 0);
		for (let optionRow: number = 0; optionRow < optionCount; optionRow++) {
			let rowFrameId: number = optionRow === this.optionIndex ? 688 : 687;
			let rowFrameIndex: number = optionRow;
			if (TitleScreen.optionIds[optionRow] === 731 && !this.longSleeves) {
				rowFrameId = 686;
				rowFrameIndex = 0;
			}
			if (TitleScreen.optionIds[optionRow] === 734 && !this.longPants) {
				rowFrameId = 685;
				rowFrameIndex = 0;
			}
			if (TitleScreen.optionIds[optionRow] === 728 && !this.hairHasAccent) {
				rowFrameId = 684;
				rowFrameIndex = 0;
			}
			SpriteRenderer.drawFrame(gfx, rowFrameId, rowFrameIndex, gradientTop, rowY);
			rowY = ((rowY + rowHeight) | 0);
		}
		gfx.setClip(clipX, clipY, clipWidth, clipHeight);
		SpriteRenderer.drawFrame(gfx, 702, 0, panelCenterX, panelY);
		this.drawSimPreview(gfx, panelCenterX, panelY, false);
		let optionId: number = TitleScreen.optionIds[this.optionIndex];
		rowY = ((((((canvas.getWidth() - panelWidth) | 0) - 2) | 0) - 8) | 0);
		let swatchX: number = ((((2 + panelWidth) | 0) + 4) | 0);
		gradientTop = this.drawIconPanel(gfx, swatchX, rowY, 719, optionId);
		rowHeight = this.optionIconId;
		if (this.optionIconId !== 196) {
			swatchX = ((gradientTop + FixedMath.mul(((((height - panelHeight) | 0) - gradientTop) | 0), 12179)) | 0);
			SpriteRenderer.drawFrame(gfx, rowHeight, 0, panelCenterX, swatchX);
			SpriteRenderer.hitTestMirrored(hitRect, rowHeight, 0, 0);
			let iconX: number = ((panelCenterX + hitRect[0]) | 0);
			let iconY: number = ((swatchX + hitRect[1]) | 0);
			let iconWidth: number = hitRect[2];
			let iconHeight: number = hitRect[3];
			let iconCenterY: number = ((iconY + (iconHeight >> 1)) | 0);
			let cellWidth: number = SpriteRenderer.frameWidth(683, 0);
			gfx.setClip(iconX, iconY, iconWidth, iconHeight);
			let hasVariants: boolean = this.variantSlot !== -1;
			let cellX: number = ((((iconX + (this.variantScroll >> 16)) | 0) + (hasVariants ? 0 : cellWidth >> 1)) | 0);
			for (let variantIndex: number = this.optionMin; variantIndex <= this.optionMax; variantIndex++) {
				if (hasVariants) {
					let lot: Lot = canvas.lot;
					let variantSlot: number = this.variantSlot;
					let variantValues: Int8Array = lot.tableRowAt(variantSlot);
					let colors: Int32Array = lot.paletteAt(variantValues[variantIndex]);
					this.drawVariantCell(gfx, cellX, iconCenterY, colors, variantIndex === this.optionValue);
				}
				else {
					let cellFrameId: number = variantIndex === this.optionValue ? this.optionFrameSelected : this.optionFrameNormal;
					SpriteRenderer.drawFrame(gfx, cellFrameId, variantIndex, cellX, iconCenterY);
				}
				cellX = ((cellX + cellWidth) | 0);
			}
			gfx.setClip(clipX, clipY, clipWidth, clipHeight);
			if (this.optionValue > this.optionMin || optionId === 723) {
				SpriteRenderer.hitTest(hitRect, rowHeight, 0, 0);
				this.leftArrowAnim.draw(gfx, ((panelCenterX + hitRect[0]) | 0), ((swatchX + hitRect[1]) | 0));
			}
			if (this.optionValue < this.optionMax || optionId === 723) {
				SpriteRenderer.hitTest(hitRect, rowHeight, 0, 1);
				this.rightArrowAnim.draw(gfx, ((panelCenterX + hitRect[0]) | 0), ((swatchX + hitRect[1]) | 0));
			}
		}
		canvas.drawBottomBar(gfx);
	}
	drawVariantCell(gfx: Graphics, xPos: number, yPos: number, colors: Int32Array, selected: boolean): void {
		let frameId: number = selected ? 683 : 682;
		SpriteRenderer.drawFrame(gfx, frameId, 0, xPos, yPos);
		let hitRect: Int32Array = this.hitRect;
		SpriteRenderer.hitTestMirrored(this.hitRect, 682, 0, 0);
		let cellX: number = ((xPos + hitRect[0]) | 0);
		let cellY: number = ((yPos + hitRect[1]) | 0);
		let cellWidth: number = hitRect[2];
		let cellHeight: number = hitRect[3];
		gfx.setColor(colors[1]);
		gfx.fillRect(cellX, cellY, cellWidth, cellHeight);
		gfx.setColor(colors[3]);
		gfx.fillRect(((cellX + 1) | 0), ((cellY + 1) | 0), ((cellWidth - 2) | 0), 3);
		gfx.setColor(colors[2]);
		gfx.fillRect(((cellX + 1) | 0), ((((cellY + 1) | 0) + 3) | 0), ((cellWidth - 2) | 0), 5);
	}
	appearanceKeyPressed(keyCode: number, action: number): void {
		this.scrollList(keyCode, action);
		if (this.matchesInput(keyCode, action, 0, 4)) {
			this.onTick(10);
		}
		else {
			if (this.matchesInput(keyCode, action, 0, 2)) {
				this.onTick(12);
			}
			else {
				if (this.matchesInput(keyCode, action, 4128, 16)) {
					let traitIndex: number = this.selectedIndex;
					this.canvas.neighborhood.setSelectedResident(traitIndex);
					this.canvas.setSoftKeys(4, 2);
				}
			}
		}
	}
	drawPersonaScreen(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		this.drawPanel(gfx, 18);
		let width: number = canvas.getWidth();
		let height: number = ((canvas.getHeight() - 15) | 0);
		this.drawSimPreview(gfx, width, height, true);
		canvas.drawBottomBar(gfx);
	}
	layoutPersonaList(): void {
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		this.beginList(neighborhood.residentLabelCount(), 15, 0);
	}
	drawPersonaRows(gfx: Graphics, xPos: number, yPos: number, width: number, height: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		let centerX: number = ((xPos + (width >> 1)) | 0);
		let rowY: number = yPos;
		let startIndex: number = this.scrollOffset;
		let selectedIndex: number = this.selectedIndex;
		while (rowY < ((yPos + height) | 0) && startIndex < this.itemCount) {
			let traitId: number = neighborhood.residentLabelText(startIndex);
			let frameId: number = -1;
			let rowFrame: number = 0;
			let textFrame: number = 0;
			if (startIndex === neighborhood.selectedResident) {
				textFrame = 0;
				rowFrame = 1;
				frameId = 75;
			}
			else {
				textFrame = 1;
				rowFrame = 2;
			}
			if (startIndex === selectedIndex) {
				frameId = 82;
			}
			if (frameId !== -1) {
				this.drawClippedRow(gfx, xPos, rowY, width, 15, ((yPos + height) | 0), frameId, startIndex);
			}
			canvas.drawStringById(traitId, textFrame, centerX, ((rowY + rowFrame) | 0), 17);
			rowY = ((rowY + 15) | 0);
			startIndex++;
			BaseScreen.drawHLine(gfx, xPos, rowY, width);
		}
	}
	drawTextBox_2(gfx: Graphics, xPos: number, yPos: number, width: number): number {
		let height: number = ((8 + Math.imul(4, this.textLineHeight)) | 0);
		if (gfx !== null) {
			this.drawRaisedBox(gfx, xPos, yPos, width, height);
			let innerWidth: number = ((((width - 8) | 0) - 15) | 0);
			let textX: number = ((xPos + 4) | 0);
			let textY: number = 0;
			let valueY: number = (((textY = ((yPos + 4) | 0)) + this.textLineHeight) | 0);
			let canvas: GameCanvas = this.canvas;
			this.canvas.drawStringById(722, 1, textX, textY, 20);
			let selectedIndex: number = this.selectedIndex;
			let value: number = canvas.neighborhood.residentInfoText(selectedIndex);
			canvas.drawSlotText(0, value, 1, innerWidth, textX, valueY, 20);
		}
		return height;
	}
	updateNameEntryMarquees(deltaMs: number): void {
		this.canvas.updateMarquees(deltaMs);
	}
	traitKeyPressed(keyCode: number, action: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		this.scrollList(keyCode, action);
		if (this.matchesInput(keyCode, action, 0, 4)) {
			this.onTick(11);
		}
		else {
			if (this.matchesInput(keyCode, action, 0, 2)) {
				this.onTick(13);
			}
			else {
				if (this.matchesInput(keyCode, action, 4128, 16)) {
					let interestIndex: number = this.selectedIndex;
					if (neighborhood.simKnownIndex(0, interestIndex) === -1) {
						neighborhood.addKnown(interestIndex);
					}
					else {
						neighborhood.removeKnown(interestIndex);
					}
					let status: number = canvas.neighborhood.simKnownFreeSlot();
					let available: boolean = 5 === status;
					canvas.setSoftKeys(4, available ? 2 : 0);
				}
			}
		}
	}
	drawTraitScreen(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		this.drawPanel(gfx, 19);
		let width: number = canvas.getWidth();
		let height: number = ((canvas.getHeight() - 15) | 0);
		this.drawSimPreview(gfx, width, height, true);
		canvas.drawBottomBar(gfx);
	}
	layoutTraitList(): void {
		this.beginList(Neighborhood.initialScreenId(), 15, 0);
	}
	drawTraitRows(gfx: Graphics, xPos: number, yPos: number, width: number, height: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		let centerX: number = ((xPos + (width >> 1)) | 0);
		let rowY: number = yPos;
		let startIndex: number = this.scrollOffset;
		let selectedIndex: number = this.selectedIndex;
		while (rowY < ((yPos + height) | 0) && startIndex < this.itemCount) {
			let interestId: number = neighborhood.itemNameText(startIndex);
			let frameId: number = -1;
			let rowFrame: number = 0;
			let textFrame: number = 0;
			if (neighborhood.simKnownIndex(0, startIndex) !== -1) {
				textFrame = 0;
				rowFrame = 1;
				frameId = 75;
			}
			else {
				textFrame = 1;
				rowFrame = 2;
			}
			if (startIndex === selectedIndex) {
				frameId = 82;
			}
			if (frameId !== -1) {
				this.drawClippedRow(gfx, xPos, rowY, width, 15, ((yPos + height) | 0), frameId, startIndex);
			}
			canvas.drawStringById(interestId, textFrame, centerX, ((rowY + rowFrame) | 0), 17);
			rowY = ((rowY + 15) | 0);
			startIndex++;
			BaseScreen.drawHLine(gfx, xPos, rowY, width);
		}
	}
	static keyToCharIndex(keyCode: number): number {
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
	resetNameEntry(): void {
		this.multiTapActive = false;
		this.nameCursor = this.canvas.profileName().length;
		this.keyRepeatTimer = 0;
		this.repeatKey = -1;
		this.keyCharIndex = 0;
	}
	nameKeyPressed(keyIndex: number): void {
		let canvas: GameCanvas = this.canvas;
		this.canvas.clearAction(1023);
		let chars: Uint16Array = TitleScreen.KEY_CHARS[keyIndex];
		if (this.multiTapActive && keyIndex === this.repeatKey) {
			this.keyRepeatTimer = 0;
			this.keyCharIndex = (((((this.keyCharIndex + 1) | 0)) % chars.length) | 0);
			canvas.setProfileNameChar(this.nameCursor, chars[this.keyCharIndex]);
		}
		else {
			this.endMultiTap();
			if (canvas.profileNameLength() !== 8) {
				canvas.insertProfileNameChar(this.nameCursor, chars[0]);
				this.multiTapActive = true;
				this.keyRepeatTimer = 0;
				this.repeatKey = keyIndex;
				this.keyCharIndex = 0;
			}
		}
	}
	endMultiTap(): void {
		if (this.multiTapActive) {
			this.multiTapActive = false;
			if (this.nameCursor < 8) {
				this.nameCursor++;
			}
			this.repeatKey = -1;
		}
	}
	updateNameEntry(deltaMs: number): void {
		let canvas: GameCanvas = this.canvas;
		this.keyRepeatTimer = ((this.keyRepeatTimer + deltaMs) | 0);
		this.hintScrollX = ((this.hintScrollX - (Math.imul(deltaMs, 1638))) | 0);
		this.hintWidth = canvas.stringWidthById(957, 0);
		if (this.hintScrollX < (((this.hintWidth << 16) * -1) | 0)) {
			this.hintScrollX = canvas.getWidth() << 16;
		}
		if (this.multiTapActive && this.keyRepeatTimer >= 1250) {
			this.endMultiTap();
		}
	}
	nameEntryKeyPressed(keyCode: number, action: number): void {
		let canvas: GameCanvas = this.canvas;
		let nameLength: number = this.canvas.profileNameLength();
		if (this.matchesInput(keyCode, action, 1023, 0)) {
			let charIndex: number = 0;
			if ((charIndex = TitleScreen.keyToCharIndex(keyCode)) !== -1) {
				this.nameKeyPressed(charIndex);
				canvas.setSoftKeys(4, canvas.profileNameLength() !== 0 ? 2 : 0);
			}
		}
		else {
			if (this.matchesInput(keyCode, action, 4096, 2)) {
				if (canvas.profileNameLength() !== 0) {
					this.onTick(18);
					return;
				}
			}
			else {
				if (this.matchesInput(keyCode, action, 524288, 0)) {
					this.endMultiTap();
					if (nameLength === 0) {
						this.onTick(12);
						return;
					}
					if (this.nameCursor > 0) {
						this.nameCursor--;
					}
					canvas.deleteProfileNameChar(this.nameCursor);
					canvas.setSoftKeys(4, canvas.profileNameLength() !== 0 ? 2 : 0);
					return;
				}
				if (this.matchesInput(keyCode, action, 0, 4)) {
					this.onTick(12);
					return;
				}
				if (this.matchesInput(keyCode, action, 32768, 0)) {
					this.endMultiTap();
					if (this.nameCursor !== 0) {
						this.nameCursor--;
						return;
					}
				}
				else {
					if (this.matchesInput(keyCode, action, 65536, 0)) {
						this.endMultiTap();
						if (this.nameCursor !== nameLength) {
							this.nameCursor++;
						}
					}
				}
			}
		}
	}
	drawNameEntryScreen(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		TitleScreen.drawPanelGradient(gfx, 0, 0, canvas.getWidth(), ((canvas.getHeight() - 15) | 0));
		let width: number = canvas.getWidth();
		let height: number = ((canvas.getHeight() - 15) | 0);
		let boxWidth: number = ((Math.imul(width, 7) / 10) | 0);
		let boxX: number = ((width - boxWidth) | 0) >> 1;
		let boxHeight: number = ((((((Math.imul(this.rowHeight, 6) + 6) | 0) + 22) | 0) + 5) | 0);
		let boxY: number = ((((height - boxHeight) | 0) + 5) | 0);
		let boxTop: number = this.drawIconPanel(gfx, boxX, boxWidth, 746, -1);
		let name: SdkString = canvas.profileName();
		let nameWidth: number = canvas.stringWidth(name, 1);
		let nameX: number = ((boxX + (((boxWidth - nameWidth) | 0) >> 1)) | 0);
		let nameY: number = ((boxTop - 16) | 0);
		let lineHeight: number = ((canvas.lineHeight(1) - 2) | 0);
		canvas.drawString(name, 1, nameX, nameY, 20);
		let cursorPos: number = -1;
		if (this.multiTapActive || ((((this.keyRepeatTimer / 750) | 0) % 2) | 0) === 1) {
			cursorPos = this.nameCursor;
		}
		if (cursorPos !== -1) {
			let cursorWidth: number = 5;
			let cursorX: number = nameX;
			if (name !== null) {
				cursorX = ((nameX + SdkText.substringWidth(name, 0, cursorPos)) | 0);
				if (cursorPos !== canvas.profileNameLength()) {
					cursorWidth = SdkText.substringWidth(name, cursorPos, 1);
				}
			}
			let cursorY: number = ((nameY + lineHeight) | 0);
			SpriteRenderer.setColor(gfx, 0);
			gfx.fillRect(cursorX, cursorY, cursorWidth, 1);
		}
		let centerX: number = width >> 1;
		nameWidth = ((boxTop + (((((height - boxTop) | 0) - boxHeight) | 0) >> 1)) | 0);
		SpriteRenderer.drawFrame(gfx, 709, 0, centerX, nameWidth);
		let hitRect: Int32Array = this.hitRect;
		SpriteRenderer.hitTest(this.hitRect, 709, 0, 0);
		nameY = ((centerX + hitRect[0]) | 0);
		lineHeight = ((nameWidth + hitRect[1]) | 0);
		this.drawCenteredLabel(gfx, nameY, lineHeight);
		let traitId: number = neighborhood.residentLabelText(neighborhood.selectedResident);
		this.drawDialogFrame(gfx, boxX, boxY, boxWidth, boxHeight, traitId, 1);
		nameWidth = ((boxX + 3) | 0);
		nameX = ((boxY + 22) | 0);
		nameY = ((boxWidth - 6) | 0);
		lineHeight = ((Math.imul(this.rowHeight, 5) + 6) | 0);
		this.drawRaisedBox(gfx, nameWidth, nameX, nameY, lineHeight);
		cursorPos = ((nameWidth + (nameY >> 1)) | 0);
		let rowY: number = ((((nameX + 3) | 0) + 1) | 0);
		for (let row: number = 0; row < 5; row++) {
			let traitSlot: number = neighborhood.simKnownId(0, row);
			let traitName: number = neighborhood.itemNameText(traitSlot);
			canvas.drawStringById(traitName, 1, cursorPos, rowY, 17);
			rowY = ((rowY + this.rowHeight) | 0);
		}
		let hintY: number = ((canvas.getHeight() - 30) | 0);
		if (GameCanvas.landscape) {
			canvas.drawStringById(957, 0, this.hintScrollX >> 16, hintY, 20);
		}
		canvas.drawBottomBar(gfx);
	}
	profileSlotKeyPressed(keyCode: number, action: number): void {
		let canvas: GameCanvas = this.canvas;
		let selected: number = 0;
		let previous: number = selected = this.newSimSelected ? 2 : canvas.profileSlot;
		let slotCount: number = ((2 + (TitleScreen.state === 14 ? 1 : 0)) | 0);
		if (this.matchesInput(keyCode, action, 0, 68)) {
			this.onTick(TitleScreen.state === 14 ? 7 : 14);
		}
		else {
			if (this.matchesInput(keyCode, action, 4128, 48)) {
				if (this.newSimSelected) {
					this.onTick(15);
				}
				else {
					if (TitleScreen.state === 15) {
						if (canvas.isProfileUsed()) {
							this.onTick(16);
						}
					}
					else {
						if (canvas.isProfileUsed()) {
							canvas.reloadGameData();
							this.requestScreen(2, -1);
						}
						else {
							this.resetForNewSim();
						}
					}
				}
			}
			else {
				if (this.matchesInput(keyCode, action, 8196, 0)) {
					selected = (((((((selected + slotCount) | 0) - 1) | 0)) % slotCount) | 0);
				}
				else {
					if (this.matchesInput(keyCode, action, 16640, 0)) {
						selected = (((((selected + 1) | 0)) % slotCount) | 0);
					}
				}
			}
		}
		if (selected !== previous) {
			this.newSimSelected = selected === 2;
			if (!this.newSimSelected) {
				canvas.selectProfile(selected);
				if (canvas.isProfileUsed()) {
					this.applySimAppearance(false);
				}
			}
			let used: boolean = canvas.isProfileUsed();
			if (TitleScreen.state === 15) {
				canvas.setSoftKeys(64, used ? 32 : 0);
			}
		}
	}
	drawProfileScreen(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let width: number = this.canvas.getWidth();
		let height: number = 0;
		let listHeight: number = (((height = ((canvas.getHeight() - 15) | 0)) - 6) | 0);
		let rowY: number = ((((3 + listHeight) | 0) - (TitleScreen.panelGradientHeight(listHeight) >> 1)) | 0);
		let panelWidth: number = ((((((((width - 3) | 0) - 64) | 0) - 3) | 0) - 3) | 0);
		let colorIndex: number = TitleScreen.state === 14 ? 77 : 43;
		SpriteRenderer.setColor(gfx, colorIndex);
		gfx.fillRect(0, 0, width, height);
		colorIndex = gfx.getClipX();
		let clipX: number = gfx.getClipY();
		let clipY: number = gfx.getClipWidth();
		let clipWidth: number = gfx.getClipHeight();
		gfx.setClip(colorIndex, 3, clipY, listHeight);
		TitleScreen.drawPanelGradient(gfx, 3, 3, 64, listHeight);
		gfx.setClip(colorIndex, clipX, clipY, clipWidth);
		SpriteRenderer.setColor(gfx, 0);
		gfx.drawRect(3, 3, 63, ((listHeight - 1) | 0));
		if (!this.newSimSelected && canvas.isProfileUsed()) {
			this.drawSimPreview(gfx, 35, rowY, false);
		}
		clipX = TitleScreen.state === 14 ? 64 : 65;
		clipX = ((this.drawIconRow(gfx, 70, panelWidth, clipX) + 3) | 0);
		clipWidth = ((((height - 3) | 0) - 19) | 0);
		let rowHeight: number = (((this.rowHeight << 2) + 8) | 0);
		let statsY: number = ((((clipWidth - 3) | 0) - rowHeight) | 0);
		let rowGap: number = (((((((((((((((((height - clipX) | 0) - 3) | 0) - 19) | 0) - 3) | 0) - rowHeight) | 0) - 3) | 0) - 3) | 0)) / 2) | 0);
		let rowTop: number = clipX;
		for (let slot: number = 0; slot < 2; slot++) {
			let selected: boolean = !this.newSimSelected && slot === canvas.profileSlot;
			let profileName: SdkString = canvas.isProfileUsedAt(slot) ? canvas.profileNameAt(slot) : canvas.getString(52);
			this.drawButtonLabel(gfx, 70, rowTop, panelWidth, rowGap, selected, profileName);
			rowTop = ((rowTop + (((rowGap + 3) | 0))) | 0);
		}
		this.drawPlainBox(gfx, 70, statsY, panelWidth, rowHeight);
		if (!this.newSimSelected && canvas.isProfileUsed()) {
			let neighborhood: Neighborhood = canvas.neighborhood;
			rowGap = ((((70 + panelWidth) | 0) - 4) | 0);
			rowTop = ((((statsY + 4) | 0) + 1) | 0);
			let simNameId: number = neighborhood.residentLabelText(neighborhood.selectedResident);
			canvas.drawStringById(simNameId, 1, 74, rowTop, 20);
			rowTop = ((rowTop + this.rowHeight) | 0);
			let money: number = neighborhood.moodTextId();
			canvas.drawStringById(521, 1, 74, rowTop, 20);
			canvas.drawStringById(money, 1, rowGap, rowTop, 24);
			rowTop = ((rowTop + this.rowHeight) | 0);
			let careerLevel: number = neighborhood.simLot(0);
			let careerId: number = neighborhood.lotNameText(careerLevel);
			canvas.drawStringById(646, 1, 74, rowTop, 20);
			canvas.drawStringById(careerId, 1, rowGap, rowTop, 24);
			rowTop = ((rowTop + this.rowHeight) | 0);
			let balance: SdkString = canvas.beginString();
			canvas.formatNumber(neighborhood.money);
			canvas.drawString(balance, 1, 74, rowTop, 20);
		}
		if (TitleScreen.state === 14) {
			this.drawButton(gfx, 70, clipWidth, panelWidth, 19, this.newSimSelected, 54);
		}
		canvas.drawBottomBar(gfx);
	}
	constructor(canvas: GameCanvas) {
		super(canvas);
		canvas.fadeSpeed = 197379;
		this.upArrowAnimator = new Animator();
		this.downArrowAnimator = new Animator();
		this.simAnimator = new Animator();
		this.simAnimator.setRunning(false);
	}
}
