package fr.sfc.api.persistence;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


public final class RepositoryManager {

    private final Map<Class<? extends Repository<?>>, Repository<?>> repositories;
    private final List<Repository<?>> setRepositories;
    private final String packageRepository;

    public RepositoryManager(final String packageRepository) {

        this.repositories = Maps.newIdentityHashMap();
        this.setRepositories = Lists.newArrayList();
        this.packageRepository = packageRepository;
    }

    public void detect() {

        try {
            final Reflections reflections = new Reflections(packageRepository);

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

    public List<Repository<?>> getAllRepository() {
        return setRepositories;
    }

    public <T> T getRepository(Class<T> rClass) {
        return (T) repositories.get(rClass);
    }


}
