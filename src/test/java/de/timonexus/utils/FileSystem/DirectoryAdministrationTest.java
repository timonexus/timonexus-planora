package de.timonexus.utils.FileSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

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

    @Test 
    void shouldListAllFiles() throws IOException{
        Files.createFile(tempDir.resolve("Test.txt"));
        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.toString());

        ArrayList<Path> files = directoryAdministration.listAllFiles();

        assertEquals(1, files.size());
        assertTrue(files.contains(tempDir.resolve("Test.txt")));
    }

    @Test 
    void shouldListFiles() throws IOException{
        Files.createFile(tempDir.resolve("Test.txt"));
        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.toString());

        ArrayList<Path> files = directoryAdministration.listFiles();

        assertEquals(1, files.size());
        assertTrue(files.contains(tempDir.resolve("Test.txt")));
    }

    @Test 
    void shouldListJustFiles() throws IOException{
        Files.createFile(tempDir.resolve("Test.txt"));
        Files.createFile(tempDir.resolve("Image.png"));
        Files.createFile(tempDir.resolve("Data.json"));

        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.toString());
        ArrayList<Path> files = directoryAdministration.listFiles();

        assertEquals(3, files.size());
        assertTrue(files.contains(tempDir.resolve("Test.txt")));
        assertTrue(files.contains(tempDir.resolve("Image.png")));
        assertTrue(files.contains(tempDir.resolve("Data.json")));
    }


    @Test 
    void shouldListMultipleFiles() throws IOException{
        Files.createFile(tempDir.resolve("Test.txt"));
        Files.createFile(tempDir.resolve("Image.png"));
        Files.createFile(tempDir.resolve("Data.json"));
        Files.createDirectories(tempDir.resolve("test"));

        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.toString());
        ArrayList<Path> files = directoryAdministration.listAllFiles();

        assertEquals(4, files.size());
        assertTrue(files.contains(tempDir.resolve("Test.txt")));
        assertTrue(files.contains(tempDir.resolve("Image.png")));
        assertTrue(files.contains(tempDir.resolve("Data.json")));
        assertTrue(files.contains(tempDir.resolve("test")));
    }

    @Test
    void shouldListAllFilesRecursively() throws IOException {
        Path subdirectory = Files.createDirectory(tempDir.resolve("test"));

        Files.createFile(tempDir.resolve("Test.txt"));
        Files.createFile(tempDir.resolve("Image.png"));
        Files.createFile(subdirectory.resolve("s_Image.png"));
        Files.createFile(subdirectory.resolve("s_Data.json"));

        DirectoryAdministration directory =
                new DirectoryAdministration(tempDir.toString());

        ArrayList<Path> files = directory.listAllFilesRecursively();

        assertEquals(4, files.size());
        assertTrue(files.contains(tempDir.resolve("Test.txt")));
        assertTrue(files.contains(tempDir.resolve("Image.png")));
        assertTrue(files.contains(subdirectory.resolve("s_Image.png")));
        assertTrue(files.contains(subdirectory.resolve("s_Data.json")));
    }

    @Test
    void shouldListFilesInNestedSubdirectories() throws IOException {
        Path nestedDirectory =
                Files.createDirectories(tempDir.resolve("test/images"));

        Files.createFile(nestedDirectory.resolve("Image.png"));

        DirectoryAdministration directory =
                new DirectoryAdministration(tempDir.toString());

        ArrayList<Path> files = directory.listAllFilesRecursively();

        assertEquals(1, files.size());
        assertTrue(files.contains(nestedDirectory.resolve("Image.png")));
    }

    @Test
    void shouldReturnEmptyListIfDirectoryIsEmptyRecursively() {
        DirectoryAdministration directory =
                new DirectoryAdministration(tempDir.toString());

        ArrayList<Path> files = directory.listAllFilesRecursively();

        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test
    void shouldReturnEmptyListIfDirectoryDoesNotExistRecurively() {
        DirectoryAdministration directory =
                new DirectoryAdministration(
                        tempDir.resolve("missing").toString()
                );

        ArrayList<Path> files = directory.listAllFilesRecursively();

        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test 
    void shouldReturnEmptyListIfDirectoryIsEmpty(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.toString());
        ArrayList<Path> files = directoryAdministration.listAllFiles();

        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test 
    void shouldReturnEmptyListIfDirectoryDoesNotExist(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.resolve("missing").toString());
        ArrayList<Path> files = directoryAdministration.listAllFiles();

        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test 
    void shouldReturnTrueIfDirectoryExists(){
        DirectoryAdministration directoryAdministration = new DirectoryAdministration(tempDir.resolve("missing").toString());
        assertFalse(directoryAdministration.exists());

        directoryAdministration.createDirectoryIfNotExists();
        assertTrue(directoryAdministration.exists());
    }
}
