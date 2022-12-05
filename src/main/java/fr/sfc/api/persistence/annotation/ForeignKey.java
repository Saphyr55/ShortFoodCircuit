package fr.sfc.api.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {

    enum Type {
        Id,
        Column
    }
    Type type() default Type.Id;
    Class<?> entity();


}
