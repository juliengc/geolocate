package fr.m2i.formation.poec.geolocateclient.rest.exception;

public class RestClientException extends Exception {

    /* */
	private static final long serialVersionUID = 1L;

	public RestClientException(String message) {
        super(message);
    }

    public RestClientException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
