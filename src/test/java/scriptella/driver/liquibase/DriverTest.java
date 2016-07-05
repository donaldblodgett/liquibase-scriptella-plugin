package scriptella.driver.liquibase;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import scriptella.jdbc.JdbcConnection;
import scriptella.spi.ConnectionParameters;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Driver.class)
public class DriverTest {
  @Mock
  private Connection connection;
  private Driver driver;
  
  @Before
  public void setup() {
    Driver.setConnection(connection);
    driver = new Driver();
  }
  
  @Test
  public void connect() throws Exception {
    ConnectionParameters connectionParameters = mock(ConnectionParameters.class);
    JdbcConnection jdbcConnection = mock(JdbcConnection.class);

    whenNew(JdbcConnection.class)
      .withParameterTypes(Connection.class, ConnectionParameters.class)
      .withArguments(any(LiquibaseConnectionWrapper.class), eq(connectionParameters))
      .thenReturn(jdbcConnection);
    
    driver.connect(connectionParameters);
    
    verifyNew(JdbcConnection.class)
      .withArguments(any(LiquibaseConnectionWrapper.class), eq(connectionParameters));
  }
}
