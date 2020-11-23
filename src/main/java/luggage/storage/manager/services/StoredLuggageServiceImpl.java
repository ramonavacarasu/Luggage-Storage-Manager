package luggage.storage.manager.services;

import luggage.storage.manager.entities.StoredLuggage;
import luggage.storage.manager.repositories.StoredLuggageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("storedLuggageService")
public class StoredLuggageServiceImpl implements StoredLuggageService {

    private final StoredLuggageRepository storedLuggageRepository;

    public StoredLuggageServiceImpl(StoredLuggageRepository storedLuggageRepository) {
        this.storedLuggageRepository = storedLuggageRepository;
    }

    @Override
    public StoredLuggage findByLuggageID(String luggageID) {
        return storedLuggageRepository.findByLuggageID(luggageID);
    }

    @Override
    public StoredLuggage findByID(long id) {
        return storedLuggageRepository.findById(id);
    }

    @Override
    public void saveStoredLuggage(StoredLuggage storedLuggage) {
        storedLuggageRepository.save(storedLuggage);
    }

    public long count() {
        return storedLuggageRepository.count();
    }

    @Override
    public List<String> luggageIDList() {
        List<String> idList = new ArrayList<>();
        try {
            for (StoredLuggage storedLuggage : storedLuggageRepository.findAll()) {
                idList.add(storedLuggage.getLuggageID());
            }
        } catch (NullPointerException ignored) { }
        return idList;
    }

    @Override
    public void deleteStoredLuggage(String code) {
        try {
            storedLuggageRepository.delete(storedLuggageRepository.findByLuggageID(code));
        } catch (NullPointerException ignored) { }
    }

}
