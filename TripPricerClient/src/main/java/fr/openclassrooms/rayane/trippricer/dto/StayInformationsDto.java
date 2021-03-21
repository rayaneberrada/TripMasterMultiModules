package fr.openclassrooms.rayane.trippricer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
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
