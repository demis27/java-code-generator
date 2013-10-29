package org.demis.darempredou.message;

public interface BinaryMessage {

    public byte[] getBinaryContent();

    public int getId();

    public long getReceivedTime();

    public void setBinaryContent(byte[] binaryContent);

    public void setId(int id);

    public void setReceivedTime(long receivedTime);
}
