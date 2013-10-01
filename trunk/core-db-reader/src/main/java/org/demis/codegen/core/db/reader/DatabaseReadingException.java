package org.demis.codegen.core.db.reader;

public class DatabaseReadingException extends Exception {

    public DatabaseReadingException() {
        super();
    }

    public DatabaseReadingException(String message) {
        super(message);
    }

    public DatabaseReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseReadingException(Throwable cause) {
        super(cause);
    }
}
