package fr.sfc.model;

import fr.sfc.core.Location;
import fr.sfc.persistence.Entity;
import fr.sfc.persistence.Id;
import fr.sfc.persistence.Table;

@Entity
@Table(name = "entreprise")
public final class Enterprise extends EC {

    @Id
    private int id;
	private String SIRET;
	
    public Enterprise(int id, Location location, String name, String phoneNumber, String address) {
        super(location, name, phoneNumber, address);
        this.id = id;
	}
    
    public String getSIRET() {
        return SIRET;
    }
    
    public void setSIRET(String SIRET) {
        this.SIRET = SIRET;
    }
    
}