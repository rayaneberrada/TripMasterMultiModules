package tourGuide;

import static org.junit.Assert.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import tourGuide.beans.Attraction;
import tourGuide.beans.Location;
import tourGuide.beans.VisitedLocation;
import tourGuide.beans.NearByAttraction;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.GpsService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserReward;

public class TestRewardsService {
  @Before
  public void setUp() {
    Locale.setDefault(new Locale("en", "US", "WIN"));
  }

  @Test
  public void userGetRewards() throws ExecutionException, InterruptedException {
    GpsService gpsService = new GpsService();
    RewardCentral rewardCentral = new RewardCentral();
    RewardsService rewardsService = new RewardsService(gpsService, rewardCentral);

    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, rewardCentral);

    User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    Attraction attraction = gpsService.getAllAttractions().get(0);
    user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction, new Date()));
    tourGuideService.trackUserLocation(user);
    rewardsService.calculateRewards(user);
    HashMap<String, UserReward> userRewards = user.getUserRewards();
    tourGuideService.tracker.stopTracking();
    assertTrue(userRewards.size() == 1);
  }

  @Test
  public void isWithinAttractionProximity() {
    GpsService gpsService = new GpsService();
    RewardsService rewardsService = new RewardsService(gpsService, new RewardCentral());
    Attraction attraction = gpsService.getAllAttractions().get(0);
    assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
  }

  @Test
  public void nearAllAttractions() throws ExecutionException, InterruptedException {
    GpsService gpsService = new GpsService();
    RewardCentral rewardCentral = new RewardCentral();
    RewardsService rewardsService = new RewardsService(gpsService, rewardCentral);
    rewardsService.setProximityBuffer(Integer.MAX_VALUE);

    InternalTestHelper.setInternalUserNumber(1);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, rewardCentral);

    rewardsService.setProximityBuffer(10000);
    rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
    HashMap<String, UserReward> userRewards =
        tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
    tourGuideService.tracker.stopTracking();

    assertEquals(gpsService.getAllAttractions().size(), userRewards.size());
  }
}
