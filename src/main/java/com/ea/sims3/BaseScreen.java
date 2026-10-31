package com.ea.sims3;

import javax.microedition.lcdui.Graphics;

public abstract class BaseScreen implements SpriteIndex, SoundIndex, Resources {
   public final GameCanvas canvas;
   public final int[] hitRect;
   public int listY;
   private int visibleLineCount;
   public int textLineHeight;
   public int panelWidth;
   public int dialogWidth;
   public int panelHeight;
   public int rowHeight;
   private static final int[] rectScratch = new int[6];
   private final short[] valueSlots = new short[11];
   public Animator leftArrowAnim;
   public Animator rightArrowAnim;
   public int itemCount;
   private int lineHeight;
   public int scrollOffset;
   private int visibleRowCount;
   public int selectedIndex;
   private int menuScroll;

   public boolean matchesInput(int keyState, int prevKeyState, int keyMask, int prevKeyMask) {
      if ((prevKeyState & prevKeyMask) == 0 && (keyState & keyMask) == 0) {
         return false;
      } else {
         this.canvas.releaseSoftKeys();
         this.canvas.clearAction(keyMask);
         return true;
      }
   }

   public BaseScreen(GameCanvas gameCanvas) {
      this.canvas = gameCanvas;
      this.hitRect = new int[4];
   }

   public abstract void setLanguage(int language);

   public abstract void stop();

   public abstract void pause();

   public abstract void render(Graphics graphics);

   public abstract void update(int delta, int flags);

   public abstract void keyPressed(int keyCode);

   public abstract void onTick(int delta);

   public final void drawPanel(Graphics graphics, int style) {
      GameCanvas canvas = this.canvas;
      this.drawTextBlock(graphics, style, 0, 0, canvas.getWidth(), canvas.getHeight() - 15);
   }

   public final void drawPanelBelow(Graphics graphics, int style, int top) {
      GameCanvas canvas = this.canvas;
      this.drawTextBlock(graphics, style, 0, top, canvas.getWidth(), canvas.getHeight() - 15 - top);
   }

   public final void initLayout() {
      GameCanvas canvas = this.canvas;
      this.textLineHeight = canvas.lineHeight(1);
      this.panelWidth = 216;
      this.dialogWidth = this.panelWidth * 4 / 5;
      this.listY = this.panelWidth * 17 / 20 - scrollbarWidth();
      this.panelHeight = 192;
      this.visibleLineCount = canvas.getHeight() * 3 / 5 / canvas.lineHeight(1);
      this.rowHeight = canvas.lineHeight(1) + 2;
      this.leftArrowAnim = new Animator();
      this.rightArrowAnim = new Animator();
      this.leftArrowAnim.play(560, 4);
      this.rightArrowAnim.play(562, 4);
   }

   public final void showLeftArrow() {
      this.leftArrowAnim.play(559, 16);
   }

   public final void showRightArrow() {
      this.rightArrowAnim.play(561, 16);
   }

   public final void advanceArrows(int delta) {
      this.leftArrowAnim.advance(delta);
      this.rightArrowAnim.advance(delta);
      if (!this.leftArrowAnim.isRunning()) {
         this.leftArrowAnim.play(560, 20);
      }

      if (!this.rightArrowAnim.isRunning()) {
         this.rightArrowAnim.play(562, 20);
      }
   }

   public final void setValue(int index, int value) {
      this.valueSlots[index] = (short)value;
   }

   public final void drawTextBlock(Graphics graphics, int textId, int x, int y, int width, int height) {
      this.drawParagraphs(graphics, this.textBlock(textId), x, y, width, height);
   }

   public final void drawParagraphs(Graphics graphics, short[] text, int x, int y, int width, int height) {
      int[] cursor = rectScratch;
      rectScratch[0] = x;
      cursor[1] = y;
      cursor[2] = width;
      cursor[3] = 0;
      this.layoutParagraphs(null, text, cursor);
      cursor[0] = x;
      cursor[2] = width;
      int measuredHeight = cursor[3];
      if (this.totalMeasurement(text) != 0) {
         cursor[1] = y;
         cursor[3] = height - measuredHeight;
      } else {
         cursor[1] = y + (height - measuredHeight >> 1);
         cursor[3] = measuredHeight;
      }

      this.layoutParagraphs(graphics, text, cursor);
   }

   private void layoutParagraphs(Graphics graphics, short[] text, int[] cursor) {
      int x = cursor[0];
      int y = cursor[1];
      int width = cursor[2];
      int height = cursor[3];
      int totalHeight = 0;
      int totalExtra = 0;

      for (byte index = 0; index < text.length; index += 2) {
         short kind = text[index];
         short value = text[index + 1];
         cursor[4] = value;
         this.renderElement(null, kind, cursor);
         int lineHeight = cursor[3];
         totalExtra += this.measureParagraph(kind, value);
         totalHeight += lineHeight;
      }

      if (graphics != null) {
         int limit = height;
         if (totalExtra > 0) {
            limit = height + totalHeight;
         }

         cursor[0] = x;
         cursor[1] = y;
         cursor[2] = width;
         int used = 0;

         for (byte row = 0; row < text.length; row += 2) {
            short kind2 = text[row];
            short value2 = text[row + 1];
            cursor[4] = value2;
            int extra;
            if ((extra = this.measureParagraph(kind2, value2)) != 0) {
               int allocated = height * extra / totalExtra;
               cursor[3] = allocated;
               this.renderElement(graphics, kind2, cursor);
            } else {
               cursor[3] = limit - used;
               this.renderElement(graphics, kind2, cursor);
            }

            used += cursor[3];
         }
      }

      if (graphics == null) {
         cursor[3] = totalHeight;
      } else {
         cursor[3] = height + totalHeight;
      }
   }

