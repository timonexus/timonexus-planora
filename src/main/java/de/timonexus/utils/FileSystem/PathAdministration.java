package de.timonexus.utils.FileSystem;

public class PathAdministration {
    
    // attributes
    private String originalPath, directoryPath, filePath;

    // constructor
    public PathAdministration(String n_OriginalPath){
        this.originalPath = n_OriginalPath;

        processSplitOriginal();
    }


    // methods
    private void processSplitOriginal(){
        String[] splittedOriginalPath = this.originalPath.split("/");
        this.filePath = splittedOriginalPath[splittedOriginalPath.length - 1];
        splittedOriginalPath[splittedOriginalPath.length - 1] = null;

        this.directoryPath = "";
        for(String pathPart : splittedOriginalPath){
            if(pathPart != null){
                this.directoryPath += pathPart + "/";
            }
        }
    }

    // getter
    public String getDirectoryPath() {
        return directoryPath;
    }
    public String getFilePath() {
        return filePath;
    }
    public String getOriginalPath() {
        return originalPath;
    }

    // setter
    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }
}
