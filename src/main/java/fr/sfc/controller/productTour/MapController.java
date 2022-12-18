package fr.sfc.controller.productTour;

import fr.sfc.container.productTour.MainProductTourContainer;
import fr.sfc.container.productTour.MapContainer;
import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.framework.item.Tag;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

public class MapController implements Controller {
    
    @AutoContainer
    private MapContainer component;

    private WebEngine engine;
    private JSObject js;
    private JavaConnector javaConnector = new JavaConnector();

    @Inject
    @Tag("container:root.producer")
    private MainProductTourContainer mainProductTourContainer;

    @Override
    public void setup() {
        engine = component.getWwMap().getEngine();
        engine.load(Resources.getResource("/html/map.html").toExternalForm());
        engine.getLoadWorker()
                .stateProperty()
                .addListener((obs, old, state) -> loadJavascript(engine, state));
        engine.reload();
    }

    private void loadJavascript(WebEngine engine, Worker.State state) {
        if (state == Worker.State.SUCCEEDED) {
            double latitude = 8.866667;
            double longitude = 2.333333;
            js = (JSObject) engine.executeScript("window");
            js.setMember("javac", javaConnector);
            Object map = js.call("createMap",  latitude, longitude);
        }
    }

    public static class JavaConnector {

        public double connect() {
            return 20.3;
        }

    }

}