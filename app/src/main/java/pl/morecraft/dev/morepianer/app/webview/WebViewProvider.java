package pl.morecraft.dev.morepianer.app.webview;

import javafx.scene.web.WebView;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class WebViewProvider {

    @Bean
    static WebView getWebView() {
        return new WebView();
    }

}
