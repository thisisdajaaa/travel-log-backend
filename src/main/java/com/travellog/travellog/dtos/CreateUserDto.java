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
    @NotEmpty
    @NotBlank
    @Size(min = 6, max = 40, message = "Email must not be more than 40 characters!")
    @Email
    private String email;

    @NotEmpty
    @NotBlank
    @Size(min = 4, max = 40, message = "Username must not be more than 40 characters!")
    private String username;

    @NotEmpty
    @NotBlank
    @Size(min = 8, max = 15, message = "Password must not be less than 8 and not more than 15 characters!")
    private String password;
}
