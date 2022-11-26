package fr.sfc.controller;

import fr.sfc.api.controller.Controller;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.model.repository.AdminRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.beans.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller {

    @Inject
    private AdminRepository adminRepository;

    @FXML
    private Button button;

    public void show(ActionEvent event) {
        System.out.println(adminRepository.count());
    }

}
