package com.travellog.travellog.dtos.travelLog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.travellog.travellog.dtos.country.CountryDetailDto;
import com.travellog.travellog.dtos.travelPhoto.TravelPhotoDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelLogDetailDto {
    private Integer id;

    private String title;

    private String description;

    private LocalDate visitStartDate;

    private LocalDate visitEndDate;

    private Integer userId;

    private CountryDetailDto country;

    private List<TravelPhotoDetailDto> photos;
}
