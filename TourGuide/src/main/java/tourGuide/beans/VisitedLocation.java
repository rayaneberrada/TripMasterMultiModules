package tourGuide.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import tourGuide.beans.Location;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class VisitedLocation {
    public final UUID userId;
    public final Location location;
    public final Date timeVisited;
}
