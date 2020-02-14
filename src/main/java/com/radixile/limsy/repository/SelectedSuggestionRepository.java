package com.radixile.limsy.repository;

import com.radixile.limsy.domain.SelectedSuggestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SelectedSuggestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelectedSuggestionRepository extends JpaRepository<SelectedSuggestion, Long> {

}
