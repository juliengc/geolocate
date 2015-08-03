package fr.m2i.formation.poec.geolocate.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;
import fr.m2i.formation.poec.geolocate.service.exception.BDDException;
import fr.m2i.formation.poec.geolocate.service.exception.InvalidAddressException;
import fr.m2i.formation.poec.geolocate.service.exception.InvalidTagException;

@Stateless
@LocalBean
public class BDDServiceImpl  implements BDDService {

	@PersistenceContext(unitName="geolocatePU")
	private EntityManager em;



	/**
	 * 
	 * @param start
	 * @param step
	 * @param count
	 * @throws IllegalArgumentException if start or step are invalid
	 */
	private void testStartAndStep(int start, int step, int count) {
		if (start < 0) {
			throw new IllegalArgumentException("start is negative!"); 
		}
		if (step < 0) {
			throw new IllegalArgumentException("step is negative!");
		}
		if (start > count) {
			throw new IllegalArgumentException("start is outside the scope");	
		}

	}
	/**
	 * Latitude must be between -90 and 90 °
	 * @param latitude
	 * @throws IllegalArgumentException if latitude is not within the bounds.
	 */
	private void testLatitude(double latitude) {
		if (latitude < -90.0 || 90.0 < latitude) {
			throw new IllegalArgumentException("Latitude is invalid! Value: " + latitude);
		}
	}

	/**
	 * Longitude must be between -180 and 180 °
	 * @param longitude
	 * @throws IllegalArgumentException if longitude is not within the bounds.
	 */
	private void testLongitude(double longitude) {
		if (longitude < -180.0 || 180.0 < longitude) {
			throw new IllegalArgumentException("Longitude is invalid! Value: " + longitude);
		}
	}

	/**
	 * Test if a tag is valid
	 * @param t
	 * @throws InvalidTagException if the tag is invalid;
	 */
	private void testTag(Tag t) {
		try {
			em.merge(t);
		}
		catch (IllegalArgumentException e){
			throw new InvalidTagException(t);
		}
	}

	/**
	 * Test if an address is valid
	 * @param t
	 * @throws InvalidAddressException if the tag is invalid;
	 */
	private void testAddress(Address a) {
		try {
			em.merge(a);
		}
		catch (IllegalArgumentException e){
			throw new InvalidAddressException(a);
		}
	}



	@Override
	public Integer getLocatedObjectsCount() {
		Query q = em.createQuery("SELECT COUNT(lo) FROM LocatedObject lo");
		try {
			long c = (Long) q.getSingleResult();
			int r = (int) c;
			return r;
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}

	}



	@Override
	public List<LocatedObject> getLocatedObjects(int start, int step) {
		int count = getLocatedObjectsCount();
		testStartAndStep(start, step, count);
		if (step == 0) {
			step = count;
		}
		TypedQuery<LocatedObject> q = em.createQuery("SELECT lo FROM LocatedObject lo ORDER BY lo.name",
				LocatedObject.class);
		q.setFirstResult(start);
		q.setMaxResults(step);

		try {
			return q.getResultList();
		}
		catch (Throwable t) {
			throw new BDDException(t);	
		}		
	}

	@Override
	public Integer getLocatedObjectsCount(Tag tag) {
		if (tag == null) {
			throw new NullPointerException("tag can't be null");
		}

		try {
			em.merge(tag);
		}
		catch (IllegalArgumentException e) {
			throw new InvalidTagException(tag);
		}

		Query q = em.createQuery("SELECT COUNT(lo) FROM LocatedObject lo WHERE :tag MEMBER OF lo.tags  ");
		q.setParameter("tag", tag);
		try {
			long c = (Long) q.getSingleResult();
			int r = (int) c;
			return r;
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}
	}

	@Override
	public List<LocatedObject> getLocatedObjects(Tag tag, int start, int step) {
		if (tag == null) {
			throw new NullPointerException("tag can't be null");
		}

		int count = getLocatedObjectsCount(tag);
		testStartAndStep(start, step, count);
		if (step == 0) {
			step = count;
		}
		try {
			em.merge(tag);
		}
		catch (IllegalArgumentException e) {
			throw new InvalidTagException(tag);
		}

		TypedQuery<LocatedObject> q = em.createQuery("SELECT lo FROM LocatedObject lo WHERE :tag MEMBER OF lo.tags", LocatedObject.class);
		q.setParameter("tag", tag);
		try {
			return q.getResultList();
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}
	}



