package fr.m2i.formation.poec.geolocate.domain;

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

@Entity
public class Tag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tag")
	private Long id;
	
	
	private String name;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="locatedObject",
           joinColumns=@JoinColumn(name="id_object", referencedColumnName ="id"))
	private Set<LocatedObject> locatedObjects = new HashSet<>();
	
	public Tag() {
		
	}
	
	
	public Tag(String name) {
		super();
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<LocatedObject> getLocatedObjects() {
		return locatedObjects;
	}


	public void setLocatedObjects(Set<LocatedObject> locatedObjects) {
		this.locatedObjects = locatedObjects;
	}

	
	
	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", locatedObjects="
				+ locatedObjects + "]";
	}
	
}
