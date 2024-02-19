package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.country.CountryDetailDto;
import com.travellog.travellog.dtos.country.CreateCountryDto;

import java.util.List;

public interface ICountryService {
    CountryDetailDto createCountry(CreateCountryDto countryDto);

    List<CountryDetailDto> getCountries();

    boolean isCountryListEmpty();

    CountryDetailDto getCountryById(Integer id);
}
