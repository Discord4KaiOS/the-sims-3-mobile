// Generated from PromoImages.java by tools/web/JavaToTs.java; keep this file in sync.
import { Image } from '../runtime';
import { SdkMoreGames } from './SdkMoreGames';
import { GameCanvas } from './GameCanvas';
import { SpriteRenderer } from './SpriteRenderer';

export class PromoImages extends SdkMoreGames {
	static PROMO_IMAGE_NAMES: string[] = ["TETPOP", "SPORE1", "MNOPWW"];
	getImage(name: string): Image {
		for (let promoIndex: number = ((PromoImages.PROMO_IMAGE_NAMES.length - 1) | 0); promoIndex !== -1; promoIndex--) {
			if ((name === PromoImages.PROMO_IMAGE_NAMES[promoIndex])) {
				let variant: number = 0;
				if (promoIndex === 2) {
					variant = GameCanvas.instance().language;
				}
				return SpriteRenderer.getTileImage(195, promoIndex, variant);
			}
		}
		return null;
	}
	constructor(width: number, height: number) {
		super(width, height);
	}
}
