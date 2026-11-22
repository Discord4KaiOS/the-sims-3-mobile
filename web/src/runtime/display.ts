/**
 * MIDP display classes: {@code Displayable}, {@code Canvas} and {@code Display}.
 *
 * The game renders into a 240x320 offscreen buffer which is scaled into the
 * window with nearest neighbour filtering, exactly like the desktop shim.
 */
import { createCanvas } from './image';
import { Graphics } from './graphics';
import type { MIDlet } from './midlet';
import type { Alert } from './alert';

export const LOGICAL_WIDTH = 240;
export const LOGICAL_HEIGHT = 320;

export abstract class Displayable {
  display: Display | null = null;
  private shown = false;
  private title = '';

  getWidth(): number {
    return LOGICAL_WIDTH;
  }

  getHeight(): number {
    return LOGICAL_HEIGHT;
  }

  isShown(): boolean {
    return this.shown;
  }

  getTitle(): string {
    return this.title;
  }

  setTitle(title: string | null): void {
    this.title = title ?? '';
  }

  notifyShown(): void {
    this.shown = true;
    this.showNotify();
  }

  notifyHidden(): void {
    this.shown = false;
    this.hideNotify();
  }

  protected showNotify(): void {}

  protected hideNotify(): void {}
}

export abstract class Canvas extends Displayable {
  static readonly UP = 1;
  static readonly DOWN = 6;
  static readonly LEFT = 2;
  static readonly RIGHT = 5;
  static readonly FIRE = 8;
  static readonly GAME_A = 9;
  static readonly GAME_B = 10;
  static readonly GAME_C = 11;
  static readonly GAME_D = 12;

  static readonly KEY_NUM0 = 48;
  static readonly KEY_NUM1 = 49;
  static readonly KEY_NUM2 = 50;
  static readonly KEY_NUM3 = 51;
  static readonly KEY_NUM4 = 52;
  static readonly KEY_NUM5 = 53;
  static readonly KEY_NUM6 = 54;
  static readonly KEY_NUM7 = 55;
  static readonly KEY_NUM8 = 56;
  static readonly KEY_NUM9 = 57;
  static readonly KEY_STAR = 42;
  static readonly KEY_POUND = 35;

  static readonly KEY_UP = -1;
  static readonly KEY_DOWN = -2;
  static readonly KEY_LEFT = -3;
  static readonly KEY_RIGHT = -4;
  static readonly KEY_FIRE = -5;
  static readonly KEY_GAME_A = -6;
  static readonly KEY_GAME_B = -7;
  static readonly KEY_GAME_C = -8;
  static readonly KEY_GAME_D = -9;

  private fullScreen = false;

  abstract paint(graphics: Graphics): void;

  protected keyPressed(keyCode: number): void {}

  protected keyReleased(keyCode: number): void {}

  protected keyRepeated(keyCode: number): void {}

  repaint(): void {
    this.display?.postRepaint(this);
  }

  repaintRegion(x: number, y: number, width: number, height: number): void {
    this.repaint();
  }

  serviceRepaints(): void {
    this.display?.paintNow(this);
  }

  setFullScreenMode(enable: boolean): void {
    this.fullScreen = enable;
  }

  isFullScreenMode(): boolean {
    return this.fullScreen;
  }

  getGameAction(keyCode: number): number {
    switch (keyCode) {
      case Canvas.KEY_UP:
      case Canvas.KEY_NUM2:
        return Canvas.UP;
      case Canvas.KEY_DOWN:
      case Canvas.KEY_NUM8:
        return Canvas.DOWN;
      case Canvas.KEY_LEFT:
      case Canvas.KEY_NUM4:
        return Canvas.LEFT;
      case Canvas.KEY_RIGHT:
      case Canvas.KEY_NUM6:
        return Canvas.RIGHT;
      case Canvas.KEY_FIRE:
      case Canvas.KEY_NUM5:
        return Canvas.FIRE;
      case Canvas.KEY_GAME_A:
        return Canvas.GAME_A;
      case Canvas.KEY_GAME_B:
        return Canvas.GAME_B;
      case Canvas.KEY_GAME_C:
        return Canvas.GAME_C;
      case Canvas.KEY_GAME_D:
        return Canvas.GAME_D;
      default:
        return 0;
    }
  }

