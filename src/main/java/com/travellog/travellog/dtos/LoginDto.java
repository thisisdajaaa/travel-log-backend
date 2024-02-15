package com.travellog.travellog.dtos;

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
public class LoginDto {
    @NotEmpty(message = "Identifier field should not be empty!")
    @Size(min = 6, max = 40, message = "Identifier must not be more than 40 characters!")
    private String identifier;

    @NotEmpty(message = "Password field should not be empty!")
    @Size(min = 8, max = 15, message = "Password must not be less than 8 and not more than 15 characters!")
    private String password;
}
