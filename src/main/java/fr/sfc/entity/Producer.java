package fr.sfc.entity;

import fr.sfc.api.persistence.annotation.*;

@Entity
@Table(name = "producer")
public class Producer {

    @Id
    @Column(name = "idProducer")
    private Integer id;

    @Column(name = "password")
    private String password;

    @ForeignKey(entity = Company.class)
    private Integer idCompany;

    @Column(name = "SIRET")
    @ForeignKey(
            type = ForeignKey.Type.Column,
            entity = Company.class)
    private Integer SIRET;

    public Producer() { }

    public Producer(String password, Integer idCompany, Integer SIRET) {
        this.password = password;
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

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", idCompany=" + idCompany +
                ", SIRET=" + SIRET +
                '}';
    }
}
