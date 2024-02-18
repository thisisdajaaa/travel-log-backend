package com.travellog.travellog.dtos;

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
public class CreateCountryDto {
    @NotEmpty(message = "Country name field should not be empty!")
    @NotBlank(message = "Country name field should not be null!")
    @Size(min = 6, max = 40, message = "Country name must not be less than 6 and more than 40 characters!")
    private String name;

    @NotEmpty(message = "Country code field should not be empty!")
    @NotBlank(message = "Country code field should not be blank!")
    @Size(min = 1, max = 5, message = "Country code must be 1 or 5 characters!")
    private String code;
}
