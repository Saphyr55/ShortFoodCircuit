package fr.sfc.api.core.process;

import fr.sfc.api.persistence.annotation.Column;
import fr.sfc.api.persistence.annotation.Entity;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@SupportedAnnotationTypes("fr.sfc.api.persistence.annotation.Entity")
public class EntityProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(Entity.class).stream()
                .filter(element -> element.getKind() == ElementKind.CLASS)
                .forEach(this::generate);
        return true;

    }

    public void generate(Element element) {
        try {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error", element);
            final String className = element.getSimpleName().toString();
            final String packageName = element.getEnclosingElement().toString();
            final String packageClassName = className + "." + className;
            final String newClassName = className + "EntityWrapper";
            final String newPackageName = "fr.sfc.model.entity";
            final String newPackageClassName = newPackageName + "." + newClassName;
            final Class<?> actualClass = Class.forName(packageClassName);
            final Map<String, Class<?>> nameColumn = new HashMap<>();

            for (Field field : actualClass.getFields()) {
                Column aColumn = field.getDeclaredAnnotation(Column.class);
                if (aColumn != null) nameColumn.put(aColumn.name(), field.getType());
                else nameColumn.put(field.getName(), field.getType());
            }

            final StringBuilder bufferInit = new StringBuilder();
            final AtomicInteger atomicInteger = new AtomicInteger();
            nameColumn.forEach((s, aClass) -> {
                bufferInit.append(aClass.getName())
                        .append(" i").append(atomicInteger.get())
                        .append(" = rs.findColumn(").append(s)
                        .append("\");").append("\n");
                atomicInteger.getAndIncrement();
            });

            final StringBuilder bufferSearch = new StringBuilder();
            atomicInteger.set(0);
            nameColumn.forEach((s, aClass) -> {
                bufferInit.append(aClass.getName())
                        .append(" n").append(atomicInteger.get())
                        .append(" = rs.getObject(i")
                        .append(atomicInteger.getAndIncrement()).append(", ")
                        .append(aClass.getName()).append(".class")
                        .append("\");").append("\n");
                atomicInteger.getAndIncrement();
            });

            String code = """
                    /**
                    package $package;
                    
                    import java.lang.String;
                    import $packageClass;
                    import java.sql.ResultSet;
                    
                    public class $class extends EntityWrapper {
                        
                        @Override
                        public wrap(ResultSet rs) {
                            $init
                            
                            while (rs.next()) {
                                 $search
                            }
                           
                        }
                        
                    }
                    */
                    """;
            JavaFileObject javaFileObject = processingEnv.getFiler().createClassFile(packageClassName, element);
            try (PrintWriter writer = new PrintWriter(javaFileObject.openWriter())) {
                writer.println(code
                        .replace("$package", newPackageName)
                        .replace("$packageClass", packageClassName)
                        .replace("$class", newClassName)
                        .replace("$entity", className)
                        .replace("$init", bufferInit.toString())
                        .replace("$search", bufferSearch.toString())
                );
            } catch (IOException exception ) {
                exception.printStackTrace();
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }


    }

}
