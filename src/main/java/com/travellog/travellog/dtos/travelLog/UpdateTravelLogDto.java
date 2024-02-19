package com.travellog.travellog.dtos.travelLog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travellog.travellog.helpers.DTOHelper;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DTOHelper.AtLeastOneFieldNotEmpty
public class UpdateTravelLogDto {
    private Integer countryId;

    @Size(min = 6, max = 100, message = "Title must not be less than 6 and more than 100 characters!")
    private String title;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant visitDate;
}
