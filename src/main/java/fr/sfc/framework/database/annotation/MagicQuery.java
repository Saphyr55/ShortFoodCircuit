package fr.sfc.framework.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MagicQuery {

    String request();

    Class<?>[] tables() default Class.class;

    Class<?>[] ids() default Class.class;

}

