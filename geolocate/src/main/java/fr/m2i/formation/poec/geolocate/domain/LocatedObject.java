package fr.m2i.formation.poec.geolocate.domain;

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

@Entity
//@NamedQuery(name="LocatedObject.FIND_BY_LONGITUDE", query = "SELECT a FROM LocatedObject a WHERE a.longitude = :longitude")
@Table(name="located_object")
public class LocatedObject {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	
	private String name;
	private String description;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	
	@Column(name = "created_on")
	private Date createdOn = Calendar.getInstance().getTime();
	
	private String uuid = UUID.randomUUID().toString();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_address")
	private Address addresses;
		
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST) // TODO: Make the eager disappear
	@JoinTable(name="object_tag",
	     joinColumns= @JoinColumn(name="id_object", referencedColumnName ="id"),
	     inverseJoinColumns= @JoinColumn(name="id_tag", referencedColumnName = "id"))
	private Set<Tag> tags = new HashSet<>();


	public LocatedObject() {
		
	}
	
	
	
	public LocatedObject(String name, String description, Double latitude,
			Double longitude, Double altitude, String uuid,
			Address addresses) {
		super();
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
		super();
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
	public long getId() {
		return id;
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


	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public Double getAltitude() {
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
		return "{id:" + id + " " + name + ", "
				+ description + ", " + latitude + ":"
				+ longitude + ";" + altitude + ", '"
				+ createdOn + "', " + uuid + ", addresses=" + addresses
				+ ", tags:" + tags + "]";
	}


    
	
	
}
