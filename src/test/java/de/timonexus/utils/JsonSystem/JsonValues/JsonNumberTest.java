package de.timonexus.utils.JsonSystem.JsonValues;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonNumberTest {

    @Test
    void shouldCreateNumberValue() {

        JsonNumber number = new JsonNumber("42");

        assertEquals(
                "42",
                number.getValue()
        );
    }

    @Test
    void shouldReturnCorrectValue() {

        JsonNumber number = new JsonNumber("123456");

        assertEquals(
                "123456",
                number.getValue()
        );
    }

    @Test
    void shouldAcceptNegativeNumber() {

        JsonNumber number = new JsonNumber("-42");

        assertEquals(
                "-42",
                number.getValue()
        );
    }

    @Test
    void shouldAcceptDecimalNumber() {

        JsonNumber number = new JsonNumber("12.5");

        assertEquals(
                "12.5",
                number.getValue()
        );
    }

    @Test
    void shouldGetInt() {

        JsonNumber number = new JsonNumber("42");

        assertEquals(
                42,
                number.getInt()
        );
    }

    @Test
    void shouldGetNegativeInt() {

        JsonNumber number = new JsonNumber("-42");

        assertEquals(
                -42,
                number.getInt()
        );
    }

    @Test
    void shouldGetLong() {

        JsonNumber number = new JsonNumber("1234567890123");

        assertEquals(
                1234567890123L,
                number.getLong()
        );
    }

    @Test
    void shouldGetNegativeLong() {

        JsonNumber number = new JsonNumber("-1234567890123");

        assertEquals(
                -1234567890123L,
                number.getLong()
        );
    }

    @Test
    void shouldGetDouble() {

        JsonNumber number = new JsonNumber("12.5");

        assertEquals(
                12.5,
                number.getDouble()
        );
    }

    @Test
    void shouldGetNegativeDouble() {

        JsonNumber number = new JsonNumber("-12.5");

        assertEquals(
                -12.5,
                number.getDouble()
        );
    }

    @Test
    void shouldGetDoubleWithExponent() {

        JsonNumber number = new JsonNumber("1.5e3");

        assertEquals(
                1500.0,
                number.getDouble()
        );
    }

    @Test
    void shouldThrowExceptionForInvalidInt() {

        JsonNumber number = new JsonNumber("12.5");

        assertThrows(
                NumberFormatException.class,
                number::getInt
        );
    }

    @Test
    void shouldThrowExceptionForInvalidLong() {

        JsonNumber number = new JsonNumber("12.5");

        assertThrows(
                NumberFormatException.class,
                number::getLong
        );
    }

    @Test
    void shouldThrowExceptionForInvalidDouble() {

        JsonNumber number = new JsonNumber("not-a-number");

        assertThrows(
                NumberFormatException.class,
                number::getDouble
        );
    }

    @Test
    void shouldThrowExceptionForIntegerOverflow() {

        JsonNumber number = new JsonNumber("999999999999999999999999");

        assertThrows(
                NumberFormatException.class,
                number::getInt
        );
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {

        JsonNumber number = new JsonNumber("42.5");

        assertEquals(
                "42.5",
                number.toString()
        );
    }

    @Test
    void shouldReturnZero() {

        JsonNumber number = new JsonNumber("0");

        assertEquals(
                0,
                number.getInt()
        );

        assertEquals(
                0L,
                number.getLong()
        );

        assertEquals(
                0.0,
                number.getDouble()
        );
    }

    @Test
    void shouldAcceptNullValue() {

        JsonNumber number = new JsonNumber(null);

        assertNull(number.getValue());
    }

    @Test
    void shouldExtendJsonValue() {

        JsonNumber number = new JsonNumber("42");

        assertInstanceOf(
                JsonValue.class,
                number
        );
    }
}