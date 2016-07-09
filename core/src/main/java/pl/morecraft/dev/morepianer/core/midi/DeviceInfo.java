package pl.morecraft.dev.morepianer.core.midi;

import javax.sound.midi.MidiDevice;

public class DeviceInfo {

    protected MidiDevice.Info info;

    private boolean available;

    protected DeviceInfo() {
        this.info = null;
    }

    protected DeviceInfo(MidiDevice.Info info) {
        this.info = info;
    }

    public String getName() {
        return info.getName();
    }

    public String getDescription() {
        return info.getDescription();
    }

    public String getVersion() {
        return info.getVersion();
    }

    public String getVendor() {
        return info.getVendor();
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
