package de.timonexus.utils.DataSystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataAdministratorTest {

    @Test
    void shouldReturnEmptyStringWhenConfigurationCannotBeLoaded() {
        DataAdministrator administrator = new DataAdministrator();

        String result = administrator.getValue("unknown.value");

        assertEquals("", result);
    }

    @Test
    void shouldReturnEmptyStringWhenValueDoesNotExist() {
        DataAdministrator administrator = new DataAdministrator();

        String result = administrator.getValue("unknown.value");

        assertEquals("", result);
    }
}
