# Reverse-engineered The Sims 3 (mobile)

## Overview

This is a reverse-engineered version of the Java ME (J2ME) MIDlet **The Sims 3**
(`MIDlet-Version: 7.7.65`, `MIDlet-Vendor: Electronic Arts`,
`MicroEdition-Profile: MIDP-2.0`, `MicroEdition-Configuration: CLDC-1.1`).

The original archive contains 26 obfuscated classes and 130 resources (sprite
sheets, bitmap fonts, the SDK text database). The classes were decompiled to
Java and the Java ME APIs they depend on were replaced with desktop
implementations, so the game now builds and runs on a normal JVM:

| Package | What it replaces |
|:-|:-|
| `javax.microedition.lcdui` | `Canvas`, `Display`, `Graphics`, `Image`, `Font`, `Alert` — AWT/Swing backed |
| `javax.microedition.midlet` | `MIDlet` lifecycle + app properties |
| `javax.microedition.media` | music/sound (accepted but silent) |
| `javax.microedition.rms` | `RecordStore`, persisted to disk |
| `javax.microedition.m3g` | the small JSR-184 subset the game actually uses |

`com/ea/sims3/Program.java` is the desktop entry point; it drives the MIDlet
lifecycle the way a handset application manager would.

## Status

Working: language selection, loading screen, title screen, main menu, goal
checklist, options, Create-A-Sim, and the in-game screen, with save data stored
through the RMS shim.

Audio works, all in Java:

* the music and jingles are standard MIDI files, played through the JDK's
  software synthesizer (`javax.sound.midi`) with looping and volume;
* WAV (`audio/x-wav`) plays through `javax.sound.sampled`;
* the game's AMR-NB sounds are decoded by
  [AmrDecoder](./src/main/java/javax/microedition/media/AmrDecoder.java), a
  pure-Java implementation of the 3GPP TS 26.073 decoding chain covering the
  two modes the archive actually uses - **4.75 kbit/s** (`ck`, `cm`, `co`,
  `cq`) and **12.2 kbit/s** (the other ten files): the LSF quantisers, pitch
  decoding, gain quantisers and algebraic codebooks of those two modes, LSP/LPC
  conversion, the MA gain predictor and the synthesis filter with the
  standard's overflow guard. The standard's tables live in
  [AmrTables](./src/main/java/javax/microedition/media/AmrTables.java),
  [AmrLsfTables122](./src/main/java/javax/microedition/media/AmrLsfTables122.java),
  [AmrLsfTables](./src/main/java/javax/microedition/media/AmrLsfTables.java) and
  [AmrGainTables](./src/main/java/javax/microedition/media/AmrGainTables.java).
  Nothing outside the JDK is involved.

The MIDP `Player` contract - state machine, loop counts, `PlayerListener`
events and `VolumeControl` - lives in
[javax/microedition/media](./src/main/java/javax/microedition/media).

Not implemented:

* **The other six AMR-NB modes.** No asset in the archive uses 5.15, 5.9, 6.7,
  7.4, 7.95 or 10.2 kbit/s, so those frames decode to silence (timing is kept).
* **AMR comfort noise (SID) frames.** SID frames decode to silence rather than
  synthesised comfort noise; the game's clips carry no DTX.
* **AMR post filter / phase dispersion.** The decoder reconstructs the speech
  but skips those two enhancement stages of the reference decoder.
* **M3G.** `javax.microedition.m3g` only implements what the game uses: one
  `Sprite3D` with a parallel camera, used for the full screen colour fades
  (`com/ea/sims3/ScreenFade.java`). It is not a general JSR-184 renderer.

## Requirements

* JDK 17 or newer (the Gradle wrapper needs it, and the sources target 17)

## Getting the game resources

The resources are the property of Electronic Arts and are **not** included in
this repository. Copy them from the original archive into `src/main/resources`
once:

