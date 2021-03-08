package tourGuide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@FeignClient("rewardCentral")
@RequestMapping(value = "/reward")
public interface RewardCentralProxy {

    @GetMapping(value = "/AttractionReward")
    public int getRewardPoints(UUID attractionId, UUID userId);
}
