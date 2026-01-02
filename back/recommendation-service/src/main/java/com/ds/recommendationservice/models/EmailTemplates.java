package com.ds.recommendationservice.models;

import lombok.Getter;

public enum EmailTemplates {
    RECOMMENDATION("recommendation.html","Recommendation created");

    @Getter
    private String template;
    @Getter
    private String subjects;

    EmailTemplates(String template, String subjects) {
        this.template = template;
        this.subjects = subjects;
    }
}
