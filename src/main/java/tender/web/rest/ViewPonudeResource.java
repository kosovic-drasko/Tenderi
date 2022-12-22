package tender.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tender.domain.ViewPonude;
import tender.repository.ViewPonudeRepository;
import tender.service.ViewPonudeQueryService;
import tender.service.ViewPonudeService;
import tender.service.criteria.ViewPonudeCriteria;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.ViewPonude}.
 */
@RestController
@RequestMapping("/api")
public class ViewPonudeResource {

    private final Logger log = LoggerFactory.getLogger(ViewPonudeResource.class);

    private static final String ENTITY_NAME = "viewPonude";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ViewPonudeService viewPonudeService;

    private final ViewPonudeRepository viewPonudeRepository;

    private final ViewPonudeQueryService viewPonudeQueryService;

    public ViewPonudeResource(
        ViewPonudeService viewPonudeService,
        ViewPonudeRepository viewPonudeRepository,
        ViewPonudeQueryService viewPonudeQueryService
    ) {
        this.viewPonudeService = viewPonudeService;
        this.viewPonudeRepository = viewPonudeRepository;
        this.viewPonudeQueryService = viewPonudeQueryService;
    }

    /**
     * {@code POST  /view-ponudes} : Create a new viewPonude.
     *
     * @param viewPonude the viewPonude to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viewPonude, or with status {@code 400 (Bad Request)} if the viewPonude has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/view-ponudes")
    public ResponseEntity<ViewPonude> createViewPonude(@Valid @RequestBody ViewPonude viewPonude) throws URISyntaxException {
        log.debug("REST request to save ViewPonude : {}", viewPonude);
        if (viewPonude.getId() != null) {
            throw new BadRequestAlertException("A new viewPonude cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ViewPonude result = viewPonudeService.save(viewPonude);
        return ResponseEntity
            .created(new URI("/api/view-ponudes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /view-ponudes/:id} : Updates an existing viewPonude.
     *
     * @param id the id of the viewPonude to save.
     * @param viewPonude the viewPonude to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viewPonude,
     * or with status {@code 400 (Bad Request)} if the viewPonude is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viewPonude couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/view-ponudes/{id}")
    public ResponseEntity<ViewPonude> updateViewPonude(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ViewPonude viewPonude
    ) throws URISyntaxException {
        log.debug("REST request to update ViewPonude : {}, {}", id, viewPonude);
        if (viewPonude.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viewPonude.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viewPonudeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ViewPonude result = viewPonudeService.update(viewPonude);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viewPonude.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /view-ponudes/:id} : Partial updates given fields of an existing viewPonude, field will ignore if it is null
     *
     * @param id the id of the viewPonude to save.
     * @param viewPonude the viewPonude to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viewPonude,
     * or with status {@code 400 (Bad Request)} if the viewPonude is not valid,
     * or with status {@code 404 (Not Found)} if the viewPonude is not found,
     * or with status {@code 500 (Internal Server Error)} if the viewPonude couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/view-ponudes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ViewPonude> partialUpdateViewPonude(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ViewPonude viewPonude
    ) throws URISyntaxException {
        log.debug("REST request to partial update ViewPonude partially : {}, {}", id, viewPonude);
        if (viewPonude.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viewPonude.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viewPonudeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ViewPonude> result = viewPonudeService.partialUpdate(viewPonude);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viewPonude.getId().toString())
        );
    }

    /**
     * {@code GET  /view-ponudes} : get all the viewPonudes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viewPonudes in body.
     */
    @GetMapping("/view-ponudes")
    public ResponseEntity<List<ViewPonude>> getAllViewPonudes(ViewPonudeCriteria criteria) {
        log.debug("REST request to get ViewPonudes by criteria: {}", criteria);
        List<ViewPonude> entityList = viewPonudeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /view-ponudes/count} : count all the viewPonudes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/view-ponudes/count")
    public ResponseEntity<Long> countViewPonudes(ViewPonudeCriteria criteria) {
        log.debug("REST request to count ViewPonudes by criteria: {}", criteria);
        return ResponseEntity.ok().body(viewPonudeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /view-ponudes/:id} : get the "id" viewPonude.
     *
     * @param id the id of the viewPonude to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viewPonude, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/view-ponudes/{id}")
    public ResponseEntity<ViewPonude> getViewPonude(@PathVariable Long id) {
        log.debug("REST request to get ViewPonude : {}", id);
        Optional<ViewPonude> viewPonude = viewPonudeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(viewPonude);
    }

    /**
     * {@code DELETE  /view-ponudes/:id} : delete the "id" viewPonude.
     *
     * @param id the id of the viewPonude to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/view-ponudes/{id}")
    public ResponseEntity<Void> deleteViewPonude(@PathVariable Long id) {
        log.debug("REST request to delete ViewPonude : {}", id);
        viewPonudeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
