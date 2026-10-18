package de.timonexus.utils.DataSystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DataFileAdministratorTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldSetFilePathInConstructor() {
        Path expectedPath = tempDir.resolve("config.json");

        DataFileAdministrator administrator =
                new DataFileAdministrator(expectedPath);

        assertEquals(expectedPath, administrator.getFilePath());
    }

    @Test
    void shouldSetDefaultFilePath() {
        DataFileAdministrator administrator =
                new DataFileAdministrator();

        assertEquals(
                Path.of("data\\data_filePaths.json"),
                administrator.getFilePath()
        );
    }

    @Test
    void shouldReturnConfiguredValue() throws Exception {
        Path configPath = tempDir.resolve("config.json");

        Files.writeString(configPath, """
                {
                    "filepaths": {
                        "MainFrame": "data/main.json"
                    }
                }
                """);

        DataFileAdministrator administrator =
                new DataFileAdministrator(configPath);

        assertEquals(
                "data/main.json",
                administrator.getFilePath("filepaths.MainFrame")
        );
    }

    @Test
    void shouldReturnEmptyStringWhenFileDoesNotExist() {
        Path configPath = tempDir.resolve("missing.json");

        DataFileAdministrator administrator =
                new DataFileAdministrator(configPath);

        assertEquals("", administrator.getFilePath("filepaths.MainFrame"));
    }

    @Test
    void shouldReturnEmptyStringWhenJsonIsInvalid() throws Exception {
        Path configPath = tempDir.resolve("invalid.json");

        Files.writeString(configPath, "{ invalid JSON }");

        DataFileAdministrator administrator =
                new DataFileAdministrator(configPath);

        assertEquals("", administrator.getFilePath("filepaths.MainFrame"));
    }

    @Test
    void shouldUpdateFilePathWithSetter() {
        DataFileAdministrator administrator =
                new DataFileAdministrator(tempDir.resolve("old.json"));

        Path expectedPath = tempDir.resolve("new.json");
        administrator.setFilePath(expectedPath);

        assertEquals(expectedPath, administrator.getFilePath());
    }
}