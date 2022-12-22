package tender.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tender.domain.ViewPrvorangirani;

/**
 * Spring Data JPA repository for the ViewPrvorangirani entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewPrvorangiraniRepository extends JpaRepository<ViewPrvorangirani, Long>, JpaSpecificationExecutor<ViewPrvorangirani> {}
