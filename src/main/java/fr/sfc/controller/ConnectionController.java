package fr.sfc.controller;

import fr.sfc.container.ConnectionContainer;
import fr.sfc.entity.Admin;
import fr.sfc.entity.Producer;
import fr.sfc.framework.BackendApplication;
import fr.sfc.framework.controlling.ContainerManager;
import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoContainer;
import fr.sfc.framework.injection.Inject;
import fr.sfc.repository.AdminRepository;
import fr.sfc.repository.ProducerRepository;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectionController implements Controller {

    @FXML
    public PasswordField passwordTextField;
    @FXML
    public TextField idTextField;
    @AutoContainer
    private ConnectionContainer container;

    @Inject
    private ContainerManager containerManager;

    @Inject
    private ProducerRepository producerRepository;

    @Inject
    private AdminRepository adminRepository;

    @FXML
    public void onPressLogin(){
        var entity = getEntity(getDataIdTextField());
        if(entity instanceof Admin admin && admin.getPassword().equals(passwordTextField.getText())){
            BackendApplication.getCurrentApplication().getPrimaryStage().hide();
            container.getMainAdminStage().show();
            System.out.println(passwordTextField.getText());
        } else if (entity instanceof Producer producer && producer.getPassword().equals(passwordTextField.getText()) ){
            BackendApplication.getCurrentApplication().getPrimaryStage().hide();
            container.getMainProducttourStage().show();
        }

    }

    public String getDataIdTextField(){
        return idTextField.getText();
    }

    public int getIdIntegerFromStringId(String id){
        char[] chars = id.toCharArray();
        String cStream = IntStream.range(0, chars.length).filter(i -> i != chars.length-1).mapToObj(i -> chars[i]).map(String::valueOf).collect(Collectors.joining());
        try{
            return Integer.parseInt(cStream);
        }catch (Exception e){
            return -1;
        }
    }

    public <E> E getEntity(String id){
        switch ( id.charAt(id.length() - 1) ){
            case 'a' ->{
                int idValue = getIdIntegerFromStringId(id);
                if (idValue < 0)
                    return null;
                return (E)adminRepository.find(idValue);
            }
            case 'p' -> {
                int idValue = getIdIntegerFromStringId(id);
                if (idValue < 0)
                    return null;
                return (E)producerRepository.find(idValue);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public void setup() {
        var producers = producerRepository.findAll();
    }
}
