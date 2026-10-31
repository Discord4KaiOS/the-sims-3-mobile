package com.ea.sims3;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

public abstract class SdkMoreGames {
   private static final byte[] keyState = new byte[5];
   private static boolean dataLoaded = false;
   private short keySequence = 0;
   private int keyStep = 0;
   private int keyRemainder = -1;
   private int keyBits = 0;
   private boolean dirty = true;
   public SdkString titleText = null;
   public SdkString bodyText = null;
   private MIDlet midlet = null;
   private Object[] fonts = null;
   public boolean available = false;
   private Image productImage = null;
   private int selectedIndex = 0;
   private int currentProduct = 0;
   private String buyUri = null;
   private int animationFrame = 0;
   private boolean productsLoaded = true;
   private boolean layoutValid = true;
   private String[] categoryUris = null;
   private short[] productNameIds = null;
   private short[] productDescIds = null;
   private short[] productPriceIds = null;
   private int[] productIndexes = null;
   private String[] productBuyUris = null;
   private String[] productCategoryUris = null;
   private int screen = 0;
   private int state = 0;
   private boolean textDirty = false;
   private short[] wrappedTitle = null;
   private short[] wrappedBody = null;
   private short[] wrappedFooter = null;
   private SdkString productName = null;
   private SdkString productDescription = null;
   private SdkString productPrice = null;
   private int titleX = 0;
   private int titleY = 0;
   private int lineSpacing = 6;
   private int bodyX = 0;
   private int bodyY = 0;
   private int buttonX = 0;
   private int buttonY = 0;
   private int buttonWidth = 0;
   private int buttonHeight = 0;
   private short[] textIds = new short[11];
   private SdkString[] texts = new SdkString[11];
   private int screenHeight = -1;
   private int screenWidth = -1;

   public SdkMoreGames(int width, int height) {
      if (SdkFeatures.isLoggingEnabled() && (width <= 0 || height <= 0)) {
         SdkText.log("SDKMoreGamesSDKMoreGames::constructor was called with zero or negative screen size. Aborting...", 4);
      }

      this.screenWidth = width;
      this.screenHeight = height;
      if (SdkFeatures.isMoreGamesEnabled()) {
         for (int index = 0; index < keyState.length; index++) {
            keyState[index] = -1;
         }
      }
   }

   public abstract Image getImage(String name);

   public final void setScreenSize(int width, int height) {
      if (SdkFeatures.isLoggingEnabled() && (width <= 0 || height <= 0)) {
         SdkText.log("SDKMoreGamessetScreenSize:: was called with zero or negative screen size. Aborting...", 4);
      }

      boolean changed = width != this.screenWidth || height != this.screenHeight;
      this.screenWidth = width;
      this.screenHeight = height;
      if (this.available && changed) {
         this.parseProductData();
      }
   }

   public final void reset() {
      if (SdkFeatures.isLoggingEnabled()) {
         if (this.midlet == null) {
            SdkText.log("SDKMoreGamesreset:: was called before the midlet instance was set. Aborting...", 4);
         }

         if (this.productNameIds == null || this.productDescIds == null || this.productPriceIds == null) {
            SdkText.log("SDKMoreGamesreset:: was called before seting the product data. Aborting...", 4);
         }

         if (!this.isAvailable()) {
            SdkText.log("SDKMoreGamesreset:: was called even though MoreGames is unavailable. Aborting...", 4);
         }

         if (this.fonts == null) {
            SdkText.log("SDKMoreGamesreset:: was called before the fonts were set. Aborting...", 4);
         }
      }

      if (this.productIndexes == null || SdkFeatures.isMoreGamesEnabled() && dataLoaded) {
         this.loadProducts();
      }

      for (int index = 0; index < this.texts.length; index++) {
         this.texts[index] = SdkText.getString(this.textIds[index], null);
      }

      this.selectedIndex = 0;
      this.available = true;
      this.state = 0;
      this.selectProduct(0);
      SdkText.setFont(this.fonts[0]);
      this.wrappedTitle = SdkText.wrapText(this.texts[0], null, this.screenWidth - 10, (short)124);
      this.wrappedBody = SdkText.wrapText(this.texts[4], null, this.screenWidth - 10, (short)124);
   }

