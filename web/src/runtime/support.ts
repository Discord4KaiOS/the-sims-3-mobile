/**
 * Small helpers that stand in for the Java library calls the game uses.
 *
 * Everything here is a module export: no globals are created or modified.
 */

/** Java {@code char} values are numbers in this port; this converts to text. */
export function chr(value: number): string {
  return String.fromCharCode(value);
}

/** Java {@code System.arraycopy}, including typed array sources and targets. */
export function arraycopy(src: ArrayLike<number> | any[], srcPos: number, dst: any, dstPos: number, length: number): void {
  if (length <= 0) {
    return;
  }
  if (src === dst && srcPos === dstPos) {
    return;
  }
  if (typeof dst.set === 'function') {
    dst.set((src as any).subarray(srcPos, srcPos + length), dstPos);
    return;
  }
  if (srcPos <= dstPos) {
    for (let i = length - 1; i >= 0; i--) {
      dst[dstPos + i] = src[srcPos + i];
    }
  } else {
    for (let i = 0; i < length; i++) {
      dst[dstPos + i] = src[srcPos + i];
    }
  }
}

/** Java 8 {@code byte[]} for a string (the game only feeds ASCII through it). */
export function charsToBytes(text: string): Int8Array {
  const bytes = new Int8Array(text.length);
  for (let i = 0; i < text.length; i++) {
    bytes[i] = text.charCodeAt(i);
  }
  return bytes;
}

/** Number of set bits in a 64 bit mask. */
export function countBits(value: bigint): number {
  let bits = BigInt.asUintN(64, value);
  let count = 0;
  while (bits !== 0n) {
    bits &= bits - 1n;
    count++;
  }
  return count;
}

/** Java {@code String.compareTo}: compares UTF-16 code units. */
export function compareStrings(left: string, right: string): number {
  const length = Math.min(left.length, right.length);
  for (let i = 0; i < length; i++) {
    const a = left.charCodeAt(i);
    const b = right.charCodeAt(i);
    if (a !== b) {
      return a - b;
    }
  }
  return left.length - right.length;
}

/** Java 8 {@code new String(byte[], off, len)}. */
export function bytesToString(bytes: ArrayLike<number>, offset = 0, length = bytes.length - offset): string {
  let text = '';
  const end = offset + length;
  for (let i = offset; i < end; i++) {
    text += String.fromCharCode(bytes[i] & 0xff);
  }
  return text;
}

/** {@code System.getProperty} for the few keys the game reads. */
export function getProperty(name: string): string | null {
  switch (name) {
    case 'microedition.locale':
      return navigator.language || 'en-US';
    case 'microedition.platform':
      return 'web';
    default:
      return null;
  }
}

export function gc(): void {
  // The JavaScript engine decides when to collect.
}

let exited = false;

/**
 * {@code System.exit}: stops the update timers and tells the platform layer.
 * The game calls this on every timer tick once it has shut down, so it has to be
 * idempotent.
 */
export function exitApp(): void {
  if (exited) {
    return;
  }
  exited = true;
  for (const timer of [...liveTimers]) {
    timer.cancel();
  }
  runtimeEvents.onExit?.();
}

/** Indirection so the platform layer can react without a module cycle. */
export const runtimeEvents: { onExit?: () => void } = {};

/**
 * {@code java.util.Random}: the same 48 bit linear congruential generator, so
 * the sequence matches a device for a given seed.
 */
export class Random {
  private seed: number;

  constructor(seed?: number) {
    const initial = seed === undefined ? Math.floor(Math.random() * 0x1000000000000) : seed;
    // (seed ^ 0x5DEECE66DL) & ((1L << 48) - 1): a 35 bit constant, so the xor has
    // to be 64 bit wide.
    this.seed = Number((BigInt(Math.floor(initial)) ^ 0x5deece66dn) & 0xffffffffffffn);
  }