   private final int measureParagraph(int kind, int value) {
      int result = 0;
      if (kind == 22) {
         return value;
      } else {
         if (kind == 27) {
            result = 1;
         } else if (kind == 29) {
            result = this.totalMeasurement(this.textBlock(value));
         }

         return result;
      }
   }

   private final int totalMeasurement(short[] text) {
      int total = 0;

      for (byte index = 0; index < text.length; index += 2) {
         short kind = text[index];
         short value = text[index + 1];
         total += this.measureParagraph(kind, value);
      }

      return total;
   }

   private void renderElement(Graphics graphics, int elementType, int[] cursor) {
      GameCanvas canvas = this.canvas;
      int x = cursor[0];
      int y = cursor[1];
      int width = cursor[2];
      int height = cursor[3];
      int value = cursor[4];
      int advance = 0;
      switch (elementType) {
         case 0:
            if (graphics != null) {
               graphics.setColor(0);
               graphics.drawRect(x, y, width, height);
            }
            break;
         case 1:
         case 2:
         case 3:
         case 4:
            if (elementType == 4) {
               width = this.panelWidth;
               x = this.canvas.getWidth() - width >> 1;
            }

            if (graphics != null) {
               this.drawDialogFrame(graphics, x, y, width, height, value, elementType);
            }

            x += 5;
            y += 22;
            width -= 10;
            advance = 27;
            break;
         case 5:
            if (graphics != null) {
               drawThickBorder(graphics, x, y, width, height);
            }
            break;
         case 6:
            if (graphics != null) {
               this.drawWindowBox(graphics, x, y, width, height);
            }

            x += 8;
            y += 8;
            width -= 16;
            advance = 16;
            break;
         case 7:
            if (graphics != null) {
               this.drawRaisedBox(graphics, x, y, width, height);
            }

            x += 4;
            y += 4;
            width -= 8;
            advance = 8;
         case 8:
         default:
            break;
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
            SdkString label;
            if (graphics != null && (label = this.textFor(value)) != null) {
               int anchorX = x;
               int anchorY = y + 1;
               byte anchor = 20;
               if (elementType == 10 || elementType == 13) {
                  anchorX = x + width;
                  anchor = 24;
               } else if (elementType == 11 || elementType == 14) {
                  anchorX = x + (width >> 1);
                  anchor = 17;
               }

               int alignFlag = elementType != 12 && elementType != 13 && elementType != 14 ? 1 : 0;
               canvas.drawString(label, alignFlag, anchorX, anchorY, anchor);
            }
            break;
         case 15:
            advance = this.rowHeight;
            y += advance;
            break;
         case 16:
         case 17:
            advance = Math.max(value, canvas.wrappedLineCount()) * this.textLineHeight;
            if (graphics != null) {
               if (elementType == 17) {
                  advance = height;
               }

               canvas.drawWrappedText(1, x + (width >> 1), y + (advance >> 1), 3);
            }

            y += advance;
            break;
         case 18:
            advance = 1;
            if (graphics != null) {
               int rectX = x + 1;
               int rectW = width - 3;
               SpriteRenderer.setColor(graphics, value);
               graphics.fillRect(rectX, y, rectW, 1);
            }

            y++;
            break;
         case 19:
            int fullW = canvas.getWidth();
            int topY = y + 3;
            int innerH = canvas.getHeight() - 15 - 3 - topY;
            if (graphics != null) {
               SpriteRenderer.setColor(graphics, 77);
               graphics.fillRect(0, y, fullW, 3);
               SpriteRenderer.setColor(graphics, 76);
               graphics.fillRect(0, topY, fullW, innerH);
               y = topY;
               advance = height - innerH;
            } else {
               advance = height;
               y = topY;
            }

            x = 0;
            width = fullW;
            break;
         case 20:
            if (graphics != null) {
               int barHeight = this.rowHeight + 2;
               int panelW = canvas.getWidth();
               int lineY = y - 2;
               SpriteRenderer.setColor(graphics, 77);
               graphics.fillRect(0, lineY, panelW, 1);
               graphics.fillRect(0, lineY + barHeight - 1, canvas.getWidth(), 1);
               SpriteRenderer.setColor(graphics, 1);
               graphics.fillRect(0, lineY + 1, panelW, barHeight - 2);
               int arrowX1 = x + width - 25 + 3;
               int arrowX2 = x + width + 3;
               SpriteRenderer.drawFrame(graphics, 660, 0, arrowX1, y);
               SpriteRenderer.drawFrame(graphics, 659, 0, arrowX2, y);
            }
            break;
         case 21:
            int rowHeight = this.rowHeight + 8;
            if (graphics != null) {
               int innerW = width * 4 / 5;
               int innerX = x + (width - innerW >> 1);
               this.drawWindowBox(graphics, innerX, y, innerW, rowHeight);
               int labelX = innerX + (innerW >> 1);
               int labelY = y + (rowHeight >> 1) + 2;
               canvas.drawString(this.textFor(value), 0, labelX, labelY, 3);
            }

            advance = rowHeight;
            y += rowHeight;
            break;
         case 22:
            if (graphics != null) {
               advance = height;
               y += height;
            }
            break;
         case 23:
            x += value;
            width -= value << 1;
            y += value;
            advance = value << 1;
            break;
         case 24:
            x += value;
            width -= value << 1;
            break;
         case 25:
            y += value;
            advance = value << 1;
            break;
         case 26:
            advance = value;
            y += value;
            break;
         case 27:
            if (graphics != null) {
               this.drawScrollList(graphics, x, y, width, height, value);
               advance = height;
               y += height;
            }
            break;
         case 28:
            this.drawTextBox(graphics, cursor);
            advance = cursor[3];
            y += advance;
            break;
         case 29:
            short[] textBlock = this.textBlock(value);
            this.layoutParagraphs(graphics, textBlock, cursor);
            advance = cursor[3];
            y += advance;
            break;
         case 30:
            advance = ((TitleScreen)this).drawTextBox(graphics, x, y, width);
            y += advance;
            break;
         case 31:
         case 33:
         case 36:
            advance = this.drawMenuList(graphics, x, y, width, elementType);
            y += advance;
            break;
         case 32:
            advance = ((GameScreen)this).drawSkillHeader(graphics, x, y, width);
            y += advance;
            break;
         case 34:
            advance = ((GameScreen)this).drawSkillRow(graphics, x, y, width, value);
            y += advance;
            break;
         case 35:
            advance = ((GameScreen)this).drawFieldRow(graphics, x, y, width);
            y += advance;
            break;
         case 37:
            ((GameScreen)this).drawMenuIcon(graphics, cursor);
            return;
         case 38:
            advance = ((GameScreen)this).drawObjectIconRow(graphics, x, y, width);
            y += advance;
      }

      cursor[0] = x;
      cursor[1] = y;
      cursor[2] = width;
      cursor[3] = advance;
   }

