package fr.sfc.framework.controlling;

import java.lang.reflect.Field;

public class ComponentProperties {

    private Field field;
    private Component self;
    private String tag;

    public ComponentProperties(Field field,
                               Component self,
                               String tag) {

        this.field = field;
        this.self = self;
        this.tag = tag;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Component getSelf() {
        return self;
    }

    public void setSelf(Component self) {
        this.self = self;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
