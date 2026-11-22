/**
 * MIDP {@code Graphics} backed by a 2D canvas context.
 *
 * All coordinates are absolute (the game never translates a Graphics), so the
 * clip is kept as a device space rectangle and applied by the primitives.
 */
import { Font } from './font';
import type { Image } from './image';

export class Graphics {
  static readonly HCENTER = 1;
  static readonly VCENTER = 2;
  static readonly LEFT = 4;
  static readonly RIGHT = 8;
  static readonly TOP = 16;
  static readonly BOTTOM = 32;
  static readonly BASELINE = 64;

  static readonly SOLID = 0;
  static readonly DOTTED = 1;

  static readonly TRANS_NONE = 0;
  static readonly TRANS_MIRROR_ROT180 = 1;
  static readonly TRANS_MIRROR = 2;
  static readonly TRANS_ROT180 = 3;
  static readonly TRANS_MIRROR_ROT270 = 4;
  static readonly TRANS_ROT90 = 5;
  static readonly TRANS_ROT270 = 6;
  static readonly TRANS_MIRROR_ROT90 = 7;

  private readonly ctx: CanvasRenderingContext2D;
  private readonly width: number;
  private readonly height: number;

  private color = 0;
  private fill = '#000000';
  /** Value currently installed in {@code ctx.fillStyle}; null after a restore. */
  private appliedFill: string | null = null;
  private font: Font = Font.getDefaultFont();
  private strokeStyle = Graphics.SOLID;

  private clipX = 0;
  private clipY = 0;
  private clipWidth: number;
  private clipHeight: number;

  /** Result of the last {@link intersect}, reused to keep drawing allocation free. */
  private clippedX = 0;
  private clippedY = 0;
  private clippedWidth = 0;
  private clippedHeight = 0;

  constructor(ctx: CanvasRenderingContext2D, width: number, height: number) {
    this.ctx = ctx;
    this.width = width;
    this.height = height;
    this.clipWidth = width;
    this.clipHeight = height;
    ctx.imageSmoothingEnabled = false;
    ctx.globalAlpha = 1;
    ctx.globalCompositeOperation = 'source-over';
    this.setColor(0);
  }

  setColor(rgb: number): void;
  setColor(red: number, green: number, blue: number): void;
  setColor(red: number, green?: number, blue?: number): void {
    if (green === undefined) {
      this.color = red & 0x00ffffff;
    } else {
      this.color = (clampByte(red) << 16) | (clampByte(green) << 8) | clampByte(blue as number);
    }
    this.fill = `#${this.color.toString(16).padStart(6, '0')}`;
  }

  /**
   * Prepares the instance for a frame: the default state is restored so the
   * previous frame's clip and colour cannot leak into the next one.
   */
  begin(): Graphics {
    this.color = 0;
    this.fill = '#000000';
    this.appliedFill = null;
    this.strokeStyle = Graphics.SOLID;
    this.clipX = 0;
    this.clipY = 0;
    this.clipWidth = this.width;
    this.clipHeight = this.height;
    return this;
  }

  /** Assigns {@code fillStyle} only when it actually changes. */
  private applyFill(style: string = this.fill): void {
    if (this.appliedFill !== style) {
      this.ctx.fillStyle = style;
      this.appliedFill = style;
    }
  }

  /** The state stack was popped, so the canvas may hold an older fill style. */
  private afterRestore(): void {
    this.appliedFill = null;
    this.ctx.restore();
  }

  getColor(): number {
    return this.color;
  }

  setGrayScale(value: number): void {
    this.setColor(value, value, value);
  }

  getGrayScale(): number {
    return (this.color >> 16) & 0xff;
  }

  setFont(font: Font | null): void {
    this.font = font ?? Font.getDefaultFont();
  }

  getFont(): Font {
    return this.font;
  }

  setStrokeStyle(style: number): void {
    this.strokeStyle = style;
  }

  getStrokeStyle(): number {
    return this.strokeStyle;
  }

