package fr.m2i.formation.poec.geolocateclient.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import fr.m2i.formation.poec.geolocateclient.domain.Address;
import fr.m2i.formation.poec.geolocateclient.domain.LocatedObject;
import fr.m2i.formation.poec.geolocateclient.domain.Tag;
import fr.m2i.formation.poec.geolocateclient.rest.exception.RestClientException;
import fr.m2i.formation.poec.geolocateclient.rest.exception.RestServiceErrorException;
import fr.m2i.formation.poec.geolocateclient.view.MapView;

public class RestClient {
	
	private static final Logger logger = Logger.getLogger(RestClient.class
			.getName());
	
	public List<LocatedObject> getLocatedObjectsArea(Double latitude1, Double longitude1, Double latitude2, Double longitude2) throws RestClientException, RestServiceErrorException {
		String urlString = "http://localhost:8080/geolocate/area/"
								+ latitude1 + "/" + longitude1 +"/x/"
								+ latitude2 + "/" + longitude2;
		try {
			
			logger.info("RestClient Bound " + latitude1
					+ ", " + longitude1
					+ ", " + latitude2
					+ ", " + longitude2
					);
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			logger.info("************************* RestClient Reception reponse ");
			
			if (conn.getResponseCode() != 200) {
				throw new RestServiceErrorException("Failed \n" +
													"HTTP error code : " + conn.getResponseCode() + "\n" +
													"HTTP error message : " + conn.getResponseMessage());
			}
			logger.info("************************* RestClient Etude reponse ");
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			StringBuilder builder = new StringBuilder();
			String output = "";
			while ((output = br.readLine()) != null) {
			    builder.append(output);
			}
			
			conn.disconnect();
			
			logger.info("************************* RestClient JSON ");
			List<LocatedObject> listLocObj = new ArrayList<LocatedObject>();
			JSONObject jsonObj = new JSONObject(builder.toString());
			
			
			int size = jsonObj.getInt("size");
			
			logger.info("************************* RestClient JSON Object size "+size);
			
			JSONArray jsonArr = jsonObj.getJSONArray("content");
			jsonArr.length();
			for (int i = 0; i < jsonArr.length()/*size*/; i++) {
				logger.info("************************* RestClient JSON Object "+i+" -> "+jsonArr.get(i).toString());
				listLocObj.add(getLocatedObject(jsonArr.get(i).toString()));
			}
			logger.info("************************* RestClient JSON Object content ");
			
			return listLocObj;

		} catch (JSONException e) {
			throw new RestClientException("Something is wrong with the returned json", e);
		} catch (MalformedURLException e) {
			throw new RestClientException("The URL " + urlString +  " is not correct", e);
		} catch (IOException e) {
			throw new RestClientException("Service is not reachable", e);
		}
	}
	
