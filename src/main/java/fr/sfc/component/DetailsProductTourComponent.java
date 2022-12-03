package fr.sfc.component;

import fr.sfc.api.controlling.Component;
import fr.sfc.api.controlling.SetComponent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class DetailsProductTourComponent extends GridPane implements Component {

    @SetComponent
    private MapComponent mapComponent;

    @SetComponent
    private ConfigProductTourComponent configProductTourComponent;

    private Button switcherMapToConfigButton;

    @Override
    public void setup() {
        switcherMapToConfigButton = new Button("Map View");
        getChildren().addAll(configProductTourComponent);
    }



}
