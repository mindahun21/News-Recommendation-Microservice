package com.ds.user_service.repository;

import com.ds.user_service.model.Preferences;
import com.ds.user_service.model.dto.InterestedNewsResponse;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface UserPreferenceRepository  extends ReactiveCrudRepository<Preferences,String> {
    @Query("SELECT * FROM preferences WHERE preferred_categories && :categories")
    Flux<Preferences> findPreferencesByPreferredCategories(List<String> preferredCategories);
}
