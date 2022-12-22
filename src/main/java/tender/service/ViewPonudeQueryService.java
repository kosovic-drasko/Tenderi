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
import tender.domain.ViewPonude;
import tender.repository.ViewPonudeRepository;
import tender.service.criteria.ViewPonudeCriteria;

/**
 * Service for executing complex queries for {@link ViewPonude} entities in the database.
 * The main input is a {@link ViewPonudeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ViewPonude} or a {@link Page} of {@link ViewPonude} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ViewPonudeQueryService extends QueryService<ViewPonude> {

    private final Logger log = LoggerFactory.getLogger(ViewPonudeQueryService.class);

    private final ViewPonudeRepository viewPonudeRepository;

    public ViewPonudeQueryService(ViewPonudeRepository viewPonudeRepository) {
        this.viewPonudeRepository = viewPonudeRepository;
    }

    /**
     * Return a {@link List} of {@link ViewPonude} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ViewPonude> findByCriteria(ViewPonudeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ViewPonude> specification = createSpecification(criteria);
        return viewPonudeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ViewPonude} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ViewPonude> findByCriteria(ViewPonudeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ViewPonude> specification = createSpecification(criteria);
        return viewPonudeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ViewPonudeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ViewPonude> specification = createSpecification(criteria);
        return viewPonudeRepository.count(specification);
    }

    /**
     * Function to convert {@link ViewPonudeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ViewPonude> createSpecification(ViewPonudeCriteria criteria) {
        Specification<ViewPonude> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ViewPonude_.id));
            }
            if (criteria.getSifraPostupka() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPostupka(), ViewPonude_.sifraPostupka));
            }
            if (criteria.getSifraPonude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPonude(), ViewPonude_.sifraPonude));
            }
            if (criteria.getBrojPartije() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBrojPartije(), ViewPonude_.brojPartije));
            }
            if (criteria.getNazivProizvodjaca() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNazivProizvodjaca(), ViewPonude_.nazivProizvodjaca));
            }
            if (criteria.getZasticeniNaziv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZasticeniNaziv(), ViewPonude_.zasticeniNaziv));
            }
            if (criteria.getPonudjenaVrijednost() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPonudjenaVrijednost(), ViewPonude_.ponudjenaVrijednost));
            }
            if (criteria.getRokIsporuke() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRokIsporuke(), ViewPonude_.rokIsporuke));
            }
            if (criteria.getJedinicnaCijena() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJedinicnaCijena(), ViewPonude_.jedinicnaCijena));
            }
            if (criteria.getSelected() != null) {
                specification = specification.and(buildSpecification(criteria.getSelected(), ViewPonude_.selected));
            }
            if (criteria.getNazivPonudjaca() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNazivPonudjaca(), ViewPonude_.nazivPonudjaca));
            }
            if (criteria.getKarakteristika() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKarakteristika(), ViewPonude_.karakteristika));
            }
        }
        return specification;
    }
}
