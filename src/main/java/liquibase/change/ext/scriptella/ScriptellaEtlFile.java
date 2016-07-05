package liquibase.change.ext.scriptella;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.SetupException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.statement.SqlStatement;
import scriptella.driver.liquibase.Driver;
import scriptella.execution.EtlExecutor;
import scriptella.execution.EtlExecutorException;

@DatabaseChange(name = "etlFile", description = "The 'etlFile' tag allows you to specify a Scriptella ETL to be executed with the database connection provided by Liquibase. "
    + "It is useful for complex changes that are not supported through LiquiBase's automated refactoring tags and not possible entirely through sql.\n"
    + "\n" + "The etlFile refactoring finds the file by searching in the following order:\n" + "\n"
    + "The file is searched for in the classpath. This can be manually set and by default the liquibase startup script adds the current directory when run.\n"
    + "The file is searched for using the file attribute as a file name. This allows absolute paths to be used or relative paths to the working directory to be used.\n"
    + "\n"
    + "The ETL file must have a connection defined with a driver of 'liquibase'. This connection can then be used in the ETL script as any JDBC connection.", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class ScriptellaEtlFile extends AbstractChange {
  private String path;
  private String rollbackPath;
  private Boolean relativeToChangelogFile;

  @DatabaseChangeProperty(description = "The file path of the ETL file to load", requiredForDatabase = "all", exampleValue = "my/path/file.etl.xml")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  @DatabaseChangeProperty(description = "The file path of the ETL file to use to rollback the changes made by the ETL file defined by path", exampleValue = "my/path/rollback-file.etl.xml")
  public String getRollbackPath() {
    return rollbackPath;
  }

  public void setRollbackPath(String rollbackPath) {
    this.rollbackPath = rollbackPath;
  }

  @DatabaseChangeProperty(description = "Determines if the ETL files are relative to the changelog file")
  public Boolean isRelativeToChangelogFile() {
    return relativeToChangelogFile;
  }

  public void setRelativeToChangelogFile(Boolean relativeToChangelogFile) {
    this.relativeToChangelogFile = relativeToChangelogFile;
  }

  @Override
  public void finishInitialization() throws SetupException {
    if (path == null) {
      throw new SetupException("<etlFile> - No path specified");
    }
  }

  @Override
  public ValidationErrors validate(Database database) {
    ValidationErrors errors = new ValidationErrors();
    if (path == null) {
      errors.addError("'path' is required");
    } else {
      validPath("path", path, errors);
      if (rollbackPath != null) {
        validPath("rollbackPath", rollbackPath, errors);
      }
    }
    return errors;
  }

  private void validPath(String name, String path, ValidationErrors errors) {
    File file = getFile(path);
    if (!file.exists()) {
      errors.addError(MessageFormat.format("''{0}'' - ''{1}'' does not exist", name, file.getAbsolutePath()));
    } else if (file.isDirectory()) {
      errors.addError(MessageFormat.format("''{0}'' - ''{1}'' is a directory", name, file.getAbsolutePath()));
    }
  }

  private File getFile(String path) {
    if (relativeToChangelogFile != null && relativeToChangelogFile) {
      File changelogFile = new File(getChangeSet().getFilePath());
      return new File(changelogFile.getParentFile(), path);
    }
    return new File(path);
  }

  @Override
  public String getConfirmationMessage() {
    return MessageFormat.format("ETL {0} completed", path);
  }

  @Override
  public boolean generateStatementsVolatile(Database database) {
    return true;
  }

  @Override
  public SqlStatement[] generateStatements(Database database) {
    executeEtl(path, database);
    return new SqlStatement[0];
  }

  private void executeEtl(String script, Database database) {
    try {
      URL url = getFile(path).toURI().toURL();
      JdbcConnection liquibaseConnection = (JdbcConnection) database.getConnection();
      Driver.setConnection(liquibaseConnection.getUnderlyingConnection());
      EtlExecutor executor = EtlExecutor.newExecutor(url);
      executor.execute();
    } catch (MalformedURLException e) {
      throw new UnexpectedLiquibaseException(e);
    } catch (EtlExecutorException e) {
      throw new UnexpectedLiquibaseException(e);
    }
  }

  @Override
  public boolean supportsRollback(Database database) {
    return rollbackPath != null;
  }

  @Override
  protected Change[] createInverses() {
    if (rollbackPath == null) {
      return null;
    }
    ScriptellaEtlFile change = new ScriptellaEtlFile();
    change.path = rollbackPath;
    change.rollbackPath = path;
    change.relativeToChangelogFile = relativeToChangelogFile;
    return new Change[] { change };
  }
}
