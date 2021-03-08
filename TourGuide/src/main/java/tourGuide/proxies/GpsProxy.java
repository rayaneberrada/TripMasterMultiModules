package tourGuide.proxies;

import tourGuide.beans.VisitedLocation;
import org.springframework.web.bind.annotation.PathVariable;
import tourGuide.beans.Attraction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@FeignClient("gps")
@RequestMapping(value = "/gps")
public interface GpsProxy {

    @GetMapping(value = "/getAttractions")
    public List<Attraction> attractions();

    @GetMapping(value = "/getLocation/{userId}")
    public VisitedLocation getUserLocation(@PathVariable("userId") UUID userId);
}