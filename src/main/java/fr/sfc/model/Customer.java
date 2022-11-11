package fr.sfc.model;

import fr.sfc.core.Location;

public class Customer extends EC {
	
    public Customer(int id, Location location, String name, String phoneNumber, String address) {
    	super(id, location, name, phoneNumber, address);
    }

}