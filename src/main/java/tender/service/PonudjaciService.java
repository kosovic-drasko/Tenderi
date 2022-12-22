package tender.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tender.domain.Ponudjaci;
import tender.repository.PonudjaciRepository;

/**
 * Service Implementation for managing {@link Ponudjaci}.
 */
@Service
@Transactional
public class PonudjaciService {

    private final Logger log = LoggerFactory.getLogger(PonudjaciService.class);

    private final PonudjaciRepository ponudjaciRepository;

    public PonudjaciService(PonudjaciRepository ponudjaciRepository) {
        this.ponudjaciRepository = ponudjaciRepository;
    }

    /**
     * Save a ponudjaci.
     *
     * @param ponudjaci the entity to save.
     * @return the persisted entity.
     */
    public Ponudjaci save(Ponudjaci ponudjaci) {
        log.debug("Request to save Ponudjaci : {}", ponudjaci);
        return ponudjaciRepository.save(ponudjaci);
    }

    /**
     * Update a ponudjaci.
     *
     * @param ponudjaci the entity to save.
     * @return the persisted entity.
     */
    public Ponudjaci update(Ponudjaci ponudjaci) {
        log.debug("Request to update Ponudjaci : {}", ponudjaci);
        return ponudjaciRepository.save(ponudjaci);
    }

    /**
     * Partially update a ponudjaci.
     *
     * @param ponudjaci the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Ponudjaci> partialUpdate(Ponudjaci ponudjaci) {
        log.debug("Request to partially update Ponudjaci : {}", ponudjaci);

        return ponudjaciRepository
            .findById(ponudjaci.getId())
            .map(existingPonudjaci -> {
                if (ponudjaci.getNazivPonudjaca() != null) {
                    existingPonudjaci.setNazivPonudjaca(ponudjaci.getNazivPonudjaca());
                }
                if (ponudjaci.getOdgovornoLice() != null) {
                    existingPonudjaci.setOdgovornoLice(ponudjaci.getOdgovornoLice());
                }
                if (ponudjaci.getAdresaPonudjaca() != null) {
                    existingPonudjaci.setAdresaPonudjaca(ponudjaci.getAdresaPonudjaca());
                }
                if (ponudjaci.getBankaRacun() != null) {
                    existingPonudjaci.setBankaRacun(ponudjaci.getBankaRacun());
                }

                return existingPonudjaci;
            })
            .map(ponudjaciRepository::save);
    }

    /**
     * Get all the ponudjacis.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Ponudjaci> findAll() {
        log.debug("Request to get all Ponudjacis");
        return ponudjaciRepository.findAll();
    }

    /**
     * Get one ponudjaci by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ponudjaci> findOne(Long id) {
        log.debug("Request to get Ponudjaci : {}", id);
        return ponudjaciRepository.findById(id);
    }

    /**
     * Delete the ponudjaci by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ponudjaci : {}", id);
        ponudjaciRepository.deleteById(id);
    }
}
