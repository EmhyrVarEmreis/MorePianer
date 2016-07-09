package pl.morecraft.dev.morepianer.core.midi;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.morecraft.dev.morepianer.core.midi.exception.MidiDeviceUnavailableException;
import pl.morecraft.dev.morepianer.core.model.Note;

import javax.sound.midi.*;
import java.util.*;

public class Device {

    public static final Logger log = LoggerFactory.getLogger(Device.class);

    protected MidiDevice midiDevice;

    private DeviceInfo deviceInfo;

    private Queue<MidiMessage> midiMessages;
    private Queue<ShortMessage> shortMessages;

    private boolean opened;

    private Set<Note> pressedNotes;

    protected Device(DeviceInfo deviceInfo, MidiDevice midiDevice) {
        this.midiDevice = midiDevice;
        this.deviceInfo = deviceInfo;
        this.midiMessages = new CircularFifoQueue<>(250);
        this.shortMessages = new CircularFifoQueue<>(250);
        this.pressedNotes = new LinkedHashSet<>();
        this.opened = false;
    }

    public void open() throws MidiDeviceUnavailableException {
        if (midiDevice == null) {
            throw new NullPointerException("Device is not set: " + deviceInfo.getName());
        }
        try {
            log.info("Opening device: [{}]", deviceInfo.getName());
            setReceiver(midiDevice.getTransmitter());
            midiDevice.getTransmitters().forEach(
                    this::setReceiver
            );
            midiDevice.open();
            opened = true;
            deviceInfo.setAvailable(true);
            log.info("Device [{}] opened and marked as available", deviceInfo.getName());
        } catch (MidiUnavailableException e) {
            log.info("Device [{}] is unavailable", deviceInfo.getName());
            throw new MidiDeviceUnavailableException("Device unavailable: " + midiDevice.getDeviceInfo().getName(), e);
        }
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    private void setReceiver(Transmitter transmitter) {
        transmitter.setReceiver(new InputReceiver(this));
    }

    private synchronized Collection<MidiMessage> getMidiMessages() {
        if (midiMessages == null) {
            return new LinkedList<>();
        }
        return new LinkedList<>(midiMessages);
    }

    private synchronized Collection<MidiMessage> getMidiShortMessages() {
        if (shortMessages == null) {
            return new LinkedList<>();
        }
        return new LinkedList<>(shortMessages);
    }

    public synchronized DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public synchronized Collection<Note> getPressedNotes() {
        return new LinkedList<>(pressedNotes);
    }

    public synchronized boolean isNotePressed(Note note) {
        return pressedNotes.contains(note);
    }

    protected synchronized void switchNote(Note note) {
        if (!pressedNotes.add(note)) {
            pressedNotes.remove(note);
        }
    }

    protected synchronized boolean pressNote(Note note) {
        return pressedNotes.add(note);
    }

    protected synchronized boolean releaseNote(Note note) {
        return pressedNotes.remove(note);
    }

}
