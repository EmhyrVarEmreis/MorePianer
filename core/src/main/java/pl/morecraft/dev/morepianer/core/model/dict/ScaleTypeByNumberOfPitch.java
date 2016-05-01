package pl.morecraft.dev.morepianer.core.model.dict;

public enum ScaleTypeByNumberOfPitch {

    OCTATONIC(8),
    HEPTATONIC(7),
    HEXATONIC(6),
    PENTATONIC(5),
    TETRATONIC(4),
    TRITONIC(3),
    DITONIC(2),
    MONOTONIC(1),
    VARIOUSTONIC(-1);

    private int number;

    ScaleTypeByNumberOfPitch(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
