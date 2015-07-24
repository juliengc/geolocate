package fr.m2i.formation.poec.geolocate.webadmin.output;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("consultAll")
@RequestScoped
public class ConsultAllLocatedObject {

	private static final Logger logger = Logger.getLogger(ConsultAllLocatedObject.class.getName());  
	
	private List<String> allObjects;
	private String oneTag;
	
	
	public String getOneTag() {
		logger.info("Get One tag <--- "+oneTag);
		return oneTag;
	}

	public void setOneTag(String oneTag) {
		logger.info("Set One tag ---> "+oneTag);
		this.oneTag = oneTag;
	}

	public List<String> getAllObjects() {
		return allObjects;
	}

	public void setAllObjects(List<String> allObjects) {
		this.allObjects = allObjects;
	}

	@PostConstruct
	void init(){
		
		logger.info("ConsultAllLocatedObject --> init");
		allObjects = new ArrayList<>();
		
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
		
	
		logger.info("ConsultAllLocatedObject --> init END");
	}
	
	
	public void doFilter(){
		logger.info("ConsultAllLocatedObject --> doFilter " +oneTag);
		allObjects.clear();
		allObjects.add("object1");
		allObjects.add("object2");
		allObjects.add("object3");
		allObjects.add("object4");
		allObjects.add("object5");
		
		logger.info("ConsultAllLocatedObject --> doFilter " +allObjects.size());
	}
	
}
