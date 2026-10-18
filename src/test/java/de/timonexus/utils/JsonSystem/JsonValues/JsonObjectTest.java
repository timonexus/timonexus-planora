package de.timonexus.utils.JsonSystem.JsonValues;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.timonexus.utils.JsonSystem.JsonParser;

class JsonObjectTest {

    // -------------------------------------------------------------------------
    // Creation
    // -------------------------------------------------------------------------

    @Test
    void shouldCreateEmptyObject() {

        JsonObject object = new JsonObject();

        assertNotNull(object);
        assertTrue(object.getValues().isEmpty());
        assertFalse(object.contains("name"));
    }


    // -------------------------------------------------------------------------
    // Put / Get
    // -------------------------------------------------------------------------

    @Test
    void shouldPutAndGetValue() {

        JsonObject object = new JsonObject();
        JsonString value = new JsonString("Timo");

        object.put("name", value);

        assertSame(
                value,
                object.get("name")
        );
    }


    @Test
    void shouldReturnNullForUnknownKey() {

        JsonObject object = new JsonObject();

        assertNull(
                object.get("unknown")
        );
    }


    @Test
    void shouldOverwriteExistingValue() {

        JsonObject object = new JsonObject();

        object.put(
                "name",
                new JsonString("Timo")
        );

        object.put(
                "name",
                new JsonString("Max")
        );

        assertEquals(
                "Max",
                object.getString("name")
        );
    }


    @Test
    void shouldContainExistingKey() {

        JsonObject object = new JsonObject();

        object.put(
                "name",
                new JsonString("Timo")
        );

        assertTrue(
                object.contains("name")
        );
    }


    @Test
    void shouldNotContainUnknownKey() {

        JsonObject object = new JsonObject();

        assertFalse(
                object.contains("name")
        );
    }


    // -------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------

    @Test
    void shouldGetString() {

        JsonObject object = new JsonObject();

        object.put(
                "name",
                new JsonString("Timo")
        );

        assertEquals(
                "Timo",
                object.getString("name")
        );
    }


    @Test
    void shouldThrowExceptionWhenGettingNonString() {

        JsonObject object = new JsonObject();

        object.put(
                "name",
                new JsonBoolean(true)
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> object.getString("name")
        );

        assertEquals(
                "Value 'name' is not a string.",
                exception.getMessage()
        );
    }


    // -------------------------------------------------------------------------
    // Integer
    // -------------------------------------------------------------------------

    @Test
    void shouldGetInt() {

        JsonObject object = new JsonObject();

        object.put(
                "age",
                new JsonNumber("17")
        );

        assertEquals(
                17,
                object.getInt("age")
        );
    }


    @Test
    void shouldThrowExceptionWhenGettingNonNumberAsInt() {

        JsonObject object = new JsonObject();

        object.put(
                "age",
                new JsonString("17")
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> object.getInt("age")
        );

        assertEquals(
                "Value 'age' is not a number.",
                exception.getMessage()
        );
    }


    // -------------------------------------------------------------------------
    // Long
    // -------------------------------------------------------------------------

    @Test
    void shouldGetLong() {

        JsonObject object = new JsonObject();

        object.put(
                "value",
                new JsonNumber("123456789")
        );

        assertEquals(
                123456789L,
                object.getLong("value")
        );
    }


    @Test
    void shouldThrowExceptionWhenGettingNonNumberAsLong() {

        JsonObject object = new JsonObject();

        object.put(
                "value",
                new JsonString("123456789")
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> object.getLong("value")
        );

        assertEquals(
                "Value 'value' is not a number.",
                exception.getMessage()
        );
    }


    // -------------------------------------------------------------------------
    // Double
    // -------------------------------------------------------------------------

    @Test
    void shouldGetDouble() {

        JsonObject object = new JsonObject();

        object.put(
                "value",
                new JsonNumber("12.5")
        );

        assertEquals(
                12.5,
                object.getDouble("value")
        );
    }


    @Test
    void shouldThrowExceptionWhenGettingNonNumberAsDouble() {

        JsonObject object = new JsonObject();

        object.put(
                "value",
                new JsonString("12.5")
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> object.getDouble("value")
        );

        assertEquals(
                "Value 'value' is not a number.",
                exception.getMessage()
        );
    }


    // -------------------------------------------------------------------------
    // Boolean
    // -------------------------------------------------------------------------

    @Test
    void shouldGetBoolean() {

        JsonObject object = new JsonObject();

        object.put(
                "active",
                new JsonBoolean(true)
        );

        assertTrue(
                object.getBoolean("active")
        );
    }


    @Test
    void shouldGetFalseBoolean() {

        JsonObject object = new JsonObject();

        object.put(
                "active",
                new JsonBoolean(false)
        );

        assertFalse(
                object.getBoolean("active")
        );
    }


