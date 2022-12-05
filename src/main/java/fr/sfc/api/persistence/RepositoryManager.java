package fr.sfc.api.persistence;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.sfc.api.persistence.annotation.Entity;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class RepositoryManager {

    private final Map<Class<? extends Repository<?>>, Repository<?>> repositories;
    private final List<Repository<?>> setRepositories;
    private String packageRepository;
    private List<String> classesName;

    public RepositoryManager() {
        this.repositories = Maps.newIdentityHashMap();
        this.setRepositories = Lists.newArrayList();
        this.classesName = Lists.newArrayList();
    }

    public void detect() {

        try {
            if (packageRepository != null) {

                final Reflections reflections = new Reflections(packageRepository);

                for (final var subClasses : reflections.getSubTypesOf(Repository.class)) {

                    if (!repositories.containsKey(subClasses))
                        repositories.put((Class<? extends Repository<?>>) subClasses,
                                subClasses.getConstructor().newInstance());
                }
                repositories.forEach((aClass, repository) -> setRepositories.add(repository));
            }

            for (final String className : classesName) {

                final Class<?> aClass = Class.forName(className);

                if (!repositories.containsKey(aClass)) {
                    try {
                        final var newaClass = (Class<? extends Repository<?>>) aClass;
                        repositories.put(newaClass, newaClass.getConstructor().newInstance());
                    } catch (ClassCastException e) {
                        throw new RuntimeException(aClass + " does not inherit from repository", e);
                    }
                }
            }

        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 ClassNotFoundException e) {

            e.printStackTrace();
        } catch (NoSuchMethodException e) {

            throw new RuntimeException("We need an empty constructor", e);
        }

    }

    public List<Repository<?>> getAllRepository() {
        return setRepositories;
    }

    public <T> T getRepository(Class<T> rClass) {
        return (T) repositories.get(rClass);
    }

    public Map<Class<? extends Repository<?>>, Repository<?>> getRepositories() {
        return repositories;
    }

    public List<Repository<?>> getSetRepositories() {
        return setRepositories;
    }

    public String getPackageRepository() {
        return packageRepository;
    }

    public List<String> getClassesName() {
        return classesName;
    }

    public void setPackageRepository(String packageRepository) {
        this.packageRepository = packageRepository;
    }

    public void setClassesName(List<String> classesName) {
        this.classesName = classesName;
    }
}
