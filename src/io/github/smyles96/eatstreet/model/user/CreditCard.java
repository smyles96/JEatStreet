package io.github.smyles96.eatstreet.model.user;

import java.io.Serializable;
import java.util.Objects;
import io.github.smyles96.eatstreet.model.EatStreetModel;

/**
 * This model class represents a user's credit card. 
 * 
 * Some example code:
 * <pre>{@code
 * // Create a new card
 * CreditCard card = new CreditCard();
 * card.setCardholderName("John Doe");
 * card.setCardholderStreetAddress("1234 Example Lane, Blacksburg, VA");
 * card.setCardholderZip("24060");
 * card.setCardNumber("1234123412341234");
 * card.setCvv("123");
 * }</pre>
 * 
 * @author smyles96
 */
public class CreditCard extends EatStreetModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * INSTANCE VARIABLES
     */
    private String nickname;
    private String cardholderName;
    private String cardholderStreetAddress;
    private String cardholderZip;
    private transient String cardNumber;
    private String lastFour;
    private String cvv;
    private String expirationMonth;
    private String expirationYear;
    
    
    /*
     * CONSTRUCTOR(S)
     */
    
    /**
     * Create a new CreditCard object
     */
    public CreditCard() {
        super("");
    }

    /**
     * Create a new CreditCard object
     * 
     * @param nickname The nickname for the card
     * @param cardholderName The cardholder's name
     * @param cardholderStreetAddress The cardholder's street address
     * @param cardholderZip The cardholder's zip code
     * @param lastFour The last four digits of the card number
     * @param cvv The card's cvv code
     * @param expirationMonth The card's expiration month
     * @param expirationYear The card's expiration year
     */
    public CreditCard(String nickname, String cardholderName,
                                       String cardholderStreetAddress,
                                       String cardholderZip,
                                       String lastFour,
                                       String cvv,
                                       String expirationMonth,
                                       String expirationYear)
    {
        super("");
        this.nickname = nickname;
        this.cardholderName = cardholderName;
        this.cardholderStreetAddress = cardholderStreetAddress;
        this.cardholderZip = cardholderZip;
        this.lastFour = lastFour;
        this.cvv = cvv;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    /*
     * GETTERS and SETTERS
     */
    
    
    public String getNickname() {
        
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCardholderName() {
        
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardholderStreetAddress() {
        
        return cardholderStreetAddress;
    }

    public void setCardholderStreetAddress(String cardholderStreetAddress) {
        this.cardholderStreetAddress = cardholderStreetAddress;
    }

    public String getCardholderZip() {
        
        return cardholderZip;
    }

    public void setCardholderZip(String cardholderZip) {
        this.cardholderZip = cardholderZip;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String number) {
        // Replace all non-digit characters to empty string. This is intended
        // to remove all formatting characters from the card number (like dashes)
        this.cardNumber = number.replaceAll("\\D", "");
    }

    public String getLastFour() {
        
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }
    
    
    /*
     * INSTANCE METHODS
     */
    
    /**
     * Checks whether this CreditCard equals another CreditCard.
     * Comparison is checked by checking whether the last four digits, the cvv, the
     * expiration month, and the expiration year match the other card's values
     * 
     * @param other Another CreditCard object to compare
     * 
     * @return true if they are equal, false otherwise
     */
    public boolean equals(Object other) {
        // Check if memory address matches
        if (this == other) {  
            return true;  
        }  
        
        if (other instanceof CreditCard) {  
            CreditCard otherCard = (CreditCard) other;
            
            String thisCardNum = (this.getCardNumber() == null) ? "" : this.getCardNumber();
            String otherNum = (otherCard.getCardNumber() == null) ? "" : otherCard.getCardNumber();
            
            String thisFour = (this.getLastFour() == null) ? "" : this.getLastFour();
            String otherFour = (otherCard.getLastFour() == null) ? "" : otherCard.getLastFour();
            
            String thisExpMonth = (this.getExpirationMonth() == null) ? "" : this.getExpirationMonth();
            String otherExpMonth = (otherCard.getExpirationMonth() == null) ? "" : otherCard.getExpirationMonth();
            
            String thisExpYear = (this.getExpirationYear() == null) ? "" : this.getExpirationYear();
            String otherExpYear = (otherCard.getExpirationYear() == null) ? "" : otherCard.getExpirationYear();
            
            
            return (thisCardNum.equals(otherNum)) && (thisFour.equals(otherFour)) &&
                   (thisExpMonth.equals(otherExpMonth)) && (thisExpYear.equals(otherExpYear));
        } 
        
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.cardholderName);
        hash = 29 * hash + Objects.hashCode(this.cardholderStreetAddress);
        hash = 29 * hash + Objects.hashCode(this.cardholderZip);
        hash = 29 * hash + Objects.hashCode(this.cardNumber);
        hash = 29 * hash + Objects.hashCode(this.lastFour);
        hash = 29 * hash + Objects.hashCode(this.cvv);
        hash = 29 * hash + Objects.hashCode(this.expirationMonth);
        hash = 29 * hash + Objects.hashCode(this.expirationYear);
        return hash;
    }
    
    /**
     * Return a String representation of this CreditCard
     * 
     * @return The String representation of this CreditCard object
     */
    public String toString() {
        return String.format("Card Nickname: %s\nLast Four: %s\nExpires: %s/%s",
                                                               getNickname(),
                                                               getLastFour(),
                                                               getExpirationMonth(),
                                                               getExpirationYear());
    }
}
