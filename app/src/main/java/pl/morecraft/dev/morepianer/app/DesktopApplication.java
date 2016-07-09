package pl.morecraft.dev.morepianer.app;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.morecraft.dev.morepianer.app.waiter.WaiterControl;
import pl.morecraft.dev.morepianer.app.waiter.WaiterDialog;
import pl.morecraft.dev.morepianer.app.webview.PrimaryStageProducer;

@SpringBootApplication
public class DesktopApplication extends Application {

    public static void main(String[] args) {

        System.setProperty("prism.lcdtext", "false");

        new WaiterDialog();

        Application.launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"beanx.xml"});
        ConfigurableApplicationContext context = SpringApplication.run(DesktopApplication.class);

        //new ConfigurationWriter(context, "beans.xml");

        PrimaryStageProducer app = context.getBean(PrimaryStageProducer.class);

        primaryStage = app.apply(primaryStage);

        primaryStage.show();
        WaiterControl.closeWaiter();

    }

}