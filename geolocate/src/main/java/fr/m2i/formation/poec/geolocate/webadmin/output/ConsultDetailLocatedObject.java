package fr.m2i.formation.poec.geolocate.webadmin.output;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("consultDetail")
@RequestScoped
public class ConsultDetailLocatedObject {
	
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public void init(){
		
	}

}
