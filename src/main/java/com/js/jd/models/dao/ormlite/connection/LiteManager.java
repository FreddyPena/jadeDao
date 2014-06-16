/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao.ormlite.connection;

import com.js.jd.models.dao.ParameterConnection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fred Pena
 */
public class LiteManager {

    private static String defaultConnectionName = "default";
    private static Map<String, LiteConnection> connections = new HashMap<>();

    public static String getDefaultConnectionName() {
        return defaultConnectionName;
    }

    public static void setDefaultConnectionName(String defaultConnectionName) {
        LiteManager.defaultConnectionName = defaultConnectionName;
    }

    public static Map<String, LiteConnection> getConnections() {
        return connections;
    }

    public static void setConnections(Map<String, LiteConnection> connections) {
        LiteManager.connections = connections;
    }

    public static void put(String connectionName, LiteConnection connection) {
        connections.put(connectionName, connection);
    }

    public static void put(String connectionName, ParameterConnection parameter) {
        connections.put(connectionName, new LiteConnection(parameter));
    }

    public static void put(LiteConnection connection) {
        connections.put(defaultConnectionName, connection);
    }

    public static void put(ParameterConnection parameter) {
        connections.put(defaultConnectionName, new LiteConnection(parameter));
    }

    public static LiteConnection get(String connectionName) {
        return connections.get(connectionName);
    }

    public static LiteConnection get() {
        return connections.get(defaultConnectionName);
    }
}
