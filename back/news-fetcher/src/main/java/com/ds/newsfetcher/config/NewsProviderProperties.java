package com.ds.newsfetcher.config;

import com.ds.newsfetcher.models.provider.ProviderConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "news")
@Data
public class NewsProviderProperties {
    private List<ProviderConfig> providers = new ArrayList<>();
}
