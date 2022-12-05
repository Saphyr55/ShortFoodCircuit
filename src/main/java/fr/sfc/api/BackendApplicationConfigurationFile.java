package fr.sfc.api;

import javafx.fxml.FXMLLoader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

public class BackendApplicationConfigurationFile {

    private final File file;
    private final Yaml yaml;
    private final InputStream inputStream;

    public BackendApplicationConfigurationFile(final String file) throws FileNotFoundException {
        this(new File(file));
    }

    public BackendApplicationConfigurationFile(final File file) throws FileNotFoundException {
        this.file = file;
        this.yaml = new Yaml(new Constructor(BackendApplicationConfigurationYaml.class));
        this.inputStream = new FileInputStream(file);
    }

    public BackendApplicationConfiguration create() throws IOException {

        BackendApplicationConfigurationYaml configurationYaml = yaml.load(inputStream);
        BackendApplicationConfigurationYaml.Config config = configurationYaml.config;

        return BackendApplicationConfiguration.Builder.of()
                .withRoot(FXMLLoader.load(Resources.getResource(config.root)))
                .withEntityClassesName(config.persistence.entities)
                .withEntityPackage(config.persistence.packages.entity)
                .withRepositoriesClassesName(config.persistence.repositories)
                .withRepositoryPackage(config.persistence.packages.repository)
                .withDatabaseManager(
                        config.database.properties,
                        config.database.databases.toArray(new String[0]))
                .withActualDatabase(config.database.actual)
                .build();
    }

}
