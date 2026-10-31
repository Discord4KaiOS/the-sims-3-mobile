package com.ea.sims3;

import javax.microedition.lcdui.Image;

public final class PromoImages extends SdkMoreGames implements SpriteIndex {
   private static final String[] PROMO_IMAGE_NAMES = new String[]{"TETPOP", "SPORE1", "MNOPWW"};

   public PromoImages(int width, int height) {
      super(width, height);
   }

   public final Image getImage(String name) {
      for (int promoIndex = PROMO_IMAGE_NAMES.length - 1; promoIndex != -1; promoIndex--) {
         if (name.equals(PROMO_IMAGE_NAMES[promoIndex])) {
            int variant = 0;
            if (promoIndex == 2) {
               variant = GameCanvas.instance().language;
            }

            return SpriteRenderer.getTileImage(195, promoIndex, variant);
         }
      }

      return null;
   }
}
