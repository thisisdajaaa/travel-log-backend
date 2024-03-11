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
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

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

    @NotNull(message = "Visit start date field should not be null!")
    private LocalDate visitStartDate;

    @NotNull(message = "Visit end date field should not be null!")
    private LocalDate visitEndDate;

    private List<MultipartFile> images;
}
