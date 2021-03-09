package tourGuide.dto;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class StayInformationsDto {
    public String apiKey;
    public UUID attractionId;
    public int adults;
    public int children;
    public int nightsStay;
    public int rewardsPoints;

    public StayInformationsDto(String apiKey, int adults) {
        this.apiKey = apiKey;
        this.adults = adults;
    }
}
