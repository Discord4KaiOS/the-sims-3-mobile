/** MIDP {@code MIDlet}: only the lifecycle the game drives is implemented. */

import { exitApp } from './support';

const PROPERTIES: Record<string, string> = {
  'MIDlet-Name': 'The Sims 3',
  'MIDlet-Version': '7.7.65',
  'MIDlet-Vendor': 'Electronic Arts',
  'MIDlet-1': 'The Sims 3, /icon.png, com.ea.sims3.MonkeyApp',
  'MicroEdition-Profile': 'MIDP-2.0',
  'MicroEdition-Configuration': 'CLDC-1.1',
};

export class MIDletStateChangeException extends Error {
  constructor(message = '') {
    super(message);
    this.name = 'MIDletStateChangeException';
  }
}

export abstract class MIDlet {
  getAppProperty(key: string): string | null {
    return PROPERTIES[key] ?? null;
  }

  notifyDestroyed(): void {
    exitApp();
  }

  notifyPaused(): void {}

  resumeRequest(): void {}

  platformRequest(url: string): boolean {
    console.log(`[platformRequest] ${url}`);
    return false;
  }

  checkPermission(permission: string): void {}

  abstract startApp(): void;

  abstract pauseApp(): void;

  abstract destroyApp(unconditional: boolean): void;
}
