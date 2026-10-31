package com.ea.sims3;

import javax.microedition.lcdui.Graphics;
import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.Background;
import javax.microedition.m3g.Camera;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Graphics3D;
import javax.microedition.m3g.Image2D;
import javax.microedition.m3g.Sprite3D;
import javax.microedition.m3g.World;

public final class ScreenFade {
   private int viewportX;
   private int viewportY;
   private int width = 1;
   private int height = 1;
   private World world;
   private Image2D image;
   private CompositingMode compositingMode;
   private final byte[] rgba = new byte[4];

   public ScreenFade() {
      Appearance appearance = new Appearance();
      this.compositingMode = new CompositingMode();
      this.compositingMode.setDepthTestEnable(false);
      this.compositingMode.setDepthWriteEnable(false);
      appearance.setCompositingMode(this.compositingMode);
      this.image = new Image2D(100, 1, 1);
      Sprite3D sprite;
      (sprite = new Sprite3D(true, this.image, appearance)).setScale(100.0F, 100.0F, 1.0F);
      this.world = new World();
      Camera camera = new Camera();
      this.world.addChild(camera);
      this.world.setActiveCamera(camera);
      Background background;
      (background = new Background()).setColorClearEnable(false);
      background.setDepthClearEnable(false);
      this.world.setBackground(background);
      this.world.addChild(sprite);
      camera.setParallel(90.0F, 1.0F, -1.0F, 1.0F);
   }

   public final void setColor(int rgb) {
      this.rgba[0] = (byte)((rgb & 0xFF0000) >> 16);
      this.rgba[1] = (byte)((rgb & 0xFF00) >> 8);
      this.rgba[2] = (byte)(rgb & 0xFF);
      this.rgba[3] = (byte)((rgb & 0xFF000000) >> 24);
      this.image.set(0, 0, 1, 1, this.rgba);
   }

   public final void setBlending(int blending) {
      this.compositingMode.setBlending(blending);
   }

   public final void setViewport(int originX, int originY, int viewportWidth, int viewportHeight) {
      this.viewportX = originX;
      this.viewportY = originY;
      this.width = viewportWidth;
      this.height = viewportHeight;
   }

   public final void render(Graphics gfx) {
      Graphics3D graphics3d;
      (graphics3d = Graphics3D.getInstance()).bindTarget(gfx);
      graphics3d.setViewport(this.viewportX, this.viewportY, this.width, this.height);
      graphics3d.render(this.world);
      graphics3d.releaseTarget();
   }
}
