package pl.morecraft.dev.morepianer.core.model.dict;

import static pl.morecraft.dev.morepianer.core.model.dict.Sign.Flat;
import static pl.morecraft.dev.morepianer.core.model.dict.Sign.Sharp;

public enum Tone {

    B_SHARP(1, Sharp),
    C(1, null),
    C_SHARP(2, Sharp),
    D_FLAT(2, Flat),
    D(3, null),
    D_SHARP(4, Sharp),
    E_FLAT(4, Flat),
    E(5, null),
    F_FLAT(5, Flat),
    E_SHARP(6, Sharp),
    F(6, null),
    F_SHARP(7, Sharp),
    G_FLAT(7, Flat),
    G(8, null),
    G_SHARP(9, Sharp),
    A_FLAT(9, Flat),
    A(10, null),
    A_SHARP(11, Sharp),
    B_FLAT(11, Flat),
    B(12, null),
    C_FLAT(12, Flat);

    private int number;
    private Sign sign;

    public static int RANGE_MIN = 1;
    public static int RANGE_MAX = 12;
    public static int RANGE = 12;

    Tone(int number, Sign sign) {
        this.number = number;
        this.sign = sign;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public boolean isAccidental() {
        return sign != null;
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

    /**
     * Checks if two Tones are exact. NOTE: F_SHARP is the same tone as G_FLAT
     *
     * @param o java.lang.Object to compare to
     * @return TRUE if tone is equal to provided java.lang.Object
     */
    public boolean isEqual(Object o) {
        return o instanceof Tone && Integer.valueOf(number).equals(((Tone) o).getNumber());
    }

}