  private next(bits: number): number {
    // seed * 0x5deece66d + 0xb mod 2^48, computed in 16 bit limbs so the
    // intermediate products stay exact as doubles.
    const s0 = this.seed % 0x10000;
    const s1 = Math.floor(this.seed / 0x10000) % 0x10000;
    const s2 = Math.floor(this.seed / 0x100000000) % 0x10000;
    const p0 = s0 * 0xe66d + 0xb;
    const p1 = s0 * 0xdeec + s1 * 0xe66d + Math.floor(p0 / 0x10000);
    const p2 = s0 * 0x5 + s1 * 0xdeec + s2 * 0xe66d + Math.floor(p1 / 0x10000);
    this.seed = (p2 % 0x10000) * 0x100000000 + (p1 % 0x10000) * 0x10000 + (p0 % 0x10000);
    return Math.floor(this.seed / Math.pow(2, 48 - bits));
  }

  nextInt(bound?: number): number {
    if (bound === undefined) {
      // (int)next(32): the high bits can set the sign.
      return this.next(32) | 0;
    }
    if (bound <= 0) {
      throw new Error('bound must be positive');
    }
    const first = this.next(31);
    const mask = bound - 1;
    if ((bound & mask) === 0) {
      // (int)((bound * (long)first) >> 31); the product is wider than 2^53.
      return Number((BigInt(bound) * BigInt(first)) >> 31n) | 0;
    }
    // Java retries while the int sum `u - r + m` overflows past 2^31-1.
    let bits = first;
    let val: number;
    for (;;) {
      val = bits % bound;
      if (bits - val + mask <= 2147483647) {
        return val;
      }
      bits = this.next(31);
    }
  }

  nextBoolean(): boolean {
    return this.next(1) !== 0;
  }

  nextFloat(): number {
    return this.next(24) / 16777216;
  }
}

/** Timers that are still ticking, so {@link exitApp} can stop them. */
const liveTimers = new Set<Timer>();

/** {@code java.util.TimerTask}. */
export abstract class TimerTask {
  cancelRequested = false;
  abstract run(): void;
}

/**
 * {@code java.util.Timer} driven by {@code setInterval}. Only the fixed rate
 * scheduling used by the game is implemented.
 */
export class Timer {
  private handles: number[] = [];
  private cancelled = false;

  constructor() {
    liveTimers.add(this);
  }

  schedule(task: TimerTask, delay: number, period: number): void {
    if (this.cancelled) {
      throw new Error('Timer already cancelled');
    }
    let first = true;
    const h = window.setInterval(() => {
      if (this.cancelled || task.cancelRequested) {
        window.clearInterval(h);
        return;
      }
      if (first) {
        first = false;
        return;
      }
      task.run();
    }, period);
    this.handles.push(h);
    if (delay <= 0) {
      // The Java timer fires the first execution immediately.
      task.run();
    }
  }

  cancel(): void {
    this.cancelled = true;
    liveTimers.delete(this);
    for (const h of this.handles) {
      window.clearInterval(h);
    }
    this.handles.length = 0;
  }
}

/**
 * {@code java.lang.StringBuffer}, kept as an array of char codes because the
 * game rewrites characters in place while formatting numbers.
 */
export class StringBuffer {
  private chars: number[] = [];

  constructor(initial: string | number = '') {
    if (typeof initial === 'number') {
      this.chars.length = initial;
    } else {
      this.append(initial);
    }
  }

  append(value: unknown): this {
    const text = typeof value === 'string' ? value : String(value);
    for (let i = 0; i < text.length; i++) {
      this.chars.push(text.charCodeAt(i));
    }
    return this;
  }

  setCharAt(index: number, character: number): void {
    this.chars[index] = character;
  }

  charAt(index: number): number {
    return this.chars[index];
  }

  length(): number {
    return this.chars.length;
  }

  setLength(length: number): void {
    this.chars.length = length;
  }

  toString(): string {
    let text = '';
    for (let i = 0; i < this.chars.length; i++) {
      text += String.fromCharCode(this.chars[i]);
    }
    return text;
  }
}
