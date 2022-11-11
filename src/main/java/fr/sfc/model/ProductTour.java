package fr.sfc.model;

import java.time.LocalDateTime;


public final class ProductTour extends Entity {
	
    private LocalDateTime startDateTime;
    private String name;
    private float weight;
    
    public ProductTour(int id, LocalDateTime startDate, String name, float weight) {
    	super(id);
    }
    
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    
    public String getName() {
        return name;
    }
    
    public float getWeight() {
        return weight;
    }
    
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setWeight(float weight) {
        this.weight = weight;
    }

}