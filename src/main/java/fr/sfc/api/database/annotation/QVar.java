package fr.sfc.api.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface QVar {

    String value();
    Type type() default Type.Column;

    enum Type {
        Column,
        Table,
    }

}
