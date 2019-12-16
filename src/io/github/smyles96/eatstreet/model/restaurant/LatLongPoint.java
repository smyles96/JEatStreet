package io.github.smyles96.eatstreet.model.restaurant;

import java.io.Serializable;

/**
 * This model class represents a geographic coordinate
 * 
 * @author smyles96
 */
public class LatLongPoint implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*
     * INSTANCE VARIABLES
     */
    private Double latitude;
    private Double longitude;
    
    /*
     * CONSTRUCTOR(S)
     */
    public LatLongPoint() {}
    
    public LatLongPoint(double latitude, double longitude) {
        this.latitude = new Double(latitude);
        this.longitude = new Double(longitude);
    }
    
    public LatLongPoint(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*
     * GETTERS and SETTERS
     */
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = new Double(latitude);
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = new Double(longitude);
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    /*
     * INSTANCE METHODS
     */
}
