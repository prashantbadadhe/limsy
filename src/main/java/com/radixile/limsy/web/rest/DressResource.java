package com.radixile.limsy.web.rest;
import com.radixile.limsy.domain.Dress;
import com.radixile.limsy.service.DressService;
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
 * REST controller for managing Dress.
 */
@RestController
@RequestMapping("/api")
public class DressResource {

    private final Logger log = LoggerFactory.getLogger(DressResource.class);

    private static final String ENTITY_NAME = "dress";

    private final DressService dressService;

    public DressResource(DressService dressService) {
        this.dressService = dressService;
    }

    /**
     * POST  /dresses : Create a new dress.
     *
     * @param dress the dress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dress, or with status 400 (Bad Request) if the dress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dresses")
    public ResponseEntity<Dress> createDress(@RequestBody Dress dress) throws URISyntaxException {
        log.debug("REST request to save Dress : {}", dress);
        if (dress.getId() != null) {
            throw new BadRequestAlertException("A new dress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dress result = dressService.save(dress);
        return ResponseEntity.created(new URI("/api/dresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dresses : Updates an existing dress.
     *
     * @param dress the dress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dress,
     * or with status 400 (Bad Request) if the dress is not valid,
     * or with status 500 (Internal Server Error) if the dress couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dresses")
    public ResponseEntity<Dress> updateDress(@RequestBody Dress dress) throws URISyntaxException {
        log.debug("REST request to update Dress : {}", dress);
        if (dress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dress result = dressService.save(dress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dresses : get all the dresses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dresses in body
     */
    @GetMapping("/dresses")
    public ResponseEntity<List<Dress>> getAllDresses(Pageable pageable) {
        log.debug("REST request to get a page of Dresses");
        Page<Dress> page = dressService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dresses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /dresses/:id : get the "id" dress.
     *
     * @param id the id of the dress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dress, or with status 404 (Not Found)
     */
    @GetMapping("/dresses/{id}")
    public ResponseEntity<Dress> getDress(@PathVariable Long id) {
        log.debug("REST request to get Dress : {}", id);
        Optional<Dress> dress = dressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dress);
    }

    /**
     * DELETE  /dresses/:id : delete the "id" dress.
     *
     * @param id the id of the dress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dresses/{id}")
    public ResponseEntity<Void> deleteDress(@PathVariable Long id) {
        log.debug("REST request to delete Dress : {}", id);
        dressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