   public final void drawDialogFrame(Graphics graphics, int x, int y, int width, int height, int titleId, int style) {
      GameCanvas canvas = this.canvas;
      if (style == 4) {
         this.drawListBox(graphics, x, y, width, height);
      }

      int clipX = graphics.getClipX();
      int clipY = graphics.getClipY();
      int clipW = graphics.getClipWidth();
      int clipH = graphics.getClipHeight();
      int capW = SpriteRenderer.frameWidth(582, 0);
      int capH = SpriteRenderer.frameHeight(582, 0);
      SpriteRenderer.drawFrame(graphics, 582, 0, x, y);
      SpriteRenderer.drawFrame(graphics, 582, 2, x + width - capW, y);
      graphics.setClip(x + capW, clipY, width - (capW << 1), clipH);
      SpriteRenderer.drawFrame(graphics, 582, 1, x + capW, y);
      graphics.setClip(clipX, clipY, clipW, clipH);
      if (style != 4) {
         SpriteRenderer.setColor(graphics, 0);
         graphics.fillRect(x, y + capH, 1, height - capH);
         graphics.fillRect(x + width - 1, y + capH, 1, height - capH);
         graphics.fillRect(x, y + height - 1, width, 1);
         SpriteRenderer.setColor(graphics, 76);
         graphics.fillRect(x + 1, y + capH, width - 2, height - capH - 1);
      }

      if (titleId != -1) {
         int titleX = x + (width >> 1);
         int titleY = y + 5;
         canvas.drawString(this.textFor(titleId), 0, titleX, titleY, 17);
      }

      if (style == 2) {
         int arrowY = y + 3;
         int leftArrowX = x + 4;
         int rightArrowX = x + width - 4;
         if (GameCanvas.landscape) {
            leftArrowX = x + 56;
            rightArrowX = x + width - 56;
         }

         if (((GameScreen)this).friendLevel != 0 || GameScreen.screenState != 11 && GameScreen.screenState != 10) {
            this.leftArrowAnim.draw(graphics, leftArrowX, arrowY);
            this.rightArrowAnim.draw(graphics, rightArrowX, arrowY);
         }
      }

      if (style == 3) {
         titleId = this.valueSlotIndex(titleId);
         short cornerFrame = -1;
         if (titleId == 209) {
            cornerFrame = 666;
         } else if (titleId == 205) {
            cornerFrame = 668;
         } else if (titleId == 208) {
            cornerFrame = 667;
         } else if (titleId == 204) {
            cornerFrame = 669;
         }

         if (cornerFrame != -1) {
            int[] hitRect = this.hitRect;
            SpriteRenderer.hitTestMirrored(this.hitRect, cornerFrame, 0, 0);
            graphics.setClip(x + hitRect[0], y + hitRect[1], hitRect[2], hitRect[3]);
            SpriteRenderer.drawFrame(graphics, cornerFrame, 0, x, y);
            graphics.setClip(x + width - hitRect[2] + hitRect[0], y + hitRect[1], hitRect[2], hitRect[3]);
            SpriteRenderer.drawFrame(graphics, cornerFrame, 0, x + width - hitRect[2], y);
            graphics.setClip(clipX, clipY, clipW, clipH);
         }
      }
   }

   public static void drawThickBorder(Graphics graphics, int x, int y, int width, int height) {
      SpriteRenderer.setColor(graphics, 77);
      graphics.fillRect(x, y, width, 6);
      graphics.fillRect(x, y + height - 6, width, 6);
      graphics.fillRect(x, y + 6, 6, height - 12);
      graphics.fillRect(x + width - 6, y + 6, 6, height - 12);
   }

