package scriptella.driver.liquibase;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LiquibaseConnectionWrapperTest {
  @Mock
  private Connection connection;
  private LiquibaseConnectionWrapper wrapper;

  @Before
  public void setup() {
    wrapper = new LiquibaseConnectionWrapper(connection);
  }

  @Test
  public void close() throws SQLException {
    wrapper.close();
    verify(connection, times(0)).close();
  }
}
