package fr.sfc.model;

import fr.sfc.core.Location;
import fr.sfc.persistence.Id;


public abstract class EC {

    private String name;
    private String phoneNumber;
    private String address;
    private float longitude;
    private float latitude;
    
    public EC(Location location, String name, String phoneNumber, String address) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }  
    
    protected String getName() {
        return name;
    }
    
    protected String getPhoneNumber() {
        return phoneNumber;
    }
    
    protected String getAddress() {
        return address;
    }
    
    protected void setName(String name) {
        this.name = name;
    }
    
    protected void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    protected void setAddress(String address) {
        this.address = address;
    }
    
}