/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao.jdbc.connection;

import com.js.jd.models.dao.ParameterConnection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Hector
 */
public class JdbcManager {

    private static String defaultConnectionName = "default";
    private static Map<String, JdbcConnection> connections = new HashMap<>();

    public static String getDefaultConnectionName() {
        return defaultConnectionName;
    }

    public static void setDefaultConnectionName(String defaultConnectionName) {
        JdbcManager.defaultConnectionName = defaultConnectionName;
    }

    public static Map<String, JdbcConnection> getConnections() {
        return connections;
    }

    public static void setConnections(Map<String, JdbcConnection> connections) {
        JdbcManager.connections = connections;
    }

    public static void put(String connectionName, JdbcConnection connection) {
        connections.put(connectionName, connection);
    }

    public static void put(String connectionName, ParameterConnection parameter) {
        connections.put(connectionName, new JdbcConnection(parameter));
    }

    public static void put(JdbcConnection connection) {
        connections.put(defaultConnectionName, connection);
    }

    public static void put(ParameterConnection parameter) {
        connections.put(defaultConnectionName, new JdbcConnection(parameter));
    }

    public static JdbcConnection get(String connectionName) {
        return connections.get(connectionName);
    }

    public static JdbcConnection get() {
        return connections.get(defaultConnectionName);
    }
}
