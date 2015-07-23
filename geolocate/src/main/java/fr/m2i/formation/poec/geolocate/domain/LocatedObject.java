package fr.m2i.formation.poec.geolocate.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
@NamedQuery(name="LocatedObject.FIND_BY_COORLONG", query = "SELECT a FROM LocatedObject a WHERE a.coordLong = :coordLong")
public class LocatedObject {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="id_object")
	
	private String name;
	private String description;
	private float coordLat;
	private float coordLong;
	private float coordAlt;
	
	@Column(name = "created_on")
	private Date createdOn = new Date();
	
	private Long uuid;
	
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


	public float getCoordLong() {
		return coordLong;
	}


	public void setCoordLong(float coordLong) {
		this.coordLong = coordLong;
	}


	public float getCoordLat() {
		return coordLat;
	}


	public void setCoordLat(float coordLat) {
		this.coordLat = coordLat;
	}


	public float getCoordAlt() {
		return coordAlt;
	}


	public void setCoordAlt(float coordAlt) {
		this.coordAlt = coordAlt;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public Long getUuid() {
		return uuid;
	}


	public void setUuid(Long uuid) {
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


	public LocatedObject(long id, String name, String description,
			float coordLong, float coordLat, float coordAlt, Date createdOn,
			Long uuid, Address addresses, Set<Tag> tags) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.coordLong = coordLong;
		this.coordLat = coordLat;
		this.coordAlt = coordAlt;
		this.createdOn = createdOn;
		this.uuid = uuid;
		this.addresses = addresses;
		Tags = tags;
	}
	
	
	
	
}
