package luggage.storage.manager.repositories;

import luggage.storage.manager.entities.ReleasedLuggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleasedLuggageRepository extends JpaRepository<ReleasedLuggage, Long> {
}
