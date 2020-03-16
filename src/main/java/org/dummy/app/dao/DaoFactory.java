package org.dummy.app.dao;

import org.dummy.app.dao.impl.jdbc.JdbcFactory;
import org.dummy.app.exception.DaoException;

public abstract class DaoFactory
{

    public static DaoFactory getDaoFactory(PersistenceType type) throws DaoException
    {
        switch(type)
        {
            case JDBC :
                return new JdbcFactory();

            case JPA :
                throw new UnsupportedOperationException("JPA factory not implemented.");

            default:
                throw new DaoException("Persistence type unknown.");
        }
    }


    public abstract UserDao getUserDao() throws DaoException;

}
