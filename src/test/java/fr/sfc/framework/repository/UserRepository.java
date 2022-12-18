package fr.sfc.framework.repository;

import fr.sfc.framework.entity.User;
import fr.sfc.framework.persistence.Repository;

import java.util.Set;

public class UserRepository implements Repository<User> {


    @Override
    public Set<User> findAll() {
        return null;
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void insert(User entity) {

    }

    @Override
    public void save(User admin) {

    }
}
