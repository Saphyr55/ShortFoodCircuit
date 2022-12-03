package fr.sfc.controller;

import com.google.common.io.Resources;
import fr.sfc.api.controlling.AutoComponent;
import fr.sfc.api.controlling.Controller;
import fr.sfc.component.productTour.MapComponent;
import javafx.scene.web.WebEngine;

public class MapController implements Controller {

    @AutoComponent
    private MapComponent component;

    @Override
    public void setup() {
        final WebEngine engine = component.getWwMap().getEngine();
        engine.load(Resources.getResource("index.html").toExternalForm());
    }

}