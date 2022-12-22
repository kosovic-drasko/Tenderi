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
import tender.domain.ViewVrednovanje;
import tender.repository.ViewVrednovanjeRepository;
import tender.service.criteria.ViewVrednovanjeCriteria;

/**
 * Service for executing complex queries for {@link ViewVrednovanje} entities in the database.
 * The main input is a {@link ViewVrednovanjeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ViewVrednovanje} or a {@link Page} of {@link ViewVrednovanje} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ViewVrednovanjeQueryService extends QueryService<ViewVrednovanje> {

    private final Logger log = LoggerFactory.getLogger(ViewVrednovanjeQueryService.class);

    private final ViewVrednovanjeRepository viewVrednovanjeRepository;

    public ViewVrednovanjeQueryService(ViewVrednovanjeRepository viewVrednovanjeRepository) {
        this.viewVrednovanjeRepository = viewVrednovanjeRepository;
    }

    /**
     * Return a {@link List} of {@link ViewVrednovanje} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ViewVrednovanje> findByCriteria(ViewVrednovanjeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ViewVrednovanje> specification = createSpecification(criteria);
        return viewVrednovanjeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ViewVrednovanje} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ViewVrednovanje> findByCriteria(ViewVrednovanjeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ViewVrednovanje> specification = createSpecification(criteria);
        return viewVrednovanjeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ViewVrednovanjeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ViewVrednovanje> specification = createSpecification(criteria);
        return viewVrednovanjeRepository.count(specification);
    }

    /**
     * Function to convert {@link ViewVrednovanjeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ViewVrednovanje> createSpecification(ViewVrednovanjeCriteria criteria) {
        Specification<ViewVrednovanje> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ViewVrednovanje_.id));
            }
            if (criteria.getSifraPostupka() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPostupka(), ViewVrednovanje_.sifraPostupka));
            }
            if (criteria.getSifraPonude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPonude(), ViewVrednovanje_.sifraPonude));
            }
            if (criteria.getBrojPartije() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBrojPartije(), ViewVrednovanje_.brojPartije));
            }
            if (criteria.getNazivProizvodjaca() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNazivProizvodjaca(), ViewVrednovanje_.nazivProizvodjaca));
            }
            if (criteria.getZasticeniNaziv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZasticeniNaziv(), ViewVrednovanje_.zasticeniNaziv));
            }
            if (criteria.getPonudjenaVrijednost() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPonudjenaVrijednost(), ViewVrednovanje_.ponudjenaVrijednost));
            }
            if (criteria.getRokIsporuke() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRokIsporuke(), ViewVrednovanje_.rokIsporuke));
            }
            if (criteria.getJedinicnaCijena() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJedinicnaCijena(), ViewVrednovanje_.jedinicnaCijena));
            }
            if (criteria.getNazivPonudjaca() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNazivPonudjaca(), ViewVrednovanje_.nazivPonudjaca));
            }
            if (criteria.getAtc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAtc(), ViewVrednovanje_.atc));
            }
            if (criteria.getTrazenaKolicina() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrazenaKolicina(), ViewVrednovanje_.trazenaKolicina));
            }
            if (criteria.getProcijenjenaVrijednost() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getProcijenjenaVrijednost(), ViewVrednovanje_.procijenjenaVrijednost)
                    );
            }
            if (criteria.getVrstaPostupka() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVrstaPostupka(), ViewVrednovanje_.vrstaPostupka));
            }
            if (criteria.getKatekteristikaPonude() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getKatekteristikaPonude(), ViewVrednovanje_.katekteristikaPonude));
            }
            if (criteria.getKarakteristikaSpecifikacije() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getKarakteristikaSpecifikacije(), ViewVrednovanje_.karakteristikaSpecifikacije)
                    );
            }
            if (criteria.getBodCijena() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodCijena(), ViewVrednovanje_.bodCijena));
            }
            if (criteria.getBodRok() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodRok(), ViewVrednovanje_.bodRok));
            }
            if (criteria.getBodUkupno() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodUkupno(), ViewVrednovanje_.bodUkupno));
            }
        }
        return specification;
    }
}