   public final void handleKeys() {
      switch (this.state) {
         case 0:
            if ((this.keyBits & 2052) != 0 && this.productIndexes.length > 0) {
               this.selectedIndex--;
               if (this.selectedIndex < 0) {
                  this.selectedIndex = this.productIndexes.length - 1;
               }

               this.currentProduct = this.productIndexes[this.selectedIndex];
               this.selectProduct(this.selectedIndex);
            } else if ((this.keyBits & 8200) != 0 && this.productIndexes.length > 0) {
               this.selectedIndex++;
               this.selectedIndex = this.selectedIndex % this.productIndexes.length;
               this.currentProduct = this.productIndexes[this.selectedIndex];
               this.selectProduct(this.selectedIndex);
            } else if (((this.keyBits & 513) != 0 || (this.keyBits & 32770) != 0) && this.productIndexes.length > 0 && this.productsLoaded && this.layoutValid) {
               if (this.animationFrame == 1) {
                  this.animationFrame = 0;
               } else {
                  this.animationFrame = 1;
               }
            } else if ((this.keyBits & 64) != 0) {
               this.available = false;
            } else if ((this.keyBits & 4144) != 0) {
               if (this.animationFrame == 0) {
                  this.buyUri = this.productBuyUris[this.selectedIndex];
                  if (SdkConfig.isDebugEnabled()) {
                     this.titleText = this.texts[8];
                     this.bodyText = this.texts[7];
                  } else {
                     this.titleText = this.texts[7];
                     this.bodyText = this.texts[8];
                  }

                  this.state = 1;
               } else if (this.animationFrame == 1) {
                  if (this.screen == 1 || this.screen == 3 && (this.productCategoryUris[this.selectedIndex] == null || this.productCategoryUris[this.selectedIndex].compareTo("") == 0)) {
                     if (SdkConfig.isDebugEnabled()) {
                        this.titleText = this.texts[6];
                        this.bodyText = null;
                     } else {
                        this.titleText = null;
                        this.bodyText = this.texts[6];
                     }

                     this.state = 2;
                  } else {
                     if (SdkConfig.isDebugEnabled()) {
                        this.titleText = this.texts[8];
                        this.bodyText = this.texts[7];
                     } else {
                        this.titleText = this.texts[7];
                        this.bodyText = this.texts[8];
                     }

                     this.buyUri = this.productCategoryUris[this.selectedIndex];
                     this.state = 1;
                  }
               }
            }
            break;
         case 1:
            if ((this.keyBits & 64) != 0) {
               if (SdkConfig.isDebugEnabled()) {
                  this.titleText = this.texts[6];
                  this.bodyText = this.texts[5];
               } else {
                  this.titleText = this.texts[5];
                  this.bodyText = this.texts[6];
               }

               this.state = 0;
            } else if ((this.keyBits & 32) != 0) {
               this.openBuyUri(this.buyUri);
            }
            break;
         case 2:
            if ((this.keyBits & 64) != 0) {
               if (SdkConfig.isDebugEnabled()) {
                  this.titleText = this.texts[6];
                  this.bodyText = this.texts[5];
               } else {
                  this.titleText = this.texts[5];
                  this.bodyText = this.texts[6];
               }

               this.state = 0;
            }
      }

      this.keyBits = 0;
   }

