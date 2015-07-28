package fr.m2i.formation.poec.geolocate.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;

public class ServiceGeolocate2 extends ServiceGeolocate  implements BDDService  {
	
	//@PersistenceContext(unitName="geolocatePU")
	private EntityManager em;

	@Override
	public Integer getLocatedObjectsCount() {
		Query q = em.createQuery("SELECT COUNT(lo) FROM LocatedObject ");
		try {
			long c = (Long) q.getSingleResult();
			int r = (int) c;
			return r;
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}

	}

	/**
	 * 
	 * @param start
	 * @param step
	 * @param count
	 * @throws IllegalArgumentException if start or step are invalid
	 */
	private void testStartAndStep(int start, int step, int count) {
		if (start <= 0) {
			throw new IllegalArgumentException("start is negative!"); 
		}
		if (step < 0) {
			throw new IllegalArgumentException("step is negative!");
		}
		if (start > count) {
			throw new IllegalArgumentException("start is outside the scope");	
		}

	}
	
	@Override
	public List<LocatedObject> getLocatedObjects(int start, int step) {
		int count = getLocatedObjectsCount();
		testStartAndStep(start, step, count);
		if (step == 0) {
			step = count;
		}
		TypedQuery<LocatedObject> q = em.createQuery("SELECT lo FROM LocatedObject LIMIT :step OFFSET :start",
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
		
		Query q = em.createQuery("SELECT lo FROM LocatedObject lo WHERE lo.tag = :tag ", LocatedObject.class);
		q.setParameter(":tag", tag);
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
		
		TypedQuery<LocatedObject> q = em.createQuery("SELECT lo FROM LocatedObject lo WHERE lo.tag = :tag ", LocatedObject.class);
		q.setParameter(":tag", tag);
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
		
		q.setParameter(":latitude", latitude); 
		q.setParameter(":longitude",  longitude);
		
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
				+ "WHERE (lo.latitude = :latitude) and (lo.longitude = :longitude) and (lo.altitude) ", LocatedObject.class);
		
		q.setParameter(":latitude", latitude); 
		q.setParameter(":longitude",  longitude);
		q.setParameter(":altitude", altitude);
		
		try { 
			
			return q.getResultList();
			
		}
		catch (Throwable t) {
			throw new BDDException(t);
		}
		
}

	@Override
	public LocatedObject getLocatedObject(String uuid) {
		TypedQuery<LocatedObject> q = em.createQuery("SELECT uuid FROM LocatedObject lo "
				+ "WHERE (lo.uuid = :uuid)", LocatedObject.class);
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

}
