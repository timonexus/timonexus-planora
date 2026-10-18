package de.timonexus.utils.FileSystem;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

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
    public ArrayList<String> read(){
        ArrayList<String> content = new ArrayList<>();

        try {
            for(String line : Files.readAllLines(path)){
                content.add(line);
            }
        } catch (Exception e) {
            return content;
        }

        return content;
    }
    public boolean write(String n_Text){
        try {
            Files.writeString(path, n_Text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean write(ArrayList<String> n_Content) {
        try {
            Files.write(path, n_Content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean append(String n_Text){
        try {
            Files.writeString(path, n_Text + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean append(ArrayList<String> n_Content){
        for(String line : n_Content){
            if(!this.append(line)) return false;
        }

        return true;
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
