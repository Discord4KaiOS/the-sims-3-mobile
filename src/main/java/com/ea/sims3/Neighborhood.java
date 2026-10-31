package com.ea.sims3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Neighborhood implements SpriteIndex, SoundIndex, Resources {
   private GameCanvas canvas;
   private short[] residentIds;
   private int[][] residentGrowthSteps;
   private short[][] residentLook;
   private short[][] residentLevelValues;
   private short[] lotNameTextIds;
   private short[] lotSubtitleTextIds;
   private short[] lotOccupantTypes;
   private byte[] lotKinds;
   private short[] lotSpritesLow;
   private short[] lotSpritesHigh;
   private short[] lotExtraSprites;
   private short[][] lotHourInteractions;
   private short[][] lotHourIncome;
   private short[][] lotHourDayBits;
   private short[][] lotHourStarts;
   private short[][] lotHourEnds;
   private byte[][][] lotHourRequirements;
   private short[] residentLabelTextIds;
   private short[] residentInfoTextIds;
   private short[] residentSpritesA;
   private short[] residentSpritesB;
   private short[] achievementTextIds;
   private short[] achievementFlags;
   public int needCount;
   private short[] needIds;
   private int[] needInitialValues;
   private int[] needDecayRates;
   private short[] needMoodFlags;
   private int[] needThresholds;
   private short[] needLabelTextIds;
   private short[] needHintTextIds;
   private byte[] achievementTriggerIds;
   private int taskCount;
   private short[] taskIds;
   private int[] taskDurations;
   private byte[] taskFollowUps;
   private byte[] taskAchievements;
   private byte[] taskFlags;
   private byte[][] taskNeedIndices;
   private int[][] taskNeedDeltas;
   private byte[][] taskMoodIndices;
   private byte[][] taskMoodDeltas;
   private byte[][] taskLinkIndices;
   private int[][] taskLinkDeltas;
   private short[] itemNameTextIds;
   private short[] itemSpriteIds;
   private int simNeedCount;
   private short[] stateSpriteIds;
   private byte[] stateFlags;
   private short[] stateBadgeIds;
   private short[] stateLabelTextIds;
   private byte[][] stateTransitionStates;
   private byte[][] stateTransitionNeeds;
   private short[][] stateTransitionThresholds;
   public int simCount;
   private short[] simNameTextIds;
   private byte[] simSpareBytes;
   private byte[][] simAttributes;
   private short[] simHomeLots;
   private byte[] simFlags;
   private byte[] simPortraitIds;
   private byte[] simLots;
   private byte[] simHours;
   private byte[][] simKnownIds;
   private byte[][] simCardList;
   public int portraitCount;
   private byte[][] portraitData;
   private short[] needTextIds;
   private int interactionCount;
   private short[] interactionTextIds;
   private short[] interactionSprites;
   private int[] interactionFlags;
   private short[] interactionAltTextIds;
   private byte[][] interactionStateReqs;
   private byte[][] interactionNeedReqs;
   private int[][] interactionFlagReqs;
   private short[][] interactionConditionFlags;
   private byte[][] interactionNeedGains;
   private byte[][] interactionNeedLosses;
   private byte[][] interactionMoodIndex;
   private byte[][] interactionMoodGain;
   private short[][] interactionResultTextIds;
   private byte[][] interactionVariants;
   private short[] visitCardTimeCosts;
   private byte[] visitCardMinuteLimits;
   private byte[] visitCardOtherLimits;
   private byte[] visitCardUp;
   private short[] visitCardTextA;
   private byte[] visitCardDown;
   private short[] visitCardTextB;
   private byte[] visitCardLeft;
   private short[] visitCardTextC;
   private byte[] visitCardRight;
   private short[] visitCardTextD;
   private byte[] cardFlags;
   private short[] cardLabelTextIds;
   private short[] cardAltTextIds;
   private short[] cardRequiredLots;
   private short[] cardRewardItems;
   private short[] cardRewardMoney;
   private short[][] cardRequirementIds;
   public static final int[] WEEKDAY_BITS = new int[]{4, 8, 16, 32, 64, 128, 256};
   public static final int[] WEEKDAY_TEXT_IDS = new int[]{653, 654, 655, 656, 657, 658, 659};
   private int timeAccumulator;
   public int gameTime;
   public int money;
   public int moneySpent;
   public boolean paused;
   private byte lastCareerLevel;
   private short lastPromotionDay;
   private byte[] itemCounts;
   public int lastPurchasedItem;
   private int[] residentGrowth;
   public static final int[] FEATURE_FLAGS = new int[]{1, 2};
   public static final int[] FEATURE_TEXT_IDS = new int[]{665, 666};
   private byte lastVisitDay;
   private short tickCount;
   private short idleTicks;
   private byte[] lotLastHour;
   private byte suggestedLot;
   private int suggestionTimer;
   public byte selectedResident;
   public byte pendingAchievementId;
   private short randomSeed;
   private int achievementTimer;
   private byte[] achievementSlots;
   private int usedTaskBits;
   private int milestoneFlags;
   private int[] achievementTimes;
   private short altEventCount;
   private short otherEventCount;
   private int[] recentTaskTimes;
   private int visitedLotBits;
   private int needTimer;
   private int[] needValues;
   private int[] needDecay;
   private int[] needOffsets;
   private int[] needModifiers;
   private short[] activeTasks;
   private int[] taskTimers;
   private static final short[] MOOD_TEXT_IDS = new short[]{387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398};
   private int[] moodDeltas;
   private int moodIndex;
   public int moodValue;
   private byte[] simStates;
   private short[] simMilestones;
   private int[][] simNeeds;
   private int[][] simNeedDecay;
   private short[][] simActionHistory;
   private byte[][] simRepeatCounts;
   private int simTickTimer;
   public int watchedSim;
   private int watchedSimTimer;
   private static final short[] REACTION_TEXT_IDS = new short[]{572, 571, 570, 196, 573, 574, 575};
   private static final short[] COMMENT_TEXT_IDS = new short[]{
      1,
      5,
      2,
      165,
      3,
      9,
      7,
      289,
      8,
      261,
      9,
      321,
      14,
      257,
      11,
      1,
      12,
      289,
      15,
      257,
      16,
      261,
      17,
      257,
      18,
      257,
      19,
      1,
      20,
      513,
      21,
      257,
      22,
      257,
      24,
      257,
      25,
      305,
      26,
      1073,
      27,
      257,
      196,
      2
   };
   private byte[] cardPerformers;
   private int[] cardTimes;
   private int celebrationTimer;
   private short[] pendingTips;
   private int[] tipValues;
   private int[] tipTimes;

   public Neighborhood(GameCanvas canvas) {
      this.canvas = canvas;
      this.loadData();
      this.resetAllState();
   }

   public final void setTipValue(int tipKey, int tipValue) {
      int tipKeyCopy;
      int slot;
      if ((slot = GameCanvas.indexOfShort(tipKeyCopy = 0 | tipKey, this.pendingTips)) != -1) {
         int highBits = this.pendingTips[slot] & '\uff00';
         this.pendingTips[slot] = (short)(highBits | tipValue);
      }
   }

   private static final short readShort(DataInputStream input) throws IOException {
      return input.readShort();
   }

   private void loadData() {
      ResourceLoader resourceLoader = this.canvas.resources;

      try {
         DataInputStream input;
         byte residentCount;
         short[] residentIds = new short[residentCount = (input = new DataInputStream(resourceLoader.openStream(61))).readByte()];
         int[][] residentGrowthSteps = new int[residentCount][];
         short[][] residentLooks = new short[residentCount][];
         short[][] residentLevelValues = new short[residentCount][];

         for (int residentIndex = 0; residentIndex < residentCount; residentIndex++) {
            residentIds[residentIndex] = readShort(input);
            short[] lookBuf = new short[3];

            for (int lookIndex = 0; lookIndex < lookBuf.length; lookIndex++) {
               lookBuf[lookIndex] = readShort(input);
            }

            residentLooks[residentIndex] = lookBuf;
            int[] growthBuf = new int[5];

            for (int growthIndex = 0; growthIndex < 5; growthIndex++) {
               growthBuf[growthIndex] = input.readInt();
            }

            residentGrowthSteps[residentIndex] = growthBuf;
            short[] levelBuf = new short[6];

            for (int levelIndex = 0; levelIndex < 6; levelIndex++) {
               levelBuf[levelIndex] = readShort(input);
            }

            residentLevelValues[residentIndex] = levelBuf;
         }

         byte lotCount;
         short[] lotNameTextIds = new short[lotCount = input.readByte()];
         short[] lotSubtitleTextIds = new short[lotCount];
         short[] lotOccupantTypes = new short[lotCount];
         byte[] lotKinds = new byte[lotCount];
         short[] lotSpritesLow = new short[lotCount];
         short[] lotSpritesHigh = new short[lotCount];
         short[] lotExtraSprites = new short[lotCount];
         short[][] lotHourInteractions = new short[lotCount][];
         short[][] lotHourIncome = new short[lotCount][];
         short[][] lotHourDayBits = new short[lotCount][];
         short[][] lotHourStarts = new short[lotCount][];
         short[][] lotHourEnds = new short[lotCount][];
         byte[][][] lotHourRequirements = new byte[lotCount][][];

         for (int lotIndex = 0; lotIndex < lotCount; lotIndex++) {
            lotNameTextIds[lotIndex] = readShort(input);
            lotSubtitleTextIds[lotIndex] = readShort(input);
            lotOccupantTypes[lotIndex] = readShort(input);
            lotKinds[lotIndex] = input.readByte();
            lotSpritesLow[lotIndex] = readShort(input);
            lotSpritesHigh[lotIndex] = readShort(input);
            lotExtraSprites[lotIndex] = readShort(input);
            byte hourCount;
            short[] hourInteractionBuf = new short[hourCount = input.readByte()];
            short[] hourIncomeBuf = new short[hourCount];
            short[] hourDayBuf = new short[hourCount];
            short[] hourStartBuf = new short[hourCount];
            short[] hourEndBuf = new short[hourCount];
            byte[][] hourReqBuf = new byte[hourCount][];

            for (int hourIndex = 0; hourIndex < hourCount; hourIndex++) {
               hourInteractionBuf[hourIndex] = readShort(input);
               hourIncomeBuf[hourIndex] = input.readShort();
               hourDayBuf[hourIndex] = input.readShort();
               int startMinutes = input.readShort();
               int endMinutes = input.readShort();
               startMinutes = startMinutes % 100 + startMinutes / 100 * 60;
               endMinutes = endMinutes % 100 + endMinutes / 100 * 60;
               hourStartBuf[hourIndex] = (short)startMinutes;
               hourEndBuf[hourIndex] = (short)endMinutes;
               byte[] requirement;
               (requirement = new byte[4])[0] = (byte)readShort(input);
               requirement[1] = input.readByte();
               requirement[2] = (byte)readShort(input);
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

         byte labelCount;
         short[] residentLabelTextIds = new short[labelCount = input.readByte()];
         short[] residentInfoTextIds = new short[labelCount];
         short[] residentSpritesA = new short[labelCount];
         short[] residentSpritesB = new short[labelCount];

         for (int labelIndex = 0; labelIndex < labelCount; labelIndex++) {
            residentLabelTextIds[labelIndex] = readShort(input);
            residentInfoTextIds[labelIndex] = readShort(input);
            residentSpritesA[labelIndex] = readShort(input);
            residentSpritesB[labelIndex] = input.readShort();
         }

         byte achievementCount;
         short[] achievementTextIds = new short[achievementCount = input.readByte()];
         short[] achievementFlags = new short[achievementCount];

         for (int achievementIndex = 0; achievementIndex < achievementCount; achievementIndex++) {
            achievementTextIds[achievementIndex] = readShort(input);
            achievementFlags[achievementIndex] = input.readShort();
         }

         byte needCount;
         short[] needIds = new short[needCount = input.readByte()];
         int[] needInitialValues = new int[needCount];
         int[] needDecayRates = new int[needCount];
         short[] needMoodFlags = new short[needCount];
         int[] needThresholds = new int[needCount];
         short[] needLabelTextIds = new short[needCount];
         short[] needHintTextIds = new short[needCount];

         for (int needIndex = 0; needIndex < needCount; needIndex++) {
            needIds[needIndex] = readShort(input);
            needInitialValues[needIndex] = input.readInt();
            needDecayRates[needIndex] = input.readInt();
            needMoodFlags[needIndex] = input.readShort();
            needThresholds[needIndex] = input.readInt();
            needLabelTextIds[needIndex] = readShort(input);
            needHintTextIds[needIndex] = readShort(input);
         }

         byte[] achievementTriggerIds = new byte[input.readByte()];
         input.readFully(achievementTriggerIds);
         byte taskCount;
         short[] taskIds = new short[taskCount = input.readByte()];
         int[] taskDurations = new int[taskCount];
         byte[] taskFollowUps = new byte[taskCount];
         byte[] taskAchievements = new byte[taskCount];
         byte[] taskFlags = new byte[taskCount];
         byte[][] taskNeedIndices = new byte[taskCount][];
         int[][] taskNeedDeltas = new int[taskCount][];
         byte[][] taskMoodIndices = new byte[taskCount][];
         byte[][] taskMoodDeltas = new byte[taskCount][];
         byte[][] taskLinkIndices = new byte[taskCount][];
         int[][] taskLinkDeltas = new int[taskCount][];

         for (int taskIndex = 0; taskIndex < taskCount; taskIndex++) {
            taskIds[taskIndex] = readShort(input);
            taskDurations[taskIndex] = input.readInt();
            taskFollowUps[taskIndex] = input.readByte();
            taskAchievements[taskIndex] = input.readByte();
            taskFlags[taskIndex] = input.readByte();
            byte subCount;
            byte[] entryIndices = new byte[subCount = input.readByte()];
            int[] entryValues = new int[subCount];

            for (int entryIndex = 0; entryIndex < subCount; entryIndex++) {
               entryIndices[entryIndex] = input.readByte();
               entryValues[entryIndex] = input.readInt();
            }

            taskNeedIndices[taskIndex] = entryIndices;
            taskNeedDeltas[taskIndex] = entryValues;
            entryIndices = new byte[subCount = input.readByte()];
            byte[] moodDeltaBuf = new byte[subCount];

            for (int moodIndex2 = 0; moodIndex2 < subCount; moodIndex2++) {
               entryIndices[moodIndex2] = (byte)readShort(input);
               moodDeltaBuf[moodIndex2] = input.readByte();
            }

            taskMoodIndices[taskIndex] = entryIndices;
            taskMoodDeltas[taskIndex] = moodDeltaBuf;
            entryIndices = new byte[subCount = input.readByte()];
            entryValues = new int[subCount];

            for (int linkIndex = 0; linkIndex < subCount; linkIndex++) {
               entryIndices[linkIndex] = input.readByte();
               entryValues[linkIndex] = input.readInt();
            }

            taskLinkIndices[taskIndex] = entryIndices;
            taskLinkDeltas[taskIndex] = entryValues;
         }

         byte itemCount;
         short[] itemNameTextIds = new short[itemCount = input.readByte()];
         short[] itemSpriteIds = new short[itemCount];

         for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
            itemNameTextIds[itemIndex] = readShort(input);
            itemSpriteIds[itemIndex] = input.readByte();
         }

         byte needLinkCount = input.readByte();
         byte stateCount;
         short[] stateSpriteIds = new short[stateCount = input.readByte()];
         byte[] stateFlags = new byte[stateCount];
         short[] stateBadgeIds = new short[stateCount];
         short[] stateLabelTextIds = new short[stateCount];
         byte[][] stateTransitionStates = new byte[stateCount][];
         byte[][] stateTransitionNeeds = new byte[stateCount][];
         short[][] stateTransitionThresholds = new short[stateCount][];

         for (int stateIndex = 0; stateIndex < stateCount; stateIndex++) {
            stateSpriteIds[stateIndex] = readShort(input);
            stateFlags[stateIndex] = input.readByte();
            stateBadgeIds[stateIndex] = readShort(input);
            stateLabelTextIds[stateIndex] = readShort(input);
            int transitionCount;
            byte[] transStates = new byte[transitionCount = input.readUnsignedByte()];
            byte[] transNeeds = new byte[transitionCount];
            short[] transThresholds = new short[transitionCount];

            for (int transitionIndex = 0; transitionIndex < transitionCount; transitionIndex++) {
               transStates[transitionIndex] = input.readByte();
               transNeeds[transitionIndex] = input.readByte();
               transThresholds[transitionIndex] = input.readShort();
            }

            stateTransitionStates[stateIndex] = transStates;
            stateTransitionNeeds[stateIndex] = transNeeds;
            stateTransitionThresholds[stateIndex] = transThresholds;
         }

         byte simCount;
         short[] simNameTextIds = new short[simCount = input.readByte()];
         byte[] simSpareBytes = new byte[simCount];
         byte[][] simAttributes = new byte[simCount][10];
         short[] simHomeLots = new short[simCount];
         byte[] simFlags = new byte[simCount];
         byte[] simPortraitIds = new byte[simCount];
         byte[] simLots = new byte[simCount];
         byte[] simHours = new byte[simCount];
         byte[][] simKnownIds = new byte[simCount][];
         byte[][] simActionList = new byte[simCount][];

         for (int simIndex = 0; simIndex < simCount; simIndex++) {
            simNameTextIds[simIndex] = readShort(input);
            simSpareBytes[simIndex] = input.readByte();

            for (int attributeIndex = 0; attributeIndex < 10; attributeIndex++) {
               simAttributes[simIndex][attributeIndex] = input.readByte();
            }

            simHomeLots[simIndex] = readShort(input);
            simFlags[simIndex] = input.readByte();
            simPortraitIds[simIndex] = input.readByte();
            simLots[simIndex] = input.readByte();
            simHours[simIndex] = input.readByte();
            byte[] knownBuf = new byte[input.readByte()];
            input.readFully(knownBuf);
            simKnownIds[simIndex] = knownBuf;
            byte[] actionBuf = new byte[input.readByte()];
            input.readFully(actionBuf);
            simActionList[simIndex] = actionBuf;
         }

         byte portraitCount;
         byte[][] portraitData = new byte[portraitCount = input.readByte()][12];

         for (int portraitIndex = 0; portraitIndex < portraitCount; portraitIndex++) {
            for (int byteIndex = 0; byteIndex < 12; byteIndex++) {
               portraitData[portraitIndex][byteIndex] = input.readByte();
            }
         }

         byte needTextCount;
         short[] needTextIds = new short[needTextCount = input.readByte()];

         for (int needTextIndex = 0; needTextIndex < needTextCount; needTextIndex++) {
            needTextIds[needTextIndex] = readShort(input);
         }

         short interactionCount;
         short[] interactionTextIds = new short[interactionCount = input.readShort()];
         short[] interactionSprites = new short[interactionCount];
         int[] interactionFlags = new int[interactionCount];
         short[] interactionAltTextIds = new short[interactionCount];
         byte[][] interactionVariants = new byte[interactionCount][];
         byte[][] interactionStateReqs = new byte[interactionCount][];
         byte[][] interactionNeedReqs = new byte[interactionCount][];
         int[][] interactionFlagReqs = new int[interactionCount][];
         short[][] interactionConditionFlags = new short[interactionCount][];
         byte[][] interactionNeedGains = new byte[interactionCount][];
         byte[][] interactionNeedLosses = new byte[interactionCount][];
         byte[][] interactionMoodIndex = new byte[interactionCount][];
         byte[][] interactionMoodGain = new byte[interactionCount][];
         short[][] interactionResultTextIds = new short[interactionCount][];

         for (int interactionIndex = 0; interactionIndex < interactionCount; interactionIndex++) {
            interactionTextIds[interactionIndex] = readShort(input);
            interactionSprites[interactionIndex] = readShort(input);
            int variantCount = input.readInt();
            interactionFlags[interactionIndex] = variantCount;
            interactionAltTextIds[interactionIndex] = readShort(input);
            byte subEntryCount;
            byte[] variantBuf = new byte[subEntryCount = input.readByte()];
            byte[] stateReqBuf = new byte[subEntryCount];
            int[] flagReqBuf = new int[subEntryCount];
            short[] conditionBuf = new short[subEntryCount];
            byte[] needGainBuf = new byte[subEntryCount];
            byte[] needLossBuf = new byte[subEntryCount];
            byte[] moodIndexBuf = new byte[subEntryCount];
            byte[] moodGainBuf = new byte[subEntryCount];
            short[] resultTextBuf = new short[subEntryCount];

            for (int subEntryIndex = 0; subEntryIndex < subEntryCount; subEntryIndex++) {
               variantBuf[subEntryIndex] = input.readByte();
               stateReqBuf[subEntryIndex] = input.readByte();
               int flagValue = input.readInt();
               conditionBuf[subEntryIndex] = input.readShort();
               needGainBuf[subEntryIndex] = input.readByte();
               needLossBuf[subEntryIndex] = input.readByte();
               moodIndexBuf[subEntryIndex] = input.readByte();
               moodGainBuf[subEntryIndex] = input.readByte();
               resultTextBuf[subEntryIndex] = input.readShort();
               flagReqBuf[subEntryIndex] = (flagValue & 1) != 0 ? ~flagValue : flagValue;
            }

            byte variantSize = input.readByte();
            Object variants = null;
            if ((variantCount & 1073741824) != 0) {
               variants = interactionVariants[26];
            } else {
               variants = new byte[variantSize];
               input.readFully((byte[])variants);
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
            interactionVariants[interactionIndex] = (byte[])variants;
         }

         byte cardCount;
         short[] visitCardTimeCosts = new short[cardCount = input.readByte()];
         byte[] visitCardMinuteLimits = new byte[cardCount];
         byte[] visitCardOtherLimits = new byte[cardCount];
         byte[] visitCardUp = new byte[cardCount];
         short[] visitCardTextA = new short[cardCount];
         byte[] visitCardDown = new byte[cardCount];
         short[] visitCardTextB = new short[cardCount];
         byte[] visitCardLeft = new byte[cardCount];
         short[] visitCardTextC = new short[cardCount];
         byte[] visitCardRight = new byte[cardCount];
         short[] visitCardTextD = new short[cardCount];

         for (int cardDataIndex = 0; cardDataIndex < cardCount; cardDataIndex++) {
            visitCardTimeCosts[cardDataIndex] = readShort(input);
            visitCardMinuteLimits[cardDataIndex] = input.readByte();
            visitCardOtherLimits[cardDataIndex] = input.readByte();
            visitCardUp[cardDataIndex] = input.readByte();
            visitCardTextA[cardDataIndex] = readShort(input);
            visitCardDown[cardDataIndex] = input.readByte();
            visitCardTextB[cardDataIndex] = readShort(input);
            visitCardLeft[cardDataIndex] = input.readByte();
            visitCardTextC[cardDataIndex] = readShort(input);
            visitCardRight[cardDataIndex] = input.readByte();
            visitCardTextD[cardDataIndex] = readShort(input);
         }

         byte cardActionCount;
         byte[] cardFlags = new byte[cardActionCount = input.readByte()];
         short[] cardLabelTextIds = new short[cardActionCount];
         short[] cardAltTextIds = new short[cardActionCount];
         short[] cardRequiredLots = new short[cardActionCount];
         short[] cardRewardItems = new short[cardActionCount];
         short[] cardRewardMoney = new short[cardActionCount];
         short[][] cardRequirementIds = new short[cardActionCount][];

         for (int cardIndex = 0; cardIndex < cardActionCount; cardIndex++) {
            cardFlags[cardIndex] = input.readByte();
            cardLabelTextIds[cardIndex] = readShort(input);
            cardAltTextIds[cardIndex] = readShort(input);
            cardRequiredLots[cardIndex] = input.readShort();
            cardRewardItems[cardIndex] = readShort(input);
            cardRewardMoney[cardIndex] = input.readShort();
            byte requirementCount;
            short[] requirementBuf = new short[requirementCount = input.readByte()];

            for (int requirementIndex = 0; requirementIndex < requirementCount; requirementIndex++) {
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
         this.lotHourIncome = lotHourIncome;
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
         this.portraitData = portraitData;
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
      } catch (IOException ioError) {
      }
   }

   public final int currentMinuteOfDay() {
      return minuteOfDay(this.gameTime);
   }

   private static int minuteOfDay(int time) {
      return time % 1440;
   }

   public final int dayOfWeek() {
      return weekdayOf(this.gameTime);
   }

   private static int weekdayOf(int time) {
      return time / 1440 % 7;
   }

   private static int startOfWeek(int time) {
      return time / 1440 * 1440;
   }

   public final int dayNumber() {
      return (this.gameTime - 840) / 1440;
   }

   public final void addMoney(int amount) {
      this.money += amount;
      if (amount < 0) {
         this.moneySpent -= amount;
      } else {
         if (this.money >= 1000) {
            this.unlockAchievement(43);
         }

         if (this.money >= 5000) {
            this.unlockAchievement(62);
         }
      }
   }

   private void resetProgress() {
      this.gameTime = 840;
      this.money = 700;
      this.moneySpent = 0;
      this.paused = false;
      this.lastCareerLevel = 0;
      this.lastPromotionDay = -1;
   }

   private void advanceTime(int elapsed) {
      this.timeAccumulator += elapsed;
      if (this.timeAccumulator > 1000) {
         int minutes = this.timeAccumulator / 1000;
         this.timeAccumulator -= minutes * 1000;
         int previousTime = this.gameTime;
         this.gameTime += minutes;
         this.canvas.gameScreen().advanceTimeUntil(previousTime, this.gameTime);
      }
   }

   public final int eventTimeCost(int eventId) {
      if (eventId == 125) {
         return 10;
      } else if (eventId == 158) {
         int lot;
         if ((lot = this.simLot(0)) != -1) {
            int hour = this.simHour(0);
            if ((this.lotHourDays(lot, hour) & 2) != 0) {
               return 15;
            }
         }

         return 30;
      } else {
         return 0;
      }
   }

   public final int careerLevel() {
      int level = this.dayNumber();

      for (int resident = 0; resident < this.residentCount(); resident++) {
         level += this.residentLevel(resident);
      }

      if (level >= 15) {
         return 2;
      } else {
         return level >= 5 ? 1 : 0;
      }
   }

   public final boolean checkCareerPromotion() {
      int level;
      if ((level = this.careerLevel()) > this.lastCareerLevel) {
         this.lastCareerLevel = (byte)level;
         return true;
      } else {
         return false;
      }
   }

   public final void markPromotionDay() {
      this.lastPromotionDay = (short)this.dayNumber();
   }

   public final int pendingPromotion() {
      int day = this.dayNumber();
      byte careerTier = this.canvas.lot.stageIndex;
      if (this.canvas.lot.stageIndex == 0) {
         if (day >= 3 && !this.hasMilestone(16)) {
            this.setMilestone(16);
            return 777;
         }
      } else if (careerTier == 1 && day - this.lastPromotionDay >= 5 && !this.hasMilestone(32)) {
         this.setMilestone(32);
         return 778;
      }

      return -1;
   }

   private int defaultOpenMinutes(int lot) {
      short minutes = 480;
      if (this.simKnownIndex(lot, 10) != -1) {
         minutes = 540;
      } else if (this.simKnownIndex(lot, 11) != -1) {
         minutes = 420;
      }

      return minutes;
   }

   public final int lotClosingTime(int sim) {
      int now = this.gameTime;
      int closing;
      if (sim == 0) {
         int needAmount = this.needValues[1];
         int factor = 65536 - FixedMath.div(needAmount, 6553600);
         closing = now + FixedMath.mul(factor, 600);
      } else {
         closing = now + 600;
      }

      int nextOpen;
      if (this.simLot(sim) != -1 && (nextOpen = this.nextOpenTime(sim, now) - 60) > now && nextOpen - now < 1440) {
         closing = Math.min(nextOpen, closing);
      }

      return closing;
   }

   public final void endOfDay() {
      int lot = this.simLot(0);
      int hour = this.simHour(0);
      int endTime = this.lotHourEnd(lot, hour);
      int now = this.currentMinuteOfDay();
      int delta;
      if ((delta = endTime - now) < 0) {
         delta += 1440;
      }

      int elapsedMs = delta * 1000;
      this.advanceTime(elapsedMs);
      this.adjustNeedIfIdle(1, -983040, 3);
      this.adjustNeedIfIdle(3, -1310720, 8);
      this.adjustNeed(5, -1638400);
      this.adjustNeed(2, this.needValue(2) < 2293760 ? 3276800 : -983040);
      if (this.simLot(0) != 0) {
         this.adjustNeedIfIdle(0, -1310720, 0);
      }

      this.celebrate();
   }

   public final void skipToTime(int targetTime) {
      int now = this.gameTime;
      int elapsedMs = (targetTime - now) * 1000;
      this.advanceTime(elapsedMs);
      this.adjustNeed(1, 6553600);
      this.adjustNeed(2, -655360);
      this.adjustNeedIfIdle(0, -1310720, 0);
      this.adjustNeedIfIdle(3, -983040, 8);
      this.celebrate();
   }

   private void refreshItemCounts() {
      if (this.itemCounts == null) {
         int count = this.canvas.lot.categoryCount();
         this.itemCounts = new byte[count];
      }

      GameCanvas.fillBytes(this.itemCounts, 0);
   }

   public final int itemCount(int item) {
      return this.itemCounts[item];
   }

   public final int ownedItemCount() {
      return this.itemCounts.length - GameCanvas.countEqualBytes(0, this.itemCounts);
   }

   public final int itemCountOf(int item) {
      return GameCanvas.nthIndexNotEqualByte(item, this.itemCounts, 0);
   }

   public final void addItem(int item, int delta) {
      int max = this.canvas.lot.categoryFlagAt(item);
      this.itemCounts[item] = (byte)Math.min(max, this.itemCounts[item] + delta);
      if (delta > 0) {
         Lot lot = this.canvas.lot;
         if ((this.canvas.lot.categoryMaskAt(item) & 2048) != 0) {
            this.unlockAchievement(27);
         }
      }
   }

   public final void buildItemMenu(short[] labels, short[] commands, int interaction) {
      short textId = this.interactionTextIds[interaction];
      GameCanvas.initList(labels, textId);
      GameCanvas.initList(commands, textId);
      Lot lot = this.canvas.lot;
      byte[] counts = this.itemCounts;

      for (int item = 0; item < counts.length; item++) {
         if (counts[item] > 0 && (lot.categoryMaskAt(item) & 64) != 0) {
            GameCanvas.addToList(labels, lot.categoryIdAt(item));
            GameCanvas.addToList(commands, interaction);
         }
      }
   }

   public final void buildUseMenu(short[] labels, short[] commands, int interaction) {
      short textId = this.interactionTextIds[interaction];
      GameCanvas.initList(labels, textId);
      GameCanvas.initList(commands, textId);
      Lot lot = this.canvas.lot;
      byte[] counts = this.itemCounts;

      for (int item = 0; item < counts.length; item++) {
         if (counts[item] > 0 && (lot.categoryMaskAt(item) & 2048) != 0) {
            GameCanvas.addToList(labels, lot.categoryIdAt(item));
            GameCanvas.addToList(commands, interaction);
         }
      }
   }

   public final boolean purchaseUpgrade(int interaction) {
      Lot lot = this.canvas.lot;
      int ingredientCount = this.canvas.lot.listSize(interaction);

      for (int checkIndex = 0; checkIndex < ingredientCount; checkIndex++) {
         int ingredient = lot.listValueAt(interaction, checkIndex);
         if (this.itemCount(ingredient) < 1) {
            return false;
         }
      }

      for (int consumeIndex = 0; consumeIndex < ingredientCount; consumeIndex++) {
         int consumedIngredient = lot.listValueAt(interaction, consumeIndex);
         this.addItem(consumedIngredient, -1);
      }

      this.lastPurchasedItem = interaction;
      return true;
   }

   public final boolean hasItemC() {
      return this.itemCount(2) > 0;
   }

   public final boolean hasItemB() {
      return this.itemCount(1) > 0;
   }

   public final boolean hasItemA() {
      return this.itemCount(0) > 0;
   }

   private void resetCareerLevels() {
      int count = this.residentCount();
      if (this.residentGrowth == null) {
         this.residentGrowth = new int[count];
      }

      GameCanvas.fillInts(this.residentGrowth, 0);
   }

   public final int careerPoints(int resident) {
      return this.residentGrowth[resident];
   }

   public final int residentCount() {
      return this.residentIds.length;
   }

   public final int residentId(int resident) {
      return this.residentIds[resident];
   }

   public final int residentBodySprite(int resident) {
      return this.residentLook[resident][0];
   }

   public final int residentHeadSprite(int resident) {
      return this.residentLook[resident][1];
   }

   public final int residentColorIndex(int resident) {
      return this.residentLook[resident][2];
   }

   public final int residentLevelValue(int resident) {
      return this.residentLevelValues[resident][this.residentLevel(resident)];
   }

   public final int residentLevel(int resident) {
      return this.residentGrowth[resident] / 65536;
   }

   public final void addCareerPoints(int resident) {
      int[] growth = this.residentGrowth;
      int level;
      if ((level = this.residentLevel(resident)) < 5) {
         growth[resident] += this.residentGrowthSteps[resident][level];
         growth[resident] = Math.min(growth[resident], 65536 * (level + 1));
      }
   }

   private void resetSelection() {
      this.simLots[0] = -1;
      this.simHours[0] = 0;
      this.lastVisitDay = -1;
      this.tickCount = 0;
      this.idleTicks = 0;
      if (this.lotLastHour == null) {
         this.lotLastHour = new byte[this.lotNameTextIds.length];
      }

      GameCanvas.fillBytes(this.lotLastHour, 0);
      this.suggestedLot = -1;
      this.suggestionTimer = 1440000;
   }

   private void updateSuggestion(int elapsed) {
      this.suggestionTimer += elapsed;
      if (this.suggestionTimer > 1440000) {
         this.suggestionTimer = 0;
         int range = this.lotNameTextIds.length;
         if (!this.canvas.gameStarted) {
            range--;
         }

         this.suggestedLot = (byte)(this.canvas.randomRange(0, range) - 1);
      }
   }

   public final void selectLocation(int lot, int hour) {
      byte previousLot = this.simLots[0];
      byte previousHour = this.simHours[0];
      this.simLots[0] = (byte)lot;
      this.simHours[0] = (byte)hour;
      if (lot != -1) {
         this.lotLastHour[lot] = (byte)hour;
      }

      this.tickCount = 0;
      this.idleTicks = 0;
      if (lot != -1) {
         if (previousLot != lot) {
            this.lastVisitDay = (byte)this.dayOfWeek();
         }

         GameCanvas canvas = this.canvas;
         GameScreen screen = this.canvas.gameScreen();
         int titleTextId = previousLot == lot ? (hour > previousHour ? 635 : 637) : 634;
         int weekday = weekdayOf(this.nextOpenTime(0, this.gameTime));
         int weekdayTextId;
         if (weekdayOf(this.gameTime + 1440) == weekday) {
            weekdayTextId = 660;
         } else {
            weekdayTextId = WEEKDAY_TEXT_IDS[weekday];
         }

         SdkString title = canvas.getString(weekdayTextId);
         SdkString body = canvas.beginString();
         canvas.appendTime(this.lotHourStarts[lot][hour]);
         SdkString subtitle = canvas.getString(canvas.lot.spriteIdAt(this.lotOccupantTypes[lot]));
         canvas.formatStringWith3(-11, titleTextId, title, body, subtitle);
         byte optionFlags = 0;
         if (previousLot == lot && hour > previousHour) {
            optionFlags = 4;
         } else if (lot != -1) {
            optionFlags = 3;
         }

         int option = this.lotHourInteraction(lot, hour);
         screen.openDialog(-11, option, optionFlags);
      }
   }

   public final int tick() {
      int lot = this.simLot(0);
      int hour = this.simHour(0);
      int income = this.lotHourIncome(lot, hour);
      this.addMoney(income);
      this.tickCount++;
      if (this.tickCount % 3 == 2 && this.idleTicks > 0) {
         this.idleTicks--;
      }

      return income;
   }

   public final void idleTick() {
      this.idleTicks++;
      if (this.idleTicks >= 3) {
         this.selectLocation(-1, 0);
      }
   }

   public final int statValue(int slot) {
      switch (slot) {
         case 0:
         default:
            return this.residentLevel(0);
         case 1:
            return this.residentLevel(2);
         case 2:
            return this.tickCount;
         case 3:
            int working = 0;

            for (int sim = 1; sim < this.simCount; sim++) {
               byte state = this.simStates[sim];
               if ((this.stateFlagsOf(state) & 16) != 0) {
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

   public static int statTextId(int slot) {
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

   public final boolean canEnterLot(int lot, int hour) {
      byte[] requirements;
      byte firstStat = (requirements = this.lotHourRequirements[lot][hour])[0];
      byte firstMin = requirements[1];
      if (firstStat != -1 && this.statValue(firstStat) < firstMin) {
         return false;
      } else {
         byte secondStat = requirements[2];
         byte secondMin = requirements[3];
         if (secondStat != -1 && this.statValue(secondStat) < secondMin) {
            return false;
         } else {
            int kind = this.lotKind(lot);
            byte state = this.simStates[kind];
            return (this.stateFlagsOf(state) & 2) == 0;
         }
      }
   }

   public final boolean canAdvanceHour() {
      int lot = this.simLot(0);
      int hour;
      return (hour = this.simHour(0)) < this.lotHourCount(lot) - 1 && this.canEnterLot(lot, hour + 1) && this.tickCount >= 10;
   }

   public final int pickActionFor(Entity entity) {
      byte currentLot = this.simLots[0];
      int lot = -1;
      if (entity != null && entity.type != 77) {
         lot = GameCanvas.indexOfShort(entity.type, this.lotOccupantTypes);
      } else {
         int minute;
         if ((minute = this.currentMinuteOfDay()) >= 450 && minute <= 1110 && this.suggestedLot != currentLot) {
            lot = this.suggestedLot;
         }
      }

      return lot;
   }

   public final int lastHourIndex(int lot) {
      return lot == -1 ? 0 : Math.max(this.lotLastHour[lot] - 1, 0);
   }

   private static boolean isTimeInRange(int time, int start, int end) {
      start = (start + 1440) % 1440;
      end = (end + 1440) % 1440;
      return start < end ? time >= start && time <= end : time >= start || time <= end;
   }

   public final int lotStateNow(int lot) {
      return this.lotState(lot, this.gameTime);
   }

   public final int lotState(int lot, int time) {
      byte state = 0;
      int weekday = weekdayOf(time);
      int minute = minuteOfDay(time);
      int endMinutes;
      int startMinutes = (endMinutes = this.defaultOpenMinutes(lot)) - 600;
      byte activeLot;
      if ((activeLot = this.simLots[lot]) == -1) {
         state = 8;
      } else {
         short openTime;
         label51: {
            byte hour = this.simHours[lot];
            openTime = this.lotHourStarts[activeLot][hour];
            if (lot != 0 || weekday != this.lastVisitDay) {
               if (lot == 0) {
                  this.lastVisitDay = -1;
               }

               int dayBit = WEEKDAY_BITS[weekday];
               if ((this.lotHourDayBits[activeLot][hour] & dayBit) != 0) {
                  short closeTime = this.lotHourEnds[activeLot][hour];
                  if (minute >= openTime - 30) {
                     if (minute < openTime) {
                        state = 1;
                        break label51;
                     }

                     if (minute < openTime + 60) {
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

         startMinutes = openTime - 660;
         endMinutes = openTime - 60;
      }

      if (isTimeInRange(minute, startMinutes, endMinutes)) {
         state |= 16;
      }

      return state;
   }

   private int nextOpenTime(int sim, int time) {
      int lot = this.simLot(sim);
      int hour = this.simHour(sim);
      int dayBits = this.lotHourDays(lot, hour);
      int startMinutes = this.lotHourStart(lot, hour);
      int minute = minuteOfDay(time);
      int weekday;
      int candidateDay = weekday = weekdayOf(time);

      for (int offset = 0; offset < 7; offset++) {
         candidateDay = (weekday + offset) % 7;
         if ((dayBits & WEEKDAY_BITS[candidateDay]) != 0 && (sim != 0 || candidateDay != this.lastVisitDay) && (candidateDay != weekday || minute < startMinutes)) {
            break;
         }
      }

      int openTime = startOfWeek(this.gameTime) + startMinutes;
      if (candidateDay != weekday) {
         openTime += (candidateDay - weekday + 7) % 7 * 1440;
      }

      return openTime;
   }

   private boolean isLotOccupant(Entity entity, int sim) {
      byte lot;
      if ((lot = this.simLots[sim]) == -1) {
         return false;
      } else if (entity != null) {
         int type = this.lotOccupantType(lot);
         return entity.type == type;
      } else {
         return false;
      }
   }

   public final boolean canInteractAt(Entity entity, int sim) {
      return entity != null && !this.isLotOccupant(entity, sim) ? false : (this.lotStateNow(sim) & 3) != 0;
   }

   public final int lotHourCount(int lot) {
      return this.lotHourInteractions[lot].length;
   }

   public final int lotNameText(int lot) {
      return lot == -1 ? 667 : this.lotNameTextIds[lot];
   }

   public final int lotSubtitleText(int lot) {
      return this.lotSubtitleTextIds[lot];
   }

   public final int lotOccupantType(int lot) {
      return this.lotOccupantTypes[lot];
   }

   public final int lotKind(int lot) {
      return this.lotKinds[lot];
   }

   public final int currentLotSprite() {
      byte lot = this.simLots[0];
      return this.canvas.randomPercent() < 50 ? this.lotSpritesLow[lot] : this.lotSpritesHigh[lot];
   }

   public final int lotExtraSprite(int lot) {
      return this.lotExtraSprites[lot];
   }

   public final int lotHourInteraction(int lot, int hour) {
      return this.lotHourInteractions[lot][hour];
   }

   public final int lotHourIncome(int lot, int hour) {
      return this.lotHourIncome[lot][hour];
   }

   public final int lotHourDays(int lot, int hour) {
      return this.lotHourDayBits[lot][hour];
   }

   public final int lotHourStart(int lot, int hour) {
      return this.lotHourStarts[lot][hour];
   }

   public final int lotHourEnd(int lot, int hour) {
      return this.lotHourEnds[lot][hour];
   }

   public final byte[] lotHourRequirement(int lot, int hour) {
      return this.lotHourRequirements[lot][hour];
   }

   public final void setSelectedResident(int resident) {
      this.selectedResident = (byte)resident;
   }

   public final int residentLabelCount() {
      return this.residentLabelTextIds.length;
   }

   public final short residentLabelText(int resident) {
      return resident == 1 && this.simAttribute(0, 0) == 1 ? 534 : this.residentLabelTextIds[resident];
   }

   public final short residentInfoText(int resident) {
      return this.residentInfoTextIds[resident];
   }

   public final int selectedResidentSpriteA() {
      return this.residentSpritesA[this.selectedResident];
   }

   private int selectedResidentSpriteB() {
      return this.residentSpritesB[this.selectedResident];
   }

   public final int selectedResidentFrame() {
      return GameCanvas.countAllFlags(this.selectedResidentSpriteB(), this.achievementFlags);
   }

   public final int selectedResidentAnimFrame(int frameStep) {
      return GameCanvas.nthIndexWithFlag(frameStep, this.achievementFlags, this.selectedResidentSpriteB());
   }

   private int selectedResidentMask() {
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

   public final boolean unlockSecondMilestone() {
      if (this.hasMilestone(1) && !this.hasMilestone(2)) {
         this.setMilestone(2);
         return true;
      } else {
         return false;
      }
   }

   private void resetAchievementState() {
      this.pendingAchievementId = -1;
      this.randomSeed = (short)this.canvas.randomRange(0, this.achievementFlags.length - 1);
      this.achievementTimer = 115000;
      if (this.achievementSlots == null) {
         this.achievementSlots = new byte[4];
         this.achievementTimes = new int[this.achievementTriggerIds.length];
         this.recentTaskTimes = new int[10];
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

   public final boolean hasMilestone(int flag) {
      return (this.milestoneFlags & flag) != 0;
   }

   private void setMilestone(int flag) {
      this.milestoneFlags |= flag;
   }

   private void updateAchievementTimers(int elapsed) {
      this.achievementTimer += elapsed;
      if (this.pendingAchievementId != -1) {
         if (this.achievementTimer > 240000) {
            this.canvas.gameScreen().showRelationshipDialog(this.pendingAchievementId);
            this.pendingAchievementId = -1;
            this.achievementTimer = 0;
         }
      } else if (this.achievementTimer > 120000) {
         this.pendingAchievementId = (byte)this.pickRandomAchievement();
         this.achievementTimer = 0;
         this.canvas.gameScreen().requestSleep();
      }

      int now = this.gameTime;

      for (int index = 0; index < this.achievementTimes.length; index++) {
         int timestamp;
         if ((timestamp = this.achievementTimes[index]) != -1 && now - timestamp > 4320) {
            this.achievementTimes[index] = now;
            this.unlockAchievement(this.achievementTriggerIds[index]);
         }
      }
   }

   private int pickRandomAchievement() {
      int count = this.achievementFlags.length;
      int event = -1;
      int seed = this.randomSeed;

      for (int attempt = 0; attempt < count; attempt++) {
         while ((seed = (1543 * seed + 3571) % 257) >= count) {
         }

         short flags;
         if (this.achievementSlotOf(seed) == -1 && ((flags = this.achievementFlags[seed]) & 8) == 0 && this.isAchievementAvailable(seed) && (!this.canvas.hasVisitedFlag(seed) || this.canvas.randomPercent() >= 70)) {
            event = seed;
            this.randomSeed = (short)seed;
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

      int index;
      if ((index = GameCanvas.indexOfByte(event, this.achievementTriggerIds)) != -1 && this.achievementTimes[index] != -1) {
         this.achievementTimes[index] = this.gameTime;
      }

      this.canvas.addFlag(event);
      return event;
   }

   public final void assignAchievement() {
      int slot;
      if ((slot = this.achievementSlotOf(-1)) != -1 && this.pendingAchievementId != -1) {
         this.achievementSlots[slot] = this.pendingAchievementId;
         this.pendingAchievementId = -1;
         this.achievementTimer = 0;
         this.canvas.gameScreen().playSlotAnimator(slot);
      }
   }

   public final boolean hasOpenAchievementSlot() {
      return GameCanvas.indexOfByte(-1, this.achievementSlots) != -1;
   }

   private boolean isAchievementAvailable(int event) {
      Lot lot = this.canvas.lot;
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
            if (this.itemCount(2) == 0) {
               return true;
            }

            return false;
         case 3:
            if (this.itemCount(3) == 0) {
               return true;
            }

            return false;
         case 4:
            if (this.itemCount(1) == 0) {
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
            int flagMask = event == 12 ? 16 : (event == 13 ? 4 : 8);

            for (int sim = 1; sim < this.simCount; sim++) {
               if ((this.stateFlagsOf(this.simStates[sim]) & flagMask) == 0) {
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
            if (this.simLot(0) == -1) {
               return true;
            }

            return false;
         case 27:
            for (int item = 0; item < this.itemCounts.length; item++) {
               if (this.itemCounts[item] == 0 && (lot.categoryMaskAt(item) & 2048) != 0) {
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
            return this.simLot(0) != -1 && this.simHour(0) < this.lotHourCount(this.simLot(0));
      }
   }

   private int achievementSlotOf(int event) {
      return GameCanvas.indexOfByte(event, this.achievementSlots);
   }

   public final int achievementSlotValue(int slot) {
      return this.achievementSlots[slot];
   }

   public final int achievementText(int index) {
      return index >= 0 && index < this.achievementTextIds.length ? this.achievementTextIds[index] : 548;
   }

   public final int achievementCount() {
      return this.achievementTextIds.length;
   }

   private int achievementFlagAt(int index) {
      return this.achievementFlags[index];
   }

   public final void unlockAchievement(int event) {
      GameCanvas canvas = this.canvas;
      GameScreen screen = this.canvas.gameScreen();
      boolean handled = false;
      int slot;
      if ((slot = this.achievementSlotOf(event)) != -1) {
         handled = true;
         this.achievementSlots[slot] = -1;
      } else if (this.pendingAchievementId == event) {
         handled = true;
         this.pendingAchievementId = -1;
         this.achievementTimer = 0;
      }

      byte milestoneFlag = 0;
      switch (event) {
         case 0:
            milestoneFlag = 4;
            break;
         case 1:
            milestoneFlag = 8;
      }

      this.setMilestone(milestoneFlag);
      int bitIndex = 0;
      int unlockedCount = 0;
      int total = this.achievementCount();
      int residentMask = this.selectedResidentSpriteB();

      for (int index = 0; index < total; index++) {
         if ((this.achievementFlagAt(index) & residentMask) != 0) {
            int bit = 1 << bitIndex;
            if (event == index && (this.usedTaskBits & bit) == 0) {
               this.usedTaskBits |= bit;
               handled = true;
            }

            if ((this.usedTaskBits & bit) != 0) {
               unlockedCount++;
            }

            bitIndex++;
         }
      }

      if (handled) {
         canvas.addVisitedFlag(event);
         screen.playSlotAnimator2(event, slot);
      }

      if (unlockedCount == bitIndex) {
         this.setMilestone(1);
      }

      boolean multiplayer = canvas.gameStarted;
      if (!canvas.gameStarted) {
         int achievementTotal = this.achievementTextIds.length;
         if (canvas.visitedFlagCount() == achievementTotal) {
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

   public final void onItemPlaced(int item) {
      Lot lot = this.canvas.lot;
      int type;
      if ((type = this.canvas.lot.objectKindAt(item)) == 3) {
         this.unlockAchievement(6);
      } else if (type == 9) {
         if (item != 42) {
            this.unlockAchievement(41);
         }
      } else if (type == 19) {
         this.unlockAchievement(5);
      } else if (type == 22 && item != 94) {
         this.unlockAchievement(42);
      }

      if (lot.allItemsPlaced()) {
         this.unlockAchievement(64);
      }

      this.unlockAchievement(21);
   }

   public final void onInteractionResult(int result) {
      this.unlockAchievement(21);
      if (result == 2) {
         this.unlockAchievement(2);
      }

      if (result == 3) {
         this.unlockAchievement(3);
      }

      if (result == 1) {
         this.unlockAchievement(4);
      }
   }

   public final void onRepeatAction(int amount) {
      this.otherEventCount = (short)(this.otherEventCount + amount);
      if (this.otherEventCount > 30) {
         this.unlockAchievement(45);
      }
   }

   public final boolean canActToday() {
      int[] times = this.recentTaskTimes;
      int now = this.gameTime;
      int count = 0;
      int freeSlot = -1;

      for (int index = 0; index < 10; index++) {
         int time = times[index];
         if (now - time > 1440) {
            times[index] = -1;
            time = -1;
         }

         if (time == -1 && freeSlot == -1) {
            freeSlot = index;
            times[index] = now;
            time = now;
         }

         if (time != -1) {
            count++;
         }
      }

      if (freeSlot != -1 && count < 10) {
         if (count >= 8) {
            this.unlockAchievement(61);
         }

         this.unlockAchievement(53);
         return false;
      } else {
         return true;
      }
   }

   public final boolean hasUsedTask(int index) {
      return (this.usedTaskBits & 1 << index) != 0;
   }

   public final boolean hasVisitedItem(int item) {
      int lotIndex;
      return (lotIndex = this.canvas.lot.indexOfKind(item)) != -1 ? (this.visitedLotBits & 1 << lotIndex) != 0 : false;
   }

   public final void markItemVisited(int item) {
      int lotIndex;
      if ((lotIndex = this.canvas.lot.indexOfKind(item)) != -1) {
         this.visitedLotBits |= 1 << lotIndex;
      }
   }

   public final short needId(int need) {
      return this.needIds[need];
   }

   public final short needLabelText(int need) {
      return this.needLabelTextIds[need];
   }

   public final short needHintText(int need) {
      return this.needHintTextIds[need];
   }

   public final int needValue(int need) {
      return this.needValues[need];
   }

   private void resetNeedValues() {
      if (this.needValues == null) {
         this.needValues = new int[this.needCount];
         this.needDecay = new int[this.needCount];
         this.needModifiers = new int[this.needCount];
         this.needOffsets = new int[this.needCount];
      }

      System.arraycopy(this.needInitialValues, 0, this.needValues, 0, this.needCount);
      System.arraycopy(this.needDecayRates, 0, this.needDecay, 0, this.needCount);
      GameCanvas.fillInts(this.needModifiers, 0);
      GameCanvas.fillInts(this.needOffsets, 65536);
      this.needTimer = 0;
   }

   private void updateNeeds(int elapsed) {
      this.needTimer += elapsed;
      if (this.needTimer > 500) {
         this.needTimer -= 500;
         int[] decay = this.needDecay;
         int[] offsets = this.needOffsets;
         int[] modifiers = this.needModifiers;

         for (int need = 0; need < this.needCount; need++) {
            int amount = FixedMath.mul(offsets[need], decay[need]);
            this.adjustNeed(need, amount + modifiers[need] >> 1);
         }
      }
   }

   public final void adjustNeed(int need, int amount) {
      int[] values = this.needValues;
      this.needValues[need] = this.needValues[need] + amount;
      values[need] = FixedMath.clamp(values[need], 0, 6553600);
   }

   private void adjustNeedIfIdle(int need, int amount, int task) {
      if (this.activeTaskSlotOf(task) == -1) {
         this.adjustNeed(need, amount);
      }
   }

   public final void setNeedModifier(int need, int modifier) {
      this.needModifiers[need] = modifier;
   }

   public final int moodFlags() {
      short flags = 0;
      int[] values = this.needValues;
      short[] moodFlagBits = this.needMoodFlags;
      int[] thresholds = this.needThresholds;

      for (int need = 0; need < values.length; need++) {
         if (values[need] < thresholds[need]) {
            flags |= moodFlagBits[need];
         }
      }

      return flags;
   }

   private void resetActiveTasks() {
      if (this.activeTasks == null) {
         this.activeTasks = new short[6];
         this.taskTimers = new int[6];
      }

      GameCanvas.fillShorts(this.activeTasks, -1);
      GameCanvas.fillInts(this.taskTimers, 0);
   }

   private void updateActiveTasks(int elapsed) {
      GameCanvas canvas = this.canvas;
      short[] tasks = this.activeTasks;
      int[] timers = this.taskTimers;
      byte[] flags = this.taskFlags;
      int[] durations = this.taskDurations;
      byte[] followUps = this.taskFollowUps;

      for (int slot = 0; slot < 6; slot++) {
         short task;
         if ((task = tasks[slot]) != -1) {
            if ((flags[task] & 2) == 0 && !this.canRunTask(task)) {
               this.stopTask(task);
            } else {
               int limit = durations[task] * 1000;
               timers[slot] += elapsed;
               if (timers[slot] > limit) {
                  timers[slot] = limit;
                  if (followUps[task] != -1) {
                     this.setActiveTask(slot, followUps[task]);
                  } else if (!this.canRunTask(task)) {
                     this.stopTask(task);
                  } else {
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
                           GameScreen screen;
                           if (!(screen = canvas.gameScreen()).isNewNeighborhood()) {
                              Sim sim = screen.activePlayer;
                              if (!screen.activePlayer.isFree()) {
                                 sim.cancelAction();
                              }

                              sim.queueAction(4, null, 0, 0);
                           }

                           this.celebrate();
                           return;
                     }
                  }
               }
            }
         }
      }

      int count = this.taskCount;

      for (int taskIndex = 0; taskIndex < count; taskIndex++) {
         int chosenTask = taskIndex;
         if ((flags[taskIndex] & 8) == 0 && this.canRunTask(taskIndex)) {
            if (taskIndex == 3 && (this.canvas.gameScreen().activePlayer.actionPhase == 50 || this.activeTaskSlotOf(6) != -1)) {
               chosenTask = 6;
            }

            int activeSlot;
            if ((activeSlot = this.activeTaskSlotOf(chosenTask)) == -1) {
               this.startTask(chosenTask);
               this.celebrate();
               return;
            }

            if (chosenTask == 6) {
               this.taskTimers[activeSlot] = 0;
            }
         }
      }
   }

   private void applyMoodDeltas() {
      int[] deltas = this.moodDeltas;
      GameCanvas.fillInts(this.moodDeltas, 0);

      for (int slot = 0; slot < 6; slot++) {
         short task;
         if ((task = this.activeTasks[slot]) != -1) {
            byte[] indices = this.taskMoodIndices[task];
            byte[] values = this.taskMoodDeltas[task];
            int count = indices.length;

            for (int slotIndex = 0; slotIndex < count; slotIndex++) {
               deltas[indices[slotIndex]] = deltas[indices[slotIndex]] + (values[slotIndex] << 16);
            }
         }
      }

      for (int clampIndex = 0; clampIndex < 2; clampIndex++) {
         deltas[clampIndex] = FixedMath.clamp(deltas[clampIndex], -6553600, 6553600);
      }

      this.updateMood();
   }

   private void setActiveTask(int slot, int task) {
      short previousTask = this.activeTasks[slot];
      this.activeTasks[slot] = (short)task;
      this.taskTimers[slot] = 0;
      Sim sim;
      (sim = this.canvas.gameScreen().activePlayer).clearLocomotionState(previousTask);
      sim.setLocomotionState(task);
      byte[][] linkIndices = this.taskLinkIndices;
      int[][] linkDeltas = this.taskLinkDeltas;
      int[] offsets = this.needOffsets;
      if (previousTask != -1) {
         for (int oldLinkIndex = 0; oldLinkIndex < linkIndices[previousTask].length; oldLinkIndex++) {
            offsets[linkIndices[previousTask][oldLinkIndex]] = offsets[linkIndices[previousTask][oldLinkIndex]] + linkDeltas[previousTask][oldLinkIndex];
         }
      }

      if (task != -1) {
         for (int newLinkIndex = 0; newLinkIndex < linkIndices[task].length; newLinkIndex++) {
            offsets[linkIndices[task][newLinkIndex]] = offsets[linkIndices[task][newLinkIndex]] - linkDeltas[task][newLinkIndex];
         }

         if (task == 9) {
            this.stopTask(8);
         } else if (task == 6) {
            this.stopTask(3);
            this.stopTask(7);
         } else if (task == 3) {
            this.stopTask(6);
            this.stopTask(7);
         }

         GameScreen screen = this.canvas.gameScreen();
         if (task == 2) {
            screen.chooseDialogOptionAt(801, 401);
         } else if (task == 1) {
            screen.showNeighborhoodIntro((byte)10);
         } else if (task == 4) {
            screen.showNeighborhoodIntro((byte)11);
         } else if (task == 5) {
            screen.showNeighborhoodIntro((byte)12);
         } else if (task == 10) {
            screen.showNeighborhoodIntro((byte)13);
         } else if (task == 9) {
            screen.showNeighborhoodIntro((byte)14);
         } else if (task == 11) {
            screen.showNeighborhoodIntro((byte)15);
         }
      }

      this.applyMoodDeltas();
   }

   private void startTask(int task) {
      byte achievement;
      if ((achievement = this.taskAchievements[task]) != -1) {
         this.achievementTimes[achievement] = -1;
      }

      int slot = GameCanvas.indexOfShort(-1, this.activeTasks);
      this.setActiveTask(slot, task);
   }

   private void stopTask(int task) {
      byte achievement;
      if ((achievement = this.taskAchievements[task]) != -1) {
         this.achievementTimes[achievement] = this.gameTime;
      }

      int slot;
      if ((slot = GameCanvas.indexOfShort(task, this.activeTasks)) != -1) {
         this.setActiveTask(slot, -1);
      }
   }

   private boolean canRunTask(int task) {
      byte followUp;
      if ((followUp = this.taskFollowUps[task]) != -1 && this.activeTaskSlotOf(followUp) != -1) {
         return false;
      } else {
         int[] values = this.needValues;
         byte[] indices = this.taskNeedIndices[task];
         int[] deltas = this.taskNeedDeltas[task];
         int count;
         if ((count = indices.length) == 0) {
            return true;
         } else {
            for (int index = 0; index < count; index++) {
               byte need = indices[index];
               int delta = deltas[index];
               int value = values[need];
               if (delta > 0 && value > delta) {
                  return true;
               }

               if (delta < 0 && value < -delta) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public final int activeTaskSlotOf(int task) {
      short[] tasks = this.activeTasks;

      for (int slot = 0; slot < 6; slot++) {
         if (tasks[slot] == task) {
            return slot;
         }
      }

      return -1;
   }

   public final int activeTaskAt(int slot) {
      return this.activeTasks[slot];
   }

   public final int taskId(int task) {
      return this.taskIds[task];
   }

   public final int taskFlagsOf(int task) {
      return this.taskFlags[task];
   }

   public static int initialScreenId() {
      return 17;
   }

   public final short itemNameText(int item) {
      return this.itemNameTextIds[item];
   }

   private void resetMoodDeltas() {
      if (this.moodDeltas == null) {
         this.moodDeltas = new int[4];
      }

      GameCanvas.fillInts(this.moodDeltas, 1638400);
   }

   private void updateMood() {
      int[] deltas = this.moodDeltas;
      int highest = Integer.MIN_VALUE;
      int lowestIndex = -1;
      int lowest = Integer.MAX_VALUE;

      for (int index = 0; index < 4; index++) {
         if (deltas[index] > highest) {
            highest = deltas[index];
         }

         if (deltas[index] < lowest) {
            lowestIndex = index;
            lowest = deltas[index];
         }
      }

      byte mood = 3;
      int value = highest;
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
         } else {
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
      } else if (highest > 655360) {
         if (highest > 4915200) {
            mood = 0;
         } else if (highest > 2621440) {
            mood = 1;
         } else {
            mood = 2;
         }

         value = highest;
      }

      this.moodIndex = mood;
      this.moodValue = value;
   }

   public final int moodTextId() {
      return MOOD_TEXT_IDS[this.moodIndex];
   }

   private void resetSimStates() {
      int count = this.simCount;
      if (this.simStates == null) {
         byte[] states = new byte[count];
         short[] milestones = new short[count];
         int[][] needs = new int[count][];
         int[][] needDecay = new int[count][];
         short[][] history = new short[count][];
         byte[][] repeatCounts = new byte[count][];

         for (int index = 1; index < count; index++) {
            needs[index] = new int[this.simNeedCount];
            needDecay[index] = new int[this.needTextIds.length];
            history[index] = new short[10];
            repeatCounts[index] = new byte[this.simKnownIds[index].length];
         }

         this.simStates = states;
         this.simMilestones = milestones;
         this.simNeeds = needs;
         this.simNeedDecay = needDecay;
         this.simActionHistory = history;
         this.simRepeatCounts = repeatCounts;
      }

      for (int sim = 1; sim < count; sim++) {
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

   private void updateSims(int elapsed) {
      if (this.watchedSimTimer < 10000) {
         this.watchedSimTimer = this.watchedSimTimer + this.canvas.gameScreen().lastTickDuration;
         if (this.watchedSimTimer >= 10000) {
            this.watchedSim = -1;
         }
      }

      this.simTickTimer += elapsed;
      if (this.simTickTimer > 3000) {
         this.simTickTimer = 0;
      }

      int count = this.simCount;

      for (int sim = 1; sim < count; sim++) {
         this.checkStateTransition(sim);
         if (this.simTickTimer == 0) {
            int previousNeed = this.dominantNeed(sim);
            this.decaySimNeeds(sim, 327680);
            int currentNeed = this.dominantNeed(sim);
            if (this.watchedSim == sim && this.watchedSimTimer < 10000 && previousNeed != currentNeed) {
               short textId = this.needTextIds[currentNeed];
               int nameTextId = this.simNameText(sim);
               this.canvas.formatString1(-12, textId, nameTextId);
               this.canvas.gameScreen().showNotification(-12, -1);
            }
         }
      }
   }

   public final void watchSim(int sim) {
      this.watchedSim = sim;
      this.watchedSimTimer = 0;
   }

   public final boolean isWatchMode() {
      return this.watchedSimTimer < 10000;
   }

   private void decaySimNeeds(int sim, int amount) {
      int[] decay = this.simNeedDecay[sim];

      for (int need = 0; need < decay.length; need++) {
         decay[need] -= amount;
         if (decay[need] < 0) {
            decay[need] = 0;
         }
      }
   }

   private boolean advanceSimState(int sim) {
      if (this.simStates[sim] == 0) {
         byte state = 1;
         if (this.simNeeds[sim][0] < 0) {
            state = 2;
         }

         this.setSimState(sim, state);
         return true;
      } else {
         return false;
      }
   }

   public final void moveSim(int sim, int dx, int dy) {
      int[] needs;
      int needX = (needs = this.simNeeds[sim])[0] + (dx << 16);
      int needY = needs[1] + (dy << 16);
      needs[0] = FixedMath.clamp(needX, -1966080, 1966080);
      needs[1] = FixedMath.clamp(needY, -327680, 1966080);
   }

   private boolean checkStateTransition(int sim) {
      byte state = this.simStates[sim];
      int[] needs = this.simNeeds[sim];
      byte[] nextStates = this.stateTransitionStates[state];
      byte[] needIndices = this.stateTransitionNeeds[state];
      short[] thresholds = this.stateTransitionThresholds[state];
      int count = nextStates.length;

      for (int index = 0; index < count; index++) {
         boolean matched = true;
         byte need = needIndices[index];
         short threshold = thresholds[index];
         int value = needs[need];
         boolean negative = threshold < 0;
         int limit = threshold << 16;
         if (negative && value > limit || !negative && value < limit) {
            matched = false;
         }

         if (matched) {
            byte nextState;
            if ((nextState = nextStates[index]) == 1 && needs[0] < 0) {
               nextState = 2;
            }

            this.setSimState(sim, nextState);
            return true;
         }
      }

      return false;
   }

   private void setSimState(int sim, int state) {
      byte[] states = this.simStates;
      byte previousState = this.simStates[sim];
      states[sim] = (byte)state;
      GameCanvas.fillInts(this.simNeeds[sim], 0);
      if (previousState == 0) {
         this.unlockAchievement(11);
      }

      if (state == 12) {
         this.unlockAchievement(65);
      }

      if ((this.stateFlagsOf(previousState) & 16) == 0 && (this.stateFlagsOf(state) & 16) != 0) {
         this.unlockAchievement(12);
      }

      if ((this.stateFlagsOf(previousState) & 8) == 0 && (this.stateFlagsOf(state) & 8) != 0) {
         this.unlockAchievement(14);
      }

      if ((this.stateFlagsOf(previousState) & 4) == 0 && (this.stateFlagsOf(state) & 4) != 0) {
         this.unlockAchievement(13);
      }

      if (GameCanvas.indexOfByteFrom(0, states, 1) == -1) {
         this.unlockAchievement(50);
      }

      int relaxingCount = 0;
      boolean allBusy = true;
      boolean allSleeping = true;

      for (int otherSim = 1; otherSim < states.length; otherSim++) {
         byte simState = states[otherSim];
         allBusy = allBusy && (this.stateFlags[simState] & 1) != 0;
         allSleeping = allSleeping && (this.stateFlags[simState] & 2) != 0;
         if ((this.stateFlags[simState] & 4) != 0) {
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

      if (state == 12) {
         this.canvas.gameScreen().openDialogPage(1, -1, sim);
      } else {
         short labelTextId = this.stateLabelTextIds[state];
         short nameTextId = this.simNameTextIds[sim];
         this.canvas.formatString1(-11, labelTextId, nameTextId);
         this.canvas.gameScreen().chooseDialogOptionAt(-11, this.stateSprite(state));
      }
   }

   private boolean handleSpecialInteraction(int sim, int interaction, int flags) {
      GameScreen screen = this.canvas.gameScreen();
      byte nextState = -1;
      if (interaction == 85) {
         nextState = 11;
      } else if (interaction == 86) {
         nextState = 12;
      } else if (interaction == 96) {
         nextState = 13;
      } else if (interaction == 95) {
         nextState = 14;
      } else if (interaction == 97) {
         this.addSimMilestoneCount(sim, 2);
         screen.chooseDialogOptionAt(769, 768);
      } else if (interaction == 98) {
         this.clearSimMilestone(sim, 2);
         screen.chooseDialogOptionAt(770, 765);
      } else if (interaction == 99) {
         if ((flags & 4096) != 0) {
            screen.chooseDialogOptionAt(636, this.simNameText(sim));
         } else {
            byte lot = this.simLots[0];
            byte hour;
            if ((hour = this.simHours[0]) < this.lotHourDayBits[lot].length - 1) {
               screen.selectFriend(lot, hour + 1, false);
            }
         }
      } else if (interaction == 100) {
         screen.confirmRequest();
      }

      if (nextState != -1) {
         this.setSimState(sim, nextState);
         return true;
      } else {
         return false;
      }
   }

   public static int reactionText(int index) {
      return REACTION_TEXT_IDS[index & 15];
   }

   public final void resolveInteraction(int[] out, int sim, int otherSim, int interaction) {
      GameScreen screen = this.canvas.gameScreen();
      int resultFlags = 0;
      short resultCode = -1;
      short animationId = -1;
      short conditionFlags = 0;
      boolean interrupt = false;
      int interactionFlags = this.interactionFlags[interaction];
      if (otherSim == 0) {
         if ((interactionFlags & 524288) == 0) {
            if (interaction == 19) {
               resultFlags = 35;
               resultCode = 11;
            } else {
               resultFlags = 19;
               resultCode = 10;
            }
         }

         this.addSimMilestoneCount(sim, 1);
         this.addSimMilestoneCount(0, 1);
         if (this.advanceSimState(sim)) {
            interrupt = true;
         }
      } else if (sim != 0) {
         if ((interactionFlags & 524288) == 0) {
            resultFlags = 20;
            resultCode = 10;
         }
      } else {
         int variant;
         if ((variant = this.bestInteractionFor(otherSim, interaction)) == -1) {
            if ((interactionFlags & 2097152) != 0) {
               resultCode = -1;
            } else {
               resultCode = -1;
            }
         } else {
            boolean recalled = false;
            byte recalledInteraction;
            if ((recalledInteraction = this.interactionNeedReqs[interaction][variant]) != -1) {
               recalled = this.simRemember(otherSim, recalledInteraction);
            }

            short[] history = this.simActionHistory[otherSim];

            for (int position = 9; position > 0; position--) {
               history[position] = history[position - 1];
            }

            history[0] = (short)interaction;
            if ((interactionFlags & 16) != 0) {
               this.clearSimMilestone(otherSim, 1);
            } else {
               this.addSimMilestoneCount(otherSim, 1);
               this.addSimMilestoneCount(0, 1);
            }

            resultCode = this.interactionResultTextIds[interaction][variant];
            boolean applyEffects = true;
            Sim partner = screen.findSimByIndex(otherSim);
            if (resultCode == -1 && partner != null && partner.isSitting()) {
               applyEffects = false;
            }

            int needSum = 0;
            if (applyEffects) {
               byte gainNeed = this.interactionNeedGains[interaction][variant];
               byte lossNeed = this.interactionNeedLosses[interaction][variant];
               this.moveSim(otherSim, gainNeed, lossNeed);
               needSum = gainNeed + lossNeed;
               if (lossNeed > 0) {
                  resultFlags = 64;
               }

               int previousNeed = this.dominantNeed(otherSim);
               byte moodNeed = this.interactionMoodIndex[interaction][variant];
               byte moodGain = this.interactionMoodGain[interaction][variant];
               int[] simDecay = this.simNeedDecay[otherSim];

               for (int need = 0; need < simDecay.length; need++) {
                  if (need == moodNeed) {
                     simDecay[moodNeed] += moodGain << 16;
                     if (simDecay[moodNeed] > 13107200) {
                        simDecay[moodNeed] = 13107200;
                     }
                  } else {
                     simDecay[need] -= 655360;
                     if (simDecay[need] < 0) {
                        simDecay[need] = 0;
                     }
                  }
               }

               int currentNeed;
               if ((currentNeed = this.dominantNeed(otherSim)) != previousNeed && !recalled) {
                  short textId = this.needTextIds[currentNeed];
                  int nameTextId = this.simNameText(otherSim);
                  this.canvas.formatString1(-12, textId, nameTextId);
                  screen.showNotification(-12, -1);
                  if (currentNeed == 1 || currentNeed == 2 || currentNeed == 8) {
                     this.unlockAchievement(16);
                     this.addSimMilestoneCount(otherSim, 16384);
                  }

                  if (currentNeed == 2) {
                     this.unlockAchievement(17);
                     this.addSimMilestoneCount(otherSim, 64);
                  }

                  if (currentNeed == 1) {
                     this.unlockAchievement(18);
                     this.addSimMilestoneCount(otherSim, 128);
                  }
               }
            }

            if (((conditionFlags = this.interactionConditionFlags[interaction][variant]) & 8) != 0) {
               if (this.handleSpecialInteraction(otherSim, interaction, conditionFlags)) {
                  interrupt = true;
               }
            } else if (interaction == 99) {
               byte lot = this.simLots[0];
               byte hour;
               if ((hour = this.simHours[0]) < this.lotHourDayBits[lot].length - 1) {
                  screen.selectRequest(lot, hour + 1);
               }
            }

            if (this.checkStateTransition(otherSim)) {
               interrupt = true;
            } else if ((interactionFlags & 2097152) == 0 && this.advanceSimState(otherSim)) {
               interrupt = true;
            }

            if (interrupt) {
               animationId = this.stateBadgeIds[this.simStates[otherSim]];
            }

            if (animationId != -1) {
               resultFlags |= 3;
            } else {
               int needFactor;
               needFactor = FixedMath.clamp(needFactor = 3 + needSum, 0, 6);
               resultFlags |= needFactor;
            }

            if (resultFlags < 3) {
               resultFlags |= 32;
            } else {
               resultFlags |= 16;
            }
         }
      }

      if (resultCode != -1) {
         int targetSim = -1;
         if (sim == 0) {
            targetSim = otherSim;
         } else if (otherSim == 0) {
            targetSim = sim;
         }

         if (targetSim != -1) {
            this.adjustNeed(4, 1310720);
            this.watchSim(targetSim);
         }
      }

      if (sim == 0 && (conditionFlags & 8192) != 0) {
         this.tryStartCard(otherSim, interaction, !interrupt);
      }

      out[0] = resultFlags;
      out[1] = resultCode;
      out[2] = animationId;
   }

   public final int stateSprite(int state) {
      return this.stateSpriteIds[state];
   }

   public final int stateFlagsOf(int state) {
      return this.stateFlags[state];
   }

   public final int simState(int sim) {
      return this.simStates[sim];
   }

   public final void collectNeedChanges(int sim, int[] out) {
      int state = this.simState(sim);
      int[] needs = this.simNeeds[sim];
      byte[] nextStates = this.stateTransitionStates[state];
      byte[] needIndices = this.stateTransitionNeeds[state];
      short[] thresholds = this.stateTransitionThresholds[state];
      byte outIndex = 0;
      int count = nextStates.length;

      for (int index = 0; index < count; index++) {
         byte need = needIndices[index];
         short threshold = thresholds[index];
         int value = needs[need] >> 16;
         if (threshold > 0 && value > 0 || threshold < 0 && value < 0) {
            out[outIndex + 0] = nextStates[index];
            out[outIndex + 1] = FixedMath.div(value << 16, threshold << 16);
            outIndex += 2;
         }
      }

      out[outIndex] = -1;
   }

   public final boolean isFreeToLeave() {
      if (!this.canvas.gameScreen().isNoSaveMode()) {
         return true;
      } else {
         int lot = this.canvas.lot.areaId();
         int count = this.simCount;

         for (int sim = 1; sim < count; sim++) {
            if (this.simHomeLotOf(sim) == lot) {
               int state = this.simState(sim);
               if ((this.stateFlagsOf(state) & 32) != 0) {
                  return true;
               }
            }
         }

         return lot == 0;
      }
   }

   private void clearSimMilestones(int mask) {
      for (int sim = 1; sim < this.simCount; sim++) {
         this.simMilestones[sim] = (short)(this.simMilestones[sim] & ~mask);
      }
   }

   private int countPendingMilestones(int mask) {
      boolean all = true;
      int count = 0;

      for (int sim = this.simCount - 1; sim >= 1; sim--) {
         int lot;
         if ((lot = this.simHomeLotOf(sim)) != 0) {
            boolean has = (this.simMilestones[sim] & mask) != 0;
            all = all && has;
            if (has && GameCanvas.indexOfShortFrom(lot, this.simHomeLots, sim + 1) == -1) {
               count++;
            }
         }
      }

      return all ? -1 : count;
   }

   public final void addSimMilestone(int lot, int flag) {
      for (int sim = 1; sim < this.simCount; sim++) {
         if (this.simHomeLots[sim] == lot) {
            this.simMilestones[sim] = (short)(this.simMilestones[sim] | flag);
         }
      }

      int pending;
      if ((pending = this.countPendingMilestones(flag)) == -1) {
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

      if (flag == 512 || flag == 1024) {
         if (pending == -1) {
            this.unlockAchievement(52);
            return;
         }

         int otherFlag = flag == 512 ? 1024 : 512;
         if (pending + this.countPendingMilestones(otherFlag) >= 3) {
            this.unlockAchievement(52);
         }
      }
   }

   public final void addSimMilestoneCount(int sim, int flag) {
      this.simMilestones[sim] = (short)(this.simMilestones[sim] | flag);
      int count = GameCanvas.countAllFlagsFrom(flag, this.simMilestones, 0);
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

   private void clearSimMilestone(int sim, int flag) {
      this.simMilestones[sim] = (short)(this.simMilestones[sim] & ~flag);
   }

   public final boolean hasSimMilestone(int sim, int flag) {
      return (this.simMilestones[sim] & flag) != 0;
   }

   public final int idleSimCount() {
      return this.simStates.length - GameCanvas.countEqualBytes(0, this.simStates);
   }

   public final int countSimsInState(int state) {
      return GameCanvas.nthIndexNotEqualByte(state, this.simStates, 0);
   }

   public final int interactionAltText(int index) {
      return this.interactionAltTextIds[index];
   }

   public final int interactionText(int index) {
      return this.interactionTextIds[index];
   }

   public final int interactionFlagsOf(int index) {
      return this.interactionFlags[index];
   }

   public final int interactionVariantCount(int index) {
      return this.interactionVariants[index].length;
   }

   public final int interactionVariant(int index, int requirement) {
      return this.interactionVariants[index][requirement];
   }

   public final void fillInteractions(Entity entity, short[] labels, short[] commands) {
      if (entity.hasFlag(16384)) {
         Sim sim = (Sim)entity;
         this.fillSimInteractions(sim, labels, commands);
      } else {
         this.fillObjectInteractions(entity, labels, commands);
      }
   }

   private void fillSimInteractions(Sim sim, short[] labels, short[] commands) {
      int count = this.interactionCount;
      short[] textIds = this.interactionTextIds;
      int[] flags = this.interactionFlags;
      int simIndex = sim.variant;
      boolean romantic = sim.isSitting();
      this.watchSim(simIndex);
      short titleId = this.simNameTextIds[simIndex];
      GameCanvas.initList(labels, titleId);
      GameCanvas.initList(commands, titleId);
      if (this.canvas.gameScreen().isNewNeighborhood()) {
         boolean partnerFlag = false;
         GameCanvas.addToList(labels, textIds[155]);
         GameCanvas.addToList(commands, 155);
      } else {
         for (int interaction = 0; interaction < count; interaction++) {
            if ((flags[interaction] & 1) != 0 && ((flags[interaction] & 4) != 0 || !romantic) && ((flags[interaction] & 4) == 0 || romantic) && textIds[interaction] != 24) {
               boolean allowed = true;
               int flagBits;
               if (((flagBits = flags[interaction]) & 8) != 0) {
                  allowed = !this.hasSimMilestone(simIndex, 1);
               } else if ((flagBits & 16) != 0) {
                  allowed = this.hasSimMilestone(simIndex, 1);
               }

               if ((flagBits & 1056964608) != 0) {
                  int residentMask = this.selectedResidentMask();
                  allowed = (flagBits & residentMask) != 0;
               }

               if (allowed) {
                  allowed = this.bestInteractionFor(simIndex, interaction) != -1;
               }

               if (allowed) {
                  byte currentLot = this.simLots[0];
                  if ((flagBits & 16384) != 0) {
                     if (currentLot != -1) {
                        allowed = this.lotKind(currentLot) == simIndex;
                     } else {
                        allowed = false;
                     }
                  }

                  if (allowed && (flagBits & 32768) != 0) {
                     if (currentLot != -1) {
                        allowed = this.simLot(simIndex) == currentLot;
                     } else {
                        allowed = false;
                     }
                  }
               }

               label146:
               if (allowed) {
                  int lotIndex = this.canvas.lot.areaId();
                  int homeLot = this.simHomeLotOf(simIndex);
                  boolean tempResult;
                  if ((flagBits & 196608) == 196608) {
                     tempResult = this.hasSimMilestone(simIndex, 2);
                  } else {
                     if ((flagBits & 65536) != 0) {
                        allowed = lotIndex == 0 && homeLot != 0;
                        break label146;
                     }

                     if ((flagBits & 131072) == 0) {
                        break label146;
                     }

                     tempResult = lotIndex != 0 && homeLot == lotIndex;
                  }

                  allowed = tempResult;
               }

               if (allowed && (flagBits & 8388608) != 0) {
                  int card = this.cardRequirementId(interaction);
                  allowed = this.cardPerformers[card] == simIndex && this.isCardUnlocked(card);
               }

               if (allowed) {
                  switch (interaction) {
                     case 85:
                        allowed = GameCanvas.indexOfByte(12, this.simStates) == -1;
                        break;
                     case 94:
                        Sim playerSim = this.canvas.gameScreen().activePlayer;
                        int dx = Math.abs(sim.posX - playerSim.posX);
                        int dy = Math.abs(sim.posY - playerSim.posY);
                        allowed = dx + dy > 2097152;
                        break;
                     case 97:
                        allowed = GameCanvas.indexOfAnyFlag(2, this.simMilestones) == -1;
                        break;
                     case 98:
                        allowed = this.simStates[simIndex] != 12;
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

   private void fillObjectInteractions(Entity entity, short[] labels, short[] commands) {
      Sim playerSim = this.canvas.gameScreen().activePlayer;
      short[] textIds = this.interactionTextIds;
      int[] flags = this.interactionFlags;
      int titleId = entity.typeAnimId();
      GameCanvas.initList(labels, titleId);
      GameCanvas.initList(commands, titleId);
      int type = entity.type;
      Lot lot = this.canvas.lot;
      short[] interactionIds = this.canvas.lot.frameRefsAt(type);
      int typeFlags = lot.objectFlagsAt(type);
      int count = interactionIds.length;
      if ((typeFlags & 67108864) != 0 && entity.hasState(256)) {
         GameCanvas.addToList(labels, textIds[110]);
         GameCanvas.addToList(commands, 110);
      }

      if ((typeFlags & 134217728) != 0 && entity.hasState(512)) {
         GameCanvas.addToList(labels, textIds[109]);
         GameCanvas.addToList(commands, 109);
      }

      for (int index = 0; index < count; index++) {
         short interaction = interactionIds[index];
         int flagBits;
         if (((flagBits = flags[interaction]) & 1) == 0
            && ((flagBits & 32) == 0 || entity.hasState(64))
            && ((flagBits & 64) == 0 || !entity.hasState(64))
            && ((flagBits & 128) == 0 || entity.isIdleModeZero())
            && ((flagBits & 256) == 0 || entity.isIdleModeOne())
            && ((flagBits & 512) == 0 || !entity.hasState(256))
            && ((flagBits & 1024) == 0 || !entity.hasState(512))
            && ((flagBits & 4096) == 0 || this.isLotOccupant(entity, 0))
            && ((flagBits & 8192) == 0 || this.canInteractAt(entity, 0))
            && ((flagBits & 2048) == 0 || !this.isLotOccupant(entity, 0))
            && ((flagBits & 131072) == 0 || lot.areaId() == 0)
            && ((flagBits & 65536) == 0 || lot.areaId() != 0)
            && (interaction != 142 || type != 114 || this.canvas.gameStarted)) {
            if ((flagBits & 1056964608) != 0) {
               int mask = this.selectedResidentMask();
               if ((flagBits & mask) == 0) {
                  continue;
               }
            }

            if ((interaction != 130 || !entity.hasState(16384))
               && (interaction != 131 || !entity.hasState(32768))
               && (interaction != 150 || this.hasSimMilestone(0, 1) || lot.areaId() == 0)
               && (interaction != 153 || !this.hasSimMilestone(0, 1) && lot.areaId() != 0)) {
               if (interaction == 152) {
                  int gridX = Lot.toTileX(playerSim.posX);
                  int gridY = Lot.toTileY(playerSim.posY);
                  if ((lot.cellFlagsAt(gridX, gridY) & 16) != 0) {
                     continue;
                  }
               }

               GameCanvas.addToList(labels, textIds[interaction]);
               GameCanvas.addToList(commands, interaction);
            }
         }
      }

      if ((typeFlags & 4096) != 0) {
         GameCanvas.addToList(labels, textIds[106]);
         GameCanvas.addToList(commands, 106);
      }

      if (labels[0] == 0) {
         GameCanvas.addToList(labels, 274);
         GameCanvas.addToList(commands, -4);
      }
   }

   public final void fillInventoryMenu(short[] labels, short[] commands, short[] inventory) {
      GameCanvas.initList(labels, inventory[1]);
      GameCanvas.initList(commands, 24);
      short[] textIds = this.interactionTextIds;
      short[] sprites = this.interactionSprites;
      short count = inventory[0];

      for (int index = 0; index < count; index++) {
         short item = inventory[5 + index];
         short spriteId;
         if ((spriteId = sprites[item]) == 24 || this.countMenuItems(inventory, spriteId) < 2) {
            GameCanvas.addToList(labels, textIds[item]);
            GameCanvas.addToList(commands, item);
         } else if (!GameCanvas.listContains(labels, spriteId)) {
            GameCanvas.addToList(labels, spriteId);
            GameCanvas.addToList(commands, -2);
         }
      }
   }

   private int countMenuItems(short[] list, int spriteId) {
      short[] sprites = this.interactionSprites;
      int count = 0;
      short size = list[0];

      for (int index = 0; index < size; index++) {
         short item = list[index + 5];
         short itemSprite;
         if ((itemSprite = sprites[item]) == spriteId) {
            count++;
         }
      }

      return count;
   }

   public final void fillFilteredMenu(short[] labels, short[] commands, short[] source, int filter) {
      GameCanvas.initList(labels, filter);
      GameCanvas.initList(commands, filter);
      short[] textIds = this.interactionTextIds;
      short[] sprites = this.interactionSprites;
      short count = source[0];

      for (int index = 0; index < count; index++) {
         short item = source[5 + index];
         short spriteId;
         if ((spriteId = sprites[item]) == filter) {
            GameCanvas.addToList(labels, textIds[item]);
            GameCanvas.addToList(commands, item);
         }
      }
   }

   public final void fillResidentMenu(short[] labels, short[] commands, int interaction) {
      short textId = this.interactionTextIds[interaction];
      GameCanvas.initList(labels, textId);
      GameCanvas.initList(commands, textId);
      int lotIndex = this.canvas.lot.areaId();

      for (int sim = 1; sim < this.simCount; sim++) {
         if (this.simStates[sim] != 0 && this.simHomeLotOf(sim) != lotIndex) {
            GameCanvas.addToList(labels, this.simNameTextIds[sim]);
            GameCanvas.addToList(commands, interaction);
         }
      }
   }

   private int dominantNeed(int sim) {
      int[] decay;
      int count = (decay = this.simNeedDecay[sim]).length;
      int need = 3;
      int value = 0;

      for (int index = 0; index < count; index++) {
         if (decay[index] > value) {
            value = decay[index];
            need = index;
         }
      }

      return need;
   }

   private static int stateNeedMask(int state) {
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

   private boolean hasRecentAction(int sim, int interaction, int required, int historyLength) {
      short[] history = this.simActionHistory[sim];
      int foundCount = 0;

      for (int index = 0; index < historyLength; index++) {
         if (history[index] == interaction) {
            if (++foundCount >= required) {
               return true;
            }
         }
      }

      return false;
   }

   private int bestInteractionFor(int sim, int interaction) {
      int need = this.dominantNeed(sim);
      byte state;
      int stateMask = stateNeedMask(state = this.simStates[sim]);
      byte[] stateReqs = this.interactionStateReqs[interaction];
      byte[] needReqs = this.interactionNeedReqs[interaction];
      int[] flagReqs = this.interactionFlagReqs[interaction];
      short[] conditionFlags = this.interactionConditionFlags[interaction];

      for (int variant = stateReqs.length - 1; variant >= 0; variant--) {
         byte stateReq;
         byte needReq;
         int flagReq;
         if (((stateReq = stateReqs[variant]) == -1 || stateReq == need)
            && ((needReq = needReqs[variant]) == -1 || this.simKnownIndex(sim, needReq) != -1)
            && ((flagReq = flagReqs[variant]) == 0 || (flagReq & stateMask) != 0)) {
            short conditions;
            if (((conditions = conditionFlags[variant]) & 7) != 0) {
               byte requiredCount = 3;
               byte historyLength = 6;
               if ((conditions & 1) != 0) {
                  requiredCount = 2;
                  historyLength = 4;
               } else if ((conditions & 4) != 0) {
                  requiredCount = 6;
                  historyLength = 10;
               }

               if (!this.hasRecentAction(sim, interaction, requiredCount, historyLength)) {
                  continue;
               }
            }

            if ((conditions & 496) != 0) {
               byte resident;
               if ((conditions & 16) != 0) {
                  resident = 0;
               } else if ((conditions & 32) != 0) {
                  resident = 1;
               } else if ((conditions & 64) != 0) {
                  resident = 2;
               } else if ((conditions & 128) != 0) {
                  resident = 3;
               } else {
                  resident = 4;
               }

               int growth = this.residentGrowth[resident];
               int threshold = (conditions & 512) != 0 ? 196608 : 65536;
               if (growth < threshold) {
                  continue;
               }
            }

            byte lot;
            byte hour;
            if (((conditions & 3072) == 0 || ((conditions & 2048) != 0 ? this.canRunTask(4) : this.canRunTask(1)))
               && (
                  interaction != 99
                     || (conditions & 8) == 0
                     || (conditions & 4096) != 0
                     || (lot = this.simLots[0]) != -1 && (hour = this.simHours[0]) < this.lotHourInteractions[lot].length - 1 && this.canEnterLot(lot, hour + 1)
               )) {
               return variant;
            }
         }
      }

      return -1;
   }

   public final boolean canUseInteraction(int sim, int target, int interaction) {
      if (sim == 0) {
         int variant = this.bestInteractionFor(target, interaction);
         return (this.interactionConditionFlags[interaction][variant] & 4096) == 0;
      } else {
         return true;
      }
   }

   public final int commentText(int interaction) {
      short categoryBits;
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

      int matched = 0;

      for (byte scanIndex = 0; scanIndex < COMMENT_TEXT_IDS.length; scanIndex += 2) {
         if ((categoryBits & COMMENT_TEXT_IDS[scanIndex + 1]) != 0) {
            matched++;
         }
      }

      int chosen = this.canvas.randomRange(0, matched - 1);
      matched = 0;

      for (byte pickIndex = 0; pickIndex < COMMENT_TEXT_IDS.length; pickIndex += 2) {
         if ((categoryBits & COMMENT_TEXT_IDS[pickIndex + 1]) != 0) {
            if (matched == chosen) {
               return COMMENT_TEXT_IDS[pickIndex];
            }

            matched++;
         }
      }

      return 196;
   }

   private boolean canEnterCard(int card) {
      byte leftAction = this.visitCardLeft[card];
      byte rightAction = this.visitCardRight[card];
      byte upAction = this.visitCardUp[card];
      byte downAction = this.visitCardDown[card];
      int lot = this.simLot(0);
      int hour = this.simHour(0);
      if ((leftAction == 2 || rightAction == 2 || upAction == 2 || downAction == 2) && hour == 0) {
         return false;
      } else {
         int lastHour = this.lotHourInteractions[lot].length - 1;
         return leftAction != 1 && rightAction != 1 && upAction != 1 && downAction != 1 || hour != lastHour;
      }
   }

   public final int pickRandomCard() {
      int card;
      do {
         card = this.canvas.randomRange(0, this.visitCardTimeCosts.length - 1);
         new StringBuffer().append("trying card: ").append(card).toString();
      } while (!this.canEnterCard(card));

      new StringBuffer().append("final card: ").append(card).toString();
      return card;
   }

   public final int visitOptionState(int card, boolean replay) {
      int progress = this.canvas.randomPercent();
      if (replay) {
         return progress < this.visitCardMinuteLimits[card] ? 0 : 1;
      } else {
         return progress < this.visitCardOtherLimits[card] ? 2 : 3;
      }
   }

   public final int cardTimeCost(int card) {
      return this.visitCardTimeCosts[card];
   }

   public final int cardOptionText(int card, int option) {
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

   public final void applyCardDirection(int card, int direction) {
      byte action;
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

      byte lot = this.simLots[0];
      byte hour = this.simHours[0];
      switch (action) {
         case 0:
            return;
         case 1:
            this.unlockAchievement(54);
            this.selectLocation(lot, hour + 1);
            return;
         case 2:
            this.selectLocation(lot, hour - 1);
            return;
         case 3:
            this.selectLocation(-1, 0);
            return;
         case 4:
            byte item = -1;
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

   private void resetCardSlots() {
      if (this.cardPerformers == null) {
         this.cardPerformers = new byte[this.cardLabelTextIds.length];
         this.cardTimes = new int[this.cardLabelTextIds.length];
      }

      GameCanvas.fillBytes(this.cardPerformers, -1);
      GameCanvas.fillInts(this.cardTimes, -1);
   }

   public final int cardLabelText(int index) {
      return this.cardLabelTextIds[index];
   }

   public final int cardAltText(int index) {
      return this.cardAltTextIds[index];
   }

   public final int cardFlagsAt(int index) {
      return this.cardFlags[index];
   }

   private int cardRequirementId(int index) {
      return GameCanvas.indexOfShort(index, this.cardRequiredLots);
   }

   private void tryStartCard(int simIndex, int lotId, boolean allowNewCard) {
      int now = this.gameTime;
      byte[] cards = this.simCardList[simIndex];

      for (int cardSlot = 0; cardSlot < cards.length; cardSlot++) {
         byte cardIndex = cards[cardSlot];
         int lastStarted = this.cardTimes[cardIndex];
         boolean ownedBySim = false;
         if (this.cardPerformers[cardIndex] == simIndex) {
            if (this.cardRequiredLots[cardIndex] == lotId) {
               this.canvas.gameScreen().openCareerGoalPage(cardIndex, simIndex);
               return;
            }

            if (now - lastStarted < 1440) {
               continue;
            }

            ownedBySim = true;
         } else if (this.cardPerformers[cardIndex] != -1) {
            continue;
         }

         if (allowNewCard
            && GameCanvas.indexOfShort(lotId, this.cardRequirementIds[cardIndex]) != -1
            && (ownedBySim || GameCanvas.indexOfByte(simIndex, this.cardPerformers) == -1 && (lastStarted == -1 || now - lastStarted >= 1440) && this.canvas.randomPercent() <= 30)) {
            this.cardTimes[cardIndex] = this.gameTime;
            this.startCard(cardIndex, simIndex);
            return;
         }
      }
   }

   private void startCard(int cardIndex, int simIndex) {
      this.cardPerformers[cardIndex] = (byte)simIndex;
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

   public final void finishCard(int card) {
      short rewardMoney;
      if ((rewardMoney = this.cardRewardMoney[card]) != 0) {
         this.addMoney(rewardMoney);
      }

      short rewardItem;
      if ((rewardItem = this.cardRewardItems[card]) != -1) {
         this.addItem(rewardItem, -1);
      }

      byte performer = -1;
      if ((this.cardFlags[card] & 1) != 0) {
         performer = -2;
      }

      this.cardPerformers[card] = performer;
      this.cardTimes[card] = this.gameTime;
      if (card == 22) {
         this.addItem(48, 1);
      }
   }

   private boolean isCardUnlocked(int card) {
      short[] milestones = this.simMilestones;
      switch (card) {
         case 0:
            if ((milestones[1] & 4096) != 0) {
               return true;
            }

            return false;
         case 1:
            if ((milestones[1] & 2048) != 0) {
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
            if ((milestones[2] & 8192) != 0) {
               return true;
            }

            return false;
         case 9:
            if ((milestones[13] & 4096) != 0) {
               return true;
            }

            return false;
         case 16:
            if ((milestones[4] & 4096) != 0) {
               return true;
            }

            return false;
         case 17:
            if ((milestones[4] & 16384) != 0) {
               return true;
            }

            return false;
         case 18:
            if ((milestones[4] & 2048) != 0) {
               return true;
            }

            return false;
         case 19:
            if ((milestones[2] & 4096) != 0) {
               return true;
            }

            return false;
         case 20:
            if ((milestones[2] & 16384) != 0) {
               return true;
            }

            return false;
         case 21:
            if ((milestones[2] & 2048) != 0) {
               return true;
            }

            return false;
         case 29:
            return (milestones[7] & 4096) != 0;
      }
   }

   private void normalizeTaskSlots() {
      if (this.simKnownIds[0].length != 5) {
         this.simKnownIds[0] = new byte[5];
         GameCanvas.fillBytes(this.simKnownIds[0], -1);
      }
   }

   public final int simNameText(int sim) {
      return this.simNameTextIds[sim];
   }

   public final int simPortrait(int sim) {
      return this.simPortraitIds[sim];
   }

   public final int simSpareByte(int sim) {
      return this.simSpareBytes[sim];
   }

   public final int simAttribute(int sim, int index) {
      return this.simAttributes[sim][index];
   }

   public final int simHomeLotOf(int sim) {
      return this.hasSimMilestone(sim, 2) ? 0 : this.simHomeLots[sim];
   }

   public final int simLot(int sim) {
      return sim == 10 && this.canvas.gameStarted ? 4 : this.simLots[sim];
   }

   public final int simHour(int sim) {
      return sim == 10 && this.canvas.gameStarted ? this.lotHourInteractions[4].length - 1 : this.simHours[sim];
   }

   public final byte[] portraitData(int index) {
      return this.portraitData[index];
   }

   public final void setAttribute(int index, int value) {
      this.simAttributes[0][index] = (byte)value;
   }

   public final boolean simHasFlag(int sim, int flag) {
      return (this.simFlags[sim] & flag) != 0;
   }

   public final int simKnownIndex(int sim, int id) {
      return GameCanvas.indexOfByte(id, this.simKnownIds[sim]);
   }

   public final boolean simKnowsWell(int sim, int id) {
      int index;
      return (index = this.simKnownIndex(sim, id)) != -1 ? this.simRepeatCounts[sim][index] >= 3 : false;
   }

   public final boolean simRemember(int sim, int interaction) {
      byte[] counts = this.simRepeatCounts[sim];
      int index = this.simKnownIndex(sim, interaction);
      if (counts[index] < 3) {
         counts[index]++;
         if (counts[index] == 3) {
            int nameTextId = this.simNameText(sim);
            short labelTextId = this.itemNameTextIds[interaction];
            this.canvas.formatString2(-12, 412, nameTextId, labelTextId);
            this.canvas.gameScreen().showNotification(-12, -1);
            return true;
         }
      }

      return false;
   }

   public final int simKnownCount(int sim) {
      return this.simKnownIds[sim].length;
   }

   public final int simKnownId(int sim, int index) {
      return this.simKnownIds[sim][index];
   }

   public final int simKnownFreeSlot() {
      byte[] ids;
      return (ids = this.simKnownIds[0]).length - GameCanvas.countEqualBytes(-1, ids);
   }

   public final void addKnown(int id) {
      if (this.simKnownIndex(0, id) == -1) {
         short sprite;
         if ((sprite = this.itemSpriteIds[id]) != -1) {
            this.removeKnown(sprite);
         }

         int slot;
         if ((slot = this.simKnownIndex(0, -1)) != -1) {
            this.simKnownIds[0][slot] = (byte)id;
         }
      }
   }

   public final void removeKnown(int id) {
      int slot;
      if ((slot = this.simKnownIndex(0, id)) != -1) {
         this.simKnownIds[0][slot] = -1;
      }
   }

   public final int simIndexOf(int sim) {
      return GameCanvas.indexOfShortFrom(sim, this.simNameTextIds, 1);
   }

   public final boolean isSimIdle(int sim) {
      byte state;
      return (state = this.simStates[sim]) != 2 && state != 3 && state != 4 && (this.lotStateNow(sim) & 16) == 0 && (this.lotStateNow(sim) & 8) != 0;
   }

   public final void update(int elapsed) {
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
         this.celebrationTimer = Math.max(this.celebrationTimer - elapsed, 0);
      }
   }

   public final void resetAll() {
      this.paused = false;
      this.watchSim(-1);
      GameCanvas.fillInts(this.needModifiers, 0);

      for (int sim = 1; sim < this.simCount; sim++) {
         this.clearSimMilestone(sim, 1);
         GameCanvas.fillInts(this.simNeedDecay[sim], 0);
         GameCanvas.fillShorts(this.simActionHistory[sim], -1);
      }

      this.clearSimMilestone(0, 1);
      this.celebrate();
   }

   public final void celebrate() {
      this.celebrationTimer = 2500;
   }

   public final boolean isCelebrating() {
      return this.celebrationTimer != 0;
   }

   public final void completeAllTips() {
      int count = this.achievementCount();
      int mask = this.selectedResidentSpriteB();

      for (int index = 0; index < count; index++) {
         if ((this.achievementFlagAt(index) & mask) != 0) {
            this.canvas.addFlag(index);
         }
      }

      this.markItemVisited(99);
   }

   private void resetTips() {
      if (this.pendingTips == null) {
         this.pendingTips = new short[100];
         this.tipValues = new int[100];
         this.tipTimes = new int[100];
      }

      GameCanvas.fillShorts(this.pendingTips, -1);
      GameCanvas.fillInts(this.tipValues, 0);
      GameCanvas.fillInts(this.tipTimes, 0);
   }

   public final void pushTip(int key, int subKey, int value) {
      int packedKey;
      int slot = GameCanvas.indexOfShort(packedKey = key << 8 | subKey, this.pendingTips);
      if (value == 0 && slot != -1) {
         this.removeTip(key, subKey);
      } else {
         if (slot == -1) {
            slot = GameCanvas.indexOfShort(-1, this.pendingTips);
         }

         this.pendingTips[slot] = (short)packedKey;
         this.tipValues[slot] = value;
         this.tipTimes[slot] = this.gameTime;
      }
   }

   public final int tipValue(int key, int subKey) {
      int slot;
      return (slot = GameCanvas.indexOfShort(key << 8 | subKey, this.pendingTips)) == -1 ? 0 : this.tipValues[slot];
   }

   public final int tipTime(int key, int subKey) {
      int slot;
      return (slot = GameCanvas.indexOfShort(key << 8 | subKey, this.pendingTips)) == -1 ? -1 : this.tipTimes[slot];
   }

   public final void removeTip(int key, int subKey) {
      int slot;
      if ((slot = GameCanvas.indexOfShort(key << 8 | subKey, this.pendingTips)) != -1) {
         this.pendingTips[slot] = -1;
         this.tipValues[slot] = 0;
         this.tipTimes[slot] = 0;
      }
   }

   public final void resetAllState() {
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

   public final void load(DataInputStream input) throws IOException {
      for (int attrIndex = 0; attrIndex < 10; attrIndex++) {
         this.simAttributes[0][attrIndex] = input.readByte();
      }

      byte[] knownIds = this.simKnownIds[0];

      for (int knownIndex = 0; knownIndex < 5; knownIndex++) {
         knownIds[knownIndex] = input.readByte();
      }

      this.timeAccumulator = input.readInt();
      this.gameTime = input.readInt();
      this.money = input.readInt();
      this.moneySpent = input.readInt();
      this.lastCareerLevel = input.readByte();
      this.lastPromotionDay = input.readShort();

      for (int itemIndex = 0; itemIndex < this.itemCounts.length; itemIndex++) {
         this.itemCounts[itemIndex] = input.readByte();
      }

      for (int growthIndex = 0; growthIndex < this.residentGrowth.length; growthIndex++) {
         this.residentGrowth[growthIndex] = input.readInt();
      }

      this.simLots[0] = input.readByte();
      this.simHours[0] = input.readByte();
      this.lastVisitDay = input.readByte();
      this.tickCount = input.readShort();
      this.idleTicks = input.readShort();

      for (int hourIndex = 0; hourIndex < this.lotLastHour.length; hourIndex++) {
         this.lotLastHour[hourIndex] = input.readByte();
      }

      this.suggestedLot = input.readByte();
      this.suggestionTimer = input.readInt();
      this.selectedResident = input.readByte();
      this.pendingAchievementId = input.readByte();
      this.randomSeed = input.readShort();
      this.achievementTimer = input.readInt();

      for (int slotIndex = 0; slotIndex < this.achievementSlots.length; slotIndex++) {
         this.achievementSlots[slotIndex] = input.readByte();
      }

      this.usedTaskBits = input.readInt();
      this.milestoneFlags = input.readInt();

      for (int achievementIndex = 0; achievementIndex < this.achievementTimes.length; achievementIndex++) {
         this.achievementTimes[achievementIndex] = input.readInt();
      }

      this.altEventCount = input.readShort();
      this.otherEventCount = input.readShort();

      for (int taskTimeIndex = 0; taskTimeIndex < this.recentTaskTimes.length; taskTimeIndex++) {
         this.recentTaskTimes[taskTimeIndex] = input.readInt();
      }

      this.visitedLotBits = input.readInt();

      for (int needIndex = 0; needIndex < this.needValues.length; needIndex++) {
         this.needValues[needIndex] = input.readInt();
      }

      for (int slot = 0; slot < 6; slot++) {
         this.activeTasks[slot] = input.readShort();
         this.taskTimers[slot] = input.readInt();
      }

      this.applyMoodDeltas();
      int simTotal = this.simCount;

      for (int sim = 1; sim < simTotal; sim++) {
         this.simStates[sim] = input.readByte();
         this.simMilestones[sim] = input.readShort();
         int[] needs = this.simNeeds[sim];

         for (int need = 0; need < needs.length; need++) {
            needs[need] = input.readInt();
         }

         byte[] counts = this.simRepeatCounts[sim];

         for (int countIndex = 0; countIndex < counts.length; countIndex++) {
            counts[countIndex] = input.readByte();
         }
      }

      for (int tipIndex = 0; tipIndex < 100; tipIndex++) {
         this.pendingTips[tipIndex] = input.readShort();
         this.tipValues[tipIndex] = input.readInt();
         this.tipTimes[tipIndex] = input.readInt();
      }

      for (int card = 0; card < this.cardPerformers.length; card++) {
         this.cardPerformers[card] = input.readByte();
         this.cardTimes[card] = input.readInt();
      }
   }

   public final void save(DataOutputStream output) throws IOException {
      for (int attrIndex = 0; attrIndex < 10; attrIndex++) {
         output.writeByte(this.simAttributes[0][attrIndex]);
      }

      byte[] knownIds = this.simKnownIds[0];

      for (int knownIndex = 0; knownIndex < 5; knownIndex++) {
         output.writeByte(knownIds[knownIndex]);
      }

      output.writeInt(this.timeAccumulator);
      output.writeInt(this.gameTime);
      output.writeInt(this.money);
      output.writeInt(this.moneySpent);
      output.writeByte(this.lastCareerLevel);
      output.writeShort(this.lastPromotionDay);

      for (int itemIndex = 0; itemIndex < this.itemCounts.length; itemIndex++) {
         output.writeByte(this.itemCounts[itemIndex]);
      }

      for (int growthIndex = 0; growthIndex < this.residentGrowth.length; growthIndex++) {
         output.writeInt(this.residentGrowth[growthIndex]);
      }

      output.writeByte(this.simLots[0]);
      output.writeByte(this.simHours[0]);
      output.writeByte(this.lastVisitDay);
      output.writeShort(this.tickCount);
      output.writeShort(this.idleTicks);

      for (int hourIndex = 0; hourIndex < this.lotLastHour.length; hourIndex++) {
         output.writeByte(this.lotLastHour[hourIndex]);
      }

      output.writeByte(this.suggestedLot);
      output.writeInt(this.suggestionTimer);
      output.writeByte(this.selectedResident);
      output.writeByte(this.pendingAchievementId);
      output.writeShort(this.randomSeed);
      output.writeInt(this.achievementTimer);

      for (int slotIndex = 0; slotIndex < this.achievementSlots.length; slotIndex++) {
         output.writeByte(this.achievementSlots[slotIndex]);
      }

      output.writeInt(this.usedTaskBits);
      output.writeInt(this.milestoneFlags);

      for (int achievementIndex = 0; achievementIndex < this.achievementTimes.length; achievementIndex++) {
         output.writeInt(this.achievementTimes[achievementIndex]);
      }

      output.writeShort(this.altEventCount);
      output.writeShort(this.otherEventCount);

      for (int taskTimeIndex = 0; taskTimeIndex < this.recentTaskTimes.length; taskTimeIndex++) {
         output.writeInt(this.recentTaskTimes[taskTimeIndex]);
      }

      output.writeInt(this.visitedLotBits);

      for (int needIndex = 0; needIndex < this.needValues.length; needIndex++) {
         output.writeInt(this.needValues[needIndex]);
      }

      for (int slot = 0; slot < 6; slot++) {
         output.writeShort(this.activeTasks[slot]);
         output.writeInt(this.taskTimers[slot]);
      }

      int simTotal = this.simCount;

      for (int sim = 1; sim < simTotal; sim++) {
         output.writeByte(this.simStates[sim]);
         output.writeShort(this.simMilestones[sim]);
         int[] needs = this.simNeeds[sim];

         for (int need = 0; need < needs.length; need++) {
            output.writeInt(needs[need]);
         }

         byte[] counts = this.simRepeatCounts[sim];

         for (int countIndex = 0; countIndex < counts.length; countIndex++) {
            output.writeByte(counts[countIndex]);
         }
      }

      for (int tipIndex = 0; tipIndex < 100; tipIndex++) {
         output.writeShort(this.pendingTips[tipIndex]);
         output.writeInt(this.tipValues[tipIndex]);
         output.writeInt(this.tipTimes[tipIndex]);
      }

      for (int card = 0; card < this.cardPerformers.length; card++) {
         output.writeByte(this.cardPerformers[card]);
         output.writeInt(this.cardTimes[card]);
      }
   }

   public final void applyCommand(int command) {
      int sim = this.watchedSim;
      int[] growth = this.residentGrowth;
      switch (command) {
         case 0:
            this.advanceTime(60000);
            return;
         case 1:
            this.advanceTime(1440000);
            return;
         case 2:
            int fullValue = 3276800;

            for (int need = 0; need < this.needValues.length; need++) {
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
            for (int simIndex = 1; simIndex < this.simCount; simIndex++) {
               if (this.simStates[simIndex] == 0) {
                  this.setSimState(simIndex, 5);
               }
            }

            return;
         case 13:
            growth[0] = Math.max(growth[0] - 65536, 0);
            growth[0] = 327675;
            return;
         case 14:
            growth[0] = Math.min(growth[0] + 65536, 327680);
            return;
         case 15:
            growth[1] = Math.max(growth[1] - 65536, 0);
            growth[1] = 327675;
            return;
         case 16:
            growth[1] = Math.min(growth[1] + 65536, 327680);
            return;
         case 17:
            growth[2] = Math.max(growth[2] - 65536, 0);
            growth[2] = 327675;
            return;
         case 18:
            growth[2] = Math.min(growth[2] + 65536, 327680);
            return;
         case 19:
            growth[3] = Math.max(growth[3] - 65536, 0);
            growth[3] = 327675;
            return;
         case 20:
            growth[3] = Math.min(growth[3] + 65536, 327680);
            return;
         case 21:
            growth[4] = Math.max(growth[4] - 65536, 0);
            growth[4] = 327675;
            return;
         case 22:
            growth[4] = Math.min(growth[4] + 65536, 327680);
            return;
         case 23:
            this.pendingAchievementId = (byte)this.pickRandomAchievement();
            this.achievementTimer = 0;
            return;
         case 24:
            for (int index = 0; index < this.achievementTextIds.length; index++) {
               this.canvas.addFlag(index);
               this.canvas.addVisitedFlag(index);
            }

            this.canvas.markGameStarted();
            return;
         case 25:
            for (int item = 0; item < this.itemCounts.length; item++) {
               this.itemCounts[item] = (byte)this.canvas.lot.categoryFlagAt(item);
            }
      }
   }
}
