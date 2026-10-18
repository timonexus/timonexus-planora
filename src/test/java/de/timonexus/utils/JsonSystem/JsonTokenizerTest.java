package de.timonexus.utils.JsonSystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class JsonTokenizerTest {

    @Test
    void shouldTokenizeEmptyInput() {

        JsonTokenizer tokenizer = new JsonTokenizer("");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(1, tokens.size());
        assertEquals(JsonTokenType.EOF, tokens.get(0).tokenType());
    }

    @Test
    void shouldTokenizeObject() {

        JsonTokenizer tokenizer = new JsonTokenizer("{}");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(3, tokens.size());

        assertEquals(JsonTokenType.LEFT_BRACE, tokens.get(0).tokenType());
        assertEquals(JsonTokenType.RIGHT_BRACE, tokens.get(1).tokenType());
        assertEquals(JsonTokenType.EOF, tokens.get(2).tokenType());
    }

    @Test
    void shouldTokenizeArray() {

        JsonTokenizer tokenizer = new JsonTokenizer("[]");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(3, tokens.size());

        assertEquals(JsonTokenType.LEFT_BRACKET, tokens.get(0).tokenType());
        assertEquals(JsonTokenType.RIGHT_BRACKET, tokens.get(1).tokenType());
        assertEquals(JsonTokenType.EOF, tokens.get(2).tokenType());
    }

    @Test
    void shouldTokenizeStructuralCharacters() {

        JsonTokenizer tokenizer = new JsonTokenizer("{}[]:,");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(7, tokens.size());

        assertEquals(JsonTokenType.LEFT_BRACE, tokens.get(0).tokenType());
        assertEquals(JsonTokenType.RIGHT_BRACE, tokens.get(1).tokenType());

        assertEquals(JsonTokenType.LEFT_BRACKET, tokens.get(2).tokenType());
        assertEquals(JsonTokenType.RIGHT_BRACKET, tokens.get(3).tokenType());

        assertEquals(JsonTokenType.COLON, tokens.get(4).tokenType());
        assertEquals(JsonTokenType.COMMA, tokens.get(5).tokenType());

        assertEquals(JsonTokenType.EOF, tokens.get(6).tokenType());
    }

    @Test
    void shouldTokenizeString() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("\"Hello World\"");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        JsonToken stringToken = tokens.get(0);

        assertEquals(JsonTokenType.STRING, stringToken.tokenType());
        assertEquals("Hello World", stringToken.value());

        assertEquals(JsonTokenType.EOF, tokens.get(1).tokenType());
    }

    @Test
    void shouldTokenizeEscapedString() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("\"Hello \\\"World\\\"\"");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                JsonTokenType.STRING,
                tokens.get(0).tokenType()
        );

        assertEquals(
                "Hello \"World\"",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldTokenizeStringEscapeSequences() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("\"\\\"\\\\\\/\\b\\f\\n\\r\\t\"");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                "\"\\/\b\f\n\r\t",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldTokenizePositiveInteger() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("12345");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                JsonTokenType.NUMBER,
                tokens.get(0).tokenType()
        );

        assertEquals(
                "12345",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldTokenizeNegativeInteger() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("-12345");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                JsonTokenType.NUMBER,
                tokens.get(0).tokenType()
        );

        assertEquals(
                "-12345",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldTokenizeDecimalNumber() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("123.456");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                JsonTokenType.NUMBER,
                tokens.get(0).tokenType()
        );

        assertEquals(
                "123.456",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldTokenizeNegativeDecimalNumber() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("-123.456");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                JsonTokenType.NUMBER,
                tokens.get(0).tokenType()
        );

        assertEquals(
                "-123.456",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldTokenizeExponent() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("1.5e10");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                JsonTokenType.NUMBER,
                tokens.get(0).tokenType()
        );

        assertEquals(
                "1.5e10",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldTokenizeNegativeExponent() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("1.5e-10");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                JsonTokenType.NUMBER,
                tokens.get(0).tokenType()
        );

        assertEquals(
                "1.5e-10",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldTokenizeBooleanTrue() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("true");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                JsonTokenType.TRUE,
                tokens.get(0).tokenType()
        );

        assertEquals(
                "true",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldTokenizeBooleanFalse() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("false");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                JsonTokenType.FALSE,
                tokens.get(0).tokenType()
        );

        assertEquals(
                "false",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldTokenizeNull() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("null");

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size());

        assertEquals(
                JsonTokenType.NULL,
                tokens.get(0).tokenType()
        );

        assertEquals(
                "null",
                tokens.get(0).value()
        );
    }

    @Test
    void shouldIgnoreWhitespace() {

        JsonTokenizer tokenizer = new JsonTokenizer("""
                
                {
                    "name" : "Timo"
                }
                
                """);

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(6, tokens.size());

        assertEquals(JsonTokenType.LEFT_BRACE, tokens.get(0).tokenType());
        assertEquals(JsonTokenType.STRING, tokens.get(1).tokenType());
        assertEquals(JsonTokenType.COLON, tokens.get(2).tokenType());
        assertEquals(JsonTokenType.STRING, tokens.get(3).tokenType());
        assertEquals(JsonTokenType.RIGHT_BRACE, tokens.get(4).tokenType());
        assertEquals(JsonTokenType.EOF, tokens.get(5).tokenType());
    }

    @Test
    void shouldTokenizeCompleteJsonObject() {

        String json = """
                {
                    "name": "Timo",
                    "age": 17,
                    "active": true,
                    "admin": false,
                    "data": null
                }
                """;

        JsonTokenizer tokenizer = new JsonTokenizer(json);

        List<JsonToken> tokens = tokenizer.tokenize();

        assertEquals(JsonTokenType.LEFT_BRACE, tokens.get(0).tokenType());

        assertEquals(JsonTokenType.STRING, tokens.get(1).tokenType());
        assertEquals("name", tokens.get(1).value());

        assertEquals(JsonTokenType.COLON, tokens.get(2).tokenType());

        assertEquals(JsonTokenType.STRING, tokens.get(3).tokenType());
        assertEquals("Timo", tokens.get(3).value());

        assertEquals(JsonTokenType.COMMA, tokens.get(4).tokenType());

        assertEquals(JsonTokenType.STRING, tokens.get(5).tokenType());
        assertEquals("age", tokens.get(5).value());

        assertEquals(JsonTokenType.COLON, tokens.get(6).tokenType());

        assertEquals(JsonTokenType.NUMBER, tokens.get(7).tokenType());
        assertEquals("17", tokens.get(7).value());

        assertEquals(JsonTokenType.COMMA, tokens.get(8).tokenType());

        assertEquals(JsonTokenType.STRING, tokens.get(9).tokenType());
        assertEquals("active", tokens.get(9).value());

        assertEquals(JsonTokenType.COLON, tokens.get(10).tokenType());

        assertEquals(JsonTokenType.TRUE, tokens.get(11).tokenType());

        assertEquals(JsonTokenType.COMMA, tokens.get(12).tokenType());

        assertEquals(JsonTokenType.STRING, tokens.get(13).tokenType());
        assertEquals("admin", tokens.get(13).value());

        assertEquals(JsonTokenType.COLON, tokens.get(14).tokenType());

        assertEquals(JsonTokenType.FALSE, tokens.get(15).tokenType());

        assertEquals(JsonTokenType.COMMA, tokens.get(16).tokenType());

        assertEquals(JsonTokenType.STRING, tokens.get(17).tokenType());
        assertEquals("data", tokens.get(17).value());

        assertEquals(JsonTokenType.COLON, tokens.get(18).tokenType());

        assertEquals(JsonTokenType.NULL, tokens.get(19).tokenType());

        assertEquals(JsonTokenType.RIGHT_BRACE, tokens.get(20).tokenType());

        assertEquals(JsonTokenType.EOF, tokens.get(21).tokenType());
    }

    @Test
    void shouldRejectUnterminatedString() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("\"Hello World");

        assertThrows(
                JsonParseException.class,
                tokenizer::tokenize
        );
    }

    @Test
    void shouldRejectInvalidEscapeSequence() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("\"Hello \\q\"");

        assertThrows(
                JsonParseException.class,
                tokenizer::tokenize
        );
    }

    @Test
    void shouldRejectInvalidNumber() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("123.");

        assertThrows(
                JsonParseException.class,
                tokenizer::tokenize
        );
    }

    @Test
    void shouldRejectInvalidExponent() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("1.5e");

        assertThrows(
                JsonParseException.class,
                tokenizer::tokenize
        );
    }

    @Test
    void shouldRejectUnexpectedCharacter() {

        JsonTokenizer tokenizer =
                new JsonTokenizer("@");

        assertThrows(
                JsonParseException.class,
                tokenizer::tokenize
        );
    }
}