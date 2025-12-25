create table demographics
(
user_id VARCHAR PRIMARY KEY,
    age_range VARCHAR,
    location VARCHAR,
CONSTRAINT  fk_user_demographics FOREIGN KEY (user_id) REFERENCES users(username) ON DELETE CASCADE
);
create table preferences(
    user_id VARCHAR PRIMARY KEY,
    preferred_categories VARCHAR[],
    blocked_sources VARCHAR[],
CONSTRAINT  fk_user_preferences FOREIGN KEY (user_id) REFERENCES users(username) ON DELETE CASCADE
);
create table engagement_Metrics(
                user_id VARCHAR PRIMARY KEY,
                last_active TIMESTAMP WITH TIME ZONE,
                average_read_time_seconds DOUBLE PRECISION DEFAULT 0.0,
                total_articles_read INTEGER DEFAULT 0,
                CONSTRAINT fk_user_engagement FOREIGN KEY (user_id) REFERENCES users(username) ON DELETE CASCADE
);
create table recommendationProfile(
    user_id varchar primary key,
    preference_embedding float8[],
    embedding_updated_at timestamp with time zone DEFAULT now()
)