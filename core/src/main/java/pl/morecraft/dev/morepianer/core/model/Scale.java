package pl.morecraft.dev.morepianer.core.model;

import pl.morecraft.dev.morepianer.core.model.dict.ScaleTypeByIntervals;
import pl.morecraft.dev.morepianer.core.model.dict.ScaleTypeByNumberOfPitch;
import pl.morecraft.dev.morepianer.core.model.dict.Tone;

@ScaleInfo
public abstract class Scale {

    private ScaleInfo scaleInfo;

    public Tone[] getTonesAscending() {
        return getScaleInfo().TONES_ASC();
    }

    public Tone[] getTonesDescending() {
        return getScaleInfo().TONES_DESC();
    }

    public String[] getNames() {
        return getScaleInfo().NAME_EN();
    }

    public String[] getGerNames() {
        return getScaleInfo().NAME_DE();
    }

    public ScaleTypeByIntervals getTypeByInvervals() {
        return getScaleInfo().SCALE_TYPE_BY_INTERVALS();
    }

    public ScaleTypeByNumberOfPitch getTypeByNumberOfPitch() {
        return getScaleInfo().SCALE_TYPE_BY_NUMBER_OF_PITCH();
    }

    public abstract boolean isMajor();

    public abstract boolean isMinor();

    private ScaleInfo getScaleInfo() {
        if (scaleInfo == null) {
            scaleInfo = this.getClass().getAnnotation(ScaleInfo.class);
        }
        return scaleInfo;
    }

}
