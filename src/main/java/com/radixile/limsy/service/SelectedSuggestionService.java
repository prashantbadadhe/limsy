package com.radixile.limsy.service;

import com.radixile.limsy.domain.SelectedSuggestion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SelectedSuggestion.
 */
public interface SelectedSuggestionService {

    /**
     * Save a selectedSuggestion.
     *
     * @param selectedSuggestion the entity to save
     * @return the persisted entity
     */
    SelectedSuggestion save(SelectedSuggestion selectedSuggestion);

    /**
     * Get all the selectedSuggestions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SelectedSuggestion> findAll(Pageable pageable);


    /**
     * Get the "id" selectedSuggestion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SelectedSuggestion> findOne(Long id);

    /**
     * Delete the "id" selectedSuggestion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
