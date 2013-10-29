package org.demis.darempredou.message;

public class MessageFetcherException extends Exception {

    private MessageFetcherExceptionCode code = null;

    public MessageFetcherException(Throwable throwable) {
        super(throwable);
    }

    public MessageFetcherException(Throwable throwable, MessageFetcherExceptionCode code) {
        super(throwable);
        this.code = code;
    }

    public MessageFetcherException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MessageFetcherException(String message, Throwable throwable, MessageFetcherExceptionCode code) {
        super(message, throwable);
        this.code = code;
    }

    public MessageFetcherException(String message) {
        super(message);
    }

    public MessageFetcherException() {
        super();
    }

    public MessageFetcherException(MessageFetcherExceptionCode code) {
        super();
        this.code = code;
    }

    public MessageFetcherException(String message, MessageFetcherExceptionCode code) {
        super(message);
        this.code = code;
    }

    public MessageFetcherExceptionCode getCode() {
        return code;
    }

    public void setCode(MessageFetcherExceptionCode code) {
        this.code = code;
    }
}
