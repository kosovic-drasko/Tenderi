package tender.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tender.domain.Prvorangirani;
import tender.repository.PrvorangiraniRepository;

/**
 * Service Implementation for managing {@link Prvorangirani}.
 */
@Service
@Transactional
public class PrvorangiraniService {

    private final Logger log = LoggerFactory.getLogger(PrvorangiraniService.class);

    private final PrvorangiraniRepository prvorangiraniRepository;

    public PrvorangiraniService(PrvorangiraniRepository prvorangiraniRepository) {
        this.prvorangiraniRepository = prvorangiraniRepository;
    }

    /**
     * Save a prvorangirani.
     *
     * @param prvorangirani the entity to save.
     * @return the persisted entity.
     */
    public Prvorangirani save(Prvorangirani prvorangirani) {
        log.debug("Request to save Prvorangirani : {}", prvorangirani);
        return prvorangiraniRepository.save(prvorangirani);
    }

    /**
     * Update a prvorangirani.
     *
     * @param prvorangirani the entity to save.
     * @return the persisted entity.
     */
    public Prvorangirani update(Prvorangirani prvorangirani) {
        log.debug("Request to update Prvorangirani : {}", prvorangirani);
        return prvorangiraniRepository.save(prvorangirani);
    }

    /**
     * Partially update a prvorangirani.
     *
     * @param prvorangirani the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Prvorangirani> partialUpdate(Prvorangirani prvorangirani) {
        log.debug("Request to partially update Prvorangirani : {}", prvorangirani);

        return prvorangiraniRepository
            .findById(prvorangirani.getId())
            .map(existingPrvorangirani -> {
                if (prvorangirani.getSifraPostupka() != null) {
                    existingPrvorangirani.setSifraPostupka(prvorangirani.getSifraPostupka());
                }
                if (prvorangirani.getNazivPonudjaca() != null) {
                    existingPrvorangirani.setNazivPonudjaca(prvorangirani.getNazivPonudjaca());
                }
                if (prvorangirani.getSifraPonude() != null) {
                    existingPrvorangirani.setSifraPonude(prvorangirani.getSifraPonude());
                }
                if (prvorangirani.getBrojPartije() != null) {
                    existingPrvorangirani.setBrojPartije(prvorangirani.getBrojPartije());
                }
                if (prvorangirani.getAtc() != null) {
                    existingPrvorangirani.setAtc(prvorangirani.getAtc());
                }
                if (prvorangirani.getTrazenaKolicina() != null) {
                    existingPrvorangirani.setTrazenaKolicina(prvorangirani.getTrazenaKolicina());
                }
                if (prvorangirani.getProcijenjenaVrijednost() != null) {
                    existingPrvorangirani.setProcijenjenaVrijednost(prvorangirani.getProcijenjenaVrijednost());
                }
                if (prvorangirani.getNazivProizvodjaca() != null) {
                    existingPrvorangirani.setNazivProizvodjaca(prvorangirani.getNazivProizvodjaca());
                }
                if (prvorangirani.getZasticeniNaziv() != null) {
                    existingPrvorangirani.setZasticeniNaziv(prvorangirani.getZasticeniNaziv());
                }
                if (prvorangirani.getJedinicnaCijena() != null) {
                    existingPrvorangirani.setJedinicnaCijena(prvorangirani.getJedinicnaCijena());
                }
                if (prvorangirani.getPonudjenaVrijednost() != null) {
                    existingPrvorangirani.setPonudjenaVrijednost(prvorangirani.getPonudjenaVrijednost());
                }
                if (prvorangirani.getRokIsporuke() != null) {
                    existingPrvorangirani.setRokIsporuke(prvorangirani.getRokIsporuke());
                }
                if (prvorangirani.getVrstaPostupka() != null) {
                    existingPrvorangirani.setVrstaPostupka(prvorangirani.getVrstaPostupka());
                }
                if (prvorangirani.getBodCijena() != null) {
                    existingPrvorangirani.setBodCijena(prvorangirani.getBodCijena());
                }
                if (prvorangirani.getBodRok() != null) {
                    existingPrvorangirani.setBodRok(prvorangirani.getBodRok());
                }
                if (prvorangirani.getBodUkupno() != null) {
                    existingPrvorangirani.setBodUkupno(prvorangirani.getBodUkupno());
                }

                return existingPrvorangirani;
            })
            .map(prvorangiraniRepository::save);
    }

    /**
     * Get all the prvorangiranis.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Prvorangirani> findAll() {
        log.debug("Request to get all Prvorangiranis");
        return prvorangiraniRepository.findAll();
    }

    /**
     * Get one prvorangirani by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Prvorangirani> findOne(Long id) {
        log.debug("Request to get Prvorangirani : {}", id);
        return prvorangiraniRepository.findById(id);
    }

    /**
     * Delete the prvorangirani by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Prvorangirani : {}", id);
        prvorangiraniRepository.deleteById(id);
    }
}
