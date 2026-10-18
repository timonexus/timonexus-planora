package de.timonexus.utils.FileSystem;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileAdministration {
    
    // attributes
    private DirectoryAdministration directory;
    private String fileName;

    private Path path;

    // constructor
    public FileAdministration(DirectoryAdministration n_Directory, String n_FileName){
        this.directory = n_Directory;
        this.fileName = n_FileName;

        this.initializePath();
    }

    // methods
    public boolean createFileIfNotExists(){
        try {
            if(Files.exists(path)){
                return true;
            }

            Files.createFile(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void initializePath(){
        this.path = Path.of(this.directory.getDirectoryPath()).resolve(this.fileName);
    }

    // getter
    public DirectoryAdministration getDirectory() {
        return directory;
    }
    public String getFileName() {
        return fileName;
    }
    public Path getPath() {
        return path;
    }

    // setter
    public void setDirectory(DirectoryAdministration directory) {
        this.directory = directory;
        this.initializePath();
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
        this.initializePath();
    }
    public void setPath(Path path) {
        this.path = path;
    }
}
