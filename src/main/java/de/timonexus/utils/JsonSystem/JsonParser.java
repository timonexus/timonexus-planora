package de.timonexus.utils.JsonSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import de.timonexus.utils.JsonSystem.JsonValues.JsonArray;
import de.timonexus.utils.JsonSystem.JsonValues.JsonBoolean;
import de.timonexus.utils.JsonSystem.JsonValues.JsonNull;
import de.timonexus.utils.JsonSystem.JsonValues.JsonNumber;
import de.timonexus.utils.JsonSystem.JsonValues.JsonObject;
import de.timonexus.utils.JsonSystem.JsonValues.JsonString;
import de.timonexus.utils.JsonSystem.JsonValues.JsonValue;

public class JsonParser {

    // Attributes

    private final Path filePath;

    private List<JsonToken> tokens;
    private int position;


    // Constructor

    public JsonParser(Path filePath) {
        this.filePath = filePath;
    }


    // Public methods

    public JsonObject parse() throws IOException {

        if (filePath == null) {
            throw new IllegalStateException(
                    "No file path has been configured."
            );
        }

        String content = Files.readString(filePath);

        return parse(content);
    }

    public JsonObject parse(String content) {

        Objects.requireNonNull(
                content,
                "JSON content must not be null."
        );

        initialize(content);

        JsonValue value = parseValue();

        if (!(value instanceof JsonObject object)) {
            throw new JsonParseException(
                    "Root element must be a JSON object."
            );
        }

        expect(JsonTokenType.EOF);

        return object;
    }


    // Initialization

    private void initialize(String content) {

        JsonTokenizer tokenizer = new JsonTokenizer(content);

        this.tokens = tokenizer.tokenize();
        this.position = 0;
    }


    // Value parsing

    private JsonValue parseValue() {

        JsonToken token = currentToken();

        return switch (token.tokenType()) {

            case LEFT_BRACE -> parseObject();

            case LEFT_BRACKET -> parseArray();

            case STRING -> {
                advance();
                yield new JsonString(token.value());
            }

            case NUMBER -> {
                advance();
                yield new JsonNumber(token.value());
            }

            case TRUE -> {
                advance();
                yield new JsonBoolean(true);
            }

            case FALSE -> {
                advance();
                yield new JsonBoolean(false);
            }

            case NULL -> {
                advance();
                yield new JsonNull();
            }

            default -> throw new JsonParseException(
                    "Unexpected token "
                            + token.tokenType()
                            + " at position "
                            + token.position()
            );
        };
    }


    // Object parsing

    private JsonObject parseObject() {

        expect(JsonTokenType.LEFT_BRACE);

        JsonObject object = new JsonObject();

        if (check(JsonTokenType.RIGHT_BRACE)) {
            advance();
            return object;
        }

        while (true) {

            JsonToken keyToken =
                    expect(JsonTokenType.STRING);

            String key = keyToken.value();

            expect(JsonTokenType.COLON);

            JsonValue value = parseValue();

            object.put(key, value);

            if (check(JsonTokenType.RIGHT_BRACE)) {
                advance();
                return object;
            }

            expect(JsonTokenType.COMMA);
        }
    }


    // Array parsing

    private JsonArray parseArray() {

        expect(JsonTokenType.LEFT_BRACKET);

        JsonArray array = new JsonArray();

        if (check(JsonTokenType.RIGHT_BRACKET)) {
            advance();
            return array;
        }

        while (true) {

            JsonValue value = parseValue();

            array.add(value);

            if (check(JsonTokenType.RIGHT_BRACKET)) {
                advance();
                return array;
            }

            expect(JsonTokenType.COMMA);
        }
    }


    // Token handling

    private JsonToken currentToken() {

        if (position >= tokens.size()) {
            throw new JsonParseException(
                    "Unexpected end of JSON input."
            );
        }

        return tokens.get(position);
    }

    private void advance() {

        if (position < tokens.size()) {
            position++;
        }
    }

    private boolean check(JsonTokenType type) {

        return currentToken().tokenType() == type;
    }

    private JsonToken expect(JsonTokenType type) {

        JsonToken token = currentToken();

        if (token.tokenType() != type) {

            throw new JsonParseException(
                    "Expected "
                            + type
                            + " but found "
                            + token.tokenType()
                            + " at position "
                            + token.position()
            );
        }

        advance();

        return token;
    }
}