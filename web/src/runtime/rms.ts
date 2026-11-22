/**
 * MIDP {@code RecordStore} persisted in {@code localStorage}.
 *
 * Records are stored as base64 so the byte payloads round-trip exactly, and the
 * whole store is rewritten on every change, matching the desktop shim.
 */

export class RecordStoreException extends Error {
  constructor(message = 'record store error') {
    super(message);
    this.name = 'RecordStoreException';
  }
}

export class RecordStoreNotFoundException extends RecordStoreException {
  constructor(message = 'record store not found') {
    super(message);
    this.name = 'RecordStoreNotFoundException';
  }
}

export class RecordStoreNotOpenException extends RecordStoreException {
  constructor(message = 'record store not open') {
    super(message);
    this.name = 'RecordStoreNotOpenException';
  }
}

export class InvalidRecordIDException extends RecordStoreException {
  constructor(message = 'invalid record id') {
    super(message);
    this.name = 'InvalidRecordIDException';
  }
}

export class RecordStoreFullException extends RecordStoreException {
  constructor(message = 'record store full') {
    super(message);
    this.name = 'RecordStoreFullException';
  }
}

export interface RecordListener {
  recordAdded(store: RecordStore, recordId: number): void;
  recordChanged(store: RecordStore, recordId: number): void;
  recordDeleted(store: RecordStore, recordId: number): void;
}

const PREFIX = 'rms:';
const STORE_SUFFIX = '.rms';

interface StoredStore {
  next: number;
  modified: number;
  records: [number, string][];
}

function toBase64(bytes: Int8Array): string {
  let text = '';
  for (let i = 0; i < bytes.length; i++) {
    text += String.fromCharCode(bytes[i] & 0xff);
  }
  return btoa(text);
}

function fromBase64(text: string): Int8Array {
  const raw = atob(text);
  const bytes = new Int8Array(raw.length);
  for (let i = 0; i < raw.length; i++) {
    bytes[i] = raw.charCodeAt(i);
  }
  return bytes;
}

export class RecordStore {
  private readonly records = new Map<number, Int8Array>();
  private nextRecordId = 1;
  private lastModified = Date.now();
  private open = true;
  private readonly listeners: RecordListener[] = [];

  private constructor(
    private readonly name: string,
    private readonly key: string,
  ) {}

  static openRecordStore(name: string, createIfNecessary: boolean): RecordStore {
    const key = PREFIX + name;
    const existing = localStorage.getItem(key);
    if (existing === null && !createIfNecessary) {
      throw new RecordStoreNotFoundException(`no record store named ${name}`);
    }
    const store = new RecordStore(name, key);
    if (existing !== null) {
      store.load(existing);
    } else {
      store.save();
    }
    return store;
  }

  static deleteRecordStore(name: string): void {
    const key = PREFIX + name;
    if (localStorage.getItem(key) === null) {
      throw new RecordStoreNotFoundException(`no record store named ${name}`);
    }
    localStorage.removeItem(key);
  }

  static listRecordStores(): string[] | null {
    const names: string[] = [];
    for (let i = 0; i < localStorage.length; i++) {
      const key = localStorage.key(i);
      if (key !== null && key.startsWith(PREFIX)) {
        names.push(key.substring(PREFIX.length));
      }
    }
    return names.length === 0 ? null : names;
  }

  getName(): string {
    this.checkOpen();
    return this.name;
  }

  addRecord(data: Int8Array | null, offset = 0, numBytes = data ? data.length - offset : 0): number {
    this.checkOpen();
    const id = this.nextRecordId++;
    this.records.set(id, copy(data, offset, numBytes));
    this.lastModified = Date.now();
    this.save();
    for (const listener of this.listeners) {
      listener.recordAdded(this, id);
    }
    return id;
  }

  setRecord(recordId: number, newData: Int8Array | null, offset = 0, numBytes = newData ? newData.length - offset : 0): void {
    this.checkOpen();
    if (!this.records.has(recordId)) {
      throw new InvalidRecordIDException(`invalid record id ${recordId}`);
    }
    this.records.set(recordId, copy(newData, offset, numBytes));
    this.lastModified = Date.now();
    this.save();
    for (const listener of this.listeners) {
      listener.recordChanged(this, recordId);
    }
  }

  getRecord(recordId: number): Int8Array {
    this.checkOpen();
    const data = this.records.get(recordId);
    if (data === undefined) {
      throw new InvalidRecordIDException(`invalid record id ${recordId}`);
    }
    return data.slice();
  }

  getRecordSize(recordId: number): number {
    this.checkOpen();
    const data = this.records.get(recordId);
    if (data === undefined) {
      throw new InvalidRecordIDException(`invalid record id ${recordId}`);
    }
    return data.length;
  }

  deleteRecord(recordId: number): void {
    this.checkOpen();
    if (!this.records.delete(recordId)) {
      throw new InvalidRecordIDException(`invalid record id ${recordId}`);
    }
    this.lastModified = Date.now();
    this.save();
    for (const listener of this.listeners) {
      listener.recordDeleted(this, recordId);
    }
  }

  getNumRecords(): number {
    this.checkOpen();
    return this.records.size;
  }

  getNextRecordID(): number {
    this.checkOpen();
    return this.nextRecordId;
  }

  getSize(): number {
    this.checkOpen();
    let size = 0;
    for (const data of this.records.values()) {
      size += data.length + 12;
    }
    return size;
  }

  getSizeAvailable(): number {
    this.checkOpen();
    return 65536;
  }

  getLastModified(): number {
    this.checkOpen();
    return this.lastModified;
  }

  closeRecordStore(): void {
    this.checkOpen();
    this.open = false;
    try {
      this.save();
    } catch (error) {
      console.warn(`warning: could not persist record store ${this.name}: ${error}`);
    }
  }

  addRecordListener(listener: RecordListener): void {
    this.listeners.push(listener);
  }

  removeRecordListener(listener: RecordListener): void {
    const index = this.listeners.indexOf(listener);
    if (index >= 0) {
      this.listeners.splice(index, 1);
    }
  }

  private checkOpen(): void {
    if (!this.open) {
      throw new RecordStoreNotOpenException(`store ${this.name} is closed`);
    }
  }

  private load(serialized: string): void {
    try {
      const stored = JSON.parse(serialized) as StoredStore;
      this.nextRecordId = stored.next;
      this.lastModified = stored.modified;
      for (const [id, base64] of stored.records) {
        this.records.set(id, fromBase64(base64));
      }
    } catch (error) {
      throw new RecordStoreException(`could not read store ${this.name}: ${error}`);
    }
  }

  private save(): void {
    const records: [number, string][] = [];
    for (const [id, data] of this.records) {
      records.push([id, toBase64(data)]);
    }
    const stored: StoredStore = { next: this.nextRecordId, modified: this.lastModified, records };
    try {
      localStorage.setItem(this.key, JSON.stringify(stored));
    } catch (error) {
      throw new RecordStoreException(`could not write store ${this.name}: ${error}`);
    }
  }
}

function copy(data: Int8Array | null, offset: number, numBytes: number): Int8Array {
  const result = new Int8Array(numBytes);
  if (data !== null && numBytes > 0) {
    result.set(data.subarray(offset, offset + numBytes));
  }
  return result;
}
