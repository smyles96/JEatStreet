package io.github.smyles96.eatstreet.model.menu;

import java.io.Serializable;
import java.util.List;
import io.github.smyles96.eatstreet.model.EatStreetModel;

/**
 * This model class represents a customization that can be made to an item
 * on a restaurant's menu (a MenuItem object).
 * 
 * @author smyles96
 */
public class Customization extends EatStreetModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*
     * INSTANCE VARIABLES
     */
    private String name;
    private String type;
    private List<CustomizationChoice> customizationChoices;
    
    /*
     * CONSTRUCTOR(S)
     */
    
    public Customization(String key) {
        super("");
    }

    
    /*
     * GETTERS and SETTERS
     */
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CustomizationChoice> getCustomizationChoices() {
        return customizationChoices;
    }

    public void setCustomizationChoices(List<CustomizationChoice> customizationChoices) {
        this.customizationChoices = customizationChoices;
    }
    
    /*
     * INSTANCE METHODS
     */
    
    /**
     * Return a String representation of this object
     */
    @Override
    public String toString() {
        return String.format("%s (%s)",
                             this.getName(),
                             this.getApiKey());
    }
}
