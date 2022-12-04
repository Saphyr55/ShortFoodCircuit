package fr.sfc.model.entity;

import fr.sfc.api.persistence.annotation.*;

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

    @Column(name = "idCompany")
    @ForeignKey(
            type = ForeignKey.Type.Id,
            entity = Company.class)
    private Integer idCompany;

    @Column(name = "SIRET")
    @ForeignKey(
            type = ForeignKey.Type.Column,
            entity = Company.class)
    private Integer SIRET;

    public Vehicle() { }

    public Vehicle(String matriculation,
                   Float maxWeight,
                   Integer idCompany,
                   Integer SIRET) {
        this.matriculation = matriculation;
        this.maxWeight = maxWeight;
        this.SIRET = SIRET;
        this.idCompany = idCompany;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMaxWeight(Float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
    }

    public Integer getSIRET() {
        return SIRET;
    }

    public void setSIRET(Integer SIRET) {
        this.SIRET = SIRET;
    }

    public String getMatriculation() {
        return matriculation;
    }

    public Float getMaxWeight() {
        return maxWeight;
    }

    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", matriculation='" + matriculation + '\'' +
                ", maxWeight=" + maxWeight +
                ", idCompany=" + idCompany +
                ", SIRET=" + SIRET +
                '}';
    }
}