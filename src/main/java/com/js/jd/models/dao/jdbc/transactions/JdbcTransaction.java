/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao.jdbc.transactions;

import java.sql.SQLException;

/**
 *
 * @author Hector
 */
public interface JdbcTransaction {

    /**
     *
     * @throws SQLException
     */
    public void run() throws SQLException;
}
