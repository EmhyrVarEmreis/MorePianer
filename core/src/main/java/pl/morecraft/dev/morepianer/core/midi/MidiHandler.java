package pl.morecraft.dev.morepianer.core.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import java.util.List;

public class MidiHandler {

    public MidiHandler() {
        MidiDevice device;
        MidiDevice.Info[] midiDeviceInfo = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : midiDeviceInfo) {
            try {
                device = MidiSystem.getMidiDevice(info);
                System.out.println(info);

                List<Transmitter> transmitters = device.getTransmitters();

                for (Transmitter transmitter : transmitters) {
                    transmitter.setReceiver(
                            new InputReceiver(device.getDeviceInfo().toString())
                    );
                }

                Transmitter trans = device.getTransmitter();
                trans.setReceiver(new InputReceiver(device.getDeviceInfo().toString()));

                device.open();
                System.out.println(device.getDeviceInfo() + " Was Opened");
                System.out.println();

            } catch (MidiUnavailableException ignored) {
            }
        }


    }
}