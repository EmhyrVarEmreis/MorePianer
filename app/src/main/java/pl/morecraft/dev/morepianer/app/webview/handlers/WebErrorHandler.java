package pl.morecraft.dev.morepianer.app.webview.handlers;

import javafx.event.EventHandler;
import javafx.scene.web.WebErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WebErrorHandler implements EventHandler<WebErrorEvent> {

    private static final Logger log = LoggerFactory.getLogger(WebErrorHandler.class);

    @Override
    public void handle(WebErrorEvent event) {
        log.info(event.getMessage());
    }

}
