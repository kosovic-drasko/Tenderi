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
import tender.domain.ViewPrvorangirani;
import tender.repository.ViewPrvorangiraniRepository;
import tender.service.criteria.ViewPrvorangiraniCriteria;
import tender.service.dto.ViewPrvorangiraniDTO;
import tender.service.mapper.ViewPrvorangiraniMapper;

/**
 * Service for executing complex queries for {@link ViewPrvorangirani} entities in the database.
 * The main input is a {@link ViewPrvorangiraniCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ViewPrvorangiraniDTO} or a {@link Page} of {@link ViewPrvorangiraniDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ViewPrvorangiraniQueryService extends QueryService<ViewPrvorangirani> {

    private final Logger log = LoggerFactory.getLogger(ViewPrvorangiraniQueryService.class);

    private final ViewPrvorangiraniRepository viewPrvorangiraniRepository;

    private final ViewPrvorangiraniMapper viewPrvorangiraniMapper;

    public ViewPrvorangiraniQueryService(
        ViewPrvorangiraniRepository viewPrvorangiraniRepository,
        ViewPrvorangiraniMapper viewPrvorangiraniMapper
    ) {
        this.viewPrvorangiraniRepository = viewPrvorangiraniRepository;
        this.viewPrvorangiraniMapper = viewPrvorangiraniMapper;
    }

    /**
     * Return a {@link List} of {@link ViewPrvorangiraniDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ViewPrvorangiraniDTO> findByCriteria(ViewPrvorangiraniCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ViewPrvorangirani> specification = createSpecification(criteria);
        return viewPrvorangiraniMapper.toDto(viewPrvorangiraniRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ViewPrvorangiraniDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ViewPrvorangiraniDTO> findByCriteria(ViewPrvorangiraniCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ViewPrvorangirani> specification = createSpecification(criteria);
        return viewPrvorangiraniRepository.findAll(specification, page).map(viewPrvorangiraniMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ViewPrvorangiraniCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ViewPrvorangirani> specification = createSpecification(criteria);
        return viewPrvorangiraniRepository.count(specification);
    }

    /**
     * Function to convert {@link ViewPrvorangiraniCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ViewPrvorangirani> createSpecification(ViewPrvorangiraniCriteria criteria) {
        Specification<ViewPrvorangirani> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ViewPrvorangirani_.id));
            }
            if (criteria.getSifraPostupka() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPostupka(), ViewPrvorangirani_.sifraPostupka));
            }
            if (criteria.getNazivPonudjaca() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNazivPonudjaca(), ViewPrvorangirani_.nazivPonudjaca));
            }
            if (criteria.getSifraPonude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSifraPonude(), ViewPrvorangirani_.sifraPonude));
            }
            if (criteria.getAtc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAtc(), ViewPrvorangirani_.atc));
            }
            if (criteria.getTrazenaKolicina() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTrazenaKolicina(), ViewPrvorangirani_.trazenaKolicina));
            }
            if (criteria.getProcijenjenaVrijednost() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getProcijenjenaVrijednost(), ViewPrvorangirani_.procijenjenaVrijednost)
                    );
            }
            if (criteria.getNazivProizvodjaca() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNazivProizvodjaca(), ViewPrvorangirani_.nazivProizvodjaca));
            }
            if (criteria.getZasticeniNaziv() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getZasticeniNaziv(), ViewPrvorangirani_.zasticeniNaziv));
            }
            if (criteria.getJedinicnaCijena() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getJedinicnaCijena(), ViewPrvorangirani_.jedinicnaCijena));
            }
            if (criteria.getPonudjenaVrijednost() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPonudjenaVrijednost(), ViewPrvorangirani_.ponudjenaVrijednost));
            }
            if (criteria.getRokIsporuke() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRokIsporuke(), ViewPrvorangirani_.rokIsporuke));
            }
            if (criteria.getVrstaPostupka() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVrstaPostupka(), ViewPrvorangirani_.vrstaPostupka));
            }
            if (criteria.getBodCijena() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodCijena(), ViewPrvorangirani_.bodCijena));
            }
            if (criteria.getBodRok() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodRok(), ViewPrvorangirani_.bodRok));
            }
            if (criteria.getBodUkupno() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodUkupno(), ViewPrvorangirani_.bodUkupno));
            }
            if (criteria.getKarakteristikaSpecifikacije() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getKarakteristikaSpecifikacije(), ViewPrvorangirani_.karakteristikaSpecifikacije)
                    );
            }
            if (criteria.getKarakteristikaPonude() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getKarakteristikaPonude(), ViewPrvorangirani_.karakteristikaPonude)
                    );
            }
        }
        return specification;
    }
}
