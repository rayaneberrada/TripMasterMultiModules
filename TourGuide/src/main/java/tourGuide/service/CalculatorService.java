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

    public int getRewardPoints(VisitedAttractionDTO visitedAttractionDTO) {
        return rewardCentralProxy.getRewardPoints(visitedAttractionDTO);
    }
}
