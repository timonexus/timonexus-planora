package de.timonexus.utils.TimeSystem.Formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class FileFormatterTest {
    
    @Test
    void testFormat() {

        FileFormatter formatter = new FileFormatter();
        LocalDateTime dateTime = LocalDateTime.of(2026, 9, 8, 14, 30, 15);

        String result = formatter.format(dateTime);
        assertEquals("2026-09-08",result);
    }

    @Test
    void testFormatNow() {
        FileFormatter formatter = new FileFormatter();

        String result = formatter.formatNow();
        assertNotNull(result);
        assertEquals(10, result.length());
    }
}
