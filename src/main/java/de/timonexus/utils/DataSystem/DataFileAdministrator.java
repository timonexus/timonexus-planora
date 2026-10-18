package de.timonexus.utils.DataSystem;

import java.nio.file.Path;

import de.timonexus.utils.JsonSystem.JsonParser;
import de.timonexus.utils.JsonSystem.JsonValues.JsonObject;

public class DataFileAdministrator {
    
    // attributes
    private Path filePath;

    // constructor
    public DataFileAdministrator(Path n_FilePath){
        this.filePath = n_FilePath;
    }
    public DataFileAdministrator(){
        this.filePath = Path.of("data\\data_filePaths.json");
    }

    // methods
    public String getFilePath(String n_Path){
        try {
            JsonParser parser = new JsonParser(this.filePath);
            JsonObject config = parser.parse();

            return config.getString(n_Path);
        } catch (Exception e) {
            return "";
        }

    }

    // getter
    public Path getFilePath() {
        return filePath;
    }

    // setter
    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }
}
