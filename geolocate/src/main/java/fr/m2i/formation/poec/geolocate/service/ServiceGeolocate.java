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

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;


 abstract class ServiceGeolocate implements BDDService {

	//@PersistenceContext(unitName="geolocatePU")
	private EntityManager em;

	

	@Override
	public int getLocatedObjectsInAreaCount(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags) {
		try{
			Integer countValues =	(Integer) em.createNativeQuery("SELECT COUNT(*) FROM LocatedObject lo WHERE"
					+ "(lo.latitude BETWEEN :latitude1 AND :latitude2 ) "
					+ "AND (lo.longitude BETWEEN :longitude1 AND :longitude2 ) "
					+ "lo.tags IN :tags ")
					.setParameter("latitude1", latitude1)
					.setParameter("longitude1", longitude1)
					.setParameter("latitude2", latitude2)
					.setParameter("longitude2", longitude2)
					.setParameter("tags", tags)
					.getSingleResult();
			return  countValues;

		} 
		catch (NullPointerException e1) {
			throw e1;
		}
		catch (IllegalArgumentException e2) {
			throw e2;
		}
		catch(Exception ex){
			throw new BDDException(ex.getMessage());
		}

	}

	@Override
	public List<LocatedObject> getLocatedObjectsInArea(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags, int start, int step) {
		// TODO Auto-generated method stub
		try {
			List<LocatedObject> list =  em.createQuery("SELECT lo FROM LocatedObject lo WHERE"
					+ "(lo.latitude BETWEEN :latitude1 AND :latitude2 ) "
					+ "AND (lo.longitude BETWEEN :longitude1 AND :longitude2 )"
					+ "lo.tags IN :tags ", LocatedObject.class)
					.setParameter("latitude1", latitude1)
					.setParameter("longitude1", longitude1)
					.setParameter("latitude2", latitude2)
					.setParameter("longitude2", longitude2)
					.setParameter("tags", tags)
					.setFirstResult(start*step)
					.setMaxResults(step)
					.getResultList();

			return list;

		} 
		catch (NullPointerException e1) {
			throw e1;
		}
		catch (IllegalArgumentException e2) {
			throw e2;
		}
		catch(Exception ex){
			throw new BDDException(ex.getMessage());
		}

	}

	public int getLocatedObjectsInAreaCountStr(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<String> tags) {
		try{
			Integer countValues =	(Integer) em.createNativeQuery("SELECT COUNT(*) FROM LocatedObject lo WHERE"
					+ "(lo.latitude BETWEEN :latitude1 AND :latitude2 ) "
					+ "AND (lo.longitude BETWEEN :longitude1 AND :longitude2 ) "
					+ "lo.tags.name IN :tags ")
					.setParameter("latitude1", latitude1)
					.setParameter("longitude1", longitude1)
					.setParameter("latitude2", latitude2)
					.setParameter("longitude2", longitude2)
					.setParameter("tags", tags)
					.getSingleResult();
			return  countValues;
		} 
		catch (NullPointerException e1) {
			throw e1;
		}
		catch (IllegalArgumentException e2) {
			throw e2;
		}
		catch(Exception ex){
			throw new BDDException(ex.getMessage());
		}
	}

	public List<LocatedObject> getLocatedObjectsInAreaStr(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<String> tags, int start, int step) {
		// TODO Auto-generated method stub
		try{

			List<LocatedObject> list = em.createQuery("SELECT lo FROM LocatedObject lo WHERE"
					+ "(lo.latitude BETWEEN :latitude1 AND :latitude2 ) "
					+ "AND (lo.longitude BETWEEN :longitude1 AND :longitude2 )"
					+ "lo.tags.name IN :tags ", LocatedObject.class)
					.setParameter("latitude1", latitude1)
					.setParameter("longitude1", longitude1)
					.setParameter("latitude2", latitude2)
					.setParameter("longitude2", longitude2)
					.setParameter("tags", tags)
					.setFirstResult(start*step)
					.setMaxResults(step)
					.getResultList();

			return list;

		} 
		catch (NullPointerException e1) {
			throw e1;
		}
		catch (IllegalArgumentException e2) {
			throw e2;
		}
		catch(Exception ex){
			throw new BDDException(ex.getMessage());
		}
	}

	

}
