package pl.morecraft.dev.morepianer.core.model;

public class Note {

    private Octave octave;
    private Tone tone;

    public Note() {

    }

    public Note(Octave octave, Tone tone) {
        this.octave = octave;
        this.tone = tone;
    }

    public Octave getOctave() {
        return octave;
    }

    public void setOctave(Octave octave) {
        this.octave = octave;
    }

    public Tone getTone() {
        return tone;
    }

    public void setTone(Tone tone) {
        this.tone = tone;
    }

    public Integer getMidiNumber() {
        if (octave == null || tone == null) {
            throw new NullPointerException("Note must have non-null octave and tone");
        }
        return ((octave.getNumber() + 1) * tone.getNumber()) - 1;
    }

    public static Note getNoteFromMidiNumber(int number) {
        int toneNumber = (number % Tone.RANGE) + 1;
        int octaveNumber = (number / Tone.RANGE) - 1;

        if (toneNumber < Tone.RANGE_MIN || toneNumber > Tone.RANGE_MAX) {
            throw new IllegalArgumentException("Invalid tone number: " + toneNumber);
        }
        if (octaveNumber < Octave.RANGE_MIN || octaveNumber > Octave.RANGE_MAX) {
            throw new IllegalArgumentException("Invalid octave number: " + octaveNumber);
        }

        return new Note(Octave.valueOf(octaveNumber), Tone.valueOf(toneNumber));
    }

    public String toString(NoteFormat format) {
        switch (format) {
            case GERMAN:
            default:
                return tone.toString() + " (" + octave.toString() + ")";
            case GERMAN_SHORTHAND:
                // TODO GERMAN_SHORTHAND
                return "NOT IMPLEMENTED YET";
            case GERMAN_NUMBERED:
                // TODO GERMAN_NUMBERED
                return "NOT IMPLEMENTED YET";
            case ENGLISH:
                // TODO ENGLISH
                return "NOT IMPLEMENTED YET";
            case ENGLISH_SHORTHAND:
                // TODO ENGLISH_SHORTHAND
                return "NOT IMPLEMENTED YET";
            case ENGLISH_NUMBERED:
                // TODO ENGLISH_NUMBERED
                return "NOT IMPLEMENTED YET";
            case NUMBER:
                return tone.getNumber() + "-" + octave.getNumber();
        }
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (octave != note.octave) return false;
        /** N O T E !
         * GIS is the same note as GES
         */
        return tone.isEqual(note.tone);

    }

    @Override
    public int hashCode() {
        int result = octave != null ? octave.hashCode() : 0;
        /** N O T E !
         * GIS is the same note as GES
         */
        result = 31 * result + (tone != null ? tone.getNumber() * 47 : 0);
        return result;
    }

}
