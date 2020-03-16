package org.dummy.app.dao;


import org.dummy.app.exception.DaoException;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface describing the behavior of a Data Access Object (DAO).<br />
 * The objective is to link the application to a database.
 * @author Nathan Salez
 */
public interface Dao<T> {
    /**
     * insert the parameter object in the database
     * @param obj
     * @throws DaoException the object is not inserted or connection with database is corrupted.
     */
    void create(T obj) throws DaoException;

    /**
     * look for the object whose id is in parameter.
     * @param id
     * @return found object.
     * @throws DaoException connection with database is corrupted.
     */
    Optional<T> research(int id) throws DaoException;

    /**
     * update an object whose id is given.
     * @param obj
     * @throws DaoException if update has not been done or connection with database is corrupted.
     */
    void update(T obj) throws DaoException;

    /**
     * delete parameter object from the database.
     * @param obj
     * @throws DaoException if delete has not been done or connection with database is corrupted.
     */
    void delete(T obj) throws DaoException;

    /**
     * get every T objects from the database.
     * @return a T collection
     * @throws DaoException connection with database is corrupted.
     */
    Collection<T> findAll() throws DaoException;
}
