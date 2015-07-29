package fr.m2i.formation.poec.geolocate.service.exception;

public class InvalidEntityException extends RuntimeException {

	public InvalidEntityException(String msg) {
		super(msg);
	}

	public InvalidEntityException(Throwable t) {
		super(t);
	}

}
