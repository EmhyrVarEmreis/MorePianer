package pl.morecraft.dev.morepianer.core.model.scales;

import pl.morecraft.dev.morepianer.core.model.Scale;
import pl.morecraft.dev.morepianer.core.model.ScaleInfo;

@ScaleInfo
public abstract class MajorScale extends Scale {

    @Override
    public boolean isMajor() {
        return true;
    }

    @Override
    public boolean isMinor() {
        return false;
    }

}
