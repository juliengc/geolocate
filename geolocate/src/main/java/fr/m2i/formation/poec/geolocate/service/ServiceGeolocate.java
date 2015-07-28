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

@Stateless
@LocalBean
public abstract class ServiceGeolocate implements BDDService {

	@PersistenceContext(unitName="geolocatePU")
	private EntityManager em;

	




	@Override
	public List<Address> getAddresses(int start, int step) {
		// TODO Auto-generated method stub
		/*return em.createQuery("SELECT a from Adress LIMIT :step OFFSET :start ",Address.class)
				.setParameter("start", start*step)
				.setParameter("step",step)
				.getResultList();*/
		try {

			List<Address> addresses =  em.createQuery("SELECT a from Adress",Address.class)
					.setFirstResult(start*step)
					.setMaxResults(step)
					.getResultList();

			return addresses;
		} catch(IllegalArgumentException  e) {
			throw e;
		}
		catch(Exception ex){
			throw new BDDException(ex.getMessage());
		}
	}

	@Override
	public Address getAddress(String uuid) {
		// TODO Auto-generated method stub
		try{
			Address address = em.createQuery("SELECT a from Address a WHERE a.uuid = :uid ",Address.class)
					.setParameter("uid",uuid)
					.getSingleResult();
			return address;
		} catch (NoResultException e) {
			return null;
		}
		catch(Exception ex){
			throw new BDDException(ex.getMessage());
		}

	}

	public Address getAddress(String street, String zipCode, String city, String country) {
		// TODO Auto-generated method stub
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
		} catch (NoResultException e) {
			return null;
		}
		catch(Exception ex){
			throw new BDDException(ex.getMessage());
		}
	}

	@Override
	public void insert(LocatedObject lo) {
		// TODO Auto-generated method stub
		try{

			if(lo.getAddresses()!=null)
			{
				try{

					Address addrLo = getAddress(lo.getAddresses().getStreet(),lo.getAddresses().getZipcode(),
							lo.getAddresses().getCity(), lo.getAddresses().getCountry());

					lo.setAddresses(addrLo);

				} catch(NoResultException e) {

					em.persist(lo.getAddresses());

					Address addrLo2 = getAddress(lo.getAddresses().getStreet(),lo.getAddresses().getZipcode(),
							lo.getAddresses().getCity(), lo.getAddresses().getCountry());

					lo.setAddresses(addrLo2);
				}
			}

			em.persist(lo);

		} catch(Exception e){
			throw new BDDException(e.getMessage());
		}
	}

	@Override
	public Tag getTag(String name) {
		// TODO Auto-generated method stub
		try { 
			return em.createQuery("SELECT t from Tag t WHERE t.name = :name ",Tag.class)
					.setParameter("name", name)
					.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		catch (Exception ex) {
			throw new BDDException(ex.getMessage());
		}

	}

	@Override
	public List<Tag> getTags(int start, int step) {
		// TODO Auto-generated method stub
		/*return em.createQuery("SELECT t from Tag LIMIT :step OFFSET :start ",Tag.class)
				.setParameter("start", start*step)
				.setParameter("step",step)
				.getResultList();*/
		try{
			List<Tag> tags = em.createQuery("SELECT t from Tag",Tag.class)
					.setFirstResult(start*step)
					.setMaxResults(step)
					.getResultList();

			return tags;
		} catch (IllegalArgumentException e) {
			throw e;
		}
		catch(Exception ex){
			throw new BDDException(ex.getMessage());
		}

	}

	public List<Tag> getTags() {
		// TODO Auto-generated method stub
		try{
			List<Tag> tags = em.createQuery("SELECT t from Tag",Tag.class).getResultList();
			return tags;
		}
		catch(Exception ex){
			throw new BDDException(ex.getMessage());
		}
	}


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

	@Override
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

	@Override
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
