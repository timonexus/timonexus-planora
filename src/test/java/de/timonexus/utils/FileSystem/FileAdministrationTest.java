package de.timonexus.utils.FileSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileAdministrationTest {

    @TempDir 
    Path tempDir;
    
    @Test 
    void shouldCreateFileAdministration(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration("test/test");
        FileAdministration fileAdministration = new FileAdministration(directoryAdministration, "Test.jpg");

        assertEquals("Test.jpg", fileAdministration.getFileName());
        assertEquals(directoryAdministration, fileAdministration.getDirectory());
    }

    @Test 
    void shouldSetDirectory(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration("test/test");
        FileAdministration fileAdministration = new FileAdministration(directoryAdministration, "Test.jpg");
        assertEquals(directoryAdministration, fileAdministration.getDirectory());

        DirectoryAdministration directoryAdministration2 = new DirectoryAdministration("test/test");
        fileAdministration.setDirectory(directoryAdministration2);
        assertEquals(directoryAdministration2, fileAdministration.getDirectory());
    }

    @Test
    void shouldSetFileName(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration("test/test");
        FileAdministration fileAdministration = new FileAdministration(directoryAdministration, "Test.jpg");
        assertEquals("Test.jpg", fileAdministration.getFileName());

        fileAdministration.setFileName("Test123.jpg");
        assertEquals("Test123.jpg", fileAdministration.getFileName());
    }

    @Test 
    void shouldInitializePath(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration("test/test");
        FileAdministration fileAdministration = new FileAdministration(directoryAdministration, "Test.jpg");

        assertEquals("test\\test\\Test.jpg", fileAdministration.getPath().toString());
    }

    @Test
    void shouldSetPath(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration("test/test");
        FileAdministration fileAdministration = new FileAdministration(directoryAdministration, "Test.jpg");
        assertEquals("test\\test\\Test.jpg", fileAdministration.getPath().toString());

        fileAdministration.setPath(Path.of("Test1234/1234"));
        assertEquals("Test1234\\1234", fileAdministration.getPath().toString());
    }

    @Test 
    void shouldCreateFileIfNotExists(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.toString());
        assertTrue(directoryAdministration.createDirectoryIfNotExists());
        FileAdministration file = new FileAdministration(directoryAdministration, "Test.txt");
        assertTrue(file.createFileIfNotExists());
        assertTrue(Files.exists(file.getPath()));
    }

    @Test 
    void shouldReturnTrueIfFileAlreadyExists() throws IOException{
        Path existingFile = Files.createFile(tempDir.resolve("Test.txt"));

        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.toString());
        directoryAdministration.createDirectoryIfNotExists();
        FileAdministration file = new FileAdministration(directoryAdministration, "Test.txt");
        assertTrue(file.createFileIfNotExists());
        assertTrue(Files.exists(existingFile));
    }

    @Test 
    void shouldReturnFalseIfFileCannotBeCreated(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.toString());
        directoryAdministration.createDirectoryIfNotExists();
        FileAdministration file = new FileAdministration(directoryAdministration, "missing/Test.txt");
        assertFalse(file.createFileIfNotExists());
    }
}
