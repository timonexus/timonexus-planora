package de.timonexus.utils.TimeSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormat{

    // attributes
    private final DateTimeFormatter formatter;

    // constructor
    public DateTimeFormat(String nPattern){
        this.formatter = DateTimeFormatter.ofPattern(nPattern);
    }

    // methods
    public String format(LocalDateTime nDateTime){
        return nDateTime.format(formatter);
    }
    public String formatNow(){
        return LocalDateTime.now().format(formatter);
    }
}