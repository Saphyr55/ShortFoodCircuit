package fr.sfc.framework.persistence;

import fr.sfc.framework.persistence.annotation.Entity;
import fr.sfc.framework.persistence.annotation.Id;

@Entity
public class DefaultEntity {

    @Id
    private Integer id;

    public DefaultEntity() { }

}
