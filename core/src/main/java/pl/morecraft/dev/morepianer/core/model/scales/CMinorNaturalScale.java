package pl.morecraft.dev.morepianer.core.model.scales;

import pl.morecraft.dev.morepianer.core.model.ScaleInfo;
import pl.morecraft.dev.morepianer.core.model.dict.ScaleTypeByIntervals;
import pl.morecraft.dev.morepianer.core.model.dict.ScaleTypeByNumberOfPitch;

import static pl.morecraft.dev.morepianer.core.model.dict.Tone.*;

@ScaleInfo(
        TONES_ASC = {C, D, E_FLAT, F, G, A_FLAT, B_FLAT},
        TONES_DESC = {B_FLAT, A_FLAT, G, F, E_FLAT, D, C},
        NAME_EN = "C minor",
        NAME_DE = "C-Moll",
        SCALE_TYPE_BY_INTERVALS = ScaleTypeByIntervals.DIATONIC,
        SCALE_TYPE_BY_NUMBER_OF_PITCH = ScaleTypeByNumberOfPitch.HEPTATONIC
)
public class CMinorNaturalScale extends MinorScale {

}
