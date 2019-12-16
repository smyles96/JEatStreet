package io.github.smyles96.eatstreet.model.order;

import java.io.Serializable;
import io.github.smyles96.eatstreet.model.EatStreetModel;
import io.github.smyles96.eatstreet.model.menu.CustomizationChoice;

/**
 * This model class represents a customization to add to an OrderItem object.
 * 
 * @author Sam
 */
public class OrderCustomizationChoice extends EatStreetModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * INSTANCE VARIABLES
     */
    private String details;
    private Double price;

    /*
     * CONSTRUCTOR(S)
     */
    
    public OrderCustomizationChoice() {
        super("");
    }
    
    public OrderCustomizationChoice(String customizationApiKey, String customizationName, Double customizationPrice) {
        super(customizationApiKey);
        
        this.details = customizationName;
        this.price = customizationPrice;
    }
    
    public OrderCustomizationChoice(CustomizationChoice menuCustomization) {
        super(menuCustomization.getApiKey());
        
        this.details = menuCustomization.getName();
        this.price = menuCustomization.getPrice();
    }

    /*
     * GETTERS and SETTERS
     */
    
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    /*
     * INSTANCE METHODS
     */
    
    @Override
    public String toString() {
        return String.format("%.2f %s", this.getPrice(), this.getDetails());
    }
}
