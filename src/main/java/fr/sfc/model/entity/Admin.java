package fr.sfc.model.entity;

import fr.sfc.persistence.Column;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.Id;
import fr.sfc.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "idAdmin")
    private int id;
    @Column(name = "password")
    private String password;

    public Admin(int id, String password) {
        this.id = id;
    }

}
