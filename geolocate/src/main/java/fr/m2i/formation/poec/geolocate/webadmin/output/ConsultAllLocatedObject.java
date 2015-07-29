package fr.m2i.formation.poec.geolocate.webadmin.output;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;
import fr.m2i.formation.poec.geolocate.service.BDDServiceImpl;


@Named("consultAll")
@RequestScoped
public class ConsultAllLocatedObject {

	private static final Logger logger = Logger.getLogger(ConsultAllLocatedObject.class.getName());  
	
	@Inject
	private BDDServiceImpl locatedObjectService;
	
	/*@Inject
	private ServiceGeolocate2 locatedObjectService2;*/
	
	private List<LocatedObject> allObjects;
	private String oneTag;
	private Integer step = 50;
	
	
	public String getOneTag() {
		logger.info("Get One tag <--- "+oneTag);
		return oneTag;
	}

	public void setOneTag(String oneTag) {
		logger.info("Set One tag ---> "+oneTag);
		this.oneTag = oneTag;
	}

	public List<LocatedObject> getAllObjects() {
		logger.info("get getAllObjects ---> ");
		return allObjects;
	}

	public void setAllObjects(List<LocatedObject> allObjects) {
		logger.info("set setAllObjects ---> ");
		this.allObjects = allObjects;
	}

	@PostConstruct
	void init(){
		
		logger.info("ConsultAllLocatedObject --> init");
		/*allObjects = new ArrayList<>();
		
		allObjects.add("object1");
		allObjects.add("object2");
		allObjects.add("object3");
		allObjects.add("object4");
		allObjects.add("object5");
		allObjects.add("object6");
		allObjects.add("object7");
		allObjects.add("object8");
		allObjects.add("object9");
		allObjects.add("object10");
		*/
		
	/*	LocatedObject objectLocated = new LocatedObject("object1 name", "description object 1", 2.0, 222.0, new Address("streee \n eeeee", "06200", "City", "state", "country"));
		logger.info("ConsultAllLocatedObject --> init NEW");
		objectLocated.setAltitude(0);
		logger.info("ConsultAllLocatedObject --> init setAltitude");
		Set<Tag> tags = new HashSet<>();
		
		tags.add(new Tag("tag11"));
		tags.add(new Tag("tag22"));
		
		logger.info("ConsultAllLocatedObject --> init new TAG");
		objectLocated.setTags(tags);
		logger.info("ConsultAllLocatedObject --> init Add TAG");
		allObjects.add(objectLocated);
		*/
		allObjects =  locatedObjectService.getLocatedObjects(0, 0);
	
		logger.info("ConsultAllLocatedObject --> init END");
	}
	
	
	public void doFilter(){
		logger.info("ConsultAllLocatedObject --> doFilter " +oneTag);
		setAllObjects(locatedObjectService.getLocatedObjects(oneTag, 0, 0) );
		
		logger.info("ConsultAllLocatedObject --> doFilter " +allObjects.size());
	}
	
}
