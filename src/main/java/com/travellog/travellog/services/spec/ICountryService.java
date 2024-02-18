package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.CountryDetailDto;
import com.travellog.travellog.dtos.CreateCountryDto;

import java.util.List;

public interface ICountryService {
    CountryDetailDto createCountry(CreateCountryDto countryDto);

    List<CountryDetailDto> getCountries();

    boolean isCountryListEmpty();
}
