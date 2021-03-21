package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
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
