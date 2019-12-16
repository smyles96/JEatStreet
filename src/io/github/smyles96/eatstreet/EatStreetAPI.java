package io.github.smyles96.eatstreet;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.smyles96.eatstreet.exception.EatStreetApiException;
import io.github.smyles96.eatstreet.model.menu.Customization;
import io.github.smyles96.eatstreet.model.menu.CustomizationChoice;
import io.github.smyles96.eatstreet.model.menu.CustomizationGroup;
import io.github.smyles96.eatstreet.model.menu.MenuCategory;
import io.github.smyles96.eatstreet.model.menu.MenuItem;
import io.github.smyles96.eatstreet.model.order.Order;
import io.github.smyles96.eatstreet.model.order.OrderCustomizationChoice;
import io.github.smyles96.eatstreet.model.order.OrderItem;
import io.github.smyles96.eatstreet.model.order.OrderStatus;
import io.github.smyles96.eatstreet.model.order.PaymentMethod;
import io.github.smyles96.eatstreet.model.restaurant.OrderType;
import io.github.smyles96.eatstreet.model.restaurant.Restaurant;
import io.github.smyles96.eatstreet.model.user.Address;
import io.github.smyles96.eatstreet.model.user.CreditCard;
import io.github.smyles96.eatstreet.model.user.User;
import io.github.smyles96.eatstreet.util.http.ApiEndpoint;
import io.github.smyles96.eatstreet.util.http.EatStreetRequestor;
import io.github.smyles96.eatstreet.util.json.JsonConverter;

/**
 * This class represents the main connecting component to the EatStreet API. It
 * provides the ability to search for restaurants and manage a user's account
 * with EatStreet.
 * 
 * 
 * Registering a new user:
 * <pre>{@code
 * EatStreetApi api = new EatStreetApi("<ACCESS TOKEN>");
 * api.setUserApiKey("<USER API KEY>");
 * 
 * try {
 *     String newUserApiKey = api.registerUser("example@example.com",
 *                                             "password",
 *                                             "John",
 *                                             "Doe",
 *                                             "123-456-7890");
 * }
 * catch(EatStreetApiException e) {
 *     System.out.println(e.getMessage());
 * }
 * }</pre>
 * 
 * Updating a user:
 * <pre>{@code
 * EatStreetApi api = new EatStreetApi("<ACCESS TOKEN>");
 * api.setUserApiKey("<USER API KEY>");
 * 
 * try {
 *     api.updateUser("password2",
 *                    "John",
 *                    "Doe",
 *                    "123-456-7890");
 * }
 * catch(EatStreetApiException e) {
 *     System.out.println(e.getMessage());
 * }
 * }</pre>
 * 
 * Get user's information:
 * <pre>{@code
 * EatStreetApi api = new EatStreetApi("<ACCESS TOKEN>");
 * api.setUserApiKey("<USER API KEY>");
 * 
 * try {
 *     User apiUser = api.getUser();
 * 
 *     List<Address> savedAddress = apiUser.getSavedAddresses();
 *     List<CreditCard> savedCards = apiUser.getSavedCards();
 * }
 * catch(EatStreetApiException e) {
 *     System.out.println(e.getMessage());
 * }
 * }</pre>
 * 
 * Add / Remove Address from User's Account:
 * <pre>{@code
 * EatStreetApi api = new EatStreetApi("<ACCESS TOKEN>");
 * api.setUserApiKey("<USER API KEY>");
 * 
 * try {
 *     Address address = new Address();
 *     address.setStreetAddress("1234 Example Street");
 *     address.setCity("Exampleville");
 *     address.setState("VA");
 *     address.setZip("12345");
 * 
 *     api.addAddress(address);
 *     api.removeAddress(address);
 * }
 * catch(EatStreetApiException e) {
 *     System.out.println(e.getMessage());
 * }
 * }</pre>
 * 
 * Add / Remove Credit Card from User's Account:
 * <pre>{@code
 * EatStreetApi api = new EatStreetApi("<ACCESS TOKEN>");
 * api.setUserApiKey("<USER API KEY>");
 * 
 * try {
 *     CreditCard card = new CreditCard();
 *     card.setCardholderName("John Doe");
 *     card.setCardholderStreetAddress("1234 Example Lane, Blacksburg, VA");
 *     card.setCardholderZip("24060");
 *     card.setCardNumber("1234123412341234");
 *     card.setCvv("123");
 * 
 *     api.addCard(card);
 *     api.removeCard(card);
 * }
 * catch(EatStreetApiException e) {
 *     System.out.println(e.getMessage());
 * }
 * }</pre>
 * 
 * Get user's order history:
 * <pre>{@code
 * EatStreetApi api = new EatStreetApi("<ACCESS TOKEN>");
 * api.setUserApiKey("<USER API KEY>");
 * 
 * try {
 *     List<Order> pastOrders = api.getOrderHistory();
 * }
 * catch(EatStreetApiException e) {
 *     System.out.println(e.getMessage());
 * }
 * }</pre>
 * 
 * Search restaurants:
 * <pre>{@code
 * EatStreetApi api = new EatStreetApi("<ACCESS TOKEN>");
 * 
 * // The user's API key doesn't need to be set to search restaurants
 * // api.setUserApiKey("<USER API KEY>");
 * 
 * try {
 *     // Find restaurants that do delivery within a 2 mile radius.
 *     // NOTICE: The address must be in the format:
 *     //             [Street Address], [City], [State]
 *     List<Restaurant> results = api.findRestaurants("1234 Example Lane, Blacksburg, VA",
 *                                                    OrderType.DELIVERY,
 *                                                    2,
 *                                                    null);
 *     
 *     for(Restaurant r : results) {
 *         // Do stuff with restaurants
 *     }
 * }
 * catch(EatStreetApiException e) {
 *     System.out.println(e.getMessage());
 * }
 * }</pre>
 * 
 * @author Sam
 */
