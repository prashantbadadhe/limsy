package com.radixile.limsy.repository;

import com.radixile.limsy.domain.UserAdditional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserAdditional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAdditionalRepository extends JpaRepository<UserAdditional, Long> {

}
