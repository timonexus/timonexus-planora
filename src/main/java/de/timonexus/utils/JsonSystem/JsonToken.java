package de.timonexus.utils.JsonSystem;

public record JsonToken(JsonTokenType tokenType, String value, int position) {
    
    @Override
    public String toString(){
        return tokenType + "(" + value + ")";
    }
}
