package fr.sfc.controller;

import fr.sfc.container.productTour.MainProductTourContainer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.repository.*;

public class MainProductTourController implements Controller {

    @AutoContainer
    private MainProductTourContainer component;

    @Inject
    private AdminRepository adminRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private CompanyRepository companyRepository;
    @Inject
    private ProductTourRepository productTourRepository;
    @Inject
    private VehicleRepository vehicleRepository;

    @Override
    public void setup() {


    }




}
