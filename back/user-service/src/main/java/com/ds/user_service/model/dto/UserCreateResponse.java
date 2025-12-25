package com.ds.user_service.model.dto;

import com.ds.user_service.model.Demographics;
import com.ds.user_service.model.EngagementMetrics;
import com.ds.user_service.model.Preferences;
import com.ds.user_service.model.UserRecommendationProfile;

public record UserCreateResponse(
        UserResponse user,
        Preferences preferences,
        UserRecommendationProfile profile,
        Demographics demographics,
        EngagementMetrics engagementMetrics
) {
}