   public final void draw(Graphics gfx) {
      this.drawOverlay(gfx);
      SdkText.setGraphics(gfx);
      switch (this.state) {
         case 0:
            this.lineSpacing--;
            if (this.lineSpacing < 0) {
               this.lineSpacing = 6;
            }

            if (this.textDirty) {
               gfx.setColor(19855);
               SdkText.setFont(this.fonts[4]);
               gfx.fillRect(0, 0, this.screenWidth, this.titleX);
               SdkText.drawString(this.texts[9], this.screenWidth >> 1, 1, SdkText.HCENTER | SdkText.TOP);
            }

            if (this.productImage != null) {
               gfx.drawImage(this.productImage, this.screenWidth >> 1, this.bodyX, SdkText.HCENTER | SdkText.TOP);
               if (this.productIndexes.length > 1) {
                  this.drawTitle(gfx, (this.screenWidth - this.productImage.getWidth() >> 2) - 4, this.bodyY);
                  this.drawBody(gfx, this.screenWidth - (this.screenWidth - this.productImage.getWidth() >> 2) + 4, this.bodyY);
               }

               SdkText.setFont(this.fonts[0]);
               SdkText.drawWrappedString(this.productName, this.wrappedFooter, 1, this.wrappedFooter[0], this.screenWidth >> 1, this.buttonX, SdkText.HCENTER | SdkText.TOP);
            } else {
               SdkText.setFont(this.fonts[0]);
               SdkText.drawString(this.productDescription, this.screenWidth >> 1, this.bodyX, SdkText.HCENTER | SdkText.TOP);
               SdkText.drawWrappedString(this.productName, this.wrappedFooter, 1, this.wrappedFooter[0], this.screenWidth >> 1, this.buttonX, SdkText.HCENTER | SdkText.TOP);
               if (this.productIndexes.length > 1) {
                  this.drawTitle(gfx, 1, this.bodyY);
                  this.drawBody(gfx, this.screenWidth - 1, this.bodyY);
               }
            }

            if (this.productsLoaded) {
               this.drawText(gfx, this.buttonY, this.titleY, this.texts[10], this.animationFrame == 0);
            }

            if (this.layoutValid) {
               this.drawText(gfx, this.buttonWidth, this.titleY, this.productPrice, this.animationFrame == 1);
            }
            break;
         case 1:
            SdkText.setFont(this.fonts[0]);
            SdkText.drawWrappedString(this.texts[4], this.wrappedBody, 1, this.wrappedBody[0], this.screenWidth >> 1, this.screenHeight - this.buttonHeight >> 1, SdkText.HCENTER | SdkText.VCENTER);
            break;
         case 2:
            SdkText.setFont(this.fonts[0]);
            SdkText.drawWrappedString(this.texts[0], this.wrappedTitle, 1, this.wrappedTitle[0], this.screenWidth >> 1, this.screenHeight - this.buttonHeight >> 1, SdkText.HCENTER | SdkText.VCENTER);
      }

      if (this.dirty) {
         SdkText.setFont(this.fonts[3]);
         if (this.titleText != null) {
            SdkText.drawString(this.titleText, 2, this.screenHeight + SdkText.getLeadingSpacing() - 2, SdkText.LEFT | SdkText.BOTTOM);
         }

         if (this.bodyText != null) {
            SdkText.drawString(this.bodyText, this.screenWidth - 2, this.screenHeight + SdkText.getLeadingSpacing() - 2, SdkText.RIGHT | SdkText.BOTTOM);
         }
      }
   }

   public final void setFonts(Object[] fonts) {
      if (SdkFeatures.isLoggingEnabled()) {
         if (fonts != null) {
            for (int index = 0; index < fonts.length; index++) {
               if (fonts[index] == null) {
                  SdkText.log("SDKMoreGamessetFonts:: invalid parameter. The fonts must NOT be null.", 4);
               } else if ((!(fonts[index] instanceof Object[]) || !SdkFeatures.isRectangleFontEnabled() && !SdkFeatures.isFreeTypeFontEnabled())
                  && (!(fonts[index] instanceof Font) || !SdkFeatures.isSystemFontEnabled())) {
                  SdkText.log(
                     "SDKMoreGamessetFonts:: invalid parameter. One of the objects in the fonts array (index " + index + ") does not appear to be a font", 3
                  );
               }
            }
         } else {
            SdkText.log("SDKMoreGamessetFonts:: invalid parameter. The font array is NULL.", 4);
         }
      }

      this.fonts = fonts;
   }

