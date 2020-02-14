package com.radixile.limsy.service.impl;

import com.radixile.limsy.service.SelectedSuggestionService;
import com.radixile.limsy.domain.SelectedSuggestion;
import com.radixile.limsy.repository.SelectedSuggestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SelectedSuggestion.
 */
@Service
@Transactional
public class SelectedSuggestionServiceImpl implements SelectedSuggestionService {

    private final Logger log = LoggerFactory.getLogger(SelectedSuggestionServiceImpl.class);

    private final SelectedSuggestionRepository selectedSuggestionRepository;

    public SelectedSuggestionServiceImpl(SelectedSuggestionRepository selectedSuggestionRepository) {
        this.selectedSuggestionRepository = selectedSuggestionRepository;
    }

    /**
     * Save a selectedSuggestion.
     *
     * @param selectedSuggestion the entity to save
     * @return the persisted entity
     */
    @Override
    public SelectedSuggestion save(SelectedSuggestion selectedSuggestion) {
        log.debug("Request to save SelectedSuggestion : {}", selectedSuggestion);
        return selectedSuggestionRepository.save(selectedSuggestion);
    }

    /**
     * Get all the selectedSuggestions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SelectedSuggestion> findAll(Pageable pageable) {
        log.debug("Request to get all SelectedSuggestions");
        return selectedSuggestionRepository.findAll(pageable);
    }


    /**
     * Get one selectedSuggestion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SelectedSuggestion> findOne(Long id) {
        log.debug("Request to get SelectedSuggestion : {}", id);
        return selectedSuggestionRepository.findById(id);
    }

    /**
     * Delete the selectedSuggestion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SelectedSuggestion : {}", id);
        selectedSuggestionRepository.deleteById(id);
    }
}
