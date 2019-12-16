package io.github.smyles96.eatstreet.model.menu;

import java.io.Serializable;
import java.util.List;
import io.github.smyles96.eatstreet.model.EatStreetModel;
import io.github.smyles96.eatstreet.model.restaurant.Restaurant;

/**
 * This model class represents an item on a restaurant's menu.
 *
 * For example: 
 *     MenuItem (Single Patty Burger)
 *         CustomizationGroup (Condiments)
 *             Customization (Add Ketchup)
 *                 CustomizationChoice (On the side)
 *                 CustomizationChoice (On the burger)
 *             Customization (Add Mustard)
 *                 CustomizationChoice (On the side)
 *                 CustomizationChoice (On the burger)
 *
 * @author smyles96
 */
public class MenuItem extends EatStreetModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * INSTANCE VARIABLES
     */
    
    private String name;
    private String description;
    private Double basePrice;
    private List<CustomizationGroup> customizationGroups;

    /*
     * CONSTRUCTOR(S)
     */
    
    public MenuItem(String key) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public List<CustomizationGroup> getCustomizationGroups() {
        return customizationGroups;
    }

    public void setCustomizationGroups(List<CustomizationGroup> customizationGroups) {
        this.customizationGroups = customizationGroups;
    }
    
    /*
     * INSTANCE METHODS
     */
    
    /**
     * Return a String representation of the MenuItem. The format is:
     *     "[Base Price] [Item Name] (Item API Key)"
     */
    @Override
    public String toString() {
        return String.format("%.2f %s (%s)",
                             this.getBasePrice(),
                             this.getName(),
                             this.getApiKey());
    }
    
    /**
     * Checks if this MenuItem equals another MenuItem. Equality is checked by
     * comparing the names of the items
     * 
     * @param other The other MenuItem to compare to
     * 
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        // Check if memory address matches
        if (this == other) {  
            return true;  
        }  
        
        if (other instanceof MenuItem) {  
            MenuItem otherItem = (MenuItem) other;
            
            if (getName().toLowerCase().equals(otherItem.getName().toLowerCase()))
            {
                return true;
            }
        } 
        
        return false;
    }
}