  getKeyCode(gameAction: number): number {
    switch (gameAction) {
      case Canvas.UP:
        return Canvas.KEY_UP;
      case Canvas.DOWN:
        return Canvas.KEY_DOWN;
      case Canvas.LEFT:
        return Canvas.KEY_LEFT;
      case Canvas.RIGHT:
        return Canvas.KEY_RIGHT;
      case Canvas.FIRE:
        return Canvas.KEY_FIRE;
      case Canvas.GAME_A:
        return Canvas.KEY_GAME_A;
      case Canvas.GAME_B:
        return Canvas.KEY_GAME_B;
      case Canvas.GAME_C:
        return Canvas.KEY_GAME_C;
      case Canvas.GAME_D:
        return Canvas.KEY_GAME_D;
      default:
        return 0;
    }
  }

  getKeyName(keyCode: number): string {
    if (keyCode >= Canvas.KEY_NUM0 && keyCode <= Canvas.KEY_NUM9) {
      return String.fromCharCode(keyCode);
    }
    switch (keyCode) {
      case Canvas.KEY_UP:
        return 'Up';
      case Canvas.KEY_DOWN:
        return 'Down';
      case Canvas.KEY_LEFT:
        return 'Left';
      case Canvas.KEY_RIGHT:
        return 'Right';
      case Canvas.KEY_FIRE:
        return 'Select';
      case Canvas.KEY_STAR:
        return '*';
      case Canvas.KEY_POUND:
        return '#';
      default:
        return `Key${keyCode}`;
    }
  }

  hasPointerEvents(): boolean {
    return false;
  }

  hasPointerMotionEvents(): boolean {
    return false;
  }

  hasRepeatEvents(): boolean {
    return true;
  }

  isDoubleBuffered(): boolean {
    return true;
  }

  deliverKeyPressed(keyCode: number): void {
    this.keyPressed(keyCode);
  }

  deliverKeyReleased(keyCode: number): void {
    this.keyReleased(keyCode);
  }

  deliverKeyRepeated(keyCode: number): void {
    this.keyRepeated(keyCode);
  }
}

/** The visible canvas plus the logical framebuffer the game paints into. */
class ScreenHost {
  private readonly element: HTMLCanvasElement;
  private readonly elementContext: CanvasRenderingContext2D;
  private readonly buffer = createCanvas(LOGICAL_WIDTH, LOGICAL_HEIGHT);
  private readonly bufferContext: CanvasRenderingContext2D;
  /** Reused every frame: a fresh Graphics at 60 Hz is needless garbage. */
  private readonly graphics: Graphics;
  private displayable: Displayable | null = null;
  private repaintPending = false;
  private readonly pressed = new Set<string>();

  constructor(element: HTMLCanvasElement) {
    this.element = element;
    this.elementContext = element.getContext('2d')!;
    this.bufferContext = this.buffer.getContext('2d')!;
    this.bufferContext.imageSmoothingEnabled = false;
    this.graphics = new Graphics(this.bufferContext, LOGICAL_WIDTH, LOGICAL_HEIGHT);
    window.addEventListener('keydown', (event) => this.onKeyDown(event));
    window.addEventListener('keyup', (event) => this.onKeyUp(event));
    window.addEventListener('blur', () => this.pressed.clear());
    window.addEventListener('resize', () => this.fit());
    this.fit();
  }

  private fit(): void {
    const ratio = window.devicePixelRatio || 1;
    const width = Math.max(1, Math.floor(window.innerWidth * ratio));
    const height = Math.max(1, Math.floor(window.innerHeight * ratio));
    if (this.element.width !== width || this.element.height !== height) {
      this.element.width = width;
      this.element.height = height;
    }
    this.elementContext.imageSmoothingEnabled = false;
    this.paint();
  }

  setDisplayable(displayable: Displayable): void {
    this.displayable = displayable;
    this.paint();
    this.pressed.clear();
  }

  postRepaint(): void {
    if (this.repaintPending) {
      return;
    }
    this.repaintPending = true;
    requestAnimationFrame(() => {
      this.repaintPending = false;
      this.paint();
    });
  }

  paintNow(): void {
    this.paint();
  }