   public final byte processKey(int keyCode) {
      if (!SdkFeatures.isMoreGamesEnabled()) {
         if (this.available) {
            this.keyBits = this.keyBits | this.softKeyMask(keyCode);
         }

         return 0;
      } else {
         if (this.available) {
            this.keyBits = this.keyBits | this.softKeyMask(keyCode);
         } else {
            byte digit;
            if ((digit = decodeKey(keyCode)) == -1) {
               this.keySequence = 0;
               this.keyStep = 0;
            } else if (this.keyStep == 0) {
               this.keySequence = (short)(this.keySequence << 4);
               this.keySequence = (short)(this.keySequence | digit + 1);
               if (this.keySequence == -26472) {
                  this.keyRemainder = 3;
                  this.keyStep = 1;
               } else {
                  if (this.keySequence == -26461) {
                     keyState[4] = 1;
                     dataLoaded = true;
                     this.keySequence = 0;
                     if (SdkFeatures.isLoggingEnabled()) {
                        SdkText.log("SDKMoreGamesprocessKey::Activated cheat type=4", 1);
                     }

                     return 1;
                  }

                  if (this.keySequence == -26475) {
                     this.keyRemainder = 0;
                     this.keyStep = 1;
                  } else if (this.keySequence == -26473) {
                     this.keyRemainder = 1;
                     this.keyStep = 1;
                  } else if (this.keySequence == -26476) {
                     this.keyRemainder = 2;
                     this.keyStep = 1;
                  }
               }
            } else if (this.keyStep == 1) {
               boolean outOfRange = false;
               if (this.keyRemainder == 0) {
                  outOfRange = digit < 0 || digit > 3;
               } else if (this.keyRemainder == 2 || this.keyRemainder == 1) {
                  outOfRange = digit < 0 || digit > 2;
               }

               if (!outOfRange) {
                  keyState[this.keyRemainder] = digit;
                  dataLoaded = true;
                  if (SdkFeatures.isLoggingEnabled()) {
                     SdkText.log("SDKMoreGamesprocessKey::Activated cheat type=" + this.keyRemainder + ", value=" + digit, 1);
                  }
               }

               this.keyStep = 0;
               this.keySequence = 0;
               return 1;
            }
         }

         return 0;
      }
   }

   public final void setProductData(byte[] data) {
      if (SdkFeatures.isLoggingEnabled() && data[0] != 0) {
         SdkText.log("SDKMoreGamessetProductData::Binary format version for the moregames data is incorrect. expected=0, actual=" + data[0], 4);
      }

      int offset = 1;
      offset++;
      byte categoryCount = data[1];
      this.categoryUris = new String[categoryCount];
      this.productNameIds = new short[categoryCount];
      this.productDescIds = new short[categoryCount];
      this.productPriceIds = new short[categoryCount];

      for (int categoryIndex = 0; categoryIndex < categoryCount; categoryIndex++) {
         byte nameLength = data[offset++];
         this.categoryUris[categoryIndex] = new String(data, offset, nameLength);
         offset += nameLength;
      }

      int descOffset = categoryCount << 1;
      int priceOffset = categoryCount << 2;

      for (int categoryIndex = 0; categoryIndex < categoryCount; categoryIndex++) {
         this.productNameIds[categoryIndex] = SdkText.readShort(data, offset);
         this.productDescIds[categoryIndex] = SdkText.readShort(data, offset + descOffset);
         this.productPriceIds[categoryIndex] = SdkText.readShort(data, offset + priceOffset);
         offset += 2;
      }

      offset += priceOffset;

      for (int textIndex = 0; textIndex < this.texts.length; textIndex++) {
         this.textIds[textIndex] = SdkText.readShort(data, offset);
         offset += 2;
      }
   }

   public final boolean isAvailable() {
      if (this.productIndexes == null || SdkFeatures.isMoreGamesEnabled() && dataLoaded) {
         this.loadProducts();
      }

      return this.screen != 0;
   }

   public final void drawOverlay(Graphics gfx) {
      gfx.setClip(0, 0, this.screenWidth, this.screenHeight);
      gfx.setColor(16777215);
      gfx.fillRect(0, 0, this.screenWidth, this.screenHeight);
   }

   public final void openBuyUri(String uri) {
      if (SdkFeatures.isLoggingEnabled() && this.midlet == null) {
         SdkText.log("SDKMoreGamesplatformRequestImpl::called without setting the midlet first", 4);
      }

      try {
         this.midlet.platformRequest(uri);
         this.midlet.notifyDestroyed();
         this.midlet.notifyDestroyed();
      } catch (Exception error) {
         this.midlet.notifyDestroyed();
         if (SdkFeatures.isLoggingEnabled()) {
            SdkText.log("SDKMoreGamesplatformRequestImpl::'" + error.getMessage() + "' received while doing native platformRequest", 4);
         }
      }
   }

