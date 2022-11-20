package fr.sfc.database;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class DBFileProperties {

    private Database.Properties propertiesSFC;
    private Database.Properties propertiesTest;
    private Database.Configuration configuration;
    private File dbFile;
    private Ini ini;

    public DBFileProperties(File dbFile) {
        try {
            this.dbFile = dbFile;
            this.ini = new Ini(this.dbFile);

            this.configuration = parse(ini.get("config"), Database.Configuration.class);
            this.propertiesSFC = parse(ini.get(DatabaseManager.sfcDBName), Database.Properties.class);
            this.propertiesTest = parse(ini.get(DatabaseManager.testDBName), Database.Properties.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert a section to a class
     *
     * @param section section
     * @param tClass class
     * @return T
     */
    public static <T> T parse(Ini.Section section, Class<T> tClass) {
        try {
            final List<String> contentSection = new ArrayList<>();
            final List<String> valueSection = new ArrayList<>();

            Arrays.asList(tClass.getDeclaredFields()).forEach(field -> contentSection.add(field.getName()));
            contentSection.forEach(s -> valueSection.add(section.get(s)));

            return (T) tClass.getDeclaredConstructors()[0].newInstance(valueSection.toArray());

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Database.Properties getPropertiesSFC() {
        return propertiesSFC;
    }

    public Database.Properties getPropertiesTest() {
        return propertiesTest;
    }

    public Database.Configuration getConfig() {
        return configuration;
    }

    public File getDbFile() {
        return dbFile;
    }
}
