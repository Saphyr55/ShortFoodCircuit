package fr.sfc.model.entity;

import fr.sfc.api.persistence.annotation.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "idCustomer")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "longitude")
    private Float longitude;
    @Column(name = "latitude")
    private Float latitude;
    @ForeignKey(entity = ProductTour.class)
    private Integer idProductTour;

    public Customer() {
    }

    public Customer(String name, String phoneNumber, String address, float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    
    public Customer(int id, String name, String phoneNumber, String address, float longitude, float latitude) {
        this(name, phoneNumber, address, longitude, latitude);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", idProductTour=" + idProductTour +
                '}';
    }
}