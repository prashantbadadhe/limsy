package com.radixile.limsy.web.rest;
import com.radixile.limsy.domain.UserAdditional;
import com.radixile.limsy.service.UserAdditionalService;
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
 * REST controller for managing UserAdditional.
 */
@RestController
@RequestMapping("/api")
public class UserAdditionalResource {

    private final Logger log = LoggerFactory.getLogger(UserAdditionalResource.class);

    private static final String ENTITY_NAME = "userAdditional";

    private final UserAdditionalService userAdditionalService;

    public UserAdditionalResource(UserAdditionalService userAdditionalService) {
        this.userAdditionalService = userAdditionalService;
    }

    /**
     * POST  /user-additionals : Create a new userAdditional.
     *
     * @param userAdditional the userAdditional to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userAdditional, or with status 400 (Bad Request) if the userAdditional has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-additionals")
    public ResponseEntity<UserAdditional> createUserAdditional(@RequestBody UserAdditional userAdditional) throws URISyntaxException {
        log.debug("REST request to save UserAdditional : {}", userAdditional);
        if (userAdditional.getId() != null) {
            throw new BadRequestAlertException("A new userAdditional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAdditional result = userAdditionalService.save(userAdditional);
        return ResponseEntity.created(new URI("/api/user-additionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-additionals : Updates an existing userAdditional.
     *
     * @param userAdditional the userAdditional to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userAdditional,
     * or with status 400 (Bad Request) if the userAdditional is not valid,
     * or with status 500 (Internal Server Error) if the userAdditional couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-additionals")
    public ResponseEntity<UserAdditional> updateUserAdditional(@RequestBody UserAdditional userAdditional) throws URISyntaxException {
        log.debug("REST request to update UserAdditional : {}", userAdditional);
        if (userAdditional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAdditional result = userAdditionalService.save(userAdditional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userAdditional.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-additionals : get all the userAdditionals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userAdditionals in body
     */
    @GetMapping("/user-additionals")
    public ResponseEntity<List<UserAdditional>> getAllUserAdditionals(Pageable pageable) {
        log.debug("REST request to get a page of UserAdditionals");
        Page<UserAdditional> page = userAdditionalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-additionals");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-additionals/:id : get the "id" userAdditional.
     *
     * @param id the id of the userAdditional to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userAdditional, or with status 404 (Not Found)
     */
    @GetMapping("/user-additionals/{id}")
    public ResponseEntity<UserAdditional> getUserAdditional(@PathVariable Long id) {
        log.debug("REST request to get UserAdditional : {}", id);
        Optional<UserAdditional> userAdditional = userAdditionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAdditional);
    }

    /**
     * DELETE  /user-additionals/:id : delete the "id" userAdditional.
     *
     * @param id the id of the userAdditional to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-additionals/{id}")
    public ResponseEntity<Void> deleteUserAdditional(@PathVariable Long id) {
        log.debug("REST request to delete UserAdditional : {}", id);
        userAdditionalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
