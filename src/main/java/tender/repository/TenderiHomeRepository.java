package tender.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tender.domain.TenderiHome;

/**
 * Spring Data JPA repository for the TenderiHome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenderiHomeRepository extends JpaRepository<TenderiHome, Long> {}
