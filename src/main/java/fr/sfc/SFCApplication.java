package fr.sfc;

import com.google.common.reflect.Reflection;
import fr.sfc.api.RuntimeApplication;
import fr.sfc.api.RuntimeApplicationConfiguration;
import fr.sfc.api.component.Component;
import fr.sfc.api.component.ComponentClassLoader;
import fr.sfc.api.component.ComponentFXML;
import fr.sfc.api.component.ComponentFactory;
import fr.sfc.api.persistence.Repository;
import fr.sfc.api.persistence.annotation.Entity;
import fr.sfc.controller.MainController;
import fr.sfc.model.entity.Admin;
import fr.sfc.model.repository.AdminRepository;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.reflections.Reflections;
import org.reflections.scanners.MemberUsageScanner;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.lang.model.element.PackageElement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

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

        ComponentClassLoader componentClassLoader = new ComponentClassLoader("fr.sfc.component");
        ComponentFactory componentFactory = componentClassLoader.createComponentFactory();
        componentFactory.detectComponents();
        System.out.println(componentFactory.getComponents().size());
        componentFactory.getComponents().forEach((aClass, component) -> System.out.println(aClass));

        primaryStage.setOnCloseRequest(event -> configuration.getDatabaseManager().shutdown("sfc"));

        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackage("fr.sfc.component")
                        .filterInputsBy(new FilterBuilder().includePackage("fr.sfc.component"))
                        .addUrls());

        System.out.println(Arrays.toString(reflections.getConfiguration().getUrls().toArray()));
        System.out.println(Arrays.toString(reflections.getSubTypesOf(Component.class).toArray()));

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