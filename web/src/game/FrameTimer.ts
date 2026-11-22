// Generated from FrameTimer.java by tools/web/JavaToTs.java; keep this file in sync.
import { TimerTask } from '../runtime';
import { GameCanvas } from './GameCanvas';

export class FrameTimer extends TimerTask {
	lastTick: number = 0;
	canvas: GameCanvas = null!;
	run(): void {
		if (!GameCanvas.isRunning(this.canvas)) {
			this.canvas.shutdown();
			this.canvas.app.destroyApp(true);
		}
		else {
			if (this.canvas.getWidth() > this.canvas.getHeight()) {
				GameCanvas.landscape = true;
				GameCanvas.currentLandscape = 1;
			}
			else {
				GameCanvas.landscape = false;
				GameCanvas.currentLandscape = 0;
			}
			if (GameCanvas.lastLandscape !== GameCanvas.currentLandscape) {
				GameCanvas.repaintTick(this.canvas);
				GameCanvas.lastLandscape = GameCanvas.currentLandscape;
			}
			if (!GameCanvas.isLandscape(this.canvas) && !GameCanvas.isUpdating(this.canvas)) {
				GameCanvas.setUpdating(this.canvas, true);
				let now: number = Date.now();
				this.canvas.advanceFrame(((now - this.lastTick) | 0));
				GameCanvas.sound(this.canvas).playQueued();
				GameCanvas.sound(this.canvas).update();
				this.lastTick = now;
				this.canvas.repaint();
			}
		}
	}
	constructor(gameCanvas: GameCanvas) {
		super();
		this.canvas = gameCanvas;
		this.lastTick = Date.now();
	}
}
