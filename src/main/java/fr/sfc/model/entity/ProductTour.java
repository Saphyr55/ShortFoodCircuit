package fr.sfc.model.entity;

import fr.sfc.database.DatabaseManager;
import fr.sfc.persistence.Column;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.Id;
import fr.sfc.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "productTour")
public class ProductTour {

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
    
    public ProductTour(LocalDateTime startDate, LocalDateTime endDateTime, String name, float weight) {
        this.startDateTime = startDate;
        this.endDateTime = endDateTime;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
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