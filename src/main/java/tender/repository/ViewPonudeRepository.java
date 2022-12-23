package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tender.domain.Ponude;
import tender.domain.ViewPonude;

/**
 * Spring Data JPA repository for the ViewPonude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewPonudeRepository extends JpaRepository<ViewPonude, Long>, JpaSpecificationExecutor<ViewPonude> {
    @Query(
        value = "SELECT ponude.*, \n" +
        "ponudjaci.naziv_ponudjaca\n" +
        "FROM\n" +
        "ponude\n" +
        "INNER JOIN ponudjaci ON ponude.sifra_ponudjaca = ponudjaci.id\n where ponude.sifra_postupka=?1",
        nativeQuery = true
    )
    List<ViewPonude> findBySifraPostupkaPonudePonudjaci(@Param("sifra_postupka") Integer sifra_postupka);

    @Query(
        value = "SELECT ponude.*, \n" +
        "        ponudjaci.naziv_ponudjaca\n" +
        "        FROM\n" +
        "        ponude\n" +
        "        INNER JOIN ponudjaci ON ponude.sifra_ponudjaca = ponudjaci.id",
        nativeQuery = true
    )
    List<ViewPonude> PonudePonudjaci();
}