```sh
mkdir -p src/main/resources
(cd src/main/resources && jar xf ../../the_sims_3_mobile.jar \
  && rm -rf META-INF com && find . -name '*.class' -delete)
```

Every file must end up directly in `src/main/resources`, because the game opens
them by short names such as `/a`, `/charmap`, `/hdr`, `/t_en` and
`/0font_sims_black.png`.

## Build

```sh
./gradlew build
java -jar build/libs/the-sims-3-mobile-1.0.jar
```

## Controls

| Key | Game action |
|:-|:-|
| Arrow keys | Up / Down / Left / Right |
| Enter or Space | OK / Select |
| 0 – 9 | Number keys |
| `*` and `#` | Star and pound |
| F1 – F4 | Game keys A – D (the soft keys: usually *Select* and *Back*) |
| Backspace | Also game key C (same as F3) |

The emulator window can be scaled with system properties, for example
`java -Dmidp.scale=3 -jar build/libs/the-sims-3-mobile-1.0.jar`.

| Property | Default | Meaning |
|:-|:-|:-|
| `midp.screen.width` | `240` | emulated screen width |
| `midp.screen.height` | `320` | emulated screen height |
| `midp.scale` | `2` | initial window scale factor |
| `midp.home` | `~/.the_sims_3_mobile` | where record stores are written |
| `midlet.Force-Locale` | unset | e.g. `-Dmidlet.Force-Locale=de` |

## Web port

`web/` contains a browser port of the same game. It is a TypeScript rewrite that
keeps the behaviour of the Java code rather than its shape: the game classes are
translated from this repository's de-obfuscated sources and the J2ME APIs are
reimplemented on top of canvas, Web Audio and `localStorage`.

```sh
cd web
npm install
npm run assets      # copy resources, pre-render MIDI/AMR to Ogg (needs ffmpeg + timidity)
npm run dev         # or: npm run build && npm run preview
```

`npm run build` also packs the output into `web/application.zip` (the contents of
`dist/` at the archive root, which is the layout KaiOS installs); the archive is
git ignored and `npm run package` repacks without rebuilding.

| Piece | Implementation |
|:-|:-|
| Graphics | `src/runtime/graphics.ts`, a MIDP `Graphics` over a 2D canvas (clipping, regions, anchors) |
| Images | `src/runtime/png.ts` decodes PNGs with the browser's own decoder; `image.ts` preloads every image at boot so the game can keep its synchronous loading |
| Fonts / text | the game's own bitmap font engine, unchanged; `font.ts` only provides metrics |
| Input | `src/runtime/display.ts` maps arrows, Enter, Space, F1–F4, Backspace, numbers, `*` and `#` to MIDP key codes, and also the KaiOS key names `SoftLeft`/`SoftRight`/`Enter`, which arrive with an empty `code`; the hardware back key is prevented from closing the app |
| Record stores | `src/runtime/rms.ts`, stored as base64 record lists in `localStorage` |
| Audio | `src/runtime/media.ts`; the build renders every MIDI/AMR/WAV asset to mono 22.05 kHz Ogg Vorbis. A loop count of −1 sets `HTMLAudioElement.loop`, matching the Java sequencer's `LOOP_CONTINUOUSLY` without a seam |
| Resources | synchronous `XMLHttpRequest` from `public/game` (`src/runtime/io.ts`) |
| Threads | the loading "threads" become cooperative pumps driven by key presses |

> `public/manifest.webmanifest` is the KaiOS 3.0 manifest: fullscreen, portrait,
> the plumbob green from `icon.png` as background and theme colour, and that same
> `icon.png` (copied into `public/game` by `npm run assets`) as the single app
> icon. Quitting the game calls `window.close()`, which is how KaiOS closes a
> packaged app; a desktop browser ignores it.

### Regenerating the ported game code

`web/src/game` is generated from `src/main/java/com/ea/sims3` by
`tools/web/JavaToTs.java` (javac's `Trees` API, so every reference, overload and
cast resolves against the real Java symbols) plus `tools/web/postprocess.mjs`,
which applies the handful of adjustments that have no direct JavaScript
equivalent:

