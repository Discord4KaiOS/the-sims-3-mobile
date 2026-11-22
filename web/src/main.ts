/**
 * Entry point: boots the MIDlet exactly like {@code com.ea.sims3.Program}.
 *
 * Every PNG is fetched and decoded by the browser before the MIDlet starts, so
 * the game's synchronous image loading finds ready made bitmaps.
 */
import { preloadImages, runtimeEvents } from './runtime';
import { Program } from './game/Program';

let stopped = false;

runtimeEvents.onExit = (): void => {
  stopped = true;
  // KaiOS closes the packaged app with window.close(); a normal browser only
  // honours it for windows a script opened, so this is a no-op there.
  window.close();
};

window.addEventListener('error', (event) => {
  console.error(event.error ?? event.message);
});

preloadImages()
  .catch((error: unknown) => {
    console.error(error);
  })
  .then(() => {
    if (!stopped) {
      Program.main([]);
    }
  });
