package fr.sfc.framework.item;

import fr.sfc.framework.controlling.Controller;
import fr.sfc.framework.controlling.annotation.AutoController;

import java.util.Arrays;

import static fr.sfc.framework.item.TagManager.DELIMITER;
import static fr.sfc.framework.item.TagManager.getValue;

@Deprecated
public class ItemManager<T> {

    private Object item;
    private final Object instance;
    private final String tags;

    public ItemManager(Object instance, String tags) {
        this.instance = instance;
        this.tags = tags;
    }

    public ItemManager<T> load() {
        Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(AutoController.class))
                .findFirst().ifPresent(field -> {
                    try {
                        field.setAccessible(true);
                        parkourTagPathController(tags, (Controller) field.get(instance));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        return this;
    }

    private void parkourTagPathController(String tags, Controller controller) {
        Arrays.stream(controller.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Item.class))
                .filter(field -> {
                    String[] t = tags.split(DELIMITER);
                    return getValue(field, field.getAnnotation(Tag.class)).equals(t[t.length - 1]);
                }).forEach(field -> {
                    try {
                        field.setAccessible(true);
                        item = field.get(controller);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public <R> R getItem() {
        return (R) item;
    }

}
