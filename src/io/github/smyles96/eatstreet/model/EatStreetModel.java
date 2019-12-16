package io.github.smyles96.eatstreet.model;

/**
 * This class acts as the abstract parent for all classes representing models
 * on the EatStreet API
 * 
 * @author smyles96
 */
public abstract class EatStreetModel {
    
    /* INSTANCE VARIABLES */
    private String apiKey;
    
    /* CONSTRUCTOR(S) */
    
    /**
     * Instantiate a new model class
     * 
     * @param key The item's API key on the EatStreet server
     */
    public EatStreetModel(String key) {
        this.apiKey = key;
    }

    
    /* INSTANCE METHODS */
    
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
