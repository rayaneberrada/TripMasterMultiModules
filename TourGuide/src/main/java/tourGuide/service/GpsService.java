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

    public List<Attraction> getAllAttractions() {return gpsProxy.attractions();}

    public VisitedLocation getUserLocation(UUID id) {return gpsProxy.getUserLocation(id);}
}
