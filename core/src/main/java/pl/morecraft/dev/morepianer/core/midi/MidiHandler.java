package pl.morecraft.dev.morepianer.core.midi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.morecraft.dev.morepianer.core.midi.exception.MidiDeviceUnavailableException;
import pl.morecraft.dev.morepianer.core.model.Note;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MidiHandler {

    public static final Logger log = LoggerFactory.getLogger(MidiHandler.class);

    private Map<DeviceInfo, Device> midiDeviceMap;

    public MidiHandler() {
        midiDeviceMap = new HashMap<>();
        init();
    }

    private void init() {
        for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
            DeviceInfo deviceInfo = new DeviceInfo(info);
            log.info("Registering device: [{}]", deviceInfo.getName());
            midiDeviceMap.put(
                    deviceInfo,
                    new Device(deviceInfo, getMidiDevice(info))
            );
            log.info("Device [{}] registered", deviceInfo.getName());
        }
    }

    public void open() {
        midiDeviceMap.values().forEach(this::open);
    }

    public void open(DeviceInfo deviceInfo) {
        open(getDevice(deviceInfo));
    }

    private void open(Device device) {
        try {
            device.open();
            log.info("Device [{}] opened", device.getDeviceInfo().getName());
        } catch (MidiDeviceUnavailableException e) {
            log.info("Device [{}] is unavailable", device.getDeviceInfo().getName());
            e.printStackTrace();
        }
    }

    public Collection<DeviceInfo> getDeviceInfos() {
        return midiDeviceMap.keySet();
    }

    public Collection<Device> getDevices() {
        return midiDeviceMap.values();
    }

    public Collection<DeviceInfo> getAvailableDeviceInfos() {
        return midiDeviceMap.keySet().stream().filter(
                DeviceInfo::isAvailable
        ).collect(Collectors.toCollection(
                ArrayList::new
        ));
    }

    public Collection<Device> getAvailableDevices() {
        return midiDeviceMap.values().stream().filter(
                device -> device.getDeviceInfo().isAvailable()
        ).collect(Collectors.toCollection(
                ArrayList::new
        ));
    }

    public Collection<Note> getAllPressedNotes() {
        return getAvailableDevices().stream().flatMap(
                device -> device.getPressedNotes().stream()
        ).collect(Collectors.toSet());
    }

    public Device getDevice(DeviceInfo deviceInfo) {
        return midiDeviceMap.get(deviceInfo);
    }

    public Device getDevice(String deviceName) {
        DeviceInfo deviceInfo = midiDeviceMap.keySet().stream().filter(
                info -> info.getName().equalsIgnoreCase(deviceName)
        ).findFirst().orElseThrow(
                () -> new NullPointerException("MIDI Device not found: " + deviceName)
        );
        return midiDeviceMap.get(deviceInfo);
    }

    private MidiDevice getMidiDevice(MidiDevice.Info info) {
        try {
            return MidiSystem.getMidiDevice(info);
        } catch (MidiUnavailableException e) {
            throw new MidiDeviceUnavailableException("Device unavailable: " + info.getName(), e);
        }
    }

}
