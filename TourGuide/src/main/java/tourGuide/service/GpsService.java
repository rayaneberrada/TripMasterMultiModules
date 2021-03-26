package tourGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.beans.Attraction;
import tourGuide.beans.VisitedLocation;
import tourGuide.proxies.GpsProxy;

import java.util.List;
import java.util.UUID;

@Service
public class GpsService {
    @Autowired GpsProxy gpsProxy;

    /**
     * Method to return all attractions available by calling the gpsClient microservice through the
     * GpsProxy class
     *
     * @return list of all attractions
     */
    public List<Attraction> getAllAttractions() {return gpsProxy.attractions();}

    /**
     * Method to return the user location by calling the gpsClient microservice through the
     * GpsProxy class
     *
     * @param id
     * @return a VisitedLocation object containing the actual user location
     */
    public VisitedLocation getUserLocation(UUID id) {return gpsProxy.getUserLocation(id);}
}
