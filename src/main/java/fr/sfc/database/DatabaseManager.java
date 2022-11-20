package fr.sfc.database;

import java.io.File;
import java.sql.SQLException;

public final class DatabaseManager {

    public static final String DATABASE_PROPERTIES_FILE_PATH = "db.ini";
    public static final DBFileProperties dbFileProperties = new DBFileProperties(new File(DATABASE_PROPERTIES_FILE_PATH));
    public static final String testDBName = "test";
    public static final String sfcDBName = "sfc";

    public static final Database SFC = new Database(sfcDBName, dbFileProperties.getPropertiesSFC());
    public static final Database TEST = new Database(testDBName, dbFileProperties.getPropertiesTest());

    public static void init() {
        try {
            Class.forName(dbFileProperties.getConfig().getDriver());
            TEST.init();
            SFC.init();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
