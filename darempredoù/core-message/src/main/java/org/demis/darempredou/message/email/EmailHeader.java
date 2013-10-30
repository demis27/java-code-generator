package org.demis.darempredou.message.email;

public class EmailHeader {

    private String name;

    private String binaryValue;

    private String value;

    public EmailHeader(String name, String binaryValue) {
        this.name = name;
        this.binaryValue = binaryValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBinaryValue() {
        return binaryValue;
    }

    public void setBinaryValue(String binaryValue) {
        this.binaryValue = binaryValue;
    }

    public void Binary(String value) {
        if (this.binaryValue != null) {
            this.binaryValue += value;
        }
        else {
            this.binaryValue = value;
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EmailHeader{" +
                "name='" + name + '\'' +
                ", binaryValue='" + binaryValue + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
