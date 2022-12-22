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
import tender.domain.ViewVrednovanje;
import tender.repository.ViewVrednovanjeRepository;
import tender.service.ViewVrednovanjeQueryService;
import tender.service.ViewVrednovanjeService;
import tender.service.criteria.ViewVrednovanjeCriteria;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.ViewVrednovanje}.
 */
@RestController
@RequestMapping("/api")
public class ViewVrednovanjeResource {

    private final Logger log = LoggerFactory.getLogger(ViewVrednovanjeResource.class);

    private final ViewVrednovanjeService viewVrednovanjeService;

    private final ViewVrednovanjeRepository viewVrednovanjeRepository;

    private final ViewVrednovanjeQueryService viewVrednovanjeQueryService;

    public ViewVrednovanjeResource(
        ViewVrednovanjeService viewVrednovanjeService,
        ViewVrednovanjeRepository viewVrednovanjeRepository,
        ViewVrednovanjeQueryService viewVrednovanjeQueryService
    ) {
        this.viewVrednovanjeService = viewVrednovanjeService;
        this.viewVrednovanjeRepository = viewVrednovanjeRepository;
        this.viewVrednovanjeQueryService = viewVrednovanjeQueryService;
    }

    /**
     * {@code GET  /view-vrednovanjes} : get all the viewVrednovanjes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viewVrednovanjes in body.
     */
    @GetMapping("/view-vrednovanjes")
    public ResponseEntity<List<ViewVrednovanje>> getAllViewVrednovanjes(ViewVrednovanjeCriteria criteria) {
        log.debug("REST request to get ViewVrednovanjes by criteria: {}", criteria);
        List<ViewVrednovanje> entityList = viewVrednovanjeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /view-vrednovanjes/count} : count all the viewVrednovanjes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/view-vrednovanjes/count")
    public ResponseEntity<Long> countViewVrednovanjes(ViewVrednovanjeCriteria criteria) {
        log.debug("REST request to count ViewVrednovanjes by criteria: {}", criteria);
        return ResponseEntity.ok().body(viewVrednovanjeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /view-vrednovanjes/:id} : get the "id" viewVrednovanje.
     *
     * @param id the id of the viewVrednovanje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viewVrednovanje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/view-vrednovanjes/{id}")
    public ResponseEntity<ViewVrednovanje> getViewVrednovanje(@PathVariable Long id) {
        log.debug("REST request to get ViewVrednovanje : {}", id);
        Optional<ViewVrednovanje> viewVrednovanje = viewVrednovanjeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(viewVrednovanje);
    }
}
