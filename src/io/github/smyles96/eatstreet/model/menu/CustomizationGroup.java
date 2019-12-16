package io.github.smyles96.eatstreet.model.menu;

import java.io.Serializable;
import java.util.List;
import io.github.smyles96.eatstreet.model.EatStreetModel;

/**
 * This model class represents a group of particular customizations that can
 * be made to a menu item.
 * 
 * For example:
 *     CustomizationGroup (Toppings on Pizza)
 *         Customization - Add Black Olives To Pizza
 *             CustomizationChoice - On Half of Pizza
 *             CustomizationChoice - On All of Pizza
 * 
 * A group has a max count of customization's that can be added to the order.
 * This is represented by an integer value that can be attained by calling the
 * {@code getMaxCount()} function of this class. Each CustomizationChoice within
 * the group has an integer value that specifies how much it counts towards the
 * group's max count value.
 * 
 * @author smyles96
 */
public class CustomizationGroup extends EatStreetModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /*
     * INSTANCE VARIABLES
     */
    private String name;
    private Integer maxCount;
    private Double basePrice;
    private List<Customization> customizations;
    
    
    /*
     * CONSTRUCTOR(S)
     */

    public CustomizationGroup(String key) {
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


    public Integer getMaxCount() {
        return maxCount;
    }


    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }


    public Double getBasePrice() {
        if(basePrice == null) {
            basePrice = new Double(0.0);
        }
        
        return basePrice;
    }


    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }


    public List<Customization> getCustomizations() {
        return customizations;
    }


    public void setCustomizations(List<Customization> customizations) {
        this.customizations = customizations;
    }
    
    /*
     * INSTANCE METHODS
     */
    
    @Override
    public String toString() {
        return String.format("%s [Max: %d] (%s)",
                             this.getName(),
                             this.getMaxCount(),
                             this.getApiKey());
    }
}
