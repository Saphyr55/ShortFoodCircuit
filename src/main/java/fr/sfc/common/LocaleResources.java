package fr.sfc.common;

import com.google.gson.Gson;
import fr.sfc.framework.Resources;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ListResourceBundle;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public final class LocaleResources extends ListResourceBundle {

    @Override
    protected Object[][] getContents() { return contents; }

    static Object[][] contents;

    static Map<?, ?> contentsMap;

    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle(LocaleResources.class.getName());
    }

    public static String get(String id) {
        return getBundle().getString(id);
    }

    static {
        try (Reader reader = Files.newBufferedReader(Resources.getFileResource("/local/en_GB.json").toPath())) {
            Gson gson = new Gson();
            AtomicInteger atomicInteger = new AtomicInteger();
            contents = new Object[contentsMap.size()][2];
            contentsMap = gson.fromJson(reader, Map.class);
            contentsMap.forEach((o, o2) -> {
                Object[] entry = new Object[2];
                entry[0] = o;
                entry[1] = o2;
                contents[atomicInteger.get()] = entry;
                atomicInteger.getAndIncrement();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
