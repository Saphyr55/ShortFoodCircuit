package fr.sfc.model;

import fr.sfc.core.Location;

public final class Enterprise extends EC {
	
	private String SIRET;
	
    public Enterprise(int id, Location location, String name, String phoneNumber, String address) {
		super(id, location, name, phoneNumber, address);
	}
    
    public String getSIRET() {
        return SIRET;
    }
    
    public void setSIRET(String SIRET) {
        this.SIRET = SIRET;
    }
    
}