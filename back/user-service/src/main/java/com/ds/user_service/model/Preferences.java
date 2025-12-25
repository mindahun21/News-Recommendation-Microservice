package com.ds.user_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("preferences")
@Data
public class Preferences {
    @Id
    @Column("user_id")
    private String userId;
    @Column("preferred_categories")
    private List<String> preferredCategories;
    @Column("blocked_sources")
    private List<String> blockedSources;
}
