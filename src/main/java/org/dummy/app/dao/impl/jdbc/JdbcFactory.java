package org.dummy.app.dao.impl.jdbc;

import org.dummy.app.exception.DaoException;
import org.dummy.app.dao.DaoFactory;
import org.dummy.app.dao.UserDao;

public class JdbcFactory extends DaoFactory
{
    @Override
    public UserDao getUserDao() throws DaoException
    {
        return JdbcUserDao.getInstance();
    }
}