  setClip(x: number, y: number, width: number, height: number): void {
    const left = Math.max(0, x);
    const top = Math.max(0, y);
    const right = Math.min(this.width, x + width);
    const bottom = Math.min(this.height, y + height);
    this.clipX = left;
    this.clipY = top;
    this.clipWidth = Math.max(0, right - left);
    this.clipHeight = Math.max(0, bottom - top);
  }

  clipRect(x: number, y: number, width: number, height: number): void {
    const left = Math.max(this.clipX, x);
    const top = Math.max(this.clipY, y);
    const right = Math.min(this.clipX + this.clipWidth, x + width);
    const bottom = Math.min(this.clipY + this.clipHeight, y + height);
    this.clipX = left;
    this.clipY = top;
    this.clipWidth = Math.max(0, right - left);
    this.clipHeight = Math.max(0, bottom - top);
  }

  getClipX(): number {
    return this.clipX;
  }

  getClipY(): number {
    return this.clipY;
  }

  getClipWidth(): number {
    return this.clipWidth;
  }

  getClipHeight(): number {
    return this.clipHeight;
  }

  getWidth(): number {
    return this.width;
  }

  getHeight(): number {
    return this.height;
  }

  fillRect(x: number, y: number, width: number, height: number): void {
    if (!this.intersect(x, y, width, height)) {
      return;
    }
    this.applyFill();
    this.ctx.fillRect(this.clippedX, this.clippedY, this.clippedWidth, this.clippedHeight);
  }

  drawRect(x: number, y: number, width: number, height: number): void {
    const right = x + width - 1;
    const bottom = y + height - 1;
    this.drawLine(x, y, right, y);
    this.drawLine(x, bottom, right, bottom);
    this.drawLine(x, y, x, bottom);
    this.drawLine(right, y, right, bottom);
  }

  drawLine(x1: number, y1: number, x2: number, y2: number): void {
    const ctx = this.ctx;
    this.applyFill();
    const clipRight = this.clipX + this.clipWidth;
    const clipBottom = this.clipY + this.clipHeight;
    const dashed = this.strokeStyle === Graphics.DOTTED;
    const dx = Math.abs(x2 - x1);
    const dy = -Math.abs(y2 - y1);
    const stepX = x1 < x2 ? 1 : -1;
    const stepY = y1 < y2 ? 1 : -1;
    let error = dx + dy;
    let x = x1;
    let y = y1;
    let index = 0;
    for (;;) {
      if (x >= this.clipX && x < clipRight && y >= this.clipY && y < clipBottom && (!dashed || (index & 1) === 0)) {
        ctx.fillRect(x, y, 1, 1);
      }
      if (x === x2 && y === y2) {
        break;
      }
      const doubled = error * 2;
      if (doubled >= dy) {
        error += dy;
        x += stepX;
      }
      if (doubled <= dx) {
        error += dx;
        y += stepY;
      }
      index++;
    }
  }

  fillArc(x: number, y: number, width: number, height: number, startAngle: number, arcAngle: number): void {
    this.withArc(x, y, width, height, startAngle, arcAngle, false);
  }

  drawArc(x: number, y: number, width: number, height: number, startAngle: number, arcAngle: number): void {
    this.withArc(x, y, width, height, startAngle, arcAngle, true);
  }

  private withArc(
    x: number,
    y: number,
    width: number,
    height: number,
    startAngle: number,
    arcAngle: number,
    strokeOnly: boolean,
  ): void {
    const ctx = this.ctx;
    const radiusX = width / 2;
    const radiusY = height / 2;
    const centerX = x + radiusX;
    const centerY = y + radiusY;
    const start = (-startAngle * Math.PI) / 180;
    const end = (-(startAngle + arcAngle) * Math.PI) / 180;
    ctx.save();
    try {
      this.applyClip();
      this.applyFill();
      ctx.strokeStyle = this.fill;
      ctx.lineWidth = 1;
      ctx.beginPath();
      if (!strokeOnly) {
        ctx.moveTo(centerX, centerY);
      }
      ctx.ellipse(centerX, centerY, Math.max(0, radiusX), Math.max(0, radiusY), 0, start, end, arcAngle > 0);
      if (!strokeOnly) {
        ctx.closePath();
        ctx.fill();
      } else {
        ctx.stroke();
      }
    } finally {
      this.afterRestore();
    }
  }

