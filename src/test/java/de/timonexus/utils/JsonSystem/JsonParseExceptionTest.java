package de.timonexus.utils.JsonSystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonParseExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {

        String message = "Unexpected token at position 5";

        JsonParseException exception =
                new JsonParseException(message);

        assertEquals(
                message,
                exception.getMessage()
        );
    }

    @Test
    void shouldBeRuntimeException() {

        JsonParseException exception =
                new JsonParseException("Test");

        assertInstanceOf(
                RuntimeException.class,
                exception
        );
    }

    @Test
    void shouldThrowException() {

        assertThrows(
                JsonParseException.class,
                () -> {
                    throw new JsonParseException(
                            "Invalid JSON"
                    );
                }
        );
    }

    @Test
    void shouldHandleEmptyMessage() {

        JsonParseException exception =
                new JsonParseException("");

        assertEquals(
                "",
                exception.getMessage()
        );
    }

    @Test
    void shouldHandleNullMessage() {

        JsonParseException exception =
                new JsonParseException(null);

        assertNull(exception.getMessage());
    }
}