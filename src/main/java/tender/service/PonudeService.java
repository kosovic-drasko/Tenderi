package tender.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tender.domain.Ponude;
import tender.repository.PonudeRepository;

/**
 * Service Implementation for managing {@link Ponude}.
 */
@Service
@Transactional
public class PonudeService {

    private final Logger log = LoggerFactory.getLogger(PonudeService.class);

    private final PonudeRepository ponudeRepository;

    public PonudeService(PonudeRepository ponudeRepository) {
        this.ponudeRepository = ponudeRepository;
    }

    /**
     * Save a ponude.
     *
     * @param ponude the entity to save.
     * @return the persisted entity.
     */
    public Ponude save(Ponude ponude) {
        log.debug("Request to save Ponude : {}", ponude);
        return ponudeRepository.save(ponude);
    }

    /**
     * Update a ponude.
     *
     * @param ponude the entity to save.
     * @return the persisted entity.
     */
    public Ponude update(Ponude ponude) {
        log.debug("Request to update Ponude : {}", ponude);
        return ponudeRepository.save(ponude);
    }

    /**
     * Partially update a ponude.
     *
     * @param ponude the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Ponude> partialUpdate(Ponude ponude) {
        log.debug("Request to partially update Ponude : {}", ponude);

        return ponudeRepository
            .findById(ponude.getId())
            .map(existingPonude -> {
                if (ponude.getSifraPostupka() != null) {
                    existingPonude.setSifraPostupka(ponude.getSifraPostupka());
                }
                if (ponude.getSifraPonude() != null) {
                    existingPonude.setSifraPonude(ponude.getSifraPonude());
                }
                if (ponude.getBrojPartije() != null) {
                    existingPonude.setBrojPartije(ponude.getBrojPartije());
                }
                if (ponude.getNazivProizvodjaca() != null) {
                    existingPonude.setNazivProizvodjaca(ponude.getNazivProizvodjaca());
                }
                if (ponude.getZasticeniNaziv() != null) {
                    existingPonude.setZasticeniNaziv(ponude.getZasticeniNaziv());
                }
                if (ponude.getPonudjenaVrijednost() != null) {
                    existingPonude.setPonudjenaVrijednost(ponude.getPonudjenaVrijednost());
                }
                if (ponude.getRokIsporuke() != null) {
                    existingPonude.setRokIsporuke(ponude.getRokIsporuke());
                }
                if (ponude.getJedinicnaCijena() != null) {
                    existingPonude.setJedinicnaCijena(ponude.getJedinicnaCijena());
                }
                if (ponude.getSelected() != null) {
                    existingPonude.setSelected(ponude.getSelected());
                }
                if (ponude.getSifraPonudjaca() != null) {
                    existingPonude.setSifraPonudjaca(ponude.getSifraPonudjaca());
                }
                if (ponude.getKarakteristika() != null) {
                    existingPonude.setKarakteristika(ponude.getKarakteristika());
                }

                return existingPonude;
            })
            .map(ponudeRepository::save);
    }

    /**
     * Get all the ponudes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Ponude> findAll() {
        log.debug("Request to get all Ponudes");
        return ponudeRepository.findAll();
    }

    /**
     * Get one ponude by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ponude> findOne(Long id) {
        log.debug("Request to get Ponude : {}", id);
        return ponudeRepository.findById(id);
    }

    /**
     * Delete the ponude by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ponude : {}", id);
        ponudeRepository.deleteById(id);
    }
}
