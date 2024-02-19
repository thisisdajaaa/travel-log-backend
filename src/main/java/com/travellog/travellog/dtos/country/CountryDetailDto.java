package com.travellog.travellog.dtos.country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDetailDto {
    private Integer id;

    private String name;

    private String code;
}
