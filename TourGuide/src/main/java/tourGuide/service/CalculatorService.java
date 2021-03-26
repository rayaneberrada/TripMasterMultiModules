package tourGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.beans.Attraction;
import tourGuide.dto.VisitedAttractionDTO;
import tourGuide.proxies.RewardCentralProxy;

import java.util.UUID;

@Service
public class CalculatorService {

    @Autowired
    RewardCentralProxy rewardCentralProxy;

    /**
     * Method to calculate a user reward for visiting an Attraction by calling the RewardCentralClient
     * microservice through the rewardCentralProxy
     *
     * @param visitedAttractionDTO
     * @return the amount of points earned by the user
     */
    public int getRewardPoints(VisitedAttractionDTO visitedAttractionDTO) {
        return rewardCentralProxy.getRewardPoints(visitedAttractionDTO);
    }
}
