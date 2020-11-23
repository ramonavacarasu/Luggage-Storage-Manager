package luggage.storage.manager.services;

import luggage.storage.manager.entities.StoredLuggage;

import java.util.List;

public interface StoredLuggageService {

    StoredLuggage findByLuggageID(String luggageID);

    StoredLuggage findByID(long id);

    List<String> luggageIDList();

    void saveStoredLuggage(StoredLuggage storedLuggage);

    void deleteStoredLuggage(String code);

}
