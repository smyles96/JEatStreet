package io.github.smyles96.eatstreet.exception;

public class EatStreetApiException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Create a new EatStreetApiException object with a specified error message
     * @param message The error message
     */
    public EatStreetApiException(String message) {
        super(message);
    }
    
}
