package fr.m2i.formation.poec.geolocate.webadmin.output;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Inject;
import javax.inject.Named;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;
import fr.m2i.formation.poec.geolocate.service.BDDException;
import fr.m2i.formation.poec.geolocate.service.ServiceGeolocate;

@Named("consultDetail")
@RequestScoped
public class ConsultDetailLocatedObject {
	
	private static final Logger logger = Logger.getLogger(ConsultDetailLocatedObject.class.getName());  

	private String uuid;
	private LocatedObject objectLocated;
	@Inject
	private ServiceGeolocate locatedObjectService;
	
	public void init(){
	
		try{
			locatedObjectService.getLocatedObject(uuid);
		}catch(BDDException ex){
			FacesContext.getCurrentInstance().addMessage(uuid, new FacesMessage(ex.getMessage()));
			logger.info("ConsultDetailLocatedObject init --> Exception");
			return;
		}
		
		//Temp 
	/*	objectLocated = new LocatedObject("object1 name", "description object 1", 2.0, 222.0, new Address("streee \n eeeee", 2222, "City", "state", "country"));
		objectLocated.setAltitude(0);
		Set<Tag> tags = new HashSet<>();
		
		tags.add(new Tag("tag11"));
		tags.add(new Tag("tag22"));
		objectLocated.setTags(tags);
		*/
		logger.info("ConsultDetailLocatedObject init --> "+ objectLocated.toString());

	}

	public String getName(){
		return objectLocated.getName();
	}
	
	public String getDescription(){
		return objectLocated.getDescription();
	}
	
	public double getLatitude(){
		return objectLocated.getLatitude();
	}
	
	public double getLongitude(){
		return objectLocated.getLongitude();
	}
	
	public double getAltitude(){
		return objectLocated.getAltitude();
	}
	
	public String getlineAddress(){
		return objectLocated.getAddresses().getStreet();
	}
	
	public String getCity(){
		return objectLocated.getAddresses().getCity();
	}
	
	public String getState(){
		return objectLocated.getAddresses().getState();
	}
	
	public String getZipCode(){
		return objectLocated.getAddresses().getZipcode();
	}
	
	public String getCountry(){
		return objectLocated.getAddresses().getCountry();
	}
	
	public String getTags(){
		
		StringBuilder builder = new StringBuilder();
		
		for (Tag elem : objectLocated.getTags()) {
			builder.append(elem.getName());
			builder.append("; ");
		}
		return builder.toString();
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	

}
