package de.timonexus.utils.JsonSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class JsonTokenTypeTest {
    
    @Test
    void shouldContainAllTokenTypes() {

        assertEquals(12,JsonTokenType.values().length);

        assertNotNull(JsonTokenType.valueOf("LEFT_BRACE"));
        assertNotNull(JsonTokenType.valueOf("RIGHT_BRACE"));
        assertNotNull(JsonTokenType.valueOf("LEFT_BRACKET"));
        assertNotNull(JsonTokenType.valueOf("RIGHT_BRACKET"));
        assertNotNull(JsonTokenType.valueOf("COLON"));
        assertNotNull(JsonTokenType.valueOf("COMMA"));
        assertNotNull(JsonTokenType.valueOf("STRING"));
        assertNotNull(JsonTokenType.valueOf("NUMBER"));
        assertNotNull(JsonTokenType.valueOf("TRUE"));
        assertNotNull(JsonTokenType.valueOf("FALSE"));
        assertNotNull(JsonTokenType.valueOf("NULL"));
        assertNotNull(JsonTokenType.valueOf("EOF"));
    }
}
