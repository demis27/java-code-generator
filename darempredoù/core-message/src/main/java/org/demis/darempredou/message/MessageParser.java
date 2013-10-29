package org.demis.darempredou.message;

public interface MessageParser {

    public Message parse(MessageParserConfiguration configuration, BinaryMessage binaryMessage);
}
