package fr.sfc.framework.persistence;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class RepositoryManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryManager.class);

    private final Map<Class<? extends Repository<?>>, Repository<?>> repositories;
    private final List<Repository<?>> setRepositories;
    private String packageRepository;
    private List<String> classesName;

    public RepositoryManager() {
        this.repositories = new HashMap<>();
        this.setRepositories =new ArrayList<>();
        this.classesName = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public void detect() {

        try {
            if (packageRepository != null) {

                final Reflections reflections = new Reflections(packageRepository);

                for (final var subClasses : reflections.getSubTypesOf(Repository.class)) {

                    if (!repositories.containsKey(subClasses)) {
                        repositories.put((Class<? extends Repository<?>>) subClasses,
                                subClasses.getConstructor().newInstance());

                        LOGGER.info("Repository {} has been loaded", subClasses);
                    }
                }
                repositories.forEach((aClass, repository) -> setRepositories.add(repository));
            }

            for (final String className : classesName) {

                final Class<?> aClass = Class.forName(className);

                if (!repositories.containsKey(aClass)) {
                    try {
                        final var newaClass = (Class<? extends Repository<?>>) aClass;
                        repositories.put(newaClass, newaClass.getConstructor().newInstance());

                        LOGGER.info("Repository {} has been loaded", newaClass);

                    } catch (ClassCastException e) {
                        LOGGER.trace(aClass + " does not inherit from repository", e);
                        throw new RuntimeException(e);
                    }
                }
            }

        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 ClassNotFoundException e) {

            LOGGER.error("Impossible to detect repository", e);
        } catch (NoSuchMethodException e) {

            throw new RuntimeException("We need an empty constructor", e);
        }

    }

    public List<Repository<?>> getAllRepository() {
        return setRepositories;
    }

    @SuppressWarnings("unchecked")
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
