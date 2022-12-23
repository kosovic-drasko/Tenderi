package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tender.domain.Ponude;

/**
 * Spring Data JPA repository for the Ponude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PonudeRepository extends JpaRepository<Ponude, Long>, JpaSpecificationExecutor<Ponude> {
    @Query(
        value = "SELECT\n" +
        "ponude.id,\n" +
        "ponude.sifra_postupka,\n" +
        "ponude.sifra_ponude,\n" +
        "ponude.broj_partije,\n" +
        "ponude.naziv_proizvodjaca,\n" +
        "ponude.zasticeni_naziv,\n" +
        "ponude.ponudjena_vrijednost,\n" +
        "ponude.rok_isporuke,\n" +
        "ponude.jedinicna_cijena,\n" +
        "ponude.selected,\n" +
        "ponude.sifra_ponudjaca,\n" +
        "ponude.karakteristika,\n" +
        "ponudjaci.id,\n" +
        "ponudjaci.naziv_ponudjaca\n" +
        "FROM\n" +
        "ponude\n" +
        "INNER JOIN ponudjaci ON ponude.sifra_ponudjaca = ponudjaci.id\n where ponude.sifra_postupka=?1",
        nativeQuery = true
    )
    List<Ponude> findBySifraPostupkaPonudjaci(@Param("sifra_postupka") Integer sifra_postupka);
}
