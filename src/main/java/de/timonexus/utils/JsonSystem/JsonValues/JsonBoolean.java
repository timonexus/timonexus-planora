package de.timonexus.utils.JsonSystem.JsonValues;

public class JsonBoolean extends JsonValue {
    
    // attributes
    private final boolean value;

    // constructor
    public JsonBoolean(boolean value){
        this.value = value;
    }

    // getter
    public boolean getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return Boolean.toString(this.value);
    }
}
