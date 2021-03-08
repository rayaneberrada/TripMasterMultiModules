package tourGuide.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import tourGuide.beans.Attraction;
import tourGuide.beans.Location;
import tourGuide.beans.VisitedLocation;
import tourGuide.beans.NearByAttraction;
import rewardCentral.RewardCentral;
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
  private final RewardCentral rewardsCentral;

  public RewardsService(GpsService gpsService, RewardCentral rewardCentral) {
    this.gpsService = gpsService;
    this.rewardsCentral = rewardCentral;
  }

  public void setProximityBuffer(int proximityBuffer) {
    this.proximityBuffer = proximityBuffer;
  }

  public void setDefaultProximityBuffer() {
    proximityBuffer = defaultProximityBuffer;
  }

  @Async
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
    // SOlution 1: Calculer la récompense à l'avance pour toues les attractions?
    // Solution 2: lancer une tache asynchrone qui va calculer rewards et la modifier dans
    // l'utilisateur
    for (VisitedLocation visitedLocation : userLocations) {
      for (Attraction attraction : attractions) {
        if (user.getUserRewards().values().stream()
                .filter(r -> r.attraction.attractionName.equals(attraction.attractionName))
                .count()
            == 0) {
          if (nearAttraction(visitedLocation, attraction)) {
            user.addUserReward(
                    attraction.attractionName,
                    new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
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

  public int getRewardPoints(Attraction attraction, User user) {
    return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
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
