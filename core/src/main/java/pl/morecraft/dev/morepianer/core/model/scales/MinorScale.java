package pl.morecraft.dev.morepianer.core.model.scales;

import pl.morecraft.dev.morepianer.core.model.Scale;
import pl.morecraft.dev.morepianer.core.model.ScaleInfo;

@ScaleInfo
public abstract class MinorScale extends Scale {

    @Override
    public boolean isMajor() {
        return false;
    }

    @Override
    public boolean isMinor() {
        return true;
    }

}
