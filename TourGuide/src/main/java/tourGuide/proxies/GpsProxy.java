package tourGuide.proxies;

import tourGuide.beans.VisitedLocation;
import org.springframework.web.bind.annotation.PathVariable;
import tourGuide.beans.Attraction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

/**
 * Feign proxy used to communicate and redirect requests to the gpsClient microservice
 */
@FeignClient("gps")
@RequestMapping(value = "/gps")
public interface GpsProxy {

    /**
     * Route to get a list of all Attractions available in the app
     *
     * @return list of Attraction
     */
    @RequestMapping(value = "/getAttractions")
    List<Attraction> attractions();

    /**
     * Route to get the current location of a user
     *
     * @param userId
     * @return VisitedLocation object representing user location
     */
    @RequestMapping(value = "/getLocation/{userId}")
    VisitedLocation getUserLocation(@PathVariable("userId") UUID userId);
}
