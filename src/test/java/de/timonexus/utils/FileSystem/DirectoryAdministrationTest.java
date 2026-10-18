package de.timonexus.utils.FileSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class DirectoryAdministrationTest {

    @TempDir 
    Path tempDir;
    
    @Test 
    void shouldCreateDirectoryAdministration(){
        String directoryPath = "Test/test";
        DirectoryAdministration directoryAdministration = new DirectoryAdministration(directoryPath);

        assertEquals(directoryPath, directoryAdministration.getDirectoryPath());
    }

    @Test 
    void shouldSetDirectoryPath(){
        String directoryPath = "Test/test";
        DirectoryAdministration directoryAdministration = new DirectoryAdministration(directoryPath);
        assertEquals(directoryPath, directoryAdministration.getDirectoryPath());

        String directoryPath2 = "Test123/test123";
        directoryAdministration.setDirectoryPath(directoryPath2);
        assertEquals(directoryPath2, directoryAdministration.getDirectoryPath());
    }

    @Test 
    void shouldCreateDirectoryIfNotExists(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration("Test123/1234");
        assertTrue(directoryAdministration.createDirectoryIfNotExists());
        assertTrue(Files.exists(directoryAdministration.getPath()));
    }

    @Test 
    void shouldReturnTrueIfAlreadyExists() throws IOException{
        Path existingDirectory = Files.createDirectories(tempDir.resolve("test123/test/"));

        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.resolve("test123/test/").toString());
        assertTrue(directoryAdministration.createDirectoryIfNotExists());
        assertTrue(Files.exists(existingDirectory));
    }

    @Test
    void shouldReturnFalseIfDirectoryCannotBeCreated() throws IOException {
        Path existingFile = Files.createFile(tempDir.resolve("file.txt"));

        DirectoryAdministration directory =
                new DirectoryAdministration(
                        existingFile.resolve("child").toString()
                );

        assertFalse(directory.createDirectoryIfNotExists());
    }
}
