package io.github.smyles96.eatstreet.model.menu;

import java.io.Serializable;
import java.util.List;
import io.github.smyles96.eatstreet.model.EatStreetModel;

/**
 * This model class represents a category of items that are on a restaurant's
 * menu.
 * 
 * For example (consider an American restaurant):
 *     MenuCategory (Burgers)
 *         MenuItem (Single Patty Burger)
 *             CustomizationGroup (Condiments)
 *                 Customization (Add Ketchup)
 *                     CustomizationChoice (On the side)
 *                     CustomizationChoice (On the burger)
 *             Customization (Add Mustard)
 *                     CustomizationChoice (On the side)
 *                     CustomizationChoice (On the burger)
 *         MenuItem (Double CheeseBurger)
 *             ...
 *             
 * @author smyles96
 */
public class MenuCategory extends EatStreetModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /*
     * INSTANCE VARIABLES
     */
    
    private String name;
    private String description;
    private List<MenuItem> items;
    
    /*
     * CONSTRUCTOR(S)
     */

    public MenuCategory(String key) {
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

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
    
    /*
     * INSTANCE METHODS
     */
    
    /**
     * Get the name of the category as a String representation
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
