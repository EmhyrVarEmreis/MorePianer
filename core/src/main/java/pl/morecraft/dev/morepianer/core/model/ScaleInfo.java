package pl.morecraft.dev.morepianer.core.model;

import pl.morecraft.dev.morepianer.core.model.dict.ScaleTypeByIntervals;
import pl.morecraft.dev.morepianer.core.model.dict.ScaleTypeByNumberOfPitch;
import pl.morecraft.dev.morepianer.core.model.dict.Tone;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScaleInfo {

    Tone[] TONES_ASC() default {};

    Tone[] TONES_DESC() default {};

    String[] NAME_EN() default {};

    String[] NAME_DE() default {};

    ScaleTypeByIntervals SCALE_TYPE_BY_INTERVALS() default ScaleTypeByIntervals.VARIOUS_INTERVALS;

    ScaleTypeByNumberOfPitch SCALE_TYPE_BY_NUMBER_OF_PITCH() default ScaleTypeByNumberOfPitch.VARIOUSTONIC;

}
