/**
 * MIDP {@code Font}.
 *
 * The game renders its own bitmap fonts, so only the metrics the SDK queries
 * have to be available. They are measured with the browser's text engine so the
 * numbers stay consistent with what {@link Graphics#drawString} draws.
 */

let scratch: CanvasRenderingContext2D | null = null;

function scratchContext(): CanvasRenderingContext2D {
  if (!scratch) {
    const canvas = document.createElement('canvas');
    canvas.width = 8;
    canvas.height = 8;
    scratch = canvas.getContext('2d')!;
  }
  return scratch;
}

export class Font {
  static readonly FACE_SYSTEM = 0;
  static readonly FACE_MONOSPACE = 32;
  static readonly FACE_PROPORTIONAL = 64;

  static readonly STYLE_PLAIN = 0;
  static readonly STYLE_BOLD = 1;
  static readonly STYLE_ITALIC = 2;
  static readonly STYLE_UNDERLINED = 4;

  static readonly SIZE_SMALL = 8;
  static readonly SIZE_MEDIUM = 0;
  static readonly SIZE_LARGE = 16;

  private static readonly DEFAULT = new Font(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

  readonly face: number;
  readonly style: number;
  readonly size: number;
  private readonly pixelSize: number;
  private readonly family: string;
  private readonly css: string;

  private constructor(face: number, style: number, size: number) {
    this.face = face;
    this.style = style;
    this.size = size;
    this.pixelSize = size === Font.SIZE_SMALL ? 9 : size === Font.SIZE_LARGE ? 16 : 12;
    this.family = face === Font.FACE_MONOSPACE ? 'monospace' : 'sans-serif';
    const weight = (style & Font.STYLE_BOLD) !== 0 ? 'bold ' : '';
    const slant = (style & Font.STYLE_ITALIC) !== 0 ? 'italic ' : '';
    this.css = `${slant}${weight}${this.pixelSize}px ${this.family}`;
  }

  static getFont(face: number, style: number, size: number): Font {
    return new Font(face, style, size);
  }

  static getDefaultFont(): Font {
    return Font.DEFAULT;
  }

  getFace(): number {
    return this.face;
  }

  getStyle(): number {
    return this.style;
  }

  getSize(): number {
    return this.size;
  }

  isPlain(): boolean {
    return this.style === Font.STYLE_PLAIN;
  }

  isBold(): boolean {
    return (this.style & Font.STYLE_BOLD) !== 0;
  }

  isItalic(): boolean {
    return (this.style & Font.STYLE_ITALIC) !== 0;
  }

  isUnderlined(): boolean {
    return (this.style & Font.STYLE_UNDERLINED) !== 0;
  }

  /** The canvas {@code font} value for this face. */
  toCss(): string {
    return this.css;
  }

  private metrics(): TextMetrics {
    const context = scratchContext();
    context.font = this.css;
    return context.measureText('Mg');
  }

  getHeight(): number {
    const metrics = this.metrics();
    const ascent = metrics.fontBoundingBoxAscent ?? this.pixelSize;
    const descent = metrics.fontBoundingBoxDescent ?? Math.round(this.pixelSize / 4);
    return Math.round(ascent + descent);
  }

  getBaselinePosition(): number {
    const metrics = this.metrics();
    return Math.round(metrics.fontBoundingBoxAscent ?? this.pixelSize);
  }

  charWidth(character: number): number {
    return this.stringWidth(String.fromCharCode(character));
  }

  charsWidth(characters: ArrayLike<number>, offset: number, length: number): number {
    let text = '';
    for (let i = 0; i < length; i++) {
      text += String.fromCharCode(characters[offset + i]);
    }
    return this.stringWidth(text);
  }

  stringWidth(text: string | null): number {
    if (text === null) {
      return 0;
    }
    const context = scratchContext();
    context.font = this.css;
    return Math.round(context.measureText(text).width);
  }

  substringWidth(text: string | null, offset: number, length: number): number {
    if (text === null) {
      return 0;
    }
    const end = Math.min(text.length, offset + length);
    return offset >= end ? 0 : this.stringWidth(text.substring(offset, end));
  }
}
