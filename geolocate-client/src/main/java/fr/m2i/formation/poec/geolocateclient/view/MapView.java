package fr.m2i.formation.poec.geolocateclient.view;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import fr.m2i.formation.poec.geolocateclient.domain.LocatedObject;
import fr.m2i.formation.poec.geolocateclient.rest.RestClient;

@Named("mapView")
@ViewScoped
public class MapView  implements Serializable  {
	
	private static final Logger logger = Logger.getLogger(MapView.class.getName());     
	
	   private MapModel modelMap;
	   private LatLngBounds currentArea;
	     
	   private String title;
	     
	   private double lat;
	   private double lng;
	   private int zoom;
	   private List<LocatedObject> allObjects;
	   
	   private RestClient servicesWS = new RestClient();
	   
		@PostConstruct
		   public void init() {
		       modelMap = new DefaultMapModel();
		       lat = 43.6043401;
		       lng = 7.0174095;
		       zoom = 13;
		       //	       center="43.6043401, 7.0174095" zoom="13" 
		     //  LoadedAllObjects();
		      // generateMarkers();
		   }
	 
		
	public int getZoom() {
			return zoom;
		}


		public void setZoom(int zoom) {
			this.zoom = zoom;
		}


	public LatLngBounds getCurrentArea() {
		return currentArea;
	}

	public void setCurrentArea(LatLngBounds currentArea) {
		this.currentArea = currentArea;
	}

	public List<LocatedObject> getAllObjects() {
		return allObjects;
	}

	public void setAllObjects(List<LocatedObject> allObjects) {
		this.allObjects = allObjects;
	}

	public MapModel getModelMap() {
		return modelMap;
	}

	public void setModelMap(MapModel modelMap) {
		this.modelMap = modelMap;
	}


	     
	   public MapModel getEmptyModel() {
	       return modelMap;
	   }
	     
	   public String getTitle() {
	       return title;
	   }
	 
	   public void setTitle(String title) {
	       this.title = title;
	   }
	 
	   public double getLat() {
	       return lat;
	   }
	 
	   public void setLat(double lat) {
	       this.lat = lat;
	   }
	 
	   public double getLng() {
	       return lng;
	   }
	 
	   public void setLng(double lng) {
	       this.lng = lng;
	   }
	     
	   //Don't use now : function of test
	   public void addMarker() {
	       Marker marker = new Marker(new LatLng(lat, lng), title);
	       modelMap.addOverlay(marker);
	       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
	   }
	   
	   public void onStateChange(StateChangeEvent event) {
	      
		   setCurrentArea( event.getBounds());
	      zoom = event.getZoomLevel();
	      lat = event.getCenter().getLat();
	      lng = event.getCenter().getLng();
	      
	       logger.info("Bound "+getCurrentArea().getNorthEast().toString()+", "+getCurrentArea().getSouthWest().toString()+"  -> Zoom "+zoom);
	       
	       LoadedAllObjects();
	       generateMarkers();
	       
	       logger.info("END onStateChange");
	   }
	   
	   public void LoadedAllObjects(){
		   
		   logger.info("LoadedAllObjects");
	   
	   //43.606931,7.0160964, Centre Azuréen de Cancérologie
		   //43.6124615,7.0161465, Mougins School
		   //@43.6078453,7.0266219 stade de la valmasque
		   
		   if ( currentArea == null){
			   setAllObjects(servicesWS.getAllObjects	(	0,	0,	0,	0));
		   }else{
			   
			   setAllObjects(servicesWS.getAllObjects	(	getCurrentArea().getNorthEast().getLat()
				   									,	getCurrentArea().getNorthEast().getLng()
				   									,	getCurrentArea().getSouthWest().getLat()
				   									,	getCurrentArea().getSouthWest().getLng()
				   									)
				   		);
		   }
	   }
	   
	   public void generateMarkers(){
		   logger.info("Generate Markers");
	   
		   getModelMap().getMarkers().clear();
		   
		   for (LocatedObject locatedObject : allObjects) {
			   getModelMap().addOverlay(new Marker(new LatLng(locatedObject.getLatitude(), locatedObject.getLongitude())
			   									, locatedObject.getName()
			   									//));
			   , locatedObject.getDescription()
			   									, "http://www.google.com/mapfiles/kml/paddle/"+locatedObject.getName().toUpperCase().charAt(0)+".png"));/**/
		   }
		   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
	   }
	
	
	
}
