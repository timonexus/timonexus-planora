package de.timonexus.utils.JsonSystem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.timonexus.utils.JsonSystem.JsonValues.JsonArray;
import de.timonexus.utils.JsonSystem.JsonValues.JsonBoolean;
import de.timonexus.utils.JsonSystem.JsonValues.JsonNull;
import de.timonexus.utils.JsonSystem.JsonValues.JsonNumber;
import de.timonexus.utils.JsonSystem.JsonValues.JsonObject;
import de.timonexus.utils.JsonSystem.JsonValues.JsonString;

class JsonParsenerTest {

    @Test
    void shouldParseEmptyObject() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("{}");

        assertNotNull(object);
        assertTrue(object.getValues().isEmpty());
    }

    @Test
    void shouldParseString() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "name": "Timo"
                }
                """);

        assertInstanceOf(
                JsonString.class,
                object.get("name")
        );

        assertEquals(
                "Timo",
                object.getString("name")
        );
    }

    @Test
    void shouldParseEmptyString() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "name": ""
                }
                """);

        assertEquals(
                "",
                object.getString("name")
        );
    }

    @Test
    void shouldParseTrue() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "active": true
                }
                """);

        assertInstanceOf(
                JsonBoolean.class,
                object.get("active")
        );

        assertTrue(
                object.getBoolean("active")
        );
    }

    @Test
    void shouldParseFalse() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "active": false
                }
                """);

        assertInstanceOf(
                JsonBoolean.class,
                object.get("active")
        );

        assertFalse(
                object.getBoolean("active")
        );
    }

    @Test
    void shouldParseNull() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "value": null
                }
                """);

        assertInstanceOf(
                JsonNull.class,
                object.get("value")
        );
    }

    @Test
    void shouldParseInteger() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "age": 17
                }
                """);

        assertInstanceOf(
                JsonNumber.class,
                object.get("age")
        );

        assertEquals(
                17,
                object.getInt("age")
        );
    }

    @Test
    void shouldParseNegativeInteger() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "temperature": -15
                }
                """);

        assertEquals(
                -15,
                object.getInt("temperature")
        );
    }

    @Test
    void shouldParseDecimal() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "price": 12.5
                }
                """);

        assertInstanceOf(
                JsonNumber.class,
                object.get("price")
        );

        assertEquals(
                12.5,
                object.getDouble("price")
        );
    }

    @Test
    void shouldParseLong() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "value": 123456789
                }
                """);

        assertEquals(
                123456789L,
                object.getLong("value")
        );
    }

    @Test
    void shouldParseExponent() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "value": 1.5e3
                }
                """);

        assertEquals(
                1500.0,
                object.getDouble("value")
        );
    }

    @Test
    void shouldParseNegativeExponent() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "value": 1.5e-3
                }
                """);

        assertEquals(
                0.0015,
                object.getDouble("value")
        );
    }

    @Test
    void shouldParseArray() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "items": [
                        "one",
                        "two",
                        "three"
                    ]
                }
                """);

        assertInstanceOf(
                JsonArray.class,
                object.get("items")
        );

        JsonArray array = object.getArray("items");

        assertEquals(3, array.size());

        assertEquals(
                "one",
                ((JsonString) array.get(0)).getValue()
        );

        assertEquals(
                "two",
                ((JsonString) array.get(1)).getValue()
        );

        assertEquals(
                "three",
                ((JsonString) array.get(2)).getValue()
        );
    }

    @Test
    void shouldParseEmptyArray() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "items": []
                }
                """);

        JsonArray array = object.getArray("items");

        assertNotNull(array);
        assertEquals(0, array.size());
    }

    @Test
    void shouldParseMixedArray() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "data": [
                        "Hello",
                        42,
                        true,
                        false,
                        null
                    ]
                }
                """);

        JsonArray array = object.getArray("data");

        assertEquals(5, array.size());

        assertInstanceOf(
                JsonString.class,
                array.get(0)
        );

        assertInstanceOf(
                JsonNumber.class,
                array.get(1)
        );

        assertInstanceOf(
                JsonBoolean.class,
                array.get(2)
        );

        assertInstanceOf(
                JsonBoolean.class,
                array.get(3)
        );

        assertInstanceOf(
                JsonNull.class,
                array.get(4)
        );

        assertEquals(
                42,
                ((JsonNumber) array.get(1)).getInt()
        );
    }

    @Test
    void shouldParseNestedObject() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "user": {
                        "name": "Timo",
                        "age": 17,
                        "active": true
                    }
                }
                """);

        JsonObject user = object.getObject("user");

        assertEquals(
                "Timo",
                user.getString("name")
        );

        assertEquals(
                17,
                user.getInt("age")
        );

        assertTrue(
                user.getBoolean("active")
        );
    }

    @Test
    void shouldParseNestedArray() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "data": [
                        [
                            "A",
                            "B"
                        ],
                        [
                            "C",
                            "D"
                        ]
                    ]
                }
                """);

        JsonArray data = object.getArray("data");

        assertEquals(2, data.size());

        JsonArray first =
                (JsonArray) data.get(0);

        JsonArray second =
                (JsonArray) data.get(1);

        assertEquals(2, first.size());
        assertEquals(2, second.size());

        assertEquals(
                "A",
                ((JsonString) first.get(0)).getValue()
        );

        assertEquals(
                "D",
                ((JsonString) second.get(1)).getValue()
        );
    }

    @Test
    void shouldParseMultipleValues() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "name": "Timo",
                    "age": 17,
                    "active": true,
                    "admin": false,
                    "value": null
                }
                """);

        assertEquals(
                "Timo",
                object.getString("name")
        );

        assertEquals(
                17,
                object.getInt("age")
        );

        assertTrue(
                object.getBoolean("active")
        );

        assertFalse(
                object.getBoolean("admin")
        );

        assertInstanceOf(
                JsonNull.class,
                object.get("value")
        );
    }

    @Test
    void shouldParseComplexObject() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "name": "Timo",
                    "age": 17,
                    "active": true,
                    "data": null,
                    "items": [
                        "Java",
                        "JSON"
                    ],
                    "settings": {
                        "enabled": false,
                        "volume": 0.75
                    }
                }
                """);

        assertNotNull(object);

        assertEquals(
                "Timo",
                object.getString("name")
        );

        assertEquals(
                17,
                object.getInt("age")
        );

        assertTrue(
                object.getBoolean("active")
        );

        assertInstanceOf(
                JsonNull.class,
                object.get("data")
        );

        assertEquals(
                2,
                object.getArray("items").size()
        );

        assertFalse(
                object.getObject("settings")
                        .getBoolean("enabled")
        );

        assertEquals(
                0.75,
                object.getObject("settings")
                        .getDouble("volume")
        );
    }

    @Test
    void shouldParseWhitespace() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                
                {
                    "name"    :    "Timo",
                    "age"     :    17,
                    "active"  :    true
                }
                
                """);

        assertEquals(
                "Timo",
                object.getString("name")
        );

        assertEquals(
                17,
                object.getInt("age")
        );

        assertTrue(
                object.getBoolean("active")
        );
    }

    @Test
    void shouldParseStringEscapes() {

        JsonParser parser = new JsonParser(null);

        JsonObject object = parser.parse("""
                {
                    "text": "Hello\\nWorld"
                }
                """);

        assertEquals(
                "Hello\nWorld",
                object.getString("text")
        );
    }

    @Test
    void shouldRejectArrayAsRoot() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("[]")
        );
    }

    @Test
    void shouldRejectStringAsRoot() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("\"Hello\"")
        );
    }

    @Test
    void shouldRejectBooleanAsRoot() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("true")
        );
    }

    @Test
    void shouldRejectNumberAsRoot() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("42")
        );
    }

    @Test
    void shouldRejectNullAsRoot() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("null")
        );
    }

    @Test
    void shouldRejectMissingColon() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("""
                        {
                            "name" "Timo"
                        }
                        """)
        );
    }

    @Test
    void shouldRejectMissingValue() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("""
                        {
                            "name":
                        }
                        """)
        );
    }

    @Test
    void shouldRejectMissingComma() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("""
                        {
                            "name": "Timo"
                            "age": 17
                        }
                        """)
        );
    }

    @Test
    void shouldRejectTrailingCommaInObject() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("""
                        {
                            "name": "Timo",
                        }
                        """)
        );
    }

    @Test
    void shouldRejectTrailingCommaInArray() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("""
                        {
                            "items": [
                                "one",
                                "two",
                            ]
                        }
                        """)
        );
    }

    @Test
    void shouldRejectMissingClosingBrace() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("""
                        {
                            "name": "Timo"
                        """)
        );
    }

    @Test
    void shouldRejectMissingClosingBracket() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("""
                        {
                            "items": [
                                "one",
                                "two"
                        }
                        """)
        );
    }

    @Test
    void shouldRejectUnexpectedCharacter() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                JsonParseException.class,
                () -> parser.parse("""
                        {
                            "name": @invalid
                        }
                        """)
        );
    }

    @Test
    void shouldRejectNullContent() {

        JsonParser parser = new JsonParser(null);

        assertThrows(
                NullPointerException.class,
                () -> parser.parse(null)
        );
    }

    @Test
    void shouldReadJsonFromFile(
            @TempDir Path tempDir
    ) throws IOException {

        Path file = tempDir.resolve("test.json");

        Files.writeString(
                file,
                """
                {
                    "name": "Timo",
                    "age": 17,
                    "active": true
                }
                """
        );

        JsonParser parser =
                new JsonParser(file);

        JsonObject object = parser.parse();

        assertEquals(
                "Timo",
                object.getString("name")
        );

        assertEquals(
                17,
                object.getInt("age")
        );

        assertTrue(
                object.getBoolean("active")
        );
    }

    @Test
    void shouldThrowIOExceptionForMissingFile(
            @TempDir Path tempDir
    ) {

        Path file =
                tempDir.resolve("missing.json");

        JsonParser parser =
                new JsonParser(file);

        assertThrows(
                IOException.class,
                parser::parse
        );
    }

        @Test
        void shouldParseNestedObjectCorrectly() {

        String json = """
                {
                "defaultFilePath": "test",
                "logger": {
                        "loggingType": 1,
                        "filePath": "/logs/",
                        "fileSuffix": "log_",
                        "filePrefix": ".log",
                        "deadline": 30
                }
                }
                """;

        JsonObject object = new JsonParser(null).parse(json);

        System.out.println(object);
        System.out.println(object.get("logger"));

        assertNotNull(object.get("logger"));
        assertInstanceOf(JsonObject.class, object.get("logger"));

        JsonObject logger = object.getObject("logger");

        assertEquals(1, logger.getInt("loggingType"));
        assertEquals("/logs/", logger.getString("filePath"));
        assertEquals("log_", logger.getString("fileSuffix"));
        assertEquals(".log", logger.getString("filePrefix"));
        assertEquals(30, logger.getInt("deadline"));
        }
}