package fr.sfc.controller;

import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.AutoComponent;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.component.productTour.MapComponent;
import javafx.scene.web.WebEngine;

public class MapController implements Controller {

    @AutoComponent
    private MapComponent component;

    @Override
    public void setup() {
        final WebEngine engine = component.getWwMap().getEngine();
        engine.load(Resources.getResource("/html/index.html").toExternalForm());
    }

}