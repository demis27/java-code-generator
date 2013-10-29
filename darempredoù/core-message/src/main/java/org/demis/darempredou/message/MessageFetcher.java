package org.demis.darempredou.message;

public interface MessageFetcher {

    public BinaryMessage[] fetch(MessageFetcherConfiguration configuration) throws MessageFetcherException;

    public void delete(MessageFetcherConfiguration configuration, int id) throws MessageFetcherException;

}
