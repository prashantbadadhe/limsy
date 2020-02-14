package com.radixile.limsy.service;

import com.radixile.limsy.domain.Suggestion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Suggestion.
 */
public interface SuggestionService {

    /**
     * Save a suggestion.
     *
     * @param suggestion the entity to save
     * @return the persisted entity
     */
    Suggestion save(Suggestion suggestion);

    /**
     * Get all the suggestions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Suggestion> findAll(Pageable pageable);


    /**
     * Get the "id" suggestion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Suggestion> findOne(Long id);

    /**
     * Delete the "id" suggestion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
