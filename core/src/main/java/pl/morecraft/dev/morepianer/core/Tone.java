package pl.morecraft.dev.morepianer.core;

public enum Tone {

    C(1, false),
    CIS(2, true),
    DES(2, true),
    D(3, false),
    DIS(4, true),
    ES(4, true),
    E(5, false),
    F(6, false),
    FIS(7, true),
    GES(7, true),
    G(8, false),
    GIS(9, true),
    AS(9, true),
    A(10, false),
    AIS(11, true),
    B(11, true),
    H(12, false);

    private int number;
    private boolean isAccidental;

    public static int RANGE_MIN = 1;
    public static int RANGE_MAX = 12;
    public static int RANGE = 12;

    Tone(int number, boolean isAccidental) {
        this.number = number;
        this.isAccidental = isAccidental;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isAccidental() {
        return isAccidental;
    }

    public void setAccidental(boolean accidental) {
        isAccidental = accidental;
    }

    public static Tone valueOf(int number) {
        for (Tone tone : Tone.values()) {
            if (tone.getNumber() == number) {
                return tone;
            }
        }
        return null;
    }

    public static Tone valueOf(int number, int previousNumber) {
        for (Tone tone : Tone.values()) {
            if (tone.getNumber() == number) {
                if (previousNumber > number && tone.isAccidental()) {
                    continue;
                }
                return tone;
            }
        }
        return null;
    }

}
