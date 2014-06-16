/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao.ormlite.connection;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.js.jd.models.dao.ParameterConnection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Fred Pena
 */
public class LiteConnection {

    private final static Logger LOGGER = Logger.getLogger(LiteConnection.class);
    private transient ConnectionSource connectionSource;
    private transient ParameterConnection parameter;

    public LiteConnection() {
        super();
    }

    /**
     *
     * @param parameter
     */
    public LiteConnection(final ParameterConnection parameter) {
        this.parameter = parameter;
    }

    public ParameterConnection getParameterConnection() {
        return parameter;
    }

    public void setParameterConnection(final ParameterConnection parameter) {
        this.parameter = parameter;
    }

    private boolean createConnection() throws Exception {//NOPMD
        if (parameter == null) {
            throw new Exception("No hay Parametros establecidos");
        }
        try {
            connectionSource = new JdbcConnectionSource(parameter.getUrl(), parameter.getUser(), parameter.getPassword());

            LOGGER.info("Conexion Establecida Exitosamente");

            return true;
        } catch (SQLException ex) {
            LOGGER.error("jadesoft-jadeDAO", ex);//NOPMD
        }
        return false;
    }

    public ConnectionSource getConnectionSource() {

        if (!isConected()) {
            reconnet();
        }
        return connectionSource;

    }

    private boolean isConected() {
        try {
            connectionSource.close();
        } catch (NullPointerException e) {
            return false;
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }

    private void reconnet() {
        try {
            createConnection();
        } catch (Exception ex) {
            LOGGER.warn("jadesoft-jadeDAO", ex);
        }
    }

    /**
     * Close connection
     */
    public void close() {
        try {
            connectionSource.close();
        } catch (SQLException ex) {
            LOGGER.error("jadesoft-jadeJdbc", ex);
        }
    }
}
