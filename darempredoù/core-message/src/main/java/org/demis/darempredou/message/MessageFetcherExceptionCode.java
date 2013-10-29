package org.demis.darempredou.message;

    public enum MessageFetcherExceptionCode {

    UNKNOWN_ERROR (900, "unknown error"),
    SERVER_CONNECTION_FAILED(101, "Server connection failed"),
    SERVER_DISCONNECTION_FAILED(102, "Server disconnection failed"),
    BAD_FETCHER_CONFIGURATION(200, "Bad fetcher configuration"),
    MAILBOX_LOGOUT_ERROR(301, "Mailbox logout error"),
    MAILBOX_LOGIN_ERROR(302, "Mailbox login error"),
    NOT_CONNECTED(103, "Not connected to server"),
    MESSAGE_DELETE_ERROR(401, "BinaryMessage delete error");

    private int id = 0;

    private String message = null;

    private MessageFetcherExceptionCode(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }}