   public static void drawNineSlice(Graphics graphics, int x, int y, int width, int height, int spriteId, int fillColor) {
      int clipX = graphics.getClipX();
      int clipY = graphics.getClipY();
      int clipW = graphics.getClipWidth();
      int clipH = graphics.getClipHeight();
      int capW = SpriteRenderer.frameWidth(spriteId, 0);
      int capH = SpriteRenderer.frameHeight(spriteId, 0);
      int innerX = x + capW;
      int rightX = x + width - capW;
      int innerW = width - (capW << 1);
      int innerY = y + capH;
      int bottomY = y + height - capH;
      int innerH = height - (capH << 1);
      SpriteRenderer.drawFrame(graphics, spriteId, 0, x, y);
      SpriteRenderer.drawFrame(graphics, spriteId, 2, rightX, y);
      SpriteRenderer.drawFrame(graphics, spriteId, 6, x, bottomY);
      SpriteRenderer.drawFrame(graphics, spriteId, 4, rightX, bottomY);
      graphics.setClip(clipX, innerY, clipW, innerH);
      SpriteRenderer.drawFrame(graphics, spriteId, 7, x, innerY);
      SpriteRenderer.drawFrame(graphics, spriteId, 3, rightX, innerY);
      graphics.setClip(innerX, clipY, innerW, clipH);
      SpriteRenderer.drawFrame(graphics, spriteId, 1, innerX, y);
      SpriteRenderer.drawFrame(graphics, spriteId, 5, innerX, bottomY);
      graphics.setClip(clipX, clipY, clipW, clipH);
      if (fillColor != -1) {
         SpriteRenderer.setColor(graphics, fillColor);
         graphics.fillRect(innerX, innerY, innerW, innerH);
      }
   }

   public final void drawWindowBox(Graphics graphics, int x, int y, int width, int height) {
      drawNineSlice(graphics, x, y, width, height, 576, 1);
   }

   public final void drawSunkenBox(Graphics graphics, int x, int y, int width, int height) {
      drawNineSlice(graphics, x, y, width, height, 580, 81);
   }

   public final void drawButton(Graphics graphics, int x, int y, int width, int height, boolean pressed, int textId) {
      this.drawButtonLabel(graphics, x, y, width, height, pressed, this.canvas.getString(textId));
   }

   public final void drawButtonLabel(Graphics graphics, int x, int y, int width, int height, boolean pressed, SdkString label) {
      byte colorIndex;
      if (pressed) {
         colorIndex = 0;
         drawNineSlice(graphics, x - 1, y - 1, width + 2, height + 2, 578, 94);
      } else {
         colorIndex = 1;
         drawNineSlice(graphics, x, y, width, height, 579, 90);
      }

      this.canvas.drawString(label, colorIndex, x + (width >> 1), y + (height >> 1) + 2, 3);
   }

   public final void drawListBox(Graphics graphics, int x, int y, int width, int height) {
      drawNineSlice(graphics, x, y, width, height, 577, 76);
   }

   public static void drawInsetRect(Graphics graphics, int x, int y, int width, int height, int color) {
      SpriteRenderer.setColor(graphics, color);
      graphics.drawRect(x, y, width - 1, height - 1);
      SpriteRenderer.setColor(graphics, 73);
      graphics.fillRect(x + 1, y + 1, width - 2, 1);
      graphics.fillRect(x + 1, y + 2, 1, height - 3);
      SpriteRenderer.setColor(graphics, 74);
      graphics.fillRect(x + 2, y + 2, width - 3, height - 3);
   }

   public final void drawRaisedBox(Graphics graphics, int x, int y, int width, int height) {
      drawInsetRect(graphics, x, y, width, height, 77);
   }

   public final void drawPlainBox(Graphics graphics, int x, int y, int width, int height) {
      drawInsetRect(graphics, x, y, width, height, 0);
   }

   public final void drawTextBox(Graphics graphics, int[] cursor) {
      GameCanvas canvas = this.canvas;
      int lineHeight = this.canvas.lineHeight(1);
      int lineCount = Math.max(5, Math.min(canvas.wrappedLineCount(), this.visibleLineCount));
      int height = lineHeight * lineCount + 16;
      if (graphics == null) {
         cursor[3] = height;
      } else {
         int x = cursor[0];
         int width = cursor[2] - scrollbarWidth();
         int y = cursor[1];
         int iconX = x + 8;
         int iconY = y + 8;
         int rightX = x + width;
         this.drawWindowBox(graphics, x, y, width, height);
         canvas.drawWrappedTextClipped(1, iconX, iconY, 20, this.menuScroll, this.visibleLineCount);
         this.drawScrollbar(graphics, rightX, y, height, this.menuScroll, this.visibleLineCount, canvas.wrappedLineCount(), true);
      }
   }

   public final int drawIconBar(Graphics graphics, int x, int y, int width, int iconId) {
      GameCanvas canvas = this.canvas;
      int innerH = width - 3 - 3;
      int height = 6 + innerH + 3;
      this.drawListBox(graphics, x, -3, y, height);
      int innerW = y - 6;
      int innerX = x + (y - innerW >> 1);
      this.drawSunkenBox(graphics, innerX, 3, innerW, innerH);
      canvas.drawStringById(iconId, 0, innerX + (innerW >> 1), 3 + (innerH >> 1) + 2, 3);
      return height - 3;
   }

   public final int drawIconRow(Graphics graphics, int x, int y, int iconId) {
      return this.drawIconBar(graphics, x, y, 25, iconId);
   }

   public final int drawIconPanel(Graphics graphics, int x, int y, int iconId, int subIconId) {
      GameCanvas canvas = this.canvas;
      this.drawListBox(graphics, x, -3, y, 47);
      int innerW = y - 6;
      int innerX = x + (y - innerW >> 1);
      this.drawSunkenBox(graphics, innerX, 3, innerW, 19);
      canvas.drawStringById(iconId, 0, innerX + (innerW >> 1), 14, 3);
      this.drawWindowBox(graphics, innerX, 25, innerW, 16);
      if (subIconId != -1) {
         canvas.drawStringById(subIconId, 1, innerX + (innerW >> 1), 34, 3);
      }

      return 44;
   }

   public final void drawCenteredLabel(Graphics graphics, int x, int y) {
      this.drawIcon(graphics, x, y, 0, false, 0);
   }

