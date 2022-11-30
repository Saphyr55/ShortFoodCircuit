package fr.sfc;

import fr.sfc.api.RuntimeApplication;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

@ExtendWith(ApplicationExtension.class)
class SFCApplicationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        var app = new SFCApplication();
        app.start(stage);
    }



}