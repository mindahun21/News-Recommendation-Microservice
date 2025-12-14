package com.ds.user_service.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRequest(
@NotNull
@NotEmpty
@Min(4)
String username,
@Min(8)
@Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])(?=\\S+$).{8,}$",
        message = "Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, and one special character."
)
String password
) {
}
