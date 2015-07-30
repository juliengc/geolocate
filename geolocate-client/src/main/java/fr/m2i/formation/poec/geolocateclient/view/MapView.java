package fr.m2i.formation.poec.geolocateclient.view;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
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
	private String centerGeoMap;

	private String title;

	private String inputOneTag;
	private String inputTags;
	
	@DecimalMin("-90.00") @DecimalMax("90.00")
	private double lat;
	
	@DecimalMin("-180.00") @DecimalMax("180.00")
	private double lng;
	
	private String address;
	
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	private double lati;
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
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lati + ", Lng:" + lngi));
	}


	public void onGeocode(GeocodeEvent event) {
		List<GeocodeResult> results = event.getResults();
		
		System.out.println("Input localization centered by address : results : " + results.toString());

		if (results != null && !results.isEmpty()) {
			LatLng center = results.get(0).getLatLng();
			centerGeoMap = center.getLat() + "," + center.getLng();
		}
		
		System.out.println("Input localization centered by address : " + centerGeoMap);
	}

	public void onSetPosCoord() {

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

}
