package com.ds.user_service.model.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsEvent {
    private String userId;
    private String value; // category, provider, location, or ageRange
    private String action; // ADD, REMOVE, UPDATE
}
