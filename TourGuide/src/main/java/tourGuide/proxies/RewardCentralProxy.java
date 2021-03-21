package tourGuide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tourGuide.dto.VisitedAttractionDTO;

import java.util.UUID;

@FeignClient("rewardCentral")
@RequestMapping(value = "/reward")
public interface RewardCentralProxy {

    @RequestMapping(value = "/AttractionReward")
    public int getRewardPoints(VisitedAttractionDTO visitedAttractionDTO);
}
