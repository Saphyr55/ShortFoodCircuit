package fr.sfc.controller.productTour;

import fr.sfc.framework.controlling.Controller;
import javafx.fxml.FXML;

import java.awt.*;

public class ProductToursFrameController implements Controller {
    @FXML
    private TextField tFName;
    @FXML
    public void EventButtonAddProductToursFinishAction(){

        System.out.println(tFName);
    }
}
