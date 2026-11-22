/**
 * MIDP media: {@code Manager}, {@code Player}, {@code PlayerListener} and
 * {@code VolumeControl}.
 *
 * The Java build decodes MIDI and AMR itself. Here the assets are pre-rendered
 * to Ogg Vorbis at build time, so a player only has to drive an audio element
 * while keeping the MIDP state machine, loop counting and listener events.
 */

export class MediaException extends Error {
  constructor(message = 'media error') {
    super(message);
    this.name = 'MediaException';
  }
}

export interface Control {
  readonly __control: string;
}

export interface VolumeControl extends Control {
  setLevel(level: number): number;
  getLevel(): number;
  setMute(mute: boolean): void;
  isMuted(): boolean;
}

/** The MIDP {@code Player} surface the game uses. */
export interface Player extends VolumeControl {
  realize(): void;
  prefetch(): void;
  start(): void;
  stop(): void;
  deallocate(): void;
  close(): void;
  setLoopCount(count: number): void;
  setMediaTime(now: number): number;
  getMediaTime(): number;
  getDuration(): number;
  getState(): number;
  getContentType(): string;
  addPlayerListener(listener: PlayerListener | null): void;
  removePlayerListener(listener: PlayerListener | null): void;
  getControl(controlType: string | null): Control | null;
  getControls(): Control[];
}

export const PlayerListener = {
  BUFFERING_STARTED: 'bufferingStarted',
  BUFFERING_STOPPED: 'bufferingStopped',
  CLOSED: 'closed',
  DEVICE_AVAILABLE: 'deviceAvailable',
  DEVICE_UNAVAILABLE: 'deviceUnavailable',
  DURATION_UPDATED: 'durationUpdated',
  END_OF_MEDIA: 'endOfMedia',
  ERROR: 'error',
  RECORD_ERROR: 'recordError',
  RECORD_STARTED: 'recordStarted',
  RECORD_STOPPED: 'recordStopped',
  SIZE_CHANGED: 'sizeChanged',
  STARTED: 'started',
  STOPPED: 'stopped',
  VOLUME_CHANGED: 'volumeChanged',
} as const;

export interface PlayerListener {
  playerUpdate(player: Player, event: string, eventData: unknown): void;
}

export const Player = {
  UNREALIZED: 100,
  REALIZED: 200,
  PREFETCHED: 300,
  STARTED: 400,
  CLOSED: 0,
  TIME_UNKNOWN: -1,
} as const;

/** A source that has already been turned into something an audio element can play. */
export interface MediaSource {
  /** Object URL of the encoded audio. */
  readonly url: string;
  readonly contentType: string;
}

/** Plays an encoded media source through an {@code HTMLAudioElement}. */
export class AudioPlayer implements Player {
  readonly __control = 'VolumeControl';

  private readonly source: MediaSource;
  private readonly listeners: PlayerListener[] = [];
  private audio: HTMLAudioElement | null = null;
  private state: number = Player.UNREALIZED;
  private loopCount = 1;
  private remainingLoops = 1;
  private level = 100;
  private muted = false;
  private finished = true;
  private onEndedListener: (() => void) | null = null;

  constructor(source: MediaSource) {
    this.source = source;
  }

  // ---------------------------------------------------------------- lifecycle

  realize(): void {
    if (this.state === Player.UNREALIZED) {
      this.open();
      this.state = Player.REALIZED;
    }
  }

  prefetch(): void {
    this.realize();
    if (this.state === Player.REALIZED) {
      this.state = Player.PREFETCHED;
    }
  }

  start(): void {
    if (this.state === Player.CLOSED) {
      throw new MediaException('player is closed');
    }
    this.prefetch();
    if (this.state !== Player.STARTED) {
      this.play();
      this.state = Player.STARTED;
    }
    this.dispatch(PlayerListener.STARTED);
  }

