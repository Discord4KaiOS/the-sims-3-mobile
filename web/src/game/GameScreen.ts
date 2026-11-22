// Generated from GameScreen.java by tools/web/JavaToTs.java; keep this file in sync.
import { Thread, Stack, InterruptedException, Graphics, StringBuffer, DataInputStream, IOException } from '../runtime';
import { BaseScreen } from './BaseScreen';
import { Animator } from './Animator';
import { Entity } from './Entity';
import { Sim } from './Sim';
import { Neighborhood } from './Neighborhood';
import { Lot } from './Lot';
import { SdkString } from './SdkString';
import { GameCanvas } from './GameCanvas';
import { ResourceLoader } from './ResourceLoader';
import { SpriteRenderer } from './SpriteRenderer';
import { Tile } from './Tile';
import { SdkSound } from './SdkSound';
import { FixedMath } from './FixedMath';

export class GameScreen extends BaseScreen {
	frameBounds: Int32Array = new Int32Array(10);
	static screenState: number = 0;
	keyRepeatTime: number = 0;
	pendingTick: number = 0;
	dialogX: number = 0;
	dialogY: number = 0;
	startOption: number = 0;
	saveSlotMode: number = 0;
	inputIdle: boolean = false;
	bottomBarHeight: number = 0;
	viewX: number = 0;
	viewY: number = 0;
	viewWidth: number = 0;
	viewHeight: number = 0;
	visibleRows: number = 0;
	loadStep: number = 0;
	loaderThread: Thread = null!;
	loaderScheduled: boolean = false;
	threadState: number = 0;
	portraitSlot: number = 0;
	portraitSimId: number = 0;
	recolorSource: Int32Array = new Int32Array(32);
	recolorTarget: Int32Array = new Int32Array(32);
	pendingPortraits: number = 0;
	slotSimIds: Int8Array = null!;
	musicStarted: boolean = false;
	dialogActive: boolean = false;
	lastTickDuration: number = 0;
	statusAnimator: Animator = null!;
	needUpAnimator: Animator = null!;
	needDownAnimator: Animator = null!;
	displayedMoney: number = 0;
	interactionSource: number = 0;
	fadePhase: number = 0;
	pendingAction: number = 0;
	fadeAmount: number = 0;
	static updateBlocked: boolean = false;
	notifyId: number = 0;
	notifyId2: number = 0;
	notificationTimer: number = 0;
	notificationScroll: number = 0;
	notificationWidth: number = 0;
	followTarget: Entity = null!;
	camX: number = 0;
	camY: number = 0;
	camTargetX: number = 0;
	camTargetY: number = 0;
	atTarget: boolean = false;
	stillTime: number = 0;
	selectionAnimator: Animator = null!;
	followTime: number = 0;
	followFlag: number = 0;
	movingToTarget: boolean = false;
	leftPressed: boolean = false;
	rightPressed: boolean = false;
	downPressed: boolean = false;
	upPressed: boolean = false;
	clampTargetX: number = 0;
	clampTargetY: number = 0;
	camVelX: number = 0;
	camVelY: number = 0;
	prevCamVelX: number = 0;
	prevCamVelY: number = 0;
	cameraClamped: boolean = false;
	cameraTarget: Entity = null!;
	recenterPending: boolean = false;
	entityCache: Entity[] = null!;
	simCache: Sim[] = null!;
	activePlayer: Sim = null!;
	entityStack: Stack = null!;
	entitiesDirty: boolean = false;
	simsDirty: boolean = false;
	menuOpen: boolean = false;
	menuSubPage: boolean = false;
	menuParent: number = 0;
	menuLabels: Int16Array = null!;
	menuArgs: Int16Array = null!;
	menuExtra: Int16Array = null!;
	menuTarget: Entity = null!;
	mainMenuOpen: boolean = false;
	menuOptionIds: Int16Array = null!;
	mainMenuPage: number = 0;
	actionMenuOpen: boolean = false;
	tabSwitchRequested: boolean = false;
	actionOptionIds: Int16Array = null!;
	objectMenuOpen: boolean = false;
	objectOptionIds: Int16Array = null!;
	menuPage: number = 0;
	selectedType: number = 0;
	pendingPlacement: Entity = null!;
	optionsTab: number = 0;
	shownSimId: number = 0;
	rosterIndex: number = 0;
	nameColumnWidth: number = 0;
	listTextWidth: number = 0;
	listValueWidth: number = 0;
	objectIndex: number = 0;
	objectRow: number = 0;
	friendSimId: number = 0;
	friendLevel: number = 0;
	requestAmount: number = 0;
	choiceMade: boolean = false;
	pendingEvent: boolean = false;
	pendingGoHome: boolean = false;
	slotAnimators: Animator[] = null!;
	pendingDialogId: number = 0;
	dialogArg1: number = 0;
	dialogArg2: number = 0;
	dialogText: number = 0;
	aiTimer: number = 0;
	soundTimer: number = 0;
	soundChoices: Int16Array = null!;
	soundInterval: number = 0;
	idleTimer: number = 0;
	static DEFAULT_SOUND_CHOICES: Int16Array = Int16Array.from([14, 15, 12, 13]);
	static ALT_SOUND_CHOICES: Int16Array = Int16Array.from([14, 15]);
	queuedCount: number = 0;
	queuedInteractions: Int32Array = null!;
	queuedTargets: Entity[] = null!;
	queuedArgs: Int32Array = null!;
	queuedArgs2: Int32Array = null!;
	neighborhood: Neighborhood = null!;
	currentLot: Lot = null!;
	relationPercent: number = 0;
	timeAdvanceSeconds: number = 0;
	cursorAnimator: Animator = null!;
	dialogSubject: number = 0;
	dialogSubjectIndex: number = 0;
	dialogPhase: number = 0;
	dialogOption: number = 0;
	dialogRequestPending: boolean = false;
	miniGamePhase: number = 0;
	dialogKind: number = 0;
	dialogIconTop: number = 0;
	dialogIconBottom: number = 0;
	dialogSubjectType: number = 0;
	dialogClock: number = 0;
	savedTarget: Entity = null!;
	miniStep: number = 0;
	miniTarget: Entity = null!;
	miniKind: number = 0;
	miniType: number = 0;
	panelTop: number = 0;
	panelBottom: number = 0;
	effectAnimators: Animator[] = null!;
	effectPositions: Int32Array[] = null!;
	progressValue: number = 0;
	progressMax: number = 0;
	candleColumns: number = 0;
	plotIndex: number = 0;
	candleDecrement: number = 0;
	candleRiseSpeed: number = 0;
	candleAnimValues: Int32Array = new Int32Array(4);
	candleTargets: Int32Array = new Int32Array(4);
	candleFrames: Int8Array = new Int8Array(4);
	toastAnimSlot: number = 0;
	sliderPosition: number = 0;
	targetPosition: number = 0;
	targetStart: number = 0;
	targetGoal: number = 0;
	timingMeter: number = 0;
	candleFlameAnimator: Animator = null!;
	candleGlowAnimator: Animator = null!;
	sliderStep: number = 0;
	sliderRange: number = 0;
	miniLevel: number = 0;
	seedCursorAnimator: Animator = null!;
	static DIR_X_TABLE: Int8Array = Int8Array.from([2, 2, 2, 0, 0, 2, 2, 1]);
	static DIR_Y_TABLE: Int8Array = Int8Array.from([0, 1, -1, -2, 2, -1, 1, 0]);
	seedRows: Int8Array[] = null!;
	seedTypes: Int8Array = null!;
	seedCells: Int16Array[] = null!;
	cursorPosition: number = 0;
	plotCount: number = 0;
	seedCount: number = 0;
	seedTimer: number = 0;
	vehicleAnimator: Animator = null!;
	gridX: number = 0;
	gridY: number = 0;
	hoverCell: number = 0;
	wobbleAmount: number = 0;
	wobbleSpeed: number = 0;
	phaseX: number = 0;
	phaseY: number = 0;
	offsetX: number = 0;
	offsetY: number = 0;
	plantPhase: number = 0;
	targetObjectType: number = 0;
	interactionAmount: number = 0;
	cheatLabels: Int16Array = null!;
	cheatText: SdkString[] = null!;
	isNoSaveMode(): boolean {
		return this.saveSlotMode === 0;
	}
	isNewNeighborhood(): boolean {
		return this.saveSlotMode === 2;
	}
	isSavedNeighborhood(): boolean {
		return this.saveSlotMode === 1;
	}
	/**
	 * The Java build runs this on a worker thread that waits for key presses; the
	 * port pumps one step per press from the event loop instead.
	 */
	startLoading(keyCode: number): void {
		this.canvas.updateLoadingTip(keyCode);
		this.threadState = 0;
		if (!this.loaderScheduled) {
			this.loaderScheduled = true;
			setTimeout((): void => {
				this.loaderScheduled = false;
				if (this.threadState === 0) {
					this.threadState = 1;
					try {
						this.runLoadingSteps();
					}
					catch (failure) {
					}
				}
			}, 0);
		}
	}
	runLoadingSteps(): void {
		let canvas: GameCanvas = this.canvas;
		switch (this.loadStep) {
			case 0:
				this.loadStep++;
				return;
			case 1:
				let lot: Lot = this.currentLot;
				this.currentLot.setBounds(this.viewX, this.viewY, 320, 320);
				this.entitiesDirty = true;
				this.simsDirty = true;
				let usePlayerPos: boolean = true;
				if (this.saveSlotMode === 2) {
					this.setupNewGame();
				}
				else {
					if (this.saveSlotMode === 1) {
						this.restoreSavedSims();
						if (this.getSims().length > 1) {
							usePlayerPos = false;
						}
					}
					else {
						this.resumeSavedGame();
					}
				}
				if (usePlayerPos) {
					let player: Sim = this.activePlayer;
					let playerX: number = this.activePlayer.posX;
					let playerY: number = player.posY;
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
				let loadMask: number = 8;
				switch (this.saveSlotMode) {
					case 0:
					default:
						loadMask = 24;
						let hoverEntity: Entity = null!;
						if ((hoverEntity = this.findEntityByObjectType(10)) !== null) {
							if (hoverEntity.type === 46) {
								loadMask = 24 | 134217728;
							}
							if (hoverEntity.type === 48) {
								loadMask = (loadMask | 268435456);
							}
							if (hoverEntity.type === 50) {
								loadMask = (loadMask | 536870912);
							}
							if (hoverEntity.type === 52) {
								loadMask = (loadMask | 1073741824);
							}
						}
						break;
					case 1:
						if (canvas.houseUpgrade === 39) {
							loadMask = 8 | 131072;
						}
						else {
							if (canvas.houseUpgrade === 41) {
								loadMask = 8 | 262144;
							}
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
				let resources: ResourceLoader = canvas.resources;
				let loadedLot: Lot = this.currentLot;
				if (this.isNewNeighborhood()) {
					let sourceColors: Int32Array = loadedLot.paletteAt(0);
					let recolorFlag: boolean = false;
					let targetColors: Int32Array = loadedLot.paletteAt(1);
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
				let soundChoices: Int16Array = null;
				let soundInterval: number = 0;
				if (this.isNewNeighborhood()) {
					soundChoices = GameScreen.DEFAULT_SOUND_CHOICES;
					soundInterval = 3000;
				}
				else {
					if (canvas.houseUpgrade === 39) {
						soundChoices = GameScreen.ALT_SOUND_CHOICES;
						soundInterval = 8000;
					}
					else {
						if (canvas.houseUpgrade !== -1) {
							soundChoices = GameScreen.DEFAULT_SOUND_CHOICES;
							soundInterval = 10000;
						}
					}
				}
				this.soundChoices = soundChoices;
				this.soundInterval = soundInterval;
				this.loadStep++;
				return;
			case 5:
				this.layoutViewport();
				this.onTick(this.startOption);
				if (this.isNewNeighborhood()) {
					this.showNeighborhoodIntro((1 << 24 >> 24));
				}
				else {
					this.showNeighborhoodIntro((0 << 24 >> 24));
				}
				this.threadState = 2;
				canvas.fadeSpeed = 197379;
				canvas.fadeFromBlack();
		}
	}
	resetPortraitQueue(): void {
		this.portraitSlot = 0;
		this.portraitSimId = 0;
		this.pendingPortraits = 0;
		if (!this.isNewNeighborhood()) {
			if (this.slotSimIds === null) {
				this.slotSimIds = new Int8Array(4);
			}
			GameCanvas.fillBytes(this.slotSimIds, -1);
		}
	}
	queuePortraitLoad(sim: Sim): void {
		if (!this.isNewNeighborhood()) {
			this.pendingPortraits++;
			let slot: number = this.findFreePortraitSlot();
			this.slotSimIds[slot] = (sim.variant << 24 >> 24);
		}
	}
	pickNextPortrait(): void {
		for (let slot: number = 0; slot < 4; slot++) {
			let simId: number = 0;
			if ((simId = this.slotSimIds[slot]) !== -1 && this.findSimByIndex(simId).recolorIndex === -1) {
				this.portraitSlot = slot;
				this.portraitSimId = simId;
				this.currentLot.buildPaletteFor(simId, this.recolorSource, this.recolorTarget);
				this.canvas.resetTileLoad();
				return;
			}
		}
	}
	findFreePortraitSlot(): number {
		return GameCanvas.indexOfByte(-1, this.slotSimIds);
	}
	loadQueuedPortrait(): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		let slot: number = this.portraitSlot;
		let simId: number = this.portraitSimId;
		let palette: number = neighborhood.simAttribute(simId, 0) === 0 ? 512 : 256;
		GameCanvas.gcYield();
		if (canvas.loadNextTileGroup(palette, this.recolorSource, this.recolorTarget, slot)) {
			let tileId: number = this.simPortraitTile(simId);
			SpriteRenderer.loadTileRecolored(canvas.resources, tileId, this.recolorSource, this.recolorTarget, slot);
			this.findSimByIndex(simId).recolorIndex = slot;
			this.portraitSimId = -1;
			this.portraitSlot = ((slot + 1) | 0);
			this.pendingPortraits--;
			if (simId === 0) {
				this.loadPlayerPortrait();
			}
			if (this.pendingPortraits > 0) {
				this.pickNextPortrait();
			}
		}
	}
	loadPlayerPortrait(): void {
		this.currentLot.buildPaletteFor(0, this.recolorSource, this.recolorTarget);
		let palette: number = this.neighborhood.simAttribute(0, 0) === 0 ? 4096 : 8192;
		this.canvas.loadTiles(palette, this.recolorSource, this.recolorTarget, 0);
	}
	simPortraitTile(simId: number): number {
		let neighborhood: Neighborhood = this.neighborhood;
		let variant: number = this.neighborhood.simAttribute(simId, 2);
		if (neighborhood.simAttribute(simId, 0) === 0) {
			if (variant === 0) {
				return 78;
			}
			else {
				if (variant === 1) {
					return 79;
				}
				else {
					return variant === 2 ? 80 : 81;
				}
			}
		}
		else {
			if (variant === 0) {
				return 70;
			}
			else {
				if (variant === 1) {
					return 71;
				}
				else {
					return variant === 2 ? 72 : 73;
				}
			}
		}
	}
	layoutViewport(): void {
		let canvas: GameCanvas = this.canvas;
		let lot: Lot = this.currentLot;
		this.bottomBarHeight = ((SpriteRenderer.frameHeight(587, 0) + SpriteRenderer.frameHeight(586, 0)) | 0);
		this.viewX = 0;
		this.viewY = 0;
		this.viewWidth = ((canvas.getWidth() - this.viewX) | 0);
		this.viewHeight = ((((((((canvas.getHeight() - 15) | 0) - this.viewY) | 0) - this.bottomBarHeight) | 0) + 5) | 0);
		let tile: Tile = this.currentLot.groundTile;
		this.currentLot.groundTile.setBackingEnabled(true);
		tile.invalidateBacking();
		lot.setBounds(this.viewX, this.viewY, this.viewWidth, this.viewHeight);
		this.entitiesDirty = true;
		this.simsDirty = true;
		let player: Sim = this.activePlayer;
		let playerX: number = this.activePlayer.posX;
		let playerY: number = player.posY;
		this.camTargetX = lot.toScreenFixedX(playerX, playerY);
		this.camTargetY = lot.toScreenFixedY(playerX, playerY);
		this.camX = this.camTargetX;
		this.camY = this.camTargetY;
		this.clearCameraTarget();
		this.snapCameraToTarget();
		tile.setBackingEnabled(true);
		tile.invalidateBacking();
	}
	setLanguage(startMode: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		this.canvas.neighborhood.resetAll();
		if (this.leftArrowAnim === null) {
			this.leftArrowAnim = new Animator();
			this.rightArrowAnim = new Animator();
		}
		GameScreen.screenState = 0;
		this.keyRepeatTime = 0;
		this.pendingTick = 0;
		this.dialogX = 0;
		this.dialogY = -1;
		this.inputIdle = false;
		this.bottomBarHeight = ((SpriteRenderer.frameHeight(587, 0) + SpriteRenderer.frameHeight(586, 0)) | 0);
		this.viewX = 0;
		this.viewY = 0;
		this.viewWidth = ((canvas.getWidth() - this.viewX) | 0);
		this.viewHeight = ((((((((canvas.getHeight() - 15) | 0) - this.viewY) | 0) - this.bottomBarHeight) | 0) + 5) | 0);
		this.visibleRows = ((((this.viewHeight / 30) | 0)) << 16 >> 16);
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
		if (this.entityStack === null) {
			this.entityStack = new Stack();
		}
		this.entityStack.removeAllElements();
		this.entitiesDirty = true;
		this.simsDirty = true;
		this.menuOpen = false;
		this.menuSubPage = false;
		this.menuParent = 0;
		if (this.menuLabels === null) {
			this.menuLabels = new Int16Array(55);
			this.menuArgs = new Int16Array(55);
			this.menuExtra = new Int16Array(55);
		}
		this.menuTarget = null;
		this.mainMenuOpen = false;
		if (this.menuOptionIds === null) {
			let soundChoices: Int16Array = null!;
			GameCanvas.initListWith(soundChoices = new Int16Array(15), 66, canvas.menuRowSpacing);
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
		if (this.actionOptionIds === null) {
			this.actionOptionIds = new Int16Array(20);
		}
		this.objectMenuOpen = false;
		if (this.objectOptionIds === null) {
			this.objectOptionIds = new Int16Array(25);
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
		if (this.candleAnimValues === null) {
			this.candleAnimValues = new Int32Array(4);
			this.candleTargets = new Int32Array(4);
			this.candleFrames = new Int8Array(4);
		}
		this.seedRows = (null as unknown as Int8Array[]);
		this.seedTypes = null;
		this.seedCells = (null as unknown as Int16Array[]);
		this.plantPhase = 0;
		if (this.statusAnimator === null) {
			this.statusAnimator = new Animator();
			this.needUpAnimator = new Animator();
			this.needDownAnimator = new Animator();
			this.selectionAnimator = new Animator();
			this.slotAnimators = new Array(4).fill(null);
			for (let slot: number = 0; slot < 4; slot++) {
				this.slotAnimators[slot] = new Animator();
			}
			this.cursorAnimator = new Animator();
			this.effectAnimators = new Array(24).fill(null);
			this.effectPositions = Array.from({ length: 24 }, () => new Int32Array(3));
			for (let effect: number = 0; effect < 24; effect++) {
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
		if (startMode === 99) {
			this.saveSlotMode = 2;
		}
		else {
			if (startMode === 98) {
				this.saveSlotMode = 2;
				startMode = 5;
			}
			else {
				if (startMode === 100) {
					this.saveSlotMode = 1;
					startMode = 5;
				}
				else {
					if (startMode === 97) {
						this.timeAdvanceSeconds = 30;
						startMode = 5;
					}
					else {
						if (startMode === -1) {
							startMode = 5;
						}
					}
				}
			}
		}
		this.startOption = startMode;
		this.onTick(1);
	}
	stop(): void {
		if ((GameScreen.screenState === 5 || GameScreen.screenState === 16) && !this.mainMenuOpen && !this.dialogActive) {
			this.openMainMenu();
		}
	}
	pause(): void {
		this.currentLot.dispose();
		let configFlags: number = 2013688824;
		if (this.canvas.pendingScreenAction === 2) {
			configFlags = (configFlags & -9);
		}
		this.canvas.unloadTiles(configFlags, -1);
		SdkSound.muteAll();
	}
	render(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		switch (GameScreen.screenState) {
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
	drawSoftKeyHint(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		this.canvas.drawBottomBar(gfx);
		let hitRect: Int32Array = this.frameBounds;
		let centerX: number = canvas.getWidth() >> 1;
		let bottomY: number = canvas.getHeight();
		SpriteRenderer.drawFrame(gfx, 635, 0, centerX, bottomY);
		SpriteRenderer.hitTest(hitRect, 635, 0, 0);
		let textX: number = ((centerX + hitRect[0]) | 0);
		let textY: number = ((bottomY + hitRect[1]) | 0);
		let label: SdkString = canvas.beginString();
		canvas.formatNumber(this.displayedMoney);
		canvas.drawString(label, 2, textX, textY, 24);
	}
	drawGameLayer(gfx: Graphics): void {
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
		}
		else {
			if (!this.inputIdle && this.menuOpen) {
				this.drawSimInfoPanel(gfx);
				this.drawBottomBar(gfx);
				this.drawRelationshipBar(gfx);
				this.drawInteractionPrompt(gfx);
			}
			else {
				if (this.dialogActive) {
					this.drawSimInfoPanel(gfx);
					this.drawBottomBar(gfx);
					this.drawRelationshipBar(gfx);
					this.canvas.drawDialogOverlay(gfx);
					this.drawPageTwo(gfx);
				}
				else {
					if (GameScreen.screenState !== 16) {
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
		}
	}
	matchesInput(keyState: number, actionMask: number, pageMask: number, dataMask: number): boolean {
		if (!super.matchesInput(keyState, actionMask, pageMask, dataMask)) {
			return false;
		}
		else {
			let sound: SdkSound = this.canvas.soundValue;
			this.canvas.soundValue.setLoopCount(1);
			if ((pageMask & 4128) !== 0) {
				if (this.mainMenuOpen || this.menuOpen || this.actionMenuOpen) {
					sound.pendingSound = 3;
				}
			}
			else {
				if ((actionMask & 32027) !== 0) {
					sound.pendingSound = 3;
				}
				else {
					if ((actionMask & 33508) === 0 && (pageMask & 524288) === 0) {
						if ((pageMask & 24836) !== 0 && this.selectedIndex !== -1) {
							sound.pendingSound = 0;
						}
						else {
							if ((pageMask & 98384) !== 0) {
								sound.pendingSound = 1;
							}
						}
					}
					else {
						if (this.actionMenuOpen || !this.menuOpen || this.menuSubPage) {
							sound.pendingSound = 2;
						}
					}
				}
			}
			return true;
		}
	}
	update(keyState: number, actionMask: number): void {
		if (GameScreen.screenState !== 1 && (!this.hasFade() || this.fadePhase === 2)) {
			let canvas: GameCanvas = this.canvas;
			if (this.canvas.fadeFinished && this.pendingTick !== 0) {
				this.onTick(this.pendingTick);
				canvas.fadeFromBlack();
				this.pendingTick = 0;
			}
			else {
				if (!canvas.isFading()) {
					if (this.mainMenuOpen) {
						this.handleMainMenuPageInput(keyState, actionMask);
					}
					else {
						if (!this.inputIdle && this.menuOpen) {
							this.handleInteractionMenuInput(keyState, actionMask);
						}
						else {
							if (!this.inputIdle && this.actionMenuOpen) {
								this.handleActionMenuInput(keyState, actionMask);
							}
							else {
								if (this.dialogActive) {
									this.handleDialogKey(keyState, actionMask);
								}
								else {
									if (this.objectMenuOpen) {
										this.handleObjectMenuInput(keyState, actionMask);
									}
									else {
										switch (GameScreen.screenState) {
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
							}
						}
					}
				}
			}
		}
		else {
			GameScreen.updateBlocked = true;
		}
	}
	handleMainMenuInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 4128, 256)) {
			this.canvas.saveSettings();
			this.canvas.saveGameData();
			this.queueTick(2);
		}
		else {
			if (this.matchesInput(keyState, actionMask, 0, 512)) {
				this.queueTick(2);
			}
		}
	}
	handlePlayInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 0, 5)) {
			this.openMainMenu();
		}
		else {
			if (this.matchesInput(keyState, actionMask, 0, 1024)) {
				this.openActionMenu();
			}
			else {
				this.handleInputBits(keyState);
				if (this.matchesInput(keyState, actionMask, 4128, 16)) {
					this.useCursorTarget();
				}
				if (this.matchesInput(keyState, actionMask, 2048, 0)) {
					this.onTick(8);
					this.setOptionsTab(4);
					this.tabSwitchRequested = true;
				}
				else {
					if (this.matchesInput(keyState, actionMask, 1024, 0)) {
						this.onTick(8);
						this.setOptionsTab(3);
						this.tabSwitchRequested = true;
					}
				}
			}
		}
	}
	keyPressed(keyCode: number): void {
		this.keyRepeatTime = ((this.keyRepeatTime + keyCode) | 0);
		if (GameScreen.screenState === 1) {
			this.startLoading(keyCode);
		}
		else {
			this.advanceAnimators(keyCode);
			this.canvas.updateMarquees(keyCode);
			switch (GameScreen.screenState) {
				case 5:
					this.updateGame(keyCode);
					return;
				case 6:
					this.loadQueuedPortrait();
					if (this.pendingPortraits === 0) {
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
	onTick(tick: number): void {
		let canvas: GameCanvas = this.canvas;
		GameScreen.screenState = tick;
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
	queueTick(tick: number): void {
		this.pendingTick = tick;
		if (!this.canvas.isFadingFromWhite()) {
			this.canvas.fadeFromWhite();
		}
		this.clearMoveFlags();
	}
	openDialogAt(posX: number, posY: number): void {
		if (this.canvas.savePromptsEnabled) {
			this.dialogX = posX;
			this.dialogY = posY;
			this.onTick(3);
		}
		else {
			this.showDialogAt(posX, posY);
		}
	}
	showDialogAt(posX: number, posY: number): void {
		this.dialogX = posX;
		this.dialogY = posY;
		this.queueTick(2);
	}
	showDialogAtOnce(posX: number, posY: number): void {
		this.dialogX = posX;
		this.dialogY = posY;
		this.onTick(2);
	}
	updateInputMasks(): void {
		let actionMask: number = 0;
		let pageMask: number = 0;
		if (this.mainMenuOpen) {
			if (this.mainMenuPage !== 1 && this.mainMenuPage !== 2 && this.mainMenuPage !== 3 && this.mainMenuPage !== 4) {
				pageMask = 4;
				actionMask = 16;
			}
			else {
				pageMask = 512;
				actionMask = 256;
			}
		}
		else {
			if (!this.inputIdle && this.menuOpen) {
				pageMask = 4;
				actionMask = 16;
			}
			else {
				if (!this.inputIdle && this.actionMenuOpen) {
					pageMask = 4;
					actionMask = 16;
				}
				else {
					label96: {
						if (this.dialogActive) {
							actionMask = 8;
						}
						else {
							if (this.objectMenuOpen) {
								pageMask = 4;
								actionMask = 16;
							}
							else {
								label92: {
									switch (GameScreen.screenState) {
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
													}
													else {
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
													let objectIndex: number = 0;
													if ((objectIndex = this.getSelectedObjectIndex()) !== -1 && (this.currentLot.categoryMaskAt(objectIndex) & 2048) !== 0) {
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
											if (this.friendSimId !== -1) {
												pageMask = 512;
												actionMask = 256;
											}
											else {
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
											let plantPhase: number = this.plantPhase;
											if (this.plantPhase === 0) {
												pageMask = 4;
												if (this.interactionAmount > 0) {
													actionMask = 4096;
												}
												else {
													if (this.interactionAmount < 0) {
														actionMask = 8192;
													}
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
						}
					}
				}
			}
		}
		let canvas: GameCanvas = this.canvas;
		this.canvas.releaseSoftKeys();
		canvas.clearActions();
		canvas.setSoftKeys(pageMask, actionMask);
	}
	closeOpenMenus(): void {
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
	startMusic(): void {
		if (!this.musicStarted) {
			let sound: SdkSound = this.canvas.soundValue;
			if (this.canvas.soundValue.enabled) {
				sound.currentLoop = 0;
				sound.pendingSound = 11;
				sound.currentLoop = 1;
				this.musicStarted = true;
			}
		}
	}
	chooseDialogOption(posX: number, posY: number): void {
		this.openDialog(posX, posY, 1);
	}
	openStatsDialog(simIndex: number): void {
		this.canvas.formatStringWith(-11, simIndex, this.canvas.profileName());
		this.openDialogPage(0, -11, -1);
	}
	cancelPlayerAction(): void {
		this.activePlayer.queueAction(6, (null as unknown as Entity));
		this.syncFadeWithAction();
	}
	continueSavedGame(): void {
		this.canvas.setHouse(0);
		this.openDialogAt(2, 97);
	}
	startNewGame(): void {
		this.openDialogAt(2, 99);
	}
	startTutorialGame(): void {
		this.openDialogAt(2, 98);
	}
	selectEntity(entity: Entity): void {
		let type: number = entity.type;
		if (entity.type === 116) {
			this.canvas.enterHouseWith((null as unknown as Sim));
			this.canvas.houseUpgrade = 39;
			this.openDialogAt(2, 100);
		}
		else {
			if (type === 1) {
				this.canvas.enterHouseWith((entity as unknown as Sim));
				this.canvas.houseUpgrade = 41;
				this.openDialogAt(2, 100);
			}
			else {
				let houseIndex: number = this.currentLot.indexOfLotId(type);
				this.canvas.setHouse(houseIndex);
				this.openDialogAt(2, -1);
			}
		}
	}
	showNeighborhoodIntro(mode: number): void {
		let canvas: GameCanvas = this.canvas;
		if (!this.canvas.hasProgressFlag(mode)) {
			canvas.setProgressFlag(mode);
			if (canvas.tutorialsEnabled) {
				let textId: number = 0;
				let imageId: number = 0;
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
	handleDialogKey(keyState: number, actionMask: number): void {
		this.scrollMenu(keyState);
		if (this.matchesInput(keyState, actionMask, 4128, 8)) {
			this.closeDialog();
		}
	}
	closeDialog(): void {
		this.dialogActive = false;
		this.updateInputMasks();
	}
	isUiBlocked(): boolean {
		return this.mainMenuOpen || this.actionMenuOpen || this.dialogActive || GameScreen.screenState !== 5;
	}
	updateGame(delta: number): void {
		if (!this.isUiBlocked()) {
			let neighborhood: Neighborhood = this.neighborhood;
			if (this.pendingPortraits > 0) {
				this.onTick(6);
			}
			else {
				if (this.hasFade()) {
					this.updateFade(delta);
				}
				else {
					GameScreen.updateBlocked = false;
				}
				if (this.dialogActive || neighborhood.isCelebrating() || !this.checkPendingEvents()) {
					this.advanceNotification(delta);
					this.lastTickDuration = delta;
					let scaledDelta: number = neighborhood.paused ? delta << 2 : delta;
					this.advanceAllEntities(scaledDelta);
					this.updatePlayerAnimator(delta);
					this.updateCameraPan(delta);
					this.advanceArrows(delta);
					if (!this.hasFade() && !this.canvas.isFading()) {
						this.updateClock(delta);
						if (this.saveSlotMode === 2) {
							this.updateNpcSpawning(delta);
						}
						else {
							if (this.saveSlotMode === 0) {
								this.updateTownieAi(delta);
							}
							else {
								this.updateRandomSimAi(delta);
							}
						}
						if (this.soundChoices !== null) {
							this.playRandomSound(delta, this.soundInterval, this.soundChoices);
						}
						this.promoteNextInteraction();
					}
				}
			}
		}
	}
	checkPendingEvents(): boolean {
		let neighborhood: Neighborhood = this.neighborhood;
		if (this.dialogRequestPending) {
			this.dialogRequestPending = false;
			this.onTick(18);
			this.setGoalPage(3);
			return true;
		}
		else {
			if (this.pendingDialogId !== -1) {
				this.onTick(15);
				return true;
			}
			else {
				if (neighborhood.unlockSecondMilestone()) {
					this.openDialogPage(8, -1, -1);
					return true;
				}
				else {
					if (this.pendingEvent) {
						this.pendingEvent = false;
						this.canvas.markGameStarted();
						this.chooseDialogOptionAt(786, 747);
						return true;
					}
					else {
						if (neighborhood.checkCareerPromotion()) {
							this.chooseDialogOptionAt(787, 788);
							return true;
						}
						else {
							let eventId: number = 0;
							if ((eventId = neighborhood.pendingPromotion()) !== -1) {
								this.chooseDialogOptionAt(eventId, 771);
								return true;
							}
							else {
								if (this.pendingGoHome) {
									this.pendingGoHome = false;
									if (neighborhood.pendingAchievementId !== -1) {
										this.goToSleep();
										return true;
									}
								}
								return false;
							}
						}
					}
				}
			}
		}
	}
	advanceAnimators(delta: number): void {
		this.statusAnimator.advance(delta);
		let money: number = this.neighborhood.money;
		this.displayedMoney = BaseScreen.approach(this.displayedMoney, money, delta, 9);
		if (!this.isUiBlocked()) {
			this.needDownAnimator.advance(delta);
			this.needUpAnimator.advance(delta);
			this.cursorAnimator.advance(delta);
		}
		if (GameScreen.screenState === 5 && !this.actionMenuOpen && !this.mainMenuOpen) {
			let neighborhood: Neighborhood = this.neighborhood;
			for (let slot: number = 0; slot < this.slotAnimators.length; slot++) {
				let moodId: number = neighborhood.achievementSlotValue(slot);
				let animator: Animator = null!;
				(animator = this.slotAnimators[slot]).advance(delta);
				if (!animator.isRunning() && (animator.animId === 590 || moodId !== -1)) {
					animator.play(589, 20);
				}
			}
		}
	}
	drawTopBar(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		let hitRect: Int32Array = this.frameBounds;
		let barX: number = ((this.viewX + this.viewWidth) | 0);
		let barY: number = this.viewY;
		if (GameCanvas.landscape) {
			barY = ((barY + 20) | 0);
		}
		if (GameScreen.screenState === 7) {
			let iconW: number = SpriteRenderer.frameWidth(596, 0);
			let iconH: number = SpriteRenderer.frameHeight(596, 0);
			let iconX: number = ((canvas.getWidth() - (((iconW + 9) | 0))) | 0);
			let iconY: number = ((((iconH + 3) | 0) - 1) | 0);
			this.drawIconBar(gfx, 3, iconX, iconY, 114);
		}
		else {
			let firstIconW: number = SpriteRenderer.frameWidth(4, 0);
			let normalIconW: number = SpriteRenderer.frameWidth(5, 0);
			let slotX: number = ((((this.viewX + 5) | 0) + (normalIconW >> 1)) | 0);
			let slotY: number = ((((barY + 3) | 0) + (normalIconW >> 1)) | 0);
			let cursorX: number = slotX;
			let slotCount: number = this.queuedCount;
			let slotIds: Int32Array = this.queuedInteractions;
			let slotEntities: Entity[] = this.queuedTargets;
			for (let index: number = 0; index < slotCount; index++) {
				let objectType: number = slotIds[index];
				let entity: Entity = slotEntities[index];
				let frame: number = 5;
				let slotWidth: number = normalIconW;
				if (index === 0) {
					frame = 4;
					slotWidth = firstIconW;
				}
				let iconFrame: number = 196;
				if (objectType === 106) {
					iconFrame = 3;
				}
				else {
					if (entity !== null) {
						if (entity.hasFlag(16384) && !this.isNewNeighborhood()) {
							iconFrame = 6;
						}
						else {
							iconFrame = this.currentLot.spriteVariantAt(entity.type);
						}
					}
				}
				SpriteRenderer.drawFrame(gfx, frame, 0, cursorX, slotY);
				SpriteRenderer.drawFrame(gfx, iconFrame, 0, cursorX, slotY);
				cursorX = ((cursorX + (((slotWidth + 2) | 0))) | 0);
			}
		}
		let money: number = neighborhood.currentMinuteOfDay();
		let label: SdkString = canvas.beginString();
		canvas.appendTime(money);
		SpriteRenderer.drawFrame(gfx, 596, 0, barX, barY);
		SpriteRenderer.hitTest(hitRect, 596, 0, 0);
		let textX: number = ((barX + hitRect[0]) | 0);
		let textY: number = ((barY + hitRect[1]) | 0);
		SpriteRenderer.hitTest(hitRect, 596, 0, 1);
		let textX2: number = ((barX + hitRect[0]) | 0);
		let textY2: number = ((barY + hitRect[1]) | 0);
		canvas.drawString(label, 2, ((textX + 1) | 0), ((textY + 1) | 0), 24);
		let moodValue: number = Neighborhood.WEEKDAY_TEXT_IDS[neighborhood.dayOfWeek()];
		canvas.drawStringById(moodValue, 2, ((textX2 + 1) | 0), ((textY2 + 1) | 0), 20);
		let anchorFrame: number = 0;
		let statusAnimId: number = 0;
		if (this.isUiBlocked()) {
			anchorFrame = 2;
			statusAnimId = 673;
		}
		else {
			if (neighborhood.paused) {
				anchorFrame = 4;
				statusAnimId = 671;
			}
			else {
				anchorFrame = 3;
				statusAnimId = 672;
			}
		}
		this.statusAnimator.play(statusAnimId, 4);
		SpriteRenderer.hitTest(hitRect, 596, 0, anchorFrame);
		SpriteRenderer.drawAnimator(gfx, this.statusAnimator, ((barX + hitRect[0]) | 0), ((barY + hitRect[1]) | 0));
	}
	drawBottomBar(gfx: Graphics): void {
		if (this.objectMenuOpen) {
			this.drawObjectMenu(gfx);
		}
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		let hitRect: Int32Array = this.frameBounds;
		let centerX: number = canvas.halfWidth();
		let bottomY: number = ((canvas.getHeight() - 15) | 0);
		if (GameScreen.screenState !== 16 && GameScreen.screenState !== 7 && this.getSelectedSimId() === -1 && !this.objectMenuOpen) {
			this.drawNeedArrows(gfx);
		}
		if (GameCanvas.landscape) {
			SpriteRenderer.drawFrame(gfx, 588, 0, centerX, bottomY);
		}
		else {
			SpriteRenderer.drawFrame(gfx, 587, 0, centerX, bottomY);
		}
		if (this.notifyId !== -1) {
			this.drawNotification(gfx);
		}
		SpriteRenderer.hitTestMirrored(hitRect, 587, 0, 0);
		let iconX: number = ((centerX + hitRect[0]) | 0);
		let iconY: number = ((bottomY + hitRect[1]) | 0);
		for (let slot: number = 0; slot < 4; slot++) {
			let slotX: number = ((iconX + Math.imul(slot, 16)) | 0);
			let animator: Animator = null!;
			if ((animator = this.slotAnimators[slot]).isRunning()) {
				SpriteRenderer.drawAnimator(gfx, animator, slotX, iconY);
			}
		}
		SpriteRenderer.hitTestMirrored(hitRect, 587, 0, 1);
		iconX = ((((centerX + hitRect[0]) | 0) + -8) | 0);
		iconY = ((((bottomY + hitRect[1]) | 0) + (hitRect[3] >> 1)) | 0);
		let barWidth: number = hitRect[2];
		let rightX: number = ((iconX + barWidth) | 0);
		let needCount: number = neighborhood.needCount;
		for (let index: number = 0; index < needCount && rightX > iconX; index++) {
			if (neighborhood.needValue(index) < 2293760) {
				let frameId: number = neighborhood.needHintText(index);
				SpriteRenderer.drawFrame(gfx, frameId, 0, rightX, iconY);
				rightX = ((rightX - 16) | 0);
			}
		}
		for (let secondIndex: number = 0; secondIndex < needCount && rightX > iconX; secondIndex++) {
			if (neighborhood.needValue(secondIndex) > 4259840) {
				let secondFrameId: number = neighborhood.needLabelText(secondIndex);
				SpriteRenderer.drawFrame(gfx, secondFrameId, 0, rightX, iconY);
				rightX = ((rightX - 16) | 0);
			}
		}
	}
	drawRelationshipBar(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let centerX: number = this.canvas.getWidth() >> 1;
		let barY: number = ((canvas.getHeight() - 15) | 0);
		SpriteRenderer.drawFrame(gfx, 586, 0, centerX, barY);
		let negative: boolean = this.relationPercent < 0;
		let percent: number = Math.abs(this.relationPercent >> 16);
		let barWidth: number = ((Math.imul(SpriteRenderer.frameWidth(584, 0), percent) / 100) | 0);
		let clipX: number = ((centerX + (negative ? ((barWidth * -1) | 0) : 0)) | 0);
		let savedClipX: number = gfx.getClipX();
		let savedClipY: number = gfx.getClipY();
		let savedClipW: number = gfx.getClipWidth();
		let savedClipH: number = gfx.getClipHeight();
		gfx.setClip(clipX, savedClipY, barWidth, savedClipH);
		let frameId: number = negative ? 585 : 584;
		SpriteRenderer.drawFrame(gfx, frameId, 0, centerX, barY);
		gfx.setClip(savedClipX, savedClipY, savedClipW, savedClipH);
		this.drawCenteredLabel(gfx, centerX, barY);
	}
	chooseDialogOptionAt(posX: number, posY: number): void {
		this.openDialog(posX, posY, 0);
	}
	openDialog(posX: number, posY: number, source: number): void {
		this.closeObjectMenu();
		this.interactionSource = source;
		this.showInfoPanel(posX, posY);
		this.onTick(9);
	}
	handleDialogChoice(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 4128, 12)) {
			switch (this.interactionSource) {
				case 0:
					this.onTick(5);
					return;
				case 1:
					this.startTutorialGame();
					return;
				case 2:
					if (this.activePlayer.actionId === 159) {
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
	hasFade(): boolean {
		return this.fadePhase !== 0;
	}
	syncFadeWithAction(): void {
		this.closeOpenMenus();
		this.pendingAction = this.activePlayer.actionId;
		this.setFadePhase(1);
	}
	startFadeIn(): void {
		this.closeOpenMenus();
		this.setFadePhase(2);
	}
	setFadePhase(phase: number): void {
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
	updateFade(delta: number): void {
		if (this.cameraClamped) {
			switch (this.fadePhase) {
				case 0:
					return;
				case 1:
					this.neighborhood.celebrate();
					if (this.fadeAmount === 1000) {
						this.setFadePhase(2);
						this.runPendingAction();
						return;
					}
					this.fadeAmount = Math.min(((this.fadeAmount + delta) | 0), 1000);
					return;
				case 2:
					this.neighborhood.celebrate();
					this.fadeAmount = ((this.fadeAmount + delta) | 0);
					if (this.fadeAmount >= 1500) {
						this.setFadePhase(3);
						return;
					}
					break;
				case 3:
					this.neighborhood.celebrate();
					if (this.fadeAmount === 0) {
						this.setFadePhase(0);
						this.finishPendingAction();
						return;
					}
					this.fadeAmount = Math.max(((this.fadeAmount - delta) | 0), 0);
			}
		}
	}
	runPendingAction(): void {
		switch (this.pendingAction) {
			case 6:
				if (this.isNoSaveMode() && this.currentLot.areaId() === 0) {
					this.assignFreeWillAction(this.activePlayer);
					this.timeAdvanceSeconds = 120;
					this.neighborhood.celebrate();
					let sims: Sim[] = this.getSims();
					for (let simIndex: number = 0; simIndex < sims.length; simIndex++) {
						let player: Sim = null!;
						if ((player = sims[simIndex]) !== this.activePlayer) {
							if (this.neighborhood.simHomeLotOf(player.variant) !== 0) {
								player.despawn();
							}
							else {
								player.queueAction(0, (null as unknown as Entity));
							}
						}
					}
					return;
				}
				else {
					this.canvas.setHouse(0);
					this.showDialogAtOnce(2, 96);
				}
			default:
				return;
			case 121:
				this.neighborhood.skipToTime(((this.activePlayer.timer - 15) | 0));
				return;
			case 139:
				let entities: Entity[] = this.getEntities();
				for (let index: number = 0; index < entities.length; index++) {
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
	finishPendingAction(): void {
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
	drawFadeBars(gfx: Graphics): void {
		if (this.cameraClamped) {
			let barHeight: number = (((((Math.imul(Math.min(this.fadeAmount, 1000), this.viewHeight) / 1000) | 0) >> 1) + 1) | 0);
			gfx.setColor(0);
			gfx.fillRect(this.viewX, this.viewY, this.viewWidth, barHeight);
			gfx.fillRect(this.viewX, ((((this.viewY + this.viewHeight) | 0) - barHeight) | 0), this.viewWidth, barHeight);
		}
	}
	showNotification(messageId: number, secondId: number): void {
		let canvas: GameCanvas = this.canvas;
		this.notifyId = (messageId << 16 >> 16);
		this.notifyId2 = (secondId << 16 >> 16);
		this.notificationTimer = 0;
		this.notificationScroll = 0;
		this.notificationWidth = (((canvas.stringWidthById(messageId, 1) << 16) + 327680) | 0);
		if (secondId !== -1) {
			this.notificationWidth = ((this.notificationWidth + (((5 + canvas.stringWidthById(secondId, 1)) | 0) << 16)) | 0);
		}
	}
	clearNotification(messageId: number): void {
		if (this.notifyId === messageId) {
			this.notifyId = -1;
		}
	}
	hasNotification(): boolean {
		return this.notifyId !== -1;
	}
	advanceNotification(delta: number): void {
		if (this.notifyId !== -1) {
			this.notificationTimer = ((this.notificationTimer + delta) | 0);
			if (this.notificationTimer > 2000) {
				if (this.notificationScroll > this.notificationWidth) {
					this.notifyId = -1;
					this.notifyId2 = -1;
					return;
				}
				this.notificationScroll = ((this.notificationScroll + (Math.imul(delta, 3276))) | 0);
			}
		}
	}
	drawNotification(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let bubbleHeight: number = SpriteRenderer.frameHeight(670, 0);
		let bubbleY: number = ((((((canvas.getHeight() - 15) | 0) - this.bottomBarHeight) | 0) - bubbleHeight) | 0);
		let hitRect: Int32Array = this.frameBounds;
		SpriteRenderer.hitTest(this.frameBounds, 670, 0, 0);
		let anchorX: number = ((0 + hitRect[0]) | 0);
		let textY: number = ((bubbleY + hitRect[1]) | 0);
		SpriteRenderer.drawFrame(gfx, 670, 0, 0, bubbleY);
		if (this.notificationTimer >= 1000 || (this.notificationTimer & 511) <= 255) {
			let scrollX: number = ((anchorX - (this.notificationScroll >> 16)) | 0);
			canvas.drawStringById(this.notifyId, 1, scrollX, textY, 20);
			if (this.notifyId2 !== -1) {
				let firstWidth: number = canvas.stringWidthById(this.notifyId, 1);
				let secondX: number = ((((scrollX + firstWidth) | 0) + 5) | 0);
				canvas.drawStringById(this.notifyId2, 1, secondX, textY, 20);
			}
		}
	}
	clearMoveFlags(): void {
		this.leftPressed = false;
		this.rightPressed = false;
		this.downPressed = false;
		this.upPressed = false;
		this.canvas.clearActions();
		this.canvas.releaseSoftKeys();
	}
	handleInputBits(bits: number): void {
		this.setMoveFlags(bits);
		if ((bits & 123870) !== 0) {
			this.clearCameraTarget();
		}
	}
	setMoveFlags(bits: number): void {
		this.leftPressed = (bits & 32914) !== 0;
		this.rightPressed = (bits & 66120) !== 0;
		this.upPressed = (bits & 8206) !== 0;
		this.downPressed = (bits & 17280) !== 0;
	}
	useCursorTarget(): void {
		this.clearCameraTarget();
		let entity: Entity = this.followTarget;
		if (this.followTarget !== null && this.atTarget) {
			if (entity === this.activePlayer) {
				this.openObjectMenu();
			}
			else {
				this.openInteractionMenu(entity);
			}
		}
		else {
			if (GameScreen.screenState === 5 && this.atTarget) {
				let lot: Lot = this.currentLot;
				let tileX: number = this.currentLot.toWorldX(this.camX, this.camY);
				let tileY: number = lot.toWorldY(this.camX, this.camY);
				if (this.currentLot.isWalkableAt(tileX, tileY)) {
					this.queueInteraction(106, null, tileX, tileY);
				}
			}
		}
	}
	drawCursorSelection(gfx: Graphics): void {
		if (this.menuPage !== 6 && this.miniGamePhase !== 1) {
			let canvas: GameCanvas = this.canvas;
			let lot: Lot = this.currentLot;
			let screenX: number = this.camX;
			let screenY: number = this.camY;
			if (this.objectMenuOpen && this.followTarget !== null) {
				screenX = this.camTargetX;
				screenY = this.camTargetY;
			}
			screenX = (((screenX >> 16) + lot.viewCenterX()) | 0);
			screenY = (((screenY >> 16) + lot.viewCenterY()) | 0);
			if (this.objectMenuOpen) {
				this.drawMenuList_2(gfx, screenX, screenY);
			}
			else {
				SpriteRenderer.drawAnimator(gfx, this.selectionAnimator, screenX, screenY);
				let thoughtId: number = 0;
				if (this.atTarget && this.followTarget !== null && this.stillTime < 1000 && (thoughtId = this.followTarget.interactionAnimId()) !== 24) {
					let thoughtW: number = canvas.stringWidthById(thoughtId, 1);
					let thoughtH: number = ((canvas.lineHeight(1) - 2) | 0);
					let boxW: number = ((((3 + thoughtW) | 0) + 3) | 0);
					let boxH: number = ((((2 + thoughtH) | 0) + 2) | 0);
					let boxX: number = Math.max(0, ((((screenX + -5) | 0) - boxW) | 0));
					let boxY: number = ((((screenY + -5) | 0) - boxH) | 0);
					SpriteRenderer.setColor(gfx, 77);
					gfx.drawRect(boxX, boxY, boxW, boxH);
					SpriteRenderer.setColor(gfx, 76);
					gfx.fillRect(((boxX + 1) | 0), ((boxY + 1) | 0), ((boxW - 1) | 0), ((boxH - 1) | 0));
					canvas.drawStringById(thoughtId, 1, ((((boxX + 3) | 0) + (thoughtW >> 1)) | 0), ((boxY + 2) | 0), 17);
				}
			}
		}
	}
	updateMovement(delta: number): void {
		if (!this.movingToTarget) {
			let xDirection: number = (((this.rightPressed ? 65536 : 0) + (this.leftPressed ? -65536 : 0)) | 0);
			let yDirection: number = (((this.downPressed ? 65536 : 0) + (this.upPressed ? -65536 : 0)) | 0);
			if (xDirection !== 0 || yDirection !== 0) {
				let angle: number = FixedMath.atan2(yDirection, xDirection);
				this.turnToNearestEntity(angle);
			}
		}
		if (this.movingToTarget) {
			if (this.followTarget !== null) {
				let hitRect: Int32Array = this.frameBounds;
				this.followTarget.computePosition(hitRect);
				this.camTargetX = hitRect[0];
				this.camTargetY = hitRect[1];
			}
			if (this.objectMenuOpen) {
			}
			let step: number = Math.imul(19660, delta);
			let targetDx: number = ((this.camTargetX - this.camX) | 0) >> 8;
			let targetDy: number = ((this.camTargetY - this.camY) | 0) >> 8;
			let distance: number = FixedMath.length(targetDx, targetDy);
			if (step >= distance << 8) {
				this.camX = this.camTargetX;
				this.camY = this.camTargetY;
			}
			else {
				step = FixedMath.div(step, distance);
				this.camX = ((this.camX + FixedMath.mul(targetDx, step)) | 0);
				this.camY = ((this.camY + FixedMath.mul(targetDy, step)) | 0);
			}
			this.atTarget = this.camX === this.camTargetX && this.camY === this.camTargetY;
			this.movingToTarget = this.movingToTarget && !this.atTarget;
		}
		else {
			if (this.followTime <= 0) {
				let stepX: number = Math.imul(delta, ((((this.rightPressed ? 13107 : 0) + (this.leftPressed ? -13107 : 0)) | 0)));
				let stepY: number = Math.imul(delta, ((((this.downPressed ? 13107 : 0) + (this.upPressed ? -13107 : 0)) | 0)));
				this.camX = ((this.camX + stepX) | 0);
				this.camY = ((this.camY + stepY) | 0);
				if (stepX !== 0 || stepY !== 0) {
					this.followTarget = null;
					this.followTime = 0;
					let tile: Tile = this.currentLot.groundTile;
					let mapWidth: number = (((Math.imul(this.currentLot.groundTile.columns, Tile.tileWidth()) << 16) - 983040) | 0);
					let mapHeight: number = (((Math.imul(tile.rowCountValue, Tile.tileHeight()) << 16) - 983040) | 0);
					this.camX = FixedMath.clamp(this.camX, 0, mapWidth);
					this.camY = FixedMath.clamp(this.camY, 0, mapHeight);
					this.camTargetX = this.camX;
					this.camTargetY = this.camY;
					this.atTarget = true;
					this.stillTime = 0;
				}
				else {
					if (this.followTarget !== null && this.atTarget) {
						let hitRect2: Int32Array = this.frameBounds;
						this.followTarget.computePosition(hitRect2);
						this.camTargetX = hitRect2[0];
						this.camTargetY = hitRect2[1];
						this.camX = hitRect2[0];
						this.camY = hitRect2[1];
					}
				}
			}
		}
		if (this.atTarget) {
			this.stillTime = ((this.stillTime + delta) | 0);
		}
		else {
			this.stillTime = 0;
		}
	}
	updatePlayerAnimator(delta: number): void {
		if (!this.hasFade()) {
			if (!this.mainMenuOpen) {
				this.updateMovement(delta);
			}
			if (this.followTarget !== null && this.followTime > 0) {
				this.followTime = ((this.followTime - delta) | 0);
			}
			let frameId: number = (!this.atTarget || this.followTarget === null) && !this.menuOpen && (GameScreen.screenState !== 7 || this.menuPage !== 2) ? 566 : 567;
			this.selectionAnimator.play(frameId, 4);
			this.selectionAnimator.advance(delta);
		}
	}
	followEntity(entity: Entity): void {
		this.followTarget = entity;
		if (entity !== null) {
			let hitRect: Int32Array = this.frameBounds;
			entity.computePosition(hitRect);
			this.camTargetX = hitRect[0];
			this.camTargetY = hitRect[1];
			this.movingToTarget = true;
			this.stillTime = 0;
		}
		else {
			this.camTargetX = this.camX;
			this.camTargetY = this.camY;
			this.movingToTarget = false;
		}
	}
	followFlaggedEntity(flag: number): void {
		this.followFlag = flag;
		if (this.followTarget === null || !this.followTarget.hasFlag(flag)) {
			this.followEntity((null as unknown as Entity));
		}
	}
	turnToNearestEntity(angle: number): void {
		let hitRect: Int32Array = this.frameBounds;
		let best: Entity = null;
		let bestDistance: number = 7680;
		let originX: number = this.camX;
		let originY: number = this.camY;
		let wanted: number = FixedMath.wrapAngle(angle);
		let entities: Entity[] = null!;
		let count: number = (entities = this.getEntities()).length;
		let flag: number = this.followFlag;
		for (let index: number = 0; index < count; index++) {
			let entity: Entity = null!;
			(entity = entities[index]).computePosition(hitRect);
			let entityX: number = hitRect[0];
			let entityY: number = hitRect[1];
			if (entity !== this.followTarget && entity.hasFlag(flag)) {
				let rangeX: number = this.viewWidth << 16;
				let rangeY: number = this.viewHeight << 16;
				let deltaX: number = Math.abs(((originX - entityX) | 0));
				let deltaY: number = Math.abs(((originY - entityY) | 0));
				if (deltaX <= rangeX && deltaY <= rangeY && (deltaX !== 0 || deltaY !== 0)) {
					let entityAngle: number = FixedMath.atan2(((entityY - originY) | 0), ((entityX - originX) | 0));
					if (Math.abs(FixedMath.wrapAngle(((wanted - entityAngle) | 0))) < 51472) {
						let gapX: number = ((originX - entityX) | 0) >> 8;
						let gapY: number = ((originY - entityY) | 0) >> 8;
						let distance: number = 0;
						if ((distance = FixedMath.length(gapX, gapY)) < bestDistance) {
							best = entity;
							bestDistance = distance;
						}
					}
				}
			}
		}
		if (best !== null) {
			this.followEntity(best);
			this.followTime = 200;
		}
	}
	followNearestEntity(): void {
		let hitRect: Int32Array = this.frameBounds;
		let best: Entity = null;
		let bestDistance: number = 2147483647;
		let originX: number = this.camX;
		let originY: number = this.camY;
		let entities: Entity[] = null!;
		let count: number = (entities = this.getEntities()).length;
		let flag: number = this.followFlag;
		for (let index: number = 0; index < count; index++) {
			let entity: Entity = null!;
			(entity = entities[index]).computePosition(hitRect);
			let entityX: number = hitRect[0];
			let entityY: number = hitRect[1];
			if (entity !== this.followTarget && entity.hasFlag(flag)) {
				let deltaX: number = ((originX - entityX) | 0) >> 8;
				let deltaY: number = ((originY - entityY) | 0) >> 8;
				let distance: number = 0;
				if ((distance = FixedMath.length(deltaX, deltaY)) < bestDistance) {
					best = entity;
					bestDistance = distance;
				}
			}
		}
		if (best !== null) {
			this.followEntity(best);
			this.followTime = 200;
		}
	}
	updateCameraClampTarget(): void {
		let lot: Lot = this.currentLot;
		let neighborhood: Neighborhood = this.neighborhood;
		let targetX: number = this.camTargetX;
		let targetY: number = this.camTargetY;
		if (this.cameraTarget !== null) {
			let hitRect: Int32Array = this.frameBounds;
			this.cameraTarget.computePosition(hitRect);
			targetX = hitRect[0];
			targetY = hitRect[1];
		}
		else {
			if (this.menuOpen || neighborhood.watchedSim !== -1) {
				let speaker: Entity = this.menuTarget;
				if (this.menuTarget === null && this.followTarget !== null && this.followTarget.hasFlag(16384) && (this.followTarget as unknown as Sim).variant === neighborhood.watchedSim) {
					speaker = this.followTarget;
				}
				if (speaker !== null) {
					let halfRangeX: number = this.viewWidth << 14;
					if (speaker.hasFlag(16384)) {
						targetX = ((targetX - halfRangeX) | 0);
					}
					else {
						targetX = ((targetX + halfRangeX) | 0);
					}
				}
			}
		}
		let tile: Tile = lot.groundTile;
		let centreX: number = lot.boundsWidth;
		let halfCentreX: number = lot.boundsWidth >> 1;
		let centreY: number = lot.boundsHeight;
		let halfCentreY: number = lot.boundsHeight >> 1;
		let minX: number = halfCentreX << 16;
		let maxX: number = ((((Math.imul(tile.columns, Tile.tileWidth()) - halfCentreX) | 0) - 1) | 0) << 16;
		let minY: number = halfCentreY << 16;
		let maxY: number = ((((Math.imul(tile.rowCountValue, Tile.tileHeight()) - halfCentreY) | 0) - 1) | 0) << 16;
		if (GameCanvas.landscape) {
			maxY = ((((Math.imul(tile.rowCountValue, Tile.tileHeight()) - halfCentreY) | 0) - 1) | 0) << 16;
		}
		else {
			maxX = ((((Math.imul(tile.columns, Tile.tileWidth()) - halfCentreX) | 0) - 1) | 0) << 16;
		}
		targetX = FixedMath.clamp(targetX, minX, maxX);
		targetY = FixedMath.clamp(targetY, minY, maxY);
		this.clampTargetX = targetX;
		this.clampTargetY = targetY;
	}
	setCameraTarget(entity: Entity): void {
		if (!this.menuOpen && !this.objectMenuOpen) {
			this.cameraTarget = entity;
			this.followTarget = null;
			if (entity === null) {
				this.recenterPending = true;
			}
		}
	}
	clearCameraTarget(): void {
		this.cameraTarget = null;
		this.recenterPending = false;
	}
	updateCameraPan(delta: number): void {
		let lot: Lot = this.currentLot;
		let maxSpeed: number = 11534;
		let lotX: number = 32;
		if (this.objectMenuOpen) {
			maxSpeed = FixedMath.mul(11534, 196608);
			lotX = FixedMath.mul(32, 196608);
		}
		let lotY: number = lot.cameraX;
		let signX: number = lot.cameraY;
		this.updateCameraClampTarget();
		let signY: number = FixedMath.sign(((this.clampTargetX - lotY) | 0));
		let stepX: number = FixedMath.sign(((this.clampTargetY - signX) | 0));
		if (signY > 0 && this.prevCamVelX > 0) {
			this.camVelX = ((this.camVelX + (Math.imul(lotX, delta))) | 0);
		}
		else {
			if (signY > 0 && this.prevCamVelX <= 0) {
				this.camVelX = 1441;
			}
			else {
				if (signY < 0 && this.prevCamVelX < 0) {
					this.camVelX = ((this.camVelX - (Math.imul(lotX, delta))) | 0);
				}
				else {
					if (signY < 0 && this.prevCamVelX >= 0) {
						this.camVelX = -1441;
					}
					else {
						if (signY === 0 && this.prevCamVelX > 0) {
							this.camVelX = -1441;
						}
						else {
							if (signY === 0 && this.prevCamVelX < 0) {
								this.camVelX = 1441;
							}
						}
					}
				}
			}
		}
		if (this.camVelX < ((maxSpeed * -1) | 0)) {
			this.camVelX = ((maxSpeed * -1) | 0);
		}
		if (this.camVelX > maxSpeed) {
			this.camVelX = maxSpeed;
		}
		if (stepX > 0 && this.prevCamVelY > 0) {
			this.camVelY = ((this.camVelY + (Math.imul(lotX, delta))) | 0);
		}
		else {
			if (stepX > 0 && this.prevCamVelY <= 0) {
				this.camVelY = 1441;
			}
			else {
				if (stepX < 0 && this.prevCamVelY < 0) {
					this.camVelY = ((this.camVelY - (Math.imul(lotX, delta))) | 0);
				}
				else {
					if (stepX < 0 && this.prevCamVelY >= 0) {
						this.camVelY = -1441;
					}
					else {
						if (stepX === 0 && this.prevCamVelY > 0) {
							this.camVelY = -1441;
						}
						else {
							if (stepX === 0 && this.prevCamVelY < 0) {
								this.camVelY = 1441;
							}
						}
					}
				}
			}
		}
		if (this.camVelY < ((maxSpeed * -1) | 0)) {
			this.camVelY = ((maxSpeed * -1) | 0);
		}
		if (this.camVelY > maxSpeed) {
			this.camVelY = maxSpeed;
		}
		let stepY: number = ((lotY + Math.imul(this.camVelX, delta)) | 0);
		let nextX: number = ((signX + Math.imul(this.camVelY, delta)) | 0);
		let reachedX: boolean = false;
		if ((this.camVelX <= 0 || stepY <= this.clampTargetX) && (this.camVelX >= 0 || stepY >= this.clampTargetX)) {
			reachedX = stepY === this.clampTargetX;
		}
		else {
			reachedX = true;
			stepY = this.clampTargetX;
		}
		let reachedY: boolean = false;
		if ((this.camVelY <= 0 || nextX <= this.clampTargetY) && (this.camVelY >= 0 || nextX >= this.clampTargetY)) {
			reachedY = nextX === this.clampTargetY;
		}
		else {
			reachedY = true;
			nextX = this.clampTargetY;
		}
		this.prevCamVelX = this.camVelX;
		this.prevCamVelY = this.camVelY;
		lot.cameraX = stepY;
		lot.cameraY = nextX;
		this.cameraClamped = reachedX && reachedY;
		if (this.cameraTarget !== null || this.recenterPending) {
			this.camX = ((this.camX + (((stepY - lotY) | 0))) | 0);
			this.camY = ((this.camY + (((nextX - signX) | 0))) | 0);
		}
	}
	snapCameraToTarget(): void {
		this.updateCameraClampTarget();
		this.currentLot.cameraX = this.clampTargetX;
		this.currentLot.cameraY = this.clampTargetY;
		this.cameraClamped = true;
	}
	placeCameraOnEntity(entity: Entity): void {
		this.followEntity(entity);
		this.camX = this.camTargetX;
		this.camY = this.camTargetY;
		this.snapCameraToTarget();
	}
	resumeSavedGame(): void {
		this.currentLot.loadArea(this.canvas.houseIndex);
		if (this.startOption === 96) {
			this.startOption = 5;
			this.assignFreeWillAction(this.activePlayer);
			this.timeAdvanceSeconds = 120;
			this.neighborhood.celebrate();
			this.startFadeIn();
		}
	}
	restoreSavedSims(): void {
		this.currentLot.openHouse(this.canvas.houseUpgrade);
		let sims: Sim[] = this.getSims();
		for (let index: number = 0; index < sims.length; index++) {
			let sim: Sim = null!;
			if ((sim = sims[index]).variant !== 0) {
				sim.addBehaviourFlags(32);
			}
		}
	}
	setupNewGame(): void {
		let addFlag: boolean = false;
		if (this.startOption === 99) {
			this.startOption = 5;
			addFlag = true;
		}
		this.currentLot.openArea(addFlag);
		this.activePlayer.addFlag(1);
	}
	getEntities(): Entity[] {
		if (this.entitiesDirty) {
			this.entitiesDirty = false;
			if (this.entityCache === null || this.entityCache.length !== this.entityStack.size()) {
				this.entityCache = null;
				this.entityCache = new Array(this.entityStack.size()).fill(null);
			}
			this.entityStack.copyInto(this.entityCache);
		}
		return this.entityCache;
	}
	getSims(): Sim[] {
		if (this.simsDirty) {
			this.simsDirty = false;
			let entities: Entity[] = this.getEntities();
			let simCount: number = 0;
			for (let index: number = 0; index < entities.length; index++) {
				if (entities[index].hasFlag(16384)) {
					simCount++;
				}
			}
			let sims: Sim[] = this.simCache;
			if (this.simCache === null || sims.length !== simCount) {
				this.simCache = null;
				sims = new Array(simCount).fill(null);
				this.simCache = sims;
			}
			let simIndex: number = 0;
			for (let secondIndex: number = 0; secondIndex < entities.length; secondIndex++) {
				if (entities[secondIndex].hasFlag(16384)) {
					sims[simIndex] = (entities[secondIndex] as unknown as Sim);
					simIndex++;
				}
			}
		}
		return this.simCache;
	}
	findEntityByType(type: number): Entity {
		let entities: Entity[] = this.getEntities();
		let start: number = this.canvas.randomRange(0, ((entities.length - 1) | 0));
		for (let offset: number = 0; offset < entities.length; offset++) {
			let index: number = (((((start + offset) | 0)) % entities.length) | 0);
			let entity: Entity = null!;
			if ((entity = entities[index]).type === type) {
				return entity;
			}
		}
		return null;
	}
	findNearestEntityOfType(type: number, tileX: number, tileY: number): Entity {
		let best: Entity = null;
		let bestDistance: number = -1;
		let entities: Entity[] = this.getEntities();
		for (let index: number = 0; index < entities.length; index++) {
			let entity: Entity = null!;
			let distance: number = 0;
			if ((entity = entities[index]).type === type && ((distance = ((Math.abs(((tileX - entity.posX) | 0)) + Math.abs(((tileY - entity.posY) | 0))) | 0)) < bestDistance || best === null)) {
				bestDistance = distance;
				best = entity;
			}
		}
		return best;
	}
	isEntityAtTile(objectType: number, posX: number, posY: number): boolean {
		let tileX: number = Lot.toTileX(posX);
		let tileY: number = Lot.toTileX(posY);
		let entities: Entity[] = this.getEntities();
		for (let index: number = 0; index < entities.length; index++) {
			let entity: Entity = null!;
			if ((entity = entities[index]).behaviorClass() === objectType) {
				let entityX: number = Lot.toTileX(entity.posX);
				let entityY: number = Lot.toTileY(entity.posY);
				if (entityX === tileX && entityY === tileY) {
					return true;
				}
			}
		}
		return false;
	}
	findEntityByObjectType(objectType: number): Entity {
		let entities: Entity[] = this.getEntities();
		let start: number = this.canvas.randomRange(0, ((entities.length - 1) | 0));
		for (let offset: number = 0; offset < entities.length; offset++) {
			let index: number = (((((start + offset) | 0)) % entities.length) | 0);
			let entity: Entity = null!;
			if ((entity = entities[index]).behaviorClass() === objectType) {
				return entity;
			}
		}
		return null;
	}
	findFreeEntityForFlag(flags: number): Entity {
		let lot: Lot = this.currentLot;
		let entities: Entity[] = this.getEntities();
		let start: number = this.canvas.randomRange(0, ((entities.length - 1) | 0));
		for (let offset: number = 0; offset < entities.length; offset++) {
			let index: number = (((((start + offset) | 0)) % entities.length) | 0);
			let entity: Entity = null!;
			let type: number = (entity = entities[index]).type;
			if ((lot.linkedIdAt(type) & flags) !== 0 && entity.hasFreeSlot() && !entity.hasState(768)) {
				return entity;
			}
		}
		return null;
	}
	findSimByIndex(simIndex: number): Sim {
		let sims: Sim[] = null!;
		let count: number = (sims = this.getSims()).length;
		for (let index: number = 0; index < count; index++) {
			let sim: Sim = null!;
			if ((sim = sims[index]).variant === simIndex) {
				return sim;
			}
		}
		return null;
	}
	createEntity(type: number, posX: number, posY: number, facing: number, extra: number): Entity {
		let traits: number = this.currentLot.objectFlagsAt(type);
		if (this.isSavedNeighborhood() && (type === 2 || type === 3)) {
			traits = (traits | 8192);
		}
		let entity: any = null;
		if ((traits & 16384) !== 0) {
			entity = new Sim(this);
			if (type === 0) {
				this.activePlayer = (entity as unknown as Sim);
			}
		}
		else {
			if ((traits & 8192) !== 0) {
				entity = new Entity(this);
			}
		}
		if (entity !== null) {
			(entity as unknown as Entity).place(type, posX, posY, facing, extra);
			this.registerEntity((entity as unknown as Entity));
			if ((entity as unknown as Entity).hasFlag(16384)) {
				this.queuePortraitLoad((entity as unknown as Sim));
			}
		}
		return (entity as unknown as Entity);
	}
	createEntityNear(type: number, near: Entity, facing: number): Entity {
		let hitRect: Int32Array = this.frameBounds;
		let offset: number = 0;
		do {
			near.computeSlotPosition(hitRect, offset);
			offset++;
		}
		while (!this.currentLot.isWalkableAt(hitRect[0], hitRect[1]));
		return this.createEntity(type, hitRect[0], hitRect[1], 0, facing);
	}
	createEntityAtTile(type: number, pixelX: number, pixelY: number, facing: number, extra: number): Entity {
		let tileX: number = Lot.toFixedX(pixelX);
		let tileY: number = Lot.toFixedY(pixelY);
		return this.createEntity(type, tileX, tileY, facing, extra);
	}
	createRoomObject(type: number, posX: number, posY: number, param: number): void {
		let entity: Entity = null!;
		if ((entity = this.createEntity(7, type, posX, 3, -1)) !== null) {
			entity.setIdleAnimation(posY, param);
		}
	}
	createObjectWithParam(type: number, posX: number, posY: number): void {
		let entity: Entity = null!;
		if ((entity = this.createEntity(8, type, posX, 3, -1)) !== null) {
			entity.showMessage(posY);
		}
	}
	registerEntity(entity: Entity): void {
		entity.updateFootprint(true);
		this.entityStack.addElement(entity);
		this.entitiesDirty = true;
		if (entity.hasFlag(16384)) {
			this.simsDirty = true;
		}
	}
	removeEntity(entity: Entity): void {
		if (this.followTarget === entity) {
			this.followEntity((null as unknown as Entity));
		}
		if (this.menuOpen && this.menuTarget === entity) {
			this.closeInteractionMenu();
		}
		let simIndex: number = -1;
		if (entity.hasFlag(16384)) {
			simIndex = entity.variant;
		}
		entity.updateFootprint(false);
		this.entityStack.removeElement(entity);
		this.entitiesDirty = true;
		if (simIndex !== -1) {
			this.simsDirty = true;
			let slot: number = 0;
			if ((slot = GameCanvas.indexOfByte(simIndex, this.slotSimIds)) !== -1) {
				let recolorFlag: number = (this.neighborhood.simAttribute(simIndex, 0) === 0 ? 512 : 256) | 16384;
				this.canvas.unloadTiles(recolorFlag, slot);
				this.slotSimIds[slot] = -1;
			}
		}
	}
	advanceAllEntities(delta: number): void {
		let entities: Entity[] = null!;
		if ((entities = this.getEntities()) !== null) {
			for (let index: number = 0; index < entities.length; index++) {
				let entity: Entity = null!;
				(entity = entities[index]).advanceTime(delta);
			}
		}
	}
	advanceEntitiesOfType(delta: number, type: number): void {
		let entities: Entity[] = null!;
		if ((entities = this.getEntities()) !== null) {
			for (let index: number = 0; index < entities.length; index++) {
				let entity: Entity = null!;
				if ((entity = entities[index]).type === type) {
					entity.advanceTime(delta);
				}
			}
		}
	}
	clearBrokenEntities(): void {
		let entities: Entity[] = this.getEntities();
		for (let index: number = 0; index < entities.length; index++) {
			let entity: Entity = null!;
			if ((entity = entities[index]).hasFlag(4)) {
				entity.despawn();
			}
		}
	}
	openInteractionMenu(entity: Entity): void {
		let neighborhood: Neighborhood = this.neighborhood;
		if (this.actionMenuOpen) {
			this.closeActionMenu();
		}
		this.clearMoveFlags();
		let labels: Int16Array = this.menuLabels;
		let args: Int16Array = this.menuArgs;
		neighborhood.watchSim(-1);
		this.menuOpen = true;
		this.menuSubPage = false;
		this.menuParent = -1;
		this.menuTarget = entity;
		if (GameScreen.screenState === 7) {
			entity.addResourceIndices(labels);
			labels[4] = this.visibleRows;
			GameCanvas.copyList(args, labels);
		}
		else {
			let slotIndex: number = 0;
			if ((slotIndex = GameCanvas.indexOfObject(this.menuTarget, this.queuedTargets)) !== -1 && this.queuedInteractions[slotIndex] !== 2) {
				let moodId: number = 0;
				if (this.menuTarget.hasFlag(16384)) {
					moodId = this.neighborhood.simNameText(this.menuTarget.variant);
				}
				else {
					moodId = this.menuTarget.typeAnimId();
				}
				GameCanvas.initList(labels, moodId);
				GameCanvas.initList(args, moodId);
				GameCanvas.addToList(labels, 113);
				GameCanvas.addToList(args, -3);
			}
			else {
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
	closeInteractionMenu(): void {
		this.menuOpen = false;
		this.menuSubPage = false;
		this.menuParent = -1;
		this.menuTarget = null;
		this.updateInputMasks();
		this.followEntity(this.followTarget);
	}
	drawInteractionPrompt(gfx: Graphics): void {
		let promptHeight: number = this.menuTarget !== null && this.menuTarget.hasFlag(16384) ? 2 : 4;
		this.drawCaption(gfx, this.menuLabels, promptHeight);
	}
	getSelectedSimId(): number {
		let neighborhood: Neighborhood = this.neighborhood;
		if (!this.inputIdle && this.menuOpen) {
			if (this.menuTarget !== null && this.menuTarget.hasFlag(16384)) {
				return (this.menuTarget as unknown as Sim).variant;
			}
		}
		else {
			if (neighborhood.isWatchMode()) {
				return neighborhood.watchedSim;
			}
		}
		return -1;
	}
	drawSimInfoPanel(gfx: Graphics): void {
		let neighborhood: Neighborhood = this.neighborhood;
		let simId: number = this.getSelectedSimId();
		let showAction: boolean = !this.menuOpen || this.inputIdle;
		if (!this.inputIdle && this.menuOpen) {
			if (this.menuTarget !== null && this.menuTarget.hasFlag(16384)) {
				simId = (this.menuTarget as unknown as Sim).variant;
			}
		}
		else {
			if (neighborhood.isWatchMode()) {
				showAction = true;
				simId = neighborhood.watchedSim;
			}
		}
		if (simId !== -1) {
			let canvas: GameCanvas = this.canvas;
			let hitRect: Int32Array = this.frameBounds;
			neighborhood.collectNeedChanges(simId, hitRect);
			let optionRows: number = (((hitRect[0] !== -1 ? 1 : 0) + (hitRect[2] !== -1 ? 1 : 0)) | 0);
			let panelX: number = ((this.viewX + 3) | 0);
			let panelWidth: number = canvas.getWidth() >> 1;
			let bubbleHeight: number = SpriteRenderer.frameHeight(670, 0);
			let panelY: number = ((((((canvas.getHeight() - 15) | 0) - this.bottomBarHeight) | 0) - Math.imul(18, (((optionRows + 1) | 0)))) | 0);
			if (this.notifyId !== -1) {
				panelY = ((panelY - bubbleHeight) | 0);
			}
			let rowValue: number = neighborhood.simState(simId);
			this.drawTwoToneBar(gfx, panelX, panelY, panelWidth, rowValue, 65536, true);
			let rowY: number = ((panelY + 18) | 0);
			for (let rowIndex: number = 0; rowIndex < 4 && (rowValue = hitRect[rowIndex]) !== -1; rowIndex = (((rowIndex + 2) | 0) << 24 >> 24)) {
				let rowValue2: number = hitRect[((rowIndex + 1) | 0)];
				this.drawTwoToneBar(gfx, panelX, rowY, panelWidth, rowValue, rowValue2, false);
				rowY = ((rowY + 18) | 0);
			}
			if (showAction) {
				let actionId: number = -1;
				let actionTarget: Sim = null!;
				if ((actionTarget = this.activePlayer.partnerSim()) !== null && actionTarget.variant === simId) {
					actionId = neighborhood.interactionText(this.activePlayer.actionId);
				}
				if (actionId === 24) {
					actionId = -1;
				}
				let actionY: number = ((((((panelY - 14) | 0) - 4) | 0) - (actionId !== -1 ? 12 : 0)) | 0);
				let actionWidth: number = ((panelWidth - 4) | 0);
				SpriteRenderer.setColor(gfx, 76);
				gfx.fillRect(panelX, actionY, panelWidth, 14);
				SpriteRenderer.setColor(gfx, 77);
				gfx.drawRect(panelX, actionY, panelWidth, 14);
				canvas.drawStringById(neighborhood.simNameText(simId), 0, ((panelX + (panelWidth >> 1)) | 0), ((actionY + 0) | 0), 17);
				if (actionId !== -1) {
					let actionY2: number = ((actionY + 14) | 0);
					SpriteRenderer.setColor(gfx, 76);
					gfx.fillRect(panelX, actionY2, panelWidth, 12);
					SpriteRenderer.setColor(gfx, 77);
					gfx.drawRect(panelX, actionY2, panelWidth, 12);
					canvas.drawTextById(gfx, actionId, 1, ((panelX + 2) | 0), ((actionY2 + 2) | 0), actionWidth, 17);
				}
			}
		}
	}
	handleInteractionMenuInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 0, 4)) {
			this.resetIdleTimer();
			if (this.menuSubPage) {
				this.neighborhood.fillInventoryMenu(this.menuLabels, this.menuArgs, this.menuExtra);
				this.menuLabels[4] = this.visibleRows;
				this.menuSubPage = false;
				this.menuLabels[2] = this.menuParent;
				GameCanvas.clampListIndex(this.menuLabels);
			}
			else {
				this.closeInteractionMenu();
			}
		}
		else {
			if (this.matchesInput(keyState, actionMask, 4128, 16)) {
				let optionIndex: number = ((5 + this.menuLabels[2]) | 0);
				if (GameScreen.screenState === 7) {
					this.handleMenuAction(this.menuLabels[optionIndex]);
				}
				else {
					let result: number = 0;
					if ((result = this.menuArgs[optionIndex]) === -4) {
						this.closeInteractionMenu();
					}
					else {
						if (result === -3) {
							this.cancelQueuedInteraction(this.menuTarget);
							this.closeInteractionMenu();
						}
						else {
							if (result === -2) {
								this.menuParent = this.menuLabels[2];
								this.menuSubPage = true;
								let arg: number = this.menuLabels[optionIndex];
								this.neighborhood.fillFilteredMenu(this.menuLabels, this.menuArgs, this.menuExtra, arg);
								this.menuLabels[4] = this.visibleRows;
							}
							else {
								if (result === 137 || result === 136) {
									this.handleMenuCommandA();
								}
								else {
									if (result === 111) {
										this.handleMenuCommandB();
									}
									else {
										if (result !== 126) {
											this.closeOpenMenus();
											this.queueInteraction(result, this.followTarget, 0, 0);
											return;
										}
										this.handleMenuCommandC();
									}
								}
							}
						}
					}
				}
			}
			else {
				this.canvas.navigateLeftRight(this.menuLabels, keyState);
			}
		}
	}
	setMainMenuPage(page: number): void {
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
	openMainMenu(): void {
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
	closeMainMenu(): void {
		this.mainMenuOpen = false;
		this.updateInputMasks();
	}
	handleMainMenuPageInput(keyState: number, actionMask: number): void {
		let canvas: GameCanvas = this.canvas;
		if (this.canvas.menuMode !== -1) {
			canvas.menuKeyPressed(keyState, actionMask);
		}
		else {
			switch (this.mainMenuPage) {
				case 0:
					if (this.matchesInput(keyState, actionMask, 0, 4)) {
						this.closeMainMenu();
						return;
					}
					if (this.matchesInput(keyState, actionMask, 4128, 16)) {
						let optionIndex: number = ((5 + this.menuOptionIds[2]) | 0);
						this.handleMenuAction(this.menuOptionIds[optionIndex]);
						return;
					}
					canvas.navigateLeftRight(this.menuOptionIds, keyState);
					return;
				case 1:
				case 2:
				case 3:
					if (this.matchesInput(keyState, actionMask, 4128, 256)) {
						if (this.mainMenuPage !== 1) {
							canvas.saveSettings();
							canvas.saveGameData();
						}
						if (this.mainMenuPage !== 2) {
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
	drawMainMenu(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		this.canvas.drawDialogOverlay(gfx);
		let optionId: number = this.mainMenuPage;
		if (canvas.menuMode !== -1) {
			canvas.drawMenu(gfx);
		}
		else {
			if (optionId === 0) {
				this.drawCaption(gfx, this.menuOptionIds, 1);
			}
			else {
				if (optionId !== 2 && optionId !== 3 && optionId !== 1 && optionId !== 4) {
					if (optionId === 5) {
						this.drawCheatMenu(gfx);
					}
				}
				else {
					this.drawPageOne(gfx);
				}
			}
		}
	}
	openActionMenu(): void {
		this.tabSwitchRequested = false;
		if (this.menuOpen) {
			this.closeInteractionMenu();
		}
		this.clearMoveFlags();
		let options: Int16Array = this.actionOptionIds;
		this.actionMenuOpen = true;
		GameCanvas.initListWith(options, 35, this.canvas.menuRowSpacing);
		if (this.queuedCount > 0) {
			GameCanvas.addToList(options, 113);
		}
		if (this.neighborhood.canInteractAt(null, 0)) {
			GameCanvas.addToList(options, 327);
		}
		if (!this.isNewNeighborhood()) {
			GameCanvas.addToList(options, 328);
		}
		else {
			GameCanvas.addToList(options, 124);
		}
		if (!this.isNewNeighborhood() && this.currentLot.areaId() === 0) {
			GameCanvas.addToList(options, 114);
		}
		else {
			GameCanvas.addToList(options, 329);
		}
		GameCanvas.addToList(options, 116);
		GameCanvas.addToList(options, 115);
		GameCanvas.addToList(options, 117);
		GameCanvas.addToList(options, 118);
		GameCanvas.addToList(options, 119);
		GameCanvas.addToList(options, 120);
		GameCanvas.addToList(options, 122);
		if (this.canvas.cheatToggled) {
			GameCanvas.addToList(options, 123);
		}
		this.updateInputMasks();
	}
	closeActionMenu(): void {
		this.actionMenuOpen = false;
		this.updateInputMasks();
	}
	handleActionMenuInput(keyState: number, actionMask: number): void {
		let canvas: GameCanvas = this.canvas;
		if (this.matchesInput(keyState, actionMask, 0, 4)) {
			canvas.releaseSoftKeys();
			this.closeActionMenu();
		}
		else {
			if (this.matchesInput(keyState, actionMask, 4128, 16)) {
				let optionIndex: number = ((this.actionOptionIds[2] + 5) | 0);
				this.handleMenuAction(this.actionOptionIds[optionIndex]);
			}
			else {
				canvas.navigateLeftRight(this.actionOptionIds, keyState);
			}
		}
	}
	drawActionMenu(gfx: Graphics): void {
		this.canvas.drawDialogOverlay(gfx);
		this.drawCaption(gfx, this.actionOptionIds, 1);
	}
	openObjectMenu(): void {
		this.clearCameraTarget();
		this.objectMenuOpen = true;
		let options: Int16Array = this.objectOptionIds;
		GameCanvas.initList(this.objectOptionIds, 24);
		let count: number = this.currentLot.objectCount();
		for (let index: number = 0; index < count; index++) {
			if (this.neighborhood.hasVisitedItem(index)) {
				GameCanvas.addToList(options, index);
			}
		}
		this.selectObjectOption(this.objectOptionIds[2]);
		this.updateInputMasks();
	}
	closeObjectMenu(): void {
		this.objectMenuOpen = false;
		this.updateInputMasks();
	}
	handleObjectMenuInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 0, 4)) {
			this.closeObjectMenu();
		}
		else {
			if (this.matchesInput(keyState, actionMask, 4128, 16)) {
				this.closeObjectMenu();
				this.useCursorTarget();
			}
			else {
				if (this.canvas.navigateUpDown(this.objectOptionIds, keyState)) {
					this.selectObjectOption(this.objectOptionIds[2]);
				}
			}
		}
		if (this.matchesInput(keyState, actionMask, 32784, 0)) {
			this.showLeftArrow();
		}
		else {
			if (this.matchesInput(keyState, actionMask, 65600, 0)) {
				this.showRightArrow();
			}
		}
	}
	selectObjectOption(option: number): void {
		let objectType: number = this.objectOptionIds[((5 + option) | 0)];
		let entity: Entity = this.findEntityByType(objectType);
		this.followEntity(entity);
	}
	drawObjectMenu(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let frameHeight: number = SpriteRenderer.frameHeight(581, 0);
		let centerX: number = ((this.viewX + (this.viewWidth >> 1)) | 0);
		let topY: number = ((this.viewY + (frameHeight >> 1)) | 0);
		let bottomY: number = ((((this.viewY + this.viewHeight) | 0) - frameHeight) | 0);
		BaseScreen.drawNineSlice(gfx, this.viewX, this.viewY, this.viewWidth, this.viewHeight, 581, -1);
		canvas.drawStringById(124, 0, centerX, topY, 3);
		let optionCount: number = ((this.objectOptionIds[2] + 5) | 0);
		let objectType: number = this.objectOptionIds[optionCount];
		let objectId: number = this.findEntityByType(objectType).interactionAnimId();
		let label: SdkString = canvas.beginString();
		canvas.appendInt(((this.objectOptionIds[2] + 1) | 0));
		canvas.appendStringId(29);
		canvas.appendStringId(objectId);
		canvas.drawString(label, 1, centerX, bottomY, 3);
		let arrowHeight: number = SpriteRenderer.frameHeight(559, 0);
		let arrowY: number = ((bottomY - (arrowHeight >> 1)) | 0);
		let rightX: number = ((canvas.getWidth() - 3) | 0);
		this.leftArrowAnim.draw(gfx, 3, arrowY);
		this.rightArrowAnim.draw(gfx, rightX, arrowY);
	}
	drawMenuList_2(gfx: Graphics, posX: number, posY: number): void {
		let canvas: GameCanvas = this.canvas;
		SpriteRenderer.drawFrame(gfx, 653, 0, posX, posY);
		let hitRect: Int32Array = this.frameBounds;
		SpriteRenderer.hitTest(this.frameBounds, 653, 0, 0);
		let label: SdkString = canvas.beginString();
		canvas.appendInt(((this.objectOptionIds[2] + 1) | 0));
		canvas.drawString(label, 0, ((posX + hitRect[0]) | 0), ((posY + hitRect[1]) | 0), 17);
	}
	setMenuPage(page: number): void {
		let lot: Lot = this.currentLot;
		switch (page) {
			case 0:
				this.onTick(5);
				break;
			case 1:
				this.followFlaggedEntity(2);
				break;
			case 2:
				if (this.menuPage !== 7 && this.menuPage !== 8 && this.menuPage !== 3 && this.menuPage !== 4 && this.menuPage !== 6) {
					this.showObjectDetails();
				}
				break;
			case 3:
				this.showInfoPanel(749, 748);
				break;
			case 4:
				if (this.menuPage !== 5) {
					this.followFlaggedEntity(4);
					this.followNearestEntity();
				}
				break;
			case 5:
				let objectId: number = lot.spriteIdAt(this.selectedType);
				this.showInfoPanel(752, objectId);
				break;
			case 6:
				this.keyRepeatTime = 0;
				break;
			case 7:
				let secondValue: number = lot.spriteIdAt(this.selectedType);
				this.showInfoPanel(750, secondValue);
				break;
			case 8:
				let value: number = lot.spriteIdAt(this.selectedType);
				this.showInfoPanel(751, value);
				break;
			case 9:
				let textId: number = this.followTarget.typeAnimId();
				this.showInfoPanel(753, textId);
				break;
			case 10:
				if (lot.stageReward() <= this.neighborhood.money) {
					this.showInfoPanel(772, 771);
				}
				else {
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
	handleMenuPageInput(keyState: number, actionMask: number): void {
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
	updateMenuPageAnim(delta: number): void {
		switch (this.menuPage) {
			case 1:
			case 4:
			case 6:
			case 11:
				this.advanceNotification(delta);
				this.updatePlayerAnimator(delta);
				this.updateCameraPan(delta);
				this.advanceEntitiesOfType(delta, 8);
				if (this.menuPage === 6 && this.keyRepeatTime > 1500) {
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
	drawMenuPage(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
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
				let frameHeight: number = 0;
				let option: number = 0;
				let jobId: number = 0;
				if (this.menuPage === 5) {
					frameHeight = this.currentLot.spriteIdAt(this.selectedType);
					option = 709;
					jobId = this.currentLot.objectIdsAt(this.selectedType);
				}
				else {
					frameHeight = this.currentLot.spriteIdAt(this.followTarget.type);
					option = 710;
					jobId = this.currentLot.altIdAt(this.followTarget.type);
				}
				this.setValue(1, frameHeight);
				this.setValue(2, option);
				let label: SdkString = canvas.beginString();
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
	handlePage1Input(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 0, 4)) {
			this.onTick(5);
		}
		this.handleInputBits(keyState);
		if (this.matchesInput(keyState, actionMask, 4128, 16)) {
			this.useCursorTarget();
		}
	}
	handleBuyPageInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 0, 4)) {
			this.onTick(5);
		}
		else {
			if (this.matchesInput(keyState, actionMask, 4128, 16)) {
				let objectType: number = this.selectedIndex;
				let objectId: number = this.currentLot.indexOfItem(objectType);
				this.selectedType = objectId;
				if (!this.currentLot.isStageItemAllowed(objectId)) {
					this.setMenuPage(8);
				}
				else {
					let price: number = this.currentLot.objectIdsAt(this.selectedType);
					if (this.neighborhood.money >= price) {
						if (this.currentLot.spawnWalkers(this.selectedType) > 0) {
							this.setMenuPage(4);
						}
						else {
							this.setMenuPage(3);
						}
					}
					else {
						this.setMenuPage(7);
					}
				}
			}
			this.scrollList(keyState, actionMask);
		}
	}
	drawBuyPage(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let lot: Lot = this.currentLot;
		let objectType: number = this.currentLot.indexOfItem(this.selectedIndex);
		if (!lot.isStageItemAllowed(objectType)) {
			this.setValue(1, 711);
		}
		else {
			let label: SdkString = canvas.beginString();
			canvas.formatNumber(lot.objectIdsAt(objectType));
			canvas.setString(-9, label);
			this.setValue(1, -9);
		}
		this.drawPanel(gfx, 23);
		this.drawSoftKeyHint(gfx);
	}
	drawMenuIcon(gfx: Graphics, hitRect: Int32Array): void {
		let slotWidth: number = 0;
		let iconWidth: number = ((Math.imul((slotWidth = hitRect[2]), 3) / 5) | 0);
		if (gfx !== null) {
			let iconX: number = ((((hitRect[0] + slotWidth) | 0) - iconWidth) | 0);
			let iconY: number = hitRect[1];
			let iconHeight: number = hitRect[3];
			this.drawRaisedBox(gfx, iconX, iconY, iconWidth, iconHeight);
			let lot: Lot = this.currentLot;
			let objectIndex: number = this.currentLot.indexOfItem(this.selectedIndex);
			let objectType: number = lot.subTypeAt(objectIndex);
			let objectId: number = lot.attachmentAt(objectIndex, 0);
			if (objectType === 196 && objectId !== 0 && (objectType = lot.frameDataAt(objectId, 1)) === 196) {
				objectType = lot.frameDataAt(objectId, 2);
			}
			let frameX: number = SpriteRenderer.frameMinX(objectType, 0);
			let frameY: number = SpriteRenderer.frameMinY(objectType, 0);
			let frameWidth: number = SpriteRenderer.frameWidth(objectType, 0);
			let frameHeight: number = SpriteRenderer.frameHeight(objectType, 0);
			let drawX: number = ((((((iconX + (iconWidth >> 1)) | 0) - frameX) | 0) - (frameWidth >> 1)) | 0);
			let drawY: number = ((((((iconY + (iconHeight >> 1)) | 0) - frameY) | 0) - (frameHeight >> 1)) | 0);
			SpriteRenderer.drawFrame(gfx, objectType, 0, drawX, drawY);
		}
		hitRect[2] = ((((slotWidth - iconWidth) | 0) - 3) | 0);
	}
	showObjectDetails(): void {
		this.beginList(this.currentLot.itemCount(), 15, 0);
	}
	drawObjectListRow(gfx: Graphics, posX: number, posY: number, width: number, height: number): void {
		let canvas: GameCanvas = this.canvas;
		let lot: Lot = this.currentLot;
		let iconX: number = ((posX + 3) | 0);
		let rowY: number = posY;
		let rowIndex: number = this.scrollOffset;
		let selectedRow: number = this.selectedIndex;
		while (rowY < ((posY + height) | 0) && rowIndex < this.itemCount) {
			let objectIndex: number = lot.indexOfItem(rowIndex);
			let objectType: number = lot.spriteIdAt(objectIndex);
			let selected: number = 1;
			if (rowIndex === selectedRow) {
				this.drawClippedRow(gfx, posX, rowY, width, 15, ((posY + height) | 0), 82, rowIndex);
				selected = 0;
			}
			canvas.drawStringById(objectType, selected, iconX, ((rowY + 7) | 0), 6);
			rowY = ((rowY + 15) | 0);
			rowIndex++;
			BaseScreen.drawHLine(gfx, posX, rowY, width);
		}
	}
	handlePlaceObjectInput(keyState: number, actionMask: number): void {
		this.handleInputBits(keyState);
		let placement: Entity = this.pendingPlacement;
		if (this.matchesInput(keyState, actionMask, 0, 4)) {
			this.clearBrokenEntities();
			if (this.menuPage === 11) {
				this.clearBrokenEntities();
				placement.updateFootprint(true);
				placement.clearBusy();
				this.pendingPlacement = null;
				this.setMenuPage(1);
			}
			else {
				this.setMenuPage(2);
			}
		}
		else {
			if (this.matchesInput(keyState, actionMask, 4128, 2048) && this.followTarget !== null) {
				if (this.menuPage === 11) {
					let target: Entity = this.followTarget;
					if (this.followTarget !== null) {
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
	handleBuyObjectInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 4128, 256)) {
			let target: Entity = this.followTarget;
			if (this.followTarget !== null) {
				let neighborhood: Neighborhood = this.neighborhood;
				let lot: Lot = this.currentLot;
				let price: number = this.currentLot.objectIdsAt(this.selectedType);
				this.createObjectWithParam(target.posX, target.posY, price);
				neighborhood.addMoney(((price * -1) | 0));
				let extra: number = lot.addObject(this.selectedType, target.posX, target.posY, target.facing);
				this.clearBrokenEntities();
				let newEntity: Entity = this.createEntity(this.selectedType, target.posX, target.posY, target.facing, extra);
				this.followEntity(newEntity);
				neighborhood.onItemPlaced(this.selectedType);
				this.setMenuPage(6);
			}
		}
		else {
			if (this.matchesInput(keyState, actionMask, 0, 516)) {
				this.setMenuPage(4);
			}
		}
	}
	handleSellObjectInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 4128, 256)) {
			let target: Entity = this.followTarget;
			let objectType: number = this.followTarget.type;
			let hitRect: Int32Array = this.frameBounds;
			this.followTarget.computePosition(hitRect);
			this.currentLot.removeObject(this.followTarget.variant);
			this.cancelQueuedInteraction(this.followTarget);
			this.followTarget.despawn();
			this.followEntity((null as unknown as Entity));
			this.movingToTarget = true;
			this.camTargetX = hitRect[0];
			this.camTargetY = hitRect[1];
			let price: number = this.currentLot.altIdAt(objectType);
			this.neighborhood.addMoney(price);
			this.setMenuPage(1);
		}
		else {
			if (this.matchesInput(keyState, actionMask, 0, 516)) {
				this.setMenuPage(1);
			}
		}
	}
	handleInventoryInput(keyState: number, actionMask: number): void {
		let row: number = 0;
		let objectType: number = 0;
		let count: number = (objectType = ((row = this.canvas.leftSoftKeyAction | this.canvas.rightSoftKeyAction) & 8) !== 0 ? 4128 : 0) === 0 ? 4128 : 0;
		if (this.matchesInput(keyState, actionMask, objectType, 524)) {
			this.onTick(5);
		}
		if (this.matchesInput(keyState, actionMask, count, 256)) {
			this.neighborhood.addMoney(((this.currentLot.stageReward() * -1) | 0));
			this.currentLot.nextStage();
			this.setMenuPage(0);
			this.syncFadeWithAction();
		}
	}
	drawInventoryPage(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		this.setValue(1, 771);
		this.setValue(2, 776);
		let label: SdkString = canvas.beginString();
		canvas.formatNumber(this.currentLot.stageReward());
		canvas.setString(-9, label);
		this.setValue(3, -9);
		this.drawGameLayer(gfx);
		canvas.drawDialogOverlay(gfx);
		this.drawPanel(gfx, 20);
		this.drawSoftKeyHint(gfx);
	}
	setOptionsTab(tab: number): void {
		switch (tab) {
			case 0:
				if (this.optionsTab !== 9) {
					this.rosterIndex = 0;
				}
				this.showRosterPage();
			case 1:
			case 2:
			default:
				break;
			case 3:
				let textId: number = 544;
				if (this.neighborhood.pendingAchievementId === -1) {
					textId = 547;
				}
				else {
					if (!this.neighborhood.hasOpenAchievementSlot()) {
						textId = 545;
					}
				}
				this.canvas.setWrappedString(textId, 1, this.panelHeight);
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
					let frame: number = -1;
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
					if (frame !== -1) {
						GameCanvas.selectListValue(this.actionOptionIds, frame);
					}
				}
				break;
			case 9:
				this.rosterIndex = this.selectedIndex;
				this.refreshOptionsPreview();
				break;
			case 10:
				this.openObjectsDialog();
		}
		this.optionsTab = tab;
		this.updateInputMasks();
	}
	refreshOptionsPreview(): void {
		let simIndex: number = 0;
		if ((simIndex = this.currentSimId()) !== this.shownSimId) {
			this.shownSimId = simIndex;
			let canvas: GameCanvas = this.canvas;
			let zoom: number = 98304;
			canvas.unloadTiles(zoom, 1);
			zoom = this.neighborhood.simAttribute(simIndex, 0) === 0 ? 32768 : 65536;
			this.currentLot.buildPaletteFor(simIndex, this.recolorSource, this.recolorTarget);
			canvas.loadTiles(zoom, this.recolorSource, this.recolorTarget, 1);
		}
	}
	handleOptionsInput(keyState: number, actionMask: number): void {
		let neighborhood: Neighborhood = this.neighborhood;
		if (this.matchesInput(keyState, actionMask, 3072, 0)) {
			this.onTick(5);
		}
		else {
			if (this.optionsTab === 9) {
				if (this.matchesInput(keyState, actionMask, 0, 4)) {
					this.setOptionsTab(0);
					return;
				}
				let tabCount: number = neighborhood.idleSimCount();
				if (!this.matchesInput(keyState, actionMask, 32784, 0)) {
					if (this.matchesInput(keyState, actionMask, 65600, 0)) {
						this.rosterIndex = (((((this.rosterIndex + 1) | 0)) % tabCount) | 0);
						this.showRightArrow();
						this.refreshOptionsPreview();
					}
					return;
				}
				this.rosterIndex = (((((((this.rosterIndex + tabCount) | 0) - 1) | 0)) % tabCount) | 0);
				this.showLeftArrow();
				this.refreshOptionsPreview();
			}
			else {
				if (this.optionsTab === 10) {
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
						if (this.itemCount > 0 && this.matchesInput(keyState, actionMask, 4128, 16)) {
							this.setOptionsTab(9);
							return;
						}
					case 1:
					case 2:
					default:
						break;
					case 3:
						this.scrollList(keyState, actionMask);
						if (neighborhood.pendingAchievementId !== -1 && neighborhood.hasOpenAchievementSlot() && this.matchesInput(keyState, actionMask, 4128, 0)) {
							neighborhood.assignAchievement();
							this.canvas.setWrappedString(546, 1, this.panelHeight);
						}
						if (this.canvas.cheatToggled && this.matchesInput(keyState, actionMask, 1, 0)) {
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
						let index: number = 0;
						if ((index = this.getSelectedObjectIndex()) !== -1 && (this.currentLot.categoryMaskAt(index) & 2048) !== 0 && this.matchesInput(keyState, actionMask, 4128, 16)) {
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
	advanceMenuArrows(delta: number): void {
		this.advanceArrows(delta);
	}
	drawOptionsPage(gfx: Graphics): void {
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
	showRosterPage(): void {
		this.beginList(this.neighborhood.idleSimCount(), 16, this.rosterIndex);
	}
	drawRosterList(gfx: Graphics, posX: number, posY: number, width: number, height: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		if (this.itemCount === 0) {
			let emptyX: number = ((posX + (width >> 1)) | 0);
			let emptyY: number = ((posY + 3) | 0);
			canvas.drawStringById(664, 1, emptyX, emptyY, 17);
		}
		else {
			if (this.nameColumnWidth === 0) {
				let maxNameWidth: number = 0;
				let simCount: number = neighborhood.simCount;
				for (let simIndex: number = 1; simIndex < simCount; simIndex++) {
					let nameTextId: number = neighborhood.simNameText(simIndex);
					let textWidth: number = 0;
					if ((textWidth = canvas.stringWidthById(nameTextId, 1)) > maxNameWidth) {
						maxNameWidth = textWidth;
					}
				}
				this.nameColumnWidth = maxNameWidth;
			}
			let valueWidth: number = ((((width - this.nameColumnWidth) | 0) - 9) | 0);
			let valueX: number = ((((((posX + width) | 0) - valueWidth) | 0) - 3) | 0);
			let nameX: number = ((posX + 3) | 0);
			let rowY: number = posY;
			let rowIndex: number = this.scrollOffset;
			let selectedRow: number = this.selectedIndex;
			while (rowY < ((posY + height) | 0) && rowIndex < this.itemCount) {
				let simId: number = neighborhood.countSimsInState(rowIndex);
				let nameId: number = neighborhood.simNameText(simId);
				if (rowIndex === selectedRow) {
					this.drawClippedRow(gfx, posX, rowY, width, 16, ((posY + height) | 0), 82, rowIndex);
				}
				canvas.drawStringById(nameId, 1, nameX, ((rowY + 5) | 0), 20);
				let value: number = neighborhood.simState(simId);
				let scale: number = 65536;
				this.drawTwoToneBar(gfx, valueX, ((rowY + 2) | 0), valueWidth, value, scale, true);
				rowY = ((rowY + 16) | 0);
				rowIndex++;
				BaseScreen.drawHLine(gfx, posX, rowY, width);
			}
		}
	}
	drawPersonDetails(gfx: Graphics, useSelection: boolean): void {
		let neighborhood: Neighborhood = this.neighborhood;
		let simId: number = 0;
		let level: number = 0;
		let backgroundFrame: number = 0;
		if (useSelection) {
			backgroundFrame = 8;
			simId = this.friendSimId;
			level = this.friendLevel;
		}
		else {
			if ((simId = neighborhood.simLot(0)) === -1) {
				backgroundFrame = 5;
			}
			else {
				backgroundFrame = 6;
				level = neighborhood.simHour(0);
			}
		}
		let labelFrame: number = 115;
		if (simId === -1) {
			if (useSelection) {
				labelFrame = 628;
			}
		}
		else {
			let canvas: GameCanvas = this.canvas;
			if (useSelection) {
				labelFrame = simId === neighborhood.simLot(0) ? 630 : 629;
			}
			this.setValue(1, labelFrame);
			this.setValue(2, neighborhood.lotNameText(simId));
			this.setValue(3, neighborhood.lotHourInteraction(simId, level));
			let value: number = neighborhood.lotHourIncome(simId, level);
			let label: SdkString = canvas.beginString();
			canvas.formatNumber(value);
			canvas.setString(-9, label);
			this.setValue(4, -9);
			let traitTextId: number = neighborhood.lotOccupantType(simId);
			this.setValue(5, this.currentLot.spriteIdAt(traitTextId));
			let traitId: number = neighborhood.lotKind(simId);
			this.setValue(6, neighborhood.simNameText(traitId));
			let mask: number = neighborhood.lotHourDays(simId, level);
			label = canvas.beginString();
			let separator: SdkString = canvas.getString(27);
			let traitCount: number = 0;
			for (let index: number = 0; index < Neighborhood.WEEKDAY_BITS.length; index++) {
				if ((mask & Neighborhood.WEEKDAY_BITS[index]) !== 0) {
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
			let moodId: number = neighborhood.lotHourStart(simId, level);
			canvas.appendTime(moodId);
			canvas.appendStringId(662);
			let maskBit: number = neighborhood.lotHourEnd(simId, level);
			canvas.appendTime(maskBit);
			canvas.setString(-7, label);
			this.setValue(8, -7);
			if (!useSelection) {
				label = canvas.beginString();
				let secondTraitCount: number = 0;
				for (let secondIndex: number = 0; secondIndex < Neighborhood.FEATURE_FLAGS.length; secondIndex++) {
					if ((mask & Neighborhood.FEATURE_FLAGS[secondIndex]) !== 0) {
						canvas.appendStringId(Neighborhood.FEATURE_TEXT_IDS[secondIndex]);
						secondTraitCount++;
					}
				}
				if (secondTraitCount > 0) {
					canvas.setString(-6, label);
					this.setValue(9, -6);
				}
				else {
					this.setValue(9, 664);
				}
			}
		}
		this.setValue(1, labelFrame);
		this.drawPanel(gfx, backgroundFrame);
	}
	drawSkillRow(gfx: Graphics, posX: number, posY: number, width: number, skillId: number): number {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		if (this.listTextWidth === 0) {
			let skillCount: number = neighborhood.residentCount();
			let maxWidth: number = 0;
			for (let skillIndex: number = 0; skillIndex < skillCount; skillIndex++) {
				let textId: number = neighborhood.residentId(skillIndex);
				let textWidth: number = 0;
				if ((textWidth = canvas.stringWidthById(textId, 1)) > maxWidth) {
					maxWidth = textWidth;
				}
			}
			this.listTextWidth = maxWidth;
		}
		if (gfx !== null) {
			let labelId: number = neighborhood.residentId(skillId);
			let value: number = neighborhood.careerPoints(skillId);
			let unitId: number = neighborhood.residentLevelValue(skillId);
			let barWidth: number = ((((width - this.listTextWidth) | 0) - 3) | 0);
			let barX: number = ((((posX + width) | 0) - barWidth) | 0);
			canvas.drawStringById(labelId, 1, posX, ((posY + 1) | 0), 20);
			GameScreen.drawProgressBar(gfx, barX, ((posY + 1) | 0), barWidth, value);
			posY = ((posY + this.rowHeight) | 0);
			canvas.drawStringById(unitId, 1, barX, ((posY + 1) | 0), 20);
		}
		return this.rowHeight << 1;
	}
	static drawProgressBar(gfx: Graphics, posX: number, posY: number, width: number, value: number): void {
		let normalized: number = ((value - 0) | 0) >> 10;
		let innerWidth: number = 0;
		let filledWidth: number = ((Math.imul((innerWidth = ((width - 2) | 0)), normalized) / 320) | 0);
		let barHeight: number = SpriteRenderer.frameHeight(657, 0);
		let innerX: number = ((posX + 1) | 0);
		let innerY: number = ((posY + 1) | 0);
		let outerHeight: number = ((barHeight + 2) | 0);
		SpriteRenderer.setColor(gfx, 77);
		gfx.drawRect(posX, posY, ((width - 1) | 0), ((outerHeight - 1) | 0));
		let savedClipX: number = gfx.getClipX();
		let savedClipY: number = gfx.getClipY();
		let savedClipWidth: number = gfx.getClipWidth();
		let savedClipHeight: number = gfx.getClipHeight();
		gfx.setClip(innerX, savedClipY, innerWidth, savedClipHeight);
		SpriteRenderer.drawFrame(gfx, 657, 0, innerX, innerY);
		gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
		SpriteRenderer.setColor(gfx, 77);
		for (let tick: number = 1; tick < 5; tick++) {
			let tickX: number = ((posX + ((Math.imul(width, tick) / 5) | 0)) | 0);
			gfx.fillRect(tickX, innerY, 1, barHeight);
		}
		gfx.setClip(innerX, savedClipY, filledWidth, savedClipHeight);
		SpriteRenderer.drawFrame(gfx, 658, 0, innerX, innerY);
		SpriteRenderer.setColor(gfx, 76);
		for (let secondTick: number = 1; secondTick < 5; secondTick++) {
			let secondTickX: number = ((posX + ((Math.imul(width, secondTick) / 5) | 0)) | 0);
			gfx.fillRect(secondTickX, innerY, 1, barHeight);
		}
		gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
	}
	showSkillsPage(): void {
		this.beginList(4, 27, -1);
	}
	drawSkillHeader(gfx: Graphics, posX: number, posY: number, width: number): number {
		if (gfx !== null) {
			let iconX: number = ((posX + 10) | 0);
			let iconHeight: number = SpriteRenderer.frameHeight(565, 0);
			let iconY: number = ((13 - (iconHeight >> 1)) | 0);
			let textWidth: number = ((width - 30) | 0);
			let textX: number = ((((posX + width) | 0) - textWidth) | 0);
			let neighborhood: Neighborhood = this.neighborhood;
			let jobId: number = this.neighborhood.pendingAchievementId;
			let labelId: number = neighborhood.achievementText(jobId);
			let frameId: number = 589;
			if (jobId === -1) {
				frameId = 196;
			}
			this.canvas.drawSlotText(19, labelId, 1, textWidth, textX, ((((posY + 13) | 0) + 2) | 0), 6);
			SpriteRenderer.drawFrame(gfx, frameId, 0, iconX, ((posY + iconY) | 0));
		}
		return 30;
	}
	drawSkillsPage(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		this.setValue(1, neighborhood.moodTextId());
		let jobId: number = neighborhood.dayNumber();
		let label: SdkString = canvas.beginString();
		canvas.appendInt(jobId);
		canvas.setString(-9, label);
		this.setValue(2, -9);
		label = canvas.beginString();
		canvas.formatNumber(neighborhood.moneySpent);
		canvas.setString(-8, label);
		this.setValue(3, -8);
		this.drawPanel(gfx, 10);
	}
	showCareerPage(): void {
		this.beginList(this.neighborhood.needCount, 17, -1);
	}
	drawCareerList(gfx: Graphics, posX: number, posY: number, width: number, height: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.canvas.neighborhood;
		let entryCount: number = this.canvas.neighborhood.needCount;
		if (this.listValueWidth === 0) {
			let maxWidth: number = 0;
			for (let entryIndex: number = 0; entryIndex < entryCount; entryIndex++) {
				let textId: number = neighborhood.needId(entryIndex);
				let textWidth: number = 0;
				if ((textWidth = canvas.stringWidthById(textId, 1)) > maxWidth) {
					maxWidth = textWidth;
				}
			}
			this.listValueWidth = maxWidth;
		}
		let nameX: number = ((posX + 3) | 0);
		let valueWidth: number = ((((width - this.listValueWidth) | 0) - 9) | 0);
		let valueX: number = ((((((posX + width) | 0) - valueWidth) | 0) - 3) | 0);
		let rowY: number = posY;
		let rowIndex: number = this.scrollOffset;
		while (rowY < ((posY + height) | 0) && rowIndex < this.itemCount) {
			let nameId: number = neighborhood.needId(rowIndex);
			canvas.drawStringById(nameId, 1, nameX, ((rowY + 5) | 0), 20);
			let value: number = neighborhood.needValue(rowIndex);
			this.drawGauge(gfx, valueX, ((rowY + 5) | 0), valueWidth, value);
			rowY = ((rowY + 17) | 0);
			rowIndex++;
			BaseScreen.drawHLine(gfx, posX, rowY, width);
		}
	}
	showFriendsPage(): void {
		this.beginList(this.neighborhood.selectedResidentFrame(), 27, -1);
	}
	drawFriendsPage(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let label: SdkString = this.canvas.profileName();
		canvas.setString(-9, label);
		this.setValue(1, -9);
		let friendCount: number = this.neighborhood.residentLabelText(this.neighborhood.selectedResident);
		this.setValue(2, friendCount);
		this.drawPanel(gfx, 16);
	}
	showObjectsPage(): void {
		let selectedRow: number = 0;
		if (this.optionsTab === 10) {
			selectedRow = this.objectRow;
		}
		this.beginList(this.neighborhood.ownedItemCount(), 18, selectedRow);
	}
	openObjectsDialog(): void {
		this.objectRow = this.selectedIndex;
		this.objectIndex = this.currentLot.indexOfRef(this.getSelectedObjectIndex());
		this.beginList(this.currentLot.listSize(this.objectIndex), 18, -1);
	}
	getSelectedObjectIndex(): number {
		return this.neighborhood.itemCountOf(this.selectedIndex);
	}
	drawObjectRows(gfx: Graphics, posX: number, posY: number, width: number, height: number, kind: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		let lot: Lot = this.currentLot;
		let iconX: number = ((((posX + 3) | 0) + 8) | 0);
		let iconRight: number = 0;
		let textX: number = (((iconRight = ((((((posX + 3) | 0) + 16) | 0) + 3) | 0)) + 3) | 0);
		let valueX: number = 0;
		let dividerX: number = (((valueX = ((((posX + width) | 0) - 3) | 0)) - 25) | 0);
		SpriteRenderer.setColor(gfx, 75);
		gfx.fillRect(iconRight, posY, 1, height);
		gfx.fillRect(dividerX, posY, 1, height);
		let rowY: number = posY;
		let rowIndex: number = this.scrollOffset;
		let selectedRow: number = this.selectedIndex;
		while (rowY < ((posY + height) | 0) && rowIndex < this.itemCount) {
			let objectId: number = 0;
			if (kind === 5) {
				objectId = neighborhood.itemCountOf(rowIndex);
			}
			else {
				if (kind === 9) {
					objectId = lot.bitForSound(this.targetObjectType, rowIndex);
				}
				else {
					objectId = lot.listValueAt(this.objectIndex, rowIndex);
				}
			}
			let iconId: number = lot.categoryIdAt(objectId);
			let frameId: number = lot.categoryValueAt(objectId);
			if (rowIndex === selectedRow) {
				this.drawClippedRow(gfx, posX, rowY, width, 18, ((posY + height) | 0), 82, rowIndex);
			}
			SpriteRenderer.drawFrame(gfx, frameId, 0, iconX, ((rowY + 9) | 0));
			canvas.drawStringById(iconId, 1, textX, ((rowY + 9) | 0), 6);
			let value: number = 0;
			if ((value = neighborhood.itemCount(objectId)) > 0 || kind === 6) {
				let label: SdkString = canvas.beginString();
				canvas.appendInt(value);
				canvas.appendStringId(27);
				if (kind === 6) {
					canvas.appendInt(1);
				}
				else {
					canvas.appendInt(lot.categoryFlagAt(objectId));
				}
				canvas.drawString(label, 1, valueX, ((rowY + 9) | 0), 10);
			}
			rowY = ((rowY + 18) | 0);
			rowIndex++;
			BaseScreen.drawHLine(gfx, posX, rowY, width);
		}
	}
	drawObjectPage(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let lot: Lot = this.currentLot;
		SpriteRenderer.setColor(gfx, 77);
		gfx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		let nextY: number = this.drawIconRow(gfx, 4, ((canvas.getWidth() - 8) | 0), 122);
		let objectIndex: number = lot.refAt(this.objectIndex);
		let iconId: number = lot.categoryIdAt(objectIndex);
		this.setValue(1, iconId);
		this.drawPanelBelow(gfx, 13, nextY);
	}
	currentSimId(): number {
		return this.neighborhood.countSimsInState(this.rosterIndex);
	}
	drawSimDetailPage(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		let simId: number = this.currentSimId();
		SpriteRenderer.setColor(gfx, 77);
		gfx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		let nextY: number = this.drawIconRow(gfx, 4, ((canvas.getWidth() - 8) | 0), 116);
		this.setValue(1, neighborhood.simNameText(simId));
		let partnerId: number = 0;
		if ((partnerId = neighborhood.simPortrait(simId)) === -1) {
			this.setValue(2, 24);
			this.setValue(3, 24);
		}
		else {
			this.setValue(2, 645);
			this.setValue(3, neighborhood.simNameText(partnerId));
		}
		let jobId: number = neighborhood.simLot(simId);
		let jobNameId: number = neighborhood.lotNameText(jobId);
		this.setValue(4, jobNameId);
		this.drawPanelBelow(gfx, 15, nextY);
	}
	drawFieldRow(gfx: Graphics, posX: number, posY: number, width: number): number {
		if (gfx !== null) {
			let neighborhood: Neighborhood = this.neighborhood;
			let simId: number = this.currentSimId();
			let value: number = neighborhood.simState(simId);
			this.drawTwoToneBar(gfx, posX, posY, width, value, 65536, true);
			let rowY: number = ((posY + 15) | 0);
			let hitRect: Int32Array = this.frameBounds;
			neighborhood.collectNeedChanges(simId, hitRect);
			for (let index: number = 0; index < 4 && (value = hitRect[index]) !== -1; index = (((index + 2) | 0) << 24 >> 24)) {
				let scale: number = hitRect[((index + 1) | 0)];
				rowY = ((rowY + 3) | 0);
				this.drawTwoToneBar(gfx, posX, rowY, width, value, scale, false);
				rowY = ((rowY + 15) | 0);
			}
		}
		return 51;
	}
	drawScrollBar(gfx: Graphics, posX: number, posY: number, width: number, height: number): void {
		let simId: number = this.currentSimId();
		let barX: number = ((((posX + width) | 0) - 20) | 0);
		let barY: number = ((((posY + height) | 0) - 1) | 0);
		this.drawIcon(gfx, barX, barY, simId, true, 1);
	}
	drawScrollHint(gfx: Graphics, posX: number, posY: number, width: number, height: number): void {
		let barX: number = ((((posX + width) | 0) - 20) | 0);
		let barY: number = ((((posY + height) | 0) - 1) | 0);
		this.drawIcon(gfx, barX, barY, 0, true, 0);
	}
	static drawBorderedRect(gfx: Graphics, posX: number, posY: number, width: number, value: number, leftFrame: number, rightFrame: number, middleFrame: number, frameIndex: number): void {
		let savedClipX: number = gfx.getClipX();
		let savedClipY: number = gfx.getClipY();
		let savedClipWidth: number = gfx.getClipWidth();
		let savedClipHeight: number = gfx.getClipHeight();
		let scaledWidth: number = FixedMath.mul(width, value);
		let leftWidth: number = SpriteRenderer.frameWidth(leftFrame, frameIndex);
		let rightWidth: number = SpriteRenderer.frameWidth(rightFrame, frameIndex);
		let middleWidth: number = Math.min(((((width - rightWidth) | 0) - leftWidth) | 0), ((scaledWidth - leftWidth) | 0));
		gfx.setClip(posX, savedClipY, scaledWidth, savedClipHeight);
		SpriteRenderer.drawFrame(gfx, leftFrame, frameIndex, posX, posY);
		SpriteRenderer.drawFrame(gfx, rightFrame, frameIndex, ((((posX + width) | 0) - rightWidth) | 0), posY);
		gfx.setClip(((posX + leftWidth) | 0), savedClipY, middleWidth, savedClipHeight);
		SpriteRenderer.drawFrame(gfx, middleFrame, frameIndex, ((posX + leftWidth) | 0), posY);
		gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
	}
	drawGauge(gfx: Graphics, posX: number, posY: number, width: number, value: number): void {
		GameScreen.drawBorderedRect(gfx, posX, posY, width, 65536, 664, 665, 663, 0);
		let frameIndex: number = value < 2293760 ? 2 : 1;
		value = FixedMath.div(value, 6553600);
		GameScreen.drawBorderedRect(gfx, posX, posY, width, value, 664, 665, 663, frameIndex);
	}
	drawTwoToneBar(gfx: Graphics, posX: number, posY: number, width: number, value: number, secondValue: number, firstTone: boolean): void {
		width++;
		let mask: number = 0;
		let frameIndex: number = ((mask = this.neighborhood.stateFlagsOf(value)) & 2) !== 0 ? 1 : (mask & 1) !== 0 ? 3 : 2;
		let firstFrame: number = firstTone ? frameIndex : 0;
		GameScreen.drawBorderedRect(gfx, posX, posY, width, 65536, 655, 656, 654, firstFrame);
		if (!firstTone) {
			let secondFrame: number = ((frameIndex + 3) | 0);
			GameScreen.drawBorderedRect(gfx, posX, posY, width, secondValue, 655, 656, 654, secondFrame);
		}
		let iconId: number = this.neighborhood.stateSprite(value);
		let centerX: number = ((posX + (width >> 1)) | 0);
		let centerY: number = ((posY + 0) | 0);
		let align: number = firstTone ? 0 : 1;
		this.canvas.drawStringById(iconId, align, centerX, centerY, 17);
	}
	selectFriend(simId: number, level: number, silent: boolean): void {
		this.closeObjectMenu();
		this.friendSimId = simId;
		this.friendLevel = level;
		if (simId === -1) {
			this.showInfoPanel(552, 628);
		}
		else {
			if (!silent && !this.neighborhood.canEnterLot(simId, level)) {
				this.selectRequest(simId, level);
				return;
			}
			let currentFriendId: number = 0;
			let frameId: number = (currentFriendId = this.neighborhood.simLot(0)) === -1 ? 631 : currentFriendId === simId ? 633 : 632;
			this.canvas.setWrappedString(frameId, 1, this.dialogWidth);
		}
		this.onTick(10);
	}
	handleFriendPageInput(keyState: number, actionMask: number): void {
		let primaryMask: number = 0;
		let secondaryMask: number = (primaryMask = this.friendSimId === -1 ? 4128 : 0) === 0 ? 4128 : 0;
		if (this.matchesInput(keyState, actionMask, primaryMask, 524)) {
			this.onTick(5);
		}
		else {
			if (this.matchesInput(keyState, actionMask, secondaryMask, 256)) {
				let neighborhood: Neighborhood = this.neighborhood;
				let currentFriendId: number = this.neighborhood.simLot(0);
				let currentLevel: number = neighborhood.simHour(0);
				let newFriendId: number = this.friendSimId;
				let newLevel: number = this.friendLevel;
				neighborhood.selectLocation(newFriendId, newLevel);
				if (currentFriendId !== newFriendId && newFriendId !== -1) {
					neighborhood.unlockAchievement(20);
				}
				if (currentFriendId === newFriendId && currentLevel < newLevel) {
					neighborhood.unlockAchievement(54);
				}
				if (newLevel === ((neighborhood.lotHourCount(newFriendId) - 1) | 0)) {
					neighborhood.unlockAchievement(55);
				}
			}
		}
	}
	drawFriendPanel(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let friendSimId: number = this.friendSimId;
		if (this.friendSimId !== -1) {
			this.drawPersonDetails(gfx, true);
		}
		else {
			this.drawGameLayer(gfx);
			canvas.drawDialogOverlay(gfx);
			this.drawPageOne(gfx);
		}
		this.drawSoftKeyHint(gfx);
	}
	openRequestsDialog(): void {
		if (this.canvas.randomPercent() < 5) {
			let simIndex: number = this.neighborhood.currentLotSprite();
			this.openStatsDialog(simIndex);
		}
		else {
			this.onTick(11);
		}
	}
	handleRequestPageInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 4128, 12)) {
			let neighborhood: Neighborhood = this.neighborhood;
			if (this.neighborhood.canAdvanceHour()) {
				let simId: number = neighborhood.simLot(0);
				let level: number = neighborhood.simHour(0);
				this.selectFriend(simId, ((level + 1) | 0), false);
				return;
			}
			if (this.canvas.randomPercent() < 15) {
				let goalId: number = this.neighborhood.pickRandomCard();
				this.openGoalPage(goalId);
				return;
			}
			this.onTick(5);
		}
	}
	drawRequestPage(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		this.setValue(1, 703);
		this.setValue(2, 648);
		let label: SdkString = canvas.beginString();
		canvas.formatNumber(this.requestAmount);
		canvas.setString(-9, label);
		this.setValue(3, -9);
		this.drawGameLayer(gfx);
		canvas.drawDialogOverlay(gfx);
		this.drawPanel(gfx, 20);
		this.drawSoftKeyHint(gfx);
	}
	selectRequest(simId: number, level: number): void {
		let neighborhood: Neighborhood = this.neighborhood;
		this.friendSimId = simId;
		this.friendLevel = level;
		let frameId: number = neighborhood.lotExtraSprite(simId);
		this.canvas.setWrappedString(frameId, 1, this.dialogWidth);
		this.onTick(12);
	}
	drawRequestDetails(gfx: Graphics): void {
		let neighborhood: Neighborhood = this.neighborhood;
		let canvas: GameCanvas = this.canvas;
		this.drawGameLayer(gfx);
		this.canvas.drawDialogOverlay(gfx);
		let simId: number = this.friendSimId;
		let level: number = this.friendLevel;
		let entries: Int8Array = null!;
		let firstId: number = (entries = neighborhood.lotHourRequirement(simId, level))[0];
		let firstValue: number = entries[1];
		let secondId: number = entries[2];
		let secondValue: number = entries[3];
		let nameId: number = neighborhood.simNameText(neighborhood.lotKind(simId));
		this.setValue(0, nameId);
		this.setValue(1, nameId);
		let rowCount: number = 1;
		if (firstId !== -1) {
			rowCount = 20;
			this.setValue(2, Neighborhood.statTextId(firstId));
			let label: SdkString = canvas.beginString();
			canvas.appendInt(neighborhood.statValue(firstId));
			canvas.appendStringId(27);
			canvas.appendInt(firstValue);
			canvas.setString(-9, label);
			this.setValue(3, -9);
		}
		if (secondId !== -1) {
			rowCount = 21;
			this.setValue(4, Neighborhood.statTextId(secondId));
			let secondLabel: SdkString = canvas.beginString();
			canvas.appendInt(neighborhood.statValue(secondId));
			canvas.appendStringId(27);
			canvas.appendInt(secondValue);
			canvas.setString(-8, secondLabel);
			this.setValue(5, -8);
		}
		this.drawPanel(gfx, rowCount);
		this.drawSoftKeyHint(gfx);
	}
	handleRequestChoiceInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 4128, 8)) {
			this.onTick(5);
		}
	}
	confirmRequest(): void {
		this.choiceMade = false;
		let neighborhood: Neighborhood = null!;
		let textId: number = (neighborhood = this.neighborhood).lotHourInteraction(neighborhood.simLot(0), neighborhood.simHour(0));
		this.showInfoPanel(705, textId);
		this.onTick(13);
	}
	handleRequestCancel(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 4128, 524)) {
			this.onTick(5);
		}
		else {
			if (this.matchesInput(keyState, actionMask, 0, 256)) {
				this.neighborhood.selectLocation(-1, 0);
				this.choiceMade = true;
				this.updateInputMasks();
				this.showInfoPanel(706, 363);
			}
		}
	}
	drawSimplePage(gfx: Graphics): void {
		this.drawGameLayer(gfx);
		this.canvas.drawDialogOverlay(gfx);
		this.drawPageOne(gfx);
		this.drawSoftKeyHint(gfx);
	}
	requestSleep(): void {
		this.pendingGoHome = true;
	}
	showRelationshipDialog(simIndex: number): void {
		this.showNotification(540, this.neighborhood.achievementText(simIndex));
	}
	goToSleep(): void {
		this.closeObjectMenu();
		this.canvas.vibrateShort();
		let frameId: number = 544;
		if (!this.neighborhood.hasOpenAchievementSlot()) {
			frameId = 545;
		}
		this.canvas.setWrappedString(frameId, 1, this.panelHeight);
		this.onTick(14);
	}
	drawRelationshipPage(gfx: Graphics): void {
		this.drawGameLayer(gfx);
		this.canvas.drawDialogOverlay(gfx);
		this.drawPanel(gfx, 24);
		this.drawSoftKeyHint(gfx);
	}
	handleRelationshipInput(keyState: number, actionMask: number): void {
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
	playSlotAnimator(slot: number): void {
		this.slotAnimators[slot].play(590, 16);
	}
	playSlotAnimator2(animId: number, slot: number): void {
		this.showNotification(541, this.neighborhood.achievementText(animId));
		this.activePlayer.queueAction_2(9, null, 0, 0);
		if (slot !== -1) {
			this.slotAnimators[slot].play(591, 16);
		}
	}
	requestNeighborhoodEvent(): void {
		this.pendingEvent = true;
	}
	showSimProfile(simId: number): void {
		this.showNotification(this.neighborhood.simNameText(simId), 537);
	}
	showMessageDialog(kind: number, firstArg: number, secondArg: number, textId: number): void {
		this.pendingDialogId = kind;
		this.dialogArg1 = firstArg;
		this.dialogArg2 = secondArg;
		if (this.neighborhood.itemCount(kind) === this.currentLot.categoryFlagAt(kind)) {
			textId = 717;
		}
		this.dialogText = textId;
	}
	drawMessagePage(gfx: Graphics): void {
		this.drawGameLayer(gfx);
		this.canvas.drawDialogOverlay(gfx);
		this.drawPanel(gfx, 25);
		this.drawSoftKeyHint(gfx);
	}
	handleMessageInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 4128, 8)) {
			this.pendingDialogId = -1;
			this.onTick(5);
		}
	}
	drawObjectIconRow(gfx: Graphics, posX: number, posY: number, width: number): number {
		if (gfx !== null) {
			let lot: Lot = this.currentLot;
			let canvas: GameCanvas = this.canvas;
			let objectIndex: number = this.pendingDialogId;
			let iconX: number = ((((posX + 3) | 0) + 8) | 0);
			let iconY: number = ((posY + 9) | 0);
			let textX: number = ((((((((posX + 3) | 0) + 16) | 0) + 3) | 0) + 3) | 0);
			let textY: number = ((posY + 9) | 0);
			let valueX: number = ((((posX + width) | 0) - 3) | 0);
			let iconId: number = lot.categoryValueAt(objectIndex);
			SpriteRenderer.drawFrame(gfx, iconId, 0, iconX, iconY);
			let label: SdkString = canvas.beginString();
			if (this.dialogArg1 !== 1) {
				canvas.appendInt(this.dialogArg1);
				canvas.appendStringId(718);
			}
			canvas.appendStringId(lot.categoryIdAt(objectIndex));
			this.canvas.drawString(label, 1, textX, textY, 6);
			label = canvas.beginString();
			canvas.appendInt(this.neighborhood.itemCount(objectIndex));
			canvas.appendStringId(27);
			canvas.appendInt(lot.categoryFlagAt(objectIndex));
			canvas.drawString(label, 1, valueX, textY, 10);
		}
		return 18;
	}
	isIdleTooLong(): boolean {
		return this.idleTimer > 20000;
	}
	resetIdleTimer(): void {
		this.idleTimer = 0;
		this.activePlayer.clearBehaviourFlags();
	}
	updateTownieAi(delta: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		this.aiTimer = ((this.aiTimer + delta) | 0);
		let interval: number = 3000;
		if (neighborhood.paused) {
			interval = 1500;
		}
		if (this.aiTimer > interval) {
			this.aiTimer = 0;
			let houseValue: number = this.currentLot.areaId();
			let simCount: number = neighborhood.simCount;
			for (let simIndex: number = 1; simIndex < simCount; simIndex++) {
				let aiFlags: number = neighborhood.lotStateNow(simIndex);
				let sim: Sim = null!;
				if ((sim = this.findSimByIndex(simIndex)) !== null) {
					if ((aiFlags & 7) !== 0) {
						sim.addBehaviourFlags(1);
					}
					if ((aiFlags & 16) !== 0) {
						if (houseValue === neighborhood.simHomeLotOf(simIndex)) {
							sim.addBehaviourFlags(4);
						}
						else {
							sim.addBehaviourFlags(256);
						}
					}
				}
				else {
					if ((aiFlags & 8) !== 0 && houseValue === neighborhood.simHomeLotOf(simIndex) && this.findFreePortraitSlot() !== -1) {
						let spawnPoint: Entity = this.findEntityByType(4);
						sim = (this.createEntityNear(1, spawnPoint, simIndex) as unknown as Sim);
						let actionFlags: number = (canvas.randomPercent() < 50 ? 2 : 0) | (canvas.randomPercent() < 50 ? 8 : 0) | (canvas.randomPercent() < 50 ? 64 : 0) | (canvas.randomPercent() < 50 ? 32 : 0) | (canvas.randomPercent() < 50 ? 16 : 0);
						sim.addBehaviourFlags(actionFlags);
					}
				}
			}
			let sims: Sim[] = this.getSims();
			for (let index: number = 0; index < sims.length; index++) {
				let randomIndex: number = canvas.randomRange(0, ((sims.length - 1) | 0));
				let randomSim: Sim = null!;
				if ((randomSim = sims[randomIndex]).isIdleModeZero() && randomSim.canOccupySlot() && randomSim.type !== 0) {
					let randomActions: number = (canvas.randomPercent() < 50 ? 2 : 0) | (canvas.randomPercent() < 25 ? 8 : 0) | (canvas.randomPercent() < 50 ? 64 : 0) | (canvas.randomPercent() < 25 ? 16 : 0) | (canvas.randomPercent() < 60 ? 32 : 0);
					if (houseValue !== neighborhood.simHomeLotOf(randomSim.variant)) {
						randomActions = (randomActions & -19);
					}
					randomSim.addBehaviourFlags(randomActions);
				}
			}
		}
		let player: Sim = this.activePlayer;
		if (canvas.autonomousEnabled) {
			if (!this.isIdleTooLong()) {
				this.idleTimer = ((this.idleTimer + delta) | 0);
				return;
			}
			if (player.isIdleModeZero()) {
				let playerActions: number = neighborhood.moodFlags();
				if (this.currentLot.areaId() !== 0 && !neighborhood.hasSimMilestone(0, 1)) {
					playerActions = (playerActions | 32);
				}
				player.addBehaviourFlags(playerActions);
				return;
			}
		}
		else {
			player.clearBehaviourFlags();
		}
	}
	updateRandomSimAi(delta: number): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		this.aiTimer = ((this.aiTimer + delta) | 0);
		let interval: number = 5000;
		if (neighborhood.paused) {
			interval = 2500;
		}
		if (this.aiTimer > interval) {
			this.aiTimer = 0;
			let sims: Sim[] = this.getSims();
			let chance: number = 50;
			if (sims.length >= 2) {
				chance = 5;
			}
			let spawned: boolean = false;
			let simCount: number = neighborhood.simCount;
			for (let index: number = 0; index < 4; index++) {
				let simIndex: number = canvas.randomRange(1, ((simCount - 1) | 0));
				let sim: Sim = this.findSimByIndex(simIndex);
				let aiFlags: number = neighborhood.lotStateNow(simIndex);
				if (sim !== null) {
					if ((aiFlags & 7) !== 0) {
						sim.addBehaviourFlags(1);
					}
					if ((aiFlags & 16) !== 0) {
						sim.addBehaviourFlags(256);
					}
				}
				else {
					if ((aiFlags & 8) !== 0 && (aiFlags & 16) === 0 && this.findFreePortraitSlot() !== -1 && !spawned) {
						spawned = true;
						if (canvas.randomPercent() < chance) {
							let spawnPoint: Entity = this.findEntityByType(4);
							(this.createEntityNear(1, spawnPoint, simIndex) as unknown as Sim).addBehaviourFlags(32);
						}
					}
				}
			}
			for (let index2: number = 0; index2 < sims.length; index2++) {
				let sim2: Sim = null!;
				if ((sim2 = sims[index2]).variant !== 0) {
					sim2.addBehaviourFlags(32);
				}
			}
		}
	}
	updateNpcSpawning(delta: number): void {
		this.aiTimer = ((this.aiTimer + delta) | 0);
		if (this.aiTimer > 5000) {
			this.aiTimer = 0;
			let neighborhood: Neighborhood = this.neighborhood;
			let lot: Lot = this.currentLot;
			let npcIndex: number = this.canvas.randomRange(1, ((neighborhood.simCount - 1) | 0));
			let npc: Sim = null!;
			if ((npc = this.findSimByIndex(npcIndex)) === null) {
				let houseIndex: number = neighborhood.simHomeLotOf(npcIndex);
				let objectType: number = lot.lotIdAt(houseIndex);
				let spawnPoint: Entity = this.findEntityByType(objectType);
				new StringBuffer().append("can't find an NPC house: ").append(houseIndex).toString();
				npc = (this.createEntityNear(1, spawnPoint, npcIndex) as unknown as Sim);
			}
			if (npc.isIdleModeZero()) {
				let target: Entity = this.findRandomBrokenEntity();
				npc.queueAction(106, target);
			}
		}
	}
	playRandomSound(delta: number, interval: number, choices: Int16Array): void {
		this.soundTimer = ((this.soundTimer + delta) | 0);
		if (this.soundTimer > interval) {
			this.soundTimer = 0;
			let index: number = this.canvas.randomRange(0, ((choices.length - 1) | 0));
			this.canvas.soundValue.pendingSound = choices[index];
		}
	}
	findRandomBrokenEntity(): Entity {
		let entities: Entity[] = this.getEntities();
		let index: number = this.canvas.randomRange(0, ((entities.length - 1) | 0));
		for (let guard: number = 0; guard < entities.length; guard++) {
			let entity: Entity = null!;
			if ((entity = entities[index]).hasFlag(2048)) {
				return entity;
			}
			while ((index = (((((Math.imul(1543, index) + 3571) | 0)) % 257) | 0)) >= entities.length) {
			}
		}
		return null;
	}
	assignFreeWillAction(sim: Sim): void {
		let entities: Entity[] = this.getEntities();
		let best: Entity = null;
		let fallback: Entity = null;
		for (let index: number = 0; index < entities.length; index++) {
			let entity: Entity = null!;
			if ((entity = entities[index]).behaviorClass() === 4) {
				if (sim.reserveTarget(entity)) {
					best = entity;
					break;
				}
			}
			else {
				if (entity.behaviorClass() === 9 && entity.canOccupySlot()) {
					fallback = entity;
				}
			}
		}
		if (best === null) {
			best = fallback;
		}
		if (best !== null) {
			sim.sitDown(best);
		}
		else {
			if (sim.variant === 0) {
				sim.queueAction(6, (null as unknown as Entity));
			}
		}
	}
	notifySimsOfAction(action: number, source: Sim, target: Entity): void {
		let neighborhood: Neighborhood = this.neighborhood;
		let houseValue: number = this.currentLot.areaId();
		let sims: Sim[] = this.getSims();
		let simCount: number = neighborhood.interactionFlagsOf(action);
		if (action === 23) {
			neighborhood.addSimMilestoneCount(0, 1);
		}
		let index: number = sims.length;
		for (let mask: number = 0; mask < index; mask++) {
			let sim: Sim = null!;
			let simId: number = 0;
			if ((simId = (sim = sims[mask]).variant) !== 0 && sim !== target && sim !== source && ((simCount & 131072) === 0 || neighborhood.simHomeLotOf(simId) === houseValue) && sim.tryInteraction(action, source, target) && (simCount & 4194304) !== 0) {
				return;
			}
		}
	}
	handleMenuAction(action: number): void {
		switch (action) {
			case 60:
				this.canvas.setMenuMode(1);
				return;
			case 61:
				this.canvas.setMenuMode(3);
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
					let slot: number = this.neighborhood.simLot(0);
					let arg: number = this.neighborhood.lotOccupantType(slot);
					let entity: Entity = this.findEntityByType(arg);
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
	resetInteractionQueue(): void {
		this.queuedCount = 0;
		if (this.queuedInteractions === null) {
			this.queuedInteractions = new Int32Array(3);
			this.queuedTargets = new Array(3).fill(null);
			this.queuedArgs = new Int32Array(3);
			this.queuedArgs2 = new Int32Array(3);
		}
		GameCanvas.fillInts(this.queuedInteractions, -1);
		GameCanvas.fillObjects(this.queuedTargets, (null as unknown as any));
	}
	promoteNextInteraction(): void {
		let player: Sim = this.activePlayer;
		let queueIds: Int32Array = this.queuedInteractions;
		let queueTargets: Entity[] = this.queuedTargets;
		if (this.queuedCount > 0) {
			if (player.isIdleModeZero()) {
				this.queuedCount--;
				for (let index: number = 0; index < 2; index++) {
					queueIds[index] = queueIds[((index + 1) | 0)];
					queueTargets[index] = queueTargets[((index + 1) | 0)];
					this.queuedArgs[index] = this.queuedArgs[((index + 1) | 0)];
					this.queuedArgs2[index] = this.queuedArgs2[((index + 1) | 0)];
				}
				queueTargets[this.queuedCount] = null;
				if (this.queuedCount > 0) {
					let actionId: number = queueIds[0];
					let target: Entity = queueTargets[0];
					let firstArg: number = this.queuedArgs[0];
					let secondArg: number = this.queuedArgs2[0];
					this.startInteraction(actionId, target, firstArg, secondArg);
					return;
				}
			}
			else {
				if (queueIds[0] !== player.actionId) {
					let currentGoal: number = player.actionId;
					if ((this.neighborhood.interactionFlagsOf(currentGoal) & 262144) === 0) {
						queueIds[0] = currentGoal;
						queueTargets[0] = player.actionTarget;
					}
					return;
				}
			}
		}
		else {
			let goal: number = player.actionId;
			if ((this.neighborhood.interactionFlagsOf(goal) & 262144) === 0) {
				queueIds[0] = player.actionId;
				queueTargets[0] = player.actionTarget;
				this.queuedCount = 1;
			}
		}
	}
	queueInteraction(action: number, target: Entity, firstArg: number, secondArg: number): void {
		this.resetIdleTimer();
		if (this.activePlayer.isIdleModeZero()) {
			this.startInteraction(action, target, firstArg, secondArg);
		}
		else {
			let slot: number = 0;
			if (this.queuedCount === 3) {
				slot = 2;
			}
			else {
				slot = this.queuedCount++;
			}
			this.queuedInteractions[slot] = action;
			this.queuedTargets[slot] = target;
			this.queuedArgs[slot] = firstArg;
			this.queuedArgs2[slot] = secondArg;
		}
	}
	startInteraction(action: number, target: Entity, firstArg: number, secondArg: number): void {
		let player: Sim = this.activePlayer;
		this.activePlayer.startAction(action, target, firstArg, secondArg);
		let sims: Sim[] = this.getSims();
		for (let index: number = 0; index < sims.length; index++) {
			let sim: Sim = null!;
			if ((sim = sims[index]) !== player) {
				sim.onBumped(target);
			}
		}
	}
	popInteraction(): void {
		this.resetIdleTimer();
		if (this.queuedCount > 0) {
			this.queuedCount--;
			this.queuedTargets[this.queuedCount] = null;
			if (this.queuedCount === 0) {
				this.activePlayer.cancelAction();
			}
		}
	}
	cancelQueuedInteraction(entity: Entity): void {
		this.resetIdleTimer();
		if (entity !== null && this.queuedCount > 0) {
			let slot: number = 0;
			if ((slot = GameCanvas.indexOfObject(entity, this.queuedTargets)) === 0) {
				this.activePlayer.cancelAction();
				return;
			}
			if (slot > 0) {
				this.queuedCount--;
				for (let index: number = slot; index < this.queuedCount; index++) {
					this.queuedInteractions[index] = this.queuedInteractions[((index + 1) | 0)];
					this.queuedTargets[index] = this.queuedTargets[((index + 1) | 0)];
					this.queuedArgs[index] = this.queuedArgs[((index + 1) | 0)];
					this.queuedArgs2[index] = this.queuedArgs2[((index + 1) | 0)];
				}
				this.queuedTargets[this.queuedCount] = null;
			}
		}
	}
	handleMenuCommandA(): void {
		let labelIndex: number = ((this.menuLabels[2] + 5) | 0);
		let action: number = this.menuArgs[labelIndex];
		let option: number = 0;
		if ((option = this.menuLabels[labelIndex]) !== 309 && option !== 308) {
			this.closeOpenMenus();
			let arg: number = this.neighborhood.simIndexOf(option);
			this.queueInteraction(action, this.followTarget, arg, 0);
		}
		else {
			this.menuParent = this.menuLabels[2];
			this.menuSubPage = true;
			this.neighborhood.fillResidentMenu(this.menuLabels, this.menuArgs, action);
			this.menuLabels[4] = this.visibleRows;
			if (this.menuLabels[0] === 0) {
				this.closeOpenMenus();
				this.chooseDialogOptionAt(761, 760);
				return;
			}
		}
	}
	handleRelationshipCommand(kind: number, simId: number): void {
		let neighborhood: Neighborhood = this.neighborhood;
		if (kind === 137) {
			if (this.findSimByIndex(simId) !== null) {
				this.chooseDialogOptionAt(762, 309);
			}
			else {
				if (this.findFreePortraitSlot() !== -1 && neighborhood.isSimIdle(simId)) {
					let spawnPoint: Entity = this.findEntityByType(4);
					(this.createEntity(1, spawnPoint.posX, spawnPoint.posY, spawnPoint.facing, simId) as unknown as Sim).addBehaviourFlags(32);
					this.chooseDialogOptionAt(763, 309);
				}
				else {
					this.chooseDialogOptionAt(764, 309);
				}
			}
		}
		else {
			if (kind === 136) {
				if (this.findSimByIndex(simId) !== null) {
					this.chooseDialogOptionAt(762, 308);
					return;
				}
				let houseIndex: number = neighborhood.simState(simId);
				if ((neighborhood.stateFlagsOf(houseIndex) & 16) !== 0) {
					neighborhood.adjustNeed(4, 983040);
				}
			}
		}
	}
	handleMenuCommandB(): void {
		let labelIndex: number = ((this.menuLabels[2] + 5) | 0);
		let action: number = this.menuArgs[labelIndex];
		let option: number = 0;
		if ((option = this.menuLabels[labelIndex]) === 284) {
			this.menuParent = this.menuLabels[2];
			this.menuSubPage = true;
			this.neighborhood.buildItemMenu(this.menuLabels, this.menuArgs, action);
			this.menuLabels[4] = this.visibleRows;
			if (this.menuLabels[0] === 0) {
				this.closeOpenMenus();
				this.chooseDialogOptionAt(783, 782);
				return;
			}
		}
		else {
			this.closeOpenMenus();
			let arg: number = this.currentLot.indexOfCategoryId(option);
			this.queueInteraction(action, this.followTarget, arg, 0);
		}
	}
	handleMenuCommandC(): void {
		let labelIndex: number = ((this.menuLabels[2] + 5) | 0);
		let action: number = this.menuArgs[labelIndex];
		let option: number = 0;
		if ((option = this.menuLabels[labelIndex]) === 300) {
			this.menuParent = this.menuLabels[2];
			this.menuSubPage = true;
			this.neighborhood.buildUseMenu(this.menuLabels, this.menuArgs, action);
			this.menuLabels[4] = this.visibleRows;
			if (this.menuLabels[0] === 0) {
				this.closeOpenMenus();
				this.chooseDialogOptionAt(781, 780);
				return;
			}
		}
		else {
			this.closeOpenMenus();
			let arg: number = this.currentLot.refForCategoryId(option);
			this.queueInteraction(action, this.followTarget, arg, 0);
		}
	}
	openBuildMenu(): void {
		this.closeOpenMenus();
		this.onTick(7);
		this.setMenuPage(2);
	}
	openBuyMenu(): void {
		this.closeOpenMenus();
		let objectType: number = 0;
		if ((objectType = this.currentLot.stageReward()) === -1) {
			this.chooseDialogOptionAt(775, 310);
		}
		else {
			if (objectType === 0) {
				this.chooseDialogOptionAt(774, 310);
			}
			else {
				this.onTick(7);
				this.setMenuPage(10);
			}
		}
	}
	updateClock(delta: number): void {
		let neighborhood: Neighborhood = this.neighborhood;
		if (this.timeAdvanceSeconds !== 0) {
			neighborhood.update(Math.imul(this.timeAdvanceSeconds, 1000));
			this.timeAdvanceSeconds = 0;
		}
		else {
			let timeDelta: number = delta;
			if (neighborhood.paused) {
				timeDelta = delta << 4;
			}
			neighborhood.update(timeDelta);
		}
		let targetPercent: number = neighborhood.moodValue;
		this.relationPercent = BaseScreen.approach(this.relationPercent, targetPercent, delta, 8);
		if (!this.activePlayer.isLeavingLot()) {
			let flags: number = 0;
			if (((flags = neighborhood.lotStateNow(0)) & 1) === 0 || this.hasNotification()) {
				if ((flags & 2) !== 0 && !this.hasNotification()) {
					this.showNotification(639, -1);
				}
				return;
			}
			this.showNotification(638, -1);
		}
		else {
			this.clearNotificationPair();
		}
	}
	clearNotificationPair(): void {
		this.clearNotification(639);
		this.clearNotification(638);
	}
	advanceTimeUntil(slot: number, endSlot: number): void {
		let neighborhood: Neighborhood = this.neighborhood;
		if (!this.activePlayer.isLeavingLot() && neighborhood.simLot(0) !== -1) {
			for (let flags: number = slot; flags < endSlot; flags++) {
				let nextFlags: number = neighborhood.lotState(0, flags);
				let index: number = neighborhood.lotState(0, ((flags + 1) | 0));
				if ((nextFlags & 2) !== 0 && (index & 4) !== 0) {
					neighborhood.idleTick();
					this.clearNotificationPair();
					if (neighborhood.simLot(0) === -1) {
						this.openDialog(643, 642, 2);
						return;
					}
					this.openDialog(641, 640, 2);
					return;
				}
			}
		}
	}
	drawCursorAnimator(gfx: Graphics, posX: number, posY: number): void {
		if (GameScreen.screenState !== 19) {
			this.cursorAnimator.draw(gfx, posX, posY);
		}
	}
	drawNeedArrows(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		let leftX: number = this.viewX;
		let iconX: number = ((this.viewX + 8) | 0);
		let rowY: number = ((((canvas.getHeight() - 15) | 0) - this.bottomBarHeight) | 0);
		if (this.notifyId !== -1) {
			rowY = ((rowY - SpriteRenderer.frameHeight(670, 0)) | 0);
		}
		for (let index: number = 0; index < 6; index++) {
			let needId: number = 0;
			if ((needId = neighborhood.activeTaskAt(index)) !== -1) {
				let labelId: number = neighborhood.taskId(needId);
				let flags: number = neighborhood.taskFlagsOf(needId);
				let labelWidth: number = canvas.stringWidthById(labelId, 1);
				SpriteRenderer.setColor(gfx, 76);
				gfx.fillRect(leftX, ((rowY - 12) | 0), ((((((labelWidth + iconX) | 0) - leftX) | 0) + 4) | 0), 12);
				((flags & 1) === 0 ? this.needDownAnimator : this.needUpAnimator).draw(gfx, leftX, ((rowY + -1) | 0));
				canvas.drawStringById(labelId, 1, iconX, ((rowY + 1) | 0), 36);
				rowY = ((rowY - 12) | 0);
			}
		}
	}
	openGoalPage(goalId: number): void {
		this.dialogSubject = goalId;
		this.dialogSubjectIndex = -1;
		this.dialogOption = -1;
		this.onTick(18);
		this.setGoalPage(0);
	}
	openSkillGoalPage(goalId: number, extra: number): void {
		this.dialogSubject = goalId;
		this.dialogSubjectIndex = extra;
		this.onTick(18);
		this.setGoalPage(2);
	}
	openCareerGoalPage(goalId: number, extra: number): void {
		this.dialogRequestPending = true;
		this.dialogSubject = goalId;
		this.dialogSubjectIndex = extra;
	}
	setGoalPage(page: number): void {
		let neighborhood: Neighborhood = this.neighborhood;
		this.dialogPhase = page;
		let subjectId: number = this.dialogSubject;
		let optionIndex: number = -1;
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
		if (optionIndex !== -1) {
			let frameId: number = 0;
			if (this.dialogSubjectIndex !== -1) {
				frameId = neighborhood.simNameText(this.dialogSubjectIndex);
			}
			else {
				frameId = 816;
			}
			this.showInfoPanel(optionIndex, frameId);
		}
		this.updateInputMasks();
	}
	drawSimpleOverlay(gfx: Graphics): void {
		this.drawGameLayer(gfx);
		this.canvas.drawDialogOverlay(gfx);
		this.drawPageOne(gfx);
		this.drawSoftKeyHint(gfx);
	}
	handleGoalInput(keyState: number, actionMask: number): void {
		let neighborhood: Neighborhood = this.neighborhood;
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
					let flags: number = 0;
					if (((flags = neighborhood.cardFlagsAt(this.dialogSubject)) & 2) !== 0) {
						this.showNeighborhoodIntro((6 << 24 >> 24));
					}
					else {
						if ((flags & 4) !== 0) {
							this.showNeighborhoodIntro((7 << 24 >> 24));
						}
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
	isGoalPageActive(): boolean {
		return this.miniGamePhase !== 0 && this.miniGamePhase !== 1 && this.miniGamePhase !== 5;
	}
	openDialogPage(kind: number, arg: number, subjectType: number): void {
		let canvas: GameCanvas = this.canvas;
		let resources: ResourceLoader = this.canvas.resources;
		let neighborhood: Neighborhood = this.neighborhood;
		let recolorSource: Int32Array = this.recolorSource;
		let recolorTarget: Int32Array = this.recolorTarget;
		let lot: Lot = this.currentLot;
		neighborhood.celebrate();
		this.canvas.vibrateShort();
		this.dialogKind = kind;
		this.onTick(19);
		this.setDialogPhase(1);
		let mask: number = 0;
		let firstFrame: number = -1;
		let secondFrame: number = -1;
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
		if (mask === 33554432) {
			lot.buildPaletteFor(0, recolorSource, recolorTarget);
			SpriteRenderer.loadTileRecolored(resources, 7, recolorSource, recolorTarget, 0);
			let objectIndex: number = neighborhood.lotKind(this.friendSimId);
			lot.buildPaletteFor(objectIndex, recolorSource, recolorTarget);
			SpriteRenderer.loadTileRecolored(resources, 8, recolorSource, recolorTarget, 0);
		}
		else {
			if (mask === 1048576) {
				lot.buildPaletteFor(0, recolorSource, recolorTarget);
				SpriteRenderer.loadTileRecolored(resources, 11, recolorSource, recolorTarget, 0);
				lot.buildPaletteFor(subjectType, recolorSource, recolorTarget);
				SpriteRenderer.loadTileRecolored(resources, 12, recolorSource, recolorTarget, this.findSimByIndex(subjectType).recolorIndex);
			}
			else {
				if (mask === 8388608) {
					lot.buildPaletteFor(0, recolorSource, recolorTarget);
					SpriteRenderer.loadTileRecolored(resources, 16, recolorSource, recolorTarget, 0);
				}
				else {
					this.canvas.loadTilesBlocking(mask);
				}
			}
		}
		this.dialogIconTop = firstFrame;
		this.dialogIconBottom = secondFrame;
		this.dialogSubjectType = subjectType;
	}
	finishDialog(): void {
		let configFlags: number = 133693440;
		if (this.isNewNeighborhood()) {
			configFlags = (configFlags & -16777217);
		}
		this.canvas.unloadTiles(configFlags, -1);
		switch (this.dialogKind) {
			case 0:
				this.canvas.setPendingScreen(1, -1);
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
	setDialogPhase(phase: number): void {
		this.miniGamePhase = phase;
		this.keyRepeatTime = 0;
		switch (phase) {
			case 1:
				this.savedTarget = this.followTarget;
				this.followEntity((this.activePlayer as unknown as Entity));
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
	updateDialogPhase(delta: number): void {
		this.dialogClock = ((this.dialogClock + delta) | 0);
		switch (this.miniGamePhase) {
			case 1:
				this.updateCameraPan(delta);
				if (this.cameraClamped) {
					this.followEntity((null as unknown as Entity));
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
	handleDialogPhaseInput(keyState: number, actionMask: number): void {
		switch (this.miniGamePhase) {
			case 3:
				if (this.matchesInput(keyState, actionMask, 4128, 8)) {
					this.setDialogPhase(4);
				}
		}
	}
	drawDialog(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
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
		if (this.miniGamePhase === 2) {
			let alpha: number = ((255 - Math.min(((Math.imul(this.keyRepeatTime, 255) / 500) | 0), 255)) | 0);
			let color: number = -16777216 | alpha << 16 | alpha << 8 | alpha << 0;
			canvas.drawFadeRect(gfx, 65, color, 0, 0, canvas.getWidth(), canvas.getHeight());
		}
		else {
			if (this.miniGamePhase === 4) {
				let alpha2: number = ((255 - Math.min(((Math.imul(this.keyRepeatTime, 255) / 1000) | 0), 255)) | 0);
				let color2: number = -16777216 | alpha2 << 16 | alpha2 << 8 | alpha2 << 0;
				canvas.drawFadeRect(gfx, 66, color2, 0, 0, canvas.getWidth(), canvas.getHeight());
			}
		}
	}
	drawDialogContent(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		if (GameCanvas.landscape) {
			SpriteRenderer.drawFrame(gfx, 46, 0, canvas.halfWidth(), ((canvas.halfHeight() - 7) | 0));
		}
		else {
			SpriteRenderer.drawFrame(gfx, 45, 0, canvas.halfWidth(), ((canvas.halfHeight() - 7) | 0));
		}
		let label: SdkString = null;
		if (this.dialogSubjectType !== -1) {
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
				let subjectType: number = this.dialogSubjectType;
				let groundFrame: number = neighborhood.residentBodySprite(subjectType);
				let wallFrame: number = neighborhood.residentHeadSprite(subjectType);
				let objectFrame: number = neighborhood.residentColorIndex(subjectType);
				let overlayFrame: number = neighborhood.simAttribute(0, 0) === 0 ? 81 : 80;
				this.drawScrollingBanner(gfx, groundFrame, wallFrame);
				this.drawDialogBox(gfx, objectFrame, null, null);
				SpriteRenderer.drawFrame(gfx, overlayFrame, 0, canvas.halfWidth(), canvas.halfHeight());
				return;
			case 5:
				this.drawScrollingBanner(gfx, 61, 88);
				this.drawDialogBox(gfx, 66, null, null);
				return;
			case 6:
				let labelId3: number = neighborhood.lotSubtitleText(neighborhood.simLot(0));
				this.drawScrollingBanner(gfx, 61, labelId3);
				this.drawDialogBox(gfx, 64, null, null);
				return;
			case 7:
				let labelId2: number = neighborhood.lotSubtitleText(neighborhood.simLot(0));
				this.drawScrollingBanner(gfx, 61, labelId2);
				this.drawDialogBox(gfx, 65, null, null);
				return;
			case 8:
				let labelId: number = neighborhood.selectedResidentSpriteA();
				this.drawScrollingBanner(gfx, 87, labelId);
				this.drawDialogBox(gfx, 75, null, null);
		}
	}
	drawDialogBox(gfx: Graphics, frameId: number, topLabel: SdkString, bottomLabel: SdkString): void {
		let canvas: GameCanvas = this.canvas;
		let screenWidth: number = this.canvas.getWidth();
		let screenHeight: number = canvas.getHeight();
		let kind: number = this.dialogKind;
		let offsetY: number = 0;
		if (this.dialogKind === 0) {
			offsetY = ((screenHeight / 10) | 0);
		}
		let centerX: number = screenWidth >> 1;
		let centerY: number = (((screenHeight >> 1) + offsetY) | 0);
		SpriteRenderer.drawFrame(gfx, frameId, 0, centerX, centerY);
		let hitRect: Int32Array = this.hitRect;
		if (topLabel !== null) {
			SpriteRenderer.hitTest(hitRect, frameId, 0, 0);
			canvas.drawString(topLabel, 1, ((centerX + hitRect[0]) | 0), ((((((centerY + 1) | 0) + hitRect[1]) | 0) - 13) | 0), 17);
		}
		if (bottomLabel !== null) {
			SpriteRenderer.hitTest(hitRect, frameId, 0, 1);
			canvas.drawString(bottomLabel, 1, ((centerX + hitRect[0]) | 0), ((((((centerY + 1) | 0) + hitRect[1]) | 0) + 13) | 0), 17);
		}
		if (kind === 8) {
			SpriteRenderer.hitTest(hitRect, frameId, 0, 0);
			this.drawIcon(gfx, ((centerX + hitRect[0]) | 0), ((centerY + hitRect[1]) | 0), 0, true, 0);
		}
		else {
			if (kind === 5) {
				let iconFrame: number = this.currentLot.stageMusicId();
				SpriteRenderer.hitTest(hitRect, iconFrame, 0, 1);
				SpriteRenderer.drawFrame(gfx, iconFrame, 0, ((centerX - hitRect[0]) | 0), ((centerY - hitRect[1]) | 0));
			}
		}
		if (this.miniGamePhase === 3) {
			if (this.dialogIconTop !== -1) {
				let iconHeight: number = SpriteRenderer.frameHeight(frameId, 0);
				let halfCenterY: number = 0;
				let topIconY: number = (halfCenterY = ((centerY - (iconHeight >> 1)) | 0)) >> 1;
				if (kind !== 3 && kind !== 7) {
					canvas.drawSlotText(0, this.dialogIconTop, 0, this.dialogWidth, centerX, topIconY, 3);
				}
				else {
					canvas.drawScrollingText(0, this.dialogIconTop, 0, centerX, topIconY, 3);
				}
			}
			if (this.dialogIconBottom !== -1) {
				let bottomGap: number = ((((screenHeight - 15) | 0) - (((centerY + (SpriteRenderer.frameHeight(frameId, 0) >> 1)) | 0))) | 0);
				let bottomIconY: number = ((((screenHeight - 15) | 0) - (bottomGap >> 1)) | 0);
				canvas.drawSlotText(0, this.dialogIconBottom, 0, this.dialogWidth, centerX, bottomIconY, 3);
			}
		}
	}
	drawScrollingBanner(gfx: Graphics, frameA: number, frameB: number): void {
		let canvas: GameCanvas = this.canvas;
		let bottomY: number = ((this.canvas.getHeight() - 15) | 0);
		let screenWidth: number = 0;
		let leftX: number = (((screenWidth = canvas.getWidth()) / 3) | 0) >> 1;
		let rightX: number = ((screenWidth - leftX) | 0);
		let waveAmplitude: number = ((Math.imul(screenWidth, 15) / 100) | 0);
		let bannerHeight: number = Math.max(SpriteRenderer.frameHeight(frameA, 0), SpriteRenderer.frameHeight(frameB, 0));
		let maxY: number = ((bottomY + bannerHeight) | 0);
		let scrollOffset: number = (((((Math.imul(this.dialogClock, 850) % 5898240) | 0)) * -1) | 0);
		let frameId: number = frameA;
		if (scrollOffset < -2949120) {
			scrollOffset = ((scrollOffset + 2949120) | 0);
			frameId = frameB;
		}
		let wavePhase: number = ((((Math.imul(this.dialogClock, 125) % 411775) | 0) - FixedMath.div(FixedMath.mul(164709, ((scrollOffset * -1) | 0)), 2949120)) | 0);
		let rowY: number = scrollOffset >> 16;
		let phase: number = wavePhase;
		for (let currentFrame: number = frameId; rowY < maxY; currentFrame = currentFrame === frameA ? frameB : frameA) {
			let waveOffset: number = FixedMath.mul(waveAmplitude, FixedMath.cos(phase));
			if (currentFrame === frameB && this.dialogKind === 7) {
				SpriteRenderer.drawFrame(gfx, 91, 0, ((leftX + waveOffset) | 0), ((rowY + -15) | 0));
			}
			SpriteRenderer.drawFrame(gfx, currentFrame, 0, ((leftX + waveOffset) | 0), rowY);
			let secondPhase: number = ((phase + 82354) | 0);
			waveOffset = FixedMath.mul(waveAmplitude, FixedMath.cos(secondPhase));
			let secondRowY: number = ((rowY + 22) | 0);
			if (currentFrame === frameB && this.dialogKind === 7) {
				SpriteRenderer.drawFrame(gfx, 91, 0, ((rightX + waveOffset) | 0), ((secondRowY + -15) | 0));
			}
			SpriteRenderer.drawFrame(gfx, currentFrame, 0, ((rightX + waveOffset) | 0), secondRowY);
			rowY = ((rowY + 45) | 0);
			phase = ((phase + 164709) | 0);
		}
	}
	drawHousePreview(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		let subjectType: number = this.dialogSubjectType;
		let groundFrame: number = 0;
		let groundFrame2: number = 0;
		let wallFrame: number = 0;
		let wallFrame2: number = 0;
		if (neighborhood.simAttribute(0, 0) === 0) {
			groundFrame = 74;
			groundFrame2 = 489;
			if (neighborhood.simAttribute(subjectType, 0) === 0) {
				wallFrame = 71;
				wallFrame2 = 489;
			}
			else {
				wallFrame = 69;
				wallFrame2 = 382;
			}
		}
		else {
			if (neighborhood.simAttribute(subjectType, 0) === 0) {
				groundFrame = 72;
				wallFrame = 70;
				wallFrame2 = 489;
			}
			else {
				groundFrame = 73;
				wallFrame = 69;
				wallFrame2 = 382;
			}
			groundFrame2 = 382;
		}
		let labelId: number = ((neighborhood.simAttribute(0, 2) + 1) | 0);
		let rowCount: number = ((neighborhood.simAttribute(subjectType, 2) + 1) | 0);
		SpriteRenderer.setRecolorIndex(0);
		SpriteRenderer.drawParts(gfx, groundFrame, 0, canvas.halfWidth(), canvas.halfHeight(), groundFrame2, labelId, null);
		SpriteRenderer.setRecolorIndex(this.findSimByIndex(subjectType).recolorIndex);
		SpriteRenderer.drawParts(gfx, wallFrame, 0, canvas.halfWidth(), canvas.halfHeight(), wallFrame2, rowCount, null);
		SpriteRenderer.setRecolorIndex(0);
	}
	drawRelationshipGraph(gfx: Graphics): void {
		let lot: Lot = this.currentLot;
		let player: Sim = this.activePlayer;
		let other: Sim = this.findSimByIndex(this.dialogSubjectType);
		let hitRect: Int32Array = this.hitRect;
		let playerX: number = player.posX;
		let playerY: number = player.posY;
		let otherX: number = other.posX;
		let otherY: number = other.posY;
		let centerX: number = ((lot.toScreenFixedX(playerX, playerY) + lot.toScreenFixedX(otherX, otherY)) | 0) >> 1;
		let centerY: number = ((lot.toScreenFixedY(playerX, playerY) + lot.toScreenFixedY(otherX, otherY)) | 0) >> 1;
		SpriteRenderer.hitTest(hitRect, 67, 0, 2);
		centerX = ((centerX + (hitRect[0] << 16)) | 0);
		centerY = ((centerY - (hitRect[1] << 17)) | 0);
		this.currentLot.drawEntityPair(gfx, player, other, centerX, centerY);
	}
	static isDialogOpen(): boolean {
		return GameScreen.screenState === 16;
	}
	openMiniGame(kind: number, target: Entity): void {
		this.closeOpenMenus();
		this.neighborhood.celebrate();
		this.miniTarget = target;
		this.miniKind = kind;
		this.resetEffectAnimators();
		let duration: number = 0;
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
	setMiniGameStep(step: number): void {
		let neighborhood: Neighborhood = null!;
		let playSound: boolean = false;
		let lot: Lot = this.currentLot;
		neighborhood = this.neighborhood;
		this.miniStep = step;
		playSound = false;
		label85: {
			switch (step) {
				case 0:
					if (this.miniKind === 1) {
						let posX: number = this.activePlayer.posX;
						let posY: number = this.activePlayer.posY;
						let targetX: number = lot.toScreenX(posX, posY);
						let targetY: number = lot.toScreenY(posX, posY);
						if (this.miniTarget.type === 5) {
							targetX = ((targetX + 50) | 0);
						}
						else {
							targetX = ((targetX - 10) | 0);
						}
						this.followTarget = null;
						this.camTargetX = targetX << 16;
						this.camX = this.camTargetX;
						this.camTargetY = targetY << 16;
						this.camY = this.camTargetY;
					}
					else {
						this.followEntity((this.activePlayer as unknown as Entity));
					}
					break;
				case 1:
					switch (this.miniKind) {
						case 0:
							this.showNeighborhoodIntro((2 << 24 >> 24));
							break label85;
						case 1:
							this.showNeighborhoodIntro((3 << 24 >> 24));
							break label85;
						case 2:
							this.showNeighborhoodIntro((4 << 24 >> 24));
							break label85;
						case 3:
							this.showNeighborhoodIntro((5 << 24 >> 24));
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
							let skillId: number = neighborhood.lastPurchasedItem;
							if (neighborhood.lastPurchasedItem === 0) {
								neighborhood.unlockAchievement(29);
							}
							else {
								if (skillId === 9) {
									neighborhood.unlockAchievement(31);
								}
								else {
									if (skillId === 6) {
										neighborhood.unlockAchievement(30);
									}
								}
							}
							break;
						case 1:
							this.miniTarget.clearBusy();
							neighborhood.unlockAchievement(22);
							if (this.miniLevel === 32) {
								neighborhood.unlockAchievement(23);
							}
							else {
								if (this.miniLevel === 33) {
									neighborhood.unlockAchievement(24);
								}
								else {
									if (this.miniLevel === 30) {
										neighborhood.unlockAchievement(25);
									}
								}
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
					if (this.miniKind === 1) {
						this.miniTarget.clearBusy();
					}
					this.followEntity(this.miniTarget);
					if (this.miniKind === 2 && this.canvas.randomPercent() < 5) {
						this.openStatsDialog(805);
					}
					else {
						this.activePlayer.cancelAction();
						this.activePlayer.queueAction_2(8, null, 0, 0);
						this.chooseDialogOptionAt(755, 754);
						playSound = true;
					}
					break;
				case 4:
					if (this.miniKind === 1) {
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
					let player: Sim = this.activePlayer;
					this.activePlayer.clearState(128);
					player.setPose(22);
					break;
				case 8:
					let playerSim: Sim = this.activePlayer;
					this.activePlayer.clearState(128);
					let animId: number = this.isSliderComplete() ? 26 : 23;
					playerSim.setPose(animId);
					break;
				case 9:
					this.keyRepeatTime = 0;
					this.effectAnimators[0].play(634, 4);
			}
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
	raiseSkillLevel(skillId: number): void {
		let neighborhood: Neighborhood = this.neighborhood;
		let oldLevel: number = this.neighborhood.residentLevel(skillId);
		neighborhood.addCareerPoints(skillId);
		let newLevel: number = neighborhood.residentLevel(skillId);
		let frameId: number = 757;
		if (oldLevel !== newLevel) {
			frameId = newLevel === 5 ? 759 : 758;
			switch (skillId) {
				case 0:
					neighborhood.unlockAchievement(7);
					if (newLevel === 5) {
						neighborhood.unlockAchievement(73);
					}
					break;
				case 1:
					neighborhood.unlockAchievement(9);
					if (newLevel === 5) {
						neighborhood.unlockAchievement(74);
					}
					break;
				case 2:
					neighborhood.unlockAchievement(8);
					if (newLevel === 5) {
						neighborhood.unlockAchievement(71);
					}
					break;
				case 3:
					neighborhood.unlockAchievement(10);
					if (newLevel === 5) {
						neighborhood.unlockAchievement(72);
					}
				case 4:
			}
		}
		if (oldLevel !== newLevel && newLevel === 5) {
			this.canvas.formatString1(-11, 793, neighborhood.residentId(skillId));
			this.openDialogPage(4, -11, skillId);
		}
		else {
			if (oldLevel !== 5) {
				let textId: number = neighborhood.residentId(skillId);
				this.chooseDialogOptionAt(frameId, textId);
			}
		}
	}
	drawMiniGame(gfx: Graphics): void {
		this.currentLot.draw(gfx);
		if (!this.mainMenuOpen && this.miniKind !== 1) {
			this.canvas.drawDialogOverlay(gfx);
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
	drawMiniGameProgress(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		SpriteRenderer.drawFrame(gfx, 568, 0, 0, 34);
		let bottomBarHeight: number = SpriteRenderer.frameHeight(586, 0);
		let barY: number = ((((((canvas.getHeight() - 15) | 0) - 34) | 0) - bottomBarHeight) | 0);
		SpriteRenderer.drawFrame(gfx, 569, 0, 0, barY);
		this.panelTop = 34;
		this.panelBottom = barY;
		this.drawRelationshipBar(gfx);
		let savedClipX: number = gfx.getClipX();
		let savedClipY: number = gfx.getClipY();
		let savedClipWidth: number = gfx.getClipWidth();
		let savedClipHeight: number = gfx.getClipHeight();
		let centerX: number = canvas.getWidth() >> 1;
		let progressHeight: number = SpriteRenderer.frameHeight(634, 0);
		let progressY: number = ((17 - progressHeight) | 0);
		let progressWidth: number = ((canvas.getWidth() - 40) | 0);
		let progressX: number = ((centerX - (progressWidth >> 1)) | 0);
		gfx.setClip(progressX, savedClipY, progressWidth, savedClipHeight);
		SpriteRenderer.drawFrame(gfx, 633, 0, progressX, progressY);
		let filledWidth: number = ((Math.imul(this.progressValue, progressWidth) / this.progressMax) | 0);
		gfx.setClip(progressX, savedClipY, filledWidth, savedClipHeight);
		if (this.miniStep === 5) {
			SpriteRenderer.drawAnimator(gfx, this.effectAnimators[0], progressX, progressY);
		}
		else {
			SpriteRenderer.drawFrame(gfx, 634, 0, progressX, progressY);
		}
		gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
	}
	drawMiniGameTop(gfx: Graphics): void {
		SpriteRenderer.drawFrame(gfx, 568, 0, 0, 14);
		let posX: number = SpriteRenderer.frameHeight(586, 0);
		let posY: number = ((((((this.canvas.getHeight() - 15) | 0) - 64) | 0) - posX) | 0);
		SpriteRenderer.drawFrame(gfx, 569, 0, 0, posY);
		this.panelTop = 14;
		this.panelBottom = posY;
		this.drawRelationshipBar(gfx);
	}
	handleMiniGameInput(keyState: number, actionMask: number): void {
		if (this.matchesInput(keyState, actionMask, 0, 5)) {
			this.openMainMenu();
		}
		else {
			if (this.matchesInput(keyState, actionMask, 0, 64)) {
				this.setMiniGameStep(4);
			}
			else {
				if (this.miniStep === 1) {
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
	}
	updateMiniGame(delta: number): void {
		if (!this.mainMenuOpen && !this.dialogActive) {
			this.activePlayer.advanceTime(delta);
			this.miniTarget.advanceTime(delta);
			this.advanceEntitiesOfType(delta, 7);
			if (this.miniStep === 0) {
				this.updateCameraPan(delta);
				if (this.cameraClamped) {
					this.followEntity((null as unknown as Entity));
					let nextStep: number = this.miniKind === 1 ? 7 : 1;
					this.setMiniGameStep(nextStep);
					return;
				}
			}
			else {
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
	resetEffectAnimators(): void {
		for (let slot: number = 0; slot < 24; slot++) {
			this.effectAnimators[slot].setRunning(false);
			GameCanvas.fillInts(this.effectPositions[slot], -1);
		}
	}
	playEffect(animId: number, posX: number, posY: number, frameRate: number): number {
		let slot: number = -1;
		for (let index: number = 0; index < 24; index++) {
			if (!this.effectAnimators[index].isRunning()) {
				slot = index;
				break;
			}
		}
		if (slot !== -1) {
			this.effectAnimators[slot].play(animId, frameRate);
			this.effectPositions[slot][0] = posX;
			this.effectPositions[slot][1] = posY;
			this.effectPositions[slot][2] = -1;
		}
		return slot;
	}
	drawEffectsFrom(gfx: Graphics, startSlot: number): void {
		this.drawEffects(gfx, startSlot, 0, 0);
	}
	drawEffects(gfx: Graphics, startSlot: number, offsetX: number, offsetY: number): void {
		for (let slot: number = startSlot; slot < 24; slot++) {
			let animator: Animator = null!;
			if ((animator = this.effectAnimators[slot]).isRunning()) {
				let position: Int32Array = this.effectPositions[slot];
				animator.draw(gfx, ((position[0] + offsetX) | 0), ((position[1] + offsetY) | 0));
			}
		}
	}
	drawEffectsScaled(gfx: Graphics, startSlot: number, offsetX: number, offsetY: number): void {
		for (let slot: number = startSlot; slot < 24; slot++) {
			let animator: Animator = null!;
			if ((animator = this.effectAnimators[slot]).isRunning()) {
				let position: Int32Array = this.effectPositions[slot];
				animator.draw(gfx, (((position[0] >> 16) + offsetX) | 0), (((position[1] >> 16) + offsetY) | 0));
			}
		}
	}
	advanceEffects(delta: number, startSlot: number): void {
		for (let slot: number = startSlot; slot < 24; slot++) {
			this.effectAnimators[slot].advance(delta);
		}
	}
	static fillTiled(gfx: Graphics, posX: number, posY: number, width: number, height: number, frameId: number): void {
		let savedClipX: number = gfx.getClipX();
		let savedClipY: number = gfx.getClipY();
		let savedClipWidth: number = gfx.getClipWidth();
		let savedClipHeight: number = gfx.getClipHeight();
		gfx.setClip(posX, posY, width, height);
		let tileWidth: number = SpriteRenderer.frameWidth(frameId, 0);
		let tileHeight: number = SpriteRenderer.frameHeight(frameId, 0);
		for (let row: number = 0; row < height; row = ((row + tileHeight) | 0)) {
			for (let column: number = 0; column < width; column = ((column + tileWidth) | 0)) {
				SpriteRenderer.drawFrame(gfx, frameId, 0, ((posX + column) | 0), ((posY + row) | 0));
			}
		}
		gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
	}
	setupCandleGame(): void {
		this.miniType = 0;
		let skillLevel: number = this.neighborhood.residentLevel(0);
		let riseSpeed: number = 655;
		let columnCount: number = 2;
		let duration: number = 0;
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
				duration = 40000;
		}
		this.candleColumns = columnCount;
		this.progressMax = duration;
		this.candleRiseSpeed = riseSpeed;
		this.candleDecrement = 1638400;
		this.plotIndex = 0;
		this.progressValue = 0;
		let canvas: GameCanvas = this.canvas;
		for (let column: number = 0; column < this.candleColumns; column++) {
			let candleValue: number = canvas.randomRange(1638400, 4915200);
			this.candleAnimValues[column] = candleValue;
			this.candleTargets[column] = candleValue;
			this.effectAnimators[((this.candleColumns + column) | 0)].play(612, 20);
			this.candleFrames[column] = (canvas.randomRange(0, 3) << 24 >> 24);
		}
	}
	drawCandles(gfx: Graphics): void {
		let savedClipX: number = gfx.getClipX();
		let savedClipY: number = gfx.getClipY();
		let savedClipWidth: number = gfx.getClipWidth();
		let savedClipHeight: number = gfx.getClipHeight();
		let centerX: number = ((this.viewX + (this.viewWidth >> 1)) | 0);
		let columnWidth: number = SpriteRenderer.frameWidth(610, 0);
		let columnHeight: number = SpriteRenderer.frameHeight(611, 0);
		let columnX: number = ((((centerX - (Math.imul(columnWidth, this.candleColumns) >> 1)) | 0) + (columnWidth >> 1)) | 0);
		let baseY: number = ((((this.panelTop + this.panelBottom) | 0) + columnHeight) | 0) >> 1;
		let candleHeight: number = SpriteRenderer.frameHeight(612, 0);
		let hitRect: Int32Array = this.frameBounds;
		let posX: number = columnX;
		for (let column: number = 0; column < this.candleColumns; column++) {
			let baseFrame: number = 611;
			let animId: number = this.effectAnimators[((this.candleColumns + column) | 0)].animId;
			let frame: number = this.effectAnimators[((this.candleColumns + column) | 0)].frame;
			if (this.miniStep === 5) {
				animId = 613;
				frame = 0;
			}
			if (column === this.plotIndex) {
				baseFrame = 610;
				if (animId !== 614) {
					animId = 612;
				}
			}
			let candleValue: number = this.candleTargets[column];
			SpriteRenderer.drawFrame(gfx, baseFrame, 0, posX, baseY);
			let fillHeight: number = ((Math.imul(candleHeight, (candleValue >> 16)) / 100) | 0);
			gfx.setClip(savedClipX, ((baseY - fillHeight) | 0), savedClipWidth, fillHeight);
			SpriteRenderer.drawFrame(gfx, animId, frame, posX, baseY);
			gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
			SpriteRenderer.drawFrame(gfx, 615, 0, posX, baseY);
			if (candleValue > 0 && candleValue < 6553600) {
				SpriteRenderer.hitTest(hitRect, 611, 0, 0);
				SpriteRenderer.drawFrame(gfx, 608, this.candleFrames[column], ((posX + hitRect[0]) | 0), ((baseY + hitRect[1]) | 0));
			}
			posX = ((posX + columnWidth) | 0);
		}
		gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
		this.drawEffectsFrom(gfx, this.candleColumns << 1);
	}
	updateCandleGame(delta: number): void {
		this.advanceEffects(delta, this.candleColumns);
		if (this.miniStep === 5) {
			this.effectAnimators[0].advance(delta);
			if (this.keyRepeatTime > 3200) {
				this.setMiniGameStep(2);
			}
		}
		else {
			if (this.miniStep === 6) {
				if (!this.effectAnimators[this.toastAnimSlot].isRunning()) {
					this.setMiniGameStep(3);
				}
			}
			else {
				this.progressValue = ((this.progressValue + delta) | 0);
				if (this.progressValue > this.progressMax) {
					this.setMiniGameStep(5);
				}
				else {
					let centerX: number = ((this.viewX + (this.viewWidth >> 1)) | 0);
					let columnWidth: number = SpriteRenderer.frameWidth(610, 0);
					let columnHeight: number = SpriteRenderer.frameHeight(611, 0);
					let columnX: number = ((((centerX - (Math.imul(columnWidth, this.candleColumns) >> 1)) | 0) + (columnWidth >> 1)) | 0);
					let baseY: number = ((((this.panelTop + this.panelBottom) | 0) + columnHeight) | 0) >> 1;
					let hitRect: Int32Array = this.frameBounds;
					let posX: number = columnX;
					for (let column: number = 0; column < this.candleColumns; column++) {
						let candleValue: number = 0;
						if ((candleValue = this.candleAnimValues[column]) > 0) {
							candleValue = ((candleValue + (Math.imul(delta, this.candleRiseSpeed))) | 0);
							this.candleAnimValues[column] = candleValue;
						}
						if (candleValue <= 6553600) {
							let animId: number = 31;
							let frameId: number = 614;
							if (candleValue < 1638400) {
								animId = 29;
							}
							else {
								if (candleValue < 4915200) {
									animId = 30;
									frameId = 613;
								}
							}
							let animator: Animator = null!;
							(animator = this.effectAnimators[column]).play(animId, 4);
							if (animator.advance(delta) && animator.matchesFrame(hitRect, 0)) {
								this.playEffect(42, ((posX + hitRect[0]) | 0), ((baseY + hitRect[1]) | 0), 16);
							}
							let secondAnimator: Animator = null!;
							(secondAnimator = this.effectAnimators[((this.candleColumns + column) | 0)]).play(frameId, 4);
						}
						this.candleTargets[column] = BaseScreen.approach(this.candleTargets[column], candleValue, delta, 8);
						if (candleValue > 6553600 || this.candleTargets[column] < 65536) {
							SpriteRenderer.hitTest(hitRect, 611, 0, 0);
							this.toastAnimSlot = this.playEffect(609, ((posX + hitRect[0]) | 0), ((baseY + hitRect[1]) | 0), 16);
							this.canvas.vibrateShort();
							this.setMiniGameStep(6);
						}
						posX = ((posX + columnWidth) | 0);
					}
				}
			}
		}
	}
	handleCandleInput(keyState: number, actionMask: number): void {
		let columnCount: number = this.candleColumns;
		let currentColumn: number = this.plotIndex;
		if (this.matchesInput(keyState, actionMask, 32784, 0) && currentColumn > 0) {
			currentColumn = (((((((currentColumn + columnCount) | 0) - 1) | 0)) % columnCount) | 0);
		}
		if (this.matchesInput(keyState, actionMask, 65600, 0) && currentColumn < ((columnCount - 1) | 0)) {
			currentColumn = (((((currentColumn + 1) | 0)) % columnCount) | 0);
		}
		if (this.matchesInput(keyState, actionMask, 4128, 0)) {
			let newValue: number = 0;
			newValue = Math.max((((newValue = this.candleAnimValues[currentColumn]) - this.candleDecrement) | 0), 0);
			this.candleAnimValues[currentColumn] = newValue;
		}
		this.plotIndex = currentColumn;
		if (this.canvas.cheatToggled && this.matchesInput(keyState, actionMask, 1, 0)) {
			this.progressValue = ((this.progressMax - 1) | 0);
		}
	}
	setupSliderGame(): void {
		let canvas: GameCanvas = this.canvas;
		this.miniType = 2;
		this.leftPressed = false;
		this.rightPressed = false;
		this.miniTarget.setBusy();
		let level: number = 0;
		let skillLevel: number = 0;
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
				if (skillLevel === 5 && level < 32) {
					level = canvas.randomRange(30, 34);
				}
		}
		this.miniLevel = level;
		let sliderStep: number = 0;
		let sliderRange: number = 0;
		switch (level) {
			case 30:
			default:
				sliderStep = 32;
				sliderRange = 32768;
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
		this.targetPosition = this.canvas.randomRange(0, 131071);
		this.candleFlameAnimator = this.effectAnimators[0];
		this.candleFlameAnimator.play(617, 20);
		this.candleGlowAnimator = this.effectAnimators[1];
		this.candleGlowAnimator.play(620, 20);
		this.pickNextSliderTarget();
	}
	drawSliderGame(gfx: Graphics): void {
		if (this.miniStep !== 0 && this.miniStep !== 7) {
			let centerX: number = ((this.viewX + (this.viewWidth >> 1)) | 0);
			let topY: number = ((this.panelBottom + 18) | 0);
			SpriteRenderer.drawFrame(gfx, 622, 0, centerX, topY);
			let hitRect: Int32Array = this.frameBounds;
			SpriteRenderer.hitTestMirrored(this.frameBounds, 622, 0, 0);
			let barY: number = ((((topY + hitRect[1]) | 0) + hitRect[3]) | 0);
			let barX: number = ((centerX + hitRect[0]) | 0);
			let barWidth: number = hitRect[2];
			let markerX: number = ((barX + FixedMath.mul(barWidth, FixedMath.div(this.sliderPosition, 131072))) | 0);
			if (this.isSliderNear()) {
				let frame: number = -1;
				if (this.timingMeter < 58982) {
					let frameCount: number = SpriteRenderer.frameCount(619);
					let frameStep: number = ((65536 / frameCount) | 0);
					frame = ((this.timingMeter / frameStep) | 0);
				}
				let savedClipX: number = gfx.getClipX();
				let savedClipY: number = gfx.getClipY();
				let savedClipWidth: number = gfx.getClipWidth();
				let savedClipHeight: number = gfx.getClipHeight();
				gfx.setClip(barX, savedClipY, barWidth, savedClipHeight);
				if (frame !== -1) {
					SpriteRenderer.drawFrame(gfx, 619, frame, markerX, barY);
				}
				else {
					SpriteRenderer.drawAnimator(gfx, this.candleGlowAnimator, markerX, barY);
				}
				gfx.setClip(savedClipX, savedClipY, savedClipWidth, savedClipHeight);
			}
			if (this.timingMeter > -16384 && this.timingMeter < 65536) {
				SpriteRenderer.drawFrame(gfx, 621, 0, markerX, barY);
			}
			markerX = ((barX + FixedMath.mul(barWidth, FixedMath.div(this.targetPosition, 131072))) | 0);
			SpriteRenderer.drawAnimator(gfx, this.candleFlameAnimator, markerX, barY);
		}
	}
	updateSliderGame(delta: number): void {
		let player: Sim = this.activePlayer;
		if (this.miniStep === 7) {
			if (!player.isAnimating()) {
				this.setMiniGameStep(1);
			}
		}
		else {
			if (this.miniStep === 8) {
				if (!player.isAnimating()) {
					let nextStep: number = this.isSliderComplete() ? 2 : 3;
					this.setMiniGameStep(nextStep);
				}
			}
			else {
				let pressure: number = 0;
				if (this.leftPressed) {
					pressure = ((0 - delta) | 0);
				}
				if (this.rightPressed) {
					pressure = ((pressure + delta) | 0);
				}
				if (pressure !== 0) {
					let newPosition: number = ((this.sliderPosition + Math.imul(pressure, 131)) | 0);
					this.sliderPosition = Math.max(Math.min(newPosition, 131072), 0);
				}
				this.candleFlameAnimator.advance(delta);
				this.candleGlowAnimator.advance(delta);
				if (this.timingMeter >= 65536) {
					if (!this.candleFlameAnimator.isRunning()) {
						this.setMiniGameStep(8);
						return;
					}
				}
				else {
					let meterStep: number = this.isSliderNear() ? 196 : -262;
					this.timingMeter = ((this.timingMeter + meterStep) | 0);
					if (this.timingMeter <= -16384) {
						this.canvas.vibrateShort();
						this.setMiniGameStep(8);
						return;
					}
					if (this.timingMeter >= 65536) {
						this.timingMeter = 65535;
					}
					let frameId: number = 617;
					if (this.timingMeter > 32768) {
						frameId = 618;
					}
					this.candleFlameAnimator.play(frameId, 4);
				}
				if (this.timingMeter > -16384 && this.timingMeter < 65536) {
					let movingLeft: boolean = false;
					let direction: number = (movingLeft = this.targetGoal < this.targetStart) ? ((delta * -1) | 0) : delta;
					this.targetPosition = ((this.targetPosition + Math.imul(this.sliderStep, direction)) | 0);
					if (movingLeft && this.targetPosition < this.targetGoal || !movingLeft && this.targetPosition > this.targetGoal) {
						this.targetPosition = this.targetGoal;
						this.pickNextSliderTarget();
					}
				}
				let animId: number = 24;
				if (this.timingMeter > 32768) {
					animId = 25;
				}
				player.addState(128);
				player.setPose(animId);
			}
		}
	}
	isSliderComplete(): boolean {
		return this.timingMeter === 65536;
	}
	isSliderNear(): boolean {
		return Math.abs(((this.sliderPosition - this.targetPosition) | 0)) < 26214;
	}
	pickNextSliderTarget(): void {
		let value: number = this.targetPosition;
		let limit: number = Math.max(this.targetPosition, ((131072 - value) | 0));
		let step: number = this.canvas.randomRange(this.sliderRange, limit);
		let movingLeft: boolean = value > ((131072 - step) | 0) ? true : value >= step && this.canvas.randomPercent() < 50;
		this.targetStart = value;
		this.targetGoal = ((value + (movingLeft ? ((step * -1) | 0) : step)) | 0);
	}
	handleSliderInput(keyState: number, actionMask: number): void {
		if (this.timingMeter > -16384 && this.timingMeter < 65536) {
			this.leftPressed = (keyState & 32784) !== 0;
			this.rightPressed = (keyState & 65600) !== 0;
		}
		else {
			this.leftPressed = false;
			this.rightPressed = false;
		}
		if (this.matchesInput(keyState, actionMask, 4128, 0) && this.timingMeter >= 58982) {
			if (this.isSliderNear()) {
				this.sliderPosition = this.targetPosition;
				this.candleFlameAnimator.play(616, 16);
				this.timingMeter = 65536;
			}
			else {
				this.canvas.vibrateShort();
				this.setMiniGameStep(8);
			}
		}
		if (this.canvas.cheatToggled && this.matchesInput(keyState, actionMask, 1, 0)) {
			this.sliderPosition = this.targetPosition;
			this.candleFlameAnimator.play(616, 16);
			this.timingMeter = 65536;
		}
	}
	loadSeedData(resourceId: number): void {
		let stream: DataInputStream = new DataInputStream(this.canvas.resources.openStream(resourceId));
		try {
			let rows: Int8Array[] = new Array(4).fill(null);
			for (let row: number = 0; row < 4; row++) {
				let length: number = 0;
				let rowData: Int8Array = new Int8Array(length = stream.readByte());
				for (let index: number = 0; index < length; index++) {
					rowData[index] = stream.readByte();
				}
				rows[row] = rowData;
			}
			this.seedRows = rows;
			if (this.seedTypes === null) {
				this.seedTypes = new Int8Array(4);
			}
			let types: Int8Array = this.seedTypes;
			for (let typeIndex: number = 0; typeIndex < 4; typeIndex++) {
				types[typeIndex] = stream.readByte();
			}
		}
		catch (failure) {
			if (failure instanceof IOException) {
				this.seedRows = (null as unknown as Int8Array[]);
				this.seedTypes = null;
			}
			else {
				throw failure;
			}
		}
	}
	setupSeedGame(): void {
		let canvas: GameCanvas = this.canvas;
		this.canvas.randomRange(0, 4);
		this.loadSeedData(49);
		this.miniType = 3;
		let timeLimit: number = (((this.neighborhood.careerPoints(3) >> 2) + 65536) | 0);
		this.seedCursorAnimator = this.effectAnimators[0];
		let rowCount: number = this.neighborhood.residentLevel(3) < 3 ? 3 : 4;
		this.plotCount = rowCount;
		this.seedCount = Math.imul(10, timeLimit) >> 16;
		this.progressMax = this.seedCount;
		this.cursorPosition = 65536;
		this.seedCells = Array.from({ length: rowCount }, () => new Int16Array(5));
		let tileWidth: number = 0;
		let rowStep: number = (tileWidth = ((SpriteRenderer.frameWidth(630, 0) / GameScreen.DIR_X_TABLE[0]) | 0)) << 2;
		for (let row: number = 0; row < rowCount; row++) {
			let rowData: Int8Array = this.seedRows[row];
			let cells: Int16Array = this.seedCells[row];
			let length: number = rowData.length;
			let offsetX: number = 0;
			let offsetY: number = Math.imul(row, rowStep);
			cells[0] = 0;
			cells[1] = (offsetY << 16 >> 16);
			for (let index: number = 0; index < length; index++) {
				let direction: number = rowData[index];
				offsetX = ((offsetX + (Math.imul(GameScreen.DIR_X_TABLE[direction], tileWidth))) | 0);
				offsetY = ((offsetY + (Math.imul(GameScreen.DIR_Y_TABLE[direction], tileWidth))) | 0);
				if (direction === 1 || direction === 2) {
					cells[4] = (offsetX << 16 >> 16);
				}
			}
			cells[2] = (offsetX << 16 >> 16);
			cells[3] = (offsetY << 16 >> 16);
		}
		this.seedTimer = 1;
		this.seedCursorAnimator.play(624, 20);
	}
	drawSeedGame(gfx: Graphics): void {
		let tileWidth: number = 0;
		let rowStep: number = 0;
		let totalHeight: number = Math.imul((rowStep = (tileWidth = ((SpriteRenderer.frameWidth(630, 0) / GameScreen.DIR_X_TABLE[0]) | 0)) << 2), this.plotCount);
		let halfHeight: number = 0;
		let rowY: number = (((halfHeight = ((((this.panelTop + this.panelBottom) | 0) - totalHeight) | 0) >> 1) + rowStep) | 0);
		let startX: number = 0;
		let startY: number = (((startX = ((((this.viewX + (this.viewWidth >> 1)) | 0) - (this.viewWidth >> 2)) | 0)) + Math.imul(tileWidth, 11)) | 0);
		GameScreen.fillTiled(gfx, ((this.viewX + 10) | 0), ((this.panelTop + 0) | 0), ((this.viewWidth - 20) | 0), ((((this.panelBottom - this.panelTop) | 0) - 0) | 0), 623);
		for (let row: number = 0; row < this.plotCount; row++) {
			let posX: number = startX;
			let posY: number = ((rowY + Math.imul(row, rowStep)) | 0);
			SpriteRenderer.drawFrame(gfx, 627, 0, startX, posY);
			let rowData: Int8Array = this.seedRows[row];
			for (let index: number = 0; index < rowData.length; index++) {
				let direction: number = rowData[index];
				SpriteRenderer.drawFrame(gfx, 630, direction, posX, posY);
				posX = ((posX + (Math.imul(GameScreen.DIR_X_TABLE[direction], tileWidth))) | 0);
				posY = ((posY + (Math.imul(GameScreen.DIR_Y_TABLE[direction], tileWidth))) | 0);
			}
		}
		let cursorY: number = ((halfHeight + FixedMath.mul(totalHeight, FixedMath.div(this.cursorPosition, 131072))) | 0);
		this.seedCursorAnimator.draw(gfx, startY, cursorY);
		this.drawEffectsScaled(gfx, 1, startX, rowY);
	}
	handleSeedInput(keyState: number, actionMask: number): void {
		this.setMoveFlags(keyState);
		if (this.seedCursorAnimator.isLooping() && this.matchesInput(keyState, actionMask, 4128, 0)) {
			this.snapSeedCursor();
		}
		if (this.canvas.cheatToggled && this.matchesInput(keyState, actionMask, 1, 0)) {
			this.setMiniGameStep(2);
		}
	}
	updateSeedGame(delta: number): void {
		switch (this.miniStep) {
			case 1:
				this.seedCursorAnimator.advance(delta);
				let speed: number = ((this.neighborhood.careerPoints(3) / 5) | 0);
				if (!this.seedCursorAnimator.isRunning()) {
					this.seedCursorAnimator.play(624, 20);
					this.plantSeed();
				}
				this.advanceEffects(delta, 1);
				if (this.seedCursorAnimator.isLooping()) {
					let tileWidth: number = Math.imul(delta, ((((this.downPressed ? 65 : 0) + (this.upPressed ? -65 : 0)) | 0)));
					let rowStep: number = 0;
					if ((rowStep = ((this.cursorPosition + tileWidth) | 0)) < 0) {
						rowStep = 0;
					}
					else {
						if (rowStep > 131072) {
							rowStep = 131072;
						}
					}
					this.cursorPosition = rowStep;
				}
				let anyRunning2: boolean = false;
				for (let row: number = 1; row < 24; row++) {
					let animator: Animator = null!;
					if ((animator = this.effectAnimators[row]).isRunning()) {
						anyRunning2 = true;
						let position: Int32Array = null!;
						let pathIndex: number = 0;
						if ((pathIndex = (position = this.effectPositions[row])[2]) !== -1) {
							let anyRunning: boolean = (pathIndex & 65536) !== 0;
							pathIndex = (pathIndex & 65535);
							let cells: Int16Array = this.seedCells[pathIndex];
							let endX: number = 0;
							let endY: number = 0;
							let startX: number = 0;
							let startY: number = 0;
							if (anyRunning) {
								endX = cells[2] << 16;
								endY = cells[3] << 16;
								startX = cells[0] << 16;
								startY = cells[1] << 16;
							}
							else {
								endX = cells[0] << 16;
								endY = cells[1] << 16;
								startX = cells[2] << 16;
								startY = cells[3] << 16;
							}
							let seedX: number = cells[4] << 16;
							let posX: number = position[0];
							let posY: number = position[1];
							let dirX: number = 0;
							let dirY: number = 0;
							if (posX === seedX) {
								dirX = 0;
								dirY = FixedMath.sign(((startY - endY) | 0));
							}
							else {
								dirX = FixedMath.sign(((startX - endX) | 0));
								dirY = 0;
							}
							let step: number = (((speed >> 1) + 65536) | 0);
							let newX: number = FixedMath.mul(Math.imul(delta, 1310), step);
							let newY: number = ((posX + Math.imul(dirX, newX)) | 0);
							let threshold: number = ((posY + Math.imul(dirY, newX)) | 0);
							if ((dirX <= 0 || posX >= seedX || newY <= seedX) && (dirX >= 0 || posX <= seedX || newY >= seedX)) {
								if (dirX > 0 && posX < startX && newY >= startX || dirX < 0 && posX > startX && newY <= startX) {
									animator.setRunning(false);
									this.canvas.vibrateShort();
									this.setMiniGameStep(3);
									return;
								}
								if (dirY > 0 && posY < startY && threshold >= startY || dirY < 0 && posY > startY && threshold <= startY) {
									threshold = startY;
									newY = ((seedX + FixedMath.sign(((startX - endX) | 0))) | 0);
								}
							}
							else {
								newY = seedX;
							}
							for (let otherIndex: number = 1; otherIndex < 24; otherIndex++) {
								let otherAnimator: Animator = this.effectAnimators[otherIndex];
								let otherPosition: Int32Array = null!;
								if (otherIndex !== row && otherAnimator.isRunning() && ((otherPosition = this.effectPositions[otherIndex])[2] & 65535) === pathIndex && ((Math.abs(((otherPosition[0] - newY) | 0)) + Math.abs(((otherPosition[1] - threshold) | 0))) | 0) < 196608) {
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
					this.seedTimer = ((this.seedTimer - delta) | 0);
					if (this.seedTimer <= 0) {
						let rowY: number = this.canvas.randomRange(0, ((this.plotCount - 1) | 0));
						this.spawnSeedEffect(rowY, false);
						this.seedCount--;
						this.progressValue++;
						let timer: number = Math.imul(5000, (((65536 - (speed >> 1)) | 0))) >> 16;
						this.seedTimer = ((1000 + this.canvas.randomRange(0, timer)) | 0);
						return;
					}
				}
				else {
					if (!anyRunning2) {
						this.setMiniGameStep(2);
					}
				}
		}
	}
	snapSeedCursor(): void {
		let step: number = ((131072 / this.plotCount) | 0);
		let value: number = ((Math.imul(Math.min(((this.cursorPosition / step) | 0), ((this.plotCount - 1) | 0)), step) + (step >> 1)) | 0);
		this.cursorPosition = value;
		this.seedCursorAnimator.play(625, 16);
	}
	plantSeed(): void {
		let step: number = ((131072 / this.plotCount) | 0);
		let index: number = GameCanvas.indexOfByte(Math.min(((this.cursorPosition / step) | 0), ((this.plotCount - 1) | 0)), this.seedTypes);
		this.spawnSeedEffect(index, true);
	}
	spawnSeedEffect(plot: number, atStart: boolean): void {
		let cells: Int16Array = this.seedCells[plot];
		let posX: number = 0;
		let posY: number = 0;
		let frameId: number = 0;
		if (atStart) {
			posX = cells[2];
			posY = cells[3];
			frameId = 628;
		}
		else {
			posX = cells[0];
			posY = cells[1];
			frameId = 626;
		}
		let slot: number = 0;
		if ((slot = this.playEffect(frameId, posX << 16, posY << 16, 20)) !== -1) {
			this.effectPositions[slot][2] = plot | (atStart ? 65536 : 0);
		}
	}
	setupVehicleGame(): void {
		let canvas: GameCanvas = this.canvas;
		this.miniType = 1;
		let wobbleSpeed: number = 0;
		let wobbleAmount: number = 0;
		let duration: number = 0;
		switch (this.neighborhood.residentLevel(1)) {
			case 0:
			default:
				wobbleSpeed = 32768;
				wobbleAmount = 19660;
				duration = 45000;
				break;
			case 1:
				wobbleSpeed = 32768;
				wobbleAmount = 29491;
				duration = 40000;
				break;
			case 2:
				wobbleSpeed = 39321;
				wobbleAmount = 39321;
				duration = 40000;
				break;
			case 3:
				wobbleSpeed = 49152;
				wobbleAmount = 52428;
				duration = 35000;
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
		let tileWidth: number = SpriteRenderer.frameWidth(603, 0);
		let fieldWidth: number = Math.imul(4, tileWidth);
		let fieldHeight: number = Math.imul(4, tileWidth);
		let cellWidth: number = SpriteRenderer.frameWidth(606, 0);
		let cellHeight: number = SpriteRenderer.frameHeight(606, 0);
		let cellSpanX: number = cellWidth;
		let cellSpanY: number = cellHeight;
		let rangeX: number = ((fieldWidth - cellWidth) | 0);
		let rangeY: number = ((fieldHeight - cellHeight) | 0);
		for (let cell: number = 0; cell < 5; cell++) {
			let position: Int32Array = null!;
			(position = this.effectPositions[((cell + 2) | 0)])[0] = canvas.randomRange(cellSpanX, rangeX);
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
	drawVehicleGame(gfx: Graphics): void {
		let cellWidth: number = SpriteRenderer.frameWidth(603, 0);
		let fieldWidth: number = Math.imul(4, cellWidth);
		let fieldHeight: number = Math.imul(4, cellWidth);
		GameScreen.fillTiled(gfx, ((this.viewX + 15) | 0), ((this.panelTop + 5) | 0), ((this.viewWidth - 30) | 0), ((((this.panelBottom - this.panelTop) | 0) - 10) | 0), 603);
		let centerX: number = 0;
		let fieldX: number = (((centerX = ((this.viewX + (this.viewWidth >> 1)) | 0)) - (fieldWidth >> 1)) | 0);
		let fieldY: number = ((((this.panelTop + this.panelBottom) | 0) - fieldHeight) | 0) >> 1;
		for (let cell: number = 2; cell < 24; cell++) {
			let position: Int32Array = null!;
			let cellValue: number = 0;
			if ((cellValue = (position = this.effectPositions[cell])[2]) !== -1) {
				SpriteRenderer.drawFrame(gfx, 606, cellValue, ((fieldX + position[0]) | 0), ((fieldY + position[1]) | 0));
			}
		}
		let cursorX: number = ((this.gridX + this.offsetX) | 0) >> 3;
		let cursorY: number = ((this.gridY + this.offsetY) | 0) >> 3;
		let posX: number = FixedMath.mul(cursorX, fieldWidth);
		let posY: number = FixedMath.mul(cursorY, fieldHeight);
		this.vehicleAnimator.draw(gfx, ((fieldX + posX) | 0), ((fieldY + posY) | 0));
		this.drawEffects(gfx, 2, fieldX, fieldY);
	}
	handleVehicleInput(keyState: number, actionMask: number): void {
		this.setMoveFlags(keyState);
		if (this.vehicleAnimator.animId !== 604 && this.matchesInput(keyState, actionMask, 4128, 0)) {
			this.vehicleAnimator.play(604, 16);
		}
		if (this.canvas.cheatToggled && this.matchesInput(keyState, actionMask, 1, 0)) {
			this.setMiniGameStep(2);
		}
	}
	updateVehicleGame(delta: number): void {
		switch (this.miniStep) {
			case 1:
				this.advanceEffects(delta, 2);
				this.vehicleAnimator.advance(delta);
				if (!this.vehicleAnimator.isRunning()) {
					this.vehicleAnimator.play(605, 20);
					this.advanceVehicleFrame(this.hoverCell);
				}
				if (this.vehicleAnimator.animId !== 604) {
					let moveX: number = Math.imul(delta, ((((this.rightPressed ? 229 : 0) + (this.leftPressed ? -229 : 0)) | 0)));
					let moveY: number = Math.imul(delta, ((((this.downPressed ? 229 : 0) + (this.upPressed ? -229 : 0)) | 0)));
					this.gridX = Math.max(0, Math.min(524288, ((this.gridX + moveX) | 0)));
					this.gridY = Math.max(0, Math.min(524288, ((this.gridY + moveY) | 0)));
					this.phaseX = ((this.phaseX + delta) | 0);
					this.phaseY = ((this.phaseY + delta) | 0);
					let phaseX: number = FixedMath.mul(this.wobbleSpeed, 1966);
					let phaseY: number = FixedMath.mul(this.wobbleSpeed, 1638);
					if (this.phaseX > phaseX) {
						this.phaseX = 0;
					}
					if (this.phaseX > phaseX >> 1) {
						let smoothValue: number = FixedMath.smoothStep(phaseX >> 1, phaseX, this.phaseX);
						this.offsetX = ((this.wobbleAmount - FixedMath.mul(smoothValue, this.wobbleAmount)) | 0);
					}
					else {
						let smoothValue2: number = FixedMath.smoothStep(0, phaseX >> 1, this.phaseX);
						this.offsetX = FixedMath.mul(smoothValue2, this.wobbleAmount);
					}
					this.offsetX = ((this.offsetX - (this.wobbleAmount >> 1)) | 0);
					if (this.phaseY > phaseY) {
						this.phaseY = 0;
					}
					if (this.phaseY > phaseY >> 1) {
						let smoothHigh: number = FixedMath.smoothStep(phaseY >> 1, phaseY, this.phaseY);
						this.offsetY = ((this.wobbleAmount - FixedMath.mul(smoothHigh, this.wobbleAmount)) | 0);
					}
					else {
						let smoothLow: number = FixedMath.smoothStep(0, phaseY >> 1, this.phaseY);
						this.offsetY = FixedMath.mul(smoothLow, this.wobbleAmount);
					}
					this.offsetY = ((this.offsetY - (this.wobbleAmount >> 1)) | 0);
				}
				let cellWidth: number = SpriteRenderer.frameWidth(603, 0);
				let fieldWidth: number = Math.imul(4, cellWidth);
				let fieldHeight: number = Math.imul(4, cellWidth);
				let cursorX: number = ((this.gridX + this.offsetX) | 0) >> 3;
				let cursorY: number = ((this.gridY + this.offsetY) | 0) >> 3;
				let posX: number = FixedMath.mul(cursorX, fieldWidth);
				let posY: number = FixedMath.mul(cursorY, fieldHeight);
				let noCells: boolean = true;
				let hoverCell: number = -1;
				let bestDistance: number = -1;
				for (let cell: number = 2; cell < 24; cell++) {
					let position: Int32Array = null!;
					if ((position = this.effectPositions[cell])[2] !== -1) {
						noCells = false;
						let offsetX: number = ((position[0] - posX) | 0);
						let offsetY: number = ((position[1] - posY) | 0);
						let distance: number = ((Math.imul(offsetX, offsetX) + Math.imul(offsetY, offsetY)) | 0);
						if (hoverCell === -1 || distance < bestDistance) {
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
				this.progressValue = ((this.progressValue + delta) | 0);
				if (this.progressValue > this.progressMax) {
					this.canvas.vibrateShort();
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
	advanceVehicleFrame(cell: number): void {
		if (cell !== -1) {
			let position: Int32Array = null!;
			let value: number = (position = this.effectPositions[cell])[2];
			if (++value >= SpriteRenderer.frameCount(606)) {
				value = -1;
				this.effectAnimators[cell].play(607, 16);
			}
			position[2] = value;
		}
	}
	openVehicleInfo(entity: Entity): void {
		let objectType: number = entity.type;
		if (entity.type === 114 && !this.canvas.gameStarted) {
			this.chooseDialogOptionAt(785, 784);
		}
		else {
			this.targetObjectType = objectType;
			this.interactionAmount = 0;
			this.onTick(17);
			this.setVehiclePhase(0);
		}
	}
	setVehiclePhase(phase: number): void {
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
	drawVehiclePage(gfx: Graphics): void {
		switch (this.plantPhase) {
			case 0:
				this.drawVehicleDetails(gfx);
			default:
				this.drawSoftKeyHint(gfx);
		}
	}
	drawVehicleDetails(gfx: Graphics): void {
		let canvas: GameCanvas = this.canvas;
		let lot: Lot = this.currentLot;
		let neighborhood: Neighborhood = this.neighborhood;
		let objectIndex: number = lot.bitForSound(this.targetObjectType, this.selectedIndex);
		this.setValue(1, this.currentLot.spriteIdAt(this.targetObjectType));
		let increase: number = 0;
		if ((increase = lot.categoryTypeAt(objectIndex)) > 0) {
			let label: SdkString = canvas.beginString();
			canvas.formatNumber(increase);
			canvas.setString(-9, label);
			this.setValue(2, -9);
		}
		else {
			this.setValue(2, 711);
		}
		let decrease: number = 0;
		if ((decrease = lot.categorySubTypeAt(objectIndex)) > 0) {
			let secondLabel: SdkString = canvas.beginString();
			canvas.formatNumber(decrease);
			canvas.setString(-8, secondLabel);
			this.setValue(3, -8);
		}
		else {
			this.setValue(3, 711);
		}
		let delta: number = 0;
		if (this.interactionAmount >= 0) {
			delta = Math.imul(this.interactionAmount, increase);
		}
		else {
			delta = Math.imul(((this.interactionAmount * -1) | 0), decrease);
		}
		let thirdLabel: SdkString = canvas.beginString();
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
	showVehicleList(): void {
		this.beginList(this.currentLot.countBySoundMask(this.targetObjectType), 18, 0);
	}
	handleVehicleListInput(keyState: number, actionMask: number): void {
		let neighborhood: Neighborhood = this.neighborhood;
		let lot: Lot = this.currentLot;
		switch (this.plantPhase) {
			case 0:
				if (this.matchesInput(keyState, actionMask, 0, 4)) {
					this.setVehiclePhase(1);
					return;
				}
				else {
					if (this.scrollList(keyState, actionMask)) {
						this.adjustVehicleAmount(0);
						return;
					}
					else {
						if (this.matchesInput(keyState, actionMask, 4128, 12288) && this.interactionAmount !== 0) {
							let objectIndex: number = lot.bitForSound(this.targetObjectType, this.selectedIndex);
							let delta: number = Math.imul(this.interactionAmount, (this.interactionAmount > 0 ? lot.categoryTypeAt(objectIndex) : lot.categorySubTypeAt(objectIndex)));
							neighborhood.addItem(objectIndex, this.interactionAmount);
							if (this.interactionAmount > 0) {
								neighborhood.onInteractionResult(objectIndex);
							}
							neighborhood.addMoney(((delta * -1) | 0));
							this.adjustVehicleAmount(0);
							return;
						}
						else {
							if (this.matchesInput(keyState, actionMask, 32784, 0)) {
								this.adjustVehicleAmount(((this.interactionAmount - 1) | 0));
							}
							if (this.matchesInput(keyState, actionMask, 65600, 0)) {
								this.adjustVehicleAmount(((this.interactionAmount + 1) | 0));
							}
						}
					}
				}
		}
	}
	adjustVehicleAmount(amount: number): void {
		let neighborhood: Neighborhood = this.neighborhood;
		let lot: Lot = this.currentLot;
		let objectIndex: number = this.currentLot.bitForSound(this.targetObjectType, this.selectedIndex);
		let money: number = neighborhood.money;
		let increase: number = lot.categoryTypeAt(objectIndex);
		let decrease: number = lot.categorySubTypeAt(objectIndex);
		let maxAmount: number = 0;
		if (increase <= 0) {
			maxAmount = 0;
		}
		else {
			maxAmount = Math.min(Math.min(((lot.categoryFlagAt(objectIndex) - neighborhood.itemCount(objectIndex)) | 0), ((money / increase) | 0)), lot.categoryFlagAt(objectIndex));
		}
		let minAmount: number = 0;
		if (decrease <= 0) {
			minAmount = 0;
		}
		else {
			minAmount = ((neighborhood.itemCount(objectIndex) * -1) | 0);
		}
		this.interactionAmount = FixedMath.clamp(amount, minAmount, maxAmount);
		this.updateInputMasks();
	}
	openCheatsDialog(): void {
		if (GameScreen.screenState === 5 && (!this.mainMenuOpen || this.mainMenuPage !== 5)) {
			this.closeOpenMenus();
			this.openMainMenu();
			this.setMainMenuPage(5);
		}
	}
	buildCheatMenu(): void {
		let canvas: GameCanvas = this.canvas;
		let neighborhood: Neighborhood = this.neighborhood;
		let ids: Int16Array = new Int16Array(35);
		let labels: SdkString[] = new Array(35).fill(null);
		GameCanvas.initListWith(ids, 123, ((canvas.menuRowSpacing - 4) | 0));
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
		let simId: number = neighborhood.watchedSim;
		if (neighborhood.watchedSim !== -1) {
			let simName: string = canvas.getString(neighborhood.simNameText(simId)).toString();
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
	handleCheatMenuInput(keyState: number, actionMask: number): void {
		let canvas: GameCanvas = this.canvas;
		this.canvas.navigateLeftRight(this.cheatLabels, keyState);
		let neighborhood: Neighborhood = this.neighborhood;
		let option: number = this.cheatLabels[2];
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
					let breakList: Entity[] = this.getEntities();
					for (let breakIndex: number = 0; breakIndex < breakList.length; breakIndex++) {
						if (breakList[breakIndex].hasFlag(67108864) && !breakList[breakIndex].hasState(256)) {
							breakList[breakIndex].turnOnPrimary();
						}
					}
					this.closeMainMenu();
					return;
				case 6:
					let fixList: Entity[] = this.getEntities();
					for (let fixIndex: number = 0; fixIndex < fixList.length; fixIndex++) {
						if (fixList[fixIndex].hasFlag(67108864) && fixList[fixIndex].hasState(256)) {
							fixList[fixIndex].turnOffPrimary();
						}
					}
					this.closeMainMenu();
					return;
				case 7:
					let dirtyList: Entity[] = this.getEntities();
					for (let dirtyIndex: number = 0; dirtyIndex < dirtyList.length; dirtyIndex++) {
						if (dirtyList[dirtyIndex].hasFlag(134217728) && !dirtyList[dirtyIndex].hasState(512)) {
							dirtyList[dirtyIndex].turnOnSecondary();
						}
					}
					this.closeMainMenu();
					return;
				case 8:
					let cleanList: Entity[] = this.getEntities();
					for (let cleanIndex: number = 0; cleanIndex < cleanList.length; cleanIndex++) {
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
					neighborhood.applyCommand(this.cheatLabels[((5 + option) | 0)]);
					this.closeMainMenu();
			}
		}
		else {
			if (this.matchesInput(keyState, actionMask, 98384, 0)) {
				let decrease: boolean = (keyState & 32784) !== 0;
				let bulkApplied: boolean = false;
				if (option === 11) {
					neighborhood.applyCommand(decrease ? 13 : 14);
					this.cheatText[11] = GameCanvas.createString("< Cooking: " + neighborhood.residentLevel(0) + " >");
				}
				else {
					if (option === 12) {
						neighborhood.applyCommand(decrease ? 15 : 16);
						this.cheatText[12] = GameCanvas.createString("< Cleaning: " + neighborhood.residentLevel(1) + " >");
					}
					else {
						if (option === 13) {
							neighborhood.applyCommand(decrease ? 17 : 18);
							this.cheatText[13] = GameCanvas.createString("< Fishing: " + neighborhood.residentLevel(2) + " >");
						}
						else {
							if (option !== 14) {
								if (option === 15) {
									neighborhood.applyCommand(decrease ? 21 : 22);
									this.cheatText[15] = GameCanvas.createString("< Gardening: " + neighborhood.residentLevel(4) + " >");
								}
								return;
							}
							neighborhood.applyCommand(decrease ? 19 : 20);
							this.cheatText[14] = GameCanvas.createString("< Repairing: " + neighborhood.residentLevel(3) + " >");
						}
					}
				}
			}
			else {
				if (this.matchesInput(keyState, actionMask, 0, 4)) {
					this.closeMainMenu();
				}
			}
		}
	}
	drawCheatMenu(gfx: Graphics): void {
		this.drawTwoColumnList(gfx, this.cheatLabels, 0, this.cheatText);
	}
	constructor(gameCanvas: GameCanvas) {
		super(gameCanvas);
	}
}
