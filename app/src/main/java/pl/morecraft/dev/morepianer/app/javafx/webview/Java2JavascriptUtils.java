package pl.morecraft.dev.morepianer.app.javafx.webview;

import javafx.event.EventHandler;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import netscape.javascript.JSObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utilities to connect Java objects as javascript objects in a WebEngine.
 * <p>
 * It also allows to call callback functions.
 *
 * @author lipido
 */
public class Java2JavascriptUtils {

    private static Map<WebEngine, Map<String, Object>> backendObjects = new HashMap<>();
    private static Set<WebEngine> webEnginesWithAlertChangeListener = new HashSet<>();

    private static boolean changing = false;

    /**
     * Registers a backend Java object as a Javascript variable.
     * The real connection to the webEngine comes when the javascript performs
     * an special "alert" message by invoking
     * "alert('__CONNECT__BACKEND__varname')" where varname is the javascript
     * variable we want to make available.
     * <p>
     * The call to this function has to be performed before the engine loads the
     * first page (where the alert call should take place).
     *
     * @param webEngine    The webEngine to register the new variable.
     * @param variableName The name of the variable in javascript.
     * @param backend      The Java backend object.
     */
    public static void connectBackendObject(final WebEngine webEngine, final String variableName, final Object backend) {

        registerBackendObject(webEngine, variableName, backend);

        // create a onAlertChangeListener. We always want to listen
        // to onAlert events, since via this event, the javascript front-end
        // will send us an special "alert" message asking to connect the
        // backend object as soon as possible(*).
        // However, if the programmer also wants to set
        // his own onAlert handler for this web engine,
        // we will create a handlerwrapper with our
        // behavior plus the programmer's one.

        // (*) It was impossible for me to re-connect the backend object
        // when the users navigates from one page to another page where the
        // backend object was also needed. The navigation erases any javascript
        // variables, so the backend has to be reconnected. However,
        // The recommended state change listeners on
        // webEngine were executed too late, after javascript code asking for the
        // backend object is executed, so it was not a solution.
        // The only way I found is to place a custom javacript "signaling"
        // code to ask Java to reconnect the backend object.
        // The solution was "alert", because we can listen to alert calls from
        // javascript, so via an special "alert" message, we can connect the
        // backend object again.
        // It is not a bad solution, because the programmer has only to inlude
        // a simple additional script (such as "mybackend.js") in the page
        // before any other scripts uses the backend variable.
        if (!webEnginesWithAlertChangeListener.contains(webEngine)) {
            if (webEngine.getOnAlert() == null) {
                webEngine.setOnAlert(new AlertEventHandlerWrapper(webEngine, null));
            }

            webEngine.onAlertProperty().addListener(
                    (listener, previous, newHandler) -> {

                        if (!changing) { // avoid recursive calls
                            changing = true;
                            webEngine.setOnAlert(
                                    new AlertEventHandlerWrapper(
                                            webEngine,
                                            newHandler
                                    )
                            );
                            changing = false;
                        }
                    }
            );
        }
        webEnginesWithAlertChangeListener.add(webEngine);
    }

    private static void registerBackendObject(final WebEngine webEngine, final String variableName, final Object backend) {
        Map<String, Object> webEngineBridges = backendObjects.get(webEngine);
        if (webEngineBridges == null) {
            webEngineBridges = new HashMap<>();
            backendObjects.put(webEngine, webEngineBridges);
        }
        webEngineBridges.put(variableName, backend);

    }

    private static void connectToWebEngine(WebEngine engine, String varname) {
        if (backendObjects.containsKey(engine) && backendObjects.get(engine).containsKey(varname)) {
            JSObject window = (JSObject) engine.executeScript("window");
            window.setMember(varname, backendObjects.get(engine).get(varname));
        }
    }

    public static void call(Object callback, Object argument) {
        // it is not a json object, so let the
        // API to create the javascript object
        ((JSObject) callback).eval("this(" + argument.toString() + ")");
    }

    private final static class AlertEventHandlerWrapper implements EventHandler<WebEvent<String>> {

        private static final String CONNECT_BACKEND_MAGIC_WORD = "__CONNECT__BACKEND__";
        private final EventHandler<WebEvent<String>> wrappedHandler;
        private WebEngine engine;

        private AlertEventHandlerWrapper(WebEngine engine, EventHandler<WebEvent<String>> wrappedHandler) {
            this.engine = engine;
            this.wrappedHandler = wrappedHandler;
        }

        @Override
        public void handle(WebEvent<String> stringWebEvent) {
            if (stringWebEvent.getData().contains(CONNECT_BACKEND_MAGIC_WORD)) {
                String variableName = stringWebEvent.getData().substring(CONNECT_BACKEND_MAGIC_WORD.length());
                connectToWebEngine(engine, variableName);
            } else if (wrappedHandler != null) {
                wrappedHandler.handle(stringWebEvent);
            }
        }

    }
}