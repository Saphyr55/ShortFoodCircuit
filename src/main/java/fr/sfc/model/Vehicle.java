package fr.sfc.model;

public final class Vehicle extends Entity {
	
    private String registration;
    private float maxWeight;

    public Vehicle(int id, String registration, float maxWeight) {
    	super(id);
    }
    
    public String getRegistration() {
        return registration;
    }
    
    public float getMaxWeight() {
        return maxWeight;
    }
    
    public void setRegistration(String registration) {
        this.registration = registration;
    }
    
    public void setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
    }
}