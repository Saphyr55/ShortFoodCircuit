package fr.sfc.controller.admin;

import fr.sfc.container.admin.ListProducerContainer;
import fr.sfc.entity.Producer;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.persistence.annotation.Inject;
import fr.sfc.repository.ProducerRepository;

import java.util.stream.Collectors;

public class ListProducerController implements Controller {

    @AutoContainer
    private ListProducerContainer component;

    @Inject
    private ProducerRepository producerRepository;

    @Override
    public void setup() {

        final var namesProducer = producerRepository
                .findAll().stream()
                .map(this::formatProducerToString)
                .collect(Collectors.toSet());

        component.getProducerListCell().getItems().addAll(namesProducer);

    }

    private String formatProducerToString(Producer producer) {
        return producer.getLastname().toUpperCase() + " " + producer.getFirstname() + " (SIRET : " + producer.getSIRET() + ")";
    }

}
