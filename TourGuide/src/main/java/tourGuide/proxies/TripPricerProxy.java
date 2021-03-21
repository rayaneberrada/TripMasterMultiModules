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

@FeignClient("tripPricer")
@RequestMapping(value = "/trip")
public interface TripPricerProxy {

    @RequestMapping(value = "/ProvidersPrice")
    @Headers("Content-Type: application/json")
    public List<Provider> getProvidersPrice(@RequestBody StayInformationsDto stayInformationsDto);

    @RequestMapping(value = "/ProviderName")
    public String getProviderName(StayInformationsDto stayInformationsDto);
}
