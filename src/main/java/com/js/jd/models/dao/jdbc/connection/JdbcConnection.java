/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao.jdbc.connection;

import com.js.jd.models.dao.ParameterConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Hector Ventura
 * @author Freddy Peña
 */
public class JdbcConnection {

    private static final Logger LOGGER = Logger.getLogger(JdbcConnection.class.getName());//NOPMD

    private transient ParameterConnection pConnection;
    private transient Connection connection;

    public JdbcConnection() {
        super();
    }

    /**
     *
     * @param pConnection
     */
    public JdbcConnection(final ParameterConnection pConnection) {
        this.pConnection = pConnection;
    }

    public ParameterConnection getParameterConnection() {
        return pConnection;
    }

    public void setParameterConnection(final ParameterConnection pConnection) {
        this.pConnection = pConnection;
    }

    /**
     * Create the connection with the database
     *
     * @return true if connection is create else false
     * @throws Exception
     */
    private boolean createConnection() throws Exception {//NOPMD

        if (pConnection == null) {
            throw new Exception("No hay Parametros establecidos");
        }
        try {

            Class.forName(pConnection.getDriver());
            LOGGER.info("Driver cargado Exitosamente");

            connection = DriverManager.getConnection(pConnection.getUrl(), pConnection.getUser(), pConnection.getPassword());

            LOGGER.info("Conexion Establecida Exitosamente");

            return true;//NOPMD

        } catch (SQLException ex) {
            LOGGER.error("jadesoft-jadeDAO", ex);//NOPMD
        }
        return false;
    }

    /**
     * Get the connection with the database
     *
     * @return Connection
     */
    public Connection getConnection() {
        if (!isConected()) {
            reconnet();
        }
        return connection;
    }

    /**
     * Connects if the connection is null
     */
    private void reconnet() {
        try {
            createConnection();
        } catch (Exception ex) {
            LOGGER.warn("jadesoft-jadeDAO", ex);
        }
    }

    /**
     * Checks for Connection
     *
     * @return true if is Connected or false is no connected
     */
    private boolean isConected() {
        if (connection == null) {
            return false;//NOPMD
        }
        try {
            connection.createStatement().executeQuery("SELECT 1");
            return true;//NOPMD
        } catch (SQLException ex) {
            LOGGER.warn("jadesoft-jadeDAO", ex);
        }
        return false;
    }

    /**
     * Close connection
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            LOGGER.error("jadesoft-jadeJdbc", ex);
        }
    }
}
