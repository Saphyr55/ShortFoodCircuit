package fr.sfc.framework.persistence;

import fr.sfc.framework.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryManagerTest {

    private RepositoryManager repositoryManager;

    @BeforeEach
    void setUp() {
        repositoryManager = new RepositoryManager();
        repositoryManager.setClassesName(List.of(UserRepository.class.getName()));
        repositoryManager.detect();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void detect() {
        var repositoryManager = new RepositoryManager();
        repositoryManager.setClassesName(List.of(UserRepository.class.getName()));
        assertDoesNotThrow(repositoryManager::detect);
    }

    @Test
    void getRepository() {
        assertNotNull(repositoryManager.getRepository(UserRepository.class));
    }

    @Test
    void getRepositories() {
        assertSame(
                repositoryManager.getRepository(UserRepository.class),
                repositoryManager.getClassesMapRepositories().get(UserRepository.class)
        );
    }

    @Test
    void getPackageRepository() {
        var repositoryManager = new RepositoryManager();
        repositoryManager.setPackageRepository("fr.sfc.framework");
        assertEquals("fr.sfc.framework", repositoryManager.getPackageRepository());
    }

    @Test
    void getClassesName() {
        var list = List.of(UserRepository.class.getName());
        var repositoryManager = new RepositoryManager();
        repositoryManager.setClassesName(list);
        assertSame(list, repositoryManager.getClassesName());
    }

    @Test
    void setClassesName() {

    }

}