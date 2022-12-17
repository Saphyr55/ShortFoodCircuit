package fr.sfc.common;

import fr.sfc.framework.Resources;
import javafx.scene.image.Image;

import java.io.File;

public final class IconsType {

    public static final Image WARNING_16x16 = create16by16("/icons/warning.png");
    public static final Image CORRECT_16x16 = create16by16("/icons/correct.png");
    public static final Image LOADING_16x16 = create16by16("/icons/loading.png");

    public static Image create16by16(String path) {
        return create(path, 16, 16);
    }

    public static Image create(String path, float w, float h) {
        return create(path, w, h, false, true);
    }

    public static Image create(String path, float w, float h, boolean ratio, boolean smooth) {
        return new Image(Resources.getResource(path).toExternalForm(), w, h, ratio, smooth);
    }

    public static Image create(File file, float w, float h, boolean ratio, boolean smooth) {
        return new Image(file.getPath(), w, h, ratio, smooth);
    }

}
