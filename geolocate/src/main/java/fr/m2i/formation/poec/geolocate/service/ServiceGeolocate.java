package fr.m2i.formation.poec.geolocate.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;

@Stateless
@LocalBean
public class ServiceGeolocate implements BDDService {
	
	@PersistenceContext(unitName="geolocatePU")
	private EntityManager em;

	@Override
	public Integer getLocatedObjectsCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LocatedObject> getLocatedObjects(int start, int step) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getLocatedObjectsCount(Tag tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LocatedObject> getLocatedObjects(Tag tag, int start, int step) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LocatedObject> getLocatedObjects(double latitude,
			double longitude) {
		// TODO Auto-generated method stub
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
		
		LocatedObject oneLocated = null;
		
		try{
			oneLocated =  em.createQuery("SELECT lo from LocatedObject lo WHERE lo.uuid=:uuid ",LocatedObject.class)
										.setParameter("uuid", uuid)
										.getSingleResult();
		}catch ( NoResultException | NonUniqueResultException ex){
			throw new BDDException("Object Located Not Found");
		}
		
		return oneLocated;
	}


	@Override
	public List<Address> getAddresses(int start, int step) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT a from Adress LIMIT :step OFFSET :start ",Address.class)
				.setParameter("start", start)
				.setParameter("step",step)
				.getResultList();
	}

	@Override
	public Address getAddress(String uuid) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT a from Address WHERE a.uuid = :uid ",Address.class)
				.setParameter("uid",uuid)
				.getSingleResult();
	}

	@Override
	public void insert(LocatedObject lo) {
		// TODO Auto-generated method stub
		em.persist(lo);
	}
	
	@Override
	public Tag getTag(String name) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT t from Tag WHERE t.name = :tName ",Tag.class)
				.setParameter("tName", name)
				.getSingleResult();
	}

	@Override
	public List<Tag> getTags(int start, int step) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT t from Tag LIMIT :step OFFSET :start ",Tag.class)
				.setParameter("start", start)
				.setParameter("step",step)
				.getResultList();
	}
	
	public List<Tag> getTags() {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT t from Tag",Tag.class).getResultList();
	}


	@Override
	public int getLocatedObjectsInAreaCount(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags) {
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

	@Override
	public List<LocatedObject> getLocatedObjectsInArea(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags, int start, int step) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT lo FROM LocatedObject lo WHERE"
				+ "(lo.latitude BETWEEN :latitude1 AND :latitude2 ) "
				+ "AND (lo.longitude BETWEEN :longitude1 AND :longitude2 )"
				+ "lo.tags IN :tags ", LocatedObject.class)
				.setParameter("latitude1", latitude1)
				.setParameter("longitude1", longitude1)
				.setParameter("latitude2", latitude2)
				.setParameter("longitude2", longitude2)
				.setParameter("tags", tags)
				.getResultList();
	}

	@Override
	public int getLocatedObjectsInAreaCountStr(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<String> tags) {
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

	@Override
	public List<LocatedObject> getLocatedObjectsInAreaStr(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<String> tags, int start, int step) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT lo FROM LocatedObject lo WHERE"
				+ "(lo.latitude BETWEEN :latitude1 AND :latitude2 ) "
				+ "AND (lo.longitude BETWEEN :longitude1 AND :longitude2 )"
				+ "lo.tags.name IN :tags ", LocatedObject.class)
				.setParameter("latitude1", latitude1)
				.setParameter("longitude1", longitude1)
				.setParameter("latitude2", latitude2)
				.setParameter("longitude2", longitude2)
				.setParameter("tags", tags)
				.getResultList();
	}

}
