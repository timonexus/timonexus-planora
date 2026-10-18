package de.timonexus.utils.FileSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

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
    public boolean exists(){
        return Files.isDirectory(path);
    }

    public ArrayList<Path> listAllFiles(){
        ArrayList<Path> filesList = new ArrayList<>();

        try (var files = Files.list(path)){
            filesList.addAll(files.toList());
        } catch (Exception e) {
            return filesList;
        }

        return filesList;
    }
    public ArrayList<Path> listFiles() {
        ArrayList<Path> filesList = new ArrayList<>();

        try (var files = Files.list(path)) {
            files.filter(Files::isRegularFile).forEach(filesList::add);
        } catch (IOException e) {
            return filesList;
        }
        return filesList;
    }
    public ArrayList<Path> listAllFilesRecursively(){
        ArrayList<Path> filesList = new ArrayList<>();

        try (Stream<Path> files = Files.walk(path)) {
            files.filter(Files::isRegularFile).forEach(filesList::add);
        } catch (IOException e) {
            return filesList;
        }
        return filesList;
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

