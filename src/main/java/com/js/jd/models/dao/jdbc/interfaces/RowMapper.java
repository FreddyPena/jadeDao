/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao.jdbc.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Hector Ventura
 * @author Freddy Peña
 * @param <T>
 */
public interface RowMapper<T> {

    /**
     * A table of data representing a database result set
     *
     * @param resultSet
     * @return the object executed in the result set
     * @throws SQLException
     */
    T getRow(final ResultSet resultSet) throws SQLException;
}
