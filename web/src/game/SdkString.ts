// Generated from SdkString.java by tools/web/JavaToTs.java; keep this file in sync.
import { arraycopy, chr, StringBuffer, IndexOutOfBoundsException } from '../runtime';
import { SdkFeatures } from './SdkFeatures';
import { SdkText } from './SdkText';

export class SdkString {
	static loggerPrefix: string = null;
	data: Int8Array = null!;
	offset: number = 0;
	length: number = 0;
	flags: number = 0;
	static convertChars(source: Int8Array, sourceOffset: number, target: Int8Array, targetOffset: number, count: number, sourceEncoding: number, targetEncoding: number): number {
		if (SdkFeatures.isLoggingEnabled() && sourceEncoding !== targetEncoding && sourceEncoding !== 1 && targetEncoding !== 0) {
			SdkText.log(SdkString.loggerPrefix + "convertBytes::method does not support copy from encoding: " + sourceEncoding + " to encoding: " + targetEncoding + "; please contact the SDK team as this scenario should be protected in all public methods!", 4);
		}
		let byteCount: number = targetEncoding === 0 ? count << 1 : count;
		if (targetOffset + byteCount > target.length || sourceOffset + count > source.length) {
			throw new IndexOutOfBoundsException("SDKString buffer overflow");
		}
		if (sourceEncoding !== targetEncoding && (sourceEncoding !== 1 || targetEncoding === 0)) {
			for (let index: number = sourceOffset; index < ((sourceOffset + count) | 0); index++) {
				let charCode: number = 0;
				if ((charCode = ((source[index] & 255) << 16 >> 16)) > 127) {
					charCode = SdkText.fromCharset(charCode);
				}
				target[targetOffset++] = ((charCode >> 8) << 24 >> 24);
				target[targetOffset++] = (charCode << 24 >> 24);
			}
		}
		else {
			arraycopy(source, sourceOffset, target, targetOffset, byteCount);
		}
		return byteCount;
	}
	isMutable(): boolean {
		return (this.flags & -128) !== 0;
	}
	append(other: SdkString): SdkString {
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkString.loggerPrefix + "append::appending str: " + other.toString() + " onto this: " + this.toString(), 1);
		}
		if (!this.isMutable()) {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkString.loggerPrefix + "append::this is not a mutable SDKString, cannot append. To create a mutable (buffer) SDKString, use SDKString(int,int)", 4);
			}
			return this;
		}
		else {
			let ourEncoding: number = (this.encoding() << 24 >> 24);
			let otherEncoding: number = 0;
			if ((otherEncoding = (other.encoding() << 24 >> 24)) !== ourEncoding && otherEncoding !== 1 && ourEncoding !== 0) {
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkString.loggerPrefix + "append::unable to append SDKString w/ encoding: " + otherEncoding + " onto SDKString w/ encoding: " + ourEncoding, 4);
				}
				return this;
			}
			else {
				let usedBytes: number = ((this.offset + (ourEncoding === 0 ? this.length << 1 : this.length)) | 0);
				if (SdkFeatures.isLoggingEnabled()) {
					let addBytes: number = ourEncoding === 0 ? 2 : 1;
					addBytes = Math.imul(other.length, addBytes);
					if (((usedBytes + addBytes) | 0) > this.data.length) {
						SdkText.log(SdkString.loggerPrefix + "append::buffer overflow; this buffer has size(bytes): " + this.data.length + " , and contained: " + usedBytes + " bytes prior to appending str: " + other.toString() + " containing " + addBytes + " bytes", 4);
					}
				}
				SdkString.convertChars(other.data, other.offset, this.data, usedBytes, other.length, otherEncoding, ourEncoding);
				this.length = ((((this.length + other.length) | 0)) << 16 >> 16);
				return this;
			}
		}
	}
	append_2(text: string): SdkString {
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkString.loggerPrefix + "append::appending JAVA str: " + text + " onto this: " + this.toString(), 1);
		}
		if (!this.isMutable()) {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkString.loggerPrefix + "append::append called on an immutable SDKString", 4);
			}
			return this;
		}
		else {
			let start: number = this.length;
			this.length = ((((this.length + text.length) | 0)) << 16 >> 16);
			for (let index: number = 0; index < text.length; index++) {
				this.setCharAt(((start + index) | 0), text.charCodeAt(index));
			}
			return this;
		}
	}
	setCharAt(index: number, value: number): void {
		if (!this.isMutable()) {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkString.loggerPrefix + "setCharAt::this method cannot be called on an immutableSDKString.  To create a mutable (buffer) SDKString, use SDKString(int,int)", 4);
			}
		}
		else {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkString.loggerPrefix + "setCharAt::setting the char at index: " + index + " of SDKString (mutable): " + this.toString() + " to: " + chr(value), 1);
				if (index >= this.length || index < 0) {
					SdkText.log(SdkString.loggerPrefix + "setCharAt::index out of bounds: " + index + " , this.length(): " + this.length, 4);
				}
			}
			let encoding: number = (this.encoding() << 24 >> 24);
			let code: number = 0;
			if ((code = (value << 16 >> 16)) > 127 && encoding !== 0) {
				code = SdkText.toCharset(code);
			}
			index = ((this.offset + (encoding === 0 ? index << 1 : index)) | 0);
			if (encoding === 0) {
				this.data[index++] = ((code >> 8) << 24 >> 24);
			}
			this.data[index] = (code << 24 >> 24);
		}
	}
	charAt(index: number): number {
		let code: number = 0;
		return (code = this.charCodeAt(index)) > 127 && this.encoding() !== 0 ? (SdkText.fromCharset(code) & 0xffff) : (code & 0xffff);
	}
	charCodeAt(index: number): number {
		if (this.encoding() === 0) {
			index = ((this.offset + (index << 1)) | 0);
			return (((this.data[index] & 255) << 8 | this.data[((index + 1) | 0)] & 255) << 16 >> 16);
		}
		else {
			return ((this.data[((this.offset + index) | 0)] & 255) << 16 >> 16);
		}
	}
	format_2(args: SdkString[]): SdkString {
		let encoding: number = ((this.flags & 127) << 24 >> 24);
		let result: SdkString = null!;
		(result = new SdkString()).flags = encoding;
		result.offset = 0;
		let written: number = 0;
		for (let templateIndex: number = 0; templateIndex < args.length; templateIndex++) {
			if (args[templateIndex] !== null) {
				written = ((written + (((args[templateIndex].length - 2) | 0))) | 0);
				let argEncoding: number = 0;
				if ((argEncoding = ((args[templateIndex].flags & 127) << 24 >> 24)) !== result.flags && argEncoding !== 1) {
					result.flags = 0;
				}
			}
		}
		let lengthShift: number = result.flags === 0 ? 1 : 0;
		if (written < 0) {
			written = 0;
		}
		result.data = new Int8Array(((written + this.length) | 0) << lengthShift);
		let outOffset: number = 0;
		written = 0;
		let sourceLengthShift: number = encoding === 0 ? 1 : 0;
		for (let sourceIndex: number = 0; sourceIndex < this.length; sourceIndex++) {
			if (this.charAt(sourceIndex) === 92) {
				let chunkLength: number = ((((sourceIndex - written) | 0)) << 16 >> 16);
				if (((outOffset + (chunkLength << lengthShift)) | 0) > result.data.length) {
					let grownChunk: Int8Array = new Int8Array(((outOffset + (chunkLength << lengthShift)) | 0));
					arraycopy(result.data, 0, grownChunk, 0, outOffset);
					result.data = grownChunk;
				}
				outOffset = ((outOffset + SdkString.convertChars(this.data, ((this.offset + (written << sourceLengthShift)) | 0), result.data, outOffset, chunkLength, encoding, result.flags)) | 0);
				written = sourceIndex++;
				if (sourceIndex < this.length) {
					chunkLength = this.charCodeAt(sourceIndex);
					if (this.charAt(sourceIndex) === 92) {
						written = sourceIndex;
					}
					else {
						if (chunkLength >= 48 && chunkLength <= 90) {
							let argIndex: number = ((chunkLength - 48) | 0);
							if (SdkFeatures.isLoggingEnabled() && (argIndex < 0 || argIndex >= args.length || args[argIndex] === null)) {
								SdkText.log(SdkString.loggerPrefix + "replace::replacement string missing: SDKString repl[" + argIndex + "]; it is needed to replace: " + this.toString(), 4);
							}
							if (((outOffset + (args[argIndex].length << lengthShift)) | 0) > result.data.length) {
								let grownArg: Int8Array = new Int8Array(((outOffset + (args[argIndex].length << lengthShift)) | 0));
								arraycopy(result.data, 0, grownArg, 0, outOffset);
								result.data = grownArg;
							}
							outOffset = ((outOffset + SdkString.convertChars(args[argIndex].data, args[argIndex].offset, result.data, outOffset, args[argIndex].length, ((args[argIndex].flags & 127) << 24 >> 24), result.flags)) | 0);
							written = ((sourceIndex + 1) | 0);
						}
					}
				}
			}
		}
		let tailLength: number = ((((this.length - written) | 0)) << 16 >> 16);
		if (((outOffset + (tailLength << lengthShift)) | 0) > result.data.length) {
			let grownTail: Int8Array = new Int8Array(((outOffset + (tailLength << lengthShift)) | 0));
			arraycopy(result.data, 0, grownTail, 0, outOffset);
			result.data = grownTail;
		}
		if ((outOffset = ((outOffset + SdkString.convertChars(this.data, ((this.offset + (written << sourceLengthShift)) | 0), result.data, outOffset, tailLength, encoding, result.flags)) | 0)) < result.data.length) {
			let trimmed: Int8Array = new Int8Array(outOffset);
			arraycopy(result.data, 0, trimmed, 0, outOffset);
			result.data = trimmed;
		}
		result.length = ((outOffset >> lengthShift) << 16 >> 16);
		return result;
	}
	concat(other: SdkString): SdkString {
		if (SdkFeatures.isLoggingEnabled()) {
			SdkText.log(SdkString.loggerPrefix + "concat::concatenating '" + other.toString() + "' onto '" + this.toString() + "'", 1);
		}
		let ourEncoding: number = (this.encoding() << 24 >> 24);
		let otherEncoding: number = (other.encoding() << 24 >> 24);
		let result: SdkString = null!;
		(result = new SdkString()).length = ((((this.length + other.length) | 0)) << 16 >> 16);
		result.offset = 0;
		if (ourEncoding !== otherEncoding && otherEncoding !== 1) {
			result.flags = 0;
		}
		else {
			result.flags = ourEncoding;
		}
		result.data = new Int8Array(result.flags === 0 ? result.length << 1 : result.length);
		let written: number = SdkString.convertChars(this.data, this.offset, result.data, 0, this.length, ourEncoding, result.flags);
		SdkString.convertChars(other.data, other.offset, result.data, written, other.length, otherEncoding, result.flags);
		return result;
	}
	toString(): string {
		let text: StringBuffer = new StringBuffer();
		for (let index: number = 0; index < this.length; index++) {
			text.append(chr(this.charAt(index)));
		}
		return text.toString();
	}
	copy(): SdkString {
		return this.concat(new SdkString(""));
	}
	capacity(): number {
		return this.encoding() === 0 ? this.data.length >> 1 : this.data.length;
	}
	encoding(): number {
		return this.flags & 127;
	}
	format_3(template: string): SdkString {
		return this.format_2([new SdkString(template)]);
	}
	format(template: SdkString): SdkString {
		return this.format_2([template]);
	}
	setLength(newLength: number): void {
		if (!this.isMutable()) {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkString.loggerPrefix + "setLength::this method cannot be called on an immutableSDKString.  To create a mutable (buffer) SDKString, use SDKString(int,int)", 4);
			}
		}
		else {
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkString.loggerPrefix + "setLength::setting the length of SDKString (mutable): " + this.toString() + " to: " + newLength, 1);
				if (newLength > this.capacity() || newLength < 0) {
					SdkText.log(SdkString.loggerPrefix + "setLength::the specified length is out of bounds, capacity: " + this.capacity(), 4);
				}
			}
			this.length = (newLength << 16 >> 16);
		}
	}
	static {
		if (SdkFeatures.isLoggingEnabled()) {
			SdkString.loggerPrefix = "SDKString::";
		}
	}
	constructor(...args: any[]) {
		if (args.length === 0) {
		}
		else if (args.length === 1 && typeof args[0] === "string") {
			const text = args[0] as string;
			this.flags = 1;
			this.offset = 0;
			this.length = (text.length << 16 >> 16);
			this.data = new Int8Array(this.length);
			for (let index: number = 0; index < this.length; index++) {
				this.data[index] = (text.charCodeAt(index) << 24 >> 24);
				if (text.charCodeAt(index) > 127 && SdkFeatures.isLoggingEnabled()) {
					SdkText.log(SdkString.loggerPrefix + "constructor::char: " + chr(text.charCodeAt(index)) + " at index: " + index + " of String: " + text + " is above ASCII range, it's representation is undefined!", 3);
				}
			}
		}
		else if (args.length === 2 && typeof args[0] === "number" && typeof args[1] === "number") {
			const capacity = args[0] as number;
			const encoding = args[1] as number;
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log(SdkString.loggerPrefix + "constructor::creating an empty SDKString for use as a buffer w/ capacity: " + capacity + " ,encoding: " + encoding, 1);
			}
			this.flags = ((encoding | -128) << 24 >> 24);
			this.data = new Int8Array(encoding === 0 ? capacity << 1 : capacity);
			this.offset = 0;
			this.length = 0;
		}
	}
}
