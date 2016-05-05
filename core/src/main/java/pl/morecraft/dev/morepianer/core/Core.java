package pl.morecraft.dev.morepianer.core;

import pl.morecraft.dev.morepianer.core.midi.MidiHandler;
import pl.morecraft.dev.morepianer.core.model.dict.NoteFormat;

import java.util.stream.Collectors;

public class Core {

    public static void main(String[] args) {
        MidiHandler handler = new MidiHandler();
        handler.open();

        Thread thread = new PressedKeysTest(handler);
        thread.start();

    }

    static class PressedKeysTest extends Thread {

        private MidiHandler handler;

        public PressedKeysTest(MidiHandler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                System.out.println(
                        handler.getAllPressedNotes().stream().map(
                                note -> note.toString(NoteFormat.GERMAN)
                        ).collect(Collectors.joining(", "))
                );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
