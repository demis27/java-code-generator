package org.demis.darempredou.message;

public class BinaryMessageImpl implements BinaryMessage {

    private int id = 0;

    private byte[] binaryContent = null;

    private long receivedTime = 0;

    public BinaryMessageImpl() {
        // no op
    }

    @Override
    public byte[] getBinaryContent() {
        return binaryContent;
    }

    @Override
    public void setBinaryContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public long getReceivedTime() {
        return receivedTime;
    }

    @Override
    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
    }
}
