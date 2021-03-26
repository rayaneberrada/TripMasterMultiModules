package fr.openclassrooms.rayane.gps.controller;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/gps")
public class gps {

    private GpsUtil gpsUtil = new GpsUtil();

    /**
     * Route to get a list of all Attractions available in the app
     *
     * @return list of Attraction
     */
    @GetMapping(value = "/getAttractions")
    List<Attraction> attractions() {
        return gpsUtil.getAttractions();
    }

    /**
     * Route to get the current location of a user
     *
     * @param userId
     * @return VisitedLocation object representing user location
     */
    @GetMapping(value = "/getLocation/{userId}")
    VisitedLocation getUserLocation(@PathVariable UUID userId) {
        return gpsUtil.getUserLocation(userId);
    }


}
