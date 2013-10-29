package org.demis.darempredou.message.email;

public class EmailRecipient {

    private String email;

    private String type;

    private String name;

    public EmailRecipient() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
