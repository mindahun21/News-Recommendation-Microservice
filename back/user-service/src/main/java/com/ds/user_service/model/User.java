package com.ds.user_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class User {
@Id
String username;
String password;
List<String> role;
}
