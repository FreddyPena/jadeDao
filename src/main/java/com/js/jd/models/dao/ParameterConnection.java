/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao;

import java.io.Serializable;

/**
 *
 * @author Hector Ventura
 * @author Freddy Peña
 */
public final class ParameterConnection implements Serializable {

    private static final long serialVersionUID = 1L;
    private String driver;
    private String user;
    private String url;
    private String password;

    /**
     * @return the database user on whose behalf the connection is being
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the database user on whose behalf the connection is being
     */
    public void setUser(final String user) {
        this.user = user;
    }

    /**
     * @return driver the fully qualified name of the desired class
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the fully qualified name of the desired class
     */
    public void setDriver(final String driver) {
        this.driver = driver;
    }

    /**
     * @return url a database url of the form
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url a database url of the form
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return password the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the user's password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ParameterConnection {" + "driver=" + driver + ", user=" + user + ", url=" + url + "}";
    }
}
