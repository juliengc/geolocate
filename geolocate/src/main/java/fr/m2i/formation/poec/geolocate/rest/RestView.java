package fr.m2i.formation.poec.geolocate.rest;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;

@Path("/")
public class RestView {
	private static final int MAX_RESULT = 50;
	BDDService bdd;
	
	
	/**
	 * /geolocate/locations
	 *	GET Method : Method: parameter ‘start’ 
	 * - A field named ‘size’, with the total number of locations in the database
	 * - An array ‘content’ with the URL to the first 50 entries that comes after start of the database can be empty
	 * @param start indicates the starting location number returns a JSON object that contains 
	 * @return
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getLocations(@QueryParam("start") Integer start) {
		//TODO:
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("size", bdd.getLocationCount());
		List<LocatedObject> locations = bdd.getLocatedObjects((start != null) ? start: 0, MAX_RESULT);
		JsonArrayBuilder arr = Json.createArrayBuilder();
		for (LocatedObject lo: locations) {
			arr.add("/location/" + lo.getUuid());
		}
		builder.add("content", arr.build());
		return builder.build();
	}
	
	/**
	 * /geolocate/location/{latitude}/{longitude}[/{altitude}]
	 *	GET Method:
	 * @return the location or all the locations corresponding to the selected latitude/longitude and altitude if specified
	 */
	@GET
	@Path("/location/{latitude}/{longitude}")
	public LocatedObject [] getLocation(@PathParam("latitude") Double latitude, 
			@PathParam("longitude") Double longitude) {
		// TODO:
		return bdd.getLocatedObjects(latitude, longitude);
	}
	
	/**
	 * /geolocate/location/{latitude}/{longitude}[/{altitude}]
	 *	GET Method:
	 * @return the location or all the locations corresponding to the selected latitude/longitude and altitude if specified
	 */
	@GET
	@Path("/location/{latitude}/{longitude}/{altitude}")
	public LocatedObject [] getLocation(@PathParam("latitude") Double latitude, 
			@PathParam("longitude") Double longitude,
			@PathParam("altitude") Double altitude) {
		//TODO:
		return bdd.getLocatedObjects(latitude, longitude, altitude);
	}
	
	
	/**
	 * /geolocate/location/{uuid}
	 * GET Method:
	 * Returns the location or all the locations corresponding to the selected uuid
	 *
	 * */
	@GET
	@Path("/location/{uuid}")
	public LocatedObject getLocation(@PathParam("uuid") String uuid) {
		//TODO:
	
		return bdd.getLocatedObject(uuid);
	}
	
	/**
     *  /geolocate/addresses
     *  GET method the list of available addresses it has the same behavior as the locations method.
	 */
	@GET
	@Path("/addresses")
	public Address [] getAddresses(@QueryParam("start") Integer start) {
		//TODO:
		return bdd.getAddresses(start, MAX_RESULT);
	}
	/**
	 * /geolocate/address/{uuid}
	 *	GET method returns an object containing a field address, an object representing the address associated with the uuid and a field locations containing an array of the location URL corresponding to the address
	 * Otherwise returns Error 404 NOT FOUND it also
	 * 
	 */
	@GET
	@Path("/address/{uuid}")
	public Address getAddress(@PathParam("uuid") String uuid) {
		//TODO:
		return bdd.getAddress(uuid);
	}
	/**
	 * /geolocate/tags
 	 * GET method the list of available tags it has the same behavior as the locations method.
	 */
	@GET
	@Path("/tags")
	public Tag [] getTags(@QueryParam("start") Integer start) {
		//TODO:
		return bdd.getTags();
	}
	/**
	 * /geolocate/tag/{name}
	 * Returns an array of urls associated with the tag GET method has the same behavior as the locations method.
	 */
	@GET
	@Path("/tag/{name}")
	public LocatedObject [] getLocationByTag(@PathParam("name") String name, @QueryParam("start") Integer start) {
		Tag t = bdd.getTag(name);
		if (t == null) {
			// TODO:
		}
		return bdd.getLocatedObjects(t, start, MAX_RESULT).toArray(new LocatedObject[0]);
	}
	/**
	 *	/geolocate/area/{latitude}/{longitude}/x/{latitude}/{longitude}[/filter/{taglist}]
	 * GET method has the same behavior as the locations method.
	 * Returns an array of locations url contained within the area, can be filtered by taglist (a list of semicolon separated tags).
	 */
	@GET
	@Path("/geolocate/area/{latitude1}/{longitude1}/x/{latitude2}/{longitude2}[/filter/{taglist}]")
	public LocatedObject [] getLocation(@QueryParam("start") Integer start,
			@PathParam("latitude1") Double latitude1,
			@PathParam("latitude2") Double latitude2,
			@PathParam("longitude1") Double longitude1,
			@PathParam("longitude2") Double longitude2,
			@PathParam("taglist") String taglist) {
		//TODO:
		String[] tags = taglist.split(" ");
		List<Tag> list = new ArrayList<Tag>();
		for(String t: tags) {
			Tag tag = bdd.getTag(t);
			list.add(tag);
		}
		return bdd.getLocatedObjectsInArea(latitude1, longitude1, latitude2, longitude2, list);
	}
}