   private void parseProductData() {
      this.titleY = (this.screenWidth << 2) / 5;
      int usableHeight = this.screenHeight;
      this.buttonHeight = 0;
      if (this.dirty) {
         SdkText.setFont(this.fonts[3]);
         this.buttonHeight = SdkText.getLineHeight() - SdkText.getLeadingSpacing();
         usableHeight -= this.buttonHeight;
      }

      usableHeight -= 2;
      if (this.layoutValid) {
         usableHeight -= 5;
         if (this.animationFrame == 1) {
            SdkText.setFont(this.fonts[2]);
         } else {
            SdkText.setFont(this.fonts[1]);
         }

         usableHeight -= SdkText.getLineHeight() - SdkText.getLeadingSpacing();
         this.buttonWidth = usableHeight;
         int priceWidth;
         if ((priceWidth = SdkText.stringWidth(this.productPrice) + 6 + SdkText.getLineHeight() - SdkText.getLeadingSpacing() + 6) > this.titleY) {
            this.titleY = priceWidth;
         }
      }

      if (this.productsLoaded) {
         usableHeight -= 5;
         if (this.animationFrame == 0) {
            SdkText.setFont(this.fonts[2]);
         } else {
            SdkText.setFont(this.fonts[1]);
         }

         usableHeight -= SdkText.getLineHeight() - SdkText.getLeadingSpacing();
         this.buttonY = usableHeight;
         int loadedWidth;
         if ((loadedWidth = SdkText.stringWidth(this.texts[10]) + 6 + SdkText.getLineHeight() - SdkText.getLeadingSpacing() + 6) > this.titleY) {
            this.titleY = loadedWidth;
         }
      }

      if (this.layoutValid && this.productsLoaded) {
         usableHeight -= 2;
         this.buttonY = usableHeight;
      }

      usableHeight -= 2;
      if (this.productImage != null) {
         SdkText.setFont(this.fonts[0]);
         this.wrappedFooter = SdkText.wrapText(this.productName, null, this.screenWidth - 2, (short)124);
         int footerTop = usableHeight - (this.wrappedFooter[0] * SdkText.getLineHeight() - SdkText.getLeadingSpacing());
         this.buttonX = footerTop;
         usableHeight = footerTop - this.productImage.getHeight();
         this.bodyX = usableHeight;
         this.bodyY = this.bodyX + (this.productImage.getHeight() >> 1);
      } else {
         SdkText.setFont(this.fonts[0]);
         this.wrappedFooter = SdkText.wrapText(this.productName, null, this.screenWidth - 7, (short)124);
         int plainFooterTop = usableHeight - this.wrappedFooter[0] * SdkText.getLineHeight();
         this.buttonX = plainFooterTop;
         usableHeight = plainFooterTop - SdkText.getLineHeight();
         this.bodyX = usableHeight;
         this.bodyY = this.bodyX + (SdkText.getLineHeight() - SdkText.getLeadingSpacing() >> 1);
      }

      usableHeight--;
      SdkText.setFont(this.fonts[4]);
      this.titleX = SdkText.getLineHeight() - SdkText.getLeadingSpacing() + 1 + 1;
      if (usableHeight >= this.titleX) {
         this.textDirty = true;
         usableHeight -= this.titleX;
      } else {
         this.textDirty = false;
      }

      if (usableHeight > 0) {
         int share = usableHeight * 3 / 10;
         int quarter1 = usableHeight >> 2;
         int quarter2 = usableHeight >> 2;
         this.bodyX -= usableHeight - share;
         this.bodyY -= usableHeight - share;
         this.buttonX -= usableHeight - share - quarter1;
         this.buttonY -= usableHeight - share - quarter1 - quarter2;
         this.buttonWidth -= usableHeight - share - quarter1 - quarter2;
      }
   }

