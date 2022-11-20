package fr.sfc.model;

import fr.sfc.persistence.Column;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.Id;
import fr.sfc.persistence.Table;

@Entity
@Table(name = "vehicle")
public final class Vehicle {

    @Id
    @Column(name = "idVehicle")
    private int id;
    @Column(name = "matriculation")
    private String matriculation;
    @Column(name = "maxWeight")
    private float maxWeight;

    public Vehicle(int id, String matriculation, float maxWeight) {
    	this.id = id;
        this.matriculation = matriculation;
        this.maxWeight = maxWeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatriculation() {
        return matriculation;
    }
    
    public float getMaxWeight() {
        return maxWeight;
    }
    
    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }
    
    public void setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
    }
}