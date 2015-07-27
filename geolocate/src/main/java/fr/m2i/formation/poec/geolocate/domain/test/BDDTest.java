package fr.m2i.formation.poec.geolocate.domain.test;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;

@Stateless
public class BDDTest {
	@PersistenceContext(unitName="geolocatePU")
	private EntityManager em;
	
	public List<Address> getAddresses() {
		//List<Address> addresses = new ArrayList<>();
		TypedQuery<Address> q = em.createQuery("SELECT a FROM Address a", Address.class);
		return q.getResultList();
	}

	public List<Tag> getTags() {
		//List<Tag> tags = new ArrayList<>();
		TypedQuery<Tag> q = em.createQuery("SELECT a FROM Tag a", Tag.class);
		return q.getResultList();
		
	}

	public List<LocatedObject> getLocatedObjects() {
		//List<Tag> tags = new ArrayList<>();
		TypedQuery<LocatedObject> q = em.createQuery("SELECT lo FROM LocatedObject lo", LocatedObject.class);
		return q.getResultList();
		
	}

}
