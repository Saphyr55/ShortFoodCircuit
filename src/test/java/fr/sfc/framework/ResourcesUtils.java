package fr.sfc.framework;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ResourcesUtils {

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

    private static File getResourceFolder() {
        Path path = Paths.get("src/test/resources");
        System.out.println(path.toFile().getAbsoluteFile());
        return path.toFile().getAbsoluteFile();
    }

}
