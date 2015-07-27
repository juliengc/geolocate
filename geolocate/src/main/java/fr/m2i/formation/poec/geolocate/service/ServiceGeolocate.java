package fr.m2i.formation.poec.geolocate.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;

@Stateless
public class ServiceGeolocate implements BDDService {
	
	@PersistenceContext(unitName="GeolocatePU")
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag getTag(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Address> getAddresses(int start, int step) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address getAddress(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(LocatedObject lo) {
		// TODO Auto-generated method stub
		em.persist(lo);
	}

	@Override
	public List<Tag> getTags(int start, int step) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocatedObjectsInAreaCount(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LocatedObject> getLocatedObjectsInArea(double latitude1,
			double longitude1, double latitude2, double longitude2,
			List<Tag> tags, int start, int step) {
		// TODO Auto-generated method stub
		return null;
	}

}
