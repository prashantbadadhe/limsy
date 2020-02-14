package com.radixile.limsy.service.impl;

import com.radixile.limsy.service.SuggestionService;
import com.radixile.limsy.domain.Suggestion;
import com.radixile.limsy.repository.SuggestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Suggestion.
 */
@Service
@Transactional
public class SuggestionServiceImpl implements SuggestionService {

    private final Logger log = LoggerFactory.getLogger(SuggestionServiceImpl.class);

    private final SuggestionRepository suggestionRepository;

    public SuggestionServiceImpl(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

    /**
     * Save a suggestion.
     *
     * @param suggestion the entity to save
     * @return the persisted entity
     */
    @Override
    public Suggestion save(Suggestion suggestion) {
        log.debug("Request to save Suggestion : {}", suggestion);
        return suggestionRepository.save(suggestion);
    }

    /**
     * Get all the suggestions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Suggestion> findAll(Pageable pageable) {
        log.debug("Request to get all Suggestions");
        return suggestionRepository.findAll(pageable);
    }


    /**
     * Get one suggestion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Suggestion> findOne(Long id) {
        log.debug("Request to get Suggestion : {}", id);
        return suggestionRepository.findById(id);
    }

    /**
     * Delete the suggestion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Suggestion : {}", id);
        suggestionRepository.deleteById(id);
    }
}
