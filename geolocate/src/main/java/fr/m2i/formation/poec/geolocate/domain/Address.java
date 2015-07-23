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
	
	
	
	
	
	

}
