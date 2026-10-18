package de.timonexus.utils.DataSystem;

import java.nio.file.Path;

import de.timonexus.utils.FileSystem.FileAdministration;
import de.timonexus.utils.JsonSystem.JsonParser;
import de.timonexus.utils.JsonSystem.JsonValues.JsonObject;

public class DataAdministrator {
    
    public String getValue(String n_Path){
        Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(frames -> frames.skip(1).findFirst().map(StackWalker.StackFrame::getDeclaringClass).orElse(null));
        try {
            JsonParser parser = new JsonParser(this.getDataFilePath(caller));
            JsonObject config = parser.parse();

            return config.getString(n_Path);
        } catch (Exception e) {
            return "";
        }
    }

    private Path getDataFilePath(Class<?> caller){
        DataFileAdministrator dataFileAdministrator = new DataFileAdministrator();
        if(caller == FileAdministration.class){
            return Path.of(dataFileAdministrator.getFilePath("filepaths.MainFrame"));
        }

        return null;
    }
}
