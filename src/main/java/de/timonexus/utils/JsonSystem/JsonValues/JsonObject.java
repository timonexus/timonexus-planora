package de.timonexus.utils.JsonSystem.JsonValues;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class JsonObject extends JsonValue {

    // Attributes

    private final Map<String, JsonValue> values;


    // Constructor

    public JsonObject() {
        this.values = new LinkedHashMap<>();
    }


    // Methods

    public void put(String key, JsonValue value) {

        Objects.requireNonNull(
                key,
                "JSON object key must not be null."
        );

        Objects.requireNonNull(
                value,
                "JSON object value must not be null."
        );

        values.put(key, value);
    }


    public JsonValue get(String key) {

        return values.get(key);
    }


    public String getString(String path) {

        JsonValue value = getLastJsonValue(path);

        if (!(value instanceof JsonString string)) {

            throw new IllegalStateException(
                    "Value '" + path + "' is not a string."
            );
        }

        return string.getValue();
    }


    public int getInt(String path) {

        JsonValue value = getLastJsonValue(path);

        if (!(value instanceof JsonNumber number)) {

            throw new IllegalStateException(
                    "Value '" + path + "' is not a number."
            );
        }

        return number.getInt();
    }


    public long getLong(String path) {

        JsonValue value = getLastJsonValue(path);

        if (!(value instanceof JsonNumber number)) {

            throw new IllegalStateException(
                    "Value '" + path + "' is not a number."
            );
        }

        return number.getLong();
    }


    public double getDouble(String path) {

        JsonValue value = getLastJsonValue(path);

        if (!(value instanceof JsonNumber number)) {

            throw new IllegalStateException(
                    "Value '" + path + "' is not a number."
            );
        }

        return number.getDouble();
    }


    public boolean getBoolean(String path) {

        JsonValue value = getLastJsonValue(path);

        if (!(value instanceof JsonBoolean booleanValue)) {

            throw new IllegalStateException(
                    "Value '" + path + "' is not a boolean."
            );
        }

        return booleanValue.getValue();
    }


    public JsonObject getObject(String path) {

        JsonValue value = getLastJsonValue(path);

        if (!(value instanceof JsonObject object)) {

            throw new IllegalStateException(
                    "Value '" + path + "' is not an object."
            );
        }

        return object;
    }


    public JsonArray getArray(String path) {

        JsonValue value = getLastJsonValue(path);

        if (!(value instanceof JsonArray array)) {

            throw new IllegalStateException(
                    "Value '" + path + "' is not an array."
            );
        }

        return array;
    }


    public boolean contains(String key) {

        return values.containsKey(key);
    }


    public Map<String, JsonValue> getValues() {

        return Collections.unmodifiableMap(
                new LinkedHashMap<>(values)
        );
    }


    private JsonValue getLastJsonValue(String path) {

        Objects.requireNonNull(
                path,
                "JSON path must not be null."
        );

        if (path.isBlank()) {

            throw new IllegalArgumentException(
                    "JSON path must not be blank."
            );
        }

        String[] pathParts = path.split("\\.", -1);

        JsonObject currentObject = this;

        for (int i = 0; i < pathParts.length - 1; i++) {

            String part = pathParts[i];

            if (part.isBlank()) {

                throw new IllegalArgumentException(
                        "JSON path contains an empty element: '"
                                + path
                                + "'."
                );
            }

            JsonValue value = currentObject.get(part);

            if (!(value instanceof JsonObject object)) {

                throw new IllegalStateException(
                        "Path '" + part + "' in '"
                                + path
                                + "' is not a JSON object."
                );
            }

            currentObject = object;
        }

        String lastPart = pathParts[pathParts.length - 1];

        if (lastPart.isBlank()) {

            throw new IllegalArgumentException(
                    "JSON path contains an empty element: '"
                            + path
                            + "'."
            );
        }

        JsonValue value = currentObject.get(lastPart);

        if (value == null) {

            throw new IllegalStateException(
                    "No value found for JSON path '"
                            + path
                            + "'."
            );
        }

        return value;
    }


    @Override
    public String toString() {

        return values.toString();
    }
}