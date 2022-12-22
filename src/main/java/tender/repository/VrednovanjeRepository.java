package tender.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tender.domain.Vrednovanje;

/**
 * Spring Data JPA repository for the Vrednovanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VrednovanjeRepository extends JpaRepository<Vrednovanje, Long>, JpaSpecificationExecutor<Vrednovanje> {}