* the 64 bit progress flags in `GameCanvas` use `BigInt` (JavaScript numbers
  only hold bits up to 2^53 and bitwise operators stop at 32),
* the loading threads become `setTimeout` pumps instead of blocking waits,
* mid/AMR playback is replaced by the pre-rendered Ogg assets,
* Java overloads are renamed (`name_2`, `name_3`) because TypeScript has no
  method overloading, and `StringBuffer.append(char)` is mapped to a character
  append instead of a number append.

```sh
npm run game        # tools/web/generate.sh, needs a JDK
```

### Images

The game asks for its sprite sheets while it loads, so the port fetches and
decodes every PNG once at start up (`preloadImages`) and the game then gets
ready made bitmaps. Decoding is done by the browser (`createImageBitmap`), which
is faster than inflating the data in JavaScript and removes the `fflate`
dependency; `scripts/copy-assets.mjs` writes `public/game/png.json` with the list
of images to preload.

The recolouring path is worth a note: the Java build rewrites the `PLTE` chunk
in memory and decodes the edited PNG again. `ResourceLoader.replacePalette` walks
the whole `fromColors` array and, for each entry that is not the `0x00FF00FF`
"skip" marker, replaces *every* palette entry of that colour — sequentially, so a
later entry can match an earlier replacement. The port decodes each image once
and runs the same substitution over the palette it read from the PNG, then maps
the decoded pixels back through it (by colour *and* alpha, so entries sharing a
colour stay distinct). The result matches the Java build pixel for pixel.

`ScreenFade.ts` is written by hand: the Java build draws the fades through the
M3G shim, which reduces to a single alpha blended rectangle.

### Integer and 64 bit arithmetic

Java's `int` operators wrap at 32 bits and its `long` operators at 64; a
JavaScript number does neither, so the translator spells the Java result out:

| Java | Emitted | Why |
|:-|:-|:-|
| `a / b`, `a % b` (int) | `((a / b) \| 0)` | truncate toward zero, keep the sign wrap |
| `a * b` (int) | `Math.imul(a, b)` | exact 32 bit product; `a * b` loses precision past 2^53 |
| `a + b`, `a - b` (int) | `((a + b) \| 0)` | 32 bit wrap |
| `-a` (int) | `((a * -1) \| 0)` | `-Integer.MIN_VALUE` stays negative in Java |
| `a >>> b` (int) | `((a >>> b) \| 0)` | `>>>` yields an unsigned value, Java's result is signed |
| `a << b`, `a >> b`, `a & b`, `a \| b`, `a ^ b` (int) | unchanged | JavaScript bitwise operators already work on int32 |
| `(byte) x`, `(short) x`, `(char) x` | `(x << 24 >> 24)`, `(x << 16 >> 16)`, `(x & 0xffff)` | narrowing conversions |
| `x += y` and friends on an int | `x = ((x + y) \| 0)` | Java narrows and wraps compound assignments too |
| `x += y` on a byte/short/char | `x = (((x + y) \| 0) << 24 >> 24)` | Java converts back to the variable's type |
| `a << b` (long) | `a * Math.pow(2, b & 63)` | 64 bit shifts do not fit an int32; the distance is masked like Java |
| `a >> b` (long) | `Math.floor(a / Math.pow(2, b & 63))` | an arithmetic shift is a floor division by a power of two |
| `a / b` (long) | `Math.trunc(a / b)` | exact for every dividend a double can hold: the quotient is more than half an ulp from the next integer |

Anything a JavaScript number cannot represent at all is emitted with a
`SIM_INEXACT` marker: `long` bitwise operators, `long <<` with a variable
distance and `long >>>`. `tools/web/generate.sh` fails if a marker survives the
post processing step, so a new 64 bit operation cannot silently lose its high
bits; the flag bitsets are the only places that need the `BigInt` rewrite.

