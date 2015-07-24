package fr.m2i.formation.poec.geolocate.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="id_address")
	
	private String street;
	private int zipcode;
	private String city;
	private String state;
	private String country;
	private String uuid = UUID.randomUUID().toString();;
	
	
	@OneToMany(mappedBy="addresses")
	private Set<LocatedObject> locatedObjects = new HashSet<>();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public int getZipcode() {
		return zipcode;
	}


	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
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


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public Set<LocatedObject> getLocatedObjects() {
		return locatedObjects;
	}


	public void setLocatedObjects(Set<LocatedObject> locatedObjects) {
		this.locatedObjects = locatedObjects;
	}
	
	
	public Address()  {
		
	}


	public Address(String street, int zipcode, String city, String state,
			String country) {
		super();
		this.street = street;
		this.zipcode = zipcode;
		this.city = city;
		this.state = state;
		this.country = country;
	}

    
	
	
	
	

}
