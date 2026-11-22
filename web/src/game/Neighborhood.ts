// Generated from Neighborhood.java by tools/web/JavaToTs.java; keep this file in sync.
import { DataInputStream, IOException, arraycopy, StringBuffer, DataOutputStream } from '../runtime';
import { GameCanvas } from './GameCanvas';
import { ResourceLoader } from './ResourceLoader';
import { FixedMath } from './FixedMath';
import { Lot } from './Lot';
import { GameScreen } from './GameScreen';
import { SdkString } from './SdkString';
import { Entity } from './Entity';
import { Sim } from './Sim';

export class Neighborhood {
	canvas: GameCanvas = null!;
	residentIds: Int16Array = null!;
	residentGrowthSteps: Int32Array[] = null!;
	residentLook: Int16Array[] = null!;
	residentLevelValues: Int16Array[] = null!;
	lotNameTextIds: Int16Array = null!;
	lotSubtitleTextIds: Int16Array = null!;
	lotOccupantTypes: Int16Array = null!;
	lotKinds: Int8Array = null!;
	lotSpritesLow: Int16Array = null!;
	lotSpritesHigh: Int16Array = null!;
	lotExtraSprites: Int16Array = null!;
	lotHourInteractions: Int16Array[] = null!;
	lotHourIncomeValue: Int16Array[] = null!;
	lotHourDayBits: Int16Array[] = null!;
	lotHourStarts: Int16Array[] = null!;
	lotHourEnds: Int16Array[] = null!;
	lotHourRequirements: Int8Array[][] = null!;
	residentLabelTextIds: Int16Array = null!;
	residentInfoTextIds: Int16Array = null!;
	residentSpritesA: Int16Array = null!;
	residentSpritesB: Int16Array = null!;
	achievementTextIds: Int16Array = null!;
	achievementFlags: Int16Array = null!;
	needCount: number = 0;
	needIds: Int16Array = null!;
	needInitialValues: Int32Array = null!;
	needDecayRates: Int32Array = null!;
	needMoodFlags: Int16Array = null!;
	needThresholds: Int32Array = null!;
	needLabelTextIds: Int16Array = null!;
	needHintTextIds: Int16Array = null!;
	achievementTriggerIds: Int8Array = null!;
	taskCount: number = 0;
	taskIds: Int16Array = null!;
	taskDurations: Int32Array = null!;
	taskFollowUps: Int8Array = null!;
	taskAchievements: Int8Array = null!;
	taskFlags: Int8Array = null!;
	taskNeedIndices: Int8Array[] = null!;
	taskNeedDeltas: Int32Array[] = null!;
	taskMoodIndices: Int8Array[] = null!;
	taskMoodDeltas: Int8Array[] = null!;
	taskLinkIndices: Int8Array[] = null!;
	taskLinkDeltas: Int32Array[] = null!;
	itemNameTextIds: Int16Array = null!;
	itemSpriteIds: Int16Array = null!;
	simNeedCount: number = 0;
	stateSpriteIds: Int16Array = null!;
	stateFlags: Int8Array = null!;
	stateBadgeIds: Int16Array = null!;
	stateLabelTextIds: Int16Array = null!;
	stateTransitionStates: Int8Array[] = null!;
	stateTransitionNeeds: Int8Array[] = null!;
	stateTransitionThresholds: Int16Array[] = null!;
	simCount: number = 0;
	simNameTextIds: Int16Array = null!;
	simSpareBytes: Int8Array = null!;
	simAttributes: Int8Array[] = null!;
	simHomeLots: Int16Array = null!;
	simFlags: Int8Array = null!;
	simPortraitIds: Int8Array = null!;
	simLots: Int8Array = null!;
	simHours: Int8Array = null!;
	simKnownIds: Int8Array[] = null!;
	simCardList: Int8Array[] = null!;
	portraitCount: number = 0;
	portraitDataValue: Int8Array[] = null!;
	needTextIds: Int16Array = null!;
	interactionCount: number = 0;
	interactionTextIds: Int16Array = null!;
	interactionSprites: Int16Array = null!;
	interactionFlags: Int32Array = null!;
	interactionAltTextIds: Int16Array = null!;
	interactionStateReqs: Int8Array[] = null!;
	interactionNeedReqs: Int8Array[] = null!;
	interactionFlagReqs: Int32Array[] = null!;
	interactionConditionFlags: Int16Array[] = null!;
	interactionNeedGains: Int8Array[] = null!;
	interactionNeedLosses: Int8Array[] = null!;
	interactionMoodIndex: Int8Array[] = null!;
	interactionMoodGain: Int8Array[] = null!;
	interactionResultTextIds: Int16Array[] = null!;
	interactionVariants: Int8Array[] = null!;
	visitCardTimeCosts: Int16Array = null!;
	visitCardMinuteLimits: Int8Array = null!;
	visitCardOtherLimits: Int8Array = null!;
	visitCardUp: Int8Array = null!;
	visitCardTextA: Int16Array = null!;
	visitCardDown: Int8Array = null!;
	visitCardTextB: Int16Array = null!;
	visitCardLeft: Int8Array = null!;
	visitCardTextC: Int16Array = null!;
	visitCardRight: Int8Array = null!;
	visitCardTextD: Int16Array = null!;
	cardFlags: Int8Array = null!;
	cardLabelTextIds: Int16Array = null!;
	cardAltTextIds: Int16Array = null!;
	cardRequiredLots: Int16Array = null!;
	cardRewardItems: Int16Array = null!;
	cardRewardMoney: Int16Array = null!;
	cardRequirementIds: Int16Array[] = null!;
	static WEEKDAY_BITS: Int32Array = Int32Array.from([4, 8, 16, 32, 64, 128, 256]);
	static WEEKDAY_TEXT_IDS: Int32Array = Int32Array.from([653, 654, 655, 656, 657, 658, 659]);
	timeAccumulator: number = 0;
	gameTime: number = 0;
	money: number = 0;
	moneySpent: number = 0;
	paused: boolean = false;
	lastCareerLevel: number = 0;
	lastPromotionDay: number = 0;
	itemCounts: Int8Array = null!;
	lastPurchasedItem: number = 0;
	residentGrowth: Int32Array = null!;
	static FEATURE_FLAGS: Int32Array = Int32Array.from([1, 2]);
	static FEATURE_TEXT_IDS: Int32Array = Int32Array.from([665, 666]);
	lastVisitDay: number = 0;
	tickCount: number = 0;
	idleTicks: number = 0;
	lotLastHour: Int8Array = null!;
	suggestedLot: number = 0;
	suggestionTimer: number = 0;
	selectedResident: number = 0;
	pendingAchievementId: number = 0;
	randomSeed: number = 0;
	achievementTimer: number = 0;
	achievementSlots: Int8Array = null!;
	usedTaskBits: number = 0;
	milestoneFlags: number = 0;
	achievementTimes: Int32Array = null!;
	altEventCount: number = 0;
	otherEventCount: number = 0;
	recentTaskTimes: Int32Array = null!;
	visitedLotBits: number = 0;
	needTimer: number = 0;
	needValues: Int32Array = null!;
	needDecay: Int32Array = null!;
	needOffsets: Int32Array = null!;
	needModifiers: Int32Array = null!;
	activeTasks: Int16Array = null!;
	taskTimers: Int32Array = null!;
	static MOOD_TEXT_IDS: Int16Array = Int16Array.from([387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398]);
	moodDeltas: Int32Array = null!;
	moodIndex: number = 0;
	moodValue: number = 0;
	simStates: Int8Array = null!;
	simMilestones: Int16Array = null!;
	simNeeds: Int32Array[] = null!;
	simNeedDecay: Int32Array[] = null!;
	simActionHistory: Int16Array[] = null!;
	simRepeatCounts: Int8Array[] = null!;
	simTickTimer: number = 0;
	watchedSim: number = 0;
	watchedSimTimer: number = 0;
	static REACTION_TEXT_IDS: Int16Array = Int16Array.from([572, 571, 570, 196, 573, 574, 575]);
	static COMMENT_TEXT_IDS: Int16Array = Int16Array.from([1, 5, 2, 165, 3, 9, 7, 289, 8, 261, 9, 321, 14, 257, 11, 1, 12, 289, 15, 257, 16, 261, 17, 257, 18, 257, 19, 1, 20, 513, 21, 257, 22, 257, 24, 257, 25, 305, 26, 1073, 27, 257, 196, 2]);
	cardPerformers: Int8Array = null!;
	cardTimes: Int32Array = null!;
	celebrationTimer: number = 0;
	pendingTips: Int16Array = null!;
	tipValues: Int32Array = null!;
	tipTimes: Int32Array = null!;
	setTipValue(tipKey: number, tipValue: number): void {
		let tipKeyCopy: number = 0;
		let slot: number = 0;
		if ((slot = GameCanvas.indexOfShort(tipKeyCopy = 0 | tipKey, this.pendingTips)) !== -1) {
			let highBits: number = this.pendingTips[slot] & 65280;
			this.pendingTips[slot] = ((highBits | tipValue) << 16 >> 16);
		}
	}
	static readShort(input: DataInputStream): number {
		return input.readShort();
	}
	loadData(): void {
		let resourceLoader: ResourceLoader = this.canvas.resources;
		try {
			let input: DataInputStream = null!;
			let residentCount: number = 0;
			let residentIds: Int16Array = new Int16Array(residentCount = (input = new DataInputStream(resourceLoader.openStream(61))).readByte());
			let residentGrowthSteps: Int32Array[] = new Array(residentCount).fill(null);
			let residentLooks: Int16Array[] = new Array(residentCount).fill(null);
			let residentLevelValues: Int16Array[] = new Array(residentCount).fill(null);
			for (let residentIndex: number = 0; residentIndex < residentCount; residentIndex++) {
				residentIds[residentIndex] = Neighborhood.readShort(input);
				let lookBuf: Int16Array = new Int16Array(3);
				for (let lookIndex: number = 0; lookIndex < lookBuf.length; lookIndex++) {
					lookBuf[lookIndex] = Neighborhood.readShort(input);
				}
				residentLooks[residentIndex] = lookBuf;
				let growthBuf: Int32Array = new Int32Array(5);
				for (let growthIndex: number = 0; growthIndex < 5; growthIndex++) {
					growthBuf[growthIndex] = input.readInt();
				}
				residentGrowthSteps[residentIndex] = growthBuf;
				let levelBuf: Int16Array = new Int16Array(6);
				for (let levelIndex: number = 0; levelIndex < 6; levelIndex++) {
					levelBuf[levelIndex] = Neighborhood.readShort(input);
				}
				residentLevelValues[residentIndex] = levelBuf;
			}
			let lotCount: number = 0;
			let lotNameTextIds: Int16Array = new Int16Array(lotCount = input.readByte());
			let lotSubtitleTextIds: Int16Array = new Int16Array(lotCount);
			let lotOccupantTypes: Int16Array = new Int16Array(lotCount);
			let lotKinds: Int8Array = new Int8Array(lotCount);
			let lotSpritesLow: Int16Array = new Int16Array(lotCount);
			let lotSpritesHigh: Int16Array = new Int16Array(lotCount);
			let lotExtraSprites: Int16Array = new Int16Array(lotCount);
			let lotHourInteractions: Int16Array[] = new Array(lotCount).fill(null);
			let lotHourIncome: Int16Array[] = new Array(lotCount).fill(null);
			let lotHourDayBits: Int16Array[] = new Array(lotCount).fill(null);
			let lotHourStarts: Int16Array[] = new Array(lotCount).fill(null);
			let lotHourEnds: Int16Array[] = new Array(lotCount).fill(null);
			let lotHourRequirements: Int8Array[][] = new Array(lotCount).fill(null);
			for (let lotIndex: number = 0; lotIndex < lotCount; lotIndex++) {
				lotNameTextIds[lotIndex] = Neighborhood.readShort(input);
				lotSubtitleTextIds[lotIndex] = Neighborhood.readShort(input);
				lotOccupantTypes[lotIndex] = Neighborhood.readShort(input);
				lotKinds[lotIndex] = input.readByte();
				lotSpritesLow[lotIndex] = Neighborhood.readShort(input);
				lotSpritesHigh[lotIndex] = Neighborhood.readShort(input);
				lotExtraSprites[lotIndex] = Neighborhood.readShort(input);
				let hourCount: number = 0;
				let hourInteractionBuf: Int16Array = new Int16Array(hourCount = input.readByte());
				let hourIncomeBuf: Int16Array = new Int16Array(hourCount);
				let hourDayBuf: Int16Array = new Int16Array(hourCount);
				let hourStartBuf: Int16Array = new Int16Array(hourCount);
				let hourEndBuf: Int16Array = new Int16Array(hourCount);
				let hourReqBuf: Int8Array[] = new Array(hourCount).fill(null);
				for (let hourIndex: number = 0; hourIndex < hourCount; hourIndex++) {
					hourInteractionBuf[hourIndex] = Neighborhood.readShort(input);
					hourIncomeBuf[hourIndex] = input.readShort();
					hourDayBuf[hourIndex] = input.readShort();
					let startMinutes: number = input.readShort();
					let endMinutes: number = input.readShort();
					startMinutes = ((((startMinutes % 100) | 0) + Math.imul(((startMinutes / 100) | 0), 60)) | 0);
					endMinutes = ((((endMinutes % 100) | 0) + Math.imul(((endMinutes / 100) | 0), 60)) | 0);
					hourStartBuf[hourIndex] = (startMinutes << 16 >> 16);
					hourEndBuf[hourIndex] = (endMinutes << 16 >> 16);
					let requirement: Int8Array = null!;
					(requirement = new Int8Array(4))[0] = (Neighborhood.readShort(input) << 24 >> 24);
					requirement[1] = input.readByte();
					requirement[2] = (Neighborhood.readShort(input) << 24 >> 24);
					requirement[3] = input.readByte();
					hourReqBuf[hourIndex] = requirement;
				}
				lotHourInteractions[lotIndex] = hourInteractionBuf;
				lotHourIncome[lotIndex] = hourIncomeBuf;
				lotHourDayBits[lotIndex] = hourDayBuf;
				lotHourStarts[lotIndex] = hourStartBuf;
				lotHourEnds[lotIndex] = hourEndBuf;
				lotHourRequirements[lotIndex] = hourReqBuf;
			}
			let labelCount: number = 0;
			let residentLabelTextIds: Int16Array = new Int16Array(labelCount = input.readByte());
			let residentInfoTextIds: Int16Array = new Int16Array(labelCount);
			let residentSpritesA: Int16Array = new Int16Array(labelCount);
			let residentSpritesB: Int16Array = new Int16Array(labelCount);
			for (let labelIndex: number = 0; labelIndex < labelCount; labelIndex++) {
				residentLabelTextIds[labelIndex] = Neighborhood.readShort(input);
				residentInfoTextIds[labelIndex] = Neighborhood.readShort(input);
				residentSpritesA[labelIndex] = Neighborhood.readShort(input);
				residentSpritesB[labelIndex] = input.readShort();
			}
			let achievementCount: number = 0;
			let achievementTextIds: Int16Array = new Int16Array(achievementCount = input.readByte());
			let achievementFlags: Int16Array = new Int16Array(achievementCount);
			for (let achievementIndex: number = 0; achievementIndex < achievementCount; achievementIndex++) {
				achievementTextIds[achievementIndex] = Neighborhood.readShort(input);
				achievementFlags[achievementIndex] = input.readShort();
			}
			let needCount: number = 0;
			let needIds: Int16Array = new Int16Array(needCount = input.readByte());
			let needInitialValues: Int32Array = new Int32Array(needCount);
			let needDecayRates: Int32Array = new Int32Array(needCount);
			let needMoodFlags: Int16Array = new Int16Array(needCount);
			let needThresholds: Int32Array = new Int32Array(needCount);
			let needLabelTextIds: Int16Array = new Int16Array(needCount);
			let needHintTextIds: Int16Array = new Int16Array(needCount);
			for (let needIndex: number = 0; needIndex < needCount; needIndex++) {
				needIds[needIndex] = Neighborhood.readShort(input);
				needInitialValues[needIndex] = input.readInt();
				needDecayRates[needIndex] = input.readInt();
				needMoodFlags[needIndex] = input.readShort();
				needThresholds[needIndex] = input.readInt();
				needLabelTextIds[needIndex] = Neighborhood.readShort(input);
				needHintTextIds[needIndex] = Neighborhood.readShort(input);
			}
			let achievementTriggerIds: Int8Array = new Int8Array(input.readByte());
			input.readFully(achievementTriggerIds);
			let taskCount: number = 0;
			let taskIds: Int16Array = new Int16Array(taskCount = input.readByte());
			let taskDurations: Int32Array = new Int32Array(taskCount);
			let taskFollowUps: Int8Array = new Int8Array(taskCount);
			let taskAchievements: Int8Array = new Int8Array(taskCount);
			let taskFlags: Int8Array = new Int8Array(taskCount);
			let taskNeedIndices: Int8Array[] = new Array(taskCount).fill(null);
			let taskNeedDeltas: Int32Array[] = new Array(taskCount).fill(null);
			let taskMoodIndices: Int8Array[] = new Array(taskCount).fill(null);
			let taskMoodDeltas: Int8Array[] = new Array(taskCount).fill(null);
			let taskLinkIndices: Int8Array[] = new Array(taskCount).fill(null);
			let taskLinkDeltas: Int32Array[] = new Array(taskCount).fill(null);
			for (let taskIndex: number = 0; taskIndex < taskCount; taskIndex++) {
				taskIds[taskIndex] = Neighborhood.readShort(input);
				taskDurations[taskIndex] = input.readInt();
				taskFollowUps[taskIndex] = input.readByte();
				taskAchievements[taskIndex] = input.readByte();
				taskFlags[taskIndex] = input.readByte();
				let subCount: number = 0;
				let entryIndices: Int8Array = new Int8Array(subCount = input.readByte());
				let entryValues: Int32Array = new Int32Array(subCount);
				for (let entryIndex: number = 0; entryIndex < subCount; entryIndex++) {
					entryIndices[entryIndex] = input.readByte();
					entryValues[entryIndex] = input.readInt();
				}
				taskNeedIndices[taskIndex] = entryIndices;
				taskNeedDeltas[taskIndex] = entryValues;
				entryIndices = new Int8Array(subCount = input.readByte());
				let moodDeltaBuf: Int8Array = new Int8Array(subCount);
				for (let moodIndex2: number = 0; moodIndex2 < subCount; moodIndex2++) {
					entryIndices[moodIndex2] = (Neighborhood.readShort(input) << 24 >> 24);
					moodDeltaBuf[moodIndex2] = input.readByte();
				}
				taskMoodIndices[taskIndex] = entryIndices;
				taskMoodDeltas[taskIndex] = moodDeltaBuf;
				entryIndices = new Int8Array(subCount = input.readByte());
				entryValues = new Int32Array(subCount);
				for (let linkIndex: number = 0; linkIndex < subCount; linkIndex++) {
					entryIndices[linkIndex] = input.readByte();
					entryValues[linkIndex] = input.readInt();
				}
				taskLinkIndices[taskIndex] = entryIndices;
				taskLinkDeltas[taskIndex] = entryValues;
			}
			let itemCount: number = 0;
			let itemNameTextIds: Int16Array = new Int16Array(itemCount = input.readByte());
			let itemSpriteIds: Int16Array = new Int16Array(itemCount);
			for (let itemIndex: number = 0; itemIndex < itemCount; itemIndex++) {
				itemNameTextIds[itemIndex] = Neighborhood.readShort(input);
				itemSpriteIds[itemIndex] = input.readByte();
			}
			let needLinkCount: number = input.readByte();
			let stateCount: number = 0;
			let stateSpriteIds: Int16Array = new Int16Array(stateCount = input.readByte());
			let stateFlags: Int8Array = new Int8Array(stateCount);
			let stateBadgeIds: Int16Array = new Int16Array(stateCount);
			let stateLabelTextIds: Int16Array = new Int16Array(stateCount);
			let stateTransitionStates: Int8Array[] = new Array(stateCount).fill(null);
			let stateTransitionNeeds: Int8Array[] = new Array(stateCount).fill(null);
			let stateTransitionThresholds: Int16Array[] = new Array(stateCount).fill(null);
			for (let stateIndex: number = 0; stateIndex < stateCount; stateIndex++) {
				stateSpriteIds[stateIndex] = Neighborhood.readShort(input);
				stateFlags[stateIndex] = input.readByte();
				stateBadgeIds[stateIndex] = Neighborhood.readShort(input);
				stateLabelTextIds[stateIndex] = Neighborhood.readShort(input);
				let transitionCount: number = 0;
				let transStates: Int8Array = new Int8Array(transitionCount = input.readUnsignedByte());
				let transNeeds: Int8Array = new Int8Array(transitionCount);
				let transThresholds: Int16Array = new Int16Array(transitionCount);
				for (let transitionIndex: number = 0; transitionIndex < transitionCount; transitionIndex++) {
					transStates[transitionIndex] = input.readByte();
					transNeeds[transitionIndex] = input.readByte();
					transThresholds[transitionIndex] = input.readShort();
				}
				stateTransitionStates[stateIndex] = transStates;
				stateTransitionNeeds[stateIndex] = transNeeds;
				stateTransitionThresholds[stateIndex] = transThresholds;
			}
			let simCount: number = 0;
			let simNameTextIds: Int16Array = new Int16Array(simCount = input.readByte());
			let simSpareBytes: Int8Array = new Int8Array(simCount);
			let simAttributes: Int8Array[] = Array.from({ length: simCount }, () => new Int8Array(10));
			let simHomeLots: Int16Array = new Int16Array(simCount);
			let simFlags: Int8Array = new Int8Array(simCount);
			let simPortraitIds: Int8Array = new Int8Array(simCount);
			let simLots: Int8Array = new Int8Array(simCount);
			let simHours: Int8Array = new Int8Array(simCount);
			let simKnownIds: Int8Array[] = new Array(simCount).fill(null);
			let simActionList: Int8Array[] = new Array(simCount).fill(null);
			for (let simIndex: number = 0; simIndex < simCount; simIndex++) {
				simNameTextIds[simIndex] = Neighborhood.readShort(input);
				simSpareBytes[simIndex] = input.readByte();
				for (let attributeIndex: number = 0; attributeIndex < 10; attributeIndex++) {
					simAttributes[simIndex][attributeIndex] = input.readByte();
				}
				simHomeLots[simIndex] = Neighborhood.readShort(input);
				simFlags[simIndex] = input.readByte();
				simPortraitIds[simIndex] = input.readByte();
				simLots[simIndex] = input.readByte();
				simHours[simIndex] = input.readByte();
				let knownBuf: Int8Array = new Int8Array(input.readByte());
				input.readFully(knownBuf);
				simKnownIds[simIndex] = knownBuf;
				let actionBuf: Int8Array = new Int8Array(input.readByte());
				input.readFully(actionBuf);
				simActionList[simIndex] = actionBuf;
			}
			let portraitCount: number = 0;
			let portraitData: Int8Array[] = Array.from({ length: portraitCount = input.readByte() }, () => new Int8Array(12));
			for (let portraitIndex: number = 0; portraitIndex < portraitCount; portraitIndex++) {
				for (let byteIndex: number = 0; byteIndex < 12; byteIndex++) {
					portraitData[portraitIndex][byteIndex] = input.readByte();
				}
			}
			let needTextCount: number = 0;
			let needTextIds: Int16Array = new Int16Array(needTextCount = input.readByte());
			for (let needTextIndex: number = 0; needTextIndex < needTextCount; needTextIndex++) {
				needTextIds[needTextIndex] = Neighborhood.readShort(input);
			}
			let interactionCount: number = 0;
			let interactionTextIds: Int16Array = new Int16Array(interactionCount = input.readShort());
			let interactionSprites: Int16Array = new Int16Array(interactionCount);
			let interactionFlags: Int32Array = new Int32Array(interactionCount);
			let interactionAltTextIds: Int16Array = new Int16Array(interactionCount);
			let interactionVariants: Int8Array[] = new Array(interactionCount).fill(null);
			let interactionStateReqs: Int8Array[] = new Array(interactionCount).fill(null);
			let interactionNeedReqs: Int8Array[] = new Array(interactionCount).fill(null);
			let interactionFlagReqs: Int32Array[] = new Array(interactionCount).fill(null);
			let interactionConditionFlags: Int16Array[] = new Array(interactionCount).fill(null);
			let interactionNeedGains: Int8Array[] = new Array(interactionCount).fill(null);
			let interactionNeedLosses: Int8Array[] = new Array(interactionCount).fill(null);
			let interactionMoodIndex: Int8Array[] = new Array(interactionCount).fill(null);
			let interactionMoodGain: Int8Array[] = new Array(interactionCount).fill(null);
			let interactionResultTextIds: Int16Array[] = new Array(interactionCount).fill(null);
			for (let interactionIndex: number = 0; interactionIndex < interactionCount; interactionIndex++) {
				interactionTextIds[interactionIndex] = Neighborhood.readShort(input);
				interactionSprites[interactionIndex] = Neighborhood.readShort(input);
				let variantCount: number = input.readInt();
				interactionFlags[interactionIndex] = variantCount;
				interactionAltTextIds[interactionIndex] = Neighborhood.readShort(input);
				let subEntryCount: number = 0;
				let variantBuf: Int8Array = new Int8Array(subEntryCount = input.readByte());
				let stateReqBuf: Int8Array = new Int8Array(subEntryCount);
				let flagReqBuf: Int32Array = new Int32Array(subEntryCount);
				let conditionBuf: Int16Array = new Int16Array(subEntryCount);
				let needGainBuf: Int8Array = new Int8Array(subEntryCount);
				let needLossBuf: Int8Array = new Int8Array(subEntryCount);
				let moodIndexBuf: Int8Array = new Int8Array(subEntryCount);
				let moodGainBuf: Int8Array = new Int8Array(subEntryCount);
				let resultTextBuf: Int16Array = new Int16Array(subEntryCount);
				for (let subEntryIndex: number = 0; subEntryIndex < subEntryCount; subEntryIndex++) {
					variantBuf[subEntryIndex] = input.readByte();
					stateReqBuf[subEntryIndex] = input.readByte();
					let flagValue: number = input.readInt();
					conditionBuf[subEntryIndex] = input.readShort();
					needGainBuf[subEntryIndex] = input.readByte();
					needLossBuf[subEntryIndex] = input.readByte();
					moodIndexBuf[subEntryIndex] = input.readByte();
					moodGainBuf[subEntryIndex] = input.readByte();
					resultTextBuf[subEntryIndex] = input.readShort();
					flagReqBuf[subEntryIndex] = (flagValue & 1) !== 0 ? ~flagValue : flagValue;
				}
				let variantSize: number = input.readByte();
				let variants: any = null;
				if ((variantCount & 1073741824) !== 0) {
					variants = interactionVariants[26];
				}
				else {
					variants = new Int8Array(variantSize);
					input.readFully((variants as unknown as Int8Array));
				}
				interactionStateReqs[interactionIndex] = variantBuf;
				interactionNeedReqs[interactionIndex] = stateReqBuf;
				interactionFlagReqs[interactionIndex] = flagReqBuf;
				interactionConditionFlags[interactionIndex] = conditionBuf;
				interactionNeedGains[interactionIndex] = needGainBuf;
				interactionNeedLosses[interactionIndex] = needLossBuf;
				interactionMoodIndex[interactionIndex] = moodIndexBuf;
				interactionMoodGain[interactionIndex] = moodGainBuf;
				interactionResultTextIds[interactionIndex] = resultTextBuf;
				interactionVariants[interactionIndex] = (variants as unknown as Int8Array);
			}
			let cardCount: number = 0;
			let visitCardTimeCosts: Int16Array = new Int16Array(cardCount = input.readByte());
			let visitCardMinuteLimits: Int8Array = new Int8Array(cardCount);
			let visitCardOtherLimits: Int8Array = new Int8Array(cardCount);
			let visitCardUp: Int8Array = new Int8Array(cardCount);
			let visitCardTextA: Int16Array = new Int16Array(cardCount);
			let visitCardDown: Int8Array = new Int8Array(cardCount);
			let visitCardTextB: Int16Array = new Int16Array(cardCount);
			let visitCardLeft: Int8Array = new Int8Array(cardCount);
			let visitCardTextC: Int16Array = new Int16Array(cardCount);
			let visitCardRight: Int8Array = new Int8Array(cardCount);
			let visitCardTextD: Int16Array = new Int16Array(cardCount);
			for (let cardDataIndex: number = 0; cardDataIndex < cardCount; cardDataIndex++) {
				visitCardTimeCosts[cardDataIndex] = Neighborhood.readShort(input);
				visitCardMinuteLimits[cardDataIndex] = input.readByte();
				visitCardOtherLimits[cardDataIndex] = input.readByte();
				visitCardUp[cardDataIndex] = input.readByte();
				visitCardTextA[cardDataIndex] = Neighborhood.readShort(input);
				visitCardDown[cardDataIndex] = input.readByte();
				visitCardTextB[cardDataIndex] = Neighborhood.readShort(input);
				visitCardLeft[cardDataIndex] = input.readByte();
				visitCardTextC[cardDataIndex] = Neighborhood.readShort(input);
				visitCardRight[cardDataIndex] = input.readByte();
				visitCardTextD[cardDataIndex] = Neighborhood.readShort(input);
			}
			let cardActionCount: number = 0;
			let cardFlags: Int8Array = new Int8Array(cardActionCount = input.readByte());
			let cardLabelTextIds: Int16Array = new Int16Array(cardActionCount);
			let cardAltTextIds: Int16Array = new Int16Array(cardActionCount);
			let cardRequiredLots: Int16Array = new Int16Array(cardActionCount);
			let cardRewardItems: Int16Array = new Int16Array(cardActionCount);
			let cardRewardMoney: Int16Array = new Int16Array(cardActionCount);
			let cardRequirementIds: Int16Array[] = new Array(cardActionCount).fill(null);
			for (let cardIndex: number = 0; cardIndex < cardActionCount; cardIndex++) {
				cardFlags[cardIndex] = input.readByte();
				cardLabelTextIds[cardIndex] = Neighborhood.readShort(input);
				cardAltTextIds[cardIndex] = Neighborhood.readShort(input);
				cardRequiredLots[cardIndex] = input.readShort();
				cardRewardItems[cardIndex] = Neighborhood.readShort(input);
				cardRewardMoney[cardIndex] = input.readShort();
				let requirementCount: number = 0;
				let requirementBuf: Int16Array = new Int16Array(requirementCount = input.readByte());
				for (let requirementIndex: number = 0; requirementIndex < requirementCount; requirementIndex++) {
					requirementBuf[requirementIndex] = input.readShort();
				}
				cardRequirementIds[cardIndex] = requirementBuf;
			}
			input.close();
			this.residentIds = residentIds;
			this.residentGrowthSteps = residentGrowthSteps;
			this.residentLook = residentLooks;
			this.residentLevelValues = residentLevelValues;
			this.lotNameTextIds = lotNameTextIds;
			this.lotSubtitleTextIds = lotSubtitleTextIds;
			this.lotOccupantTypes = lotOccupantTypes;
			this.lotKinds = lotKinds;
			this.lotSpritesLow = lotSpritesLow;
			this.lotSpritesHigh = lotSpritesHigh;
			this.lotExtraSprites = lotExtraSprites;
			this.lotHourInteractions = lotHourInteractions;
			this.lotHourIncomeValue = lotHourIncome;
			this.lotHourDayBits = lotHourDayBits;
			this.lotHourStarts = lotHourStarts;
			this.lotHourEnds = lotHourEnds;
			this.lotHourRequirements = lotHourRequirements;
			this.residentLabelTextIds = residentLabelTextIds;
			this.residentInfoTextIds = residentInfoTextIds;
			this.residentSpritesA = residentSpritesA;
			this.residentSpritesB = residentSpritesB;
			this.achievementTextIds = achievementTextIds;
			this.achievementFlags = achievementFlags;
			this.needCount = needCount;
			this.needIds = needIds;
			this.needInitialValues = needInitialValues;
			this.needDecayRates = needDecayRates;
			this.needMoodFlags = needMoodFlags;
			this.needThresholds = needThresholds;
			this.needLabelTextIds = needLabelTextIds;
			this.needHintTextIds = needHintTextIds;
			this.achievementTriggerIds = achievementTriggerIds;
			this.taskCount = taskCount;
			this.taskIds = taskIds;
			this.taskDurations = taskDurations;
			this.taskFollowUps = taskFollowUps;
			this.taskAchievements = taskAchievements;
			this.taskFlags = taskFlags;
			this.taskNeedIndices = taskNeedIndices;
			this.taskNeedDeltas = taskNeedDeltas;
			this.taskMoodIndices = taskMoodIndices;
			this.taskMoodDeltas = taskMoodDeltas;
			this.taskLinkIndices = taskLinkIndices;
			this.taskLinkDeltas = taskLinkDeltas;
			this.itemNameTextIds = itemNameTextIds;
			this.itemSpriteIds = itemSpriteIds;
			this.simNeedCount = needLinkCount;
			this.stateSpriteIds = stateSpriteIds;
			this.stateFlags = stateFlags;
			this.stateBadgeIds = stateBadgeIds;
			this.stateLabelTextIds = stateLabelTextIds;
			this.stateTransitionStates = stateTransitionStates;
			this.stateTransitionNeeds = stateTransitionNeeds;
			this.stateTransitionThresholds = stateTransitionThresholds;
			this.simCount = simCount;
			this.simNameTextIds = simNameTextIds;
			this.simSpareBytes = simSpareBytes;
			this.simAttributes = simAttributes;
			this.simHomeLots = simHomeLots;
			this.simFlags = simFlags;
			this.simPortraitIds = simPortraitIds;
			this.simLots = simLots;
			this.simHours = simHours;
			this.simKnownIds = simKnownIds;
			this.simCardList = simActionList;
			this.portraitCount = portraitCount;
			this.portraitDataValue = portraitData;
			this.needTextIds = needTextIds;
			this.interactionCount = interactionCount;
			this.interactionTextIds = interactionTextIds;
			this.interactionSprites = interactionSprites;
			this.interactionFlags = interactionFlags;
			this.interactionAltTextIds = interactionAltTextIds;
			this.interactionStateReqs = interactionStateReqs;
			this.interactionNeedReqs = interactionNeedReqs;
			this.interactionFlagReqs = interactionFlagReqs;
			this.interactionConditionFlags = interactionConditionFlags;
			this.interactionNeedGains = interactionNeedGains;
			this.interactionNeedLosses = interactionNeedLosses;
			this.interactionMoodIndex = interactionMoodIndex;
			this.interactionMoodGain = interactionMoodGain;
			this.interactionResultTextIds = interactionResultTextIds;
			this.interactionVariants = interactionVariants;
			this.visitCardTimeCosts = visitCardTimeCosts;
			this.visitCardMinuteLimits = visitCardMinuteLimits;
			this.visitCardOtherLimits = visitCardOtherLimits;
			this.visitCardUp = visitCardUp;
			this.visitCardTextA = visitCardTextA;
			this.visitCardDown = visitCardDown;
			this.visitCardTextB = visitCardTextB;
			this.visitCardLeft = visitCardLeft;
			this.visitCardTextC = visitCardTextC;
			this.visitCardRight = visitCardRight;
			this.visitCardTextD = visitCardTextD;
			this.cardFlags = cardFlags;
			this.cardLabelTextIds = cardLabelTextIds;
			this.cardAltTextIds = cardAltTextIds;
			this.cardRequiredLots = cardRequiredLots;
			this.cardRewardItems = cardRewardItems;
			this.cardRewardMoney = cardRewardMoney;
			this.cardRequirementIds = cardRequirementIds;
		}
		catch (ioError) {
			if (ioError instanceof IOException) {
			}
			else {
				throw ioError;
			}
		}
	}
	currentMinuteOfDay(): number {
		return Neighborhood.minuteOfDay(this.gameTime);
	}
	static minuteOfDay(time: number): number {
		return ((time % 1440) | 0);
	}
	dayOfWeek(): number {
		return Neighborhood.weekdayOf(this.gameTime);
	}
	static weekdayOf(time: number): number {
		return ((((time / 1440) | 0) % 7) | 0);
	}
	static startOfWeek(time: number): number {
		return Math.imul(((time / 1440) | 0), 1440);
	}
	dayNumber(): number {
		return (((((this.gameTime - 840) | 0)) / 1440) | 0);
	}
	addMoney(amount: number): void {
		this.money = ((this.money + amount) | 0);
		if (amount < 0) {
			this.moneySpent = ((this.moneySpent - amount) | 0);
		}
		else {
			if (this.money >= 1000) {
				this.unlockAchievement(43);
			}
			if (this.money >= 5000) {
				this.unlockAchievement(62);
			}
		}
	}
	resetProgress(): void {
		this.gameTime = 840;
		this.money = 700;
		this.moneySpent = 0;
		this.paused = false;
		this.lastCareerLevel = 0;
		this.lastPromotionDay = -1;
	}
	advanceTime(elapsed: number): void {
		this.timeAccumulator = ((this.timeAccumulator + elapsed) | 0);
		if (this.timeAccumulator > 1000) {
			let minutes: number = ((this.timeAccumulator / 1000) | 0);
			this.timeAccumulator = ((this.timeAccumulator - (Math.imul(minutes, 1000))) | 0);
			let previousTime: number = this.gameTime;
			this.gameTime = ((this.gameTime + minutes) | 0);
			this.canvas.gameScreen().advanceTimeUntil(previousTime, this.gameTime);
		}
	}
	eventTimeCost(eventId: number): number {
		if (eventId === 125) {
			return 10;
		}
		else {
			if (eventId === 158) {
				let lot: number = 0;
				if ((lot = this.simLot(0)) !== -1) {
					let hour: number = this.simHour(0);
					if ((this.lotHourDays(lot, hour) & 2) !== 0) {
						return 15;
					}
				}
				return 30;
			}
			else {
				return 0;
			}
		}
	}
	careerLevel(): number {
		let level: number = this.dayNumber();
		for (let resident: number = 0; resident < this.residentCount(); resident++) {
			level = ((level + this.residentLevel(resident)) | 0);
		}
		if (level >= 15) {
			return 2;
		}
		else {
			return level >= 5 ? 1 : 0;
		}
	}
	checkCareerPromotion(): boolean {
		let level: number = 0;
		if ((level = this.careerLevel()) > this.lastCareerLevel) {
			this.lastCareerLevel = (level << 24 >> 24);
			return true;
		}
		else {
			return false;
		}
	}
	markPromotionDay(): void {
		this.lastPromotionDay = (this.dayNumber() << 16 >> 16);
	}
	pendingPromotion(): number {
		let day: number = this.dayNumber();
		let careerTier: number = this.canvas.lot.stageIndex;
		if (this.canvas.lot.stageIndex === 0) {
			if (day >= 3 && !this.hasMilestone(16)) {
				this.setMilestone(16);
				return 777;
			}
		}
		else {
			if (careerTier === 1 && ((day - this.lastPromotionDay) | 0) >= 5 && !this.hasMilestone(32)) {
				this.setMilestone(32);
				return 778;
			}
		}
		return -1;
	}
	defaultOpenMinutes(lot: number): number {
		let minutes: number = 480;
		if (this.simKnownIndex(lot, 10) !== -1) {
			minutes = 540;
		}
		else {
			if (this.simKnownIndex(lot, 11) !== -1) {
				minutes = 420;
			}
		}
		return minutes;
	}
	lotClosingTime(sim: number): number {
		let now: number = this.gameTime;
		let closing: number = 0;
		if (sim === 0) {
			let needAmount: number = this.needValues[1];
			let factor: number = ((65536 - FixedMath.div(needAmount, 6553600)) | 0);
			closing = ((now + FixedMath.mul(factor, 600)) | 0);
		}
		else {
			closing = ((now + 600) | 0);
		}
		let nextOpen: number = 0;
		if (this.simLot(sim) !== -1 && (nextOpen = ((this.nextOpenTime(sim, now) - 60) | 0)) > now && ((nextOpen - now) | 0) < 1440) {
			closing = Math.min(nextOpen, closing);
		}
		return closing;
	}
	endOfDay(): void {
		let lot: number = this.simLot(0);
		let hour: number = this.simHour(0);
		let endTime: number = this.lotHourEnd(lot, hour);
		let now: number = this.currentMinuteOfDay();
		let delta: number = 0;
		if ((delta = ((endTime - now) | 0)) < 0) {
			delta = ((delta + 1440) | 0);
		}
		let elapsedMs: number = Math.imul(delta, 1000);
		this.advanceTime(elapsedMs);
		this.adjustNeedIfIdle(1, -983040, 3);
		this.adjustNeedIfIdle(3, -1310720, 8);
		this.adjustNeed(5, -1638400);
		this.adjustNeed(2, this.needValue(2) < 2293760 ? 3276800 : -983040);
		if (this.simLot(0) !== 0) {
			this.adjustNeedIfIdle(0, -1310720, 0);
		}
		this.celebrate();
	}
	skipToTime(targetTime: number): void {
		let now: number = this.gameTime;
		let elapsedMs: number = Math.imul((((targetTime - now) | 0)), 1000);
		this.advanceTime(elapsedMs);
		this.adjustNeed(1, 6553600);
		this.adjustNeed(2, -655360);
		this.adjustNeedIfIdle(0, -1310720, 0);
		this.adjustNeedIfIdle(3, -983040, 8);
		this.celebrate();
	}
	refreshItemCounts(): void {
		if (this.itemCounts === null) {
			let count: number = this.canvas.lot.categoryCount();
			this.itemCounts = new Int8Array(count);
		}
		GameCanvas.fillBytes(this.itemCounts, 0);
	}
	itemCount(item: number): number {
		return this.itemCounts[item];
	}
	ownedItemCount(): number {
		return ((this.itemCounts.length - GameCanvas.countEqualBytes(0, this.itemCounts)) | 0);
	}
	itemCountOf(item: number): number {
		return GameCanvas.nthIndexNotEqualByte(item, this.itemCounts, 0);
	}
	addItem(item: number, delta: number): void {
		let max: number = this.canvas.lot.categoryFlagAt(item);
		this.itemCounts[item] = (Math.min(max, ((this.itemCounts[item] + delta) | 0)) << 24 >> 24);
		if (delta > 0) {
			let lot: Lot = this.canvas.lot;
			if ((this.canvas.lot.categoryMaskAt(item) & 2048) !== 0) {
				this.unlockAchievement(27);
			}
		}
	}
	buildItemMenu(labels: Int16Array, commands: Int16Array, interaction: number): void {
		let textId: number = this.interactionTextIds[interaction];
		GameCanvas.initList(labels, textId);
		GameCanvas.initList(commands, textId);
		let lot: Lot = this.canvas.lot;
		let counts: Int8Array = this.itemCounts;
		for (let item: number = 0; item < counts.length; item++) {
			if (counts[item] > 0 && (lot.categoryMaskAt(item) & 64) !== 0) {
				GameCanvas.addToList(labels, lot.categoryIdAt(item));
				GameCanvas.addToList(commands, interaction);
			}
		}
	}
	buildUseMenu(labels: Int16Array, commands: Int16Array, interaction: number): void {
		let textId: number = this.interactionTextIds[interaction];
		GameCanvas.initList(labels, textId);
		GameCanvas.initList(commands, textId);
		let lot: Lot = this.canvas.lot;
		let counts: Int8Array = this.itemCounts;
		for (let item: number = 0; item < counts.length; item++) {
			if (counts[item] > 0 && (lot.categoryMaskAt(item) & 2048) !== 0) {
				GameCanvas.addToList(labels, lot.categoryIdAt(item));
				GameCanvas.addToList(commands, interaction);
			}
		}
	}
	purchaseUpgrade(interaction: number): boolean {
		let lot: Lot = this.canvas.lot;
		let ingredientCount: number = this.canvas.lot.listSize(interaction);
		for (let checkIndex: number = 0; checkIndex < ingredientCount; checkIndex++) {
			let ingredient: number = lot.listValueAt(interaction, checkIndex);
			if (this.itemCount(ingredient) < 1) {
				return false;
			}
		}
		for (let consumeIndex: number = 0; consumeIndex < ingredientCount; consumeIndex++) {
			let consumedIngredient: number = lot.listValueAt(interaction, consumeIndex);
			this.addItem(consumedIngredient, -1);
		}
		this.lastPurchasedItem = interaction;
		return true;
	}
	hasItemC(): boolean {
		return this.itemCount(2) > 0;
	}
	hasItemB(): boolean {
		return this.itemCount(1) > 0;
	}
	hasItemA(): boolean {
		return this.itemCount(0) > 0;
	}
	resetCareerLevels(): void {
		let count: number = this.residentCount();
		if (this.residentGrowth === null) {
			this.residentGrowth = new Int32Array(count);
		}
		GameCanvas.fillInts(this.residentGrowth, 0);
	}
	careerPoints(resident: number): number {
		return this.residentGrowth[resident];
	}
	residentCount(): number {
		return this.residentIds.length;
	}
	residentId(resident: number): number {
		return this.residentIds[resident];
	}
	residentBodySprite(resident: number): number {
		return this.residentLook[resident][0];
	}
	residentHeadSprite(resident: number): number {
		return this.residentLook[resident][1];
	}
	residentColorIndex(resident: number): number {
		return this.residentLook[resident][2];
	}
	residentLevelValue(resident: number): number {
		return this.residentLevelValues[resident][this.residentLevel(resident)];
	}
	residentLevel(resident: number): number {
		return ((this.residentGrowth[resident] / 65536) | 0);
	}
	addCareerPoints(resident: number): void {
		let growth: Int32Array = this.residentGrowth;
		let level: number = 0;
		if ((level = this.residentLevel(resident)) < 5) {
			growth[resident] = ((growth[resident] + this.residentGrowthSteps[resident][level]) | 0);
			growth[resident] = Math.min(growth[resident], Math.imul(65536, (((level + 1) | 0))));
		}
	}
	resetSelection(): void {
		this.simLots[0] = -1;
		this.simHours[0] = 0;
		this.lastVisitDay = -1;
		this.tickCount = 0;
		this.idleTicks = 0;
		if (this.lotLastHour === null) {
			this.lotLastHour = new Int8Array(this.lotNameTextIds.length);
		}
		GameCanvas.fillBytes(this.lotLastHour, 0);
		this.suggestedLot = -1;
		this.suggestionTimer = 1440000;
	}
	updateSuggestion(elapsed: number): void {
		this.suggestionTimer = ((this.suggestionTimer + elapsed) | 0);
		if (this.suggestionTimer > 1440000) {
			this.suggestionTimer = 0;
			let range: number = this.lotNameTextIds.length;
			if (!this.canvas.gameStarted) {
				range--;
			}
			this.suggestedLot = ((((this.canvas.randomRange(0, range) - 1) | 0)) << 24 >> 24);
		}
	}
	selectLocation(lot: number, hour: number): void {
		let previousLot: number = this.simLots[0];
		let previousHour: number = this.simHours[0];
		this.simLots[0] = (lot << 24 >> 24);
		this.simHours[0] = (hour << 24 >> 24);
		if (lot !== -1) {
			this.lotLastHour[lot] = (hour << 24 >> 24);
		}
		this.tickCount = 0;
		this.idleTicks = 0;
		if (lot !== -1) {
			if (previousLot !== lot) {
				this.lastVisitDay = (this.dayOfWeek() << 24 >> 24);
			}
			let canvas: GameCanvas = this.canvas;
			let screen: GameScreen = this.canvas.gameScreen();
			let titleTextId: number = previousLot === lot ? hour > previousHour ? 635 : 637 : 634;
			let weekday: number = Neighborhood.weekdayOf(this.nextOpenTime(0, this.gameTime));
			let weekdayTextId: number = 0;
			if (Neighborhood.weekdayOf(((this.gameTime + 1440) | 0)) === weekday) {
				weekdayTextId = 660;
			}
			else {
				weekdayTextId = Neighborhood.WEEKDAY_TEXT_IDS[weekday];
			}
			let title: SdkString = canvas.getString(weekdayTextId);
			let body: SdkString = canvas.beginString();
			canvas.appendTime(this.lotHourStarts[lot][hour]);
			let subtitle: SdkString = canvas.getString(canvas.lot.spriteIdAt(this.lotOccupantTypes[lot]));
			canvas.formatStringWith3(-11, titleTextId, title, body, subtitle);
			let optionFlags: number = 0;
			if (previousLot === lot && hour > previousHour) {
				optionFlags = 4;
			}
			else {
				if (lot !== -1) {
					optionFlags = 3;
				}
			}
			let option: number = this.lotHourInteraction(lot, hour);
			screen.openDialog(-11, option, optionFlags);
		}
	}
	tick(): number {
		let lot: number = this.simLot(0);
		let hour: number = this.simHour(0);
		let income: number = this.lotHourIncome(lot, hour);
		this.addMoney(income);
		this.tickCount++;
		if (((this.tickCount % 3) | 0) === 2 && this.idleTicks > 0) {
			this.idleTicks--;
		}
		return income;
	}
	idleTick(): void {
		this.idleTicks++;
		if (this.idleTicks >= 3) {
			this.selectLocation(-1, 0);
		}
	}
	statValue(slot: number): number {
		switch (slot) {
			case 0:
			default:
				return this.residentLevel(0);
			case 1:
				return this.residentLevel(2);
			case 2:
				return this.tickCount;
			case 3:
				let working: number = 0;
				for (let sim: number = 1; sim < this.simCount; sim++) {
					let state: number = this.simStates[sim];
					if ((this.stateFlagsOf(state) & 16) !== 0) {
						working++;
					}
				}
				return working;
			case 4:
				return this.residentLevel(4);
			case 5:
				return this.residentLevel(3);
		}
	}
	static statTextId(slot: number): number {
		switch (slot) {
			case 0:
			default:
				return 486;
			case 1:
				return 500;
			case 2:
				return 708;
			case 3:
				return 707;
			case 4:
				return 514;
			case 5:
				return 507;
		}
	}
	canEnterLot(lot: number, hour: number): boolean {
		let requirements: Int8Array = null!;
		let firstStat: number = (requirements = this.lotHourRequirements[lot][hour])[0];
		let firstMin: number = requirements[1];
		if (firstStat !== -1 && this.statValue(firstStat) < firstMin) {
			return false;
		}
		else {
			let secondStat: number = requirements[2];
			let secondMin: number = requirements[3];
			if (secondStat !== -1 && this.statValue(secondStat) < secondMin) {
				return false;
			}
			else {
				let kind: number = this.lotKind(lot);
				let state: number = this.simStates[kind];
				return (this.stateFlagsOf(state) & 2) === 0;
			}
		}
	}
	canAdvanceHour(): boolean {
		let lot: number = this.simLot(0);
		let hour: number = 0;
		return (hour = this.simHour(0)) < ((this.lotHourCount(lot) - 1) | 0) && this.canEnterLot(lot, ((hour + 1) | 0)) && this.tickCount >= 10;
	}
	pickActionFor(entity: Entity): number {
		let currentLot: number = this.simLots[0];
		let lot: number = -1;
		if (entity !== null && entity.type !== 77) {
			lot = GameCanvas.indexOfShort(entity.type, this.lotOccupantTypes);
		}
		else {
			let minute: number = 0;
			if ((minute = this.currentMinuteOfDay()) >= 450 && minute <= 1110 && this.suggestedLot !== currentLot) {
				lot = this.suggestedLot;
			}
		}
		return lot;
	}
	lastHourIndex(lot: number): number {
		return lot === -1 ? 0 : Math.max(((this.lotLastHour[lot] - 1) | 0), 0);
	}
	static isTimeInRange(time: number, start: number, end: number): boolean {
		start = (((((start + 1440) | 0)) % 1440) | 0);
		end = (((((end + 1440) | 0)) % 1440) | 0);
		return start < end ? time >= start && time <= end : time >= start || time <= end;
	}
	lotStateNow(lot: number): number {
		return this.lotState(lot, this.gameTime);
	}
	lotState(lot: number, time: number): number {
		let state: number = 0;
		let weekday: number = Neighborhood.weekdayOf(time);
		let minute: number = Neighborhood.minuteOfDay(time);
		let endMinutes: number = 0;
		let startMinutes: number = (((endMinutes = this.defaultOpenMinutes(lot)) - 600) | 0);
		let activeLot: number = 0;
		if ((activeLot = this.simLots[lot]) === -1) {
			state = 8;
		}
		else {
			let openTime: number = 0;
			label51: {
				let hour: number = this.simHours[lot];
				openTime = this.lotHourStarts[activeLot][hour];
				if (lot !== 0 || weekday !== this.lastVisitDay) {
					if (lot === 0) {
						this.lastVisitDay = -1;
					}
					let dayBit: number = Neighborhood.WEEKDAY_BITS[weekday];
					if ((this.lotHourDayBits[activeLot][hour] & dayBit) !== 0) {
						let closeTime: number = this.lotHourEnds[activeLot][hour];
						if (minute >= ((openTime - 30) | 0)) {
							if (minute < openTime) {
								state = 1;
								break label51;
							}
							if (minute < ((openTime + 60) | 0)) {
								state = 2;
								break label51;
							}
							if (minute < closeTime) {
								state = 4;
								break label51;
							}
						}
						state = 8;
						break label51;
					}
				}
				state = 8;
			}
			startMinutes = ((openTime - 660) | 0);
			endMinutes = ((openTime - 60) | 0);
		}
		if (Neighborhood.isTimeInRange(minute, startMinutes, endMinutes)) {
			state = ((state | 16) << 24 >> 24);
		}
		return state;
	}
	nextOpenTime(sim: number, time: number): number {
		let lot: number = this.simLot(sim);
		let hour: number = this.simHour(sim);
		let dayBits: number = this.lotHourDays(lot, hour);
		let startMinutes: number = this.lotHourStart(lot, hour);
		let minute: number = Neighborhood.minuteOfDay(time);
		let weekday: number = 0;
		let candidateDay: number = weekday = Neighborhood.weekdayOf(time);
		for (let offset: number = 0; offset < 7; offset++) {
			candidateDay = (((((weekday + offset) | 0)) % 7) | 0);
			if ((dayBits & Neighborhood.WEEKDAY_BITS[candidateDay]) !== 0 && (sim !== 0 || candidateDay !== this.lastVisitDay) && (candidateDay !== weekday || minute < startMinutes)) {
				break;
			}
		}
		let openTime: number = ((Neighborhood.startOfWeek(this.gameTime) + startMinutes) | 0);
		if (candidateDay !== weekday) {
			openTime = ((openTime + (Math.imul((((((((candidateDay - weekday) | 0) + 7) | 0)) % 7) | 0), 1440))) | 0);
		}
		return openTime;
	}
	isLotOccupant(entity: Entity, sim: number): boolean {
		let lot: number = 0;
		if ((lot = this.simLots[sim]) === -1) {
			return false;
		}
		else {
			if (entity !== null) {
				let type: number = this.lotOccupantType(lot);
				return entity.type === type;
			}
			else {
				return false;
			}
		}
	}
	canInteractAt(entity: Entity, sim: number): boolean {
		return entity !== null && !this.isLotOccupant(entity, sim) ? false : (this.lotStateNow(sim) & 3) !== 0;
	}
	lotHourCount(lot: number): number {
		return this.lotHourInteractions[lot].length;
	}
	lotNameText(lot: number): number {
		return lot === -1 ? 667 : this.lotNameTextIds[lot];
	}
	lotSubtitleText(lot: number): number {
		return this.lotSubtitleTextIds[lot];
	}
	lotOccupantType(lot: number): number {
		return this.lotOccupantTypes[lot];
	}
	lotKind(lot: number): number {
		return this.lotKinds[lot];
	}
	currentLotSprite(): number {
		let lot: number = this.simLots[0];
		return this.canvas.randomPercent() < 50 ? this.lotSpritesLow[lot] : this.lotSpritesHigh[lot];
	}
	lotExtraSprite(lot: number): number {
		return this.lotExtraSprites[lot];
	}
	lotHourInteraction(lot: number, hour: number): number {
		return this.lotHourInteractions[lot][hour];
	}
	lotHourIncome(lot: number, hour: number): number {
		return this.lotHourIncomeValue[lot][hour];
	}
	lotHourDays(lot: number, hour: number): number {
		return this.lotHourDayBits[lot][hour];
	}
	lotHourStart(lot: number, hour: number): number {
		return this.lotHourStarts[lot][hour];
	}
	lotHourEnd(lot: number, hour: number): number {
		return this.lotHourEnds[lot][hour];
	}
	lotHourRequirement(lot: number, hour: number): Int8Array {
		return this.lotHourRequirements[lot][hour];
	}
	setSelectedResident(resident: number): void {
		this.selectedResident = (resident << 24 >> 24);
	}
	residentLabelCount(): number {
		return this.residentLabelTextIds.length;
	}
	residentLabelText(resident: number): number {
		return resident === 1 && this.simAttribute(0, 0) === 1 ? 534 : this.residentLabelTextIds[resident];
	}
	residentInfoText(resident: number): number {
		return this.residentInfoTextIds[resident];
	}
	selectedResidentSpriteA(): number {
		return this.residentSpritesA[this.selectedResident];
	}
	selectedResidentSpriteB(): number {
		return this.residentSpritesB[this.selectedResident];
	}
	selectedResidentFrame(): number {
		return GameCanvas.countAllFlags(this.selectedResidentSpriteB(), this.achievementFlags);
	}
	selectedResidentAnimFrame(frameStep: number): number {
		return GameCanvas.nthIndexWithFlag(frameStep, this.achievementFlags, this.selectedResidentSpriteB());
	}
	selectedResidentMask(): number {
		switch (this.selectedResident) {
			case 0:
				return 536870912;
			case 1:
				return 134217728;
			case 2:
				return 268435456;
			case 3:
				return 33554432;
			case 4:
				return 67108864;
			case 5:
			default:
				return 16777216;
		}
	}
	unlockSecondMilestone(): boolean {
		if (this.hasMilestone(1) && !this.hasMilestone(2)) {
			this.setMilestone(2);
			return true;
		}
		else {
			return false;
		}
	}
	resetAchievementState(): void {
		this.pendingAchievementId = -1;
		this.randomSeed = (this.canvas.randomRange(0, ((this.achievementFlags.length - 1) | 0)) << 16 >> 16);
		this.achievementTimer = 115000;
		if (this.achievementSlots === null) {
			this.achievementSlots = new Int8Array(4);
			this.achievementTimes = new Int32Array(this.achievementTriggerIds.length);
			this.recentTaskTimes = new Int32Array(10);
		}
		GameCanvas.fillBytes(this.achievementSlots, -1);
		this.usedTaskBits = 0;
		this.milestoneFlags = 0;
		GameCanvas.fillInts(this.achievementTimes, this.gameTime);
		this.altEventCount = 0;
		this.otherEventCount = 0;
		GameCanvas.fillInts(this.recentTaskTimes, -1);
		this.visitedLotBits = 0;
	}
	hasMilestone(flag: number): boolean {
		return (this.milestoneFlags & flag) !== 0;
	}
	setMilestone(flag: number): void {
		this.milestoneFlags = (this.milestoneFlags | flag);
	}
	updateAchievementTimers(elapsed: number): void {
		this.achievementTimer = ((this.achievementTimer + elapsed) | 0);
		if (this.pendingAchievementId !== -1) {
			if (this.achievementTimer > 240000) {
				this.canvas.gameScreen().showRelationshipDialog(this.pendingAchievementId);
				this.pendingAchievementId = -1;
				this.achievementTimer = 0;
			}
		}
		else {
			if (this.achievementTimer > 120000) {
				this.pendingAchievementId = (this.pickRandomAchievement() << 24 >> 24);
				this.achievementTimer = 0;
				this.canvas.gameScreen().requestSleep();
			}
		}
		let now: number = this.gameTime;
		for (let index: number = 0; index < this.achievementTimes.length; index++) {
			let timestamp: number = 0;
			if ((timestamp = this.achievementTimes[index]) !== -1 && ((now - timestamp) | 0) > 4320) {
				this.achievementTimes[index] = now;
				this.unlockAchievement(this.achievementTriggerIds[index]);
			}
		}
	}
	pickRandomAchievement(): number {
		let count: number = this.achievementFlags.length;
		let event: number = -1;
		let seed: number = this.randomSeed;
		for (let attempt: number = 0; attempt < count; attempt++) {
			while ((seed = (((((Math.imul(1543, seed) + 3571) | 0)) % 257) | 0)) >= count) {
			}
			let flags: number = 0;
			if (this.achievementSlotOf(seed) === -1 && ((flags = this.achievementFlags[seed]) & 8) === 0 && this.isAchievementAvailable(seed) && (!this.canvas.hasVisitedFlag(seed) || this.canvas.randomPercent() >= 70)) {
				event = seed;
				this.randomSeed = (seed << 16 >> 16);
				break;
			}
		}
		switch (event) {
			case 44:
				this.altEventCount = 0;
				break;
			case 45:
				this.otherEventCount = 0;
				break;
			case 52:
				this.clearSimMilestones(1536);
		}
		let index: number = 0;
		if ((index = GameCanvas.indexOfByte(event, this.achievementTriggerIds)) !== -1 && this.achievementTimes[index] !== -1) {
			this.achievementTimes[index] = this.gameTime;
		}
		this.canvas.addFlag(event);
		return event;
	}
	assignAchievement(): void {
		let slot: number = 0;
		if ((slot = this.achievementSlotOf(-1)) !== -1 && this.pendingAchievementId !== -1) {
			this.achievementSlots[slot] = this.pendingAchievementId;
			this.pendingAchievementId = -1;
			this.achievementTimer = 0;
			this.canvas.gameScreen().playSlotAnimator(slot);
		}
	}
	hasOpenAchievementSlot(): boolean {
		return GameCanvas.indexOfByte(-1, this.achievementSlots) !== -1;
	}
	isAchievementAvailable(event: number): boolean {
		let lot: Lot = this.canvas.lot;
		switch (event) {
			case 0:
				if (!this.hasMilestone(4)) {
					return true;
				}
				return false;
			case 1:
				if (!this.hasMilestone(8)) {
					return true;
				}
				return false;
			case 2:
				if (this.itemCount(2) === 0) {
					return true;
				}
				return false;
			case 3:
				if (this.itemCount(3) === 0) {
					return true;
				}
				return false;
			case 4:
				if (this.itemCount(1) === 0) {
					return true;
				}
				return false;
			case 5:
				if (!lot.hasObjectKind(19)) {
					return true;
				}
				return false;
			case 6:
				if (!lot.hasObjectKind(3)) {
					return true;
				}
				return false;
			case 7:
				if (this.residentLevel(0) < 5) {
					return true;
				}
				return false;
			case 8:
				if (this.residentLevel(2) < 5) {
					return true;
				}
				return false;
			case 9:
				if (this.residentLevel(1) < 5) {
					return true;
				}
				return false;
			case 10:
				if (this.residentLevel(3) < 5) {
					return true;
				}
				return false;
			case 11:
			case 50:
				if (GameCanvas.countEqualBytes(0, this.simStates) > 1) {
					return true;
				}
				return false;
			case 12:
			case 13:
			case 14:
				let flagMask: number = event === 12 ? 16 : event === 13 ? 4 : 8;
				for (let sim: number = 1; sim < this.simCount; sim++) {
					if ((this.stateFlagsOf(this.simStates[sim]) & flagMask) === 0) {
						return true;
					}
				}
				return false;
			case 15:
			case 16:
			case 17:
			case 18:
			case 19:
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
			case 26:
			case 28:
			case 29:
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 36:
			case 37:
			case 38:
			case 39:
			case 40:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 51:
			case 52:
			case 53:
			default:
				return true;
			case 20:
				if (this.simLot(0) === -1) {
					return true;
				}
				return false;
			case 27:
				for (let item: number = 0; item < this.itemCounts.length; item++) {
					if (this.itemCounts[item] === 0 && (lot.categoryMaskAt(item) & 2048) !== 0) {
						return true;
					}
				}
				return false;
			case 41:
				if (lot.hasObjectKind(9) && !lot.hasObject(43) && !lot.hasObject(44) && !lot.hasObject(45)) {
					return true;
				}
				return false;
			case 42:
				if (lot.hasObjectKind(22) && !lot.hasObject(95) && !lot.hasObject(96) && !lot.hasObject(97)) {
					return true;
				}
				return false;
			case 43:
				if (this.money < 500) {
					return true;
				}
				return false;
			case 54:
			case 55:
				return this.simLot(0) !== -1 && this.simHour(0) < this.lotHourCount(this.simLot(0));
		}
	}
	achievementSlotOf(event: number): number {
		return GameCanvas.indexOfByte(event, this.achievementSlots);
	}
	achievementSlotValue(slot: number): number {
		return this.achievementSlots[slot];
	}
	achievementText(index: number): number {
		return index >= 0 && index < this.achievementTextIds.length ? this.achievementTextIds[index] : 548;
	}
	achievementCount(): number {
		return this.achievementTextIds.length;
	}
	achievementFlagAt(index: number): number {
		return this.achievementFlags[index];
	}
	unlockAchievement(event: number): void {
		let canvas: GameCanvas = this.canvas;
		let screen: GameScreen = this.canvas.gameScreen();
		let handled: boolean = false;
		let slot: number = 0;
		if ((slot = this.achievementSlotOf(event)) !== -1) {
			handled = true;
			this.achievementSlots[slot] = -1;
		}
		else {
			if (this.pendingAchievementId === event) {
				handled = true;
				this.pendingAchievementId = -1;
				this.achievementTimer = 0;
			}
		}
		let milestoneFlag: number = 0;
		switch (event) {
			case 0:
				milestoneFlag = 4;
				break;
			case 1:
				milestoneFlag = 8;
		}
		this.setMilestone(milestoneFlag);
		let bitIndex: number = 0;
		let unlockedCount: number = 0;
		let total: number = this.achievementCount();
		let residentMask: number = this.selectedResidentSpriteB();
		for (let index: number = 0; index < total; index++) {
			if ((this.achievementFlagAt(index) & residentMask) !== 0) {
				let bit: number = 1 << bitIndex;
				if (event === index && (this.usedTaskBits & bit) === 0) {
					this.usedTaskBits = (this.usedTaskBits | bit);
					handled = true;
				}
				if ((this.usedTaskBits & bit) !== 0) {
					unlockedCount++;
				}
				bitIndex++;
			}
		}
		if (handled) {
			canvas.addVisitedFlag(event);
			screen.playSlotAnimator2(event, slot);
		}
		if (unlockedCount === bitIndex) {
			this.setMilestone(1);
		}
		let multiplayer: boolean = canvas.gameStarted;
		if (!canvas.gameStarted) {
			let achievementTotal: number = this.achievementTextIds.length;
			if (canvas.visitedFlagCount() === achievementTotal) {
				screen.requestNeighborhoodEvent();
			}
		}
		switch (event) {
			case 22:
				this.altEventCount++;
				if (this.altEventCount >= 15) {
					this.unlockAchievement(44);
				}
		}
	}
	onItemPlaced(item: number): void {
		let lot: Lot = this.canvas.lot;
		let type: number = 0;
		if ((type = this.canvas.lot.objectKindAt(item)) === 3) {
			this.unlockAchievement(6);
		}
		else {
			if (type === 9) {
				if (item !== 42) {
					this.unlockAchievement(41);
				}
			}
			else {
				if (type === 19) {
					this.unlockAchievement(5);
				}
				else {
					if (type === 22 && item !== 94) {
						this.unlockAchievement(42);
					}
				}
			}
		}
		if (lot.allItemsPlaced()) {
			this.unlockAchievement(64);
		}
		this.unlockAchievement(21);
	}
	onInteractionResult(result: number): void {
		this.unlockAchievement(21);
		if (result === 2) {
			this.unlockAchievement(2);
		}
		if (result === 3) {
			this.unlockAchievement(3);
		}
		if (result === 1) {
			this.unlockAchievement(4);
		}
	}
	onRepeatAction(amount: number): void {
		this.otherEventCount = ((((this.otherEventCount + amount) | 0)) << 16 >> 16);
		if (this.otherEventCount > 30) {
			this.unlockAchievement(45);
		}
	}
	canActToday(): boolean {
		let times: Int32Array = this.recentTaskTimes;
		let now: number = this.gameTime;
		let count: number = 0;
		let freeSlot: number = -1;
		for (let index: number = 0; index < 10; index++) {
			let time: number = times[index];
			if (((now - time) | 0) > 1440) {
				times[index] = -1;
				time = -1;
			}
			if (time === -1 && freeSlot === -1) {
				freeSlot = index;
				times[index] = now;
				time = now;
			}
			if (time !== -1) {
				count++;
			}
		}
		if (freeSlot !== -1 && count < 10) {
			if (count >= 8) {
				this.unlockAchievement(61);
			}
			this.unlockAchievement(53);
			return false;
		}
		else {
			return true;
		}
	}
	hasUsedTask(index: number): boolean {
		return (this.usedTaskBits & 1 << index) !== 0;
	}
	hasVisitedItem(item: number): boolean {
		let lotIndex: number = 0;
		return (lotIndex = this.canvas.lot.indexOfKind(item)) !== -1 ? (this.visitedLotBits & 1 << lotIndex) !== 0 : false;
	}
	markItemVisited(item: number): void {
		let lotIndex: number = 0;
		if ((lotIndex = this.canvas.lot.indexOfKind(item)) !== -1) {
			this.visitedLotBits = (this.visitedLotBits | (1 << lotIndex));
		}
	}
	needId(need: number): number {
		return this.needIds[need];
	}
	needLabelText(need: number): number {
		return this.needLabelTextIds[need];
	}
	needHintText(need: number): number {
		return this.needHintTextIds[need];
	}
	needValue(need: number): number {
		return this.needValues[need];
	}
	resetNeedValues(): void {
		if (this.needValues === null) {
			this.needValues = new Int32Array(this.needCount);
			this.needDecay = new Int32Array(this.needCount);
			this.needModifiers = new Int32Array(this.needCount);
			this.needOffsets = new Int32Array(this.needCount);
		}
		arraycopy(this.needInitialValues, 0, this.needValues, 0, this.needCount);
		arraycopy(this.needDecayRates, 0, this.needDecay, 0, this.needCount);
		GameCanvas.fillInts(this.needModifiers, 0);
		GameCanvas.fillInts(this.needOffsets, 65536);
		this.needTimer = 0;
	}
	updateNeeds(elapsed: number): void {
		this.needTimer = ((this.needTimer + elapsed) | 0);
		if (this.needTimer > 500) {
			this.needTimer = ((this.needTimer - 500) | 0);
			let decay: Int32Array = this.needDecay;
			let offsets: Int32Array = this.needOffsets;
			let modifiers: Int32Array = this.needModifiers;
			for (let need: number = 0; need < this.needCount; need++) {
				let amount: number = FixedMath.mul(offsets[need], decay[need]);
				this.adjustNeed(need, ((amount + modifiers[need]) | 0) >> 1);
			}
		}
	}
	adjustNeed(need: number, amount: number): void {
		let values: Int32Array = this.needValues;
		this.needValues[need] = ((this.needValues[need] + amount) | 0);
		values[need] = FixedMath.clamp(values[need], 0, 6553600);
	}
	adjustNeedIfIdle(need: number, amount: number, task: number): void {
		if (this.activeTaskSlotOf(task) === -1) {
			this.adjustNeed(need, amount);
		}
	}
	setNeedModifier(need: number, modifier: number): void {
		this.needModifiers[need] = modifier;
	}
	moodFlags(): number {
		let flags: number = 0;
		let values: Int32Array = this.needValues;
		let moodFlagBits: Int16Array = this.needMoodFlags;
		let thresholds: Int32Array = this.needThresholds;
		for (let need: number = 0; need < values.length; need++) {
			if (values[need] < thresholds[need]) {
				flags = ((flags | moodFlagBits[need]) << 16 >> 16);
			}
		}
		return flags;
	}
	resetActiveTasks(): void {
		if (this.activeTasks === null) {
			this.activeTasks = new Int16Array(6);
			this.taskTimers = new Int32Array(6);
		}
		GameCanvas.fillShorts(this.activeTasks, -1);
		GameCanvas.fillInts(this.taskTimers, 0);
	}
	updateActiveTasks(elapsed: number): void {
		let canvas: GameCanvas = this.canvas;
		let tasks: Int16Array = this.activeTasks;
		let timers: Int32Array = this.taskTimers;
		let flags: Int8Array = this.taskFlags;
		let durations: Int32Array = this.taskDurations;
		let followUps: Int8Array = this.taskFollowUps;
		for (let slot: number = 0; slot < 6; slot++) {
			let task: number = 0;
			if ((task = tasks[slot]) !== -1) {
				if ((flags[task] & 2) === 0 && !this.canRunTask(task)) {
					this.stopTask(task);
				}
				else {
					let limit: number = Math.imul(durations[task], 1000);
					timers[slot] = ((timers[slot] + elapsed) | 0);
					if (timers[slot] > limit) {
						timers[slot] = limit;
						if (followUps[task] !== -1) {
							this.setActiveTask(slot, followUps[task]);
						}
						else {
							if (!this.canRunTask(task)) {
								this.stopTask(task);
							}
							else {
								switch (task) {
									case 2:
										canvas.formatStringWith(-11, 802, canvas.profileName());
										canvas.gameScreen().openStatsDialog(-11);
										this.celebrate();
										return;
									case 5:
										canvas.gameScreen().cancelPlayerAction();
										this.celebrate();
										this.setActiveTask(slot, 4);
										return;
									case 10:
										this.adjustNeed(2, 3276800);
										this.stopTask(task);
										let screen: GameScreen = null!;
										if (!(screen = canvas.gameScreen()).isNewNeighborhood()) {
											let sim: Sim = screen.activePlayer;
											if (!screen.activePlayer.isFree()) {
												sim.cancelAction();
											}
											sim.queueAction_2(4, null, 0, 0);
										}
										this.celebrate();
										return;
								}
							}
						}
					}
				}
			}
		}
		let count: number = this.taskCount;
		for (let taskIndex: number = 0; taskIndex < count; taskIndex++) {
			let chosenTask: number = taskIndex;
			if ((flags[taskIndex] & 8) === 0 && this.canRunTask(taskIndex)) {
				if (taskIndex === 3 && (this.canvas.gameScreen().activePlayer.actionPhase === 50 || this.activeTaskSlotOf(6) !== -1)) {
					chosenTask = 6;
				}
				let activeSlot: number = 0;
				if ((activeSlot = this.activeTaskSlotOf(chosenTask)) === -1) {
					this.startTask(chosenTask);
					this.celebrate();
					return;
				}
				if (chosenTask === 6) {
					this.taskTimers[activeSlot] = 0;
				}
			}
		}
	}
	applyMoodDeltas(): void {
		let deltas: Int32Array = this.moodDeltas;
		GameCanvas.fillInts(this.moodDeltas, 0);
		for (let slot: number = 0; slot < 6; slot++) {
			let task: number = 0;
			if ((task = this.activeTasks[slot]) !== -1) {
				let indices: Int8Array = this.taskMoodIndices[task];
				let values: Int8Array = this.taskMoodDeltas[task];
				let count: number = indices.length;
				for (let slotIndex: number = 0; slotIndex < count; slotIndex++) {
					deltas[indices[slotIndex]] = ((deltas[indices[slotIndex]] + (values[slotIndex] << 16)) | 0);
				}
			}
		}
		for (let clampIndex: number = 0; clampIndex < 2; clampIndex++) {
			deltas[clampIndex] = FixedMath.clamp(deltas[clampIndex], -6553600, 6553600);
		}
		this.updateMood();
	}
	setActiveTask(slot: number, task: number): void {
		let previousTask: number = this.activeTasks[slot];
		this.activeTasks[slot] = (task << 16 >> 16);
		this.taskTimers[slot] = 0;
		let sim: Sim = null!;
		(sim = this.canvas.gameScreen().activePlayer).clearLocomotionState(previousTask);
		sim.setLocomotionState(task);
		let linkIndices: Int8Array[] = this.taskLinkIndices;
		let linkDeltas: Int32Array[] = this.taskLinkDeltas;
		let offsets: Int32Array = this.needOffsets;
		if (previousTask !== -1) {
			for (let oldLinkIndex: number = 0; oldLinkIndex < linkIndices[previousTask].length; oldLinkIndex++) {
				offsets[linkIndices[previousTask][oldLinkIndex]] = ((offsets[linkIndices[previousTask][oldLinkIndex]] + linkDeltas[previousTask][oldLinkIndex]) | 0);
			}
		}
		if (task !== -1) {
			for (let newLinkIndex: number = 0; newLinkIndex < linkIndices[task].length; newLinkIndex++) {
				offsets[linkIndices[task][newLinkIndex]] = ((offsets[linkIndices[task][newLinkIndex]] - linkDeltas[task][newLinkIndex]) | 0);
			}
			if (task === 9) {
				this.stopTask(8);
			}
			else {
				if (task === 6) {
					this.stopTask(3);
					this.stopTask(7);
				}
				else {
					if (task === 3) {
						this.stopTask(6);
						this.stopTask(7);
					}
				}
			}
			let screen: GameScreen = this.canvas.gameScreen();
			if (task === 2) {
				screen.chooseDialogOptionAt(801, 401);
			}
			else {
				if (task === 1) {
					screen.showNeighborhoodIntro((10 << 24 >> 24));
				}
				else {
					if (task === 4) {
						screen.showNeighborhoodIntro((11 << 24 >> 24));
					}
					else {
						if (task === 5) {
							screen.showNeighborhoodIntro((12 << 24 >> 24));
						}
						else {
							if (task === 10) {
								screen.showNeighborhoodIntro((13 << 24 >> 24));
							}
							else {
								if (task === 9) {
									screen.showNeighborhoodIntro((14 << 24 >> 24));
								}
								else {
									if (task === 11) {
										screen.showNeighborhoodIntro((15 << 24 >> 24));
									}
								}
							}
						}
					}
				}
			}
		}
		this.applyMoodDeltas();
	}
	startTask(task: number): void {
		let achievement: number = 0;
		if ((achievement = this.taskAchievements[task]) !== -1) {
			this.achievementTimes[achievement] = -1;
		}
		let slot: number = GameCanvas.indexOfShort(-1, this.activeTasks);
		this.setActiveTask(slot, task);
	}
	stopTask(task: number): void {
		let achievement: number = 0;
		if ((achievement = this.taskAchievements[task]) !== -1) {
			this.achievementTimes[achievement] = this.gameTime;
		}
		let slot: number = 0;
		if ((slot = GameCanvas.indexOfShort(task, this.activeTasks)) !== -1) {
			this.setActiveTask(slot, -1);
		}
	}
	canRunTask(task: number): boolean {
		let followUp: number = 0;
		if ((followUp = this.taskFollowUps[task]) !== -1 && this.activeTaskSlotOf(followUp) !== -1) {
			return false;
		}
		else {
			let values: Int32Array = this.needValues;
			let indices: Int8Array = this.taskNeedIndices[task];
			let deltas: Int32Array = this.taskNeedDeltas[task];
			let count: number = 0;
			if ((count = indices.length) === 0) {
				return true;
			}
			else {
				for (let index: number = 0; index < count; index++) {
					let need: number = indices[index];
					let delta: number = deltas[index];
					let value: number = values[need];
					if (delta > 0 && value > delta) {
						return true;
					}
					if (delta < 0 && value < ((delta * -1) | 0)) {
						return true;
					}
				}
				return false;
			}
		}
	}
	activeTaskSlotOf(task: number): number {
		let tasks: Int16Array = this.activeTasks;
		for (let slot: number = 0; slot < 6; slot++) {
			if (tasks[slot] === task) {
				return slot;
			}
		}
		return -1;
	}
	activeTaskAt(slot: number): number {
		return this.activeTasks[slot];
	}
	taskId(task: number): number {
		return this.taskIds[task];
	}
	taskFlagsOf(task: number): number {
		return this.taskFlags[task];
	}
	static initialScreenId(): number {
		return 17;
	}
	itemNameText(item: number): number {
		return this.itemNameTextIds[item];
	}
	resetMoodDeltas(): void {
		if (this.moodDeltas === null) {
			this.moodDeltas = new Int32Array(4);
		}
		GameCanvas.fillInts(this.moodDeltas, 1638400);
	}
	updateMood(): void {
		let deltas: Int32Array = this.moodDeltas;
		let highest: number = -2147483648;
		let lowestIndex: number = -1;
		let lowest: number = 2147483647;
		for (let index: number = 0; index < 4; index++) {
			if (deltas[index] > highest) {
				highest = deltas[index];
			}
			if (deltas[index] < lowest) {
				lowestIndex = index;
				lowest = deltas[index];
			}
		}
		let mood: number = 3;
		let value: number = highest;
		if (lowest < -655360) {
			if (lowest < -3276800) {
				switch (lowestIndex) {
					case 0:
					default:
						mood = 5;
						break;
					case 1:
						mood = 7;
						break;
					case 2:
						mood = 9;
						break;
					case 3:
						mood = 11;
				}
			}
			else {
				switch (lowestIndex) {
					case 0:
					default:
						mood = 4;
						break;
					case 1:
						mood = 6;
						break;
					case 2:
						mood = 8;
						break;
					case 3:
						mood = 10;
				}
			}
			value = lowest;
		}
		else {
			if (highest > 655360) {
				if (highest > 4915200) {
					mood = 0;
				}
				else {
					if (highest > 2621440) {
						mood = 1;
					}
					else {
						mood = 2;
					}
				}
				value = highest;
			}
		}
		this.moodIndex = mood;
		this.moodValue = value;
	}
	moodTextId(): number {
		return Neighborhood.MOOD_TEXT_IDS[this.moodIndex];
	}
	resetSimStates(): void {
		let count: number = this.simCount;
		if (this.simStates === null) {
			let states: Int8Array = new Int8Array(count);
			let milestones: Int16Array = new Int16Array(count);
			let needs: Int32Array[] = new Array(count).fill(null);
			let needDecay: Int32Array[] = new Array(count).fill(null);
			let history: Int16Array[] = new Array(count).fill(null);
			let repeatCounts: Int8Array[] = new Array(count).fill(null);
			for (let index: number = 1; index < count; index++) {
				needs[index] = new Int32Array(this.simNeedCount);
				needDecay[index] = new Int32Array(this.needTextIds.length);
				history[index] = new Int16Array(10);
				repeatCounts[index] = new Int8Array(this.simKnownIds[index].length);
			}
			this.simStates = states;
			this.simMilestones = milestones;
			this.simNeeds = needs;
			this.simNeedDecay = needDecay;
			this.simActionHistory = history;
			this.simRepeatCounts = repeatCounts;
		}
		for (let sim: number = 1; sim < count; sim++) {
			this.simStates[sim] = 0;
			this.simMilestones[sim] = 0;
			GameCanvas.fillInts(this.simNeeds[sim], 0);
			GameCanvas.fillShorts(this.simActionHistory[sim], -1);
			GameCanvas.fillBytes(this.simRepeatCounts[sim], 0);
		}
		this.simTickTimer = 0;
		this.watchedSim = -1;
		this.watchedSimTimer = 0;
	}
	updateSims(elapsed: number): void {
		if (this.watchedSimTimer < 10000) {
			this.watchedSimTimer = ((this.watchedSimTimer + this.canvas.gameScreen().lastTickDuration) | 0);
			if (this.watchedSimTimer >= 10000) {
				this.watchedSim = -1;
			}
		}
		this.simTickTimer = ((this.simTickTimer + elapsed) | 0);
		if (this.simTickTimer > 3000) {
			this.simTickTimer = 0;
		}
		let count: number = this.simCount;
		for (let sim: number = 1; sim < count; sim++) {
			this.checkStateTransition(sim);
			if (this.simTickTimer === 0) {
				let previousNeed: number = this.dominantNeed(sim);
				this.decaySimNeeds(sim, 327680);
				let currentNeed: number = this.dominantNeed(sim);
				if (this.watchedSim === sim && this.watchedSimTimer < 10000 && previousNeed !== currentNeed) {
					let textId: number = this.needTextIds[currentNeed];
					let nameTextId: number = this.simNameText(sim);
					this.canvas.formatString1(-12, textId, nameTextId);
					this.canvas.gameScreen().showNotification(-12, -1);
				}
			}
		}
	}
	watchSim(sim: number): void {
		this.watchedSim = sim;
		this.watchedSimTimer = 0;
	}
	isWatchMode(): boolean {
		return this.watchedSimTimer < 10000;
	}
	decaySimNeeds(sim: number, amount: number): void {
		let decay: Int32Array = this.simNeedDecay[sim];
		for (let need: number = 0; need < decay.length; need++) {
			decay[need] = ((decay[need] - amount) | 0);
			if (decay[need] < 0) {
				decay[need] = 0;
			}
		}
	}
	advanceSimState(sim: number): boolean {
		if (this.simStates[sim] === 0) {
			let state: number = 1;
			if (this.simNeeds[sim][0] < 0) {
				state = 2;
			}
			this.setSimState(sim, state);
			return true;
		}
		else {
			return false;
		}
	}
	moveSim(sim: number, dx: number, dy: number): void {
		let needs: Int32Array = null!;
		let needX: number = (((needs = this.simNeeds[sim])[0] + (dx << 16)) | 0);
		let needY: number = ((needs[1] + (dy << 16)) | 0);
		needs[0] = FixedMath.clamp(needX, -1966080, 1966080);
		needs[1] = FixedMath.clamp(needY, -327680, 1966080);
	}
	checkStateTransition(sim: number): boolean {
		let state: number = this.simStates[sim];
		let needs: Int32Array = this.simNeeds[sim];
		let nextStates: Int8Array = this.stateTransitionStates[state];
		let needIndices: Int8Array = this.stateTransitionNeeds[state];
		let thresholds: Int16Array = this.stateTransitionThresholds[state];
		let count: number = nextStates.length;
		for (let index: number = 0; index < count; index++) {
			let matched: boolean = true;
			let need: number = needIndices[index];
			let threshold: number = thresholds[index];
			let value: number = needs[need];
			let negative: boolean = threshold < 0;
			let limit: number = threshold << 16;
			if (negative && value > limit || !negative && value < limit) {
				matched = false;
			}
			if (matched) {
				let nextState: number = 0;
				if ((nextState = nextStates[index]) === 1 && needs[0] < 0) {
					nextState = 2;
				}
				this.setSimState(sim, nextState);
				return true;
			}
		}
		return false;
	}
	setSimState(sim: number, state: number): void {
		let states: Int8Array = this.simStates;
		let previousState: number = this.simStates[sim];
		states[sim] = (state << 24 >> 24);
		GameCanvas.fillInts(this.simNeeds[sim], 0);
		if (previousState === 0) {
			this.unlockAchievement(11);
		}
		if (state === 12) {
			this.unlockAchievement(65);
		}
		if ((this.stateFlagsOf(previousState) & 16) === 0 && (this.stateFlagsOf(state) & 16) !== 0) {
			this.unlockAchievement(12);
		}
		if ((this.stateFlagsOf(previousState) & 8) === 0 && (this.stateFlagsOf(state) & 8) !== 0) {
			this.unlockAchievement(14);
		}
		if ((this.stateFlagsOf(previousState) & 4) === 0 && (this.stateFlagsOf(state) & 4) !== 0) {
			this.unlockAchievement(13);
		}
		if (GameCanvas.indexOfByteFrom(0, states, 1) === -1) {
			this.unlockAchievement(50);
		}
		let relaxingCount: number = 0;
		let allBusy: boolean = true;
		let allSleeping: boolean = true;
		for (let otherSim: number = 1; otherSim < states.length; otherSim++) {
			let simState: number = states[otherSim];
			allBusy = allBusy && (this.stateFlags[simState] & 1) !== 0;
			allSleeping = allSleeping && (this.stateFlags[simState] & 2) !== 0;
			if ((this.stateFlags[simState] & 4) !== 0) {
				relaxingCount++;
			}
		}
		if (allBusy) {
			this.unlockAchievement(66);
		}
		if (allSleeping) {
			this.unlockAchievement(67);
		}
		if (relaxingCount >= 3) {
			this.unlockAchievement(60);
		}
		if (state === 12) {
			this.canvas.gameScreen().openDialogPage(1, -1, sim);
		}
		else {
			let labelTextId: number = this.stateLabelTextIds[state];
			let nameTextId: number = this.simNameTextIds[sim];
			this.canvas.formatString1(-11, labelTextId, nameTextId);
			this.canvas.gameScreen().chooseDialogOptionAt(-11, this.stateSprite(state));
		}
	}
	handleSpecialInteraction(sim: number, interaction: number, flags: number): boolean {
		let screen: GameScreen = this.canvas.gameScreen();
		let nextState: number = -1;
		if (interaction === 85) {
			nextState = 11;
		}
		else {
			if (interaction === 86) {
				nextState = 12;
			}
			else {
				if (interaction === 96) {
					nextState = 13;
				}
				else {
					if (interaction === 95) {
						nextState = 14;
					}
					else {
						if (interaction === 97) {
							this.addSimMilestoneCount(sim, 2);
							screen.chooseDialogOptionAt(769, 768);
						}
						else {
							if (interaction === 98) {
								this.clearSimMilestone(sim, 2);
								screen.chooseDialogOptionAt(770, 765);
							}
							else {
								if (interaction === 99) {
									if ((flags & 4096) !== 0) {
										screen.chooseDialogOptionAt(636, this.simNameText(sim));
									}
									else {
										let lot: number = this.simLots[0];
										let hour: number = 0;
										if ((hour = this.simHours[0]) < ((this.lotHourDayBits[lot].length - 1) | 0)) {
											screen.selectFriend(lot, ((hour + 1) | 0), false);
										}
									}
								}
								else {
									if (interaction === 100) {
										screen.confirmRequest();
									}
								}
							}
						}
					}
				}
			}
		}
		if (nextState !== -1) {
			this.setSimState(sim, nextState);
			return true;
		}
		else {
			return false;
		}
	}
	static reactionText(index: number): number {
		return Neighborhood.REACTION_TEXT_IDS[index & 15];
	}
	resolveInteraction(out: Int32Array, sim: number, otherSim: number, interaction: number): void {
		let screen: GameScreen = this.canvas.gameScreen();
		let resultFlags: number = 0;
		let resultCode: number = -1;
		let animationId: number = -1;
		let conditionFlags: number = 0;
		let interrupt: boolean = false;
		let interactionFlags: number = this.interactionFlags[interaction];
		if (otherSim === 0) {
			if ((interactionFlags & 524288) === 0) {
				if (interaction === 19) {
					resultFlags = 35;
					resultCode = 11;
				}
				else {
					resultFlags = 19;
					resultCode = 10;
				}
			}
			this.addSimMilestoneCount(sim, 1);
			this.addSimMilestoneCount(0, 1);
			if (this.advanceSimState(sim)) {
				interrupt = true;
			}
		}
		else {
			if (sim !== 0) {
				if ((interactionFlags & 524288) === 0) {
					resultFlags = 20;
					resultCode = 10;
				}
			}
			else {
				let variant: number = 0;
				if ((variant = this.bestInteractionFor(otherSim, interaction)) === -1) {
					if ((interactionFlags & 2097152) !== 0) {
						resultCode = -1;
					}
					else {
						resultCode = -1;
					}
				}
				else {
					let recalled: boolean = false;
					let recalledInteraction: number = 0;
					if ((recalledInteraction = this.interactionNeedReqs[interaction][variant]) !== -1) {
						recalled = this.simRemember(otherSim, recalledInteraction);
					}
					let history: Int16Array = this.simActionHistory[otherSim];
					for (let position: number = 9; position > 0; position--) {
						history[position] = history[((position - 1) | 0)];
					}
					history[0] = (interaction << 16 >> 16);
					if ((interactionFlags & 16) !== 0) {
						this.clearSimMilestone(otherSim, 1);
					}
					else {
						this.addSimMilestoneCount(otherSim, 1);
						this.addSimMilestoneCount(0, 1);
					}
					resultCode = this.interactionResultTextIds[interaction][variant];
					let applyEffects: boolean = true;
					let partner: Sim = screen.findSimByIndex(otherSim);
					if (resultCode === -1 && partner !== null && partner.isSitting()) {
						applyEffects = false;
					}
					let needSum: number = 0;
					if (applyEffects) {
						let gainNeed: number = this.interactionNeedGains[interaction][variant];
						let lossNeed: number = this.interactionNeedLosses[interaction][variant];
						this.moveSim(otherSim, gainNeed, lossNeed);
						needSum = ((gainNeed + lossNeed) | 0);
						if (lossNeed > 0) {
							resultFlags = 64;
						}
						let previousNeed: number = this.dominantNeed(otherSim);
						let moodNeed: number = this.interactionMoodIndex[interaction][variant];
						let moodGain: number = this.interactionMoodGain[interaction][variant];
						let simDecay: Int32Array = this.simNeedDecay[otherSim];
						for (let need: number = 0; need < simDecay.length; need++) {
							if (need === moodNeed) {
								simDecay[moodNeed] = ((simDecay[moodNeed] + (moodGain << 16)) | 0);
								if (simDecay[moodNeed] > 13107200) {
									simDecay[moodNeed] = 13107200;
								}
							}
							else {
								simDecay[need] = ((simDecay[need] - 655360) | 0);
								if (simDecay[need] < 0) {
									simDecay[need] = 0;
								}
							}
						}
						let currentNeed: number = 0;
						if ((currentNeed = this.dominantNeed(otherSim)) !== previousNeed && !recalled) {
							let textId: number = this.needTextIds[currentNeed];
							let nameTextId: number = this.simNameText(otherSim);
							this.canvas.formatString1(-12, textId, nameTextId);
							screen.showNotification(-12, -1);
							if (currentNeed === 1 || currentNeed === 2 || currentNeed === 8) {
								this.unlockAchievement(16);
								this.addSimMilestoneCount(otherSim, 16384);
							}
							if (currentNeed === 2) {
								this.unlockAchievement(17);
								this.addSimMilestoneCount(otherSim, 64);
							}
							if (currentNeed === 1) {
								this.unlockAchievement(18);
								this.addSimMilestoneCount(otherSim, 128);
							}
						}
					}
					if (((conditionFlags = this.interactionConditionFlags[interaction][variant]) & 8) !== 0) {
						if (this.handleSpecialInteraction(otherSim, interaction, conditionFlags)) {
							interrupt = true;
						}
					}
					else {
						if (interaction === 99) {
							let lot: number = this.simLots[0];
							let hour: number = 0;
							if ((hour = this.simHours[0]) < ((this.lotHourDayBits[lot].length - 1) | 0)) {
								screen.selectRequest(lot, ((hour + 1) | 0));
							}
						}
					}
					if (this.checkStateTransition(otherSim)) {
						interrupt = true;
					}
					else {
						if ((interactionFlags & 2097152) === 0 && this.advanceSimState(otherSim)) {
							interrupt = true;
						}
					}
					if (interrupt) {
						animationId = this.stateBadgeIds[this.simStates[otherSim]];
					}
					if (animationId !== -1) {
						resultFlags = (resultFlags | 3);
					}
					else {
						let needFactor: number = 0;
						needFactor = FixedMath.clamp(needFactor = ((3 + needSum) | 0), 0, 6);
						resultFlags = (resultFlags | needFactor);
					}
					if (resultFlags < 3) {
						resultFlags = (resultFlags | 32);
					}
					else {
						resultFlags = (resultFlags | 16);
					}
				}
			}
		}
		if (resultCode !== -1) {
			let targetSim: number = -1;
			if (sim === 0) {
				targetSim = otherSim;
			}
			else {
				if (otherSim === 0) {
					targetSim = sim;
				}
			}
			if (targetSim !== -1) {
				this.adjustNeed(4, 1310720);
				this.watchSim(targetSim);
			}
		}
		if (sim === 0 && (conditionFlags & 8192) !== 0) {
			this.tryStartCard(otherSim, interaction, !interrupt);
		}
		out[0] = resultFlags;
		out[1] = resultCode;
		out[2] = animationId;
	}
	stateSprite(state: number): number {
		return this.stateSpriteIds[state];
	}
	stateFlagsOf(state: number): number {
		return this.stateFlags[state];
	}
	simState(sim: number): number {
		return this.simStates[sim];
	}
	collectNeedChanges(sim: number, out: Int32Array): void {
		let state: number = this.simState(sim);
		let needs: Int32Array = this.simNeeds[sim];
		let nextStates: Int8Array = this.stateTransitionStates[state];
		let needIndices: Int8Array = this.stateTransitionNeeds[state];
		let thresholds: Int16Array = this.stateTransitionThresholds[state];
		let outIndex: number = 0;
		let count: number = nextStates.length;
		for (let index: number = 0; index < count; index++) {
			let need: number = needIndices[index];
			let threshold: number = thresholds[index];
			let value: number = needs[need] >> 16;
			if (threshold > 0 && value > 0 || threshold < 0 && value < 0) {
				out[((outIndex + 0) | 0)] = nextStates[index];
				out[((outIndex + 1) | 0)] = FixedMath.div(value << 16, threshold << 16);
				outIndex = (((outIndex + 2) | 0) << 24 >> 24);
			}
		}
		out[outIndex] = -1;
	}
	isFreeToLeave(): boolean {
		if (!this.canvas.gameScreen().isNoSaveMode()) {
			return true;
		}
		else {
			let lot: number = this.canvas.lot.areaId();
			let count: number = this.simCount;
			for (let sim: number = 1; sim < count; sim++) {
				if (this.simHomeLotOf(sim) === lot) {
					let state: number = this.simState(sim);
					if ((this.stateFlagsOf(state) & 32) !== 0) {
						return true;
					}
				}
			}
			return lot === 0;
		}
	}
	clearSimMilestones(mask: number): void {
		for (let sim: number = 1; sim < this.simCount; sim++) {
			this.simMilestones[sim] = ((this.simMilestones[sim] & ~mask) << 16 >> 16);
		}
	}
	countPendingMilestones(mask: number): number {
		let all: boolean = true;
		let count: number = 0;
		for (let sim: number = ((this.simCount - 1) | 0); sim >= 1; sim--) {
			let lot: number = 0;
			if ((lot = this.simHomeLotOf(sim)) !== 0) {
				let has: boolean = (this.simMilestones[sim] & mask) !== 0;
				all = all && has;
				if (has && GameCanvas.indexOfShortFrom(lot, this.simHomeLots, ((sim + 1) | 0)) === -1) {
					count++;
				}
			}
		}
		return all ? -1 : count;
	}
	addSimMilestone(lot: number, flag: number): void {
		for (let sim: number = 1; sim < this.simCount; sim++) {
			if (this.simHomeLots[sim] === lot) {
				this.simMilestones[sim] = ((this.simMilestones[sim] | flag) << 16 >> 16);
			}
		}
		let pending: number = 0;
		if ((pending = this.countPendingMilestones(flag)) === -1) {
			switch (flag) {
				case 4:
					this.unlockAchievement(56);
					break;
				case 8:
					this.unlockAchievement(57);
					break;
				case 16:
					this.unlockAchievement(70);
			}
		}
		if (flag === 512 || flag === 1024) {
			if (pending === -1) {
				this.unlockAchievement(52);
				return;
			}
			let otherFlag: number = flag === 512 ? 1024 : 512;
			if (((pending + this.countPendingMilestones(otherFlag)) | 0) >= 3) {
				this.unlockAchievement(52);
			}
		}
	}
	addSimMilestoneCount(sim: number, flag: number): void {
		this.simMilestones[sim] = ((this.simMilestones[sim] | flag) << 16 >> 16);
		let count: number = GameCanvas.countAllFlagsFrom(flag, this.simMilestones, 0);
		switch (flag) {
			case 32:
				if (count >= 4) {
					this.unlockAchievement(68);
					return;
				}
				break;
			case 64:
				if (count >= 5) {
					this.unlockAchievement(69);
					return;
				}
				break;
			case 128:
				if (count >= 5) {
					this.unlockAchievement(58);
					return;
				}
				break;
			case 256:
				if (count >= 3) {
					this.unlockAchievement(59);
				}
		}
	}
	clearSimMilestone(sim: number, flag: number): void {
		this.simMilestones[sim] = ((this.simMilestones[sim] & ~flag) << 16 >> 16);
	}
	hasSimMilestone(sim: number, flag: number): boolean {
		return (this.simMilestones[sim] & flag) !== 0;
	}
	idleSimCount(): number {
		return ((this.simStates.length - GameCanvas.countEqualBytes(0, this.simStates)) | 0);
	}
	countSimsInState(state: number): number {
		return GameCanvas.nthIndexNotEqualByte(state, this.simStates, 0);
	}
	interactionAltText(index: number): number {
		return this.interactionAltTextIds[index];
	}
	interactionText(index: number): number {
		return this.interactionTextIds[index];
	}
	interactionFlagsOf(index: number): number {
		return this.interactionFlags[index];
	}
	interactionVariantCount(index: number): number {
		return this.interactionVariants[index].length;
	}
	interactionVariant(index: number, requirement: number): number {
		return this.interactionVariants[index][requirement];
	}
	fillInteractions(entity: Entity, labels: Int16Array, commands: Int16Array): void {
		if (entity.hasFlag(16384)) {
			let sim: Sim = (entity as unknown as Sim);
			this.fillSimInteractions(sim, labels, commands);
		}
		else {
			this.fillObjectInteractions(entity, labels, commands);
		}
	}
	fillSimInteractions(sim: Sim, labels: Int16Array, commands: Int16Array): void {
		let count: number = this.interactionCount;
		let textIds: Int16Array = this.interactionTextIds;
		let flags: Int32Array = this.interactionFlags;
		let simIndex: number = sim.variant;
		let romantic: boolean = sim.isSitting();
		this.watchSim(simIndex);
		let titleId: number = this.simNameTextIds[simIndex];
		GameCanvas.initList(labels, titleId);
		GameCanvas.initList(commands, titleId);
		if (this.canvas.gameScreen().isNewNeighborhood()) {
			let partnerFlag: boolean = false;
			GameCanvas.addToList(labels, textIds[155]);
			GameCanvas.addToList(commands, 155);
		}
		else {
			for (let interaction: number = 0; interaction < count; interaction++) {
				if ((flags[interaction] & 1) !== 0 && ((flags[interaction] & 4) !== 0 || !romantic) && ((flags[interaction] & 4) === 0 || romantic) && textIds[interaction] !== 24) {
					let allowed: boolean = true;
					let flagBits: number = 0;
					if (((flagBits = flags[interaction]) & 8) !== 0) {
						allowed = !this.hasSimMilestone(simIndex, 1);
					}
					else {
						if ((flagBits & 16) !== 0) {
							allowed = this.hasSimMilestone(simIndex, 1);
						}
					}
					if ((flagBits & 1056964608) !== 0) {
						let residentMask: number = this.selectedResidentMask();
						allowed = (flagBits & residentMask) !== 0;
					}
					if (allowed) {
						allowed = this.bestInteractionFor(simIndex, interaction) !== -1;
					}
					if (allowed) {
						let currentLot: number = this.simLots[0];
						if ((flagBits & 16384) !== 0) {
							if (currentLot !== -1) {
								allowed = this.lotKind(currentLot) === simIndex;
							}
							else {
								allowed = false;
							}
						}
						if (allowed && (flagBits & 32768) !== 0) {
							if (currentLot !== -1) {
								allowed = this.simLot(simIndex) === currentLot;
							}
							else {
								allowed = false;
							}
						}
					}
					label146: {
						if (allowed) {
							let lotIndex: number = this.canvas.lot.areaId();
							let homeLot: number = this.simHomeLotOf(simIndex);
							let tempResult: boolean = false;
							if ((flagBits & 196608) === 196608) {
								tempResult = this.hasSimMilestone(simIndex, 2);
							}
							else {
								if ((flagBits & 65536) !== 0) {
									allowed = lotIndex === 0 && homeLot !== 0;
									break label146;
								}
								if ((flagBits & 131072) === 0) {
									break label146;
								}
								tempResult = lotIndex !== 0 && homeLot === lotIndex;
							}
							allowed = tempResult;
						}
					}
					if (allowed && (flagBits & 8388608) !== 0) {
						let card: number = this.cardRequirementId(interaction);
						allowed = this.cardPerformers[card] === simIndex && this.isCardUnlocked(card);
					}
					if (allowed) {
						switch (interaction) {
							case 85:
								allowed = GameCanvas.indexOfByte(12, this.simStates) === -1;
								break;
							case 94:
								let playerSim: Sim = this.canvas.gameScreen().activePlayer;
								let dx: number = Math.abs(((sim.posX - playerSim.posX) | 0));
								let dy: number = Math.abs(((sim.posY - playerSim.posY) | 0));
								allowed = ((dx + dy) | 0) > 2097152;
								break;
							case 97:
								allowed = GameCanvas.indexOfAnyFlag(2, this.simMilestones) === -1;
								break;
							case 98:
								allowed = this.simStates[simIndex] !== 12;
						}
					}
					if (allowed) {
						GameCanvas.addToList(labels, textIds[interaction]);
						GameCanvas.addToList(commands, interaction);
					}
				}
			}
		}
	}
	fillObjectInteractions(entity: Entity, labels: Int16Array, commands: Int16Array): void {
		let playerSim: Sim = this.canvas.gameScreen().activePlayer;
		let textIds: Int16Array = this.interactionTextIds;
		let flags: Int32Array = this.interactionFlags;
		let titleId: number = entity.typeAnimId();
		GameCanvas.initList(labels, titleId);
		GameCanvas.initList(commands, titleId);
		let type: number = entity.type;
		let lot: Lot = this.canvas.lot;
		let interactionIds: Int16Array = this.canvas.lot.frameRefsAt(type);
		let typeFlags: number = lot.objectFlagsAt(type);
		let count: number = interactionIds.length;
		if ((typeFlags & 67108864) !== 0 && entity.hasState(256)) {
			GameCanvas.addToList(labels, textIds[110]);
			GameCanvas.addToList(commands, 110);
		}
		if ((typeFlags & 134217728) !== 0 && entity.hasState(512)) {
			GameCanvas.addToList(labels, textIds[109]);
			GameCanvas.addToList(commands, 109);
		}
		for (let index: number = 0; index < count; index++) {
			let interaction: number = interactionIds[index];
			let flagBits: number = 0;
			if (((flagBits = flags[interaction]) & 1) === 0 && ((flagBits & 32) === 0 || entity.hasState(64)) && ((flagBits & 64) === 0 || !entity.hasState(64)) && ((flagBits & 128) === 0 || entity.isIdleModeZero()) && ((flagBits & 256) === 0 || entity.isIdleModeOne()) && ((flagBits & 512) === 0 || !entity.hasState(256)) && ((flagBits & 1024) === 0 || !entity.hasState(512)) && ((flagBits & 4096) === 0 || this.isLotOccupant(entity, 0)) && ((flagBits & 8192) === 0 || this.canInteractAt(entity, 0)) && ((flagBits & 2048) === 0 || !this.isLotOccupant(entity, 0)) && ((flagBits & 131072) === 0 || lot.areaId() === 0) && ((flagBits & 65536) === 0 || lot.areaId() !== 0) && (interaction !== 142 || type !== 114 || this.canvas.gameStarted)) {
				if ((flagBits & 1056964608) !== 0) {
					let mask: number = this.selectedResidentMask();
					if ((flagBits & mask) === 0) {
						continue;
					}
				}
				if ((interaction !== 130 || !entity.hasState(16384)) && (interaction !== 131 || !entity.hasState(32768)) && (interaction !== 150 || this.hasSimMilestone(0, 1) || lot.areaId() === 0) && (interaction !== 153 || !this.hasSimMilestone(0, 1) && lot.areaId() !== 0)) {
					if (interaction === 152) {
						let gridX: number = Lot.toTileX(playerSim.posX);
						let gridY: number = Lot.toTileY(playerSim.posY);
						if ((lot.cellFlagsAt(gridX, gridY) & 16) !== 0) {
							continue;
						}
					}
					GameCanvas.addToList(labels, textIds[interaction]);
					GameCanvas.addToList(commands, interaction);
				}
			}
		}
		if ((typeFlags & 4096) !== 0) {
			GameCanvas.addToList(labels, textIds[106]);
			GameCanvas.addToList(commands, 106);
		}
		if (labels[0] === 0) {
			GameCanvas.addToList(labels, 274);
			GameCanvas.addToList(commands, -4);
		}
	}
	fillInventoryMenu(labels: Int16Array, commands: Int16Array, inventory: Int16Array): void {
		GameCanvas.initList(labels, inventory[1]);
		GameCanvas.initList(commands, 24);
		let textIds: Int16Array = this.interactionTextIds;
		let sprites: Int16Array = this.interactionSprites;
		let count: number = inventory[0];
		for (let index: number = 0; index < count; index++) {
			let item: number = inventory[((5 + index) | 0)];
			let spriteId: number = 0;
			if ((spriteId = sprites[item]) === 24 || this.countMenuItems(inventory, spriteId) < 2) {
				GameCanvas.addToList(labels, textIds[item]);
				GameCanvas.addToList(commands, item);
			}
			else {
				if (!GameCanvas.listContains(labels, spriteId)) {
					GameCanvas.addToList(labels, spriteId);
					GameCanvas.addToList(commands, -2);
				}
			}
		}
	}
	countMenuItems(list: Int16Array, spriteId: number): number {
		let sprites: Int16Array = this.interactionSprites;
		let count: number = 0;
		let size: number = list[0];
		for (let index: number = 0; index < size; index++) {
			let item: number = list[((index + 5) | 0)];
			let itemSprite: number = 0;
			if ((itemSprite = sprites[item]) === spriteId) {
				count++;
			}
		}
		return count;
	}
	fillFilteredMenu(labels: Int16Array, commands: Int16Array, source: Int16Array, filter: number): void {
		GameCanvas.initList(labels, filter);
		GameCanvas.initList(commands, filter);
		let textIds: Int16Array = this.interactionTextIds;
		let sprites: Int16Array = this.interactionSprites;
		let count: number = source[0];
		for (let index: number = 0; index < count; index++) {
			let item: number = source[((5 + index) | 0)];
			let spriteId: number = 0;
			if ((spriteId = sprites[item]) === filter) {
				GameCanvas.addToList(labels, textIds[item]);
				GameCanvas.addToList(commands, item);
			}
		}
	}
	fillResidentMenu(labels: Int16Array, commands: Int16Array, interaction: number): void {
		let textId: number = this.interactionTextIds[interaction];
		GameCanvas.initList(labels, textId);
		GameCanvas.initList(commands, textId);
		let lotIndex: number = this.canvas.lot.areaId();
		for (let sim: number = 1; sim < this.simCount; sim++) {
			if (this.simStates[sim] !== 0 && this.simHomeLotOf(sim) !== lotIndex) {
				GameCanvas.addToList(labels, this.simNameTextIds[sim]);
				GameCanvas.addToList(commands, interaction);
			}
		}
	}
	dominantNeed(sim: number): number {
		let decay: Int32Array = null!;
		let count: number = (decay = this.simNeedDecay[sim]).length;
		let need: number = 3;
		let value: number = 0;
		for (let index: number = 0; index < count; index++) {
			if (decay[index] > value) {
				value = decay[index];
				need = index;
			}
		}
		return need;
	}
	static stateNeedMask(state: number): number {
		switch (state) {
			case 0:
			default:
				return 2;
			case 1:
				return 4;
			case 2:
				return 32;
			case 3:
				return 1024;
			case 4:
				return 2048;
			case 5:
				return 8;
			case 6:
				return 64;
			case 7:
				return 128;
			case 8:
				return 16;
			case 9:
				return 256;
			case 10:
				return 512;
			case 11:
				return 4096;
			case 12:
				return 8192;
			case 13:
				return 16384;
			case 14:
				return 32768;
		}
	}
	hasRecentAction(sim: number, interaction: number, required: number, historyLength: number): boolean {
		let history: Int16Array = this.simActionHistory[sim];
		let foundCount: number = 0;
		for (let index: number = 0; index < historyLength; index++) {
			if (history[index] === interaction) {
				if (++foundCount >= required) {
					return true;
				}
			}
		}
		return false;
	}
	bestInteractionFor(sim: number, interaction: number): number {
		let need: number = this.dominantNeed(sim);
		let state: number = 0;
		let stateMask: number = Neighborhood.stateNeedMask(state = this.simStates[sim]);
		let stateReqs: Int8Array = this.interactionStateReqs[interaction];
		let needReqs: Int8Array = this.interactionNeedReqs[interaction];
		let flagReqs: Int32Array = this.interactionFlagReqs[interaction];
		let conditionFlags: Int16Array = this.interactionConditionFlags[interaction];
		for (let variant: number = ((stateReqs.length - 1) | 0); variant >= 0; variant--) {
			let stateReq: number = 0;
			let needReq: number = 0;
			let flagReq: number = 0;
			if (((stateReq = stateReqs[variant]) === -1 || stateReq === need) && ((needReq = needReqs[variant]) === -1 || this.simKnownIndex(sim, needReq) !== -1) && ((flagReq = flagReqs[variant]) === 0 || (flagReq & stateMask) !== 0)) {
				let conditions: number = 0;
				if (((conditions = conditionFlags[variant]) & 7) !== 0) {
					let requiredCount: number = 3;
					let historyLength: number = 6;
					if ((conditions & 1) !== 0) {
						requiredCount = 2;
						historyLength = 4;
					}
					else {
						if ((conditions & 4) !== 0) {
							requiredCount = 6;
							historyLength = 10;
						}
					}
					if (!this.hasRecentAction(sim, interaction, requiredCount, historyLength)) {
						continue;
					}
				}
				if ((conditions & 496) !== 0) {
					let resident: number = 0;
					if ((conditions & 16) !== 0) {
						resident = 0;
					}
					else {
						if ((conditions & 32) !== 0) {
							resident = 1;
						}
						else {
							if ((conditions & 64) !== 0) {
								resident = 2;
							}
							else {
								if ((conditions & 128) !== 0) {
									resident = 3;
								}
								else {
									resident = 4;
								}
							}
						}
					}
					let growth: number = this.residentGrowth[resident];
					let threshold: number = (conditions & 512) !== 0 ? 196608 : 65536;
					if (growth < threshold) {
						continue;
					}
				}
				let lot: number = 0;
				let hour: number = 0;
				if (((conditions & 3072) === 0 || ((conditions & 2048) !== 0 ? this.canRunTask(4) : this.canRunTask(1))) && (interaction !== 99 || (conditions & 8) === 0 || (conditions & 4096) !== 0 || (lot = this.simLots[0]) !== -1 && (hour = this.simHours[0]) < ((this.lotHourInteractions[lot].length - 1) | 0) && this.canEnterLot(lot, ((hour + 1) | 0)))) {
					return variant;
				}
			}
		}
		return -1;
	}
	canUseInteraction(sim: number, target: number, interaction: number): boolean {
		if (sim === 0) {
			let variant: number = this.bestInteractionFor(target, interaction);
			return (this.interactionConditionFlags[interaction][variant] & 4096) === 0;
		}
		else {
			return true;
		}
	}
	commentText(interaction: number): number {
		let categoryBits: number = 0;
		switch (interaction) {
			case 19:
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
			case 70:
			case 79:
			case 82:
			case 83:
			case 84:
			case 93:
			case 104:
			case 105:
			case 106:
			case 107:
			case 108:
			case 109:
			case 110:
			case 111:
			case 113:
			case 114:
			case 115:
			case 116:
			case 117:
			case 118:
			case 119:
			case 120:
			case 121:
			case 122:
			case 123:
			case 124:
			case 125:
			case 126:
			case 127:
			case 128:
			case 129:
			case 130:
			case 131:
			case 132:
			case 133:
			case 134:
			case 135:
			default:
				categoryBits = 16;
				break;
			case 20:
				categoryBits = 16;
				break;
			case 26:
				categoryBits = 1;
				break;
			case 27:
				categoryBits = 1;
				break;
			case 28:
				categoryBits = 16;
				break;
			case 29:
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 36:
			case 37:
			case 38:
			case 39:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
			case 58:
				categoryBits = 1;
				break;
			case 59:
				categoryBits = 4;
				break;
			case 60:
				categoryBits = 64;
				break;
			case 61:
				categoryBits = 1;
				break;
			case 62:
				categoryBits = 1;
				break;
			case 63:
				categoryBits = 1;
				break;
			case 64:
				categoryBits = 1;
				break;
			case 65:
				categoryBits = 1;
				break;
			case 66:
				categoryBits = 1;
				break;
			case 67:
				categoryBits = 4;
				break;
			case 68:
				categoryBits = 128;
				break;
			case 69:
				categoryBits = 512;
				break;
			case 71:
				categoryBits = 256;
				break;
			case 72:
				categoryBits = 4;
				break;
			case 73:
				categoryBits = 128;
				break;
			case 74:
				categoryBits = 512;
				break;
			case 75:
				categoryBits = 256;
				break;
			case 76:
				categoryBits = 32;
				break;
			case 77:
				categoryBits = 32;
				break;
			case 78:
				categoryBits = 32;
				break;
			case 80:
				categoryBits = 1;
				break;
			case 81:
				categoryBits = 1;
				break;
			case 85:
				categoryBits = 1;
				break;
			case 86:
				categoryBits = 1;
				break;
			case 87:
				categoryBits = 1;
				break;
			case 88:
				categoryBits = 1;
				break;
			case 89:
				categoryBits = 1;
				break;
			case 90:
				categoryBits = 16;
				break;
			case 91:
				categoryBits = 16;
				break;
			case 92:
				categoryBits = 16;
				break;
			case 94:
				categoryBits = 8;
				break;
			case 95:
				categoryBits = 1;
				break;
			case 96:
				categoryBits = 1;
				break;
			case 97:
				categoryBits = 1;
				break;
			case 98:
				categoryBits = 1;
				break;
			case 99:
				categoryBits = 1;
				break;
			case 100:
				categoryBits = 1;
				break;
			case 101:
				categoryBits = 1;
				break;
			case 102:
				categoryBits = 1;
				break;
			case 103:
				categoryBits = 1;
				break;
			case 112:
				categoryBits = -1;
				break;
			case 136:
				categoryBits = 1;
				break;
			case 137:
				categoryBits = 1;
				break;
			case 138:
				categoryBits = 256;
				break;
			case 139:
				categoryBits = 256;
				break;
			case 140:
				categoryBits = 1;
		}
		let matched: number = 0;
		for (let scanIndex: number = 0; scanIndex < Neighborhood.COMMENT_TEXT_IDS.length; scanIndex = (((scanIndex + 2) | 0) << 24 >> 24)) {
			if ((categoryBits & Neighborhood.COMMENT_TEXT_IDS[((scanIndex + 1) | 0)]) !== 0) {
				matched++;
			}
		}
		let chosen: number = this.canvas.randomRange(0, ((matched - 1) | 0));
		matched = 0;
		for (let pickIndex: number = 0; pickIndex < Neighborhood.COMMENT_TEXT_IDS.length; pickIndex = (((pickIndex + 2) | 0) << 24 >> 24)) {
			if ((categoryBits & Neighborhood.COMMENT_TEXT_IDS[((pickIndex + 1) | 0)]) !== 0) {
				if (matched === chosen) {
					return Neighborhood.COMMENT_TEXT_IDS[pickIndex];
				}
				matched++;
			}
		}
		return 196;
	}
	canEnterCard(card: number): boolean {
		let leftAction: number = this.visitCardLeft[card];
		let rightAction: number = this.visitCardRight[card];
		let upAction: number = this.visitCardUp[card];
		let downAction: number = this.visitCardDown[card];
		let lot: number = this.simLot(0);
		let hour: number = this.simHour(0);
		if ((leftAction === 2 || rightAction === 2 || upAction === 2 || downAction === 2) && hour === 0) {
			return false;
		}
		else {
			let lastHour: number = ((this.lotHourInteractions[lot].length - 1) | 0);
			return leftAction !== 1 && rightAction !== 1 && upAction !== 1 && downAction !== 1 || hour !== lastHour;
		}
	}
	pickRandomCard(): number {
		let card: number = 0;
		do {
			card = this.canvas.randomRange(0, ((this.visitCardTimeCosts.length - 1) | 0));
			new StringBuffer().append("trying card: ").append(card).toString();
		}
		while (!this.canEnterCard(card));
		new StringBuffer().append("final card: ").append(card).toString();
		return card;
	}
	visitOptionState(card: number, replay: boolean): number {
		let progress: number = this.canvas.randomPercent();
		if (replay) {
			return progress < this.visitCardMinuteLimits[card] ? 0 : 1;
		}
		else {
			return progress < this.visitCardOtherLimits[card] ? 2 : 3;
		}
	}
	cardTimeCost(card: number): number {
		return this.visitCardTimeCosts[card];
	}
	cardOptionText(card: number, option: number): number {
		switch (option) {
			case 0:
			default:
				return this.visitCardTextA[card];
			case 1:
				return this.visitCardTextB[card];
			case 2:
				return this.visitCardTextC[card];
			case 3:
				return this.visitCardTextD[card];
		}
	}
	applyCardDirection(card: number, direction: number): void {
		let action: number = 0;
		switch (direction) {
			case 0:
			default:
				action = this.visitCardUp[card];
				break;
			case 1:
				action = this.visitCardDown[card];
				break;
			case 2:
				action = this.visitCardLeft[card];
				break;
			case 3:
				action = this.visitCardRight[card];
		}
		let lot: number = this.simLots[0];
		let hour: number = this.simHours[0];
		switch (action) {
			case 0:
				return;
			case 1:
				this.unlockAchievement(54);
				this.selectLocation(lot, ((hour + 1) | 0));
				return;
			case 2:
				this.selectLocation(lot, ((hour - 1) | 0));
				return;
			case 3:
				this.selectLocation(-1, 0);
				return;
			case 4:
				let item: number = -1;
				switch (lot) {
					case 0:
						item = 21;
						break;
					case 1:
						item = 10;
						break;
					case 2:
						item = 22;
						break;
					case 3:
						item = 32;
						break;
					case 4:
						item = 17;
				}
				this.addItem(item, 1);
				return;
			case 5:
				this.addMoney(1000);
		}
	}
	resetCardSlots(): void {
		if (this.cardPerformers === null) {
			this.cardPerformers = new Int8Array(this.cardLabelTextIds.length);
			this.cardTimes = new Int32Array(this.cardLabelTextIds.length);
		}
		GameCanvas.fillBytes(this.cardPerformers, -1);
		GameCanvas.fillInts(this.cardTimes, -1);
	}
	cardLabelText(index: number): number {
		return this.cardLabelTextIds[index];
	}
	cardAltText(index: number): number {
		return this.cardAltTextIds[index];
	}
	cardFlagsAt(index: number): number {
		return this.cardFlags[index];
	}
	cardRequirementId(index: number): number {
		return GameCanvas.indexOfShort(index, this.cardRequiredLots);
	}
	tryStartCard(simIndex: number, lotId: number, allowNewCard: boolean): void {
		let now: number = this.gameTime;
		let cards: Int8Array = this.simCardList[simIndex];
		for (let cardSlot: number = 0; cardSlot < cards.length; cardSlot++) {
			let cardIndex: number = cards[cardSlot];
			let lastStarted: number = this.cardTimes[cardIndex];
			let ownedBySim: boolean = false;
			if (this.cardPerformers[cardIndex] === simIndex) {
				if (this.cardRequiredLots[cardIndex] === lotId) {
					this.canvas.gameScreen().openCareerGoalPage(cardIndex, simIndex);
					return;
				}
				if (((now - lastStarted) | 0) < 1440) {
					continue;
				}
				ownedBySim = true;
			}
			else {
				if (this.cardPerformers[cardIndex] !== -1) {
					continue;
				}
			}
			if (allowNewCard && GameCanvas.indexOfShort(lotId, this.cardRequirementIds[cardIndex]) !== -1 && (ownedBySim || GameCanvas.indexOfByte(simIndex, this.cardPerformers) === -1 && (lastStarted === -1 || ((now - lastStarted) | 0) >= 1440) && this.canvas.randomPercent() <= 30)) {
				this.cardTimes[cardIndex] = this.gameTime;
				this.startCard(cardIndex, simIndex);
				return;
			}
		}
	}
	startCard(cardIndex: number, simIndex: number): void {
		this.cardPerformers[cardIndex] = (simIndex << 24 >> 24);
		this.canvas.gameScreen().openSkillGoalPage(cardIndex, simIndex);
		switch (cardIndex) {
			case 0:
				this.clearSimMilestone(1, 4096);
				return;
			case 1:
				this.clearSimMilestone(1, 2048);
				return;
			case 4:
				this.clearSimMilestone(2, 8192);
				return;
			case 9:
				this.clearSimMilestone(13, 4096);
				return;
			case 16:
				this.clearSimMilestone(4, 4096);
				return;
			case 17:
				this.clearSimMilestone(4, 16384);
				return;
			case 18:
				this.clearSimMilestone(4, 2048);
				return;
			case 19:
				this.clearSimMilestone(2, 4096);
				return;
			case 20:
				this.clearSimMilestone(2, 16384);
				return;
			case 21:
				this.clearSimMilestone(2, 2048);
				return;
			case 29:
				this.clearSimMilestone(7, 4096);
			case 2:
			case 3:
			case 5:
			case 6:
			case 7:
			case 8:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 22:
			case 23:
			case 24:
			case 25:
			case 26:
			case 27:
			case 28:
		}
	}
	finishCard(card: number): void {
		let rewardMoney: number = 0;
		if ((rewardMoney = this.cardRewardMoney[card]) !== 0) {
			this.addMoney(rewardMoney);
		}
		let rewardItem: number = 0;
		if ((rewardItem = this.cardRewardItems[card]) !== -1) {
			this.addItem(rewardItem, -1);
		}
		let performer: number = -1;
		if ((this.cardFlags[card] & 1) !== 0) {
			performer = -2;
		}
		this.cardPerformers[card] = performer;
		this.cardTimes[card] = this.gameTime;
		if (card === 22) {
			this.addItem(48, 1);
		}
	}
	isCardUnlocked(card: number): boolean {
		let milestones: Int16Array = this.simMilestones;
		switch (card) {
			case 0:
				if ((milestones[1] & 4096) !== 0) {
					return true;
				}
				return false;
			case 1:
				if ((milestones[1] & 2048) !== 0) {
					return true;
				}
				return false;
			case 2:
			case 3:
			case 5:
			case 6:
			case 7:
			case 8:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 22:
			case 23:
			case 24:
			case 25:
			case 26:
			case 27:
			case 28:
			default:
				if (this.itemCount(this.cardRewardItems[card]) > 0) {
					return true;
				}
				return false;
			case 4:
				if ((milestones[2] & 8192) !== 0) {
					return true;
				}
				return false;
			case 9:
				if ((milestones[13] & 4096) !== 0) {
					return true;
				}
				return false;
			case 16:
				if ((milestones[4] & 4096) !== 0) {
					return true;
				}
				return false;
			case 17:
				if ((milestones[4] & 16384) !== 0) {
					return true;
				}
				return false;
			case 18:
				if ((milestones[4] & 2048) !== 0) {
					return true;
				}
				return false;
			case 19:
				if ((milestones[2] & 4096) !== 0) {
					return true;
				}
				return false;
			case 20:
				if ((milestones[2] & 16384) !== 0) {
					return true;
				}
				return false;
			case 21:
				if ((milestones[2] & 2048) !== 0) {
					return true;
				}
				return false;
			case 29:
				return (milestones[7] & 4096) !== 0;
		}
	}
	normalizeTaskSlots(): void {
		if (this.simKnownIds[0].length !== 5) {
			this.simKnownIds[0] = new Int8Array(5);
			GameCanvas.fillBytes(this.simKnownIds[0], -1);
		}
	}
	simNameText(sim: number): number {
		return this.simNameTextIds[sim];
	}
	simPortrait(sim: number): number {
		return this.simPortraitIds[sim];
	}
	simSpareByte(sim: number): number {
		return this.simSpareBytes[sim];
	}
	simAttribute(sim: number, index: number): number {
		return this.simAttributes[sim][index];
	}
	simHomeLotOf(sim: number): number {
		return this.hasSimMilestone(sim, 2) ? 0 : this.simHomeLots[sim];
	}
	simLot(sim: number): number {
		return sim === 10 && this.canvas.gameStarted ? 4 : this.simLots[sim];
	}
	simHour(sim: number): number {
		return sim === 10 && this.canvas.gameStarted ? ((this.lotHourInteractions[4].length - 1) | 0) : this.simHours[sim];
	}
	portraitData(index: number): Int8Array {
		return this.portraitDataValue[index];
	}
	setAttribute(index: number, value: number): void {
		this.simAttributes[0][index] = (value << 24 >> 24);
	}
	simHasFlag(sim: number, flag: number): boolean {
		return (this.simFlags[sim] & flag) !== 0;
	}
	simKnownIndex(sim: number, id: number): number {
		return GameCanvas.indexOfByte(id, this.simKnownIds[sim]);
	}
	simKnowsWell(sim: number, id: number): boolean {
		let index: number = 0;
		return (index = this.simKnownIndex(sim, id)) !== -1 ? this.simRepeatCounts[sim][index] >= 3 : false;
	}
	simRemember(sim: number, interaction: number): boolean {
		let counts: Int8Array = this.simRepeatCounts[sim];
		let index: number = this.simKnownIndex(sim, interaction);
		if (counts[index] < 3) {
			counts[index]++;
			if (counts[index] === 3) {
				let nameTextId: number = this.simNameText(sim);
				let labelTextId: number = this.itemNameTextIds[interaction];
				this.canvas.formatString2(-12, 412, nameTextId, labelTextId);
				this.canvas.gameScreen().showNotification(-12, -1);
				return true;
			}
		}
		return false;
	}
	simKnownCount(sim: number): number {
		return this.simKnownIds[sim].length;
	}
	simKnownId(sim: number, index: number): number {
		return this.simKnownIds[sim][index];
	}
	simKnownFreeSlot(): number {
		let ids: Int8Array = null!;
		return (((ids = this.simKnownIds[0]).length - GameCanvas.countEqualBytes(-1, ids)) | 0);
	}
	addKnown(id: number): void {
		if (this.simKnownIndex(0, id) === -1) {
			let sprite: number = 0;
			if ((sprite = this.itemSpriteIds[id]) !== -1) {
				this.removeKnown(sprite);
			}
			let slot: number = 0;
			if ((slot = this.simKnownIndex(0, -1)) === -1) {
				slot = 0;
			}
			this.simKnownIds[0][slot] = (id << 24 >> 24);
		}
	}
	removeKnown(id: number): void {
		let slot: number = 0;
		if ((slot = this.simKnownIndex(0, id)) !== -1) {
			this.simKnownIds[0][slot] = -1;
		}
	}
	simIndexOf(sim: number): number {
		return GameCanvas.indexOfShortFrom(sim, this.simNameTextIds, 1);
	}
	isSimIdle(sim: number): boolean {
		let state: number = 0;
		return (state = this.simStates[sim]) !== 2 && state !== 3 && state !== 4 && (this.lotStateNow(sim) & 16) === 0 && (this.lotStateNow(sim) & 8) !== 0;
	}
	update(elapsed: number): void {
		this.advanceTime(elapsed);
		this.updateSuggestion(elapsed);
		if (!this.isCelebrating()) {
			this.updateNeeds(elapsed);
			this.updateActiveTasks(elapsed);
		}
		if (!this.isCelebrating()) {
			this.updateSims(elapsed);
		}
		if (!this.isCelebrating()) {
			this.updateAchievementTimers(elapsed);
		}
		if (this.isCelebrating()) {
			this.celebrationTimer = Math.max(((this.celebrationTimer - elapsed) | 0), 0);
		}
	}
	resetAll(): void {
		this.paused = false;
		this.watchSim(-1);
		GameCanvas.fillInts(this.needModifiers, 0);
		for (let sim: number = 1; sim < this.simCount; sim++) {
			this.clearSimMilestone(sim, 1);
			GameCanvas.fillInts(this.simNeedDecay[sim], 0);
			GameCanvas.fillShorts(this.simActionHistory[sim], -1);
		}
		this.clearSimMilestone(0, 1);
		this.celebrate();
	}
	celebrate(): void {
		this.celebrationTimer = 2500;
	}
	isCelebrating(): boolean {
		return this.celebrationTimer !== 0;
	}
	completeAllTips(): void {
		let count: number = this.achievementCount();
		let mask: number = this.selectedResidentSpriteB();
		for (let index: number = 0; index < count; index++) {
			if ((this.achievementFlagAt(index) & mask) !== 0) {
				this.canvas.addFlag(index);
			}
		}
		this.markItemVisited(99);
	}
	resetTips(): void {
		if (this.pendingTips === null) {
			this.pendingTips = new Int16Array(100);
			this.tipValues = new Int32Array(100);
			this.tipTimes = new Int32Array(100);
		}
		GameCanvas.fillShorts(this.pendingTips, -1);
		GameCanvas.fillInts(this.tipValues, 0);
		GameCanvas.fillInts(this.tipTimes, 0);
	}
	pushTip(key: number, subKey: number, value: number): void {
		let packedKey: number = 0;
		let slot: number = GameCanvas.indexOfShort(packedKey = key << 8 | subKey, this.pendingTips);
		if (value === 0 && slot !== -1) {
			this.removeTip(key, subKey);
		}
		else {
			if (slot === -1) {
				slot = GameCanvas.indexOfShort(-1, this.pendingTips);
			}
			this.pendingTips[slot] = (packedKey << 16 >> 16);
			this.tipValues[slot] = value;
			this.tipTimes[slot] = this.gameTime;
		}
	}
	tipValue(key: number, subKey: number): number {
		let slot: number = 0;
		return (slot = GameCanvas.indexOfShort(key << 8 | subKey, this.pendingTips)) === -1 ? 0 : this.tipValues[slot];
	}
	tipTime(key: number, subKey: number): number {
		let slot: number = 0;
		return (slot = GameCanvas.indexOfShort(key << 8 | subKey, this.pendingTips)) === -1 ? -1 : this.tipTimes[slot];
	}
	removeTip(key: number, subKey: number): void {
		let slot: number = 0;
		if ((slot = GameCanvas.indexOfShort(key << 8 | subKey, this.pendingTips)) !== -1) {
			this.pendingTips[slot] = -1;
			this.tipValues[slot] = 0;
			this.tipTimes[slot] = 0;
		}
	}
	resetAllState(): void {
		this.resetProgress();
		this.refreshItemCounts();
		this.resetCareerLevels();
		this.resetSelection();
		this.selectedResident = -1;
		GameCanvas.fillBytes(this.simKnownIds[0], -1);
		this.resetAchievementState();
		this.resetNeedValues();
		this.resetActiveTasks();
		this.resetMoodDeltas();
		this.resetCardSlots();
		this.normalizeTaskSlots();
		this.resetSimStates();
		this.resetTips();
		GameCanvas.fillBytes(this.simAttributes[0], 0);
	}
	load(input: DataInputStream): void {
		for (let attrIndex: number = 0; attrIndex < 10; attrIndex++) {
			this.simAttributes[0][attrIndex] = input.readByte();
		}
		let knownIds: Int8Array = this.simKnownIds[0];
		for (let knownIndex: number = 0; knownIndex < 5; knownIndex++) {
			knownIds[knownIndex] = input.readByte();
		}
		this.timeAccumulator = input.readInt();
		this.gameTime = input.readInt();
		this.money = input.readInt();
		this.moneySpent = input.readInt();
		this.lastCareerLevel = input.readByte();
		this.lastPromotionDay = input.readShort();
		for (let itemIndex: number = 0; itemIndex < this.itemCounts.length; itemIndex++) {
			this.itemCounts[itemIndex] = input.readByte();
		}
		for (let growthIndex: number = 0; growthIndex < this.residentGrowth.length; growthIndex++) {
			this.residentGrowth[growthIndex] = input.readInt();
		}
		this.simLots[0] = input.readByte();
		this.simHours[0] = input.readByte();
		this.lastVisitDay = input.readByte();
		this.tickCount = input.readShort();
		this.idleTicks = input.readShort();
		for (let hourIndex: number = 0; hourIndex < this.lotLastHour.length; hourIndex++) {
			this.lotLastHour[hourIndex] = input.readByte();
		}
		this.suggestedLot = input.readByte();
		this.suggestionTimer = input.readInt();
		this.selectedResident = input.readByte();
		this.pendingAchievementId = input.readByte();
		this.randomSeed = input.readShort();
		this.achievementTimer = input.readInt();
		for (let slotIndex: number = 0; slotIndex < this.achievementSlots.length; slotIndex++) {
			this.achievementSlots[slotIndex] = input.readByte();
		}
		this.usedTaskBits = input.readInt();
		this.milestoneFlags = input.readInt();
		for (let achievementIndex: number = 0; achievementIndex < this.achievementTimes.length; achievementIndex++) {
			this.achievementTimes[achievementIndex] = input.readInt();
		}
		this.altEventCount = input.readShort();
		this.otherEventCount = input.readShort();
		for (let taskTimeIndex: number = 0; taskTimeIndex < this.recentTaskTimes.length; taskTimeIndex++) {
			this.recentTaskTimes[taskTimeIndex] = input.readInt();
		}
		this.visitedLotBits = input.readInt();
		for (let needIndex: number = 0; needIndex < this.needValues.length; needIndex++) {
			this.needValues[needIndex] = input.readInt();
		}
		for (let slot: number = 0; slot < 6; slot++) {
			this.activeTasks[slot] = input.readShort();
			this.taskTimers[slot] = input.readInt();
		}
		this.applyMoodDeltas();
		let simTotal: number = this.simCount;
		for (let sim: number = 1; sim < simTotal; sim++) {
			this.simStates[sim] = input.readByte();
			this.simMilestones[sim] = input.readShort();
			let needs: Int32Array = this.simNeeds[sim];
			for (let need: number = 0; need < needs.length; need++) {
				needs[need] = input.readInt();
			}
			let counts: Int8Array = this.simRepeatCounts[sim];
			for (let countIndex: number = 0; countIndex < counts.length; countIndex++) {
				counts[countIndex] = input.readByte();
			}
		}
		for (let tipIndex: number = 0; tipIndex < 100; tipIndex++) {
			this.pendingTips[tipIndex] = input.readShort();
			this.tipValues[tipIndex] = input.readInt();
			this.tipTimes[tipIndex] = input.readInt();
		}
		for (let card: number = 0; card < this.cardPerformers.length; card++) {
			this.cardPerformers[card] = input.readByte();
			this.cardTimes[card] = input.readInt();
		}
	}
	save(output: DataOutputStream): void {
		for (let attrIndex: number = 0; attrIndex < 10; attrIndex++) {
			output.writeByte(this.simAttributes[0][attrIndex]);
		}
		let knownIds: Int8Array = this.simKnownIds[0];
		for (let knownIndex: number = 0; knownIndex < 5; knownIndex++) {
			output.writeByte(knownIds[knownIndex]);
		}
		output.writeInt(this.timeAccumulator);
		output.writeInt(this.gameTime);
		output.writeInt(this.money);
		output.writeInt(this.moneySpent);
		output.writeByte(this.lastCareerLevel);
		output.writeShort(this.lastPromotionDay);
		for (let itemIndex: number = 0; itemIndex < this.itemCounts.length; itemIndex++) {
			output.writeByte(this.itemCounts[itemIndex]);
		}
		for (let growthIndex: number = 0; growthIndex < this.residentGrowth.length; growthIndex++) {
			output.writeInt(this.residentGrowth[growthIndex]);
		}
		output.writeByte(this.simLots[0]);
		output.writeByte(this.simHours[0]);
		output.writeByte(this.lastVisitDay);
		output.writeShort(this.tickCount);
		output.writeShort(this.idleTicks);
		for (let hourIndex: number = 0; hourIndex < this.lotLastHour.length; hourIndex++) {
			output.writeByte(this.lotLastHour[hourIndex]);
		}
		output.writeByte(this.suggestedLot);
		output.writeInt(this.suggestionTimer);
		output.writeByte(this.selectedResident);
		output.writeByte(this.pendingAchievementId);
		output.writeShort(this.randomSeed);
		output.writeInt(this.achievementTimer);
		for (let slotIndex: number = 0; slotIndex < this.achievementSlots.length; slotIndex++) {
			output.writeByte(this.achievementSlots[slotIndex]);
		}
		output.writeInt(this.usedTaskBits);
		output.writeInt(this.milestoneFlags);
		for (let achievementIndex: number = 0; achievementIndex < this.achievementTimes.length; achievementIndex++) {
			output.writeInt(this.achievementTimes[achievementIndex]);
		}
		output.writeShort(this.altEventCount);
		output.writeShort(this.otherEventCount);
		for (let taskTimeIndex: number = 0; taskTimeIndex < this.recentTaskTimes.length; taskTimeIndex++) {
			output.writeInt(this.recentTaskTimes[taskTimeIndex]);
		}
		output.writeInt(this.visitedLotBits);
		for (let needIndex: number = 0; needIndex < this.needValues.length; needIndex++) {
			output.writeInt(this.needValues[needIndex]);
		}
		for (let slot: number = 0; slot < 6; slot++) {
			output.writeShort(this.activeTasks[slot]);
			output.writeInt(this.taskTimers[slot]);
		}
		let simTotal: number = this.simCount;
		for (let sim: number = 1; sim < simTotal; sim++) {
			output.writeByte(this.simStates[sim]);
			output.writeShort(this.simMilestones[sim]);
			let needs: Int32Array = this.simNeeds[sim];
			for (let need: number = 0; need < needs.length; need++) {
				output.writeInt(needs[need]);
			}
			let counts: Int8Array = this.simRepeatCounts[sim];
			for (let countIndex: number = 0; countIndex < counts.length; countIndex++) {
				output.writeByte(counts[countIndex]);
			}
		}
		for (let tipIndex: number = 0; tipIndex < 100; tipIndex++) {
			output.writeShort(this.pendingTips[tipIndex]);
			output.writeInt(this.tipValues[tipIndex]);
			output.writeInt(this.tipTimes[tipIndex]);
		}
		for (let card: number = 0; card < this.cardPerformers.length; card++) {
			output.writeByte(this.cardPerformers[card]);
			output.writeInt(this.cardTimes[card]);
		}
	}
	applyCommand(command: number): void {
		let sim: number = this.watchedSim;
		let growth: Int32Array = this.residentGrowth;
		switch (command) {
			case 0:
				this.advanceTime(60000);
				return;
			case 1:
				this.advanceTime(1440000);
				return;
			case 2:
				let fullValue: number = 3276800;
				for (let need: number = 0; need < this.needValues.length; need++) {
					this.needValues[need] = fullValue;
				}
				return;
			case 3:
				this.setSimState(sim, 5);
				return;
			case 4:
				this.setSimState(sim, 2);
				return;
			case 5:
				this.setSimState(sim, 8);
				return;
			case 6:
				this.setSimState(sim, 6);
				return;
			case 7:
				this.setSimState(sim, 3);
				return;
			case 8:
				this.setSimState(sim, 9);
				return;
			case 9:
				this.setSimState(sim, 7);
				return;
			case 10:
				this.setSimState(sim, 4);
				return;
			case 11:
				this.setSimState(sim, 10);
				return;
			case 12:
				for (let simIndex: number = 1; simIndex < this.simCount; simIndex++) {
					if (this.simStates[simIndex] === 0) {
						this.setSimState(simIndex, 5);
					}
				}
				return;
			case 13:
				growth[0] = Math.max(((growth[0] - 65536) | 0), 0);
				growth[0] = 327675;
				return;
			case 14:
				growth[0] = Math.min(((growth[0] + 65536) | 0), 327680);
				return;
			case 15:
				growth[1] = Math.max(((growth[1] - 65536) | 0), 0);
				growth[1] = 327675;
				return;
			case 16:
				growth[1] = Math.min(((growth[1] + 65536) | 0), 327680);
				return;
			case 17:
				growth[2] = Math.max(((growth[2] - 65536) | 0), 0);
				growth[2] = 327675;
				return;
			case 18:
				growth[2] = Math.min(((growth[2] + 65536) | 0), 327680);
				return;
			case 19:
				growth[3] = Math.max(((growth[3] - 65536) | 0), 0);
				growth[3] = 327675;
				return;
			case 20:
				growth[3] = Math.min(((growth[3] + 65536) | 0), 327680);
				return;
			case 21:
				growth[4] = Math.max(((growth[4] - 65536) | 0), 0);
				growth[4] = 327675;
				return;
			case 22:
				growth[4] = Math.min(((growth[4] + 65536) | 0), 327680);
				return;
			case 23:
				this.pendingAchievementId = (this.pickRandomAchievement() << 24 >> 24);
				this.achievementTimer = 0;
				return;
			case 24:
				for (let index: number = 0; index < this.achievementTextIds.length; index++) {
					this.canvas.addFlag(index);
					this.canvas.addVisitedFlag(index);
				}
				this.canvas.markGameStarted();
				return;
			case 25:
				for (let item: number = 0; item < this.itemCounts.length; item++) {
					this.itemCounts[item] = (this.canvas.lot.categoryFlagAt(item) << 24 >> 24);
				}
		}
	}
	constructor(canvas: GameCanvas) {
		this.canvas = canvas;
		this.loadData();
		this.resetAllState();
	}
}
