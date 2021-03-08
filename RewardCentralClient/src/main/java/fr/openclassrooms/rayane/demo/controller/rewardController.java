package fr.openclassrooms.rayane.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rewardCentral.RewardCentral;

import java.util.UUID;

@RestController
@RequestMapping(value = "/reward")
public class rewardController {

    private RewardCentral rewardCentral;

    @GetMapping(value = "/AttractionReward")
    public int getRewardPoints(UUID attractionId, UUID userId) {
        return rewardCentral.getAttractionRewardPoints(attractionId, userId);
    }
}
