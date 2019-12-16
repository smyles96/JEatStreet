package io.github.smyles96.eatstreet.model.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import io.github.smyles96.eatstreet.model.EatStreetModel;
import io.github.smyles96.eatstreet.model.menu.MenuItem;

/**
 * This model class represents an item on an order
 * 
 * For example:
 *         OrderItem (Cheeseburger)
 *             OrderCustomizationChoice (With Pickles)
 *         OrderItem (French Fries)
 *             OrderCustomizationChoice (Wedge Cut)
 *             
 * @author smyles96
 */
public class OrderItem extends EatStreetModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /*
     * INSTANCE VARIABLES
     */
    private String name;
    private String comments;
    private Double basePrice;
    private Double totalPrice;
    private List<OrderCustomizationChoice> customizationChoices;
    
    /*
     * CONSTRUCTOR(S)
     */
    
    public OrderItem() {
        super("");
        
        customizationChoices = new ArrayList<>();
    }
    
    public OrderItem(String menuItemApiKey, String menuItemName, Double menuItemBasePrice) {
        super(menuItemApiKey);
        
        name = menuItemName;
        basePrice = menuItemBasePrice;
        customizationChoices = new ArrayList<>();
    }
    
    public OrderItem(MenuItem menuItem) {
        super(menuItem.getApiKey());
        
        name = menuItem.getName();
        basePrice = menuItem.getBasePrice();
        customizationChoices = new ArrayList<>();
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderCustomizationChoice> getCustomizationChoices() {
        return customizationChoices;
    }

    public void setCustomizationChoices(List<OrderCustomizationChoice> customizationChoices) {
        this.customizationChoices = customizationChoices;
    }
    
    public int getTotalCustomizations() {
        return getCustomizationChoices().size();
    }
    
    /*
     * INSTANCE METHODS
     */
    
    public Double calculateSubtotal() {
        return this.getBasePrice() + 
               this.getCustomizationChoices()
                   .stream()
                   .collect(Collectors.summingDouble(i -> i.getPrice().doubleValue()));
    }
    
    /**
     * Adds a customization to this item
     * 
     * @param customization The customization to add
     * 
     * @return true if the customization was added successfully, false otherwise
     */
    public boolean addCustomization(OrderCustomizationChoice customization) {
        return customizationChoices.add(customization);
    }
    
    /**
     * Add a list of customizations to the item
     * 
     * @param customizations The list of OrderCustomizationChoice objects
     * 
     * @return true if added successfully, false otherwise
     */
    public boolean addCustomizations(List<OrderCustomizationChoice> customizations) {
        return customizationChoices.addAll(customizations);
    }
    
    
    @Override
    public String toString() {
        return String.format("%.2f %s (%s)\n%s", this.getTotalPrice(),
                                                 this.getName(),
                                                 this.getApiKey(),
                                                 this.getComments());
    }
}
