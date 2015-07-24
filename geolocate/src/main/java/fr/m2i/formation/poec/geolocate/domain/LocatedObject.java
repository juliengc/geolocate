package fr.m2i.formation.poec.geolocate.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="LocatedObject.FIND_BY_LONGITUDE", query = "SELECT a FROM LocatedObject a WHERE a.longitude = :longitude")
public class LocatedObject {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="id_object")
	
	private String name;
	private String description;
	private float latitude;
	private float longitude;
	private float altitude;
	
	@Column(name = "created_on")
	private Date createdOn = new Date();
	
	private String uuid = UUID.randomUUID().toString();
	
	@ManyToOne
	@JoinColumn(name="id_address")
	private Address addresses;
	
		
	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="objectTags",
	     joinColumns= @JoinColumn(name="id_object", referencedColumnName ="id"),
	     inverseJoinColumns= @JoinColumn(name="id_tag", referencedColumnName = "id"))
	private Set<Tag> Tags = new HashSet<>();


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
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


	public float getLatitude() {
		return latitude;
	}


	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}


	public float getLongitude() {
		return longitude;
	}


	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}


	public float getAltitude() {
		return altitude;
	}


	public void setAltitude(float altitude) {
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
		return Tags;
	}


	public void setTags(Set<Tag> tags) {
		Tags = tags;
	}

	public LocatedObject() {
		
	}
	public LocatedObject(String name, String description,
			float latitude, float longitude, float altitude, Date createdOn,
			Address addresses, Set<Tag> tags) {
		super();
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.createdOn = createdOn;
		this.addresses = addresses;
		Tags = tags;
	}


	public LocatedObject(String name, float latitude, float longitude,
			float altitude) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

    
	
	
}
