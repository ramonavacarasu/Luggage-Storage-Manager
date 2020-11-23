package luggage.storage.manager.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReleasedLuggage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String luggageID;
    private long duration;
    private long price;

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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
