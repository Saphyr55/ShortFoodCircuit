package fr.sfc;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.framework.junit5.ApplicationTest.launch;

@ExtendWith(ApplicationExtension.class)
class SFCApplicationTest {

    SFCApplication app;

    Button button;

    @BeforeAll
    static void setup() throws Exception {
        launch(SFCApplication.class);
    }

    @Start
    public void start(Stage stage) throws Exception {
        app = new SFCApplication();
        app.start(stage);
        button = new Button("clicked");
    }

    @Test
    void buttonWork() {
        Assertions.assertThat(button).hasText("clicked!");
    }


}