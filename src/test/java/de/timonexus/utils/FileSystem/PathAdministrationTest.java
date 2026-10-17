package de.timonexus.utils.FileSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PathAdministrationTest {
    
    @Test 
    void shouldCreatePathAdministration(){
        PathAdministration pathAdmin = new PathAdministration("test123/test1234/test.txt");

        assertEquals("test123/test1234/test.txt", pathAdmin.getOriginalPath());
    }

    @Test 
    void shouldProcessOriginalPath(){
        PathAdministration pathAdmin = new PathAdministration("test123/test1234/test.txt");

        assertEquals("test.txt", pathAdmin.getFilePath());
        assertEquals("test123/test1234/", pathAdmin.getDirectoryPath());
    }

    @Test 
    void shouldSetOriginalPath(){
        PathAdministration pathAdmin = new PathAdministration("test123/test1234/test.txt");
        assertEquals("test123/test1234/test.txt", pathAdmin.getOriginalPath());

        pathAdmin.setOriginalPath("Test12345");
        assertEquals("Test12345", pathAdmin.getOriginalPath());
    }

    @Test 
    void shouldSetFilePath(){
        PathAdministration pathAdmin = new PathAdministration("test123/test1234/test.txt");
        pathAdmin.setFilePath("Test1234");

        assertEquals("Test1234", pathAdmin.getFilePath());
    }

    @Test 
    void shouldSetDirectoryPath(){
        PathAdministration pathAdmin = new PathAdministration("test123/test1234/test.txt");
        pathAdmin.setDirectoryPath("Test1234");

        assertEquals("Test1234", pathAdmin.getDirectoryPath());
    }
}
