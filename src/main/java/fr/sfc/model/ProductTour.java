package fr.sfc.model;

import fr.sfc.persistence.Column;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.Id;
import fr.sfc.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tournee")
public final class ProductTour {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "heureDebut")
    private LocalDateTime startDateTime;
    @Column(name = "heureFin")
    private LocalDateTime endDateTime;
    @Column(name = "nom")
    private String name;
    @Column(name = "poids")
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