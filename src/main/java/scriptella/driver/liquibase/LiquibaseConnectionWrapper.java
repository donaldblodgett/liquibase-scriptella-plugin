package scriptella.driver.liquibase;

import java.sql.Connection;
import java.sql.SQLException;

public class LiquibaseConnectionWrapper extends ConnectionWrapper {
  public LiquibaseConnectionWrapper(Connection delegate) {
    super(delegate);
  }

  @Override
  public void close() throws SQLException {
    // Do nothing
  }
}
