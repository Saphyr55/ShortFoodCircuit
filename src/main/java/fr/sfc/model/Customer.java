package fr.sfc.model;

import fr.sfc.core.Location;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.Id;
import fr.sfc.persistence.Table;

@Entity
@Table(name = "client")
public class Customer extends EC {

    @Id
    private int id;

    public Customer(int id, Location location, String name, String phoneNumber, String address) {
    	super(location, name, phoneNumber, address);
    }

}