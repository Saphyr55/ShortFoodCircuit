package fr.sfc.model;

import fr.sfc.core.Location;

public abstract class EC extends Entity {
	
    private String name;
    private String phoneNumber;
    private String address;
    
    public EC(int id, Location location, String name, String phoneNumber, String address) {
    	super(id);
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