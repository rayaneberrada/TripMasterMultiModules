package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import rewardCentral.RewardCentral;
import tourGuide.beans.Attraction;
import tourGuide.beans.Location;
import tourGuide.beans.VisitedLocation;
import tourGuide.beans.NearByAttraction;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.GpsService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tripPricer.Provider;

public class TestTourGuideService {
  @Before
  public void setUp() {
    Locale.setDefault(new Locale("en", "US", "WIN"));
  }

  @Test
  public void getUserLocation() throws ExecutionException, InterruptedException {
    GpsService gpsService = new GpsService();
    RewardsService rewardsService = new RewardsService(gpsService, new RewardCentral());
    RewardCentral rewardCentral = new RewardCentral();
    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, rewardCentral);

    User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);
    tourGuideService.tracker.stopTracking();
    assertTrue(visitedLocation.userId.equals(user.getUserId()));
  }

  @Test
  public void addUser() {
    GpsService gpsService = new GpsService();
    RewardsService rewardsService = new RewardsService(gpsService, new RewardCentral());
    RewardCentral rewardCentral = new RewardCentral();
    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, rewardCentral);

    User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

    tourGuideService.addUser(user);
    tourGuideService.addUser(user2);

    User retrivedUser = tourGuideService.getUser(user.getUserName());
    User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

    tourGuideService.tracker.stopTracking();

    assertEquals(user, retrivedUser);
    assertEquals(user2, retrivedUser2);
  }

  @Test
  public void getAllUsers() {
    GpsService gpsService = new GpsService();
    RewardCentral rewardCentral = new RewardCentral();
    RewardsService rewardsService = new RewardsService(gpsService, rewardCentral);
    InternalTestHelper.setInternalUserNumber(100);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, rewardCentral);

    User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

    tourGuideService.addUser(user);
    tourGuideService.addUser(user2);

    List<User> allUsers = tourGuideService.getAllUsers();

    tourGuideService.tracker.stopTracking();

    assertTrue(allUsers.contains(user));
    assertTrue(allUsers.contains(user2));
  }

  @Test
  public void trackUser() throws ExecutionException, InterruptedException {
    GpsService gpsService = new GpsService();
    RewardCentral rewardCentral = new RewardCentral();
    RewardsService rewardsService = new RewardsService(gpsService, rewardCentral);
    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, rewardCentral);

    User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

    tourGuideService.tracker.stopTracking();

    assertEquals(user.getUserId(), visitedLocation.userId);
  }

  @Test
  public void getNearbyAttractions() throws ExecutionException, InterruptedException {
    GpsService gpsService = new GpsService();
    RewardCentral rewardCentral = new RewardCentral();
    RewardsService rewardsService = new RewardsService(gpsService, rewardCentral);
    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, rewardCentral);

    User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

    List<NearByAttraction> attractions = tourGuideService.getNearByAttractions(visitedLocation);

    tourGuideService.tracker.stopTracking();

    assertEquals(5, attractions.size());
  }

  @Test
  public void getTripDeals() {
    GpsService gpsService = new GpsService();
    RewardCentral rewardCentral = new RewardCentral();
    RewardsService rewardsService = new RewardsService(gpsService, rewardCentral);
    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, rewardCentral);

    User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

    List<Provider> providers = tourGuideService.getTripDeals(user);

    tourGuideService.tracker.stopTracking();

    assertEquals(5, providers.size());
  }
}
