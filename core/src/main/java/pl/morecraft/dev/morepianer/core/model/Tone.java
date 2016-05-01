package pl.morecraft.dev.morepianer.core.model;

import static pl.morecraft.dev.morepianer.core.model.Sign.Sharp;
import static pl.morecraft.dev.morepianer.core.model.Sign.Flat;

public enum Tone {

    C(1, null),
    CIS(2, Sharp),
    DES(2, Flat),
    D(3, null),
    DIS(4, Sharp),
    ES(4, Flat),
    E(5, null),
    F(6, null),
    FIS(7, Sharp),
    GES(7, Flat),
    G(8, null),
    GIS(9, Sharp),
    AS(9, Flat),
    A(10, null),
    AIS(11, Sharp),
    B(11, Flat),
    H(12, null);

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

}
