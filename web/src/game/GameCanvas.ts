// Generated from GameCanvas.java by tools/web/JavaToTs.java; keep this file in sync.
import { Canvas, Random, Timer, Player, Graphics, Image, IOException, InputStream, loadResource, ByteArrayInputStream, arraycopy, gc, countBits, InterruptedException, RecordStore, DataInputStream, RecordStoreNotFoundException, ByteArrayOutputStream, DataOutputStream, MIDlet } from '../runtime';
import { MonkeyApp } from './MonkeyApp';
import { BaseScreen } from './BaseScreen';
import { PromoImages } from './PromoImages';
import { ResourceLoader } from './ResourceLoader';
import { SdkSound } from './SdkSound';
import { SdkString } from './SdkString';
import { ScreenFade } from './ScreenFade';
import { Animator } from './Animator';
import { Neighborhood } from './Neighborhood';
import { Lot } from './Lot';
import { SoundIndex } from './SoundIndex';
import { GameScreen } from './GameScreen';
import { FrameTimer } from './FrameTimer';
import { TitleScreen } from './TitleScreen';
import { SdkText } from './SdkText';
import { FixedMath } from './FixedMath';
import { SpriteRenderer } from './SpriteRenderer';
import { Sim } from './Sim';

export class GameCanvas extends Canvas {
	app: MonkeyApp = null!;
	running: boolean = false;
	screen: BaseScreen = null!;
	suspended: boolean = false;
	static landscape: boolean = false;
	static lastLandscape: number = -1;
	static currentLandscape: number = -1;
	promoImages: PromoImages = null;
	promoActive: boolean = false;
	resources: ResourceLoader = null;
	soundLoopId: number = 0;
	soundValue: SdkSound = null!;
	random: Random = null!;
	gcPending: boolean = false;
	static instanceValue: GameCanvas = null;
	timer: Timer = null;
	updating: boolean = false;
	static resuming: boolean = false;
	frozenFrames: number = 0;
	actionState: number = 0;
	softKeyAction: number = 0;
	keyQueue: Int32Array = null!;
	keyQueueSize: number = 0;
	rightSoftKeyAction: number = 0;
	leftSoftKeyAction: number = 0;
	fonts: any[] = null!;
	headerCount: number = 0;
	static ioBuffer: Int8Array = null!;
	tempStringCursor: number = 0;
	tempStrings: SdkString[] = null!;
	formattedStrings: SdkString[] = new Array(10).fill(null);
	static formatArgs: SdkString[] = new Array(3).fill(null);
	buildBuffer: SdkString = null!;
	stringPool: SdkString[] = null!;
	buildStringCursor: number = 0;
	wrappedLineOffsets: Int16Array = null!;
	wrappedString: SdkString = null!;
	wrapOffsets: Int16Array[] = null!;
	slotTexts: SdkString[] = null!;
	slotStringIds: Int16Array = null!;
	marqueeStringIds: Int32Array = new Int32Array(2);
	marqueeTexts: SdkString[] = new Array(2).fill(null);
	textWidths: Int32Array = new Int32Array(2);
	marqueeTimers: Int32Array = new Int32Array(2);
	scrollOffsets: Int32Array = new Int32Array(2);
	fade: ScreenFade = new ScreenFade();
	fading: boolean = false;
	fadeFinished: boolean = false;
	fadeColor: number = 0;
	fadeStep: number = 0;
	fadeSpeed: number = 0;
	pendingScreenAction: number = 0;
	pendingLanguage: number = 0;
	cheatToggled: boolean = false;
	static cheatSequences: Int32Array[] = [Int32Array.from([0, 2048, 2048, 16, 64, 128, 8])];
	leftSoftKeyTimer: number = 0;
	rightSoftKeyTimer: number = 0;
	leftSoftKeyAnimator: Animator = null!;
	rightSoftKeyAnimator: Animator = null!;
	rightSoftKeySelected: boolean = false;
	tileMasks: Int32Array = null!;
	tileLoadCursor: number = 0;
	vibrationEnabled: boolean = false;
	tutorialsEnabled: boolean = false;
	autonomousEnabled: boolean = false;
	savePromptsEnabled: boolean = false;
	language: number = 0;
	profileSlot: number = 0;
	profileNames: SdkString[] = new Array(2).fill(null);
	profileUsed: boolean[] = new Array(2).fill(false);
	progressFlags: number = 0;
	gameStarted: boolean = false;
	flagsLow: bigint = 0n;
	flagsHigh: bigint = 0n;
	visitedFlagsLow: bigint = 0n;
	visitedFlagsHigh: bigint = 0n;
	newFlagsLow: bigint = 0n;
	newFlagsHigh: bigint = 0n;
	newVisitedFlagsLow: bigint = 0n;
	newVisitedFlagsHigh: bigint = 0n;
	loadingTipId: number = 0;
	loadingTipX: number = 0;
	loadingTipWidth: number = 0;
	menuMode: number = 0;
	optionsMenu: Int16Array = null!;
	compactOptionsMenu: Int16Array = null!;
	helpMenu: Int16Array = null!;
	currentMenu: Int16Array = null!;
	returnMenuMode: number = 0;
	menuRowSpacing: number = 0;
	neighborhood: Neighborhood = null!;
	lot: Lot = null!;
	houseIndex: number = 0;
	houseUpgrade: number = 0;
	tileGroups: Int16Array[] = null!;
	simPosX: number = 0;
	simPosY: number = 0;
	walkTargetX: number = 0;
	walkTargetY: number = 0;
	walkTargetFrame: number = 0;
	playerUpdate(player: Player, event: string, data: any): void {
	}
	setSoundLoop(soundId: number): void {
		if (this.soundLoopId !== soundId && this.soundLoopId !== -1) {
			this.soundValue.stop();
		}
		this.soundLoopId = soundId;
		if (soundId === -1) {
			this.soundValue.setLoopCount(1);
		}
		else {
			this.soundValue.setLoopCount(-1);
			this.soundValue.pendingSound = soundId;
		}
	}
	preloadSounds(): void {
		let soundRef: SdkSound = this.soundValue;
		this.soundValue.play(11);
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
	halfWidth(): number {
		return this.getWidth() >> 1;
	}
	halfHeight(): number {
		return this.getHeight() >> 1;
	}
	static create(app: MonkeyApp): void {
		GameCanvas.instanceValue = new GameCanvas(app);
	}
	static instance(): GameCanvas {
		return GameCanvas.instanceValue;
	}
	startGame(): void {
		this.buildMenus();
		let languageIndex: number = 5;
		if (this.language === -1) {
			languageIndex = 4;
		}
		this.setPendingScreen(1, languageIndex);
		this.promoImages.setScreenSize(this.getWidth(), this.getHeight());
		this.suspended = false;
	}
	shutdown(): void {
		this.running = false;
		if (this.screen !== null) {
			this.screen.pause();
		}
		this.saveSettings();
		this.setGcPending(true);
		for (let index: number = 0; index < SoundIndex.SOUND_IDS.length; index++) {
			this.soundValue.stopAll();
		}
	}
	showNotify(): void {
		if (!GameScreen.updateBlocked) {
			if (this.suspended) {
				this.resumeGame();
				this.startTimer();
			}
		}
	}
	hideNotify(): void {
		try {
			void 0;
		}
		catch (ignored) {
		}
		if (!GameScreen.updateBlocked) {
			this.suspendGame();
		}
	}
	startTimer(): void {
		this.running = true;
		if (this.timer !== null) {
			this.timer.cancel();
			this.timer = null;
			GameCanvas.gcYield();
		}
		this.timer = new Timer();
		this.timer.schedule(new FrameTimer(this), 0, 15);
	}
	tick(): void {
		try {
			this.repaint();
			if (this.promoImages !== null) {
				this.promoImages.setScreenSize(this.getWidth(), this.getHeight());
			}
			if (this.screen !== null) {
				if (this.screen instanceof GameScreen) {
					let game: GameScreen = (this.screen as unknown as GameScreen);
					if (GameScreen.screenState !== 1) {
						game.layoutViewport();
						return;
					}
				}
				else {
					if (this.screen instanceof TitleScreen) {
						let unused: any = null;
						if (TitleScreen.state === 5) {
							this.screen.onTick(5);
						}
						if (TitleScreen.state === 11) {
							this.slotStringIds[0] = 0;
						}
						if (TitleScreen.state === 10) {
							this.screen.onTick(10);
						}
					}
				}
			}
		}
		catch (ignored) {
		}
	}
	advanceFrame(deltaMs: number): void {
		let actions: number = this.consumeKeyState();
		this.updateTimers(deltaMs);
		this.clearAction(actions);
		if (this.gcPending) {
			GameCanvas.gcYield();
			this.gcPending = false;
		}
	}
	consumeKeyState(): number {
		let queue: Int32Array = this.keyQueue;
		for (let clearIndex: number = ((this.keyQueueSize - 1) | 0); clearIndex >= 0; clearIndex--) {
			if (queue[clearIndex] < 0) {
				this.actionState = this.actionState & ~(((queue[clearIndex] * -1) | 0));
				if (((queue[clearIndex] * -1) | 0) === 131072 || ((queue[clearIndex] * -1) | 0) === 262144 || ((queue[clearIndex] * -1) | 0) === 524288) {
					this.softKeyAction = 0;
				}
			}
		}
		for (let pressIndex: number = ((this.keyQueueSize - 1) | 0); pressIndex >= 0; pressIndex--) {
			if (queue[pressIndex] > 0) {
				this.actionState = this.actionState | queue[pressIndex];
				if (queue[pressIndex] === 524288) {
					this.softKeyAction = 4;
				}
				else {
					if (queue[pressIndex] === 262144) {
						this.softKeyAction = this.rightSoftKeyAction;
					}
					else {
						if (queue[pressIndex] === 131072) {
							this.softKeyAction = this.leftSoftKeyAction;
						}
					}
				}
			}
		}
		let state: number = 0;
		for (let actionBit: number = 1; actionBit <= 18; actionBit++) {
			for (let index: number = 0; index < this.keyQueueSize; index++) {
				if (queue[index] < 0) {
					state = (state | ((queue[index] * -1) | 0));
				}
				else {
					if (state === 1 << actionBit && queue[index] > 0) {
						state = (state & ~queue[index]);
					}
				}
				queue[index] = 0;
			}
		}
		this.keyQueueSize = 0;
		return state;
	}
	muteSound(): void {
		SdkSound.muteAll();
		this.running = false;
	}
	suspendGame(): void {
		this.releaseSoftKeys();
		this.clearActions();
		if (this.screen !== null) {
			this.screen.stop();
		}
		SdkSound.muteAll();
		this.soundValue.playQueued();
		this.soundValue.update();
		this.suspended = true;
	}
	resumeGame(): void {
		GameCanvas.resuming = true;
		this.releaseSoftKeys();
		this.clearActions();
		this.keyQueueSize = 0;
		this.suspended = false;
	}
	updateTimers(deltaMs: number): void {
		if (GameCanvas.resuming) {
			this.frozenFrames++;
		}
		if (this.frozenFrames > 500) {
			if (this.soundLoopId !== -1) {
				this.setSoundLoop(this.soundLoopId);
			}
			this.frozenFrames = 0;
			GameCanvas.resuming = false;
		}
		if (deltaMs > 150) {
			deltaMs = 150;
		}
		if (this.pendingScreenAction !== -1) {
			this.changeScreen(this.pendingScreenAction, this.pendingLanguage);
			this.pendingScreenAction = -1;
			this.pendingLanguage = -1;
		}
		if (this.screen !== null) {
			if (this.fading) {
				this.fadeColor = ((this.fadeColor + Math.imul(Math.imul(this.fadeStep, this.fadeSpeed), (deltaMs >> 3))) | 0);
				if (this.fadeStep > 0 && this.fadeColor >= 16777215) {
					this.fadeColor = 16777215;
				}
				else {
					if (this.fadeStep < 0 && this.fadeColor <= 0) {
						this.fadeColor = 0;
					}
				}
			}
			if (this.screen !== null) {
				try {
					let newAction: number = 0;
					if (this.softKeyAction === 4 && this.rightSoftKeyAction !== 4 && this.leftSoftKeyAction !== 4) {
						newAction = 4;
						this.softKeyAction = 0;
					}
					this.screen.update(this.actionState, newAction);
					this.startSoftKeyAnimation(this.softKeyAction);
				}
				catch (updateError) {
				}
				try {
					this.updateSoftKeyAnimation(deltaMs);
					this.screen.keyPressed(deltaMs);
					return;
				}
				catch (keyError) {
				}
			}
		}
	}
	paint(gfx: Graphics): void {
		try {
			let graphicsCopy: Graphics = gfx;
			SdkText.setGraphics(gfx);
			try {
				if (this.screen !== null && this.running) {
					this.screen.render(graphicsCopy);
				}
				if (this.isFading()) {
					this.drawFade(graphicsCopy);
					if (this.fadeStep > 0 && this.fadeColor >= 16777215 || this.fadeStep < 0 && this.fadeColor <= 0) {
						this.fadeFinished = true;
					}
				}
			}
			catch (ignored) {
			}
		}
		finally {
			this.updating = false;
		}
	}
	keyToAction(keyCode: number): number {
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
	keyPressed(keyCode: number): void {
		if (this.promoActive) {
			this.promoImages.processKey(keyCode);
		}
		else {
			let action: number = 0;
			if ((action = this.keyToAction(keyCode)) !== 0 && this.keyQueueSize < this.keyQueue.length) {
				this.keyQueue[this.keyQueueSize++] = action;
				this.trackCheatSequence(action);
			}
		}
	}
	keyRepeated(keyCode: number): void {
		this.keyPressed(keyCode);
	}
	keyReleased(keyCode: number): void {
		let action: number = 0;
		if ((action = this.keyToAction(keyCode)) !== 0 && this.keyQueueSize < this.keyQueue.length) {
			this.keyQueue[this.keyQueueSize++] = ((action * -1) | 0);
		}
	}
	clearAction(mask: number): void {
		this.actionState = (this.actionState & ~mask);
	}
	clearActions(): void {
		this.actionState = 0;
	}
	releaseSoftKeys(): void {
		this.softKeyAction = 0;
		this.clearAction(524288);
	}
	setSoftKeys(leftAction: number, rightAction: number): void {
		if (leftAction !== this.rightSoftKeyAction) {
			this.rightSoftKeyAction = leftAction;
			this.rightSoftKeyTimer = 0;
		}
		if (rightAction !== this.leftSoftKeyAction) {
			this.leftSoftKeyAction = rightAction;
			this.leftSoftKeyTimer = 0;
		}
	}
	static actionToLabelId(action: number): number {
		if (action === 0) {
			return -1;
		}
		else {
			if (action === 1) {
				return 45;
			}
			else {
				if (action === 2) {
					return 36;
				}
				else {
					if (action === 4) {
						return 37;
					}
					else {
						if (action === 8) {
							return 38;
						}
						else {
							if (action === 16) {
								return 39;
							}
							else {
								if (action === 32) {
									return 40;
								}
								else {
									if (action === 64) {
										return 41;
									}
									else {
										if (action === 128) {
											return 49;
										}
										else {
											if (action === 256) {
												return 43;
											}
											else {
												if (action === 512) {
													return 44;
												}
												else {
													if (action === 1024) {
														return 35;
													}
													else {
														if (action === 2048) {
															return 46;
														}
														else {
															if (action === 4096) {
																return 47;
															}
															else {
																if (action === 8192) {
																	return 48;
																}
																else {
																	if (action === 16384) {
																		return 1;
																	}
																	else {
																		return action === 32768 ? 2 : -1;
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	loadFonts(): void {
		GameCanvas.ioBuffer = new Int8Array(2600);
		let header: Int8Array = this.readResource("/hdr");
		this.headerCount = SdkText.loadTextHeader(header);
		let languageArg: number = 0;
		let fontSetIndex: number = 0;
		let selectedLanguage: number = fontSetIndex = SdkText.chooseLanguage(languageArg = ((0 + this.language) | 0));
		if (fontSetIndex < 0) {
			selectedLanguage = -1;
			fontSetIndex = 0;
		}
		let fontArray: any[] = new Array(5).fill(null);
		let fontImages: Image[] = new Array(5).fill(null);
		this.fonts = fontArray;
		let fontPath: string = "/" + SdkText.getFontDifferentiator(fontSetIndex);
		try {
			fontImages[0] = Image.createImage(fontPath + "font_sims_white_outline.png");
			fontImages[1] = Image.createImage(fontPath + "font_sims_black.png");
			fontImages[2] = Image.createImage(fontPath + "font_sims_white_smallcaps.png");
			fontImages[3] = Image.createImage(fontPath + "font_the.png");
			fontImages[4] = Image.createImage(fontPath + "font_thesmall.png");
		}
		catch (ignored) {
			if (ignored instanceof IOException) {
			}
			else {
				throw ignored;
			}
		}
		let data: Int8Array = this.readResource(fontPath + "font_sims_white_outline.rff");
		fontArray[0] = SdkText.createFont_2(fontImages[0], data);
		data = this.readResource(fontPath + "font_sims_black.rff");
		fontArray[1] = SdkText.createFont_2(fontImages[1], data);
		data = this.readResource(fontPath + "font_sims_white_smallcaps.rff");
		fontArray[2] = SdkText.createFont_2(fontImages[2], data);
		data = this.readResource(fontPath + "font_the.rff");
		fontArray[3] = SdkText.createFont_2(fontImages[3], data);
		data = this.readResource(fontPath + "font_thesmall.rff");
		fontArray[4] = SdkText.createFont_2(fontImages[4], data);
		GameCanvas.ioBuffer = null;
		GameCanvas.gcYield();
		this.setLanguage(fontSetIndex);
		this.language = selectedLanguage;
		let orderedFonts: any[] = null!;
		(orderedFonts = new Array(5).fill(null))[0] = fontArray[1];
		orderedFonts[1] = fontArray[0];
		orderedFonts[2] = fontArray[0];
		orderedFonts[3] = fontArray[1];
		orderedFonts[4] = fontArray[0];
		this.promoImages.setFonts(orderedFonts);
		try {
			let stream: InputStream = this.resources.openStream(52);
			let productData: Int8Array = new Int8Array(ResourceLoader.getSize(52));
			stream.read(productData);
			stream.close();
			this.promoImages.setProductData(productData);
			GameCanvas.gcYield();
		}
		catch (ignored2) {
			if (ignored2 instanceof IOException) {
			}
			else {
				throw ignored2;
			}
		}
	}
	setLanguage(languageIndex: number): void {
		this.language = languageIndex;
		SdkText.setCurrentLanguage(languageIndex);
		SdkText.loadStringsChunk(2);
		if (this.titleScreen() !== null) {
			SdkText.loadStringsChunk(1);
		}
		GameCanvas.fillShorts(this.slotStringIds, -1);
		GameCanvas.fillObjects(this.slotTexts, (null as unknown as any));
		this.wrappedString = null;
		GameCanvas.fillInts(this.marqueeStringIds, -1);
		GameCanvas.fillObjects(this.marqueeTexts, (null as unknown as any));
	}
	readResource(path: string): Int8Array {
		let data: Int8Array = null;
		let stream: InputStream = null;
		try {
			if ((stream = new ByteArrayInputStream(loadResource(path))) !== null) {
				let read: number = stream.read(GameCanvas.ioBuffer);
				let total: number = 0;
				for (let remaining: number = 2600; read > 0; read = stream.read(GameCanvas.ioBuffer, total, remaining)) {
					total = ((total + read) | 0);
					remaining = ((remaining - read) | 0);
				}
				data = new Int8Array(total);
				arraycopy(GameCanvas.ioBuffer, 0, data, 0, total);
			}
		}
		catch (ignored) {
			if (ignored instanceof IOException) {
			}
			else {
				throw ignored;
			}
		}
		return data;
	}
	drawStringById(stringId: number, fontIndex: number, xPos: number, yPos: number, anchor: number): void {
		if (stringId !== -1) {
			let text: SdkString = this.getString(stringId);
			SdkText.setFont(this.fonts[fontIndex]);
			this.drawString(text, fontIndex, xPos, yPos, anchor);
		}
	}
	drawString(text: SdkString, fontIndex: number, xPos: number, yPos: number, anchor: number): void {
		if (text !== null) {
			SdkText.setFont(this.fonts[fontIndex]);
			SdkText.setSpaceSpacing(5);
			if ((anchor & 2) !== 0) {
				yPos = ((yPos - (((SdkText.getLineHeight() - SdkText.getLeadingSpacing()) | 0) >> 1)) | 0);
				anchor = (anchor & -3);
			}
			SdkText.drawString(text, xPos, yPos, anchor);
		}
	}
	spaceWidth(fontIndex: number): number {
		SdkText.setFont(this.fonts[fontIndex]);
		return SdkText.getSpaceSpacing();
	}
	lineHeight(fontIndex: number): number {
		SdkText.setFont(this.fonts[fontIndex]);
		return SdkText.getLineHeight();
	}
	getString(stringId: number): SdkString {
		if (stringId < -2) {
			return this.formattedStrings[((stringId - -12) | 0)];
		}
		else {
			let buffer: SdkString = this.tempStrings[this.tempStringCursor];
			this.tempStringCursor = (((((this.tempStringCursor + 1) | 0)) % 10) | 0);
			return stringId <= 3 ? SdkText.getHeaderString(SdkText.getCurrentLanguage(), stringId, buffer) : SdkText.getString(stringId, buffer);
		}
	}
	stringWidthById(stringId: number, fontIndex: number): number {
		SdkText.setFont(this.fonts[fontIndex]);
		return SdkText.stringWidth(this.getString(stringId));
	}
	stringWidth(text: SdkString, fontIndex: number): number {
		SdkText.setFont(this.fonts[fontIndex]);
		return SdkText.stringWidth(text);
	}
	static createString(text: string): SdkString {
		return new SdkString(text);
	}
	formatString1(slot: number, formatId: number, argId: number): void {
		let format: SdkString = this.getString(formatId);
		this.formattedStrings[((slot - -12) | 0)] = format.format(this.getString(argId));
	}
	formatString2(slot: number, formatId: number, argId1: number, argId2: number): void {
		let format: SdkString = this.getString(formatId);
		let args: SdkString[] = null!;
		(args = GameCanvas.formatArgs)[0] = this.getString(argId1);
		args[1] = this.getString(argId2);
		this.formattedStrings[((slot - -12) | 0)] = format.format_2(args);
	}
	formatStringWith(slot: number, formatId: number, arg: SdkString): void {
		let format: SdkString = this.getString(formatId);
		this.formattedStrings[((slot - -12) | 0)] = format.format(arg);
	}
	formatStringWith2(slot: number, formatId: number, argument1: SdkString, argument2: SdkString): void {
		let format: SdkString = this.getString(formatId);
		let args: SdkString[] = GameCanvas.formatArgs;
		GameCanvas.formatArgs[0] = argument1;
		args[1] = argument2;
		this.formattedStrings[((slot - -12) | 0)] = format.format_2(args);
	}
	formatStringWith3(slot: number, formatId: number, argument1: SdkString, argument2: SdkString, argument3: SdkString): void {
		let format: SdkString = this.getString(formatId);
		let args: SdkString[] = GameCanvas.formatArgs;
		GameCanvas.formatArgs[0] = argument1;
		args[1] = argument2;
		args[2] = argument3;
		this.formattedStrings[((slot - -12) | 0)] = format.format_2(args);
	}
	setString(slot: number, text: SdkString): void {
		this.formattedStrings[((slot - -12) | 0)] = text;
	}
	beginString(): SdkString {
		this.buildStringCursor++;
		if (this.buildStringCursor >= 25) {
			this.buildStringCursor = 0;
		}
		this.buildBuffer = this.stringPool[this.buildStringCursor];
		this.buildBuffer.setLength(0);
		return this.buildBuffer;
	}
	appendStringId(stringId: number): void {
		this.buildBuffer.append(this.getString(stringId));
	}
	appendString(text: SdkString): void {
		this.buildBuffer.append(text);
	}
	appendInt(value: number): void {
		let builder: SdkString = this.buildBuffer;
		let length: number = this.buildBuffer.length;
		if (value === 0) {
			builder.setLength(((length + 1) | 0));
			builder.setCharAt(length, 48);
		}
		else {
			if (value < 0) {
				builder.setLength(((length + 1) | 0));
				builder.setCharAt(length++, 45);
				value = ((value * -1) | 0);
			}
			let divisor: number = 1000000000;
			let digit: number = 0;
			for (let started: boolean = false; divisor > 0; divisor = ((divisor / 10) | 0)) {
				if ((digit = ((value / divisor) | 0)) !== 0 || started) {
					started = true;
					builder.setLength(((length + 1) | 0));
					builder.setCharAt(length++, ((((48 + digit) | 0)) & 0xffff));
				}
				value = ((value - (Math.imul(digit, divisor))) | 0);
			}
		}
	}
	appendGroupedInt(value: number): void {
		let builder: SdkString = this.buildBuffer;
		let length: number = this.buildBuffer.length;
		if (value === 0) {
			builder.setLength(((length + 1) | 0));
			builder.setCharAt(length, 48);
		}
		else {
			if (value < 0) {
				builder.setLength(((length + 1) | 0));
				builder.setCharAt(length++, 45);
				value = ((value * -1) | 0);
			}
			let divisor: number = 1000000000;
			let digit: number = 0;
			for (let started: boolean = false; divisor > 0; divisor = ((divisor / 10) | 0)) {
				if ((digit = ((value / divisor) | 0)) !== 0 || started) {
					started = true;
					builder.setLength(((length + 1) | 0));
					builder.setCharAt(length++, ((((48 + digit) | 0)) & 0xffff));
					if (divisor === 1000000000 || divisor === 1000000 || divisor === 1000) {
						builder.append(this.getString(31));
						length = builder.length;
					}
				}
				value = ((value - (Math.imul(digit, divisor))) | 0);
			}
		}
	}
	formatNumber(value: number): void {
		let savedCursor: number = this.buildStringCursor;
		let outer: SdkString = this.buildBuffer;
		let inner: SdkString = this.beginString();
		this.appendGroupedInt(Math.abs(value));
		let formatted: SdkString = this.getString(30).format(inner);
		if (value < 0) {
			outer.append_2("-");
		}
		outer.append(formatted);
		this.buildBuffer = outer;
		this.buildStringCursor = savedCursor;
	}
	appendTime(seconds: number): void {
		let minutes: number = ((seconds / 60) | 0);
		let secondsPart: number = ((seconds - Math.imul(minutes, 60)) | 0);
		let minuteValue: number = minutes;
		let builder: SdkString = this.buildBuffer;
		let index: number = this.buildBuffer.length;
		let minuteTens: number = 48;
		if (minutes >= 20) {
			minuteTens = 50;
			minuteValue = ((minutes - 20) | 0);
		}
		else {
			if (minutes >= 10) {
				minuteTens = 49;
				minuteValue = ((minutes - 10) | 0);
			}
		}
		builder.setLength(((index + 1) | 0));
		builder.setCharAt(index++, minuteTens);
		builder.setLength(((index + 1) | 0));
		builder.setCharAt(index++, ((((48 + minuteValue) | 0)) & 0xffff));
		builder.setLength(((index + 1) | 0));
		builder.setCharAt(index++, 58);
		let secondTens: number = ((secondsPart / 10) | 0);
		builder.setLength(((index + 2) | 0));
		builder.setCharAt(index++, ((((48 + secondTens) | 0)) & 0xffff));
		builder.setCharAt(index, ((((48 + (((secondsPart - Math.imul(secondTens, 10)) | 0))) | 0)) & 0xffff));
	}
	initTextSlots(): void {
		this.wrappedLineOffsets = new Int16Array(255);
		this.wrappedLineOffsets[0] = 0;
		this.wrapOffsets = Array.from({ length: 20 }, () => new Int16Array(10));
		for (let slot: number = 0; slot < 20; slot++) {
			this.wrapOffsets[slot][0] = 0;
		}
		this.slotTexts = new Array(20).fill(null);
		this.slotStringIds = new Int16Array(20);
	}
	setWrappedString(stringId: number, fontIndex: number, width: number): number {
		SdkText.setFont(this.fonts[fontIndex]);
		if (stringId === -1) {
			return 0;
		}
		else {
			this.wrappedString = this.getString(stringId).copy();
			if (stringId === 89) {
				let version: string = this.app.getAppProperty("MIDlet-Version");
				this.wrappedString = this.wrappedString.format_3(version);
			}
			SdkText.wrapText(this.wrappedString, this.wrappedLineOffsets, width, (124 << 16 >> 16));
			return this.wrappedLineOffsets[0];
		}
	}
	wrappedLineCount(): number {
		return this.wrappedLineOffsets[0];
	}
	drawWrappedText(fontIndex: number, xPos: number, yPos: number, anchor: number): void {
		SdkText.setFont(this.fonts[fontIndex]);
		SdkText.setSpaceSpacing(5);
		SdkText.drawWrappedString(this.wrappedString, this.wrappedLineOffsets, 1, this.wrappedLineOffsets[0], xPos, yPos, anchor);
	}
	drawWrappedTextClipped(fontIndex: number, xPos: number, yPos: number, anchor: number, firstLine: number, lineCount: number): void {
		SdkText.setFont(this.fonts[fontIndex]);
		SdkText.setSpaceSpacing(5);
		if ((anchor & 2) !== 0) {
			let lineHeight: number = ((SdkText.getLineHeight() + SdkText.getLeadingSpacing()) | 0);
			let halfHeight: number = 0;
			let centerOffset: number = (halfHeight = Math.imul(lineCount, lineHeight)) >> 1;
			yPos = ((yPos - centerOffset) | 0);
			anchor = (anchor ^ 2);
		}
		lineCount = Math.min(((this.wrappedLineOffsets[0] - firstLine) | 0), lineCount);
		SdkText.drawWrappedString(this.wrappedString, this.wrappedLineOffsets, ((firstLine + 1) | 0), lineCount, xPos, yPos, anchor);
	}
	setSlotText(slot: number, stringId: number, fontIndex: number, width: number): number {
		SdkText.setFont(this.fonts[fontIndex]);
		this.slotStringIds[slot] = (stringId << 16 >> 16);
		if (stringId === -1) {
			return 0;
		}
		else {
			this.slotTexts[slot] = this.getString(stringId).copy();
			SdkText.wrapText(this.slotTexts[slot], this.wrapOffsets[slot], width, (124 << 16 >> 16));
			return this.wrapOffsets[slot][0];
		}
	}
	drawSlotText(slot: number, stringId: number, fontIndex: number, xPos: number, yPos: number, firstLine: number, lineCount: number): void {
		if (stringId !== this.slotStringIds[slot]) {
			this.setSlotText(slot, stringId, fontIndex, xPos);
			this.slotStringIds[slot] = (stringId << 16 >> 16);
		}
		SdkText.setFont(this.fonts[fontIndex]);
		SdkText.setSpaceSpacing(5);
		SdkText.drawWrappedString(this.slotTexts[slot], this.wrapOffsets[slot], 1, this.wrapOffsets[slot][0], yPos, firstLine, lineCount);
	}
	drawTextById(gfx: Graphics, stringId: number, fontIndex: number, xPos: number, yPos: number, maxWidth: number, anchor: number): void {
		this.drawTextClipped(gfx, this.getString(stringId), fontIndex, xPos, yPos, maxWidth, anchor);
	}
	drawTextClipped(gfx: Graphics, text: SdkString, fontIndex: number, xPos: number, yPos: number, maxWidth: number, anchor: number): void {
		if (this.stringWidth(text, fontIndex) <= maxWidth) {
			if ((anchor & 1) !== 0) {
				xPos = ((xPos + (maxWidth >> 1)) | 0);
			}
			this.drawString(text, fontIndex, xPos, yPos, anchor);
		}
		else {
			let clipX: number = gfx.getClipX();
			let clipY: number = gfx.getClipY();
			let clipWidth: number = gfx.getClipWidth();
			let clipHeight: number = gfx.getClipHeight();
			let ellipsisWidth: number = this.stringWidthById(26, fontIndex);
			gfx.setClip(xPos, clipY, ((maxWidth - ellipsisWidth) | 0), clipHeight);
			this.drawString(text, fontIndex, xPos, yPos, anchor & -10 | 4);
			gfx.setClip(clipX, clipY, clipWidth, clipHeight);
			this.drawStringById(26, fontIndex, ((xPos + maxWidth) | 0), yPos, anchor & -6 | 8);
		}
	}
	resetMarquees(): void {
		for (let slot: number = 0; slot < 2; slot++) {
			this.marqueeStringIds[slot] = -1;
			this.marqueeTexts[slot] = null;
		}
	}
	setMarqueeId(slot: number, fontIndex: number, stringId: number): void {
		if (slot < 2) {
			this.marqueeTimers[slot] = 0;
			this.marqueeStringIds[slot] = stringId;
			this.marqueeTexts[slot] = null;
			if (stringId !== -1) {
				this.textWidths[slot] = this.stringWidthById(stringId, fontIndex);
				this.scrollOffsets[slot] = 0;
			}
		}
	}
	setMarqueeText(slot: number, fontIndex: number, text: SdkString): void {
		if (slot < 2) {
			this.marqueeTimers[slot] = 0;
			this.marqueeStringIds[slot] = -1;
			this.marqueeTexts[slot] = text;
			if (text !== null) {
				this.textWidths[slot] = this.stringWidth(text, fontIndex);
				this.scrollOffsets[slot] = 0;
			}
		}
	}
	updateMarquees(deltaMs: number): void {
		for (let slot: number = 0; slot < 2; slot++) {
			if (this.marqueeStringIds[slot] !== -1 || this.marqueeTexts[slot] !== null) {
				this.marqueeTimers[slot] = ((this.marqueeTimers[slot] + deltaMs) | 0);
				if (this.marqueeTimers[slot] > 1500) {
					let overrun: number = ((this.marqueeTimers[slot] - 1500) | 0);
					this.scrollOffsets[slot] = ((Math.floor((overrun * 20) / 1024)) | 0);
					let gap: number = ((this.textWidths[slot] + 20) | 0);
					if (this.scrollOffsets[slot] >= gap) {
						this.scrollOffsets[slot] = ((this.scrollOffsets[slot] - gap) | 0);
						this.marqueeTimers[slot] = ((this.marqueeTimers[slot] - (((gap << 10) / 20) | 0)) | 0);
					}
				}
			}
		}
	}
	drawMarqueeId(slot: number, gfx: Graphics, stringId: number, fontIndex: number, xPos: number, yPos: number, maxWidth: number, anchor: number): void {
		if (this.marqueeStringIds[slot] !== stringId || this.textWidths[slot] !== this.stringWidthById(stringId, fontIndex)) {
			this.setMarqueeId(slot, fontIndex, stringId);
		}
		if (this.textWidths[slot] <= maxWidth) {
			if ((anchor & 1) !== 0) {
				xPos = ((xPos + (maxWidth >> 1)) | 0);
			}
			else {
				if ((anchor & 8) !== 0) {
					xPos = ((xPos + maxWidth) | 0);
				}
			}
			this.drawStringById(stringId, fontIndex, xPos, yPos, anchor);
		}
		else {
			let clipX: number = gfx.getClipX();
			let clipY: number = gfx.getClipY();
			let clipWidth: number = gfx.getClipWidth();
			let clipHeight: number = gfx.getClipHeight();
			gfx.setClip(xPos, clipY, maxWidth, clipHeight);
			anchor = anchor & -10 | 4;
			this.drawStringById(stringId, fontIndex, ((xPos - this.scrollOffsets[slot]) | 0), yPos, anchor);
			this.drawStringById(stringId, fontIndex, ((((((xPos - this.scrollOffsets[slot]) | 0) + this.textWidths[slot]) | 0) + 20) | 0), yPos, anchor);
			gfx.setClip(clipX, clipY, clipWidth, clipHeight);
		}
	}
	drawMarqueeText(slot: number, gfx: Graphics, text: SdkString, fontIndex: number, xPos: number, yPos: number, maxWidth: number, anchor: number): void {
		if (this.marqueeTexts[slot] !== text || this.textWidths[slot] !== this.stringWidth(text, fontIndex)) {
			this.setMarqueeText(slot, fontIndex, text);
		}
		if (this.textWidths[slot] <= maxWidth) {
			if ((anchor & 1) !== 0) {
				xPos = ((xPos + (maxWidth >> 1)) | 0);
			}
			else {
				if ((anchor & 8) !== 0) {
					xPos = ((xPos + maxWidth) | 0);
				}
			}
			this.drawString(text, fontIndex, xPos, yPos, anchor);
		}
		else {
			let clipX: number = gfx.getClipX();
			let clipY: number = gfx.getClipY();
			let clipWidth: number = gfx.getClipWidth();
			let clipHeight: number = gfx.getClipHeight();
			gfx.setClip(xPos, clipY, maxWidth, clipHeight);
			anchor = anchor & -10 | 4;
			this.drawString(text, fontIndex, ((xPos - this.scrollOffsets[slot]) | 0), yPos, anchor);
			this.drawString(text, fontIndex, ((((((xPos - this.scrollOffsets[slot]) | 0) + this.textWidths[slot]) | 0) + 20) | 0), yPos, anchor);
			gfx.setClip(clipX, clipY, clipWidth, clipHeight);
		}
	}
	drawScrollingText(slot: number, fontIndex: number, stringId: number, xPos: number, yPos: number, anchor: number): void {
		if (this.marqueeStringIds[slot] !== fontIndex) {
			this.setMarqueeText(slot, stringId, SdkText.getString(fontIndex, null));
			this.marqueeStringIds[slot] = fontIndex;
			this.marqueeTimers[slot] = 1500;
		}
		let text: SdkString = this.marqueeTexts[slot];
		let textWidth: number = this.stringWidth(text, stringId);
		if ((anchor & 1) !== 0) {
			xPos = ((xPos - (textWidth >> 1)) | 0);
		}
		else {
			if ((anchor & 8) !== 0) {
				xPos = ((xPos - textWidth) | 0);
			}
		}
		anchor = anchor & -10 | 4;
		let charString: SdkString = null!;
		(charString = this.beginString()).setLength(1);
		let charCount: number = text.length;
		let scroll: number = 0;
		if ((scroll = this.scrollOffsets[slot] << 1) > ((textWidth + 20) | 0)) {
			this.marqueeTimers[slot] = 1500;
			this.scrollOffsets[slot] = 0;
			scroll = 0;
		}
		let waveStart: number = 0;
		let waveEnd: number = (((waveStart = ((((scroll - 10) | 0) - 20) | 0)) + 20) | 0);
		let penX: number = 0;
		for (let charIndex: number = 0; charIndex < charCount; charIndex++) {
			charString.setCharAt(0, text.charAt(charIndex));
			if (penX >= waveStart && penX <= waveEnd) {
				let phase: number = FixedMath.div(((penX - waveStart) | 0) << 16, 1310720);
				let wave: number = Math.imul(FixedMath.cos(((FixedMath.mul(411775, phase) - 205887) | 0)), 6);
				this.drawString(charString, stringId, ((xPos + penX) | 0), ((((yPos - (wave >> 16)) | 0) - 6) | 0), anchor);
			}
			else {
				this.drawString(charString, stringId, ((xPos + penX) | 0), yPos, anchor);
			}
			penX = ((penX + this.stringWidth(charString, stringId)) | 0);
		}
	}
	fadeFromWhite(): void {
		this.fadeColor = 16777215;
		this.fadeStep = -1;
		this.fading = true;
		this.fadeFinished = false;
	}
	fadeFromBlack(): void {
		this.fadeColor = 0;
		this.fadeStep = 1;
		this.fading = true;
		this.fadeFinished = false;
	}
	clearFade(): void {
		this.fadeColor = 16777215;
		this.fadeStep = -1;
		this.fading = false;
		this.fadeFinished = true;
	}
	isFading(): boolean {
		return this.fading && !this.fadeFinished;
	}
	isFadingFromWhite(): boolean {
		return this.isFading() && this.fadeStep === -1;
	}
	drawFade(gfx: Graphics): void {
		this.fade.setViewport(0, 0, this.getWidth(), this.getHeight());
		this.fade.setBlending(66);
		this.fade.setColor(this.fadeColor);
		this.fade.render(gfx);
	}
	drawFadeRect(gfx: Graphics, blending: number, color: number, xPos: number, yPos: number, width: number, height: number): void {
		this.fade.setBlending(blending);
		this.fade.setViewport(xPos, yPos, width, height);
		this.fade.setColor(color);
		this.fade.render(gfx);
	}
	drawDialogOverlay(gfx: Graphics): void {
		this.fade.setViewport(0, 0, this.getWidth(), ((this.getHeight() - 15) | 0));
		this.fade.setBlending(66);
		this.fade.setColor(6316128);
		this.fade.render(gfx);
	}
	static gcYield(): void {
		gc();
		void 0;
		try {
			void 0;
		}
		catch (ignored) {
			if (ignored instanceof InterruptedException) {
			}
			else {
				throw ignored;
			}
		}
	}
	setGcPending(pending: boolean): void {
		if (pending) {
			GameCanvas.gcYield();
			this.gcPending = false;
		}
		else {
			this.gcPending = true;
		}
	}
	gameScreen(): GameScreen {
		return this.screen instanceof GameScreen ? (this.screen as unknown as GameScreen) : null;
	}
	titleScreen(): TitleScreen {
		return this.screen instanceof TitleScreen ? (this.screen as unknown as TitleScreen) : null;
	}
	setPendingScreen(action: number, languageIndex: number): void {
		this.pendingScreenAction = action;
		this.pendingLanguage = languageIndex;
	}
	changeScreen(action: number, languageIndex: number): void {
		try {
			let sameGameScreen: boolean = this.gameScreen() !== null && action === 2;
			this.loadingTipId = -1;
			if (this.screen !== null) {
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
		}
		catch (ignored) {
		}
		this.clearActions();
		this.releaseSoftKeys();
		this.repaint();
	}
	randomRange(min: number, max: number): number {
		let range: number = ((((max + 1) | 0) - min) | 0);
		return ((min + this.random.nextInt(range)) | 0);
	}
	randomPercent(): number {
		return this.randomRange(0, 99);
	}
	vibrateShort(): void {
		this.vibrate(400);
	}
	vibrate(durationMs: number): void {
		if (this.vibrationEnabled) {
			this.soundValue.vibrate(durationMs);
		}
	}
	trackCheatSequence(action: number): void {
		for (let sequenceIndex: number = 0; sequenceIndex < GameCanvas.cheatSequences.length; sequenceIndex++) {
			let sequence: Int32Array = null!;
			let lastIndex: number = (((sequence = GameCanvas.cheatSequences[sequenceIndex]).length - 1) | 0);
			let cursor: number = sequence[0];
			let expected: number = 0;
			if ((expected = sequence[((cursor + 1) | 0)]) === action) {
				if (++cursor === lastIndex) {
					cursor = 0;
					switch (sequenceIndex) {
						case 0:
							this.cheatToggled = !this.cheatToggled;
					}
				}
			}
			else {
				cursor = 0;
			}
			sequence[0] = cursor;
		}
	}
	static ease(kind: number, from: number, toValue: number, elapsed: number, duration: number): number {
		if (elapsed >= duration) {
			return toValue;
		}
		else {
			if (elapsed < 0) {
				return from;
			}
			else {
				let value: number = 0;
				switch (kind) {
					case 0:
					default:
						return ((from + Math.trunc(elapsed * (((toValue - from) | 0)) / duration)) | 0);
					case 1:
						let half: number = ((Math.floor((duration) / 2)) | 0);
						if (elapsed < half) {
							value = FixedMath.mul(FixedMath.sinShifted(FixedMath.div(FixedMath.mul((elapsed | 0), 102944), half)), 72089);
						}
						else {
							let progress: number = 0;
							let angle: number = FixedMath.mul(progress = FixedMath.div((((elapsed | 0) - half) | 0), (((duration | 0) - half) | 0)), 617662);
							let amplitude: number = ((6554 - FixedMath.mul(6554, progress)) | 0);
							value = ((FixedMath.mul(FixedMath.cos(angle), amplitude) + 65536) | 0);
						}
						break;
					case 2:
						value = FixedMath.smoothStep(0, (duration | 0), (elapsed | 0));
				}
				return ((from + (Math.imul(value, (((toValue - from) | 0))) >> 16)) | 0);
			}
		}
	}
	startSoftKeyAnimation(action: number): void {
		if (!this.isFading() && action !== 0 && !this.leftSoftKeyAnimator.isRunning()) {
			if (action === this.leftSoftKeyAction) {
				this.releaseSoftKeys();
				this.rightSoftKeySelected = false;
				this.leftSoftKeyAnimator.play(675, 16);
				this.leftSoftKeyAnimator.frame = 1;
				this.rightSoftKeyAnimator.play(677, 16);
				this.rightSoftKeyAnimator.frame = 1;
			}
			else {
				if (action === this.rightSoftKeyAction) {
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
	updateSoftKeyAnimation(deltaMs: number): void {
		if (this.isFading()) {
			this.leftSoftKeyTimer = 0;
			this.rightSoftKeyTimer = 0;
		}
		else {
			if (this.leftSoftKeyTimer < 400) {
				this.leftSoftKeyTimer += deltaMs;
			}
			if (this.rightSoftKeyTimer < 400) {
				this.rightSoftKeyTimer += deltaMs;
			}
		}
		if (this.leftSoftKeyAnimator.isRunning()) {
			this.leftSoftKeyAnimator.advance(deltaMs);
			this.rightSoftKeyAnimator.advance(deltaMs);
			if (!this.leftSoftKeyAnimator.isRunning()) {
				let action: number = this.rightSoftKeySelected ? this.rightSoftKeyAction : this.leftSoftKeyAction;
				if (this.screen !== null) {
					this.screen.update(0, action);
				}
			}
		}
	}
	drawBottomBar(gfx: Graphics): void {
		this.drawBottomBar_2(gfx, 0);
	}
	drawBottomBar_2(gfx: Graphics, yOffset: number): void {
		let height: number = this.getHeight();
		let width: number = this.getWidth();
		let frameWidth: number = SpriteRenderer.frameWidth(674, 0);
		for (let xPos: number = 0; xPos < width; xPos = ((xPos + frameWidth) | 0)) {
			SpriteRenderer.drawFrame(gfx, 674, 0, xPos, ((height + yOffset) | 0));
		}
		if (!this.isFading()) {
			this.drawSoftKeys(gfx, yOffset);
		}
	}
	drawSoftKeys(gfx: Graphics, yOffset: number): void {
		let leftLabelId: number = GameCanvas.actionToLabelId(this.leftSoftKeyAction);
		let rightLabelId: number = GameCanvas.actionToLabelId(this.rightSoftKeyAction);
		if (GameCanvas.landscape) {
			if (leftLabelId >= 0) {
				let leftTextWide: SdkString = this.getString(leftLabelId);
				let leftAnimOffsetWide: number = GameCanvas.ease(1, 15, 0, this.rightSoftKeyTimer, 400);
				let leftYWide: number = 0;
				let leftTextYWide: number = (((leftYWide = ((((this.getHeight() + leftAnimOffsetWide) | 0) + yOffset) | 0)) - 7) | 0);
				let leftWidthWide: number = Math.max(25, this.stringWidth(leftTextWide, 0));
				let rightXWide: number = 0;
				let leftXWide: number = 0;
				let centerXWide: number = (((leftXWide = (((((rightXWide = ((this.getWidth() - 11) | 0)) - leftWidthWide) | 0) + 4) | 0)) + rightXWide) | 0) >> 1;
				if (this.leftSoftKeyAnimator.isRunning() && this.rightSoftKeySelected) {
					SpriteRenderer.drawAnimator(gfx, this.leftSoftKeyAnimator, leftXWide, leftYWide);
					SpriteRenderer.drawAnimator(gfx, this.rightSoftKeyAnimator, rightXWide, leftYWide);
					GameCanvas.drawDivider(gfx, ((leftXWide + 2) | 0), leftYWide, ((((rightXWide - leftXWide) | 0) - 4) | 0), true);
					this.drawString(leftTextWide, 0, centerXWide, leftTextYWide, 3);
				}
				else {
					SpriteRenderer.drawFrame(gfx, 677, 0, rightXWide, leftYWide);
					SpriteRenderer.drawFrame(gfx, 675, 0, leftXWide, leftYWide);
					GameCanvas.drawDivider(gfx, ((leftXWide + 2) | 0), leftYWide, ((((rightXWide - leftXWide) | 0) - 4) | 0), false);
					this.drawString(leftTextWide, 0, centerXWide, leftTextYWide, 3);
				}
			}
			if (rightLabelId >= 0) {
				let rightTextWide: SdkString = this.getString(rightLabelId);
				let rightAnimOffsetWide: number = GameCanvas.ease(1, 15, 0, this.rightSoftKeyTimer, 400);
				let rightYWide: number = 0;
				let rightTextYWide: number = (((rightYWide = ((((15 + rightAnimOffsetWide) | 0) + yOffset) | 0)) - 7) | 0);
				let rightWidthWide: number = Math.max(25, this.stringWidth(rightTextWide, 0));
				let rightRightXWide: number = 0;
				let rightLeftXWide: number = 0;
				let rightCenterXWide: number = (((rightLeftXWide = (((((rightRightXWide = ((this.getWidth() - 11) | 0)) - rightWidthWide) | 0) + 4) | 0)) + rightRightXWide) | 0) >> 1;
				if (this.leftSoftKeyAnimator.isRunning() && this.rightSoftKeySelected) {
					SpriteRenderer.drawAnimator(gfx, this.leftSoftKeyAnimator, rightLeftXWide, rightYWide);
					SpriteRenderer.drawAnimator(gfx, this.rightSoftKeyAnimator, rightRightXWide, rightYWide);
					GameCanvas.drawDivider(gfx, ((rightLeftXWide + 2) | 0), rightYWide, ((((rightRightXWide - rightLeftXWide) | 0) - 4) | 0), true);
					this.drawString(rightTextWide, 0, rightCenterXWide, rightTextYWide, 3);
					return;
				}
				SpriteRenderer.drawFrame(gfx, 677, 0, rightRightXWide, rightYWide);
				SpriteRenderer.drawFrame(gfx, 675, 0, rightLeftXWide, rightYWide);
				GameCanvas.drawDivider(gfx, ((rightLeftXWide + 2) | 0), rightYWide, ((((rightRightXWide - rightLeftXWide) | 0) - 4) | 0), false);
				this.drawString(rightTextWide, 0, rightCenterXWide, rightTextYWide, 3);
			}
		}
		else {
			if (leftLabelId >= 0) {
				let leftText: SdkString = this.getString(leftLabelId);
				let leftAnimOffset: number = GameCanvas.ease(1, 15, 0, this.leftSoftKeyTimer, 400);
				let leftY: number = 0;
				let leftTextY: number = (((leftY = ((((this.getHeight() + leftAnimOffset) | 0) + yOffset) | 0)) - 7) | 0);
				let leftWidth: number = Math.max(25, this.stringWidth(leftText, 0));
				let rightX: number = ((((11 + leftWidth) | 0) - 4) | 0);
				let leftCenterX: number = ((11 + rightX) | 0) >> 1;
				if (this.leftSoftKeyAnimator.isRunning() && !this.rightSoftKeySelected) {
					SpriteRenderer.drawAnimator(gfx, this.leftSoftKeyAnimator, 11, leftY);
					SpriteRenderer.drawAnimator(gfx, this.rightSoftKeyAnimator, rightX, leftY);
					GameCanvas.drawDivider(gfx, 13, leftY, ((((rightX - 11) | 0) - 4) | 0), true);
					this.drawString(leftText, 0, leftCenterX, leftTextY, 3);
				}
				else {
					SpriteRenderer.drawFrame(gfx, 677, 0, rightX, leftY);
					SpriteRenderer.drawFrame(gfx, 675, 0, 11, leftY);
					GameCanvas.drawDivider(gfx, 13, leftY, ((((rightX - 11) | 0) - 4) | 0), false);
					this.drawString(leftText, 0, leftCenterX, leftTextY, 3);
				}
			}
			if (rightLabelId >= 0) {
				let rightText: SdkString = this.getString(rightLabelId);
				let rightAnimOffset: number = GameCanvas.ease(1, 15, 0, this.rightSoftKeyTimer, 400);
				let rightY: number = 0;
				let rightTextY: number = (((rightY = ((((this.getHeight() + rightAnimOffset) | 0) + yOffset) | 0)) - 7) | 0);
				let rightWidth: number = Math.max(25, this.stringWidth(rightText, 0));
				let rightLimit: number = 0;
				let rightRightX: number = 0;
				let rightCenterX: number = (((rightRightX = (((((rightLimit = ((this.getWidth() - 11) | 0)) - rightWidth) | 0) + 4) | 0)) + rightLimit) | 0) >> 1;
				if (!this.leftSoftKeyAnimator.isRunning() || !this.rightSoftKeySelected) {
					SpriteRenderer.drawFrame(gfx, 677, 0, rightLimit, rightY);
					SpriteRenderer.drawFrame(gfx, 675, 0, rightRightX, rightY);
					GameCanvas.drawDivider(gfx, ((rightRightX + 2) | 0), rightY, ((((rightLimit - rightRightX) | 0) - 4) | 0), false);
					this.drawString(rightText, 0, rightCenterX, rightTextY, 3);
					return;
				}
				SpriteRenderer.drawAnimator(gfx, this.leftSoftKeyAnimator, rightRightX, rightY);
				SpriteRenderer.drawAnimator(gfx, this.rightSoftKeyAnimator, rightLimit, rightY);
				GameCanvas.drawDivider(gfx, ((rightRightX + 2) | 0), rightY, ((((rightLimit - rightRightX) | 0) - 4) | 0), true);
				this.drawString(rightText, 0, rightCenterX, rightTextY, 3);
			}
		}
	}
	static drawDivider(gfx: Graphics, xPos: number, yPos: number, width: number, active: boolean): void {
		let frame: number = active ? 1 : 0;
		let clipX: number = gfx.getClipX();
		let clipY: number = gfx.getClipY();
		let clipWidth: number = gfx.getClipWidth();
		let clipHeight: number = gfx.getClipHeight();
		gfx.setClip(xPos, clipY, width, clipHeight);
		SpriteRenderer.drawFrame(gfx, 676, frame, xPos, yPos);
		gfx.setClip(clipX, clipY, clipWidth, clipHeight);
	}
	resetTileLoad(): void {
		this.tileLoadCursor = 0;
	}
	loadTilesFor(mask: number): boolean {
		return this.loadNextTileGroup(mask, null, null, 0);
	}
	loadNextTileGroup(mask: number, recolorFrom: Int32Array, recolorTo: Int32Array, recolorIndex: number): boolean {
		let loader: ResourceLoader = this.resources;
		let loaded: number = 0;
		let index: number = 0;
		for (index = this.tileLoadCursor; index << 1 < this.tileMasks.length && loaded < 2; index++) {
			if ((this.tileMasks[(((index << 1) + 1) | 0)] & mask) !== 0) {
				let attempt: number = 1;
				while (!SpriteRenderer.loadTileRecolored(loader, this.tileMasks[index << 1], recolorFrom, recolorTo, recolorIndex) && attempt < 2) {
					attempt++;
					GameCanvas.gcYield();
				}
				if (attempt >= 2) {
					throw new Error("OOM");
				}
				loaded++;
			}
		}
		this.tileLoadCursor = index;
		return index << 1 === this.tileMasks.length;
	}
	loadTilesBlocking(mask: number): void {
		this.loadTiles(mask, null, null, 0);
	}
	loadTiles(mask: number, recolorFrom: Int32Array, recolorTo: Int32Array, recolorIndex: number): void {
		this.resetTileLoad();
		while (!this.loadNextTileGroup(mask, recolorFrom, recolorTo, recolorIndex)) {
		}
	}
	unloadTiles(mask: number, variant: number): void {
		let masks: Int32Array = this.tileMasks;
		for (let index: number = 0; index << 1 < masks.length; index++) {
			if ((masks[(((index << 1) + 1) | 0)] & mask) !== 0) {
				let tileId: number = masks[index << 1];
				if (variant === -1) {
					for (let variantIndex: number = 0; variantIndex < 4; variantIndex++) {
						SpriteRenderer.unloadTile(tileId, variantIndex);
					}
				}
				else {
					SpriteRenderer.unloadTile(tileId, variant);
				}
			}
		}
		GameCanvas.gcYield();
	}
	static fillObjects(array: any[], value: any): void {
		for (let index: number = 0; index < array.length; index++) {
			array[index] = value;
		}
	}
	static fillBytes(array: Int8Array, value: number): void {
		for (let index: number = 0; index < array.length; index++) {
			array[index] = (value << 24 >> 24);
		}
	}
	static fillShorts(array: Int16Array, value: number): void {
		for (let index: number = 0; index < array.length; index++) {
			array[index] = (value << 16 >> 16);
		}
	}
	static fillInts(array: Int32Array, value: number): void {
		for (let index: number = 0; index < array.length; index++) {
			array[index] = value;
		}
	}
	static fillIntMatrix(matrix: Int32Array[], value: number): void {
		for (let row: number = 0; row < matrix.length; row++) {
			GameCanvas.fillInts(matrix[row], value);
		}
	}
	static fillShortMatrix(matrix: Int16Array[], value: number): void {
		for (let row: number = 0; row < matrix.length; row++) {
			GameCanvas.fillShorts(matrix[row], value);
		}
	}
	static indexOfInt(value: number, array: Int32Array): number {
		return GameCanvas.indexOfIntFrom(value, array, 0);
	}
	static indexOfShort(value: number, array: Int16Array): number {
		return GameCanvas.indexOfShortFrom(value, array, 0);
	}
	static indexOfByte(value: number, array: Int8Array): number {
		return GameCanvas.indexOfByteFrom(value, array, 0);
	}
	static indexOfObject(target: any, array: any[]): number {
		return GameCanvas.indexOfObjectFrom(target, array, 0);
	}
	static indexOfIntFrom(value: number, array: Int32Array, from: number): number {
		for (let index: number = from; index < array.length; index++) {
			if (array[index] === value) {
				return index;
			}
		}
		return -1;
	}
	static indexOfShortFrom(value: number, array: Int16Array, from: number): number {
		for (let index: number = from; index < array.length; index++) {
			if (array[index] === value) {
				return index;
			}
		}
		return -1;
	}
	static indexOfByteFrom(value: number, array: Int8Array, from: number): number {
		for (let index: number = from; index < array.length; index++) {
			if (array[index] === value) {
				return index;
			}
		}
		return -1;
	}
	static indexOfObjectFrom(target: any, array: any[], from: number): number {
		for (let index: number = from; index < array.length; index++) {
			if (array[index] === target) {
				return index;
			}
		}
		return -1;
	}
	static indexOfAnyFlag(mask: number, array: Int16Array): number {
		return GameCanvas.indexOfAnyFlagFrom(mask, array, 0);
	}
	static indexOfAnyFlagFrom(mask: number, array: Int16Array, from: number): number {
		for (let index: number = from; index < array.length; index++) {
			if ((array[index] & mask) !== 0) {
				return index;
			}
		}
		return -1;
	}
	static nthIndexNotEqualByte(occurrence: number, array: Int8Array, excluded: number): number {
		let count: number = 0;
		for (let index: number = 0; index < array.length; index++) {
			if (array[index] !== excluded && count++ === occurrence) {
				return index;
			}
		}
		return -1;
	}
	static nthIndexNotEqualShort(occurrence: number, array: Int16Array, excluded: number): number {
		let count: number = 0;
		for (let index: number = 0; index < array.length; index++) {
			if (array[index] !== excluded && count++ === occurrence) {
				return index;
			}
		}
		return -1;
	}
	static nthIndexWithFlag(occurrence: number, array: Int16Array, mask: number): number {
		let count: number = 0;
		for (let index: number = 0; index < array.length; index++) {
			if ((array[index] & mask) !== 0 && count++ === occurrence) {
				return index;
			}
		}
		return -1;
	}
	static nthIndexWithFlagInt(occurrence: number, array: Int32Array, mask: number): number {
		let count: number = 0;
		for (let index: number = 0; index < array.length; index++) {
			if ((array[index] & mask) !== 0 && count++ === occurrence) {
				return index;
			}
		}
		return -1;
	}
	static countEqualShorts(value: number, array: Int16Array): number {
		let count: number = 0;
		for (let index: number = 0; index < array.length; index++) {
			if (array[index] === value) {
				count++;
			}
		}
		return count;
	}
	static countEqualBytes(value: number, array: Int8Array): number {
		let count: number = 0;
		for (let index: number = 0; index < array.length; index++) {
			if (array[index] === value) {
				count++;
			}
		}
		return count;
	}
	static countAnyFlagInt(mask: number, array: Int32Array): number {
		return GameCanvas.countAnyFlagIntFrom(mask, array, 0);
	}
	static countAnyFlagIntFrom(mask: number, array: Int32Array, from: number): number {
		let count: number = 0;
		for (let index: number = from; index < array.length; index++) {
			if ((array[index] & mask) !== 0) {
				count++;
			}
		}
		return count;
	}
	static countAllFlags(mask: number, array: Int16Array): number {
		return GameCanvas.countAllFlagsFrom(mask, array, 0);
	}
	static countAllFlagsFrom(mask: number, array: Int16Array, from: number): number {
		let count: number = 0;
		for (let index: number = from; index < array.length; index++) {
			if ((array[index] & mask) !== 0) {
				count++;
			}
		}
		return count;
	}
	static initList(list: Int16Array, value: number): void {
		GameCanvas.initListWith(list, value, -1);
	}
	static initListWith(list: Int16Array, value: number, extra: number): void {
		list[1] = (value << 16 >> 16);
		list[2] = 0;
		list[3] = 0;
		list[4] = (extra << 16 >> 16);
		list[0] = 0;
	}
	static addToList(list: Int16Array, value: number): void {
		let count: number = list[0];
		list[((5 + count) | 0)] = (value << 16 >> 16);
		list[0] = ((((count + 1) | 0)) << 16 >> 16);
	}
	static listContains(list: Int16Array, value: number): boolean {
		let end: number = ((list[0] + 5) | 0);
		for (let index: number = 5; index < end; index++) {
			if (value === list[index]) {
				return true;
			}
		}
		return false;
	}
	static copyList(target: Int16Array, source: Int16Array): void {
		arraycopy(source, 0, target, 0, ((5 + source[0]) | 0));
	}
	static clampListIndex(list: Int16Array): void {
		let maxValue: number = 0;
		if ((maxValue = list[4]) > 0) {
			let value: number = list[2];
			let start: number = list[3];
			if (value < start) {
				start = value;
			}
			else {
				if (value >= ((start + maxValue) | 0)) {
					start = Math.max(0, ((((value - maxValue) | 0) + 1) | 0));
				}
			}
			list[3] = (start << 16 >> 16);
		}
	}
	navigateLeftRight(list: Int16Array, actions: number): boolean {
		let count: number = list[0];
		let index: number = list[2];
		if ((actions & 8196) !== 0) {
			this.clearAction(8196);
			list[2] = (((((((((index + count) | 0) - 1) | 0)) % count) | 0)) << 16 >> 16);
		}
		if ((actions & 16640) !== 0) {
			this.clearAction(16640);
			list[2] = (((((((index + 1) | 0)) % count) | 0)) << 16 >> 16);
		}
		if (list[2] !== index) {
			GameCanvas.clampListIndex(list);
			if (this.gameScreen() !== null) {
				this.soundValue.pendingSound = 0;
			}
			return true;
		}
		else {
			return false;
		}
	}
	navigateUpDown(list: Int16Array, actions: number): boolean {
		let count: number = list[0];
		let index: number = list[2];
		if ((actions & 32784) !== 0) {
			this.clearAction(32784);
			list[2] = (((((((((index + count) | 0) - 1) | 0)) % count) | 0)) << 16 >> 16);
		}
		if ((actions & 65600) !== 0) {
			this.clearAction(65600);
			list[2] = (((((((index + 1) | 0)) % count) | 0)) << 16 >> 16);
		}
		if (list[2] !== index) {
			GameCanvas.clampListIndex(list);
			this.resetMarquees();
			if (this.gameScreen() !== null) {
				this.soundValue.pendingSound = 1;
			}
			return true;
		}
		else {
			return false;
		}
	}
	static selectListValue(list: Int16Array, value: number): void {
		for (let index: number = 5; index < list.length; index++) {
			if (list[index] === value) {
				list[2] = ((((index - 5) | 0)) << 16 >> 16);
				return;
			}
		}
	}
	resetSettings(): void {
		this.vibrationEnabled = true;
		this.tutorialsEnabled = true;
		this.autonomousEnabled = true;
		this.savePromptsEnabled = true;
		this.gameStarted = false;
		this.flagsLow = 0n;
		this.flagsHigh = 0n;
		this.visitedFlagsLow = 0n;
		this.visitedFlagsHigh = 0n;
		this.language = -1;
		this.resetProfiles();
	}
	loadSettings(): void {
		let store: RecordStore = null;
		let bytes: ByteArrayInputStream = null;
		let input: DataInputStream = null;
		try {
			let data: Int8Array = (store = RecordStore.openRecordStore("sim3set", false)).getRecord(1);
			bytes = new ByteArrayInputStream(data);
			let version: number = (input = new DataInputStream(bytes)).readInt();
			let format: number = input.readInt();
			if (version === 86 && format === 2) {
				this.vibrationEnabled = input.readBoolean();
				this.tutorialsEnabled = input.readBoolean();
				this.autonomousEnabled = input.readBoolean();
				this.savePromptsEnabled = input.readBoolean();
				this.gameStarted = input.readBoolean();
				this.flagsLow = input.readLong64();
				this.flagsHigh = input.readLong64();
				this.visitedFlagsLow = input.readLong64();
				this.visitedFlagsHigh = input.readLong64();
				this.language = input.readByte();
				for (let slot: number = 0; slot !== 2; slot++) {
					let done: boolean = false;
					let name: SdkString = this.profileNames[slot];
					for (let charIndex: number = 0; charIndex < 8; charIndex++) {
						let character: number = 0;
						if ((character = input.readChar()) === 32) {
							done = true;
						}
						else {
							if (!done) {
								name.setLength(((charIndex + 1) | 0));
								name.setCharAt(charIndex, character);
							}
						}
					}
					if (!done) {
						name.setLength(8);
					}
					this.profileUsed[slot] = input.readBoolean();
				}
			}
			else {
				this.resetSettings();
				this.saveSettings();
			}
		}
		catch (notFound) {
			if (notFound instanceof RecordStoreNotFoundException) {
				this.resetSettings();
			}
		}
		try {
			input.close();
			bytes.close();
			if (store !== null) {
				store.closeRecordStore();
			}
		}
		catch (closeError) {
		}
	}
	saveSettings(): boolean {
		try {
			RecordStore.deleteRecordStore("sim3set");
		}
		catch (deleteError) {
		}
		let failed: boolean = false;
		let store: RecordStore = null;
		let bytes: ByteArrayOutputStream = new ByteArrayOutputStream();
		let out: DataOutputStream = new DataOutputStream(bytes);
		try {
			out.writeInt(86);
			out.writeInt(2);
			out.writeBoolean(this.vibrationEnabled);
			out.writeBoolean(this.tutorialsEnabled);
			out.writeBoolean(this.autonomousEnabled);
			out.writeBoolean(this.savePromptsEnabled);
			out.writeBoolean(this.gameStarted);
			out.writeLong64(this.flagsLow);
			out.writeLong64(this.flagsHigh);
			out.writeLong64(this.visitedFlagsLow);
			out.writeLong64(this.visitedFlagsHigh);
			out.writeByte(this.language);
			for (let slot: number = 0; slot !== 2; slot++) {
				let name: SdkString = this.profileNames[slot];
				for (let charIndex: number = 0; charIndex !== 8; charIndex++) {
					let character: number = 32;
					if (charIndex < name.length) {
						character = name.charAt(charIndex);
					}
					out.writeChar(character);
				}
				out.writeBoolean(this.profileUsed[slot]);
			}
			let data: Int8Array = bytes.toByteArray();
			if ((store = RecordStore.openRecordStore("sim3set", true)).getNumRecords() === 0) {
				store.addRecord(data, 0, data.length);
			}
			else {
				store.setRecord(1, data, 0, data.length);
			}
		}
		catch (writeError) {
			failed = true;
		}
		try {
			out.close();
			bytes.close();
			store.closeRecordStore();
		}
		catch (closeError) {
			failed = true;
		}
		return !failed;
	}
	resetGameData(): void {
		this.progressFlags = 0;
		this.clearNewFlags();
		this.neighborhood.resetAllState();
		this.lot.resetStages();
	}
	loadGameData(): void {
		let store: RecordStore = null;
		let bytes: ByteArrayInputStream = null;
		let input: DataInputStream = null;
		try {
			let data: Int8Array = (store = RecordStore.openRecordStore("sim3dat" + this.profileSlot, false)).getRecord(1);
			bytes = new ByteArrayInputStream(data);
			if ((input = new DataInputStream(bytes)).readInt() === 86) {
				this.progressFlags = input.readInt();
				this.neighborhood.load(input);
				this.lot.readObjects(input);
				this.houseIndex = 0;
			}
			else {
				this.resetGameData();
				this.saveGameData();
			}
		}
		catch (loadError) {
			this.resetGameData();
			this.saveGameData();
		}
		try {
			input.close();
			bytes.close();
			if (store !== null) {
				store.closeRecordStore();
			}
		}
		catch (closeError) {
		}
	}
	saveGameData(): boolean {
		let recordName: string = "sim3dat" + this.profileSlot;
		try {
			RecordStore.deleteRecordStore(recordName);
		}
		catch (deleteError) {
		}
		this.flagsLow = (this.flagsLow | this.newFlagsLow);
		this.flagsHigh = (this.flagsHigh | this.newFlagsHigh);
		this.visitedFlagsLow = (this.visitedFlagsLow | this.newVisitedFlagsLow);
		this.visitedFlagsHigh = (this.visitedFlagsHigh | this.newVisitedFlagsHigh);
		this.saveSettings();
		let failed: boolean = false;
		let store: RecordStore = null;
		let bytes: ByteArrayOutputStream = new ByteArrayOutputStream();
		let out: DataOutputStream = new DataOutputStream(bytes);
		try {
			out.writeInt(86);
			out.writeInt(this.progressFlags);
			this.neighborhood.save(out);
			this.lot.writeObjects(out);
			let data: Int8Array = bytes.toByteArray();
			if ((store = RecordStore.openRecordStore(recordName, true)).getNumRecords() === 0) {
				store.addRecord(data, 0, data.length);
			}
			else {
				store.setRecord(1, data, 0, data.length);
			}
		}
		catch (writeError) {
			failed = true;
		}
		try {
			out.close();
			bytes.close();
			if (store !== null) {
				store.closeRecordStore();
			}
		}
		catch (closeError) {
			failed = true;
		}
		return !failed;
	}
	optionStringId(optionId: number): number {
		if (optionId === 79) {
			return this.soundValue.enabled ? 87 : 88;
		}
		else {
			if (optionId === 80) {
				return this.vibrationEnabled ? 87 : 88;
			}
			else {
				if (optionId === 81) {
					return this.tutorialsEnabled ? 87 : 88;
				}
				else {
					if (optionId === 82) {
						return this.autonomousEnabled ? 87 : 88;
					}
					else {
						if (optionId === 83) {
							return this.savePromptsEnabled ? 87 : 88;
						}
						else {
							return -1;
						}
					}
				}
			}
		}
	}
	profileNameLength(): number {
		return this.profileNames[this.profileSlot].length;
	}
	clearProfileName(): void {
		let name: SdkString = null!;
		(name = this.profileNames[this.profileSlot]).setLength(0);
	}
	setProfileNameChar(index: number, character: number): void {
		this.profileNames[this.profileSlot].setCharAt(index, character);
	}
	insertProfileNameChar(index: number, character: number): void {
		let name: SdkString = null!;
		let length: number = (name = this.profileNames[this.profileSlot]).length;
		name.setLength(((name.length + 1) | 0));
		while (index < length) {
			let moved: number = name.charAt(((length - 1) | 0));
			name.setCharAt(length, moved);
			length--;
		}
		name.setCharAt(index, character);
	}
	deleteProfileNameChar(index: number): void {
		let name: SdkString = null!;
		let lastIndex: number = (((name = this.profileNames[this.profileSlot]).length - 1) | 0);
		while (index !== lastIndex) {
			let moved: number = name.charAt(((index + 1) | 0));
			name.setCharAt(index, moved);
			index++;
		}
		name.setCharAt(index, 32);
		name.setLength(lastIndex);
	}
	selectProfile(slot: number): void {
		this.profileSlot = slot;
		if (this.isProfileUsed()) {
			this.loadGameData();
		}
	}
	profileName(): SdkString {
		return this.profileNames[this.profileSlot];
	}
	profileNameAt(slot: number): SdkString {
		return this.profileNames[slot];
	}
	isProfileUsed(): boolean {
		return this.profileUsed[this.profileSlot];
	}
	isProfileUsedAt(slot: number): boolean {
		return this.profileUsed[slot];
	}
	resetProfiles(): void {
		for (let slot: number = 0; slot < 2; slot++) {
			this.clearProfile(slot);
		}
	}
	clearProfile(slot: number): void {
		this.profileUsed[slot] = false;
	}
	commitProfile(): void {
		this.profileUsed[this.profileSlot] = true;
		this.progressFlags = 0;
		this.saveSettings();
		this.neighborhood.completeAllTips();
		this.saveGameData();
	}
	reloadGameData(): void {
		this.loadGameData();
		this.houseIndex = 0;
	}
	hasProgressFlag(flag: number): boolean {
		return (1 << flag & this.progressFlags) !== 0;
	}
	setProgressFlag(flag: number): void {
		this.progressFlags = (this.progressFlags | (1 << flag));
	}
	markGameStarted(): void {
		this.gameStarted = true;
		this.saveSettings();
	}
	resetProgressFlags(): void {
		this.gameStarted = false;
		this.flagsLow = 0n;
		this.flagsHigh = 0n;
		this.visitedFlagsLow = 0n;
		this.visitedFlagsHigh = 0n;
	}
	clearNewFlags(): void {
		this.newFlagsLow = 0n;
		this.newFlagsHigh = 0n;
		this.newVisitedFlagsLow = 0n;
		this.newVisitedFlagsHigh = 0n;
	}
	/** Progress bitset: Java stores 64 flags in a long, so this uses BigInt. */
	hasFlag(flag: number): boolean {
		const bit = BigInt.asUintN(64, 1n << BigInt(flag < 64 ? flag : flag - 64));
		const bits = flag < 64 ? (this.flagsLow | this.newFlagsLow) : (this.flagsHigh | this.newFlagsHigh);
		return (bits & bit) !== 0n;
	}
	addFlag(flag: number): void {
		const bit = BigInt.asUintN(64, 1n << BigInt(flag < 64 ? flag : flag - 64));
		if (flag < 64) {
			this.newFlagsLow |= bit;
		} else {
			this.newFlagsHigh |= bit;
		}
	}
	flagCount(): number {
		return countBits(this.flagsLow | this.newFlagsLow) + countBits(this.flagsHigh | this.newFlagsHigh);
	}
	hasVisitedFlag(flag: number): boolean {
		const bit = BigInt.asUintN(64, 1n << BigInt(flag < 64 ? flag : flag - 64));
		const bits = flag < 64
			? (this.visitedFlagsLow | this.newVisitedFlagsLow)
			: (this.visitedFlagsHigh | this.newVisitedFlagsHigh);
		return (bits & bit) !== 0n;
	}
	addVisitedFlag(flag: number): void {
		this.addFlag(flag);
		const bit = BigInt.asUintN(64, 1n << BigInt(flag < 64 ? flag : flag - 64));
		if (flag < 64) {
			this.newVisitedFlagsLow |= bit;
		} else {
			this.newVisitedFlagsHigh |= bit;
		}
	}
	visitedFlagCount(): number {
		return countBits(this.visitedFlagsLow | this.newVisitedFlagsLow)
			+ countBits(this.visitedFlagsHigh | this.newVisitedFlagsHigh);
	}
	updateLoadingTip(deltaMs: number): void {
		if (this.loadingTipId === -1) {
			this.loadingTipId = this.randomRange(920, 955);
			this.loadingTipX = this.getWidth() << 16;
			this.loadingTipWidth = this.stringWidthById(this.loadingTipId, 0);
		}
		else {
			this.loadingTipX = ((this.loadingTipX - (Math.imul(deltaMs, 4915))) | 0);
			if (this.loadingTipX < (((this.loadingTipWidth << 16) * -1) | 0)) {
				this.loadingTipId = -1;
			}
		}
	}
	drawLoadingScreen(gfx: Graphics): void {
		SpriteRenderer.setColor(gfx, 77);
		gfx.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.screen.drawTextBlock(gfx, 0, 0, 0, this.getWidth(), this.getHeight());
		if (this.loadingTipId !== -1) {
			let yPos: number = ((this.getHeight() - 40) | 0);
			this.drawStringById(this.loadingTipId, 0, this.loadingTipX >> 16, yPos, 20);
		}
	}
	buildMenus(): void {
		this.menuRowSpacing = (((((((((this.getHeight() - 15) | 0) - 22) | 0)) / 15) | 0)) << 16 >> 16);
		let items: Int16Array = null!;
		GameCanvas.initListWith(items = new Int16Array(12), 60, this.menuRowSpacing);
		GameCanvas.addToList(items, 79);
		GameCanvas.addToList(items, 80);
		GameCanvas.addToList(items, 81);
		GameCanvas.addToList(items, 82);
		GameCanvas.addToList(items, 83);
		this.compactOptionsMenu = new Int16Array(((items[0] + 5) | 0));
		GameCanvas.copyList(this.compactOptionsMenu, items);
		GameCanvas.addToList(items, 3);
		GameCanvas.addToList(items, 85);
		this.optionsMenu = items;
		GameCanvas.initListWith(items = new Int16Array(12), 61, this.menuRowSpacing);
		GameCanvas.addToList(items, 62);
		GameCanvas.addToList(items, 90);
		GameCanvas.addToList(items, 94);
		GameCanvas.addToList(items, 96);
		GameCanvas.addToList(items, 98);
		GameCanvas.addToList(items, 100);
		GameCanvas.addToList(items, 105);
		this.helpMenu = items;
		this.menuMode = -1;
	}
	setMenuMode(mode: number): void {
		let items: Int16Array = null;
		let argMode: number = -1;
		let leftAction: number = 4;
		let rightAction: number = 16;
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
				leftAction = 32768;
				rightAction = 16384;
		}
		if (this.menuMode === -1 && items !== null) {
			items[2] = 0;
		}
		this.currentMenu = items;
		this.returnMenuMode = argMode;
		this.menuMode = mode;
		this.setSoftKeys(leftAction, rightAction);
	}
	drawMenu(gfx: Graphics): void {
		let screen: BaseScreen = this.screen;
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
	menuKeyPressed(keyCode: number, action: number): void {
		if (this.currentMenu !== null) {
			if (action !== 16 && (keyCode & 4128) === 0) {
				this.navigateLeftRight(this.currentMenu, keyCode);
			}
			else {
				this.releaseSoftKeys();
				this.clearAction(4128);
				let selectedValue: number = ((5 + this.currentMenu[2]) | 0);
				this.menuAction(this.currentMenu[selectedValue]);
			}
		}
		if (this.menuMode === 4) {
			this.screen.scrollMenu(keyCode);
		}
		else {
			if (this.menuMode === 2) {
				if (action === 256) {
					this.releaseSoftKeys();
					this.resetProfiles();
					this.resetProgressFlags();
					this.saveSettings();
					this.resetGameData();
					this.saveGameData();
					this.setMenuMode(this.returnMenuMode);
					this.titleScreen().buildMainMenu(60);
				}
				else {
					if (action === 512) {
						this.releaseSoftKeys();
						this.setMenuMode(this.returnMenuMode);
					}
				}
			}
		}
		if (action === 4) {
			this.releaseSoftKeys();
			this.setMenuMode(this.returnMenuMode);
		}
	}
	menuAction(actionId: number): void {
		let screen: BaseScreen = this.screen;
		switch (actionId) {
			case 3:
				this.setMenuMode(5);
				return;
			case 62:
				screen.showPanelTwo(89, 62);
				this.setMenuMode(4);
				return;
			case 79:
				this.soundValue.setEnabled(!this.soundValue.enabled);
				if (this.soundValue.enabled) {
					if (this.titleScreen() !== null) {
						this.setSoundLoop(this.soundLoopId);
						return;
					}
					this.soundValue.pendingSound = 3;
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
	initWorld(): void {
		this.loadTileTable();
		this.lot = new Lot(this);
		this.neighborhood = new Neighborhood(this);
		this.houseIndex = 0;
		this.houseUpgrade = -1;
		this.walkTargetFrame = -1;
	}
	setHouse(houseIndex: number): void {
		this.houseIndex = houseIndex;
		this.houseUpgrade = -1;
	}
	enterHouseWith(sim: Sim): void {
		let currentSim: Sim = this.gameScreen().activePlayer;
		this.simPosX = currentSim.posX;
		this.simPosY = currentSim.posY;
		if (sim !== null) {
			this.walkTargetX = sim.posX;
			this.walkTargetY = sim.posY;
			this.walkTargetFrame = sim.variant;
		}
		else {
			this.walkTargetFrame = -1;
		}
		this.houseIndex = -1;
	}
	static readShort(input: DataInputStream): number {
		return input.readShort();
	}
	loadTileTable(): void {
		let loader: ResourceLoader = this.resources;
		try {
			let input: DataInputStream = null!;
			let groupCount: number = 0;
			let groups: Int16Array[] = new Array(groupCount = (input = new DataInputStream(loader.openStream(63))).readByte()).fill(null);
			for (let groupIndex: number = 0; groupIndex < groupCount; groupIndex++) {
				let groupSize: number = 0;
				let entries: Int16Array = new Int16Array((groupSize = input.readByte()) << 1);
				let entryIndex: number = 0;
				for (let index: number = 0; index < groupSize; index++) {
					entries[entryIndex] = input.readByte();
					entries[((entryIndex + 1) | 0)] = GameCanvas.readShort(input);
					entryIndex = ((entryIndex + 2) | 0);
				}
				groups[groupIndex] = entries;
			}
			let maskCount: number = 0;
			let masks: Int32Array = new Int32Array((maskCount = input.readByte()) << 1);
			let maskIndex: number = 0;
			for (let index2: number = 0; index2 < maskCount; index2++) {
				masks[maskIndex] = GameCanvas.readShort(input);
				masks[((maskIndex + 1) | 0)] = input.readInt();
				maskIndex = ((maskIndex + 2) | 0);
			}
			this.tileMasks = masks;
			input.close();
			this.tileGroups = groups;
		}
		catch (ignored) {
			if (ignored instanceof IOException) {
			}
			else {
				throw ignored;
			}
		}
	}
	tileGroup(index: number): Int16Array {
		return this.tileGroups[index];
	}
	static isRunning(canvas: GameCanvas): boolean {
		return canvas.running;
	}
	static repaintTick(canvas: GameCanvas): void {
		canvas.tick();
	}
	static isLandscape(canvas: GameCanvas): boolean {
		return canvas.suspended;
	}
	static isUpdating(canvas: GameCanvas): boolean {
		return canvas.updating;
	}
	static setUpdating(canvas: GameCanvas, updating: boolean): boolean {
		return canvas.updating = updating;
	}
	static sound(canvas: GameCanvas): SdkSound {
		return canvas.soundValue;
	}
	constructor(app: MonkeyApp) {
		super();
		SdkText.setMidlet((app as unknown as MIDlet));
		if (this.getWidth() > this.getHeight()) {
			GameCanvas.lastLandscape = 1;
			GameCanvas.currentLandscape = 1;
		}
		else {
			GameCanvas.lastLandscape = 0;
			GameCanvas.currentLandscape = 0;
		}
		this.app = app;
		this.running = false;
		this.keyQueue = new Int32Array(8);
		this.keyQueueSize = 0;
		this.gcPending = false;
		this.resources = new ResourceLoader();
		this.soundValue = new SdkSound();
		this.soundValue.setEnabled(false);
		this.soundValue.currentLoop = 1;
		this.soundValue.resources = this.resources;
		this.soundLoopId = -1;
		this.preloadSounds();
		this.random = new Random();
		SpriteRenderer.loadSpriteRects(this.resources);
		SpriteRenderer.loadFrameData(this.resources);
		SpriteRenderer.loadPalette(this.resources);
		this.leftSoftKeyAnimator = new Animator();
		this.rightSoftKeyAnimator = new Animator();
		this.initWorld();
		this.promoImages = new PromoImages(this.getWidth(), this.getHeight());
		let blankName: SdkString = null;
		for (let slot: number = 0; slot < 2; slot++) {
			(blankName = new SdkString(8, 0)).setLength(8);
			for (let charIndex: number = 0; charIndex !== 8; charIndex++) {
				blankName.setCharAt(charIndex, 32);
			}
			this.profileNames[slot] = blankName;
		}
		this.loadSettings();
		this.initTextSlots();
		this.loadFonts();
		if (this.tempStrings === null) {
			this.tempStrings = new Array(10).fill(null);
			for (let stringIndex: number = 0; stringIndex < 10; stringIndex++) {
				this.tempStrings[stringIndex] = new SdkString();
			}
		}
		this.stringPool = new Array(25).fill(null);
		for (let poolIndex: number = 0; poolIndex < 25; poolIndex++) {
			this.stringPool[poolIndex] = new SdkString(100, 0);
		}
		this.loadTilesBlocking(1);
		this.resetGameData();
		this.loadGameData();
	}
}