`long` `+ - * %` stay plain numbers and are exact for every value this game
produces (millisecond timestamps, elapsed times and the fixed point
intermediates, all far below 2^53).

### Numeric parity checks

```sh
tools/web/parity/run.sh     # needs a JDK and node, no gradle
```

translates a generated class holding one method per operator/operand-type
combination, runs it through javac and through the emitted TypeScript and
compares 19 086 results, then does the same for the fixed point maths
(191 515 cases covering the whole `sin`/`cos`/`sqrt` range plus `Integer`
edge values) and for the ported `java.util.Random` against the JDK
implementation. Cases whose exact result needs more than 53 bits are reported
as known losses; everything else has to match.

## Web port status

The port boots, reaches the main menu, the Create A Sim flow and the lot itself.
Frames were compared against the desktop build by capturing both at the same
game states: the sound prompt, the title screen and matched menu frames are
pixel identical.

Three classes of difference are handled by restoring Java semantics where
JavaScript would silently continue:

* typed arrays ignore out-of-range writes, so `SdkString` checks its buffer
  bounds and throws `IndexOutOfBoundsException` like the Java `byte[]` would
  (the game catches these and skips the draw, which is why some list rows are
  blank),
* `SdkString` throws for unknown string ids for the same reason,
* compound integer assignment truncates like Java (`divisor /= 10` was dividing
  into a float and looping ~324 times instead of 10, which broke the number
  formatting behind the Traits list rows),
* integer division keeps its `| 0` wrapper parenthesised, because `|` binds
  looser than the operators around it (`listY` came out as `-9` instead of
  `183 - 14`, which collapsed every wrapped text block into one character per
  line and broke the in-game tutorial screen).

Every `int` operation now wraps at 32 bits like Java (see the table above), and
the numeric parity suite covers the translation rules rather than individual
call sites.

Timing differs slightly because the port loads resources synchronously, so key
presses land on the same screens a little sooner than on the desktop build.

### Memory and frame cost

The target is a low end phone, so the runtime avoids anything that grows or
allocates per frame:

* clipping is computed arithmetically and the result is reused instead of
  returning a fresh rectangle for every sprite,
* `drawRegion` clips once (not twice) and skips both the clip and the draw when a
  sprite is off screen or already inside the clip,
* one `Graphics` instance is reused per frame and `fillStyle` is only assigned
  when it changes,
* the frame buffer is *not* cleared between frames, matching the desktop build,
  which paints onto a persistent buffer — that way a repaint that draws nothing
  (the exit sequence) leaves the last frame visible instead of flashing black,
* every canvas `save()` is paired with `restore()` in a `finally`, so a throwing
  draw cannot leak the canvas state stack,
* `ctx.roundRect` (absent on KaiOS) is replaced by a portable path,
* audio players detach their listeners and drop their source on close, and the
  autoplay retry queue is bounded,
* images are preloaded four at a time; the 81 decoded sprites total about 3.8 MB
  and are shared rather than copied per caller.

A 30 second soak (menus, movement, dialog churn) keeps the JavaScript heap flat at
about 6.8 MB.

## Decompilation notes

### Tooling

