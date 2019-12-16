package io.github.smyles96.eatstreet.model.restaurant;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.smyles96.eatstreet.exception.EatStreetApiException;
import io.github.smyles96.eatstreet.model.EatStreetModel;
import io.github.smyles96.eatstreet.model.menu.MenuCategory;
import io.github.smyles96.eatstreet.model.order.Order;
import io.github.smyles96.eatstreet.model.user.Address;
import io.github.smyles96.eatstreet.util.http.ApiEndpoint;
import io.github.smyles96.eatstreet.util.http.EatStreetRequestor;
import io.github.smyles96.eatstreet.util.json.JsonConverter;

/**
 * This model class represents a restaurant on the EatStreet API.
 * Objects of this class have the ability to get the menu for the restaurant
 * they represent, validate/send orders to the restaurant, and get other
 * information about the restaurant.
 * 
 * Get a restaurant's menu:
 * <pre>{@code
 * try {
 *     // Find restaurants that do delivery within a 2 mile radius.
 *     // NOTICE: The address must be in the format:
 *     //             [Street Address], [City], [State]
 *     List<Restaurant> results = api.findRestaurants("1234 Example Lane, Blacksburg, VA",
 *                                                    OrderType.DELIVERY,
 *                                                    2,
 *                                                    null);
 * 
 *     Restaurant target = results.get(0);
 *     List<MenuCategory> menu = target.getMenu();
 * 
 *     for(MenuCategory category : r.getMenu()) {
 *         System.out.println(category.getName());
 * 
 *         for(MenuItem item : category.getItems()) {
 *             System.out.println("   " + item);
 * 
 *             for(CustomizationGroup group : item.getCustomizationGroups()) {
 *                 System.out.println("      " + group.toString());
 * 
 *                 for(Customization customization : group.getCustomizations()) {
 *                     System.out.println("         " + customization.toString());
 *
 *                     for(CustomizationChoice choice : customization.getCustomizationChoices()) {
 *                         System.out.println("            " + choice.toString());
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 * catch(EatStreetApiException e) {
 *     System.out.println(e.getMessage())
 * }
 * }</pre>
 * 
 * @author smyles96
 */