   public final void drawIcon(Graphics graphics, int x, int y, int iconId, boolean highlighted, int recolor) {
      GameCanvas canvas = this.canvas;
      Neighborhood neighborhood = this.canvas.neighborhood;
      int frame = this.canvas.neighborhood.simAttribute(iconId, 0) == 0 ? (highlighted ? 595 : 594) : (highlighted ? 593 : 592);
      int frameIndex = neighborhood.simAttribute(iconId, 2);
      SpriteRenderer.setRecolorIndex(recolor);
      SpriteRenderer.drawFrame(graphics, frame, frameIndex, x, y + 0);
      SpriteRenderer.setRecolorIndex(0);
   }

   public static void drawFrameBorder(Graphics graphics, int x, int y, int width, int height, int thickness) {
      int innerH = height - thickness - thickness;
      graphics.fillRect(x, y, width, thickness);
      graphics.fillRect(x, y + height - thickness, width, thickness);
      graphics.fillRect(x, y + thickness, thickness, innerH);
      graphics.fillRect(x + width - thickness, y + thickness, thickness, innerH);
   }

   public final SdkString textFor(int textId) {
      GameCanvas canvas = this.canvas;
      if (textId >= -12) {
         return canvas.getString(textId);
      } else {
         int mappedId;
         return (mappedId = this.valueSlotIndex(textId)) == 24 ? null : canvas.getString(mappedId);
      }
   }

   private int valueSlotIndex(int textId) {
      return this.valueSlots[-13 - textId];
   }

   public final short[] textBlock(int textId) {
      return this.canvas.tileGroup(textId);
   }

   public final void beginList(int count, int lineHeight, int selected) {
      this.itemCount = count;
      this.lineHeight = lineHeight;
      this.scrollOffset = 0;
      this.visibleRowCount = -1;
      this.selectedIndex = selected;
   }

   public final boolean scrollList(int keyState, int prevKeyState) {
      if (this.visibleRowCount != -1) {
         int previous = this.selectedIndex;
         int target;
         if (this.selectedIndex == -1) {
            target = this.scrollOffset;
         } else {
            target = this.selectedIndex;
         }

         if (this.matchesInput(keyState, prevKeyState, 16640, 0)) {
            target++;
         } else if (this.matchesInput(keyState, prevKeyState, 8196, 0)) {
            target--;
         }

         if (this.selectedIndex == -1) {
            this.scrollOffset = Math.max(0, Math.min(target, this.itemCount - this.visibleRowCount));
         } else {
            this.selectedIndex = Math.max(0, Math.min(target, this.itemCount - 1));
         }

         return previous != this.selectedIndex;
      } else {
         return false;
      }
   }

   public final void drawScrollList(Graphics graphics, int x, int y, int width, int height, int listKind) {
      GameCanvas canvas = this.canvas;
      int innerW = width - scrollbarWidth();
      int clipX = x + 2;
      int clipY = y + 2;
      int clipW = innerW - 2 - 1;
      int clipH = height - 2 - 1;
      this.drawWindowBox(graphics, x, y, innerW, height);
      int rowsVisible = clipH / this.lineHeight;
      int count = this.itemCount;
      this.visibleRowCount = rowsVisible;
      if (this.selectedIndex != -1) {
         int selected = this.selectedIndex;
         int newOffset = this.scrollOffset;
         if (selected < newOffset) {
            newOffset = selected;
         } else if (selected >= newOffset + rowsVisible) {
            newOffset = Math.max(0, selected - rowsVisible + 1);
         }

         this.scrollOffset = newOffset;
      }

      int scrollbarX = x + innerW;
      this.drawScrollbar(graphics, scrollbarX, y, height, this.scrollOffset, rowsVisible, count, true);
      int oldClipX = graphics.getClipX();
      int oldClipY = graphics.getClipY();
      int oldClipW = graphics.getClipWidth();
      int oldClipH = graphics.getClipHeight();
      graphics.setClip(clipX, clipY, clipW, clipH);
      TitleScreen titleScreen = canvas.titleScreen();
      GameScreen gameScreen = canvas.gameScreen();
      switch (listKind) {
         case 0:
            titleScreen.drawPersonaRows(graphics, clipX, clipY, clipW, clipH);
            break;
         case 1:
            titleScreen.drawTraitRows(graphics, clipX, clipY, clipW, clipH);
            break;
         case 2:
         case 4:
         case 8:
            this.drawItemList(graphics, clipX, clipY, clipW, clipH, listKind);
            break;
         case 3:
            gameScreen.drawCareerList(graphics, clipX, clipY, clipW, clipH);
            break;
         case 5:
         case 6:
         case 9:
            gameScreen.drawObjectRows(graphics, clipX, clipY, clipW, clipH, listKind);
            break;
         case 7:
            gameScreen.drawRosterList(graphics, clipX, clipY, clipW, clipH);
            break;
         case 10:
            gameScreen.drawObjectListRow(graphics, clipX, clipY, clipW, clipH);
      }

      graphics.setClip(oldClipX, oldClipY, oldClipW, oldClipH);
   }

   public static void drawHLine(Graphics graphics, int x, int y, int width) {
      SpriteRenderer.setColor(graphics, 75);
      graphics.fillRect(x, y, width, 1);
   }

   public final void drawClippedRow(Graphics graphics, int x, int y, int width, int height, int clipBottom, int color, int rowIndex) {
      if (rowIndex == this.scrollOffset) {
         fillRowFirst(graphics, x, y, width, height, color);
      } else if (y + height >= clipBottom) {
         fillRowLast(graphics, x, y, width, clipBottom - y, color);
      } else {
         fillRowTop(graphics, x, y, width, height, color);
      }
   }

