/** MIDP {@code Alert}/{@code AlertType}: only used to report fatal SDK errors. */
import { Displayable } from './display';
import type { Graphics } from './graphics';
import type { Image } from './image';
import { Font } from './font';

export class AlertType {
  static readonly INFO = new AlertType('info', true);
  static readonly WARNING = new AlertType('warning', true);
  static readonly ERROR = new AlertType('error', true);
  static readonly ALARM = new AlertType('alarm', true);
  static readonly CONFIRMATION = new AlertType('confirmation', true);

  constructor(
    readonly name: string,
    readonly sound: boolean,
  ) {}

  getName(): string {
    return this.name;
  }

  isSoundEnabled(): boolean {
    return this.sound;
  }
}

/** MIDP {@code Screen}: the base for displayables with a title. */
export abstract class Screen extends Displayable {}

export class Alert extends Screen {
  static readonly FOREVER = -2;
  static readonly DEFAULT_TIMEOUT = 3000;

  private timeout = Alert.DEFAULT_TIMEOUT;
  private reported = false;

  constructor(
    title: string | null,
    readonly text: string | null,
    readonly image: Image | null,
    readonly type: AlertType | null,
  ) {
    super();
    this.setTitle(title);
  }

  setTimeout(timeout: number): void {
    this.timeout = timeout;
  }

  getTimeout(): number {
    return this.timeout;
  }

  getString(): string {
    return this.text ?? '';
  }

  getImage(): Image | null {
    return this.image;
  }

  getType(): AlertType {
    return this.type ?? AlertType.INFO;
  }

  paintTo(graphics: Graphics): void {
    graphics.setColor(0x000000);
    graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
    if (!this.reported) {
      this.reported = true;
      console.error(`[Alert/${this.getType().getName()}] ${this.getTitle()}: ${this.text}`);
    }
    graphics.setColor(0xffffff);
    let y = 8;
    const message = this.getTitle().length === 0 ? this.getString() : `${this.getTitle()}\n${this.getString()}`;
    for (const line of message.split('\n')) {
      graphics.drawString(line, this.getWidth() / 2, y, 16 | 1);
      y += Font.getDefaultFont().getHeight() + 2;
    }
  }
}
