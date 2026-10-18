package de.timonexus.utils.JsonSystem;

import java.util.ArrayList;
import java.util.List;

public class JsonTokenizer{

    // attributes
    private final String input;
    private int position;

    // constructor
    public JsonTokenizer(String input){
        this.input = input;
        this.position = 0;
    }

    public List<JsonToken> tokenize(){
        List<JsonToken> tokens = new ArrayList<>();


        while(position < this.input.length()){
            skipWhiteSpace();
            if(position >= input.length()) break;

            char current = input.charAt(position);

            switch (current) {
                case '{':
                    tokens.add(new JsonToken(JsonTokenType.LEFT_BRACE, null, position));
                    position++;
                    break;
                case '}':
                    tokens.add(new JsonToken(JsonTokenType.RIGHT_BRACE, null, position));
                    position++;
                    break;
                case '[':
                    tokens.add(new JsonToken(JsonTokenType.LEFT_BRACKET, null, position));
                    position++;
                    break;
                case ']':
                    tokens.add(new JsonToken(JsonTokenType.RIGHT_BRACKET, null, position));
                    position++;
                    break;
                case ':':
                    tokens.add(new JsonToken(JsonTokenType.COLON, null, position));
                    position++;
                    break;
                case ',':
                    tokens.add(new JsonToken(JsonTokenType.COMMA, null, position));
                    position++;
                    break;
                case '"':
                    tokens.add(readString());
                    break;
                case '-':
                    tokens.add(readNumber());
                    break;
                default:
                    if(Character.isDigit(current)){
                        tokens.add(readNumber());
                    }else if(input.startsWith("true", position)){
                        tokens.add(new JsonToken(JsonTokenType.TRUE, "true", position));
                        position += 4;
                    }else if(input.startsWith("false", position)){
                        tokens.add(new JsonToken(JsonTokenType.FALSE, "false", position));
                        position += 5;
                    }else if(input.startsWith("null", position)){
                        tokens.add(new JsonToken(JsonTokenType.NULL, "null", position));
                        position += 4;
                    }else {
                        throw new JsonParseException("Unexpected character '" + current + "' at position " + position);
                    }
            }
        }
        tokens.add(new JsonToken(JsonTokenType.EOF, null, position));
        return tokens;
    }

    private void skipWhiteSpace(){
        while(position < input.length() && Character.isWhitespace(input.charAt(position))){
            position++;
        }
    }

    private JsonToken readString(){
        int startPosition = position;
        position++;

        StringBuilder value = new StringBuilder();
        while(position < input.length()){
            char current = input.charAt(position);
            if(current == '"'){
                position++;
                return new JsonToken(JsonTokenType.STRING, value.toString(), startPosition);
            }
            if(current == '\\'){
                position++;
                if(position >= input.length()){
                    throw new JsonParseException("Unterminated string at position " + startPosition);
                }

                char escaped = input.charAt(position);
                switch (escaped) {
                    case '"':
                        value.append('"');
                        break;
                    case '\\':
                        value.append('\\');
                        break;
                    case '/':
                        value.append('/');
                        break;
                    case 'b':
                        value.append('\b');
                        break;
                    case 'f':
                        value.append('\f');
                        break;
                    case 'n':
                        value.append('\n');
                        break;
                    case 'r':
                        value.append('\r');
                        break;
                    case 't':
                        value.append('\t');
                        break;
                
                    default:
                        throw new JsonParseException("Invalid escape sequence '\\" + escaped + "'at position " + position);
                }
            } else{
                value.append(current);
            }

            position++;
        }

        throw new JsonParseException("Unterminated string at position " + startPosition);
    }

    private JsonToken readNumber() {

        int startPosition = position;

        // Optionales Minus
        if (input.charAt(position) == '-') {
            position++;
        }

        // Mindestens eine Ziffer
        if (position >= input.length()
                || !Character.isDigit(input.charAt(position))) {

            throw new JsonParseException(
                    "Invalid number at position " + startPosition
            );
        }

        // Ganzzahliger Teil
        while (position < input.length()
                && Character.isDigit(input.charAt(position))) {

            position++;
        }

        // Optionaler Dezimalteil
        if (position < input.length()
                && input.charAt(position) == '.') {

            position++;

            // Nach dem Punkt muss mindestens eine Ziffer kommen
            if (position >= input.length()
                    || !Character.isDigit(input.charAt(position))) {

                throw new JsonParseException(
                        "Invalid decimal number at position "
                                + startPosition
                );
            }

            while (position < input.length()
                    && Character.isDigit(input.charAt(position))) {

                position++;
            }
        }

        // Optionaler Exponent
        if (position < input.length()
                && (input.charAt(position) == 'e'
                || input.charAt(position) == 'E')) {

            position++;

            // Optionales + oder -
            if (position < input.length()
                    && (input.charAt(position) == '+'
                    || input.charAt(position) == '-')) {

                position++;
            }

            // Exponent benötigt mindestens eine Ziffer
            if (position >= input.length()
                    || !Character.isDigit(input.charAt(position))) {

                throw new JsonParseException(
                        "Invalid exponent at position "
                                + startPosition
                );
            }

            while (position < input.length()
                    && Character.isDigit(input.charAt(position))) {

                position++;
            }
        }

        return new JsonToken(
                JsonTokenType.NUMBER,
                input.substring(startPosition, position),
                startPosition
        );
    }
}