package com.ds.user_service.model.dto;

import com.ds.user_service.model.Demographics;
import com.ds.user_service.model.Preferences;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestedNewsResponse {
    private String userId;

    private Preferences preferences;
    private float[] preferenceEmbedding;
    private Instant embeddingUpdatedAt;
    private Demographics demographics;

}
