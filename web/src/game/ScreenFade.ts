/**
 * Screen fade, hand written.
 *
 * The Java build draws this through the M3G shim (a sprite covered by a parallel
 * camera). That shim reduces to a single alpha blended rectangle, which is
 * reproduced here without the 3D machinery.
 */
import { Graphics } from '../runtime';

export class ScreenFade {
  private viewportX = 0;
  private viewportY = 0;
  private width = 1;
  private height = 1;
  private blending = 68; // CompositingMode.REPLACE
  private red = 0;
  private green = 0;
  private blue = 0;
  private alpha = 0;

  setColor(rgb: number): void {
    this.red = (rgb >> 16) & 0xff;
    this.green = (rgb >> 8) & 0xff;
    this.blue = rgb & 0xff;
    this.alpha = ((rgb & 0xff000000) >> 24) & 0xff;
  }

  setBlending(blending: number): void {
    this.blending = blending;
  }

  setViewport(originX: number, originY: number, viewportWidth: number, viewportHeight: number): void {
    this.viewportX = originX;
    this.viewportY = originY;
    this.width = viewportWidth;
    this.height = viewportHeight;
  }

  render(gfx: Graphics): void {
    const argb = (this.alpha << 24) | (this.red << 16) | (this.green << 8) | this.blue;
    let color: number;
    if (this.blending === 64 || this.blending === 65) {
      // ALPHA, ALPHA_ADD
      color = argb;
    } else if (this.blending === 66 || this.blending === 67) {
      // MODULATE, MODULATE_X2
      let alpha = (argb >>> 24) & 0xff;
      if (this.blending === 67) {
        alpha = Math.min(255, alpha * 2);
      }
      color = (alpha << 24) | (argb & 0xffffff);
    } else {
      color = 0xff000000 | (argb & 0xffffff);
    }
    gfx.fillRectWithAlpha(color, this.viewportX, this.viewportY, this.width, this.height);
  }
}