   private static void fillRowTop(Graphics graphics, int x, int y, int width, int height, int color) {
      SpriteRenderer.setColor(graphics, color);
      graphics.fillRect(x, y + 1, width, height - 1);
   }

   private static void fillRowFirst(Graphics graphics, int x, int y, int width, int height, int color) {
      SpriteRenderer.setColor(graphics, color);
      graphics.fillRect(x + 1, y, width - 2, 1);
      graphics.fillRect(x, y + 1, width, height - 1);
   }

   private static void fillRowLast(Graphics graphics, int x, int y, int width, int height, int color) {
      SpriteRenderer.setColor(graphics, color);
      graphics.fillRect(x, y + 1, width, height - 1 - 1);
      graphics.fillRect(x + 1, y + height - 1, width - 2, 1);
   }

   public final void drawHeader(Graphics graphics, int y) {
      GameCanvas canvas = this.canvas;
      int levelCount = this.canvas.neighborhood.achievementCount();
      SdkString text = canvas.beginString();
      canvas.appendStringId(551);
      canvas.appendInt(canvas.visitedFlagCount());
      canvas.appendStringId(27);
      canvas.appendInt(levelCount);
      canvas.setString(-9, text);
      this.setValue(1, -9);
      text = canvas.beginString();
      canvas.appendStringId(550);
      canvas.appendInt(canvas.flagCount());
      canvas.appendStringId(27);
      canvas.appendInt(levelCount);
      canvas.setString(-8, text);
      this.setValue(2, -8);
      this.drawPanel(graphics, y);
   }

   public final void resetList() {
      this.beginList(this.canvas.neighborhood.achievementCount(), 27, -1);
   }

   private int itemIndexAt(int offset) {
      GameCanvas canvas = this.canvas;
      int simCount = this.canvas.neighborhood.achievementCount();
      int firstSectionCount = canvas.flagCount();
      int secondSectionBase = canvas.visitedFlagCount();
      int firstSectionIndex = 0;
      int secondSectionIndex = 0;

      for (int index = 0; index < simCount; index++) {
         if (!canvas.hasFlag(index) && offset >= firstSectionCount) {
            return index;
         }

         if (canvas.hasFlag(index)) {
            if (canvas.hasVisitedFlag(index)) {
               if (firstSectionIndex == offset) {
                  return index;
               }

               firstSectionIndex++;
            } else {
               if (secondSectionIndex == offset - secondSectionBase && offset >= secondSectionBase) {
                  return index;
               }

               secondSectionIndex++;
            }
         }
      }

      return -1;
   }

   private void drawItemList(Graphics graphics, int x, int y, int width, int height, int listKind) {
      GameCanvas canvas = this.canvas;
      Neighborhood neighborhood = this.canvas.neighborhood;
      int iconX = x + 3;
      int iconH = SpriteRenderer.frameHeight(565, 0);
      int iconY = 13 - (iconH >> 1);
      int dividerX;
      int textX = (dividerX = x + 3 + iconH + 3) + 3;
      int rowW = x + width - textX - 3;
      SpriteRenderer.setColor(graphics, 75);
      graphics.fillRect(dividerX, y, 1, height);
      int rowY = y;
      int rowIndex = 0;
      int itemIndex = this.scrollOffset;

      while (rowY < y + height && itemIndex < this.itemCount) {
         int iconId;
         short frameSprite;
         byte frameIndex;
         label39: {
            byte frameIndexTemp;
            label38: {
               frameIndex = 0;
               if (listKind == 2) {
                  int entityIndex = this.itemIndexAt(itemIndex);
                  if (!canvas.hasFlag(entityIndex)) {
                     iconId = 549;
                     frameSprite = 565;
                     frameIndexTemp = 2;
                     break label38;
                  }

                  iconId = neighborhood.achievementText(entityIndex);
                  if (canvas.hasVisitedFlag(entityIndex)) {
                     frameSprite = 565;
                     frameIndexTemp = 1;
                     break label38;
                  }

                  frameSprite = 565;
               } else {
                  if (listKind == 8) {
                     int levelIndex = neighborhood.achievementSlotValue(itemIndex);
                     iconId = neighborhood.achievementText(levelIndex);
                     frameSprite = 589;
                     if (levelIndex == -1) {
                        frameSprite = 196;
                     }
                     break label39;
                  }

                  int simIndex = neighborhood.selectedResidentAnimFrame(itemIndex);
                  iconId = neighborhood.achievementText(simIndex);
                  frameSprite = 565;
                  if (neighborhood.hasUsedTask(itemIndex)) {
                     frameIndexTemp = 1;
                     break label38;
                  }
               }

               frameIndexTemp = 0;
            }

            frameIndex = frameIndexTemp;
         }

         canvas.drawSlotText(rowIndex, iconId, 1, rowW, textX, rowY + 13, 6);
         SpriteRenderer.drawFrame(graphics, frameSprite, frameIndex, iconX, rowY + iconY);
         rowIndex++;
         rowY += 27;
         itemIndex++;
         drawHLine(graphics, x, rowY, width);
      }
   }

