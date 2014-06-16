/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao.jdbc.transactions;

import com.js.jd.models.dao.AbstractTemplateDao;
import com.js.jd.models.dao.jdbc.connection.JdbcConnection;
import com.js.jd.models.dao.jdbc.interfaces.PreparedStatementSetBatch;
import com.js.jd.models.dao.jdbc.interfaces.RowMapper;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Hector Ventura
 * @author Freddy Peña
 * @param <T>
 * @param <ID>
 */
public class JdbcStatemant<T, ID extends Serializable> extends AbstractTemplateDao<T, ID> {

    private JdbcConnection jdbcConnection;
    private static final Logger LOGGER = Logger.getLogger(JdbcStatemant.class);

    public JdbcStatemant() {
    }

    public JdbcStatemant(final JdbcConnection connection) {
        this.jdbcConnection = connection;
    }

    public JdbcConnection getJdbcConnection() {
        return jdbcConnection;
    }

    public void setJdbcConnection(final JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    /**
     * start a transaction
     */
    public void biginTransaction() {
        setAutoCommit(false);
    }

    /**
     * finish a transaction
     */
    public void endTransaction() {
        setAutoCommit(true);
    }

    /**
     *
     * @param autoCommit true to start a Transaction
     */
    @Override
    public void setAutoCommit(final boolean autoCommit) {
        try {
            jdbcConnection.getConnection().setAutoCommit(autoCommit);
        } catch (SQLException ex) {
            LOGGER.error("jadesoft-jadeDAO", ex);
        }
    }

    /**
     *
     */
    public void Commit() {
        try {
            jdbcConnection.getConnection().commit();
        } catch (SQLException ex) {
            LOGGER.error("jadesoft-jadeDAO", ex);
        }
    }

    /**
     *
     * @param query
     * @param preparedStatementSet
     * @throws java.sql.SQLException
     */
    public void execute(final String query, final AbstractPreparedStatementSet preparedStatementSet) throws SQLException {

        LOGGER.info(query);

        final PreparedStatement preparedStatement;

        /*
         * Ask if goint put the id to pararmeters set
         */
        if (preparedStatementSet.isGenerateKey()) {
            preparedStatement = jdbcConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } else {
            preparedStatement = jdbcConnection.getConnection().prepareStatement(query);
        }

        preparedStatementSet.putParameters(preparedStatement);
        preparedStatement.execute();

        if (preparedStatementSet.isGenerateKey()) {
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                preparedStatementSet.setKeyId(rs.getLong(1));
            }
        }
        preparedStatement.close();

    }

    public void execute(final String query, final Object... elements) throws SQLException {
        executeGetId(query, false, elements);
    }

    /**
     *
     * @param query
     * @param getId
     * @param elements
     * @return
     * @throws java.sql.SQLException
     */
    public Integer executeGetId(final String query, final boolean getId, final Object... elements) throws SQLException {

        LOGGER.info(query);
        PreparedStatement pStatement;
        Integer id = null;

        /*
         * Ask if goint put the id to pararmeters set
         */
        if (getId) {
            pStatement = jdbcConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } else {
            pStatement = jdbcConnection.getConnection().prepareStatement(query);
        }

        int i = 1;
        for (Object el : elements) {
            pStatement.setObject(i, el);
            i++;
        }

        pStatement.execute();

        /**
         * If quetion for key,
         */
        if (getId) {
            ResultSet rs = pStatement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                id = ((Long) rs.getLong(1)).intValue();
            }
        }
        pStatement.close();

