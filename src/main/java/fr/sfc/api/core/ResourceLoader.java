package fr.sfc.api.core;

import java.net.URL;

public final class ResourceLoader {

    public URL getResourceClassPath(Class<?> aClass, String filename) {
        return aClass.getResource(filename);
    }

}
