package fr.m2i.formation.poec.geolocate.service;

public class InvalidTagException  extends RuntimeException {

	/**  */
	private static final long serialVersionUID = 1L;
	
	
	public InvalidTagException(String msg) {
		super(msg);
	}
	
	public InvalidTagException(Throwable t) {
		super(t);
	}

}
