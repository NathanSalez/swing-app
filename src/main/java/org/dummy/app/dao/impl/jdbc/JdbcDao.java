package org.dummy.app.dao.impl.jdbc;

import lombok.extern.log4j.Log4j2;
import org.dummy.app.dao.Dao;
import org.dummy.app.exception.DaoException;
import org.dummy.app.flow.Record;
import org.dummy.app.utils.FileUtils;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.SubmissionPublisher;

/**
 * Class implementing DAO interface.
 * The objective is to implement the app's connection to the database.
 * @param <T>
 */
@Log4j2
public abstract class JdbcDao<T> implements Dao<T> {

    protected Connection connector;

    protected SubmissionPublisher<Record<T>> recordPublisher;

    public JdbcDao() throws DaoException
    {
        try {
            SQLConnect(
                FileUtils.getProperties("jdbc-dao.properties")
            );
            log.info("Connection with database established");
            recordPublisher = new SubmissionPublisher<>();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new DaoException(ex);
        }
    }

    /**
     * build a new prepared statement with given arguments.
     * param connection must be initialized. Otherwise, this method returns a null value.
     * Set returnGeneratedKeys to true if you want to create an update prepared statement.
     * @param connection must be initialized
     * @param sql the sql statement.
     * @param returnGeneratedKeys true if you want to create an update prepared statement.
     * @param objects the params table of statement's params.
     * @return the built prepared statement.
     * @throws SQLException
     */
    protected static PreparedStatement buildPreparedStatement(Connection connection, String sql, boolean returnGeneratedKeys, Object... objects ) throws SQLException {
        Objects.requireNonNull(connection);

        PreparedStatement psst = connection.prepareStatement(sql, returnGeneratedKeys ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS);
        for(int i = 1; i <= objects.length; i++)
        {
            psst.setObject(i,objects[i-1]);
        }

        return psst;
    }


    /**
     * Execute a sql script.
     * @param connection must be initialized
     * @param sql the sql statement
     * @return data returned by sql server.
     * @throws SQLException
     */
    protected static void executeScript(Connection connection, String sql) throws SQLException {
        Objects.requireNonNull(connection);

        Statement stmt = connection.createStatement();

        stmt.execute(sql);
    }



    private void SQLConnect(Properties databaseProperties ) throws ClassNotFoundException, SQLException
    {
        Class.forName( databaseProperties.getProperty("driver") );

        this.connector = DriverManager.getConnection(
            databaseProperties.getProperty("url"),
            databaseProperties.getProperty("user"),
            databaseProperties.getProperty("password")
        );

       Optional<String> optionalScriptFilePath = Optional.ofNullable( databaseProperties.getProperty("sql") );
       optionalScriptFilePath.ifPresent(
           filePath -> {
               try {
                   executeScript(connector, FileUtils.getFileContent(filePath));
               } catch (SQLException | IOException e) {
                    log.error("Error while creating database.",e);
               }
           }
       );
    }
}
