package org.demis.codegen.core.generator.configuration;

public class DatabaseConfiguration {

    private String url;

    private String user;

    private String password;

    private String schema;

    private boolean readData;

    private String filter;

    private String h2scripts;

    private String ddl;

    public DatabaseConfiguration() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean isReadData() {
        return readData;
    }

    public void setReadData(boolean readData) {
        this.readData = readData;
    }

    public String getH2scripts() {
        return h2scripts;
    }

    public void setH2scripts(String h2scripts) {
        this.h2scripts = h2scripts;
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }

}
