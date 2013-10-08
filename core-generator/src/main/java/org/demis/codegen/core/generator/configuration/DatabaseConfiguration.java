package org.demis.codegen.core.generator.configuration;

import org.demis.codegen.core.generator.configuration.filter.DatabaseFilter;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConfiguration {

    private String url;

    private String user;

    private String password;

    private String schema;

    private boolean readData;

    private String h2scripts;

    private String ddl;

    private List<DatabaseFilter> filters = new ArrayList<>();

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

    public void addFilter(DatabaseFilter filter) {
        filters.add(filter);
    }

    public List<DatabaseFilter> getFilters() {
        return filters;
    }


    @Override
    public String toString() {
        return "DatabaseConfiguration{" +
                "url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", schema='" + schema + '\'' +
                ", readData=" + readData +
                ", h2scripts='" + h2scripts + '\'' +
                ", ddl='" + ddl + '\'' +
                '}';
    }
}
