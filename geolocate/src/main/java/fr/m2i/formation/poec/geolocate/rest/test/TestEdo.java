package fr.m2i.formation.poec.geolocate.rest.test;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import fr.m2i.formation.poec.geolocate.rest.RestInsert;

@ApplicationPath("/rest")
public class TestEdo extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(RestInsert.class);
		return super.getClasses();
	}
	
}