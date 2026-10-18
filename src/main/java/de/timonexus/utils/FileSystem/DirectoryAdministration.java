package de.timonexus.utils.FileSystem;

import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryAdministration {
    
    // attributes
    private String directoryPath;
    private Path path;

    // constructor
    public DirectoryAdministration(String n_DirectoryPath){
        this.directoryPath = n_DirectoryPath;

        this.initializePath();
    }

    // methods
    public boolean createDirectoryIfNotExists(){
        try {
            if(Files.isDirectory(path)){
                return true;
            }

            Files.createDirectories(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private void initializePath(){
        this.path = Path.of(this.directoryPath);
    }

    // getter
    public String getDirectoryPath() {
        return directoryPath;
    }
    public Path getPath() {
        return path;
    }

    // setter
    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;

        this.initializePath();
    }
    public void setPath(Path path) {
        this.path = path;
    }
}

