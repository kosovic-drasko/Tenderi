package tender.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tender.repository.ViewPrvorangiraniRepository;
import tender.service.ViewPrvorangiraniQueryService;
import tender.service.ViewPrvorangiraniService;
import tender.service.criteria.ViewPrvorangiraniCriteria;
import tender.service.dto.ViewPrvorangiraniDTO;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.ViewPrvorangirani}.
 */
@RestController
@RequestMapping("/api")
public class ViewPrvorangiraniResource {

    private final Logger log = LoggerFactory.getLogger(ViewPrvorangiraniResource.class);

    private final ViewPrvorangiraniService viewPrvorangiraniService;

    private final ViewPrvorangiraniRepository viewPrvorangiraniRepository;

    private final ViewPrvorangiraniQueryService viewPrvorangiraniQueryService;

    public ViewPrvorangiraniResource(
        ViewPrvorangiraniService viewPrvorangiraniService,
        ViewPrvorangiraniRepository viewPrvorangiraniRepository,
        ViewPrvorangiraniQueryService viewPrvorangiraniQueryService
    ) {
        this.viewPrvorangiraniService = viewPrvorangiraniService;
        this.viewPrvorangiraniRepository = viewPrvorangiraniRepository;
        this.viewPrvorangiraniQueryService = viewPrvorangiraniQueryService;
    }

    /**
     * {@code GET  /view-prvorangiranis} : get all the viewPrvorangiranis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viewPrvorangiranis in body.
     */
    @GetMapping("/view-prvorangiranis")
    public ResponseEntity<List<ViewPrvorangiraniDTO>> getAllViewPrvorangiranis(ViewPrvorangiraniCriteria criteria) {
        log.debug("REST request to get ViewPrvorangiranis by criteria: {}", criteria);
        List<ViewPrvorangiraniDTO> entityList = viewPrvorangiraniQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /view-prvorangiranis/count} : count all the viewPrvorangiranis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/view-prvorangiranis/count")
    public ResponseEntity<Long> countViewPrvorangiranis(ViewPrvorangiraniCriteria criteria) {
        log.debug("REST request to count ViewPrvorangiranis by criteria: {}", criteria);
        return ResponseEntity.ok().body(viewPrvorangiraniQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /view-prvorangiranis/:id} : get the "id" viewPrvorangirani.
     *
     * @param id the id of the viewPrvorangiraniDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viewPrvorangiraniDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/view-prvorangiranis/{id}")
    public ResponseEntity<ViewPrvorangiraniDTO> getViewPrvorangirani(@PathVariable Long id) {
        log.debug("REST request to get ViewPrvorangirani : {}", id);
        Optional<ViewPrvorangiraniDTO> viewPrvorangiraniDTO = viewPrvorangiraniService.findOne(id);
        return ResponseUtil.wrapOrNotFound(viewPrvorangiraniDTO);
    }
}
