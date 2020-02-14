package com.radixile.limsy.service.impl;

import com.radixile.limsy.service.DressService;
import com.radixile.limsy.domain.Dress;
import com.radixile.limsy.repository.DressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Dress.
 */
@Service
@Transactional
public class DressServiceImpl implements DressService {

    private final Logger log = LoggerFactory.getLogger(DressServiceImpl.class);

    private final DressRepository dressRepository;

    public DressServiceImpl(DressRepository dressRepository) {
        this.dressRepository = dressRepository;
    }

    /**
     * Save a dress.
     *
     * @param dress the entity to save
     * @return the persisted entity
     */
    @Override
    public Dress save(Dress dress) {
        log.debug("Request to save Dress : {}", dress);
        return dressRepository.save(dress);
    }

    /**
     * Get all the dresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Dress> findAll(Pageable pageable) {
        log.debug("Request to get all Dresses");
        return dressRepository.findAll(pageable);
    }


    /**
     * Get one dress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Dress> findOne(Long id) {
        log.debug("Request to get Dress : {}", id);
        return dressRepository.findById(id);
    }

    /**
     * Delete the dress by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dress : {}", id);
        dressRepository.deleteById(id);
    }
}
