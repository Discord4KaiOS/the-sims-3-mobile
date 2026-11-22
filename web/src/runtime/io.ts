/**
 * The subset of {@code java.io} the game uses, plus the synchronous resource
 * loader the port relies on.
 *
 * Resources are fetched with a blocking XMLHttpRequest so that image and data
 * loading keeps the original call-and-return behaviour.
 */

export class IOException extends Error {
  constructor(message = 'I/O error') {
    super(message);
    this.name = 'IOException';
  }
}

export abstract class InputStream {
  abstract readByteAt(): number;
  abstract skip(count: number): number;
  abstract available(): number;
  abstract close(): void;

  /** Debug description used in end-of-stream errors. */
  describe(): string {
    return 'stream';
  }

  /** Java {@code InputStream.read()} returning -1 at the end of the stream. */
  read(): number;
  read(target: Int8Array, offset?: number, length?: number): number;
  read(target?: Int8Array, offset = 0, length = 0): number {
    if (target === undefined) {
      return this.readByteAt();
    }
    if (length === 0) {
      length = target.length - offset;
    }
    let count = 0;
    for (let i = 0; i < length; i++) {
      const value = this.readByteAt();
      if (value < 0) {
        return count === 0 ? -1 : count;
      }
      target[offset + i] = value;
      count++;
    }
    return count;
  }

  readAllBytes(): Int8Array {
    const chunks: number[] = [];
    for (;;) {
      const value = this.readByteAt();
      if (value < 0) {
        break;
      }
      chunks.push(value);
    }
    return Int8Array.from(chunks);
  }
}

const resourceCache = new Map<string, Int8Array>();

/** Reads a game resource synchronously, relative to the document root. */
export function loadResource(path: string): Int8Array {
  const url = path.startsWith('/') ? `/game${path}` : `/game/${path}`;
  const cached = resourceCache.get(url);
  if (cached) {
    return cached;
  }
  const request = new XMLHttpRequest();
  request.open('GET', url, false);
  request.overrideMimeType('text/plain; charset=x-user-defined');
  request.send(null);
  if (request.status !== 200 && request.status !== 0) {
    throw new IOException(`resource not found: ${url}`);
  }
  const text: string = request.responseText;
  const bytes = new Int8Array(text.length);
  for (let i = 0; i < text.length; i++) {
    bytes[i] = text.charCodeAt(i) & 0xff;
  }
  resourceCache.set(url, bytes);
  return bytes;
}

export class ByteArrayInputStream extends InputStream {
  readonly data: Int8Array;
  position: number;

  constructor(data: Int8Array, offset = 0, length = data.length - offset) {
    super();
    this.data = data.subarray(offset, offset + length);
    this.position = 0;
  }

  readByteAt(): number {
    // Unsigned, like java.io.InputStream: 0xFF must not look like end of stream.
    return this.position < this.data.length ? this.data[this.position++] & 0xff : -1;
  }

  skip(count: number): number {
    const skipped = Math.max(0, Math.min(count, this.data.length - this.position));
    this.position += skipped;
    return skipped;
  }

  available(): number {
    return this.data.length - this.position;
  }

  close(): void {}

  /** Debug description used in end-of-stream errors. */
  describe(): string {
    return `${this.position}/${this.data.length}`;
  }
}

/** Big-endian {@code DataInputStream} over an {@link InputStream}. */
export class DataInputStream {
  readonly source: InputStream;

  constructor(source: InputStream) {
    this.source = source;
  }

  private u8(): number {
    const value = this.source.readByteAt();
    if (value < 0) {
      throw new IOException(`unexpected end of stream (${this.source.describe()})`);
    }
    return value;
  }

  readByte(): number {
    const value = this.u8();
    return value >= 128 ? value - 256 : value;
  }

  readUnsignedByte(): number {
    return this.u8();
  }

  readShort(): number {
    const value = (this.u8() << 8) | this.u8();
    return value >= 0x8000 ? value - 0x10000 : value;
  }

  readUnsignedShort(): number {
    return (this.u8() << 8) | this.u8();
  }

  readChar(): number {
    return (this.u8() << 8) | this.u8();
  }

