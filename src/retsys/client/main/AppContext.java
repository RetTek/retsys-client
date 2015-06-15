/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.main;

import java.util.Properties;

/**
 *
 * @author ranju
 */
public class AppContext {

    private static AppContext context = new AppContext();
    private String username;
    private String password;
    private Properties appProperties;

    private AppContext() {

    }

    public static AppContext getInstance() {
        if (context == null) {
            context = new AppContext();
        }

        return context;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getProperties() {
        return this.appProperties;
    }

    public void setProperties(Properties props) {
        this.appProperties = props;
    }

    public String getHost() {
        String host = "localhost";
        if (this.appProperties != null) {
            host = this.appProperties.getProperty("host");
        }

        return host;
    }

    public String getPort() {
        String port = "8080";
        if (this.appProperties != null) {
            port = this.appProperties.getProperty("port");
        }

        return port;
    }
}
