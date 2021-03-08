package tourGuide;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import tourGuide.beans.Attraction;
import tourGuide.beans.Location;
import tourGuide.beans.VisitedLocation;
import tourGuide.beans.NearByAttraction;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.proxies.RewardCentralProxy;
import tourGuide.service.*;
import tourGuide.user.User;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class TestPerformance {

  @Mock GpsService gpsService;

  @Mock CalculatorService calculatorService;

  @Mock TripPricerService tripPricerService;

  List<Attraction> attractions = new ArrayList();

  @Before
  public void setUp() {
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

  /*
   * A note on performance improvements:
   *
   *     The number of users generated for the high volume tests can be easily adjusted via this method:
   *
   *     		InternalTestHelper.setInternalUserNumber(100000);
   *
   *
   *     These tests can be modified to suit new solutions, just as long as the performance metrics
   *     at the end of the tests remains consistent.
   *
   *     These are performance metrics that we are trying to hit:
   *
   *     highVolumeTrackLocation: 100,000 users within 15 minutes:
   *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
   *
   *     highVolumeGetRewards: 100,000 users within 20 minutes:
   *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
   */

  @Test
  public void highVolumeTrackLocation() throws ExecutionException, InterruptedException {
    when(gpsService.getAllAttractions()).thenReturn(attractions);
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);
    // Users should be incremented up to 100,000, and test finishes within 15 minutes
    InternalTestHelper.setInternalUserNumber(100);
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, tripPricerService);

    List<User> allUsers = new ArrayList<>();
    List<CompletableFuture> completableFutureList = new ArrayList<>();
    allUsers = tourGuideService.getAllUsers();

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    for (User user : allUsers) {
      CompletableFuture completableFuture =
          CompletableFuture.runAsync(
              () -> {
                try {
                  tourGuideService.trackUserLocation(user);
                } catch (ExecutionException | InterruptedException e) {
                  e.printStackTrace();
                }
              },
              Executors.newFixedThreadPool(20));
      completableFutureList.add(completableFuture);
    }
    CompletableFuture.allOf(
            completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]))
        .join();
    stopWatch.stop();
    tourGuideService.tracker.stopTracking();

    System.out.println(
        "highVolumeTrackLocation: Time Elapsed: "
            + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime())
            + " seconds.");
    assertTrue(
        TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
  }

  // deux options de solutions de l'erreur possible
  @Test
  @Ignore
  public void highVolumeGetRewards() {
    when(gpsService.getAllAttractions()).thenReturn(attractions);
    RewardCentral rewardCentral = new RewardCentral();
    RewardsService rewardsService = new RewardsService(gpsService, calculatorService);

    // Users should be incremented up to 100,000, and test finishes within 20 minutes
    InternalTestHelper.setInternalUserNumber(1);
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    TourGuideService tourGuideService =
        new TourGuideService(gpsService, rewardsService, tripPricerService);

    Attraction attraction = attractions.get(0);
    List<User> allUsers = new ArrayList<>();
    List<CompletableFuture> completableFutureList = new ArrayList<>();
    allUsers = tourGuideService.getAllUsers();
    allUsers.forEach( u -> System.out.println(u));
    System.out.println(allUsers.get(0).getVisitedLocations().isEmpty());
    System.out.println("Taille: " + allUsers.get(0).getVisitedLocations().size());
    allUsers.forEach(
        u -> {
          CompletableFuture completableFuture =
              CompletableFuture.runAsync(
                  () -> {
                    try {
                      rewardsService.calculateRewards(u);
                    } catch (ExecutionException | InterruptedException e) {
                      e.printStackTrace();
                    }
                  },
                  Executors.newFixedThreadPool(20));
          completableFutureList.add(completableFuture);
        });
    System.out.println("Taille: " + allUsers.get(0).getVisitedLocations().size());

    CompletableFuture.allOf(
            completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]))
        .join();

    for (User user : allUsers) {
      assertTrue(user.getUserRewards().size() > 0);
    }
    stopWatch.stop();
    tourGuideService.tracker.stopTracking();

    System.out.println(
        "highVolumeGetRewards: Time Elapsed: "
            + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime())
            + " seconds.");
    assertTrue(
        TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
  }
}
