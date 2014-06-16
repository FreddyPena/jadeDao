/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao.jdbc.transactions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Hector Ventura
 * @author Freddy Peña
 */
public abstract class AbstractPreparedStatementSet {

    private boolean generateKey;
    private Long keyId;

    /**
     * @param preparedStatement
     * @throws SQLException
     */
    public abstract void putParameters(final PreparedStatement preparedStatement) throws SQLException;

    public boolean isGenerateKey() {
        return generateKey;
    }

    public void setGenerateKey(final boolean generateKey) {
        this.generateKey = generateKey;
    }

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(final Long keyId) {
        this.keyId = keyId;
    }
}
