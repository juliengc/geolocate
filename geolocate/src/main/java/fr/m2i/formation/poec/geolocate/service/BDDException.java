package fr.m2i.formation.poec.geolocate.service;

public class BDDException extends RuntimeException {

	/**  */
	private static final long serialVersionUID = 1L;
	
	
	public BDDException(String msg) {
		super(msg);
	}


	public BDDException(Throwable t) {
		super(t);
	}
	

}
