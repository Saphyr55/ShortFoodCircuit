package fr.sfc.entity;

import fr.sfc.framework.persistence.annotation.Column;
import fr.sfc.framework.persistence.annotation.Entity;
import fr.sfc.framework.persistence.annotation.Id;
import fr.sfc.framework.persistence.annotation.Table;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "idCompany")
    private Integer id;

    @Column(name = "nameOwner")
    private String nameOwner;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "SIRET")
    private Integer SIRET;

    public Company() { }

    public Company(String nameOwner,
                   String phoneNumber,
                   String address,
                   float longitude,
                   float latitude) {

        this.longitude = longitude;
        this.latitude = latitude;
        this.nameOwner = nameOwner;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Integer getSIRET() {
        return SIRET;
    }

    public void setSIRET(Integer SIRET) {
        this.SIRET = SIRET;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", nameOwner='" + nameOwner + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", SIRET='" + SIRET + '\'' +
                '}';
    }
}