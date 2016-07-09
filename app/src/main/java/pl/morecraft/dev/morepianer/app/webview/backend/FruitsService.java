package pl.morecraft.dev.morepianer.app.webview.backend;

import java.util.List;

import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import static javafx.application.Platform.runLater;
import static org.json.simple.JSONValue.toJSONString;
import static pl.morecraft.dev.morepianer.app.javafx.webview.Java2JavascriptUtils.call;

public class FruitsService {

    // async function
    public void loadFruits(final Object callbackfunction) {

        // a database...
        final List<String> fruits = asList("orange", "apple", "banana", "strawberry");

        // launch a background thread (async)
        new Thread(() -> {
            try {
                shuffle(fruits);
                sleep(1000); //add some processing simulation...
                runLater(() ->
                        call(callbackfunction, toJSONString(fruits))
                );
            } catch (InterruptedException e) {
            }
        }
        ).start();
    }
}
