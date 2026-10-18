package de.timonexus.utils.JsonSystem.JsonValues;

public class JsonNumber extends JsonValue {
    
    // attributes
    private final String value;

    // constructor
    public JsonNumber(String value){
        this.value = value;
    }

    // getter
    public String getValue(){
        return this.value;
    }
    public int getInt(){
        return Integer.parseInt(this.value);
    }
    public long getLong(){
        return Long.parseLong(this.value);
    }
    public double getDouble(){
        return Double.parseDouble(this.value);
    }

    @Override
    public String toString(){
        return this.value;
    }
}
