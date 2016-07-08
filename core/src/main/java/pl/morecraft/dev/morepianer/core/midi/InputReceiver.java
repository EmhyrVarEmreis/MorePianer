package pl.morecraft.dev.morepianer.core.midi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.morecraft.dev.morepianer.core.model.Note;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class InputReceiver implements Receiver {

    public static final Logger log = LoggerFactory.getLogger(InputReceiver.class);

    private Device device;

    public InputReceiver(Device device) {
        this.device = device;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) message;

            int nChannel = shortMessage.getChannel();
            int nCommand = shortMessage.getCommand();
            int nData1 = shortMessage.getData1();
            int nData2 = shortMessage.getData2();

            Note note = Note.getNoteFromMidiNumber(nData1);

            try {
                switch (nCommand) {
                    case ShortMessage.NOTE_ON:
                        log.debug("Received ShortMessage [{}] from [{}]: [{}]: [{}]", shortMessageToString(shortMessage), device.getDeviceInfo().getName(), "NOTE_ON", note);
                        device.switchNote(note);
                        break;
                    case ShortMessage.NOTE_OFF:
                        log.debug("Received ShortMessage [{}] from [{}]: [{}]: [{}]", shortMessageToString(shortMessage), device.getDeviceInfo().getName(), "NOTE_OFF", note);
                        device.releaseNote(note);
                        break;
                    case ShortMessage.CONTROL_CHANGE:
                        log.debug("Received ShortMessage [{}] from [{}]: [{}]: [{}]: [{}]", shortMessageToString(shortMessage), device.getDeviceInfo().getName(), "CONTROL_CHANGE", nData1, nData2);
                        device.releaseNote(note);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            log.debug("Received MidiMessage from [{}]", device.getDeviceInfo().getName());
        }
    }

    @Override
    public void close() {
    }

    private String shortMessageToString(ShortMessage shortMessage) {
        return toHex(shortMessage.getChannel())
                + " " + toHex(shortMessage.getCommand())
                + " " + toHex(shortMessage.getData1())
                + " " + toHex(shortMessage.getData2());
    }


    private String toHex(int i) {
        return String.format("%02X", i);
    }

}