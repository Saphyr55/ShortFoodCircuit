package fr.sfc.controller.productTour;

import fr.sfc.component.productTour.MapComponent;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoComponent;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.nio.file.Files;

public class MapController implements Controller {
    
    @AutoComponent
    private MapComponent component;

    private WebEngine engine;
    private JSObject js;
    private JavaConnector javaConnector = new JavaConnector();

    @Override
    public void setup() {
        engine = component.getWwMap().getEngine();
        engine.load(Resources.getResource("/html/map.html").toExternalForm());

        //loadJsFile("/js/map.js");

        engine.getLoadWorker()
                .stateProperty()
                .addListener((obs, old, state) -> loadJavascript(engine, state));
        engine.reload();
    }

    private void loadJavascript(WebEngine engine, Worker.State state) {
        if (state == Worker.State.SUCCEEDED) {
            double latitude = 48.866667;
            double longitude = 2.333333;
            js = (JSObject) engine.executeScript("window");
            js.setMember("javac", javaConnector);
            Object map = js.call("createMap",  latitude, longitude);
        }
    }

    private void loadJsFile(String resource) {
        try {
            var content = Files.readString(Resources.getFileResource(resource).toPath());
            engine.loadContent("<script type=\"text/javascript\">"+content+"</script>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class JavaConnector {

        public double connect() {
            return 20.3;
        }

    }

}