package fr.m2i.formation.poec.geolocateclient.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

public class LocatedObject {
		
	private String name;
	private String description;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	
	private Date createdOn = Calendar.getInstance().getTime();
	
	private String uuid;
	
	private Address addresses;
	
	private Set<Tag> tags = new HashSet<>();


	public LocatedObject() {
		
	}
		
	public LocatedObject(String name, String description, Double latitude,
			Double longitude, Double altitude, String uuid,
			Address addresses) {
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.uuid = uuid;
		this.addresses = addresses;
	}


	public LocatedObject(String name, String description,
			Double latitude, Double longitude, Double altitude, Date createdOn,
			Address addresses, Set<Tag> tags) {
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.createdOn = createdOn;
		this.addresses = addresses;
		this.tags = tags;
	}


	public LocatedObject(String name, double latitude, double longitude,
			Double altitude) {
		this(name, "", latitude, longitude, altitude, UUID.randomUUID().toString(), null);
		
	}

	public LocatedObject(String name, String description, double latitude, double longitude,
			Double altitude) {
		this(name, description, latitude, longitude, altitude, UUID.randomUUID().toString(), null);
		
	}
	public LocatedObject(String name, String description, double latitude, double longitude,
			Double altitude, Address address) {
		this(name, description, latitude, longitude, altitude, UUID.randomUUID().toString(), address);
		
	}
	public LocatedObject(String name, String description, double latitude, double longitude, Address address) {
		this(name, description, (Double) latitude, (Double) longitude, null, UUID.randomUUID().toString(), address);
		
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


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public double getAltitude() {
		return altitude;
	}


	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public Address getAddresses() {
		return addresses;
	}


	public void setAddresses(Address addresses) {
		this.addresses = addresses;
	}
	
	public Set<Tag> getTags() {
		return tags;
	}


	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}


    
	@Override
	public String toString() {
		return "{ " + name + ", "
				+ description + ", " + latitude + ":"
				+ longitude + ";" + altitude + ", '"
				+ createdOn + "', " + uuid + ", addresses=" + addresses
				+ ", tags:" + tags + "]";
	}

	public String objectDescStr(){
		if(addresses != null) {
			return "| Desc : " + description + " | \n " + " Address : " + addresses.addrDescStr() + " | \n "+ "Tags : " + tags + " | ";
		}
		else {
			return "| Desc : " + description + " | \n " + "Tags : " + tags + " | ";
		}
	}
	
	
}
