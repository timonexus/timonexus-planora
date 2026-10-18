package de.timonexus.utils.FileSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    @Test 
    void shouldReadAllLines() throws IOException{
        Path filePath = tempDir.resolve("Test.txt");

        Files.write(filePath, List.of(
            "Erste Zeile",
            "Zweite Zeile",
            "Dritte Zeile"
        ));

        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "Test.txt");
        ArrayList<String> content = fileAdministration.read();

        assertEquals(3, content.size());
        assertEquals("Erste Zeile", content.get(0));
        assertEquals("Zweite Zeile", content.get(1));
        assertEquals("Dritte Zeile", content.get(2));
    }

    @Test 
    void shouldReturnEmptyListIfFileIsEmpty() throws IOException{
        Files.createFile(tempDir.resolve("Test.txt"));

        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "Test.txt");
        ArrayList<String> content = fileAdministration.read();

        assertNotNull(content);
        assertTrue(content.isEmpty());
    }

    @Test 
    void shouldReturnEmptyListIfFileDoesNotExist(){
        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "Test.txt");
        ArrayList<String> content = fileAdministration.read();

        assertNotNull(content);
        assertTrue(content.isEmpty());
    }

    @Test 
    void shouldReadSpecialCharacters() throws IOException{
        Path filePath = tempDir.resolve("Test.txt");
        Files.writeString(filePath, "Grüße aus Bayern!\nÄÖÜ äöü ß");

        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "Test.txt");
        ArrayList<String> content = fileAdministration.read();

        assertEquals(2, content.size());
        assertEquals("Grüße aus Bayern!", content.get(0));
        assertEquals("ÄÖÜ äöü ß", content.get(1));
    }

    @Test 
    void shouldWriteText() throws IOException{
        Files.createFile(tempDir.resolve("Test.txt"));

        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "Test.txt");

        assertTrue(fileAdministration.write("Hello World!"));
        assertEquals("Hello World!", Files.readString(fileAdministration.getPath()));
    }

    @Test 
    void shouldOverrideExistingContent() throws IOException{
        Path filePath = tempDir.resolve("Test.txt");
        Files.writeString(filePath, "Alter Inhalt");

        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "Test.txt");

        assertTrue(fileAdministration.write("Neuer Inhalt"));
        assertEquals("Neuer Inhalt", Files.readString(filePath));
    }

    @Test 
    void shouldWriteEmptyText() throws IOException{
        Files.createFile(tempDir.resolve("Test.txt"));
        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "Test.txt");

        assertTrue(fileAdministration.write(""));
        assertEquals("", Files.readString(fileAdministration.getPath()));
    }

    @Test 
    void shouldReturnFalseIfFileCannotBeWritten(){
        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "missing/Text.txt");
        
        assertFalse(fileAdministration.write("Hello World!"));
    }

    @Test 
    void shouldAppendText() throws IOException{
        Path filePath = tempDir.resolve("Test.txt");
        Files.writeString(filePath, "Erste Zeile" + System.lineSeparator());

        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "Test.txt");
        assertTrue(fileAdministration.append("Zweite Zeile"));
        assertEquals( "Erste Zeile" + System.lineSeparator() + "Zweite Zeile" + System.lineSeparator(), Files.readString(filePath));
    }

    @Test
    void shouldPreserveExistingContent() throws IOException {
        Path filePath = tempDir.resolve("Test.txt");
        Files.writeString(filePath, "Alter Inhalt" + System.lineSeparator());

        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()),"Test.txt");

        assertTrue(fileAdministration.append("Neuer Inhalt"));
        assertEquals("Alter Inhalt" + System.lineSeparator() + "Neuer Inhalt" + System.lineSeparator(), Files.readString(filePath));
    }

    @Test
    void shouldAppendEmptyText() throws IOException {
        Path filePath = tempDir.resolve("Test.txt");
        Files.writeString(filePath, "Inhalt" + System.lineSeparator());

        FileAdministration file = new FileAdministration( new DirectoryAdministration(tempDir.toString()), "Test.txt");

        assertTrue(file.append(""));
        assertEquals("Inhalt" + System.lineSeparator() + "" + System.lineSeparator(), Files.readString(filePath));
    }

    @Test
    void shouldReturnFalseIfFileDoesNotExist() {
        FileAdministration file = new FileAdministration(new DirectoryAdministration(tempDir.toString()),"missing/test.txt");

        assertFalse(file.append("Hallo"));
    }

    @Test 
    void shouldWriteMultipleLines() throws IOException{
        Path filePath = tempDir.resolve("Test.txt");
        Files.createFile(filePath);

        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "Test.txt");
        ArrayList<String> content = new ArrayList<>(List.of("Erste Zeile", "Zweite Zeile", "Dritte Zeile"));

        assertTrue(fileAdministration.write(content));
        assertEquals(List.of("Erste Zeile", "Zweite Zeile", "Dritte Zeile"), Files.readAllLines(filePath));
    }

    @Test
    void shouldWriteEmptyList() throws IOException {
        Path filePath = tempDir.resolve("Test.txt");
        Files.writeString(filePath, "Alter Inhalt");

        FileAdministration fileAdministration = new FileAdministration( new DirectoryAdministration(tempDir.toString()), "Test.txt");

        assertTrue(fileAdministration.write(new ArrayList<>()));
        assertEquals("", Files.readString(filePath));
    }

    @Test
    void shouldOverwriteExistingContent() throws IOException {
        Path filePath = tempDir.resolve("Test.txt");
        Files.writeString(filePath, "Alter Inhalt");

        FileAdministration fileAdministration = new FileAdministration(new DirectoryAdministration(tempDir.toString()), "Test.txt");
        ArrayList<String> content = new ArrayList<>(List.of("Neuer Inhalt", "Zweite Zeile"));

        assertTrue(fileAdministration.write(content));
        assertEquals( List.of("Neuer Inhalt", "Zweite Zeile"), Files.readAllLines(filePath));
    }

    @Test
    void shouldAppendMultipleLines() throws IOException {
        Path filePath = tempDir.resolve("Test.txt");
        Files.writeString(filePath, "Vorhandener Inhalt" + System.lineSeparator());

        FileAdministration fileAdministration = new FileAdministration( new DirectoryAdministration(tempDir.toString()),"Test.txt");

        ArrayList<String> content = new ArrayList<>(
                List.of("Erste Zeile", "Zweite Zeile", "Dritte Zeile")
        );

        assertTrue(fileAdministration.append(content));

        assertEquals(
                List.of(
                        "Vorhandener Inhalt",
                        "Erste Zeile",
                        "Zweite Zeile",
                        "Dritte Zeile"
                ),
                Files.readAllLines(filePath)
        );
    }

    @Test
    void shouldAppendEmptyList() throws IOException {
        Path filePath = tempDir.resolve("Test.txt");
        Files.writeString(filePath, "Vorhandener Inhalt");

        FileAdministration file = new FileAdministration(
                new DirectoryAdministration(tempDir.toString()),
                "Test.txt"
        );

        assertTrue(file.append(new ArrayList<>()));
        assertEquals("Vorhandener Inhalt", Files.readString(filePath));
    }

    @Test
    void shouldAppendSingleLine() throws IOException {
        Path filePath = tempDir.resolve("Test.txt");
        Files.writeString(filePath, "Vorhandener Inhalt" + System.lineSeparator());

        FileAdministration file = new FileAdministration(
                new DirectoryAdministration(tempDir.toString()),
                "Test.txt"
        );

        assertTrue(file.append(new ArrayList<>(List.of("Neue Zeile"))));

        assertEquals(
                List.of("Vorhandener Inhalt", "Neue Zeile"),
                Files.readAllLines(filePath)
        );
    }
}
