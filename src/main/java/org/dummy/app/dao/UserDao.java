package org.dummy.app.dao;

import org.dummy.app.exception.DaoException;
import org.dummy.app.model.User;

import java.util.Optional;

public interface UserDao extends Dao<User>
{
    /**
     * Return the user's information whose login and password are given.
     * @param login
     * @param password
     * @return null if no user found or the built user.
     * @throws DaoException connection with database is corrupted.
     */
    Optional<User> connect(String login, char[] password) throws DaoException;
}
