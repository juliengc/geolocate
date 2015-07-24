package fr.m2i.formation.poec.geolocate.domain;

import java.util.HashSet;
import java.util.Set;

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
	
	private int num;
	private String street;
	private int zipcode;
	private String city;
	private String country;
	private String uuid;
	
	
	@OneToMany(mappedBy="id_Object")
	private Set<LocatedObject> locatedObjects = new HashSet<>();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
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

	public Address() {
		
	}
	public Address(Long id, int num, String street, int zipcode, String city,
			String country, String uuid, Set<LocatedObject> locatedObjects) {
		super();
		this.id = id;
		this.num = num;
		this.street = street;
		this.zipcode = zipcode;
		this.city = city;
		this.country = country;
		this.uuid = uuid;
		this.locatedObjects = locatedObjects;
	} 
	
	
	
	
	
	

}