   private void loadProducts() {
      this.midlet = SdkText.midlet;
      if (SdkFeatures.isLoggingEnabled() && this.midlet == null) {
         SdkText.log("SDKMoreGamesinitialize::called before the midlet is set. Aborting...", 4);
      }

      String impl;
      if ((impl = this.resolveUrl("MG_Impl")) == null) {
         this.screen = 0;
      } else {
         if (!(impl = impl.toLowerCase()).equals("static")) {
            if (impl.equals("wapstatic")) {
            }

            if (impl.equals("wap")) {
               this.screen = 2;
            } else {
               if (!impl.equals("wapstatic")) {
                  this.screen = 0;
                  return;
               }

               this.screen = 3;
            }
         } else {
            this.screen = 1;
         }

         if ((impl = this.resolveUrl("MG_PRODUCTS")) != null && impl.length() != 0) {
            int validCount = 0;
            int tokenEnd = -1;

            int nextComma;
            do {
               String token;
               if ((nextComma = impl.indexOf(44, tokenEnd + 1)) == -1) {
                  token = impl.substring(tokenEnd + 1);
               } else {
                  token = impl.substring(tokenEnd + 1, nextComma);
               }

               tokenEnd = nextComma;
               if (token.equals("LOG")) {
                  validCount++;
               } else {
                  boolean knownCategory = false;

                  for (int categoryIndex = 0; categoryIndex < this.categoryUris.length; categoryIndex++) {
                     if (this.categoryUris[categoryIndex].equals(token)) {
                        knownCategory = true;
                        break;
                     }
                  }

                  if (knownCategory) {
                     validCount++;
                  }
               }
            } while (nextComma != -1);

            if (validCount == 0) {
               if (SdkFeatures.isLoggingEnabled()) {
                  SdkText.log("SDKMoreGamesinitialize::Found no valid advertised items in the JAD (MG_PRODUCTS property). More Games will be disabled.", 3);
               }

               this.screen = 0;
            }

            if (SdkFeatures.isLoggingEnabled()) {
               SdkText.log("SDKMoreGamesinitialize::Found " + validCount + " advertised items", 1);
            }

            this.productIndexes = new int[validCount];
            this.productBuyUris = new String[validCount];
            this.productCategoryUris = new String[validCount];
            tokenEnd = -1;

            for (int productIndex = 0; productIndex < this.productIndexes.length; productIndex++) {
               int productTokenEnd = impl.indexOf(44, tokenEnd + 1);
               Object productToken = null;
               if (productTokenEnd == -1) {
                  productToken = impl.substring(tokenEnd + 1);
               } else {
                  productToken = impl.substring(tokenEnd + 1, productTokenEnd);
               }

               tokenEnd = productTokenEnd;
               if (productToken.equals("LOG")) {
                  this.productIndexes[productIndex] = -1;
                  this.productBuyUris[productIndex] = null;
                  this.productCategoryUris[productIndex] = this.resolveUrl("MG_CATURI_LOG");
                  if (this.productCategoryUris[productIndex] != null) {
                     this.productCategoryUris[productIndex] = this.productCategoryUris[productIndex].trim();
                  }

                  if (SdkFeatures.isLoggingEnabled()) {
                     SdkText.log("SDKMoreGamesinitialize::Found Generic Category. CATURI=" + this.productCategoryUris[productIndex], 1);
                  }
               } else {
                  this.productIndexes[productIndex] = -2;
                  this.productBuyUris[productIndex] = null;
                  this.productCategoryUris[productIndex] = null;

                  for (int categoryIndex = 0; categoryIndex < this.categoryUris.length; categoryIndex++) {
                     if (this.categoryUris[categoryIndex].equals(productToken)) {
                        this.productIndexes[productIndex] = categoryIndex;
                        this.productBuyUris[productIndex] = this.resolveUrl("MG_BUYURI_" + productToken);
                        this.productCategoryUris[productIndex] = this.resolveUrl("MG_CATURI_" + productToken);
                        if (this.productBuyUris[productIndex] != null) {
                           this.productBuyUris[productIndex] = this.productBuyUris[productIndex].trim();
                        }

                        if (this.productCategoryUris[productIndex] != null) {
                           this.productCategoryUris[productIndex] = this.productCategoryUris[productIndex].trim();
                        }

                        if (SdkFeatures.isLoggingEnabled()) {
                           SdkText.log("SDKMoreGamesinitialize::Found Game " + productToken + ". CATURI=" + this.productCategoryUris[productIndex] + " BUYURI=" + this.productBuyUris[productIndex], 1);
                        }
                        break;
                     }
                  }

                  if (this.productIndexes[productIndex] == -2) {
                     if (SdkFeatures.isLoggingEnabled()) {
                        SdkText.log(
                           "SDKMoreGamesinitialize::Game '" + productToken + "' mentioned in the JAD (MG_PRODUCTS property) does not exist. Ignoring...", 3
                        );
                     }

                     productIndex--;
                  }
               }
            }
         } else {
            if (SdkFeatures.isLoggingEnabled()) {
               SdkText.log("SDKMoreGamesinitialize::MG_PRODUCTS property in the JAD file is not set or could not be read. More Games will be disabled.", 3);
            }

            this.screen = 0;
         }
      }
   }

