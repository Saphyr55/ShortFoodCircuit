package fr.sfc.entity;

import fr.sfc.framework.persistence.annotation.*;

@Entity
@Table(name = "producer")
public class Producer {

    @Id
    @Column(name = "idProducer")
    private Integer id;

    @Column(name = "password")
    private String password;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @ForeignKey(entity = Company.class)
    private Integer idCompany;

    @Column(name = "SIRET")
    @ForeignKey(
            type = ForeignKey.Type.Unique,
            entity = Company.class)
    private Integer SIRET;

    public Producer() { }

    public Producer(String password,
                    String lastname,
                    String firstname,
                    Integer idCompany,
                    Integer SIRET) {

        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.idCompany = idCompany;
        this.SIRET = SIRET;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", idCompany=" + idCompany +
                ", SIRET=" + SIRET +
                '}';
    }
}
