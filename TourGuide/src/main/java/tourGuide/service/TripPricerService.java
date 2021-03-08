package tourGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.proxies.TripPricerProxy;
import tripPricer.Provider;

import java.util.List;
import java.util.UUID;

@Service
public class TripPricerService {
    @Autowired
    TripPricerProxy tripPricerProxy;

    public List<Provider> getProvidersPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay, int rewardsPoints){
        return tripPricerProxy.getProvidersPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
    }

    public String getProviderName(String apiKey, int adults){
        return tripPricerProxy.getProviderName(apiKey, adults);
    }

}