  fillRoundRect(x: number, y: number, width: number, height: number, arcWidth: number, arcHeight: number): void {
    const ctx = this.ctx;
    ctx.save();
    try {
      this.applyClip();
      this.applyFill();
      ctx.beginPath();
      roundedRectPath(ctx, x, y, width, height, Math.min(arcWidth, arcHeight) / 2);
      ctx.fill();
    } finally {
      this.afterRestore();
    }
  }

  drawRoundRect(x: number, y: number, width: number, height: number, arcWidth: number, arcHeight: number): void {
    const ctx = this.ctx;
    ctx.save();
    try {
      this.applyClip();
      ctx.strokeStyle = this.fill;
      ctx.lineWidth = 1;
      ctx.beginPath();
      roundedRectPath(ctx, x + 0.5, y + 0.5, width - 1, height - 1, Math.min(arcWidth, arcHeight) / 2);
      ctx.stroke();
    } finally {
      this.afterRestore();
    }
  }

  fillTriangle(x1: number, y1: number, x2: number, y2: number, x3: number, y3: number): void {
    const ctx = this.ctx;
    ctx.save();
    try {
      this.applyClip();
      this.applyFill();
      ctx.beginPath();
      ctx.moveTo(x1, y1);
      ctx.lineTo(x2, y2);
      ctx.lineTo(x3, y3);
      ctx.closePath();
      ctx.fill();
    } finally {
      this.afterRestore();
    }
  }

  drawImage(image: Image | null, x: number, y: number, anchor: number): void {
    if (image === null) {
      return;
    }
    const dx = anchorX(x, image.width, anchor);
    const dy = anchorY(y, image.height, anchor);
    if (!this.intersect(dx, dy, image.width, image.height)) {
      return;
    }
    this.ctx.drawImage(
      image.source,
      this.clippedX - dx,
      this.clippedY - dy,
      this.clippedWidth,
      this.clippedHeight,
      this.clippedX,
      this.clippedY,
      this.clippedWidth,
      this.clippedHeight,
    );
  }

  drawRegion(
    src: Image | null,
    xSrc: number,
    ySrc: number,
    width: number,
    height: number,
    transform: number,
    xDest: number,
    yDest: number,
    anchor: number,
  ): void {
    if (src === null || width <= 0 || height <= 0) {
      return;
    }
    const swap =
      transform === Graphics.TRANS_ROT90 ||
      transform === Graphics.TRANS_ROT270 ||
      transform === Graphics.TRANS_MIRROR_ROT90 ||
      transform === Graphics.TRANS_MIRROR_ROT270;
    const dstWidth = swap ? height : width;
    const dstHeight = swap ? width : height;
    const dx = anchorX(xDest, dstWidth, anchor);
    const dy = anchorY(yDest, dstHeight, anchor);

    let m00 = 1;
    let m01 = 0;
    let m02 = -xSrc;
    let m10 = 0;
    let m11 = 1;
    let m12 = -ySrc;
    switch (transform) {
      case Graphics.TRANS_NONE:
        break;
      case Graphics.TRANS_MIRROR:
        m00 = -1;
        m02 = xSrc + width;
        break;
      case Graphics.TRANS_ROT180:
        m00 = -1;
        m02 = xSrc + width;
        m11 = -1;
        m12 = ySrc + height;
        break;
      case Graphics.TRANS_MIRROR_ROT180:
        m11 = -1;
        m12 = ySrc + height;
        break;
      case Graphics.TRANS_ROT90:
        m00 = 0;
        m01 = -1;
        m02 = ySrc + height;
        m10 = 1;
        m11 = 0;
        m12 = -xSrc;
        break;
      case Graphics.TRANS_ROT270:
        m00 = 0;
        m01 = 1;
        m02 = -ySrc;
        m10 = -1;
        m11 = 0;
        m12 = xSrc + width;
        break;
      case Graphics.TRANS_MIRROR_ROT90:
        m00 = 0;
        m01 = -1;
        m02 = ySrc + height;
        m10 = -1;
        m11 = 0;
        m12 = xSrc + width;
        break;
      case Graphics.TRANS_MIRROR_ROT270:
        m00 = 0;
        m01 = 1;
        m02 = -ySrc;
        m10 = 1;
        m11 = 0;
        m12 = -xSrc;
        break;
      default:
        return;
    }

    // One clip instead of two, and sprites that are off screen are skipped
    // outright: the isometric lot draws hundreds of them every frame.
    if (!this.intersect(dx, dy, dstWidth, dstHeight)) {
      return;
    }
    // A sprite that lands entirely inside the clip needs no clip at all, which is
    // the common case and saves a rectangle plus a rasteriser pass per sprite.
    const whole = this.clippedWidth === dstWidth && this.clippedHeight === dstHeight;
    const ctx = this.ctx;
    ctx.save();
    try {
      if (!whole) {
        ctx.beginPath();
        ctx.rect(this.clippedX, this.clippedY, this.clippedWidth, this.clippedHeight);
        ctx.clip();
      }
      ctx.translate(dx, dy);
      ctx.transform(m00, m10, m01, m11, m02, m12);
      // The matrix maps source coordinates into destination space, so the region is
      // drawn back into its own source rectangle (the 9 argument overload).
      ctx.drawImage(src.source, xSrc, ySrc, width, height, xSrc, ySrc, width, height);
    } finally {
      this.afterRestore();
    }
  }

