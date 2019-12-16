package io.github.smyles96.eatstreet.model.order;

import java.io.Serializable;

/**
 * This model represents the current status of an Order that was placed
 * 
 * The list of possible statuses is found here:
 * https://developers.eatstreet.com/object/OrderStatus
 * 
 * @author smyles96
 */
public class OrderStatus implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * INSTANCE VARIABLES
     */
    private String status;
    private Integer date;
    private String orderApiKey;
    
    /*
     * CONSTRUCTOR(S)
     */

    public OrderStatus(String key) {}
    
    /*
     * GETTERS and SETTERS
     */

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getOrderApiKey() {
        return orderApiKey;
    }

    public void setOrderApiKey(String orderApiKey) {
        this.orderApiKey = orderApiKey;
    }
}
