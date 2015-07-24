package fr.m2i.formation.poec.geolocate.rest;

import java.util.List;

import fr.m2i.formation.poec.geolocate.domain.Address;
import fr.m2i.formation.poec.geolocate.domain.LocatedObject;
import fr.m2i.formation.poec.geolocate.domain.Tag;


public interface BDDService {


        Integer getLocationCount();

        List<LocatedObject> getLocatedObjects(int start, int step);
        List<LocatedObject> getLocatedObjects(Tag tag, int start, int step);


        LocatedObject[] getLocatedObjects(Double latitude, Double longitude);

        LocatedObject[] getLocatedObjects(Double latitude, Double longitude,
                        Double altitude);

        LocatedObject getLocatedObject(String uuid);
        Tag getTag(String name);
        Address[] getAddresses(int start, int step);
        Address getAddress(String uuid);

        void insert(LocatedObject lo);


}

