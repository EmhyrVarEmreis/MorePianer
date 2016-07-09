package pl.morecraft.dev.morepianer.core.midi.exception;

public class MidiDeviceUnavailableException extends RuntimeException {

    public MidiDeviceUnavailableException(String message) {
        super(message);
    }

    public MidiDeviceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
