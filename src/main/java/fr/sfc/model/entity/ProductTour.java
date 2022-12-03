package fr.sfc.model.entity;

import fr.sfc.api.persistence.annotation.Column;
import fr.sfc.api.persistence.annotation.Entity;
import fr.sfc.api.persistence.annotation.Id;
import fr.sfc.api.persistence.annotation.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "productTour")
public class ProductTour {

    @Id
    @Column(name = "idProductTour")
    private Integer id;
    @Column(name = "startDate")
    private LocalDateTime startDateTime;
    @Column(name = "endDate")
    private LocalDateTime endDateTime;
    @Column(name = "name")
    private String name;
    @Column(name = "weight")
    private Float weight;

    public ProductTour() {
    }

    public ProductTour(LocalDateTime startDate, LocalDateTime endDateTime, String name, Float weight) {
        this.startDateTime = startDate;
        this.endDateTime = endDateTime;
        this.name = name;
        this.weight = weight;
    }
    
    public ProductTour(Integer id, LocalDateTime startDateTime, LocalDateTime endDateTime, String name, Float weight) {
        this(startDateTime, endDateTime, name, weight);
        this.id = id;
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