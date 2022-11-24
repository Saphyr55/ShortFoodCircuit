package fr.sfc;

import fr.sfc.api.RuntimeApplication;
import fr.sfc.api.RuntimeApplicationConfiguration;
import fr.sfc.api.database.DatabaseManager;
import fr.sfc.model.entity.Admin;
import fr.sfc.model.repository.AdminRepository;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public final class SFCApplication extends Application {

    public static URL index;

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent parent = new FXMLLoader(SFCApplication.class.getResource("default.fxml")).load();

        RuntimeApplicationConfiguration configuration = RuntimeApplicationConfiguration.Builder.of()
                .widthTitle("Short Food Circuit")
                .withWidth(880)
                .withHeight(620)
                .withDatabaseFileConfig(new File("db.ini"))
                .withDatabasesName("sfc", "test")
                .withConnectDatabase("sfc")
                .build();

        configuration.configure(
                "sfc",
                "fr.sfc.model.entity",
                "fr.sfc.model.repository"
        );

        RuntimeApplication application = configuration.createApplication(primaryStage, parent);
        application.show();

        AdminRepository adminRepository = configuration
                .getRepositoryFactory()
                .getRepository(AdminRepository.class);

        Admin admin = adminRepository.find(1);
        System.out.println(admin);
        
        primaryStage.setOnCloseRequest(event -> configuration.getDatabaseManager().shutdown("sfc"));
    }

    public static void main(String[] args) {
        try {
            index = SFCApplication.class.getResource("index.html");
            Application.launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}