package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import rewardCentral.RewardCentral;
import tourGuide.beans.Attraction;
import tourGuide.beans.Location;
import tourGuide.beans.VisitedLocation;
import tourGuide.beans.NearByAttraction;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.CalculatorService;
import tourGuide.service.GpsService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tripPricer.Provider;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class TestTourGuideService {

  @Mock
  GpsService gpsService;

  @Mock
  CalculatorService calculatorService;

  User user;
  List<Attraction> attractions = new ArrayList();

  @Before
  public void setUp() {
    user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    Locale.setDefault(new Locale("en", "US", "WIN"));
    attractions.add(new Attraction("Flatiron Building", "New York City", "NY", 40.741112D, -73.989723D));
    attractions.add(new Attraction("Fallingwater", "Mill Run", "PA", 39.906113D, -79.468056D));
    attractions.add(new Attraction("Union Station", "Washington D.C.", "CA", 38.897095D, -77.006332D));
    attractions.add(new Attraction("Roger Dean Stadium", "Jupiter", "FL", 26.890959D, -80.116577D));
    attractions.add(new Attraction("Texas Memorial Stadium", "Austin", "TX", 30.283682D, -97.732536D));
    attractions.add(new Attraction("Bryant-Denny Stadium", "Tuscaloosa", "AL", 33.208973D, -87.550438D));
    attractions.add(new Attraction("Tiger Stadium", "Baton Rouge", "LA", 30.412035D, -91.183815D));
    attractions.add(new Attraction("Neyland Stadium", "Knoxville", "TN", 35.955013D, -83.925011D));
    attractions.add(new Attraction("Kyle Field", "College Station", "TX", 30.61025D, -96.339844D));
    attractions.add(new Attraction("San Diego Zoo", "San Diego", "CA", 32.735317D, -117.149048D));
    attractions.add(new Attraction("Zoo Tampa at Lowry Park", "Tampa", "FL", 28.012804D, -82.469269D));
    attractions.add(new Attraction("Franklin Park Zoo", "Boston", "MA", 42.302601D, -71.086731D));
    attractions.add(new Attraction("El Paso Zoo", "El Paso", "TX", 31.769125D, -106.44487D));
    attractions.add(new Attraction("Kansas City Zoo", "Kansas City", "MO", 39.007504D, -94.529625D));
    attractions.add(new Attraction("Bronx Zoo", "Bronx", "NY", 40.852905D, -73.872971D));
    attractions.add(new Attraction("Cinderella Castle", "Orlando", "FL", 28.419411D, -81.5812D));
  }

  @Test
  public void getUserLocation() throws ExecutionException, InterruptedException {
    // GIVEN
    when(gpsService.getUserLocation(any(UUID.class))).thenReturn(new VisitedLocation(user.getUserId(), attractions.get(0), new Date()));
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);
    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService);

    // WHEN
    VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);
    tourGuideService.tracker.stopTracking();

    // THEN
    assertTrue(visitedLocation.userId.equals(user.getUserId()));
  }

  @Test
  public void addUser() {
    // GIVEN
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);
    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService);

    User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

    tourGuideService.addUser(user);
    tourGuideService.addUser(user2);

    // WHEN
    User retrivedUser = tourGuideService.getUser(user.getUserName());
    User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

    tourGuideService.tracker.stopTracking();

    // THEN
    assertEquals(user, retrivedUser);
    assertEquals(user2, retrivedUser2);
  }

  @Test
  public void getAllUsers() {
    // GIVEN
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);
    InternalTestHelper.setInternalUserNumber(100);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService);

    User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

    // WHEN
    tourGuideService.addUser(user);
    tourGuideService.addUser(user2);

    List<User> allUsers = tourGuideService.getAllUsers();

    tourGuideService.tracker.stopTracking();

    // THEN
    assertTrue(allUsers.contains(user));
    assertTrue(allUsers.contains(user2));
  }

  @Test
  public void trackUser() throws ExecutionException, InterruptedException {
    // GIVEN
    when(gpsService.getUserLocation(any(UUID.class))).thenReturn(new VisitedLocation(user.getUserId(), attractions.get(0), new Date()));
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);
    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService);

    // WHEN
    VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

    tourGuideService.tracker.stopTracking();

    // THEN
    assertEquals(user.getUserId(), visitedLocation.userId);
  }

  @Test
  public void getNearbyAttractions() throws ExecutionException, InterruptedException {
    // GIVEN
    when(gpsService.getAllAttractions()).thenReturn(attractions);
    when(gpsService.getUserLocation(any(UUID.class))).thenReturn(new VisitedLocation(user.getUserId(), attractions.get(0), new Date()));
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);
    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService);

    // WHEN
    VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);
    List<NearByAttraction> attractions = tourGuideService.getNearByAttractions(visitedLocation);

    tourGuideService.tracker.stopTracking();

    // THEN
    assertEquals(5, attractions.size());
  }

  @Test
  public void getTripDeals() {
    // GIVEN
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);
    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService);

    // WHEN
    List<Provider> providers = tourGuideService.getTripDeals(user);

    tourGuideService.tracker.stopTracking();

    // THEN
    assertEquals(5, providers.size());
  }
}
