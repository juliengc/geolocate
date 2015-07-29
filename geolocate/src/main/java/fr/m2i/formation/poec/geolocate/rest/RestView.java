package fr.m2i.formation.poec.geolocate.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;
import fr.m2i.formation.poec.geolocate.service.BDDServiceImpl;
import fr.m2i.formation.poec.geolocate.service.exception.BDDException;
import fr.m2i.formation.poec.geolocate.service.exception.InvalidAddressException;
import fr.m2i.formation.poec.geolocate.service.exception.InvalidTagException;

@Path("/")
@RequestScoped
public class RestView {
	Logger logger = Logger.getLogger(RestView.class.getName());
	
	private static final int MAX_RESULT = 50;

	@Inject
	BDDServiceImpl bdd;
	
	@Context
	private UriInfo uriInfo;
	
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
	@Path("/locations")
	public Response getLocations(@QueryParam("start") String startStr) {
		try {
			Integer start;
			if (startStr == null) {
				start = 0;
			} else {
				start = Integer.valueOf(startStr);
			}
			
			ResponseBuilder res = Response.ok();
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("size", (Integer) bdd.getLocatedObjectsCount());
			List<LocatedObject> locations = bdd.getLocatedObjects((start != null) ? start: 0, MAX_RESULT);
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (LocatedObject lo: locations) {
				// URI absolute
				URI uri = uriInfo.getBaseUriBuilder()
									.path(RestView.class)
									.path(RestView.class, "getLocationByUUID")
									.build(lo.getUuid());
				arr.add(uri.toString());
			}
			builder.add("content", arr.build());
			res.entity(builder.build());
			
			return res.build();
		}
		catch (NumberFormatException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity("start is not a Integer");
			return res.build();
		}
		catch (EJBException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity(e.getCausedByException().getMessage());
			return res.build();
		}
		catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
	
	/**
	 * /geolocate/location/{latitude}/{longitude}
	 *	GET Method:
	 * @return the location or all the locations corresponding to the selected latitude/longitude and altitude if specified
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/location/{latitude}/{longitude}")
	public Response getLocation(@PathParam("latitude") Double latitude, 
			@PathParam("longitude") Double longitude) {
		try {
			ResponseBuilder res = Response.ok();

			List<LocatedObject> list = bdd.getLocatedObjects(latitude, longitude);
			
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (LocatedObject lo: list) {
				// URI absolute
				URI uri = uriInfo.getBaseUriBuilder()
									.path(RestView.class)
									.path(RestView.class, "getLocationByUUID")
									.build(lo.getUuid());
				arr.add(uri.toString());
			}
			res.entity(arr.build());
			
			return res.build();
		}
		catch (EJBException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity(e.getCausedByException().getMessage());
			return res.build();
		}
		catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
	
	/**
	 * /geolocate/location/{latitude}/{longitude}/{altitude}
	 *	GET Method:
	 * @return the location or all the locations corresponding to the selected latitude/longitude and altitude if specified
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/location/{latitude}/{longitude}/{altitude}")
	public Response getLocationWithAltitude(@PathParam("latitude") Double latitude, 
			@PathParam("longitude") Double longitude,
			@PathParam("altitude") Double altitude) {
		try {
			ResponseBuilder res = Response.ok();

			List<LocatedObject> list = bdd.getLocatedObjects(latitude, longitude, altitude);
			
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (LocatedObject lo: list) {
				// URI absolute
				URI uri = uriInfo.getBaseUriBuilder()
									.path(RestView.class)
									.path(RestView.class, "getLocationByUUID")
									.build(lo.getUuid());
				arr.add(uri.toString());
			}
			res.entity(arr.build());
			
			return res.build();
		}
		catch (EJBException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity(e.getCausedByException().getMessage());
			return res.build();
		}
		catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
	
	/**
	 * /geolocate/location/{uuid}
	 * GET Method:
	 * Returns the location or all the locations corresponding to the selected uuid
	 *
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/location/{uuid}")
	public Response getLocationByUUID(@PathParam("uuid") String uuid) {
		try {
			ResponseBuilder res = Response.ok();

			LocatedObject location = bdd.getLocatedObject(uuid);
			
			if (location == null) {
				return Response.status(Status.NOT_FOUND)
						.entity("Location doesn't exist with uuid : " + uuid)
						.build();
			}

			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("name", location.getName());
			builder.add("description", location.getDescription());
			builder.add("latitude", location.getLatitude());
			builder.add("longitude", location.getLongitude());
			builder.add("createdOn", location.getCreatedOn().toString());
			builder.add("uuid", location.getUuid());
			// URI absolute
			URI uri = uriInfo.getBaseUriBuilder()
								.path(RestView.class)
								.path(RestView.class, "getAddress")
								.build(location.getAddresses().getUuid());
			builder.add("address", uri.toString());

			res.entity(builder.build());
			
			return res.build();
		} catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
	
	/**
     *  /geolocate/addresses
     *  GET method the list of available addresses it has the same behavior as the locations method.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addresses")
	public Response getAddresses(@QueryParam("start") String startStr) {
		try {
			Integer start;
			if (startStr == null) {
				start = 0;
			} else {
				start = Integer.valueOf(startStr);
			}
			
			ResponseBuilder res = Response.ok();
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("size", (Integer) bdd.getAddressesCount());
			List<Address> addresses = bdd.getAddresses((start != null) ? start: 0, MAX_RESULT);
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (Address ad: addresses) {
				// URI absolute
				URI uri = uriInfo.getBaseUriBuilder()
									.path(RestView.class)
									.path(RestView.class, "getAddress")
									.build(ad.getUuid());
				arr.add(uri.toString());
			}
			builder.add("content", arr.build());
			res.entity(builder.build());
			
			return res.build();
		}
		catch (NumberFormatException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity("start is not a Integer");
			return res.build();
		}
		catch (EJBException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity(e.getCausedByException().getMessage());
			return res.build();
		}
		catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
	
	/**
	 * /geolocate/address/{uuid}
	 *	GET method returns an object containing a field address, an object representing the address associated with the uuid and a field locations containing an array of the location URL corresponding to the address
	 * Otherwise returns Error 404 NOT FOUND it also
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/address/{uuid}")
	public Response getAddress(@PathParam("uuid") String uuid) {
		try {
			ResponseBuilder res = Response.ok();

			Address address = bdd.getAddress(uuid);
			
			if (address == null) {
				return Response.status(Status.NOT_FOUND)
						.entity("Address doesn't exist with uuid : " + uuid)
						.build();
			}
			
			List<LocatedObject> list = bdd.getLocatedObjects(address);
			
			JsonObjectBuilder builder = Json.createObjectBuilder();
			if (!(address.getStreet() == null)) {
				builder.add("street", address.getStreet());	
			}
			if (!(address.getZipcode() == null)) {
				builder.add("zipcode", address.getZipcode());	
			}			
			if (!(address.getCity() == null)) {
				builder.add("city", address.getCity());	
			}
			if (!(address.getState() == null)) {
				builder.add("state", address.getState());	
			}
			builder.add("country", address.getCountry());
			builder.add("uuid", address.getUuid());
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (LocatedObject lo: list) {
				// URI absolute
				URI uri = uriInfo.getBaseUriBuilder()
									.path(RestView.class)
									.path(RestView.class, "getLocationByUUID")
									.build(lo.getUuid());
				arr.add(uri.toString());
			}
			builder.add("locatedObjects", arr.build());
			res.entity(builder.build());
			
			return res.build();
		}
		catch (InvalidAddressException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity(e.getMessage());
			return res.build();	
		}
		catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
	
	/**
	 * /geolocate/tags
 	 * GET method the list of available tags it has the same behavior as the locations method.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tags")
	public Response getTags(@QueryParam("start") String startStr) {
		try {
			Integer start;
			if (startStr == null) {
				start = 0;
			} else {
				start = Integer.valueOf(startStr);
			}
			
			JsonObjectBuilder builder = Json.createObjectBuilder();
			List<Tag> tags = bdd.getTags((start != null) ? start: 0, MAX_RESULT);

			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (Tag tag: tags) {
				arr.add(Json.createObjectBuilder().add("name", tag.getName()));
			}
			builder.add("size", (Integer) bdd.getTagsCount());
			builder.add("content", arr);
			
			return 	Response.ok(builder.build()).build();
		}
		catch (NumberFormatException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity("start is not a Integer");
			return res.build();
		}
		catch (EJBException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity(e.getCausedByException().getMessage());
			return res.build();
		}
		catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
	
	/**
	 * /geolocate/tag/{name}
	 * Returns an array of urls associated with the tag GET method has the same behavior as the locations method.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tag/{name}")
	public Response getLocationByTag(@PathParam("name") String name, @QueryParam("start") Integer start) {
		try {
			Tag t = bdd.getTag(name);
			if (t == null) {
				return Response.status(Status.NOT_FOUND)
						.entity("Tag doesn't exist with name : " + name)
						.build();
			}
			
			ResponseBuilder res = Response.ok();
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("size", (Integer) bdd.getLocatedObjectsCount(t));
			List<LocatedObject> locations = bdd.getLocatedObjects(t, (start != null) ? start: 0, MAX_RESULT);
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (LocatedObject lo: locations) {
				// URI absolute
				URI uri = uriInfo.getBaseUriBuilder()
									.path(RestView.class)
									.path(RestView.class, "getLocationByUUID")
									.build(lo.getUuid());
				arr.add(uri.toString());
			}
			builder.add("content", arr.build());
			res.entity(builder.build());
			
			return res.build();
		}
		catch (EJBException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity(e.getCausedByException().getMessage());
			return res.build();
		}
		catch (InvalidTagException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity(e.getMessage());
			return res.build();	
		}
		catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
	
	/**
	 *	/geolocate/area/{latitude}/{longitude}/x/{latitude}/{longitude}
	 * GET method has the same behavior as the locations method.
	 * Returns an array of locations url contained within the area, can be filtered by taglist (a list of semicolon separated tags).
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/area/{latitude1}/{longitude1}/x/{latitude2}/{longitude2}")
	public Response getLocation(@QueryParam("start") Integer start,
			@PathParam("latitude1") Double latitude1,
			@PathParam("latitude2") Double latitude2,
			@PathParam("longitude1") Double longitude1,
			@PathParam("longitude2") Double longitude2) {
		try {
			ResponseBuilder res = Response.ok();
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("size", (Integer) bdd.getLocatedObjectsInAreaCount(latitude1, longitude1, latitude2, longitude2, new ArrayList<Tag>()));
			List<LocatedObject> locations = bdd.getLocatedObjectsInArea(latitude1, longitude1, latitude2, longitude2, new ArrayList<Tag>(), (start != null) ? start: 0, MAX_RESULT);
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (LocatedObject lo: locations) {
				// URI absolute
				URI uri = uriInfo.getBaseUriBuilder()
									.path(RestView.class)
									.path(RestView.class, "getLocationByUUID")
									.build(lo.getUuid());
				arr.add(uri.toString());
			}
			builder.add("content", arr.build());
			res.entity(builder.build());
			
			return res.build();
		}
		catch (EJBException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity(e.getCausedByException().getMessage());
			return res.build();
		}
		catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
	
	/**
	 *	/geolocate/area/{latitude}/{longitude}/x/{latitude}/{longitude}/filter/{taglist}
	 * GET method has the same behavior as the locations method.
	 * Returns an array of locations url contained within the area, can be filtered by taglist (a list of semicolon separated tags).
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/area/{latitude1}/{longitude1}/x/{latitude2}/{longitude2}/filter/{taglist}")
	public Response getLocation(@QueryParam("start") Integer start,
			@PathParam("latitude1") Double latitude1,
			@PathParam("latitude2") Double latitude2,
			@PathParam("longitude1") Double longitude1,
			@PathParam("longitude2") Double longitude2,
			@PathParam("taglist") String taglist) {
		try {
			String[] tags = taglist.split(",");
			List<Tag> list = new ArrayList<Tag>();
			for(String t: tags) {
				logger.info("tag :" + t);
				
				Tag tag = bdd.getTag(t);
				if (tag == null) {
					return Response.status(Status.NOT_FOUND)
							.entity("Tag doesn't exist with name : " + t)
							.build();
				}
				list.add(tag);
			}
			ResponseBuilder res = Response.ok();
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("size", (Integer) bdd.getLocatedObjectsInAreaCount(latitude1, longitude1, latitude2, longitude2, list));
			List<LocatedObject> locations = bdd.getLocatedObjectsInArea(latitude1, longitude1, latitude2, longitude2, list, (start != null) ? start: 0, MAX_RESULT);
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (LocatedObject lo: locations) {
				// URI absolute
				URI uri = uriInfo.getBaseUriBuilder()
									.path(RestView.class)
									.path(RestView.class, "getLocationByUUID")
									.build(lo.getUuid());
				arr.add(uri.toString());
			}
			builder.add("content", arr.build());
			res.entity(builder.build());
			
			return res.build();
		}
		catch (EJBException e) {
			ResponseBuilder res = Response.status(Status.BAD_REQUEST).entity(e.getCausedByException().getMessage());
			return res.build();
		}
		catch (InvalidTagException e) {
			ResponseBuilder res = Response.serverError().entity(e.getMessage());
			return res.build();	
		}
		catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
}
