package pl.morecraft.dev.morepianer.app;

import javafx.application.Application;
import pl.morecraft.dev.morepianer.app.webview.MainApplication;

public class DesktopApplication {

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false"); // enhance fonts

        MainApplication app = new MainApplication();

        Application.launch(app.getClass(), args);
    }

}