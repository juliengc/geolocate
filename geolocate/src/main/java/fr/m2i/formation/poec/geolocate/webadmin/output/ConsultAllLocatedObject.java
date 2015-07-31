package fr.m2i.formation.poec.geolocate.webadmin.output;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.service.BDDServiceImpl;


@Named("consultAll")
@ViewScoped
public class ConsultAllLocatedObject implements Serializable{

	private static final Logger logger = Logger.getLogger(ConsultAllLocatedObject.class.getName());  
	
	@Inject
	private BDDServiceImpl locatedObjectService;
	
	/*@Inject
	private ServiceGeolocate2 locatedObjectService2;*/
	
	private List<LocatedObject> allObjects;
	private String oneTag;
	private Integer step = 50;
	private Integer countPage = 1;
	private Integer indexPage = 1;
	
	public Integer getStartElem(){
		return ((indexPage-1)*step);
	}
	
	public boolean getShowBack(){
		return (countPage >  1 && indexPage > 1); 
	}
	
	public boolean getShowNext(){
		return (countPage >  1 && (indexPage < countPage)  ); 
	}
	
	
	public Integer getIndexPage() {
		logger.info("Get Index Page "+indexPage+" - " + FacesContext.getCurrentInstance().getCurrentPhaseId() );
		return indexPage;
	}

	public void setIndexPage(Integer indexPage) {
		this.indexPage = indexPage;
	}

	public Integer getCountPage() {
		logger.info("Get Count Page "+countPage);
		
		return countPage;
	}

	public void setCountPage(Integer countPage) {
		if ( countPage<=0)
			this.countPage = 1;
		else
			this.countPage = countPage;
	}

	public String getOneTag() {
		logger.info("Get One tag <--- "+oneTag);
		return oneTag;
	}

	public void setOneTag(String oneTag) {
		logger.info("Set One tag ---> "+oneTag);
		this.oneTag = oneTag;
	}

	public List<LocatedObject> getAllObjects() {
		logger.info("get getAllObjects ---> "+allObjects.size());
		return allObjects;
	}

	public void setAllObjects(List<LocatedObject> allObjects) {
		logger.info("set setAllObjects ---> "+allObjects.size());
		this.allObjects = allObjects;
	}

	@PostConstruct
	void init(){
		
		logger.info("ConsultAllLocatedObject --> init");
		
		setAllObjects(locatedObjectService.getLocatedObjects(getStartElem(), step));
		setCountPage((int)Math.round( (double)locatedObjectService.getLocatedObjectsCount()/(double)step));
	
		logger.info("ConsultAllLocatedObject --> init END nbPage :" + countPage.toString()+", nbObject : "+allObjects.size());
	}
	
	
	public void doFilter(){
		logger.info("ConsultAllLocatedObject --> doFilter " +oneTag);
		
		if ( oneTag!= null && !oneTag.isEmpty()){
			setAllObjects(locatedObjectService.getLocatedObjects(oneTag, getStartElem(), step) );
			setCountPage( (int)Math.round( (double)locatedObjectService.getLocatedObjectsLikeCount(oneTag)/(double)step));
		}
		else{
			setAllObjects(locatedObjectService.getLocatedObjects(getStartElem(), step) );
			setCountPage( (int)Math.round( (double)locatedObjectService.getLocatedObjectsCount()/(double)step));
		}
		
		indexPage=1;
		logger.info("ConsultAllLocatedObject --> doFilter nbObject: " +allObjects.size()+" nbPage "+countPage );
	}
	
	public void doBack(){
		logger.info("ConsultAllLocatedObject doBack --> " +indexPage);
		indexPage--;
		if ( oneTag!= null && !oneTag.isEmpty() ){
			setAllObjects(locatedObjectService.getLocatedObjects(oneTag, getStartElem(), step) );
		}
		else{
			setAllObjects(locatedObjectService.getLocatedObjects(getStartElem(), step) );
		}
		logger.info("ConsultAllLocatedObject doBack END--> " +indexPage);
	}
	
	public void doNext(){
		logger.info("ConsultAllLocatedObject doNext --> " +indexPage);
		indexPage++;
		
		if ( oneTag != null && !oneTag.isEmpty() ){
			setAllObjects(locatedObjectService.getLocatedObjects(oneTag, getStartElem(), step) );
		}
		else{
			setAllObjects(locatedObjectService.getLocatedObjects(getStartElem(), step) );
		}
		
		logger.info("ConsultAllLocatedObject doNext END--> " +indexPage);
	}
	
}
