// Generated from MonkeyApp.java by tools/web/JavaToTs.java; keep this file in sync.
import { MIDlet, Display } from '../runtime';
import { GameCanvas } from './GameCanvas';

export class MonkeyApp extends MIDlet {
	started: boolean = false;
	canvas: GameCanvas = null!;
	display: Display = null!;
	startApp(): void {
		let midletCanvas: GameCanvas = this.canvas;
		this.canvas.setFullScreenMode(true);
		this.display.setCurrent(midletCanvas);
		midletCanvas.setFullScreenMode(true);
		if (!this.started) {
			midletCanvas.startGame();
			this.started = true;
			midletCanvas.startTimer();
		}
		if (midletCanvas.suspended) {
			midletCanvas.resumeGame();
		}
	}
	pauseApp(): void {
		if (this.canvas !== null) {
			this.canvas.suspendGame();
		}
	}
	destroyApp(unconditional: boolean): void {
		this.notifyDestroyed();
	}
	constructor() {
		super();
		this.started = false;
		this.display = Display.getDisplay(this);
		GameCanvas.create(this);
		this.canvas = GameCanvas.instance();
	}
}
