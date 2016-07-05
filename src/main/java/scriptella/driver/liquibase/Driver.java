package scriptella.driver.liquibase;

import java.sql.Connection;

import scriptella.jdbc.JdbcConnection;
import scriptella.spi.ConnectionParameters;
import scriptella.spi.ScriptellaDriver;

public class Driver implements ScriptellaDriver {
  private static Connection connection;

  public static void setConnection(Connection connection) {
    Driver.connection = connection;
  }

  @Override
  public scriptella.spi.Connection connect(ConnectionParameters connectionParameters) {
    LiquibaseConnectionWrapper wrapper = new LiquibaseConnectionWrapper(connection);
    return new JdbcConnection(wrapper, connectionParameters);
  }
}
