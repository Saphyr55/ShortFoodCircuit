package fr.sfc.framework.entity;

import fr.sfc.framework.persistence.annotation.Column;
import fr.sfc.framework.persistence.annotation.Entity;
import fr.sfc.framework.persistence.annotation.Id;
import fr.sfc.framework.persistence.annotation.Table;

@Entity
@Table(name = "administrator")
public class Admin {

    @Id
    @Column(name = "idAdmin")
    private Integer id;
    
    public Admin() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
