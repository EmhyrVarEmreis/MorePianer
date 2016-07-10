package pl.morecraft.dev.morepianer.app.webview;

import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.morepianer.app.webview.backend.SystemService;
import pl.morecraft.dev.morepianer.app.webview.handlers.WebAlertHandler;
import pl.morecraft.dev.morepianer.app.webview.handlers.WebErrorHandler;

import static pl.morecraft.dev.morepianer.app.javafx.webview.Java2JavascriptUtils.connectBackendObject;

@Service
public class PrimaryStageProducer implements Function<Stage, Stage> {

    private static final String PAGE = "/index.html";

    @Autowired
    private WebView webView;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public Stage apply(Stage stage) {

        // connect the Service instance as "fruitsService" javascript variable
        //connectBackendObject(webView.getEngine(), "fruitsService", new FruitsService());
        //connectBackendObject(webView.getEngine(), "calculatorService", new CalculatorService());
        connectBackendObject(webView.getEngine(), "systemService", new SystemService());

        // show "alert" Javascript messages in stdout (useful to debug)
        webView.getEngine().setOnAlert(new WebAlertHandler());
        webView.getEngine().setOnError(new WebErrorHandler());

        // load index.html
        webView.getEngine().load(getClass().getResource(PAGE).toExternalForm());

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: white;");

        ToolBar toolBar = new ToolBar();

        int height = 15;
        toolBar.setPrefHeight(height);
        toolBar.setMinHeight(height);
        toolBar.setMaxHeight(height);
        //toolBar.getItems().add(new WindowButtons());

        toolBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        toolBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        borderPane.setTop(toolBar);
        borderPane.setCenter(webView);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(borderPane, 1024, 768));
        stage.setTitle("MorePianer");

        ResizeHelper.addResizeListener(stage);

        return stage;
    }

}
