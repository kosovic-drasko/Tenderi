package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tender.domain.Specifikacije;

/**
 * Spring Data JPA repository for the Specifikacije entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecifikacijeRepository extends JpaRepository<Specifikacije, Long>, JpaSpecificationExecutor<Specifikacije> {
    List<Specifikacije> findBySifraPostupka(@Param("sifraPostupka") Integer sifra);
}
