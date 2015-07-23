package fr.m2i.formation.poec.geolocate.webadmin.input;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Named("inputLocatedObjForm")
@RequestScoped
public class LocatedObjectInput {
	
	private static Logger logger = Logger.getLogger(LocatedObjectInput.class.getName());
	
	/*	@Inject
	private LocatedObjectService locatedObjectService;*/
	
	// Located Object
	@NotNull
	@Size(max=255)
	private String name;
	
	@Size(max=4000)
	private String description;
	
	
	//GPS coordinates
	private double longitude;
	
	private double latitude;
	
	private double altitude;
	
	
	//Address data
	
	private String firstLineAddress;
	
	private String secondLineAddress;
	
	private String city;
	
	private String state;
	
	private int zipCode;
	
	private String country;
	
	private long phoneNumber;
	
	//Tags
	private String inputTags;
	

	@PostConstruct
	private void init(){
		
	}
	
	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LocatedObjectInput.logger = logger;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public String getFirstLineAddress() {
		return firstLineAddress;
	}

	public void setFirstLineAddress(String firstLineAddress) {
		this.firstLineAddress = firstLineAddress;
	}

	public String getSecondLineAddress() {
		return secondLineAddress;
	}

	public void setSecondLineAddress(String secondLineAddress) {
		this.secondLineAddress = secondLineAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getInputTags() {
		return inputTags;
	}

	public void setInputTags(String inputTags) {
		this.inputTags = inputTags;
	}

	 public void saveAction(ActionEvent actionEvent) {
		 	process();
	    }
	 
	public String process(){
		
		//if object well created
		//redirection to view object detail 
		//return "bravo?faces-redirect=true";
		
		//else stay on same pages with error
		
		FacesMessage messageError = new FacesMessage("Input Error", "Some fields have errors. Please correct it !"); 
		
		if (true) { //TODO check object well created
			//redirection to view object details
			return "ConsultDetailLocatedObject?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, messageError);
		}
			
		return null;
	}

}
