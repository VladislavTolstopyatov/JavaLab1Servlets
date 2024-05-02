package dao;

import exceptions.DataBaseException;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import exceptions.LoginAlreadyRegisteredException;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    E save(E entity) throws GameWithSuchTitleAlreadyExistsException, LoginAlreadyRegisteredException, DataBaseException;

    E findById(K id) throws DataBaseException;

    List<E> findAll() throws DataBaseException;

    void update(E entity) throws DataBaseException;

    boolean deleteById(K id) throws DataBaseException;

}
