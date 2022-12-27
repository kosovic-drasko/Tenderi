package tender.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tender.domain.HvalePonude;

/**
 * Spring Data JPA repository for the HvalePonude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HvalePonudeRepository extends JpaRepository<HvalePonude, Long> {}
