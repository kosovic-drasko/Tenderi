package tender.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tender.domain.HvalePonude;
import tender.repository.HvalePonudeRepository;

/**
 * Service Implementation for managing {@link HvalePonude}.
 */
@Service
@Transactional
public class HvalePonudeService {

    private final Logger log = LoggerFactory.getLogger(HvalePonudeService.class);

    private final HvalePonudeRepository hvalePonudeRepository;

    public HvalePonudeService(HvalePonudeRepository hvalePonudeRepository) {
        this.hvalePonudeRepository = hvalePonudeRepository;
    }

    /**
     * Save a hvalePonude.
     *
     * @param hvalePonude the entity to save.
     * @return the persisted entity.
     */
    public HvalePonude save(HvalePonude hvalePonude) {
        log.debug("Request to save HvalePonude : {}", hvalePonude);
        return hvalePonudeRepository.save(hvalePonude);
    }

    /**
     * Update a hvalePonude.
     *
     * @param hvalePonude the entity to save.
     * @return the persisted entity.
     */
    public HvalePonude update(HvalePonude hvalePonude) {
        log.debug("Request to update HvalePonude : {}", hvalePonude);
        return hvalePonudeRepository.save(hvalePonude);
    }

    /**
     * Partially update a hvalePonude.
     *
     * @param hvalePonude the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HvalePonude> partialUpdate(HvalePonude hvalePonude) {
        log.debug("Request to partially update HvalePonude : {}", hvalePonude);

        return hvalePonudeRepository
            .findById(hvalePonude.getId())
            .map(existingHvalePonude -> {
                if (hvalePonude.getSifraPostupka() != null) {
                    existingHvalePonude.setSifraPostupka(hvalePonude.getSifraPostupka());
                }
                if (hvalePonude.getBrojPartije() != null) {
                    existingHvalePonude.setBrojPartije(hvalePonude.getBrojPartije());
                }
                if (hvalePonude.getInn() != null) {
                    existingHvalePonude.setInn(hvalePonude.getInn());
                }
                if (hvalePonude.getFarmaceutskiOblikLijeka() != null) {
                    existingHvalePonude.setFarmaceutskiOblikLijeka(hvalePonude.getFarmaceutskiOblikLijeka());
                }
                if (hvalePonude.getPakovanje() != null) {
                    existingHvalePonude.setPakovanje(hvalePonude.getPakovanje());
                }
                if (hvalePonude.getTrazenaKolicina() != null) {
                    existingHvalePonude.setTrazenaKolicina(hvalePonude.getTrazenaKolicina());
                }
                if (hvalePonude.getProcijenjenaVrijednost() != null) {
                    existingHvalePonude.setProcijenjenaVrijednost(hvalePonude.getProcijenjenaVrijednost());
                }

                return existingHvalePonude;
            })
            .map(hvalePonudeRepository::save);
    }

    /**
     * Get all the hvalePonudes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HvalePonude> findAll() {
        log.debug("Request to get all HvalePonudes");
        return hvalePonudeRepository.findAll();
    }

    /**
     * Get one hvalePonude by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HvalePonude> findOne(Long id) {
        log.debug("Request to get HvalePonude : {}", id);
        return hvalePonudeRepository.findById(id);
    }

    /**
     * Delete the hvalePonude by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HvalePonude : {}", id);
        hvalePonudeRepository.deleteById(id);
    }
}
