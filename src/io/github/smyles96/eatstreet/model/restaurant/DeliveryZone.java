package io.github.smyles96.eatstreet.model.restaurant;

import java.io.Serializable;
import java.util.List;

/**
 * This model class represents a delivery zone for a restaurant
 * 
 * @author smyles96
 */
public class DeliveryZone implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * INSTANCE VARIABLES
     */
    private String apiKey;
    private String description;
    private List<String> zips;
    private List<LatLongPoint> points;
    private List<LatLongPoint> holePoints;
    private Double maxRadius;
    
    /*
     * CONSTRUCTOR(S)
     */
    public DeliveryZone() {}

    /*
     * GETTERS and SETTERS
     */
    
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getZips() {
        return zips;
    }

    public void setZips(List<String> zips) {
        this.zips = zips;
    }

    public List<LatLongPoint> getPoints() {
        return points;
    }

    public void setPoints(List<LatLongPoint> points) {
        this.points = points;
    }

    public List<LatLongPoint> getHolePoints() {
        return holePoints;
    }

    public void setHolePoints(List<LatLongPoint> holePoints) {
        this.holePoints = holePoints;
    }

    public Double getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(Double maxRadius) {
        this.maxRadius = maxRadius;
    }
}
