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

public final class l {
   private int a;
   private int b;
   private int c = 1;
   private int d = 1;
   private World a2;
   private Image2D a3;
   private CompositingMode a4;
   private final byte[] a5 = new byte[4];

   public l() {
      Appearance var1 = new Appearance();
      this.a4 = new CompositingMode();
      this.a4.setDepthTestEnable(false);
      this.a4.setDepthWriteEnable(false);
      var1.setCompositingMode(this.a4);
      this.a3 = new Image2D(100, 1, 1);
      Sprite3D var2;
      (var2 = new Sprite3D(true, this.a3, var1)).setScale(100.0F, 100.0F, 1.0F);
      this.a2 = new World();
      Camera var3 = new Camera();
      this.a2.addChild(var3);
      this.a2.setActiveCamera(var3);
      Background var4;
      (var4 = new Background()).setColorClearEnable(false);
      var4.setDepthClearEnable(false);
      this.a2.setBackground(var4);
      this.a2.addChild(var2);
      var3.setParallel(90.0F, 1.0F, -1.0F, 1.0F);
   }

   public final void a12(int var1) {
      this.a5[0] = (byte)((var1 & 0xFF0000) >> 16);
      this.a5[1] = (byte)((var1 & 0xFF00) >> 8);
      this.a5[2] = (byte)(var1 & 0xFF);
      this.a5[3] = (byte)((var1 & 0xFF000000) >> 24);
      this.a3.set(0, 0, 1, 1, this.a5);
   }

   public final void b7(int var1) {
      this.a4.setBlending(var1);
   }

   public final void a2(int var1, int var2, int var3, int var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public final void a(Graphics var1) {
      Graphics3D var2;
      (var2 = Graphics3D.getInstance()).bindTarget(var1);
      var2.setViewport(this.a, this.b, this.c, this.d);
      var2.render(this.a2);
      var2.releaseTarget();
   }
}
