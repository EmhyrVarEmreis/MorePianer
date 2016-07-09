package pl.morecraft.dev.morepianer.app.webview.handlers;

import javafx.event.EventHandler;
import javafx.scene.web.WebEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WebAlertHandler implements EventHandler<WebEvent<String>> {

    private static final Logger log = LoggerFactory.getLogger(WebAlertHandler.class);

    @Override
    public void handle(WebEvent<String> event) {
        log.info(event.getData());
    }

}
