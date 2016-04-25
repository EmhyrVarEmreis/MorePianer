package pl.morecraft.dev.morepianer.core.midi;

import javax.sound.midi.*;
import java.util.List;

public class MidiHandler {

    public MidiHandler() {
        MidiDevice device;
        MidiDevice.Info[] midiDeviceInfo = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : midiDeviceInfo) {
            try {
                device = MidiSystem.getMidiDevice(info);
                //does the device have any transmitters?
                //if it does, add it to the device list
                System.out.println(info);

                //get all transmitters
                List<Transmitter> transmitters = device.getTransmitters();

                //and for each transmitter
                for (Transmitter transmitter : transmitters) {
                    //create a new receiver
                    transmitter.setReceiver(
                            //using my own MidiInputReceiver
                            new InputReceiver(device.getDeviceInfo().toString())
                    );
                }

                Transmitter trans = device.getTransmitter();
                trans.setReceiver(new InputReceiver(device.getDeviceInfo().toString()));

                //open each device
                device.open();
                //if code gets this far without throwing an exception
                //print a success message
                System.out.println(device.getDeviceInfo() + " Was Opened");
                System.out.println();

            } catch (MidiUnavailableException ignored) {
            }
        }


    }
}