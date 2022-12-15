package fr.sfc.container.productTour;

import fr.sfc.framework.Resources;
import fr.sfc.framework.controlling.Container;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ConfigProductTourContainer extends AnchorPane implements Container {


    private FXMLLoader loader;

    public ConfigProductTourContainer() throws IOException {
        loader = new FXMLLoader();
        loader.load(Resources.getResource("/fxml/specifiesProductTour.fxml"));
        loader.setRoot(this);
    }

    @Override
    public void setup()  {

    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }
}
