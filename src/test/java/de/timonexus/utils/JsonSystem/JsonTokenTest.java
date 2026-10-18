package de.timonexus.utils.JsonSystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonTokenTest {

    @Test
    void shouldCreateToken() {

        JsonToken token = new JsonToken(
                JsonTokenType.STRING,
                "Hello World",
                10
        );

        assertEquals(JsonTokenType.STRING, token.tokenType());
        assertEquals("Hello World", token.value());
        assertEquals(10, token.position());
    }

    @Test
    void shouldCreateTokenWithoutValue() {

        JsonToken token = new JsonToken(
                JsonTokenType.LEFT_BRACE,
                null,
                0
        );

        assertEquals(JsonTokenType.LEFT_BRACE, token.tokenType());
        assertNull(token.value());
        assertEquals(0, token.position());
    }

    @Test
    void shouldCreateNumberToken() {

        JsonToken token = new JsonToken(
                JsonTokenType.NUMBER,
                "-123.45",
                5
        );

        assertEquals(JsonTokenType.NUMBER, token.tokenType());
        assertEquals("-123.45", token.value());
        assertEquals(5, token.position());
    }

    @Test
    void shouldCreateBooleanToken() {

        JsonToken token = new JsonToken(
                JsonTokenType.TRUE,
                "true",
                20
        );

        assertEquals(JsonTokenType.TRUE, token.tokenType());
        assertEquals("true", token.value());
        assertEquals(20, token.position());
    }

    @Test
    void shouldCreateNullToken() {

        JsonToken token = new JsonToken(
                JsonTokenType.NULL,
                "null",
                15
        );

        assertEquals(JsonTokenType.NULL, token.tokenType());
        assertEquals("null", token.value());
        assertEquals(15, token.position());
    }

    @Test
    void shouldFormatToStringCorrectly() {

        JsonToken token = new JsonToken(
                JsonTokenType.STRING,
                "Hello",
                0
        );

        assertEquals(
                "STRING(Hello)",
                token.toString()
        );
    }

    @Test
    void shouldFormatTokenWithoutValueCorrectly() {

        JsonToken token = new JsonToken(
                JsonTokenType.LEFT_BRACE,
                null,
                0
        );

        assertEquals(
                "LEFT_BRACE(null)",
                token.toString()
        );
    }

    @Test
    void shouldCompareEqualTokens() {

        JsonToken first = new JsonToken(
                JsonTokenType.STRING,
                "Hello",
                5
        );

        JsonToken second = new JsonToken(
                JsonTokenType.STRING,
                "Hello",
                5
        );

        assertEquals(first, second);
    }

    @Test
    void shouldNotCompareDifferentTokensAsEqual() {

        JsonToken first = new JsonToken(
                JsonTokenType.STRING,
                "Hello",
                5
        );

        JsonToken second = new JsonToken(
                JsonTokenType.STRING,
                "World",
                5
        );

        assertNotEquals(first, second);
    }
}