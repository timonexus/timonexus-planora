package de.timonexus.utils.JsonSystem.JsonValues;

import java.util.ArrayList;
import java.util.List;

public class JsonArray extends JsonValue {
    
    // attributes
    private final List<JsonValue> values;

    // constructor
    public JsonArray(){
        this.values = new ArrayList<>();
    }

    public void add(JsonValue value){
        values.add(value);
    }

    public JsonValue get(int index){
        return values.get(index);
    }

    public int size(){
        return values.size();
    }

    public List<JsonValue> getValues(){
        return List.copyOf(this.values);
    }

    @Override
    public String toString(){
        return values.toString();
    }
}
