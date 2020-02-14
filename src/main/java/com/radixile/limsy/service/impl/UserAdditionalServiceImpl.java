package com.radixile.limsy.service.impl;

import com.radixile.limsy.service.UserAdditionalService;
import com.radixile.limsy.domain.UserAdditional;
import com.radixile.limsy.repository.UserAdditionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing UserAdditional.
 */
@Service
@Transactional
public class UserAdditionalServiceImpl implements UserAdditionalService {

    private final Logger log = LoggerFactory.getLogger(UserAdditionalServiceImpl.class);

    private final UserAdditionalRepository userAdditionalRepository;

    public UserAdditionalServiceImpl(UserAdditionalRepository userAdditionalRepository) {
        this.userAdditionalRepository = userAdditionalRepository;
    }

    /**
     * Save a userAdditional.
     *
     * @param userAdditional the entity to save
     * @return the persisted entity
     */
    @Override
    public UserAdditional save(UserAdditional userAdditional) {
        log.debug("Request to save UserAdditional : {}", userAdditional);
        return userAdditionalRepository.save(userAdditional);
    }

    /**
     * Get all the userAdditionals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAdditional> findAll(Pageable pageable) {
        log.debug("Request to get all UserAdditionals");
        return userAdditionalRepository.findAll(pageable);
    }


    /**
     * Get one userAdditional by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserAdditional> findOne(Long id) {
        log.debug("Request to get UserAdditional : {}", id);
        return userAdditionalRepository.findById(id);
    }

    /**
     * Delete the userAdditional by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAdditional : {}", id);
        userAdditionalRepository.deleteById(id);
    }
}
