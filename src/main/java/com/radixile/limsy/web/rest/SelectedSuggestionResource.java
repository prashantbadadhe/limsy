package com.radixile.limsy.web.rest;
import com.radixile.limsy.domain.SelectedSuggestion;
import com.radixile.limsy.service.SelectedSuggestionService;
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
 * REST controller for managing SelectedSuggestion.
 */
@RestController
@RequestMapping("/api")
public class SelectedSuggestionResource {

    private final Logger log = LoggerFactory.getLogger(SelectedSuggestionResource.class);

    private static final String ENTITY_NAME = "selectedSuggestion";

    private final SelectedSuggestionService selectedSuggestionService;

    public SelectedSuggestionResource(SelectedSuggestionService selectedSuggestionService) {
        this.selectedSuggestionService = selectedSuggestionService;
    }

    /**
     * POST  /selected-suggestions : Create a new selectedSuggestion.
     *
     * @param selectedSuggestion the selectedSuggestion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new selectedSuggestion, or with status 400 (Bad Request) if the selectedSuggestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/selected-suggestions")
    public ResponseEntity<SelectedSuggestion> createSelectedSuggestion(@RequestBody SelectedSuggestion selectedSuggestion) throws URISyntaxException {
        log.debug("REST request to save SelectedSuggestion : {}", selectedSuggestion);
        if (selectedSuggestion.getId() != null) {
            throw new BadRequestAlertException("A new selectedSuggestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SelectedSuggestion result = selectedSuggestionService.save(selectedSuggestion);
        return ResponseEntity.created(new URI("/api/selected-suggestions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /selected-suggestions : Updates an existing selectedSuggestion.
     *
     * @param selectedSuggestion the selectedSuggestion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated selectedSuggestion,
     * or with status 400 (Bad Request) if the selectedSuggestion is not valid,
     * or with status 500 (Internal Server Error) if the selectedSuggestion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/selected-suggestions")
    public ResponseEntity<SelectedSuggestion> updateSelectedSuggestion(@RequestBody SelectedSuggestion selectedSuggestion) throws URISyntaxException {
        log.debug("REST request to update SelectedSuggestion : {}", selectedSuggestion);
        if (selectedSuggestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SelectedSuggestion result = selectedSuggestionService.save(selectedSuggestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, selectedSuggestion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /selected-suggestions : get all the selectedSuggestions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of selectedSuggestions in body
     */
    @GetMapping("/selected-suggestions")
    public ResponseEntity<List<SelectedSuggestion>> getAllSelectedSuggestions(Pageable pageable) {
        log.debug("REST request to get a page of SelectedSuggestions");
        Page<SelectedSuggestion> page = selectedSuggestionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/selected-suggestions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /selected-suggestions/:id : get the "id" selectedSuggestion.
     *
     * @param id the id of the selectedSuggestion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the selectedSuggestion, or with status 404 (Not Found)
     */
    @GetMapping("/selected-suggestions/{id}")
    public ResponseEntity<SelectedSuggestion> getSelectedSuggestion(@PathVariable Long id) {
        log.debug("REST request to get SelectedSuggestion : {}", id);
        Optional<SelectedSuggestion> selectedSuggestion = selectedSuggestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(selectedSuggestion);
    }

    /**
     * DELETE  /selected-suggestions/:id : delete the "id" selectedSuggestion.
     *
     * @param id the id of the selectedSuggestion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/selected-suggestions/{id}")
    public ResponseEntity<Void> deleteSelectedSuggestion(@PathVariable Long id) {
        log.debug("REST request to delete SelectedSuggestion : {}", id);
        selectedSuggestionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
