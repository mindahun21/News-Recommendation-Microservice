package com.ds.newsfetcher.models.provider;

import com.ds.newsfetcher.models.enums.ProviderType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProviderConfig {
    private String name;
    private ProviderType type;
    private String apiKeyNeeded;
    private String apiKey;
    private int rateLimitPerSecond;
    private List<ProviderFeedConfig> feeds = new ArrayList<>();
}
