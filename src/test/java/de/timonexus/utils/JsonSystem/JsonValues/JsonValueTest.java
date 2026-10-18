package de.timonexus.utils.JsonSystem.JsonValues;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonValueTest {

    @Test
    void shouldBeAbstractClass() {

        assertTrue(
                java.lang.reflect.Modifier.isAbstract(
                        JsonValue.class.getModifiers()
                )
        );
    }

    @Test
    void shouldBeSuperclassOfJsonBoolean() {

        JsonBoolean value = new JsonBoolean(true);

        assertInstanceOf(
                JsonValue.class,
                value
        );
    }

    @Test
    void shouldBeSuperclassOfJsonArray() {

        JsonArray value = new JsonArray();

        assertInstanceOf(
                JsonValue.class,
                value
        );
    }
}