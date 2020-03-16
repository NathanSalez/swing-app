package org.dummy.app.service;

import org.dummy.app.exception.ConnectionException;
import org.dummy.app.exception.DaoException;
import org.dummy.app.dao.DaoFactory;
import org.dummy.app.dao.PersistenceType;
import org.dummy.app.dao.UserDao;
import org.dummy.app.exception.InvalidUserException;
import org.dummy.app.exception.RegisterException;
import org.dummy.app.model.User;
import org.dummy.app.utils.PasswordUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;


public class UserService implements Validator<User>
{
    private UserDao userDao;

    private static final int MAX_LENGTH_STRING = 50;

    public UserService() throws DaoException
    {
        userDao = DaoFactory.getDaoFactory(PersistenceType.JDBC).getUserDao();
    }

    public int connectUser(String login, char[] password) throws DaoException, ConnectionException
    {
        User connectedUser = userDao
                .connect(login, password)
                .orElseThrow( () -> new ConnectionException("Bad login or password."));

        return connectedUser.getId();
    }

    public int registerUser(String login, char[] password, char[] confirmPassword) throws DaoException, IllegalArgumentException
    {
        if( ! Arrays.equals(password, confirmPassword) )
            throw new RegisterException("Fields password and confirm password do not match.");

        User newUser = new User(login,password);
        validate( newUser );
        userDao.create(newUser);

        PasswordUtils.cleanPassword(password,confirmPassword);

        return newUser.getId();
    }

    public Collection<User> getAllUsers() throws DaoException
    {
        return userDao.findAll();
    }

    @Override
    public void validate(User object) throws IllegalArgumentException
    {
        Objects.requireNonNull(object,"User can not be null !");

        String login = object.getLogin();
        if( login == null || login.isEmpty() )
            throw new InvalidUserException("Login is empty.");
        if( login.length() >= MAX_LENGTH_STRING)
            throw new InvalidUserException("Login must have less than " + MAX_LENGTH_STRING + " characters.");

        char[] password = object.getPassword();
        if( password == null || password.length == 0 )
            throw new InvalidUserException("Password is empty.");
        if( password.length >= MAX_LENGTH_STRING)
            throw new InvalidUserException("Password must have less than " + MAX_LENGTH_STRING + " characters.");
    }
}
