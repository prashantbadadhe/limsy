package com.radixile.limsy.web.rest;
import com.radixile.limsy.domain.Used;
import com.radixile.limsy.service.UsedService;
import com.radixile.limsy.web.rest.errors.BadRequestAlertException;
import com.radixile.limsy.web.rest.util.HeaderUtil;
import com.radixile.limsy.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Used.
 */
@RestController
@RequestMapping("/api")
public class UsedResource {

    private final Logger log = LoggerFactory.getLogger(UsedResource.class);

    private static final String ENTITY_NAME = "used";

    private final UsedService usedService;

    public UsedResource(UsedService usedService) {
        this.usedService = usedService;
    }

    /**
     * POST  /useds : Create a new used.
     *
     * @param used the used to create
     * @return the ResponseEntity with status 201 (Created) and with body the new used, or with status 400 (Bad Request) if the used has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/useds")
    public ResponseEntity<Used> createUsed(@RequestBody Used used) throws URISyntaxException {
        log.debug("REST request to save Used : {}", used);
        if (used.getId() != null) {
            throw new BadRequestAlertException("A new used cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Used result = usedService.save(used);
        return ResponseEntity.created(new URI("/api/useds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /useds : Updates an existing used.
     *
     * @param used the used to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated used,
     * or with status 400 (Bad Request) if the used is not valid,
     * or with status 500 (Internal Server Error) if the used couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/useds")
    public ResponseEntity<Used> updateUsed(@RequestBody Used used) throws URISyntaxException {
        log.debug("REST request to update Used : {}", used);
        if (used.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Used result = usedService.save(used);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, used.getId().toString()))
            .body(result);
    }

    /**
     * GET  /useds : get all the useds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of useds in body
     */
    @GetMapping("/useds")
    public ResponseEntity<List<Used>> getAllUseds(Pageable pageable) {
        log.debug("REST request to get a page of Useds");
        Page<Used> page = usedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/useds");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /useds/:id : get the "id" used.
     *
     * @param id the id of the used to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the used, or with status 404 (Not Found)
     */
    @GetMapping("/useds/{id}")
    public ResponseEntity<Used> getUsed(@PathVariable Long id) {
        log.debug("REST request to get Used : {}", id);
        Optional<Used> used = usedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(used);
    }

    /**
     * DELETE  /useds/:id : delete the "id" used.
     *
     * @param id the id of the used to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/useds/{id}")
    public ResponseEntity<Void> deleteUsed(@PathVariable Long id) {
        log.debug("REST request to delete Used : {}", id);
        usedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
