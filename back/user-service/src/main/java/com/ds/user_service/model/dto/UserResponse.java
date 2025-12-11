package com.ds.user_service.model.dto;

import java.util.List;

public record UserResponse(
        String username,
        List<String> role
) { }
