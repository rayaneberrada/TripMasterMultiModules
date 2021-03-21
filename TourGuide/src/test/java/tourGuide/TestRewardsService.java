package tourGuide;

import static org.junit.Assert.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tourGuide.beans.Attraction;
import tourGuide.beans.Location;
import tourGuide.beans.VisitedLocation;
import tourGuide.beans.NearByAttraction;
import rewardCentral.RewardCentral;
import tourGuide.dto.VisitedAttractionDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.*;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
public class TestRewardsService {

  @Autowired GpsService gpsService;

  @Autowired CalculatorService calculatorService;

  @Autowired TripPricerService tripPricerService;

  List<Attraction> attractions = new ArrayList();

  @Before
  public void setUp() {
    Locale.setDefault(new Locale("en", "US", "WIN"));
    attractions.add(new Attraction("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
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
  public void userGetRewards() throws ExecutionException, InterruptedException {
    // GIVEN
    User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attractions.get(0), new Date()));
    /*    when(gpsService.getAllAttractions()).thenReturn(attractions);
    when(gpsService.getUserLocation(any(UUID.class))).thenReturn(new VisitedLocation(user.getUserId(), attractions.get(0), new Date()));*/
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);

    InternalTestHelper.setInternalUserNumber(0);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, tripPricerService);

    // WHEN
    tourGuideService.trackUserLocation(user);
    rewardsService.calculateRewards(user);
    HashMap<String, UserReward> userRewards = user.getUserRewards();
    tourGuideService.tracker.stopTracking();

    // THEN
    assertTrue(userRewards.size() == 1);
  }

  @Test
  public void isWithinAttractionProximity() {
    //    when(gpsService.getAllAttractions()).thenReturn(attractions);
    // GIVEN
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);

    // WHEN
    Attraction attraction = gpsService.getAllAttractions().get(0);

    // THEN
    assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
  }

  @Test
  public void nearAllAttractions() throws ExecutionException, InterruptedException {
    /*    when(gpsService.getAllAttractions()).thenReturn(attractions);
    when(calculatorService.getRewardPoints(any(VisitedAttractionDTO.class))).thenReturn(1);*/
    // GIVEN
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);
    rewardsService.setProximityBuffer(Integer.MAX_VALUE);

    InternalTestHelper.setInternalUserNumber(1);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, tripPricerService);

    rewardsService.setProximityBuffer(10000);

    // WHEN
    rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
    HashMap<String, UserReward> userRewards =
        tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
    tourGuideService.tracker.stopTracking();

    // THEN
    assertEquals(gpsService.getAllAttractions().size(), userRewards.size());
  }
}
