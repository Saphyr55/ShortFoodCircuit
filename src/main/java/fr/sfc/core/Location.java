package fr.sfc.core;

import static org.apache.lucene.util.SloppyMath.haversinMeters;

/**
 *
 */
public final class Location {

	private String name;
    private float longitude;
    private float latitude;

    public static Location Of(String name, float longitude, float latitude) {
        return new Location(name, longitude, latitude);
    }
    public static Location Of(Location location) {
        return new Location(location.name, location.longitude, location.latitude);
    }

    public Location(String name, float longitude, float latitude) {
        this.name = name;
        this.latitude  = latitude;
        this.longitude = longitude;
    }

    /**
     * Give the distance from two location
     *
     * @param location Location
     * @return distance float
     */
    public float distanceTo(Location location) {
        return (float) haversinMeters(this.latitude, this.longitude, location.latitude, location.longitude);
    }

    public String getName() {
        return name;
    }
    
    public float getLongitude() {
        return longitude;
    }
    
    public float getLatitude() {
        return latitude;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

}