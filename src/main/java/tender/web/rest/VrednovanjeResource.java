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
import tender.domain.Vrednovanje;
import tender.repository.VrednovanjeRepository;
import tender.service.VrednovanjeQueryService;
import tender.service.VrednovanjeService;
import tender.service.criteria.VrednovanjeCriteria;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.Vrednovanje}.
 */
@RestController
@RequestMapping("/api")
public class VrednovanjeResource {

    private final Logger log = LoggerFactory.getLogger(VrednovanjeResource.class);

    private final VrednovanjeService vrednovanjeService;

    private final VrednovanjeRepository vrednovanjeRepository;

    private final VrednovanjeQueryService vrednovanjeQueryService;

    public VrednovanjeResource(
        VrednovanjeService vrednovanjeService,
        VrednovanjeRepository vrednovanjeRepository,
        VrednovanjeQueryService vrednovanjeQueryService
    ) {
        this.vrednovanjeService = vrednovanjeService;
        this.vrednovanjeRepository = vrednovanjeRepository;
        this.vrednovanjeQueryService = vrednovanjeQueryService;
    }

    /**
     * {@code GET  /vrednovanjes} : get all the vrednovanjes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vrednovanjes in body.
     */
    @GetMapping("/vrednovanjes")
    public ResponseEntity<List<Vrednovanje>> getAllVrednovanjes(VrednovanjeCriteria criteria) {
        log.debug("REST request to get Vrednovanjes by criteria: {}", criteria);
        List<Vrednovanje> entityList = vrednovanjeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /vrednovanjes/count} : count all the vrednovanjes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vrednovanjes/count")
    public ResponseEntity<Long> countVrednovanjes(VrednovanjeCriteria criteria) {
        log.debug("REST request to count Vrednovanjes by criteria: {}", criteria);
        return ResponseEntity.ok().body(vrednovanjeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vrednovanjes/:id} : get the "id" vrednovanje.
     *
     * @param id the id of the vrednovanje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vrednovanje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vrednovanjes/{id}")
    public ResponseEntity<Vrednovanje> getVrednovanje(@PathVariable Long id) {
        log.debug("REST request to get Vrednovanje : {}", id);
        Optional<Vrednovanje> vrednovanje = vrednovanjeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vrednovanje);
    }
}
