package de.timonexus.utils.JsonSystem.JsonValues;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class JsonArrayTest {

    @Test
    void shouldCreateEmptyArray() {

        JsonArray array = new JsonArray();

        assertNotNull(array);
        assertEquals(0, array.size());
    }

    @Test
    void shouldAddValue() {

        JsonArray array = new JsonArray();
        JsonString value = new JsonString("Test");

        array.add(value);

        assertEquals(1, array.size());
        assertSame(value, array.get(0));
    }

    @Test
    void shouldAddMultipleValues() {

        JsonArray array = new JsonArray();

        JsonString first = new JsonString("One");
        JsonString second = new JsonString("Two");
        JsonString third = new JsonString("Three");

        array.add(first);
        array.add(second);
        array.add(third);

        assertEquals(3, array.size());

        assertSame(first, array.get(0));
        assertSame(second, array.get(1));
        assertSame(third, array.get(2));
    }

    @Test
    void shouldReturnValueAtIndex() {

        JsonArray array = new JsonArray();

        JsonString value = new JsonString("Test");
        array.add(value);

        JsonValue result = array.get(0);

        assertSame(value, result);
    }

    @Test
    void shouldReturnCorrectValuesForMultipleIndexes() {

        JsonArray array = new JsonArray();

        JsonString first = new JsonString("First");
        JsonBoolean second = new JsonBoolean(true);
        JsonNull third = new JsonNull();

        array.add(first);
        array.add(second);
        array.add(third);

        assertSame(first, array.get(0));
        assertSame(second, array.get(1));
        assertSame(third, array.get(2));
    }

    @Test
    void shouldReturnCorrectSize() {

        JsonArray array = new JsonArray();

        assertEquals(0, array.size());

        array.add(new JsonString("One"));
        assertEquals(1, array.size());

        array.add(new JsonString("Two"));
        assertEquals(2, array.size());

        array.add(new JsonString("Three"));
        assertEquals(3, array.size());
    }

    @Test
    void shouldReturnCopyOfValues() {

        JsonArray array = new JsonArray();

        JsonString first = new JsonString("One");
        JsonString second = new JsonString("Two");

        array.add(first);
        array.add(second);

        List<JsonValue> values = array.getValues();

        assertEquals(2, values.size());
        assertSame(first, values.get(0));
        assertSame(second, values.get(1));
    }

    @Test
    void shouldReturnUnmodifiableList() {

        JsonArray array = new JsonArray();

        array.add(new JsonString("Test"));

        List<JsonValue> values = array.getValues();

        assertThrows(
                UnsupportedOperationException.class,
                () -> values.add(new JsonString("Another"))
        );
    }

    @Test
    void shouldNotExposeInternalList() {

        JsonArray array = new JsonArray();

        array.add(new JsonString("Test"));

        List<JsonValue> values = array.getValues();

        assertThrows(
                UnsupportedOperationException.class,
                values::clear
        );

        assertEquals(1, array.size());
    }

    @Test
    void shouldThrowExceptionForInvalidIndex() {

        JsonArray array = new JsonArray();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(0)
        );
    }

    @Test
    void shouldThrowExceptionForNegativeIndex() {

        JsonArray array = new JsonArray();

        array.add(new JsonString("Test"));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(-1)
        );
    }

    @Test
    void shouldAcceptNullValue() {

        JsonArray array = new JsonArray();

        array.add(null);

        assertEquals(1, array.size());
        assertNull(array.get(0));
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {

        JsonArray array = new JsonArray();

        array.add(new JsonString("One"));
        array.add(new JsonString("Two"));

        assertEquals(
                "[One, Two]",
                array.toString()
        );
    }

    @Test
    void shouldReturnEmptyStringRepresentationForEmptyArray() {

        JsonArray array = new JsonArray();

        assertEquals(
                "[]",
                array.toString()
        );
    }
}