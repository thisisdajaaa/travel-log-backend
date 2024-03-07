package com.travellog.travellog.dtos.address;

import com.travellog.travellog.dtos.country.CountryDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDetailDto{
    private String addressOne;

    private String addressTwo;

    private String city;

    private String state;

    private Integer zipcode;

    private CountryDetailDto country;

}