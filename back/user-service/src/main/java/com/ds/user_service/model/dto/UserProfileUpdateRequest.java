package com.ds.user_service.model.dto;

import java.util.List;

public record UserProfileUpdateRequest(
        String location,
        String ageRange,
        List<String> preferredCategories,
        List<String> blockedSources
) {
}
