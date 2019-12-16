package io.github.smyles96.eatstreet.model.order;

/**
 * This model enum represents the type of payment used for an order.
 * 
 * @author Sam
 */
public enum PaymentMethod {
    CASH("cash"),
    CARD("card");
    
    private final String METHOD;
    
    PaymentMethod(String method) {
        this.METHOD = method;
    }
    
    @Override
    public String toString() {
        return this.METHOD;
    }
}
