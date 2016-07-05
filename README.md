Scriptella Gradle Plugin
-----------------------
A plugin for [Liquibase](http://www.liquibase.org) that allows you to use
[Scriptella](http://scriptella.javaforge.com/) to manage
a change within a Liquibase changeset. This project is created and managed by
Donald Blodgett.

Usage
-----
Liquibase changelog:

```xml
<changetSet id="1" author="donaldblodgett">
  <ext:etlFile path="/path/to/your/scriptella.etl.xml"/>
</changeSet>
```

Scriptella ETL:

```xml
<etl>
  <connection id="db" driver="liquibase"/>
  <!-- Rest of Scriptella ETL script -->
</etl>
```

News
----
###July 04, 2016
The plugin has been released.

## Usage
The Liquibase Scriptella plugin allows you to use Liquibase to execute Scriptella ETL scripts.
It is meant to be a light weight wrapper for Scriptella. This can be useful when
you need to make a change that is very complicated or cannot be easily done from Liquibase.

When the Scriptella plugin is applied, it creates a Liquibase change `ext:etlFile`.
The etlFile change has a few parameters that can be set to allow the Scriptella ETL script to be executed.
* path - required; the path to the Scriptella ETL file to be executed when this change is applied
* rollbackPath - optional; default: null; the path to a Scriptella ETL file to be executed when this change is rolled back
  - if rollbackPath is null, this change does not support rollback
  - if rollbackPath is not null, the specified file will be executed upon rollback of this change
* relativeToChangelogFile - optional; default: false; determines if `path` and `rollbackPath` are relative to the changelog file
  - if true, the paths are relative to the changelog file the change is defined in
  - if false, the paths can be absolute or relative to the current working directory

The Liquibase connection is exposed to Scriptella in the form of a driver `liquibase` which can be used by
Scriptella as a JDBC connection.
