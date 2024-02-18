package com.travellog.travellog.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {
    @NotEmpty(message = "Email field should not be empty!")
    @NotBlank(message = "Email field should not be null!")
    @Size(min = 6, max = 40, message = "Email must not be less than 6 and more than 40 characters!")
    @Email(message = "Must be a valid email!")
    private String email;

    @NotEmpty(message = "Username field should not be empty!")
    @NotBlank(message = "Username field should not be null!")
    @Size(min = 4, max = 40, message = "Username must not be less than 4 and more than 40 characters!")
    private String username;

    @NotEmpty(message = "Password field should not be empty!")
    @NotBlank(message = "Password field should not be null!")
    @Size(min = 8, max = 15, message = "Password must not be less than 8 and not more than 15 characters!")
    private String password;
}
