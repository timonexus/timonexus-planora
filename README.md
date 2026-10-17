# Timonexus-Utils

**Timonexus-Utils** is a collection of reusable utility classes and components
for Java applications.

The library is primarily developed for my own private projects and is
continuously extended and improved over time. At the same time, the project
serves as a personal learning project for exploring software architecture,
API design, file handling, data processing and other areas of Java development.

The project is actively developed and should therefore not be considered
production-ready or completely error-free. APIs and implementations may
change as the project evolves.

---

## Features

- [x] FileManager
- [x] LogManager
- [x] DataLoader

---

## Table of Contents

- [FileManager](#filemanager)
  - [Creating a ManagedFile](#creating-a-managedfile)
  - [Reading a File](#reading-a-file)
  - [Writing to a File](#writing-to-a-file)
  - [Appending to a File](#appending-to-a-file)
  - [Checking if a File Exists](#checking-if-a-file-exists)
  - [Creating a File](#creating-a-file)
  - [Deleting a File](#deleting-a-file)
  - [Moving a File](#moving-a-file)
  - [Changing the File Path](#changing-the-file-path)
  - [Available Operations](#available-operations)
  - [Exception Handling](#exception-handling)
- [DataLoader](#dataloader)
  - [Parsing a JSON File](#parsing-a-json-file)
  - [Accessing Nested Values](#accessing-nested-values)
  - [Working with JSON Objects](#working-with-json-objects)
  - [Working with JSON Arrays](#working-with-json-arrays)
  - [Error Handling](#error-handling)
  - [Supported JSON Types](#supported-json-types)
- [Logger](#logger)
  - [Configuration](#1-configuration)
  - [Creating a Logger](#2-creating-a-logger)
  - [Writing Log Messages](#3-writing-log-messages)
  - [Logging Exceptions](#4-logging-exceptions)
  - [Daily Log Files](#5-daily-log-files)
  - [Automatic Daily Rotation](#6-automatic-daily-rotation)
  - [Automatic Log Cleanup](#7-automatic-log-cleanup)
  - [Complete Example](#8-complete-example)
  - [Recommended Project Structure](#9-recommended-project-structure)
  - [Recommended Usage](#10-recommended-usage)
  - [Log Format](#11-log-format)

---

# FileManager

The `FileManager` package provides simple and reusable abstractions for
working with files using Java's `java.nio.file` API.

The main class, `ManagedFile`, encapsulates a `java.nio.file.Path` and provides
common file operations such as:

- Reading
- Writing
- Appending
- Creating
- Deleting
- Moving
- Changing the file path

The goal is to provide a small and convenient abstraction around common
filesystem operations without hiding the underlying Java NIO functionality.

---

## Creating a ManagedFile

A `ManagedFile` is created using a `java.nio.file.Path`.

```java
import java.nio.file.Path;

import de.timonexus.utils.filemanager.ManagedFile;

public class Main {

    public static void main(String[] args) {

        Path path =
                Path.of("data", "example.txt");

        ManagedFile file =
                new ManagedFile(path);
    }
}
````

The path can contain both relative and absolute locations.

---

## Reading a File

The complete file can be loaded as a list of lines:

```java
List<String> lines =
        file.loadFile();

for (String line : lines) {

    System.out.println(line);
}
```

`ManagedFile` reads text files using UTF-8 encoding.

---

## Writing to a File

`writeFile()` creates the file if it does not exist and replaces its existing
content if it already exists.

```java
file.writeFile(
        "Hello World!"
);
```

For example, if the file previously contained:

```text
Old content
```

after calling `writeFile()` it will contain:

```text
Hello World!
```

This operation therefore represents a complete overwrite of the file.

---

## Appending to a File

Use `append()` to add content to the end of an existing file:

```java
file.append(
        "Another line"
);
```

The existing content is preserved.

This makes `append()` particularly useful for files such as log files where
new content should be added without replacing existing data.

---

## Checking if a File Exists

The `exists()` method can be used to check whether the managed file currently
exists:

```java
if (file.exists()) {

    System.out.println(
            "File exists."
    );
}
```

The method can be used before operations such as deleting a file.

---

## Creating a File

A file can be explicitly created using:

```java
file.create();
```

This operation throws an `IOException` if the file already exists or if the
file cannot be created.

---

## Deleting a File

A managed file can be deleted using:

```java
file.delete();
```

Example:

```java
if (file.exists()) {

    file.delete();
}
```

If the file does not exist, the behavior depends on the underlying
implementation of the `ManagedFile` operation.

---

## Moving a File

`moveTo()` can be used to move the file to another directory:

```java
Path target =
        Path.of("backup");

file.moveTo(target);
```

The original filename is preserved.

A custom filename can also be specified:

```java
file.moveTo(
        Path.of("backup"),
        "backup.txt"
);
```

After a successful move, the internal path of the `ManagedFile` is
automatically updated.

The current path can be retrieved using:

```java
System.out.println(
        file.getFilePath()
);
```

---

## Changing the File Path

The path of a `ManagedFile` can be changed using:

```java
file.setFilePath(
        Path.of(
                "new",
                "location",
                "file.txt"
        )
);
```

The current path can be retrieved with:

```java
Path path =
        file.getFilePath();
```

Changing the path does not automatically move or rename the underlying file.
It only changes the path managed by the `ManagedFile` instance.

---

## Available Operations

| Method                     | Description                               |
| -------------------------- | ----------------------------------------- |
| `loadFile()`               | Reads the file as a list of UTF-8 lines   |
| `writeFile(content)`       | Creates or completely overwrites the file |
| `append(content)`          | Appends content to the file               |
| `exists()`                 | Checks whether the file exists            |
| `create()`                 | Creates the file                          |
| `delete()`                 | Deletes the file                          |
| `moveTo(target)`           | Moves the file while keeping its filename |
| `moveTo(target, fileName)` | Moves the file using a custom filename    |
| `getFilePath()`            | Returns the current file path             |
| `setFilePath(path)`        | Changes the current file path             |

---

## Exception Handling

File operations that access or modify the filesystem throw `IOException`
when an operation fails.

Example:

```java
try {

    file.writeFile(
            "Hello World!"
    );

} catch (IOException exception) {

    System.err.println(
            "Failed to write file: "
                    + exception.getMessage()
    );
}
```

This allows applications using `ManagedFile` to decide how filesystem errors
should be handled instead of forcing a specific error-handling strategy.

---

# DataLoader

The `DataLoader` package provides functionality for reading and parsing JSON
files without requiring an external JSON library.

The parser converts JSON data into a type-safe object structure consisting of:

* `JsonObject`
* `JsonArray`
* `JsonString`
* `JsonNumber`
* `JsonBoolean`
* `JsonNull`

The parser is implemented as part of Timonexus-Utils and is therefore
independent of external JSON parsing libraries.

---

## Parsing a JSON File

Given the following JSON file:

```json
{
    "application": {
        "name": "MyApplication",
        "version": "1.0.0",
        "debug": true
    },
    "database": {
        "host": "localhost",
        "port": 5432
    }
}
```

The file can be parsed using `JsonParser`:

```java
import java.nio.file.Path;

import de.timonexus.utils.DataLoader.JsonParser;
import de.timonexus.utils.DataLoader.JsonValues.JsonObject;

public class Main {

    public static void main(String[] args)
            throws Exception {

        Path path =
                Path.of("config.json");

        JsonParser parser =
                new JsonParser(path);

        JsonObject config =
                parser.parse();

        System.out.println(
                config.getString(
                        "application.name"
                )
        );

        System.out.println(
                config.getString(
                        "application.version"
                )
        );

        System.out.println(
                config.getBoolean(
                        "application.debug"
                )
        );

        System.out.println(
                config.getInt(
                        "database.port"
                )
        );
    }
}
```

---

## Accessing Nested Values

Nested JSON objects can be accessed using dot-separated paths:

```java
String applicationName =
        config.getString(
                "application.name"
        );

boolean debug =
        config.getBoolean(
                "application.debug"
        );

int port =
        config.getInt(
                "database.port"
        );
```

This allows deeply nested configuration values to be accessed without
manually traversing every `JsonObject`.

The following getter methods are available:

| Method             | Description                        |
| ------------------ | ---------------------------------- |
| `getString(path)`  | Returns a `String` value           |
| `getInt(path)`     | Returns an `int` value             |
| `getLong(path)`    | Returns a `long` value             |
| `getDouble(path)`  | Returns a `double` value           |
| `getBoolean(path)` | Returns a `boolean` value          |
| `getObject(path)`  | Returns a nested `JsonObject`      |
| `getArray(path)`   | Returns a `JsonArray`              |
| `get(path)`        | Returns the underlying `JsonValue` |

---

## Working with JSON Objects

`JsonObject` can also be created and modified programmatically:

```java
JsonObject object =
        new JsonObject();

object.put(
        "name",
        new JsonString("Timo")
);

object.put(
        "age",
        new JsonNumber("17")
);

object.put(
        "active",
        new JsonBoolean(true)
);
```

Values can then be accessed using the corresponding getter:

```java
String name =
        object.getString("name");

int age =
        object.getInt("age");

boolean active =
        object.getBoolean("active");
```

`JsonObject` therefore works both as the result of parsing a JSON document
and as a programmatically constructed JSON structure.

---

## Working with JSON Arrays

Arrays are represented by `JsonArray`:

```java
JsonArray array =
        new JsonArray();

array.add(
        new JsonString("Java")
);

array.add(
        new JsonString("JSON")
);

array.add(
        new JsonNumber("25")
);
```

Values can be accessed using their index:

```java
JsonValue first =
        array.get(0);

int size =
        array.size();
```

---

## Error Handling

Invalid JSON or invalid value access results in an exception.

For example, attempting to read a string as a number:

```java
object.put(
        "name",
        new JsonString("Timo")
);

int value =
        object.getInt("name");
```

throws an `IllegalStateException` because the stored value is not a number.

Invalid JSON syntax is reported using `JsonParseException`:

```java
JsonParser parser =
        new JsonParser(path);

try {

    JsonObject object =
            parser.parse();

} catch (JsonParseException exception) {

    System.err.println(
            "Failed to parse JSON: "
                    + exception.getMessage()
    );
}
```

This makes it possible to distinguish JSON parsing errors from other
application errors.

---

## Supported JSON Types

The `DataLoader` currently supports the following JSON types:

* Objects: `{ ... }`
* Arrays: `[ ... ]`
* Strings: `"..."`
* Numbers
* Booleans: `true` / `false`
* `null`

The parser also supports:

* Nested objects
* Nested arrays
* Whitespace
* Common JSON string escape sequences

---

# Logger

The `timonexus-utils` Logger provides a simple and configurable logging
system for Java applications.

The Logger separates the logging API from the actual output destinations,
allowing log messages to be written to different outputs without changing
the application code.

It supports:

* Console logging
* File logging
* Console and file logging simultaneously
* Log levels
* Timestamps
* Daily log files
* Automatic log file rotation at midnight
* Automatic deletion of old log files
* JSON-based configuration

---

## 1. Configuration

The Logger is configured through a JSON configuration file.

Example:

```json
{
    "defaultFilePath": "C:\\Timob\\.Timonexus",
    "logger": {
        "filePath": "logs",
        "loggingType": 0,
        "fileSuffix": "log_",
        "filePrefix": ".log",
        "deadline": 14
    }
}
```

### Configuration Options

| Property             | Description                                          |
| -------------------- | ---------------------------------------------------- |
| `defaultFilePath`    | Base directory for the application data              |
| `logger.filePath`    | Directory in which log files are stored              |
| `logger.loggingType` | Determines where log messages are written            |
| `logger.fileSuffix`  | Text before the date in the filename                 |
| `logger.filePrefix`  | Text after the date in the filename                  |
| `logger.deadline`    | Number of days after which old log files are deleted |

### Logging Types

The `loggingType` property determines the output destination.

| Value | Mode           | Description                               |
| ----: | -------------- | ----------------------------------------- |
|   `0` | Console + File | Writes logs to the console and a log file |
|   `1` | Console        | Writes logs only to the console           |
|   `2` | File           | Writes logs only to a log file            |

Example:

```json
"loggingType": 0
```

enables both console and file logging.

---

## 2. Creating a Logger

Create a `Logger` by providing the path to your JSON configuration file:

```java
import java.nio.file.Path;

import de.timonexus.utils.logger.Logger;

public class Main {

    public static void main(String[] args)
            throws Exception {

        Path configPath =
                Path.of("config.json");

        Logger logger =
                new Logger(configPath);
    }
}
```

During initialization, the Logger automatically:

1. Loads the configuration
2. Configures the selected outputs
3. Initializes the logging system
4. Checks the log directory
5. Removes expired log files

The application therefore only needs to provide the configuration file and
create the Logger instance.

---

# 3. Writing Log Messages

The Logger provides four log levels:

* `INFO`
* `DEBUG`
* `WARNING`
* `ERROR`

Each logging method receives the originating class name and the message.

---

## INFO

Use `info()` for general information about the application.

```java
logger.info(
        Main.class.getName(),
        "Application started."
);
```

Example output:

```text
[2026-09-08 17:30:12] [INFO] [Main] Application started.
```

For shorter class names, `Class.getSimpleName()` can also be used:

```java
logger.info(
        Main.class.getSimpleName(),
        "Application started."
);
```

---

## DEBUG

Use `debug()` for detailed information that is primarily useful during
development and troubleshooting.

```java
logger.debug(
        Main.class.getName(),
        "Loading configuration..."
);
```

Example:

```text
[2026-09-08 17:30:15] [DEBUG] [Main] Loading configuration...
```

---

## WARNING

Use `warning()` when something unexpected occurs but the application can
continue operating.

```java
logger.warning(
        Main.class.getName(),
        "Configuration file is using default values."
);
```

Example:

```text
[2026-09-08 17:30:18] [WARNING] [Main] Configuration file is using default values.
```

---

## ERROR

Use `error()` when an error occurs.

```java
logger.error(
        Main.class.getName(),
        "Could not load configuration."
);
```

Example:

```text
[2026-09-08 17:30:20] [ERROR] [Main] Could not load configuration.
```

---

# 4. Logging Exceptions

The Logger also provides an `error()` method that accepts an exception.

```java
try {

    // Some operation

} catch (Exception exception) {

    logger.error(
            Main.class.getName(),
            "An error occurred while loading the configuration.",
            exception
    );
}
```

This adds the exception information to the log message.

This overload is useful when an operation fails and the corresponding
exception should be recorded together with the application error message.

---

# 5. Daily Log Files

The Logger creates a new log file for each day.

The filename format is:

```text
<fileSuffix><date><filePrefix>
```

With the following configuration:

```json
{
    "fileSuffix": "log_",
    "filePrefix": ".log"
}
```

the generated files look like:

```text
log_2026-09-08.log
log_2026-09-09.log
log_2026-09-10.log
```

All log messages generated during the same day are written to the same
log file.

The date format used for log files is:

```text
yyyy-MM-dd
```

---

# 6. Automatic Daily Rotation

The Logger automatically detects a date change.

No timer or background thread is required.

For example:

```text
23:59:59
    ↓
log_2026-09-08.log
```

After midnight:

```text
00:00:01
    ↓
log_2026-09-09.log
```

The new file is selected automatically when the next log message is written.

This means the Logger does not need to run a permanent background task just
to detect midnight.

The rotation is therefore performed lazily when the next log entry is
generated.

---

# 7. Automatic Log Cleanup

Old log files can automatically be removed.

The following configuration:

```json
"deadline": 14
```

means that log files older than 14 days are deleted when the Logger is
initialized.

For example:

```text
log_2026-09-08.log
log_2026-08-30.log
log_2026-08-10.log
```

If the configured deadline is 14 days, old files outside the retention
period are removed automatically.

Files that do not match the expected log filename format are ignored.

This prevents unrelated files in the log directory from being accidentally
deleted.

---

# 8. Complete Example

A simple application can use the Logger like this:

```java
import java.nio.file.Path;

import de.timonexus.utils.logger.Logger;

public class Main {

    public static void main(String[] args)
            throws Exception {

        Path configPath =
                Path.of("config.json");

        Logger logger =
                new Logger(configPath);

        logger.info(
                Main.class.getSimpleName(),
                "Application started."
        );

        logger.debug(
                Main.class.getSimpleName(),
                "Loading application configuration."
        );

        try {

            // Application logic

        } catch (Exception exception) {

            logger.error(
                    Main.class.getSimpleName(),
                    "An unexpected error occurred.",
                    exception
            );
        }

        logger.info(
                Main.class.getSimpleName(),
                "Application finished."
        );
    }
}
```

---

# 9. Recommended Project Structure

A typical application using the Logger could look like this:

```text
MyApplication/
│
├── config.json
│
├── logs/
│   ├── log_2026-09-08.log
│   ├── log_2026-09-09.log
│   └── log_2026-09-10.log
│
└── src/
    └── main/
        └── java/
            └── ...
```

The exact location of the log directory depends on the configured
`defaultFilePath` and `logger.filePath` values.

---

# 10. Recommended Usage

It is recommended to create one Logger instance for the application or for
a major application component and reuse it.

Example:

```java
public class UserService {

    private final Logger logger;

    public UserService(Logger logger) {

        this.logger =
                logger;
    }

    public void loadUser() {

        logger.debug(
                UserService.class.getName(),
                "Loading user."
        );
    }
}
```

The Logger can then be passed into application components that need logging:

```java
UserService userService =
        new UserService(logger);
```

This keeps the Logger configurable and avoids unnecessary Logger instances.

It also makes dependencies explicit and makes components easier to test.

---

# 11. Log Format

Every log message follows this basic format:

```text
[TIMESTAMP] [LEVEL] [CLASS] MESSAGE
```

Example:

```text
[2026-09-08 17:42:15] [INFO] [UserService] User loaded successfully.
```

The individual components are:

| Component | Example                     | Description                    |
| --------- | --------------------------- | ------------------------------ |
| Timestamp | `2026-09-08 17:42:15`       | Date and time of the log entry |
| Level     | `INFO`                      | Severity of the message        |
| Class     | `UserService`               | Origin of the message          |
| Message   | `User loaded successfully.` | Actual log message             |

---

# Architecture

The Logger is structured so that the main `Logger` class is responsible for
the logging API while specialized components handle individual output and
file-management tasks.

The current architecture can be represented as:

```text
Logger
│
├── LogOutput
│   │
│   ├── ConsoleOutput
│   │
│   └── FileOutput
│       │
│       └── LogFileManager
│           │
│           └── FileFormatter
│
└── LogFileAdmin
    │
    └── ManagedFile
```

This separation keeps the individual components focused on specific
responsibilities:

* `Logger` provides the public logging API.
* `LogOutput` defines the common output abstraction.
* `ConsoleOutput` writes messages to the console.
* `FileOutput` writes messages to log files.
* `LogFileManager` manages the currently active daily log file.
* `FileFormatter` formats dates used in log filenames.
* `LogFileAdmin` handles the cleanup of old log files.
* `ManagedFile` provides the underlying file operations.

The application only needs to interact with:

```java
logger.info(...);

logger.debug(...);

logger.warning(...);

logger.error(...);
```

while the Logger handles output selection, formatting and daily log file
management internally.

---

# Project Status

Timonexus-Utils is an actively developed personal utility library and
learning project.

The project is continuously extended, refactored and improved as new
requirements arise in my other Java projects.

Because of this, the current implementation should be considered a
development project rather than a finalized production library.

APIs, package structures and implementations may change in future versions.

---

# Summary

Timonexus-Utils currently provides three main utility areas:

```text
Timonexus-Utils
│
├── FileManager
│   └── ManagedFile
│
├── DataLoader
│   └── JSON Parser
│
└── Logger
    ├── ConsoleOutput
    ├── FileOutput
    ├── LogFileManager
    ├── LogFileAdmin
    └── FileFormatter
```

The goal of the project is to build reusable Java components that can be
used across my own applications while simultaneously improving the
architecture, design and implementation of the library over time.