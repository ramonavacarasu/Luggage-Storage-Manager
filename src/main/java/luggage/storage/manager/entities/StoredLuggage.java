package luggage.storage.manager.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class StoredLuggage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String luggageID;
    private Instant startTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLuggageID() {
        return luggageID;
    }

    public void setLuggageID(String luggageID) {
        this.luggageID = luggageID;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
}
