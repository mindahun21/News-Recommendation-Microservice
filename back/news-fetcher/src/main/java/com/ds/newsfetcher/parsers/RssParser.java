package com.ds.newsfetcher.parsers;

import com.ds.newsfetcher.models.payload.RawNewsPayload;
import com.ds.newsfetcher.models.provider.ProviderConfig;
import com.ds.newsfetcher.models.provider.ProviderFeedConfig;
import com.ds.newsfetcher.util.HashUtil;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.StringReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RssParser {

        public static Flux<RawNewsPayload> parse(String xml,
                                                 ProviderConfig provider,
                                                 ProviderFeedConfig feed ){
            return Mono.fromCallable(()->parseInternal(xml, provider, feed))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMapMany(Flux::fromIterable);
        }

        private static List<RawNewsPayload> parseInternal(String xml,
                                                          ProviderConfig provider,
                                                          ProviderFeedConfig feed) throws Exception{
            List<RawNewsPayload> result = new ArrayList<>();
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed syndFeed = input.build(new StringReader(xml));

            for(SyndEntry entry : syndFeed.getEntries()){
                RawNewsPayload payload = new RawNewsPayload();
                payload.setProvider(provider.getName());
                payload.setExternalId(entry.getUri());
                payload.setTitle(entry.getTitle());
                payload.setDescription(
                        entry.getDescription() != null
                                ? entry.getDescription().getValue()
                                : null
                );
                payload.setContent(
                        entry.getContents().isEmpty()
                                ? null
                                : entry.getContents().get(0).getValue()
                );
                payload.setAuthor(entry.getAuthor());
                payload.setCategory(feed.getCategory());
                payload.setSourceUrl(entry.getLink());
                payload.setPublishedAt(
                        entry.getPublishedDate() != null
                                ? entry.getPublishedDate().toInstant()
                                : null
                );
                payload.setDedupKey(
                        HashUtil.sha256(provider.getName() + "|" + entry.getUri())
                );
                payload.setFetchedAt(Instant.now());

                result.add(payload);
            }
            return result;
        }

}
