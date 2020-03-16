package org.dummy.app.exception;

/**
 *
 * @author Nathan Salez
 */
public class DaoException extends Exception {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

}
