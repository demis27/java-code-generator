package org.demis.codegen.core.db.reader;

public class DatabaseConfiguration {

    private String url = null;

    private String user = null;

    private String password = null;

    private String schemaName = null;

    public DatabaseConfiguration() {
        // no op
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DatabaseConfiguration{" +
                "url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", schemaName='" + schemaName + '\'' +
                '}';
    }
}
