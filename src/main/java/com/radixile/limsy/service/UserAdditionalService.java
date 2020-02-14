package com.radixile.limsy.service;

import com.radixile.limsy.domain.UserAdditional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserAdditional.
 */
public interface UserAdditionalService {

    /**
     * Save a userAdditional.
     *
     * @param userAdditional the entity to save
     * @return the persisted entity
     */
    UserAdditional save(UserAdditional userAdditional);

    /**
     * Get all the userAdditionals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserAdditional> findAll(Pageable pageable);


    /**
     * Get the "id" userAdditional.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserAdditional> findOne(Long id);

    /**
     * Delete the "id" userAdditional.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
