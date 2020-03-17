package org.dummy.app.dao.impl.jdbc;

import lombok.Cleanup;
import org.dummy.app.exception.DaoException;
import org.dummy.app.dao.UserDao;
import org.dummy.app.flow.Record;
import org.dummy.app.flow.State;
import org.dummy.app.flow.UserSubscriber;
import org.dummy.app.model.User;
import org.dummy.app.view.PrivatePanel;
import org.dummy.app.view.model.UserListTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;


public class JdbcUserDao extends JdbcDao<User> implements UserDao, RowMapper<User>
{
    private static JdbcUserDao instance;

    private static final String CONNECTION_USER_REQUEST = "SELECT * FROM USER WHERE login = ? AND password = ?";
    private static final String REGISTER_USER_REQUEST = "INSERT INTO USER(login,password) VALUES(?,?)";
    private static final String FETCH_USERS_REQUEST= "SELECT * FROM USER";

    public static JdbcUserDao getInstance() throws DaoException
    {
        if( instance == null)
            instance = new JdbcUserDao();

        return instance;
    }

    @Override
    public Optional<User> connect(String login, char[] password) throws DaoException {
        User connectedUser = null;
        try {
            @Cleanup PreparedStatement pstmt = buildPreparedStatement(connector,CONNECTION_USER_REQUEST,false,login,password);
            @Cleanup ResultSet rs = pstmt.executeQuery();
            if( rs.next() )
                connectedUser = map(rs);
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return Optional.ofNullable(connectedUser);
    }

    @Override
    public void create(User obj) throws DaoException {
        try {
            @Cleanup PreparedStatement psst = buildPreparedStatement(connector,REGISTER_USER_REQUEST,true,
                obj.getLogin(),
                obj.getPassword());

            int generatedKey = psst.executeUpdate();
            obj.setId(generatedKey);
            recordPublisher.submit( new Record<>(obj, State.CREATE));
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public Optional<User> research(int id) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(User obj) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User obj) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<User> findAll() throws DaoException {
        try {
            LinkedList<User> users = new LinkedList<>();
            @Cleanup Statement stmt = connector.createStatement();
            @Cleanup ResultSet res = stmt.executeQuery(FETCH_USERS_REQUEST);
            while( res.next() )
            {
                users.add( map(res));
            }
            return users;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public User map(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("login")
        );
    }

    private JdbcUserDao() throws DaoException
    {
        super();
        recordPublisher.subscribe( new UserSubscriber(UserListTable.getInstance(findAll())));
    }
}
