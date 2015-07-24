package fr.m2i.formation.poec.geolocate.rest;

import java.util.List;

import fr.m2i.formation.poec.geolocate.domain.LocatedObject;


public interface BDDService {

	Integer getLocationCount();

	List<LocatedObject> getLocations(int start);

	LocatedObject[] getLocation(Double latitude, Double longitude);

	LocatedObject[] getLocation(Double latitude, Double longitude,
			Double altitude);

	LocatedObject[] getLocation(String uuid);
	

}
