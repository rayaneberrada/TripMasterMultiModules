package tourGuide.proxies;

import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tourGuide.beans.Provider;
import tourGuide.dto.StayInformationsDto;

import java.util.List;
import java.util.UUID;

/**
 * Feign proxy used to communicate and redirect requests to the TripPricerClient microservice
 */
@FeignClient("tripPricer")
@RequestMapping(value = "/trip")
public interface TripPricerProxy {

    /**
     * Route calculating the prices of the travel agencies depending of the staying options chosen by the user
     *
     * @param stayInformationsDto
     * @return list of Provider with their prices
     */
    @RequestMapping(value = "/ProvidersPrice")
    @Headers("Content-Type: application/json")
    List<Provider> getProvidersPrice(@RequestBody StayInformationsDto stayInformationsDto);

    /**
     *  Route to get the name of a provider
     *
     * @param stayInformationsDto
     * @return a random provider's name
     */
    @RequestMapping(value = "/ProviderName")
    String getProviderName(StayInformationsDto stayInformationsDto);
}