	@Override
	public List<LocatedObject> getLocatedObjects(double latitude,
			double longitude) {
		/* latitude between -90 and 90°
		 * longitude between -180 and 180°
		 */
		if (!(-90 < latitude && latitude < 90)) {
			throw new IllegalArgumentException("latitude is invalid!");
		}
		if (!(-180 < longitude && longitude < 180)) {
			throw new IllegalArgumentException("longitude is invalid!");			
		}

		TypedQuery<LocatedObject> q = em.createQuery("SELECT lo FROM LocatedObject lo "
				+ "WHERE (lo.latitude = :latitude) and (lo.longitude = :longitude) ", LocatedObject.class);

		q.setParameter("latitude", latitude); 
		q.setParameter("longitude",  longitude);

		try { 

			return q.getResultList();

		}
		catch (Throwable t) {
			throw new BDDException(t);
		}

	}

	@Override
	public List<LocatedObject> getLocatedObjects(double latitude,
			double longitude, double altitude) {

		/* latitude between -90 and 90°
		 * longitude between -180 and 180°
		 */
		if (!(-90 < latitude && latitude < 90)) {
			throw new IllegalArgumentException("latitude is invalid!");
		}
		if (!(-180 < longitude && longitude < 180)) {
			throw new IllegalArgumentException("longitude is invalid!");			
		}

		TypedQuery<LocatedObject> q = em.createQuery("SELECT lo FROM LocatedObject lo "
				+ "WHERE (lo.latitude = :latitude) and (lo.longitude = :longitude) and (lo.altitude = :altitude) ", LocatedObject.class);

		q.setParameter("latitude", latitude); 
		q.setParameter("longitude",  longitude);
		q.setParameter("altitude", altitude);

		try { 

			return q.getResultList();

		}
		catch (Throwable t) {
			throw new BDDException(t);
		}

	}



