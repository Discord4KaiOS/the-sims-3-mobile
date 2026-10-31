package com.ea.sims3;

import java.io.ByteArrayInputStream;
import javax.microedition.lcdui.Display;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

public final class SdkSound implements PlayerListener {
   private static int targetFps = com.ea.sims3.SdkConfig.targetFps();
   public boolean isWavFormat = false;
   public int currentSound = -1;
   public int loopCount = 1;
   public int currentLoop = 0;
   public boolean enabled = true;
   public Player player;
   public ResourceLoader resources = null;
   public Display display;
   public static boolean muted = false;
   public int pendingSound = -2;

   public SdkSound() {
      this.isWavFormat = true;
   }

   public final void setLoopCount(int count) {
      if (SdkFeatures.isLoggingEnabled() && count < 1 && count != -1) {
         SdkText.log("SDKSound::setCurrentLoop::count: " + count + " will result in undefined behavior", 4);
      }

      this.loopCount = count;
   }

   public final void play(int soundId) {
      if (this.currentSound != soundId) {
         byte[] data = this.soundBytes(soundId);
         if (this.player != null) {
            this.player.removePlayerListener(this);
            this.player.close();
         }

         this.player = this.createPlayer(data);

         try {
            this.player.realize();
            this.player.prefetch();
            this.player.addPlayerListener(this);
         } catch (MediaException mediaError) {
         } catch (IllegalStateException stateError) {
         } catch (SecurityException securityError) {
         }
      }
   }

   public final void stopAll() {
      muteAll();
   }

   private void playIfEnabled(int soundId) {
      try {
         if (this.enabled) {
            if (this.isFirstLoop() && this.currentLoop == 1) {
               return;
            }

            this.play(soundId);
            this.startPlayer(soundId);
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   private void startPlayer(int soundId) {
      if (this.player != null) {
         try {
            this.player.setLoopCount(this.loopCount);
            this.player.setMediaTime(0L);
         } catch (Exception ignored) {
         }

         try {
            VolumeControl volume;
            if (this.player != null && (volume = (VolumeControl)this.player.getControl("VolumeControl")) != null) {
               volume.setLevel(50);
            }

            this.player.start();
            this.currentSound = soundId;
            return;
         } catch (Exception ignored2) {
         }
      }
   }

   public static void muteAll() {
      muted = true;
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
         } catch (Exception exception) {
            exception.printStackTrace();
            return;
         }

         this.currentSound = -1;
      }
   }

   public final void vibrate(int duration) {
      duration = duration * com.ea.sims3.SdkConfig.vibrateScaleDen() / com.ea.sims3.SdkConfig.vibrateScaleNum();
      this.display = Display.getDisplay(GameCanvas.instance().app);
      this.display.vibrate(duration);
   }

   public final void setEnabled(boolean enable) {
      this.enabled = enable;
      if (!enable) {
         muteAll();
      }
   }

   private byte[] soundBytes(int soundId) {
      return this.resources.loadSound(soundId);
   }

   private Player createPlayer(byte[] data) {
      byte format = data[0];
      String contentType = null;
      switch (format) {
         case 35:
            contentType = "audio/amr";
            break;
         case 77:
            contentType = "audio/midi";
            break;
         case 82:
            if (this.isWavFormat) {
               contentType = "audio/x-wav";
            } else {
               contentType = "audio/wav";
            }
      }

      if (contentType == null) {
         return null;
      } else {
         Player player = null;

         try {
            ByteArrayInputStream stream;
            player = Manager.createPlayer(stream = new ByteArrayInputStream(data), contentType);
            stream.close();
         } catch (Exception exception) {
            exception.printStackTrace();
         }

         return player;
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
      if (muted) {
         this.stopCurrent();
      }

      muted = false;
   }

   public final void playerUpdate(Player player, String event, Object data) {
      if (event == "endOfMedia" && this.loopCount != -1) {
         this.currentSound = -1;
      }
   }
}
