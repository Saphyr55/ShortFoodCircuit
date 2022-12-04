package fr.sfc.controller;

import fr.sfc.api.controlling.AutoComponent;
import fr.sfc.api.controlling.Controller;
import fr.sfc.api.persistence.annotation.Inject;
import fr.sfc.component.MainComponent;
import fr.sfc.entity.Company;
import fr.sfc.entity.Order;
import fr.sfc.entity.ProductTour;
import fr.sfc.entity.Vehicle;
import fr.sfc.repository.*;

public class MainController implements Controller {

    @AutoComponent
    private MainComponent component;

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

        Order order = orderRepository.find(1);
        Company company = companyRepository.find(order.getIdCompany());
        ProductTour productTour = productTourRepository.find(order.getIdProductTour());
        Vehicle vehicle = vehicleRepository.find(productTour.getIdVehicle());

        System.out.println(company + "\n" + vehicle + "\n" + productTour + "\n" + order);
        System.out.println(orderRepository.count());
    }


}
