package tourGuide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tripPricer.Provider;

import java.util.List;
import java.util.UUID;

@FeignClient("tripPricer")
@RequestMapping(value = "/trip")
public interface TripPricerProxy {

    @GetMapping(value = "/ProvidersPrice")
    public List<Provider> getProvidersPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay, int rewardsPoints);

    @GetMapping(value = "/ProviderName")
    public String getProviderName(String apiKey, int adults);
}
