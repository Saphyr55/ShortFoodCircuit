package fr.sfc.controller;

import fr.sfc.api.controlling.AutoComponent;
import fr.sfc.api.controlling.Controller;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.common.Password;
import fr.sfc.component.MainComponent;
import fr.sfc.model.entity.Admin;
import fr.sfc.model.repository.AdminRepository;

public class MainController implements Controller {

    @AutoComponent
    private MainComponent component;

    @Inject
    private AdminRepository adminRepository;

    @Override
    public void setup() {
        Admin admin = new Admin("CvGHf3h38TLuJrRS3jau8PDhanyb5JuagekqZ6L9");
        System.out.println(adminRepository.findAll());
    }


}
