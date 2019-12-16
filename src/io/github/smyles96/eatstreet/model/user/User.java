package io.github.smyles96.eatstreet.model.user;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import io.github.smyles96.eatstreet.exception.EatStreetApiException;
import io.github.smyles96.eatstreet.model.EatStreetModel;
import io.github.smyles96.eatstreet.model.order.Order;
import io.github.smyles96.eatstreet.util.http.ApiEndpoint;
import io.github.smyles96.eatstreet.util.http.EatStreetRequestor;
import io.github.smyles96.eatstreet.util.json.JsonConverter;

/**
 * This model class represents a user on EatStreet.
 * 
 * @author smyles96
 */
public class User extends EatStreetModel {
    
    /*
     * INSTANCE VARIABLES
     */
    private transient int id;
    private String email;
    private String password;
    private String phone;
    private String firstName;
    private String middleName;
    private String lastName;
    private transient int securityQuestionNumber;
    private transient String securityAnswer;
    private transient List<Order> orderHistory;
    private List<Address> savedAddresses;
    
    @SerializedName("creditCards")
    private List<CreditCard> savedCards;
    
    public User() {
        super("");
        
        savedAddresses = new ArrayList<>();
        savedCards = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Address> getSavedAddresses() {
        return savedAddresses;
    }

    public void setSavedAddresses(List<Address> savedAddresses) {
        this.savedAddresses = savedAddresses;
    }

    public List<CreditCard> getSavedCards() {
        return savedCards;
    }

    public void setSavedCards(List<CreditCard> creditCards) {
        this.savedCards = creditCards;
    }

    public int getSecurityQuestionNumber() {
        return securityQuestionNumber;
    }

    public void setSecurityQuestionNumber(int securityQuestionNumber) {
        this.securityQuestionNumber = securityQuestionNumber;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
    
    /**
     * Get the user's order history
     * 
     * @return A List of Order objects representing the order history of the user
     * 
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public List<Order> getOrderHistory() throws EatStreetApiException {
        if( orderHistory == null ) {
            // Call the other function to retrieve the data (prevents redundant code)
            this.getOrderHistory(true);
        }
        
        return orderHistory;
    }
    
    /**
     * Refreshes the user's order history.
     * 
     * Getting the user's order history requires extra calls to the EatStreet API.
     * To avoid extra calls, which can max out the rate limit, the user's history
     * is stored locally after being retrieved for the first time, after that
     * the locally stored order history list is returned. Calling this method
     * with refresh set to true will force a call to the EatStreet API to update
     * the locally stored order history.
     * 
     * @param refresh Whether to refresh the user's current order history
     * 
     * @return A List of Order objects representing the order history of the user
     * 
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public List<Order> getOrderHistory(boolean refresh) throws EatStreetApiException {
        if( orderHistory == null || refresh ) {
            // Call the EatStreet API to get this user's order history
            try(Reader response = EatStreetRequestor.makeGetRequest(ApiEndpoint.ORDER_HISTORY, null, EatStreetRequestor.getUserApiKey())) {
                JsonArray json = JsonParser.parseReader(response).getAsJsonArray();
                orderHistory = Arrays.asList(JsonConverter.fromJson(json, Order[].class));
            }
            catch (IOException e) {
                throw new EatStreetApiException("Unable to close the HTTP response object");
            }
        }
        
        return orderHistory;
    }
    
    /*
     * INSTANCE METHODS
     */
    
    /**
     * Adds an Address to the user's account
     * 
     * @param newAddress The new Address to add
     * 
     * @return The Address that was added or, if Address was already registered
     *         for the user, the already registered Address
     * 
     * @throws EatStreetApiException If the request is unable to be made or was corrupted
     */
    public Address addAddress(Address newAddress) throws EatStreetApiException {
        // Ensure the address isn't already saved for this user
        for(Address savedAddress : savedAddresses) {
            if(savedAddress.equals(newAddress)) {
                return savedAddress;
            }
        }
        
        try(Reader response = EatStreetRequestor.makePostRequest(ApiEndpoint.ADD_ADDRESS, JsonConverter.toJson(newAddress), EatStreetRequestor.getUserApiKey())) {
            // Parse the response as a JSON object
            JsonObject json = JsonParser.parseReader(response).getAsJsonObject();
            
            // Extract the apiKey JSON property as a Java String
            Address returnedAddress = JsonConverter.fromJson(json, Address.class);
            
            savedAddresses.add(returnedAddress);
            return returnedAddress;
        }
        catch (IOException e) {
            throw new EatStreetApiException("Unable to close the HTTP response object");
        }
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
        // Check to see if the user has such an address
        if(!savedAddresses.contains(addressToRemove)) {
            return false;
        }
        
        try(Reader response = EatStreetRequestor.makePostRequest(ApiEndpoint.REMOVE_ADDRESS, "", EatStreetRequestor.getUserApiKey(), addressToRemove.getApiKey())) {
            // Remove address from local list
            savedAddresses.remove(addressToRemove);
            
            return true;
        }
        catch (IOException e) {
            throw new EatStreetApiException("Unable to close the HTTP response object");
        }
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
        // Ensure the address isn't already saved for this user
        for(CreditCard savedCard : savedCards) {
            if(savedCard.equals(cardToAdd)) {
                return savedCard;
            }
        }
        
        // When adding a card, the card number must be included
        JsonObject cardData = JsonConverter.toJsonObject(cardToAdd);
        cardData.addProperty("cardNumber", cardToAdd.getCardNumber());
        
        try(Reader response = EatStreetRequestor.makePostRequest(ApiEndpoint.ADD_CARD, cardData.toString(), EatStreetRequestor.getUserApiKey())) {
            // Parse the response as a JSON object
            JsonObject json = JsonParser.parseReader(response).getAsJsonObject();
            
            // Extract the apiKey JSON property as a Java String
            CreditCard returnedCard = JsonConverter.fromJson(json, CreditCard.class);
            
            savedCards.add(returnedCard);
            return returnedCard;
        }
        catch (IOException e) {
            throw new EatStreetApiException("Unable to close the HTTP response object");
        }
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
        // Check to see if the user has such an address
        if(!savedCards.contains(cardToRemove)) {
            return false;
        }
        
        try(Reader response = EatStreetRequestor.makePostRequest(ApiEndpoint.REMOVE_CARD, "", EatStreetRequestor.getUserApiKey(), cardToRemove.getApiKey())) {
            // Remove address from local list
            savedCards.remove(cardToRemove);
            
            return true;
        }
        catch (IOException e) {
            throw new EatStreetApiException("Unable to close the HTTP response object");
        }
    }
}