  drawRGB(
    rgbData: Int32Array,
    offset: number,
    scanlength: number,
    x: number,
    y: number,
    width: number,
    height: number,
    processAlpha: boolean,
  ): void {
    if (width <= 0 || height <= 0) {
      return;
    }
    const imageData = new ImageData(width, height);
    const pixels = imageData.data;
    for (let row = 0; row < height; row++) {
      for (let column = 0; column < width; column++) {
        const value = rgbData[offset + row * scanlength + column];
        const target = (row * width + column) * 4;
        pixels[target] = (value >> 16) & 0xff;
        pixels[target + 1] = (value >> 8) & 0xff;
        pixels[target + 2] = value & 0xff;
        pixels[target + 3] = processAlpha ? (value >>> 24) & 0xff : 255;
      }
    }
    const scratch = document.createElement('canvas');
    scratch.width = width;
    scratch.height = height;
    scratch.getContext('2d')!.putImageData(imageData, 0, 0);
    if (!this.intersect(x, y, width, height)) {
      return;
    }
    this.ctx.drawImage(
      scratch,
      this.clippedX - x,
      this.clippedY - y,
      this.clippedWidth,
      this.clippedHeight,
      this.clippedX,
      this.clippedY,
      this.clippedWidth,
      this.clippedHeight,
    );
  }

  drawString(text: string | null, x: number, y: number, anchor: number): void {
    if (text === null) {
      return;
    }
    const ctx = this.ctx;
    const textWidth = this.font.stringWidth(text);
    const ascent = this.font.getBaselinePosition();
    const dx = anchorX(x, textWidth, anchor);
    const dy = y + ascent;
    ctx.save();
    try {
      this.applyClip();
      ctx.font = this.font.toCss();
      this.appliedFill = null;
      ctx.fillStyle = this.fill;
      ctx.textBaseline = 'alphabetic';
      ctx.fillText(text, dx, dy);
    } finally {
      this.afterRestore();
    }
  }

  drawChar(character: number, x: number, y: number, anchor: number): void {
    this.drawString(String.fromCharCode(character), x, y, anchor);
  }

  drawSubstring(text: string | null, offset: number, length: number, x: number, y: number, anchor: number): void {
    if (text === null) {
      return;
    }
    const end = Math.min(text.length, offset + length);
    if (offset >= end) {
      return;
    }
    this.drawString(text.substring(offset, end), x, y, anchor);
  }

