package fr.sfc.api.persistence;

import fr.sfc.model.repository.Repository;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class RepositoryFactory {

    private Map<Class<? extends Repository<?>>, Repository<?>> repositories;
    private Set<Repository<?>> setRepositories;

    public RepositoryFactory() {
        repositories = new HashMap<>();
        setRepositories = new HashSet<>();
    }
    public void detectRepositories(String packageRepository) {
        try {
            final Reflections reflections = new Reflections(packageRepository);
            for (var permittedSubclass : reflections.getSubTypesOf(Repository.class)) {
                if (!repositories.containsKey(permittedSubclass)) {
                    repositories.put(
                            (Class<? extends Repository<?>>) permittedSubclass,
                            (Repository<?>) permittedSubclass.getConstructors()[0].newInstance()
                    );
                }
            }
            repositories.forEach((aClass, repository) -> setRepositories.add(repository));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
        }

    }

    public Set<Repository<?>> getAllRepository() {
        return setRepositories;
    }

    public <T> T getRepository(Class<T> rClass) {
        return (T) repositories.get(rClass);
    }



}
