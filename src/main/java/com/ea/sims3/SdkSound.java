package com.ea.sims3;

import java.io.ByteArrayInputStream;
import javax.microedition.lcdui.Display;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

public final class SdkSound implements PlayerListener {
   private static int e = com.ea.sims3.SdkConfig.targetFps();
   public boolean isWavFormat = false;
   public int currentSound = -1;
   public int loopCount = 1;
   public int currentLoop = 0;
   public boolean enabled = true;
   public Player player;
   public ResourceLoader resources = null;
   public Display display;
   public static boolean c2 = false;
   public int pendingSound = -2;

   public SdkSound() {
      this.isWavFormat = true;
   }

   public final void setLoopCount(int var1) {
      if (SdkFeatures.isLoggingEnabled() && var1 < 1 && var1 != -1) {
         SdkText.a("SDKSound::setCurrentLoop::count: " + var1 + " will result in undefined behavior", 4);
      }

      this.loopCount = var1;
   }

   public final void play(int var1) {
      if (this.currentSound != var1) {
         byte[] var2 = this.soundBytes(var1);
         if (this.player != null) {
            this.player.removePlayerListener(this);
            this.player.close();
         }

         this.player = this.createPlayer(var2);

         try {
            this.player.realize();
            this.player.prefetch();
            this.player.addPlayerListener(this);
         } catch (MediaException var4) {
         } catch (IllegalStateException var5) {
         } catch (SecurityException var6) {
         }
      }
   }

   public final void stopAll() {
      muteAll();
   }

   private void playIfEnabled(int var1) {
      try {
         if (this.enabled) {
            if (this.isFirstLoop() && this.currentLoop == 1) {
               return;
            }

            this.play(var1);
            this.startPlayer(var1);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   private void startPlayer(int var1) {
      if (this.player != null) {
         try {
            this.player.setLoopCount(this.loopCount);
            this.player.setMediaTime(0L);
         } catch (Exception var3) {
         }

         try {
            VolumeControl var2;
            if (this.player != null && (var2 = (VolumeControl)this.player.getControl("VolumeControl")) != null) {
               var2.setLevel(50);
            }

            this.player.start();
            this.currentSound = var1;
            return;
         } catch (Exception var4) {
         }
      }
   }

   public static void muteAll() {
      c2 = true;
   }

   private void stopCurrent() {
      this.stop();
   }

   public final void stop() {
      if (this.currentSound != -1 && this.player != null) {
         try {
            if (this.player.getState() == 400) {
               this.player.stop();
            }

            this.player.getState();
         } catch (Exception var2) {
            var2.printStackTrace();
            return;
         }

         this.currentSound = -1;
      }
   }

   public final void vibrate(int var1) {
      var1 = var1 * com.ea.sims3.SdkConfig.vibrateScaleDen() / com.ea.sims3.SdkConfig.vibrateScaleNum();
      this.display = Display.getDisplay(GameCanvas.a7().a);
      this.display.vibrate(var1);
   }

   public final void setEnabled(boolean var1) {
      this.enabled = var1;
      if (!var1) {
         muteAll();
      }
   }

   private byte[] soundBytes(int var1) {
      return this.resources.loadSound(var1);
   }

   private Player createPlayer(byte[] var1) {
      byte var2 = var1[0];
      String var3 = null;
      switch (var2) {
         case 35:
            var3 = "audio/amr";
            break;
         case 77:
            var3 = "audio/midi";
            break;
         case 82:
            if (this.isWavFormat) {
               var3 = "audio/x-wav";
            } else {
               var3 = "audio/wav";
            }
      }

      if (var3 == null) {
         return null;
      } else {
         Player var4 = null;

         try {
            ByteArrayInputStream var5;
            var4 = Manager.createPlayer(var5 = new ByteArrayInputStream(var1), var3);
            var5.close();
         } catch (Exception var6) {
            var6.printStackTrace();
         }

         return var4;
      }
   }

   private boolean isFirstLoop() {
      return this.currentSound == 1;
   }

   public final void playQueued() {
      if (this.pendingSound != -2) {
         this.playIfEnabled(this.pendingSound);
         this.pendingSound = -2;
      }
   }

   public final void update() {
      if (c2) {
         this.stopCurrent();
      }

      c2 = false;
   }

   public final void playerUpdate(Player var1, String var2, Object var3) {
      if (var2 == "endOfMedia" && this.loopCount != -1) {
         this.currentSound = -1;
      }
   }
}
