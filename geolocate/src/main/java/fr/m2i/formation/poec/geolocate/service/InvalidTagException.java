package fr.m2i.formation.poec.geolocate.service;

import fr.m2i.formation.poec.geolocate.domain.Tag;
import fr.m2i.formation.poec.geolocate.service.exception.InvalidEntityException;

public class InvalidTagException  extends InvalidEntityException {

	/**  */
	private static final long serialVersionUID = 1L;
	
	
	public InvalidTagException(String msg) {
		super(msg);
	}

	public InvalidTagException(Tag tag) {
		super(tag.getName() + " is invalid/");
	}

	
	public InvalidTagException(Throwable t) {
		super(t);
	}

}
