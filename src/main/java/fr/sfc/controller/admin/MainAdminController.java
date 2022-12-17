package fr.sfc.controller.admin;

import fr.sfc.container.admin.MainAdminContainer;
import fr.sfc.entity.Company;
import fr.sfc.entity.Customer;
import fr.sfc.entity.Producer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.CompanyRepository;
import fr.sfc.repository.ProductTourRepository;

import static fr.sfc.common.Phones.formatPhoneNumberFR;

public class MainAdminController implements Controller {

    @AutoContainer
    private MainAdminContainer container;

    @Inject
    private CompanyRepository companyRepository;
    @Inject
    private ProductTourRepository productTourRepository;

    private Producer currentProducer;
    private Customer currentCustomer;

    @Override
    public void setup() { }

    /**
     * Rempli les champs de texte avec les données du producteur courant sélectionner
     */
    public void fillDataProducer() {
        companyRepository.findBySIRET(currentProducer.getSIRET()).ifPresent(company -> {
            container.getSpecificsDataProducer().getLastnameProducer().setText(currentProducer.getLastname().toUpperCase());
            container.getSpecificsDataProducer().getFirstnameProducer().setText(currentProducer.getFirstname());
            container.getSpecificsDataProducer().getSIRETCompany().setText(company.getSIRET().toString());
            container.getSpecificsDataProducer().getAddressCompany().setText(company.getAddress());
            container.getSpecificsDataProducer().getPhoneNumber().setText(formatPhoneNumberFR(company.getPhoneNumber()));
            container.getSpecificsDataProducer().getCountProductTour().setText(countProductTourByCompany(company));
        });
    }

    public void fillDataCustomer() {
        container.getSpecificsDataCustomer().getPhoneNumber().setText(formatPhoneNumberFR(currentCustomer.getPhoneNumber()));
        container.getSpecificsDataCustomer().getAddress().setText(currentCustomer.getAddress());
        container.getSpecificsDataCustomer().getName().setText(currentCustomer.getName());
    }

    private String countProductTourByCompany(Company company) {
        return String.valueOf(productTourRepository.countProductTourByCompany(company));
    }



    public Producer getCurrentProducer() {
        return currentProducer;
    }

    public void setCurrentProducer(Producer currentProducer) {
        this.currentProducer = currentProducer;
    }


    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public MainAdminContainer getContainer() {
        return container;
    }
}
