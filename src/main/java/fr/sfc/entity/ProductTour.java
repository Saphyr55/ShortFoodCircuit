package fr.sfc.entity;

import fr.sfc.framework.persistence.annotation.*;

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

    @ForeignKey(entity = Company.class)
    private Integer idCompany;

    @Column(name = "SIRET")
    @ForeignKey(
            type = ForeignKey.Type.Unique,
            entity = Company.class)
    private Integer SIRET;

    @ForeignKey(entity = Vehicle.class)
    private Integer idVehicle;

    @Column(name = "matriculation")
    @ForeignKey(
            type = ForeignKey.Type.Unique,
            entity = Vehicle.class)
    private String matriculation;

    public ProductTour() {}

    public ProductTour(LocalDateTime startDate,
                       LocalDateTime endDateTime,
                       String name,
                       Float weight,
                       Integer idCompany,
                       Integer SIRET,
                       Integer idVehicle,
                       String matriculation) {
        this.startDateTime = startDate;
        this.endDateTime = endDateTime;
        this.name = name;
        this.weight = weight;
        this.idVehicle = idVehicle;
        this.matriculation = matriculation;
        this.SIRET = SIRET;
        this.idCompany = idCompany;
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

    public Float getWeight() {
        return weight;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
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

    public Integer getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(Integer idVehicle) {
        this.idVehicle = idVehicle;
    }

    public String getMatriculation() {
        return matriculation;
    }

    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }

    @Override
    public String toString() {
        return "ProductTour{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", idCompany=" + idCompany +
                ", SIRET=" + SIRET +
                ", idVehicle=" + idVehicle +
                ", matriculation='" + matriculation + '\'' +
                '}';
    }

}