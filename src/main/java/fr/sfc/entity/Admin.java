package fr.sfc.entity;

import fr.sfc.framework.persistence.annotation.Column;
import fr.sfc.framework.persistence.annotation.Entity;
import fr.sfc.framework.persistence.annotation.Id;
import fr.sfc.framework.persistence.annotation.Table;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "idAdmin")
    private Integer id;
    @Column(name = "password")
    private String password;

    public Admin() { }

    public Admin(String password) {
        this.password = password;
    }

    public Admin(int id, String password) {
        this(password);
        this.id = id;
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

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }

}