  readInt(): number {
    return ((this.u8() << 24) | (this.u8() << 16) | (this.u8() << 8) | this.u8()) | 0;
  }

  /** Java {@code long}; the game only stores small counters in these. */
  readLong(): number {
    return Number(this.readLong64());
  }

  /** Java {@code long} as an exact BigInt (the progress flags need all 64 bits). */
  readLong64(): bigint {
    let value = 0n;
    for (let i = 0; i < 8; i++) {
      value = (value << 8n) | BigInt(this.u8());
    }
    return BigInt.asIntN(64, value);
  }

  readBoolean(): boolean {
    return this.u8() !== 0;
  }

  readFully(target: Int8Array, offset = 0, length = target.length - offset): void {
    for (let i = 0; i < length; i++) {
      target[offset + i] = this.readByte();
    }
  }

  read(target: Int8Array, offset = 0, length = target.length - offset): number {
    for (let i = 0; i < length; i++) {
      const value = this.source.readByteAt();
      if (value < 0) {
        return i;
      }
      target[offset + i] = value >= 128 ? value - 256 : value;
    }
    return length;
  }

  skip(count: number): number {
    return this.source.skip(count);
  }

  available(): number {
    return this.source.available();
  }

  close(): void {
    this.source.close();
  }
}

export class ByteArrayOutputStream {
  private bytes = new Int8Array(256);
  private count = 0;

  private ensure(extra: number): void {
    if (this.count + extra <= this.bytes.length) {
      return;
    }
    let size = this.bytes.length * 2;
    while (size < this.count + extra) {
      size *= 2;
    }
    const grown = new Int8Array(size);
    grown.set(this.bytes.subarray(0, this.count));
    this.bytes = grown;
  }

  writeByte(value: number): void {
    this.ensure(1);
    this.bytes[this.count++] = value;
  }

  write(source: Int8Array, offset = 0, length = source.length - offset): void {
    this.ensure(length);
    this.bytes.set(source.subarray(offset, offset + length), this.count);
    this.count += length;
  }

  toByteArray(): Int8Array {
    return this.bytes.slice(0, this.count);
  }

  size(): number {
    return this.count;
  }

  close(): void {}
}

/** Big-endian {@code DataOutputStream} over a {@link ByteArrayOutputStream}. */
export class DataOutputStream {
  private readonly sink: ByteArrayOutputStream;

  constructor(sink: ByteArrayOutputStream) {
    this.sink = sink;
  }

  writeByte(value: number): void {
    this.sink.writeByte(value & 0xff);
  }

  writeBoolean(value: boolean): void {
    this.sink.writeByte(value ? 1 : 0);
  }

  writeShort(value: number): void {
    this.sink.writeByte((value >> 8) & 0xff);
    this.sink.writeByte(value & 0xff);
  }

  writeChar(value: number): void {
    this.writeShort(value);
  }

  writeInt(value: number): void {
    this.sink.writeByte((value >> 24) & 0xff);
    this.sink.writeByte((value >> 16) & 0xff);
    this.sink.writeByte((value >> 8) & 0xff);
    this.sink.writeByte(value & 0xff);
  }

  writeLong(value: number): void {
    const high = Math.floor(value / 4294967296);
    const low = value % 4294967296;
    for (let shift = 24; shift >= 0; shift -= 8) {
      this.sink.writeByte(Math.floor(high / Math.pow(2, shift)) & 0xff);
    }
    for (let shift = 24; shift >= 0; shift -= 8) {
      this.sink.writeByte(Math.floor(low / Math.pow(2, shift)) & 0xff);
    }
  }

  /** Writes a {@code long} held as a BigInt. */
  writeLong64(value: bigint): void {
    const unsigned = BigInt.asUintN(64, value);
    for (let shift = 56n; shift >= 0n; shift -= 8n) {
      this.sink.writeByte(Number((unsigned >> shift) & 0xffn));
    }
  }

  write(source: Int8Array, offset = 0, length = source.length - offset): void {
    this.sink.write(source, offset, length);
  }

  flush(): void {}

  close(): void {
    this.sink.close();
  }
}
