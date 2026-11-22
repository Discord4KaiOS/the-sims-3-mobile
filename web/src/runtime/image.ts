/**
 * MIDP {@code Image} backed by a browser bitmap.
 *
 * Decoding is asynchronous, so every PNG is fetched and decoded once at boot
 * (see {@link preloadImages}) and the game then asks for ready made images. An
 * image loaded from a resource is immutable, which is why the decoded instance
 * can be shared between callers; only {@code createImage(width, height)} and the
 * recolouring path allocate a canvas.
 *
 * Mutability is tracked because the game uses {@code getGraphics()} to build
 * recoloured masks and never draws into a loaded image.
 */
import { decodePng, parsePngHeader, parsePngPalette, type PngHeader, type PngPalette } from './png';
import { Graphics } from './graphics';

interface LoadedImage {
  image: Image;
  header: PngHeader;
  palette: PngPalette | null;
}

const loadedImages = new Map<string, LoadedImage>();

/** {@code fromColors} entries of this value are left alone by the Java code. */
const SKIP_COLOR = 0x00ff00ff;

/** Maps a resource path to the bare file name the manifest and the cache use. */
function imageKey(name: string): string {
  const slash = name.lastIndexOf('/');
  return slash < 0 ? name : name.slice(slash + 1);
}

/**
 * Fetches and decodes every PNG the game can ask for. The manifest is written by
 * scripts/copy-assets.mjs, so it lists exactly what ended up in public/game.
 */
export async function preloadImages(): Promise<void> {
  const response = await fetch('/game/png.json');
  if (!response.ok) {
    throw new Error(`image manifest missing (${response.status}); run: npm run assets`);
  }
  const names = (await response.json()) as string[];
  const queue = names.slice();
  const workers: Promise<void>[] = [];
  const worker = async (): Promise<void> => {
    for (;;) {
      const name = queue.pop();
      if (name === undefined) {
        return;
      }
      try {
        const bytes = new Uint8Array(await (await fetch(`/game/${name}`)).arrayBuffer());
        const header = parsePngHeader(bytes);
        if (header === null) {
          continue;
        }
        const bitmap = await decodePng(bytes);
        const image = new Image(bitmap, bitmap.width, bitmap.height, false);
        loadedImages.set(name, { image, header, palette: parsePngPalette(bytes) });
      } catch (error) {
        // A missing image must not stop the boot; the game treats it as absent.
        console.warn(`image failed to preload: ${name}`, error);
      }
    }
  };
  // Four at a time: enough to overlap decode with the fetch, without spiking the
  // heap on a device that only has a few megabytes to spare.
  for (let i = 0; i < 4; i++) {
    workers.push(worker());
  }
  await Promise.all(workers);
}

/** The {@code IHDR} of a preloaded image, or null when it was not a PNG. */
export function pngHeaderOf(name: string): PngHeader | null {
  return loadedImages.get(imageKey(name))?.header ?? null;
}

/** The colour table of a preloaded image, or null when it is not palette based. */
export function pngPaletteOf(name: string): PngPalette | null {
  return loadedImages.get(imageKey(name))?.palette ?? null;
}

/**
 * Replaces palette colours on already decoded pixels.
 *
 * The Java build rewrites the {@code PLTE} chunk and decodes the edited PNG
 * again; {@code ResourceLoader.replacePalette} walks the whole {@code fromColors}
 * array and, for every entry that is not the {@code 0x00FF00FF} "skip" marker,
 * replaces *every* palette entry with that colour, in order. The substitution is
 * therefore a running one over the table, which is what this reproduces before
 * mapping the decoded pixels back through the result.
 */
export function recolourImage(
  source: Image,
  fromColors: Int32Array | number[],
  toColors: Int32Array | number[],
  palette: PngPalette,
): Image {
  const canvas = createCanvas(source.width, source.height);
  // The pixels are read straight back, so ask for a CPU-side surface.
  const context = canvas.getContext('2d', { willReadFrequently: true })!;
  context.imageSmoothingEnabled = false;
  context.drawImage(source.source, 0, 0);
  if (source.width <= 0 || source.height <= 0) {
    return new Image(canvas, source.width, source.height, false);
  }

  const entries = palette.colors.length;
  const replaced = Int32Array.from(palette.colors);
  const count = Math.min(fromColors.length, toColors.length);
  for (let colorIndex = 0; colorIndex < count; colorIndex++) {
    const fromColor = fromColors[colorIndex];
    if (fromColor === SKIP_COLOR) {
      continue;
    }
    const from = fromColor & 0xffffff;
    const to = toColors[colorIndex] & 0xffffff;
    for (let entry = 0; entry < entries; entry++) {
      if (replaced[entry] === from) {
        replaced[entry] = to;
      }
    }
  }

  // Colour and alpha together identify an entry, so pixels can be mapped back to
  // the palette they came from.
  const entryOf = new Map<number, number>();
  for (let entry = 0; entry < entries; entry++) {
    const alpha = palette.transparency === null ? 255 : palette.transparency[entry] ?? 255;
    const key = palette.colors[entry] * 256 + alpha;
    if (!entryOf.has(key)) {
      entryOf.set(key, entry);
    }
  }

  const pixels = context.getImageData(0, 0, source.width, source.height);
  const data = pixels.data;
  for (let i = 0; i < data.length; i += 4) {
    const entry = entryOf.get(((data[i] << 16) | (data[i + 1] << 8) | data[i + 2]) * 256 + data[i + 3]);
    if (entry === undefined) {
      continue;
    }
    const color = replaced[entry];
    data[i] = (color >> 16) & 0xff;
    data[i + 1] = (color >> 8) & 0xff;
    data[i + 2] = color & 0xff;
  }
  context.putImageData(pixels, 0, 0);
  return new Image(canvas, source.width, source.height, false);
}

