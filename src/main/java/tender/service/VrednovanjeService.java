package tender.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tender.domain.Vrednovanje;
import tender.repository.VrednovanjeRepository;

/**
 * Service Implementation for managing {@link Vrednovanje}.
 */
@Service
@Transactional
public class VrednovanjeService {

    private final Logger log = LoggerFactory.getLogger(VrednovanjeService.class);

    private final VrednovanjeRepository vrednovanjeRepository;

    public VrednovanjeService(VrednovanjeRepository vrednovanjeRepository) {
        this.vrednovanjeRepository = vrednovanjeRepository;
    }

    /**
     * Save a vrednovanje.
     *
     * @param vrednovanje the entity to save.
     * @return the persisted entity.
     */
    public Vrednovanje save(Vrednovanje vrednovanje) {
        log.debug("Request to save Vrednovanje : {}", vrednovanje);
        return vrednovanjeRepository.save(vrednovanje);
    }

    /**
     * Update a vrednovanje.
     *
     * @param vrednovanje the entity to save.
     * @return the persisted entity.
     */
    public Vrednovanje update(Vrednovanje vrednovanje) {
        log.debug("Request to update Vrednovanje : {}", vrednovanje);
        return vrednovanjeRepository.save(vrednovanje);
    }

    /**
     * Partially update a vrednovanje.
     *
     * @param vrednovanje the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Vrednovanje> partialUpdate(Vrednovanje vrednovanje) {
        log.debug("Request to partially update Vrednovanje : {}", vrednovanje);

        return vrednovanjeRepository
            .findById(vrednovanje.getId())
            .map(existingVrednovanje -> {
                if (vrednovanje.getSifraPostupka() != null) {
                    existingVrednovanje.setSifraPostupka(vrednovanje.getSifraPostupka());
                }
                if (vrednovanje.getSifraPonude() != null) {
                    existingVrednovanje.setSifraPonude(vrednovanje.getSifraPonude());
                }
                if (vrednovanje.getBrojPartije() != null) {
                    existingVrednovanje.setBrojPartije(vrednovanje.getBrojPartije());
                }
                if (vrednovanje.getNazivProizvodjaca() != null) {
                    existingVrednovanje.setNazivProizvodjaca(vrednovanje.getNazivProizvodjaca());
                }
                if (vrednovanje.getZasticeniNaziv() != null) {
                    existingVrednovanje.setZasticeniNaziv(vrednovanje.getZasticeniNaziv());
                }
                if (vrednovanje.getPonudjenaVrijednost() != null) {
                    existingVrednovanje.setPonudjenaVrijednost(vrednovanje.getPonudjenaVrijednost());
                }
                if (vrednovanje.getRokIsporuke() != null) {
                    existingVrednovanje.setRokIsporuke(vrednovanje.getRokIsporuke());
                }
                if (vrednovanje.getJedinicnaCijena() != null) {
                    existingVrednovanje.setJedinicnaCijena(vrednovanje.getJedinicnaCijena());
                }
                if (vrednovanje.getNazivPonudjaca() != null) {
                    existingVrednovanje.setNazivPonudjaca(vrednovanje.getNazivPonudjaca());
                }
                if (vrednovanje.getAtc() != null) {
                    existingVrednovanje.setAtc(vrednovanje.getAtc());
                }
                if (vrednovanje.getTrazenaKolicina() != null) {
                    existingVrednovanje.setTrazenaKolicina(vrednovanje.getTrazenaKolicina());
                }
                if (vrednovanje.getProcijenjenaVrijednost() != null) {
                    existingVrednovanje.setProcijenjenaVrijednost(vrednovanje.getProcijenjenaVrijednost());
                }
                if (vrednovanje.getVrstaPostupka() != null) {
                    existingVrednovanje.setVrstaPostupka(vrednovanje.getVrstaPostupka());
                }
                if (vrednovanje.getBodCijena() != null) {
                    existingVrednovanje.setBodCijena(vrednovanje.getBodCijena());
                }
                if (vrednovanje.getBodRok() != null) {
                    existingVrednovanje.setBodRok(vrednovanje.getBodRok());
                }
                if (vrednovanje.getBodUkupno() != null) {
                    existingVrednovanje.setBodUkupno(vrednovanje.getBodUkupno());
                }

                return existingVrednovanje;
            })
            .map(vrednovanjeRepository::save);
    }

    /**
     * Get all the vrednovanjes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Vrednovanje> findAll() {
        log.debug("Request to get all Vrednovanjes");
        return vrednovanjeRepository.findAll();
    }

    /**
     * Get one vrednovanje by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Vrednovanje> findOne(Long id) {
        log.debug("Request to get Vrednovanje : {}", id);
        return vrednovanjeRepository.findById(id);
    }

    /**
     * Delete the vrednovanje by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vrednovanje : {}", id);
        vrednovanjeRepository.deleteById(id);
    }
}
