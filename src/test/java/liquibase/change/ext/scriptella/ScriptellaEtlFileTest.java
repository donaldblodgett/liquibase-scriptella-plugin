package liquibase.change.ext.scriptella;
import static com.donaldblodgett.scriptella.utils.TestUtils.randomString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.File;
import java.net.URI;
import java.sql.Connection;
import java.text.MessageFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.statement.SqlStatement;
import scriptella.execution.EtlExecutor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ScriptellaEtlFile.class, EtlExecutor.class})
public class ScriptellaEtlFileTest {
  @Mock
  private Database database;

  private ScriptellaEtlFile etlFile;

  @Before
  public void setup() {
    etlFile = new ScriptellaEtlFile();
  }
  
  @Test
  public void getPath_afterSetPath() {
    String path = randomString();
    etlFile.setPath(path);
    assertEquals(path, etlFile.getPath());
  }
  
  @Test
  public void getRollbackPath_afterSetRollbackPath() {
    String path = randomString();
    etlFile.setRollbackPath(path);
    assertEquals(path, etlFile.getRollbackPath());
  }
  
  @Test
  public void isRelativeToChangelogFile_afterSetRelativeToChangelogFile_true() {
    etlFile.setRelativeToChangelogFile(true);
    assertEquals(true, etlFile.isRelativeToChangelogFile());
  }
  
  @Test
  public void isRelativeToChangelogFile_afterSetRelativeToChangelogFile_false() {
    etlFile.setRelativeToChangelogFile(false);
    assertEquals(false, etlFile.isRelativeToChangelogFile());
  }
  
  @Test(expected = SetupException.class)
  public void finishInitialization_pathIsNull() throws SetupException {
    etlFile.finishInitialization();
  }
  
  @Test()
  public void finishInitialization_pathIsNotNull() throws SetupException {
    etlFile.setPath(randomString());
    etlFile.finishInitialization();
  }
  
  @Test
  public void validate_pathIsNull() {
    ValidationErrors errors = etlFile.validate(database);
    assertTrue(errors.getErrorMessages().contains("'path' is required"));
  }

  @Test
  public void validate_pathDoesNotExist() throws Exception {
    String path = randomString();
    String absolutePath = randomString();
    File pathFile = mock(File.class);
    when(pathFile.getAbsolutePath()).thenReturn(absolutePath);
    when(pathFile.exists()).thenReturn(false);
    whenNew(File.class).withArguments(path).thenReturn(pathFile);
    etlFile.setPath(path);
    ValidationErrors errors = etlFile.validate(database);
    assertTrue(errors.getErrorMessages()
        .contains(MessageFormat.format("''path'' - ''{0}'' does not exist", absolutePath)));
  }

  @Test
  public void validate_pathIsADirectory() throws Exception {
    String path = randomString();
    String absolutePath = randomString();
    File pathFile = mock(File.class);
    when(pathFile.getAbsolutePath()).thenReturn(absolutePath);
    when(pathFile.exists()).thenReturn(true);
    when(pathFile.isDirectory()).thenReturn(true);
    whenNew(File.class).withArguments(path).thenReturn(pathFile);
    etlFile.setPath(path);
    ValidationErrors errors = etlFile.validate(database);
    assertTrue(errors.getErrorMessages()
        .contains(MessageFormat.format("''path'' - ''{0}'' is a directory", absolutePath)));
  }
  
  @Test
  public void validate_pathIsValid() throws Exception {
    String path = randomString();
    String absolutePath = randomString();
    File pathFile = mock(File.class);
    when(pathFile.getAbsolutePath()).thenReturn(absolutePath);
    when(pathFile.exists()).thenReturn(true);
    when(pathFile.isDirectory()).thenReturn(false);
    whenNew(File.class).withArguments(path).thenReturn(pathFile);
    etlFile.setPath(path);
    ValidationErrors errors = etlFile.validate(database);
    assertTrue(errors.getErrorMessages().isEmpty());
  }
  
  @Test
  public void validate_pathIsValid_rollbackPathDoesNotExist() throws Exception {
    String path = randomString();
    String absolutePath = randomString();
    File pathFile = mock(File.class);
    when(pathFile.getAbsolutePath()).thenReturn(absolutePath);
    when(pathFile.exists()).thenReturn(true);
    when(pathFile.isDirectory()).thenReturn(false);
    whenNew(File.class).withArguments(path).thenReturn(pathFile);

    String rollbackPath = randomString();
    String absoluteRollbackPath = randomString();
    File rollbackPathFile = mock(File.class);
    when(rollbackPathFile.exists()).thenReturn(false);
    when(rollbackPathFile.getAbsolutePath()).thenReturn(absoluteRollbackPath);
    whenNew(File.class).withArguments(rollbackPath).thenReturn(rollbackPathFile);
    
    etlFile.setPath(path);
    etlFile.setRollbackPath(rollbackPath);
    ValidationErrors errors = etlFile.validate(database);
    System.out.println(errors.getErrorMessages());
    assertTrue(errors.getErrorMessages()
        .contains(MessageFormat
            .format("''rollbackPath'' - ''{0}'' does not exist", absoluteRollbackPath)));
  }
  
