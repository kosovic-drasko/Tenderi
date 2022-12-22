package tender.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tender.domain.ViewVrednovanje;
import tender.repository.ViewVrednovanjeRepository;

/**
 * Service Implementation for managing {@link ViewVrednovanje}.
 */
@Service
@Transactional
public class ViewVrednovanjeService {

    private final Logger log = LoggerFactory.getLogger(ViewVrednovanjeService.class);

    private final ViewVrednovanjeRepository viewVrednovanjeRepository;

    public ViewVrednovanjeService(ViewVrednovanjeRepository viewVrednovanjeRepository) {
        this.viewVrednovanjeRepository = viewVrednovanjeRepository;
    }

    /**
     * Save a viewVrednovanje.
     *
     * @param viewVrednovanje the entity to save.
     * @return the persisted entity.
     */
    public ViewVrednovanje save(ViewVrednovanje viewVrednovanje) {
        log.debug("Request to save ViewVrednovanje : {}", viewVrednovanje);
        return viewVrednovanjeRepository.save(viewVrednovanje);
    }

    /**
     * Update a viewVrednovanje.
     *
     * @param viewVrednovanje the entity to save.
     * @return the persisted entity.
     */
    public ViewVrednovanje update(ViewVrednovanje viewVrednovanje) {
        log.debug("Request to update ViewVrednovanje : {}", viewVrednovanje);
        return viewVrednovanjeRepository.save(viewVrednovanje);
    }

    /**
     * Partially update a viewVrednovanje.
     *
     * @param viewVrednovanje the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ViewVrednovanje> partialUpdate(ViewVrednovanje viewVrednovanje) {
        log.debug("Request to partially update ViewVrednovanje : {}", viewVrednovanje);

        return viewVrednovanjeRepository
            .findById(viewVrednovanje.getId())
            .map(existingViewVrednovanje -> {
                if (viewVrednovanje.getSifraPostupka() != null) {
                    existingViewVrednovanje.setSifraPostupka(viewVrednovanje.getSifraPostupka());
                }
                if (viewVrednovanje.getSifraPonude() != null) {
                    existingViewVrednovanje.setSifraPonude(viewVrednovanje.getSifraPonude());
                }
                if (viewVrednovanje.getBrojPartije() != null) {
                    existingViewVrednovanje.setBrojPartije(viewVrednovanje.getBrojPartije());
                }
                if (viewVrednovanje.getNazivProizvodjaca() != null) {
                    existingViewVrednovanje.setNazivProizvodjaca(viewVrednovanje.getNazivProizvodjaca());
                }
                if (viewVrednovanje.getZasticeniNaziv() != null) {
                    existingViewVrednovanje.setZasticeniNaziv(viewVrednovanje.getZasticeniNaziv());
                }
                if (viewVrednovanje.getPonudjenaVrijednost() != null) {
                    existingViewVrednovanje.setPonudjenaVrijednost(viewVrednovanje.getPonudjenaVrijednost());
                }
                if (viewVrednovanje.getRokIsporuke() != null) {
                    existingViewVrednovanje.setRokIsporuke(viewVrednovanje.getRokIsporuke());
                }
                if (viewVrednovanje.getJedinicnaCijena() != null) {
                    existingViewVrednovanje.setJedinicnaCijena(viewVrednovanje.getJedinicnaCijena());
                }
                if (viewVrednovanje.getNazivPonudjaca() != null) {
                    existingViewVrednovanje.setNazivPonudjaca(viewVrednovanje.getNazivPonudjaca());
                }
                if (viewVrednovanje.getAtc() != null) {
                    existingViewVrednovanje.setAtc(viewVrednovanje.getAtc());
                }
                if (viewVrednovanje.getTrazenaKolicina() != null) {
                    existingViewVrednovanje.setTrazenaKolicina(viewVrednovanje.getTrazenaKolicina());
                }
                if (viewVrednovanje.getProcijenjenaVrijednost() != null) {
                    existingViewVrednovanje.setProcijenjenaVrijednost(viewVrednovanje.getProcijenjenaVrijednost());
                }
                if (viewVrednovanje.getVrstaPostupka() != null) {
                    existingViewVrednovanje.setVrstaPostupka(viewVrednovanje.getVrstaPostupka());
                }
                if (viewVrednovanje.getKatekteristikaPonude() != null) {
                    existingViewVrednovanje.setKatekteristikaPonude(viewVrednovanje.getKatekteristikaPonude());
                }
                if (viewVrednovanje.getKarakteristikaSpecifikacije() != null) {
                    existingViewVrednovanje.setKarakteristikaSpecifikacije(viewVrednovanje.getKarakteristikaSpecifikacije());
                }
                if (viewVrednovanje.getBodCijena() != null) {
                    existingViewVrednovanje.setBodCijena(viewVrednovanje.getBodCijena());
                }
                if (viewVrednovanje.getBodRok() != null) {
                    existingViewVrednovanje.setBodRok(viewVrednovanje.getBodRok());
                }
                if (viewVrednovanje.getBodUkupno() != null) {
                    existingViewVrednovanje.setBodUkupno(viewVrednovanje.getBodUkupno());
                }

                return existingViewVrednovanje;
            })
            .map(viewVrednovanjeRepository::save);
    }

    /**
     * Get all the viewVrednovanjes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ViewVrednovanje> findAll() {
        log.debug("Request to get all ViewVrednovanjes");
        return viewVrednovanjeRepository.findAll();
    }

    /**
     * Get one viewVrednovanje by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ViewVrednovanje> findOne(Long id) {
        log.debug("Request to get ViewVrednovanje : {}", id);
        return viewVrednovanjeRepository.findById(id);
    }

    /**
     * Delete the viewVrednovanje by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ViewVrednovanje : {}", id);
        viewVrednovanjeRepository.deleteById(id);
    }
}
