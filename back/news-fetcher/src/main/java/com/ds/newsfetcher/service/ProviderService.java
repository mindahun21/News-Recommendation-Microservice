package com.ds.newsfetcher.service;

import com.ds.newsfetcher.models.enums.ProviderType;
import com.ds.newsfetcher.models.payload.RawNewsPayload;
import com.ds.newsfetcher.models.provider.ProviderConfig;
import com.ds.newsfetcher.provider.NewsProviderClient;
import com.ds.newsfetcher.provider.RssProviderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProviderService {
    private final Map<ProviderType, NewsProviderClient> clients;

    public ProviderService(List<NewsProviderClient> clientsList) {
        this.clients = clientsList.stream()
                .collect(Collectors.toMap(
                        c -> c instanceof RssProviderClient
                        ? ProviderType.RSS
                                : ProviderType.API,
                        Function.identity()
                ));
    }

    public Flux<RawNewsPayload> fetchFrom(ProviderConfig provider){
        return clients.get(provider.getType()).fetch(provider);
    }

}
