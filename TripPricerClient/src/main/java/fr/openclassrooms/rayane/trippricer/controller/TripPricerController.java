package fr.openclassrooms.rayane.trippricer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/trip")
public class TripPricerController {
    private TripPricer tripPricer;

    @GetMapping(value = "/ProvidersPrice")
    public List<Provider> getProvidersPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay, int rewardsPoints) {
        return tripPricer.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
    }

    @GetMapping(value = "/ProviderName")
    public String getProviderName(String apiKey, int adults) {
        return tripPricer.getProviderName(apiKey, adults);
    }
}
