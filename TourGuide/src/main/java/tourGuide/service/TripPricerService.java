package tourGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.beans.Provider;
import tourGuide.dto.StayInformationsDto;
import tourGuide.proxies.TripPricerProxy;

import java.util.List;
import java.util.UUID;

@Service
public class TripPricerService {
    @Autowired
    TripPricerProxy tripPricerProxy;

    public List<Provider> getProvidersPrice(StayInformationsDto stayInformationsDto){
        return tripPricerProxy.getProvidersPrice(stayInformationsDto);
    }

    public String getProviderName(StayInformationsDto stayInformationsDto){
        return tripPricerProxy.getProviderName(stayInformationsDto);
    }

}
