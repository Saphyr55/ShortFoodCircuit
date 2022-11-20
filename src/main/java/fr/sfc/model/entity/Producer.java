package fr.sfc.model.entity;

import fr.sfc.persistence.Column;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.Id;
import fr.sfc.persistence.Table;

@Entity
@Table(name = "producer")
public class Producer {

    @Id
    @Column(name = "idProducer")
    private int id;

    @Column(name = "password")
    private String password;

}
