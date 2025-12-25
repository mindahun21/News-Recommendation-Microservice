package com.ds.user_service.repository;

import com.ds.user_service.model.Demographics;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DemographicsRepository extends ReactiveCrudRepository<Demographics, String> {
}
