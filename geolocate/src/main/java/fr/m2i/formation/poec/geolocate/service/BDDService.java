package fr.m2i.formation.poec.geolocate.service;

import java.util.List;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;
import fr.m2i.formation.poec.geolocate.service.exception.BDDException;
import fr.m2i.formation.poec.geolocate.service.exception.InvalidTagException;


public interface BDDService {
	/**
	 * @return the number of located objects in the database.
	 * Cannot be negative.
	 * @throws BDDException in case of database error
	 */
    Integer getLocatedObjectsCount();

    /**
     * Returns the list of located objects contained in the DB 
     * ordered by the name of the object.
     * @param start the start of the list, if outside the list throws an IllegalArgumentException
     * @param step the max number of element in the list must be positive, 0 means all the list of objects
     * @return a valid object cannot be null.
     * 
     * @throws java.lang.IllegalArgumentException if start or step are invalid
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */
    List<LocatedObject> getLocatedObjects(int start, int step);

	/**
	 * @param tag must not be null
	 * @return the number of located objects in the database tagged with the specific tag .
	 * Cannot be negative.
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
	 * @throws java.lang.NullPointerException if the parameter is null
	 * @throws java.lang.InvalidTagException if tag is not in the database
	 */
    Integer getLocatedObjectsCount(Tag tag);
    
    /**
     * Returns the list of located objects that are tagged with the specified tag contained in the DB, 
     * ordered by the name of the object.
     * @param tag cannot be null
     * @param start the start of the list, if outside the list throws an IllegalArgumentException
     * @param step the max number of element in the list must be positive, 0 means all the list of objects
     * @return a valid object cannot be null.
     * 
     * @throws java.lang.IllegalArgumentException if start or step are invalid
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     * @throws java.lang.NullPointerException if the parameter is null
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.InvalidTagException if tag is not in the database

     */    
    List<LocatedObject> getLocatedObjects(Tag tag, int start, int step);

    /**
     * Returns the list of located objects that have a name which has the specified substring in the DB, 
     * ordered by the name of the object.
     * @param substring a portion of the name of the located objects to look for.
     * @param start the start of the list, if outside the list throws an IllegalArgumentException
     * @param step the max number of element in the list must be positive, 0 means all the list of objects
     * @return a valid object cannot be null.
     * 
     * @throws java.lang.IllegalArgumentException if start or step are invalid
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     * @throws java.lang.NullPointerException if the parameter is null
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.InvalidTagException if tag is not in the database

     */    
    List<LocatedObject> getLocatedObjects(String substring, int start, int step);
    
    /**
     * Returns the list of located objects that are located at the specified coords contained in the DB, 
     * ordered by the name of the object.
     * @param latitude the latitude
     * @param longitude the longitude
     * @return a valid object cannot be null.
     * 
     * @throws java.lang.IllegalArgumentException if latitude or longitude are invalid
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */    
    List<LocatedObject> getLocatedObjects(double latitude, double longitude);

    /**
     * Returns the list of located objects that are located at the specified coords contained in the DB, 
     * ordered by the name of the object.
     * @param latitude the latitude
     * @param longitude the longitude
     * @param altitude the altitude
     * @return a valid object cannot be null.
     * 
     * @throws java.lang.IllegalArgumentException if latitude or longitude are invalid
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */
    List<LocatedObject> getLocatedObjects(double latitude, double longitude,
    		double altitude);

    /**
     * Get the object from the db by its uuid.
     * @param uuid a uuid of an object in the db
     * @return either the object if found or null
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */
    LocatedObject getLocatedObject(String uuid);
    
    /**
     * Get the tag from the db by its name.
     * @param name the name of the tag
     * @return either the tag if found or null
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */
    Tag getTag(String name);
    
    /**
     * Returns the list of located addresses contained in the DB 
     * ordered by ????.///TODO: decide of an order
     * @param start the start of the list, if outside the list throws an IllegalArgumentException
     * @param step the max number of element in the list must be positive, 0 means all the list of addresses
     * @return a valid address; cannot be null.
     * 
     * @throws java.lang.IllegalArgumentException if start or step are invalid
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */   
    List<Address> getAddresses(int start, int step);
    
