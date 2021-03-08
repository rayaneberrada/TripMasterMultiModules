package tourGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.beans.Attraction;
import tourGuide.proxies.RewardCentralProxy;

import java.util.UUID;

@Service
public class CalculatorService {

    @Autowired
    RewardCentralProxy rewardCentralProxy;

    public int getRewardPoints(Attraction attraction, UUID userId) {
        return rewardCentralProxy.getRewardPoints(attraction.attractionId, userId);
    }
}
