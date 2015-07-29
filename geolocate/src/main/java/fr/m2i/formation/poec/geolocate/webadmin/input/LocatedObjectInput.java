package fr.m2i.formation.poec.geolocate.webadmin.input;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;
import fr.m2i.formation.poec.geolocate.service.BDDServiceImpl;

@Named("inputLocatedObjForm")
//@RequestScoped
@ViewScoped
public class LocatedObjectInput implements Serializable {

	private static Logger logger = Logger.getLogger(LocatedObjectInput.class.getName());

	@Inject
	private BDDServiceImpl locatedObjectService;

	private Set<Tag> tags =  new HashSet<Tag>() ;

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

	@Pattern(regexp="[0-9]*")
	private String zipCode;

	private String country;

	//Tags
	private String inputTags="";
	private String inputOneTag;

	/*
 	@PostConstruct
	private void init(){

	}
	 */

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
		this.name = name.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description.trim();
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
		if(firstLineAddress !=null && !firstLineAddress.isEmpty()){
			firstLineAddress = firstLineAddress.toLowerCase().trim();
		}
		this.firstLineAddress = firstLineAddress;
	}

	public String getSecondLineAddress() {
		return secondLineAddress;
	}

	public void setSecondLineAddress(String secondLineAddress) {
		if(secondLineAddress !=null && !secondLineAddress.isEmpty()){
			secondLineAddress = secondLineAddress.toLowerCase().trim();
		}
		this.secondLineAddress = secondLineAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if(city !=null && !city.isEmpty()){
			city = city.toLowerCase().trim();
		}
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if(state !=null && !state.isEmpty()){
			state = state.toLowerCase().trim();
		}
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		if(country !=null && !country.isEmpty()){
			country = country.toLowerCase().trim();
		}
		this.country = country;
	}

	public String getInputTags() {
		return inputTags;
	}

	public void setInputTags(String inputTags) {
		this.inputTags = inputTags;
	}


	public String getInputOneTag() {
		return inputOneTag;
	}


	public void setInputOneTag(String inputOneTag) {
		this.inputOneTag = inputOneTag;
	}


	public void addTagAction() {
		
		if(inputOneTag != null && !inputOneTag.isEmpty()) {
			String tagName = inputOneTag.toLowerCase().trim();

			inputTags += tagName + ";";

			Tag tag = new Tag(tagName);

			tags.add(tag);
		}

		inputOneTag = "";
	}

	public String process(){
		logger.info("start process");

		LocatedObject locatedObject = new LocatedObject(name,latitude,longitude,altitude);

		Address address = new Address();

		locatedObject.setDescription(description);

		if(!(firstLineAddress.isEmpty() 
				|| zipCode.isEmpty()
				|| city.isEmpty())){

			address.setStreet(firstLineAddress + "\n" + secondLineAddress);
			address.setZipcode(zipCode);
			address.setCity(city);
			address.setState(state);
			address.setCountry(country);

			locatedObject.setAddresses(address);
		}

		locatedObject.setTags(tags);

		//object well created
		try{
			locatedObjectService.insert(locatedObject);
			return "/output/ConsultDetailLocatedObject?uuid="+ locatedObject.getUuid() +"faces-redirect=true";
		}
		catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ! Located Object not stored !","Error ! Located Object not stored !"));
			return null;
		}

	}

}
