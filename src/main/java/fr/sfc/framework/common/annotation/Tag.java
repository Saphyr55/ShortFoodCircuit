package fr.sfc.framework.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.TYPE,
        ElementType.LOCAL_VARIABLE,
        ElementType.PARAMETER,
        ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Tag {

    String tag() default "";

}
