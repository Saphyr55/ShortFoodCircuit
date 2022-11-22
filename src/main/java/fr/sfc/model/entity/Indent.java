package fr.sfc.model.entity;

import fr.sfc.persistence.Column;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.Id;
import fr.sfc.persistence.Table;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "indent")
public class Indent {

    @Id
    @Column(name = "idIndent")
    private int id;

    @Column(name = "startDate")
    private LocalDateTime startTime;

    @Column(name = "endDate")
    private LocalDateTime endTime;

    @Column(name = "wording")
    private String wording;

    @Column(name = "weight")
    private float weight;

    public Indent(LocalDateTime startTime, LocalDateTime endTime, String wording, float weight) {
        this.endTime = endTime;
        this.startTime = startTime;
        this.wording = wording;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public String getWording() {
        return wording;
    }
    
    public float getWeight() {
        return weight;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public void setWording(String wording) {
        this.wording = wording;
    }
    
    public void setWeight(float weight) {
        this.weight = weight;
    }
}