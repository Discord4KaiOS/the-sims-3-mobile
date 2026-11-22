// Generated from SdkSound.java by tools/web/JavaToTs.java; keep this file in sync.
import { Player, Display, MediaException, IllegalStateException, SecurityException, VolumeControl, ByteArrayInputStream, Manager } from '../runtime';
import { SdkConfig } from './SdkConfig';
import { ResourceLoader } from './ResourceLoader';
import { SdkFeatures } from './SdkFeatures';
import { SdkText } from './SdkText';
import { GameCanvas } from './GameCanvas';

export class SdkSound {
	static targetFps: number = SdkConfig.targetFps();
	isWavFormat: boolean = false;
	currentSound: number = -1;
	loopCount: number = 1;
	currentLoop: number = 0;
	enabled: boolean = true;
	player: Player = null!;
	resources: ResourceLoader = null;
	display: Display = null!;
	static muted: boolean = false;
	pendingSound: number = -2;
	setLoopCount(count: number): void {
		if (SdkFeatures.isLoggingEnabled() && count < 1 && count !== -1) {
			SdkText.log("SDKSound::setCurrentLoop::count: " + count + " will result in undefined behavior", 4);
		}
		this.loopCount = count;
	}
	play(soundId: number): void {
		if (this.currentSound !== soundId) {
			let data: Int8Array = this.soundBytes(soundId);
			if (this.player !== null) {
				this.player.removePlayerListener(this);
				this.player.close();
			}
			this.player = this.createPlayer(data);
			try {
				this.player.realize();
				this.player.prefetch();
				this.player.addPlayerListener(this);
			}
			catch (mediaError) {
				if (mediaError instanceof MediaException) {
				}
				else if (mediaError instanceof IllegalStateException) {
				}
				else if (mediaError instanceof SecurityException) {
				}
				else {
					throw mediaError;
				}
			}
		}
	}
	stopAll(): void {
		SdkSound.muteAll();
	}
	playIfEnabled(soundId: number): void {
		try {
			if (this.enabled) {
				if (this.isFirstLoop() && this.currentLoop === 1) {
					return;
				}
				this.play(soundId);
				this.startPlayer(soundId);
			}
		}
		catch (exception) {
			console.error(exception);
		}
	}
	startPlayer(soundId: number): void {
		if (this.player !== null) {
			try {
				this.player.setLoopCount(this.loopCount);
				this.player.setMediaTime(0);
			}
			catch (ignored) {
			}
			try {
				let volume: VolumeControl = null!;
				if (this.player !== null && (volume = (this.player.getControl("VolumeControl") as unknown as VolumeControl)) !== null) {
					volume.setLevel(50);
				}
				this.player.start();
				this.currentSound = soundId;
				return;
			}
			catch (ignored2) {
			}
		}
	}
	static muteAll(): void {
		SdkSound.muted = true;
	}
	stopCurrent(): void {
		this.stop();
	}
	stop(): void {
		if (this.currentSound !== -1 && this.player !== null) {
			try {
				if (this.player.getState() === 400) {
					this.player.stop();
				}
				this.player.getState();
			}
			catch (exception) {
				console.error(exception);
				return;
			}
			this.currentSound = -1;
		}
	}
	vibrate(duration: number): void {
		duration = ((Math.imul(duration, SdkConfig.vibrateScaleDen()) / SdkConfig.vibrateScaleNum()) | 0);
		this.display = Display.getDisplay(GameCanvas.instance().app);
		this.display.vibrate(duration);
	}
	setEnabled(enable: boolean): void {
		this.enabled = enable;
		if (!enable) {
			SdkSound.muteAll();
		}
	}
	soundBytes(soundId: number): Int8Array {
		return this.resources.loadSound(soundId);
	}
	createPlayer(data: Int8Array): Player {
		// The build renders MIDI/AMR/WAV assets to Ogg Vorbis, so the magic bytes
		// the Java player sniffs for are replaced by the Ogg encapsulation header.
		let contentType: string = null;
		if (data.length >= 4 && data[0] === 79 && data[1] === 103 && data[2] === 103 && data[3] === 83) {
			contentType = "audio/ogg";
		}
		if (contentType === null) {
			return null;
		}
		else {
			let player: Player = null;
			try {
				let stream: ByteArrayInputStream = null!;
				player = Manager.createPlayer(stream = new ByteArrayInputStream(data), contentType);
				stream.close();
			}
			catch (exception) {
				console.error(exception);
			}
			return player;
		}
	}
	isFirstLoop(): boolean {
		return this.currentSound === 1;
	}
	playQueued(): void {
		if (this.pendingSound !== -2) {
			this.playIfEnabled(this.pendingSound);
			this.pendingSound = -2;
		}
	}
	update(): void {
		if (SdkSound.muted) {
			this.stopCurrent();
		}
		SdkSound.muted = false;
	}
	playerUpdate(player: Player, event: string, data: any): void {
		if (event === "endOfMedia" && this.loopCount !== -1) {
			this.currentSound = -1;
		}
	}
	constructor() {
		this.isWavFormat = true;
	}
}
