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

@Entity
public class LocatedObject {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="id_object")
	
	private String name;
	private String description;
	private float coordLong;
	private float coordLat;
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
	
	
	
	
}
