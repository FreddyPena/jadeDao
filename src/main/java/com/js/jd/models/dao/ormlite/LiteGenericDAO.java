/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao.ormlite;//NOPMD

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.ObjectFactory;
import com.js.jd.models.dao.AbstractTemplateDao;
import com.js.jd.models.dao.ormlite.connection.LiteConnection;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fred Peña
 * @param <T>
 * @param <ID>
 */
public class LiteGenericDAO<T, ID extends Serializable> extends AbstractTemplateDao<T, ID> implements Dao<T, ID> { //NOPMD

    private final transient LiteConnection liteConnection;

    public LiteGenericDAO(final LiteConnection Connection) {
        super();
        this.liteConnection =  Connection;
    }

    protected Dao<T, ID> getDao() {
        try {
            return DaoManager.createDao(this.liteConnection.getConnectionSource(), getEntityClass());//NOPMD
        } catch (SQLException ex) {
            Logger.getLogger(LiteGenericDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T queryForId(final ID id) throws SQLException {//NOPMD
        return getDao().queryForId(id);
    }

    @Override
    public T queryForFirst(final PreparedQuery<T> preparedQuery) throws SQLException {
        return getDao().queryForFirst(preparedQuery);
    }

    @Override
    public List<T> queryForAll() throws SQLException {
        return getDao().queryForAll();
    }

    @Override
    public List<T> queryForEq(final String fieldName, final Object value) throws SQLException {
        return getDao().queryForEq(fieldName, value);
    }

    @Override
    public List<T> queryForMatching(final T matchObj) throws SQLException {
        return getDao().queryForMatching(matchObj);
    }

    @Override
    public List<T> queryForMatchingArgs(final T matchObj) throws SQLException {
        return getDao().queryForMatchingArgs(matchObj);
    }

    @Override
    public List<T> queryForFieldValues(final Map<String, Object> fieldValues) throws SQLException {
        return getDao().queryForFieldValues(fieldValues);
    }

    @Override
    public List<T> queryForFieldValuesArgs(final Map<String, Object> fieldValues) throws SQLException {
        return getDao().queryForFieldValuesArgs(fieldValues);
    }

    @Override
    public T queryForSameId(final T data) throws SQLException {
        return getDao().queryForSameId(data);
    }

    @Override
    public QueryBuilder<T, ID> queryBuilder() {
        return getDao().queryBuilder();
    }

    @Override
    public UpdateBuilder<T, ID> updateBuilder() {
        return getDao().updateBuilder();
    }

    @Override
    public DeleteBuilder<T, ID> deleteBuilder() {
        return getDao().deleteBuilder();
    }

    @Override
    public List<T> query(final PreparedQuery<T> preparedQuery) throws SQLException {
        return getDao().query(preparedQuery);
    }

    @Override
    public int create(final T data) throws SQLException {
        return getDao().create(data);
    }

    @Override
    public T createIfNotExists(final T data) throws SQLException {
        return getDao().createIfNotExists(data);
    }

    @Override
    public CreateOrUpdateStatus createOrUpdate(final T data) throws SQLException {
        return getDao().createOrUpdate(data);
    }

    @Override
    public int update(final T data) throws SQLException {
        return getDao().update(data);
    }

    @Override
    public int updateId(final T data, final ID newId) throws SQLException {
        return getDao().updateId(data, newId);
    }

    @Override
    public int update(final PreparedUpdate<T> preparedUpdate) throws SQLException {
        return getDao().update(preparedUpdate);
    }

    @Override
    public int refresh(final T data) throws SQLException {
        return getDao().refresh(data);
    }

    @Override
    public int delete(final T data) throws SQLException {
        return getDao().delete(data);
    }

    @Override
    public int deleteById(final ID id) throws SQLException {//NOPMD
        return getDao().deleteById(id);
    }

    @Override
    public int delete(final Collection<T> datas) throws SQLException {
        return getDao().delete(datas);
    }

    @Override
    public int deleteIds(final Collection<ID> ids) throws SQLException {
        return getDao().deleteIds(ids);
    }

    @Override
    public int delete(final PreparedDelete<T> preparedDelete) throws SQLException {
        return getDao().delete(preparedDelete);
    }

    @Override
    public CloseableIterator<T> iterator() {
        return getDao().iterator();
    }

    @Override
    public CloseableIterator<T> iterator(final int resultFlags) {
        return getDao().iterator(resultFlags);
    }

    @Override
    public CloseableIterator<T> iterator(final PreparedQuery<T> preparedQuery) throws SQLException {
        return getDao().iterator(preparedQuery);
    }

    @Override
    public CloseableIterator<T> iterator(final PreparedQuery<T> preparedQuery, final int resultFlags) throws SQLException {
        return getDao().iterator(preparedQuery, resultFlags);
    }

    @Override
    public CloseableWrappedIterable<T> getWrappedIterable() {
        return getDao().getWrappedIterable();
    }

    @Override
    public CloseableWrappedIterable<T> getWrappedIterable(final PreparedQuery<T> preparedQuery) {
        return getDao().getWrappedIterable(preparedQuery);
    }

    @Override
    public void closeLastIterator() throws SQLException {
        getDao().closeLastIterator();
    }

    @Override
    public GenericRawResults<String[]> queryRaw(final String query, final String... arguments) throws SQLException {
        return getDao().queryRaw(query, arguments);
    }

    @Override
    public <UO> GenericRawResults<UO> queryRaw(final String query, final RawRowMapper<UO> mapper, final String... arguments) throws SQLException {
        return getDao().queryRaw(query, mapper, arguments);
    }

    @Override
    public <UO> GenericRawResults<UO> queryRaw(final String query, final DataType[] columnTypes, final RawRowObjectMapper<UO> mapper, final String... arguments) throws SQLException {
        return getDao().queryRaw(query, columnTypes, mapper, arguments);
    }

    @Override
    public GenericRawResults<Object[]> queryRaw(final String query, final DataType[] columnTypes, final String... arguments) throws SQLException {
        return getDao().queryRaw(query, columnTypes, arguments);
    }

    @Override
    public long queryRawValue(final String query, final String... arguments) throws SQLException {
        return getDao().queryRawValue(query, arguments);
    }

    @Override
    public int executeRaw(final String statement, final String... arguments) throws SQLException {
        return getDao().executeRaw(statement, arguments);
    }

    @Override
    public int executeRawNoArgs(final String statement) throws SQLException {
        return getDao().executeRawNoArgs(statement);
    }

    @Override
    public int updateRaw(final String statement, final String... arguments) throws SQLException {
        return getDao().updateRaw(statement, arguments);
    }

    @Override
    public <CT> CT callBatchTasks(final Callable<CT> callable) throws Exception {//NOPMD
        return getDao().callBatchTasks(callable);
    }

    @Override
    public String objectToString(final T data) {
        return getDao().objectToString(data);
    }

    @Override
    public boolean objectsEqual(final T data1, final T data2) throws SQLException {
        return getDao().objectsEqual(data1, data2);
    }

    @Override
    public ID extractId(final T data) throws SQLException {
        return getDao().extractId(data);
    }

    @Override
    public Class<T> getDataClass() {
        return getDao().getDataClass();
    }

    @Override
    public FieldType findForeignFieldType(final Class<?> clazz) {
        return getDao().findForeignFieldType(clazz);
    }

    @Override
    public boolean isUpdatable() {
        return getDao().isUpdatable();
    }

    @Override
    public boolean isTableExists() throws SQLException {
        return getDao().isTableExists();
    }

    @Override
    public long countOf() throws SQLException {
        return getDao().countOf();
    }

    @Override
    public long countOf(final PreparedQuery<T> preparedQuery) throws SQLException {
        return getDao().countOf(preparedQuery);
    }

    @Override
    public void assignEmptyForeignCollection(final T parent, final String fieldName) throws SQLException {
        getDao().assignEmptyForeignCollection(parent, fieldName);
    }

    @Override
    public <FT> ForeignCollection<FT> getEmptyForeignCollection(final String fieldName) throws SQLException {
        return getDao().getEmptyForeignCollection(fieldName);
    }

    @Override
    public void setObjectCache(final boolean enabled) throws SQLException {
        getDao().setObjectCache(enabled);
    }

    @Override
    public void setObjectCache(final ObjectCache objectCache) throws SQLException {
        getDao().setObjectCache(objectCache);
    }

    @Override
    public ObjectCache getObjectCache() {
        return getDao().getObjectCache();
    }

    @Override
    public void clearObjectCache() {
        getDao().clearObjectCache();
    }

    @Override
    public T mapSelectStarRow(final DatabaseResults results) throws SQLException {
        return getDao().mapSelectStarRow(results);
    }

    @Override
    public GenericRowMapper<T> getSelectStarRowMapper() throws SQLException {
        return getDao().getSelectStarRowMapper();
    }

    @Override
    public RawRowMapper<T> getRawRowMapper() {
        return getDao().getRawRowMapper();
    }

    @Override
    public boolean idExists(final ID id) throws SQLException {//NOPMD
        return getDao().idExists(id);
    }

    @Override
    public DatabaseConnection startThreadConnection() throws SQLException {
        return getDao().startThreadConnection();
    }

    @Override
    public void endThreadConnection(final DatabaseConnection connection) throws SQLException {
        getDao().endThreadConnection(connection);
    }

    @Override
    @Deprecated
    public void setAutoCommit(final boolean autoCommit) throws SQLException {
        getDao().setAutoCommit(autoCommit);
    }

    @Override
    public void setAutoCommit(final DatabaseConnection connection, final boolean autoCommit) throws SQLException {
        getDao().setAutoCommit(connection, autoCommit);
    }

    @Override
    @Deprecated
    public boolean isAutoCommit() throws SQLException {
        return getDao().isAutoCommit();
    }

    @Override
    public boolean isAutoCommit(final DatabaseConnection connection) throws SQLException {
        return getDao().isAutoCommit(connection);
    }

    @Override
    public void commit(final DatabaseConnection connection) throws SQLException {
        getDao().commit(connection);
    }

    @Override
    public void rollBack(final DatabaseConnection connection) throws SQLException {
        getDao().rollBack(connection);
    }

    @Override
    public ConnectionSource getConnectionSource() {
        return getDao().getConnectionSource();
    }

    @Override
    public void setObjectFactory(final ObjectFactory<T> objectFactory) {
        getDao().setObjectFactory(objectFactory);
    }

    @Override
    public CloseableIterator<T> closeableIterator() {
        return getDao().closeableIterator();
    }

}
