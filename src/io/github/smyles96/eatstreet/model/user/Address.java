package io.github.smyles96.eatstreet.model.user;

import java.io.Serializable;
import io.github.smyles96.eatstreet.model.EatStreetModel;

/**
 * This class models a user's address on the EatStreet API.
 * 
 * Some example code:
 * <pre>{@code
 * // Create a new address for the order
 *  Address address = new Address();
 *  address.setStreetAddress("1234 Example Lane");
 *  address.setCity("Blacksburg");
 *  address.setState("VA");
 *  address.setZip("24060");
 * }</pre>
 * 
 * @author smyles96
 */
public class Address extends EatStreetModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*
     * INSTANCE VARIABLES
     */
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String aptNumber;
    private String latitude;
    private String longitude;
    
    /*
     * CONSTRUCTOR(S)
     */
    
    /**
     * Create a new Address object
     */
    public Address() {
        super("");
    }
    
    /**
     * Create a new Address object
     * 
     * @param streetAddress The street address
     * @param city The city
     * @param state The state
     * @param zip The zip code
     * @param aptNumber The possible apartment number
     * @param latitude The address's latitude as a String
     * @param longitude The address's longitude as a String
     */
    public Address(String streetAddress, String city, String state, String zip, String aptNumber, String latitude, String longitude) {
        super("");
        
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.aptNumber = aptNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /*
     * GETTERS AND SETTERS
     */

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

    public String getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    /*
     * INSTANCE METHODS
     */
    
    /**
     * Checks whether this Address equals another Address.
     * Comparison is checked by checking whether the lowercased street address,
     * city, state, zip, and apartment components match the other address's values
     * 
     * @param other Another Address object to compare
     * 
     * @return true if they are equal, false otherwise
     */
    public boolean equals(Object other) {
        // Check if memory address matches
        if (this == other) {  
            return true;  
        }  
        
        if (other instanceof Address) {  
            Address otherAddress = (Address) other;
            
            String thisStreet = (this.getStreetAddress() == null) ? "" : this.getStreetAddress().toLowerCase();
            String otherStreet = (otherAddress.getStreetAddress() == null) ? "" : otherAddress.getStreetAddress().toLowerCase();
            
            String thisCity = (this.getCity() == null) ? "" : this.getCity().toLowerCase();
            String otherCity = (otherAddress.getCity() == null) ? "" : otherAddress.getCity().toLowerCase();
            
            String thisState = (this.getState() == null) ? "" : this.getState().toLowerCase();
            String otherState = (otherAddress.getState() == null) ? "" : otherAddress.getState().toLowerCase();
            
            String thisZip = (this.getZip() == null) ? "" : this.getZip().toLowerCase();
            String otherZip = (otherAddress.getZip() == null) ? "" : otherAddress.getZip().toLowerCase();
            
            String thisApt = (this.getAptNumber() == null) ? "" : this.getAptNumber().toLowerCase();
            String otherApt = (otherAddress.getAptNumber() == null) ? "" : otherAddress.getAptNumber().toLowerCase();
            
            if (thisStreet.equals(otherStreet) &&
                thisCity.equals(otherCity) &&
                thisState.equals(otherState) &&
                thisZip.equals(otherZip) &&
                thisApt.equals(otherApt))
            {
                return true;
            }
        } 
        
        return false;
    }
    
    /**
     * Return a String representation of this Address
     * 
     * @return The String representation of this Address object
     */
    public String toString() {
        return String.format("%s, %s, %s", getStreetAddress(),
                                           getCity(),
                                           getState());
    }
}
