package fr.sfc.model;

import java.time.LocalTime;

public final class Order extends Entity {
    
	private LocalTime startTime;
    private LocalTime endTime;
    private String wording;
    private float weight;
    

    public Order(int id, LocalTime startTime, LocalTime endTime, String wording, float weight) {
    	super(id);
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public String getWording() {
        return wording;
    }
    
    public float getWeight() {
        return weight;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public void setWording(String wording) {
        this.wording = wording;
    }
    
    public void setWeight(float weight) {
        this.weight = weight;
    }
}