public class Restaurant extends EatStreetModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * INSTANCE VARIABLES
     */
    
    private Double deliveryMin;
    private Double deliveryPrice;
    private String logoUrl;
    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private List<String> foodTypes;
    private String phone;
    private Double latitude;
    private Double longitude;
    private Double minFreeDelivery;
    private Double taxRate;
    private Boolean acceptsCash;
    private Boolean acceptsCard;
    private Boolean offersPickup;
    private Boolean offersDevlivery;
    private Boolean isTestRestaurant;
    private Integer minWaitTime;
    private Integer maxWaitTime;
    private Boolean open;
    private String url;
    private Map<String, String[]> hours;
    private String timezone;
    private List<DeliveryZone> zones;
    
    private transient List<MenuCategory> menu;
    
    /*
     * CONSTRUCTOR(S)
     */
    public Restaurant() {
        super("");
    }

    /*
     * GETTERS and SETTERS
     */
    
    public Double getDeliveryMin() {
        return deliveryMin;
    }

    public void setDeliveryMin(Double deliveryMin) {
        this.deliveryMin = deliveryMin;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getLogoUrl() {
        return logoUrl.replaceAll("&(?!amp;)", "&amp;");
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<String> getFoodTypes() {
        return foodTypes;
    }

    public void setFoodTypes(List<String> foodTypes) {
        this.foodTypes = foodTypes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getMinFreeDelivery() {
        return minFreeDelivery;
    }

    public void setMinFreeDelivery(Double minFreeDelivery) {
        this.minFreeDelivery = minFreeDelivery;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Boolean getAcceptsCash() {
        return acceptsCash;
    }

    public void setAcceptsCash(Boolean acceptsCash) {
        this.acceptsCash = acceptsCash;
    }

    public Boolean getAcceptsCard() {
        return acceptsCard;
    }

    public void setAcceptsCard(Boolean acceptsCard) {
        this.acceptsCard = acceptsCard;
    }

    public Boolean getOffersPickup() {
        return offersPickup;
    }

    public void setOffersPickup(Boolean offersPickup) {
        this.offersPickup = offersPickup;
    }

    public Boolean getOffersDevlivery() {
        return offersDevlivery;
    }

    public void setOffersDevlivery(Boolean offersDevlivery) {
        this.offersDevlivery = offersDevlivery;
    }

    public Boolean getIsTestRestaurant() {
        return isTestRestaurant;
    }

    public void setIsTestRestaurant(Boolean isTestRestaurant) {
        this.isTestRestaurant = isTestRestaurant;
    }

    public Integer getMinWaitTime() {
        return minWaitTime;
    }

    public void setMinWaitTime(Integer minWaitTime) {
        this.minWaitTime = minWaitTime;
    }

    public Integer getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(Integer maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String[]> getHours() {
        return hours;
    }
    
    public String[] getHoursFor(String day) {
        if( hours == null ) {
            return new String[] {""};
        }
        
        // Format day name to only have capital letter at start and the rest lowercase
        String dayName = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();
        String[] dayHours = hours.get(dayName);
        
        // If a key other than a day name was given by the user, the Map of hours will
        // return null. Instead of returning null, the Optional class can be used
        // to check whether dayHours == null, and if so it will return [""] instead
        String[] hours = Optional.ofNullable(dayHours).orElse(new String[] {""});
        
        return hours;
    }

    public void setHours(Map<String, String[]> hours) {
        this.hours = hours;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<DeliveryZone> getZones() {
        return zones;
    }

    public void setZones(List<DeliveryZone> zones) {
        this.zones = zones;
    }
    
    /**
     * Get the menu for the restaurant
     * 
     * @return A List of MenuCategory objects representing the menu
     * 
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public List<MenuCategory> getMenu() throws EatStreetApiException {
        // If the menu is null, it needs to be retrieved from the EatStreet API
        if( menu == null ) {
            // Add query parameter to include item customization information
            List<NameValuePair> getParams = new ArrayList<>();
            getParams.add( new BasicNameValuePair("includeCustomizations", "true") );
            
            try(Reader response = EatStreetRequestor.makeGetRequest(ApiEndpoint.RESTAURANT_MENU, getParams, this.getApiKey())) {
                JsonArray json = JsonParser.parseReader(response).getAsJsonArray();
                menu = Arrays.asList(JsonConverter.fromJson(json, MenuCategory[].class));
            }
            catch (IOException e) {
                throw new EatStreetApiException("Unable to close the HTTP response object");
            }
            
        }
        
        return menu;
    }
    
    
    /*
     * INSTANCE METHODS
     */
    
    /**
     * Check if a delivery order meets the minimum subtotal cost of this
     * restaurant
     * 
     * @param order The order to check
     * 
     * @return true if the order meets the requirement, false otherwise
     */
    public boolean orderMeetsMinimumSubtotal(Order order) {
        if( order.getMethod().equals("pickup") ) {
            return true;
        }
        else {
            int comparison = this.getDeliveryMin().compareTo(order.getTotal());
            
            return (comparison <= 0) ? true : false;
        }
    }
    
    /**
     * Validates an order with the restaurant.
     * 
     * This method will set the total price, subtotal, and tax for the order
     * based on the restaurant's tax rate
     * 
     * @param order The order to validate
     * 
     * @return The validated order
     * 
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public Order validateOrder(Order order) throws EatStreetApiException {
        return this.sendOrderToApi(order, true);
    }
    
    /**
     * Send an order to this restaurant
     * 
     * @param order The order to send to the restaurant
     * 
     * @return The sent order
     * 
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public Order sendOrder(Order order) throws EatStreetApiException {
        return this.sendOrderToApi(order, false);
    }
    
    private Order sendOrderToApi(Order order, boolean validateOrder) throws EatStreetApiException {
        // The send order REST endpoint requires a recipient json object within
        // the request's json payload; this parameter specifies the user making
        // the order.
        JsonObject recipientParameter = new JsonObject();
        recipientParameter.addProperty("apiKey", EatStreetRequestor.getUserApiKey());
        
        // Add optional parameters to the recipient object
        String phone = order.getPhone();
        String firstName = order.getFirstName();
        String lastName = order.getLastName();
        if(phone != null) { recipientParameter.addProperty("phone", phone); }
        if(firstName != null) { recipientParameter.addProperty("firstName", firstName); }
        if(lastName != null) { recipientParameter.addProperty("lastName", lastName); }
        
        // Create the order json
        order.setRestaurantApiKey(this.getApiKey());
        JsonObject jsonPayload = JsonConverter.toJsonObject(order);
        jsonPayload.add("recipient", recipientParameter);
        
        // Select the endpoint to send the request to
        ApiEndpoint endpoint = validateOrder ? ApiEndpoint.VALIDATE_ORDER : ApiEndpoint.SEND_ORDER;
        
        try(Reader response = EatStreetRequestor.makePostRequest(endpoint, jsonPayload.toString())) {
            // Parse the response as a JSON object
            JsonObject json = JsonParser.parseReader(response).getAsJsonObject();
            
            // Fill the price fields in the order using the response json
            JsonConverter.populate(json, Order.class, order);
            return order;
        }
        catch (IOException e) {
            throw new EatStreetApiException("Unable to close the HTTP response object");
        }
    }
    
    /**
     * Get string representation of this restaurant
     */
    public String toString() {
        return String.format("Name: %s (%s)\nAddress: %s %s,%s %s",
                                this.getName(),
                                this.getApiKey(),
                                this.getStreetAddress(),
                                this.getCity(),
                                this.getState(),
                                this.getZip());
    }
    
    /**
     * Check whether this restaurant and another are equal. Equality is checked
     * by comparing the restaurant name and street address
     * 
     * @return true if they are equal, false otherwise
     */
    public boolean equals(Object other) {
        // Check if memory address matches
        if (this == other) {  
            return true;  
        }  
        
        if (other instanceof Restaurant) {  
            Restaurant otherRestaurant = (Restaurant) other;
            
            if (getStreetAddress().toLowerCase().equals(otherRestaurant.getStreetAddress().toLowerCase()) &&
                getName().equals(otherRestaurant.getName()))
            {
                return true;
            }
        } 
        
        return false;
    }
}
