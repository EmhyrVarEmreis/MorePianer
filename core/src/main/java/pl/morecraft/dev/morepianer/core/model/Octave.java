package pl.morecraft.dev.morepianer.core.model;

public enum Octave {

    SUB_SUB_CONTRA(-1),
    SUB_CONTRA(0),
    CONTRA(1),
    GREAT(2),
    SMALL(3),
    ONE_LINED(4),
    TWO_LINED(5),
    THREE_LINED(6),
    FOUR_LINED(7),
    FIVE_LINED(8),
    SIX_LINED(9);

    private int number;

    public static int RANGE_MIN = -1;
    public static int RANGE_MAX = 9;
    public static int RANGE = 11;

    Octave(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static Octave valueOf(int number) {
        for (Octave octave : Octave.values()) {
            if (octave.getNumber() == number) {
                return octave;
            }
        }
        return null;
    }

}
