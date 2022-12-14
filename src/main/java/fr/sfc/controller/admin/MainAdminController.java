package fr.sfc.controller.admin;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import fr.sfc.container.admin.MainAdminContainer;
import fr.sfc.entity.Company;
import fr.sfc.entity.Customer;
import fr.sfc.entity.Producer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.CompanyRepository;
import fr.sfc.repository.ProductTourRepository;

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
    public void setup() {
    }

    /**
     * Rempli les champs de texte avec les données du producteur courant sélectionner
     */
    public void fillDataProducer() {
        companyRepository.findBySIRET(currentProducer.getSIRET()).ifPresent(company -> {
            container.getSpecificsDataProducer().getLastnameProducer().setText(currentProducer.getLastname().toUpperCase());
            container.getSpecificsDataProducer().getFirstnameProducer().setText(currentProducer.getFirstname());
            container.getSpecificsDataProducer().getSIRETCompany().setText(company.getSIRET());
            container.getSpecificsDataProducer().getAddressCompany().setText(company.getAddress());
            container.getSpecificsDataProducer().getPhoneNumber().setText(formatPhoneNumber(company.getPhoneNumber()));
            container.getSpecificsDataProducer().getCountProductTour().setText(countProductTourByCompany(company));
        });
    }

    public void fillDataCustomer() {
        container.getSpecificsDataCustomer().getPhoneNumber().setText(formatPhoneNumber(currentCustomer.getPhoneNumber()));
        container.getSpecificsDataCustomer().getAddress().setText(currentCustomer.getAddress());
        container.getSpecificsDataCustomer().getName().setText(currentCustomer.getName());
    }


    private String countProductTourByCompany(Company company) {
        return String.valueOf(productTourRepository.countProductTourByCompany(company));
    }

    private String formatPhoneNumber(String phone) {
        try {
            Phonenumber.PhoneNumber phoneNumber = PhoneNumberUtil.getInstance()
                    .parse(phone, "FR");
            return  PhoneNumberUtil.getInstance().format(phoneNumber,
                    PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (NumberParseException e) {
            throw new RuntimeException(e);
        }
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
