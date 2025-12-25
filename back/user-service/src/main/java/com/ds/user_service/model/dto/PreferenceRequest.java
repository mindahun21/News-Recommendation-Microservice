package com.ds.user_service.model.dto;

import java.util.List;

public record PreferenceRequest (
  List<String> preferredCategories,
  List<String> blockedSources
){}
