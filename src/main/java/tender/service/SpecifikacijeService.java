package tender.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tender.domain.Specifikacije;
import tender.repository.SpecifikacijeRepository;

/**
 * Service Implementation for managing {@link Specifikacije}.
 */
@Service
@Transactional
public class SpecifikacijeService {

    private final Logger log = LoggerFactory.getLogger(SpecifikacijeService.class);

    private final SpecifikacijeRepository specifikacijeRepository;

    public SpecifikacijeService(SpecifikacijeRepository specifikacijeRepository) {
        this.specifikacijeRepository = specifikacijeRepository;
    }

    /**
     * Save a specifikacije.
     *
     * @param specifikacije the entity to save.
     * @return the persisted entity.
     */
    public Specifikacije save(Specifikacije specifikacije) {
        log.debug("Request to save Specifikacije : {}", specifikacije);
        return specifikacijeRepository.save(specifikacije);
    }

    /**
     * Update a specifikacije.
     *
     * @param specifikacije the entity to save.
     * @return the persisted entity.
     */
    public Specifikacije update(Specifikacije specifikacije) {
        log.debug("Request to update Specifikacije : {}", specifikacije);
        return specifikacijeRepository.save(specifikacije);
    }

    /**
     * Partially update a specifikacije.
     *
     * @param specifikacije the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Specifikacije> partialUpdate(Specifikacije specifikacije) {
        log.debug("Request to partially update Specifikacije : {}", specifikacije);

        return specifikacijeRepository
            .findById(specifikacije.getId())
            .map(existingSpecifikacije -> {
                if (specifikacije.getSifraPostupka() != null) {
                    existingSpecifikacije.setSifraPostupka(specifikacije.getSifraPostupka());
                }
                if (specifikacije.getBrojPartije() != null) {
                    existingSpecifikacije.setBrojPartije(specifikacije.getBrojPartije());
                }
                if (specifikacije.getAtc() != null) {
                    existingSpecifikacije.setAtc(specifikacije.getAtc());
                }
                if (specifikacije.getInn() != null) {
                    existingSpecifikacije.setInn(specifikacije.getInn());
                }
                if (specifikacije.getFarmaceutskiOblikLijeka() != null) {
                    existingSpecifikacije.setFarmaceutskiOblikLijeka(specifikacije.getFarmaceutskiOblikLijeka());
                }
                if (specifikacije.getJacinaLijeka() != null) {
                    existingSpecifikacije.setJacinaLijeka(specifikacije.getJacinaLijeka());
                }
                if (specifikacije.getTrazenaKolicina() != null) {
                    existingSpecifikacije.setTrazenaKolicina(specifikacije.getTrazenaKolicina());
                }
                if (specifikacije.getPakovanje() != null) {
                    existingSpecifikacije.setPakovanje(specifikacije.getPakovanje());
                }
                if (specifikacije.getJedinicaMjere() != null) {
                    existingSpecifikacije.setJedinicaMjere(specifikacije.getJedinicaMjere());
                }
                if (specifikacije.getProcijenjenaVrijednost() != null) {
                    existingSpecifikacije.setProcijenjenaVrijednost(specifikacije.getProcijenjenaVrijednost());
                }
                if (specifikacije.getJedinicnaCijena() != null) {
                    existingSpecifikacije.setJedinicnaCijena(specifikacije.getJedinicnaCijena());
                }
                if (specifikacije.getKarakteristika() != null) {
                    existingSpecifikacije.setKarakteristika(specifikacije.getKarakteristika());
                }

                return existingSpecifikacije;
            })
            .map(specifikacijeRepository::save);
    }

    /**
     * Get all the specifikacijes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Specifikacije> findAll() {
        log.debug("Request to get all Specifikacijes");
        return specifikacijeRepository.findAll();
    }

    /**
     * Get one specifikacije by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Specifikacije> findOne(Long id) {
        log.debug("Request to get Specifikacije : {}", id);
        return specifikacijeRepository.findById(id);
    }

    /**
     * Delete the specifikacije by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Specifikacije : {}", id);
        specifikacijeRepository.deleteById(id);
    }
}
