package fr.sfc.model.entity;

import fr.sfc.api.persistence.annotation.Column;
import fr.sfc.api.persistence.annotation.Entity;
import fr.sfc.api.persistence.annotation.Id;
import fr.sfc.api.persistence.annotation.Table;

@Entity
@Table(name = "producer")
public class Producer {

    @Id
    @Column(name = "idProducer")
    private Integer id;

    @Column(name = "password")
    private String password;

    public Producer() { }

    public Producer(String password) {
        this.password = password;
    }

    public Producer(int id, String password) {
        this.id = id;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
