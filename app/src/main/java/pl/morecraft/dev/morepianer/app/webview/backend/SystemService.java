package pl.morecraft.dev.morepianer.app.webview.backend;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SystemService {

    private static final Logger log = LoggerFactory.getLogger(SystemService.class);

    public void exit() {
        log.info("Exit");
        Platform.exit();
    }

}
