package fr.m2i.formation.poec.geolocate.service;

import java.util.List;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;


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
     * @throws IllegalArgumentException if start or step are invalid
     * @throws BDDException in case of database error
     */
    List<LocatedObject> getLocatedObjects(int start, int step);

	/**
	 * @param tag must not be null
	 * @return the number of located objects in the database tagged with the specific tag .
	 * Cannot be negative.
	 * @throws BDDException in case of database error
	 * @throws NullPointerException if the parameter is null
	 * @throws InvalidTagException if tag is not in the database
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
     * @throws IllegalArgumentException if start or step are invalid
     * @throws BDDException in case of database error
     * @throws NullPointerException if the parameter is null
	 * @throws InvalidTagException if tag is not in the database

     */    
    List<LocatedObject> getLocatedObjects(Tag tag, int start, int step);

    /**
     * Returns the list of located objects that are located at the specified coords contained in the DB, 
     * ordered by the name of the object.
     * @param latitude the latitude
     * @param longitude the longitude
     * @return a valid object cannot be null.
     * 
     * @throws IllegalArgumentException if latitude or longitude are invalid
     * @throws BDDException in case of database error
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
     * @throws IllegalArgumentException if latitude or longitude are invalid
     * @throws BDDException in case of database error
     */
    List<LocatedObject> getLocatedObjects(double latitude, double longitude,
    		double altitude);

    /**
     * Get the object from the db by its uuid.
     * @param uuid a uuid of an object in the db
     * @return either the object if found or null
     * @throws BDDException in case of database error
     */
    LocatedObject getLocatedObject(String uuid);
    
    /**
     * Get the tag from the db by its name.
     * @param name the name of the tag
     * @return either the tag if found or null
     * @throws BDDException in case of database error
     */
    Tag getTag(String name);
    
    /**
     * Returns the list of located addresses contained in the DB 
     * ordered by ????.///TODO: decide of an order
     * @param start the start of the list, if outside the list throws an IllegalArgumentException
     * @param step the max number of element in the list must be positive, 0 means all the list of addresses
     * @return a valid address; cannot be null.
     * 
     * @throws IllegalArgumentException if start or step are invalid
     * @throws BDDException in case of database error
     */   
    List<Address> getAddresses(int start, int step);
    
    /**
     * Get the Address from the db by its uuid.
     * @param uuid the uuid of the address
     * @return either the address if found or null
     * @throws BDDException in case of database error
     */
    Address getAddress(String uuid);

    /**
     * Adds a well-formed object in the database
     * @param lo the object to add
     * @throws BDDException in case of database error
     */
    void insert(LocatedObject lo);

    /**
     * Returns the list of tags contained in the DB 
     * ordered by their name.
     * @param start the start of the list, if outside the list throws an IllegalArgumentException
     * @param step the max number of element in the list must be positive, 0 means all the list of addresses
     * @return a valid tag; cannot be null.
     * 
     * @throws IllegalArgumentException if start or step are invalid
     * @throws BDDException in case of database error
     */ 
	List<Tag> getTags(int start, int step);

	
	
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
	 * @throws NullPointerException if the tag parameter is null or contains invalid tags
	 * @throws InvalidTagException if tag is not in the database
	 * @throws IllegalArgumentException if latitude or longitude are invalid
	 * @throws BDDException in case of database error
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
	 * @throws NullPointerException if the tag parameter is null or contains invalid tags
	 * @throws InvalidTagException if tag is not in the database
	 * @throws IllegalArgumentException if latitude or longitude are invalid
	 * @throws BDDException in case of database error
	 */
	List<LocatedObject> getLocatedObjectsInArea(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags, int start, int step);


}

