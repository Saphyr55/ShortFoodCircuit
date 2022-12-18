package fr.sfc.framework.item;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,
        ElementType.TYPE,
        ElementType.PARAMETER,
        ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Item {
}
