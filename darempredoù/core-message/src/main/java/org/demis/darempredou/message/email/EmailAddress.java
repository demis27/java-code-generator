package org.demis.darempredou.message.email;

public class EmailAddress {

    private String email;

    private String name;

    public EmailAddress() {

    }

    public EmailAddress(String email) {
        this.email = email;
    }

    public EmailAddress(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
