package fr.sfc.controller;

import fr.sfc.api.controller.Controller;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.model.repository.AdminRepository;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {

    @Inject
    private AdminRepository adminRepository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(adminRepository.count());
    }


}
