package tourGuide;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import tourGuide.beans.Provider;
import tourGuide.beans.VisitedLocation;
import tourGuide.beans.Location;
import tourGuide.beans.Attraction;
import tourGuide.proxies.GpsProxy;
import tourGuide.service.GpsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;

@RestController
public class TourGuideController {

  @Autowired
  GpsService gpsService;

  @Autowired TourGuideService tourGuideService;

  @RequestMapping("/getAttractions")
  public List<Attraction> getGps() {
    return gpsService.getAllAttractions();
  }

  @RequestMapping("/")
  public String index() {
    return "Greetings from TourGuide!";
  }

  /**
   * Return the last location or create a location to return for a user
   *
   * @param userName
   * @return serialized last VisitedLocation
   */
  @RequestMapping("/getLocation")
  public String getLocation(@RequestParam String userName)
      throws ExecutionException, InterruptedException {
    VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
    return JsonStream.serialize(visitedLocation.location);
  }

  //  TODO: Change this method to no longer return a List of Attractions.
  //  Instead: Get the closest five tourist attractions to the user - no matter how far away they
  // are.
  //  Return a new JSON object that contains:
  // Name of Tourist attraction,
  // Tourist attractions lat/long,
  // The user's location lat/long,
  // The distance in miles between the user's location and each of the attractions.
  // The reward points for visiting each Attraction.
  //    Note: Attraction reward points can be gathered from RewardsCentral
  @RequestMapping("/getNearbyAttractions")
  public String getNearbyAttractions(@RequestParam String userName)
      throws ExecutionException, InterruptedException {
    VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
    return JsonStream.serialize(tourGuideService.getNearByAttractions(visitedLocation));
  }

  @RequestMapping("/getRewards")
  public String getRewards(@RequestParam String userName) {
    return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
  }

  @RequestMapping("/getAllCurrentLocations")
  public String getAllCurrentLocations() {
    HashMap<UUID, Location> currentUsersLocation = tourGuideService.getAllUsersLocation();
    // TODO: Get a list of every user's most recent location as JSON
    // - Note: does not use gpsUtil to query for their current location,
    //        but rather gathers the user's current location from their stored location history.
    //
    // Return object should be the just a JSON mapping of userId to Locations similar to:
    //     {
    //        "019b04a9-067a-4c76-8817-ee75088c3822": {"longitude":-48.188821,"latitude":74.84371}
    //        ...
    //     }

    return JsonStream.serialize(currentUsersLocation);
  }

  /**
   * Return the list of traveling agencies with their price
   *
   * @param userName
   * @return
   */
  @RequestMapping("/getTripDeals")
  public String getTripDeals(@RequestParam String userName) {
    List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
    return JsonStream.serialize(providers);
  }

  private User getUser(String userName) {
    return tourGuideService.getUser(userName);
  }
}
