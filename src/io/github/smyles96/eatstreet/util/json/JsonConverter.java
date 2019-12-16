package io.github.smyles96.eatstreet.util.json;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * This class contains static helper methods that handle JSON conversions
 * 
 * @author smyles96
 */
public class JsonConverter {
    
    /* STATIC FIELDS */
    public static final Gson GSON;
    
    // Static initializer will initialize static final field
    static {
        GSON = new Gson();
    }
    
    /* STATIC METHODS */
    
    /**
     * Gets the JSON object string representation of a Java object
     * 
     * @param src The java object to get a JSON string representation from
     * 
     * @return The JSON string representation of the object
     */
    public static String toJson(Object src) {
        return GSON.toJson(src);
    }
    
    /**
     * Converts a Java object into a JsonObject
     * 
     * @param src The Java object to convert
     * 
     * @return The object as a JsonObject
     */
    public static JsonObject toJsonObject(Object src) {
        return GSON.toJsonTree(src).getAsJsonObject();
    }
    
    /**
     * Turn a JSON object string to a Java object
     * 
     * @param json The JSON string object
     * @param srcClass The class type to turn the string into
     * 
     * @return A Java object of type srcClass with the JSON object attributes
     */
    public static <T> T fromJson(JsonElement json, Class<T> srcClass) {
        return GSON.fromJson(json, srcClass);
    }
    
    /**
     * Populates the fields of an existing Java object using a JSON string
     * 
     * @param json A json object to get attribute values from
     * @param type The type of java object to populate
     * @param into An existing java object of type T
     */
    public static <T> void populate(JsonObject json, Class<T> type, T into) {
        GsonBuilder builder = new GsonBuilder();
        
        builder.registerTypeAdapter(type, new InstanceCreator<T>() {
            @Override public T createInstance(Type t) { return into; }
        }).create().fromJson(json, type);
    }
}
