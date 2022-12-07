package fr.sfc.framework.entity;

import fr.sfc.framework.persistence.annotation.Column;
import fr.sfc.framework.persistence.annotation.Entity;
import fr.sfc.framework.persistence.annotation.Id;
import fr.sfc.framework.persistence.annotation.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    private Integer id;
    private String username;

    public User() { }
    
    public User(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
