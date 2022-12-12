package fr.sfc.container.admin;

import fr.sfc.framework.controlling.Container;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class SpecificsProducerContainer extends GridPane implements Container {

    private GridPane nameGridPane;
    private Label lastnameProducerLabel;
    private Label firstnameProducerLabel;


    @Override
    public void setup() {
        nameGridPane = new GridPane();

        lastnameProducerLabel = new Label("Lastname");
        lastnameProducerLabel.setFont(Font.font("Arial", 30));
        lastnameProducerLabel.setPadding(new Insets(0,15,0, 0));

        firstnameProducerLabel = new Label("Firstname");
        firstnameProducerLabel.setFont(Font.font("Arial", 30));
        firstnameProducerLabel.setPadding(new Insets(0,0,0, 15));

        addColumn(0, lastnameProducerLabel);
        addColumn(1, firstnameProducerLabel);
        setPadding(new Insets(30));

        addRow(0, nameGridPane);

    }

    public GridPane getNameGridPane() {
        return nameGridPane;
    }

    public Label getLastnameProducerLabel() {
        return lastnameProducerLabel;
    }

    public Label getFirstnameProducerLabel() {
        return firstnameProducerLabel;
    }
}