	public List<LocatedObject> getLocatedObjectsAreaTags(Double latitude1, Double longitude1, Double latitude2, Double longitude2, String[] tags) throws RestClientException, RestServiceErrorException {
		String urlString = "http://localhost:8080/geolocate/area/"
								+ latitude1 + "/" + longitude1 +"/x/"
								+ latitude2 + "/" + longitude2 + "/filter/" + String.join(",", tags);
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RestServiceErrorException("Failed \n" +
													"HTTP error code : " + conn.getResponseCode() + "\n" +
													"HTTP error message : " + conn.getResponseMessage());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			StringBuilder builder = new StringBuilder();
			String output = "";
			while ((output = br.readLine()) != null) {
			    builder.append(output);
			}
			
			conn.disconnect();
			
			List<LocatedObject> listLocObj = new ArrayList<LocatedObject>();
			JSONObject jsonObj = new JSONObject(builder.toString());
			
			int size = jsonObj.getInt("size");
			
			JSONArray jsonArr = jsonObj.getJSONArray("content");
			for (int i = 0; i < size; i++) {
				listLocObj.add(getLocatedObject(jsonArr.get(i).toString()));
			}
			
			return listLocObj;

		} catch (JSONException e) {
			throw new RestClientException("Something is wrong with the returned json", e);
		} catch (MalformedURLException e) {
			throw new RestClientException("The URL " + urlString +  " is not correct", e);
		} catch (IOException e) {
			throw new RestClientException("Service is not reachable", e);
		}
	}
	
	private LocatedObject getLocatedObject(String urlStr) throws RestClientException, RestServiceErrorException {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RestServiceErrorException("Failed \n" +
													"HTTP error code : " + conn.getResponseCode() + "\n" +
													"HTTP error message : " + conn.getResponseMessage());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			StringBuilder builder = new StringBuilder();
			String output = "";
			while ((output = br.readLine()) != null) {
			    builder.append(output);
			}
			
			conn.disconnect();
			
			LocatedObject newLoc = new LocatedObject();
			JSONObject jsonObj = new JSONObject(builder.toString());
			logger.info("RestClient ********* START");
			newLoc.setName(jsonObj.getString("name"));
			newLoc.setDescription(jsonObj.getString("description"));
			newLoc.setLatitude(jsonObj.getDouble("latitude"));
			newLoc.setLongitude(jsonObj.getDouble("longitude"));
			if (jsonObj.has("altitude")) {
				newLoc.setAltitude(jsonObj.getDouble("altitude"));
			}
			newLoc.setUuid(jsonObj.getString("uuid"));
			
			try{
				logger.info("RestClient ********* START ADDRESS");
				if ( jsonObj.getString("address")!=null && !jsonObj.getString("address").isEmpty() ){
				
					newLoc.setAddresses(getAddress(jsonObj.getString("address")));
				}
				
			}catch(JSONException exc){
				logger.info("No adresses in JSON Object");
			}
			
			logger.info("RestClient ********* START TAGS");
			JSONArray jsonArr = jsonObj.getJSONArray("tags");
			List<Tag> listTags = new ArrayList<Tag>();
			for (int i = 0; i < jsonArr.length(); i++) {
				listTags.add(new Tag(jsonArr.get(i).toString()));
			}
			newLoc.setTags(new HashSet<Tag>(listTags));
			
			logger.info("RestClient ********* END");
			return newLoc;
			
		} catch (JSONException e) {
			throw new RestClientException("Something is wrong with the returned json", e);
		} catch (MalformedURLException e) {
			throw new RestClientException("The URL " + urlStr +  " is not correct", e);
		} catch (IOException e) {
			throw new RestClientException("Service is not reachable", e);
		}
	}
	
	private Address getAddress(String urlStr) throws RestClientException, RestServiceErrorException {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RestServiceErrorException("Failed \n" +
													"HTTP error code : " + conn.getResponseCode() + "\n" +
													"HTTP error message : " + conn.getResponseMessage());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			StringBuilder builder = new StringBuilder();
			String output = "";
			while ((output = br.readLine()) != null) {
			    builder.append(output);
			}
			
			conn.disconnect();
			
			Address newAdd = new Address();
			JSONObject jsonObj = new JSONObject(builder.toString());
			
			if (jsonObj.has("street")) {
				newAdd.setStreet(jsonObj.getString("street"));
			}
			if (jsonObj.has("zipcode")) {
				newAdd.setZipcode(jsonObj.getString("zipcode"));
			}
			if (jsonObj.has("city")) {
				newAdd.setCity(jsonObj.getString("city"));
			}
			if (jsonObj.has("state")) {
				newAdd.setState(jsonObj.getString("state"));
			}
			
			newAdd.setCountry(jsonObj.getString("country"));
			newAdd.setUuid(jsonObj.getString("uuid"));
			
			return newAdd;
			
		} catch (JSONException e) {
			throw new RestClientException("Something is wrong with the returned json", e);
		} catch (MalformedURLException e) {
			throw new RestClientException("The URL " + urlStr +  " is not correct", e);
		} catch (IOException e) {
			throw new RestClientException("Service is not reachable", e);
		}
	}
}