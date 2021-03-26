package tourGuide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tourGuide.dto.VisitedAttractionDTO;

import java.util.UUID;

/**
 * Feign proxy used to communicate and redirect requests to the RewardCentralCLient microservice
 */
@FeignClient("rewardCentral")
@RequestMapping(value = "/reward")
public interface RewardCentralProxy {

    /**
     * Route to calculate the reward for an attraction visited by a user
     *
     * @param visitedAttractionDTO
     * @return int representing the amount of points earned by the user
     */
    @RequestMapping(value = "/AttractionReward")
    int getRewardPoints(VisitedAttractionDTO visitedAttractionDTO);
}