   public final int drawMenuList(Graphics graphics, int x, int y, int width, int listKind) {
      int height = 8 + 6 * this.textLineHeight;
      if (graphics != null) {
         GameCanvas canvas = this.canvas;
         Neighborhood neighborhood = this.canvas.neighborhood;
         int category = 0;
         int itemCount = 5;
         if (listKind == 36) {
            category = ((GameScreen)this).currentSimId();
            itemCount = neighborhood.simKnownCount(category);
         }

         this.drawRaisedBox(graphics, x, y, width, height);
         int iconX = x + 4;
         int iconY = y + 4;
         int labelX = iconX + 12;
         int rowY = iconY + this.textLineHeight + 2;
         int rowH = this.textLineHeight - 2;
         int halfW = width >> 1;
         short headerSprite = 644;
         if (listKind == 31) {
            headerSprite = 745;
         }

         canvas.drawStringById(headerSprite, 1, iconX, iconY, 20);
         canvas.beginString();
         Object text = null;
         int textY = rowY;

         for (int index = 0; index < itemCount; index++) {
            text = canvas.beginString();
            canvas.appendInt(index + 1);
            canvas.appendStringId(29);
            canvas.drawString((SdkString)text, 1, iconX, textY, 20);
            int itemId = neighborhood.simKnownId(category, index);
            if (listKind == 36 && !neighborhood.simKnowsWell(category, itemId)) {
               itemId = -1;
            }

            if (itemId != -1) {
               short iconId = neighborhood.itemNameText(itemId);
               canvas.drawStringById(iconId, 1, labelX, textY, 20);
            } else {
               SpriteRenderer.setColor(graphics, 0);
               graphics.fillRect(labelX, textY + rowH, width - halfW, 1);
            }

            textY += this.textLineHeight;
         }

         if (listKind == 36) {
            ((GameScreen)this).drawScrollBar(graphics, x, y, width, height);
         } else if (listKind == 33) {
            ((GameScreen)this).drawScrollHint(graphics, x, y, width, height);
         }
      }

      return height;
   }

   public final void showInfoPanel(int textId, int value) {
      this.setValue(0, value);
      this.canvas.setWrappedString(textId, 1, this.dialogWidth);
   }

   public final void drawPageOne(Graphics graphics) {
      this.drawPanel(graphics, 1);
   }

   public final void showPanelTwo(int textId, int value) {
      this.setValue(0, value);
      this.canvas.setWrappedString(textId, 1, this.listY);
      this.menuScroll = 0;
   }

   public final void drawPageTwo(Graphics graphics) {
      this.drawPanel(graphics, 2);
   }

   public final void scrollMenu(int keyState) {
      if (this.matchesInput(keyState, 0, 8196, 0)) {
         this.menuScroll = Math.max(0, this.menuScroll - 1);
      }

      if (this.matchesInput(keyState, 0, 16640, 0) && this.canvas.wrappedLineCount() > this.menuScroll + this.visibleLineCount) {
         this.menuScroll++;
      }
   }

   public final void drawCaption(Graphics graphics, short[] text, int align) {
      this.drawTwoColumnList(graphics, text, align, null);
   }

   public final void drawTwoColumnList(Graphics graphics, short[] data, int flags, SdkString[] labels) {
      GameCanvas canvas = this.canvas;
      Lot lot = this.canvas.lot;
      short rowCount = data[0];
      short firstIndex = data[3];
      short selectedIndex = data[2];
      short maxRows;
      int rowsShown = (maxRows = data[4]) != -1 ? Math.min((int)maxRows, (int)rowCount) : rowCount;
      int screenW = canvas.getWidth();
      int screenH = canvas.getHeight();
      int border = (flags & 1) != 0 ? 4 : 0;
      int panelW;
      boolean centered;
      if (centered = (flags & 6) != 0) {
         panelW = screenW >> 1;
      } else if ((flags & 8) != 0) {
         panelW = screenW >> 1;
      } else {
         panelW = screenW * 4 / 5;
      }

      int headerH;
      int panelH = (headerH = (flags & 16) == 0 ? 14 : 0) + 7 + 15 * rowsShown + 1 + (border << 1);
      int panelX;
      if ((flags & 2) != 0) {
         panelX = lot.boundsX + 3;
      } else if ((flags & 4) != 0) {
         panelX = lot.boundsX + canvas.getWidth() - panelW - 3;
      } else if ((flags & 8) != 0) {
         panelX = 3;
      } else {
         panelX = screenW - panelW >> 1;
      }

      int panelY;
      if (centered) {
         panelY = (lot.boundsY + lot.boundsHeight - panelH >> 1) - ((flags & 2) != 0 ? 15 : 0);
      } else {
         panelY = screenH - 15 - panelH >> 1;
      }

      if (border != 0) {
         boolean unusedFlag = false;
         byte shadeColor = 61;
         if ((flags & 32) != 0) {
            shadeColor = 76;
         }

         SpriteRenderer.setColor(graphics, 77);
         graphics.drawRect(panelX, panelY, panelW, panelH);
         SpriteRenderer.setColor(graphics, shadeColor);
         drawFrameBorder(graphics, panelX + 1, panelY + 1, panelW - 1, panelH - 1, border - 1);
         panelX += border;
         panelY += border;
         panelW -= border << 1;
         panelH -= border << 1;
      }

      boolean scrollable = rowsShown != rowCount;
      int textX = panelX + 3;
      int rowW = panelW - 6 + (scrollable ? -9 : 1);
      int centerX = textX + (rowW >> 1);
      SpriteRenderer.setColor(graphics, 76);
      graphics.fillRect(panelX, panelY, panelW, panelH);
      SpriteRenderer.setColor(graphics, 77);
      graphics.drawRect(panelX, panelY, panelW, panelH);
      if ((flags & 16) == 0) {
         graphics.fillRect(panelX, panelY + headerH, panelW, 1);
         canvas.drawMarqueeId(0, graphics, data[1], 0, panelX + 3, panelY + 0, panelW - 6, 17);
      }

      if (scrollable) {
         int scrollbarX = panelX + panelW - scrollbarWidth();
         int scrollbarY = panelY + headerH + 2;
         int scrollbarH = panelH - headerH - 4;
         this.drawScrollbar(graphics, scrollbarX, scrollbarY, scrollbarH, firstIndex, rowsShown, rowCount, false);
      }

      int rowY = panelY + headerH + 7;

      for (int row = 0; row < rowsShown; row++) {
         int index = firstIndex + row;
         byte colorIndex = 1;
         short iconId = -1;
         if (labels == null) {
            iconId = data[5 + index];
         }

         if (selectedIndex == index && iconId != 274 && iconId != 275) {
            colorIndex = 0;
            if ((flags & 32) != 0) {
               SpriteRenderer.setColor(graphics, 1);
               graphics.fillRect(textX, rowY, rowW, 15);
            } else if (centered) {
               SpriteRenderer.setColor(graphics, 77);
               graphics.fillRect(textX, rowY, rowW, 15);
            } else {
               drawSeparator(graphics, textX, rowY, rowW, 15);
            }
         }

         int secondIcon;
         if ((secondIcon = canvas.optionStringId(iconId)) != -1) {
            int iconW = canvas.stringWidthById(iconId, colorIndex) + canvas.spaceWidth(colorIndex);
            int secondW = canvas.stringWidthById(secondIcon, colorIndex);
            int secondX;
            int labelX = (secondX = centerX - (iconW + secondW >> 1)) + iconW;
            canvas.drawStringById(iconId, colorIndex, secondX, rowY, 20);
            canvas.drawStringById(secondIcon, colorIndex, labelX, rowY, 20);
         } else if (iconId != -1) {
            if (selectedIndex == index) {
               canvas.drawMarqueeId(1, graphics, iconId, colorIndex, textX, rowY, rowW, 17);
            } else {
               canvas.drawTextById(graphics, iconId, colorIndex, textX, rowY, rowW, 17);
            }
         } else {
            SdkString text = labels[index];
            if (selectedIndex == index) {
               canvas.drawMarqueeText(1, graphics, text, colorIndex, textX, rowY, rowW, 17);
            } else {
               canvas.drawTextClipped(graphics, text, colorIndex, textX, rowY, rowW, 17);
            }
         }

         rowY += 15;
      }
   }

