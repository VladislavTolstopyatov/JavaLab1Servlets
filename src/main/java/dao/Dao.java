package dao;

import exceptions.GameWithSuchTitleAlreadyExistsException;
import exceptions.LoginAlreadyRegisteredException;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    E save(E entity) throws GameWithSuchTitleAlreadyExistsException, LoginAlreadyRegisteredException;

    E findById(K id);

    List<E> findAll();

    void update(E entity);

    boolean deleteById(K id);

}
