package org.demis.darempredou.message;

public class BinaryMessage {

    private int id = 0;

    private byte[] binaryContent = null;

    private long receivedTime = 0;

    public BinaryMessage() {
        // no op
    }

    public byte[] getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
    }
}