  stop(): void {
    if (this.state !== Player.STARTED) {
      return;
    }
    this.halt();
    this.state = Player.PREFETCHED;
    this.dispatch(PlayerListener.STOPPED);
  }

  deallocate(): void {
    this.stop();
    this.state = Player.REALIZED;
  }

  close(): void {
    if (this.state === Player.CLOSED) {
      return;
    }
    if (this.state === Player.STARTED) {
      this.halt();
    }
    this.release();
    this.state = Player.CLOSED;
    this.dispatch(PlayerListener.CLOSED);
  }

  setLoopCount(count: number): void {
    if (count === 0) {
      throw new Error('loop count 0 is not valid');
    }
    this.loopCount = count;
    this.remainingLoops = count;
    // -1 means "loop forever"; let the element itself loop so the seam is
    // gapless, exactly like the Java sequencer's LOOP_CONTINUOUSLY.
    if (this.audio !== null) {
      this.audio.loop = count === -1;
    }
  }

  setMediaTime(now: number): number {
    if (this.state === Player.CLOSED) {
      throw new MediaException('player is closed');
    }
    this.seek(Math.max(0, now));
    return this.getMediaTime();
  }

  getMediaTime(): number {
    if (this.state === Player.CLOSED || this.audio === null) {
      return Player.TIME_UNKNOWN;
    }
    return Math.round(this.audio.currentTime * 1000);
  }

  getDuration(): number {
    if (this.state === Player.CLOSED || this.audio === null) {
      return Player.TIME_UNKNOWN;
    }
    const duration = this.audio.duration;
    return Number.isFinite(duration) ? Math.round(duration * 1000) : Player.TIME_UNKNOWN;
  }

  getState(): number {
    return this.state;
  }

  getContentType(): string {
    return this.source.contentType;
  }

  addPlayerListener(listener: PlayerListener | null): void {
    if (listener !== null && !this.listeners.includes(listener)) {
      this.listeners.push(listener);
    }
  }

  removePlayerListener(listener: PlayerListener | null): void {
    const index = this.listeners.indexOf(listener as PlayerListener);
    if (index >= 0) {
      this.listeners.splice(index, 1);
    }
  }

  // ----------------------------------------------------------- VolumeControl

  setLevel(newLevel: number): number {
    const clamped = Math.max(0, Math.min(100, newLevel));
    if (clamped !== this.level) {
      this.level = clamped;
      this.applyVolume();
    }
    return this.level;
  }

  getLevel(): number {
    return this.level;
  }

  setMute(mute: boolean): void {
    if (this.muted !== mute) {
      this.muted = mute;
      this.applyVolume();
    }
  }

  isMuted(): boolean {
    return this.muted;
  }

  getControl(controlType: string | null): Control | null {
    if (controlType === null) {
      return null;
    }
    const name = controlType.substring(controlType.lastIndexOf('.') + 1);
    return name === 'VolumeControl' ? this : null;
  }

  getControls(): Control[] {
    return [this];
  }

  // ------------------------------------------------------------- media hooks

  private open(): void {
    const audio = new Audio();
    audio.preload = 'auto';
    audio.src = this.source.url;
    audio.loop = this.loopCount === -1;
    this.onEndedListener = () => this.onEnded();
    audio.addEventListener('ended', this.onEndedListener);
    this.audio = audio;
    this.applyVolume();
  }

  private play(): void {
    this.finished = false;
    const audio = this.audio!;
    audio.loop = this.loopCount === -1;
    if (this.loopCount !== -1) {
      this.remainingLoops = this.loopCount;
    }
    const started: Promise<void> = audio.play();
    started.catch((): void => {
      // Browsers block audio until the first user gesture; retry then.
      unlockAudio(() => {
        if (this.state === Player.STARTED) {
          ignoreRejection(audio.play());
        }
      });
    });
  }

  private halt(): void {
    this.audio?.pause();
  }

