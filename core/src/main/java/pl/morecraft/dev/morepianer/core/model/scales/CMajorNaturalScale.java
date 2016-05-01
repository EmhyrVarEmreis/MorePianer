package pl.morecraft.dev.morepianer.core.model.scales;

import pl.morecraft.dev.morepianer.core.model.ScaleInfo;
import pl.morecraft.dev.morepianer.core.model.dict.ScaleTypeByIntervals;
import pl.morecraft.dev.morepianer.core.model.dict.ScaleTypeByNumberOfPitch;

import static pl.morecraft.dev.morepianer.core.model.dict.Tone.*;

@ScaleInfo(
        TONES_ASC = {C, D, E, F, G, A, B},
        TONES_DESC = {B, A, G, F, E, D, C},
        NAME_EN = "C major",
        NAME_DE = "C-Dur",
        SCALE_TYPE_BY_INTERVALS = ScaleTypeByIntervals.DIATONIC,
        SCALE_TYPE_BY_NUMBER_OF_PITCH = ScaleTypeByNumberOfPitch.HEPTATONIC
)
public class CMajorNaturalScale extends MajorScale {

}
