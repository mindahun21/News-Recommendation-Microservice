package com.ds.user_service.repository;

import com.ds.user_service.model.UserRecommendationProfile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRecommendationProfileRepository extends ReactiveCrudRepository<UserRecommendationProfile, String> {
}
