package fr.sfc.controller;

import fr.sfc.api.controlling.AutoComponent;
import fr.sfc.api.controlling.Controller;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.component.MainComponent;
import fr.sfc.model.repository.AdminRepository;

public class MainController implements Controller {


    @AutoComponent
    private MainComponent component;

    @Inject
    private AdminRepository adminRepository;

    @Override
    public void setup() {
        System.out.println(adminRepository.findAll());
    }


}
