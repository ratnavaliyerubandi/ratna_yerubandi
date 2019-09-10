package com.noesisinformatica.test;

public class DatabaseConnectionInfo {
    private String driverClass;
    private String url;
    private String username;
    private String password;

    public DatabaseConnectionInfo() {
      super();
    }

    public String getDriverClass() {
      return this.driverClass;
    }

    public void setDriverClass(final String driverClass) {
      this.driverClass = driverClass;
    }

    public String getUrl() {
      return this.url;
    }

    public void setUrl(final String url) {
      this.url = url;
    }

    public String getUsername() {
      return this.username;
    }

    public void setUsername(final String username) {
      this.username = username;
    }

    public String getPassword() {
      return this.password;
    }

    public void setPassword(final String password) {
      this.password = password;
    }
  }