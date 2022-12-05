package fr.sfc.api;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public final class Resources {

    public static final File FOLDER_RESOURCE = getResourceFolder();

    public static URL getResource(String path) {
        try {
            return getFileResource(path).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getFileResource(String path) {
        return new File(FOLDER_RESOURCE + path);
    }

    private static File getResourceFolder()  {
        return new File(ClassLoader.getSystemResource("fxml").getPath()).getParentFile();
    }

}
