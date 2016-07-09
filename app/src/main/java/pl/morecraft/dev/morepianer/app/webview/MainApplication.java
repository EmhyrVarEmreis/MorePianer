package pl.morecraft.dev.morepianer.app.webview;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.morepianer.app.webview.backend.CalculatorService;
import pl.morecraft.dev.morepianer.app.webview.backend.FruitsService;

import static pl.morecraft.dev.morepianer.app.javafx.webview.Java2JavascriptUtils.connectBackendObject;

@Service
public class MainApplication extends Application {

    private static final String PAGE = "/index.html";

    private double xOffset = 0;
    private double yOffset = 0;

    @Autowired
    private WebView webView;

    @Override
    public void start(Stage primaryStage) {
        createWebView(primaryStage, PAGE);
    }

    private void createWebView(Stage primaryStage, String page) {

        webView = new WebView();

        // connect the FruitsService instance as "fruitsService"
        // javascript variable
        connectBackendObject(webView.getEngine(), "fruitsService", new FruitsService());

        // connect the CalculatorService instance as "calculatorService"
        // javascript variable
        connectBackendObject(webView.getEngine(), "calculatorService", new CalculatorService());

        // show "alert" Javascript messages in stdout (useful to debug)
        webView.getEngine().setOnAlert(arg0 -> System.err.println("alertwb1: " + arg0.getData()));

        // load index.html
        webView.getEngine().load(getClass().getResource(page).toExternalForm());

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
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        borderPane.setTop(toolBar);
        borderPane.setCenter(webView);


        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(new Scene(borderPane, 800, 600));
        primaryStage.setTitle("WebView with Java backend");

        ResizeHelper.addResizeListener(primaryStage);

        primaryStage.show();
    }

    class WindowButtons extends HBox {

        public WindowButtons() {
            Button closeBtn = new Button("X");

            closeBtn.setOnAction(actionEvent -> Platform.exit());

            this.getChildren().add(closeBtn);
        }

    }

}
