package io.github.smyles96.eatstreet.model.restaurant;

/**
 * This model enum represents the type of order being made to a restaurant.
 * 
 * @author smyles96
 */
public enum OrderType {
    PICKUP("pickup"),
    DELIVERY("delivery"),
    BOTH("both");
    
    private final String typeString;
    
    OrderType(String typeString) {
        this.typeString = typeString;
    }
    
    @Override
    public String toString() {
        return this.typeString;
    }
}
