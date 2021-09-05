package stonker.stonker.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stonker.stonker.entities.Candlestick;

@Repository
public interface CandlestickRepository extends CrudRepository<Candlestick, Long> {
}
