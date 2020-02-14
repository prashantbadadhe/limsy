package com.radixile.limsy.service;

import com.radixile.limsy.domain.Used;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Used.
 */
public interface UsedService {

    /**
     * Save a used.
     *
     * @param used the entity to save
     * @return the persisted entity
     */
    Used save(Used used);

    /**
     * Get all the useds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Used> findAll(Pageable pageable);


    /**
     * Get the "id" used.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Used> findOne(Long id);

    /**
     * Delete the "id" used.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
