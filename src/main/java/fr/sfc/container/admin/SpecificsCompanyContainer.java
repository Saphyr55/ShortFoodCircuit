package fr.sfc.container.admin;

import fr.sfc.framework.controlling.Container;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class SpecificsCompanyContainer extends GridPane implements Container {

    private GridPane companyGridPane;
    private Label titleCompanyLabel;
    private Label SIRETCompanyLabel;
    private Label addressCompanyLabel;
    private Label countProductTour;

    @Override
    public void setup() {

        companyGridPane = new GridPane();

        titleCompanyLabel = new Label("Company");
        titleCompanyLabel.setFont(Font.font("Arial", 32));
        titleCompanyLabel.setPadding(new Insets(15,0,0, 15));

        SIRETCompanyLabel = new Label("SIRET : ");
        SIRETCompanyLabel.setFont(Font.font("Arial", 30));
        SIRETCompanyLabel.setPadding(new Insets(15,0,0, 15));

        addressCompanyLabel = new Label("Address : ");
        addressCompanyLabel.setFont(Font.font("Arial", 30));
        addressCompanyLabel.setPadding(new Insets(15,0,0, 15));

        countProductTour = new Label("Count Product Tour : ");
        countProductTour.setFont(Font.font("Arial", 30));
        countProductTour.setPadding(new Insets(15,0,0, 15));

        companyGridPane.addRow(0, titleCompanyLabel);
        companyGridPane.addRow(1, SIRETCompanyLabel);
        companyGridPane.addRow(2, addressCompanyLabel);
        companyGridPane.addRow(3, countProductTour);

        addRow(0, companyGridPane);

    }

    public GridPane getCompanyGridPane() {
        return companyGridPane;
    }

    public Label getTitleCompanyLabel() {
        return titleCompanyLabel;
    }

    public Label getSIRETCompanyLabel() {
        return SIRETCompanyLabel;
    }

    public Label getAddressCompanyLabel() {
        return addressCompanyLabel;
    }

    public Label getCountProductTour() {
        return countProductTour;
    }
}
