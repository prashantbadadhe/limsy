package com.radixile.limsy.service;

import com.radixile.limsy.domain.Dress;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Dress.
 */
public interface DressService {

    /**
     * Save a dress.
     *
     * @param dress the entity to save
     * @return the persisted entity
     */
    Dress save(Dress dress);

    /**
     * Get all the dresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Dress> findAll(Pageable pageable);


    /**
     * Get the "id" dress.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Dress> findOne(Long id);

    /**
     * Delete the "id" dress.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
