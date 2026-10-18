package de.timonexus.utils.JsonSystem.JsonValues;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonNullTest {

    @Test
    void shouldReturnNullAsString() {

        JsonNull jsonNull = new JsonNull();

        assertEquals(
                "null",
                jsonNull.toString()
        );
    }

    @Test
    void shouldExtendJsonValue() {

        JsonNull jsonNull = new JsonNull();

        assertInstanceOf(
                JsonValue.class,
                jsonNull
        );
    }
}