   public static void drawSeparator(Graphics graphics, int x, int y, int width, int height) {
      SpriteRenderer.setColor(graphics, 62);
      graphics.fillRect(x, y, width, 1);
      SpriteRenderer.setColor(graphics, 63);
      graphics.fillRect(x, y + 1, width, height - 3);
      SpriteRenderer.setColor(graphics, 64);
      graphics.fillRect(x, y + height - 2, width, 1);
   }

   public final void drawScrollbar(Graphics graphics, int x, int y, int height, int offset, int visibleCount, int totalCount, boolean drawTrack) {
      x += 3;
      int capH = SpriteRenderer.frameHeight(599, 0);
      int trackY = y + capH + 1 + 2;
      int trackH = height - (capH + 1 + 2 << 1);
      int topCapFrame;
      int bottomCapFrame;
      byte thumbFrame;
      int thumbY;
      int thumbH;
      if (visibleCount >= totalCount) {
         topCapFrame = 0;
         bottomCapFrame = 0;
         thumbFrame = 0;
         thumbY = trackY;
         thumbH = trackH;
      } else {
         topCapFrame = offset > 0 ? 1 : 0;
         bottomCapFrame = offset + visibleCount < totalCount ? 1 : 0;
         thumbFrame = 2;
         thumbH = Math.max(18, trackH * visibleCount / totalCount);
         thumbY = trackY + (trackH - thumbH) * offset / (totalCount - visibleCount);
      }

      int topY = y + 2;
      int bottomY = y + height - 2 - capH;
      SpriteRenderer.drawFrame(graphics, 599, topCapFrame, x, topY);
      SpriteRenderer.drawFrame(graphics, 598, bottomCapFrame, x, bottomY);
      if (thumbFrame == 2) {
         drawScrollThumb(graphics, x, trackY, trackH, 1);
      }

      drawScrollThumb(graphics, x, thumbY, thumbH, thumbFrame);
      if (drawTrack) {
         SpriteRenderer.setColor(graphics, 77);
         int lineX = x - 2;
         int lineW = SpriteRenderer.frameWidth(602, 0) + 4;
         graphics.fillRect(lineX, y, lineW, 1);
         graphics.fillRect(lineX, y + height - 1, lineW, 1);
         graphics.fillRect(lineX + lineW, y, 1, height);
      }
   }

   private static void drawScrollThumb(Graphics graphics, int x, int y, int height, int frame) {
      int capH = SpriteRenderer.frameHeight(601, frame);
      int bottomCapH = SpriteRenderer.frameHeight(600, frame);
      int clipX = graphics.getClipX();
      int clipY = graphics.getClipY();
      int clipW = graphics.getClipWidth();
      int clipH = graphics.getClipHeight();
      graphics.setClip(clipX, y, clipW, height);
      SpriteRenderer.drawFrame(graphics, 601, frame, x, y);
      SpriteRenderer.drawFrame(graphics, 600, frame, x, y + height - bottomCapH);
      y += capH;
      height -= capH + bottomCapH;
      graphics.setClip(clipX, y, clipW, height);
      SpriteRenderer.drawFrame(graphics, 602, frame, x, y);
      graphics.setClip(clipX, clipY, clipW, clipH);
   }

   public static int scrollbarWidth() {
      return SpriteRenderer.frameWidth(602, 0) + 6;
   }

   public static int approach(int current, int target, int numerator, int shift) {
      if (current != target) {
         int delta;
         int step;
         if ((step = (delta = target - current) * numerator >> shift) == 0) {
            step = FixedMath.sign(delta);
         } else if (delta > 0 && current + step > target || delta < 0 && current + step < target) {
            step = delta;
         }

         current += step;
      }

      return current;
   }
}
