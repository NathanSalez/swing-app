package org.dummy.app.exception;

public class ConnectionException extends IllegalArgumentException
{
    public ConnectionException(String message) {
        super(message);
    }
}
