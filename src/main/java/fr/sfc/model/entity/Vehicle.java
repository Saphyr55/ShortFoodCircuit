package fr.sfc.model.entity;

import fr.sfc.api.persistence.annotation.Column;
import fr.sfc.api.persistence.annotation.Entity;
import fr.sfc.api.persistence.annotation.Id;
import fr.sfc.api.persistence.annotation.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @Column(name = "idVehicle")
    private Integer id;
    @Column(name = "matriculation")
    private String matriculation;
    @Column(name = "maxWeight")
    private Float maxWeight;

    public Vehicle() { }

    public Vehicle(String matriculation, Float maxWeight) {
        this.matriculation = matriculation;
        this.maxWeight = maxWeight;
    }

    public Vehicle(Integer id, String matriculation, Float maxWeight) {
        this(matriculation, maxWeight);
        this.id = id;
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