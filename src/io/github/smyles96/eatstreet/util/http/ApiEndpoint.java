package io.github.smyles96.eatstreet.util.http;

/**
 * This enum holds the values of endpoint urls for the EatStreet URL
 * 
 * @author smyles96
 */
public enum ApiEndpoint {
    // Restaurant Endpoint Paths
    RESTAURANT_SEARCH("restaurant/search", false),
    RESTAURANT_SEARCH_TEST("restaurant/search-test", false),
    RESTAURANT_MENU("restaurant/%s/menu", true),
    RESTAURANT_DETAILS("restaurant", false),
    CUSTOMIZATIONS("customizations", false),
    
    // Order Endpoint Paths
    SEND_ORDER("send-order", false),
    VALIDATE_ORDER("validate-order", false),
    GET_ORDER("order/%s", true),
    ORDER_STATUS("order/%s/statuses", true),
    
    // User Endpoint Paths
    REGISTER_USER("register-user", false),
    UPDATE_USER("update-user/%s", true),
    GET_USER("user/%s", true),
    SIGN_IN("signin", false),
    ORDER_HISTORY("user/%s/orders", true),
    ADD_ADDRESS("user/%s/add-address", true),
    ADD_CARD("user/%s/add-card", true),
    REMOVE_ADDRESS("user/%s/remove-address/%s", true),
    REMOVE_CARD("user/%s/remove-card/%s", true);
    
    private final String ENDPOINT_PATH;
    private final boolean REQUIRES_FORMATTING;
    
    ApiEndpoint(String endpointPath, boolean requiresFormatting) {
        this.ENDPOINT_PATH = endpointPath;
        this.REQUIRES_FORMATTING = requiresFormatting;
    }
    
    public boolean requiresFormatting() {
        return this.REQUIRES_FORMATTING;
    }
    
    @Override
    public String toString() {
        return this.ENDPOINT_PATH;
    }
}
