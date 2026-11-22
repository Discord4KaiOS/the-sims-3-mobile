// Generated from FixedMath.java by tools/web/JavaToTs.java; keep this file in sync.

export abstract class FixedMath {
	static sinTableNegative: Int32Array = Int32Array.from([205525, 205375, 205260, 205163, 205077]);
	static sinTablePositive: Int32Array = Int32Array.from([270, 512, 627, 724, 809]);
	static sqrtTable: Int32Array = Int32Array.from([0, 256, 362, 443, 512, 572, 627, 677, 724, 768, 810, 849, 887, 923, 958, 991, 1024, 1056, 1086, 1116, 1145, 1173, 1201, 1227, 1254, 1280, 1305, 1330, 1355, 1378, 1402, 1425, 1448, 1471, 1493, 1515, 1536, 1557, 1578, 1599, 1619, 1639, 1659, 1679, 1698, 1717, 1736, 1755]);
	static sqrtTableLength: number = FixedMath.sqrtTable.length;
	static sqrtTableMax: number = FixedMath.sqrtTable[((FixedMath.sqrtTableLength - 1) | 0)];
	static sign(value: number): number {
		if (value > 0) {
			return 1;
		}
		else {
			return value < 0 ? -1 : 0;
		}
	}
	static clamp(value: number, min: number, max: number): number {
		return Math.max(min, Math.min(max, value));
	}
	static mul(left: number, right: number): number {
		return ((Math.floor((left * right) / 65536)) | 0);
	}
	static div(dividend: number, divisor: number): number {
		return ((Math.trunc((((dividend) * 65536)) / divisor)) | 0);
	}
	static length(x: number, y: number): number {
		let wideX: number = x;
		let wideY: number = y;
		return FixedMath.sqrt(((Math.floor((wideX * wideX + wideY * wideY) / 65536)) | 0));
	}
	static wrapAngle(angle: number): number {
		if (angle > 205887) {
			angle = ((angle - (Math.imul(Math.max(1, ((angle / 411775) | 0)), 411775))) | 0);
		}
		else {
			if (angle < -205887) {
				angle = ((angle + (Math.imul(Math.max(1, ((((angle * -1) | 0) / 411775) | 0)), 411775))) | 0);
			}
		}
		return angle;
	}
	static cos(angle: number): number {
		let sign: number = 0;
		let angle16: number = 0;
		let quadrant: number = (angle16 = FixedMath.mul(Math.imul((sign = angle >= 0 ? 1 : -1), angle) << 1, 20861) & 262143) >> 16 & 3;
		angle16 = (angle16 & 65535);
		sign = 1;
		if ((quadrant & 1) !== 0) {
			sign = -1;
			angle16 = ((65536 - angle16) | 0);
		}
		if ((quadrant & 2) !== 0) {
			sign = ((sign * -1) | 0);
		}
		angle16 = FixedMath.mul(angle16, 205887) >> 1;
		let series: number = 262144;
		let square: number = 0;
		let term: number = (square = FixedMath.mul(angle16, angle16)) << 1;
		series = ((series - term) | 0);
		term = FixedMath.mul(FixedMath.mul(square, term), 5461);
		series = ((series + term) | 0);
		term = FixedMath.mul(FixedMath.mul(square, term), 2185);
		series = ((series - term) | 0);
		term = FixedMath.mul(FixedMath.mul(square, term), 1170);
		let result: number = 0;
		return Math.imul((result = ((series + term) | 0)), sign) >> 2;
	}
	static sin(angle: number): number {
		if (angle <= -65536) {
			return 205887;
		}
		else {
			if (((65535 + angle) | 0) < FixedMath.sinTableNegative.length) {
				return FixedMath.sinTableNegative[((65535 + angle) | 0)];
			}
			else {
				if (angle >= 65536) {
					return 0;
				}
				else {
					if (((65535 - angle) | 0) < FixedMath.sinTablePositive.length) {
						return FixedMath.sinTablePositive[((65535 - angle) | 0)];
					}
					else {
						let angleSquared: number = angle * angle;
						if (angle > 46661) {
							return ((102944 - FixedMath.sin(FixedMath.sqrt(((Math.floor((4294967296 - angleSquared + 32768) / 65536)) | 0)))) | 0);
						}
						else {
							if (angle < -46661) {
								return ((102944 + FixedMath.sin(FixedMath.sqrt(((Math.floor((4294967296 - angleSquared + 32768) / 65536)) | 0)))) | 0);
							}
							else {
								let square: number = ((Math.floor((angleSquared + 32768) / 65536)) | 0);
								let series: number = ((angle * -1) | 0);
								let term: number = FixedMath.mul(angle, square);
								series = ((series - FixedMath.mul(10923, term)) | 0);
								term = FixedMath.mul(term, square);
								series = ((series - FixedMath.mul(4915, term)) | 0);
								term = FixedMath.mul(term, square);
								series = ((series - FixedMath.mul(2926, term)) | 0);
								return ((102944 + series) | 0);
							}
						}
					}
				}
			}
		}
	}
	static sinShifted(angle: number): number {
		return FixedMath.cos(((angle - 102944) | 0));
	}
	static atan2(y: number, x: number): number {
		if (y === 0) {
			return x < 0 ? 205887 : 0;
		}
		else {
			if (x === 0) {
				return y < 0 ? -102944 : 102944;
			}
			else {
				let length: number = FixedMath.length(y, x);
				let absX: number = x < 0 ? ((x * -1) | 0) : x;
				let angle: number = FixedMath.sin(length > 0 ? FixedMath.div(absX, length) : absX);
				if (y < 0 && x < 0) {
					angle = ((angle - 205887) | 0);
				}
				else {
					if (y < 0 && x > 0) {
						angle = ((angle * -1) | 0);
					}
					else {
						if (y > 0 && x < 0) {
							angle = ((205887 - angle) | 0);
						}
					}
				}
				return angle;
			}
		}
	}
	static sqrt(value: number): number {
		if (value < 0) {
			return 0;
		}
		else {
			if (value < FixedMath.sqrtTableLength) {
				return FixedMath.sqrtTable[value];
			}
			else {
				let adjusted: number = value;
				if (value < FixedMath.sqrtTableMax) {
					adjusted = value << 2;
				}
				else {
					if (value > 4194304) {
						adjusted = value >> 2;
					}
				}
				let result: number = 0;
				if (adjusted > 65200 && adjusted < 65800) {
					result = ((adjusted + 65536) | 0) >> 1;
				}
				else {
					let low: number = 0;
					if ((low = (((adjusted >> 3) + 131072) | 0)) > 8388608) {
						low = 8388608;
					}
					let high: number = FixedMath.sqrtTableMax;
					result = ((low + high) | 0) >> 1;
					for (let step: number = 0; step < 13; step++) {
						if (FixedMath.mul(result, result) > adjusted) {
							low = result;
						}
						else {
							high = result;
						}
						result = ((low + high) | 0) >> 1;
					}
				}
				if (value < FixedMath.sqrtTableMax) {
					result = (result >> 1);
				}
				else {
					if (value > 4194304) {
						result = (result << 1);
					}
				}
				return result;
			}
		}
	}
	static smoothStep(edge0: number, edge1: number, x: number): number {
		if (x < edge0) {
			return 0;
		}
		else {
			if (x >= edge1) {
				return 65536;
			}
			else {
				let deltaX: number = ((x - edge0) | 0);
				let deltaEdge: number = ((edge1 - edge0) | 0);
				let ratio: number = 0;
				let squared: number = FixedMath.mul(ratio = FixedMath.div(deltaX, deltaEdge), ratio);
				return ((Math.imul(-2, FixedMath.mul(squared, ratio)) + Math.imul(3, squared)) | 0);
			}
		}
	}
	constructor() {
	}
}
