package fr.sfc.persistence;

import java.lang.annotation.Annotation;

public final class PersistenceCheck {


    public static boolean isTable(Class<?> tClass) {
        return haveAnnotation(tClass, Table.class);
    }

    public static boolean haveAnnotation(Class<?> tClass, Class<? extends Annotation> annotation) {
        return tClass.isAnnotationPresent(annotation);
    }

    public static boolean throwHaveNotAnnotation(Class<?> tClass, Class<? extends Annotation> annotation) {
        if(!tClass.isAnnotationPresent(annotation))
            throw new PersistenceAnnotationPresentException(annotation.getName() + " not present in " + tClass.getName());
        return true;
    }


}
