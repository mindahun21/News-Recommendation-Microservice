package com.ds.user_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("demographics")
@Data
public class Demographics {
  @Id
  @Column("user_id")
  private String userId;
  private String location;
  @Column("age_range")
  private String ageRange;
}