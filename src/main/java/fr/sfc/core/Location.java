package fr.sfc.core;

public final class Location {
    
	private String name;
    private float longitude;
    private float latitude;
    
    public Location(String name, float longitude, float latitude) {
    	
    }
    
    public void distanceTo(Location localtion) {
    	
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