        return id;
    }

    public void executeSafery(final String query, final AbstractPreparedStatementSet preparedStatementSet) {
        try {
            execute(query, preparedStatementSet);
        } catch (SQLException ex) {
            LOGGER.error("jadesoft-jadeJdbc", ex);
        }

    }

    /**
     *
     * @param query
     * @param preparedStatementSetBatch
     * @throws java.sql.SQLException
     */
    public void executeBatch(final String query, final PreparedStatementSetBatch preparedStatementSetBatch) throws SQLException {

        LOGGER.info(query);
        try (PreparedStatement pStatement = jdbcConnection.getConnection().prepareStatement(query)) {
            for (int i = 0; i < preparedStatementSetBatch.getSizeBatch(); i++) {
                preparedStatementSetBatch.setValues(pStatement, i);
                pStatement.addBatch();
            }
            pStatement.executeBatch();
        }
    }

    /**
     *
     * @param query
     * @param preparedStatementSetBatch
     * @throws java.sql.SQLException
     */
    public void executeBatchAsTransaction(final String query, final PreparedStatementSetBatch preparedStatementSetBatch) throws SQLException {

        executeTransaction(new JdbcTransaction() {
            @Override
            public void run() throws SQLException {

                LOGGER.info(query);
                try (PreparedStatement pStatement = jdbcConnection.getConnection().prepareStatement(query)) {
                    for (int i = 0; i < preparedStatementSetBatch.getSizeBatch(); i++) {
                        preparedStatementSetBatch.setValues(pStatement, i);
                        pStatement.addBatch();
                    }
                    pStatement.executeBatch();
                }

            }
        });

    }

    /**
     * Execute a queryForList
     *
     * @param query
     * @param rowMapper
     * @return List of entity of RowMapper
     * @throws java.sql.SQLException
     */
    public List queryForList(final String query, final RowMapper rowMapper) throws SQLException {
        List list = new ArrayList();

        LOGGER.info(query);
        try (ResultSet resultSet = jdbcConnection.getConnection().createStatement().executeQuery(query)) {
            while (resultSet.next()) {
                list.add(rowMapper.getRow(resultSet));
            }
        }

        return list;
    }

    /**
     * Execute a queryForList
     *
     * @param query
     * @param rowMapper
     * @return List of entity of RowMapper
     */
    public List queryForListSafary(final String query, final RowMapper rowMapper) {

        try {
            return queryForList(query, rowMapper);
        } catch (SQLException ex) {
            LOGGER.error("jadesoft-jadeJdbc", ex);
        }

        return new ArrayList();
    }

    /**
     *
     * @param query Execute a Query (Update, Procedure, Delete)
     */
    public void executeSafary(final String query) {
        try {
            LOGGER.info(query);
            jdbcConnection.getConnection().createStatement().execute(query);
        } catch (SQLException ex) {
            LOGGER.error(query);
        }
    }

    /**
     * Execute a queryForList
     *
     * @param query
     * @param rowMapper
     * @return Object
     */
    public Object queryForObject(final String query, final RowMapper rowMapper) {
        Object obj = null;
        try {
            LOGGER.info(query);
            try (ResultSet rs = jdbcConnection.getConnection().createStatement().executeQuery(query)) {
                if (rs.next()) {
                    obj = rowMapper.getRow(rs);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("jadesoft-jadeDAO", ex);
        }
        return obj;
    }

    /**
     *
     * @param query
     * @return Integer of queryForList example SELECT COUNT(*) FROM costumer
     */
    public Integer queryForInt(final String query) {
        Integer obj;

        obj = (Integer) queryForObject(query, new RowMapper() {
            @Override
            public Object getRow(ResultSet rs) throws SQLException {
                Integer obj = rs.getInt(1);
                return obj;
            }
        });
        return obj;
    }

    /**
     *
     * @param transaction
     * @throws java.sql.SQLException
     */
    public void executeTransaction(final JdbcTransaction transaction) throws SQLException {

        try {
            biginTransaction();
            transaction.run();
            endTransaction();

        } catch (SQLException ex) {
            try {

                if (!jdbcConnection.getConnection().getAutoCommit()) {
                    jdbcConnection.getConnection().rollback();
                }

                endTransaction();
                throw ex;
            } catch (SQLException ex1) {
            }

            endTransaction();
            throw ex;
        }

    }

}
