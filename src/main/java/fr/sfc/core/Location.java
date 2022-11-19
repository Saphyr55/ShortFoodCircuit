package fr.sfc.core;

/**
 *
 */
public final class Location {

	private String name;
    private float longitude;
    private float latitude;
    
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
        float STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945f;

        float lat1 = (float) Math.toRadians(this.latitude);
        float lon1 = (float) Math.toRadians(this.longitude);
        float lat2 = (float) Math.toRadians(location.latitude);
        float lon2 = (float) Math.toRadians(location.longitude);

        float angle = (float) Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        float nauticalMiles = (float) (60 * Math.toDegrees(angle));
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
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