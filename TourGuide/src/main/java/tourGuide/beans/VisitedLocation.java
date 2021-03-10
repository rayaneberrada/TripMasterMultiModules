package tourGuide.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import tourGuide.beans.Location;

import java.util.Date;
import java.util.UUID;

@ToString
public class VisitedLocation {
    public final UUID userId;
    public final Location location;
    public final Date timeVisited;

    public VisitedLocation(UUID userId, Location location, Date timeVisited) {
        this.userId = userId;
        this.location = location;
        this.timeVisited = timeVisited;
    }
}
