package fr.sfc.entity;

import fr.sfc.framework.persistence.annotation.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordering")
public class Order {

    @Id
    @Column(name = "idOrder")
    private Integer id;

    @Column(name = "startDate")
    private LocalDateTime startLocalDateTime;

    @Column(name = "endDate")
    private LocalDateTime endLocalDateTime;

    @Column(name = "wording")
    private String wording;

    @Column(name = "weight")
    private Float weight;

    @ForeignKey(
            type = ForeignKey.Type.Id,
            entity = ProductTour.class)
    private Integer idProductTour;

    @ForeignKey(
            type = ForeignKey.Type.Id,
            entity = Customer.class)
    private Integer idCustomer;

    @ForeignKey(
            type = ForeignKey.Type.Id,
            entity = Company.class)
    private Integer idCompany;

    @Column(name = "SIRET")
    @ForeignKey(
            type = ForeignKey.Type.Unique,
            entity = Company.class)
    private String SIRET;

    public Order() { }

    public Order(LocalDateTime startLocalDateTime,
                 LocalDateTime endLocalDateTime,
                 String wording,
                 Float weight,
                 Integer idProductTour,
                 Integer idCustomer,
                 Integer idCompany,
                 String SIRET) {
        this.startLocalDateTime = startLocalDateTime;
        this.endLocalDateTime = endLocalDateTime;
        this.wording = wording;
        this.weight = weight;
        this.idProductTour = idProductTour;
        this.idCustomer = idCustomer;
        this.idCompany = idCompany;
        this.SIRET = SIRET;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartLocalDateTime() {
        return startLocalDateTime;
    }

    public LocalDateTime getEndLocalDateTime() {
        return endLocalDateTime;
    }

    public String getWording() {
        return wording;
    }

    public Float getWeight() {
        return weight;
    }

    public void setStartLocalDateTime(LocalDateTime startLocalDateTime) {
        this.startLocalDateTime = startLocalDateTime;
    }

    public void setEndLocalDateTime(LocalDateTime endLocalDateTime) {
        this.endLocalDateTime = endLocalDateTime;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }


    public Integer getIdProductTour() {
        return idProductTour;
    }

    public void setIdProductTour(Integer idProductTour) {
        this.idProductTour = idProductTour;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
    }

    public String getSIRET() {
        return SIRET;
    }

    public void setSIRET(String SIRET) {
        this.SIRET = SIRET;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", startTime=" + startLocalDateTime +
                ", endTime=" + endLocalDateTime +
                ", wording='" + wording + '\'' +
                ", weight=" + weight +
                ", idProductTour=" + idProductTour +
                ", idCustomer=" + idCustomer +
                ", idCompany=" + idCompany +
                ", SIRET='" + SIRET + '\'' +
                '}';
    }

}