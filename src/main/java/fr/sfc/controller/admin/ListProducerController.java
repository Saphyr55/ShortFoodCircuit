package fr.sfc.controller.admin;

import fr.sfc.container.admin.ListProducerContainer;
import fr.sfc.container.admin.MainAdminContainer;
import fr.sfc.entity.Producer;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.ProducerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListProducerController implements Controller {

    @AutoContainer
    private ListProducerContainer container;

    @Inject
    private ProducerRepository producerRepository;

    @Inject
    private ContainerManager containerManager;

    private List<Producer> producers;
    private Producer producerSelected;

    @Override
    public void setup() {

        // Ajoute tous les producteurs la liste vue
        producers = new ArrayList<>(producerRepository.findAll());
        final var namesProducer = producers.stream().map(this::formatProducerToString).collect(Collectors.toSet());
        container.getProducerListCell().getItems().addAll(namesProducer);

        // Rempli les données du producteur en cliquant sur un element de la liste
        container.getProducerListCell().getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            producerSelected = producers.get(newValue.intValue());
            MainAdminContainer mainAdminContainer = containerManager.getContainer("root");
            mainAdminContainer.getController().setCurrentProducer(producerSelected);
            mainAdminContainer.getController().fillData();
        });
    }


    private String formatProducerToString(Producer producer) {
        return producer.getLastname().toUpperCase() + " " + producer.getFirstname() + " (SIRET : " + producer.getSIRET() + ")";
    }

}
