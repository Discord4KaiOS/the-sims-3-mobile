/**
 * PNG support on top of the browser's own image decoder.
 *
 * The game decodes its sprite sheets while it loads, so the bytes still have to
 * be available up front: {@link preloadImages} fetches and decodes every PNG
 * once at boot and the game then gets ready made bitmaps. Decoding itself is
 * done by the browser, which is both faster than a hand written inflate and
 * keeps the exact pixels (all shipped images are palette based with binary
 * transparency, so the bitmap round trip is lossless).
 *
 * Only the header is parsed here, because the game needs the size of an image
 * (and its palette/bit depth, for the recolouring path) without waiting.
 */
export interface PngHeader {
  width: number;
  height: number;
  bitDepth: number;
  colorType: number;
  interlace: number;
}

const SIGNATURE = [137, 80, 78, 71, 13, 10, 26, 10];

function readU32(bytes: Uint8Array, offset: number): number {
  return ((bytes[offset] << 24) | (bytes[offset + 1] << 16) | (bytes[offset + 2] << 8) | bytes[offset + 3]) >>> 0;
}

function asBytes(source: Uint8Array | Int8Array): Uint8Array {
  return source instanceof Uint8Array
    ? source
    : new Uint8Array(source.buffer, source.byteOffset, source.byteLength);
}

export function isPng(source: Uint8Array | Int8Array): boolean {
  if (source.length < SIGNATURE.length) {
    return false;
  }
  const bytes = asBytes(source);
  for (let i = 0; i < SIGNATURE.length; i++) {
    if (bytes[i] !== SIGNATURE[i]) {
      return false;
    }
  }
  return true;
}

/** Reads the {@code IHDR} chunk, or null when the bytes are not a PNG. */
export function parsePngHeader(source: Uint8Array | Int8Array): PngHeader | null {
  if (!isPng(source)) {
    return null;
  }
  const bytes = asBytes(source);
  let offset = SIGNATURE.length;
  while (offset + 8 <= bytes.length) {
    const length = readU32(bytes, offset);
    const type = String.fromCharCode(bytes[offset + 4], bytes[offset + 5], bytes[offset + 6], bytes[offset + 7]);
    if (type === 'IHDR') {
      return {
        width: readU32(bytes, offset + 8),
        height: readU32(bytes, offset + 12),
        bitDepth: bytes[offset + 16],
        colorType: bytes[offset + 17],
        interlace: bytes[offset + 20],
      };
    }
    if (type === 'IEND') {
      break;
    }
    offset += 12 + length;
  }
  return null;
}

/** The colour table of a palette image, needed to recolor decoded pixels. */
export interface PngPalette {
  /** Packed {@code 0xRRGGBB} entries, in palette order. */
  colors: Int32Array;
  /** Per entry alpha from {@code tRNS}, or null when every entry is opaque. */
  transparency: Uint8Array | null;
}

/** Reads the {@code PLTE}/{@code tRNS} chunks; null when the image is not palette based. */
export function parsePngPalette(source: Uint8Array | Int8Array): PngPalette | null {
  const header = parsePngHeader(source);
  if (header === null || header.colorType !== 3) {
    return null;
  }
  const bytes = asBytes(source);
  let colors: Int32Array | null = null;
  let transparency: Uint8Array | null = null;
  let offset = SIGNATURE.length;
  while (offset + 8 <= bytes.length) {
    const length = readU32(bytes, offset);
    const type = String.fromCharCode(bytes[offset + 4], bytes[offset + 5], bytes[offset + 6], bytes[offset + 7]);
    const data = offset + 8;
    if (type === 'PLTE') {
      const count = Math.floor(length / 3);
      colors = new Int32Array(count);
      for (let i = 0; i < count; i++) {
        colors[i] = (bytes[data + i * 3] << 16) | (bytes[data + i * 3 + 1] << 8) | bytes[data + i * 3 + 2];
      }
    } else if (type === 'tRNS') {
      transparency = bytes.slice(data, data + length);
    } else if (type === 'IEND') {
      break;
    }
    offset += 12 + length;
  }
  return colors === null ? null : { colors, transparency };
}

/** Decodes a PNG with the browser's decoder, keeping straight alpha. */
export async function decodePng(source: Uint8Array | Int8Array): Promise<ImageBitmap> {
  const bytes = asBytes(source);
  const blob = new Blob([bytes.slice().buffer as ArrayBuffer], { type: 'image/png' });
  // KaiOS only implements the one argument overload; the options dictionary is not
  // needed anyway, because reading the bitmap back through a canvas always yields
  // straight (unpremultiplied) pixels.
  return createImageBitmap(blob);
}
