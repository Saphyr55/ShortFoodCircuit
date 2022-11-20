package fr.sfc.model.entity;

import fr.sfc.database.DatabaseManager;
import fr.sfc.persistence.Column;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.Id;
import fr.sfc.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "productTour")
public final class ProductTour {

    @Id
    @Column(name = "idProductTour")
    private int id;
    @Column(name = "startDate")
    private LocalDateTime startDateTime;
    @Column(name = "endDate")
    private LocalDateTime endDateTime;
    @Column(name = "name")
    private String name;
    @Column(name = "weight")
    private float weight;
    
    public ProductTour(int id, LocalDateTime startDate, LocalDateTime endDateTime, String name, float weight) {
    	this.id = id;
        this.startDateTime = startDate;
        this.endDateTime = endDateTime;
        this.name = name;
        this.weight = weight;
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