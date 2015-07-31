package fr.m2i.formation.poec.geolocateclient.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

import fr.m2i.formation.poec.geolocateclient.domain.LocatedObject;
import fr.m2i.formation.poec.geolocateclient.rest.RestClient;
import fr.m2i.formation.poec.geolocateclient.rest.exception.RestClientException;
import fr.m2i.formation.poec.geolocateclient.rest.exception.RestServiceErrorException;

@Named("mapView")
@ViewScoped
public class MapView  implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(MapView.class.getName());     

	private MapModel modelMap;
	private LatLngBounds currentArea;
	private String centerGeoMap;
	private TagCloudModel modelTagCloud;

	private String title;

	private String inputOneTag;
	private String inputTags;
	
	@DecimalMin("-90.00") @DecimalMax("90.00")
	private double lat;
	
	@DecimalMin("-180.00") @DecimalMax("180.00")
	private double lng;
	
	private String address;
	
	private List<String> tags;
	
	private Marker marker;
	

	@DecimalMin("-90.00") @DecimalMax("90.00")
	private double lati;
	
	@DecimalMin("-180.00") @DecimalMax("180.00")
	private double lngi;
	
	private int zoom;
	private List<LocatedObject> allObjects;

	private RestClient servicesWS = new RestClient();

	@PostConstruct
	public void init() {
		modelMap = new DefaultMapModel();
		lati = 43.6043401;
		lngi = 7.0174095;
		zoom = 13;
		centerGeoMap = Double.toString(lati)+ "," +Double.toString(lngi);
		
		modelTagCloud = new DefaultTagCloudModel();
		
		modelTagCloud.addTag(new DefaultTagCloudItem("Transformers", 1));
		modelTagCloud.addTag(new DefaultTagCloudItem("RIA", "#", 3));
        modelTagCloud.addTag(new DefaultTagCloudItem("AJAX", 2));
        modelTagCloud.addTag(new DefaultTagCloudItem("jQuery", "#", 5));
        modelTagCloud.addTag(new DefaultTagCloudItem("NextGen", 4));
        modelTagCloud.addTag(new DefaultTagCloudItem("JSF 2.0", "#", 2));
        modelTagCloud.addTag(new DefaultTagCloudItem("FCB", 5));
        modelTagCloud.addTag(new DefaultTagCloudItem("Mobile",  3));
        modelTagCloud.addTag(new DefaultTagCloudItem("Themes", "#", 4));
        modelTagCloud.addTag(new DefaultTagCloudItem("Rocks", "#", 1));
		
		inputTags = "";
        tags = new ArrayList<String>();

		LoadedAllObjects();
		generateMarkers();
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

	public double getLati() {
		return lati;
	}

	public void setLati(double lati) {
		this.lati = lati;
	}

	public double getLngi() {
		return lngi;
	}

	public void setLngi(double lngi) {
		this.lngi = lngi;
	}

	public String getCenterGeoMap() {
		return centerGeoMap;
	}


	public void setCenterGeoMap(String centerGeoMap) {
		this.centerGeoMap = centerGeoMap;
	}
	
    public TagCloudModel getModelTagCloud() {
        return modelTagCloud;
    }
     
    public void onSelect(SelectEvent event) {
        TagCloudItem item = (TagCloudItem) event.getObject();
        
        inputTags = "";
        if (! tags.contains(item.getLabel())) {
        	tags.add(item.getLabel());
        }
        for (String string : tags) {
			if (inputTags.isEmpty()) {
				inputTags = string;
			} else {
				inputTags = inputTags + "," + string;
			}
				
		}
    }
    
    public void clearInputTags() {
		inputTags = "";
        tags = new ArrayList<String>();
    }


	//Don't use now : function of test
	public void addMarker() {
		Marker marker = new Marker(new LatLng(lati, lngi), title);
		modelMap.addOverlay(marker);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
	}

	public void onStateChange(StateChangeEvent event) {

		setCurrentArea( event.getBounds());
		zoom = event.getZoomLevel();
		lati = event.getCenter().getLat();
		lngi = event.getCenter().getLng();
		centerGeoMap = Double.toString(lati)+ "," +Double.toString(lngi);


		logger.info("Bound "+getCurrentArea().getNorthEast().toString()+", "+getCurrentArea().getSouthWest().toString()+"  -> Zoom "+zoom);

		LoadedAllObjects();
		generateMarkers();

		logger.info("END onStateChange");
	}

	public void LoadedAllObjects() {

		logger.info("LoadedAllObjects");

		// 43.606931,7.0160964, Centre Azuréen de Cancérologie
		// 43.6124615,7.0161465, Mougins School
		// @43.6078453,7.0266219 stade de la valmasque
		try {
			if (currentArea == null) {

				setAllObjects(servicesWS.getLocatedObjectsArea(0.0, 0.0, 0.0,
						0.0));

			} else {

				setAllObjects(servicesWS.getLocatedObjectsArea
						(	getCurrentArea().getSouthWest().getLat()
						, 	getCurrentArea().getSouthWest().getLng()
						,	getCurrentArea().getNorthEast().getLat()
						, 	getCurrentArea().getNorthEast().getLng()
						));
			}

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestServiceErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generateMarkers(){
		logger.info("Generate Markers");

		if(allObjects == null) {
			return;
		}
		
		getModelMap().getMarkers().clear(); //getModelMap().clearMarkers()

		for (LocatedObject locatedObject : allObjects) {
			getModelMap().addOverlay(new Marker(new LatLng(locatedObject.getLatitude(), locatedObject.getLongitude())
			, locatedObject.getName()
			, "Desc: " + locatedObject.getDescription() + " | Tags : " + locatedObject.getTags().toString() 
			, "http://www.google.com/mapfiles/kml/paddle/"+locatedObject.getName().trim().toUpperCase().charAt(0)+".png"));/**/
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lati + ", Lng:" + lngi));
	}


	public void onGeocode(GeocodeEvent event) {
		  
		List<GeocodeResult> results = event.getResults();
		
		logger.info("Input localization centered by address : results : " + results.toString());

		if (results != null && !results.isEmpty()) {
			LatLng center = results.get(0).getLatLng();
			centerGeoMap = center.getLat() + "," + center.getLng();
		}
		
		logger.info("Input localization centered by address : " + centerGeoMap);
	}

	public void onSetPosCoord() {
		
		logger.info("onSetPosCoord : " + Double.toString(lat) + ","  + Double.toString(lng));

	    centerGeoMap =  Double.toString(lat) + ","  + Double.toString(lng);

	}


	public String getInputOneTag() {
		return inputOneTag;
	}


	public void setInputOneTag(String inputOneTag) {
		this.inputOneTag = inputOneTag;
	}


	public String getInputTags() {
		return inputTags;
	}


	public void setInputTags(String inputTags) {
		this.inputTags = inputTags;
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

	public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }
      
    public Marker getMarker() {
        return marker;
    }
	
    public Object getMarkerData() {
        return marker.getData();
    }
    
    public String getMarkerTitle() {
        return marker.getTitle();
    }
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
}