  private release(): void {
    const audio = this.audio;
    if (audio !== null) {
      if (this.onEndedListener !== null) {
        audio.removeEventListener('ended', this.onEndedListener);
        this.onEndedListener = null;
      }
      audio.pause();
      audio.removeAttribute('src');
      // Drops the decoder buffers the element was holding.
      audio.load();
    }
    this.audio = null;
    this.finished = true;
  }

  private seek(millis: number): void {
    if (this.audio !== null && this.audio.readyState > 0) {
      this.audio.currentTime = millis / 1000;
    }
  }

  private applyVolume(): void {
    if (this.audio !== null) {
      this.audio.volume = this.muted ? 0 : this.level / 100;
    }
  }

  private onEnded(): void {
    if (this.state !== Player.STARTED || this.finished || this.loopCount === -1) {
      return;
    }
    this.remainingLoops--;
    if (this.remainingLoops > 0) {
      this.audio!.currentTime = 0;
      ignoreRejection(this.audio!.play());
      return;
    }
    this.finished = true;
    this.state = Player.PREFETCHED;
    this.dispatch(PlayerListener.END_OF_MEDIA);
  }

  private dispatch(event: string): void {
    for (const listener of this.listeners) {
      listener.playerUpdate(this, event, null);
    }
  }
}

/** Playback may be blocked by the autoplay policy; rejections are expected. */
function ignoreRejection(promise: Promise<void>): void {
  promise.catch(() => {});
}

const pendingUnlocks: (() => void)[] = [];
/** A game that keeps retrying while audio is locked must not queue forever. */
const MAX_PENDING_UNLOCKS = 8;
let unlockInstalled = false;

/** Runs {@code action} on the next user gesture (autoplay policy). */
function unlockAudio(action: () => void): void {
  if (pendingUnlocks.length >= MAX_PENDING_UNLOCKS) {
    pendingUnlocks.shift();
  }
  pendingUnlocks.push(action);
  if (unlockInstalled) {
    return;
  }
  unlockInstalled = true;
  const run = (): void => {
    for (const pending of pendingUnlocks.splice(0)) {
      pending();
    }
  };
  for (const type of ['pointerdown', 'keydown', 'touchstart']) {
    window.addEventListener(type, run, { once: true, capture: true });
  }
}

/** MIDP {@code Manager}: turns an encoded asset into a player. */
export class Manager {
  static readonly TONE_DEVICE_LOCATOR = 'device://tone';

  static createPlayer(data: Int8Array | { data: Int8Array }, type?: string | null): AudioPlayer {
    const bytes = data instanceof Int8Array ? data : data.data;
    const format = type == null ? Manager.sniff(bytes) : Manager.normalize(type);
    if (format === null) {
      throw new MediaException(`unsupported content type: ${type}`);
    }
    const url = URL.createObjectURL(new Blob([bytes as unknown as BlobPart], { type: format }));
    return new AudioPlayer({ url, contentType: format });
  }

  private static normalize(type: string): string | null {
    let base = type.trim().toLowerCase();
    const semicolon = base.indexOf(';');
    if (semicolon >= 0) {
      base = base.substring(0, semicolon).trim();
    }
    switch (base) {
      case 'audio/ogg':
      case 'audio/vorbis':
      case 'application/ogg':
        return 'audio/ogg';
      case 'audio/midi':
      case 'audio/x-midi':
      case 'audio/mid':
      case 'audio/sp-midi':
        return 'audio/ogg';
      case 'audio/amr':
      case 'audio/3gpp':
        return 'audio/ogg';
      case 'audio/x-wav':
      case 'audio/wav':
      case 'audio/wave':
      case 'audio/x-pn-wav':
        return 'audio/ogg';
      default:
        return null;
    }
  }

  private static sniff(data: Int8Array): string | null {
    if (data.length >= 4 && data[0] === 0x4f && data[1] === 0x67 && data[2] === 0x67 && data[3] === 0x53) {
      return 'audio/ogg';
    }
    return null;
  }
}
