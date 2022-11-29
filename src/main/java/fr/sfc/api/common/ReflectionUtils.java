package fr.sfc.api.common;

import com.google.common.collect.Sets;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ReflectionUtils {

    public static Set<Field> getFieldsAnnotated(Class<?> aClass, Class<? extends Annotation> annotation) {
        return Sets.newHashSet(Arrays.stream(aClass.getFields()).filter(field -> field.getAnnotation(annotation) != null).iterator());
    }

}
