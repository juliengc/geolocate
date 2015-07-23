package fr.m2i.formation.poec.geolocate.webadmin.input;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
	@Size(min=1,max=255)
	private String name;
	
	@Size(max=4000)
	private String description;
	
	
	//GPS coordinates
	@DecimalMin("-90.00") @DecimalMax("90.00")
	private double latitude;
	
	@DecimalMin("-180.00") @DecimalMax("180.00")
	private double longitude;
	
	@DecimalMin("-10000.00") @DecimalMax("10000.00")
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
		 logger.info("Into the listener");
		 	process();
	    }
	 
	public String process(){
		logger.info("start process");
		
		//if object well created
		//redirection to view object detail 
		//return "bravo?faces-redirect=true";
		
		//else stay on same pages with error
		
		FacesMessage messageError = new FacesMessage("Input Error", "Some fields have errors. Please correct it !"); 
		
		/*if (true) { //TODO check object well created
			//redirection to view object details
			return "ConsultDetailLocatedObject?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, messageError);
		}*/
			
		return null;
	}

}
