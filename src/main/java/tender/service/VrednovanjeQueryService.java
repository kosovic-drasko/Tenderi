package tender.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tender.domain.*; // for static metamodels
import tender.domain.Vrednovanje;
import tender.repository.VrednovanjeRepository;
import tender.service.criteria.VrednovanjeCriteria;

/**
 * Service for executing complex queries for {@link Vrednovanje} entities in the database.
 * The main input is a {@link VrednovanjeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Vrednovanje} or a {@link Page} of {@link Vrednovanje} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VrednovanjeQueryService extends QueryService<Vrednovanje> {

    private final Logger log = LoggerFactory.getLogger(VrednovanjeQueryService.class);

    private final VrednovanjeRepository vrednovanjeRepository;

    public VrednovanjeQueryService(VrednovanjeRepository vrednovanjeRepository) {
        this.vrednovanjeRepository = vrednovanjeRepository;
    }

    /**
     * Return a {@link List} of {@link Vrednovanje} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Vrednovanje> findByCriteria(VrednovanjeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vrednovanje> specification = createSpecification(criteria);
        return vrednovanjeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Vrednovanje} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Vrednovanje> findByCriteria(VrednovanjeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vrednovanje> specification = createSpecification(criteria);
        return vrednovanjeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VrednovanjeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vrednovanje> specification = createSpecification(criteria);
        return vrednovanjeRepository.count(specification);
    }

    /**
     * Function to convert {@link VrednovanjeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vrednovanje> createSpecification(VrednovanjeCriteria criteria) {
        Specification<Vrednovanje> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Vrednovanje_.id));
            }
            if (criteria.getSifraPostupka() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPostupka(), Vrednovanje_.sifraPostupka));
            }
            if (criteria.getSifraPonude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPonude(), Vrednovanje_.sifraPonude));
            }
            if (criteria.getBrojPartije() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBrojPartije(), Vrednovanje_.brojPartije));
            }
            if (criteria.getNazivProizvodjaca() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNazivProizvodjaca(), Vrednovanje_.nazivProizvodjaca));
            }
            if (criteria.getZasticeniNaziv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZasticeniNaziv(), Vrednovanje_.zasticeniNaziv));
            }
            if (criteria.getPonudjenaVrijednost() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPonudjenaVrijednost(), Vrednovanje_.ponudjenaVrijednost));
            }
            if (criteria.getRokIsporuke() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRokIsporuke(), Vrednovanje_.rokIsporuke));
            }
            if (criteria.getJedinicnaCijena() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJedinicnaCijena(), Vrednovanje_.jedinicnaCijena));
            }
            if (criteria.getNazivPonudjaca() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNazivPonudjaca(), Vrednovanje_.nazivPonudjaca));
            }
            if (criteria.getAtc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAtc(), Vrednovanje_.atc));
            }
            if (criteria.getTrazenaKolicina() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrazenaKolicina(), Vrednovanje_.trazenaKolicina));
            }
            if (criteria.getProcijenjenaVrijednost() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getProcijenjenaVrijednost(), Vrednovanje_.procijenjenaVrijednost));
            }
            if (criteria.getVrstaPostupka() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVrstaPostupka(), Vrednovanje_.vrstaPostupka));
            }
            if (criteria.getBodCijena() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodCijena(), Vrednovanje_.bodCijena));
            }
            if (criteria.getBodRok() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodRok(), Vrednovanje_.bodRok));
            }
            if (criteria.getBodUkupno() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodUkupno(), Vrednovanje_.bodUkupno));
            }
        }
        return specification;
    }
}
