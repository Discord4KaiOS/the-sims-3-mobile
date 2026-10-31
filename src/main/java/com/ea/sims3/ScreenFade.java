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
   private int x;
   private int y;
   private int width = 1;
   private int height = 1;
   private World world;
   private Image2D image;
   private CompositingMode compositingMode;
   private final byte[] rgba = new byte[4];

   public ScreenFade() {
      Appearance var1 = new Appearance();
      this.compositingMode = new CompositingMode();
      this.compositingMode.setDepthTestEnable(false);
      this.compositingMode.setDepthWriteEnable(false);
      var1.setCompositingMode(this.compositingMode);
      this.image = new Image2D(100, 1, 1);
      Sprite3D var2;
      (var2 = new Sprite3D(true, this.image, var1)).setScale(100.0F, 100.0F, 1.0F);
      this.world = new World();
      Camera var3 = new Camera();
      this.world.addChild(var3);
      this.world.setActiveCamera(var3);
      Background var4;
      (var4 = new Background()).setColorClearEnable(false);
      var4.setDepthClearEnable(false);
      this.world.setBackground(var4);
      this.world.addChild(var2);
      var3.setParallel(90.0F, 1.0F, -1.0F, 1.0F);
   }

   public final void setColor(int var1) {
      this.rgba[0] = (byte)((var1 & 0xFF0000) >> 16);
      this.rgba[1] = (byte)((var1 & 0xFF00) >> 8);
      this.rgba[2] = (byte)(var1 & 0xFF);
      this.rgba[3] = (byte)((var1 & 0xFF000000) >> 24);
      this.image.set(0, 0, 1, 1, this.rgba);
   }

   public final void setBlending(int var1) {
      this.compositingMode.setBlending(var1);
   }

   public final void setViewport(int var1, int var2, int var3, int var4) {
      this.x = var1;
      this.y = var2;
      this.width = var3;
      this.height = var4;
   }

   public final void render(Graphics var1) {
      Graphics3D var2;
      (var2 = Graphics3D.getInstance()).bindTarget(var1);
      var2.setViewport(this.x, this.y, this.width, this.height);
      var2.render(this.world);
      var2.releaseTarget();
   }
}
