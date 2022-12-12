package fr.sfc.controller.admin;

import fr.sfc.container.admin.MainAdminContainer;
import fr.sfc.entity.Producer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.CompanyRepository;

public class MainAdminController implements Controller {

    @AutoContainer
    private MainAdminContainer container;

    private Producer currentProducer;

    @Inject
    private CompanyRepository companyRepository;

    @Override
    public void setup() { }

    public void fillData() {
        companyRepository.findBySIRET(currentProducer.getSIRET()).ifPresent(company -> {
            container.getDetailsProducer().getLastnameProducerLabel().setText(currentProducer.getLastname().toUpperCase());
            container.getDetailsProducer().getFirstnameProducerLabel().setText(currentProducer.getFirstname());
            container.getDetailsCompany().getSIRETCompanyLabel().setText("SIRET : " + company.getSIRET());
            container.getDetailsCompany().getAddressCompanyLabel().setText("Address : " + company.getAddress());
        });
    }

    public Producer getCurrentProducer() {
        return currentProducer;
    }

    public void setCurrentProducer(Producer currentProducer) {
        this.currentProducer = currentProducer;
    }

    public MainAdminContainer getContainer() {
        return container;
    }
}
