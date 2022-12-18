package fr.sfc.controller;

import fr.sfc.container.productTour.MainProductTourContainer;
import fr.sfc.entity.Company;
import fr.sfc.entity.Producer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.repository.*;

public class MainProductTourController implements Controller {

    @AutoContainer
    private MainProductTourContainer component;

    @Inject
    private ProducerRepository producerRepository;
    @Inject
    private CompanyRepository companyRepository;

    private Producer currentProducer;
    private Company currentCompany;

    @Override
    public void setup() {
        currentProducer = producerRepository.find(3);
        currentCompany = companyRepository.find(currentProducer.getIdCompany());
        System.out.println(currentCompany);
    }

    public Company getCurrentCompany() {
        return currentCompany;
    }

    public Producer getCurrentProducer() {
        return currentProducer;
    }
}
