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
import tender.domain.HvalePonude;
import tender.repository.HvalePonudeRepository;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.HvalePonude}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HvalePonudeResource {

    private final Logger log = LoggerFactory.getLogger(HvalePonudeResource.class);

    private final HvalePonudeRepository hvalePonudeRepository;

    public HvalePonudeResource(HvalePonudeRepository hvalePonudeRepository) {
        this.hvalePonudeRepository = hvalePonudeRepository;
    }

    /**
     * {@code GET  /hvale-ponudes} : get all the hvalePonudes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hvalePonudes in body.
     */
    @GetMapping("/hvale-ponudes")
    public List<HvalePonude> getAllHvalePonudes() {
        log.debug("REST request to get all HvalePonudes");
        return hvalePonudeRepository.findAll();
    }

    /**
     * {@code GET  /hvale-ponudes/:id} : get the "id" hvalePonude.
     *
     * @param id the id of the hvalePonude to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hvalePonude, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hvale-ponudes/{id}")
    public ResponseEntity<HvalePonude> getHvalePonude(@PathVariable Long id) {
        log.debug("REST request to get HvalePonude : {}", id);
        Optional<HvalePonude> hvalePonude = hvalePonudeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hvalePonude);
    }
}
