package luggage.storage.manager.services;

import luggage.storage.manager.entities.ReleasedLuggage;
import luggage.storage.manager.repositories.ReleasedLuggageRepository;
import org.springframework.stereotype.Service;

@Service("releasedLuggageService")
public class ReleasedLuggageServiceImpl implements ReleasedLuggageService{

    private final ReleasedLuggageRepository releasedLuggageRepository;

    public ReleasedLuggageServiceImpl(ReleasedLuggageRepository releasedLuggageRepository) {
        this.releasedLuggageRepository = releasedLuggageRepository;
    }

    @Override
    public void saveReleasedLuggage(ReleasedLuggage releasedLuggage) {
        releasedLuggageRepository.save(releasedLuggage);
    }

}
