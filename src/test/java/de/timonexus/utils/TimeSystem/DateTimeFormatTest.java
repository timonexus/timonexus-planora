package de.timonexus.utils.TimeSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DateTimeFormatTest {

    // format()

    @Test
    void testFormat() {

        DateTimeFormat formatter =
                new DateTimeFormat("dd.MM.yyyy HH:mm:ss");

        LocalDateTime dateTime =
                LocalDateTime.of(2026, 9, 8, 14, 30, 15);

        String result = formatter.format(dateTime);

        assertEquals("08.09.2026 14:30:15", result);
    }

    @Test
    void testFormatWithDifferentPattern() {

        DateTimeFormat formatter =
                new DateTimeFormat("yyyy-MM-dd");

        LocalDateTime dateTime =
                LocalDateTime.of(2026, 9, 8, 14, 30, 15);

        String result = formatter.format(dateTime);

        assertEquals("2026-09-08", result);
    }

    // formatNow()

    @Test
    void testFormatNow() {

        DateTimeFormat formatter =
                new DateTimeFormat("yyyy-MM-dd HH:mm");

        LocalDateTime before = LocalDateTime.now();

        String result = formatter.formatNow();

        LocalDateTime after = LocalDateTime.now();

        LocalDateTime formattedDateTime =
                LocalDateTime.parse(
                        result,
                        java.time.format.DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd HH:mm"
                        )
                );

        assertTrue(
                !formattedDateTime.isBefore(
                        before.withSecond(0).withNano(0)
                )
        );

        assertTrue(
                !formattedDateTime.isAfter(
                        after.withSecond(0).withNano(0)
                )
        );
    }

    // Invalid pattern

    @Test
    void testInvalidPattern() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new DateTimeFormat("[invalid")
        );
    }

    // Null pattern

    @Test
    void testNullPattern() {

        assertThrows(
                NullPointerException.class,
                () -> new DateTimeFormat(null)
        );
    }
}