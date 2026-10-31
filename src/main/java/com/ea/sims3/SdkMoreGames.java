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

   public SdkMoreGames(int var1, int var2) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled() && (var1 <= 0 || var2 <= 0)) {
         com.ea.sims3.SdkText.log("SDKMoreGamesSDKMoreGames::constructor was called with zero or negative screen size. Aborting...", 4);
      }

      this.screenWidth = var1;
      this.screenHeight = var2;
      if (com.ea.sims3.SdkFeatures.isMoreGamesEnabled()) {
         for (int var3 = 0; var3 < keyState.length; var3++) {
            keyState[var3] = -1;
         }
      }
   }

   public abstract Image getImage(String var1);

   public final void setScreenSize(int width, int height) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled() && (width <= 0 || height <= 0)) {
         com.ea.sims3.SdkText.log("SDKMoreGamessetScreenSize:: was called with zero or negative screen size. Aborting...", 4);
      }

      boolean changed = width != this.screenWidth || height != this.screenHeight;
      this.screenWidth = width;
      this.screenHeight = height;
      if (this.available && changed) {
         this.parseProductData();
      }
   }

   public final void reset() {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (this.midlet == null) {
            com.ea.sims3.SdkText.log("SDKMoreGamesreset:: was called before the midlet instance was set. Aborting...", 4);
         }

         if (this.productNameIds == null || this.productDescIds == null || this.productPriceIds == null) {
            com.ea.sims3.SdkText.log("SDKMoreGamesreset:: was called before seting the product data. Aborting...", 4);
         }

         if (!this.isAvailable()) {
            com.ea.sims3.SdkText.log("SDKMoreGamesreset:: was called even though MoreGames is unavailable. Aborting...", 4);
         }

         if (this.fonts == null) {
            com.ea.sims3.SdkText.log("SDKMoreGamesreset:: was called before the fonts were set. Aborting...", 4);
         }
      }

      if (this.productIndexes == null || com.ea.sims3.SdkFeatures.isMoreGamesEnabled() && dataLoaded) {
         this.loadProducts();
      }

      for (int i = 0; i < this.texts.length; i++) {
         this.texts[i] = com.ea.sims3.SdkText.getString(this.textIds[i], null);
      }

      this.selectedIndex = 0;
      this.available = true;
      this.state = 0;
      this.selectProduct(0);
      com.ea.sims3.SdkText.setFont(this.fonts[0]);
      this.wrappedTitle = com.ea.sims3.SdkText.wrapText(this.texts[0], null, this.screenWidth - 10, (short)124);
      this.wrappedBody = com.ea.sims3.SdkText.wrapText(this.texts[4], null, this.screenWidth - 10, (short)124);
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
                  if (com.ea.sims3.SdkConfig.isDebugEnabled()) {
                     this.titleText = this.texts[8];
                     this.bodyText = this.texts[7];
                  } else {
                     this.titleText = this.texts[7];
                     this.bodyText = this.texts[8];
                  }

                  this.state = 1;
               } else if (this.animationFrame == 1) {
                  if (this.screen == 1 || this.screen == 3 && (this.productCategoryUris[this.selectedIndex] == null || this.productCategoryUris[this.selectedIndex].compareTo("") == 0)) {
                     if (com.ea.sims3.SdkConfig.isDebugEnabled()) {
                        this.titleText = this.texts[6];
                        this.bodyText = null;
                     } else {
                        this.titleText = null;
                        this.bodyText = this.texts[6];
                     }

                     this.state = 2;
                  } else {
                     if (com.ea.sims3.SdkConfig.isDebugEnabled()) {
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
               if (com.ea.sims3.SdkConfig.isDebugEnabled()) {
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
               if (com.ea.sims3.SdkConfig.isDebugEnabled()) {
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

   public final void draw(Graphics g) {
      this.drawOverlay(g);
      com.ea.sims3.SdkText.setGraphics(g);
      switch (this.state) {
         case 0:
            this.lineSpacing--;
            if (this.lineSpacing < 0) {
               this.lineSpacing = 6;
            }

            if (this.textDirty) {
               g.setColor(19855);
               com.ea.sims3.SdkText.setFont(this.fonts[4]);
               g.fillRect(0, 0, this.screenWidth, this.titleX);
               com.ea.sims3.SdkText.drawString(this.texts[9], this.screenWidth >> 1, 1, com.ea.sims3.SdkText.HCENTER | com.ea.sims3.SdkText.TOP);
            }

            if (this.productImage != null) {
               g.drawImage(this.productImage, this.screenWidth >> 1, this.bodyX, com.ea.sims3.SdkText.HCENTER | com.ea.sims3.SdkText.TOP);
               if (this.productIndexes.length > 1) {
                  this.drawTitle(g, (this.screenWidth - this.productImage.getWidth() >> 2) - 4, this.bodyY);
                  this.drawBody(g, this.screenWidth - (this.screenWidth - this.productImage.getWidth() >> 2) + 4, this.bodyY);
               }

               com.ea.sims3.SdkText.setFont(this.fonts[0]);
               com.ea.sims3.SdkText.drawWrappedString(this.productName, this.wrappedFooter, 1, this.wrappedFooter[0], this.screenWidth >> 1, this.buttonX, com.ea.sims3.SdkText.HCENTER | com.ea.sims3.SdkText.TOP);
            } else {
               com.ea.sims3.SdkText.setFont(this.fonts[0]);
               com.ea.sims3.SdkText.drawString(this.productDescription, this.screenWidth >> 1, this.bodyX, com.ea.sims3.SdkText.HCENTER | com.ea.sims3.SdkText.TOP);
               com.ea.sims3.SdkText.drawWrappedString(this.productName, this.wrappedFooter, 1, this.wrappedFooter[0], this.screenWidth >> 1, this.buttonX, com.ea.sims3.SdkText.HCENTER | com.ea.sims3.SdkText.TOP);
               if (this.productIndexes.length > 1) {
                  this.drawTitle(g, 1, this.bodyY);
                  this.drawBody(g, this.screenWidth - 1, this.bodyY);
               }
            }

            if (this.productsLoaded) {
               this.drawText(g, this.buttonY, this.titleY, this.texts[10], this.animationFrame == 0);
            }

            if (this.layoutValid) {
               this.drawText(g, this.buttonWidth, this.titleY, this.productPrice, this.animationFrame == 1);
            }
            break;
         case 1:
            com.ea.sims3.SdkText.setFont(this.fonts[0]);
            com.ea.sims3.SdkText.drawWrappedString(this.texts[4], this.wrappedBody, 1, this.wrappedBody[0], this.screenWidth >> 1, this.screenHeight - this.buttonHeight >> 1, com.ea.sims3.SdkText.HCENTER | com.ea.sims3.SdkText.VCENTER);
            break;
         case 2:
            com.ea.sims3.SdkText.setFont(this.fonts[0]);
            com.ea.sims3.SdkText.drawWrappedString(this.texts[0], this.wrappedTitle, 1, this.wrappedTitle[0], this.screenWidth >> 1, this.screenHeight - this.buttonHeight >> 1, com.ea.sims3.SdkText.HCENTER | com.ea.sims3.SdkText.VCENTER);
      }

      if (this.dirty) {
         com.ea.sims3.SdkText.setFont(this.fonts[3]);
         if (this.titleText != null) {
            com.ea.sims3.SdkText.drawString(this.titleText, 2, this.screenHeight + com.ea.sims3.SdkText.getLeadingSpacing() - 2, com.ea.sims3.SdkText.LEFT | com.ea.sims3.SdkText.BOTTOM);
         }

         if (this.bodyText != null) {
            com.ea.sims3.SdkText.drawString(this.bodyText, this.screenWidth - 2, this.screenHeight + com.ea.sims3.SdkText.getLeadingSpacing() - 2, com.ea.sims3.SdkText.RIGHT | com.ea.sims3.SdkText.BOTTOM);
         }
      }
   }

   public final void setFonts(Object[] var1) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
         if (var1 != null) {
            for (int var2 = 0; var2 < var1.length; var2++) {
               if (var1[var2] == null) {
                  com.ea.sims3.SdkText.log("SDKMoreGamessetFonts:: invalid parameter. The fonts must NOT be null.", 4);
               } else if ((!(var1[var2] instanceof Object[]) || !com.ea.sims3.SdkFeatures.isRectangleFontEnabled() && !com.ea.sims3.SdkFeatures.isFreeTypeFontEnabled())
                  && (!(var1[var2] instanceof Font) || !com.ea.sims3.SdkFeatures.isSystemFontEnabled())) {
                  com.ea.sims3.SdkText.log(
                     "SDKMoreGamessetFonts:: invalid parameter. One of the objects in the fonts array (index " + var2 + ") does not appear to be a font", 3
                  );
               }
            }
         } else {
            com.ea.sims3.SdkText.log("SDKMoreGamessetFonts:: invalid parameter. The font array is NULL.", 4);
         }
      }

      this.fonts = var1;
   }

   public final byte processKey(int keyCode) {
      if (!com.ea.sims3.SdkFeatures.isMoreGamesEnabled()) {
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
                     if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                        com.ea.sims3.SdkText.log("SDKMoreGamesprocessKey::Activated cheat type=4", 1);
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
               boolean var2 = false;
               if (this.keyRemainder == 0) {
                  var2 = digit < 0 || digit > 3;
               } else if (this.keyRemainder == 2 || this.keyRemainder == 1) {
                  var2 = digit < 0 || digit > 2;
               }

               if (!var2) {
                  keyState[this.keyRemainder] = digit;
                  dataLoaded = true;
                  if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                     com.ea.sims3.SdkText.log("SDKMoreGamesprocessKey::Activated cheat type=" + this.keyRemainder + ", value=" + digit, 1);
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
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled() && data[0] != 0) {
         com.ea.sims3.SdkText.log("SDKMoreGamessetProductData::Binary format version for the moregames data is incorrect. expected=0, actual=" + data[0], 4);
      }

      int var2 = 1;
      var2++;
      byte var3 = data[1];
      this.categoryUris = new String[var3];
      this.productNameIds = new short[var3];
      this.productDescIds = new short[var3];
      this.productPriceIds = new short[var3];

      for (int var4 = 0; var4 < var3; var4++) {
         byte var5 = data[var2++];
         this.categoryUris[var4] = new String(data, var2, var5);
         var2 += var5;
      }

      int var10 = var3 << 1;
      int var11 = var3 << 2;

      for (int var6 = 0; var6 < var3; var6++) {
         this.productNameIds[var6] = com.ea.sims3.SdkText.readShort(data, var2);
         this.productDescIds[var6] = com.ea.sims3.SdkText.readShort(data, var2 + var10);
         this.productPriceIds[var6] = com.ea.sims3.SdkText.readShort(data, var2 + var11);
         var2 += 2;
      }

      var2 += var11;

      for (int var12 = 0; var12 < this.texts.length; var12++) {
         this.textIds[var12] = com.ea.sims3.SdkText.readShort(data, var2);
         var2 += 2;
      }
   }

   public final boolean isAvailable() {
      if (this.productIndexes == null || com.ea.sims3.SdkFeatures.isMoreGamesEnabled() && dataLoaded) {
         this.loadProducts();
      }

      return this.screen != 0;
   }

   public final void drawOverlay(Graphics var1) {
      var1.setClip(0, 0, this.screenWidth, this.screenHeight);
      var1.setColor(16777215);
      var1.fillRect(0, 0, this.screenWidth, this.screenHeight);
   }

   public final void openBuyUri(String var1) {
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled() && this.midlet == null) {
         com.ea.sims3.SdkText.log("SDKMoreGamesplatformRequestImpl::called without setting the midlet first", 4);
      }

      try {
         this.midlet.platformRequest(var1);
         this.midlet.notifyDestroyed();
         this.midlet.notifyDestroyed();
      } catch (Exception var2) {
         this.midlet.notifyDestroyed();
         if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
            com.ea.sims3.SdkText.log("SDKMoreGamesplatformRequestImpl::'" + var2.getMessage() + "' received while doing native platformRequest", 4);
         }
      }
   }

   private void parseProductData() {
      this.titleY = (this.screenWidth << 2) / 5;
      int var1 = this.screenHeight;
      this.buttonHeight = 0;
      if (this.dirty) {
         com.ea.sims3.SdkText.setFont(this.fonts[3]);
         this.buttonHeight = com.ea.sims3.SdkText.getLineHeight() - com.ea.sims3.SdkText.getLeadingSpacing();
         var1 -= this.buttonHeight;
      }

      var1 -= 2;
      if (this.layoutValid) {
         var1 -= 5;
         if (this.animationFrame == 1) {
            com.ea.sims3.SdkText.setFont(this.fonts[2]);
         } else {
            com.ea.sims3.SdkText.setFont(this.fonts[1]);
         }

         var1 -= com.ea.sims3.SdkText.getLineHeight() - com.ea.sims3.SdkText.getLeadingSpacing();
         this.buttonWidth = var1;
         int var2;
         if ((var2 = com.ea.sims3.SdkText.stringWidth(this.productPrice) + 6 + com.ea.sims3.SdkText.getLineHeight() - com.ea.sims3.SdkText.getLeadingSpacing() + 6) > this.titleY) {
            this.titleY = var2;
         }
      }

      if (this.productsLoaded) {
         var1 -= 5;
         if (this.animationFrame == 0) {
            com.ea.sims3.SdkText.setFont(this.fonts[2]);
         } else {
            com.ea.sims3.SdkText.setFont(this.fonts[1]);
         }

         var1 -= com.ea.sims3.SdkText.getLineHeight() - com.ea.sims3.SdkText.getLeadingSpacing();
         this.buttonY = var1;
         int var13;
         if ((var13 = com.ea.sims3.SdkText.stringWidth(this.texts[10]) + 6 + com.ea.sims3.SdkText.getLineHeight() - com.ea.sims3.SdkText.getLeadingSpacing() + 6) > this.titleY) {
            this.titleY = var13;
         }
      }

      if (this.layoutValid && this.productsLoaded) {
         var1 -= 2;
         this.buttonY = var1;
      }

      var1 -= 2;
      if (this.productImage != null) {
         com.ea.sims3.SdkText.setFont(this.fonts[0]);
         this.wrappedFooter = com.ea.sims3.SdkText.wrapText(this.productName, null, this.screenWidth - 2, (short)124);
         int var9 = var1 - (this.wrappedFooter[0] * com.ea.sims3.SdkText.getLineHeight() - com.ea.sims3.SdkText.getLeadingSpacing());
         this.buttonX = var9;
         var1 = var9 - this.productImage.getHeight();
         this.bodyX = var1;
         this.bodyY = this.bodyX + (this.productImage.getHeight() >> 1);
      } else {
         com.ea.sims3.SdkText.setFont(this.fonts[0]);
         this.wrappedFooter = com.ea.sims3.SdkText.wrapText(this.productName, null, this.screenWidth - 7, (short)124);
         int var11 = var1 - this.wrappedFooter[0] * com.ea.sims3.SdkText.getLineHeight();
         this.buttonX = var11;
         var1 = var11 - com.ea.sims3.SdkText.getLineHeight();
         this.bodyX = var1;
         this.bodyY = this.bodyX + (com.ea.sims3.SdkText.getLineHeight() - com.ea.sims3.SdkText.getLeadingSpacing() >> 1);
      }

      var1--;
      com.ea.sims3.SdkText.setFont(this.fonts[4]);
      this.titleX = com.ea.sims3.SdkText.getLineHeight() - com.ea.sims3.SdkText.getLeadingSpacing() + 1 + 1;
      if (var1 >= this.titleX) {
         this.textDirty = true;
         var1 -= this.titleX;
      } else {
         this.textDirty = false;
      }

      if (var1 > 0) {
         int var14 = var1 * 3 / 10;
         int var3 = var1 >> 2;
         int var4 = var1 >> 2;
         this.bodyX -= var1 - var14;
         this.bodyY -= var1 - var14;
         this.buttonX -= var1 - var14 - var3;
         this.buttonY -= var1 - var14 - var3 - var4;
         this.buttonWidth -= var1 - var14 - var3 - var4;
      }
   }

   private void loadProducts() {
      this.midlet = com.ea.sims3.SdkText.midlet;
      if (com.ea.sims3.SdkFeatures.isLoggingEnabled() && this.midlet == null) {
         com.ea.sims3.SdkText.log("SDKMoreGamesinitialize::called before the midlet is set. Aborting...", 4);
      }

      String i;
      if ((i = this.resolveUrl("MG_Impl")) == null) {
         this.screen = 0;
      } else {
         if (!(i = i.toLowerCase()).equals("static")) {
            if (i.equals("wapstatic")) {
            }

            if (i.equals("wap")) {
               this.screen = 2;
            } else {
               if (!i.equals("wapstatic")) {
                  this.screen = 0;
                  return;
               }

               this.screen = 3;
            }
         } else {
            this.screen = 1;
         }

         if ((i = this.resolveUrl("MG_PRODUCTS")) != null && i.length() != 0) {
            int var2 = 0;
            int var3 = -1;

            int var4;
            do {
               String var5;
               if ((var4 = i.indexOf(44, var3 + 1)) == -1) {
                  var5 = i.substring(var3 + 1);
               } else {
                  var5 = i.substring(var3 + 1, var4);
               }

               var3 = var4;
               if (var5.equals("LOG")) {
                  var2++;
               } else {
                  boolean var6 = false;

                  for (int var7 = 0; var7 < this.categoryUris.length; var7++) {
                     if (this.categoryUris[var7].equals(var5)) {
                        var6 = true;
                        break;
                     }
                  }

                  if (var6) {
                     var2++;
                  }
               }
            } while (var4 != -1);

            if (var2 == 0) {
               if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                  com.ea.sims3.SdkText.log("SDKMoreGamesinitialize::Found no valid advertised items in the JAD (MG_PRODUCTS property). More Games will be disabled.", 3);
               }

               this.screen = 0;
            }

            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               com.ea.sims3.SdkText.log("SDKMoreGamesinitialize::Found " + var2 + " advertised items", 1);
            }

            this.productIndexes = new int[var2];
            this.productBuyUris = new String[var2];
            this.productCategoryUris = new String[var2];
            var3 = -1;

            for (int var11 = 0; var11 < this.productIndexes.length; var11++) {
               int var12 = i.indexOf(44, var3 + 1);
               Object var13 = null;
               if (var12 == -1) {
                  var13 = i.substring(var3 + 1);
               } else {
                  var13 = i.substring(var3 + 1, var12);
               }

               var3 = var12;
               if (var13.equals("LOG")) {
                  this.productIndexes[var11] = -1;
                  this.productBuyUris[var11] = null;
                  this.productCategoryUris[var11] = this.resolveUrl("MG_CATURI_LOG");
                  if (this.productCategoryUris[var11] != null) {
                     this.productCategoryUris[var11] = this.productCategoryUris[var11].trim();
                  }

                  if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                     com.ea.sims3.SdkText.log("SDKMoreGamesinitialize::Found Generic Category. CATURI=" + this.productCategoryUris[var11], 1);
                  }
               } else {
                  this.productIndexes[var11] = -2;
                  this.productBuyUris[var11] = null;
                  this.productCategoryUris[var11] = null;

                  for (int var15 = 0; var15 < this.categoryUris.length; var15++) {
                     if (this.categoryUris[var15].equals(var13)) {
                        this.productIndexes[var11] = var15;
                        this.productBuyUris[var11] = this.resolveUrl("MG_BUYURI_" + var13);
                        this.productCategoryUris[var11] = this.resolveUrl("MG_CATURI_" + var13);
                        if (this.productBuyUris[var11] != null) {
                           this.productBuyUris[var11] = this.productBuyUris[var11].trim();
                        }

                        if (this.productCategoryUris[var11] != null) {
                           this.productCategoryUris[var11] = this.productCategoryUris[var11].trim();
                        }

                        if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                           com.ea.sims3.SdkText.log("SDKMoreGamesinitialize::Found Game " + var13 + ". CATURI=" + this.productCategoryUris[var11] + " BUYURI=" + this.productBuyUris[var11], 1);
                        }
                        break;
                     }
                  }

                  if (this.productIndexes[var11] == -2) {
                     if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
                        com.ea.sims3.SdkText.log(
                           "SDKMoreGamesinitialize::Game '" + var13 + "' mentioned in the JAD (MG_PRODUCTS property) does not exist. Ignoring...", 3
                        );
                     }

                     var11--;
                  }
               }
            }
         } else {
            if (com.ea.sims3.SdkFeatures.isLoggingEnabled()) {
               com.ea.sims3.SdkText.log("SDKMoreGamesinitialize::MG_PRODUCTS property in the JAD file is not set or could not be read. More Games will be disabled.", 3);
            }

            this.screen = 0;
         }
      }
   }

   private String resolveUrl(String var1) {
      if (!com.ea.sims3.SdkFeatures.isMoreGamesEnabled()) {
         return this.midlet.getAppProperty(var1);
      } else {
         if (dataLoaded) {
            if (var1.startsWith("MG_CATURI_")) {
               if (keyState[1] == 2) {
                  return null;
               }
            } else if (var1.startsWith("MG_BUYURI_") && keyState[2] == 2) {
               return null;
            }

            if (var1.equals("MG_Impl")) {
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

            if (var1.equals("MG_PRODUCTS")) {
               String var3;
               if ((var3 = this.midlet.getAppProperty(var1)) == null) {
                  return null;
               }

               if (keyState[3] == 0) {
                  return "";
               }

               String var4 = var3.trim();
               if (keyState[3] != -1) {
                  int var5 = -1;
                  int var2 = 0;

                  while (var2 < keyState[3] && (var5 = var4.indexOf(44, var5 + 1)) != -1) {
                     var2++;
                  }

                  if (var5 != -1) {
                     var4 = var4.substring(0, var5);
                  }
               }

               if (keyState[4] != -1) {
                  int var6;
                  if ((var6 = var4.indexOf(44, 0)) == -1) {
                     return var4;
                  }

                  var4 = var4.substring(var6 + 1) + ',' + var4.substring(0, var6);
               }

               return var4;
            }
         }

         return this.midlet.getAppProperty(var1);
      }
   }

   private static byte decodeKey(int var0) {
      return var0 >= 48 && var0 <= 57 ? (byte)(var0 - 48) : -1;
   }

   private int softKeyMask(int keyCode) {
      if (keyCode == com.ea.sims3.SdkConfig.softKey1()) {
         return 1;
      } else if (keyCode == com.ea.sims3.SdkConfig.softKey2()) {
         return 2;
      } else if (keyCode == com.ea.sims3.SdkConfig.softKey3()) {
         return 4;
      } else if (keyCode == com.ea.sims3.SdkConfig.softKey4()) {
         return 8;
      } else if (keyCode == com.ea.sims3.SdkConfig.softKey5()) {
         return 16;
      } else if (keyCode == com.ea.sims3.SdkConfig.softKey6()) {
         return com.ea.sims3.SdkConfig.isDebugEnabled() ? 64 : 32;
      } else if (keyCode == com.ea.sims3.SdkConfig.softKey7()) {
         return com.ea.sims3.SdkConfig.isDebugEnabled() ? 32 : 64;
      } else {
         byte var2;
         if ((var2 = decodeKey(keyCode)) != -1) {
            if (var2 == 5) {
               return 16;
            }

            if (var2 == 2) {
               return 1;
            }

            if (var2 == 8) {
               return 2;
            }

            if (var2 == 4) {
               return 4;
            }

            if (var2 == 6) {
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
            this.productName = com.ea.sims3.SdkText.getString(this.productPriceIds[this.currentProduct], null);
            this.productPrice = com.ea.sims3.SdkText.getString(this.productDescIds[this.currentProduct], null);
            this.productDescription = com.ea.sims3.SdkText.getString(this.productNameIds[this.currentProduct], null);
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

      if (com.ea.sims3.SdkConfig.isDebugEnabled()) {
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

   private void drawTitle(Graphics var1, int var2, int var3) {
      var2 += this.lineSpacing >> 1;
      var1.setColor(19855);

      for (int var4 = 0; var4 <= 8; var4++) {
         var1.drawLine(var2 + var4, var3 - var4, var2 + var4, var3 + var4);
      }

      var1.setColor(7314887);
      var1.drawLine(var2, var3, var2 + 8, var3 - 8);
      var1.drawLine(var2, var3, var2 + 8, var3 + 8);
      var1.drawLine(var2 + 8, var3 - 8, var2 + 8, var3 + 8);
   }

   private void drawBody(Graphics var1, int var2, int var3) {
      var2 -= this.lineSpacing >> 1;
      var1.setColor(19855);

      for (int var4 = 0; var4 <= 8; var4++) {
         var1.drawLine(var2 - var4, var3 - var4, var2 - var4, var3 + var4);
      }

      var1.setColor(7314887);
      var1.drawLine(var2, var3, var2 - 8, var3 - 8);
      var1.drawLine(var2, var3, var2 - 8, var3 + 8);
      var1.drawLine(var2 - 8, var3 - 8, var2 - 8, var3 + 8);
   }

   private void drawText(Graphics var1, int var2, int var3, SdkString var4, boolean var5) {
      int var6 = 0;
      if (var5) {
         com.ea.sims3.SdkText.setFont(this.fonts[2]);
         if (this.fonts[2] == this.fonts[1]) {
            var6 = 1;
         }
      } else {
         com.ea.sims3.SdkText.setFont(this.fonts[1]);
      }

      int var5b = com.ea.sims3.SdkText.getLineHeight() - com.ea.sims3.SdkText.getLeadingSpacing();  // var5 is the boolean parameter above
      int var7 = var5b + 3 + 2;
      int var8 = this.screenWidth - var3 >> 1;
      var1.setColor(0);
      var1.fillRect(var8, var2, var3, var7);
      var1.setColor(19855);
      var1.fillRect(var8, var2, var3 - 1, var7 - 1);
      var1.setColor(7314887);
      var1.drawLine(var8 + 1, var2 + 1, var8 + var3 - 2, var2 + 1);
      var1.drawLine(var8 + 1, var2 + 1, var8 + 1, var2 + var7 - 3);
      if (var6 != 0) {
         var1.setColor(16777215);
         var3 = var8 + (var5b >> 1) + 3;
         var6 = var2 + (var7 >> 1);
         var5b = (var5b >> 1) - 1;

         for (int var13 = 0; var13 <= var5b; var13++) {
            var1.drawLine(var3 - var13, var6 - var13, var3 - var13, var6 + var13);
         }
      }

      com.ea.sims3.SdkText.drawString(var4, this.screenWidth >> 1, var2 + 2, com.ea.sims3.SdkText.TOP | com.ea.sims3.SdkText.HCENTER);
   }
}