   private String resolveUrl(String key) {
      if (!SdkFeatures.isMoreGamesEnabled()) {
         return this.midlet.getAppProperty(key);
      } else {
         if (dataLoaded) {
            if (key.startsWith("MG_CATURI_")) {
               if (keyState[1] == 2) {
                  return null;
               }
            } else if (key.startsWith("MG_BUYURI_") && keyState[2] == 2) {
               return null;
            }

            if (key.equals("MG_Impl")) {
               switch (keyState[0]) {
                  case 0:
                     return "off";
                  case 1:
                     return "static";
                  case 2:
                     return "wap";
                  case 3:
                     return "wapstatic";
               }
            }

            if (key.equals("MG_PRODUCTS")) {
               String products;
               if ((products = this.midlet.getAppProperty(key)) == null) {
                  return null;
               }

               if (keyState[3] == 0) {
                  return "";
               }

               String productList = products.trim();
               if (keyState[3] != -1) {
                  int commaIndex = -1;
                  int skipped = 0;

                  while (skipped < keyState[3] && (commaIndex = productList.indexOf(44, commaIndex + 1)) != -1) {
                     skipped++;
                  }

                  if (commaIndex != -1) {
                     productList = productList.substring(0, commaIndex);
                  }
               }

               if (keyState[4] != -1) {
                  int firstComma;
                  if ((firstComma = productList.indexOf(44, 0)) == -1) {
                     return productList;
                  }

                  productList = productList.substring(firstComma + 1) + ',' + productList.substring(0, firstComma);
               }

               return productList;
            }
         }

         return this.midlet.getAppProperty(key);
      }
   }

   private static byte decodeKey(int keyCode) {
      return keyCode >= 48 && keyCode <= 57 ? (byte)(keyCode - 48) : -1;
   }

   private int softKeyMask(int keyCode) {
      if (keyCode == SdkConfig.softKey1()) {
         return 1;
      } else if (keyCode == SdkConfig.softKey2()) {
         return 2;
      } else if (keyCode == SdkConfig.softKey3()) {
         return 4;
      } else if (keyCode == SdkConfig.softKey4()) {
         return 8;
      } else if (keyCode == SdkConfig.softKey5()) {
         return 16;
      } else if (keyCode == SdkConfig.softKey6()) {
         return SdkConfig.isDebugEnabled() ? 64 : 32;
      } else if (keyCode == SdkConfig.softKey7()) {
         return SdkConfig.isDebugEnabled() ? 32 : 64;
      } else {
         byte digit;
         if ((digit = decodeKey(keyCode)) != -1) {
            if (digit == 5) {
               return 16;
            }

            if (digit == 2) {
               return 1;
            }

            if (digit == 8) {
               return 2;
            }

            if (digit == 4) {
               return 4;
            }

            if (digit == 6) {
               return 8;
            }
         }

         return 0;
      }
   }

