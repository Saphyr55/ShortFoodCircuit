package fr.sfc.api.common;

import static org.apache.lucene.util.SloppyMath.haversinMeters;

/**
 *
 */
public final class Location {

    private String name;
    private float longitude;
    private float latitude;

    /**
     * Create a location
     *
     * @param name      String
     * @param longitude float
     * @param latitude  float
     * @return location
     */
    public static Location of(String name, float longitude, float latitude) {
        return new Location(name, longitude, latitude);
    }

    /**
     * Copy a location
     *
     * @param location Location
     * @return location
     */
    public static Location of(Location location) {
        return new Location(location.name, location.longitude, location.latitude);
    }

    /**
     * Constructor location
     *
     * @param name      String
     * @param longitude float
     * @param latitude  float
     */
    public Location(String name, float longitude, float latitude) {
        this.name = name;
        this.latitude = latitude;
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

    /**
     * @return name String
     */
    public String getName() {
        return name;
    }

    /**
     * @return longitude float
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @return latitude float
     */
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