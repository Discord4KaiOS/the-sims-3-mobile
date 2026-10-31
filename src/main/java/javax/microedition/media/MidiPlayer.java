package javax.microedition.media;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.InvalidMidiDataException;

/**
 * Renders {@code audio/midi} with the JDK's software synthesizer.
 *
 * The game ships its music as standard MIDI files, so a {@link Sequencer}
 * feeding a {@link Synthesizer} is all that is needed; looping is handed to the
 * sequencer itself so the loops are gapless.
 */
final class MidiPlayer extends AudioPlayer {
    private final byte[] data;
    private Sequencer sequencer;
    private Synthesizer synthesizer;
    private volatile int generation;
    private long seekMillis;

    MidiPlayer(byte[] data) {
        super("audio/midi");
        this.data = data;
    }

    @Override
    protected void open() throws MediaException {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            sequencer = MidiSystem.getSequencer(false);
            sequencer.open();
            sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
            sequencer.setSequence(new ByteArrayInputStream(data));
        } catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
            release();
            throw new MediaException("cannot open MIDI: " + e.getMessage());
        }
    }

    @Override
    protected void play() throws MediaException {
        if (sequencer == null) {
            throw new MediaException("player is not realized");
        }
        int count = loopCount();
        sequencer.setLoopCount(count < 0 ? Sequencer.LOOP_CONTINUOUSLY : count - 1);
        sequencer.setMicrosecondPosition(seekMillis * 1000);
        applyVolume();
        sequencer.start();
        int mine = ++generation;
        startThread("midp-midi-player", () -> watch(mine));
    }

    @Override
    protected void halt() {
        generation++;
        if (sequencer != null && sequencer.isOpen()) {
            sequencer.stop();
        }
    }

    @Override
    protected void release() {
        generation++;
        if (sequencer != null) {
            sequencer.stop();
            sequencer.close();
            sequencer = null;
        }
        if (synthesizer != null) {
            synthesizer.close();
            synthesizer = null;
        }
    }

    @Override
    protected void applyVolume() {
        if (synthesizer == null) {
            return;
        }
        int value = Math.round(gain() * 127f);
        for (MidiChannel channel : synthesizer.getChannels()) {
            if (channel != null) {
                channel.controlChange(7, value);
            }
        }
    }

    @Override
    protected long mediaTime() {
        return sequencer != null && sequencer.isOpen() ? sequencer.getMicrosecondPosition() / 1000 : 0;
    }

    @Override
    protected void seek(long millis) {
        seekMillis = millis;
        if (sequencer != null && sequencer.isOpen()) {
            sequencer.setMicrosecondPosition(millis * 1000);
        }
    }

    @Override
    protected long duration() {
        return sequencer != null && sequencer.isOpen() ? sequencer.getMicrosecondLength() / 1000 : TIME_UNKNOWN;
    }

    /**
     * The sequencer stops by itself once the last loop has been played; that is
     * the only reliable end-of-media signal across JDK synthesizers.
     */
    private void watch(int mine) {
        Sequencer seq = sequencer;
        if (seq == null) {
            return;
        }
        try {
            Thread.sleep(60);
            while (mine == generation && seq.isOpen()) {
                if (!seq.isRunning()) {
                    finish();
                    return;
                }
                Thread.sleep(25);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
