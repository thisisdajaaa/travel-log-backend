package com.travellog.travellog.dtos.travelLog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travellog.travellog.helpers.DTOHelper;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DTOHelper.AtLeastOneFieldNotEmpty
public class UpdateTravelLogDto {
    private Integer countryId;

    @Size(min = 6, max = 100, message = "Title must not be less than 6 and more than 100 characters!")
    private String title;

    private String description;

    @NotNull(message = "Visit start date field should not be null!")
    private LocalDate visitStartDate;

    @NotNull(message = "Visit end date field should not be null!")
    private LocalDate visitEndDate;
}