The classes are decompiled with [Vineflower](https://github.com/Vineflower/vineflower)
1.11.1. CFR 0.152 was tried as well, but it cannot structure a handful of
methods (`p.w()`, `u.h(...)`, `v.a(int,int)`, `w.a(int)`) and silently skips
class `n`.

### Why the archive needs preparation

An obfuscated CLDC class file may contain things Java source cannot express:

* several fields with the same name in one class (they differ by descriptor),
* methods that differ only in their return type.

Vineflower reproduces those faithfully, which cannot compile.
`tools/RemapTool.java` (built on ASM) rewrites the class files first:

* every class is moved into the `com.ea.sims3` package, so the default-package
  classes and `com.ea.sims3.MonkeyApp` can see each other,
* duplicated field names (including names shadowing an inherited field) become
  `a`, `a2`, `a3`, …,
* methods that only differ in return type get the same numeric suffix.

Every rename is listed in [tools/rename-map.txt](./tools/rename-map.txt), so any
name in the decompiled source can be traced back to the original archive.

### Manual repairs

A few spots had to be edited by hand after decompilation. All of them are
annotated with a comment in the source:

| File | Problem |
|:-|:-|
| `GameCanvas.java` (`G`) | the entry counters were inferred as `byte`; the tables have more than 127 entries |
| `SdkString.java` (`a2`) | a comparison against the constant `1` was rendered as `!= true` |
| `SdkText.java` (`a4`) | one local slot is reused as two booleans and later as two ints |
| `SdkText.java` | `InputStream.read/skip` helpers cannot compile without handling `IOException` |
| `Neighborhood.java`, `SdkMoreGames.java`, `Lot.java` | one local slot reused for two different variables |
| `SpriteRenderer.java`, `SdkText.java` | `short`/`byte` arithmetic that needs an explicit cast |
| `GameCanvas.java`, `GameScreen.java`, `Lot.java`, `Sim.java` | calls that pass `null` to an overloaded method; the original descriptors were read from the class files to pick the right overload |
| `TitleScreen.java` (`y`) | the intro flashed white before the EA logo and back to black, and dimmed the white sim/logo frames to black; it now holds black with the EA logo, fades to white once, and stays white (`drawFadeIn`/`drawFadeBands` removed) |

### Porting bugs found while play-testing

Two behaviours of the original bytecode are *not* preserved automatically when
it is expressed as Java source, and both were visible in game:

1. **Overload binding.** The obfuscator produced methods such as
   `i.a(int, c)` and `i.a(int, i)` — `i` extends `c`. A call that passes `null`
   (or an `i` where the original invoked the `c` variant) is re-bound by javac to
   the most derived overload, i.e. to a *different method*. That produced
   `NullPointerException`s inside the game's own update step, which the retail
   code silently swallows, so Sims stopped updating (they could vanish) and the
   game could stop responding. Every such call site now has an explicit cast.
2. **`Graphics.drawRegion` transforms.** The desktop `drawRegion` originally
   mapped the source region with an offset error for the mirrored variants, so
   sprites drawn with `TRANS_MIRROR` (which the game uses for the direction a Sim
   faces) were shifted out of their destination rectangle.

`tools/NullArgs.java` lists every call that passes a null constant together with
the descriptor the original bytecode used, and `tools/CallDump.java` dumps all
calls into the game's classes so the compiled port and the original archive can
be diffed; both need the [ASM](https://asm.ow2.io/) jars on the classpath:

```sh
javac -cp asm.jar:asm-tree.jar:asm-analysis.jar -d classes tools/NullArgs.java
java -cp classes:asm.jar:asm-tree.jar:asm-analysis.jar NullArgs classes/**/*.class
```

### Class map

The class names are no longer the obfuscated letters; each one is named after
the role it plays (see [tools/rename-symbols.txt](./tools/rename-symbols.txt)
for the mapping):

| Class | Role |
|:-|:-|
| `MonkeyApp` | MIDlet entry point (`Display`, lifecycle) |
| `GameCanvas` | game controller: `Canvas`, frame timer, key handling, screen switching, save data |
| `GameScreen` | in-game screen (the lot, Sims, cheats, day/hour advance) |
| `TitleScreen` | loading screen and title screen |
| `BaseScreen` | base class for the screens: panels, frames, soft keys |
| `Neighborhood` | world state: clock, money, lots, residents, needs, interactions, cards, achievements, save file |
| `Lot` | tile/room data of the lot plus the sprite drawing for it |
| `Sim` | a Sim: motives, interaction routing, animation state |
| `Entity` | base class for anything placed in the lot |
| `Animator` | per-entity animation state machine (animation id, frame, timing, flags) |
| `FrameTimer` | `TimerTask` that ticks the game loop every 15 ms |
| `PromoImages`, `SdkMoreGames` | EA "More Games" promo screen and its image provider |
| `SdkText` | EA SDK text engine: strings, glyph tables, text drawing |
| `SdkString` | EA SDK string class with encodings |
| `SdkSound`, `SdkConfig`, `SdkFeatures` | EA SDK sound manager and configuration |
| `ResourceLoader` | resource loading, including the PNG palette recolouring trick (the port recolours decoded pixels instead of re-encoding the PNG) |
| `Resources` | resource name/size tables |
| `SpriteRenderer` | sprite and glyph drawing helpers |
| `SpriteIndex`, `SoundIndex` | sprite and sound index tables (constant interfaces) |
| `Tile` | a tile/frame of a sprite sheet |
| `ScreenFade` | the only M3G user: a full screen colour quad for fades |
| `FixedMath` | 16.16 fixed point maths (sin, cos, sqrt, atan2, interpolation) |

### Member names

The whole codebase is de-obfuscated: every class, field, method, parameter,
local, catch parameter and loop variable now carries a name that describes what
it does (`updateNeeds`, `drawNineSlice`, `findPath`, `pathHeuristic`,
`spawnFound`...). `tools/ObfuscationReport.java` is the checker:

```
javac -d /tmp/ren tools/ObfuscationReport.java
java -cp /tmp/ren ObfuscationReport summary  src/main/java   # what is left per class
java -cp /tmp/ren ObfuscationReport class    src/main/java GameScreen
java -cp /tmp/ren ObfuscationReport shadows  src/main/java   # fields hiding inherited fields
```

`summary` currently reports `TOTAL 0 0 0 0` and `shadows` reports
`hidden fields: 0`.

The names themselves live in [tools/rename-symbols.txt](./tools/rename-symbols.txt)
and are applied by [tools/Rename.java](./tools/Rename.java), which resolves
every occurrence through javac, so a rename can never hit an unrelated
identifier, overrides follow their base method, and constructors survive
`super(...)`. The mapping is cumulative and replaying it from the original
sources reproduces the current tree.

```
# apply/extend the mapping at any time
javac -d /tmp/ren tools/Rename.java
java -cp /tmp/ren Rename tools/rename-symbols.txt src/main/java decl
java -cp /tmp/ren Rename tools/rename-symbols.txt src/main/java vars
```

Two phases are needed because a rename can be chained (`a2` -> `init` ->
`place`) and because a variable entry is keyed by the method name that applies
at that point. [tools/normalize-renames.py](./tools/normalize-renames.py)
rewrites variable keys to the final method names, and
[tools/merge-renames.sh](./tools/merge-renames.sh) merges mapping files
produced in parallel and runs both phases to a fixed point.

Three traps are worth knowing, and all three are checked:

* subclass fields shadow inherited ones, so a rename can silently change which
  field a `this.x` refers to. Position fields are therefore `posX`/`posY` and
  not `x`/`y`, and `ObfuscationReport shadows` must stay at zero.
* an override must keep the name of the method it overrides, otherwise call
  sites that dispatch through the supertype silently stop reaching it.
  `Rename.java` warns (`WARN override mismatch`) when a mapping would break
  that.
* the decompiler reused local slots, so a single local can hold two unrelated
  values; such a local is named after the value it carries in the section of
  the method that matters.

How the result was verified: the sources were recompiled and the bytecode of
all 68 classes compared against the pre-rename build with member names
normalised away. Every class but `Alert` (where the shim was fixed to stop
shadowing `Displayable.title`) is instruction-for-instruction identical, so the
renaming pass changed names only. The game was then played through the title,
save-select, main-menu and scrolling screens with the same harness used before
the rename pass.

## Legal

This is an unofficial reverse engineering effort for research and preservation.
*The Sims* and all related assets are copyright Electronic Arts. The decompiled
classes in `com/ea/sims3` are derived from EA's original binary; the Java ME
shims and the tooling in `tools/` are original work.
