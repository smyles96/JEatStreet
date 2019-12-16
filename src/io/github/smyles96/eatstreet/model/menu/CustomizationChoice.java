package io.github.smyles96.eatstreet.model.menu;

import java.io.Serializable;
import io.github.smyles96.eatstreet.model.EatStreetModel;

/**
 * This model class represents a possible choice that can be made for a
 * particular menu item's customization
 * 
 * For example:
 *     Customization - Add Black Olives To Pizza
 *         CustomizationChoice - On Half of Pizza
 *         CustomizationChoice - On All of Pizza
 *         
 * @author smyles96
 */
public class CustomizationChoice extends EatStreetModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * INSTANCE VARIABLES
     */
    private String name;
    private Double price;
    private Integer count;

    /*
     * CONSTRUCTOR(S)
     */
    
    public CustomizationChoice(String key) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /*
     * INSTANCE METHODS
     */
    
    /**
     * Return a String representation of this object
     */
    @Override
    public String toString() {
        return String.format("%.2f %s [%d] (%s)",
                             this.getPrice(),
                             this.getName(),
                             this.getCount(),
                             this.getApiKey());
    }
}
