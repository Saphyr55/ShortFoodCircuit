package fr.sfc.framework.database.annotation;

import fr.sfc.framework.persistence.DefaultEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MagicQuery {

    String request();

    Class<?>[] tables() default DefaultEntity.class;

    Class<?>[] ids() default DefaultEntity.class;

}

