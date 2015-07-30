package fr.m2i.formation.poec.geolocateclient.rest;

import java.util.ArrayList;
import java.util.List;

import fr.m2i.formation.poec.geolocateclient.domain.LocatedObject;

public class RestClient {


	public List<LocatedObject> getAllObjects(double lat, double lng,
            double lat2, double lng2) {
        
           //43.606931,7.0160964, Centre Azuréen de Cancérologie
           //43.6124615,7.0161465, Mougins School
           //@43.6078453,7.0266219 stade de la valmasque
        
        List<LocatedObject> lresult = new ArrayList<LocatedObject>();
        lresult.add(new LocatedObject("Centre Azuréen de Cancérologie", "desc Centre Azuréen de Cancérologie", 43.606931, 7.0160964, 0.0));
        lresult.add(new LocatedObject("Mougins School", "desc Mougins School", 43.6124615,7.0161465, 0.0));
        lresult.add(new LocatedObject("stade de la valmasque", "Centre Azuréen de Cancérologie", 43.6078453, 7.0266219, 0.0));

        return lresult;
    }
	
}
