package com.kirana.finalphase1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank(message = "Invalid username or password")
    @Email(message = "Invalid username or password")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
            message = "Invalid username or password"
    )
    private String email;

    @NotBlank(message = "Invalid username or password")
    private String password;
}
