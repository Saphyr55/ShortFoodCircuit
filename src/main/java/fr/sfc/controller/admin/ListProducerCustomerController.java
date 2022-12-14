package fr.sfc.controller.admin;

import fr.sfc.container.admin.ListProducerCustomerContainer;
import fr.sfc.container.admin.MainAdminContainer;
import fr.sfc.entity.Customer;
import fr.sfc.entity.Producer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.SimpleAlertUtils;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.framework.item.Tag;
import fr.sfc.repository.CustomerRepository;
import fr.sfc.repository.ProducerRepository;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ListProducerCustomerController implements Controller {

    public enum State {
        Customer,
        Producer
    }

    @AutoContainer
    private ListProducerCustomerContainer container;

    @Inject
    @Tag("container:root.admin")
    private MainAdminContainer mainAdminContainer;
    @Inject
    private ProducerRepository producerRepository;
    @Inject
    private CustomerRepository customerRepository;

    private List<Producer> producers = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private Customer customerSelected;
    private Producer producerSelected;
    private State state = State.Producer;

    @Override
    public void setup() {
        container.getAdderButton().setOnAction(this::onAdderButton);
        container.getDeleteButton().setOnAction(this::onDeleteButton);
        fillListView();
        // Filtre la liste en fonction de l'observation du text field
        container.getSearchTextField().textProperty().addListener(this::filterProducerListView);
        // Switch entre list de client et de producteur
        container.getSwitchProducerCustomer().setOnAction(this::switchProducerCustomerInListEvent);
        // Rempli les données du producteur en cliquant sur un element de la liste
        container.getListView().getSelectionModel().selectedIndexProperty().addListener(this::selectedList);
    }

    private void onAdderButton(ActionEvent event) {
        switch (state) {
            case Producer -> container.getAdderProducerStage().show();
            case Customer -> container.getAdderCustomerStage().show();
        }
    }

    private void onDeleteButton(ActionEvent event) {
        SimpleAlertUtils.createAlertConfirmation()
                .withTitle("Confirmation")
                .withContentText("Voulez-vous vraiment supprimer")
                .withOnOkButton(alert -> {
                    switch (state) {
                        case Producer -> producerRepository.delete(producerSelected);
                        case Customer -> customerRepository.delete(customerSelected);
                    }
                }).buildShowAndWait();
    }

    private boolean createConfirmAlertInsertCustomer() {
        AtomicBoolean quit = new AtomicBoolean(false);

        return quit.get();
    }

    private void filterProducerListView(Observable observable) {

        String filter = container.getSearchTextField().getText();

        if(filter == null || filter.length() == 0) {
            container.getFilteredList().setPredicate(s -> true);
            return;
        }

        container.getFilteredList().setPredicate(s -> s.contains(filter));
    }

    private void selectedList(final ObservableValue<? extends Number> observableV,
                              final Number oldV,
                              final Number newV) {

        // Si on n'a rien sélectionné on quitte la methode
        if (newV.intValue() == -1) return;

        // Clear tout avant de faire la selection
        customerSelected = null;
        producerSelected = null;

        // Changement de producteur et customer a la selection
        switchProducerCustomerSelected(mainAdminContainer, newV.intValue());
    }

    private void switchProducerCustomerSelected(MainAdminContainer container, int index) {
        switch (state) {
            case Producer -> {
                producerSelected = producers.get(index);
                container.getController().setCurrentProducer(producerSelected);
                container.getController().fillDataProducer();
            }
            case Customer -> {
                customerSelected = customers.get(index);
                container.getController().setCurrentCustomer(customerSelected);
                container.getController().fillDataCustomer();
            }
        }
    }

    private void switchProducerCustomerInListEvent(ActionEvent event) {
        switchState();
        fillListView();
    }

    /**
     * Rafraîchie la vue liste et la met à jour
     */
    public void fillListView() {
        container.getObservableList().setAll(getListStringFromDbInFunctionOfState());
        container.getListView().refresh();
    }

    public void switchState() {

        switch (state) {
            case Customer -> {
                state = State.Producer;
                mainAdminContainer.getDetailsPane().getChildren().setAll(mainAdminContainer.getDetailsProducer());
            }
            case Producer -> {
                state = State.Customer;
                mainAdminContainer.getDetailsPane().getChildren().setAll(mainAdminContainer.getDetailsCustomer());
            }
        }
    }

    private Set<String> getListStringFromDbInFunctionOfState() {

        switch (state) {

            // Ajoute tous les producteurs dans la liste vue
            case Producer -> {
                producers = new ArrayList<>(producerRepository.findAll());
                return producers.stream().map(this::formatProducerToString).collect(Collectors.toSet());
            }

            // Ajoute tous les clients dans la liste vue
            case Customer -> {
                customers = new ArrayList<>(customerRepository.findAll());
                return customers.stream().map(this::formatCustomerToString).collect(Collectors.toSet());
            }

            default -> throw new NullPointerException("State is null");
        }
    }

    private String formatCustomerToString(Customer customer) {
        return customer.getName().toUpperCase();
    }

    private String formatProducerToString(Producer producer) {
        return producer.getLastname().toUpperCase() + " " +
                producer.getFirstname() +
                " (SIRET : " + producer.getSIRET() + ")";
    }

}
