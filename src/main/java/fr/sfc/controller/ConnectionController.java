package fr.sfc.controller;

import fr.sfc.container.ConnectionContainer;
import fr.sfc.entity.Admin;
import fr.sfc.entity.Producer;
import fr.sfc.framework.BackendApplication;
import fr.sfc.framework.common.Validator;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.framework.persistence.Repository;
import fr.sfc.repository.AdminRepository;
import fr.sfc.repository.ProducerRepository;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectionController implements Controller {

    @AutoContainer
    private ConnectionContainer container;

    @FXML
    public PasswordField passwordTextField;
    @FXML
    public TextField identifierTextField;
    @FXML
    public Font errorMessage;

    @Inject
    private ContainerManager containerManager;

    @Inject
    private ProducerRepository producerRepository;

    @Inject
    private AdminRepository adminRepository;

    @Override
    public void setup() { }

    @FXML
    public void onPressLogin(){
        getEntity(identifierTextField.getText()).ifPresent(entity -> {

            if (entity instanceof Admin admin) {

                Validator.of(admin).validate(a -> a.getPassword().equals(passwordTextField.getText())).get();
                BackendApplication.getCurrentApplication().getPrimaryStage().hide();
                container.getMainAdminStage().show();

            } else if (entity instanceof Producer producer) {

                Validator.of(producer).validate(p -> p.getPassword().equals(passwordTextField.getText())).get();
                BackendApplication.getCurrentApplication().getPrimaryStage().hide();
                container.getMainProducttourStage().show();
            }
        });
    }

    public Optional<?> getEntity(String id) {

        if (id.isBlank()) return Optional.empty();

        switch ( id.charAt(id.length() - 1) ) {
            case 'a' -> {
                return find(adminRepository, id);
            }
            case 'p' -> {
                return find(producerRepository, id);
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    private <T> Optional<T> find(Repository<T> repository, String id) {
        AtomicReference<Optional<T>> optionalT = new AtomicReference<>(Optional.empty());
        parseIdentifier(id).ifPresent(integerID -> optionalT.set(Optional.ofNullable(repository.find(integerID))));
        return optionalT.get();
    }


    private Optional<Integer> parseIdentifier(String id){

        char[] chars = id.toCharArray();
        if (chars.length == 0) return Optional.empty();

        // Convert a character list to a String without the last element character
        String cStream = IntStream.range(0, chars.length)
                .filter(i -> i != chars.length-1)
                .mapToObj(i -> chars[i])
                .map(String::valueOf)
                .collect(Collectors.joining());
        try {
            return Optional.of(Integer.parseInt(cStream));
        } catch (Exception e){
            return Optional.empty();
        }
    }

}
