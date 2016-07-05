package scriptella.driver.liquibase;

import static com.donaldblodgett.scriptella.utils.TestUtils.randomInt;
import static com.donaldblodgett.scriptella.utils.TestUtils.randomString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionWrapperTest {
  @Mock
  private Connection connection;
  private ConnectionWrapper wrapper;

  @Before
  public void setup() {
    wrapper = new ConnectionWrapper(connection);
  }

  @Test
  public void abort() throws SQLException {
    Executor executor = mock(Executor.class);
    wrapper.abort(executor);
    verify(connection).abort(executor);
  }

  @Test
  public void clearWarnings() throws SQLException {
    wrapper.clearWarnings();
    verify(connection).clearWarnings();
  }

  @Test
  public void close() throws SQLException {
    wrapper.close();
    verify(connection).close();
  }

  @Test
  public void commit() throws SQLException {
    wrapper.commit();
    verify(connection).commit();
  }

  @Test
  public void createArrayOf() throws SQLException {
    String typeName = randomString();
    Object[] elements = new Object[] {};
    wrapper.createArrayOf(typeName, elements);
    verify(connection).createArrayOf(typeName, elements);
  }

  @Test
  public void createBlob() throws SQLException {
    wrapper.createBlob();
    verify(connection).createBlob();
  }

  @Test
  public void createClob() throws SQLException {
    wrapper.createClob();
    verify(connection).createClob();
  }

  @Test
  public void createNClob() throws SQLException {
    wrapper.createNClob();
    verify(connection).createNClob();
  }

  @Test
  public void createSQLXML() throws SQLException {
    wrapper.createSQLXML();
    verify(connection).createSQLXML();
  }

  @Test
  public void createStatement() throws SQLException {
    wrapper.createStatement();
    verify(connection).createStatement();
  }

  @Test
  public void createStatement_2params() throws SQLException {
    Integer resultSetType = randomInt();
    Integer resultSetConcurrency = randomInt();
    wrapper.createStatement(resultSetType, resultSetConcurrency);
    verify(connection).createStatement(resultSetType, resultSetConcurrency);
  }

  @Test
  public void createStatement_3params() throws SQLException {
    Integer resultSetType = randomInt();
    Integer resultSetConcurrency = randomInt();
    Integer resultSetHoldability = randomInt();
    wrapper.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    verify(connection).createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Test
  public void createStruct() throws SQLException {
    String typeName = randomString();
    Object[] attributes = new Object[] {};
    wrapper.createStruct(typeName, attributes);
    verify(connection).createStruct(typeName, attributes);
  }

  @Test
  public void getAutoCommit() throws SQLException {
    wrapper.getAutoCommit();
    verify(connection).getAutoCommit();
  }

  @Test
  public void getCatalog() throws SQLException {
    wrapper.getCatalog();
    verify(connection).getCatalog();
  }

  @Test
  public void getClientInfo() throws SQLException {
    wrapper.getClientInfo();
    verify(connection).getClientInfo();
  }

  @Test
  public void getClientInfo_1param() throws SQLException {
    String name = randomString();
    wrapper.getClientInfo(name);
    verify(connection).getClientInfo(name);
  }

  @Test
  public void getHoldability() throws SQLException {
    wrapper.getHoldability();
    verify(connection).getHoldability();
  }

  @Test
  public void getMetaData() throws SQLException {
    wrapper.getMetaData();
    verify(connection).getMetaData();
  }

  @Test
  public void getNetworkTimeout() throws SQLException {
    wrapper.getNetworkTimeout();
    verify(connection).getNetworkTimeout();
  }

  @Test
  public void getSchema() throws SQLException {
    wrapper.getSchema();
    verify(connection).getSchema();
  }

  @Test
  public void getTransactionIsolation() throws SQLException {
    wrapper.getTransactionIsolation();
    verify(connection).getTransactionIsolation();
  }

  @Test
  public void getTypeMap() throws SQLException {
    wrapper.getTypeMap();
    verify(connection).getTypeMap();
  }

  @Test
  public void getWarnings() throws SQLException {
    wrapper.getWarnings();
    verify(connection).getWarnings();
  }

  @Test
  public void isClosed() throws SQLException {
    wrapper.isClosed();
    verify(connection).isClosed();
  }

  @Test
  public void isReadOnly() throws SQLException {
    wrapper.isReadOnly();
    verify(connection).isReadOnly();
  }

  @Test
  public void isValid() throws SQLException {
    int timeout = randomInt();
    wrapper.isValid(timeout);
    verify(connection).isValid(timeout);
  }

  @Test
  public void isWrapperFor() throws SQLException {
    Class iface = Class.class;
    wrapper.isWrapperFor(iface);
    verify(connection).isWrapperFor(iface);
  }

  @Test
  public void nativeSQL() throws SQLException {
    String sql = randomString();
    wrapper.nativeSQL(sql);
    verify(connection).nativeSQL(sql);
  }

  @Test
  public void prepareCall_1param() throws SQLException {
    String sql = randomString();
    wrapper.prepareCall(sql);
    verify(connection).prepareCall(sql);
  }

  @Test
  public void prepareCall_3params() throws SQLException {
    String sql = randomString();
    int resultSetType = randomInt();
    int resultSetConcurrency = randomInt();
    wrapper.prepareCall(sql, resultSetType, resultSetConcurrency);
    verify(connection).prepareCall(sql, resultSetType, resultSetConcurrency);
  }

  @Test
  public void prepareCall_4params() throws SQLException {
    String sql = randomString();
    int resultSetType = randomInt();
    int resultSetConcurrency = randomInt();
    int resultSetHoldability = randomInt();
    wrapper.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    verify(connection).prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Test
  public void prepareStatement_1param() throws SQLException {
    String sql = randomString();
    wrapper.prepareStatement(sql);
    verify(connection).prepareStatement(sql);
  }

  @Test
  public void prepareStatement_autoGeneratedKeys() throws SQLException {
    String sql = randomString();
    int autoGeneratedKeys = randomInt();
    wrapper.prepareStatement(sql, autoGeneratedKeys);
    verify(connection).prepareStatement(sql, autoGeneratedKeys);
  }

  @Test
  public void prepareStatement_columnIndexes() throws SQLException {
    String sql = randomString();
    int[] columnIndexes = new int[] {};
    wrapper.prepareStatement(sql, columnIndexes);
    verify(connection).prepareStatement(sql, columnIndexes);
  }

  @Test
  public void prepareStatement_columnNames() throws SQLException {
    String sql = randomString();
    String[] columnNames = new String[] {};
    wrapper.prepareStatement(sql, columnNames);
    verify(connection).prepareStatement(sql, columnNames);
  }

  @Test
  public void prepareStatement_3params() throws SQLException {
    String sql = randomString();
    int resultSetType = randomInt();
    int resultSetConcurrency = randomInt();
    wrapper.prepareStatement(sql, resultSetType, resultSetConcurrency);
    verify(connection).prepareStatement(sql, resultSetType, resultSetConcurrency);
  }

  @Test
  public void prepareStatement_4params() throws SQLException {
    String sql = randomString();
    int resultSetType = randomInt();
    int resultSetConcurrency = randomInt();
    int resultSetHoldability = randomInt();
    wrapper.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    verify(connection).prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Test
  public void releaseSavepoint() throws SQLException {
    Savepoint savepoint = mock(Savepoint.class);
    wrapper.releaseSavepoint(savepoint);
    verify(connection).releaseSavepoint(savepoint);
  }

  @Test
  public void rollback() throws SQLException {
    wrapper.rollback();
    verify(connection).rollback();
  }

  @Test
  public void rollback_1param() throws SQLException {
    Savepoint savepoint = mock(Savepoint.class);
    wrapper.rollback(savepoint);
    verify(connection).rollback(savepoint);
  }

  @Test
  public void setAutoCommit() throws SQLException {
    boolean autoCommit = true;
    wrapper.setAutoCommit(autoCommit);
    verify(connection).setAutoCommit(autoCommit);
  }

  @Test
  public void setCatalog() throws SQLException {
    String catalog = randomString();
    wrapper.setCatalog(catalog);
    verify(connection).setCatalog(catalog);
  }

  @Test
  public void setClientInfo() throws SQLException {
    Properties properties = mock(Properties.class);
    wrapper.setClientInfo(properties);
    verify(connection).setClientInfo(properties);
  }

  @Test
  public void setClientInfo_2params() throws SQLException {
    String name = randomString();
    String value = randomString();
    wrapper.setClientInfo(name, value);
    verify(connection).setClientInfo(name, value);
  }

  @Test
  public void setHoldability() throws SQLException {
    int holdability = randomInt();
    wrapper.setHoldability(holdability);
    verify(connection).setHoldability(holdability);
  }

  @Test
  public void setNetworkTimeout() throws SQLException {
    Executor executor = mock(Executor.class);
    int milliseconds = randomInt();
    wrapper.setNetworkTimeout(executor, milliseconds);
    verify(connection).setNetworkTimeout(executor, milliseconds);
  }

  @Test
  public void setReadOnly() throws SQLException {
    boolean readOnly = true;
    wrapper.setReadOnly(readOnly);
    verify(connection).setReadOnly(readOnly);
  }

  @Test
  public void setSavepoint() throws SQLException {
    wrapper.setSavepoint();
    verify(connection).setSavepoint();
  }

  @Test
  public void setSavepoint_1param() throws SQLException {
    String name = randomString();
    wrapper.setSavepoint(name);
    verify(connection).setSavepoint(name);
  }

  @Test
  public void setSchema() throws SQLException {
    String schema = randomString();
    wrapper.setSchema(schema);
    verify(connection).setSchema(schema);
  }

  @Test
  public void setTransactionIsolation() throws SQLException {
    int level = randomInt();
    wrapper.setTransactionIsolation(level);
    verify(connection).setTransactionIsolation(level);
  }

  @Test
  public void setTypeMap() throws SQLException {
    Map map = mock(Map.class);
    wrapper.setTypeMap(map);
    verify(connection).setTypeMap(map);
  }

  @Test
  public void unwrap() throws SQLException {
    Class iface = Class.class;
    wrapper.unwrap(iface);
    verify(connection).unwrap(iface);
  }
}
