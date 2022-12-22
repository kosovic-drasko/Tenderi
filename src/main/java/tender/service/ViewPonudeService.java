package tender.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tender.domain.ViewPonude;
import tender.repository.ViewPonudeRepository;

/**
 * Service Implementation for managing {@link ViewPonude}.
 */
@Service
@Transactional
public class ViewPonudeService {

    private final Logger log = LoggerFactory.getLogger(ViewPonudeService.class);

    private final ViewPonudeRepository viewPonudeRepository;

    public ViewPonudeService(ViewPonudeRepository viewPonudeRepository) {
        this.viewPonudeRepository = viewPonudeRepository;
    }

    /**
     * Save a viewPonude.
     *
     * @param viewPonude the entity to save.
     * @return the persisted entity.
     */
    public ViewPonude save(ViewPonude viewPonude) {
        log.debug("Request to save ViewPonude : {}", viewPonude);
        return viewPonudeRepository.save(viewPonude);
    }

    /**
     * Update a viewPonude.
     *
     * @param viewPonude the entity to save.
     * @return the persisted entity.
     */
    public ViewPonude update(ViewPonude viewPonude) {
        log.debug("Request to update ViewPonude : {}", viewPonude);
        return viewPonudeRepository.save(viewPonude);
    }

    /**
     * Partially update a viewPonude.
     *
     * @param viewPonude the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ViewPonude> partialUpdate(ViewPonude viewPonude) {
        log.debug("Request to partially update ViewPonude : {}", viewPonude);

        return viewPonudeRepository
            .findById(viewPonude.getId())
            .map(existingViewPonude -> {
                if (viewPonude.getSifraPostupka() != null) {
                    existingViewPonude.setSifraPostupka(viewPonude.getSifraPostupka());
                }
                if (viewPonude.getSifraPonude() != null) {
                    existingViewPonude.setSifraPonude(viewPonude.getSifraPonude());
                }
                if (viewPonude.getBrojPartije() != null) {
                    existingViewPonude.setBrojPartije(viewPonude.getBrojPartije());
                }
                if (viewPonude.getNazivProizvodjaca() != null) {
                    existingViewPonude.setNazivProizvodjaca(viewPonude.getNazivProizvodjaca());
                }
                if (viewPonude.getZasticeniNaziv() != null) {
                    existingViewPonude.setZasticeniNaziv(viewPonude.getZasticeniNaziv());
                }
                if (viewPonude.getPonudjenaVrijednost() != null) {
                    existingViewPonude.setPonudjenaVrijednost(viewPonude.getPonudjenaVrijednost());
                }
                if (viewPonude.getRokIsporuke() != null) {
                    existingViewPonude.setRokIsporuke(viewPonude.getRokIsporuke());
                }
                if (viewPonude.getJedinicnaCijena() != null) {
                    existingViewPonude.setJedinicnaCijena(viewPonude.getJedinicnaCijena());
                }
                if (viewPonude.getSelected() != null) {
                    existingViewPonude.setSelected(viewPonude.getSelected());
                }
                if (viewPonude.getNazivPonudjaca() != null) {
                    existingViewPonude.setNazivPonudjaca(viewPonude.getNazivPonudjaca());
                }
                if (viewPonude.getKarakteristika() != null) {
                    existingViewPonude.setKarakteristika(viewPonude.getKarakteristika());
                }

                return existingViewPonude;
            })
            .map(viewPonudeRepository::save);
    }

    /**
     * Get all the viewPonudes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ViewPonude> findAll() {
        log.debug("Request to get all ViewPonudes");
        return viewPonudeRepository.findAll();
    }

    /**
     * Get one viewPonude by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ViewPonude> findOne(Long id) {
        log.debug("Request to get ViewPonude : {}", id);
        return viewPonudeRepository.findById(id);
    }

    /**
     * Delete the viewPonude by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ViewPonude : {}", id);
        viewPonudeRepository.deleteById(id);
    }
}
