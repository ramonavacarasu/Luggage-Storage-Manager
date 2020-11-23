package luggage.storage.manager.repositories;

import luggage.storage.manager.entities.StoredLuggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredLuggageRepository extends JpaRepository<StoredLuggage, Long>  {

    StoredLuggage findByLuggageID(String luggageID);

    StoredLuggage findById(long id);

}
