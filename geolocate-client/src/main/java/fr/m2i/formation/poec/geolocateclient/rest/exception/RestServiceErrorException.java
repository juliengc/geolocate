package fr.m2i.formation.poec.geolocateclient.rest.exception;

public class RestServiceErrorException extends Exception {

    /* */
	private static final long serialVersionUID = 1L;

	public RestServiceErrorException(String message) {
        super(message);
    }

    public RestServiceErrorException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