	@Override
	public LocatedObject getLocatedObject(String uuid) {
		TypedQuery<LocatedObject> q = em.createQuery("SELECT lo FROM LocatedObject lo "
				+ "WHERE (lo.uuid = :uuid)", LocatedObject.class);
		q.setParameter("uuid", uuid);
		try {
			LocatedObject lo = q.getSingleResult();
			return lo;
		}
		catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}
	}

	public Integer getTagsLikeCount(String substring) {
		if (substring == null) {
			throw new NullPointerException("substring can't be null");
		}


		Query q = em.createQuery("SELECT COUNT(t) FROM Tag t "
				+ "WHERE t.name LIKE :subs ");
		q.setParameter("subs", substring + "%");
		try {
			long c = (Long) q.getSingleResult();
			int r = (int) c;
			return r;
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}

	}

	@Override
	public List<Tag> getTagsLike(String substring, int start, int step) {
		if (substring == null) {
			throw new NullPointerException("substring can't be null");
		}

		int count = getLocatedObjectsLikeCount(substring);
		testStartAndStep(start, step, count);
		if (step == 0) {
			step = count;
		}
		TypedQuery<Tag> q = em.createQuery("SELECT t FROM Tag t "
				+ "WHERE t.name LIKE :subs ORDER BY t.name", Tag.class);
		q.setFirstResult(start);
		q.setMaxResults(step);
		q.setParameter("subs", substring + "%");
		try {
			return q.getResultList();
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}		


	}

	public Integer getLocatedObjectsLikeCount(String substring) {
		if (substring == null) {
			throw new NullPointerException("substring can't be null");
		}


		Query q = em.createQuery("SELECT COUNT(lo) FROM LocatedObject lo "
				+ "WHERE lo.name LIKE :subs ORDER BY lo.name");
		q.setParameter("subs", "%" + substring + "%");
		try {
			long c = (Long) q.getSingleResult();
			int r = (int) c;
			return r;
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}
	}

	@Override
	public List<LocatedObject> getLocatedObjects(String substring, int start,
			int step) {
		if (substring == null) {
			throw new NullPointerException("substring can't be null");
		}

		int count = getLocatedObjectsLikeCount(substring);
		testStartAndStep(start, step, count);
		if (step == 0) {
			step = count;
		}
		TypedQuery<LocatedObject> q = em.createQuery("SELECT lo FROM LocatedObject lo "
				+ "WHERE lo.name LIKE :subs ORDER BY lo.name", LocatedObject.class);
		q.setFirstResult(start);
		q.setMaxResults(step);
		q.setParameter("subs", "%" + substring + "%");
		try {
			return q.getResultList();
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}
	}



	@Override
	public Integer getAddressesCount() {		
		Query q = em.createQuery("SELECT COUNT(a) FROM Address a ");
		try {
			long c = (Long) q.getSingleResult();
			int r = (int) c;
			return r;
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}

	}

	@Override
	public void insertAddress(Address ad) {
		try {
			em.persist(ad);
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}


	}

	@Override
	public Integer getTagsCount() {
		Query q = em.createQuery("SELECT COUNT(t) FROM Tag t ");
		try {
			long c = (Long) q.getSingleResult();
			int r = (int) c;
			return r;
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}
	}

	@Override
	public Tag getTag(String name) {
		try { 
			return em.createQuery("SELECT t from Tag t WHERE t.name = :name ",Tag.class)
					.setParameter("name", name)
					.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		catch (Throwable e) {
			throw new BDDException(e);
		}

	}

	@Override
	public List<Address> getAddresses(int start, int step) {
		int count = getAddressesCount();
		testStartAndStep(start, step, count);
		if (step == 0) {
			step = count;
		}
		TypedQuery<Address> q = em.createQuery("SELECT a FROM Address a",
				Address.class);
		q.setFirstResult(start);
		q.setMaxResults(step);

		try {
			return q.getResultList();
		}
		catch (Throwable t) {
			throw new BDDException(t);	
		}
	}

	@Override
	public Address getAddress(String uuid) {
		try { 
			TypedQuery<Address> q = em.createQuery("SELECT a from Address a WHERE a.uuid = :uuid ", Address.class);
			q.setParameter("uuid", uuid);
			return q.getSingleResult();
		}
		catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
		catch (Throwable e) {
			throw new BDDException(e);
		}
	}

	@Override
	public Address getAddress(String street, String zipCode, String city, String country) {
		try {
			Address address = em.createQuery("SELECT a from Address a WHERE "
					+ "a.street = :street "
					+ "AND a.zipcode = :zipcode "
					+ "AND a.city = :city "
					+ "AND a.country = :country ",Address.class)
					.setParameter("street",street)
					.setParameter("zipcode",zipCode)
					.setParameter("city", city)
					.setParameter("country",country)
					.getSingleResult();

			return address;
		} 
		catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
		catch(Exception ex){
			throw new BDDException(ex.getMessage());
		}
	}

	@Override
	public void insert(LocatedObject lo) {
		try{

			if(lo.getAddresses() != null)
			{
				try{

					Address addrLo = getAddress(lo.getAddresses().getStreet(),lo.getAddresses().getZipcode(),
							lo.getAddresses().getCity(), lo.getAddresses().getCountry());

					if(addrLo != null) {
						lo.setAddresses(addrLo);
					}
					else {
						em.persist(lo.getAddresses());

						Address addrLo2 = getAddress(lo.getAddresses().getStreet(),lo.getAddresses().getZipcode(),
								lo.getAddresses().getCity(), lo.getAddresses().getCountry());

						lo.setAddresses(addrLo2);
					}

				} catch(Throwable e) {

					throw new BDDException(e);
				}
			}

			Set<Tag> tmpTags = new HashSet<>();

			for( Tag t : lo.getTags())
			{
				Tag ta = getTag(t.getName());
				if( ta == null) {
					em.persist(t);
					tmpTags.add(getTag(t.getName()));
				}
				else {
					tmpTags.add(ta);
				}

			}

			lo.getTags().clear();

			em.persist(lo);

			Set<Tag> set = lo.getTags();

			for(Tag t : tmpTags) {
				set.add(t);
			}

			em.merge(lo);

		} catch(Throwable e){
			throw new BDDException(e);
		}
	}

	@Override
	public List<Tag> getTags(int start, int step) {
		int count = getTagsCount();
		testStartAndStep(start, step, count);
		if (step == 0) {
			step = count;
		}
		TypedQuery<Tag> q = em.createQuery("SELECT t FROM Tag t",
				Tag.class);
		q.setFirstResult(start);
		q.setMaxResults(step);

		try {
			return q.getResultList();
		}
		catch (Throwable t) {
			throw new BDDException(t);	
		}
	}



	@Override
	public int getLocatedObjectsInAreaCount(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags) {

		testLatitude(latitude1);
		testLatitude(latitude2);
		testLongitude(longitude1);
		testLongitude(longitude2);
		for (Tag t: tags) {
			testTag(t);
		}

		try{

			Query q;
			if (!tags.isEmpty()) {
				q = em.createQuery(
						"SELECT COUNT(lo) FROM LocatedObject lo "
								+ "WHERE "
								+ "(lo.latitude BETWEEN :latitude1 AND :latitude2) "
								+ " AND "
								+ "(lo.longitude BETWEEN :longitude1 AND :longitude2 ) "
								+ " AND "
								+ "EXISTS(SELECT t FROM lo.tags t WHERE t IN (:tags) ) ");
				q.setParameter("tags", tags);
			}
			else {
				q = em.createQuery(
						"SELECT COUNT(lo) FROM LocatedObject lo "
								+ "WHERE "
								+ "(lo.latitude BETWEEN :latitude1 AND :latitude2) "
								+ " AND "
								+ "(lo.longitude BETWEEN :longitude1 AND :longitude2 ) ");
			}
			q.setParameter("latitude1", latitude1);
			q.setParameter("latitude2", latitude2);
			q.setParameter("longitude1", longitude1);
			q.setParameter("longitude2", longitude2);

			long l = (Long) q.getSingleResult();
			return  (int) l;

		} 
		catch (Throwable t) {
			throw new BDDException(t);
		}
	}

	@Override
	public List<LocatedObject> getLocatedObjectsInArea(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags, int start, int step) {


		int count = getLocatedObjectsCount();
		testStartAndStep(start, step, count);
		if (step == 0) {
			step = count;
		}

		try{

			TypedQuery<LocatedObject> q;
			if (!tags.isEmpty()) {
				q = em.createQuery(
						"SELECT lo FROM LocatedObject lo "
								+ "WHERE "
								+ "(lo.latitude BETWEEN :latitude1 AND :latitude2) "
								+ " AND "
								+ "(lo.longitude BETWEEN :longitude1 AND :longitude2 ) "
								+ " AND "
								+ "EXISTS(SELECT t FROM lo.tags t WHERE t IN (:tags) ) ", 
								LocatedObject.class);
				q.setParameter("tags", tags);
			}
			else {
				q = em.createQuery(
						"SELECT lo FROM LocatedObject lo "
								+ "WHERE "
								+ "(lo.latitude BETWEEN :latitude1 AND :latitude2) "
								+ " AND "
								+ "(lo.longitude BETWEEN :longitude1 AND :longitude2 ) ",
								LocatedObject.class);
			}
			q.setParameter("latitude1", latitude1);
			q.setParameter("latitude2", latitude2);
			q.setParameter("longitude1", longitude1);
			q.setParameter("longitude2", longitude2);
			q.setFirstResult(start);
			q.setMaxResults(step);

			return q.getResultList();

		} 
		catch (Throwable t) {
			throw new BDDException(t);
		}

	}


	@Override
	public List<LocatedObject> getLocatedObjects(Address a) {
		testAddress(a);
		TypedQuery<Address> q = em.createQuery( "SELECT a FROM Address a JOIN FETCH a.locatedObjects WHERE a.id = :id", Address.class);
		q.setParameter("id", a.getId());
		try {
			return new ArrayList<LocatedObject> (q.getSingleResult().getLocatedObjects());
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}
	}
}
