package fr.m2i.formation.poec.geolocate.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;
import fr.m2i.formation.poec.geolocate.service.BDDServiceImpl;
import fr.m2i.formation.poec.geolocate.service.exception.BDDException;

@Path("/")
@RequestScoped
public class RestInsert {
	Logger logger = Logger.getLogger(RestInsert.class.getName());
	
	@Inject
	BDDServiceImpl bdd;
	
	@Context
	private UriInfo uriInfo;
	
	/**
	 * /geolocate/location/{latitude}/{longitude}
	 * POST Method: parameter ‘name’, ‘description’, ‘addressURL’, ‘taglist’ (a comma separated list of words)
	 * Creates a new location. If successful, the result is equivalent to the result of the GET method. On creation error returns a 400 or 405 error
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/location/{latitude}/{longitude}")
	public Response createLocation(
			@PathParam("latitude") Double latitude,
			@PathParam("longitude") Double longitude,
			@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("addressURL") String addressURL,
			@FormParam("taglist") String taglist
			) {
		return insertLocation(latitude, longitude, null, name, description, addressURL, taglist);
	}
	
	/**
	 * /geolocate/location/{latitude}/{longitude}/{altitude}
	 * POST Method: parameter ‘name’, ‘description’, ‘addressURL’, ‘taglist’ (a comma separated list of words)
	 * Creates a new location. If successful, the result is equivalent to the result of the GET method. On creation error returns a 400 or 405 error
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/location/{latitude}/{longitude}/{altitude}")
	public Response createLocation(
			@PathParam("latitude") Double latitude,
			@PathParam("longitude") Double longitude,
			@PathParam("altitude") Double altitude,
			@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("addressURL") String addressURL,
			@FormParam("taglist") String taglist
			) {
		return insertLocation(latitude, longitude, altitude, name, description, addressURL, taglist);
	}
	
	private Response insertLocation(Double latitude, Double longitude, Double altitude, String name, String description, String addressURL, String taglist) {
		try {
			
			logger.info("insertLocation 1");
		
			String errMsg  = "";
			if (name == null || name.isEmpty()) {
				errMsg = errMsg + "name is null or empty \n";
			}
			if (description == null || description.isEmpty()) {
				errMsg = errMsg + "description is null or empty \n";
			}
			if (addressURL == null || addressURL.isEmpty()) {
				errMsg = errMsg + "addressURL is null or empty \n";
			}
			if (taglist == null || taglist.isEmpty()) {
				errMsg = errMsg + "taglist is null or empty \n";
			}
			
			if (!errMsg.isEmpty()) {
				return Response.status(Status.BAD_REQUEST)
						.entity(errMsg).build();
			}
			
			logger.info("insertLocation 2");
			
			
			
			String[] tags = taglist.trim().split(",");
			List<Tag> tagList = new ArrayList<Tag>();
			for (String tag : tags) {
				logger.info("tag " + tag);
				Tag newTag = new Tag(tag);
				tagList.add(newTag);
			}
			Set<Tag> tagSet = new HashSet<Tag>(tagList);
			
			String[] uuid = addressURL.split("/");
			Address address = null;
			
			logger.info("insertLocation 3 " + uuid[uuid.length - 1]);
			
			address = bdd.getAddress(uuid[uuid.length - 1]);
			
			if (address == null) {
				return Response.status(Status.NOT_FOUND)
						.entity("Address doesn't exist with uuid : " + uuid[uuid.length - 1])
						.build();
			}
			
			LocatedObject newLocatedObject = new LocatedObject(name, description, latitude, longitude, altitude, Calendar.getInstance().getTime(), address, tagSet);

			bdd.insert(newLocatedObject);
			
			
			
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("name", newLocatedObject.getName());
			builder.add("description", newLocatedObject.getDescription());
			builder.add("latitude", newLocatedObject.getLatitude());
			builder.add("longitude", newLocatedObject.getLongitude());
			builder.add("createdOn", newLocatedObject.getCreatedOn().toString());
			builder.add("uuid", newLocatedObject.getUuid());
			// URI absolute
			URI uri = uriInfo.getBaseUriBuilder()
								.path(RestView.class)
								.path(RestView.class, "getAddress")
								.build(newLocatedObject.getAddresses().getUuid());
			builder.add("address", uri.toString());

			
			return Response.created(UriBuilder.fromResource(RestView.class)
										.path(RestView.class, "getLocationByUUID")
										.build(newLocatedObject.getUuid()))
					.entity(builder.build())
					.build();
		
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
	 * /geolocate/address/
	 * POST with parameters ‘num’ ‘street’ ‘zipcode’ ‘city’ ‘country’
	 * On success returns the URL of the created resource as a String
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/address")
	public Response createAddress(
			@FormParam("street") String street,
			@FormParam("zipcode") String zipcode,
			@FormParam("city") String city,
			@FormParam("state") String state,
			@FormParam("country") String country
			) {
		try {
		
			String errMsg  = "";
			if (street == null || street.isEmpty()) {
				errMsg = errMsg + "street is null or empty \n";
			}
			if (zipcode == null || zipcode.isEmpty()) {
				errMsg = errMsg + "zipcode is null or empty \n";
			}
			if (city == null || city.isEmpty()) {
				errMsg = errMsg + "city is null or empty \n";
			}
			if (state == null || state.isEmpty()) {
				errMsg = errMsg + "state is null or empty \n";
			}
			if (country == null || country.isEmpty()) {
				errMsg = errMsg + "country is null or empty \n";
			}
			
			if (!errMsg.isEmpty()) {
				return Response.status(Status.BAD_REQUEST)
						.entity(errMsg).build();
			}
			
			Address newAddress = new Address(street, zipcode, city, state, country);
			
			bdd.insertAddress(newAddress);
			
			URI uri = UriBuilder.fromResource(RestView.class)
						.path(RestView.class, "getAddress")
						.build(newAddress.getUuid());
			
			// URI absolute
			URI uriAbsolute = uriInfo.getBaseUriBuilder()
								.path(RestView.class)
								.path(RestView.class, "getAddress")
								.build(newAddress.getUuid());
			
			return Response.created(uri)
					.entity(uriAbsolute)
					.build();
		
		} catch (BDDException e) {
			ResponseBuilder res = Response.serverError().entity("Internal BDD error!");
			return res.build();
		}
	}
}
