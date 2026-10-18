package de.timonexus.utils.JsonSystem.JsonValues;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonStringTest {

    @Test
    void shouldCreateStringValue() {

        JsonString jsonString = new JsonString("Hello");

        assertEquals(
                "Hello",
                jsonString.getValue()
        );
    }

    @Test
    void shouldReturnCorrectValue() {

        String expected = "Timo";

        JsonString jsonString = new JsonString(expected);

        assertEquals(
                expected,
                jsonString.getValue()
        );
    }

    @Test
    void shouldReturnEmptyString() {

        JsonString jsonString = new JsonString("");

        assertEquals(
                "",
                jsonString.getValue()
        );
    }

    @Test
    void shouldAcceptNullValue() {

        JsonString jsonString = new JsonString(null);

        assertNull(jsonString.getValue());
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {

        JsonString jsonString = new JsonString("Hello World");

        assertEquals(
                "Hello World",
                jsonString.toString()
        );
    }

    @Test
    void shouldReturnEmptyStringRepresentation() {

        JsonString jsonString = new JsonString("");

        assertEquals(
                "",
                jsonString.toString()
        );
    }

    @Test
    void shouldExtendJsonValue() {

        JsonString jsonString = new JsonString("Test");

        assertInstanceOf(
                JsonValue.class,
                jsonString
        );
    }
}