    @Test
    void shouldThrowExceptionWhenGettingNonBoolean() {

        JsonObject object = new JsonObject();

        object.put(
                "active",
                new JsonString("true")
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> object.getBoolean("active")
        );

        assertEquals(
                "Value 'active' is not a boolean.",
                exception.getMessage()
        );
    }


    // -------------------------------------------------------------------------
    // Object
    // -------------------------------------------------------------------------

    @Test
    void shouldGetObject() {

        JsonObject object = new JsonObject();
        JsonObject nestedObject = new JsonObject();

        object.put(
                "settings",
                nestedObject
        );

        assertSame(
                nestedObject,
                object.getObject("settings")
        );
    }


    @Test
    void shouldThrowExceptionWhenGettingNonObject() {

        JsonObject object = new JsonObject();

        object.put(
                "settings",
                new JsonString("test")
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> object.getObject("settings")
        );

        assertEquals(
                "Value 'settings' is not an object.",
                exception.getMessage()
        );
    }


    // -------------------------------------------------------------------------
    // Array
    // -------------------------------------------------------------------------

    @Test
    void shouldGetArray() {

        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();

        object.put(
                "items",
                array
        );

        assertSame(
                array,
                object.getArray("items")
        );
    }


    @Test
    void shouldThrowExceptionWhenGettingNonArray() {

        JsonObject object = new JsonObject();

        object.put(
                "items",
                new JsonString("test")
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> object.getArray("items")
        );

        assertEquals(
                "Value 'items' is not an array.",
                exception.getMessage()
        );
    }


    // -------------------------------------------------------------------------
    // Nested paths
    // -------------------------------------------------------------------------

    @Test
    void shouldGetNestedStringUsingPath() {

        JsonObject object = createNestedObject();

        assertEquals(
                "localhost",
                object.getString("database.connection.host")
        );
    }


    @Test
    void shouldGetNestedIntUsingPath() {

        JsonObject object = createNestedObject();

        assertEquals(
                5432,
                object.getInt("database.connection.port")
        );
    }


    @Test
    void shouldGetNestedBooleanUsingPath() {

        JsonObject object = createNestedObject();

        assertTrue(
                object.getBoolean("application.debug")
        );
    }


        @Test
        void shouldGetNestedObjectUsingPath() { 
        JsonObject object = createNestedObject();       
        JsonObject database =
                object.getObject("database");   
        assertNotNull(database);        
        JsonObject connection =
                database.getObject("connection");       
        assertNotNull(connection);      
        assertEquals(
                "localhost",
                connection.getString("host")
        );
        }


    @Test
    void shouldGetNestedArrayUsingPath() {

        JsonObject object = new JsonObject();
        JsonObject application = new JsonObject();
        JsonArray modules = new JsonArray();

        modules.add(
                new JsonString("database")
        );

        modules.add(
                new JsonString("logging")
        );

        application.put(
                "modules",
                modules
        );

        object.put(
                "application",
                application
        );

        JsonArray result =
                object.getArray("application.modules");

        assertSame(
                modules,
                result
        );

        assertEquals(
                2,
                result.size()
        );
    }


    @Test
    void shouldThrowExceptionWhenNestedPathDoesNotExist() {

        JsonObject object = createNestedObject();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> object.getString("database.connection.username")
        );

