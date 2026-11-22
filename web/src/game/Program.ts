// Generated from Program.java by tools/web/JavaToTs.java; keep this file in sync.
import { exitApp } from '../runtime';
import { MonkeyApp } from './MonkeyApp';

export class Program {
	static main(args: string[]): void {
		try {
			let midlet: MonkeyApp = new MonkeyApp();
			midlet.startApp();
		}
		catch (error) {
			console.error(error);
			exitApp();
		}
	}
	constructor() {
	}
}
