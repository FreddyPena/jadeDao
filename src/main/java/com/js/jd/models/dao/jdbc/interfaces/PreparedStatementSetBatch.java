/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao.jdbc.interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Hector Ventura
 * @author Freddy Peña
 */
public interface PreparedStatementSetBatch {

    /**
     * @param pStatement
     * @param index
     * @throws SQLException
     */
    void setValues(final PreparedStatement pStatement, final int index) throws SQLException;

    /**
     * @return size the batch
     */
    int getSizeBatch();
}
