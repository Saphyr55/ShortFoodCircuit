package fr.sfc.controller;

import fr.sfc.api.controlling.AutoComponent;
import fr.sfc.api.controlling.Controller;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.component.MainComponent;
import fr.sfc.component.MapComponent;
import fr.sfc.model.repository.AdminRepository;
import javafx.fxml.FXML;

public class MainController implements Controller {


    @AutoComponent
    private MainComponent mainComponent;

    @FXML
    private MapComponent mapComponent;

    @Inject
    private AdminRepository adminRepository;

    @Override
    public void setup() {
        System.out.println(mainComponent.getClass().getName());
    }


}
