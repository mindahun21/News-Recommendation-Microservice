package com.ds.newsfetcher.provider;

import com.ds.newsfetcher.models.payload.RawNewsPayload;
import com.ds.newsfetcher.models.provider.ProviderConfig;
import reactor.core.publisher.Flux;

public interface NewsProviderClient {
    Flux<RawNewsPayload> fetch(ProviderConfig provider);
}
