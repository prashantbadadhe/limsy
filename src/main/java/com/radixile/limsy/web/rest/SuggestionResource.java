package com.radixile.limsy.web.rest;
import com.radixile.limsy.domain.Suggestion;
import com.radixile.limsy.service.SuggestionService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Suggestion.
 */
@RestController
@RequestMapping("/api")
public class SuggestionResource {

    private final Logger log = LoggerFactory.getLogger(SuggestionResource.class);

    private static final String ENTITY_NAME = "suggestion";

    private final SuggestionService suggestionService;

    public SuggestionResource(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    /**
     * POST  /suggestions : Create a new suggestion.
     *
     * @param suggestion the suggestion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new suggestion, or with status 400 (Bad Request) if the suggestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suggestions")
    public ResponseEntity<Suggestion> createSuggestion(@Valid @RequestBody Suggestion suggestion) throws URISyntaxException {
        log.debug("REST request to save Suggestion : {}", suggestion);
        if (suggestion.getId() != null) {
            throw new BadRequestAlertException("A new suggestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Suggestion result = suggestionService.save(suggestion);
        return ResponseEntity.created(new URI("/api/suggestions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suggestions : Updates an existing suggestion.
     *
     * @param suggestion the suggestion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated suggestion,
     * or with status 400 (Bad Request) if the suggestion is not valid,
     * or with status 500 (Internal Server Error) if the suggestion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suggestions")
    public ResponseEntity<Suggestion> updateSuggestion(@Valid @RequestBody Suggestion suggestion) throws URISyntaxException {
        log.debug("REST request to update Suggestion : {}", suggestion);
        if (suggestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Suggestion result = suggestionService.save(suggestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, suggestion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /suggestions : get all the suggestions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of suggestions in body
     */
    @GetMapping("/suggestions")
    public ResponseEntity<List<Suggestion>> getAllSuggestions(Pageable pageable) {
        log.debug("REST request to get a page of Suggestions");
        Page<Suggestion> page = suggestionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/suggestions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /suggestions/:id : get the "id" suggestion.
     *
     * @param id the id of the suggestion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the suggestion, or with status 404 (Not Found)
     */
    @GetMapping("/suggestions/{id}")
    public ResponseEntity<Suggestion> getSuggestion(@PathVariable Long id) {
        log.debug("REST request to get Suggestion : {}", id);
        Optional<Suggestion> suggestion = suggestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suggestion);
    }

    /**
     * DELETE  /suggestions/:id : delete the "id" suggestion.
     *
     * @param id the id of the suggestion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/suggestions/{id}")
    public ResponseEntity<Void> deleteSuggestion(@PathVariable Long id) {
        log.debug("REST request to delete Suggestion : {}", id);
        suggestionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