export class Image {
  /** Whatever {@code CanvasRenderingContext2D.drawImage} can take as a source. */
  readonly source: CanvasImageSource;
  readonly width: number;
  readonly height: number;
  readonly mutable: boolean;
  private canvasSource: HTMLCanvasElement | null;

  constructor(
    source: CanvasImageSource,
    width: number,
    height: number,
    mutable: boolean,
    canvas: HTMLCanvasElement | null = null,
  ) {
    this.source = source;
    this.width = width;
    this.height = height;
    this.mutable = mutable;
    this.canvasSource = canvas;
  }

  /** Pixel access for the mutable images; loaded images never need one. */
  get canvas(): HTMLCanvasElement {
    let canvas = this.canvasSource;
    if (canvas === null) {
      canvas = createCanvas(this.width, this.height);
      const context = canvas.getContext('2d')!;
      context.imageSmoothingEnabled = false;
      context.drawImage(this.source, 0, 0);
      this.canvasSource = canvas;
    }
    return canvas;
  }

  static createImage(width: number, height: number): Image;
  static createImage(name: string): Image;
  static createImage(source: Image, x: number, y: number, width: number, height: number): Image;
  static createImage(a: number | string | Image, b?: number, c?: number, d?: number, e?: number): Image {
    if (typeof a === 'number') {
      if (a <= 0 || (b as number) <= 0) {
        throw new Error(`bad size ${a}x${b}`);
      }
      // The canvas is both the draw source and the pixel store: drawing into it
      // through getGraphics() has to be visible to whoever draws the image.
      const canvas = createCanvas(a, b as number);
      return new Image(canvas, a, b as number, true, canvas);
    }
    if (typeof a === 'string') {
      const entry = loadedImages.get(imageKey(a));
      if (entry === undefined) {
        throw new Error(`image not preloaded: ${a}`);
      }
      return entry.image;
    }
    const target = createCanvas(d as number, e as number);
    const context = target.getContext('2d')!;
    context.imageSmoothingEnabled = false;
    context.drawImage(a.source, -(b as number), -(c as number));
    return new Image(target, d as number, e as number, false, target);
  }

  getGraphics(): Graphics {
    if (!this.mutable) {
      throw new Error('image is immutable');
    }
    return new Graphics(this.canvas.getContext('2d')!, this.width, this.height);
  }

  getWidth(): number {
    return this.width;
  }

  getHeight(): number {
    return this.height;
  }

  isMutable(): boolean {
    return this.mutable;
  }

  getRGB(rgbData: Int32Array, offset: number, scanlength: number, x: number, y: number, width: number, height: number): void {
    const pixels = this.canvas.getContext('2d')!.getImageData(x, y, width, height).data;
    for (let row = 0; row < height; row++) {
      for (let column = 0; column < width; column++) {
        const source = (row * width + column) * 4;
        rgbData[offset + row * scanlength + column] =
          (pixels[source + 3] << 24) | (pixels[source] << 16) | (pixels[source + 1] << 8) | pixels[source + 2];
      }
    }
  }

  setRGB(rgbData: Int32Array, offset: number, scanlength: number, x: number, y: number, width: number, height: number): void {
    const imageData = new ImageData(width, height);
    const pixels = imageData.data;
    for (let row = 0; row < height; row++) {
      for (let column = 0; column < width; column++) {
        const value = rgbData[offset + row * scanlength + column];
        const target = (row * width + column) * 4;
        pixels[target] = (value >> 16) & 0xff;
        pixels[target + 1] = (value >> 8) & 0xff;
        pixels[target + 2] = value & 0xff;
        pixels[target + 3] = (value >>> 24) & 0xff;
      }
    }
    this.canvas.getContext('2d')!.putImageData(imageData, x, y);
  }
}

export function createCanvas(width: number, height: number): HTMLCanvasElement {
  const canvas = document.createElement('canvas');
  canvas.width = Math.max(1, width);
  canvas.height = Math.max(1, height);
  return canvas;
}
