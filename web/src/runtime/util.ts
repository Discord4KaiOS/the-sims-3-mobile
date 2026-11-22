/**
 * {@code java.util.Stack} and a placeholder {@code Thread}.
 *
 * The game spawns a loader thread that only reacts to key presses; the port
 * drives it cooperatively from the event loop, so {@code Thread} exists purely
 * for type compatibility.
 */

export class Stack<T = unknown> {
  private items: T[] = [];

  push(item: T): T {
    this.items.push(item);
    return item;
  }

  pop(): T {
    return this.items.pop() as T;
  }

  peek(): T {
    return this.items[this.items.length - 1];
  }

  empty(): boolean {
    return this.items.length === 0;
  }

  size(): number {
    return this.items.length;
  }

  removeAllElements(): void {
    this.items.length = 0;
  }

  addElement(item: T): void {
    this.items.push(item);
  }

  removeElement(item: T): boolean {
    const index = this.items.indexOf(item);
    if (index < 0) {
      return false;
    }
    this.items.splice(index, 1);
    return true;
  }

  copyInto(target: T[]): void {
    for (let i = 0; i < this.items.length; i++) {
      target[i] = this.items[i];
    }
  }

  elementAt(index: number): T {
    return this.items[index];
  }
}

/** Not a real thread: {@link #start} runs nothing until the game pumps it. */
export class Thread {
  static sleep(millis: number): void {}
  static yield(): void {}

  constructor(readonly runnable: { run(): void } | null = null) {}

  start(): void {}

  interrupt(): void {}
}