  private paint(): void {
    const current = this.displayable;
    if (current === null) {
      return;
    }
    const context = this.bufferContext;
    context.save();
    context.setTransform(1, 0, 0, 1, 0, 0);
    // The frame is *not* cleared first: the Java build paints onto a persistent
    // buffer, so a repaint that draws nothing leaves the previous frame on screen
    // instead of flashing black (which is what the exit sequence relies on).
    context.globalAlpha = 1;
    context.globalCompositeOperation = 'source-over';
    const graphics = this.graphics.begin();
    if (current instanceof Canvas) {
      try {
        current.paint(graphics);
      } catch (error) {
        console.error(error);
      }
    } else {
      (current as Alert).paintTo(graphics);
    }
    context.restore();

    const canvasWidth = this.element.width;
    const canvasHeight = this.element.height;
    const scale = Math.min(canvasWidth / LOGICAL_WIDTH, canvasHeight / LOGICAL_HEIGHT);
    const destWidth = Math.max(1, Math.round(LOGICAL_WIDTH * scale));
    const destHeight = Math.max(1, Math.round(LOGICAL_HEIGHT * scale));
    const destX = Math.floor((canvasWidth - destWidth) / 2);
    const destY = Math.floor((canvasHeight - destHeight) / 2);
    this.elementContext.fillStyle = '#000';
    this.elementContext.fillRect(0, 0, canvasWidth, canvasHeight);
    this.elementContext.imageSmoothingEnabled = false;
    this.elementContext.drawImage(this.buffer, destX, destY, destWidth, destHeight);
  }

  private onKeyDown(event: KeyboardEvent): void {
    // KaiOS routes the hardware back key to the system unless the page claims it.
    if (isBackKey(event)) {
      event.preventDefault();
    }
    const keyCode = mapKey(event);
    if (keyCode === null) {
      return;
    }
    event.preventDefault();
    const current = this.displayable;
    if (!(current instanceof Canvas)) {
      return;
    }
    const identity = keyIdentity(event);
    if (this.pressed.has(identity)) {
      current.deliverKeyRepeated(keyCode);
      return;
    }
    this.pressed.add(identity);
    current.deliverKeyPressed(keyCode);
  }

  private onKeyUp(event: KeyboardEvent): void {
    if (isBackKey(event)) {
      event.preventDefault();
    }
    const keyCode = mapKey(event);
    if (keyCode === null) {
      return;
    }
    event.preventDefault();
    this.pressed.delete(keyIdentity(event));
    const current = this.displayable;
    if (current instanceof Canvas) {
      current.deliverKeyReleased(keyCode);
    }
  }
}

/**
 * Key names that map to a MIDP key code.
 *
 * A browser reports these in {@code event.code} (a physical key name such as
 * {@code Digit1}), but KaiOS leaves {@code code} empty for the soft keys and the
 * centre key and only fills in {@code event.key} with {@code SoftLeft},
 * {@code SoftRight} or {@code Enter}, so both are looked up in the same table.
 */
const KEY_CODES: ReadonlyArray<readonly [readonly string[], number]> = [
  [['ArrowUp'], Canvas.KEY_UP],
  [['ArrowDown'], Canvas.KEY_DOWN],
  [['ArrowLeft'], Canvas.KEY_LEFT],
  [['ArrowRight'], Canvas.KEY_RIGHT],
  [['Enter', 'NumpadEnter', 'Space', ' '], Canvas.KEY_FIRE],
  [['F1', 'SoftLeft'], Canvas.KEY_GAME_A],
  [['F2', 'SoftRight'], Canvas.KEY_GAME_B],
  [['F3', 'Backspace'], Canvas.KEY_GAME_C],
  [['F4'], Canvas.KEY_GAME_D],
  [['NumpadMultiply', '*'], Canvas.KEY_STAR],
  [['#'], Canvas.KEY_POUND],
  [['Digit0', 'Numpad0', '0'], Canvas.KEY_NUM0],
  [['Digit1', 'Numpad1', '1'], Canvas.KEY_NUM1],
  [['Digit2', 'Numpad2', '2'], Canvas.KEY_NUM2],
  [['Digit3', 'Numpad3', '3'], Canvas.KEY_NUM3],
  [['Digit4', 'Numpad4', '4'], Canvas.KEY_NUM4],
  [['Digit5', 'Numpad5', '5'], Canvas.KEY_NUM5],
  [['Digit6', 'Numpad6', '6'], Canvas.KEY_NUM6],
  [['Digit7', 'Numpad7', '7'], Canvas.KEY_NUM7],
  [['Digit8', 'Numpad8', '8'], Canvas.KEY_NUM8],
  [['Digit9', 'Numpad9', '9'], Canvas.KEY_NUM9],
];

