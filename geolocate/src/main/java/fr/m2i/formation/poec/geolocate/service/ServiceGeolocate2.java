package fr.m2i.formation.poec.geolocate.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;

public class ServiceGeolocate2 extends ServiceGeolocate{
	
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

	@Override
	public List<LocatedObject> getLocatedObjects(int start, int step) {
		if (start <= 0) {
			throw new IllegalArgumentException("start is negative!"); 
		}
		if (step <= 0) {
			throw new IllegalArgumentException("step is negative!");
		}
		if (start > getLocatedObjectsCount()) {
			throw new IllegalArgumentException("start is outside the scope");	
		}
		
		TypedQuery<LocatedObject> q = em.createQuery("SELECT lo FROM LocatedObject LIMIT :step OFFSET :start",
				LocatedObject.class);
		q.setParameter("start", start);
		q.setParameter("step",step);
		try {
			return q.getResultList();
		}
		catch (Throwable t) {
			throw new BDDException(t);	
		}		
	}

	@Override
	public Integer getLocatedObjectsCount(Tag tag) {
		
		if (getTags() == null) {
			throw new IllegalArgumentException("tag can't be null");
		}
		
		if (getLocatedObjectsCount() <= 0) {
			throw new IllegalArgumentException("number of located objects tagged can't be negative");	
		}
			
		Query q = em.createQuery("SELECT lo WITH tag FROM LocatedObject  ", LocatedObject.class);
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
		
		if (getTags() == null) {
			throw new IllegalArgumentException("tag can't be null");
		}
		
		if (getLocatedObjectsCount() <= 0) {
			throw new IllegalArgumentException("number of located objects tagged can't be negative");	
		}
		
			
		Query q = em.createQuery("SELECT lo WITH tag FROM LocatedObject ORDER_BY_NAME ", LocatedObject.class);
		try {
			long c = (Long) q.getSingleResult();
			int r = (int) c;
			return r;
		}
		catch (Throwable t) {
			throw new BDDException(t);
		} try {
			
			
			
		} catch (Throwable t) {
			throw new InvalidTagException(t);
			
		}
	}
		
		

	@Override
	public List<LocatedObject> getLocatedObjects(double latitude,
			double longitude) {
		
		
		return null;
	}

	@Override
	public List<LocatedObject> getLocatedObjects(double latitude,
			double longitude, double altitude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocatedObject getLocatedObject(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

}
