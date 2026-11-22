/**
 * The Java runtime exception types the game throws or catches.
 *
 * JavaScript has one exception type, so these exist purely so that
 * {@code instanceof} based catch chains keep working after translation.
 */

export class RuntimeException extends Error {
  constructor(message?: unknown) {
    super(message === undefined ? undefined : String(message));
  }
}
export class IllegalArgumentException extends RuntimeException {}
export class IllegalStateException extends RuntimeException {}
export class NullPointerException extends RuntimeException {}
export class SecurityException extends RuntimeException {}
export class InterruptedException extends RuntimeException {}
export class UnsupportedOperationException extends RuntimeException {}
export class IndexOutOfBoundsException extends RuntimeException {}
