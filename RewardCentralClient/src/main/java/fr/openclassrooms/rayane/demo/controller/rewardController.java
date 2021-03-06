package fr.openclassrooms.rayane.demo.controller;

import fr.openclassrooms.rayane.demo.dto.VisitedAttractionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rewardCentral.RewardCentral;

import java.util.UUID;

@RestController
@RequestMapping(value = "/reward")
public class rewardController {

    private RewardCentral rewardCentral = new RewardCentral();

    /**
     * Route to calculate the reward for an attraction visited by a user
     *
     * @param visitedAttractionDTO
     * @return int representing the amount of points earned by the user
     */
    @RequestMapping(value = "/AttractionReward")
    public int getRewardPoints(VisitedAttractionDTO visitedAttractionDTO) {
        return rewardCentral.getAttractionRewardPoints(visitedAttractionDTO.attractionId, visitedAttractionDTO.userId);
    }
}
