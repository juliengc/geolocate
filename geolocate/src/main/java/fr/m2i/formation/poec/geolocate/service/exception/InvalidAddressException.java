package fr.m2i.formation.poec.geolocate.service.exception;

import fr.m2i.formation.poec.geolocate.domain.Address;

public class InvalidAddressException extends InvalidEntityException {

	/**	 */
	private static final long serialVersionUID = 1L;

	public InvalidAddressException(Address a) {
		super(a.toString() + " is invalid/");
	}

}
