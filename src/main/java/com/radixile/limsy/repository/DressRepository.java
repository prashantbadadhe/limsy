package com.radixile.limsy.repository;

import com.radixile.limsy.domain.Dress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DressRepository extends JpaRepository<Dress, Long> {

}