const KEY_BY_NAME = new Map<string, number>();
for (const [names, keyCode] of KEY_CODES) {
  for (const name of names) {
    KEY_BY_NAME.set(name, keyCode);
  }
}

/** The hardware back key, which KaiOS otherwise turns into "leave the app". */
function isBackKey(event: KeyboardEvent): boolean {
  return event.key === 'Backspace' || event.code === 'Backspace' || event.keyCode === 8;
}

/**
 * Identifies a physical key across keydown/keyup. KaiOS reports an empty
 * {@code code} for the soft keys, so the name has to stand in for it.
 */
function keyIdentity(event: KeyboardEvent): string {
  if (event.code) {
    return event.code;
  }
  if (event.key && event.key !== 'Unidentified') {
    return event.key;
  }
  return `keyCode${event.keyCode}`;
}

/** Maps a DEPRECATED keyCode, the last resort for engines without a key name. */
function mapKeyCode(keyCode: number): number | null {
  // MIDP numbers, '*' and '#' use their ASCII values.
  if ((keyCode >= Canvas.KEY_NUM0 && keyCode <= Canvas.KEY_NUM9) || keyCode === Canvas.KEY_STAR
      || keyCode === Canvas.KEY_POUND) {
    return keyCode;
  }
  switch (keyCode) {
    case 13:
      return Canvas.KEY_FIRE;
    case 8:
      return Canvas.KEY_GAME_C;
    case 37:
      return Canvas.KEY_LEFT;
    case 38:
      return Canvas.KEY_UP;
    case 39:
      return Canvas.KEY_RIGHT;
    case 40:
      return Canvas.KEY_DOWN;
    case 112:
      return Canvas.KEY_GAME_A;
    case 113:
      return Canvas.KEY_GAME_B;
    case 114:
      return Canvas.KEY_GAME_C;
    case 115:
      return Canvas.KEY_GAME_D;
    default:
      return null;
  }
}

/** Maps a browser key event to a MIDP key code, or null when unmapped. */
function mapKey(event: KeyboardEvent): number | null {
  const byCode = KEY_BY_NAME.get(event.code);
  if (byCode !== undefined) {
    return byCode;
  }
  const byKey = KEY_BY_NAME.get(event.key);
  if (byKey !== undefined) {
    return byKey;
  }
  return mapKeyCode(event.keyCode);
}

export class Display {
  private static instance: Display | null = null;

  private readonly host: ScreenHost;
  private current: Displayable | null = null;

  private constructor(readonly midlet: MIDlet) {
    this.host = new ScreenHost(document.getElementById('screen') as HTMLCanvasElement);
  }

  static getDisplay(midlet: MIDlet | null): Display {
    if (Display.instance === null) {
      if (midlet === null) {
        throw new Error('midlet');
      }
      Display.instance = new Display(midlet);
    }
    return Display.instance;
  }

  static logicalWidth(): number {
    return LOGICAL_WIDTH;
  }

  static logicalHeight(): number {
    return LOGICAL_HEIGHT;
  }

  getCurrent(): Displayable | null {
    return this.current;
  }

  setCurrent(next: Displayable | null): void {
    if (next === null) {
      throw new Error('next');
    }
    const previous = this.current;
    if (previous !== next) {
      this.current = next;
      next.display = this;
      previous?.notifyHidden();
      queueMicrotask(() => {
        this.host.setDisplayable(next);
        next.notifyShown();
      });
    }
  }

  isColor(): boolean {
    return true;
  }

  numColors(): number {
    return 65536;
  }

  numAlphaLevels(): number {
    return 256;
  }

  vibrate(duration: number): boolean {
    if (duration > 0 && typeof navigator.vibrate === 'function') {
      return navigator.vibrate(duration);
    }
    return false;
  }

  flashBacklight(duration: number): void {}

  postRepaint(canvas: Canvas): void {
    if (this.current === canvas) {
      this.host.postRepaint();
    }
  }

  paintNow(canvas: Canvas): void {
    if (this.current === canvas) {
      this.host.paintNow();
    }
  }

  shutdown(): void {}
}
