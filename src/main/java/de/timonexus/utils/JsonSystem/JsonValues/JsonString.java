package de.timonexus.utils.JsonSystem.JsonValues;

public class JsonString extends JsonValue{
    
    // attributes
    private final String value;

    public JsonString(String value){
        this.value = value;
    }

    // getter
    public String getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return this.value;
    }
}
