// Generated from Animator.java by tools/web/JavaToTs.java; keep this file in sync.
import { Graphics } from '../runtime';
import { SpriteRenderer } from './SpriteRenderer';

export class Animator {
	animId: number = -1;
	frame: number = 0;
	frameTime: number = 0;
	flags: number = 0;
	advance(deltaMs: number): boolean {
		if ((this.flags & 2) === 0) {
			return false;
		}
		else {
			this.frameTime = ((this.frameTime + deltaMs) | 0);
			if (this.frameTime > SpriteRenderer.frameDuration(this.animId, this.frame)) {
				if ((this.flags & 8) !== 0) {
					this.stepBackward();
					return true;
				}
				else {
					this.stepForward();
					return true;
				}
			}
			else {
				return false;
			}
		}
	}
	stepForward(): void {
		this.frameTime = ((this.frameTime - SpriteRenderer.frameDuration(this.animId, this.frame)) | 0);
		if (this.frameTime < 0) {
			this.frameTime = 0;
		}
		this.frame++;
		if (this.frame >= SpriteRenderer.frameCount(this.animId)) {
			if ((this.flags & 4) !== 0) {
				this.frame = 0;
				return;
			}
			this.frame = ((SpriteRenderer.frameCount(this.animId) - 1) | 0);
			this.flags = (this.flags & -3);
		}
	}
	stepBackward(): void {
		this.frameTime = ((this.frameTime - SpriteRenderer.frameDuration(this.animId, this.frame)) | 0);
		if (this.frameTime < 0) {
			this.frameTime = 0;
		}
		this.frame--;
		if (this.frame <= 0) {
			if ((this.flags & 4) !== 0) {
				this.frame = ((SpriteRenderer.frameCount(this.animId) - 1) | 0);
				return;
			}
			this.frame = 0;
			this.flags = (this.flags & -3);
		}
	}
	play(newAnimId: number, playFlags: number): void {
		let preserveFrame: boolean = false;
		if (!(preserveFrame = (playFlags & 16) !== 0) && this.animId === newAnimId) {
			if (this.frame >= SpriteRenderer.frameCount(this.animId)) {
				this.frame = 0;
			}
			else {
				if (this.frame < 0) {
					this.frame = ((SpriteRenderer.frameCount(this.animId) - 1) | 0);
				}
			}
		}
		else {
			this.animId = newAnimId;
			if ((this.flags & 8) !== 0) {
				this.frame = ((SpriteRenderer.frameCount(this.animId) - 1) | 0);
			}
			else {
				this.frame = 0;
			}
			this.frameTime = 0;
		}
		this.flags = 3 | playFlags & 4;
	}
	draw(gfx: Graphics, x: number, y: number): void {
		if (this.animId >= 0) {
			SpriteRenderer.drawFrame(gfx, this.animId, this.frame, x, y);
		}
	}
	frameCount(): number {
		return this.animId === -1 ? 0 : SpriteRenderer.frameCount(this.animId);
	}
	frameDuration(): number {
		return SpriteRenderer.countSpecialParts(this.animId, this.frame);
	}
	matchesFrame(hitMask: Int32Array, x: number): boolean {
		return SpriteRenderer.hitTest(hitMask, this.animId, this.frame, x);
	}
	isRunning(): boolean {
		return (this.flags & 2) !== 0;
	}
	isLooping(): boolean {
		return (this.flags & 4) !== 0;
	}
	setRunning(running: boolean): void {
		if (running) {
			this.flags = (this.flags | 2);
		}
		else {
			this.flags = (this.flags & -3);
		}
	}
	rewind(): void {
		this.frame = Math.max(0, ((this.frameCount() - 1) | 0));
		if (!this.isLooping()) {
			this.setRunning(false);
		}
	}
	constructor() {
		this.animId = -1;
		this.frame = 0;
		this.frameTime = 0;
		this.flags = 0;
	}
}
