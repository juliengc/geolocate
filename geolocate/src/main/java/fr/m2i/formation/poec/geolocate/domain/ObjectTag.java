package fr.m2i.formation.poec.geolocate.domain;

public class ObjectTag {
	
	
	private Long id_object;
	private Long id_tag;
	public Long getId_object() {
		return id_object;
	}
	public void setId_object(Long id_object) {
		this.id_object = id_object;
	}
	public Long getId_tag() {
		return id_tag;
	}
	public void setId_tag(Long id_tag) {
		this.id_tag = id_tag;
	}
	
	public ObjectTag() {
		
	}
	public ObjectTag(Long id_object, Long id_tag) {
		super();
		this.id_object = id_object;
		this.id_tag = id_tag;
	}
	
	

}
