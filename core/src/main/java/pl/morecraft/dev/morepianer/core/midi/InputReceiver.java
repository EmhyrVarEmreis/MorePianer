package pl.morecraft.dev.morepianer.core.midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class InputReceiver implements Receiver {

    private String name;

    public InputReceiver(String name) {
        this.name = name;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        //System.out.println(name);
        if (message instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) message;
            int nChannel = shortMessage.getChannel();
            int nCommand = shortMessage.getCommand();
            int nData1 = shortMessage.getData1();
            int nData2 = shortMessage.getData2();

            //System.out.println(shortMessageToString(shortMessage));

            switch (nCommand) {
                case ShortMessage.NOTE_ON:
                    System.out.println("NOTE_ON " + toHex(nData1) + " " + toHex(nData2));
                    break;
                case ShortMessage.NOTE_OFF:
                    System.out.println("NOTE_OFF " + toHex(nData1) + " " + toHex(nData2));
                    break;
                default:
                    break;
            }

        } else {
            System.out.println("LongMessage");
        }
        //System.out.println();
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