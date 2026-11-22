// Generated from SdkMoreGames.java by tools/web/JavaToTs.java; keep this file in sync.
import { MIDlet, Image, compareStrings, Graphics, Font, bytesToString, chr } from '../runtime';
import { SdkString } from './SdkString';
import { SdkFeatures } from './SdkFeatures';
import { SdkText } from './SdkText';
import { SdkConfig } from './SdkConfig';

export abstract class SdkMoreGames {
	static keyState: Int8Array = new Int8Array(5);
	static dataLoaded: boolean = false;
	keySequence: number = 0;
	keyStep: number = 0;
	keyRemainder: number = -1;
	keyBits: number = 0;
	dirty: boolean = true;
	titleText: SdkString = null;
	bodyText: SdkString = null;
	midlet: MIDlet = null;
	fonts: any[] = null;
	available: boolean = false;
	productImage: Image = null;
	selectedIndex: number = 0;
	currentProduct: number = 0;
	buyUri: string = null;
	animationFrame: number = 0;
	productsLoaded: boolean = true;
	layoutValid: boolean = true;
	categoryUris: string[] = null;
	productNameIds: Int16Array = null;
	productDescIds: Int16Array = null;
	productPriceIds: Int16Array = null;
	productIndexes: Int32Array = null;
	productBuyUris: string[] = null;
	productCategoryUris: string[] = null;
	screen: number = 0;
	state: number = 0;
	textDirty: boolean = false;
	wrappedTitle: Int16Array = null;
	wrappedBody: Int16Array = null;
	wrappedFooter: Int16Array = null;
	productName: SdkString = null;
	productDescription: SdkString = null;
	productPrice: SdkString = null;
	titleX: number = 0;
	titleY: number = 0;
	lineSpacing: number = 6;
	bodyX: number = 0;
	bodyY: number = 0;
	buttonX: number = 0;
	buttonY: number = 0;
	buttonWidth: number = 0;
	buttonHeight: number = 0;
	textIds: Int16Array = new Int16Array(11);
	texts: SdkString[] = new Array(11).fill(null);
	screenHeight: number = -1;
	screenWidth: number = -1;
	abstract getImage(name: string): Image;
	setScreenSize(width: number, height: number): void {
		if (SdkFeatures.isLoggingEnabled() && (width <= 0 || height <= 0)) {
			SdkText.log("SDKMoreGamessetScreenSize:: was called with zero or negative screen size. Aborting...", 4);
		}
		let changed: boolean = width !== this.screenWidth || height !== this.screenHeight;
		this.screenWidth = width;
		this.screenHeight = height;
		if (this.available && changed) {
			this.parseProductData();
		}
	}
	reset(): void {
		if (SdkFeatures.isLoggingEnabled()) {
			if (this.midlet === null) {
				SdkText.log("SDKMoreGamesreset:: was called before the midlet instance was set. Aborting...", 4);
			}
			if (this.productNameIds === null || this.productDescIds === null || this.productPriceIds === null) {
				SdkText.log("SDKMoreGamesreset:: was called before seting the product data. Aborting...", 4);
			}
			if (!this.isAvailable()) {
				SdkText.log("SDKMoreGamesreset:: was called even though MoreGames is unavailable. Aborting...", 4);
			}
			if (this.fonts === null) {
				SdkText.log("SDKMoreGamesreset:: was called before the fonts were set. Aborting...", 4);
			}
		}
		if (this.productIndexes === null || SdkFeatures.isMoreGamesEnabled() && SdkMoreGames.dataLoaded) {
			this.loadProducts();
		}
		for (let index: number = 0; index < this.texts.length; index++) {
			this.texts[index] = SdkText.getString(this.textIds[index], null);
		}
		this.selectedIndex = 0;
		this.available = true;
		this.state = 0;
		this.selectProduct(0);
		SdkText.setFont(this.fonts[0]);
		this.wrappedTitle = SdkText.wrapText(this.texts[0], null, ((this.screenWidth - 10) | 0), (124 << 16 >> 16));
		this.wrappedBody = SdkText.wrapText(this.texts[4], null, ((this.screenWidth - 10) | 0), (124 << 16 >> 16));
	}
	handleKeys(): void {
		switch (this.state) {
			case 0:
				if ((this.keyBits & 2052) !== 0 && this.productIndexes.length > 0) {
					this.selectedIndex--;
					if (this.selectedIndex < 0) {
						this.selectedIndex = ((this.productIndexes.length - 1) | 0);
					}
					this.currentProduct = this.productIndexes[this.selectedIndex];
					this.selectProduct(this.selectedIndex);
				}
				else {
					if ((this.keyBits & 8200) !== 0 && this.productIndexes.length > 0) {
						this.selectedIndex++;
						this.selectedIndex = ((this.selectedIndex % this.productIndexes.length) | 0);
						this.currentProduct = this.productIndexes[this.selectedIndex];
						this.selectProduct(this.selectedIndex);
					}
					else {
						if (((this.keyBits & 513) !== 0 || (this.keyBits & 32770) !== 0) && this.productIndexes.length > 0 && this.productsLoaded && this.layoutValid) {
							if (this.animationFrame === 1) {
								this.animationFrame = 0;
							}
							else {
								this.animationFrame = 1;
							}
						}
						else {
							if ((this.keyBits & 64) !== 0) {
								this.available = false;
							}
							else {
								if ((this.keyBits & 4144) !== 0) {
									if (this.animationFrame === 0) {
										this.buyUri = this.productBuyUris[this.selectedIndex];
										if (SdkConfig.isDebugEnabled()) {
											this.titleText = this.texts[8];
											this.bodyText = this.texts[7];
										}
										else {
											this.titleText = this.texts[7];
											this.bodyText = this.texts[8];
										}
										this.state = 1;
									}
									else {
										if (this.animationFrame === 1) {
											if (this.screen === 1 || this.screen === 3 && (this.productCategoryUris[this.selectedIndex] === null || compareStrings(this.productCategoryUris[this.selectedIndex], "") === 0)) {
												if (SdkConfig.isDebugEnabled()) {
													this.titleText = this.texts[6];
													this.bodyText = null;
												}
												else {
													this.titleText = null;
													this.bodyText = this.texts[6];
												}
												this.state = 2;
											}
											else {
												if (SdkConfig.isDebugEnabled()) {
													this.titleText = this.texts[8];
													this.bodyText = this.texts[7];
												}
												else {
													this.titleText = this.texts[7];
													this.bodyText = this.texts[8];
												}
												this.buyUri = this.productCategoryUris[this.selectedIndex];
												this.state = 1;
											}
										}
									}
								}
							}
						}
					}
				}
				break;
			case 1:
				if ((this.keyBits & 64) !== 0) {
					if (SdkConfig.isDebugEnabled()) {
						this.titleText = this.texts[6];
						this.bodyText = this.texts[5];
					}
					else {
						this.titleText = this.texts[5];
						this.bodyText = this.texts[6];
					}
					this.state = 0;
				}
				else {
					if ((this.keyBits & 32) !== 0) {
						this.openBuyUri(this.buyUri);
					}
				}
				break;
			case 2:
				if ((this.keyBits & 64) !== 0) {
					if (SdkConfig.isDebugEnabled()) {
						this.titleText = this.texts[6];
						this.bodyText = this.texts[5];
					}
					else {
						this.titleText = this.texts[5];
						this.bodyText = this.texts[6];
					}
					this.state = 0;
				}
		}
		this.keyBits = 0;
	}
	draw(gfx: Graphics): void {
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
				if (this.productImage !== null) {
					gfx.drawImage(this.productImage, this.screenWidth >> 1, this.bodyX, SdkText.HCENTER | SdkText.TOP);
					if (this.productIndexes.length > 1) {
						this.drawTitle(gfx, (((((this.screenWidth - this.productImage.getWidth()) | 0) >> 2) - 4) | 0), this.bodyY);
						this.drawBody(gfx, ((((this.screenWidth - (((this.screenWidth - this.productImage.getWidth()) | 0) >> 2)) | 0) + 4) | 0), this.bodyY);
					}
					SdkText.setFont(this.fonts[0]);
					SdkText.drawWrappedString(this.productName, this.wrappedFooter, 1, this.wrappedFooter[0], this.screenWidth >> 1, this.buttonX, SdkText.HCENTER | SdkText.TOP);
				}
				else {
					SdkText.setFont(this.fonts[0]);
					SdkText.drawString(this.productDescription, this.screenWidth >> 1, this.bodyX, SdkText.HCENTER | SdkText.TOP);
					SdkText.drawWrappedString(this.productName, this.wrappedFooter, 1, this.wrappedFooter[0], this.screenWidth >> 1, this.buttonX, SdkText.HCENTER | SdkText.TOP);
					if (this.productIndexes.length > 1) {
						this.drawTitle(gfx, 1, this.bodyY);
						this.drawBody(gfx, ((this.screenWidth - 1) | 0), this.bodyY);
					}
				}
				if (this.productsLoaded) {
					this.drawText(gfx, this.buttonY, this.titleY, this.texts[10], this.animationFrame === 0);
				}
				if (this.layoutValid) {
					this.drawText(gfx, this.buttonWidth, this.titleY, this.productPrice, this.animationFrame === 1);
				}
				break;
			case 1:
				SdkText.setFont(this.fonts[0]);
				SdkText.drawWrappedString(this.texts[4], this.wrappedBody, 1, this.wrappedBody[0], this.screenWidth >> 1, ((this.screenHeight - this.buttonHeight) | 0) >> 1, SdkText.HCENTER | SdkText.VCENTER);
				break;
			case 2:
				SdkText.setFont(this.fonts[0]);
				SdkText.drawWrappedString(this.texts[0], this.wrappedTitle, 1, this.wrappedTitle[0], this.screenWidth >> 1, ((this.screenHeight - this.buttonHeight) | 0) >> 1, SdkText.HCENTER | SdkText.VCENTER);
		}
		if (this.dirty) {
			SdkText.setFont(this.fonts[3]);
			if (this.titleText !== null) {
				SdkText.drawString(this.titleText, 2, ((((this.screenHeight + SdkText.getLeadingSpacing()) | 0) - 2) | 0), SdkText.LEFT | SdkText.BOTTOM);
			}
			if (this.bodyText !== null) {
				SdkText.drawString(this.bodyText, ((this.screenWidth - 2) | 0), ((((this.screenHeight + SdkText.getLeadingSpacing()) | 0) - 2) | 0), SdkText.RIGHT | SdkText.BOTTOM);
			}
		}
	}
	setFonts(fonts: any[]): void {
		if (SdkFeatures.isLoggingEnabled()) {
			if (fonts !== null) {
				for (let index: number = 0; index < fonts.length; index++) {
					if (fonts[index] === null) {
						SdkText.log("SDKMoreGamessetFonts:: invalid parameter. The fonts must NOT be null.", 4);
					}
					else {
						if ((!(Array.isArray(fonts[index])) || !SdkFeatures.isRectangleFontEnabled() && !SdkFeatures.isFreeTypeFontEnabled()) && (!(fonts[index] instanceof Font) || !SdkFeatures.isSystemFontEnabled())) {
							SdkText.log("SDKMoreGamessetFonts:: invalid parameter. One of the objects in the fonts array (index " + index + ") does not appear to be a font", 3);
						}
					}
				}
			}
			else {
				SdkText.log("SDKMoreGamessetFonts:: invalid parameter. The font array is NULL.", 4);
			}
		}
		this.fonts = fonts;
	}
	processKey(keyCode: number): number {
		if (!SdkFeatures.isMoreGamesEnabled()) {
			if (this.available) {
				this.keyBits = this.keyBits | this.softKeyMask(keyCode);
			}
			return 0;
		}
		else {
			if (this.available) {
				this.keyBits = this.keyBits | this.softKeyMask(keyCode);
			}
			else {
				let digit: number = 0;
				if ((digit = SdkMoreGames.decodeKey(keyCode)) === -1) {
					this.keySequence = 0;
					this.keyStep = 0;
				}
				else {
					if (this.keyStep === 0) {
						this.keySequence = ((this.keySequence << 4) << 16 >> 16);
						this.keySequence = ((this.keySequence | ((digit + 1) | 0)) << 16 >> 16);
						if (this.keySequence === -26472) {
							this.keyRemainder = 3;
							this.keyStep = 1;
						}
						else {
							if (this.keySequence === -26461) {
								SdkMoreGames.keyState[4] = 1;
								SdkMoreGames.dataLoaded = true;
								this.keySequence = 0;
								if (SdkFeatures.isLoggingEnabled()) {
									SdkText.log("SDKMoreGamesprocessKey::Activated cheat type=4", 1);
								}
								return 1;
							}
							if (this.keySequence === -26475) {
								this.keyRemainder = 0;
								this.keyStep = 1;
							}
							else {
								if (this.keySequence === -26473) {
									this.keyRemainder = 1;
									this.keyStep = 1;
								}
								else {
									if (this.keySequence === -26476) {
										this.keyRemainder = 2;
										this.keyStep = 1;
									}
								}
							}
						}
					}
					else {
						if (this.keyStep === 1) {
							let outOfRange: boolean = false;
							if (this.keyRemainder === 0) {
								outOfRange = digit < 0 || digit > 3;
							}
							else {
								if (this.keyRemainder === 2 || this.keyRemainder === 1) {
									outOfRange = digit < 0 || digit > 2;
								}
							}
							if (!outOfRange) {
								SdkMoreGames.keyState[this.keyRemainder] = digit;
								SdkMoreGames.dataLoaded = true;
								if (SdkFeatures.isLoggingEnabled()) {
									SdkText.log("SDKMoreGamesprocessKey::Activated cheat type=" + this.keyRemainder + ", value=" + digit, 1);
								}
							}
							this.keyStep = 0;
							this.keySequence = 0;
							return 1;
						}
					}
				}
			}
			return 0;
		}
	}
	setProductData(data: Int8Array): void {
		if (SdkFeatures.isLoggingEnabled() && data[0] !== 0) {
			SdkText.log("SDKMoreGamessetProductData::Binary format version for the moregames data is incorrect. expected=0, actual=" + data[0], 4);
		}
		let offset: number = 1;
		offset++;
		let categoryCount: number = data[1];
		this.categoryUris = new Array(categoryCount).fill(null);
		this.productNameIds = new Int16Array(categoryCount);
		this.productDescIds = new Int16Array(categoryCount);
		this.productPriceIds = new Int16Array(categoryCount);
		for (let categoryIndex: number = 0; categoryIndex < categoryCount; categoryIndex++) {
			let nameLength: number = data[offset++];
			this.categoryUris[categoryIndex] = bytesToString(data, offset, nameLength);
			offset = ((offset + nameLength) | 0);
		}
		let descOffset: number = categoryCount << 1;
		let priceOffset: number = categoryCount << 2;
		for (let categoryIndex: number = 0; categoryIndex < categoryCount; categoryIndex++) {
			this.productNameIds[categoryIndex] = SdkText.readShort(data, offset);
			this.productDescIds[categoryIndex] = SdkText.readShort(data, ((offset + descOffset) | 0));
			this.productPriceIds[categoryIndex] = SdkText.readShort(data, ((offset + priceOffset) | 0));
			offset = ((offset + 2) | 0);
		}
		offset = ((offset + priceOffset) | 0);
		for (let textIndex: number = 0; textIndex < this.texts.length; textIndex++) {
			this.textIds[textIndex] = SdkText.readShort(data, offset);
			offset = ((offset + 2) | 0);
		}
	}
	isAvailable(): boolean {
		if (this.productIndexes === null || SdkFeatures.isMoreGamesEnabled() && SdkMoreGames.dataLoaded) {
			this.loadProducts();
		}
		return this.screen !== 0;
	}
	drawOverlay(gfx: Graphics): void {
		gfx.setClip(0, 0, this.screenWidth, this.screenHeight);
		gfx.setColor(16777215);
		gfx.fillRect(0, 0, this.screenWidth, this.screenHeight);
	}
	openBuyUri(uri: string): void {
		if (SdkFeatures.isLoggingEnabled() && this.midlet === null) {
			SdkText.log("SDKMoreGamesplatformRequestImpl::called without setting the midlet first", 4);
		}
		try {
			this.midlet.platformRequest(uri);
			this.midlet.notifyDestroyed();
			this.midlet.notifyDestroyed();
		}
		catch (error) {
			this.midlet.notifyDestroyed();
			if (SdkFeatures.isLoggingEnabled()) {
				SdkText.log("SDKMoreGamesplatformRequestImpl::'" + (error as any).message + "' received while doing native platformRequest", 4);
			}
		}
	}
	parseProductData(): void {
		this.titleY = (((this.screenWidth << 2) / 5) | 0);
		let usableHeight: number = this.screenHeight;
		this.buttonHeight = 0;
		if (this.dirty) {
			SdkText.setFont(this.fonts[3]);
			this.buttonHeight = ((SdkText.getLineHeight() - SdkText.getLeadingSpacing()) | 0);
			usableHeight = ((usableHeight - this.buttonHeight) | 0);
		}
		usableHeight = ((usableHeight - 2) | 0);
		if (this.layoutValid) {
			usableHeight = ((usableHeight - 5) | 0);
			if (this.animationFrame === 1) {
				SdkText.setFont(this.fonts[2]);
			}
			else {
				SdkText.setFont(this.fonts[1]);
			}
			usableHeight = ((usableHeight - (((SdkText.getLineHeight() - SdkText.getLeadingSpacing()) | 0))) | 0);
			this.buttonWidth = usableHeight;
			let priceWidth: number = 0;
			if ((priceWidth = ((((((((SdkText.stringWidth(this.productPrice) + 6) | 0) + SdkText.getLineHeight()) | 0) - SdkText.getLeadingSpacing()) | 0) + 6) | 0)) > this.titleY) {
				this.titleY = priceWidth;
			}
		}
		if (this.productsLoaded) {
			usableHeight = ((usableHeight - 5) | 0);
			if (this.animationFrame === 0) {
				SdkText.setFont(this.fonts[2]);
			}
			else {
				SdkText.setFont(this.fonts[1]);
			}
			usableHeight = ((usableHeight - (((SdkText.getLineHeight() - SdkText.getLeadingSpacing()) | 0))) | 0);
			this.buttonY = usableHeight;
			let loadedWidth: number = 0;
			if ((loadedWidth = ((((((((SdkText.stringWidth(this.texts[10]) + 6) | 0) + SdkText.getLineHeight()) | 0) - SdkText.getLeadingSpacing()) | 0) + 6) | 0)) > this.titleY) {
				this.titleY = loadedWidth;
			}
		}
		if (this.layoutValid && this.productsLoaded) {
			usableHeight = ((usableHeight - 2) | 0);
			this.buttonY = usableHeight;
		}
		usableHeight = ((usableHeight - 2) | 0);
		if (this.productImage !== null) {
			SdkText.setFont(this.fonts[0]);
			this.wrappedFooter = SdkText.wrapText(this.productName, null, ((this.screenWidth - 2) | 0), (124 << 16 >> 16));
			let footerTop: number = ((usableHeight - (((Math.imul(this.wrappedFooter[0], SdkText.getLineHeight()) - SdkText.getLeadingSpacing()) | 0))) | 0);
			this.buttonX = footerTop;
			usableHeight = ((footerTop - this.productImage.getHeight()) | 0);
			this.bodyX = usableHeight;
			this.bodyY = ((this.bodyX + (this.productImage.getHeight() >> 1)) | 0);
		}
		else {
			SdkText.setFont(this.fonts[0]);
			this.wrappedFooter = SdkText.wrapText(this.productName, null, ((this.screenWidth - 7) | 0), (124 << 16 >> 16));
			let plainFooterTop: number = ((usableHeight - Math.imul(this.wrappedFooter[0], SdkText.getLineHeight())) | 0);
			this.buttonX = plainFooterTop;
			usableHeight = ((plainFooterTop - SdkText.getLineHeight()) | 0);
			this.bodyX = usableHeight;
			this.bodyY = ((this.bodyX + (((SdkText.getLineHeight() - SdkText.getLeadingSpacing()) | 0) >> 1)) | 0);
		}
		usableHeight--;
		SdkText.setFont(this.fonts[4]);
		this.titleX = ((((((SdkText.getLineHeight() - SdkText.getLeadingSpacing()) | 0) + 1) | 0) + 1) | 0);
		if (usableHeight >= this.titleX) {
			this.textDirty = true;
			usableHeight = ((usableHeight - this.titleX) | 0);
		}
		else {
			this.textDirty = false;
		}
		if (usableHeight > 0) {
			let share: number = ((Math.imul(usableHeight, 3) / 10) | 0);
			let quarter1: number = usableHeight >> 2;
			let quarter2: number = usableHeight >> 2;
			this.bodyX = ((this.bodyX - (((usableHeight - share) | 0))) | 0);
			this.bodyY = ((this.bodyY - (((usableHeight - share) | 0))) | 0);
			this.buttonX = ((this.buttonX - (((((usableHeight - share) | 0) - quarter1) | 0))) | 0);
			this.buttonY = ((this.buttonY - (((((((usableHeight - share) | 0) - quarter1) | 0) - quarter2) | 0))) | 0);
			this.buttonWidth = ((this.buttonWidth - (((((((usableHeight - share) | 0) - quarter1) | 0) - quarter2) | 0))) | 0);
		}
	}
	loadProducts(): void {
		this.midlet = SdkText.midlet;
		if (SdkFeatures.isLoggingEnabled() && this.midlet === null) {
			SdkText.log("SDKMoreGamesinitialize::called before the midlet is set. Aborting...", 4);
		}
		let impl: string = null!;
		if ((impl = this.resolveUrl("MG_Impl")) === null) {
			this.screen = 0;
		}
		else {
			if (!((impl = impl.toLowerCase()) === "static")) {
				if ((impl === "wapstatic")) {
				}
				if ((impl === "wap")) {
					this.screen = 2;
				}
				else {
					if (!(impl === "wapstatic")) {
						this.screen = 0;
						return;
					}
					this.screen = 3;
				}
			}
			else {
				this.screen = 1;
			}
			if ((impl = this.resolveUrl("MG_PRODUCTS")) !== null && impl.length !== 0) {
				let validCount: number = 0;
				let tokenEnd: number = -1;
				let nextComma: number = 0;
				do {
					let token: string = null!;
					if ((nextComma = impl.indexOf(chr(44), ((tokenEnd + 1) | 0))) === -1) {
						token = impl.substring(((tokenEnd + 1) | 0));
					}
					else {
						token = impl.substring(((tokenEnd + 1) | 0), nextComma);
					}
					tokenEnd = nextComma;
					if ((token === "LOG")) {
						validCount++;
					}
					else {
						let knownCategory: boolean = false;
						for (let categoryIndex: number = 0; categoryIndex < this.categoryUris.length; categoryIndex++) {
							if ((this.categoryUris[categoryIndex] === token)) {
								knownCategory = true;
								break;
							}
						}
						if (knownCategory) {
							validCount++;
						}
					}
				}
				while (nextComma !== -1);
				if (validCount === 0) {
					if (SdkFeatures.isLoggingEnabled()) {
						SdkText.log("SDKMoreGamesinitialize::Found no valid advertised items in the JAD (MG_PRODUCTS property). More Games will be disabled.", 3);
					}
					this.screen = 0;
				}
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log("SDKMoreGamesinitialize::Found " + validCount + " advertised items", 1);
				}
				this.productIndexes = new Int32Array(validCount);
				this.productBuyUris = new Array(validCount).fill(null);
				this.productCategoryUris = new Array(validCount).fill(null);
				tokenEnd = -1;
				for (let productIndex: number = 0; productIndex < this.productIndexes.length; productIndex++) {
					let productTokenEnd: number = impl.indexOf(chr(44), ((tokenEnd + 1) | 0));
					let productToken: any = null;
					if (productTokenEnd === -1) {
						productToken = impl.substring(((tokenEnd + 1) | 0));
					}
					else {
						productToken = impl.substring(((tokenEnd + 1) | 0), productTokenEnd);
					}
					tokenEnd = productTokenEnd;
					if (productToken.equals("LOG")) {
						this.productIndexes[productIndex] = -1;
						this.productBuyUris[productIndex] = null;
						this.productCategoryUris[productIndex] = this.resolveUrl("MG_CATURI_LOG");
						if (this.productCategoryUris[productIndex] !== null) {
							this.productCategoryUris[productIndex] = this.productCategoryUris[productIndex].trim();
						}
						if (SdkFeatures.isLoggingEnabled()) {
							SdkText.log("SDKMoreGamesinitialize::Found Generic Category. CATURI=" + this.productCategoryUris[productIndex], 1);
						}
					}
					else {
						this.productIndexes[productIndex] = -2;
						this.productBuyUris[productIndex] = null;
						this.productCategoryUris[productIndex] = null;
						for (let categoryIndex: number = 0; categoryIndex < this.categoryUris.length; categoryIndex++) {
							if ((this.categoryUris[categoryIndex] === productToken)) {
								this.productIndexes[productIndex] = categoryIndex;
								this.productBuyUris[productIndex] = this.resolveUrl("MG_BUYURI_" + productToken);
								this.productCategoryUris[productIndex] = this.resolveUrl("MG_CATURI_" + productToken);
								if (this.productBuyUris[productIndex] !== null) {
									this.productBuyUris[productIndex] = this.productBuyUris[productIndex].trim();
								}
								if (this.productCategoryUris[productIndex] !== null) {
									this.productCategoryUris[productIndex] = this.productCategoryUris[productIndex].trim();
								}
								if (SdkFeatures.isLoggingEnabled()) {
									SdkText.log("SDKMoreGamesinitialize::Found Game " + productToken + ". CATURI=" + this.productCategoryUris[productIndex] + " BUYURI=" + this.productBuyUris[productIndex], 1);
								}
								break;
							}
						}
						if (this.productIndexes[productIndex] === -2) {
							if (SdkFeatures.isLoggingEnabled()) {
								SdkText.log("SDKMoreGamesinitialize::Game '" + productToken + "' mentioned in the JAD (MG_PRODUCTS property) does not exist. Ignoring...", 3);
							}
							productIndex--;
						}
					}
				}
			}
			else {
				if (SdkFeatures.isLoggingEnabled()) {
					SdkText.log("SDKMoreGamesinitialize::MG_PRODUCTS property in the JAD file is not set or could not be read. More Games will be disabled.", 3);
				}
				this.screen = 0;
			}
		}
	}
	resolveUrl(key: string): string {
		if (!SdkFeatures.isMoreGamesEnabled()) {
			return this.midlet.getAppProperty(key);
		}
		else {
			if (SdkMoreGames.dataLoaded) {
				if (key.startsWith("MG_CATURI_")) {
					if (SdkMoreGames.keyState[1] === 2) {
						return null;
					}
				}
				else {
					if (key.startsWith("MG_BUYURI_") && SdkMoreGames.keyState[2] === 2) {
						return null;
					}
				}
				if ((key === "MG_Impl")) {
					switch (SdkMoreGames.keyState[0]) {
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
				if ((key === "MG_PRODUCTS")) {
					let products: string = null!;
					if ((products = this.midlet.getAppProperty(key)) === null) {
						return null;
					}
					if (SdkMoreGames.keyState[3] === 0) {
						return "";
					}
					let productList: string = products.trim();
					if (SdkMoreGames.keyState[3] !== -1) {
						let commaIndex: number = -1;
						let skipped: number = 0;
						while (skipped < SdkMoreGames.keyState[3] && (commaIndex = productList.indexOf(chr(44), ((commaIndex + 1) | 0))) !== -1) {
							skipped++;
						}
						if (commaIndex !== -1) {
							productList = productList.substring(0, commaIndex);
						}
					}
					if (SdkMoreGames.keyState[4] !== -1) {
						let firstComma: number = 0;
						if ((firstComma = productList.indexOf(chr(44), 0)) === -1) {
							return productList;
						}
						productList = productList.substring(((firstComma + 1) | 0)) + chr(44) + productList.substring(0, firstComma);
					}
					return productList;
				}
			}
			return this.midlet.getAppProperty(key);
		}
	}
	static decodeKey(keyCode: number): number {
		return keyCode >= 48 && keyCode <= 57 ? ((((keyCode - 48) | 0)) << 24 >> 24) : -1;
	}
	softKeyMask(keyCode: number): number {
		if (keyCode === SdkConfig.softKey1()) {
			return 1;
		}
		else {
			if (keyCode === SdkConfig.softKey2()) {
				return 2;
			}
			else {
				if (keyCode === SdkConfig.softKey3()) {
					return 4;
				}
				else {
					if (keyCode === SdkConfig.softKey4()) {
						return 8;
					}
					else {
						if (keyCode === SdkConfig.softKey5()) {
							return 16;
						}
						else {
							if (keyCode === SdkConfig.softKey6()) {
								return SdkConfig.isDebugEnabled() ? 64 : 32;
							}
							else {
								if (keyCode === SdkConfig.softKey7()) {
									return SdkConfig.isDebugEnabled() ? 32 : 64;
								}
								else {
									let digit: number = 0;
									if ((digit = SdkMoreGames.decodeKey(keyCode)) !== -1) {
										if (digit === 5) {
											return 16;
										}
										if (digit === 2) {
											return 1;
										}
										if (digit === 8) {
											return 2;
										}
										if (digit === 4) {
											return 4;
										}
										if (digit === 6) {
											return 8;
										}
									}
									return 0;
								}
							}
						}
					}
				}
			}
		}
	}
	selectProduct(index: number): void {
		label71: {
			this.currentProduct = this.productIndexes[index];
			if (this.currentProduct !== -1) {
				this.productImage = this.getImage(this.categoryUris[this.currentProduct]);
				this.productName = SdkText.getString(this.productPriceIds[this.currentProduct], null);
				this.productPrice = SdkText.getString(this.productDescIds[this.currentProduct], null);
				this.productDescription = SdkText.getString(this.productNameIds[this.currentProduct], null);
				switch (this.screen) {
					case 1:
						this.productsLoaded = false;
						break;
					case 2:
						this.productsLoaded = this.productBuyUris[index] !== null && compareStrings(this.productBuyUris[index], "") !== 0;
						this.layoutValid = this.productCategoryUris[index] !== null && compareStrings(this.productCategoryUris[index], "") !== 0;
						if (this.productsLoaded) {
							this.animationFrame = 0;
						}
						else {
							if (this.layoutValid) {
								this.animationFrame = 1;
							}
							else {
								this.animationFrame = 2;
							}
						}
						break label71;
					case 3:
						this.layoutValid = true;
						if (this.productBuyUris[index] !== null && compareStrings(this.productBuyUris[index], "") !== 0) {
							this.productsLoaded = true;
							this.animationFrame = 0;
						}
						else {
							this.productsLoaded = false;
							this.animationFrame = 1;
						}
					default:
						break label71;
				}
			}
			else {
				this.productImage = this.getImage("LOG");
				this.productName = this.texts[1];
				this.productPrice = this.texts[2];
				this.productDescription = this.texts[3];
				this.productsLoaded = false;
				if (this.screen === 2 && (this.productCategoryUris[index] === null || compareStrings(this.productCategoryUris[index], "") === 0)) {
					this.layoutValid = false;
					this.animationFrame = 2;
					break label71;
				}
			}
			this.layoutValid = true;
			this.animationFrame = 1;
		}
		if (SdkConfig.isDebugEnabled()) {
			if (this.animationFrame !== 2) {
				this.bodyText = this.texts[5];
			}
			else {
				this.bodyText = null;
			}
			this.titleText = this.texts[6];
		}
		else {
			if (this.animationFrame !== 2) {
				this.titleText = this.texts[5];
			}
			else {
				this.titleText = null;
			}
			this.bodyText = this.texts[6];
		}
		this.parseProductData();
	}
	drawTitle(gfx: Graphics, x: number, y: number): void {
		x = ((x + (this.lineSpacing >> 1)) | 0);
		gfx.setColor(19855);
		for (let step: number = 0; step <= 8; step++) {
			gfx.drawLine(((x + step) | 0), ((y - step) | 0), ((x + step) | 0), ((y + step) | 0));
		}
		gfx.setColor(7314887);
		gfx.drawLine(x, y, ((x + 8) | 0), ((y - 8) | 0));
		gfx.drawLine(x, y, ((x + 8) | 0), ((y + 8) | 0));
		gfx.drawLine(((x + 8) | 0), ((y - 8) | 0), ((x + 8) | 0), ((y + 8) | 0));
	}
	drawBody(gfx: Graphics, x: number, y: number): void {
		x = ((x - (this.lineSpacing >> 1)) | 0);
		gfx.setColor(19855);
		for (let step: number = 0; step <= 8; step++) {
			gfx.drawLine(((x - step) | 0), ((y - step) | 0), ((x - step) | 0), ((y + step) | 0));
		}
		gfx.setColor(7314887);
		gfx.drawLine(x, y, ((x - 8) | 0), ((y - 8) | 0));
		gfx.drawLine(x, y, ((x - 8) | 0), ((y + 8) | 0));
		gfx.drawLine(((x - 8) | 0), ((y - 8) | 0), ((x - 8) | 0), ((y + 8) | 0));
	}
	drawText(gfx: Graphics, y: number, width: number, text: SdkString, highlighted: boolean): void {
		let drawArrow: number = 0;
		if (highlighted) {
			SdkText.setFont(this.fonts[2]);
			if (this.fonts[2] === this.fonts[1]) {
				drawArrow = 1;
			}
		}
		else {
			SdkText.setFont(this.fonts[1]);
		}
		let lineHeight: number = ((SdkText.getLineHeight() - SdkText.getLeadingSpacing()) | 0);
		let height: number = ((((lineHeight + 3) | 0) + 2) | 0);
		let x: number = ((this.screenWidth - width) | 0) >> 1;
		gfx.setColor(0);
		gfx.fillRect(x, y, width, height);
		gfx.setColor(19855);
		gfx.fillRect(x, y, ((width - 1) | 0), ((height - 1) | 0));
		gfx.setColor(7314887);
		gfx.drawLine(((x + 1) | 0), ((y + 1) | 0), ((((x + width) | 0) - 2) | 0), ((y + 1) | 0));
		gfx.drawLine(((x + 1) | 0), ((y + 1) | 0), ((x + 1) | 0), ((((y + height) | 0) - 3) | 0));
		if (drawArrow !== 0) {
			gfx.setColor(16777215);
			width = ((((x + (lineHeight >> 1)) | 0) + 3) | 0);
			drawArrow = ((y + (height >> 1)) | 0);
			lineHeight = (((lineHeight >> 1) - 1) | 0);
			for (let step: number = 0; step <= lineHeight; step++) {
				gfx.drawLine(((width - step) | 0), ((drawArrow - step) | 0), ((width - step) | 0), ((drawArrow + step) | 0));
			}
		}
		SdkText.drawString(text, this.screenWidth >> 1, ((y + 2) | 0), SdkText.TOP | SdkText.HCENTER);
	}
	constructor(width: number, height: number) {
		if (SdkFeatures.isLoggingEnabled() && (width <= 0 || height <= 0)) {
			SdkText.log("SDKMoreGamesSDKMoreGames::constructor was called with zero or negative screen size. Aborting...", 4);
		}
		this.screenWidth = width;
		this.screenHeight = height;
		if (SdkFeatures.isMoreGamesEnabled()) {
			for (let index: number = 0; index < SdkMoreGames.keyState.length; index++) {
				SdkMoreGames.keyState[index] = -1;
			}
		}
	}
}