	/**
	 * @return the number of addresses in the database.
	 * Cannot be negative.
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
	 */
    Integer getAddressesCount();
    
    /**
     * Get the Address from the db by its uuid.
     * @param uuid the uuid of the address
     * @return either the address if found or null
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */
    Address getAddress(String uuid);

    /**
     * Adds a well-formed object in the database
     * @param lo the object to add
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */
    void insert(LocatedObject lo);
    
    /**
     * Adds a well-formed address in the database
     * @param ad the address to add
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */    
    void insertAddress(Address ad);

    /**
     * Returns the list of tags contained in the DB 
     * ordered by their name.
     * @param start the start of the list, if outside the list throws an IllegalArgumentException
     * @param step the max number of element in the list must be positive, 0 means all the list of addresses
     * @return a valid tag; cannot be null.
     * 
     * @throws java.lang.IllegalArgumentException if start or step are invalid
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */ 
	List<Tag> getTags(int start, int step);

	/**
	 * @return the number of tags in the database.
	 * Cannot be negative.
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
	 */
    Integer getTagsCount();
	
	/**
     * Returns the list of tags that has a name which begins with the 
     * specified substring in the DB, ordered by their name.
     * 
     * @param substring the substring of the tag to look for.
     * @param start the start of the list, if outside the list throws an IllegalArgumentException
     * @param step the max number of element in the list must be positive, 0 means all the list of addresses
     * @return a valid tag; cannot be null.
     * 
     * @throws java.lang.IllegalArgumentException if start or step are invalid
     * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
     */ 
	List<Tag> getTagsLike(String substring, int start, int step);
	
	/**
	 * Returns the number of located objects in the selected area, filtered by the tags
	 * 
	 * @param latitude1 corner 1 of the rectangle coords
	 * @param longitude1 corner 1 of the rectangle coords
	 * @param latitude2 corner 2 of the rectangle coords
	 * @param longitude2 corner 2 of the rectangle coords
	 * @param tags the list of selection tags, if empty select any tags
	 * @return the number of located objects in the selected area, cannot be negative.
	 * 
	 * @throws java.lang.NullPointerException if the tag parameter is null or contains invalid tags
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.InvalidTagException if tag is not in the database
	 * @throws java.lang.IllegalArgumentException if latitude or longitude are invalid
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
	 */
	int getLocatedObjectsInAreaCount(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags);

	
	/**
	 * Returns the located objects in the selected rectangle area, filtered by the tags.
	 * 
	 * @param latitude1 corner 1 of the rectangle coords
	 * @param longitude1 corner 1 of the rectangle coords
	 * @param latitude2 corner 2 of the rectangle coords
	 * @param longitude2 corner 2 of the rectangle coords
	 * @param tags the list of selection tags, if empty select any tags
	 * @param start the start of the list, if outside the list throws an IllegalArgumentException
     * @param step the max number of element in the list must be positive, 0 means all the list of objects
     * 
	 * @return the number of located objects in the selected area, cannot be negative.
	 * 
	 * @throws java.lang.NullPointerException if the tag parameter is null or contains invalid tags
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.InvalidTagException if tag is not in the database
	 * @throws java.lang.IllegalArgumentException if latitude or longitude are invalid
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
	 */
	List<LocatedObject> getLocatedObjectsInArea(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags, int start, int step);
	


	/**
	 * Retrieve an address entity from the database wih the given parameters. 
	 * @param street the address street field
	 * @param zipCode the zipcode
	 * @param city the city name
	 * @param country the country name
	 * @return if the entity is found; return the entity otherwise returns null.
	 * 
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
	 */
	public Address getAddress(String street, String zipCode, String city, String country);

	/**
	 * Retrieves the list of located objects associated with the given address.
	 * @param a the address to get the objects from
	 * @return a list which can be null
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.InvalidAddressException
	 * @throws fr.m2i.formation.poec.geolocate.service.exception.BDDException in case of database error
	 */
	public List<LocatedObject> getLocatedObjects(Address a);
}

