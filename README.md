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

Not implemented:

* **Audio.** The retail archive ships no sound files, and the media shim
  (`Manager`/`Player`) is a state-machine-only stub, so the game is silent.
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

The emulator window can be scaled with system properties, for example
`java -Dmidp.scale=3 -jar build/libs/the-sims-3-mobile-1.0.jar`.

| Property | Default | Meaning |
|:-|:-|:-|
| `midp.screen.width` | `240` | emulated screen width |
| `midp.screen.height` | `320` | emulated screen height |
| `midp.scale` | `2` | initial window scale factor |
| `midp.home` | `~/.the_sims_3_mobile` | where record stores are written |
| `midlet.Force-Locale` | unset | e.g. `-Dmidlet.Force-Locale=de` |

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
| `Neighborhood` | card based screen logic and world data |
| `Lot` | tile/room data of the lot plus the sprite drawing for it |
| `Sim` | a Sim: motives, interaction routing, animation state |
| `Entity` | base class for anything placed in the lot |
| `Animator` | per-entity animation state machine (animation id, frame, timing, flags) |
| `FrameTimer` | `TimerTask` that ticks the game loop every 15 ms |
| `PromoImages`, `SdkMoreGames` | EA "More Games" promo screen and its image provider |
| `SdkText` | EA SDK text engine: strings, glyph tables, text drawing |
| `SdkString` | EA SDK string class with encodings |
| `SdkSound`, `SdkConfig`, `SdkFeatures` | EA SDK sound manager and configuration |
| `ResourceLoader` | resource loading, including the PNG palette recolouring trick |
| `Resources` | resource name/size tables |
| `SpriteRenderer` | sprite and glyph drawing helpers |
| `SpriteIndex`, `SoundIndex` | sprite and sound index tables (constant interfaces) |
| `Tile` | a tile/frame of a sprite sheet |
| `ScreenFade` | the only M3G user: a full screen colour quad for fades |
| `FixedMath` | 16.16 fixed point maths (sin, cos, sqrt, atan2, interpolation) |

### Member names

Classes, plus the members whose meaning could be established with confidence,
were renamed by [tools/Rename.java](./tools/Rename.java), which resolves every
occurrence through javac, so a rename can never hit an unrelated identifier.
The mapping lives in [tools/rename-symbols.txt](./tools/rename-symbols.txt).

Members that are still called `a`, `a2`, `b7`, … are the ones whose purpose is
not obvious from the code; they are the natural next step for another pass.
Locals are left as Vineflower's `varN`.

## Legal

This is an unofficial reverse engineering effort for research and preservation.
*The Sims* and all related assets are copyright Electronic Arts. The decompiled
classes in `com/ea/sims3` are derived from EA's original binary; the Java ME
shims and the tooling in `tools/` are original work.
