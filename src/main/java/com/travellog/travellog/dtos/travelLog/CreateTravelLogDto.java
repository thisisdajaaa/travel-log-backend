package com.travellog.travellog.dtos.travelLog;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTravelLogDto {
    @NotNull(message = "Country ID field should not be null!")
    private Integer countryId;

    @NotEmpty(message = "Title field should not be empty!")
    @NotBlank(message = "Title field should not be null!")
    @Size(min = 6, max = 100, message = "Title must not be less than 6 and more than 100 characters!")
    private String title;

    private String description;

    @NotNull(message = "Visit date field should not be null!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant visitDate;
}
