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
import tender.domain.HvalePonude;
import tender.repository.HvalePonudeRepository;
import tender.service.criteria.HvalePonudeCriteria;

/**
 * Service for executing complex queries for {@link HvalePonude} entities in the database.
 * The main input is a {@link HvalePonudeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HvalePonude} or a {@link Page} of {@link HvalePonude} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HvalePonudeQueryService extends QueryService<HvalePonude> {

    private final Logger log = LoggerFactory.getLogger(HvalePonudeQueryService.class);

    private final HvalePonudeRepository hvalePonudeRepository;

    public HvalePonudeQueryService(HvalePonudeRepository hvalePonudeRepository) {
        this.hvalePonudeRepository = hvalePonudeRepository;
    }

    /**
     * Return a {@link List} of {@link HvalePonude} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HvalePonude> findByCriteria(HvalePonudeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HvalePonude> specification = createSpecification(criteria);
        return hvalePonudeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HvalePonude} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HvalePonude> findByCriteria(HvalePonudeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HvalePonude> specification = createSpecification(criteria);
        return hvalePonudeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HvalePonudeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HvalePonude> specification = createSpecification(criteria);
        return hvalePonudeRepository.count(specification);
    }

    /**
     * Function to convert {@link HvalePonudeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HvalePonude> createSpecification(HvalePonudeCriteria criteria) {
        Specification<HvalePonude> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), HvalePonude_.id));
            }
            if (criteria.getSifraPostupka() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPostupka(), HvalePonude_.sifraPostupka));
            }
            if (criteria.getBrojPartije() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBrojPartije(), HvalePonude_.brojPartije));
            }
            if (criteria.getInn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInn(), HvalePonude_.inn));
            }
            if (criteria.getFarmaceutskiOblikLijeka() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getFarmaceutskiOblikLijeka(), HvalePonude_.farmaceutskiOblikLijeka)
                    );
            }
            if (criteria.getPakovanje() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPakovanje(), HvalePonude_.pakovanje));
            }
            if (criteria.getTrazenaKolicina() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrazenaKolicina(), HvalePonude_.trazenaKolicina));
            }
            if (criteria.getProcijenjenaVrijednost() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getProcijenjenaVrijednost(), HvalePonude_.procijenjenaVrijednost));
            }
        }
        return specification;
    }
}
