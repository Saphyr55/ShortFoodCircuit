package fr.sfc.api.persistence;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


public final class RepositoryFactory {

    private final Map<Class<? extends Repository<?>>, Repository<?>> repositories;
    private final Set<Repository<?>> setRepositories;

    public RepositoryFactory() {
        repositories = new HashMap<>();
        setRepositories = new HashSet<>();
    }

    public void detectRepositories(String packageRepository) {
        try {
            final Reflections reflections = new Reflections(packageRepository, Scanners.values());
            System.out.println(Arrays.toString(reflections.getConfiguration().getUrls().toArray()));

            for (final var subClasses : reflections.getSubTypesOf(Repository.class)) {

                if (!repositories.containsKey(subClasses)) {
                    repositories.put((Class<? extends Repository<?>>) subClasses,
                            subClasses.getConstructor().newInstance());
                }
            }
            repositories.forEach((aClass, repository) -> setRepositories.add(repository));

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("We need an empty constructor", e);
        }

    }

    public Set<Repository<?>> getAllRepository() {
        return setRepositories;
    }

    public <T> T getRepository(Class<T> rClass) {
        return (T) repositories.get(rClass);
    }



}
