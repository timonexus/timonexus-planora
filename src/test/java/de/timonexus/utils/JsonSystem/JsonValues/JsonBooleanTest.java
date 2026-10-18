package de.timonexus.utils.JsonSystem.JsonValues;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonBooleanTest {

    @Test
    void shouldCreateTrueValue() {

        JsonBoolean jsonBoolean = new JsonBoolean(true);

        assertTrue(jsonBoolean.getValue());
    }

    @Test
    void shouldCreateFalseValue() {

        JsonBoolean jsonBoolean = new JsonBoolean(false);

        assertFalse(jsonBoolean.getValue());
    }

    @Test
    void shouldReturnCorrectValue() {

        JsonBoolean jsonBoolean = new JsonBoolean(true);

        boolean value = jsonBoolean.getValue();

        assertEquals(true, value);
    }

    @Test
    void shouldReturnTrueAsString() {

        JsonBoolean jsonBoolean = new JsonBoolean(true);

        assertEquals(
                "true",
                jsonBoolean.toString()
        );
    }

    @Test
    void shouldReturnFalseAsString() {

        JsonBoolean jsonBoolean = new JsonBoolean(false);

        assertEquals(
                "false",
                jsonBoolean.toString()
        );
    }

    @Test
    void shouldExtendJsonValue() {

        JsonBoolean jsonBoolean = new JsonBoolean(true);

        assertInstanceOf(
                JsonValue.class,
                jsonBoolean
        );
    }
}