package com.ds.user_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table
@Data
public class Preferences {
    @Id
    private String userId;
    private List<String> preferredCategories;
    private List<String> blockedSources;
}
