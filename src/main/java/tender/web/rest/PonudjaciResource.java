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
import tender.domain.Ponudjaci;
import tender.repository.PonudjaciRepository;
import tender.service.PonudjaciQueryService;
import tender.service.PonudjaciService;
import tender.service.criteria.PonudjaciCriteria;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.Ponudjaci}.
 */
@RestController
@RequestMapping("/api")
public class PonudjaciResource {

    private final Logger log = LoggerFactory.getLogger(PonudjaciResource.class);

    private static final String ENTITY_NAME = "ponudjaci";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PonudjaciService ponudjaciService;

    private final PonudjaciRepository ponudjaciRepository;

    private final PonudjaciQueryService ponudjaciQueryService;

    public PonudjaciResource(
        PonudjaciService ponudjaciService,
        PonudjaciRepository ponudjaciRepository,
        PonudjaciQueryService ponudjaciQueryService
    ) {
        this.ponudjaciService = ponudjaciService;
        this.ponudjaciRepository = ponudjaciRepository;
        this.ponudjaciQueryService = ponudjaciQueryService;
    }

    /**
     * {@code POST  /ponudjacis} : Create a new ponudjaci.
     *
     * @param ponudjaci the ponudjaci to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ponudjaci, or with status {@code 400 (Bad Request)} if the ponudjaci has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ponudjacis")
    public ResponseEntity<Ponudjaci> createPonudjaci(@RequestBody Ponudjaci ponudjaci) throws URISyntaxException {
        log.debug("REST request to save Ponudjaci : {}", ponudjaci);
        if (ponudjaci.getId() != null) {
            throw new BadRequestAlertException("A new ponudjaci cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ponudjaci result = ponudjaciService.save(ponudjaci);
        return ResponseEntity
            .created(new URI("/api/ponudjacis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ponudjacis/:id} : Updates an existing ponudjaci.
     *
     * @param id the id of the ponudjaci to save.
     * @param ponudjaci the ponudjaci to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ponudjaci,
     * or with status {@code 400 (Bad Request)} if the ponudjaci is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ponudjaci couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ponudjacis/{id}")
    public ResponseEntity<Ponudjaci> updatePonudjaci(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ponudjaci ponudjaci
    ) throws URISyntaxException {
        log.debug("REST request to update Ponudjaci : {}, {}", id, ponudjaci);
        if (ponudjaci.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ponudjaci.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ponudjaciRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ponudjaci result = ponudjaciService.update(ponudjaci);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ponudjaci.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ponudjacis/:id} : Partial updates given fields of an existing ponudjaci, field will ignore if it is null
     *
     * @param id the id of the ponudjaci to save.
     * @param ponudjaci the ponudjaci to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ponudjaci,
     * or with status {@code 400 (Bad Request)} if the ponudjaci is not valid,
     * or with status {@code 404 (Not Found)} if the ponudjaci is not found,
     * or with status {@code 500 (Internal Server Error)} if the ponudjaci couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ponudjacis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ponudjaci> partialUpdatePonudjaci(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ponudjaci ponudjaci
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ponudjaci partially : {}, {}", id, ponudjaci);
        if (ponudjaci.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ponudjaci.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ponudjaciRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ponudjaci> result = ponudjaciService.partialUpdate(ponudjaci);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ponudjaci.getId().toString())
        );
    }

    /**
     * {@code GET  /ponudjacis} : get all the ponudjacis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ponudjacis in body.
     */
    @GetMapping("/ponudjacis")
    public ResponseEntity<List<Ponudjaci>> getAllPonudjacis(PonudjaciCriteria criteria) {
        log.debug("REST request to get Ponudjacis by criteria: {}", criteria);
        List<Ponudjaci> entityList = ponudjaciQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ponudjacis/count} : count all the ponudjacis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ponudjacis/count")
    public ResponseEntity<Long> countPonudjacis(PonudjaciCriteria criteria) {
        log.debug("REST request to count Ponudjacis by criteria: {}", criteria);
        return ResponseEntity.ok().body(ponudjaciQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ponudjacis/:id} : get the "id" ponudjaci.
     *
     * @param id the id of the ponudjaci to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ponudjaci, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ponudjacis/{id}")
    public ResponseEntity<Ponudjaci> getPonudjaci(@PathVariable Long id) {
        log.debug("REST request to get Ponudjaci : {}", id);
        Optional<Ponudjaci> ponudjaci = ponudjaciService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ponudjaci);
    }

    /**
     * {@code DELETE  /ponudjacis/:id} : delete the "id" ponudjaci.
     *
     * @param id the id of the ponudjaci to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ponudjacis/{id}")
    public ResponseEntity<Void> deletePonudjaci(@PathVariable Long id) {
        log.debug("REST request to delete Ponudjaci : {}", id);
        ponudjaciService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
