package fr.openclassrooms.rayane.trippricer.controller;

import fr.openclassrooms.rayane.trippricer.dto.StayInformationsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/trip")
public class TripPricerController {
    private TripPricer tripPricer = new TripPricer();

    @RequestMapping(value = "/ProvidersPrice")
    public List<Provider> getProvidersPrice(@RequestBody StayInformationsDto stayInformations) {
        return tripPricer.getPrice(stayInformations.apiKey, stayInformations.attractionId, stayInformations.adults, stayInformations.children, stayInformations.nightsStay, stayInformations.rewardsPoints);
    }

    @RequestMapping(value = "/ProviderName")
    public String getProviderName(@RequestBody StayInformationsDto stayInformations) {
        return tripPricer.getProviderName(stayInformations.apiKey, stayInformations.adults);
    }
}
