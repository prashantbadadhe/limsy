package com.radixile.limsy.repository;

import com.radixile.limsy.domain.Used;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Used entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsedRepository extends JpaRepository<Used, Long> {

}