public class EatStreetAPI {
    
    private User apiUser;
    
    /**
     * Creates an object to access the EatStreet Public API.
     * Using this constructor requires later setting a user's API key in
     * order to access some functionality
     * 
     * @param accessToken Developer token to access the API
     */
    public EatStreetAPI(String accessToken) {
        EatStreetRequestor.setAccessToken(accessToken);
    }
    
    /**
     * Creates an object to access the EatStreet Public API
     * 
     * @param accessToken Developer token to access the API
     * @param userApiKey A user's API key
     */
    public EatStreetAPI(String accessToken, String userApiKey) {
        EatStreetRequestor.setAccessToken(accessToken);
        EatStreetRequestor.setUserApiKey(userApiKey);
    }
    
    /**
     * Gets the current user's api key
     * 
     * @return The user's api key
     */
    public String getUserApiKey() {
        return EatStreetRequestor.getUserApiKey();
    }
    
    /**
     * Sets the user's api key
     * 
     * @param apiKey The api key
     */
    public void setUserApiKey(String apiKey) {
        EatStreetRequestor.setUserApiKey(apiKey);
    }
    
    /**
     * Registers a new user on the EatStreet API. The registered user's API
     * key will be set within the method so that it can be used automatically
     * by other methods of the API.
     * 
     * @param email The user's email
     * @param password The user's password
     * @param firstName The user's first name
     * @param lastName The user's last name
     * @param phone The user's phone number
     * 
     * @return The user's API key created by the EatStreet server
     * 
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public String registerUser(String email, String password, String firstName, String lastName, String phone) throws EatStreetApiException {
        // Great a list of request parameters (will be encoded as JSON)
        List<NameValuePair> jsonParams = new ArrayList<>();
        jsonParams.add(new BasicNameValuePair("email", email));
        jsonParams.add(new BasicNameValuePair("password", password));
        jsonParams.add(new BasicNameValuePair("firstName", firstName));
        jsonParams.add(new BasicNameValuePair("lastName", lastName));
        jsonParams.add(new BasicNameValuePair("phone", phone));
        
        try(Reader response = EatStreetRequestor.makePostRequest(ApiEndpoint.REGISTER_USER, jsonParams)) {
            // Parse the response as a JSON object
            JsonObject json = JsonParser.parseReader(response).getAsJsonObject();
            
            // Add user API key
            String newUserApiKey = json.get("apiKey").getAsString();
            
            // Set the user API key for the library
            this.setUserApiKey(newUserApiKey);
            
            // Extract the apiKey JSON property as a Java String 
            return newUserApiKey;
        }
        catch (IOException e) {
            throw new EatStreetApiException("Unable to close the HTTP response object");
        }
    }
    
    /**
     * Updates a user's information on EatStreet
     * 
     * @param user The user to update. The fields password, firstName,
     *             lastName, and phone will be checked and updated
     *             
     * @return true on successful update, false otherwise
     * 
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public boolean updateUser(User user) throws EatStreetApiException {
        return this.updateUser(user.getPassword(),
                               user.getFirstName(),
                               user.getLastName(),
                               user.getPhone());
    }
    
    /**
     * Updates a user's information on EatStreet. In order to use this method,
     * the user's api key must have been set or passed in to this classe's
     * constructor.
     * 
     * @param password The new user password (may be null to keep original value)
     * @param firstName The new user first name (may be null to keep original value)
     * @param lastName The new user last name (may be null to keep original value)
     * @param phone The new user phone (may be null to keep original value)
     * 
     * @return true on successful update, false otherwise
     * 
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public boolean updateUser(String password, String firstName, String lastName, String phone) throws EatStreetApiException {
        // Great a list of request parameters (will be encoded as JSON)
        List<NameValuePair> jsonParams = new ArrayList<>();
        jsonParams.add(new BasicNameValuePair("password", password));
        jsonParams.add(new BasicNameValuePair("firstName", firstName));
        jsonParams.add(new BasicNameValuePair("lastName", lastName));
        jsonParams.add(new BasicNameValuePair("phone", phone));
        
        try(Reader response = EatStreetRequestor.makePostRequest(ApiEndpoint.UPDATE_USER, jsonParams, EatStreetRequestor.getUserApiKey())) {
            // Parse the response as a JSON object
            JsonObject json = JsonParser.parseReader(response).getAsJsonObject();
            JsonConverter.populate(json, User.class, apiUser);
            
            // Check if an API key was returned (indicating successful update)
            return (json.get("apiKey") != null);
        }
        catch (IOException e) {
            throw new EatStreetApiException("Unable to close the HTTP response object");
        }
    }
    
    /**
     * Gets a user's info from the EatStreet API.
     * 
     * @return A User object containing the user's information
     * 
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public User getUser() throws EatStreetApiException {
        // Perform lazy loading
        if( apiUser == null ) {
            try(Reader response = EatStreetRequestor.makeGetRequest(ApiEndpoint.GET_USER, null, EatStreetRequestor.getUserApiKey())) {
                // Get the JSON object response
                JsonObject json = JsonParser.parseReader(response).getAsJsonObject();
                
                return JsonConverter.fromJson(json, User.class);
            }
            catch(IOException e) {
                throw new EatStreetApiException("Unable to close the HTTP response object");
            }
        }
        else {
            return apiUser;
        }
    }
    
    /**
     * Adds an Address to the user's account.
     * 
     * @param newAddress The new Address to add
     * 
     * @return The Address that was added or, if Address was already registered
     *         for the user, the already registered Address
     * 
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public Address addAddress(Address newAddress) throws EatStreetApiException {
        return this.getUser().addAddress(newAddress);
    }
    
    /**
     * Remove a user's saved Address
     * 
     * @param addressToRemove The Address to remove
     * 
     * @return true if removed successfully, false otherwise
     * 
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public boolean removeAddress(Address addressToRemove) throws EatStreetApiException {
        return this.getUser().removeAddress(addressToRemove);
    }
    
    /**
     * Save a CreditCard to the user's account
     * 
     * @param cardToAdd The card to add
     * 
     * @return The CreditCard that was added, or, if CreditCard was already registered
     *         for the user, the already registered CreditCard
     *         
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public CreditCard addCard(CreditCard cardToAdd) throws EatStreetApiException {
        return this.getUser().addCard(cardToAdd);
    }
    
    /**
     * Remove a user's saved CreditCard
     * 
     * @param cardToRemove The CreditCard to remove
     * 
     * @return true if removed successfully, false otherwise
     * 
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public boolean removeCard(CreditCard cardToRemove) throws EatStreetApiException {
        return this.getUser().removeCard(cardToRemove);
    }
    
    /**
     * Get the user's order history
     * 
     * @return A List of Order objects representing the order history of the user
     * 
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public List<Order> getOrderHistory() throws EatStreetApiException {
        return this.getUser().getOrderHistory(true);
    }
    
    /**
     * Find nearby restaurants
     * 
     * @param userAddress An Address object corresponding to the user's address
     * @param delivery The type of ordering done by the restaurant (e.g. pickup, delivery, or both)
     * @param radius The delivery radius (in miles)
     * @param extraSearchTerms Variable number of String arguments containing extra terms to filter restaurants by
     * 
     * @return A list of restaurants matching the search criteria
     * 
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public List<Restaurant> findRestaurants(Address userAddress, OrderType delivery, int radius, String... extraSearchTerms) throws EatStreetApiException {
        return this.findRestaurants(userAddress.toString(), delivery, radius, extraSearchTerms);
    }
    
    /**
     * Find nearby restaurants
     * 
     * @param streetAddress A street address including city, state, and zip (e.g. 316 W. Washington Ave., Madison, WI)
     * @param delivery The type of ordering done by the restaurant (e.g. pickup, delivery, or both)
     * @param radius The delivery radius (in miles)
     * @param extraSearchTerms Variable number of String arguments containing extra terms to filter restaurants by
     * 
     * @return A list of restaurants matching the search criteria
     * 
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public List<Restaurant> findRestaurants(String streetAddress, OrderType delivery, int radius, String... extraSearchTerms) throws EatStreetApiException {
        // Great a list of request parameters
        List<NameValuePair> getParams = new ArrayList<>();
        getParams.add(new BasicNameValuePair("street-address", streetAddress));
        getParams.add(new BasicNameValuePair("pickup-radius", Integer.toString(radius)));
        getParams.add(new BasicNameValuePair("method", delivery.toString()));
        
        // Check whether extra search terms were given and append them if so
        if( extraSearchTerms.length > 0 ) {
            getParams.add(new BasicNameValuePair("search", Arrays.toString(extraSearchTerms)));
        }
        
        // Attempt to parse the JSON from the response stream. This is wrapped in a try-with block
        // to ensure that "response" is closed no matter if an exception arises or not
        try(Reader response = EatStreetRequestor.makeGetRequest(ApiEndpoint.RESTAURANT_SEARCH, getParams)) {
            // The JSON returned by the search restaurant end point contains the data for the restaurants
            // within a nested JSON array with name "restaurants". Since the other data in the JSON is
            // unneeded, the JSON is first manually parsed to get the array, then passed to GSON to
            // parse the Restaurant objects
            JsonObject json = JsonParser.parseReader(response).getAsJsonObject();
            return Arrays.asList(JsonConverter.fromJson(json.getAsJsonArray("restaurants"), Restaurant[].class));
        }
        catch(IOException e) {
            throw new EatStreetApiException("Unable to close the HTTP response object");
        }
    }
    
    /**
     * Find nearby restaurants
     * 
     * @param latitude The user's latitude
     * @param longitude The user's longitude
     * @param delivery The type of ordering done by the restaurant (e.g. pickup or delivery)
     * @param radius The delivery radius (in miles)
     * @param extraSearchTerms Variable number of String arguments containing extra terms to filter restaurants by
     * 
     * @return A list of restaurants matching the search criteria
     * 
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public List<Restaurant> findRestaurants(Double latitude, Double longitude, OrderType delivery, int radius, String... extraSearchTerms) throws EatStreetApiException {
        
        List<NameValuePair> getParams = new ArrayList<>();
        getParams.add(new BasicNameValuePair("latitude", latitude.toString()));
        getParams.add(new BasicNameValuePair("longitude", longitude.toString()));
        getParams.add(new BasicNameValuePair("pickup-radius", Integer.toString(radius)));
        getParams.add(new BasicNameValuePair("method", delivery.toString()));
        
        // Check whether extra search terms were given and append them if so
        if( extraSearchTerms.length > 0 ) {
            getParams.add(new BasicNameValuePair("search", Arrays.toString(extraSearchTerms)));
        }
        
        // Attempt to parse the JSON from the response stream. This is wrapped in a try-with block
        // to ensure that "response" is closed no matter if an exception arises or not
        try(Reader response = EatStreetRequestor.makeGetRequest(ApiEndpoint.RESTAURANT_SEARCH, getParams)) {
            // The JSON returned by the search restaurant end point contains the data for the restaurants
            // within a nested JSON array with name "restaurants". Since the other data in the JSON is
            // unneeded, the JSON is first manually parsed to get the array, then passed to GSON to
            // parse the Restaurant objects
            JsonObject json = JsonParser.parseReader(response).getAsJsonObject();
            return Arrays.asList(JsonConverter.fromJson(json.getAsJsonArray("restaurants"), Restaurant[].class));
        }
        catch(IOException e) {
            throw new EatStreetApiException("Unable to close the response reader");
        }
    }
}
