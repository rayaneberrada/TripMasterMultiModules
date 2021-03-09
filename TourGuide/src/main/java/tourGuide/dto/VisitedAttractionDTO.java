package tourGuide.dto;

import lombok.AllArgsConstructor;
import tourGuide.beans.Attraction;
import tourGuide.beans.Location;

import java.util.UUID;

@AllArgsConstructor
public class VisitedAttractionDTO {
    public final UUID userId;
    public final UUID attractionId;
}
