package fr.sfc.framework.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {

    enum Type {
        Id,
        Unique
    }

    Type type() default Type.Id;
    Class<?> entity();


}
