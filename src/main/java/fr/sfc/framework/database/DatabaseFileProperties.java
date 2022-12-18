package fr.sfc.framework.database;

import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

final class DatabaseFileProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManager.class);

    private static final String sectionNameConfig = "config";
    private Map<String, Database.Properties> propertiesMap;
    private Database.Configuration configuration;
    private File dbFile;
    private Ini ini;

    public DatabaseFileProperties(Set<String> dbNames, File dbFile) {
        try {
            this.propertiesMap = new HashMap<>();
            this.dbFile = dbFile;
            this.ini = new Ini(this.dbFile);
            this.configuration = parse(ini.get(sectionNameConfig), Database.Configuration.class);
            dbNames.forEach(s -> this.propertiesMap.put(s, parse(ini.get(s), Database.Properties.class)));
        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }

    /**
     * Convert an ini section to a class
     *
     * @param section section
     * @param tClass  class
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T parse(Ini.Section section, Class<T> tClass) {
        try {
            final List<String> contentSection = new ArrayList<>();
            final List<String> valueSection = new ArrayList<>();

            Arrays.asList(tClass.getDeclaredFields()).forEach(field -> contentSection.add(field.getName()));
            contentSection.forEach(s -> valueSection.add(section.get(s)));

            return (T) tClass.getDeclaredConstructors()[0].newInstance(valueSection.toArray());

        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            LOGGER.error("Impossible to convert an ini section to a class",e);
        }

        return null;
    }

    public Database.Properties getPropertiesByName(String name) {
        return propertiesMap.get(name);
    }


    public Database.Configuration getConfig() {
        return configuration;
    }

    public File getDbFile() {
        return dbFile;
    }

    public Map<String, Database.Properties> getPropertiesMap() {
        return propertiesMap;
    }

    public Database.Configuration getConfiguration() {
        return configuration;
    }

    public Ini getIni() {
        return ini;
    }
}
