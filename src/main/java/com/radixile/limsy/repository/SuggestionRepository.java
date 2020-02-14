package com.radixile.limsy.repository;

import com.radixile.limsy.domain.Suggestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Suggestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

}