        assertEquals(
                "No value found for JSON path 'database.connection.username'.",
                exception.getMessage()
        );
    }


    @Test
    void shouldThrowExceptionWhenPathElementIsNotObject() {

        JsonObject object = new JsonObject();

        object.put(
                "database",
                new JsonString("database")
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> object.getString("database.host")
        );

        assertEquals(
                "Path 'database' in 'database.host' is not a JSON object.",
                exception.getMessage()
        );
    }


    // -------------------------------------------------------------------------
    // Path validation
    // -------------------------------------------------------------------------

    @Test
    void shouldRejectNullPath() {

        JsonObject object = new JsonObject();

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> object.getString(null)
        );

        assertEquals(
                "JSON path must not be null.",
                exception.getMessage()
        );
    }


    @Test
    void shouldRejectBlankPath() {

        JsonObject object = new JsonObject();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> object.getString("   ")
        );

        assertEquals(
                "JSON path must not be blank.",
                exception.getMessage()
        );
    }


    @Test
    void shouldRejectEmptyPathElement() {

        JsonObject object = createNestedObject();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> object.getString("database..host")
        );

        assertEquals(
                "JSON path contains an empty element: 'database..host'.",
                exception.getMessage()
        );
    }


    @Test
    void shouldRejectLeadingDot() {

        JsonObject object = createNestedObject();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> object.getString(".database")
        );

        assertEquals(
                "JSON path contains an empty element: '.database'.",
                exception.getMessage()
        );
    }


    @Test
    void shouldRejectTrailingDot() {

        JsonObject object = createNestedObject();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> object.getString("database.")
        );

        assertEquals(
                "JSON path contains an empty element: 'database.'.",
                exception.getMessage()
        );
    }


    // -------------------------------------------------------------------------
    // Map
    // -------------------------------------------------------------------------

    @Test
    void shouldReturnValues() {

        JsonObject object = new JsonObject();

        JsonString name = new JsonString("Timo");
        JsonBoolean active = new JsonBoolean(true);

        object.put(
                "name",
                name
        );

        object.put(
                "active",
                active
        );

        Map<String, JsonValue> values =
                object.getValues();

        assertEquals(
                2,
                values.size()
        );

        assertSame(
                name,
                values.get("name")
        );

        assertSame(
                active,
                values.get("active")
        );
    }


    @Test
    void shouldPreserveInsertionOrder() {

        JsonObject object = new JsonObject();

        object.put(
                "first",
                new JsonString("1")
        );

        object.put(
                "second",
                new JsonString("2")
        );

        object.put(
                "third",
                new JsonString("3")
        );

        assertEquals(
                "[first, second, third]",
                object.getValues()
                        .keySet()
                        .toString()
        );
    }


    @Test
    void shouldReturnUnmodifiableMap() {

        JsonObject object = new JsonObject();

        object.put(
                "name",
                new JsonString("Timo")
        );

        Map<String, JsonValue> values =
                object.getValues();

        assertThrows(
                UnsupportedOperationException.class,
                () -> values.put(
                        "other",
                        new JsonString("Test")
                )
        );
    }


    @Test
    void shouldNotExposeInternalMap() {

        JsonObject object = new JsonObject();

        object.put(
                "name",
                new JsonString("Timo")
        );

        Map<String, JsonValue> values =
                object.getValues();

        assertThrows(
                UnsupportedOperationException.class,
                values::clear
        );

        assertTrue(
                object.contains("name")
        );
    }


    // -------------------------------------------------------------------------
    // Null validation
    // -------------------------------------------------------------------------

    @Test
    void shouldRejectNullValue() {

        JsonObject object = new JsonObject();

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> object.put("value", null)
        );

        assertEquals(
                "JSON object value must not be null.",
                exception.getMessage()
        );
    }


    @Test
    void shouldRejectNullKey() {

        JsonObject object = new JsonObject();

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> object.put(
                        null,
                        new JsonString("Test")
                )
        );

        assertEquals(
                "JSON object key must not be null.",
                exception.getMessage()
        );
    }


    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Test
    void shouldReturnCorrectStringRepresentation() {

        JsonObject object = new JsonObject();

        object.put(
                "name",
                new JsonString("Timo")
        );

        object.put(
                "active",
                new JsonBoolean(true)
        );

        assertEquals(
                "{name=Timo, active=true}",
                object.toString()
        );
    }


    @Test
    void shouldReturnEmptyStringRepresentationForEmptyObject() {

        JsonObject object = new JsonObject();

        assertEquals(
                "{}",
                object.toString()
        );
    }


    // -------------------------------------------------------------------------
    // Inheritance
    // -------------------------------------------------------------------------

    @Test
    void shouldExtendJsonValue() {

        JsonObject object = new JsonObject();

        assertInstanceOf(
                JsonValue.class,
                object
        );
    }


    // -------------------------------------------------------------------------
    // Test helpers
    // -------------------------------------------------------------------------

    private JsonObject createNestedObject() {

        JsonObject root = new JsonObject();

        JsonObject database = new JsonObject();
        JsonObject connection = new JsonObject();

        connection.put(
                "host",
                new JsonString("localhost")
        );

        connection.put(
                "port",
                new JsonNumber("5432")
        );

        database.put(
                "connection",
                connection
        );

        JsonObject application = new JsonObject();

        application.put(
                "debug",
                new JsonBoolean(true)
        );

        root.put(
                "database",
                database
        );

        root.put(
                "application",
                application
        );

        return root;
    }


        @Test
        void shouldParseNestedLoggerConfiguration() {
        String json = """
                {
                        "logger": {
                        "loggingType": 0,
                        "filePath": "logs/",
                        "fileSuffix": "log_",
                        "filePrefix": ".log",
                        "deadline": 14
                        }
                }
                """;

        JsonParser parser = new JsonParser(Path.of("unused.json"));

        JsonObject config = parser.parse(json);

        assertNotNull(config);
        assertTrue(config.contains("logger"));

        JsonObject logger = config.getObject("logger");

        assertEquals(0, logger.getInt("loggingType"));
        assertEquals("logs/", logger.getString("filePath"));
        assertEquals("log_", logger.getString("fileSuffix"));
        assertEquals(".log", logger.getString("filePrefix"));
        assertEquals(14, logger.getInt("deadline"));
        }
}