package scriptella.driver.liquibase;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ConnectionWrapper implements Connection {
  private Connection deletegate;

  public ConnectionWrapper(Connection delegate) {
    this.deletegate = delegate;
  }
  
  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return deletegate.isWrapperFor(iface);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return deletegate.unwrap(iface);
  }

  @Override
  public void abort(Executor executor) throws SQLException {
    deletegate.abort(executor);
  }

  @Override
  public void clearWarnings() throws SQLException {
    deletegate.clearWarnings();
  }

  @Override
  public void close() throws SQLException {
    deletegate.close();
  }

  @Override
  public void commit() throws SQLException {
    deletegate.commit();
  }

  @Override
  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return deletegate.createArrayOf(typeName, elements);
  }

  @Override
  public Blob createBlob() throws SQLException {
    return deletegate.createBlob();
  }

  @Override
  public Clob createClob() throws SQLException {
    return deletegate.createClob();
  }

  @Override
  public NClob createNClob() throws SQLException {
    return deletegate.createNClob();
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    return deletegate.createSQLXML();
  }

  @Override
  public Statement createStatement() throws SQLException {
    return deletegate.createStatement();
  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
    return deletegate.createStatement(resultSetType, resultSetConcurrency);
  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
      throws SQLException {
    return deletegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return deletegate.createStruct(typeName, attributes);
  }

  @Override
  public boolean getAutoCommit() throws SQLException {
    return deletegate.getAutoCommit();
  }

  @Override
  public String getCatalog() throws SQLException {
    return deletegate.getCatalog();
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    return deletegate.getClientInfo();
  }

  @Override
  public String getClientInfo(String name) throws SQLException {
    return deletegate.getClientInfo(name);
  }

  @Override
  public int getHoldability() throws SQLException {
    return deletegate.getHoldability();
  }

  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    return deletegate.getMetaData();
  }

  @Override
  public int getNetworkTimeout() throws SQLException {
    return deletegate.getNetworkTimeout();
  }

  @Override
  public String getSchema() throws SQLException {
    return deletegate.getSchema();
  }

  @Override
  public int getTransactionIsolation() throws SQLException {
    return deletegate.getTransactionIsolation();
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    return deletegate.getTypeMap();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return deletegate.getWarnings();
  }

  @Override
  public boolean isClosed() throws SQLException {
    return deletegate.isClosed();
  }

  @Override
  public boolean isReadOnly() throws SQLException {
    return deletegate.isReadOnly();
  }

  @Override
  public boolean isValid(int timeout) throws SQLException {
    return deletegate.isValid(timeout);
  }

  @Override
  public String nativeSQL(String sql) throws SQLException {
    return deletegate.nativeSQL(sql);
  }

  @Override
  public CallableStatement prepareCall(String sql) throws SQLException {
    return deletegate.prepareCall(sql);
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
    return deletegate.prepareCall(sql, resultSetType, resultSetConcurrency);
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return deletegate.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public PreparedStatement prepareStatement(String sql) throws SQLException {
    return deletegate.prepareStatement(sql);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    return deletegate.prepareStatement(sql, autoGeneratedKeys);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    return deletegate.prepareStatement(sql, columnIndexes);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    return deletegate.prepareStatement(sql, columnNames);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    return deletegate.prepareStatement(sql, resultSetType, resultSetConcurrency);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return deletegate.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    deletegate.releaseSavepoint(savepoint);
  }

  @Override
  public void rollback() throws SQLException {
    deletegate.rollback();
  }

  @Override
  public void rollback(Savepoint savepoint) throws SQLException {
    deletegate.rollback(savepoint);
  }

  @Override
  public void setAutoCommit(boolean autoCommit) throws SQLException {
    deletegate.setAutoCommit(autoCommit);
  }

  @Override
  public void setCatalog(String catalog) throws SQLException {
    deletegate.setCatalog(catalog);
  }

  @Override
  public void setClientInfo(Properties properties) throws SQLClientInfoException {
    deletegate.setClientInfo(properties);
  }

  @Override
  public void setClientInfo(String name, String value) throws SQLClientInfoException {
    deletegate.setClientInfo(name, value);
  }

  @Override
  public void setHoldability(int holdability) throws SQLException {
    deletegate.setHoldability(holdability);
  }

  @Override
  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
    deletegate.setNetworkTimeout(executor, milliseconds);
  }

  @Override
  public void setReadOnly(boolean readOnly) throws SQLException {
    deletegate.setReadOnly(readOnly);
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {
    return deletegate.setSavepoint();
  }

  @Override
  public Savepoint setSavepoint(String name) throws SQLException {
    return deletegate.setSavepoint(name);
  }

  @Override
  public void setSchema(String schema) throws SQLException {
    deletegate.setSchema(schema);
  }

  @Override
  public void setTransactionIsolation(int level) throws SQLException {
    deletegate.setTransactionIsolation(level);
  }

  @Override
  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    deletegate.setTypeMap(map);
  }
}