   private void selectProduct(int index) {
      label71: {
         this.currentProduct = this.productIndexes[index];
         if (this.currentProduct != -1) {
            this.productImage = this.getImage(this.categoryUris[this.currentProduct]);
            this.productName = SdkText.getString(this.productPriceIds[this.currentProduct], null);
            this.productPrice = SdkText.getString(this.productDescIds[this.currentProduct], null);
            this.productDescription = SdkText.getString(this.productNameIds[this.currentProduct], null);
            switch (this.screen) {
               case 1:
                  this.productsLoaded = false;
                  break;
               case 2:
                  this.productsLoaded = this.productBuyUris[index] != null && this.productBuyUris[index].compareTo("") != 0;
                  this.layoutValid = this.productCategoryUris[index] != null && this.productCategoryUris[index].compareTo("") != 0;
                  if (this.productsLoaded) {
                     this.animationFrame = 0;
                  } else if (this.layoutValid) {
                     this.animationFrame = 1;
                  } else {
                     this.animationFrame = 2;
                  }
                  break label71;
               case 3:
                  this.layoutValid = true;
                  if (this.productBuyUris[index] != null && this.productBuyUris[index].compareTo("") != 0) {
                     this.productsLoaded = true;
                     this.animationFrame = 0;
                  } else {
                     this.productsLoaded = false;
                     this.animationFrame = 1;
                  }
               default:
                  break label71;
            }
         } else {
            this.productImage = this.getImage("LOG");
            this.productName = this.texts[1];
            this.productPrice = this.texts[2];
            this.productDescription = this.texts[3];
            this.productsLoaded = false;
            if (this.screen == 2 && (this.productCategoryUris[index] == null || this.productCategoryUris[index].compareTo("") == 0)) {
               this.layoutValid = false;
               this.animationFrame = 2;
               break label71;
            }
         }

         this.layoutValid = true;
         this.animationFrame = 1;
      }

      if (SdkConfig.isDebugEnabled()) {
         if (this.animationFrame != 2) {
            this.bodyText = this.texts[5];
         } else {
            this.bodyText = null;
         }

         this.titleText = this.texts[6];
      } else {
         if (this.animationFrame != 2) {
            this.titleText = this.texts[5];
         } else {
            this.titleText = null;
         }

         this.bodyText = this.texts[6];
      }

      this.parseProductData();
   }

   private void drawTitle(Graphics gfx, int x, int y) {
      x += this.lineSpacing >> 1;
      gfx.setColor(19855);

      for (int step = 0; step <= 8; step++) {
         gfx.drawLine(x + step, y - step, x + step, y + step);
      }

      gfx.setColor(7314887);
      gfx.drawLine(x, y, x + 8, y - 8);
      gfx.drawLine(x, y, x + 8, y + 8);
      gfx.drawLine(x + 8, y - 8, x + 8, y + 8);
   }

   private void drawBody(Graphics gfx, int x, int y) {
      x -= this.lineSpacing >> 1;
      gfx.setColor(19855);

      for (int step = 0; step <= 8; step++) {
         gfx.drawLine(x - step, y - step, x - step, y + step);
      }

      gfx.setColor(7314887);
      gfx.drawLine(x, y, x - 8, y - 8);
      gfx.drawLine(x, y, x - 8, y + 8);
      gfx.drawLine(x - 8, y - 8, x - 8, y + 8);
   }

   private void drawText(Graphics gfx, int y, int width, SdkString text, boolean highlighted) {
      int drawArrow = 0;
      if (highlighted) {
         SdkText.setFont(this.fonts[2]);
         if (this.fonts[2] == this.fonts[1]) {
            drawArrow = 1;
         }
      } else {
         SdkText.setFont(this.fonts[1]);
      }

      int lineHeight = SdkText.getLineHeight() - SdkText.getLeadingSpacing();
      int height = lineHeight + 3 + 2;
      int x = this.screenWidth - width >> 1;
      gfx.setColor(0);
      gfx.fillRect(x, y, width, height);
      gfx.setColor(19855);
      gfx.fillRect(x, y, width - 1, height - 1);
      gfx.setColor(7314887);
      gfx.drawLine(x + 1, y + 1, x + width - 2, y + 1);
      gfx.drawLine(x + 1, y + 1, x + 1, y + height - 3);
      if (drawArrow != 0) {
         gfx.setColor(16777215);
         width = x + (lineHeight >> 1) + 3;
         drawArrow = y + (height >> 1);
         lineHeight = (lineHeight >> 1) - 1;

         for (int step = 0; step <= lineHeight; step++) {
            gfx.drawLine(width - step, drawArrow - step, width - step, drawArrow + step);
         }
      }

      SdkText.drawString(text, this.screenWidth >> 1, y + 2, SdkText.TOP | SdkText.HCENTER);
   }
}