  copyArea(xSrc: number, ySrc: number, width: number, height: number, xDest: number, yDest: number, anchor: number): void {
    const dx = anchorX(xDest, width, anchor);
    const dy = anchorY(yDest, height, anchor);
    if (dx === xSrc && dy === ySrc) {
      return;
    }
    const snapshot = document.createElement('canvas');
    snapshot.width = Math.max(1, width);
    snapshot.height = Math.max(1, height);
    snapshot.getContext('2d')!.drawImage(this.ctx.canvas, xSrc, ySrc, width, height, 0, 0, width, height);
    this.ctx.drawImage(snapshot, 0, 0, width, height, dx, dy, width, height);
  }

  /** Not part of MIDP: used by the screen fade to paint a translucent colour. */
  fillRectWithAlpha(argb: number, x: number, y: number, width: number, height: number): void {
    const alpha = (argb >>> 24) & 0xff;
    if (alpha >= 255) {
      this.setColor(argb & 0xffffff);
      this.fillRect(x, y, width, height);
      return;
    }
    if (!this.intersect(x, y, width, height)) {
      return;
    }
    const ctx = this.ctx;
    const savedAlpha = ctx.globalAlpha;
    ctx.globalAlpha = alpha / 255;
    ctx.fillStyle = `#${(argb & 0xffffff).toString(16).padStart(6, '0')}`;
    ctx.fillRect(this.clippedX, this.clippedY, this.clippedWidth, this.clippedHeight);
    ctx.globalAlpha = savedAlpha;
  }

  private applyClip(): void {
    this.ctx.beginPath();
    this.ctx.rect(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
    this.ctx.clip();
  }

  /**
   * Intersects a rectangle with the current clip. The result is left in the
   * {@code clipped*} fields instead of a fresh array, because the game draws
   * hundreds of sprites per frame and a tuple each time is pure garbage.
   */
  private intersect(x: number, y: number, width: number, height: number): boolean {
    const left = Math.max(x, this.clipX);
    const top = Math.max(y, this.clipY);
    const right = Math.min(x + width, this.clipX + this.clipWidth);
    const bottom = Math.min(y + height, this.clipY + this.clipHeight);
    if (right <= left || bottom <= top) {
      return false;
    }
    this.clippedX = left;
    this.clippedY = top;
    this.clippedWidth = right - left;
    this.clippedHeight = bottom - top;
    return true;
  }
}

/** Rounded rectangle path; {@code ctx.roundRect} does not exist on KaiOS. */
function roundedRectPath(
  ctx: CanvasRenderingContext2D,
  x: number,
  y: number,
  width: number,
  height: number,
  radius: number,
): void {
  const r = Math.max(0, Math.min(radius, width / 2, height / 2));
  ctx.moveTo(x + r, y);
  ctx.lineTo(x + width - r, y);
  ctx.quadraticCurveTo(x + width, y, x + width, y + r);
  ctx.lineTo(x + width, y + height - r);
  ctx.quadraticCurveTo(x + width, y + height, x + width - r, y + height);
  ctx.lineTo(x + r, y + height);
  ctx.quadraticCurveTo(x, y + height, x, y + height - r);
  ctx.lineTo(x, y + r);
  ctx.quadraticCurveTo(x, y, x + r, y);
  ctx.closePath();
}

function anchorX(x: number, width: number, anchor: number): number {
  if ((anchor & Graphics.RIGHT) !== 0) {
    return x - width;
  }
  if ((anchor & Graphics.HCENTER) !== 0) {
    return x - (width >> 1);
  }
  return x;
}

function anchorY(y: number, height: number, anchor: number): number {
  if ((anchor & Graphics.BOTTOM) !== 0) {
    return y - height;
  }
  if ((anchor & Graphics.VCENTER) !== 0) {
    return y - (height >> 1);
  }
  return y;
}

function clampByte(value: number): number {
  return value < 0 ? 0 : value > 255 ? 255 : value;
}
