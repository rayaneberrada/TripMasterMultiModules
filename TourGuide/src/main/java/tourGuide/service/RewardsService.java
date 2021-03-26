package tourGuide.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import tourGuide.beans.Attraction;
import tourGuide.beans.Location;
import tourGuide.beans.VisitedLocation;
import tourGuide.beans.NearByAttraction;
import rewardCentral.RewardCentral;
import tourGuide.dto.VisitedAttractionDTO;
import tourGuide.proxies.RewardCentralProxy;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@Service
public class RewardsService {
  private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

  // proximity in miles
  private int defaultProximityBuffer = 10;
  private int proximityBuffer = defaultProximityBuffer;
  private int attractionProximityRange = 200;
  private final GpsService gpsService;
  private CalculatorService calulatorService;

  public RewardsService(GpsService gpsService, CalculatorService calulatorService) {

    this.gpsService = gpsService;
    this.calulatorService = calulatorService;
  }

  /**
   * Method to define when we consider an Attraction to be close
   *
   * @param proximityBuffer
   */
  public void setProximityBuffer(int proximityBuffer) {
    this.proximityBuffer = proximityBuffer;
  }

  public void setDefaultProximityBuffer() {
    proximityBuffer = defaultProximityBuffer;
  }

  /**
   * Method to calculate the rewards of a user
   *
   * @param user
   * @throws ExecutionException
   * @throws InterruptedException
   */
  public void calculateRewards(User user) throws ExecutionException, InterruptedException {
    List<VisitedLocation> userLocations = new CopyOnWriteArrayList<>(user.getVisitedLocations());
    // un array ou CopyOnWriteArrayList
    List<Attraction> attractions = gpsService.getAllAttractions();

    // Pour chaque endroit visité, on vérfie pour toutes les attractions si l'utilisateur n'a pas
    // dans sa liste de récompense déjà recu une récompense pour une attraction ayant ce nom
    // Si ce n'est pas le cas, on vérifie si la localisation visitée est proche de l'attraction en
    // utilisant nearAttraction()
    // Si c'est le cas, on ajoute une récompense à la lsite de l'utilisateur qu'on calcule en
    // utilisant RewardCentral
    for (VisitedLocation visitedLocation : userLocations) {
      for (Attraction attraction : attractions) {
        if (user.getUserRewards().values().stream()
                .filter(r -> r.attraction.attractionName.equals(attraction.attractionName))
                .count()
            == 0) {
          if (nearAttraction(visitedLocation, attraction)) {
            user.addUserReward(
                    attraction.attractionName,
                    new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user.getUserId())));
          }
        }
      }
    }
  }

  public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
    return !(getDistance(attraction, location) > attractionProximityRange);
  }

  private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
    return !(getDistance(attraction, visitedLocation.location) > proximityBuffer);
  }

  public int getRewardPoints(Attraction attraction, UUID userId) {
    return calulatorService.getRewardPoints(new VisitedAttractionDTO(attraction.attractionId, userId));
  }

  public double getDistance(Location loc1, Location loc2) {
    double lat1 = Math.toRadians(loc1.latitude);
    double lon1 = Math.toRadians(loc1.longitude);
    double lat2 = Math.toRadians(loc2.latitude);
    double lon2 = Math.toRadians(loc2.longitude);

    double angle =
        Math.acos(
            Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

    double nauticalMiles = 60 * Math.toDegrees(angle);
    double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    return statuteMiles;
  }
}
