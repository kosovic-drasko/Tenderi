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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tender.domain.TenderiHome;
import tender.repository.TenderiHomeRepository;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.TenderiHome}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TenderiHomeResource {

    private final Logger log = LoggerFactory.getLogger(TenderiHomeResource.class);

    private final TenderiHomeRepository tenderiHomeRepository;

    public TenderiHomeResource(TenderiHomeRepository tenderiHomeRepository) {
        this.tenderiHomeRepository = tenderiHomeRepository;
    }

    /**
     * {@code GET  /tenderi-homes} : get all the tenderiHomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tenderiHomes in body.
     */
    @GetMapping("/tenderi-homes")
    public List<TenderiHome> getAllTenderiHomes() {
        log.debug("REST request to get all TenderiHomes");
        return tenderiHomeRepository.findAll();
    }

    /**
     * {@code GET  /tenderi-homes/:id} : get the "id" tenderiHome.
     *
     * @param id the id of the tenderiHome to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tenderiHome, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tenderi-homes/{id}")
    public ResponseEntity<TenderiHome> getTenderiHome(@PathVariable Long id) {
        log.debug("REST request to get TenderiHome : {}", id);
        Optional<TenderiHome> tenderiHome = tenderiHomeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tenderiHome);
    }
}
