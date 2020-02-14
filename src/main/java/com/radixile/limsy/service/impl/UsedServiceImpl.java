package com.radixile.limsy.service.impl;

import com.radixile.limsy.service.UsedService;
import com.radixile.limsy.domain.Used;
import com.radixile.limsy.repository.UsedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Used.
 */
@Service
@Transactional
public class UsedServiceImpl implements UsedService {

    private final Logger log = LoggerFactory.getLogger(UsedServiceImpl.class);

    private final UsedRepository usedRepository;

    public UsedServiceImpl(UsedRepository usedRepository) {
        this.usedRepository = usedRepository;
    }

    /**
     * Save a used.
     *
     * @param used the entity to save
     * @return the persisted entity
     */
    @Override
    public Used save(Used used) {
        log.debug("Request to save Used : {}", used);
        return usedRepository.save(used);
    }

    /**
     * Get all the useds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Used> findAll(Pageable pageable) {
        log.debug("Request to get all Useds");
        return usedRepository.findAll(pageable);
    }


    /**
     * Get one used by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Used> findOne(Long id) {
        log.debug("Request to get Used : {}", id);
        return usedRepository.findById(id);
    }

    /**
     * Delete the used by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Used : {}", id);
        usedRepository.deleteById(id);
    }
}
