package fr.m2i.formation.poec.geolocateclient.rest.test;

import java.util.List;

import fr.m2i.formation.poec.geolocateclient.domain.LocatedObject;
import fr.m2i.formation.poec.geolocateclient.domain.Tag;
import fr.m2i.formation.poec.geolocateclient.rest.RestClient;
import fr.m2i.formation.poec.geolocateclient.rest.exception.RestClientException;
import fr.m2i.formation.poec.geolocateclient.rest.exception.RestServiceErrorException;

public class Test {

	public static void main(String[] args) throws RestClientException, RestServiceErrorException {
		RestClient restClient = new RestClient();
		
		List<LocatedObject> lists1 = restClient.getLocatedObjectsArea(1.0, 1.0, 1.0, 1.0);
		for (LocatedObject locatedObject : lists1) {
			System.out.println(locatedObject);
		}
		
		String[] tags = {"test","tag1","tag2"};
		List<LocatedObject> lists2 = restClient.getLocatedObjectsAreaTags(1.0, 1.0, 1.0, 1.0, tags);
		for (LocatedObject locatedObject : lists2) {
			System.out.println(locatedObject);
		}
		
		List<Tag> listsTag = restClient.getTags("a");
		for (Tag tag : listsTag) {
			System.out.println(tag);
		}		
	}
}