  @Test
  public void validate_pathIsValid_rollbackPathIsDirectory() throws Exception {
    String path = randomString();
    String absolutePath = randomString();
    File pathFile = mock(File.class);
    when(pathFile.getAbsolutePath()).thenReturn(absolutePath);
    when(pathFile.exists()).thenReturn(true);
    when(pathFile.isDirectory()).thenReturn(false);
    whenNew(File.class).withArguments(path).thenReturn(pathFile);

    String rollbackPath = randomString();
    String absoluteRollbackPath = randomString();
    File rollbackPathFile = mock(File.class);
    when(rollbackPathFile.exists()).thenReturn(true);
    when(rollbackPathFile.isDirectory()).thenReturn(true);
    when(rollbackPathFile.getAbsolutePath()).thenReturn(absoluteRollbackPath);
    whenNew(File.class).withArguments(rollbackPath).thenReturn(rollbackPathFile);
    
    etlFile.setPath(path);
    etlFile.setRollbackPath(rollbackPath);
    ValidationErrors errors = etlFile.validate(database);
    System.out.println(errors.getErrorMessages());
    assertTrue(errors.getErrorMessages()
        .contains(MessageFormat
            .format("''rollbackPath'' - ''{0}'' is a directory", absoluteRollbackPath)));
  }
  
  @Test
  public void validate_pathIsValid_rollbackPathIsValid() throws Exception {
    String path = randomString();
    String absolutePath = randomString();
    File pathFile = mock(File.class);
    when(pathFile.getAbsolutePath()).thenReturn(absolutePath);
    when(pathFile.exists()).thenReturn(true);
    when(pathFile.isDirectory()).thenReturn(false);
    whenNew(File.class).withArguments(path).thenReturn(pathFile);
    
    String rollbackPath = randomString();
    String absoluteRollbackPath = randomString();
    File rollbackPathFile = mock(File.class);
    when(rollbackPathFile.getAbsolutePath()).thenReturn(absoluteRollbackPath);
    when(rollbackPathFile.exists()).thenReturn(true);
    when(rollbackPathFile.isDirectory()).thenReturn(false);
    whenNew(File.class).withArguments(rollbackPath).thenReturn(rollbackPathFile);
    
    etlFile.setPath(path);
    etlFile.setRollbackPath(rollbackPath);
    ValidationErrors errors = etlFile.validate(database);
    assertTrue(errors.getErrorMessages().isEmpty());
  }
  
  @Test
  public void generateStatementsVolatile() {
    assertTrue(etlFile.generateStatementsVolatile(database));
  }
  
  @Test
  public void supportsRollback_rollbackNotDefined() {
    assertFalse(etlFile.supportsRollback(database));
  }
  
  @Test
  public void supportsRollback_rollbackDefined() {
    etlFile.setRollbackPath(randomString());
    assertTrue(etlFile.supportsRollback(database));
  }
  
  @Test
  public void createInverse_rollbackNotDefined() {
    assertNull(etlFile.createInverses());
  }
  
  @Test
  public void createInverse_rollbackDefined() {
    String path = randomString();
    String rollbackPath = randomString();
    etlFile.setPath(path);
    etlFile.setRollbackPath(rollbackPath);
    Change[] changes = etlFile.createInverses();
    assertEquals(rollbackPath, ((ScriptellaEtlFile) changes[0]).getPath());
    assertEquals(path, ((ScriptellaEtlFile) changes[0]).getRollbackPath());
  }
  
  @Test
  public void generateStatements() throws Exception {
    String path = randomString();
    File pathFile = mock(File.class);
    URI pathUri = URI.create("file://test");
    when(pathFile.toURI()).thenReturn(pathUri);
    whenNew(File.class).withArguments(path).thenReturn(pathFile);
    
    JdbcConnection jdbcConnection = mock(JdbcConnection.class);
    Connection connection = mock(Connection.class);
    when(jdbcConnection.getUnderlyingConnection()).thenReturn(connection);
    when(database.getConnection()).thenReturn(jdbcConnection);
    
    mockStatic(EtlExecutor.class);
    EtlExecutor executor = mock(EtlExecutor.class);
    when(EtlExecutor.newExecutor(pathUri.toURL())).thenReturn(executor);
    
    etlFile.setPath(path);
    SqlStatement[] sql = etlFile.generateStatements(database);
    
    verify(executor).execute();
    assertArrayEquals(new SqlStatement[]{}, sql);
  }
}
